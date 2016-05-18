package com.ottocap.NewWorkFlow.server.PriceCalculation;

import com.extjs.gxt.ui.client.core.FastMap;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataVendorInformation;
import com.ottocap.NewWorkFlow.client.StyleInformationData.StyleType;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderData;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataAddress;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataCustomerInformation;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataDiscount;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataItem;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataLogo;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataLogoDecoration;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataSet;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataVendors;
import com.ottocap.NewWorkFlow.server.PriceCalculation.Helper.OverseasCFSCost;
import com.ottocap.NewWorkFlow.server.PriceCalculation.Helper.OverseasFreeProductSampleCalculator;
import com.ottocap.NewWorkFlow.server.PriceCalculation.Helper.OverseasPriceHelper;
import com.ottocap.NewWorkFlow.server.PriceCalculation.Helper.OverseasSpecialDyeCost;
import com.ottocap.NewWorkFlow.server.PriceCalculation.Helper.OverseasVendorFreeProductSampleCalculator;
import com.ottocap.NewWorkFlow.server.PriceCalculation.Shipping.WorkFlowShippingCalculation;
import com.ottocap.NewWorkFlow.server.SQLTable;
import com.ottocap.NewWorkFlow.server.Settings;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.TreeMap;

public class OverseasPriceCalculation
{
  public String output = "";
  private ArrayList<String[]> pricetableholder = new ArrayList();
  private SQLTable sqltable;
  private ExtendOrderData myorderdata;
  private OverseasPriceHelper pricehelper;
  private double customertotaldecorationprice = 0.0D;
  private double customertotalcapprice = 0.0D;
  private double totalcustomerprice = 0.0D;
  private double[] vendorchargetotal;
  private double[] vendoritemdozencost;
  private double[] vendorsamplecharge;
  
  public OverseasPriceCalculation(SQLTable sqltable, ExtendOrderData myorderdata) throws Exception {
    this.sqltable = sqltable;
    this.myorderdata = myorderdata;
  }
  
