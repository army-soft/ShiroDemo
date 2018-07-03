package com.nj.rcxc;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * Created by Fred on 2018/6/11.
 */
public class JdbcRealmTest {

    DruidDataSource druidDataSource = new DruidDataSource();

    {
        druidDataSource.setUrl("jdbc:mysql://localhost:3306/shiro_jdbc_test");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("root");
    }


    @Test
    public void testAuthentication(){

        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(druidDataSource);
        //必须要拖动设置，默认为false
        jdbcRealm.setPermissionsLookupEnabled(true);

        String sql = "select password from test_user where username = ? ";
        jdbcRealm.setAuthenticationQuery(sql);

        String roleSql = "select role_name from test_user_roles where username = ? ";
        jdbcRealm.setUserRolesQuery(roleSql);

        //1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(jdbcRealm);

        //2.主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        //UsernamePasswordToken token = new UsernamePasswordToken("Harry","123456");
        UsernamePasswordToken token = new UsernamePasswordToken("fred","111111");

        subject.login(token);

        System.out.println("subject.isAuthenticated()-->"+subject.isAuthenticated());
        //subject.logout();
        //System.out.println("subject.isAuthenticated()-->"+subject.isAuthenticated());

        /*subject.checkRole("admin");
        subject.checkPermission("user:select");*/

        subject.checkRole("user");

    }
}
