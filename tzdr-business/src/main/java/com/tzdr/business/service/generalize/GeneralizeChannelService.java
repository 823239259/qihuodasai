package com.tzdr.business.service.generalize;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.web.entity.GeneralizeChannel;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/13.
 */
public interface GeneralizeChannelService extends BaseService<GeneralizeChannel> {

    public PageInfo<Object> queryAllTrades(EasyUiPageInfo easyUiPage,
                                           Map<String, Object> searchParams);

    public boolean isRepeat(String parms);

    public void create(GeneralizeChannel generalizeChannel);

    public List<String> getType(String name,Integer type);

    public Map<String,List<String>> getList();
    
    public JsonResult delete(GeneralizeChannel generalizeChannel);
    /**
     * 获取渠道
     * @param params
     * @return
     */
    public List<GeneralizeChannel> findByParamAndDeletedFalse(String params);
}
