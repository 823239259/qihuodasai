package com.tzdr.business.service.comprehensiveCommodity;

import java.util.List;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.domain.web.entity.ComprehensiveCommodityPrice;

/**
 * @description
 * @Author huangkai
 * @Date 2016-03-29
 */
public interface ComprehensiveCommodityPriceService extends BaseService<ComprehensiveCommodityPrice> {
    void edit(ComprehensiveCommodityPrice commodityPrice);
    
    /**
     * @Author Guo Xingyou
     */
    public List<ComprehensiveCommodityPrice> findAllComprehensiveCommodityPrice();
    void create(ComprehensiveCommodityPrice commodityPrice);
}
