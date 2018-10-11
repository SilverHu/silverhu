package com.silverhu.session.framework.shiro.session;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.data.redis.core.RedisTemplate;

import lombok.extern.slf4j.Slf4j;

/**
 * Redis会话管理器
 * 
 * @author sliverhu
 *
 */
@Slf4j
public class RedisSessionDAO extends EnterpriseCacheSessionDAO {

    private final String SESSION_PREFIX_ = "session_prefix_";

    private RedisTemplate<String, Session> redisTemplate;

    private long expire;

    public RedisSessionDAO(RedisTemplate redisTemplate, long expire) {
        super();
        this.redisTemplate = redisTemplate;
        this.expire = expire;
    }

    @Override
    protected Serializable doCreate(Session session) {
        super.assignSessionId(session, super.generateSessionId(session));
        updateRedis(session);
        log.debug("doCreate, key : {} = {}", session.getId(), session);
        return session.getId();
    }

    @Override
    protected void doUpdate(Session session) {
        updateRedis(session);
        log.debug("doUpdate, key : {} = {}", session.getId(), session);
    }

    @Override
    protected void doDelete(Session session) {
        redisTemplate.delete(getSessionKey(session.getId()));
        log.debug("doDelete, key : {} = {}", session.getId(), session);
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        Object value = redisTemplate.opsForValue().get(getSessionKey(sessionId));
        if (value != null) {
            Session session = (Session) value;
            updateRedis(session);
            log.debug("doReadSession, key : {} = {}", getSessionKey(session.getId()), session);
            return session;
        }
        return null;
    }

    private void updateRedis(Session session) {
        redisTemplate.opsForValue().set(getSessionKey(session.getId()), session, expire, TimeUnit.MILLISECONDS);
    }

    /**
     * 生成session key
     * 
     * @param k
     * @return
     */
    private String getSessionKey(Serializable sessionId) {
        return SESSION_PREFIX_ + sessionId.toString();
    }
}
