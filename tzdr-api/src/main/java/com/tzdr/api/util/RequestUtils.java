package com.tzdr.api.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tzdr.api.constants.DataConstant;
import com.tzdr.common.utils.IpUtils;
import com.tzdr.domain.api.entity.ApiToken;
import com.tzdr.domain.constants.Constant;

/**
 *<B>说明: </B> 根据请求获取相关参数工具类
 * @zhouchen
 * 2015年5月21日 下午1:17:22
 */
public class RequestUtils{
	public static final Logger logger = LoggerFactory.getLogger(RequestUtils.class);

	/**
	 * 获取token
	 * @param request
	 * @return
	 */
	public static String getToken(HttpServletRequest request){
		return request.getHeader(DataConstant.API_TOKEN);
	}
	
	/**
	 * 获取请求来源
	 * @param request
	 * @return
	 */
	public static String getSource(HttpServletRequest request){
		return request.getHeader(DataConstant.SOURCE);
	}
	
	/**
	 * 获取请求ip
	 * @param request
	 * @return
	 */
	public static String getIp(HttpServletRequest request){
		return IpUtils.getIpAddr(request);
	}
	
	
	public static void printText(String str, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(str);
			out.flush();
		} catch (Exception e) {
			logger.error(("response write: {} error."+e.getMessage()),str);
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
	
	/**
	 * 获取来源类型
	 * @param apiToken
	 * @return
	 */
	public static int getSource(ApiToken apiToken){
		if (ObjectUtil.equals(null,apiToken)){
			return Constant.RegistSource.WAP_REGIST;
		}
		String source = apiToken.getSource();
		if (StringUtil.equals(source,DataConstant.SOURCE_WAP)){
			return Constant.RegistSource.WAP_REGIST;
		}
		
		if (StringUtil.equals(source, DataConstant.SOURCE_PEIGUBAO)){
			return Constant.RegistSource.WAP_PEIGUBAO_REGIST;
		}
		
		return Constant.RegistSource.WAP_REGIST;
	}
	
	/**
	 * 支付宝 自动充值 生成查询字符串
	 * 支付宝帐号：xxx***123235  替换为：xxx%123235
	 * @param account
	 * @return
	 */
	/*public static String createAlipayQueryStr(String account){
		if (StringUtil.isBlank(account)){
			return null;
		}
		
		if (!account.contains(DataConstant.ASTERISK_FLAG)){
			return account;
		}
		int start = account.indexOf(DataConstant.ASTERISK_FLAG);
		int end = account.lastIndexOf(DataConstant.ASTERISK_FLAG);
		
		return account.substring(DataConstant.ZERO,start)
				.concat(DataConstant.SIGN_FLAG)
				.concat(account.substring(end+DataConstant.ONE,account.length()));
	}*/
	
	
	
	/**
	 * 根据银行卡获取银行图片路径
	 * @param bankname
	 * @return
	 * @date 2015年1月12日
	 * @author zhangjun
	 */
	public static  String getBankpathbybankname(String bankname){
		String path="";
		if("icbc".equals(bankname)){	//中国工商银行	
			 path="/images/banks/bank_01.jpg";
		}else if("ccb".equals(bankname)){//建设银行
			 path="/images/banks/bank_04.jpg";
		}else if("abc".equals(bankname)){//农业银行
			 path="/images/banks/bank_03.jpg";
		}else if("cmb".equals(bankname)){//招商银行
			 path="/images/banks/bank_02.jpg";
		}else if("boc".equals(bankname)){//中国银行
			 path="/images/banks/bank_10.jpg";
		}else if("cmbc".equals(bankname)){//中国民生银行
			 path="/images/banks/bank_05.jpg";
		}else if("spdb".equals(bankname)){//浦发银行
			 path="/images/banks/bank_06.jpg";
		}else if("gdb".equals(bankname)){//广发银行
			 path="/images/banks/bank_07.jpg";
		}else if("hxb".equals(bankname)){//华夏银行
			 path="/images/banks/bank_08.jpg";
		}else if("psbc".equals(bankname)){//中国邮政储蓄银行
			 path="/images/banks/bank_09.jpg";
		}else if("ceb".equals(bankname)){//光大银行
			 path="/images/banks/bank_11.jpg";
		}else if("bea".equals(bankname)){//东亚银行
			 path="/images/banks/bank_12.jpg";
		}else if("cib".equals(bankname)){//兴业银行
			 path="/images/banks/bank_13.jpg";
		}else if("comm".equals(bankname)){//交通银行
			 path="/images/banks/bank_14.jpg";
		}else if("citic".equals(bankname)){//中信银行
			 path="/images/banks/bank_15.jpg";
		}else if("bja".equals(bankname)){//北京银行
			 path="/images/banks/bank_16.jpg";
		}else if("shrcb".equals(bankname)){//上海农商银行
			 path="/images/banks/bank_18.jpg";
		}else if("wzcb".equals(bankname)){//温州银行
			 path="/images/banks/bank_17.jpg";
		}else if("shb".equals(bankname)){//上海银行
			 path="/images/banks/shanghai.jpg";
		}else if("spab".equals(bankname)){//平安银行
			 path="/images/banks/pingan.jpg";
		}
		return path;
	}
	
	/**
	 * 是否有效手机号码
	 * @param mobileNum
	 * @return
	 */
	public static boolean isMobileNum(String mobileNum) {
		if (null == mobileNum) {
			return false;
		}
		return mobileNum.matches("^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))(\\d{8})$");
	}
}
