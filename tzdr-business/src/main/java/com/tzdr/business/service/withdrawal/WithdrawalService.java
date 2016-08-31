package com.tzdr.business.service.withdrawal;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.tzdr.business.cms.exception.ParentAccountException;
import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.service.datamap.DataMapService;
import com.tzdr.business.service.drawMoney.DrawMoneyService;
import com.tzdr.business.service.drawMoneyData.DrawMoneyDataService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.dao.support.SearchFilter;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.BigDecimalUtils;
import com.tzdr.common.utils.DateUtils;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.EasyuiUtil;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.MultiListParam;
import com.tzdr.domain.cache.DataDicKeyConstants;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.dao.withdrawal.WithdrawalDao;
import com.tzdr.domain.entity.DataMap;
import com.tzdr.domain.vo.BibiTreatDrawListVo;
import com.tzdr.domain.vo.DrawListAuditVo;
import com.tzdr.domain.vo.DrawMoneyListVo;
import com.tzdr.domain.vo.DrawMoneyListVoNew;
import com.tzdr.domain.vo.PayeaseTreatDrawListVo;
import com.tzdr.domain.web.entity.DrawList;
import com.tzdr.domain.web.entity.DrawMoneyData;

/**
 * 提现记录
 * 
 * @zhouchen 2015年1月3日
 */
@Service
@Transactional
public class WithdrawalService extends BaseServiceImpl<DrawList, WithdrawalDao> {

	public static final Logger logger = LoggerFactory.getLogger(WithdrawalService.class);
	@Autowired
	private DrawMoneyService drawMoneyService;
	
	@Autowired
	private DrawMoneyDataService drawMoneyDataService;
	
	@Autowired
	private AuthService authService;

	@Autowired
	private DataMapService dataMapService;

	public PageInfo<Object> queryListNew(EasyUiPageInfo easyUiPage, Map<String, Object> searchParams) {
		PageInfo<Object> pageInfo = new PageInfo<Object>(easyUiPage.getRows(), easyUiPage.getPage());

		String sql = " SELECT first_audit_time firstAuditTime,dl.payment_channel paymentChannel,dl.below_line belowLine,dl.is_audit isAudit,dl.first_audit_user firstAuditUser, dl.uid, dl.id, us.mobile, dl.bank, dl.card, dl.`status`, dl.money, dl.addtime, dl.oktime, uv.tname, us.avl_bal balance, dl.update_time auditTime, dl.update_user auditUser,dl.source source FROM w_draw_list dl, w_user us, w_user_verified uv WHERE us.id = dl.uid AND uv.uid = us.id  ";
		// params 查询参数 依次 存入
		MultiListParam multilistParam = new MultiListParam(easyUiPage, searchParams, null, sql);
		pageInfo = multiListPageQuery(multilistParam, DrawMoneyListVoNew.class);
		return pageInfo;
	}

	public PageInfo<Object> queryList(EasyUiPageInfo easyUiPage, Map<String, Object> searchParams) {
		PageInfo<Object> pageInfo = new PageInfo<Object>(easyUiPage.getRows(), easyUiPage.getPage());

		String sql = " SELECT dl.source source,first_audit_time firstAuditTime,dl.payment_channel paymentChannel,dl.below_line belowLine,dl.is_audit isAudit,dl.first_audit_user firstAuditUser,dl.uid, dl.id, us.mobile, dl.bank, dl.card,dl.acc_address, dl.`status`, dl.money,dl.addtime, dl.oktime, uv.tname, us.avl_bal balance, dl.update_time auditTime,dl.update_user auditUser FROM  w_draw_list dl, w_user us, w_user_verified uv WHERE us.id = dl.uid AND uv.uid = us.id";
		// params 查询参数 依次 存入
		MultiListParam multilistParam = new MultiListParam(easyUiPage, searchParams, null, sql);
		pageInfo = multiListPageQuery(multilistParam, DrawMoneyListVo.class);
		return pageInfo;
	}

