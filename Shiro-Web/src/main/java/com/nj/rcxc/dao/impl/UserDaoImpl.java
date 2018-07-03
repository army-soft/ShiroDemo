package com.nj.rcxc.dao.impl;

import com.nj.rcxc.dao.UserDao;
import com.nj.rcxc.vo.User;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Fred on 2018/6/20.
 */
@Component
public class UserDaoImpl implements UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public User getUserByUserName(String username) {
        String sql = "select username,password from users where username=?";
        List<User> list = jdbcTemplate.query(sql, new String[]{username}, new RowMapper<User>() {

            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();
                //这里如果要用列的数字来取值时要从 1 开始
                user.setUsername(resultSet.getString(1));
                user.setPassword(resultSet.getString(2));
                return user;
            }
        });
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<String> queryRolesByUserName(String username) {
        String sql = "select role_name from user_roles where username=?";
        return jdbcTemplate.query(sql, new String[]{username}, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString(1);
            }
        });
    }

    @Override
    public List<String> queryPermissionsByUserName(String username) {
        String sql = "select permission from roles_permissions p,user_roles u "+
                "where p.role_name=u.role_name and u.username=? ";
        return jdbcTemplate.query(sql, new String[]{username}, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString(1);
            }
        });
    }
}
