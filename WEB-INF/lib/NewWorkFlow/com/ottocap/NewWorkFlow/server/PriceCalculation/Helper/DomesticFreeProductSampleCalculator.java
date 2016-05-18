package com.ottocap.NewWorkFlow.server.PriceCalculation.Helper;

import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderData;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataCustomerInformation;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataItem;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataLogo;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataSet;
import com.ottocap.NewWorkFlow.server.PriceCalculation.ListItemPrice;
import com.ottocap.NewWorkFlow.server.SQLTable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;






public class DomesticFreeProductSampleCalculator
{
  ExtendOrderData myorderdata;
  TreeMap<Integer, DomesticSampleDiscountCounter> mydiscounts = new TreeMap();
  
  public DomesticFreeProductSampleCalculator(ExtendOrderData orderdata, SQLTable sqltable, ItemHelper itemhelper) throws Exception {
    this.myorderdata = orderdata;
    

    TreeMap<String, Integer> logoamount = new TreeMap();
    for (int i = 0; i < this.myorderdata.getSetCount(); i++)
    {
      String capflattype = this.myorderdata.getSet(i).getItem(0).getGoodsType();
      
      for (int j = 0; j < this.myorderdata.getSet(i).getLogoCount(); j++) {
        if ((this.myorderdata.getSet(i).getLogo(j).getDecoration().equals("Flat Embroidery")) || (this.myorderdata.getSet(i).getLogo(j).getDecoration().equals("3D Embroidery")) || (this.myorderdata.getSet(i).getLogo(j).getDecoration().equals("Direct To Garment")))
        {
          String uniqueid = getUniqueID(this.myorderdata.getSet(i).getLogo(j).getDecoration(), capflattype, String.valueOf(this.myorderdata.getSet(i).getLogo(j).getStitchCount()), this.myorderdata.getSet(i).getLogo(j).getFilename());
          if (!logoamount.containsKey(uniqueid)) {
            logoamount.put(uniqueid, this.myorderdata.getSet(i).getItem(0).getQuantity());
          } else {
            logoamount.put(uniqueid, Integer.valueOf(((Integer)logoamount.get(uniqueid)).intValue() + this.myorderdata.getSet(i).getItem(0).getQuantity().intValue()));
          }
        }
      }
    }
    


    ArrayList<ListItemPrice> samplehatcost = new ArrayList();
    
    boolean sampleispricelevel = false;
    double pricelevelmorediscount = 0.0D;
    int pricelevel = 1;
    String pricelevelstring = "";
    if (sqltable.makeTable("SELECT `pricelevel` FROM `eclipse_customer_info` WHERE `eclipseaccount` = '" + this.myorderdata.getCustomerInformation().getEclipseAccountNumber() + "'").booleanValue()) {
      pricelevelstring = ((String)sqltable.getValue()).toLowerCase();
    } else {
      pricelevelstring = "1";
    }
    
    if (pricelevelstring.contains("s")) {
      sampleispricelevel = true;
    }
    if (pricelevelstring.contains("-")) {
      pricelevelmorediscount = Double.valueOf("0." + pricelevelstring.substring(pricelevelstring.indexOf("-") + 1).replace("s", "")).doubleValue();
    } else if (pricelevelstring.contains("+")) {
      pricelevelmorediscount = Double.valueOf("-0." + pricelevelstring.substring(pricelevelstring.indexOf("+") + 1).replace("s", "")).doubleValue();
    }
    pricelevel = Integer.valueOf(pricelevelstring.substring(0, 1)).intValue();
    
    for (int i = 0; i < this.myorderdata.getSetCount(); i++) {
      int totalsamplesdone = this.myorderdata.getSet(i).getItem(0).getProductSampleTotalDone() != null ? this.myorderdata.getSet(i).getItem(0).getProductSampleTotalDone().intValue() : 0;
      if (totalsamplesdone > 0) {
        double price1 = 0.0D;
        double price2 = 0.0D;
        double price3 = 0.0D;
        double price4 = 0.0D;
        
        if ((!this.myorderdata.getSet(i).getItem(0).getStyleNumber().equals("nonotto")) && (!this.myorderdata.getSet(i).getItem(0).getStyleNumber().equals("nonottoflat"))) {
          if (sqltable.makeTable("SELECT * FROM `styles_domestic` WHERE `sku` = '" + this.myorderdata.getSet(i).getItem(0).getStyleNumber() + "' LIMIT 1").booleanValue()) {
            price1 = Double.valueOf((String)sqltable.getCell("price1")).doubleValue();
            price2 = Double.valueOf((String)sqltable.getCell("price2")).doubleValue();
            price3 = Double.valueOf((String)sqltable.getCell("price3")).doubleValue();
            price4 = Double.valueOf((String)sqltable.getCell("price4")).doubleValue();
          } else if (sqltable.makeTable("SELECT * FROM `styles_domestic_totebags` WHERE `sku` = '" + this.myorderdata.getSet(i).getItem(0).getStyleNumber() + "' LIMIT 1").booleanValue()) {
            price1 = Double.valueOf((String)sqltable.getCell("price1")).doubleValue();
            price2 = Double.valueOf((String)sqltable.getCell("price2")).doubleValue();
            price3 = Double.valueOf((String)sqltable.getCell("price3")).doubleValue();
            price4 = Double.valueOf((String)sqltable.getCell("price4")).doubleValue();
          } else if (sqltable.makeTable("SELECT * FROM `styles_domestic_aprons` WHERE `sku` = '" + this.myorderdata.getSet(i).getItem(0).getStyleNumber() + "' LIMIT 1").booleanValue()) {
            price1 = Double.valueOf((String)sqltable.getCell("price1")).doubleValue();
            price2 = Double.valueOf((String)sqltable.getCell("price2")).doubleValue();
            price3 = Double.valueOf((String)sqltable.getCell("price3")).doubleValue();
            price4 = Double.valueOf((String)sqltable.getCell("price4")).doubleValue();
          } else if (sqltable.makeTable("SELECT * FROM `styles_domestic_specials` WHERE `style` = '" + this.myorderdata.getSet(i).getItem(0).getStyleNumber() + "' ORDER BY `endingdate` DESC LIMIT 1").booleanValue()) {
            price1 = ((Double)sqltable.getCell("price")).doubleValue();
            price2 = ((Double)sqltable.getCell("price")).doubleValue();
            price3 = ((Double)sqltable.getCell("price")).doubleValue();
            price4 = ((Double)sqltable.getCell("price")).doubleValue();
          } else if (sqltable.makeTable("SELECT * FROM `domestic_price_shirts` WHERE `style` = '" + this.myorderdata.getSet(i).getItem(0).getStyleNumber() + "-" + this.myorderdata.getSet(i).getItem(0).getColorCode() + "-" + this.myorderdata.getSet(i).getItem(0).getSize() + "' LIMIT 1").booleanValue()) {
            price1 = ((Double)sqltable.getCell("price1")).doubleValue();
            price2 = ((Double)sqltable.getCell("price2")).doubleValue();
            price3 = ((Double)sqltable.getCell("price3")).doubleValue();
            price4 = ((Double)sqltable.getCell("price4")).doubleValue();
          } else if (sqltable.makeTable("SELECT * FROM `domestic_price_sweatshirts` WHERE `style` = '" + this.myorderdata.getSet(i).getItem(0).getStyleNumber() + "-" + this.myorderdata.getSet(i).getItem(0).getColorCode() + "-" + this.myorderdata.getSet(i).getItem(0).getSize() + "' LIMIT 1").booleanValue()) {
            price1 = ((Double)sqltable.getCell("price1")).doubleValue();
            price2 = ((Double)sqltable.getCell("price2")).doubleValue();
            price3 = ((Double)sqltable.getCell("price3")).doubleValue();
            price4 = ((Double)sqltable.getCell("price4")).doubleValue();
          }
        }
        
        double sampleprice = 0.0D;
        if (sampleispricelevel) {
          if (pricelevel == 1) {
            sampleprice = price1 - pricelevelmorediscount;
          } else if (pricelevel == 2) {
            sampleprice = price2 - pricelevelmorediscount;
          } else if (pricelevel == 3) {
            sampleprice = price3 - pricelevelmorediscount;
          } else if (pricelevel == 4) {
            sampleprice = price4 - pricelevelmorediscount;
          }
        } else if ((!this.myorderdata.getSet(i).getItem(0).getStyleNumber().equals("nonotto")) && (!this.myorderdata.getSet(i).getItem(0).getStyleNumber().equals("nonottoflat")))
        {
          sampleprice = price1;
        } else {
          sampleprice = price1;
        }
        samplehatcost.add(new ListItemPrice(i, sampleprice));
      }
    }
    


    Collections.sort(samplehatcost, ListItemPrice.getPriceDesc());
    
    for (int k = 0; k < samplehatcost.size(); k++) {
      int item = ((ListItemPrice)samplehatcost.get(k)).getItem();
      
      if ((!this.myorderdata.getSet(item).getItem(0).getStyleNumber().equals("nonotto")) && (!this.myorderdata.getSet(item).getItem(0).getStyleNumber().equals("nonottoflat")) && (((Integer)itemhelper.getCloneOfTotalStyleItem().get(this.myorderdata.getSet(item).getItem(0).getSameStyle())).intValue() >= 144))
      {
        String capflattype = this.myorderdata.getSet(item).getItem(0).getGoodsType();
        
        boolean onlyemb = true;
        boolean allreqmeet = true;
        
        for (int sampleamount = 0; sampleamount < this.myorderdata.getSet(item).getItem(0).getProductSampleTotalDone().intValue(); sampleamount++) {
          double logodiscount = 0.0D;
          for (int j = 0; j < this.myorderdata.getSet(item).getLogoCount(); j++) {
            if ((this.myorderdata.getSet(item).getLogo(j).getDecoration().equals("Flat Embroidery")) || (this.myorderdata.getSet(item).getLogo(j).getDecoration().equals("3D Embroidery")) || (this.myorderdata.getSet(item).getLogo(j).getDecoration().equals("Direct To Garment"))) {
              String uniqueid = getUniqueID(this.myorderdata.getSet(item).getLogo(j).getDecoration(), capflattype, String.valueOf(this.myorderdata.getSet(item).getLogo(j).getStitchCount()), this.myorderdata.getSet(item).getLogo(j).getFilename());
              if (((Integer)logoamount.get(uniqueid)).intValue() >= 144) {
                if ((this.myorderdata.getSet(item).getLogo(j).getDecoration().equals("Flat Embroidery")) || (this.myorderdata.getSet(item).getLogo(j).getDecoration().equals("3D Embroidery"))) {
                  int samplestitchcount = this.myorderdata.getSet(item).getLogo(j).getStitchCount().intValue();
                  int samplestitchmultiple = (int)Math.floor(samplestitchcount / 10000.0D) + 1;
                  logodiscount += samplestitchmultiple * 8.0D;
                  if (this.myorderdata.getSet(item).getLogo(j).getDecoration().equals("3D Embroidery")) {
                    logodiscount += 2.0D;
                  }
                } else if (this.myorderdata.getSet(item).getLogo(j).getDecoration().equals("Direct To Garment")) {
                  String thelogolocation = this.myorderdata.getSet(item).getLogo(j).getLogoLocation();
                  if ((thelogolocation.equals("1")) || (thelogolocation.equals("2")) || (thelogolocation.equals("3")) || (thelogolocation.equals("4")) || (thelogolocation.equals("18")) || (thelogolocation.equals("19"))) {
                    logodiscount += 7.95D;
                  } else if (thelogolocation.equals("42")) {
                    logodiscount += 8.95D;
                  } else if ((thelogolocation.startsWith("A")) && (!thelogolocation.startsWith("AP"))) {
                    logodiscount += 7.95D;
                  }
                }
                

                logoamount.put(uniqueid, Integer.valueOf(fixlogoquantity(((Integer)logoamount.get(uniqueid)).intValue())));
              }
              else {
                allreqmeet = false;
              }
            } else {
              onlyemb = false;
            }
          }
          if (logodiscount > 0.0D) {
            logodiscount += ((ListItemPrice)samplehatcost.get(k)).getPrice();
            if ((allreqmeet) && (onlyemb)) {
              if (this.mydiscounts.containsKey(Integer.valueOf(item))) {
                ((DomesticSampleDiscountCounter)this.mydiscounts.get(Integer.valueOf(item))).addFreeSample(logodiscount);
              } else {
                DomesticSampleDiscountCounter myitem = new DomesticSampleDiscountCounter();
                myitem.addFreeSample(logodiscount);
                this.mydiscounts.put(Integer.valueOf(item), myitem);
              }
            }
            else if (this.mydiscounts.containsKey(Integer.valueOf(item))) {
              ((DomesticSampleDiscountCounter)this.mydiscounts.get(Integer.valueOf(item))).addDiscountSample(logodiscount);
            } else {
              DomesticSampleDiscountCounter myitem = new DomesticSampleDiscountCounter();
              myitem.addDiscountSample(logodiscount);
              this.mydiscounts.put(Integer.valueOf(item), myitem);
            }
          }
          else {
            sampleamount = this.myorderdata.getSet(item).getItem(0).getProductSampleTotalDone().intValue();
          }
        }
      }
    }
  }
  

  private String getUniqueID(String decoration, String capflattype, String stitchcount, String filename)
  {
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
  
  public TreeMap<Integer, DomesticSampleDiscountCounter> getDiscounts()
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
