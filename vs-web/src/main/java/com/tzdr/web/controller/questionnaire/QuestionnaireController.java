package com.tzdr.web.controller.questionnaire;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.StringUtil;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.service.area.AreaService;
import com.tzdr.business.service.questionnaire.QuestionnaireService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.exception.EmailExceptionHandler;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.IpAddressUtils;
import com.tzdr.common.utils.IpUtils;
import com.tzdr.common.utils.RandomCodeUtil;
import com.tzdr.common.utils.StringCodeUtils;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cache.DataDicKeyConstants;
import com.tzdr.domain.entity.DataMap;
import com.tzdr.domain.web.entity.Area;
import com.tzdr.domain.web.entity.Questionnaire;
import com.tzdr.domain.web.entity.WUser;
import com.tzdr.web.constants.Constants;
import com.tzdr.web.constants.ViewConstants;


/**
 * 问卷调查
 * <P>title:@Questionnaire.java</p>																								
 * <P>Description：</p>
 * <p>Copyright: Copyright (c) 2015 tzdr</p>
 * <p>Company: 上海信闳</p>
 * History：
 * @author:zhangjun
 * @date 2015年2月6日
 * @version 1.0
 */
@Controller
@RequestMapping("/")
public class QuestionnaireController {
	public static final Logger logger = LoggerFactory.getLogger(QuestionnaireController.class);
	@Autowired
	private AreaService areaService;
	@Autowired
	private WUserService wUserService;
	@Autowired
	private QuestionnaireService questionnaireService;
	/**
	 * 到问卷调查页面
	 * @param request
	 * @return
	 * @date 2015年2月6日
	 * @author zhangjun
	 */
	@RequestMapping(value = "/totoQuestionnaire")
	public String toQuestionnaire(ModelMap modelMap,HttpServletRequest request){
		List<Area> provinceList = areaService.findByPidOrderBySortAsc("0");  //区域一级列表信息
		modelMap.put("place", provinceList);
		List<DataMap>  startdata=CacheManager.getDataMapListByTypeKey(DataDicKeyConstants.ACTI_START);
		List<DataMap>  enddata=CacheManager.getDataMapListByTypeKey(DataDicKeyConstants.ACTI_END);
		String starttime=startdata.get(0).getValueKey();
		String endtime=enddata.get(0).getValueKey();
		Date nowdate=new Date();
		Date startdate=Dates.parse(starttime, "yyyy-MM-dd HH:mm:ss");
		Date enddate=Dates.parse(endtime, "yyyy-MM-dd HH:mm:ss");
		//活动期间才显示
		if(nowdate.getTime()>=startdate.getTime() && nowdate.getTime()<=enddate.getTime()){
			return ViewConstants.QuestionnaireJsp.TO_QUESTION;
		}else{
			return ViewConstants.QuestionnaireJsp.TO_FAIL;
		}
		
		
	}
	
	/**
	 * 到发送验证码页面
	 * @param modelMap
	 * @param request
	 * @return
	 * @date 2015年2月11日
	 * @author zhangjun
	 */
	@RequestMapping(value = "/totelphone")
	public String telphone(ModelMap modelMap,HttpServletRequest request){
		String id=request.getParameter("id");
		Questionnaire entity=this.questionnaireService.get(id);
		if(entity!=null){
			String mobile=StringCodeUtils.buildMobile(entity.getMobile());
			modelMap.put("mobile", mobile);
			modelMap.put("entity", entity);
		}
		return ViewConstants.QuestionnaireJsp.TO_TEL;
	}
	
	/**
	 * 发送成功页面
	 * @param modelMap
	 * @param request
	 * @return
	 * @date 2015年2月12日
	 * @author zhangjun
	 */
	@RequestMapping(value = "/tosendAccount")
	public String sendAccount(ModelMap modelMap,String id,HttpServletRequest request){
		if(StringUtil.isNotBlank(id)){
			Questionnaire entity=this.questionnaireService.get(id);
			if(entity!=null){
				String uid=entity.getUid();
				if(StringUtil.isNotBlank(uid)){
					String mobile=StringCodeUtils.buildMobile(entity.getMobile());
					String pwd=request.getParameter("pwd");
					modelMap.put("mobile",mobile);
					modelMap.put("usename",entity.getMobile());
					modelMap.put("pwd", pwd);
					return ViewConstants.QuestionnaireJsp.TO_SUC;
				  }
				}
		}
			return ViewConstants.QuestionnaireJsp.TO_FAIL;
		}
		
	
	
