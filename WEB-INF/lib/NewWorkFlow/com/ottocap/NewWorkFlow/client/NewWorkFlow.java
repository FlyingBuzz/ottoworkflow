package com.ottocap.NewWorkFlow.client;

import com.extjs.gxt.ui.client.core.FastMap;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.google.gwt.core.client.GWT;
import com.ottocap.NewWorkFlow.client.DataHolder.Stores.DataStores;
import com.ottocap.NewWorkFlow.client.DataHolder.Stores.NewStyleStore;
import com.ottocap.NewWorkFlow.client.DataHolder.Stores.ThreadInformationData;
import com.ottocap.NewWorkFlow.client.MainScreen.MainScreen;
import com.ottocap.NewWorkFlow.client.MainScreen.MainTab;
import com.ottocap.NewWorkFlow.client.Services.WorkFlowServiceAsync;
import com.ottocap.NewWorkFlow.client.Widget.BuzzAsyncCallback;
import com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBoxModelData;
import com.ottocap.NewWorkFlow.client.Widget.LogOffDialog;
import com.ottocap.NewWorkFlow.client.Widget.LoginDialog;
import java.util.ArrayList;
import java.util.Set;

public class NewWorkFlow implements com.google.gwt.core.client.EntryPoint
{
  public static final String baseurl = ;
  public static final String filepath = baseurl + "ottoflow/";
  public static final String ProgramTitle = "WorkFlow Program";
  private static NewWorkFlow singleton;
  private final WorkFlowServiceAsync workflowrpc = (WorkFlowServiceAsync)GWT.create(com.ottocap.NewWorkFlow.client.Services.WorkFlowService.class);
  private StoredUser storeduser = new StoredUser();
  private MainTabPanel maintabpanel;
  private DataStores datastores = new DataStores();
  private LoginDialog thelogin = new LoginDialog();
  private LogOffDialog thelogout = new LogOffDialog();
  private MessageBox waitbox = MessageBox.progress("Please wait", "Loading items...", "Initializing...");
  private Viewport viewport = new Viewport();
  public Boolean isFaya;
  
  public static NewWorkFlow get() {
    return singleton;
  }
  
  public MessageBox getWait() {
    return this.waitbox;
  }
  
  private BuzzAsyncCallback<Boolean> isfayacallback = new BuzzAsyncCallback()
  {
    public void onFailure(Throwable caught) {
      NewWorkFlow.this.workflowrpc.getIsFaya(NewWorkFlow.this.isfayacallback);
    }
    
    public void doRetry()
    {
      NewWorkFlow.this.workflowrpc.getIsFaya(NewWorkFlow.this.isfayacallback);
    }
    

    public void onSuccess(Boolean result)
    {
      NewWorkFlow.this.isFaya = result;
      NewWorkFlow.this.doAfterLoadingSettings();
    }
  };
  


