package com.tzdr.business.service.spot;

import java.util.Map;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.domain.web.entity.SpotSalesman;

/**
 * 现货销售员 service接口
 * @ClassName SpotSalesmanService
 * @author L.Y
 * @email liuyuan@peigubao.com
 * @date 2015年10月8日
 */
public interface SpotSalesmanService extends BaseService<SpotSalesman> {

	/**
	 * 获取列表数据
	 * @MethodName getData
	 * @author L.Y
	 * @date 2015年10月9日
	 * @param easyUiPage
	 * @param searchParams
	 * @return
	 */
	PageInfo<Object> getData(EasyUiPageInfo easyUiPage,
			Map<String, Object> searchParams);

	/**
	 * 查找ctn 最小的记录（获取一条）
	 * @MethodName findOneByMinCtn
	 * @author L.Y
	 * @date 2015年10月9日
	 * @return
	 */
	SpotSalesman findOneByMinCtn();
}