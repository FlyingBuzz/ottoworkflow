package com.ottocap.NewWorkFlow.server.PriceCalculation;

import com.extjs.gxt.ui.client.core.FastMap;
import com.ottocap.NewWorkFlow.client.StyleInformationData.StyleType;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderData;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataAddress;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataCustomerInformation;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataDiscount;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataItem;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataLogo;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataLogoColorway;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataSet;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataVendorInformation;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataVendors;
import com.ottocap.NewWorkFlow.server.PriceCalculation.Helper.DomesticFreeProductSampleCalculator;
import com.ottocap.NewWorkFlow.server.PriceCalculation.Helper.DomesticFreeSwatchCalculator;
import com.ottocap.NewWorkFlow.server.PriceCalculation.Helper.DomesticSampleDiscountCounter;
import com.ottocap.NewWorkFlow.server.PriceCalculation.Helper.ItemHelper;
import com.ottocap.NewWorkFlow.server.PriceCalculation.Shipping.WorkFlowShippingCalculation;
import com.ottocap.NewWorkFlow.server.SQLTable;
import com.ottocap.NewWorkFlow.server.SharedData;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;

public class DomesticPriceCalculation
{
  private double customertotalprice = 0.0D;
  private double customertotaldecorationprice = 0.0D;
  private double customertotalcapprice = 0.0D;
  private double digitizingtotalcost = 0.0D;
  private double sampletotalcost = 0.0D;
  private double ordertotalcost = 0.0D;
  private double[] orderitemcost;
  private ArrayList<String[]> pricetableholder = new ArrayList();
  public static int ScreenPrint = 1;
  public static int HeatTransfer = 2;
  public static int DirectToGarment = 3;
  public static int Embroidery = 4;
  public static int Patch = 5;
  public static int CADPrint = 6;
  private SQLTable sqltable;
  private ExtendOrderData myorderdata;
  private String output = "";
  
  public DomesticPriceCalculation(SQLTable sqltable, ExtendOrderData myorderdata) throws Exception {
    this.sqltable = sqltable;
    this.myorderdata = myorderdata;
  }
  
