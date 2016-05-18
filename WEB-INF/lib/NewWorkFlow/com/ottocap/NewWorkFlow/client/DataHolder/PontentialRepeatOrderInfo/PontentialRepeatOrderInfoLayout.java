package com.ottocap.NewWorkFlow.client.DataHolder.PontentialRepeatOrderInfo;

import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.google.gwt.user.client.Element;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData;
import com.ottocap.NewWorkFlow.client.Widget.FormHorizontalPanel2;

public class PontentialRepeatOrderInfoLayout extends LayoutContainer
{
  private OrderData myorderdata;
  private PontentialRepeatOrderInfoField pontentialrepeatorderinfofield;
  
  public PontentialRepeatOrderInfoLayout(OrderData myorderdata)
  {
    this.myorderdata = myorderdata;
  }
  
  protected void onRender(Element parent, int index) {
    super.onRender(parent, index);
    RunMethod();
  }
  
  public void RunMethod() {
    this.pontentialrepeatorderinfofield = new PontentialRepeatOrderInfoField(this.myorderdata);
    
    FormHorizontalPanel2 row1 = new FormHorizontalPanel2();
    row1.add(this.pontentialrepeatorderinfofield.getRepeatFrequencyField());
    row1.add(this.pontentialrepeatorderinfofield.getRepeatDateField());
    add(row1);
    
    FormHorizontalPanel2 row2 = new FormHorizontalPanel2();
    row2.add(this.pontentialrepeatorderinfofield.getRepeatInternalCommentsField(), new com.extjs.gxt.ui.client.widget.layout.FormData(854, -1));
    add(row2);
  }
  
  public void focusRepeatFreq()
  {
    this.pontentialrepeatorderinfofield.getRepeatFrequencyField().focus();
  }
}
