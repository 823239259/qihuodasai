package com.tzdr.domain.web.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseCrudEntity;
/**
 * 
 * 类说明      国际综合价格设置
 * @author  zhaozhao
 * @date    2016年2月22日下午5:41:24
 * @version 1.0
 */
@Entity
@Table(name="w_out_disk_price")
public class OutDiskPrice extends BaseCrudEntity{


	private static final long serialVersionUID = 5688032662262628021L;
	
	/**
	 * 交易品种
	 * 业务类型【0.富时A50  6.原油    7. 恒指   9.迷你道指、10.迷你纳指、11.迷你标普、12.德国DAX、13.日经225、
	 *       14小恒指、15美黄金】
	 */
	private Integer tradeType;
	
	
	
	/**
	 * 主力合约
	 */
	private String mainContract;
	
	/**
	 * 价格
	 */
	private BigDecimal price;


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
	
	
}
