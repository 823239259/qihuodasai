package com.tzdr.business.service.ftseActive;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.domain.web.entity.FtseActive;

public interface FtseActiveService extends BaseService<FtseActive>{
	
	/**
	 * 通过申请 ，分配名额
	 */
	public void apply(FtseActive ftseactive);
	
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
}
