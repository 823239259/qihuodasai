package com.tzdr.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.api.util.AuthUtils;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.utils.WeChatUtil;
import com.tzdr.common.utils.WeChatUtil.QrcodeType;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.web.entity.WUser;

@Controller
@RequestMapping(value = "/wechat")
public class WeChatController {
	@Autowired
	private WUserService wUserService;
	/**
	 * 获取ticket
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getWechatQrcodeTicket",method = RequestMethod.GET)
	@ResponseBody
	public JsonResult getWechatQrcodeTicket(HttpServletRequest request){
		JsonResult jsonResult = new JsonResult();
		String uid = AuthUtils.getCacheUser(request).getUid();  //获取用户信息
		WUser user = wUserService.getUser(uid);
		String ticket = WeChatUtil.getQrcodeTicket(user.getGeneralizeId(), QrcodeType.QR_SCENE, 604800L);
		jsonResult.appendData("ticket", ticket);
		jsonResult.appendData("url", WeChatUtil.CREATE_QRCODE_URL);
		jsonResult.setSuccess(true);
		return jsonResult;
	}
}

