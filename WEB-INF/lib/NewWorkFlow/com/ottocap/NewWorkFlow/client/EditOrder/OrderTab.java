package com.ottocap.NewWorkFlow.client.EditOrder;

import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.core.FastMap;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabItem.HeaderItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.RadioGroup;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.ottocap.NewWorkFlow.client.DataHolder.PontentialRepeatOrderInfo.PontentialRepeatOrderInfoLayout;
import com.ottocap.NewWorkFlow.client.DataHolder.ProductsInfo.ItemPortlet;
import com.ottocap.NewWorkFlow.client.DataHolder.ProductsInfo.ItemPortletField;
import com.ottocap.NewWorkFlow.client.DataHolder.ProductsInfo.ProductsPortal;
import com.ottocap.NewWorkFlow.client.DataHolder.ProductsInfo.SetPortlet;
import com.ottocap.NewWorkFlow.client.DataHolder.Stores.DataStores;
import com.ottocap.NewWorkFlow.client.DataHolder.Stores.NewStyleStore;
import com.ottocap.NewWorkFlow.client.DataHolder.WorkOrderInfo.OverseasVendorsLayout;
import com.ottocap.NewWorkFlow.client.Icons.ResourceIcons;
import com.ottocap.NewWorkFlow.client.Icons.Resources;
import com.ottocap.NewWorkFlow.client.MainTabPanel;
import com.ottocap.NewWorkFlow.client.NewWorkFlow;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData.OrderType;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataDiscount;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataItem;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogo;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataSet;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataVendorInformation;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataVendors;
import com.ottocap.NewWorkFlow.client.OrderDataWithStyle;
import com.ottocap.NewWorkFlow.client.Services.WorkFlowServiceAsync;
import com.ottocap.NewWorkFlow.client.StoredUser;
import com.ottocap.NewWorkFlow.client.StyleInformationData.StyleType;
import com.ottocap.NewWorkFlow.client.Widget.BuzzAsyncCallback;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

public class OrderTab extends TabItem
{
  private OrderData orderdata = null;
  private Button logoutbutton = new Button("<u>L</u>og Out", Resources.ICONS.door_open_out());
  private Button filebutton = new Button("<u>F</u>ile");
  private MenuItem savefile = new MenuItem("Save", Resources.ICONS.disk());
  private TabPanel myportal = new TabPanel();
  private OverseasVendorsLayout ordersvendorlayout;
  private MessageBox wanttosavebox = new MessageBox();
  private MessageBox savebeforeprinting = new MessageBox();
  private boolean closenow = false;
  private MenuItem genericapprovalfile = new MenuItem("Generic Approval", Resources.ICONS.document());
  private MenuItem detailedquotefile = null;
  private MenuItem productionapprovalfile = null;
  private MenuItem approvalfile = null;
  private MenuItem closefile = new MenuItem("Close", Resources.ICONS.folder_horizontal());
  private MenuItem digitizingfile = new MenuItem("Digitizing WorkOrder", Resources.ICONS.document_workorder());
  private MenuItem sampleorderfile = new MenuItem("Sample Order", Resources.ICONS.document_workorder());
  private MenuItem orderfile = new MenuItem("Order", Resources.ICONS.document_workorder());
  private MenuItem artworkfile = new MenuItem("Artwork Request", Resources.ICONS.images());
  private MenuItem copyfile = new MenuItem("Copy Order", Resources.ICONS.document_copy());
  private MenuItem copytofile = null;
  private MenuItem exportfile = new MenuItem("Export", Resources.ICONS.folder_export());
  private String printouttype = null;
  private TabItem pontentialrepeatorderinfo = new TabItem("Potential Next Project Information");
  private PontentialRepeatOrderInfoLayout pontentialrepeatorderinfolayout = null;
  private TabItem productsportlet = new TabItem("Products");
  private ProductsPortal productsportal = null;
  private boolean havetowait = false;
  private TabItem shippinginfoportlet = new TabItem("Shipping Information");
  
