package com.emagsoftware.frame.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.MDC;
import org.springframework.context.annotation.Configuration;

import com.emagsoftware.frame.log4j.ILog;
import com.emagsoftware.frame.log4j.Logger;
import com.emagsoftware.frame.utils.BaseUtils;
import com.emagsoftware.frame.utils.SystemConstant;

@Configuration
public class RequestListener implements ServletRequestListener {

	@Logger
	protected static ILog log;

	public void requestInitialized(ServletRequestEvent arg0) {
		// 日志编号
		MDC.put(SystemConstant.LOG_ID, BaseUtils.getLogId());
		// 记录时间
		MDC.put(SystemConstant.TIMESTAMP, System.currentTimeMillis());
	}

	public void requestDestroyed(ServletRequestEvent event) {
		// 记录未被程序捕获，但被容器捕获的异常信息
//		((javax.servlet.http.HttpServletRequest) request).getRequestURL()
		HttpServletRequest request = (HttpServletRequest) event.getServletRequest();
		String url=request.getRequestURI().toLowerCase();
		if(url.endsWith(".jpg") || url.endsWith(".png") || url.endsWith(".css") || url.endsWith(".js")){
			return;
		}
		log.info("访问URL为：" + url);
		if (request != null) {
			Object object = request.getAttribute(SystemConstant.UNCAUGHT_EXCEPTION);
			if (object != null && object instanceof Throwable) {
				log.error(SystemConstant.UNCAUGHT_EXCEPTION, (Throwable) object);
			}
		}

		log.info("耗时:" + (System.currentTimeMillis() - ((Long) MDC.get(SystemConstant.TIMESTAMP)) + "毫秒"));

		// 重置日志唯一标识
		MDC.remove(SystemConstant.LOG_ID);
		MDC.remove(SystemConstant.TIMESTAMP);
	}
}
