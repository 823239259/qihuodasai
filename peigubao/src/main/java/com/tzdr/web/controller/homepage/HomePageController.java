package com.tzdr.web.controller.homepage;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.tzdr.business.hkstock.service.HkUserTradeService;
import com.tzdr.business.service.bespokeTrade.BespokeTradeService;
import com.tzdr.business.service.company.CompanyCommissionService;
import com.tzdr.business.service.generalize.GeneralizeService;
import com.tzdr.business.service.homepagecinfig.HomePageCinfigService;
import com.tzdr.business.service.notice.NoticeService;
import com.tzdr.business.service.operational.OperationalConfigService;
import com.tzdr.business.service.securityInfo.impl.SecurityInfoServiceImpl;
import com.tzdr.business.service.userTrade.UserTradeService;
import com.tzdr.business.service.volume.impl.VolumeDetailServiceImpl;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.utils.BigDecimalUtils;
import com.tzdr.common.utils.ConfUtil;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.StringCodeUtils;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.cache.CachePeiGuBaoHomePageManager;
import com.tzdr.domain.vo.GeneralizeVisitUserVo;
import com.tzdr.domain.vo.OperationalConfigVo;
import com.tzdr.domain.vo.UserTradeVo;
import com.tzdr.domain.vo.UserVolumeDetailVo;
import com.tzdr.domain.vo.WuserVo;
import com.tzdr.domain.web.entity.CompanyCommission;
import com.tzdr.domain.web.entity.GeneralizeVisit;
import com.tzdr.domain.web.entity.Notice;
import com.tzdr.domain.web.entity.OperationalConfig;
import com.tzdr.domain.web.entity.UserVerified;
import com.tzdr.domain.web.entity.WUser;
import com.tzdr.web.constants.Constants;
import com.tzdr.web.constants.ViewConstants;
import com.tzdr.web.utils.UserSessionBean;
import com.tzdr.web.utils.WebUtil;


/**
 * 主页
 * <P>title:@MainPage.java</p>																								
 * <P>Description：</p>
 * <p>Copyright: Copyright (c) 2015 tzdr</p>
 * <p>Company: 上海信闳</p>
 * History：
 * @author:zhangjun
 * @date 2015年1月21日
 * @version 1.0
 */
@Controller
@RequestMapping("/")
public class HomePageController {
	public static final Logger logger = LoggerFactory.getLogger(HomePageController.class);

	@Autowired
	private OperationalConfigService operationalConfigService;
	@Autowired
	private WUserService wUserService;
	@Autowired
	private SecurityInfoServiceImpl securityInfoService;
	@Autowired
	private UserTradeService userTradeService;
	@Autowired
	private GeneralizeService generalizeService;
	@Autowired
	private HomePageCinfigService homePageCinfigService;
	@Autowired
	private NoticeService noticeService;
	@Autowired
	HkUserTradeService hkUserTradeService;
	@Autowired
	private CompanyCommissionService companyCommissionService;
	
	@Autowired
	private VolumeDetailServiceImpl volumeDetailServiceImpl;
	@Autowired
	private BespokeTradeService  bespokeTradeService;

