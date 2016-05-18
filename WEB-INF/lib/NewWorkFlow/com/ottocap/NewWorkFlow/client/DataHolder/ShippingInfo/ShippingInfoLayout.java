package com.ottocap.NewWorkFlow.client.DataHolder.ShippingInfo;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.PortalEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.custom.Portal;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.ottocap.NewWorkFlow.client.Icons.ResourceIcons;
import com.ottocap.NewWorkFlow.client.Icons.Resources;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData;
import com.ottocap.NewWorkFlow.client.Widget.FormHorizontalPanel2;
import java.util.ArrayList;

public class ShippingInfoLayout extends LayoutContainer
{
  private OrderData myorderdata;
  private Button addpriceadjustment;
  private Portal priceadjustportal;
  private ArrayList<DiscountItemPortlet> mydiscountitems;
  private ShippingInfoField shippinginfofield;
  
  public ShippingInfoLayout(OrderData myorderdata)
  {
    this.myorderdata = myorderdata;
  }
  
  protected void onRender(Element parent, int index) {
    super.onRender(parent, index);
    RunMethod();
  }
  
  public void RunMethod() {
    this.shippinginfofield = new ShippingInfoField(this.myorderdata);
    this.addpriceadjustment = new Button("Add Price Adjustment", Resources.ICONS.price_tag());
    this.priceadjustportal = new Portal(1);
    this.mydiscountitems = new ArrayList();
    
    this.priceadjustportal.setColumnWidth(0, 1.0D);
    

    setLayout(new RowLayout());
    
    addListeners();
    
    FormHorizontalPanel2 orderhorizontalpanel1 = new FormHorizontalPanel2();
    
    orderhorizontalpanel1.add(this.shippinginfofield.getShippingTypeField(), new FormData(418, 0));
    orderhorizontalpanel1.add(this.shippinginfofield.getShippingCostField());
    
    int discountitemcoutner = 1;
    for (com.ottocap.NewWorkFlow.client.OrderData.OrderDataDiscount mydiscount : this.myorderdata.getDiscountItems()) {
      DiscountItemPortlet myportlet = new DiscountItemPortlet(mydiscount, this.removeporletcallback);
      myportlet.setHeadingHtml("Discount Item " + discountitemcoutner++);
      this.mydiscountitems.add(myportlet);
      this.priceadjustportal.add(myportlet, 0);
    }
    
    add(orderhorizontalpanel1, new RowData(1.0D, -1.0D));
    add(this.priceadjustportal, new RowData(1.0D, -1.0D));
    add(this.addpriceadjustment, new RowData(-1.0D, -1.0D));
  }
  
  private AsyncCallback<DiscountItemPortlet> removeporletcallback = new AsyncCallback()
  {
    public void onFailure(Throwable caught) {}
    


    public void onSuccess(DiscountItemPortlet result)
    {
      ShippingInfoLayout.this.mydiscountitems.remove(result);
      ShippingInfoLayout.this.myorderdata.removeDiscountItem(result.getDiscountItem());
      ShippingInfoLayout.this.priceadjustportal.remove(result, 0);
      ShippingInfoLayout.this.fixDiscountItemTitle();
    }
  };
  
  private void addListeners()
  {
    this.addpriceadjustment.addSelectionListener(this.selectionlistener);
    this.priceadjustportal.addListener(Events.Drop, this.droplistener);
  }
  
  private Listener<PortalEvent> droplistener = new Listener() {
    public void handleEvent(PortalEvent be) {
      if (be.getSource() == ShippingInfoLayout.this.priceadjustportal) {
        ShippingInfoLayout.this.moveDiscountItem(be.getStartRow(), be.getRow());
        ShippingInfoLayout.this.fixDiscountItemTitle();
      }
    }
  };
  
  private void moveDiscountItem(int startpos, int newpos) {
    this.myorderdata.moveDiscountItem(startpos, newpos);
    if ((startpos < newpos) || (startpos > newpos)) {
      DiscountItemPortlet myitem = (DiscountItemPortlet)this.mydiscountitems.get(startpos);
      this.mydiscountitems.remove(startpos);
      this.mydiscountitems.add(newpos, myitem);
    }
  }
  
  private void fixDiscountItemTitle() {
    int discountitemcoutner = 1;
    for (DiscountItemPortlet discountitem : this.mydiscountitems) {
      discountitem.setHeadingHtml("Discount Item " + discountitemcoutner++);
    }
  }
  
  private SelectionListener<ButtonEvent> selectionlistener = new SelectionListener()
  {
    public void componentSelected(ButtonEvent ce)
    {
      if (ce.getSource() == ShippingInfoLayout.this.addpriceadjustment) {
        DiscountItemPortlet myportlet = new DiscountItemPortlet(ShippingInfoLayout.this.myorderdata.addDiscountItem(), ShippingInfoLayout.this.removeporletcallback);
        myportlet.setHeadingHtml("Discount Item " + (ShippingInfoLayout.this.mydiscountitems.size() + 1));
        ShippingInfoLayout.this.mydiscountitems.add(myportlet);
        ShippingInfoLayout.this.priceadjustportal.add(myportlet, 0);
      }
    }
  };
}
