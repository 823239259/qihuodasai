
package com.tzdr.domain.dao.auth;

import com.google.common.collect.Sets;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.List;
import java.util.Set;

/**
 * <p>User: Lin Feng
 * <p>Version: 1.0
 */
public class AuthDaoImpl {

    @PersistenceContext
    private EntityManager em;


    public Set<Long> findRoleIds(Long userId) {


        StringBuilder hql = new StringBuilder("select roleIds from Auth where ");
        hql.append(" (userId=:userId) ");     

        Query q = em.createQuery(hql.toString()).setHint("org.hibernate.cacheable", true);

        q.setParameter("userId", userId);
      
        @SuppressWarnings("unchecked")
		List<Set<Long>> roleIdSets = (List<Set<Long>>) q.getResultList();

        Set<Long> roleIds = Sets.newHashSet();
        for (Set<Long> set : roleIdSets) {
            roleIds.addAll(set);
        }

        return roleIds;
    }
}
