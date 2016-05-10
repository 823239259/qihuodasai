package com.tzdr.common.utils;

import java.util.UUID;

import jodd.util.StringUtil;


/**
 * 手机号和邮箱用星号显示
 * <P>title:@StringCodeUtils.java</p>																								
 * <P>Description：</p>
 * <p>Copyright: Copyright (c) 2014 tzdr</p>
 * <p>Company: 上海信闳</p>
 * History：
 * @author:zhangjun
 * @date 2014年12月31日
 * @version 1.0
 */
public class StringCodeUtils {

	/**
	 * 身份证编号用星号
	 * @param idCard
	 * @return
	 * @date 2014年12月31日
	 * @author zhangjun
	 */
	public static  String buildIdCard(String idCard) {
		//身份证号码构建
		if(StringUtil.isNotBlank(idCard)){
			idCard=buildStr(idCard, 2, 4, 2);
			
		}
			return idCard;
	}
	
	/**
	 * 将字符串构建成*号
	 * @param val
	 * @return
	 * @date 2014年12月25日
	 * @author zhangjun
	 */

	private  static String buildStr(String val,int prefixlength,int mlength,int suffixlength){
		String prefix=val.substring(0,prefixlength);
		String str="";
		for(int i=0;i<val.length()-mlength;i++){
			str+="*";
		}
		String suffix=val.substring(val.length()-suffixlength,val.length());
		String card=prefix+str+suffix;
		return card;
	}

	

	/**
	 * 邮箱用星号显示
	 * @param email
	 * @return
	 * @date 2014年12月29日
	 * @author zhangjun
	 */
	public static String buildEmail(String email) {
		String prefix=email.substring(0,3);
		String str="";
		if(email.length()>3){
			for(int i=0;i<email.lastIndexOf("@")-3;i++){
				str+="*";
			}
		}
		String suffix=email.substring(email.lastIndexOf("@"),email.length());
		return prefix+str+suffix;
	}

	/**
	 * 手机号码用星号显示
	 * @param email
	 * @return
	 * @date 2014年12月29日
	 * @author zhangjun
	 */
	public static String buildMobile(String mobile) {
		String prefix=mobile.substring(0,3);
		String str="";
		if(mobile.length()>3){
			for(int i=3;i<mobile.length()-4;i++){
				str+="*";
			}
		}
		String suffix=mobile.substring(mobile.length()-4,mobile.length());
		return prefix+str+suffix;
	}
	
	
	/**
	 * 银行卡用星号表示
	 * @param mobile
	 * @return
	 * @date 2015年1月9日
	 * @author zhangjun
	 */
	public static String buildBank(String bank) {
		String prefix=bank.substring(0,4);
		String str="";
		if(bank.length()>3){
			for(int i=4;i<bank.length()-4;i++){
				str+="*";
			}
		}
		String suffix=bank.substring(bank.length()-4,bank.length());
		return prefix+str+suffix;
	}
	
	
	/**
	 * 随机生成字符串
	 * @param n
	 * @return
	 * @date 2015年1月4日
	 * @author zhangjun
	 */
	public static String getRandomStr(int length) { 
		 	String str = UUID.randomUUID().toString().replace("-", ""); 
		    String s = str.substring(str.length()-length);  
		    return s;
	}  
	
	
	
}
