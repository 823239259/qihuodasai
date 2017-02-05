package com.tzdr.business.service.userTrade;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.tzdr.business.service.thread.SMSSendForContentThread;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.MessageUtils;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.MultiListParam;
import com.tzdr.domain.dao.userTrade.FSimpleProductAppendLevelMoneyDao;
import com.tzdr.domain.vo.cms.FSimpleAppendLevelMoneyVo;
import com.tzdr.domain.web.entity.FSimpleProductAppendLevelMoney;
import com.tzdr.domain.web.entity.WUser;


/**
 * 
 * @author LiuYang
 * 商品期货追加保证金 service
 * 2015年9月17日 下午4:40:03
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class FSimpleProductAppendLevelMoneyService extends BaseServiceImpl<FSimpleProductAppendLevelMoney,FSimpleProductAppendLevelMoneyDao>{

	@Autowired
	private WUserService wUserService;
	
	/**
	 * 提供后台获取数据
	 * @param easyUiPage
	 * @param searchParams
	 * @return
	 */
	public PageInfo<Object> getData(EasyUiPageInfo easyUiPage, Map<String, Object> searchParams){
		
		String sql="SELECT f.id,"
				+ "f.uid,"
				+ "f.type,"
				+ "f.status,"
				+ "fut.tran_account AS tranAccount,"
				+ "f.program_no AS programNo,"
				+ "f.append_money AS appendMoney,"
				+ "f.append_date AS appendDate,"
				+ "f.update_time AS updateTime,"
				+ "w.mobile,"
				+ "v.tname AS username "
				+ "FROM f_simple_product_append_level_money f,"
				+ "f_simple_ftse_user_trade fut,"
				+ "w_user w,w_user_verified v "
				+ "WHERE f.uid=w.id AND w.id=v.uid AND fut.program_no=f.program_no "
				+ "AND f.type in (1,2,3,4,20) AND f.status in (0,1)"
				+ "ORDER BY f.status ASC,updateTime DESC,appendDate ASC";
		
		MultiListParam multilistParam = new MultiListParam(easyUiPage,
				searchParams, Lists.newArrayList(), sql);
		
		return multiListPageQuery(multilistParam, FSimpleAppendLevelMoneyVo.class);
	}
	
	/**
	 * 改变状态由“未处理”改为“已处理”，并自动给用户发送短信，“您的商品期货补充保证金已添加，请重新登录交易软件查看”
	 * @param id
	 */
	public String changeStatus(String id){
		FSimpleProductAppendLevelMoney appendLevelMoney=get(id);
		if(appendLevelMoney.getStatus()==0){
			appendLevelMoney.setStatus(1);
			appendLevelMoney.setUpdateTime(Dates.getCurrentLongDate());
			update(appendLevelMoney);
			WUser wUser=wUserService.get(appendLevelMoney.getUid());
			String content = MessageUtils.message("commodity.future.append.audit.success",getTypeName(appendLevelMoney.getType()));
			new SMSSendForContentThread(wUser.getMobile(),content,2000).start();
			return "操作成功！";
		}else{
			return "补充保证金已处理，请刷新后查看";		 	
		}
	}
	
	/**
	 * 根据类型获取类型名称
	 * 
	 * @param type
	 * @return
	 */
	private String getTypeName(int type) {
		switch (type) {
		case 1:
			return "沪金期货";
		case 2:
			return "沪银期货";
		case 3:
			return "沪铜期货";
		case 4:
			return "橡胶期货";
		case 20:
			return "商品综合";
		default:
			break;
		}
		return "";
	}
}
