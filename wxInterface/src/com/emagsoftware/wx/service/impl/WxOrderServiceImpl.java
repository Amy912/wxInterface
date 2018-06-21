package com.emagsoftware.wx.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emagsoftware.wx.bean.WxBill;
import com.emagsoftware.wx.bean.WxOrder;
import com.emagsoftware.wx.bean.WxRedbag;
import com.emagsoftware.wx.bean.WxUser;
import com.emagsoftware.wx.dao.WxOrderDao;
import com.emagsoftware.wx.dao.WxUserDao;
import com.emagsoftware.wx.service.WxOrderService;
import com.emagsoftware.wx.wxapi.HongBao;
import com.emagsoftware.wx.wxapi.WXPayUtil;

@Service
public class WxOrderServiceImpl implements WxOrderService {

	protected Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private WxOrderDao wxOrderDao;
	@Autowired
	private WxUserDao wxUserDao;
	@Override
	public String addOrder(WxOrder order, int number) {
		//保存订单信息
		Integer id = wxOrderDao.insertOrder(order);
		logger.info("新增支付订单" + id);
		//修改订单号
		String orderNo = WXPayUtil.getCurrentTimestampMs()+""+id;
		Map<String, Object> updateOrder = new HashMap<String, Object>();
		updateOrder.put("id", id);
		updateOrder.put("orderNo", orderNo);
		updateOrderNo(updateOrder);
		//创建红包列表
		List<Double> hbList = HongBao.hb(order.getMoney(), number, 0.01);
		for (int i = 0; i < hbList.size(); i++) {
			Double money = hbList.get(i);
			WxRedbag hb = new WxRedbag();
			hb.setOrderId(id);
			hb.setOrderNumber(i + 1);
			hb.setMoney(money);
			hb.setStatus("0");
			hb.setCreateTime(WXPayUtil.getCurrentTime());
			int hbid = wxOrderDao.insertRedBag(hb);
			logger.info("新增红包" + hbid);
		}
		return orderNo;
	}

	@Override
	public void updateOrderNo(Map<String, Object> map) {
		wxOrderDao.updateOrderInfoById(map);
	}

	@Override
	public Integer insertBill(WxBill bill) {
		return wxOrderDao.insertBill(bill);
	}

	@Override
	public WxOrder selectOrderByOrderNo(String orderNo) {
		WxOrder order = wxOrderDao.selectOrderByOrderNo(orderNo);
		return order;
	}

	@Override
	public void deleteOrder(String orderId) {
		wxOrderDao.deleteOrder(orderId);
		wxOrderDao.deleteRedBag(orderId);
	}

	@Override
	public List<Map<String, Object>> selectRedBagByOrderNo(Map<String, Object> map) {
		return wxOrderDao.selectRedBagByOrderNo(map);
	}

	@Override
	public Map<String, Object> updateRedBagByOrderId(Map<String, Object> map) {
		
		Map<String, Object> resMap = new HashMap<String, Object>();
		double  leftmoney = 0;
		// 抢红包
		int count = wxOrderDao.updateRedBagByOrderId(map);
		Map<String, Object> umap = new HashMap<String, Object>();
		umap.put("id",map.get("userId"));
		WxUser user = wxUserDao.selectUserInfoByUserId(umap);
		leftmoney= user.getMoney();
		if (count > 0) {
			double money= user.getMoney();
			// 修改用户账户余额信息
			wxOrderDao.updateUserMoney(map);
			user = wxUserDao.selectUserInfoByUserId(umap);
			//保存账单信息
			leftmoney= user.getMoney();
			WxBill bill = new WxBill();
			bill.setBillTime(WXPayUtil.getCurrentTime());
			bill.setMoney(BigDecimal.valueOf(leftmoney).subtract(BigDecimal.valueOf(money)).doubleValue());
			bill.setLeftMoney(leftmoney);
			bill.setOpenId((String)map.get("openid"));
			int orderId = Integer.parseInt((String)map.get("orderId"));
			bill.setOrderId(orderId);
			bill.setUserId(user.getId());
			bill.setDesc("领红包");
			saveWidthDrawBill(bill);
		}
		resMap.put("leftmoney", leftmoney);
		resMap.put("count", count);
		return resMap;
	}

	@Override
	public Map<String, Object> selectOrderByMap(Map<String, Object> map) {
		return wxOrderDao.selectOrderByMap(map);
	}

	@Override
	public List<Map<String, Object>> selectRedBagByUserId(Map<String, Object> map) {
		return wxOrderDao.selectRedBagByUserId(map);
	}

	@Override
	public WxOrder addWidthDrawOrder(WxOrder order) {
		//保存订单信息
		Integer id = wxOrderDao.insertOrder(order);
		logger.info("新增提现订单" + id);
		String orderNo = WXPayUtil.getCurrentTimestampMs()+""+id;
		Map<String, Object> updateOrder = new HashMap<String, Object>();
		updateOrder.put("id", id);
		updateOrder.put("orderNo", orderNo);
		updateOrderNo(updateOrder);
		order.setId(id);
		//修改订单号
		order.setOrderNo(orderNo);
		return order;
	}

	@Override
	public String saveWidthDrawBill(WxBill bill) {
		//保存账单信息
		int id = insertBill(bill);
		Map<String, Object> upMap = new HashMap<String, Object>();
		upMap.put("id", bill.getUserId());
		upMap.put("money", bill.getLeftMoney());
		//修改用户余额
		wxUserDao.updateUserInfoById(upMap);
		return id+"";
	}

	@Override
	public List<Map<String, Object>> selectReceiveRedBagByUserId(Map<String, Object> map) {
		return wxOrderDao.selectReceiveRedBagByUserId(map);
	}

	@Override
	public Map<String, Object> selectSendRedBagCount(Map<String, Object> map) {
		return wxOrderDao.selectSendRedBagCount(map);
	}

	@Override
	public Map<String, Object> selectReceiveRedBagCount(Map<String, Object> map) {
		return wxOrderDao.selectReceiveRedBagCount(map);
	}
}
