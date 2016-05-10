package com.tzdr.cms.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jodd.util.ObjectUtil;

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
import com.tzdr.business.cms.service.resource.ResourceService;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.domain.cms.entity.resource.Resource;

@Controller
@RequestMapping("/admin/resource")
public class ResourceController extends BaseCmsController<Resource> {
	private static Logger log = LoggerFactory
			.getLogger(ResourceController.class);

	@Autowired
	private ResourceService resourceService;

	@RequestMapping(value = "/getTreeData")
	@ResponseBody
	public Object getTreeData(HttpServletRequest request,
			@RequestParam(value = "parentId") Long parentId) {
		List<Resource> resources = resourceService.findByParentId(parentId);

		if (CollectionUtils.isEmpty(resources)) {
			return null;
		}

		if (ObjectUtil.equals(parentId, Long.valueOf(0L))) {
			resources = resourceService
					.findByParentId(resources.get(0).getId());
		}

		if (CollectionUtils.isEmpty(resources)) {
			return null;
		}
		JSONArray array = new JSONArray();
		for (Resource resource : resources) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", resource.getId());
			jsonObject.put("text", resource.getName());
			if (resource.isHasChildren()) {
				jsonObject.put("state", "closed");
			}
			array.add(jsonObject);
		}
		return array;
	}

	@Override
	public BaseService<Resource> getBaseService() {
		return resourceService;
	}
}
