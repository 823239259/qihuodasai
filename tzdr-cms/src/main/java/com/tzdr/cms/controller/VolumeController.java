package com.tzdr.cms.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.service.volume.VolumeDeductibleService;
import com.tzdr.business.service.volume.VolumeDetailService;
import com.tzdr.business.service.wuser.UserVerifiedService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.cms.utils.DataGridVo;
import com.tzdr.cms.utils.WebUtil;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.exception.BusinessException;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.utils.MessageUtils;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.vo.VolumeDeductibleVo;
import com.tzdr.domain.vo.VolumeDetailVoNew;
import com.tzdr.domain.web.entity.UserVerified;
import com.tzdr.domain.web.entity.VolumeDeductible;
import com.tzdr.domain.web.entity.VolumeDetail;
import com.tzdr.domain.web.entity.WUser;

/**
 * 
 * <p></p>
 * @author LiuQing
 * @see
 * @version 2.0
 * 2015年4月28日上午9:09:29
 */
@Controller
@RequestMapping("/admin/volume")
public class VolumeController extends BaseCmsController<VolumeDeductible> {
	
	private static Logger log = LoggerFactory.getLogger(VolumeController.class);
	
	@Autowired
	private VolumeDeductibleService volumeDeductibleService;
	
	@Autowired
	private VolumeDetailService volumeDetailService;
	@Autowired
	private UserVerifiedService userVerifiedService;
	@Autowired
	private WUserService wuserService;
	
	public VolumeController() {
		setResourceIdentity("sys:operationalConfig:volume");
	}

	@Override
	public BaseService<VolumeDeductible> getBaseService() {
		return null;
	}
	
	@RequestMapping(value = "/list")
	public String listEnd() {
		return ViewConstants.VolumeViewJsp.LIST_VIEW;
	}
	
	@RequestMapping(value = "/detailList")
	public String detailList() {
		return ViewConstants.VolumeViewJsp.DETAIL_VIEW;
	}
	
