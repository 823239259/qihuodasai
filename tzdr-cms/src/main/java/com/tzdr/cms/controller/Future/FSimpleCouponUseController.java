package com.tzdr.cms.controller.Future;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tzdr.business.service.future.FSimpleCouponService;
import com.tzdr.business.service.wuser.impl.WUserServiceImpl;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.DateUtils;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.EasyuiUtil;
import com.tzdr.common.web.support.EasyUiPageData;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.web.entity.WUser;
import com.tzdr.domain.web.entity.future.FSimpleCoupon;

@Controller
@RequestMapping("/admin/couponUse")
public class FSimpleCouponUseController extends BaseCmsController<FSimpleCoupon>{
	@Autowired
	private FSimpleCouponService fSimpleCouponService;
	@Autowired
	private WUserServiceImpl wUserServiceImpl;
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

	
	@RequestMapping("/findName")
	@ResponseBody
	public Object findName(ModelMap modelmap){
		List<FSimpleCoupon> all = fSimpleCouponService.queryCouponToGive();
		
				JSONArray  jsonArray = new JSONArray();
				if(!all.isEmpty()){
					for (FSimpleCoupon f : all) {
						JSONObject  jsonObject  = new JSONObject();
						jsonObject.put("id", f.getId());
						jsonObject.put("text", f.getName());
						jsonArray.add(jsonObject);
					}
				}else{
					return null;
				}
		return jsonArray;
		
	}
	@RequestMapping("/findById")
	@ResponseBody
	public JsonResult findById(String id){
		if(StringUtils.isEmpty(id)){
			return null;
		}
		FSimpleCoupon f = fSimpleCouponService.get(id);
		JsonResult jsonResult = new JsonResult();
		Map<String, Object> data = new HashMap<String, Object>();
		if(f!=null){
			data.put("type", f.getType());
			data.put("money", f.getMoney());
		}else{
			return new JsonResult(false);
		}
		jsonResult.appendData(data);
		return jsonResult;
		
	}
	
	
	@RequestMapping("/findByPhone")
	@ResponseBody
	public JsonResult findByPhone(String userPhone){
		if(StringUtils.isEmpty(userPhone)){
			return null;
		}
		WUser wUser = wUserServiceImpl.getWUserByMobile(userPhone);
		Map<String, Object> data = new HashMap<String, Object>();
		if(wUser!=null){
			data.put("tname", wUser.getUserVerified().getTname());
		}else{
			return new JsonResult(false);
		}
		JsonResult jsonResult = new JsonResult();
		jsonResult.appendData(data);
		return jsonResult;
		
	}
	
	/**
	 * 发放优惠券
	 * @param name
	 * @param userPhone
	 * @param userName
	 * @return
	 */
	@RequestMapping("/passGive")
	@ResponseBody
	public String passGive(String name,String userPhone,String userName){
		//判断绑定参数是否为空
		if(StringUtils.isEmpty(userPhone)&&StringUtils.isEmpty(name)){
			return null;
		}
		List<FSimpleCoupon> fs = fSimpleCouponService.findByStatusAndName((short)1, name);
		//判断查询数据是否为空
		if(CollectionUtils.isEmpty(fs)){
			return "false.list.null";
		}
		FSimpleCoupon f = fs.get(0);
		//判断优惠券状态是否为1，不是 返回false
		if(f.getStatus()!=1){
			return "false.status";
		}
		//判断优惠券截止日期是否过期
		if(f.getDeadline() != null && f.getDeadline() < Dates.getCurrentLongDate()){
			return "false.deadline";
		}
		
		WUser wUser = wUserServiceImpl.getWUserByMobile(userPhone);
		//判断用户是否存在
		if(wUser == null){
			 return "false.user";
		}
		
		f.setUserId(wUser.getId());
		f.setUserPhone(userPhone);
		f.setUserName(userName);
		f.setStatus((short)2);
		f.setGrantTime(new Date().getTime()/1000);
		if(f.getCycle()!=null && f.getCycle()!=0){
			Long grantTime = f.getGrantTime();
			Long deadLine = Dates.toDate(Dates.dateAddDay(Dates.parseLong2Date(grantTime),f.getCycle())).getTime() / 1000;
			f.setDeadline(deadLine);
		}
		fSimpleCouponService.update(f);
		return "true";
		
	}
	
	
	@RequestMapping("/useCoupon")
	@ResponseBody
	public String useCooupon(String id){
		//判断参数是否为空
		if(StringUtils.isEmpty(id)){
			return null;
		}
		FSimpleCoupon f = fSimpleCouponService.get(id);
		//判断优惠券是否存在
		if(f==null){
			return "false.null";
		}
		//判断优惠券状态是否是已发放，不是 返回false
		if(f.getStatus()!=2){
			return "false.status";
		}
		//判断优惠券截止日期，如果为空，即优惠券未发放；如果小于现在日期，即优惠券过期
		if(f.getDeadline()==null||f.getDeadline()<Dates.getCurrentLongDate()){
			return "false.deadline";	
		}

		f.setStatus((short)3);
		f.setUseTime(Dates.getCurrentLongDate());
		fSimpleCouponService.update(f);
		
		return "true";
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
				
				PageInfo<Object>  pageInfo = fSimpleCouponService.queryList(easyUiPage, searchParams);
				
				return new EasyUiPageData(pageInfo);
		} catch (Exception e) {
		  e.printStackTrace();
		}
		 
		 return null;
		  
	}
	
	
}
