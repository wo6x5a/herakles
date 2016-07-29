package com.lcw.herakles.platform.system.user.entity.id;

import java.io.Serializable;

/**
 * @author chenwulou
 *
 */
public class RolePermPK implements Serializable {

    private static final long serialVersionUID = 1L;

    private String roleId;

    private String permId;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getPermId() {
        return permId;
    }

    public void setPermId(String permId) {
        this.permId = permId;
    }

}
