package com.tzdr.cms.controller.crawler;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.service.crawler.CrawlerUrlService;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.EasyuiUtil;
import com.tzdr.common.web.support.EasyUiPageData;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.domain.web.entity.CrawlerUrl;

@Controller
@RequestMapping(value = "/admin/crawler/url")
public class CrawlerUrlController extends BaseCmsController<CrawlerUrl>{
	@Autowired
	private CrawlerUrlService crawlerService;
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
	@Override
	public BaseService<CrawlerUrl> getBaseService() {
		// TODO Auto-generated method stub
		return null;
	}

}
