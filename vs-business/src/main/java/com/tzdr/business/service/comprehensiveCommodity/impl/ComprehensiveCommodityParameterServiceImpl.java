package com.tzdr.business.service.comprehensiveCommodity.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.service.comprehensiveCommodity.ComprehensiveCommodityParameterService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.utils.Dates;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.dao.comprehensiveCommodity.ComprehensiveCommodityParameterDao;
import com.tzdr.domain.web.entity.ComprehensiveCommodityParameter;

import jodd.util.StringUtil;

/**
 * @description
 * @Author huangkai
 * @Date 2016-03-29
 */
@Service("comprehensiveCommodityParameterService")
@Transactional
public class ComprehensiveCommodityParameterServiceImpl extends BaseServiceImpl<ComprehensiveCommodityParameter, ComprehensiveCommodityParameterDao> implements ComprehensiveCommodityParameterService{

    private static final Logger logger = LoggerFactory.getLogger(ComprehensiveCommodityParameterService.class);
    @Autowired
    private AuthService authService;
    @Override
    public void edit(ComprehensiveCommodityParameter commodityParameters)  {
           ComprehensiveCommodityParameter commodityParameter=this.getEntityDao().findOne(commodityParameters.getId());
            try {
                BeanUtils.copyProperties(commodityParameter, commodityParameters);
                setOperateLog(commodityParameter,"更新商品综合参数设置","edit");
            }catch (IllegalAccessException e){
                e.printStackTrace();
            }catch (InvocationTargetException a){
                a.printStackTrace();
            }
            super.update(commodityParameter);
    }


    
    @Override
    public List<ComprehensiveCommodityParameter> findAllComprehensiveCommodityParameter() {
        // TODO Auto-generated method stub
        return this.getEntityDao().findAllOrderByTraderBond();
    }
    @Override
    public List<ComprehensiveCommodityParameter> findByTraderBond(BigDecimal traderBond) {
    	// TODO Auto-generated method stub
    	return this.getEntityDao().findByTraderBond(traderBond);
    }
    
    @Override
    public void create(ComprehensiveCommodityParameter commodityParameter) {
        setOperateLog(commodityParameter,"添加商品综合参数设置","add");
        super.save(commodityParameter);
    }

    private void setOperateLog(ComprehensiveCommodityParameter outDiskPrice, String operateContent, String operateType){
        User loginUser = authService.getCurrentUser();
        outDiskPrice.setOperateContent(operateContent);
        if (StringUtil.equals(operateType,"edit")){
            outDiskPrice.setUpdateTime(Dates.getCurrentLongDate());
            outDiskPrice.setUpdateUser(loginUser.getRealname());
            outDiskPrice.setUpdateUserOrg(loginUser.getOrganization().getName());
            outDiskPrice.setUpdateUserId(loginUser.getId());
        }else {
            outDiskPrice.setCreateTime(Dates.getCurrentLongDate());
            outDiskPrice.setCreateUserId(loginUser.getId());
            outDiskPrice.setCreateUser(loginUser.getRealname());
            outDiskPrice.setCreateUserOrg(loginUser.getOrganization().getName());
        }
    }
}
