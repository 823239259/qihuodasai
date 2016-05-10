package com.tzdr.domain.vo;

import java.io.Serializable;

import com.tzdr.common.utils.SqlColumn;
import com.tzdr.common.utils.TypeConvert;

/**  
 * @Title: UserVerifiedVo.java     
 * @Description: 实名认证-照片审核记录    
 * @author： WangPinQun 
 * @E-mail：wangpinqun@tzdr.com
 * @company： 上海信闳投资管理有限公司重庆分公司
 * @address：重庆市渝北区黄山大道中段70号两江新界3栋13楼
 * @date： 2016年3月14日 下午4:35:09    
 * @version： V1.0  
 */
public class UserVerifiedVo implements Serializable {

	private static final long serialVersionUID = 1L;

	@SqlColumn
	private String id;
	
	@SqlColumn
	private String mobile;
	
	@SqlColumn
	private String tname;
	
	@SqlColumn
	private String idCard;
	
	@SqlColumn
	private Short status;
	
	@SqlColumn(name="updat_user_id")
	private Long updatUserId;
	
	private String updateUsername;
	
	@SqlColumn(name="not_by_reason")
	private String notByReason;
	
	@SqlColumn
	private Long ctime;
	
	@SqlColumn(name="last_submit_verified_time")
	private Long lastSubmitVerifiedTime;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getTname() {
		return tname;
	}
	public void setTname(String tname) {
		this.tname = tname;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getStatus() {
		if(this.status != null){
			return this.status == 1? "未通过" :"通过";
		}
		return "";
	}
	public void setStatus(Short status) {
		this.status = status;
	}
	public String getUpdateUsername() {
		return updateUsername;
	}
	public void setUpdateUsername(String updateUsername) {
		this.updateUsername = updateUsername;
	}
	public String getNotByReason() {
		return notByReason;
	}
	public void setNotByReason(String notByReason) {
		this.notByReason = notByReason;
	}
	public String getCtime() {
		if(this.ctime != null){
			return TypeConvert.long1000ToDatetimeStr(this.ctime);
		}
		return "";
	}
	public void setCtime(Long ctime) {
		this.ctime = ctime;
	}
	public String getLastSubmitVerifiedTime() {
		if(this.lastSubmitVerifiedTime != null){
			return TypeConvert.long1000ToDatetimeStr(this.lastSubmitVerifiedTime);
		}
		return "";
	}
	public void setLastSubmitVerifiedTime(Long lastSubmitVerifiedTime) {
		this.lastSubmitVerifiedTime = lastSubmitVerifiedTime;
	}
	public Long getUpdatUserId() {
		return updatUserId;
	}
	public void setUpdatUserId(Long updatUserId) {
		this.updatUserId = updatUserId;
	}
}

