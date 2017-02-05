package com.tzdr.business.service.pay;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import jodd.datetime.TimeUtil;
import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.exception.BusinessException;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.MultiListParam;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.dao.pay.PaymentSupportBankDao;
import com.tzdr.domain.vo.AppendLevelMoneyFailVo;
import com.tzdr.domain.vo.AppendMoneyFailVo;
import com.tzdr.domain.vo.PaymentSupportBankVo;
import com.tzdr.domain.web.entity.PaymentSupportBank;


/**
 * 网银通道设置service
 * @zhouchen
 * 2015年11月30日
 */
@Service
@Transactional
public class BankChannelService extends BaseServiceImpl<PaymentSupportBank,PaymentSupportBankDao> {
	public static final Logger logger = LoggerFactory.getLogger(BankChannelService.class);
	
	@Autowired
	private AuthService authService;
	@Autowired
	private EntityManager em;
	
	
	@Override
	public void update(PaymentSupportBank o) throws BusinessException {
		PaymentSupportBank pk=this.getEntityDao().findOne(o.getId());
		pk.setBankName(o.getBankName());
		pk.setAbbreviation(o.getAbbreviation());
		pk.setBbpayCode(o.getBbpayCode());
		pk.setBbpayContactNumber(o.getBbpayContactNumber());
		pk.setWeight(o.getWeight());
		pk.setIconPath(o.getIconPath());
		setOperateLog(pk,"修改支付支持的银行","edit");
		super.update(pk);
	}
	
	
	@Override
	public void save(PaymentSupportBank o) throws BusinessException {
		setOperateLog(o,"新增支付支持的银行","add");
		super.save(o);
	}
	
	@Override
	public void removeById(Serializable id) throws BusinessException {
		super.removeById(id);
	}
	
	public List<PaymentSupportBank> queryBank(){
	
		return this.getAll();
	}
	
	
	
	
	private void setOperateLog(PaymentSupportBank paymentSupportBank,String operateContent,String operateType){
		User   loginUser = authService.getCurrentUser();	
		paymentSupportBank.setOperateContent(operateContent);
		if (StringUtil.equals(operateType,"edit")){
			paymentSupportBank.setUpdateTime(Dates.getCurrentLongDate());
			paymentSupportBank.setUpdateUser(loginUser.getRealname());
			paymentSupportBank.setUpdateUserOrg(loginUser.getOrganization().getName());
			paymentSupportBank.setUpdateUserId(loginUser.getId());
			return ;
		}
		
		paymentSupportBank.setCreateTime(Dates.getCurrentLongDate());
		paymentSupportBank.setCreateUserId(loginUser.getId());
		paymentSupportBank.setCreateUser(loginUser.getRealname());
		paymentSupportBank.setCreateUserOrg(loginUser.getOrganization().getName());
		return ;
	}
}
