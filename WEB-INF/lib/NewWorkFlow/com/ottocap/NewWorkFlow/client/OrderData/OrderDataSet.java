package com.ottocap.NewWorkFlow.client.OrderData;

import java.io.Serializable;
import java.util.ArrayList;


public class OrderDataSet
  extends OrderDataShared
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  protected ArrayList<OrderDataItem> theitems = new ArrayList();
  protected ArrayList<OrderDataLogo> thelogos = new ArrayList();
  
  public ArrayList<OrderDataItem> getItems() {
    return this.theitems;
  }
  
  public OrderDataItem getItem(int i) {
    return (OrderDataItem)this.theitems.get(i);
  }
  
  public OrderDataItem addItem(OrderDataItem theitem) {
    this.changehappened = true;
    this.theitems.add(theitem);
    return theitem;
  }
  
  public OrderDataItem addItem() {
    this.changehappened = true;
    OrderDataItem newitem = new OrderDataItem();
    this.theitems.add(newitem);
    return newitem;
  }
  
  public void removeItem(int i) {
    this.changehappened = true;
    this.theitems.remove(i);
  }
  
  public void removeItem(OrderDataItem theitem) {
    this.changehappened = true;
    this.theitems.remove(theitem);
  }
  
  public int getItemCount() {
    return this.theitems.size();
  }
  
  public void moveItem(int startpos, int newpos) {
    if ((startpos < newpos) || (startpos > newpos)) {
      OrderDataItem myitem = (OrderDataItem)this.theitems.get(startpos);
      this.theitems.remove(startpos);
      this.theitems.add(newpos, myitem);
      this.changehappened = true;
    }
  }
  
  public ArrayList<OrderDataLogo> getLogos() {
    return this.thelogos;
  }
  
  public OrderDataLogo getLogo(int i) {
    return (OrderDataLogo)this.thelogos.get(i);
  }
  
  public OrderDataLogo addLogo(OrderDataLogo thelogo) {
    this.changehappened = true;
    this.thelogos.add(thelogo);
    return thelogo;
  }
  
  public OrderDataLogo addLogo() {
    this.changehappened = true;
    OrderDataLogo newlogo = new OrderDataLogo();
    this.thelogos.add(newlogo);
    return newlogo;
  }
  
  public void removeLogo(int i) {
    this.changehappened = true;
    this.thelogos.remove(i);
  }
  
  public void removeLogo(OrderDataLogo thelogo) {
    this.changehappened = true;
    this.thelogos.remove(thelogo);
  }
  
  public void moveLogo(int startpos, int newpos) {
    if ((startpos < newpos) || (startpos > newpos)) {
      OrderDataLogo mylogo = (OrderDataLogo)this.thelogos.get(startpos);
      this.thelogos.remove(startpos);
      this.thelogos.add(newpos, mylogo);
      this.changehappened = true;
    }
  }
  
  public int getLogoCount() {
    return this.thelogos.size();
  }
  
  public boolean getChangeHappened()
  {
    for (OrderDataLogo currentlogo : this.thelogos) {
      if (currentlogo.getChangeHappened()) {
        return true;
      }
    }
    
    for (OrderDataItem currentitem : this.theitems) {
      if (currentitem.getChangeHappened()) {
        return true;
      }
    }
    
    return super.getChangeHappened();
  }
  
  public void setChangeHappened(boolean changehappened)
  {
    for (OrderDataLogo currentlogo : this.thelogos) {
      currentlogo.setChangeHappened(changehappened);
    }
    for (OrderDataItem currentitem : this.theitems) {
      currentitem.setChangeHappened(changehappened);
    }
    super.setChangeHappened(changehappened);
  }
  
  public int getLogoIndex(OrderDataLogo orderdatalogo) {
    return this.thelogos.indexOf(orderdatalogo);
  }
  
  public OrderDataSet copy() {
    OrderDataSet orderdataset = new OrderDataSet();
    for (OrderDataItem currentitem : this.theitems) {
      orderdataset.addItem(currentitem.copy());
    }
    for (OrderDataLogo currentlogo : this.thelogos) {
      orderdataset.addLogo(currentlogo.copy());
    }
    return orderdataset;
  }
}
