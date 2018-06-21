package com.emagsoftware.wx.controller;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

	protected String getOpenId(HttpServletRequest request) {
		return (String) request.getSession().getAttribute("openid");
	}
}
