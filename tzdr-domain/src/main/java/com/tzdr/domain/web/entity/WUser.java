package com.tzdr.domain.web.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.RandomStringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tzdr.common.domain.BaseEntity;


@Entity
@Table(name="w_user")
public class WUser extends BaseEntity {
	
		// 用户类型
		public static class UserType {
			/**
			 * 上海信闳-平台
			 */
			public static final String PLAT = "-1";
			/**
			 * 上海信闳-web渠道
			 */
			public static final String WEB_ACCESS = "-2";
			/**
			 * 上海信闳-cms渠道
			 */
			public static final String CMS_ACCESS = "-3";
			/**
			 * web注册用户
			 */
			public static final String WEB_REGIST = "0";
			/**
			 * 配资用户
			 */
			public static final String TRADE = "1";
		}
		
		// 活动类型  活动类型 0：没有活动，1：8800活动
		public static class ActivityType {
			/**
			 * 没有活动
			 */
			public static final  int NO = 0;
			/**
			* 8800活动
			*/
			public static final int ACTIVITY_8800  = 1;
			/**
			 * 6600活动
			 */
			public static final int ACTIVITY_6600  = 2;
		}



	/**
	 * 
	 */ 
	private static final long serialVersionUID = 1L;

	private String login;
	private String password;
	private String newpassword;

	/**
	 * 加密密码时使用的种子
	 */
	private String loginSalt;
	private String uname;
	private String email;
	private String mobile;
	private String regIp;
	private String regCity;
	private Long ctime;
	private String groupid;
	private Short info;
	private Short work;
	private Short family;
	//资产总额
	private Double fund=0.0;
	private Double acctBal=0.0;
	//冻结金额
	private Double frzBal=0.0;
	//可用金额
	private Double avlBal=0.0;
	private Double pnrAvlBal=0.0;
	private Double income=0.0;
	private Double rePay=0.0;
	private Double score=0.0;
	private Double score1=0.0;
	private Double score2=0.0;
	private Short isDel;
	private Short salary;
	private Short isadress;
	private Short edu;
	private Short car;
	private Short house;
	private Short credit;
	private Integer oktime;
	private Short marry;
	private Short other;
	private Short examine;
	private String getpwdstr;
	private Short sendmsg;
	private Integer scoreStatus;
	private Double xumoney=0.0;
	private String scode;
	private Integer sendMailTime;
	private Long lastLoginTime;
	private Long lastbeforeloginTime;
	private String lastLoginIp;
	private Boolean mailsendmsg;
	private Boolean isSendMail;
	private Integer isupdate;
	private String smsService;
	private Integer insleep;
	
	/**
	 * '1: web 2:wap 3:bbs';
	 */
	private int source;
	private String keyword;
	
	/**
	 * 累积总操盘金额(提现免手续费额度)
	 */
    private Double countOperateMoney=0.0;
	
	public Long getLastbeforeloginTime() {
		return lastbeforeloginTime;
	}

	public void setLastbeforeloginTime(Long lastbeforeloginTime) {
		this.lastbeforeloginTime = lastbeforeloginTime;
	}

	private Integer isMobile;
	private Double daiAmount=0.0;

	/**
	 * 余额不足 提醒状态0：未通知   1：已通知 2：未接通
	 */
	private Integer noticeStatus=0;
	
	/**
	 * 累计支出保证金
	 */
	private Double totalDeposit=0.0;

	/**
	 * 累计配资金额 
	 */
	private Double totalLending=0.0;

	/**
	 * 用户类型  -1:上海信闳-平台,-2:上海信闳-web渠道 ,-3:上海信闳-cms渠道, 0:web注册用户  ,1:配资用户
	 */
	private String userType;

	/**
	 * 累计管理费
	 */
	private Double totalManagerMo=0.0;

	/**
	 * 累计利息
	 */
	private Double totalInterestMo=0.0;

	/**
	 * 返点
	 */
	private Double rebate=0.0;

	/**
	 * 累计佣金
	 */
	private Double totalCommission=0.0;
	
	/**
	 * 默认返点
	 */
	private Double subordinateDefaultRebate=0.0;
	
	/**
	 * 用户级别
	 */
	private Integer userGrade;
	
	private Long userId;

