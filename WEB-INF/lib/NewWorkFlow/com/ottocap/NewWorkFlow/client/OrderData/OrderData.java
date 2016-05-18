package com.ottocap.NewWorkFlow.client.OrderData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;






public class OrderData
  extends OrderDataShared
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private OrderType ordertype;
  protected OrderDataVendors vendorinformation = new OrderDataVendors();
  protected OrderDataCustomerInformation customerinformation = new OrderDataCustomerInformation();
  protected ArrayList<OrderDataDiscount> discountitems = new ArrayList();
  protected ArrayList<OrderDataSet> thesets = new ArrayList();
  
  public static enum OrderType {
    DOMESTIC,  OVERSEAS;
  }
  
  public OrderDataSet addSet() {
    this.changehappened = true;
    OrderDataSet newset = new OrderDataSet();
    this.thesets.add(newset);
    return newset;
  }
  
  public OrderDataSet addSet(OrderDataSet newset) {
    this.changehappened = true;
    this.thesets.add(newset);
    return newset;
  }
  
  public void removeSet(int i) {
    this.changehappened = true;
    this.thesets.remove(i);
  }
  
  public void removeSet(OrderDataSet theset) {
    this.changehappened = true;
    this.thesets.remove(theset);
  }
  
  public ArrayList<OrderDataSet> getSets() {
    return this.thesets;
  }
  
  public OrderDataSet getSet(int i) {
    return (OrderDataSet)this.thesets.get(i);
  }
  
  public int getSetIndex(OrderDataSet theset) {
    return this.thesets.indexOf(theset);
  }
  
  public int getSetCount() {
    return this.thesets.size();
  }
  
  public void moveSet(int startpos, int newpos) {
    if ((startpos < newpos) || (startpos > newpos)) {
      OrderDataSet myset = (OrderDataSet)this.thesets.get(startpos);
      this.thesets.remove(startpos);
      this.thesets.add(newpos, myset);
      this.changehappened = true;
    }
  }
  
  public OrderDataVendors getVendorInformation() {
    return this.vendorinformation;
  }
  
  public OrderDataDiscount addDiscountItem() {
    this.changehappened = true;
    OrderDataDiscount newdiscountitem = new OrderDataDiscount();
    this.discountitems.add(newdiscountitem);
    return newdiscountitem;
  }
  
  public void moveDiscountItem(int startpos, int newpos) {
    if ((startpos < newpos) || (startpos > newpos)) {
      OrderDataDiscount myitem = (OrderDataDiscount)this.discountitems.get(startpos);
      this.discountitems.remove(startpos);
      this.discountitems.add(newpos, myitem);
      this.changehappened = true;
    }
  }
  
  public void addDiscountItem(OrderDataDiscount newdiscountitem) {
    this.changehappened = true;
    this.discountitems.add(newdiscountitem);
  }
  
  public void removeDiscountItem(int i) {
    this.changehappened = true;
    this.discountitems.remove(i);
  }
  
  public void removeDiscountItem(OrderDataDiscount discountitem) {
    this.changehappened = true;
    this.discountitems.remove(discountitem);
  }
  
  public OrderDataDiscount getDiscountItem(int i) {
    return (OrderDataDiscount)this.discountitems.get(i);
  }
  
  public ArrayList<OrderDataDiscount> getDiscountItems() {
    return this.discountitems;
  }
  
  public int getDiscountItemCount() {
    return this.discountitems.size();
  }
  
  public boolean getChangeHappened()
  {
    if ((this.customerinformation.getChangeHappened()) || (this.vendorinformation.getChangeHappened())) {
      return true;
    }
    
    for (OrderDataDiscount currentdiscountitem : this.discountitems) {
      if (currentdiscountitem.getChangeHappened()) {
        return true;
      }
    }
    
    for (OrderDataSet currentset : this.thesets) {
      if (currentset.getChangeHappened()) {
        return true;
      }
    }
    
    return super.getChangeHappened();
  }
  
  public void setChangeHappened(boolean changehappened)
  {
    this.customerinformation.setChangeHappened(changehappened);
    this.vendorinformation.setChangeHappened(changehappened);
    for (OrderDataDiscount currentdiscountitem : this.discountitems) {
      currentdiscountitem.setChangeHappened(changehappened);
    }
    for (OrderDataSet currentset : this.thesets) {
      currentset.setChangeHappened(changehappened);
    }
    super.setChangeHappened(changehappened);
  }
  


  public void setCustomerInformation(OrderDataCustomerInformation customerinformation)
  {
    this.customerinformation = customerinformation;
  }
  
  public OrderDataCustomerInformation getCustomerInformation() {
    return this.customerinformation;
  }
  
  public Integer getHiddenKey() {
    return (Integer)get("hiddenkey");
  }
  
  public void setHiddenKey(Integer hiddenkey) {
    set("hiddenkey", hiddenkey);
  }
  
  public String getOrderOrKey() {
    if (getOrderNumber().equals("")) {
      return "Quotation #" + String.valueOf(getHiddenKey());
    }
    return "Order Number #" + getOrderNumber();
  }
  
  public OrderType getOrderType()
  {
    return this.ordertype;
  }
  
  public void setOrderType(OrderType ordertype) {
    checkForChange(this.ordertype, ordertype);
    this.ordertype = ordertype;
  }
  
  public Date getOrderDate() {
    return (Date)get("orderdate", new Date());
  }
  
  public void setOrderDate(Date orderdate) {
    set("orderdate", orderdate);
  }
  
  public Date getOrderShipDate() {
    return (Date)get("ordershipdate");
  }
  
  public void setOrderShipDate(Date ordershipdate) {
    set("ordershipdate", ordershipdate);
  }
  
  public void setInHandDate(Date inhanddate) {
    set("inhanddate", inhanddate);
  }
  
  public Date getInHandDate() {
    return (Date)get("inhanddate");
  }
  
  public void setEmployeeId(String employeeid) {
    set("employeeid", employeeid);
  }
  
  public String getEmployeeId() {
    return (String)get("employeeid", "");
  }
  
  public void setOrderNumber(String ordernumber) {
    set("ordernumber", ordernumber);
  }
  
  public String getOrderNumber() {
    return (String)get("ordernumber", "");
  }
  
  public void setRushOrder(boolean rushorder) {
    set("rushorder", Boolean.valueOf(rushorder));
  }
  
  public boolean getRushOrder() {
    return ((Boolean)get("rushorder", Boolean.valueOf(false))).booleanValue();
  }
  
  public void setShippingCost(Double shippingcost) {
    set("shippingcost", shippingcost);
  }
  
  public Double getShippingCost() {
    return (Double)get("shippingcost");
  }
  
  public void setCustomerPO(String customerpo) {
    set("customerpo", customerpo);
  }
  
  public String getCustomerPO() {
    return (String)get("customerpo", "");
  }
  
  public void setEstimatedShipDate(Date estimatedshipdate) {
    set("estimatedshipdate", estimatedshipdate);
  }
  
  public Date getEstimatedShipDate() {
    return (Date)get("estimatedshipdate");
  }
  
  public void setShippingType(String shippingtype) {
    set("shippingtype", shippingtype);
  }
  
  public String getShippingType() {
    return (String)get("shippingtype", "UPS Ground");
  }
  
  public void setSpecialNotes(String specialnotes) {
    set("specialnotes", specialnotes);
  }
  
  public String getSpecialNotes() {
    return (String)get("specialnotes", "");
  }
  
  public void setNextAction(String nextaction) {
    set("nextaction", nextaction);
  }
  
  public String getNextAction() {
    return (String)get("nextaction", "");
  }
  
  public void setInternalDueDateTime(Date internalduedatetime) {
    set("internalduedatetime", internalduedatetime);
  }
  
  public Date getInternalDueDateTime() {
    return (Date)get("internalduedatetime");
  }
  
  public void setOrderStatus(String orderstatus) {
    set("orderstatus", orderstatus);
  }
  
  public String getOrderStatus() {
    return (String)get("orderstatus", "");
  }
  
  public void setQuoteToOrder(String quotetoorder) {
    set("quotetoorder", quotetoorder);
  }
  
  public String getQuoteToOrder() {
    return (String)get("quotetoorder", "");
  }
  
  public void setInternalComments(String internalcomments)
  {
    set("internalcomments", internalcomments);
  }
  
  public String getInternalComments() {
    return (String)get("internalcomments", "");
  }
  
  public void setPotentialRepeatFrequency(String potentialrepeatfrequency) {
    set("potentialrepeatfrequency", potentialrepeatfrequency);
  }
  
  public String getPotentialRepeatFrequency() {
    return (String)get("potentialrepeatfrequency", "");
  }
  
  public void setPotentialRepeatDate(Date potentialrepeatdate) {
    set("potentialrepeatdate", potentialrepeatdate);
  }
  
  public Date getPotentialRepeatDate() {
    return (Date)get("potentialrepeatdate");
  }
  
  public void setPotentialInternalComments(String potentialinternalcomments) {
    set("potentialinternalcomments", potentialinternalcomments);
  }
  
  public String getPotentialInternalComments() {
    return (String)get("potentialinternalcomments", "");
  }
}
