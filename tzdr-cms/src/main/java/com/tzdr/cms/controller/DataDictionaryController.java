package com.tzdr.cms.controller;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tzdr.business.service.datamap.DataMapService;
import com.tzdr.cms.constants.Constants;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.config.ActivityConfig;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.entity.DataMap;

/**
 * 数据字典  获取数据Controller
 * @zhouchen
 * 2014年12月27日
 */
@Controller
@RequestMapping("/admin/dataDic")
public class DataDictionaryController extends BaseCmsController<DataMap>
{
	private static Logger log = LoggerFactory.getLogger(DataDictionaryController.class);
	
	@Autowired
	private DataMapService dataMapService;
	/**
	 * 获取数据字典数据  返回json数据 供combox 使用
	 */
	@RequestMapping("/getDicDatas")
	@ResponseBody
	public Object getDicDatas(@RequestParam(value="typeKey") String typeKey){
		log.debug("获取字典表数据....");		
		List<Map.Entry<String, DataMap>> datas =  CacheManager.getDataMapSortListByTypeKey(typeKey);
		if (CollectionUtils.isEmpty(datas)){
			return null;
		}
		
		JSONArray  jsonArray = new JSONArray();
		for (Map.Entry<String, DataMap> mapEntry:datas){
			JSONObject  jsonObject  = new JSONObject();
			jsonObject.put("valueField", mapEntry.getKey());
			DataMap dataMap = mapEntry.getValue();
			if (ObjectUtil.equals(dataMap, null)){
				continue;
			}
			jsonObject.put("textField", dataMap.getValueName());
			jsonArray.add(jsonObject);
		}
		
		return jsonArray;
	}

	@Override
	public BaseService<DataMap> getBaseService() {
		return dataMapService;
	}
	
	@Override
	public void setResourceIdentity(String resourceIdentity) {
		super.setResourceIdentity("sys:system:datamap");
	}
	@RequestMapping(value = "updateActivityDate",method =  RequestMethod.POST)
	@ResponseBody
	public String updateActivityDate(HttpServletRequest request){
		SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:ss");
		//更新活动时间
		List<DataMap> dataMapsStart = dataMapService.findByTypeKey("activityOnlineStartTime");
		if(dataMapsStart != null && dataMapsStart.size() > 0){
			String dataTime =  dataMapsStart.get(0).getValueName();
			try {
				List<DataMap> dataMapsEnd = dataMapService.findByTypeKey("activityOnlineEndTime");
				if(dataMapsEnd != null && dataMapsEnd.size() > 0){
					String dateEndTime = dataMapsEnd.get(0).getValueName();
					Date endDate = df.parse(dateEndTime);
					Date startDate =  df.parse(dataTime);
					long start = startDate.getTime();
					long end = endDate.getTime();
					ActivityConfig.map.put("activity_onLineEndTime", String.valueOf(end));
					ActivityConfig.map.put("activity_onLineStartTime", String.valueOf(start));
					System.out.println(ActivityConfig.map);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	@RequestMapping(value="list")
	public String list(HttpServletRequest  request){
		return ViewConstants.OpertinalConfigViewJsp.DATA_MAP_LIST_VIEW;
	}
	
	@RequestMapping(value = "/edit")
	public String edit(HttpServletRequest request,@RequestParam("fromType") String fromType,
			@RequestParam(value="id",required=false) String id) throws Exception 
	{
		//更新活动时间
		request.setAttribute("fromType",fromType);
		if (StringUtil.equals(fromType,Constants.ADD)){
			return ViewConstants.OpertinalConfigViewJsp.DATA_MAP_EDIT_VIEW;			
		}
		
		if (StringUtil.equals(fromType,Constants.EDIT)){
			DataMap  dataMap  =  dataMapService.get(id);
			request.setAttribute("dataMap",dataMap);
			return ViewConstants.OpertinalConfigViewJsp.DATA_MAP_EDIT_VIEW;	
		}
		
		return ViewConstants.ERROR_VIEW;
	}
}