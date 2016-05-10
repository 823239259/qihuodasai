package com.tzdr.web.controller.help;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.service.stock.StockService;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.web.entity.Stock;
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

	
	@Autowired
	private StockService stockService;
	
	/**
	* @Description: TODO(访问帮助中心页面)
	* @Title: help
	* @param modelMap
	* @param tab        帮助中心选项卡
	* @param leftMenu   帮助中心选项卡左菜单
	* @param request
	* @param response
	* @return String    返回类型
	 * @throws IOException 
	 */
	@RequestMapping(value = "/help")
	public String help(ModelMap modelMap,Integer status,HttpServletRequest request,HttpServletResponse response)  {
//		modelMap.put("tab", tab);
//		modelMap.put("leftMenu", leftMenu);
		if(status==1){
			/*List<Stock> stocks=stockService.findData();
			if(stocks.size()>0){
				request.setAttribute("stocks", stocks);
				int n=stocks.size()%4==0 ? stocks.size()/4 : stocks.size()/4 + 1;
				request.setAttribute("length", n);
				modelMap.put("st", stocks);
				request.setAttribute("size", stocks.size());
				StringBuffer sb=new StringBuffer("{");
				for(Stock s:stocks){
					sb.append("{\"name\":\"").append(s.getName()).append("\",");
					sb.append("\"code\":\"").append(s.getCode()).append("\"},");
				}
				String res=sb.substring(0,sb.length()-1)+"}";
				System.out.println(res);
				request.setAttribute("res", res);
				
			}*/
			return ViewConstants.HelpViewJsp.HELP_INDEX_VIEW;
		}else if(status==2){
			return ViewConstants.HelpViewJsp.HELP_GANGGU_VIEW;
		}else if(status==3){
			return ViewConstants.HelpViewJsp.HELP_DOWNLOAD_VIEW;
		}else if(status==4){
			return ViewConstants.HelpViewJsp.HELP_ANSWER_VIEW;
		}
		else if(status==5){
			return ViewConstants.HelpViewJsp.HELP_AUOUTUS_VIEW;
		}
			return ViewConstants.HelpViewJsp.HELP_INDEX_VIEW;
	}
	
	@RequestMapping(value = "/helpPage/getData")
	@ResponseBody
	public JsonResult getData(Integer status){
		JsonResult js = new JsonResult();
		if(status==1){
			List<Stock> stocks=stockService.findData();
			if(stocks.size()>0){
				int n=stocks.size()%4==0 ? stocks.size()/4 : stocks.size()/4 + 1;
				js.getData().put("st", stocks);
				js.getData().put("n", n);
			}
		}
		return js;
	}
}