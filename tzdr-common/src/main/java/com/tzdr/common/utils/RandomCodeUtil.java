package com.tzdr.common.utils;

import java.util.Random;


/**
 * 随机验证码
 * <P>title:@RandomCodeUtil.java</p>																								
 * <P>Description：</p>
 * <p>Copyright: Copyright (c) 2014 tzdr</p>
 * <p>Company: 上海信闳</p>
 * History：
 * @author:zhangjun
 * @date 2014年12月25日
 * @version 1.0
 */
public class RandomCodeUtil {

	
	 /**
	  * 生成随机数字验证码
	  * @param length 长度
	  * @return
	  * @date 2014年12月25日
	  * @author zhangjun
	  */
	public static  String randStr (int length)    {   
		Random random = new Random();
		String result="";
		for(int i=0;i<length;i++){
			result+=random.nextInt(10);
		}
		return result;
	}
	
	public static String genToken(int size) {
		String temp = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		int length = temp.length();
		int p;
		Random r = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			p = r.nextInt(length);
			sb.append(temp.substring(p, p + 1));
		}
		return sb.toString();
	}
}
