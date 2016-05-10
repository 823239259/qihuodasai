package com.tzdr.web.controller.news;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.service.operational.OperationalConfigService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.Dates;
import com.tzdr.domain.web.entity.OperationalConfig;
import com.tzdr.web.constants.Constants;
import com.tzdr.web.constants.ViewConstants;


/**
 * 新闻
 * <P>title:@NewsController.java</p>																								
 * <P>Description：</p>
 * <p>Copyright: Copyright (c) 2015 tzdr</p>
 * <p>Company: 上海信闳</p>
 * History：
 * @author:zhangjun
 * @date 2015年2月2日
 * @version 1.0
 */
@Controller
@RequestMapping("/news")
public class NewsController {
	public static final Logger logger = LoggerFactory.getLogger(NewsController.class);

	@Autowired
	private OperationalConfigService operationalConfigService;
	


	@RequestMapping(value = "/shownews/{id:[A-Za-z0-9]{32}}")
	public String shownews(ModelMap modelMap,@PathVariable("id") String id, String type,HttpServletRequest request,HttpServletResponse response){
		OperationalConfig news=operationalConfigService.get(id);
		List<OperationalConfig> newscols=this.operationalConfigService.findData(Constants.Mainpage.NEWSCOLUMN_STATUS);
		Long loginTime=news.getCreateTime();
		 Date date=Dates.parseLong2Date(loginTime);
		 String createtime=Dates.format(date);
		modelMap.put("news", news);
		modelMap.put("newscols", newscols);
		modelMap.put("type", type);//新闻中心的页面单独处理
		modelMap.put("createtime", createtime);
		return ViewConstants.NewsViewJsp.NEWS_VIEW;
	}
	
	@RequestMapping(value = "/newsdata")
	public String newsdata(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		List<OperationalConfig> newscols=this.operationalConfigService.findData(Constants.Mainpage.NEWSCOLUMN_STATUS);
		List<OperationalConfig> links=this.operationalConfigService.findData(Constants.Mainpage.LINKS_STATUS);
		//首页导航过来的新闻栏目
		String colname=request.getParameter("colname");
		if(StringUtil.isNotBlank(colname)){
			try {
			    colname=java.net.URLDecoder.decode(colname,"UTF-8");
				//colname=new String(colname.getBytes("ISO-8859-1"));
				OperationalConfig col=this.operationalConfigService.getColByName(colname);
				if(col==null){
					//colname=new String(colname.getBytes("UTF-8"));
					col=this.operationalConfigService.get(colname);
				}
				modelMap.put("col", col);
			} catch (UnsupportedEncodingException e) {
				logger.error("获取新闻栏目名称错误"+e.getMessage());
			} 
		}else{
			//首页底部进来的新闻
			modelMap.put("first", "false");
		}
		
		modelMap.put("links", links);
		modelMap.put("newscols", newscols);
		return ViewConstants.NewsViewJsp.NEWS_PAGE;
	}
	
	@RequestMapping(value = "/datapage")
	@ResponseBody
	public PageInfo<OperationalConfig> datapage(HttpServletResponse response,HttpServletRequest request){
		String pageIndex=request.getParameter("pageIndex")==null?"0":request.getParameter("pageIndex");
		String perPage=request.getParameter("perPage")==null?"10":request.getParameter("perPage");	
		String colid=request.getParameter("colid");	
		PageInfo<OperationalConfig>  page=operationalConfigService.getNewspage(pageIndex,perPage,Constants.Mainpage.NEWS_STATUS,colid);
		return page;
	}
	
}