	/**
	* @Description: TODO(推广访问首页)
	* @Title: homepageByParam
	* @param modelMap
	* @param uid
	* @param request
	* @param response
	* @return void    返回类型
	 */
	@RequestMapping(value = "/{uid:[0-9]*}")
	public String homepageByParam(ModelMap modelMap,@PathVariable("uid") String uid,String p,String from,HttpServletRequest request,HttpServletResponse response){
		
		Cookie userIdCookie = new Cookie(Constants.TZDR_GENERALIZEUID, uid);  //佣金推广
		userIdCookie.setMaxAge(Constants.USERID_COOKIEMAX_VALUE);
		response.addCookie(userIdCookie);
		request.getSession().setAttribute(Constants.TZDR_GENERALIZEUID, uid);
		
		if(!StringUtil.isBlank(p)&& !"null".equals(p)){      //渠道推广
			Cookie channelCookie = new Cookie(Constants.TZDR_CHANNEL, p);
			channelCookie.setMaxAge(Constants.USERID_COOKIEMAX_VALUE);
			response.addCookie(channelCookie);
			request.getSession().setAttribute(Constants.TZDR_CHANNEL, p);
		}
		
		if(!StringUtil.isBlank(from) && !"null".equals(from)){  //源URL
			Cookie channelFromCookie = new Cookie(Constants.TZDR_CHANNEL_FROM, from);
			channelFromCookie.setMaxAge(Constants.USERID_COOKIEMAX_VALUE);
			response.addCookie(channelFromCookie);
			request.getSession().setAttribute(Constants.TZDR_CHANNEL_FROM, from);
		}
		
		//首页记录推广信息(佣金推广或死渠道推广)
		GeneralizeVisit generalizeVisit = WebUtil.getGeneralizeVisit(request, response);
		if(generalizeVisit != null){
			generalizeVisit.setGeneralizeId(uid);
			generalizeService.saveGeneralizeVisit(generalizeVisit);
		}
		
		return "redirect:/" ;
	}
	
