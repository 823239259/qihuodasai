package com.tzdr.business.hkstock.service;

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
import com.tzdr.common.api.ihuyi.SMSSender;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.MessageUtils;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.MultiListParam;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.hkstock.dao.HkAppendLevelMoneyDao;
import com.tzdr.domain.hkstock.entity.HkAppendLevelMoney;
import com.tzdr.domain.hkstock.vo.hkappendLevelMoneyVo;
import com.tzdr.domain.web.entity.WUser;



/**
 * 追加保证金服务
 * @zhouchen
 * 2015年12月14日
 */
@Service
@Transactional
public class HkAppendLevelMoneyService extends BaseServiceImpl<HkAppendLevelMoney,HkAppendLevelMoneyDao> {
	
	@Autowired
	private AuthService authService;
	@Autowired
	private WUserService wUserService;
	@Autowired
	private DataMapService dataService;
	public String handleappendMoney(String [] idArray){
		for (String id:idArray){
			HkAppendLevelMoney hkAppendLevelMoney = super.get(id);
			if (1==hkAppendLevelMoney.getStatus()){
				return "追加保证金已处理，请刷新后查看";
			}
			hkAppendLevelMoney.setStatus(1);
			hkAppendLevelMoney.setUpdateTime(Dates.getCurrentLongDate());
			User  loginUser = authService.getCurrentUser();
			hkAppendLevelMoney.setUpdateUserId(loginUser.getId());
			hkAppendLevelMoney.setUpdateUser(loginUser.getRealname());
			hkAppendLevelMoney.setOperateContent("处理追加保证金记录");
			super.update(hkAppendLevelMoney);
			String uid = hkAppendLevelMoney.getUid();
			WUser wUser = wUserService.get(uid);
			
			SMSSender.getInstance().send(dataService.getSmsContentOthers(), wUser.getMobile(),MessageUtils.message("hk.append.Level.Money", hkAppendLevelMoney.getGroupId(),hkAppendLevelMoney.getAppendMoney()));
		}
		return "";
	}
	
	
	public PageInfo<Object> queryList(EasyUiPageInfo easyUiPage,
			Map<String, Object> searchParams) {
		PageInfo<Object> pageInfo = new PageInfo<Object>(easyUiPage.getRows(),
				easyUiPage.getPage());
		String sql = "SELECT alm.id,usr.id uid,usr.mobile,uv.tname realName,trade.account_name accountName,trade.account_no accountNo,alm.group_id groupId,alm.hk_dollar_money hkDollarMoney,alm.append_date appendDate,alm.update_user handlerName,alm.update_time handleDate,alm.`status`,pa.trade_channel feeType"
				+ " FROM hk_append_level_money alm INNER JOIN w_user usr ON usr.id = alm.uid INNER JOIN w_user_verified uv ON uv.uid = usr.id LEFT JOIN hk_user_trade trade ON trade.group_id = alm.group_id AND trade.type = 0 INNER JOIN hk_parent_account  pa ON pa.id=trade.parent_account_id ";
		// params 查询参数 依次 存入
		List<Object> params = Lists.newArrayList();
		MultiListParam multilistParam = new MultiListParam(easyUiPage,
				searchParams, params, sql);
		if (searchParams.get("GTE_status") != null && StringUtil.isNotBlank(searchParams.get("GTE_status").toString()) && Integer.parseInt(searchParams.get("GTE_status").toString())==0){
			pageInfo = multiListPageQuery(multilistParam, hkappendLevelMoneyVo.class);			
		}
		else{
			pageInfo = multiListPageQuery(multilistParam, hkappendLevelMoneyVo.class);			
		}
		
		return pageInfo;
	}
	
}
