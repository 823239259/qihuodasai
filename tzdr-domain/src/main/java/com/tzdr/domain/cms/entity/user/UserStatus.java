package com.tzdr.domain.cms.entity.user;

/**
 * <p>User: Lin Feng
 * <p>Version: 1.0
 */
public enum UserStatus {

    normal("正常状态"), blocked("封禁状态");

    private final String info;

    private UserStatus(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
