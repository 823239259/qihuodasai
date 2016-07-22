package com.tzdr.business.service.togetherFuture.impl;

import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.service.comprehensiveCommodity.ComprehensiveCommodityParameterService;
import com.tzdr.business.service.togetherFuture.TogetherFutureService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.dao.togetherFuture.TogetherFutureDao;
import com.tzdr.domain.web.entity.ComprehensiveCommodityParameter;
import com.tzdr.domain.web.entity.TogetherFuture;
import jodd.util.StringUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class TogetherFutureServiceImpl extends BaseServiceImpl<TogetherFuture,TogetherFutureDao> implements TogetherFutureService{
    private static final Logger logger = LoggerFactory.getLogger(TogetherFutureService.class);
    @Autowired
    private AuthService authService;

    @Override
    public void create(TogetherFuture togetherFuture) {
        setOperateLog(togetherFuture,"添加期货合买参数设置","add");
        super.save(togetherFuture);
    }

    @Override
    public void delete(TogetherFuture togetherFuture) {
        this.getEntityDao().delete(togetherFuture);
    }

    @Override
    public void update(TogetherFuture togetherFutures)  {
        TogetherFuture togetherFuture=this.getEntityDao().findOne(togetherFutures.getId());
        try {
            BeanUtils.copyProperties(togetherFuture, togetherFutures);
            setOperateLog(togetherFuture,"更新期货合买参数设置","edit");
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }catch (InvocationTargetException a){
            a.printStackTrace();
        }
        super.update(togetherFuture);
    }



    private void setOperateLog(TogetherFuture outDiskPrice, String operateContent, String operateType){
        User loginUser = authService.getCurrentUser();
        outDiskPrice.setOperateContent(operateContent);
        if (StringUtil.equals(operateType, "edit")){
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

    @Override
    public List<Map<String,Object>> getType() {
        String sql = "select f.scope as scope, f.id as id from f_together_config f group by f.scope";
        List<Map<String,Object>> list=this.getEntityDao().queryMapBySql(sql);
        return list;
    }


}
