package com.tzdr.common.api.ihuyi;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tzdr.common.api.alidayu.AlidayuSMSSender;
import com.tzdr.common.api.ihuyi.util.IhuyiConfigUtil;
import com.tzdr.common.api.lianyus.LianyusSMSSender;
import com.tzdr.common.exception.EmailExceptionHandler;
import com.tzdr.common.utils.ConfUtil;
import com.tzdr.common.utils.TypeConvert;

/**
 * Lin Feng 互亿无线短信工具类 互亿无线 http://106.ihuyi.cn
 * 接口URL:http://106.ihuyi.cn/webservice/sms.php?method=Submit
 */
public class SMSSender {

	public static final Logger log = LoggerFactory.getLogger(SMSSender.class);

	private static SMSSender instance;

	private static String account;

	private static String password;

	private static String Url;

	/**
	 * 
	 * 提交成功
	 * 
	 */
	public static final String SEND_SUCCESS = "2";

	private SMSSender() {
		account = IhuyiConfigUtil.getContext("tzdr.ihuyi.account");
		password = IhuyiConfigUtil.getContext("tzdr.ihuyi.password");
		Url = IhuyiConfigUtil.getContext("ihuyi.url");
	}

	public static synchronized SMSSender getInstance() {
		if (instance == null) {
			instance = new SMSSender();
		}
		return instance;
	}

	/** 
	 * 发送短信接口
	 * 
	 * @param smsChannel 短信通道：1-互亿无线；2-lianyus；默认使用互亿无线
	 * @param mobile
	 * @param content
	 * @return
	 */
	public boolean send(int smsChannel, String mobile, String content) {
		boolean flag = false;
		// 判断短信通道：1-互亿无线；2-lianyus；默认使用互亿无线
		switch (smsChannel) {
		case 1:
			flag = sendSMS(mobile, content);
			break;
		case 2:
			content = content.replaceAll("【", "[").replaceAll("】", "]");
			flag = LianyusSMSSender.getInstance().send(mobile, content);
			break;
		default:
			flag = sendSMS(mobile, content);
			break;
		}
		log.info("短信通道:{},手机号：{},msg:{}",  smsChannel, mobile, (flag?"短信发送成功":"短信发送失败"));
		return flag;
	}

	/**
	 * 短信发送接口
	 * 
	 * 返回数据      	
	 * code	msg
	 * 0	提交失败
	 * 2	提交成功
	 * 400	非法ip访问
	 * 401	帐号不能为空
	 * 402	密码不能为空
	 * 403	手机号码不能为空
	 * 4030	手机号码已被列入黑名单
	 * 404	短信内容不能为空
	 * 405	用户名或密码不正确
	 * 4050	账号被冻结
	 * 4051	剩余条数不足
	 * 4052	访问ip与备案ip不符
	 * 406	手机格式不正确
	 * 407	短信内容含有敏感字符
	 * 4070	签名格式不正确
	 * 4071	没有提交备案模板
	 * 4072	短信内容与模板不匹配
	 * 4073	短信内容超出长度限制
	 * 408	您的帐户疑被恶意利用，已被自动冻结，如有疑问请与客服联系。
	 * 
	 * @param mobile 手机号码
	 * @param content 短信内容
	 * @return result true发送成功，false发送失败
	 */
	public boolean sendSMS(String mobile, String content) {
		PostMethod method = new PostMethod(Url);
		HttpClient client = new HttpClient();
		client.getParams().setContentCharset("UTF-8");
		method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=UTF-8");

		NameValuePair[] data = { new NameValuePair("account", account), new NameValuePair("password", password), new NameValuePair("mobile", mobile), new NameValuePair("content", content), };

		method.setRequestBody(data);
		try {
			client.executeMethod(method);

			String SubmitResult = method.getResponseBodyAsString();

			Document doc = DocumentHelper.parseText(SubmitResult);
			Element root = doc.getRootElement();
			if (SEND_SUCCESS.equals(root.elementText("code"))) {
				return true;
			} else {
				log.info("手机号：{},code:{},msg:{}", mobile, root.elementText("code"), root.elementText("msg"));
				return false;
			}
		} catch (Exception e) {
			String details = mobile + "," + content;
			EmailExceptionHandler.getInstance().HandleExceptionWithData(e, "发送短信接口报错", this.getClass().getName() + "send", details);
		}
		return false;
	}

