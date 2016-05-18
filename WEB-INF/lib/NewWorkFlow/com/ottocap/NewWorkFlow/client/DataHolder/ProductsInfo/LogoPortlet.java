package com.ottocap.NewWorkFlow.client.DataHolder.ProductsInfo;

import com.extjs.gxt.ui.client.event.IconButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Header;
import com.extjs.gxt.ui.client.widget.button.ToolButton;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData.OrderType;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogo;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataSet;

public class LogoPortlet extends com.extjs.gxt.ui.client.widget.custom.Portlet
{
  private SetPortlet setportlet;
  private ToolButton closebutton = new ToolButton("x-tool-close");
  private ToolButton copybutton = new ToolButton("x-tool-restore");
  private ToolButton copylogobutton = new ToolButton("x-tool-maximize");
  private OverseasLogoLayout overseaslogolayout = null;
  private DomesticLogoLayout domesticlogolayout = null;
  private OrderData orderdata;
  private OrderDataLogo orderdatalogo;
  
  public LogoPortlet(OrderData orderdata, OrderDataSet orderdataset, OrderDataLogo orderdatalogo, SetPortlet setportlet) {
    this.setportlet = setportlet;
    this.orderdata = orderdata;
    this.orderdatalogo = orderdatalogo;
    setCollapsible(true);
    getHeader().addTool(this.copylogobutton);
    getHeader().addTool(this.copybutton);
    getHeader().addTool(this.closebutton);
    this.copylogobutton.addSelectionListener(this.selectionlistener);
    this.copybutton.addSelectionListener(this.selectionlistener);
    this.closebutton.addSelectionListener(this.selectionlistener);
    
    this.copylogobutton.setToolTip("Copy Logo");
    this.copybutton.setToolTip("Duplicate Logo Into Set");
    this.closebutton.setToolTip("Delete Logo");
    

    if (orderdata.getOrderType() == OrderData.OrderType.DOMESTIC) {
      this.domesticlogolayout = new DomesticLogoLayout(orderdata, orderdatalogo, setportlet);
      add(this.domesticlogolayout);
    } else {
      this.overseaslogolayout = new OverseasLogoLayout(orderdata, orderdataset, orderdatalogo, setportlet);
      add(this.overseaslogolayout);
    }
  }
  


  private SelectionListener<IconButtonEvent> selectionlistener = new SelectionListener()
  {
    public void componentSelected(IconButtonEvent ce)
    {
      if (ce.getSource().equals(LogoPortlet.this.closebutton)) {
        LogoPortlet.this.setportlet.removeLogo(LogoPortlet.this);
      } else if (ce.getSource().equals(LogoPortlet.this.copybutton)) {
        LogoPortlet.this.setportlet.addLogo(LogoPortlet.this.orderdatalogo.copy());
      } else if (ce.getSource().equals(LogoPortlet.this.copylogobutton)) {
        LogoPortlet.this.setportlet.getProductsPortal().setCopiedLogo(LogoPortlet.this.orderdatalogo.copy());
      }
    }
  };
  
  public OrderDataLogo getLogo() {
    return this.orderdatalogo;
  }
  
  public void updateLocations() {
    if (this.orderdata.getOrderType() == OrderData.OrderType.DOMESTIC) {
      this.domesticlogolayout.updateLocations();
    } else {
      this.overseaslogolayout.updateLocations();
    }
  }
}
