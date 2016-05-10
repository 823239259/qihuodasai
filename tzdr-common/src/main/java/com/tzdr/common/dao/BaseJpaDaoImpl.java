package com.tzdr.common.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import jodd.util.StringUtil;

import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tzdr.common.auth.SearchCallback;
import com.tzdr.common.auth.Searchable;
import com.tzdr.common.dao.support.DynamicSpecifications;
import com.tzdr.common.dao.support.SearchFilter;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.utils.RepositoryHelper;
import com.tzdr.common.utils.TypeConvert;

/**
 * 
 * <p>JpaData 基类实现接口</p>
 * @author Lin Feng
 * @author QingLiu
 * @see
 * @version 2.0
 * 2015年3月2日下午5:09:27
 * @param <T>
 * @param <ID>
 */
public class BaseJpaDaoImpl<T, ID extends Serializable> extends
		SimpleJpaRepository<T, ID> implements BaseJpaDao<T, ID> {

	private Class<T> domainClass;
	private EntityManager em;	
	private final JpaEntityInformation<T, ID> entityInformation;
	private String entityName;
	
	 private final RepositoryHelper repositoryHelper;
	
    public static final String FIND_QUERY_STRING = "from %s x where 1=1 ";
    public static final String COUNT_QUERY_STRING = "select count(x) from %s x where 1=1 ";
	
	 /**
     * 查询所有的QL
     */
    private String findAllQL;
    /**
     * 统计QL
     */
    private String countAllQL;
	
	private SearchCallback searchCallback = SearchCallback.DEFAULT;

	public BaseJpaDaoImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager em) {
		super(entityInformation, em);
		this.em = em;
		this.entityInformation = entityInformation;
	    this.domainClass = this.entityInformation.getJavaType();
	    this.entityName = this.entityInformation.getEntityName();		
		 repositoryHelper = new RepositoryHelper(domainClass,em);
		 findAllQL = String.format(FIND_QUERY_STRING, entityName);
	     countAllQL = String.format(COUNT_QUERY_STRING, entityName);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(Serializable id) throws DataAccessException {
		return findOne((ID) id);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void create(T o) throws DataAccessException {
		save(o);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void update(T o) throws DataAccessException {
		save(o);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void saves(List<T> entities) {
		save(entities);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void remove(T o) throws DataAccessException {
		delete(o);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@SuppressWarnings("unchecked")
	@Override
	public void removeById(Serializable id) throws DataAccessException {
		delete((ID) id);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void removes(Serializable... ids) {
		Iterator<Serializable> iterator = Arrays.asList(ids).iterator();
		String queryString = String.format(QueryUtils.DELETE_ALL_QUERY_STRING,
				getEntityName());
		String alias = QueryUtils.detectAlias(queryString);
		StringBuilder builder = new StringBuilder(queryString);
		builder.append(" where");
		int i = 0;
		while (iterator.hasNext()) {
			iterator.next();
			builder.append(String.format(" %s.id = ?%d", alias, ++i));
			if (iterator.hasNext()) {
				builder.append(" or");
			}
		}
		Query query = em.createQuery(builder.toString());
		iterator = Arrays.asList(ids).iterator();
		i = 0;
		while (iterator.hasNext()) {
			query.setParameter(++i, iterator.next());
		}
		query.executeUpdate();
	}

	@Override
	public List<T> getAll() throws DataAccessException {
		return findAll();
	}

	@Override
	public List<T> query(Map<String, Object> map, Map<String, Boolean> sortMap) {
		Specification<T> spec = buildSpecification(map);
		Sort sort = buildSort(sortMap);
		return findAll(spec, sort);
	}

	@Override
	public PageInfo<T> query(PageInfo<T> pageInfo, Map<String, Object> map,
			Map<String, Boolean> sortMap) throws DataAccessException {
		Specification<T> spec = buildSpecification(map);
		Sort sort = buildSort(sortMap);
		Pageable pageable = new PageRequest(pageInfo.getCurrentPage() - 1,
				pageInfo.getCountOfCurrentPage(), sort);
		Page<T> page = findAll(spec, pageable);
		pageInfo.setPageResults(page.getContent());
		pageInfo.setTotalCount(page.getTotalElements());
		pageInfo.setTotalPage(page.getTotalPages());
		return pageInfo;
	}

	protected Class<T> getDomainClass() {
		return domainClass;
	}

	protected EntityManager getEntityManager() {
		return em;
	}

	protected String getEntityName() {
		return domainClass.getSimpleName();
	}

	/**
	 * 生成查询条件
	 * 
	 * @param map
	 * @return
	 */
	private Specification<T> buildSpecification(Map<String, Object> map) {
		List<SearchFilter> searchFilters = SearchFilter.parse(map);
		return DynamicSpecifications.bySearchFilter(searchFilters, null);
	}

	/**
	 * 生成查询排序
	 * 
	 * @param orderMap
	 * @return
	 */
	private Sort buildSort(Map<String, Boolean> orderMap) {
		List<Order> orders = new ArrayList<Order>();
		if (orderMap != null && orderMap.size() > 0) {
			for (Map.Entry<String, Boolean> entry : orderMap.entrySet()) {
				orders.add(new Order(entry.getValue() ? Direction.ASC
						: Direction.DESC, entry.getKey()));
			}
		} else {
			orders.add(new Order(Direction.DESC, "id"));
		}
		Sort sort = new Sort(orders);
		return sort;
	}

	@Override
	public Page<T> findAll(final Searchable searchable) {
		 List<T> list = repositoryHelper.findAll(findAllQL, searchable, searchCallback);
	        long total = searchable.hasPageable() ? count(searchable) : list.size();
	        return new PageImpl<T>(
	                list,
	                searchable.getPage(),
	                total
	        );
	}
	
	@Override
	public long count(final Searchable searchable) {
	     return repositoryHelper.count(countAllQL, searchable, searchCallback);
	}

	@Override
	public PageInfo<T> queryDataPageByConndition(final PageInfo<T> page,
			final Map<String, Object> equals, final Map<String, String> isLike,
			final Map<String, String> groupNames,final Map<String, String> polys,
			final Map<String, Boolean> orders) {
		Page<T> pageData = this.findAll(new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
			
				List<Predicate> pres = new ArrayList<Predicate>();
				if (equals != null  && equals.size() > 0) {
					for (Map.Entry<String, Object> equalObj:equals.entrySet()) {
						String[] names = StringUtil.split(equalObj.getKey(), ".");
						if (names.length > 1) {
							Path expression = root.get(names[0]);
							expression = expression.get(names[1]);
							Predicate pre = cb.equal(expression, equalObj.getValue());
							pres.add(pre);
						}
						else {
							Path<Object> path = root.get(equalObj.getKey());
							Predicate pre = cb.equal(path, equalObj.getValue());
							pres.add(pre);
						}
					}
				}
				if (isLike != null && isLike.size() > 0) {
					for (Map.Entry<String, String> isLikeObj:isLike.entrySet()) {
						String[] names = StringUtil.split(isLikeObj.getKey(), ".");
						if (names.length > 1) {
							Path expression = root.get(names[0]);
							expression = expression.get(names[1]);
							Predicate pre = cb.like(expression, "%" + isLikeObj.getValue() + "%");
							pres.add(pre);
						}
						else {
							Path<String> path = root.get(isLikeObj.getKey());
							Predicate pre = cb.like(path, "%" + isLikeObj.getValue() + "%");
							pres.add(pre);
						}
					}
				}
				
				if (groupNames != null) {
			    	List<Path> groups = new ArrayList<Path>();
			    	for (Map.Entry<String, String> groupName:groupNames.entrySet()) {
			    		String[] names = StringUtil.split(groupName.getKey(), ".");
						if (names.length > 1) {
							Path expression = root.get(names[0]);
							expression = expression.get(names[1]);
							groups.add(expression);
							//query.groupBy(expression);
						}
						else {
							Path<String> expression = root.get(groupName.getKey());
							groups.add(expression);
							//query.groupBy(expression);
						}
			    	}
			    	if (groups != null && groups.size() > 0) {
			    		query.groupBy(groups.toArray(new Path[groups.size()]));
			    		query.getGroupRestriction();
			    	}
				}
				
				List<javax.persistence.criteria.Order> queryOrders = new ArrayList<javax.persistence.criteria.Order>();
				if (orders != null && orders.size() > 0) {
					for (Map.Entry<String, Boolean> order:orders.entrySet()) {
						if (order.getValue()) {
							queryOrders.add(cb.asc(root.get(order.getKey())));
						}
						else {
							queryOrders.add(cb.desc(root.get(order.getKey())));
						}
					}
				}
				if (queryOrders.size() > 0) {
					query.orderBy(queryOrders);
				}
				CriteriaQuery q = cb.createQuery();
				
				if (polys != null && polys.size() > 0) {
					List<Expression> polies = new ArrayList<Expression>();
					for (Map.Entry<String, String> polymerization:polys.entrySet()) {
						String[] names = StringUtil.split(polymerization.getKey(), ".");
						if (names.length > 1) {
							Path expression = root.get(names[0]);
							expression = expression.get(names[1]);
							Expression exp = TypeConvert.settingExpression(cb, expression, polymerization.getValue());
							polies.add(exp);
						}
						else {
							Path expression = root.get(polymerization.getKey());
							Expression exp = TypeConvert.settingExpression(cb, expression, polymerization.getValue());
							polies.add(exp);
						}
					}
				    if (polies != null) {
				    	for (Expression exp:polies) {
				    		q.select(exp);
				    	}
				    }
				}
				return cb.and(pres.toArray(new Predicate[pres.size()]));
				
			}
		}, new PageRequest(page.getCurrentPage(),page.getCountOfCurrentPage()));
		page.setTotalCount((int)pageData.getTotalElements());
		page.setPageResults(pageData.getContent());
		return page;
	}
	
	
	@Override
	public List<T> queryBySimple(final Map<String, Object> equals,final Map<String,Object> notEquals,
			final Map<String, String> isLike, final Map<String, Boolean> orders) {
		
		List<T> data = this.findAll(new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
			
				List<Predicate> pres = new ArrayList<Predicate>();
				if (equals != null  && equals.size() > 0) {
					for (Map.Entry<String, Object> equalObj:equals.entrySet()) {
						String[] names = StringUtil.split(equalObj.getKey(), ".");
						if (names.length > 1) {
							Path expression = root.get(names[0]);
							expression = expression.get(names[1]);
							Predicate pre = cb.equal(expression, equalObj.getValue());
							pres.add(pre);
						}
						else {
							Path<Object> path = root.get(equalObj.getKey());
							Predicate pre = cb.equal(path, equalObj.getValue());
							pres.add(pre);
						}
					}
				}
				
				if (notEquals != null  && notEquals.size() > 0) {
					for (Map.Entry<String, Object> equalObj:notEquals.entrySet()) {
						String[] names = StringUtil.split(equalObj.getKey(), ".");
						if (names.length > 1) {
							Path expression = root.get(names[0]);
							expression = expression.get(names[1]);
							Predicate pre = cb.notEqual(expression, equalObj.getValue());
							pres.add(pre);
						}
						else {
							Path<Object> path = root.get(equalObj.getKey());
							Predicate pre = cb.notEqual(path, equalObj.getValue());
							pres.add(pre);
						}
					}
				}
				
				
				if (isLike != null && isLike.size() > 0) {
					for (Map.Entry<String, String> isLikeObj:isLike.entrySet()) {
						String[] names = StringUtil.split(isLikeObj.getKey(), ".");
						if (names.length > 1) {
							Path expression = root.get(names[0]);
							expression = expression.get(names[1]);
							Predicate pre = cb.like(expression, "%" + isLikeObj.getValue() + "%");
							pres.add(pre);
						}
						else {
							Path<String> path = root.get(isLikeObj.getKey());
							Predicate pre = cb.like(path, "%" + isLikeObj.getValue() + "%");
							pres.add(pre);
						}
					}
				}
				List<javax.persistence.criteria.Order> queryOrders = 
						new ArrayList<javax.persistence.criteria.Order>();
				if (orders != null && orders.size() > 0) {
					for (Map.Entry<String, Boolean> order:orders.entrySet()) {
						if (order.getValue()) {
							queryOrders.add(cb.asc(root.get(order.getKey())));
						}
						else {
							queryOrders.add(cb.desc(root.get(order.getKey())));
						}
					}
				}
				if (queryOrders.size() > 0) {
					query.orderBy(queryOrders);
				}
				cb.createQuery();
				return cb.and(pres.toArray(new Predicate[pres.size()]));
				
			}
		});
		return data;
	}
	
	
	
	
	
	
	@Override
	public List<T> queryBySimple(final Map<String, Object> equals,
			final Map<String, String> isLike, final Map<String, Boolean> orders) {
		
		List<T> data = this.findAll(new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
			
				List<Predicate> pres = new ArrayList<Predicate>();
				if (equals != null  && equals.size() > 0) {
					for (Map.Entry<String, Object> equalObj:equals.entrySet()) {
						String[] names = StringUtil.split(equalObj.getKey(), ".");
						if (names.length > 1) {
							Path expression = root.get(names[0]);
							expression = expression.get(names[1]);
							Predicate pre = cb.equal(expression, equalObj.getValue());
							pres.add(pre);
						}
						else {
							Path<Object> path = root.get(equalObj.getKey());
							Predicate pre = cb.equal(path, equalObj.getValue());
							pres.add(pre);
						}
					}
				}
				if (isLike != null && isLike.size() > 0) {
					for (Map.Entry<String, String> isLikeObj:isLike.entrySet()) {
						String[] names = StringUtil.split(isLikeObj.getKey(), ".");
						if (names.length > 1) {
							Path expression = root.get(names[0]);
							expression = expression.get(names[1]);
							Predicate pre = cb.like(expression, "%" + isLikeObj.getValue() + "%");
							pres.add(pre);
						}
						else {
							Path<String> path = root.get(isLikeObj.getKey());
							Predicate pre = cb.like(path, "%" + isLikeObj.getValue() + "%");
							pres.add(pre);
						}
					}
				}
				List<javax.persistence.criteria.Order> queryOrders = 
						new ArrayList<javax.persistence.criteria.Order>();
				if (orders != null && orders.size() > 0) {
					for (Map.Entry<String, Boolean> order:orders.entrySet()) {
						if (order.getValue()) {
							queryOrders.add(cb.asc(root.get(order.getKey())));
						}
						else {
							queryOrders.add(cb.desc(root.get(order.getKey())));
						}
					}
				}
				if (queryOrders.size() > 0) {
					query.orderBy(queryOrders);
				}
				cb.createQuery();
				return cb.and(pres.toArray(new Predicate[pres.size()]));
				
			}
		});
		return data;
	}
	
	
	@Override
	public List<T> queryBySimpleNull(final Map<String, Object> equals,
			final Map<String, String> isLike, final Map<String,Boolean> isNullNotNull,final Map<String, Boolean> orders) {
		
		List<T> data = this.findAll(new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
			
				List<Predicate> pres = new ArrayList<Predicate>();
				if (equals != null  && equals.size() > 0) {
					for (Map.Entry<String, Object> equalObj:equals.entrySet()) {
						String[] names = StringUtil.split(equalObj.getKey(), ".");
						if (names.length > 1) {
							Path expression = root.get(names[0]);
							expression = expression.get(names[1]);
							Predicate pre = cb.equal(expression, equalObj.getValue());
							pres.add(pre);
						}
						else {
							Path<Object> path = root.get(equalObj.getKey());
							Predicate pre = cb.equal(path, equalObj.getValue());
							pres.add(pre);
						}
					}
				}
				if (isLike != null && isLike.size() > 0) {
					for (Map.Entry<String, String> isLikeObj:isLike.entrySet()) {
						String[] names = StringUtil.split(isLikeObj.getKey(), ".");
						if (names.length > 1) {
							Path expression = root.get(names[0]);
							expression = expression.get(names[1]);
							Predicate pre = cb.like(expression, "%" + isLikeObj.getValue() + "%");
							pres.add(pre);
						}
						else {
							Path<String> path = root.get(isLikeObj.getKey());
							Predicate pre = cb.like(path, "%" + isLikeObj.getValue() + "%");
							pres.add(pre);
						}
					}
				}
				if (isNullNotNull != null && isNullNotNull.size() > 0) {
					for (Map.Entry<String, Boolean> nullObj:isNullNotNull.entrySet()) {
						String[] names = StringUtil.split(nullObj.getKey(), ".");
						if (names.length > 1) {
							Path expression = root.get(names[0]);
							expression = expression.get(names[1]);
							if (nullObj.getValue()) {
								Predicate pre = cb.isNull(expression);
								pres.add(pre);
							}
							else {
								Predicate pre = cb.isNotNull(expression);
								pres.add(pre);
							}
						}
						else {
							Path<String> path = root.get(nullObj.getKey());
							if (nullObj.getValue()) {
								Predicate pre = cb.isNull(path);
								pres.add(pre);
							}
							else {
								Predicate pre = cb.isNull(path);
								pres.add(pre);
							}
						}
					}
				}
				
				List<javax.persistence.criteria.Order> queryOrders = 
						new ArrayList<javax.persistence.criteria.Order>();
				if (orders != null && orders.size() > 0) {
					for (Map.Entry<String, Boolean> order:orders.entrySet()) {
						if (order.getValue()) {
							queryOrders.add(cb.asc(root.get(order.getKey())));
						}
						else {
							queryOrders.add(cb.desc(root.get(order.getKey())));
						}
					}
				}
				if (queryOrders.size() > 0) {
					query.orderBy(queryOrders);
				}
				cb.createQuery();
				return cb.and(pres.toArray(new Predicate[pres.size()]));
				
			}
		});
		return data;
	}
	
	
	

	@Override
	public PageInfo<T> queryDataPageByJpql(PageInfo<T> page, String jpql,
			Object... params) {
		Query query = this.em.createQuery(jpql);
		int i = 0;
		if (params != null && params.length > 0) {
			for (Object obj:params) {
				query.setParameter(++i, obj);
			}
		}
		query.setFirstResult((page.getCurrentPage() - 1) * page.getCountOfCurrentPage());
		query.setMaxResults(page.getCountOfCurrentPage());
		page.setPageResults(query.getResultList());
		return page;
	}

	@Override
	@Deprecated
	public PageInfo queryDataPageByJpql(PageInfo page, String jpql,Class clazz,
			Object... params) {
		Query query = this.em.createQuery(jpql);
		int i = 0;
		if (params != null && params.length > 0) {
			for (Object obj:params) {
				query.setParameter(++i, obj);
			}
		}
		query.setFirstResult((page.getCurrentPage() - 1) * page.getCountOfCurrentPage());
		query.setMaxResults(page.getCountOfCurrentPage());
		List data = TypeConvert.objectsToListData(query.getResultList(),clazz);
		page.setPageResults(data);
		
		//设置数量
		int n = 0;
		Query countQuery = this.em.createQuery(TypeConvert.jpqlToCountJpql(jpql));
		if (params != null && params.length > 0) {
			for (Object obj:params) {
				countQuery.setParameter(++n, obj);
			}
		}
		int total = 0;
		List<Long> countObj = countQuery.getResultList();
		if (countObj != null && countObj.size() > 0) {
			if (countObj.size() == 1) {
				total += countObj.get(0).longValue();
			}
			else {
				total = countObj.size();
			}
		}
		page.setTotalCount(total);
		return page;
	}
	
	@Override
	public PageInfo queryPageByJpql(PageInfo page, String jpql,Class clazz,ConnditionVo connVo,
			Object... params) {
		if (connVo != null && connVo.getSortFieldName() != null) {
			jpql = connVo.autoOrderBy(jpql, clazz);
		}
		Query query = this.em.createQuery(jpql);
		int i = 0;
		if (params != null && params.length > 0) {
			for (Object obj:params) {
				query.setParameter(++i, obj);
			}
		}
		SQLQuery sqlQuery = null;
		try {
			Field field = query.getClass().getDeclaredField("query");
			field.setAccessible(true);
			sqlQuery = (SQLQuery)field.get(query);
			field.setAccessible(false);
			sqlQuery.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		query.setFirstResult((page.getCurrentPage() - 1) * page.getCountOfCurrentPage());
		query.setMaxResults(page.getCountOfCurrentPage());
		List data = TypeConvert.objectsToListDataByMethod(query.getResultList(),clazz);
		page.setPageResults(data);
		
		//设置数量
		int n = 0;
		Query countQuery = this.em.createQuery(TypeConvert.jpqlToCountJpql(jpql));
		if (params != null && params.length > 0) {
			for (Object obj:params) {
				countQuery.setParameter(++n, obj);
			}
		}
		int total = 0;
		List<Long> countObj = countQuery.getResultList();
		if (countObj != null && countObj.size() > 0) {
			if (countObj.size() == 1) {
				total += countObj.get(0).longValue();
			}
			else {
				total = countObj.size();
			}
		}
		page.setTotalCount(total);
		return page;
	}
	
	
	
	
	@Override
	@Deprecated
	public PageInfo queryDataPageBySql(PageInfo page,String sql,Class clazz,Object...params) {
		
		Query query = this.em.createNativeQuery(sql);
		int i = 0;
		if (params != null && params.length > 0) {
			for (Object obj:params) {
				query.setParameter(++i, obj);
			}
		}
		
		query.setFirstResult((page.getCurrentPage() - 1) * page.getCountOfCurrentPage());
		query.setMaxResults(page.getCountOfCurrentPage());
		List data = TypeConvert.objectsToListData(query.getResultList(),clazz);
		page.setPageResults(data);
		
		//设置数量
		int n = 0;
		Query countQuery = this.em.createNativeQuery(TypeConvert.sqlToCountSql(sql));
		if (params != null && params.length > 0) {
			for (Object obj:params) {
				countQuery.setParameter(++n, obj);
			}
		}
		int total = 0;
		List<BigInteger> countObj = countQuery.getResultList();
		if (countObj != null && countObj.size() > 0) {
			if (countObj.size() == 1) {
				total += countObj.get(0).longValue();
			}
			else {
				total = countObj.size();
			}
		}
		page.setTotalCount(total);
		return page;
	}
	
	@Override
	public PageInfo queryPageBySql(PageInfo page,String sql,Class clazz,ConnditionVo connVo,Object...params) {
		if (connVo != null && connVo.getSortFieldName() != null) {
			sql = connVo.autoOrderBy(sql, clazz);
		}
		
		Query query = this.em.createNativeQuery(sql);
		int i = 0;
		if (params != null && params.length > 0) {
			for (Object obj:params) {
				query.setParameter(++i, obj);
			}
		}
		
		SQLQuery sqlQuery = null;
		try {
			Field field = query.getClass().getDeclaredField("query");
			field.setAccessible(true);
			sqlQuery = (SQLQuery)field.get(query);
			field.setAccessible(false);
			sqlQuery.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		query.setFirstResult((page.getCurrentPage() - 1) * page.getCountOfCurrentPage());
		query.setMaxResults(page.getCountOfCurrentPage());
		List data = TypeConvert.objectsToListDataByMethod(query.getResultList(),clazz);
		page.setPageResults(data);
		//设置数量
		int n = 0;
		Query countQuery = this.em.createNativeQuery(TypeConvert.sqlToCountSql(sql));
		if (params != null && params.length > 0) {
			for (Object obj:params) {
				countQuery.setParameter(++n, obj);
			}
		}
		int total = 0;
		List<BigInteger> countObj = countQuery.getResultList();
		if (countObj != null && countObj.size() > 0) {
			if (countObj.size() == 1) {
				total += countObj.get(0).longValue();
			}
			else {
				total = countObj.size();
			}
		}
		page.setTotalCount(total);
		return page;
	}
	
	
	
	
	
	@Override
	@Deprecated
	public List queryDataBySql(String sql,Class clazz,Object...params) {
		
		Query query = this.em.createNativeQuery(sql);
		int i = 0;
		if (params != null && params.length > 0) {
			for (Object obj:params) {
				query.setParameter(++i, obj);
			}
		}
		List data = TypeConvert.objectsToListData(query.getResultList(),clazz);
		return data;
		
	}
	
	@Override
	public List queryListBySql(String sql,Class clazz,ConnditionVo connVo,Object...params) {
		if (connVo != null && connVo.getSortFieldName() != null) {
			sql = connVo.autoOrderBy(sql, clazz);
		}
		Query query = this.em.createNativeQuery(sql);
		int i = 0;
		if (params != null && params.length > 0) {
			for (Object obj:params) {
				query.setParameter(++i, obj);
			}
		}
		SQLQuery sqlQuery = null;
		try {
			Field field = query.getClass().getDeclaredField("query");
			field.setAccessible(true);
			sqlQuery = (SQLQuery)field.get(query);
			field.setAccessible(false);
			sqlQuery.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		List data = TypeConvert.objectsToListDataByMethod(query.getResultList(),clazz);
		return data;
		
	}
	
	
	
	@Override
	@Deprecated
	public List queryDataByParamNameSql(String sql,Class clazz,String[] paramNames,Object...values) {
		
		Query query = this.em.createNativeQuery(sql);
		if (paramNames != null && paramNames.length > 0) {
			int i = 0;
			for (String name:paramNames) {
				query.setParameter(name, values[i++]);
			}
		}
		List data = TypeConvert.objectsToListData(query.getResultList(),clazz);
		return data;
		
	}
	
	@Override
	public List queryDataByParamsSql(String sql,Class clazz,Map<String,Object> paramValues,ConnditionVo vo) {
		if (vo != null && vo.getSortFieldName() != null) {
			sql = vo.autoOrderBy(sql, clazz);
		}
		Query query = this.em.createNativeQuery(sql);
		SQLQuery sqlQuery = null;
		try {
			Field field = query.getClass().getDeclaredField("query");
			field.setAccessible(true);
			sqlQuery = (SQLQuery)field.get(query);
			field.setAccessible(false);
			sqlQuery.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		if (paramValues != null && paramValues.size() > 0) {
			for (Map.Entry<String, Object> enVal:paramValues.entrySet()) {
				query.setParameter(enVal.getKey(), enVal.getValue());
			}
		}
		List data = TypeConvert.objectsToListDataByMethod(query.getResultList(),clazz);
		return data;
		
	}
	
	@Override
	public List queryDataByParamsSql(String sql,Class clazz,ConnditionVo vo,String[] paramNames,Object...values) {
		
		if (vo != null && vo.getSortFieldName() != null) {
			sql = vo.autoOrderBy(sql, clazz);
		}
		
		Query query = this.em.createNativeQuery(sql);
		if (paramNames != null && paramNames.length > 0) {
			int i = 0;
			for (String name:paramNames) {
				query.setParameter(name, values[i++]);
			}
		}
		SQLQuery sqlQuery = null;
		try {
			Field field = query.getClass().getDeclaredField("query");
			field.setAccessible(true);
			sqlQuery = (SQLQuery)field.get(query);
			field.setAccessible(false);
			sqlQuery.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		List data = TypeConvert.objectsToListDataByMethod(query.getResultList(),clazz);
		return data;
		
	}
	
	
	
	
	@Override
	@Deprecated
	public List<?> queryBySql(String sql, Class<?> clazz,Object... params) {
		Query query = this.em.createNativeQuery(sql);
		int i = 0;
		if (params != null && params.length > 0) {
			for (Object obj:params) {
				query.setParameter(++i, obj);
			}
		}
		@SuppressWarnings({ "unchecked", "rawtypes" })
		List data = TypeConvert.objectsToListData(query.getResultList(),clazz);
		return data;
	}
	

	@Override
	public List<?> queryBySql(String sql, Class<?> clazz,ConnditionVo connVo, Object... params) {
		if (connVo != null && connVo.getSortFieldName() != null) {
			sql = connVo.autoOrderBy(sql, clazz);
		}
		Query query = this.em.createNativeQuery(sql);
		
		SQLQuery sqlQuery = null;
		try {
			Field field = query.getClass().getDeclaredField("query");
			field.setAccessible(true);
			sqlQuery = (SQLQuery)field.get(query);
			field.setAccessible(false);
			sqlQuery.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		int i = 0;
		if (params != null && params.length > 0) {
			for (Object obj:params) {
				query.setParameter(++i, obj);
			}
		}
		List data = TypeConvert.objectsToListDataByMethod(query.getResultList(),clazz);
		return data;
	}
	
	
	@Override
	public PageInfo<T> queryDataPageByConndition(PageInfo<T> page,
			final Map<String, Object> equals, final Map<String, Object> notEquals,
			final Map<String, String> isLike, final Map<String, String> groupNames,
			final Map<String, String> polys, final Map<String, Boolean> orders) {
		return this.queryDataPageByConndition(page, equals, notEquals, isLike, groupNames, polys, null, orders);
	}
	

	@Override
	public PageInfo<T> queryDataPageByConndition(PageInfo<T> page,
			final Map<String, Object> equals, final Map<String, Object> notEquals,
			final Map<String, String> isLike, final Map<String, String> groupNames,
			final Map<String, String> polys, final Map<String,Object[]> between,final Map<String, Boolean> orders) {
		Page<T> pageData = this.findAll(new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
			
				List<Predicate> pres = new ArrayList<Predicate>();
				if (equals != null  && equals.size() > 0) {
					for (Map.Entry<String, Object> equalObj:equals.entrySet()) {
						String[] names = StringUtil.split(equalObj.getKey(), ".");
						if (names.length > 1) {
							Path expression = root.get(names[0]);
							expression = expression.get(names[1]);
							Predicate pre = cb.equal(expression, equalObj.getValue());
							pres.add(pre);
						}
						else {
							Path<Object> path = root.get(equalObj.getKey());
							Predicate pre = cb.equal(path, equalObj.getValue());
							pres.add(pre);
						}
					}
				}
				if (notEquals != null  && notEquals.size() > 0) {
					for (Map.Entry<String, Object> equalObj:notEquals.entrySet()) {
						String[] names = StringUtil.split(equalObj.getKey(), ".");
						if (names.length > 1) {
							Path expression = root.get(names[0]);
							expression = expression.get(names[1]);
							Predicate pre = cb.notEqual(expression, equalObj.getValue());
							pres.add(pre);
						}
						else {
							Path<Object> path = root.get(equalObj.getKey());
							Predicate pre = cb.notEqual(path, equalObj.getValue());
							pres.add(pre);
						}
					}
				}
				if (isLike != null && isLike.size() > 0) {
					for (Map.Entry<String, String> isLikeObj:isLike.entrySet()) {
						String[] names = StringUtil.split(isLikeObj.getKey(), ".");
						if (names.length > 1) {
							Path expression = root.get(names[0]);
							expression = expression.get(names[1]);
							Predicate pre = cb.like(expression, "%" + isLikeObj.getValue() + "%");
							pres.add(pre);
						}
						else {
							Path<String> path = root.get(isLikeObj.getKey());
							Predicate pre = cb.like(path, "%" + isLikeObj.getValue() + "%");
							pres.add(pre);
						}
					}
				}
				
				if (groupNames != null) {
			    	List<Path> groups = new ArrayList<Path>();
			    	for (Map.Entry<String, String> groupName:groupNames.entrySet()) {
			    		String[] names = StringUtil.split(groupName.getKey(), ".");
						if (names.length > 1) {
							Path expression = root.get(names[0]);
							expression = expression.get(names[1]);
							groups.add(expression);
							//query.groupBy(expression);
						}
						else {
							Path<String> expression = root.get(groupName.getKey());
							groups.add(expression);
							//query.groupBy(expression);
						}
			    	}
			    	if (groups != null && groups.size() > 0) {
			    		query.groupBy(groups.toArray(new Path[groups.size()]));
			    		query.getGroupRestriction();
			    	}
				}
				
				if (between != null) {
					
					for (Map.Entry<String, Object[]> betweenObj:between.entrySet()) {
						String[] names = StringUtil.split(betweenObj.getKey(), ".");
						Object value[] = betweenObj.getValue();
						if (value == null || value.length != 2) {
							continue;
						}
						
						if (names.length > 1) {
							Path<Long> expression = root.get(names[0]);
							expression = expression.get(names[1]);
							String valueOne = TypeConvert.objToStrIsNotNull(value[0]);
							String valueTwo = TypeConvert.objToStrIsNotNull(value[1]);
							Predicate pre = null;
							if (valueOne != null && valueTwo != null) {
								if (value[0] instanceof Long) {
									pre = cb.between(expression,
											Long.parseLong(String.valueOf(value[0])),
											Long.parseLong(String.valueOf(value[1])));
								}
							}
							if (pre != null) {
								pres.add(pre);
							}
						}
						else {
							
							Path<Long> path = root.get(betweenObj.getKey());
							//expression = expression.get(names[1]);
							String valueOne = TypeConvert.objToStrIsNotNull(value[0]);
							String valueTwo = TypeConvert.objToStrIsNotNull(value[1]);
							Predicate pre = null;
							if (valueOne != null && valueTwo != null) {
								if (value[0] instanceof Long) {
									pre = cb.between(path,
											Long.parseLong(String.valueOf(value[0])),
											Long.parseLong(String.valueOf(value[1])));
								}
							}
							if (pre != null) {
								pres.add(pre);
							}
						}
						
					}
					
					
				}
				
				List<javax.persistence.criteria.Order> queryOrders = new ArrayList<javax.persistence.criteria.Order>();
				if (orders != null && orders.size() > 0) {
					for (Map.Entry<String, Boolean> order:orders.entrySet()) {
						if (order.getValue()) {
							queryOrders.add(cb.asc(root.get(order.getKey())));
						}
						else {
							queryOrders.add(cb.desc(root.get(order.getKey())));
						}
					}
				}
				if (queryOrders.size() > 0) {
					query.orderBy(queryOrders);
				}
				CriteriaQuery q = cb.createQuery();
				
				if (polys != null && polys.size() > 0) {
					List<Expression> polies = new ArrayList<Expression>();
					for (Map.Entry<String, String> polymerization:polys.entrySet()) {
						String[] names = StringUtil.split(polymerization.getKey(), ".");
						if (names.length > 1) {
							Path expression = root.get(names[0]);
							expression = expression.get(names[1]);
							Expression exp = TypeConvert.settingExpression(cb, expression, polymerization.getValue());
							polies.add(exp);
						}
						else {
							Path expression = root.get(polymerization.getKey());
							Expression exp = TypeConvert.settingExpression(cb, expression, polymerization.getValue());
							polies.add(exp);
						}
					}
				    if (polies != null) {
				    	for (Expression exp:polies) {
				    		q.select(exp);
				    	}
				    }
				}
				return cb.and(pres.toArray(new Predicate[pres.size()]));
				
			}
		}, new PageRequest(page.getCurrentPage(),page.getCountOfCurrentPage()));
		page.setTotalCount((int)pageData.getTotalElements());
		page.setPageResults(pageData.getContent());
		return page;
	}

	@Override
	public PageInfo queryDataByParamsSql(PageInfo page, String sql, Class clazz,
			Map<String, Object> paramValues, ConnditionVo connVo) {
		
		if (connVo != null && connVo.getSortFieldName() != null) {
			sql = connVo.autoOrderBy(sql, clazz);
		}
		
		Query query = this.em.createNativeQuery(sql);
		if (paramValues != null && paramValues.size() > 0) {
			for (Map.Entry<String, Object> enVal:paramValues.entrySet()) {
				query.setParameter(enVal.getKey(), enVal.getValue());
			}
		}
		
		SQLQuery sqlQuery = null;
		try {
			Field field = query.getClass().getDeclaredField("query");
			field.setAccessible(true);
			sqlQuery = (SQLQuery)field.get(query);
			field.setAccessible(false);
			sqlQuery.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		query.setFirstResult((page.getCurrentPage() - 1) * page.getCountOfCurrentPage());
		query.setMaxResults(page.getCountOfCurrentPage());
		List data = TypeConvert.objectsToListDataByMethod(query.getResultList(),clazz);
		page.setPageResults(data);
		//设置数量
		Query countQuery = this.em.createNativeQuery(TypeConvert.sqlToCountSql(sql));
		
		if (paramValues != null && paramValues.size() > 0) {
			for (Map.Entry<String, Object> enVal:paramValues.entrySet()) {
				countQuery.setParameter(enVal.getKey(), enVal.getValue());
			}
		}
		
		
		int total = 0;
		List<BigInteger> countObj = countQuery.getResultList();
		if (countObj != null && countObj.size() > 0) {
			if (countObj.size() == 1) {
				total += countObj.get(0).longValue();
			}
			else {
				total = countObj.size();
			}
		}
		page.setTotalCount(total);
		return page;
		
	}

	@Override
	public List<Map<String,Object>> queryMapBySql(String sql,Object... params) {
		Query query = this.em.createNativeQuery(sql);
		
		SQLQuery sqlQuery = null;
		try {
			Field field = query.getClass().getDeclaredField("query");
			field.setAccessible(true);
			sqlQuery = (SQLQuery)field.get(query);
			field.setAccessible(false);
			sqlQuery.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		int i = 0;
		if (params != null && params.length > 0) {
			for (Object obj:params) {
				query.setParameter(++i, obj);
			}
		}
		List<Map<String,Object>> data = query.getResultList();
		return data;
	}
	
/*
	@Override
	public PageInfo queryDataByParamsSql(PageInfo page, String sql, Class clazz,
			Map<String, Object> paramValues, ConnditionVo connVo) {
		
		if (connVo != null && connVo.getSortFieldName() != null) {
			sql = connVo.autoOrderBy(sql, clazz);
		}
		
		Query query = this.em.createNativeQuery(sql);
		if (paramValues != null && paramValues.size() > 0) {
			for (Map.Entry<String, Object> enVal:paramValues.entrySet()) {
				query.setParameter(enVal.getKey(), enVal.getValue());
			}
		}
		
		SQLQuery sqlQuery = null;
		try {
			Field field = query.getClass().getDeclaredField("query");
			field.setAccessible(true);
			sqlQuery = (SQLQuery)field.get(query);
			field.setAccessible(false);
			sqlQuery.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		query.setFirstResult((page.getCurrentPage() - 1) * page.getCountOfCurrentPage());
		query.setMaxResults(page.getCountOfCurrentPage());
		List data = TypeConvert.objectsToListDataByMethod(query.getResultList(),clazz);
		page.setPageResults(data);
		//设置数量
		Query countQuery = this.em.createNativeQuery(TypeConvert.sqlToCountSql(sql));
		
		if (paramValues != null && paramValues.size() > 0) {
			for (Map.Entry<String, Object> enVal:paramValues.entrySet()) {
				countQuery.setParameter(enVal.getKey(), enVal.getValue());
			}
		}
		
		
		int total = 0;
		List<BigInteger> countObj = countQuery.getResultList();
		if (countObj != null && countObj.size() > 0) {
			if (countObj.size() == 1) {
				total += countObj.get(0).longValue();
			}
			else {
				total = countObj.size();
			}
		}
		page.setTotalCount(total);
		return page;
		
	}
		
*/
}
