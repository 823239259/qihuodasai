package com.tzdr.domain.app.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.tzdr.common.utils.SqlColumn;

/**  
 * @Title: OutDiskVo.java     
 * @Description: 期货交易品种Vo    
 * @author： WangPinQun 
 * @E-mail：wangpinqun@tzdr.com
 * @company： 上海信闳投资管理有限公司重庆分公司
 * @address：重庆市渝北区黄山大道中段70号两江新界3栋13楼
 * @date： 2016年3月22日 上午9:53:12    
 * @version： V1.0  
 */
public class OutDiskVo implements Serializable{

	private static final long serialVersionUID = -4693834438577977997L;

	/**
	 * 交易品种 如：0:富时A50；6:国际原油;7:恒指期货;9:迷你道指;10:迷你纳指;11:迷你标普;12:德国DAX;13:日经225;14:小恒指
     * 15:美黄金;16:H股指数;17:小H股指数;18:美铜;19:美白银;20:小原油;21:迷你德国DAX指数;
	 */
	@SqlColumn(name="trade_type")
	private Integer tradeType;
	
	/**
	 * 主力合约
	 */
	@SqlColumn(name="main_contract")
	private String mainContract;
	
	/**
	 * 价格
	 */
	@SqlColumn(name="price")
	private BigDecimal price;
	
	/**
	 * 交易时间段
	 */
	@SqlColumn(name="trad_time")
	private String tradTime;

	public Integer getTradeType() {
		return tradeType;
	}

	public void setTradeType(Integer tradeType) {
		this.tradeType = tradeType;
	}

	public String getMainContract() {
		return mainContract;
	}

	public void setMainContract(String mainContract) {
		this.mainContract = mainContract;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getTradTime() {
		return tradTime;
	}

	public void setTradTime(String tradTime) {
		this.tradTime = tradTime;
	}
	
}
