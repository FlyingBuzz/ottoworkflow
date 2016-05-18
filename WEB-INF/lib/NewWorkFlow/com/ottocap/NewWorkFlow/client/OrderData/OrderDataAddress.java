package com.ottocap.NewWorkFlow.client.OrderData;

import java.io.Serializable;





public class OrderDataAddress
  extends OrderDataShared
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  protected AddressType addresstype;
  
  public static enum AddressType
  {
    BILLING,  SHIPPING;
  }
  
  public OrderDataAddress(AddressType addresstype) {
    this.addresstype = addresstype;
  }
  

  public OrderDataAddress() {}
  
  public AddressType getAddressType()
  {
    return this.addresstype;
  }
  
  public String getCompany() {
    return (String)get("company", "");
  }
  
  public void setCompany(String company) {
    set("company", company);
  }
  
  public String getStreetLine1() {
    return (String)get("streetline1", "");
  }
  
  public void setStreetLine1(String streetline1) {
    set("streetline1", streetline1);
  }
  
  public void setStreetLine2(String streetline2) {
    set("streetline2", streetline2);
  }
  
  public String getStreetLine2() {
    return (String)get("streetline2", "");
  }
  
  public String getCity() {
    return (String)get("city", "");
  }
  
  public void setCity(String city) {
    set("city", city);
  }
  
  public String getState() {
    return (String)get("state", "");
  }
  
  public void setState(String state) {
    set("state", state);
  }
  
  public String getZip() {
    return (String)get("zip", "");
  }
  
  public void setZip(String zip) {
    set("zip", zip);
  }
  
  public String getCountry() {
    return (String)get("country", "");
  }
  
  public void setCountry(String country) {
    set("country", country);
  }
  
  public boolean getResidential() {
    return ((Boolean)get("residential", Boolean.valueOf(false))).booleanValue();
  }
  
  public void setResidential(boolean residential) {
    set("residential", Boolean.valueOf(residential));
  }
}
