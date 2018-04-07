package com.drrufus.restserver.models;


public class UserModel {
    
    private Integer id;
    private String name;
    private String email;
    private String pass;
    private String login;
    
    public UserModel() {
        
    }

    public UserModel(Integer id, String name, String email, String pass, String login) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.login = login;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
