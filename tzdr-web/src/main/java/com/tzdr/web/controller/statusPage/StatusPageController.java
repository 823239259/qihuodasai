package com.tzdr.web.controller.statusPage;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.service.operational.OperationalConfigService;
import com.tzdr.business.service.securityInfo.impl.SecurityInfoServiceImpl;
import com.tzdr.business.service.userTrade.UserTradeService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.utils.BigDecimalUtils;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.StringCodeUtils;
import com.tzdr.domain.vo.OperationalConfigVo;
import com.tzdr.domain.vo.UserTradeVo;
import com.tzdr.domain.vo.WuserVo;
import com.tzdr.domain.web.entity.OperationalConfig;
import com.tzdr.domain.web.entity.UserVerified;
import com.tzdr.domain.web.entity.WUser;
import com.tzdr.web.constants.Constants;
import com.tzdr.web.controller.homepage.OnlineCounter;
import com.tzdr.web.utils.PageStatusUtil;

/**
 * @Description:
 * @ClassName: UserTradeController.java
 * @author Lin Feng
 * @date 2015年1月5日
 */
@Controller
@RequestMapping("/statusPage")
public class StatusPageController {
	
	@Autowired
	private OperationalConfigService operationalConfigService;
	
	@Autowired
	private WUserService wuserService;
	
	@Autowired
	private SecurityInfoServiceImpl securityInfoService;
	
	@Autowired
	private UserTradeService userTradeService;

	private static Logger log = LoggerFactory
			.getLogger(StatusPageController.class);
	
	/**
	 * 
	 * @return  Map<String,Object>
	 */
	public Map<String,Object> getData() {
		
		//WUser user=(WUser) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		WUser user = null;
		Double totalLending = 0.00;     //操盘中总配资金额
		String mobile="";
		String lastLoginTime="";
		 UserVerified verified=null;
		if (user != null){
			 user = this.wuserService.get(user.getId());
			 verified = securityInfoService.findByUserId(user.getId());
			 mobile = StringCodeUtils.buildMobile(user.getMobile());
			 Long loginTime = user.getLastLoginTime();
			 Date date = Dates.parseLong2Date(loginTime);
			 lastLoginTime = Dates.format(date);
			 
			//获取用户进行中的配资列表信息
			List<UserTradeVo> userTradeVoList = userTradeService.queryUserTradeVoByWuserAndStatus(user.getId(), (short)1, (short)0);
			if(userTradeVoList != null && !userTradeVoList.isEmpty()){
				for (UserTradeVo userTradeVo : userTradeVoList) {
					totalLending = BigDecimalUtils.add2(totalLending, userTradeVo.getTotalLending());
				}
			}
		}
		
		List<WuserVo> usercount=this.wuserService.getUserCount();
		List<WuserVo> moneycount=this.wuserService.getUserTradecount();
		String usercountstr=this.transnum(usercount.get(0).getUsercount().longValue()*100);
		String moneystr=this.transmoneynum(moneycount.get(0).getTotalMoney()*1000);
	
		
		List<OperationalConfig> newscols=this.operationalConfigService.findData(Constants.Mainpage.NEWSCOLUMN_STATUS);
		List<OperationalConfig> banners=this.operationalConfigService.findData(Constants.Mainpage.BANNER_STATUS);
		List<OperationalConfig> backgrounds=this.operationalConfigService.findData(Constants.Mainpage.BACKGROUND_STATUS);
		List<OperationalConfig> links=this.operationalConfigService.findData(Constants.Mainpage.LINKS_STATUS);
		List<OperationalConfig> partners=this.operationalConfigService.findData(Constants.Mainpage.PARTNER_STATUS);
		
		List<OperationalConfig> bankimgs=this.operationalConfigService.findData(Constants.Mainpage.BANK_STATUS);
		List<OperationalConfigVo> newsdata=this.operationalConfigService.findTopNews(1,3);
		//List<WuserVo> wusers=this.wUserService.findProfit(6);
		Map<String,List<OperationalConfig>> map=new HashMap<String,List<OperationalConfig>>();
		for(OperationalConfig col:newscols){
			String colname=col.getName();
			List<OperationalConfig> list=operationalConfigService.findNews(Constants.Mainpage.NEWS_STATUS, col);
			map.put(colname, list);
		}
		//在线用户
		 int activeUserCount = OnlineCounter.getInstance().getActiveUser();
		 Random rdm = new Random();  
	     int rdmnum = rdm.nextInt(100-80+1)+80;  
	     activeUserCount += rdmnum;
	     long countUser = wuserService.countUser();
	     
	     
		//首页推广信息
	     /**
		if(!StringUtil.isBlank(uid)){
			Cookie userIdCookie = new Cookie(com.tzdr.web.constants.Constants.TZDR_GENERALIZEUID, uid);
			userIdCookie.setMaxAge(com.tzdr.web.constants.Constants.USERID_COOKIEMAX_VALUE);
			response.addCookie(userIdCookie);
			request.getSession().setAttribute(com.tzdr.web.constants.Constants.TZDR_GENERALIZEUID, uid);
			//保存访问记录
			GeneralizeVisit generalizeVisit = new GeneralizeVisit();
			WUser wuser =  wUserService.getUser(uid);
			generalizeVisit.setWuser(wuser);
			generalizeVisit.setClieantIp(IpUtils.getIpAddr(request));
			generalizeVisit.setCreatedate((new Date().getTime()/1000));
			generalizeService.saveGeneralizeVisit(generalizeVisit);
		}
		**/
		Map<String,Object> modelMap = new HashMap<String,Object>();
		modelMap.put("moneystr", moneystr);
		modelMap.put("usercountstr", usercountstr);
		modelMap.put("verified", verified);
		modelMap.put("lastLoginTime", lastLoginTime);
		modelMap.put("mobile", mobile);
		modelMap.put("totalLending", totalLending);
		modelMap.put("user", user);
		modelMap.put("backgrounds", backgrounds);
		modelMap.put("partners", partners);
		modelMap.put("bankimgs", bankimgs);
		modelMap.put("countUser", countUser);
		modelMap.put("activeUserCount", activeUserCount);
		modelMap.put("newsdata", newsdata);
		modelMap.put("newscols", newscols);
		modelMap.put("banners", banners);
		modelMap.put("links", links);
		return modelMap;
	  
		
	}
	
