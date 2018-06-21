package com.emagsoftware.frame.utils;

public class SystemConstant {

	/** 日志KEY */
	public static final String LOG_ID = "ID";

	public static final String TIMESTAMP = "TIMESTAMP";

	/** UTF-8字符集编码 */
	public static final String CH_ENCODING_UTF8 = "UTF-8";

	/** GBK字符集编码 */
	public static final String CH_ENCODING_GBK = "GBK";

	/** XML_HEAD */
	public static final String XML_HEAD = "<?xml version='1.0' encoding='UTF-8'?>";

	/** XML转义 */
	public static final String CDATA_PREFIX = "<![CDATA[";

	/** XML转义 */
	public static final String CDATA_SUFFIX = "]]>";

	/** HTTP协议 Content-Type */
	public static final String HTTP_CONTENT_TYPE = "Content-Type";

	/**
	 * HTTP协议 Content-Type为multipart/form-data
	 */
	public static final String HTTP_MULTIPART_FORM_DATA = "multipart/form-data";

	/**
	 * HTTP协议 Content-Type为application/x-www-form-urlencoded
	 */
	public static final String HTTP_APPLICATION_FORM_URLENCODED = "application/x-www-form-urlencoded";

	/** 空格符 */
	public static String SPACE = " ";

	/** 顿号 */
	public static String SIGN = "、";

	/** 逗号 */
	public static String COMMA = "，";

	/** 句号 */
	public static String PERIOD = "。 ";

	/** 拼接符 */
	public static String MONTAGE = "-";

	/** 空字符 */
	public static String EMPTY = "";

	public static final String RETURN_MESSAGE = "retMsg";

	/** 时间格式 */
	public static final String FORMAT_YYYY_MM_DDHHMMSS = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 未被程序捕获异常
	 */
	public static final String UNCAUGHT_EXCEPTION = "javax.servlet.error.exception";

	public static final String LOG_TYPE_INSERT = "insert";
	public static final String LOG_TYPE_UPDATE = "update";
	public static final String LOG_TYPE_DELETE = "delete";
	public static final String LOG_TYPE_LOGIN = "login";
	public static final String LOG_TYPE_AUDIT = "audit";
	public static final String LOG_TYPE_OFFLINE = "offline";
	public static final String LOG_TYPE_RECOMMEND = "recommend";

}