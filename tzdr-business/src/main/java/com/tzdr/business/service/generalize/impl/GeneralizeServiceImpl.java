package com.tzdr.business.service.generalize.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jodd.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.tzdr.business.service.generalize.GeneralizeService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.web.support.MultiListParam;
import com.tzdr.domain.dao.generalize.GeneralizeDao;
import com.tzdr.domain.vo.AgentChildVo;
import com.tzdr.domain.vo.GeneralizeVisitUserVo;
import com.tzdr.domain.vo.GeneralizeVisitVo;
import com.tzdr.domain.web.entity.GeneralizeVisit;
import com.tzdr.domain.web.entity.WUser;
/**
* @Description: TODO(推广业务信息管理实现层)
* @ClassName: GeneralizeServiceImpl
* @author wangpinqun
* @date 2015年1月12日 下午8:24:23
 */
@Service("generalizeService")
public class GeneralizeServiceImpl extends BaseServiceImpl<GeneralizeVisit, GeneralizeDao> implements GeneralizeService {
	
	@Autowired
	private WUserService wUserService;

	@Override
	public void saveGeneralizeVisit(GeneralizeVisit generalizeVisit) {
		super.save(generalizeVisit);
	}

	@Override
	public Integer getWuserTotalChild(String uid) {
		if(StringUtil.isBlank(uid)){
			return 0;
		}
		WUser wuser = wUserService.getUser(uid);
		List<WUser> childs = wuser == null ? null : wuser.getChilds();
		if(childs == null || childs.isEmpty()){
			return 0;
		}
		return wuser.getChilds().size();
	}
	
	@Override
	public Long getGeneralizeVisitCount(String generalizeId) {
		return getEntityDao().getCountByGeneralizeId(generalizeId);
	}

	@Override
	public Long getGeneralizeVisitClieantIpCount(String generalizeId) {
		String sql = "SELECT count(lin.clieant_ip)  FROM (SELECT clieant_ip FROM w_generalize_visit WHERE generalize_id=? GROUP BY clieant_ip) as lin";
		List<Object> params = Lists.newArrayList();
		params.add(generalizeId);
		Object result =  this.nativeQueryOne(sql, params);
		return result == null ? 0:Long.valueOf(result.toString());
	}

	@Override
	public PageInfo<Object> queryPageGeneralizeVisitVo(String generalizeId,PageInfo<Object> pageInfo) {
		if(StringUtil.isBlank(generalizeId)){
			return pageInfo;
		}
		String sql = "SELECT clieant_ip as clieantIp,max(createdate) as createdate,count(clieant_ip) as totalCount FROM w_generalize_visit WHERE generalize_id= ? GROUP BY clieant_ip ORDER BY max(createdate) DESC";
		List<Object> params = Lists.newArrayList();
		params.add(generalizeId);
		
		MultiListParam  multilistParam  = new MultiListParam(pageInfo, sql, params);
		
		pageInfo = multiListPageQuery(multilistParam,GeneralizeVisitVo.class);
		
		return pageInfo;
	}

	@Override
	public PageInfo<Object> queryPageGeneralizeVisitUserVo(String parentId,PageInfo<Object> pageInfo) {
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT u.id,u.mobile,(select v.tname from w_user_verified v where v.uid = u.id) tname,u.rebate,(SELECT count(*) FROM w_user utemp WHERE utemp.parent_id=u.id) as totalChild,");
		sql.append(" u.subordinate_default_rebate as subordinateDefaultRebate,u.total_commission as totalCommission,if(uc.totaMoney is null,0.00,uc.totaMoney) as totalReturnCommission,u.ctime ");
		sql.append(" FROM w_user u ");
		sql.append(" LEFT JOIN (SELECT sum(c.money) as totaMoney, c.deduct_child_uid,c.uid FROM w_user_commission c WHERE c.uid=? GROUP BY c.deduct_child_uid) uc ");
		sql.append(" ON u.id = uc.deduct_child_uid");
		sql.append(" WHERE u.parent_id=? ORDER BY u.ctime DESC");
		
		List<Object> params = Lists.newArrayList();
		params.add(parentId);
		params.add(parentId);
		
		pageInfo = multiListPageQuery(new MultiListParam(pageInfo, sql.toString(), params),GeneralizeVisitUserVo.class);
		
		return pageInfo;
	}
	
	
	

	@Override
	public List<AgentChildVo> queryChildsCountByParentId(String parentId) {
		String sql = 
				  " SELECT w.id,w.parent_id"
				+ " ,(SELECT count(*) FROM w_user temp WHERE temp.parent_id = w.id) childSize "
				+ " FROM w_user w WHERE w.parent_id = ?";
		return this.getEntityDao().queryBySql(sql, AgentChildVo.class, null, parentId);
	}

	@Override
	public synchronized int queryChildsSize(String parentId) {
		AgentNumberTool tool = new AgentNumberTool();
		return tool.queryChildSum(parentId);
	}

	@Override
	public synchronized Map<String,String> allChildUids(String parentId) {
		AgentNumberTool tool = new AgentNumberTool();
		return tool.queryUidsByParentId(parentId);
	}
	
	/**
	 * 
	 * 
	 * <p></p>
	 * @author LiuQing
	 * @see 计算下级数量
	 * @version 2.0
	 * 2015年4月16日下午1:22:46
	 */
	class AgentNumberTool {
		
		/**
		 * 数量
		 */
		private int count;
		
		/**
		 * 用户编号集合
		 */
		private Map<String,String> uids = new HashMap<String,String>();
		public int queryChildSum(String parentId) {
			List<AgentChildVo> voes = queryChildsCountByParentId(parentId);
			if (voes != null && voes.size() > 0) {
				count += voes.size();
				for (AgentChildVo vo:voes) {
					if (vo.getChildSize() > 0) {
						queryChildSum(vo.getId());
					}
				}
			}
			return count;
		}
		
		/**
		 * 获取数据集数据
		 * @param parentId String
		 * @return List<String>
		 */
		public Map<String,String> queryUidsByParentId(String parentId) {
			uids.put(parentId, parentId);
			List<AgentChildVo> voes = queryChildsCountByParentId(parentId);
			if (voes != null && voes.size() > 0) {
				for (AgentChildVo childVo:voes) {
					uids.put(childVo.getId(),childVo.getId());
					if (childVo.getChildSize() > 0) {
						queryUidsByParentId(childVo.getId());
					}
				}
			}
			return uids;
		}
		
		
		
		
	}



	
	
	
}
