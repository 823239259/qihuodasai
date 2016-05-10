package com.tzdr.api.controller;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hundsun.t2sdk.interfaces.T2SDKException;
import com.tzdr.business.service.api.hundsun.HundsunJres;
import com.tzdr.common.exception.EmailExceptionHandler;
import com.tzdr.common.utils.ConfUtil;
import com.tzdr.common.utils.Exceptions;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.dao.DataMapDao;
import com.tzdr.domain.entity.HundsunToken;

/**
 * <B>说明: </B>初始化数据字典数据 保存在缓存中
 * @zhouchen
 * 2016年1月20日
 * @PostConstruct 详情了解此注解：http://comeonbabye.iteye.com/blog/1399472
 */
@Controller
@RequestMapping("/")
public class CacheDataInit {
	public static final Logger log = LoggerFactory
			.getLogger(CacheDataInit.class);
	
	@Autowired
	private  DataMapDao dataMapDao;
	
	@PostConstruct
	private void init() {
		new CacheManager(dataMapDao);
		log.info("==============================CacheManager hundsun data Init Begin==============================");
		//initHundsunData();
		log.info("==============================CacheManager hundsun data Init End==============================");
	}
	/**
	 * 
	 * 初始恒生 combine_id 数据
	 * @throws T2SDKException 
	 */
	public static void initHundsunData() {			
		try {
			HundsunJres.getInstance();	
		} catch (T2SDKException e) {
			log.error("恒生接口初始化失败。", e);
		}
		
	}
	
	
	@RequestMapping("/refreshCache")
	@ResponseBody
	private JsonResult initdata() {
		new CacheManager(dataMapDao);
		return  new JsonResult("刷新成功！");
	}
	
	
	@RequestMapping(value = "/refreshToken")
	@ResponseBody
	public JsonResult  refreshToken(HttpServletRequest request){
		String operatorNo = ConfUtil.getContext("hundsun.manager.operator.no");
		try {
			HundsunToken hundsunToken = HundsunJres.getInstance()
					.findHundsunToken(operatorNo);
			if (hundsunToken != null) {
				HundsunJres.getInstance().LogoutOnce(hundsunToken.getToken());
			}
			HundsunToken newHundsunToken = HundsunJres.getInstance().get(operatorNo);
			
			if (ObjectUtil.equals(null, newHundsunToken) 
					|| StringUtil.isBlank(newHundsunToken.getToken())){
				return new JsonResult(false,"刷新恒生Token失败！");
			}
		} catch (Exception e) {
			String errorMessage = Exceptions.getStackTraceAsString(e);
			log.error(errorMessage);
			EmailExceptionHandler.getInstance().HandleException(e, "刷新恒生Token失败！",errorMessage);
			return new JsonResult(false,"刷新恒生Token失败！");
		}
		return new JsonResult("刷新成功！");
	}
}
