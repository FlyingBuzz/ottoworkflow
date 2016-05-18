package com.ottocap.NewWorkFlow.server.PriceCalculation.Helper;

import com.ottocap.NewWorkFlow.client.StyleInformationData.StyleType;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderData;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataItem;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataLogo;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataSet;
import com.ottocap.NewWorkFlow.server.Settings;
import java.util.TreeMap;







public class OverseasPriceHelper
{
  ExtendOrderData myorderdata;
  TreeMap<String, Integer> samestyleuniquelogo = new TreeMap();
  TreeMap<String, Integer> samestylelogo = new TreeMap();
  TreeMap<String, Integer> samestylenologo = new TreeMap();
  TreeMap<String, Integer> samevendorlogo = new TreeMap();
  TreeMap<String, Integer> samevendorcaplogo = new TreeMap();
  int[] totalvendoritems = new int[Settings.TotalVendors];
  int totalitems;
  
  public OverseasPriceHelper(ExtendOrderData orderdata) throws Exception {
    this.myorderdata = orderdata;
    
    for (int i = 0; i < this.myorderdata.getSetCount(); i++) {
      String stylenumber = this.myorderdata.getSet(i).getItem(0).getStyleNumber();
      
      if (this.myorderdata.getSet(i).getItem(0).getStyleType() == StyleInformationData.StyleType.OVERSEAS_INSTOCK) {
        stylenumber = this.myorderdata.getSet(i).getItem(0).getSameStyle();
      }
      
      if ((this.myorderdata.getSet(i).getItem(0).getCustomStyleName() != null) && (!this.myorderdata.getSet(i).getItem(0).getCustomStyleName().trim().equals(""))) {
        stylenumber = this.myorderdata.getSet(i).getItem(0).getCustomStyleName().trim();
      }
      
      this.totalvendoritems[this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber()] += this.myorderdata.getSet(i).getItem(0).getQuantity().intValue();
      this.totalitems += this.myorderdata.getSet(i).getItem(0).getQuantity().intValue();
      
      TreeMap<String, Boolean> samelogoinitem = new TreeMap();
      if (this.myorderdata.getSet(i).getItem(0).getAdvanceType().equals("AB")) {
        String uniquestylekey = stylenumber + this.myorderdata.getSet(i).getItem(0).getVisorStyleNumber() + this.myorderdata.getSet(i).getItem(0).getClosureStyleNumber() + this.myorderdata.getSet(i).getItem(0).getSweatbandStyleNumber() + this.myorderdata.getSet(i).getItem(0).getClosureStyleNumber() + this.myorderdata.getSet(i).getItem(0).getEyeletStyleNumber();
        if (!this.samestylenologo.containsKey(uniquestylekey)) {
          this.samestylenologo.put(uniquestylekey, Integer.valueOf(0));
        }
        this.samestylenologo.put(uniquestylekey, Integer.valueOf(((Integer)this.samestylenologo.get(uniquestylekey)).intValue() + this.myorderdata.getSet(i).getItem(0).getQuantity().intValue()));
      }
      for (int j = 0; j < this.myorderdata.getSet(i).getLogoCount(); j++)
      {
        String filename = getMatchingFilename(this.myorderdata.getSet(i).getLogo(j).getFilename());
        String dstfilename = getMatchingFilename(this.myorderdata.getSet(i).getLogo(j).getDstFilename());
        int thestitchcount = this.myorderdata.getSet(i).getLogo(j).getStitchCount() == null ? 0 : this.myorderdata.getSet(i).getLogo(j).getStitchCount().intValue();
        String uniquekey = stylenumber + this.myorderdata.getSet(i).getItem(0).getVisorStyleNumber() + this.myorderdata.getSet(i).getItem(0).getClosureStyleNumber() + this.myorderdata.getSet(i).getItem(0).getSweatbandStyleNumber() + this.myorderdata.getSet(i).getItem(0).getClosureStyleNumber() + this.myorderdata.getSet(i).getItem(0).getEyeletStyleNumber() + filename + dstfilename + thestitchcount + this.myorderdata.getSet(i).getLogo(j).getDecorationCount();
        String uniquefilekey = filename + dstfilename + thestitchcount + this.myorderdata.getSet(i).getLogo(j).getDecorationCount();
        String samevendorlogokey = this.myorderdata.getSet(i).getItem(0).getOverseasVendorNumber() + filename + dstfilename + thestitchcount + this.myorderdata.getSet(i).getLogo(j).getDecorationCount();
        
        if (!samelogoinitem.containsKey(uniquefilekey)) {
          if (!this.samestyleuniquelogo.containsKey(uniquekey)) {
            this.samestyleuniquelogo.put(uniquekey, Integer.valueOf(0));
          }
          this.samestyleuniquelogo.put(uniquekey, Integer.valueOf(((Integer)this.samestyleuniquelogo.get(uniquekey)).intValue() + this.myorderdata.getSet(i).getItem(0).getQuantity().intValue()));
          
          if (!this.samevendorcaplogo.containsKey(samevendorlogokey)) {
            this.samevendorcaplogo.put(samevendorlogokey, Integer.valueOf(0));
          }
          this.samevendorcaplogo.put(samevendorlogokey, Integer.valueOf(((Integer)this.samevendorcaplogo.get(samevendorlogokey)).intValue() + this.myorderdata.getSet(i).getItem(0).getQuantity().intValue()));
          
          samelogoinitem.put(uniquefilekey, Boolean.valueOf(true));
        }
        if (!this.samestylelogo.containsKey(uniquekey)) {
          this.samestylelogo.put(uniquekey, Integer.valueOf(0));
        }
        this.samestylelogo.put(uniquekey, Integer.valueOf(((Integer)this.samestylelogo.get(uniquekey)).intValue() + this.myorderdata.getSet(i).getItem(0).getQuantity().intValue()));
        
        if (!this.samevendorlogo.containsKey(samevendorlogokey)) {
          this.samevendorlogo.put(samevendorlogokey, Integer.valueOf(0));
        }
        this.samevendorlogo.put(samevendorlogokey, Integer.valueOf(((Integer)this.samevendorlogo.get(samevendorlogokey)).intValue() + this.myorderdata.getSet(i).getItem(0).getQuantity().intValue()));
      }
    }
  }
  

