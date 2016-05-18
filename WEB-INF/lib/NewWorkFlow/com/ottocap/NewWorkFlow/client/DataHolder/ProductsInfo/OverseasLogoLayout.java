package com.ottocap.NewWorkFlow.client.DataHolder.ProductsInfo;

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.ottocap.NewWorkFlow.client.DataHolder.ProductsInfo.Decoration.DecorationInfo;
import com.ottocap.NewWorkFlow.client.DataHolder.ProductsInfo.Decoration.DecorationPanel;
import com.ottocap.NewWorkFlow.client.DataHolder.Stores.DataStores;
import com.ottocap.NewWorkFlow.client.NewWorkFlow;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogo;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogoDecoration;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataSet;
import com.ottocap.NewWorkFlow.client.Widget.BuzzEvents;
import com.ottocap.NewWorkFlow.client.Widget.FormHorizontalPanel2;

public class OverseasLogoLayout extends LayoutContainer
{
  private Button adddecoration = new Button("Add Decoration", com.ottocap.NewWorkFlow.client.Icons.Resources.ICONS.paint_tube());
  private LayoutContainer decorationoptionspanel = new LayoutContainer();
  private OrderDataLogo orderdatalogo;
  private ColorwayPanel colorwaypanel;
  private OverseasLogoField overseaslogofield;
  private SetPortlet setportlet;
  
  public OverseasLogoLayout(OrderData orderdata, OrderDataSet orderdataset, OrderDataLogo orderdatalogo, SetPortlet setportlet)
  {
    this.setportlet = setportlet;
    this.orderdatalogo = orderdatalogo;
    
    this.overseaslogofield = new OverseasLogoField(orderdata, orderdatalogo, setportlet.getSetField());
    this.overseaslogofield.addListener(BuzzEvents.SetDSTInfo, this.setdstinfolistener);
    this.overseaslogofield.addListener(BuzzEvents.UpdateColorways, this.updatecolorwayslistener);
    this.overseaslogofield.addListener(BuzzEvents.TabLabelChange, this.tablabelchangelistener);
    this.overseaslogofield.addListener(BuzzEvents.FixDecorations, this.fixdecorationslistener);
    
    this.adddecoration.addSelectionListener(this.selectionlistener);
    
    FormHorizontalPanel2 logolocationpanel = new FormHorizontalPanel2();
    logolocationpanel.add(this.overseaslogofield.getLogoLocationField(), new FormData(418, -1));
    add(logolocationpanel);
    
    add(this.overseaslogofield.getLogoUploadFormField());
    
    FormHorizontalPanel2 hor2 = new FormHorizontalPanel2();
    hor2.add(this.overseaslogofield.getLogoSizeWidthField());
    hor2.add(this.overseaslogofield.getLogoSizeHeightField());
    hor2.add(this.overseaslogofield.getNumberOfColorField());
    hor2.add(this.overseaslogofield.getTotalStitchCountField());
    hor2.add(this.overseaslogofield.getNameDropLogoField());
    add(hor2);
    
    for (OrderDataLogoDecoration currentdecoration : orderdatalogo.getDecorations()) {
      this.decorationoptionspanel.add(new DecorationPanel(orderdatalogo, currentdecoration, this));
    }
    
    FieldSet decorationoptionsfields = new FieldSet();
    decorationoptionsfields.setHeadingHtml("Decoration Options");
    decorationoptionsfields.add(this.decorationoptionspanel);
    decorationoptionsfields.add(this.adddecoration);
    
    add(decorationoptionsfields);
    
    this.colorwaypanel = new ColorwayPanel(orderdatalogo, this.colorwaycallback);
    add(this.colorwaypanel);
    
    add(this.overseaslogofield.getDSTImageField());
    add(this.overseaslogofield.getLogoImageField());
    
    FieldSet extraoptions = new FieldSet();
    extraoptions.setCollapsible(true);
    extraoptions.setHeadingHtml("Extra Options");
    extraoptions.collapse();
    
    FormHorizontalPanel2 logodescriptionpanel = new FormHorizontalPanel2();
    logodescriptionpanel.add(this.overseaslogofield.getLogoDescriptionField(), new FormData(1072, -1));
    extraoptions.add(logodescriptionpanel);
    
    FieldSet artworkrequestgroup = new FieldSet();
    artworkrequestgroup.setHeadingHtml("Artwork Request");
    FormHorizontalPanel2 artworkrequestform = new FormHorizontalPanel2();
    artworkrequestform.add(this.overseaslogofield.getArtworkTypeField());
    artworkrequestform.add(this.overseaslogofield.getArtworkChargeField());
    artworkrequestform.add(this.overseaslogofield.getArtworkTypeCommentsField());
    artworkrequestgroup.add(artworkrequestform);
    FieldSet swatchgroup = new FieldSet();
    swatchgroup.setHeadingHtml("Swatch");
    FormHorizontalPanel2 swatchform = new FormHorizontalPanel2();
    swatchform.addCheckBox(this.overseaslogofield.getEmailProofField(), new FormData(91, 0));
    swatchform.addCheckBox(this.overseaslogofield.getShipProofField(), new FormData(91, 0));
    swatchform.add(this.overseaslogofield.getProofsToDoField());
    swatchform.add(this.overseaslogofield.getTotalEmailProofsField());
    swatchform.add(this.overseaslogofield.getTotalShipProofsField());
    swatchgroup.add(swatchform);
    extraoptions.add(artworkrequestgroup);
    extraoptions.add(swatchgroup);
    add(extraoptions);
  }
  

