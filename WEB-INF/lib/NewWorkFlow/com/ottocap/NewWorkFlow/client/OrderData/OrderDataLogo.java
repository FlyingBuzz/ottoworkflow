package com.ottocap.NewWorkFlow.client.OrderData;

import java.io.Serializable;
import java.util.ArrayList;










public class OrderDataLogo
  extends OrderDataShared
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  protected ArrayList<OrderDataLogoColorway> colorways = new ArrayList();
  protected ArrayList<OrderDataLogoDecoration> decorations = new ArrayList();
  
  public OrderDataLogo() {
    set("threadbrand", "Madeira Polyneon");
  }
  
  public boolean getChangeHappened()
  {
    for (OrderDataLogoColorway currentcolorway : this.colorways) {
      if (currentcolorway.getChangeHappened()) {
        return true;
      }
    }
    for (OrderDataLogoDecoration currentdecoration : this.decorations) {
      if (currentdecoration.getChangeHappened()) {
        return true;
      }
    }
    
    return super.getChangeHappened();
  }
  
  public void setChangeHappened(boolean changehappened)
  {
    for (OrderDataLogoColorway currentcolorway : this.colorways) {
      currentcolorway.setChangeHappened(changehappened);
    }
    for (OrderDataLogoDecoration currentdecoration : this.decorations) {
      currentdecoration.setChangeHappened(changehappened);
    }
    super.setChangeHappened(changehappened);
  }
  
  public OrderDataLogoColorway addColorway() {
    this.changehappened = true;
    OrderDataLogoColorway newcolorway = new OrderDataLogoColorway();
    this.colorways.add(newcolorway);
    return newcolorway;
  }
  
  public void addColorway(OrderDataLogoColorway newcolorway) {
    this.changehappened = true;
    this.colorways.add(newcolorway);
  }
  
  public void removeColorway(int colorwaynumber) {
    this.changehappened = true;
    this.colorways.remove(colorwaynumber);
  }
  
  public void removeColorway(OrderDataLogoColorway colorway) {
    this.changehappened = true;
    this.colorways.remove(colorway);
  }
  
  public ArrayList<OrderDataLogoColorway> getColorways() {
    return this.colorways;
  }
  
  public OrderDataLogoColorway getColorway(int k) {
    return (OrderDataLogoColorway)this.colorways.get(k);
  }
  
  public int getColorwayCount() {
    return this.colorways.size();
  }
  
  public OrderDataLogoDecoration addDecoration() {
    this.changehappened = true;
    OrderDataLogoDecoration newdecoration = new OrderDataLogoDecoration();
    this.decorations.add(newdecoration);
    return newdecoration;
  }
  
  public void addDecoration(OrderDataLogoDecoration newdecoration) {
    this.changehappened = true;
    this.decorations.add(newdecoration);
  }
  
  public void removeDecoration(int decorationnumber) {
    this.changehappened = true;
    this.decorations.remove(decorationnumber);
  }
  
  public void removeDecoration(OrderDataLogoDecoration decoration) {
    this.changehappened = true;
    this.decorations.remove(decoration);
  }
  
  public ArrayList<OrderDataLogoDecoration> getDecorations() {
    return this.decorations;
  }
  
  public OrderDataLogoDecoration getDecoration(Integer k) {
    return (OrderDataLogoDecoration)this.decorations.get(k.intValue());
  }
  
  public Integer getDecorationCount() {
    return Integer.valueOf(this.decorations.size());
  }
  
  public void setColorChangeAmount(Integer colorchangeamount) {
    set("colorchangeamount", colorchangeamount);
  }
  
  public Integer getColorChangeAmount() {
    return (Integer)get("colorchangeamount");
  }
  
  public void setFilename(String filename) {
    set("filename", filename);
  }
  
  public String getFilename() {
    return (String)get("filename", "");
  }
  
  public void setLogoLocation(String logolocation) {
    set("logolocation", logolocation);
  }
  
  public String getLogoLocation() {
    return (String)get("logolocation", "");
  }
  
  public void setDecoration(String decoration) {
    set("decoration", decoration);
  }
  
  public String getDecoration() {
    return (String)get("decoration", "");
  }
  
  public void setLogoSizeWidth(Double logosizewidth) {
    set("logosizewidth", logosizewidth);
  }
  
  public Double getLogoSizeWidth() {
    return (Double)get("logosizewidth");
  }
  
  public void setLogoSizeHeight(Double logosizeheight) {
    set("logosizeheight", logosizeheight);
  }
  
  public Double getLogoSizeHeight() {
    return (Double)get("logosizeheight");
  }
  
  public void setNumberOfColor(Integer numofcolor) {
    set("numofcolor", numofcolor);
  }
  
  public Integer getNumberOfColor() {
    return (Integer)get("numofcolor");
  }
  
  public void setStitchCount(Integer stitchcount) {
    set("stitchcount", stitchcount);
  }
  
  public Integer getStitchCount() {
    return (Integer)get("stitchcount");
  }
  
  public void setArtworkChargePerHour(Double artworkchargehour) {
    set("artworkchargehour", artworkchargehour);
  }
  
  public Double getArtworkChargePerHour() {
    return (Double)get("artworkchargehour");
  }
  
  public void setArtworkType(String artworktype) {
    set("artworktype", artworktype);
  }
  
  public String getArtworkType() {
    return (String)get("artworktype", "");
  }
  
  public void setArtworkTypeComments(String artworktypecomments) {
    set("artworktypecomments", artworktypecomments);
  }
  
  public String getArtworkTypeComments() {
    return (String)get("artworktypecomments", "");
  }
  
  public void setDigitizing(boolean digitizing) {
    set("digitizing", Boolean.valueOf(digitizing));
  }
  
  public boolean getDigitizing() {
    return ((Boolean)get("digitizing", Boolean.valueOf(false))).booleanValue();
  }
  
  public void setFilmCharge(boolean filmcharge) {
    set("filmcharge", Boolean.valueOf(filmcharge));
  }
  
  public boolean getFilmCharge() {
    return ((Boolean)get("filmcharge", Boolean.valueOf(false))).booleanValue();
  }
  
  public void setPMSMatch(boolean pmsmatch) {
    set("pmsmatch", Boolean.valueOf(pmsmatch));
  }
  
  public boolean getPMSMatch() {
    return ((Boolean)get("pmsmatch", Boolean.valueOf(false))).booleanValue();
  }
  
  public void setTapeEdit(boolean tapeedit)
  {
    set("tapeedit", Boolean.valueOf(tapeedit));
  }
  
  public boolean getTapeEdit() {
    return ((Boolean)get("tapeedit", Boolean.valueOf(false))).booleanValue();
  }
  
  public void setFlashCharge(boolean flashcharge) {
    set("flashcharge", Boolean.valueOf(flashcharge));
  }
  
  public boolean getFlashCharge() {
    return ((Boolean)get("flashcharge", Boolean.valueOf(false))).booleanValue();
  }
  
  public void setMetallicThread(boolean metallicthread) {
    set("metallicthread", Boolean.valueOf(metallicthread));
  }
  
  public boolean getMetallicThread() {
    return ((Boolean)get("metallicthread", Boolean.valueOf(false))).booleanValue();
  }
  
  public void setNeonThread(boolean neonthread) {
    set("neonthread", Boolean.valueOf(neonthread));
  }
  
  public boolean getNeonThread() {
    return ((Boolean)get("neonthread", Boolean.valueOf(false))).booleanValue();
  }
  
  public void setSpecialInk(boolean specialink) {
    set("specialink", Boolean.valueOf(specialink));
  }
  
  public boolean getSpecialInk() {
    return ((Boolean)get("specialink", Boolean.valueOf(false))).booleanValue();
  }
  
  public void setColorChange(boolean colorchange) {
    set("colorchange", Boolean.valueOf(colorchange));
  }
  
  public boolean getColorChange() {
    return ((Boolean)get("colorchange", Boolean.valueOf(false))).booleanValue();
  }
  
  public void setColorDescription(String colordescription) {
    set("colordescription", colordescription);
  }
  
  public String getColorDescription() {
    return (String)get("colordescription", "");
  }
  
  public void setThreadBrand(String threadbrand) {
    set("threadbrand", threadbrand);
  }
  
  public String getThreadBrand() {
    return (String)get("threadbrand", "Madeira Polyneon");
  }
  
  public void setSwatchEmail(boolean swatchemail) {
    set("swatchemail", Boolean.valueOf(swatchemail));
  }
  
  public boolean getSwatchEmail() {
    return ((Boolean)get("swatchemail", Boolean.valueOf(false))).booleanValue();
  }
  
  public void setSwatchShip(boolean swatchship) {
    set("swatchship", Boolean.valueOf(swatchship));
  }
  
  public boolean getSwatchShip() {
    return ((Boolean)get("swatchship", Boolean.valueOf(false))).booleanValue();
  }
  
  public void setSwatchToDo(Integer swatchtodo) {
    set("swatchtodo", swatchtodo);
  }
  
  public Integer getSwatchToDo() {
    return (Integer)get("swatchtodo");
  }
  
  public void setSwatchTotalDone(Integer swatchtotaldone) {
    set("swatchtotaldone", swatchtotaldone);
  }
  
  public Integer getSwatchTotalDone() {
    return (Integer)get("swatchtotaldone");
  }
  
  public void setFilmSetupCharge(Double filmsetupcharge) {
    set("filmsetupcharge", filmsetupcharge);
  }
  
  public Double getFilmSetupCharge() {
    return (Double)get("filmsetupcharge");
  }
  
  public void setLogoSizeChoice(String logosizechoice) {
    set("logosizechoice", logosizechoice);
  }
  
  public String getLogoSizeChoice() {
    return (String)get("logosizechoice", "");
  }
  
  public void setDstFilename(String dstfilename) {
    set("dstfilename", dstfilename);
  }
  
  public String getDstFilename() {
    return (String)get("dstfilename", "");
  }
  
  public void setNameDropLogo(String namedroplogo) {
    set("namedroplogo", namedroplogo);
  }
  
  public String getNameDropLogo() {
    return (String)get("namedroplogo", "");
  }
  
  public void setSwatchTotalShip(Integer swatchtotalship) {
    set("swatchtotalship", swatchtotalship);
  }
  
  public Integer getSwatchTotalShip() {
    return (Integer)get("swatchtotalship");
  }
  
  public void setSwatchTotalEmail(Integer swatchtotalemail) {
    set("swatchtotalemail", swatchtotalemail);
  }
  
  public Integer getSwatchTotalEmail() {
    return (Integer)get("swatchtotalemail");
  }
  
  public void setCustomerLogoPrice(Double customerlogoprice) {
    set("customerlogoprice", customerlogoprice);
  }
  
  public Double getCustomerLogoPrice() {
    return (Double)get("customerlogoprice");
  }
  
  public void setVendorLogoPrice(Double vendorlogoprice) {
    set("vendorlogoprice", vendorlogoprice);
  }
  
  public Double getVendorLogoPrice() {
    return (Double)get("vendorlogoprice");
  }
  
  public OrderDataLogo copy() {
    OrderDataLogo orderdatalogo = new OrderDataLogo();
    orderdatalogo.setFilename(getFilename());
    orderdatalogo.setLogoLocation(getLogoLocation());
    orderdatalogo.setDecoration(getDecoration());
    orderdatalogo.setLogoSizeWidth(getLogoSizeWidth());
    orderdatalogo.setLogoSizeHeight(getLogoSizeHeight());
    orderdatalogo.setNumberOfColor(getNumberOfColor());
    orderdatalogo.setStitchCount(getStitchCount());
    orderdatalogo.setArtworkChargePerHour(getArtworkChargePerHour());
    orderdatalogo.setArtworkType(getArtworkType());
    orderdatalogo.setArtworkTypeComments(getArtworkTypeComments());
    orderdatalogo.setDigitizing(getDigitizing());
    orderdatalogo.setFilmCharge(getFilmCharge());
    orderdatalogo.setTapeEdit(getTapeEdit());
    orderdatalogo.setFlashCharge(getFlashCharge());
    orderdatalogo.setMetallicThread(getMetallicThread());
    orderdatalogo.setNeonThread(getNeonThread());
    orderdatalogo.setSpecialInk(getSpecialInk());
    orderdatalogo.setColorChange(getColorChange());
    orderdatalogo.setColorChangeAmount(getColorChangeAmount());
    orderdatalogo.setColorDescription(getColorDescription());
    orderdatalogo.setThreadBrand(getThreadBrand());
    orderdatalogo.setSwatchEmail(getSwatchEmail());
    orderdatalogo.setSwatchShip(getSwatchShip());
    orderdatalogo.setSwatchToDo(getSwatchToDo());
    orderdatalogo.setSwatchTotalDone(getSwatchTotalDone());
    orderdatalogo.setFilmSetupCharge(getFilmSetupCharge());
    orderdatalogo.setLogoSizeChoice(getLogoSizeChoice());
    orderdatalogo.setDstFilename(getDstFilename());
    orderdatalogo.setNameDropLogo(getNameDropLogo());
    orderdatalogo.setSwatchTotalShip(getSwatchTotalShip());
    orderdatalogo.setSwatchTotalEmail(getSwatchTotalEmail());
    orderdatalogo.setCustomerLogoPrice(getCustomerLogoPrice());
    orderdatalogo.setVendorLogoPrice(getVendorLogoPrice());
    for (OrderDataLogoColorway currentcolorway : this.colorways) {
      orderdatalogo.addColorway(currentcolorway.copy());
    }
    for (OrderDataLogoDecoration currentdecoration : this.decorations) {
      orderdatalogo.addDecoration(currentdecoration.copy());
    }
    
    return orderdatalogo;
  }
}
