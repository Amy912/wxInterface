package com.emagsoftware.wx.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.utils.DateUtils;
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
public class WxHongbaoController extends BaseController {

	protected Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private WxOrderService wxOrderService;
	/**
	 * 获取红包信息
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, value = "/getHongbao")
	public String getHongbao(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String orderNo = request.getParameter("orderNo");
			map.put("orderNo", orderNo);
			map.put("userid", request.getParameter("userid"));
			Map<String, Object> orderMap = wxOrderService.selectOrderByMap(map);
			Map<String ,Object> msg = new HashMap<String ,Object>();
			msg.put("order", orderMap);
			map.put("orderId", orderMap.get("id"));
			map.put("status", "1");
			List<Map<String ,Object>> list = wxOrderService.selectRedBagByOrderNo(map);
			msg.put("redBag", list);
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write(JsonUtils.getJSONString(msg));
			return null;
		} catch (Exception e) {
			logger.error("pay error.", e);
		}
		response.getWriter().write("");
		return null;
	}
	/**
	 * 领取红包
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, value = "/receiveHongbao")
	public String receiveHongbao(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String orderId = request.getParameter("orderId");
			String userid = request.getParameter("userid");
			
			Map<String, Object> updateMap = new HashMap<String, Object>();
			updateMap.put("orderId", orderId);
			updateMap.put("updateTime", WXPayUtil.getCurrentTime());
			updateMap.put("status", "1");
			updateMap.put("userId", userid);
			String openid = getOpenId(request);
			updateMap.put("openid", openid);
			Map<String ,Object> msg = wxOrderService.updateRedBagByOrderId(updateMap);
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write(JsonUtils.getJSONString(msg));
			return null;
		} catch (Exception e) {
			logger.error("pay error.", e);
		}
		response.getWriter().write("");
		return null;
	}
	/**
	 * 发出的红包列表
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, value = "/sendHbList")
	public String sendHbList(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String userid = request.getParameter("userid");
			String pageStart = StringUtils.isBlank(request.getParameter("pageStart"))?"0":request.getParameter("pageStart");
			Map<String, Object> msg = new HashMap<String, Object>();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userid", userid);
			map.put("orderType", "1");
			map.put("pageStart",  Integer.parseInt(pageStart));
			map.put("pageSize", 20);
			List<Map<String ,Object>> list = wxOrderService.selectRedBagByUserId(map);
			msg.put("hongBaoList", list);
			Map<String, Object> sendHbMap = wxOrderService.selectSendRedBagCount(map);
			msg.put("sendHbMap", sendHbMap);
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write(JsonUtils.getJSONString(msg));
			return null;
		} catch (Exception e) {
			logger.error("pay error.", e);
		}
		response.getWriter().write("");
		return null;
	}
	/**
	 * 发出的红包列表
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, value = "/receiveHbList")
	public String hongBaoList(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String userid = request.getParameter("userid");
			String pageStart = StringUtils.isBlank(request.getParameter("pageStart"))?"0":request.getParameter("pageStart");
			Map<String, Object> msg = new HashMap<String, Object>();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userid", userid);
			map.put("pageStart", Integer.parseInt(pageStart));
			map.put("pageSize", 20);
			List<Map<String ,Object>> rlist = wxOrderService.selectReceiveRedBagByUserId(map);
			msg.put("receiveHbList", rlist);
			Map<String, Object> receiveHbMap = wxOrderService.selectReceiveRedBagCount(map);
			msg.put("receiveHbMap", receiveHbMap);
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write(JsonUtils.getJSONString(msg));
			return null;
		} catch (Exception e) {
			logger.error("pay error.", e);
		}
		response.getWriter().write("");
		return null;
	}
}
