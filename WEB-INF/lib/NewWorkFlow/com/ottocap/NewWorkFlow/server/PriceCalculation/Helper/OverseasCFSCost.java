package com.ottocap.NewWorkFlow.server.PriceCalculation.Helper;

import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderData;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataItem;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataSet;
import com.ottocap.NewWorkFlow.server.SQLTable;

public class OverseasCFSCost
{
  double[] totalvendorcfscost = new double[com.ottocap.NewWorkFlow.server.Settings.TotalVendors];
  double[] vendorcbmrate = new double[com.ottocap.NewWorkFlow.server.Settings.TotalVendors];
  double[] vendorlandedcost = new double[com.ottocap.NewWorkFlow.server.Settings.TotalVendors];
  double[] vendortotalqty = new double[com.ottocap.NewWorkFlow.server.Settings.TotalVendors];
  
  public OverseasCFSCost(SQLTable sqltable, ExtendOrderData myorderdata)
  {
    for (int i = 0; i < myorderdata.getSetCount(); i++) {
      int qty = myorderdata.getSet(i).getItem(0).getQuantity().intValue();
      double cbmpiece = 0.0D;
      double percbmrate = 0.0D;
      double landedcost = 0.0D;
      
      if (myorderdata.getSet(i).getItem(0).getStyleType() == com.ottocap.NewWorkFlow.client.StyleInformationData.StyleType.OVERSEAS_INSTOCK) {
        sqltable.makeTable("SELECT * FROM `styles_overseasinstock` WHERE `STYLE NUMBER` = '" + myorderdata.getSet(i).getItem(0).getStyleNumber() + "' AND `Backup VENDOR` = '" + myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + "' LIMIT 1 ");
        cbmpiece = Double.valueOf(((String)sqltable.getCell("CBM / Piece")).trim()).doubleValue();
        percbmrate = Double.valueOf(((String)sqltable.getCell("Rate/CBM")).replace("$", "").trim()).doubleValue();
        landedcost = Double.valueOf(((String)sqltable.getCell("CFS Freight")).replace("$", "").trim()).doubleValue();
      } else if (myorderdata.getSet(i).getItem(0).getStyleType() == com.ottocap.NewWorkFlow.client.StyleInformationData.StyleType.OVERSEAS_INSTOCK_SHIRTS) {
        sqltable.makeTable("SELECT * FROM `overseas_price_instockshirts` WHERE `stylenumber` = '" + myorderdata.getSet(i).getItem(0).getStyleNumber() + "' LIMIT 1");
        cbmpiece = Double.valueOf(((String)sqltable.getCell("CBM / Piece")).trim()).doubleValue();
        percbmrate = Double.valueOf(((String)sqltable.getCell("Per CBM Rate")).replace("$", "").trim()).doubleValue();
        landedcost = Double.valueOf(((String)sqltable.getCell("Landed Cost")).replace("$", "").trim()).doubleValue();
      } else if (myorderdata.getSet(i).getItem(0).getStyleType() == com.ottocap.NewWorkFlow.client.StyleInformationData.StyleType.OVERSEAS_INSTOCK_FLATS) {
        sqltable.makeTable("SELECT * FROM `overseas_price_instockflats` WHERE `stylenumber` = '" + myorderdata.getSet(i).getItem(0).getStyleNumber() + "' LIMIT 1");
        cbmpiece = Double.valueOf(((String)sqltable.getCell("CBM / Piece")).trim()).doubleValue();
        percbmrate = Double.valueOf(((String)sqltable.getCell("Per CBM Rate")).replace("$", "").trim()).doubleValue();
        landedcost = Double.valueOf(((String)sqltable.getCell("Landed Cost")).replace("$", "").trim()).doubleValue();










      }
      else if ((myorderdata.getSet(i).getItem(0).getStyleType() == com.ottocap.NewWorkFlow.client.StyleInformationData.StyleType.OVERSEAS_PREDESIGNED) || (myorderdata.getSet(i).getItem(0).getStyleType() == com.ottocap.NewWorkFlow.client.StyleInformationData.StyleType.OVERSEAS_NONOTTO) || (myorderdata.getSet(i).getItem(0).getStyleType() == com.ottocap.NewWorkFlow.client.StyleInformationData.StyleType.OVERSEAS_NONOTTO_SHIRTS)) {
        sqltable.makeTable("SELECT * FROM `styles_pre-designed` WHERE `Style` = '" + myorderdata.getSet(i).getItem(0).getStyleNumber() + "' LIMIT 1");
        cbmpiece = Double.valueOf(((String)sqltable.getCell("CBM / Piece")).trim()).doubleValue();
        percbmrate = Double.valueOf(((String)sqltable.getCell("Per CBM Rate")).replace("$", "").trim()).doubleValue();
        landedcost = Double.valueOf(((String)sqltable.getCell("Landed Cost")).replace("$", "").trim()).doubleValue();
      } else if (myorderdata.getSet(i).getItem(0).getStyleType() == com.ottocap.NewWorkFlow.client.StyleInformationData.StyleType.OVERSEAS_CUSTOMSTYLE) {
        if (sqltable.makeTable("SELECT * FROM `styles_overseas_customstyle` WHERE `Style` = '" + myorderdata.getSet(i).getItem(0).getStyleNumber() + "' AND `Profile` = '" + myorderdata.getSet(i).getItem(0).getProfile() + "' AND `WorkFlow Fabic Name` = '" + myorderdata.getSet(i).getItem(0).getFrontPanelFabric() + "' LIMIT 1").booleanValue()) {
          cbmpiece = Double.valueOf(((String)sqltable.getCell("CBM / Piece")).trim()).doubleValue();
          percbmrate = Double.valueOf(((String)sqltable.getCell("Per CBM Rate")).replace("$", "").trim()).doubleValue();
          landedcost = Double.valueOf(((String)sqltable.getCell("Landed Cost")).replace("$", "").trim()).doubleValue();
        } else if (sqltable.makeTable("SELECT * FROM `styles_overseas_customstyle` WHERE `Style` = '" + myorderdata.getSet(i).getItem(0).getStyleNumber() + "' AND `Profile` = '" + myorderdata.getSet(i).getItem(0).getProfile() + "' AND `WorkFlow Fabic Name` = 'Custom' LIMIT 1").booleanValue()) {
          cbmpiece = Double.valueOf(((String)sqltable.getCell("CBM / Piece")).trim()).doubleValue();
          percbmrate = Double.valueOf(((String)sqltable.getCell("Per CBM Rate")).replace("$", "").trim()).doubleValue();
          landedcost = Double.valueOf(((String)sqltable.getCell("Landed Cost")).replace("$", "").trim()).doubleValue();
        }
      }
      
      double cbmrate = qty * cbmpiece;
      this.totalvendorcfscost[myorderdata.getSet(i).getItem(0).getOverseasVendorNumber()] += cbmrate;
      this.vendortotalqty[myorderdata.getSet(i).getItem(0).getOverseasVendorNumber()] += qty;
      
      this.vendorcbmrate[myorderdata.getSet(i).getItem(0).getOverseasVendorNumber()] = percbmrate;
      this.vendorlandedcost[myorderdata.getSet(i).getItem(0).getOverseasVendorNumber()] = landedcost;
    }
    
    for (int i = 1; i < this.totalvendorcfscost.length; i++) {
      if (this.totalvendorcfscost[i] != 0.0D)
      {
        this.totalvendorcfscost[i] = ((Math.ceil(this.totalvendorcfscost[i]) * this.vendorcbmrate[i] + this.vendorlandedcost[i]) / this.vendortotalqty[i]);
      }
    }
  }
  
  public double getVendorCFS(int vendorNumber)
  {
    return this.totalvendorcfscost[vendorNumber];
  }
}