	/**
	 * 数字转化
	 * @param num
	 * @param mulnum
	 * @return
	 * @date 2015年2月5日
	 * @author zhangjun
	 */
	private  String transmoneynum(double num){
		if(num>=100000000){//大于1亿
			double val=BigDecimalUtils.div(num,100000000l);
			if(String.valueOf(val).indexOf(".")>0){
				 double x = Math.floor(val);
			     int y = (int)x;
			     String s = Integer.toString(y);
			     String d = Double.toString(val); 
			     d = d.substring(s.length()+1,d.length());  
			     d="0."+d;
			     long numval=Math.round((Double.valueOf(d)*10000));
			     if(numval>0){
			    	  return s+"亿"+numval+"万";  
			     }else{
			    	  return s+"亿";  
			     }
			   
			 }
		}else if(num>=10000){
			double val=BigDecimalUtils.div(num,10000);
			if(String.valueOf(val).indexOf(".")>0){
				 double x = Math.floor(val);
			     int y = (int)x;
			     String s = Integer.toString(y);
			     String d = Double.toString(val); 
			     d = d.substring(s.length()+1,d.length());  
			     d="0."+d;
			     long numval=Math.round((Double.valueOf(d)*10000));
			     if(numval>0){
			    	 return s+"万"+numval;  
			     }else{
			    	 return s+"万";  
			     }
			    
			 }
		}else{
			return String.valueOf(num);
		}
		return null;
	}
	
	/**
	 * 配置人数转化
	 * @param num
	 * @return
	 * @date 2015年2月5日
	 * @author zhangjun
	 */
	private  String transnum(long num){
		 if(num>=10000){
			double val=BigDecimalUtils.div(num,10000);
			DecimalFormat df = new DecimalFormat("#.0");    
			return df.format(val);  
	   }else{
			return String.valueOf(num);
		}
		
	}

	
	@RequestMapping(value = "/statusUpdate")
	@ResponseBody
	public String statusUpdate(HttpServletRequest request,HttpServletResponse response) {
		
		//测试静态
		try {
			String pageStatus = request.getParameter("templateName");
			if (pageStatus == null || "".equals(pageStatus)) {
				return "none";
			}
			PageStatusUtil.writeHtml(request.getServletContext(),pageStatus + ".ftl",getData());
			return "success";
		} 
		catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
	}


	public OperationalConfigService getOperationalConfigService() {
		return operationalConfigService;
	}


	public void setOperationalConfigService(
			OperationalConfigService operationalConfigService) {
		this.operationalConfigService = operationalConfigService;
	}

	public WUserService getWuserService() {
		return wuserService;
	}

	public void setWuserService(WUserService wuserService) {
		this.wuserService = wuserService;
	}


	public SecurityInfoServiceImpl getSecurityInfoService() {
		return securityInfoService;
	}


	public void setSecurityInfoService(SecurityInfoServiceImpl securityInfoService) {
		this.securityInfoService = securityInfoService;
	}


	public UserTradeService getUserTradeService() {
		return userTradeService;
	}


	public void setUserTradeService(UserTradeService userTradeService) {
		this.userTradeService = userTradeService;
	}

	
}
