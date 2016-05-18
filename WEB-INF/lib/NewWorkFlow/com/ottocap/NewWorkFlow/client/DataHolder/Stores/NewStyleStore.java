package com.ottocap.NewWorkFlow.client.DataHolder.Stores;

import com.extjs.gxt.ui.client.core.FastMap;
import com.extjs.gxt.ui.client.store.ListStore;
import com.ottocap.NewWorkFlow.client.StyleInformationData.StyleType;
import com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBoxModelData;
import java.util.ArrayList;


public class NewStyleStore
{
  private StyleInformationData.StyleType styletype = null;
  private String category = "";
  private boolean showfobdozen = false;
  private boolean usesizecolor = false;
  private FastMap<ListStore<OtherComboBoxModelData>> sizecolorstore = new FastMap();
  private ListStore<OtherComboBoxModelData> colorcodestore = new ListStore();
  private ListStore<OtherComboBoxModelData> sizestore = new ListStore();
  private ListStore<OtherComboBoxModelData> frontpanelcolorstore = new ListStore();
  private ListStore<OtherComboBoxModelData> sidebackpanelcolorstore = new ListStore();
  private ListStore<OtherComboBoxModelData> backpanelcolorstore = new ListStore();
  private ListStore<OtherComboBoxModelData> sidepanelcolorstore = new ListStore();
  private ListStore<OtherComboBoxModelData> visorstylenumberstore = new ListStore();
  private ListStore<OtherComboBoxModelData> primaryvisorcolorstore = new ListStore();
  private ListStore<OtherComboBoxModelData> visortrimedgecolorstore = new ListStore();
  private ListStore<OtherComboBoxModelData> visorsandwichcolorstore = new ListStore();
  private ListStore<OtherComboBoxModelData> undervisorcolorstore = new ListStore();
  private ListStore<OtherComboBoxModelData> closurestylenumberstore = new ListStore();
  private ListStore<OtherComboBoxModelData> closurestrapcolorstore = new ListStore();
  private ListStore<OtherComboBoxModelData> c2c31closurestrapcolorstore = new ListStore();
  private ListStore<OtherComboBoxModelData> eyeletstylenumberstore = new ListStore();
  private ListStore<OtherComboBoxModelData> fronteyeletcolorstore = new ListStore();
  private ListStore<OtherComboBoxModelData> sidebackeyeletcolorstore = new ListStore();
  private ListStore<OtherComboBoxModelData> backeyeletcolorstore = new ListStore();
  private ListStore<OtherComboBoxModelData> sideeyeletcolorstore = new ListStore();
  private ListStore<OtherComboBoxModelData> buttoncolorstore = new ListStore();
  private ListStore<OtherComboBoxModelData> innertapingcolorstore = new ListStore();
  private ListStore<OtherComboBoxModelData> sweatbandstylenumberstore = new ListStore();
  private ListStore<OtherComboBoxModelData> sweatbandcolorstore = new ListStore();
  private ListStore<OtherComboBoxModelData> s5sweatbandcolorstore = new ListStore();
  private ListStore<OtherComboBoxModelData> stripecolorstore = new ListStore();
  private ListStore<OtherComboBoxModelData> distressedvisorinsidecolorstore = new ListStore();
  private ListStore<OtherComboBoxModelData> logolocationstore = new ListStore();
  private ListStore<OtherComboBoxModelData> allvendorsstore = new ListStore();
  private ListStore<OtherComboBoxModelData> allprofilestore = new ListStore();
  private ListStore<OtherComboBoxModelData> frontfabricstore = new ListStore();
  private ListStore<OtherComboBoxModelData> sidebackfabricstore = new ListStore();
  private ListStore<OtherComboBoxModelData> backfabricstore = new ListStore();
  private ListStore<OtherComboBoxModelData> sidefabricstore = new ListStore();
  private ListStore<OtherComboBoxModelData> numberofpanelsstore = new ListStore();
  private ListStore<OtherComboBoxModelData> crownconstructionstore = new ListStore();
  private ListStore<OtherComboBoxModelData> panelstitchcolorstore = new ListStore();
  private ListStore<OtherComboBoxModelData> visorstitchcolorstore = new ListStore();
  private ListStore<OtherComboBoxModelData> visorrowstitchingstore = new ListStore();
  
