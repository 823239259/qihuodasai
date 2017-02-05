package com.tzdr.business.service.investor;

import java.io.Serializable;
import java.util.List;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.exception.InvestorException;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.exception.BusinessException;
import com.tzdr.common.utils.Dates;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.dao.investor.InvestorDao;
import com.tzdr.domain.web.entity.Investor;

/**
 * 
 * @zhouchen
 * 2014年12月26日
 */
@Service
@Transactional
public class InvestorService extends BaseServiceImpl<Investor,InvestorDao> {
	public static final Logger logger = LoggerFactory.getLogger(InvestorService.class);
	
	
	@Autowired
	private AuthService authService;
	
	
	@Override
	public void update(Investor investor) {
		String investorId = investor.getId();
		Investor  dbInvestor= getEntityDao().get(investorId);
		
		if (ObjectUtil.equals(dbInvestor, null)){
			throw new InvestorException("business.update.not.found.data",null);
		}
		
		String idCard = investor.getIdCard();
		
		/*if (!StringUtil.equals(dbInvestor.getIdCard(),idCard)){
			List<Investor> lists = getEntityDao().findByIdCardAndDeletedFalse(idCard);
			if (!CollectionUtils.isEmpty(lists)){
				throw new InvestorException("investor.idcard.exist",null);
			}
		}*/
		dbInvestor.setRealName(investor.getRealName());
		dbInvestor.setIdCard(idCard);
		setOperateLog(dbInvestor,"更新出资人","edit");
		super.update(dbInvestor);
	}
	
	
	@Override
	public void save(Investor investor) {
		
		/*List<Investor> lists = getEntityDao().findByIdCardAndDeletedFalse(investor.getIdCard());
		if (!CollectionUtils.isEmpty(lists)){
			throw new InvestorException("investor.idcard.exist",null);
		}
		*/
		setOperateLog(investor,"新增出资人","add");
		super.save(investor);
	}

	@Override
    public void removes(Serializable... ids) throws BusinessException {
    	for (Serializable id : ids){
    		Investor investor = getEntityDao().get(id);
    		if (ObjectUtil.equals(investor, null)){
    			throw new InvestorException("business.delete.not.found.data",null);
    		}
    		investor.setDeleted(Boolean.TRUE);
    		setOperateLog(investor,"删除出资人","edit");
    		super.update(investor);
    	}
    }
	
	public List<Investor> findAll(){
		
		return getEntityDao().findAll();
	}
	
	private void setOperateLog(Investor investor,String operateContent,String operateType){
		User   loginUser = authService.getCurrentUser();	
		investor.setOperateContent(operateContent);
		if (StringUtil.equals(operateType,"edit")){
			investor.setUpdateTime(Dates.getCurrentLongDate());
			investor.setUpdateUser(loginUser.getRealname());
			investor.setUpdateUserOrg(loginUser.getOrganization().getName());
			investor.setUpdateUserId(loginUser.getId());
			return ;
		}
		
		investor.setCreateTime(Dates.getCurrentLongDate());
		investor.setCreateUser(loginUser.getRealname());
		investor.setCreateUserId(loginUser.getId());
		investor.setCreateUserOrg(loginUser.getOrganization().getName());
		return ;
	}
	
}
