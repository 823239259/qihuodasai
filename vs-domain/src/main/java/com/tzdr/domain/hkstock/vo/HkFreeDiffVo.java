package com.tzdr.domain.hkstock.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import com.tzdr.common.utils.AllowExcel;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cache.DataDicKeyConstants;

import jodd.util.StringUtil;

/**
 * 港股佣金过户费差VO
 * 
 * @Description:
 * @author liuhaichuan
 * @date 2015年10月27日
 *
 */
public class HkFreeDiffVo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;

	/**
	 * 母账户
	 */
	@AllowExcel(name = "母账号")
	private String parentaccount;

	/**
	 * 子交易账户
	 */
	@AllowExcel(name = "交易账户")
	private String account;

	/**
	 * 方案号
	 */
	@AllowExcel(name = "方案号")
	private String groupid;

	/**
	 * 类型
	 */
	private String type;
	@AllowExcel(name = "类型")
	private String typeValue;

	/**
	 * 金额
	 */
	@AllowExcel(name = "金额")
	private Double money;

	/**
	 * 创建时间
	 */
	private BigInteger addtime;
	@AllowExcel(name = "创建时间")
	private String createdate;

	/**
	 * 录入用户
	 */
	@AllowExcel(name = "录入者")
	private String lrr;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	public String getParentaccount() {
		return parentaccount;
	}

	public void setParentaccount(String parentaccount) {
		this.parentaccount = parentaccount;
	}

	public Double getMoney() {
		if (this.money != null) {
			BigDecimal leverMoneyBig = TypeConvert.scale(new BigDecimal(this.money), TypeConvert.SCALE_VALUE);
			if (leverMoneyBig != null) {
				this.money = leverMoneyBig.doubleValue();
			}
		}
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public String getTypeValue() {
		if(StringUtil.isNotBlank(type)){
			typeValue=CacheManager.getDataMapByKey(DataDicKeyConstants.FREETYPE, type);
		}
		return typeValue;
	}

	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}

	public BigInteger getAddtime() {
		return addtime;
	}

	public void setAddtime(BigInteger addtime) {
		this.addtime = addtime;
	}

	public String getCreatedate() {
		if (addtime != null) {
			createdate = Dates.format(Dates.parseLong2Date(addtime.longValue()), Dates.CHINESE_DATE_FORMAT_LINE);
		}
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public String getLrr() {
		return lrr;
	}

	public void setLrr(String lrr) {
		this.lrr = lrr;
	}

}