  private int vendornumber;
  private String originalclosure = "";
  private String originaleyelet = "";
  private String originalsweatband = "";
  private String originalvisor = "";
  private String description = "";
  private String samestylenumber = "";
  private String originalsize = "";
  
  private FastMap<NewStyleStore> allvendors = new FastMap();
  private FastMap<NewStyleStore> allprofile = new FastMap();
  private FastMap<NewStyleStore> allfabric = new FastMap();
  
  public void setSizeColorStore(String size, ArrayList<OtherComboBoxModelData> colorcodelist) {
    ListStore<OtherComboBoxModelData> currentstore = new ListStore();
    currentstore.add(colorcodelist);
    this.sizecolorstore.put(size, currentstore);
  }
  
  public FastMap<ListStore<OtherComboBoxModelData>> getSizeColorStore() {
    return this.sizecolorstore;
  }
  
  public void setColorCodeStore(ArrayList<OtherComboBoxModelData> colorcodelist) {
    this.colorcodestore.add(colorcodelist);
  }
  
  public ListStore<OtherComboBoxModelData> getColorCodeStore() {
    return this.colorcodestore;
  }
  
  public void setStyleType(StyleInformationData.StyleType styletype) {
    this.styletype = styletype;
  }
  
  public StyleInformationData.StyleType getStyleType() {
    return this.styletype;
  }
  
  public void setShowFOBDozen(boolean showfobdozen) {
    this.showfobdozen = showfobdozen;
  }
  
  public boolean getShowFOBDozen() {
    return this.showfobdozen;
  }
  
  public void setFrontPanelColorStore(ArrayList<OtherComboBoxModelData> frontpanelcolorlist) {
    this.frontpanelcolorstore.add(frontpanelcolorlist);
  }
  
  public ListStore<OtherComboBoxModelData> getFrontPanelColorStore() {
    return this.frontpanelcolorstore;
  }
  
  public void setSideBackPanelColorStore(ArrayList<OtherComboBoxModelData> sidebackpanelcolorlist) {
    this.sidebackpanelcolorstore.add(sidebackpanelcolorlist);
  }
  
  public ListStore<OtherComboBoxModelData> getSideBackPanelColorStore() {
    return this.sidebackpanelcolorstore;
  }
  
  public void setBackPanelColorStore(ArrayList<OtherComboBoxModelData> backpanelcolorlist) {
    this.backpanelcolorstore.add(backpanelcolorlist);
  }
  
  public ListStore<OtherComboBoxModelData> getBackPanelColorStore() {
    return this.backpanelcolorstore;
  }
  
  public void setSidePanelColorStore(ArrayList<OtherComboBoxModelData> sidepanelcolorlist) {
    this.sidepanelcolorstore.add(sidepanelcolorlist);
  }
  
  public ListStore<OtherComboBoxModelData> getSidePanelColorStore() {
    return this.sidepanelcolorstore;
  }
  
  public void setVisorStyleNumberStore(ArrayList<OtherComboBoxModelData> visorstylenumberlist)
  {
    this.visorstylenumberstore.add(visorstylenumberlist);
  }
  
  public ListStore<OtherComboBoxModelData> getVisorStyleNumberStore() {
    return this.visorstylenumberstore;
  }
  
  public void setPrimaryVisorColorStore(ArrayList<OtherComboBoxModelData> primaryvisorcolorlist) {
    this.primaryvisorcolorstore.add(primaryvisorcolorlist);
  }
  
  public ListStore<OtherComboBoxModelData> getPrimaryVisorColorStore() {
    return this.primaryvisorcolorstore;
  }
  
  public void setVisorTrimEdgeColorStore(ArrayList<OtherComboBoxModelData> visortrimedgecolorlist) {
    this.visortrimedgecolorstore.add(visortrimedgecolorlist);
  }
  
