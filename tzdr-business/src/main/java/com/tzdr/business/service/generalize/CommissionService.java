package com.tzdr.business.service.generalize;

import java.util.List;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.domain.hkstock.vo.HkEarningsVo;
import com.tzdr.domain.vo.UserCommissionVo;
import com.tzdr.domain.web.entity.UserCommission;

/**
* @Description: 用户返利报表管理信息
* @ClassName: CommissionService
* @author wangpinqun
* @date 2015年3月19日 上午9:58:55
 */
public interface CommissionService extends BaseService<UserCommission>{

	
	/**
	* @Description: 返利报表查询
	* @Title: queryUserCommissionVo
	* @param connVo
	* @return List<UserCommissionVo>    返回类型
	 */
	public List<UserCommissionVo> queryUserCommissionVo(ConnditionVo connVo);
	
	/**
	* @Title: PageDataListVo    
	* @Description: 返利报表查询
	* @param dataPage 
	* @param connVo
	* @return
	 */
	public PageInfo<UserCommissionVo> queryPageDataListVo(PageInfo<UserCommissionVo> dataPage,ConnditionVo connVo);
	
	/**
	* @Title: getDataTotalVo    
	* @Description: 获取返利报表合计信息
	* @param connVo
	* @return
	 */
	public UserCommissionVo getDataTotalVo(ConnditionVo connVo);
}
