package com.nj.rcxc.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Fred on 2018/6/11.
 */
public class CustomRealm extends AuthorizingRealm {

    Map<String,String> userMap = new HashMap<String,String>(16);

    {
        userMap.put("fred","f001463a2d886998f609ebbd9d4a5ef3");
        userMap.put("Harry","123456");
        super.setName("customRealm");
    }
    //授权方法
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //从认证信息中获取认证的用户名
        String username = (String) principals.getPrimaryPrincipal();
        //从数据库中或者缓存中获取角色数据
        Set<String> roles = getRolesByUserName(username);
        //从数据库中或者缓存中获取对应的权限信息
        Set<String> permissions = getPermissionsByUserName(username);

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setRoles(roles);
        simpleAuthorizationInfo.setStringPermissions(permissions);

        return simpleAuthorizationInfo;
    }
    //设定用户所具有的相关操作权限
    private Set<String> getPermissionsByUserName(String username) {
        Set<String> sets = new HashSet<String>();
        sets.add("user:select");
        sets.add("user:add");
        sets.add("user:delete");
        return sets;
    }
    //设定对应的用户所具有的相关角色
    private Set<String> getRolesByUserName(String username) {
        Set<String> sets = new HashSet<String>();
        sets.add("admin");
        sets.add("user");
        return sets;
    }

    //AuthenticationToken token是主体传递过来的认证信息
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        //1.获取从主体传过来认证信息中的用户名
        String username = (String) token.getPrincipal();
        System.out.println("token username: " + username);
        //2.通过用户名到数据库中获得凭证也就是对应的密码
        String password = getPasswordByUserName(username);
        //如果没有凭证（也就是密码）说明没有被认证
        if(null == password){
            return null;
        }
        //认证通过后，要生成一个认证对象
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username,password,"customRealm");

        simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("loue"));
        //返回认证后的信息
        return simpleAuthenticationInfo;
    }

    //模拟通过数据库的查找获取凭证信息
    private String getPasswordByUserName(String username) {
        return userMap.get(username);
    }

    public static void main(String[] args) {
        Md5Hash md5Hash = new Md5Hash("111111","loue");
        System.out.println(md5Hash);
    }
}
