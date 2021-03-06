package com.example.servermatch.cecs445.models;

import com.google.firebase.firestore.Exclude;

import java.util.List;

public class Bill {

    private String documentId;
    private String customerId;
    private String customerEmail;
    private Double totalCost;

   @Exclude private List<MenuItem> billItems; //excluded for database but handled in app

    public Bill() {
    }


    public Bill(Double totalCost, List<MenuItem> menuItems) {
        this.totalCost = totalCost;
        this.billItems = menuItems;
    }

    public Bill(String customerID, Double totalCost, List<MenuItem> menuItems) {
        this.customerId = customerID;
        this.totalCost = totalCost;
        this.billItems = menuItems;
    }


    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public List<MenuItem> getBillItems() {
        return billItems;
    }

    public void setBillItems(List<MenuItem> billItems) {
        this.billItems = billItems;
    }

    @Exclude
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getCustomerID() {
        return customerId;
    }

    public void setCustomerID(String customerID) {
        this.customerId = customerID;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public List<MenuItem> getMenuItems() {
        return billItems;
    }

    public void setMenuItems(List<MenuItem> billItems) {
        this.billItems = billItems;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "documentID='" + documentId + '\'' +
                ", customerID='" + customerId + '\'' +
                ", totalCost=" + totalCost +
                ", billItems=" + billItems +
                '}';
    }
}
