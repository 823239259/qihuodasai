package com.tzdr.business.service.comprehensiveCommodity;

import java.math.BigDecimal;
import java.util.List;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.domain.web.entity.ComprehensiveCommodityParameter;


/**
 * @description
 * @Author huangkai
 * @Date 2016-03-29
 */
public interface ComprehensiveCommodityParameterService extends BaseService<ComprehensiveCommodityParameter>{
    void edit(ComprehensiveCommodityParameter commodityParameter);
    void create(ComprehensiveCommodityParameter commodityParameter);
    /**
     * @Author Guo Xingyou
     */
    public List<ComprehensiveCommodityParameter> findAllComprehensiveCommodityParameter();
    /**
     * @Author Guo Xingyou
     */
    public List<ComprehensiveCommodityParameter> findByTraderBond(BigDecimal traderBond);
}
