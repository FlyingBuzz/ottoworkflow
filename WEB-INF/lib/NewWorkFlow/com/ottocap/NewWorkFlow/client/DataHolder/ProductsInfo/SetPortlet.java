package com.ottocap.NewWorkFlow.client.DataHolder.ProductsInfo;

import com.extjs.gxt.ui.client.core.FastMap;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.IconButtonEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.PortalEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.button.ToolButton;
import com.extjs.gxt.ui.client.widget.custom.Portal;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.ottocap.NewWorkFlow.client.DataHolder.Stores.NewStyleStore;
import com.ottocap.NewWorkFlow.client.DataHolder.WorkOrderInfo.OverseasVendorsLayout;
import com.ottocap.NewWorkFlow.client.EditOrder.OrderTab;
import com.ottocap.NewWorkFlow.client.Icons.ResourceIcons;
import com.ottocap.NewWorkFlow.client.Icons.Resources;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData.OrderType;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataItem;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogo;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataSet;
import com.ottocap.NewWorkFlow.client.Widget.BuzzEvents;
import com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBoxModelData;
import java.util.ArrayList;
import java.util.Iterator;

public class SetPortlet extends com.extjs.gxt.ui.client.widget.TabItem
{
  private ToolButton closebutton = new ToolButton("x-tool-close");
  private ToolButton copybutton = new ToolButton("x-tool-restore");
  private OrderData orderdata;
  private OrderDataSet orderset;
  private Portal itemsportal = new Portal(1);
  private ArrayList<ItemPortlet> myitems = new ArrayList();
  private Button additembutton = new Button("Add Item", Resources.ICONS.t_shirt_gray());
  private Portal logosportal = new Portal(1);
  private ArrayList<LogoPortlet> mylogos = new ArrayList();
  private Button addlogobutton = new Button("Add Logo", Resources.ICONS.image_sunset());
  private boolean deletenow = false;
  private MessageBox deletesetbox = new MessageBox();
  
  private ProductsPortal productsportal;
  
  private SetField setfield;
  

  public SetPortlet(OrderData orderdata, OrderDataSet orderset, ProductsPortal productsportal)
  {
    this.productsportal = productsportal;
    this.orderdata = orderdata;
    this.orderset = orderset;
    setScrollMode(com.extjs.gxt.ui.client.Style.Scroll.AUTO);
    setClosable(true);
    addListener(Events.BeforeClose, this.closelistener);
    this.setfield = new SetField(orderset);
    this.setfield.addListener(BuzzEvents.SetLogoLocations, this.setlogolocationslistener);
    






    this.deletesetbox.setButtons("yesno");
    this.deletesetbox.setIcon(MessageBox.QUESTION);
    this.deletesetbox.setTitleHtml("Are you sure?");
    this.deletesetbox.addCallback(this.messageboxlistener);
    




    setLayout(new com.ottocap.NewWorkFlow.client.Widget.MinRowLayout());
    this.copybutton.addSelectionListener(this.selectionlistener);
    this.closebutton.addSelectionListener(this.selectionlistener);
    this.itemsportal.setColumnWidth(0, 1.0D);
    this.itemsportal.addListener(Events.Drop, this.droplistener);
    this.additembutton.addSelectionListener(this.buttonlistener);
    this.logosportal.setColumnWidth(0, 1.0D);
    this.logosportal.addListener(Events.Drop, this.droplistener);
    this.addlogobutton.addSelectionListener(this.buttonlistener);
    
    this.copybutton.setToolTip("Duplicate Item Into Set");
    this.closebutton.setToolTip("Delete Item");
    
    if (orderset.getItemCount() == 0) {
      orderset.addItem();
    }
    
    int currentitemcoutner = 1;
    ItemPortlet myitemportlet; for (OrderDataItem currentitem : orderset.getItems()) {
      myitemportlet = new ItemPortlet(orderdata, orderset, currentitem, this);
      myitemportlet.setHeadingHtml("Item " + currentitemcoutner++);
      this.myitems.add(myitemportlet);
      this.itemsportal.add(myitemportlet, 0);
    }
    
    add(this.itemsportal, new RowData(1.0D, -1.0D));
    add(this.additembutton, new RowData(-1.0D, -1.0D));
    this.setfield.setLogoLocations();
    
    if ((orderdata.getOrderType() == OrderData.OrderType.DOMESTIC) && 
      (orderset.getLogoCount() == 0)) {
      orderset.addLogo();
    }
    

    int currentlogocounter = 1;
    for (OrderDataLogo currentlogo : orderset.getLogos()) {
      LogoPortlet mylogoportlet = new LogoPortlet(orderdata, orderset, currentlogo, this);
      mylogoportlet.setHeadingHtml("Logo " + currentlogocounter++);
      this.mylogos.add(mylogoportlet);
      this.logosportal.add(mylogoportlet, 0);
    }
    
    add(this.logosportal, new RowData(1.0D, -1.0D));
    add(this.addlogobutton, new RowData(-1.0D, -1.0D));
  }
  

