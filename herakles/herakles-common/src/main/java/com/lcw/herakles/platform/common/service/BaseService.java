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

    public void setCreate(Object entity) {
        BasePo po = (BasePo) entity;
        po.setCreateOpId(getCurrentOperatorId());
        po.setCreateTs(new Date());
    }

    public void setUpdate(Object entity) {
        BaseMaintainablePo po = (BaseMaintainablePo) entity;
        po.setLastMntOpId(getCurrentOperatorId());
        po.setLastMntTs(new Date());
    }

    /** 获取当前用户id */
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
