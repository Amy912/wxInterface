package com.emagsoftware.inequation.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.emagsoftware.frame.controller.BaseController;
import com.emagsoftware.frame.log4j.ILog;
import com.emagsoftware.frame.log4j.Logger;
import com.emagsoftware.frame.utils.ClientHttpConUtil;
import com.emagsoftware.frame.utils.ConfigCache;
import com.emagsoftware.frame.utils.JsonUtils;
import com.emagsoftware.frame.utils.RedisUtils;
import com.emagsoftware.frame.utils.WXBizDataCrypt;
import com.emagsoftware.inequation.bean.GameUser;
import com.emagsoftware.inequation.service.GameInfoService;
import com.emagsoftware.inequation.service.GameUserService;
import com.emagsoftware.wx.bean.WxBill;
import com.emagsoftware.wx.bean.WxOrder;
import com.emagsoftware.wx.bean.WxUser;
import com.emagsoftware.wx.service.WxOrderService;
import com.emagsoftware.wx.service.WxUserService;
import com.emagsoftware.wx.wxapi.WXPayConstants;
import com.emagsoftware.wx.wxapi.WXPayRequest;
import com.emagsoftware.wx.wxapi.WXPayUtil;
import com.emagsoftware.wx.wxapi.impl.WXPayConfigImpl;

@Controller
public class GameUserController extends BaseController  {
	@Logger
	private ILog logger;
	
	@Autowired
	private GameUserService userService;
	
	@Autowired
	private GameInfoService gameInfoService;

	@Autowired
	private WxUserService wxUserService;
	
	@Autowired
	private WxOrderService wxOrderService;
	
	private static String appId = "wxd1a56d49be0fd7e8";
	
	private static String appSecret = "9025f245beff0279184f00269a3af464";
	
