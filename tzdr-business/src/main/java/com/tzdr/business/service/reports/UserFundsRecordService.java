package com.tzdr.business.service.reports;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.tzdr.business.exception.UserFundRrcordException;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.exception.EmailExceptionHandler;
import com.tzdr.common.utils.BigDecimalUtils;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.ExcelUtils;
import com.tzdr.common.utils.Exceptions;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.dao.reports.UserFundsRecordDao;
import com.tzdr.domain.web.entity.reports.UserFundsRecord;

/**
 * 
 * @zhouchen
 * 2014年12月26日
 */
@Service
@Transactional
public class UserFundsRecordService extends BaseServiceImpl<UserFundsRecord,UserFundsRecordDao> {
	public static final Logger logger = LoggerFactory.getLogger(UserFundsRecordService.class);
	
	
	/**
	 * 查询前一日 用户资金记录
	 */
	public List<UserFundsRecord> queryUserFunds(){
		Map<String,UserFundsRecord> listMap = new HashMap<String,UserFundsRecord>();
		List<UserFundsRecord> list = new ArrayList<UserFundsRecord>();
		Long twoDaysAgoTime = Dates.getDatebyDaynum(-2);
		Long beginTime = Dates.getDatebyDaynum(-1);
		Long endTime = Dates.getLastLongDayNum(-1);
		
		// 用户基本信息
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("SELECT IFNULL(u.mobile,'') as mobile, \n");
		sqlBuf.append("	IFNULL(v.tname,'') as realName, \n");
		sqlBuf.append("	IFNULL(u.id,'') as uid \n");
		sqlBuf.append("FROM w_user u  \n");
		sqlBuf.append("	left JOIN w_user_verified v ON u.id = v.uid\n");
		sqlBuf.append("WHERE u.user_type NOT IN(-1,-2,-3)");
		List<Map<String,Object>> userList = this.getEntityDao().queryMapBySql(sqlBuf.toString(), null);
		List<UserFundsRecord> userListRS = TypeConvert.objectsToListDataByMethod(userList,UserFundsRecord.class);
		if(userListRS != null){
			for(UserFundsRecord obj : userListRS){
				listMap.put(obj.getUid(), obj);
			}
			userList = null;
			userListRS = null;
		}
		logger.info("------------------用户资金记录任务--------query用户基本信息-------------------");
		//总余额(最近平台余额)
		sqlBuf.setLength(0);
		sqlBuf.append("SELECT cc.uid,\n");
		sqlBuf.append("		(SELECT ff.amount FROM w_user_fund ff WHERE ff.uptime=max(cc.uptime) AND ff.uid=cc.uid LIMIT 0,1) AS platBalance \n");
		sqlBuf.append("FROM w_user_fund cc \n");
		sqlBuf.append("WHERE cc.uptime <= ?\n");
		sqlBuf.append("group by cc.uid");
		List<Map<String,Object>> ccList = this.getEntityDao().queryMapBySql(sqlBuf.toString(), endTime);
		List<UserFundsRecord> ccListRS = TypeConvert.objectsToListDataByMethod(ccList,UserFundsRecord.class);
		if(ccListRS != null){
			for(UserFundsRecord obj : ccListRS){
				if(listMap.get(obj.getUid())!=null)listMap.get(obj.getUid()).setPlatBalance(obj.getPlatBalance());
			}
			ccList = null;
			ccListRS = null;
		}
		logger.info("------------------用户资金记录任务--------query最近平台余额-------------------");
		//(配资金额)公司出的钱,配资保证金(含追加保证金)
		sqlBuf.setLength(0);
		sqlBuf.append("SELECT t.uid,\n");
		sqlBuf.append("			sum(t.money) AS amountCapital,\n");
		sqlBuf.append("			sum(t.lever_money+t.append_lever_money-ifnull(t.voucher_actual_money,0)) AS balanceCapitalMargin \n");
		sqlBuf.append("		FROM w_user_trade t  \n");
		sqlBuf.append("		WHERE ((t.`status`=1 OR t.`status`=0)  and t.addtime <=?) \n");
		sqlBuf.append("			OR (t.`status`=2 AND t.endtime >=? AND t.addtime <=?) \n");
		sqlBuf.append("		GROUP BY t.uid");
		List<Map<String,Object>> tList = this.getEntityDao().queryMapBySql(sqlBuf.toString(), endTime,endTime,endTime);
		List<UserFundsRecord> tListRS = TypeConvert.objectsToListDataByMethod(tList,UserFundsRecord.class);
		if(tListRS != null){
			for(UserFundsRecord obj : tListRS){
				if(listMap.get(obj.getUid())!=null)listMap.get(obj.getUid()).setAmountCapital(obj.getAmountCapital());
				if(listMap.get(obj.getUid())!=null)listMap.get(obj.getUid()).setBalanceCapitalMargin(obj.getBalanceCapitalMargin());
			}
			tList = null;
			tListRS = null;
		}
		logger.info("------------------用户资金记录任务--------query配资金额+保证金-------------------");
		
		sqlBuf.setLength(0);
		sqlBuf.append("SELECT t.uid,\n");
		sqlBuf.append("			(sum(t.money)*max(apply_exchange_rate)) AS amountCapital,\n");
		sqlBuf.append("			sum(t.lever_money+t.append_lever_money) AS balanceCapitalMargin \n");
		sqlBuf.append("		FROM hk_user_trade t  \n");
		sqlBuf.append("		WHERE ((t.`status`=1 OR t.`status`=0)  and t.addtime <=?) \n");
		sqlBuf.append("			OR (t.`status`=2 AND t.endtime >=? AND t.addtime <=?) \n");
		sqlBuf.append("		GROUP BY t.uid");
		tList = this.getEntityDao().queryMapBySql(sqlBuf.toString(), endTime,endTime,endTime);
		tListRS = TypeConvert.objectsToListDataByMethod(tList,UserFundsRecord.class);
		if(tListRS != null){
			for(UserFundsRecord obj : tListRS){
				String uid = obj.getUid();
				if(listMap.get(uid)!=null)
				{
					//港股配资金额
					listMap.get(uid).setAmountCapital(BigDecimalUtils.addRound(listMap.get(uid).getAmountCapital(),obj.getAmountCapital()));
				}
				if(listMap.get(uid)!=null){
					listMap.get(uid).setBalanceCapitalMargin(BigDecimalUtils.addRound(listMap.get(uid).getBalanceCapitalMargin(),obj.getBalanceCapitalMargin()));
				}
			}
			tList = null;
			tListRS = null;
		}
		logger.info("------------------用户资金记录任务--------query港股配资金额+保证金-------------------");
		
		/*
		 *  -- 交易金额:充值+系统调账+系统冲账（type=1,3,4）
		 *  -- 利息支出(type=11)
		 *  -- 扣取管理费(type=12)
		 *  -- 佣金收入(type=13)
		 *  --  利润提取(type=16)
		 *  --  收入卡卷 活动赠送(type=19)
		 *  --  收入其他 补偿收入(type=21)
		 *  -- 抵扣劵收入(type=24)
		 *  -- 配资管理费撤回(type=25)
		 *  -- 配资利息撤回（type=26)
		 */
		sqlBuf.setLength(0);
		sqlBuf.append("select f.uid, \n");
		sqlBuf.append("	IFNULL(sum(if(f.type in(1,3,4),f.money,0)),0) as incomeRecharge,\n");
		sqlBuf.append("	ABS(IFNULL(sum(if(f.type=11,f.money,0)),0)) as interestFee,\n");
		sqlBuf.append("	ABS(IFNULL(sum(if(f.type=12,f.money,0)),0)) as managementFee,\n");
		sqlBuf.append("	ABS(IFNULL(sum(if(f.type=13,f.money,0)),0)) as incomeRebate, \n");
		sqlBuf.append("	ABS(IFNULL(sum(if(f.type=16,f.money,0)),0))  as profit, \n");
		sqlBuf.append("	ABS(IFNULL(sum(if(f.type=19,f.money,0)),0))  as cardCapitalMargin, \n");
		sqlBuf.append("	ABS(IFNULL(sum(if(f.type=21,f.money,0)),0))  as incomeOther, \n");
		sqlBuf.append("	ABS(IFNULL(sum(if(f.type=24,f.money,0)),0)) as deductionFee, \n");
		sqlBuf.append("	ABS(IFNULL(sum(if(f.type=25,f.money,0)),0)) as revokeManagerMoney, \n");
		sqlBuf.append("	ROUND(ABS(IFNULL(sum(if(f.type=26,f.money,0)),0)),2) as revokeInterest \n");
		sqlBuf.append("from w_user_fund f  \n");
		sqlBuf.append("where f.pay_status=1 AND f.uptime >=? and  f.uptime <=? \n");
		sqlBuf.append("group by f.uid");
		List<Map<String,Object>> wufList = this.getEntityDao().queryMapBySql(sqlBuf.toString(), beginTime,endTime);
		List<UserFundsRecord> wufListRS = TypeConvert.objectsToListDataByMethod(wufList,UserFundsRecord.class);
		if(wufListRS != null){
			for(UserFundsRecord obj : wufListRS){
				if(listMap.get(obj.getUid())!=null)listMap.get(obj.getUid()).setIncomeRecharge(obj.getIncomeRecharge());
				if(listMap.get(obj.getUid())!=null)listMap.get(obj.getUid()).setInterestFee(obj.getInterestFee());
				if(listMap.get(obj.getUid())!=null)listMap.get(obj.getUid()).setManagementFee(obj.getManagementFee());
				if(listMap.get(obj.getUid())!=null)listMap.get(obj.getUid()).setIncomeRebate(obj.getIncomeRebate());
				if(listMap.get(obj.getUid())!=null)listMap.get(obj.getUid()).setProfit(obj.getProfit());
				if(listMap.get(obj.getUid())!=null)listMap.get(obj.getUid()).setCardCapitalMargin(obj.getCardCapitalMargin());
				if(listMap.get(obj.getUid())!=null)listMap.get(obj.getUid()).setIncomeOther(obj.getIncomeOther());
				if(listMap.get(obj.getUid())!=null)listMap.get(obj.getUid()).setDeductionFee(obj.getDeductionFee());
				if(listMap.get(obj.getUid())!=null)listMap.get(obj.getUid()).setRevokeManagerMoney(obj.getRevokeManagerMoney());
				if(listMap.get(obj.getUid())!=null)listMap.get(obj.getUid()).setRevokeInterest(obj.getRevokeInterest());
				if(listMap.get(obj.getUid())!=null)listMap.get(obj.getUid()).setActualFee(obj.getInterestFee()-obj.getDeductionFee()-obj.getRevokeInterest());
			}
			wufList = null;
			wufListRS = null;
		}
		
		logger.info("------------------用户资金记录任务--------股票抵扣利息金额-------------------");
		sqlBuf.setLength(0);
		sqlBuf.append("SELECT tu.uid, IFNULL(sum(tu.discount_actual_money), 0) as discountActualMoney\n");
		sqlBuf.append("FROM w_user_trade tu \n");
		sqlBuf.append("WHERE tu.addtime >= ? and tu.addtime <= ? \n");
		sqlBuf.append("GROUP BY tu.uid");
		List<Map<String,Object>> dsList = this.getEntityDao().queryMapBySql(sqlBuf.toString(), beginTime,endTime);
		List<UserFundsRecord> dsListRS = TypeConvert.objectsToListDataByMethod(dsList,UserFundsRecord.class);
		if(dsListRS != null){
			for(UserFundsRecord obj : dsListRS){
				UserFundsRecord tmp = listMap.get(obj.getUid());
				
				if(tmp!=null)listMap.get(obj.getUid()).setInterestFee(obj.getDiscountActualMoney().doubleValue()+tmp.getInterestFee());
				if(tmp!=null)listMap.get(obj.getUid()).setDeductionFee(obj.getDiscountActualMoney().doubleValue()+tmp.getDeductionFee());
			}
			dsList = null;
			dsListRS = null;
		}
		logger.info("------------------用户资金记录任务--------股票抵扣利息金额-------------------");
		
		logger.info("------------------用户资金记录任务--------股票保证金卡卷金额-------------------");
		sqlBuf.setLength(0);
		sqlBuf.append("SELECT tu.uid, sum(tu.deduction_lever_money) as cardCapitalMargin \n");
		sqlBuf.append("FROM w_user_trade tu \n");
		sqlBuf.append("WHERE  tu.status=2 and tu.endtime >= ? and tu.endtime <= ? \n");
		sqlBuf.append("GROUP BY tu.uid");
		List<Map<String,Object>> cardList = this.getEntityDao().queryMapBySql(sqlBuf.toString(), beginTime,endTime);
		List<UserFundsRecord> cardListRS = TypeConvert.objectsToListDataByMethod(cardList,UserFundsRecord.class);
		if(cardListRS != null){
			for(UserFundsRecord obj : cardListRS){
				UserFundsRecord tmp = listMap.get(obj.getUid());
				if(tmp!=null)listMap.get(obj.getUid()).setCardCapitalMargin(obj.getCardCapitalMargin()+tmp.getCardCapitalMargin());
			}
			cardList = null;
			cardListRS = null;
		}
		logger.info("------------------用户资金记录任务--------股票保证金卡卷金额-------------------");
		
		
		
		logger.info("------------------用户资金记录任务--------query用户资金明细-------------------");
		//利润提取
		sqlBuf.setLength(0);
		sqlBuf.append("SELECT tu.uid, sum(tu.accrual) as profit\n");
		sqlBuf.append("FROM w_user_trade tu \n");
		sqlBuf.append("WHERE tu.`status`=2 and tu.endtime >=? and tu.endtime<=? \n");
		sqlBuf.append("GROUP BY tu.uid");
		List<Map<String,Object>> rtList = this.getEntityDao().queryMapBySql(sqlBuf.toString(), beginTime,endTime);
		List<UserFundsRecord> rtListRS = TypeConvert.objectsToListDataByMethod(rtList,UserFundsRecord.class);
		if(rtListRS != null){
			for(UserFundsRecord obj : rtListRS){
				UserFundsRecord tmp = listMap.get(obj.getUid());
				if(tmp!=null)listMap.get(obj.getUid()).setProfit(obj.getProfit()+tmp.getProfit());
			}
			rtList = null;
			rtListRS = null;
		}
		logger.info("------------------用户资金记录任务--------query利润提取-------------------");
		
		logger.info("------------------用户资金记录任务-------港股实际盈亏-------------------");
		sqlBuf.setLength(0);
		sqlBuf.append("SELECT hk.uid, sum(hk.accrual) as profit\n");
		sqlBuf.append("FROM hk_user_trade hk \n");
		sqlBuf.append("WHERE hk.`status`=2 and hk.endtime >=? and hk.endtime<=? \n");
		sqlBuf.append("GROUP BY hk.uid");
		List<Map<String,Object>> hkrtList = this.getEntityDao().queryMapBySql(sqlBuf.toString(), beginTime,endTime);
		List<UserFundsRecord> hkrtListRS = TypeConvert.objectsToListDataByMethod(hkrtList,UserFundsRecord.class);
		if(hkrtListRS != null){
			for(UserFundsRecord obj : hkrtListRS){
				UserFundsRecord tmp = listMap.get(obj.getUid());
				if(tmp!=null)listMap.get(obj.getUid()).setProfit(obj.getProfit()+tmp.getProfit());
			}
			hkrtList = null;
			hkrtListRS = null;
		}
		logger.info("------------------用户资金记录任务--------港股实际盈亏-------------------");
		
		//补仓欠费(type=27)
		sqlBuf.setLength(0);
		sqlBuf.append("SELECT fc.uid, sum(-fc.money) as coverMoney \n");
		sqlBuf.append("FROM w_user_fund fc,w_user_trade tr  \n");
		sqlBuf.append("WHERE fc.`no`=tr.id AND tr.`status`=1 AND fc.type=27 AND fc.type_status=0  \n");
		sqlBuf.append("GROUP BY fc.uid");
		List<Map<String,Object>> coverList = this.getEntityDao().queryMapBySql(sqlBuf.toString(),null);
		List<UserFundsRecord> coverListRS = TypeConvert.objectsToListDataByMethod(coverList,UserFundsRecord.class);
		if(coverListRS != null){
			for(UserFundsRecord obj : coverListRS){
				if(listMap.get(obj.getUid())!=null)listMap.get(obj.getUid()).setCoverMoney(obj.getCoverMoney());
			}
			coverList = null;
			coverListRS = null;
		}
		logger.info("------------------用户资金记录任务--------query补仓欠费-------------------");
		// -- 提现金额(status=31提现成功),提现金额(status=21提现处理中)
		sqlBuf.setLength(0);
		sqlBuf.append("SELECT d.uid,\n");
		sqlBuf.append("	IFNULL(sum(if(d.status=31,d.money,0)),0) as drawFee,\n");
		sqlBuf.append("	IFNULL(sum(if(d.status=21,d.money,0)),0) as drawingFee\n");
		sqlBuf.append("FROM  w_draw_list d  \n");
		sqlBuf.append("where (d.status=31 AND d.oktime >=? and d.oktime <=?)\n");
		sqlBuf.append("	 OR (d.status=21 AND  d.addtime<=? )\n");
		sqlBuf.append("GROUP BY d.uid");
		List<Map<String,Object>> dlList = this.getEntityDao().queryMapBySql(sqlBuf.toString(),beginTime,endTime,endTime);
		List<UserFundsRecord> dlListRS = TypeConvert.objectsToListDataByMethod(dlList,UserFundsRecord.class);
		if(dlListRS != null){
			for(UserFundsRecord obj : dlListRS){
				if(listMap.get(obj.getUid())!=null)listMap.get(obj.getUid()).setDrawFee(obj.getDrawFee());
				if(listMap.get(obj.getUid())!=null)listMap.get(obj.getUid()).setDrawingFee(obj.getDrawingFee());
			}
			dlList = null;
			dlListRS = null;
		}
		logger.info("------------------用户资金记录任务--------query体现金额-------------------");
		// --账户资产(前一次)
		sqlBuf.setLength(0);
		sqlBuf.append("SELECT am.uid,\n");
		sqlBuf.append("		IFNULL(am.all_money,0) AS lastdayBalance\n");
		sqlBuf.append("FROM w_userfund_record am WHERE am.fund_date =?");
		List<Map<String,Object>> amList = this.getEntityDao().queryMapBySql(sqlBuf.toString(),twoDaysAgoTime);
		List<UserFundsRecord> amListRS = TypeConvert.objectsToListDataByMethod(amList,UserFundsRecord.class);
		if(amListRS != null){
			for(UserFundsRecord obj : amListRS){
				if(listMap.get(obj.getUid())!=null)listMap.get(obj.getUid()).setLastdayBalance(obj.getLastdayBalance());
			}
			amList = null;
			amListRS = null;
		}
		
		logger.info("------------------用户资金记录任务--------query合买收益分成-------------------");
		// --合买收益分成(前一次)
		sqlBuf.setLength(0);
		sqlBuf.append("SELECT ut.uid, ROUND(SUM(profit_money), 2) as profitMoney \n");
		sqlBuf.append("FROM w_user_trade ut, w_together_trade tt \n");
		sqlBuf.append("WHERE ut.id = tt.tid AND ut.endtime > ? AND ut.endtime < ? \n");
		sqlBuf.append("GROUP BY ut.uid");
		List<Map<String,Object>> profitList = this.getEntityDao().queryMapBySql(sqlBuf.toString(),beginTime,endTime);
		List<UserFundsRecord> profitListRS = TypeConvert.objectsToListDataByMethod(profitList,UserFundsRecord.class);
		if(profitListRS != null){
			for(UserFundsRecord obj : profitListRS){
				if(listMap.get(obj.getUid())!=null)listMap.get(obj.getUid()).setProfitMoney(obj.getProfitMoney());
			}
			profitList = null;
			profitListRS = null;
		}
		logger.info("------------------用户资金记录任务--------query账户资产（头一天）-------------------");
		if(listMap.keySet().size() > 0){
			double allMoney = 0;
			UserFundsRecord obj = null;
			for(String key : listMap.keySet()){
				obj = listMap.get(key);
				allMoney += obj.getLastdayBalance()+obj.getIncomeRecharge();
				allMoney += obj.getIncomeRebate()+obj.getProfit();
				allMoney += obj.getDeductionFee()+obj.getRevokeInterest()+obj.getRevokeManagerMoney();
				allMoney =  allMoney - obj.getManagementFee()-obj.getInterestFee()-obj.getDrawFee()-obj.getCoverMoney();
				obj.setAllMoney(BigDecimalUtils.round(allMoney, 2));
				list.add(obj);
				allMoney = 0;
			}
		}
		return list;
	}
	
