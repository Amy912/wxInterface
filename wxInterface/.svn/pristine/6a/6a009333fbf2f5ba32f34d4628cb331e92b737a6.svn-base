package com.emagsoftware.frame.utils;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import net.sf.json.JSONObject;

public class CommonUtil {

	private static Logger logger = Logger.getLogger(CommonUtil.class);

	private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
			'E', 'F' };

	@SuppressWarnings("rawtypes")
	public static String covertRealPath(Class className, String fileName) {
		String classPathName = "";
		try {
			URL url = className.getClassLoader().getResource(fileName);
			classPathName = url.getPath();
			int endingIndex = classPathName.length() - fileName.length();
			classPathName = classPathName.substring(0, endingIndex);
		} catch (Exception e) {
			System.out.println(CommonUtil.class.getName() + " Exception " + e);
			classPathName = className.getClassLoader().getResource("").getPath();
		}
		try {
			classPathName = java.net.URLDecoder.decode(classPathName, "utf-8");
		} catch (UnsupportedEncodingException e) {
			System.out.println(CommonUtil.class.getName() + " UnsupportedEncodingException " + e);
			classPathName = className.getClassLoader().getResource("").getPath();
		}
		File file = new File(classPathName + fileName);
		if (!file.exists()) {
			classPathName = className.getResource("/").getPath();
		}
		return classPathName;
	}

	public static boolean checkMobile(String mobile) {
		Pattern pattern = Pattern.compile("^1\\d{10}$");
		Matcher matcher = pattern.matcher(mobile);
		return matcher.find();
	}

	/**
	 * 根据Properties文件名，读取内容
	 * 
	 * @param name
	 *            Properties文件名（不含路径）
	 * @return HashMap<String, String> Properties文件内容
	 */
	public static HashMap<String, String> getPropertiesConfig(String name) throws Exception {
		if (!StringUtils.isEmpty(name)) {
			// 文件名(包括路径)
			String filename = name + ".properties";
			ClassLoader cl = CommonUtil.class.getClassLoader();
			InputStream is = cl.getResourceAsStream(filename);

			MyPropertiesConfig my = new MyPropertiesConfig();
			HashMap<String, String> map = my.loadValues(is);
			return map;
		}
		return null;
	}

	/**
	 * 检查值body是否有值，没值给""字段串
	 */
	public static String checkBodyStrParam(String param, JSONObject jbody) {
		return jbody.get(param) == null ? StringUtils.EMPTY : jbody.get(param).toString();
	}

	// 获得本机IP
	public static String getLocalHost() {
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		String ip = addr.getHostAddress().toString();

		return ip;
	}

	public static String toHexString(byte[] b) {
		// String to byte
		StringBuilder sb = new StringBuilder(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
			sb.append(HEX_DIGITS[b[i] & 0x0f]);
		}
		return sb.toString();
	}

	public static String mmd5(String s) {
		try {
			logger.info("md5src:[" + s + "]");
			// Create MD5 Hash
			MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
			digest.update(s.getBytes());
			byte messageDigest[] = digest.digest();
			String ret = toHexString(messageDigest);
			logger.info("md5to:[" + ret + "]");
			return ret;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return "";
	}

}
