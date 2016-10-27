package com.tzdr.cms.controller.crawler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.service.crawler.CrawlerUrlParamService;
import com.tzdr.business.service.crawler.CrawlerUrlService;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.cms.timer.wallstreetcn.WallstreetcnTask;
import com.tzdr.cms.timer.wallstreetcn.Wallstreetn;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.EasyuiUtil;
import com.tzdr.common.web.support.EasyUiPageData;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.web.entity.CrawlerUrl;
import com.tzdr.domain.web.entity.CrawlerUrlParam;

@Controller
@RequestMapping(value = "/admin/crawler/url")
public class CrawlerUrlController extends BaseCmsController<CrawlerUrl>{
	@Autowired
	private CrawlerUrlService crawlerService;
	@Autowired
	private CrawlerUrlParamService crawlerUrlParamService;
	@RequestMapping(value = "/list",method=RequestMethod.GET)
	public String list(){
		return ViewConstants.CrawlerView.LIST_VIEW;
	}
	@RequestMapping(value = "/listData",method = RequestMethod.POST)
	@ResponseBody
	private Object listData(EasyUiPageInfo easyUiPage, Model model,HttpServletResponse response,HttpServletRequest request){
		//判断是否具有查看权限
		if (permissionList != null) {
			this.permissionList.assertHasViewPermission();
		}
		//获取模糊搜索参数
		Map<String, Object> searchParams = EasyuiUtil.getParametersStartingWith(request,EasyuiUtil.SEARCH_PREFIX);
		PageInfo<Object> pageInfo = crawlerService.doGetCrawlerUrlList(easyUiPage,searchParams);
		return new EasyUiPageData<Object>(pageInfo);
	}
	/**
	 * 新增数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/save",method = RequestMethod.POST)
	@ResponseBody
	public JsonResult doSave(HttpServletRequest request){
		String id = request.getParameter("id");
		String urlTitle = request.getParameter("urlTitle");
		String urlUrl = request.getParameter("urlUrl");
		String urlMethod = request.getParameter("urlMethod");
		String execRule = request.getParameter("execRule");
		String key =  request.getParameter("key");
		String value = request.getParameter("value");
		String urlRemarks = request.getParameter("urlRemarks");
		Long time = new Date().getTime()/1000;
		CrawlerUrl crawlerUrl = new CrawlerUrl();
		crawlerUrl.setExecRule(execRule);
		crawlerUrl.setStatus("0");
		crawlerUrl.setUrlMethod(urlMethod);
		crawlerUrl.setUrlRemarks(urlRemarks);
		crawlerUrl.setUrlTitle(urlTitle);
		crawlerUrl.setUrlCreatetime(time);
		crawlerUrl.setUrlUpdatetime(time);
		crawlerUrl.setLastOpentime(time);
		crawlerUrl.setUrlUrl(urlUrl);
		crawlerUrl.setId(id);
		crawlerUrl.setDeleted(false);
		String[] keysArray = key.split(",");
		String[] valuesArray = value.split(",");
		int length = keysArray.length;
		List<CrawlerUrlParam> crawlerUrlParams = new ArrayList<>();
		for(int i = 0 ; i < length ; i ++ ){
			CrawlerUrlParam crawlerUrlParam = new CrawlerUrlParam();
			crawlerUrlParam.setUrlParamKey(keysArray[i]);
			crawlerUrlParam.setUrlParamValue(valuesArray[i]);
			crawlerUrlParam.setDeleted(false);
			crawlerUrlParam.setUrlParamCreatetime(time);
			crawlerUrlParam.setUrlParamUpdatetime(time);
			crawlerUrlParams.add(crawlerUrlParam);
		}
		crawlerService.doSave(crawlerUrl, crawlerUrlParams);
		JsonResult resultJson = new JsonResult(true);
		return resultJson;
	}
	/**
	 * 根据id获取当个数据
	 * @return
	 */
	@RequestMapping(value = "/doGetDataById",method = RequestMethod.GET)
	@ResponseBody
	public JsonResult doGetDataById(HttpServletRequest request,@RequestParam("id") String id){
		CrawlerUrl crawlerUrl = crawlerService.doGetDataById(id);
		List<CrawlerUrlParam> crawlerUrlParams = crawlerUrlParamService.doGetawlerUrlParamByUrlId(id);
		JsonResult resultJson = new JsonResult(true);
		resultJson.appendData("data", crawlerUrl);
		resultJson.appendData("param", crawlerUrlParams);
		return resultJson;
	}
	/**
	 * 删除数据
	 * @param request
	 * @param urlId
	 * @return
	 */
	@RequestMapping(value = "/doDeleteByUrlId",method = RequestMethod.POST)
	@ResponseBody
	public JsonResult doDeleteUrlAndParam(HttpServletRequest request,@RequestParam("urlId")String urlId){
			crawlerService.doDeleteByUrlId(urlId);
		return new JsonResult(true);
	}
	@RequestMapping(value = "/startCrawler",method = RequestMethod.GET)
	@ResponseBody
	public JsonResult startCrawler(HttpServletRequest request,@RequestParam("id") String id){
		JsonResult resultJson = new JsonResult(true);
		CrawlerUrl crawlerUrl = crawlerService.get(id);
		if(crawlerUrl == null){
			resultJson.setSuccess(false);
			resultJson.setMessage("url不存在");
		}else{
			List<CrawlerUrlParam> crawlerUrlParams = crawlerUrlParamService.doGetawlerUrlParamByUrlId(id);
			StringBuffer buffer = new StringBuffer();
			int size = crawlerUrlParams.size();
			for (int i = 0; i < size; i++) {
				buffer.append(crawlerUrlParams.get(i).getUrlParamValue());
				if(i != size-1){
					buffer.append("&");
				}
			}
			Wallstreetn wallstreetn = new Wallstreetn();
			wallstreetn.setMethod(crawlerUrl.getUrlMethod());
			wallstreetn.setParam(buffer.toString());
			wallstreetn.setRule(crawlerUrl.getExecRule());
			wallstreetn.setUrl(crawlerUrl.getUrlUrl());
			WallstreetcnTask task = new WallstreetcnTask(wallstreetn);
			task.start();
		}
		
		return resultJson;
	}
	@Override
	public BaseService<CrawlerUrl> getBaseService() {
		// TODO Auto-generated method stub
		return null;
	}

}
