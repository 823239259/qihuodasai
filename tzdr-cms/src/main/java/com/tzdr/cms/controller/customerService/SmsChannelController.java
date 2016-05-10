/**
 * 
 */
package com.tzdr.cms.controller.customerService;

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
import com.tzdr.business.service.datamap.DataMapService;
import com.tzdr.cms.constants.Constants;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.entity.DataMap;

/**
 * @author uvanix
 * @description 短信通道切换
 * @create-date 2015.11.30
 *
 */
@Controller
@RequestMapping("/admin/smsChannel")
public class SmsChannelController extends BaseCmsController<DataMap> {
	private static Logger log = LoggerFactory.getLogger(SmsChannelController.class);

	@Autowired
	private DataMapService dataMapService;

	@Override
	public BaseService<DataMap> getBaseService() {
		return dataMapService;
	}

	@RequestMapping("list")
	public String list(HttpServletRequest request) {
		return ViewConstants.WuserViewJsp.SMS_CHANNEL_LIST_VIEW;
	}

	@RequestMapping("/edit")
	public String edit(HttpServletRequest request, @RequestParam("fromType") String fromType, @RequestParam(value = "id", required = false) String id) throws Exception {
		request.setAttribute("fromType", fromType);
		if (StringUtil.equals(fromType, Constants.EDIT)) {
			DataMap dataMap = dataMapService.get(id);
			request.setAttribute("dataMap", dataMap);
			return ViewConstants.WuserViewJsp.SMS_CHANNEL_EDIT_VIEW;
		}

		return ViewConstants.ERROR_VIEW;
	}

	/**
	 * 获取短信通道 返回json数据 供combox 使用
	 * 
	 * @param typeKey
	 * @return
	 */
	@RequestMapping("/getSmsChannel")
	@ResponseBody
	public Object getSmsChannel(@RequestParam(value = "typeKey") String typeKey) {
		log.debug("获取短信通道....");
		List<Map.Entry<String, DataMap>> datas = CacheManager.getDataMapSortListByTypeKey(typeKey);
		if (CollectionUtils.isEmpty(datas)) {
			return null;
		}

		JSONArray jsonArray = new JSONArray();
		for (Map.Entry<String, DataMap> mapEntry : datas) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("valueKey", mapEntry.getKey());
			DataMap dataMap = mapEntry.getValue();
			if (ObjectUtil.equals(dataMap, null)) {
				continue;
			}
			jsonObject.put("valueName", dataMap.getValueName());
			jsonArray.add(jsonObject);
		}

		return jsonArray;
	}
}
