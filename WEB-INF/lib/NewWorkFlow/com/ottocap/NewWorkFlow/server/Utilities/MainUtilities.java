package com.ottocap.NewWorkFlow.server.Utilities;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class MainUtilities
  extends HttpServlet
{
  private static final long serialVersionUID = 1L;
  
  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException
  {
    String functiontype = request.getParameter("functiontype");
    if (functiontype.equals("pricechecker")) {
      try {
        new PriceChecker(request, response);
      } catch (Exception e) {
        e.printStackTrace();
        throw new IOException(e);
      }
    } else if (functiontype.equals("shippingtest")) {
      try {
        new ShippingTest(request, response);
      } catch (Exception e) {
        e.printStackTrace();
        throw new IOException(e);
      }
    } else if (functiontype.equals("generateStyleData")) {
      try {
        new GenerateStyleData(response);
      } catch (Exception e) {
        e.printStackTrace();
        throw new IOException(e);
      }
    } else if (functiontype.equals("saveallorders")) {
      new SaveAllOrders(request, response);
    }
  }
}