	public DrawMoneyListVo getTotalAuditList(Map<String, Object> searchParams) {

		String sql = " SELECT dl.source source,first_audit_time firstAuditTime,dl.payment_channel paymentChannel,dl.below_line belowLine,dl.is_audit isAudit,dl.first_audit_user firstAuditUser, dl.uid, dl.id, us.mobile, dl.bank, dl.card, dl.`status`, dl.money, dl.addtime, dl.oktime, uv.tname, us.avl_bal balance, dl.update_time auditTime, dl.update_user auditUser FROM  w_draw_list dl, w_user us, w_user_verified uv WHERE us.id = dl.uid AND uv.uid = us.id ";

		MultiListParam multilistParam = new MultiListParam(searchParams, sql, null);
		EasyuiUtil.bySearchFilter(multilistParam); // 增加搜索条件
		sql = "select ifnull(sum(money),0.0) from (" + multilistParam.getSql() + ") temp";
		Object totalMoney = nativeQueryOne(sql, multilistParam.getParams());
		DrawMoneyListVo totalData = new DrawMoneyListVo();
		double money = 0; // 合计
		if (totalMoney != null) {
			money = Double.valueOf(totalMoney.toString());
		}
		totalData.setMoney(BigDecimalUtils.round(money, 2));

		return totalData;
	}

	/**
	 * 获取指定用户的资金明细中收入或支出的总计
	 * 
	 * @param searchParams
	 *            过滤条件
	 * @param isPay
	 *            true：支出；false：收入
	 * @return
	 */
	public String getTotalMoneyByUserFund(Map<String, Object> searchParams, boolean isPay) {
		/*
		 * 慢sql 优化处理 String sql="SELECT *  FROM w_user_fund WHERE 1=1 ";
		 * if(isPay){ sql+="and money<0"; }else{ sql+="and money>0"; }
		 * MultiListParam multilistParam = new
		 * MultiListParam(searchParams,sql,null);
		 * EasyuiUtil.bySearchFilter(multilistParam); // 增加搜索条件 sql =
		 * "select ifnull(sum(money),0.0) from (" + multilistParam.getSql()+
		 * ") temp"; Object totalMoney = nativeQueryOne(sql,
		 * multilistParam.getParams()); DecimalFormat decimalFormat = new
		 * DecimalFormat("#,##0.00");//格式化设置 return
		 * decimalFormat.format(totalMoney);
		 */

		String sql = "SELECT ifnull(sum(money),0.0)  FROM w_user_fund WHERE 1=1 ";
		if (isPay) {
			sql += "and money<0";
		} else {
			sql += "and money>0";
		}

		List<Object> params = Lists.newArrayList();
		List<SearchFilter> searchFilters = SearchFilter.parse(searchParams);
		StringBuilder builder = new StringBuilder();
		for (SearchFilter searchFilter : searchFilters) {
			// logic operator
			switch (searchFilter.operator) {
			case EQ:
				builder.append(" and " + searchFilter.fieldName + " = ? ");
				params.add(searchFilter.value);
				break;
			case GTE:
				builder.append(" and " + searchFilter.fieldName + " >= ? ");
				params.add(searchFilter.value);
				break;
			case LTE:
				builder.append(" and " + searchFilter.fieldName + " <= ? ");
				params.add(searchFilter.value);
				break;
			}
		}

		Object totalMoney = nativeQueryOne(sql + builder.toString(), params);
		DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");// 格式化设置
		return decimalFormat.format(totalMoney);
	}

	/**
	 * 获取提现金额总计
	 * 
	 * @param searchParams
	 * @return
	 */
	public String getTotalMoney(Map<String, Object> searchParams) {
		String sql = " SELECT dl.is_audit isAudit,dl.payment_channel paymentChannel, dl.uid, dl.id, us.mobile, dl.bank, dl.card, dl.`status`, dl.money, dl.addtime, dl.oktime, uv.tname, us.avl_bal balance, dl.update_time auditTime, dl.update_user auditUser,dl.source FROM w_draw_list dl, w_user us, w_user_verified uv WHERE us.id = dl.uid AND uv.uid = us.id  ";
		MultiListParam multilistParam = new MultiListParam(searchParams, sql, null);
		EasyuiUtil.bySearchFilter(multilistParam); // 增加搜索条件
		sql = "select ifnull(sum(money),0.0) from (" + multilistParam.getSql() + ") temp";
		Object totalMoney = nativeQueryOne(sql, multilistParam.getParams());

		DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");// 格式化设置
		return decimalFormat.format(totalMoney);
	}

