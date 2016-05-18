package com.ottocap.NewWorkFlow.client.OnlineWorkflow;

import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.ottocap.NewWorkFlow.client.DataHolder.ProductsInfo.ColorwayPanelField;
import com.ottocap.NewWorkFlow.client.DataHolder.ProductsInfo.DSTInfoContainer;
import com.ottocap.NewWorkFlow.client.OnlineWorkflow.Widget.GreyDescription;
import com.ottocap.NewWorkFlow.client.OnlineWorkflow.Widget.PaddedBodyContainer;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogo;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogoColorway;
import com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBox;
import com.ottocap.NewWorkFlow.client.Widget.FormHorizontalPanel2;
import java.util.ArrayList;

public class OnlineColorwayPanel extends VerticalPanel
{
  private OrderDataLogo orderdatalogo;
  private ArrayList<OnlineColorwayOption> colorwayoptions = new ArrayList();
  private LayoutContainer thecontainer = new LayoutContainer();
  private AsyncCallback<Boolean> colorwaycallback;
  private ColorwayPanelField colorwaypanelfield;
  
  public OnlineColorwayPanel(OrderDataLogo orderdatalogo, AsyncCallback<Boolean> colorwaycallback) {
    this.colorwaycallback = colorwaycallback;
    this.orderdatalogo = orderdatalogo;
    this.colorwaypanelfield = new ColorwayPanelField(orderdatalogo);
    
    int currentcolorwaycount = 1;
    for (OrderDataLogoColorway currentcolorway : orderdatalogo.getColorways()) {
      OnlineColorwayOption colorwayoption = new OnlineColorwayOption(orderdatalogo, currentcolorway, currentcolorwaycount++, colorwaycallback);
      this.thecontainer.add(colorwayoption);
      this.colorwayoptions.add(colorwayoption);
    }
    
    updateColorways();
    
    add(new GreyDescription("Please specify the thread brand and color for your logo. If you would like to match the thread color of the cap, please select \"Match Product Color\" and pick a cap color. For heat transfers, please list out the PMS colors of the logo."));
    
    PaddedBodyContainer colorwaypadded = new PaddedBodyContainer();
    FieldSet fieldset = new FieldSet();
    fieldset.setWidth(960);
    fieldset.setHeadingHtml("Color Option");
    
    fieldset.add(this.thecontainer);
    FormHorizontalPanel2 convertallthreadpanel = new FormHorizontalPanel2();
    convertallthreadpanel.add(this.colorwaypanelfield.getConvertAllThreadsField());
    
    fieldset.add(convertallthreadpanel);
    
    colorwaypadded.add(fieldset);
    add(colorwaypadded);
  }
  
  public void updateColorways()
  {
    boolean hasdst = false;
    if (((this.orderdatalogo.getFilename() != null) && (this.orderdatalogo.getFilename().toLowerCase().endsWith(".dst"))) || ((this.orderdatalogo.getDstFilename() != null) && (this.orderdatalogo.getDstFilename().toLowerCase().endsWith(".dst")))) {
      hasdst = true;
    }
    
    if (!hasdst)
    {
      if (this.orderdatalogo.getNumberOfColor() != null) {
        while (this.colorwayoptions.size() < this.orderdatalogo.getNumberOfColor().intValue()) {
          addColorway();
        }
        while (this.colorwayoptions.size() > this.orderdatalogo.getNumberOfColor().intValue()) {
          removeColorway();
        }
      }
    }
    
    this.colorwaypanelfield.getConvertAllThreadsField().setVisible(hasdst);
    
    for (OnlineColorwayOption currentcolorwayoption : this.colorwayoptions) {
      currentcolorwayoption.updateLayout();
    }
    
    layout();
    setVisible(this.colorwayoptions.size() != 0);
  }
  
  private void removeColorway() {
    OnlineColorwayOption theoption = (OnlineColorwayOption)this.colorwayoptions.get(this.colorwayoptions.size() - 1);
    this.thecontainer.remove(theoption);
    this.orderdatalogo.removeColorway(theoption.getOrderDataColorway());
    this.colorwayoptions.remove(theoption);
  }
  
  private void addColorway() {
    OrderDataLogoColorway thecolorway = this.orderdatalogo.addColorway();
    OnlineColorwayOption theoption = new OnlineColorwayOption(this.orderdatalogo, thecolorway, this.orderdatalogo.getColorwayCount(), this.colorwaycallback);
    this.thecontainer.add(theoption);
    this.colorwayoptions.add(theoption);
  }
  
  private void addColorway(String threadtype) {
    OrderDataLogoColorway thecolorway = this.orderdatalogo.addColorway();
    thecolorway.setThreadType(threadtype);
    OnlineColorwayOption theoption = new OnlineColorwayOption(this.orderdatalogo, thecolorway, this.orderdatalogo.getColorwayCount(), this.colorwaycallback);
    this.thecontainer.add(theoption);
    this.colorwayoptions.add(theoption);
  }
  
  public void setDSTInfo(DSTInfoContainer theinfo) {
    while (this.colorwayoptions.size() < theinfo.getColorwayCount()) {
      addColorway("Madeira Polyneon");
    }
    while (this.colorwayoptions.size() > theinfo.getColorwayCount()) {
      removeColorway();
    }
    
    int currentcolorwayoptioncount = 0;
    for (OnlineColorwayOption currentcolorwayoption : this.colorwayoptions) {
      currentcolorwayoption.getOrderDataColorway().setStitches(Integer.valueOf(theinfo.getStitchCount(currentcolorwayoptioncount++)));
      currentcolorwayoption.updateLayout();
    }
    
    this.colorwaypanelfield.getConvertAllThreadsField().setVisible(true);
    
    layout();
  }
}
