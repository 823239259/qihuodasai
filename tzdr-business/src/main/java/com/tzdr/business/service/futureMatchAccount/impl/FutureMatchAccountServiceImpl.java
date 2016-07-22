package com.tzdr.business.service.futureMatchAccount.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.tzdr.business.service.futureMatchAccount.FutureMatchAccountService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.MultiListParam;
import com.tzdr.domain.dao.futureMatchAccount.FutureMatchAccountDao;
import com.tzdr.domain.vo.FutureAccountAssignVO;
import com.tzdr.domain.vo.FutureMatchAccountVO;
import com.tzdr.domain.web.entity.FutureMatchAccount;

import jodd.util.StringUtil;

/**
 * 
 * @author GuoXingyou
 *
 */
@Service("futureMatchAccountService")
@Transactional
public class FutureMatchAccountServiceImpl extends BaseServiceImpl<FutureMatchAccount, FutureMatchAccountDao> implements FutureMatchAccountService {
	private static Logger log = LoggerFactory.getLogger(FutureMatchAccountServiceImpl.class);

	@Override
	public PageInfo<Object> queryAccountDate(EasyUiPageInfo easyUiPage,
														   Map<String, Object> searchParams) {
		if ("createTimeStr".equals(easyUiPage.getSort())) {
			easyUiPage.setSort("createTime");
		}
		PageInfo<Object> pageInfo = new PageInfo<>(easyUiPage.getPage(),easyUiPage.getRows());
		StringBuffer sql = new StringBuffer();
		sql.append("select	f.id as id ,f.account AS account ,f.`password` AS password , f.lever as lever , f.trade_money as tradeMoney ,f.business_type as businessType,f.create_time as createTime from f_future_match_account f where is_use = 0 ");
		sql.append("ORDER BY f.create_time DESC");
		List<Object> params = Lists.newArrayList();
		MultiListParam multilistParam = new MultiListParam(easyUiPage,
				searchParams, params, sql.toString());
		pageInfo = multiListPageQuery(multilistParam, FutureMatchAccountVO.class);
		return pageInfo ;
	}

	
	
