package com.ottocap.NewWorkFlow.server.Utilities;

import com.ottocap.NewWorkFlow.server.PriceCalculation.Shipping.JOOQ_ShippingCalculation;
import com.ottocap.NewWorkFlow.server.PriceCalculation.Shipping.ShippingData;
import com.ottocap.NewWorkFlow.server.PriceCalculation.Shipping.ShippingItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;











public class JOOQ_ShippingTest
{
  private String outputstring = "";
  
  public JOOQ_ShippingTest(HttpServletRequest request, HttpServletResponse response) throws Exception
  {
    this.outputstring += "<html><head></head><body>";
    
    this.outputstring += "====================================================================================================== \n";
    this.outputstring += "Test 1: \n";
    ShippingData mydata = new ShippingData();
    mydata.setZipCode("91730");
    mydata.setHasEmb(true);
    mydata.setPaymentType(ShippingData.PaymentType_COD);
    

    mydata.addShippingItem(new ShippingItem("85-466", "", "003", 5));
    
    JOOQ_ShippingCalculation newcalc = new JOOQ_ShippingCalculation(mydata);
    
    this.outputstring += "Shipping Test \n";
    this.outputstring = (this.outputstring + "Current Zip Area: " + mydata.getZipCode() + " \n");
    this.outputstring = (this.outputstring + "Total Items: " + mydata.getTotalShippingItems() + " \n");
    
    this.outputstring += newcalc.getDebugString();
    
    this.outputstring += "====================================================================================================== \n";
    this.outputstring += "Test 2: \n";
    mydata = new ShippingData();
    mydata.setZipCode("79699");
    
    mydata.addShippingItem(new ShippingItem("11-413", "", "0316", 144));
    
    newcalc = new JOOQ_ShippingCalculation(mydata);
    
    this.outputstring += "Shipping Test \n";
    this.outputstring = (this.outputstring + "Current Zip Area: " + mydata.getZipCode() + " \n");
    this.outputstring = (this.outputstring + "Total Items: " + mydata.getTotalShippingItems() + " \n");
    
    this.outputstring += newcalc.getDebugString();
    

































    this.outputstring = this.outputstring.replace("\n", "<BR>\n");
    this.outputstring += "</body></html>";
    response.getOutputStream().print(this.outputstring);
  }
}
