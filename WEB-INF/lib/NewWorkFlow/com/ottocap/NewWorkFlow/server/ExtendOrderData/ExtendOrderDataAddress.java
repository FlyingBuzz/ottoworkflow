package com.ottocap.NewWorkFlow.server.ExtendOrderData;

import com.ottocap.NewWorkFlow.client.OrderData.OrderDataAddress.AddressType;

public class ExtendOrderDataAddress extends com.ottocap.NewWorkFlow.client.OrderData.OrderDataAddress {
  private static final long serialVersionUID = 1L;
  
  public ExtendOrderDataAddress(OrderDataAddress.AddressType addresstype) { this.addresstype = addresstype; }
  
  public OrderDataAddress.AddressType getAddressType()
  {
    return this.addresstype;
  }
}
