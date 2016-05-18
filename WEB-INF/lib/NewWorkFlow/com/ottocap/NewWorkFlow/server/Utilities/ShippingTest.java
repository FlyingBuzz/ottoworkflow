package com.ottocap.NewWorkFlow.server.Utilities;

import com.ottocap.NewWorkFlow.server.PriceCalculation.Shipping.ShippingCalculation;
import com.ottocap.NewWorkFlow.server.PriceCalculation.Shipping.ShippingData;
import com.ottocap.NewWorkFlow.server.PriceCalculation.Shipping.ShippingItem;
import com.ottocap.NewWorkFlow.server.SQLTable;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;










public class ShippingTest
{
  private String outputstring = "";
  
  public ShippingTest(HttpServletRequest request, HttpServletResponse response) throws Exception
  {
    this.outputstring += "<html><head></head><body>";
    
    SQLTable sqltable = new SQLTable();
    this.outputstring += "====================================================================================================== \n";
    this.outputstring += "Test 1: \n";
    ShippingData mydata = new ShippingData();
    mydata.setZipCode("91730");
    mydata.setHasEmb(true);
    mydata.setPaymentType(ShippingData.PaymentType_COD);
    

    mydata.addShippingItem(new ShippingItem("85-466", "", "003", 5));
    
    ShippingCalculation newcalc = new ShippingCalculation(mydata, sqltable);
    
    this.outputstring += "Shipping Test \n";
    this.outputstring = (this.outputstring + "Current Zip Area: " + mydata.getZipCode() + " \n");
    this.outputstring = (this.outputstring + "Total Items: " + mydata.getTotalShippingItems() + " \n");
    
    this.outputstring += newcalc.getDebugString();
    




















































    this.outputstring = this.outputstring.replace("\n", "<BR>\n");
    this.outputstring += "</body></html>";
    sqltable.closeSQL();
    response.getOutputStream().print(this.outputstring);
  }
}