  public void calculateOrderPrice(int ordertype) throws Exception {
    ItemHelper itemhelper = new ItemHelper(this.myorderdata);
    TreeMap<String, Integer> totallogocolorchange = itemhelper.getCloneOfTotalLogoColorChange();
    
    int vendornumber = 0;
    if (ordertype == CADPrint) {
      vendornumber = this.myorderdata.getVendorInformation().getCADPrintVendor().getVendor().intValue();
    } else if (ordertype == ScreenPrint) {
      vendornumber = this.myorderdata.getVendorInformation().getScreenPrintVendor().getVendor().intValue();
    } else if (ordertype == Embroidery) {
      vendornumber = this.myorderdata.getVendorInformation().getEmbroideryVendor().getVendor().intValue();
    } else if (ordertype == DirectToGarment) {
      vendornumber = this.myorderdata.getVendorInformation().getDirectToGarmentVendor().getVendor().intValue();
    } else if (ordertype == HeatTransfer) {
      vendornumber = this.myorderdata.getVendorInformation().getHeatTransferVendor().getVendor().intValue();
    } else if (ordertype == Patch) {
      vendornumber = this.myorderdata.getVendorInformation().getPatchVendor().getVendor().intValue();
    }
    
    HiddenEcho("Vendor Number: " + vendornumber + "<BR>");
    
    double[] itemcost = new double[this.myorderdata.getSetCount()];
    for (int i = 0; i < this.myorderdata.getSetCount(); i++)
    {
      HiddenEcho("Set: " + (i + 1) + "<BR>");
      
      int quantity = this.myorderdata.getSet(i).getItem(0).getQuantity().intValue();
      String capflattype = this.myorderdata.getSet(i).getItem(0).getGoodsType();
      
      HiddenEcho("Quantity: " + quantity + "<BR>");
      HiddenEcho("Capflattype: " + capflattype + "<BR>");
      
      itemcost[i] = 0.0D;
      HiddenEcho("Current Item Cost: " + itemcost[i] + "<BR>");
      
      if (vendornumber == this.myorderdata.getVendorInformation().getEmbroideryVendor().getVendor().intValue()) {
        if ((this.myorderdata.getSet(i).getItem(0).getPolybag()) && (
          (vendornumber == 3) || (vendornumber == 4) || (vendornumber == 7) || (vendornumber == 9))) {
          itemcost[i] += quantity * 0.03D;
          HiddenEcho("Current Item Cost after ploybag: " + itemcost[i] + "<BR>");
        }
        

        if (this.myorderdata.getSet(i).getItem(0).getTagging()) {
          if ((vendornumber == 3) || (vendornumber == 4) || (vendornumber == 7) || (vendornumber == 9)) {
            itemcost[i] += quantity * 0.03D;
            HiddenEcho("Current Item Cost after tagging: " + itemcost[i] + "<BR>");
          } else if (vendornumber == 2) {
            itemcost[i] += quantity * 0.05D;
            HiddenEcho("Current Item Cost after tagging: " + itemcost[i] + "<BR>");
          }
        }
        
        if (this.myorderdata.getSet(i).getItem(0).getStickers()) {
          if ((vendornumber == 3) || (vendornumber == 4) || (vendornumber == 7) || (vendornumber == 9)) {
            itemcost[i] += quantity * 0.03D;
            HiddenEcho("Current Item Cost after stickers: " + itemcost[i] + "<BR>");
          } else if (vendornumber == 2) {
            itemcost[i] += quantity * 0.05D;
            HiddenEcho("Current Item Cost after stickers: " + itemcost[i] + "<BR>");
          }
        }
        

        if (this.myorderdata.getSet(i).getItem(0).getSewingPatches()) {
          if (vendornumber == 7) {
            itemcost[i] += quantity * 0.75D;
            HiddenEcho("Current Item Cost after sewingpatches: " + itemcost[i] + "<BR>");
          }
          if (vendornumber == 2) {
            if (quantity < 72) {
              itemcost[i] += quantity * 1.5D;
            } else if (quantity < 500) {
              itemcost[i] += quantity * 1.25D;
            } else {
              itemcost[i] += quantity * 1.15D;
            }
            
            HiddenEcho("Current Item Cost after sewingpatches: " + itemcost[i] + "<BR>");
          }
        }
      }
      
      for (int j = 0; j < this.myorderdata.getSet(i).getLogoCount(); j++) {
        HiddenEcho("Current Item Logo: " + (j + 1) + "<BR>");
        String filename = this.myorderdata.getSet(i).getLogo(j).getFilename();
        String stitchcountname = String.valueOf(this.myorderdata.getSet(i).getLogo(j).getStitchCount());
        int stitchcount = this.myorderdata.getSet(i).getLogo(j).getStitchCount() == null ? 0 : this.myorderdata.getSet(i).getLogo(j).getStitchCount().intValue();
        HiddenEcho("Current Logo filename: " + filename + "<BR>");
        HiddenEcho("Current Logo stitchcount: " + stitchcount + "<BR>");
        
        String uniquefilename = itemhelper.getUniqueCapTypeLogo(capflattype, filename, stitchcountname);
        String uniquecolorchange = itemhelper.getUniqueLogo(filename, stitchcountname);
        
        if (this.myorderdata.getSet(i).getLogo(j).getDecoration().equals("Heat Transfer")) {
          HiddenEcho("Current Logo have heat transfer<BR>");
          int numofcolor = this.myorderdata.getSet(i).getLogo(j).getNumberOfColor() == null ? 0 : this.myorderdata.getSet(i).getLogo(j).getNumberOfColor().intValue();
          if (vendornumber == this.myorderdata.getVendorInformation().getHeatTransferVendor().getVendor().intValue()) {
            if (vendornumber == 5) {
              if (this.myorderdata.getSet(i).getLogo(j).getFilmCharge()) {
                itemcost[i] += numofcolor * 15.0D;
                HiddenEcho("Current Item Cost after filmcharge: " + itemcost[i] + "<BR>");
              }
              
              if ((this.myorderdata.getSet(i).getLogo(j).getColorChangeAmount() != null) && (this.myorderdata.getSet(i).getLogo(j).getColorChangeAmount().intValue() > 0) && 
                (((Integer)totallogocolorchange.get(uniquecolorchange)).intValue() > 0)) {
                itemcost[i] += 5.0D;
                totallogocolorchange.put(uniquecolorchange, Integer.valueOf(((Integer)totallogocolorchange.get(uniquecolorchange)).intValue() - 1));
                HiddenEcho("Current Item Cost after colorchange: " + itemcost[i] + "<BR>");
              }
              

              if ((this.myorderdata.getSet(i).getItem(0).getStyleType() == StyleInformationData.StyleType.DOMESTIC) || (this.myorderdata.getSet(i).getItem(0).getStyleType() == StyleInformationData.StyleType.DOMESTIC_SPECIAL)) {
                if (((this.myorderdata.getSet(i).getLogo(j).getLogoSizeWidth() == null) || (this.myorderdata.getSet(i).getLogo(j).getLogoSizeWidth().doubleValue() <= 4.0D)) && ((this.myorderdata.getSet(i).getLogo(j).getLogoSizeHeight() == null) || (this.myorderdata.getSet(i).getLogo(j).getLogoSizeHeight().doubleValue() <= 2.5D)))
                {
                  if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 72) {
                    itemcost[i] += ((numofcolor - 1) * 0.1D + 0.5D) * quantity;
                  } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 144) {
                    itemcost[i] += ((numofcolor - 1) * 0.05D + 0.45D) * quantity;
                  } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 288) {
                    itemcost[i] += ((numofcolor - 1) * 0.05D + 0.4D) * quantity;
                  } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 500) {
                    itemcost[i] += ((numofcolor - 1) * 0.05D + 0.35D) * quantity;
                  } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 1000) {
                    itemcost[i] += ((numofcolor - 1) * 0.05D + 0.32D) * quantity;
                  } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 3000) {
                    itemcost[i] += ((numofcolor - 1) * 0.05D + 0.29D) * quantity;
                  } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 5000) {
                    itemcost[i] += ((numofcolor - 1) * 0.05D + 0.26D) * quantity;
                  } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 10000) {
                    itemcost[i] += ((numofcolor - 1) * 0.05D + 0.23D) * quantity;
                  } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 50000) {
                    itemcost[i] += ((numofcolor - 1) * 0.05D + 0.2D) * quantity;
                  } else {
                    itemcost[i] += ((numofcolor - 1) * 0.05D + 0.17D) * quantity;
                  }
                }
                else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 72) {
                  itemcost[i] += ((numofcolor - 1) * 0.2D + 0.7D) * quantity;
                } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 144) {
                  itemcost[i] += ((numofcolor - 1) * 0.15D + 0.6D) * quantity;
                } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 288) {
                  itemcost[i] += ((numofcolor - 1) * 0.1D + 0.5D) * quantity;
                } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 500) {
                  itemcost[i] += ((numofcolor - 1) * 0.1D + 0.4D) * quantity;
                } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 1000) {
                  itemcost[i] += ((numofcolor - 1) * 0.1D + 0.35D) * quantity;
                } else {
                  itemcost[i] += ((numofcolor - 1) * 0.1D + 0.32D) * quantity;
                }
                

              }
              else if ((this.myorderdata.getSet(i).getItem(0).getStyleType() == StyleInformationData.StyleType.DOMESTIC_SHIRTS) || (this.myorderdata.getSet(i).getItem(0).getStyleType() == StyleInformationData.StyleType.DOMESTIC_SWEATSHIRTS) || (this.myorderdata.getSet(i).getItem(0).getStyleType() == StyleInformationData.StyleType.DOMESTIC_TOTEBAGS) || (this.myorderdata.getSet(i).getItem(0).getStyleType() == StyleInformationData.StyleType.DOMESTIC_APRONS)) {
                if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 72) {
                  if (numofcolor < 2) {
                    itemcost[i] += 0.95D * quantity;
                  } else {
                    itemcost[i] += ((numofcolor - 1) * 0.6D + 0.9D) * quantity;
                  }
                } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 144) {
                  itemcost[i] += ((numofcolor - 1) * 0.4D + 0.8D) * quantity;
                } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 288) {
                  itemcost[i] += ((numofcolor - 1) * 0.4D + 0.7D) * quantity;
                } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 500) {
                  itemcost[i] += ((numofcolor - 1) * 0.35D + 0.55D) * quantity;
                } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 1000) {
                  itemcost[i] += ((numofcolor - 1) * 0.3D + 0.45D) * quantity;
                } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 3000) {
                  itemcost[i] += ((numofcolor - 1) * 0.25D + 0.35D) * quantity;
                } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 5000) {
                  itemcost[i] += ((numofcolor - 1) * 0.25D + 0.35D) * quantity;
                } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 10000) {
                  itemcost[i] += ((numofcolor - 1) * 0.25D + 0.35D) * quantity;
                } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 50000) {
                  itemcost[i] += ((numofcolor - 1) * 0.25D + 0.35D) * quantity;
                } else {
                  itemcost[i] += ((numofcolor - 1) * 0.25D + 0.35D) * quantity;
                }
              }
              HiddenEcho("Current Item Cost after quantity: " + itemcost[i] + "<BR>");
            }
            else if (vendornumber == 10) {
              itemcost[i] += 0.75D * quantity;
              HiddenEcho("Current Item Cost after extra: " + itemcost[i] + "<BR>");
            }
          }
        } else if (this.myorderdata.getSet(i).getLogo(j).getDecoration().equals("CAD Print")) {
          HiddenEcho("Current Logo have cad print<BR>");
          if ((vendornumber == this.myorderdata.getVendorInformation().getCADPrintVendor().getVendor().intValue()) && 
            (vendornumber == 13))
          {
            itemcost[i] += this.myorderdata.getSet(i).getLogo(j).getVendorLogoPrice().doubleValue() * quantity;
            HiddenEcho("Current Item Cost after quantity: " + itemcost[i] + "<BR>");

          }
          

        }
        else if (this.myorderdata.getSet(i).getLogo(j).getDecoration().equals("Screen Print")) {
          HiddenEcho("Current Logo have screen print<BR>");
          int numofcolor = this.myorderdata.getSet(i).getLogo(j).getNumberOfColor() == null ? 0 : this.myorderdata.getSet(i).getLogo(j).getNumberOfColor().intValue();
          if ((vendornumber == this.myorderdata.getVendorInformation().getScreenPrintVendor().getVendor().intValue()) && 
            (vendornumber == 12))
          {
            if ((this.myorderdata.getSet(i).getItem(0).getStyleType() == StyleInformationData.StyleType.DOMESTIC_TOTEBAGS) || (this.myorderdata.getSet(i).getItem(0).getStyleType() == StyleInformationData.StyleType.DOMESTIC_APRONS))
            {
              if (this.myorderdata.getSet(i).getLogo(j).getPMSMatch()) {
                itemcost[i] += numofcolor * 10.0D;
                HiddenEcho("Current Item Cost after pms match: " + itemcost[i] + "<BR>");
              }
              
              if (this.myorderdata.getSet(i).getLogo(j).getFilmCharge())
              {
                itemcost[i] += numofcolor * 5.0D;
                HiddenEcho("Current Item Cost after film charge: " + itemcost[i] + "<BR>");
              }
              
              if ((this.myorderdata.getSet(i).getLogo(j).getFilmSetupCharge() != null) && (this.myorderdata.getSet(i).getLogo(j).getFilmSetupCharge().doubleValue() > 0.0D)) {
                itemcost[i] += numofcolor * this.myorderdata.getSet(i).getLogo(j).getFilmSetupCharge().doubleValue();
                HiddenEcho("Current Item Cost after screen setup charge: " + itemcost[i] + "<BR>");
              }
              
              if ((this.myorderdata.getSet(i).getLogo(j).getColorChangeAmount() != null) && (this.myorderdata.getSet(i).getLogo(j).getColorChangeAmount().intValue() > 0) && 
                (((Integer)totallogocolorchange.get(uniquecolorchange)).intValue() > 0)) {
                itemcost[i] += 10.0D;
                totallogocolorchange.put(uniquecolorchange, Integer.valueOf(((Integer)totallogocolorchange.get(uniquecolorchange)).intValue() - 1));
                HiddenEcho("Current Item Cost after color change: " + itemcost[i] + "<BR>");
              }
              

              for (int k = 0; k < this.myorderdata.getSet(i).getLogo(j).getColorwayCount(); k++) {
                if ((this.myorderdata.getSet(i).getLogo(j).getColorway(k).getInkType() != null) && (!this.myorderdata.getSet(i).getLogo(j).getColorway(k).getInkType().equals("")) && (!this.myorderdata.getSet(i).getLogo(j).getColorway(k).getInkType().equals("Plastisol Ink")) && (!this.myorderdata.getSet(i).getLogo(j).getColorway(k).getInkType().equals("Waterbased Ink")))
                {
                  if (this.myorderdata.getSet(i).getLogo(j).getColorway(k).getInkType().equals("Discharge Ink")) {
                    itemcost[i] += 0.25D * quantity;
                  } else if ((this.myorderdata.getSet(i).getLogo(j).getColorway(k).getInkType().equals("Metallic Ink")) || (this.myorderdata.getSet(i).getLogo(j).getColorway(k).getInkType().equals("Puff Ink")) || (this.myorderdata.getSet(i).getLogo(j).getColorway(k).getInkType().equals("Crystalina Ink"))) {
                    itemcost[i] += 0.15D * quantity;
                  } else if ((this.myorderdata.getSet(i).getLogo(j).getColorway(k).getInkType().equals("Glitter Ink")) || (this.myorderdata.getSet(i).getLogo(j).getColorway(k).getInkType().equals("Reflective Ink")) || (this.myorderdata.getSet(i).getLogo(j).getColorway(k).getInkType().equals("High Density Ink")))
                    itemcost[i] += 0.3D * quantity;
                }
                HiddenEcho("Current Item Cost after ink cost: " + itemcost[i] + "<BR>");
              }
              
              int flashchargetimes = 0;
              
              if (this.myorderdata.getSet(i).getLogo(j).getFlashCharge()) {
                flashchargetimes = numofcolor;
              } else {
                for (int k = 0; k < this.myorderdata.getSet(i).getLogo(j).getColorwayCount(); k++) {
                  if (this.myorderdata.getSet(i).getLogo(j).getFlashCharge()) {
                    flashchargetimes++;
                  }
                }
              }
              if (flashchargetimes > 0) {
                if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 72) {
                  itemcost[i] += 1.0D * numofcolor * quantity;
                } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 144) {
                  itemcost[i] += 0.6D * numofcolor * quantity;
                } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 250) {
                  itemcost[i] += 0.25D * numofcolor * quantity;
                } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 500) {
                  itemcost[i] += 0.25D * numofcolor * quantity;
                } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 1000) {
                  itemcost[i] += 0.2D * numofcolor * quantity;
                } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 1500) {
                  itemcost[i] += 0.15D * numofcolor * quantity;
                } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 3000) {
                  itemcost[i] += 0.13D * numofcolor * quantity;
                } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 6000) {
                  itemcost[i] += 0.12D * numofcolor * quantity;
                } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 10000) {
                  itemcost[i] += 0.12D * numofcolor * quantity;
                } else {
                  itemcost[i] += 0.11D * numofcolor * quantity;
                }
                HiddenEcho("Current Item Cost after flash charge: " + itemcost[i] + "<BR>");
              }
              





































              itemcost[i] += this.myorderdata.getSet(i).getLogo(j).getVendorLogoPrice().doubleValue() * quantity;
              HiddenEcho("Current Item Cost after color and quantity: " + itemcost[i] + "<BR>");
            }
            else
            {
              if (this.myorderdata.getSet(i).getLogo(j).getPMSMatch()) {
                itemcost[i] += numofcolor * 10.0D;
                HiddenEcho("Current Item Cost after pms match: " + itemcost[i] + "<BR>");
              }
              
              if (this.myorderdata.getSet(i).getLogo(j).getFilmCharge()) {
                double sizecheck = 0.0D;
                if (this.myorderdata.getSet(i).getLogo(j).getLogoSizeWidth() != null) {
                  sizecheck = this.myorderdata.getSet(i).getLogo(j).getLogoSizeWidth().doubleValue();
                }
                double sizecheck2 = 0.0D;
                if (this.myorderdata.getSet(i).getLogo(j).getLogoSizeHeight() != null) {
                  sizecheck2 = this.myorderdata.getSet(i).getLogo(j).getLogoSizeHeight().doubleValue();
                }
                
                if (sizecheck > sizecheck2) {
                  double temp = sizecheck;
                  sizecheck = sizecheck2;
                  sizecheck2 = temp;
                }
                if ((sizecheck2 <= 11.0D) && (sizecheck <= 8.5D)) {
                  itemcost[i] += numofcolor * 10.0D;
                  HiddenEcho("Current Item Cost after film charge: " + itemcost[i] + "<BR>");
                } else if ((sizecheck2 <= 12.0D) && (sizecheck <= 11.0D)) {
                  itemcost[i] += numofcolor * 15.0D;
                  HiddenEcho("Current Item Cost after film charge: " + itemcost[i] + "<BR>");
                } else {
                  itemcost[i] += numofcolor * 20.0D;
                  HiddenEcho("Current Item Cost after film charge: " + itemcost[i] + "<BR>");
                }
              }
              if ((this.myorderdata.getSet(i).getLogo(j).getFilmSetupCharge() != null) && (this.myorderdata.getSet(i).getLogo(j).getFilmSetupCharge().doubleValue() > 0.0D)) {
                itemcost[i] += numofcolor * this.myorderdata.getSet(i).getLogo(j).getFilmSetupCharge().doubleValue();
                HiddenEcho("Current Item Cost after screen setup charge: " + itemcost[i] + "<BR>");
              }
              
              if ((this.myorderdata.getSet(i).getLogo(j).getColorChangeAmount() != null) && (this.myorderdata.getSet(i).getLogo(j).getColorChangeAmount().intValue() > 0) && 
                (((Integer)totallogocolorchange.get(uniquecolorchange)).intValue() > 0)) {
                itemcost[i] += 10.0D;
                totallogocolorchange.put(uniquecolorchange, Integer.valueOf(((Integer)totallogocolorchange.get(uniquecolorchange)).intValue() - 1));
                HiddenEcho("Current Item Cost after color change: " + itemcost[i] + "<BR>");
              }
              

              for (int k = 0; k < this.myorderdata.getSet(i).getLogo(j).getColorwayCount(); k++) {
                if ((this.myorderdata.getSet(i).getLogo(j).getColorway(k).getInkType() != null) && (!this.myorderdata.getSet(i).getLogo(j).getColorway(k).getInkType().equals("")) && (!this.myorderdata.getSet(i).getLogo(j).getColorway(k).getInkType().equals("Plastisol Ink")) && (!this.myorderdata.getSet(i).getLogo(j).getColorway(k).getInkType().equals("Waterbased Ink")))
                {
                  if (this.myorderdata.getSet(i).getLogo(j).getColorway(k).getInkType().equals("Discharge Ink")) {
                    itemcost[i] += 0.25D * quantity;
                  } else if ((this.myorderdata.getSet(i).getLogo(j).getColorway(k).getInkType().equals("Metallic Ink")) || (this.myorderdata.getSet(i).getLogo(j).getColorway(k).getInkType().equals("Puff Ink")) || (this.myorderdata.getSet(i).getLogo(j).getColorway(k).getInkType().equals("Crystalina Ink"))) {
                    itemcost[i] += 0.15D * quantity;
                  } else if ((this.myorderdata.getSet(i).getLogo(j).getColorway(k).getInkType().equals("Glitter Ink")) || (this.myorderdata.getSet(i).getLogo(j).getColorway(k).getInkType().equals("Reflective Ink")) || (this.myorderdata.getSet(i).getLogo(j).getColorway(k).getInkType().equals("High Density Ink")))
                    itemcost[i] += 0.3D * quantity;
                }
                HiddenEcho("Current Item Cost after ink cost: " + itemcost[i] + "<BR>");
              }
              
              int flashchargetimes = 0;
              
              if (this.myorderdata.getSet(i).getLogo(j).getFlashCharge()) {
                flashchargetimes = numofcolor;
              } else {
                for (int k = 0; k < this.myorderdata.getSet(i).getLogo(j).getColorwayCount(); k++) {
                  if (this.myorderdata.getSet(i).getLogo(j).getFlashCharge()) {
                    flashchargetimes++;
                  }
                }
              }
              if (flashchargetimes > 0) {
                if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 48) {
                  itemcost[i] += 0.25D * numofcolor * quantity;
                } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 84) {
                  itemcost[i] += 0.25D * numofcolor * quantity;
                } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 144) {
                  itemcost[i] += 0.25D * numofcolor * quantity;
                } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 288) {
                  itemcost[i] += 0.2D * numofcolor * quantity;
                } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 600) {
                  itemcost[i] += 0.1D * numofcolor * quantity;
                } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 1200) {
                  itemcost[i] += 0.1D * numofcolor * quantity;
                } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 3000) {
                  itemcost[i] += 0.1D * numofcolor * quantity;
                } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 6000) {
                  itemcost[i] += 0.1D * numofcolor * quantity;
                } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 12000) {
                  itemcost[i] += 0.1D * numofcolor * quantity;
                } else {
                  itemcost[i] += 0.1D * numofcolor * quantity;
                }
                HiddenEcho("Current Item Cost after flash charge: " + itemcost[i] + "<BR>");
              }
              

































































              itemcost[i] += this.myorderdata.getSet(i).getLogo(j).getVendorLogoPrice().doubleValue() * quantity;
              HiddenEcho("Current Item Cost after color and quantity: " + itemcost[i] + "<BR>");
              

              if (itemcost[i] < (numofcolor - 1) * 15.0D + 25.0D) {
                itemcost[i] = ((numofcolor - 1) * 15.0D + 25.0D);
              }
              HiddenEcho("Current Item Cost after minimum cost: " + itemcost[i] + "<BR>");
            }
          }
        }
        else if (this.myorderdata.getSet(i).getLogo(j).getDecoration().equals("Four Color Screen Print")) {
          HiddenEcho("Current Logo have four color screen print<BR>");
          if ((vendornumber == this.myorderdata.getVendorInformation().getScreenPrintVendor().getVendor().intValue()) && 
            (vendornumber == 12)) {
            if (this.myorderdata.getSet(i).getLogo(j).getFilmCharge()) {
              itemcost[i] += 200.0D;
            }
            if ((this.myorderdata.getSet(i).getLogo(j).getFilmSetupCharge() != null) && (this.myorderdata.getSet(i).getLogo(j).getFilmSetupCharge().doubleValue() > 0.0D)) {
              itemcost[i] += 4.0D * this.myorderdata.getSet(i).getLogo(j).getFilmSetupCharge().doubleValue();
            }
            
            if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 48) {
              itemcost[i] += 4.6D * quantity;
            } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 84) {
              itemcost[i] += 2.6D * quantity;
            } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 144) {
              itemcost[i] += 2.0D * quantity;
            } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 288) {
              itemcost[i] += 1.25D * quantity;
            } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 600) {
              itemcost[i] += 0.85D * quantity;
            } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 1200) {
              itemcost[i] += 0.7D * quantity;
            } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 3000) {
              itemcost[i] += 0.54D * quantity;
            } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 6000) {
              itemcost[i] += 0.48D * quantity;
            } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 12000) {
              itemcost[i] += 0.4D * quantity;
            } else {
              itemcost[i] += 0.38D * quantity;
            }
            HiddenEcho("Current Item Cost after color and quantity: " + itemcost[i] + "<BR>");
            

            if (itemcost[i] < 120.0D) {
              itemcost[i] = 120.0D;
            }
            HiddenEcho("Current Item Cost after minimum cost: " + itemcost[i] + "<BR>");
          }
          
        }
        else if ((this.myorderdata.getSet(i).getLogo(j).getDecoration().equals("3D Embroidery")) || (this.myorderdata.getSet(i).getLogo(j).getDecoration().equals("Flat Embroidery"))) {
          HiddenEcho("Current Logo have embroidery<BR>");
          int numofcolor = this.myorderdata.getSet(i).getLogo(j).getNumberOfColor() == null ? 0 : this.myorderdata.getSet(i).getLogo(j).getNumberOfColor().intValue();
          if (vendornumber == this.myorderdata.getVendorInformation().getEmbroideryVendor().getVendor().intValue()) {
            if (vendornumber == 1) {
              if (((Integer)totallogocolorchange.get(uniquecolorchange)).intValue() > 1) {
                itemcost[i] += 3.5D;
                totallogocolorchange.put(uniquecolorchange, Integer.valueOf(((Integer)totallogocolorchange.get(uniquecolorchange)).intValue() - 1));
              }
              

              double stitchmultiple = stitchcount / 1000.0D;
              





              if (stitchcount < 4000) {
                stitchmultiple = 4.0D;
              }
              
              double metallicthreadcost = 0.0D;
              if (this.myorderdata.getSet(i).getLogo(j).getMetallicThread()) {
                metallicthreadcost = 0.03D;
              }
              double neonthreadcost = 0.0D;
              




              double extrathreadcost = 0.0D;
              if (this.myorderdata.getSet(i).getLogo(j).getDecoration().equals("3D Embroidery")) {
                extrathreadcost += 0.75D;
              }
              if (this.myorderdata.getSet(i).getItem(0).getCrownConstruction().equals("wristbands")) {
                extrathreadcost += 0.15D;
              }
              double stitchcost = 0.0D;
              




























              if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 72) {
                stitchcost += stitchmultiple * (0.21D + metallicthreadcost + neonthreadcost);
              } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 144) {
                stitchcost += stitchmultiple * (0.18D + metallicthreadcost + neonthreadcost);
              } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 1000) {
                stitchcost += stitchmultiple * (0.16D + metallicthreadcost + neonthreadcost);
              } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 5000) {
                stitchcost += stitchmultiple * (0.15D + metallicthreadcost + neonthreadcost);
              } else {
                stitchcost += stitchmultiple * (0.14D + metallicthreadcost + neonthreadcost);
              }
              
              stitchcost += extrathreadcost;
              
              if (numofcolor > 8) {
                int extracolorcount = numofcolor - 8;
                double halfstitchcost = stitchcost / 2.0D;
                for (int extracolor = 0; extracolor < extracolorcount; extracolor++) {
                  stitchcost += halfstitchcost;
                }
              }
              
              itemcost[i] += stitchcost * quantity;
              HiddenEcho("Current item cost: " + itemcost[i] + " += " + stitchcost * quantity + " = " + stitchcost + " * " + quantity + " <BR>");
            }
            else if (vendornumber == 2) {
              if (((Integer)totallogocolorchange.get(uniquecolorchange)).intValue() > 0) {
                itemcost[i] += 4.0D;
                totallogocolorchange.put(uniquecolorchange, Integer.valueOf(((Integer)totallogocolorchange.get(uniquecolorchange)).intValue() - 1));
              }
              

              double stitchmultiple = stitchcount / 1000.0D;
              if (stitchcount < 4000) {
                stitchmultiple = 4.0D;
              }
              double metallicthreadcost = 0.0D;
              if (this.myorderdata.getSet(i).getLogo(j).getMetallicThread()) {
                metallicthreadcost = 0.03D;
              }
              double neonthreadcost = 0.0D;
              if (this.myorderdata.getSet(i).getLogo(j).getNeonThread()) {
                neonthreadcost = 0.03D;
              }
              double extrathreadcost = 0.0D;
              if (this.myorderdata.getSet(i).getLogo(j).getDecoration().equals("3D Embroidery")) {
                extrathreadcost += 1.15D;
              }
              
              if (this.myorderdata.getSet(i).getItem(0).getCrownConstruction().equals("wristbands")) {
                extrathreadcost += 0.05D;
              } else if ((this.myorderdata.getSet(i).getItem(0).getCrownConstruction().equals("cowbow hat")) || (this.myorderdata.getSet(i).getItem(0).getCrownConstruction().equals("fedor hat"))) {
                extrathreadcost += 0.2D;
              }
              
              double stitchcost = 0.0D;
              
              if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 48) {
                stitchcost += 5.5D;
              } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 72) {
                stitchcost += stitchmultiple * (0.23D + metallicthreadcost + neonthreadcost);
              } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 144) {
                stitchcost += stitchmultiple * (0.19D + metallicthreadcost + neonthreadcost);
              } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 1000) {
                stitchcost += stitchmultiple * (0.18D + metallicthreadcost + neonthreadcost);
              } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 3000) {
                stitchcost += stitchmultiple * (0.17D + metallicthreadcost + neonthreadcost);
              } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 5000) {
                stitchcost += stitchmultiple * (0.16D + metallicthreadcost + neonthreadcost);
              } else {
                stitchcost += stitchmultiple * (0.15D + metallicthreadcost + neonthreadcost);
              }
              stitchcost += extrathreadcost;
              








              if (numofcolor > 8) {
                int extracolorcount = numofcolor - 8;
                for (int extracolor = 0; extracolor < extracolorcount; extracolor++) {
                  double moreperstitch = stitchcost * 0.35D;
                  stitchcost += moreperstitch;
                }
              }
              
              itemcost[i] += stitchcost * quantity;
              HiddenEcho("Current item cost: " + itemcost[i] + " += " + stitchcost * quantity + " = " + stitchcost + " * " + quantity + " <BR>");
            }
            else if (vendornumber == 11) {
              if (((Integer)totallogocolorchange.get(uniquecolorchange)).intValue() > 3) {
                itemcost[i] += 3.5D;
                totallogocolorchange.put(uniquecolorchange, Integer.valueOf(((Integer)totallogocolorchange.get(uniquecolorchange)).intValue() - 1));
              }
              
              double stitchmultiple = stitchcount / 1000.0D;
              if (stitchcount < 4000) {
                stitchmultiple = 4.0D;
              }
              
              double stitchcost = 0.0D;
              
              if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 72) {
                stitchcost += stitchmultiple * 0.21D;
              } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 144) {
                stitchcost += stitchmultiple * 0.18D;
              } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 1000) {
                stitchcost += stitchmultiple * 0.16D;
              } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 5000) {
                stitchcost += stitchmultiple * 0.15D;
              } else {
                stitchcost += stitchmultiple * 0.14D;
              }
              
              if (numofcolor > 7) {
                int extracolorcount = numofcolor - 7;
                double halfstitchcost = stitchcost / 2.0D;
                for (int extracolor = 0; extracolor < extracolorcount; extracolor++) {
                  stitchcost += halfstitchcost;
                }
              }
              
              if (this.myorderdata.getSet(i).getLogo(j).getMetallicThread()) {
                stitchcost *= 1.1D;
              }
              if (this.myorderdata.getSet(i).getLogo(j).getNeonThread()) {
                stitchcost *= 1.1D;
              }
              
              if (this.myorderdata.getSet(i).getLogo(j).getDecoration().equals("3D Embroidery")) {
                double countpercentcost = stitchcost * 0.3D;
                if (countpercentcost > 0.75D) {
                  countpercentcost = 0.75D;
                }
                stitchcount = (int)(stitchcount + countpercentcost);
              }
              if (this.myorderdata.getSet(i).getItem(0).getCrownConstruction().equals("wristbands")) {
                stitchcost += 0.15D;
              }
              
              itemcost[i] += stitchcost * quantity;
              HiddenEcho("Current item cost: " + itemcost[i] + " += " + stitchcost * quantity + " = " + stitchcost + " * " + quantity + " <BR>");
            }
            else if ((vendornumber == 3) || (vendornumber == 4) || (vendornumber == 7) || (vendornumber == 9))
            {
              if (((Integer)totallogocolorchange.get(uniquecolorchange)).intValue() > 2) {
                itemcost[i] += 3.5D;
                totallogocolorchange.put(uniquecolorchange, Integer.valueOf(((Integer)totallogocolorchange.get(uniquecolorchange)).intValue() - 1));
              }
              

              double stitchmultiple = stitchcount / 1000.0D;
              if (stitchcount < 4000) {
                stitchmultiple = 4.0D;
              }
              double metallicthreadcost = 0.0D;
              if (this.myorderdata.getSet(i).getLogo(j).getMetallicThread()) {
                metallicthreadcost = 0.03D;
              }
              double neonthreadcost = 0.0D;
              if (this.myorderdata.getSet(i).getLogo(j).getNeonThread()) {
                neonthreadcost = 0.03D;
              }
              double stitchcost = 0.0D;
              
              if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 72) {
                stitchcost += stitchmultiple * (0.2D + metallicthreadcost + neonthreadcost);
              } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 144) {
                stitchcost += stitchmultiple * (0.18D + metallicthreadcost + neonthreadcost);
              } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 1000) {
                stitchcost += stitchmultiple * (0.15D + metallicthreadcost + neonthreadcost);
              } else {
                stitchcost += stitchmultiple * (0.13D + metallicthreadcost + neonthreadcost);
              }
              if (this.myorderdata.getSet(i).getLogo(j).getDecoration().equals("3D Embroidery")) {
                if (stitchcost * 0.5D > 0.75D) {
                  stitchcost += 0.75D;
                } else {
                  stitchcost += stitchcost * 0.5D;
                }
              }
              if (this.myorderdata.getSet(i).getItem(0).getCrownConstruction().equals("wristbands")) {
                stitchcost += 0.15D;
              }
              
              itemcost[i] += stitchcost * quantity;
              HiddenEcho("Current item cost: " + itemcost[i] + " += " + stitchcost * quantity + " = " + stitchcost + " * " + quantity + " <BR>");
            }
            
          }
        }
        else if (this.myorderdata.getSet(i).getLogo(j).getDecoration().equals("Direct To Garment")) {
          HiddenEcho("Current Logo have direct to garment<BR>");
          if ((vendornumber == this.myorderdata.getVendorInformation().getDirectToGarmentVendor().getVendor().intValue()) && (
            (vendornumber == 1) || (vendornumber == 9)))
          {
            double directtogarmentcost = 0.0D;
            
            String thelogolocation = this.myorderdata.getSet(i).getLogo(j).getLogoLocation().trim();
            












            if ((!thelogolocation.startsWith("A")) || (thelogolocation.startsWith("AP"))) {
              if ((thelogolocation.equals("1")) || (thelogolocation.equals("2")) || (thelogolocation.equals("3")) || (thelogolocation.equals("4")) || (vendornumber == 9)) {
                if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 24) {
                  directtogarmentcost = 5.0D;
                } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 48) {
                  directtogarmentcost = 2.95D;
                } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 72) {
                  directtogarmentcost = 2.7D;
                } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 144) {
                  directtogarmentcost = 2.55D;
                } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 288) {
                  directtogarmentcost = 2.5D;
                } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 576) {
                  directtogarmentcost = 2.3D;
                } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 1008) {
                  directtogarmentcost = 2.2D;
                } else {
                  directtogarmentcost = 2.15D;
                }
              } else if ((thelogolocation.equals("18")) || (thelogolocation.equals("19"))) {
                if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 24) {
                  directtogarmentcost = 4.5D;
                } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 48) {
                  directtogarmentcost = 2.7D;
                } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 72) {
                  directtogarmentcost = 2.4D;
                } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 144) {
                  directtogarmentcost = 2.3D;
                } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 288) {
                  directtogarmentcost = 2.2D;
                } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 576) {
                  directtogarmentcost = 2.1D;
                } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 1008) {
                  directtogarmentcost = 2.05D;
                } else {
                  directtogarmentcost = 2.0D;
                }
              } else if (thelogolocation.equals("42")) {
                if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 24) {
                  directtogarmentcost = 5.75D;
                } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 48) {
                  directtogarmentcost = 3.85D;
                } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 72) {
                  directtogarmentcost = 3.6D;
                } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 144) {
                  directtogarmentcost = 3.4D;
                } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 288) {
                  directtogarmentcost = 3.3D;
                } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 576) {
                  directtogarmentcost = 3.1D;
                } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 1008) {
                  directtogarmentcost = 3.0D;
                } else {
                  directtogarmentcost = 2.9D;
                }
              } else {
                throw new Exception("Direct To Garment Can't Have Selected Location");
              }
            }
            else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 24) {
              directtogarmentcost = 5.0D;
            } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 48) {
              directtogarmentcost = 3.25D;
            } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 72) {
              directtogarmentcost = 3.0D;
            } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 144) {
              directtogarmentcost = 2.85D;
            } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 288) {
              directtogarmentcost = 2.75D;
            } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 576) {
              directtogarmentcost = 2.6D;
            } else if (itemhelper.getTotalCapTypeLogo(uniquefilename) < 1008) {
              directtogarmentcost = 2.45D;
            } else {
              directtogarmentcost = 2.35D;
            }
            


            itemcost[i] += directtogarmentcost * quantity;
          }
          
        }
        else if ((this.myorderdata.getSet(i).getLogo(j).getVendorLogoPrice() != null) && (this.myorderdata.getSet(i).getLogo(j).getVendorLogoPrice().doubleValue() > 0.0D)) {
          HiddenEcho("Current Logo have custom vendor price<BR>");
          itemcost[i] += this.myorderdata.getSet(i).getLogo(j).getVendorLogoPrice().doubleValue() * quantity;
        }
      }
    }
    



    double processingcost = 0.0D;
    int totalvenitems = 0;
    
    for (int i = 0; i < this.myorderdata.getSetCount(); i++)
    {




      HiddenEcho("--- Set " + i + 1 + " ---<BR>");
      itemcost[i] = (Math.ceil((float)(itemcost[i] / this.myorderdata.getSet(i).getItem(0).getQuantity().intValue() * 100.0D)) / 100.0D);
      if (Double.isNaN(itemcost[i])) {
        itemcost[i] = 0.0D;
      }
      HiddenEcho("Item Cost after rounding: " + itemcost[i] + "<BR>");
      processingcost += itemcost[i] * this.myorderdata.getSet(i).getItem(0).getQuantity().intValue();
      totalvenitems += this.myorderdata.getSet(i).getItem(0).getQuantity().intValue();
      HiddenEcho("Item Processing cost: " + processingcost + "<BR>");
    }
    
    HiddenEcho("Final Processing cost: " + processingcost + "<BR>");
    setOrderTotalCost(processingcost);
    setOrderItemCost(itemcost);
    
    if (!this.myorderdata.getOrderStatus().equals("Order Completed"))
    {
      FastMap<Object> mykeys = new FastMap();
      mykeys.put("hiddenkey", this.myorderdata.getHiddenKey());
      
      if (ordertype == CADPrint) {
        mykeys.put("cadventotqty", Integer.valueOf(totalvenitems));
        mykeys.put("cadventotcost", Double.valueOf(processingcost));
      } else if (ordertype == ScreenPrint) {
        mykeys.put("screenventotqty", Integer.valueOf(totalvenitems));
        mykeys.put("screenventotcost", Double.valueOf(processingcost));
      } else if (ordertype == Embroidery) {
        mykeys.put("embventotqty", Integer.valueOf(totalvenitems));
        mykeys.put("embventotcost", Double.valueOf(processingcost));
      } else if (ordertype == DirectToGarment) {
        mykeys.put("dtgventotqty", Integer.valueOf(totalvenitems));
        mykeys.put("dtgventotcost", Double.valueOf(processingcost));
      } else if (ordertype == HeatTransfer) {
        mykeys.put("heatventotqty", Integer.valueOf(totalvenitems));
        mykeys.put("heatventotcost", Double.valueOf(processingcost));
      }
      
      FastMap<Object> myfilter = new FastMap();
      myfilter.put("hiddenkey", this.myorderdata.getHiddenKey());
      this.sqltable.insertUpdateRow("ordermain_sales_report", mykeys, myfilter);
    }
  }
  


  private void setOrderItemCost(double[] itemscost)
  {
    this.orderitemcost = itemscost;
  }
  
  public double getOrderItemCost(int i) {
    return this.orderitemcost[i];
  }
  
  private void setOrderTotalCost(double totalcost) {
    this.ordertotalcost = totalcost;
  }
  
  public double getOrderTotalCost() {
    return this.ordertotalcost;
  }
  
  public void calculateSamplePrice(int ordertype) throws Exception {
    double processingcost = 0.0D;
    
    int vendornumber = 0;
    if (ordertype == CADPrint) {
      vendornumber = this.myorderdata.getVendorInformation().getCADPrintVendor().getVendor().intValue();
    } else if (ordertype == ScreenPrint) {
      vendornumber = this.myorderdata.getVendorInformation().getScreenPrintVendor().getVendor().intValue();
    } else if (ordertype == HeatTransfer) {
      vendornumber = this.myorderdata.getVendorInformation().getHeatTransferVendor().getVendor().intValue();
    } else if (ordertype == Patch) {
      vendornumber = this.myorderdata.getVendorInformation().getPatchVendor().getVendor().intValue();
    } else if (ordertype == Embroidery) {
      vendornumber = this.myorderdata.getVendorInformation().getEmbroideryVendor().getVendor().intValue();
    } else if (ordertype == DirectToGarment) {
      vendornumber = this.myorderdata.getVendorInformation().getDirectToGarmentVendor().getVendor().intValue();
    }
    for (int i = 0; i < this.myorderdata.getSetCount(); i++) {
      int totalproductsamples = this.myorderdata.getSet(i).getItem(0).getProductSampleToDo() == null ? 0 : this.myorderdata.getSet(i).getItem(0).getProductSampleToDo().intValue();
      if ((vendornumber == 1) || (vendornumber == 3) || (vendornumber == 4) || (vendornumber == 7) || (vendornumber == 9)) {
        double totallogocost = 0.0D;
        for (int j = 0; j < this.myorderdata.getSet(i).getLogoCount(); j++) {
          if ((this.myorderdata.getSet(i).getLogo(j).getDecoration().equals("3D Embroidery")) || (this.myorderdata.getSet(i).getLogo(j).getDecoration().equals("Flat Embroidery"))) {
            if (vendornumber == this.myorderdata.getVendorInformation().getEmbroideryVendor().getVendor().intValue()) {
              int stitchcount = this.myorderdata.getSet(i).getLogo(j).getStitchCount() == null ? 0 : this.myorderdata.getSet(i).getLogo(j).getStitchCount().intValue();
              
              int stitchmultiple = (int)Math.floor(stitchcount / 10000.0D) + 1;
              totallogocost += stitchmultiple * 5.0D;
              if (this.myorderdata.getSet(i).getLogo(j).getDecoration().equals("3D Embroidery")) {
                totallogocost += 0.75D;
              }
              if (this.myorderdata.getSet(i).getItem(0).getCrownConstruction().equals("wristbands")) {
                totallogocost += 0.2D;
              }
            }
          }
          else if ((this.myorderdata.getSet(i).getLogo(j).getDecoration().equals("Direct To Garment")) && 
            (vendornumber == this.myorderdata.getVendorInformation().getDirectToGarmentVendor().getVendor().intValue()) && 
            (vendornumber == 1)) {
            if (SharedData.isFaya.booleanValue())
            {
              totallogocost += 5.0D;
            } else {
              String thelogolocation = this.myorderdata.getSet(i).getLogo(j).getLogoLocation().trim();
              if ((!thelogolocation.startsWith("A")) || (thelogolocation.startsWith("AP"))) {
                if ((thelogolocation.equals("1")) || (thelogolocation.equals("2")) || (thelogolocation.equals("3")) || (thelogolocation.equals("4")) || (vendornumber == 9)) {
                  totallogocost += 5.0D;
                } else if ((thelogolocation.equals("18")) || (thelogolocation.equals("19"))) {
                  totallogocost += 4.5D;
                } else if (thelogolocation.equals("42")) {
                  totallogocost += 5.75D;
                } else {
                  throw new Exception("Direct To Garment Can't Have Selected Location");
                }
              } else {
                totallogocost += 5.0D;
              }
            }
          }
        }
        


        processingcost += totalproductsamples * totallogocost;
      } else if (vendornumber == 2) {
        double totallogocost = 0.0D;
        for (int j = 0; j < this.myorderdata.getSet(i).getLogoCount(); j++) {
          if (((this.myorderdata.getSet(i).getLogo(j).getDecoration().equals("3D Embroidery")) || (this.myorderdata.getSet(i).getLogo(j).getDecoration().equals("Flat Embroidery"))) && 
            (vendornumber == this.myorderdata.getVendorInformation().getEmbroideryVendor().getVendor().intValue())) {
            int stitchcount = this.myorderdata.getSet(i).getLogo(j).getStitchCount() == null ? 0 : this.myorderdata.getSet(i).getLogo(j).getStitchCount().intValue();
            
            int stitchmultiple = (int)Math.floor(stitchcount / 10000.0D) + 1;
            totallogocost += stitchmultiple * 6.0D;
            if (this.myorderdata.getSet(i).getLogo(j).getDecoration().equals("3D Embroidery")) {
              totallogocost += 1.15D;
            }
            if (this.myorderdata.getSet(i).getItem(0).getCrownConstruction().equals("wristbands")) {
              totallogocost += 0.05D;
            } else if ((this.myorderdata.getSet(i).getItem(0).getCrownConstruction().equals("cowbow hat")) || (this.myorderdata.getSet(i).getItem(0).getCrownConstruction().equals("fedor hat"))) {
              totallogocost += 0.2D;
            }
            if (this.myorderdata.getSet(i).getLogo(j).getMetallicThread()) {
              totallogocost += 0.12D;
            }
          }
        }
        

        processingcost += totalproductsamples * totallogocost;
      } else if (vendornumber == 5) {
        double totallogocost = 0.0D;
        for (int j = 0; j < this.myorderdata.getSet(i).getLogoCount(); j++)
        {
          if (this.myorderdata.getSet(i).getLogo(j).getDecoration().equals("Heat Transfer")) {
            int numberofheattransferlogoscolors = this.myorderdata.getSet(i).getLogo(j).getNumberOfColor() == null ? 0 : this.myorderdata.getSet(i).getLogo(j).getNumberOfColor().intValue();
            totallogocost += 15.0D * numberofheattransferlogoscolors;
          } else if (this.myorderdata.getSet(i).getLogo(j).getDecoration().equals("CAD Print")) {
            int numberofheattransferlogoscolors = this.myorderdata.getSet(i).getLogo(j).getNumberOfColor() == null ? 0 : this.myorderdata.getSet(i).getLogo(j).getNumberOfColor().intValue();
            totallogocost += 15.0D * numberofheattransferlogoscolors;
          } else if (this.myorderdata.getSet(i).getLogo(j).getDecoration().equals("Screen Print")) {
            int numberofheattransferlogoscolors = this.myorderdata.getSet(i).getLogo(j).getNumberOfColor() == null ? 0 : this.myorderdata.getSet(i).getLogo(j).getNumberOfColor().intValue();
            totallogocost += 15.0D * numberofheattransferlogoscolors;
          }
        }
        
        processingcost += totalproductsamples * totallogocost;
      }
      
      for (int j = 0; j < this.myorderdata.getSet(i).getLogoCount(); j++) {
        int stitchcount = this.myorderdata.getSet(i).getLogo(j).getStitchCount() == null ? 0 : this.myorderdata.getSet(i).getLogo(j).getStitchCount().intValue();
        int totalswatchs = this.myorderdata.getSet(i).getLogo(j).getSwatchToDo() == null ? 0 : this.myorderdata.getSet(i).getLogo(j).getSwatchToDo().intValue();
        if ((vendornumber == 1) || (vendornumber == 3) || (vendornumber == 4) || (vendornumber == 7) || (vendornumber == 9)) {
          if ((this.myorderdata.getVendorInformation().getEmbroideryVendor().getVendor() != null) && (vendornumber == this.myorderdata.getVendorInformation().getEmbroideryVendor().getVendor().intValue()) && (
            (this.myorderdata.getSet(i).getLogo(j).getDecoration().equals("3D Embroidery")) || (this.myorderdata.getSet(i).getLogo(j).getDecoration().equals("Flat Embroidery")))) {
            double logocost = 5.0D;
            if (this.myorderdata.getSet(i).getLogo(j).getDecoration().equals("3D Embroidery")) {
              logocost += 0.75D;
            }
            int stitchmultiple = (int)Math.floor(stitchcount / 10000.0D) + 1;
            processingcost += stitchmultiple * totalswatchs * logocost;
          }
          
          if ((this.myorderdata.getVendorInformation().getDirectToGarmentVendor().getVendor() != null) && (vendornumber == this.myorderdata.getVendorInformation().getDirectToGarmentVendor().getVendor().intValue()) && 
            (this.myorderdata.getSet(i).getLogo(j).getDecoration().equals("Direct To Garment"))) {
            double logocost = 5.0D;
            int stitchmultiple = (int)Math.floor(stitchcount / 10000.0D) + 1;
            processingcost += stitchmultiple * totalswatchs * logocost;
          }
        }
        else if (vendornumber == 2) {
          if ((this.myorderdata.getVendorInformation().getEmbroideryVendor().getVendor() != null) && (vendornumber == this.myorderdata.getVendorInformation().getEmbroideryVendor().getVendor().intValue()) && (
            (this.myorderdata.getSet(i).getLogo(j).getDecoration().equals("3D Embroidery")) || (this.myorderdata.getSet(i).getLogo(j).getDecoration().equals("Flat Embroidery")))) {
            double logocost = 6.0D;
            if (this.myorderdata.getSet(i).getLogo(j).getDecoration().equals("3D Embroidery")) {
              logocost += 1.15D;
            }
            int stitchmultiple = (int)Math.floor(stitchcount / 10000.0D) + 1;
            processingcost += stitchmultiple * totalswatchs * logocost;
          }
        }
        else if ((vendornumber == 5) && (
          (this.myorderdata.getSet(i).getLogo(j).getDecoration().equals("Heat Transfer")) || (this.myorderdata.getSet(i).getLogo(j).getDecoration().equals("CAD Print")) || (this.myorderdata.getSet(i).getLogo(j).getDecoration().equals("Screen Print")))) {
          processingcost += totalswatchs * 10.0D;
        }
      }
    }
    


    setSampleTotalCost(processingcost);
  }
  
  private void setSampleTotalCost(double totalcost) {
    this.sampletotalcost = totalcost;
  }
  
  public double getSampleTotalCost() {
    return this.sampletotalcost;
  }
  
  public void calculateDigitizingPrice() throws Exception {
    ItemHelper itemhelper2 = new ItemHelper(this.myorderdata);
    
    double processingcost = 0.0D;
    
    int vendornumber = this.myorderdata.getVendorInformation().getDigitizerVendor().getVendor().intValue();
    boolean samevendor = false;
    
    if (this.myorderdata.getVendorInformation().getDigitizerVendor().getVendor() == this.myorderdata.getVendorInformation().getEmbroideryVendor().getVendor()) {
      samevendor = true;
    }
    
    for (int i = 0; i < this.myorderdata.getSetCount(); i++) {
      for (int j = 0; j < this.myorderdata.getSet(i).getLogoCount(); j++) {
        String filename = this.myorderdata.getSet(i).getLogo(j).getFilename();
        String stitchcountname = String.valueOf(this.myorderdata.getSet(i).getLogo(j).getStitchCount());
        int stitchcount = this.myorderdata.getSet(i).getLogo(j).getStitchCount() == null ? 0 : this.myorderdata.getSet(i).getLogo(j).getStitchCount().intValue();
        String uniquefilename = itemhelper2.getUniqueLogo(filename, stitchcountname);
        
        if (this.myorderdata.getSet(i).getLogo(j).getDigitizing()) {
          if (vendornumber == 1) {
            int stitchmultiple = (int)Math.ceil(stitchcount / 1000.0D);
            double currentcost = stitchmultiple * 5.0D;
            if (currentcost < 32.0D) {
              currentcost = 32.0D;
            }
            processingcost += currentcost;
          } else if (vendornumber == 2) {
            int stitchmultiple = (int)Math.ceil(stitchcount / 1000.0D);
            double currentcost = stitchmultiple * 7.0D;
            if (currentcost < 35.0D) {
              currentcost = 35.0D;
            }
            processingcost += currentcost;
          } else if (vendornumber == 11) {
            int stitchmultiple = (int)Math.ceil(stitchcount / 1000.0D);
            double currentcost = stitchmultiple * 6.0D;
            if (currentcost < 24.0D) {
              currentcost = 24.0D;
            }
            processingcost += currentcost;
          } else if ((vendornumber == 3) || (vendornumber == 4) || (vendornumber == 7) || (vendornumber == 9)) {
            if ((itemhelper2.getTotalLogo(uniquefilename) > 144) && (samevendor) && (vendornumber == 4)) {
              processingcost += 0.0D;
            } else if ((itemhelper2.getTotalLogo(uniquefilename) > 144) && (samevendor) && (vendornumber == 9)) {
              processingcost += 0.0D;
            } else if ((itemhelper2.getTotalLogo(uniquefilename) > 300) && (samevendor)) {
              processingcost += 0.0D;
            } else {
              double currentcost = 0.0D;
              int stitchmultiple = (int)Math.ceil(stitchcount / 1000.0D);
              if ((vendornumber == 1) || (vendornumber == 7)) {
                currentcost = stitchmultiple * 5.0D;
              } else {
                currentcost = stitchmultiple * 8.0D;
              }
              if (vendornumber == 7) {
                if (currentcost < 25.0D) {
                  currentcost = 25.0D;
                }
              } else if (vendornumber != 1)
              {

                if (currentcost < 32.0D) {
                  currentcost = 32.0D;
                }
              }
              
              processingcost += currentcost;
            }
          } else if ((vendornumber == 6) || (vendornumber == 8)) {
            processingcost = 0.0D;
          }
        }
        if ((this.myorderdata.getSet(i).getLogo(j).getTapeEdit()) && ((vendornumber == 2) || (vendornumber == 3) || (vendornumber == 4) || (vendornumber == 7) || (vendornumber == 9))) {
          processingcost += 25.0D;
        } else if ((this.myorderdata.getSet(i).getLogo(j).getTapeEdit()) && ((vendornumber == 1) || (vendornumber == 11))) {
          processingcost += 15.0D;
        }
      }
    }
    
    setDigitizingTotalCost(processingcost);
  }
  
  private void setDigitizingTotalCost(double totalcost) {
    this.digitizingtotalcost = totalcost;
  }
  
  public double getDigitizingTotalCost() {
    return this.digitizingtotalcost;
  }
  
  public void calculateCustomerPrice(boolean calculateonlineshipping) throws Exception {
    ItemHelper itemhelper3 = new ItemHelper(this.myorderdata);
    
    String pricelevelstring = "";
    boolean sampleispricelevel = false;
    double pricelevelmorediscount = 0.0D;
    int pricelevel = 1;
    boolean cheaperdigitizingmethod = false;
    TreeSet<String> cadtreeset = new TreeSet();
    
    if (this.sqltable.makeTable("SELECT `value` FROM `settings` WHERE `name` = 'cheaperdigitizingmethod'").booleanValue()) {
      if (Integer.valueOf((String)this.sqltable.getValue()).intValue() == 1) {
        cheaperdigitizingmethod = true;
      } else {
        cheaperdigitizingmethod = false;
      }
    }
    
    if (this.sqltable.makeTable("SELECT pricelevel FROM `eclipse_customer_info` WHERE `eclipseaccount` = '" + this.myorderdata.getCustomerInformation().getEclipseAccountNumber() + "'").booleanValue()) {
      pricelevelstring = ((String)this.sqltable.getValue()).toLowerCase();
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
    
    double totalprice = 0.0D;
    int higheststitchcount = 0;
    int higheststitchcountitem = 0;
    int higheststitchcountlogo = 0;
    int loweststitchcount = 0;
    int loweststitchcountitem = 0;
    int loweststitchcountlogo = 0;
    double totalcapprice = 0.0D;
    int totalitems = 0;
    int alltotalitems = 0;
    

    for (int i = 0; i < this.myorderdata.getSetCount(); i++) {
      int quantity = this.myorderdata.getSet(i).getItem(0).getQuantity() != null ? this.myorderdata.getSet(i).getItem(0).getQuantity().intValue() : 0;
      


      if ((!this.myorderdata.getSet(i).getItem(0).getStyleNumber().equals("nonotto")) && (!this.myorderdata.getSet(i).getItem(0).getStyleNumber().equals("nonottoflat"))) {
        totalitems += quantity;
      }
      alltotalitems += quantity;
    }
    
    for (int i = 0; i < this.myorderdata.getSetCount(); i++)
    {
      if (SharedData.isFaya.booleanValue())
      {
        for (int j = 0; j < this.myorderdata.getSet(i).getLogoCount(); j++)
        {
          String stitchcountname = String.valueOf(this.myorderdata.getSet(i).getLogo(j).getStitchCount());
          int stitchcount = this.myorderdata.getSet(i).getLogo(j).getStitchCount() == null ? 0 : this.myorderdata.getSet(i).getLogo(j).getStitchCount().intValue();
          String filename = this.myorderdata.getSet(i).getLogo(j).getFilename();
          String digitizinguniquefilename = itemhelper3.getUniqueLogo(filename, stitchcountname);
          
          int samefilenamequantity = itemhelper3.getTotalLogo(digitizinguniquefilename);
          



          if ((this.myorderdata.getSet(i).getLogo(j).getDigitizing()) && (samefilenamequantity >= 144)) {
            if (stitchcount > higheststitchcount) {
              higheststitchcount = stitchcount;
              higheststitchcountitem = i;
              higheststitchcountlogo = j;
            }
            if ((stitchcount > 0) && (stitchcount <= 5000)) {
              loweststitchcount = stitchcount;
              loweststitchcountitem = i;
              loweststitchcountlogo = j;
            }
            
          }
        }
      } else {
        for (int j = 0; j < this.myorderdata.getSet(i).getLogoCount(); j++)
        {
          String stitchcountname = String.valueOf(this.myorderdata.getSet(i).getLogo(j).getStitchCount());
          int stitchcount = this.myorderdata.getSet(i).getLogo(j).getStitchCount() == null ? 0 : this.myorderdata.getSet(i).getLogo(j).getStitchCount().intValue();
          String filename = this.myorderdata.getSet(i).getLogo(j).getFilename();
          String digitizinguniquefilename = itemhelper3.getUniqueLogo(filename, stitchcountname);
          
          int samefilenamequantity = itemhelper3.getTotalLogo(digitizinguniquefilename);
          



          if ((this.myorderdata.getSet(i).getLogo(j).getDigitizing()) && (samefilenamequantity >= 144)) {
            if ((stitchcount > higheststitchcount) && (!this.myorderdata.getSet(i).getItem(0).getStyleNumber().equals("nonotto")) && (!this.myorderdata.getSet(i).getItem(0).getStyleNumber().equals("nonottoflat"))) {
              higheststitchcount = stitchcount;
              higheststitchcountitem = i;
              higheststitchcountlogo = j;
            }
            if ((stitchcount > 0) && (stitchcount <= 5000) && (!this.myorderdata.getSet(i).getItem(0).getStyleNumber().equals("nonotto")) && (!this.myorderdata.getSet(i).getItem(0).getStyleNumber().equals("nonottoflat"))) {
              loweststitchcount = stitchcount;
              loweststitchcountitem = i;
              loweststitchcountlogo = j;
            }
          }
        }
      }
      

      if ((higheststitchcount < 10000) && (loweststitchcount > 0)) {
        higheststitchcount = loweststitchcount;
        higheststitchcountitem = loweststitchcountitem;
        higheststitchcountlogo = loweststitchcountlogo;
      }
    }
    


    DomesticFreeProductSampleCalculator myfreesamples = new DomesticFreeProductSampleCalculator(this.myorderdata, this.sqltable, itemhelper3);
    TreeMap<Integer, DomesticSampleDiscountCounter> myproductsamplediscount = myfreesamples.getDiscounts();
    

    DomesticFreeSwatchCalculator mynewfreeswatch = new DomesticFreeSwatchCalculator(this.myorderdata);
    TreeMap<String, DomesticSampleDiscountCounter> mynewfreeswatchlist = mynewfreeswatch.getDiscounts();
    
    for (int i = 0; i < this.myorderdata.getSetCount(); i++) {
      double polybagprice = 0.3D;
      double taggingprice = 0.1D;
      double stickersprice = 0.1D;
      double sewingpatchsprice = 1.75D;
      double heatpresspatchsprice = 0.75D;
      double removeinnerprintedlabelprice = 0.4D;
      double removeinnerwovenlabelprice = 0.6D;
      double addinnerprintedlabelprice = 0.4D;
      double addinnerwovenlabelprice = 0.6D;
      double personalizationprice = 8.0D;
      double price1 = 0.0D;
      double price2 = 0.0D;
      double price3 = 0.0D;
      double price4 = 0.0D;
      double priceofstyle = 0.0D;
      double lasttotalprice = totalprice;
      
      int quantity = this.myorderdata.getSet(i).getItem(0).getQuantity() != null ? this.myorderdata.getSet(i).getItem(0).getQuantity().intValue() : 0;
      
      String specialbasestyle = "";
      
      if (SharedData.isFaya.booleanValue()) {
        personalizationprice = 8.75D;
        polybagprice = 0.4D;
        taggingprice = 0.15D;
      }
      
      if ((!this.myorderdata.getSet(i).getItem(0).getStyleNumber().equals("nonotto")) && (!this.myorderdata.getSet(i).getItem(0).getStyleNumber().equals("nonottoflat")))
      {
        if (SharedData.isFaya.booleanValue())
        {
          int currentstylequantity = ((Integer)itemhelper3.getCloneOfTotalStyleItem().get(this.myorderdata.getSet(i).getItem(0).getSameStyle())).intValue();
          
          if ((this.myorderdata.getSet(i).getItem(0).getStyleType() == StyleInformationData.StyleType.DOMESTIC) || (this.myorderdata.getSet(i).getItem(0).getStyleType() == StyleInformationData.StyleType.DOMESTIC_SPECIAL) || (this.myorderdata.getSet(i).getItem(0).getStyleType() == StyleInformationData.StyleType.DOMESTIC_TOTEBAGS) || (this.myorderdata.getSet(i).getItem(0).getStyleType() == StyleInformationData.StyleType.DOMESTIC_APRONS)) {
            if (!this.sqltable.makeTable("SELECT `price` FROM `faya_price_table` WHERE `code` = '" + this.myorderdata.getSet(i).getItem(0).getStyleNumber() + "' LIMIT 1").booleanValue()) {
              throw new Exception("Style: " + this.myorderdata.getSet(i).getItem(0).getStyleNumber() + " Missing from faya price table");
            }
            String gottenvalue = (String)this.sqltable.getValue();
            String[] valuearray = gottenvalue.split(" ");
            
            double minstyleprice = Double.valueOf(valuearray[0]).doubleValue();
            
            for (int j = 1; j < valuearray.length; j += 2) {
              double dataquantity = Double.valueOf(valuearray[j]).doubleValue();
              if (currentstylequantity >= dataquantity) {
                minstyleprice = Double.valueOf(valuearray[(j + 1)]).doubleValue() / dataquantity;
              }
            }
            
            priceofstyle = minstyleprice;
          } else if ((this.myorderdata.getSet(i).getItem(0).getStyleType() == StyleInformationData.StyleType.DOMESTIC_SHIRTS) || (this.myorderdata.getSet(i).getItem(0).getStyleType() == StyleInformationData.StyleType.DOMESTIC_SWEATSHIRTS)) {
            if (!this.sqltable.makeTable("SELECT `price` FROM `faya_price_shirttable` WHERE `code` = '" + this.myorderdata.getSet(i).getItem(0).getStyleNumber() + "-" + this.myorderdata.getSet(i).getItem(0).getColorCode() + "-" + this.myorderdata.getSet(i).getItem(0).getSize() + "' LIMIT 1").booleanValue()) {
              throw new Exception("Misisng from `faya_price_shirttable` WHERE `code` = '" + this.myorderdata.getSet(i).getItem(0).getStyleNumber() + "-" + this.myorderdata.getSet(i).getItem(0).getColorCode() + "-" + this.myorderdata.getSet(i).getItem(0).getSize());
            }
            
            String gottenvalue = (String)this.sqltable.getValue();
            String[] valuearray = gottenvalue.split(" ");
            
            double minstyleprice = Double.valueOf(valuearray[0]).doubleValue();
            
            for (int j = 1; j < valuearray.length; j += 2) {
              double dataquantity = Double.valueOf(valuearray[j]).doubleValue();
              
              if (currentstylequantity >= dataquantity) {
                minstyleprice = Double.valueOf(valuearray[(j + 1)]).doubleValue();
              }
            }
            
            priceofstyle = minstyleprice;
          }
        } else {
          if (this.myorderdata.getSet(i).getItem(0).getStyleType() == StyleInformationData.StyleType.DOMESTIC) {
            this.sqltable.makeTable("SELECT * FROM `styles_domestic` WHERE `sku` = '" + this.myorderdata.getSet(i).getItem(0).getStyleNumber() + "' LIMIT 1");
            price1 = Double.valueOf((String)this.sqltable.getCell("price1")).doubleValue();
            price2 = Double.valueOf((String)this.sqltable.getCell("price2")).doubleValue();
            price3 = Double.valueOf((String)this.sqltable.getCell("price3")).doubleValue();
            price4 = Double.valueOf((String)this.sqltable.getCell("price4")).doubleValue();
          } else if (this.myorderdata.getSet(i).getItem(0).getStyleType() == StyleInformationData.StyleType.DOMESTIC_SPECIAL) {
            if (this.sqltable.makeTable("SELECT * FROM `styles_domestic_specials` WHERE `style` = '" + this.myorderdata.getSet(i).getItem(0).getStyleNumber() + "' AND `startingdate` <= '" + this.myorderdata.getOrderDate() + "' AND `endingdate` >= '" + this.myorderdata.getOrderDate() + "' LIMIT 1").booleanValue()) {
              price1 = ((Double)this.sqltable.getCell("price")).doubleValue();
              price2 = ((Double)this.sqltable.getCell("price")).doubleValue();
              price3 = ((Double)this.sqltable.getCell("price")).doubleValue();
              price4 = ((Double)this.sqltable.getCell("price")).doubleValue();
              specialbasestyle = (String)this.sqltable.getCell("basestyle");
            } else if (this.sqltable.makeTable("SELECT * FROM (SELECT * FROM (SELECT * FROM `styles_domestic_specials` ORDER BY `endingdate` DESC) AS T1 GROUP BY `style`) AS T2 WHERE `style` =  '" + this.myorderdata.getSet(i).getItem(0).getStyleNumber() + "' LIMIT 1").booleanValue()) {
              price1 = ((Double)this.sqltable.getCell("price")).doubleValue();
              price2 = ((Double)this.sqltable.getCell("price")).doubleValue();
              price3 = ((Double)this.sqltable.getCell("price")).doubleValue();
              price4 = ((Double)this.sqltable.getCell("price")).doubleValue();
              specialbasestyle = (String)this.sqltable.getCell("basestyle");
            } else {
              throw new Exception("Can't Find Special Pricing");
            }
          }
          else if (this.myorderdata.getSet(i).getItem(0).getStyleType() == StyleInformationData.StyleType.DOMESTIC_SHIRTS) {
            this.sqltable.makeTable("SELECT * FROM `domestic_price_shirts` WHERE `style` = '" + this.myorderdata.getSet(i).getItem(0).getStyleNumber() + "-" + this.myorderdata.getSet(i).getItem(0).getColorCode() + "-" + this.myorderdata.getSet(i).getItem(0).getSize() + "' LIMIT 1");
            price1 = ((Double)this.sqltable.getCell("price1")).doubleValue();
            price2 = ((Double)this.sqltable.getCell("price2")).doubleValue();
            price3 = ((Double)this.sqltable.getCell("price3")).doubleValue();
            price4 = ((Double)this.sqltable.getCell("price4")).doubleValue();
          } else if (this.myorderdata.getSet(i).getItem(0).getStyleType() == StyleInformationData.StyleType.DOMESTIC_SWEATSHIRTS) {
            this.sqltable.makeTable("SELECT * FROM `domestic_price_sweatshirts` WHERE `style` = '" + this.myorderdata.getSet(i).getItem(0).getStyleNumber() + "-" + this.myorderdata.getSet(i).getItem(0).getColorCode() + "-" + this.myorderdata.getSet(i).getItem(0).getSize() + "' LIMIT 1");
            price1 = ((Double)this.sqltable.getCell("price1")).doubleValue();
            price2 = ((Double)this.sqltable.getCell("price2")).doubleValue();
            price3 = ((Double)this.sqltable.getCell("price3")).doubleValue();
            price4 = ((Double)this.sqltable.getCell("price4")).doubleValue();
          } else if (this.myorderdata.getSet(i).getItem(0).getStyleType() == StyleInformationData.StyleType.DOMESTIC_LACKPARD) {
            this.sqltable.makeTable("SELECT * FROM `styles_lackpard` WHERE `Style` = '" + this.myorderdata.getSet(i).getItem(0).getStyleNumber() + "' LIMIT 1");
            price1 = Double.valueOf((String)this.sqltable.getCell("price1")).doubleValue();
            price2 = Double.valueOf((String)this.sqltable.getCell("price2")).doubleValue();
            price3 = Double.valueOf((String)this.sqltable.getCell("price3")).doubleValue();
            price4 = Double.valueOf((String)this.sqltable.getCell("price4")).doubleValue();
          } else if (this.myorderdata.getSet(i).getItem(0).getStyleType() == StyleInformationData.StyleType.DOMESTIC_TOTEBAGS) {
            this.sqltable.makeTable("SELECT * FROM `styles_domestic_totebags` WHERE `sku` = '" + this.myorderdata.getSet(i).getItem(0).getStyleNumber() + "' LIMIT 1");
            price1 = Double.valueOf((String)this.sqltable.getCell("price1")).doubleValue();
            price2 = Double.valueOf((String)this.sqltable.getCell("price2")).doubleValue();
            price3 = Double.valueOf((String)this.sqltable.getCell("price3")).doubleValue();
            price4 = Double.valueOf((String)this.sqltable.getCell("price4")).doubleValue();
          } else if (this.myorderdata.getSet(i).getItem(0).getStyleType() == StyleInformationData.StyleType.DOMESTIC_APRONS) {
            this.sqltable.makeTable("SELECT * FROM `styles_domestic_aprons` WHERE `sku` = '" + this.myorderdata.getSet(i).getItem(0).getStyleNumber() + "' LIMIT 1");
            price1 = Double.valueOf((String)this.sqltable.getCell("price1")).doubleValue();
            price2 = Double.valueOf((String)this.sqltable.getCell("price2")).doubleValue();
            price3 = Double.valueOf((String)this.sqltable.getCell("price3")).doubleValue();
            price4 = Double.valueOf((String)this.sqltable.getCell("price4")).doubleValue();
          }
          
          if ((totalitems < 144) && (pricelevel == 1)) {
            priceofstyle = price1 - pricelevelmorediscount;
          } else if ((totalitems < 576) && (pricelevel <= 2)) {
            priceofstyle = price2 - pricelevelmorediscount;
          } else if ((totalitems < 1440) && (pricelevel <= 3)) {
            priceofstyle = price3 - pricelevelmorediscount;
          } else {
            priceofstyle = price4 - pricelevelmorediscount;
          }
          
          if (specialbasestyle.equals("")) {
            specialbasestyle = this.myorderdata.getSet(i).getItem(0).getStyleNumber();
          }
          
          if ((this.myorderdata.getSet(i).getItem(0).getStyleType() != StyleInformationData.StyleType.DOMESTIC_SPECIAL) && 
            (this.sqltable.makeTable("SELECT `lowest` FROM `styles_domesticlowestprice` WHERE `style` = '" + specialbasestyle + "' LIMIT 1").booleanValue())) {
            String thelowest = (String)this.sqltable.getValue();
            thelowest = thelowest.replaceAll("\\$", "").trim();
            double loweststyleprice = Double.valueOf(thelowest).doubleValue();
            if (priceofstyle < loweststyleprice) {
              priceofstyle = loweststyleprice;
            }
            
          }
          
        }
        
      }
      else if ((this.myorderdata.getSet(i).getItem(0).getFOBMarkupPrice() != null) && (this.myorderdata.getSet(i).getItem(0).getFOBMarkupPrice().doubleValue() > 0.0D)) {
        priceofstyle = this.myorderdata.getSet(i).getItem(0).getFOBMarkupPrice().doubleValue();
      } else if ((this.myorderdata.getSet(i).getItem(0).getFOBPrice() != null) && (this.myorderdata.getSet(i).getItem(0).getFOBPrice().doubleValue() > 0.0D)) {
        priceofstyle = this.myorderdata.getSet(i).getItem(0).getFOBPrice().doubleValue();
        

        if (SharedData.isFaya.booleanValue()) {
          int currentstylequantity = ((Integer)itemhelper3.getCloneOfTotalStyleItem().get(this.myorderdata.getSet(i).getItem(0).getSameStyle())).intValue();
          if (currentstylequantity < 12) {
            priceofstyle *= 1.46D;
          } else if (currentstylequantity < 24) {
            priceofstyle *= 1.4D;
          } else if (currentstylequantity < 48) {
            priceofstyle *= 1.36D;
          } else if (currentstylequantity < 72) {
            priceofstyle *= 1.32D;
          } else if (currentstylequantity < 144) {
            priceofstyle *= 1.29D;
          } else if (currentstylequantity < 288) {
            priceofstyle *= 1.25D;
          } else if (currentstylequantity < 576) {
            priceofstyle *= 1.22D;
          } else if (currentstylequantity < 1000) {
            priceofstyle *= 1.2D;
          } else {
            priceofstyle *= 1.18D;
          }
        }
      }
      else {
        priceofstyle = 0.0D;
      }
      


      for (int j = 0; j < this.myorderdata.getDiscountItemCount(); j++) {
        if (this.myorderdata.getDiscountItem(j).getIntoItems().booleanValue()) {
          try {
            double amount = this.myorderdata.getDiscountItem(j).getAmount().doubleValue();
            priceofstyle += amount / alltotalitems;
            priceofstyle = Math.ceil((float)(priceofstyle * 100.0D)) / 100.0D;
            this.myorderdata.getSet(i).getItem(0).setSpecialItemPrice(true);
          } catch (Exception e) {
            throw new Exception("Discount Item Price Is Invalid");
          }
        }
      }
      
      String currentstyle = this.myorderdata.getSet(i).getItem(0).getStyleNumber();
      if (currentstyle.equals("nonotto")) {
        if ((this.myorderdata.getSet(i).getItem(0).getCustomStyleName() != null) && (!this.myorderdata.getSet(i).getItem(0).getCustomStyleName().equals(""))) {
          if (SharedData.isFaya.booleanValue()) {
            currentstyle = this.myorderdata.getSet(i).getItem(0).getCustomStyleName();
          } else {
            currentstyle = "Non Otto: " + this.myorderdata.getSet(i).getItem(0).getCustomStyleName();
          }
        }
        else if (SharedData.isFaya.booleanValue()) {
          currentstyle = "Non Faya";
        } else {
          currentstyle = "Non Otto";
        }
        
      }
      else if (currentstyle.equals("nonottoflat")) {
        if ((this.myorderdata.getSet(i).getItem(0).getCustomStyleName() != null) && (!this.myorderdata.getSet(i).getItem(0).getCustomStyleName().equals(""))) {
          if (SharedData.isFaya.booleanValue()) {
            currentstyle = this.myorderdata.getSet(i).getItem(0).getCustomStyleName();
          } else {
            currentstyle = "Non Otto flat: " + this.myorderdata.getSet(i).getItem(0).getCustomStyleName();
          }
        }
        else if (SharedData.isFaya.booleanValue()) {
          currentstyle = "Non Faya flat";
        } else {
          currentstyle = "Non Otto flat";
        }
        
      }
      else if ((this.myorderdata.getSet(i).getItem(0).getStyleType() == StyleInformationData.StyleType.DOMESTIC) || (this.myorderdata.getSet(i).getItem(0).getStyleType() == StyleInformationData.StyleType.DOMESTIC_LACKPARD) || (this.myorderdata.getSet(i).getItem(0).getStyleType() == StyleInformationData.StyleType.DOMESTIC_TOTEBAGS) || (this.myorderdata.getSet(i).getItem(0).getStyleType() == StyleInformationData.StyleType.DOMESTIC_APRONS) || (this.myorderdata.getSet(i).getItem(0).getStyleType() == StyleInformationData.StyleType.DOMESTIC_SPECIAL)) {
        currentstyle = currentstyle + "_" + this.myorderdata.getSet(i).getItem(0).getColorCode();
      } else if ((this.myorderdata.getSet(i).getItem(0).getStyleType() == StyleInformationData.StyleType.DOMESTIC_SHIRTS) || (this.myorderdata.getSet(i).getItem(0).getStyleType() == StyleInformationData.StyleType.DOMESTIC_SWEATSHIRTS)) {
        currentstyle = currentstyle + "_" + this.myorderdata.getSet(i).getItem(0).getColorCode() + "_" + this.myorderdata.getSet(i).getItem(0).getSize();
      }
      

      if (this.myorderdata.getSet(i).getItem(0).getProductDiscount() != null) {
        HiddenEcho("&nbsp;&nbsp;&nbsp;Product Discount " + (priceofstyle - this.myorderdata.getSet(i).getItem(0).getProductDiscount().doubleValue()) + " = " + priceofstyle + " - " + this.myorderdata.getSet(i).getItem(0).getProductDiscount() + "<br>");
        priceofstyle -= this.myorderdata.getSet(i).getItem(0).getProductDiscount().doubleValue();
        this.myorderdata.getSet(i).getItem(0).setSpecialItemPrice(true);
      }
      
      if (quantity > 0) {
        addTableRow("Style: " + currentstyle, "", String.valueOf(quantity), NumberFormat.getCurrencyInstance().format(priceofstyle) + (this.myorderdata.getSet(i).getItem(0).getSpecialItemPrice() ? "*" : ""), NumberFormat.getCurrencyInstance().format(priceofstyle * quantity));
      } else {
        addTableRow("Style: " + currentstyle, "", "", "", "");
      }
      totalprice += priceofstyle * quantity;
      totalcapprice += priceofstyle * quantity;
      
      if (quantity > 0) {
        if (this.myorderdata.getSet(i).getItem(0).getPolybag()) {
          addTableRow("Polybag", "", String.valueOf(quantity), NumberFormat.getCurrencyInstance().format(polybagprice), NumberFormat.getCurrencyInstance().format(polybagprice * quantity));
          totalprice += polybagprice * quantity;
        }
        
        if (this.myorderdata.getSet(i).getItem(0).getOakLeaves()) {
          double oakleavesprice = 1.0D;
          addTableRow("Oak Leaves Emblems", "", String.valueOf(quantity), NumberFormat.getCurrencyInstance().format(oakleavesprice), NumberFormat.getCurrencyInstance().format(oakleavesprice * quantity));
          totalprice += oakleavesprice * quantity;
        }
        



























        if (this.myorderdata.getSet(i).getItem(0).getTagging()) {
          addTableRow("Tagging", "", String.valueOf(quantity), NumberFormat.getCurrencyInstance().format(taggingprice), NumberFormat.getCurrencyInstance().format(taggingprice * quantity));
          totalprice += taggingprice * quantity;
        }
        
        if (this.myorderdata.getSet(i).getItem(0).getStickers()) {
          addTableRow("Stickers", "", String.valueOf(quantity), NumberFormat.getCurrencyInstance().format(stickersprice), NumberFormat.getCurrencyInstance().format(stickersprice * quantity));
          totalprice += stickersprice * quantity;
        }
        
        if (this.myorderdata.getSet(i).getItem(0).getSewingPatches()) {
          addTableRow("Sewing Patches", "", String.valueOf(quantity), NumberFormat.getCurrencyInstance().format(sewingpatchsprice), NumberFormat.getCurrencyInstance().format(sewingpatchsprice * quantity));
          totalprice += sewingpatchsprice * quantity;
        }
        
        if (this.myorderdata.getSet(i).getItem(0).getHeatPressPatches()) {
          addTableRow("Heat Press Patches", "", String.valueOf(quantity), NumberFormat.getCurrencyInstance().format(heatpresspatchsprice), NumberFormat.getCurrencyInstance().format(heatpresspatchsprice * quantity));
          totalprice += heatpresspatchsprice * quantity;
        }
        
        if (this.myorderdata.getSet(i).getItem(0).getRemoveInnerPrintedLabel()) {
          addTableRow("Remove Inner Printed Label", "", String.valueOf(quantity), NumberFormat.getCurrencyInstance().format(removeinnerprintedlabelprice), NumberFormat.getCurrencyInstance().format(removeinnerprintedlabelprice * quantity));
          totalprice += removeinnerprintedlabelprice * quantity;
        }
        
        if (this.myorderdata.getSet(i).getItem(0).getAddInnerPrintedLabel()) {
          addTableRow("Add Inner Printed Label", "", String.valueOf(quantity), NumberFormat.getCurrencyInstance().format(addinnerprintedlabelprice), NumberFormat.getCurrencyInstance().format(addinnerprintedlabelprice * quantity));
          totalprice += addinnerprintedlabelprice * quantity;
        }
        
        if (this.myorderdata.getSet(i).getItem(0).getRemoveInnerWovenLabel()) {
          addTableRow("Remove Inner Woven Label", "", String.valueOf(quantity), NumberFormat.getCurrencyInstance().format(removeinnerwovenlabelprice), NumberFormat.getCurrencyInstance().format(removeinnerwovenlabelprice * quantity));
          totalprice += removeinnerwovenlabelprice * quantity;
        }
        
        if (this.myorderdata.getSet(i).getItem(0).getAddInnerWovenLabel()) {
          addTableRow("Add Inner Woven Label", "", String.valueOf(quantity), NumberFormat.getCurrencyInstance().format(addinnerwovenlabelprice), NumberFormat.getCurrencyInstance().format(addinnerwovenlabelprice * quantity));
          totalprice += addinnerwovenlabelprice * quantity;
        }
        
        if ((this.myorderdata.getSet(i).getItem(0).getPersonalizationChanges() != null) && (this.myorderdata.getSet(i).getItem(0).getPersonalizationChanges().intValue() > 0)) {
          int personalizationchanges = this.myorderdata.getSet(i).getItem(0).getPersonalizationChanges().intValue();
          addTableRow("# of Personalization", "", String.valueOf(personalizationchanges), NumberFormat.getCurrencyInstance().format(personalizationprice), NumberFormat.getCurrencyInstance().format(personalizationprice * personalizationchanges));
          totalprice += personalizationprice * personalizationchanges;
        }
      }
      

      int totalsamplesdone = this.myorderdata.getSet(i).getItem(0).getProductSampleTotalDone() != null ? this.myorderdata.getSet(i).getItem(0).getProductSampleTotalDone().intValue() : 0;
      double productsampleprice = 8.0D;
      double productheatsampleprice = 20.0D;
      double productdirecttogarmentsampleprice = 7.95D;
      double productdirecttogarmentsampleprice2 = 8.95D;
      double productdirecttogarmentsampleprice3 = 7.95D;
      double sewingpatchesprice = 6.0D;
      
      if (SharedData.isFaya.booleanValue()) {
        productsampleprice = 9.0D;
      }
      
      if (totalsamplesdone > 0) {
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
        
        totalcapprice += sampleprice * totalsamplesdone;
        
        double realsampleprice = 0.0D;
        int numberofcadlogos = 0;
        int numberofheattransferlogoscolors = 0;
        int numberofstitchemblogos = 0;
        int numberofdirecttogarmentlogos = 0;
        int numberofdirecttogarmentlogos2 = 0;
        int numberofdirecttogarmentlogos3 = 0;
        int numberofsewingpatches = 0;
        double addedchargetd = 0.0D;
        
        for (int j = 0; j < this.myorderdata.getSet(i).getLogoCount(); j++)
        {
          if (this.myorderdata.getSet(i).getLogo(j).getDecoration().equals("CAD Print")) {
            numberofcadlogos++;
          } else if ((this.myorderdata.getSet(i).getLogo(j).getDecoration().equals("Heat Transfer")) || (this.myorderdata.getSet(i).getLogo(j).getDecoration().equals("Screen Print"))) {
            numberofheattransferlogoscolors += (this.myorderdata.getSet(i).getLogo(j).getNumberOfColor() == null ? 0 : this.myorderdata.getSet(i).getLogo(j).getNumberOfColor().intValue());
          } else if ((this.myorderdata.getSet(i).getLogo(j).getDecoration().equals("3D Embroidery")) || (this.myorderdata.getSet(i).getLogo(j).getDecoration().equals("Flat Embroidery"))) {
            int samplestitchcount = this.myorderdata.getSet(i).getLogo(j).getStitchCount() == null ? 0 : this.myorderdata.getSet(i).getLogo(j).getStitchCount().intValue();
            int samplestitchmultiple = (int)Math.floor(samplestitchcount / 10000.0D) + 1;
            numberofstitchemblogos = (int)(numberofstitchemblogos + samplestitchmultiple * productsampleprice);
          } else if (this.myorderdata.getSet(i).getLogo(j).getDecoration().equals("Direct To Garment")) {
            String thelogolocation = this.myorderdata.getSet(i).getLogo(j).getLogoLocation().trim();
            if ((thelogolocation.equals("1")) || (thelogolocation.equals("2")) || (thelogolocation.equals("3")) || (thelogolocation.equals("4")) || (thelogolocation.equals("18")) || (thelogolocation.equals("19"))) {
              numberofdirecttogarmentlogos++;
            } else if (thelogolocation.equals("42")) {
              numberofdirecttogarmentlogos2++;
            } else if ((thelogolocation.startsWith("A")) && (!thelogolocation.startsWith("AP"))) {
              numberofdirecttogarmentlogos3++;
            } else {
              throw new Exception("Direct To Garment Can't Have Selected Location");
            }
          } else if (this.myorderdata.getSet(i).getLogo(j).getDecoration().equals("Sewing Patch")) {
            numberofsewingpatches++;
          }
          
          if (this.myorderdata.getSet(i).getLogo(j).getDecoration().equals("3D Embroidery")) {
            addedchargetd += 2.0D;
          }
        }
        
        if (!SharedData.isFaya.booleanValue()) {
          realsampleprice += sampleprice + numberofstitchemblogos + numberofdirecttogarmentlogos * productdirecttogarmentsampleprice + numberofdirecttogarmentlogos2 * productdirecttogarmentsampleprice2 + numberofdirecttogarmentlogos3 * productdirecttogarmentsampleprice3 + productheatsampleprice * numberofheattransferlogoscolors + addedchargetd + numberofsewingpatches * sewingpatchesprice + numberofcadlogos * 40.0D;
          
          addTableRow("", "Pre-Production Sample", String.valueOf(totalsamplesdone), NumberFormat.getCurrencyInstance().format(realsampleprice), NumberFormat.getCurrencyInstance().format(realsampleprice * totalsamplesdone));
          totalprice += realsampleprice * totalsamplesdone;
        }
      }
      

      if (myproductsamplediscount.get(Integer.valueOf(i)) != null)
      {
        if (((DomesticSampleDiscountCounter)myproductsamplediscount.get(Integer.valueOf(i))).getFreeSampleAmount() > 0) {
          addTableRow("", "Sample Discount", String.valueOf(((DomesticSampleDiscountCounter)myproductsamplediscount.get(Integer.valueOf(i))).getFreeSampleAmount()), NumberFormat.getCurrencyInstance().format(((DomesticSampleDiscountCounter)myproductsamplediscount.get(Integer.valueOf(i))).getFreeSampleCost() * -1.0D), NumberFormat.getCurrencyInstance().format(((DomesticSampleDiscountCounter)myproductsamplediscount.get(Integer.valueOf(i))).getFreeSampleCost() * ((DomesticSampleDiscountCounter)myproductsamplediscount.get(Integer.valueOf(i))).getFreeSampleAmount() * -1.0D));
          totalprice += ((DomesticSampleDiscountCounter)myproductsamplediscount.get(Integer.valueOf(i))).getFreeSampleCost() * ((DomesticSampleDiscountCounter)myproductsamplediscount.get(Integer.valueOf(i))).getFreeSampleAmount() * -1.0D;
        }
        if (((DomesticSampleDiscountCounter)myproductsamplediscount.get(Integer.valueOf(i))).getDiscountSampleAmount() > 0) {
          addTableRow("", "Sample Discount", "", "", NumberFormat.getCurrencyInstance().format(((DomesticSampleDiscountCounter)myproductsamplediscount.get(Integer.valueOf(i))).getDiscountSampleCost() * -1.0D));
          totalprice += ((DomesticSampleDiscountCounter)myproductsamplediscount.get(Integer.valueOf(i))).getDiscountSampleCost() * -1.0D;
        }
      }
      
      boolean haddirecttogarment = false;
      
      for (int j = 0; j < this.myorderdata.getSet(i).getLogoCount(); j++) {
        String stitchcountname = String.valueOf(this.myorderdata.getSet(i).getLogo(j).getStitchCount());
        int stitchcount = this.myorderdata.getSet(i).getLogo(j).getStitchCount() == null ? 0 : this.myorderdata.getSet(i).getLogo(j).getStitchCount().intValue();
        String filename = this.myorderdata.getSet(i).getLogo(j).getFilename();
        double tapeeditprice = 35.0D;
        double metallicthreadprice = 0.06D;
        double neonthreadprice = 0.03D;
        double artworkchargehourprice = 50.0D;
        double colorchangeembprice = 5.0D;
        double colorchangeheatprice = 10.0D;
        String logolocation = this.myorderdata.getSet(i).getLogo(j).getLogoLocation();
        
        String capflattype = this.myorderdata.getSet(i).getItem(0).getGoodsType();
        String uniquefilename = itemhelper3.getUniqueCapTypeLocationLogo(capflattype, logolocation, filename, stitchcountname);
        
        addTableRow("", "Logo " + (j + 1) + " - " + this.myorderdata.getSet(i).getLogo(j).getFilename(), "", "", "");
        addTableRow("", this.myorderdata.getSet(i).getLogo(j).getLogoLocationName(), "", "", "");
        
        if ((this.myorderdata.getSet(i).getLogo(j).getDecoration().equals("3D Embroidery")) || (this.myorderdata.getSet(i).getLogo(j).getDecoration().equals("Flat Embroidery"))) {
          double stitchprice = 0.0D;
          
          int stitchmultiple = (int)Math.ceil(stitchcount / 1000.0D);
          if (stitchcount <= 5000) {
            stitchmultiple = 5;
          }
          double extracost = 0.0D;
          if (this.myorderdata.getSet(i).getLogo(j).getDecoration().equals("3D Embroidery")) {
            if (SharedData.isFaya.booleanValue()) {
              extracost += 2.75D;
            } else {
              extracost += 2.0D;
            }
          }
          if (this.myorderdata.getSet(i).getItem(0).getCrownConstruction().equals("wristbands")) {
            extracost += 0.1D;
          }
          if ((this.myorderdata.getSet(i).getItem(0).getCrownConstruction().equals("cowbow hat")) || (this.myorderdata.getSet(i).getItem(0).getCrownConstruction().equals("fedor hat"))) {
            extracost += 0.3D;
          }
          
          if (SharedData.isFaya.booleanValue())
          {

            if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 12) {
              stitchprice = stitchmultiple * 0.55D + 4.2D + extracost;
            } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 24) {
              stitchprice = stitchmultiple * 0.5D + 1.45D + extracost;
            } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 48) {
              stitchprice = stitchmultiple * 0.45D + 0.7D + extracost;
            } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 72) {
              stitchprice = stitchmultiple * 0.4D + 0.25D + extracost;
            } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 144) {
              stitchprice = stitchmultiple * 0.33D + 0.05D + extracost;
            } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 288) {
              stitchprice = stitchmultiple * 0.28D + 0.1D + extracost;
            } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 500) {
              stitchprice = stitchmultiple * 0.25D + 0.15D + extracost;
            } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 1000) {
              stitchprice = stitchmultiple * 0.22D + 0.2D + extracost;
            } else {
              stitchprice = stitchmultiple * 0.2D + 0.15D + extracost;
            }
          }
          else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 72) {
            stitchprice = stitchmultiple * 0.42D + extracost;
          } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 144) {
            stitchprice = stitchmultiple * 0.32D + extracost;
          } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 288) {
            stitchprice = stitchmultiple * 0.28D + extracost;
          } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 500) {
            stitchprice = stitchmultiple * 0.26D + extracost;
          } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 1000) {
            stitchprice = stitchmultiple * 0.25D + extracost;
          } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 3000) {
            stitchprice = stitchmultiple * 0.22D + extracost;
          } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 5000) {
            stitchprice = stitchmultiple * 0.21D + extracost;
          } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 10000) {
            stitchprice = stitchmultiple * 0.2D + extracost;
          } else {
            stitchprice = stitchmultiple * 0.19D + extracost;
          }
          

          int numberofcolors = this.myorderdata.getSet(i).getLogo(j).getNumberOfColor() == null ? 0 : this.myorderdata.getSet(i).getLogo(j).getNumberOfColor().intValue();
          
          if (SharedData.isFaya.booleanValue()) {
            if (numberofcolors > 12) {
              int extracolorcount = numberofcolors - 12;
              for (int extracolor = 0; extracolor < extracolorcount; extracolor++) {
                stitchprice += 2.5D;
              }
              
            }
          }
          else if (numberofcolors > 8) {
            int extracolorcount = numberofcolors - 8;
            double halfstitchcost = stitchprice / 2.0D;
            for (int extracolor = 0; extracolor < extracolorcount; extracolor++) {
              stitchprice += halfstitchcost;
            }
          }
          





          if (SharedData.isFaya.booleanValue()) {
            tapeeditprice = 25.0D;
            artworkchargehourprice = 40.0D;
            colorchangeembprice = 8.0D;
          }
          else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 48) {
            int uniquefilenamequantity = itemhelper3.getTotalCapTypeLocationLogo(uniquefilename);
            if (uniquefilenamequantity != 0) {
              stitchprice += (48 - uniquefilenamequantity) * stitchprice / uniquefilenamequantity;
            }
          }
          

          if (quantity > 0) {
            addTableRow("", this.myorderdata.getSet(i).getLogo(j).getDecoration(), String.valueOf(quantity), NumberFormat.getCurrencyInstance().format(stitchprice), NumberFormat.getCurrencyInstance().format(stitchprice * quantity));
          } else {
            addTableRow("", this.myorderdata.getSet(i).getLogo(j).getDecoration(), "", "", "");
          }
          totalprice += stitchprice * quantity;
          
          if (this.myorderdata.getSet(i).getLogo(j).getTapeEdit()) {
            addTableRow("", "Tape Edit Charge", "1", NumberFormat.getCurrencyInstance().format(tapeeditprice), NumberFormat.getCurrencyInstance().format(tapeeditprice));
            totalprice += tapeeditprice;
          }
          
          if (SharedData.isFaya.booleanValue()) {
            metallicthreadprice = 0.15D;
            if ((this.myorderdata.getSet(i).getLogo(j).getMetallicThread()) || (this.myorderdata.getSet(i).getLogo(j).getNeonThread())) {
              double specialthreadpriceing = stitchmultiple * metallicthreadprice;
              if (specialthreadpriceing < 0.5D) {
                specialthreadpriceing = 0.5D;
              }
              addTableRow("", "Special Thread", String.valueOf(quantity), NumberFormat.getCurrencyInstance().format(stitchmultiple * metallicthreadprice), NumberFormat.getCurrencyInstance().format(specialthreadpriceing * quantity));
              totalprice += specialthreadpriceing * quantity;
            }
          }
          else {
            if (this.myorderdata.getSet(i).getLogo(j).getMetallicThread()) {
              addTableRow("", "Metallic Thread", String.valueOf(quantity), NumberFormat.getCurrencyInstance().format(stitchmultiple * metallicthreadprice), NumberFormat.getCurrencyInstance().format(stitchmultiple * metallicthreadprice * quantity));
              totalprice += stitchmultiple * metallicthreadprice * quantity;
            }
            if (this.myorderdata.getSet(i).getLogo(j).getNeonThread()) {
              addTableRow("", "Neon Thread", String.valueOf(quantity), NumberFormat.getCurrencyInstance().format(stitchmultiple * neonthreadprice), NumberFormat.getCurrencyInstance().format(stitchmultiple * neonthreadprice * quantity));
              totalprice += stitchmultiple * neonthreadprice * quantity;
            }
          }
          

          if (this.myorderdata.getSet(i).getLogo(j).getColorChange()) {
            addTableRow("", "Color Change Charge", "1", NumberFormat.getCurrencyInstance().format(colorchangeembprice), NumberFormat.getCurrencyInstance().format(colorchangeembprice));
            totalprice += colorchangeembprice;
          }
        }
        else if (this.myorderdata.getSet(i).getLogo(j).getDecoration().equals("Heat Transfer")) {
          int numofcolor = this.myorderdata.getSet(i).getLogo(j).getNumberOfColor() == null ? 0 : this.myorderdata.getSet(i).getLogo(j).getNumberOfColor().intValue();
          
          double filmchargeprice = 8.0D;
          double heatpressprice = 0.0D;
          double flashchargeprice = 0.1D;
          
          if (SharedData.isFaya.booleanValue()) {
            filmchargeprice = 15.0D;
          }
          
          if ((this.myorderdata.getSet(i).getItem(0).getStyleType() == StyleInformationData.StyleType.DOMESTIC_SHIRTS) || (this.myorderdata.getSet(i).getItem(0).getStyleType() == StyleInformationData.StyleType.DOMESTIC_SWEATSHIRTS) || (this.myorderdata.getSet(i).getItem(0).getStyleType() == StyleInformationData.StyleType.DOMESTIC_TOTEBAGS) || (this.myorderdata.getSet(i).getItem(0).getStyleType() == StyleInformationData.StyleType.DOMESTIC_APRONS)) {
            if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 72) {
              heatpressprice = 1.6D + numofcolor * 0.7D;
            } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 144) {
              heatpressprice = 1.3D + numofcolor * 0.5D;
            } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 288) {
              heatpressprice = 0.9D + numofcolor * 0.5D;
            } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 500) {
              heatpressprice = 0.7D + numofcolor * 0.4D;
            } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 1000) {
              heatpressprice = 0.55D + numofcolor * 0.35D;
            } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 3000) {
              heatpressprice = 0.4D + numofcolor * 0.3D;
            } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 5000) {
              heatpressprice = 0.35D + numofcolor * 0.3D;
            } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 10000) {
              heatpressprice = 0.3D + numofcolor * 0.3D;
            } else {
              heatpressprice = 0.25D + numofcolor * 0.3D;
            }
          }
          else if (((this.myorderdata.getSet(i).getLogo(j).getLogoSizeWidth() == null) || (this.myorderdata.getSet(i).getLogo(j).getLogoSizeWidth().doubleValue() <= 4.0D)) && ((this.myorderdata.getSet(i).getLogo(j).getLogoSizeHeight() == null) || (this.myorderdata.getSet(i).getLogo(j).getLogoSizeHeight().doubleValue() <= 2.5D))) {
            if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 72) {
              heatpressprice = 1.7D + numofcolor * 0.2D;
            } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 144) {
              heatpressprice = 1.5D + numofcolor * 0.1D;
            } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 288) {
              heatpressprice = 1.2D + numofcolor * 0.1D;
            } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 500) {
              heatpressprice = 0.9D + numofcolor * 0.1D;
            } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 1000) {
              heatpressprice = 0.8D + numofcolor * 0.1D;
            } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 3000) {
              heatpressprice = 0.7D + numofcolor * 0.1D;
            } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 5000) {
              heatpressprice = 0.6D + numofcolor * 0.1D;
            } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 10000) {
              heatpressprice = 0.5D + numofcolor * 0.1D;
            } else {
              heatpressprice = 0.4D + numofcolor * 0.1D;
            }
          }
          else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 72) {
            heatpressprice += 1.6D + numofcolor * 0.7D;
          } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 144) {
            heatpressprice += 1.4D + numofcolor * 0.5D;
          } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 288) {
            heatpressprice += 1.1D + numofcolor * 0.5D;
          } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 500) {
            heatpressprice += 0.9D + numofcolor * 0.4D;
          } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 1000) {
            heatpressprice += 0.7D + numofcolor * 0.35D;
          } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 3000) {
            heatpressprice += 0.6D + numofcolor * 0.3D;
          } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 5000) {
            heatpressprice += 0.45D + numofcolor * 0.3D;
          } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 10000) {
            heatpressprice += 0.4D + numofcolor * 0.3D;
          } else {
            heatpressprice += 0.3D + numofcolor * 0.3D;
          }
          






          if (SharedData.isFaya.booleanValue()) {
            if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 48) {
              heatpressprice *= 2.0D;
            } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 144) {
              heatpressprice *= 1.35D;
            } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 288) {
              heatpressprice *= 1.3D;
            } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 576) {
              heatpressprice *= 1.26D;
            } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 1000) {
              heatpressprice *= 1.23D;
            } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 2000) {
              heatpressprice *= 1.2D;
            } else {
              heatpressprice *= 1.18D;
            }
          }
          

          if (!SharedData.isFaya.booleanValue())
          {
            if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 48) {
              int uniquefilenamequantity = itemhelper3.getTotalCapTypeLocationLogo(uniquefilename);
              if (uniquefilenamequantity != 0) {
                heatpressprice += (48 - uniquefilenamequantity) * heatpressprice / uniquefilenamequantity;
              }
            }
          }
          
          if (quantity > 0) {
            addTableRow("", this.myorderdata.getSet(i).getLogo(j).getDecoration(), String.valueOf(quantity), NumberFormat.getCurrencyInstance().format(heatpressprice), NumberFormat.getCurrencyInstance().format(heatpressprice * quantity));
          } else {
            addTableRow("", this.myorderdata.getSet(i).getLogo(j).getDecoration(), "", "", "");
          }
          totalprice += heatpressprice * quantity;
          
          if (this.myorderdata.getSet(i).getLogo(j).getFilmCharge()) {
            addTableRow("", "Film Charge", String.valueOf(numofcolor), NumberFormat.getCurrencyInstance().format(filmchargeprice), NumberFormat.getCurrencyInstance().format(filmchargeprice * numofcolor));
            totalprice += filmchargeprice * numofcolor;
          }
          
          if ((this.myorderdata.getSet(i).getLogo(j).getFilmSetupCharge() != null) && (this.myorderdata.getSet(i).getLogo(j).getFilmSetupCharge().doubleValue() > 0.0D)) {
            double filmsetupcharge = this.myorderdata.getSet(i).getLogo(j).getFilmSetupCharge().doubleValue();
            addTableRow("", "Screen Setup Charge", String.valueOf(numofcolor), NumberFormat.getCurrencyInstance().format(filmsetupcharge), NumberFormat.getCurrencyInstance().format(filmsetupcharge * numofcolor));
            totalprice += filmsetupcharge * numofcolor;
          }
          
          boolean foundflashcharge = false;
          if (this.myorderdata.getSet(i).getLogo(j).getFlashCharge()) {
            addTableRow("", (this.myorderdata.getSet(i).getLogo(j).getDecoration() != null) && (this.myorderdata.getSet(i).getLogo(j).getDecoration().equals("Heat Transfer")) ? "Color Garment Charge" : "Flash Charge", String.valueOf(quantity), NumberFormat.getCurrencyInstance().format(flashchargeprice * numofcolor), NumberFormat.getCurrencyInstance().format(flashchargeprice * numofcolor * quantity));
            totalprice += flashchargeprice * numofcolor * quantity;
            foundflashcharge = true;
          }
          if (!foundflashcharge) {
            int flashchargetimes = 0;
            for (int l = 0; l < this.myorderdata.getSet(i).getLogo(j).getColorwayCount(); l++) {
              if (this.myorderdata.getSet(i).getLogo(j).getColorway(l).getFlashCharge()) {
                flashchargetimes++;
              }
            }
            if (flashchargetimes > 0) {
              addTableRow("", "Color Garment Charge", String.valueOf(quantity), NumberFormat.getCurrencyInstance().format(flashchargeprice * flashchargetimes), NumberFormat.getCurrencyInstance().format(flashchargeprice * flashchargetimes * quantity));
              totalprice += flashchargeprice * flashchargetimes * quantity;
            }
          }
          
          if (this.myorderdata.getSet(i).getLogo(j).getSpecialInk()) {
            double specialinkprice = 0.2D * numofcolor;
            addTableRow("", "Special Ink", String.valueOf(quantity), NumberFormat.getCurrencyInstance().format(specialinkprice), NumberFormat.getCurrencyInstance().format(specialinkprice * quantity));
            totalprice += specialinkprice * quantity;
          }
          
          if ((this.myorderdata.getSet(i).getLogo(j).getColorChangeAmount() != null) && (this.myorderdata.getSet(i).getLogo(j).getColorChangeAmount().intValue() > 0)) {
            int colorchangeamount = this.myorderdata.getSet(i).getLogo(j).getColorChangeAmount().intValue();
            addTableRow("", "Color Change Charge", String.valueOf(colorchangeamount), NumberFormat.getCurrencyInstance().format(colorchangeheatprice), NumberFormat.getCurrencyInstance().format(colorchangeheatprice * colorchangeamount));
            totalprice += colorchangeheatprice * colorchangeamount;
          }
        } else if (this.myorderdata.getSet(i).getLogo(j).getDecoration().equals("CAD Print"))
        {
          double cadprintprice = 0.0D;
          
          if (!SharedData.isFaya.booleanValue()) {
            if ((this.myorderdata.getSet(i).getLogo(j).getCustomerLogoPrice() != null) && (this.myorderdata.getSet(i).getLogo(j).getCustomerLogoPrice().doubleValue() != 0.0D)) {
              cadprintprice = this.myorderdata.getSet(i).getLogo(j).getCustomerLogoPrice().doubleValue();
            } else {
              double venlogoprice = this.myorderdata.getSet(i).getLogo(j).getVendorLogoPrice().doubleValue();
              if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 72) {
                cadprintprice = venlogoprice * 1.45D;
              } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 144) {
                cadprintprice = venlogoprice * 1.4D;
              } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 288) {
                cadprintprice = venlogoprice * 1.35D;
              } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 500) {
                cadprintprice = venlogoprice * 1.3D;
              } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 1000) {
                cadprintprice = venlogoprice * 1.28D;
              } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 3000) {
                cadprintprice = venlogoprice * 1.25D;
              } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 5000) {
                cadprintprice = venlogoprice * 1.23D;
              } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 10000) {
                cadprintprice = venlogoprice * 1.22D;
              } else {
                cadprintprice = venlogoprice * 1.21D;
              }
            }
            

            cadprintprice += 0.5D;
          }
          else {
            if ((this.myorderdata.getSet(i).getLogo(j).getCustomerLogoPrice() != null) && (this.myorderdata.getSet(i).getLogo(j).getCustomerLogoPrice().doubleValue() != 0.0D)) {
              cadprintprice = this.myorderdata.getSet(i).getLogo(j).getCustomerLogoPrice().doubleValue();
            } else {
              double venlogoprice = this.myorderdata.getSet(i).getLogo(j).getVendorLogoPrice().doubleValue();
              if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 144) {
                cadprintprice = venlogoprice * 1.5D;
              } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 288) {
                cadprintprice = venlogoprice * 1.45D;
              } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 576) {
                cadprintprice = venlogoprice * 1.4D;
              } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 1000) {
                cadprintprice = venlogoprice * 1.35D;
              } else {
                cadprintprice = venlogoprice * 1.3D;
              }
            }
            

            cadprintprice += 0.75D;
          }
          

          if (!SharedData.isFaya.booleanValue())
          {
            if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 48) {
              int uniquefilenamequantity = itemhelper3.getTotalCapTypeLocationLogo(uniquefilename);
              if (uniquefilenamequantity != 0) {
                cadprintprice += (48 - uniquefilenamequantity) * cadprintprice / uniquefilenamequantity;
              }
            }
          }
          
          cadprintprice = Math.ceil((float)(cadprintprice * 100.0D)) / 100.0D;
          
          if (quantity > 0) {
            addTableRow("", this.myorderdata.getSet(i).getLogo(j).getDecoration(), String.valueOf(quantity), NumberFormat.getCurrencyInstance().format(cadprintprice), NumberFormat.getCurrencyInstance().format(cadprintprice * quantity));
          } else {
            addTableRow("", this.myorderdata.getSet(i).getLogo(j).getDecoration(), "", "", "");
          }
          totalprice += cadprintprice * quantity;
          

          String caduniquefilename = itemhelper3.getUniqueLogo(filename, stitchcountname);
          if (!cadtreeset.contains(caduniquefilename)) {
            addTableRow("", "Setup Fee", String.valueOf(1), NumberFormat.getCurrencyInstance().format(100.0D), NumberFormat.getCurrencyInstance().format(100.0D));
            totalprice += 100.0D;
            cadtreeset.add(caduniquefilename);
          }
        }
        else if (this.myorderdata.getSet(i).getLogo(j).getDecoration().equals("Screen Print"))
        {
          if (!SharedData.isFaya.booleanValue())
          {
            if (this.myorderdata.getSet(i).getItem(0).getStyleType() == StyleInformationData.StyleType.DOMESTIC_TOTEBAGS)
            {
              int numofcolor = this.myorderdata.getSet(i).getLogo(j).getNumberOfColor() == null ? 0 : this.myorderdata.getSet(i).getLogo(j).getNumberOfColor().intValue();
              
              double filmchargeprice = 10.0D;
              double screenprintprice = 0.0D;
              double flashchargeprice = 0.5D;
              





























              if ((this.myorderdata.getSet(i).getLogo(j).getCustomerLogoPrice() != null) && (this.myorderdata.getSet(i).getLogo(j).getCustomerLogoPrice().doubleValue() != 0.0D)) {
                screenprintprice = this.myorderdata.getSet(i).getLogo(j).getCustomerLogoPrice().doubleValue();
              } else {
                double venlogoprice = this.myorderdata.getSet(i).getLogo(j).getVendorLogoPrice().doubleValue();
                if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 72) {
                  screenprintprice = venlogoprice * 1.45D;
                } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 144) {
                  screenprintprice = venlogoprice * 1.4D;
                } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 288) {
                  screenprintprice = venlogoprice * 1.35D;
                } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 500) {
                  screenprintprice = venlogoprice * 1.3D;
                } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 1000) {
                  screenprintprice = venlogoprice * 1.28D;
                } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 3000) {
                  screenprintprice = venlogoprice * 1.25D;
                } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 5000) {
                  screenprintprice = venlogoprice * 1.23D;
                } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 10000) {
                  screenprintprice = venlogoprice * 1.22D;
                } else {
                  screenprintprice = venlogoprice * 1.21D;
                }
              }
              

              if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 48) {
                int uniquefilenamequantity = itemhelper3.getTotalCapTypeLocationLogo(uniquefilename);
                if (uniquefilenamequantity != 0) {
                  screenprintprice += (48 - uniquefilenamequantity) * screenprintprice / uniquefilenamequantity;
                }
              }
              
              if (quantity > 0) {
                addTableRow("", this.myorderdata.getSet(i).getLogo(j).getDecoration(), String.valueOf(quantity), NumberFormat.getCurrencyInstance().format(screenprintprice), NumberFormat.getCurrencyInstance().format(screenprintprice * quantity));
              } else {
                addTableRow("", this.myorderdata.getSet(i).getLogo(j).getDecoration(), "", "", "");
              }
              totalprice += screenprintprice * quantity;
              
              if (this.myorderdata.getSet(i).getLogo(j).getFilmCharge()) {
                addTableRow("", "Film Charge", String.valueOf(numofcolor), NumberFormat.getCurrencyInstance().format(filmchargeprice), NumberFormat.getCurrencyInstance().format(filmchargeprice * numofcolor));
                totalprice += filmchargeprice * numofcolor;
              }
              
              if ((this.myorderdata.getSet(i).getLogo(j).getFilmSetupCharge() != null) && (this.myorderdata.getSet(i).getLogo(j).getFilmSetupCharge().doubleValue() > 0.0D)) {
                double filmsetupcharge = this.myorderdata.getSet(i).getLogo(j).getFilmSetupCharge().doubleValue();
                addTableRow("", "Setup Charge", String.valueOf(numofcolor), NumberFormat.getCurrencyInstance().format(filmsetupcharge), NumberFormat.getCurrencyInstance().format(filmsetupcharge * numofcolor));
                totalprice += filmsetupcharge * numofcolor;
              }
              
              boolean foundflashcharge = false;
              if (this.myorderdata.getSet(i).getLogo(j).getFlashCharge()) {
                addTableRow("", "Flash Charge", String.valueOf(quantity), NumberFormat.getCurrencyInstance().format(flashchargeprice * numofcolor), NumberFormat.getCurrencyInstance().format(flashchargeprice * numofcolor * quantity));
                totalprice += flashchargeprice * numofcolor * quantity;
                foundflashcharge = true;
              }
              if (!foundflashcharge) {
                int flashchargetimes = 0;
                for (int l = 0; l < this.myorderdata.getSet(i).getLogo(j).getColorwayCount(); l++) {
                  if (this.myorderdata.getSet(i).getLogo(j).getColorway(l).getFlashCharge()) {
                    flashchargetimes++;
                  }
                }
                if (flashchargetimes > 0) {
                  addTableRow("", "Flash Charge", String.valueOf(quantity), NumberFormat.getCurrencyInstance().format(flashchargeprice * flashchargetimes), NumberFormat.getCurrencyInstance().format(flashchargeprice * flashchargetimes * quantity));
                  totalprice += flashchargeprice * flashchargetimes * quantity;
                }
              }
              
              double specialinkcost = 0.0D;
              for (int k = 0; k < this.myorderdata.getSet(i).getLogo(j).getColorwayCount(); k++) {
                if ((this.myorderdata.getSet(i).getLogo(j).getColorway(k).getInkType() != null) && (!this.myorderdata.getSet(i).getLogo(j).getColorway(k).getInkType().equals("")) && (!this.myorderdata.getSet(i).getLogo(j).getColorway(k).getInkType().equals("Plastisol Ink")))
                {
                  if ((this.myorderdata.getSet(i).getLogo(j).getColorway(k).getInkType().equals("Discharge Ink")) || (this.myorderdata.getSet(i).getLogo(j).getColorway(k).getInkType().equals("Waterbased Ink"))) {
                    specialinkcost += 0.3D;
                  } else if ((this.myorderdata.getSet(i).getLogo(j).getColorway(k).getInkType().equals("Metallic Ink")) || (this.myorderdata.getSet(i).getLogo(j).getColorway(k).getInkType().equals("Puff Ink")) || (this.myorderdata.getSet(i).getLogo(j).getColorway(k).getInkType().equals("Crystalina Ink"))) {
                    specialinkcost += 0.2D;
                  } else if (this.myorderdata.getSet(i).getLogo(j).getColorway(k).getInkType().equals("Glitter Ink")) {
                    specialinkcost += 0.35D;
                  } else {
                    throw new Exception("Ink Type does not have a price");
                  }
                }
              }
              if (specialinkcost > 0.0D) {
                addTableRow("", "Special Ink", String.valueOf(quantity), NumberFormat.getCurrencyInstance().format(specialinkcost), NumberFormat.getCurrencyInstance().format(specialinkcost * quantity));
                totalprice += specialinkcost * quantity;
              }
              
              if ((this.myorderdata.getSet(i).getLogo(j).getColorChangeAmount() != null) && (this.myorderdata.getSet(i).getLogo(j).getColorChangeAmount().intValue() > 0)) {
                int colorchangeamount = this.myorderdata.getSet(i).getLogo(j).getColorChangeAmount().intValue();
                addTableRow("", "Color Change Charge", String.valueOf(colorchangeamount), NumberFormat.getCurrencyInstance().format(colorchangeheatprice), NumberFormat.getCurrencyInstance().format(colorchangeheatprice * colorchangeamount));
                totalprice += colorchangeheatprice * colorchangeamount;
              }
            }
            else
            {
              int numofcolor = this.myorderdata.getSet(i).getLogo(j).getNumberOfColor() == null ? 0 : this.myorderdata.getSet(i).getLogo(j).getNumberOfColor().intValue();
              
              double filmchargeprice = 15.0D;
              double screenprintprice = 0.0D;
              double flashchargeprice = 0.3D;
              






























              if ((this.myorderdata.getSet(i).getLogo(j).getCustomerLogoPrice() != null) && (this.myorderdata.getSet(i).getLogo(j).getCustomerLogoPrice().doubleValue() != 0.0D)) {
                screenprintprice = this.myorderdata.getSet(i).getLogo(j).getCustomerLogoPrice().doubleValue();
              } else { double venlogoprice;
                double venlogoprice;
                if (this.myorderdata.getSet(i).getLogo(j).getVendorLogoPrice() == null) {
                  System.out.println(i + " " + j);
                  venlogoprice = 0.0D;
                } else {
                  venlogoprice = this.myorderdata.getSet(i).getLogo(j).getVendorLogoPrice().doubleValue();
                }
                if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 72) {
                  screenprintprice = venlogoprice * 1.45D;
                } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 144) {
                  screenprintprice = venlogoprice * 1.4D;
                } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 288) {
                  screenprintprice = venlogoprice * 1.35D;
                } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 500) {
                  screenprintprice = venlogoprice * 1.3D;
                } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 1000) {
                  screenprintprice = venlogoprice * 1.28D;
                } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 3000) {
                  screenprintprice = venlogoprice * 1.25D;
                } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 5000) {
                  screenprintprice = venlogoprice * 1.23D;
                } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 10000) {
                  screenprintprice = venlogoprice * 1.22D;
                } else {
                  screenprintprice = venlogoprice * 1.21D;
                }
              }
              

              if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 48) {
                int uniquefilenamequantity = itemhelper3.getTotalCapTypeLocationLogo(uniquefilename);
                if (uniquefilenamequantity != 0) {
                  screenprintprice += (48 - uniquefilenamequantity) * screenprintprice / uniquefilenamequantity;
                }
              }
              
              if (quantity > 0) {
                addTableRow("", this.myorderdata.getSet(i).getLogo(j).getDecoration(), String.valueOf(quantity), NumberFormat.getCurrencyInstance().format(screenprintprice), NumberFormat.getCurrencyInstance().format(screenprintprice * quantity));
              } else {
                addTableRow("", this.myorderdata.getSet(i).getLogo(j).getDecoration(), "", "", "");
              }
              totalprice += screenprintprice * quantity;
              
              if (this.myorderdata.getSet(i).getLogo(j).getFilmCharge()) {
                addTableRow("", "Film Charge", String.valueOf(numofcolor), NumberFormat.getCurrencyInstance().format(filmchargeprice), NumberFormat.getCurrencyInstance().format(filmchargeprice * numofcolor));
                totalprice += filmchargeprice * numofcolor;
              }
              
              if ((this.myorderdata.getSet(i).getLogo(j).getFilmSetupCharge() != null) && (this.myorderdata.getSet(i).getLogo(j).getFilmSetupCharge().doubleValue() > 0.0D)) {
                double filmsetupcharge = this.myorderdata.getSet(i).getLogo(j).getFilmSetupCharge().doubleValue();
                addTableRow("", "Setup Charge", String.valueOf(numofcolor), NumberFormat.getCurrencyInstance().format(filmsetupcharge), NumberFormat.getCurrencyInstance().format(filmsetupcharge * numofcolor));
                totalprice += filmsetupcharge * numofcolor;
              }
              
              boolean foundflashcharge = false;
              if (this.myorderdata.getSet(i).getLogo(j).getFlashCharge()) {
                addTableRow("", "Flash Charge", String.valueOf(quantity), NumberFormat.getCurrencyInstance().format(flashchargeprice * numofcolor), NumberFormat.getCurrencyInstance().format(flashchargeprice * numofcolor * quantity));
                totalprice += flashchargeprice * numofcolor * quantity;
                foundflashcharge = true;
              }
              if (!foundflashcharge) {
                int flashchargetimes = 0;
                for (int l = 0; l < this.myorderdata.getSet(i).getLogo(j).getColorwayCount(); l++) {
                  if (this.myorderdata.getSet(i).getLogo(j).getColorway(l).getFlashCharge()) {
                    flashchargetimes++;
                  }
                }
                if (flashchargetimes > 0) {
                  addTableRow("", "Flash Charge", String.valueOf(quantity), NumberFormat.getCurrencyInstance().format(flashchargeprice * flashchargetimes), NumberFormat.getCurrencyInstance().format(flashchargeprice * flashchargetimes * quantity));
                  totalprice += flashchargeprice * flashchargetimes * quantity;
                }
              }
              
              double specialinkcost = 0.0D;
              for (int k = 0; k < this.myorderdata.getSet(i).getLogo(j).getColorwayCount(); k++) {
                if ((this.myorderdata.getSet(i).getLogo(j).getColorway(k).getInkType() != null) && (!this.myorderdata.getSet(i).getLogo(j).getColorway(k).getInkType().equals("")) && (!this.myorderdata.getSet(i).getLogo(j).getColorway(k).getInkType().equals("Plastisol Ink")))
                {
                  if ((this.myorderdata.getSet(i).getLogo(j).getColorway(k).getInkType().equals("Discharge Ink")) || (this.myorderdata.getSet(i).getLogo(j).getColorway(k).getInkType().equals("Waterbased Ink"))) {
                    specialinkcost += 0.3D;
                  } else if ((this.myorderdata.getSet(i).getLogo(j).getColorway(k).getInkType().equals("Metallic Ink")) || (this.myorderdata.getSet(i).getLogo(j).getColorway(k).getInkType().equals("Puff Ink")) || (this.myorderdata.getSet(i).getLogo(j).getColorway(k).getInkType().equals("Crystalina Ink"))) {
                    specialinkcost += 0.2D;
                  } else if (this.myorderdata.getSet(i).getLogo(j).getColorway(k).getInkType().equals("Glitter Ink")) {
                    specialinkcost += 0.35D;
                  } else {
                    throw new Exception("Ink Type does not have a price");
                  }
                }
              }
              if (specialinkcost > 0.0D) {
                addTableRow("", "Special Ink", String.valueOf(quantity), NumberFormat.getCurrencyInstance().format(specialinkcost), NumberFormat.getCurrencyInstance().format(specialinkcost * quantity));
                totalprice += specialinkcost * quantity;
              }
              
              if ((this.myorderdata.getSet(i).getLogo(j).getColorChangeAmount() != null) && (this.myorderdata.getSet(i).getLogo(j).getColorChangeAmount().intValue() > 0)) {
                int colorchangeamount = this.myorderdata.getSet(i).getLogo(j).getColorChangeAmount().intValue();
                addTableRow("", "Color Change Charge", String.valueOf(colorchangeamount), NumberFormat.getCurrencyInstance().format(colorchangeheatprice), NumberFormat.getCurrencyInstance().format(colorchangeheatprice * colorchangeamount));
                totalprice += colorchangeheatprice * colorchangeamount;
              }
              
            }
            
          }
          else
          {
            int numofcolor = this.myorderdata.getSet(i).getLogo(j).getNumberOfColor() == null ? 0 : this.myorderdata.getSet(i).getLogo(j).getNumberOfColor().intValue();
            
            double filmchargeprice = 15.0D;
            double screenprintprice = 0.0D;
            double flashchargeprice = 0.3D;
            
            if ((this.myorderdata.getSet(i).getLogo(j).getCustomerLogoPrice() != null) && (this.myorderdata.getSet(i).getLogo(j).getCustomerLogoPrice().doubleValue() != 0.0D)) {
              screenprintprice = this.myorderdata.getSet(i).getLogo(j).getCustomerLogoPrice().doubleValue();
            } else {
              double venlogoprice = this.myorderdata.getSet(i).getLogo(j).getVendorLogoPrice().doubleValue();
              if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 144) {
                screenprintprice = venlogoprice * 1.5D;
              } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 288) {
                screenprintprice = venlogoprice * 1.45D;
              } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 576) {
                screenprintprice = venlogoprice * 1.4D;
              } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 1000) {
                screenprintprice = venlogoprice * 1.35D;
              } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 2000) {
                screenprintprice = venlogoprice * 1.3D;
              } else {
                screenprintprice = venlogoprice * 1.25D;
              }
              

              if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 48) {
                int uniquefilenamequantity = itemhelper3.getTotalCapTypeLocationLogo(uniquefilename);
                if (uniquefilenamequantity != 0) {
                  screenprintprice += (48 - uniquefilenamequantity) * screenprintprice / uniquefilenamequantity;
                }
              }
            }
            


            if (quantity > 0) {
              addTableRow("", this.myorderdata.getSet(i).getLogo(j).getDecoration(), String.valueOf(quantity), NumberFormat.getCurrencyInstance().format(screenprintprice), NumberFormat.getCurrencyInstance().format(screenprintprice * quantity));
            } else {
              addTableRow("", this.myorderdata.getSet(i).getLogo(j).getDecoration(), "", "", "");
            }
            totalprice += screenprintprice * quantity;
            
            if (this.myorderdata.getSet(i).getLogo(j).getFilmCharge()) {
              addTableRow("", "Film Charge", String.valueOf(numofcolor), NumberFormat.getCurrencyInstance().format(filmchargeprice), NumberFormat.getCurrencyInstance().format(filmchargeprice * numofcolor));
              totalprice += filmchargeprice * numofcolor;
            }
            
            if ((this.myorderdata.getSet(i).getLogo(j).getFilmSetupCharge() != null) && (this.myorderdata.getSet(i).getLogo(j).getFilmSetupCharge().doubleValue() > 0.0D)) {
              double filmsetupcharge = this.myorderdata.getSet(i).getLogo(j).getFilmSetupCharge().doubleValue();
              addTableRow("", "Setup Charge", String.valueOf(numofcolor), NumberFormat.getCurrencyInstance().format(filmsetupcharge), NumberFormat.getCurrencyInstance().format(filmsetupcharge * numofcolor));
              totalprice += filmsetupcharge * numofcolor;
            }
            
            boolean foundflashcharge = false;
            if (this.myorderdata.getSet(i).getLogo(j).getFlashCharge()) {
              addTableRow("", "Flash Charge", String.valueOf(quantity), NumberFormat.getCurrencyInstance().format(flashchargeprice * numofcolor), NumberFormat.getCurrencyInstance().format(flashchargeprice * numofcolor * quantity));
              totalprice += flashchargeprice * numofcolor * quantity;
              foundflashcharge = true;
            }
            if (!foundflashcharge) {
              int flashchargetimes = 0;
              for (int l = 0; l < this.myorderdata.getSet(i).getLogo(j).getColorwayCount(); l++) {
                if (this.myorderdata.getSet(i).getLogo(j).getColorway(l).getFlashCharge()) {
                  flashchargetimes++;
                }
              }
              if (flashchargetimes > 0) {
                addTableRow("", "Flash Charge", String.valueOf(quantity), NumberFormat.getCurrencyInstance().format(flashchargeprice * flashchargetimes), NumberFormat.getCurrencyInstance().format(flashchargeprice * flashchargetimes * quantity));
                totalprice += flashchargeprice * flashchargetimes * quantity;
              }
            }
            
            double specialinkcost = 0.0D;
            for (int k = 0; k < this.myorderdata.getSet(i).getLogo(j).getColorwayCount(); k++) {
              if ((this.myorderdata.getSet(i).getLogo(j).getColorway(k).getInkType() != null) && (!this.myorderdata.getSet(i).getLogo(j).getColorway(k).getInkType().equals("")) && (!this.myorderdata.getSet(i).getLogo(j).getColorway(k).getInkType().equals("Plastisol Ink")))
              {
                if ((this.myorderdata.getSet(i).getLogo(j).getColorway(k).getInkType().equals("Discharge Ink")) || (this.myorderdata.getSet(i).getLogo(j).getColorway(k).getInkType().equals("Waterbased Ink"))) {
                  specialinkcost += 0.3D;
                } else if ((this.myorderdata.getSet(i).getLogo(j).getColorway(k).getInkType().equals("Metallic Ink")) || (this.myorderdata.getSet(i).getLogo(j).getColorway(k).getInkType().equals("Puff Ink")) || (this.myorderdata.getSet(i).getLogo(j).getColorway(k).getInkType().equals("Crystalina Ink"))) {
                  specialinkcost += 0.2D;
                } else if (this.myorderdata.getSet(i).getLogo(j).getColorway(k).getInkType().equals("Glitter Ink")) {
                  specialinkcost += 0.35D;
                } else {
                  throw new Exception("Ink Type does not have a price");
                }
              }
            }
            if (specialinkcost > 0.0D) {
              addTableRow("", "Special Ink", String.valueOf(quantity), NumberFormat.getCurrencyInstance().format(specialinkcost), NumberFormat.getCurrencyInstance().format(specialinkcost * quantity));
              totalprice += specialinkcost * quantity;
            }
            
            if ((this.myorderdata.getSet(i).getLogo(j).getColorChangeAmount() != null) && (this.myorderdata.getSet(i).getLogo(j).getColorChangeAmount().intValue() > 0)) {
              int colorchangeamount = this.myorderdata.getSet(i).getLogo(j).getColorChangeAmount().intValue();
              addTableRow("", "Color Change Charge", String.valueOf(colorchangeamount), NumberFormat.getCurrencyInstance().format(colorchangeheatprice), NumberFormat.getCurrencyInstance().format(colorchangeheatprice * colorchangeamount));
              totalprice += colorchangeheatprice * colorchangeamount;
            }
            
          }
        }
        else if (this.myorderdata.getSet(i).getLogo(j).getDecoration().equals("Direct To Garment"))
        {
          double directtogarmentprice = 0.0D;
          
          String thelogolocation = this.myorderdata.getSet(i).getLogo(j).getLogoLocation().trim();
          
          if (SharedData.isFaya.booleanValue()) {
            if ((!thelogolocation.startsWith("A")) || (thelogolocation.startsWith("AP"))) {
              if ((thelogolocation.equals("1")) || (thelogolocation.equals("2")) || (thelogolocation.equals("3")) || (thelogolocation.equals("4")) || (thelogolocation.equals("18")) || (thelogolocation.equals("19"))) {
                if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 2) {
                  directtogarmentprice = 8.5D;
                } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 6) {
                  directtogarmentprice = 7.5D;
                } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 12) {
                  directtogarmentprice = 6.75D;
                } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 24) {
                  directtogarmentprice = 6.0D;
                } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 48) {
                  directtogarmentprice = 5.25D;
                } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 72) {
                  directtogarmentprice = 4.75D;
                } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 144) {
                  directtogarmentprice = 4.35D;
                } else {
                  directtogarmentprice = 3.95D;
                }
              } else if (thelogolocation.equals("42")) {
                if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 2) {
                  directtogarmentprice = 9.5D;
                } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 6) {
                  directtogarmentprice = 8.5D;
                } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 12) {
                  directtogarmentprice = 7.95D;
                } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 24) {
                  directtogarmentprice = 7.25D;
                } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 48) {
                  directtogarmentprice = 6.5D;
                } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 72) {
                  directtogarmentprice = 5.95D;
                } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 144) {
                  directtogarmentprice = 5.5D;
                } else {
                  directtogarmentprice = 4.75D;
                }
              } else {
                throw new Exception("Direct To Garment Can't Have Selected Location");
              }
            }
            else if (!haddirecttogarment) {
              if (this.myorderdata.getSet(i).getLogo(j).getLogoSizeChoice().equals("Small")) {
                if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 2) {
                  directtogarmentprice = 8.95D;
                } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 6) {
                  directtogarmentprice = 7.95D;
                } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 12) {
                  directtogarmentprice = 7.5D;
                } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 24) {
                  directtogarmentprice = 6.75D;
                } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 48) {
                  directtogarmentprice = 6.25D;
                } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 72) {
                  directtogarmentprice = 6.0D;
                } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 144) {
                  directtogarmentprice = 5.75D;
                } else {
                  directtogarmentprice = 5.49D;
                }
              }
              else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 2) {
                directtogarmentprice = 9.5D;
              } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 6) {
                directtogarmentprice = 8.95D;
              } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 12) {
                directtogarmentprice = 8.5D;
              } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 24) {
                directtogarmentprice = 8.0D;
              } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 48) {
                directtogarmentprice = 7.75D;
              } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 72) {
                directtogarmentprice = 7.5D;
              } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 144) {
                directtogarmentprice = 7.25D;
              } else {
                directtogarmentprice = 6.99D;
              }
              

              haddirecttogarment = true;
            }
            else if (this.myorderdata.getSet(i).getLogo(j).getLogoSizeChoice().equals("Small")) {
              if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 2) {
                directtogarmentprice = 3.99D;
              } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 6) {
                directtogarmentprice = 3.99D;
              } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 12) {
                directtogarmentprice = 3.99D;
              } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 24) {
                directtogarmentprice = 3.99D;
              } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 48) {
                directtogarmentprice = 3.99D;
              } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 72) {
                directtogarmentprice = 3.99D;
              } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 144) {
                directtogarmentprice = 3.99D;
              } else {
                directtogarmentprice = 3.99D;
              }
            }
            else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 2) {
              directtogarmentprice = 5.99D;
            } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 6) {
              directtogarmentprice = 5.99D;
            } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 12) {
              directtogarmentprice = 5.99D;
            } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 24) {
              directtogarmentprice = 5.99D;
            } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 48) {
              directtogarmentprice = 5.99D;
            } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 72) {
              directtogarmentprice = 5.99D;
            } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 144) {
              directtogarmentprice = 5.99D;
            } else {
              directtogarmentprice = 5.99D;
            }
            

          }
          else
          {

            if ((!thelogolocation.startsWith("A")) || (thelogolocation.startsWith("AP"))) {
              if ((thelogolocation.equals("1")) || (thelogolocation.equals("2")) || (thelogolocation.equals("3")) || (thelogolocation.equals("4")) || (thelogolocation.equals("18")) || (thelogolocation.equals("19"))) {
                if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 24) {
                  directtogarmentprice = 7.95D;
                } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 48) {
                  directtogarmentprice = 4.95D;
                } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 72) {
                  directtogarmentprice = 4.5D;
                } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 144) {
                  directtogarmentprice = 4.25D;
                } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 288) {
                  directtogarmentprice = 3.75D;
                } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 576) {
                  directtogarmentprice = 3.3D;
                } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 1008) {
                  directtogarmentprice = 2.95D;
                } else {
                  directtogarmentprice = 2.85D;
                }
              } else if (thelogolocation.equals("42")) {
                if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 24) {
                  directtogarmentprice = 8.95D;
                } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 48) {
                  directtogarmentprice = 6.25D;
                } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 72) {
                  directtogarmentprice = 5.8D;
                } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 144) {
                  directtogarmentprice = 5.5D;
                } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 288) {
                  directtogarmentprice = 4.75D;
                } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 576) {
                  directtogarmentprice = 4.35D;
                } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 1008) {
                  directtogarmentprice = 3.95D;
                } else {
                  directtogarmentprice = 3.85D;
                }
              } else {
                throw new Exception("Direct To Garment Can't Have Selected Location");
              }
            }
            else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 24) {
              directtogarmentprice = 7.95D;
            } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 48) {
              directtogarmentprice = 4.75D;
            } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 72) {
              directtogarmentprice = 4.25D;
            } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 144) {
              directtogarmentprice = 3.85D;
            } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 288) {
              directtogarmentprice = 3.5D;
            } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 576) {
              directtogarmentprice = 3.25D;
            } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 1008) {
              directtogarmentprice = 2.95D;
            } else {
              directtogarmentprice = 2.85D;
            }
            


            if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 24) {
              int uniquefilenamequantity = itemhelper3.getTotalCapTypeLocationLogo(uniquefilename);
              if (uniquefilenamequantity != 0) {
                directtogarmentprice += (24 - uniquefilenamequantity) * directtogarmentprice / uniquefilenamequantity;
              }
            }
          }
          
          if (quantity > 0) {
            addTableRow("", this.myorderdata.getSet(i).getLogo(j).getDecoration(), String.valueOf(quantity), NumberFormat.getCurrencyInstance().format(directtogarmentprice), NumberFormat.getCurrencyInstance().format(directtogarmentprice * quantity));
          } else {
            addTableRow("", this.myorderdata.getSet(i).getLogo(j).getDecoration(), "", "", "");
          }
          totalprice += directtogarmentprice * quantity;
        }
        else if ((this.myorderdata.getSet(i).getLogo(j).getCustomerLogoPrice() != null) && (this.myorderdata.getSet(i).getLogo(j).getCustomerLogoPrice().doubleValue() > 0.0D)) {
          addTableRow("", this.myorderdata.getSet(i).getLogo(j).getDecoration(), String.valueOf(quantity), NumberFormat.getCurrencyInstance().format(this.myorderdata.getSet(i).getLogo(j).getCustomerLogoPrice()), NumberFormat.getCurrencyInstance().format(this.myorderdata.getSet(i).getLogo(j).getCustomerLogoPrice().doubleValue() * quantity));
          totalprice += this.myorderdata.getSet(i).getLogo(j).getCustomerLogoPrice().doubleValue() * quantity;
        } else if ((this.myorderdata.getSet(i).getLogo(j).getVendorLogoPrice() != null) && (this.myorderdata.getSet(i).getLogo(j).getVendorLogoPrice().doubleValue() > 0.0D) && 
          (SharedData.isFaya.booleanValue()))
        {
          double vendorpricewithmarkup = this.myorderdata.getSet(i).getLogo(j).getVendorLogoPrice().doubleValue();
          
          if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 144) {
            vendorpricewithmarkup *= 1.5D;
          } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 288) {
            vendorpricewithmarkup *= 1.45D;
          } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 576) {
            vendorpricewithmarkup *= 1.4D;
          } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 1000) {
            vendorpricewithmarkup *= 1.35D;
          } else if (itemhelper3.getTotalCapTypeLocationLogo(uniquefilename) < 2000) {
            vendorpricewithmarkup *= 1.3D;
          } else {
            vendorpricewithmarkup *= 1.25D;
          }
          
          addTableRow("", this.myorderdata.getSet(i).getLogo(j).getDecoration(), String.valueOf(quantity), NumberFormat.getCurrencyInstance().format(vendorpricewithmarkup), NumberFormat.getCurrencyInstance().format(vendorpricewithmarkup * quantity));
          totalprice += vendorpricewithmarkup * quantity;
        }
        




        if ((this.myorderdata.getSet(i).getLogo(j).getArtworkChargePerHour() != null) && (this.myorderdata.getSet(i).getLogo(j).getArtworkChargePerHour().doubleValue() > 0.0D)) {
          double artworkchargehour = this.myorderdata.getSet(i).getLogo(j).getArtworkChargePerHour().doubleValue();
          addTableRow("", "Artwork Charge", String.valueOf(artworkchargehour), NumberFormat.getCurrencyInstance().format(artworkchargehourprice), NumberFormat.getCurrencyInstance().format(artworkchargehourprice * artworkchargehour));
          totalprice += artworkchargehourprice * artworkchargehour;
        }
        
        if (this.myorderdata.getSet(i).getLogo(j).getDigitizing()) {
          String digitizinguniquefilename = itemhelper3.getUniqueLogo(filename, stitchcountname);
          
          if (SharedData.isFaya.booleanValue())
          {
            int totalflatnreg = itemhelper3.getTotalLogo(digitizinguniquefilename);
            if ((i == higheststitchcountitem) && (j == higheststitchcountlogo) && (totalflatnreg >= 144))
            {
              int stitchmultiple = (int)Math.ceil((stitchcount - 5000) / 1000.0D);
              if (stitchcount - 5000 <= 0) {
                stitchmultiple = 0;
              }
              
              double digitizingprice = 8.0D * stitchmultiple;
              
              if (digitizingprice > 0.0D) {
                addTableRow("", "Digitizing Fee - 144 Qty Discount", "1", NumberFormat.getCurrencyInstance().format(digitizingprice), NumberFormat.getCurrencyInstance().format(digitizingprice));
                totalprice += digitizingprice;
              } else {
                addTableRow("", "Free Digitizing - 144 Qty Discount", "1", NumberFormat.getCurrencyInstance().format(0L), NumberFormat.getCurrencyInstance().format(0L));
              }
            }
            else {
              int stitchmultiple = (int)Math.ceil(stitchcount / 1000.0D);
              



              double digitizingprice = 8.0D * stitchmultiple;
              if (digitizingprice < 35.0D) {
                digitizingprice = 35.0D;
              }
              
              addTableRow("", "Digitizing Fee", "1", NumberFormat.getCurrencyInstance().format(digitizingprice), NumberFormat.getCurrencyInstance().format(digitizingprice));
              totalprice += digitizingprice;
            }
          }
          else {
            int totalflatnreg = itemhelper3.getTotalLogoOttoOnly(digitizinguniquefilename);
            if (cheaperdigitizingmethod)
            {
              if ((!this.myorderdata.getSet(i).getItem(0).getStyleNumber().equals("nonotto")) && (!this.myorderdata.getSet(i).getItem(0).getStyleNumber().equals("nonottoflat")) && (totalflatnreg >= 144)) {
                int stitchmultiple = (int)Math.ceil(stitchcount / 1000.0D);
                if (stitchcount <= 5000) {
                  stitchmultiple = 5;
                }
                double digitizingprice = 8.0D * stitchmultiple;
                
                addTableRow("", "Digitizing Fee", "1", NumberFormat.getCurrencyInstance().format(digitizingprice), NumberFormat.getCurrencyInstance().format(digitizingprice));
                
                int discountstitchmultiple = (int)Math.ceil((stitchcount - 6000) / 1000.0D);
                if (stitchcount - 6000 <= 0) {
                  discountstitchmultiple = 0;
                }
                
                double digitizingpricediscounted = 8.0D * discountstitchmultiple;
                
                double digfee = digitizingpricediscounted - digitizingprice;
                
                addTableRow("", "Digitizing Discount", "1", NumberFormat.getCurrencyInstance().format(digfee), NumberFormat.getCurrencyInstance().format(digfee));
                
                totalprice += digitizingpricediscounted;



              }
              else
              {



                int stitchmultiple = (int)Math.ceil(stitchcount / 1000.0D);
                if (stitchcount <= 5000) {
                  stitchmultiple = 5;
                }
                double digitizingprice = 8.0D * stitchmultiple;
                addTableRow("", "Digitizing Fee", "1", NumberFormat.getCurrencyInstance().format(digitizingprice), NumberFormat.getCurrencyInstance().format(digitizingprice));
                totalprice += digitizingprice;
              }
              

            }
            else if ((!this.myorderdata.getSet(i).getItem(0).getStyleNumber().equals("nonotto")) && (!this.myorderdata.getSet(i).getItem(0).getStyleNumber().equals("nonottoflat")) && (i == higheststitchcountitem) && (j == higheststitchcountlogo) && (totalflatnreg >= 144)) {
              int stitchmultiple = (int)Math.ceil((stitchcount - 6000) / 1000.0D);
              if (stitchcount - 6000 <= 0) {
                stitchmultiple = 0;
              }
              



              double digitizingprice = 8.0D * stitchmultiple;
              
              if (digitizingprice > 0.0D) {
                addTableRow("", "Digitizing Fee - 144 Qty Discount", "1", NumberFormat.getCurrencyInstance().format(digitizingprice), NumberFormat.getCurrencyInstance().format(digitizingprice));
                totalprice += digitizingprice;
              } else {
                addTableRow("", "Free Digitizing - 144 Qty Discount", "1", NumberFormat.getCurrencyInstance().format(0L), NumberFormat.getCurrencyInstance().format(0L));
              }
            }
            else {
              int stitchmultiple = (int)Math.ceil(stitchcount / 1000.0D);
              if (stitchcount <= 5000) {
                stitchmultiple = 5;
              }
              double digitizingprice = 8.0D * stitchmultiple;
              addTableRow("", "Digitizing Fee", "1", NumberFormat.getCurrencyInstance().format(digitizingprice), NumberFormat.getCurrencyInstance().format(digitizingprice));
              totalprice += digitizingprice;
            }
          }
        }
        





        if ((this.myorderdata.getSet(i).getLogo(j).getSwatchTotalDone() != null) && (this.myorderdata.getSet(i).getLogo(j).getSwatchTotalDone().intValue() > 0)) {
          if (this.myorderdata.getSet(i).getLogo(j).getDecoration().equals("CAD Print")) {
            double swatchprice = 40.0D;
            int totalswatch = this.myorderdata.getSet(i).getLogo(j).getSwatchTotalDone().intValue();
            
            addTableRow("", "Swatch", String.valueOf(totalswatch), NumberFormat.getCurrencyInstance().format(swatchprice), NumberFormat.getCurrencyInstance().format(swatchprice * totalswatch));
            totalprice += swatchprice * totalswatch;
          }
          else {
            double swatchprice = 8.0D;
            int totalswatch = this.myorderdata.getSet(i).getLogo(j).getSwatchTotalDone().intValue();
            int swatchstitchmultiple = (int)Math.floor(stitchcount / 10000.0D) + 1;
            double newswatchprice = swatchstitchmultiple * swatchprice;
            
            addTableRow("", "Swatch", String.valueOf(totalswatch), NumberFormat.getCurrencyInstance().format(newswatchprice), NumberFormat.getCurrencyInstance().format(newswatchprice * totalswatch));
            totalprice += newswatchprice * totalswatch;
          }
        }
        
        if (!SharedData.isFaya.booleanValue())
        {
          String swatchuniqueid = mynewfreeswatch.getUniqueID(this.myorderdata.getSet(i).getLogo(j).getDecoration(), capflattype, String.valueOf(this.myorderdata.getSet(i).getLogo(j).getStitchCount()), this.myorderdata.getSet(i).getLogo(j).getFilename());
          if (mynewfreeswatch.hasOttoProduct(swatchuniqueid)) {
            int totalswatch = this.myorderdata.getSet(i).getLogo(j).getSwatchTotalDone() != null ? this.myorderdata.getSet(i).getLogo(j).getSwatchTotalDone().intValue() : 0;
            
            if ((mynewfreeswatchlist.get(swatchuniqueid) != null) && (((DomesticSampleDiscountCounter)mynewfreeswatchlist.get(swatchuniqueid)).getFreeSampleAmount() > 0))
            {
              int totaltotake = totalswatch - ((DomesticSampleDiscountCounter)mynewfreeswatchlist.get(swatchuniqueid)).getFreeSampleAmount() < 0 ? totalswatch : ((DomesticSampleDiscountCounter)mynewfreeswatchlist.get(swatchuniqueid)).getFreeSampleAmount();
              
              addTableRow("", "Swatch Discount", String.valueOf(totaltotake), NumberFormat.getCurrencyInstance().format(((DomesticSampleDiscountCounter)mynewfreeswatchlist.get(swatchuniqueid)).getFreeSampleCost() * -1.0D), NumberFormat.getCurrencyInstance().format(((DomesticSampleDiscountCounter)mynewfreeswatchlist.get(swatchuniqueid)).getFreeSampleCost() * totaltotake * -1.0D));
              totalprice += ((DomesticSampleDiscountCounter)mynewfreeswatchlist.get(swatchuniqueid)).getFreeSampleCost() * totaltotake * -1.0D;
              
              ((DomesticSampleDiscountCounter)mynewfreeswatchlist.get(swatchuniqueid)).setFreeSampleAmount(((DomesticSampleDiscountCounter)mynewfreeswatchlist.get(swatchuniqueid)).getFreeSampleAmount() - totaltotake);
            }
          }
        }
      }
      



      addTableRow("", "", "Subtotal:", NumberFormat.getCurrencyInstance().format(Math.ceil((float)((totalprice - lasttotalprice) / quantity * 100.0D)) / 100.0D), NumberFormat.getCurrencyInstance().format(totalprice - lasttotalprice));
    }
    

    if (this.myorderdata.getRushOrder()) {
      HiddenEcho("Rush Order: " + (totalprice - totalcapprice) * 0.2D + " = ((" + totalprice + " - " + totalcapprice + ") * 0.20 <BR>");
      
      double rushordercharge = (totalprice - totalcapprice) * 0.2D;
      if ((totalprice - totalcapprice) * 0.2D < 50.0D) {
        rushordercharge = 50.0D;
      }
      totalprice += rushordercharge;
      addTableRow("Rush Order Charge", "", "1", NumberFormat.getCurrencyInstance().format(rushordercharge), NumberFormat.getCurrencyInstance().format(rushordercharge));
    }
    



    int timesquantitychange = 0;
    
    TreeMap<String, Integer> allstylesinorder = itemhelper3.getCloneOfTotalStyleItem();
    TreeSet<String> stylewithtype = itemhelper3.getCloneOfSameStyleDecorationType();
    String[] thekeys = (String[])allstylesinorder.keySet().toArray(new String[0]);
    for (int i = 0; i < thekeys.length; i++) {
      if ((!thekeys[i].equals("nonotto")) && (!thekeys[i].equals("nonottoflat")) && 
        (((Integer)allstylesinorder.get(thekeys[i])).intValue() < 48)) {
        if ((stylewithtype.contains(thekeys[i] + " <> Flat Embroidery")) || (stylewithtype.contains(thekeys[i] + " <> 3D Embroidery")) || (stylewithtype.contains(thekeys[i] + " <> Heat Transfer")) || (stylewithtype.contains(thekeys[i] + " <> CAD Print")) || (stylewithtype.contains(thekeys[i] + " <> Screen Print")) || (stylewithtype.contains(thekeys[i] + " <> Other"))) {
          timesquantitychange++;
        } else if (stylewithtype.contains(thekeys[i] + " <> Direct To Garment")) {
          if (((Integer)allstylesinorder.get(thekeys[i])).intValue() < 24) {
            timesquantitychange++;
          }
        } else {
          timesquantitychange++;
        }
      }
    }
    

































    if (!SharedData.isFaya.booleanValue())
    {
      if (timesquantitychange > 0) {
        addTableRow("Under-minimum Charge", "", String.valueOf(timesquantitychange), NumberFormat.getCurrencyInstance().format(10L), NumberFormat.getCurrencyInstance().format(10 * timesquantitychange));
        totalprice += 10 * timesquantitychange;
      }
    }
    








































































    for (int i = 0; i < this.myorderdata.getDiscountItemCount(); i++) {
      if (!this.myorderdata.getDiscountItem(i).getIntoItems().booleanValue()) {
        String reason = this.myorderdata.getDiscountItem(i).getReason();
        try {
          double amount = this.myorderdata.getDiscountItem(i).getAmount().doubleValue();
          addTableRow(reason, "", "", "", NumberFormat.getCurrencyInstance().format(amount));
          totalprice += amount;
        } catch (Exception e) {
          throw new Exception("Discount Item Price Is Invalid");
        }
      }
    }
    
    if (SharedData.isFaya.booleanValue())
    {
      addTableRow(" ", "", "", "", "");
      addTableRow("", "", "", "Subtotal:", NumberFormat.getCurrencyInstance().format(totalprice));
      
      if ((this.myorderdata.getCustomerInformation().getBillInformation().getState().toLowerCase().equals("ca")) && (!this.myorderdata.getCustomerInformation().getTaxExampt())) {
        this.sqltable.makeTable("SELECT `tax` FROM `faya_tax` WHERE `name` = 'current' LIMIT 1");
        double currenttax = ((Double)this.sqltable.getValue()).doubleValue();
        double tax = totalprice * (currenttax / 100.0D);
        addTableRow(" ", "", "", "", "");
        addTableRow("Tax", "", "", currenttax + "%", NumberFormat.getCurrencyInstance().format(tax));
        totalprice += tax;
      }
    }
    

    double dropshipmentprice = 5.0D;
    if ((this.myorderdata.getCustomerInformation().getHaveDropShipment()) && (this.myorderdata.getCustomerInformation().getDropShipmentAmount().intValue() > 0)) {
      addTableRow("Additional Drop Shipment Charge", "", String.valueOf(this.myorderdata.getCustomerInformation().getDropShipmentAmount()), NumberFormat.getCurrencyInstance().format(dropshipmentprice), NumberFormat.getCurrencyInstance().format(dropshipmentprice * this.myorderdata.getCustomerInformation().getDropShipmentAmount().intValue()));
      totalprice += dropshipmentprice * this.myorderdata.getCustomerInformation().getDropShipmentAmount().intValue();
    }
    

    double shippingcost = 0.0D;
    if ((itemhelper3.getHaveNonOtto()) && (this.myorderdata.getShippingCost() == null)) {
      addTableRow("Shipping", "", "", "", "");
      addTableRow("", this.myorderdata.getShippingType() + " (Estimated)", "", "", "(N/A)");
    } else if (this.myorderdata.getShippingType().equals("Pick-up")) {
      addTableRow("Shipping", "", "", "", "");
      addTableRow("", this.myorderdata.getShippingType(), "", "", "(N/A)");
    } else if ((this.myorderdata.getShippingType().equals("Truck")) && (this.myorderdata.getShippingCost() == null)) {
      shippingcost = 0.0D;
      addTableRow("Shipping", "", "", "", "");
      addTableRow("", this.myorderdata.getShippingType(), "", "", "(TBD)");
    } else if (this.myorderdata.getShippingCost() != null) {
      shippingcost = this.myorderdata.getShippingCost().doubleValue();
      addTableRow("Shipping", "", "", "", "");
      addTableRow("", this.myorderdata.getShippingType() + " (Estimated)", "", "", NumberFormat.getCurrencyInstance().format(shippingcost));
    } else if ((this.myorderdata.getShippingType().equals("Will Call")) || (this.myorderdata.getShippingType().equals("Will Call COD")) || (this.myorderdata.getShippingType().equals("Pick Up")) || (this.myorderdata.getShippingType().equals("Truck")) || (this.myorderdata.getShippingType().equals("Special Truck")) || (this.myorderdata.getShippingType().equals("UPS 3rd Party"))) {
      addTableRow("Shipping", "", "", "", "");
      addTableRow("", this.myorderdata.getShippingType() + " (Estimated)", "", "", NumberFormat.getCurrencyInstance().format(shippingcost));
    } else if ((this.myorderdata.getShippingType().equals("UPS Ground")) || (this.myorderdata.getShippingType().equals("UPS Ground Residential")) || (this.myorderdata.getShippingType().equals("UPS Next Day")) || (this.myorderdata.getShippingType().equals("UPS Next Day Saver")) || (this.myorderdata.getShippingType().equals("UPS Next Day Saturday")) || (this.myorderdata.getShippingType().equals("UPS 2nd Day")) || (this.myorderdata.getShippingType().equals("UPS 3rd Day"))) {
      try {
        if (this.myorderdata.getCustomerInformation().getShipInformation().getZip().trim().equals("")) {
          addTableRow("Shipping", "", "", "", "");
          addTableRow("", this.myorderdata.getShippingType() + " (TBD)", "", "", NumberFormat.getCurrencyInstance().format(shippingcost));
        } else {
          if (calculateonlineshipping) {
            WorkFlowShippingCalculation newshipping = new WorkFlowShippingCalculation(this.myorderdata, this.sqltable);
            shippingcost = newshipping.getTotalPrice();
            HiddenEcho(newshipping.getDebugInfo());
          }
          addTableRow("Shipping", "", "", "", "");
          addTableRow("", this.myorderdata.getShippingType() + " (Estimated)", "", "", NumberFormat.getCurrencyInstance().format(shippingcost));
        }
      } catch (Exception e) {
        System.out.println(this.myorderdata.getHiddenKey() + " have shipping errors");
        
        addTableRow("Shipping", "", "", "", "");
        addTableRow("", this.myorderdata.getShippingType() + " (TBD)", "", "", NumberFormat.getCurrencyInstance().format(shippingcost));
      }
    } else {
      addTableRow("Shipping", "", "", "", "");
      addTableRow("", this.myorderdata.getShippingType() + " (Estimated)", "", "", "(N/A)");
    }
    
    setCustomerTotalCapPrice(totalcapprice);
    setCustomerTotalDecorationPrice(totalprice - totalcapprice);
    
    totalprice += shippingcost;
    
    addTableRow(" ", "", "", "", "");
    addTableRow("", "", "", "Grand Total:", NumberFormat.getCurrencyInstance().format(totalprice));
    
    setCustomerTotalPrice(totalprice);
  }
  
  public String getHiddenEcho()
  {
    return this.output;
  }
  
  private void HiddenEcho(String myecho) {
    this.output += myecho;
  }
  
  private void addTableRow(String string1, String string2, String string3, String string4, String string5) {
    String[] mystring = new String[5];
    mystring[0] = string1;
    mystring[1] = string2;
    mystring[2] = string3;
    mystring[3] = string4;
    mystring[4] = string5;
    this.pricetableholder.add(mystring);
  }
  
  public ArrayList<String[]> getTableRow() {
    return this.pricetableholder;
  }
  
  private void setCustomerTotalCapPrice(double price) {
    this.customertotalcapprice = price;
  }
  
  public double getCustomerTotalCapPrice() {
    return this.customertotalcapprice;
  }
  
  private void setCustomerTotalDecorationPrice(double price) {
    this.customertotaldecorationprice = price;
  }
  
  public double getCustomerTotalDecorationPrice() {
    return this.customertotaldecorationprice;
  }
  
  private void setCustomerTotalPrice(double price) {
    this.customertotalprice = price;
  }
  
  public double getCustomerTotalPrice() {
    return this.customertotalprice;
  }
}
