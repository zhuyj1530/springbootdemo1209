package com.qf.yongji.springbootdemo1209.shiroConfig;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.qf.yongji.springbootdemo1209.realm.UserRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

@Configuration
public class ShiroConfig {

    /**
     * 创建realm对象
     * @return
     */
    @Bean(name="userRealm")
    public UserRealm getRealm(){
        return new UserRealm();
    }

    /**
     * 创建DefaultWebSecurityManager对象
     * @param userRealm
     * @return
     */
    @Bean(name="securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm")UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联realm
        securityManager.setRealm(userRealm);
        //关联rememberMe
        securityManager.setRememberMeManager(cookieRememberMeManager());
        return securityManager;
    }

    /**
     * 创建ShiroFilterFactoryBean对象
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager")DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //如果没有认证，跳转的登录界面
        shiroFilterFactoryBean.setLoginUrl("/toLogin");
        //如果没有授权，跳转到禁止访问页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/refuse");

        //添加shiro内置过滤器，实现权限相关的url拦截
        LinkedHashMap<String, String> filterMap = new LinkedHashMap<>();
//        filterMap.put("/add", "authc");
//        filterMap.put("/update", "authc");
        /**
         * 常见过滤器：
         * anon：无需认证（登录）可以访问
         * authc：必须认证才可以访问
         * user:如果使用Remember Me的功能，可以直接访问
         * perms:该资源必须得到资源权限才可以访问
         * role:该资源必须得到角色权限才可以访问
         */
        filterMap.put("/login", "anon");
        filterMap.put("/testThymeleaf", "user");
        filterMap.put("/add", "perms[user:add]");
        filterMap.put("/update", "perms[user:update]");
        filterMap.put("/logout", "logout");

        filterMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 配合在thymeleaf中使用shiro坐标
     * @return
     */
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }

    /**
     * 创建cookie和管理cookie对象
     * @return
     */
    @Bean
    public CookieRememberMeManager cookieRememberMeManager(){
        SimpleCookie rememberMe = new SimpleCookie("rememberMe");
        rememberMe.setMaxAge(3000);
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMe);
        return cookieRememberMeManager;
    }
}
