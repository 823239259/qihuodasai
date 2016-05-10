package com.tzdr.common.web;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.BaseEntity;

/**
 * base控制器以后备用
 * 
 * 
 * @author Lin Feng
 * 
 * @param <T>
 * @param <M>
 */
public abstract class BaseController<M extends BaseEntity> {
	
	/**
	 * 注入service
	 * @return
	 */
	public abstract BaseService<M> getBaseService();
	
}
