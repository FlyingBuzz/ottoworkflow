package com.ottocap.NewWorkFlow.client.OnlineWorkflow;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.VerticalAlignment;
import com.extjs.gxt.ui.client.core.FastMap;
import com.extjs.gxt.ui.client.core.Template;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Html;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.form.RadioGroup;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.TableData;
import com.extjs.gxt.ui.client.widget.tips.ToolTipConfig;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.PushButton;
import com.ottocap.NewWorkFlow.client.DataHolder.ProductsInfo.ItemPortletField;
import com.ottocap.NewWorkFlow.client.DataHolder.ProductsInfo.SetField;
import com.ottocap.NewWorkFlow.client.DataHolder.Stores.NewStyleStore;
import com.ottocap.NewWorkFlow.client.NewWorkFlow;
import com.ottocap.NewWorkFlow.client.OnlineWorkflow.Widget.BlackLineBar;
import com.ottocap.NewWorkFlow.client.OnlineWorkflow.Widget.GreyDescription;
import com.ottocap.NewWorkFlow.client.OnlineWorkflow.Widget.HeadingBox;
import com.ottocap.NewWorkFlow.client.OnlineWorkflow.Widget.LogoHeadingBox;
import com.ottocap.NewWorkFlow.client.OnlineWorkflow.Widget.PaddedBodyContainer;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData.OrderType;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataItem;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogo;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataSet;
import com.ottocap.NewWorkFlow.client.Widget.BuzzEvents;
import com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBox;
import com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBoxModelData;
import com.ottocap.NewWorkFlow.client.Widget.FormHorizontalPanel2;
import java.util.ArrayList;

public class OnlineProductDesignTab extends VerticalPanel
{
  private OrderDataItem orderdataitem;
  private OrderDataSet orderdataset;
  private OrderData orderdata;
  private InstantQuoteMainScreen instantquotemainscreen;
  private PushButton menu_previousitem = new PushButton();
  private PushButton menu_nextitem = new PushButton();
  private PushButton menu_deleteitem = new PushButton();
  private PushButton menu_copyitem = new PushButton();
  private PushButton menu_addnewitem = new PushButton();
  private PushButton menu_gotostep2 = new PushButton();
  private PushButton button_removeitem = new PushButton();
  private PushButton button_addlogo = new PushButton();
  private HeadingBox productinfoheadingbox = new HeadingBox("");
  private LogoHeadingBox designinfoheadingbox = new LogoHeadingBox("");
  private Html blueitem = new Html();
  private ItemPortletField itemportletfield;
  private SetField setfield;
  private VerticalPanel logopanel = new VerticalPanel();
  
  private ToolTipConfig visortooltip = new ToolTipConfig();
  private ToolTipConfig sunvisortooltip = new ToolTipConfig();
  private ArrayList<OnlineDomesticLogoLayout> domesticlogolist = new ArrayList();
  private ArrayList<OnlineOverseasLogoLayout> overseaslogolist = new ArrayList();
  
