package com.tzdr.web.controller.help;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tzdr.web.constants.ViewConstants;

/**
* @Description: TODO(帮助业务管理信息类)
* @ClassName: HelpController
* @author wangpinqun
* @date 2015年1月29日 上午9:14:38
 */
@Controller
@RequestMapping("/")
public class HelpController {

	
	/**
	* @Description: TODO(访问帮助中心页面)
	* @Title: help
	* @param modelMap
	* @param tab        帮助中心选项卡
	* @param leftMenu   帮助中心选项卡左菜单
	* @param request
	* @param response
	* @return String    返回类型
	 */
	@RequestMapping(value = "/help")
	public String help(ModelMap modelMap,String tab,String leftMenu,HttpServletRequest request,HttpServletResponse response){
		modelMap.put("tab", tab);
		modelMap.put("leftMenu", leftMenu);
		return ViewConstants.HelpViewJsp.HELP_INDEX_VIEW;
	}

}
