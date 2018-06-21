package com.emagsoftware.frame.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emagsoftware.frame.utils.CommonUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.util.Pool;

public class RedisUtils {

	private static List<JedisShardInfo> readShards = new ArrayList<JedisShardInfo>();

	private static List<JedisShardInfo> writeShards = new ArrayList<JedisShardInfo>();

	private static JedisPoolConfig config = new JedisPoolConfig();

	private static Pool<Jedis> jedisReadPool;

	private static Pool<Jedis> jedisWritePool;

	private static Logger logger = LoggerFactory.getLogger(RedisUtils.class);
	
	/**
	 * 配置文件信息
	 */
	private static Map<String, String> configMap;
	
	private static String initStartModel = "use";

	static {
		try {
			configMap = CommonUtil.getPropertiesConfig("redis");
			if (null != configMap && !configMap.isEmpty()) {
				// 生成多机连接信息列表
				if (StringUtils.isNotBlank(configMap.get("redis.host"))) {
					readShards.add(new JedisShardInfo(configMap.get("redis.host"), Integer.parseInt(configMap.get("redis.port"))));
					writeShards.add(new JedisShardInfo(configMap.get("redis.host"), Integer.parseInt(configMap.get("redis.port"))));
				} else {
					readShards.add(new JedisShardInfo(configMap.get("redis.read.host"), Integer.parseInt(configMap.get("redis.port"))));
					writeShards.add(new JedisShardInfo(configMap.get("redis.write.host"), Integer.parseInt(configMap.get("redis.port"))));
				}
				// 生成连接池配置信息
				config.setMaxIdle(Integer.parseInt(configMap.get("redis.maxidle")));
				config.setMaxActive(Integer.parseInt(configMap.get("redis.maxactive")));
			}
			if (StringUtils.isNotBlank(configMap.get("redis.host"))) {
				jedisReadPool = new JedisPool(config, configMap.get("redis.host"));
				jedisWritePool = new JedisPool(config, configMap.get("redis.host"));
			} else {
				jedisReadPool = new JedisPool(config, configMap.get("redis.read.host"));
				jedisWritePool = new JedisPool(config, configMap.get("redis.write.host"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public static void add(String key, String value, int seconds) {
		String startModel = initStartModel;
		if ("test".equals(startModel)) {
			return;
		}
		ShardedJedisPool pool = new ShardedJedisPool(config, writeShards);
		ShardedJedis client = pool.getResource();
		try {
			client.setex(key, seconds, value);
		} catch (Exception e) {
			e.printStackTrace();
			if (StringUtils.isNotBlank(e.getMessage())) {
				logger.error(e.getMessage());
			}
		} finally {
			if (null != client) {
				pool.returnResource(client);
			}
			pool.destroy();
		}
	}

	public static void addnx(String key, String value) {
		String startModel = initStartModel;
		if ("test".equals(startModel)) {
			return;
		}
		ShardedJedisPool pool = new ShardedJedisPool(config, writeShards);
		ShardedJedis client = pool.getResource();
		try {
			client.setnx(key, value);
		} catch (Exception e) {
			e.printStackTrace();
			if (StringUtils.isNotBlank(e.getMessage())) {
				logger.error(e.getMessage());
			}
		} finally {
			if (null != client) {
				pool.returnResource(client);
			}
			pool.destroy();
		}
	}

	public static void add(String key, String value) {
		String startModel = initStartModel;
		if ("test".equals(startModel)) {
			return;
		}
		ShardedJedisPool pool = new ShardedJedisPool(config, writeShards);
		ShardedJedis client = pool.getResource();
		try {
			client.set(key, value);
		} catch (Exception e) {
			e.printStackTrace();
			if (StringUtils.isNotBlank(e.getMessage())) {
				logger.error(e.getMessage());
			}
		} finally {
			if (null != client) {
				pool.returnResource(client);
			}
			pool.destroy();
		}
	}

	/**
	 * 存储序列化对象
	 * 
	 * @param key
	 * @param value
	 */
	public static void add(byte[] key, byte[] value, int seconds) {
		String startModel = initStartModel;
		if ("test".equals(startModel)) {
			return;
		}
		ShardedJedisPool pool = new ShardedJedisPool(config, writeShards);
		ShardedJedis client = pool.getResource();
		try {

			if (value == null) {
			} else {
				client.setex(key, seconds, value);
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (StringUtils.isNotBlank(e.getMessage())) {
				logger.error(e.getMessage());
			}
		} finally {
			if (null != client) {
				pool.returnResource(client);
			}
			pool.destroy();
		}
	}
	/**
	 * 存储序列化对象
	 * 
	 * @param key
	 * @param value
	 */
	public static void add(byte[] key, byte[] value) {
		String startModel = initStartModel;
		if ("test".equals(startModel)) {
			return;
		}
		ShardedJedisPool pool = new ShardedJedisPool(config, writeShards);
		ShardedJedis client = pool.getResource();
		try {

			if (value == null) {
			} else {
				client.set(key, value);
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (StringUtils.isNotBlank(e.getMessage())) {
				logger.error(e.getMessage());
			}
		} finally {
			if (null != client) {
				pool.returnResource(client);
			}
			pool.destroy();
		}
	}
	public static String get(String key) {

		String startModel = initStartModel;
		if ("test".equals(startModel)) {
			return null;
		}
		ShardedJedisPool pool = new ShardedJedisPool(config, readShards);
		ShardedJedis client = pool.getResource();
		try {
			return client.get(key);
		} catch (Exception e) {
			e.printStackTrace();
			if (StringUtils.isNotBlank(e.getMessage())) {
				logger.error(e.getMessage());
			}
		} finally {
			if (null != client) {
				pool.returnResource(client);
			}
			pool.destroy();
		}
		return null;
	}

	public static String getSet(String key, String value) {
		String startModel = initStartModel;
		if ("test".equals(startModel)) {
			return null;
		}
		ShardedJedisPool pool = new ShardedJedisPool(config, readShards);
		ShardedJedis client = pool.getResource();
		try {
			return client.getSet(key, value);
		} catch (Exception e) {
			e.printStackTrace();
			if (StringUtils.isNotBlank(e.getMessage())) {
				logger.error(e.getMessage());
			}
		} finally {
			if (null != client) {
				pool.returnResource(client);
			}
			pool.destroy();
		}
		return null;
	}

	/**
	 * 获取序列化对象
	 * 
	 * @param key
	 * @return
	 */
	public static byte[] get(byte[] key) {
		String startModel = initStartModel;
		if ("test".equals(startModel)) {
			return null;
		}
		ShardedJedisPool pool = new ShardedJedisPool(config, readShards);
		ShardedJedis client = pool.getResource();
		try {
			byte[] bs = client.get(key);
			if (bs == null) {
			} else {
			}
			return bs;
		} catch (Exception e) {
			e.printStackTrace();
			if (StringUtils.isNotBlank(e.getMessage())) {
				logger.error(e.getMessage());
			}
		} finally {
			if (null != client) {
				pool.returnResource(client);
			}
			pool.destroy();
		}
		return null;
	}

	public static void del(String key) {
		String startModel = initStartModel;
		if ("test".equals(startModel)) {
			return;
		}
		ShardedJedisPool pool = new ShardedJedisPool(config, writeShards);
		ShardedJedis client = pool.getResource();
		try {
			client.del(key);
		} catch (Exception e) {
			e.printStackTrace();
			if (StringUtils.isNotBlank(e.getMessage())) {
				logger.error(e.getMessage());
			}
		} finally {
			if (null != client) {
				pool.returnResource(client);
			}
			pool.destroy();
		}
	}

	public static Jedis getJedis() {
		if (jedisReadPool != null) {
			return jedisReadPool.getResource();
		} else if (jedisWritePool != null) {
			return jedisWritePool.getResource();
		}
		return null;
	}

	/**
	 * 释放以损坏的jedis.      
	 */
	public static void closeBroken(Jedis jedis) {
		if (jedis != null) {
			try {
				jedisReadPool.returnBrokenResource(jedis);
				jedisWritePool.returnBrokenResource(jedis);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 从连接池中释放jedis      
	 */
	public static void close(Jedis jedis) {
		if (jedis != null) {
			try {
				jedisReadPool.returnResource(jedis);
				jedisWritePool.returnResource(jedis);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 判断事物执行后返回结果列表是否为期望的结果列表      
	 * 
	 * @param results
	 *            事务执行结果      
	 * @param expects
	 *            期望的结果     
	 * @return      
	 */
	public static boolean expect(List<Object> results, Object... expects) {
		try {
			if (results != null && expects != null && results.size() == expects.length) {
				for (int i = 0; i < expects.length; i++) {
					Object result = results.get(i);
					if (result == null || !result.equals(expects[i])) {
						return false;
					}
				}
				return true;
			}
		} catch (Exception e) {

		}
		return false;
	}

	public static Map<String, String> getConfigMap() {
		return configMap;
	}

}