  private Listener<PortalEvent> droplistener = new Listener() {
    public void handleEvent(PortalEvent be) {
      if (be.getSource() == SetPortlet.this.itemsportal) {
        SetPortlet.this.moveItems(be.getStartRow(), be.getRow());
        SetPortlet.this.fixItemTitle();
      }
      if (be.getSource() == SetPortlet.this.logosportal) {
        SetPortlet.this.moveLogos(be.getStartRow(), be.getRow());
        SetPortlet.this.fixLogoTitle();
      }
    }
  };
  
  private void moveItems(int startpos, int newpos) {
    this.orderset.moveItem(startpos, newpos);
    if ((startpos < newpos) || (startpos > newpos)) {
      ItemPortlet myitem = (ItemPortlet)this.myitems.get(startpos);
      this.myitems.remove(startpos);
      this.myitems.add(newpos, myitem);
    }
  }
  
  private void moveLogos(int startpos, int newpos) {
    this.orderset.moveLogo(startpos, newpos);
    if ((startpos < newpos) || (startpos > newpos)) {
      LogoPortlet mylogo = (LogoPortlet)this.mylogos.get(startpos);
      this.mylogos.remove(startpos);
      this.mylogos.add(newpos, mylogo);
    }
  }
  
  public void removeItem(ItemPortlet itemportlet) {
    this.myitems.remove(itemportlet);
    this.orderset.removeItem(itemportlet.getItem());
    this.itemsportal.remove(itemportlet, 0);
    fixItemTitle();
    if (this.orderset.getItemCount() == 0) {
      addItem();
    }
    this.setfield.setLogoLocations();
    if ((this.orderdata.getOrderType() == OrderData.OrderType.OVERSEAS) && (getProductsPortal().getOrderTab().getOverseasVendorsLayout() != null)) {
      getProductsPortal().getOrderTab().getOverseasVendorsLayout().setVendorsVisible();
    }
  }
  
  public void removeLogo(LogoPortlet logoportlet)
  {
    this.mylogos.remove(logoportlet);
    this.orderset.removeLogo(logoportlet.getLogo());
    this.logosportal.remove(logoportlet, 0);
    fixLogoTitle();
    if ((this.orderdata.getOrderType() == OrderData.OrderType.DOMESTIC) && 
      (this.orderset.getLogoCount() == 0)) {
      addLogo();
    }
  }
  
  public ArrayList<ItemPortlet> getItemPortlets()
  {
    return this.myitems;
  }
  
  private void fixItemTitle() {
    int count = 1;
    for (ItemPortlet theitem : this.myitems) {
      theitem.setHeadingHtml("Item " + count);
      count++;
    }
  }
  
  private void fixLogoTitle() {
    int count = 1;
    for (LogoPortlet thelogo : this.mylogos) {
      thelogo.setHeadingHtml("Logo " + count);
      count++;
    }
  }
  
