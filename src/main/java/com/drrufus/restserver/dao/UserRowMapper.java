package com.drrufus.restserver.dao;

import com.drrufus.restserver.models.UserModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class UserRowMapper implements RowMapper<UserModel> {
    
    @Override
    public UserModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserModel user = new UserModel();
        user.setId(rs.getInt("id"));
        user.setEmail(rs.getString("email"));
        user.setLogin(rs.getString("login"));
        user.setName(rs.getString("name"));
        user.setPass(rs.getString("pass"));
        return user;
    }
}
