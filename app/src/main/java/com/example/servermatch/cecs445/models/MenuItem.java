/**
 * @author Andrew Delgado
 */
package com.example.servermatch.cecs445.models;

import com.example.servermatch.cecs445.R;
import com.google.firebase.firestore.Exclude;

import java.util.List;

public class MenuItem {

    private String itemName;
    private String itemDesc;
    private Double itemCost;
    private Integer image;
    private Integer mQuantity;
    private List<String> tags;

    public MenuItem(){
//        mItemName = "Name Not Set";
//        mItemCost = NULL;
//        mImage = NULL;
//        mQuantity = 0;
    }

//    public MenuItem(String itemName, double itemCost){
//        mItemName = itemName;
//        mItemCost = itemCost;
//        mQuantity = 0;
//    }


    public MenuItem(String mItemName, String mItemDesc, Double mItemCost, Integer mImage, List<String> tags) {
        this.itemName = mItemName;
        this.itemDesc = mItemDesc;
        this.itemCost = mItemCost;
        this.image = mImage;
        this.mQuantity = 0;
        this.tags = tags;
    }

    public MenuItem(String name, int quantity, double cost) {
        itemName = name;
        mQuantity = quantity;
        itemCost = cost;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public Double getItemCost() {
        return itemCost;
    }

    public void setItemCost(Double itemCost) {
        this.itemCost = itemCost;
    }

    public Integer getImage() {
        return image;
    }
    @Exclude
    public int getImageResource(int num){
        switch (num){
            case 0:
                return R.drawable.chinese_chicken_salad;
            case 1:
                return R.drawable.pumpkin_pie;
            case 2:
                return R.drawable.shadow_xmas;
            case 3:
                return R.drawable.cheeseburger;
        }
        return R.drawable.default_dish_tofu;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public void setQuantity(Integer quantity) {
        this.mQuantity = quantity;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void incrementQuantity(){++mQuantity;}
    public void decrementQuantity(){--mQuantity;}
    public int getQuantity(){return mQuantity;}

    @Override
    public String toString() {
        return "MenuItem{" +
                "mItemName='" + itemName + '\'' +
                ", mItemCost=" + itemCost +
                ", mImage=" + image +
                ", mQuantity=" + mQuantity +
                ", tags=" + tags +
                '}';
    }

}
