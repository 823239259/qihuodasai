package com.tzdr.common.api.lianyus;

import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tzdr.common.api.lianyus.util.LianYusUtil;
import com.tzdr.common.exception.EmailExceptionHandler;
import com.tzdr.common.utils.ConfUtil;
import com.tzdr.common.utils.TypeConvert;

/**
 * 新的短信发送平台
 * @zhouchen
 * 2015年11月17日
 */
public class LianyusSMSSender {

	public static final Logger log = LoggerFactory.getLogger(LianyusSMSSender.class);

	private static LianyusSMSSender instance;

	private static String username;

	private static String password;

	private static String sendUrl;
	
	private static String checkFilterWordUrl;
	
	 // 提交成功
	public static final String SEND_SUCCESS = "0";
	
	// 短信内容关键词检测成功
	public static final String CHECK_SUCCESS = "0";

	private LianyusSMSSender() {
		username = LianYusUtil.getContext("tzdr.lianyus.username");
		password = LianYusUtil.getContext("tzdr.lianyus.password");
		sendUrl = LianYusUtil.getContext("lianyus.send.url");
		checkFilterWordUrl = LianYusUtil.getContext("lianyus.check.filter.word.url");		
	}

	public static synchronized LianyusSMSSender getInstance() {
		if (instance == null) {
			instance = new LianyusSMSSender();
		}
		return instance;
	}

	
	/**
	 * 
	 * @param mobile 手机号码（多个号码用英文半角逗号分开，最多可提交10000个。）
	 * @param content 短信发送内容。
	 * @return
	 * 返回结果：发送返回结果为xml格式：
	 *  <?xml version="1.0" encoding="UTF-8"?>
	 *  <result>
	 *  	<resultcode>0</ resultcode >
	 *  	<taskcode>20130213231023</taskcode>
	 *  	<errordescription>发送成功</errordescription>
	 *  </result>
	 *  其中resultcode为发送返回值，0为提交成功，其他为失败，失败代码见错误代码附表,
	 *  taskcode为发送成功编号，用来获取状态报告使用，errordescription为返回值描述信息。
	 *  1 用户名不能为空
	 *  2 密码不能为空 
	 *  3 短信内容不能为空
	 *  4 手机号码不能为空
	 *  5 用户名错误 
	 *  6 密码错误 
	 *  7 用户账号已被锁定 
	 *  8 包含屏蔽词
	 *  9 用户账号余额不足，请充值后发送
	 *  10 检测内容不能为空
	 *  11 用户未开放http接口访问
	 *  12 用户访问接口ip错误
	 */
	public  boolean  send(String mobile, String content) {
			
		/*if(!checkFilterWord(content)){
			return false;
		}*/
		PostMethod method = new PostMethod(sendUrl);
		HttpClient client=new HttpClient();	
		client.getParams().setContentCharset("UTF-8");
		method.setRequestHeader("ContentType",
				"application/x-www-form-urlencoded;charset=UTF-8");

		NameValuePair[] data = { new NameValuePair("username", username),
				new NameValuePair("password", password),
				new NameValuePair("mobile", mobile),
				new NameValuePair("content", content), };

		method.setRequestBody(data);
		try {
			client.executeMethod(method);

			String SubmitResult = method.getResponseBodyAsString();

			Document doc = DocumentHelper.parseText(SubmitResult);
			Element root = doc.getRootElement();
			if(SEND_SUCCESS.equals(root.elementText("resultcode"))){
				return true;				
			}else{
				log.info("手机号：{},resultcode:{},msg:{}",mobile,root.elementText("resultcode"),root.elementText("errordescription"));
				return false;
			}			
		} catch (Exception e) {
			String details=mobile+","+content;	
			EmailExceptionHandler.getInstance().HandleExceptionWithData(e, "发送短信接口报错", this.getClass().getName()+"send",details);	
		}finally{
			method.releaseConnection();
		}
		return false;

	}
	/**
	 * 模板全写在config.properties中
	 * tempateKey:ihuyi.verification.code.template. map key值为module，code
	 * 
	 */
	
	public boolean sendByTemplate(String mobile, String templateKey,Map<String,String> map){
		String template = ConfUtil.getContext(templateKey);
		String content = "";
		if("ihuyi.verification.code.template".equals(templateKey)){
			content=String.format(template, map.get("module"), map.get("code"));
		}else if("ihuyi.verification.signin.code.template".equals(templateKey)){
			content=String.format(template, map.get("module"), map.get("code"));
		}else if("ihuyi.trade.ok.code.template".equals(templateKey)){
			content=String.format(template, map.get("group"), map.get("starttime"));
		}else if("hk.ihuyi.trade.ok.code.template".equals(templateKey)){
			content=String.format(template, map.get("group"), map.get("starttime"));
		}else if("8800.template".equals(templateKey)){
			content=template;
		}else if("draw.money.template".equals(templateKey)){
			content=String.format(template, map.get("account"), map.get("money"));
		}else if("hk.ihuyi.end.trade.ok.code.template".equals(templateKey)){
			content=String.format(template, map.get("groupId"), map.get("leverMoney"), map.get("accrualValue"), map.get("endMoney"));
		}		
		return send(mobile,content);
	}
	
	/**
	 * 
	 * @since 根据Classpath路径进行发送短信
	 * 如classpath /sms/activity002.ftl
	 * 2 + 3 = %s
	 * sendByTemplateClasspath("/sms/activity002","13109982819","5")
	 * 输出：2 + 3 = 5
	 * 
	 * @param mobile String
	 * @param filePath String
	 * @param params ...String
	 * @return boolean
	 */
	public boolean sendByTemplateClasspath(String filePath,String mobile,String...params) {
		String content = TypeConvert.getActivityFileContent(filePath, params);
		return send(mobile,content);
	}

	

	/**
	 * 监测屏蔽词接口
	 * 发送返回结果为xml格式： <?xml version="1.0" encoding="UTF-8"?>
	 * <result> 
	 * <resultcode>0</ resultcode >
	 * <errordescription>不包含屏蔽词</errordescription>
	 * </result>其中resultcode为发送返回值，0为查询余额成功，其他为失败，失败代码见错误代码附表,errordescription为返回值描述信息。
	 * @param content
	 */
	private boolean checkFilterWord(String content) {
		PostMethod method = new PostMethod(checkFilterWordUrl);
		HttpClient client=new HttpClient();	
		client.getParams().setContentCharset("UTF-8");
		method.setRequestHeader("ContentType",
				"application/x-www-form-urlencoded;charset=UTF-8");

		NameValuePair[] data = { new NameValuePair("username", username),
				new NameValuePair("password", password),
				new NameValuePair("content", content), };

		method.setRequestBody(data);
		try {
			client.executeMethod(method);
			String SubmitResult = method.getResponseBodyAsString();
			Document doc = DocumentHelper.parseText(SubmitResult);
			Element root = doc.getRootElement();
			if(CHECK_SUCCESS.equals(root.elementText("resultcode"))){
				return true;				
			}else{
				log.info("监测内容：{},resultcode:{},msg:{}",content,root.elementText("resultcode"),root.elementText("errordescription"));
				return false;
			}			
		} catch (Exception e) {
			String details="短信内容：["+content+"]关键词检测调用失败！";	
			EmailExceptionHandler.getInstance().HandleExceptionWithData(e, "发送短信接口报错", this.getClass().getName()+"send",details);	
		}finally{
			method.releaseConnection();
		}
		return false;
	}
}
