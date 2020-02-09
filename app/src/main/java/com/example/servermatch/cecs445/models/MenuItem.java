package com.example.servermatch.cecs445.models;

public class MenuItem {

    private String mItemName;
    private int mItemCost;

   public MenuItem(String itemName, int itemCost){
       mItemName = itemName;
       mItemCost = itemCost;
   }

   public void setItemName(String newItemName){
       mItemName = newItemName;
   }

   public void setmItemCost(int newItemCost) {
       mItemCost = newItemCost;
   }

    public String getItemName() {
        return mItemName;
    }

    public int getItemCost() {
        return mItemCost;
    }
}
