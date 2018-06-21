package com.emagsoftware.wx.dao;

import java.util.Map;

import com.emagsoftware.wx.bean.WxOrder;
import com.emagsoftware.wx.bean.WxUser;

public interface WxUserDao {

	public Integer insertUser(WxUser user);

	public WxUser selectUserInfoByOpenId(Map<String, Object> map);

	public int updateUserInfoById(Map<String, Object> map);
	/**
	 * 通过userid查询用户信息
	 * @param map
	 * @return
	 */
	public WxUser selectUserInfoByUserId(Map<String, Object> map);
}
