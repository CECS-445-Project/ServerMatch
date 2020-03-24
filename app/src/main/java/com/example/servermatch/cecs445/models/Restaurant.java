package com.example.servermatch.cecs445.models;

public class Restaurant {

    private String name;
    private String phoneNum;
    private String email;
    private Integer icon;

    public Restaurant(){}

    public Restaurant(String name, String phoneNum, String email, Integer icon) {
        this.name = name;
        this.phoneNum = phoneNum;
        this.email = email;
        this.icon = icon;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhoneNum() { return phoneNum; }
    public void setPhoneNum(String phoneNum) { this.phoneNum = phoneNum; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Integer getIcon() {
        return icon;
    }

    public void setIcon(Integer icon) {
        this.icon = icon;
    }

    @Override
    public String toString(){
        return ("Restaurant[name=" + name + ", phone=" + phoneNum + ", email=" + email
        + "icon= " + icon + "]");
    }
}
