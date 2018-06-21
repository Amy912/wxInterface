package com.emagsoftware.inequation.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.emagsoftware.frame.log4j.ILog;
import com.emagsoftware.frame.log4j.Logger;
import com.emagsoftware.frame.utils.ConfigCache;
import com.emagsoftware.inequation.service.GameInfoService;
import com.emagsoftware.inequation.service.GameUserService;
import com.emagsoftware.wx.controller.BaseController;


@Controller
public class GameController  extends BaseController {
	@Logger
	private ILog logger;
	
	@Autowired
	private GameInfoService gameInfoService;
	
	@Autowired
	private GameUserService userService;

	/**
	 * 乱序获取题库中的40道题
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/game/questions")
	public String getGameQuestions(HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		Map<String,Object> params = new HashMap<String,Object>();
		//调整下发答题时间
		String userId = request.getSession().getAttribute("inequationGameUserId") == null ? "" : request.getSession().getAttribute("inequationGameUserId").toString();
		if(StringUtils.isNotBlank(userId)) {
			
			Map<String,Object> user  = this.userService.getUserGameInfoByUserId(userId);
			int successTimes = user.get("successTimes") == null ? 0: (Integer) user.get("successTimes");
			if(successTimes > 4) {
				successTimes = 4;
			}
			params.put("grade", successTimes);
			String level = ConfigCache.getOtherConfigValue("game.grade"+successTimes);
			//TODO 难度系统暂定7个等级
			int gameLevel = 7;
			if(StringUtils.isNotBlank(level)) {
				String[] levels = level.split(";");
				if(levels.length == gameLevel) {
					for(int i=0;i<gameLevel;i++) {
						params.put("n"+i, levels[i]);
					}
					List<Map<String, String>> questions = this.gameInfoService.getQuestions(params);
					response.getWriter().write(JSON.toJSONString(questions));
				}
			}else {
				logger.info("题目难度系数配置有误！");
			}
		}else {
			logger.info("无法获取用户session！");
		}
		return null;
	}
	
	/**
	 * 荣誉榜
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/game/honors")
	public String getGameHonors(HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		//遍历所有用户抽奖记录中抽中娃娃的数据
		Map<String,Object> params = new HashMap<String,Object>();
		String lotteryType = getLotteryType("game.lottery.doll");
		params.put("ltype", lotteryType);
		List<Map<String, Object>> honors = this.gameInfoService.getGameHonors(params);
		response.getWriter().write(JSON.toJSONString(honors));
		return null;
	}
	
	/**
	 * 毅力榜
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/game/backbones")
	public String getGameBackbones(HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		List<Map<String, Object>> backbones = this.gameInfoService.getGameBackbones();
		response.getWriter().write(JSON.toJSONString(backbones));
		return null;
	}
	
	/**
	 * 查找当前用户可领取的娃娃列表
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/game/mydolls")
	public String startLottery(HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		String userId = request.getSession().getAttribute("inequationGameUserId") == null ? "" :request.getSession().getAttribute("inequationGameUserId").toString();
		if(StringUtils.isNotBlank(userId)) {
			Map<String, Object> userInfo = this.userService.getUserGameInfoByUserId(userId);
			int lottery = userInfo.get("lotteryTimes") == null ? 0:(Integer) userInfo.get("lotteryTimes");
			
			//获取娃娃个数
			Map<String,Object> map = new HashMap<String,Object>();
			String lotteryType = getLotteryType("game.lottery.doll");
			map.put("userId", userId);
			map.put("mytype", lotteryType);
			map.put("status", "0");
			List<Map<String, Object>> dolls = this.gameInfoService.getUserLotteryListWithTotal(map);
			String doll = JSON.toJSONString(dolls);
			
			//获取优惠券的个数
			List<Map<String, Object>> vouchers = this.gameInfoService.getUserLotteryVoucherList(map);
			String voucher = JSON.toJSONString(vouchers);
			
			String result = "{\"lotteryTimes\":"+lottery+",\"dollsList\":"+doll+",\"vouchersList\":"+voucher+"}";
			System.out.println(result);
			response.getWriter().write(result);
		}else {
			System.out.println("无法获取用户session！");
			logger.info("无法获取用户session！");
		}
		
		return null;
	}
	
	/**
	 * 获取所有游戏玩家的挑战次数
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/game/totalTimes")
	public String getAllUserTryTimes(HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		int count = this.gameInfoService.getAllUserTryTimes();
		response.getWriter().write(count+"");
		return null;
	}
	
	/**
	 * 获取配置的所有抽奖类型
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/game/lotteryTypes")
	public String getGameLotteryTypes(HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		Map<String,Object> map = new HashMap<String,Object>();
		List<Map<String, Object>> types = this.gameInfoService.getGameLotteryTypes(map);
		response.getWriter().write(JSON.toJSONString(types));
		return null;
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
		System.out.println("lotteryType====="+lotteryType);
		return lotteryType;
	}
	
}