  private void HiddenEcho(String myecho) {
    this.output += myecho;
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
  
  public void calculateSamplePrice(OverseasVendorFreeProductSampleCalculator mycalc) throws Exception
  {
    double[] samplecost = new double[Settings.TotalVendors];
    
    for (int i = 0; i < this.myorderdata.getSetCount(); i++) {
      double numberofsamples = 0.0D;
      if (this.myorderdata.getSet(i).getItem(0).getProductSampleToDo() != null) {
        numberofsamples = this.myorderdata.getSet(i).getItem(0).getProductSampleToDo().intValue();
      }
      
      if (!mycalc.checkItemFree(i)) {
        if (this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() == 1) {
          samplecost[this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber()] += numberofsamples * 3.8D;
        } else if (this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() == 4) {
          samplecost[this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber()] += numberofsamples * 0.0D;
        } else {
          samplecost[this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber()] += numberofsamples * 3.0D;
        }
      }
    }
    
    setSamplePrice(samplecost);
  }
  





















  private void setSamplePrice(double[] samplecost)
  {
    this.vendorsamplecharge = samplecost;
  }
  
  public double getSamplePrice(int vendornumber) {
    return this.vendorsamplecharge[vendornumber];
  }
  
  public void calculateOrderPrice(boolean calculateonlineshipping) throws Exception {
    this.pricehelper = new OverseasPriceHelper(this.myorderdata);
    OverseasCFSCost cfscost = new OverseasCFSCost(this.sqltable, this.myorderdata);
    OverseasSpecialDyeCost specialdyecost = new OverseasSpecialDyeCost(this.sqltable, this.myorderdata);
    
    double[] vendoritemdozcost = new double[this.myorderdata.getSetCount()];
    double[] vendortotalarray = new double[Settings.TotalVendors];
    
    double[] storeditemunitprice = new double[this.myorderdata.getSetCount()];
    double[] storeditemtotalprice = new double[this.myorderdata.getSetCount()];
    
    double customertotalprice = 0.0D;
    double customertotalcappricing = 0.0D;
    


    double exchangerate = this.myorderdata.getExchangeRate();
    double currentfactroyexchangerate;
    if (this.sqltable.makeTable("SELECT `rate`,`factoryrate` FROM `exchange_rates` ORDER BY `ratedate` DESC LIMIT 1").booleanValue()) {
      exchangerate = ((Double)this.sqltable.getCell("rate")).doubleValue();
      currentfactroyexchangerate = ((Double)this.sqltable.getCell("factoryrate")).doubleValue();
    } else {
      throw new Exception("Missing Factory Exchange Rate");
    }
    double currentfactroyexchangerate;
    if (exchangerate > currentfactroyexchangerate) {
      exchangerate = currentfactroyexchangerate;
    }
    
    for (int i = 0; i < this.myorderdata.getSetCount(); i++) {
      HiddenEcho("Item " + (i + 1) + "<br>");
      
      HiddenEcho("&nbsp;&nbsp;&nbsp;Style Number " + this.myorderdata.getSet(i).getItem(0).getStyleNumber() + "<br>");
      HiddenEcho("&nbsp;&nbsp;&nbsp;Quantity " + this.myorderdata.getSet(i).getItem(0).getQuantity() + "<br>");
      

      double styleprice = 0.0D;
      
      if (this.myorderdata.getSet(i).getItem(0).getStyleType() == StyleInformationData.StyleType.OVERSEAS_INSTOCK) {
        if ((this.myorderdata.getSet(i).getItem(0).getVendorNumber() != null) && (!this.myorderdata.getSet(i).getItem(0).getVendorNumber().equals("")) && (!this.myorderdata.getSet(i).getItem(0).getVendorNumber().equals("Default"))) {
          this.sqltable.makeTable("SELECT `Cap Price` FROM `styles_overseasinstock` WHERE `STYLE NUMBER` = '" + this.myorderdata.getSet(i).getItem(0).getStyleNumber() + "' AND `Backup VENDOR` = '" + this.myorderdata.getSet(i).getItem(0).getVendorNumber() + "' LIMIT 1");
        } else {
          this.sqltable.makeTable("SELECT `Primary VENDOR` FROM `styles_overseasinstock` WHERE `STYLE NUMBER` = '" + this.myorderdata.getSet(i).getItem(0).getStyleNumber() + "' LIMIT 1");
          String thevendor = (String)this.sqltable.getValue();
          this.sqltable.makeTable("SELECT `Cap Price` FROM `styles_overseasinstock` WHERE `STYLE NUMBER` = '" + this.myorderdata.getSet(i).getItem(0).getStyleNumber() + "' AND `Backup VENDOR` = '" + thevendor + "' LIMIT 1");
        }
        styleprice = Double.valueOf(((String)this.sqltable.getValue()).replace("$", "").trim()).doubleValue();
      } else if (this.myorderdata.getSet(i).getItem(0).getStyleType() == StyleInformationData.StyleType.OVERSEAS_INSTOCK_SHIRTS) {
        this.sqltable.makeTable("SELECT `shirtprice`,`colorcode` FROM `overseas_price_instockshirts` WHERE `stylenumber` = '" + this.myorderdata.getSet(i).getItem(0).getStyleNumber() + "' AND `size` = '" + this.myorderdata.getSet(i).getItem(0).getSize() + "' AND `colorcode` REGEXP '" + this.myorderdata.getSet(i).getItem(0).getColorCode() + "'");
        ArrayList<FastMap<Object>> mytable = this.sqltable.getTable();
        boolean foundmatch = false;
        for (int j = 0; j < mytable.size(); j++) {
          String mycolors = (String)((FastMap)mytable.get(j)).get("colorcode");
          String[] mycolorarray = mycolors.split(",");
          for (int k = 0; k < mycolorarray.length; k++) {
            if (mycolorarray[k].trim().equals(this.myorderdata.getSet(i).getItem(0).getColorCode())) {
              styleprice = Double.valueOf(((String)((FastMap)mytable.get(j)).get("shirtprice")).replace("$", "").trim()).doubleValue() * 12.0D;
              foundmatch = true;
            }
            if (foundmatch) {
              k = mycolorarray.length;
              j = mytable.size();
            }
          }
        }
        if (!foundmatch) {
          throw new Exception("Missing Cost of " + this.myorderdata.getSet(i).getItem(0).getStyleNumber() + "_" + this.myorderdata.getSet(i).getItem(0).getColorCode() + "_" + this.myorderdata.getSet(i).getItem(0).getSize());
        }
      } else if (this.myorderdata.getSet(i).getItem(0).getStyleType() == StyleInformationData.StyleType.OVERSEAS_INSTOCK_FLATS) {
        this.sqltable.makeTable("SELECT `price` FROM `overseas_price_instockflats` WHERE `stylenumber` = '" + this.myorderdata.getSet(i).getItem(0).getStyleNumber() + "' LIMIT 1");
        styleprice = Double.valueOf(((String)this.sqltable.getValue()).replace("$", "").trim()).doubleValue() * 12.0D;
      } else if (this.myorderdata.getSet(i).getItem(0).getStyleType() == StyleInformationData.StyleType.OVERSEAS) {
        this.sqltable.makeTable("SELECT `Cap FOB dz` FROM `styles_overseas` WHERE `Style` = '" + this.myorderdata.getSet(i).getItem(0).getStyleNumber() + "'");
        styleprice = Double.valueOf(((String)this.sqltable.getValue()).replace("$", "").trim()).doubleValue();
      } else if (this.myorderdata.getSet(i).getItem(0).getStyleType() == StyleInformationData.StyleType.OVERSEAS_LACKPARD) {
        this.sqltable.makeTable("SELECT * FROM `styles_lackpard` WHERE `Style` = '" + this.myorderdata.getSet(i).getItem(0).getStyleNumber() + "'");
        styleprice = 21.6D;
      } else if ((this.myorderdata.getSet(i).getItem(0).getStyleType() == StyleInformationData.StyleType.OVERSEAS_CUSTOMSTYLE) || (this.myorderdata.getSet(i).getItem(0).getStyleType() == StyleInformationData.StyleType.OVERSEAS_NONOTTO) || (this.myorderdata.getSet(i).getItem(0).getStyleType() == StyleInformationData.StyleType.OVERSEAS_NONOTTO_SHIRTS)) {
        try {
          styleprice = this.myorderdata.getSet(i).getItem(0).getFOBPrice().doubleValue();
        } catch (Exception e) {
          throw new Exception("FOB Price Is Missing For Style " + this.myorderdata.getSet(i).getItem(0).getStyleNumber());
        }
      } else if (this.myorderdata.getSet(i).getItem(0).getStyleType() == StyleInformationData.StyleType.OVERSEAS_PREDESIGNED) {
        this.sqltable.makeTable("SELECT `Blank FOB dz` FROM `styles_pre-designed` WHERE `Style` = '" + this.myorderdata.getSet(i).getItem(0).getStyleNumber() + "'");
        String fobdzvalue = (String)this.sqltable.getValue();
        if (fobdzvalue == null) {
          styleprice = 0.0D;
        } else {
          styleprice = Double.valueOf(fobdzvalue.replace("$", "").trim()).doubleValue();
        }
      }
      
      HiddenEcho("&nbsp;&nbsp;&nbsp;Style Price " + styleprice + "<br>");
      HiddenEcho("&nbsp;&nbsp;&nbsp;Vendor " + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "<br>");
      
      int breakdowncapquantity = 0;
      if (this.myorderdata.getSet(i).getItem(0).getAdvanceType().equals("AB")) {
        breakdowncapquantity = this.pricehelper.getBlankStyleQuantity(i);
      }
      else
      {
        breakdowncapquantity = this.pricehelper.getLogoStyleQuantity(i);
      }
      
      HiddenEcho("&nbsp;&nbsp;&nbsp;Mixed Cap Quantity " + breakdowncapquantity + "<br>");
      

      double quantitybreakdownfee = 0.0D;
      

      if (this.myorderdata.getSet(i).getItem(0).getAdvanceType().equals("AB"))
      {
        this.sqltable.makeTable("SELECT * FROM `overseas_price_quantity_break_fee` ORDER BY `overseas_price_quantity_break_fee`.`quantity` ASC");
        
        for (int j = 0; j < this.sqltable.getTable().size(); j++) {
          int quantity = ((Integer)((FastMap)this.sqltable.getTable().get(j)).get("quantity")).intValue();
          
          if (breakdowncapquantity >= quantity) {
            quantitybreakdownfee = Double.valueOf((String)((FastMap)this.sqltable.getTable().get(j)).get(String.valueOf(this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber()))).doubleValue();
            HiddenEcho("&nbsp;&nbsp;&nbsp;Checking BreakDown For Right Table: " + breakdowncapquantity + " >= " + quantity + " <br>");
          }
        }
        
        HiddenEcho("&nbsp;&nbsp;&nbsp;Quantity Break Fee " + quantitybreakdownfee + "<br>");
      }
      else
      {
        this.sqltable.makeTable("SELECT * FROM `overseas_price_quantity_break_fee` ORDER BY `overseas_price_quantity_break_fee`.`quantity` ASC");
        
        for (int j = 0; j < this.sqltable.getTable().size(); j++) {
          int quantity = ((Integer)((FastMap)this.sqltable.getTable().get(j)).get("quantity")).intValue();
          
          if (breakdowncapquantity >= quantity) {
            quantitybreakdownfee = Double.valueOf((String)((FastMap)this.sqltable.getTable().get(j)).get(String.valueOf(this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber()))).doubleValue();
            HiddenEcho("&nbsp;&nbsp;&nbsp;Checking BreakDown For Right Table: " + breakdowncapquantity + " >= " + quantity + " <br>");
          }
        }
        HiddenEcho("&nbsp;&nbsp;&nbsp;Quantity Break Fee " + quantitybreakdownfee + "<br>");
      }
      


      double[] decorationstotal = new double[this.myorderdata.getSet(i).getLogoCount()];
      double[] labelstotal = new double[this.myorderdata.getSet(i).getLogoCount()];
      boolean hadlabel = false;
      for (int j = 0; j < this.myorderdata.getSet(i).getLogoCount(); j++) {
        String filename = getMatchingFilename(this.myorderdata.getSet(i).getLogo(j).getFilename());
        String dstfilename = getMatchingFilename(this.myorderdata.getSet(i).getLogo(j).getDstFilename());
        int thestitchcount = this.myorderdata.getSet(i).getLogo(j).getStitchCount() == null ? 0 : this.myorderdata.getSet(i).getLogo(j).getStitchCount().intValue();
        String samevendorlogokey = this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + filename + dstfilename + thestitchcount + this.myorderdata.getSet(i).getLogo(j).getDecorationCount();
        
        decorationstotal[j] = 0.0D;
        HiddenEcho("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--------------------------------------------------------------------<br>");
        HiddenEcho("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Logo " + (j + 1) + "<br>");
        
        for (int k = 0; k < this.myorderdata.getSet(i).getLogo(j).getDecorationCount().intValue(); k++)
        {
          if (this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getName().equals("Flat Embroidery")) {
            double embprice = 0.0D;
            int flatstitch = this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getField1().intValue();
            int metallicstitch = this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getField2().intValue();
            
            if (flatstitch + metallicstitch < 3000) {
              flatstitch = 3000 - metallicstitch;
            }
            HiddenEcho("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + flatstitch + " = 3000 - " + metallicstitch + "<br>");
            
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'Flat Embroidery'");
            double flatembcost = Double.valueOf((String)this.sqltable.getValue()).doubleValue();
            

            if ((this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() == 4) && (this.myorderdata.getSet(i).getLogo(j).getNumberOfColor() != null) && (this.myorderdata.getSet(i).getLogo(j).getNumberOfColor().intValue() > 9)) {
              this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = '3D Embroidery' LIMIT 1");
              flatembcost = Double.valueOf((String)this.sqltable.getValue()).doubleValue();
            }
            

            HiddenEcho("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + (embprice + flatstitch / 1000 * flatembcost) + " = " + embprice + " + " + flatstitch + "/1000 * " + flatembcost + "<br>");
            embprice += flatstitch / 1000.0D * flatembcost;
            
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'Metallic Thread Flat Embroidery'");
            double matelembcost = Double.valueOf((String)this.sqltable.getValue()).doubleValue();
            

            if ((this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() == 4) && (this.myorderdata.getSet(i).getLogo(j).getNumberOfColor().intValue() > 9)) {
              this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'Metallic Thread 3D Embroidery' LIMIT 1");
              flatembcost = Double.valueOf((String)this.sqltable.getValue()).doubleValue();
            }
            
            HiddenEcho("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + (embprice + metallicstitch / 1000 * matelembcost) + " = " + embprice + " + " + metallicstitch + "/1000 * " + matelembcost + "<br>");
            embprice += metallicstitch / 1000.0D * matelembcost;
            
            if (this.myorderdata.getSet(i).getLogo(j).getLogoLocation().equals("42")) {
              this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'location42addcharge'");
              
              double loc42cost = Double.valueOf((String)this.sqltable.getValue()).doubleValue();
              HiddenEcho("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + (embprice + loc42cost) + " = " + embprice + " + " + loc42cost + "<br>");
              embprice += loc42cost;
            }
            
            HiddenEcho("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Flat EMB Price " + embprice + "<br>");
            
            decorationstotal[j] += embprice;
          } else if (this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getName().equals("3D Embroidery")) {
            double embprice = 0.0D;
            int stitch3d = this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getField1().intValue();
            int metallicstitch3d = this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getField2().intValue();
            int flatstitch = this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getField3().intValue();
            int metallicstitch = this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getField4().intValue();
            
            if ((flatstitch == 0) && (metallicstitch == 0) && ((metallicstitch3d > 0) || (stitch3d > 0)) && (metallicstitch3d + stitch3d < 3000)) {
              stitch3d = 3000 - metallicstitch3d;
            } else if (flatstitch + metallicstitch + metallicstitch3d + stitch3d < 3000) {
              flatstitch = 3000 - metallicstitch - metallicstitch3d - stitch3d;
            }
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'Flat Embroidery'");
            double flatembcost = Double.valueOf((String)this.sqltable.getValue()).doubleValue();
            

            if ((this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() == 4) && (this.myorderdata.getSet(i).getLogo(j).getNumberOfColor().intValue() > 9)) {
              this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = '3D Embroidery' LIMIT 1");
              flatembcost = Double.valueOf((String)this.sqltable.getValue()).doubleValue();
            }
            
            embprice += flatstitch / 1000.0D * flatembcost;
            
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'Metallic Thread Flat Embroidery'");
            double metalembcost = Double.valueOf((String)this.sqltable.getValue()).doubleValue();
            

            if ((this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() == 4) && (this.myorderdata.getSet(i).getLogo(j).getNumberOfColor().intValue() > 9)) {
              this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'Metallic Thread 3D Embroidery' LIMIT 1");
              flatembcost = Double.valueOf((String)this.sqltable.getValue()).doubleValue();
            }
            
            embprice += metallicstitch / 1000.0D * metalembcost;
            
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'Metallic Thread 3D Embroidery'");
            double metal3dembcost = Double.valueOf((String)this.sqltable.getValue()).doubleValue();
            embprice += metallicstitch3d / 1000.0D * metal3dembcost;
            
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = '3D Embroidery'");
            double flat3dembcost = Double.valueOf((String)this.sqltable.getValue()).doubleValue();
            embprice += stitch3d / 1000.0D * flat3dembcost;
            
            if (this.myorderdata.getSet(i).getLogo(j).getLogoLocation().equals("42")) {
              this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'location42addcharge'");
              
              double loc42cost = Double.valueOf((String)this.sqltable.getValue()).doubleValue();
              HiddenEcho("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + (embprice + loc42cost) + " = " + embprice + " + " + loc42cost + "<br>");
              embprice += loc42cost;
            }
            
            HiddenEcho("x.) 3D EMB Price ");
            HiddenEcho(String.valueOf(embprice));
            HiddenEcho("<br>");
            
            decorationstotal[j] += embprice;
          } else if ((this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getName().equals("Woven Label Applique")) || (this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getName().equals("Woven Label With Trim Applique"))) {
            double wovenlabelprice = 0.0D;
            double width = this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getField1().doubleValue();
            double height = this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getField2().doubleValue();
            double squarefeet = width * height;
            
            boolean foundmatch = false;
            this.sqltable.makeTable("SELECT * FROM `overseas_price_woven_label` ORDER BY `size` ASC");
            for (int l = 0; l < this.sqltable.getTable().size(); l++) {
              if ((!foundmatch) && 
                (squarefeet < ((Double)((FastMap)this.sqltable.getTable().get(l)).get("size")).doubleValue())) {
                foundmatch = true;
                wovenlabelprice = Double.valueOf((String)((FastMap)this.sqltable.getTable().get(l)).get(String.valueOf(this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber()))).doubleValue();
              }
            }
            

            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'wovenlabelsetup'");
            double setupfee = Double.valueOf((String)this.sqltable.getValue()).doubleValue() / (this.pricehelper.getSameVendorLogoQuantity(samevendorlogokey) / 12.0D);
            
            double trimcost = 0.0D;
            if (this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getName().equals("Woven Label With Trim Applique")) {
              HiddenEcho("x.) Woven Label With Trim Price ");
              this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'wovenlabelwithtrim'");
              trimcost = Double.valueOf((String)this.sqltable.getValue()).doubleValue();
            } else {
              HiddenEcho("x.) Woven Label Price ");
            }
            wovenlabelprice += setupfee + trimcost;
            
            if (!foundmatch) {
              throw new Exception("Logo Might Be Too Big For Woven Label Pricing");
            }
            HiddenEcho(String.valueOf(wovenlabelprice));
            HiddenEcho("<br>");
            
            decorationstotal[j] += wovenlabelprice;
          } else if (this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getName().equals("Felt Applique")) {
            int layers = this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getField1().intValue();
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'felt per piece'");
            double feltappliqueprice = layers * Double.valueOf((String)this.sqltable.getValue()).doubleValue();
            
            HiddenEcho("x.) Felt Applique Price ");
            HiddenEcho(String.valueOf(feltappliqueprice));
            HiddenEcho("<br>");
            
            decorationstotal[j] += feltappliqueprice;
          } else if (this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getName().equals("Twill Applique")) {
            int layers = this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getField1().intValue();
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'twill per piece'");
            double twillappliqueprice = layers * Double.valueOf((String)this.sqltable.getValue()).doubleValue();
            
            HiddenEcho("x.) Twill Applique Price ");
            HiddenEcho(String.valueOf(twillappliqueprice));
            HiddenEcho("<br>");
            
            decorationstotal[j] += twillappliqueprice;
          } else if (this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getName().equals("Distressed Twill Applique")) {
            int layers = this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getField1().intValue();
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'distressed twill per piece'");
            double distressedtwillappliqueprice = layers * Double.valueOf((String)this.sqltable.getValue()).doubleValue();
            
            HiddenEcho("x.) Distressed Twill Applique Price ");
            HiddenEcho(String.valueOf(distressedtwillappliqueprice));
            HiddenEcho("<br>");
            
            decorationstotal[j] += distressedtwillappliqueprice;
          } else if (this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getName().equals("Metallic Powder Fabic")) {
            int layers = this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getField1().intValue();
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'metallic powder fabic per piece'");
            double metallicpowerfabicprice = layers * Double.valueOf((String)this.sqltable.getValue()).doubleValue();
            
            HiddenEcho("x.) Metallic Powder Fabic Price ");
            HiddenEcho(String.valueOf(metallicpowerfabicprice));
            HiddenEcho("<br>");
            
            decorationstotal[j] += metallicpowerfabicprice;
          } else if (this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getName().equals("Rubber Patch")) {
            double rubberpatchprice = 0.0D;
            double width = this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getField1().doubleValue();
            double height = this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getField2().doubleValue();
            double squarefeet = width * height;
            
            boolean foundmatch = false;
            this.sqltable.makeTable("SELECT * FROM `overseas_price_rubber_patch` ORDER BY `size` ASC");
            for (int l = 0; l < this.sqltable.getTable().size(); l++) {
              if ((!foundmatch) && 
                (squarefeet < ((Double)((FastMap)this.sqltable.getTable().get(l)).get("size")).doubleValue())) {
                foundmatch = true;
                rubberpatchprice = Double.valueOf((String)((FastMap)this.sqltable.getTable().get(l)).get(String.valueOf(this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber()))).doubleValue();
              }
            }
            

            this.sqltable.makeTable("SELECT * FROM `overseas_price_charges` WHERE `name` = 'rubberpatchsetup'");
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'rubberpatchsetup'");
            double setupfee = Double.valueOf((String)this.sqltable.getValue()).doubleValue() / (this.pricehelper.getSameVendorLogoQuantity(samevendorlogokey) / 12.0D);
            
            rubberpatchprice += setupfee;
            
            HiddenEcho("x.) Rubber Patch Price ");
            if (!foundmatch) {
              throw new Exception("Logo Might Be Too Big For Woven Label Pricing");
            }
            HiddenEcho(String.valueOf(rubberpatchprice));
            HiddenEcho("<br>");
            
            decorationstotal[j] += rubberpatchprice;
          } else if (this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getName().equals("Printed Twill Applique")) {
            int numberofcolors = this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getField1().intValue();
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'printedtwillbycolor'");
            double printedtwillprice = numberofcolors * Double.valueOf((String)this.sqltable.getValue()).doubleValue();
            
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'printedtwilladdcharge'");
            printedtwillprice += Double.valueOf((String)this.sqltable.getValue()).doubleValue();
            
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'printedtwillsetupcharge'");
            printedtwillprice += numberofcolors * Double.valueOf((String)this.sqltable.getValue()).doubleValue() / (this.pricehelper.getSameVendorLogoQuantity(samevendorlogokey) / 12.0D);
            
            HiddenEcho("x.) Printed Twill Price ");
            HiddenEcho(String.valueOf(printedtwillprice));
            HiddenEcho("<br>");
            
            decorationstotal[j] += printedtwillprice;
          } else if (this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getName().equals("Puff Printed Twill Applique")) {
            int numberofcolors = this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getField1().intValue();
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'puffprintedtwillbycolor'");
            double puffprintedtwillprice = numberofcolors * Double.valueOf((String)this.sqltable.getValue()).doubleValue();
            
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'puffprintedtwilladdcharge'");
            puffprintedtwillprice += Double.valueOf((String)this.sqltable.getValue()).doubleValue();
            
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'printedtwillsetupcharge'");
            puffprintedtwillprice += numberofcolors * Double.valueOf((String)this.sqltable.getValue()).doubleValue() / (this.pricehelper.getSameVendorLogoQuantity(samevendorlogokey) / 12.0D);
            
            HiddenEcho("x.) Puff Printed Twill Price ");
            HiddenEcho(String.valueOf(puffprintedtwillprice));
            HiddenEcho("<br>");
            
            decorationstotal[j] += puffprintedtwillprice;
          } else if (this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getName().equals("Metallic Printed Twill Applique")) {
            int numberofcolors = this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getField1().intValue();
            
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'metallicprintedtwillbycolor'");
            HiddenEcho(numberofcolors * Double.valueOf((String)this.sqltable.getValue()).doubleValue() + " = " + numberofcolors + " * " + Double.valueOf((String)this.sqltable.getValue()));
            double metallicprintedtwillprice = numberofcolors * Double.valueOf((String)this.sqltable.getValue()).doubleValue();
            
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'metallicprintedtwilladdcharge'");
            HiddenEcho(metallicprintedtwillprice + Double.valueOf((String)this.sqltable.getValue()).doubleValue() + " = " + metallicprintedtwillprice + " + " + Double.valueOf((String)this.sqltable.getValue()));
            metallicprintedtwillprice += Double.valueOf((String)this.sqltable.getValue()).doubleValue();
            
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'printedtwillsetupcharge'");
            HiddenEcho(metallicprintedtwillprice + numberofcolors * Double.valueOf((String)this.sqltable.getValue()).doubleValue() / (this.pricehelper.getSameVendorLogoQuantity(samevendorlogokey) / 12.0D) + " = " + metallicprintedtwillprice + " + (" + numberofcolors + " * " + Double.valueOf((String)this.sqltable.getValue()) + ") / (" + this.pricehelper.getSameVendorLogoQuantity(samevendorlogokey) + " / " + 12.0D + ")");
            metallicprintedtwillprice += numberofcolors * Double.valueOf((String)this.sqltable.getValue()).doubleValue() / (this.pricehelper.getSameVendorLogoQuantity(samevendorlogokey) / 12.0D);
            
            HiddenEcho("x.) Metallic Printed Twill Price ");
            HiddenEcho(String.valueOf(metallicprintedtwillprice));
            HiddenEcho("<br>");
            
            decorationstotal[j] += metallicprintedtwillprice;
          } else if (this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getName().equals("Sublimation")) {
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'sublimationprintingbylogo'");
            double sublimationprintingbylogo = Double.valueOf((String)this.sqltable.getValue()).doubleValue();
            
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'sublimationmoldcharge'");
            sublimationprintingbylogo += Double.valueOf((String)this.sqltable.getValue()).doubleValue() / (this.pricehelper.getSameVendorLogoQuantity(samevendorlogokey) / 12.0D);
            
            HiddenEcho("x.) Sublimation Price ");
            HiddenEcho(String.valueOf(sublimationprintingbylogo));
            HiddenEcho("<br>");
            
            decorationstotal[j] += sublimationprintingbylogo;
          } else if (this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getName().equals("Sublimation Full Panel")) {
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'sublimationprintingfullpanelbylogo'");
            double sublimationprintingfullpanelbylogo = Double.valueOf((String)this.sqltable.getValue()).doubleValue();
            
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'sublimationmoldcharge'");
            sublimationprintingfullpanelbylogo += Double.valueOf((String)this.sqltable.getValue()).doubleValue() / (this.pricehelper.getSameVendorLogoQuantity(samevendorlogokey) / 12.0D);
            
            HiddenEcho("x.) Sublimation Full Panel Price ");
            HiddenEcho(String.valueOf(sublimationprintingfullpanelbylogo));
            HiddenEcho("<br>");
            
            decorationstotal[j] += sublimationprintingfullpanelbylogo;
          } else if (this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getName().equals("Sublimation Golf Front Full Panel")) {
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'sublimationgolfstylefrontfullpanel'");
            double sublimationgolfstylefrontfullpanel = Double.valueOf((String)this.sqltable.getValue()).doubleValue();
            
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'sublimationmoldcharge'");
            sublimationgolfstylefrontfullpanel += Double.valueOf((String)this.sqltable.getValue()).doubleValue() / (this.pricehelper.getSameVendorLogoQuantity(samevendorlogokey) / 12.0D);
            
            HiddenEcho("x.) Sublimation Full Panel Price ");
            HiddenEcho(String.valueOf(sublimationgolfstylefrontfullpanel));
            HiddenEcho("<br>");
            
            decorationstotal[j] += sublimationgolfstylefrontfullpanel;
          } else if (this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getName().equals("Flock Printed Twill Applique")) {
            int numberofcolors = this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getField1().intValue();
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'flockprintedtwillbycolor'");
            double flockprintedtwillprice = numberofcolors * Double.valueOf((String)this.sqltable.getValue()).doubleValue();
            
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'flockprintedtwilladdcharge'");
            flockprintedtwillprice += Double.valueOf((String)this.sqltable.getValue()).doubleValue();
            
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'printedtwillsetupcharge'");
            flockprintedtwillprice += numberofcolors * Double.valueOf((String)this.sqltable.getValue()).doubleValue() / (this.pricehelper.getSameVendorLogoQuantity(samevendorlogokey) / 12.0D);
            
            HiddenEcho("x.) Flock Printed Twill Price ");
            HiddenEcho(String.valueOf(flockprintedtwillprice));
            HiddenEcho("<br>");
            
            decorationstotal[j] += flockprintedtwillprice;
          } else if (this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getName().equals("Sandwich Woven Insert")) {
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'sandwichwovenstripeinsertaddcharge'");
            double sandwichwovenlabelprice = Double.valueOf((String)this.sqltable.getValue()).doubleValue();
            
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'specialdecorationsetupcharge'");
            sandwichwovenlabelprice += Double.valueOf((String)this.sqltable.getValue()).doubleValue() / (this.pricehelper.getSameVendorLogoQuantity(samevendorlogokey) / 12.0D);
            
            HiddenEcho("x.) Sandwich Woven Label Price ");
            HiddenEcho(String.valueOf(sandwichwovenlabelprice));
            HiddenEcho("<br>");
            
            decorationstotal[j] += sandwichwovenlabelprice;
          } else if (this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getName().equals("Center Sandwich Woven Label")) {
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'centersandwichwovenlabeladdcharge'");
            double centersandwichwovenlabelprice = Double.valueOf((String)this.sqltable.getValue()).doubleValue();
            
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'specialdecorationsetupcharge'");
            centersandwichwovenlabelprice += Double.valueOf((String)this.sqltable.getValue()).doubleValue() / (this.pricehelper.getSameVendorLogoQuantity(samevendorlogokey) / 12.0D);
            
            HiddenEcho("x.) Center Sandwich Woven Label Price ");
            HiddenEcho(String.valueOf(centersandwichwovenlabelprice));
            HiddenEcho("<br>");
            
            decorationstotal[j] += centersandwichwovenlabelprice;
          } else if (this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getName().equals("Crown Woven Button")) {
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'crownwovenbuttonaddcharge'");
            double crownwovenbuttonprice = Double.valueOf((String)this.sqltable.getValue()).doubleValue();
            
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'specialdecorationsetupcharge'");
            crownwovenbuttonprice += Double.valueOf((String)this.sqltable.getValue()).doubleValue() / (this.pricehelper.getSameVendorLogoQuantity(samevendorlogokey) / 12.0D);
            
            HiddenEcho("x.) Crown Woven Button Price ");
            HiddenEcho(String.valueOf(crownwovenbuttonprice));
            HiddenEcho("<br>");
            
            decorationstotal[j] += crownwovenbuttonprice;
          } else if (this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getName().equals("Crown Embossed Button")) {
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'crownembossedbuttonaddcharge'");
            double crownembossedbuttonprice = Double.valueOf((String)this.sqltable.getValue()).doubleValue();
            
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'crownembossedbuttonsetupfee'");
            crownembossedbuttonprice += Double.valueOf((String)this.sqltable.getValue()).doubleValue() / (this.pricehelper.getSameVendorLogoQuantity(samevendorlogokey) / 12.0D);
            
            HiddenEcho("x.) Crown Embossed Button Price ");
            HiddenEcho(String.valueOf(crownembossedbuttonprice));
            HiddenEcho("<br>");
            
            decorationstotal[j] += crownembossedbuttonprice;
          } else if (this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getName().equals("Woven Hang Tag")) {
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'wovenhangtagaddcharge'");
            double wovenhangtagprice = Double.valueOf((String)this.sqltable.getValue()).doubleValue();
            
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'specialdecorationsetupcharge'");
            wovenhangtagprice += Double.valueOf((String)this.sqltable.getValue()).doubleValue() / (this.pricehelper.getSameVendorLogoQuantity(samevendorlogokey) / 12.0D);
            
            HiddenEcho("x.) Woven Hang Tag Price ");
            HiddenEcho(String.valueOf(wovenhangtagprice));
            HiddenEcho("<br>");
            
            decorationstotal[j] += wovenhangtagprice;
          } else if (this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getName().equals("Sweatband Woven Stripe Insert")) {
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'sweatbandwovenstripeinsertaddcharge'");
            double sweatbandwovenstripinsertprice = Double.valueOf((String)this.sqltable.getValue()).doubleValue();
            
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'specialdecorationsetupcharge'");
            sweatbandwovenstripinsertprice += Double.valueOf((String)this.sqltable.getValue()).doubleValue() / (this.pricehelper.getSameVendorLogoQuantity(samevendorlogokey) / 12.0D);
            
            HiddenEcho("x.) Sweatband Woven Stripe Insert Price ");
            HiddenEcho(String.valueOf(sweatbandwovenstripinsertprice));
            HiddenEcho("<br>");
            
            decorationstotal[j] += sweatbandwovenstripinsertprice;
          } else if (this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getName().equals("Custom Inside Woven Label")) {
            if ((this.myorderdata.getSet(i).getLogo(j).getLogoLocation().equals("28")) && (!hadlabel)) {
              this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'custominsidewovenlabeladdcharge'");
              double custominsidewovenlabelprice = Double.valueOf((String)this.sqltable.getValue()).doubleValue();
              
              this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'specialdecorationsetupcharge'");
              custominsidewovenlabelprice += Double.valueOf((String)this.sqltable.getValue()).doubleValue() / (this.pricehelper.getSameVendorLogoQuantity(samevendorlogokey) / 12.0D);
              
              HiddenEcho("x.) Custom Inside Woven Label Price ");
              HiddenEcho(String.valueOf(custominsidewovenlabelprice));
              HiddenEcho("<br>");
              
              labelstotal[j] += custominsidewovenlabelprice;
              hadlabel = true;
            } else {
              this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'custominsidewovenlabeladdcharge'");
              double custominsidewovenlabelprice = Double.valueOf((String)this.sqltable.getValue()).doubleValue();
              
              this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'specialdecorationsetupcharge'");
              custominsidewovenlabelprice += Double.valueOf((String)this.sqltable.getValue()).doubleValue() / (this.pricehelper.getSameVendorLogoQuantity(samevendorlogokey) / 12.0D);
              
              HiddenEcho("x.) Custom Inside Woven Label Price ");
              HiddenEcho(String.valueOf(custominsidewovenlabelprice));
              HiddenEcho("<br>");
              
              decorationstotal[j] += custominsidewovenlabelprice;
            }
          } else if (this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getName().equals("Inside Woven Hang Tag")) {
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'insidewovenhangtagaddprice'");
            double insidewovenhangtagprice = Double.valueOf((String)this.sqltable.getValue()).doubleValue();
            
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'specialdecorationsetupcharge'");
            insidewovenhangtagprice += Double.valueOf((String)this.sqltable.getValue()).doubleValue() / (this.pricehelper.getSameVendorLogoQuantity(samevendorlogokey) / 12.0D);
            
            HiddenEcho("x.) Inside Woven Hang Tag Price ");
            HiddenEcho(String.valueOf(insidewovenhangtagprice));
            HiddenEcho("<br>");
            
            decorationstotal[j] += insidewovenhangtagprice;
          } else if (this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getName().equals("Inner Taping Print")) {
            int numberofcolors = this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getField1().intValue();
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'innertapingprintbycolor'");
            double innertapingprintprice = numberofcolors * Double.valueOf((String)this.sqltable.getValue()).doubleValue();
            
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'specialdecorationsetupcharge'");
            innertapingprintprice += numberofcolors * Double.valueOf((String)this.sqltable.getValue()).doubleValue() / (this.pricehelper.getSameVendorLogoQuantity(samevendorlogokey) / 12.0D);
            
            HiddenEcho("x.) Inner Taping Print Price ");
            HiddenEcho(String.valueOf(innertapingprintprice));
            HiddenEcho("<br>");
            
            decorationstotal[j] += innertapingprintprice;
          } else if (this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getName().equals("Inner Arch Taping Print")) {
            int numberofcolors = this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getField1().intValue();
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'innerarctapingprintbycolor'");
            double innerarctapingprintprice = numberofcolors * Double.valueOf((String)this.sqltable.getValue()).doubleValue();
            
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'specialdecorationsetupcharge'");
            innerarctapingprintprice += numberofcolors * Double.valueOf((String)this.sqltable.getValue()).doubleValue() / (this.pricehelper.getSameVendorLogoQuantity(samevendorlogokey) / 12.0D);
            
            HiddenEcho("x.) Inner Arch Taping Print Price ");
            HiddenEcho(String.valueOf(innerarctapingprintprice));
            HiddenEcho("<br>");
            
            decorationstotal[j] += innerarctapingprintprice;
          } else if (this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getName().equals("Custom Inside Print Label")) {
            if ((this.myorderdata.getSet(i).getLogo(j).getLogoLocation().equals("28")) && (!hadlabel)) {
              int numberofcolors = this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getField1().intValue();
              this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'printlabelbycolor'");
              
              HiddenEcho(numberofcolors * Double.valueOf((String)this.sqltable.getValue()).doubleValue() + " = " + numberofcolors + " * " + Double.valueOf((String)this.sqltable.getValue()) + " <BR>");
              double printlabelprice = numberofcolors * Double.valueOf((String)this.sqltable.getValue()).doubleValue();
              
              this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'specialdecorationsetupcharge'");
              
              HiddenEcho(printlabelprice + numberofcolors * Double.valueOf((String)this.sqltable.getValue()).doubleValue() / (this.pricehelper.getSameVendorLogoQuantity(samevendorlogokey) / 12.0D) + " = " + printlabelprice + " + (" + numberofcolors + " * " + Double.valueOf((String)this.sqltable.getValue()) + ") / (" + this.pricehelper.getSameVendorLogoQuantity(samevendorlogokey) + "/" + 12.0D + ") <BR>");
              printlabelprice += numberofcolors * Double.valueOf((String)this.sqltable.getValue()).doubleValue() / (this.pricehelper.getSameVendorLogoQuantity(samevendorlogokey) / 12.0D);
              
              HiddenEcho("x.) Custom Inside Print Label Price ");
              HiddenEcho(String.valueOf(printlabelprice));
              HiddenEcho("<br>");
              
              labelstotal[j] += printlabelprice;
              hadlabel = true;
            } else {
              int numberofcolors = this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getField1().intValue();
              this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'printlabelbycolor'");
              
              HiddenEcho(numberofcolors * Double.valueOf((String)this.sqltable.getValue()).doubleValue() + " = " + numberofcolors + " * " + Double.valueOf((String)this.sqltable.getValue()) + " <BR>");
              double printlabelprice = numberofcolors * Double.valueOf((String)this.sqltable.getValue()).doubleValue();
              
              this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'specialdecorationsetupcharge'");
              
              HiddenEcho(printlabelprice + numberofcolors * Double.valueOf((String)this.sqltable.getValue()).doubleValue() / (this.pricehelper.getSameVendorLogoQuantity(samevendorlogokey) / 12.0D) + " = " + printlabelprice + " + (" + numberofcolors + " * " + Double.valueOf((String)this.sqltable.getValue()) + ") / (" + this.pricehelper.getSameVendorLogoQuantity(samevendorlogokey) + "/" + 12.0D + ") <BR>");
              printlabelprice += numberofcolors * Double.valueOf((String)this.sqltable.getValue()).doubleValue() / (this.pricehelper.getSameVendorLogoQuantity(samevendorlogokey) / 12.0D);
              
              HiddenEcho("x.) Custom Inside Print Label Price ");
              HiddenEcho(String.valueOf(printlabelprice));
              HiddenEcho("<br>");
              
              decorationstotal[j] += printlabelprice;
            }
          } else if (this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getName().equals("Cap Hang Tag")) {
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'caphangtagaddcharge'");
            double caphangtagprice = Double.valueOf((String)this.sqltable.getValue()).doubleValue();
            
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'caphangtagsetupcharge'");
            caphangtagprice += Double.valueOf((String)this.sqltable.getValue()).doubleValue() / (this.pricehelper.getSameVendorLogoQuantity(samevendorlogokey) / 12.0D);
            
            HiddenEcho("x.) Cap Hang Tag Price ");
            HiddenEcho(String.valueOf(caphangtagprice));
            HiddenEcho("<br>");
            
            decorationstotal[j] += caphangtagprice;
          } else if (this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getName().equals("Embossed Buckle")) {
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'embossedbucklesetupcharge'");
            double embossedbuckleprice = Double.valueOf((String)this.sqltable.getValue()).doubleValue() / (this.pricehelper.getSameVendorLogoQuantity(samevendorlogokey) / 12.0D);
            
            HiddenEcho("x.) Embossed Buckle Price ");
            HiddenEcho(String.valueOf(embossedbuckleprice));
            HiddenEcho("<br>");
            
            decorationstotal[j] += embossedbuckleprice;
          } else if (this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getName().equals("Sonic Welding")) {
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'sonicweldingaddcharge'");
            double sonicweldingprice = Double.valueOf((String)this.sqltable.getValue()).doubleValue();
            
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'sonicweldingsetupcharge'");
            sonicweldingprice += Double.valueOf((String)this.sqltable.getValue()).doubleValue() / (this.pricehelper.getSameVendorLogoQuantity(samevendorlogokey) / 12.0D);
            
            HiddenEcho("x.) Sonic Welding Price ");
            HiddenEcho(String.valueOf(sonicweldingprice));
            HiddenEcho("<br>");
            
            decorationstotal[j] += sonicweldingprice;
          } else if (this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getName().equals("Rhinestones Design Transfer")) {
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'rhinestonesdesigntransferaddcharge'");
            double rhinestonesdesigntransferprice = Double.valueOf((String)this.sqltable.getValue()).doubleValue();
            
            HiddenEcho("x.) Rhinestones Design Transfer Price ");
            HiddenEcho(String.valueOf(rhinestonesdesigntransferprice));
            HiddenEcho("<br>");
            
            decorationstotal[j] += rhinestonesdesigntransferprice;
          } else if (this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getName().equals("Loose Rhinestones")) {
            int numberofstones = this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getField1().intValue();
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'lossrhinestonesbystone'");
            double lossrhinestonesprice = numberofstones * Double.valueOf((String)this.sqltable.getValue()).doubleValue();
            
            HiddenEcho("x.) Loose Rhinestones Price ");
            HiddenEcho(String.valueOf(lossrhinestonesprice));
            HiddenEcho("<br>");
            
            decorationstotal[j] += lossrhinestonesprice;
          } else if (this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getName().equals("Sequins")) {
            int numberofamount = this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getField1().intValue();
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'sequinsbyamount'");
            double sequinsprice = numberofamount * Double.valueOf((String)this.sqltable.getValue()).doubleValue();
            
            HiddenEcho("x.) Sequins Price ");
            HiddenEcho(String.valueOf(sequinsprice));
            HiddenEcho("<br>");
            
            decorationstotal[j] += sequinsprice;
          } else if (this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getName().equals("Metal Stud")) {
            int numberofamount = this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getField1().intValue();
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'metalstudbystud'");
            double metalstudprice = numberofamount * Double.valueOf((String)this.sqltable.getValue()).doubleValue();
            
            HiddenEcho("x.) Metal Stud Price ");
            HiddenEcho(String.valueOf(metalstudprice));
            HiddenEcho("<br>");
            
            decorationstotal[j] += metalstudprice;
          } else if (this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getName().equals("Emblem Patch")) {
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'emblempatchaddcharge'");
            double emblempatchprice = Double.valueOf((String)this.sqltable.getValue()).doubleValue();
            
            HiddenEcho("x.) Emblem Patch Price ");
            HiddenEcho(String.valueOf(emblempatchprice));
            HiddenEcho("<br>");
            
            decorationstotal[j] += emblempatchprice;
          } else if (this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getName().equals("Woven Patch")) {
            double wovenpatchprice = 0.0D;
            double width = this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getField1().doubleValue();
            double height = this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getField2().doubleValue();
            double squarefeet = width * height;
            
            boolean foundmatch = false;
            this.sqltable.makeTable("SELECT * FROM `overseas_price_woven_patch` ORDER BY `size` ASC");
            for (int l = 0; l < this.sqltable.getTable().size(); l++) {
              if ((!foundmatch) && 
                (squarefeet < ((Double)((FastMap)this.sqltable.getTable().get(l)).get("size")).doubleValue())) {
                foundmatch = true;
                wovenpatchprice = Double.valueOf((String)((FastMap)this.sqltable.getTable().get(l)).get(String.valueOf(this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber()))).doubleValue();
              }
            }
            

            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'wovenpatchsetupcharge'");
            wovenpatchprice += Double.valueOf((String)this.sqltable.getValue()).doubleValue() / (this.pricehelper.getSameVendorLogoQuantity(samevendorlogokey) / 12.0D);
            
            if (!foundmatch) {
              throw new Exception("Woven Patch Is Too Big");
            }
            HiddenEcho("x.) Woven Patch Price ");
            HiddenEcho(String.valueOf(wovenpatchprice));
            HiddenEcho("<br>");
            
            decorationstotal[j] += wovenpatchprice;
          } else if (this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getName().equals("Metal Patch")) {
            double metalpatchprice = 0.0D;
            double width = this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getField1().doubleValue();
            double height = this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getField2().doubleValue();
            double squarefeet = width * height;
            
            boolean foundmatch = false;
            this.sqltable.makeTable("SELECT * FROM `overseas_price_metal_patch` ORDER BY `size` ASC");
            for (int l = 0; l < this.sqltable.getTable().size(); l++) {
              if ((!foundmatch) && 
                (squarefeet < ((Double)((FastMap)this.sqltable.getTable().get(l)).get("size")).doubleValue())) {
                foundmatch = true;
                metalpatchprice = Double.valueOf((String)((FastMap)this.sqltable.getTable().get(l)).get(String.valueOf(this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber()))).doubleValue();
              }
            }
            

            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'metalpatchsetupcharge'");
            metalpatchprice += Double.valueOf((String)this.sqltable.getValue()).doubleValue() / (this.pricehelper.getSameVendorLogoQuantity(samevendorlogokey) / 12.0D);
            
            if (!foundmatch) {
              throw new Exception("Metal Patch Is Too Big");
            }
            HiddenEcho("x.) Metal Patch Price ");
            HiddenEcho(String.valueOf(metalpatchprice));
            HiddenEcho("<br>");
            
            decorationstotal[j] += metalpatchprice;
          } else if (this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getName().equals("Screen Print")) {
            int numberofcolors = this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getField1().intValue();
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'screenprintbycolors'");
            double screenprintprice = 0.0D;
            try {
              screenprintprice = numberofcolors * Double.valueOf((String)this.sqltable.getValue()).doubleValue();
              if (this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() == 5) {
                screenprintprice += Double.valueOf((String)this.sqltable.getValue()).doubleValue();
              }
            } catch (Exception e) {
              throw new Exception("Vendor: " + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + " screenprintbycolors is " + this.sqltable.getValue());
            }
            
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'screenprintsetupcharge'");
            screenprintprice += numberofcolors * Double.valueOf((String)this.sqltable.getValue()).doubleValue() / (this.pricehelper.getSameVendorLogoQuantity(samevendorlogokey) / 12.0D);
            
            HiddenEcho("x.) Screen Print Price ");
            HiddenEcho(String.valueOf(screenprintprice));
            HiddenEcho("<br>");
            
            decorationstotal[j] += screenprintprice;
          } else if (this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getName().equals("Screen Print Full Panel")) {
            int numberofcolors = this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getField1().intValue();
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'screenprintfullpanelbycolors'");
            double screenprintprice = numberofcolors * Double.valueOf((String)this.sqltable.getValue()).doubleValue();
            if (this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() == 5) {
              screenprintprice += Double.valueOf((String)this.sqltable.getValue()).doubleValue();
            }
            
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'screenprintsetupcharge'");
            screenprintprice += numberofcolors * Double.valueOf((String)this.sqltable.getValue()).doubleValue() / (this.pricehelper.getSameVendorLogoQuantity(samevendorlogokey) / 12.0D);
            
            HiddenEcho("x.) Screen Print Full Panel Price ");
            HiddenEcho(String.valueOf(screenprintprice));
            HiddenEcho("<br>");
            
            decorationstotal[j] += screenprintprice;
          } else if (this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getName().equals("Screen Print Golf Style Front Full Panel")) {
            int numberofcolors = this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getField1().intValue();
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'screenprintgolfstylefrontfullpanelbycolors'");
            double screenprintprice = numberofcolors * Double.valueOf((String)this.sqltable.getValue()).doubleValue();
            if (this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() == 5) {
              screenprintprice += Double.valueOf((String)this.sqltable.getValue()).doubleValue();
            }
            
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'screenprintsetupcharge'");
            screenprintprice += numberofcolors * Double.valueOf((String)this.sqltable.getValue()).doubleValue() / (this.pricehelper.getSameVendorLogoQuantity(samevendorlogokey) / 12.0D);
            
            HiddenEcho("x.) Screen Print Golf Style Front Full Panel ");
            HiddenEcho(String.valueOf(screenprintprice));
            HiddenEcho("<br>");
            
            decorationstotal[j] += screenprintprice;
          } else if (this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getName().equals("Puff Print")) {
            int numberofcolors = this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getField1().intValue();
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'puffprintbycolors'");
            double puffprintprice = numberofcolors * Double.valueOf((String)this.sqltable.getValue()).doubleValue();
            
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'puffprintsetupcharge'");
            puffprintprice += numberofcolors * Double.valueOf((String)this.sqltable.getValue()).doubleValue() / (this.pricehelper.getSameVendorLogoQuantity(samevendorlogokey) / 12.0D);
            
            HiddenEcho("x.) Puff Print Price ");
            HiddenEcho(String.valueOf(puffprintprice));
            HiddenEcho("<br>");
            
            decorationstotal[j] += puffprintprice;
          } else if (this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getName().equals("Puff Print Full Panel")) {
            int numberofcolors = this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getField1().intValue();
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'puffprintfullpanelbycolors'");
            double puffprintprice = numberofcolors * Double.valueOf((String)this.sqltable.getValue()).doubleValue();
            
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'puffprintsetupcharge'");
            puffprintprice += numberofcolors * Double.valueOf((String)this.sqltable.getValue()).doubleValue() / (this.pricehelper.getSameVendorLogoQuantity(samevendorlogokey) / 12.0D);
            
            HiddenEcho("x.) Puff Print Full Panel Price ");
            HiddenEcho(String.valueOf(puffprintprice));
            HiddenEcho("<br>");
            
            decorationstotal[j] += puffprintprice;
          } else if (this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getName().equals("Puff Print Golf Style Front Full Panel")) {
            int numberofcolors = this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getField1().intValue();
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'puffprintgolfstylefrontfullpanelbycolors'");
            double puffprintprice = numberofcolors * Double.valueOf((String)this.sqltable.getValue()).doubleValue();
            
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'puffprintsetupcharge'");
            puffprintprice += numberofcolors * Double.valueOf((String)this.sqltable.getValue()).doubleValue() / (this.pricehelper.getSameVendorLogoQuantity(samevendorlogokey) / 12.0D);
            
            HiddenEcho("x.) Puff Print Golf Style Front Full Panel Price ");
            HiddenEcho(String.valueOf(puffprintprice));
            HiddenEcho("<br>");
            
            decorationstotal[j] += puffprintprice;
          } else if (this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getName().equals("Metallic Print")) {
            int numberofcolors = this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getField1().intValue();
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'metallicprintbycolors'");
            double metallicprintprice = numberofcolors * Double.valueOf((String)this.sqltable.getValue()).doubleValue();
            
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'screenprintsetupcharge'");
            metallicprintprice += numberofcolors * Double.valueOf((String)this.sqltable.getValue()).doubleValue() / (this.pricehelper.getSameVendorLogoQuantity(samevendorlogokey) / 12.0D);
            
            HiddenEcho("x.) Metallic Print Price ");
            HiddenEcho(String.valueOf(metallicprintprice));
            HiddenEcho("<br>");
            
            decorationstotal[j] += metallicprintprice;
          } else if (this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getName().equals("Metallic Print Full Panel")) {
            int numberofcolors = this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getField1().intValue();
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'metallicprintfullpanelbycolors'");
            double metallicprintprice = numberofcolors * Double.valueOf((String)this.sqltable.getValue()).doubleValue();
            
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'screenprintsetupcharge'");
            metallicprintprice += numberofcolors * Double.valueOf((String)this.sqltable.getValue()).doubleValue() / (this.pricehelper.getSameVendorLogoQuantity(samevendorlogokey) / 12.0D);
            
            HiddenEcho("x.) Metallic Print Full Panel Price ");
            HiddenEcho(String.valueOf(metallicprintprice));
            HiddenEcho("<br>");
            
            decorationstotal[j] += metallicprintprice;
          } else if (this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getName().equals("Metallic Print Golf Style Front Full Panel")) {
            int numberofcolors = this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getField1().intValue();
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'metallicprintgolfstylefrontfullpanelbycolors'");
            double metallicprintprice = numberofcolors * Double.valueOf((String)this.sqltable.getValue()).doubleValue();
            
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'screenprintsetupcharge'");
            metallicprintprice += numberofcolors * Double.valueOf((String)this.sqltable.getValue()).doubleValue() / (this.pricehelper.getSameVendorLogoQuantity(samevendorlogokey) / 12.0D);
            
            HiddenEcho("x.) Metallic Print Golf Style Front Full Panel Price ");
            HiddenEcho(String.valueOf(metallicprintprice));
            HiddenEcho("<br>");
            
            decorationstotal[j] += metallicprintprice;
          } else if (this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getName().equals("Flock Screen Print")) {
            int numberofcolors = this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getField1().intValue();
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'flockprintbycolors'");
            double flockscreneprintprice = numberofcolors * Double.valueOf((String)this.sqltable.getValue()).doubleValue();
            
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'screenprintsetupcharge'");
            flockscreneprintprice += numberofcolors * Double.valueOf((String)this.sqltable.getValue()).doubleValue() / (this.pricehelper.getSameVendorLogoQuantity(samevendorlogokey) / 12.0D);
            
            HiddenEcho("x.) Flock Screen Print Price ");
            HiddenEcho(String.valueOf(flockscreneprintprice));
            HiddenEcho("<br>");
            
            decorationstotal[j] += flockscreneprintprice;
          } else {
            double fobprice = this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getField1() != null ? this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getField1().doubleValue() : 0.0D;
            double setupfee = this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getField2() != null ? this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getField2().doubleValue() : 0.0D;
            double otherprice = fobprice + setupfee / (this.pricehelper.getSameVendorLogoQuantity(samevendorlogokey) / 12.0D);
            
            HiddenEcho("x.) Other Price ");
            HiddenEcho(String.valueOf(otherprice));
            HiddenEcho("<br>");
            
            decorationstotal[j] += otherprice;
          }
        }
        































        HiddenEcho("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Logo " + (j + 1) + " Total Price: " + decorationstotal[j] + "<br>");
      }
      HiddenEcho("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--------------------------------------------------------------------<br>");
      
      double capparts = 0.0D;
      
      if ((this.myorderdata.getSet(i).getItem(0).getStyleType() != StyleInformationData.StyleType.OVERSEAS_CUSTOMSTYLE) && (this.myorderdata.getSet(i).getItem(0).getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO) && (this.myorderdata.getSet(i).getItem(0).getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO_SHIRTS))
      {
        if ((this.myorderdata.getSet(i).getItem(0).getVisorStyleNumber() != null) && (!this.myorderdata.getSet(i).getItem(0).getVisorStyleNumber().equals("")) && (!this.myorderdata.getSet(i).getItem(0).getVisorStyleNumber().equals("Default")))
        {
          if (this.myorderdata.getSet(i).getItem(0).getStyleType() == StyleInformationData.StyleType.OVERSEAS_INSTOCK) {
            this.sqltable.makeTable("SELECT `Original Visor` FROM `styles_overseasinstock` WHERE `Backup VENDOR` = '" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "' AND `STYLE NUMBER` = '" + this.myorderdata.getSet(i).getItem(0).getStyleNumber() + "' LIMIT 1");
            String orgvisor = (String)this.sqltable.getValue();
            HiddenEcho("&nbsp;&nbsp;&nbsp;Original Visor " + orgvisor + "<br>");
            if (this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_styles_visor` WHERE `style` = '" + orgvisor + "' LIMIT 1").booleanValue()) {
              double vosorprice = 0.0D;
              try {
                vosorprice = Double.valueOf((String)this.sqltable.getValue()).doubleValue();
              }
              catch (Exception localException1) {}
              
              HiddenEcho("&nbsp;&nbsp;&nbsp;Original Visor Price " + vosorprice + "<br>");
              capparts -= vosorprice;
            }
          }
          

          this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_styles_visor` WHERE `style` = '" + this.myorderdata.getSet(i).getItem(0).getVisorStyleNumber() + "' LIMIT 1");
          double visorprice = Double.valueOf((String)this.sqltable.getValue()).doubleValue();
          capparts += visorprice;
          
          HiddenEcho("&nbsp;&nbsp;&nbsp;Visor Style " + this.myorderdata.getSet(i).getItem(0).getVisorStyleNumber() + "<br>");
          HiddenEcho("&nbsp;&nbsp;&nbsp;Visor Price " + visorprice + "<br>");
          HiddenEcho("&nbsp;&nbsp;&nbsp;Current Cap Parts Price " + capparts + "<br>");
        }
        if ((this.myorderdata.getSet(i).getItem(0).getClosureStyleNumber() != null) && (!this.myorderdata.getSet(i).getItem(0).getClosureStyleNumber().equals("")) && (!this.myorderdata.getSet(i).getItem(0).getClosureStyleNumber().equals("Default")))
        {
          if (this.myorderdata.getSet(i).getItem(0).getStyleType() == StyleInformationData.StyleType.OVERSEAS_INSTOCK) {
            this.sqltable.makeTable("SELECT `Original Closure` FROM `styles_overseasinstock` WHERE `Backup VENDOR` = '" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "' AND `STYLE NUMBER` = '" + this.myorderdata.getSet(i).getItem(0).getStyleNumber() + "' LIMIT 1");
            String orgclosure = (String)this.sqltable.getValue();
            HiddenEcho("&nbsp;&nbsp;&nbsp;Original Closure " + orgclosure + "<br>");
            if (this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_styles_closure` WHERE `style` = '" + orgclosure + "' LIMIT 1").booleanValue()) {
              double closureprice = 0.0D;
              try {
                closureprice = Double.valueOf((String)this.sqltable.getValue()).doubleValue();
              }
              catch (Exception localException2) {}
              
              HiddenEcho("&nbsp;&nbsp;&nbsp;Original Closure Price " + closureprice + "<br>");
              capparts -= closureprice;
            }
          }
          

          this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_styles_closure` WHERE `style` = '" + this.myorderdata.getSet(i).getItem(0).getClosureStyleNumber() + "' LIMIT 1");
          double closureprice = Double.valueOf((String)this.sqltable.getValue()).doubleValue();
          capparts += closureprice;
          
          HiddenEcho("&nbsp;&nbsp;&nbsp;Closure Style " + this.myorderdata.getSet(i).getItem(0).getClosureStyleNumber() + "<br>");
          HiddenEcho("&nbsp;&nbsp;&nbsp;Closure Price " + closureprice + "<br>");
          HiddenEcho("&nbsp;&nbsp;&nbsp;Current Cap Parts Price " + capparts + "<br>");
        }
        if ((this.myorderdata.getSet(i).getItem(0).getEyeletStyleNumber() != null) && (!this.myorderdata.getSet(i).getItem(0).getEyeletStyleNumber().equals("")) && (!this.myorderdata.getSet(i).getItem(0).getEyeletStyleNumber().equals("Default")))
        {
          if (this.myorderdata.getSet(i).getItem(0).getStyleType() == StyleInformationData.StyleType.OVERSEAS_INSTOCK) {
            this.sqltable.makeTable("SELECT `Original Eyelet` FROM `styles_overseasinstock` WHERE `Backup VENDOR` = '" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "' AND `STYLE NUMBER` = '" + this.myorderdata.getSet(i).getItem(0).getStyleNumber() + "' LIMIT 1");
            String orgeyelet = (String)this.sqltable.getValue();
            HiddenEcho("&nbsp;&nbsp;&nbsp;Original Eyelet " + orgeyelet + "<br>");
            if (this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_styles_eyelet` WHERE `style` = '" + orgeyelet + "' LIMIT 1").booleanValue()) {
              double eyeletprice = 0.0D;
              try {
                eyeletprice = Double.valueOf((String)this.sqltable.getValue()).doubleValue();
              }
              catch (Exception localException3) {}
              
              HiddenEcho("&nbsp;&nbsp;&nbsp;Original Eyelet Price " + eyeletprice + "<br>");
              capparts -= eyeletprice;
            }
          }
          

          this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_styles_eyelet` WHERE `style` = '" + this.myorderdata.getSet(i).getItem(0).getEyeletStyleNumber() + "' LIMIT 1");
          double eyeletprice = Double.valueOf((String)this.sqltable.getValue()).doubleValue();
          capparts += eyeletprice;
          
          HiddenEcho("&nbsp;&nbsp;&nbsp;Eyelet Style " + this.myorderdata.getSet(i).getItem(0).getEyeletStyleNumber() + "<br>");
          HiddenEcho("&nbsp;&nbsp;&nbsp;Eyelet Price " + eyeletprice + "<br>");
          HiddenEcho("&nbsp;&nbsp;&nbsp;Current Cap Parts Price " + capparts + "<br>");
        }
        if ((this.myorderdata.getSet(i).getItem(0).getSweatbandStyleNumber() != null) && (!this.myorderdata.getSet(i).getItem(0).getSweatbandStyleNumber().equals("")) && (!this.myorderdata.getSet(i).getItem(0).getSweatbandStyleNumber().equals("Default"))) {
          if (this.myorderdata.getSet(i).getItem(0).getStyleType() == StyleInformationData.StyleType.OVERSEAS_INSTOCK) {
            this.sqltable.makeTable("SELECT `Original Sweatband` FROM `styles_overseasinstock` WHERE `Backup VENDOR` = '" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "' AND `STYLE NUMBER` = '" + this.myorderdata.getSet(i).getItem(0).getStyleNumber() + "' LIMIT 1");
            String orgsweatband = (String)this.sqltable.getValue();
            HiddenEcho("&nbsp;&nbsp;&nbsp;Original Sweatband " + orgsweatband + "<br>");
            if (this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_styles_sweatband` WHERE `style` = '" + orgsweatband + "' LIMIT 1").booleanValue()) {
              double sweatbandprice = 0.0D;
              try {
                sweatbandprice = Double.valueOf((String)this.sqltable.getValue()).doubleValue();
              }
              catch (Exception localException4) {}
              
              HiddenEcho("&nbsp;&nbsp;&nbsp;Original Sweatband Price " + sweatbandprice + "<br>");
              capparts -= sweatbandprice;
            }
          }
          

          this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_styles_sweatband` WHERE `style` = '" + this.myorderdata.getSet(i).getItem(0).getSweatbandStyleNumber() + "'");
          double sweatbandprice = Double.valueOf((String)this.sqltable.getValue()).doubleValue();
          capparts += sweatbandprice;
          
          HiddenEcho("&nbsp;&nbsp;&nbsp;Sweatband Style " + this.myorderdata.getSet(i).getItem(0).getSweatbandStyleNumber() + "<br>");
          HiddenEcho("&nbsp;&nbsp;&nbsp;Sweatband Price " + sweatbandprice + "<br>");
          HiddenEcho("&nbsp;&nbsp;&nbsp;Current Cap Parts Price " + capparts + "<br>");
        }
      }
      
      double completestyleprice = styleprice + quantitybreakdownfee + capparts;
      
      HiddenEcho("&nbsp;&nbsp;&nbsp;Complete Blank Cap Price Before Rounding: " + completestyleprice + " = " + styleprice + "+" + quantitybreakdownfee + "+" + capparts + "<br>");
      HiddenEcho("&nbsp;&nbsp;&nbsp;" + completestyleprice / 12.0D + " = " + completestyleprice + "/12.0<br>");
      completestyleprice /= 12.0D;
      

      HiddenEcho("&nbsp;&nbsp;&nbsp;Special Dye Cost Into Item: " + (completestyleprice + specialdyecost.getVenDyeCost(i)) + " += " + specialdyecost.getVenDyeCost(i) + "<br>");
      completestyleprice += specialdyecost.getVenDyeCost(i);
      
      HiddenEcho("&nbsp;&nbsp;&nbsp;" + Math.ceil((float)(completestyleprice * 100.0D)) / 100.0D + "<br>");
      completestyleprice = Math.ceil((float)(completestyleprice * 100.0D)) / 100.0D;
      
      HiddenEcho("&nbsp;&nbsp;&nbsp;" + completestyleprice * 12.0D + " = " + completestyleprice + "*12.0<br>");
      completestyleprice *= 12.0D;
      HiddenEcho("&nbsp;&nbsp;&nbsp;Complete Blank Cap Price After Rounding: " + completestyleprice + "<br>");
      
      double customlabelextracost = 0.0D;
      double totallogocost = 0.0D;
      for (int j = 0; j < decorationstotal.length; j++) {
        HiddenEcho("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Logo " + (j + 1) + " Price Before Rounding: " + decorationstotal[j] + "<br>");
        HiddenEcho("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + decorationstotal[j] / 12.0D + "= " + decorationstotal[j] + " + /12.0<br>");
        decorationstotal[j] /= 12.0D;
        HiddenEcho("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + Math.ceil((float)(decorationstotal[j] * 100.0D)) / 100.0D + "<br>");
        decorationstotal[j] = (Math.ceil((float)(decorationstotal[j] * 100.0D)) / 100.0D);
        HiddenEcho("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + decorationstotal[j] * 12.0D + "= " + decorationstotal[j] + "*12<br>");
        decorationstotal[j] *= 12.0D;
        HiddenEcho("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Logo " + (j + 1) + " Price After Rounding: " + decorationstotal[j] + "<br>");
        totallogocost += decorationstotal[j];
        
        labelstotal[j] /= 12.0D;
        labelstotal[j] = (Math.ceil((float)(labelstotal[j] * 100.0D)) / 100.0D);
        labelstotal[j] *= 12.0D;
        customlabelextracost += labelstotal[j];
      }
      HiddenEcho("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Extra Label Cost For Customers: " + customlabelextracost + "<br>");
      
      double vendorcharge = completestyleprice + totallogocost;
      HiddenEcho("&nbsp;&nbsp;&nbsp;Vendor Charge " + vendorcharge + " = " + completestyleprice + "+" + totallogocost + "<br>");
      

      if (this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() == 6) {
        if (this.myorderdata.getSet(i).getItem(0).getQuantity().intValue() < 288) {
          if (vendorcharge < 26.4D) {
            vendorcharge = 26.4D;
            HiddenEcho("&nbsp;&nbsp;&nbsp;Vendor Charge " + vendorcharge + " = 26.4");
          }
        } else if (this.myorderdata.getSet(i).getItem(0).getQuantity().intValue() < 576) {
          if (vendorcharge < 24.0D) {
            vendorcharge = 24.0D;
            HiddenEcho("&nbsp;&nbsp;&nbsp;Vendor Charge " + vendorcharge + " = 24.0");
          }
        }
        else if (vendorcharge < 22.2D) {
          vendorcharge = 22.2D;
          HiddenEcho("&nbsp;&nbsp;&nbsp;Vendor Charge " + vendorcharge + " = 22.2");
        }
      }
      


      HiddenEcho("&nbsp;&nbsp;&nbsp;Vendor Charge With Minimum Charge " + vendorcharge + "<br>");
      

      completestyleprice = vendorcharge - totallogocost;
      
      vendoritemdozcost[i] = vendorcharge;
      
      vendortotalarray[Integer.valueOf(this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber()).intValue()] += vendorcharge * this.myorderdata.getSet(i).getItem(0).getQuantity().intValue() / 12.0D;
      
      for (int j = 0; j < decorationstotal.length; j++) {
        HiddenEcho("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Logo " + (j + 1) + " Adding label cost back in to logo cost:<br>");
        HiddenEcho("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + (decorationstotal[j] + labelstotal[j]) + " = " + decorationstotal[j] + " + " + labelstotal[j] + "<BR>");
        decorationstotal[j] += labelstotal[j];
      }
      
      for (int j = 0; j < decorationstotal.length; j++)
      {
        HiddenEcho("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Logo " + (j + 1) + " Upcharge:<br>");
        for (int k = 0; k < this.myorderdata.getSet(i).getLogo(j).getDecorationCount().intValue(); k++)
        {
          if (this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getName().equals("Flat Embroidery")) {
            double embprice = 0.0D;
            int flatstitch = this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getField1().intValue();
            int metallicstitch = this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getField2().intValue();
            
            if (flatstitch + metallicstitch < 3000) {
              flatstitch = 3000 - metallicstitch;
            }
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'Upcharge Flat Embriodery (OTTO)' LIMIT 1");
            double flatembcost = Double.valueOf((String)this.sqltable.getValue()).doubleValue();
            
            HiddenEcho("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + (embprice + flatstitch / 1000.0D * flatembcost) + " = " + embprice + " + " + flatstitch + "/1000.0 * " + flatembcost + "<br>");
            embprice += flatstitch / 1000.0D * flatembcost;
            HiddenEcho("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + (decorationstotal[j] + embprice) + " = " + decorationstotal[j] + " + " + embprice + "<br>");
            

            decorationstotal[j] += embprice;
          } else if (this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getName().equals("3D Embroidery")) {
            double embprice = 0.0D;
            int stitch3d = this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getField1().intValue();
            int metallicstitch3d = this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getField2().intValue();
            int flatstitch = this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getField3().intValue();
            int metallicstitch = this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getField4().intValue();
            
            if (flatstitch + metallicstitch + metallicstitch3d + stitch3d < 3000) {
              flatstitch = 3000 - metallicstitch - metallicstitch3d - stitch3d;
            }
            this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'Upcharge Flat Embriodery (OTTO)' LIMIT 1");
            double flatembcost = Double.valueOf((String)this.sqltable.getValue()).doubleValue();
            HiddenEcho("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + (embprice + flatstitch / 1000.0D * flatembcost) + " = " + embprice + " + " + flatstitch + "/1000.0 * " + flatembcost + "<br>");
            embprice += flatstitch / 1000.0D * flatembcost;
            HiddenEcho("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + (decorationstotal[j] + embprice) + " = " + decorationstotal[j] + " + " + embprice + "<br>");
            decorationstotal[j] += embprice;
          }
        }
      }
      


      completestyleprice /= 12.0D;
      completestyleprice -= specialdyecost.getVenDyeCost(i);
      completestyleprice = Math.ceil((float)(completestyleprice * 100.0D)) / 100.0D;
      completestyleprice *= 12.0D;
      HiddenEcho("&nbsp;&nbsp;&nbsp;Special Dye Cost Remove From Item: " + completestyleprice + "<br>");
      
      HiddenEcho("&nbsp;&nbsp;&nbsp;Exchange Rate " + exchangerate + "<br>");
      HiddenEcho("&nbsp;&nbsp;&nbsp;Complete Blank Cap Price After Exchange Rate " + completestyleprice * currentfactroyexchangerate / exchangerate + " = " + completestyleprice + " * " + currentfactroyexchangerate + " / " + exchangerate + "<br>");
      
      completestyleprice = completestyleprice * currentfactroyexchangerate / exchangerate;
      
      double totallogocostwithexchange = 0.0D;
      for (int j = 0; j < decorationstotal.length; j++) {
        HiddenEcho("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Logo " + (j + 1) + " Price After Exchange Rate " + decorationstotal[j] * currentfactroyexchangerate / exchangerate + " = " + decorationstotal[j] + " * " + currentfactroyexchangerate + " / " + exchangerate + "<br>");
        decorationstotal[j] = (decorationstotal[j] * currentfactroyexchangerate / exchangerate);
        totallogocostwithexchange += decorationstotal[j];
      }
      
      HiddenEcho("&nbsp;&nbsp;&nbsp;Exchange Rate Charge " + (completestyleprice + totallogocostwithexchange) + " = " + completestyleprice + " + " + totallogocostwithexchange + "<br>");
      


      double rate = 0.0D;
      double otherfees = 0.0D;
      double oceanfreightfee = 0.0D;
      if (this.myorderdata.getSet(i).getItem(0).getStyleType() == StyleInformationData.StyleType.OVERSEAS_INSTOCK) {
        if ((this.myorderdata.getSet(i).getItem(0).getVendorNumber() != null) && (!this.myorderdata.getSet(i).getItem(0).getVendorNumber().equals("")) && (!this.myorderdata.getSet(i).getItem(0).getVendorNumber().equals("Default"))) {
          this.sqltable.makeTable("SELECT * FROM `styles_overseasinstock` WHERE `STYLE NUMBER` = '" + this.myorderdata.getSet(i).getItem(0).getStyleNumber() + "' AND `Backup VENDOR` = '" + this.myorderdata.getSet(i).getItem(0).getVendorNumber() + "' LIMIT 1 ");
        } else {
          this.sqltable.makeTable("SELECT `Primary VENDOR` FROM `styles_overseasinstock` WHERE `STYLE NUMBER` = '" + this.myorderdata.getSet(i).getItem(0).getStyleNumber() + "' LIMIT 1");
          String thevendor = (String)this.sqltable.getValue();
          this.sqltable.makeTable("SELECT * FROM `styles_overseasinstock` WHERE `STYLE NUMBER` = '" + this.myorderdata.getSet(i).getItem(0).getStyleNumber() + "' AND `Backup VENDOR` = '" + thevendor + "' LIMIT 1 ");
        }
        if ((this.sqltable.getCell("Rate") != null) && (!((String)this.sqltable.getCell("Rate")).trim().equals(""))) {
          rate = Double.valueOf(((String)this.sqltable.getCell("Rate")).replace("%", "").trim()).doubleValue() / 100.0D;
        }
        otherfees = Double.valueOf((String)this.sqltable.getCell("Other Fees")).doubleValue();
        oceanfreightfee = Double.valueOf((String)this.sqltable.getCell("Ocean Freight Fee")).doubleValue();
      } else if (this.myorderdata.getSet(i).getItem(0).getStyleType() == StyleInformationData.StyleType.OVERSEAS_INSTOCK_SHIRTS) {
        this.sqltable.makeTable("SELECT * FROM `overseas_price_instockshirts` WHERE `stylenumber` = '" + this.myorderdata.getSet(i).getItem(0).getStyleNumber() + "' LIMIT 1");
        if ((this.sqltable.getCell("rate") != null) && (!((String)this.sqltable.getCell("rate")).trim().equals(""))) {
          rate = Double.valueOf(((String)this.sqltable.getCell("rate")).replace("%", "").trim()).doubleValue() / 100.0D;
        }
        otherfees = Double.valueOf((String)this.sqltable.getCell("otherfees")).doubleValue();
        oceanfreightfee = Double.valueOf((String)this.sqltable.getCell("oceanfreightfee")).doubleValue();
      } else if (this.myorderdata.getSet(i).getItem(0).getStyleType() == StyleInformationData.StyleType.OVERSEAS_INSTOCK_FLATS) {
        this.sqltable.makeTable("SELECT * FROM `overseas_price_instockflats` WHERE `stylenumber` = '" + this.myorderdata.getSet(i).getItem(0).getStyleNumber() + "' LIMIT 1");
        if ((this.sqltable.getCell("rate") != null) && (!((String)this.sqltable.getCell("rate")).trim().equals(""))) {
          rate = Double.valueOf(((String)this.sqltable.getCell("rate")).replace("%", "").trim()).doubleValue() / 100.0D;
        }
        otherfees = Double.valueOf((String)this.sqltable.getCell("otherfees")).doubleValue();
        oceanfreightfee = Double.valueOf((String)this.sqltable.getCell("oceanfreightfee")).doubleValue();
      } else if (this.myorderdata.getSet(i).getItem(0).getStyleType() == StyleInformationData.StyleType.OVERSEAS) {
        this.sqltable.makeTable("SELECT * FROM `styles_overseas` WHERE `Style` = '" + this.myorderdata.getSet(i).getItem(0).getStyleNumber() + "' LIMIT 1");
        if ((this.sqltable.getCell("Rate") != null) && (!((String)this.sqltable.getCell("Rate")).trim().equals(""))) {
          rate = Double.valueOf(((String)this.sqltable.getCell("Rate")).replace("%", "").trim()).doubleValue() / 100.0D;
        }
        otherfees = Double.valueOf((String)this.sqltable.getCell("other fees (weight + freight + handling)")).doubleValue();
        oceanfreightfee = Double.valueOf((String)this.sqltable.getCell("freight")).doubleValue();
      } else if ((this.myorderdata.getSet(i).getItem(0).getStyleType() == StyleInformationData.StyleType.OVERSEAS_PREDESIGNED) || (this.myorderdata.getSet(i).getItem(0).getStyleType() == StyleInformationData.StyleType.OVERSEAS_NONOTTO) || (this.myorderdata.getSet(i).getItem(0).getStyleType() == StyleInformationData.StyleType.OVERSEAS_NONOTTO_SHIRTS)) {
        this.sqltable.makeTable("SELECT * FROM `styles_pre-designed` WHERE `Style` = '" + this.myorderdata.getSet(i).getItem(0).getStyleNumber() + "' LIMIT 1");
        if ((this.sqltable.getCell("Rate") != null) && (!((String)this.sqltable.getCell("Rate")).trim().equals(""))) {
          rate = Double.valueOf(((String)this.sqltable.getCell("Rate")).replace("%", "").trim()).doubleValue() / 100.0D;
        }
        otherfees = Double.valueOf((String)this.sqltable.getCell("other fees")).doubleValue();
        if ((this.sqltable.getCell("weight fee") != null) && (!((String)this.sqltable.getCell("weight fee")).trim().equals("")) && (!((String)this.sqltable.getCell("weight fee")).trim().equals("N/A"))) {
          oceanfreightfee = Double.valueOf((String)this.sqltable.getCell("weight fee")).doubleValue();
        }
      } else if (this.myorderdata.getSet(i).getItem(0).getStyleType() == StyleInformationData.StyleType.OVERSEAS_CUSTOMSTYLE) {
        if (this.sqltable.makeTable("SELECT * FROM `styles_overseas_customstyle` WHERE `Style` = '" + this.myorderdata.getSet(i).getItem(0).getStyleNumber() + "' AND `Profile` = '" + this.myorderdata.getSet(i).getItem(0).getProfile() + "' AND `WorkFlow Fabic Name` = '" + this.myorderdata.getSet(i).getItem(0).getFrontPanelFabric() + "' LIMIT 1").booleanValue()) {
          if ((this.sqltable.getCell("Duty Rate") != null) && (!((String)this.sqltable.getCell("Duty Rate")).trim().equals("")) && (!((String)this.sqltable.getCell("Duty Rate")).trim().equals("N/A"))) {
            rate = Double.valueOf(((String)this.sqltable.getCell("Duty Rate")).replace("%", "").trim()).doubleValue() / 100.0D;
          }
          double theweightfee = 0.0D;
          String weightfee = (String)this.sqltable.getCell("Weight Fee");
          if ((weightfee != null) && (!weightfee.trim().equals("N/A"))) {
            theweightfee = Double.valueOf((String)this.sqltable.getCell("Weight Fee")).doubleValue();
          }
          
          otherfees = theweightfee + Double.valueOf((String)this.sqltable.getCell("Freight")).doubleValue() + Double.valueOf((String)this.sqltable.getCell("Handling")).doubleValue();
          oceanfreightfee = Double.valueOf((String)this.sqltable.getCell("Freight")).doubleValue();
        } else if (this.sqltable.makeTable("SELECT * FROM `styles_overseas_customstyle` WHERE `Style` = '" + this.myorderdata.getSet(i).getItem(0).getStyleNumber() + "' AND `Profile` = '" + this.myorderdata.getSet(i).getItem(0).getProfile() + "' AND `WorkFlow Fabic Name` = 'Custom' LIMIT 1").booleanValue()) {
          if ((this.sqltable.getCell("Duty Rate") != null) && (!((String)this.sqltable.getCell("Duty Rate")).trim().equals("")) && (!((String)this.sqltable.getCell("Duty Rate")).trim().equals("N/A"))) {
            rate = Double.valueOf(((String)this.sqltable.getCell("Duty Rate")).replace("%", "").trim()).doubleValue() / 100.0D;
          }
          double theweightfee = 0.0D;
          String weightfee = (String)this.sqltable.getCell("Weight Fee");
          if ((weightfee != null) && (!weightfee.trim().equals("N/A"))) {
            theweightfee = Double.valueOf((String)this.sqltable.getCell("Weight Fee")).doubleValue();
          }
          otherfees = theweightfee + Double.valueOf((String)this.sqltable.getCell("Freight")).doubleValue() + Double.valueOf((String)this.sqltable.getCell("Handling")).doubleValue();
          oceanfreightfee = Double.valueOf((String)this.sqltable.getCell("Freight")).doubleValue();
        }
        else if (this.sqltable.makeTable("SELECT `Vendor` FROM `styles_overseas_customstyle` WHERE `Style` = '" + this.myorderdata.getSet(i).getItem(0).getStyleNumber() + "' LIMIT 1").booleanValue()) {
          this.sqltable.makeTable("SELECT * FROM `styles_pre-designed` WHERE `Vendor` =" + this.sqltable.getValue() + " LIMIT 1");
          if ((this.sqltable.getCell("Rate") != null) && (!((String)this.sqltable.getCell("Rate")).trim().equals(""))) {
            rate = Double.valueOf(((String)this.sqltable.getCell("Rate")).replace("%", "").trim()).doubleValue() / 100.0D;
          }
          otherfees = Double.valueOf((String)this.sqltable.getCell("other fees")).doubleValue();
          if ((this.sqltable.getCell("weight fee") != null) && (!((String)this.sqltable.getCell("weight fee")).trim().equals("")) && (!((String)this.sqltable.getCell("weight fee")).trim().equals("N/A"))) {
            oceanfreightfee = Double.valueOf((String)this.sqltable.getCell("weight fee")).doubleValue();
          }
        }
      }
      

      HiddenEcho("&nbsp;&nbsp;&nbsp;Rate " + rate + "<br>");
      HiddenEcho("&nbsp;&nbsp;&nbsp;Other Fees " + otherfees + "<br>");
      


      if (((OrderDataVendorInformation)this.myorderdata.getVendorInformation().getOverseasVendor().get(String.valueOf(this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber()))).getShippingMethod().equals("FOB China")) {
        HiddenEcho("&nbsp;&nbsp;&nbsp;FOB China Skip Landing Cost <br>");
      }
      else {
        HiddenEcho("&nbsp;&nbsp;&nbsp;Complete Blank Cap Price After Landing Cost " + (completestyleprice * (1.0D + rate) + otherfees) + " = ( " + completestyleprice + "*(1+" + rate + "))+" + otherfees + "<br>");
        completestyleprice = completestyleprice * (1.0D + rate) + otherfees;
        
        double totallogocostwithlanding = 0.0D;
        for (int j = 0; j < decorationstotal.length; j++) {
          HiddenEcho("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Logo " + (j + 1) + " Price After Landing Cost " + decorationstotal[j] * (1.0D + rate) + " = " + decorationstotal[j] + "*(1+" + rate + ")<br>");
          decorationstotal[j] *= (1.0D + rate);
          totallogocostwithlanding += decorationstotal[j];
        }
        
        HiddenEcho("&nbsp;&nbsp;&nbsp;Landing Cost " + (completestyleprice + totallogocostwithlanding) + " = " + completestyleprice + " + " + totallogocostwithlanding + "<br>");
      }
      

      int customerpricelevel = 1;
      double customerprice = 0.0D;
      double customerrate = 0.65D;
      double additionalpercap = 0.0D;
      
      if (this.sqltable.makeTable("SELECT `overseaspricelevel` FROM `eclipse_customer_info` WHERE `eclipseaccount` = '" + this.myorderdata.getCustomerInformation().getEclipseAccountNumber() + "'").booleanValue()) {
        customerpricelevel = Integer.valueOf((String)this.sqltable.getValue()).intValue();
      }
      this.sqltable.makeTable("SELECT * FROM `overseas_price_levels` ORDER BY `Quantity` ASC");
      for (int j = 0; j < this.sqltable.getTable().size(); j++) {
        if (breakdowncapquantity >= ((Integer)((FastMap)this.sqltable.getTable().get(j)).get("Quantity")).intValue()) {
          customerrate = Double.valueOf(((String)((FastMap)this.sqltable.getTable().get(j)).get(String.valueOf(customerpricelevel))).replace("%", "").trim()).doubleValue() / 100.0D;
          additionalpercap = ((Double)((FastMap)this.sqltable.getTable().get(j)).get("Additional Per Cap")).doubleValue();
        }
      }
      
      HiddenEcho("&nbsp;&nbsp;&nbsp;Customer Price Level " + customerpricelevel + "<br>");
      HiddenEcho("&nbsp;&nbsp;&nbsp;Complete Blank Cap Quantity For Rate " + breakdowncapquantity + "<br>");
      HiddenEcho("&nbsp;&nbsp;&nbsp;Complete Blank Cap Rate " + customerrate + "<br>");
      
      HiddenEcho("&nbsp;&nbsp;&nbsp;Complete Blank Cap Price After Cap Rate And Additional Per Cap " + (completestyleprice / 12.0D / (1.0D - customerrate) + additionalpercap) + " = ((" + completestyleprice + "/12.0)/(1.0-" + customerrate + ")) + " + additionalpercap + "<br>");
      completestyleprice = completestyleprice / 12.0D / (1.0D - customerrate) + additionalpercap;
      

      HiddenEcho("&nbsp;&nbsp;&nbsp;Complete Blank Cap Price Added Special Dye Cost" + (completestyleprice + specialdyecost.getCusDyeCost(i)) + " = " + completestyleprice + " + " + specialdyecost.getCusDyeCost(i) + "<br>");
      completestyleprice += specialdyecost.getCusDyeCost(i);
      
      double logocostafterlogorates = 0.0D;
      for (int j = 0; j < decorationstotal.length; j++) {
        String filename = getMatchingFilename(this.myorderdata.getSet(i).getLogo(j).getFilename());
        String dstfilename = getMatchingFilename(this.myorderdata.getSet(i).getLogo(j).getDstFilename());
        int thestitchcount = this.myorderdata.getSet(i).getLogo(j).getStitchCount() == null ? 0 : this.myorderdata.getSet(i).getLogo(j).getStitchCount().intValue();
        String samevendorlogokey = this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + filename + dstfilename + thestitchcount + this.myorderdata.getSet(i).getLogo(j).getDecorationCount();
        
        this.sqltable.makeTable("SELECT * FROM `overseas_price_levels` ORDER BY `Quantity` ASC");
        for (int k = 0; k < this.sqltable.getTable().size(); k++) {
          if (this.pricehelper.getSameVendorCapsWithSameLogoQuantity(samevendorlogokey) >= ((Integer)((FastMap)this.sqltable.getTable().get(k)).get("Quantity")).intValue()) {
            customerrate = Double.valueOf(((String)((FastMap)this.sqltable.getTable().get(k)).get(String.valueOf(customerpricelevel))).replace("%", "").trim()).doubleValue() / 100.0D;
          }
        }
        
        HiddenEcho("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Logo " + (j + 1) + " Max Cap Of Logos: " + this.pricehelper.getSameVendorCapsWithSameLogoQuantity(samevendorlogokey) + "<br>");
        HiddenEcho("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Logo " + (j + 1) + " Rate " + customerrate + "<br>");
        HiddenEcho("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Logo " + (j + 1) + " Price After Rate " + decorationstotal[j] / 12.0D / (1.0D - customerrate) + " = (" + decorationstotal[j] + "/12.0)/(1-" + customerrate + ")<br>");
        decorationstotal[j] = (decorationstotal[j] / 12.0D / (1.0D - customerrate));
        logocostafterlogorates += decorationstotal[j];
      }
      
      customerprice = completestyleprice + logocostafterlogorates;
      HiddenEcho("&nbsp;&nbsp;&nbsp;Customer Price Before Rounding " + customerprice + " = " + completestyleprice + "+ " + logocostafterlogorates + "<br>");
      
      customerprice = Math.ceil((float)(customerprice * 100.0D)) / 100.0D;
      
      HiddenEcho("&nbsp;&nbsp;&nbsp;Customer Price After Rounding " + customerprice + "<br>");
      

      for (int j = 0; j < this.myorderdata.getDiscountItemCount(); j++) {
        if (this.myorderdata.getDiscountItem(j).getIntoItems().booleanValue()) {
          double amount = this.myorderdata.getDiscountItem(j).getAmount().doubleValue();
          customerprice += amount / this.pricehelper.getTotalItems();
          this.myorderdata.getSet(i).getItem(0).setSpecialItemPrice(true);
        }
      }
      

      if (com.ottocap.NewWorkFlow.server.SharedData.isFaya.booleanValue()) {
        if (this.pricehelper.getTotalItems() < 576) {
          customerprice *= 1.38D;
        } else if (this.pricehelper.getTotalItems() < 1008) {
          customerprice *= 1.35D;
        } else if (this.pricehelper.getTotalItems() < 2000) {
          customerprice *= 1.3D;
        } else if (this.pricehelper.getTotalItems() < 3000) {
          customerprice *= 1.25D;
        } else {
          customerprice *= 1.22D;
        }
      }
      

      HiddenEcho("&nbsp;&nbsp;&nbsp;Customer Price Before Rounding With Discounts" + customerprice + " <br>");
      
      customerprice = Math.ceil((float)(customerprice * 100.0D)) / 100.0D;
      
      HiddenEcho("&nbsp;&nbsp;&nbsp;Customer Price After Rounding " + customerprice + "<br>");
      
      HiddenEcho("&nbsp;&nbsp;&nbsp;Customer Final Unit Price " + customerprice + "<br>");
      
      if (((OrderDataVendorInformation)this.myorderdata.getVendorInformation().getOverseasVendor().get(String.valueOf(this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber()))).getShippingMethod().equals("CFS"))
      {
        otherfees = cfscost.getVendorCFS(this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber());
        customerprice += otherfees;
        HiddenEcho("&nbsp;&nbsp;&nbsp;Complete Blank Cap Price After CFS Cost " + customerprice + " += " + otherfees + "<br>");
        
        customerprice -= oceanfreightfee / 12.0D;
        HiddenEcho("&nbsp;&nbsp;&nbsp;Complete Blank Cap Price After Ocean Removal " + customerprice + " -= (" + oceanfreightfee + "/ 12.0) <br>");
        

        HiddenEcho("&nbsp;&nbsp;&nbsp;Customer Final Unit Price With CFS Cost Before Rounding " + customerprice + "<br>");
        customerprice = Math.ceil((float)(customerprice * 100.0D)) / 100.0D;
        HiddenEcho("&nbsp;&nbsp;&nbsp;Customer Final Unit Price With CFS Cost After Rounding " + customerprice + "<br>");
      } else if (((OrderDataVendorInformation)this.myorderdata.getVendorInformation().getOverseasVendor().get(String.valueOf(this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber()))).getShippingMethod().equals("FOB China")) {
        HiddenEcho("&nbsp;&nbsp;&nbsp;FOB China No CFS or Loose Container Charge <br>");
      }
      else {
        this.sqltable.makeTable("SELECT `" + this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "` FROM `overseas_price_charges` WHERE `name` = 'loosercontainercharge'");
        double loosecontainercharge = Double.valueOf((String)this.sqltable.getValue()).doubleValue();
        int totalvendoritem = this.pricehelper.getTotalVendorItems(Integer.valueOf(this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber()).intValue());
        customerprice += loosecontainercharge / totalvendoritem;
        
        HiddenEcho("&nbsp;&nbsp;&nbsp;Loose Container Charge " + loosecontainercharge + "<br>");
        HiddenEcho("&nbsp;&nbsp;&nbsp;Total Items From Vendor " + totalvendoritem + "<br>");
        HiddenEcho("&nbsp;&nbsp;&nbsp;Customer Final Unit Price With Loose Container Charge Before Rounding " + customerprice + "<br>");
        customerprice = Math.ceil((float)(customerprice * 100.0D)) / 100.0D;
        HiddenEcho("&nbsp;&nbsp;&nbsp;Customer Final Unit Price With Loose Container Charge After Rounding " + customerprice + "<br>");
      }
      

      customertotalcappricing += completestyleprice * this.myorderdata.getSet(i).getItem(0).getQuantity().intValue();
      
      if (this.myorderdata.getSet(i).getItem(0).getProductDiscount() != null) {
        HiddenEcho("&nbsp;&nbsp;&nbsp;Product Discount " + (customerprice - this.myorderdata.getSet(i).getItem(0).getProductDiscount().doubleValue()) + " = " + customerprice + " - " + this.myorderdata.getSet(i).getItem(0).getProductDiscount() + "<br>");
        customerprice -= this.myorderdata.getSet(i).getItem(0).getProductDiscount().doubleValue();
        this.myorderdata.getSet(i).getItem(0).setSpecialItemPrice(true);
      }
      

      if (this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() == 13) {
        customerprice /= 0.9D;
        HiddenEcho("&nbsp;&nbsp;&nbsp;Prolink Markup " + customerprice + " = ( " + customerprice + " / 0.90 )" + "<br>");
        customerprice = Math.ceil((float)(customerprice * 100.0D)) / 100.0D;
        HiddenEcho("&nbsp;&nbsp;&nbsp;Prolink Markup After Rounding " + customerprice + "<br>");
      }
      
      double customeritemprice = customerprice * this.myorderdata.getSet(i).getItem(0).getQuantity().intValue();
      HiddenEcho("&nbsp;&nbsp;&nbsp;Customer Final Total Price " + customeritemprice + " = " + customerprice + " * " + this.myorderdata.getSet(i).getItem(0).getQuantity() + "<br>");
      
      storeditemunitprice[i] = customerprice;
      storeditemtotalprice[i] = customeritemprice;
      customertotalprice += customeritemprice;
      HiddenEcho("<BR><BR><BR>");
    }
    


    if ((vendortotalarray[12] > 0.0D) && (vendortotalarray[12] < 3500.0D)) {
      HiddenEcho("&nbsp;&nbsp;&nbsp;Yulite added handling charge<br>");
      double thehandlingcharge = 250.0D;
      vendortotalarray[12] += thehandlingcharge;
      
      int totalvendoritem = this.pricehelper.getTotalVendorItems(12);
      double singlehandlingcharge = thehandlingcharge / totalvendoritem;
      for (int i = 0; i < this.myorderdata.getSetCount(); i++) {
        if (this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() == 12)
        {
          storeditemunitprice[i] += singlehandlingcharge;
          storeditemtotalprice[i] += singlehandlingcharge * this.myorderdata.getSet(i).getItem(0).getQuantity().intValue();
          customertotalprice += singlehandlingcharge * this.myorderdata.getSet(i).getItem(0).getQuantity().intValue();
        }
      }
    }
    

    double totalartworkhours = 0.0D;
    int totalshipswatchs = 0;
    int totalproductsamplesship = 0;
    int totalproductsamplesshipandemail = 0;
    
    for (int i = 0; i < this.myorderdata.getSetCount(); i++) {
      if ((this.myorderdata.getSet(i).getItem(0).getStyleNumber().startsWith("888-")) || (this.myorderdata.getSet(i).getItem(0).getStyleNumber().startsWith("889-")) || (this.myorderdata.getSet(i).getItem(0).getStyleNumber().startsWith("111-")) || (this.myorderdata.getSet(i).getItem(0).getStyleNumber().startsWith("777-"))) {
        if ((this.myorderdata.getSet(i).getItem(0).getCustomStyleName() != null) && (!this.myorderdata.getSet(i).getItem(0).getCustomStyleName().equals(""))) {
          addTableRow("Style: " + this.myorderdata.getSet(i).getItem(0).getStyleNumber() + ": " + this.myorderdata.getSet(i).getItem(0).getCustomStyleName(), "", String.valueOf(this.myorderdata.getSet(i).getItem(0).getQuantity()), NumberFormat.getCurrencyInstance().format(storeditemunitprice[i]) + (this.myorderdata.getSet(i).getItem(0).getSpecialItemPrice() ? "*" : ""), NumberFormat.getCurrencyInstance().format(storeditemtotalprice[i]));
        } else {
          addTableRow("Style: " + this.myorderdata.getSet(i).getItem(0).getStyleNumber(), "", String.valueOf(this.myorderdata.getSet(i).getItem(0).getQuantity()), NumberFormat.getCurrencyInstance().format(storeditemunitprice[i]) + (this.myorderdata.getSet(i).getItem(0).getSpecialItemPrice() ? "*" : ""), NumberFormat.getCurrencyInstance().format(storeditemtotalprice[i]));
        }
      } else {
        addTableRow("Style: " + this.myorderdata.getSet(i).getItem(0).getStyleNumber(), "", String.valueOf(this.myorderdata.getSet(i).getItem(0).getQuantity()), NumberFormat.getCurrencyInstance().format(storeditemunitprice[i]) + (this.myorderdata.getSet(i).getItem(0).getSpecialItemPrice() ? "*" : ""), NumberFormat.getCurrencyInstance().format(storeditemtotalprice[i]));
      }
      
      double productsampleemailprice = 6.0D;
      

      int productsampletotalship = this.myorderdata.getSet(i).getItem(0).getProductSampleTotalShip() != null ? this.myorderdata.getSet(i).getItem(0).getProductSampleTotalShip().intValue() : 0;
      int productsampletotalemail = this.myorderdata.getSet(i).getItem(0).getProductSampleTotalEmail() != null ? this.myorderdata.getSet(i).getItem(0).getProductSampleTotalEmail().intValue() : 0;
      totalproductsamplesship += productsampletotalship;
      totalproductsamplesshipandemail += productsampletotalship;
      totalproductsamplesshipandemail += productsampletotalemail;
      

      if (productsampletotalemail > 0) {
        addTableRow("E-Mail Only Sample", "", String.valueOf(productsampletotalemail), NumberFormat.getCurrencyInstance().format(productsampleemailprice), NumberFormat.getCurrencyInstance().format(productsampletotalemail * productsampleemailprice));
        customertotalprice += productsampletotalemail * productsampleemailprice;
      }
      
      for (int j = 0; j < this.myorderdata.getSet(i).getLogoCount(); j++) {
        if (!this.myorderdata.getSet(i).getLogo(j).getFilename().equals("")) {
          addTableRow("", "Logo " + (j + 1) + " - " + this.myorderdata.getSet(i).getLogo(j).getFilename(), "", "", "");
        } else if (!this.myorderdata.getSet(i).getLogo(j).getDstFilename().equals("")) {
          addTableRow("", "Logo " + (j + 1) + " - " + this.myorderdata.getSet(i).getLogo(j).getDstFilename(), "", "", "");
        } else {
          addTableRow("", "Logo " + (j + 1), "", "", "");
        }
        addTableRow("", this.myorderdata.getSet(i).getLogo(j).getLogoLocationName(), "", "", "");
        
        for (int k = 0; k < this.myorderdata.getSet(i).getLogo(j).getDecorationCount().intValue(); k++) {
          addTableRow("", this.myorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getName(), "", "", "");
        }
        
        double artworkchargehour = this.myorderdata.getSet(i).getLogo(j).getArtworkChargePerHour() != null ? this.myorderdata.getSet(i).getLogo(j).getArtworkChargePerHour().doubleValue() : 0.0D;
        totalartworkhours += artworkchargehour;
        
        int logoswatch = this.myorderdata.getSet(i).getLogo(j).getSwatchTotalShip() != null ? this.myorderdata.getSet(i).getLogo(j).getSwatchTotalShip().intValue() : 0;
        totalshipswatchs += logoswatch;
      }
    }
    
    if (totalartworkhours > 0.0D) {
      double totalartworkhourscost = totalartworkhours * 50.0D;
      addTableRow("Artwork Charge", "", String.valueOf(totalartworkhours), "$50.00", NumberFormat.getCurrencyInstance().format(totalartworkhourscost));
      customertotalprice += totalartworkhourscost;
    }
    if (totalshipswatchs > 0) {
      double totalshipswatchscost = totalshipswatchs * 2 + 18;
      addTableRow("Swatches", "", String.valueOf(totalshipswatchs), "", NumberFormat.getCurrencyInstance().format(totalshipswatchscost));
      customertotalprice += totalshipswatchscost;
    }
    if (totalproductsamplesship > 0) {
      double totalproductsamplescost = totalproductsamplesship * 6 + 34;
      addTableRow("Samples", "", String.valueOf(totalproductsamplesship), "", NumberFormat.getCurrencyInstance().format(totalproductsamplescost));
      customertotalprice += totalproductsamplescost;
    }
    
    if (totalproductsamplesshipandemail > 0) {
      OverseasFreeProductSampleCalculator totalproductsamplefree = new OverseasFreeProductSampleCalculator(this.myorderdata);
      int totalfree = totalproductsamplefree.getTotalProductSampleFree();
      if (totalfree > 0) {
        double totalproductsamplediscountcost = 0.0D;
        if (totalproductsamplesship > 0) {
          totalproductsamplediscountcost = -(totalfree * 6 + 34);
        } else {
          totalproductsamplediscountcost = -(totalfree * 6);
        }
        addTableRow("Sample Discount 720 Qty Same Style Only", "", String.valueOf(totalfree), "", NumberFormat.getCurrencyInstance().format(totalproductsamplediscountcost));
        customertotalprice += totalproductsamplediscountcost;
      }
    }
    
    for (int i = 0; i < this.myorderdata.getDiscountItemCount(); i++) {
      if (!this.myorderdata.getDiscountItem(i).getIntoItems().booleanValue()) {
        String reason = this.myorderdata.getDiscountItem(i).getReason();
        double amount = this.myorderdata.getDiscountItem(i).getAmount().doubleValue();
        addTableRow(reason, "", "", "", NumberFormat.getCurrencyInstance().format(amount));
        customertotalprice += amount;
      }
    }
    

    double dropshipmentprice = 5.0D;
    if ((this.myorderdata.getCustomerInformation().getHaveDropShipment()) && (this.myorderdata.getCustomerInformation().getDropShipmentAmount().intValue() > 0)) {
      addTableRow("Additional Drop Shipment Charge", "", String.valueOf(this.myorderdata.getCustomerInformation().getDropShipmentAmount()), NumberFormat.getCurrencyInstance().format(dropshipmentprice), NumberFormat.getCurrencyInstance().format(dropshipmentprice * this.myorderdata.getCustomerInformation().getDropShipmentAmount().intValue()));
      customertotalprice += dropshipmentprice * this.myorderdata.getCustomerInformation().getDropShipmentAmount().intValue();
    }
    
    double shippingcost = 0.0D;
    if (this.myorderdata.getShippingType().equals("Pick-up")) {
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
        e.printStackTrace();
        addTableRow("Shipping", "", "", "", "");
        addTableRow("", this.myorderdata.getShippingType() + " (TBD)", "", "", NumberFormat.getCurrencyInstance().format(shippingcost));
      }
    } else {
      addTableRow("Shipping", "", "", "", "");
      addTableRow("", this.myorderdata.getShippingType() + " (Estimated)", "", "", "(N/A)");
    }
    
    setCustomerTotalCapPrice(customertotalcappricing);
    setCustomerTotalDecorationPrice(customertotalprice - customertotalcappricing);
    
    customertotalprice += shippingcost;
    
    addTableRow(" ", "", "", "", "");
    addTableRow("", "", "", "Total Price:", NumberFormat.getCurrencyInstance().format(customertotalprice));
    
    setCustomerTotalPrice(customertotalprice);
    setVendorTotalCharge(vendortotalarray);
    setVendorItemDozCost(vendoritemdozcost);
  }
  
  private void setCustomerTotalCapPrice(double price)
  {
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
  
  private void setCustomerTotalPrice(double customertotalprice) {
    this.totalcustomerprice = customertotalprice;
  }
  
  private void setVendorTotalCharge(double[] vendortotalarray) {
    this.vendorchargetotal = vendortotalarray;
  }
  
  private void setVendorItemDozCost(double[] vendoritemdozcost) {
    this.vendoritemdozencost = vendoritemdozcost;
  }
  
  public double getCustomerTotalPrice() {
    return this.totalcustomerprice;
  }
  
  public double getVendorTotalCharge(int vendornumber) {
    return this.vendorchargetotal[vendornumber];
  }
  
  public double getVendorItemDozCost(int itemnumber) {
    return this.vendoritemdozencost[itemnumber];
  }
  
  private void addTableRow(String string1, String string2, String string3, String string4, String string5) {
    String[] mystring = new String[5];
    mystring[0] = string1;
    mystring[1] = string2;
    mystring[2] = string3;
    mystring[3] = string4;
    mystring[4] = string5;
    this.pricetableholder.add(mystring);
    this.output = (this.output + string1 + "," + string2 + "," + string3 + "," + string4 + "," + string5 + "<br>");
  }
  
  public ArrayList<String[]> getTableRow() {
    return this.pricetableholder;
  }
  
  public String getOutput() {
    return this.output;
  }
}
