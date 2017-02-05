package com.tzdr.cms.controller;




import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.StringUtil;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.cms.service.user.UserService;
import com.tzdr.business.service.customer.CustomerDetailsService;
import com.tzdr.business.service.customer.CustomerService;
import com.tzdr.cms.constants.Constants;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.cms.utils.DataGridVo;
import com.tzdr.cms.utils.WebUtil;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.vo.CustomerDetailsVo;
import com.tzdr.domain.vo.CustomerVo;
import com.tzdr.domain.web.entity.Customer;
import com.tzdr.domain.web.entity.CustomerDetails;

@Controller
@RequestMapping("/admin/customer")
public class CustomerController extends BaseCmsController<Customer> {

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CustomerDetailsService customerDetailsService;

	@Autowired
	private AuthService authService;
	
	@Autowired
	private UserService userService;

	@Override
	public BaseService<Customer> getBaseService() {
		return customerService;
	}

	@Override
	public void setResourceIdentity(String resourceIdentity) {
		super.setResourceIdentity("sys:customerService:customer");
	}

	/**
	 * 销售客户列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="list")
	public String list(HttpServletRequest request){
		return ViewConstants.CustomerJsp.LIST_VIEW;
	}

	/**
	 * 根据条件获取客户信息
	 * @param agentVo
	 * @param request
	 * @param resp
	 * @throws Exception
	 */
	@RequestMapping(value = "/listData")
	public void listData(@ModelAttribute CustomerVo customerVo,HttpServletRequest request,HttpServletResponse resp) throws Exception {
		try {
			DataGridVo<CustomerVo> grid = new DataGridVo<CustomerVo>();
			PageInfo<CustomerVo> dataPage = new PageInfo<CustomerVo>(request);
			if(customerVo != null){
				User user = authService.getCurrentUser();
				if(customerVo.getBelongMarket() == null && user.getOrganization().getCode().length() >= 12){  //销售专员
					customerVo.setBelongMarket(user.getId());
				}else{
					customerVo.setOrganizationCode(user.getOrganization().getCode());
				}
			}
			PageInfo<CustomerVo> datas = this.customerService.findCustomerList(dataPage, customerVo);
			grid.add(datas.getPageResults());
			grid.setTotal(datas.getTotalCount());
			WebUtil.printText(JSON.toJSONString(grid), resp);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 销售客户联系详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value="details/{id}")
	public String details(ModelMap modelMap,@PathVariable("id") String id ,HttpServletRequest request, CustomerVo customerVo){
		modelMap.put("customerId", id);
		modelMap.put("customer", customerVo);
		return ViewConstants.CustomerJsp.DETAILS_VIEW;
	}
	
	/**
	 * 根据条件获取客户联系信息
	 * @param agentVo
	 * @param request
	 * @param resp
	 * @throws Exception
	 */
	@RequestMapping(value = "/detailsData/{id}")
	public void detailsData(@PathVariable("id") String id ,HttpServletRequest request,HttpServletResponse resp) throws Exception {
		try {
			
			List<CustomerDetails> customerDetailsList =this.customerDetailsService.findCustomerDetailsByCid(id); 
			List<CustomerDetailsVo> datas = new ArrayList<CustomerDetailsVo>();
			if(customerDetailsList != null && !customerDetailsList.isEmpty()){
				for (CustomerDetails customerDetails : customerDetailsList) {
					CustomerDetailsVo vo = new CustomerDetailsVo();
					vo.setId(customerDetails.getId());
					vo.setContactTime(customerDetails.getContactTime());
					vo.setRemark(customerDetails.getRemark());
					vo.setCreateUser(customerDetails.getCreateUser());
					vo.setCreateTime(customerDetails.getCreateTime());
					vo.setUpdateUser(customerDetails.getUpdateUser());
					vo.setUpdateTime(customerDetails.getUpdateTime());
					datas.add(vo);
				}
			}
			WebUtil.printText(JSON.toJSONString(datas), resp);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 新增客户信息
	 * @param vo 客户信息
	 * @param request
	 * @param resp
	 */
	@RequestMapping(value = "/saveCustomer")
	@ResponseBody
	public JsonResult saveCustomer(@ModelAttribute CustomerVo vo, HttpServletRequest request,HttpServletResponse resp) {
		JsonResult result=new JsonResult(true);
		if(vo == null || StringUtil.isBlank(vo.getName()) || StringUtil.isBlank(vo.getMobile()) || StringUtil.isBlank(vo.getContactTimeStr()) || StringUtil.isBlank(vo.getRemark())){
			result.setSuccess(false);
			result.setMessage("提交客户数据有误");
			return result;
		}

		Customer oldCustomer = customerService.getByMobile(vo.getMobile());
		if(oldCustomer != null){
			result.setSuccess(false);
			result.setMessage("手机号码重复");
			return result;
		}

		User user = authService.getCurrentUser();
		Customer customer = new Customer();  //创建客户信息
		customer.setMobile(vo.getMobile());
		customer.setName(vo.getName());
		customer.setCreateTime(TypeConvert.dbDefaultDate());
		customer.setCreateUserId(user.getId());
		customer.setCreateUser(user.getRealname());
		customer.setBelongMarket(user.getId());
		customer.setAssignTime(TypeConvert.dbDefaultDate());
		customer.setOrganizationCode(user.getOrganization().getCode());
		CustomerDetails customerDetails = new CustomerDetails();  //创建与客户联系信息
		customerDetails.setContactTime(TypeConvert.strToDatetime(vo.getContactTimeStr()+" 00:00:00").getTime()/ 1000);
		customerDetails.setRemark(vo.remark);
		customerDetails.setCreateTime(TypeConvert.dbDefaultDate());
		customerDetails.setCreateUserId(user.getId());
		customerDetails.setCreateUser(user.getRealname());
		customer.add(customerDetails);

		customerService.saveCustomer(customer);

		result.setMessage("添加成功");
		return result;
	}
	
	/**
	 * 分配客户
	 * @param request
	 * @param belongMarket
	 * @param ids
	 */
	@RequestMapping(value="assign")
	@ResponseBody
	public JsonResult assign(HttpServletRequest request,
			@RequestParam("belongMarket") Long belongMarket,
			@RequestParam("ids") String  ids){
		JsonResult result=new JsonResult(true);
		String [] idArray = ids.split(Constants.SEPERATOR_COMMA);
		if (ArrayUtils.isEmpty(idArray)){
			result.setSuccess(false);
			result.setMessage("请选择数据");
			return result;
		}else if(belongMarket == null){
			result.setSuccess(false);
			result.setMessage("请选择分配的销售人员");
			return result;
		}
		User belongUser = userService.get(belongMarket);
		if(belongUser == null){
			result.setSuccess(false);
			result.setMessage("未找到该销售人员");
			return result;
		}
		customerService.updateCustomer(belongMarket,belongUser.getOrganization().getCode(), idArray);
		result.setMessage("分配成功");
		return result;
	}
	
	/**
	 * 新增客户联系信息
	 * @param vo 客户与客户联系信息
	 * @param request
	 * @param resp
	 */
	@RequestMapping(value = "/saveCustomerDetails")
	@ResponseBody
	public JsonResult saveCustomerDetails(@ModelAttribute CustomerDetailsVo vo, HttpServletRequest request,HttpServletResponse resp) {
		JsonResult result=new JsonResult(true);
		if(vo == null || StringUtil.isBlank(vo.getCustomerId()) || StringUtil.isBlank(vo.getContactTimeStr()) || StringUtil.isBlank(vo.getRemark())){
			result.setSuccess(false);
			result.setMessage("提交联系数据有误");
			return result;
		}
		Customer customer = customerService.get(vo.getCustomerId());  //获取客户信息
		if(customer == null){
			result.setSuccess(false);
			result.setMessage("未找到该客户信息，提交联系数据有误");
			return result;
		}
		
		User user = authService.getCurrentUser();
		CustomerDetails customerDetails = new CustomerDetails();  //创建与客户联系信息
		customerDetails.setContactTime(TypeConvert.strToDatetime(vo.getContactTimeStr()+" 00:00:00").getTime()/ 1000);
		customerDetails.setRemark(vo.getRemark());
		customerDetails.setCreateTime(TypeConvert.dbDefaultDate());
		customerDetails.setCreateUserId(user.getId());
		customerDetails.setCreateUser(user.getRealname());
		customerDetails.setCustomer(customer);
		customerDetailsService.saveCustomerDetails(customerDetails);

		result.setMessage("添加成功");
		return result;
	}
	
	/**
	 * 修改客户联系信息
	 * @param vo 客户与客户联系信息
	 * @param request
	 * @param resp
	 */
	@RequestMapping(value = "/updateCustomerDetails")
	@ResponseBody
	public JsonResult updateCustomerDetails(@ModelAttribute CustomerDetailsVo vo, HttpServletRequest request,HttpServletResponse resp) {
		JsonResult result=new JsonResult(true);
		if(vo == null || StringUtil.isBlank(vo.getCustomerId()) ||  StringUtil.isBlank(vo.getId()) || StringUtil.isBlank(vo.getContactTimeStr()) || StringUtil.isBlank(vo.getRemark())){
			result.setSuccess(false);
			result.setMessage("提交联系数据有误");
			return result;
		}
		CustomerDetails customerDetails = customerDetailsService.get(vo.getId());  //更新客户联系信息
		if(customerDetails == null){
			result.setSuccess(false);
			result.setMessage("未找到该信息，提交联系数据有误");
			return result;
		}
		
		User user = authService.getCurrentUser();
		customerDetails.setContactTime(TypeConvert.strToDatetime(vo.getContactTimeStr()+" 00:00:00").getTime()/ 1000);
		customerDetails.setRemark(vo.getRemark());
		customerDetails.setUpdateTime(TypeConvert.dbDefaultDate());
		customerDetails.setUpdateUserId(user.getId());
		customerDetails.setUpdateUser(user.getRealname());
		customerDetailsService.updateCustomerDetails(customerDetails);
		result.setMessage("修改成功");
		return result;
	}
}
