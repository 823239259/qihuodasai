package com.tzdr.business.service.generalize.impl;

import java.util.ArrayList;
import java.util.List;

import jodd.util.StringUtil;

import org.springframework.stereotype.Service;

import com.tzdr.business.service.generalize.CommissionService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.dao.generalize.UserCommissionDao;
import com.tzdr.domain.hkstock.vo.HkEarningsVo;
import com.tzdr.domain.vo.UserCommissionVo;
import com.tzdr.domain.web.entity.UserCommission;

/**
* @Description: 用户返利报表管理信息
* @ClassName: CommissionServiceImpl
* @author wangpinqun
* @date 2015年3月19日 上午10:03:02
 */
@Service("commissionService")
public class CommissionServiceImpl extends BaseServiceImpl<UserCommission, UserCommissionDao> implements CommissionService{

	@SuppressWarnings("unchecked")
	@Override
	public List<UserCommissionVo> queryUserCommissionVo(ConnditionVo connVo) {
		StringBuffer sql = new StringBuffer();
		
		List<Object> params = new ArrayList<Object>();
        String ctimeStrStartObj = null;      //查询开始时间
        String ctimeStrEndObj = null;        //查询结束时间
        Integer rebateStrStartObj = null;     //查询返利率开始值
        Integer rebateStrEndObj =  null;      //查询返利率结算值
        String mobileObj = null;             //查询上一级用户名/用户名
        String tnameObj  = null;             //查询实名
		if (connVo != null) {
			ctimeStrStartObj = connVo.getValue("ctimeStr_start") == null ? null : connVo.getValue("ctimeStr_start").toString();
			ctimeStrStartObj = StringUtil.isBlank(ctimeStrStartObj) ? null : ctimeStrStartObj;
			
			ctimeStrEndObj = connVo.getValue("ctimeStr_end") == null ? null : connVo.getValue("ctimeStr_end").toString();
			ctimeStrEndObj = StringUtil.isBlank(ctimeStrEndObj) ? null : ctimeStrEndObj;
			
			rebateStrStartObj = connVo.getValue("rebateStr_start") == null ? null : StringUtil.isBlank(connVo.getValue("rebateStr_start").toString()) ? null : Integer.valueOf(connVo.getValue("rebateStr_start").toString());
			
			rebateStrEndObj = connVo.getValue("rebateStr_end") == null ? null : StringUtil.isBlank(connVo.getValue("rebateStr_end").toString()) ? null : Integer.valueOf(connVo.getValue("rebateStr_end").toString());
			
			mobileObj = connVo.getValue("mobile") == null ? null : connVo.getValue("mobile").toString();
			
			mobileObj = StringUtil.isBlank(mobileObj) ? null : mobileObj;
			
			tnameObj = connVo.getValue("tname") == null ? null : connVo.getValue("tname").toString();
			
			tnameObj = StringUtil.isBlank(tnameObj) ? null : tnameObj;
		}
		
		sql.append("SELECT temp.`level`-2 AS level,temp.uid AS userId,temp.user_grade AS userGrade,u2.mobile AS parentName, ");
		sql.append(" temp.mobile AS userName ,temp.tname AS trueName,wuc.rebate AS userRebate,sum(temp.userManageMoney) AS userManageMoney, ");
		sql.append(" sum(temp.userMoney) AS userMoney,sum(temp.subordinateManageMoney) AS subordinateManageMoney, ");
		sql.append(" sum(temp.subordinateMoney) AS subordinateMoney,sum(temp.userMoney)+sum(temp.subordinateMoney) AS totalMoney, ");
		sql.append(" temp.parent_id AS parentId,temp.user_type AS userType,u2.user_type AS parentUserType ");
		sql.append(" FROM ( ");
		sql.append(" SELECT c.uid AS uid,u.id,u.`level`,u.mobile,u.user_grade,v.tname,u.parent_id,u.user_type,0 AS rebate,0 AS userManageMoney,0 AS userMoney,sum(c.money) AS subordinateMoney, ");
		sql.append(" sum(c.manage_money) AS subordinateManageMoney ");
		sql.append(" FROM w_user_commission c,w_user u,w_user_verified v ");
		sql.append(" WHERE u.id=v.uid AND c.uid=u.id AND  c.income_source_uid IS NOT NULL ");
		if(tnameObj != null){
			sql.append(" AND v.tname like ? ");
			params.add("%"+String.valueOf(tnameObj) + "%");
		}
		if(ctimeStrStartObj != null && ctimeStrEndObj != null){
			sql.append(" AND c.create_time BETWEEN ? AND ? ");
			params.add(TypeConvert.strToZeroDate1000(ctimeStrStartObj,0));
			params.add(TypeConvert.strToZeroDate1000(ctimeStrEndObj,1,-1));
		}else if(ctimeStrStartObj != null && ctimeStrEndObj == null){
			sql.append(" AND c.create_time >= ? ");
			params.add(TypeConvert.strToZeroDate1000(ctimeStrStartObj,0));
		}else if(ctimeStrStartObj == null && ctimeStrEndObj != null){
			sql.append(" AND c.create_time <= ? ");
			params.add(TypeConvert.strToZeroDate1000(ctimeStrEndObj,1,-1));
		}
		sql.append(" GROUP BY c.uid ");
		sql.append(" UNION ALL ");
		sql.append(" SELECT c.uid AS uid,u.id,u.`level`,u.mobile,u.user_grade,v.tname,u.parent_id,u.user_type,0 AS rebate,sum(c.manage_money) AS userManageMoney,sum(c.money) AS userMoney,0 AS subordinateMoney,  ");
		sql.append(" 0 AS subordinateManageMoney ");
		sql.append(" FROM w_user_commission c,w_user u,w_user_verified v ");
		sql.append(" WHERE u.id=v.uid AND c.uid=u.id AND  c.income_source_uid IS NULL "); 
		if(tnameObj != null){
			sql.append(" AND v.tname like ? ");
			params.add("%"+String.valueOf(tnameObj) + "%");
		}
		if(ctimeStrStartObj != null && ctimeStrEndObj != null){
			sql.append(" AND c.create_time BETWEEN ? AND ? ");
			params.add(TypeConvert.strToZeroDate1000(ctimeStrStartObj,0));
			params.add(TypeConvert.strToZeroDate1000(ctimeStrEndObj,1,-1));
		}else if(ctimeStrStartObj != null && ctimeStrEndObj == null){
			sql.append(" AND c.create_time >= ? ");
			params.add(TypeConvert.strToZeroDate1000(ctimeStrStartObj,0));
		}else if(ctimeStrStartObj == null && ctimeStrEndObj != null){
			sql.append(" AND c.create_time <= ? ");
			params.add(TypeConvert.strToZeroDate1000(ctimeStrEndObj,1,-1));
		}
		sql.append(" GROUP BY c.uid ) temp,  ");
		sql.append(" w_user u2, ");  
		sql.append(" (SELECT uc.uid,uc.rebate  ");  
		sql.append(" FROM (SELECT c.uid,c.rebate,c.create_time FROM w_user_commission c WHERE 1=1 ");  
		if(ctimeStrStartObj != null && ctimeStrEndObj != null){
			sql.append(" AND c.create_time BETWEEN ? AND ? ");
			params.add(TypeConvert.strToZeroDate1000(ctimeStrStartObj,0));
			params.add(TypeConvert.strToZeroDate1000(ctimeStrEndObj,1,-1));
		}else if(ctimeStrStartObj != null && ctimeStrEndObj == null){
			sql.append(" AND c.create_time >= ? ");
			params.add(TypeConvert.strToZeroDate1000(ctimeStrStartObj,0));
		}else if(ctimeStrStartObj == null && ctimeStrEndObj != null){
			sql.append(" AND c.create_time <= ? ");
			params.add(TypeConvert.strToZeroDate1000(ctimeStrEndObj,1,-1));
		}
		sql.append(" ORDER BY c.create_time DESC ) uc ");
		sql.append(" GROUP BY uc.uid ");
		sql.append(" ) wuc  ");
		sql.append(" WHERE temp.parent_id=u2.id AND temp.uid=wuc.uid ");  
		if(rebateStrStartObj != null && rebateStrEndObj != null){
			sql.append(" AND wuc.rebate BETWEEN ? AND ? ");
			params.add(rebateStrStartObj);
			params.add(rebateStrEndObj);
		}else if(rebateStrStartObj != null && rebateStrEndObj == null){
			params.add(rebateStrStartObj);
			sql.append(" AND wuc.rebate >= ? ");
		}else if(rebateStrStartObj == null && rebateStrEndObj != null){
			sql.append(" AND wuc.rebate <= ? ");
			params.add(rebateStrEndObj);
		}
		if(mobileObj != null){
			sql.append(" AND (temp.mobile like ? OR u2.mobile like ? )");
			params.add("%"+String.valueOf(mobileObj) + "%");
			params.add("%"+String.valueOf(mobileObj) + "%");
		}
		sql.append(" GROUP BY temp.uid ORDER BY userMoney DESC ");
		
		
		/*sql.append("SELECT uv.`level`-2 AS level,temp.uid AS userId,uv.user_grade AS userGrade,uv.parentName AS parentName, ");
		sql.append(" uv.mobile AS userName ,uv.tname AS trueName,wuc.rebate AS userRebate,sum(temp.userManageMoney) AS userManageMoney, ");
		sql.append(" sum(temp.userMoney) AS userMoney,sum(temp.subordinateManageMoney) AS subordinateManageMoney, ");
		sql.append(" sum(temp.subordinateMoney) AS subordinateMoney,sum(temp.userMoney)+sum(temp.subordinateMoney) AS totalMoney, ");
		sql.append(" uv.parent_id AS parentId,uv.user_type AS userType,uv.parentUserType AS parentUserType ");
		sql.append(" FROM ( ");
		sql.append(" SELECT c.uid AS uid,0 AS rebate,0 AS userManageMoney,0 AS userMoney,sum(c.money) AS subordinateMoney, ");
		sql.append(" sum(c.manage_money) AS subordinateManageMoney ");
		sql.append(" FROM w_user_commission c WHERE c.income_source_uid IS NOT NULL ");
		
		if(ctimeStrStartObj != null && ctimeStrEndObj != null){
			sql.append(" AND c.create_time BETWEEN ? AND ? ");
			params.add(TypeConvert.strToZeroDate1000(ctimeStrStartObj,0));
			params.add(TypeConvert.strToZeroDate1000(ctimeStrEndObj,1,-1));
		}else if(ctimeStrStartObj != null && ctimeStrEndObj == null){
			sql.append(" AND c.create_time >= ? ");
			params.add(TypeConvert.strToZeroDate1000(ctimeStrStartObj,0));
		}else if(ctimeStrStartObj == null && ctimeStrEndObj != null){
			sql.append(" AND c.create_time <= ? ");
			params.add(TypeConvert.strToZeroDate1000(ctimeStrEndObj,1,-1));
		}
		sql.append(" GROUP BY c.uid ");
		
		sql.append(" UNION ALL ");
		sql.append(" SELECT c.uid AS uid,0 AS rebate,sum(c.manage_money) AS userManageMoney,sum(c.money) AS userMoney,0 AS subordinateMoney, ");
		sql.append(" 0 AS subordinateManageMoney ");
		sql.append(" FROM w_user_commission c WHERE c.income_source_uid IS NULL ");
		
		if(ctimeStrStartObj != null && ctimeStrEndObj != null){
			sql.append(" AND c.create_time BETWEEN ? AND ? ");
			params.add(TypeConvert.strToZeroDate1000(ctimeStrStartObj,0));
			params.add(TypeConvert.strToZeroDate1000(ctimeStrEndObj,1,-1));
		}else if(ctimeStrStartObj != null && ctimeStrEndObj == null){
			sql.append(" AND c.create_time >= ? ");
			params.add(TypeConvert.strToZeroDate1000(ctimeStrStartObj,0));
		}else if(ctimeStrStartObj == null && ctimeStrEndObj != null){
			sql.append(" AND c.create_time <= ? ");
			params.add(TypeConvert.strToZeroDate1000(ctimeStrEndObj,1,-1));
		}
		sql.append("  GROUP BY c.uid ");
		
		sql.append(" ) temp ");
		sql.append(" LEFT JOIN ( ");
		sql.append(" SELECT uc.uid,uc.rebate ");
		sql.append(" FROM ( ");
		sql.append(" SELECT c.uid,c.rebate,c.create_time FROM w_user_commission c WHERE 1=1 ");
		
		if(ctimeStrStartObj != null && ctimeStrEndObj != null){
			sql.append(" AND c.create_time BETWEEN ? AND ? ");
			params.add(TypeConvert.strToZeroDate1000(ctimeStrStartObj,0));
			params.add(TypeConvert.strToZeroDate1000(ctimeStrEndObj,1,-1));
		}else if(ctimeStrStartObj != null && ctimeStrEndObj == null){
			sql.append(" AND c.create_time >= ? ");
			params.add(TypeConvert.strToZeroDate1000(ctimeStrStartObj,0));
		}else if(ctimeStrStartObj == null && ctimeStrEndObj != null){
			sql.append(" AND c.create_time <= ? ");
			params.add(TypeConvert.strToZeroDate1000(ctimeStrEndObj,1,-1));
		}
		sql.append(" ORDER BY c.create_time DESC ");
		
		sql.append(" ) uc GROUP BY uc.uid ");
		sql.append(" ) wuc ");
		sql.append(" ON temp.uid=wuc.uid ");
		sql.append(" LEFT JOIN ( ");
		sql.append(" SELECT u.id,u.`level`,u.mobile,u.user_grade,v.tname,u.parent_id,u.user_type,u2.user_type AS parentUserType,u2.mobile AS parentName ");
		sql.append(" FROM w_user u LEFT JOIN w_user_verified v ON u.id=v.uid LEFT JOIN w_user u2 ON u.parent_id=u2.id ");
		sql.append(" ) uv ");
		sql.append(" ON temp.uid=uv.id ");
		sql.append(" GROUP BY temp.uid ");
		
		if(rebateStrStartObj != null || rebateStrEndObj != null || tnameObj != null || mobileObj != null){
			sql.append(" HAVING 1=1");
			if(rebateStrStartObj != null && rebateStrEndObj != null){
				sql.append(" AND userRebate BETWEEN ? AND ? ");
				params.add(rebateStrStartObj);
				params.add(rebateStrEndObj);
			}else if(rebateStrStartObj != null && rebateStrEndObj == null){
				params.add(rebateStrStartObj);
				sql.append(" AND userRebate >= ? ");
			}else if(rebateStrStartObj == null && rebateStrEndObj != null){
				sql.append(" AND userRebate <= ? ");
				params.add(rebateStrEndObj);
			}
	        if(tnameObj != null){
				sql.append(" AND trueName like ? ");
				params.add("%"+String.valueOf(tnameObj) + "%");
			}
	        if(mobileObj != null){
				sql.append(" AND (userName like ? OR parentName like ? )");
				params.add("%"+String.valueOf(mobileObj) + "%");
				params.add("%"+String.valueOf(mobileObj) + "%");
			}
		}
		
		sql.append(" ORDER BY userMoney DESC ");*/
		
		List<UserCommissionVo> result =  this.getEntityDao().queryBySql(sql.toString(), UserCommissionVo.class, params.toArray());
		
		return result;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public PageInfo<UserCommissionVo> queryPageDataListVo(
			PageInfo<UserCommissionVo> dataPage, ConnditionVo connVo) {
		
		StringBuffer sql = new StringBuffer();
		
		List<Object> params = new ArrayList<Object>();
        String ctimeStrStartObj = null;      //查询开始时间
        String ctimeStrEndObj = null;        //查询结束时间
        Integer rebateStrStartObj = null;     //查询返利率开始值
        Integer rebateStrEndObj =  null;      //查询返利率结算值
        String mobileObj = null;             //查询上一级用户名/用户名
        String tnameObj  = null;             //查询实名
		if (connVo != null) {
			ctimeStrStartObj = connVo.getValue("ctimeStr_start") == null ? null : connVo.getValue("ctimeStr_start").toString();
			ctimeStrStartObj = StringUtil.isBlank(ctimeStrStartObj) ? null : ctimeStrStartObj;
			
			ctimeStrEndObj = connVo.getValue("ctimeStr_end") == null ? null : connVo.getValue("ctimeStr_end").toString();
			ctimeStrEndObj = StringUtil.isBlank(ctimeStrEndObj) ? null : ctimeStrEndObj;
			
			rebateStrStartObj = connVo.getValue("rebateStr_start") == null ? null : StringUtil.isBlank(connVo.getValue("rebateStr_start").toString()) ? null : Integer.valueOf(connVo.getValue("rebateStr_start").toString());
			
			rebateStrEndObj = connVo.getValue("rebateStr_end") == null ? null : StringUtil.isBlank(connVo.getValue("rebateStr_end").toString()) ? null : Integer.valueOf(connVo.getValue("rebateStr_end").toString());
			
			mobileObj = connVo.getValue("mobile") == null ? null : connVo.getValue("mobile").toString();
			
			mobileObj = StringUtil.isBlank(mobileObj) ? null : mobileObj;
			
			tnameObj = connVo.getValue("tname") == null ? null : connVo.getValue("tname").toString();
			
			tnameObj = StringUtil.isBlank(tnameObj) ? null : tnameObj;
		}
		
		sql.append("SELECT temp.`level`-2 AS level,temp.uid AS userId,temp.user_grade AS userGrade,u2.mobile AS parentName, ");
		sql.append(" temp.mobile AS userName ,temp.tname AS trueName,wuc.rebate AS userRebate,sum(temp.userManageMoney) AS userManageMoney, ");
		sql.append(" sum(temp.userMoney) AS userMoney,sum(temp.subordinateManageMoney) AS subordinateManageMoney, ");
		sql.append(" sum(temp.subordinateMoney) AS subordinateMoney,sum(temp.userMoney)+sum(temp.subordinateMoney) AS totalMoney, ");
		sql.append(" temp.parent_id AS parentId,temp.user_type AS userType,u2.user_type AS parentUserType ");
		sql.append(" FROM ( ");
		sql.append(" SELECT c.uid AS uid,u.id,u.`level`,u.mobile,u.user_grade,v.tname,u.parent_id,u.user_type,0 AS rebate,0 AS userManageMoney,0 AS userMoney,sum(c.money) AS subordinateMoney, ");
		sql.append(" sum(c.manage_money) AS subordinateManageMoney ");
		sql.append(" FROM w_user_commission c,w_user u,w_user_verified v ");
		sql.append(" WHERE u.id=v.uid AND c.uid=u.id AND  c.income_source_uid IS NOT NULL ");
		if(tnameObj != null){
			sql.append(" AND v.tname like ? ");
			params.add("%"+String.valueOf(tnameObj) + "%");
		}
		if(ctimeStrStartObj != null && ctimeStrEndObj != null){
			sql.append(" AND c.create_time BETWEEN ? AND ? ");
			params.add(TypeConvert.strToZeroDate1000(ctimeStrStartObj,0));
			params.add(TypeConvert.strToZeroDate1000(ctimeStrEndObj,1,-1));
		}else if(ctimeStrStartObj != null && ctimeStrEndObj == null){
			sql.append(" AND c.create_time >= ? ");
			params.add(TypeConvert.strToZeroDate1000(ctimeStrStartObj,0));
		}else if(ctimeStrStartObj == null && ctimeStrEndObj != null){
			sql.append(" AND c.create_time <= ? ");
			params.add(TypeConvert.strToZeroDate1000(ctimeStrEndObj,1,-1));
		}
		sql.append(" GROUP BY c.uid ");
		sql.append(" UNION ALL ");
		sql.append(" SELECT c.uid AS uid,u.id,u.`level`,u.mobile,u.user_grade,v.tname,u.parent_id,u.user_type,0 AS rebate,sum(c.manage_money) AS userManageMoney,sum(c.money) AS userMoney,0 AS subordinateMoney,  ");
		sql.append(" 0 AS subordinateManageMoney ");
		sql.append(" FROM w_user_commission c,w_user u,w_user_verified v ");
		sql.append(" WHERE u.id=v.uid AND c.uid=u.id AND  c.income_source_uid IS NULL "); 
		if(tnameObj != null){
			sql.append(" AND v.tname like ? ");
			params.add("%"+String.valueOf(tnameObj) + "%");
		}
		if(ctimeStrStartObj != null && ctimeStrEndObj != null){
			sql.append(" AND c.create_time BETWEEN ? AND ? ");
			params.add(TypeConvert.strToZeroDate1000(ctimeStrStartObj,0));
			params.add(TypeConvert.strToZeroDate1000(ctimeStrEndObj,1,-1));
		}else if(ctimeStrStartObj != null && ctimeStrEndObj == null){
			sql.append(" AND c.create_time >= ? ");
			params.add(TypeConvert.strToZeroDate1000(ctimeStrStartObj,0));
		}else if(ctimeStrStartObj == null && ctimeStrEndObj != null){
			sql.append(" AND c.create_time <= ? ");
			params.add(TypeConvert.strToZeroDate1000(ctimeStrEndObj,1,-1));
		}
		sql.append(" GROUP BY c.uid ) temp,  ");
		sql.append(" w_user u2, ");  
		sql.append(" (SELECT uc.uid,uc.rebate  ");  
		sql.append(" FROM (SELECT c.uid,c.rebate,c.create_time FROM w_user_commission c WHERE 1=1 ");  
		if(ctimeStrStartObj != null && ctimeStrEndObj != null){
			sql.append(" AND c.create_time BETWEEN ? AND ? ");
			params.add(TypeConvert.strToZeroDate1000(ctimeStrStartObj,0));
			params.add(TypeConvert.strToZeroDate1000(ctimeStrEndObj,1,-1));
		}else if(ctimeStrStartObj != null && ctimeStrEndObj == null){
			sql.append(" AND c.create_time >= ? ");
			params.add(TypeConvert.strToZeroDate1000(ctimeStrStartObj,0));
		}else if(ctimeStrStartObj == null && ctimeStrEndObj != null){
			sql.append(" AND c.create_time <= ? ");
			params.add(TypeConvert.strToZeroDate1000(ctimeStrEndObj,1,-1));
		}
		sql.append(" ORDER BY c.create_time DESC ) uc ");
		sql.append(" GROUP BY uc.uid ");
		sql.append(" ) wuc  ");
		sql.append(" WHERE temp.parent_id=u2.id AND temp.uid=wuc.uid ");  
		if(rebateStrStartObj != null && rebateStrEndObj != null){
			sql.append(" AND wuc.rebate BETWEEN ? AND ? ");
			params.add(rebateStrStartObj);
			params.add(rebateStrEndObj);
		}else if(rebateStrStartObj != null && rebateStrEndObj == null){
			params.add(rebateStrStartObj);
			sql.append(" AND wuc.rebate >= ? ");
		}else if(rebateStrStartObj == null && rebateStrEndObj != null){
			sql.append(" AND wuc.rebate <= ? ");
			params.add(rebateStrEndObj);
		}
		if(mobileObj != null){
			sql.append(" AND (temp.mobile like ? OR u2.mobile like ? )");
			params.add("%"+String.valueOf(mobileObj) + "%");
			params.add("%"+String.valueOf(mobileObj) + "%");
		}
		sql.append(" GROUP BY temp.uid ORDER BY userMoney DESC ");
		
		PageInfo<UserCommissionVo> pageInfo =  this.getEntityDao().queryDataPageBySql(dataPage, sql.toString(), UserCommissionVo.class, params.toArray());
		
		return pageInfo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public UserCommissionVo getDataTotalVo(ConnditionVo connVo) {
		
		StringBuffer sql = new StringBuffer();
		
		List<Object> params = new ArrayList<Object>();
        String ctimeStrStartObj = null;      //查询开始时间
        String ctimeStrEndObj = null;        //查询结束时间
        Integer rebateStrStartObj = null;     //查询返利率开始值
        Integer rebateStrEndObj =  null;      //查询返利率结算值
        String mobileObj = null;             //查询上一级用户名/用户名
        String tnameObj  = null;             //查询实名
		if (connVo != null) {
			ctimeStrStartObj = connVo.getValue("ctimeStr_start") == null ? null : connVo.getValue("ctimeStr_start").toString();
			ctimeStrStartObj = StringUtil.isBlank(ctimeStrStartObj) ? null : ctimeStrStartObj;
			
			ctimeStrEndObj = connVo.getValue("ctimeStr_end") == null ? null : connVo.getValue("ctimeStr_end").toString();
			ctimeStrEndObj = StringUtil.isBlank(ctimeStrEndObj) ? null : ctimeStrEndObj;
			
			rebateStrStartObj = connVo.getValue("rebateStr_start") == null ? null : StringUtil.isBlank(connVo.getValue("rebateStr_start").toString()) ? null : Integer.valueOf(connVo.getValue("rebateStr_start").toString());
			
			rebateStrEndObj = connVo.getValue("rebateStr_end") == null ? null : StringUtil.isBlank(connVo.getValue("rebateStr_end").toString()) ? null : Integer.valueOf(connVo.getValue("rebateStr_end").toString());
			
			mobileObj = connVo.getValue("mobile") == null ? null : connVo.getValue("mobile").toString();
			
			mobileObj = StringUtil.isBlank(mobileObj) ? null : mobileObj;
			
			tnameObj = connVo.getValue("tname") == null ? null : connVo.getValue("tname").toString();
			
			tnameObj = StringUtil.isBlank(tnameObj) ? null : tnameObj;
		}
		
		sql.append("SELECT  ");
		sql.append(" sum(temp.userManageMoney) AS userManageMoney, ");
		sql.append(" sum(temp.userMoney) AS userMoney,sum(temp.subordinateManageMoney) AS subordinateManageMoney, ");
		sql.append(" sum(temp.subordinateMoney) AS subordinateMoney,sum(temp.userMoney)+sum(temp.subordinateMoney) AS totalMoney ");
		sql.append(" FROM ( ");
		sql.append(" SELECT c.uid AS uid,u.id,u.`level`,u.mobile,u.user_grade,v.tname,u.parent_id,u.user_type,0 AS rebate,0 AS userManageMoney,0 AS userMoney,sum(c.money) AS subordinateMoney, ");
		sql.append(" sum(c.manage_money) AS subordinateManageMoney ");
		sql.append(" FROM w_user_commission c,w_user u,w_user_verified v ");
		sql.append(" WHERE u.id=v.uid AND c.uid=u.id AND  c.income_source_uid IS NOT NULL ");
		if(tnameObj != null){
			sql.append(" AND v.tname like ? ");
			params.add("%"+String.valueOf(tnameObj) + "%");
		}
		if(ctimeStrStartObj != null && ctimeStrEndObj != null){
			sql.append(" AND c.create_time BETWEEN ? AND ? ");
			params.add(TypeConvert.strToZeroDate1000(ctimeStrStartObj,0));
			params.add(TypeConvert.strToZeroDate1000(ctimeStrEndObj,1,-1));
		}else if(ctimeStrStartObj != null && ctimeStrEndObj == null){
			sql.append(" AND c.create_time >= ? ");
			params.add(TypeConvert.strToZeroDate1000(ctimeStrStartObj,0));
		}else if(ctimeStrStartObj == null && ctimeStrEndObj != null){
			sql.append(" AND c.create_time <= ? ");
			params.add(TypeConvert.strToZeroDate1000(ctimeStrEndObj,1,-1));
		}
		sql.append(" GROUP BY c.uid ");
		sql.append(" UNION ALL ");
		sql.append(" SELECT c.uid AS uid,u.id,u.`level`,u.mobile,u.user_grade,v.tname,u.parent_id,u.user_type,0 AS rebate,sum(c.manage_money) AS userManageMoney,sum(c.money) AS userMoney,0 AS subordinateMoney,  ");
		sql.append(" 0 AS subordinateManageMoney ");
		sql.append(" FROM w_user_commission c,w_user u,w_user_verified v ");
		sql.append(" WHERE u.id=v.uid AND c.uid=u.id AND  c.income_source_uid IS NULL "); 
		if(tnameObj != null){
			sql.append(" AND v.tname like ? ");
			params.add("%"+String.valueOf(tnameObj) + "%");
		}
		if(ctimeStrStartObj != null && ctimeStrEndObj != null){
			sql.append(" AND c.create_time BETWEEN ? AND ? ");
			params.add(TypeConvert.strToZeroDate1000(ctimeStrStartObj,0));
			params.add(TypeConvert.strToZeroDate1000(ctimeStrEndObj,1,-1));
		}else if(ctimeStrStartObj != null && ctimeStrEndObj == null){
			sql.append(" AND c.create_time >= ? ");
			params.add(TypeConvert.strToZeroDate1000(ctimeStrStartObj,0));
		}else if(ctimeStrStartObj == null && ctimeStrEndObj != null){
			sql.append(" AND c.create_time <= ? ");
			params.add(TypeConvert.strToZeroDate1000(ctimeStrEndObj,1,-1));
		}
		sql.append(" GROUP BY c.uid ) temp,  ");
		sql.append(" w_user u2, ");  
		sql.append(" (SELECT uc.uid,uc.rebate  ");  
		sql.append(" FROM (SELECT c.uid,c.rebate,c.create_time FROM w_user_commission c WHERE 1=1 ");  
		if(ctimeStrStartObj != null && ctimeStrEndObj != null){
			sql.append(" AND c.create_time BETWEEN ? AND ? ");
			params.add(TypeConvert.strToZeroDate1000(ctimeStrStartObj,0));
			params.add(TypeConvert.strToZeroDate1000(ctimeStrEndObj,1,-1));
		}else if(ctimeStrStartObj != null && ctimeStrEndObj == null){
			sql.append(" AND c.create_time >= ? ");
			params.add(TypeConvert.strToZeroDate1000(ctimeStrStartObj,0));
		}else if(ctimeStrStartObj == null && ctimeStrEndObj != null){
			sql.append(" AND c.create_time <= ? ");
			params.add(TypeConvert.strToZeroDate1000(ctimeStrEndObj,1,-1));
		}
		sql.append(" ORDER BY c.create_time DESC ) uc ");
		sql.append(" GROUP BY uc.uid ");
		sql.append(" ) wuc  ");
		sql.append(" WHERE temp.parent_id=u2.id AND temp.uid=wuc.uid ");  
		if(rebateStrStartObj != null && rebateStrEndObj != null){
			sql.append(" AND wuc.rebate BETWEEN ? AND ? ");
			params.add(rebateStrStartObj);
			params.add(rebateStrEndObj);
		}else if(rebateStrStartObj != null && rebateStrEndObj == null){
			params.add(rebateStrStartObj);
			sql.append(" AND wuc.rebate >= ? ");
		}else if(rebateStrStartObj == null && rebateStrEndObj != null){
			sql.append(" AND wuc.rebate <= ? ");
			params.add(rebateStrEndObj);
		}
		if(mobileObj != null){
			sql.append(" AND (temp.mobile like ? OR u2.mobile like ? )");
			params.add("%"+String.valueOf(mobileObj) + "%");
			params.add("%"+String.valueOf(mobileObj) + "%");
		}
		sql.append(" ORDER BY userMoney DESC ");
		
		
		List<UserCommissionVo> result =  this.getEntityDao().queryBySql(sql.toString(), UserCommissionVo.class,null, params.toArray());
		
		UserCommissionVo userCommissionVo = null;
		
		if(result != null && result.size() > 0){
			userCommissionVo = result.get(0);
		}
		
		return userCommissionVo;
	}
}
