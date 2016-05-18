package com.ottocap.NewWorkFlow.server.ExtendOrderData;

import com.ottocap.NewWorkFlow.client.OrderData.OrderDataAddress.AddressType;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataCustomerInformation;


public class ExtendOrderDataCustomerInformation
  extends OrderDataCustomerInformation
{
  private static final long serialVersionUID = 1L;
  private boolean virtualsampleshowaddress = true;
  
  public ExtendOrderDataCustomerInformation() {
    this.billinformation = new ExtendOrderDataAddress(OrderDataAddress.AddressType.BILLING);
    this.shipinformation = new ExtendOrderDataAddress(OrderDataAddress.AddressType.SHIPPING);
  }
  
  public ExtendOrderDataAddress getBillInformation() {
    return (ExtendOrderDataAddress)this.billinformation;
  }
  
  public ExtendOrderDataAddress getShipInformation() {
    return (ExtendOrderDataAddress)this.shipinformation;
  }
  
  public boolean getVirtualSampleShowAddress() {
    return this.virtualsampleshowaddress;
  }
  
  public void setVirtualSampleShowAddress(boolean virtualsampleshowaddress) {
    this.virtualsampleshowaddress = virtualsampleshowaddress;
  }
}
