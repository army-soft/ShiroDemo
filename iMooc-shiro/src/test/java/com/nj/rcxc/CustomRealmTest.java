package com.nj.rcxc;

import com.nj.rcxc.shiro.realm.CustomRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * Created by Fred on 2018/6/11.
 */
public class CustomRealmTest {
    @Test
    public void testAuthentication(){

        CustomRealm customRealm = new CustomRealm();


        //1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(customRealm);

        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        //设置加密算法的名称
        matcher.setHashAlgorithmName("md5");
        matcher.setHashIterations(1);
        //设置加密方式到自定义的Realm中
        customRealm.setCredentialsMatcher(matcher);

        //2.主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        //UsernamePasswordToken token = new UsernamePasswordToken("Harry","123456");
        UsernamePasswordToken token = new UsernamePasswordToken("fred","111111");

        subject.login(token);

        System.out.println("subject.isAuthenticated()-->"+subject.isAuthenticated());

        subject.checkRole("admin");
        subject.checkPermissions("user:delete","user:add");


    }
}
