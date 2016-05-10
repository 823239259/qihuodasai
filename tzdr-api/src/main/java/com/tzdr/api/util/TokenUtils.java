package com.tzdr.api.util;

import java.util.UUID;

import com.tzdr.api.constants.DataConstant;
import com.tzdr.common.utils.Md5Utils;
import com.tzdr.domain.api.entity.ApiToken;

/**
 * <B>说明: </B> token工具类
 * @zhouchen
 * 2015年5月21日 下午1:17:22
 */
public class TokenUtils{
	
	/**
	 * 生成token字符串
	 * @param appToken
	 * @return
	 */
	public static String createToken(ApiToken appToken){
		StringBuffer buf = new StringBuffer();
		buf.append(Md5Utils.hash(appToken.getUserName()));
		buf.append(UUID.randomUUID().toString().replaceAll(DataConstant.UUID_SPLIT,DataConstant.REPLACE_UUID_SPLIT));
		buf.append(Md5Utils.hash(appToken.getIpAddr()));
		return buf.toString();
	}
}
