package com.emagsoftware.wx.service;

import java.util.Map;

import com.emagsoftware.wx.bean.WxOrder;
import com.emagsoftware.wx.bean.WxUser;


public interface WxUserService {
	/**
	 * 保存用户信息
	 * @param user
	 * @return
	 */
	public Map<String, Object> saveUser(WxUser user);
	/**
	 * 通过openid appid查询用户信息
	 * @param map
	 * @return
	 */
	public WxUser selectUserInfoByOpenId(Map<String, Object> map);
	/**
	 * 通过userid查询用户信息
	 * @param map
	 * @return
	 */
	public WxUser selectUserInfoByUserId(Map<String, Object> map);
}
