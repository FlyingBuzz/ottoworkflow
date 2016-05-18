package com.ottocap.NewWorkFlow.server.PriceCalculation.Helper;

import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderData;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataItem;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataLogo;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;






public class DomesticFreeSwatchCalculator
{
  ExtendOrderData myorderdata;
  TreeMap<String, DomesticSampleDiscountCounter> mydiscounts = new TreeMap();
  TreeSet<String> hasottoproduct = new TreeSet();
  
  public DomesticFreeSwatchCalculator(ExtendOrderData orderdata) throws Exception {
    this.myorderdata = orderdata;
    

    TreeMap<String, Integer> logoamount = new TreeMap();
    TreeMap<String, Integer> logoswatchamount = new TreeMap();
    for (int i = 0; i < this.myorderdata.getSetCount(); i++)
    {

      String capflattype = this.myorderdata.getSet(i).getItem(0).getGoodsType();
      
      for (int j = 0; j < this.myorderdata.getSet(i).getLogoCount(); j++) {
        if ((this.myorderdata.getSet(i).getLogo(j).getDecoration().equals("Flat Embroidery")) || (this.myorderdata.getSet(i).getLogo(j).getDecoration().equals("3D Embroidery")))
        {
          String uniqueid = getUniqueID(this.myorderdata.getSet(i).getLogo(j).getDecoration(), capflattype, String.valueOf(this.myorderdata.getSet(i).getLogo(j).getStitchCount()), this.myorderdata.getSet(i).getLogo(j).getFilename());
          if (!logoamount.containsKey(uniqueid)) {
            logoamount.put(uniqueid, this.myorderdata.getSet(i).getItem(0).getQuantity());
          } else {
            logoamount.put(uniqueid, Integer.valueOf(((Integer)logoamount.get(uniqueid)).intValue() + this.myorderdata.getSet(i).getItem(0).getQuantity().intValue()));
          }
          
          if ((this.myorderdata.getSet(i).getLogo(j).getSwatchTotalDone() != null) && (this.myorderdata.getSet(i).getLogo(j).getSwatchTotalDone().intValue() > 0)) {
            if ((!this.myorderdata.getSet(i).getItem(0).getStyleNumber().equals("nonotto")) && (!this.myorderdata.getSet(i).getItem(0).getStyleNumber().equals("nonottoflat"))) {
              this.hasottoproduct.add(uniqueid);
            }
            if (!logoswatchamount.containsKey(uniqueid)) {
              logoswatchamount.put(uniqueid, this.myorderdata.getSet(i).getLogo(j).getSwatchTotalDone());
            } else {
              logoswatchamount.put(uniqueid, Integer.valueOf(((Integer)logoswatchamount.get(uniqueid)).intValue() + this.myorderdata.getSet(i).getLogo(j).getSwatchTotalDone().intValue()));
            }
          }
        }
      }
    }
    


    String[] swatchlist = (String[])logoswatchamount.keySet().toArray(new String[0]);
    for (int i = 0; i < swatchlist.length; i++)
    {
      for (int totalswatchs = 0; totalswatchs < ((Integer)logoswatchamount.get(swatchlist[i])).intValue(); totalswatchs++) {
        double logodiscount = 0.0D;
        
        if (((Integer)logoamount.get(swatchlist[i])).intValue() >= 144) {
          String[] styleparts = swatchlist[i].split("<>");
          String decorationtype = styleparts[0].trim();
          String stitchcount = styleparts[2].trim();
          int samplestitchcount = Integer.valueOf(stitchcount).intValue();
          int samplestitchmultiple = (int)Math.floor(samplestitchcount / 10000.0D) + 1;
          logodiscount += samplestitchmultiple * 8.0D;
          if (decorationtype.equals("3D Embroidery")) {
            logodiscount += 2.0D;
          }
          
          logoamount.put(swatchlist[i], Integer.valueOf(fixlogoquantity(((Integer)logoamount.get(swatchlist[i])).intValue())));
        }
        

        if (logodiscount > 0.0D) {
          if (this.mydiscounts.containsKey(swatchlist[i])) {
            ((DomesticSampleDiscountCounter)this.mydiscounts.get(swatchlist[i])).addFreeSample(logodiscount);
          } else {
            DomesticSampleDiscountCounter myitem = new DomesticSampleDiscountCounter();
            myitem.addFreeSample(logodiscount);
            this.mydiscounts.put(swatchlist[i], myitem);
          }
        }
      }
    }
  }
  
  public boolean hasOttoProduct(String uniqueid)
  {
    return this.hasottoproduct.contains(uniqueid);
  }
  
  public String getUniqueID(String decoration, String capflattype, String stitchcount, String filename) {
    return decoration + " <> " + capflattype + " <> " + stitchcount + " <> " + getMatchingFilename(filename);
  }
  
  private String getMatchingFilename(String filename) {
    if (filename.indexOf(".") != filename.lastIndexOf(".")) {
      String[] myfilenamearray = filename.split("\\.");
      String newstring = "";
      for (int i = 0; i < myfilenamearray.length; i++) {
        if (i != myfilenamearray.length - 2) {
          newstring = newstring + myfilenamearray[i] + ".";
        }
      }
      newstring = newstring.substring(0, newstring.length() - 1);
      return newstring.toLowerCase();
    }
    return filename.toLowerCase();
  }
  
  public TreeMap<String, DomesticSampleDiscountCounter> getDiscounts()
  {
    return this.mydiscounts;
  }
  
  private int fixlogoquantity(int quantity) {
    if (quantity - 500 >= 144)
      return quantity - 500;
    if ((quantity < 644) && (quantity >= 500)) {
      return 144;
    }
    return 0;
  }
}
