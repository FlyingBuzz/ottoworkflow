package com.ottocap.NewWorkFlow.client.OnlineWorkflow;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.VerticalAlignment;
import com.extjs.gxt.ui.client.core.Template;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.Html;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.TableData;
import com.extjs.gxt.ui.client.widget.tips.ToolTipConfig;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.PushButton;
import com.ottocap.NewWorkFlow.client.DataHolder.ProductsInfo.Decoration.DecorationInfo;
import com.ottocap.NewWorkFlow.client.DataHolder.ProductsInfo.OverseasLogoField;
import com.ottocap.NewWorkFlow.client.DataHolder.ProductsInfo.SetField;
import com.ottocap.NewWorkFlow.client.DataHolder.Stores.DataStores;
import com.ottocap.NewWorkFlow.client.DataHolder.Stores.NewStyleStore;
import com.ottocap.NewWorkFlow.client.NewWorkFlow;
import com.ottocap.NewWorkFlow.client.OnlineWorkflow.Widget.GreyDescription;
import com.ottocap.NewWorkFlow.client.OnlineWorkflow.Widget.PaddedBodyContainer;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataItem;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogo;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogoDecoration;
import com.ottocap.NewWorkFlow.client.Widget.BuzzEvents;
import com.ottocap.NewWorkFlow.client.Widget.FormHorizontalPanel2;

public class OnlineOverseasLogoLayout extends com.extjs.gxt.ui.client.widget.VerticalPanel
{
  private LayoutContainer decorationoptionspanel = new LayoutContainer();
  
  private OrderDataLogo orderdatalogo;
  private OnlineColorwayPanel colorwaypanel;
  private OverseasLogoField overseaslogofield;
  private Html redlogo = new Html();
  private PushButton button_removelogo = new PushButton();
  private PushButton adddecoration = new PushButton();
  private OnlineProductDesignTab onlineproductdesigntab;
  private GreyDescription adddecorationdescription = new GreyDescription("If you would like to add another decoration application to your logo click ADD DECORATION below.");
  private ToolTipConfig logolocationtip = new ToolTipConfig();
  