  public OnlineProductDesignTab(OrderData orderdata, OrderDataSet orderdataset, InstantQuoteMainScreen instantquotemainscreen) {
    this.instantquotemainscreen = instantquotemainscreen;
    this.orderdataset = orderdataset;
    this.orderdata = orderdata;
    if (orderdataset.getItemCount() == 0) {
      this.orderdataitem = orderdataset.addItem();
      if ((Cookies.getCookie("defaultstyle") != null) && (!Cookies.getCookie("defaultstyle").equals("")) && (!Cookies.getCookie("defaultstyle").equals("N/A"))) {
        this.orderdataitem.setStyleNumber(Cookies.getCookie("defaultstyle"));
      }
      if ((Cookies.getCookie("defaultcolor") != null) && (!Cookies.getCookie("defaultcolor").equals("")) && (!Cookies.getCookie("defaultcolor").equals("N/A"))) {
        this.orderdataitem.setColorCode(Cookies.getCookie("defaultcolor"));
      }
    } else {
      this.orderdataitem = orderdataset.getItem(0);
    }
    
    this.setfield = new SetField(orderdataset);
    this.itemportletfield = new ItemPortletField(orderdata, orderdataset, this.orderdataitem, this.setfield);
    this.setfield.addListener(BuzzEvents.SetLogoLocations, this.setlogolocationslistener);
    
    this.itemportletfield.getProductPreviewImageField().setWidth(960);
    
    setStyleAttribute("margin-left", "auto");
    setStyleAttribute("margin-right", "auto");
    setWidth(1000);
    
    this.menu_previousitem.setStylePrimaryName("previousitem-PushButton");
    this.menu_nextitem.setStylePrimaryName("nextitem-PushButton");
    this.menu_deleteitem.setStylePrimaryName("pushdeleteitem-PushButton");
    this.menu_copyitem.setStylePrimaryName("pushcopythisitem-PushButton");
    this.menu_addnewitem.setStylePrimaryName("pushaddnewitem-PushButton");
    this.button_removeitem.setStylePrimaryName("pushdeletelogo-PushButton");
    this.button_addlogo.setStylePrimaryName("pushaddnewlogo-PushButton");
    
    if (instantquotemainscreen.getOnlineType() == InstantQuoteMainScreen.OnlineType.VIRTUALSAMPLE) {
      this.menu_gotostep2.setStylePrimaryName("pushgotostepbuttonvirtualsample-PushButton");
    } else {
      this.menu_gotostep2.setStylePrimaryName("pushgotostepbutton-PushButton");
    }
    
    this.blueitem.setStyleAttribute("color", "#0000ff");
    
    this.menu_previousitem.addClickHandler(this.clickhandler);
    this.menu_nextitem.addClickHandler(this.clickhandler);
    this.menu_deleteitem.addClickHandler(this.clickhandler);
    this.menu_copyitem.addClickHandler(this.clickhandler);
    this.menu_addnewitem.addClickHandler(this.clickhandler);
    this.menu_gotostep2.addClickHandler(this.clickhandler);
    this.button_removeitem.addClickHandler(this.clickhandler);
    this.button_addlogo.addClickHandler(this.clickhandler);
    this.itemportletfield.addListener(BuzzEvents.StyleChanged, this.stylechangedlistener);
    this.itemportletfield.addListener(BuzzEvents.AddLogo, this.addlogolistener);
    
    doToolTips();
    
    fixItemDisplay();
    
    add(this.productinfoheadingbox);
    if (orderdata.getOrderType() == OrderData.OrderType.DOMESTIC) {
      add(new GreyDescription("<B>Please select a product type, style number, and color. There is a 48 pieces minimum per style, design, and decoration location. To quickly copy the same product with the same design information for small changes, you can utilize the Copy Item button below. You may also add additional items to the quote by clicking on Add New Item below.</B>"));
    } else {
      add(new GreyDescription("<B>Please select a product type, style number, and color. There is a 144 pieces minimum per style, design, and decoration location. To quickly copy the same product with the same design information for small changes, you can utilize the Copy Item button below. You may also add additional items to the quote by clicking on Add New Item below.</B>"));
    }
    

    PaddedBodyContainer iteminfocontainer = new PaddedBodyContainer(960);
    
    FormHorizontalPanel2 itemtextandremoverow = new FormHorizontalPanel2();
    itemtextandremoverow.add(this.blueitem);
    itemtextandremoverow.add(this.button_removeitem);
    
    iteminfocontainer.add(itemtextandremoverow);
    
    FormHorizontalPanel2 productsizecolorpanel = new FormHorizontalPanel2();
    FormHorizontalPanel2 quantityfobpanel = new FormHorizontalPanel2();
    FormHorizontalPanel2 frontsidecolorpanel = new FormHorizontalPanel2();
    FormHorizontalPanel2 visorpanel = new FormHorizontalPanel2();
    FormHorizontalPanel2 closurepanel = new FormHorizontalPanel2();
    FormHorizontalPanel2 eyeletpanel = new FormHorizontalPanel2();
    FormHorizontalPanel2 sweatbandpanel = new FormHorizontalPanel2();
    
    FormHorizontalPanel2 productstylepanel = new FormHorizontalPanel2();
    productstylepanel.add(this.itemportletfield.getProductCategoryField());
    productstylepanel.add(this.itemportletfield.getProductStyleNumberField());
    productstylepanel.add(this.itemportletfield.getAdvanceCustomizationGroupField());
    iteminfocontainer.add(productstylepanel);
    
    productsizecolorpanel.add(this.itemportletfield.getProductSizeField());
    productsizecolorpanel.add(this.itemportletfield.getProductColorField());
    iteminfocontainer.add(productsizecolorpanel);
    
    quantityfobpanel.add(this.itemportletfield.getQuantityField());
    quantityfobpanel.add(this.itemportletfield.getCustomStyleNameField());
    iteminfocontainer.add(quantityfobpanel);
    
    frontsidecolorpanel.add(this.itemportletfield.getFrontPanelColorField());
    frontsidecolorpanel.add(this.itemportletfield.getSideBackPanelColorField());
    frontsidecolorpanel.add(this.itemportletfield.getButtonColorField());
    frontsidecolorpanel.add(this.itemportletfield.getInnerTapingColorField());
    iteminfocontainer.add(frontsidecolorpanel);
    
    visorpanel.add(this.itemportletfield.getVisorStyleNumberField());
    visorpanel.add(this.itemportletfield.getPrimaryVisorColorField());
    visorpanel.add(this.itemportletfield.getVisorTrimEdgeColorField());
    visorpanel.add(this.itemportletfield.getVisorSandwichColorField());
    visorpanel.add(this.itemportletfield.getDistressedVisorInsideColorField());
    visorpanel.add(this.itemportletfield.getUndervisorColorField());
    iteminfocontainer.add(visorpanel);
    
    closurepanel.add(this.itemportletfield.getClosureStyleNumberField());
    closurepanel.add(this.itemportletfield.getClosureStrapColorField());
    iteminfocontainer.add(closurepanel);
    
    eyeletpanel.add(this.itemportletfield.getEyeletStyleNumberField());
    eyeletpanel.add(this.itemportletfield.getFrontEyeletColorField());
    eyeletpanel.add(this.itemportletfield.getSideBackEyeletColorField());
    iteminfocontainer.add(eyeletpanel);
    
    sweatbandpanel.add(this.itemportletfield.getSweatbandStyleNumberField());
    sweatbandpanel.add(this.itemportletfield.getSweatbandColorField());
    sweatbandpanel.add(this.itemportletfield.getSweatbandStripeColorField());
    iteminfocontainer.add(sweatbandpanel);
    
    FormHorizontalPanel2 proofspanel = new FormHorizontalPanel2();
    proofspanel.add(new Html("<B>If a pre-production sample is required, please select a proof method by:</B>"));
    proofspanel.add(this.itemportletfield.getEmailProofField(), new FormData(91, 0), true);
    proofspanel.add(this.itemportletfield.getShipProofField(), new FormData(91, 0), true);
    iteminfocontainer.add(proofspanel);
    
    if (orderdata.getOrderType() == OrderData.OrderType.OVERSEAS) {
      HorizontalPanel privateorstandardlabelpanel = new HorizontalPanel();
      privateorstandardlabelpanel.add(new Html("<B>Please select the type of label:</B>"));
      this.itemportletfield.getPrivateOrStandardLabelGroupField().setStyleAttribute("margin-left", "20px");
      privateorstandardlabelpanel.add(this.itemportletfield.getPrivateOrStandardLabelGroupField());
      iteminfocontainer.add(privateorstandardlabelpanel);
    }
    

    TableData tabledata = new TableData(Style.HorizontalAlignment.CENTER, Style.VerticalAlignment.TOP);
    iteminfocontainer.add(this.itemportletfield.getProductPreviewImageField(), tabledata);
    
    add(iteminfocontainer);
    

    add(this.designinfoheadingbox);
    add(new GreyDescription("<B>If you would like to add a logo to your cap, please click on ADD LOGO below. If no decoration is needed, please proceed to Step 2.</B>"));
    
    initLogos();
    
    add(this.logopanel);
    
    PaddedBodyContainer logobuttoncontainer = new PaddedBodyContainer();
    
    logobuttoncontainer.add(this.button_addlogo);
    
    add(logobuttoncontainer);
    

    add(new BlackLineBar(1000));
    


    HorizontalPanel menubarrow = new HorizontalPanel();
    
    menubarrow.add(this.menu_previousitem);
    menubarrow.add(this.menu_nextitem);
    menubarrow.add(this.menu_deleteitem);
    menubarrow.add(this.menu_copyitem);
    menubarrow.add(this.menu_addnewitem);
    menubarrow.add(this.menu_gotostep2);
    
    add(menubarrow, new TableData(Style.HorizontalAlignment.CENTER, Style.VerticalAlignment.MIDDLE));
    
    add(new BlackLineBar(1000));
  }
  
