package com.tzdr.web.controller.generalize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

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

import com.tzdr.business.service.company.CompanyCommissionService;
import com.tzdr.business.service.generalize.GeneralizeService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.BigDecimalUtils;
import com.tzdr.common.utils.ConfUtil;
import com.tzdr.common.utils.StringCodeUtils;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.vo.GeneralizeVisitUserVo;
import com.tzdr.domain.web.entity.CompanyCommission;
import com.tzdr.domain.web.entity.WUser;
import com.tzdr.web.constants.Constants;
import com.tzdr.web.constants.ViewConstants;
import com.tzdr.web.utils.UserSessionBean;

/**
* @Description: TODO(推广业务信息管理类)
* @ClassName: GeneralizeController
* @author wangpinqun
* @date 2015年1月12日 下午8:25:06
 */
@Controller
@RequestMapping("/generalize")
public class GeneralizeController{

	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(GeneralizeController.class);
	
	@Autowired
	private GeneralizeService generalizeService;
	
	@Autowired
	private WUserService wUserService;
	
	@Autowired
	private CompanyCommissionService companyCommissionService;
	
	/**
	* @Description: TODO(访问推广详情信息页面)
	* @Title: details
	* @param modelMap
	* @param tab     指定显示tab信息
	* @param request
	* @param response
	* @return String    返回类型
	 */
	@RequestMapping(value = "/details")
	public String details(ModelMap modelMap,String tab,HttpServletRequest request,HttpServletResponse response){
		UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constants.TZDR_USER_SESSION);  //获取用户账户信息
		//获取用户信息
		WUser newWUser = wUserService.getUser(userSessionBean.getId());
		//获取下一级数量 
		int totalSubordinate = generalizeService.getWuserTotalChild(userSessionBean.getId());
		//获取访问数量
		long totalVisit =  generalizeService.getGeneralizeVisitCount(newWUser.getGeneralizeId());
		//访问IP
		long totalVisitClieantIp = generalizeService.getGeneralizeVisitClieantIpCount(newWUser.getGeneralizeId());
		//获取推广佣金排名
		List<WUser> wuserList = null;
		List<GeneralizeVisitUserVo> generalizeVisitUserVoList = new ArrayList<GeneralizeVisitUserVo>();
		
