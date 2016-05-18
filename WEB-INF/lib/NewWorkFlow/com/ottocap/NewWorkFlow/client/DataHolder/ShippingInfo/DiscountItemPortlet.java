package com.ottocap.NewWorkFlow.client.DataHolder.ShippingInfo;

import com.extjs.gxt.ui.client.event.IconButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Header;
import com.extjs.gxt.ui.client.widget.button.ToolButton;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataDiscount;
import com.ottocap.NewWorkFlow.client.Widget.FormHorizontalPanel2;

public class DiscountItemPortlet extends com.extjs.gxt.ui.client.widget.custom.Portlet
{
  private OrderDataDiscount discountitem;
  private AsyncCallback<DiscountItemPortlet> removecallback;
  private ToolButton closebutton = new ToolButton("x-tool-close");
  private DiscountItemField discountitemfield;
  
  public DiscountItemPortlet(OrderDataDiscount discountitem, AsyncCallback<DiscountItemPortlet> removecallback) {
    this.discountitemfield = new DiscountItemField(discountitem);
    
    this.removecallback = removecallback;
    this.discountitem = discountitem;
    getHeader().addTool(this.closebutton);
    this.closebutton.addSelectionListener(this.selectionlistener);
    
    FormHorizontalPanel2 mypanel = new FormHorizontalPanel2();
    mypanel.add(this.discountitemfield.getReasonField());
    mypanel.add(this.discountitemfield.getAmountField());
    mypanel.add(this.discountitemfield.getIntoItemsField());
    
    add(mypanel);
  }
  
  private SelectionListener<IconButtonEvent> selectionlistener = new SelectionListener()
  {
    public void componentSelected(IconButtonEvent ce)
    {
      if (ce.getSource().equals(DiscountItemPortlet.this.closebutton)) {
        DiscountItemPortlet.this.removecallback.onSuccess(DiscountItemPortlet.this);
      }
    }
  };
  
  public OrderDataDiscount getDiscountItem() {
    return this.discountitem;
  }
}