  public OnlineOverseasLogoLayout(OrderData orderdata, OrderDataLogo orderdatalogo, SetField setfield, OnlineProductDesignTab onlineproductdesigntab) {
    this.onlineproductdesigntab = onlineproductdesigntab;
    this.orderdatalogo = orderdatalogo;
    setWidth(1000);
    
    this.overseaslogofield = new OverseasLogoField(orderdata, orderdatalogo, setfield);
    this.overseaslogofield.addListener(BuzzEvents.SetDSTInfo, this.setdstinfolistener);
    this.overseaslogofield.addListener(BuzzEvents.UpdateColorways, this.updatecolorwayslistener);
    this.overseaslogofield.addListener(BuzzEvents.FixDecorations, this.fixdecorationslistener);
    
    this.overseaslogofield.getLogoLocationField().setForceSelection(true);
    
    this.button_removelogo.setStylePrimaryName("pushdeletelogo-PushButton");
    this.adddecoration.setStylePrimaryName("pushadddecoration-PushButton");
    this.button_removelogo.addClickHandler(this.clickhandler);
    this.adddecoration.addClickHandler(this.clickhandler);
    
    this.redlogo.setStyleAttribute("color", "#ff0000");
    
    ToolTipConfig sitchcounttooltip = new ToolTipConfig();
    sitchcounttooltip.setMouseOffset(new int[] { 65076, -5 });
    sitchcounttooltip.setAnchor("bottom");
    sitchcounttooltip.setAnchorOffset(600);
    sitchcounttooltip.setTemplate(new Template(getTemplate(NewWorkFlow.filepath, "stitchexamples.jpg")));
    sitchcounttooltip.setMaxWidth(1000);
    this.overseaslogofield.getTotalStitchCountField().setToolTip(sitchcounttooltip);
    
    this.logolocationtip.setMouseOffset(new int[] { 0, -5 });
    this.logolocationtip.setAnchor("bottom");
    this.logolocationtip.setMaxWidth(1000);
    fixLocationToolTip();
    this.overseaslogofield.getLogoLocationField().setToolTip(this.logolocationtip);
    
    add(new GreyDescription("Please upload the artwork for your logo. Acceptable formats include JPGs and DST files. If DST files are uploaded, the exact stitch count & logo size will be automatically entered, otherwise please follow the stitch count samples below to estimate the logo stitch count as close as possible."));
    
    PaddedBodyContainer thelogofields = new PaddedBodyContainer();
    
    FormHorizontalPanel2 itemtextandremoverow = new FormHorizontalPanel2();
    itemtextandremoverow.add(this.redlogo);
    itemtextandremoverow.add(this.button_removelogo);
    thelogofields.add(itemtextandremoverow);
    
    FormHorizontalPanel2 logolocationpanel = new FormHorizontalPanel2();
    logolocationpanel.add(this.overseaslogofield.getLogoLocationField(), new FormData(418, -1));
    thelogofields.add(logolocationpanel);
    
    thelogofields.add(this.overseaslogofield.getLogoUploadFormField());
    
    FormHorizontalPanel2 hor2 = new FormHorizontalPanel2();
    hor2.add(this.overseaslogofield.getLogoSizeWidthField());
    hor2.add(this.overseaslogofield.getLogoSizeHeightField());
    hor2.add(this.overseaslogofield.getNumberOfColorField());
    hor2.add(this.overseaslogofield.getTotalStitchCountField());
    
    thelogofields.add(hor2);
    
    for (OrderDataLogoDecoration currentdecoration : orderdatalogo.getDecorations()) {
      this.decorationoptionspanel.add(new OnlineDecorationPanel(orderdatalogo, currentdecoration, this));
    }
    
    FieldSet decorationoptionsfields = new FieldSet();
    decorationoptionsfields.setHeadingHtml("Decoration Options");
    decorationoptionsfields.add(this.decorationoptionspanel);
    
    thelogofields.add(decorationoptionsfields);
    
    add(thelogofields);
    
    add(this.adddecorationdescription);
    
    PaddedBodyContainer adddecorationpadded = new PaddedBodyContainer();
    adddecorationpadded.add(this.adddecoration);
    add(adddecorationpadded);
    
    this.colorwaypanel = new OnlineColorwayPanel(orderdatalogo, this.colorwaycallback);
    add(this.colorwaypanel);
    
    PaddedBodyContainer thelogofields2 = new PaddedBodyContainer();
    FormHorizontalPanel2 swatchform = new FormHorizontalPanel2();
    swatchform.add(new Html("<B>If a pre-production sample is required, please select a proof method by:</B>"));
    swatchform.add(this.overseaslogofield.getEmailProofField(), new FormData(91, 0), true);
    swatchform.add(this.overseaslogofield.getShipProofField(), new FormData(91, 0), true);
    thelogofields2.add(swatchform);
    add(thelogofields2);
    
    TableData tabledata = new TableData(Style.HorizontalAlignment.CENTER, Style.VerticalAlignment.TOP);
    TableData tabledata2 = new TableData(Style.HorizontalAlignment.CENTER, Style.VerticalAlignment.TOP);
    
    add(this.overseaslogofield.getDSTImageField(), tabledata);
    add(this.overseaslogofield.getLogoImageField(), tabledata2);
  }
  









  private com.google.gwt.user.client.rpc.AsyncCallback<Boolean> colorwaycallback = new com.google.gwt.user.client.rpc.AsyncCallback()
  {
    public void onFailure(Throwable caught) {}
    



    public void onSuccess(Boolean result)
    {
      if ((OnlineOverseasLogoLayout.this.orderdatalogo.getDstFilename() != null) && (OnlineOverseasLogoLayout.this.orderdatalogo.getDstFilename().toLowerCase().endsWith(".dst"))) {
        OnlineOverseasLogoLayout.this.overseaslogofield.setDSTImageURL();
      }
      OnlineOverseasLogoLayout.this.overseaslogofield.calcNumberOfColors();
    }
  };
  
  private native String getTemplate(String paramString1, String paramString2);
  
  public PushButton getAddDecorationButton() {
    return this.adddecoration;
  }
  
  public GreyDescription getAddDecorationDescription() {
    return this.adddecorationdescription;
  }
  
  public void addDecoration() {
    OrderDataLogoDecoration mydecoration = this.orderdatalogo.addDecoration();
    OnlineDecorationPanel mypanel = new OnlineDecorationPanel(this.orderdatalogo, mydecoration, this);
    this.decorationoptionspanel.add(mypanel);
    this.decorationoptionspanel.layout();
  }
  
  public void removeDecoration(OnlineDecorationPanel decorationpanel) {
    this.decorationoptionspanel.remove(decorationpanel);
    this.orderdatalogo.removeDecoration(decorationpanel.getDecoration());
    if (this.orderdatalogo.getDecorationCount().intValue() == 0) {
      addDecoration();
    }
  }
  
  private Listener<BaseEvent> setdstinfolistener = new Listener()
  {
    public void handleEvent(BaseEvent be)
    {
      OnlineOverseasLogoLayout.this.colorwaypanel.setDSTInfo((com.ottocap.NewWorkFlow.client.DataHolder.ProductsInfo.DSTInfoContainer)be.getSource());
      OnlineOverseasLogoLayout.this.colorwaypanel.setVisible(true);
    }
  };
  