	@RequestMapping(value = "/detailData")
	public void detailData(HttpServletRequest request,HttpServletResponse resp) throws Exception {
		try {
			
			DataGridVo<VolumeDetailVoNew> grid = new DataGridVo<VolumeDetailVoNew>();
			PageInfo<VolumeDetailVoNew> pageInfo = new PageInfo<VolumeDetailVoNew>(request);
			ConnditionVo connVo = new ConnditionVo(request);
			if (connVo.isExcel()) {
				pageInfo.setCurrentPage(1);
				pageInfo.setCountOfCurrentPage(TypeConvert.EXCEL_PAGE_SIZE);
			}
			pageInfo = volumeDetailService.queryDataListVo(pageInfo, connVo);
			if (connVo.isNotExcel(pageInfo.getPageResults(), resp,"抵扣券使用详细.xls")) {
				grid.add(pageInfo.getPageResults());
				grid.setTotal(pageInfo.getTotalCount());
				WebUtil.printText(JSON.toJSONString(grid), resp);
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/listData")
	public void listData(HttpServletRequest request,HttpServletResponse resp) throws Exception {
		try {
			
			DataGridVo<VolumeDeductibleVo> grid = new DataGridVo<VolumeDeductibleVo>();
			PageInfo<VolumeDeductibleVo> pageInfo = new PageInfo<VolumeDeductibleVo>(request);
			ConnditionVo connVo = new ConnditionVo(request);
			pageInfo = volumeDeductibleService.queryDataListVo(pageInfo, connVo);
			grid.add(pageInfo.getPageResults());
			grid.setTotal(pageInfo.getTotalCount());
			WebUtil.printText(JSON.toJSONString(grid), resp);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/saveVolume")
	public void saveVolume(VolumeDeductibleVo volumeVo,HttpServletRequest request,HttpServletResponse resp) {
		synchronized (lock) {
			try {
				VolumeDeductible entity = VolumeDeductibleVo.getEntity(volumeVo);
				User user = authService.getCurrentUser();
				entity.setUpdateTime(TypeConvert.dbDefaultDate());
				entity.setCreateTime(TypeConvert.dbDefaultDate());
				if (user != null) {
					entity.setUpdateUserId(user.getId());
					entity.setCreateUserId(user.getId());
				}
				entity.setStateValue(1);
				String typeCode = this.volumeDeductibleService.queryMaxTypeCode();
				entity.setTypeCode(typeCode);
				if (entity.getReleaseNum() <= 0) {
					WebUtil.printText("发行数量不能小于等于0!", resp);
				}
				else {
					if (entity.getReleaseNum() > TypeConvert.VOLUME_MAX ) {
						entity.setReleaseNum(TypeConvert.VOLUME_MAX);
					}
					int code = TypeConvert.VOLUME_INI;
					for (int i = 0; i < entity.getReleaseNum(); i++) {
						VolumeDetail detail = new VolumeDetail();
						detail.setCode(code + "");
						detail.setUseState(VolumeDetail.USE_STATE_USE);
						entity.add(detail);
						code++;
					}
					this.volumeDeductibleService.save(entity);
					WebUtil.printText("success",resp);
				}
			} 
			catch (BusinessException e) {
				e.printStackTrace();
				WebUtil.printText("服务器忙!",resp);
			}
		}
	}
	
	
	@RequestMapping(value = "/updateVolume")
	public void updateVolume(VolumeDeductibleVo volumeVo,HttpServletRequest request,HttpServletResponse resp) {
		synchronized (lock) {
			try {
				if (volumeVo == null) {
					WebUtil.printText("数据不完整!", resp);
					return;
				}
				else if (volumeVo.getReleaseNum() == 0) {
					WebUtil.printText("发布张数不能为0!", resp);
					return;
				}
				VolumeDeductible entity = this.volumeDeductibleService.get(volumeVo.getId());
				int releaseNum = entity.getReleaseNum();
				if (volumeVo.getReleaseNum() > TypeConvert.VOLUME_MAX ) {
					volumeVo.setReleaseNum(TypeConvert.VOLUME_MAX);
				}
				int newNum = volumeVo.getReleaseNum();
				entity.setReleaseNum(newNum);
				
				if (newNum < releaseNum) {
					WebUtil.printText("张数不能小于已发放的张数!", resp);
					return;
				}
				if (volumeVo.getReleaseNum() <= 0) {
					WebUtil.printText("发行数量不能小于等于0!", resp);
					return;
				}
				if (newNum > releaseNum) {
					int newReleaseNum = newNum - releaseNum;
					int code = this.volumeDetailService.queryDetailMaxCodeByVolumeId(entity.getId());
					List<VolumeDetail> details = new ArrayList<VolumeDetail>();
					for (int i = 0; i < newReleaseNum; i++) {
						VolumeDetail detail = new VolumeDetail();
						detail.setCode(code + "");
						detail.setUseState(VolumeDetail.USE_STATE_USE);
						detail.setVolumeDeductible(entity);
						details.add(detail);
						code++;
					}
					this.volumeDetailService.saveCollection(details,entity);
					WebUtil.printText("success",resp);
				}
				entity.setUpdateTime(TypeConvert.dbDefaultDate());
				User user = authService.getCurrentUser();
				if (user != null) {
					entity.setUpdateUserId(user.getId());
					entity.setCreateUserId(user.getId());
				}
				this.volumeDeductibleService.update(entity);
				WebUtil.printText("success",resp);
				
			} 
			catch (BusinessException e) {
				e.printStackTrace();
				WebUtil.printText("服务器忙!",resp);
			}
		}
	}
	
	@RequestMapping(value = "/deleteVolume")
	public void deleteVolume(String id,HttpServletResponse resp) {
		try {
			VolumeDeductible volume = this.volumeDeductibleService.get(id);
			Long startupDatetime = volume.getDealStartDateValue();
			if (startupDatetime != null) {
				long sysLong = TypeConvert.dbDefaultDate();
				if (startupDatetime.longValue() > sysLong) {
					this.volumeDeductibleService.remove(volume);
					WebUtil.printText("success",resp);
				}
				else {
					WebUtil.printText("该卷已发行不能被删除!",resp);
				}
			}
			else {
				this.volumeDeductibleService.remove(volume);
				WebUtil.printText("success",resp);
			}
		} 
		catch (BusinessException e) {
			e.printStackTrace();
			WebUtil.printText("服务器忙!",resp);
		}
	}
	
	@RequestMapping(value = "/stopVolume")
	public void stopVolume(String id,HttpServletResponse resp) {
		try {
			VolumeDeductible volume = this.volumeDeductibleService.get(id);
			volume.setStateValue(VolumeDeductible.STATE_VALUE_STOP);
			this.volumeDeductibleService.update(volume);
			WebUtil.printText("success",resp);
		} 
		catch (BusinessException e) {
			e.printStackTrace();
			WebUtil.printText("服务器忙!",resp);
		}
	}
	
	@Autowired
	private AuthService authService;
	
	private static Object lock = new Object();
	/**
	 * 获取用户信息
	 * @param request
	 * @param mobile  手机号码
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getWUser")
	@ResponseBody
	public JsonResult getWUser(HttpServletRequest request,String mobile) throws Exception {
		JsonResult result=new JsonResult(true);
		if(StringUtil.isBlank(mobile)){
			result.setSuccess(false);
			result.setMessage(MessageUtils.message("no.mobile"));
			return result;
		}			
		
		WUser wuser = wuserService.findByMobileOrEmail(mobile);
		
		if(wuser == null || !mobile.equals(wuser.getMobile())){
			result.setSuccess(false);
			result.setMessage(MessageUtils.message("no.user"));
			return result;
		}
		
		UserVerified userVerified = userVerifiedService.queryUserVerifiedByUid(wuser.getId());
		
		Map<Object,Object> data = new HashMap<Object, Object>();
		
		data.put("trueName", userVerified == null ? null : userVerified.getTname());
		data.put("uid",wuser.getId());
		
		result.setData(data);
		
		return result;
	}
	
	@RequestMapping(value = "/grantVolume")
	public void grantVolume(String uid,String volumeid,HttpServletRequest request,HttpServletResponse resp) {
		synchronized (lock) {
			try {
				if (uid == null) {
					WebUtil.printText("数据不完整!", resp);
					return;
				}else{
					VolumeDetail entity = volumeDetailService.findByVolumeid(volumeid);
					String volume_uid = entity.getUid();
					if(StringUtil.isNotBlank(volume_uid)){	
						WebUtil.printText("该抵扣券刚被发放，请重新选择",resp);
					}else{
						entity.setUid(uid);
						entity.setTimeValueOfGetVolume(TypeConvert.dbDefaultDate());
						this.volumeDetailService.update(entity);
						WebUtil.printText("success",resp);
					}
				}
			} 
			catch (BusinessException e) {
				e.printStackTrace();
				WebUtil.printText("服务器忙!",resp);
			}
		}
	}
}
