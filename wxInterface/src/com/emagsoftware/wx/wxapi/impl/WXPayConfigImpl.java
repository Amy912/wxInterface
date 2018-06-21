package com.emagsoftware.wx.wxapi.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import com.emagsoftware.wx.wxapi.IWXPayDomain;
import com.emagsoftware.wx.wxapi.WXPayConfig;

public class WXPayConfigImpl extends WXPayConfig {

	private byte[] certData;

	private static WXPayConfigImpl INSTANCE;
	
	//需配置成开发者本地路径
	private final static String myCertPath = "resources/1488420152/apiclient_cert.p12";

	public String appID;

	public String mchID;

	public String key;

	private WXPayConfigImpl() throws Exception {
		String certPath = Thread.currentThread().getContextClassLoader().getResource("").getPath() + myCertPath;
		File file = new File(certPath);
		InputStream certStream = new FileInputStream(file);
		this.certData = new byte[(int) file.length()];
		certStream.read(this.certData);
		certStream.close();
	}

	public static WXPayConfigImpl getInstance() throws Exception {
		if (INSTANCE == null) {
			synchronized (WXPayConfigImpl.class) {
				if (INSTANCE == null) {
					INSTANCE = new WXPayConfigImpl();
				}
			}
		}
		return INSTANCE;
	}

	public String getAppID() {
		return this.appID;
	}

	public void setAppID(String appID) {
		this.appID = appID;
	}

	public String getMchID() {
		return this.mchID;
	}

	public void setMchID(String mchID) {
		this.mchID = mchID;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public InputStream getCertStream() {
		ByteArrayInputStream certBis;
		certBis = new ByteArrayInputStream(this.certData);
		return certBis;
	}

	public int getHttpConnectTimeoutMs() {
		return 2000;
	}

	public int getHttpReadTimeoutMs() {
		return 10000;
	}

	protected IWXPayDomain getWXPayDomain() {
		return WXPayDomainSimpleImpl.instance();
	}

	public String getPrimaryDomain() {
		return "api.mch.weixin.qq.com";
	}

	public String getAlternateDomain() {
		return "api2.mch.weixin.qq.com";
	}

	@Override
	public int getReportWorkerNum() {
		return 1;
	}

	@Override
	public int getReportBatchSize() {
		return 2;
	}
	
	public static void main(String[] args) {
		String certPath = Thread.currentThread().getContextClassLoader().getResource("").getPath() + myCertPath;
		File file = new File(certPath);
		System.out.println(file.length()+"==================");
	}
}
