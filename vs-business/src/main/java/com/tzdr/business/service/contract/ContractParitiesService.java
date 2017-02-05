package com.tzdr.business.service.contract;

import java.util.Map;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.vo.ContractParitiesVo;
import com.tzdr.domain.web.entity.ContractParities;

/**
 * 
 * @author LiuYang
 *
 * 2015年10月20日 上午10:57:57
 */
public interface ContractParitiesService extends BaseService<ContractParities> {
	/**
	 * 查询合约配置列表
	 * @param easyUiPage
	 * @param searchParams
	 * @return
	 * @throws Exception
	 */
	public PageInfo<Object> getPageDatas(EasyUiPageInfo easyUiPage,Map<String, Object> searchParams) throws Exception;
	
	/**
	 * 新增方案配置，或者更新方案配置
	 * @param simpleConfig
	 * @return
	 * @throws Exception
	 */
	public JsonResult saveOrUpdateConfig(ContractParitiesVo ContractParities) throws Exception;
	
}
