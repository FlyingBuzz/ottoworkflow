package com.ottocap.NewWorkFlow.server.ExtendOrderData;

import com.ottocap.NewWorkFlow.client.OrderData.OrderDataItem;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogo;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataSet;
import java.util.ArrayList;

public class ExtendOrderDataSet
  extends OrderDataSet
{
  private static final long serialVersionUID = 1L;
  
  public ExtendOrderDataItem getItem(int i)
  {
    return (ExtendOrderDataItem)this.theitems.get(i);
  }
  
  public ExtendOrderDataItem addItem(ExtendOrderDataItem theitem) {
    this.changehappened = true;
    this.theitems.add(theitem);
    return theitem;
  }
  
  public ExtendOrderDataItem addItem() {
    this.changehappened = true;
    ExtendOrderDataItem newitem = new ExtendOrderDataItem();
    this.theitems.add(newitem);
    return newitem;
  }
  
  public ExtendOrderDataLogo getLogo(int i) {
    return (ExtendOrderDataLogo)this.thelogos.get(i);
  }
  
  public ExtendOrderDataLogo addLogo(ExtendOrderDataLogo thelogo) {
    this.changehappened = true;
    this.thelogos.add(thelogo);
    return thelogo;
  }
  
  public ExtendOrderDataLogo addLogo() {
    this.changehappened = true;
    ExtendOrderDataLogo newlogo = new ExtendOrderDataLogo();
    this.thelogos.add(newlogo);
    return newlogo;
  }
  
  public ExtendOrderDataSet copy() {
    ExtendOrderDataSet orderdataset = new ExtendOrderDataSet();
    for (OrderDataItem currentitem : this.theitems) {
      orderdataset.addItem(((ExtendOrderDataItem)currentitem).copy());
    }
    for (OrderDataLogo currentlogo : this.thelogos) {
      orderdataset.addLogo(((ExtendOrderDataLogo)currentlogo).copy());
    }
    return orderdataset;
  }
}