	/**
	 * 微信登录校验用户信息---- 该方法已舍弃，改为调用统一封装的getSessionId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/game/wxUserVerify")
	public String getWeiXinUserSessionByCode(HttpServletRequest request,HttpServletResponse response)
	{
		try {
			response.setContentType("text/html; charset=UTF-8");
			if(StringUtils.isNotBlank(request.getParameter("code"))) {
				String code = request.getParameter("code");
				String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+appId+"&secret="+appSecret+"&js_code="+code+"&grant_type=authorization_code";
				String returnJson = ClientHttpConUtil.getHttpJson("", url, null);
				logger.info("微信登录接口返回信息："+returnJson);
				
				Map<String, String> map = JsonUtils.getMapFromJson(returnJson);
				String userId = "";
				if(map.containsKey("openid")) {
					Map<String,Object> params = new HashMap<String,Object>();
					params.put("openId", map.get("openid"));
					if(map.containsKey("unionid")) {
						params.put("unionId", map.get("unionid"));
					}
					
					//查询该用户是否已经注册登陆过
					GameUser user = this.userService.getUserInfoByMap(params);
					if(user == null) {
						//用户未注册进行注册
						params.put("createTime", new Date());
						userId = this.userService.insertLoginUserInfo(params)+"";
					}else {
						userId = user.getId().toString();
						
						//根据用户主键进行登录领取次数的判断
						Date today = new Date();
						int difference = timeBetween(today,user.getCreateTime());
						String time = DateUtils.formatDate(today, "yyyy-MM-dd");
						String loginTimes = RedisUtils.get(userId+"login"+time);
						
						//次日起每天首次登录挑战机会+3
						if((loginTimes == null || StringUtils.isBlank(loginTimes)) && difference >= 1) {
							RedisUtils.add(userId+"login"+time,"3",1440*60);
						}
					}
					//存放
					RedisUtils.add("session_key:"+userId,map.get("session_key"),120*60);
					RedisUtils.add("openId:"+userId,map.get("openid"),120*60);
				}
				String sessionId = request.getSession().getId();
				RedisUtils.add(sessionId, map.get("openid") + map.get("session_key"), 120*60);
				//返回经过加密的用户登录凭证 ： 生成一个唯一字符串sessionid作为键，将openid和session_key作为值，存入redis，超时时间设置为2小时
				String result = "{\"sessionId\": \""+sessionId+"\",\"userId\":\""+userId+"\"}";
				response.getWriter().write(result);
				
			}else {
				logger.info("微信校验参数为空！");
			}
			
		} catch (Exception e) {
			logger.info("微信用户校验失败！");
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/game/insertGameUser")
	public String insertGameUserInfoBySessionId(HttpServletRequest request,HttpServletResponse response) {
		try {
			if(StringUtils.isNotBlank(request.getHeader("cookie"))) {
				String wxuserId = request.getSession().getAttribute("userid") == null ? "" : request.getSession().getAttribute("userid").toString();
				System.out.println("wxuserId:"+wxuserId);
				Map<String,Object> params = new HashMap<String,Object>();
				
				if(StringUtils.isNotBlank(wxuserId)) {
					String userId = "";
					params.put("wxuserId", wxuserId);
					//查询该用户是否已经注册登陆过
					GameUser user = this.userService.getUserInfoByMap(params);
					if(user == null) {
						//用户未注册进行注册
						params.put("createTime", new Date());
						userId = this.userService.insertLoginUserInfo(params)+"";
					}else {
						userId = user.getId().toString();
						
						//根据用户主键进行登录领取次数的判断
						Date today = new Date();
						int difference = timeBetween(today,user.getCreateTime());
						String time = DateUtils.formatDate(today, "yyyy-MM-dd");
						String loginTimes = RedisUtils.get(userId+"login"+time);
						
						//次日起每天首次登录挑战机会+3
						if((loginTimes == null || StringUtils.isBlank(loginTimes)) && difference >= 1) {
							RedisUtils.add(userId+"login"+time,"3",1440*60);
						}
					}
					System.out.println("inequationGameUserId:"+userId);
					//保存gameuser的主键
					request.getSession().setAttribute("inequationGameUserId", userId);
				}
				
			}else {
				logger.info("用户校验参数为空！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	//计算时间差
	private int timeBetween(Date t1,Date t2) {
		Calendar cld1 = Calendar.getInstance();
		cld1.setTime(t1);
		
		Calendar cld2 = Calendar.getInstance();
		cld2.setTime(t2);
		
		int dif = 0;
		if(cld1.get(Calendar.YEAR) > cld2.get(Calendar.YEAR)) {
			dif = 10;
		}else if(cld1.get(Calendar.YEAR) == cld2.get(Calendar.YEAR)) {
			if(cld1.get(Calendar.MONTH) > cld2.get(Calendar.MONTH) ) {
				dif = 10;
			}else if(cld1.get(Calendar.MONTH) == cld2.get(Calendar.MONTH)) {
				dif = cld1.get(Calendar.DATE) - cld2.get(Calendar.DATE);
				//System.out.println("时间差："+cld1.get(Calendar.DATE)  + "===" + cld2.get(Calendar.DATE));
			}
		}
		
		return dif;
	}
	
	/**
	 * 个人信息的获取
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/game/userInfo")
	public String getGameUserInfo(HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		String userId = request.getSession().getAttribute("inequationGameUserId") == null ? "" :request.getSession().getAttribute("inequationGameUserId").toString();
		if(StringUtils.isNotBlank(userId)) {
			Map<String,Object> result = this.userService.getUserGameInfoByUserId(userId);
			
			//可挑战次数
			int ownTimes = fetchUserOwnTimes(userId);
			result.put("ownTimes", ownTimes);
			
			//已拥有的娃娃券个数
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("userId", userId);
			
			String lotteryType = getLotteryType("game.lottery.doll");
			params.put("mytype", lotteryType);
			int totalDolls = this.gameInfoService.getUserOwnAllDolls(params);
			result.put("dolls", totalDolls);
			
			
			//抽奖获得的的现金 TODO [暂定6：1元 7：3元 8：5元]
			lotteryType = getLotteryType("game.lottery.cash");
			params.put("mytype", lotteryType);
			params.put("status", "0");
			List<Map<String, Object>> lists = this.gameInfoService.getUserLotteryListByMap(params);
			int cash = 0;
			for(Map<String, Object> map:lists) {
				cash += Integer.parseInt(map.get("backup").toString());
			}
			System.out.println("现金总额："+cash);
			result.put("mycash", cash);
			
			response.getWriter().write(JSON.toJSONString(result));
		}
		return null;
	}
	
	/**
	 * 修改用户信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/game/updateUserInfo")
	public void uodateGameUserInfo(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new HashMap<String,Object>();
		String userId = request.getSession().getAttribute("inequationGameUserId") == null ? "" :request.getSession().getAttribute("inequationGameUserId").toString();
		boolean flag = false;
		if(StringUtils.isNotBlank(userId)) {
			params.put("id",userId);
		}
		if(StringUtils.isNotBlank(request.getParameter("lottery"))) {
			flag = true;
			params.put("lottery", request.getParameter("lottery"));
		}
		if(StringUtils.isNotBlank(request.getParameter("highScore"))) {
			flag = true;
			params.put("highScore", request.getParameter("highScore"));
		}
		//以下参数无需具体数值
		if(StringUtils.isNotBlank(request.getParameter("ownTimes"))) {
			flag = true;
			params.put("ownTimes", request.getParameter("ownTimes"));
		}
		if(StringUtils.isNotBlank(request.getParameter("tryTimes"))) {
			flag = true;
			params.put("tryTimes", request.getParameter("tryTimes"));
		}
		
		if(StringUtils.isNotBlank(request.getParameter("successTimes"))) {
			flag = true;
			params.put("successTimes", request.getParameter("successTimes"));
		}
		
		if(StringUtils.isNotBlank(request.getParameter("lotteryTimes"))) {
			flag = true;
			params.put("lotteryTimes", request.getParameter("lotteryTimes"));
		}
		
		if(StringUtils.isNotBlank(request.getParameter("usedLotteryTimes"))) {
			flag = true;
			params.put("usedLotteryTimes", request.getParameter("usedLotteryTimes"));
		}
		if(flag) {
			params.put("updateGameUser", "updateGameUser");
		}
		
		//更新微信用户的相关信息
		String wxuserId = request.getSession().getAttribute("userid") == null ? "" :request.getSession().getAttribute("userid").toString();
		params.put("sysuserid", wxuserId);
		if(StringUtils.isNotBlank(request.getParameter("wxName"))) {
			params.put("wxName", request.getParameter("wxName"));
		}
		if(StringUtils.isNotBlank(request.getParameter("wxIcon"))) {
			params.put("wxIcon", request.getParameter("wxIcon"));
		}
		this.userService.updateLoginUserInfo(params);
	}
	
	/**
	 * 娃娃等实物奖品的领取
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/game/lotteryRecord")
	public String insertUserLotteryRecord(HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.setContentType("text/html; charset=UTF-8");
		Map<String,Object> params = new HashMap<String,Object>();
		String userId = request.getSession().getAttribute("inequationGameUserId") == null ? "" :request.getSession().getAttribute("inequationGameUserId").toString();
		if(StringUtils.isNotBlank(userId)) {
			params.put("userId", userId);
		}
		if(StringUtils.isNotBlank(request.getParameter("lotteryId"))) {
			params.put("lotteryId", request.getParameter("lotteryId"));
		}
		if(StringUtils.isNotBlank(request.getParameter("address"))) {
			params.put("address", request.getParameter("address"));
		}
		if(StringUtils.isNotBlank(request.getParameter("name"))) {
			params.put("name", request.getParameter("name"));
		}
		if(StringUtils.isNotBlank(request.getParameter("phone"))) {
			params.put("phone", request.getParameter("phone"));
		}
		int result = this.userService.insertUserLotteryRecord(params);
		response.getWriter().write( result +"");
		return null;
	}
	
	/**
	 * 用户投诉
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping("/game/userComplaint")
	public void userComplaint(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String,Object> params = new HashMap<String,Object>();
		String userId = request.getSession().getAttribute("inequationGameUserId") == null ? "" :request.getSession().getAttribute("inequationGameUserId").toString();
		if(StringUtils.isNotBlank(userId)) {
			params.put("userId", userId);
		}
		if(StringUtils.isNotBlank(request.getParameter("type"))) {
			params.put("type", request.getParameter("type"));
		}
		params.put("createTime", new Date());
		this.userService.insertLoginUserComplaint(params);
	}
	
	@RequestMapping("/game/userShare")
	public String userShare(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String,Object> params = new HashMap<String,Object>();
		String encryptedData = "";
		String iv = "";
		String myappId = "";
		
		String userId = request.getSession().getAttribute("inequationGameUserId") == null ? "" :request.getSession().getAttribute("inequationGameUserId").toString();
		if(StringUtils.isNotBlank(userId)) {
			myappId = request.getSession().getAttribute("appid").toString();
			params.put("userId", userId);
		}
		
		if(StringUtils.isNotBlank(request.getParameter("encryptedData"))) {
			encryptedData = request.getParameter("encryptedData");
		}
		if(StringUtils.isNotBlank(request.getParameter("iv"))) {
			iv = request.getParameter("iv");
		}
		
		String session_key = request.getSession().getAttribute("session_key") == null ? "" :request.getSession().getAttribute("session_key").toString();
		if(StringUtils.isNotBlank(session_key)) {
			WXBizDataCrypt biz = new WXBizDataCrypt(myappId, session_key);
			String myJson = biz.decryptData(encryptedData, iv);
			System.out.println("群消息解密数据："+myJson);
			
			Map<String, Object> map = JSON.parseObject(myJson);
			
			String today = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
			params.put("shareTime", today);
			//获取群对当前小程序的唯一 ID
			if(map.containsKey("openGId")) {
				params.put("openGId", map.get("openGId"));
				List<Map<String, Object>> lists = this.userService.getUserShareByMap(params);
				
				if(lists !=null && lists.size() > 0) {
					//该群已经分享过
					response.getWriter().write("0");
				}else {
					this.userService.insertUserShareInfo(params);
					
					//分享到10个不同的群，挑战次数+1，每天不超过10次
					String shareTimes = RedisUtils.get(userId+"share"+today);
					if( shareTimes != null && StringUtils.isNotBlank(shareTimes) ) {
						int count = Integer.parseInt(shareTimes);
						if(count < 10) {
							count = count+1;
							RedisUtils.add(userId+"share"+today,count+"");
						}
						
					}else {
						RedisUtils.add(userId+"share"+today,"1",2880*60);
					}
					
					response.getWriter().write("1");
				}
				
			}else {
				logger.info("群分享校验失败！");
			}
		}else {
			logger.info("用户session已失效！");
		}

		return null;
	}
	
	@RequestMapping("/game/userChances")
	public String getUserAvailableChances(HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		String userId = request.getSession().getAttribute("inequationGameUserId") == null ? "" :request.getSession().getAttribute("inequationGameUserId").toString();
		if(StringUtils.isNotBlank(userId)) {
			int count = fetchUserOwnTimes(userId);
			if(count > 0) {
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("id", userId);
				//需要将挑战次数减一 [优先使用当天登录赠与的3次，其次是群分享的 再其次是抽奖获得的 最好使用初始给与的5次机会]
				String today = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
				String yesterday = getYesterday(new Date());
				int clogin = getTimesOfType(userId,"login",today);
				int lshare = getTimesOfType(userId,"share",yesterday);
				int cshare = getTimesOfType(userId,"share",today);
				int llottery = getTimesOfType(userId,"lottery",yesterday);
				int clottery = getTimesOfType(userId,"lottery",today);
				if(clogin > 0) {
					clogin = clogin -1;
					RedisUtils.add(userId+"login"+today,clogin+"");
				}else if(lshare > 0) {
					lshare = lshare -1;
					RedisUtils.add(userId+"share"+yesterday,lshare+"");
				}else if(llottery > 0) {
					llottery = llottery -1;
					RedisUtils.add(userId+"lottery"+yesterday,llottery+"");
				}else if(cshare > 0) {
					cshare = cshare -1;
					RedisUtils.add(userId+"share"+today,cshare+"");
				}else if(clottery > 0) {
					clottery = clottery -1;
					RedisUtils.add(userId+"lottery"+today,clottery+"");
				}else{
					Map<String,Object> user = this.userService.getUserGameInfoByUserId(userId);
					int owntimes = 0 ;
					if(user.get("ownTimes") != null && (Integer)user.get("ownTimes") > 0) {
						owntimes = (Integer)user.get("ownTimes") -1 ;
						params.put("ownTimes", owntimes);
					}
					
				}
				//用户字段已挑战次数【tryTimes】+1
				params.put("tryTimes", "1");
				params.put("updateGameUser","updateGameUser");
				this.userService.updateLoginUserInfo(params);
			}
			response.getWriter().write( count +"");
		}
		
		return null;
	}
	
	private int fetchUserOwnTimes(String userId) {
		int count = 0;
		Map<String,Object> mymap = this.userService.getUserGameInfoByUserId(userId);
		count =  (Integer) mymap.get("ownTimes");
		String today = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
		String yesterday = getYesterday(new Date());
		
		count += getTimesOfType(userId,"login",today) + getTimesOfType(userId,"share",today) + getTimesOfType(userId,"share",yesterday) + getTimesOfType(userId,"lottery",today) + getTimesOfType(userId,"lottery",yesterday);
		
		//从抽奖表获取用户的挑战次数 并使这条抽奖数据失效
		//从群分享表获取挑战次数 
		//获取登录取得的挑战次数
		//所有数据存放在redis , 挑战获取类型 + 时间戳 + userId 为key,挑战次数为 value 
		return count;
	}
	
	/**
	 * 获得指定用户、类型以及日期的挑战次数
	 * @param id
	 * @param type
	 * @param time
	 * @return
	 */
	private int getTimesOfType(String id,String type,String time) {
		int count = 0;
		String times = RedisUtils.get(id+type+time);
		if(times !=null && StringUtils.isNotBlank(times)) {
			count = Integer.parseInt(times);
		}
		return count;
	}
	
