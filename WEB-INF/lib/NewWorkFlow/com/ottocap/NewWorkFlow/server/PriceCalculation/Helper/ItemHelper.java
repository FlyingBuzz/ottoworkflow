package com.ottocap.NewWorkFlow.server.PriceCalculation.Helper;

import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderData;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataItem;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataLogo;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataSet;
import java.util.TreeMap;
import java.util.TreeSet;







public class ItemHelper
{
  private TreeMap<String, Integer> totalcaptypelogo = new TreeMap();
  private TreeMap<String, Integer> totallogocolorchange = new TreeMap();
  private TreeMap<String, Integer> totallogo = new TreeMap();
  private TreeMap<String, Integer> totallogoottoonly = new TreeMap();
  private TreeMap<String, Integer> totalstyleitem = new TreeMap();
  private TreeMap<String, Integer> totalcaptypelocation = new TreeMap();
  private TreeMap<String, Integer> totalsamestyledecorationname = new TreeMap();
  private TreeSet<String> samestylehasdecorationtype = new TreeSet();
  private boolean havenonotto = false;
  
  public ItemHelper(ExtendOrderData myorderdata)
  {
    for (int i = 0; i < myorderdata.getSetCount(); i++) {
      int quantity = myorderdata.getSet(i).getItem(0).getQuantity() != null ? myorderdata.getSet(i).getItem(0).getQuantity().intValue() : 0;
      String capflattype = myorderdata.getSet(i).getItem(0).getGoodsType();
      if ((myorderdata.getSet(i).getItem(0).getStyleNumber().equals("nonotto")) || (myorderdata.getSet(i).getItem(0).getStyleNumber().equals("nonottoflat"))) {
        this.havenonotto = true;
      }
      
      String samestylenumber = myorderdata.getSet(i).getItem(0).getSameStyle();
      if (!this.totalstyleitem.containsKey(samestylenumber)) {
        this.totalstyleitem.put(samestylenumber, Integer.valueOf(0));
      }
      this.totalstyleitem.put(samestylenumber, Integer.valueOf(((Integer)this.totalstyleitem.get(samestylenumber)).intValue() + quantity));
      
      for (int j = 0; j < myorderdata.getSet(i).getLogoCount(); j++) {
        String filename = myorderdata.getSet(i).getLogo(j).getFilename();
        String stitchcount = String.valueOf(myorderdata.getSet(i).getLogo(j).getStitchCount());
        String logolocation = myorderdata.getSet(i).getLogo(j).getLogoLocation();
        String decoration = myorderdata.getSet(i).getLogo(j).getDecoration();
        String uniquefilename = getUniqueCapTypeLogo(capflattype, filename, stitchcount);
        String uniquelogo = getUniqueLogo(filename, stitchcount);
        String uniquelocationfilename = getUniqueCapTypeLocationLogo(capflattype, logolocation, filename, stitchcount);
        String uniquesamestyledecorationname = getUniqueSameStyleDecorationName(decoration, samestylenumber, filename, stitchcount);
        String uniquesamestylecontainsdecoration = getSameStyleDecorationType(samestylenumber, decoration);
        
        if (!this.totalcaptypelogo.containsKey(uniquefilename)) {
          this.totalcaptypelogo.put(uniquefilename, Integer.valueOf(0));
        }
        this.totalcaptypelogo.put(uniquefilename, Integer.valueOf(((Integer)this.totalcaptypelogo.get(uniquefilename)).intValue() + quantity));
        
        if (!this.totallogo.containsKey(uniquelogo)) {
          this.totallogo.put(uniquelogo, Integer.valueOf(0));
        }
        this.totallogo.put(uniquelogo, Integer.valueOf(((Integer)this.totallogo.get(uniquelogo)).intValue() + quantity));
        
        if (!this.totallogoottoonly.containsKey(uniquelogo)) {
          this.totallogoottoonly.put(uniquelogo, Integer.valueOf(0));
        }
        if ((!myorderdata.getSet(i).getItem(0).getStyleNumber().equals("nonotto")) && (!myorderdata.getSet(i).getItem(0).getStyleNumber().equals("nonottoflat"))) {
          this.totallogoottoonly.put(uniquelogo, Integer.valueOf(((Integer)this.totallogoottoonly.get(uniquelogo)).intValue() + quantity));
        }
        
        if (!this.totallogocolorchange.containsKey(uniquelogo)) {
          this.totallogocolorchange.put(uniquelogo, Integer.valueOf(0));
        }
        if (Boolean.valueOf(myorderdata.getSet(i).getLogo(j).getColorChange()).booleanValue()) {
          this.totallogocolorchange.put(uniquelogo, Integer.valueOf(((Integer)this.totallogocolorchange.get(uniquelogo)).intValue() + 1));
        }
        
        if (!this.totalcaptypelocation.containsKey(uniquelocationfilename)) {
          this.totalcaptypelocation.put(uniquelocationfilename, Integer.valueOf(0));
        }
        this.totalcaptypelocation.put(uniquelocationfilename, Integer.valueOf(((Integer)this.totalcaptypelocation.get(uniquelocationfilename)).intValue() + quantity));
        
        if (!this.totalsamestyledecorationname.containsKey(uniquesamestyledecorationname)) {
          this.totalsamestyledecorationname.put(uniquesamestyledecorationname, Integer.valueOf(0));
        }
        this.totalsamestyledecorationname.put(uniquesamestyledecorationname, Integer.valueOf(((Integer)this.totalsamestyledecorationname.get(uniquesamestyledecorationname)).intValue() + quantity));
        
        this.samestylehasdecorationtype.add(uniquesamestylecontainsdecoration);
      }
    }
  }
  