  public void fixItemDisplay()
  {
    int currentitem = this.orderdata.getSetIndex(this.orderdataset);
    this.productinfoheadingbox.setHtml("ITEM <B>" + (currentitem + 1) + "</B> - PRODUCT <B>INFORMATION</B>");
    this.designinfoheadingbox.setHtml("ITEM <B>" + (currentitem + 1) + "</B> - DESIGN <B>INFORMATION</B>");
    this.menu_previousitem.setVisible(currentitem != 0);
    this.menu_nextitem.setVisible(currentitem + 1 != this.orderdata.getSetCount());
    this.blueitem.setHtml("<I><B>ITEM " + (currentitem + 1) + "</B></I>");
  }
  
  private ClickHandler clickhandler = new ClickHandler()
  {
    public void onClick(ClickEvent event)
    {
      if (event.getSource() == OnlineProductDesignTab.this.menu_previousitem) {
        OnlineProductDesignTab.this.instantquotemainscreen.previousItem();
      } else if (event.getSource() == OnlineProductDesignTab.this.menu_nextitem) {
        OnlineProductDesignTab.this.instantquotemainscreen.nextItem();
      } else if ((event.getSource() == OnlineProductDesignTab.this.menu_deleteitem) || (event.getSource() == OnlineProductDesignTab.this.button_removeitem)) {
        OnlineProductDesignTab.this.instantquotemainscreen.deleteSet(OnlineProductDesignTab.this.orderdataset);
      } else if (event.getSource() == OnlineProductDesignTab.this.menu_copyitem) {
        OnlineProductDesignTab.this.instantquotemainscreen.copySet(OnlineProductDesignTab.this.orderdataset);
      } else if (event.getSource() == OnlineProductDesignTab.this.menu_addnewitem) {
        OnlineProductDesignTab.this.instantquotemainscreen.addSet();
      } else if (event.getSource() == OnlineProductDesignTab.this.menu_gotostep2) {
        if (OnlineProductDesignTab.this.instantquotemainscreen.getOnlineType() == InstantQuoteMainScreen.OnlineType.VIRTUALSAMPLE) {
          OnlineProductDesignTab.this.instantquotemainscreen.doStep(3);
        } else {
          OnlineProductDesignTab.this.instantquotemainscreen.doStep(2);
        }
      } else if (event.getSource() == OnlineProductDesignTab.this.button_addlogo) {
        OnlineProductDesignTab.this.doAddLogo();
      }
    }
  };
  