  private BuzzAsyncCallback<FirstLoadData> firstloadcallback = new BuzzAsyncCallback()
  {
    public void onFailure(Throwable caught)
    {
      NewWorkFlow.this.reLogin2(caught, NewWorkFlow.this.firstloadcallback);
    }
    
    public void doRetry()
    {
      NewWorkFlow.this.workflowrpc.getFirstLoad(NewWorkFlow.this.firstloadcallback);
    }
    

    public void onSuccess(FirstLoadData result)
    {
      NewWorkFlow.this.datastores.setOrderStatusStore(result.getOrderStatus());
      NewWorkFlow.this.datastores.setContainerInvoiceStatusStore(result.getContainerInvoiceStatus());
      NewWorkFlow.this.datastores.setContainerPortofOriginStore(result.getContainerPortofOrigin());
      NewWorkFlow.this.datastores.setContainerFreightForwarderStore(result.getContainerFreightForwarder());
      NewWorkFlow.this.datastores.setShippingTypeStore(result.getShippingTypes());
      NewWorkFlow.this.datastores.setDomesticVendorStore(result.getDomesticVendors());
      NewWorkFlow.this.datastores.setCategoryListStore(result.getCategoryList());
      NewWorkFlow.this.datastores.setPontentialFreqStore(result.getPontentialRepeatFreqList());
      NewWorkFlow.this.datastores.setOverseasVendorList(result.getOverseasVendors());
      NewWorkFlow.this.datastores.setArtworkTypeStore(result.getArtworkTypes());
      NewWorkFlow.this.datastores.setDecorationInfo(result.getDecorationInfo());
      NewWorkFlow.this.datastores.getThreadsInformationData().setConvertToThreadStore(result.getConvertToThreadList());
      NewWorkFlow.this.datastores.getThreadsInformationData().setThreadBrands(result.getThreadBrandList());
      NewWorkFlow.this.datastores.setInkTypes(result.getInkTypesList());
      NewWorkFlow.this.datastores.setOverseasVendorDefaultShipping(result.getOverseasVendorDefaultShippingList());
      
      String[] thekeys = (String[])result.getThreadBrandColor().keySet().toArray(new String[0]);
      for (int i = 0; i < thekeys.length; i++) {
        NewWorkFlow.this.datastores.getThreadsInformationData().addThreadBrandColor(thekeys[i], (ArrayList)result.getThreadBrandColor().get(thekeys[i]));
      }
      
      NewWorkFlow.this.storeduser.setAccessLevel(result.getUserInfo().getAccessLevel());
      NewWorkFlow.this.storeduser.setEmailAddress(result.getUserInfo().getEmailAddress());
      NewWorkFlow.this.storeduser.setUsername(result.getUserInfo().getUsername());
      NewWorkFlow.this.waitbox.close();
      NewWorkFlow.this.maintabpanel.getMainTab().updateEmail();
      if (result.getUserInfo().getAccessLevel() == 4) {
        NewWorkFlow.this.maintabpanel.getMainTab().getMainScreen().setOnlineGatherScreen();
      } else if (result.getUserInfo().getAccessLevel() == 6) {
        NewWorkFlow.this.maintabpanel.getMainTab().getMainScreen().setContainerScreen();
      } else if (result.getUserInfo().getAccessLevel() == 0) {
        NewWorkFlow.this.maintabpanel.getMainTab().getMainScreen().setInstantQuoteSelectionScreen();
      } else {
        NewWorkFlow.this.maintabpanel.getMainTab().getMainScreen().setManageOrderScreen();
      }
    }
  };
  

  public DataStores getDataStores()
  {
    return this.datastores;
  }
  
  public StoredUser getStoredUser() {
    return this.storeduser;
  }
  
  public void onModuleLoad() {
    singleton = this;
    this.workflowrpc.getIsFaya(this.isfayacallback);
  }
  
  public void doAfterLoadingSettings() {
    if (this.isFaya.booleanValue()) {
      com.google.gwt.user.client.Window.setTitle("Faya Corporation WorkFlow Program");
    } else {
      com.google.gwt.user.client.Window.setTitle("Otto International WorkFlow Program");
    }
    
    this.maintabpanel = new MainTabPanel();
    this.maintabpanel.setTabScroll(true);
    
    this.viewport.setLayout(new com.extjs.gxt.ui.client.widget.layout.FitLayout());
    this.viewport.add(this.maintabpanel);
    com.google.gwt.user.client.ui.RootPanel.get().add(this.viewport);
    
    this.workflowrpc.getFirstLoad(this.firstloadcallback);
  }
  
  public Viewport getViewport() {
    return this.viewport;
  }
  
  public MainTabPanel getMainTabPanel() {
    return this.maintabpanel;
  }
  
  public WorkFlowServiceAsync getWorkFlowRPC() {
    return this.workflowrpc;
  }
  
  public void doLogOut() {
    this.thelogout.show();
  }
  
  public void reLogin2(Throwable caught, BuzzAsyncCallback<?> callback) {
    if ((caught.getLocalizedMessage() != null) && ((caught.getLocalizedMessage().equals("Session Expired")) || (caught.getLocalizedMessage().equals("Bad Session")) || (caught.getLocalizedMessage().equals("Please Login")))) {
      if (getStoredUser().getAccessLevel() == 0) {
        new AutoQuoteLogin(callback);
      } else {
        this.thelogin.setHeadingHtml("Login - " + caught.getLocalizedMessage());
        this.thelogin.show(callback);
      }
    } else {
      caught.printStackTrace();
      MessageBox.alert("Error", caught.getLocalizedMessage(), null);
    }
  }
  
