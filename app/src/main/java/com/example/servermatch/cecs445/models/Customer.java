package com.example.servermatch.cecs445.models;

import com.google.firebase.firestore.Exclude;

/**
 * author: Howard
 * TO DO : delete comments for old class.
 */

public class Customer {
    private String documentId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNum;
    private boolean receiptText;
    private boolean receiptEmail;
    private Integer visits;//number of Visits. going to somehow connect to each bill paid by customer.

    public Customer(String firstName, String lastName, String email, String phoneNum, boolean receiptText, boolean receiptEmail) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNum = phoneNum;
        this.receiptText = receiptText;
        this.receiptEmail = receiptEmail;
        this.visits = 0;

    }

    public Customer(){}

    @Exclude
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNum() { return phoneNum; }
    public void setPhone(String phoneNum) { this.phoneNum = phoneNum; }

    public boolean getRecepitText() { return receiptText; }
    public void setReceiptText(boolean receiptText) { this.receiptText = receiptText; }

    public boolean getRecepitEmail() { return receiptEmail; }
    public void setReceiptEmail(boolean receiptEmail) { this.receiptEmail = receiptEmail; }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public boolean isReceiptText() {
        return receiptText;
    }

    public boolean isReceiptEmail() {
        return receiptEmail;
    }

    public Integer getVisits() {
        return visits;
    }

    public void setVisits(Integer visits) {
        this.visits = visits;
    }

    @Override
    public String toString() {
        return "Customer[super=" + super.toString() + "email='" + email + '\'' + ", phoneNum='" + phoneNum
                + '\'' + ", receiptText=" + receiptText + ", receiptEmail=" + receiptEmail + ']';
    }
}
