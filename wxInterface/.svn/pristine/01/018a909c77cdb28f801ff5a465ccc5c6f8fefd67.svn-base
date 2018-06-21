package com.emagsoftware.frame.utils;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @Title: 序列处理工具类
 * @Description:
 * @Copyright: Copyright (c) Sep 5, 2013
 * @Company: 幻方朗睿
 * @Author: shenjun
 * @Version: 1.0
 * @history:
 */
public class SeqUtil
{
	private static JdbcTemplate	jdbcTemplate;

	/**
	 * 根据序列名称获取序列下个值
	 * 
	 * @param seq
	 *            序列名称
	 * @param type
	 *            数据类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getNextValueBySeqName(String seq, Class<T> type)
	{
		Object id = jdbcTemplate.queryForObject("select " + seq + ".nextval from dual", type);

		return (T)id;
	}

	public static JdbcTemplate getJdbcTemplate()
	{
		return jdbcTemplate;
	}

	public static boolean setJdbcTemplate(JdbcTemplate jdbcTemplate)
	{
		SeqUtil.jdbcTemplate = jdbcTemplate;

		return true;
	}
}
