package com.ottocap.NewWorkFlow.client.OnlineWorkflow;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.VerticalAlignment;
import com.extjs.gxt.ui.client.core.Template;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.Html;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.TableData;
import com.extjs.gxt.ui.client.widget.tips.ToolTipConfig;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.PushButton;
import com.ottocap.NewWorkFlow.client.DataHolder.ProductsInfo.DomesticLogoField;
import com.ottocap.NewWorkFlow.client.DataHolder.ProductsInfo.SetField;
import com.ottocap.NewWorkFlow.client.DataHolder.Stores.NewStyleStore;
import com.ottocap.NewWorkFlow.client.NewWorkFlow;
import com.ottocap.NewWorkFlow.client.OnlineWorkflow.Widget.PaddedBodyContainer;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataItem;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogo;
import com.ottocap.NewWorkFlow.client.Widget.BuzzEvents;
import com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBox;
import com.ottocap.NewWorkFlow.client.Widget.FormHorizontalPanel2;

public class OnlineDomesticLogoLayout extends com.extjs.gxt.ui.client.widget.VerticalPanel
{
  private DomesticLogoField domesticlogofield;
  private FormHorizontalPanel2 hor2 = new FormHorizontalPanel2();
  
  private OrderDataLogo orderdatalogo;
  private OnlineColorwayPanel colorwaypanel;
  private Html redlogo = new Html();
  private PushButton button_removelogo = new PushButton();
  private OnlineProductDesignTab onlineproductdesigntab;
  private ToolTipConfig logolocationtip = new ToolTipConfig();
  
  public OnlineDomesticLogoLayout(OrderData orderdata, OrderDataLogo orderdatalogo, SetField setfield, OnlineProductDesignTab onlineproductdesigntab) {
    this.onlineproductdesigntab = onlineproductdesigntab;
    this.orderdatalogo = orderdatalogo;
    setWidth(1000);
    
    this.domesticlogofield = new DomesticLogoField(orderdata, orderdatalogo, setfield);
    this.domesticlogofield.getLogoLocationField().setForceSelection(true);
    this.domesticlogofield.getDecorationListField().setForceSelection(true);
    this.domesticlogofield.addListener(BuzzEvents.SetDSTInfo, this.setdstinfolistener);
    this.domesticlogofield.addListener(BuzzEvents.UpdateColorways, this.updatecolorwayslistener);
    
    this.button_removelogo.setStylePrimaryName("pushdeletelogo-PushButton");
    this.button_removelogo.addClickHandler(this.clickhandler);
    
    this.redlogo.setStyleAttribute("color", "#ff0000");
    
    ToolTipConfig sitchcounttooltip = new ToolTipConfig();
    sitchcounttooltip.setMouseOffset(new int[] { 65076, -5 });
    sitchcounttooltip.setAnchor("bottom");
    sitchcounttooltip.setAnchorOffset(600);
    sitchcounttooltip.setTemplate(new Template(getTemplate(NewWorkFlow.filepath, "stitchexamples.jpg")));
    sitchcounttooltip.setMaxWidth(1000);
    this.domesticlogofield.getTotalStitchCountField().setToolTip(sitchcounttooltip);
    
    this.logolocationtip.setMouseOffset(new int[] { 0, -5 });
    this.logolocationtip.setAnchor("bottom");
    this.logolocationtip.setMaxWidth(1000);
    fixLocationToolTip();
    this.domesticlogofield.getLogoLocationField().setToolTip(this.logolocationtip);
    
    add(new com.ottocap.NewWorkFlow.client.OnlineWorkflow.Widget.GreyDescription("Please upload the artwork for your logo. Acceptable formats include JPGs and DST files. If DST files are uploaded, the exact stitch count & logo size will be automatically entered, otherwise please follow the stitch count samples below to estimate the logo stitch count as close as possible."));
    
    PaddedBodyContainer thelogofields = new PaddedBodyContainer();
    
    FormHorizontalPanel2 itemtextandremoverow = new FormHorizontalPanel2();
    itemtextandremoverow.add(this.redlogo);
    itemtextandremoverow.add(this.button_removelogo);
    thelogofields.add(itemtextandremoverow);
    
    thelogofields.add(this.domesticlogofield.getLogoUploadFormField());
    
    FormHorizontalPanel2 hor4 = new FormHorizontalPanel2();
    hor4.add(this.domesticlogofield.getDecorationListField());
    thelogofields.add(hor4);
    
    this.hor2.add(this.domesticlogofield.getLogoSizeWidthField());
    this.hor2.add(this.domesticlogofield.getLogoSizeHeightField());
    this.hor2.add(this.domesticlogofield.getNumberOfColorField());
    this.hor2.add(this.domesticlogofield.getTotalStitchCountField());
    if (NewWorkFlow.get().isFaya.booleanValue()) {
      this.hor2.add(this.domesticlogofield.getLogoSizeChoiceField());
    }
    thelogofields.add(this.hor2);
    
    this.colorwaypanel = new OnlineColorwayPanel(orderdatalogo, this.colorwaycallback);
    
    PaddedBodyContainer thelogofields2 = new PaddedBodyContainer();
    
    FormHorizontalPanel2 swatchform = new FormHorizontalPanel2();
    swatchform.add(new Html("<B>If a pre-production sample is required, please select a proof method by:</B>"));
    swatchform.add(this.domesticlogofield.getEmailProofField(), new FormData(91, 0), true);
    swatchform.add(this.domesticlogofield.getShipProofField(), new FormData(91, 0), true);
    thelogofields2.add(swatchform);
    
    TableData tabledata = new TableData(Style.HorizontalAlignment.CENTER, Style.VerticalAlignment.TOP);
    TableData tabledata2 = new TableData(Style.HorizontalAlignment.CENTER, Style.VerticalAlignment.TOP);
    
    add(thelogofields);
    add(this.colorwaypanel);
    add(thelogofields2);
    add(this.domesticlogofield.getDSTImageField(), tabledata);
    add(this.domesticlogofield.getLogoImageField(), tabledata2);
  }
  








