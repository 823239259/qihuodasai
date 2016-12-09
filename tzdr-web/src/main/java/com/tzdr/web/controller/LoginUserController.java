package com.tzdr.web.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.utils.ConfUtil;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.web.entity.WUser;
import com.tzdr.web.constants.Constants;
import com.tzdr.web.utils.UserSessionBean;

import jodd.util.Base64;

@Controller
@RequestMapping(value = "/login/user")
public class LoginUserController {
	@Autowired
	private WUserService wUserService;
	/**
	 * 获取用户登录信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getAccount",method = RequestMethod.GET)
	@ResponseBody
	public JsonResult getAccount(HttpServletRequest request,@RequestParam("mobile")String mobile,@RequestParam("check")String check){
		JsonResult result = new JsonResult(false);
		if(check != null){
			//check为0表示登录
			if(check.equals("0")){
				WUser user = wUserService.getWUserByMobile(Base64.decodeToString(mobile));
				result.appendData("data", user);
				result.setSuccess(true);
			}
		}
		return result;
	}
	/**
	 * 获取用户登录信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/redirect/account",method = RequestMethod.GET)
	public String getAccounts(HttpServletRequest request){
		UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constants.TZDR_USER_SESSION);  //获取用户账户信息
		StringBuffer url = new StringBuffer();
		url.append(request.getParameter("url"));
		if(userSessionBean != null){
			byte[] b = null;  
	        String s = null;
    		String mobile = userSessionBean.getMobile();
    		try {
				b = mobile.getBytes("utf-8");
				if (b != null) {  
					s = Base64.encodeToString(b);
					url.append("?o="+s+"&check=0"); 
			   }else{
				   url.append("?check=1");
			   }
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}  
		}else{
			url.append("?check=1");
		}
			return "redirect:"+url.toString()+"";
	}
	/**
	* @Description: TODO(注销操作)
	* @Title: logout
	* @param modelMap
	* @param request
	* @param response
	* @return String    返回类型
	 */
	@RequestMapping("/logout")
	public String  logout(@RequestParam(value = "url",required = false)String url,HttpServletRequest request,HttpServletResponse response){
		request.getSession().invalidate();
		if(url == null){
			url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
		}
		String casServerUrl = ConfUtil.getContext("SSO.casServerUrl");
		/*String callbackUrl = ConfUtil.getContext("SSO.stock.logout.callback.url");*/
		return "redirect:" + casServerUrl + "logout?service=" + url;
	} 
}