  private void doToolTips()
  {
    this.visortooltip.setMouseOffset(new int[2]);
    this.visortooltip.setAnchor("left");
    this.visortooltip.setTemplate(new Template(getTemplate(NewWorkFlow.filepath, "visorexamples.jpg")));
    this.visortooltip.setMaxWidth(1000);
    
    this.sunvisortooltip.setMouseOffset(new int[2]);
    this.sunvisortooltip.setAnchor("left");
    this.sunvisortooltip.setTemplate(new Template(getTemplate(NewWorkFlow.filepath, "sunvisorclosureexamples.jpg")));
    this.sunvisortooltip.setMaxWidth(1000);
    
    if ((!this.orderdataitem.getStyleNumber().equals("")) && (this.instantquotemainscreen.getStyleStore(this.orderdataitem).getCategory().equals("Sun Visor"))) {
      this.itemportletfield.getVisorStyleNumberField().setToolTip(this.sunvisortooltip);
    } else {
      this.itemportletfield.getVisorStyleNumberField().setToolTip(this.visortooltip);
    }
    
    ToolTipConfig eyelettooltip = new ToolTipConfig();
    eyelettooltip.setMouseOffset(new int[2]);
    eyelettooltip.setAnchor("left");
    eyelettooltip.setTemplate(new Template(getTemplate(NewWorkFlow.filepath, "eyeletexamples.jpg")));
    eyelettooltip.setMaxWidth(1000);
    this.itemportletfield.getEyeletStyleNumberField().setToolTip(eyelettooltip);
    
    ToolTipConfig closuretooltip = new ToolTipConfig();
    closuretooltip.setMouseOffset(new int[2]);
    closuretooltip.setAnchor("left");
    closuretooltip.setTemplate(new Template(getTemplate(NewWorkFlow.filepath, "closureexamples.jpg")));
    closuretooltip.setMaxWidth(1000);
    this.itemportletfield.getClosureStyleNumberField().setToolTip(closuretooltip);
    
    ToolTipConfig sweatbandtooltip = new ToolTipConfig();
    sweatbandtooltip.setMouseOffset(new int[2]);
    sweatbandtooltip.setAnchor("left");
    sweatbandtooltip.setTemplate(new Template(getTemplate(NewWorkFlow.filepath, "sweatbandexamples.jpg")));
    sweatbandtooltip.setMaxWidth(1000);
    this.itemportletfield.getSweatbandStyleNumberField().setToolTip(sweatbandtooltip);
  }
  









