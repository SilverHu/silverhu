package com.silverhu.session.framework.shiro;

import java.util.Set;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.silverhu.session.framework.shiro.util.SessionUtils;


/**
 * 客户端Realm，安全数据源
 *
 * @author sliverhu
 */
public class ClientRealm extends AuthorizingRealm {

    /**
     * 为当前登录的Subject授予角色和权限
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Set<String> permissions = (Set<String>) SessionUtils.getPermissions();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(permissions);
        return info;
    }

    /**
     * 用户名密码方式认证过程，认证成功后返回AuthenticationInfo对象
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        throw new UnsupportedOperationException("永远不会被调用");
    }
}