	/**
	 * 模板全写在config.properties中
	 * tempateKey:ihuyi.verification.code.template. map key值为module，code
	 * 
	 */
	public boolean sendByTemplate(int smsChannel, String mobile, String templateKey, Map<String, String> map) {
		if(smsChannel == 3){  // 判断短信通道：1-互亿无线；2-lianyus；3-阿里大鱼；默认使用互亿无线
			boolean flag = AlidayuSMSSender.getInstance().sendByTemplate(mobile, templateKey, map);
			log.info("短信通道:{},手机号：{},msg:{}",  smsChannel, mobile, (flag?"短信发送成功":"短信发送失败"));
			return flag;
		}
		String template = ConfUtil.getContext(templateKey);
		log.info("template"+template);
		String content = "";
		if("ihuyi.verification.signin.success.template".equals(templateKey)){
			content = String.format(template);
		}else if("ihuyi.forget.loginPwd.code.template".equals(templateKey)){
			content = String.format(template, map.get("code"));
		}else if("ihuyi.forget.withdrawPwd.code.template".equals(templateKey)){
			content = String.format(template, map.get("code"));
		}else if("ihuyi.update.newMobile.code.template".equals(templateKey)){
			content = String.format(template,map.get("code"));
		}else if("ihuyi.recharge.success.template".equals(templateKey)){
			content = String.format(template , map.get("money"));
		}else if("ihuyi.settlement.success.template".equals(templateKey)){
			content = String.format(template , map.get("lever"),map.get("business"));
		}else if("ihuyi.presentation.to.account.template".equals(templateKey)){
			content = String.format(template , map.get("money"));
		}else if ("ihuyi.verification.signin.code.template".equals(templateKey)) {
			content = String.format(template, map.get("module"), map.get("code"));
		}else if ("ihuyi.verification.code.template".equals(templateKey)) {
			content = String.format(template, map.get("module"), map.get("code"));
		} 
		if ("ihuyi.trade.ok.code.template".equals(templateKey)) {
			content = String.format(template, map.get("group"), map.get("starttime"));
		} else if ("hk.ihuyi.trade.ok.code.template".equals(templateKey)) {
			content = String.format(template, map.get("group"), map.get("starttime"));
		} else if ("8800.template".equals(templateKey)) {
			content = template;
		} else if ("draw.money.template".equals(templateKey)) {
			content = String.format(template, map.get("account"), map.get("money"));
		} else if ("hk.ihuyi.end.trade.ok.code.template".equals(templateKey)) {
			content = String.format(template, map.get("groupId"), map.get("leverMoney"), map.get("accrualValue"), map.get("endMoney"));
		} else if ("together.ihuyi.trade.ok.code.template".equals(templateKey)) {
			content = String.format(template, map.get("group"), map.get("starttime"));
		} else if ("together.ihuyi.end.trade.ok.code.template".equals(templateKey)) {
			content = String.format(template, map.get("group"), map.get("totalInvest"), map.get("accrualValue"), map.get("revocationMoney"));
		} else if ("ag.ihuyi.end.trade.ok.code.template".equals(templateKey)) {
			content = String.format(template, map.get("group"), map.get("totalInvest"), map.get("accrualValue"), map.get("revocationMoney"));
		} else if("subdsion.ihuyi.code.template".equals(templateKey)){
			content = String.format(template , map.get("money"));
		} else if("activity.luck.ihuyi.code.template".equals(templateKey)){
			content = String.format(template);
		}
		
		log.info("发送模板内容为："+content);
		return send(smsChannel, mobile, content);
	}

	/**
	 * 
	 * @since 根据Classpath路径进行发送短信
	 * 如classpath /sms/activity002.ftl
	 * 2 + 3 = %s
	 * sendByTemplateClasspath("/sms/activity002","13109982819","5")
	 * 输出：2 + 3 = 5
	 * 
	 * @param smsChannel int
	 * @param mobile String
	 * @param filePath String
	 * @param params ...String
	 * @return boolean
	 */
	public boolean sendByTemplateClasspath(int smsChannel, String filePath, String mobile, String... params) {
		String content = TypeConvert.getActivityFileContent(filePath, params);
		return send(smsChannel, mobile, content);
	}
}