  public ListStore<OtherComboBoxModelData> getVisorTrimEdgeColorStore() {
    return this.visortrimedgecolorstore;
  }
  
  public void setVisorSandwichColorStore(ArrayList<OtherComboBoxModelData> visorsandwichcolorlist) {
    this.visorsandwichcolorstore.add(visorsandwichcolorlist);
  }
  
  public ListStore<OtherComboBoxModelData> getVisorSandwichColorStore() {
    return this.visorsandwichcolorstore;
  }
  
  public void setUndervisorColorStore(ArrayList<OtherComboBoxModelData> undervisorcolorlist) {
    this.undervisorcolorstore.add(undervisorcolorlist);
  }
  
  public ListStore<OtherComboBoxModelData> getUndervisorColorStore() {
    return this.undervisorcolorstore;
  }
  
  public void setClosureStyleNumberStore(ArrayList<OtherComboBoxModelData> closurestylenumberlist) {
    this.closurestylenumberstore.add(closurestylenumberlist);
  }
  
  public ListStore<OtherComboBoxModelData> getClosureStyleNumberStore() {
    return this.closurestylenumberstore;
  }
  
  public void setClosureStrapColorStore(ArrayList<OtherComboBoxModelData> closurestrapcolorlist) {
    this.closurestrapcolorstore.add(closurestrapcolorlist);
  }
  
  public ListStore<OtherComboBoxModelData> getClosureStrapColorStore() {
    return this.closurestrapcolorstore;
  }
  
  public void setC2C31ClosureStrapColorStore(ArrayList<OtherComboBoxModelData> c2c31closurestrapcolorlist) {
    this.c2c31closurestrapcolorstore.add(c2c31closurestrapcolorlist);
  }
  
  public ListStore<OtherComboBoxModelData> getC2C31ClosureStrapColorStore() {
    return this.c2c31closurestrapcolorstore;
  }
  
  public void setEyeletStyleNumberStore(ArrayList<OtherComboBoxModelData> eyeletstylenumberlist) {
    this.eyeletstylenumberstore.add(eyeletstylenumberlist);
  }
  
  public ListStore<OtherComboBoxModelData> getEyeletStyleNumberStore() {
    return this.eyeletstylenumberstore;
  }
  
  public void setFrontEyeletColorStore(ArrayList<OtherComboBoxModelData> fronteyeletcolorlist) {
    this.fronteyeletcolorstore.add(fronteyeletcolorlist);
  }
  
  public ListStore<OtherComboBoxModelData> getFrontEyeletColorStore() {
    return this.fronteyeletcolorstore;
  }
  
  public void setSideBackEyeletColorStore(ArrayList<OtherComboBoxModelData> sidebackeyeletcolorlist) {
    this.sidebackeyeletcolorstore.add(sidebackeyeletcolorlist);
  }
  
  public ListStore<OtherComboBoxModelData> getSideBackEyeletColorStore() {
    return this.sidebackeyeletcolorstore;
  }
  
  public void setBackEyeletColorStore(ArrayList<OtherComboBoxModelData> backeyeletcolorlist) {
    this.backeyeletcolorstore.add(backeyeletcolorlist);
  }
  
  public ListStore<OtherComboBoxModelData> getBackEyeletColorStore() {
    return this.backeyeletcolorstore;
  }
  
  public void setSideEyeletColorStore(ArrayList<OtherComboBoxModelData> sideeyeletcolorlist) {
    this.sideeyeletcolorstore.add(sideeyeletcolorlist);
  }
  
  public ListStore<OtherComboBoxModelData> getSideEyeletColorStore() {
    return this.sideeyeletcolorstore;
  }
  
  public void setButtonColorStore(ArrayList<OtherComboBoxModelData> buttoncolorlist) {
    this.buttoncolorstore.add(buttoncolorlist);
  }
  
  public ListStore<OtherComboBoxModelData> getButtonColorStore() {
    return this.buttoncolorstore;
  }
  