  Listener<BaseEvent> stylechangedlistener = new Listener()
  {
    public void handleEvent(BaseEvent be)
    {
      if ((!OnlineProductDesignTab.this.orderdataitem.getStyleNumber().equals("")) && (OnlineProductDesignTab.this.instantquotemainscreen.getStyleStore(OnlineProductDesignTab.this.orderdataitem).getCategory().equals("Sun Visor"))) {
        OnlineProductDesignTab.this.itemportletfield.getVisorStyleNumberField().setToolTip(OnlineProductDesignTab.this.sunvisortooltip);
      } else {
        OnlineProductDesignTab.this.itemportletfield.getVisorStyleNumberField().setToolTip(OnlineProductDesignTab.this.visortooltip);
      }
    }
  };
  

  private Listener<BaseEvent> setlogolocationslistener = new Listener()
  {

    public void handleEvent(BaseEvent be)
    {
      FastMap<OtherComboBoxModelData> allitems = new FastMap();
      String blankcolor = "#ffffff";
      
      ListStore<OtherComboBoxModelData> currentstore = OnlineProductDesignTab.this.instantquotemainscreen.getStyleStore(OnlineProductDesignTab.this.orderdataitem).getLogoLocationStore();
      OtherComboBoxModelData thebox; for (int j = 0; j < currentstore.getCount(); j++) {
        OtherComboBoxModelData thecombobox = (OtherComboBoxModelData)currentstore.getAt(j);
        if (allitems.containsKey(thecombobox.getValue())) {
          OtherComboBoxModelData thebox = (OtherComboBoxModelData)allitems.get(thecombobox.getValue());
          thebox.set("matchingall", Integer.valueOf(((Integer)thebox.get("matchingall")).intValue() + 1));
          if (OnlineProductDesignTab.this.orderdata.getOrderType() == OrderData.OrderType.DOMESTIC) {
            if (((Boolean)thecombobox.get("heat")).booleanValue()) {
              thebox.set("heatcount", Integer.valueOf(((Integer)thebox.get("heatcount")).intValue() + 1));
            }
            if (((Boolean)thecombobox.get("emb")).booleanValue()) {
              thebox.set("embcount", Integer.valueOf(((Integer)thebox.get("embcount")).intValue() + 1));
            }
            if (((Boolean)thecombobox.get("dtg")).booleanValue()) {
              thebox.set("dtgcount", Integer.valueOf(((Integer)thebox.get("dtgcount")).intValue() + 1));
            }
            if (((Boolean)thecombobox.get("cadprint")).booleanValue()) {
              thebox.set("cadprintcount", Integer.valueOf(((Integer)thebox.get("cadprintcount")).intValue() + 1));
            }
            if (((Boolean)thecombobox.get("screenprint")).booleanValue()) {
              thebox.set("screenprintcount", Integer.valueOf(((Integer)thebox.get("screenprintcount")).intValue() + 1));
            }
          }
        } else {
          thebox = new OtherComboBoxModelData();
          thebox.set("name", thecombobox.getName());
          thebox.set("value", thecombobox.getValue());
          thebox.set("matchingall", Integer.valueOf(1));
          if (OnlineProductDesignTab.this.orderdata.getOrderType() == OrderData.OrderType.DOMESTIC) {
            thebox.set("heatcount", Integer.valueOf(((Boolean)thecombobox.get("heat")).booleanValue() ? 1 : 0));
            thebox.set("embcount", Integer.valueOf(((Boolean)thecombobox.get("emb")).booleanValue() ? 1 : 0));
            thebox.set("dtgcount", Integer.valueOf(((Boolean)thecombobox.get("dtg")).booleanValue() ? 1 : 0));
            thebox.set("cadprintcount", Integer.valueOf(((Boolean)thecombobox.get("cadprint")).booleanValue() ? 1 : 0));
            thebox.set("screenprintcount", Integer.valueOf(((Boolean)thecombobox.get("screenprint")).booleanValue() ? 1 : 0));
          }
          allitems.put(thecombobox.getValue(), thebox);
        }
      }
      
      ListStore<OtherComboBoxModelData> thestore = new ListStore();
      
      for (OtherComboBoxModelData currentitem : allitems.values()) {
        OtherComboBoxModelData themodeldata = new OtherComboBoxModelData();
        themodeldata.set("name", currentitem.getName());
        themodeldata.set("value", currentitem.getValue());
        themodeldata.set("matchingrest", blankcolor);
        if (OnlineProductDesignTab.this.orderdata.getOrderType() == OrderData.OrderType.DOMESTIC) {
          themodeldata.set("heatcount", currentitem.get("heatcount"));
          themodeldata.set("embcount", currentitem.get("embcount"));
          themodeldata.set("dtgcount", currentitem.get("dtgcount"));
          themodeldata.set("cadprintcount", currentitem.get("cadprintcount"));
          themodeldata.set("screenprintcount", currentitem.get("screenprintcount"));
        }
        thestore.add(themodeldata);
      }
      

      thestore.sort("name", com.extjs.gxt.ui.client.Style.SortDir.ASC);
      OnlineProductDesignTab.this.setfield.setLogoLocations(thestore);
      
      for (OnlineDomesticLogoLayout logo : OnlineProductDesignTab.this.domesticlogolist) {
        logo.updateLocations();
      }
      for (OnlineOverseasLogoLayout logo : OnlineProductDesignTab.this.overseaslogolist) {
        logo.updateLocations();
      }
    }
  };
  