	public void changeStatus(String[] idArray, String tradestatus) {
		for (String id : idArray) {
			DrawList drawList = getEntityDao().get(id);

			drawList.setIsAudit(StringUtil.equals(tradestatus, "4") ? 1 : 2);
			drawList.setUpdateUser(authService.getCurrentUser().getRealname());
			drawList.setUpdateTime(Dates.getCurrentLongDate());
			drawList.setUpdateUserId(authService.getCurrentUser().getId());
			super.update(drawList);
			drawMoneyService.updatDraw(drawList.getNo(), "", drawList.getTradeNo(), tradestatus,
					Dates.format(new Date(), Dates.CHINESE_DATE_FORMAT_LONG));
		}
	}

	/**
	 * 线下审核通过
	 * 
	 * @param id
	 */
	public void lineAuditPass(String id) {
		DrawList drawList = getEntityDao().get(id);
		drawMoneyService.updatDraw(drawList.getNo(), "", drawList.getTradeNo(), "4",
				Dates.format(new Date(), Dates.CHINESE_DATE_FORMAT_LONG));
		drawList.setIsAudit(1);
		drawList.setUpdateUser(authService.getCurrentUser().getRealname());
		drawList.setUpdateTime(Dates.getCurrentLongDate());
		drawList.setUpdateUserId(authService.getCurrentUser().getId());
		
		super.update(drawList);
		DataMap dataMap = dataMapService.getWithDrawMoney();
		logger.info("线下划账审核通过，系统操作员【" + authService.getCurrentUser().getRealname() + "】," + "提现审核配置金额【"
				+ (ObjectUtil.equals(null, dataMap) ? DataDicKeyConstants.DEFAULT_WITHDRAW_MONEY_VALUE_NAME
						: dataMap.getValueName())
				+ "】;" + "审核通过客户【手机号：" + drawList.getUser().getMobile() + ",姓名：" + drawList.getName() + "】提现，金额："
				+ drawList.getMoney());

	}

	

	/**
	 * 线下初审核通过
	 * 
	 * @param id
	 */
	public void preLineAuditPass(String id) {
		DrawList drawList = getEntityDao().get(id);
		drawList.setIsAudit(-1);
		drawList.setFirstAuditUser(authService.getCurrentUser().getRealname());
		drawList.setFirstAuditTime(Dates.getCurrentLongDate());
		super.update(drawList);
		DataMap dataMap = dataMapService.getWithDrawMoney();
		logger.info("线下划账审核(初审)通过，系统操作员【" + authService.getCurrentUser().getRealname() + "】," + "提现审核配置金额【"
				+ (ObjectUtil.equals(null, dataMap) ? DataDicKeyConstants.DEFAULT_WITHDRAW_MONEY_VALUE_NAME
						: dataMap.getValueName())
				+ "】;" + "初步审核通过客户【手机号：" + drawList.getUser().getMobile() + ",姓名：" + drawList.getName() + "】提现，金额："
				+ drawList.getMoney());

	}

