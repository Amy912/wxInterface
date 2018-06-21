package com.emagsoftware.wx.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.emagsoftware.frame.utils.JsonUtils;
import com.emagsoftware.wx.bean.WxBill;
import com.emagsoftware.wx.bean.WxOrder;
import com.emagsoftware.wx.bean.WxUser;
import com.emagsoftware.wx.service.WxOrderService;
import com.emagsoftware.wx.service.WxUserService;
import com.emagsoftware.wx.wxapi.WXPayConstants;
import com.emagsoftware.wx.wxapi.WXPayRequest;
import com.emagsoftware.wx.wxapi.WXPayUtil;
import com.emagsoftware.wx.wxapi.impl.WXPayConfigImpl;

@Controller
public class WxPayController extends BaseController {

	protected Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private WxUserService wxUserService;

	@Autowired
	private WxOrderService wxOrderService;
	/**
	 * 创建支付订单
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, value = "/pay")
	public String pay(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			System.out.println("sessionId:" + request.getSession().getId());
			String openid = getOpenId(request);
			String money = request.getParameter("money");
			String appid = (String) request.getSession().getAttribute("appid");
			String number = request.getParameter("number");
			String kouling = request.getParameter("kouling") == null||StringUtils.equals(request.getParameter("kouling"), "undefined") ? "" : java.net.URLDecoder.decode(request.getParameter("kouling"), "UTF-8");
			double payfee = Double.parseDouble(money);
			int hbNum = Integer.parseInt(number);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("openId",openid);
			map.put("appId",appid);
			WxUser user = wxUserService.selectUserInfoByOpenId(map);
			String ip = WXPayUtil.getRequestIp(request);
			String ret = prePay(openid,appid,user.getId(),payfee,hbNum,ip,kouling);
			response.getWriter().write(ret);
			return null;
		} catch (Exception e) {
			logger.error("pay error.", e);
		}
		response.getWriter().write("");
		return null;
	}
	/**
	 * 调微信支付接口
	 * @param openid
	 * @param appid
	 * @param userid
	 * @param money
	 * @param number
	 * @param clientIP
	 * @param kouling
	 * @return
	 * @throws Exception
	 */
	public String prePay(String openid,String appid,Integer userid,double money,int number,String clientIP,String kouling) throws Exception {
		String mchid = WXPayConstants.API_MCHID_MAP.get(appid);
		String key = WXPayConstants.SIGN_XML_KEY;
		String nonceStr = WXPayUtil.generateNonceStr();//随机字符串，长度要求在32位以内
		String body = "微信红包";
		//创建新订单
		WxOrder order = new WxOrder();
		order.setMoney(money);
		order.setOpenId(openid);
		order.setOrderType("1");
		order.setUserId(userid);
		order.setOrderTime(WXPayUtil.getCurrentTime());
		order.setOrderDesc(kouling);
		String orderNo = wxOrderService.addOrder(order,number);
		StringBuffer reqBody = new StringBuffer();
		reqBody.append("<xml>");
		reqBody.append("<appid>").append(appid).append("</appid>");
		reqBody.append("<attach>支付测试</attach>");
		reqBody.append("<body>").append(body).append("</body>");
		reqBody.append("<mch_id>").append(mchid).append("</mch_id>");
		reqBody.append("<nonce_str>").append(nonceStr).append("</nonce_str>");
		reqBody.append("<notify_url>http://wxpay.wxutil.com/pub_v2/pay/notify.v2.php</notify_url>");// 回调URL
		reqBody.append("<openid>" + openid + "</openid>");
		reqBody.append("<out_trade_no>" + orderNo + "</out_trade_no>");// 自己的订单号
		reqBody.append("<spbill_create_ip>"+clientIP+"</spbill_create_ip>");// 客户端IP
		reqBody.append("<total_fee>"+((int)(money))+"</total_fee>");// 费用，分*100
		reqBody.append("<trade_type>JSAPI</trade_type>");
		reqBody.append("</xml>");
		String req = WXPayUtil.generateSignedXml(WXPayUtil.xmlToMap(reqBody.toString()), key);
		System.out.println(req);
		WXPayConfigImpl config = WXPayConfigImpl.getInstance();
		config.setAppID(appid);
		config.setKey(key);
		config.setMchID(mchid);
		WXPayRequest wxPayRequest = new WXPayRequest(config);
		// https://api.mch.weixin.qq.com/pay/unifiedorder
		String resp = wxPayRequest.requestWithoutCert("/pay/unifiedorder", "oV4n54sLMaY7SYEKmGrY36gh5Rng", req, true);
		System.out.println(resp);
		Map<String, String> respMap = WXPayUtil.xmlToMap(resp);
		System.out.println(respMap.get("return_code"));
		Map<String, String> retmap = new HashMap<String, String>();
		if (StringUtils.equals(respMap.get("return_code"), "SUCCESS")) {
			if (StringUtils.equals(respMap.get("result_code"), "SUCCESS")) {
				System.out.println(respMap.get("nonce_str"));
				System.out.println(respMap.get("prepay_id"));
				long pay_timeStamp = new Date().getTime();
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
				// pay_timeStamp, pay_nonceStr, prepay_id, pay_paySign
				retmap.put("return_code", "SUCCESS");
				retmap.put("pay_timeStamp", String.valueOf(pay_timeStamp));
				retmap.put("pay_nonceStr", respMap.get("nonce_str"));
				retmap.put("prepay_id", respMap.get("prepay_id"));
				retmap.put("pay_paySign", sbmd5);
				retmap.put("orderNo", orderNo);
				
			} else {
				retmap.put("return_code", respMap.get("result_code"));
				retmap.put("err_code_des", respMap.get("err_code_des"));
			}
		} else {
			retmap.put("return_code", respMap.get("return_code"));
			retmap.put("err_code_des", respMap.get("return_msg"));
		}
		return JsonUtils.getJSONString(retmap);
	}
	/**
	 * 保存订单信息
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, value = "/saveOrderBill")
	public String saveOrderBill(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			System.out.println("-----sessionId:" + request.getSession().getId());
			String openid = (String) request.getSession().getAttribute("openid");
			String money = request.getParameter("money");
			String orderNo = request.getParameter("orderNo");
			String status = request.getParameter("status");
			WxOrder order = wxOrderService.selectOrderByOrderNo(orderNo);
			if(StringUtils.equals(status, "1")){//支付成功，生成账单
				String appid = (String) request.getSession().getAttribute("appid");
				Map<String, Object> selmap = new HashMap<String, Object>();
				selmap.put("openId",openid);
				selmap.put("appId",appid);
				WxUser user = wxUserService.selectUserInfoByOpenId(selmap);
				WxBill bill = new WxBill();
				bill.setBillTime(WXPayUtil.getCurrentTime());
				bill.setMoney(Double.parseDouble(money));
				bill.setLeftMoney(0);
				bill.setOpenId(openid);
				bill.setOrderId(order.getId());
				bill.setUserId(user.getId());
				bill.setDesc("订单支付");
				wxOrderService.insertBill(bill);
			}else{//支付失败，取消订单
				wxOrderService.deleteOrder(order.getId()+"");
			}
			
			
			
			response.getWriter().write("");
			return null;
		} catch (Exception e) {
			logger.error("pay error.", e);
		}
		response.getWriter().write("");
		return null;
	}
	/**
	 * 提现
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, value = "/transfers")
	public String transfers(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			System.out.println("sessionId:" + request.getSession().getId());
			String openid = getOpenId(request);
			String money = request.getParameter("money");
			String userid = request.getParameter("userid");
			String appid = (String) request.getSession().getAttribute("appid");
			double payfee = Double.parseDouble(money);
			String ip = WXPayUtil.getRequestIp(request);
			String ret = transfersMoney(openid,appid,Integer.parseInt(userid),payfee,ip);
			response.getWriter().write(ret);
			return null;
		} catch (Exception e) {
			logger.error("pay error.", e);
		}
		response.getWriter().write("");
		return null;
	}
	/**
	 * 调微信提现接口
	 * @param openid
	 * @param appid
	 * @param userid
	 * @param money
	 * @param clientIP
	 * @return
	 * @throws Exception
	 */
	public String transfersMoney(String openid,String appid,Integer userid,double money,String clientIP) throws Exception {
		String mchid = WXPayConstants.API_MCHID_MAP.get(appid);//微信分配的商户号
		String key = WXPayConstants.SIGN_XML_KEY;// API秘钥
		String nonceStr = WXPayUtil.generateNonceStr();//随机字符串，长度要求在32位以内
		String desc = "提现";

		//创建新订单 获取订单号
		WxOrder order = new WxOrder();
		order.setMoney(money);
		order.setOpenId(openid);
		order.setOrderType("2");
		order.setUserId(userid);
		order.setOrderTime(WXPayUtil.getCurrentTime());
		order.setOrderDesc("提现");
		order = wxOrderService.addWidthDrawOrder(order);
		
		StringBuffer reqBody = new StringBuffer();
		reqBody.append("<xml>");
		reqBody.append("<mch_appid>").append(appid).append("</mch_appid>");//小程序appid
		reqBody.append("<mchid>").append(mchid).append("</mchid>");//商户号
		reqBody.append("<nonce_str>").append(nonceStr).append("</nonce_str>");//随机字符串
		//reqBody.append("<sign>").append(nonceStr).append("</sign>");
		reqBody.append("<partner_trade_no>").append(order.getOrderNo()).append("</partner_trade_no>");//订单号
		reqBody.append("<openid>").append(openid).append("</openid>");//用户openid
		reqBody.append("<check_name>").append("NO_CHECK").append("</check_name>");
		reqBody.append("<amount>").append(((int)(money*100))).append("</amount>");//金额
		reqBody.append("<desc>").append(desc).append("</desc>");//描述
		reqBody.append("<spbill_create_ip>").append(clientIP).append("</spbill_create_ip>");//客户端ip
		reqBody.append("</xml>");
		//生成带有 sign 的 XML 格式字符串
		String req = WXPayUtil.generateSignedXml(WXPayUtil.xmlToMap(reqBody.toString()), key);
		System.out.println(req);
		//
		WXPayConfigImpl config = WXPayConfigImpl.getInstance();
		config.setAppID(appid);
		config.setKey(key);
		config.setMchID(mchid);
		WXPayRequest wxPayRequest = new WXPayRequest(config);
		//发起提现请求
		String result = wxPayRequest.requestWithCert("/mmpaymkttransfers/promotion/transfers", "oV4n54sLMaY7SYEKmGrY36gh5Rng", req, 10000, 10000,true);
		System.out.println(result);
		Map<String, String> respMap = WXPayUtil.xmlToMap(result);
		Map<String, String> retmap = new HashMap<String, String>();
		System.out.println(respMap.get("return_code"));
		if (StringUtils.equals(respMap.get("return_code"), "SUCCESS")) {
			if (StringUtils.equals(respMap.get("result_code"), "SUCCESS")) {
				retmap.put("return_code", respMap.get("result_code"));
				retmap.put("orderNo", order.getOrderNo());
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id",order.getUserId());
				WxUser user = wxUserService.selectUserInfoByUserId(map);
				WxBill bill = new WxBill();
				bill.setBillTime(WXPayUtil.getCurrentTime());
				bill.setMoney(order.getMoney());
				bill.setLeftMoney(BigDecimal.valueOf(user.getMoney()).subtract(BigDecimal.valueOf(order.getMoney())).doubleValue());
				bill.setOpenId(order.getOpenId());
				bill.setOrderId(order.getId());
				bill.setUserId(user.getId());
				bill.setDesc("提取现金");
				wxOrderService.saveWidthDrawBill(bill);
				retmap.put("leftMoney", bill.getLeftMoney()+"");
			} else {
				retmap.put("return_code", respMap.get("result_code"));
				retmap.put("err_code_des", respMap.get("err_code_des"));
			}
		} else {
			retmap.put("return_code", respMap.get("return_code"));
			retmap.put("err_code_des", respMap.get("return_msg"));
		}
		return JsonUtils.getJSONString(retmap);
	}
	public static void main(String[] args) {
		BigDecimal a = BigDecimal.valueOf(7.63);
		BigDecimal b = BigDecimal.valueOf(1);
		System.out.println(a.subtract(b).doubleValue());
		System.out.println(WXPayUtil.getCurrentTime());
	}
}
