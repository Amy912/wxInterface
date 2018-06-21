package com.emagsoftware.inequation.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emagsoftware.inequation.dao.GameInfoDao;
import com.emagsoftware.inequation.service.GameInfoService;

@Service
public class GameInfoServiceImpl implements GameInfoService{
	@Autowired
	private GameInfoDao gameInfoDao;

	@Override
	public Map<String, Object> getGameTimesInfoByMap(Map<String, Object> params) {
		return this.gameInfoDao.getGameTimesInfoByMap(params);
	}

	@Override
	public Integer updateGameTimes(Map<String, Object> params) {
		return this.gameInfoDao.updateGameTimes(params);
	}

	@Override
	public List<Map<String, String>> getQuestions(Map<String, Object> params) {
		return this.gameInfoDao.getQuestions(params);
	}

	@Override
	public List<Map<String, Object>> getGameHonors(Map<String, Object> params) {
		return this.gameInfoDao.getGameHonors(params);
	}

	@Override
	public List<Map<String, Object>> getGameBackbones() {
		return this.gameInfoDao.getGameBackbones();
	}

	@Override
	public Integer getAllUserTryTimes() {
		return this.gameInfoDao.getAllUserTryTimes();
	}

	@Override
	public List<Map<String, Object>> getUserOwnDolls(Map<String, Object> params) {
		return this.gameInfoDao.getUserOwnDolls(params);
	}

	@Override
	public Integer getUserOwnAllDolls(Map<String, Object> params) {
		return this.gameInfoDao.getUserOwnAllDolls(params);
	}

	@Override
	public List<Map<String, Object>> getUserLotteryListByMap(Map<String, Object> params) {
		return this.gameInfoDao.getUserLotteryListByMap(params);
	}

	@Override
	public List<Map<String, Object>> getUserLotteryListWithTotal(Map<String, Object> params) {
		return this.gameInfoDao.getUserLotteryListWithTotal(params);
	}

	@Override
	public List<Map<String, Object>> getUserLotteryVoucherList(Map<String, Object> params) {
		return this.gameInfoDao.getUserLotteryVoucherList(params);
	}

	@Override
	public List<Map<String, Object>> getGameLotteryTypes(Map<String, Object> params) {
		return this.gameInfoDao.getGameLotteryTypes(params);
	}


}
