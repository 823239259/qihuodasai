package com.tzdr.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import jodd.util.ObjectUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.tzdr.business.service.notice.NoticeService;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.web.entity.Notice;
import com.tzdr.web.constants.Constants;
import com.tzdr.web.utils.WebUtil;

/**
 * 公告控制层
 * @ClassName NoticeController
 * @author L.Y
 * @email liuyuan@peigubao.com
 * @date 2015年4月30日
 */
@Controller
@RequestMapping("/notice")
public class NoticeController {
	@Autowired
	private NoticeService noticeService;
	
	/**
	 * 查询公告, 使用JSONP
	 * @MethodName item
	 * @author L.Y
	 * @date 2015年4月30日
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/item")
	public void item(HttpServletResponse response) throws Exception {
		StringBuffer buffer=new StringBuffer();
		JsonResult jsonResult = new JsonResult(false);
		List<Notice> notices = noticeService.findNotices(Constants.Notice.UNLOCK); //启用状态
		Notice notice = null;
		if(!CollectionUtils.isEmpty(notices) && !ObjectUtil.equals(null, notice = notices.get(0))) {
			jsonResult.setSuccess(true);
			jsonResult.setObj(notice.getContent());
		}
		buffer.append("callback(");
		buffer.append(JSONObject.toJSONString(jsonResult));
		buffer.append(");");
		WebUtil.printText(buffer.toString(), response);
	}
}