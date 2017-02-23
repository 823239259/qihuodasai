package com.tzdr.business.service.userTrade;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.service.thread.SMSSendForContentThread;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.MessageUtils;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.MultiListParam;
import com.tzdr.domain.dao.userTrade.FinternationFutureAppendLevelMoneyDao;
import com.tzdr.domain.vo.cms.FinternationFutureMoneyVo;
import com.tzdr.domain.web.entity.FSimpleFtseUserTrade;
import com.tzdr.domain.web.entity.FSimpleProductAppendLevelMoney;
import com.tzdr.domain.web.entity.FinternationFutureAppendLevelMoney;
import com.tzdr.domain.web.entity.WUser;

/**
 * 国际期货追加保证金
 * 
 * @zhouchen 2015年11月20日
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class FinternationFutureAppendLevelMoneyService
		extends BaseServiceImpl<FinternationFutureAppendLevelMoney, FinternationFutureAppendLevelMoneyDao> {

	@Autowired
	private WUserService wUserService;

	@Autowired
	private AuthService authService;

	/**
	 * 提供后台获取数据
	 * 
	 * @param easyUiPage
	 * @param searchParams
	 * @return
	 */
	public PageInfo<Object> getData(EasyUiPageInfo easyUiPage, Map<String, Object> searchParams) {

		String sql = "SELECT f.id," + "f.uid," + "f.type," + "f.status," + "fut.tran_account AS tranAccount," 
				+ "fut.line_loss AS lineLoss," + "f.program_no AS programNo," + "f.append_money AS appendMoney,"
				+ "f.dollar_money as dollarMoney, " + "f.append_date AS appendDate," + "f.update_time AS updateTime,"
				+ "fut.source AS source," + "w.mobile," + "v.tname AS username,"+"f.operator as operator FROM f_internation_future_append_level_money f,f_simple_ftse_user_trade fut,"
				+ "w_user w,w_user_verified v " + "WHERE f.uid=w.id AND w.id=v.uid AND fut.program_no=f.program_no "
				+ "ORDER BY f.status ASC,updateTime DESC, appendDate ASC";

		MultiListParam multilistParam = new MultiListParam(easyUiPage, searchParams, Lists.newArrayList(), sql);

		return multiListPageQuery(multilistParam, FinternationFutureMoneyVo.class);
	}

	/**
	 * 改变状态由“未处理”改为“已处理”，并自动给用户发送短信，“您的商品期货补充保证金已添加，请重新登录交易软件查看”
	 * 
	 * @param id
	 */
	public String changeStatus(String id) {
		FinternationFutureAppendLevelMoney appendLevelMoney = get(id);
		if (appendLevelMoney.getStatus() == 0) {
			appendLevelMoney.setStatus(1);
			appendLevelMoney.setUpdateTime(Dates.getCurrentLongDate());
			appendLevelMoney.setOperator(authService.getCurrentUser().getRealname());
			update(appendLevelMoney);
			WUser wUser = wUserService.get(appendLevelMoney.getUid());
			String content = MessageUtils.message("commodity.future.append.audit.success",
					getTypeName(appendLevelMoney.getType()));
			new SMSSendForContentThread(wUser.getMobile(), content, 2000).start();
			return "";
		} else {
			if (appendLevelMoney.getStatus() == 1) {
				return "补充保证金已处理，请刷新后查看";
			}
		}
		return "";
	}

	/**
	 * 根据类型获取类型名称
	 * 
	 * @param type
	 * @return
	 */
	private String getTypeName(int type) {
		switch (type) {
		case 0:
			return "富时A50";
		case 6:
			return "国际原油";
		case 7:
			return "恒生指数";
		case 8:
			return "国际综合";
		case 9:
			return "小恒指";
		default:
			break;
		}
		return "";
	}

}
