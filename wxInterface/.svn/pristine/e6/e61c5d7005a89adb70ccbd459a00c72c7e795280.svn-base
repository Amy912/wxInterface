package com.emagsoftware.inequation.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emagsoftware.inequation.bean.GameUser;
import com.emagsoftware.inequation.dao.GameUserDao;
import com.emagsoftware.inequation.service.GameUserService;

@Service
public class GameUserServiceImpl implements GameUserService{
	@Autowired
	private GameUserDao userDao;

	@Override
	public Integer insertLoginUserInfo(Map<String, Object> params) throws Exception {
		return this.userDao.insertLoginUser(params);
	}

	@Override
	public GameUser getUserInfoByMap(Map<String, Object> params) {
		List<GameUser> list = this.userDao.getUserInfoByMap(params);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
		
	}

	@Override
	public Integer updateLoginUserInfo(Map<String, Object> params) {
		int count = 0;
		if(params.get("sysuserid") != null && StringUtils.isNotBlank(params.get("sysuserid").toString())) {
			count += this.userDao.updateWeiXinUserInfo(params);
		}
		if(params.get("updateGameUser") != null && StringUtils.isNotBlank(params.get("updateGameUser").toString())) {
			count += this.userDao.updateLoginUserInfo(params);
		}
		return count;
	}

	@Override
	public Map<String, Object> getUserGameInfoByUserId(String userId) {
		return this.userDao.getUserGameInfoByUserId(userId);
	}

	@Override
	public Integer insertLoginUserComplaint(Map<String, Object> params) throws Exception {
		return this.userDao.insertLoginUserComplaint(params);
	}

	@Override
	public List<Map<String, Object>> getUserShareByMap(Map<String, Object> params) {
		return this.userDao.getUserShareByMap(params);
	}

	@Override
	public Integer insertUserShareInfo(Map<String, Object> params) {
		return this.userDao.insertUserShareInfo(params);
	}

	@Override
	public Integer insertUserLotteryRecord(Map<String, Object> params) throws Exception {
		int count = this.userDao.insertUserLotteryRecord(params);
		if(count > 0) {
			count +=  this.userDao.updateLotteryRecordStatus(params);
		}
		return count;
	}

	@Override
	public Integer updateLotteryRecordStatus(Map<String, Object> params) {
		return this.userDao.updateLotteryRecordStatus(params);
	}

}
