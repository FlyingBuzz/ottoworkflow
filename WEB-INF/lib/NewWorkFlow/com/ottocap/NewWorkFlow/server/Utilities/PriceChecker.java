package com.ottocap.NewWorkFlow.server.Utilities;

import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderData;
import com.ottocap.NewWorkFlow.server.OrderExpander;
import com.ottocap.NewWorkFlow.server.PriceCalculation.DomesticPriceCalculation;
import com.ottocap.NewWorkFlow.server.PriceCalculation.OverseasPriceCalculation;
import com.ottocap.NewWorkFlow.server.SQLTable;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PriceChecker
{
  public PriceChecker(HttpServletRequest request, HttpServletResponse response) throws Exception
  {
    SQLTable sqltable = new SQLTable();
    response.setContentType("text/html; charset=UTF-8");
    
    OrderExpander myorder = new OrderExpander(Integer.valueOf(request.getParameter("hiddenkey")).intValue(), request.getSession(), sqltable);
    
    ExtendOrderData thedata = myorder.getExpandedOrderData();
    if (thedata.getOrderType() == com.ottocap.NewWorkFlow.client.OrderData.OrderData.OrderType.DOMESTIC) {
      DomesticPriceCalculation mycalc = new DomesticPriceCalculation(sqltable, thedata);
      mycalc.calculateCustomerPrice(true);
      response.getOutputStream().println(mycalc.getCustomerTotalPrice());
      response.getOutputStream().println("<BR>====================================<BR>");
      response.getOutputStream().println(mycalc.getHiddenEcho());
      
      response.getOutputStream().println("<BR>=EMBROIDERY-ORDER=<BR>");
      DomesticPriceCalculation mycalc2 = new DomesticPriceCalculation(sqltable, thedata);
      mycalc2.calculateOrderPrice(DomesticPriceCalculation.Embroidery);
      response.getOutputStream().println(mycalc2.getHiddenEcho());
      response.getOutputStream().println("<BR>====================================<BR>");
      
      response.getOutputStream().println("<BR>=DIRECT-TO-GARMENT-ORDER=<BR>");
      DomesticPriceCalculation mycalc3 = new DomesticPriceCalculation(sqltable, thedata);
      mycalc3.calculateOrderPrice(DomesticPriceCalculation.DirectToGarment);
      response.getOutputStream().println(mycalc3.getHiddenEcho());
      response.getOutputStream().println("<BR>====================================<BR>");
      
      response.getOutputStream().println("<BR>=CAD-PRINT-ORDER=<BR>");
      DomesticPriceCalculation mycalc6 = new DomesticPriceCalculation(sqltable, thedata);
      mycalc6.calculateOrderPrice(DomesticPriceCalculation.CADPrint);
      response.getOutputStream().println(mycalc6.getHiddenEcho());
      response.getOutputStream().println("<BR>====================================<BR>");
      
      response.getOutputStream().println("<BR>=SCREEN-PRINT-ORDER=<BR>");
      DomesticPriceCalculation mycalc4 = new DomesticPriceCalculation(sqltable, thedata);
      mycalc4.calculateOrderPrice(DomesticPriceCalculation.ScreenPrint);
      response.getOutputStream().println(mycalc4.getHiddenEcho());
      response.getOutputStream().println("<BR>====================================<BR>");
      
      response.getOutputStream().println("<BR>=HEAT-TRANSFER-ORDER=<BR>");
      DomesticPriceCalculation mycalc5 = new DomesticPriceCalculation(sqltable, thedata);
      mycalc5.calculateOrderPrice(DomesticPriceCalculation.HeatTransfer);
      response.getOutputStream().println(mycalc5.getHiddenEcho());
      response.getOutputStream().println("<BR>====================================<BR>");
    }
    else
    {
      OverseasPriceCalculation mycalc = new OverseasPriceCalculation(sqltable, thedata);
      mycalc.calculateOrderPrice(true);
      response.getOutputStream().println(mycalc.getOutput());
    }
    sqltable.closeSQL();
  }
}
