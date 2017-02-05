package com.tzdr.web.controller.wechat;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.sword.wechat4j.WechatSupport;

import com.alibaba.fastjson.JSONObject;
import com.tzdr.business.service.securityInfo.SecurityInfoService;
import com.tzdr.business.service.wechat.WechatUserService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.utils.WeChatUtil;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.web.entity.UserVerified;
import com.tzdr.domain.web.entity.WUser;
import com.tzdr.domain.web.entity.WechatUser;
import com.tzdr.web.utils.HttpRequest;
public class WeChatSupport extends WechatSupport{
	private WUserService wUserService;
	private WechatUserService wechatUserService;
	private SecurityInfoService securityInfoService;
	public WeChatSupport(HttpServletRequest request) {
		super(request);
	}
	
	
	public WeChatSupport(HttpServletRequest request, WUserService wUserService, WechatUserService wechatUserService,SecurityInfoService securityInfoService) {
		super(request);
		this.wUserService = wUserService;
		this.wechatUserService = wechatUserService;
		this.securityInfoService = securityInfoService;
	}


	@Override
	protected void click() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void location() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void locationSelect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onImage() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onLink() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onLocation() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onText() {
		responseText("测试测试");
	}
	@Override
	protected void onUnknown() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onVideo() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onVoice() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void picPhotoOrAlbum() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void picSysPhoto() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void picWeixin() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void scan() {
		responseText("维胜金融欢迎您！");
	}

	@Override
	protected void scanCodePush() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void scanCodeWaitMsg() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void subscribe() {
		String eventKey = this.wechatRequest.getEventKey();
		String openId = this.wechatRequest.getFromUserName();
		String result = WeChatUtil.getWechatUser(openId);
		JSONObject resultJson = JSONObject.parseObject(result);
		boolean flag = true;
		try {
			int eventKeyLength = eventKey.length();
			WechatUser wechatUser = null;
			List<WechatUser> wechatUsers = wechatUserService.doGetWechatUserByOpenId(openId);
			if(wechatUsers.size() > 0){
				wechatUser = wechatUsers.get(0);
				flag = false;
			}else{
				wechatUser = new WechatUser();
			}
			String userId = wechatUser.getUserId();
			if(userId == null ||  userId.equals("-")){
				WUser wUser = null;
				if(eventKeyLength > 0){
					String[] ser = eventKey.split("_");
					if(ser.length > 1){
						wUser = wUserService.findByGeneralizeId(ser[1]);
						userId = wUser.getId();
					}else{
						userId = "-";
					}
				}else{
					userId = "-";
				}
			}
			wechatUser.setUserId(userId);
			wechatUser.setWechatCity(resultJson.getString("city"));
			wechatUser.setWechatCountry(resultJson.getString("country"));
			wechatUser.setWechatGroupid(resultJson.getString("groupid"));
			wechatUser.setWechatHeadimgurl(resultJson.getString("headimgurl"));
			wechatUser.setWechatLanguage(resultJson.getString("language"));
			wechatUser.setWechatNickName(WeChatUtil.filterEmoji(resultJson.getString("nickname")));
			wechatUser.setWechatOpenId(openId);
			wechatUser.setWechatProvince(resultJson.getString("province"));
			wechatUser.setWechatSex(resultJson.getString("sex"));
			wechatUser.setWechatSubscribe("1");
			if(flag){
				wechatUserService.save(wechatUser);
			}else{
				wechatUserService.update(wechatUser);
			}
			if(userId != null && !userId.equals("-")){
				UserVerified userVerified = securityInfoService.findByUserId(userId);
				userVerified.setWxAccount(wechatUser.getWechatNickName());
				securityInfoService.update(userVerified);
			}
			responseText("感谢你关注维胜金融！");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void templateMsgCallback() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void unSubscribe() {
		String openId = this.wechatRequest.getFromUserName();
		List<WechatUser> wechatUsers = wechatUserService.doGetWechatUserByOpenId(openId);
		if(wechatUsers.size() > 0){
			WechatUser userWechat = wechatUsers.get(0);
			userWechat.setWechatSubscribe("0");
			wechatUserService.update(userWechat);
			String userId = userWechat.getUserId();
			if(userId != null && !userId.equals("-")){
				UserVerified userVerified = securityInfoService.findByUserId(userId);
				if(userVerified != null){
					userVerified.setWxAccount(null);
					securityInfoService.update(userVerified);
				}
			}
		}
	}

	@Override
	protected void view() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void kfCloseSession() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void kfCreateSession() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void kfSwitchSession() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onShortVideo() {
		// TODO Auto-generated method stub
		
	}
	public static void main(String[] args) {
		JSONObject json  = new JSONObject();
		json.put("expire_seconds", 604800);
		json.put("action_name", "QR_SCENE");
		JSONObject lastJson = new JSONObject();
		lastJson.put("scene_id", 123);
		JSONObject chaJson  = new JSONObject();
		chaJson.put("scene", lastJson);
		json.put("action_info", chaJson);
		System.out.println(json.toJSONString());
		String s = HttpRequest.httpGet("https://api.weixin.qq.com/cgi-bin/token", "grant_type=client_credential&appid=wxef4d2241203e3ff4&secret=f921a29ed77a5e2d73ed47896f04341b");
		System.out.println(s);
		JSONObject result = JSONObject.parseObject(s);
		String str = HttpRequest.httpPost("https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+result.getString("access_token")+"", json.toJSONString());
		System.out.println(str);
		JSONObject object = JSONObject.parseObject(str);
		
		try {
			InputStream in = HttpRequest.httpGetInputStream("https://mp.weixin.qq.com/cgi-bin/showqrcode", "ticket="+URLEncoder.encode(object.getString("ticket"), "UTF-8")+"");
			
			System.out.println("https://mp.weixin.qq.com/cgi-bin/showqrcode"+ "ticket="+URLEncoder.encode(object.getString("ticket"), "UTF-8"));
			// 设置数据缓冲
			byte[] bs = new byte[1024 * 2];
			// 读取到的数据长度
			int len;
			// 输出的文件流保存图片至本地
			OutputStream os = new FileOutputStream("D:\\a.jpg");
			while ((len = in.read(bs)) != -1) {
				os.write(bs, 0, len);
				}
			os.close();
			in.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