  public OrderTab(OrderData orderdata) {
    this.orderdata = orderdata;
    setClosable(true);
    if (orderdata.getOrderType() == OrderData.OrderType.DOMESTIC) {
      this.copytofile = new MenuItem("Copy To Overseas", Resources.ICONS.document_copy());
      this.detailedquotefile = new MenuItem("Detailed Quote", Resources.ICONS.document_domestic());
      this.productionapprovalfile = new MenuItem("Production Apporval", Resources.ICONS.document_domestic());
      this.approvalfile = new MenuItem("Approval", Resources.ICONS.document_domestic());
      setIcon(Resources.ICONS.document_domestic());
      getHeader().setStyleName("domestic-tab x-tab-strip-closable x-tab-with-icon");
      this.myportal.setStyleName("domestic-tabpanel x-tab-panel");
    } else {
      this.copytofile = new MenuItem("Copy To Domestic", Resources.ICONS.document_copy());
      this.detailedquotefile = new MenuItem("Detailed Quote", Resources.ICONS.document_overseas());
      this.productionapprovalfile = new MenuItem("Production Apporval", Resources.ICONS.document_overseas());
      this.approvalfile = new MenuItem("Approval", Resources.ICONS.document_overseas());
      setIcon(Resources.ICONS.document_overseas());
    }
    
    this.logoutbutton.addSelectionListener(this.buttonlistener);
    this.filebutton.addSelectionListener(this.buttonlistener);
    
    this.savefile.addSelectionListener(this.menulistener);
    this.closefile.addSelectionListener(this.menulistener);
    this.copyfile.addSelectionListener(this.menulistener);
    this.exportfile.addSelectionListener(this.menulistener);
    this.copytofile.addSelectionListener(this.menulistener);
    
    this.detailedquotefile.addSelectionListener(this.menulistener);
    this.genericapprovalfile.addSelectionListener(this.menulistener);
    this.productionapprovalfile.addSelectionListener(this.menulistener);
    this.approvalfile.addSelectionListener(this.menulistener);
    this.digitizingfile.addSelectionListener(this.menulistener);
    this.sampleorderfile.addSelectionListener(this.menulistener);
    this.orderfile.addSelectionListener(this.menulistener);
    this.artworkfile.addSelectionListener(this.menulistener);
    
    this.wanttosavebox.setButtons("yesnocancel");
    this.wanttosavebox.setIcon(MessageBox.QUESTION);
    this.wanttosavebox.setTitleHtml("Save Changes?");
    this.wanttosavebox.addCallback(this.messageboxlistener);
    this.wanttosavebox.setMessage("You are closing a tab that has unsaved changes. Would you like to save your changes?");
    
    this.savebeforeprinting.setButtons("yesnocancel");
    this.savebeforeprinting.setIcon(MessageBox.QUESTION);
    this.savebeforeprinting.setTitleHtml("Save Changes?");
    this.savebeforeprinting.addCallback(this.messageboxlistener);
    this.savebeforeprinting.setMessage("Would you like to save your changes?");
    
    setTabHeader();
    setLayout(new com.extjs.gxt.ui.client.widget.layout.RowLayout());
    addListener(com.extjs.gxt.ui.client.event.Events.BeforeClose, this.closelistener);
    
    ToolBar ordertoolbar = new ToolBar();
    if (orderdata.getOrderType() == OrderData.OrderType.DOMESTIC) {
      ordertoolbar.setStyleName("x-small-editor domestic-toolbar");
    }
    
    MenuItem printfile = new MenuItem("Print", Resources.ICONS.printer());
    
    Menu printmenu = new Menu();
    MenuItem customerfile = new MenuItem("Customer", Resources.ICONS.user_green_female());
    MenuItem vendorfile = new MenuItem("Vendor", Resources.ICONS.truck());
    printmenu.add(customerfile);
    printmenu.add(vendorfile);
    printmenu.add(this.artworkfile);
    
    Menu customermenu = new Menu();
    customermenu.add(this.detailedquotefile);
    customermenu.add(this.genericapprovalfile);
    customermenu.add(this.productionapprovalfile);
    customermenu.add(this.approvalfile);
    
    Menu vendormenu = new Menu();
    
    vendormenu.add(this.digitizingfile);
    vendormenu.add(this.sampleorderfile);
    vendormenu.add(this.orderfile);
    
    Menu filemenu = new Menu();
    
    filemenu.add(this.savefile);
    filemenu.add(printfile);
    filemenu.add(new com.extjs.gxt.ui.client.widget.menu.SeparatorMenuItem());
    filemenu.add(this.copyfile);
    filemenu.add(this.copytofile);
    filemenu.add(this.exportfile);
    filemenu.add(new com.extjs.gxt.ui.client.widget.menu.SeparatorMenuItem());
    filemenu.add(this.closefile);
    
    this.filebutton.setMenu(filemenu);
    
    ordertoolbar.add(this.filebutton);
    ordertoolbar.add(new com.extjs.gxt.ui.client.widget.toolbar.FillToolItem());
    ordertoolbar.add(this.logoutbutton);
    
    printfile.setSubMenu(printmenu);
    customerfile.setSubMenu(customermenu);
    vendorfile.setSubMenu(vendormenu);
    
    this.myportal.setTabPosition(com.extjs.gxt.ui.client.widget.TabPanel.TabPosition.BOTTOM);
    this.myportal.setTabScroll(true);
    
    TabItem customerinfoportlet = new TabItem("Customer Information");
    


    customerinfoportlet.setScrollMode(Style.Scroll.AUTO);
    customerinfoportlet.setIcon(Resources.ICONS.user_green_female());
    customerinfoportlet.add(new com.ottocap.NewWorkFlow.client.DataHolder.CustomerInfo.CustomerInfoLayout(orderdata, this));
    this.myportal.add(customerinfoportlet);
    
    TabItem orderinfoportlet = new TabItem("Order Information");
    


    orderinfoportlet.setScrollMode(Style.Scroll.AUTO);
    orderinfoportlet.setIcon(Resources.ICONS.clock__pencil());
    orderinfoportlet.add(new com.ottocap.NewWorkFlow.client.DataHolder.OrderInfo.OrderInfoLayout(orderdata, this));
    this.myportal.add(orderinfoportlet);
    



    this.productsportlet.setIcon(Resources.ICONS.t_shirt_print());
    this.productsportlet.setLayout(new com.extjs.gxt.ui.client.widget.layout.RowLayout());
    this.productsportal = new ProductsPortal(orderdata, this);
    this.productsportlet.add(this.productsportal, new RowData(1.0D, 1.0D));
    this.myportal.add(this.productsportlet);
    



    this.shippinginfoportlet.setScrollMode(Style.Scroll.AUTO);
    this.shippinginfoportlet.setIcon(Resources.ICONS.truck());
    this.shippinginfoportlet.setLayout(new com.ottocap.NewWorkFlow.client.Widget.MinRowLayout());
    this.shippinginfoportlet.add(new com.ottocap.NewWorkFlow.client.DataHolder.ShippingInfo.ShippingInfoLayout(orderdata), new RowData(1.0D, -1.0D));
    this.myportal.add(this.shippinginfoportlet);
    
    if (orderdata.getOrderType() == OrderData.OrderType.DOMESTIC) {
      TabItem workorderportlet = new TabItem("Work Order Information");
      workorderportlet.getHeader().setStyleName("domestic-tab x-tab-with-icon");
      


      workorderportlet.setScrollMode(Style.Scroll.AUTO);
      workorderportlet.setIcon(Resources.ICONS.document_workorder());
      workorderportlet.add(new com.ottocap.NewWorkFlow.client.DataHolder.WorkOrderInfo.WorkOrderInfoLayout(orderdata));
      this.myportal.add(workorderportlet);
    } else {
      this.ordersvendorlayout = new OverseasVendorsLayout(orderdata);
      TabItem purchaseorderportlet = new TabItem("Purchase Order Information");
      


      purchaseorderportlet.setScrollMode(Style.Scroll.AUTO);
      purchaseorderportlet.setIcon(Resources.ICONS.document_workorder());
      purchaseorderportlet.add(this.ordersvendorlayout);
      this.myportal.add(purchaseorderportlet);
    }
    



    this.pontentialrepeatorderinfo.setScrollMode(Style.Scroll.AUTO);
    this.pontentialrepeatorderinfo.setIcon(Resources.ICONS.arrow_step_over());
    this.pontentialrepeatorderinfolayout = new PontentialRepeatOrderInfoLayout(orderdata);
    this.pontentialrepeatorderinfo.add(this.pontentialrepeatorderinfolayout);
    this.myportal.add(this.pontentialrepeatorderinfo);
    
    if (orderdata.getOrderType() == OrderData.OrderType.DOMESTIC) {
      customerinfoportlet.getHeader().setStyleName("domestic-tab x-tab-with-icon");
      orderinfoportlet.getHeader().setStyleName("domestic-tab x-tab-with-icon");
      this.productsportlet.getHeader().setStyleName("domestic-tab x-tab-with-icon");
      this.shippinginfoportlet.getHeader().setStyleName("domestic-tab x-tab-with-icon");
      this.pontentialrepeatorderinfo.getHeader().setStyleName("domestic-tab x-tab-with-icon");
    }
    
    add(ordertoolbar, new RowData(1.0D, -1.0D));
    add(this.myportal, new RowData(1.0D, 1.0D));
  }
  
