package com.tzdr.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.api.constants.DataConstant;
import com.tzdr.api.constants.ResultStatusConstant;
import com.tzdr.api.support.ApiResult;
import com.tzdr.business.app.service.BannerService;
import com.tzdr.domain.app.vo.BannerVo;

/**  
 * @Title: BannerController.java     
 * @Description: banner业务信息管理类    
 * @author： WangPinQun 
 * @E-mail：wangpinqun@tzdr.com
 * @company： 上海信闳投资管理有限公司重庆分公司
 * @address：重庆市渝北区黄山大道中段70号两江新界3栋13楼
 * @date： 2016年3月22日 下午1:37:05    
 * @version： V1.0  
 */
@Controller
@RequestMapping("/banner")
public class BannerController {
	
	public static final Logger logger = LoggerFactory.getLogger(BannerController.class);

	@Autowired
	private BannerService bannerService;
	
	/**
	* @Title: list    
	* @Description: 获取banner信息
	* @param modelMap
	* @param request
	* @param response
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/list" , method = RequestMethod.POST)
	@ResponseBody
	public ApiResult list(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws Exception{
	
		List<BannerVo> dataList = bannerService.findBannerVos(DataConstant.BANNER_TYPE);
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("bannerList", dataList);
		
		return new ApiResult(true,ResultStatusConstant.SUCCESS,null,dataMap);
	}
	
}