	/**
	 * 后台审核通过
	 * 
	 * @param id
	 */
	public boolean auditMoney(String id) {
		JSONObject jsonObject = drawMoneyService.drawMoney(id);

		logger.info("提现审核调用支付接口返回结果::::code=" + jsonObject.getString("retCode") + ",message="
				+ jsonObject.getString("retMsg"));

		// 接口调用成功 返回0000
		if (StringUtil.equals("0000", jsonObject.getString("retCode"))) {
			DrawList drawList = getEntityDao().get(id);
			drawList.setIsAudit(1);
			drawList.setUpdateUser(authService.getCurrentUser().getRealname());
			drawList.setUpdateTime(Dates.getCurrentLongDate());
			drawList.setUpdateUserId(authService.getCurrentUser().getId());
			super.update(drawList);
			DataMap dataMap = dataMapService.getWithDrawMoney();
			logger.info("系统操作员【" + authService.getCurrentUser().getRealname() + "】," + "提现审核配置金额【"
					+ (ObjectUtil.equals(null, dataMap) ? DataDicKeyConstants.DEFAULT_WITHDRAW_MONEY_VALUE_NAME
							: dataMap.getValueName())
					+ "】;" + "审核通过客户【手机号：" + drawList.getUser().getMobile() + ",姓名：" + drawList.getName() + "】提现，金额："
					+ drawList.getMoney());
			return true;
		}

		return false;
	}

	/**
	 * 更新审核状态
	 * 
	 * @param id
	 */
	public DrawList updateAuditStatus(String id, int isAudit) {
		DrawList drawList = getEntityDao().get(id);
		drawList.setIsAudit(isAudit);
		drawList.setUpdateUser(authService.getCurrentUser().getRealname());
		drawList.setUpdateTime(Dates.getCurrentLongDate());
		drawList.setUpdateUserId(authService.getCurrentUser().getId());
		super.update(drawList);
		DataMap dataMap = dataMapService.getWithDrawMoney();
		logger.info("审核操作：" + (isAudit == 1 ? "成功！" : "失败！系统自动恢复为未审核状态。") + ";系统操作员【"
				+ authService.getCurrentUser().getRealname() + "】," + "提现审核配置金额【"
				+ (ObjectUtil.equals(null, dataMap) ? DataDicKeyConstants.DEFAULT_WITHDRAW_MONEY_VALUE_NAME
						: dataMap.getValueName())
				+ "】;" + "审核通过客户【手机号：" + drawList.getUser().getMobile() + ",姓名：" + drawList.getName() + "】提现，金额："
				+ drawList.getMoney());
		return drawList;
	}

	public boolean doAuit(String id, int isAudit) {
		DrawList drawList = getEntityDao().get(id);
		drawList.setIsAudit(isAudit);
		drawList.setUpdateUser(authService.getCurrentUser().getRealname());
		drawList.setUpdateTime(Dates.getCurrentLongDate());
		drawList.setUpdateUserId(authService.getCurrentUser().getId());
		super.update(drawList);
		DataMap dataMap = dataMapService.getWithDrawMoney();
		logger.info("审核操作：" + (isAudit == 1 ? "成功！" : "失败！系统自动恢复为未审核状态。") + ";系统操作员【"
				+ authService.getCurrentUser().getRealname() + "】," + "提现审核配置金额【"
				+ (ObjectUtil.equals(null, dataMap) ? DataDicKeyConstants.DEFAULT_WITHDRAW_MONEY_VALUE_NAME
						: dataMap.getValueName())
				+ "】;" + "审核通过客户【手机号：" + drawList.getUser().getMobile() + ",姓名：" + drawList.getName() + "】提现，金额："
				+ drawList.getMoney());
		// 开始提现操作

		return true;
	}

	/**
	 * 初审通过
	 * 
	 * @param id
	 * @param i
	 * @return
	 */
	public DrawList firstAuditStatus(String id, int isAudit) {
		DrawList drawList = getEntityDao().get(id);
		drawList.setIsAudit(isAudit);
		drawList.setFirstAuditUser(authService.getCurrentUser().getRealname());
		drawList.setFirstAuditTime(Dates.getCurrentLongDate());
		// drawList.setUpdateUserId(authService.getCurrentUser().getId());
		super.update(drawList);
		return drawList;
	}

