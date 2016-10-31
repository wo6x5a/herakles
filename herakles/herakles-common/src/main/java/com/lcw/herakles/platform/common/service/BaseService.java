package com.lcw.herakles.platform.common.service;

import java.util.Date;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.lcw.herakles.platform.common.constant.CacheConsts;
import com.lcw.herakles.platform.common.entity.BaseMaintainablePo;
import com.lcw.herakles.platform.common.entity.BasePo;

/**
 * base service
 * 
 * @author chenwulou
 *
 */
public class BaseService {

    /**
     * 填充创建人, 创建时间
     * 
     * @param entity
     */
    public void setCreateInfo(Object entity) {
        BasePo po = (BasePo) entity;
        po.setCreateOpId(getCurrentOperatorId());
        po.setCreateTs(new Date());
    }

    /**
     * 填充更新人, 更新时间
     * 
     * @param entity
     */
    public void setUpdateInfo(Object entity) {
        BaseMaintainablePo po = (BaseMaintainablePo) entity;
        po.setLastMntOpId(getCurrentOperatorId());
        po.setLastMntTs(new Date());
    }

    /**
     * 获取当前用户id
     * 
     * @return
     */
    private String getCurrentOperatorId() {
        Subject currentUser = SecurityUtils.getSubject();
        if (null != currentUser) {
            Session session = currentUser.getSession();
            if (null != session) {
                return (String) session.getAttribute(CacheConsts.USER_ID_KEY);
            }
        }
        return null;
    }

}