  private AsyncCallback<Boolean> colorwaycallback = new AsyncCallback()
  {
    public void onFailure(Throwable caught) {}
    



    public void onSuccess(Boolean result)
    {
      if ((OnlineDomesticLogoLayout.this.orderdatalogo.getFilename() != null) && (OnlineDomesticLogoLayout.this.orderdatalogo.getFilename().toLowerCase().endsWith(".dst"))) {
        OnlineDomesticLogoLayout.this.domesticlogofield.setDSTImageURL();
      }
      OnlineDomesticLogoLayout.this.domesticlogofield.calcNumberOfColors();
    }
  };
  


  private Listener<BaseEvent> setdstinfolistener = new Listener()
  {
    public void handleEvent(BaseEvent be)
    {
      OnlineDomesticLogoLayout.this.colorwaypanel.setDSTInfo((com.ottocap.NewWorkFlow.client.DataHolder.ProductsInfo.DSTInfoContainer)be.getSource());
      OnlineDomesticLogoLayout.this.colorwaypanel.setVisible(true);
    }
  };
  

  private Listener<BaseEvent> updatecolorwayslistener = new Listener()
  {

    public void handleEvent(BaseEvent be) {
      OnlineDomesticLogoLayout.this.colorwaypanel.updateColorways(); }
  };
  
  private native String getTemplate(String paramString1, String paramString2);
  
  public void updateLocations() {
    this.domesticlogofield.updateLocations();
    
    fixLocationToolTip();
  }
  
  public void fixLocationToolTip() {
    if ((this.onlineproductdesigntab.getOrderDataItem().getStyleNumber() == null) || (this.onlineproductdesigntab.getOrderDataItem().getStyleNumber().equals(""))) {
      this.logolocationtip.setTemplate(new Template(getTemplate(NewWorkFlow.filepath, "popup_loc_DOM_panel.jpg")));
    } else {
      NewStyleStore thestylestore = this.onlineproductdesigntab.getInstantQuoteMainScreen().getStyleStore(this.onlineproductdesigntab.getOrderDataItem());
      if ((thestylestore != null) && (thestylestore.getCategory() != null)) {
        if (thestylestore.getCategory().equals("Knit Caps")) {
          this.logolocationtip.setTemplate(new Template(getTemplate(NewWorkFlow.filepath, "popup_loc_DOM_beanie.jpg")));
        } else if (thestylestore.getCategory().equals("Military Styles")) {
          this.logolocationtip.setTemplate(new Template(getTemplate(NewWorkFlow.filepath, "popup_loc_DOM_military.jpg")));
        } else if (thestylestore.getCategory().equals("Sport Shirt Styles")) {
          this.logolocationtip.setTemplate(new Template(getTemplate(NewWorkFlow.filepath, "popup_loc_DOM_sportshirt.jpg")));
        } else if (thestylestore.getCategory().equals("Sun Visor")) {
          this.logolocationtip.setTemplate(new Template(getTemplate(NewWorkFlow.filepath, "popup_loc_DOM_sunvisor.jpg")));
        } else if (thestylestore.getCategory().equals("T-Shirt")) {
          this.logolocationtip.setTemplate(new Template(getTemplate(NewWorkFlow.filepath, "popup_loc_DOM_tshirt.jpg")));
        } else {
          this.logolocationtip.setTemplate(new Template(getTemplate(NewWorkFlow.filepath, "popup_loc_DOM_panel.jpg")));
        }
      } else {
        this.logolocationtip.setTemplate(new Template(getTemplate(NewWorkFlow.filepath, "popup_loc_DOM_panel.jpg")));
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
      if (event.getSource() == OnlineDomesticLogoLayout.this.button_removelogo) {
        OnlineDomesticLogoLayout.this.onlineproductdesigntab.removeLogo(OnlineDomesticLogoLayout.this);
      }
    }
  };
  
  public OrderDataLogo getOrderDataLogo()
  {
    return this.orderdatalogo;
  }
}
