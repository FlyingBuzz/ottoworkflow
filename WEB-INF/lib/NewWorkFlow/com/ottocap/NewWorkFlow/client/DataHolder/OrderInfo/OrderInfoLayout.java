package com.ottocap.NewWorkFlow.client.DataHolder.OrderInfo;

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.google.gwt.user.client.Element;
import com.ottocap.NewWorkFlow.client.EditOrder.OrderTab;
import com.ottocap.NewWorkFlow.client.NewWorkFlow;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData;
import com.ottocap.NewWorkFlow.client.Widget.FormHorizontalPanel2;

public class OrderInfoLayout extends LayoutContainer
{
  private OrderData myorderdata;
  private OrderTab ordertab;
  private OrderInfoFields orderinfofields;
  
  public OrderInfoLayout(OrderData myorderdata, OrderTab ordertab)
  {
    this.myorderdata = myorderdata;
    this.ordertab = ordertab;
  }
  
  protected void onRender(Element parent, int index) {
    super.onRender(parent, index);
    RunMethod();
  }
  
  public void RunMethod() {
    this.orderinfofields = new OrderInfoFields(this.myorderdata);
    
    this.orderinfofields.getOrderNumberField().addListener(com.extjs.gxt.ui.client.event.Events.Change, this.changelistener);
    
    FormHorizontalPanel2 orderhorizontalpanel1 = new FormHorizontalPanel2();
    FormHorizontalPanel2 orderhorizontalpanel2 = new FormHorizontalPanel2();
    FormHorizontalPanel2 orderhorizontalpanel3 = new FormHorizontalPanel2();
    FormHorizontalPanel2 orderhorizontalpanel4 = new FormHorizontalPanel2();
    FormHorizontalPanel2 orderhorizontalpanel5 = new FormHorizontalPanel2();
    FormHorizontalPanel2 orderhorizontalpanel6 = new FormHorizontalPanel2();
    
    orderhorizontalpanel1.add(this.orderinfofields.getOrderNumberField());
    if (NewWorkFlow.get().isFaya.booleanValue()) {
      this.orderinfofields.getOrderNumberField().setVisible(false);
    }
    orderhorizontalpanel1.add(this.orderinfofields.getCustomerPOField());
    orderhorizontalpanel1.addCheckBox(this.orderinfofields.getRushOrderField());
    
    orderhorizontalpanel2.add(this.orderinfofields.getOrderDateField());
    orderhorizontalpanel2.add(this.orderinfofields.getEstimatedShipDateField());
    orderhorizontalpanel2.add(this.orderinfofields.getInhandDateField());
    
    orderhorizontalpanel3.add(this.orderinfofields.getOrderCommentField(), new FormData(854, -1));
    
    orderhorizontalpanel4.add(this.orderinfofields.getOrderStatusField(), new FormData(418, 0));
    orderhorizontalpanel4.add(this.orderinfofields.getStatusDueDateField());
    orderhorizontalpanel4.add(this.orderinfofields.getStatusDueTimeField());
    orderhorizontalpanel4.add(this.orderinfofields.getOrderShipDateField());
    
    orderhorizontalpanel5.add(this.orderinfofields.getNextActionField(), new FormData(854, -1));
    
    this.orderinfofields.getInternalCommentField().setHeight(232);
    orderhorizontalpanel6.add(this.orderinfofields.getInternalCommentField(), new FormData(854, -1));
    
    add(orderhorizontalpanel1);
    add(orderhorizontalpanel2);
    add(orderhorizontalpanel3);
    add(orderhorizontalpanel4);
    add(orderhorizontalpanel5);
    add(orderhorizontalpanel6);
  }
  
  private Listener<BaseEvent> changelistener = new Listener()
  {
    public void handleEvent(BaseEvent be)
    {
      if (be.getSource().equals(OrderInfoLayout.this.orderinfofields.getOrderNumberField())) {
        OrderInfoLayout.this.ordertab.setTabHeader();
      }
    }
  };
}
