package com.ottocap.NewWorkFlow.client.DataHolder.ProductsInfo;

import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogo;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogoColorway;
import com.ottocap.NewWorkFlow.client.Widget.FormHorizontalPanel2;

public class ColorwayOption extends LayoutContainer
{
  private FormHorizontalPanel2 hor1 = new FormHorizontalPanel2();
  private ColorwayOptionField colorwayoptionfield;
  
  public ColorwayOption(OrderDataLogo orderdatalogo, OrderDataLogoColorway colorway, int thenumber, AsyncCallback<Boolean> colorwaychangedcallback) {
    this.colorwayoptionfield = new ColorwayOptionField(orderdatalogo, colorway, thenumber, colorwaychangedcallback);
    
    if (thenumber == 1) {
      this.hor1.add(this.colorwayoptionfield.getColorNumberField(), new FormData(91, 0));
      this.hor1.add(this.colorwayoptionfield.getStitchesField(), new FormData(91, 0));
      this.hor1.add(this.colorwayoptionfield.getInkTypesField());
      this.hor1.add(this.colorwayoptionfield.getFlashChargeField(), new FormData(91, 0));
      this.hor1.add(this.colorwayoptionfield.getThreadBrandField());
      this.hor1.add(this.colorwayoptionfield.getColorCodeField());
      this.hor1.add(this.colorwayoptionfield.getLogoColorDescriptionField());
    } else {
      this.hor1.add(this.colorwayoptionfield.getColorNumberField(), new FormData(91, 0));
      this.hor1.add(this.colorwayoptionfield.getStitchesField(), new FormData(91, 0));
      this.hor1.add(this.colorwayoptionfield.getInkTypesField(), new FormData(200, 0));
      this.hor1.add(this.colorwayoptionfield.getFlashChargeField(), new FormData(91, 0));
      this.hor1.add(this.colorwayoptionfield.getThreadBrandField(), new FormData(200, 0));
      this.hor1.add(this.colorwayoptionfield.getColorCodeField(), new FormData(200, 0));
      this.hor1.add(this.colorwayoptionfield.getLogoColorDescriptionField(), new FormData(200, 0));
    }
    
    add(this.hor1);
  }
  
  public OrderDataLogoColorway getOrderDataColorway()
  {
    return this.colorwayoptionfield.getOrderDataColorway();
  }
  
  public void updateLayout() {
    this.colorwayoptionfield.updateLayout();
  }
}
