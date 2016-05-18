package com.ottocap.NewWorkFlow.client.OrderData;

import java.io.Serializable;












public class OrderDataDiscount
  extends OrderDataShared
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  
  public String getReason()
  {
    return (String)get("reason", "");
  }
  
  public void setReason(String reason) {
    set("reason", reason);
  }
  
  public Double getAmount() {
    return (Double)get("amount");
  }
  
  public void setAmount(Double amount) {
    set("amount", amount);
  }
  
  public Boolean getIntoItems()
  {
    return (Boolean)get("intoitems");
  }
  
  public void setIntoItems(Boolean intoitems) {
    set("intoitems", intoitems);
  }
}
