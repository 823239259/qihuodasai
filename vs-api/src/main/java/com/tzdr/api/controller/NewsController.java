package com.tzdr.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.api.constants.ResultStatusConstant;
import com.tzdr.api.support.ApiResult;
import com.tzdr.business.app.service.NewsService;
import com.tzdr.common.utils.MessageUtils;
import com.tzdr.domain.app.vo.NewsVo;
import com.tzdr.domain.web.entity.OperationalConfig;

/**
 * 系统新闻动态
 * @author zhouchen
 * 2016年3月24日 下午8:13:04
 */
@Controller
@RequestMapping("/news")
public class NewsController {
	
	public static final Logger logger = LoggerFactory.getLogger(NewsController.class);

	@Autowired
	private NewsService newsService;
	
	/**
	 * 获取新闻
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list" , method = RequestMethod.POST)
	@ResponseBody
	public ApiResult list(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws Exception{
		List<NewsVo>  newsVos = newsService.findNews(MessageUtils.message("tzdr.app.news.column.name"));
		return new ApiResult(true, ResultStatusConstant.SUCCESS,"query.success", newsVos);
	}
	
	/**
	 * 获取新闻详情
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/detail" , method = RequestMethod.POST)
	@ResponseBody
	public ApiResult detail(String nid,ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws Exception{
		if (StringUtil.isBlank(nid)){
			return new ApiResult(false,ResultStatusConstant.FAIL,"news.id.is.empty.");
		}
		OperationalConfig  news = newsService.get(nid);
		return new ApiResult(true, ResultStatusConstant.SUCCESS,"query.success", new NewsVo(news));
	}
	
}
