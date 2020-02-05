package com.example.servermatch.cecs445.models;

/**
 * comment
 */
public class Customer extends Person {
    private String email;
    private String phoneNum;

    public Customer(String firstName, String lastName, String email, String phoneNum) {
        super(firstName, lastName);
        this.email = email;
        this.phoneNum = phoneNum;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNum() { return phoneNum; }
    public void setPhone(String phoneNum) { this.phoneNum = phoneNum; }

    @Override
    public String toString(){
        return ("Customer[super=" + super.toString() + ",email=" + email + ",phone=" + phoneNum + "]");

    }
}
