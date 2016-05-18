package com.ottocap.NewWorkFlow.server.PriceCalculation.Helper;

import com.ottocap.NewWorkFlow.client.StyleInformationData.StyleType;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderData;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataItem;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataLogo;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataSet;
import java.util.TreeSet;







public class OverseasFreeProductSampleCalculator
{
  int totalfree = 0;
  boolean[] itemalreadyused = null;
  ExtendOrderData myorderdata;
  
  public OverseasFreeProductSampleCalculator(ExtendOrderData orderdata) {
    this.myorderdata = orderdata;
    this.itemalreadyused = new boolean[this.myorderdata.getSetCount()];
    
    for (int i = 0; i < this.myorderdata.getSetCount(); i++) {
      getTotalUniqueItem(i);
    }
  }
  
  private void getTotalUniqueItem(int item)
  {
    if (this.itemalreadyused[item] != 0)
      return;
    if (item + 1 == this.myorderdata.getSetCount()) {
      this.itemalreadyused[item] = true;
      int itemquantity = this.myorderdata.getSet(item).getItem(0).getQuantity().intValue();
      
      int totalfreetimes = (int)Math.floor(itemquantity / 720.0D);
      int maxtotalfree = totalfreetimes * 2;
      
      int productsampletotalship = 0;
      if (this.myorderdata.getSet(item).getItem(0).getProductSampleTotalShip() != null) {
        productsampletotalship = this.myorderdata.getSet(item).getItem(0).getProductSampleTotalShip().intValue();
      }
      int productsampletotalemail = 0;
      if (this.myorderdata.getSet(item).getItem(0).getProductSampleTotalEmail() != null) {
        productsampletotalemail = this.myorderdata.getSet(item).getItem(0).getProductSampleTotalEmail().intValue();
      }
      
      int emailandshipquantity = productsampletotalship + productsampletotalemail;
      
      if (maxtotalfree > emailandshipquantity) {
        this.totalfree += emailandshipquantity;
      } else {
        this.totalfree += maxtotalfree;
      }
    }
    else {
      this.itemalreadyused[item] = true;
      int itemquantity = this.myorderdata.getSet(item).getItem(0).getQuantity().intValue();
      
      int productsampletotalship = 0;
      if (this.myorderdata.getSet(item).getItem(0).getProductSampleTotalShip() != null) {
        productsampletotalship = this.myorderdata.getSet(item).getItem(0).getProductSampleTotalShip().intValue();
      }
      int productsampletotalemail = 0;
      if (this.myorderdata.getSet(item).getItem(0).getProductSampleTotalEmail() != null) {
        productsampletotalemail = this.myorderdata.getSet(item).getItem(0).getProductSampleTotalEmail().intValue();
      }
      
      int emailandshipquantity = productsampletotalship + productsampletotalemail;
      
      for (int i = item + 1; i < this.myorderdata.getSetCount(); i++) {
        if ((styleSame(item, i)) && (logoSame(item, i))) {
          this.itemalreadyused[i] = true;
          itemquantity += this.myorderdata.getSet(i).getItem(0).getQuantity().intValue();
          
          int comapredproductsampletotalship = 0;
          if (this.myorderdata.getSet(i).getItem(0).getProductSampleTotalShip() != null) {
            comapredproductsampletotalship = this.myorderdata.getSet(i).getItem(0).getProductSampleTotalShip().intValue();
          }
          int comparedproductsampletotalemail = 0;
          if (this.myorderdata.getSet(i).getItem(0).getProductSampleTotalEmail() != null) {
            comparedproductsampletotalemail = this.myorderdata.getSet(i).getItem(0).getProductSampleTotalEmail().intValue();
          }
          
          emailandshipquantity += comapredproductsampletotalship + comparedproductsampletotalemail;
        }
      }
      
      int totalfreetimes = (int)Math.floor(itemquantity / 720.0D);
      int maxtotalfree = totalfreetimes * 2;
      if (maxtotalfree > emailandshipquantity) {
        this.totalfree += emailandshipquantity;
      } else {
        this.totalfree += maxtotalfree;
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
    if (this.myorderdata.getSet(compareitem).getItem(0).getStyleType() != StyleInformationData.StyleType.OVERSEAS_INSTOCK) {
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
  
  public int getTotalProductSampleFree()
  {
    return this.totalfree;
  }
}
