package com.tzdr.business.service.customer.impl;

import java.util.ArrayList;
import java.util.List;

import jodd.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.cms.service.user.UserService;
import com.tzdr.business.service.customer.CustomerService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.DateUtils;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.dao.customer.CustomerDao;
import com.tzdr.domain.vo.CustomerVo;
import com.tzdr.domain.web.entity.Customer;
@Service("customerService")
public class CustomerServiceImpl extends BaseServiceImpl<Customer, CustomerDao> implements CustomerService {

	@Autowired
	private AuthService authService;
	
	@Autowired
	private UserService userService;
	
	@Override
	public void saveCustomer(Customer customer) {
		this.getEntityDao().save(customer);
	}

	@Override
	public Customer getByMobile(String mobile) {
		if(StringUtil.isBlank(mobile)){
			return null;
		}
		return this.getEntityDao().findByMobile(mobile);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageInfo<CustomerVo> findCustomerList(PageInfo<CustomerVo> dataPage,
			CustomerVo vo) {

		List<Object> params=new ArrayList<Object>();
		
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT tm.id,tm.name,tm.mobile,tm.isSignIn,tm.signInTime,tm.isTrade,tm.firstTradeTime, ");
		sql.append(" tm.lastTradeTime,tm.lastContactTime,tm.belongMarket,tm.realName,tm.organizationCode,tm.createTime,tm.assignTime ");
		sql.append(" FROM ( ");
		sql.append(" SELECT c.id,c.name,c.mobile,if(tp.uid IS NULL,0,1) AS isSignIn,tp.ctime AS signInTime, ");
		sql.append(" if(tp.tid IS NULL,0,1) AS isTrade,MIN(tp.addtime) AS firstTradeTime,MAX(tp.addtime) AS lastTradeTime, ");
		sql.append(" max(d.contact_time) AS lastContactTime,c.belong_market AS belongMarket,s.realName,c.organization_code AS organizationCode,c.create_time AS createTime,c.assign_time AS assignTime ");
		sql.append(" FROM  w_customer c ");
		sql.append(" LEFT JOIN w_customer_detail d ON c.id=d.c_id ");
		sql.append(" LEFT JOIN sys_user s ON c.belong_market=s.id ");
		sql.append(" LEFT JOIN ( ");
		sql.append(" SELECT tmp.id AS uid,tmp.mobile,tmp.ctime,t.id AS tid,t.addtime ");
		sql.append(" FROM ( SELECT u.id,m.mobile,u.ctime FROM w_customer m,w_user u where m.mobile=u.mobile) tmp ");
		sql.append(" LEFT JOIN w_user_trade t ON tmp.id=t.uid ");
		sql.append(" ) tp ON c.mobile=tp.mobile  GROUP BY c.id ) tm where 1=1 ");
		if(vo != null){
			if(!StringUtil.isBlank(vo.getName())){   //客户姓名
				params.add("%"+String.valueOf(vo.getName()) + "%");
				sql.append(" and tm.name like ? ");
			}
			if(!StringUtil.isBlank(vo.getMobile())){ //客户电话
				params.add("%"+String.valueOf(vo.getMobile()) + "%");
				sql.append(" and tm.mobile like ? ");
			}
			if(vo.getIsSignIn() != null){   //是否注册
				params.add(vo.getIsSignIn());
				sql.append(" and tm.isSignIn=? ");			
			}
			if(vo.getIsTrade() != null){  //是否配资
				params.add(vo.getIsTrade());
				sql.append(" and tm.isTrade=? ");	
			}
			if(!StringUtil.isBlank(vo.getOrganizationCode())){  //所属组织code
				params.add(String.valueOf(vo.getOrganizationCode())+ "%");
				sql.append(" and tm.organizationCode like ? ");
			}
			if(vo.getBelongMarket() != null){  //所属人编号
				params.add(vo.getBelongMarket());
				sql.append(" and tm.belongMarket=? ");	
			}
			
			if(!StringUtil.isBlank(vo.getStartSignInTime())){  //注册时间段开始时间
				params.add(DateUtils.stringToDate(vo.getStartSignInTime(), "yyyy-MM-dd").getTime()/1000);
				sql.append(" and tm.signInTime >= ? ");	
			}
			if(!StringUtil.isBlank(vo.getEndSignInTime())){    //注册时间段结束时间
				params.add(DateUtils.stringToDate(vo.getEndSignInTime()+" 23:59:59", "yyyy-MM-dd HH:mm:ss").getTime()/1000);
				sql.append(" and tm.signInTime <= ? ");	
			}
			
			if(!StringUtil.isBlank(vo.getStartFirstTradeTime())){  //首次配资时间段开始时间
				params.add(DateUtils.stringToDate(vo.getStartFirstTradeTime(), "yyyy-MM-dd").getTime()/1000);
				sql.append(" and tm.firstTradeTime >= ? ");	
			}
			if(!StringUtil.isBlank(vo.getEndFirstTradeTime())){    //首次配资时间段结束时间
				params.add(DateUtils.stringToDate(vo.getEndFirstTradeTime()+" 23:59:59", "yyyy-MM-dd HH:mm:ss").getTime()/1000);
				sql.append(" and tm.firstTradeTime <= ? ");	
			}
			
			if(!StringUtil.isBlank(vo.getStartLastTradeTime())){    //最后配资时间段开始时间
				params.add(DateUtils.stringToDate(vo.getStartLastTradeTime(), "yyyy-MM-dd").getTime()/1000);
				sql.append(" and tm.lastTradeTime >= ? ");	
			}
			if(!StringUtil.isBlank(vo.getEndLastTradeTime())){       //最后配资时间段结束时间
				params.add(DateUtils.stringToDate(vo.getEndLastTradeTime()+" 23:59:59", "yyyy-MM-dd HH:mm:ss").getTime()/1000);
				sql.append(" and tm.lastTradeTime <= ? ");
			}
			
			if(!StringUtil.isBlank(vo.getStartLastContactTime())){    //最后联系时间段开始时间
				params.add(DateUtils.stringToDate(vo.getStartLastContactTime(), "yyyy-MM-dd").getTime()/1000);
				sql.append(" and tm.lastContactTime >= ? ");	
			}
			if(!StringUtil.isBlank(vo.getEndLastContactTime())){       //最后联系时间段结束时间
				params.add(DateUtils.stringToDate(vo.getEndLastContactTime()+" 23:59:59", "yyyy-MM-dd HH:mm:ss").getTime()/1000);
				sql.append(" and tm.lastContactTime <= ? ");	
			}
			
			if(!StringUtil.isBlank(vo.getStartCreateTime())){          //创建客户时间段开始时间
				params.add(DateUtils.stringToDate(vo.getStartCreateTime(), "yyyy-MM-dd").getTime()/1000);
				sql.append(" and tm.createTime >= ? ");
			}if(!StringUtil.isBlank(vo.getEndCreateTime())){           //创建客户时间段结束时间
				params.add(DateUtils.stringToDate(vo.getEndCreateTime()+" 23:59:59", "yyyy-MM-dd HH:mm:ss").getTime()/1000);
				sql.append(" and tm.createTime <= ? ");			
			}
		}
		PageInfo<CustomerVo> page= getEntityDao().queryPageBySql(dataPage, sql.toString(),
				CustomerVo.class,null, params.toArray());
		return page;
	}

	@Override
	public void updateCustomer(Long belongMarket,String organizationCode, String[] idArray) {
		if(belongMarket == null || idArray== null || idArray.length <= 0){
			return;
		}
		User user = authService.getCurrentUser();
		List<Customer> customerList = new ArrayList<Customer>();   
		for (String id : idArray) {
			Customer customer = this.getEntityDao().get(id);
			if(customer != null){
				customer.setUpdateTime(TypeConvert.dbDefaultDate());
				customer.setUpdateUserId(user.getId());
				customer.setUpdateUser(user.getRealname());
				customer.setBelongMarket(belongMarket);
				customer.setOrganizationCode(organizationCode);
				customer.setAssignTime(TypeConvert.dbDefaultDate());
				customerList.add(customer);
			}
		}
		if(customerList != null && !customerList.isEmpty()){
			for (Customer customer : customerList) {
				this.getEntityDao().update(customer);
			}
		}
	}
	
}
