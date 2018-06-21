package com.emagsoftware.wx.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emagsoftware.wx.bean.WxUser;
import com.emagsoftware.wx.dao.WxUserDao;
import com.emagsoftware.wx.service.WxUserService;

@Service
public class WxUserServiceImpl implements WxUserService {
	protected Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private WxUserDao wxUserDao;
	@Override
	public Map<String, Object> saveUser(WxUser user) {
		Integer id;
		Map<String, Object> resMap = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("openId", user.getOpenId());
		map.put("appId", user.getAppId());
		WxUser seluser = wxUserDao.selectUserInfoByOpenId(map);
		double money = 0;
		if(seluser!=null){
			id = seluser.getId();
			if((StringUtils.isNotBlank(user.getNickName())&&!StringUtils.equals(user.getNickName(),seluser.getNickName()))||
					(StringUtils.isNotBlank(user.getUnionId())&&!StringUtils.equals(user.getUnionId(),seluser.getUnionId()))||
					(StringUtils.isNotBlank(user.getUserIcon())&&!StringUtils.equals(user.getUserIcon(),seluser.getUserIcon()))){//用户昵称变更
				Map<String, Object> upMap = new HashMap<String, Object>();
				if(StringUtils.isNotBlank(user.getNickName())&&!StringUtils.equals(user.getNickName(),seluser.getNickName())){
					upMap.put("nickName", user.getNickName());
				}
				if(StringUtils.isNotBlank(user.getUnionId())&&!StringUtils.equals(user.getUnionId(),seluser.getUnionId())){
					upMap.put("unionId", user.getUnionId());
				}

				if(StringUtils.isNotBlank(user.getUserIcon())&&!StringUtils.equals(user.getUserIcon(),seluser.getUserIcon())){
					upMap.put("userIcon", user.getUserIcon());
				}
				upMap.put("id", seluser.getId());
				wxUserDao.updateUserInfoById(upMap);
				logger.info("修改微信用户信息"+id);
			}
			money = seluser.getMoney();
		}else{
			id =wxUserDao.insertUser(user);
			logger.info("新增微信用户"+id);
		}
		resMap.put("userid", id);
		resMap.put("userMoney", money);
		return resMap;
	}
	@Override
	public WxUser selectUserInfoByOpenId(Map<String, Object> map) {
		return wxUserDao.selectUserInfoByOpenId(map);
	}
	@Override
	public WxUser selectUserInfoByUserId(Map<String, Object> map) {
		return wxUserDao.selectUserInfoByUserId(map);
	}

}
