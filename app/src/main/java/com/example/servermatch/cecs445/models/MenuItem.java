/**
 * @author Andrew Delgado & Howard Chen
 */
package com.example.servermatch.cecs445.models;

import com.google.firebase.firestore.Exclude;

import java.util.List;

public class MenuItem  implements Comparable<MenuItem> {

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

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
     * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
     * <tt>y.compareTo(x)</tt> throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
     * <tt>x.compareTo(z)&gt;0</tt>.
     *
     * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
     * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
     * all <tt>z</tt>.
     *
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
     * class that implements the <tt>Comparable</tt> interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     *
     * <p>In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.
     *
     * @param m the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(MenuItem m) {
        if(this.getItemName().compareTo(m.getItemName()) < 1) return -1;
        if(this.getItemName().compareTo(m.getItemName()) == 0) return 0;

        return 1;
    }
}
