package com.ottocap.NewWorkFlow.server.ExtendOrderData;

import com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogoColorway;


public class ExtendOrderDataLogoColorway
  extends OrderDataLogoColorway
{
  private static final long serialVersionUID = 1L;
  private String convertcode;
  private String convertcolorname;
  private String logocolorcodename;
  private String convertcodecolorvalue;
  private String logocolorcodevalue;
  
  public String getConvertCodeColorValue()
  {
    return this.convertcodecolorvalue;
  }
  
  public void setConvertCodeColorValue(String convertcodecolorvalue) {
    this.convertcodecolorvalue = convertcodecolorvalue;
  }
  
  public String getConvertCode() {
    return this.convertcode;
  }
  
  public void setConvertCode(String convertcode) {
    this.convertcode = convertcode;
  }
  
  public String getConvertColorName() {
    return this.convertcolorname;
  }
  
  public void setConvertColorName(String convertcolorname) {
    this.convertcolorname = convertcolorname;
  }
  
  public String getLogoColorCodeName() {
    return this.logocolorcodename;
  }
  
  public void setLogoColorCodeName(String logocolorcodename) {
    this.logocolorcodename = logocolorcodename;
  }
  
  public String getLogoColorCodeValue() {
    return this.logocolorcodevalue;
  }
  
  public void setLogoColorCodeValue(String logocolorcodevalue) {
    this.logocolorcodevalue = logocolorcodevalue;
  }
  
  public ExtendOrderDataLogoColorway copy() {
    ExtendOrderDataLogoColorway orderdatalogocolorway = new ExtendOrderDataLogoColorway();
    orderdatalogocolorway.setLogoColorCode(getLogoColorCode());
    orderdatalogocolorway.setColorCodeComment(getColorCodeComment());
    orderdatalogocolorway.setThreadType(getThreadType());
    orderdatalogocolorway.setStitches(getStitches());
    
    orderdatalogocolorway.setConvertCode(getConvertCode());
    orderdatalogocolorway.setConvertColorName(getConvertColorName());
    orderdatalogocolorway.setLogoColorCodeName(getLogoColorCodeName());
    orderdatalogocolorway.setConvertCodeColorValue(getConvertCodeColorValue());
    
    return orderdatalogocolorway;
  }
}
