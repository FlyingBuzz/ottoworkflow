package com.ottocap.NewWorkFlow.client.DataHolder.CustomerInfo;

import com.ottocap.NewWorkFlow.client.OrderData.OrderDataCustomerInformation;
import java.io.Serializable;




public class EclipseDataHolder
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String companylogo = "";
  

  private OrderDataCustomerInformation orderdatacustomerinformation;
  

  public void setCompanyLogo(String companylogo)
  {
    this.companylogo = companylogo;
  }
  
  public String getCompanyLogo() {
    return this.companylogo;
  }
  
  public void setOrderDataCustomerInformation(OrderDataCustomerInformation orderdatacustomerinformation) {
    this.orderdatacustomerinformation = orderdatacustomerinformation;
  }
  
  public OrderDataCustomerInformation getOrderDataCustomerInformation() {
    return this.orderdatacustomerinformation;
  }
}
