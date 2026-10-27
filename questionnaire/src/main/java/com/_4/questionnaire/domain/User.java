package com._4.questionnaire.domain;

import jakarta.persistence.Entity;

@Entity 
public class User {
    private String _role;
    private int _id;
    private String _username;
    private String _hashPassword;

    public User(String role, int id, String username, String hashPassword) {
        _role = role;
        _id = id;
        _username = username;
        _hashPassword = hashPassword;
    }

    public User(){

    }

    public void setRole(String role) {
        _role = role;
    }

    public void setId(int id) {
        _id = id;
    }

    public void setUsername(String username) {
        _username = username;
    }

    public void setHashPassword(String hashPassword) {
        _hashPassword = hashPassword;
    }

    public String getRole() {
        return _role;
    }

    public int getId() {
        return _id;
    }

    public String getUsername() {
        return _username;
    }

    public String getHashPassword() {
        return _hashPassword;
    }
    

}
