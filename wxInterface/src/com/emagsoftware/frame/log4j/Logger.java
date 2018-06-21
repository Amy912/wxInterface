package com.emagsoftware.frame.log4j;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Title: 添加日志注解类
 * @Copyright: Copyright (c) 2012-7-27
 * @Company: 幻方朗睿
 * @Author: Andy.D
 * @Version: 1.0
 * 
 */
// 运行时编译
@Retention(RetentionPolicy.RUNTIME)
// 修饰字段
@Target({ ElementType.FIELD })
public @interface Logger {

}