  private AsyncCallback<Boolean> colorwaycallback = new AsyncCallback()
  {
    public void onFailure(Throwable caught) {}
    



    public void onSuccess(Boolean result)
    {
      if ((OverseasLogoLayout.this.orderdatalogo.getDstFilename() != null) && (OverseasLogoLayout.this.orderdatalogo.getDstFilename().toLowerCase().endsWith(".dst"))) {
        OverseasLogoLayout.this.overseaslogofield.setDSTImageURL();
      }
      OverseasLogoLayout.this.overseaslogofield.calcNumberOfColors();
    }
  };
  

  public Button getAddDecorationButton()
  {
    return this.adddecoration;
  }
  
  public void addDecoration() {
    OrderDataLogoDecoration mydecoration = this.orderdatalogo.addDecoration();
    DecorationPanel mypanel = new DecorationPanel(this.orderdatalogo, mydecoration, this);
    this.decorationoptionspanel.add(mypanel);
    this.decorationoptionspanel.layout();
  }
  
  public void removeDecoration(DecorationPanel decorationpanel) {
    this.decorationoptionspanel.remove(decorationpanel);
    this.orderdatalogo.removeDecoration(decorationpanel.getDecoration());
    if (this.orderdatalogo.getDecorationCount().intValue() == 0) {
      addDecoration();
    }
  }
  
  private SelectionListener<ButtonEvent> selectionlistener = new SelectionListener()
  {
    public void componentSelected(ButtonEvent ce)
    {
      if (ce.getSource().equals(OverseasLogoLayout.this.adddecoration)) {
        OverseasLogoLayout.this.addDecoration();
      }
    }
  };
  

  private Listener<BaseEvent> setdstinfolistener = new Listener()
  {
    public void handleEvent(BaseEvent be)
    {
      OverseasLogoLayout.this.colorwaypanel.setDSTInfo((DSTInfoContainer)be.getSource());
      OverseasLogoLayout.this.colorwaypanel.setVisible(true);
    }
  };
  

  private Listener<BaseEvent> updatecolorwayslistener = new Listener()
  {
    public void handleEvent(BaseEvent be)
    {
      OverseasLogoLayout.this.colorwaypanel.updateColorways();
    }
  };
  

  private Listener<BaseEvent> tablabelchangelistener = new Listener()
  {

    public void handleEvent(BaseEvent be)
    {
      OverseasLogoLayout.this.setportlet.getProductsPortal().getOrderTab().setTabHeader();
    }
  };
  


  private Listener<BaseEvent> fixdecorationslistener = new Listener()
  {

    public void handleEvent(BaseEvent be)
    {
      if ((NewWorkFlow.get().getDataStores().getDecorationinfo().getLocation(OverseasLogoLayout.this.orderdatalogo.getLogoLocation()) != null) && (!NewWorkFlow.get().getDataStores().getDecorationinfo().getLocation(OverseasLogoLayout.this.orderdatalogo.getLogoLocation()).getCanAdd())) {
        OverseasLogoLayout.this.decorationoptionspanel.removeAll();
        while (OverseasLogoLayout.this.orderdatalogo.getDecorationCount().intValue() > 0) {
          OverseasLogoLayout.this.orderdatalogo.removeDecoration(0);
        }
        OverseasLogoLayout.this.addDecoration();
      } else {
        for (com.extjs.gxt.ui.client.widget.Component theitem : OverseasLogoLayout.this.decorationoptionspanel.getItems()) {
          if ((theitem instanceof DecorationPanel)) {
            ((DecorationPanel)theitem).setDecorationStore();
          }
        }
      }
    }
  };
  

  public void updateLocations()
  {
    this.overseaslogofield.updateLocations();
  }
}
