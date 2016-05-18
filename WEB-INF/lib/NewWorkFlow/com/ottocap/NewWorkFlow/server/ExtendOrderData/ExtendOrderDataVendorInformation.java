package com.ottocap.NewWorkFlow.server.ExtendOrderData;

import com.ottocap.NewWorkFlow.client.OrderData.OrderDataVendorInformation;


public class ExtendOrderDataVendorInformation
  extends OrderDataVendorInformation
{
  private static final long serialVersionUID = 1L;
  private String vendorname;
  
  public String getVendorName()
  {
    return this.vendorname;
  }
  
  public void setVendorName(String vendorname) {
    this.vendorname = vendorname;
  }
}
