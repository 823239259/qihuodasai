package com.tzdr.business.service.api.hundsun;

import java.util.List;

import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.hundsun.t2sdk.common.core.context.ContextUtil;
import com.hundsun.t2sdk.common.share.dataset.DatasetService;
import com.hundsun.t2sdk.impl.client.T2Services;
import com.hundsun.t2sdk.interfaces.IClient;
import com.hundsun.t2sdk.interfaces.T2SDKException;
import com.hundsun.t2sdk.interfaces.share.dataset.IDataset;
import com.hundsun.t2sdk.interfaces.share.dataset.IDatasets;
import com.hundsun.t2sdk.interfaces.share.event.EventReturnCode;
import com.hundsun.t2sdk.interfaces.share.event.EventType;
import com.hundsun.t2sdk.interfaces.share.event.IEvent;
import com.tzdr.common.api.hundsun.data.Amount;
import com.tzdr.common.api.hundsun.data.BranchLiquidate;
import com.tzdr.common.api.hundsun.data.Combasset;
import com.tzdr.common.api.hundsun.data.Combinfo;
import com.tzdr.common.api.hundsun.data.Combofund;
import com.tzdr.common.api.hundsun.data.Combostock;
import com.tzdr.common.api.hundsun.data.Current;
import com.tzdr.common.api.hundsun.data.Entrust;
import com.tzdr.common.api.hundsun.data.EntrustResult;
import com.tzdr.common.api.hundsun.data.Futu;
import com.tzdr.common.api.hundsun.data.FutureLoan;
import com.tzdr.common.api.hundsun.data.Futureamount;
import com.tzdr.common.api.hundsun.data.OperatorInfo;
import com.tzdr.common.api.hundsun.data.OperatorRight;
import com.tzdr.common.api.hundsun.data.Realdeal;
import com.tzdr.common.api.hundsun.data.RiskControl;
import com.tzdr.common.api.hundsun.data.StockCurrent;
import com.tzdr.common.exception.EmailExceptionHandler;
import com.tzdr.common.utils.ConfUtil;
import com.tzdr.common.utils.EmailUtils;
import com.tzdr.common.utils.SpringUtils;
import com.tzdr.domain.dao.HundsunTokenDao;
import com.tzdr.domain.entity.HundsunToken;

/**
 * 恒生系统接口封装
 * 
 * @author Lin Feng 2014年12月23日
 * 
 */

public class HundsunJres {

	public static final Logger log = LoggerFactory.getLogger(HundsunJres.class);


	private static HundsunJres instance;

	private static IClient client;

	private static T2Services server;

	private static final String FUNC_AM_USER_LOGIN = "769000";

	private static final String FUNC_AM_USER_LOGOUT = "769001";

	private static final String FUNC_AM_CHANGE_PASSWORD = "769002";

	private static final String FUNC_AM_ASSERT_MOVE = "769115";

	private static final String FUNC_AM_COMBOSTOCK_QRY = "769150";

	private static final String FUNC_AM_COMBOFUND_QRY = "769151";

	private static final String FUNC_AM_FUTU_QRY = "769152";

	private static final String FUNC_AM_ENTRUST_QRY = "769153";

	private static final String FUNC_AM_REALDEAL_QRY = "769155";

	private static final String FUNC_AM_AMOUNT_QRY = "769156";

	private static final String FUNC_AM_FUTUREAMOUNT_QRY = "769157";

	private static final String FUNC_AM_ENTRUST_HISTORY_QRY = "769900";

	private static final String FUNC_AM_REALDEAL_HISTORY_QRY = "769901";

	private static final String FUNC_AM_COMBINFO_QRY = "769902";

	private static final String FUNC_AM_CHANGE_ASSET_INFO = "769952";

	private static final String FUNC_AM_COMBASSET_QRY = "769201";

	private static final String FUNC_AM_CURRENT_QRY = "769202";

	private static final String FUNC_AM_STOCK_CURRENT_QRY = "769203";

	private static final String FUNC_AM_ENTRUST = "769100";

	private static final String FUNC_AM_ENTRUST_WITHDRAW = "769101";

	private static final String FUNC_AM_RISKCONTROL_QRY = "769905";

	private static final String FUNC_BRANCH_LIQUIDATE_QRY = "769600";

	private static final String FUNC_AM_CHANGE_OPERATOR_INFO = "769301";

	private static final String FUNC_AM_FUTURE_LOAD_QRY = "769204";

	private static final String FUNC_AM_OPERATOR_INFO_QRY = "769302";

	private static final String FUNC_AM_OPERATOR_RIGHT_QRY = "769303";

	private static final String FUNC_AM_CHANGE_ASSET_RIGHT = "769304";

	private static final String FUNC_AM_STOCK_MOVE = "769117";

	private static final String RETURN_CODE = "returnCode";

	private static final String MESSAGE = "message";

	private static final String LOGIN_NO_RESULT = "-100";

	private static final String USER_TOKEN = "userToken";

	private static final String SUCCESS = "0";

	public static final String CASH = "107";

	public static final String BOND = "117";

	public static String className = "";

	private HundsunJres() throws T2SDKException {
		className = this.getClass().getName();
		server = T2Services.getInstance();
		server.init();
		server.start();
		client = server.getClient("tzdr");
	}

	public static synchronized HundsunJres getInstance() throws T2SDKException {
		if (instance == null) {
			instance = new HundsunJres();
		}
		return instance;
	}

	public void stop() {
		server.stop();
	}

	private IEvent getEventByAlias(String alias, int eventType) {
		return ContextUtil.getServiceContext().getEventFactory()
				.getEventByAlias(alias, eventType);
	}

	private void sendEmail(List<String> pramas) {
		try {
			EmailUtils.getInstance().sendMailTemp(
					ConfUtil.getContext("mail.to.dev"), "恒生接口返回失败",
					"exceptionemail", pramas);
		} catch (Exception e) {
			log.error(this.getClass().getName() + e.getMessage());
		}
	}

	/**
	 * 登录
	 * 
	 * @param operatorNo
	 *            恒生帐户
	 * @param password
	 *            恒生帐户密码
	 * @return { RETURN_CODE:状态码 O为成功 其它失败。 MESSAGE:返回消息 USER_TOKEN:后面要用的token }
	 * 
	 */
	public JSONObject funcAmUserLogin(String operatorNo, String password) {

		JSONObject resultJson = new JSONObject();

		try {
			// 获取event
			IEvent event = getEventByAlias(FUNC_AM_USER_LOGIN,
					EventType.ET_REQUEST);
			// 往event中添加dataset
			IDataset dataset = DatasetService.getDefaultInstance().getDataset();
			dataset.addColumn("operator_no");
			dataset.addColumn("password");
			dataset.appendRow();
			dataset.updateString("operator_no", operatorNo);
			dataset.updateString("password", password);
			event.putEventData(dataset);
			// 同步发送如下
			IEvent rsp = client.sendReceive(event, 10000);
			// 先判断返回值
			if (rsp.getReturnCode() != EventReturnCode.I_OK) {
				resultJson.put(RETURN_CODE, rsp.getErrorNo());
				resultJson.put(MESSAGE, rsp.getErrorInfo());
				resultJson.put(USER_TOKEN, "");
				String details = rsp.getErrorNo() + "," +rsp.getErrorInfo();
				List<String> pramas = Lists.newArrayList();
				pramas.add("com.tzdr.common.api.ihuyi.hundsun.HundsunJres.funcAmUserLogin");
				pramas.add("errorNo::" + rsp.getErrorNo() + ",errorInfo::"
						+ rsp.getErrorInfo() + ",details::" + details);
				sendEmail(pramas);
			} else {
				// 获得结果集
				IDatasets result = rsp.getEventDatas();
				// 获得结果集总数
				int datasetCount = result.getDatasetCount();
				// 遍历所有的结果集
				if (datasetCount > 0) {
					IDataset ds = result.getDataset(0);
					resultJson.put(RETURN_CODE,
							String.valueOf(rsp.getReturnCode()));
					resultJson.put(MESSAGE, rsp.getErrorInfo());
					resultJson.put(USER_TOKEN, ds.getString("user_token"));
				} else {
					resultJson.put(RETURN_CODE, LOGIN_NO_RESULT);
					resultJson.put(MESSAGE, "No user_token in the dataset.");
					resultJson.put(USER_TOKEN, "");
				}
			}
			// 同步发送到此结束
		} catch (T2SDKException e) {
			String details = operatorNo + "," + password;
			EmailExceptionHandler.getInstance().HandleExceptionWithData(e,
					"恒生接口报错", className + "funcAmUserLogin", details);
		}
		return resultJson;

	}

	/**
	 * @param operatorNo
	 * @param password
	 * @return
	 */
	public String Login(String operatorNo, String password) {
		return funcAmUserLogin(operatorNo, password).getString("userToken");
	}

	/**
	 * 拿token
	 * 
	 * @return
	 */
	public String getLoginToken() {
		String operatorNo = ConfUtil.getContext("hundsun.manager.operator.no");
		String password = ConfUtil
				.getContext("hundsun.manager.operator.password");
		return funcAmUserLogin(operatorNo, password).getString("userToken");
	}

	public HundsunToken findHundsunToken(String account) {
		List<HundsunToken> hundsunToken = SpringUtils.getBean(HundsunTokenDao.class)
				.findByAccount(account);
		if (hundsunToken.size() > 0) {
			return hundsunToken.get(0);
		}
		return null;
	}

	public HundsunToken get(String account) {
		String token = getLoginToken();
		if (StringUtil.isBlank(token)) {
			log.error("恒生接口拿token失败，account:"+account);
			List<String> pramas = Lists.newArrayList();
			pramas.add("恒生接口拿token失败:");
			pramas.add("details::" + account);
			sendEmail(pramas);
		}
		HundsunToken hundsunToken = findHundsunToken(account);
		if (hundsunToken == null) {
			hundsunToken = new HundsunToken();
			hundsunToken.setAccount(account);
			hundsunToken.setToken(token);
			SpringUtils.getBean(HundsunTokenDao.class).save(hundsunToken);
		} else {
			hundsunToken.setToken(token);
			SpringUtils.getBean(HundsunTokenDao.class).update(hundsunToken);
		}
		return hundsunToken;
	}

	/**
	 * 登录
	 * 
	 * @return
	 */
	public String Login() {
		String operatorNo = ConfUtil.getContext("hundsun.manager.operator.no");
		HundsunToken hundsunToken = findHundsunToken(operatorNo);
		if (hundsunToken != null) {
			return hundsunToken.getToken();
		} else {
			hundsunToken=get(operatorNo);
			return hundsunToken.getToken();
		}
	}

	/**
	 * 退出登录
	 * 
	 * @param userToken
	 * @return
	 */
	public boolean LogoutOnce(String userToken) {
		String operatorNo = ConfUtil.getContext("hundsun.manager.operator.no");
		return funcAmUserLogout(userToken, operatorNo);
	}

	/**
	 * 退出登录(兼容原来代码，2.0后面后删除)
	 * 
	 * @param userToken
	 * @return
	 */
	@Deprecated
	public boolean Logout(String userToken) {
		// String operatorNo =
		// ConfUtil.getContext("hundsun.manager.operator.no");
		// return funcAmUserLogout(userToken, operatorNo);
		return Boolean.TRUE;
	}

	/**
	 * 退出登录
	 * 
	 * @param userToken
	 * @param operatorNo
	 * @return true 成功 false 失败
	 */
	public boolean funcAmUserLogout(String userToken, String operatorNo) {

		boolean logout = Boolean.FALSE;

		try {
			// 获取event
			IEvent event = getEventByAlias(FUNC_AM_USER_LOGOUT,
					EventType.ET_REQUEST);
			// 往event中添加dataset
			IDataset dataset = DatasetService.getDefaultInstance().getDataset();
			dataset.addColumn("user_token");
			dataset.addColumn("operator_no");
			dataset.appendRow();
			dataset.updateString("user_token", userToken);
			dataset.updateString("operator_no", operatorNo);
			event.putEventData(dataset);
			// 同步发送如下
			IEvent rsp = client.sendReceive(event, 10000);

			// 先判断返回值
			if (rsp.getReturnCode() == EventReturnCode.I_OK) { // 返回错误
				// 获得结果集
				IDatasets result = rsp.getEventDatas();
				// 获得结果集总数
				int datasetCount = result.getDatasetCount();
				// 遍历所有的结果集
				if (datasetCount > 0) {
					IDataset ds = result.getDataset(0);
					String errorNo = ds.getString("error_no");
					if (SUCCESS.equals(errorNo)) {
						logout = Boolean.TRUE;
					}
				}
			}
			// 同步发送到此结束
		} catch (T2SDKException e) {
			String details = userToken + "," + operatorNo;
			EmailExceptionHandler.getInstance().HandleExceptionWithData(e,
					"恒生接口报错", className + "funcAmUserLogout", details);
		}
		return logout;

	}

