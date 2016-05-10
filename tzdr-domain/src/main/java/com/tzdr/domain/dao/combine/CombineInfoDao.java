package com.tzdr.domain.dao.combine;


import java.util.List;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.CombineInfo;

/**
 * 
 * @zhouchen
 * 2014年12月26日
 */
public interface CombineInfoDao extends BaseJpaDao<CombineInfo, String> {
	
	public CombineInfo  findByAssetIdAndFundAccount(Long assetId,String fundAccount);
	
	public CombineInfo  findByAssetId(Long assetId);
	
	
	public List<CombineInfo>  findByFundAccount(String fundAccount);
}
