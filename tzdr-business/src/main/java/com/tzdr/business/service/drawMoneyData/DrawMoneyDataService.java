package com.tzdr.business.service.drawMoneyData;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.domain.vo.DrawMoneyDataVo;
import com.tzdr.domain.web.entity.DrawMoneyData;

public interface DrawMoneyDataService  extends BaseService<DrawMoneyData>{

	/**
	 * 分页查询
	 * @param dataPage
	 * @param vo
	 * @return
	 */
	PageInfo<DrawMoneyDataVo> queryData(PageInfo<DrawMoneyDataVo> dataPage,
			DrawMoneyDataVo vo);

	/**
	 * 查询类型查询配置
	 * @return
	 */
	DrawMoneyData getAduitMoneyByType(String type);

	/**
	 * 根据类型和金额查询配置 主要是手工提款
	 * @param type
	 * @param money
	 * @return
	 */
	DrawMoneyData getAduitMoneyByType(String type, Double money);

}
