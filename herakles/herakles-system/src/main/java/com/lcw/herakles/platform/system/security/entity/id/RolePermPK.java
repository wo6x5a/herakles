package com.lcw.herakles.platform.system.security.entity.id;

import java.io.Serializable;

/**
 * @author chenwulou
 *
 */
public class RolePermPK implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7304468458307888125L;

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
    
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
    
    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
