package com.tzdr.cms.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tzdr.common.utils.BigDecimalUtils;
import com.tzdr.domain.vo.UserCommissionVo;

/**
* @Description: 返利tree工具类
* @ClassName: CommissionTreeUtils
* @author wangpinqun
* @date 2015年3月20日 下午2:53:05
 */
public class CommissionTreeUtils {

	private static final Logger logger = LoggerFactory.getLogger(CommissionTreeUtils.class);
	
	public static List<UserCommissionVo> buildCommissionTree(List<UserCommissionVo> userCommissionVos){
		
		List<UserCommissionVo> parentUserCommission = new ArrayList<UserCommissionVo>();
		
		if(userCommissionVos == null){
			logger.debug("param: rsList is null");
			return parentUserCommission;
		}
	    
		Map<String,String> rootParentIds =getRootParentIds(userCommissionVos);
		
		for (UserCommissionVo userCommissionVo : userCommissionVos) {
			if(rootParentIds.containsKey(userCommissionVo.getUserId())){
				UserCommissionVo newUserCommissionVo = createNewUserCommissionVo(userCommissionVo);
				parentUserCommission.add(newUserCommissionVo);
				buildCommission(newUserCommissionVo,userCommissionVos);
			}
		}
		
		return parentUserCommission;
    }
	
	public static  Map<String,String> getRootParentIds(List<UserCommissionVo> userCommissionVos){
		
		Map<String,String> rootParentIds = new HashMap<String, String>();
		
		for (UserCommissionVo userCommissionVo : userCommissionVos) {
			String parentUserType = userCommissionVo.getParentUserType();
			if(parentUserType.equals("-2") || parentUserType.equals("-3") || isRootParentId(userCommissionVo.getParentId(),userCommissionVos)){
				rootParentIds.put(userCommissionVo.getUserId(), userCommissionVo.getUserId());
			}
		}
		
		return rootParentIds;
	}
	
	public static boolean isRootParentId(String parentId,List<UserCommissionVo> userCommissionVos){
		
		boolean result = true;
		
		for (UserCommissionVo userCommissionVo : userCommissionVos) {
			if(parentId.equals(userCommissionVo.getUserId())){
				result = false;
				break;
			}
		}
		return result;
	}
	
	/**
	* @Description: 创建节点
	* @Title: createNewUserCommissionVo
	* @param userCommissionVo
	* @return UserCommissionVo    返回类型
	 */
	public static UserCommissionVo createNewUserCommissionVo(UserCommissionVo userCommissionVo){
		userCommissionVo.setUserManageMoney(BigDecimalUtils.round2(userCommissionVo.getUserManageMoney(), 2));
		userCommissionVo.setUserMoney(BigDecimalUtils.round2(userCommissionVo.getUserMoney(), 2));
		userCommissionVo.setSubordinateManageMoney(BigDecimalUtils.round2(userCommissionVo.getSubordinateManageMoney(), 2));
		userCommissionVo.setSubordinateMoney(BigDecimalUtils.round2(userCommissionVo.getSubordinateMoney(), 2));
		userCommissionVo.setTotalMoney(BigDecimalUtils.round2(userCommissionVo.getTotalMoney(), 2));
		return userCommissionVo;
	}
	
	public static void buildCommission(UserCommissionVo userCommissionVo,List<UserCommissionVo> rsList){
        List<UserCommissionVo> children=findDirectChildren(userCommissionVo.getUserId(), rsList);
        if(children.size()>0){
            for(UserCommissionVo child:children){
            	UserCommissionVo childUserCommission = createNewUserCommissionVo(child);;
            	childUserCommission.setParentName(userCommissionVo.getUserName());
            	childUserCommission.setParentNode(userCommissionVo);
            	userCommissionVo.addChild(childUserCommission);
                buildCommission(childUserCommission,rsList);
            }
        }
    }
	
	 /**
     * 取得直接的子节点
     * @param parentId
     * @param rsList
     * @return
     */
    public static List<UserCommissionVo> findDirectChildren(String parentId,List<UserCommissionVo> rsList){
    	
        List<UserCommissionVo> children=new LinkedList<UserCommissionVo>();
        for(UserCommissionVo res:rsList){
            if(parentId.equals(res.getParentId())){
                children.add(res);
            }
        }
        return children;
    }
}
