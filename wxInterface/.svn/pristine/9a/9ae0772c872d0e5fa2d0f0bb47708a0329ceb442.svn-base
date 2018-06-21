package com.emagsoftware.frame.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;


/**
 * 
 * Title. 定制化系统实时接口 class. ClientHttpConUtil
 * <p>
 * 客服端发送POS请求 Copyright: Jun 5, 2015 4:22:44 PM
 * <p>
 * Company: 南京朗睿技术有限公司
 * <p>
 * Author: zhx
 * <p>
 * Version: 1.0
 * <p>
 */
public class ClientHttpConUtil {

	private static Logger logger = Logger.getLogger(ClientHttpConUtil.class);

	
	private static org.apache.http.client.HttpClient httpclient = null;
	
	private static final String CH_ENCODING_UTF8 = "UTF-8";
	
	public static String clientHttpConnection(String req, String url) {
		logger.info("发送请求地址：" + url);
		HttpClient httpclient = new HttpClient();
		httpclient.getParams().setBooleanParameter("http.protocol.expect-continue", false);

		HttpConnectionManagerParams params = httpclient.getHttpConnectionManager().getParams();
		params.setConnectionTimeout(50000);
		params.setSoTimeout(210000);

		PostMethod meth = null;

		String backXml = null;
		try {
			meth = new PostMethod(url);
			meth.setRequestHeader("Connection", "close");

			meth.setRequestEntity(new StringRequestEntity(req, "text/xml; charset=" + CH_ENCODING_UTF8, null));
			httpclient.executeMethod(meth);
			backXml = meth.getResponseBodyAsString();
		} catch (Exception e) {
			logger.error("调用接口失败:" + url, e);
			e.printStackTrace();
		} finally {
			if (meth != null) {
				meth.releaseConnection();
			}

			((SimpleHttpConnectionManager) httpclient.getHttpConnectionManager()).shutdown();
		}
		return backXml;
	}

	/**
	 * 热词
	 */
	public static String clientHttpConnectionHotWords(String req, String url) {
		logger.info("发送请求地址：" + url);
		HttpClient httpclient = new HttpClient();
		httpclient.getParams().setBooleanParameter("http.protocol.expect-continue", false);

		HttpConnectionManagerParams params = httpclient.getHttpConnectionManager().getParams();
		params.setConnectionTimeout(50000);
		params.setSoTimeout(210000);

		PostMethod meth = null;

		String backXml = null;
		try {
			meth = new PostMethod(url);
			meth.setRequestHeader("Connection", "close");

			meth.setRequestEntity(new StringRequestEntity(req, "text/xml; charset=" + CH_ENCODING_UTF8, null));
			httpclient.executeMethod(meth);
			InputStream responseBody = meth.getResponseBodyAsStream();
			backXml = new String(toByteArray(responseBody), "utf-8");
		} catch (Exception e) {
			logger.error("调用接口失败:" + url, e);
			e.printStackTrace();
		} finally {
			if (meth != null) {
				meth.releaseConnection();
			}

			((SimpleHttpConnectionManager) httpclient.getHttpConnectionManager()).shutdown();
		}
		return backXml;
	}

	public static String sendHTTPSJSON(String url, String json) {
		HttpClient httpclient = new HttpClient();
		PostMethod post = new PostMethod(url);
		HttpConnectionManagerParams params = httpclient.getHttpConnectionManager().getParams();
		params.setConnectionTimeout(50000);
		params.setSoTimeout(210000);

		String info = null;
		try {
			RequestEntity entity = new StringRequestEntity(json, "application/json", "utf-8");
			post.setRequestEntity(entity);
			httpclient.executeMethod(post);
			int code = post.getStatusCode();
			logger.info("code: " + code);
			if (code == HttpStatus.SC_OK)
				info = new String(post.getResponseBodyAsString());
		} catch (Exception ex) {
			logger.error("调用接口失败:" + url, ex);
		} finally {
			post.releaseConnection();
		}
		return info;
	}


