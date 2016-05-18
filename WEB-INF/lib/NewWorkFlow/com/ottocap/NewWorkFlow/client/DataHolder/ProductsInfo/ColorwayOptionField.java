package com.ottocap.NewWorkFlow.client.DataHolder.ProductsInfo;

import com.extjs.gxt.ui.client.binding.Bindings;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.ottocap.NewWorkFlow.client.DataHolder.Stores.DataStores;
import com.ottocap.NewWorkFlow.client.DataHolder.Stores.ThreadInformationData;
import com.ottocap.NewWorkFlow.client.NewWorkFlow;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogo;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogoColorway;
import com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBox;
import com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBoxModelData;

public class ColorwayOptionField
{
  private OtherComboBox threadbrand = new OtherComboBox();
  private OtherComboBox colorcode = new OtherComboBox();
  private CheckBox flashcharge = new CheckBox();
  private OtherComboBox inktypes = new OtherComboBox();
  private TextField<String> logocolordescription = new TextField();
  private LabelField colornumber = new LabelField();
  private LabelField stitches = new LabelField();
  private OrderDataLogoColorway colorway;
  private OrderDataLogo orderdatalogo;
  private AsyncCallback<Boolean> colorwaychangedcallback;
  
  public ColorwayOptionField(OrderDataLogo orderdatalogo, OrderDataLogoColorway colorway, int thenumber, AsyncCallback<Boolean> colorwaychangedcallback)
  {
    this.colorwaychangedcallback = colorwaychangedcallback;
    this.colorway = colorway;
    this.orderdatalogo = orderdatalogo;
    
    this.inktypes.setFieldLabel("Ink Type");
    this.colornumber.setFieldLabel("Color Number");
    this.stitches.setFieldLabel("Stitches");
    this.threadbrand.setFieldLabel("Thread Brand");
    this.flashcharge.setLabelSeparator(":");
    this.colorcode.setFieldLabel("Color Code");
    this.logocolordescription.setFieldLabel("Logo Color Description");
    
    this.colorcode.setTemplate(getColorCodeTemplate());
    this.colorcode.setItemSelector("div.search-item");
    
    this.threadbrand.setStore(NewWorkFlow.get().getDataStores().getThreadsInformationData().getThreadBrands());
    this.threadbrand.setForceSelection(true);
    this.threadbrand.setMinListWidth(200);
    
    this.inktypes.setStore(NewWorkFlow.get().getDataStores().getInkTypes());
    this.inktypes.setForceSelection(true);
    
    this.colorcode.setPageSize(25);
    this.colorcode.setMinChars(0);
    this.colorcode.setMinListWidth(763);
    this.colorcode.setStore(OtherComboBox.getPagingStore());
    this.colorcode.setDataSource(NewWorkFlow.get().getDataStores().getThreadsInformationData().getThreadBrandColor(colorway.getThreadType()));
    
    this.colorcode.setForceSelection(true);
    
    this.colornumber.setValue(thenumber + ".)");
    this.threadbrand.setValue(colorway.getThreadType());
    this.colorcode.setValue(colorway.getLogoColorCode());
    
    Bindings allbindings = new Bindings();
    allbindings.bind(colorway);
    allbindings.addFieldBinding(new com.extjs.gxt.ui.client.binding.FieldBinding(this.logocolordescription, "colorcodecomment"));
    allbindings.addFieldBinding(new com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBoxFieldBinding(this.inktypes, "inktype"));
    allbindings.addFieldBinding(new com.extjs.gxt.ui.client.binding.FieldBinding(this.flashcharge, "flashcharge"));
    
    updateLayout();
    
    this.threadbrand.addListener(Events.Change, this.changelistener);
    this.colorcode.addListener(Events.Change, this.changelistener);
    
    if (thenumber != 1)
    {

      this.colornumber.setHideLabel(true);
      this.stitches.setHideLabel(true);
      this.inktypes.setHideLabel(true);
      this.flashcharge.setHideLabel(true);
      this.threadbrand.setHideLabel(true);
      this.colorcode.setHideLabel(true);
      this.logocolordescription.setHideLabel(true);
    }
  }
  








