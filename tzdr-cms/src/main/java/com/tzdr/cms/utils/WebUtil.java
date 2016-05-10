package com.tzdr.cms.utils;

import java.io.PrintWriter;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.StringUtil;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import com.tzdr.cms.constants.Constants;
import com.tzdr.common.api.bbpay.util.BbConfigUtil;
import com.tzdr.common.exception.EmailExceptionHandler;
import com.tzdr.common.web.support.JsonResult;

/**
 * @author zhouchen
 * @version 创建时间：2014年12月8日 下午5:54:13 类说明
 */
public class WebUtil {
	private static Logger log = LoggerFactory.getLogger(WebUtil.class);
	public static void printText(String str, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(str);
			out.flush();
		} catch (Exception e) {
			log.error(("response write: {} error."+e.getMessage()),str);
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
				Constants.LOGIN_SESSION);

		if (null == loginInfo) {
			return true;
		}

		return false;
	}

	/**
	 * 将逗号 隔开的转换为Set
	 * @param param aa,bb,cc
	 * @return
	 */
	public static Set<Long>  convertStringToSet(String param){
		String [] params  = param.split(Constants.SEPERATOR_COMMA);
		if (ArrayUtils.isEmpty(params)){
			return null;
		}
		Set<Long> resultSet = Sets.newHashSet();
		for (String str : params){
			resultSet.add(Long.valueOf(str));
		}
		
		return resultSet;
	}
		
	
	/**
	 * 将逗号 隔开的转换为Set
	 * @param <T>
	 * @param param aa,bb,cc
	 * @return
	 */
	public static <T> String  convertSetToString(Set<T> set){
		if (CollectionUtils.isEmpty(set)){
			return null;
		}
		String  setString = ArrayUtils.toString(set.toArray(),Constants.SEPERATOR_COMMA);
		return setString.substring(1, setString.length()-1);
	}
	
	
	/**
	 * 调用api接口 审核配股宝 提现订单
	 * @param drawlistId
	 * @return
	 */
	public static JsonResult pgbAuditWithdraw(String drawlistId){
		String  url = BbConfigUtil.getContext("pgb.audit.withdraw.url");
		if (StringUtil.isBlank(url)){
			return new JsonResult(false,"配股宝提现审核接口地址有误！");
		}
		
		PostMethod method = new PostMethod(url);
		HttpClient client = new HttpClient();
		JSONObject params = new JSONObject();
		params.put("withdrawId",drawlistId);
		try {
			RequestEntity requestEntity = new StringRequestEntity(
					params.toJSONString(), "application/json", "UTF-8");
			method.setRequestEntity(requestEntity);
			client.executeMethod(method);
			String SubmitResult = method.getResponseBodyAsString();
			log.info(SubmitResult);
			return JSONObject.parseObject(SubmitResult,JsonResult.class);
		} catch (Exception e) {
			log.info("CMS提现审核调用配股宝API审核接口失败",e);
			EmailExceptionHandler.getInstance().HandleExceptionWithData(e, "CMS提现审核调用配股宝API审核接口失败", "pgbAuditWithdraw", "提现记录ID="+drawlistId);
			return new JsonResult(false,"CMS提现审核调用配股宝API审核接口失败，请联系技术部核实后再进行下一步操作！");
		}
	}
}
