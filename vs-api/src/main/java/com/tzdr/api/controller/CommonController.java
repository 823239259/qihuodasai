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
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.api.constants.ResultStatusConstant;
import com.tzdr.api.support.ApiResult;
import com.tzdr.business.service.datamap.DataMapService;
import com.tzdr.domain.entity.DataMap;

/**  
 * @Title: CommController.java     
 * @Description: 公共接口业务信息管理累  
 * @author： WangPinQun 
 * @E-mail：wangpinqun@tzdr.com
 * @company： 上海信闳投资管理有限公司重庆分公司
 * @address：重庆市渝北区黄山大道中段70号两江新界3栋13楼
 * @date： 2016年3月23日 下午1:37:58    
 * @version： V1.0  
 */
@Controller
@RequestMapping("/")
public class CommonController {
	
	public static final Logger logger = LoggerFactory.getLogger(CommonController.class);
	
	@Autowired
	private DataMapService dataMapService;
	
	/**
	* @Title: getVersion    
	* @Description: 获取app应用当前版本信息
	* @param modelMap
	* @param request
	* @param response
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/getVersion" , method = RequestMethod.POST)
	@ResponseBody
	public ApiResult getVersion(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws Exception{
	
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<DataMap> dataList = dataMapService.findByTypeKey("versionManage");
		String version = CollectionUtils.isEmpty(dataList) ? null :dataList.get(0).getValueName();
		dataMap.put("version", version); //当前app当前版本号信息
		return new ApiResult(true,ResultStatusConstant.SUCCESS,null,dataMap);
	}
}
