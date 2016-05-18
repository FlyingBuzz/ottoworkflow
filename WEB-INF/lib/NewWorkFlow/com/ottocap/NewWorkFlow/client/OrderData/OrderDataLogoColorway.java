package com.ottocap.NewWorkFlow.client.OrderData;

import java.io.Serializable;





public class OrderDataLogoColorway
  extends OrderDataShared
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  
  public OrderDataLogoColorway()
  {
    set("threadtype", "Pantone");
    set("inktype", "Plastisol Ink");
  }
  
  public void setLogoColorCode(String logocolorcode) {
    set("logocolorcode", logocolorcode);
  }
  
  public String getLogoColorCode() {
    return (String)get("logocolorcode", "");
  }
  
  public void setColorCodeComment(String colorcodecomment) {
    set("colorcodecomment", colorcodecomment);
  }
  
  public String getColorCodeComment() {
    return (String)get("colorcodecomment", "");
  }
  
  public void setThreadType(String threadtype) {
    set("threadtype", threadtype);
  }
  
  public String getThreadType() {
    return (String)get("threadtype", "Pantone");
  }
  
  public void setInkType(String inktype) {
    set("inktype", inktype);
  }
  
  public String getInkType() {
    return (String)get("inktype", "Plastisol Ink");
  }
  
  public void setFlashCharge(boolean flashcharge) {
    set("flashcharge", Boolean.valueOf(flashcharge));
  }
  
  public boolean getFlashCharge() {
    return ((Boolean)get("flashcharge", Boolean.valueOf(false))).booleanValue();
  }
  
  public void setStitches(Integer stitches) {
    set("stitches", stitches);
  }
  
  public Integer getStitches() {
    return (Integer)get("stitches");
  }
  
  public OrderDataLogoColorway copy() {
    OrderDataLogoColorway orderdatalogocolorway = new OrderDataLogoColorway();
    orderdatalogocolorway.setLogoColorCode(getLogoColorCode());
    orderdatalogocolorway.setColorCodeComment(getColorCodeComment());
    orderdatalogocolorway.setThreadType(getThreadType());
    orderdatalogocolorway.setStitches(getStitches());
    
    return orderdatalogocolorway;
  }
}
