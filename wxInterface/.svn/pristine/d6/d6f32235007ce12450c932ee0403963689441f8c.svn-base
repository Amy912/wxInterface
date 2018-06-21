package com.emagsoftware.wx.wxapi.impl;

import java.util.HashMap;
import java.util.Map;

import com.emagsoftware.wx.wxapi.WXPayConstants;
import com.emagsoftware.wx.wxapi.WXPayRequest;
import com.emagsoftware.wx.wxapi.WXPayUtil;

/**
 * Created by ariesun on 2017/4/20.
 */
public class TestWXPayRequest {

	public static void main(String[] args) throws Exception {
		String mchid = WXPayConstants.API_MCHID_MAP.get("wxd1a56d49be0fd7e8");
		String key = WXPayConstants.SIGN_XML_KEY;
		String nonceStr = WXPayUtil.generateNonceStr();//随机字符串，长度要求在32位以内
		String result;
		WXPayConfigImpl config = WXPayConfigImpl.getInstance();
		config.setAppID("wxd1a56d49be0fd7e8");
		config.setKey(key);
		config.setMchID(mchid);
		WXPayRequest wxPayRequest = new WXPayRequest(config);

		// String data =
		// "</trade_type><mch_id>11473623</mch_id><sign_type>HMAC-SHA256</sign_type><nonce_str>b1089cb0231011e7b7e1484520356fdc</nonce_str><detail
		// /><fee_type>CNY</fee_type><device_info>WEB</device_info><out_trade_no>20161909105959000000111108</out_trade_no><total_fee>1</total_fee><appid>wxab8acb865bb1637e</appid><notify_url>http://test.letiantian.com/wxpay/notify</notify_url><sign>78F24E555374B988277D18633BF2D4CA23A6EAF06FEE0CF1E50EA4EADEEC41A3</sign><spbill_create_ip>123.12.12.123</spbill_create_ip></xml>";
		// result = wxPayRequest.rawRequestWithoutCert("/pay/unifiedorder",
		// data, 5000, 10000);
		// System.out.println(result);

		// String data2 =
		// "<xml><sign_type>HMAC-SHA256</sign_type><nonce_str>7261bbcf236b11e7a2bd484520356fdc</nonce_str><sign>6010521E041EA6F378D25B85CCD5DA6871E34E9BBBC441E7BC4744C3CBF27AEB</sign><op_user_id>100</op_user_id><mch_id>11473623</mch_id><out_trade_no>20161909105959000000111108</out_trade_no><total_fee>1</total_fee><appid>wxab8acb865bb1637e</appid><out_refund_no>20161909105959000000111108</out_refund_no><refund_fee>1</refund_fee></xml>";
		// result = wxPayRequest.requestWithCert("/secapi/pay/refund",
		// "1213uuid", data2, 10000, 10000, true);
		// System.out.println(result);
		
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("mch_appid", "wxd1a56d49be0fd7e8");
		map.put("mchid", mchid);
		map.put("nonce_str", nonceStr);
		map.put("sign", "");
		map.put("partner_trade_no", "TEST00003");
		map.put("openid", "oV4n54iZHfFHS_hoX9a6_OwHDhcs");
		// NO_CHECK：不校验真实姓名
		// FORCE_CHECK：强校验真实姓名
		map.put("check_name", "NO_CHECK");
		map.put("amount", "100");
		map.put("desc", "123");
		map.put("spbill_create_ip", "14.23.150.211");
		String xml = WXPayUtil.generateSignedXml(map, key);
		result = wxPayRequest.requestWithCert("/mmpaymkttransfers/promotion/transfers", "oV4n54sLMaY7SYEKmGrY36gh5Rng", xml, 10000, 10000,
				true);
		System.out.println(result);

	}

}
