package com.tzdr.business.service.breakStore;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.MultiListParam;
import com.tzdr.domain.dao.breakStore.BreakStoreDao;
import com.tzdr.domain.vo.BreakStoreVo;
import com.tzdr.domain.web.entity.UserTrade;

/**
 * 穿仓Service层
 * @ClassName BreakStoreService
 * @author L.Y
 * @email liuyuan@peigubao.com
 * @date 2015年7月6日
 */
@Service
@Transactional
public class BreakStoreService extends BaseServiceImpl<UserTrade, BreakStoreDao> {
	
	/**
	 * 查询穿仓列表统计数据
	 * @MethodName queryListNew
	 * @author L.Y
	 * @date 2015年7月7日
	 * @param easyUiPage
	 * @param searchParams
	 * @return
	 */
	public PageInfo<Object> queryListNew(EasyUiPageInfo  easyUiPage,Map<String, Object> searchParams) {
		PageInfo<Object> pageInfo = new PageInfo<Object>(easyUiPage.getRows(), easyUiPage.getPage());
		
		String sql = "SELECT "
						+ "* "
					 + "FROM ("
					 	+ "SELECT t.activity_type activityType, "
							+ "u.mobile, v.tname, "
							+ "t.group_id programNo, "
							+ "a.account_name accountName, "
							+ "ROUND((t.money - t.finished_money), 2) accrual, "
							+ "ROUND(IFNULL(ABS(f.amount),0), 2) amount, "
							+ "ROUND(IF(ISNULL(uf1.amount), t.money - t.finished_money, t.money - t.finished_money + uf1.amount), 2) paid, "
							+ "ROUND(IFNULL(u.unpayment,0), 2) unpayment, "
							+ "ROUND(u.avl_bal, 2) avlBal, t.endtime endTime "
						+ "FROM "
							+ "w_user u " 
						+ "INNER JOIN (SELECT ut.activity_type, "
								+ "ut.uid, "
								+ "ut.group_id, "
								+ "ut.endtime, "
								+ "ut.`status`, "
								+ "ut.account_id, "
								+ "SUM(ut.money) money, "
								+ "SUM(ut.finished_money) finished_money "
							+ "FROM "
								+ "w_user_trade ut "
							+ "GROUP BY "
								+ "ut.group_id "
						+ ") t ON t.uid = u.id AND t.status=2 "
						+ "INNER JOIN (SELECT "
										+ "wuf.lid, "
										+ "wuf.type "
									  + "FROM "
									  	+ "w_user_fund wuf "
									  + "WHERE "
										+ "wuf.type = 18 "
									  //+ "AND wuf.pay_status=0 "
									  + "GROUP BY wuf.lid) uf ON uf.lid=t.group_id AND uf.type=18 " // IN(18, 27) AND uf.pay_status=0
						+ "LEFT JOIN w_user_verified v ON v.uid=u.id "
						+ "LEFT JOIN w_account a ON a.id=t.account_id "
						+ "LEFT JOIN w_user_fund f ON f.lid=uf.lid AND f.type=27 AND f.pay_status=0 "
						+ "LEFT JOIN w_user_fund uf1 ON uf1.lid=uf.lid AND uf1.pay_status=0 AND uf1.type=18 "
						+ "LEFT JOIN (SELECT "
										+ "f2.lid, ABS(SUM(f2.amount)) unpayment "
									 + "FROM "
											+ "w_user_fund f2 "
									 + "WHERE "
									 	+ "f2.type IN(18, 27) "
									 + "AND f2.pay_status=0 "
									 + "GROUP BY f2.lid) u ON u.lid=uf.lid "
						+ "WHERE "
							+ "t.status=2 "
						//+ "AND uf.pay_status=0 "
						+ "AND uf.type=18) t "
					+ "GROUP BY t.programNo";
		
		//params  查询参数  依次 存入
		MultiListParam  multilistParam  = new MultiListParam(easyUiPage, searchParams, null, sql);
		pageInfo = multiListPageQuery(multilistParam, BreakStoreVo.class);
		return pageInfo;
	}
}