	/**
	 * 修改操作员密码
	 * 
	 * @param userToken
	 *            token
	 * @param passwordOld
	 *            原密码
	 * @param passwordNew
	 *            新密码
	 * @return true成功,false失败
	 */
	public boolean funcAmChangePassword(String userToken, String passwordOld,
			String passwordNew) {

		boolean change = Boolean.FALSE;

		try {
			// 获取event
			IEvent event = getEventByAlias(FUNC_AM_CHANGE_PASSWORD,
					EventType.ET_REQUEST);
			// 往event中添加dataset
			IDataset dataset = DatasetService.getDefaultInstance().getDataset();
			dataset.addColumn("user_token");
			dataset.addColumn("password_old");
			dataset.addColumn("password_new");
			dataset.appendRow();
			dataset.updateString("password_old", userToken);
			dataset.updateString("operator_no", passwordOld);
			dataset.updateString("password_new", passwordNew);
			event.putEventData(dataset);
			// 同步发送如下
			IEvent rsp = client.sendReceive(event, 10000);

			// 先判断返回值
			if (rsp.getReturnCode() == EventReturnCode.I_OK) {
				// 获得结果集
				IDatasets result = rsp.getEventDatas();
				// 获得结果集总数
				int datasetCount = result.getDatasetCount();
				// 遍历所有的结果集
				if (datasetCount > 0) {
					IDataset ds = result.getDataset(0);
					String errorNo = ds.getString("error_no");
					if (SUCCESS.equals(errorNo)) {
						change = Boolean.TRUE;
					}
				}
			} else {
				String details = userToken + "," + passwordOld + ","
						+ passwordNew;
				List<String> pramas = Lists.newArrayList();
				pramas.add("com.tzdr.common.api.ihuyi.hundsun.HundsunJres.funcAmChangePassword");
				pramas.add("errorNo::" + rsp.getErrorNo() + ",errorInfo::"
						+ rsp.getErrorInfo() + ",details::" + details);
				sendEmail(pramas);
				log.warn(
						"com.tzdr.common.api.ihuyi.hundsun.HundsunJres.funcAmChangePassword,errorNo::{},errorInfo::{}",
						rsp.getErrorNo(), rsp.getErrorInfo());
			}
			// 同步发送到此结束
		} catch (T2SDKException e) {
			String details = userToken + "," + passwordOld + "," + passwordNew;
			EmailExceptionHandler.getInstance().HandleExceptionWithData(e,
					"恒生接口报错", className + "funcAmChangePassword", details);
		}
		return change;

	}

	/**
	 * 组合持仓查询
	 * 
	 * 非必填 不用时,用null.
	 * 
	 * @param userToken
	 *            必填
	 * @param fundAccount
	 *            非必填
	 * @param combineId
	 *            非必填
	 * @param exchangeType
	 *            非必填
	 * @param stockCode
	 *            非必填
	 * @return
	 */
	public List<Combostock> funcAmCombostockQry(String userToken,
			String fundAccount, String combineId, String exchangeType,
			String stockCode) {

		List<Combostock> combostockList = Lists.newArrayList();

		try {
			// 获取event
			IEvent event = getEventByAlias(FUNC_AM_COMBOSTOCK_QRY,
					EventType.ET_REQUEST);
			// 往event中添加dataset
			IDataset dataset = DatasetService.getDefaultInstance().getDataset();
			dataset.addColumn("user_token");
			dataset.addColumn("fund_account");
			dataset.addColumn("combine_id");
			dataset.addColumn("exchange_type");
			dataset.addColumn("stock_code");
			dataset.appendRow();
			dataset.updateString("user_token", userToken);
			dataset.updateString("fund_account", fundAccount);
			dataset.updateString("combine_id", combineId);
			dataset.updateString("exchange_type", exchangeType);
			dataset.updateString("stock_code", stockCode);
			event.putEventData(dataset);
			// 同步发送如下
			IEvent rsp = client.sendReceive(event, 10000);
			// 先判断返回值
			if (rsp.getReturnCode() != EventReturnCode.I_OK) { // 返回错误
				String details = userToken + "," + fundAccount + ","
						+ combineId + "," + exchangeType + "," + stockCode;
				List<String> pramas = Lists.newArrayList();
				pramas.add("com.tzdr.common.api.ihuyi.hundsun.HundsunJres.funcAmCombostockQry");
				pramas.add("errorNo::" + rsp.getErrorNo() + ",errorInfo::"
						+ rsp.getErrorInfo() + ",details::" + details);
				sendEmail(pramas);
				log.warn(
						"com.tzdr.common.api.ihuyi.hundsun.HundsunJres.funcAmCombostockQry,errorNo::{},errorInfo::{}",
						rsp.getErrorNo(), rsp.getErrorInfo());
			} else {
				// 获得结果集
				IDatasets result = rsp.getEventDatas();
				// 获得结果集总数
				int datasetCount = result.getDatasetCount();
				// 遍历所有的结果集

				for (int i = 0; i < datasetCount; i++) {
					// 开始读取单个结果集的信息
					IDataset ds = result.getDataset(i);
					ds.beforeFirst();
					while (ds.hasNext()) {
						ds.next();
						Combostock combostock = new Combostock();
						combostock.setInitDate(ds.getLong("init_date"));
						combostock.setFundAccount(ds.getString("fund_account"));
						combostock.setCombineId(ds.getString("combine_id"));
						combostock.setStockAccount(ds
								.getString("stock_account"));
						combostock.setSeatNo(ds.getString("seat_no"));
						combostock.setExchangeType(ds
								.getString("exchange_type"));
						combostock.setStockCode(ds.getString("stock_code"));
						combostock.setInvestWay(ds.getString("invest_way"));
						combostock.setPupilFlag(ds.getString("pupil_flag"));
						combostock.setBeginAmount(ds.getDouble("begin_amount"));
						combostock.setCurrentAmount(ds
								.getDouble("current_amount"));
						combostock.setEnableAmount(ds
								.getDouble("enable_amount"));
						combostock.setCostBalance(ds.getDouble("cost_balance"));
						combostock.setMarketValue(ds.getDouble("market_value"));
						combostock.setBuyAmount(ds.getDouble("buy_amount"));
						combostock.setSellAmount(ds.getDouble("sell_amount"));
						combostockList.add(combostock);
					}
				}
			}
			// 同步发送到此结束

		} catch (T2SDKException e) {
			String details = userToken + "," + fundAccount + "," + combineId
					+ "," + exchangeType + "," + stockCode;
			EmailExceptionHandler.getInstance().HandleExceptionWithData(e,
					"恒生接口报错", className + "funcAmCombostockQry", details);
		}
		return combostockList;

	}

	/**
	 * 组合层现货可用资金查询
	 * 
	 * @param userToken
	 * @param fundAccount
	 * @param combineId
	 * @return
	 */
	public List<Combofund> funcAmCombofundQry(String userToken,
			String fundAccount, String combineId) {

		List<Combofund> combofundList = Lists.newArrayList();

		try {
			// 获取event
			IEvent event = getEventByAlias(FUNC_AM_COMBOFUND_QRY,
					EventType.ET_REQUEST);
			// 往event中添加dataset
			IDataset dataset = DatasetService.getDefaultInstance().getDataset();
			dataset.addColumn("user_token");
			dataset.addColumn("fund_account");
			dataset.addColumn("combine_id");
			dataset.appendRow();
			dataset.updateString("user_token", userToken);
			dataset.updateString("fund_account", fundAccount);
			dataset.updateString("combine_id", combineId);
			event.putEventData(dataset);
			// 同步发送如下
			IEvent rsp = client.sendReceive(event, 10000);
			// 先判断返回值
			if (rsp.getReturnCode() != EventReturnCode.I_OK) { // 返回错误
				String details = userToken + "," + fundAccount + ","
						+ combineId;
				List<String> pramas = Lists.newArrayList();
				pramas.add("com.tzdr.common.api.ihuyi.hundsun.HundsunJres.funcAmCombofundQry");
				pramas.add("errorNo::" + rsp.getErrorNo() + ",errorInfo::"
						+ rsp.getErrorInfo() + ",details::" + details);
				sendEmail(pramas);
				log.warn(
						"com.tzdr.common.api.ihuyi.hundsun.HundsunJres.funcAmCombofundQry,errorNo::{},errorInfo::{}",
						rsp.getErrorNo(), rsp.getErrorInfo());
			} else {
				// 获得结果集
				IDatasets result = rsp.getEventDatas();
				// 获得结果集总数
				int datasetCount = result.getDatasetCount();
				// 遍历所有的结果集

				for (int i = 0; i < datasetCount; i++) {
					// 开始读取单个结果集的信息
					IDataset ds = result.getDataset(i);
					ds.beforeFirst();
					while (ds.hasNext()) {
						ds.next();
						Combofund combofund = new Combofund();
						combofund.setFundAccount(ds.getString("fund_account"));
						combofund.setCombineId(ds.getString("combine_id"));
						combofund.setEnableBalance(ds
								.getDouble("enable_balance"));
						combofund.setEnableBalanceT1(ds
								.getDouble("enable_balance_t1"));
						combofund.setDate(ds.getLong("date"));
						combofund.setCurrencyNo(ds.getString("currency_no"));
						combofundList.add(combofund);

					}
				}
			}
			// 同步发送到此结束

		} catch (T2SDKException e) {
			String details = userToken + "," + fundAccount + "," + combineId;
			EmailExceptionHandler.getInstance().HandleExceptionWithData(e,
					"恒生接口报错", className + "funcAmCombofundQry", details);
		}
		return combofundList;

	}

	/**
	 * 资金划转
	 * 
	 * @param userToken
	 * @param fundAccount
	 * @param combineId
	 * @param targetCombineId
	 * @param occurBalance
	 * @param businOpflag
	 * @return
	 */
	public boolean funcAmAssetMove(String userToken, String fundAccount,
			String combineId, String targetCombineId, double occurBalance,
			String businOpflag, String remark) {
		log.info("资金划转:" + "userToken:" + userToken + "fundAccount:"
				+ fundAccount + "combineId:" + combineId + "targetCombineId:"
				+ targetCombineId + "occurBalance:" + occurBalance
				+ "businOpflag:" + businOpflag + "remark:" + remark);
		boolean flag = Boolean.FALSE;

		try {
			// 获取event
			IEvent event = getEventByAlias(FUNC_AM_ASSERT_MOVE,
					EventType.ET_REQUEST);
			// 往event中添加dataset
			IDataset dataset = DatasetService.getDefaultInstance().getDataset();
			dataset.addColumn("user_token");
			dataset.addColumn("fund_account");
			dataset.addColumn("combine_id");
			dataset.addColumn("target_combine_id");
			dataset.addColumn("occur_balance");
			dataset.addColumn("busin_opflag");
			dataset.addColumn("remark");

			dataset.appendRow();
			dataset.updateString("user_token", userToken);
			dataset.updateString("fund_account", fundAccount);
			dataset.updateString("combine_id", combineId);
			dataset.updateString("target_combine_id", targetCombineId);
			dataset.updateDouble("occur_balance", occurBalance);
			dataset.updateString("busin_opflag", businOpflag);
			dataset.updateString("remark", remark);
			event.putEventData(dataset);
			// 同步发送如下
			IEvent rsp = client.sendReceive(event, 10000);
			// 先判断返回值
			if (rsp.getReturnCode() == EventReturnCode.I_OK) {
				// 获得结果集
				IDatasets result = rsp.getEventDatas();
				// 获得结果集总数
				int datasetCount = result.getDatasetCount();
				// 遍历所有的结果集
				if (datasetCount > 0) {
					IDataset ds = result.getDataset(0);
					String errorNo = ds.getString("error_no");
					if (SUCCESS.equals(errorNo)) {
						flag = Boolean.TRUE;
					} else {
						List<String> pramas = Lists.newArrayList();
						String methodName = this.getClass().getName() + "."
								+ "TransferMoney";
						String exception = "error_no:"
								+ ds.getString("error_no") + "|error_info:"
								+ ds.getString("error_info");
						pramas.add(methodName);
						pramas.add("参数userToken：" + userToken + " fundAccount:"
								+ fundAccount + "-combineId:" + combineId
								+ "-targetCombineId:" + targetCombineId
								+ "-occurBalance" + occurBalance + exception);
						sendEmail(pramas);
						log.warn("error_no:" + ds.getString("error_no")
								+ "|error_info:" + ds.getString("error_info"));
					}
				}
			}
			// 同步发送到此结束

		} catch (T2SDKException e) {
			String details = userToken + "," + fundAccount + "," + combineId
					+ "," + targetCombineId + "," + occurBalance + ","
					+ businOpflag + "," + remark;
			EmailExceptionHandler.getInstance().HandleExceptionWithData(e,
					"恒生接口报错", className + "funcAmAssetMove", details);
		}
		return flag;

	}

	/**
	 * 
	 * 组合层期货可用资金查询
	 * 
	 * @param userToken
	 * @param fundAccount
	 * @param combineId
	 * @return
	 */
	public List<Futu> funcAmFutuQry(String userToken, String fundAccount,
			String combineId) {

		List<Futu> futuList = Lists.newArrayList();

		try {
			// 获取event
			IEvent event = getEventByAlias(FUNC_AM_FUTU_QRY,
					EventType.ET_REQUEST);
			// 往event中添加dataset
			IDataset dataset = DatasetService.getDefaultInstance().getDataset();
			dataset.addColumn("user_token");
			dataset.addColumn("fund_account");
			dataset.addColumn("combine_id");
			dataset.appendRow();
			dataset.updateString("user_token", userToken);
			dataset.updateString("fund_account", fundAccount);
			dataset.updateString("combine_id", combineId);
			event.putEventData(dataset);
			// 同步发送如下
			IEvent rsp = client.sendReceive(event, 10000);
			// 先判断返回值
			if (rsp.getReturnCode() != EventReturnCode.I_OK) { // 返回错误
				String details = userToken + "," + fundAccount + ","
						+ combineId;
				List<String> pramas = Lists.newArrayList();
				pramas.add("com.tzdr.common.api.ihuyi.hundsun.HundsunJres.funcAmFutuQry");
				pramas.add("errorNo::" + rsp.getErrorNo() + ",errorInfo::"
						+ rsp.getErrorInfo() + ",details::" + details);
				sendEmail(pramas);
				log.warn(
						"com.tzdr.common.api.ihuyi.hundsun.HundsunJres.funcAmFutuQry,errorNo::{},errorInfo::{}",
						rsp.getErrorNo(), rsp.getErrorInfo());
			} else {
				// 获得结果集
				IDatasets result = rsp.getEventDatas();
				// 获得结果集总数
				int datasetCount = result.getDatasetCount();
				// 遍历所有的结果集

				for (int i = 0; i < datasetCount; i++) {
					// 开始读取单个结果集的信息
					IDataset ds = result.getDataset(i);
					ds.beforeFirst();
					while (ds.hasNext()) {
						ds.next();
						Futu futu = new Futu();
						futu.setFundAccount(ds.getString("fund_account"));
						futu.setCombineId(ds.getString("combine_id"));
						futu.setFutuEnableBalance(ds
								.getDouble("futu_enable_balance"));
						futu.setDate(ds.getLong("date"));
						futu.setCurrencyNo(ds.getString("currency_no"));
						futuList.add(futu);
					}
				}
			}
			// 同步发送到此结束

		} catch (T2SDKException e) {
			String details = userToken + "," + fundAccount + "," + combineId;
			EmailExceptionHandler.getInstance().HandleExceptionWithData(e,
					"恒生接口报错", className + "funcAmFutuQry", details);
		}
		return futuList;

	}

