package com.tzdr.business.service.togetherFuture;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.web.entity.TogetherFuture;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/17.
 */
public interface TogetherFutureService extends BaseService<TogetherFuture> {
    void create(TogetherFuture togetherFuture);

    void update(TogetherFuture togetherFuture);

    void delete(TogetherFuture togetherFuture);

    List<Map<String,Object>> getType();
}
