package com.ottocap.NewWorkFlow.server.ExtendOrderData;

import com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogoDecoration;


public class ExtendOrderDataLogoDecoration
  extends OrderDataLogoDecoration
{
  private static final long serialVersionUID = 1L;
  private String field1name;
  private String field2name;
  private String field3name;
  private String field4name;
  
  public String getField1Name()
  {
    return this.field1name;
  }
  
  public void setField1Name(String field1name) {
    this.field1name = field1name;
  }
  
  public String getField2Name() {
    return this.field2name;
  }
  
  public void setField2Name(String field2name) {
    this.field2name = field2name;
  }
  
  public String getField3Name() {
    return this.field3name;
  }
  
  public void setField3Name(String field3name) {
    this.field3name = field3name;
  }
  
  public String getField4Name() {
    return this.field4name;
  }
  
  public void setField4Name(String field4name) {
    this.field4name = field4name;
  }
  
  public ExtendOrderDataLogoDecoration copy() {
    ExtendOrderDataLogoDecoration orderdatalogodecoration = new ExtendOrderDataLogoDecoration();
    orderdatalogodecoration.setName(getName());
    orderdatalogodecoration.setField1(getField1());
    orderdatalogodecoration.setField2(getField2());
    orderdatalogodecoration.setField3(getField3());
    orderdatalogodecoration.setField4(getField4());
    orderdatalogodecoration.setComments(getComments());
    
    orderdatalogodecoration.setField1Name(getField1Name());
    orderdatalogodecoration.setField2Name(getField2Name());
    orderdatalogodecoration.setField3Name(getField3Name());
    orderdatalogodecoration.setField4Name(getField4Name());
    
    return orderdatalogodecoration;
  }
}
