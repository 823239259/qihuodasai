package com.tzdr.common.utils;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.orm.jpa.SharedEntityManagerCreator;
import org.springframework.util.Assert;

import com.tzdr.common.auth.SearchCallback;
import com.tzdr.common.auth.Searchable;

/**
 * 仓库辅助类
 * <p>
 * User: Lin Feng
 * <p>
 * Version: 1.0
 */
public class RepositoryHelper {

	@PersistenceContext
	protected static EntityManager entityManager;

	private Class<?> entityClass;

	private boolean enableQueryCache = false;

	public static void setEntityManager(EntityManager entityManager) {
		RepositoryHelper.entityManager = entityManager;
	}

	public boolean isEnableQueryCache() {
		return enableQueryCache;
	}

	public void setEnableQueryCache(boolean enableQueryCache) {
		this.enableQueryCache = enableQueryCache;
	}

	public RepositoryHelper(Class<?> entityClass, EntityManager entityManager) {
		this.entityClass = entityClass;
		synchronized (this) {
			this.entityManager = entityManager;
		}
	}

	public static void setEntityManagerFactory(
			EntityManagerFactory entityManagerFactory) {
		entityManager = SharedEntityManagerCreator
				.createSharedEntityManager(entityManagerFactory);
	}

	public static EntityManager getEntityManager() {
		Assert.notNull(
				entityManager,
				"entityManager must null, please see "
						+ "[com.tzdr.common.utils.RepositoryHelper#setEntityManagerFactory]");

		return entityManager;
	}

	public <T> JpaEntityInformation<T, ?> getMetadata(Class<T> entityClass) {
		return JpaEntityInformationSupport.getMetadata(entityClass,
				entityManager);
	}

	public String getEntityName(Class<?> entityClass) {
		return getMetadata(entityClass).getEntityName();
	}

	/**
	 * 按顺序设置Query参数
	 *
	 * @param query
	 * @param params
	 */
	public void setParameters(Query query, Object[] params) {
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i + 1, params[i]);
			}
		}
	}

	public int batchUpdate(final String ql, final Object... params) {

		Query query = getEntityManager().createQuery(ql);
		setParameters(query, params);

		return query.executeUpdate();
	}

	/**
	 * <p>
	 * 根据ql和按照索引顺序的params查询一个实体<br/>
	 * 具体使用请参考测试用例：{@see
	 * com.sishuok.es.common.repository.UserRepository2ImplIT#testFindOne()}
	 *
	 * @param ql
	 * @param params
	 * @param <M>
	 * @return
	 */
	public <M> M findOne(final String ql, final Object... params) {

		List<M> list = findAll(ql, new PageRequest(0, 1), params);

		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * <p>
	 * 根据ql和按照索引顺序的params执行ql，pageable存储分页信息 null表示不分页<br/>
	 * 具体使用请参考测试用例：{@see
	 * com.sishuok.es.common.repository.UserRepository2ImplIT#testFindAll()}
	 *
	 * @param ql
	 * @param pageable
	 *            null表示不分页
	 * @param params
	 * @param <M>
	 * @return
	 */
	public <M> List<M> findAll(final String ql, final Pageable pageable,
			final Object... params) {

		Query query = getEntityManager()
				.createQuery(
						ql
								+ prepareOrder(pageable != null ? pageable
										.getSort() : null));
		applyEnableQueryCache(query);
		setParameters(query, params);
		if (pageable != null) {
			query.setFirstResult(pageable.getOffset());
			query.setMaxResults(pageable.getPageSize());
		}

		return query.getResultList();
	}

	/**
	 * 拼排序
	 *
	 * @param sort
	 * @return
	 */
	public String prepareOrder(Sort sort) {
		if (sort == null || !sort.iterator().hasNext()) {
			return "";
		}
		StringBuilder orderBy = new StringBuilder("");
		orderBy.append(" order by ");
		orderBy.append(sort.toString().replace(":", " "));
		return orderBy.toString();
	}

	public void applyEnableQueryCache(Query query) {
		if (enableQueryCache) {
			query.setHint("org.hibernate.cacheable", true);// 开启查询缓存
		}
	}

	/**
	 * <p>
	 * ql条件查询<br/>
	 * searchCallback默认实现请参考 {@see
	 * com.sishuok.es.common.repository.callback.DefaultSearchCallback}<br/>
	 * <p/>
	 * 测试用例请参考：{@see
	 * com.sishuok.es.common.repository.UserRepositoryImplForCustomSearchIT} 和
	 * {@see
	 * com.sishuok.es.common.repository.UserRepositoryImplForDefaultSearchIT}
	 *
	 * @param ql
	 * @param searchable
	 *            查询条件、分页 排序
	 * @param searchCallback
	 *            查询回调 自定义设置查询条件和赋值
	 * @return
	 */
	public <M> List<M> findAll(final String ql, final Searchable searchable,
			final SearchCallback searchCallback) {

		assertConverted(searchable);
		StringBuilder s = new StringBuilder(ql);
		searchCallback.prepareQL(s, searchable);
		searchCallback.prepareOrder(s, searchable);
		Query query = getEntityManager().createQuery(s.toString());
		applyEnableQueryCache(query);
		searchCallback.setValues(query, searchable);
		searchCallback.setPageable(query, searchable);

		return query.getResultList();
	}

	private void assertConverted(Searchable searchable) {
		if (!searchable.isConverted()) {
			searchable.convert(this.entityClass);
		}
	}

	/**
	 * <p>
	 * 按条件统计<br/>
	 * 测试用例请参考：{@see
	 * com.sishuok.es.common.repository.UserRepositoryImplForCustomSearchIT} 和
	 * {@see
	 * com.sishuok.es.common.repository.UserRepositoryImplForDefaultSearchIT}
	 *
	 * @param ql
	 * @param searchable
	 * @param searchCallback
	 * @return
	 */
	public long count(final String ql, final Searchable searchable,
			final SearchCallback searchCallback) {

		assertConverted(searchable);

		StringBuilder s = new StringBuilder(ql);
		searchCallback.prepareQL(s, searchable);
		Query query = getEntityManager().createQuery(s.toString());
		applyEnableQueryCache(query);
		searchCallback.setValues(query, searchable);

		return (Long) query.getSingleResult();
	}

	/**
	 * @param ql
	 * @param params
	 * @param <M>
	 * @return
	 * @see RepositoryHelper#findAll(String,
	 *      org.springframework.data.domain.Pageable, Object...)
	 */
	public <M> List<M> findAll(final String ql, final Object... params) {

		// 此处必须 (Pageable) null 否则默认有调用自己了 可变参列表
		return findAll(ql, (Pageable) null, params);

	}

}
