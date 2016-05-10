package com.tzdr.business.service.stock;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.exception.StockException;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.exception.BusinessException;
import com.tzdr.common.utils.Dates;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.dao.stock.StockDao;
import com.tzdr.domain.web.entity.Stock;

/**
 * 
 * @zhouchen
 * 2014年12月26日
 */
@Service
@Transactional
public class StockService extends BaseServiceImpl<Stock,StockDao> {
	public static final Logger logger = LoggerFactory.getLogger(StockService.class);
	
	
	@Autowired
	private AuthService authService;
	
	
	@Override
	public void update(Stock stock) {
		String stockId = stock.getId();
		Stock   dbStock= getEntityDao().get(stockId);
		
		if (ObjectUtil.equals(dbStock, null)){
			throw new StockException("business.update.not.found.data",null);
		}
		
		String code = stock.getCode();
		
		if (!StringUtil.equals(dbStock.getCode(),code)){
			List<Stock> lists = getEntityDao().findByCodeAndDeletedFalseAndType(code,stock.getType());
			if (!CollectionUtils.isEmpty(lists)){
				throw new StockException("stock.code.exist",null);
			}
		}
		dbStock.setName(stock.getName());
		dbStock.setCode(code);
		dbStock.setEffectiveDate(stock.getEffectiveDate());
		setOperateLog(dbStock,"更新股票信息","edit");
		super.update(dbStock);
	}
	
	
	@Override
	public void save(Stock stock) {
		
		List<Stock> lists = getEntityDao().findByCodeAndDeletedFalseAndType(stock.getCode(),stock.getType());
		if (!CollectionUtils.isEmpty(lists)){
			throw new StockException("stock.code.exist",null);
		}
		
		setOperateLog(stock,"新增股票信息","add");
		super.save(stock);
	}

	@Override
    public void removes(Serializable... ids) throws BusinessException {
    	for (Serializable id : ids){
    		Stock stock = getEntityDao().get(id);
    		if (ObjectUtil.equals(stock, null)){
    			throw new StockException("business.delete.not.found.data",null);
    		}
    		stock.setDeleted(Boolean.TRUE);
    		setOperateLog(stock,"删除股票信息","edit");
    		super.update(stock);
    	}
    }
	
	
	private void setOperateLog(Stock stock,String operateContent,String operateType){
		User   loginUser = authService.getCurrentUser();	
		stock.setOperateContent(operateContent);
		if (StringUtil.equals(operateType,"edit")){
			stock.setUpdateTime(Dates.getCurrentLongDate());
			stock.setUpdateUser(loginUser.getRealname());
			stock.setUpdateUserOrg(loginUser.getOrganization().getName());
			stock.setUpdateUserId(loginUser.getId());
			return ;
		}
		
		stock.setCreateTime(Dates.getCurrentLongDate());
		stock.setCreateUser(loginUser.getRealname());
		stock.setCreateUserId(loginUser.getId());
		stock.setCreateUserOrg(loginUser.getOrganization().getName());
		return ;
	}


	/**
	 * 分页显示
	 * @param pageIndex
	 * @param perPage
	 * @return
	 */
	public List<Stock> findData() {
		return this.getEntityDao().findByDeletedFalseAndType(1);
	}
	
	/**
	 * excel导入数据
	 * @param data
	 * @return
	 */
	public String saveByExcelExport(List<Stock> data){
		List<Stock> list=new ArrayList<>();
		if(data!=null){
			for(Stock stock:data){
				if(!CollectionUtils.isEmpty(getEntityDao().findByCodeAndDeletedFalseAndType(stock.getCode(), stock.getType()))){
					if(stock.getType()==2){
						return "股票代码为"+stock.getCode()+"的停牌股记录已存在";
					}
					return "股票代码为"+stock.getCode()+"的限制股记录已存在";
				}
				list.add(stock);
			}
			for(Stock stock:list){
				setOperateLog(stock,"新增股票信息","add");
				super.save(stock);
			}
		}
		return "";
	}
}
