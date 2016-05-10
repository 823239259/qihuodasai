package com.tzdr.cms.controller.Future;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.service.future.FSimpleCouponService;
import com.tzdr.business.service.future.impl.FSimpleCouponServiceImpl;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.controller.AppendLevelMoneyFailController;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.EasyuiUtil;
import com.tzdr.common.web.support.EasyUiPageData;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.vo.future.FSimpleCouponManageVo;
import com.tzdr.domain.web.entity.future.FSimpleCoupon;

@Controller
@RequestMapping("/admin/couponManage")
public class FSimpleCouponMnageController extends BaseCmsController<FSimpleCoupon>{
	private static Logger log = LoggerFactory.getLogger(AppendLevelMoneyFailController.class);
	@Autowired
	private AuthService authService;
	@Autowired
	private FSimpleCouponService fSimpleCouponService;
	@Autowired
	private FSimpleCouponServiceImpl fSimpleCouponServiceImpl;
	
	@Override
	public BaseService<FSimpleCoupon> getBaseService() {
		// TODO Auto-generated method stub
		return fSimpleCouponService;
	}

	@Override
	public void setResourceIdentity(String resourceIdentity) {
		// TODO Auto-generated method stub
		super.setResourceIdentity("sys:operationalConfig:simplecoupon");
	}
	
	@RequestMapping("list")
	public String getList(){
		return ViewConstants.SimpleCoupon.LIST_VIEW;
	}
	

	@RequestMapping("/add")
	@ResponseBody
	public JsonResult addCoupon(FSimpleCouponManageVo fSimpleCouponManageVo){
		if(fSimpleCouponManageVo==null){
			return new JsonResult(false,"参数不能为空");
		}
		String name = fSimpleCouponManageVo.getName();
		if(StringUtil.isBlank(name)){
			return new JsonResult(false,"名称不能为空");
		}
		Integer count=0;
		if(name!=null&&name!=""){
			 count = fSimpleCouponService.checkName(name);
		}
		if(count!=0){
			return new JsonResult(false,"名称必须唯一");
		}
		
		fSimpleCouponServiceImpl.addCoupons(fSimpleCouponManageVo, name);
		
		return new JsonResult(true);
		
	}
	
	@RequestMapping("/edit")
	@ResponseBody
	public Object edit(String name,Integer numToHave,Long deadline,Integer cycle){
		List<FSimpleCoupon> fS=null;
		if(name!=null){
			fS = fSimpleCouponServiceImpl.findByName(name);
		}else{
			return new JsonResult(false,"请选择数据！");
		}
		
		if(fS!=null){
			if(!ObjectUtil.equals(null, numToHave) && numToHave>0){
				//当新增个数大于0时，同时新增优惠券并修改使用周期或截止日期
				fS=fSimpleCouponServiceImpl.edits(fS, "add", deadline, cycle, numToHave);
			}else if(numToHave==0){
				//当新增个数等于0时，只修改使用周期或截止日期
				fS=fSimpleCouponServiceImpl.edits(fS, "edit", deadline, cycle, numToHave);
			}else{
				return new JsonResult(false,"新增个数有误！");
			}
			fSimpleCouponService.saves(fS);
			return new JsonResult(true);
		}else{
			return new JsonResult(false);
		}
	}
	
	
	/**
	 *  含参数的搜索  search_EQ_name
	 * @param easyUiPage
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getDatas",method = RequestMethod.POST)
	@ResponseBody
	public Object easyuiPage(EasyUiPageInfo easyUiPage, Model model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		 try {
			//查看权限
			  if (permissionList != null) {
		            this.permissionList.assertHasViewPermission();
		        }
			
				//获取模糊搜索参数
				Map<String, Object> searchParams = EasyuiUtil.getParametersStartingWith(request,EasyuiUtil.SEARCH_PREFIX);
				//排序设置
				EasyuiUtil.getMultilistSortMap(easyUiPage);
				//查询数据
				
				PageInfo<Object>  pageInfo = fSimpleCouponServiceImpl.queryData(easyUiPage, searchParams);
				
				return new EasyUiPageData(pageInfo);
		} catch (Exception e) {
		  e.printStackTrace();
		}
		 
		 return null;
		  
	}
	
	
}
