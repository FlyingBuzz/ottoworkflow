package com.ottocap.NewWorkFlow.client;

import com.ottocap.NewWorkFlow.client.OrderData.OrderData;
import java.io.Serializable;
import java.util.ArrayList;




public class OrderDataWithStyle
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private OrderData orderdata = null;
  private ArrayList<StyleInformationData> styleinfoarray = null;
  



  public void setOrderData(OrderData orderdata)
  {
    this.orderdata = orderdata;
  }
  
  public OrderData getOrderData() {
    return this.orderdata;
  }
  
  public void setStyleInfoArray(ArrayList<StyleInformationData> styleinfoarray) {
    this.styleinfoarray = styleinfoarray;
  }
  
  public ArrayList<StyleInformationData> getStyleInfoArray() {
    return this.styleinfoarray;
  }
}
