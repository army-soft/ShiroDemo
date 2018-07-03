package com.nj.rcxc.dao;

import com.nj.rcxc.vo.User;

import java.util.List;

/**
 * Created by Fred on 2018/6/20.
 */
public interface UserDao {
    public User getUserByUserName(String username);

    public List<String> queryRolesByUserName(String username);

    public List<String> queryPermissionsByUserName(String username);
}
