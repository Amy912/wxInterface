package com.emagsoftware.frame.utils;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.emagsoftware.frame.utils.CommonUtil;

public final class ConfigCache {

	private static Logger logger = Logger.getLogger(ConfigCache.class);

	/** 小程序appid */
	private static String appid;

	/** 配置文件信息 */
	private static Map<String, String> configMap;

	/**
	 * 默认构造函数
	 */
	private ConfigCache() {

	}

	/**
	 * 读配置文件信息，存入缓存
	 */
	public static void readConfigCache() {
		long logId = BaseUtils.getLogId();
		try {
			configMap = CommonUtil.getPropertiesConfig("config");
			if (null != configMap && !configMap.isEmpty()) {
				readAppid();
			}
		} catch (Exception e) {
			logger.info(logId + "ERROR:读取配置文件信息出错。", e);

		}

	}

	public static String getAppid() {
		return appid;
	}

	private static void readAppid() {
		String str = configMap.get("wx.little.appid");
		if (StringUtils.isBlank(str)) {
			logger.error("启动异常，wx.little.appid为空");
		} else {
			appid = str;
			logger.info("读配置文件wx.little.appid：" + appid);
		}
	}
	
	public static String getOtherConfigValue(String key) {
		if (configMap == null) {
			try {
				configMap = CommonUtil.getPropertiesConfig("config");
			} catch (Exception e) {
				logger.info("读配置文件，页面配置路径为：" + e.getMessage());
			}
		}
		return configMap.get(key);
	}

}