	/**
	 * 委托查询
	 * 
	 * @param userToken
	 * @param fundAccount
	 * @param combineId
	 * @param enAmentrustStatus
	 * @param exchangeType
	 * @param entrustDirection
	 * @param beginBatchNo
	 * @param endBatchNo
	 * @param beginEntrustNo
	 * @param endEntrustNo
	 * @return @
	 */
	public List<Entrust> funcAmEntrustQry(String userToken, String fundAccount,
			String combineId, String enAmentrustStatus, String exchangeType,
			String entrustDirection, long beginBatchNo, long endBatchNo,
			long beginEntrustNo, long endEntrustNo) {

		List<Entrust> entrustList = Lists.newArrayList();

		try {
			// 获取event
			IEvent event = getEventByAlias(FUNC_AM_ENTRUST_QRY,
					EventType.ET_REQUEST);
			// 往event中添加dataset
			IDataset dataset = DatasetService.getDefaultInstance().getDataset();
			dataset.addColumn("user_token");
			dataset.addColumn("fund_account");
			dataset.addColumn("combine_id");
			dataset.addColumn("en_amentrust_status");
			dataset.addColumn("exchange_type");
			dataset.addColumn("entrust_direction");
			dataset.addColumn("begin_batch_no");
			dataset.addColumn("end_batch_no");
			dataset.addColumn("begin_entrust_no");
			dataset.addColumn("end_entrust_no");
			dataset.appendRow();
			dataset.updateString("user_token", userToken);
			dataset.updateString("fund_account", fundAccount);
			dataset.updateString("combine_id", combineId);
			dataset.updateString("en_amentrust_status", enAmentrustStatus);
			dataset.updateString("exchange_type", exchangeType);
			dataset.updateString("entrust_direction", entrustDirection);
			dataset.updateLong("begin_batch_no", beginBatchNo);
			dataset.updateLong("end_batch_no", endBatchNo);
			dataset.updateLong("begin_entrust_no", beginEntrustNo);
			dataset.updateLong("end_entrust_no", endEntrustNo);
			event.putEventData(dataset);
			// 同步发送如下
			IEvent rsp = client.sendReceive(event, 10000);
			// 先判断返回值
			if (rsp.getReturnCode() != EventReturnCode.I_OK) { // 返回错误
				String details = userToken + "," + fundAccount + ","
						+ combineId + "," + enAmentrustStatus + ","
						+ exchangeType + "," + entrustDirection + ","
						+ beginBatchNo + "," + endBatchNo + ","
						+ beginEntrustNo + "," + endEntrustNo;
				List<String> pramas = Lists.newArrayList();
				pramas.add("com.tzdr.common.api.ihuyi.hundsun.HundsunJres.funcAmEntrustQry");
				pramas.add("errorNo::" + rsp.getErrorNo() + ",errorInfo::"
						+ rsp.getErrorInfo() + ",details::" + details);
				sendEmail(pramas);
				log.warn(
						"com.tzdr.common.api.ihuyi.hundsun.HundsunJres.funcAmEntrustQry,errorNo::{},errorInfo::{}",
						rsp.getErrorNo(), rsp.getErrorInfo());
			} else {
				// 获得结果集
				IDatasets result = rsp.getEventDatas();
				// 获得结果集总数
				int datasetCount = result.getDatasetCount();
				// 遍历所有的结果集

				for (int i = 0; i < datasetCount; i++) {
					// 开始读取单个结果集的信息
					IDataset ds = result.getDataset(i);
					ds.beforeFirst();
					while (ds.hasNext()) {
						ds.next();
						Entrust entrust = new Entrust();
						entrust.setBatchNo(ds.getLong("batch_no"));
						entrust.setEntrustNo(ds.getLong("entrust_no"));
						entrust.setFundAccount(ds.getString("fund_account"));
						entrust.setCombineId(ds.getString("combine_id"));
						entrust.setInstructNo(ds.getLong("instruct_no"));
						entrust.setInstructmodNo(ds.getLong("instructmod_no"));
						entrust.setStockCode(ds.getString("stock_code"));
						entrust.setExchangeType(ds.getString("exchange_type"));
						entrust.setStockAccount(ds.getString("stock_account"));
						entrust.setSeatNo(ds.getString("seat_no"));
						entrust.setInvestWay(ds.getString("invest_way"));
						entrust.setEntrustDirection(ds
								.getString("entrust_direction"));
						entrust.setEntrustPrice(ds.getDouble("entrust_price"));
						entrust.setEntrustAmount(ds.getDouble("entrust_amount"));
						entrust.setEntrustTime(ds.getLong("entrust_time"));
						entrust.setReportTime(ds.getLong("report_time"));
						entrust.setBusinessAmount(ds
								.getDouble("business_amount"));
						entrust.setBusinessBalance(ds
								.getDouble("business_balance"));
						entrust.setWithdrawAmount(ds.getLong("withdraw_amount"));
						entrust.setAmentrustStatus(ds
								.getString("amentrust_status"));
						entrust.setCancelInfo(ds.getString("cancel_info"));
						entrustList.add(entrust);

					}
				}
			}
			// 同步发送到此结束

		} catch (T2SDKException e) {
			String details = userToken + "," + fundAccount + "," + combineId
					+ "," + enAmentrustStatus + "," + exchangeType + ","
					+ entrustDirection + "," + beginBatchNo + "," + endBatchNo
					+ "," + beginEntrustNo + "," + endEntrustNo;
			EmailExceptionHandler.getInstance().HandleExceptionWithData(e,
					"恒生接口报错", className + "funcAmEntrustQry", details);
		}
		
		if (CollectionUtils.isEmpty(entrustList) 
				|| StringUtil.isBlank(entrustList.get(0).getFundAccount())){
			return null;
		}
		return entrustList;

	}

	/**
	 * 成交查询
	 * 
	 * @param userToken
	 * @param fundAccount
	 * @param combineId
	 * @param exchangeType
	 * @param entrustDirection
	 * @param beginBatchNo
	 * @param endBatchNo
	 * @param beginEntrustNo
	 * @param endEntrustNo
	 * @return @
	 */
	public List<Realdeal> funcAmRealdealQry(String userToken,
			String fundAccount, String combineId, String exchangeType,
			String entrustDirection, long beginBatchNo, long endBatchNo,
			long beginEntrustNo, long endEntrustNo) {

		List<Realdeal> realdealList = Lists.newArrayList();

		try {
			// 获取event
			IEvent event = getEventByAlias(FUNC_AM_REALDEAL_QRY,
					EventType.ET_REQUEST);
			// 往event中添加dataset
			IDataset dataset = DatasetService.getDefaultInstance().getDataset();
			dataset.addColumn("user_token");
			dataset.addColumn("fund_account");
			dataset.addColumn("combine_id");
			dataset.addColumn("exchange_type");
			dataset.addColumn("entrust_direction");
			dataset.addColumn("begin_batch_no");
			dataset.addColumn("end_batch_no");
			dataset.addColumn("begin_entrust_no");
			dataset.addColumn("end_entrust_no");
			dataset.appendRow();
			dataset.updateString("user_token", userToken);
			dataset.updateString("fund_account", fundAccount);
			dataset.updateString("combine_id", combineId);
			dataset.updateString("exchange_type", exchangeType);
			dataset.updateString("entrust_direction", entrustDirection);
			dataset.updateLong("begin_batch_no", beginBatchNo);
			dataset.updateLong("end_batch_no", endBatchNo);
			dataset.updateLong("begin_entrust_no", beginEntrustNo);
			dataset.updateLong("end_entrust_no", endEntrustNo);
			event.putEventData(dataset);
			// 同步发送如下
			IEvent rsp = client.sendReceive(event, 10000);
			// 先判断返回值
			if (rsp.getReturnCode() != EventReturnCode.I_OK) { // 返回错误
				String details = userToken + "," + fundAccount + ","
						+ combineId + "," + exchangeType + ","
						+ entrustDirection + "," + beginBatchNo + ","
						+ endBatchNo + "," + beginEntrustNo + ","
						+ endEntrustNo;
				List<String> pramas = Lists.newArrayList();
				pramas.add("com.tzdr.common.api.ihuyi.hundsun.HundsunJres.funcAmRealdealQry");
				pramas.add("errorNo::" + rsp.getErrorNo() + ",errorInfo::"
						+ rsp.getErrorInfo() + ",details::" + details);
				sendEmail(pramas);
				log.warn(
						"com.tzdr.common.api.ihuyi.hundsun.HundsunJres.funcAmRealdealQry,errorNo::{},errorInfo::{}",
						rsp.getErrorNo(), rsp.getErrorInfo());
			} else {
				// 获得结果集
				IDatasets result = rsp.getEventDatas();
				// 获得结果集总数
				int datasetCount = result.getDatasetCount();
				// 遍历所有的结果集

				for (int i = 0; i < datasetCount; i++) {
					// 开始读取单个结果集的信息
					IDataset ds = result.getDataset(i);
					ds.beforeFirst();
					while (ds.hasNext()) {
						ds.next();
						Realdeal realdeal = new Realdeal();
						realdeal.setPositionStr(ds.getString("position_str"));
						realdeal.setBusinessNo(ds.getLong("business_no"));
						realdeal.setBatchNo(ds.getLong("batch_no"));
						realdeal.setEntrustNo(ds.getLong("entrust_no"));
						realdeal.setFundAccount(ds.getString("fund_account"));
						realdeal.setCombineId(ds.getString("combine_id"));
						realdeal.setStockCode(ds.getString("stock_code"));
						realdeal.setExchangeType(ds.getString("exchange_type"));
						realdeal.setStockAccount(ds.getString("stock_account"));
						realdeal.setSeatNo(ds.getString("seat_no"));
						realdeal.setEntrustDirection(ds
								.getString("entrust_direction"));
						realdeal.setBusinessTime(ds.getLong("business_time"));
						realdeal.setBusinessAmount(ds
								.getDouble("business_amount"));
						realdeal.setBusinessPrice(ds
								.getDouble("business_price"));
						realdeal.setBusinessBalance(ds
								.getDouble("business_balance"));
						realdeal.setAmpayoffType(ds.getString("ampayoff_type"));
						realdeal.setTotalFare(ds.getDouble("total_fare"));
						realdeal.setYjFare(ds.getDouble("yj_fare"));
						realdeal.setGhFare(ds.getDouble("gh_fare"));
						realdeal.setYhFare(ds.getDouble("yh_far"));
						realdeal.setJyFare(ds.getDouble("jy_fare"));
						realdealList.add(realdeal);

					}
				}
			}
			// 同步发送到此结束

		} catch (T2SDKException e) {
			String details = userToken + "," + fundAccount + "," + combineId
					+ "," + exchangeType + "," + entrustDirection + ","
					+ beginBatchNo + "," + endBatchNo + "," + beginEntrustNo
					+ "," + endEntrustNo;
			EmailExceptionHandler.getInstance().HandleExceptionWithData(e,
					"恒生接口报错", className + "funcAmRealdealQry", details);
		}
		return realdealList;

	}

