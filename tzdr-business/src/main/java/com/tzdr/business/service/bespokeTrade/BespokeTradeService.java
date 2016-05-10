package com.tzdr.business.service.bespokeTrade;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.service.combine.CombineInfoService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.exception.BusinessException;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.dao.bespokeTrade.BespokeTradeDao;
import com.tzdr.domain.vo.BespokeTradeVo;
import com.tzdr.domain.web.entity.BespokeTrade;
/**
 * 
 * @author LiuYang
 *
 * 2015年6月12日 上午9:37:38
 */
@Service
public class BespokeTradeService extends BaseServiceImpl<BespokeTrade,BespokeTradeDao>{
	    public static final Logger logger = LoggerFactory
			.getLogger(CombineInfoService.class);

	    @Autowired
	    private AuthService  authService;
	
		@Override
		public void save(BespokeTrade bespokeTrade) throws BusinessException {
			
			User user = authService.getCurrentUser();
			if (user != null) {
				bespokeTrade.setCreateUserId(user.getId());
				bespokeTrade.setCreateUser(user.getRealname());
			}
			bespokeTrade.setCreateTime(TypeConvert.dbDefaultDate());
			bespokeTrade.setStartTime(Dates.parse(bespokeTrade.getStartTimeValue(),Dates.CHINESE_DATETIME_FORMAT_LINE).getTime()/1000);
			bespokeTrade.setState(BespokeTrade.STATE_VALUE_STARTUP);
			super.save(bespokeTrade);
		}
		
		/**
		 * 查询定制配资到web显示
		 * @return
		 */
		public List<BespokeTradeVo> getBespokeTrade() {
			List<Object> params = Lists.newArrayList();
			String sql=" select id,name,min_bond minBond,max_bond maxBond,"
					+ " min_multiple minMultiple,max_multiple maxMultiple,"
					+" min_duration minDuration, max_duration maxDuration, "
					+" shortest_duration shortestDuration,interest,expenese,start_time startTime,"
					+" state from w_bespoke_trade where state=? and start_time<=? order by start_time limit 4 ";
			params.add(1);
	
			params.add(new Date().getTime()/1000);
			List<BespokeTradeVo> list=getEntityDao().queryListBySql(sql, BespokeTradeVo.class, null, params.toArray());
			if(list!=null){
				for(BespokeTradeVo vo:list){
					if(vo.getMinBond()>=10000){
						DecimalFormat df = new DecimalFormat("#.##");
						vo.setMaxmoneystr(df.format(vo.getMaxBond()/10000)+"万");
						vo.setMinmoneystr(df.format(vo.getMinBond()/10000)+"万");
					}
				}
			}
			return list;
//			Map<String,Object> map=new HashMap<String,Object>();
//			Map<String,Boolean> sortmap=new HashMap<String,Boolean>();
//			sortmap.put("startTime", false);
//			map.put("EQ_state", 1);
//			map.put("LTE_startTime", new Date().getTime()/1000);
			//List<BespokeTrade> list=getEntityDao().query(map, sortmap);
			//return this.getEntityDao().findData(1);
		}
		
		/**
		 * 查询定制配资到web显示
		 * @return
		 */
		@SuppressWarnings("unchecked")
		public List<BespokeTradeVo> findAllBespokeTrade() {
			List<Object> params = Lists.newArrayList();
			String sql=" select id,name,min_bond minBond,max_bond maxBond,"
					+ " min_multiple minMultiple,max_multiple maxMultiple,"
					+" min_duration minDuration, max_duration maxDuration, "
					+" shortest_duration shortestDuration,interest,expenese,start_time startTime,"
					+" state from w_bespoke_trade where state=? and start_time<=? order by start_time ";
			params.add(1);
	
			params.add(new Date().getTime()/1000);
			List<BespokeTradeVo> list=getEntityDao().queryListBySql(sql, BespokeTradeVo.class, null, params.toArray());
			if(list!=null){
				for(BespokeTradeVo vo:list){
					if(vo.getMinBond()>=10000){
						DecimalFormat df = new DecimalFormat("#.##");
						vo.setMaxmoneystr(df.format(vo.getMaxBond()/10000)+"万");
						vo.setMinmoneystr(df.format(vo.getMinBond()/10000)+"万");
					}
				}
			}
			return list;
		}

}
