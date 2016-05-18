package com.ottocap.NewWorkFlow.server.PriceCalculation.Helper;

import com.ottocap.NewWorkFlow.client.StyleInformationData.StyleType;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderData;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataItem;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataLogo;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataSet;
import com.ottocap.NewWorkFlow.server.JOOQSQL;
import java.util.TreeSet;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.jooq.SelectOffsetStep;
import org.jooq.SelectWhereStep;
import org.jooq.impl.DSL;



public class JOOQ_OverseasVendorFreeProductSampleCalculator
{
  boolean[] itemalreadyused = null;
  ExtendOrderData myorderdata = null;
  boolean[] itemfree = null;
  int[] sameitemquantity = null;
  Record myvendorminqty = null;
  
  public JOOQ_OverseasVendorFreeProductSampleCalculator(ExtendOrderData orderdata) throws Exception {
    this.myorderdata = orderdata;
    
    this.myvendorminqty = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "overseas_price_charges" })).where(new Condition[] { DSL.fieldByName(new String[] { "name" }).equal("minqtyforfreesample") }).limit(1).fetchOne();
    
    this.itemalreadyused = new boolean[this.myorderdata.getSetCount()];
    this.itemfree = new boolean[this.myorderdata.getSetCount()];
    this.sameitemquantity = new int[this.myorderdata.getSetCount()];
    
    for (int i = 0; i < this.myorderdata.getSetCount(); i++) {
      getTotalUniqueItem(i);
    }
  }
  
  public int getSameItemQuantity(int item)
  {
    return this.sameitemquantity[item];
  }
  
  public boolean checkItemFree(int item) {
    return this.itemfree[item];
  }
  
  private void getTotalUniqueItem(int item) throws Exception {
    if (this.itemalreadyused[item] != 0)
      return;
    if (item + 1 == this.myorderdata.getSetCount()) {
      this.itemalreadyused[item] = true;
      int itemquantity = this.myorderdata.getSet(item).getItem(0).getQuantity().intValue();
      
      int mintobefree = 0;
      try {
        mintobefree = Integer.valueOf((String)this.myvendorminqty.getValue(DSL.fieldByName(new String[] { String.valueOf(this.myorderdata.getSet(item).getItem(0).getOverseasVendorNumber()) }))).intValue();
      }
      catch (Exception localException) {}
      
      this.sameitemquantity[item] = itemquantity;
      
      if (itemquantity >= mintobefree) {
        this.itemfree[item] = true;
      }
    }
    else {
      this.itemalreadyused[item] = true;
      boolean[] itemseton = new boolean[this.myorderdata.getSetCount()];
      int itemquantity = this.myorderdata.getSet(item).getItem(0).getQuantity().intValue();
      
      for (int i = item + 1; i < this.myorderdata.getSetCount(); i++) {
        if ((styleSame(item, i)) && (logoSame(item, i))) {
          this.itemalreadyused[i] = true;
          itemseton[i] = true;
          itemquantity += this.myorderdata.getSet(i).getItem(0).getQuantity().intValue();
        }
      }
      
      int mintobefree = 0;
      try {
        mintobefree = Integer.valueOf((String)this.myvendorminqty.getValue(DSL.fieldByName(new String[] { String.valueOf(this.myorderdata.getSet(item).getItem(0).getOverseasVendorNumber()) }))).intValue();
      }
      catch (Exception localException1) {}
      
      this.sameitemquantity[item] = itemquantity;
      
      if (itemquantity >= mintobefree)
      {
        this.itemfree[item] = true;
        for (int i = item + 1; i < this.myorderdata.getSetCount(); i++) {
          if (itemseton[i] != 0)
          {
            this.itemfree[i] = true;
            this.sameitemquantity[i] = itemquantity;
          }
        }
      }
    }
  }
  
  private boolean styleSame(int item, int compareitem)
  {
    String stylenumber = this.myorderdata.getSet(item).getItem(0).getStyleNumber();
    if (this.myorderdata.getSet(item).getItem(0).getStyleType() == StyleInformationData.StyleType.OVERSEAS_INSTOCK) {
      stylenumber = this.myorderdata.getSet(item).getItem(0).getSameStyle();
    }
    String uniquestylekey = stylenumber + this.myorderdata.getSet(item).getItem(0).getVisorStyleNumber() + this.myorderdata.getSet(item).getItem(0).getClosureStyleNumber() + this.myorderdata.getSet(item).getItem(0).getSweatbandStyleNumber() + this.myorderdata.getSet(item).getItem(0).getClosureStyleNumber() + this.myorderdata.getSet(item).getItem(0).getEyeletStyleNumber();
    
    String comparedstylenumber = this.myorderdata.getSet(compareitem).getItem(0).getStyleNumber();
    if (this.myorderdata.getSet(compareitem).getItem(0).getStyleType() == StyleInformationData.StyleType.OVERSEAS_INSTOCK) {
      comparedstylenumber = this.myorderdata.getSet(compareitem).getItem(0).getSameStyle();
    }
    String compareduniquestylekey = comparedstylenumber + this.myorderdata.getSet(compareitem).getItem(0).getVisorStyleNumber() + this.myorderdata.getSet(compareitem).getItem(0).getClosureStyleNumber() + this.myorderdata.getSet(compareitem).getItem(0).getSweatbandStyleNumber() + this.myorderdata.getSet(compareitem).getItem(0).getClosureStyleNumber() + this.myorderdata.getSet(compareitem).getItem(0).getEyeletStyleNumber();
    
    return uniquestylekey.equals(compareduniquestylekey);
  }
  
  private boolean logoSame(int item, int compareitem) {
    if (this.myorderdata.getSet(item).getLogoCount() != this.myorderdata.getSet(compareitem).getLogoCount()) {
      return false;
    }
    
    TreeSet<String> itemlogos = new TreeSet();
    for (int j = 0; j < this.myorderdata.getSet(item).getLogoCount(); j++)
    {
      String filename = getMatchingFilename(this.myorderdata.getSet(item).getLogo(j).getFilename());
      String dstfilename = getMatchingFilename(this.myorderdata.getSet(item).getLogo(j).getDstFilename());
      String uniquefilekey = filename + dstfilename + this.myorderdata.getSet(item).getLogo(j).getStitchCount() + this.myorderdata.getSet(item).getLogo(j).getDecorationCount();
      itemlogos.add(uniquefilekey);
    }
    
    for (int j = 0; j < this.myorderdata.getSet(compareitem).getLogoCount(); j++)
    {
      String filename = getMatchingFilename(this.myorderdata.getSet(compareitem).getLogo(j).getFilename());
      String dstfilename = getMatchingFilename(this.myorderdata.getSet(compareitem).getLogo(j).getDstFilename());
      String uniquefilekey = filename + dstfilename + this.myorderdata.getSet(compareitem).getLogo(j).getStitchCount() + this.myorderdata.getSet(compareitem).getLogo(j).getDecorationCount();
      if (!itemlogos.contains(uniquefilekey)) {
        return false;
      }
    }
    
    return true;
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
}