  private com.extjs.gxt.ui.client.event.Listener<BaseEvent> changelistener = new com.extjs.gxt.ui.client.event.Listener()
  {
    public void handleEvent(BaseEvent be)
    {
      if (be.getSource().equals(ColorwayOptionField.this.threadbrand)) {
        ColorwayOptionField.this.colorway.setThreadType(ColorwayOptionField.this.threadbrand.getValue() == null ? "" : ColorwayOptionField.this.threadbrand.getValue().getValue());
        ColorwayOptionField.this.colorcode.changeDataSource(NewWorkFlow.get().getDataStores().getThreadsInformationData().getThreadBrandColor(ColorwayOptionField.this.colorway.getThreadType()), ColorwayOptionField.this.colorway.getLogoColorCode());
        ColorwayOptionField.this.colorcode.clearRemoteFilter();
        ColorwayOptionField.this.colorway.setLogoColorCode(ColorwayOptionField.this.colorcode.getValue() == null ? "" : ColorwayOptionField.this.colorcode.getValue().getValue());
        ColorwayOptionField.this.colorwaychangedcallback.onSuccess(Boolean.valueOf(true));
      } else if (be.getSource().equals(ColorwayOptionField.this.colorcode)) {
        ColorwayOptionField.this.colorway.setLogoColorCode(ColorwayOptionField.this.colorcode.getValue() == null ? "" : ColorwayOptionField.this.colorcode.getValue().getValue());
        ColorwayOptionField.this.colorwaychangedcallback.onSuccess(Boolean.valueOf(true));
      }
    }
  };
  
  private native String getColorCodeTemplate();
  
  public OrderDataLogoColorway getOrderDataColorway() { return this.colorway; }
  
  public void updateLayout()
  {
    this.stitches.setValue(this.colorway.getStitches());
    boolean hasdst = false;
    if (((this.orderdatalogo.getFilename() != null) && (this.orderdatalogo.getFilename().toLowerCase().endsWith(".dst"))) || ((this.orderdatalogo.getDstFilename() != null) && (this.orderdatalogo.getDstFilename().toLowerCase().endsWith(".dst")))) {
      hasdst = true;
    }
    
    if ((this.orderdatalogo.getDecoration() != null) && (this.orderdatalogo.getDecoration().equals("CAD Print")))
    {

      this.flashcharge.setFieldLabel("Color Garment Charge");
      this.inktypes.setVisible(false);
      this.flashcharge.setVisible(false);
    } else if ((this.orderdatalogo.getDecoration() != null) && (this.orderdatalogo.getDecoration().equals("Screen Print")))
    {

      this.flashcharge.setFieldLabel("Flash Charge");
      this.inktypes.setVisible(true);
      this.flashcharge.setVisible(true);
    } else if ((this.orderdatalogo.getDecoration() != null) && (this.orderdatalogo.getDecoration().equals("Four Color Screen Print")))
    {

      this.flashcharge.setFieldLabel("Flash Charge");
      this.inktypes.setVisible(true);
      this.flashcharge.setVisible(true);
    } else if ((this.orderdatalogo.getDecoration() != null) && (this.orderdatalogo.getDecoration().equals("Heat Transfer"))) {
      this.flashcharge.setFieldLabel("Color Garment Charge");
      this.inktypes.setVisible(false);
      this.flashcharge.setVisible(true);
    }
    else
    {
      this.flashcharge.setFieldLabel("Flash Charge");
      this.inktypes.setVisible(false);
      this.flashcharge.setVisible(false);
    }
    

    this.stitches.setVisible(hasdst);
    this.colorcode.setValue(this.colorway.getLogoColorCode());
  }
  
  public OtherComboBox getThreadBrandField() {
    return this.threadbrand;
  }
  
  public OtherComboBox getColorCodeField() {
    return this.colorcode;
  }
  
  public CheckBox getFlashChargeField() {
    return this.flashcharge;
  }
  
  public OtherComboBox getInkTypesField() {
    return this.inktypes;
  }
  
  public TextField<String> getLogoColorDescriptionField() {
    return this.logocolordescription;
  }
  
  public LabelField getColorNumberField() {
    return this.colornumber;
  }
  
  public LabelField getStitchesField() {
    return this.stitches;
  }
}
