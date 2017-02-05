package com.tzdr.business.service.volume;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.domain.vo.VolumeDeductibleVo;
import com.tzdr.domain.web.entity.VolumeDeductible;

/**
 * 
 * 
 * <p></p>
 * @author LiuQing
 * @see
 * @version 2.0
 * 2015年4月27日下午5:43:09
 */
public interface VolumeDeductibleService extends BaseService<VolumeDeductible> {
	
	/**
	 * 
	 * @param page PageInfo<VolumeDeductibleVo>
	 * @param connVo ConnditionVo
	 * @return PageInfo<VolumeDeductibleVo>
	 */
	public PageInfo<VolumeDeductibleVo> queryDataListVo(PageInfo<VolumeDeductibleVo> pageInfo,
			ConnditionVo connVo);
	
	public String queryMaxTypeCode();

	
}

