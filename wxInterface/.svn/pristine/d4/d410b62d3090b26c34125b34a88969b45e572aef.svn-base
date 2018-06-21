package com.emagsoftware.frame.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * iBatis DAO基类
 */
@SuppressWarnings("deprecation")
public class BaseDao extends SqlMapClientDaoSupport {

	/**
	 * 注入SqlMapClient
	 * 
	 * @param sqlMapClient
	 */
	@Autowired
	public void setSqlMapClientFoJrAutowire(SqlMapClient sqlMapClient) {
		super.setSqlMapClient(sqlMapClient);
	}
}