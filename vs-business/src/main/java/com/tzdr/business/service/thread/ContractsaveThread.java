package com.tzdr.business.service.thread;

import com.tzdr.business.service.contractsave.ContractsaveService;
import com.tzdr.common.utils.SpringUtils;
import com.tzdr.domain.web.entity.UserTrade;

/**
 * @Description:
 * @ClassName: ContractsaveThread.java
 * @author Lin Feng
 * @date 2015年5月4日
 */
public class ContractsaveThread extends Thread {
	
	private UserTrade trade;
	private String basePath;

	public ContractsaveThread(UserTrade trade, String basePath) {
		this.trade = trade;
		this.basePath = basePath;
	}

	public void run() {		
		ContractsaveService ContractsaveService=SpringUtils.getBean("contractsaveService");
		ContractsaveService.createSafeData(trade, basePath);
	}
}
