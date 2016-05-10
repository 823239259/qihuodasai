package com.tzdr.api.support;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.Base64;
import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.tzdr.api.constants.DataConstant;
import com.tzdr.api.constants.ResultStatusConstant;
import com.tzdr.api.util.AuthUtils;
import com.tzdr.api.util.RequestUtils;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.web.entity.WUser;

/**
 * 
 * <B>说明: </B>系统session拦截器，页面构建完成后校验token是否有效
 * @zhouchen
 * 2016年1月20日
 */
public class SessionInterceptor implements HandlerInterceptor {
	
	@Autowired
	private WUserService  wUserService;

	/**
	 * 页面构建完成后执行，类似try catch的finally代码块 可处理页面异常信息，将异常做统一处理。 通过URL判断session时间是否过期
	 * ，是否需要登录
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String token = request.getHeader(DataConstant.APP_TOKEN);
		String secret = request.getHeader(DataConstant.SECRET_KEY);
		if (StringUtil.isBlank(token) || StringUtil.isBlank(secret)){
			RequestUtils.printText(JSON.toJSONString(new JsonResult(false,ResultStatusConstant.AUTH_PARAMS_ERROR,"auth.params.is.null.")), response);
			return false;
		}
		// 解析token 和 密钥进行认证
		String uid = Base64.decodeToString(token);
		/**
		 * 当缓存中没用当前认证用户，需要查询数据库进行认证
		 */
		if (!DataConstant.CACHE_USER_MAP.containsKey(token)){
			return dbAuth(uid, secret, token, response);
		}
		/**
		 * 当用户信息存在缓存中
		 */
		CacheUser  cacheUser  = DataConstant.CACHE_USER_MAP.get(token);
		// 当缓存中存在,但用户信息为空
		if (ObjectUtil.equals(null, cacheUser)){
			return dbAuth(uid, secret, token, response);
		}
		// 当认证密钥匹配则认证成功
		if (StringUtil.equals(secret, cacheUser.getSecret())){
			return true;
		}
		RequestUtils.printText(JSON.toJSONString(new JsonResult(false,ResultStatusConstant.AUTH_PARAMS_ERROR,"auth.secret.error")), response);
		return false;
	}

	/**
	 * 缓存中不存在用户信息进行数据认证
	 * @param uid
	 * @param secret
	 * @param token
	 * @param response
	 * @return
	 */
	private  boolean dbAuth(String uid,String secret,String token,HttpServletResponse response){
		WUser wuser = wUserService.get(uid);
		//用户信息不存在
		if (ObjectUtil.equals(null, wuser)){
			RequestUtils.printText(JSON.toJSONString(new JsonResult(false,ResultStatusConstant.AUTH_PARAMS_ERROR,"user.info.not.exist.")), response);
			return false;
		}
		//认证密钥错误
		String secretKey = AuthUtils.createSecretKey(wuser);
		if (!StringUtil.equals(secret, secretKey)){
			RequestUtils.printText(JSON.toJSONString(new JsonResult(false,ResultStatusConstant.AUTH_PARAMS_ERROR,"auth.secret.error")), response);
			return false;
		}
		
		// 认证成功,用户信息放入缓存
		DataConstant.CACHE_USER_MAP.put(token,new CacheUser(wuser, secretKey));
		return true;
	}
	/**
	 * 在controller执行完成后，页面构建之前执行 可执行通用操作，例如：每个页面的头部、底部内容
	 */
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	/**
	 * 在进入controller之前执行 返回值true表示放行，false会阻止执行 此处异常获取 跟配置的异常页面跳转存在冲突 只有取消
	 * spring-servlert 的配置 这里的exception 才能有效的获取
	 * 
	 * 如果不取消 异常ex 全部为null 同步请求的 会跳转到异常页面，ajax请求的会返回error
	 * 
	 */
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
