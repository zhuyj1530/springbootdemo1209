package com.qf.yongji.springbootdemo1209.realm;

import com.qf.yongji.springbootdemo1209.pojo.User;
import com.qf.yongji.springbootdemo1209.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    /**
     * 执行授权逻辑
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("授权");
        //给资源进行授权
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
       //添加资源的授权字符串
//        simpleAuthorizationInfo.addStringPermission("user:add");
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();
        User dbUser = userService.findById(user.getId());
        System.out.println(dbUser.getPerms());
        simpleAuthorizationInfo.addStringPermission(dbUser.getPerms());
        return simpleAuthorizationInfo;
    }

    /**
     * 执行认证逻辑
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String principal = (String) authenticationToken.getPrincipal();
        Object credentials = authenticationToken.getCredentials();
        String password = new String((char[])credentials);
        User user = userService.findByUserName(principal);
        if (user.getUsername().equals(principal) && user.getPassword().equals(password)){
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(), "userRealm");
            return simpleAuthenticationInfo;
        }
        //用户名或密码不正确
        return null;
    }
}