	/**
	 * 创建用户资金记录
	 */
	public void createUserFundRecords(){
		List<UserFundsRecord>  fundsRecords = queryUserFunds();
		logger.info("------------------用户资金记录任务--------query all end-------------------");
		if (CollectionUtils.isEmpty(fundsRecords)){
			return;
		}
		List<Object[]> list= Lists.newArrayList();
		for (UserFundsRecord userFundsRecord:fundsRecords){
			list.add(userFundsRecord.getObjectArray());
		}
		batchSaveRecords(list);
	}
	
	
	public void  batchSaveRecords(List<Object[]> list){
		if (CollectionUtils.isEmpty(list)){
			return;
		}
		logger.info("------------------用户资金记录任务--------delete last 1 day-------------------");
		deleteByFundDate(Dates.getDatebyDaynum(-1));
		try 
		{
			logger.info("------------------用户资金记录任务--------insert begin-------------------");
			String sql = "INSERT INTO `w_userfund_record` ( `id`, `all_money`, `amount_capital`, `balance_capital_margin`, `card_capital_margin`, `draw_fee`, `drawing_fee`, `fund_date`, `income_other`, `income_rebate`, `income_recharge`, `interest_fee`, `lastday_balance`, `management_fee`, `mobile`, `plat_balance`, `profit`, `real_name`,`uid`,`deduction_fee`,`actual_fee`, `revoke_manager_money`,`revoke_interest`,`cover_money`,`profit_money`) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			batchSave(sql, 1000, list);
			logger.info("------------------用户资金记录任务--------insert end-------------------");
		} catch (Exception e) {
			logger.error(Exceptions.getStackTraceAsString(e));
			// 回滚数据,即删除当天新增数据
			deleteByFundDate(Dates.getDatebyDaynum(-1));
			 //发送异常邮件
			 EmailExceptionHandler.getInstance().HandleException(e, "获取用户资金记录定时任务异常:", this.getClass().getName()+" : batchSaveRecords ");
			throw new UserFundRrcordException("save.userfund.records.fail",null);
		}
	}
	
	
	/**
	 * 删除某天的历史记录
	 * @param realDate
	 */
	public void deleteByFundDate(long realDate){
		String  sql = " delete from w_userfund_record where fund_date=?";
		List<Object> params = Lists.newArrayList();
		params.add(realDate);
		nativeUpdate(sql,params);
	}
	
	
	public void testCreateDatas(){
		//读取excl数据
		 File file = new File("D:/投资达人客户资金表2015.4.xlsx");
		  BufferedInputStream inputStream = null;
		  List<Object[]> data = Lists.newArrayList();

		  try {
			  inputStream = new BufferedInputStream(new FileInputStream(file));
			  Workbook workbook =  ExcelUtils.create(inputStream);
			  Sheet  sheet = workbook.getSheetAt(8);
			 
			  for (int i=8;i<1084;i++){
				  Row tempRow = sheet.getRow(i);  
				  int  cells = tempRow.getPhysicalNumberOfCells();
				  String mobile = null;
				  double lastBalance = 0.0;
				  double allMoney=0.0;
				  for (int j=0;j<cells;j++){
					  Cell cell = tempRow.getCell(j);					
					 if (j==2){
						 mobile=String.format("%.0f", cell.getNumericCellValue());
					 }
					 
					 if (j==4){
						 lastBalance=cell.getNumericCellValue(); 
					 }
					 
					 if (j==12){
						 allMoney=cell.getNumericCellValue(); 
					 }
				  }
				  
				  data.add(new Object[]{mobile,lastBalance,allMoney});
			  }
			  System.out.println(data.size());
		  }catch(Exception exception){
			  exception.printStackTrace();
		  }finally{
			  if (!ObjectUtil.equals(null, inputStream)){
				  try {
					inputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			  }
		  }
		  List<UserFundsRecord> userfunds = this.getAll();
		  if (CollectionUtils.isEmpty(data)
				  || CollectionUtils.isEmpty(userfunds)){
			  return;
		  }
		  
		
		  for (Object[] obj:data){
			  String mobile=  String.valueOf(obj[0]);
			  if (StringUtil.isBlank(mobile)){
				  continue;
			  }
			  for (UserFundsRecord fundsRecord:userfunds){
				  if (StringUtil.isBlank(fundsRecord.getMobile())){
					  continue;
				  }
				  if (StringUtil.equals(mobile,fundsRecord.getMobile())){
					  fundsRecord.setLastdayBalance(NumberUtils.toDouble(String.valueOf(obj[1])));
					  fundsRecord.setAllMoney(NumberUtils.toDouble(String.valueOf(obj[2])));
					  this.update(fundsRecord);
				  }
			  }
		  }
	}
}
