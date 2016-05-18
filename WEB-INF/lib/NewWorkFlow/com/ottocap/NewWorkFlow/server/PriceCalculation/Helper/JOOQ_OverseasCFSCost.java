package com.ottocap.NewWorkFlow.server.PriceCalculation.Helper;

import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderData;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataItem;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataSet;
import com.ottocap.NewWorkFlow.server.JOOQSQL;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;

public class JOOQ_OverseasCFSCost
{
  double[] totalvendorcfscost = new double[com.ottocap.NewWorkFlow.server.Settings.TotalVendors];
  double[] vendorcbmrate = new double[com.ottocap.NewWorkFlow.server.Settings.TotalVendors];
  double[] vendorlandedcost = new double[com.ottocap.NewWorkFlow.server.Settings.TotalVendors];
  double[] vendortotalqty = new double[com.ottocap.NewWorkFlow.server.Settings.TotalVendors];
  
  public JOOQ_OverseasCFSCost(ExtendOrderData myorderdata)
  {
    for (int i = 0; i < myorderdata.getSetCount(); i++) {
      int qty = myorderdata.getSet(i).getItem(0).getQuantity().intValue();
      double cbmpiece = 0.0D;
      double percbmrate = 0.0D;
      double landedcost = 0.0D;
      
      if (myorderdata.getSet(i).getItem(0).getStyleType() == com.ottocap.NewWorkFlow.client.StyleInformationData.StyleType.OVERSEAS_INSTOCK) {
        Record sqlrecord = null;
        if ((myorderdata.getSet(i).getItem(0).getVendorNumber() != null) && (!myorderdata.getSet(i).getItem(0).getVendorNumber().equals("")) && (!myorderdata.getSet(i).getItem(0).getVendorNumber().equals("Default"))) {
          sqlrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_overseasinstock" })).where(new org.jooq.Condition[] { DSL.fieldByName(new String[] { "STYLE NUMBER" }).equal(myorderdata.getSet(i).getItem(0).getStyleNumber()) }).and(DSL.fieldByName(new String[] { "Backup VENDOR" }).equal(Integer.valueOf(myorderdata.getSet(i).getItem(0).getOverseasVendorNumber()))).limit(1).fetchOne();
        } else {
          sqlrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "Primary VENDOR" })).from(new org.jooq.TableLike[] { DSL.tableByName(new String[] { "styles_overseasinstock" }) }).where(new org.jooq.Condition[] { DSL.fieldByName(new String[] { "STYLE NUMBER" }).equal(myorderdata.getSet(i).getItem(0).getStyleNumber()) }).limit(1).fetchOne();
          String thevendor = (String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Primary VENDOR" }));
          sqlrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_overseasinstock" })).where(new org.jooq.Condition[] { DSL.fieldByName(new String[] { "STYLE NUMBER" }).equal(myorderdata.getSet(i).getItem(0).getStyleNumber()) }).and(DSL.fieldByName(new String[] { "Backup VENDOR" }).equal(thevendor)).limit(1).fetchOne();
        }
        cbmpiece = Double.valueOf(((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "CBM / Piece" }))).trim()).doubleValue();
        percbmrate = Double.valueOf(((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Rate/CBM" }))).replace("$", "").trim()).doubleValue();
        landedcost = Double.valueOf(((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "CFS Freight" }))).replace("$", "").trim()).doubleValue();
      } else if (myorderdata.getSet(i).getItem(0).getStyleType() == com.ottocap.NewWorkFlow.client.StyleInformationData.StyleType.OVERSEAS_INSTOCK_SHIRTS) {
        Record sqlrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "overseas_price_instockshirts" })).where(new org.jooq.Condition[] { DSL.fieldByName(new String[] { "stylenumber" }).equal(myorderdata.getSet(i).getItem(0).getStyleNumber()) }).limit(1).fetchOne();
        cbmpiece = Double.valueOf(((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "CBM / Piece" }))).trim()).doubleValue();
        percbmrate = Double.valueOf(((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Per CBM Rate" }))).replace("$", "").trim()).doubleValue();
        landedcost = Double.valueOf(((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Landed Cost" }))).replace("$", "").trim()).doubleValue();
      } else if (myorderdata.getSet(i).getItem(0).getStyleType() == com.ottocap.NewWorkFlow.client.StyleInformationData.StyleType.OVERSEAS_INSTOCK_FLATS) {
        Record sqlrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "overseas_price_instockflats" })).where(new org.jooq.Condition[] { DSL.fieldByName(new String[] { "stylenumber" }).equal(myorderdata.getSet(i).getItem(0).getStyleNumber()) }).limit(1).fetchOne();
        cbmpiece = Double.valueOf(((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "CBM / Piece" }))).trim()).doubleValue();
        percbmrate = Double.valueOf(((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Per CBM Rate" }))).replace("$", "").trim()).doubleValue();
        landedcost = Double.valueOf(((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Landed Cost" }))).replace("$", "").trim()).doubleValue();
      }
      else if ((myorderdata.getSet(i).getItem(0).getStyleType() == com.ottocap.NewWorkFlow.client.StyleInformationData.StyleType.OVERSEAS_PREDESIGNED) || (myorderdata.getSet(i).getItem(0).getStyleType() == com.ottocap.NewWorkFlow.client.StyleInformationData.StyleType.OVERSEAS_NONOTTO) || (myorderdata.getSet(i).getItem(0).getStyleType() == com.ottocap.NewWorkFlow.client.StyleInformationData.StyleType.OVERSEAS_NONOTTO_SHIRTS)) {
        Record sqlrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_pre-designed" })).where(new org.jooq.Condition[] { DSL.fieldByName(new String[] { "Style" }).equal(myorderdata.getSet(i).getItem(0).getStyleNumber()) }).limit(1).fetchOne();
        cbmpiece = Double.valueOf(((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "CBM / Piece" }))).trim()).doubleValue();
        percbmrate = Double.valueOf(((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Per CBM Rate" }))).replace("$", "").trim()).doubleValue();
        landedcost = Double.valueOf(((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Landed Cost" }))).replace("$", "").trim()).doubleValue();
      } else if (myorderdata.getSet(i).getItem(0).getStyleType() == com.ottocap.NewWorkFlow.client.StyleInformationData.StyleType.OVERSEAS_CUSTOMSTYLE) {
        Record sqlrecord = null;
        if ((sqlrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_overseas_customstyle" })).where(new org.jooq.Condition[] { DSL.fieldByName(new String[] { "Style" }).equal(myorderdata.getSet(i).getItem(0).getStyleNumber()) }).and(DSL.fieldByName(new String[] { "Profile" }).equal(myorderdata.getSet(i).getItem(0).getProfile())).and(DSL.fieldByName(new String[] { "WorkFlow Fabic Name" }).equal(myorderdata.getSet(i).getItem(0).getFrontPanelFabric())).limit(1).fetchOne()) != null) {
          cbmpiece = Double.valueOf(((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "CBM / Piece" }))).trim()).doubleValue();
          percbmrate = Double.valueOf(((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Per CBM Rate" }))).replace("$", "").trim()).doubleValue();
          landedcost = Double.valueOf(((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Landed Cost" }))).replace("$", "").trim()).doubleValue();
        } else if ((sqlrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_overseas_customstyle" })).where(new org.jooq.Condition[] { DSL.fieldByName(new String[] { "Style" }).equal(myorderdata.getSet(i).getItem(0).getStyleNumber()) }).and(DSL.fieldByName(new String[] { "Profile" }).equal(myorderdata.getSet(i).getItem(0).getProfile())).and(DSL.fieldByName(new String[] { "WorkFlow Fabic Name" }).equal("Custom")).limit(1).fetchOne()) != null) {
          cbmpiece = Double.valueOf(((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "CBM / Piece" }))).trim()).doubleValue();
          percbmrate = Double.valueOf(((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Per CBM Rate" }))).replace("$", "").trim()).doubleValue();
          landedcost = Double.valueOf(((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Landed Cost" }))).replace("$", "").trim()).doubleValue();
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
