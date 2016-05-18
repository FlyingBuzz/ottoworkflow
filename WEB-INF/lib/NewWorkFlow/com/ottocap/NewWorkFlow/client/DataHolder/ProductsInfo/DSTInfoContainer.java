package com.ottocap.NewWorkFlow.client.DataHolder.ProductsInfo;

import com.google.gwt.json.client.JSONObject;

public class DSTInfoContainer
{
  private String filename;
  private int totalstitchcount;
  private double logosizewidth;
  private double logosizeheight;
  private com.google.gwt.json.client.JSONArray thecolorway;
  
  public DSTInfoContainer(JSONObject jsonObject)
  {
    this.filename = jsonObject.get("Filename").isString().stringValue();
    this.totalstitchcount = ((int)jsonObject.get("Total Stitch Count").isNumber().doubleValue());
    this.logosizewidth = jsonObject.get("Logo Size Width").isNumber().doubleValue();
    this.logosizeheight = jsonObject.get("Logo Size Height").isNumber().doubleValue();
    this.thecolorway = jsonObject.get("Colorway").isArray();
  }
  
  public int getColorwayCount() {
    return this.thecolorway.size();
  }
  
  public int getStitchCount(int colorway) {
    return (int)this.thecolorway.get(colorway).isObject().get("Stitches Per Color").isNumber().doubleValue();
  }
  
  public int getTotalStitchCount() {
    return this.totalstitchcount;
  }
  
  public double getLogoSizeWidth() {
    return this.logosizewidth;
  }
  
  public double getLogoSizeHeight() {
    return this.logosizeheight;
  }
  
  public String getFilename() {
    return this.filename;
  }
}
