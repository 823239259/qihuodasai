package com.tzdr.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.common.utils.WeChatQrcodeUtil;
import com.tzdr.common.utils.WeChatQrcodeUtil.QrcodeType;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.web.constants.Constants;
import com.tzdr.web.utils.UserSessionBean;

@Controller
@RequestMapping(value = "/wechat")
public class WechatController {
	/**
	 * 获取ticket
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getWechatQrcodeTicket",method = RequestMethod.GET)
	@ResponseBody
	public JsonResult getWechatQrcodeTicket(HttpServletRequest request){
		JsonResult jsonResult = new JsonResult();
		UserSessionBean sessionBean = (UserSessionBean) request.getSession()
				.getAttribute(Constants.TZDR_USER_SESSION);
		String ticket = WeChatQrcodeUtil.getQrcodeTicket(sessionBean.getMobile(), QrcodeType.QR_SCENE, 604800L);
		jsonResult.appendData("ticket", ticket);
		jsonResult.appendData("url", WeChatQrcodeUtil.CREATE_QRCODE_URL);
		jsonResult.setSuccess(true);
		return jsonResult;
	}
}
