package com.tzdr.business.service.OutDisk;

import java.math.BigDecimal;
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
import com.tzdr.domain.dao.OutDisk.OutDiskParametersDao;
import com.tzdr.domain.web.entity.OutDiskParameters;
/**
 * 国际综合参数设置
 * 类说明
 * @author  zhaozhao
 * @date    2016年2月23日上午8:45:07
 * @version 1.0
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class OutDiskParametersService extends BaseServiceImpl<OutDiskParameters, OutDiskParametersDao>{
	@Autowired
	private AuthService authService;
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(OutDiskParametersService.class);


	public List<OutDiskParameters> findAllOutDiskParameters() {
		return this.getEntityDao().findAllOrderByTraderBond();
	}

	public List<OutDiskParameters> findByTraderBond(BigDecimal traderBond) {
		return this.getEntityDao().findByTraderBond(traderBond);
	}



	
	@Override
	public void save(OutDiskParameters o) throws BusinessException {
		// TODO Auto-generated method stub
		setOperateLog(o,"添加国际综合参数设置","add");
		super.save(o);
	}
	
	@Override
	public void update(OutDiskParameters o) throws BusinessException {
		OutDiskParameters odp=this.getEntityDao().findOne(o.getId());
		odp.setTraderBond(o.getTraderBond());
		odp.setTraderTotal(o.getTraderTotal());
		odp.setLineLoss(o.getLineLoss());
		odp.setGoldenMoney(o.getGoldenMoney());
		odp.setAtranActualLever(o.getAtranActualLever());
		odp.setHtranActualLever(o.getHtranActualLever());
		odp.setYtranActualLever(o.getYtranActualLever());
		
		odp.setMbtranActualLever(o.getMbtranActualLever());
		odp.setMdtranActualLever(o.getMdtranActualLever());
		odp.setMntranActualLever(o.getMntranActualLever());
		odp.setDaxtranActualLever(o.getDaxtranActualLever());
		odp.setNikkeiTranActualLever(o.getNikkeiTranActualLever());
		odp.setHstranActualLever(o.getHstranActualLever());
		odp.setAgtranActualLever(o.getAgtranActualLever());
		setOperateLog(odp,"修改国际综合参数设置","edit");
		super.update(odp);
	}
	
	
	private void setOperateLog(OutDiskParameters outDiskParameters,String operateContent,String operateType){
		User   loginUser = authService.getCurrentUser();	
		outDiskParameters.setOperateContent(operateContent);
		if (StringUtil.equals(operateType,"edit")){
			outDiskParameters.setUpdateTime(Dates.getCurrentLongDate());
			outDiskParameters.setUpdateUser(loginUser.getRealname());
			outDiskParameters.setUpdateUserOrg(loginUser.getOrganization().getName());
			outDiskParameters.setUpdateUserId(loginUser.getId());
			return ;
		}
		
		outDiskParameters.setCreateTime(Dates.getCurrentLongDate());
		outDiskParameters.setCreateUserId(loginUser.getId());
		outDiskParameters.setCreateUser(loginUser.getRealname());
		outDiskParameters.setCreateUserOrg(loginUser.getOrganization().getName());
		return ;
	}
	
}