	/**
	 * 组合层现货可用数量查询
	 * 
	 * @param userToken
	 * @param fundAccount
	 * @param combineId
	 * @param exchangeType
	 * @param stockCode
	 * @param tradingDirection
	 *            暂时只支持2、4、5、U、T(非必填)
	 * @return @
	 */
	public Amount funcAmAmountQry(String userToken, String fundAccount,
			String combineId, String exchangeType, String stockCode,
			String tradingDirection) {

		Amount amount = new Amount();

		try {
			// 获取event
			IEvent event = getEventByAlias(FUNC_AM_AMOUNT_QRY,
					EventType.ET_REQUEST);
			// 往event中添加dataset
			IDataset dataset = DatasetService.getDefaultInstance().getDataset();
			dataset.addColumn("user_token");
			dataset.addColumn("fund_account");
			dataset.addColumn("combine_id");
			dataset.addColumn("exchange_type");
			dataset.addColumn("stock_code");
			dataset.addColumn("trading_direction");
			dataset.appendRow();
			dataset.updateString("user_token", userToken);
			dataset.updateString("fund_account", fundAccount);
			dataset.updateString("combine_id", combineId);
			dataset.updateString("exchange_type", exchangeType);
			dataset.updateString("stock_code", stockCode);
			dataset.updateString("trading_direction", tradingDirection);

			event.putEventData(dataset);
			// 同步发送如下
			IEvent rsp = client.sendReceive(event, 10000);
			// 先判断返回值
			if (rsp.getReturnCode() != EventReturnCode.I_OK) {
				String details = userToken + "," + fundAccount + ","
						+ combineId + "," + exchangeType + "," + stockCode
						+ "," + tradingDirection;
				List<String> pramas = Lists.newArrayList();
				pramas.add("com.tzdr.common.api.ihuyi.hundsun.HundsunJres.funcAmAmountQry");
				pramas.add("errorNo::" + rsp.getErrorNo() + ",errorInfo::"
						+ rsp.getErrorInfo() + ",details::" + details);
				sendEmail(pramas);
				log.warn(
						"com.tzdr.common.api.ihuyi.hundsun.HundsunJres.funcAmAmountQry,errorNo::{},errorInfo::{}",
						rsp.getErrorNo(), rsp.getErrorInfo());
			} else {
				// 获得结果集
				IDatasets result = rsp.getEventDatas();
				// 获得结果集总数
				int datasetCount = result.getDatasetCount();
				// 遍历所有的结果集
				if (datasetCount > 0) {
					IDataset ds = result.getDataset(0);
					String errorNo = ds.getString("error_no");
					if (SUCCESS.equals(errorNo)) {
						amount.setEnableAmount(ds.getDouble("enable_amount"));
						amount.setT1Amount(ds.getDouble("t1_amount"));
					}
				}
			}
			// 同步发送到此结束

		} catch (T2SDKException e) {
			String details = userToken + "," + fundAccount + "," + combineId
					+ "," + exchangeType + "," + stockCode + ","
					+ tradingDirection;
			EmailExceptionHandler.getInstance().HandleExceptionWithData(e,
					"恒生接口报错", className + "funcAmAmountQry", details);
		}
		return amount;

	}

	/**
	 * 组合层期货可用数量查询
	 * 
	 * @param userToken
	 * @param fundAccount
	 * @param combineId
	 * @param exchangeType
	 * @param stockCode
	 * @param investWay
	 * @return @
	 */
	public Futureamount funcAmFutureamountQry(String userToken,
			String fundAccount, String combineId, String exchangeType,
			String stockCode, String investWay) {

		Futureamount futureamount = new Futureamount();

		try {
			// 获取event
			IEvent event = getEventByAlias(FUNC_AM_FUTUREAMOUNT_QRY,
					EventType.ET_REQUEST);
			// 往event中添加dataset
			IDataset dataset = DatasetService.getDefaultInstance().getDataset();
			dataset.addColumn("user_token");
			dataset.addColumn("fund_account");
			dataset.addColumn("combine_id");
			dataset.addColumn("exchange_type");
			dataset.addColumn("stock_code");
			dataset.addColumn("invest_way");
			dataset.appendRow();
			dataset.updateString("user_token", userToken);
			dataset.updateString("fund_account", fundAccount);
			dataset.updateString("combine_id", combineId);
			dataset.updateString("exchange_type", exchangeType);
			dataset.updateString("stock_code", stockCode);
			dataset.updateString("invest_way", investWay);

			event.putEventData(dataset);
			// 同步发送如下
			IEvent rsp = client.sendReceive(event, 10000);
			// 先判断返回值
			if (rsp.getReturnCode() != EventReturnCode.I_OK) { // 返回错误
				String details = userToken + "," + fundAccount + ","
						+ combineId + "," + exchangeType + "," + stockCode
						+ "," + investWay;
				List<String> pramas = Lists.newArrayList();
				pramas.add("com.tzdr.common.api.ihuyi.hundsun.HundsunJres.funcAmFutureamountQry");
				pramas.add("errorNo::" + rsp.getErrorNo() + ",errorInfo::"
						+ rsp.getErrorInfo() + ",details::" + details);
				sendEmail(pramas);
				log.warn(
						"com.tzdr.common.api.ihuyi.hundsun.HundsunJres.funcAmFutureamountQry,errorNo::{},errorInfo::{}",
						rsp.getErrorNo(), rsp.getErrorInfo());
			} else {
				// 获得结果集
				IDatasets result = rsp.getEventDatas();
				// 获得结果集总数
				int datasetCount = result.getDatasetCount();
				// 遍历所有的结果集
				if (datasetCount > 0) {
					IDataset ds = result.getDataset(0);
					String errorNo = ds.getString("error_no");
					if (SUCCESS.equals(errorNo)) {
						futureamount.setLongCurrentAmount(ds
								.getLong("long_current_amount"));
						futureamount.setShortCurrentAmount(ds
								.getLong("short_current_amount"));
						futureamount.setLongAmount(ds.getLong("long_amount"));
						futureamount.setShortAmount(ds.getLong("short_amount"));
						futureamount.setYesLongAmount(ds
								.getLong("yes_long_amount"));
						futureamount.setYesShortAmount(ds
								.getLong("yes_short_amount"));
						futureamount.setExchangeType(ds
								.getString("exchange_type"));
						futureamount.setStockCode(ds.getString("stock_code"));
						futureamount.setInvestWay(ds.getString("invest_way"));
					}
				}
			}
			// 同步发送到此结束

		} catch (T2SDKException e) {
			String details = userToken + "," + fundAccount + "," + combineId
					+ "," + exchangeType + "," + stockCode + "," + investWay;
			EmailExceptionHandler.getInstance().HandleExceptionWithData(e,
					"恒生接口报错", className + "funcAmFutureamountQry", details);
		}
		return futureamount;

	}

	/**
	 * 历史成交查询
	 * 
	 * @param userToken
	 * @param initDate
	 * @param beginEntrustNo
	 * @param endEntrustNo
	 * @return @
	 */
	public List<Realdeal> funcAmRealdealHistoryQry(String userToken,
			long initDate, long beginEntrustNo, long endEntrustNo) {

		List<Realdeal> realdealList = Lists.newArrayList();

		try {
			// 获取event
			IEvent event = getEventByAlias(FUNC_AM_REALDEAL_HISTORY_QRY,
					EventType.ET_REQUEST);
			// 往event中添加dataset
			IDataset dataset = DatasetService.getDefaultInstance().getDataset();
			dataset.addColumn("user_token");
			dataset.addColumn("init_date");
			dataset.addColumn("begin_entrust_no");
			dataset.addColumn("end_entrust_no");
			dataset.appendRow();
			dataset.updateString("user_token", userToken);
			dataset.updateLong("init_date", initDate);
			dataset.updateLong("begin_entrust_no", beginEntrustNo);
			dataset.updateLong("end_entrust_no", endEntrustNo);
			event.putEventData(dataset);
			// 同步发送如下
			IEvent rsp = client.sendReceive(event, 10000);
			// 先判断返回值
			if (rsp.getReturnCode() != EventReturnCode.I_OK) { // 返回错误
				String details = userToken + "," + initDate + ","
						+ beginEntrustNo + "," + endEntrustNo;
				List<String> pramas = Lists.newArrayList();
				pramas.add("com.tzdr.common.api.ihuyi.hundsun.HundsunJres.funcAmRealdealHistoryQry");
				pramas.add("errorNo::" + rsp.getErrorNo() + ",errorInfo::"
						+ rsp.getErrorInfo() + ",details::" + details);
				sendEmail(pramas);
				log.warn(
						"com.tzdr.common.api.ihuyi.hundsun.HundsunJres.funcAmRealdealHistoryQry,errorNo::{},errorInfo::{}",
						rsp.getErrorNo(), rsp.getErrorInfo());
			} else {
				// 获得结果集
				IDatasets result = rsp.getEventDatas();
				// 获得结果集总数
				int datasetCount = result.getDatasetCount();
				// 遍历所有的结果集

				for (int i = 0; i < datasetCount; i++) {
					// 开始读取单个结果集的信息
					IDataset ds = result.getDataset(i);
					ds.beforeFirst();
					while (ds.hasNext()) {
						ds.next();
						Realdeal realdeal = new Realdeal();
						realdeal.setInitDate(ds.getLong("init_date"));
						realdeal.setPositionStr(ds.getString("position_str"));
						realdeal.setBusinessNo(ds.getLong("business_no"));
						// realdeal.setBatchNo(ds.getLong("batch_no"));
						realdeal.setEntrustNo(ds.getLong("entrust_no"));
						realdeal.setFundAccount(ds.getString("fund_account"));
						realdeal.setCombineId(ds.getString("combine_id"));
						realdeal.setStockCode(ds.getString("stock_code"));
						realdeal.setExchangeType(ds.getString("exchange_type"));
						realdeal.setStockAccount(ds.getString("stock_account"));
						realdeal.setSeatNo(ds.getString("seat_no"));
						realdeal.setEntrustDirection(ds
								.getString("entrust_direction"));
						realdeal.setBusinessTime(ds.getLong("business_time"));
						realdeal.setBusinessAmount(ds
								.getDouble("business_amount"));
						realdeal.setBusinessPrice(ds
								.getDouble("business_price"));
						realdeal.setBusinessBalance(ds
								.getDouble("business_balance"));
						realdeal.setAmpayoffType(ds.getString("ampayoff_type"));
						realdeal.setTotalFare(ds.getDouble("total_fare"));
						realdeal.setYjFare(ds.getDouble("yj_fare"));
						realdeal.setGhFare(ds.getDouble("gh_fare"));
						realdeal.setYhFare(ds.getDouble("yh_far"));
						realdeal.setJyFare(ds.getDouble("jy_fare"));
						realdealList.add(realdeal);

					}
				}
			}
			// 同步发送到此结束

		} catch (T2SDKException e) {
			String details = userToken + "," + initDate + "," + beginEntrustNo
					+ "," + endEntrustNo;
			EmailExceptionHandler.getInstance().HandleExceptionWithData(e,
					"恒生接口报错", className + "funcAmRealdealHistoryQry", details);
		}
		return realdealList;

	}

	/**
	 * 历史委托查询
	 * 
	 * @param userToken
	 * @param initDate
	 * @param beginBatchNo
	 * @param endBatchNo
	 * @param beginEntrustNo
	 * @param endEntrustNo
	 * @return @
	 */
	public List<Entrust> funcAmEntrustHistoryQry(String userToken,
			long initDate, long beginBatchNo, long endBatchNo,
			long beginEntrustNo, long endEntrustNo) {

		List<Entrust> entrustList = Lists.newArrayList();

		try {
			// 获取event
			IEvent event = getEventByAlias(FUNC_AM_ENTRUST_HISTORY_QRY,
					EventType.ET_REQUEST);
			// 往event中添加dataset
			IDataset dataset = DatasetService.getDefaultInstance().getDataset();
			dataset.addColumn("user_token");
			dataset.addColumn("init_date");

			dataset.addColumn("begin_batch_no");
			dataset.addColumn("end_batch_no");
			dataset.addColumn("begin_entrust_no");
			dataset.addColumn("end_entrust_no");
			dataset.appendRow();
			dataset.updateString("user_token", userToken);
			dataset.updateLong("init_date", initDate);
			dataset.updateLong("begin_batch_no", beginBatchNo);
			dataset.updateLong("end_batch_no", endBatchNo);
			dataset.updateLong("begin_entrust_no", beginEntrustNo);
			dataset.updateLong("end_entrust_no", endEntrustNo);
			event.putEventData(dataset);
			// 同步发送如下
			IEvent rsp = client.sendReceive(event, 10000);
			// 先判断返回值
			if (rsp.getReturnCode() != EventReturnCode.I_OK) { // 返回错误
				String details = userToken + "," + initDate + ","
						+ beginBatchNo + "," + endBatchNo + ","
						+ beginEntrustNo + "," + endEntrustNo;
				List<String> pramas = Lists.newArrayList();
				pramas.add("com.tzdr.common.api.ihuyi.hundsun.HundsunJres.funcAmEntrustHistoryQry");
				pramas.add("errorNo::" + rsp.getErrorNo() + ",errorInfo::"
						+ rsp.getErrorInfo() + ",details::" + details);
				sendEmail(pramas);
				log.warn(
						"com.tzdr.common.api.ihuyi.hundsun.HundsunJres.funcAmEntrustHistoryQry,errorNo::{},errorInfo::{}",
						rsp.getErrorNo(), rsp.getErrorInfo());
			} else {
				// 获得结果集
				IDatasets result = rsp.getEventDatas();
				// 获得结果集总数
				int datasetCount = result.getDatasetCount();
				// 遍历所有的结果集

				for (int i = 0; i < datasetCount; i++) {
					// 开始读取单个结果集的信息
					IDataset ds = result.getDataset(i);
					ds.beforeFirst();
					while (ds.hasNext()) {
						ds.next();
						Entrust entrust = new Entrust();
						entrust.setBatchNo(ds.getLong("batch_no"));
						entrust.setEntrustNo(ds.getLong("entrust_no"));
						entrust.setFundAccount(ds.getString("fund_account"));
						entrust.setCombineId(ds.getString("combine_id"));
						entrust.setInstructNo(ds.getLong("instruct_no"));
						entrust.setInstructmodNo(ds.getLong("instructmod_no"));
						entrust.setStockCode(ds.getString("stock_code"));
						entrust.setExchangeType(ds.getString("exchange_type"));
						entrust.setStockAccount(ds.getString("stock_account"));
						entrust.setSeatNo(ds.getString("seat_no"));
						entrust.setInvestWay(ds.getString("invest_way"));
						entrust.setEntrustDirection(ds
								.getString("entrust_direction"));
						entrust.setEntrustPrice(ds.getDouble("entrust_price"));
						entrust.setEntrustAmount(ds.getDouble("entrust_amount"));
						entrust.setEntrustTime(ds.getLong("entrust_time"));
						entrust.setReportTime(ds.getLong("report_time"));
						entrust.setBusinessAmount(ds
								.getDouble("business_amount"));
						entrust.setBusinessBalance(ds
								.getDouble("business_balance"));
						entrust.setWithdrawAmount(ds.getLong("withdraw_amount"));
						entrust.setAmentrustStatus(ds
								.getString("amentrust_status"));
						entrust.setCancelInfo(ds.getString("cancel_info"));
						entrustList.add(entrust);

					}
				}
			}
			// 同步发送到此结束

		} catch (T2SDKException e) {
			String details = userToken + "," + initDate + "," + beginBatchNo
					+ "," + endBatchNo + "," + beginEntrustNo + ","
					+ endEntrustNo;
			EmailExceptionHandler.getInstance().HandleExceptionWithData(e,
					"恒生接口报错", className + "funcAmEntrustHistoryQry", details);
		}
		return entrustList;

	}

