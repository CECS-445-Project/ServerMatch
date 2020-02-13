/**
 * @author Andrew Delgado
 */
package com.example.servermatch.cecs445.models;

import java.util.List;

import static java.sql.Types.NULL;

public class MenuItem {

    private String mItemName;
    private double mItemCost;
    private int mImage;
    private int mQuantity;
    private List<String> tags;

    public MenuItem(){
        mItemName = "Name Not Set";
        mItemCost = NULL;
        mImage = NULL;
        mQuantity = 0;
    }

    public MenuItem(String itemName, double itemCost){
        mItemName = itemName;
        mItemCost = itemCost;
        mQuantity = 0;
    }

    public MenuItem(String mItemName, double mItemCost, int mImage) {
        this.mItemName = mItemName;
        this.mItemCost = mItemCost;
        this.mImage = mImage;
        mQuantity = 0;
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

    public void setQuanity(int quantity){ mQuantity = quantity; }

    public void incrementQuantity(){++mQuantity;}
    public void decrementQuantity(){--mQuantity;}
    public int getQuantity(){return mQuantity;}

    @Override
    public String toString() {
        return "MenuItem{" +
                "mItemName='" + mItemName + '\'' +
                ", mItemCost=" + mItemCost +
                ", mImage=" + mImage +
                ", mQuantity=" + mQuantity +
                ", tags=" + tags +
                '}';
    }
}
