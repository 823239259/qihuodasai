package com.tzdr.web.controller.weixin;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sword.lang.JaxbParser;
import org.sword.lang.StreamUtils;
import org.sword.wechat4j.common.Config;
import org.sword.wechat4j.request.WechatRequest;
import org.sword.wechat4j.response.WechatResponse;

import com.tzdr.web.utils.ValidationWeixin;
import com.tzdr.web.utils.WexinWechatSupport;


@Controller
@RequestMapping(value = "/wx")
public class WeChatController {
	@RequestMapping(value = "/wxEntrance",method = RequestMethod.GET)
	public String wxEntrance(HttpServletRequest request){
		ValidationWeixin validationWeixin = new ValidationWeixin();
		System.out.println(Config.instance().getToken());
		System.out.println(request.getParameter("signature"));
		System.out.println(request.getParameter("timestamp"));
		System.out.println(request.getParameter("nonce"));
		System.out.println(request.getParameter("echostr"));
		try {
			validationWeixin.validate(request.getParameter("signature"), request.getParameter("timestamp"), request.getParameter("nonce"));
			String xmlStr = StreamUtils.streamToString(request.getInputStream());
			WechatRequest wechatRequest = new WechatRequest();
			JaxbParser jaxbParser = new JaxbParser(WechatRequest.class);
			System.out.println(xmlStr);
			wechatRequest = (WechatRequest)jaxbParser.toObj(xmlStr);
			System.out.println(wechatRequest.getMsgType()+"执行事件");
			JaxbParser jaxbParserRes = new JaxbParser(WechatResponse.class);
			jaxbParserRes.setCdataNode(WechatResponse.CDATA_TAG);
			String result = jaxbParserRes.toXML(new WechatResponse());
			return result;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping(value = "/wx")
	@ResponseBody
	public String wx(HttpServletRequest request){
		System.out.println(Config.instance().getToken());
		WexinWechatSupport ws = new WexinWechatSupport(request);
		String s = ws.execute();
		return s;
	}
}
