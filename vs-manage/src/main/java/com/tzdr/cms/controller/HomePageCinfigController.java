package com.tzdr.cms.controller;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tzdr.business.service.homepagecinfig.HomePageCinfigService;
import com.tzdr.cms.constants.Constants;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.domain.cache.CacheHomePageManager;
import com.tzdr.domain.entity.HomePageCinfig;

/**
 * 首页配置  获取数据Controller
 * @WangPinQun
 * 2015年11月23日
 */
@Controller
@RequestMapping("/admin/homepagecinfig")
public class HomePageCinfigController extends BaseCmsController<HomePageCinfig>
{
	private static Logger log = LoggerFactory.getLogger(HomePageCinfigController.class);
	
	@Autowired
	private HomePageCinfigService homePageCinfigService;
	/**
	 * 获取数据字典数据  返回json数据 供combox 使用
	 */
	@RequestMapping("/getDicDatas")
	@ResponseBody
	public Object getDicDatas(@RequestParam(value="typeKey") String typeKey){
		log.debug("获取字典表数据....");		
		List<Map.Entry<String, HomePageCinfig>> datas =  CacheHomePageManager.getDataMapSortListByTypeKey(typeKey);
		if (CollectionUtils.isEmpty(datas)){
			return null;
		}
		
		JSONArray  jsonArray = new JSONArray();
		for (Map.Entry<String, HomePageCinfig> mapEntry:datas){
			JSONObject  jsonObject  = new JSONObject();
			jsonObject.put("valueField", mapEntry.getKey());
			HomePageCinfig dataMap = mapEntry.getValue();
			if (ObjectUtil.equals(dataMap, null)){
				continue;
			}
			jsonObject.put("textField", dataMap.getValueName());
			jsonArray.add(jsonObject);
		}
		
		return jsonArray;
	}

	@Override
	public BaseService<HomePageCinfig> getBaseService() {
		return homePageCinfigService;
	}
	
	@Override
	public void setResourceIdentity(String resourceIdentity) {
		super.setResourceIdentity("sys:operationalConfig:homepagecinfig");
	}
	
	@RequestMapping(value="list")
	public String list(HttpServletRequest  request){
		return ViewConstants.HomepageCinfigJsp.HOME_PAGE_CINFIG_LIST_VIEW;
	}

	
	@RequestMapping(value = "/edit")
	public String edit(HttpServletRequest request,@RequestParam("fromType") String fromType,
			@RequestParam(value="id",required=false) String id) throws Exception 
	{
		request.setAttribute("fromType",fromType);
		if (StringUtil.equals(fromType,Constants.ADD)){
			return ViewConstants.HomepageCinfigJsp.HOME_PAGE_CINFIG_EDIT_VIEW;			
		}
		
		if (StringUtil.equals(fromType,Constants.EDIT)){
			HomePageCinfig  dataMap  =  homePageCinfigService.get(id);
			request.setAttribute("dataMap",dataMap);
			return ViewConstants.HomepageCinfigJsp.HOME_PAGE_CINFIG_EDIT_VIEW;	
		}
		
		return ViewConstants.ERROR_VIEW;
	}
}