package com.tzdr.cms.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.tzdr.business.service.notice.NoticeService;
import com.tzdr.cms.constants.Constants;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.cms.utils.DataGridVo;
import com.tzdr.cms.utils.WebUtil;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.web.BaseController;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.vo.NoticeVo;
import com.tzdr.domain.web.entity.Notice;


/**
 * 系统公告信息管理
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author liuhaichuan
 * @date 2015年4月28日
 *
 */
@Controller
@RequestMapping("/admin/notice")
public class NoticeController extends BaseCmsController<Notice>{

	@Autowired
	private NoticeService noticeService;
	
	
	@Override
	public BaseService<Notice> getBaseService() {
		// TODO Auto-generated method stub
		return noticeService;
	}
	
	@Override
	public void setResourceIdentity(String resourceIdentity) {
		// TODO Auto-generated method stub
		super.setResourceIdentity("sys:operationalConfig:notice");
	}
	
	/**
	 * 跳转到系统公告列表页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String getNoticeList(HttpServletRequest request, HttpServletResponse response){
		return ViewConstants.NoticeViewJsp.LIST_VIEW;
	}
	
	/**
	 * 分页查询系统公告记录
	 * @param notice
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/listData")
	public void getListData(NoticeVo notice, HttpServletRequest request, HttpServletResponse response){
		DataGridVo<NoticeVo> grid = new DataGridVo<NoticeVo>();
		PageInfo<NoticeVo> data = noticeService.findByPage(
				new PageInfo<NoticeVo>(request), notice);
		List<NoticeVo> pageResults = data.getPageResults();
		for (NoticeVo noticeVo : pageResults) {
			if(noticeVo.getDiff()==1){
				grid.add(noticeVo);
			}
		}
		grid.setTotal(data.getTotalCount());
		WebUtil.printText(JSON.toJSONString(grid), response);
	}
	
	/**
	 * 配股宝分页查询系统公告记录
	 * @param notice
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/pgblistData")
	public void getPgbListData(NoticeVo notice, HttpServletRequest request, HttpServletResponse response){
		DataGridVo<NoticeVo> grid = new DataGridVo<NoticeVo>();
		PageInfo<NoticeVo> data = noticeService.findByPage(
				new PageInfo<NoticeVo>(request), notice);
		List<NoticeVo> pageResults = data.getPageResults();
		for (NoticeVo noticeVo : pageResults) {
			if(noticeVo.getDiff()==2){
				grid.add(noticeVo);
			}
		}
		grid.setTotal(data.getTotalCount());
		WebUtil.printText(JSON.toJSONString(grid), response);
	}
	
	
	
	/**
	 * 跳转到编辑页面
	 * 
	 * @param id
	 * @param request
	 * @return 
	 * @return
	 */
	@RequestMapping(value = "/edit")
	public String getNoticeEdit(@RequestParam("fromType") String fromType,
			@RequestParam(value = "id", required = false) String id,HttpServletRequest request, HttpServletResponse response){
		if (StringUtil.equals(fromType, Constants.EDIT)) {
			Notice notice=noticeService.get(id);
			request.setAttribute("notice", notice);
			return ViewConstants.NoticeViewJsp.EDIT_VIEW;
		}
		return ViewConstants.ERROR_VIEW;
	}
	
	/**
	 * 开启公告
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/open")
	@ResponseBody
	public JsonResult open(String id, HttpServletRequest request, HttpServletResponse response){
		return updateStatus(id, 2, "启用");
	}
	
	/**
	 * 停用公告
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/stop")
	@ResponseBody
	public JsonResult stop(String id, HttpServletRequest request, HttpServletResponse response){
		return updateStatus(id, 1, "停用");
	}
	
	@RequestMapping(value="/updateContent")
	@ResponseBody
	public JsonResult updateNoticeContent(String id, String content, HttpServletRequest request, HttpServletResponse response){
		JsonResult json = new JsonResult();
		Notice notice=noticeService.get(id);
		if(notice!=null){
			notice.setContent(content);
			noticeService.updateNotice(notice);
			json.setSuccess(true);
			json.setMessage("修改成功");
		}else{
			json.setSuccess(false);
			json.setMessage("修改失败");
		}
		return json;
	}
	
	/**
	 * 修改公告的状态
	 * @param id
	 * @param status
	 * @param messageHead
	 * @return
	 */
	private JsonResult updateStatus(String id, int status, String messageHead){
		JsonResult json = new JsonResult();
		Notice notice=noticeService.get(id);
		if(notice!=null){
			if(notice.getStatus()!=status){
				notice.setStatus(status);
				noticeService.updateNotice(notice);
			}
			json.setSuccess(true);
			json.setMessage(messageHead+"成功");
		}else{
			json.setSuccess(false);
			json.setMessage(messageHead+"失败");
		}
		return json;
	}
	
}