	/**
	 * 发送手机验证码
	 * @param response
	 * @param request
	 * @return
	 * @date 2014年12月25日
	 * @author zhangjun
	 */
	@RequestMapping(value = "/tosendQuestionPhoneCode")
	@ResponseBody
	public JsonResult sendPhoneCode(String id,HttpServletResponse response,HttpServletRequest request){
		Questionnaire entity=this.questionnaireService.get(id);
		JsonResult jsonResult = new JsonResult(false);
		if(entity!=null){
			jsonResult.setSuccess(true);
			Long codestart=new Date().getTime()/1000;
			String randomStr=RandomCodeUtil.randStr(6);
			entity.setValidatecode(randomStr);
			entity.setValidatetime(codestart);
			this.questionnaireService.sendSms(entity.getMobile(),"8800活动短信验证",randomStr);
			questionnaireService.update(entity);
			jsonResult.setMessage("success");
		}
		
		return jsonResult;
	}
	private static Object lock = new Object();
	/**
	 * 验证验证码
	 * @param id
	 * @param response
	 * @param request
	 * @return
	 * @date 2015年2月12日
	 * @author zhangjun
	 */
	@RequestMapping(value = "/tovalidateCode")
	@ResponseBody
	public JsonResult validateCode(String id,String code,HttpServletResponse response,HttpServletRequest request){
		Questionnaire entity=this.questionnaireService.get(id);
		 String ip=IpUtils.getIpAddr(request);
		 int count=wUserService.findUsersByIp(ip);
		JsonResult jsonResult = new JsonResult(false);
		if(entity!=null){
			long nowtime=new Date().getTime()/1000;
			long vtime=entity.getValidatetime();
			long minit=nowtime-vtime;
			jsonResult.setSuccess(true);
			String validatecode=entity.getValidatecode();
			if(!validatecode.equals(code)){
				jsonResult.setMessage("输入的验证码错误");
			}else if(minit>300){
				jsonResult.setMessage("输入的验证码已超时");
			}/*else if(count>=5){
				Map<Object, Object> data = new HashMap<Object, Object>();
				data.put("fail", true);
				jsonResult.setData(data);
				jsonResult.setMessage("活动结束");
			}*/else{
				int num=wUserService.activityWuserCount();
				synchronized (lock) {
					if(wUserService.getWUserByMobile(entity.getMobile()) == null){
						WUser wuser=new WUser();
						wuser.setRegIp(ip);
						wuser.setMobile(entity.getMobile());
						wuser.setRegCity(IpAddressUtils.getAffiliationCity(ip, "utf-8"));
						Cookie channelcookie=getCookieByName(request,Constants.TZDR_CHANNEL);
						Cookie genercookie=getCookieByName(request, Constants.TZDR_GENERALIZEUID);
						if(channelcookie!=null){
							String channel=channelcookie.getValue();
							wuser.setChannel(channel);
						}
						if(genercookie!=null){
							String geruid=genercookie.getValue();
							wuser.setGeneralizeId(geruid);
						}
						wuser=this.questionnaireService.saveWUser(wuser,entity,ip);
						jsonResult.setObj(wuser);
					}else{
						jsonResult.setMessage("输入的手机号码已经申请活动账号");
					}
				}
			}
		}else{
			jsonResult.setMessage("输入的信息错误");
		}
		
		return jsonResult;
	}
	