  private SelectionListener<ButtonEvent> buttonlistener = new SelectionListener()
  {
    public void componentSelected(ButtonEvent ce)
    {
      if (ce.getSource().equals(SetPortlet.this.additembutton)) {
        SetPortlet.this.addItem();
      } else if (ce.getSource().equals(SetPortlet.this.addlogobutton)) {
        SetPortlet.this.addLogo();
      }
    }
  };
  


  private SelectionListener<IconButtonEvent> selectionlistener = new SelectionListener()
  {
    public void componentSelected(IconButtonEvent ce)
    {
      if (ce.getSource().equals(SetPortlet.this.closebutton)) {
        SetPortlet.this.productsportal.removeSet(SetPortlet.this);
      } else if (ce.getSource().equals(SetPortlet.this.copybutton)) {
        SetPortlet.this.productsportal.addSet(SetPortlet.this.orderset.copy());
      }
    }
  };
  
  public OrderDataSet getSet() {
    return this.orderset;
  }
  








  public ProductsPortal getProductsPortal()
  {
    return this.productsportal;
  }
  
  private void doCloseNow() {
    this.deletenow = true;
    this.productsportal.removeSet(this);
  }
  
  public void confirmdelete() {
    this.deletesetbox.setMessage("Are you sure you want to delete set " + (this.orderdata.getSetIndex(this.orderset) + 1));
    this.deletesetbox.show();
  }
  
  private Listener<BaseEvent> closelistener = new Listener()
  {
    public void handleEvent(BaseEvent be)
    {
      if (!SetPortlet.this.deletenow) {
        SetPortlet.this.confirmdelete();
        be.setCancelled(true);
      }
    }
  };
  

  private Listener<MessageBoxEvent> messageboxlistener = new Listener()
  {
    public void handleEvent(MessageBoxEvent be)
    {
      if ((be.getMessageBox().equals(SetPortlet.this.deletesetbox)) && 
        (be.getButtonClicked().getHtml().equals("Yes"))) {
        SetPortlet.this.doCloseNow();
      }
    }
  };
  



  public void addLogo()
  {
    addLogo(new OrderDataLogo());
  }
  
  public void addLogo(OrderDataLogo orderdatalogo) {
    LogoPortlet mylogoportlet = new LogoPortlet(this.orderdata, this.orderset, this.orderset.addLogo(orderdatalogo), this);
    mylogoportlet.setHeadingHtml("Logo " + this.orderset.getLogoCount());
    this.mylogos.add(mylogoportlet);
    this.logosportal.add(mylogoportlet, 0);
  }
  
  public void addItem() {
    addItem(new OrderDataItem());
  }
  
  public void addItem(OrderDataItem orderdataitem) {
    ItemPortlet myitemportlet = new ItemPortlet(this.orderdata, this.orderset, this.orderset.addItem(orderdataitem), this);
    myitemportlet.setHeadingHtml("Item " + this.orderset.getItemCount());
    this.myitems.add(myitemportlet);
    this.itemsportal.add(myitemportlet, 0);
    this.setfield.setLogoLocations();
  }
  
  public void focusAddLogoButton() {
    this.addlogobutton.focus();
  }
  
  public SetField getSetField() {
    return this.setfield;
  }
  