  public OverseasVendorsLayout getOverseasVendorsLayout()
  {
    return this.ordersvendorlayout;
  }
  
  private SelectionListener<MenuEvent> menulistener = new SelectionListener()
  {
    public void componentSelected(MenuEvent ce) {
      if (ce.getItem().equals(OrderTab.this.savefile)) {
        OrderTab.this.checkSaveFile();
      } else if (ce.getItem().equals(OrderTab.this.detailedquotefile)) {
        OrderTab.this.printouttype = "Quotation";
        if (OrderTab.this.orderdata.getHiddenKey() == null) {
          if (OrderTab.this.checkSaveFile()) {
            OrderTab.this.doPrintingPDF();
          }
        } else if ((NewWorkFlow.get().getStoredUser().getAccessLevel() == 1) && (!NewWorkFlow.get().getStoredUser().getUsername().equals(OrderTab.this.orderdata.getEmployeeId())) && (!OrderTab.this.orderdata.getEmployeeId().equals(""))) {
          OrderTab.this.doPrintingPDF();
        } else if (OrderTab.this.orderdata.getChangeHappened()) {
          OrderTab.this.savebeforeprinting.show();
        } else {
          OrderTab.this.doPrintingPDF();
        }
      } else if (ce.getItem().equals(OrderTab.this.genericapprovalfile)) {
        OrderTab.this.printouttype = "GenericApproval";
        if (OrderTab.this.orderdata.getHiddenKey() == null) {
          if (OrderTab.this.checkSaveFile()) {
            OrderTab.this.doPrintingPDF();
          }
        } else if ((NewWorkFlow.get().getStoredUser().getAccessLevel() == 1) && (!NewWorkFlow.get().getStoredUser().getUsername().equals(OrderTab.this.orderdata.getEmployeeId())) && (!OrderTab.this.orderdata.getEmployeeId().equals(""))) {
          OrderTab.this.doPrintingPDF();
        } else if (OrderTab.this.orderdata.getChangeHappened()) {
          OrderTab.this.savebeforeprinting.show();
        } else {
          OrderTab.this.doPrintingPDF();
        }
      } else if (ce.getItem().equals(OrderTab.this.productionapprovalfile)) {
        OrderTab.this.printouttype = "ProductionApproval";
        if (OrderTab.this.orderdata.getHiddenKey() == null) {
          if (OrderTab.this.checkSaveFile()) {
            OrderTab.this.doPrintingPDF();
          }
        } else if ((NewWorkFlow.get().getStoredUser().getAccessLevel() == 1) && (!NewWorkFlow.get().getStoredUser().getUsername().equals(OrderTab.this.orderdata.getEmployeeId())) && (!OrderTab.this.orderdata.getEmployeeId().equals(""))) {
          OrderTab.this.doPrintingPDF();
        } else if (OrderTab.this.orderdata.getChangeHappened()) {
          OrderTab.this.savebeforeprinting.show();
        } else {
          OrderTab.this.doPrintingPDF();
        }
      } else if (ce.getItem().equals(OrderTab.this.approvalfile)) {
        OrderTab.this.printouttype = "Approval";
        if (OrderTab.this.orderdata.getHiddenKey() == null) {
          if (OrderTab.this.checkSaveFile()) {
            OrderTab.this.doPrintingPDF();
          }
        } else if ((NewWorkFlow.get().getStoredUser().getAccessLevel() == 1) && (!NewWorkFlow.get().getStoredUser().getUsername().equals(OrderTab.this.orderdata.getEmployeeId())) && (!OrderTab.this.orderdata.getEmployeeId().equals(""))) {
          OrderTab.this.doPrintingPDF();
        } else if (OrderTab.this.orderdata.getChangeHappened()) {
          OrderTab.this.savebeforeprinting.show();
        } else {
          OrderTab.this.doPrintingPDF();
        }
      } else if (ce.getItem().equals(OrderTab.this.digitizingfile)) {
        OrderTab.this.printouttype = "Digitizing";
        if (OrderTab.this.orderdata.getHiddenKey() == null) {
          if (OrderTab.this.checkSaveFile()) {
            OrderTab.this.doPrintingPDF();
          }
        } else if ((NewWorkFlow.get().getStoredUser().getAccessLevel() == 1) && (!NewWorkFlow.get().getStoredUser().getUsername().equals(OrderTab.this.orderdata.getEmployeeId())) && (!OrderTab.this.orderdata.getEmployeeId().equals(""))) {
          OrderTab.this.doPrintingPDF();
        } else if (OrderTab.this.orderdata.getChangeHappened()) {
          OrderTab.this.savebeforeprinting.show();
        } else {
          OrderTab.this.doPrintingPDF();
        }
      } else if (ce.getItem().equals(OrderTab.this.sampleorderfile)) {
        OrderTab.this.printouttype = "SampleOrder";
        if (OrderTab.this.orderdata.getHiddenKey() == null) {
          if (OrderTab.this.checkSaveFile()) {
            OrderTab.this.doPrintingPDF();
          }
        } else if ((NewWorkFlow.get().getStoredUser().getAccessLevel() == 1) && (!NewWorkFlow.get().getStoredUser().getUsername().equals(OrderTab.this.orderdata.getEmployeeId())) && (!OrderTab.this.orderdata.getEmployeeId().equals(""))) {
          OrderTab.this.doPrintingPDF();
        } else if (OrderTab.this.orderdata.getChangeHappened()) {
          OrderTab.this.savebeforeprinting.show();
        } else {
          OrderTab.this.doPrintingPDF();
        }
      } else if (ce.getItem().equals(OrderTab.this.orderfile))
      {
        if (OrderTab.this.orderdata.getOrderType() == OrderData.OrderType.OVERSEAS) {
          boolean foundmissingsampleapprovallist = false;
          for (Iterator localIterator1 = OrderTab.this.orderdata.getSets().iterator(); localIterator1.hasNext(); 
              








              ???.hasNext())
          {
            OrderDataSet theset = (OrderDataSet)localIterator1.next();
            boolean needlogovalid = false;
            
            for (OrderDataLogo thelogo : theset.getLogos()) {
              if (((thelogo.getSwatchToDo() != null) && (thelogo.getSwatchToDo().intValue() > 0)) || ((thelogo.getSwatchTotalEmail() != null) && (thelogo.getSwatchTotalEmail().intValue() > 0)) || ((thelogo.getSwatchTotalShip() != null) && (thelogo.getSwatchTotalShip().intValue() > 0))) {
                needlogovalid = true;
              }
            }
            

            ??? = theset.getItems().iterator(); continue;OrderDataItem theitem = (OrderDataItem)???.next();
            if (((theitem.getProductSampleToDo() != null) && (theitem.getProductSampleToDo().intValue() > 0)) || ((theitem.getProductSampleTotalEmail() != null) && (theitem.getProductSampleTotalEmail().intValue() > 0)) || ((theitem.getProductSampleTotalShip() != null) && (theitem.getProductSampleTotalShip().intValue() > 0) && 
              (theitem.getSampleApprovedList().equals("")))) {
              foundmissingsampleapprovallist = true;
            }
            

            if ((needlogovalid) && 
              (theitem.getSampleApprovedList().equals(""))) {
              foundmissingsampleapprovallist = true;
            }
          }
          



          if (foundmissingsampleapprovallist) {
            MessageBox.alert("Error", "Purchase Order Sample Approval List Is Blank", null);
            return;
          }
        }
        
        OrderTab.this.printouttype = "Order";
        if (OrderTab.this.orderdata.getHiddenKey() == null) {
          if (OrderTab.this.checkSaveFile()) {
            OrderTab.this.doPrintingPDF();
          }
        } else if ((NewWorkFlow.get().getStoredUser().getAccessLevel() == 1) && (!NewWorkFlow.get().getStoredUser().getUsername().equals(OrderTab.this.orderdata.getEmployeeId())) && (!OrderTab.this.orderdata.getEmployeeId().equals(""))) {
          OrderTab.this.doPrintingPDF();
        } else if (OrderTab.this.orderdata.getChangeHappened()) {
          OrderTab.this.savebeforeprinting.show();
        } else {
          OrderTab.this.doPrintingPDF();
        }
      } else if (ce.getItem().equals(OrderTab.this.artworkfile)) {
        OrderTab.this.printouttype = "ArtworkRequest";
        if (OrderTab.this.orderdata.getHiddenKey() == null) {
          if (OrderTab.this.checkSaveFile()) {
            OrderTab.this.doPrintingPDF();
          }
        } else if ((NewWorkFlow.get().getStoredUser().getAccessLevel() == 1) && (!NewWorkFlow.get().getStoredUser().getUsername().equals(OrderTab.this.orderdata.getEmployeeId())) && (!OrderTab.this.orderdata.getEmployeeId().equals(""))) {
          OrderTab.this.doPrintingPDF();
        } else if (OrderTab.this.orderdata.getChangeHappened()) {
          OrderTab.this.savebeforeprinting.show();
        } else {
          OrderTab.this.doPrintingPDF();
        }
      } else if (ce.getItem().equals(OrderTab.this.closefile)) {
        if ((NewWorkFlow.get().getStoredUser().getAccessLevel() == 1) && (!NewWorkFlow.get().getStoredUser().getUsername().equals(OrderTab.this.orderdata.getEmployeeId())) && (!OrderTab.this.orderdata.getEmployeeId().equals(""))) {
          OrderTab.this.doCloseNow();
        } else if (OrderTab.this.orderdata.getChangeHappened()) {
          OrderTab.this.askWantToSave();
        } else {
          OrderTab.this.doCloseNow();
        }
      } else if (ce.getItem().equals(OrderTab.this.copyfile)) {
        OrderTab.this.printouttype = "Copy";
        OrderTab.this.doPrintingPDF();
      } else if (ce.getItem().equals(OrderTab.this.copytofile)) {
        OrderTab.this.printouttype = "CopyTo";
        OrderTab.this.doPrintingPDF();
      } else if (ce.getItem().equals(OrderTab.this.exportfile)) {
        OrderTab.this.printouttype = "Export";
        if (OrderTab.this.orderdata.getChangeHappened()) {
          OrderTab.this.savebeforeprinting.show();
        } else {
          OrderTab.this.doPrintingPDF();
        }
      }
    }
  };
  
