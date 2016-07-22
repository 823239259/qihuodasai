package com.tzdr.common.api.payease.vo;

import java.io.Serializable;

import com.tzdr.common.utils.Dates;

/**
 * 支付时所需参数
 * @author zhouchen
 * 2016年4月29日 下午4:12:08
 */
public class PayEaseParams implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 支付接口地址
	 */
	private String payurl;
	/**
	 * 1． 商户编号(v_mid)     
	 * 说明：不可为空值，以初始单上所填商户编号为准。         
	 */
	private String vmid;
	
	/**
	 * 支付方式
	 */
	private String vpmode;
	
	/**
	 * 2． 订单编号(v_oid)    
	 * 说明：不可为空值，首信易支付订单编号格式统一为：     
	 * 订单生成日期(yyyymmdd)-商户编号-商户流水号
	        例如：19990720-888-12345。商户流水号为数字，每日内不重复即可。
	        注：订单编号所有字符总和不可超过64位，否则首信易支付平台拒绝接受。      
	 */
	private String vorid;
	
	/**
	 * 收货人姓名(v_rcvname)      
	  说明：不可为空值，统一用商户编号的值代替。
	*/
	private String vrcvname;
	
	/**4． 收货人地址(v_rcvaddr)       
	说明：不可为空值，总长不超过128个字符。
	*/
	private String vrcvaddr;
	
	/**5． 收货人电话(v_rcvtel)        
	说明：不可为空值，总长不超过32个字符。
	*/
	private String vrcvtel;
	
	/**6． 收货人邮政编码(v_rcvpost)   
	说明：不可为空值，总长不超过10个字符。
	*/
	private String vrcvpost;
	/**7． 订单总金额(v_amount)       
	说明：不可为空值，单位：元，小数点后保留两位，如13.45
	*/
	private String vamount;
	/**8． 订单产生日期(v_ymd)        
	说明：不可为空值，长度为8位，格式为yyyymmdd
	*/
	private String vymd=Dates.format(Dates.CHINESE_DATE_FORMAT_LONG);
	
	/**9． 配货状态(v_orderstatus)       
	说明：商户配货状态，0为未配齐，1为已配齐
	*/
	private int vorderstatus=1;
	/**10．订货人姓名(v_ordername)     
	       说明：总长不超过64个字符
	*/
	private String vordername;
	/**11．支付币种(v_moneytype)       
	说明：0为人民币，1为美元
	*/
	private String vmoneytype="0";
	/**12．返回商户页面地址(v_url)         
	说明：为消费者完成购物后返回的商户页面，此地址为页面连接方式的返回地址，在此地址放置接收程序用于接收银行返回的支付确认消息（参数格式参照文档第二部分首信易支付订单支付结果页面返回接口）。URL参数是以http://开头的完整URL地址。
	*/
	private String vurl;
	/**
	13．订单数字指纹(v_md5info)  	详情见md5说明
	*/
	private String vmd5info;
	
	
	
	/****************临时传递参数********************/
	
	/**
	 * 银行简称
	 */
	private String abbreviation;
	
	/**
	 * 充值用户ip地址
	 */
	private String userIp;
	
	
	/**
	 * 支付方式
	 */
	private String type="2";
	
	/**
	 * 充值状态
	 */
	private Integer status;
	
	
	/**
	 * 商户密钥
	 */
	private String secret;
	
	
	/************************************/
	public String getVmid() {
		return vmid;
	}
	public void setVmid(String vmid) {
		this.vmid = vmid;
	}
	public String getVorid() {
		return vorid;
	}
	public void setVorid(String vorid) {
		this.vorid = vorid;
	}
	public String getVrcvname() {
		return vrcvname;
	}
	public void setVrcvname(String vrcvname) {
		this.vrcvname = vrcvname;
	}
	public String getVrcvaddr() {
		return vrcvaddr;
	}
	public void setVrcvaddr(String vrcvaddr) {
		this.vrcvaddr = vrcvaddr;
	}
	public String getVrcvtel() {
		return vrcvtel;
	}
	public void setVrcvtel(String vrcvtel) {
		this.vrcvtel = vrcvtel;
	}
	public String getVrcvpost() {
		return vrcvpost;
	}
	public void setVrcvpost(String vrcvpost) {
		this.vrcvpost = vrcvpost;
	}
	public String getVamount() {
		return vamount;
	}
	public void setVamount(String vamount) {
		this.vamount = vamount;
	}
	public String getVymd() {
		return vymd;
	}
	public void setVymd(String vymd) {
		this.vymd = vymd;
	}
	public int getVorderstatus() {
		return vorderstatus;
	}
	public void setVorderstatus(int vorderstatus) {
		this.vorderstatus = vorderstatus;
	}
	public String getVordername() {
		return vordername;
	}
	public void setVordername(String vordername) {
		this.vordername = vordername;
	}
	public String getVmoneytype() {
		return vmoneytype;
	}
	public void setVmoneytype(String vmoneytype) {
		this.vmoneytype = vmoneytype;
	}
	public String getVurl() {
		return vurl;
	}
	public void setVurl(String vurl) {
		this.vurl = vurl;
	}
	public String getVmd5info() {
		return vmd5info;
	}
	public void setVmd5info(String vmd5info) {
		this.vmd5info = vmd5info;
	}
	public String getPayurl() {
		return payurl;
	}
	public void setPayurl(String payurl) {
		this.payurl = payurl;
	}
	
	
	public String getVpmode() {
		return vpmode;
	}
	public void setVpmode(String vpmode) {
		this.vpmode = vpmode;
	}
	public PayEaseParams() {
		
	}
	
	public PayEaseParams(String vmid,String vamount,String sretUrl,String vpmode) {
		this.vmid=vmid;
		this.vrcvname=vmid;
		this.vrcvaddr=vmid;
		this.vrcvtel=vmid;
		this.vrcvpost=vmid;
		this.vordername=vmid;
		this.vamount = vamount;
		this.vurl=sretUrl;
		this.vpmode=vpmode;
	}
	public String getAbbreviation() {
		return abbreviation;
	}
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}
	public String getUserIp() {
		return userIp;
	}
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public PayEaseParams(String vpmode, String vamount, String userIp,
			Integer status,String abbreviation) {
		super();
		this.vpmode = vpmode;
		this.vamount = vamount;
		this.userIp = userIp;
		this.status = status;
		this.abbreviation=abbreviation;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	
	public PayEaseParams(String vmid, String secret) {
		super();
		this.vmid = vmid;
		this.secret = secret;
	}  
	
	
}
