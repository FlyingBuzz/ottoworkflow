package com.ottocap.NewWorkFlow.client.OrderData;

import java.io.Serializable;









public class OrderDataLogoDecoration
  extends OrderDataShared
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  
  public void setName(String name)
  {
    set("name", name);
  }
  
  public String getName() {
    return (String)get("name", "");
  }
  
  public void setField1(Double field1) {
    set("field1", field1);
  }
  
  public Double getField1() {
    return (Double)get("field1");
  }
  
  public void setField2(Double field2) {
    set("field2", field2);
  }
  
  public Double getField2() {
    return (Double)get("field2");
  }
  
  public void setField3(Double field3) {
    set("field3", field3);
  }
  
  public Double getField3() {
    return (Double)get("field3");
  }
  
  public void setField4(Double field4) {
    set("field4", field4);
  }
  
  public Double getField4() {
    return (Double)get("field4");
  }
  
  public void setComments(String comments) {
    set("comments", comments);
  }
  
  public String getComments() {
    return (String)get("comments", "");
  }
  
  public OrderDataLogoDecoration copy() {
    OrderDataLogoDecoration orderdatalogodecoration = new OrderDataLogoDecoration();
    orderdatalogodecoration.setName(getName());
    orderdatalogodecoration.setField1(getField1());
    orderdatalogodecoration.setField2(getField2());
    orderdatalogodecoration.setField3(getField3());
    orderdatalogodecoration.setField4(getField4());
    orderdatalogodecoration.setComments(getComments());
    return orderdatalogodecoration;
  }
}
