package com.nj.rcxc;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * Created by Fred on 2018/6/11.
 */
public class IniRealmTest {

    @Test
    public void testAuthentication(){

        IniRealm iniRealm = new IniRealm("classpath:user.ini");

        //1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(iniRealm);

        //2.主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("Harry","123456");
        subject.login(token);

        System.out.println("subject.isAuthenticated()-->"+subject.isAuthenticated());
        //subject.logout();
        //System.out.println("subject.isAuthenticated()-->"+subject.isAuthenticated());
        subject.checkRole("admin");

        subject.checkPermission("user:add");

    }
}
