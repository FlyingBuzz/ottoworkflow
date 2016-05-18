package com.ottocap.NewWorkFlow.client.DataHolder.ProductsInfo.Decoration;

import java.io.Serializable;

public class DecorationInfoNames
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String field1 = "";
  private String field2 = "";
  private String field3 = "";
  private String field4 = "";
  



  public String getField1()
  {
    return this.field1;
  }
  
  public String getField2() {
    return this.field2;
  }
  
  public String getField3() {
    return this.field3;
  }
  
  public String getField4() {
    return this.field4;
  }
  
  public void setNames(String field1, String field2, String field3, String field4) {
    this.field1 = field1;
    this.field2 = field2;
    this.field3 = field3;
    this.field4 = field4;
  }
}
