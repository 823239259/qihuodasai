package com.tzdr.cms.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.tzdr.cms.utils.WebUtil;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.dao.DataMapDao;
import com.tzdr.domain.entity.DataMap;
import com.tzdr.domain.vo.XhCombobox;

/**
 * 
 * <p></p>
 * @author QingLiu
 * @see 基础组件控制器
 * @version 2.0
 * 2015年2月9日下午7:17:58
 */
@Controller
@RequestMapping("/admin/component")
public class ComponentController {
	
	@Autowired
	DataMapDao dataMapDao;
	
	@RequestMapping(value="dictCombobox")
	public void dictionaryCombobox(HttpServletRequest request,HttpServletResponse resp) {
		Map<String,Object> equals = new HashMap<String,Object>();
		Map<String,Boolean> orders = null;
		String typeKey = request.getParameter("typeKey");
		String orderBy = request.getParameter("orderBy");
		
		if (typeKey != null && !"".equals(typeKey)) {
			equals.put("typeKey", typeKey);
		}
		if (orderBy != null && !"".equals(orderBy)) {
			orders = TypeConvert.strToOrderByType(orderBy);
		}
		try {
			List<DataMap> datas = this.dataMapDao.queryBySimple(equals, null, orders);
			List<XhCombobox> combobox = dataMapToEasyui(datas);
			WebUtil.printText(JSON.toJSONString(combobox), resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private List<XhCombobox> dataMapToEasyui(List<DataMap> datas) {
		List<XhCombobox> comboboxs = new ArrayList<XhCombobox>();
		if (datas != null && datas.size() > 0) {
			for (DataMap dataMap:datas) {
				XhCombobox xh = new XhCombobox(dataMap);
				comboboxs.add(xh);
			}
		}
		return comboboxs;
	}

}
