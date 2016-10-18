package com.lcw.herakles.platform.common.cache.redis.shiro;

import java.io.Serializable;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;

import lombok.extern.slf4j.Slf4j;

/**
 * redis for shiro cache session
 * 
 * @author chenwulou
 *
 */
@Slf4j
public class ShiroRedisCacheSessionDAO extends CachingSessionDAO {

    // 1000*60*30 = 30 min
    private long sessionTimeout = 0;

    private Cache<Serializable, Session> getRedisSessionCache() {
        return super.getCacheManager().getCache(ACTIVE_SESSION_CACHE_NAME);
    }

    private void saveSession(Session session) throws UnknownSessionException {
        if (session == null || session.getId() == null) {
            log.error("session or session id is null");
            return;
        }
        session.setTimeout(sessionTimeout);
        getRedisSessionCache().put(session.getId(), session);
    }

    @Override
    protected void doUpdate(Session session) {
        this.saveSession(session);
    }

    @Override
    protected void doDelete(Session session) {
        if (session == null || session.getId() == null) {
            log.error("session or session id is null");
            return;
        }
        getRedisSessionCache().remove(session.getId());
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        this.saveSession(session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        if (sessionId == null) {
            log.error("session id is null");
            return null;
        }
        return getRedisSessionCache().get(sessionId);
    }

    public long getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(long sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

}