	@RequestMapping(value = "/check_exist_mobile")
	@ResponseBody
	public JsonResult  checkMobile(String mobile,ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		JsonResult jsonResult = new JsonResult(true);
		 String ip=IpUtils.getIpAddr(request);
		 int count=wUserService.findUsersByIp(ip);
		Map<Object, Object> data = new HashMap<Object, Object>();
		data.put("isExist", false);
		if(wUserService.getWUserByMobile(mobile) != null){ //判断手机号码是否已存在
			data.put("isExist", true);
		}else{
			boolean flag=false;
//			try {
//				flag = TypeConvert.isChongqingPhoneNumber(mobile);
//			} catch (Exception e) {
//				logger.error("查询手机归属地错误"+e.getMessage());
//			}
			if(flag){
				data.put("ischongqing", true);
			}
//			else if(count>5){
//				data.put("fail", true);
//			}
		}
		jsonResult.setData(data);
		return jsonResult;
	}
	
	
	/**
	 * 保存
	 * @param response
	 * @param request
	 * @return
	 * @date 2015年2月11日
	 * @author zhangjun
	 */
	@RequestMapping(value = "/doQuestionnaire")
	@ResponseBody
	public JsonResult doQuestionnaire(HttpServletResponse response,HttpServletRequest request){
		Map map=this.getMapByRequest(request);
		String birthday=(String) map.get("birthday");
		JsonResult jsonResult = new JsonResult(false);
		WUser user=wUserService.getWUserByMobile((String)map.get("mobile"));
		 String ip=IpUtils.getIpAddr(request);
		 int count=wUserService.findUsersByIp(ip);
		boolean flag=true;
		try {
			flag = TypeConvert.isChongqingPhoneNumber((String)map.get("mobile"));
		} catch (Exception e) {
			String dataDetail="mobile:"+(String)map.get("mobile")+"|异常："+e.getMessage();
			EmailExceptionHandler.getInstance().HandleExceptionWithData(e, "查询手机归属地失败", this.getClass().getName()+":dataDetail", dataDetail);
		}
		if(user!=null){
			jsonResult.setSuccess(false);
			jsonResult.setMessage("手机号码存在");
			return jsonResult;
		}/*else if(flag==true){
			jsonResult.setSuccess(false);
			jsonResult.setMessage("账号已经发放完毕");
			return jsonResult;
		}else if(count>5){
			jsonResult.setSuccess(false);
			jsonResult.setMessage("账号已经发放完毕");
			return jsonResult;
		}*/
		String id=(String) map.get("id");
		Questionnaire entity=null;
		if(StringUtil.isNotBlank(id)){
			 entity=questionnaireService.get(id);
			if(entity==null)
				 entity=new Questionnaire();
		}else{
			 entity=new Questionnaire();
		}
	
		try {
			BeanUtils.populate(entity,map);//map转为实体
			if(StringUtil.isNotBlank(birthday)){
				Date date=Dates.parse(birthday, "yyyy-MM-dd");
				Long time=date.getTime()/1000;
				entity.setBirthday(time);
			}
			if(StringUtils.isNotBlank(entity.getId())){
				questionnaireService.update(entity);
				jsonResult.setObj(entity);
			}else{
				
				Questionnaire questionnaire=questionnaireService.insertEntity(entity);
				jsonResult.setObj(questionnaire);
			}
			jsonResult.setSuccess(true);
		} catch (Exception e) {
			jsonResult.setMessage("系统出现错误");
			logger.error("实体转化错误"+e.getMessage());
		} 
		return  jsonResult;
	}
	
	/** 
	 * 根据名字获取cookie 
	 * @param request 
	 * @param name cookie名字 
	 * @return 
	 */
	public  Cookie getCookieByName(HttpServletRequest request,String name){ 
	    Map<String,Cookie> cookieMap = ReadCookieMap(request); 
	    if(cookieMap.containsKey(name)){ 
	        Cookie cookie = (Cookie)cookieMap.get(name); 
	        return cookie; 
	    }else{ 
	        return null; 
	    }    
	} 
	
	/** 
	 * 将cookie封装到Map里面 
	 * @param request 
	 * @return 
	 */
	private  Map<String,Cookie> ReadCookieMap(HttpServletRequest request){   
	    Map<String,Cookie> cookieMap = new HashMap<String,Cookie>(); 
	    Cookie[] cookies = request.getCookies(); 
	    if(null!=cookies){ 
	        for(Cookie cookie : cookies){ 
	            cookieMap.put(cookie.getName(), cookie); 
	        } 
	    } 
	    return cookieMap; 
	} 
	/**
	 * 传数值转为map
	 * @param request
	 * @return
	 * @date 2015年2月11日
	 * @author zhangjun
	 */
	public static Map getMapByRequest(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		Enumeration enu = request.getParameterNames();
		while(enu.hasMoreElements()) {   
            String paraName = (String)enu.nextElement();   
            String paraValue = request.getParameter(paraName).trim(); 
            if(paraValue!=null && !"".equals(paraValue)){
            	map.put(paraName, paraValue);
            }           
		}
				
		return map;
	}
}
