package com.ottocap.NewWorkFlow.server.PriceCalculation.Shipping;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;







public class UrlReader
{
  public static String getUrlContext(URL myurl)
    throws Exception
  {
    BufferedReader in = new BufferedReader(
      new InputStreamReader(
      myurl.openStream()));
    
    String totalstuff = "";
    
    String inputLine;
    while ((inputLine = in.readLine()) != null) { String inputLine;
      totalstuff = totalstuff + inputLine;
    }
    
    in.close();
    return totalstuff;
  }
}
