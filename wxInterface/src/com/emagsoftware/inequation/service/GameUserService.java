package com.emagsoftware.inequation.service;

import java.util.List;
import java.util.Map;

import com.emagsoftware.inequation.bean.GameUser;


public interface GameUserService {
	public Integer insertLoginUserInfo(Map<String, Object> params) throws Exception;
	
	public GameUser getUserInfoByMap(Map<String, Object> params);
	
	public Integer updateLoginUserInfo(Map<String, Object> params);
	
	public Map<String, Object> getUserGameInfoByUserId(String userId);
	
	public Integer insertLoginUserComplaint(Map<String, Object> params) throws Exception;
	
	public List<Map<String, Object>> getUserShareByMap(Map<String, Object> params);
	
	public Integer insertUserShareInfo(Map<String, Object> params);
	
	public Integer insertUserLotteryRecord(Map<String, Object> params) throws Exception;
	
	public Integer updateLotteryRecordStatus(Map<String, Object> params);
}
