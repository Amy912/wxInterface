package com.emagsoftware.frame.log4j;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @Title: 自动注入logger实例
 * @Copyright: Copyright (c) 2012-7-27
 * @Company: 幻方朗睿
 * @Author: Andy.D
 * @Version: 1.0
 * 
 */
// Log4j日志注解化的应用 基本原理如下: 通过自定义一个BeanPostProcessor, 在对所有bean初始化之前,
// 对每一个bean的field进行检查, 是否适用了Logger注解, 如果有, 则调用LogFactory创建一个logger实例.
public class LogBeanPostProcessor implements BeanPostProcessor {

    /**
     * 初始化之后的操作
     */
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    /**
     * 初始化之前的操作
     * 
     * @param bean
     * @param clazz
     */
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        List<Class<?>> clazzes = getAllClasses(bean);

        for (Class<?> clazz : clazzes) {
            initializeLog(bean,
                    clazz);
        }

        return bean;
    }

    /**
     * 取得指定bean的class以及所有父类的列表, 该列表排列顺序为从父类到当前类
     * 
     * @param bean
     * @return
     */
    private List<Class<?>> getAllClasses(Object bean) {
        Class<? extends Object> clazz = bean.getClass();
        List<Class<?>> clazzes = new ArrayList<Class<?>>();
        while (clazz != null) {
            clazzes.add(clazz);
            clazz = clazz.getSuperclass();
        }

        Collections.reverse(clazzes);
        return clazzes;
    }

    /**
     * 对logger变量进行初始化
     * 
     * @param bean
     * @param clazz
     */
    private void initializeLog(Object bean, Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.getAnnotation(Logger.class) == null) {
                continue;
            }

            if (!field.getType().isAssignableFrom(ILog.class)) {
                continue;
            }

            field.setAccessible(true);
            try {
                field.set(bean,
                        ILogFactory.getILogInstance(clazz));
            } catch (Exception e) {
                throw new BeanInitializationException(String.format("初始化logger失败!bean=%s;field=%s",
                        bean,
                        field));
            }

        }
    }

}