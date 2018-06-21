package com.emagsoftware.inequation.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 抽奖奖项
 * Created by liushijie on 2018/5/11.
 */
public class GameLottery implements Serializable {
    private Integer id;
    private Integer parentId;//父级id
    private Integer isLastNode;//是否末节点：0否1是
    private Integer type;//奖品类型
    private String imageUrl;//奖品图片
    private String content;//奖品内容
    private BigDecimal probability;//中奖概率
    private Integer effectiveHours;//有效时间(小时)，-1永久有效
    private String backup;//备用字段

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public BigDecimal getProbability() {
        return probability;
    }

    public void setProbability(BigDecimal probability) {
        this.probability = probability;
    }

    public Integer getEffectiveHours() {
        return effectiveHours;
    }

    public void setEffectiveHours(Integer effectiveHours) {
        this.effectiveHours = effectiveHours;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getIsLastNode() {
        return isLastNode;
    }

    public void setIsLastNode(Integer isLastNode) {
        this.isLastNode = isLastNode;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

	public String getBackup() {
		return backup;
	}

	public void setBackup(String backup) {
		this.backup = backup;
	}
    
}
