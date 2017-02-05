package com.tzdr.cms.timer.wallstreetcn;

import java.util.List;

import com.tzdr.domain.web.entity.CrawlerUrl;
import com.tzdr.domain.web.entity.CrawlerUrlParam;

public class WallstreetcnEntry {
	/**
	 * 任务执行
	 * @param crawlerUrl
	 * @param crawlerUrlParams
	 * @return
	 */
	public boolean exected(CrawlerUrl crawlerUrl,List<CrawlerUrlParam> crawlerUrlParams){
		Wallstreetn wallstreetn = new Wallstreetn();
		wallstreetn.setId(crawlerUrl.getId());
		wallstreetn.setMethod(crawlerUrl.getUrlMethod());
		wallstreetn.setParam(getParam(crawlerUrlParams));
		wallstreetn.setRule(crawlerUrl.getExecRule());
		wallstreetn.setUrl(crawlerUrl.getUrlUrl());
		wallstreetn.setType(crawlerUrl.getType());
		crawlerUrl.setStatus("1");//设置该url执行状态
		BaseWallstreetnTask task = BaseGetWallstreetcn.get(wallstreetn.getType());
		if(task == null){
			throw new RuntimeException("执行类型错误");
		}
		task.setWallstreetn(wallstreetn);
		BaseWallstreetcnHandle handle = task.getBaseWallstreetcnHandle();
		handle.setCrawlerUrl(crawlerUrl);
		handle.setCrawlerUrlParams(crawlerUrlParams);
		task.setBaseWallstreetcnHandle(handle);
		boolean result = WallstreetcnTimer.start(task);
		return result;
	}
	public String getParam(List<CrawlerUrlParam> crawlerUrlParams){
		StringBuffer buffer = new StringBuffer();
		int size = crawlerUrlParams.size();
		for (int i = 0; i < size; i++) {
			CrawlerUrlParam crawlerUrlParam = crawlerUrlParams.get(i);
			buffer.append(crawlerUrlParam.getUrlParamKey()+"="+crawlerUrlParam.getUrlParamValue());
			if(i != size-1){
				buffer.append("&");
			}
		}
		return buffer.toString();
	}
}
