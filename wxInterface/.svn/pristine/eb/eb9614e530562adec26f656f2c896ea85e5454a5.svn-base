package com.emagsoftware.frame.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.emagsoftware.frame.bean.BaseResponseBean;
import com.emagsoftware.frame.log4j.ILog;
import com.emagsoftware.frame.log4j.Logger;
import com.emagsoftware.frame.utils.SystemConstant;
import com.emagsoftware.frame.utils.XStreamUtils;

/**
 * @Title: 控制层基类
 * @author LH
 */
@Component
public class BaseController {

	@Autowired
	private ApplicationContext ctx;

	@Logger
	protected static ILog log;

	@Autowired
	protected HttpServletRequest request;

	/** 返回空白JSP画面 */
	protected static final String RET_JSP = "ret";

	/** 重定向画面（URL转到目标） */
	protected static final String REDIRECT_JSP = "redirect";

	/**
	 * 返回上下文
	 * 
	 * @return
	 */
	protected ApplicationContext getCtx() {
		return ctx;
	}

	/**
	 * 返回方法
	 * 
	 * @param logId
	 * @param model
	 * @param responseBean
	 * @param stime
	 * @return
	 */
	protected String finishBack(long logId, Model model, BaseResponseBean responseBean, long stime) {
		String xml = XStreamUtils.parseObjToXml(responseBean);
		long etime = System.currentTimeMillis();
		log.info(logId, "业务处理结束！花费" + (etime - stime) + "毫秒！");
		log.info(logId, "returnXML:" + xml);
		model.addAttribute(SystemConstant.RETURN_MESSAGE, xml);
		return RET_JSP;
	}

	protected int pagesize = 10, all = 0;

	private int maxpage = 0, currentPage = 1;

	protected Map<String, Object> initPage(Map<String, Object> params, Model model) {
		getPageNo();
		if (params == null) {
			params = new HashMap<String, Object>();
		}
		params.put("pageStart", (currentPage - 1) * this.pagesize);
		params.put("pageSize", this.pagesize);

		String pageStr = getPageIndex(this.getmaxpage(), currentPage, all);
		model.addAttribute("pageStr", pageStr);
		model.addAttribute("pageNumber", currentPage);
		request.setAttribute("pageNumber", currentPage);
		request.setAttribute("pageCount", getmaxpage());
		request.setAttribute("itemCount", all);
		return params;
	}

	protected Map<String, Object> initPage(Model model) {
		return initPage(null, model);
	}

	/**
	 * 获得当前页码
	 * 
	 * @return
	 */
	private void getPageNo() {
		try {
			String pageNo = request.getParameter("pageNumber");
			if (StringUtils.isBlank(pageNo)) {
				currentPage = 1;
			} else {
				currentPage = Integer.valueOf(StringUtils.trim(pageNo));
			}
		} catch (Exception e) {
			currentPage = 1;
			e.printStackTrace();
		}
	}

	/**
	 * 分页函数
	 */
	private int getmaxpage() {
		int tmp = (all / pagesize);
		int m = (all % pagesize);
		if (m == 0) {
			if (tmp == 0) {
				maxpage = 1;
			} else {
				maxpage = tmp;
			}
		} else {
			maxpage = tmp + 1;
		}
		return maxpage;
	}

	/**
	 * 生成翻页索引html
	 * 
	 * @param page
	 * @return
	 */
	private String getPageIndex(int pageCount, int curPage, int recordCount) {
		StringBuffer ret = new StringBuffer();
		ret.append("<div id=\"wp_page_numbers\"><ul>");
		if (pageCount >= 2) {
			Set<Integer> set = new HashSet<Integer>();
			set.add(Integer.valueOf(1));
			set.add(Integer.valueOf(2));
			set.add(Integer.valueOf(pageCount));
			set.add(Integer.valueOf(curPage));
			if (curPage > 3) {
				set.add(Integer.valueOf(curPage - 1));
			}
			if (curPage > 4) {
				set.add(Integer.valueOf(curPage - 2));
			}
			if (curPage < pageCount - 2) {
				set.add(Integer.valueOf(curPage + 1));
			}
			if (curPage < pageCount - 3) {
				set.add(Integer.valueOf(curPage + 2));
			}
			if (pageCount > 3) {
				set.add(Integer.valueOf(pageCount - 1));
			}
			List<Integer> list = new ArrayList<Integer>(set);
			Collections.sort(list);

			if (curPage > 1) {
				ret.append("<li><a href=\"javascript:NagivatePage(").append(curPage - 1).append(");\">&lt;</a></li>");
			} else {
				ret.append("<li class=\"no_link\">&lt;</li>");
			}

			for (int i = 0; i < list.size(); i++) {
				Integer num = (Integer) list.get(i);
				if (ret.length() > 0) {
					ret.append(" ");
				}
				if (i > 1 && ((Integer) list.get(i)).intValue() - ((Integer) list.get(i - 1)).intValue() > 1) {
					ret.append("<li class=\"space\">...</li>");
				}
				ret.append(renderSingleLink(num.intValue(), curPage));
			}

			if (curPage < pageCount) {
				ret.append("<li><a href=\"javascript:NagivatePage(").append(curPage + 1).append(");\">&gt;</a></li>");
			} else {
				ret.append("<li class=\"no_link\">&gt;</li>");
			}
		}
		ret.append("<li class=\"page_info\">共 ").append(recordCount).append(" 条记录</li>");
		ret.append("</ul><div style=\"float: none; clear: both;\"></div></div>");
		return ret.toString();
	}

	private String renderSingleLink(int num, int curPage) {
		StringBuffer sb = new StringBuffer();
		if (num == curPage) {
			sb.append("<li class=\"active_page\">").append(num).append("</li>");
		} else {
			sb.append("<li><a href=\"javascript:NagivatePage(").append(num).append(");\">").append(num).append("</a></li>");
		}
		return sb.toString();
	}

}