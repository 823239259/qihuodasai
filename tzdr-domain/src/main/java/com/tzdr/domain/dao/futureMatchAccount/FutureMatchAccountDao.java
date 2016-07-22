package com.tzdr.domain.dao.futureMatchAccount;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.FutureMatchAccount;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

/**
 * 
 * @author GuoXingyou
 *
 */
public interface FutureMatchAccountDao extends BaseJpaDao<FutureMatchAccount, String> {

    @Query("from FutureMatchAccount f where f.isUse = 0 and f.businessType = ?1 and f.lever =?2 and f.tradeMoney = ?3 order by f.createTime ASC")
    public List<FutureMatchAccount> findByType(int type,int lever,Double tradeMoney);


}
