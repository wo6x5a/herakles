package com.lcw.herakles.platform.system.security.entity.id;

import java.io.Serializable;

/**
 * @author chenwulou
 *
 */
public class UserRolePk implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5288658480733622188L;

    private String userId;

    private String roleId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
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