	/**
	 * 发送http请求
	 * 
	 * @param req
	 *            内容体
	 * @param url
	 *            url
	 * @param headMap
	 *            消息头
	 * @return 返回消息
	 */
	public static String clientHttpConnection(String req, String url, Map<String, String> headMap) {
		logger.info("发送请求地址：" + url);
		HttpClient httpclient = new HttpClient();
		httpclient.getParams().setBooleanParameter("http.protocol.expect-continue", false);

		HttpConnectionManagerParams params = httpclient.getHttpConnectionManager().getParams();
		params.setConnectionTimeout(50000);
		params.setSoTimeout(210000);

		PostMethod meth = null;

		String backXml = null;
		try {
			meth = new PostMethod(url);
			meth.setRequestHeader("Connection", "close");
			// 增加消息头的添加
			if (headMap != null && !headMap.isEmpty()) {
				Iterator<String> iterator = headMap.keySet().iterator();
				while (iterator.hasNext()) {
					String key = iterator.next();
					String value = headMap.get(key);
					meth.setRequestHeader(key, value);
				}

			}
			meth.setRequestEntity(new StringRequestEntity(req, "text/xml; charset=" + CH_ENCODING_UTF8, null));
			httpclient.executeMethod(meth);
			backXml = meth.getResponseBodyAsString();
		} catch (Exception e) {
			logger.error("调用接口失败:" + url, e);
			e.printStackTrace();
		} finally {
			if (meth != null) {
				meth.releaseConnection();
			}

			((SimpleHttpConnectionManager) httpclient.getHttpConnectionManager()).shutdown();
		}
		return backXml;
	}

	/**
	 * 发送http请求
	 * 
	 * @param req
	 *            内容体
	 * @param url
	 *            url
	 * @param headMap
	 *            消息头
	 * @return 返回消息
	 */
	public static String clientHttpConnection(String body, String url, Map<String, String> headMap, Map<String, String> paramsMap) {
		logger.info("发送请求地址：" + url);
		HttpClient httpclient = new HttpClient();
		httpclient.getParams().setBooleanParameter("http.protocol.expect-continue", false);

		HttpConnectionManagerParams params = httpclient.getHttpConnectionManager().getParams();
		params.setConnectionTimeout(50000);
		params.setSoTimeout(210000);

		PostMethod meth = null;

		String backXml = null;
		try {
			meth = new PostMethod(url);
			if (body == null) {
				body = "";
			}
			meth.setRequestEntity(new StringRequestEntity(body, "text/xml; charset=" + CH_ENCODING_UTF8, null));
			meth.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
			// 增加消息头的添加
			if (headMap != null && !headMap.isEmpty()) {
				logger.info("填充消息头：" + headMap.size());
				Iterator<String> iterator = headMap.keySet().iterator();
				while (iterator.hasNext()) {
					String key = iterator.next();
					String value = headMap.get(key);
					meth.setRequestHeader(key, value);
				}
			}
			// 向请求中加入需要post过去的数据
			if (paramsMap != null && !paramsMap.isEmpty()) {
				logger.info("填充消息体：" + paramsMap.size());
				Iterator<String> iterator = paramsMap.keySet().iterator();
				while (iterator.hasNext()) {
					String key = iterator.next();
					String value = paramsMap.get(key);
					meth.addParameter(key, value);
				}
			}
			httpclient.executeMethod(meth);
			backXml = meth.getResponseBodyAsString();
		} catch (Exception e) {
			logger.error("调用接口失败:" + url, e);
			e.printStackTrace();
		} finally {
			if (meth != null) {
				meth.releaseConnection();
			}

			((SimpleHttpConnectionManager) httpclient.getHttpConnectionManager()).shutdown();
		}
		return backXml;
	}

