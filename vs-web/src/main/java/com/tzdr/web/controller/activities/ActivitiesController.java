package com.tzdr.web.controller.activities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jodd.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tzdr.business.service.activities.ActivitiesService;
import com.tzdr.common.utils.Dates;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cache.DataDicKeyConstants;
import com.tzdr.domain.entity.DataMap;
import com.tzdr.domain.vo.ActivityProfitRecordVo;
import com.tzdr.web.constants.ViewConstants;


/**
 * 活动页面
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
public class ActivitiesController {
	
	@Autowired
	private ActivitiesService activitiesService;
	/**
	 * 到问卷调查页面
	 * @param request
	 * @return
	 * @date 2015年2月6日
	 * @author zhangjun
	 */
	@RequestMapping(value = "/toactivities")
	public String activities(ModelMap modelMap,HttpServletRequest request){
		List<DataMap>  startdata=CacheManager.getDataMapListByTypeKey(DataDicKeyConstants.ACTI_START);
		List<DataMap>  enddata=CacheManager.getDataMapListByTypeKey(DataDicKeyConstants.ACTI_END);
		String starttime=startdata.get(0).getValueKey();
		String endtime=enddata.get(0).getValueKey();
		Date nowdate=new Date();
		Date startdate=Dates.parse(starttime, "yyyy-MM-dd HH:mm:ss");
		Date enddate=Dates.parse(endtime, "yyyy-MM-dd HH:mm:ss");
		//活动期间才显示
		if(nowdate.getTime()>=startdate.getTime() && nowdate.getTime()<=enddate.getTime()){
			modelMap.put("isshow", "show");
		}
		Long yesterday=Dates.getDatebyDaynum(-1);//昨天的时间
		Long beforeyesterday=Dates.getDatebyDaynum(-2);//前天的时间
		List<ActivityProfitRecordVo> list=activitiesService.getActivities(yesterday);
		List<ActivityProfitRecordVo> stocklist=activitiesService.getStockData();
		//增幅榜
		List<Map<String,String>> growlist=activitiesService.getGrowActivities(yesterday,beforeyesterday);
		modelMap.put("growlist", growlist);
		modelMap.put("records", list);
		modelMap.put("stocklist", stocklist);
		return ViewConstants.ActiviesJsp.ACTIVIES;
	}
	
	/**
	 * 根据股票代码获取股票名称
	 * @param stockCode
	 * @return
	 * @throws IOException
	 * @date 2015年3月9日
	 * @author zhangjun
	 */
	  public  String  getStockInfoByCode(String stockCode) throws IOException{
		  String stockname=this.getStockInfoByshCode(stockCode);
		  if(StringUtil.isBlank(stockname))
			  stockname=this.getStockInfoByszCode(stockCode);
		  return stockname;
	  }
	
	/**
	 * 获取沪市股票名称
	 * @param stockCode
	 * @return
	 * @throws IOException
	 * @date 2015年3月9日
	 * @author zhangjun
	 */
	  public  String  getStockInfoByshCode(String stockCode) throws IOException{  
	           String[] stockInfo = new String[32] ;    
	           URL url = new URL("http://hq.sinajs.cn/?list=sh"+stockCode) ;   
	           URLConnection connection = url.openConnection() ;   
	           connection.setConnectTimeout(16000) ;   
	           BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(),"GBK")) ;   
	           String line = null ;   
	           StringBuffer sb = new StringBuffer() ;   
	           while(( line = br.readLine()) != null ){   
	               sb.append(line) ;   
	          }   
	           if(sb.length() > 0 ){   
	               String rs = sb.toString() ;   
	               rs = rs.substring(rs.indexOf("\"")+1,rs.lastIndexOf("\"")) ;   
	               String[] rss = rs.split(",") ;   
	               for(int i = 0 ;  i< rss.length ; i ++ ){   
	                 stockInfo[i] = rss[i];   
	              }   
	           }   
	          return stockInfo[0] ;   
	     }   
	
	  /**
	   * 获取深市股票名称
	   * @param stockCode
	   * @return
	   * @throws IOException
	   * @date 2015年3月9日
	   * @author zhangjun
	   */
	  public  String  getStockInfoByszCode(String stockCode) throws IOException{
         String[] stockInfo = new String[32] ;    
         URL url = new URL("http://hq.sinajs.cn/?list=sz"+stockCode) ;   
         URLConnection connection = url.openConnection() ;   
         connection.setConnectTimeout(16000) ;   
         BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(),"GBK")) ;   
         String line = null ;   
         StringBuffer sb = new StringBuffer() ;   
         while(( line = br.readLine()) != null ){   
             sb.append(line) ;   
        }   
         if(sb.length() > 0 ){   
             String rs = sb.toString() ;   
             rs = rs.substring(rs.indexOf("\"")+1,rs.lastIndexOf("\"")) ;   
             String[] rss = rs.split(",") ;   
             for(int i = 0 ;  i< rss.length ; i ++ ){   
               stockInfo[i] = rss[i];   
            }   
         }   
        return stockInfo[0] ;   
   }   

	/**
	 * 活动细则
	 * @param modelMap
	 * @param request
	 * @return
	 * @date 2015年3月3日
	 * @author zhangjun
	 */
	@RequestMapping(value = "/toactivitiesDetail")
	public String activitiesDetail(ModelMap modelMap,HttpServletRequest request){
		return ViewConstants.ActiviesJsp.ACTIVIES_DETAIL;
	}
	
	
	
}
