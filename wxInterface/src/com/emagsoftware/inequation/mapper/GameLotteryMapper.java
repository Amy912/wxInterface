package com.emagsoftware.inequation.mapper;

import com.emagsoftware.inequation.bean.GameLottery;

import java.util.List;

/**
 * Created by liushijie on 2018/5/14.
 */
public interface GameLotteryMapper {
    /**
     * 条件查询list
     *
     * @param searchCondition
     * @return
     */
    List<GameLottery> getList(GameLottery searchCondition);
    
}
