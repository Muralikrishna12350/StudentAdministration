package com.org.StudentAdministration.entity;

public class LoginRequest {

    private String name;
    private String password;

    public void setName(String name) {
    	this.name=name;
    }
    
    public String getName() {
        return name;
    }

    public void setEmail(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "email='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
