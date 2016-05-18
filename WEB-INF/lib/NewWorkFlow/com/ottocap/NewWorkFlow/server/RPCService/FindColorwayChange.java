package com.ottocap.NewWorkFlow.server.RPCService;

import com.ottocap.NewWorkFlow.client.OrderData.OrderData;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogo;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogoColorway;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataSet;
import com.ottocap.NewWorkFlow.server.SharedData;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.TreeSet;

public class FindColorwayChange
{
  private TreeMap<String, TreeSet<String>> alldiffcolorways = new TreeMap();
  
  public FindColorwayChange(OrderData orderdata) {
    Iterator localIterator2;
    for (Iterator localIterator1 = orderdata.getSets().iterator(); localIterator1.hasNext(); 
        localIterator2.hasNext())
    {
      OrderDataSet theset = (OrderDataSet)localIterator1.next();
      localIterator2 = theset.getLogos().iterator(); continue;OrderDataLogo thelogo = (OrderDataLogo)localIterator2.next();
      if ((thelogo.getDecoration().equals("Flat Embroidery")) || (thelogo.getDecoration().equals("3D Embroidery"))) {
        int stitchcount = thelogo.getStitchCount() != null ? thelogo.getStitchCount().intValue() : 0;
        thelogo.setColorChange(addToMap(getMatchingFilename(thelogo.getFilename() + stitchcount), thelogo.getColorways()));
      }
    }
  }
  

  private boolean addToMap(String name, ArrayList<OrderDataLogoColorway> thecolorways)
  {
    TreeSet<String> colorwaystrings = (TreeSet)this.alldiffcolorways.get(name);
    if (colorwaystrings == null) {
      colorwaystrings = new TreeSet();
      this.alldiffcolorways.put(name, colorwaystrings);
    }
    int currentarraysize = colorwaystrings.size();
    String currentcolorstring = "";
    for (OrderDataLogoColorway thecolors : thecolorways) {
      currentcolorstring = currentcolorstring + thecolors.getThreadType() + thecolors.getLogoColorCode();
    }
    colorwaystrings.add(currentcolorstring);
    int newarraysize = colorwaystrings.size();
    
    if (SharedData.isFaya.booleanValue()) {
      return (newarraysize > 2) && (newarraysize > currentarraysize);
    }
    return (newarraysize != 1) && (newarraysize > currentarraysize);
  }
  
  private String getMatchingFilename(String filename)
  {
    int fileextindex = filename.lastIndexOf(".");
    if (fileextindex != -1) {
      int substringindex = filename.lastIndexOf(".", fileextindex - 1);
      if (substringindex != -1) {
        return (filename.substring(0, substringindex) + filename.substring(fileextindex)).toLowerCase();
      }
    }
    
    return filename.toLowerCase();
  }
}
