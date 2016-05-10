package com.tzdr.business.pgb.service;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.exception.BusinessException;
import com.tzdr.common.utils.Dates;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.pgb.dao.PGBPaymentSupportBankDao;
import com.tzdr.domain.pgb.entity.PGBPaymentSupportBank;
import com.tzdr.domain.vo.PaymentSupportBankVo;


/**
 * 网银通道设置service
 * @zhouchen
 * 2015年11月30日
 */
@Service
@Transactional
public class PGBBankChannelService extends BaseServiceImpl<PGBPaymentSupportBank,PGBPaymentSupportBankDao> {
	public static final Logger logger = LoggerFactory.getLogger(PGBBankChannelService.class);
	
	@Autowired
	private AuthService authService;
	@Autowired
	private EntityManager em;
	
	
	@Override
	public void update(PGBPaymentSupportBank o) throws BusinessException {
		PGBPaymentSupportBank pk=this.getEntityDao().findOne(o.getId());
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
	public void save(PGBPaymentSupportBank o) throws BusinessException {
		setOperateLog(o,"新增支付支持的银行","add");
		super.save(o);
	}
	
	@Override
	public void removeById(Serializable id) throws BusinessException {
		super.removeById(id);
	}
	
	public List<PGBPaymentSupportBank> queryBank(){
	
		return this.getAll();
	}
	
	
	
	
	private void setOperateLog(PGBPaymentSupportBank paymentSupportBank,String operateContent,String operateType){
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
	
	
	/**
	 * 获取支付 充值支持的银行
	 * @return
	 */
	public List<PaymentSupportBankVo> querySupportPayBanks(){
		String sql = "SELECT bank_name bankName, abbreviation abbreviation, icon_path iconPath FROM pgb_payment_support_bank WHERE support_bbpay = 1 OR support_umpay = 1 ORDER BY weight ASC ";
		List<PaymentSupportBankVo>  bankVos = this.nativeQuery(sql, null,PaymentSupportBankVo.class);
		return  bankVos;
	}
	
	/**
	 * 根据银行简称获取银行信息
	 * @param abbreviation
	 * @return
	 */
	public PGBPaymentSupportBank findByAbbreviation(String abbreviation){
		List<PGBPaymentSupportBank>  paymentSupportBanks =  getEntityDao().findByAbbreviation(abbreviation);
		if (CollectionUtils.isEmpty(paymentSupportBanks)){
			return null;
		}
		return paymentSupportBanks.get(0);
		
	}
	
	
	/**
	 * 获取支付  提现支持的银行
	 * @return
	 */
	public List<PaymentSupportBankVo> querySupportDrawBanks(int withdrawSetting){
		if (withdrawSetting !=1 && withdrawSetting !=2){
			return null;
		}
		String sql = "SELECT bank_name bankName, abbreviation abbreviation  FROM pgb_payment_support_bank";
		if (withdrawSetting==1){
			sql = sql + " WHERE support_umdraw = 1 ORDER BY weight ASC ";
		}
		
		if (withdrawSetting==2){
			sql = sql + " WHERE support_bbdraw = 1 ORDER BY weight ASC ";
		}
		List<PaymentSupportBankVo>  bankVos = this.nativeQuery(sql, null,PaymentSupportBankVo.class);
		return  bankVos;
	}
}
