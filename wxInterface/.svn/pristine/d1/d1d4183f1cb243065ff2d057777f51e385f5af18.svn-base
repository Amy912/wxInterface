package com.emagsoftware.frame.listener;

import org.springframework.context.ApplicationContext;

public class SpringContext {

	private static ApplicationContext context;

	public static ApplicationContext getContext() {
		return context;
	}

	public static void setContext(ApplicationContext applicationContext) {
		context = applicationContext;
	}

	public static Object getBean(String name) {
		return context.getBean(name);
	}

	public static Object getService(String name) {
		return getBean(name);
	}

	public static Object getBeanByClass(Class<?> clazz) {
		return context.getBean(clazz);
	}
}