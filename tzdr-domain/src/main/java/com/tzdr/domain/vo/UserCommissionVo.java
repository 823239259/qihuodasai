package com.tzdr.domain.vo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.tzdr.common.utils.AllowExcel;
import com.tzdr.common.utils.SqlColumn;

/**
* @Description: TODO(佣金报表)
* @ClassName: UserCommissionVo
* @author wangpinqun
* @date 2015年3月18日 下午12:12:31
 */
public class UserCommissionVo implements Serializable{

	/**
	* 
	*/ 
	private static final long serialVersionUID = 1L;
	
	/**
	 * 代理级次
	 */
	@AllowExcel(name="代理级次")
	@SqlColumn
	private BigInteger level;
	
	/**
	 * 用户编号
	 */
	@SqlColumn
	private String userId;
	
	/**
	 * 代理级别
	 */
	@SqlColumn
	@AllowExcel(name="代理级别")
	private String userGrade;
	
	/**
	 * 上一级用户名
	 */
	@SqlColumn
	@AllowExcel(name="上一级用户名")
	private String parentName;

	/**
	 * 用户名
	 */
	@SqlColumn
	@AllowExcel(name="用户名")
	private String userName;
	
	/**
	 * 实名
	 */
	@SqlColumn
	@AllowExcel(name="实名")
	private String trueName;
	
	/**
	 * 返利率
	 */
	@SqlColumn
	@AllowExcel(name="返利率")
	private Double userRebate;
	
	/**
	 * 管理费
	 */
	@SqlColumn
	@AllowExcel(name="管理费")
	private Double userManageMoney;
	
	/**
	 * 本级返利
	 */
	@SqlColumn
	@AllowExcel(name="本级返利")
	private Double userMoney;
	
	/**
	 * 下级管理费
	 */
	@SqlColumn
	@AllowExcel(name="下级管理费")
	private Double subordinateManageMoney;
	
	/**
	 * 下级返利
	 */
	@SqlColumn
	@AllowExcel(name="下级返利")
	private Double subordinateMoney;
	
	/**
	 * 返利合计
	 */
	@SqlColumn
	@AllowExcel(name="返利合计")
	private Double totalMoney;
	
	
	/**
	 * 父编号
	 */
	@SqlColumn
	private String parentId;
	
	
	/**
	 * 用户类型
	 */
	@SqlColumn
	private String userType;
	
	/**
	 * 用户上级类型
	 */
	@SqlColumn
	private String parentUserType;
	
	
	/**
	 * 父节点
	 */
	private UserCommissionVo parentNode;
	
	/**
	 * 子节点
	 */
	private List<UserCommissionVo> childrenNotes = new ArrayList<UserCommissionVo>();
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public BigInteger getLevel() {
		return level;
	}

	public void setLevel(BigInteger level) {
		this.level = level;
	}

	public String getUserGrade() {
		return userGrade;
	}

	public void setUserGrade(String userGrade) {
		this.userGrade = userGrade;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public Double getUserRebate() {
		return userRebate;
	}

	public void setUserRebate(Double userRebate) {
		this.userRebate = userRebate;
	}

	public Double getUserManageMoney() {
		return userManageMoney;
	}

	public void setUserManageMoney(Double userManageMoney) {
		this.userManageMoney = userManageMoney;
	}

	public Double getSubordinateManageMoney() {
		return subordinateManageMoney;
	}

	public void setSubordinateManageMoney(Double subordinateManageMoney) {
		this.subordinateManageMoney = subordinateManageMoney;
	}

	public Double getUserMoney() {
		return userMoney;
	}

	public void setUserMoney(Double userMoney) {
		this.userMoney = userMoney;
	}

	public Double getSubordinateMoney() {
		return subordinateMoney;
	}

	public String getParentUserType() {
		return parentUserType;
	}

	public void setParentUserType(String parentUserType) {
		this.parentUserType = parentUserType;
	}

	public void setSubordinateMoney(Double subordinateMoney) {
		this.subordinateMoney = subordinateMoney;
	}

	public Double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public UserCommissionVo getParentNode() {
		return parentNode;
	}

	public void setParentNode(UserCommissionVo parentNode) {
		this.parentNode = parentNode;
	}

	public List<UserCommissionVo> getChildrenNotes() {
		return childrenNotes;
	}

	public void setChildrenNotes(List<UserCommissionVo> childrenNotes) {
		this.childrenNotes = childrenNotes;
	}
	
	public void addChild(UserCommissionVo userCommissionVo){
		this.childrenNotes.add(userCommissionVo);
	}
}
