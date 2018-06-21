package com.emagsoftware.wx.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.emagsoftware.frame.dao.BaseDao;
import com.emagsoftware.wx.bean.WxOrder;
import com.emagsoftware.wx.bean.WxUser;
import com.emagsoftware.wx.dao.WxOrderDao;
import com.emagsoftware.wx.dao.WxUserDao;

@SuppressWarnings("deprecation")
@Repository("wxUserDao")
public class WxUserDaoImpl extends BaseDao implements WxUserDao {

	@Override
	public Integer insertUser(WxUser user) {
		return (Integer) this.getSqlMapClientTemplate().insert("t_sys_user.insertUser",user);
	}

	@Override
	public WxUser selectUserInfoByOpenId(Map<String, Object> map) {
		return (WxUser) this.getSqlMapClientTemplate().queryForObject("t_sys_user.selectUserInfoByOpenId", map);
	}

	@Override
	public int updateUserInfoById(Map<String, Object> map) {
		return this.getSqlMapClientTemplate().update("t_sys_user.updateUserInfoById", map);
	}

	@Override
	public WxUser selectUserInfoByUserId(Map<String, Object> map) {
		return (WxUser) this.getSqlMapClientTemplate().queryForObject("t_sys_user.selectUserInfoByUserId", map);
	}

}
