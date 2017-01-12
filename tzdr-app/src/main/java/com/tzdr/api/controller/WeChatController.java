package com.tzdr.api.controller;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hundsun.t2sdk.common.util.ObjectUtils;
import com.tzdr.api.constants.ResultStatusConstant;
import com.tzdr.api.support.ApiResult;
import com.tzdr.api.support.CacheUser;
import com.tzdr.api.util.AuthUtils;
import com.tzdr.business.service.wuser.UserVerifiedService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.utils.WeChatUtil;
import com.tzdr.common.utils.WeChatUtil.QrcodeType;
import com.tzdr.domain.vo.WuserActivityVo;
import com.tzdr.domain.web.entity.WUser;
import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

@Controller
@RequestMapping(value = "/wechat")
public class WeChatController {
	@Autowired
	private WUserService wUserService;
	@Autowired
	private UserVerifiedService userVerifiedService;
	
	//注册页面
	private static final String  REDIECTURL = "http://www.vs.com/signin";
	
	/**
	 * 第三方链接  用于生成二维码
	 */
	private static final String THREEPARTYURL = "http://qr.topscan.com/api.php?text=";
	
	/**
	 * 获取微信关注ticket
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
	
	/**
	 * 生成老带新  二维码 和 邀请链接
	 * @param request
	 * @return
	 */
	/*@RequestMapping(value="/createOldAndNewQrcode",method=RequestMethod.POST)
	@ResponseBody
	public ApiResult createOldAndNewQrcode(HttpServletRequest request){
		String uid = AuthUtils.getCacheUser(request).getUid();  //获取用户信息
		WUser user = wUserService.getUser(uid); 
		String realName = userVerifiedService.queryUserVerifiedByUid(uid).getTname();
		String generalizeId = user.getGeneralizeId();
		if(ObjectUtil.equals(null, user)){
			return new ApiResult(false,ResultStatusConstant.FAIL,"user info not error");
		}
		Map<String, Object> dataMap = new HashMap<String,Object>();
		dataMap.put("inviteUrl", REDIECTURL + "?generalizeId=" + generalizeId);
		String realPath = request.getSession().getServletContext().getRealPath("/");
		String path = realPath+"static\\images\\qrcode";
		Map<String, String> map = new HashMap<String, String>();
		map.put("generalizeId", generalizeId);
		QrcodeUtil.createQrcode(path,REDIECTURL,map);
		dataMap.put("qrcodeUrl",path + "\\" + generalizeId + ".git");
		dataMap.put("realName",realName);
		return new  ApiResult(true,ResultStatusConstant.SUCCESS,"qrcode create success",dataMap);
	}*/
	
	/**
	 * 调用第三方 生成二维码     邀请链接
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/threePartyCreateQrcode",method=RequestMethod.POST)
	@ResponseBody
	public ApiResult createtThreePartyQrcode(HttpServletRequest request){
		CacheUser cacheUser = AuthUtils.getCacheUser(request);  //获取用户信息
		WUser user = null;
		String uid = null;
		if(cacheUser == null || cacheUser.getUid() == null){
			String mobile = request.getParameter("mobile");
			user = wUserService.getWUserByMobile(mobile);
		    uid = user.getId();
		}else{
			uid = cacheUser.getUid();
			user = wUserService.getUser(uid); 
		}
		String realName = userVerifiedService.queryUserVerifiedByUid(uid).getTname();
		String generalizeId = user.getGeneralizeId();
		String inviteUrl = REDIECTURL + "?generalizeId=" + generalizeId;
		String qrcodeUrl = THREEPARTYURL + inviteUrl;
		Map<String, Object> dataMap = new HashMap<String,Object>();
		dataMap.put("qrcodeUrl",qrcodeUrl );
		dataMap.put("inviteUrl", inviteUrl);
		boolean isRealName = true;
		if(StringUtil.isBlank(realName)){
			isRealName = false;
		}
		dataMap.put("isRealName", isRealName);
		return new ApiResult(true,ResultStatusConstant.SUCCESS,"qrcode create success",dataMap);
	}
	
}

