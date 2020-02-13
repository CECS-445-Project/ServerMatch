package com.example.servermatch.cecs445.models;

/**
 * comment
 */
public class Customer extends Person {
    private String email;
    private String phoneNum;
    private boolean receiptText;
    private boolean receiptEmail;

    public Customer(String firstName, String lastName, String email, String phoneNum, boolean receiptText, boolean receiptEmail) {
        super(firstName, lastName);
        this.email = email;
        this.phoneNum = phoneNum;
        this.receiptText = receiptText;
        this.receiptEmail = receiptEmail;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNum() { return phoneNum; }
    public void setPhone(String phoneNum) { this.phoneNum = phoneNum; }

    public boolean getRecepitText() { return receiptText; }
    public void setReceiptText(boolean receiptText) { this.receiptText = receiptText; }

    public boolean getRecepitEmail() { return receiptEmail; }
    public void setReceiptEmail(boolean receiptEmail) { this.receiptEmail = receiptEmail; }


    @Override
    public String toString() {
        return "Customer[super=" + super.toString() + "email='" + email + '\'' + ", phoneNum='" + phoneNum
                + '\'' + ", receiptText=" + receiptText + ", receiptEmail=" + receiptEmail + ']';
    }
}