  private native String getTemplate(String paramString1, String paramString2);
  
  private void doAddLogo() {
    OrderDataLogo newlogo = this.orderdataset.addLogo();
    
    if (this.orderdata.getOrderType() == OrderData.OrderType.DOMESTIC) {
      OnlineDomesticLogoLayout domesticlogolayout = new OnlineDomesticLogoLayout(this.orderdata, newlogo, this.setfield, this);
      this.domesticlogolist.add(domesticlogolayout);
      this.logopanel.add(domesticlogolayout);
      domesticlogolayout.fixlogodisplay(this.domesticlogolist.size() - 1);
    } else {
      OnlineOverseasLogoLayout overseaslogolayout = new OnlineOverseasLogoLayout(this.orderdata, newlogo, this.setfield, this);
      this.overseaslogolist.add(overseaslogolayout);
      this.logopanel.add(overseaslogolayout);
      overseaslogolayout.fixlogodisplay(this.overseaslogolist.size() - 1);
    }
    
    this.logopanel.layout(true);
  }
  
  public void removeLogo(OnlineDomesticLogoLayout onlinedomesticlogolayout) {
    this.domesticlogolist.remove(onlinedomesticlogolayout);
    this.orderdataset.removeLogo(onlinedomesticlogolayout.getOrderDataLogo());
    this.logopanel.remove(onlinedomesticlogolayout);
    for (int i = 0; i < this.domesticlogolist.size(); i++) {
      ((OnlineDomesticLogoLayout)this.domesticlogolist.get(i)).fixlogodisplay(i);
    }
    if (this.orderdataset.getLogoCount() == 0) {
      doAddLogo();
    }
  }
  
