package com.tzdr.web.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.io.FileUtil;
import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import com.tzdr.common.utils.HtmlToDocUtils;
import com.tzdr.common.utils.IpAddressUtils;
import com.tzdr.common.utils.IpUtils;
import com.tzdr.domain.web.entity.GeneralizeVisit;
import com.tzdr.web.constants.Constants;

/**
 * @author zhouchen
 * @version 创建时间：2014年12月8日 下午5:54:13 类说明
 */
public class WebUtil {
	public static void printText(String str, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(str);
			out.flush();
		} catch (Exception e) {

		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	/**
	 * 获取请求是否通过ajax请求的
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isAjax(HttpServletRequest request) {
		String ajax = request.getParameter(Constants.AJAX);
		if (StringUtil.isBlank(ajax)) {
			return false;
		}

		if (StringUtil.equals(ajax, Constants.IS_AJAX)) {
			return true;
		}

		return false;
	}

	/**
	 * 判断session是否失效 即是否登录
	 * 
	 * @param request
	 * @return
	 */
	public static boolean sessionFailure(HttpServletRequest request) {

		Object loginInfo = request.getSession().getAttribute(
				Constants.TZDR_USER_SESSION);

		if (ObjectUtil.equals(null,loginInfo)) {
			return true;
		}

		return false;
	}
	
	/**
	* @Description: TODO(创建配资合同文件)
	* @Title: createContractFile
	* @param prefix   	文件名称   如：contractTemplate.ftl的contractTemplate为文件名称
	* @param suffix     文件后缀  如：contractTemplate.ftl的.ftl为文件后缀  
	* @param params     合同模板动态值
	* @param request
	* @throws IOException
	* @return void    返回类型
	 */
	public static String createContractFile(String prefix,String suffix,List<String> params,String basePath) throws Exception{
		//站点根目录的绝对路径
		//String basePath = request.getSession().getServletContext().getRealPath("/");
		//模板文件绝对路径
		String tempPath = basePath +"static"+File.separator+"views"+File.separator+"template"+File.separator;
		//模板文件内容
		String tempStr = FileUtil.readUTFString(new FileInputStream(tempPath+"contractTemplate.ftl"));
		for (String string : params) {
			tempStr = tempStr.replaceFirst("%s", string);
		}
		//新文件绝对路径
		String filePath = basePath + "upload"+File.separator+"tradeContract"+File.separator;
		//创建配资合同文件
		FileUtil.appendString(new File(filePath+prefix+suffix), tempStr);
		return filePath+prefix+suffix;
	}
	
	/**
	* @Description: TODO(访问记录)
	* @Title: getGeneralizeVisit  
	* @param request
	* @param response
	* @return GeneralizeVisit    返回类型
	 */
	@SuppressWarnings("rawtypes")
	public static GeneralizeVisit getGeneralizeVisit(HttpServletRequest request,HttpServletResponse response){
		GeneralizeVisit generalizeVisit = new GeneralizeVisit();    //访问记录
		String url = request.getRequestURL().toString();
		Enumeration names = request.getParameterNames(); 
		if (names != null) {
			int index = 0;
			while (names.hasMoreElements()) {
				String name = (String) names.nextElement();
				url = index == 0 ? url + "?" : url + "&";
				String value = request.getParameter(name);
				url = url + name + "=" + (value == null ? "" : value);
				if(Constants.GENERALIZE_UID_KEY.equals(name)){        //佣金推广
					Cookie userIdCookie = new Cookie(Constants.TZDR_GENERALIZEUID, value);
					userIdCookie.setMaxAge(Constants.USERID_COOKIEMAX_VALUE);
					response.addCookie(userIdCookie);
					request.getSession().setAttribute(Constants.TZDR_GENERALIZEUID, value);
					generalizeVisit.setGeneralizeId(value);
				}else if(Constants.CHANNEL_KEY.equals(name)){         //渠道推广
					Cookie userIdCookie = new Cookie(Constants.TZDR_CHANNEL, value);
					userIdCookie.setMaxAge(Constants.USERID_COOKIEMAX_VALUE);
					response.addCookie(userIdCookie);
					request.getSession().setAttribute(Constants.TZDR_CHANNEL, value);
					generalizeVisit.setParam(value);
				}
				index++;
			}
		}
		generalizeVisit.setUrl(url);   
		String ip = IpUtils.getIpAddr(request);
		generalizeVisit.setClieantIp(ip);
		generalizeVisit.setCity(IpAddressUtils.getAffiliationCity(ip, "utf-8"));
		generalizeVisit.setCreatedate((new Date().getTime()/1000));
		return generalizeVisit;
	}
}
