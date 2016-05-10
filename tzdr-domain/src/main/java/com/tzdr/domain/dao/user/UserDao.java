package com.tzdr.domain.dao.user;


import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.QueryHints;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.cms.entity.organization.Organization;
import com.tzdr.domain.cms.entity.user.User;

/**
 * <p>User: Lin Feng
 * <p>Version: 1.0
 */

public interface UserDao extends BaseJpaDao<User, Long> {

	@QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
    User findByUsername(String username);
	
	@QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
    User findByUsernameAndDeletedFalse(String username);

    User findByMobilePhoneNumber(String mobilePhoneNumber);

    User findByEmail(String email);
    
    List<User> findByOrganization(Organization organization);
    
    List<User> findByDeletedFalseAndOrganization(Organization organization);
    
    List<User> findByDeletedFalse();
}
