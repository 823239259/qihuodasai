package com.tzdr.cms.support;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;

import jodd.util.StringUtil;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;

import com.google.common.collect.Maps;
import com.tzdr.common.utils.MessageUtils;

/**
 * <p>User: Lin Feng
 * <p>Date: 13-5-7 下午2:37
 * <p>Version: 1.0
 */
public class PermissionList implements Serializable {

    public static final String CREATE_PERMISSION = "create";
    public static final String UPDATE_PERMISSION = "update";
    public static final String DELETE_PERMISSION = "delete";
    public static final String VIEW_PERMISSION = "view";
    public static final String RESET_PASSWORD_PERMISSION = "reset";
    
    public static final String AUDIT_PERMISSION = "audit";
    
    public static final String LIMITBUY_PERMISSION = "limitBuy";
    public static final String LIMITSELL_PERMISSION = "limitSell";
    public static final String CANCELLIMIT_PERMISSION = "cancelLimit";
    public static final String UNWINDING_PERMISSION = "unwinding";
    public static final String END_PERMISSION = "end";
    
    public static final String NOTCONNECTED_PERMISSION = "notConnected";
    public static final String NOTIFIED_PERMISSION = "notified";
    public static final String IMPORT_PERMISSION = "import";
    public static final String INSTEAD_ACTIVITY_USER_PERMISSION = "insteadActivityUser";
    
    
    /**
     * 资源前缀
     */
    private String resourceIdentity;

    /**
     * key:    权限
     * value : 资源
     */
    private Map<String, String> resourcePermissions = Maps.newHashMap();


    /**
     * 自动生成 create update delete 的权限串
     *
     * @param resourceIdentity
     * @return
     */
    public static PermissionList newPermissionList(String resourceIdentity) {

        PermissionList permissionList = new PermissionList();

        permissionList.resourceIdentity = resourceIdentity;

        permissionList.resourcePermissions.put(CREATE_PERMISSION, resourceIdentity + ":" + CREATE_PERMISSION);
        permissionList.resourcePermissions.put(UPDATE_PERMISSION, resourceIdentity + ":" + UPDATE_PERMISSION);
        permissionList.resourcePermissions.put(DELETE_PERMISSION, resourceIdentity + ":" + DELETE_PERMISSION);
        permissionList.resourcePermissions.put(VIEW_PERMISSION, resourceIdentity + ":" + VIEW_PERMISSION);
        permissionList.resourcePermissions.put(RESET_PASSWORD_PERMISSION, resourceIdentity + ":" + RESET_PASSWORD_PERMISSION);
        
        permissionList.resourcePermissions.put(AUDIT_PERMISSION, resourceIdentity + ":" + AUDIT_PERMISSION);
        
        permissionList.resourcePermissions.put(LIMITBUY_PERMISSION, resourceIdentity + ":" + LIMITBUY_PERMISSION);
        permissionList.resourcePermissions.put(LIMITSELL_PERMISSION, resourceIdentity + ":" + LIMITSELL_PERMISSION);
        permissionList.resourcePermissions.put(CANCELLIMIT_PERMISSION, resourceIdentity + ":" + CANCELLIMIT_PERMISSION);
        permissionList.resourcePermissions.put(UNWINDING_PERMISSION, resourceIdentity + ":" + UNWINDING_PERMISSION);
        permissionList.resourcePermissions.put(END_PERMISSION, resourceIdentity + ":" + END_PERMISSION);
        permissionList.resourcePermissions.put(NOTCONNECTED_PERMISSION, resourceIdentity + ":" + NOTCONNECTED_PERMISSION);
        permissionList.resourcePermissions.put(NOTIFIED_PERMISSION, resourceIdentity + ":" + NOTIFIED_PERMISSION);
        permissionList.resourcePermissions.put(IMPORT_PERMISSION, resourceIdentity + ":" + IMPORT_PERMISSION);
        permissionList.resourcePermissions.put(INSTEAD_ACTIVITY_USER_PERMISSION, resourceIdentity + ":" + INSTEAD_ACTIVITY_USER_PERMISSION);

        
        return permissionList;
    }


    /**
     * 添加权限 自动生成如showcase:sample:permission
     *
     * @param permission
     */
    public void addPermission(String permission) {
        resourcePermissions.put(permission, resourceIdentity + ":" + permission);
    }


    public void assertHasCreatePermission() {
        assertHasPermission(CREATE_PERMISSION, "no.create.permission");
    }

    public void assertHasUpdatePermission() {
        assertHasPermission(UPDATE_PERMISSION, "no.update.permission");
    }

    public void assertHasDeletePermission() {
        assertHasPermission(DELETE_PERMISSION, "no.delete.permission");
    }


