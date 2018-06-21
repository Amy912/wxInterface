package com.emagsoftware.wx.service;

import java.util.List;
import java.util.Map;

import com.emagsoftware.wx.bean.WxBill;
import com.emagsoftware.wx.bean.WxOrder;


public interface WxOrderService {
	/**
	 * 新增订单信息
	 * @param order
	 */
	public String addOrder(WxOrder order,int number);
	/**
	 * 修改订单号
	 * @param order
	 */
	public void updateOrderNo(Map<String, Object> map);
	/**
	 * 新增账单
	 * @param bill
	 * @return
	 */
	public Integer insertBill(WxBill bill);
	/**
	 * 通过订单号查询订单信息
	 * @param bill
	 * @return
	 */
	public WxOrder selectOrderByOrderNo(String orderNo);
	/**
	 * 删除订单
	 * @param order
	 */
	public void deleteOrder(String orderId);
	/**
	 * 获取已领红包列表
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> selectRedBagByOrderNo(Map<String, Object> map);
	/**
	 * 领红包
	 * @param map
	 * @return
	 */
	public Map<String, Object> updateRedBagByOrderId(Map<String, Object> map);
	/**
	 * 查询红包信息
	 * @param map
	 * @return
	 */
	public Map<String, Object> selectOrderByMap(Map<String, Object> map);
	/**
	 * 获取发出的红包列表
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> selectRedBagByUserId(Map<String, Object> map);
	/**
	 * 新增提现订单信息
	 * @param order
	 */
	public WxOrder addWidthDrawOrder(WxOrder order);
	/**
	 * 保存提现订单后账单信息
	 * @param order
	 */
	public String saveWidthDrawBill(WxBill bill);
	/**
	 * 获取领取的红包列表
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> selectReceiveRedBagByUserId(Map<String, Object> map);
	/**
	 * 发出的红包统计
	 * @param map
	 * @return
	 */
	public Map<String, Object> selectSendRedBagCount(Map<String, Object> map);
	/**
	 * 收到的红包统计
	 * @param map
	 * @return
	 */
	public Map<String, Object> selectReceiveRedBagCount(Map<String, Object> map);
}
