/**
 * @author Andrew Delgado
 */
package com.example.servermatch.cecs445.models;

import java.util.Collection;
import java.util.List;

import static java.sql.Types.NULL;

public class MenuItem {

    private String itemName;
    private String itemDesc;
    private Double itemCost;
    private Integer image;
    private Integer quantity;
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
        this.quantity = 0;
        this.tags = tags;
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

    public void setImage(Integer image) {
        this.image = image;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void incrementQuantity(){++quantity;}
    public void decrementQuantity(){--quantity;}
    public int getQuantity(){return quantity;}

    @Override
    public String toString() {
        return "MenuItem{" +
                "mItemName='" + itemName + '\'' +
                ", mItemCost=" + itemCost +
                ", mImage=" + image +
                ", mQuantity=" + quantity +
                ", tags=" + tags +
                '}';
    }

}