	/**
	 * 后台审核不通过
	 * 
	 * @param id
	 */
	public void setNotPass(DrawList drawList, String type) {
		String id = drawList.getId();
		DrawList dbDrawList = getEntityDao().get(id);
		if (ObjectUtil.equals(dbDrawList, null)) {
			throw new ParentAccountException("business.update.not.found.data", null);
		}
		// String aremark=drawList.getAremark(); 不通过原因 不显示
		drawMoneyService.updatDraw(dbDrawList.getNo(), "", dbDrawList.getTradeNo(), "3",
				Dates.format(new Date(), Dates.CHINESE_DATE_FORMAT_LONG));
		logger.info("后台审核提现不通过：Mobile=" + dbDrawList.getUser().getMobile() + ",提现金额：" + dbDrawList.getMoney() + ",提现时间："
				+ dbDrawList.getAddtime());

		DrawList tempDrawList = getEntityDao().get(id);
		tempDrawList.setAremark(drawList.getAremark());
		tempDrawList.setIsAudit(2);
		if ("1".equals(type)) {// 初审
				tempDrawList.setFirstAuditUser(authService.getCurrentUser().getRealname());
				tempDrawList.setFirstAuditTime(Dates.getCurrentLongDate());
		} else {
			tempDrawList.setUpdateUser(authService.getCurrentUser().getRealname());
			tempDrawList.setUpdateTime(Dates.getCurrentLongDate());
			tempDrawList.setUpdateUserId(authService.getCurrentUser().getId());
		}
		tempDrawList.setOperateContent("填写审核不通过原因");

		super.update(tempDrawList);
	}

	/**
	 * 根据id 找到对应的数据
	 * 
	 * @param id
	 * @return
	 */
	public DrawList findByDrawList(String id) {
		return getEntityDao().get(id);
	}

	/**
	 * 获取初审取现数据
	 * 
	 * @param dataPage
	 * @param vo
	 * @return
	 */
	public PageInfo<DrawListAuditVo> findFirstAuditDrawList(PageInfo<DrawListAuditVo> dataPage, DrawListAuditVo vo,
			String type) {
		List<Object> params = new ArrayList<Object>();
		User user = authService.getCurrentUser();
		String sql = "select dl.source source,dl.payment_channel paymentChannel,dl.below_line belowLine,dl.is_audit isAudit, dl.uid, dl.id, u.mobile, dl.bank, dl.card, dl.status, \r";
		sql += "dl.money,  DATE_FORMAT(FROM_UNIXTIME(dl.addtime),'%Y-%m-%d %H:%i:%s') addtime, dl.oktime, v.tname, u.avl_bal balance, dl.update_time auditTime, \r";
		sql += "dl.update_user auditUser  from w_draw_list dl,w_user_verified v,w_user u where dl.uid=v.uid and u.id=dl.uid and u.id=v.uid and dl.`status`=21 \r";

		if ("firstAudit".equals(type)) {// 初审列表
			sql += " and dl.audit_id in (select id from w_draw_money_data wm where wm.first_audit_id=?)   \r";
			sql += " and dl.is_audit=0 \r";
		} else if ("reAudit".equals(type)) {// 复审列表
			sql += " and dl.audit_id in (select id from w_draw_money_data wm where wm.re_audit_id=?)   \r";
			sql += " and dl.is_audit=-1 \r";
		}

		params.add(user.getId());
		if (StringUtil.isNotBlank(vo.getMobile())) {
			sql += " and u.mobile like '%" + vo.getMobile() + "%'";
		}
		if (StringUtil.isNotBlank(vo.getBank())) {
			sql += " and dl.bank like '%" + vo.getBank() + "%'";
		}
		if (StringUtil.isNotBlank(vo.getTname())) {
			sql += " and v.tname like '%" + vo.getTname() + "%'";
		}
		if (StringUtil.isNotBlank(vo.getStarttime())) {
			Date startdate = DateUtils.stringToDate(vo.getStarttime(), "yyyy-MM-dd");
			Long sdate = startdate.getTime() / 1000;
			sql += " and dl.addtime>=?";
			params.add(sdate);
		}
		if (StringUtil.isNotBlank(vo.getEndtime())) {
			String endtime = vo.getEndtime() + " 23:59:59";
			Date enddate = DateUtils.stringToDate(endtime, "yyyy-MM-dd HH:mm:ss");
			Long edate = enddate.getTime() / 1000;
			sql += " and dl.addtime<=?";
			params.add(edate);
		}
		if (StringUtil.isNotBlank(vo.getAuditstarttime())) {
			Date startdate = DateUtils.stringToDate(vo.getAuditstarttime(), "yyyy-MM-dd");
			Long sdate = startdate.getTime() / 1000;
			sql += " and dl.update_time>=?";
			params.add(sdate);
		}
		if (StringUtil.isNotBlank(vo.getAuditendtime())) {
			String endtime = vo.getAuditendtime() + " 23:59:59";
			Date enddate = DateUtils.stringToDate(endtime, "yyyy-MM-dd HH:mm:ss");
			Long edate = enddate.getTime() / 1000;
			sql += " and dl.update_time<=?";
			params.add(edate);
		}
		if (StringUtil.isNotBlank(vo.getMinmoney())) {
			sql += " and dl.money>=?";
			params.add(vo.getMinmoney());
		}

		if (StringUtil.isNotBlank(vo.getMaxmoney())) {
			sql += " and dl.money<=?";
			params.add(vo.getMaxmoney());
		}
		if (StringUtil.isNotBlank(vo.getAuditstatus())) {
			sql += " and dl.is_audit=?";
			params.add(vo.getAuditstatus());
		}

		if (!ObjectUtil.equals(null, vo.getPaymentChannel())) {
			sql += " and dl.payment_channel=?";
			params.add(vo.getPaymentChannel());
		}

		if (!ObjectUtil.equals(null, vo.getSource())) {
			sql += " and dl.source=?";
			params.add(vo.getSource());
		}

		sql += " order by dl.addtime";
		PageInfo<DrawListAuditVo> page = getEntityDao().queryPageBySql(dataPage, sql, DrawListAuditVo.class, null,
				params.toArray());
		return page;
	}

