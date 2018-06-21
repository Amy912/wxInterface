package com.emagsoftware.frame.log4j;

/**
 * @Title: 自定义日志工厂类
 * @Copyright: Copyright (c) 2012-7-27
 * @Company: 幻方朗睿
 * @Author: Andy.D
 * @Version: 1.0
 */
public class ILogFactory {
    /**
     * 取得ILog接口实例
     * 
     * @param clazz
     * @return ILog接口实例
     */
    public static ILog getILogInstance(Class<?> clazz) {
        return new ILogImpl(clazz);
    }
}