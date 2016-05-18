package com.ottocap.NewWorkFlow.client.OrderData;

import java.io.Serializable;












public class OrderDataItem
  extends OrderDataShared
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  
  public String getStyleNumber()
  {
    return (String)get("stylenumber", "");
  }
  
  public void setStyleNumber(String stylenumber) {
    set("stylenumber", stylenumber);
  }
  
  public String getVendorNumber() {
    return (String)get("vendornumber", "");
  }
  
  public void setVendorNumber(String vendornumber) {
    set("vendornumber", vendornumber);
  }
  
  public String getColorCode() {
    return (String)get("colorcode", "");
  }
  
  public void setColorCode(String colorcode) {
    set("colorcode", colorcode);
  }
  
  public Integer getQuantity() {
    return (Integer)get("quantity");
  }
  
  public void setQuantity(Integer quantity) {
    set("quantity", quantity);
  }
  
  public boolean getPolybag() {
    return ((Boolean)get("polybag", Boolean.valueOf(false))).booleanValue();
  }
  
  public void setPolybag(boolean polybag) {
    set("polybag", Boolean.valueOf(polybag));
  }
  
  public boolean getRemoveInnerPrintedLabel() {
    return ((Boolean)get("removeinnerprintedlabel", Boolean.valueOf(false))).booleanValue();
  }
  
  public void setRemoveInnerPrintedLabel(boolean removeinnerprintedlabel) {
    set("removeinnerprintedlabel", Boolean.valueOf(removeinnerprintedlabel));
  }
  
  public boolean getRemoveInnerWovenLabel() {
    return ((Boolean)get("removeinnerwovenlabel", Boolean.valueOf(false))).booleanValue();
  }
  
  public void setRemoveInnerWovenLabel(boolean removeinnerwovenlabel) {
    set("removeinnerwovenlabel", Boolean.valueOf(removeinnerwovenlabel));
  }
  
  public boolean getAddInnerPrintedLabel() {
    return ((Boolean)get("addinnerprintedlabel", Boolean.valueOf(false))).booleanValue();
  }
  
  public void setAddInnerPrintedLabel(boolean addinnerprintedlabel) {
    set("addinnerprintedlabel", Boolean.valueOf(addinnerprintedlabel));
  }
  
  public boolean getAddInnerWovenLabel() {
    return ((Boolean)get("addinnerwovenlabel", Boolean.valueOf(false))).booleanValue();
  }
  
  public void setAddInnerWovenLabel(boolean addinnerwovenlabel) {
    set("addinnerwovenlabel", Boolean.valueOf(addinnerwovenlabel));
  }
  
  public boolean getSewingPatches() {
    return ((Boolean)get("sewingpatches", Boolean.valueOf(false))).booleanValue();
  }
  
  public void setSewingPatches(boolean sewingpatches) {
    set("sewingpatches", Boolean.valueOf(sewingpatches));
  }
  
  public boolean getHeatPressPatches() {
    return ((Boolean)get("heatpresspatches", Boolean.valueOf(false))).booleanValue();
  }
  
  public void setHeatPressPatches(boolean heatpresspatches) {
    set("heatpresspatches", Boolean.valueOf(heatpresspatches));
  }
  
  public boolean getTagging() {
    return ((Boolean)get("tagging", Boolean.valueOf(false))).booleanValue();
  }
  
  public void setTagging(boolean tagging) {
    set("tagging", Boolean.valueOf(tagging));
  }
  
  public boolean getStickers() {
    return ((Boolean)get("stickers", Boolean.valueOf(false))).booleanValue();
  }
  
  public void setStickers(boolean stickers) {
    set("stickers", Boolean.valueOf(stickers));
  }
  
  public Integer getPersonalizationChanges() {
    return (Integer)get("personalizationchanges");
  }
  
  public void setPersonalizationChanges(Integer personalizationchanges) {
    set("personalizationchanges", personalizationchanges);
  }
  
  public String getComments() {
    return (String)get("comments", "");
  }
  
  public void setComments(String comments) {
    set("comments", comments);
  }
  
  public String getPreviewFilename() {
    return (String)get("previewfilename", "");
  }
  
  public void setPreviewFilename(String previewfilename) {
    set("previewfilename", previewfilename);
  }
  
  public boolean getProductSampleEmail() {
    return ((Boolean)get("productsampleemail", Boolean.valueOf(false))).booleanValue();
  }
  
  public void setProductSampleEmail(boolean productsampleemail) {
    set("productsampleemail", Boolean.valueOf(productsampleemail));
  }
  
  public boolean getProductSampleShip() {
    return ((Boolean)get("productsampleship", Boolean.valueOf(false))).booleanValue();
  }
  
  public void setProductSampleShip(boolean productsampleship) {
    set("productsampleship", Boolean.valueOf(productsampleship));
  }
  
  public Integer getProductSampleToDo() {
    return (Integer)get("productsampletodo");
  }
  
  public void setProductSampleToDo(Integer productsampletodo) {
    set("productsampletodo", productsampletodo);
  }
  
  public Integer getProductSampleTotalDone() {
    return (Integer)get("productsampletotaldone");
  }
  
  public void setProductSampleTotalDone(Integer productsampletotaldone) {
    set("productsampletotaldone", productsampletotaldone);
  }
  
  public Integer getProductSampleTotalShip() {
    return (Integer)get("productsampletotalship");
  }
  
  public void setProductSampleTotalShip(Integer productsampletotalship) {
    set("productsampletotalship", productsampletotalship);
  }
  
  public Integer getProductSampleTotalEmail() {
    return (Integer)get("productsampletotalemail");
  }
  
  public void setProductSampleTotalEmail(Integer productsampletotalemail) {
    set("productsampletotalemail", productsampletotalemail);
  }
  
  public boolean getOakLeaves() {
    return ((Boolean)get("oakleaves", Boolean.valueOf(false))).booleanValue();
  }
  
  public void setOakLeaves(boolean oakleaves) {
    set("oakleaves", Boolean.valueOf(oakleaves));
  }
  
  public String getSize() {
    return (String)get("size", "");
  }
  
  public void setSize(String size) {
    set("size", size);
  }
  
  public String getFrontPanelColor() {
    return (String)get("frontpanelcolor", "");
  }
  
  public void setFrontPanelColor(String frontpanelcolor) {
    set("frontpanelcolor", frontpanelcolor);
  }
  
  public String getSideBackPanelColor() {
    return (String)get("sidebackpanelcolor", "");
  }
  
  public void setSideBackPanelColor(String sidebackpanelcolor) {
    set("sidebackpanelcolor", sidebackpanelcolor);
  }
  
  public String getBackPanelColor() {
    return (String)get("backpanelcolor", "");
  }
  
  public void setBackPanelColor(String backpanelcolor) {
    set("backpanelcolor", backpanelcolor);
  }
  
  public String getSidePanelColor() {
    return (String)get("sidepanelcolor", "");
  }
  
  public void setSidePanelColor(String sidepanelcolor) {
    set("sidepanelcolor", sidepanelcolor);
  }
  
  public String getVisorStyleNumber() {
    return (String)get("visorstylenumber", "");
  }
  
  public void setVisorStyleNumber(String visorstylenumber) {
    set("visorstylenumber", visorstylenumber);
  }
  
  public String getPrimaryVisorColor() {
    return (String)get("primaryvisorcolor", "");
  }
  
  public void setPrimaryVisorColor(String primaryvisorcolor) {
    set("primaryvisorcolor", primaryvisorcolor);
  }
  
  public String getVisorTrimColor() {
    return (String)get("visortrimcolor", "");
  }
  
  public void setVisorTrimColor(String visortrimcolor) {
    set("visortrimcolor", visortrimcolor);
  }
  
  public String getVisorSandwichColor() {
    return (String)get("visorsandwichcolor", "");
  }
  
  public void setVisorSandwichColor(String visorsandwichcolor) {
    set("visorsandwichcolor", visorsandwichcolor);
  }
  
  public String getUndervisorColor() {
    return (String)get("undervisorcolor", "");
  }
  
  public void setUndervisorColor(String undervisorcolor) {
    set("undervisorcolor", undervisorcolor);
  }
  
  public String getDistressedVisorInsideColor() {
    return (String)get("distressedvisorinsidecolor", "");
  }
  
  public void setDistressedVisorInsideColor(String distressedvisorinsidecolor) {
    set("distressedvisorinsidecolor", distressedvisorinsidecolor);
  }
  
  public String getClosureStyleNumber() {
    return (String)get("closurestylenumber", "");
  }
  
  public void setClosureStyleNumber(String closurestylenumber) {
    set("closurestylenumber", closurestylenumber);
  }
  
  public String getClosureStrapColor() {
    return (String)get("closurestrapcolor", "");
  }
  
  public void setClosureStrapColor(String closurestrapcolor) {
    set("closurestrapcolor", closurestrapcolor);
  }
  
  public String getEyeletStyleNumber() {
    return (String)get("eyeletstylenumber", "");
  }
  
  public void setEyeletStyleNumber(String eyeletstylenumber) {
    set("eyeletstylenumber", eyeletstylenumber);
  }
  
  public String getFrontEyeletColor() {
    return (String)get("fronteyeletcolor", "");
  }
  
  public void setFrontEyeletColor(String fronteyeletcolor) {
    set("fronteyeletcolor", fronteyeletcolor);
  }
  
  public String getSideBackEyeletColor() {
    return (String)get("sidebackeyeletcolor", "");
  }
  
  public void setSideBackEyeletColor(String sidebackeyeletcolor) {
    set("sidebackeyeletcolor", sidebackeyeletcolor);
  }
  
  public String getBackEyeletColor()
  {
    return (String)get("backeyeletcolor", "");
  }
  
  public void setBackEyeletColor(String backeyeletcolor) {
    set("backeyeletcolor", backeyeletcolor);
  }
  
  public String getSideEyeletColor()
  {
    return (String)get("sideeyeletcolor", "");
  }
  
  public void setSideEyeletColor(String sideeyeletcolor) {
    set("sideeyeletcolor", sideeyeletcolor);
  }
  
  public String getButtonColor() {
    return (String)get("buttoncolor", "");
  }
  
  public void setButtonColor(String buttoncolor) {
    set("buttoncolor", buttoncolor);
  }
  
  public String getInnerTapingColor() {
    return (String)get("innertapingcolor", "");
  }
  
  public void setInnerTapingColor(String innertapingcolor) {
    set("innertapingcolor", innertapingcolor);
  }
  
  public String getSweatbandStyleNumber() {
    return (String)get("sweatbandstylenumber", "");
  }
  
  public void setSweatbandStyleNumber(String sweatbandstylenumber) {
    set("sweatbandstylenumber", sweatbandstylenumber);
  }
  
  public String getSweatbandColor() {
    return (String)get("sweatbandcolor", "");
  }
  
  public void setSweatbandColor(String sweatbandcolor) {
    set("sweatbandcolor", sweatbandcolor);
  }
  
  public String getSweatbandStripeColor() {
    return (String)get("sweatbandstripecolor", "");
  }
  
  public void setSweatbandStripeColor(String sweatbandstripecolor) {
    set("sweatbandstripecolor", sweatbandstripecolor);
  }
  
  public void setCustomStyleName(String customstylename) {
    set("customstylename", customstylename);
  }
  
  public String getCustomStyleName() {
    return (String)get("customstylename", "");
  }
  
  public Double getFOBPrice() {
    return (Double)get("fobprice");
  }
  
  public void setFOBPrice(Double fobprice) {
    set("fobprice", fobprice);
  }
  
  public Double getFOBMarkupPrice()
  {
    return (Double)get("fobmarkupprice");
  }
  
  public void setFOBMarkupPrice(Double fobmarkupprice) {
    set("fobmarkupprice", fobmarkupprice);
  }
  
  public void setHasPrivateLabel(Boolean hasprivatelabel) {
    set("hasprivatelabel", hasprivatelabel);
  }
  
  public Boolean getHasPrivateLabel() {
    return (Boolean)get("hasprivatelabel");
  }
  
  public void setHasPrivatePackaging(Boolean hasprivatepackaging) {
    set("hasprivatepackaging", hasprivatepackaging);
  }
  
  public Boolean getHasPrivatePackaging() {
    return (Boolean)get("hasprivatepackaging");
  }
  
  public void setHasPrivateShippingMark(Boolean hasprivateshippingmark) {
    set("hasprivateshippingmark", hasprivateshippingmark);
  }
  
  public Boolean getHasPrivateShippingMark() {
    return (Boolean)get("hasprivateshippingmark");
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
  
  public void setProfile(String profile) {
    set("profile", profile);
  }
  
  public String getProfile() {
    return (String)get("profile", "");
  }
  
  public void setNumberOfPanels(String numberofpanels) {
    set("numberofpanels", numberofpanels);
  }
  
  public String getNumberOfPanels() {
    return (String)get("numberofpanels", "");
  }
  
  public void setCrownConstruction(String crownconstruction) {
    set("crownconstruction", crownconstruction);
  }
  
  public String getCrownConstruction() {
    return (String)get("crownconstruction", "");
  }
  
  public void setPanelStitchColor(String panelstitchcolor) {
    set("panelstitchcolor", panelstitchcolor);
  }
  
  public String getPanelStitchColor() {
    return (String)get("panelstitchcolor", "");
  }
  
  public void setVisorStitchColor(String visorstitchcolor) {
    set("visorstitchcolor", visorstitchcolor);
  }
  
  public String getVisorStitchColor() {
    return (String)get("visorstitchcolor", "");
  }
  
  public void setVisorRowStitching(String visorrowstitching) {
    set("visorrowstitching", visorrowstitching);
  }
  
  public String getVisorRowStitching() {
    return (String)get("visorrowstitching", "");
  }
  
  public void setFrontPanelFabric(String frontpanelfabric) {
    set("frontpanelfabric", frontpanelfabric);
  }
  
  public String getFrontPanelFabric() {
    return (String)get("frontpanelfabric", "");
  }
  
  public void setSideBackPanelFabric(String sidebackpanelfabric) {
    set("sidebackpanelfabric", sidebackpanelfabric);
  }
  
  public String getSideBackPanelFabric() {
    return (String)get("sidebackpanelfabric", "");
  }
  
  public void setBackPanelFabric(String backpanelfabric) {
    set("backpanelfabric", backpanelfabric);
  }
  
  public String getBackPanelFabric() {
    return (String)get("backpanelfabric", "");
  }
  
  public void setSidePanelFabric(String sidepanelfabric)
  {
    set("sidepanelfabric", sidepanelfabric);
  }
  
  public String getSidePanelFabric() {
    return (String)get("sidepanelfabric", "");
  }
  
  public void setSampleApprovedList(String sampleapprovedlist) {
    set("sampleapprovedlist", sampleapprovedlist);
  }
  
  public String getSampleApprovedList() {
    return (String)get("sampleapprovedlist", "");
  }
  
  public Double getProductDiscount()
  {
    return (Double)get("productdiscount");
  }
  
  public void setProductDiscount(Double productdiscount) {
    set("productdiscount", productdiscount);
  }
  
  public OrderDataItem copy() {
    OrderDataItem orderdataitem = new OrderDataItem();
    orderdataitem.setStyleNumber(getStyleNumber());
    orderdataitem.setVendorNumber(getVendorNumber());
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
    orderdataitem.setPanelStitchColor(getPanelStitchColor());
    orderdataitem.setVisorStitchColor(getVisorStitchColor());
    orderdataitem.setVisorRowStitching(getVisorRowStitching());
    orderdataitem.setFrontPanelFabric(getFrontPanelFabric());
    orderdataitem.setSideBackPanelFabric(getSideBackPanelFabric());
    orderdataitem.setBackPanelFabric(getBackPanelFabric());
    orderdataitem.setSidePanelFabric(getSidePanelFabric());
    orderdataitem.setSampleApprovedList(getSampleApprovedList());
    orderdataitem.setProductDiscount(getProductDiscount());
    
    return orderdataitem;
  }
}