	/**
	 * 到首页
	 * @param modelMap
	 * @param uid 前端传入的用户id
	 * @param request
	 * @param response
	 * @date 2015年1月21日
	 * @author zhangjun
	 */
	@RequestMapping(value = "/")
	public String homepage(ModelMap modelMap,String uid,HttpServletRequest request,HttpServletResponse response){
		
		UserSessionBean userSessionBean=(UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		WUser user= null;
		String mobile="";
		//A股方案个数
		Integer  tradeCount = 0;
		
		//港股方案个数
		Integer hkTradeCount = 0;
		
		List<OperationalConfig> banners = CachePeiGuBaoHomePageManager.bannersCache;
		
		if(banners == null || banners.size() <= 0){
			banners=this.operationalConfigService.findBanner(Constants.Mainpage.BANNER_STATUS);
		}
		if(userSessionBean!=null){
			mobile=userSessionBean.getMobile();
			mobile=StringCodeUtils.buildMobile(mobile);
			//得到用户信息
			user=wUserService.getUser(userSessionBean.getId());
			tradeCount = userTradeService.getCountUserTradesByUid(userSessionBean.getId());
			hkTradeCount = hkUserTradeService.getCountHKUserTradesByUid(userSessionBean.getId());
		}
		modelMap.put("mobile", mobile);
		modelMap.put("avlbal", user == null || user.getAvlBal() == null ? 0.00 : user.getAvlBal());
		modelMap.put("tradeCount", tradeCount == null ? 0 : tradeCount);
		modelMap.put("hkTradeCount", hkTradeCount == null ? 0 : hkTradeCount);
		modelMap.put("banners", banners);
		
		return ViewConstants.HomePageViewJsp.HOME_PAGE;
	}
	
	/**
	 * 查询友情链接 用于登陆后的显示,使用JSONP
	 * @param modelMap
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/findLinks")
	public void findLinks(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		StringBuffer buffer=new StringBuffer();
		JsonResult jsonResult = new JsonResult(false);
		List<OperationalConfig> links=this.operationalConfigService.findData(Constants.Mainpage.LINKS_STATUS);
		if(!CollectionUtils.isEmpty(links)) {
			jsonResult.setSuccess(true);
			jsonResult.setObj(links);
		}
		buffer.append("findLinks(");
		buffer.append(JSONObject.toJSONString(jsonResult));
		buffer.append(");");
		WebUtil.printText(buffer.toString(), response);
	}
	
	
	/**
	 * 是否登陆
	 * @param request
	 * @param response
	 * @return
	 * @date 2015年2月6日
	 * @author zhangjun
	 */
	@SuppressWarnings("null")
	@RequestMapping(value = "/homeIslogin")
	@ResponseBody
	private JsonResult homeIslogin(HttpServletRequest request,HttpServletResponse response) {
		UserSessionBean userSessionBean=(UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		JsonResult jsonResult = new JsonResult(false);
		if(userSessionBean!=null){
			 String mobile="";
			 Double totalLending = 0.00;//操盘中总配资金额
			 WuserVo uservo=new WuserVo();
			 WUser user=this.wUserService.get(userSessionBean.getId());
			 int level=user.getLevel()==null?0:user.getLevel();
			 UserVerified verified=securityInfoService.findByUserId(user.getId());
			 mobile=StringCodeUtils.buildMobile(user.getMobile());
			 Long loginTime=user.getLastbeforeloginTime()==null?user.getLastLoginTime():user.getLastbeforeloginTime();
			 Date date=Dates.parseLong2Date(loginTime);
			 String lastLoginTime=Dates.format(date);//登陆时间
			 jsonResult.setSuccess(true);
			 //获取用户进行中的配资列表信息
				List<UserTradeVo> userTradeVoList = userTradeService.queryUserTradeVoByWuserAndStatus(userSessionBean.getId(), (short)1, (short)0);
				if(userTradeVoList != null && !userTradeVoList.isEmpty()){
					for (UserTradeVo userTradeVo : userTradeVoList) {
						totalLending = BigDecimalUtils.add2(totalLending, userTradeVo.getTotalMoney());
					}
				}
		
			 DecimalFormat df = new DecimalFormat("0.00");   
			 if(level>2){
				 level=level-2;
			 }
			 uservo.setLevel(level);
			 uservo.setMobile(mobile);
			 uservo.setTotalLending(df.format(totalLending));
			 uservo.setAvlBal(df.format(user.getAvlBal()));
			 uservo.setLastLoginTime(lastLoginTime);
			 List<UserVolumeDetailVo> userVolumeDetailVoList = volumeDetailServiceImpl.findVolumeByUId(userSessionBean.getId(), -1);
			 uservo.setValidVolumeNum(userVolumeDetailVoList == null && userVolumeDetailVoList.isEmpty() ? 0: userVolumeDetailVoList.size());
			 if(StringUtil.isNotBlank(user.getEmail())){
				 uservo.setIsemail("已绑定");
			 }else{
				 uservo.setIsemail("未绑定");
			 }
			 if(verified!=null && StringUtil.isNotBlank(verified.getIdcard())){
				 uservo.setIsidcard("已绑定");
			 }else{
				 uservo.setIsidcard("未绑定");
			 }
			 jsonResult.setObj(uservo);
		}
		return jsonResult;
	}
	
	/**
	 * 快捷方式
	 * @param request
	 * @param response
	 * @return
	 * @date 2015年1月30日
	 * @author zhangjun
	 */
	@RequestMapping(value = "/quickToDesktop")
	public String quickToDesktop(HttpServletRequest request,HttpServletResponse response){
		String filename = "投资达人.url";   
		String realfilename = "tzdr.url";   
	    String contextpath =request.getServletContext().getRealPath("");
	    String path=contextpath+File.separator+"static"+File.separator+"url"+File.separator+realfilename;
	    try {   
	        File file = new File(path);   
	        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));   
	        byte[] buffer = new byte[1024];   
	        if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {   
	        	filename = new String(realfilename.getBytes("UTF-8"), "ISO8859-1");   
	         } else{   
	        	filename = java.net.URLEncoder.encode(realfilename,"UTF-8");   
	         }
		        response.reset();   
		        response.setCharacterEncoding("UTF-8");   
		        response.setContentType("application/x-download");
		        response.setHeader("Content-Disposition",   
		        "attachment; filename=" + filename);   
		        OutputStream os = response.getOutputStream();   
	        while (bis.read(buffer) > 0) {   
	            os.write(buffer);   
	        }   
	        bis.close();   
	        os.close();   
	    } catch (Exception e) {   
	    	logger.error("发送到桌面快捷方式出错"+e.getMessage());
	      
	    }  
		
		return null;
	}
	