	/**
	 * 获取指定日期的前一天
	 * @param date
	 * @return
	 */
	private String getYesterday(Date date) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        calendar.add(Calendar.DAY_OF_MONTH, -1);  
        date = calendar.getTime();  
        System.out.println(sdf.format(date));
        return sdf.format(date);
	}
	
	/**
	 * 答题结束，更新用户信息
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/game/gameEnd")
	public void gameEndEffects(HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		String userId = request.getSession().getAttribute("inequationGameUserId") == null ? "" :request.getSession().getAttribute("inequationGameUserId").toString();
		Map<String,Object> userInfo = new HashMap<String,Object>();
		Map<String,Object> params = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(userId)) {
			params.put("id", userId);
			userInfo = this.userService.getUserGameInfoByUserId(userId);
			boolean flag = false;
			if(StringUtils.isNotBlank(request.getParameter("qscore"))) {
				int hscore = userInfo.get("highScore") != null ? (Integer)userInfo.get("highScore") : 0;
				//若本次挑战高于该分值，需要刷新用户信息
				if(hscore < Integer.parseInt(request.getParameter("qscore"))) {
					params.put("highScore", request.getParameter("qscore"));
					flag = true;
				}
				if(Integer.parseInt(request.getParameter("qscore")) == 40) {
					//用户字段【successTimes】+1
					params.put("successTimes", "1");
					//挑战成功，抽奖次数+1
					int lotteryTimes = userInfo.get("lotteryTimes") != null ? (Integer)userInfo.get("lotteryTimes") : 0;
					params.put("lottery", lotteryTimes+1);
					System.out.println("gameEnd===lottery"+userId+"用户的lottery："+(lotteryTimes+1));
					flag = true;
				}
				if(flag) {
					params.put("updateGameUser","updateGameUser");
					this.userService.updateLoginUserInfo(params);
				}
				
			}
			
		}else {
			logger.info("用户ID校验失败！");
		}

	}
	
	/**
	 * 抽奖的现金提现
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/game/deposit")
	public String withdrawDeposit(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		try {
			String userId = request.getSession().getAttribute("userid") == null ? "" :request.getSession().getAttribute("userid").toString();
			String openId = request.getSession().getAttribute("openid") == null ? "":request.getSession().getAttribute("openid").toString();
			String myappId = request.getSession().getAttribute("appid") == null ? "":request.getSession().getAttribute("appid").toString();

			String money = request.getParameter("money");
			double payfee = Double.parseDouble(money);
			String ip = WXPayUtil.getRequestIp(request);
			System.out.println("userId:"+userId+"openId:"+openId+"payfee:"+payfee+"ip:"+ip);
			
			String ret = transfersMoney(openId,myappId,Integer.parseInt(userId),payfee,ip);
			
			//提现成功的粗略判断
			if(ret.contains("return_code") && ret.contains("SUCCESS")) {
				String gameuserId =  request.getSession().getAttribute("inequationGameUserId") == null ? "" :request.getSession().getAttribute("inequationGameUserId").toString();
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("userId", gameuserId);
				
				String lotteryType = getLotteryType("game.lottery.cash");
				params.put("lotteryType", lotteryType);
				this.userService.updateLotteryRecordStatus(params);
			}
			response.getWriter().write(ret);
		} catch (Exception e) {
			logger.error("pay error.", e);
		}
		return null;
	}
	
	/**
	 * 调微信提现接口
	 * @param openid
	 * @param appid
	 * @param userid
	 * @param money
	 * @param clientIP
	 * @return
	 * @throws Exception
	 */
	public String transfersMoney(String openid,String appid,Integer userid,double money,String clientIP) throws Exception {
		String mchid = WXPayConstants.API_MCHID_MAP.get(appid);//微信分配的商户号
		String key = WXPayConstants.SIGN_XML_KEY;// API秘钥
		String nonceStr = WXPayUtil.generateNonceStr();//随机字符串，长度要求在32位以内
		String desc = "提现";

		//创建新订单 获取订单号
		WxOrder order = new WxOrder();
		order.setMoney(money);
		order.setOpenId(openid);
		order.setOrderType("2");
		order.setUserId(userid);
		order.setOrderTime(WXPayUtil.getCurrentTime());
		order.setOrderDesc("提现");
		order = wxOrderService.addWidthDrawOrder(order);
		
		StringBuffer reqBody = new StringBuffer();
		reqBody.append("<xml>");
		reqBody.append("<mch_appid>").append(appid).append("</mch_appid>");//小程序appid
		reqBody.append("<mchid>").append(mchid).append("</mchid>");//商户号
		reqBody.append("<nonce_str>").append(nonceStr).append("</nonce_str>");//随机字符串
		//reqBody.append("<sign>").append(nonceStr).append("</sign>");
		reqBody.append("<partner_trade_no>").append(order.getOrderNo()).append("</partner_trade_no>");//订单号
		reqBody.append("<openid>").append(openid).append("</openid>");//用户openid
		reqBody.append("<check_name>").append("NO_CHECK").append("</check_name>");
		reqBody.append("<amount>").append(((int)(money*100))).append("</amount>");//金额
		reqBody.append("<desc>").append(desc).append("</desc>");//描述
		reqBody.append("<spbill_create_ip>").append(clientIP).append("</spbill_create_ip>");//客户端ip
		reqBody.append("</xml>");
		//生成带有 sign 的 XML 格式字符串
		String req = WXPayUtil.generateSignedXml(WXPayUtil.xmlToMap(reqBody.toString()), key);
		System.out.println(req);

		WXPayConfigImpl config = WXPayConfigImpl.getInstance();
		config.setAppID(appid);
		config.setKey(key);
		config.setMchID(mchid);
		WXPayRequest wxPayRequest = new WXPayRequest(config);
		//发起提现请求
		String result = wxPayRequest.requestWithCert("/mmpaymkttransfers/promotion/transfers", "oV4n54sLMaY7SYEKmGrY36gh5Rng", req, 10000, 10000,true);
		System.out.println("提现请求结果："+result);
		Map<String, String> respMap = WXPayUtil.xmlToMap(result);
		Map<String, String> retmap = new HashMap<String, String>();
		
		retmap.put("return_code", respMap.get("result_code"));
		if (StringUtils.equals(respMap.get("return_code"), "SUCCESS")) {
			if (StringUtils.equals(respMap.get("result_code"), "SUCCESS")) {
				retmap.put("orderNo", order.getOrderNo());
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id",order.getUserId());
				WxUser user = wxUserService.selectUserInfoByUserId(map);
				System.out.println("order信息："+order.getUserId()+"=="+order.getMoney()+"=="+BigDecimal.valueOf(user.getMoney()).subtract(BigDecimal.valueOf(order.getMoney())).doubleValue()+"=="+order.getOpenId()+"==="+order.getId());
				
				WxBill bill = new WxBill();
				bill.setBillTime(WXPayUtil.getCurrentTime());
				bill.setMoney(order.getMoney());
				bill.setLeftMoney(BigDecimal.valueOf(user.getMoney()).subtract(BigDecimal.valueOf(order.getMoney())).doubleValue());
				bill.setOpenId(order.getOpenId());
				bill.setOrderId(order.getId());
				bill.setUserId(user.getId());
				bill.setDesc("提取现金");
				wxOrderService.saveWidthDrawBill(bill);
				retmap.put("leftMoney", bill.getLeftMoney()+"");
			} else {
				retmap.put("err_code_des", respMap.get("err_code_des"));
			}
		} else {
			retmap.put("err_code_des", respMap.get("return_msg"));
		}
		return JsonUtils.getJSONString(retmap);
	}
	
	private String getLotteryType(String configKey) {
		String lotteryType = "(";
		String key = ConfigCache.getOtherConfigValue(configKey);
		if(StringUtils.isNotBlank(key)) {
			String[] keyes = key.split(";");
			
			for(int i=0;i<keyes.length;i++) {
				lotteryType += keyes[i];
				if(i< keyes.length -1) {
					lotteryType += ",";
				}
			}
			lotteryType += ")";
		}else {
			logger.info(configKey + "未设置！");
		}
		return lotteryType;
	}

}