  public void setStyleStore(StyleInformationData styleinformationdata) {
    NewStyleStore mystylestore = new NewStyleStore();
    mystylestore.setStyleType(styleinformationdata.getStyleType());
    mystylestore.setShowFOBDozen(styleinformationdata.getShowFOBDozen());
    
    mystylestore.setCategory(styleinformationdata.getCategory());
    mystylestore.setDescription(styleinformationdata.getDescription());
    
    mystylestore.setColorCodeStore(styleinformationdata.getColorCode());
    mystylestore.setSizeStore(styleinformationdata.getSize());
    mystylestore.setFrontPanelColorStore(styleinformationdata.getFrontPanelColor());
    mystylestore.setSideBackPanelColorStore(styleinformationdata.getSideBackPanelColor());
    mystylestore.setBackPanelColorStore(styleinformationdata.getBackPanelColor());
    mystylestore.setSidePanelColorStore(styleinformationdata.getSidePanelColor());
    mystylestore.setVisorStyleNumberStore(styleinformationdata.getVisorStyleNumber());
    mystylestore.setPrimaryVisorColorStore(styleinformationdata.getPrimaryVisorColor());
    mystylestore.setVisorTrimEdgeColorStore(styleinformationdata.getVisorTrimEdgeColor());
    mystylestore.setVisorSandwichColorStore(styleinformationdata.getVisorSandwichColor());
    mystylestore.setDistressedVisorInsideColorStore(styleinformationdata.getDistressedVisorInsideColor());
    mystylestore.setUndervisorColorStore(styleinformationdata.getUndervisorColor());
    mystylestore.setClosureStyleNumberStore(styleinformationdata.getClosureStyleNumber());
    mystylestore.setClosureStrapColorStore(styleinformationdata.getClosureStrapColor());
    mystylestore.setC2C31ClosureStrapColorStore(styleinformationdata.getC2C31ClosureStrapColor());
    mystylestore.setEyeletStyleNumberStore(styleinformationdata.getEyeletStyleNumber());
    mystylestore.setFrontEyeletColorStore(styleinformationdata.getFrontEyeletColor());
    mystylestore.setSideBackEyeletColorStore(styleinformationdata.getSideBackEyeletColor());
    mystylestore.setBackEyeletColorStore(styleinformationdata.getBackEyeletColor());
    mystylestore.setSideEyeletColorStore(styleinformationdata.getSideEyeletColor());
    mystylestore.setButtonColorStore(styleinformationdata.getButtonColor());
    mystylestore.setInnerTapingColorStore(styleinformationdata.getInnerTapingColor());
    mystylestore.setSweatbandStyleNumberStore(styleinformationdata.getSweatbandStyleNumber());
    mystylestore.setSweatbandColorStore(styleinformationdata.getSweatbandColor());
    mystylestore.setS5SweatbandColorStore(styleinformationdata.getS5SweatbandColor());
    mystylestore.setStripeColorStore(styleinformationdata.getStripeColor());
    mystylestore.setLogoLocationStore(styleinformationdata.getLogoLocation());
    mystylestore.setVendorNumber(styleinformationdata.getVendorNumber());
    mystylestore.setAllVendorsStore(styleinformationdata.getAllVendorsStore());
    mystylestore.setOriginalClosure(styleinformationdata.getOriginalClosure());
    mystylestore.setOriginalSize(styleinformationdata.getOriginalSize());
    mystylestore.setOriginalEyelet(styleinformationdata.getOriginalEyelet());
    mystylestore.setOriginalSweatband(styleinformationdata.getOriginalSweatband());
    mystylestore.setOriginalVisor(styleinformationdata.getOriginalVisor());
    mystylestore.setSameStyleNumber(styleinformationdata.getSameStyleNumber());
    
    mystylestore.setAllProfileStore(styleinformationdata.getAllProfileStore());
    
    mystylestore.setUseSizeColor(styleinformationdata.getUseSizeColor());
    if (mystylestore.getUseSizeColor()) {
      for (OtherComboBoxModelData currentsize : styleinformationdata.getSize()) {
        mystylestore.setSizeColorStore(currentsize.getValue(), (ArrayList)styleinformationdata.getSizeColor().get(currentsize.getValue()));
      }
    }
    
    if (styleinformationdata.getOrderType() == com.ottocap.NewWorkFlow.client.OrderData.OrderData.OrderType.DOMESTIC) {
      getDataStores().getDomesticStyleStore().put(styleinformationdata.getStyleNumber(), mystylestore);
    } else {
      getDataStores().getOverseasStyleStore().put(styleinformationdata.getStyleNumber(), mystylestore);
      

      String[] allvendorkeys = (String[])styleinformationdata.getAllVendors().keySet().toArray(new String[0]);
      for (int i = 0; i < allvendorkeys.length; i++) {
        NewStyleStore mystylestore2 = new NewStyleStore();
        mystylestore2.setStyleType(((StyleInformationData)styleinformationdata.getAllVendors().get(allvendorkeys[i])).getStyleType());
        mystylestore2.setShowFOBDozen(((StyleInformationData)styleinformationdata.getAllVendors().get(allvendorkeys[i])).getShowFOBDozen());
        
        mystylestore2.setCategory(((StyleInformationData)styleinformationdata.getAllVendors().get(allvendorkeys[i])).getCategory());
        mystylestore2.setDescription(((StyleInformationData)styleinformationdata.getAllVendors().get(allvendorkeys[i])).getDescription());
        
        mystylestore2.setColorCodeStore(((StyleInformationData)styleinformationdata.getAllVendors().get(allvendorkeys[i])).getColorCode());
        mystylestore2.setSizeStore(((StyleInformationData)styleinformationdata.getAllVendors().get(allvendorkeys[i])).getSize());
        mystylestore2.setFrontPanelColorStore(((StyleInformationData)styleinformationdata.getAllVendors().get(allvendorkeys[i])).getFrontPanelColor());
        mystylestore2.setSideBackPanelColorStore(((StyleInformationData)styleinformationdata.getAllVendors().get(allvendorkeys[i])).getSideBackPanelColor());
        mystylestore2.setBackPanelColorStore(((StyleInformationData)styleinformationdata.getAllVendors().get(allvendorkeys[i])).getBackPanelColor());
        mystylestore2.setSidePanelColorStore(((StyleInformationData)styleinformationdata.getAllVendors().get(allvendorkeys[i])).getSidePanelColor());
        mystylestore2.setVisorStyleNumberStore(((StyleInformationData)styleinformationdata.getAllVendors().get(allvendorkeys[i])).getVisorStyleNumber());
        mystylestore2.setPrimaryVisorColorStore(((StyleInformationData)styleinformationdata.getAllVendors().get(allvendorkeys[i])).getPrimaryVisorColor());
        mystylestore2.setVisorTrimEdgeColorStore(((StyleInformationData)styleinformationdata.getAllVendors().get(allvendorkeys[i])).getVisorTrimEdgeColor());
        mystylestore2.setVisorSandwichColorStore(((StyleInformationData)styleinformationdata.getAllVendors().get(allvendorkeys[i])).getVisorSandwichColor());
        mystylestore2.setDistressedVisorInsideColorStore(((StyleInformationData)styleinformationdata.getAllVendors().get(allvendorkeys[i])).getDistressedVisorInsideColor());
        mystylestore2.setUndervisorColorStore(((StyleInformationData)styleinformationdata.getAllVendors().get(allvendorkeys[i])).getUndervisorColor());
        mystylestore2.setClosureStyleNumberStore(((StyleInformationData)styleinformationdata.getAllVendors().get(allvendorkeys[i])).getClosureStyleNumber());
        mystylestore2.setClosureStrapColorStore(((StyleInformationData)styleinformationdata.getAllVendors().get(allvendorkeys[i])).getClosureStrapColor());
        mystylestore2.setC2C31ClosureStrapColorStore(((StyleInformationData)styleinformationdata.getAllVendors().get(allvendorkeys[i])).getC2C31ClosureStrapColor());
        mystylestore2.setEyeletStyleNumberStore(((StyleInformationData)styleinformationdata.getAllVendors().get(allvendorkeys[i])).getEyeletStyleNumber());
        mystylestore2.setFrontEyeletColorStore(((StyleInformationData)styleinformationdata.getAllVendors().get(allvendorkeys[i])).getFrontEyeletColor());
        mystylestore2.setSideBackEyeletColorStore(((StyleInformationData)styleinformationdata.getAllVendors().get(allvendorkeys[i])).getSideBackEyeletColor());
        mystylestore2.setBackEyeletColorStore(((StyleInformationData)styleinformationdata.getAllVendors().get(allvendorkeys[i])).getBackEyeletColor());
        mystylestore2.setSideEyeletColorStore(((StyleInformationData)styleinformationdata.getAllVendors().get(allvendorkeys[i])).getSideEyeletColor());
        mystylestore2.setButtonColorStore(((StyleInformationData)styleinformationdata.getAllVendors().get(allvendorkeys[i])).getButtonColor());
        mystylestore2.setInnerTapingColorStore(((StyleInformationData)styleinformationdata.getAllVendors().get(allvendorkeys[i])).getInnerTapingColor());
        mystylestore2.setSweatbandStyleNumberStore(((StyleInformationData)styleinformationdata.getAllVendors().get(allvendorkeys[i])).getSweatbandStyleNumber());
        mystylestore2.setSweatbandColorStore(((StyleInformationData)styleinformationdata.getAllVendors().get(allvendorkeys[i])).getSweatbandColor());
        mystylestore2.setS5SweatbandColorStore(((StyleInformationData)styleinformationdata.getAllVendors().get(allvendorkeys[i])).getS5SweatbandColor());
        mystylestore2.setStripeColorStore(((StyleInformationData)styleinformationdata.getAllVendors().get(allvendorkeys[i])).getStripeColor());
        mystylestore2.setLogoLocationStore(((StyleInformationData)styleinformationdata.getAllVendors().get(allvendorkeys[i])).getLogoLocation());
        mystylestore2.setVendorNumber(((StyleInformationData)styleinformationdata.getAllVendors().get(allvendorkeys[i])).getVendorNumber());
        mystylestore2.setAllVendorsStore(((StyleInformationData)styleinformationdata.getAllVendors().get(allvendorkeys[i])).getAllVendorsStore());
        mystylestore2.setOriginalClosure(((StyleInformationData)styleinformationdata.getAllVendors().get(allvendorkeys[i])).getOriginalClosure());
        mystylestore2.setOriginalSize(((StyleInformationData)styleinformationdata.getAllVendors().get(allvendorkeys[i])).getOriginalSize());
        mystylestore2.setOriginalEyelet(((StyleInformationData)styleinformationdata.getAllVendors().get(allvendorkeys[i])).getOriginalEyelet());
        mystylestore2.setOriginalSweatband(((StyleInformationData)styleinformationdata.getAllVendors().get(allvendorkeys[i])).getOriginalSweatband());
        mystylestore2.setOriginalVisor(((StyleInformationData)styleinformationdata.getAllVendors().get(allvendorkeys[i])).getOriginalVisor());
        mystylestore2.setSameStyleNumber(((StyleInformationData)styleinformationdata.getAllVendors().get(allvendorkeys[i])).getSameStyleNumber());
        
        mystylestore2.setUseSizeColor(((StyleInformationData)styleinformationdata.getAllVendors().get(allvendorkeys[i])).getUseSizeColor());
        if (mystylestore2.getUseSizeColor()) {
          for (OtherComboBoxModelData currentsize : ((StyleInformationData)styleinformationdata.getAllVendors().get(allvendorkeys[i])).getSize()) {
            mystylestore2.setSizeColorStore(currentsize.getValue(), (ArrayList)((StyleInformationData)styleinformationdata.getAllVendors().get(allvendorkeys[i])).getSizeColor().get(currentsize.getValue()));
          }
        }
        
        mystylestore.setAllVendors(allvendorkeys[i], mystylestore2);
      }
      

      String[] allprofilekeys = (String[])styleinformationdata.getAllProfile().keySet().toArray(new String[0]);
      for (int i = 0; i < allprofilekeys.length; i++) {
        NewStyleStore mystylestore2 = new NewStyleStore();
        mystylestore2.setStyleType(((StyleInformationData)styleinformationdata.getAllProfile().get(allprofilekeys[i])).getStyleType());
        mystylestore2.setShowFOBDozen(((StyleInformationData)styleinformationdata.getAllProfile().get(allprofilekeys[i])).getShowFOBDozen());
        
        mystylestore2.setCategory(((StyleInformationData)styleinformationdata.getAllProfile().get(allprofilekeys[i])).getCategory());
        mystylestore2.setDescription(((StyleInformationData)styleinformationdata.getAllProfile().get(allprofilekeys[i])).getDescription());
        
        mystylestore2.setSizeStore(((StyleInformationData)styleinformationdata.getAllProfile().get(allprofilekeys[i])).getSize());
        mystylestore2.setVisorStyleNumberStore(((StyleInformationData)styleinformationdata.getAllProfile().get(allprofilekeys[i])).getVisorStyleNumber());
        mystylestore2.setClosureStyleNumberStore(((StyleInformationData)styleinformationdata.getAllProfile().get(allprofilekeys[i])).getClosureStyleNumber());
        mystylestore2.setEyeletStyleNumberStore(((StyleInformationData)styleinformationdata.getAllProfile().get(allprofilekeys[i])).getEyeletStyleNumber());
        mystylestore2.setSweatbandStyleNumberStore(((StyleInformationData)styleinformationdata.getAllProfile().get(allprofilekeys[i])).getSweatbandStyleNumber());
        mystylestore2.setLogoLocationStore(((StyleInformationData)styleinformationdata.getAllProfile().get(allprofilekeys[i])).getLogoLocation());
        mystylestore2.setVendorNumber(((StyleInformationData)styleinformationdata.getAllProfile().get(allprofilekeys[i])).getVendorNumber());
        mystylestore2.setSameStyleNumber(((StyleInformationData)styleinformationdata.getAllProfile().get(allprofilekeys[i])).getSameStyleNumber());
        mystylestore2.setAllProfileStore(styleinformationdata.getAllProfileStore());
        
        mystylestore2.setNumberOfPanelsStore(((StyleInformationData)styleinformationdata.getAllProfile().get(allprofilekeys[i])).getNumberOfPanels());
        mystylestore2.setCrownConstructionStore(((StyleInformationData)styleinformationdata.getAllProfile().get(allprofilekeys[i])).getCrownConstruction());
        mystylestore2.setVisorRowStitchingStore(((StyleInformationData)styleinformationdata.getAllProfile().get(allprofilekeys[i])).getVisorRowStitching());
        
        mystylestore2.setUseSizeColor(((StyleInformationData)styleinformationdata.getAllProfile().get(allprofilekeys[i])).getUseSizeColor());
        if (mystylestore2.getUseSizeColor()) {
          for (OtherComboBoxModelData currentsize : ((StyleInformationData)styleinformationdata.getAllProfile().get(allprofilekeys[i])).getSize()) {
            mystylestore2.setSizeColorStore(currentsize.getValue(), (ArrayList)((StyleInformationData)styleinformationdata.getAllProfile().get(allprofilekeys[i])).getSizeColor().get(currentsize.getValue()));
          }
        }
        
        mystylestore2.setFrontFabricStore(((StyleInformationData)styleinformationdata.getAllProfile().get(allprofilekeys[i])).getFrontFabricStore());
        
        mystylestore2.setBackFabricStore(((StyleInformationData)styleinformationdata.getAllProfile().get(allprofilekeys[i])).getBackFabricStore());
        mystylestore2.setSideFabricStore(((StyleInformationData)styleinformationdata.getAllProfile().get(allprofilekeys[i])).getSideFabricStore());
        
        String[] allfabrickeys = (String[])((StyleInformationData)styleinformationdata.getAllProfile().get(allprofilekeys[i])).getAllFabric().keySet().toArray(new String[0]);
        for (int j = 0; j < allfabrickeys.length; j++) {
          NewStyleStore mystylestore3 = new NewStyleStore();
          
          mystylestore3.setColorCodeStore(((StyleInformationData)((StyleInformationData)styleinformationdata.getAllProfile().get(allprofilekeys[i])).getAllFabric().get(allfabrickeys[j])).getColorCode());
          mystylestore3.setFrontPanelColorStore(((StyleInformationData)((StyleInformationData)styleinformationdata.getAllProfile().get(allprofilekeys[i])).getAllFabric().get(allfabrickeys[j])).getFrontPanelColor());
          mystylestore3.setSideBackPanelColorStore(((StyleInformationData)((StyleInformationData)styleinformationdata.getAllProfile().get(allprofilekeys[i])).getAllFabric().get(allfabrickeys[j])).getSideBackPanelColor());
          mystylestore3.setBackPanelColorStore(((StyleInformationData)((StyleInformationData)styleinformationdata.getAllProfile().get(allprofilekeys[i])).getAllFabric().get(allfabrickeys[j])).getBackPanelColor());
          mystylestore3.setSidePanelColorStore(((StyleInformationData)((StyleInformationData)styleinformationdata.getAllProfile().get(allprofilekeys[i])).getAllFabric().get(allfabrickeys[j])).getSidePanelColor());
          mystylestore3.setPrimaryVisorColorStore(((StyleInformationData)((StyleInformationData)styleinformationdata.getAllProfile().get(allprofilekeys[i])).getAllFabric().get(allfabrickeys[j])).getPrimaryVisorColor());
          mystylestore3.setVisorTrimEdgeColorStore(((StyleInformationData)((StyleInformationData)styleinformationdata.getAllProfile().get(allprofilekeys[i])).getAllFabric().get(allfabrickeys[j])).getVisorTrimEdgeColor());
          mystylestore3.setVisorSandwichColorStore(((StyleInformationData)((StyleInformationData)styleinformationdata.getAllProfile().get(allprofilekeys[i])).getAllFabric().get(allfabrickeys[j])).getVisorSandwichColor());
          mystylestore3.setDistressedVisorInsideColorStore(((StyleInformationData)((StyleInformationData)styleinformationdata.getAllProfile().get(allprofilekeys[i])).getAllFabric().get(allfabrickeys[j])).getDistressedVisorInsideColor());
          mystylestore3.setUndervisorColorStore(((StyleInformationData)((StyleInformationData)styleinformationdata.getAllProfile().get(allprofilekeys[i])).getAllFabric().get(allfabrickeys[j])).getUndervisorColor());
          mystylestore3.setClosureStrapColorStore(((StyleInformationData)((StyleInformationData)styleinformationdata.getAllProfile().get(allprofilekeys[i])).getAllFabric().get(allfabrickeys[j])).getClosureStrapColor());
          mystylestore3.setC2C31ClosureStrapColorStore(((StyleInformationData)((StyleInformationData)styleinformationdata.getAllProfile().get(allprofilekeys[i])).getAllFabric().get(allfabrickeys[j])).getC2C31ClosureStrapColor());
          mystylestore3.setFrontEyeletColorStore(((StyleInformationData)((StyleInformationData)styleinformationdata.getAllProfile().get(allprofilekeys[i])).getAllFabric().get(allfabrickeys[j])).getFrontEyeletColor());
          mystylestore3.setSideBackEyeletColorStore(((StyleInformationData)((StyleInformationData)styleinformationdata.getAllProfile().get(allprofilekeys[i])).getAllFabric().get(allfabrickeys[j])).getSideBackEyeletColor());
          mystylestore3.setBackEyeletColorStore(((StyleInformationData)((StyleInformationData)styleinformationdata.getAllProfile().get(allprofilekeys[i])).getAllFabric().get(allfabrickeys[j])).getBackEyeletColor());
          mystylestore3.setSideEyeletColorStore(((StyleInformationData)((StyleInformationData)styleinformationdata.getAllProfile().get(allprofilekeys[i])).getAllFabric().get(allfabrickeys[j])).getSideEyeletColor());
          mystylestore3.setButtonColorStore(((StyleInformationData)((StyleInformationData)styleinformationdata.getAllProfile().get(allprofilekeys[i])).getAllFabric().get(allfabrickeys[j])).getButtonColor());
          mystylestore3.setInnerTapingColorStore(((StyleInformationData)((StyleInformationData)styleinformationdata.getAllProfile().get(allprofilekeys[i])).getAllFabric().get(allfabrickeys[j])).getInnerTapingColor());
          mystylestore3.setSweatbandColorStore(((StyleInformationData)((StyleInformationData)styleinformationdata.getAllProfile().get(allprofilekeys[i])).getAllFabric().get(allfabrickeys[j])).getSweatbandColor());
          mystylestore3.setS5SweatbandColorStore(((StyleInformationData)((StyleInformationData)styleinformationdata.getAllProfile().get(allprofilekeys[i])).getAllFabric().get(allfabrickeys[j])).getS5SweatbandColor());
          mystylestore3.setStripeColorStore(((StyleInformationData)((StyleInformationData)styleinformationdata.getAllProfile().get(allprofilekeys[i])).getAllFabric().get(allfabrickeys[j])).getStripeColor());
          

          mystylestore3.setVisorStitchColorStore(((StyleInformationData)((StyleInformationData)styleinformationdata.getAllProfile().get(allprofilekeys[i])).getAllFabric().get(allfabrickeys[j])).getVisorStitchColor());
          mystylestore3.setPanelStitchColorStore(((StyleInformationData)((StyleInformationData)styleinformationdata.getAllProfile().get(allprofilekeys[i])).getAllFabric().get(allfabrickeys[j])).getPanelStitchColor());
          
          mystylestore2.setAllFabric(allfabrickeys[j], mystylestore3);
        }
        

        mystylestore.setAllProfile(allprofilekeys[i], mystylestore2);
      }
    }
  }
}