	/**
	 * 数字转化
	 * @param num
	 * @param mulnum
	 * @return
	 * @date 2015年2月5日
	 * @author zhangjun
	 */
	@SuppressWarnings("unused")
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
	@SuppressWarnings("unused")
	private  String transnum(long num){
		 if(num>=10000){
			double val=BigDecimalUtils.div(num,10000);
			DecimalFormat df = new DecimalFormat("#.0");    
			String numstr=df.format(val);  
			return numstr+"万";
	   }else{
			return String.valueOf(num);
		}
		
	}

	/**
	 * 推广佣金查询
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @date 2015年2月9日
	 * @author zhangjun
	 */
	@RequestMapping(value = "/homegeneralize")
	public String homegeneralize(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		//获取推广佣金排名
		List<WUser> wuserList = wUserService.queryByCommissionDesc();
		
		
		List<GeneralizeVisitUserVo> generalizeVisitUserVoList = new ArrayList<GeneralizeVisitUserVo>();
		String isOpen = ConfUtil.getContext("is.open.company.commission");
		if(Boolean.valueOf(isOpen)){
			List<CompanyCommission> companyCommissionList = companyCommissionService.findOrderByTotalCommissionDesc();
			if(companyCommissionList != null){
				for (CompanyCommission companyCommission : companyCommissionList) {
					GeneralizeVisitUserVo generalizeVisitUserVo = new GeneralizeVisitUserVo();
					generalizeVisitUserVo.setMobile(StringCodeUtils.buildMobile(companyCommission.getMobile()));
					generalizeVisitUserVo.setTotalCommission(companyCommission.getTotalCommission());
					generalizeVisitUserVoList.add(generalizeVisitUserVo);
				}
			}
		}else{
			wuserList = wUserService.queryByCommissionDesc();
			if(wuserList != null && !wuserList.isEmpty()){
				for (WUser wuserSort : wuserList) {
					if("-1".equals(wuserSort.getUserType()) || "-2".equals(wuserSort.getUserType()) || "-3".equals(wuserSort.getUserType())) continue;
					GeneralizeVisitUserVo generalizeVisitUserVo = new GeneralizeVisitUserVo();
					generalizeVisitUserVo.setMobile(StringCodeUtils.buildMobile(wuserSort.getMobile()));
					generalizeVisitUserVo.setTotalCommission(wuserSort.getTotalCommission());
					generalizeVisitUserVoList.add(generalizeVisitUserVo);
				}
			}
		}
		
		modelMap.put("generalizeVisitUserVoList", generalizeVisitUserVoList);
		return ViewConstants.HomePageViewJsp.GENERALIZE_PAGE;
	}
	
