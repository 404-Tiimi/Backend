package com._4.questionnaire.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity 
public class User {
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int _id;

    @Column(name="role")
    private String _role;
    
    @Column(name="username")
    private String _username;

    @Column(name="passwordhash")
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