  private Listener<BaseEvent> updatecolorwayslistener = new Listener()
  {
    public void handleEvent(BaseEvent be)
    {
      OnlineOverseasLogoLayout.this.colorwaypanel.updateColorways();
    }
  };
  

  private Listener<BaseEvent> fixdecorationslistener = new Listener()
  {

    public void handleEvent(BaseEvent be)
    {
      if ((NewWorkFlow.get().getDataStores().getDecorationinfo().getLocation(OnlineOverseasLogoLayout.this.orderdatalogo.getLogoLocation()) != null) && (!NewWorkFlow.get().getDataStores().getDecorationinfo().getLocation(OnlineOverseasLogoLayout.this.orderdatalogo.getLogoLocation()).getCanAdd())) {
        OnlineOverseasLogoLayout.this.decorationoptionspanel.removeAll();
        while (OnlineOverseasLogoLayout.this.orderdatalogo.getDecorationCount().intValue() > 0) {
          OnlineOverseasLogoLayout.this.orderdatalogo.removeDecoration(0);
        }
        OnlineOverseasLogoLayout.this.addDecoration();
      } else {
        for (com.extjs.gxt.ui.client.widget.Component theitem : OnlineOverseasLogoLayout.this.decorationoptionspanel.getItems()) {
          if ((theitem instanceof OnlineDecorationPanel)) {
            ((OnlineDecorationPanel)theitem).setDecorationStore();
          }
        }
      }
    }
  };
  

  public void updateLocations()
  {
    this.overseaslogofield.updateLocations();
    
    fixLocationToolTip();
  }
  
  public void fixLocationToolTip() {
    if ((this.onlineproductdesigntab.getOrderDataItem().getStyleNumber() == null) || (this.onlineproductdesigntab.getOrderDataItem().getStyleNumber().equals(""))) {
      this.logolocationtip.setTemplate(new Template(getTemplate(NewWorkFlow.filepath, "popup_loc_OS_cap.jpg")));
    } else {
      NewStyleStore thestylestore = this.onlineproductdesigntab.getInstantQuoteMainScreen().getStyleStore(this.onlineproductdesigntab.getOrderDataItem());
      if ((thestylestore != null) && (thestylestore.getCategory() != null)) {
        if (thestylestore.getCategory().equals("Knit Caps")) {
          this.logolocationtip.setTemplate(new Template(getTemplate(NewWorkFlow.filepath, "popup_loc_OS_beanie.jpg")));
        } else if (thestylestore.getCategory().equals("Military Styles")) {
          this.logolocationtip.setTemplate(new Template(getTemplate(NewWorkFlow.filepath, "popup_loc_OS_military.jpg")));
        } else if (thestylestore.getCategory().equals("Sport Shirt Styles")) {
          this.logolocationtip.setTemplate(new Template(getTemplate(NewWorkFlow.filepath, "popup_loc_OS_sportshirt.jpg")));
        } else if (thestylestore.getCategory().equals("Sun Visor")) {
          this.logolocationtip.setTemplate(new Template(getTemplate(NewWorkFlow.filepath, "popup_loc_OS_sunvisor.jpg")));
        } else if (thestylestore.getCategory().equals("T-Shirt")) {
          this.logolocationtip.setTemplate(new Template(getTemplate(NewWorkFlow.filepath, "popup_loc_OS_tshirt.jpg")));
        } else {
          this.logolocationtip.setTemplate(new Template(getTemplate(NewWorkFlow.filepath, "popup_loc_DOM_panel.jpg")));
        }
      } else {
        this.logolocationtip.setTemplate(new Template(getTemplate(NewWorkFlow.filepath, "popup_loc_OS_cap.jpg")));
      }
    }
  }
  
  public void fixlogodisplay(int currentlogo) {
    this.redlogo.setHtml("<I><B>LOGO " + (currentlogo + 1) + "</B></I>");
  }
  

  private ClickHandler clickhandler = new ClickHandler()
  {
    public void onClick(ClickEvent event)
    {
      if (event.getSource() == OnlineOverseasLogoLayout.this.button_removelogo) {
        OnlineOverseasLogoLayout.this.onlineproductdesigntab.removeLogo(OnlineOverseasLogoLayout.this);
      } else if (event.getSource() == OnlineOverseasLogoLayout.this.adddecoration) {
        OnlineOverseasLogoLayout.this.addDecoration();
      }
    }
  };
  
  public OrderDataLogo getOrderDataLogo()
  {
    return this.orderdatalogo;
  }
}
