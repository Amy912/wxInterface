package com.emagsoftware.wx.wxapi;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HongBao {
	private static double MIN = 0.01;
	/**
	 * 分配红包，规则最小0.01元,红包数大于4时将最大红包放入非1的随机排序中
	 * @param total 红包金额，单位元
	 * @param num 红包数量
	 * @param min 最小金额
	 * @return
	 */
	public static List<Double> hb(double total, int num, double min) {
		List<Double> moneyList = new ArrayList<Double>(); 
		double max = 0;
		int maxIndex = 0;
		for (int i = 1; i < num; i++) {
			double safe_total = (total - (num - i) * min) / (num - i);
			double money = Math.random() * (safe_total - min) + min;
			BigDecimal money_bd = new BigDecimal(money);
			money = money_bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			if(money<MIN){
				money = MIN;
			}
			if(num>4 & money>max){
				max = money;
				maxIndex = i-1;
			}
			total = total - money;
			BigDecimal total_bd = new BigDecimal(total);
			total = total_bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			moneyList.add(money);
		}
		if(num>4 & total>max){
			max = total;
			maxIndex = num-1;
		}
		moneyList.add(total);
		if(num>4){//红包数大于4时将最大红包放入非1的随机排序中
			moneyList.remove(maxIndex);
			int randomIndex = getRandom(2,num-1)-1;
			moneyList.add(randomIndex, max);
		}
		return moneyList;
	}
	private static int getRandom(int min, int max){
	    Random random = new Random();
	    int s = random.nextInt(max) % (max - min + 1) + min;
	    return s;

	}
	public static void main(String[] args) {
		int money = 1;
		int num = 6;
		List<Double> moneyList = hb(money, num, 0.01);//金额，个数，最少值
		double total = 0d;
		String s = "计算sum：";
		for(double m : moneyList){
			System.out.println(m);
			s+=  m+"+";
			total += m;
		}
		System.out.println(s);
		System.out.println(total);
	}
}
