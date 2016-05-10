package com.tzdr.domain.dao.generalize;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.GeneralizeChannel;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/4/13.
 */
public interface GeneralizeChannelDao extends BaseJpaDao<GeneralizeChannel, String> {

    public List<GeneralizeChannel> findByParamAndDeletedFalse(String params);

    @Query(value = "from GeneralizeChannel g where g.deleted =false order by g.createDate DESC")
    public List<GeneralizeChannel> findByDeletedFalse();

    @Query(value = "from GeneralizeChannel g where g.typeOneTitle =?1 and g.deleted =false order by g.createDate DESC")
    public List<GeneralizeChannel> findByTypeTwoTitle(String name);

    @Query(value = "from GeneralizeChannel g where g.typeOneTitle=?1 and g.typeTwoTitle =?2 and g.deleted =false order by g.createDate DESC")
    public List<GeneralizeChannel> findByTypeThreeTitle(String fristname,String name);
    
    @Query(value = "from GeneralizeChannel g where g.typeTwoTitle =?1 and g.deleted =false order by g.createDate DESC")
    public List<GeneralizeChannel> findTypeThreeTitleOnlyByTypeTwo(String name);
    
}
