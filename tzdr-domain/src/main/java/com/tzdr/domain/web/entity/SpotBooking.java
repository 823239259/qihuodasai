package com.tzdr.domain.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.tzdr.common.domain.BaseCrudEntity;

/**
 * 现货预约
 * @ClassName SpotBooking
 * @author L.Y
 * @email liuyuan@peigubao.com
 * @date 2015年10月8日
 */
@Entity
@Table(name = "s_spot_booking")
public class SpotBooking extends BaseCrudEntity {
	///^(((13[0-9])|(14[7])|(15[0-9])|(17[0-9])|(18[0-9]))+\d{8})$/
	public static final String MOBILE_PHONE_NUMBER_PATTERN = "^0{0,1}(13[0-9]|15[0-9]|14[0-9]|(17[0-9])|18[0-9])[0-9]{8}$";
	public static final int AUTONYM_MAX_LENGTH = 50;

	/**
	 * 
	 */
	private static final long serialVersionUID = -7822947778822025774L;

	/**
	 * 真实姓名
	 */
	@NotEmpty(message = "{not.null}")
	@Length(max = AUTONYM_MAX_LENGTH, message = "{user.autonym.not.valid}")
	@Column(name = "autonym", nullable = false)
	private String autonym;
	
	/**
	 * 手机号码
	 */
	@NotEmpty(message = "{not.null}")
    @Pattern(regexp = MOBILE_PHONE_NUMBER_PATTERN, message = "{user.mobile.phone.number.not.valid}")
	@Column(name = "mobile", nullable = false)
	private String mobile;
	
	/**
	 * 销售id
	 */
	@Column(name = "sid", nullable = false)
	private String sid;
	
	/**
	 * 预约人id
	 */
	@Column(name = "uid", nullable = false)
	private String uid;
	
	/**
	 * 备注（暂未使用）
	 */
	@Column(name = "remark")
	private String remark;
	
	/**
	 * 状态（暂未使用）0未查看 1已查看
	 */
	@Column(name = "status")
	private short status;
	
	public SpotBooking() {
	}
	
	public SpotBooking(String autonym, String mobile, String uid) {
		super();
		this.autonym = autonym;
		this.mobile = mobile;
		this.uid = uid;
	}

	public SpotBooking(String autonym, String mobile, String sid, String uid) {
		super();
		this.autonym = autonym;
		this.mobile = mobile;
		this.sid = sid;
		this.uid = uid;
	}

	public String getAutonym() {
		return autonym;
	}

	public void setAutonym(String autonym) {
		this.autonym = autonym;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}
}