  public OrderDataItem getOrderDataItem() {
    return this.orderdataitem;
  }
  
  public InstantQuoteMainScreen getInstantQuoteMainScreen() {
    return this.instantquotemainscreen;
  }
  
  private void initLogos() {
    for (int i = 0; i < this.orderdataset.getLogoCount(); i++) {
      OrderDataLogo newlogo = this.orderdataset.getLogo(i);
      
      if (this.orderdata.getOrderType() == OrderData.OrderType.DOMESTIC) {
        OnlineDomesticLogoLayout domesticlogolayout = new OnlineDomesticLogoLayout(this.orderdata, newlogo, this.setfield, this);
        this.domesticlogolist.add(domesticlogolayout);
        this.logopanel.add(domesticlogolayout);
        domesticlogolayout.fixlogodisplay(this.domesticlogolist.size() - 1);
      } else {
        OnlineOverseasLogoLayout overseaslogolayout = new OnlineOverseasLogoLayout(this.orderdata, newlogo, this.setfield, this);
        this.overseaslogolist.add(overseaslogolayout);
        this.logopanel.add(overseaslogolayout);
        overseaslogolayout.fixlogodisplay(this.overseaslogolist.size() - 1);
      }
    }
    
    if ((this.orderdataset.getLogoCount() == 0) && (this.orderdata.getOrderType() == OrderData.OrderType.DOMESTIC)) {
      doAddLogo();
    }
    
    this.setlogolocationslistener.handleEvent(null);
  }
  
  public void removeLogo(OnlineOverseasLogoLayout onlineoverseaslogolayout)
  {
    this.overseaslogolist.remove(onlineoverseaslogolayout);
    this.orderdataset.removeLogo(onlineoverseaslogolayout.getOrderDataLogo());
    this.logopanel.remove(onlineoverseaslogolayout);
    for (int i = 0; i < this.overseaslogolist.size(); i++) {
      ((OnlineOverseasLogoLayout)this.overseaslogolist.get(i)).fixlogodisplay(i);
    }
  }
  
  private Listener<BaseEvent> addlogolistener = new Listener()
  {

    public void handleEvent(BaseEvent be)
    {
      OrderDataLogo newlogo = OnlineProductDesignTab.this.orderdataset.addLogo((OrderDataLogo)be.getSource());
      OnlineOverseasLogoLayout overseaslogolayout = new OnlineOverseasLogoLayout(OnlineProductDesignTab.this.orderdata, newlogo, OnlineProductDesignTab.this.setfield, OnlineProductDesignTab.this);
      OnlineProductDesignTab.this.overseaslogolist.add(overseaslogolayout);
      OnlineProductDesignTab.this.logopanel.add(overseaslogolayout);
      overseaslogolayout.fixlogodisplay(OnlineProductDesignTab.this.overseaslogolist.size() - 1);
      OnlineProductDesignTab.this.logopanel.layout(true);
    }
  };
}
