package com.tzdr.business.service.contractsave;

import java.util.List;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.domain.hkstock.entity.HkUserTrade;
import com.tzdr.domain.hkstock.vo.ContractHkTradeSafeVo;
import com.tzdr.domain.vo.ContractTradeSafeVo;
import com.tzdr.domain.vo.ContractsaveVo;
import com.tzdr.domain.web.entity.Contractsave;
import com.tzdr.domain.web.entity.UserTrade;


public interface ContractsaveService extends BaseService<Contractsave>{

	PageInfo<ContractsaveVo> queryData(PageInfo<ContractsaveVo> dataPage,
			ContractsaveVo vo);

	List<ContractTradeSafeVo> createTradeVo(List<UserTrade> userTradeList,String basePath);
	
	/**
	 * 创建合同
	 * @param trade
	 * @param basePath
	 * @return
	 */
	Contractsave createSafeData(UserTrade trade,String basePath);

	String getUrlById(Long valueOf);

	
	List<ContractHkTradeSafeVo> createHkTradeVo(List<HkUserTrade> userTradeList,String basePath);
	
	public Contractsave createSafeData(HkUserTrade trade,String basePath);
	
}
