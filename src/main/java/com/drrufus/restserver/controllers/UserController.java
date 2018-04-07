package com.drrufus.restserver.controllers;

import com.drrufus.restserver.models.UserModel;
import com.drrufus.restserver.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    
    private transient Logger logger = LoggerFactory.getLogger(UserController.class);
    
    @Autowired
    UserService userService;
    
    @RequestMapping(path = "/get", method = GET)
    public Object get(@RequestParam(value="id", defaultValue="0") Integer id,
            @RequestParam(value="login", defaultValue="") String login,
            @RequestParam(value="email", defaultValue="") String email,
            @RequestParam(value="name", defaultValue="") String name,
            @RequestParam(value="pass", defaultValue="") String pass) {
        logger.info("Processing request on /get");
        try {
            return userService.getUserByParams((id == null || id < 1) ? null : id,
                (login == null || login.isEmpty()) ? null : login, 
                (name == null || name.isEmpty()) ? null : name, 
                (email == null || email.isEmpty()) ? null : email, 
                (pass == null || pass.isEmpty()) ? null : pass);
        }
        catch (Exception e) {
            logger.error("Error while users retrieving: " + e.getMessage());
            return e;
        }
    }
    
    @RequestMapping(path = "/create", method = POST, 
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Object create(@RequestBody UserModel user) {
        logger.info("Processing request on /create");
        try {
            return userService.createUser(user.getLogin(), user.getName(), user.getEmail(), user.getPass());
        }
        catch (Exception e) {
            logger.error("Error while user creating: " + e.getMessage());
            return e;
        }
    }
    
    @RequestMapping(path = "/edit", method = PUT,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Object update(@RequestBody UserModel user) {
        logger.info("Processing request on /edit");
        try {
            userService.modifyUser(user.getId(), user.getLogin(), user.getName(), user.getEmail(), user.getPass());
            return "OK";
        }
        catch (Exception e) {
            logger.error("Error while user editing: " + e.getMessage());
            return e;
        }
    }
    
    @RequestMapping(path = "/delete", method = DELETE)
    public Object delete(@RequestParam(value="id", defaultValue="0") Integer id) {
        logger.info("Processing request on /delete");
        try {
            userService.deleteUser(id);
            return "OK";
        }
        catch (Exception e) {
            logger.error("Error while user deleting: " + e.getMessage());
            return e;
        }
    }
    
}
