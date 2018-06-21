package com.emagsoftware.inequation.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.emagsoftware.inequation.dao.GameInfoDao;
import com.emagsoftware.frame.dao.BaseDao;

@Repository("gameInfoDao")
public class GameInfoDaoImpl extends BaseDao implements GameInfoDao{

	@Override
	public Map<String, Object> getGameTimesInfoByMap(Map<String, Object> params) {
		return  (Map<String, Object>) this.getSqlMapClientTemplate().queryForObject("t_sys_game.getGameTimesInfo",params);
	}

	@Override
	public Integer updateGameTimes(Map<String, Object> params) {
		return (Integer) this.getSqlMapClientTemplate().update("t_sys_game.updateGameTimes",params);
	}

	@Override
	public List<Map<String, String>> getQuestions(Map<String, Object> params) {
		return (List<Map<String, String>>) this.getSqlMapClientTemplate().queryForList("t_sys_game.getQuestions", params);
	}

	@Override
	public List<Map<String, Object>> getGameHonors(Map<String, Object> params) {
		return (List<Map<String, Object>>) this.getSqlMapClientTemplate().queryForList("t_sys_game.getGameHonors", params);
	}

	@Override
	public List<Map<String, Object>> getGameBackbones() {
		return (List<Map<String, Object>>) this.getSqlMapClientTemplate().queryForList("t_sys_game.getGameBackbones");
	}

	@Override
	public Integer getAllUserTryTimes() {
		return (Integer) this.getSqlMapClientTemplate().queryForObject("t_sys_game.getAllUserTryTimes");
	}

	@Override
	public List<Map<String, Object>> getUserOwnDolls(Map<String, Object> params) {
		return (List<Map<String, Object>>) this.getSqlMapClientTemplate().queryForList("t_sys_game.getUserLotteryByType",params);
	}

	@Override
	public Integer getUserOwnAllDolls(Map<String, Object> params) {
		return (Integer)  this.getSqlMapClientTemplate().queryForObject("t_sys_game.getUserLotteryByType",params);
	}

	@Override
	public List<Map<String, Object>> getUserLotteryListByMap(Map<String, Object> params) {
		return (List<Map<String, Object>>) this.getSqlMapClientTemplate().queryForList("t_sys_game.getUserLotteryListByMap",params);
	}

	@Override
	public List<Map<String, Object>> getUserLotteryListWithTotal(Map<String, Object> params) {
		return (List<Map<String, Object>>) this.getSqlMapClientTemplate().queryForList("t_sys_game.getUserLotteryListWithTotal",params);
	}

	@Override
	public List<Map<String, Object>> getUserLotteryVoucherList(Map<String, Object> params) {
		return (List<Map<String, Object>>) this.getSqlMapClientTemplate().queryForList("t_sys_game.getUserLotteryVoucherList",params);
	}

	@Override
	public List<Map<String, Object>> getGameLotteryTypes(Map<String, Object> params) {
		return this.getSqlMapClientTemplate().queryForList("t_sys_game.getGameLotteryTypes",params);
	}

}