	/**
	 * 流转byte[]
	 */
	public static byte[] toByteArray(InputStream input) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] buffer = new byte[4096];
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
		}
		return output.toByteArray();
	}

	public static String getLocation(String url) {
		String location = "";
		HttpURLConnection conn = null;
		try {
			logger.info("访问地址:" + url);
			URL serverUrl = new URL(url);
			conn = (HttpURLConnection) serverUrl.openConnection();
			conn.setRequestMethod("GET");
			// 必须设置false，否则会自动redirect到Location的地址
			conn.setInstanceFollowRedirects(false);

			conn.addRequestProperty("Accept-Charset", "UTF-8;");
			conn.connect();
			location = conn.getHeaderField("Location");
			logger.info("跳转地址:" + location);
		} catch (Exception e) {
			logger.error("访问异常", e);
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return location;
	}

	

	public static String doHttpsPost(String url, Map<String, String> paramMap, Map<String, String> headMap, String charset) {
		org.apache.http.client.HttpClient httpClient = null;
		HttpPost httpPost = null;
		String result = null;
		try {
			httpClient = new SSLClient();
			httpPost = new HttpPost(url);
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).setConnectionRequestTimeout(5000).build();
			httpPost.setConfig(requestConfig);
			// 设置参数
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			if (paramMap != null) {
				Iterator iterator = paramMap.entrySet().iterator();
				while (iterator.hasNext()) {
					Entry<String, String> elem = (Entry<String, String>) iterator.next();
					list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
				}
				if (list.size() > 0) {
					UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
					httpPost.setEntity(entity);
				}
			}
			if (headMap != null && !headMap.isEmpty()) {
				logger.info("填充消息头：" + headMap.size());
				Iterator<String> iterator2 = headMap.keySet().iterator();
				while (iterator2.hasNext()) {
					String key = iterator2.next();
					String value = headMap.get(key);
					httpPost.addHeader(key, value);
				}
			}
			HttpResponse response = httpClient.execute(httpPost);
			if (response != null) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					result = EntityUtils.toString(resEntity, charset);
				}
			}
		} catch (Exception ex) {
			logger.error("发送HTTPS请求失败。", ex);
		} finally {
			if (httpPost != null) {
				httpPost.releaseConnection();
			}
		}
		return result;
	}

	/**
	 * 功能：json post请求
	 * 
	 * @param req
	 * @param url
	 * @return
	 */
	public static String postHttpJson(String req, String url) {
		logger.info("发送请求地址：" + url);
		HttpClient httpclient = new HttpClient();
		httpclient.getParams().setBooleanParameter("http.protocol.expect-continue", false);

		HttpConnectionManagerParams params = httpclient.getHttpConnectionManager().getParams();
		params.setConnectionTimeout(50000);
		params.setSoTimeout(210000);

		PostMethod meth = null;

		String backXml = null;
		try {
			meth = new PostMethod(url);
			meth.setRequestHeader("Connection", "close");

			meth.setRequestEntity(new StringRequestEntity(req, "text/xml; charset=" + CH_ENCODING_UTF8, null));
			httpclient.executeMethod(meth);
			backXml = new String(meth.getResponseBody(), "utf-8");
		} catch (Exception e) {
			logger.error("调用接口失败:" + url, e);
			e.printStackTrace();
		} finally {
			if (meth != null) {
				meth.releaseConnection();
			}
			((SimpleHttpConnectionManager) httpclient.getHttpConnectionManager()).shutdown();
		}
		return backXml;
	}

	public static String getHttpsPost(String url, String param) {
		org.apache.http.client.HttpClient httpClient = null;
		HttpPost httpPost = null;
		String result = null;
		try {
			httpClient = new SSLClient();
			httpPost = new HttpPost(url);
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).setConnectionRequestTimeout(5000).build();
			httpPost.setConfig(requestConfig);
			if (StringUtils.isNotEmpty(param)) {
				StringEntity entity = new StringEntity(param);
				httpPost.setEntity(entity);
			}
			HttpResponse response = httpClient.execute(httpPost);
			if (response != null) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					result = EntityUtils.toString(resEntity, "UTF-8");
				}
			}
		} catch (Exception ex) {
			logger.error("发送HTTPS请求失败。", ex);
		} finally {
			if (httpPost != null) {
				httpPost.releaseConnection();
			}
		}

		return result;
	}

	public static String searchClientHttpConnection(String req, String url) {
		logger.info("发送请求地址：" + url);
		HttpClient httpclient = new HttpClient();
		httpclient.getParams().setBooleanParameter("http.protocol.expect-continue", false);

		HttpConnectionManagerParams params = httpclient.getHttpConnectionManager().getParams();
		params.setConnectionTimeout(50000);
		params.setSoTimeout(210000);

		PostMethod meth = null;

		String backXml = null;
		try {
			meth = new PostMethod(url);
			meth.setRequestHeader("Connection", "close");
			meth.setRequestHeader("content-type", "application/json");
			meth.setRequestHeader("accept", "application/json");
			meth.setRequestEntity(new StringRequestEntity(req, "text/xml; charset=" + CH_ENCODING_UTF8, null));
			httpclient.executeMethod(meth);
			backXml = meth.getResponseBodyAsString();
		} catch (Exception e) {
			logger.error("调用接口失败:" + url, e);
			e.printStackTrace();
		} finally {
			if (meth != null) {
				meth.releaseConnection();
			}

			((SimpleHttpConnectionManager) httpclient.getHttpConnectionManager()).shutdown();
		}
		return backXml;
	}
	
	
	/**
	 * 功能：json post请求
	 * 
	 * @param req
	 * @param url
	 * @return
	 */
	public static String postHttpJson(String req, String url,Map<String, String> headMap) {
		logger.info("发送请求地址：" + url);
		HttpClient httpclient = new HttpClient();
		httpclient.getParams().setBooleanParameter("http.protocol.expect-continue", false);

		HttpConnectionManagerParams params = httpclient.getHttpConnectionManager().getParams();
		params.setConnectionTimeout(50000);
		params.setSoTimeout(210000);

		PostMethod meth = null;

		String backXml = null;
		try {
			meth = new PostMethod(url);
			meth.setRequestHeader("Connection", "close");
			if (headMap != null && !headMap.isEmpty()) {
				Iterator<String> iterator = headMap.keySet().iterator();
				while (iterator.hasNext()) {
					String key = iterator.next();
					String value = headMap.get(key);
					meth.setRequestHeader(key, value);
				}

			}
			meth.setRequestEntity(new StringRequestEntity(req, "text/xml; charset=" + CH_ENCODING_UTF8, null));
			httpclient.executeMethod(meth);
			backXml = new String(meth.getResponseBody(), "utf-8");
		} catch (Exception e) {
			logger.error("调用接口失败:" + url, e);
			e.printStackTrace();
		} finally {
			if (meth != null) {
				meth.releaseConnection();
			}
			((SimpleHttpConnectionManager) httpclient.getHttpConnectionManager()).shutdown();
		}
		return backXml;
	}
	
	/**
	 * 功能：json get请求
	 * 
	 * @param req
	 * @param url
	 * @return
	 */
	public static String getHttpJson(String req,String url,Map<String, String> headMap) {
		logger.info("发送请求地址：" + url);
		HttpClient httpclient = new HttpClient();
		httpclient.getParams().setBooleanParameter("http.protocol.expect-continue", false);

		HttpConnectionManagerParams params = httpclient.getHttpConnectionManager().getParams();
		params.setConnectionTimeout(50000);
		params.setSoTimeout(210000);

		GetMethod meth = null;

		String backXml = null;
		try {
			meth = new GetMethod(url);
			meth.setRequestHeader("Connection", "close");
			if (headMap != null && !headMap.isEmpty()) {
				Iterator<String> iterator = headMap.keySet().iterator();
				while (iterator.hasNext()) {
					String key = iterator.next();
					String value = headMap.get(key);
					meth.setRequestHeader(key, value);
				}

			}
			httpclient.executeMethod(meth);
			backXml = new String(meth.getResponseBody(), "utf-8");
		} catch (Exception e) {
			logger.error("调用接口失败:" + url, e);
			e.printStackTrace();
		} finally {
			if (meth != null) {
				meth.releaseConnection();
			}
			((SimpleHttpConnectionManager) httpclient.getHttpConnectionManager()).shutdown();
		}
		return backXml;
	}
	

}