  public boolean getHaveNonOtto()
  {
    return this.havenonotto;
  }
  
  public TreeMap<String, Integer> getCloneOfTotalStyleItem() {
    return new TreeMap(this.totalstyleitem);
  }
  
  public TreeSet<String> getCloneOfSameStyleDecorationType() {
    return new TreeSet(this.samestylehasdecorationtype);
  }
  
  public int getTotalSameStyleDecorationName(String uniquelocationfilename) {
    return ((Integer)this.totalsamestyledecorationname.get(uniquelocationfilename)).intValue();
  }
  
  public TreeMap<String, Integer> getCloneOfTotalSameStyleDecorationName() {
    return new TreeMap(this.totalsamestyledecorationname);
  }
  
  public int getTotalCapTypeLocationLogo(String uniquelocationfilename) {
    return ((Integer)this.totalcaptypelocation.get(uniquelocationfilename)).intValue();
  }
  
  public int getTotalCapTypeLogo(String uniquefilename) {
    return ((Integer)this.totalcaptypelogo.get(uniquefilename)).intValue();
  }
  
  public int getTotalLogo(String uniquefilename) {
    return ((Integer)this.totallogo.get(uniquefilename)).intValue();
  }
  
  public int getTotalLogoOttoOnly(String uniquefilename) {
    return ((Integer)this.totallogoottoonly.get(uniquefilename)).intValue();
  }
  
  public int getTotalLogoColorChange(String uniquecolorchange) {
    return ((Integer)this.totallogocolorchange.get(uniquecolorchange)).intValue();
  }
  
  public TreeMap<String, Integer> getCloneOfTotalLogoColorChange() {
    return new TreeMap(this.totallogocolorchange);
  }
  
  public String getUniqueSameStyleDecorationName(String decoration, String samestyle, String filename, String stitchcount) {
    return decoration + " <> " + samestyle + " <> " + getMatchingFilename(filename) + " <> " + stitchcount;
  }
  
  public String getUniqueCapTypeLogo(String capflattype, String filename, String stitchcount) {
    return capflattype + " <> " + getMatchingFilename(filename) + " <> " + stitchcount;
  }
  
  public String getUniqueCapTypeLocationLogo(String capflattype, String location, String filename, String stitchcount) {
    return capflattype + " <> " + location.trim() + " <> " + getMatchingFilename(filename) + " <> " + stitchcount;
  }
  
  public String getUniqueLogo(String filename, String stitchcount) {
    return getMatchingFilename(filename) + " <> " + stitchcount;
  }
  
  public String getSameStyleDecorationType(String samestyle, String decoration) {
    return samestyle + " <> " + decoration;
  }
  
  private String getMatchingFilename(String filename) {
    int fileextindex = filename.lastIndexOf(".");
    if (fileextindex != -1) {
      int substringindex = filename.lastIndexOf(".", fileextindex - 1);
      if (substringindex != -1) {
        return (filename.substring(0, substringindex) + filename.substring(fileextindex)).toLowerCase();
      }
    }
    
    return filename.toLowerCase();
  }
}
