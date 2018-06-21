package com.emagsoftware.inequation.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.emagsoftware.inequation.bean.GameUser;
import com.emagsoftware.inequation.dao.GameUserDao;
import com.emagsoftware.frame.dao.BaseDao;

@Repository("gameUserDao")
public class GameUserDaoImpl extends BaseDao implements GameUserDao{

	@Override
	public Integer insertLoginUser(Map<String, Object> params) throws Exception {
		return (Integer) this.getSqlMapClientTemplate().insert("t_sys_gameuser.insertLoginUser", params);
	}

	@Override
	public List<GameUser> getUserInfoByMap(Map<String, Object> params) {
		return this.getSqlMapClientTemplate().queryForList("t_sys_gameuser.searchLoginUser",params);
	}

	@Override
	public Integer updateLoginUserInfo(Map<String, Object> params) {
		return (Integer) this.getSqlMapClientTemplate().update("t_sys_gameuser.updateUserInfo", params);
	}

	@Override
	public Map<String, Object> getUserGameInfoByUserId(String userId) {
		return (Map<String, Object>) this.getSqlMapClientTemplate().queryForObject("t_sys_gameuser.searchUserGameInfo",userId);
	}

	@Override
	public Integer insertLoginUserComplaint(Map<String, Object> params) throws Exception {
		return (Integer) this.getSqlMapClientTemplate().insert("t_sys_gameuser.insertLoginUserComplaint", params);
	}

	@Override
	public List<Map<String, Object>> getUserShareByMap(Map<String, Object> params) {
		return this.getSqlMapClientTemplate().queryForList("t_sys_gameuser.getUserShareByMap", params);
	}

	@Override
	public Integer insertUserShareInfo(Map<String, Object> params) {
		return (Integer) this.getSqlMapClientTemplate().insert("t_sys_gameuser.insertUserShareInfo", params);
	}

	@Override
	public Integer insertUserLotteryRecord(Map<String, Object> params) throws Exception {
		return (Integer) this.getSqlMapClientTemplate().insert("t_sys_gameuser.insertUserLotteryRecord", params);
	}

	@Override
	public Integer updateLotteryRecordStatus(Map<String, Object> params) { 
		return (Integer) this.getSqlMapClientTemplate().update("t_sys_gameuser.updateLotteryRecordStatus", params);
	}

	@Override
	public Integer updateWeiXinUserInfo(Map<String, Object> params) {
		return (Integer) this.getSqlMapClientTemplate().update("t_sys_gameuser.updateWeiXinUserInfo", params);
	}


}
