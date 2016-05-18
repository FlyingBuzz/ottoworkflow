package com.ottocap.NewWorkFlow.server.ExtendOrderData;

import com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogo;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogoColorway;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogoDecoration;
import java.util.ArrayList;

public class ExtendOrderDataLogo
  extends OrderDataLogo
{
  private static final long serialVersionUID = 1L;
  private String logolocationname;
  
  public ExtendOrderDataLogoColorway addColorway()
  {
    this.changehappened = true;
    ExtendOrderDataLogoColorway newcolorway = new ExtendOrderDataLogoColorway();
    this.colorways.add(newcolorway);
    return newcolorway;
  }
  
  public void addColorway(ExtendOrderDataLogoColorway newcolorway) {
    this.changehappened = true;
    this.colorways.add(newcolorway);
  }
  
  public ExtendOrderDataLogoColorway getColorway(int k) {
    return (ExtendOrderDataLogoColorway)this.colorways.get(k);
  }
  
  public ExtendOrderDataLogoDecoration addDecoration() {
    this.changehappened = true;
    ExtendOrderDataLogoDecoration newdecoration = new ExtendOrderDataLogoDecoration();
    this.decorations.add(newdecoration);
    return newdecoration;
  }
  
  public void addDecoration(ExtendOrderDataLogoDecoration newdecoration) {
    this.changehappened = true;
    this.decorations.add(newdecoration);
  }
  
  public ExtendOrderDataLogoDecoration getDecoration(Integer k) {
    return (ExtendOrderDataLogoDecoration)this.decorations.get(k.intValue());
  }
  
  public String getLogoSize() {
    double width = getLogoSizeWidth() != null ? getLogoSizeWidth().doubleValue() : 0.0D;
    double height = getLogoSizeHeight() != null ? getLogoSizeHeight().doubleValue() : 0.0D;
    if ((width == 0.0D) && (height == 0.0D))
      return "Proportional W & H";
    if (width == 0.0D)
      return "Proportional W By " + height + "\" H";
    if (height == 0.0D) {
      return width + "\" W By Proportional H";
    }
    return width + "\" W * " + height + "\" H";
  }
  
  public String getLogoLocationName()
  {
    return this.logolocationname;
  }
  
  public void setLogoLocationName(String logolocationname) {
    this.logolocationname = logolocationname;
  }
  
  public OrderDataLogo copy() {
    ExtendOrderDataLogo orderdatalogo = new ExtendOrderDataLogo();
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
    orderdatalogo.setPMSMatch(getPMSMatch());
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
    orderdatalogo.setSwatchTotalEmail(orderdatalogo.getSwatchTotalEmail());
    orderdatalogo.setCustomerLogoPrice(getCustomerLogoPrice());
    orderdatalogo.setVendorLogoPrice(orderdatalogo.getVendorLogoPrice());
    for (OrderDataLogoColorway currentcolorway : this.colorways) {
      orderdatalogo.addColorway(((ExtendOrderDataLogoColorway)currentcolorway).copy());
    }
    for (OrderDataLogoDecoration currentdecoration : this.decorations) {
      orderdatalogo.addDecoration(((ExtendOrderDataLogoDecoration)currentdecoration).copy());
    }
    
    orderdatalogo.setLogoLocationName(getLogoLocationName());
    
    return orderdatalogo;
  }
}
