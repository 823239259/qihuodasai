package com.tzdr.domain.vo;

import java.math.BigInteger;

/**
 * Created by Administrator on 2016/4/14.
 */
public class GeneralizeChannelVo {

    private String id;

    private String typeOneTitle;

    private String typeOneParam;

    private String typeTwoTitle;

    private String typeTwoParam;

    private String typeThreeTitle;

    private String typeThreeParam;

    private String param;

    private String urlKey;

    private String title;

    private Integer sort;

    private BigInteger createdate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeOneTitle() {
        return typeOneTitle;
    }

    public void setTypeOneTitle(String typeOneTitle) {
        this.typeOneTitle = typeOneTitle;
    }

    public String getTypeOneParam() {
        return typeOneParam;
    }

    public void setTypeOneParam(String typeOneParam) {
        this.typeOneParam = typeOneParam;
    }

    public String getTypeTwoTitle() {
        return typeTwoTitle;
    }

    public void setTypeTwoTitle(String typeTwoTitle) {
        this.typeTwoTitle = typeTwoTitle;
    }

    public String getTypeTwoParam() {
        return typeTwoParam;
    }

    public void setTypeTwoParam(String typeTwoParam) {
        this.typeTwoParam = typeTwoParam;
    }

    public String getTypeThreeTitle() {
        return typeThreeTitle;
    }

    public void setTypeThreeTitle(String typeThreeTitle) {
        this.typeThreeTitle = typeThreeTitle;
    }

    public String getTypeThreeParam() {
        return typeThreeParam;
    }

    public void setTypeThreeParam(String typeThreeParam) {
        this.typeThreeParam = typeThreeParam;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getUrlKey() {
        return urlKey;
    }

    public void setUrlKey(String urlKey) {
        this.urlKey = urlKey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public BigInteger getCreatedate() {
        return createdate;
    }

    public void setCreatedate(BigInteger createdate) {
        this.createdate = createdate;
    }
}
