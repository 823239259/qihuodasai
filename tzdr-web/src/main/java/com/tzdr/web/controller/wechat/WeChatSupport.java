package com.tzdr.web.controller.wechat;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.sword.wechat4j.WechatSupport;

import com.alibaba.fastjson.JSONObject;
import com.tzdr.web.utils.HttpRequest;
public class WeChatSupport extends WechatSupport{

	public WeChatSupport(HttpServletRequest request) {
		super(request);
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
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void templateMsgCallback() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void unSubscribe() {
		// TODO Auto-generated method stub
		
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
