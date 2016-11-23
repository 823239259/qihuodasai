package com.tzdr.web.controller.wechat;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sword.lang.StreamUtils;

import com.tzdr.business.service.securityInfo.SecurityInfoService;
import com.tzdr.business.service.wechat.WechatUserService;
import com.tzdr.business.service.wuser.WUserService;

@Controller
@RequestMapping(value = "/wx")
public class WxContoller{
	@Autowired
	private WUserService wUserService;
	@Autowired 
	private WechatUserService wechatUserService;
	@Autowired
	private SecurityInfoService securityInfoService;
	@RequestMapping(value = "/vswx",produces="text/plain")
	@ResponseBody
	public void wx(HttpServletRequest reqest,HttpServletResponse response){
		try {
			WeChatSupport chatSupport = new WeChatSupport(reqest , wUserService , wechatUserService,securityInfoService);
			String result = chatSupport.execute();
			response.setHeader("content-type", "text/html;charset=gbk");// 浏览器编码
			response.getOutputStream().write(result.getBytes("gbk"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
