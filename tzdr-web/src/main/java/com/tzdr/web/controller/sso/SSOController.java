package com.tzdr.web.controller.sso;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tzdr.common.utils.ConfUtil;


@Controller
@RequestMapping("/")
public class SSOController {
	
	
	@RequestMapping("/indexSSO")
	public String  indexSSO(HttpServletRequest request,HttpServletResponse response) throws Exception{
		return "redirect:/";
	}
	
	/**
	 * 单点登出
	 * @param casURL
	 * @param sourceCode
	 * @param backData
	 * @param key
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/logoutSSO")
	public String  logoutSSO(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//注销session
		request.getSession().invalidate();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
		String casUrl=ConfUtil.getContext("SSO.casServer.logoutUrl");
		String returnUrl = ConfUtil.getContext("SSO.stock.logout.callback.url");
		return "redirect:"+casUrl+"?service="+URLEncoder.encode((StringUtils.isNotEmpty(returnUrl) ? returnUrl : basePath), "UTF-8");
	} 

	/**
	 * 随心操盘
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toDaySSO")
	public String toDay(HttpServletRequest request, HttpServletResponse response){
		return "redirect:/day?enter=0";
	}
	
	/**
	 * 单点登录后进入股指操盘——期指随心乐
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toFutureIndexSSO")
	public String toFutureIndex(HttpServletRequest request, HttpServletResponse response){
		return "redirect:/future/index";
	}
	
	/**
	 * 单点登录后进入股指操盘——富时A50
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toFtseIndexSSO")
	public String toFtseIndexSSO(HttpServletRequest request, HttpServletResponse response){
		return "redirect:/ftse/index";
	}
	
	/**
	 * 单点登录后进入股指操盘——国际原油
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toCrudeOilIndexSSO")
	public String toCrudeOilIndexSSO(HttpServletRequest request, HttpServletResponse response){
		return "redirect:/crudeoil/index";
	}
	
	/**
	 * 单点登录后进入股指操盘——恒生指数
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toHSIIndexSSO")
	public String toHSIIndexSSO(HttpServletRequest request, HttpServletResponse response){
		return "redirect:/hsi/index";
	}
	
	/**
	 * 单点登录后进入股指操盘——期指天天乐
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toFutureDayIndexSSO")
	public String toFutureDayIndex(HttpServletRequest request, HttpServletResponse response){
		return "redirect:/future/day_index";
	}
	
	/**
	 * 单点登录后进入沪金
	 * @param request
	 * @param response
	 * @return
	 */
	
	@RequestMapping(value = "/toProductGoldIndexSSO")
	public String toGoldIndexSSO(HttpServletRequest request, HttpServletResponse response){
		return "redirect:/product/gold_index";
	}
	/**
	 * 单点登录后进入沪银
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toProductSliverIndexSSO")
	public String toSliverIndexSSO(HttpServletRequest request, HttpServletResponse response){
		return "redirect:/product/sliver_index";
	}
	@RequestMapping(value = "/toProductCopperIndexSSO")
	public String toCopperIndexSSO(HttpServletRequest request, HttpServletResponse response){
		return "redirect:/product/copper_index";
	}
	/**
	 * 单点登录后进入沪铜
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toProductRubberIndexSSO")
	public String toRubberIndexSSO(HttpServletRequest request, HttpServletResponse response){
		return "redirect:/product/rubber_index";
	}
	/**
	 * 单点登录后进入橡胶
	 * @param request
	 * @param response
	 * @return
	 */
	
	
	/**
	 * 单点登录后进入港股操盘
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toHkStockSSO")
	public String toHkStock(HttpServletRequest request, HttpServletResponse response){
		return "redirect:/hkstock/index";
	}
	
	/**
	 * 单点登录后进入港股操盘
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toIfredEnvelopSSO")
	public String toIfredEnvelop(HttpServletRequest request, HttpServletResponse response){
		return "redirect:/topic/ifRedenvelope/";
	}
	
	/**
	 * 单点登录进入喊单PK大赛
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toshoutOrderPKSSO")
	public String toshoutOrderPK(HttpServletRequest request, HttpServletResponse response){
		return "redirect:/topic/shoutOrderPK/";
	}
	
	/**
	 * 外盘开箱壕礼
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/tokxhlSSO")
	public String tokxhlSSO(HttpServletRequest request, HttpServletResponse response){
		return "redirect:/sifactivites/kxhl";
	}
	
		/**
	 * 单点登录进入股票合买
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toTogetherSSO")
	public String toTogether(HttpServletRequest request, HttpServletResponse response){
		return "redirect:/together/index.jsp";
	}
	 /**
	 * 单点登录进入公共合买列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toPublicTogetherSSO")
	public String toPublicTogether(HttpServletRequest request, HttpServletResponse response){
		return "redirect:/together/list";
	} 
	
	
	
	/**
	 * 新年股票活动
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toNewYearStockSSO")
	public String toNewYearStockSSO(HttpServletRequest request, HttpServletResponse response){
		return "redirect:/topic/newYearStock/";
	}
	
	/**
	 * 新年期货活动
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toNewYearFutureSSO")
	public String toNewYearFutureSSO(HttpServletRequest request, HttpServletResponse response){
		return "redirect:/topic/newYearFuture/";
	}
	/**
	 * 外盘期货综合
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toOutDiskIndexSSO")
	public String toOutDiskIndexSSO(HttpServletRequest request, HttpServletResponse response){
		return "redirect:/outDisk/index";
	}
	
	/**
	 * 商品期货综合
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toCommodityAllIndexSSO")
	public String toComprehensiveCommodityIndexSSO(HttpServletRequest request, HttpServletResponse response){
		return "redirect:/commodity/index";
	}
	
	
	
}
