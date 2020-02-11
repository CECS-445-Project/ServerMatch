package com.example.servermatch.cecs445.models;

import java.util.List;

public class MenuItem {

    private String mItemName;
    private double mItemCost;
    private int mImage;
    private List<String> tags;

    public MenuItem(String itemName, double itemCost){
        mItemName = itemName;
        mItemCost = itemCost;
    }

    public MenuItem(String mItemName, double mItemCost, int mImage) {
        this.mItemName = mItemName;
        this.mItemCost = mItemCost;
        this.mImage = mImage;
    }

    public void setItemName(String newItemName){
        mItemName = newItemName;
    }

    public void setmItemCost(double newItemCost) {
        mItemCost = newItemCost;
    }

    public String getItemName() {
        return mItemName;
    }

    public double getItemCost() {
    return mItemCost;
    }

    public int getImage() {
        return mImage;
    }

    public void setImage(int mImage) {
        this.mImage = mImage;
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "mItemName='" + mItemName + '\'' +
                ", mItemCost=" + String.format("%.2f",mItemCost) +
                ", mImage=" + mImage +
                '}';
    }
}