    public void assertHasViewPermission() {
        assertHasPermission(VIEW_PERMISSION, "no.view.permission");
    }

    public void assertHasResetPermission() {
        assertHasPermission(RESET_PASSWORD_PERMISSION, "no.reset.password.permission");
    }
    
    
    public void assertHasLimitBuyPermission() {
        assertHasPermission(LIMITBUY_PERMISSION, "no.limitBuy.permission");
    }
    
    public void assertHasLimitSellPermission() {
        assertHasPermission(LIMITSELL_PERMISSION, "no.limitSell.permission");
    }

    
    public void assertHasCancelLimitPermission() {
        assertHasPermission(CANCELLIMIT_PERMISSION, "no.cancelLimit.permission");
    }
    
    
    public void assertHasUnwindingPermission() {
        assertHasPermission(UNWINDING_PERMISSION, "no.unwinding.permission");
    }
    
    
    public void assertHasEndPermission() {
        assertHasPermission(END_PERMISSION, "no.end.permission");
    }
    
    public void assertHasNotConnectedPermission() {
        assertHasPermission(NOTCONNECTED_PERMISSION, "no.notConnected.permission");
    }
    
    
    public void assertHasNotifiedPermission() {
        assertHasPermission(NOTIFIED_PERMISSION, "no.notified.permission");
    }
    
    
    public void assertHasAuditPermission() {
        assertHasPermission(AUDIT_PERMISSION, "no.audit.permission");
    }
        
    public void assertHasImportPermission() {
        assertHasPermission(IMPORT_PERMISSION, "no.import.permission");
    }
    
    public void assertHasInsteadActivityUserPermission() {
    	assertHasPermission(INSTEAD_ACTIVITY_USER_PERMISSION, "no.instead.activity.user.permission");
    }
    
    /**
     * 即增删改中的任何一个即可
     */
    public void assertHasEditPermission() {
        assertHasAnyPermission(new String[]{
                CREATE_PERMISSION,
                UPDATE_PERMISSION,
                DELETE_PERMISSION
        });
    }

    public void assertHasPermission(String permission) {
        assertHasPermission(permission, null);
    }

    public void assertHasPermission(String permission, String errorCode) {
        if (StringUtil.isEmpty(errorCode)) {
            errorCode = getDefaultErrorCode();
        }
        String resourcePermission = resourcePermissions.get(permission);
        if (resourcePermission == null) {
            resourcePermission = this.resourceIdentity + ":" + permission;
        }
        if (!SecurityUtils.getSubject().isPermitted(resourcePermission)) {
            throw new UnauthorizedException(MessageUtils.message(errorCode, resourcePermission));
        }
    }

    public void assertHasAllPermission(String[] permissions) {
        assertHasAllPermission(permissions, null);
    }

    public void assertHasAllPermission(String[] permissions, String errorCode) {
        if (StringUtil.isEmpty(errorCode)) {
            errorCode = getDefaultErrorCode();
        }

        if (permissions == null || permissions.length == 0) {
            throw new UnauthorizedException(MessageUtils.message(errorCode, resourceIdentity + ":" + Arrays.toString(permissions)));
        }

        Subject subject = SecurityUtils.getSubject();

        for (String permission : permissions) {
            String resourcePermission = resourcePermissions.get(permission);
            if (resourcePermission == null) {
                resourcePermission = this.resourceIdentity + ":" + permission;
            }
            if (!subject.isPermitted(resourcePermission)) {
                throw new UnauthorizedException(MessageUtils.message(errorCode, resourceIdentity + ":" + Arrays.toString(permissions)));
            }
        }

    }

    public void assertHasAnyPermission(String[] permissions) {
        assertHasAnyPermission(permissions, null);
    }

    public void assertHasAnyPermission(String[] permissions, String errorCode) {
        if (StringUtil.isEmpty(errorCode)) {
            errorCode = getDefaultErrorCode();
        }
        if (permissions == null || permissions.length == 0) {
            throw new UnauthorizedException(MessageUtils.message(errorCode, resourceIdentity + ":" + Arrays.toString(permissions)));
        }

        Subject subject = SecurityUtils.getSubject();

        for (String permission : permissions) {
            String resourcePermission = resourcePermissions.get(permission);
            if (resourcePermission == null) {
                resourcePermission = this.resourceIdentity + ":" + permission;
            }
            if (subject.isPermitted(resourcePermission)) {
                return;
            }
        }

        throw new UnauthorizedException(MessageUtils.message(errorCode, resourceIdentity + ":" + Arrays.toString(permissions)));
    }

    private String getDefaultErrorCode() {
        return "no.permission";
    }

}
