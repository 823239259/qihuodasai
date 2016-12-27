package com.tzdr.api.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.api.constants.ResultStatusConstant;
import com.tzdr.api.support.ApiResult;
import com.tzdr.api.util.AuthUtils;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.utils.WeChatUtil;
import com.tzdr.common.utils.WeChatUtil.QrcodeType;
import com.tzdr.domain.web.entity.WUser;

import jodd.util.ObjectUtil;

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
	@RequestMapping(value = "/getWechatQrcodeTicket",method = RequestMethod.POST)
	@ResponseBody
	public ApiResult getWechatQrcodeTicket(HttpServletRequest request){
		String uid = AuthUtils.getCacheUser(request).getUid();  //获取用户信息
		WUser user = wUserService.getUser(uid);
		if(ObjectUtil.equals(null, user)){
			return new ApiResult(false,ResultStatusConstant.FAIL,"user info not error");
		}
		Map<String,String> data = new HashMap<String,String>();
		String ticket = WeChatUtil.getQrcodeTicket(user.getGeneralizeId(), QrcodeType.QR_SCENE, 604800L);
		data.put("ticket", ticket);
		data.put("url", WeChatUtil.CREATE_QRCODE_URL);
		
		return new ApiResult(true,ResultStatusConstant.SUCCESS,"Wechat Qrcode Ticke success",data);
	}
}

