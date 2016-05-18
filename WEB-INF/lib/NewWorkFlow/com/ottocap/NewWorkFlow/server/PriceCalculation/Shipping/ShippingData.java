package com.ottocap.NewWorkFlow.server.PriceCalculation.Shipping;

import java.util.Vector;










public class ShippingData
{
  static int PaymentType_CC = 0;
  static int PaymentType_SCOD = 1;
  public static int PaymentType_COD = 2;
  
  private String zipcode = "";
  private boolean residential = false;
  private int paymenttype = PaymentType_CC;
  private Vector<ShippingItem> myitems = new Vector();
  private Vector<Vector<ShippingItem>> myfilteredproducts = new Vector();
  private boolean shippingitemschanged = false;
  private boolean hasemb = false;
  private String addressline1_to = "";
  private String city_to = "";
  private String state_to = "";
  


  public int getTotalShippingItems()
  {
    return this.myitems.size();
  }
  
  public void generateNewFilterProduct() {
    this.myfilteredproducts = new Vector();
    for (int i = 0; i < 4; i++) {
      this.myfilteredproducts.add(new Vector());
    }
    for (int i = 0; i < this.myitems.size(); i++) {
      int producttype = ((ShippingItem)this.myitems.get(i)).getProductType();
      ((Vector)this.myfilteredproducts.get(producttype)).add((ShippingItem)this.myitems.get(i));
    }
    this.shippingitemschanged = false;
  }
  
  public Vector<ShippingItem> getShippingItemOf(int producttype) {
    if (this.shippingitemschanged) {
      generateNewFilterProduct();
    }
    return (Vector)this.myfilteredproducts.get(producttype);
  }
  
  public int getTotalShippingItemsOf(int producttype) {
    return getShippingItemOf(producttype).size();
  }
  
  public void addShippingItem(ShippingItem myitem) {
    this.myitems.add(myitem);
    this.shippingitemschanged = true;
  }
  
  public ShippingItem getShippingItem(int position) {
    return (ShippingItem)this.myitems.get(position);
  }
  
  public void clearShippingItems() {
    this.myitems = new Vector();
    this.shippingitemschanged = true;
  }
  
  public void setZipCode(String zipcode) {
    this.zipcode = zipcode;
  }
  
  public String getZipCode() {
    return this.zipcode;
  }
  
  public void setResidential(boolean residential) {
    this.residential = residential;
  }
  
  public boolean getResidential() {
    return this.residential;
  }
  
  public void setAddressLine1_To(String addressline1_to) {
    this.addressline1_to = addressline1_to;
  }
  
  public String getAddressLine1_To() {
    return this.addressline1_to;
  }
  
  public void setCity_To(String city_to) {
    this.city_to = city_to;
  }
  
  public String getCity_To() {
    return this.city_to;
  }
  
  public void setState_To(String state_to) {
    this.state_to = state_to;
  }
  
  public String getState_To() {
    return this.state_to;
  }
  
  public void setPaymentType(int paymenttype)
  {
    this.paymenttype = paymenttype;
  }
  
  public int getPaymentType() {
    return this.paymenttype;
  }
  
  public void setHasEmb(boolean hasemb) {
    this.hasemb = hasemb;
  }
  
  public boolean getHasEmb() {
    return this.hasemb;
  }
}
