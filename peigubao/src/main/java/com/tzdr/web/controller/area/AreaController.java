package com.tzdr.web.controller.area;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.service.area.AreaService;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.web.entity.Area;
import com.tzdr.web.controller.securityInfo.SecurityInfoController;

/**
* @Description: TODO(区域信息业务管理控制层)
* @ClassName: AreaController
* @author wangpinqun
* @date 2014年12月30日 上午11:44:02
 */
@Controller
@RequestMapping("/")
public class AreaController{

	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(SecurityInfoController.class);
	
	@Autowired
	private AreaService areaService;
	
	/**
	* @Description: TODO(根据区域父节点编号获取子节点列表信息)
	* @Title: queryAreas
	* @param pid    父节点编号
	* @param modelMap
	* @param request
	* @param response
	* @return
	* @return JsonResult    返回类型
	 */
	@RequestMapping(value = {"/queryareas/{pid}"})
	@ResponseBody
	public JsonResult queryAreas(@PathVariable String pid,ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		JsonResult  jsonResult = new JsonResult(true);
		List<Area> areaList = areaService.findByPidOrderBySortAsc(pid);
		Map<Object, Object> data = new HashMap<Object, Object>();
		data.put("areaList", areaList);
		jsonResult.setData(data);
		return jsonResult;
	}
}
