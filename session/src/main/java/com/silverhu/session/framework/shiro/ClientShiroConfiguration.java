package com.silverhu.session.framework.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.core.RedisTemplate;

import com.silverhu.session.framework.shiro.cache.RedisCacheManager;
import com.silverhu.session.framework.shiro.session.RedisSessionDAO;

@Configuration
public class ClientShiroConfiguration {
    public static final String PREMISSION_STRING = "perms[\"{0}\"]";

    @Autowired
    private RedisTemplate redisSessionTemplate;

    // session过期时间，单位：毫秒
    @Value("${session.globalSessionTimeout}")
    private int globalSessionTimeout;
    @Value("${session.cookieId}")
    private String cookieId;
    @Value("${session.cookiePath}")
    private String cookiePath;
    @Value("${session.client.shiro.loginUrl}")
    private String loginUrl;
    @Value("${session.client.shiro.successUrl}")
    private String successUrl;
    @Value("${session.client.shiro.unauthorizedUrl}")
    private String unauthorizedUrl;

    /**
     * 定义shiro生命周期
     *
     * @return
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 开启shiro注解
     * 
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 开启shiro注解
     * 
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
    }

    /**
     * 自定义登录方法
     *
     * @return
     */
    @Bean(name = "shiroRealm")
    @DependsOn("lifecycleBeanPostProcessor")
    public ClientRealm shiroRealm() {
        ClientRealm realm = new ClientRealm();
        return realm;
    }

    /**
     * 定义redis会话缓存
     * 
     * @param redisTemplate
     * @return
     */
    @Bean(name = "cacheManager")
    @DependsOn("lifecycleBeanPostProcessor")
    public RedisCacheManager cacheManager() {
        RedisCacheManager cacheManager = new RedisCacheManager();
        cacheManager.setRedisTemplate(redisSessionTemplate);
        cacheManager.setExpire(globalSessionTimeout);
        return cacheManager;
    }

    @Bean(name = "sessionManager")
    @ConfigurationProperties(prefix = "shiro.session")
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(new RedisSessionDAO(redisSessionTemplate, globalSessionTimeout));
        sessionManager.setSessionIdCookie(sessionIdCookie());
        sessionManager.setGlobalSessionTimeout(globalSessionTimeout);
        sessionManager.setDeleteInvalidSessions(true);
        return sessionManager;
    }

    /**
     * 给shiro的sessionId默认的JSSESSIONID名字改掉
     * 
     * @return
     */
    @Bean(name = "sessionIdCookie")
    public SimpleCookie sessionIdCookie() {
        SimpleCookie simpleCookie = new SimpleCookie(cookieId);
        simpleCookie.setPath(cookiePath);
        return simpleCookie;
    }

    /**
     * 定义安全管理器
     *
     * @return
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm());

        // 定义基于redis的会话管理
        securityManager.setCacheManager(cacheManager());
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    /**
     * 定义安全拦截器
     *
     * @return
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());

        // 自定义表单登录拦截器
        Map<String, Filter> filters = new LinkedHashMap<String, Filter>();
        shiroFilterFactoryBean.setFilters(filters);

        // 配置url访问限制
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        filterChainDefinitionMap.put("/logout", "anon");
        filterChainDefinitionMap.put("/api/**", "anon");
        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        shiroFilterFactoryBean.setLoginUrl(loginUrl);
        shiroFilterFactoryBean.setSuccessUrl(successUrl);
        shiroFilterFactoryBean.setUnauthorizedUrl(unauthorizedUrl);
        return shiroFilterFactoryBean;
    }
}
