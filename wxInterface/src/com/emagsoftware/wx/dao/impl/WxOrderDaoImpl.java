package com.emagsoftware.wx.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.emagsoftware.frame.dao.BaseDao;
import com.emagsoftware.wx.bean.WxBill;
import com.emagsoftware.wx.bean.WxOrder;
import com.emagsoftware.wx.bean.WxRedbag;
import com.emagsoftware.wx.dao.WxOrderDao;

@SuppressWarnings("deprecation")
@Repository("wxOrderDao")
public class WxOrderDaoImpl extends BaseDao implements WxOrderDao {

	@Override
	public Integer insertOrder(WxOrder order) {
		return (Integer) this.getSqlMapClientTemplate().insert("t_sys_order.insertOrder",order);
	}

	@Override
	public int updateOrderInfoById(Map<String, Object> map) {
		return this.getSqlMapClientTemplate().update("t_sys_order.updateOrderById", map);
	}

	@Override
	public Integer insertRedBag(WxRedbag hb) {
		return (Integer) this.getSqlMapClientTemplate().insert("t_sys_order.insertRedbag",hb);
	}

	@Override
	public Integer insertBill(WxBill bill) {
		return (Integer) this.getSqlMapClientTemplate().insert("t_sys_order.insertBill",bill);
	}

	@Override
	public WxOrder selectOrderByOrderNo(String orderNo) {
		return (WxOrder) this.getSqlMapClientTemplate().queryForObject("t_sys_order.selectOrderByOrderNo", orderNo);
	}


	@Override
	public void deleteOrder(String id) {
		this.getSqlMapClientTemplate().delete("t_sys_order.deleteOrder", id);
	}

	@Override
	public void deleteRedBag(String orderId) {
		this.getSqlMapClientTemplate().delete("t_sys_order.deleteRedBag", orderId);
	}

	@Override
	public List<Map<String, Object>> selectRedBagByOrderNo(Map<String, Object> map) {
		return (List<Map<String, Object>>)this.getSqlMapClientTemplate().queryForList("t_sys_order.selectRedBagByOrderNo", map);
	}

	@Override
	public int updateRedBagByOrderId(Map<String, Object> map) {
		return this.getSqlMapClientTemplate().update("t_sys_order.updateRedBagByOrderId", map);
	}

	@Override
	public Map<String, Object> selectOrderByMap(Map<String, Object> map) {
		return (Map<String, Object>) this.getSqlMapClientTemplate().queryForObject("t_sys_order.selectOrderByMap", map);
	}

	@Override
	public int updateUserMoney(Map<String, Object> map) {
		return this.getSqlMapClientTemplate().update("t_sys_order.updateUserMoney", map);
	}

	@Override
	public List<Map<String, Object>> selectRedBagByUserId(Map<String, Object> map) {
		return (List<Map<String, Object>>)this.getSqlMapClientTemplate().queryForList("t_sys_order.selectRedBagByUserId", map);
	}

	@Override
	public List<Map<String, Object>> selectReceiveRedBagByUserId(Map<String, Object> map) {
		return (List<Map<String, Object>>)this.getSqlMapClientTemplate().queryForList("t_sys_order.selectReceiveRedBagByUserId", map);
	}

	@Override
	public Map<String, Object> selectSendRedBagCount(Map<String, Object> map) {
		return (Map<String, Object>) this.getSqlMapClientTemplate().queryForObject("t_sys_order.selectSendRedBagCount", map);
	}

	@Override
	public Map<String, Object> selectReceiveRedBagCount(Map<String, Object> map) {
		return (Map<String, Object>) this.getSqlMapClientTemplate().queryForObject("t_sys_order.selectReceiveRedBagCount", map);
	}

}
