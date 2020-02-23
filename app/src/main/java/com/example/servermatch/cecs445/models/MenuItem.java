/**
 * @author Andrew Delgado
 */
package com.example.servermatch.cecs445.models;

import com.example.servermatch.cecs445.R;
import com.google.firebase.firestore.Exclude;

import java.util.List;

public class MenuItem {
    private String documentId;
    private String itemName;
    private String itemDesc;
    private Double itemCost;
    private String image;
    private Integer mQuantity;


    private int mIntQuantity;

    private List<String> tags;

    public MenuItem(){ }

    public MenuItem(String mItemName, String mItemDesc, Double mItemCost, String mImage, List<String> tags) {
        this.itemName = mItemName;
        this.itemDesc = mItemDesc;
        this.itemCost = mItemCost;
        this.image = mImage;
        this.mQuantity = 0;
        this.tags = tags;
    }
    @Exclude
    public String getDocumentId() {
        return documentId;
    }

    public MenuItem(String name, int quantity, double cost) {
        itemName = name;
        mIntQuantity = quantity;
        itemCost = cost;
    }

    public int getmIntQuantity() {
        return mIntQuantity;
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

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
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
