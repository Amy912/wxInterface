package com.emagsoftware.frame.aspect;

import java.text.MessageFormat;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import com.emagsoftware.frame.log4j.ILog;
import com.emagsoftware.frame.log4j.Logger;

/**
 * 日志AOP
 */
//@Aspect
public class LogAspect {
//
//	@Logger
//	private static ILog log;
//
//	/**
//	 * 控制层前置通知
//	 *
//	 * @param jp
//	 */
//	@Before("execution(public * com.emagsoftware.migu.controller.*.*(..))")
//	public void doBeforeController(JoinPoint jp) {
//		log.info(MessageFormat.format("<<<<<<<<<<<<<<<<<Go to the controller method:{0}:{1}>>>>>>>>>>>>>>>>>", jp.getTarget().getClass().getName(), jp.getSignature().getName()));
//	}
//
//	/**
//	 * 业务层前置通知
//	 *
//	 * @param jp
//	 */
//	@Before("execution(public * com.emagsoftware.migu.service.*.*(..))")
//	public void doBeforeService(JoinPoint jp) {
//		log.info(MessageFormat.format("Go to the service method:{0}:{1}", jp.getTarget().getClass().getName(), jp.getSignature().getName()));
//	}
//
//	/**
//	 * DAO前置通知
//	 *
//	 * @param jp
//	 */
//	@Before("execution(public * com.emagsoftware.migu.dao.*.*(..))")
//	public void doBeforeDao(JoinPoint jp) {
//		log.info(MessageFormat.format("Go to the dao method:{0}:{1}", jp.getTarget().getClass().getName(), jp.getSignature().getName()));
//	}
//
//	/**
//	 * 环绕通知
//	 *
//	 * @param pjp
//	 * @return
//	 * @throws Throwable
//	 */
//	@Around("(execution(public * com.emagsoftware.migu.controller.*.*(..))) or (execution(* com.emagsoftware.migu.service.*.*(..))) or (execution(* com.emagsoftware.migu.dao.*.*(..)))")
//	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
//		long time = System.currentTimeMillis();
//		Object retVal = pjp.proceed();
//		time = System.currentTimeMillis() - time;
//		log.info(MessageFormat.format("{0}:{1}, Execution time: {2} ms", pjp.getTarget().getClass().getName(), pjp.getSignature().getName(), time));
//		return retVal;
//	}
//
//	/**
//	 * 后置通知
//	 */
//	@After("execution(public * com.emagsoftware.migu.controller.*.*(..))")
//	public void doAfterController(JoinPoint jp) {
//		log.info(MessageFormat.format("<<<<<<<<<<<<<<<<<Complete the controller method:{0}:{1}>>>>>>>>>>>>>>>>>", jp.getTarget().getClass().getName(), jp.getSignature().getName()));
//	}
//
//	/**
//	 * 后置通知
//	 */
//	@After("execution(public * com.emagsoftware.migu.service.*.*(..))")
//	public void doAfterService(JoinPoint jp) {
//		log.info(MessageFormat.format("Complete the service method:{0}:{1}", jp.getTarget().getClass().getName(), jp.getSignature().getName()));
//	}
//
//	/**
//	 * 后置通知
//	 */
//	@After("execution(public * com.emagsoftware.migu.dao.*.*(..))")
//	public void doAfterDao(JoinPoint jp) {
//		log.info(MessageFormat.format("Complete the dao method:{0}:{1}", jp.getTarget().getClass().getName(), jp.getSignature().getName()));
//	}

}