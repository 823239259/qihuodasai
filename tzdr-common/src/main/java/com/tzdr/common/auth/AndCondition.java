package com.tzdr.common.auth;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * and 条件
 * <p>User: Lin Feng
 * <p>Version: 1.0
 */
public class AndCondition implements SearchFilter {

    private List<SearchFilter> andFilters = Lists.newArrayList();

    AndCondition() {
    }

    public AndCondition add(SearchFilter filter) {
        this.andFilters.add(filter);
        return this;
    }

    public List<SearchFilter> getAndFilters() {
        return andFilters;
    }

    @Override
    public String toString() {
        return "AndCondition{" + andFilters + '}';
    }
}
