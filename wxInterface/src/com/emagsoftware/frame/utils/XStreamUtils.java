package com.emagsoftware.frame.utils;

import java.io.Writer;

import org.apache.commons.lang.StringUtils;

import com.emagsoftware.frame.log4j.ILog;
import com.emagsoftware.frame.log4j.Logger;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * XStream 工具 接口输入输出参数 XML与BEAN转换 XML的解析支撑类
 */
@SuppressWarnings("deprecation")
public final class XStreamUtils {

	public static final String XML_HEAD = "<?xml version='1.0' encoding='UTF-8'?>";
	
	@Logger
	private static ILog log;

	/**
	 * <默认构造函数>
	 */
	private XStreamUtils() {
	}

	/**
	 * xml类型转换成Object类型
	 * 
	 * @param xml
	 * @param objClass
	 * @return 转换后的对象
	 */
	public static Object parseXmlToObj(String xml, Class<?> objClass) {
		Object obj = null;
		try {
			XStream xstream = new XStream();
			// 显示声明使用注解
			xstream.processAnnotations(objClass);
			xstream.autodetectAnnotations(true);
			obj = xstream.fromXML(xml);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("ERROR:xml解析出错：" + xml + "\n", e);
		}
		return obj;
	}

	/**
	 * Object类型转换成xml
	 * 
	 * @param out
	 * @return String 输入xml
	 */
	public static String parseObjToXml(Object out) {
		String str = null;
		try {
			XStream xstream = new XStream(new XppDriver(new XmlFriendlyReplacer("_-", "_")));// 解决解析下划线时出现双下划线
			// 显示声明使用注解
			xstream.processAnnotations(out.getClass());
			xstream.autodetectAnnotations(true);
			str = xstream.toXML(out);
			str = XStreamUtils.XML_HEAD + str;
		} catch (Exception e) {
			// log.error("ERROR:Object类型转换成xml出错。\n", e);
		}
		return str;
	}

	public static String parseObjToXmlCDATA(Object out) {
		String str = null;
		try {
			XStream xstream = new XStream(new XppDriver() {

				public HierarchicalStreamWriter createWriter(Writer out) {
					return new PrettyPrintWriter(out) {

						public void startNode(String name, @SuppressWarnings("rawtypes") Class clazz) {
							super.startNode(name, clazz);
						}

						protected void writeText(QuickWriter writer, String text) {
							if (StringUtils.startsWith(text, "<![CDATA[") && StringUtils.endsWith(text, "]]>")) {
								writer.write(text);
							} else if (StringUtils.containsIgnoreCase(text, "http://")
									|| StringUtils.containsIgnoreCase(text, "http.//")
									|| StringUtils.contains(text, "&") || StringUtils.contains(text, "<")
									|| StringUtils.contains(text, ">")) {
								writer.write("<![CDATA[");
								writer.write(text);
								writer.write("]]>");
							} else {
								writer.write(text);
							}
						}
					};
				}
			});
			// 显示声明使用注解
			xstream.processAnnotations(out.getClass());
			xstream.autodetectAnnotations(true);
			str = xstream.toXML(out);
			str = XStreamUtils.XML_HEAD + str;
		} catch (Exception e) {
			// log.error("ERROR:Object类型转换成xml出错。\n", e);
		}
		return str;
	}
}
