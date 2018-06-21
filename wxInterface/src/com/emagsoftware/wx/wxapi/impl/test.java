package com.emagsoftware.wx.wxapi.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Map;

import com.emagsoftware.wx.wxapi.WXPayUtil;

public class test {

	public static void main(String[] args) throws Exception {

		// HostnameVerifier hnv = new HostnameVerifier() {
		// public boolean verify(String hostname, SSLSession session) {
		// // Always return true，接受任意域名服务器
		// return true;
		// }
		// };
		// HttpsURLConnection.setDefaultHostnameVerifier(hnv);
		String appid = "wxd1a56d49be0fd7e8";
		String mchid = "1488420152";
		String key = "emagnsr20170906emagnsr20170906aa";
		String nonceStr = "ibuaiVcKdpRxkhJA";
		String body = "微信红包";
		String stringA = "appid=" + appid + "&body=" + body + "&device_info=1000&mch_id=" + mchid + "&nonce_str="
				+ nonceStr;
		String stringSignTemp = stringA + "&key=" + key; // 注：key为商户平台设置的密钥key
		String sign = WXPayUtil.MD5(stringSignTemp).toUpperCase();// ="9A0A8659F005D6984697E2CA0A9CF3B7"
		// sign = hash_hmac("sha256", stringSignTemp, key).toUpperCase();//
		// ="6A9AE1657590FD6257D693A078E1C3E4BB6BA4DC30B23E0EE2496E54170DACD6"
		String UTF8 = "UTF-8";
		StringBuffer reqBody = new StringBuffer();
		reqBody.append("<xml>");
		reqBody.append("<appid>").append(appid).append("</appid>");
		reqBody.append("<attach>支付测试</attach>");
		reqBody.append("<body>").append(body).append("</body>");
		reqBody.append("<mch_id>").append(mchid).append("</mch_id>");
		reqBody.append("<nonce_str>").append(nonceStr).append("</nonce_str>");
		reqBody.append("<notify_url>http://wxpay.wxutil.com/pub_v2/pay/notify.v2.php</notify_url>");//回调URL
		reqBody.append("<openid>oV4n54sLMaY7SYEKmGrY36gh5Rng</openid>");
		reqBody.append("<out_trade_no>1415659993</out_trade_no>");//自己的订单号
		reqBody.append("<spbill_create_ip>14.23.150.211</spbill_create_ip>");//客户端IP
		reqBody.append("<total_fee>2</total_fee>");//费用，分
		reqBody.append("<trade_type>JSAPI</trade_type>");
		reqBody.append("<sign>").append(sign).append("</sign>");
		reqBody.append("</xml>");
		String req = WXPayUtil.generateSignedXml(WXPayUtil.xmlToMap(reqBody.toString()), key);
		System.out.println(req);
		// String reqBody =
		// "<xml><body>测试商家-商品类目</body><trade_type>NATIVE</trade_type><mch_id>11473623</mch_id><sign_type>HMAC-SHA256</sign_type><nonce_str>b1089cb0231011e7b7e1484520356fdc</nonce_str><detail
		// /><fee_type>CNY</fee_type><device_info>WEB</device_info><out_trade_no>20161909105959000000111108</out_trade_no><total_fee>1</total_fee><appid>wxab8acb865bb1637e</appid><notify_url>http://test.letiantian.com/wxpay/notify</notify_url><sign>78F24E555374B988277D18633BF2D4CA23A6EAF06FEE0CF1E50EA4EADEEC41A3</sign><spbill_create_ip>123.12.12.123</spbill_create_ip></xml>";
		URL httpUrl = new URL("https://api.mch.weixin.qq.com/pay/unifiedorder");
		HttpURLConnection httpURLConnection = (HttpURLConnection) httpUrl.openConnection();
		httpURLConnection.setRequestProperty("Host", "api.mch.weixin.qq.com");
		httpURLConnection.setDoOutput(true);
		httpURLConnection.setRequestMethod("POST");
		httpURLConnection.setConnectTimeout(10 * 1000);
		httpURLConnection.setReadTimeout(10 * 1000);
		httpURLConnection.connect();
		OutputStream outputStream = httpURLConnection.getOutputStream();
		outputStream.write(req.getBytes(UTF8));


		// 获取内容
		InputStream inputStream = httpURLConnection.getInputStream();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, UTF8));
		final StringBuffer stringBuffer = new StringBuffer();
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			stringBuffer.append(line);
		}
		String resp = stringBuffer.toString();
		if (stringBuffer != null) {
			try {
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (inputStream != null) {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (outputStream != null) {
			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		System.out.println(resp);
		
		Map<String, String> respMap = WXPayUtil.xmlToMap(resp);
		System.out.println(respMap.get("nonce_str"));
		System.out.println(respMap.get("prepay_id"));
		StringBuffer sb = new StringBuffer();
		sb.append("appId=").append(appid);
		sb.append("&nonceStr=").append(respMap.get("nonce_str"));
		sb.append("&package=prepay_id=").append(respMap.get("prepay_id"));
		sb.append("&signType=MD5");
		sb.append("&timeStamp=").append(new Date().getTime());
		sb.append("&key=").append(key);
		System.out.println(sb.toString());
		String sbmd5 = WXPayUtil.MD5(sb.toString());
		System.out.println(sbmd5);

	}

}
