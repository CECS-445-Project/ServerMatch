package com.example.servermatch.cecs445.models;

public class Restaurant {

    private String name;
    private String phoneNum;
    private String email;

    public Restaurant(String name, String phoneNum, String email) {
        this.name = name;
        this.phoneNum = phoneNum;
        this.email = email;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhoneNum() { return phoneNum; }
    public void setPhoneNum(String phoneNum) { this.phoneNum = phoneNum; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString(){
        return ("Restaurant[name=" + name + ", phone=" + phoneNum + ", email=" + email + "]");
    }
}
