package com.tzdr.business.service.generalize;

import java.util.List;

import com.tzdr.domain.web.entity.UserCommission;

/**
* @Description: TODO(佣金收入业务信息管理业务接口)
* @ClassName: CommissionIncomeService
* @author wangpinqun
* @date 2015年1月14日 下午2:57:03
 */
public interface CommissionIncomeService {

	/**
	* @Description: TODO(保存佣金流水信息)
	* @Title: saveUserCommission 佣金流水信息  
	* @param userCommission
	* @return void    返回类型
	 */
	public void saveUserCommission(UserCommission userCommission);
	
	/**
	* @Description: TODO(保存佣金流水信息)
	* @Title: saveUserCommission
	* @param userCommissions   佣金流水信息
	* @return void    返回类型
	 */
	public void saveUserCommission(List<UserCommission> userCommissions);
	
	/**
	* @Description: TODO(佣金核算)
	* @Title: commissionCalculation
	* @return void    返回类型
	 */
	public void commissionCalculation();
}
