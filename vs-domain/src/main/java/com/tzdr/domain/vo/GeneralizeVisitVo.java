package com.tzdr.domain.vo;

import java.io.Serializable;
import java.math.BigInteger;

/**
* @Description: TODO(访问记录Vo)
* @ClassName: GeneralizeVisitVo
* @author wangpinqun
* @date 2015年1月12日 下午8:17:54
 */
public class GeneralizeVisitVo implements Serializable{

	/**
	* 
	*/ 
	private static final long serialVersionUID = 1L;

	private String clieantIp;
	
	private BigInteger createdate;
	
	private BigInteger  totalCount;
	
	public GeneralizeVisitVo() {
		super();
	}

	public String getClieantIp() {
		return clieantIp;
	}

	public void setClieantIp(String clieantIp) {
		this.clieantIp = clieantIp;
	}

	public BigInteger getCreatedate() {
		return createdate;
	}

	public void setCreatedate(BigInteger createdate) {
		this.createdate = createdate;
	}

	public BigInteger getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(BigInteger totalCount) {
		this.totalCount = totalCount;
	}
}