	/**
	 * 父节点
	 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	 @JoinColumn(name = "parent_id")
	@JsonIgnore
	private WUser parentNode;
	/**
	 * 子结点列表
	 */
	@OneToMany(mappedBy="parentNode",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JsonIgnore
	private List<WUser> childs;

	@OneToOne(mappedBy="wuser",fetch=FetchType.EAGER)
	@JsonIgnore
	private UserVerified userVerified;
	
	/**
	 * 活动类型 0：没有活动，1：8800活动  2:6600活动
	 */
	private int activityType=0;
	
	/**
	 * 推广编号
	 */
	private String generalizeId;
	
	/**
	 * 渠道
	 */
	private String channel;
	
	/**
	 * 层次
	 */
	private Integer level;
	
	/**
	 * 劵张数
	 */
	@Transient 
	private Integer volumeNum;
	
	/**
	 * 劵值
	 */
	@Transient 
	private Double volumePrice;
	
	/**
	 * 活动类型 0：没有活动，1：8800活动,2:6600活动
	 * @return Integer
	 */
	public int getActivityType() {
		return activityType;
	}

	/**
	 * 
	 * @param activityType Integer
	 */
	public void setActivityType(int activityType) {
		this.activityType = activityType;
	}

	/**
	 * 添加子结点
	 * @param wuser WUser
	 */
	public void add(WUser wuser) {
		if (childs == null) {
			childs = new ArrayList<WUser>();
		}
		wuser.setParentNode(this);
		this.childs.add(wuser);
	}

	public WUser(){

	}

	public WUser(String id){
		this.id=id;
	}


	/**
	 * 生成新的种子
	 */
	public void randomSalt() {
		setLoginSalt(RandomStringUtils.randomAlphanumeric(10));
	}

	@Column(name="login")
	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Column(name="password")
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name="newpassword")
	public String getNewpassword() {
		return this.newpassword;
	}

	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}

	@Column(name="login_salt")
	public String getLoginSalt() {
		return this.loginSalt;
	}

	public void setLoginSalt(String loginSalt) {
		this.loginSalt = loginSalt;
	}

	@Column(name="uname")
	public String getUname() {
		return this.uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	@Column(name="email")
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name="mobile")
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name="reg_ip")
	public String getRegIp() {
		return this.regIp;
	}

	public void setRegIp(String regIp) {
		this.regIp = regIp;
	}

	@Column(name="reg_city")
	public String getRegCity() {
		return regCity;
	}

	public void setRegCity(String regCity) {
		this.regCity = regCity;
	}
	
	@Column(name="ctime")
	public Long getCtime() {
		return this.ctime;
	}

	public void setCtime(Long ctime) {
		this.ctime = ctime;
	}

	@Column(name="groupid", length=32)
	public String getGroupid() {
		return this.groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	@Column(name="info")
	public Short getInfo() {
		return this.info;
	}

	public void setInfo(Short info) {
		this.info = info;
	}

	@Column(name="work")
	public Short getWork() {
		return this.work;
	}

	public void setWork(Short work) {
		this.work = work;
	}

	@Column(name="family")
	public Short getFamily() {
		return this.family;
	}

	public void setFamily(Short family) {
		this.family = family;
	}

	@Column(name="Fund")
	public Double getFund() {
		return this.fund;
	}

	public void setFund(Double fund) {
		this.fund =fund;
	}

	@Column(name="AcctBal")
	public Double getAcctBal() {
		return this.acctBal;
	}

	public void setAcctBal(Double acctBal) {
		this.acctBal = acctBal;
	}

	@Column(name="FrzBal")
	public Double getFrzBal() {
		return this.frzBal;
	}

	public void setFrzBal(Double frzBal) {
		this.frzBal = frzBal;
	}

	@Column(name="AvlBal")
	public Double getAvlBal() {
		return this.avlBal;
	}

	public void setAvlBal(Double avlBal) {
		this.avlBal = avlBal;
	}

	@Column(name="PnrAvlBal")
	public Double getPnrAvlBal() {
		return this.pnrAvlBal;
	}

	public void setPnrAvlBal(Double pnrAvlBal) {
		this.pnrAvlBal = pnrAvlBal;
	}

	@Column(name="Income")
	public Double getIncome() {
		return this.income;
	}

	public void setIncome(Double income) {
		this.income = income;
	}

	@Column(name="RePay")
	public Double getRePay() {
		return this.rePay;
	}

	public void setRePay(Double rePay) {
		this.rePay = rePay;
	}

	@Column(name="score")
	public Double getScore() {
		return this.score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	@Column(name="score1")
	public Double getScore1() {
		return this.score1;
	}

	public void setScore1(Double score1) {
		this.score1 = score1;
	}

	@Column(name="score2")
	public Double getScore2() {
		return this.score2;
	}

	public void setScore2(Double score2) {
		this.score2 = score2;
	}

	@Column(name="is_del")
	public Short getIsDel() {
		return this.isDel;
	}

	public void setIsDel(Short isDel) {
		this.isDel = isDel;
	}

	@Column(name="salary")
	public Short getSalary() {
		return this.salary;
	}

	public void setSalary(Short salary) {
		this.salary = salary;
	}

	@Column(name="isadress")
	public Short getIsadress() {
		return this.isadress;
	}

	public void setIsadress(Short isadress) {
		this.isadress = isadress;
	}

	@Column(name="edu")
	public Short getEdu() {
		return this.edu;
	}

	public void setEdu(Short edu) {
		this.edu = edu;
	}

	@Column(name="car")
	public Short getCar() {
		return this.car;
	}

	public void setCar(Short car) {
		this.car = car;
	}

	@Column(name="house")
	public Short getHouse() {
		return this.house;
	}

	public void setHouse(Short house) {
		this.house = house;
	}

	@Column(name="credit")
	public Short getCredit() {
		return this.credit;
	}

	public void setCredit(Short credit) {
		this.credit = credit;
	}

	@Column(name="oktime")
	public Integer getOktime() {
		return this.oktime;
	}

	public void setOktime(Integer oktime) {
		this.oktime = oktime;
	}

	@Column(name="marry")
	public Short getMarry() {
		return this.marry;
	}

	public void setMarry(Short marry) {
		this.marry = marry;
	}

	@Column(name="other")
	public Short getOther() {
		return this.other;
	}

	public void setOther(Short other) {
		this.other = other;
	}

	@Column(name="examine")
	public Short getExamine() {
		return this.examine;
	}

	public void setExamine(Short examine) {
		this.examine = examine;
	}

	@Column(name="getpwdstr", length=100)
	public String getGetpwdstr() {
		return this.getpwdstr;
	}

	public void setGetpwdstr(String getpwdstr) {
		this.getpwdstr = getpwdstr;
	}

	@Column(name="sendmsg")
	public Short getSendmsg() {
		return this.sendmsg;
	}

	public void setSendmsg(Short sendmsg) {
		this.sendmsg = sendmsg;
	}

	@Column(name="score_status")
	public Integer getScoreStatus() {
		return this.scoreStatus;
	}

	public void setScoreStatus(Integer scoreStatus) {
		this.scoreStatus = scoreStatus;
	}

	@Column(name="xumoney")
	public Double getXumoney() {
		return this.xumoney;
	}

	public void setXumoney(Double xumoney) {
		this.xumoney = xumoney;
	}

	@Column(name="scode", length=32)
	public String getScode() {
		return this.scode;
	}

	public void setScode(String scode) {
		this.scode = scode;
	}

	@Column(name="send_mail_time")
	public Integer getSendMailTime() {
		return this.sendMailTime;
	}

	public void setSendMailTime(Integer sendMailTime) {
		this.sendMailTime = sendMailTime;
	}

	@Column(name="last_login_time")
	public Long getLastLoginTime() {
		return this.lastLoginTime;
	}

	public void setLastLoginTime(Long lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	@Column(name="last_login_ip")
	public String getLastLoginIp() {
		return this.lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	@Column(name="mailsendmsg")
	public Boolean getMailsendmsg() {
		return this.mailsendmsg;
	}

	public void setMailsendmsg(Boolean mailsendmsg) {
		this.mailsendmsg = mailsendmsg;
	}

	@Column(name="is_send_mail")
	public Boolean getIsSendMail() {
		return this.isSendMail;
	}

	public void setIsSendMail(Boolean isSendMail) {
		this.isSendMail = isSendMail;
	}

	@Column(name="isupdate")
	public Integer getIsupdate() {
		return this.isupdate;
	}

	public void setIsupdate(Integer isupdate) {
		this.isupdate = isupdate;
	}

	@Column(name="sms_service")
	public String getSmsService() {
		return this.smsService;
	}

	public void setSmsService(String smsService) {
		this.smsService = smsService;
	}

	@Column(name="insleep")
	public Integer getInsleep() {
		return this.insleep;
	}

	public void setInsleep(Integer insleep) {
		this.insleep = insleep;
	}

	@Column(name="is_mobile")
	public Integer getIsMobile() {
		return this.isMobile;
	}

	public void setIsMobile(Integer isMobile) {
		this.isMobile = isMobile;
	}

	@Column(name="dai_amount")
	public Double getDaiAmount() {
		return this.daiAmount;
	}

	public void setDaiAmount(Double daiAmount) {
		this.daiAmount = daiAmount;
	}
    //0是注册用户，1是配资用户
	@Column(name="user_type")
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	@Column(name="total_manager_mo")
	public Double getTotalManagerMo() {
		return totalManagerMo;
	}

	public void setTotalManagerMo(Double totalManagerMo) {
		this.totalManagerMo = totalManagerMo;
	}

	@Column(name="total_interest_mo")
	public Double getTotalInterestMo() {
		return totalInterestMo;
	}

	public void setTotalInterestMo(Double totalInterestMo) {
		this.totalInterestMo = totalInterestMo;
	}


	public UserVerified getUserVerified() {
		return userVerified;
	}

	public void setUserVerified(UserVerified userVerified) {
		this.userVerified = userVerified;
	}

	@Column(name="total_deposit")
	public Double getTotalDeposit() {
		return this.totalDeposit;
	}

	public void setTotalDeposit(Double totalDeposit) {
		this.totalDeposit = totalDeposit;
	}

	@Column(name="total_lending")
	public Double getTotalLending() {
		return this.totalLending;
	}

	public void setTotalLending(Double totalLending) {
		this.totalLending = totalLending;
	}

	@Column(name="rebate")
	public Double getRebate() {
		return this.rebate;
	}

	public void setRebate(Double rebate) {
		this.rebate = rebate;
	}

	@Column(name="total_commission")
	public Double getTotalCommission() {
		return this.totalCommission;
	}

	public void setTotalCommission(Double totalCommission) {
		this.totalCommission = totalCommission;
	}

	@Column(name="subordinate_default_rebate")
	public Double getSubordinateDefaultRebate() {
		return subordinateDefaultRebate;
	}

	public void setSubordinateDefaultRebate(Double subordinateDefaultRebate) {
		this.subordinateDefaultRebate = subordinateDefaultRebate;
	}

	@Column(name="user_grade")
	public Integer getUserGrade() {
		return userGrade;
	}

	public void setUserGrade(Integer userGrade) {
		this.userGrade = userGrade;
	}

	public WUser getParentNode() {
		return parentNode;
	}

	public void setParentNode(WUser parentNode) {
		this.parentNode = parentNode;
	}

	public List<WUser> getChilds() {
		return childs;
	}

	public void setChilds(List<WUser> childs) {
		this.childs = childs;
	}

	@Column(name="sys_user_id")
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getNoticeStatus() {
		return noticeStatus;
	}

	public void setNoticeStatus(Integer noticeStatus) {
		this.noticeStatus = noticeStatus;
	}

	@Column(name="generalize_id")
	public String getGeneralizeId() {
		return generalizeId;
	}

	public void setGeneralizeId(String generalizeId) {
		this.generalizeId = generalizeId;
	}

	@Column(name = "channel")
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	@Column(name = "level")
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getVolumeNum() {
		return volumeNum;
	}

	public void setVolumeNum(Integer volumeNum) {
		this.volumeNum = volumeNum;
	}

	public Double getVolumePrice() {
		return volumePrice;
	}

	public void setVolumePrice(Double volumePrice) {
		this.volumePrice = volumePrice;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Double getCountOperateMoney() {
		return countOperateMoney;
	}

	public void setCountOperateMoney(Double countOperateMoney) {
		this.countOperateMoney = countOperateMoney;
	}

	
	
}