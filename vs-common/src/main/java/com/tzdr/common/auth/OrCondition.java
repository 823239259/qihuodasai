package com.tzdr.common.auth;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * or 条件
 * <p>User: Lin Feng
 * <p>Version: 1.0
 */
public class OrCondition implements SearchFilter {

    private List<SearchFilter> orFilters = Lists.newArrayList();

    OrCondition() {
    }

    public OrCondition add(SearchFilter filter) {
        this.orFilters.add(filter);
        return this;
    }

    public List<SearchFilter> getOrFilters() {
        return orFilters;
    }

    @Override
    public String toString() {
        return "OrCondition{" + orFilters + '}';
    }
}
