package com.tzdr.api.util;

import java.util.regex.Pattern;

import jodd.util.StringUtil;


/**
 * 
 * <B>说明: </B>密码相关工具类
 * @zhouchen
 * 2016年1月20日
 */
public class PasswordUtils {

	/**
	 * 提现密码规则
	 * 6-16位字母和数字组成  不能全是字母，不能全是数字
	 */
	public static final String WITHDRAW_PASSWORD_PATTERN="^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
	/**
	 * 验证密码格式  6-16 位字母数字组合
	 * @param password
	 * @return
	 */
	public static boolean validatePwd(String password) {
		if (StringUtil.isBlank(password)){
			return false;
		}
		
		return Pattern.matches(WITHDRAW_PASSWORD_PATTERN, password);
	}
	
}
