package com.emagsoftware.frame.utils;

/**
 * 系统常量
 * Created by liushijie on 2018/5/14.
 */
public class Constant {


    /**
     * 优先奖项和次数
     */
    public static enum LotteryAndCountRelEnum {
        t2(6, 2),
        t3(7, 1),
        t4(8, 1);

        LotteryAndCountRelEnum(Integer type, Integer count) {
            this.type = type;
            this.count = count;
        }

        private Integer type;//奖品类型
        private Integer count;//次数

        public Integer getType() {
            return type;
        }

        public Integer getCount() {
            return count;
        }

        //根据type获取value
        public static Integer getValueByType(Integer type) {
            LotteryAndCountRelEnum[] enums = LotteryAndCountRelEnum.values();
            for (int i = 0; i < enums.length; i++) {
                if (enums[i].getType().equals(type)) {
                    return enums[i].getCount();
                }
            }
            return 0;
        }
    }
}
