package com.ottocap.NewWorkFlow.server.ExtendOrderData;

import com.ottocap.NewWorkFlow.client.OrderData.OrderDataItem;
import com.ottocap.NewWorkFlow.client.StyleInformationData.StyleType;



public class ExtendOrderDataItem
  extends OrderDataItem
{
  private static final long serialVersionUID = 1L;
  private int overseasvendornumber;
  private String crownstyle = "";
  private String material = "";
  private String advancetype = "";
  private StyleInformationData.StyleType styletype;
  private String category = "";
  private String colorcodename = "";
  private String frontpanelcolorname = "";
  private String sidebackpanelcolorname = "";
  private String backpanelcolorname = "";
  private String sidepanelcolorname = "";
  private String primaryvisorcolorname = "";
  private String visortrimcolorname = "";
  private String visorsandwichcolorname = "";
  private String undervisorcolorname = "";
  private String distressedvisorinsidecolorname = "";
  private String closurestrapcolorname = "";
  private String closurestylenumbername = "";
  private String eyeletstylenumbername = "";
  private String fronteyeletcolorname = "";
  private String sidebackeyeletcolorname = "";
  private String backeyeletcolorname = "";
  private String sideeyeletcolorname = "";
  private String buttoncolorname = "";
  private String innertapingcolorname = "";
  private String sweatbandstylenumbername = "";
  private String sweatbandcolorname = "";
  private String sweatbandstripecolorname = "";
  private String visorstylenumbername = "";
  private String panelstitchcolorname = "";
  private String visorstitchcolorname = "";
  private String frontpanelfabricname = "";
  private String frontpanelcontentname = "";
  private String frontpanelfabricspecname = "";
  private String sidebackpanelfabricname = "";
  private String sidebackpanelcontentname = "";
  private String sidebackpanelfabricspecname = "";
  private String backpanelfabricname = "";
  private String backpanelcontentname = "";
  private String backpanelfabricspecname = "";
  private String sidepanelfabricname = "";
  private String sidepanelcontentname = "";
  private String sidepanelfabricspecname = "";
  private String factoryvisorcode = "";
  private String goodstype = "";
  private String samestyle = "";
  private int orgsetnum;
  private int orgitemnum;
  private SideView sideview;
  private boolean specialitemprice;
  
  public static enum SideView {
    FRONT,  INSIDE,  LEFT,  RIGHT,  BACK;
  }
  
  public void setSideView(SideView sideview) {
    this.sideview = sideview;
  }
  
  public SideView getSideView() {
    return this.sideview;
  }
  
  public void setOrgSetNum(int orgsetnum) {
    this.orgsetnum = orgsetnum;
  }
  
  public int getOrgSetNum() {
    return this.orgsetnum;
  }
  
  public void setOrgItemNum(int orgitemnum) {
    this.orgitemnum = orgitemnum;
  }
  
  public int getOrgItemNum() {
    return this.orgitemnum;
  }
  
  public void setSameStyle(String samestyle) {
    this.samestyle = samestyle;
  }
  
  public String getSameStyle() {
    return this.samestyle;
  }
  
  public void setGoodsType(String goodstype) {
    this.goodstype = goodstype;
  }
  
  public String getGoodsType() {
    return this.goodstype;
  }
  
  public void setOverseasVendorNumber(int overseasvendornumber) {
    this.overseasvendornumber = overseasvendornumber;
  }
  
  public int getOverseasVendorNumber() {
    return this.overseasvendornumber;
  }
  
  public String getColorCodeName() {
    return this.colorcodename;
  }
  
  public void setColorCodeName(String colorcodename) {
    this.colorcodename = colorcodename;
  }
  
  public String getCrownStyle() {
    return this.crownstyle;
  }
  
  public void setCrownStyle(String crownstyle) {
    this.crownstyle = crownstyle;
  }
  
  public String getMaterial() {
    return this.material;
  }
  
  public void setMaterial(String material) {
    this.material = material;
  }
  
  public String getAdvanceType() {
    return this.advancetype;
  }
  
  public void setAdvanceType(String advancetype) {
    this.advancetype = advancetype;
  }
  
  public String getFrontPanelColorName() {
    return this.frontpanelcolorname;
  }
  
  public void setFrontPanelColorName(String frontpanelcolorname) {
    this.frontpanelcolorname = frontpanelcolorname;
  }
  
  public String getSideBackPanelColorName() {
    return this.sidebackpanelcolorname;
  }
  
  public void setSideBackPanelColorName(String sidebackpanelcolorname) {
    this.sidebackpanelcolorname = sidebackpanelcolorname;
  }
  
  public String getBackPanelColorName() {
    return this.backpanelcolorname;
  }
  
  public void setBackPanelColorName(String backpanelcolorname) {
    this.backpanelcolorname = backpanelcolorname;
  }
  
  public String getSidePanelColorName() {
    return this.sidepanelcolorname;
  }
  
  public void setSidePanelColorName(String sidepanelcolorname) {
    this.sidepanelcolorname = sidepanelcolorname;
  }
  
  public String getPrimaryVisorColorName() {
    return this.primaryvisorcolorname;
  }
  
  public void setPrimaryVisorColorName(String primaryvisorcolorname) {
    this.primaryvisorcolorname = primaryvisorcolorname;
  }
  
  public String getVisorTrimColorName() {
    return this.visortrimcolorname;
  }
  
  public void setVisorTrimColorName(String visortrimcolorname) {
    this.visortrimcolorname = visortrimcolorname;
  }
  
  public String getVisorSandwichColorName() {
    return this.visorsandwichcolorname;
  }
  
  public void setVisorSandwichColorName(String visorsandwichcolorname) {
    this.visorsandwichcolorname = visorsandwichcolorname;
  }
  
  public String getUndervisorColorName() {
    return this.undervisorcolorname;
  }
  
  public void setUndervisorColorName(String undervisorcolorname) {
    this.undervisorcolorname = undervisorcolorname;
  }
  
  public String getDistressedVisorInsideColorName() {
    return this.distressedvisorinsidecolorname;
  }
  
  public void setDistressedVisorInsideColorName(String distressedvisorinsidecolorname) {
    this.distressedvisorinsidecolorname = distressedvisorinsidecolorname;
  }
  
  public String getClosureStrapColorName() {
    return this.closurestrapcolorname;
  }
  
  public void setClosureStrapColorName(String closurestrapcolorname) {
    this.closurestrapcolorname = closurestrapcolorname;
  }
  
  public String getClosureStyleNumberName() {
    return this.closurestylenumbername;
  }
  
  public void setClosureStyleNumberName(String closurestylenumbername) {
    this.closurestylenumbername = closurestylenumbername;
  }
  
  public String getEyeletStyleNumberName() {
    return this.eyeletstylenumbername;
  }
  
  public void setEyeletStyleNumberName(String eyeletstylenumbername) {
    this.eyeletstylenumbername = eyeletstylenumbername;
  }
  
  public String getFrontEyeletColorName() {
    return this.fronteyeletcolorname;
  }
  
  public void setFrontEyeletColorName(String fronteyeletcolorname) {
    this.fronteyeletcolorname = fronteyeletcolorname;
  }
  
  public String getSideBackEyeletColorName() {
    return this.sidebackeyeletcolorname;
  }
  
  public void setSideBackEyeletColorName(String sidebackeyeletcolorname) {
    this.sidebackeyeletcolorname = sidebackeyeletcolorname;
  }
  
  public String getBackEyeletColorName() {
    return this.backeyeletcolorname;
  }
  
  public void setBackEyeletColorName(String backeyeletcolorname) {
    this.backeyeletcolorname = backeyeletcolorname;
  }
  
  public String getSideEyeletColorName() {
    return this.sideeyeletcolorname;
  }
  
  public void setSideEyeletColorName(String sideeyeletcolorname) {
    this.sideeyeletcolorname = sideeyeletcolorname;
  }
  
  public String getButtonColorName() {
    return this.buttoncolorname;
  }
  
  public void setButtonColorName(String buttoncolorname) {
    this.buttoncolorname = buttoncolorname;
  }
  
  public String getInnerTapingColorName() {
    return this.innertapingcolorname;
  }
  
  public void setInnerTapingColorName(String innertapingcolorname) {
    this.innertapingcolorname = innertapingcolorname;
  }
  
  public String getSweatbandStyleNumberName() {
    return this.sweatbandstylenumbername;
  }
  
  public void setSweatbandStyleNumberName(String sweatbandstylenumbername) {
    this.sweatbandstylenumbername = sweatbandstylenumbername;
  }
  
  public String getSweatbandColorName() {
    return this.sweatbandcolorname;
  }
  
  public void setSweatbandColorName(String sweatbandcolorname) {
    this.sweatbandcolorname = sweatbandcolorname;
  }
  
  public String getSweatbandStripeColorName() {
    return this.sweatbandstripecolorname;
  }
  
  public void setSweatbandStripeColorName(String sweatbandstripecolorname) {
    this.sweatbandstripecolorname = sweatbandstripecolorname;
  }
  
  public StyleInformationData.StyleType getStyleType() {
    return this.styletype;
  }
  
  public void setStyleType(StyleInformationData.StyleType itemtype) {
    this.styletype = itemtype;
  }
  
  public String getCategory() {
    return this.category;
  }
  
  public void setCategory(String category) {
    this.category = category;
  }
  
  public String getVisorStyleNumberName() {
    return this.visorstylenumbername;
  }
  
  public void setVisorStyleNumberName(String visorstylenumbername) {
    this.visorstylenumbername = visorstylenumbername;
  }
  
  public String getPanelStitchColorName() {
    return this.panelstitchcolorname;
  }
  
  public void setPanelStitchColorName(String panelstitchcolorname) {
    this.panelstitchcolorname = panelstitchcolorname;
  }
  
  public String getVisorStitchColorName() {
    return this.visorstitchcolorname;
  }
  
  public void setVisorStitchColorName(String visorstitchcolorname) {
    this.visorstitchcolorname = visorstitchcolorname;
  }
  
  public String getFrontPanelFabricName() {
    return this.frontpanelfabricname;
  }
  
  public void setFrontPanelFabricName(String frontpanelfabricname) {
    this.frontpanelfabricname = frontpanelfabricname;
  }
  
  public String getFrontPanelContentName() {
    return this.frontpanelcontentname;
  }
  
  public void setFrontPanelContentName(String frontpanelcontentname) {
    this.frontpanelcontentname = frontpanelcontentname;
  }
  
  public String getFrontPanelFabricSpecName() {
    return this.frontpanelfabricspecname;
  }
  
  public void setFrontPanelFabricSpecName(String frontpanelfabricspecname) {
    this.frontpanelfabricspecname = frontpanelfabricspecname;
  }
  
  public String getSideBackPanelFabricName() {
    return this.sidebackpanelfabricname;
  }
  
  public void setSideBackPanelFabricName(String sidebackpanelfabricname) {
    this.sidebackpanelfabricname = sidebackpanelfabricname;
  }
  
  public String getSideBackPanelContentName() {
    return this.sidebackpanelcontentname;
  }
  
  public void setSideBackPanelContentName(String sidebackpanelcontentname) {
    this.sidebackpanelcontentname = sidebackpanelcontentname;
  }
  
  public String getSideBackPanelFabricSpecName() {
    return this.sidebackpanelfabricspecname;
  }
  
  public void setSideBackPanelFabricSpecName(String sidebackpanelfabricspecname) {
    this.sidebackpanelfabricspecname = sidebackpanelfabricspecname;
  }
  
  public String getBackPanelFabricName() {
    return this.backpanelfabricname;
  }
  
  public void setBackPanelFabricName(String backpanelfabricname) {
    this.backpanelfabricname = backpanelfabricname;
  }
  
  public String getBackPanelContentName() {
    return this.backpanelcontentname;
  }
  
  public void setBackPanelContentName(String backpanelcontentname) {
    this.backpanelcontentname = backpanelcontentname;
  }
  
  public String getBackPanelFabricSpecName() {
    return this.backpanelfabricspecname;
  }
  
  public void setBackPanelFabricSpecName(String backpanelfabricspecname) {
    this.backpanelfabricspecname = backpanelfabricspecname;
  }
  
  public String getSidePanelFabricName() {
    return this.sidepanelfabricname;
  }
  
  public void setSidePanelFabricName(String sidepanelfabricname) {
    this.sidepanelfabricname = sidepanelfabricname;
  }
  
  public String getSidePanelContentName() {
    return this.sidepanelcontentname;
  }
  
  public void setSidePanelContentName(String sidepanelcontentname) {
    this.sidepanelcontentname = sidepanelcontentname;
  }
  
  public String getSidePanelFabricSpecName() {
    return this.sidepanelfabricspecname;
  }
  
  public void setSidePanelFabricSpecName(String sidepanelfabricspecname) {
    this.sidepanelfabricspecname = sidepanelfabricspecname;
  }
  
  public String getFactoryVisorCode() {
    return this.factoryvisorcode;
  }
  
  public void setFactoryVisorCode(String factoryvisorcode) {
    this.factoryvisorcode = factoryvisorcode;
  }
  
  public boolean getSpecialItemPrice() {
    return this.specialitemprice;
  }
  
  public void setSpecialItemPrice(boolean specialitemprice) {
    this.specialitemprice = specialitemprice;
  }
  
  public ExtendOrderDataItem copy()
  {
    ExtendOrderDataItem orderdataitem = new ExtendOrderDataItem();
    orderdataitem.setStyleNumber(getStyleNumber());
    orderdataitem.setClosureStyleNumber(getClosureStyleNumber());
    orderdataitem.setVisorStyleNumber(getVisorStyleNumber());
    orderdataitem.setSweatbandStyleNumber(getSweatbandStyleNumber());
    orderdataitem.setEyeletStyleNumber(getEyeletStyleNumber());
    orderdataitem.setColorCode(getColorCode());
    orderdataitem.setFrontPanelColor(getFrontPanelColor());
    orderdataitem.setSideBackPanelColor(getSideBackPanelColor());
    orderdataitem.setBackPanelColor(getBackPanelColor());
    orderdataitem.setSidePanelColor(getSidePanelColor());
    orderdataitem.setPrimaryVisorColor(getPrimaryVisorColor());
    orderdataitem.setVisorTrimColor(getVisorTrimColor());
    orderdataitem.setVisorSandwichColor(getVisorSandwichColor());
    orderdataitem.setUndervisorColor(getUndervisorColor());
    orderdataitem.setDistressedVisorInsideColor(getDistressedVisorInsideColor());
    orderdataitem.setClosureStrapColor(getClosureStrapColor());
    orderdataitem.setFrontEyeletColor(getFrontEyeletColor());
    orderdataitem.setSideBackEyeletColor(getSideBackEyeletColor());
    orderdataitem.setBackEyeletColor(getBackEyeletColor());
    orderdataitem.setSideEyeletColor(getSideEyeletColor());
    orderdataitem.setButtonColor(getButtonColor());
    orderdataitem.setInnerTapingColor(getInnerTapingColor());
    orderdataitem.setSweatbandColor(getSweatbandColor());
    orderdataitem.setSweatbandStripeColor(getSweatbandStripeColor());
    orderdataitem.setQuantity(getQuantity());
    orderdataitem.setSize(getSize());
    orderdataitem.setPreviewFilename(getPreviewFilename());
    orderdataitem.setComments(getComments());
    orderdataitem.setPolybag(getPolybag());
    orderdataitem.setRemoveInnerPrintedLabel(getRemoveInnerPrintedLabel());
    orderdataitem.setRemoveInnerWovenLabel(getRemoveInnerWovenLabel());
    orderdataitem.setAddInnerPrintedLabel(getAddInnerPrintedLabel());
    orderdataitem.setAddInnerWovenLabel(getAddInnerWovenLabel());
    orderdataitem.setSewingPatches(getSewingPatches());
    orderdataitem.setHeatPressPatches(getHeatPressPatches());
    orderdataitem.setTagging(getTagging());
    orderdataitem.setStickers(getStickers());
    orderdataitem.setOakLeaves(getOakLeaves());
    orderdataitem.setFOBPrice(getFOBPrice());
    orderdataitem.setFOBMarkupPrice(getFOBMarkupPrice());
    orderdataitem.setPersonalizationChanges(getPersonalizationChanges());
    orderdataitem.setProductSampleEmail(getProductSampleEmail());
    orderdataitem.setProductSampleShip(getProductSampleShip());
    orderdataitem.setProductSampleToDo(getProductSampleToDo());
    orderdataitem.setProductSampleTotalDone(getProductSampleTotalDone());
    orderdataitem.setProductSampleTotalEmail(getProductSampleTotalEmail());
    orderdataitem.setProductSampleTotalShip(getProductSampleTotalShip());
    orderdataitem.setCustomStyleName(getCustomStyleName());
    orderdataitem.setHasPrivateLabel(getHasPrivateLabel());
    orderdataitem.setHasPrivatePackaging(getHasPrivatePackaging());
    orderdataitem.setHasPrivateShippingMark(getHasPrivateShippingMark());
    orderdataitem.setArtworkType(getArtworkType());
    orderdataitem.setArtworkTypeComments(getArtworkTypeComments());
    
    orderdataitem.setProfile(getProfile());
    orderdataitem.setNumberOfPanels(getNumberOfPanels());
    orderdataitem.setCrownConstruction(getCrownConstruction());
    orderdataitem.setVisorRowStitching(getVisorRowStitching());
    orderdataitem.setFrontPanelFabric(getFrontPanelFabric());
    orderdataitem.setSideBackPanelFabric(getSideBackPanelFabric());
    orderdataitem.setBackPanelFabric(getBackPanelFabric());
    orderdataitem.setSidePanelFabric(getSidePanelFabric());
    
    orderdataitem.setFrontPanelFabricName(getFrontPanelFabricName());
    orderdataitem.setFrontPanelContentName(getFrontPanelContentName());
    orderdataitem.setFrontPanelFabricSpecName(getFrontPanelFabricSpecName());
    orderdataitem.setSideBackPanelFabricName(getSideBackPanelFabricName());
    orderdataitem.setSideBackPanelContentName(getSideBackPanelContentName());
    orderdataitem.setSideBackPanelFabricSpecName(getSideBackPanelFabricSpecName());
    orderdataitem.setBackPanelFabricName(getBackPanelFabricName());
    orderdataitem.setBackPanelContentName(getBackPanelContentName());
    orderdataitem.setBackPanelFabricSpecName(getBackPanelFabricSpecName());
    orderdataitem.setSidePanelFabricName(getSidePanelFabricName());
    orderdataitem.setSidePanelContentName(getSidePanelContentName());
    orderdataitem.setSidePanelFabricSpecName(getSidePanelFabricSpecName());
    
    orderdataitem.setPanelStitchColor(getPanelStitchColor());
    orderdataitem.setVisorStitchColor(getVisorStitchColor());
    
    orderdataitem.setPanelStitchColorName(getPanelStitchColorName());
    orderdataitem.setVisorStitchColorName(getVisorStitchColorName());
    
    orderdataitem.setOverseasVendorNumber(getOverseasVendorNumber());
    orderdataitem.setCrownStyle(getCrownStyle());
    orderdataitem.setMaterial(getMaterial());
    orderdataitem.setAdvanceType(getAdvanceType());
    orderdataitem.setStyleType(getStyleType());
    orderdataitem.setCategory(getCategory());
    orderdataitem.setColorCodeName(getColorCodeName());
    orderdataitem.setFrontPanelColorName(getFrontPanelColorName());
    orderdataitem.setSideBackPanelColorName(getSideBackPanelColorName());
    orderdataitem.setBackPanelColorName(getBackPanelColorName());
    orderdataitem.setSidePanelColorName(getSidePanelColorName());
    orderdataitem.setPrimaryVisorColorName(getPrimaryVisorColorName());
    orderdataitem.setVisorTrimColorName(getVisorTrimColorName());
    orderdataitem.setVisorSandwichColorName(getVisorSandwichColorName());
    orderdataitem.setUndervisorColorName(getUndervisorColorName());
    orderdataitem.setDistressedVisorInsideColorName(getDistressedVisorInsideColorName());
    orderdataitem.setClosureStrapColorName(getClosureStrapColorName());
    orderdataitem.setClosureStyleNumberName(getClosureStyleNumberName());
    orderdataitem.setEyeletStyleNumberName(getEyeletStyleNumberName());
    orderdataitem.setFrontEyeletColorName(getFrontEyeletColorName());
    orderdataitem.setSideBackEyeletColorName(getSideBackEyeletColorName());
    orderdataitem.setBackEyeletColorName(getBackEyeletColorName());
    orderdataitem.setSideEyeletColorName(getSideEyeletColorName());
    orderdataitem.setButtonColorName(getButtonColorName());
    orderdataitem.setInnerTapingColorName(getInnerTapingColorName());
    orderdataitem.setSweatbandStyleNumberName(getSweatbandStyleNumberName());
    orderdataitem.setSweatbandColorName(getSweatbandColorName());
    orderdataitem.setSweatbandStripeColorName(getSweatbandStripeColorName());
    orderdataitem.setVisorStyleNumberName(getVisorStyleNumberName());
    orderdataitem.setGoodsType(getGoodsType());
    orderdataitem.setSameStyle(getSameStyle());
    orderdataitem.setOrgSetNum(getOrgSetNum());
    orderdataitem.setOrgItemNum(getOrgItemNum());
    orderdataitem.setSideView(getSideView());
    
    orderdataitem.setSpecialItemPrice(getSpecialItemPrice());
    
    return orderdataitem;
  }
}
