package com.tzdr.common.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConnditionVo;



/**
 * <p>Spring Data with Jpa 基础DAO接口</p>
 * 
 * 扩展该接口是为了兼容原来的框架API 使用定位：用户后台管理型业务系统开发，后端使用Hibernate，对于新能要求是不太高的系统推荐使用。
 * 核心能力：使用JPA方式开发DAO，只写接口，不用写实现。支持一层的多条件组合查询。如果需要特殊功能，可以为特定的DAO扩展JDBC实现。
 * 
 * @author Lin Feng
 * @author QingLiu
 * @see
 * @version 2.0
 * 
 * @param <T>
 * @param <ID>
 * 
 */
public interface BaseJpaDao<T, ID extends Serializable> extends
		JpaRepository<T, ID>, BaseDao<T>,JpaSpecificationExecutor<T>  {

	/**
	 * 
	 * @param page DataPage<T> 
	 * @param equals Map<String,Object> 等值查询
	 * @param isLike Map<String,String> like 查询
	 * @param groupNames  Map<String,String>分组查询条件
	 * @param polys 聚合函数
	 * @param orders Map<String,Boolean> 排序查询
	 * @return DataPage<T> 
	 */
	public PageInfo<T> queryDataPageByConndition(PageInfo<T> page,
			Map<String,Object> equals,Map<String,String> isLike,Map<String,String> groupNames,
			Map<String,String> polys,Map<String,Boolean> orders);
	
	
	/**
	 * 
	 * @param page DataPage<T> 
	 * @param equals Map<String,Object> 等值查询
	 * @param notEquals Map<String,Object> 不等值查询
	 * @param isLike Map<String,String> like 查询
	 * @param groupNames  Map<String,String>分组查询条件
	 * @param polys 聚合函数
	 * @param orders Map<String,Boolean> 排序查询
	 * @return DataPage<T> 
	 */
	public PageInfo<T> queryDataPageByConndition(PageInfo<T> page,
			Map<String,Object> equals,Map<String,Object> notEquals,Map<String,String> isLike,Map<String,String> groupNames,
			Map<String,String> polys,Map<String,Boolean> orders);
	
	/**
	 * 
	 * @param page DataPage<T> 
	 * @param equals Map<String,Object> 等值查询
	 * @param notEquals Map<String,Object> 不等值查询
	 * @param isLike Map<String,String> like 查询
	 * @param groupNames  Map<String,String>分组查询条件
	 * @param polys 聚合函数
	 * @param orders Map<String,Boolean> 排序查询
	 * @return DataPage<T> 
	 */
	public PageInfo<T> queryDataPageByConndition(PageInfo<T> page,
			Map<String,Object> equals,Map<String,Object> notEquals,Map<String,String> isLike,Map<String,String> groupNames,
			Map<String,String> polys,Map<String,Object[]> between,Map<String,Boolean> orders);
	
	/**
	 * 
	 * 
	 * @param equals Map<String,Object> 等值查询
	 * @param isLike Map<String,String> like 查询
	 * @param orders Map<String,Boolean> 排序查询
	 * @return List<T>
	 */
	public List<T> queryBySimple(Map<String,Object> equals,Map<String,String> isLike,Map<String,Boolean> orders);
	
    /**
     * 
     * @param equals
     * @param isLike
     * @param isNullNotNull
     * @param orders
     * @return
     */
	public List<T> queryBySimpleNull(Map<String,Object> equals,Map<String,String> isLike,Map<String,Boolean> isNullNotNull, Map<String,Boolean> orders);
	
	/**
	 * 
	 * 
	 * @param equals Map<String,Object> 等值查询
	 * @param isLike Map<String,String> like 查询
	 * @param orders Map<String,Boolean> 排序查询
	 * @return List<T>
	 */
	public List<T> queryBySimple(Map<String,Object> equals,Map<String,Object> notEquals,Map<String,String> isLike,Map<String,Boolean> orders);
	
	/**
	 * 查询页面
	 * @param page DataPage<T>
	 * @param jpql String
	 * @param params Object ...
	 * @return DataPage<T>
	 */
	public PageInfo<T> queryDataPageByJpql(PageInfo<T> page,String jpql,Object...params);
	
	/**
	 * 查询页面
	 * 建议使用queryPageByJpql(PageInfo page,String jpql,Class clazz,ConnditionVo connVo,Object...params)
	 * 因本方法不支持方法注解
	 * @param page DataPage<T>
	 * @param jpql String
	 * @param params Object ...
	 * @return DataPage<T>
	 */
	@Deprecated
	public PageInfo queryDataPageByJpql(PageInfo page,String jpql,Class clazz,Object...params);
	
	
	/**
	 * 查询页面
	 * @param page DataPage<T>
	 * @param jpql String
	 * @param params Object ...
	 * @return DataPage<T>
	 */
	public PageInfo queryPageByJpql(PageInfo page,String jpql,Class clazz,ConnditionVo connVo,Object...params);
	
	
	/**
	 * @since
	 * 查询页面
	 * public PageInfo queryPageBySql(PageInfo page,String sql,Class clazz,ConnditionVo connVo,Object...params);
	 * 因本方法不支持方法注解及别名
	 * @param page DataPage<T>
	 * @param jpql String
	 * @param params Object ...
	 * @return DataPage<T>
	 */
	@Deprecated
	public PageInfo queryDataPageBySql(PageInfo page,String sql,Class clazz,Object...params);
	
	/**
	 * 查询页面
	 * @param page DataPage<T>
	 * @param jpql String
	 * @param params Object ...
	 * @return DataPage<T>
	 */
	public PageInfo queryPageBySql(PageInfo page,String sql,Class clazz,ConnditionVo connVo,Object...params);
	
	/**
	 * 
	 * @param sql
	 * @param params
	 * @return Map<String,Object>
	 */
	public List<Map<String,Object>> queryMapBySql(String sql,Object...params);
	
	
	
	/**
	 * 
	 * @param sql
	 * @param params
	 * @return Map<String,Object>
	 */
	public Map<String,Object> queryMapObj(String sql,Object...params);
	
	/**
	 * 查询页面
	 * 建议使用 public List queryListBySql(String sql,Class clazz,Object...params);
	 * @param sql String
	 * @param clazz Class
	 * @param params Object ...
	 * @return List
	 */
	@Deprecated
	public List queryDataBySql(String sql,Class clazz,Object...params);
	
	/**
	 * 查询页面
	 * @param sql String
	 * @param clazz Class
	 * @param params Object ...
	 * @return List
	 */
	public List queryListBySql(String sql,Class clazz,ConnditionVo connVo,Object...params);
	
	/**
	 * 
	 * @see 
	 * 查询页面
	 * 建议使用 public List queryDataByParamsSql(String sql,Class clazz,Map<String,Object> paramValues,ConnditionVo connVo)
	 * public List queryDataByParamsSql(String sql,Class clazz,ConnditionVo connVo,String[] paramNames,Object...values)
	 * @param sql String
	 * @param clazz Class
	 * @param params Object ...
	 * @return List
	 */
	@Deprecated
	public List queryDataByParamNameSql(String sql,Class clazz,String[] paramNames,Object...values);
	
    /**
     * 查询参数
     * @param sql String
     * @param clazz Class
     * @param paramValues Map<String,Object>
     * @param connVo ConnditionVo
     * @return List
     */
	public List queryDataByParamsSql(String sql,Class clazz,Map<String,Object> paramValues,ConnditionVo connVo);
	
	/**
     * 查询参数
     * @param sql String
     * @param clazz Class
     * @param paramValues Map<String,Object>
     * @param connVo ConnditionVo
     * @return List
     */
	public PageInfo queryDataByParamsSql(PageInfo page,String sql,Class clazz,Map<String,Object> paramValues,ConnditionVo connVo);
	
	/**
     * 查询参数
     * @param sql String
     * @param clazz Class
     * @param paramValues Map<String,Object>
     * @param connVo ConnditionVo
     * @return List
     */
	public List queryDataByParamsSql(String sql,Class clazz,ConnditionVo connVo,String[] paramNames,Object...values);
	
	
	/**
	 * 查询页面
	 * @param jpql String
	 * @param params Object ...
	 * @return DataPage<T>
	 */
	@Deprecated
	public List queryBySql(String sql,Class<?> clazz,Object...params);
	
	/**
	 * 
	 * @param sql String
	 * @param clazz Class<?>
	 * @param connVo ConnditionVo
	 * @param params Object..
	 * @return List
	 */
	public List queryBySql(String sql,Class<?> clazz,ConnditionVo connVo,Object...params);
	
	
	/**
     * 查询参数
     * @param sql String
     * @param clazz Class
     * @param paramValues Map<String,Object>
     * @param connVo ConnditionVo
     * @return List
    
	public PageInfo queryDataByParamsSql(PageInfo page,String sql,Class clazz,Map<String,Object> paramValues,ConnditionVo connVo);
	 */
	

}
