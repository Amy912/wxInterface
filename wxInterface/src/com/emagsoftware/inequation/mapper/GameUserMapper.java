package com.emagsoftware.inequation.mapper;

/**
 * Created by liushijie on 2018/5/15.
 */
public interface GameUserMapper {
    /**
     * 抽奖 更新用户可抽奖和以抽奖次数
     * @param userId
     */
    void updateUserDoLottery(Integer userId);
    
    /**
     * 查看数据库中是否存在未下发的优惠券
     * @return
     */
    Integer getLotteryVoucherCount();
    
    /**
     * 随机分配一优惠券给用户
     * @param recordId
     */
    void updateUserLotteryVoucher(Integer recordId);
    
    
}
