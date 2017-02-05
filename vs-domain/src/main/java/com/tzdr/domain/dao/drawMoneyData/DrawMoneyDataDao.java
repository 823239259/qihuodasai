package com.tzdr.domain.dao.drawMoneyData;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.DrawMoneyData;

public interface DrawMoneyDataDao extends BaseJpaDao<DrawMoneyData, String>{

	@Query("from DrawMoneyData where type=?1")
	List<DrawMoneyData> getAduitMoneyByType(String type);
	
	@Query("from DrawMoneyData where type=?1 and minmoney<=?2 and maxmoney>?2")
	List<DrawMoneyData> getAduitMoneyByTypeAndMoney(String type, Double money);

}