	/** 
	 * 设置cookie 
	 * @param response 
	 * @param name  cookie名字 
	 * @param value cookie值 
	 * @param maxAge cookie生命周期  以秒为单位 
	 */
	public static void addCookie(HttpServletResponse response,String name,String value,int maxAge){ 
	    Cookie cookie = new Cookie(name,value); 
	    cookie.setPath("/"); 
	    if(maxAge>0)  cookie.setMaxAge(maxAge); 
	    response.addCookie(cookie); 
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

	
	@RequestMapping(value = "/findBanners")
	@ResponseBody
	public List<OperationalConfig> findBanners(ModelMap modelMap,String height,HttpServletRequest request,HttpServletResponse response){
		if(StringUtil.isNotBlank(height)){
			int heightnum=Integer.valueOf(height);
			if(heightnum>=1000){
				List<OperationalConfig> bigbanners=this.operationalConfigService.findBanners(Constants.Mainpage.BANNER_STATUS,Constants.Mainpage.BIGVALUE_TYPE);
				return bigbanners;
			}
		}
		//小图banner
		List<OperationalConfig> smallbanner=this.operationalConfigService.findBanners(Constants.Mainpage.BANNER_STATUS, Constants.Mainpage.VALUE_TYPE);
		
		return smallbanner;
	}
	
	
	@RequestMapping(value = "/abort")
	public String abort(HttpServletRequest request,HttpServletResponse response){
		return ViewConstants.HomePageViewJsp.ABORT_PAGE;
	}
	
	@RequestMapping(value = "/company")
	public String company(HttpServletRequest request,HttpServletResponse response){
		return ViewConstants.HomePageViewJsp.COMPANY_PAGE;
	}
	
	
	@RequestMapping(value = "/partner")
	public String partner(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){	
		List<OperationalConfig> partners=this.operationalConfigService.findData(Constants.Mainpage.PARTNER_STATUS);
		modelMap.put("partners", partners);
		return ViewConstants.HomePageViewJsp.PARTNER_PAGE;
	}
	
	
	@RequestMapping(value = "/netmap")
	public String netmap(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){	
		
		return ViewConstants.HomePageViewJsp.NET_MAP;
	}
	
	
	@RequestMapping(value = "/contact")
	public String contact(HttpServletRequest request,HttpServletResponse response){
		return ViewConstants.HomePageViewJsp.CONTACT_PAGE;
	}
	
	/**
	 * 公司展示
	 * @MethodName companypic
	 * @author L.Y
	 * @date 2015年4月30日
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/companypic")
	public String companypic(HttpServletRequest request,HttpServletResponse response){
		return ViewConstants.HomePageViewJsp.COMPANYPIC_PAGE;
	}
	
	/**
	 * 注册送
	 * @MethodName regSend
	 * @author L.Y
	 * @date 2015年5月5日
	 * @return
	 */
	@RequestMapping(value = "/regsend")
	public String regSend() {
		return ViewConstants.HomePageViewJsp.REG_SEND;
	}
	
	/**
	 * 获取股票指数
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/findstockindex")
	@ResponseBody
	private JsonResult findStockIndex(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		JsonResult  jsonResult = new JsonResult(true);
		
		String url = ConfUtil.getContext("stock.index.url")+"/list="+Constants.StockIndex.S_SZ+","+Constants.StockIndex.S_SH+","+Constants.StockIndex.S_SZ_GEM+","+Constants.StockIndex.S_SZ_SMALL;
		
		String result = TypeConvert.httpClientComplex(url,"GBK",null);
		
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		
		if(!StringUtil.isBlank(result)){
			 String[] stocks = result.split(";");  
			 if(stocks != null && stocks.length > 0){
				 for (String stock : stocks) {  
	                 String[] datas = stock.split(","); 
	                 if(datas != null && datas.length > 2){
	                	 Map<String, Object> stockData = new HashMap<String, Object>(); 
	                	 if(datas[0].contains(Constants.StockIndex.S_SZ)){
		                	 stockData.put("name", "shenzhen");
		                 }else if(datas[0].contains(Constants.StockIndex.S_SH)){
		                	 stockData.put("name", "shanghai");
		                 }else if(datas[0].contains(Constants.StockIndex.S_SZ_GEM)){
		                	 stockData.put("name", "gem");
		                 }else if(datas[0].contains(Constants.StockIndex.S_SZ_SMALL)){
		                	 stockData.put("name", "small");
		                 }
		                 stockData.put("data1", String.format("%.2f", BigDecimalUtils.round2(Double.valueOf(datas[1]), 2)));
		                 stockData.put("data2", String.format("%.2f", BigDecimalUtils.round2(Double.valueOf(datas[2]), 2)));
		                 stockData.put("data3", String.format("%.2f", BigDecimalUtils.round2(Double.valueOf(datas[3]), 2)));
		                 data.add(stockData);
	                 }
	             } 
			 }
		}
		jsonResult.setObj(data);
		return jsonResult;
	}
	
	
	
	/**
	 * 获取最新公告
	 */
	@RequestMapping(value="/findnewData")
	@ResponseBody
	public List<Notice> findnewData(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		List<Notice> nitives=noticeService.findPGBNotices(2, 2);
		return nitives;
		
	}
	
	/**
	 * 获取六条最新动态
	 */
	@RequestMapping(value="/findnews")
	@ResponseBody
	public List<OperationalConfigVo> findnews(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		List<OperationalConfigVo> newsdata=this.operationalConfigService.findTopNews(1,3);
		return newsdata;
	}
}
