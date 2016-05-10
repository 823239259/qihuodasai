package com.tzdr.business.service.freediff;

import java.util.List;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.domain.vo.FreeDiffVo;
import com.tzdr.domain.vo.UserFundMoneyVo;
import com.tzdr.domain.web.entity.FreeDiff;
import com.tzdr.domain.web.entity.FreezeFailInfo;
import com.tzdr.domain.web.entity.UserFund;

/**
 * 
 * <P>title:@CautionMoneyService.java</p>																								
 * <P>Description：</p>
 * <p>Copyright: Copyright (c) 2015 tzdr</p>
 * <p>Company: 上海信宏</p>
 * History：
 * @author:zhangjun
 * @date 2015年3月31日
 * @version 1.0
 */
public interface FreeDiffService extends BaseService<FreeDiff>{

	PageInfo<FreeDiffVo> queryData(PageInfo<FreeDiffVo> dataPage,
			FreeDiffVo freeDiffVo);

	
	public FreeDiff getEntity(String account,String addtime,String type);


	boolean checkAccount(String account);


	String savesByVo(List<FreeDiffVo> vos) throws Exception ;


	FreeDiffVo queryTotalData(FreeDiffVo freeDiffVo);

}

