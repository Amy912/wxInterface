package com.emagsoftware.wx.dao;

import java.util.List;
import java.util.Map;

import com.emagsoftware.wx.bean.WxBill;
import com.emagsoftware.wx.bean.WxOrder;
import com.emagsoftware.wx.bean.WxRedbag;
import com.emagsoftware.wx.bean.WxUser;

public interface WxOrderDao {

	public Integer insertOrder(WxOrder order);

	public int updateOrderInfoById(Map<String, Object> map);

	public Integer insertRedBag(WxRedbag hb);

	public Integer insertBill(WxBill bill);

	public WxOrder selectOrderByOrderNo(String orderNo);

	public void deleteOrder(String id);

	public void deleteRedBag(String orderId);

	public Map<String, Object> selectOrderByMap(Map<String, Object> map);

	public List<Map<String, Object>> selectRedBagByOrderNo(Map<String, Object> map);

	public int updateRedBagByOrderId(Map<String, Object> map);

	public int updateUserMoney(Map<String, Object> map);

	public Map<String, Object> selectSendRedBagCount(Map<String, Object> map);
	/**
	 * 获取发出的红包列表
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> selectRedBagByUserId(Map<String, Object> map);

	public Map<String, Object> selectReceiveRedBagCount(Map<String, Object> map);
	/**
	 * 获取领取的红包列表
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> selectReceiveRedBagByUserId(Map<String, Object> map);
	
}
