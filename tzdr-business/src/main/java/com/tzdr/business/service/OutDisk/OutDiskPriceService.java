package com.tzdr.business.service.OutDisk;

import java.util.List;

import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.exception.BusinessException;
import com.tzdr.common.utils.Dates;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.dao.OutDisk.OutDiskPriceDao;
import com.tzdr.domain.web.entity.OutDiskParameters;
import com.tzdr.domain.web.entity.OutDiskPrice;
/**
 * 国际综合价格设置
 * 类说明
 * @author  zhaozhao
 * @date    2016年2月23日上午8:49:48
 * @version 1.0
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class OutDiskPriceService extends BaseServiceImpl<OutDiskPrice, OutDiskPriceDao>{
	
	private static final Logger logger = LoggerFactory.getLogger(OutDiskPriceService.class);

	public List<OutDiskPrice> findAllOutDiskPrice() {
		return this.getEntityDao().findAllOrderByTradeType();
	}
	@Autowired
	private AuthService authService;
	@Override
	public void save(OutDiskPrice o) throws BusinessException {
		// TODO Auto-generated method stub
		setOperateLog(o,"添加国际综合价格设置","add");
		super.save(o);
	}
	
	@Override
	public void update(OutDiskPrice o) throws BusinessException {
		OutDiskPrice odp=this.getEntityDao().findOne(o.getId());
		odp.setTradeType(o.getTradeType());
		odp.setMainContract(o.getMainContract());
		odp.setPrice(o.getPrice());
		setOperateLog(odp,"修改国际综合价格设置","edit");
		super.update(odp);
	}
	
	private void setOperateLog(OutDiskPrice outDiskPrice,String operateContent,String operateType){
		User   loginUser = authService.getCurrentUser();	
		outDiskPrice.setOperateContent(operateContent);
		if (StringUtil.equals(operateType,"edit")){
			outDiskPrice.setUpdateTime(Dates.getCurrentLongDate());
			outDiskPrice.setUpdateUser(loginUser.getRealname());
			outDiskPrice.setUpdateUserOrg(loginUser.getOrganization().getName());
			outDiskPrice.setUpdateUserId(loginUser.getId());
			return ;
		}
		
		outDiskPrice.setCreateTime(Dates.getCurrentLongDate());
		outDiskPrice.setCreateUserId(loginUser.getId());
		outDiskPrice.setCreateUser(loginUser.getRealname());
		outDiskPrice.setCreateUserOrg(loginUser.getOrganization().getName());
		return ;
	}
}
