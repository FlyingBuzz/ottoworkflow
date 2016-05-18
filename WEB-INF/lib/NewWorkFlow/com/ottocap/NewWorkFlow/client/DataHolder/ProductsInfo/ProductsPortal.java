package com.ottocap.NewWorkFlow.client.DataHolder.ProductsInfo;

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabItem.HeaderItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.ottocap.NewWorkFlow.client.EditOrder.OrderTab;
import com.ottocap.NewWorkFlow.client.Icons.ResourceIcons;
import com.ottocap.NewWorkFlow.client.Icons.Resources;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData.OrderType;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogo;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataSet;
import java.util.ArrayList;

public class ProductsPortal extends TabPanel
{
  private OrderData myorderdata;
  private OrderTab ordertab;
  private TabItem addsetbutton;
  private ArrayList<SetPortlet> mysets;
  private OrderDataLogo copiedlogo;
  private MenuItem pastelogomenuitem;
  
  public ProductsPortal(OrderData myorderdata, OrderTab ordertab)
  {
    this.myorderdata = myorderdata;
    this.ordertab = ordertab;
    
    if (myorderdata.getSetCount() == 0) {
      OrderDataSet theset = myorderdata.addSet();
      theset.addItem();
    }
  }
  

  protected void onRender(com.google.gwt.user.client.Element parent, int index)
  {
    setTabPosition(com.extjs.gxt.ui.client.widget.TabPanel.TabPosition.BOTTOM);
    setBodyBorder(false);
    setTabScroll(true);
    super.onRender(parent, index);
    RunMethod();
  }
  
  public void RunMethod() {
    this.addsetbutton = new TabItem("Add Set");
    this.mysets = new ArrayList();
    
    if (this.myorderdata.getOrderType() == OrderData.OrderType.DOMESTIC) {
      this.addsetbutton.getHeader().setStyleName("domestic-tab x-tab-with-icon");
    }
    
    add(this.addsetbutton);
    
    int currentsetcoutner = 1;
    for (OrderDataSet currentset : this.myorderdata.getSets()) {
      SetPortlet mysetportlet = new SetPortlet(this.myorderdata, currentset, this);
      mysetportlet.setText("Set " + currentsetcoutner++);
      mysetportlet.setIcon(Resources.ICONS.t_shirt_print_gray());
      if (this.myorderdata.getOrderType() == OrderData.OrderType.DOMESTIC) {
        mysetportlet.getHeader().setStyleName("domestic-tab x-tab-strip-closable x-tab-with-icon");
      }
      
      this.mysets.add(mysetportlet);
      add(mysetportlet);
    }
    
    setSelection((TabItem)getItem(1));
    
    this.addsetbutton.setIcon(Resources.ICONS.plus_button());
    this.addsetbutton.addListener(Events.BeforeSelect, this.clicklistener);
    this.addsetbutton.addListener(Events.Select, this.clicklistener);
  }
  
  public SetPortlet getSetPortlet(OrderDataSet currentset)
  {
    for (TabItem theitem : getItems()) {
      if ((theitem instanceof SetPortlet)) {
        SetPortlet currentitem = (SetPortlet)theitem;
        if (currentitem.getSet() == currentset) {
          return currentitem;
        }
      }
    }
    return null;
  }
  






  public void addSet()
  {
    addSet(new OrderDataSet());
  }
  
  public void addSet(OrderDataSet orderdataset) {
    SetPortlet mysetportlet = new SetPortlet(this.myorderdata, this.myorderdata.addSet(orderdataset), this);
    mysetportlet.setText("Set " + this.myorderdata.getSetCount());
    mysetportlet.setIcon(Resources.ICONS.t_shirt_print_gray());
    if (this.myorderdata.getOrderType() == OrderData.OrderType.DOMESTIC) {
      mysetportlet.getHeader().setStyleName("domestic-tab x-tab-strip-closable x-tab-with-icon");
    }
    this.mysets.add(mysetportlet);
    add(mysetportlet);
    setSelection(mysetportlet);
  }
  












  private com.extjs.gxt.ui.client.event.Listener<BaseEvent> clicklistener = new com.extjs.gxt.ui.client.event.Listener()
  {
    public void handleEvent(BaseEvent be)
    {
      if (be.getSource().equals(ProductsPortal.this.addsetbutton.getTabPanel())) {
        be.setCancelled(true);
        ProductsPortal.this.addSet();
      }
    }
  };
  
  public void removeSet(SetPortlet setportlet)
  {
    this.mysets.remove(setportlet);
    this.myorderdata.removeSet(setportlet.getSet());
    remove(setportlet);
    fixSetTitle();
    if (this.myorderdata.getSetCount() == 0) {
      addSet();
    }
  }
  
  private void fixSetTitle() {
    int currentsetcounter = 1;
    for (SetPortlet currentset : this.mysets) {
      currentset.setText("Set " + currentsetcounter++);
    }
  }
  
  public OrderTab getOrderTab() {
    return this.ordertab;
  }
  
  protected void onItemContextMenu(TabItem item, int x, int y)
  {
    if ((item instanceof SetPortlet)) {
      if (this.closeContextMenu == null) {
        this.closeContextMenu = new Menu();
        MenuItem copysetmenuitem = new MenuItem("Copy Set", new SelectionListener()
        {
          public void componentSelected(MenuEvent ce) {
            SetPortlet item = (SetPortlet)((Menu)ce.getContainer()).getData("tab");
            ProductsPortal.this.addSet(item.getSet().copy());
          }
        });
        MenuItem deletesetmenuitem = new MenuItem("Delete Set", new SelectionListener()
        {
          public void componentSelected(MenuEvent ce) {
            SetPortlet item = (SetPortlet)((Menu)ce.getContainer()).getData("tab");
            item.confirmdelete();
          }
        });
        this.pastelogomenuitem = new MenuItem("Paste Logo", new SelectionListener()
        {
          public void componentSelected(MenuEvent ce) {
            if (ProductsPortal.this.copiedlogo != null) {
              SetPortlet item = (SetPortlet)((Menu)ce.getContainer()).getData("tab");
              item.addLogo(ProductsPortal.this.copiedlogo.copy());
            }
          }
        });
        this.closeContextMenu.add(copysetmenuitem);
        this.closeContextMenu.add(deletesetmenuitem);
        this.closeContextMenu.add(this.pastelogomenuitem);
      }
      if (this.pastelogomenuitem != null) {
        if (this.copiedlogo == null) {
          this.pastelogomenuitem.disable();
        } else {
          this.pastelogomenuitem.enable();
        }
      }
      this.closeContextMenu.setData("tab", item);
      this.closeContextMenu.showAt(x, y);
    }
  }
  
  public void setCopiedLogo(OrderDataLogo copiedlogo) {
    this.copiedlogo = copiedlogo;
  }
  
  public OrderDataLogo getCopiedLogo() {
    return this.copiedlogo;
  }
}