		String isOpen = ConfUtil.getContext("is.open.company.commission");
		if(Boolean.valueOf(isOpen)){
			List<CompanyCommission> companyCommissionList = companyCommissionService.findOrderByTotalCommissionDesc();
			if(companyCommissionList != null){
				for (CompanyCommission companyCommission : companyCommissionList) {
					GeneralizeVisitUserVo generalizeVisitUserVo = new GeneralizeVisitUserVo();
					generalizeVisitUserVo.setMobile(StringCodeUtils.buildMobile(companyCommission.getMobile()));
					generalizeVisitUserVo.setTotalCommission(companyCommission.getTotalCommission());
					generalizeVisitUserVoList.add(generalizeVisitUserVo);
				}
			}
		}else{
			wuserList = wUserService.queryByCommissionDesc();
			if(wuserList != null && !wuserList.isEmpty()){
				for (WUser wuserSort : wuserList) {
					if("-1".equals(wuserSort.getUserType()) || "-2".equals(wuserSort.getUserType()) || "-3".equals(wuserSort.getUserType())) continue;
					GeneralizeVisitUserVo generalizeVisitUserVo = new GeneralizeVisitUserVo();
					generalizeVisitUserVo.setMobile(StringCodeUtils.buildMobile(wuserSort.getMobile()));
					generalizeVisitUserVo.setTotalCommission(wuserSort.getTotalCommission());
					generalizeVisitUserVoList.add(generalizeVisitUserVo);
				}
			}
		}
		modelMap.put("id", newWUser.getGeneralizeId());
		modelMap.put("rebate", newWUser.getRebate());
		modelMap.put("userGrade", newWUser.getUserGrade());
		modelMap.put("subordinateDefaultRebate", newWUser.getSubordinateDefaultRebate());
		modelMap.put("totalCommission", newWUser.getTotalCommission());
		modelMap.put("totalSubordinate", totalSubordinate);
		modelMap.put("totalVisit", totalVisit);
		modelMap.put("totalVisitClieantIp", totalVisitClieantIp);
		modelMap.put("generalizeVisitUserVoList", generalizeVisitUserVoList);
		modelMap.put("tab", tab);
		return ViewConstants.GeneralizeViewJsp.DETAILS_VIEW;
	}
	
	/**
	* @Description: TODO(获取用户推广的访问记录信息)
	* @Title: visitors
	* @param pageInfo
	* @param modelMap
	* @param request
	* @param response
	* @return JsonResult    返回类型
	 */
	@RequestMapping(value = "/visitors")
	@ResponseBody
	public JsonResult visitors(PageInfo<Object> pageInfo,ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		JsonResult  jsonResult = new JsonResult(true);
		UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constants.TZDR_USER_SESSION);  //获取用户账户信息
		//获取用户信息
		WUser newWUser = wUserService.getUser(userSessionBean.getId());
		//获取访问IP总数
		long totalVisitClieantIp = generalizeService.getGeneralizeVisitClieantIpCount(newWUser.getGeneralizeId());
		//获取访问总数
		long totalVisit =  generalizeService.getGeneralizeVisitCount(newWUser.getGeneralizeId());
		//获取访问记录列表信息
		pageInfo = generalizeService.queryPageGeneralizeVisitVo(newWUser.getGeneralizeId(), pageInfo);
		Map<Object, Object> data = new HashMap<Object, Object>();
		data.put("totalVisitClieantIp", totalVisitClieantIp);
		data.put("totalVisit", totalVisit);
		data.put("pageInfo", pageInfo);
		jsonResult.setData(data);
		return jsonResult;
	}
	
	/**
	* @Description: TODO(获取用户的下级列表信息)
	* @Title: subordinates
	* @param pageInfo   分页信息
	* @param modelMap
	* @param request
	* @param response
	* @return JsonResult    返回类型
	 */
	@RequestMapping(value = "/subordinates")
	@ResponseBody
	public JsonResult subordinates(PageInfo<Object> pageInfo,ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		JsonResult  jsonResult = new JsonResult(true);
		UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constants.TZDR_USER_SESSION);  //获取用户账户信息
		pageInfo = generalizeService.queryPageGeneralizeVisitUserVo(userSessionBean.getId(), pageInfo);  //获取用户下级信息
		List<Object> subordinates = pageInfo == null ? null : pageInfo.getPageResults();
		List<Object> generalizeVisitUserVoList = new ArrayList<Object>();
		if(subordinates != null && !subordinates.isEmpty()){
			for (Object subordinate : subordinates) {
				GeneralizeVisitUserVo generalizeVisitUserVo = (GeneralizeVisitUserVo) subordinate;
				generalizeVisitUserVo.setTname(TypeConvert.showPasswordText(generalizeVisitUserVo.getTname()));
				generalizeVisitUserVo.setAllChildNumber(this.generalizeService.queryChildsSize(generalizeVisitUserVo.getId()));
				if(!StringUtil.isBlank(generalizeVisitUserVo.getMobile())){   //用户手机号码加*
					generalizeVisitUserVo.setMobile(StringCodeUtils.buildMobile(generalizeVisitUserVo.getMobile()));
				}
				generalizeVisitUserVoList.add(generalizeVisitUserVo);
			}
			pageInfo.setPageResults(generalizeVisitUserVoList);
		}
		Map<Object, Object> data = new HashMap<Object, Object>();
		data.put("pageInfo", pageInfo);
		jsonResult.setData(data);
		return jsonResult;
	}
	
	/**
	* @Description: TODO(设置用户下级默认返点)
	* @Title: updateDefaultRebate
	* @param rebate  默认返点值
	* @param modelMap
	* @param request
	* @param response
	* @return JsonResult    返回类型
	 */
	@RequestMapping(value="updateDefaultRebate")
	@ResponseBody
	public JsonResult updateDefaultRebate(String rebate,ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		JsonResult  jsonResult = new JsonResult(true);
		Pattern pattern = Pattern.compile("[0-9]*"); 
		if(StringUtil.isBlank(rebate)){
			jsonResult.setMessage("rebateIsNull");
			return jsonResult;
		}else if(!pattern.matcher(rebate).matches() || (rebate.length() > 1 && Integer.valueOf(rebate.substring(0,1)) == 0)){
			jsonResult.setMessage("rebateNotPositiveInt");
			return jsonResult;
		}
		
		UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constants.TZDR_USER_SESSION);  //获取用户账户信息
		WUser newWuser = wUserService.getUser(userSessionBean.getId()); //获取用户新信息
		double newDefaultRebate = BigDecimalUtils.round2(Double.valueOf(rebate), 2);
		double userRebe = BigDecimalUtils.round2(newWuser.getRebate(), 2);
		if(newDefaultRebate > userRebe){  //判断新设置的默认返点是否大于用户的返点
			jsonResult.setMessage("rebateError");
			return jsonResult;
		}
		//更新用户默认返点
		newWuser.setSubordinateDefaultRebate(newDefaultRebate);
		wUserService.updateUser(newWuser);
		return jsonResult;
	}
	
	/**
	* @Description: TODO(修改用户的某个下级返点)
	* @Title: updateSubordinate
	* @param uid    用户编号
	* @param rebate  返点值
	* @param modelMap
	* @param request
	* @param response
	* @return
	* @return JsonResult    返回类型
	 */
	@RequestMapping(value="updateSubordinate")
	@ResponseBody
	public JsonResult updateSubordinate(String uid,String rebate,ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		JsonResult  jsonResult = new JsonResult(true);
		Pattern pattern = Pattern.compile("^\\+?[1-9]\\d*$"); 
		if(StringUtil.isBlank(rebate)){
			jsonResult.setMessage("rebateIsNull");
			return jsonResult;
		}else if(!pattern.matcher(rebate).matches() /*|| (rebate.length() > 1 && Integer.valueOf(rebate.substring(0,1)) == 0)*/){
			jsonResult.setMessage("rebateNotPositiveInt");
			return jsonResult;
		}
		
		UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constants.TZDR_USER_SESSION);  //获取用户账户信息
		WUser newWuser = wUserService.getUser(userSessionBean.getId()); //获取用户新信息
		WUser subordinateWuser =  wUserService.getUser(uid); //获取用户设置的下级信息
		double userRebe = BigDecimalUtils.round2(newWuser.getRebate(), 2);
		double subordinateRebate = BigDecimalUtils.round2(subordinateWuser.getRebate(), 2);
		double subordinateNewRebate = BigDecimalUtils.round2(Double.valueOf(rebate), 2);
		if(subordinateNewRebate < subordinateRebate || subordinateNewRebate > userRebe){  //判断新设置的下级返点是否小于他原来的返点、或是否大于用户的返点
			jsonResult.setMessage("rebateError");
			return jsonResult;
		}
		//更新下级返点
		subordinateWuser.setRebate(subordinateNewRebate);
		wUserService.updateUser(subordinateWuser);
		return jsonResult;
	}
}
