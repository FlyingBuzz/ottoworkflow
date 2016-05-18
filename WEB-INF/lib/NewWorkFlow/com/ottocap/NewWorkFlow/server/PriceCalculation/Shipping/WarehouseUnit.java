package com.ottocap.NewWorkFlow.server.PriceCalculation.Shipping;










public class WarehouseUnit
{
  private long id = 0L;
  private String zipcode = "";
  private double distance = 0.0D;
  private ShippingData myshippingdata = new ShippingData();
  
  public WarehouseUnit(long id, String zipcode) {
    this.id = id;
    this.zipcode = zipcode;
  }
  
  public ShippingData getShippingData() {
    return this.myshippingdata;
  }
  
  public long getId() {
    return this.id;
  }
  
  public int getZipCode() {
    return Integer.valueOf(this.zipcode.substring(0, 5)).intValue();
  }
  
  public String getFullZipCode() {
    return this.zipcode;
  }
  
  public void setDistance(double distance) {
    this.distance = distance;
  }
  
  public double getDistance() {
    return this.distance;
  }
}
