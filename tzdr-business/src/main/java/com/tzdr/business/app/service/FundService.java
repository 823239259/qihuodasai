package com.tzdr.business.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jodd.util.StringUtil;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.domain.app.vo.UserFundVo;
import com.tzdr.domain.dao.pay.UserFundDao;
import com.tzdr.domain.web.entity.UserFund;

/**  
 * @Title: FundService.java     
 * @Description: (用一句话描述该文件做什么)    
 * @author： WangPinQun 
 * @E-mail：wangpinqun@tzdr.com
 * @company： 上海信闳投资管理有限公司重庆分公司
 * @address：重庆市渝北区黄山大道中段70号两江新界3栋13楼
 * @date： 2016年3月24日 上午11:08:04    
 * @version： V1.0  
 */
@Transactional(propagation=Propagation.REQUIRED)
@Service("fundService")
public class FundService  extends BaseServiceImpl<UserFund,UserFundDao> {

	/**
	* @Title: getFundbytype    
	* @Description: 根据条件获取用户资金明细情况【个数、总额】 ,如：总收入笔数及总收入金额、总支出笔数及总支出金额；
	* @param userId 用户编号
	* @param types  资金明细类型
	* @return
	 */
	public List<Map<String, Object>> getFundbytype(String userId,Integer...types) {
		
		if(StringUtil.isBlank(userId)){
			return null;
		}
		
		List<Object> paramList = new ArrayList<Object>();
		
		paramList.add(userId);

		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT count(*) as count, SUM(abs(money)) summoney ");
		sql.append(" FROM w_user_fund WHERE  uid=? ");

		if(types != null && types.length > 0){
			
			String inSql = this.getQueryInSql(types.length);
			
			sql.append(" AND type in "+inSql+" ");
			
			for (Integer integer : types) {
				paramList.add(integer);
			}
		}
		
		return this.getEntityDao().queryMapBySql(sql.toString(), paramList.toArray());
	}
	
	/**
	* @Title: findUserFundVos    
	* @Description: 获取用户资金明细信息
	* @param uid
	* @return
	 */
	@SuppressWarnings("unchecked")
	public List<UserFundVo> findUserFundVos(String uid){
		
		if(StringUtil.isBlank(uid)){
			return null;
		}
		
		StringBuffer sql  = new StringBuffer();
		sql.append(" SELECT f.addtime,f.type,f.money,f.uptime,f.pay_status,f.lid,f.rid,f.remark ");
		sql.append(" FROM w_user_fund f WHERE f.uid=? ORDER BY f.addtime DESC ");
		List<UserFundVo> dataList = this.getEntityDao().queryListBySql(sql.toString(), UserFundVo.class, null, new Object[]{uid});
		
		return dataList;
	}
}
