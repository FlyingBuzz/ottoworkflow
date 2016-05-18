package com.ottocap.NewWorkFlow.server.PriceCalculation.Shipping;

import java.util.TreeMap;











public class ShippingItem
{
  public static final int ProductType_Undefined = 0;
  public static final int ProductType_Cap = 1;
  public static final int ProductType_Shirt = 2;
  public static final int ProductType_SampleBag = 3;
  public static final int TotalProductTypes = 4;
  private String itemid;
  private int quantity;
  private int producttype;
  private String itemsize;
  private String colorcode;
  private double weight = 0.0D;
  private TreeMap<Integer, Integer> boxsizes = new TreeMap();
  
  public ShippingItem(String itemid, String itemsize, String colorcode, int quantity)
  {
    this(itemid, itemsize, colorcode, quantity, 0);
  }
  
  public ShippingItem(String itemid, String itemsize, String colorcode, int quantity, int producttype) {
    this.itemid = itemid;
    this.itemsize = itemsize;
    this.quantity = quantity;
    this.producttype = producttype;
    this.colorcode = colorcode;
  }
  
  public void setItemWeight(double weight) {
    this.weight = weight;
  }
  
  public double getItemWeight() {
    return this.weight;
  }
  
  public void setItemBoxSizes(TreeMap<Integer, Integer> boxsizes) {
    this.boxsizes = boxsizes;
  }
  
  public TreeMap<Integer, Integer> getItemBoxSizes() {
    return this.boxsizes;
  }
  
  public void addBox(int id, int cubicinch)
  {
    this.boxsizes.put(Integer.valueOf(id), Integer.valueOf(cubicinch));
  }
  
  public void setProductType(int producttype) {
    this.producttype = producttype;
  }
  
  public String getItemId() {
    return this.itemid;
  }
  
  public int getQuantity() {
    return this.quantity;
  }
  
  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
  
  public int getProductType() {
    return this.producttype;
  }
  
  public String getItemSize() {
    return this.itemsize;
  }
  
  public String getColorCode() {
    return this.colorcode;
  }
  
  public Item getItem() {
    return new Item(this.itemid, this.weight, this.boxsizes);
  }
}
