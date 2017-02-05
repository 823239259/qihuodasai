package com.tzdr.business.service.drawBlackList;

import java.util.List;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.domain.vo.DrawBlackListVo;
import com.tzdr.domain.web.entity.DrawBlackList;

/**
 * 
 * <P>title:@DrawBlackListService.java</p>																								
 * <P>Description：</p>
 * <p>Copyright: Copyright (c) 2015 tzdr</p>
 * <p>Company: 上海信宏</p>
 * History：
 * @author:zhangjun
 * @date 2015年5月22日
 * @version 1.0
 */
public interface DrawBlackListService  extends BaseService<DrawBlackList>{

	/**
	 * 查询黑名单数据
	 * @param dataPage
	 * @param vo
	 * @return
	 */
	PageInfo<DrawBlackListVo> queryData(PageInfo<DrawBlackListVo> dataPage,
			DrawBlackListVo vo);
	
	public DrawBlackList getDrawBlackListByUid(String uid);

	/**
	 * 新增
	 * @param uid 
	 * @param reason 原因
	 */
	void saveEntity(String uid, String reason);

	List<DrawBlackList> getEntityByUid(String uid);

	void batchImportData(List<DrawBlackList> drawBlackLists);

}

