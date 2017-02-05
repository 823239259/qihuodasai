package com.tzdr.business.service.spot.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.exception.SpotSalesmanException;
import com.tzdr.business.service.spot.SpotSalesmanService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.exception.BusinessException;
import com.tzdr.common.utils.BeanUtils;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.dao.spot.SpotSalesmanDao;
import com.tzdr.domain.web.entity.SpotSalesman;

/**
 * 现货销售员 service实现层
 * @ClassName SpotSalesmanServiceImpl
 * @author L.Y
 * @email liuyuan@peigubao.com
 * @date 2015年10月8日
 */
@Service
@Transactional
public class SpotSalesmanServiceImpl extends BaseServiceImpl<SpotSalesman, SpotSalesmanDao> implements SpotSalesmanService {
	public static final Logger logger = LoggerFactory
			.getLogger(SpotSalesmanServiceImpl.class);
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private SpotSalesmanDao spotSalesmanDao;

	@Override
	public PageInfo<Object> getData(EasyUiPageInfo easyUiPage,
			Map<String, Object> searchParams) {
		String sql = "SELECT "
						+ "s.id, s.name, s.mobile "
					+"FROM "
						+ "s_spot_salesman s "
					+ "WHERE "
						+ " 1=1 AND s.deleted = FALSE";

		return multiListPageQuery(easyUiPage, searchParams, sql, SpotSalesman.class);
	}

	@Override
	public void update(SpotSalesman spotSalesman) throws BusinessException {
		SpotSalesman ss = null;
		
		if (ObjectUtil.equals(null, spotSalesman) 
				|| ObjectUtil.equals(null, ss = getEntityDao().get(spotSalesman.getId()))) {
			throw new SpotSalesmanException("business.update.not.found.data", null);
		}
		
//		ss.setName(spotSalesman.getName());
		BeanUtils.copyProperties(spotSalesman, ss);
//		setOperateLog(ss, "更新现货联系人", "edit");
		super.update(ss);
	}

	@Override
	public void removes(Serializable... ids) throws BusinessException {
		for (Serializable id : ids) {
			SpotSalesman ss = getEntityDao().get(id);
			if (ObjectUtil.equals(ss, null)) {
				throw new SpotSalesmanException("business.delete.not.found.data",
						null);
			}
			ss.setDeleted(Boolean.TRUE);
			setOperateLog(ss, "删除现货联系人", "edit");
			super.update(ss);
//			super.removeById(id);
		}
	}
	
	@Override
	public void save(SpotSalesman ss) {
		String mobile = null;
		if(!ObjectUtil.equals(null, ss)) {
			mobile = ss.getMobile();
			SpotSalesman spotSalesman = spotSalesmanDao.findByMobile(mobile);
			//已存在 更新状态 ..
			if(!ObjectUtil.equals(null, spotSalesman)) {
				spotSalesman.setName(ss.getName());
				spotSalesman.setDeleted(Boolean.FALSE);
				update(spotSalesman);
				return;
			}
			
			SpotSalesman ssm = findOneByMinCtn();
			ss.setCtn(ObjectUtil.equals(null, ssm) 
					|| ObjectUtil.equals(null, ssm.getCtn()) 
					? 0 : ssm.getCtn()); //初始化为表内最小ctn;分配到客户后递增
			setOperateLog(ss, "新增现货联系人", "add");
			super.save(ss);
		}
	}
	
	/**
	 * 记录日志
	 * @MethodName setOperateLog
	 * @author L.Y
	 * @date 2015年10月8日
	 * @param spotSalesman
	 * @param operateContent
	 * @param operateType
	 */
	private void setOperateLog(SpotSalesman spotSalesman, String operateContent,
			String operateType) {
		User loginUser = authService.getCurrentUser();
		spotSalesman.setOperateContent(operateContent);
		if (StringUtil.equals(operateType, "edit")) {
			spotSalesman.setUpdateTime(Dates.getCurrentLongDate());
			spotSalesman.setUpdateUser(loginUser.getRealname());
			spotSalesman.setUpdateUserOrg(loginUser.getOrganization().getName());
			spotSalesman.setUpdateUserId(loginUser.getId());
			return;
		}

		spotSalesman.setCreateTime(Dates.getCurrentLongDate());
		spotSalesman.setCreateUser(loginUser.getRealname());
		spotSalesman.setCreateUserOrg(loginUser.getOrganization().getName());
		spotSalesman.setCreateUserId(loginUser.getId());
		return;
	}

	/**
	 * 查找ctn 最小的记录（获取一条）
	 */
	@Override
	public SpotSalesman findOneByMinCtn() {
		List<SpotSalesman> ss = spotSalesmanDao.findByDeletedFalseOrderByCtnAsc(new PageRequest(0,1));
		if(!CollectionUtils.isEmpty(ss)) {
			return ss.get(0);
		}
		return null;
	}
}