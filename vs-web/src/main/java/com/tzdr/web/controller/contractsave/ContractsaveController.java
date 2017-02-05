package com.tzdr.web.controller.contractsave;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tzdr.business.service.contractsave.ContractsaveService;
import com.tzdr.web.constants.ViewConstants;

/**
 * 显示查看页面
 * <P>title:@ContractsaveController.java</p>																								
 * <P>Description：</p>
 * <p>Copyright: Copyright (c) 2015 tzdr</p>
 * <p>Company: 上海信宏</p>
 * History：
 * @author:zhangjun
 * @date 2015年5月9日
 * @version 1.0
 */
@Controller
@RequestMapping("/contractsave")
public class ContractsaveController {
	public static final Logger logger = LoggerFactory.getLogger(ContractsaveController.class);
	@Autowired
	private ContractsaveService contractsaveService;
	
	@RequestMapping(value = "/viewInfo")
	public String viewInfo(String saveId,HttpServletResponse response,HttpServletRequest request){
		if(StringUtil.isNotBlank(saveId)){
			String url=contractsaveService.getUrlById(Long.valueOf(saveId));
			request.setAttribute("url", url);
		}
		return ViewConstants.ContractsaveJsp.VIEW_INFO;
	}
	

}