	/**
	 * 组合信息查询
	 * 
	 * @param userToken
	 * @param fundAccount
	 * @param combineId
	 * @return @
	 */
	public List<Combinfo> funcAmCombinfoQry(String userToken,
			String fundAccount, String combineId) {

		List<Combinfo> combinfoList = Lists.newArrayList();

		try {
			// 获取event
			IEvent event = getEventByAlias(FUNC_AM_COMBINFO_QRY,
					EventType.ET_REQUEST);
			// 往event中添加dataset
			IDataset dataset = DatasetService.getDefaultInstance().getDataset();
			dataset.addColumn("user_token");
			dataset.addColumn("fund_account");
			dataset.addColumn("combine_id");

			dataset.appendRow();
			dataset.updateString("user_token", userToken);
			dataset.updateString("fund_account", fundAccount);
			dataset.updateString("combine_id", combineId);

			event.putEventData(dataset);
			// 同步发送如下
			IEvent rsp = client.sendReceive(event, 10000);
			// 先判断返回值
			if (rsp.getReturnCode() != EventReturnCode.I_OK) { // 返回错误
				String details = userToken + "," + fundAccount + ","
						+ combineId;
				List<String> pramas = Lists.newArrayList();
				pramas.add("com.tzdr.common.api.ihuyi.hundsun.HundsunJres.funcAmCombinfoQry");
				pramas.add("errorNo::" + rsp.getErrorNo() + ",errorInfo::"
						+ rsp.getErrorInfo() + ",details::" + details);
				sendEmail(pramas);
				log.warn(
						"com.tzdr.common.api.ihuyi.hundsun.HundsunJres.funcAmCombinfoQry,errorNo::{},errorInfo::{}",
						rsp.getErrorNo(), rsp.getErrorInfo());
			} else {
				// 获得结果集
				IDatasets result = rsp.getEventDatas();
				// 获得结果集总数
				int datasetCount = result.getDatasetCount();
				// 遍历所有的结果集

				for (int i = 0; i < datasetCount; i++) {
					// 开始读取单个结果集的信息
					IDataset ds = result.getDataset(i);
					ds.beforeFirst();
					while (ds.hasNext()) {
						ds.next();
						Combinfo combinfo = new Combinfo();
						combinfo.setFundAccount(ds.getString("fund_account"));
						combinfo.setClientName(ds.getString("client_name"));
						combinfo.setAssetunitName(ds
								.getString("assetunit_name"));
						combinfo.setCombineNo(ds.getLong("combine_no"));
						combinfo.setCombineId(ds.getString("combine_id"));
						combinfo.setCombineName(ds.getString("combine_name"));
						combinfo.setAssetId(ds.getLong("asset_id"));
						combinfoList.add(combinfo);
					}
				}
			}
			// 同步发送到此结束
		} catch (T2SDKException e) {
			String details = userToken + "," + fundAccount + "," + combineId;
			EmailExceptionHandler.getInstance().HandleExceptionWithData(e,
					"恒生接口报错", className + "funcAmCombinfoQry", details);
		}
		return combinfoList;
	}

	/**
	 * 修改单元信息
	 * 
	 * @param userToken
	 * @param fundAccount
	 * @param combineId
	 * @param assetName
	 * @param assetStatus
	 * @return @
	 */
	public boolean funcAmChangeAssetInfo(String userToken, String fundAccount,
			String combineId, String assetName, String assetStatus) {

		boolean change = Boolean.FALSE;

		try {
			// 获取event
			IEvent event = getEventByAlias(FUNC_AM_CHANGE_ASSET_INFO,
					EventType.ET_REQUEST);
			// 往event中添加dataset
			IDataset dataset = DatasetService.getDefaultInstance().getDataset();
			dataset.addColumn("user_token");
			dataset.addColumn("fund_account");
			dataset.addColumn("combine_id");
			dataset.addColumn("asset_name");
			dataset.addColumn("asset_status");
			dataset.appendRow();
			dataset.updateString("user_token", userToken);
			dataset.updateString("fund_account", fundAccount);
			dataset.updateString("combine_id", combineId);
			dataset.updateString("asset_name", assetName);
			dataset.updateString("asset_status", assetStatus);
			event.putEventData(dataset);
			// 同步发送如下
			IEvent rsp = client.sendReceive(event, 10000);

			// 先判断返回值
			if (rsp.getReturnCode() == EventReturnCode.I_OK) {
				// 获得结果集
				IDatasets result = rsp.getEventDatas();
				// 获得结果集总数
				int datasetCount = result.getDatasetCount();
				// 遍历所有的结果集
				if (datasetCount > 0) {
					IDataset ds = result.getDataset(0);
					String errorNo = ds.getString("error_no");
					if (SUCCESS.equals(errorNo)) {
						change = Boolean.TRUE;
					}
				}
			} else {
				String details = userToken + "," + fundAccount + ","
						+ combineId + "," + assetName + "," + assetStatus;
				List<String> pramas = Lists.newArrayList();
				pramas.add("com.tzdr.common.api.ihuyi.hundsun.HundsunJres.funcAmChangeAssetInfo");
				pramas.add("errorNo::" + rsp.getErrorNo() + ",errorInfo::"
						+ rsp.getErrorInfo() + ",details::" + details);
				sendEmail(pramas);
				log.warn(
						"com.tzdr.common.api.ihuyi.hundsun.HundsunJres.funcAmChangeAssetInfo,errorNo::{},errorInfo::{}",
						rsp.getErrorNo(), rsp.getErrorInfo());
			}
			// 同步发送到此结束
		} catch (T2SDKException e) {
			String details = userToken + "," + fundAccount + "," + combineId
					+ "," + assetName + "," + assetStatus;
			EmailExceptionHandler.getInstance().HandleExceptionWithData(e,
					"恒生接口报错", className + "funcAmChangeAssetInfo", details);
		}
		return change;

	}

	/**
	 * 组合资产查询
	 * 
	 * @param userToken
	 * @param fundAccount
	 * @param combineId
	 * @return @
	 */
	public List<Combasset> funcAmCombassetQry(String userToken,
			String fundAccount, String combineId) {

		List<Combasset> combassetList = Lists.newArrayList();

		try {
			// 获取event
			IEvent event = getEventByAlias(FUNC_AM_COMBASSET_QRY,
					EventType.ET_REQUEST);
			// 往event中添加dataset
			IDataset dataset = DatasetService.getDefaultInstance().getDataset();
			dataset.addColumn("user_token");
			dataset.addColumn("fund_account");
			dataset.addColumn("combine_id");

			dataset.appendRow();
			dataset.updateString("user_token", userToken);
			dataset.updateString("fund_account", fundAccount);
			dataset.updateString("combine_id", combineId);

			event.putEventData(dataset);
			// 同步发送如下
			IEvent rsp = client.sendReceive(event, 10000);
			// 先判断返回值
			if (rsp.getReturnCode() != EventReturnCode.I_OK) { // 返回错误
				String details = userToken + "," + fundAccount + ","
						+ combineId;
				List<String> pramas = Lists.newArrayList();
				pramas.add("com.tzdr.common.api.ihuyi.hundsun.HundsunJres.funcAmCombassetQry");
				pramas.add("errorNo::" + rsp.getErrorNo() + ",errorInfo::"
						+ rsp.getErrorInfo() + ",details::" + details);
				sendEmail(pramas);
				log.warn(
						"com.tzdr.common.api.ihuyi.hundsun.HundsunJres.funcAmCombassetQry,errorNo::{},errorInfo::{}",
						rsp.getErrorNo(), rsp.getErrorInfo());
			} else {
				// 获得结果集
				IDatasets result = rsp.getEventDatas();
				// 获得结果集总数
				int datasetCount = result.getDatasetCount();
				// 遍历所有的结果集
				for (int i = 0; i < datasetCount; i++) {
					// 开始读取单个结果集的信息
					IDataset ds = result.getDataset(i);
					ds.beforeFirst();
					while (ds.hasNext()) {
						ds.next();
						Combasset combasset = new Combasset();
						combasset.setFundAccount(ds.getString("fund_account"));
						combasset.setAssetId(ds.getLong("asset_id"));
						combasset.setCombineId(ds.getString("combine_id"));
						combasset.setAssetValue(ds.getDouble("asset_value"));
						combasset.setAssetTotalValue(ds
								.getDouble("asset_total_value"));
						combasset.setCurrentCash(ds.getDouble("current_cash"));
						combasset.setStockAsset(ds.getDouble("stock_asset"));
						combasset.setFundAsset(ds.getDouble("fund_asset"));
						combasset.setBondAsset(ds.getDouble("bond_asset"));
						combasset.setHgAsset(ds.getDouble("hg_asset"));
						combasset
								.setFuturesAsset(ds.getDouble("futures_asset"));
						combassetList.add(combasset);

					}
				}
			}
			// 同步发送到此结束
		} catch (T2SDKException e) {
			String details = userToken + "," + fundAccount + "," + combineId;
			EmailExceptionHandler.getInstance().HandleExceptionWithData(e,
					"恒生接口报错", className + "funcAmCombassetQry", details);
		}
		return combassetList;
	}

	/**
	 * 组合流水查询
	 * 
	 * @param userToken
	 * @param fundAccount
	 * @param combineId
	 * @return @
	 */
	public List<Current> funcAmCurrentQry(String userToken, String fundAccount,
			String combineId) {

		List<Current> currentList = Lists.newArrayList();

		try {
			// 获取event
			IEvent event = getEventByAlias(FUNC_AM_CURRENT_QRY,
					EventType.ET_REQUEST);
			// 往event中添加dataset
			IDataset dataset = DatasetService.getDefaultInstance().getDataset();
			dataset.addColumn("user_token");
			dataset.addColumn("fund_account");
			dataset.addColumn("combine_id");

			dataset.appendRow();
			dataset.updateString("user_token", userToken);
			dataset.updateString("fund_account", fundAccount);
			dataset.updateString("combine_id", combineId);

			event.putEventData(dataset);
			// 同步发送如下
			IEvent rsp = client.sendReceive(event, 10000);
			// 先判断返回值
			if (rsp.getReturnCode() != EventReturnCode.I_OK) {
				String details = userToken + "," + fundAccount + ","
						+ combineId;
				List<String> pramas = Lists.newArrayList();
				pramas.add("com.tzdr.common.api.ihuyi.hundsun.HundsunJres.funcAmCurrentQry");
				pramas.add("errorNo::" + rsp.getErrorNo() + ",errorInfo::"
						+ rsp.getErrorInfo() + ",details::" + details);
				sendEmail(pramas);
				log.warn(
						"com.tzdr.common.api.ihuyi.hundsun.HundsunJres.funcAmCurrentQry,errorNo::{},errorInfo::{}",
						rsp.getErrorNo(), rsp.getErrorInfo());
			} else {
				// 获得结果集
				IDatasets result = rsp.getEventDatas();
				// 获得结果集总数
				int datasetCount = result.getDatasetCount();
				// 遍历所有的结果集
				for (int i = 0; i < datasetCount; i++) {
					// 开始读取单个结果集的信息
					IDataset ds = result.getDataset(i);
					ds.beforeFirst();
					while (ds.hasNext()) {
						ds.next();
						Current current = new Current();
						current.setFundAccount(ds.getString("fund_account"));
						current.setCombineId(ds.getString("combine_id"));
						current.setDate(ds.getLong("date"));
						current.setOccurBalance(ds.getDouble("occur_balance"));
						current.setExchangeType(ds.getString("exchange_type"));
						current.setStockCode(ds.getString("stock_code"));
						current.setBusinOpflag(ds.getString("busin_opflag"));
						current.setBusinCaption(ds.getString("busin_caption"));
						current.setPostBalance(ds.getDouble("post_balance"));
						current.setBusinessPrice(ds.getDouble("business_price"));
						current.setBusinessAmount(ds.getLong("business_amount"));
						current.setTotalFare(ds.getDouble("total_fare"));
						current.setSerialNo(ds.getLong("serial_no"));
						current.setEntrustdirName(ds
								.getString("entrustdir_name"));
						currentList.add(current);

					}
				}
			}
			// 同步发送到此结束
		} catch (T2SDKException e) {
			String details = userToken + "," + fundAccount + "," + combineId;
			EmailExceptionHandler.getInstance().HandleExceptionWithData(e,
					"恒生接口报错", className + "funcAmCurrentQry", details);
		}
		return currentList;
	}

