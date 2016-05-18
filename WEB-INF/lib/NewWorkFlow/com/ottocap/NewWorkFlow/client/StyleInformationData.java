package com.ottocap.NewWorkFlow.client;

import com.extjs.gxt.ui.client.core.FastMap;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData.OrderType;
import com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBoxModelData;
import java.io.Serializable;
import java.util.ArrayList;




public class StyleInformationData
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private OrderData.OrderType ordertype = null;
  private String stylenumber = "";
  private String category = "";
  private boolean showfobdozen = false;
  private boolean usesizecolor = false;
  private FastMap<ArrayList<OtherComboBoxModelData>> sizecolor = new FastMap();
  private ArrayList<OtherComboBoxModelData> colorcode = new ArrayList();
  private ArrayList<OtherComboBoxModelData> size = new ArrayList();
  private ArrayList<OtherComboBoxModelData> frontpanelcolor = new ArrayList();
  private ArrayList<OtherComboBoxModelData> sidebackpanelcolor = new ArrayList();
  private ArrayList<OtherComboBoxModelData> backpanelcolor = new ArrayList();
  private ArrayList<OtherComboBoxModelData> sidepanelcolor = new ArrayList();
  private ArrayList<OtherComboBoxModelData> visorstylenumber = new ArrayList();
  private ArrayList<OtherComboBoxModelData> primaryvisorcolor = new ArrayList();
  private ArrayList<OtherComboBoxModelData> visortrimedgecolor = new ArrayList();
  private ArrayList<OtherComboBoxModelData> visorsandwichcolor = new ArrayList();
  private ArrayList<OtherComboBoxModelData> undervisorcolor = new ArrayList();
  private ArrayList<OtherComboBoxModelData> closurestylenumber = new ArrayList();
  private ArrayList<OtherComboBoxModelData> closurestrapcolor = new ArrayList();
  private ArrayList<OtherComboBoxModelData> c2c31closurestrapcolor = new ArrayList();
  private ArrayList<OtherComboBoxModelData> eyeletstylenumber = new ArrayList();
  private ArrayList<OtherComboBoxModelData> fronteyeletcolor = new ArrayList();
  private ArrayList<OtherComboBoxModelData> sidebackeyeletcolor = new ArrayList();
  private ArrayList<OtherComboBoxModelData> backeyeletcolor = new ArrayList();
  private ArrayList<OtherComboBoxModelData> sideeyeletcolor = new ArrayList();
  private ArrayList<OtherComboBoxModelData> buttoncolor = new ArrayList();
  private ArrayList<OtherComboBoxModelData> innertapingcolor = new ArrayList();
  private ArrayList<OtherComboBoxModelData> sweatbandstylenumber = new ArrayList();
  private ArrayList<OtherComboBoxModelData> sweatbandcolor = new ArrayList();
  private ArrayList<OtherComboBoxModelData> s5sweatbandcolor = new ArrayList();
  private ArrayList<OtherComboBoxModelData> stripecolor = new ArrayList();
  private ArrayList<OtherComboBoxModelData> distressedvisorinsidecolor = new ArrayList();
  private ArrayList<OtherComboBoxModelData> logolocation = new ArrayList();
  
  private ArrayList<OtherComboBoxModelData> numberofpanels = new ArrayList();
  private ArrayList<OtherComboBoxModelData> crownconstruction = new ArrayList();
  private ArrayList<OtherComboBoxModelData> panelstitchcolor = new ArrayList();
  private ArrayList<OtherComboBoxModelData> visorrowstitching = new ArrayList();
  private ArrayList<OtherComboBoxModelData> visorstitchcolor = new ArrayList();
  
  private String originaleyelet = "";
  private String originalclosure = "";
  private String originalsweatband = "";
  private String originalvisor = "";
  private String description = "";
  private String samestylenumber = "";
  private String originalsize = "";
  
  private ArrayList<OtherComboBoxModelData> allvendorsstore = new ArrayList();
  private FastMap<StyleInformationData> allvendors = new FastMap();
  
  private ArrayList<OtherComboBoxModelData> allprofilestore = new ArrayList();
  private FastMap<StyleInformationData> allprofile = new FastMap();
  private ArrayList<OtherComboBoxModelData> frontfabricstore = new ArrayList();
  private ArrayList<OtherComboBoxModelData> sidebackfabricstore = new ArrayList();
  private ArrayList<OtherComboBoxModelData> backfabricstore = new ArrayList();
  private ArrayList<OtherComboBoxModelData> sidefabricstore = new ArrayList();
  private FastMap<StyleInformationData> allfabric = new FastMap();
  
  public static enum StyleType {
    NONOTTO,  DOMESTIC,  DOMESTIC_LACKPARD,  DOMESTIC_SHIRTS,  DOMESTIC_SWEATSHIRTS,  DOMESTIC_TOTEBAGS,  DOMESTIC_APRONS,  OVERSEAS,  OVERSEAS_LACKPARD,  OVERSEAS_INSTOCK,  OVERSEAS_INSTOCK_SHIRTS,  OVERSEAS_INSTOCK_FLATS,  OVERSEAS_PREDESIGNED,  OVERSEAS_NONOTTO,  OVERSEAS_NONOTTO_SHIRTS,  DOMESTIC_SPECIAL,  OVERSEAS_CUSTOMSTYLE;
  }
  
  private StyleType styletype = null;
  

  private int vendornumber;
  


  public void setOrderType(OrderData.OrderType ordertype)
  {
    this.ordertype = ordertype;
  }
  
  public OrderData.OrderType getOrderType() {
    return this.ordertype;
  }
  
  public void setStyleNumber(String stylenumber) {
    this.stylenumber = stylenumber;
  }
  
  public String getStyleNumber() {
    return this.stylenumber;
  }
  
  public void setColorCode(ArrayList<OtherComboBoxModelData> colorcode) {
    this.colorcode = colorcode;
  }
  
  public ArrayList<OtherComboBoxModelData> getColorCode() {
    return this.colorcode;
  }
  
  public void setSize(ArrayList<OtherComboBoxModelData> size) {
    this.size = size;
  }
  
  public ArrayList<OtherComboBoxModelData> getSize() {
    return this.size;
  }
  
  public void setShowFOBDozen(boolean showfobdozen) {
    this.showfobdozen = showfobdozen;
  }
  
  public boolean getShowFOBDozen() {
    return this.showfobdozen;
  }
  
  public void setFrontPanelColor(ArrayList<OtherComboBoxModelData> frontpanelcolor) {
    this.frontpanelcolor = frontpanelcolor;
  }
  
  public ArrayList<OtherComboBoxModelData> getFrontPanelColor() {
    return this.frontpanelcolor;
  }
  
  public void setSideBackPanelColor(ArrayList<OtherComboBoxModelData> sidebackpanelcolor) {
    this.sidebackpanelcolor = sidebackpanelcolor;
  }
  
  public ArrayList<OtherComboBoxModelData> getSideBackPanelColor() {
    return this.sidebackpanelcolor;
  }
  
  public void setBackPanelColor(ArrayList<OtherComboBoxModelData> backpanelcolor) {
    this.backpanelcolor = backpanelcolor;
  }
  
  public ArrayList<OtherComboBoxModelData> getBackPanelColor() {
    return this.backpanelcolor;
  }
  
  public void setSidePanelColor(ArrayList<OtherComboBoxModelData> sidepanelcolor) {
    this.sidepanelcolor = sidepanelcolor;
  }
  
  public ArrayList<OtherComboBoxModelData> getSidePanelColor() {
    return this.sidepanelcolor;
  }
  
  public void setVisorStyleNumber(ArrayList<OtherComboBoxModelData> visorstylenumber) {
    this.visorstylenumber = visorstylenumber;
  }
  
  public ArrayList<OtherComboBoxModelData> getVisorStyleNumber() {
    return this.visorstylenumber;
  }
  
  public void setPrimaryVisorColor(ArrayList<OtherComboBoxModelData> primaryvisorcolor) {
    this.primaryvisorcolor = primaryvisorcolor;
  }
  
  public ArrayList<OtherComboBoxModelData> getPrimaryVisorColor() {
    return this.primaryvisorcolor;
  }
  
  public void setVisorTrimEdgeColor(ArrayList<OtherComboBoxModelData> visortrimedgecolor) {
    this.visortrimedgecolor = visortrimedgecolor;
  }
  
  public ArrayList<OtherComboBoxModelData> getVisorTrimEdgeColor() {
    return this.visortrimedgecolor;
  }
  
  public void setVisorSandwichColor(ArrayList<OtherComboBoxModelData> visorsandwichcolor) {
    this.visorsandwichcolor = visorsandwichcolor;
  }
  
  public ArrayList<OtherComboBoxModelData> getVisorSandwichColor() {
    return this.visorsandwichcolor;
  }
  
  public void setUndervisorColor(ArrayList<OtherComboBoxModelData> undervisorcolor) {
    this.undervisorcolor = undervisorcolor;
  }
  
  public ArrayList<OtherComboBoxModelData> getUndervisorColor() {
    return this.undervisorcolor;
  }
  
  public void setClosureStyleNumber(ArrayList<OtherComboBoxModelData> closurestylenumber) {
    this.closurestylenumber = closurestylenumber;
  }
  
  public ArrayList<OtherComboBoxModelData> getClosureStyleNumber() {
    return this.closurestylenumber;
  }
  
  public void setClosureStrapColor(ArrayList<OtherComboBoxModelData> closurestrapcolor) {
    this.closurestrapcolor = closurestrapcolor;
  }
  
  public ArrayList<OtherComboBoxModelData> getClosureStrapColor() {
    return this.closurestrapcolor;
  }
  
  public void setC2C31ClosureStrapColor(ArrayList<OtherComboBoxModelData> c2c31closurestrapcolor) {
    this.c2c31closurestrapcolor = c2c31closurestrapcolor;
  }
  
  public ArrayList<OtherComboBoxModelData> getC2C31ClosureStrapColor() {
    return this.c2c31closurestrapcolor;
  }
  
  public void setEyeletStyleNumber(ArrayList<OtherComboBoxModelData> eyeletstylenumber) {
    this.eyeletstylenumber = eyeletstylenumber;
  }
  
  public ArrayList<OtherComboBoxModelData> getEyeletStyleNumber() {
    return this.eyeletstylenumber;
  }
  
  public void setFrontEyeletColor(ArrayList<OtherComboBoxModelData> fronteyeletcolor) {
    this.fronteyeletcolor = fronteyeletcolor;
  }
  
  public ArrayList<OtherComboBoxModelData> getFrontEyeletColor() {
    return this.fronteyeletcolor;
  }
  
  public void setSideBackEyeletColor(ArrayList<OtherComboBoxModelData> sidebackeyeletcolor) {
    this.sidebackeyeletcolor = sidebackeyeletcolor;
  }
  
  public ArrayList<OtherComboBoxModelData> getSideBackEyeletColor() {
    return this.sidebackeyeletcolor;
  }
  
  public void setBackEyeletColor(ArrayList<OtherComboBoxModelData> backeyeletcolor) {
    this.backeyeletcolor = backeyeletcolor;
  }
  
  public ArrayList<OtherComboBoxModelData> getBackEyeletColor() {
    return this.backeyeletcolor;
  }
  
  public void setSideEyeletColor(ArrayList<OtherComboBoxModelData> sideeyeletcolor) {
    this.sideeyeletcolor = sideeyeletcolor;
  }
  
  public ArrayList<OtherComboBoxModelData> getSideEyeletColor() {
    return this.sideeyeletcolor;
  }
  
  public void setButtonColor(ArrayList<OtherComboBoxModelData> buttoncolor) {
    this.buttoncolor = buttoncolor;
  }
  
  public ArrayList<OtherComboBoxModelData> getButtonColor() {
    return this.buttoncolor;
  }
  
  public void setInnerTapingColor(ArrayList<OtherComboBoxModelData> innertapingcolor) {
    this.innertapingcolor = innertapingcolor;
  }
  
  public ArrayList<OtherComboBoxModelData> getInnerTapingColor() {
    return this.innertapingcolor;
  }
  
  public void setSweatbandStyleNumber(ArrayList<OtherComboBoxModelData> sweatbandstylenumber) {
    this.sweatbandstylenumber = sweatbandstylenumber;
  }
  
  public ArrayList<OtherComboBoxModelData> getSweatbandStyleNumber() {
    return this.sweatbandstylenumber;
  }
  
  public void setSweatbandColor(ArrayList<OtherComboBoxModelData> sweatbandcolor) {
    this.sweatbandcolor = sweatbandcolor;
  }
  
  public ArrayList<OtherComboBoxModelData> getSweatbandColor() {
    return this.sweatbandcolor;
  }
  
  public void setS5SweatbandColor(ArrayList<OtherComboBoxModelData> s5sweatbandcolor) {
    this.s5sweatbandcolor = s5sweatbandcolor;
  }
  
  public ArrayList<OtherComboBoxModelData> getS5SweatbandColor() {
    return this.s5sweatbandcolor;
  }
  
  public void setStripeColor(ArrayList<OtherComboBoxModelData> stripecolor) {
    this.stripecolor = stripecolor;
  }
  
  public ArrayList<OtherComboBoxModelData> getStripeColor() {
    return this.stripecolor;
  }
  
  public void setDistressedVisorInsideColor(ArrayList<OtherComboBoxModelData> distressedvisorinsidecolor) {
    this.distressedvisorinsidecolor = distressedvisorinsidecolor;
  }
  
  public ArrayList<OtherComboBoxModelData> getDistressedVisorInsideColor() {
    return this.distressedvisorinsidecolor;
  }
  
  public void setLogoLocation(ArrayList<OtherComboBoxModelData> logolocation) {
    this.logolocation = logolocation;
  }
  
  public ArrayList<OtherComboBoxModelData> getLogoLocation() {
    return this.logolocation;
  }
  
  public void setVendorNumber(int vendornumber) {
    this.vendornumber = vendornumber;
  }
  
  public int getVendorNumber() {
    return this.vendornumber;
  }
  
  public void setStyleType(StyleType styletype) {
    this.styletype = styletype;
  }
  
  public StyleType getStyleType() {
    return this.styletype;
  }
  
  public void setUseSizeColor(boolean usesizecolor) {
    this.usesizecolor = usesizecolor;
  }
  
  public boolean getUseSizeColor() {
    return this.usesizecolor;
  }
  
  public void setSizeColor(FastMap<ArrayList<OtherComboBoxModelData>> sizecolor) {
    this.sizecolor = sizecolor;
  }
  
  public FastMap<ArrayList<OtherComboBoxModelData>> getSizeColor() {
    return this.sizecolor;
  }
  
  public void setCategory(String category) {
    this.category = category;
  }
  
  public String getCategory() {
    return this.category;
  }
  
  public void addAllVendors(String vendornumber, StyleInformationData vendorinformation) {
    this.allvendors.put(vendornumber, vendorinformation);
  }
  
  public FastMap<StyleInformationData> getAllVendors() {
    return this.allvendors;
  }
  
  public void setOriginalEyelet(String originaleyelet) {
    this.originaleyelet = originaleyelet;
  }
  
  public String getOriginalEyelet() {
    return this.originaleyelet;
  }
  
  public void setOriginalClosure(String originalclosure) {
    this.originalclosure = originalclosure;
  }
  
  public String getOriginalClosure() {
    return this.originalclosure;
  }
  
  public void setOriginalSweatband(String originalsweatband) {
    this.originalsweatband = originalsweatband;
  }
  
  public String getOriginalSweatband() {
    return this.originalsweatband;
  }
  
  public void setOriginalVisor(String originalvisor) {
    this.originalvisor = originalvisor;
  }
  
  public String getOriginalVisor() {
    return this.originalvisor;
  }
  
  public ArrayList<OtherComboBoxModelData> getAllVendorsStore() {
    return this.allvendorsstore;
  }
  
  public void setAllVendorsStore(ArrayList<OtherComboBoxModelData> allvendorsstore) {
    this.allvendorsstore = allvendorsstore;
  }
  
  public void setDescription(String description) {
    this.description = description;
  }
  
  public String getDescription() {
    return this.description;
  }
  
  public void setSameStyleNumber(String samestylenumber) {
    this.samestylenumber = samestylenumber;
  }
  
  public String getSameStyleNumber() {
    return this.samestylenumber;
  }
  
  public ArrayList<OtherComboBoxModelData> getAllProfileStore() {
    return this.allprofilestore;
  }
  
  public void setAllProfileStore(ArrayList<OtherComboBoxModelData> allprofilestore) {
    this.allprofilestore = allprofilestore;
  }
  
  public void addAllProfile(String profilename, StyleInformationData profileinformation) {
    this.allprofile.put(profilename, profileinformation);
  }
  
  public FastMap<StyleInformationData> getAllProfile() {
    return this.allprofile;
  }
  
  public void setNumberOfPanels(ArrayList<OtherComboBoxModelData> numberofpanels) {
    this.numberofpanels = numberofpanels;
  }
  
  public ArrayList<OtherComboBoxModelData> getNumberOfPanels() {
    return this.numberofpanels;
  }
  
  public void setCrownConstruction(ArrayList<OtherComboBoxModelData> crownconstruction) {
    this.crownconstruction = crownconstruction;
  }
  
  public ArrayList<OtherComboBoxModelData> getCrownConstruction() {
    return this.crownconstruction;
  }
  
  public void setFrontFabricStore(ArrayList<OtherComboBoxModelData> frontfabricstore) {
    this.frontfabricstore = frontfabricstore;
  }
  
  public ArrayList<OtherComboBoxModelData> getFrontFabricStore() {
    return this.frontfabricstore;
  }
  
  public void setSideBackFabricStore(ArrayList<OtherComboBoxModelData> sidebackfabricstore) {
    this.sidebackfabricstore = sidebackfabricstore;
  }
  
  public ArrayList<OtherComboBoxModelData> getSideBackFabricStore() {
    return this.sidebackfabricstore;
  }
  
  public void setBackFabricStore(ArrayList<OtherComboBoxModelData> backfabricstore) {
    this.backfabricstore = backfabricstore;
  }
  
  public ArrayList<OtherComboBoxModelData> getBackFabricStore() {
    return this.backfabricstore;
  }
  
  public void setSideFabricStore(ArrayList<OtherComboBoxModelData> sidefabricstore) {
    this.sidefabricstore = sidefabricstore;
  }
  
  public ArrayList<OtherComboBoxModelData> getSideFabricStore() {
    return this.sidefabricstore;
  }
  
  public void addAllFabric(String fabricname, StyleInformationData fabricinformation) {
    this.allfabric.put(fabricname, fabricinformation);
  }
  
  public FastMap<StyleInformationData> getAllFabric() {
    return this.allfabric;
  }
  
  public void setPanelStitchColor(ArrayList<OtherComboBoxModelData> panelstitchcolor) {
    this.panelstitchcolor = panelstitchcolor;
  }
  
  public ArrayList<OtherComboBoxModelData> getPanelStitchColor() {
    return this.panelstitchcolor;
  }
  
  public void setVisorRowStitching(ArrayList<OtherComboBoxModelData> visorrowstitching) {
    this.visorrowstitching = visorrowstitching;
  }
  
  public ArrayList<OtherComboBoxModelData> getVisorRowStitching() {
    return this.visorrowstitching;
  }
  
  public void setVisorStitchColor(ArrayList<OtherComboBoxModelData> visorstitchcolor) {
    this.visorstitchcolor = visorstitchcolor;
  }
  
  public ArrayList<OtherComboBoxModelData> getVisorStitchColor() {
    return this.visorstitchcolor;
  }
  
  public void setOriginalSize(String originalsize) {
    this.originalsize = originalsize;
  }
  
  public String getOriginalSize() {
    return this.originalsize;
  }
}
