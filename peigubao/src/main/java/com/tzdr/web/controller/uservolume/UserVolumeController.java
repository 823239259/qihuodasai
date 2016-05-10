package com.tzdr.web.controller.uservolume;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.service.volume.impl.VolumeDetailServiceImpl;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cache.DataDicKeyConstants;
import com.tzdr.domain.vo.UserVolumeDetailVo;
import com.tzdr.web.constants.Constants;
import com.tzdr.web.constants.ViewConstants;
import com.tzdr.web.utils.UserSessionBean;


@Controller
@RequestMapping("/uservolume")
public class UserVolumeController {

	private static Logger log = LoggerFactory.getLogger(UserVolumeController.class);
	
	@Autowired
	private VolumeDetailServiceImpl volumeDetailServiceImpl;
	
	/**
	* @Description: 访问用户劵管理页面
	* @Title: volumelist
	* @param modelMap
	* @param request
	* @param response
	* @return String    返回类型
	 */
	@RequestMapping(value = "/volumelist")
	public String volumelist(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constants.TZDR_USER_SESSION);  //获取用户账户信息
		List<UserVolumeDetailVo> userVolumeDetailVoList = volumeDetailServiceImpl.findVolumeByUId(userSessionBean.getId(), null);
		if(userVolumeDetailVoList != null && !userVolumeDetailVoList.isEmpty()){
			for (UserVolumeDetailVo userVolumeDetailVo : userVolumeDetailVoList) {
				userVolumeDetailVo.setUseTypeName(CacheManager.getDataMapByKey(DataDicKeyConstants.VOLUMETYPE_KEY, userVolumeDetailVo.getUseType()));
			}
		}
		modelMap.put("userVolumeDetailVoList", userVolumeDetailVoList);
		return ViewConstants.UserVolumeJsp.USERVOLUME;
	}
	
	/**
	* @Description: 获取用户劵
	* @Title: getvolumelist
	* @param volumeType 获取类型
	* @param modelMap
	* @param request
	* @param response
	* @return JsonResult    返回类型
	 */
	@RequestMapping(value = "/getvolumelist")
	@ResponseBody
	public JsonResult getvolumelist(Integer volumeType,ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constants.TZDR_USER_SESSION);  //获取用户账户信息
		JsonResult  jsonResult = new JsonResult(true);
		List<UserVolumeDetailVo> userVolumeDetailVoList = volumeDetailServiceImpl.findVolumeByUId(userSessionBean.getId(), volumeType);
		if(userVolumeDetailVoList != null && !userVolumeDetailVoList.isEmpty()){
			for (UserVolumeDetailVo userVolumeDetailVo : userVolumeDetailVoList) {
				userVolumeDetailVo.setUseTypeName(CacheManager.getDataMapByKey(DataDicKeyConstants.VOLUMETYPE_KEY, userVolumeDetailVo.getUseType()));
			}
		}
		jsonResult.setObj(userVolumeDetailVoList);
		return jsonResult;
	}
}
