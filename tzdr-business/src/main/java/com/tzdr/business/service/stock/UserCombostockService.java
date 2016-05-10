package com.tzdr.business.service.stock;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.tzdr.business.exception.UserCombostockException;
import com.tzdr.business.service.userTrade.UserTradeService;
import com.tzdr.common.api.hundsun.data.Combostock;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.exception.EmailExceptionHandler;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.Exceptions;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.MultiListParam;
import com.tzdr.domain.dao.stock.UserCombostockDao;
import com.tzdr.domain.vo.UserRestrictStockVo;
import com.tzdr.domain.web.entity.UserCombostock;

/**
 * 
 * @zhouchen
 * 2014年12月26日
 */
@Service
@Transactional
public class UserCombostockService extends BaseServiceImpl<UserCombostock,UserCombostockDao> {
	public static final Logger logger = LoggerFactory.getLogger(UserCombostockService.class);
	
	@Autowired
	private UserTradeService  userTradeService;
	
	public void  batchSaveCombostock(List<Object[]> list,long initDate){
		try 
		{
			String sql = "INSERT INTO `w_user_combostock` (`save_time`,`id`, `begin_amount`, `buy_amount`, `combine_id`, `cost_balance`, `current_amount`, "
					+ "`enable_amount`, `exchange_type`, `fund_account`, `init_date`, `invest_way`, `market_value`, `pupil_flag`, `seat_no`, "
					+ "`sell_amount`, `stock_account`, `stock_code`)"
					+ " VALUES (?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			batchSave(sql, 1000, list);
		} catch (Exception e) {
			logger.error(Exceptions.getStackTraceAsString(e));
			// 回滚数据,即删除当天新增数据
			deleteByInitDate(initDate);
			throw new UserCombostockException("query.user.combostock.fail",null);
		}
	}

	/**
	 * 查询具体某一天的 历史持仓
	 * @param initdate
	 * @return
	 */
	public List<UserCombostock> findByInitDate(long initdate){
		return getEntityDao().findByInitDate(initdate);
	}
	
	/**
	 * 删除某天的持仓
	 * @param realDate
	 */
	public void deleteByInitDate(long initDate){
		String  sql = " delete from w_user_combostock where init_date=?";
		List<Object> params = Lists.newArrayList();
		params.add(initDate);
		nativeUpdate(sql,params);
	}
	
	/**
	 * 删除所有的持仓数据
	 */
	public void deleteAll(){
		String  sql = "delete from w_user_combostock";
		List<Object> params = Lists.newArrayList();
		nativeUpdate(sql,params);
	}
	
	public void  saveCombostocks(){
		Long  initDate = NumberUtils.toLong(Dates.format(new Date(),Dates.CHINESE_DATE_FORMAT_LONG));
		
		deleteAll();
		
		List<Combostock> combostocks = userTradeService.getAllCombostocks();
		if (CollectionUtils.isEmpty(combostocks)){
			return;
		}
		try{
			List<Object[]> list= Lists.newArrayList();
			for (Combostock combostock : combostocks){
				UserCombostock  destEntity = new UserCombostock();
				BeanUtils.copyProperties(destEntity, combostock);		
				list.add(destEntity.getObjectArray());
			}
			
			if (CollectionUtils.isEmpty(list)){
				return;
			}
			this.batchSaveCombostock(list, initDate);
		} catch (Exception e) {
			logger.error("当日持仓查询失败！"+Exceptions.getStackTraceAsString(e));
			EmailExceptionHandler.getInstance().HandleException(e, "当日持仓查询失败！", this.getClass().getName()+".saveRealCombostocks");
		}
	}
	
	
	public PageInfo<Object> queryDatas(EasyUiPageInfo easyUiPage,
			Map<String, Object> searchParams) {
		PageInfo<Object> pageInfo = new PageInfo<Object>(easyUiPage.getRows(),easyUiPage.getPage());
		String sql = "SELECT uc.id,usr.mobile, veri.tname username, trade.account, trade.group_id groupId, "
				+ " stock.`code`, stock.`name` stockName, stock.effective_date effectiveDate,uc.save_time positionTime FROM w_user_combostock uc,"
				+ " w_stock stock, ( SELECT combine_id, uid, group_id, account FROM w_user_trade where status=1 GROUP BY combine_id, group_id ) trade, "
				+ " w_user_verified veri, w_user usr WHERE uc.stock_code = stock.`code` AND stock.type = 2  and stock.deleted = 0"
				+ " AND trade.combine_id = uc.combine_id AND veri.uid = trade.uid AND usr.id = trade.uid  ";
		MultiListParam multilistParam = new MultiListParam(easyUiPage,
				searchParams,null, sql);
		pageInfo = multiListPageQuery(multilistParam, UserRestrictStockVo.class);
		return pageInfo;
	}
	
}