	/**
	 * 普通委托
	 * 
	 * @param userToken
	 * @param fundAccount
	 * @param combineId
	 * @param stockAccount
	 * @param seatNo
	 * @param exchangeType
	 * @param stockCode
	 * @param entrustDirection
	 * @param ampriceType
	 * @param entrustAmount
	 * @param entrustPrice
	 * @param investWay
	 * @param closeDirection
	 * @return @
	 */
	public EntrustResult funcAmEntrust(String userToken, String fundAccount,
			String combineId, String stockAccount, String seatNo,
			String exchangeType, String stockCode, String entrustDirection,
			String ampriceType, double entrustAmount, double entrustPrice,
			String investWay, String closeDirection) {

		EntrustResult entrustResult = new EntrustResult();

		try {
			// 获取event
			IEvent event = getEventByAlias(FUNC_AM_ENTRUST,
					EventType.ET_REQUEST);
			// 往event中添加dataset
			IDataset dataset = DatasetService.getDefaultInstance().getDataset();
			dataset.addColumn("user_token");
			dataset.addColumn("fund_account");
			dataset.addColumn("combine_id");
			dataset.addColumn("stock_account");
			dataset.addColumn("seat_no");
			dataset.addColumn("exchange_type");
			dataset.addColumn("stock_code");
			dataset.addColumn("entrust_direction");
			dataset.addColumn("amprice_type");
			dataset.addColumn("entrust_amount");
			dataset.addColumn("entrust_price");
			dataset.addColumn("invest_way");
			dataset.addColumn("close_direction");
			dataset.appendRow();
			dataset.updateString("user_token", userToken);
			dataset.updateString("fund_account", fundAccount);
			dataset.updateString("combine_id", combineId);
			dataset.updateString("stock_account", stockAccount);
			dataset.updateString("seat_no", seatNo);
			dataset.updateString("exchange_type", exchangeType);
			dataset.updateString("stock_code", stockCode);
			dataset.updateString("entrust_direction", entrustDirection);
			dataset.updateString("amprice_type", ampriceType);
			dataset.updateDouble("entrust_amount", entrustAmount);
			dataset.updateDouble("entrust_price", entrustPrice);
			dataset.updateString("invest_way", investWay);
			dataset.updateString("close_direction", closeDirection);
			event.putEventData(dataset);
			// 同步发送如下
			IEvent rsp = client.sendReceive(event, 10000);
			// 先判断返回值
			if (rsp.getReturnCode() != EventReturnCode.I_OK) { // 返回错误
				String details = userToken + "," + fundAccount + ","
						+ combineId + "," + stockAccount + "," + seatNo + ","
						+ exchangeType + "," + stockCode + ","
						+ entrustDirection + "," + ampriceType + ","
						+ entrustAmount + "," + entrustPrice + "," + investWay
						+ "," + closeDirection;
				List<String> pramas = Lists.newArrayList();
				pramas.add("com.tzdr.common.api.ihuyi.hundsun.HundsunJres.funcAmEntrust");
				pramas.add("errorNo::" + rsp.getErrorNo() + ",errorInfo::"
						+ rsp.getErrorInfo() + ",details::" + details);
				sendEmail(pramas);
				log.warn(
						"com.tzdr.common.api.ihuyi.hundsun.HundsunJres.funcAmEntrust,errorNo::{},errorInfo::{}",
						rsp.getErrorNo(), rsp.getErrorInfo());
			} else {
				// 获得结果集
				IDatasets result = rsp.getEventDatas();
				// 获得结果集总数
				int datasetCount = result.getDatasetCount();
				// 遍历所有的结果集
				if (datasetCount > 0) {
					IDataset ds = result.getDataset(0);
					String errorNo = ds.getString("error_no");
					if (SUCCESS.equals(errorNo)) {
						entrustResult.setBatchNo(ds.getLong("batch_no"));
						entrustResult.setEntrustNo(ds.getLong("entrust_no"));
						entrustResult.setSuccess(true);
					} else {
						entrustResult.setSuccess(false);
					}
				}
			}
			// 同步发送到此结束
		} catch (T2SDKException e) {
			String details = userToken + "," + fundAccount + "," + combineId
					+ "," + stockAccount + "," + seatNo + "," + exchangeType
					+ "," + stockCode + "," + entrustDirection + ","
					+ ampriceType + "," + entrustAmount + "," + entrustPrice
					+ "," + investWay + "," + closeDirection;
			EmailExceptionHandler.getInstance().HandleExceptionWithData(e,
					"恒生接口报错", className + "funcAmEntrust", details);
		}
		return entrustResult;
	}

	/**
	 * @param userToken
	 * @param date
	 * @param capitalAccount
	 * @param stockholderId
	 * @param seatId
	 * @return @
	 */
	public List<BranchLiquidate> funcBranchLiquidateQry(String userToken,
			long date, String capitalAccount, String stockholderId,
			String seatId) {

		List<BranchLiquidate> branchLiquidateList = Lists.newArrayList();

		try {
			// 获取event
			IEvent event = getEventByAlias(FUNC_BRANCH_LIQUIDATE_QRY,
					EventType.ET_REQUEST);
			// 往event中添加dataset
			IDataset dataset = DatasetService.getDefaultInstance().getDataset();
			dataset.addColumn("user_token");
			dataset.addColumn("date");
			dataset.addColumn("capital_account");
			dataset.addColumn("stockholder_id");
			dataset.addColumn("seat_id");
			dataset.appendRow();
			dataset.updateString("user_token", userToken);
			dataset.updateLong("date", date);
			dataset.updateString("capital_account", capitalAccount);
			dataset.updateString("stockholder_id", stockholderId);
			dataset.updateString("seat_id", seatId);
			event.putEventData(dataset);
			// 同步发送如下
			IEvent rsp = client.sendReceive(event, 10000);
			// 先判断返回值
			if (rsp.getReturnCode() != EventReturnCode.I_OK) {
				String details = userToken + "," + date + "," + capitalAccount
						+ "," + stockholderId + "," + seatId;
				List<String> pramas = Lists.newArrayList();
				pramas.add("com.tzdr.common.api.ihuyi.hundsun.HundsunJres.funcBranchLiquidateQry");
				pramas.add("errorNo::" + rsp.getErrorNo() + ",errorInfo::"
						+ rsp.getErrorInfo() + ",details::" + details);
				sendEmail(pramas);
				log.warn(
						"com.tzdr.common.api.ihuyi.hundsun.HundsunJres.funcBranchLiquidateQry,errorNo::{},errorInfo::{}",
						rsp.getErrorNo(), rsp.getErrorInfo());
			} else {
				// 获得结果集
				IDatasets result = rsp.getEventDatas();
				// 获得结果集总数
				int datasetCount = result.getDatasetCount();
				// 遍历所有的结果集
				for (int i = 0; i < datasetCount; i++) {
					// 开始读取单个结果集的信息
					IDataset ds = result.getDataset(i);
					ds.beforeFirst();
					while (ds.hasNext()) {
						ds.next();
						BranchLiquidate branchLiquidate = new BranchLiquidate();
						branchLiquidate.setDate(ds.getLong("date"));
						branchLiquidate.setCapitalAccount(ds
								.getString("capital_account"));
						branchLiquidate.setCurrencyNo(ds
								.getString("currency_no"));
						branchLiquidate.setStockholderId(ds
								.getString("stockholder_id"));
						branchLiquidate.setSeatId(ds.getString("seat_id"));
						branchLiquidate.setReportCode(ds
								.getString("report_code"));
						branchLiquidate
								.setStockName(ds.getString("stock_name"));
						branchLiquidate.setBusinessPrice(ds
								.getDouble("business_price"));
						branchLiquidate.setBusinessFlag(ds
								.getLong("business_flag"));
						branchLiquidate.setBusinessName(ds
								.getString("business_name"));
						branchLiquidate.setBusinessType(ds
								.getString("business_type"));
						branchLiquidate.setTypeName(ds.getString("type_name"));
						branchLiquidate.setExchangeType(ds
								.getString("exchange_type"));
						branchLiquidate.setExchangeName(ds
								.getString("exchange_name"));
						branchLiquidate
								.setEntrustBs(ds.getString("entrust_bs"));
						branchLiquidate.setBsName(ds.getString("bs_name"));
						branchLiquidate.setOccurAmount(ds
								.getDouble("occur_amount"));
						branchLiquidate.setBusinessBalance(ds
								.getDouble("business_balance"));
						branchLiquidate.setOccurBalance(ds
								.getDouble("occur_balance"));
						branchLiquidate.setPostBalance(ds
								.getDouble("post_balance"));
						branchLiquidate.setPostAmount(ds
								.getDouble("post_amount"));
						branchLiquidate.setReportSerialNo(ds
								.getLong("report_serial_no"));
						branchLiquidate.setDealNo(ds.getString("deal_no"));
						branchLiquidate
								.setReportTime(ds.getLong("report_time"));
						branchLiquidate.setBusinessTime(ds
								.getLong("business_time"));
						branchLiquidate.setFare0(ds.getDouble("fare0"));
						branchLiquidate.setFare1(ds.getDouble("fare1"));
						branchLiquidate.setFare2(ds.getDouble("fare2"));
						branchLiquidate.setFare3(ds.getDouble("fare3"));
						branchLiquidate.setFarex(ds.getDouble("farex"));
						branchLiquidate.setRemark(ds.getString("remark"));
						branchLiquidateList.add(branchLiquidate);

					}
				}
			}
			// 同步发送到此结束
		} catch (T2SDKException e) {
			String details = userToken + "," + date + "," + capitalAccount
					+ "," + stockholderId + "," + seatId;
			EmailExceptionHandler.getInstance().HandleExceptionWithData(e,
					"恒生接口报错", className + "funcBranchLiquidateQry", details);
		}
		return branchLiquidateList;
	}

	/**
	 * 委托撤销
	 * 
	 * @param userToken
	 * @param entrustNo
	 * @return @
	 */
	public boolean funcAmEntrustWithdraw(String userToken, String entrustNo) {
		boolean withdraw = Boolean.FALSE;
		try {
			// 获取event
			IEvent event = getEventByAlias(FUNC_AM_ENTRUST_WITHDRAW,
					EventType.ET_REQUEST);
			// 往event中添加dataset
			IDataset dataset = DatasetService.getDefaultInstance().getDataset();
			dataset.addColumn("user_token");
			dataset.addColumn("entrust_no");
			dataset.appendRow();
			dataset.updateString("user_token", userToken);
			dataset.updateString("entrust_no", entrustNo);
			event.putEventData(dataset);
			// 同步发送如下
			IEvent rsp = client.sendReceive(event, 10000);
			// 先判断返回值
			if (rsp.getReturnCode() == EventReturnCode.I_OK) { // 返回错误
				// 获得结果集
				IDatasets result = rsp.getEventDatas();
				// 获得结果集总数
				int datasetCount = result.getDatasetCount();
				// 遍历所有的结果集
				if (datasetCount > 0) {
					IDataset ds = result.getDataset(0);
					String errorNo = ds.getString("error_no");
					if (SUCCESS.equals(errorNo)) {
						withdraw = Boolean.TRUE;
					}
				}
			} else {
				String details = userToken + "," + entrustNo;
				List<String> pramas = Lists.newArrayList();
				pramas.add("com.tzdr.common.api.ihuyi.hundsun.HundsunJres.funcAmEntrustWithdraw");
				pramas.add("errorNo::" + rsp.getErrorNo() + ",errorInfo::"
						+ rsp.getErrorInfo() + ",details::" + details);
				sendEmail(pramas);
				log.warn(
						"com.tzdr.common.api.ihuyi.hundsun.HundsunJres.funcAmEntrustWithdraw,errorNo::{},errorInfo::{}",
						rsp.getErrorNo(), rsp.getErrorInfo());
			}
			// 同步发送到此结束
		} catch (T2SDKException e) {
			String details = userToken + "," + entrustNo;
			EmailExceptionHandler.getInstance().HandleExceptionWithData(e,
					"恒生接口报错", className + "funcAmEntrustWithdraw", details);
		}
		return withdraw;
	}

	/**
	 * 借贷信息查询
	 * 
	 * @param userToken
	 * @return
	 */
	public List<FutureLoan> funcAmFutureLoadQry(String userToken) {

		List<FutureLoan> futureLoanList = Lists.newArrayList();

		try {
			// 获取event
			IEvent event = getEventByAlias(FUNC_AM_FUTURE_LOAD_QRY,
					EventType.ET_REQUEST);
			// 往event中添加dataset
			IDataset dataset = DatasetService.getDefaultInstance().getDataset();
			dataset.addColumn("user_token");
			dataset.appendRow();
			dataset.updateString("user_token", userToken);
			event.putEventData(dataset);
			// 同步发送如下
			IEvent rsp = client.sendReceive(event, 10000);
			// 先判断返回值
			if (rsp.getReturnCode() != EventReturnCode.I_OK) {
				String details = userToken;
				List<String> pramas = Lists.newArrayList();
				pramas.add("com.tzdr.common.api.ihuyi.hundsun.HundsunJres.funcAmFutureLoadQry");
				pramas.add("errorNo::" + rsp.getErrorNo() + ",errorInfo::"
						+ rsp.getErrorInfo() + ",details::" + details);
				sendEmail(pramas);
				log.warn(
						"com.tzdr.common.api.ihuyi.hundsun.HundsunJres.funcAmFutureLoadQry,errorNo::{},errorInfo::{}",
						rsp.getErrorNo(), rsp.getErrorInfo());
			} else {
				// 获得结果集
				IDatasets result = rsp.getEventDatas();
				// 获得结果集总数
				int datasetCount = result.getDatasetCount();
				// 遍历所有的结果集
				for (int i = 0; i < datasetCount; i++) {
					// 开始读取单个结果集的信息
					IDataset ds = result.getDataset(i);
					ds.beforeFirst();
					while (ds.hasNext()) {
						ds.next();
						FutureLoan futureLoan = new FutureLoan();
						futureLoan.setBorrowerName(ds
								.getString("borrower_name"));
						futureLoan.setBeginAsset(ds.getString("begin_asset"));
						futureLoan.setOperatorNo(ds.getLong("operator_no"));
						futureLoan.setAssetId(ds.getDouble("asset_id"));
						futureLoan.setAssetName(ds.getString("asset_name"));
						futureLoan.setFutureOpright(ds
								.getString("future_opright"));
						futureLoan.setWarningValue(ds
								.getDouble("warning_value"));
						futureLoan.setStopValue(ds.getDouble("stop_value"));
						futureLoan.setCurLoan(ds.getDouble("cur_loan"));
						futureLoan.setRiskRatio(ds.getDouble("risk_ratio"));
						futureLoan.setCombieId(ds.getString("combie_id"));
						futureLoanList.add(futureLoan);

					}
				}
			}
			// 同步发送到此结束
		} catch (T2SDKException e) {
			String details = userToken;
			EmailExceptionHandler.getInstance().HandleExceptionWithData(e,
					"恒生接口报错", className + "funcAmFutureLoadQry", details);
		}
		return futureLoanList;
	}