  public void setInnerTapingColorStore(ArrayList<OtherComboBoxModelData> innertapingcolorlist) {
    this.innertapingcolorstore.add(innertapingcolorlist);
  }
  
  public ListStore<OtherComboBoxModelData> getInnerTapingColorStore() {
    return this.innertapingcolorstore;
  }
  
  public void setSweatbandStyleNumberStore(ArrayList<OtherComboBoxModelData> sweatbandstylenumberlist) {
    this.sweatbandstylenumberstore.add(sweatbandstylenumberlist);
  }
  
  public ListStore<OtherComboBoxModelData> getSweatbandStyleNumberStore() {
    return this.sweatbandstylenumberstore;
  }
  
  public void setSweatbandColorStore(ArrayList<OtherComboBoxModelData> sweatbandcolorlist) {
    this.sweatbandcolorstore.add(sweatbandcolorlist);
  }
  
  public ListStore<OtherComboBoxModelData> getSweatbandColorStore() {
    return this.sweatbandcolorstore;
  }
  
  public void setS5SweatbandColorStore(ArrayList<OtherComboBoxModelData> s5sweatbandcolorlist) {
    this.s5sweatbandcolorstore.add(s5sweatbandcolorlist);
  }
  
  public ListStore<OtherComboBoxModelData> getS5SweatbandColorStore() {
    return this.s5sweatbandcolorstore;
  }
  
  public void setStripeColorStore(ArrayList<OtherComboBoxModelData> stripecolorlist) {
    this.stripecolorstore.add(stripecolorlist);
  }
  
  public ListStore<OtherComboBoxModelData> getStripeColorStore() {
    return this.stripecolorstore;
  }
  
  public void setSizeStore(ArrayList<OtherComboBoxModelData> sizelist) {
    this.sizestore.add(sizelist);
  }
  
  public ListStore<OtherComboBoxModelData> getSizeStore() {
    return this.sizestore;
  }
  
  public void setUseSizeColor(boolean usesizecolor) {
    this.usesizecolor = usesizecolor;
  }
  
  public boolean getUseSizeColor() {
    return this.usesizecolor;
  }
  
  public void setDistressedVisorInsideColorStore(ArrayList<OtherComboBoxModelData> distressedvisorinsidecolorlist) {
    this.distressedvisorinsidecolorstore.add(distressedvisorinsidecolorlist);
  }
  
  public ListStore<OtherComboBoxModelData> getDistressedVisorInsideColorStore() {
    return this.distressedvisorinsidecolorstore;
  }
  
  public void setAllVendorsStore(ArrayList<OtherComboBoxModelData> allvendorslist) {
    this.allvendorsstore.add(allvendorslist);
  }
  
  public ListStore<OtherComboBoxModelData> getAllVendorsStore() {
    return this.allvendorsstore;
  }
  
  public void setLogoLocationStore(ArrayList<OtherComboBoxModelData> logolocationlist) {
    this.logolocationstore.add(logolocationlist);
  }
  
  public ListStore<OtherComboBoxModelData> getLogoLocationStore() {
    return this.logolocationstore;
  }
  
  public void setVendorNumber(int vendornumber) {
    this.vendornumber = vendornumber;
  }
  
  public int getVendorNumber() {
    return this.vendornumber;
  }
  
  public void setCategory(String category) {
    this.category = category;
  }
  
  public String getCategory() {
    return this.category;
  }
  
  public void setOriginalClosure(String originalclosure) {
    this.originalclosure = originalclosure;
  }
  
  public String getOriginalClosure() {
    return this.originalclosure;
  }
  
  public void setOriginalEyelet(String originaleyelet) {
    this.originaleyelet = originaleyelet;
  }
  
