package com.tzdr.business.service.failinfo.impl;

import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

import com.tzdr.business.service.failinfo.FreezeFailInfoService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.dao.failinfo.FreezeFailInfoDao;
import com.tzdr.domain.web.entity.FreezeFailInfo;

/**
 * 
 * <p></p>
 * 
 * @author QingLiu
 * @see 冻结失败
 * @version 2.0 2015年2月5日下午7:48:05
 */
@Service
public class FreezeFailInfoServiceImpl extends
		BaseServiceImpl<FreezeFailInfo, FreezeFailInfoDao> implements FreezeFailInfoService {

	@Override
	public PageInfo<FreezeFailInfo> queryDataPage(PageInfo<FreezeFailInfo> page) {
		Map<String,Boolean> orders = new TreeMap<String,Boolean>();
		orders.put("crDatetime", false);
		page.setCurrentPage(page.getCurrentPage() - 1);
		page = this.getEntityDao().queryDataPageByConndition(page, 
				null, null, null, null, orders);
		return page;
	}

	@Override
	public PageInfo<FreezeFailInfo> queryDataPage(
			PageInfo<FreezeFailInfo> page, ConnditionVo connVo) {
		// TODO Auto-generated method stub
		Map<String,Boolean> orders = new TreeMap<String,Boolean>();
		orders.put("crDatetime", false);
		Map<String, String> isLikes=new TreeMap<String, String>();
		if (connVo != null) {
			Object accountObj=connVo.getValue("account");
			if(TypeConvert.objToStrIsNotNull(accountObj)!=null){
				isLikes.put("account", String.valueOf(accountObj));
			}
		}
		page.setCurrentPage(page.getCurrentPage() - 1);
		page = this.getEntityDao().queryDataPageByConndition(page, 
				null, isLikes, null, null, orders);
		return page;
	}

}
