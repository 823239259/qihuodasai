package com.tzdr.business.service.crudeActive;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.domain.web.entity.CrudeActive;

public interface CrudeActiveService extends BaseService<CrudeActive>{
	
	/**
	 * 通过申请 ，分配名额
	 */
	public void apply(CrudeActive CrudeActive);
	
	/**
	 * 提交方案 ，使用名额
	 */
	public void use(String  uid);
	
	/**
	 * 判断是否重复申请
	 */
	public boolean isAgain(String uid);
	/**
	 * 判断是否过期
	 * @param uid
	 * @return
	 */
	public boolean isOut(String uid);
	
	/**
	 * 判断是否申请过A50活动
	 */
	public  boolean isFtseActive(String uid);
}
