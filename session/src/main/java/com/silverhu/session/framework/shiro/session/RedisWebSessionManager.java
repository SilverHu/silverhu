package com.silverhu.session.framework.shiro.session;

import java.io.Serializable;

import javax.servlet.ServletRequest;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionKey;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RedisWebSessionManager extends DefaultWebSessionManager {

    @Override
    protected Session retrieveSession(SessionKey sessionKey) throws UnknownSessionException {
        Serializable sessionId = getSessionId(sessionKey);
        if (sessionId == null) {
            log.debug("Unable to resolve session ID from SessionKey [{}].  Returning null to indicate a "
                    + "session could not be found.", sessionKey);
            return null;
        }

        // *************** 解决频繁从远程获取session信息 ****************
        ServletRequest request = null;
        if (sessionKey instanceof WebSessionKey) {
            request = ((WebSessionKey) sessionKey).getServletRequest();
        }

        if (request != null) {
            Object s = request.getAttribute(sessionId.toString());
            if (s != null) {
                 log.debug("SESSION FROM LOCAL REQUEST OBJECT");
                return (Session) s;
            }
        }
        // *************** END ****************

        log.debug("GET SESSION FROM REDIS");
        Session s = retrieveSessionFromDataSource(sessionId);
        if (s == null) {
            // session ID was provided, meaning one is expected to be found, but
            // we couldn't find one:
            String msg = "Could not find session with ID [" + sessionId + "]";
            throw new UnknownSessionException(msg);
        }
        // 远程获取并赋值到request对象中
        if (request != null) {
            request.setAttribute(sessionId.toString(), s);
        }
        return s;
    }
}
