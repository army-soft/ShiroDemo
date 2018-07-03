package com.nj.rcxc.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Created by Fred on 2018/6/22.
 */
public class RolesOrFilter extends AuthorizationFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest,
                                      ServletResponse servletResponse, Object o) throws Exception {
        Subject subject = getSubject(servletRequest,servletResponse);
        String[] roles = (String[]) o;
        //没有指定角色限制的情况
        if(roles == null || roles.length == 0){
            return true;
        }
        //遍历角色列表，如果有对应的角色就返回真
        for(String role : roles){
            if(subject.hasRole(role)){
                return true;
            }
        }
        return false;
    }
}
