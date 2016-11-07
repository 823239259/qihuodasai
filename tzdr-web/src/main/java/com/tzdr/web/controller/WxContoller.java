package com.tzdr.web.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/wx")
public class WxContoller{
	@RequestMapping(value = "/vswx",produces="text/plain")
	@ResponseBody
	public void wx(HttpServletRequest reqest,HttpServletResponse response){
		try {
			reqest.setCharacterEncoding("utf-8"); 
			WeChatSupport chatSupport = new WeChatSupport(reqest);
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