  public int getTotalItems()
  {
    return this.totalitems;
  }
  
  public int getTotalVendorItems(int vendornumber) {
    return this.totalvendoritems[vendornumber];
  }
  
  public int getSameVendorLogoQuantity(String samevendorlogokey) {
    return ((Integer)this.samevendorlogo.get(samevendorlogokey)).intValue();
  }
  
  public int getSameVendorCapsWithSameLogoQuantity(String samevendorlogokey) {
    return ((Integer)this.samevendorcaplogo.get(samevendorlogokey)).intValue();
  }
  

  public int getBlankStyleQuantity(int item)
    throws Exception
  {
    String stylenumber = this.myorderdata.getSet(item).getItem(0).getStyleNumber();
    
    if (this.myorderdata.getSet(item).getItem(0).getStyleType() == StyleInformationData.StyleType.OVERSEAS_INSTOCK) {
      stylenumber = this.myorderdata.getSet(item).getItem(0).getSameStyle();
    }
    
    if ((this.myorderdata.getSet(item).getItem(0).getCustomStyleName() != null) && (!this.myorderdata.getSet(item).getItem(0).getCustomStyleName().trim().equals(""))) {
      stylenumber = this.myorderdata.getSet(item).getItem(0).getCustomStyleName().trim();
    }
    
    String uniquestylekey = stylenumber + this.myorderdata.getSet(item).getItem(0).getVisorStyleNumber() + this.myorderdata.getSet(item).getItem(0).getClosureStyleNumber() + this.myorderdata.getSet(item).getItem(0).getSweatbandStyleNumber() + this.myorderdata.getSet(item).getItem(0).getClosureStyleNumber() + this.myorderdata.getSet(item).getItem(0).getEyeletStyleNumber();
    

    int blankquantity = ((Integer)this.samestylenologo.get(uniquestylekey)).intValue();
    

    for (int i = 0; i < this.myorderdata.getSetCount(); i++) {
      if (this.myorderdata.getSet(i).getLogoCount() > 0) {
        String otehrstylenumber = this.myorderdata.getSet(i).getItem(0).getStyleNumber();
        
        if (this.myorderdata.getSet(i).getItem(0).getStyleType() == StyleInformationData.StyleType.OVERSEAS_INSTOCK) {
          otehrstylenumber = this.myorderdata.getSet(i).getItem(0).getSameStyle();
        }
        
        if ((this.myorderdata.getSet(i).getItem(0).getCustomStyleName() != null) && (!this.myorderdata.getSet(i).getItem(0).getCustomStyleName().trim().equals(""))) {
          otehrstylenumber = this.myorderdata.getSet(i).getItem(0).getCustomStyleName().trim();
        }
        
        String otheruniquestylekey = otehrstylenumber + this.myorderdata.getSet(i).getItem(0).getVisorStyleNumber() + this.myorderdata.getSet(i).getItem(0).getClosureStyleNumber() + this.myorderdata.getSet(i).getItem(0).getSweatbandStyleNumber() + this.myorderdata.getSet(i).getItem(0).getClosureStyleNumber() + this.myorderdata.getSet(i).getItem(0).getEyeletStyleNumber();
        
        if (otheruniquestylekey.equals(uniquestylekey)) {
          blankquantity = Math.max(blankquantity, getLogoStyleQuantity(i));
        }
      }
    }
    
    return blankquantity;
  }
  
