package com.tzdr.web.controller.userInfo;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.service.area.AreaService;
import com.tzdr.business.service.securityInfo.impl.SecurityInfoServiceImpl;
import com.tzdr.business.service.userInfo.UserInfoService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.utils.StringCodeUtils;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cache.DataDicKeyConstants;
import com.tzdr.domain.entity.DataMap;
import com.tzdr.domain.web.entity.Area;
import com.tzdr.domain.web.entity.UserInfo;
import com.tzdr.domain.web.entity.UserVerified;
import com.tzdr.web.constants.Constants;
import com.tzdr.web.constants.ViewConstants;
import com.tzdr.web.utils.UserSessionBean;

/**
* @Description: TODO(个人业务信息管理控制层)
* @ClassName: UserInfoController
* @author wangpinqun
* @date 2014年12月30日 上午11:56:46
 */
@Controller
@RequestMapping("/userinfo")
public class UserInfoController{

	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(UserInfoController.class);
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private AreaService areaService;
	
	@Autowired
	private SecurityInfoServiceImpl securityInfoService;
	
	@Autowired
	private WUserService wUserService;
	
	
	/**
	* @Description: TODO(访问用户个人信息页面)
	* @Title: info
	* @param modelMap
	* @param request
	* @param response
	* @return String    返回类型
	 */
	@RequestMapping("/info")
	public String info(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constants.TZDR_USER_SESSION);  //获取用户账户信息
		UserVerified userVerified = securityInfoService.findByUserId(userSessionBean.getId());
		String idcard = null;
		if(userVerified != null && !StringUtil.isBlank(userVerified.getIdcard())){  //身份证号加*
			idcard = userVerified.getIdcard();
			idcard = StringCodeUtils.buildIdCard(idcard);
		}
		UserInfo userInfo = userInfoService.getUserInfoByUId(userSessionBean.getId());  //获取用户基本信息
		if(userInfo != null){  //替换用户个人选择框信息
			String province = StringUtil.isBlank(userInfo.getProvince())? null : userInfo.getProvince(); //获取用户所属省份信息
			String city = StringUtil.isBlank(userInfo.getCity())? null : userInfo.getCity();  //获取用户所属省份信息
			List<String>  areaIdList = new ArrayList<String>();   //存储用户区域编号信息
			if(!StringUtil.isBlank(province)){   //判断用户是否选择所属省份
				areaIdList.add(province);
			}
			if(!StringUtil.isBlank(city)){   //判断用户是否选择所属城市
				areaIdList.add(city);
			}
			StringBuffer  address = new StringBuffer();  //详情地址(省份-城市-地址)
			List<Area> areaList = areaIdList == null || areaIdList.size() <= 0 ? null : areaService.findAreaByIds(areaIdList);  //获取用户区域详情信息
			if(areaList != null && areaList.size() > 0){
				for (Area area : areaList) {
					address.append(area.getTitle() + "-");
				}
			}
			if(!StringUtil.isBlank(userInfo.getAddress())){   //判断是否地址为空
				address.append(address.length() > 0 ? userInfo.getAddress() : "-" + userInfo.getAddress());
			}
			userInfo.setAddress(address.toString());
			if(!StringUtil.isBlank(userInfo.getMarriage())){ //判断婚姻状况
				userInfo.setMarriage(CacheManager.getDataMapByKey(DataDicKeyConstants.MARRIAGE, userInfo.getMarriage()));
			}
			if(!StringUtil.isBlank(userInfo.getEducation())){  //判断最高学历
				userInfo.setEducation(CacheManager.getDataMapByKey(DataDicKeyConstants.EDUCATION, userInfo.getEducation()));
			}
			if(!StringUtil.isBlank(userInfo.getIndustry())){ //判断所属行业
				userInfo.setIndustry(CacheManager.getDataMapByKey(DataDicKeyConstants.INDUSTRY, userInfo.getIndustry()));
			}
			if(!StringUtil.isBlank(userInfo.getPosition())){  //判断职位
				userInfo.setPosition(CacheManager.getDataMapByKey(DataDicKeyConstants.POSITION, userInfo.getPosition()));
			}
			if(!StringUtil.isBlank(userInfo.getIncome())){  //判断月收入
				userInfo.setIncome(CacheManager.getDataMapByKey(DataDicKeyConstants.INCOME, userInfo.getIncome()));
			}
		}
		String email = StringUtil.isBlank(userSessionBean.getEmail()) ? null : userSessionBean.getEmail();
		email = StringUtil.isBlank(email) ? null : StringCodeUtils.buildEmail(email);
		modelMap.put("idcard", idcard);  //返回用户帐号信息
		modelMap.put("email", email);  //返回用户帐号信息
		modelMap.put("userName", request.getSession().getAttribute(Constants.TZDR_USERNAME_SESSION));  //返回用户帐号信息
		modelMap.put("userInfo", userInfo);   //返回用户基本信息
		modelMap.put("userVerified", userVerified);   //返回安全验证信息
		return ViewConstants.UserInfoViewJsp.USERINFO_INDEX_VIEW;
	}
	
	/**
	* @Description: TODO(访问修改个人信息页面)
	* @Title: upInfo
	* @param modelMap
	* @param request
	* @param response
	* @return String    返回类型
	 */
	@RequestMapping(value ="/upinfo")
	public String upInfo(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constants.TZDR_USER_SESSION);  //获取用户账户信息
		UserInfo userInfo = userInfoService.getUserInfoByUId(userSessionBean.getId());  //获取用户基本信息
		String city = userInfo == null || StringUtil.isBlank(userInfo.getProvince())? null : userInfo.getProvince();  //获取用户所属省份信息
		List<Area> provinceList = areaService.findByPidOrderBySortAsc("0");  //区域一级列表信息
		List<Area> cityList = StringUtil.isBlank(city) ? null : areaService.findByPidOrderBySortAsc(city);  //获取用户所属省份的区域二级列表信息
		List<DataMap> marriageList = CacheManager.getDataMapListByTypeKey(DataDicKeyConstants.MARRIAGE); //获取婚姻状况列表信息
		List<DataMap> educationList = CacheManager.getDataMapListByTypeKey(DataDicKeyConstants.EDUCATION);  //获取最高学历列表信息
		List<DataMap> industryList = CacheManager.getDataMapListByTypeKey(DataDicKeyConstants.INDUSTRY);  //获取所属行业列表信息
		List<DataMap> positionList = CacheManager.getDataMapListByTypeKey(DataDicKeyConstants.POSITION);  //获取职位列表信息
		List<DataMap> incomeList = CacheManager.getDataMapListByTypeKey(DataDicKeyConstants.INCOME);  //获取月收入列表信息
		modelMap.put("mobile", request.getSession().getAttribute(Constants.TZDR_USERNAME_SESSION));   //返回页面用户手机号码信息
		modelMap.put("userInfo", userInfo);   //返回页面个人信息
		modelMap.put("provinceList", provinceList);   //返回页面区域一级列表信息
		modelMap.put("cityList", cityList);           //返回页面区域二级列表信息
		modelMap.put("marriageList", marriageList);   //返回页面婚姻状况列表信息
		modelMap.put("educationList", educationList); //返回页面最高学历列表信息
		modelMap.put("industryList", industryList);   //返回页面所属行业列表信息
		modelMap.put("positionList", positionList);   //返回页面职位列表信息
		modelMap.put("incomeList", incomeList);       //返回页面月收入列表信息
		return ViewConstants.UserInfoViewJsp.USERINFO_UPDATE_VIEW;
	}
	
	/**
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @Title: updateInfo
	* @param userInfo
	* @param modelMap
	* @param request
	* @param response
	* @return 
	* @return JsonResult    返回类型
	 */
	@RequestMapping(value = "/update_info")
	@ResponseBody
	public JsonResult updateInfo(UserInfo userInfo,ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constants.TZDR_USER_SESSION);  //获取用户账户信息
		JsonResult  jsonResult = new JsonResult(true);
		UserInfo oldUserInfo = userInfoService.getUserInfoByUId(userSessionBean.getId());
		if(StringUtil.isBlank(userInfo.getId())){   //保存用户基本信息
			userInfo.setWuser(wUserService.getUser(userSessionBean.getId()));
			userInfoService.saveUserInfo(userInfo);
		}else{
			oldUserInfo.setMarriage(userInfo.getMarriage());
			oldUserInfo.setProvince(userInfo.getProvince());
			oldUserInfo.setAddress(userInfo.getAddress());
			oldUserInfo.setCity(userInfo.getCity());
			oldUserInfo.setEducation(userInfo.getEducation());
			oldUserInfo.setIndustry(userInfo.getIndustry());
			oldUserInfo.setPosition(userInfo.getPosition());
			oldUserInfo.setIncome(userInfo.getIncome());
			userInfoService.updateUserInfo(oldUserInfo); //更新用户基本信息
		}
		return jsonResult;
	}
}
