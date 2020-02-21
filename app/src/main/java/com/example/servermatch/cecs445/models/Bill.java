package com.example.servermatch.cecs445.models;

import com.google.firebase.firestore.Exclude;

import java.util.List;

public class Bill {

    private String documentId;
    private String customerID;
    private Double totalCost;
    private List<MenuItem> billItems;

    public Bill() {
    }

    public Bill(String customerID, Double totalCost, List<MenuItem> menuItems) {
        this.customerID = customerID;
        this.totalCost = totalCost;
        this.billItems = menuItems;
    }
    @Exclude
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
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
}
