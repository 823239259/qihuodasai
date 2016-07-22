package com.tzdr.business.service.futureMatchAccount;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.domain.vo.FutureMatchAccountVO;
import com.tzdr.domain.web.entity.FutureMatchAccount;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author GuoXingyou
 *
 */

public interface FutureMatchAccountService extends BaseService<FutureMatchAccount> {

    PageInfo<Object> queryAccountDate(EasyUiPageInfo easyUiPage, Map<String, Object> searchParams);
    
    public Map<String, List<FutureMatchAccountVO>> futureAccountStatistical();

    Boolean isUse(String name);
    
    /**
	 * easyui分页查询期货账号分配记录
	 * @param easyUiPage
	 * @param searchParams
	 * @return
	 */
	PageInfo<Object> getAssignFutureAccountList(EasyUiPageInfo easyUiPage,Map<String, Object> searchParams);

    List<Integer> getLever();

    /**
     * 得到一个未分配的账号
     * @param type
     * @param lever
     * @param money
     * @return
     */
    public FutureMatchAccount getOne(Integer type,Integer lever,Double money);

}
