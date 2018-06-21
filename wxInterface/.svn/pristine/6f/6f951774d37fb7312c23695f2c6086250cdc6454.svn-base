package com.emagsoftware.frame.utils;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class DbUtils {

	/**
	 * map转xml
	 * 
	 * @param map
	 * @param itemId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String praseObj2Xml(Object obj, String itemId) {
		StringBuilder sb = new StringBuilder();
		if (StringUtils.isNotBlank(itemId)) {
			sb.append("<item id=\"").append(itemId).append("\">");
		} else {
			sb.append("<item>");
		}
		if (obj != null) {
			if (obj instanceof Map) {
				sb.append(praseMap2Xml((Map<String, String>) obj));
			}
		}
		sb.append("</item>");
		return sb.toString();
	}

	/**
	 * map转xml
	 * 
	 * @param map
	 * @param itemId
	 * @return
	 */
	public static String praseMap2Xml(Map<String, String> map, String itemId) {
		StringBuilder sb = new StringBuilder();
		if (StringUtils.isNotBlank(itemId)) {
			sb.append("<item id=\"").append(itemId).append("\">");
		} else {
			sb.append("<item>");
		}
		for (String key : map.keySet()) {
			if (StringUtils.containsIgnoreCase(map.get(key), "http") || StringUtils.contains(map.get(key), "&")) {
				sb.append("<").append(key).append("><![CDATA[").append(map.get(key)).append("]]></").append(key)
						.append(">");
			} else {
				sb.append("<").append(key).append(">").append(map.get(key)).append("</").append(key).append(">");
			}
		}
		sb.append("</item>");
		return sb.toString();
	}

	/**
	 * map转xml
	 * 
	 * @param map
	 * @param itemId
	 * @return
	 */
	public static String praseMap2Xml(Map<String, String> map) {
		StringBuilder sb = new StringBuilder();
		for (String key : map.keySet()) {
			if (StringUtils.containsIgnoreCase(String.valueOf(map.get(key)), "http")
					|| StringUtils.contains(String.valueOf(map.get(key)), "&")) {
				sb.append("<").append(key).append("><![CDATA[").append(String.valueOf(map.get(key))).append("]]></")
						.append(key).append(">");
			} else {
				sb.append("<").append(key).append(">").append(String.valueOf(map.get(key))).append("</").append(key)
						.append(">");
			}
		}
		return sb.toString();
	}

	/**
	 * map转xml
	 * 
	 * @param map
	 * @param itemId
	 *            sh
	 * @return
	 */
	public static String praseMap2Xmls(Map<String, String> map, String itemId, List<String> list) {
		StringBuilder sb = new StringBuilder();
		if (StringUtils.isNotBlank(itemId)) {
			sb.append("<item id=\"").append(itemId).append("\">");
		} else {
			sb.append("<item>");
		}
		for (String key : map.keySet()) {
			for (int i = 0; i < list.size(); i++) {
				if ((key.equals(list.get(i))) && (StringUtils.containsIgnoreCase(map.get(key), "http")
						|| StringUtils.contains(map.get(key), "&"))) {
					sb.append("<").append(key).append("><![CDATA[").append(map.get(key)).append("]]></").append(key)
							.append(">");
				} else if (key.equals(list.get(i))) {
					sb.append("<").append(key).append(">").append(map.get(key)).append("</").append(key).append(">");
				}
			}
		}
		sb.append("</item>");
		return sb.toString();
	}

	/**
	 * list转xml
	 * 
	 * @param list
	 * @param listId
	 *            sh
	 * @return
	 */
	public static String praseList2Xmls(List<Map<String, String>> list, String listId, List<String> list2) {
		StringBuilder sb = new StringBuilder();
		if (StringUtils.isNotBlank(listId)) {
			sb.append("<list id=\"").append(listId).append("\">");
		} else {
			sb.append("<list>");
		}
		for (Map<String, String> map : list) {
			sb.append(praseMap2Xmls(map, null, list2));
		}
		sb.append("</list>");
		return sb.toString();
	}

	/**
	 * 广告位元素或导航元素list转xml
	 * 
	 * @param list
	 * @param listId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String praseList2Xml(List lists, String listId) {
		StringBuilder sb = new StringBuilder();
		if (StringUtils.isNotBlank(listId)) {
			sb.append("<list id=\"").append(listId).append("\">");
		} else {
			sb.append("<list>");
		}
		// 支持多种类型
		for (Object o : lists) {
			sb.append(praseObj2Xml(o, null));
		}
		sb.append("</list>");
		return sb.toString();
	}

}
