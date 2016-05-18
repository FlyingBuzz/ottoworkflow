package com.ottocap.NewWorkFlow.server.ExtendOrderData;

import com.ottocap.NewWorkFlow.client.OrderData.OrderData;
import java.util.ArrayList;


public class ExtendOrderData
  extends OrderData
{
  private static final long serialVersionUID = 1L;
  private String employeefullname = "";
  private String employeeemail = "";
  private String ordertypefolder = "";
  private double exchangerate;
  
  public ExtendOrderData() {
    this.vendorinformation = new ExtendOrderDataVendors();
    this.customerinformation = new ExtendOrderDataCustomerInformation();
  }
  
  public void setExchangeRate(double exchangerate) {
    this.exchangerate = exchangerate;
  }
  
  public double getExchangeRate() {
    return this.exchangerate;
  }
  
  public ExtendOrderDataSet addSet() {
    this.changehappened = true;
    ExtendOrderDataSet newset = new ExtendOrderDataSet();
    this.thesets.add(newset);
    return newset;
  }
  
  public ExtendOrderDataSet addSet(ExtendOrderDataSet newset) {
    this.changehappened = true;
    this.thesets.add(newset);
    return newset;
  }
  
  public ExtendOrderDataSet getSet(int i) {
    return (ExtendOrderDataSet)this.thesets.get(i);
  }
  
  public ExtendOrderDataVendors getVendorInformation() {
    return (ExtendOrderDataVendors)this.vendorinformation;
  }
  
  public ExtendOrderDataDiscount addDiscountItem() {
    this.changehappened = true;
    ExtendOrderDataDiscount newdiscountitem = new ExtendOrderDataDiscount();
    this.discountitems.add(newdiscountitem);
    return newdiscountitem;
  }
  
  public void addDiscountItem(ExtendOrderDataDiscount newdiscountitem) {
    this.changehappened = true;
    this.discountitems.add(newdiscountitem);
  }
  
  public ExtendOrderDataDiscount getDiscountItem(int i) {
    return (ExtendOrderDataDiscount)this.discountitems.get(i);
  }
  
  public ExtendOrderDataCustomerInformation getCustomerInformation() {
    return (ExtendOrderDataCustomerInformation)this.customerinformation;
  }
  
  public String getEmployeeFullName() {
    return this.employeefullname;
  }
  
  public void setEmployeeFullName(String employeefullname) {
    this.employeefullname = employeefullname;
  }
  
  public String getEmployeeEmail() {
    return this.employeeemail;
  }
  
  public void setEmployeeEmail(String employeeemail) {
    this.employeeemail = employeeemail;
  }
  
  public String getOrderTypeFolder() {
    return this.ordertypefolder;
  }
  
  public void setOrderTypeFolder(String ordertypefolder) {
    this.ordertypefolder = ordertypefolder;
  }
}
