package com.example.servermatch.cecs445.models;

public class MenuItem {

    private String mItemName;
    private double mItemCost;

   public MenuItem(String itemName, double itemCost){
       mItemName = itemName;
       mItemCost = itemCost;
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
}
