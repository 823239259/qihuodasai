package com.tzdr.domain.app.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import jodd.util.ObjectUtil;

import com.tzdr.common.utils.Dates;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cache.DataDicKeyConstants;
import com.tzdr.domain.constants.Constant;

/**
 * 合买记录vo
 * Created by huangkai on 2016/5/20.
 */
public class UserFTogetherRecordVo  implements Serializable  {


	/**
	 * id
	 */
    private String id;
    /**
     * 用户id
     */
    private String uid;
    
    /**
     * 方案名称
     */
    private String name;

    /**
     * 方案id
     */
    private String tradeId;
    /**
     * 购买份数
     */
    private Integer copies;
    
    /**
     * 退回份数
     */
    private Integer backCopies=0;

    /**
     * 实际支付金额
     */
    private BigDecimal payMoney;
    
    
    /**
     * 退回金额金额
     */
    private BigDecimal backMoney= new BigDecimal(0);
    

    /**
     * 手续费
     */
    private BigDecimal poundage;
    /**
     * 盈亏点数
     */
    private BigDecimal profitLossPoint;

    /**
     * 实现盈亏
     */
    private BigDecimal achieveProfitLoss;

    /**
     * 预计结算金额
     */
    private BigDecimal expectSettlementMoney;

    /**
     * 实际结算金额
     */
    private BigDecimal actualSettlementMoney;

    /**
     * 结算时间
     */
    private Long settlementTime;

    
    /**
     * 涨跌方向（1：涨 2：跌）
     */
    private Integer direction;
    
    /**
     * 当前方案同方向总共操盘份数
     */
    private BigInteger sameDireCopies;
    
    /**
     * 当前方案总共操盘份数
     */
    private BigInteger allCopies;
    
    /**
     * 操盘时间（分钟）
     */
    private Integer operateTime;
    
    
    /**
     * 开仓时间字符串格式显示
     */
    private String openTimeStr;
    
    /**
     * 开仓时间
     */
    private BigInteger openTime;
    
    /**
     * 合买价格
     */
    private BigDecimal price;
    
    /**
     * 合买状态（1：合买中，2：操盘中，3：已结算）
     */
    private Integer status;
    
    
    /**
     *操盘合约
     */
    private String contract;
    
    /**
     * 合买品种（与参数配置中品种一致，冗余）
     */
    private Integer species;
    
    
    /**
     * 满单份数
     */
    private Integer fullCopies;
    
    
    
    /**
     * 相同方向所有合买份数
     */
    private BigInteger  allSameDireCopies;
    
    
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public Integer getCopies() {
        return copies;
    }

    public void setCopies(Integer copies) {
        this.copies = copies;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }


    public BigDecimal getPoundage() {
        return poundage;
    }

    public void setPoundage(BigDecimal poundage) {
        this.poundage = poundage;
    }

    public BigDecimal getProfitLossPoint() {
        return profitLossPoint;
    }

    public void setProfitLossPoint(BigDecimal profitLossPoint) {
        this.profitLossPoint = profitLossPoint;
    }

    public BigDecimal getAchieveProfitLoss() {
        return achieveProfitLoss;
    }

    public void setAchieveProfitLoss(BigDecimal achieveProfitLoss) {
        this.achieveProfitLoss = achieveProfitLoss;
    }

    public BigDecimal getExpectSettlementMoney() {
        return expectSettlementMoney;
    }

    public void setExpectSettlementMoney(BigDecimal expectSettlementMoney) {
        this.expectSettlementMoney = expectSettlementMoney;
    }

    public BigDecimal getActualSettlementMoney() {
        return actualSettlementMoney;
    }

    public void setActualSettlementMoney(BigDecimal actualSettlementMoney) {
        this.actualSettlementMoney = actualSettlementMoney;
    }

    public Long getSettlementTime() {
        return settlementTime;
    }

    public void setSettlementTime(Long settlementTime) {
        this.settlementTime = settlementTime;
    }


	public Integer getBackCopies() {
		return backCopies;
	}

	public void setBackCopies(Integer backCopies) {
		this.backCopies = backCopies;
	}

	public BigDecimal getBackMoney() {
		return backMoney;
	}

	public void setBackMoney(BigDecimal backMoney) {
		this.backMoney = backMoney;
	}

	public Integer getDirection() {
		return direction;
	}

	public void setDirection(Integer direction) {
		this.direction = direction;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigInteger getSameDireCopies() {
		return sameDireCopies;
	}

	public void setSameDireCopies(BigInteger sameDireCopies) {
		this.sameDireCopies = sameDireCopies;
	}

	public BigInteger getAllCopies() {
		return allCopies;
	}

	public void setAllCopies(BigInteger allCopies) {
		this.allCopies = allCopies;
	}

	public Integer getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Integer operateTime) {
		this.operateTime = operateTime;
	}

	public String getOpenTimeStr() {
		if (ObjectUtil.equals(this.openTime,null) 
				|| ObjectUtil.equals(null, this.operateTime)){
			return openTimeStr;
		}
		return Dates.getContainsWeekStr(openTime.longValue(),"MM.dd周 HH:mm")+"-"
		+Dates.format(new Date((openTime.longValue()+this.operateTime*60)*1000),"HH:mm");
	}

	public void setOpenTimeStr(String openTimeStr) {
		this.openTimeStr = openTimeStr;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		if (this.status==Constant.FtogetherGame.JOINT_PURCHASE_STATUS 
				&& !ObjectUtil.equals(this.openTime,null)
				&& Dates.getCurrentLongDate()+Constant.FtogetherGame.FIVE_MINITE_SEC>this.openTime.longValue()){
			return Constant.FtogetherGame.OPERATE_STATUS;
		}
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public BigInteger getOpenTime() {
		return openTime;
	}

	public void setOpenTime(BigInteger openTime) {
		this.openTime = openTime;
	}

	public String getContract() {
		return contract;
	}

	public void setContract(String contract) {
		this.contract = contract;
	}

	public String getSpecies() {
		return CacheManager.getDataMapByKey(DataDicKeyConstants.FTOGETHER_TRADE_TYPE,String.valueOf(this.species));
	}

	public void setSpecies(Integer species) {
		this.species = species;
	}

	public BigInteger getAllSameDireCopies() {
		return allSameDireCopies;
	}

	public void setAllSameDireCopies(BigInteger allSameDireCopies) {
		this.allSameDireCopies = allSameDireCopies;
	}

	public Integer getFullCopies() {
		return fullCopies;
	}

	public void setFullCopies(Integer fullCopies) {
		this.fullCopies = fullCopies;
	}
    
    
}