  private Listener<BaseEvent> setlogolocationslistener = new Listener()
  {

    public void handleEvent(BaseEvent be)
    {
      FastMap<OtherComboBoxModelData> allitems = new FastMap();
      String thecolor = "#d63232";
      String blankcolor = "#ffffff";
      int count = SetPortlet.this.myitems.size();
      ListStore<OtherComboBoxModelData> currentstore;
      int j; for (Iterator localIterator = SetPortlet.this.myitems.iterator(); localIterator.hasNext(); 
          
          j < currentstore.getCount())
      {
        ItemPortlet currentitem = (ItemPortlet)localIterator.next();
        currentstore = currentitem.getItemPortletField().getCurrentStyle().getLogoLocationStore();
        j = 0; continue;
        OtherComboBoxModelData thecombobox = (OtherComboBoxModelData)currentstore.getAt(j);
        if (allitems.containsKey(thecombobox.getValue())) {
          OtherComboBoxModelData thebox = (OtherComboBoxModelData)allitems.get(thecombobox.getValue());
          thebox.set("matchingall", Integer.valueOf(((Integer)thebox.get("matchingall")).intValue() + 1));
          if (SetPortlet.this.orderdata.getOrderType() == OrderData.OrderType.DOMESTIC) {
            if (((Boolean)thecombobox.get("heat")).booleanValue()) {
              thebox.set("heatcount", Integer.valueOf(((Integer)thebox.get("heatcount")).intValue() + 1));
            }
            if (((Boolean)thecombobox.get("emb")).booleanValue()) {
              thebox.set("embcount", Integer.valueOf(((Integer)thebox.get("embcount")).intValue() + 1));
            }
            if (((Boolean)thecombobox.get("dtg")).booleanValue()) {
              thebox.set("dtgcount", Integer.valueOf(((Integer)thebox.get("dtgcount")).intValue() + 1));
            }
            if (((Boolean)thecombobox.get("cadprint")).booleanValue()) {
              thebox.set("cadprintcount", Integer.valueOf(((Integer)thebox.get("cadprintcount")).intValue() + 1));
            }
            if (((Boolean)thecombobox.get("screenprint")).booleanValue()) {
              thebox.set("screenprintcount", Integer.valueOf(((Integer)thebox.get("screenprintcount")).intValue() + 1));
            }
          }
        } else {
          OtherComboBoxModelData thebox = new OtherComboBoxModelData();
          thebox.set("name", thecombobox.getName());
          thebox.set("value", thecombobox.getValue());
          thebox.set("matchingall", Integer.valueOf(1));
          if (SetPortlet.this.orderdata.getOrderType() == OrderData.OrderType.DOMESTIC) {
            thebox.set("heatcount", Integer.valueOf(((Boolean)thecombobox.get("heat")).booleanValue() ? 1 : 0));
            thebox.set("embcount", Integer.valueOf(((Boolean)thecombobox.get("emb")).booleanValue() ? 1 : 0));
            thebox.set("dtgcount", Integer.valueOf(((Boolean)thecombobox.get("dtg")).booleanValue() ? 1 : 0));
            thebox.set("cadprintcount", Integer.valueOf(((Boolean)thecombobox.get("cadprint")).booleanValue() ? 1 : 0));
            thebox.set("screenprintcount", Integer.valueOf(((Boolean)thecombobox.get("screenprint")).booleanValue() ? 1 : 0));
          }
          allitems.put(thecombobox.getValue(), thebox);
        }
        j++;
      }
      



































      ListStore<OtherComboBoxModelData> thestore = new ListStore();
      
      for (OtherComboBoxModelData currentitem : allitems.values()) {
        OtherComboBoxModelData themodeldata = new OtherComboBoxModelData();
        themodeldata.set("name", currentitem.getName());
        themodeldata.set("value", currentitem.getValue());
        int totalmatching = ((Integer)currentitem.get("matchingall")).intValue();
        themodeldata.set("matchingrest", count == totalmatching ? blankcolor : thecolor);
        if (SetPortlet.this.orderdata.getOrderType() == OrderData.OrderType.DOMESTIC) {
          themodeldata.set("heatcount", currentitem.get("heatcount"));
          themodeldata.set("embcount", currentitem.get("embcount"));
          themodeldata.set("dtgcount", currentitem.get("dtgcount"));
          themodeldata.set("cadprintcount", currentitem.get("cadprintcount"));
          themodeldata.set("screenprintcount", currentitem.get("screenprintcount"));
        }
        thestore.add(themodeldata);
      }
      

      thestore.sort("name", com.extjs.gxt.ui.client.Style.SortDir.ASC);
      SetPortlet.this.setfield.setLogoLocations(thestore);
      
      for (LogoPortlet logo : SetPortlet.this.mylogos) {
        logo.updateLocations();
      }
    }
  };
}
