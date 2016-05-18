package com.ottocap.NewWorkFlow.client.DataHolder.ProductsInfo;

import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogo;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogoColorway;
import com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBox;
import com.ottocap.NewWorkFlow.client.Widget.FormHorizontalPanel2;
import java.util.ArrayList;

public class ColorwayPanel extends FieldSet
{
  private OrderDataLogo orderdatalogo;
  private ArrayList<ColorwayOption> colorwayoptions = new ArrayList();
  private LayoutContainer thecontainer = new LayoutContainer();
  private AsyncCallback<Boolean> colorwaycallback;
  private ColorwayPanelField colorwaypanelfield;
  
  public ColorwayPanel(OrderDataLogo orderdatalogo, AsyncCallback<Boolean> colorwaycallback) {
    this.colorwaycallback = colorwaycallback;
    this.orderdatalogo = orderdatalogo;
    setHeadingHtml("Color Option");
    this.colorwaypanelfield = new ColorwayPanelField(orderdatalogo);
    
    int currentcolorwaycount = 1;
    for (OrderDataLogoColorway currentcolorway : orderdatalogo.getColorways()) {
      ColorwayOption colorwayoption = new ColorwayOption(orderdatalogo, currentcolorway, currentcolorwaycount++, colorwaycallback);
      this.thecontainer.add(colorwayoption);
      this.colorwayoptions.add(colorwayoption);
    }
    
    updateColorways();
    
    add(this.thecontainer);
    FormHorizontalPanel2 convertallthreadpanel = new FormHorizontalPanel2();
    convertallthreadpanel.add(this.colorwaypanelfield.getConvertAllThreadsField());
    
    add(convertallthreadpanel);
  }
  
  public void updateColorways()
  {
    boolean hasdst = false;
    




    if ((this.orderdatalogo.getDstFilename() != null) && (this.orderdatalogo.getDstFilename().toLowerCase().endsWith(".dst"))) {
      if ((this.orderdatalogo.getFilename() == null) || (this.orderdatalogo.getFilename().equals(""))) {
        hasdst = true;
      } else if ((this.orderdatalogo.getFilename() != null) && (this.orderdatalogo.getFilename().toLowerCase().endsWith(".dst"))) {
        hasdst = true;
      }
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
    
    for (ColorwayOption currentcolorwayoption : this.colorwayoptions) {
      currentcolorwayoption.updateLayout();
    }
    
    layout();
    setVisible(this.colorwayoptions.size() != 0);
  }
  
  private void removeColorway() {
    ColorwayOption theoption = (ColorwayOption)this.colorwayoptions.get(this.colorwayoptions.size() - 1);
    this.thecontainer.remove(theoption);
    this.orderdatalogo.removeColorway(theoption.getOrderDataColorway());
    this.colorwayoptions.remove(theoption);
  }
  
  private void addColorway() {
    OrderDataLogoColorway thecolorway = this.orderdatalogo.addColorway();
    ColorwayOption theoption = new ColorwayOption(this.orderdatalogo, thecolorway, this.orderdatalogo.getColorwayCount(), this.colorwaycallback);
    this.thecontainer.add(theoption);
    this.colorwayoptions.add(theoption);
  }
  
  private void addColorway(String threadtype) {
    OrderDataLogoColorway thecolorway = this.orderdatalogo.addColorway();
    thecolorway.setThreadType(threadtype);
    ColorwayOption theoption = new ColorwayOption(this.orderdatalogo, thecolorway, this.orderdatalogo.getColorwayCount(), this.colorwaycallback);
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
    for (ColorwayOption currentcolorwayoption : this.colorwayoptions) {
      if ((currentcolorwayoption.getOrderDataColorway().getLogoColorCode() == null) || (currentcolorwayoption.getOrderDataColorway().getLogoColorCode().equals(""))) {
        currentcolorwayoption.getOrderDataColorway().setLogoColorCode("ColorCode " + (currentcolorwayoptioncount + 1));
      }
      currentcolorwayoption.getOrderDataColorway().setStitches(Integer.valueOf(theinfo.getStitchCount(currentcolorwayoptioncount++)));
      currentcolorwayoption.updateLayout();
    }
    
    this.colorwaypanelfield.getConvertAllThreadsField().setVisible(true);
    
    layout();
  }
}
