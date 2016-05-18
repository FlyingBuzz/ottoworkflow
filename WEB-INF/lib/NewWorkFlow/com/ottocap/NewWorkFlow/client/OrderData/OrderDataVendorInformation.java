package com.ottocap.NewWorkFlow.client.OrderData;

import java.io.Serializable;
import java.util.Date;












public class OrderDataVendorInformation
  extends OrderDataShared
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  
  public Integer getVendor()
  {
    return get("vendor") != null ? Integer.valueOf((String)get("vendor")) : null;
  }
  
  public void setVendor(Integer vendor) {
    set("vendor", vendor != null ? String.valueOf(vendor) : null);
  }
  
  public String getWorkOrderNumber() {
    return (String)get("workordernumber", "");
  }
  
  public void setWorkOrderNumber(String workordernumber) {
    set("workordernumber", workordernumber);
  }
  
  public void setDigitizingProcessingDate(Date digitizingprocessingdate) {
    set("digitizingprocessingdate", digitizingprocessingdate);
  }
  
  public Date getDigitizingProcessingDate() {
    return (Date)get("digitizingprocessingdate");
  }
  
  public void setDigitizingDueDate(Date digitizingduedate) {
    set("digitizingduedate", digitizingduedate);
  }
  
  public Date getDigitizingDueDate() {
    return (Date)get("digitizingduedate");
  }
  
  public void setWorkOrderProcessingDate(Date workorderprocessingdate) {
    set("workorderprocessingdate", workorderprocessingdate);
  }
  
  public Date getWorkOrderProcessingDate() {
    return (Date)get("workorderprocessingdate");
  }
  
  public void setWorkOrderDueDate(Date workorderduedate) {
    set("workorderduedate", workorderduedate);
  }
  
  public Date getWorkOrderDueDate() {
    return (Date)get("workorderduedate");
  }
  
  public void setShippingMethod(String shippingmethod) {
    set("shippingmethod", shippingmethod);
  }
  
  public String getShippingMethod() {
    return (String)get("shippingmethod", "Ocean");
  }
}