  private void doPrintingPDF()
  {
    if (this.orderdata.getOrderType() == OrderData.OrderType.OVERSEAS) {
      boolean msgaboutvendor6 = false;
      Iterator localIterator2;
      for (Iterator localIterator1 = this.orderdata.getSets().iterator(); localIterator1.hasNext(); 
          localIterator2.hasNext())
      {
        OrderDataSet theset = (OrderDataSet)localIterator1.next();
        localIterator2 = theset.getItems().iterator(); continue;OrderDataItem theitem = (OrderDataItem)localIterator2.next();
        String stylenumber = theitem.getStyleNumber();
        if (!stylenumber.equals("")) {
          NewStyleStore thedata = (NewStyleStore)NewWorkFlow.get().getDataStores().getOverseasStyleStore().get(stylenumber);
          if (((theitem != null) && (theitem.getVendorNumber() != null) && (theitem.getVendorNumber().equals("6"))) || ((thedata != null) && (thedata.getVendorNumber() == 6) && (
            (theitem.getQuantity() == null) || (theitem.getQuantity().intValue() < 576)))) {
            msgaboutvendor6 = true;
          }
        }
      }
      


      if (msgaboutvendor6) {
        MessageBox.alert("Note", "Make sure quantity per cap is at least 576 pieces for vendor 6", new Listener()
        {
          public void handleEvent(MessageBoxEvent be)
          {
            if (be.getButtonClicked().getHtml().equals(com.extjs.gxt.ui.client.GXT.MESSAGES.messageBox_ok()))
            {
              OrderTab.this.waittimer.scheduleRepeating(1000);
            }
            
          }
          
        });
      } else {
        this.waittimer.scheduleRepeating(1000);
      }
    }
    else
    {
      this.waittimer.scheduleRepeating(1000);
    }
  }
  