	/**
	 * 修改操作员信息
	 * 
	 * @param userToken
	 * @param operatorNo
	 * @param businOpflag
	 * @param operatorName
	 * @param expireDay
	 * @param forceChgpassword
	 * @param stockOpright
	 * @param futureOpright
	 * @return
	 */
	public boolean funcAmChangeOperatorInfo(String userToken, long operatorNo,
			String businOpflag, String operatorName, long expireDay,
			String forceChgpassword, String stockOpright, String futureOpright) {
		log.info("修改操作员信息:" + "userToken:" + userToken + "operatorNo:"
				+ operatorNo + "businOpflag:" + businOpflag + "operatorName:"
				+ operatorName + "expireDay:" + expireDay + "forceChgpassword:"
				+ forceChgpassword + "stockOpright:" + stockOpright
				+ "futureOpright:" + futureOpright);
		boolean changeOperator = Boolean.FALSE;
		try {
			// 获取event
			IEvent event = getEventByAlias(FUNC_AM_CHANGE_OPERATOR_INFO,
					EventType.ET_REQUEST);
			// 往event中添加dataset
			IDataset dataset = DatasetService.getDefaultInstance().getDataset();
			dataset.addColumn("user_token");
			dataset.addColumn("operator_no");
			dataset.addColumn("busin_opflag");
			dataset.addColumn("operator_name");
			dataset.addColumn("expire_day");
			dataset.addColumn("force_chgpassword");
			dataset.addColumn("stock_opright");
			dataset.addColumn("future_opright");
			dataset.appendRow();
			dataset.updateString("user_token", userToken);
			dataset.updateLong("operator_no", operatorNo);
			dataset.updateString("busin_opflag", businOpflag);
			dataset.updateString("operator_name", operatorName);
			dataset.updateLong("expire_day", expireDay);
			dataset.updateString("force_chgpassword", forceChgpassword);
			dataset.updateString("stock_opright", stockOpright);
			dataset.updateString("future_opright", futureOpright);
			event.putEventData(dataset);
			// 同步发送如下
			IEvent rsp = client.sendReceive(event, 10000);
			// 先判断返回值
			if (rsp.getReturnCode() == EventReturnCode.I_OK) { // 返回错误
				// 获得结果集
				IDatasets result = rsp.getEventDatas();
				// 获得结果集总数
				int datasetCount = result.getDatasetCount();
				// 遍历所有的结果集
				if (datasetCount > 0) {
					IDataset ds = result.getDataset(0);
					String errorNo = ds.getString("error_no");
					log.info("修改操作员信息成功后返回数据:errorNo="+errorNo+",errorInfo"+ds.getString("error_info"));
					if (SUCCESS.equals(errorNo)) {
						changeOperator = Boolean.TRUE;
					}
				}
			} else {
				String details = userToken + "," + operatorNo + ","
						+ businOpflag + "," + operatorName + "," + expireDay
						+ "," + forceChgpassword + "," + stockOpright + ","
						+ futureOpright;
				List<String> pramas = Lists.newArrayList();
				pramas.add("com.tzdr.common.api.ihuyi.hundsun.HundsunJres.funcAmChangeOperatorInfo");
				pramas.add("errorNo::" + rsp.getErrorNo() + ",errorInfo::"
						+ rsp.getErrorInfo() + ",details::" + details);
				sendEmail(pramas);
				log.warn(
						"com.tzdr.common.api.ihuyi.hundsun.HundsunJres.funcAmChangeOperatorInfo,errorNo::{},errorInfo::{}",
						rsp.getErrorNo(), rsp.getErrorInfo());
			}
			// 同步发送到此结束
		} catch (T2SDKException e) {
			String details = userToken + "," + operatorNo + "," + businOpflag
					+ "," + operatorName + "," + expireDay + ","
					+ forceChgpassword + "," + stockOpright + ","
					+ futureOpright;
			EmailExceptionHandler.getInstance().HandleExceptionWithData(e,
					"恒生接口报错", className + "funcAmChangeOperatorInfo", details);
		}
		return changeOperator;
	}

	/**
	 * 公司层已设置风控信息查询
	 * 
	 * @param userToken
	 * @param serialNo
	 * @return
	 */
	public List<RiskControl> funcAmRiskcontrolQry(String userToken,
			String serialNo) {

		List<RiskControl> riskControlList = Lists.newArrayList();

		try {
			// 获取event
			IEvent event = getEventByAlias(FUNC_AM_RISKCONTROL_QRY,
					EventType.ET_REQUEST);
			// 往event中添加dataset
			IDataset dataset = DatasetService.getDefaultInstance().getDataset();
			dataset.addColumn("user_token");
			dataset.addColumn("serial_no");
			dataset.appendRow();
			dataset.updateString("user_token", userToken);
			dataset.updateString("serial_no", serialNo);
			event.putEventData(dataset);
			// 同步发送如下
			IEvent rsp = client.sendReceive(event, 10000);
			// 先判断返回值
			if (rsp.getReturnCode() != EventReturnCode.I_OK) {
				String details = userToken + "," + serialNo;
				List<String> pramas = Lists.newArrayList();
				pramas.add("com.tzdr.common.api.ihuyi.hundsun.HundsunJres.funcAmRiskcontrolQry");
				pramas.add("errorNo::" + rsp.getErrorNo() + ",errorInfo::"
						+ rsp.getErrorInfo() + ",details::" + details);
				sendEmail(pramas);
				log.warn(
						"com.tzdr.common.api.ihuyi.hundsun.HundsunJres.funcAmRiskcontrolQry,errorNo::{},errorInfo::{}",
						rsp.getErrorNo(), rsp.getErrorInfo());
			} else {
				// 获得结果集
				IDatasets result = rsp.getEventDatas();
				// 获得结果集总数
				int datasetCount = result.getDatasetCount();
				// 遍历所有的结果集
				for (int i = 0; i < datasetCount; i++) {
					// 开始读取单个结果集的信息
					IDataset ds = result.getDataset(i);
					ds.beforeFirst();
					while (ds.hasNext()) {
						ds.next();
						RiskControl riskControl = new RiskControl();
						riskControl.setSerialNo(ds.getLong("serial_no"));
						riskControl.setDate(ds.getLong("date"));
						riskControl.setRiskType(ds.getString("risk_type"));
						riskControl.setSubRiskType(ds
								.getString("sub_risk_type"));
						riskControl.setControlLayer(ds
								.getString("control_layer"));
						riskControl.setFundIds(ds.getString("fund_ids"));
						riskControl.setLayerIds(ds.getString("layer_ids"));
						riskControl.setValveType(ds.getString("valve_type"));
						riskControl
								.setControlType(ds.getString("control_type"));
						riskControl
								.setFundCollect(ds.getString("fund_collect"));
						riskControl.setStockCollect(ds
								.getString("stock_collect"));
						riskControl.setRiskLevel(ds.getLong("risk_level"));
						riskControl
								.setEffectRange(ds.getString("effect_range"));
						riskControl.setCompareDirection(ds
								.getString("compare_direction"));
						riskControl.setEnValue1(ds.getDouble("en_value1"));
						riskControl.setValueOpertion1(ds
								.getString("value_opertion1"));
						riskControl.setEnValue2(ds.getDouble("en_value2"));
						riskControl.setValueOpertion2(ds
								.getString("value_opertion2"));
						riskControl.setEnValue3(ds.getDouble("en_value3"));
						riskControl.setValueOpertion3(ds
								.getString("value_opertion3"));
						riskControl.setControlSwitch(ds
								.getString("control_switch"));
						riskControl.setModifyDate(ds.getLong("modify_date"));
						riskControl.setModifyTime(ds.getLong("modify_time"));
						riskControl.setModifyOperator(ds
								.getLong("modify_operator"));
						riskControl.setControlSummary(ds
								.getString("control_summary"));
						riskControl
								.setMessageHint(ds.getString("message_hint"));
						riskControl.setRemark(ds.getString("remark"));
						riskControl.setCompanyNo(ds.getLong("company_no"));
						riskControlList.add(riskControl);

					}
				}
			}
			// 同步发送到此结束
		} catch (T2SDKException e) {
			String details = userToken + "," + serialNo;
			EmailExceptionHandler.getInstance().HandleExceptionWithData(e,
					"恒生接口报错", className + "funcAmRiskcontrolQry", details);
		}
		return riskControlList;
	}

	/**
	 * 操作员信息查询
	 * 
	 * @param userToken
	 * @param operatorNo
	 * @param operatorStatus
	 * @return
	 */
	public List<OperatorInfo> funcAmOperatorInfoQry(String userToken,
			long operatorNo, String operatorStatus) {

		List<OperatorInfo> operatorInfoList = Lists.newArrayList();

		try {
			// 获取event
			IEvent event = getEventByAlias(FUNC_AM_OPERATOR_INFO_QRY,
					EventType.ET_REQUEST);
			// 往event中添加dataset
			IDataset dataset = DatasetService.getDefaultInstance().getDataset();
			dataset.addColumn("user_token");
			dataset.addColumn("operator_no");
			dataset.addColumn("operator_status");
			dataset.appendRow();
			dataset.updateString("user_token", userToken);
			dataset.updateLong("operator_no", operatorNo);
			dataset.updateString("operator_status", operatorStatus);
			event.putEventData(dataset);
			// 同步发送如下
			IEvent rsp = client.sendReceive(event, 10000);
			// 先判断返回值
			if (rsp.getReturnCode() != EventReturnCode.I_OK) {
				String details = userToken + "," + operatorNo + ","
						+ operatorStatus;
				List<String> pramas = Lists.newArrayList();
				pramas.add("com.tzdr.common.api.ihuyi.hundsun.HundsunJres.funcAmOperatorInfoQry");
				pramas.add("errorNo::" + rsp.getErrorNo() + ",errorInfo::"
						+ rsp.getErrorInfo() + ",details::" + details);
				sendEmail(pramas);
				log.warn(
						"com.tzdr.common.api.ihuyi.hundsun.HundsunJres.funcAmOperatorInfoQry,errorNo::{},errorInfo::{}",
						rsp.getErrorNo(), rsp.getErrorInfo());
			} else {
				// 获得结果集
				IDatasets result = rsp.getEventDatas();
				// 获得结果集总数
				int datasetCount = result.getDatasetCount();
				// 遍历所有的结果集
				for (int i = 0; i < datasetCount; i++) {
					// 开始读取单个结果集的信息
					IDataset ds = result.getDataset(i);
					ds.beforeFirst();
					while (ds.hasNext()) {
						ds.next();
						OperatorInfo operatorInfo = new OperatorInfo();
						operatorInfo.setOperatorNo(ds.getLong("operator_no"));
						operatorInfo.setOperatorName(ds
								.getString("operator_name"));
						operatorInfo.setOperatorStatus(ds
								.getString("operator_status"));
						operatorInfo.setExpireDays(ds.getLong("expire_days"));
						operatorInfo.setForceChgPassword(ds
								.getString("force_chg_password"));
						operatorInfo.setStockOpright(ds
								.getString("stock_opright"));
						operatorInfo.setFutureOpright(ds
								.getString("future_opright"));
						operatorInfoList.add(operatorInfo);

					}
				}
			}
			// 同步发送到此结束
		} catch (T2SDKException e) {
			String details = userToken + "," + operatorNo + ","
					+ operatorStatus;
			EmailExceptionHandler.getInstance().HandleExceptionWithData(e,
					"恒生接口报错", className + "funcAmOperatorInfoQry", details);
		}
		return operatorInfoList;
	}

