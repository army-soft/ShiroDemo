package com.nj.rcxc.vo;

/**
 * Created by Fred on 2018/6/19.
 */
public class User {
    private String username;
    private String password;
    private boolean rememberMe;
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }
}
