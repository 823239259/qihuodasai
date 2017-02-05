package com.tzdr.business.service.userTrade;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import jodd.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.service.datamap.DataMapService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.api.ihuyi.PgbSMSSender;
import com.tzdr.common.api.ihuyi.SMSSender;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.MessageUtils;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.common.web.support.MultiListParam;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.dao.userTrade.AppendLevelMoneyFailDao;
import com.tzdr.domain.vo.AppendLevelMoneyFailVo;
import com.tzdr.domain.vo.AppendMoneyFailVo;
import com.tzdr.domain.web.entity.AppendLevelMoneyFail;
import com.tzdr.domain.web.entity.UserTrade;
import com.tzdr.domain.web.entity.WUser;

/**
 * 追加保证金失败 service
 * @zhouchen
 * 2015年4月11日
 */
@Service
@Transactional
public class AppendLevelMoneyFailService extends BaseServiceImpl<AppendLevelMoneyFail,AppendLevelMoneyFailDao> {
	
	@Autowired
	private AuthService authService;
	@Autowired
	private WUserService wUserService;
	@Autowired
	private DataMapService dataService;
	public JsonResult handleFail(String [] idArray){
		for (String id:idArray){
			AppendLevelMoneyFail appendLevelMoneyFail = super.get(id.split(":")[0]);
			if(appendLevelMoneyFail.getStatus()==1){
				return  new JsonResult(false,"追加保证金已处理，请刷新后查看");
			}
			appendLevelMoneyFail.setStatus(1);
			appendLevelMoneyFail.setUpdateTime(Dates.getCurrentLongDate());
			User  loginUser = authService.getCurrentUser();
			appendLevelMoneyFail.setUpdateUserId(loginUser.getId());
			appendLevelMoneyFail.setUpdateUser(loginUser.getRealname());
			appendLevelMoneyFail.setOperateContent("处理追加保证金失败记录");
			super.update(appendLevelMoneyFail);
			String uid = appendLevelMoneyFail.getUid();
			WUser wUser = wUserService.get(uid);
			// 区别股票合买短信
			if((UserTrade.ActivityType.TOGETHER_TRADE+"").equals(id.split(":")[1])) {
				SMSSender.getInstance().send(dataService.getSmsContentOthers(), wUser.getMobile(), MessageUtils.message("together.append.Level.Money.Fail",appendLevelMoneyFail.getGroupId(),appendLevelMoneyFail.getAppendMoney()));
			}
			else if((UserTrade.ActivityType.MONTH_TRADE+"").equals(id.split(":")[1])) {
				PgbSMSSender.getInstance().send(dataService.getPgbSmsContentOthers(), wUser.getMobile(), MessageUtils.message("month.append.Level.Money.Fail",appendLevelMoneyFail.getGroupId(),appendLevelMoneyFail.getAppendMoney()));
			}
			else {
				PgbSMSSender.getInstance().send(dataService.getPgbSmsContentOthers(), wUser.getMobile(), MessageUtils.message("Append.Level.Money.Fail",appendLevelMoneyFail.getGroupId(),appendLevelMoneyFail.getAppendMoney()));
			}
			
		}
		return  new JsonResult("操作成功！");
	}
	
	public PageInfo<Object> queryList(EasyUiPageInfo easyUiPage,
			Map<String, Object> searchParams) {
		PageInfo<Object> pageInfo = new PageInfo<Object>(easyUiPage.getRows(),
				easyUiPage.getPage());
		String sql = " SELECT trade.activity_type activityType, alm.id, usr.id uid, usr.mobile, uv.tname realName, acc.account_name accountName, acc.account accountNo, alm.group_id groupId, alm.append_money appendMoney, alm.append_date appendDate, alm.update_user handlerName, alm.update_time handleDate, alm.`status`, trade.fee_type feeType FROM w_append_level_money_fail alm INNER JOIN w_account acc ON alm.account_id = acc.id INNER JOIN w_user usr ON usr.id = alm.uid INNER JOIN w_user_verified uv ON uv.uid = usr.id LEFT JOIN w_user_trade trade ON trade.group_id = alm.group_id and trade.type in(0,1) ";
		// params 查询参数 依次 存入
		List<Object> params = Lists.newArrayList();
		MultiListParam multilistParam = new MultiListParam(easyUiPage,
				searchParams, params, sql);
		if (searchParams.get("GTE_status") != null && StringUtil.isNotBlank(searchParams.get("GTE_status").toString()) && Integer.parseInt(searchParams.get("GTE_status").toString())==0){
			pageInfo = multiListPageQuery(multilistParam, AppendLevelMoneyFailVo.class);			
		}
		else{
			pageInfo = multiListPageQuery(multilistParam, AppendMoneyFailVo.class);			
		}
		return pageInfo;
	}
	
}