  public int getLogoStyleQuantity(int item) throws Exception
  {
    String stylenumber = this.myorderdata.getSet(item).getItem(0).getStyleNumber();
    
    if (this.myorderdata.getSet(item).getItem(0).getStyleType() == StyleInformationData.StyleType.OVERSEAS_INSTOCK) {
      stylenumber = this.myorderdata.getSet(item).getItem(0).getSameStyle();
    }
    
    if ((this.myorderdata.getSet(item).getItem(0).getCustomStyleName() != null) && (!this.myorderdata.getSet(item).getItem(0).getCustomStyleName().trim().equals(""))) {
      stylenumber = this.myorderdata.getSet(item).getItem(0).getCustomStyleName().trim();
    }
    

    int maxquantity = 0;
    for (int j = 0; j < this.myorderdata.getSet(item).getLogoCount(); j++) {
      String filename = getMatchingFilename(this.myorderdata.getSet(item).getLogo(j).getFilename());
      String dstfilename = getMatchingFilename(this.myorderdata.getSet(item).getLogo(j).getDstFilename());
      int thestitchcount = this.myorderdata.getSet(item).getLogo(j).getStitchCount() == null ? 0 : this.myorderdata.getSet(item).getLogo(j).getStitchCount().intValue();
      String uniquekey = stylenumber + this.myorderdata.getSet(item).getItem(0).getVisorStyleNumber() + this.myorderdata.getSet(item).getItem(0).getClosureStyleNumber() + this.myorderdata.getSet(item).getItem(0).getSweatbandStyleNumber() + this.myorderdata.getSet(item).getItem(0).getClosureStyleNumber() + this.myorderdata.getSet(item).getItem(0).getEyeletStyleNumber() + filename + dstfilename + thestitchcount + this.myorderdata.getSet(item).getLogo(j).getDecorationCount();
      



      maxquantity = Math.max(maxquantity, ((Integer)this.samestyleuniquelogo.get(uniquekey)).intValue());
    }
    return maxquantity;
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
