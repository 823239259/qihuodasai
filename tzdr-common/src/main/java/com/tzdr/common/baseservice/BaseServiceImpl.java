package com.tzdr.common.baseservice;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.hibernate.SQLQuery;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.transform.Transformers;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.tzdr.common.auth.Searchable;
import com.tzdr.common.dao.BaseDao;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.exception.BusinessException;
import com.tzdr.common.exception.GeneralException;
import com.tzdr.common.utils.BeanUtils;
import com.tzdr.common.utils.EasyuiUtil;
import com.tzdr.common.utils.GenericsUtils;
import com.tzdr.common.utils.ParseSqlUtils;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.MultiListParam;



/**
 * 基础EntityService的抽象实现。
 * 
 * @author LinFeng
 * @param <T>
 *            被管理的实体类
 * @param <M>
 *            实体类的DAO
 */
public abstract class BaseServiceImpl<T, M extends BaseDao<T>> implements
		ApplicationContextAware, BaseService<T> {

	private M entityDao;
	private ApplicationContext context;

	
	@PersistenceContext
    private EntityManager em;
	
	
	@SuppressWarnings("unchecked")
	protected M getEntityDao() throws BusinessException {
		if (entityDao != null) {
			return entityDao;
		}
		// 获取定义的第一个实例变量类型
		Class<M> daoType = GenericsUtils.getSuperClassGenricType(getClass(), 1);
		List<Field> fields = BeanUtils.getFieldsByType(this, daoType);
		try {
			if (fields != null && fields.size() > 0) {
				entityDao = (M) BeanUtils.getDeclaredProperty(this,
						fields.get(0).getName());
			} else {
				entityDao = (M) context.getBean(daoType);
			}
		} catch (IllegalAccessException e) {
			throw new BusinessException(e.getMessage());
		} catch (NoSuchFieldException e) {
			throw new BusinessException(e.getMessage());
		}
		return entityDao;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.context = applicationContext;
	}

	@Override
	public T get(Serializable id) throws BusinessException {
		try {
			return getEntityDao().get(id);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}

	}

	@Override
	public List<T> getAll() throws BusinessException {
		try {
			return getEntityDao().getAll();
		} catch (DataAccessException e) {
			throw new BusinessException(e.getMessage());
		}
	}

	@Override
	public void remove(T o) throws BusinessException {
		try {
			getEntityDao().remove(o);
		} catch (DataAccessException e) {
			throw new BusinessException(e.getMessage());
		}
	}

	@Override
	public void removeById(Serializable id) throws BusinessException {
		try {
			getEntityDao().removeById(id);
		} catch (DataAccessException e) {
			throw new BusinessException(e.getMessage());
		}

	}

	@Override
	public void removes(Serializable... ids) throws BusinessException {
		try {
			getEntityDao().removes(ids);
		} catch (DataAccessException e) {
			throw new BusinessException(e.getMessage());
		}
	}

	@Override
	public void save(T o) throws BusinessException {
		try {
			getEntityDao().create(o);
		} catch (DataAccessException e) {
			throw new BusinessException(e.getMessage());
		}
	}

	@Override
	public void saves(List<T> ts) throws BusinessException {
		try {
			getEntityDao().saves(ts);
		} catch (DataAccessException e) {
			throw new BusinessException(e.getMessage());
		}
	}

	@Override
	public void update(T o) throws BusinessException {
		try {
			getEntityDao().update(o);
		} catch (DataAccessException e) {
			throw new BusinessException(e.getMessage());
		}
	}

	@Override
	public PageInfo<T> query(PageInfo<T> pageInfo, Map<String, Object> map,
			Map<String, Boolean> orderMap) throws GeneralException {
		try {
			return getEntityDao().query(pageInfo, map, orderMap);
		} catch (DataAccessException e) {
			throw new BusinessException(e.getMessage());
		}
	}

	@Override
	public PageInfo<T> query(PageInfo<T> pageInfo, Map<String, Object> map)
			throws GeneralException {
		return query(pageInfo, map, null);
	}

	@Override
	public List<T> query(Map<String, Object> map, Map<String, Boolean> sortMap) {
		try {
			return getEntityDao().query(map, sortMap);
		} catch (DataAccessException e) {
			throw new BusinessException(e.getMessage());
		}
	}

	
	 /**
     * 另外一种查询方式
     */

    /**
     * 按条件分页并排序查询实体
     *
     * @param searchable 条件
     * @return
     */
    public Page<T> findAll(Searchable searchable) {
        return getEntityDao().findAll(searchable);
    }
    
    /**
     * 按条件不分页不排序查询实体
     *
     * @param searchable 条件
     * @return
     */
    public List<T> findAllWithNoPageNoSort(Searchable searchable) {
        searchable.removePageable();
        searchable.removeSort();
        return Lists.newArrayList(getEntityDao().findAll(searchable).getContent());
    }

    /**
     * 按条件排序查询实体(不分页)
     *
     * @param searchable 条件
     * @return
     */
    public List<T> findAllWithSort(Searchable searchable) {
        searchable.removePageable();
        return Lists.newArrayList(getEntityDao().findAll(searchable).getContent());
    }


    /**
     * 按条件分页并排序统计实体数量
     *
     * @param searchable 条件
     * @return
     */
    public Long count(Searchable searchable) {
        return getEntityDao().count(searchable);
    }
    
    /**
     * 多表分页查询
     * @MethodName multiListPageQuery
     * @author L.Y
     * @param easyUiPage easyUI对象
     * @param searchParams 搜索参数
     * @param sql 表格数据sql
     * @param clazz 实体类
     * @return
     */
    @SuppressWarnings("unchecked")
	public PageInfo<Object>  multiListPageQuery(EasyUiPageInfo easyUiPage, Map<String, Object> searchParams, String sql, Class<?> clazz) 
	{
    	MultiListParam multilistParam = new MultiListParam(easyUiPage,
				searchParams, Lists.newArrayList(), sql);
    	
    	multilistParam = EasyuiUtil.bySearchFilter(multilistParam); // 增加搜索条件
		
		String totalSql = ParseSqlUtils.createCountSql(multilistParam.getSql());
		//" select count(0) totalNum from ("+multilistParam.getSql()+")  tempTab";

		EasyuiUtil.createOrderSql(multilistParam); // 查看是否排序
		
		sql = multilistParam.getSql();
		
		PageInfo<Object>  pageInfo = multilistParam.getPageInfo();
		List<Object> params = multilistParam.getParams();
		
		Query dataQuery = em.createNativeQuery(sql);
		dataQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(clazz)); 
		Query countQuery = em.createNativeQuery(totalSql);
		
		int pageCount = pageInfo.getCountOfCurrentPage();
		dataQuery.setFirstResult((pageInfo.getCurrentPage()-1)*pageCount);
		dataQuery.setMaxResults(pageCount);
		
		
		if (CollectionUtils.isEmpty(params)){
			List<Object> list = dataQuery.getResultList();
			pageInfo.setPageResults(list);
			Long totoalCount =  NumberUtils.toLong(String.valueOf(countQuery.getSingleResult()));
			Long totoalPage = totoalCount/pageCount;
			pageInfo.setTotalPage(totoalPage==0?totoalPage:totoalPage+1);
			pageInfo.setTotalCount(totoalCount);
			return pageInfo;
		}
		
		for (int i=1;i<=params.size();i++){
			dataQuery.setParameter(i, params.get(i-1));
			countQuery.setParameter(i, params.get(i-1));
		}
		
		@SuppressWarnings("rawtypes")
		List list = dataQuery.getResultList();
		pageInfo.setPageResults(list);
		Long totoalCount =  NumberUtils.toLong(String.valueOf(countQuery.getSingleResult()));
		Long totoalPage = totoalCount/pageCount;
		pageInfo.setTotalPage(totoalPage==0?totoalPage:totoalPage+1);
		pageInfo.setTotalCount(totoalCount);
		return pageInfo;
	}

    /**
     * 多表分页查询
     * @param multilistParam
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
	public PageInfo<Object>  multiListPageQuery(MultiListParam multilistParam,Class<?> clazz) 
	{
    	
		EasyuiUtil.bySearchFilter(multilistParam); // 增加搜索条件
		
		String totalSql = ParseSqlUtils.createCountSql(multilistParam.getSql());
		//" select count(0) totalNum from ("+multilistParam.getSql()+")  tempTab";

		EasyuiUtil.createOrderSql(multilistParam); // 查看是否排序
		
		String  sql = multilistParam.getSql();
		
		PageInfo<Object>  pageInfo = multilistParam.getPageInfo();
		List<Object> params = multilistParam.getParams();
		
		Query dataQuery = em.createNativeQuery(sql);
		dataQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(clazz)); 
		Query countQuery = em.createNativeQuery(totalSql);
		
		int pageCount = pageInfo.getCountOfCurrentPage();
		dataQuery.setFirstResult((pageInfo.getCurrentPage()-1)*pageCount);
		dataQuery.setMaxResults(pageCount);
		
		
		if (CollectionUtils.isEmpty(params)){
			List<Object> list = dataQuery.getResultList();
			pageInfo.setPageResults(list);
			Long totoalCount =  NumberUtils.toLong(String.valueOf(countQuery.getSingleResult()));
			Long totoalPage = totoalCount/pageCount;
			pageInfo.setTotalPage(totoalPage==0?totoalPage:totoalPage+1);
			pageInfo.setTotalCount(totoalCount);
			return pageInfo;
		}
		
		for (int i=1;i<=params.size();i++){
			dataQuery.setParameter(i, params.get(i-1));
			countQuery.setParameter(i, params.get(i-1));
		}
		
		@SuppressWarnings("rawtypes")
		List list = dataQuery.getResultList();
		pageInfo.setPageResults(list);
		Long totoalCount =  NumberUtils.toLong(String.valueOf(countQuery.getSingleResult()));
		Long totoalPage = totoalCount/pageCount;
		pageInfo.setTotalPage(totoalPage==0?totoalPage:totoalPage+1);
		pageInfo.setTotalCount(totoalCount);
		return pageInfo;
	}
	
    
    /**
     * 多表分页查询
     * @param multilistParam
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
	public PageInfo<Object>  oldMultiListPageQuery(MultiListParam multilistParam,Class<?> clazz) 
	{
    	
		EasyuiUtil.bySearchFilter(multilistParam); // 增加搜索条件
		
		String totalSql = " select count(0) totalNum from ("+multilistParam.getSql()+")  tempTab";

		EasyuiUtil.createOrderSql(multilistParam); // 查看是否排序
		
		String  sql = multilistParam.getSql();
		
		PageInfo<Object>  pageInfo = multilistParam.getPageInfo();
		List<Object> params = multilistParam.getParams();
		
		Query dataQuery = em.createNativeQuery(sql);
		dataQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(clazz)); 
		Query countQuery = em.createNativeQuery(totalSql);
		
		int pageCount = pageInfo.getCountOfCurrentPage();
		dataQuery.setFirstResult((pageInfo.getCurrentPage()-1)*pageCount);
		dataQuery.setMaxResults(pageCount);
		
		
		if (CollectionUtils.isEmpty(params)){
			List<Object> list = dataQuery.getResultList();
			pageInfo.setPageResults(list);
			Long totoalCount =  NumberUtils.toLong(String.valueOf(countQuery.getSingleResult()));
			Long totoalPage = totoalCount/pageCount;
			pageInfo.setTotalPage(totoalPage==0?totoalPage:totoalPage+1);
			pageInfo.setTotalCount(totoalCount);
			return pageInfo;
		}
		
		for (int i=1;i<=params.size();i++){
			dataQuery.setParameter(i, params.get(i-1));
			countQuery.setParameter(i, params.get(i-1));
		}
		
		@SuppressWarnings("rawtypes")
		List list = dataQuery.getResultList();
		pageInfo.setPageResults(list);
		Long totoalCount =  NumberUtils.toLong(String.valueOf(countQuery.getSingleResult()));
		Long totoalPage = totoalCount/pageCount;
		pageInfo.setTotalPage(totoalPage==0?totoalPage:totoalPage+1);
		pageInfo.setTotalCount(totoalCount);
		return pageInfo;
	}
    
    /**
     * 返回list<map>
     * @param sql
     * @param params
     * @return
     */
    @SuppressWarnings("rawtypes")
	public List nativeQuery(String sql,List<Object> params) {
    	Query dataQuery = em.createNativeQuery(sql);
		dataQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP); 
		if (CollectionUtils.isEmpty(params)){			
			return dataQuery.getResultList();
		}
		
		for (int i=1;i<=params.size();i++){
			dataQuery.setParameter(i, params.get(i-1));
		}
		return dataQuery.getResultList();
    }

   @SuppressWarnings("rawtypes")
   public List  nativeQuery(String sql,List<Object> params,Class<?> clazz){
	    Query dataQuery = em.createNativeQuery(sql);
		dataQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(clazz)); 
		if (CollectionUtils.isEmpty(params)){			
			return dataQuery.getResultList();
		}
		
		for (int i=1;i<=params.size();i++){
			dataQuery.setParameter(i, params.get(i-1));
		}
		return dataQuery.getResultList();
   }
   
   @SuppressWarnings("rawtypes")
   public List  nativeQuery(MultiListParam multilistParam,Class<?> clazz){
	    EasyuiUtil.bySearchFilter(multilistParam); // 增加搜索条件
	    EasyuiUtil.createOrderSql(multilistParam); // 查看是否排序
		String  sql = multilistParam.getSql();
		List<Object> params = multilistParam.getParams();
		
	    Query dataQuery = em.createNativeQuery(sql);
		dataQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(clazz)); 
		if (CollectionUtils.isEmpty(params)){			
			return dataQuery.getResultList();
		}
		
		for (int i=1;i<=params.size();i++){
			dataQuery.setParameter(i, params.get(i-1));
		}
		return dataQuery.getResultList();
   }
   
   /**
    * 如果sql中含有in 函数，根据paramSize =3生成（?,?,?）
    * 
    * @param
    * @param paramSize
    * @return
    */
   public  String getQueryInSql(int paramSize){
	  StringBuffer builder = new StringBuffer("(");
	  for (int i=0;i<paramSize;i++){
		  builder.append("?");
		  if ((paramSize-1)==i){
			  continue;
		  }
		  builder.append(",");
	  }
	  builder.append(")");
	  return builder.toString();
   }
   
   /**
    * 生成 按后台管理用户 分级进行 管理方案数据
    * @param sql
    * @return
    */
   public String getPermissionSql(String sql,String code,List<Object> params,Long longuserId){
	   StringBuffer builder = new StringBuffer("select temp.*  from ( ");
	   builder.append(sql);
	   builder.append(" ) temp ,");
	   builder.append("sys_user us, sys_organization org, w_account wa WHERE temp.accountId = wa.id AND us.id = wa.create_user_id AND org.id = us.organization_id AND org.`code` LIKE ?");
	  
	   if (code.length() >= 12){
		    builder.append(" and  wa.create_user_id=? ");
			params.add(longuserId);
		}
	   return builder.toString();
   }
   /**
    * 查询当个结果集
    * @param sql
    * @param params
    * @param
    * @return
    */
   public Object  nativeQueryOne(String sql,List<Object> params){
	    Query dataQuery = em.createNativeQuery(sql);
		if (!CollectionUtils.isEmpty(params)){	
			for (int i=1;i<=params.size();i++){
				dataQuery.setParameter(i, params.get(i-1));
			}
		}
		Object result = 0;
		try {
			result = dataQuery.getSingleResult();
		} catch (Exception e) {
		}
		return result;
   }
 
   /**
    * 纯sql 的更新操作
    * @param sql
    * @param params
    * @return
    */
   public int  nativeUpdate(String sql,List<Object> params){
	    Query update = em.createNativeQuery(sql);
		if (CollectionUtils.isEmpty(params)){	
			return update.executeUpdate();
		}
		
		for (int i=1;i<=params.size();i++){
			update.setParameter(i, params.get(i-1));
		}
		return update.executeUpdate();
   }
   
   /**
    * 批量保存数据
    * @param sql
    * @param batchSize
    * @param list
    * @throws SQLException
    */
   public void batchSave(String sql,int batchSize,List<Object[]> list) throws SQLException{
	   if (CollectionUtils.isEmpty(list)){
		   return;
	   }
	  
	   SessionImplementor session =em.unwrap(SessionImplementor.class);
   	   Connection connection =   session.connection();
	   PreparedStatement st = connection.prepareStatement(sql);

	   for(int i=0;i<list.size();i++){
		   Object[] params = list.get(i);
		   if (ArrayUtils.isEmpty(params)){
			   continue;
		   }
		   
		   for (int j=1;j<=params.length;j++){
			   st.setString(j,String.valueOf(params[j-1]));
		   }
		   st.addBatch();
		   if(i > 0 && i%batchSize==0){
			   st.executeBatch();
			   st.clearBatch();
		   }
	   }
	  st.executeBatch();
   }

	public void nativeQuery(String mobile){
		Query  query = em.createNativeQuery("call lowerUserRebate(:mobile,0)");
		query.setParameter("mobile",mobile);
		query.executeUpdate();
	}
}