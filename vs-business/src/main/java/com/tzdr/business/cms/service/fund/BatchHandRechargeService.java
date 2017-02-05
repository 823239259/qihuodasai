package com.tzdr.business.cms.service.fund;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.tzdr.business.cms.service.auth.UserAuthService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.MultiListParam;
import com.tzdr.domain.cms.entity.fund.BatchHandRecharge;
import com.tzdr.domain.dao.rechargelist.BatchHandRechargeDao;
import com.tzdr.domain.vo.cms.BatchHandRechargeVo;

/**
 * 
 * <B>说明:手工批量现金充值 </B>
 * @zhouchen
 * 2016年2月17日 上午10:43:24
 */
@Service
public class BatchHandRechargeService  extends BaseServiceImpl<BatchHandRecharge, BatchHandRechargeDao>  {

    @Autowired
    private UserAuthService userAuthService;  

    /**
     * 分页查询批量处理数据
     * @param easyUiPage
     * @param searchParams
     * @return
     */
    public PageInfo<Object> queryDatas(EasyUiPageInfo easyUiPage,
			Map<String, Object> searchParams) {
		String sql = " SELECT bhr.id, bhr.mobile, bhr.type, ver.tname, bhr.serial_number serialNumber, bhr.import_time importTime, bhr.money, bhr.reason, bhr.handle_result handleResult FROM w_batch_hand_recharge bhr, w_user usr, w_user_verified ver WHERE usr.id = ver.uid AND bhr.mobile = usr.mobile ";
		return multiListPageQuery(new MultiListParam(easyUiPage, searchParams, null, sql), BatchHandRechargeVo.class);
    }

    public Double getMoney(Object type){
        StringBuffer sql =  new StringBuffer("select sum(money) from w_batch_hand_recharge ");
        if(null != type && !type.equals("")) {
            sql.append("where type="+type);
        }
        return (Double)this.nativeQueryOne(sql.toString(),null);

    }
    /**
     * 根据调帐类型查询数据
     * @param type
     * @return
     */
    public List<BatchHandRecharge> queryByType(int type){
    	return this.getEntityDao().findByType(type);
    }
    
    
    public void  deleteDatas() {
		List<BatchHandRecharge>  batchHandRecharges = this.getAll();
		if (!CollectionUtils.isEmpty(batchHandRecharges)){
			for (BatchHandRecharge batchHandRecharge : batchHandRecharges){
				this.remove(batchHandRecharge);
			}
		}
    }
    
}
