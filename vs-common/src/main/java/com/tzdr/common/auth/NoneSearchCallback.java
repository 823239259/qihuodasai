package com.tzdr.common.auth;



import javax.persistence.Query;

/**
 * <p>User: Lin Feng
 * <p>Version: 1.0
 */
public final class NoneSearchCallback implements SearchCallback {

    @Override
    public void prepareQL(StringBuilder ql, Searchable search) {
    }

    @Override
    public void prepareOrder(StringBuilder ql, Searchable search) {
    }

    @Override
    public void setValues(Query query, Searchable search) {
    }

    @Override
    public void setPageable(Query query, Searchable search) {
    }
}
