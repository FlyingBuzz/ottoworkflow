package com.ottocap.NewWorkFlow.server.Utilities;

import com.ottocap.NewWorkFlow.client.OrderData.OrderData.OrderType;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderData;
import com.ottocap.NewWorkFlow.server.JOOQ_OrderExpander;
import com.ottocap.NewWorkFlow.server.PriceCalculation.JOOQ_DomesticPriceCalculation;
import com.ottocap.NewWorkFlow.server.PriceCalculation.JOOQ_OverseasPriceCalculation;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JOOQ_PriceChecker
{
  public JOOQ_PriceChecker(HttpServletRequest request, HttpServletResponse response) throws Exception
  {
    response.setContentType("text/html; charset=UTF-8");
    
    JOOQ_OrderExpander myorder = new JOOQ_OrderExpander(Integer.valueOf(request.getParameter("hiddenkey")).intValue(), request.getSession());
    
    ExtendOrderData thedata = myorder.getExpandedOrderData();
    if (thedata.getOrderType() == OrderData.OrderType.DOMESTIC) {
      JOOQ_DomesticPriceCalculation mycalc = new JOOQ_DomesticPriceCalculation(thedata);
      mycalc.calculateCustomerPrice(true);
      response.getOutputStream().println(mycalc.getCustomerTotalPrice());
      response.getOutputStream().println("<BR>====================================<BR>");
      response.getOutputStream().println(mycalc.getHiddenEcho());
      
      response.getOutputStream().println("<BR>=EMBROIDERY-ORDER=<BR>");
      JOOQ_DomesticPriceCalculation mycalc2 = new JOOQ_DomesticPriceCalculation(thedata);
      mycalc2.calculateOrderPrice(JOOQ_DomesticPriceCalculation.Embroidery);
      response.getOutputStream().println(mycalc2.getHiddenEcho());
      response.getOutputStream().println("<BR>====================================<BR>");
      
      response.getOutputStream().println("<BR>=DIRECT-TO-GARMENT-ORDER=<BR>");
      JOOQ_DomesticPriceCalculation mycalc3 = new JOOQ_DomesticPriceCalculation(thedata);
      mycalc3.calculateOrderPrice(JOOQ_DomesticPriceCalculation.DirectToGarment);
      response.getOutputStream().println(mycalc3.getHiddenEcho());
      response.getOutputStream().println("<BR>====================================<BR>");
      
      response.getOutputStream().println("<BR>=SCREEN-PRINT-ORDER=<BR>");
      JOOQ_DomesticPriceCalculation mycalc4 = new JOOQ_DomesticPriceCalculation(thedata);
      mycalc4.calculateOrderPrice(JOOQ_DomesticPriceCalculation.ScreenPrint);
      response.getOutputStream().println(mycalc4.getHiddenEcho());
      response.getOutputStream().println("<BR>====================================<BR>");
      
      response.getOutputStream().println("<BR>=HEAT-TRANSFER-ORDER=<BR>");
      JOOQ_DomesticPriceCalculation mycalc5 = new JOOQ_DomesticPriceCalculation(thedata);
      mycalc5.calculateOrderPrice(JOOQ_DomesticPriceCalculation.HeatTransfer);
      response.getOutputStream().println(mycalc5.getHiddenEcho());
      response.getOutputStream().println("<BR>====================================<BR>");
    }
    else {
      JOOQ_OverseasPriceCalculation mycalc = new JOOQ_OverseasPriceCalculation(thedata);
      mycalc.calculateOrderPrice(true);
      response.getOutputStream().println(mycalc.getOutput());
    }
  }
}
