package com.tzdr.domain.app.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.tzdr.common.utils.SqlColumn;

/**  
 * @Title: FTradeVo.java     
 * @Description: 期货产品信息VO    
 * @author： WangPinQun 
 * @E-mail：wangpinqun@tzdr.com
 * @company： 上海信闳投资管理有限公司重庆分公司
 * @address：重庆市渝北区黄山大道中段70号两江新界3栋13楼
 * @date： 2016年3月22日 上午9:49:05    
 * @version： V1.0  
 */
public class FTradeVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 交易品种类型，如：type=0(富时A50),type=6(国际原油),type=7(恒生指数),type=8(综合操盘);
	 */
	@SqlColumn(name="type")
	private Integer type;
	
	/**
	 * 交易品种类型名称
	 */
	@SqlColumn(name="type_name")
	private String typeName;
	
	/**
	 * 交易手续费
	 */
	@SqlColumn(name="tran_fees")
	private BigDecimal tradeFees;
	
	/**
	 * 交易品种集合
	 */
	private List<OutDiskVo> outDiskVoList = new ArrayList<OutDiskVo>();

	public Integer getType() {
		if(this.type == 5){
			this.type=0;
		}
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public BigDecimal getTradeFees() {
		return tradeFees;
	}

	public void setTradeFees(BigDecimal tradeFees) {
		this.tradeFees = tradeFees;
	}

	public List<OutDiskVo> getOutDiskVoList() {
		return outDiskVoList;
	}

	public void setOutDiskVoList(List<OutDiskVo> outDiskVoList) {
		this.outDiskVoList = outDiskVoList;
	}
}
