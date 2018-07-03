package com.nj.rcxc;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Fred on 2018/6/11.
 */
public class AuthenticationTest {

    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();


    @Before
    public void addUser(){
        //simpleAccountRealm.addAccount("Harry","123456");
        simpleAccountRealm.addAccount("Harry","123456","admin","user");
    }
    @Test
    public void testAuthentication(){

        //1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(simpleAccountRealm);

        //2.主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("Harry","123456");
        subject.login(token);

        //System.out.println("subject.isAuthenticated()-->"+subject.isAuthenticated());
        //subject.logout();
        //System.out.println("subject.isAuthenticated()-->"+subject.isAuthenticated());
        subject.checkRoles("admin","user");

    }
}