	@Override
	public Map<String,List<FutureMatchAccountVO>> futureAccountStatistical() {

		String goodsSql = " SELECT id, '1' AS businessType, '0' AS lever, trader_total AS traderMoney, (SELECT count(1) FROM f_future_match_account intb WHERE intb.business_type = 1 AND is_use = 0 AND intb.trade_money = trader_total AND intb.lever = 0) AS number FROM w_comprehensive_commodity_parameter ";
		String internationalSql = " SELECT id, '2' AS businessType, '0' AS lever, trader_total AS traderMoney, (SELECT count(1) FROM f_future_match_account intb WHERE intb.business_type = 2 AND is_use = 0 AND intb.trade_money = trader_total AND intb.lever = 0) AS number FROM w_out_disk_parameters ";
		String a50Sql = " SELECT id, '3' AS businessType, tran_lever AS lever, trader_money AS traderMoney, (SELECT count(1) FROM f_future_match_account intb WHERE intb.business_type = 3 AND is_use = 0 AND intb.trade_money = trader_money AND intb.lever = tran_lever) AS number FROM f_simple_config WHERE type = 5 ";
		String oilSql = " SELECT id, '4' AS businessType, tran_lever AS lever, trader_money AS traderMoney, (SELECT count(1) FROM f_future_match_account intb WHERE intb.business_type = 4 AND is_use = 0 AND intb.trade_money = trader_money AND intb.lever = tran_lever) AS number FROM f_simple_config WHERE type = 6 ";
		String hsiSql = " SELECT id, '5' AS businessType, tran_lever AS lever, trader_money AS traderMoney, (SELECT count(1) FROM f_future_match_account intb WHERE intb.business_type = 5 AND is_use = 0 AND intb.trade_money = trader_money AND intb.lever = tran_lever ) AS number FROM f_simple_config WHERE type = 7 ";
		String lhsiSql = " SELECT id, '6' AS businessType, tran_lever AS lever, trader_money AS traderMoney, (SELECT count(1) FROM f_future_match_account intb WHERE intb.business_type = 6 AND is_use = 0 AND intb.trade_money = trader_money AND intb.lever = tran_lever ) AS number FROM f_simple_config WHERE type = 9 ";
		
		String sql = " SELECT id, businessType, CONVERT (lever, SIGNED) AS lever, traderMoney, number FROM ( " + goodsSql + " UNION ALL " + internationalSql + " UNION ALL " + a50Sql + " UNION ALL " + oilSql + " UNION ALL " + hsiSql + " UNION ALL " +lhsiSql + ") rs order by businessType asc, lever asc, traderMoney asc";

		// 1.��Ʒ�ڻ��ۺ�
		List<FutureMatchAccountVO> goodsFuture = Lists.newArrayList();
		// 2.�����ڻ��ۺ�
		List<FutureMatchAccountVO> internationalFuture = Lists.newArrayList();
		// 3.��ʱA50
		List<FutureMatchAccountVO> a50Future = Lists.newArrayList();
		// 4.����ԭ��
		List<FutureMatchAccountVO> oilFuture = Lists.newArrayList();
		// 5.����ָ��
		List<FutureMatchAccountVO> hsiFuture = Lists.newArrayList();
		//6.小恒指
		List<FutureMatchAccountVO> lhsiFuture = Lists.newArrayList();
		
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> dataList = this.nativeQuery(sql, Lists.newArrayList());
		
		for (Map<String, Object> map : dataList) {
			String businessType = (String)map.get("businessType");
			BigInteger lever = (BigInteger)map.get("lever");
			BigDecimal traderMoney = (BigDecimal)map.get("traderMoney");
			BigInteger number = (BigInteger)map.get("number");
			FutureMatchAccountVO futureMatchAccountVO = new FutureMatchAccountVO();
			futureMatchAccountVO.setBusinessType(Integer.valueOf(businessType));
			futureMatchAccountVO.setLever(lever.intValue());
			futureMatchAccountVO.setTradeMoney(traderMoney.doubleValue());
			futureMatchAccountVO.setNumber(number.intValue());
			if("1".equals(businessType)){
				goodsFuture.add(futureMatchAccountVO);
			}else if("2".equals(businessType)){
				internationalFuture.add(futureMatchAccountVO);
			}else if("3".equals(businessType)){
				a50Future.add(futureMatchAccountVO);
			}else if("4".equals(businessType)){
				oilFuture.add(futureMatchAccountVO);
			}else if("5".equals(businessType)){
				hsiFuture.add(futureMatchAccountVO);
			}else if("6".equals(businessType)){
				lhsiFuture.add(futureMatchAccountVO);
			}
		}
		Map<String, List<FutureMatchAccountVO>> map = new HashMap<String, List<FutureMatchAccountVO>>();
		map.put("ifu", internationalFuture);
		map.put("gfu", goodsFuture);
		map.put("afu", a50Future);
		map.put("ofu", oilFuture);
		map.put("hfu", hsiFuture);
		map.put("lhfu", lhsiFuture);
		//System.out.println("11");
		return map;
	}



	
	@Override
	public PageInfo<Object> getAssignFutureAccountList(
			EasyUiPageInfo easyUiPage, Map<String, Object> searchParams) {
		
		if (StringUtil.isBlank(easyUiPage.getOrder())) {
			easyUiPage.setOrder("desc");
			easyUiPage.setSort("assignTime");
		} else if ("assignTimeStr".equals(easyUiPage.getSort())) {
			easyUiPage.setSort("assignTime");
		} else if ("createTimeStr".equals(easyUiPage.getSort())) {
			easyUiPage.setSort("createTime");
		}
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("select")
				  	.append(" tb1.business_type as businessType,")
					.append(" tb1.account,")
					.append(" tb1.password,")
					.append(" tb1.lever,")
					.append(" tb1.trade_money as tradeMoney,")
					.append(" tb1.assign_time as assignTime,")
					.append(" tb3.mobile,")
					.append(" tb1.create_time as createTime,")
					.append(" tb4.tname as username")
					.append(" from ")
					.append(" f_future_match_account tb1")
					.append(" left join f_simple_ftse_user_trade tb2 on tb1.tid = tb2.id")
					.append(" left join w_user tb3 on tb2.uid = tb3.id")
					.append(" left join w_user_verified tb4 on tb2.uid = tb4.uid where tb1.deleted=0 and tb1.is_use=1");
		
		List<Object> params = Lists.newArrayList();
		MultiListParam multilistParam = new MultiListParam(easyUiPage,searchParams, params, sqlBuilder.toString());
		return multiListPageQuery(multilistParam, FutureAccountAssignVO.class);

	}

    @Override
    public Boolean isUse(String name) {
        Boolean b = false;
        String sql = "select f.account from f_future_match_account f where f.is_use =0";
        List list = this.nativeQuery(sql, null);
        List<String> accountList = new ArrayList<String>();
        for(Object obj : list) {
            HashMap map = (HashMap) obj;
            accountList.add((String) map.get("account"));
        }
        if (accountList.contains(name)) {
            b = true;
        }
        return b;
    }

    @Override
    public List<Integer> getLever() {
        String sql ="select f.lever from f_future_match_account f group by f.lever";
        List list = this.nativeQuery(sql, null);
        List<Integer> leverList = new ArrayList<Integer>();
        for(Object obj : list) {
            HashMap map = (HashMap) obj;
            leverList.add((Integer) map.get("lever"));
        }
        return leverList;
    }

	/**
	 * �õ�һ��δ������˺�
	 * @param type
	 * @param lever
	 * @param money
	 * @return
	 */
	public FutureMatchAccount getOne(Integer type,Integer lever,Double money){
		List<FutureMatchAccount> futureMatchAccounts =this.getEntityDao().findByType(type,lever,money);
		if(futureMatchAccounts.size()==0||futureMatchAccounts.isEmpty()){
			return null;
		}else {
			return futureMatchAccounts.get(0);
		}

	}
}
