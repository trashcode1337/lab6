package com.drrufus.restserver.dao;

import com.drrufus.restserver.exceptions.ValidationException;
import com.drrufus.restserver.models.UserModel;
import java.util.List;
import javax.mail.internet.InternetAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {
    
    private transient Logger logger = LoggerFactory.getLogger(UserDao.class);
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    public UserModel getUserById(Integer id) {
        logger.info("Retrieving user for id={}", id);
        String sql = "SELECT * FROM `users` WHERE `id` = ? ;";
        UserModel user = (UserModel)jdbcTemplate.queryForObject(sql, new UserRowMapper(), id);
        return user;
    }
    
    public int insertUser(String login, String name, String email, String pass) throws ValidationException {
        
        checkLogin(login);
        checkMail(email);
        checkPass(pass);
        
        String sql = "insert into `users`(`name`, `email`, `login`, `pass`) values"
                + " (?, ?, ?, ?)";
        jdbcTemplate.update(sql, name, email, login, pass);
        
        sql = "select max(id) from `users`;";
        Integer lastId = jdbcTemplate.queryForObject(sql, Integer.class);
        logger.info("Inserted with ID={}", lastId);
        return lastId;
    }

    public List<UserModel> getUserByParams(Integer id, String login, String name, String email, String pass) throws Exception {
        
        checkId(id);
        checkLogin(login);
        checkMail(email);
        checkPass(pass);
        
        logger.info("Retrieving users by id={}, login={}, name={}, email={}, pass={}",
                id, login, name, email, pass);
        
        if (id == null && login == null && name == null
                && email == null && pass == null)
            throw new Exception("No parameters for searching found");
        
        String sql = "SELECT * FROM `users` WHERE" +
                (id == null ? "" : (" `id` = " + id)) +
                (login == null ? "" : (" `login` = '" + login + "'")) +
                (name == null ? "" : (" `name` = '" + name + "'")) +
                (email == null ? "" : (" `email` = '" + email + "'")) +
                (pass == null ? "" : (" `pass` = '" + pass + "'"));
        logger.info("Generated SQL: {}", sql);
        List<UserModel> users = jdbcTemplate.query(sql, new UserRowMapper());
        return users;
    }
    
    public void modifyUser(Integer id, String login, String name, String email, String pass) throws ValidationException, Exception {
        logger.info("Modifying user with id={}; new values: login={}, name={}, email={}, pass={}",
                id, login, name, email, pass);
        checkId(id);
        checkLogin(login);
        checkMail(email);
        checkPass(pass);
        
        String sql = "UPDATE `users` SET " + 
                "`name` = " + (name == null ? "''," : ("'" + name + "',")) +
                "`email` = " + (email == null ? "''," : ("'" + email + "',")) +
                "`login` = " + (login == null ? "''," : ("'" + login + "',")) +
                "`pass` = " + (pass == null ? "''" : ("'" + pass + "'")) +
                " WHERE `id` = " + id.toString() + ";";
        
        logger.info("Generated SQL: {}", sql);
        jdbcTemplate.update(sql);
    }
    
    public void deleteUser(Integer id) throws ValidationException {
        logger.info("Deleting user with ID={}", id);
        checkId(id);
        
        String sql = "DELETE FROM `users` WHERE `id` = " + id.toString() + ";";
        logger.info("Generated SQL: {}", sql);
        jdbcTemplate.update(sql);
    }
    
    private void checkId(Integer id) throws ValidationException {
        if (id == null) return;
        logger.info("Checking id...");
        if (id == null || id < 1)
            throw new ValidationException("Wrong ID value");
    }
    
    private void checkMail(String email) throws ValidationException {
        if (email == null) return;
        logger.info("Checking email...");
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (Exception ex) {
            throw new ValidationException("Wrong email format");
        }
    }
    
    private void checkLogin(String login) throws ValidationException {
        if (login == null) return;
        logger.info("Checking login...");
        if (login == null || login.isEmpty() || login.length() < 6 || login.contains(" "))
            throw new ValidationException("Wrong login format");
    }
    
    private void checkPass(String pass) throws ValidationException {
        if (pass == null) return;
        logger.info("Checking password...");
        if (pass == null || pass.isEmpty() || pass.length() < 8 || pass.contains(" "))
            throw new ValidationException("Wrong password format");
    }
    
}
