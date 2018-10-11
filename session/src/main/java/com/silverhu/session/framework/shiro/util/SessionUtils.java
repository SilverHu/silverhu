package com.silverhu.session.framework.shiro.util;

import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.silverhu.session.framework.shiro.bean.ShiroUser;


/**
 * session工具类
 * 
 * @author sliverhu
 *
 */
public class SessionUtils {

    public static final String CURRENT_USER = "CurrentUser";
    public static final String PERMISSIONS = "permissions";
    
    public static Set<String> getPermissions() {
        return (Set<String>) getSessionAttribute(PERMISSIONS);
    }

    /**
     * 设置权限信息
     * 
     * @param permissions
     */
    public static void setPermissions(Set<String> permissions) {
        addSessionAttribute(PERMISSIONS, permissions);
    }

    /**
     * 设置当前用户信息
     * 
     * @param user
     */
    public static void setCurrentUser(ShiroUser user) {
        addSessionAttribute(CURRENT_USER, user);
    }

    /**
     * 获取当前用户信息
     */
    public static ShiroUser getCurrentUser() {
        return (ShiroUser) getSessionAttribute(CURRENT_USER);
    }

    /**
     * 将一些数据放到ShiroSession中,以便于其它地方使用
     *
     * @param key
     * @param value
     */
    public static void addSessionAttribute(Object key, Object value) {
        Subject currentUser = SecurityUtils.getSubject();
        if (null != currentUser) {
            Session session = currentUser.getSession();
            if (null != session) {
                session.setAttribute(key, value);
            }
        }
    }

    /**
     * 从ShiroSession中取出数据
     *
     * @param key
     * @param value
     */
    public static Object getSessionAttribute(Object key) {
        Subject currentUser = SecurityUtils.getSubject();
        if (null != currentUser) {
            Session session = currentUser.getSession();
            if (null != session) {
                return session.getAttribute(key);
            }
        }
        return null;
    }
}