	/**
	 * 账户操作权限查询
	 * 
	 * @param userToken
	 * @param operatorNo
	 * @param fundAccount
	 * @param combineId
	 * @return
	 */
	public List<OperatorRight> funcAmOperatorRightQry(String userToken,
			long operatorNo, String fundAccount, String combineId) {

		List<OperatorRight> operatorRightList = Lists.newArrayList();

		try {
			// 获取event
			IEvent event = getEventByAlias(FUNC_AM_OPERATOR_RIGHT_QRY,
					EventType.ET_REQUEST);
			// 往event中添加dataset
			IDataset dataset = DatasetService.getDefaultInstance().getDataset();
			dataset.addColumn("user_token");
			dataset.addColumn("operator_no");
			dataset.addColumn("fund_account");
			dataset.addColumn("combine_id");
			dataset.appendRow();
			dataset.updateString("user_token", userToken);
			dataset.updateLong("operator_no", operatorNo);
			dataset.updateString("fund_account", fundAccount);
			dataset.updateString("combine_id", combineId);
			event.putEventData(dataset);
			// 同步发送如下
			IEvent rsp = client.sendReceive(event, 10000);
			// 先判断返回值
			if (rsp.getReturnCode() != EventReturnCode.I_OK) {
				String details = userToken + "," + operatorNo + ","
						+ fundAccount + "," + combineId;
				List<String> pramas = Lists.newArrayList();
				pramas.add("com.tzdr.common.api.ihuyi.hundsun.HundsunJres.funcAmOperatorRightQry");
				pramas.add("errorNo::" + rsp.getErrorNo() + ",errorInfo::"
						+ rsp.getErrorInfo() + ",details::" + details);
				sendEmail(pramas);
				log.warn(
						"com.tzdr.common.api.ihuyi.hundsun.HundsunJres.funcAmOperatorRightQry,errorNo::{},errorInfo::{}",
						rsp.getErrorNo(), rsp.getErrorInfo());
			} else {
				// 获得结果集
				IDatasets result = rsp.getEventDatas();
				// 获得结果集总数
				int datasetCount = result.getDatasetCount();
				// 遍历所有的结果集
				for (int i = 0; i < datasetCount; i++) {
					// 开始读取单个结果集的信息
					IDataset ds = result.getDataset(i);
					ds.beforeFirst();
					while (ds.hasNext()) {
						ds.next();
						OperatorRight operatorRight = new OperatorRight();
						operatorRight.setFundAccount(ds
								.getString("fund_account"));
						operatorRight.setCombineId(ds.getString("combine_id"));
						operatorRight.setAssetId(ds.getLong("asset_id"));
						operatorRight.setAssetName(ds.getString("asset_name"));
						operatorRight.setRights(ds.getString("rights"));
						operatorRight
								.setFundStatus(ds.getString("fund_status"));
						operatorRight.setFundName(ds.getString("fund_name"));
						operatorRightList.add(operatorRight);

					}
				}
			}
			// 同步发送到此结束
		} catch (T2SDKException e) {
			String details = userToken + "," + operatorNo + "," + fundAccount
					+ "," + combineId;
			EmailExceptionHandler.getInstance().HandleExceptionWithData(e,
					"恒生接口报错", className + "funcAmOperatorRightQry", details);
		}
		return operatorRightList;
	}

	/**
	 * 修改账户操作权限
	 * 
	 * @param userToken
	 * @param operatorNo
	 * @param fundAccount
	 * @param combineId
	 * @param assetId
	 * @param rights
	 * @return
	 */
	public boolean funcAmChangeAssetRight(String userToken, long operatorNo,
			String fundAccount, String combineId, long assetId, String rights) {
		boolean changeAssetRight = Boolean.FALSE;
		log.info("修改账户操作权限:" + "userToken:" + userToken + "operatorNo:"
				+ operatorNo + "fundAccount:" + fundAccount + "combineId:"
				+ combineId + "assetId:" + assetId + "rights:" + rights);
		try {
			// 获取event
			IEvent event = getEventByAlias(FUNC_AM_CHANGE_ASSET_RIGHT,
					EventType.ET_REQUEST);
			// 往event中添加dataset
			IDataset dataset = DatasetService.getDefaultInstance().getDataset();
			dataset.addColumn("user_token");
			dataset.addColumn("operator_no");
			dataset.addColumn("fund_account");
			dataset.addColumn("combine_id");
			dataset.addColumn("asset_id");
			dataset.addColumn("rights");
			dataset.appendRow();
			dataset.updateString("user_token", userToken);
			dataset.updateLong("operator_no", operatorNo);
			dataset.updateString("fund_account", fundAccount);
			dataset.updateString("combine_id", combineId);
			dataset.updateLong("asset_id", assetId);
			dataset.updateString("rights", rights);
			event.putEventData(dataset);
			// 同步发送如下
			IEvent rsp = client.sendReceive(event, 10000);
			// 先判断返回值
			if (rsp.getReturnCode() == EventReturnCode.I_OK) { // 返回错误
				// 获得结果集
				IDatasets result = rsp.getEventDatas();
				// 获得结果集总数
				int datasetCount = result.getDatasetCount();
				// 遍历所有的结果集
				if (datasetCount > 0) {
					IDataset ds = result.getDataset(0);
					String errorNo = ds.getString("error_no");
					if (SUCCESS.equals(errorNo)) {
						changeAssetRight = Boolean.TRUE;
					}
				}
			} else {
				String details = userToken + "," + operatorNo + ","
						+ fundAccount + "," + combineId + "," + assetId + ","
						+ rights;
				List<String> pramas = Lists.newArrayList();
				pramas.add("com.tzdr.common.api.ihuyi.hundsun.HundsunJres.funcAmChangeAssetRight");
				pramas.add("errorNo::" + rsp.getErrorNo() + ",errorInfo::"
						+ rsp.getErrorInfo() + ",details::" + details);
				sendEmail(pramas);
				log.warn(
						"com.tzdr.common.api.ihuyi.hundsun.HundsunJres.funcAmChangeAssetRight,errorNo::{},errorInfo::{}",
						rsp.getErrorNo(), rsp.getErrorInfo());
			}
			// 同步发送到此结束
		} catch (T2SDKException e) {
			String details = userToken + "," + operatorNo + "," + fundAccount
					+ "," + combineId + "," + assetId + "," + rights;
			EmailExceptionHandler.getInstance().HandleExceptionWithData(e,
					"恒生接口报错", className + "funcAmChangeAssetRight", details);
		}
		return changeAssetRight;
	}

	/**
	 * 股票划转
	 * 
	 * @param userToken
	 * @param fundAccount
	 * @param combineId
	 * @param targetCombineId
	 * @param occurPrice
	 * @param occurAmount
	 * @param occurCost
	 * @param occurProfit
	 * @param exchangeType
	 * @param stockCode
	 * @return
	 */
	public boolean funcAmStockMove(String userToken, String fundAccount,
			String combineId, String targetCombineId, double occurPrice,
			double occurAmount, double occurCost, double occurProfit,
			String exchangeType, String stockCode) {
		boolean stockMove = Boolean.FALSE;
		try {
			// 获取event
			IEvent event = getEventByAlias(FUNC_AM_STOCK_MOVE,
					EventType.ET_REQUEST);
			// 往event中添加dataset
			IDataset dataset = DatasetService.getDefaultInstance().getDataset();
			dataset.addColumn("user_token");
			dataset.addColumn("fund_account");
			dataset.addColumn("combine_id");
			dataset.addColumn("target_combine_id");
			dataset.addColumn("occur_price");
			dataset.addColumn("occur_amount");
			dataset.addColumn("occur_cost");
			dataset.addColumn("occur_profit");
			dataset.addColumn("exchange_type");
			dataset.addColumn("stock_code");
			dataset.appendRow();
			dataset.updateString("user_token", userToken);
			dataset.updateString("fund_account", fundAccount);
			dataset.updateString("combine_id", combineId);
			dataset.updateString("target_combine_id", targetCombineId);
			dataset.updateDouble("occur_price", occurPrice);
			dataset.updateDouble("occur_amount", occurAmount);
			dataset.updateDouble("occur_cost", occurCost);
			dataset.updateDouble("occur_profit", occurProfit);
			dataset.updateString("exchange_type", exchangeType);
			dataset.updateString("stock_code", stockCode);
			event.putEventData(dataset);
			// 同步发送如下
			IEvent rsp = client.sendReceive(event, 10000);
			// 先判断返回值
			if (rsp.getReturnCode() == EventReturnCode.I_OK) { // 返回错误
				// 获得结果集
				IDatasets result = rsp.getEventDatas();
				// 获得结果集总数
				int datasetCount = result.getDatasetCount();
				// 遍历所有的结果集
				if (datasetCount > 0) {
					IDataset ds = result.getDataset(0);
					String errorNo = ds.getString("error_no");
					if (SUCCESS.equals(errorNo)) {
						stockMove = Boolean.TRUE;
					}
				}
			} else {
				String details = userToken + "," + fundAccount + ","
						+ combineId + "," + targetCombineId + "," + occurPrice
						+ "," + occurAmount + "," + occurCost + ","
						+ occurProfit + "," + exchangeType + "," + stockCode;
				List<String> pramas = Lists.newArrayList();
				pramas.add("com.tzdr.common.api.ihuyi.hundsun.HundsunJres.funcAmStockMove");
				pramas.add("errorNo::" + rsp.getErrorNo() + ",errorInfo::"
						+ rsp.getErrorInfo() + ",details::" + details);
				sendEmail(pramas);
				log.warn(
						"com.tzdr.common.api.ihuyi.hundsun.HundsunJres.funcAmStockMove,errorNo::{},errorInfo::{}",
						rsp.getErrorNo(), rsp.getErrorInfo());
			}
			// 同步发送到此结束
		} catch (T2SDKException e) {
			String details = userToken + "," + fundAccount + "," + combineId
					+ "," + targetCombineId + "," + occurPrice + ","
					+ occurAmount + "," + occurCost + "," + occurProfit + ","
					+ exchangeType + "," + stockCode;
			EmailExceptionHandler.getInstance().HandleExceptionWithData(e,
					"恒生接口报错", className + "funcAmStockMove", details);
		}
		return stockMove;
	}

	/**
	 * 股票资产查询
	 * 
	 * @param userToken
	 * @param fundAccount
	 * @param combineId
	 * @return
	 */
	public List<StockCurrent> funcAmStockCurrentQry(String userToken,
			String fundAccount, String combineId) {

		List<StockCurrent> stockCurrentList = Lists.newArrayList();

		try {
			// 获取event
			IEvent event = getEventByAlias(FUNC_AM_STOCK_CURRENT_QRY,
					EventType.ET_REQUEST);
			// 往event中添加dataset
			IDataset dataset = DatasetService.getDefaultInstance().getDataset();
			dataset.addColumn("user_token");
			dataset.addColumn("fund_account");
			dataset.addColumn("combine_id");

			dataset.appendRow();
			dataset.updateString("user_token", userToken);
			dataset.updateString("fund_account", fundAccount);
			dataset.updateString("combine_id", combineId);

			event.putEventData(dataset);
			// 同步发送如下
			IEvent rsp = client.sendReceive(event, 10000);
			// 先判断返回值
			if (rsp.getReturnCode() != EventReturnCode.I_OK) {
				String details = userToken + "," + fundAccount + ","
						+ combineId;
				List<String> pramas = Lists.newArrayList();
				pramas.add("com.tzdr.common.api.ihuyi.hundsun.HundsunJres.funcAmStockCurrentQry");
				pramas.add("errorNo::" + rsp.getErrorNo() + ",errorInfo::"
						+ rsp.getErrorInfo() + ",details::" + details);
				sendEmail(pramas);
				log.warn(
						"com.tzdr.common.api.ihuyi.hundsun.HundsunJres.funcAmStockCurrentQry,errorNo::{},errorInfo::{}",
						rsp.getErrorNo(), rsp.getErrorInfo());
			} else {
				// 获得结果集
				IDatasets result = rsp.getEventDatas();
				// 获得结果集总数
				int datasetCount = result.getDatasetCount();
				// 遍历所有的结果集
				for (int i = 0; i < datasetCount; i++) {
					// 开始读取单个结果集的信息
					IDataset ds = result.getDataset(i);
					ds.beforeFirst();
					while (ds.hasNext()) {
						ds.next();
						StockCurrent stockCurrent = new StockCurrent();
						stockCurrent.setAssetId(ds.getLong("asset_id"));
						stockCurrent.setCombineId(ds.getString("combine_id"));
						stockCurrent.setCurrentCash(ds
								.getDouble("current_cash"));
						stockCurrent.setFundAccount(ds
								.getString("fund_account"));
						stockCurrent.setMarketValue(ds
								.getDouble("market_value"));
						stockCurrentList.add(stockCurrent);

					}
				}
			}
			// 同步发送到此结束
		} catch (T2SDKException e) {
			String details = userToken + "," + fundAccount + "," + combineId;
			EmailExceptionHandler.getInstance().HandleExceptionWithData(e,
					"恒生接口报错", className + "funcAmStockCurrentQry", details);
		}
		
		if (CollectionUtils.isEmpty(stockCurrentList) 
				|| StringUtil.isBlank(stockCurrentList.get(0).getFundAccount())){
			return null;
		}
		return stockCurrentList;
	}
	
	/**
	 * 根据帐号密码刷新token
	 * @param operatorNo
	 * @param password
	 * @return
	 */
	public HundsunToken get(String operatorNo,String password) {
		String token =funcAmUserLogin(operatorNo, password).getString("userToken");
		if (StringUtil.isBlank(token)) {
			log.error("恒生接口拿token失败，account:"+operatorNo);
			List<String> pramas = Lists.newArrayList();
			pramas.add("恒生接口拿token失败:");
			pramas.add("details::" + operatorNo);
			sendEmail(pramas);
		}
		HundsunToken hundsunToken = findHundsunToken(operatorNo);
		if (hundsunToken == null) {
			hundsunToken = new HundsunToken();
			hundsunToken.setAccount(operatorNo);
			hundsunToken.setToken(token);
			SpringUtils.getBean(HundsunTokenDao.class).save(hundsunToken);
		} else {
			hundsunToken.setToken(token);
			SpringUtils.getBean(HundsunTokenDao.class).update(hundsunToken);
		}
		return hundsunToken;
	}
}
