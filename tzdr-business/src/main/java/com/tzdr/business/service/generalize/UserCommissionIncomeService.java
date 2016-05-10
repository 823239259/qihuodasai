package com.tzdr.business.service.generalize;

import java.util.List;

import com.tzdr.domain.web.entity.UserCommission;
import com.tzdr.domain.web.entity.UserFund;
import com.tzdr.domain.web.entity.UserFundFail;

/**
* @Description: 用户佣金收入业务信息管理业务接口
* @ClassName: UserCommissionIncomeService
* @author wangpinqun
* @date 2015年06月25日 下午2:57:03
 */
public interface UserCommissionIncomeService {
	
	/**
	* @Description: 根据类型和管理费时间获取佣金流水单
	* @Title: findUserCommissions
	* @param type   佣金流水信息
	* @param manageFeeTime   佣金流水信息
	* @return void    List<UserCommission>
	 */
	public List<UserCommission> findUserCommissions(int type,long manageFeeTime);

	/**
	* @Description: 用户保存佣金流水信息
	* @Title: saveUserCommission
	* @param userCommissions   佣金流水信息
	* @return void    返回类型
	 */
	public void saveBatchUserCommission(List<UserCommission> userCommissions);
	
	/**
	* @Description: 保存用户佣金流行单信息
	* @Title: saveUserCommission
	* @param runBatchTime 跑批时间【系统自动跑批该值为空，手动跑批该值为跑批设置时间】
	* @return List<UserCommission> 返回类型
	 */
	
	public List<UserCommission> saveUserCommission(Long runBatchTime);
	
	/**
	 * @Description: 结算用户佣金收入情况
	 * @Title: settlementUserCommission   
	 * @param userCommissionList    用户佣金流行单信息 
	 * @param runBatchTime 跑批时间【系统自动跑批该值为空，手动跑批该值为跑批设置时间】
	 * @return List<UserFund>    返回类型  用户佣金收入
	 */
	public List<UserFund> settlementUserCommission(List<UserCommission> userCommissionList,Long runBatchTime);
	
	/**
	 * @Description: 保存结算用户佣金收入信息(更新用户帐号、保存资金明细-佣金收入)
	 * @Title: saveCommissionUserFund
	 * @param userFund   资金明细-佣金收入
	 * @return void    返回类型
	 */
	public void saveCommissionUserFund(UserFund userFund);
	
	/**
	 * @Description: 保存佣金失败信息
	 * @Title: saveUserFundFail
	 * @param userFundFail   失败佣金收入信息
	 * @return void    返回类型
	 */
	public void saveUserFundFail(UserFundFail userFundFail);
}
