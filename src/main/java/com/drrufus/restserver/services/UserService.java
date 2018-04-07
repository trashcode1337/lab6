package com.drrufus.restserver.services;

import com.drrufus.restserver.dao.UserDao;
import com.drrufus.restserver.exceptions.ValidationException;
import com.drrufus.restserver.models.UserModel;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    @Autowired
    private UserDao userDao;
    
    public UserModel getUserById(int id) {
        return userDao.getUserById(id);
    }
    
    public List<UserModel> getUserByParams(Integer id, String login, String name, String email, String pass) throws ValidationException, Exception {
        return userDao.getUserByParams(id, login, name, email, pass);
    }
    
    public int createUser(String login, String name, String email, String pass) throws ValidationException, Exception {
        return userDao.insertUser(login, name, email, pass);
    }
    
    public void modifyUser(Integer id, String login, String name, String email, String pass) throws ValidationException, Exception {
        userDao.modifyUser(id, login, name, email, pass);
    }
    
    public void deleteUser(Integer id) throws ValidationException {
        userDao.deleteUser(id);
    }
    
}
