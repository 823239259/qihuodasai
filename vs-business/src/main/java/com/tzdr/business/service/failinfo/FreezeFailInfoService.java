package com.tzdr.business.service.failinfo;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.domain.web.entity.FreezeFailInfo;
import com.tzdr.domain.web.entity.WUser;

/**
 * 
 * <p></p>
 * @author QingLiu
 * @see 冻结失败信息
 * @version 2.0
 * 2015年2月5日下午7:45:43
 */
public interface FreezeFailInfoService extends BaseService<FreezeFailInfo> {
	
	/**
	 * 分页查询
	 * @param page DataPage<WUser>
	 * @return DataPage<WUser>
	 */
	public PageInfo<FreezeFailInfo> queryDataPage(PageInfo<FreezeFailInfo> page);
	
	/**
	 * 含查询条件的分页查询
	 * @param page
	 * @param connVo
	 * @return
	 */
	public PageInfo<FreezeFailInfo> queryDataPage(PageInfo<FreezeFailInfo> page, ConnditionVo connVo);

}