	public DrawListAuditVo queryTotalData(DrawListAuditVo vo, String type) {
		List<Object> params = new ArrayList<Object>();
		User user = authService.getCurrentUser();
		String sql = "select sum(dl.money) money   from w_draw_list dl,w_user_verified v,w_user u where dl.uid=v.uid and u.id=dl.uid and u.id=v.uid and dl.`status`=21 \r";

		if ("firstAudit".equals(type)) {// 初审列表
			sql += " and dl.audit_id in (select id from w_draw_money_data wm where wm.first_audit_id=?)   \r";
			sql += " and dl.is_audit=0 \r";
		} else if ("reAudit".equals(type)) {// 复审列表
			sql += " and dl.audit_id in (select id from w_draw_money_data wm where wm.re_audit_id=?)   \r";
			sql += " and dl.is_audit=-1 \r";
		}

		params.add(user.getId());
		if (StringUtil.isNotBlank(vo.getMobile())) {
			sql += " and u.mobile like '%" + vo.getMobile() + "%'";
		}
		if (StringUtil.isNotBlank(vo.getBank())) {
			sql += " and dl.bank like '%" + vo.getBank() + "%'";
		}
		if (StringUtil.isNotBlank(vo.getTname())) {
			sql += " and v.tname like '%" + vo.getTname() + "%'";
		}
		if (StringUtil.isNotBlank(vo.getStarttime())) {
			Date startdate = DateUtils.stringToDate(vo.getStarttime(), "yyyy-MM-dd");
			Long sdate = startdate.getTime() / 1000;
			sql += " and dl.addtime>=?";
			params.add(sdate);
		}
		if (StringUtil.isNotBlank(vo.getEndtime())) {
			String endtime = vo.getEndtime() + " 23:59:59";
			Date enddate = DateUtils.stringToDate(endtime, "yyyy-MM-dd HH:mm:ss");
			Long edate = enddate.getTime() / 1000;
			sql += " and dl.addtime<=?";
			params.add(edate);
		}
		if (StringUtil.isNotBlank(vo.getAuditstarttime())) {
			Date startdate = DateUtils.stringToDate(vo.getAuditstarttime(), "yyyy-MM-dd");
			Long sdate = startdate.getTime() / 1000;
			sql += " and dl.update_time>=?";
			params.add(sdate);
		}
		if (StringUtil.isNotBlank(vo.getAuditendtime())) {
			String endtime = vo.getAuditendtime() + " 23:59:59";
			Date enddate = DateUtils.stringToDate(endtime, "yyyy-MM-dd HH:mm:ss");
			Long edate = enddate.getTime() / 1000;
			sql += " and dl.update_time<=?";
			params.add(edate);
		}
		if (StringUtil.isNotBlank(vo.getMinmoney())) {
			sql += " and dl.money>=?";
			params.add(vo.getMinmoney());
		}

		if (StringUtil.isNotBlank(vo.getMaxmoney())) {
			sql += " and dl.money<=?";
			params.add(vo.getMaxmoney());
		}
		if (StringUtil.isNotBlank(vo.getAuditstatus())) {
			sql += " and dl.is_audit=?";
			params.add(vo.getAuditstatus());
		}
		if (!ObjectUtil.equals(null, vo.getPaymentChannel())) {
			sql += " and dl.payment_channel=?";
			params.add(vo.getPaymentChannel());
		}
		if (!ObjectUtil.equals(null, vo.getSource())) {
			sql += " and dl.source=?";
			params.add(vo.getSource());
		}

		List<DrawListAuditVo> vos = nativeQuery(sql, params, DrawListAuditVo.class);
		if (vos != null) {
			return vos.get(0);
		}

		return null;
	}

