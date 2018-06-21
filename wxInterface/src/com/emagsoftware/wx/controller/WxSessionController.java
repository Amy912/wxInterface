package com.emagsoftware.wx.controller;

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

import com.emagsoftware.frame.utils.HttpUtils;
import com.emagsoftware.frame.utils.JsonUtils;
import com.emagsoftware.wx.bean.WxUser;
import com.emagsoftware.wx.service.WxUserService;
import com.emagsoftware.wx.wxapi.HttpClientConnectionManager;
import com.emagsoftware.wx.wxapi.WXPayConstants;
import com.emagsoftware.wx.wxapi.WXPayUtil;

import net.sf.json.JSONObject;

@Controller
public class WxSessionController {

	@Autowired
	private WxUserService wxUserService;

	protected Logger logger = Logger.getLogger(this.getClass());
	
	
	//@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, value = "/getSessionId")
	@SuppressWarnings("unchecked")
	@RequestMapping("/getSessionId")
	public String getSessionId(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String code = request.getParameter("code");
			String appid = request.getParameter("appid");
			String nickName = request.getParameter("nickName") == null||StringUtils.equals(request.getParameter("nickName"), "undefined") ? "" : java.net.URLDecoder.decode(request.getParameter("nickName"), "UTF-8");
			String userIcon = request.getParameter("userIcon") == null||StringUtils.equals(request.getParameter("userIcon"), "undefined") ? "" : request.getParameter("userIcon");
			
			System.out.println("code:" + code);
			System.out.println("sessionId:" + request.getSession().getId());
			String ret = jscode2session(code, appid);
			Map<String, String> map = JsonUtils.getMapFromJson(ret);
			//校验成功
			if(map.containsKey("openid")) {
				System.out.println(map.get("session_key"));
				System.out.println(map.get("openid"));
				System.out.println(map.get("unionid"));
				// 保存session信息
				request.getSession().setAttribute("appid", appid);
				request.getSession().setAttribute("openid", map.get("openid"));
				request.getSession().setAttribute("unionid", map.get("unionid"));
				request.getSession().setAttribute("session_key", map.get("session_key"));
				// 保存用户记录
				// TODO:入库保存
				WxUser user = new WxUser();
				user.setAppId(appid);
				user.setOpenId(map.get("openid"));
				user.setUnionId(map.get("unionid"));
				user.setCreateTime(WXPayUtil.getCurrentTime());
				user.setNickName(nickName);
				user.setUserIcon(userIcon);
				
				Map<String, Object> resMap = wxUserService.saveUser(user);
				request.getSession().setAttribute("userid", resMap.get("userid"));
				resMap.put("sessionid", request.getSession().getId());
				response.getWriter().write(JsonUtils.getJSONString(resMap));
			}
		} catch (Exception e) {
			logger.error("getSessionId error.", e);
		}
		return null;
	}

	public String jscode2session(String code, String appid) throws Exception {
		// String appid = WXPayConstants.APPID_HONHBAO;
		String secret = WXPayConstants.API_SECRET_MAP.get(appid);// 小程序的 app secret
		String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appid + "&secret=" + secret + "&js_code=" + code + "&grant_type=authorization_code";
		HttpUtils httpUtils = new HttpUtils();
		String ret = httpUtils.sendGet(url);
		return ret;
	}


	@SuppressWarnings("unchecked")
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, value = "/getBarcode")
	public String getBarcode(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String appid = request.getParameter("appid");
			String ret = getToken(appid);
			Map<String, String> map = JsonUtils.getMapFromJson(ret);
			String path = request.getParameter("path");
			String orderNo = request.getParameter("orderNo");
			Map<String, Object> qrmap = new HashMap<String, Object>();  
			qrmap.put("path", path);  
			qrmap.put("width", 430);  
			qrmap.put("scene", orderNo);
			qrmap.put("auto_color", false);
            Map<String,Object> line_color = new HashMap<String,Object>();
            line_color.put("r", 0);
            line_color.put("g", 0);
            line_color.put("b", 0);
            qrmap.put("line_color", line_color);
            JSONObject json = JSONObject.fromObject(qrmap);  
          String qrPath =  HttpClientConnectionManager.httpPostWithJSON("https://api.weixin.qq.com/wxa/getwxacode?access_token="+ map.get("access_token"), 
            		json.toString(),orderNo);  
          System.out.println(qrPath);
			return null;
		} catch (Exception e) {
			logger.error("getSessionId error.", e);
		}
		response.getWriter().write("");
		return null;
	}
	
	public String getToken(String appid) throws Exception {
		// String appid = WXPayConstants.APPID_HONHBAO;
		String secret = WXPayConstants.API_SECRET_MAP.get(appid);// 小程序的 app secret
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid + "&secret=" + secret;
		HttpUtils httpUtils = new HttpUtils();
		String ret = httpUtils.sendGet(url);
		return ret;
	}
}
