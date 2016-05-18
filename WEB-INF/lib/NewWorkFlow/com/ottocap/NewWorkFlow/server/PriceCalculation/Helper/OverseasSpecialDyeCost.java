package com.ottocap.NewWorkFlow.server.PriceCalculation.Helper;

import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderData;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataItem;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataSet;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;

public class OverseasSpecialDyeCost
{
  double specialdyevencost = 150.0D;
  double specialdyecuscost = 200.0D;
  
  ArrayList<Double> vendyecost = new ArrayList();
  ArrayList<Double> cusdyecost = new ArrayList();
  
  public OverseasSpecialDyeCost(com.ottocap.NewWorkFlow.server.SQLTable sqltable, ExtendOrderData myorderdata)
  {
    TreeMap<String, TreeMap<String, Integer>> vendor_differentcolors = new TreeMap();
    ArrayList<String[]> item_differentcolors = new ArrayList();
    
    for (int i = 0; i < myorderdata.getSetCount(); i++)
    {
      TreeSet<String> usedcolor = new TreeSet();
      
      if (myorderdata.getSet(i).getItem(0).getColorCode().toUpperCase().startsWith("PMS")) {
        usedcolor.add(myorderdata.getSet(i).getItem(0).getColorCode().toUpperCase());
      }
      if (myorderdata.getSet(i).getItem(0).getPanelStitchColor().toUpperCase().startsWith("PMS")) {
        usedcolor.add(myorderdata.getSet(i).getItem(0).getPanelStitchColor().toUpperCase());
      }
      if (myorderdata.getSet(i).getItem(0).getFrontPanelColor().toUpperCase().startsWith("PMS")) {
        usedcolor.add(myorderdata.getSet(i).getItem(0).getFrontPanelColor().toUpperCase());
      }
      if (myorderdata.getSet(i).getItem(0).getSideBackPanelColor().toUpperCase().startsWith("PMS")) {
        usedcolor.add(myorderdata.getSet(i).getItem(0).getSideBackPanelColor().toUpperCase());
      }
      if (myorderdata.getSet(i).getItem(0).getBackPanelColor().toUpperCase().startsWith("PMS")) {
        usedcolor.add(myorderdata.getSet(i).getItem(0).getBackPanelColor().toUpperCase());
      }
      if (myorderdata.getSet(i).getItem(0).getSidePanelColor().toUpperCase().startsWith("PMS")) {
        usedcolor.add(myorderdata.getSet(i).getItem(0).getSidePanelColor().toUpperCase());
      }
      if (myorderdata.getSet(i).getItem(0).getVisorStitchColor().toUpperCase().startsWith("PMS")) {
        usedcolor.add(myorderdata.getSet(i).getItem(0).getVisorStitchColor().toUpperCase());
      }
      if (myorderdata.getSet(i).getItem(0).getPrimaryVisorColor().toUpperCase().startsWith("PMS")) {
        usedcolor.add(myorderdata.getSet(i).getItem(0).getPrimaryVisorColor().toUpperCase());
      }
      if (myorderdata.getSet(i).getItem(0).getVisorTrimColor().toUpperCase().startsWith("PMS")) {
        usedcolor.add(myorderdata.getSet(i).getItem(0).getVisorTrimColor().toUpperCase());
      }
      if (myorderdata.getSet(i).getItem(0).getVisorSandwichColor().toUpperCase().startsWith("PMS")) {
        usedcolor.add(myorderdata.getSet(i).getItem(0).getVisorSandwichColor().toUpperCase());
      }
      if (myorderdata.getSet(i).getItem(0).getUndervisorColor().toUpperCase().startsWith("PMS")) {
        usedcolor.add(myorderdata.getSet(i).getItem(0).getUndervisorColor().toUpperCase());
      }
      if (myorderdata.getSet(i).getItem(0).getDistressedVisorInsideColor().toUpperCase().startsWith("PMS")) {
        usedcolor.add(myorderdata.getSet(i).getItem(0).getDistressedVisorInsideColor().toUpperCase());
      }
      if (myorderdata.getSet(i).getItem(0).getClosureStrapColor().toUpperCase().startsWith("PMS")) {
        usedcolor.add(myorderdata.getSet(i).getItem(0).getClosureStrapColor().toUpperCase());
      }
      if (myorderdata.getSet(i).getItem(0).getClosureStrapColor().toUpperCase().startsWith("PMS")) {
        usedcolor.add(myorderdata.getSet(i).getItem(0).getClosureStrapColor().toUpperCase());
      }
      if (myorderdata.getSet(i).getItem(0).getFrontEyeletColor().toUpperCase().startsWith("PMS")) {
        usedcolor.add(myorderdata.getSet(i).getItem(0).getFrontEyeletColor().toUpperCase());
      }
      if (myorderdata.getSet(i).getItem(0).getSideBackEyeletColor().toUpperCase().startsWith("PMS")) {
        usedcolor.add(myorderdata.getSet(i).getItem(0).getSideBackEyeletColor().toUpperCase());
      }
      if (myorderdata.getSet(i).getItem(0).getBackEyeletColor().toUpperCase().startsWith("PMS")) {
        usedcolor.add(myorderdata.getSet(i).getItem(0).getBackEyeletColor().toUpperCase());
      }
      if (myorderdata.getSet(i).getItem(0).getSideEyeletColor().toUpperCase().startsWith("PMS")) {
        usedcolor.add(myorderdata.getSet(i).getItem(0).getSideEyeletColor().toUpperCase());
      }
      if (myorderdata.getSet(i).getItem(0).getButtonColor().toUpperCase().startsWith("PMS")) {
        usedcolor.add(myorderdata.getSet(i).getItem(0).getButtonColor().toUpperCase());
      }
      if (myorderdata.getSet(i).getItem(0).getInnerTapingColor().toUpperCase().startsWith("PMS")) {
        usedcolor.add(myorderdata.getSet(i).getItem(0).getInnerTapingColor().toUpperCase());
      }
      if (myorderdata.getSet(i).getItem(0).getSweatbandColor().toUpperCase().startsWith("PMS")) {
        usedcolor.add(myorderdata.getSet(i).getItem(0).getSweatbandColor().toUpperCase());
      }
      if (myorderdata.getSet(i).getItem(0).getSweatbandStripeColor().toUpperCase().startsWith("PMS")) {
        usedcolor.add(myorderdata.getSet(i).getItem(0).getSweatbandStripeColor().toUpperCase());
      }
      

      String[] thecolors = (String[])usedcolor.toArray(new String[0]);
      item_differentcolors.add(thecolors);
      
      for (int j = 0; j < thecolors.length; j++) {
        TreeMap<String, Integer> colorqty = (TreeMap)vendor_differentcolors.get(String.valueOf(myorderdata.getSet(i).getItem(0).getOverseasVendorNumber()));
        if (colorqty == null) {
          colorqty = new TreeMap();
          colorqty.put(thecolors[j], myorderdata.getSet(i).getItem(0).getQuantity());
          vendor_differentcolors.put(String.valueOf(myorderdata.getSet(i).getItem(0).getOverseasVendorNumber()), colorqty);
        } else {
          Integer qty = (Integer)colorqty.get(thecolors[j]);
          if (qty == null) {
            colorqty.put(thecolors[j], myorderdata.getSet(i).getItem(0).getQuantity());
          } else {
            colorqty.put(thecolors[j], Integer.valueOf(((Integer)colorqty.get(thecolors[j])).intValue() + myorderdata.getSet(i).getItem(0).getQuantity().intValue()));
          }
        }
      }
    }
    



    for (int i = 0; i < myorderdata.getSetCount(); i++) {
      TreeMap<String, Integer> colorqty = (TreeMap)vendor_differentcolors.get(String.valueOf(myorderdata.getSet(i).getItem(0).getOverseasVendorNumber()));
      String[] thecolors = (String[])item_differentcolors.get(i);
      
      double vencostforitem = 0.0D;
      double cuscostforitem = 0.0D;
      
      for (int j = 0; j < thecolors.length; j++)
      {
        int colorquantity = ((Integer)colorqty.get(thecolors[j])).intValue();
        
        vencostforitem += this.specialdyevencost / colorquantity;
        cuscostforitem += this.specialdyecuscost / colorquantity;
      }
      

      this.vendyecost.add(Double.valueOf(vencostforitem));
      this.cusdyecost.add(Double.valueOf(cuscostforitem));
    }
  }
  

  public double getVenDyeCost(int item)
  {
    return ((Double)this.vendyecost.get(item)).doubleValue();
  }
  
  public double getCusDyeCost(int item) {
    return ((Double)this.cusdyecost.get(item)).doubleValue();
  }
}
