package com.ottocap.NewWorkFlow.client.DataHolder.ProductsInfo;

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.ottocap.NewWorkFlow.client.NewWorkFlow;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogo;
import com.ottocap.NewWorkFlow.client.Widget.BuzzEvents;
import com.ottocap.NewWorkFlow.client.Widget.FormHorizontalPanel2;

public class DomesticLogoLayout extends LayoutContainer
{
  private DomesticLogoField domesticlogofield;
  private FormHorizontalPanel2 hor2 = new FormHorizontalPanel2();
  private FormHorizontalPanel2 hor3 = new FormHorizontalPanel2();
  private OrderDataLogo orderdatalogo;
  private ColorwayPanel colorwaypanel;
  private SetPortlet setportlet;
  
  public DomesticLogoLayout(OrderData orderdata, OrderDataLogo orderdatalogo, SetPortlet setportlet)
  {
    this.setportlet = setportlet;
    this.orderdatalogo = orderdatalogo;
    
    this.domesticlogofield = new DomesticLogoField(orderdata, orderdatalogo, setportlet.getSetField());
    this.domesticlogofield.addListener(BuzzEvents.SetDSTInfo, this.setdstinfolistener);
    this.domesticlogofield.addListener(BuzzEvents.UpdateColorways, this.updatecolorwayslistener);
    this.domesticlogofield.addListener(BuzzEvents.TabLabelChange, this.tablabelchangelistener);
    
    add(this.domesticlogofield.getLogoUploadFormField());
    
    FormHorizontalPanel2 hor4 = new FormHorizontalPanel2();
    hor4.add(this.domesticlogofield.getDecorationListField());
    add(hor4);
    
    this.hor2.add(this.domesticlogofield.getLogoSizeWidthField());
    this.hor2.add(this.domesticlogofield.getLogoSizeHeightField());
    this.hor2.add(this.domesticlogofield.getNumberOfColorField());
    this.hor2.add(this.domesticlogofield.getTotalStitchCountField());
    this.hor2.add(this.domesticlogofield.getVendorLogoPriceField());
    this.hor2.add(this.domesticlogofield.getCustomerLogoPriceField());
    if (NewWorkFlow.get().isFaya.booleanValue()) {
      this.hor2.add(this.domesticlogofield.getLogoSizeChoiceField());
    }
    add(this.hor2);
    
    this.hor3.addCheckBox(this.domesticlogofield.getDigitizingRequiredField());
    this.hor3.addCheckBox(this.domesticlogofield.getMetallicThreadField(), new FormData(91, 0));
    this.hor3.addCheckBox(this.domesticlogofield.getNeonThreadField(), new FormData(91, 0));
    this.hor3.addCheckBox(this.domesticlogofield.getTapeEditField(), new FormData(91, 0));
    this.hor3.addCheckBox(this.domesticlogofield.getFlashChargeField());
    this.hor3.addCheckBox(this.domesticlogofield.getFilmChargeField(), new FormData(91, 0));
    this.hor3.add(this.domesticlogofield.getFilmSetupChargeField());
    this.hor3.addCheckBox(this.domesticlogofield.getSpecialInkField(), new FormData(91, 0));
    
    this.hor3.addCheckBox(this.domesticlogofield.getPMSMatchField(), new FormData(91, 0));
    this.hor3.add(this.domesticlogofield.getColorChangeAmountField());
    
    add(this.hor3);
    
    this.colorwaypanel = new ColorwayPanel(orderdatalogo, this.colorwaycallback);
    add(this.colorwaypanel);
    
    add(this.domesticlogofield.getDSTImageField());
    add(this.domesticlogofield.getLogoImageField());
    
    FieldSet extraoptions = new FieldSet();
    extraoptions.setCollapsible(true);
    extraoptions.setHeadingHtml("Extra Options");
    extraoptions.collapse();
    
    FormHorizontalPanel2 logodescriptionpanel = new FormHorizontalPanel2();
    logodescriptionpanel.add(this.domesticlogofield.getLogoDescriptionField(), new FormData(1072, -1));
    extraoptions.add(logodescriptionpanel);
    
    FieldSet artworkrequestgroup = new FieldSet();
    artworkrequestgroup.setHeadingHtml("Artwork Request");
    FormHorizontalPanel2 artworkrequestform = new FormHorizontalPanel2();
    artworkrequestform.add(this.domesticlogofield.getArtworkTypeField());
    artworkrequestform.add(this.domesticlogofield.getArtworkChargeField());
    artworkrequestform.add(this.domesticlogofield.getArtworkTypeCommentsField());
    artworkrequestgroup.add(artworkrequestform);
    FieldSet swatchgroup = new FieldSet();
    swatchgroup.setHeadingHtml("Swatch");
    FormHorizontalPanel2 swatchform = new FormHorizontalPanel2();
    swatchform.addCheckBox(this.domesticlogofield.getEmailProofField(), new FormData(91, 0));
    swatchform.addCheckBox(this.domesticlogofield.getShipProofField(), new FormData(91, 0));
    swatchform.add(this.domesticlogofield.getProofsToDoField());
    swatchform.add(this.domesticlogofield.getTotalProofsField());
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
      if ((DomesticLogoLayout.this.orderdatalogo.getFilename() != null) && (DomesticLogoLayout.this.orderdatalogo.getFilename().toLowerCase().endsWith(".dst"))) {
        DomesticLogoLayout.this.domesticlogofield.setDSTImageURL();
      }
      DomesticLogoLayout.this.domesticlogofield.calcNumberOfColors();
    }
  };
  


  private Listener<BaseEvent> setdstinfolistener = new Listener()
  {
    public void handleEvent(BaseEvent be)
    {
      DomesticLogoLayout.this.colorwaypanel.setDSTInfo((DSTInfoContainer)be.getSource());
      DomesticLogoLayout.this.colorwaypanel.setVisible(true);
    }
  };
  

  private Listener<BaseEvent> updatecolorwayslistener = new Listener()
  {
    public void handleEvent(BaseEvent be)
    {
      DomesticLogoLayout.this.colorwaypanel.updateColorways();
    }
  };
  

  private Listener<BaseEvent> tablabelchangelistener = new Listener()
  {

    public void handleEvent(BaseEvent be)
    {
      DomesticLogoLayout.this.setportlet.getProductsPortal().getOrderTab().setTabHeader();
    }
  };
  

  public void updateLocations()
  {
    this.domesticlogofield.updateLocations();
  }
}
