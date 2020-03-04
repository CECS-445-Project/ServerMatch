/**
 * @author Andrew Delgado & Howard Chen
 */
package com.example.servermatch.cecs445.models;

import com.google.firebase.firestore.Exclude;

import java.util.List;

public class MenuItem {

    private String documentId;
    private String itemName;
    private String itemDesc;
    private Double itemCost;
    private String image;
  @Exclude private int mIntQuantity;

    private List<String> tags;

    public MenuItem(){ }

    public MenuItem(String mItemName, String mItemDesc, Double mItemCost, String mImage, List<String> tags) {
        this.itemName = mItemName;
        this.itemDesc = mItemDesc;
        this.itemCost = mItemCost;
        this.image = mImage;
        this.mIntQuantity = 0;
        this.tags = tags;
    }

    public MenuItem(String mItemName, String mItemDesc, Double mItemCost, String mImage, int quantity) {
        this.itemName = mItemName;
        this.itemDesc = mItemDesc;
        this.itemCost = mItemCost;
        this.image = mImage;
        this.mIntQuantity = quantity;
    }
    @Exclude
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) { this.documentId = documentId; }

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

    public void setmIntQuantity(int mIntQuantity) {
        this.mIntQuantity = mIntQuantity;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void incrementQuantity(){++mIntQuantity;}
    public void decrementQuantity(){--mIntQuantity;}
    public int getQuantity(){return mIntQuantity;}

    @Override
    public String toString() {
        return "MenuItem{" +
                "documentId='" + documentId + '\'' +
                ", itemName='" + itemName + '\'' +
                ", itemDesc='" + itemDesc + '\'' +
                ", itemCost=" + itemCost +
                ", image='" + image + '\'' +
                ", mQuantity=" + mIntQuantity +
                ", mIntQuantity=" + mIntQuantity +
                ", tags=" + tags +
                '}';
    }

}
