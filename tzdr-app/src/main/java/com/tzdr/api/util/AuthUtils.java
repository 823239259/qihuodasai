package com.tzdr.api.util;

import javax.servlet.http.HttpServletRequest;

import jodd.util.Base64;

import com.tzdr.api.constants.DataConstant;
import com.tzdr.api.support.CacheUser;
import com.tzdr.common.utils.Md5Utils;
import com.tzdr.domain.web.entity.WUser;

/**
 * <B>说明: </B> token工具类
 * @zhouchen
 * 2015年5月21日 下午1:17:22
 */
public class AuthUtils{
	
	
	/**
	 * 根据用户登录密码加密种子和手机号生成认证密钥
	 * @param wUser
	 * @return
	 */
	public static String createSecretKey(WUser wUser){
		String secretKey = Md5Utils.hash(new StringBuilder(wUser.getLoginSalt()).append(wUser.getPassword()).append(wUser.getMobile()).toString());
		return secretKey;
	}
	
	
	/**
	 * 根据uid 生成64位加密token
	 * @param uid
	 * @return
	 */
	public static String createToken(String uid){
		String token =Base64.encodeToString(uid);
		return token;
	}
	
	
	/**
	 * 获取缓存中的用户信息
	 * @param request
	 * @return
	 */
	public static CacheUser getCacheUser(HttpServletRequest request){
		String token = request.getHeader(DataConstant.APP_TOKEN);
		return DataConstant.CACHE_USER_MAP.get(token);
	}
	
	/**
	 * 清除缓存信息
	 * @param uid
	 */
	public static void clearCacheUser(String uid){
		DataConstant.CACHE_USER_MAP.remove(createToken(uid));
	}
}