  public String getOriginalEyelet() {
    return this.originaleyelet;
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
  
  public NewStyleStore getAllVendors(String vendornumber) {
    return (NewStyleStore)this.allvendors.get(vendornumber);
  }
  
  public void setAllVendors(String vendornumber, NewStyleStore stylestore) {
    this.allvendors.put(vendornumber, stylestore);
  }
  
  public void setFrontFabricStore(ArrayList<OtherComboBoxModelData> frontfabriclist) {
    this.frontfabricstore.add(frontfabriclist);
  }
  
  public ListStore<OtherComboBoxModelData> getFrontFabricStore() {
    return this.frontfabricstore;
  }
  
  public void setSideBackFabricStore(ArrayList<OtherComboBoxModelData> sidebackfabriclist) {
    this.sidebackfabricstore.add(sidebackfabriclist);
  }
  
  public ListStore<OtherComboBoxModelData> getSideBackFabricStore() {
    return this.sidebackfabricstore;
  }
  
  public void setBackFabricStore(ArrayList<OtherComboBoxModelData> backfabriclist) {
    this.backfabricstore.add(backfabriclist);
  }
  
  public ListStore<OtherComboBoxModelData> getBackFabricStore() {
    return this.backfabricstore;
  }
  
  public void setSideFabricStore(ArrayList<OtherComboBoxModelData> sidefabriclist) {
    this.sidefabricstore.add(sidefabriclist);
  }
  
  public ListStore<OtherComboBoxModelData> getSideFabricStore() {
    return this.sidefabricstore;
  }
  
  public NewStyleStore getAllFabric(String fabrictype) {
    NewStyleStore thefabric = (NewStyleStore)this.allfabric.get(fabrictype);
    if (thefabric == null) {
      return (NewStyleStore)this.allfabric.get("Custom");
    }
    return thefabric;
  }
  

  public void setAllFabric(String fabrictype, NewStyleStore stylestore)
  {
    this.allfabric.put(fabrictype, stylestore);
  }
  
  public void setAllProfileStore(ArrayList<OtherComboBoxModelData> allprofilelist) {
    this.allprofilestore.add(allprofilelist);
  }
  
  public ListStore<OtherComboBoxModelData> getAllProfileStore() {
    return this.allprofilestore;
  }
  
  public NewStyleStore getAllProfile(String vendornumber) {
    return (NewStyleStore)this.allprofile.get(vendornumber);
  }
  
  public void setAllProfile(String vendornumber, NewStyleStore stylestore) {
    this.allprofile.put(vendornumber, stylestore);
  }
  
  public void setNumberOfPanelsStore(ArrayList<OtherComboBoxModelData> numberofpanelslist) {
    this.numberofpanelsstore.add(numberofpanelslist);
  }
  
  public ListStore<OtherComboBoxModelData> getNumberOfPanelsStore() {
    return this.numberofpanelsstore;
  }
  
  public void setCrownConstructionStore(ArrayList<OtherComboBoxModelData> crownconstructionlist) {
    this.crownconstructionstore.add(crownconstructionlist);
  }
  
  public ListStore<OtherComboBoxModelData> getCrownConstructionStore() {
    return this.crownconstructionstore;
  }
  
  public void setPanelStitchColorStore(ArrayList<OtherComboBoxModelData> panelstitchcolorlist) {
    this.panelstitchcolorstore.add(panelstitchcolorlist);
  }
  
  public ListStore<OtherComboBoxModelData> getPanelStitchColorStore() {
    return this.panelstitchcolorstore;
  }
  
  public void setVisorStitchColorStore(ArrayList<OtherComboBoxModelData> visorstitchcolorlist) {
    this.visorstitchcolorstore.add(visorstitchcolorlist);
  }
  
  public ListStore<OtherComboBoxModelData> getVisorStitchColorStore() {
    return this.visorstitchcolorstore;
  }
  
  public void setVisorRowStitchingStore(ArrayList<OtherComboBoxModelData> visorrowstitchinglist) {
    this.visorrowstitchingstore.add(visorrowstitchinglist);
  }
  
  public ListStore<OtherComboBoxModelData> getVisorRowStitchingStore() {
    return this.visorrowstitchingstore;
  }
  
  public void setOriginalSize(String originalsize)
  {
    this.originalsize = originalsize;
  }
  
  public String getOriginalSize() {
    return this.originalsize;
  }
}
