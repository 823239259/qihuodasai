package com.tzdr.common.api.alidayu;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import com.tzdr.common.api.alidayu.util.AlidayuUtil;
import com.tzdr.common.exception.EmailExceptionHandler;
/**
 * 新的短信发送平台
 * @WangPinQun
 * 2016年07月08日
 */
public class AlidayuSMSSender {

	public static final Logger log = LoggerFactory.getLogger(AlidayuSMSSender.class);

	private static AlidayuSMSSender instance;

	private static String appkey;

	private static String secret;

	private static String sendUrl;
	
	private static String smsType;
	
	private static String freeSignName;
	
	 // 提交成功
	public static final String SEND_SUCCESS = "0";
	
	private AlidayuSMSSender() {
		appkey = AlidayuUtil.getContext("tzdr.alidayu.appkey");
		secret = AlidayuUtil.getContext("tzdr.alidayu.secret");
		sendUrl = AlidayuUtil.getContext("tzdr.alidayu.send.url");
		smsType = AlidayuUtil.getContext("tzdr.alidayu.sms.type");	
		freeSignName = AlidayuUtil.getContext("tzdr.alidayu.free.sign.name");
	}

	public static synchronized AlidayuSMSSender getInstance() {
		if (instance == null) {
			instance = new AlidayuSMSSender();
		}
		return instance;
	}

	/**
	 * 模板全写在alidayu.properties中
	 * tempateKey:tzdr.alidayu.signin.code.template map key值为module，code
	 */
	public boolean send(String mobile, String content,String templateKey){
		TaobaoClient client = new DefaultTaobaoClient(sendUrl, appkey, secret);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setSmsType(smsType);
		req.setSmsFreeSignName(freeSignName);
		req.setSmsParamString(content);
		req.setRecNum(mobile);
		req.setSmsTemplateCode(AlidayuUtil.getContext(templateKey));
		AlibabaAliqinFcSmsNumSendResponse rsp;
		try {
			rsp = client.execute(req);
			if(rsp.isSuccess()){
				return true;
			}else{
				log.info("手机号：{},sub_code:{},sub_msg:{}",mobile,rsp.getSubCode(),rsp.getSubMsg());
				return false;
			}
		} catch (ApiException e) {
			String details = mobile + ","+content;	
			EmailExceptionHandler.getInstance().HandleExceptionWithData(e, "发送短信接口报错", this.getClass().getName()+"send",details);	
		}
		return false;
	}
	
	/**
	 * 模板全写在alidayu.properties中
	 * tempateKey:tzdr.alidayu.signin.code.template map key值为module，code
	 */
	public boolean sendByTemplate(String mobile, String templateKey,Map<String,String> map){
		
		String content = "";
		
		if(!CollectionUtils.isEmpty(map)){
			content+="{";
			int index = 0;
			for (Map.Entry<String, String> entry : map.entrySet()) {
				content+="\""+entry.getKey()+"\":\""+entry.getValue()+"\"";
				content = index < map.size() - 1 ? content + "," : content;
				index++;
			}
			content+="}";
		}

		return send(mobile,content,templateKey);
	}
}
