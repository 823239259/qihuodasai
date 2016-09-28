package com.tzdr.business.service.recharge;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.exception.RechargeAdditionalException;
import com.tzdr.business.service.pay.PayService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.MultiListParam;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.dao.rechargelist.RechargeAdditionalDao;
import com.tzdr.domain.vo.RechargeAdditionalVo;
import com.tzdr.domain.web.entity.RechargeAdditional;
import com.tzdr.domain.web.entity.RechargeList;
import com.tzdr.domain.web.entity.WUser;

/**
 * @author zhouchen
 * @version 创建时间：2015年1月12日 下午4:58:44
 * 充值表单补录
 */
@Service
@Transactional
public class RechargeAdditionalService extends BaseServiceImpl<RechargeAdditional,RechargeAdditionalDao>{
	
	@Autowired
	private PayService payService;
	
	@Autowired
	private WUserService wUserService;
	
	
	@Autowired
	private AuthService authService;
	
	public PageInfo<Object> queryDatas(EasyUiPageInfo easyUiPage,
			Map<String, Object> searchParams) {
		PageInfo<Object> pageInfo = new PageInfo<Object>(easyUiPage.getRows(),easyUiPage.getPage());
		String sql = "SELECT rl.source,rl.type, ra.id, ra.recharge_id rechargeId, ra.bank_card bankCard, ra.mobile, rl.trade_account tradeAccount, rl.money, rl.trade_no tradeNo, if(trade_account = 'alipay',rl.account,'')  alipayNo,if(trade_account = 'wechat',rl.account,'')  wechatNo, rl.`status`, uv.tname username, ra.create_time createTime, ra.create_user createUser FROM w_recharge_additional ra, w_recharge_list rl, w_user_verified uv WHERE rl.id = ra.recharge_id AND uv.uid = ra.uid ";
		MultiListParam multilistParam = new MultiListParam(easyUiPage,
				searchParams,null, sql);
		pageInfo = multiListPageQuery(multilistParam, RechargeAdditionalVo.class);
		return pageInfo;
	}
			
	
	/**
	 * 根据id获取 补录信息
	 * @param id
	 * @return
	 */
	public RechargeAdditionalVo getVoById(String id)
	{
		String  sql="SELECT rl.source,rl.type,ra.id, ra.recharge_id rechargeId, ra.bank_card bankCard, ra.mobile, rl.trade_account tradeAccount, rl.money, rl.trade_no tradeNo, rl.account alipayNo, rl.`status` FROM w_recharge_additional ra, w_recharge_list rl WHERE rl.id = ra.recharge_id AND ra.id =? ";
		List<Object> params = Lists.newArrayList();
		params.add(id);
		List<RechargeAdditionalVo> list = nativeQuery(sql, params, RechargeAdditionalVo.class);
		if (CollectionUtils.isEmpty(list)){
			return null;
		}
		
		return list.get(0);
	}
	
	/**
	 * 保存数据
	 * @param additionalVo
	 */
	public void saveData(RechargeAdditionalVo additionalVo){
		
		String mobile = additionalVo.getMobile();
		WUser  wUser = wUserService.getWUserByMobile(mobile);
		if (ObjectUtil.equals(null, wUser)){
			throw new RechargeAdditionalException("recharge.additional.not.find.wuser",new Object[]{mobile});
		}
		String uid = wUser.getId();
		RechargeList rechargeList = payService.saveRechargeRecord(uid,additionalVo.getTradeAccount(),0,additionalVo.getAlipayNo(),
				additionalVo.getMoney(),additionalVo.getIp(),additionalVo.getType(),additionalVo.getTradeNo(),additionalVo.getSource());

		RechargeAdditional additional = new RechargeAdditional(mobile, rechargeList.getId(), uid,additionalVo.getBankCard());
		setOperateLog(additional,"新增-补录充值记录", "add");
		super.save(additional);
		
	}
	
	/**
	 * 保存数据
	 * @param additionalVo
	 */
	public void updateData(RechargeAdditionalVo additionalVo){

		String mobile = additionalVo.getMobile();
		WUser  wUser = wUserService.getWUserByMobile(mobile);
		if (ObjectUtil.equals(null, wUser)){
			throw new RechargeAdditionalException("recharge.additional.not.find.wuser",new Object[]{mobile});
		}
		String uid = wUser.getId();
		String id = additionalVo.getId();
		String rechargeId = additionalVo.getRechargeId();
		
		RechargeList  rechargeList = payService.get(rechargeId);
		if (ObjectUtil.equals(null, rechargeList)){
			throw new RechargeAdditionalException("business.update.not.found.data",null);
		}
		
		RechargeAdditional additional = this.get(id);
		if (ObjectUtil.equals(null, additional)){
			throw new RechargeAdditionalException("business.update.not.found.data",null);
		}
		
		rechargeList.setUid(uid);
		rechargeList.setSource(additionalVo.getSource());
		rechargeList.setAccount(additionalVo.getAlipayNo());
		rechargeList.setMoney(additionalVo.getMoney());
		rechargeList.setActualMoney(additionalVo.getMoney());
		rechargeList.setTradeNo(additionalVo.getTradeNo());
		rechargeList.setTradeAccount(additionalVo.getTradeAccount());
		rechargeList.setAddip(additionalVo.getIp());
		rechargeList.setType(additionalVo.getType());
		payService.update(rechargeList);
		
		additional.setBankCard(additionalVo.getBankCard());
		additional.setMobile(additionalVo.getMobile());
		additional.setUid(uid);		
		setOperateLog(additional,"修改-补录充值记录", "edit");
		super.update(additional);
	}
	
	
	/**
	 * 设置操作记录
	 * @param additional
	 * @param operateContent
	 * @param operateType
	 */
	private void setOperateLog(RechargeAdditional additional,String operateContent,String operateType){
		User   loginUser = authService.getCurrentUser();	
		additional.setOperateContent(operateContent);
		if (StringUtil.equals(operateType,"edit")){
			additional.setUpdateTime(Dates.getCurrentLongDate());
			additional.setUpdateUser(loginUser.getRealname());
			additional.setUpdateUserOrg(loginUser.getOrganization().getName());
			additional.setUpdateUserId(loginUser.getId());
			return ;
		}
		
		additional.setCreateTime(Dates.getCurrentLongDate());
		additional.setCreateUser(loginUser.getRealname());
		additional.setCreateUserId(loginUser.getId());
		additional.setCreateUserOrg(loginUser.getOrganization().getName());
		return ;
	}
}