  Timer waittimer = new Timer()
  {
    public void run()
    {
      if (!OrderTab.this.havetowait) {
        OrderTab.this.doopen();
        cancel();
      }
    }
  };
  
  private void doopen()
  {
    if (this.printouttype.equals("Quotation")) {
      Window.open(NewWorkFlow.baseurl + "pdfprintout/?hiddenkey=" + this.orderdata.getHiddenKey() + "&printouttype=Quotation", "_blank", "");
    } else if (this.printouttype.equals("GenericApproval")) {
      Window.open(NewWorkFlow.baseurl + "pdfprintout/?hiddenkey=" + this.orderdata.getHiddenKey() + "&printouttype=GenericApproval", "_blank", "");
    } else if (this.printouttype.equals("ProductionApproval")) {
      Window.open(NewWorkFlow.baseurl + "pdfprintout/?hiddenkey=" + this.orderdata.getHiddenKey() + "&printouttype=ProductionApproval", "_blank", "");
    } else if (this.printouttype.equals("Approval")) {
      Window.open(NewWorkFlow.baseurl + "pdfprintout/?hiddenkey=" + this.orderdata.getHiddenKey() + "&printouttype=Approval", "_blank", "");
    } else if (this.printouttype.equals("Digitizing")) {
      Window.open(NewWorkFlow.baseurl + "pdfprintout/?hiddenkey=" + this.orderdata.getHiddenKey() + "&printouttype=Digitizing", "_blank", "");
    } else if (this.printouttype.equals("SampleOrder")) {
      Window.open(NewWorkFlow.baseurl + "pdfprintout/?hiddenkey=" + this.orderdata.getHiddenKey() + "&printouttype=SampleOrder", "_blank", "");
    } else if (this.printouttype.equals("Order")) {
      MessageBox.info("Note", "Check Deposit Needed", new Listener()
      {
        public void handleEvent(MessageBoxEvent be)
        {
          if (be.getButtonClicked().getHtml().equals(com.extjs.gxt.ui.client.GXT.MESSAGES.messageBox_ok())) {
            Window.open(NewWorkFlow.baseurl + "pdfprintout/?hiddenkey=" + OrderTab.this.orderdata.getHiddenKey() + "&printouttype=Order", "_blank", "");
          }
          
        }
      });
    } else if (this.printouttype.equals("ArtworkRequest")) {
      Window.open(NewWorkFlow.baseurl + "pdfprintout/?hiddenkey=" + this.orderdata.getHiddenKey() + "&printouttype=ArtworkRequest", "_blank", "");
    } else if (this.printouttype.equals("Export")) {
      Window.open(NewWorkFlow.baseurl + "exportorder/?hiddenkey=" + this.orderdata.getHiddenKey(), "_blank", "");
    } else if (this.printouttype.equals("Copy")) {
      NewWorkFlow.get().getWorkFlowRPC().copyOrder(this.orderdata, this.copyordercallback);
    } else if (this.printouttype.equals("CopyTo")) {
      NewWorkFlow.get().getWorkFlowRPC().copyToOrder(this.orderdata, this.copyordertocallback);
    }
  }
  
