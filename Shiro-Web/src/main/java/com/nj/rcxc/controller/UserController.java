package com.nj.rcxc.controller;

import com.nj.rcxc.vo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Fred on 2018/6/19.
 */
@Controller
public class UserController {

    @RequestMapping(value = "/subLogin",method = RequestMethod.POST,
    produces = "application/json;charset=utf-8")
    @ResponseBody
    public String subLogin(User user){

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(),user.getPassword());
        try{
            token.setRememberMe(user.getRememberMe());
            subject.login(token);
        } catch (AuthenticationException e){
            return e.getMessage();
        }
        String str = "登录成功！";
        if (subject.hasRole("admin")){
            str +="管理员";
        }
        //subject.checkPermission("user:delete");

        if(subject.isPermitted("user:delete")){
            str += ",超级权限";
        }
        return str;
    }

    //@RequiresRoles("admin")
    @RequestMapping(value = "/testRole",method = RequestMethod.GET)
    @ResponseBody
    public String testRole(){
        return "testRole success";
    }

    @RequestMapping(value = "/testRole1",method = RequestMethod.GET)
    @ResponseBody
    public String testRole1(){
        return "testRole1 success";
    }

    //@RequiresPermissions("user:add")
    @RequestMapping(value = "/testPermission",method = RequestMethod.GET)
    @ResponseBody
    public String testPermission(){
        return "testPermission success";
    }

    @RequestMapping(value = "/testPermission1",method = RequestMethod.GET)
    @ResponseBody
    public String testPermission1(){
        return "testPermission1 success";
    }
}