	/**
	 * 获取币币支付待处理订单
	 */
	public List<BibiTreatDrawListVo> queryBbTreatOrders() {
		String sql = "SELECT id,trade_no bbOrderId from w_draw_list where payment_channel = 2 AND LENGTH(trade_no)>0 AND `status`=21 ";
		return nativeQuery(sql, null, BibiTreatDrawListVo.class);
	}

	/**
	 * 获取易支付提现待处理订单
	 */
	public List<PayeaseTreatDrawListVo> queryPayEaseTreatOrders() {
		String sql = " SELECT id, `no` orderId, nbank vmid, narea secret FROM w_draw_list WHERE payment_channel = 3 AND LENGTH(nbank) > 0 AND LENGTH(narea) > 0 AND `status` = 21 ";
		return nativeQuery(sql, null, PayeaseTreatDrawListVo.class);
	}

	/**
	 * 调用配股宝提现审核接口前，先保存操作人信息
	 * 
	 * @param id
	 * @return
	 */
	public DrawList updateAuditUserInfo(String id) {
		DrawList drawList = getEntityDao().get(id);
		drawList.setUpdateUser(authService.getCurrentUser().getRealname());
		drawList.setUpdateTime(Dates.getCurrentLongDate());
		drawList.setUpdateUserId(authService.getCurrentUser().getId());
		logger.info("配股宝API-审核操作,更新操作人信息.");
		super.update(drawList);
		return drawList;
	}

	/**
	 * 调用配股宝api接口进行 提现审核
	 * 
	 * @param id
	 * @param isAudit
	 * @return
	 */
	public DrawList updateApiAuditStatus(String id, int isAudit) {
		DrawList drawList = getEntityDao().get(id);
		drawList.setIsAudit(isAudit);
		super.update(drawList);
		DataMap dataMap = dataMapService.getWithDrawMoney();
		logger.info("配股宝API-审核操作：" + (isAudit == 1 ? "成功！" : "失败！系统自动恢复为未审核状态。") + "提现审核配置金额【"
				+ (ObjectUtil.equals(null, dataMap) ? DataDicKeyConstants.DEFAULT_WITHDRAW_MONEY_VALUE_NAME
						: dataMap.getValueName())
				+ "】;" + "审核通过客户【手机号：" + drawList.getUser().getMobile() + ",姓名：" + drawList.getName() + "】提现，金额："
				+ drawList.getMoney());

		return drawList;
	}
}