  private AsyncCallback<OrderDataWithStyle> copyordertocallback = new AsyncCallback()
  {
    public void onFailure(Throwable caught)
    {
      MessageBox.alert("ERROR", caught.getMessage(), null);
    }
    
    public void onSuccess(OrderDataWithStyle result)
    {
      if ((OrderTab.this.orderdata.getOrderStatus().equals("Order Completed")) && 
        (!OrderTab.this.orderdata.getPotentialRepeatFrequency().equals("Never"))) {
        OrderTab.this.orderdata.setPotentialRepeatFrequency("Never");
        OrderTab.this.orderdata.setPotentialRepeatDate(null);
      }
      try
      {
        for (com.ottocap.NewWorkFlow.client.StyleInformationData currentdata : result.getStyleInfoArray()) {
          NewWorkFlow.get().setStyleStore(currentdata);
        }
        OrderTab mytab = new OrderTab(result.getOrderData());
        NewWorkFlow.get().getMainTabPanel().add(mytab);
        NewWorkFlow.get().getMainTabPanel().setSelection(mytab);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  };
  


  private AsyncCallback<OrderData> copyordercallback = new AsyncCallback()
  {
    public void onFailure(Throwable caught) {}
    


    public void onSuccess(OrderData result)
    {
      if ((OrderTab.this.orderdata.getOrderStatus().equals("Order Completed")) && 
        (!OrderTab.this.orderdata.getPotentialRepeatFrequency().equals("Never"))) {
        OrderTab.this.orderdata.setPotentialRepeatFrequency("Never");
        OrderTab.this.orderdata.setPotentialRepeatDate(null);
      }
      

      OrderTab mytab = new OrderTab(result);
      NewWorkFlow.get().getMainTabPanel().add(mytab);
      NewWorkFlow.get().getMainTabPanel().setSelection(mytab);
    }
  };
  
  private void askWantToSave()
  {
    this.wanttosavebox.show();
  }
  
  private Listener<MessageBoxEvent> messageboxlistener = new Listener()
  {
    public void handleEvent(MessageBoxEvent be)
    {
      if (be.getMessageBox().equals(OrderTab.this.wanttosavebox)) {
        if (be.getButtonClicked().getHtml().equals("Yes")) {
          if (OrderTab.this.checkSaveFile()) {
            OrderTab.this.doCloseNow();
          }
        } else if (be.getButtonClicked().getHtml().equals("No")) {
          OrderTab.this.doCloseNow();
        }
        
      }
      else if (be.getMessageBox().equals(OrderTab.this.savebeforeprinting)) {
        if (be.getButtonClicked().getHtml().equals("Yes")) {
          if (OrderTab.this.checkSaveFile()) {
            OrderTab.this.doPrintingPDF();
          }
        } else if (be.getButtonClicked().getHtml().equals("No")) {
          OrderTab.this.doPrintingPDF();
        }
      }
    }
  };
  


  private boolean checkSaveFile()
  {
    this.havetowait = true;
    if ((this.orderdata.getOrderStatus().equals("Cancelled Order")) || (this.orderdata.getOrderStatus().equals("Quot. Lost Delivery Time Issue")) || (this.orderdata.getOrderStatus().equals("Quot. Lost Other Issue")) || (this.orderdata.getOrderStatus().equals("Quot. Lost Price Issue")) || (this.orderdata.getOrderStatus().equals("Quot. Lost Quality Issue"))) {
      NewWorkFlow.get().getWorkFlowRPC().saveOrder(this.orderdata, this.saverodercallback);
      return true;
    }
    if ((this.orderdata.getOrderStatus().equals("Order Completed")) && 
      (this.orderdata.getPotentialRepeatFrequency().equals(""))) {
      MessageBox.alert("Error", "Please select a potential repeat order status", null);
      this.myportal.setSelection(this.pontentialrepeatorderinfo);
      this.pontentialrepeatorderinfolayout.focusRepeatFreq();
      return false;
    }
    

    if (this.orderdata.getCustomerInformation().getContactName().equals("")) {
      MessageBox.alert("Error", "Please fill in a contact name", null);
      return false;
    }
    
    if (this.orderdata.getPotentialRepeatFrequency().equals("")) {
      MessageBox.alert("Error", "Please fill in a potential projects frequency", null);
      return false;
    }
    
    for (OrderDataSet theset : this.orderdata.getSets())
    {
      boolean needprivatelabel = false;
      for (OrderDataItem theitem : theset.getItems()) {
        if (theitem.getStyleNumber().equals("")) {
          MessageBox.alert("Error", "Please choose a style", null);
          this.myportal.setSelection(this.productsportlet);
          SetPortlet currentset = this.productsportal.getSetPortlet(theset);
          this.productsportal.setSelection(currentset);
          for (ItemPortlet theitemportlet : currentset.getItemPortlets()) {
            if (theitemportlet.getItem() == theitem) {
              theitemportlet.getItemPortletField().getProductStyleNumberField().focus();
            }
          }
          return false; }
        if (this.orderdata.getOrderType() == OrderData.OrderType.OVERSEAS)
        {
          if ((theitem.getVendorNumber() != null) && (!theitem.getVendorNumber().equals("")) && (!theitem.getVendorNumber().equals("Default"))) {
            if (NewWorkFlow.get().getDataStores().getOverseasStyleStore().get(theitem.getStyleNumber() + "_" + theitem.getVendorNumber()) == null) {
              if (((NewStyleStore)NewWorkFlow.get().getDataStores().getOverseasStyleStore().get(theitem.getStyleNumber())).getStyleType() == StyleInformationData.StyleType.OVERSEAS) {
                MessageBox.alert("Error", "Can't use overseas style number", null);
                return false;
              }
            }
            else if (((NewStyleStore)NewWorkFlow.get().getDataStores().getOverseasStyleStore().get(theitem.getStyleNumber() + "_" + theitem.getVendorNumber())).getStyleType() == StyleInformationData.StyleType.OVERSEAS) {
              MessageBox.alert("Error", "Can't use overseas style number", null);
              return false;
            }
            
          }
          else if ((NewWorkFlow.get().getDataStores().getOverseasStyleStore().get(theitem.getStyleNumber()) != null) && (((NewStyleStore)NewWorkFlow.get().getDataStores().getOverseasStyleStore().get(theitem.getStyleNumber())).getStyleType() == StyleInformationData.StyleType.OVERSEAS)) {
            MessageBox.alert("Error", "Can't use overseas style number", null);
            return false;
          }
          

          if ((theitem.getVendorNumber() != null) && (!theitem.getVendorNumber().equals("")) && (!theitem.getVendorNumber().equals("Default")) && (this.orderdata.getVendorInformation().getOverseasVendor().get(theitem.getVendorNumber()) == null)) {
            OrderDataVendorInformation vendorinfodata = new OrderDataVendorInformation();
            vendorinfodata.setVendor(Integer.valueOf(theitem.getVendorNumber()));
            vendorinfodata.setShippingMethod(NewWorkFlow.get().getDataStores().getOverseasVendorDefaultShipping(theitem.getVendorNumber()));
            this.orderdata.getVendorInformation().getOverseasVendor().put(theitem.getVendorNumber(), vendorinfodata);
          } else if ((NewWorkFlow.get().getDataStores().getOverseasStyleStore().get(theitem.getStyleNumber()) != null) && (this.orderdata.getVendorInformation().getOverseasVendor().get(String.valueOf(((NewStyleStore)NewWorkFlow.get().getDataStores().getOverseasStyleStore().get(theitem.getStyleNumber())).getVendorNumber())) == null)) {
            OrderDataVendorInformation vendorinfodata = new OrderDataVendorInformation();
            vendorinfodata.setVendor(Integer.valueOf(((NewStyleStore)NewWorkFlow.get().getDataStores().getOverseasStyleStore().get(theitem.getStyleNumber())).getVendorNumber()));
            vendorinfodata.setShippingMethod(NewWorkFlow.get().getDataStores().getOverseasVendorDefaultShipping(String.valueOf(((NewStyleStore)NewWorkFlow.get().getDataStores().getOverseasStyleStore().get(theitem.getStyleNumber())).getVendorNumber())));
            this.orderdata.getVendorInformation().getOverseasVendor().put(String.valueOf(((NewStyleStore)NewWorkFlow.get().getDataStores().getOverseasStyleStore().get(theitem.getStyleNumber())).getVendorNumber()), vendorinfodata);
          }
        }
        

        if (theitem.getQuantity() == null) {
          MessageBox.alert("Error", "Please fill in item quantity", null);
          this.myportal.setSelection(this.productsportlet);
          SetPortlet currentset = this.productsportal.getSetPortlet(theset);
          this.productsportal.setSelection(currentset);
          for (ItemPortlet theitemportlet : currentset.getItemPortlets()) {
            if (theitemportlet.getItem() == theitem) {
              theitemportlet.getItemPortletField().getQuantityField().focus();
            }
          }
          return false;
        }
        
        if ((this.orderdata.getOrderType() == OrderData.OrderType.OVERSEAS) && (theitem.getHasPrivateLabel() == null)) {
          MessageBox.alert("Error", "Please select if there is a private label or a standard label", null);
          this.myportal.setSelection(this.productsportlet);
          SetPortlet currentset = this.productsportal.getSetPortlet(theset);
          this.productsportal.setSelection(currentset);
          for (ItemPortlet theitemportlet : currentset.getItemPortlets()) {
            if (theitemportlet.getItem() == theitem) {
              theitemportlet.getItemPortletField().getPrivateOrStandardLabelGroupField().focus();
            }
          }
          return false;
        }
        
        if ((this.orderdata.getOrderType() == OrderData.OrderType.OVERSEAS) && (theitem.getHasPrivatePackaging() == null)) {
          MessageBox.alert("Error", "Please select if there is a custom packaging or a standard packaging", null);
          this.myportal.setSelection(this.productsportlet);
          SetPortlet currentset = this.productsportal.getSetPortlet(theset);
          this.productsportal.setSelection(currentset);
          for (ItemPortlet theitemportlet : currentset.getItemPortlets()) {
            if (theitemportlet.getItem() == theitem) {
              theitemportlet.getItemPortletField().getPrivateOrStandardPackagingGroupField().focus();
            }
          }
          return false;
        }
        
        if ((this.orderdata.getOrderType() == OrderData.OrderType.OVERSEAS) && (theitem.getHasPrivateShippingMark() == null)) {
          MessageBox.alert("Error", "Please select if there is a custom shipping mark or a standard shipping mark", null);
          this.myportal.setSelection(this.productsportlet);
          SetPortlet currentset = this.productsportal.getSetPortlet(theset);
          this.productsportal.setSelection(currentset);
          for (ItemPortlet theitemportlet : currentset.getItemPortlets()) {
            if (theitemportlet.getItem() == theitem) {
              theitemportlet.getItemPortletField().getPrivateOrStandardShippingMarkGroupField().focus();
            }
          }
          return false;
        }
        
        if ((this.orderdata.getOrderType() == OrderData.OrderType.OVERSEAS) && (theitem.getHasPrivateLabel() != null) && (theitem.getHasPrivateLabel().booleanValue())) {
          needprivatelabel = true;
        }
      }
      

      for (OrderDataLogo thelogo : theset.getLogos()) {
        if ((thelogo.getLogoLocation().equals("28")) || (thelogo.getLogoLocation().equals("A17"))) {
          needprivatelabel = false;
        }
        
        if ((this.orderdata.getOrderType() == OrderData.OrderType.DOMESTIC) && 
          ((thelogo.getDecoration().equals("Flat Embroidery")) || (thelogo.getDecoration().equals("3D Embroidery"))) && 
          (thelogo.getStitchCount() == null)) {
          MessageBox.alert("Error", "Stitch count for logo is missing", null);
          SetPortlet currentset = this.productsportal.getSetPortlet(theset);
          this.productsportal.setSelection(currentset);
          return false;
        }
      }
      



      if (needprivatelabel) {
        MessageBox.alert("Error", "Need private label because an item said there was a private label", null);
        this.myportal.setSelection(this.productsportlet);
        SetPortlet currentset = this.productsportal.getSetPortlet(theset);
        this.productsportal.setSelection(currentset);
        currentset.focusAddLogoButton();
        return false;
      }
    }
    

    for (OrderDataDiscount thediscountitems : this.orderdata.getDiscountItems()) {
      if ((thediscountitems.getAmount() == null) || (thediscountitems.getAmount().doubleValue() == 0.0D)) {
        MessageBox.alert("Error", "Invalid discount amount", null);
        this.myportal.setSelection(this.shippinginfoportlet);
        return false;
      }
    }
    
    NewWorkFlow.get().getWorkFlowRPC().saveOrder(this.orderdata, this.saverodercallback);
    return true;
  }
  
  private BuzzAsyncCallback<Integer> saverodercallback = new BuzzAsyncCallback()
  {
    public void onFailure(Throwable caught)
    {
      NewWorkFlow.get().reLogin2(caught, OrderTab.this.saverodercallback);
    }
    
    public void doRetry()
    {
      NewWorkFlow.get().getWorkFlowRPC().saveOrder(OrderTab.this.orderdata, OrderTab.this.saverodercallback);
    }
    
    public void onSuccess(Integer result)
    {
      if (OrderTab.this.orderdata.getHiddenKey() == null) {
        OrderTab.this.orderdata.setHiddenKey(result);
      }
      OrderTab.this.orderdata.setChangeHappened(false);
      com.extjs.gxt.ui.client.widget.Info.display("Save Successful", "The changes have been saved");
      OrderTab.this.setTabHeader();
      OrderTab.this.havetowait = false;
    }
  };
  

  private SelectionListener<ButtonEvent> buttonlistener = new SelectionListener()
  {
    public void componentSelected(ButtonEvent be) {
      if (be.getButton().equals(OrderTab.this.logoutbutton)) {
        NewWorkFlow.get().doLogOut();
      } else if (be.getButton().equals(OrderTab.this.filebutton)) {
        if ((NewWorkFlow.get().getStoredUser().getAccessLevel() == 1) && (!NewWorkFlow.get().getStoredUser().getUsername().equals(OrderTab.this.orderdata.getEmployeeId())) && (!OrderTab.this.orderdata.getEmployeeId().equals(""))) {
          OrderTab.this.savefile.setEnabled(false);
        } else {
          OrderTab.this.savefile.setEnabled(OrderTab.this.orderdata.getChangeHappened());
        }
      }
    }
  };
  
  private void doCloseNow() {
    this.closenow = true;
    close();
  }
  
  private Listener<BaseEvent> closelistener = new Listener()
  {

    public void handleEvent(BaseEvent be)
    {
      if ((NewWorkFlow.get().getStoredUser().getAccessLevel() != 1) || (NewWorkFlow.get().getStoredUser().getUsername().equals(OrderTab.this.orderdata.getEmployeeId())) || (OrderTab.this.orderdata.getEmployeeId().equals("")))
      {

        if ((!OrderTab.this.closenow) && (OrderTab.this.orderdata.getChangeHappened())) {
          OrderTab.this.askWantToSave();
          be.setCancelled(true);
        }
      }
    }
  };
  

  public void setTabHeader()
  {
    if (!this.closenow) {
      if (this.orderdata.getHiddenKey() == null) {
        getHeader().setText(this.orderdata.getOrderStatus());
      } else if ((this.orderdata.getOrderNumber() != null) && (!this.orderdata.getOrderNumber().equals(""))) {
        getHeader().setText("O#: " + this.orderdata.getOrderNumber());
      } else {
        getHeader().setText("Q#: " + this.orderdata.getHiddenKey());
      }
    }
  }
  
  public OrderData getOrderData()
  {
    return this.orderdata;
  }
  
  public Integer getHiddenKey() {
    return this.orderdata.getHiddenKey();
  }
}
