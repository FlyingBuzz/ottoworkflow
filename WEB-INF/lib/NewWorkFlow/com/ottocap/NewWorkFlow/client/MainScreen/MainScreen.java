package com.ottocap.NewWorkFlow.client.MainScreen;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.extjs.gxt.ui.client.widget.menu.SeparatorMenuItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.ottocap.NewWorkFlow.client.ContainerInvoice.ContainerScreen;
import com.ottocap.NewWorkFlow.client.ContainerInvoice.ContainerScreen.ManageType;
import com.ottocap.NewWorkFlow.client.EditOrder.OrderTab;
import com.ottocap.NewWorkFlow.client.Icons.ResourceIcons;
import com.ottocap.NewWorkFlow.client.Icons.Resources;
import com.ottocap.NewWorkFlow.client.MainTabPanel;
import com.ottocap.NewWorkFlow.client.ManageOrders.ManageOrderScreen;
import com.ottocap.NewWorkFlow.client.ManageOrders.ManageOrderScreen.ManageType;
import com.ottocap.NewWorkFlow.client.NewWorkFlow;
import com.ottocap.NewWorkFlow.client.OnlineWorkflow.OnlineGatherScreen;
import com.ottocap.NewWorkFlow.client.OnlineWorkflow.OnlineGatherScreen.ManageType;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData.OrderType;
import com.ottocap.NewWorkFlow.client.SalesReport.SalesReportScreen;
import com.ottocap.NewWorkFlow.client.SalesReport.SalesReportScreen.ReportType;
import com.ottocap.NewWorkFlow.client.StoredUser;

public class MainScreen extends LayoutContainer
{
  private MenuItem newitem_domesticquote = new MenuItem("Domestic Quotation", Resources.ICONS.document_domestic());
  private MenuItem newitem_domesticorder = new MenuItem("Domestic Order", Resources.ICONS.document_domestic());
  private MenuItem newitem_overseasquote = new MenuItem("Overseas Quotation", Resources.ICONS.document_overseas());
  private MenuItem newitem_overseasorder = new MenuItem("Overseas Order", Resources.ICONS.document_overseas());
  private MenuItem importitem = new MenuItem("<u>I</u>mport Data", Resources.ICONS.folder_import());
  private MenuItem logoutitem = new MenuItem("<u>L</u>og Out", Resources.ICONS.door_open_out());
  private Button logoutbutton = new Button("<u>L</u>og Out", Resources.ICONS.door_open_out());
  private Button filebutton = new Button("<u>F</u>ile");
  private MenuItem manageitem_quotesorders = new MenuItem("Quotes & Orders", Resources.ICONS.application_view_columns());
  private MenuItem manageitem_completedorders = new MenuItem("Completed Orders", Resources.ICONS.application_view_columns());
  private MenuItem manageitem_potentialrepeatorders = new MenuItem("Potential Repeat Orders", Resources.ICONS.application_view_columns());
  private MenuItem manageitem_lostquotesorders = new MenuItem("Lost Quotes & Orders", Resources.ICONS.application_view_columns());
  private MenuItem manageitem_virtualsample = new MenuItem("Virtual Sample", Resources.ICONS.application_view_columns());
  private MenuItem manageitem_completedvirtualsample = new MenuItem("Completed Virtual Sample", Resources.ICONS.application_view_columns());
  

  private MenuItem manageitem_onlinegather_newquotesorders = new MenuItem("New Quotes & Orders", Resources.ICONS.application_view_columns());
  private MenuItem manageitem_onlinegather_exportedquotesorders = new MenuItem("Exported Quotes & Orders", Resources.ICONS.application_view_columns());
  private MenuItem manageitem_onlinegather_newvirtualsamples = new MenuItem("New Virtual Samples", Resources.ICONS.application_view_columns());
  private MenuItem manageitem_onlinegather_exportedvirtualsamples = new MenuItem("Exported Virtual Samples", Resources.ICONS.application_view_columns());
  

  private MenuItem manageitem_container_bookingnoticereceived = new MenuItem("Booking Notice Received", Resources.ICONS.application_view_columns());
  private MenuItem manageitem_container_intransit = new MenuItem("In Transit, Pending Arrival at Port", Resources.ICONS.application_view_columns());
  private MenuItem manageitem_container_pendingdelivery = new MenuItem("Released from Port, Pending Delivery", Resources.ICONS.application_view_columns());
  private MenuItem manageitem_container_currentlyreceving = new MenuItem("Otto Warehouse Currently Receiving", Resources.ICONS.application_view_columns());
  private MenuItem manageitem_container_shipmentsreceived = new MenuItem("Shipments Received", Resources.ICONS.application_view_columns());
  private MenuItem newitem_container_invoice = new MenuItem("New Invoice", Resources.ICONS.document_overseas());
  

  private MenuItem salesreport_report1 = new MenuItem("Customer Sales Report", Resources.ICONS.report());
  private MenuItem salesreport_report2 = new MenuItem("Item Sales History", Resources.ICONS.report());
  private MenuItem salesreport_report3 = new MenuItem("Customer Item Sales", Resources.ICONS.report());
  private MenuItem salesreport_report4 = new MenuItem("Monthly Sales", Resources.ICONS.report());
  private MenuItem salesreport_report5 = new MenuItem("Quotes To Order Conversion", Resources.ICONS.report());
  private MenuItem salesreport_report6 = new MenuItem("Vendor", Resources.ICONS.report());
  private ToolBar toptoolbar = new ToolBar();
  private LayoutContainer mainpanel;
  
  public MainScreen() {
    setLayout(new com.extjs.gxt.ui.client.widget.layout.RowLayout());
    setScrollMode(com.extjs.gxt.ui.client.Style.Scroll.AUTO);
    buildTopToolBar();
    add(this.toptoolbar, new com.extjs.gxt.ui.client.widget.layout.RowData(1.0D, -1.0D));
  }
  
  private void buildTopToolBar()
  {
    this.newitem_domesticquote.addSelectionListener(this.menulistener);
    this.newitem_domesticorder.addSelectionListener(this.menulistener);
    this.newitem_overseasquote.addSelectionListener(this.menulistener);
    this.newitem_overseasorder.addSelectionListener(this.menulistener);
    this.importitem.addSelectionListener(this.menulistener);
    this.logoutitem.addSelectionListener(this.menulistener);
    this.manageitem_quotesorders.addSelectionListener(this.menulistener);
    this.manageitem_completedorders.addSelectionListener(this.menulistener);
    this.manageitem_potentialrepeatorders.addSelectionListener(this.menulistener);
    this.manageitem_lostquotesorders.addSelectionListener(this.menulistener);
    this.manageitem_virtualsample.addSelectionListener(this.menulistener);
    this.manageitem_completedvirtualsample.addSelectionListener(this.menulistener);
    this.logoutbutton.addSelectionListener(this.buttonlistener);
    this.salesreport_report1.addSelectionListener(this.menulistener);
    this.salesreport_report2.addSelectionListener(this.menulistener);
    this.salesreport_report3.addSelectionListener(this.menulistener);
    this.salesreport_report4.addSelectionListener(this.menulistener);
    this.salesreport_report5.addSelectionListener(this.menulistener);
    this.salesreport_report6.addSelectionListener(this.menulistener);
    this.manageitem_onlinegather_newquotesorders.addSelectionListener(this.menulistener);
    this.manageitem_onlinegather_exportedquotesorders.addSelectionListener(this.menulistener);
    this.manageitem_onlinegather_newvirtualsamples.addSelectionListener(this.menulistener);
    this.manageitem_onlinegather_exportedvirtualsamples.addSelectionListener(this.menulistener);
    
    this.manageitem_container_bookingnoticereceived.addSelectionListener(this.menulistener);
    this.manageitem_container_intransit.addSelectionListener(this.menulistener);
    this.manageitem_container_pendingdelivery.addSelectionListener(this.menulistener);
    this.manageitem_container_currentlyreceving.addSelectionListener(this.menulistener);
    this.manageitem_container_shipmentsreceived.addSelectionListener(this.menulistener);
    this.newitem_container_invoice.addSelectionListener(this.menulistener);
  }
  

  private void setupFileMenu()
  {
    this.toptoolbar.add(this.filebutton);
    Menu filemenu = new Menu();
    
    if ((NewWorkFlow.get().getStoredUser().getAccessLevel() == 1) || (NewWorkFlow.get().getStoredUser().getAccessLevel() == 2) || (NewWorkFlow.get().getStoredUser().getAccessLevel() == 3) || (NewWorkFlow.get().getStoredUser().getAccessLevel() == 5))
    {
      MenuItem newitem = new MenuItem("<u>N</u>ew", Resources.ICONS.document_new());
      newitem.setHideOnClick(false);
      filemenu.add(newitem);
      
      Menu newitemoptions = new Menu();
      newitem.setSubMenu(newitemoptions);
      
      newitemoptions.add(this.newitem_domesticquote);
      newitemoptions.add(this.newitem_domesticorder);
      newitemoptions.add(new SeparatorMenuItem());
      newitemoptions.add(this.newitem_overseasquote);
      newitemoptions.add(this.newitem_overseasorder);
    } else if (NewWorkFlow.get().getStoredUser().getAccessLevel() == 6) {
      MenuItem newitem = new MenuItem("<u>N</u>ew", Resources.ICONS.document_new());
      newitem.setHideOnClick(false);
      filemenu.add(newitem);
      
      Menu newitemoptions = new Menu();
      newitem.setSubMenu(newitemoptions);
      
      newitemoptions.add(this.newitem_container_invoice);
    }
    

    this.filebutton.setMenu(filemenu);
    
    MenuItem manageitem = new MenuItem("<u>M</u>anage", Resources.ICONS.application_view_columns());
    manageitem.setHideOnClick(false);
    filemenu.add(manageitem);
    
    Menu manageitemoptions = new Menu();
    manageitem.setSubMenu(manageitemoptions);
    
    if ((NewWorkFlow.get().getStoredUser().getAccessLevel() == 1) || (NewWorkFlow.get().getStoredUser().getAccessLevel() == 2) || (NewWorkFlow.get().getStoredUser().getAccessLevel() == 3) || (NewWorkFlow.get().getStoredUser().getAccessLevel() == 5)) {
      manageitemoptions.add(this.manageitem_quotesorders);
      
      if ((NewWorkFlow.get().getStoredUser().getAccessLevel() == 1) || (NewWorkFlow.get().getStoredUser().getAccessLevel() == 2) || (NewWorkFlow.get().getStoredUser().getAccessLevel() == 5)) {
        manageitemoptions.add(this.manageitem_completedorders);
        manageitemoptions.add(this.manageitem_potentialrepeatorders);
      }
      
      manageitemoptions.add(this.manageitem_lostquotesorders);
      
      if ((NewWorkFlow.get().getStoredUser().getAccessLevel() == 1) || (NewWorkFlow.get().getStoredUser().getAccessLevel() == 2) || (NewWorkFlow.get().getStoredUser().getAccessLevel() == 5)) {
        manageitemoptions.add(new SeparatorMenuItem());
        manageitemoptions.add(this.manageitem_virtualsample);
        manageitemoptions.add(this.manageitem_completedvirtualsample);
      }
    }
    else if (NewWorkFlow.get().getStoredUser().getAccessLevel() == 4) {
      manageitemoptions.add(this.manageitem_onlinegather_newquotesorders);
      manageitemoptions.add(this.manageitem_onlinegather_exportedquotesorders);
      manageitemoptions.add(new SeparatorMenuItem());
      manageitemoptions.add(this.manageitem_onlinegather_newvirtualsamples);
      manageitemoptions.add(this.manageitem_onlinegather_exportedvirtualsamples);
    } else if (NewWorkFlow.get().getStoredUser().getAccessLevel() == 6) {
      manageitemoptions.add(this.manageitem_container_bookingnoticereceived);
      manageitemoptions.add(this.manageitem_container_intransit);
      manageitemoptions.add(this.manageitem_container_pendingdelivery);
      manageitemoptions.add(this.manageitem_container_currentlyreceving);
      manageitemoptions.add(this.manageitem_container_shipmentsreceived);
    }
    

    if ((NewWorkFlow.get().getStoredUser().getAccessLevel() == 2) || (NewWorkFlow.get().getStoredUser().getAccessLevel() == 5)) {
      MenuItem salesreportitem = new MenuItem("<u>S</u>ales Report", Resources.ICONS.report());
      filemenu.add(salesreportitem);
      
      Menu salesreportoptions = new Menu();
      salesreportitem.setSubMenu(salesreportoptions);
      
      salesreportoptions.add(this.salesreport_report1);
      salesreportoptions.add(this.salesreport_report2);
      salesreportoptions.add(this.salesreport_report3);
      salesreportoptions.add(this.salesreport_report4);
      salesreportoptions.add(this.salesreport_report5);
      salesreportoptions.add(this.salesreport_report6);
    }
    
    if ((NewWorkFlow.get().getStoredUser().getAccessLevel() == 2) || (NewWorkFlow.get().getStoredUser().getAccessLevel() == 5)) {
      filemenu.add(new SeparatorMenuItem());
      filemenu.add(this.importitem);
    }
    filemenu.add(new SeparatorMenuItem());
    filemenu.add(this.logoutitem);
    this.toptoolbar.add(new com.extjs.gxt.ui.client.widget.toolbar.FillToolItem());
    this.toptoolbar.add(this.logoutbutton);
  }
  
  public void resetTopToolBar()
  {
    if (this.filebutton.getMenu() == null) {
      setupFileMenu();
    }
    
    for (int i = 0; i < this.toptoolbar.getItemCount(); i++) {
      if ((!this.toptoolbar.getItem(i).equals(this.filebutton)) && (!this.toptoolbar.getItem(i).equals(this.logoutbutton))) {
        this.toptoolbar.remove(this.toptoolbar.getItem(i));
        i--;
      }
    }
    this.toptoolbar.insert(new com.extjs.gxt.ui.client.widget.toolbar.FillToolItem(), 1);
  }
  
  public ToolBar getTopToolBar()
  {
    return this.toptoolbar;
  }
  
  public void emptyMainScreen() {
    if (this.mainpanel != null) {
      this.mainpanel.removeFromParent();
      this.mainpanel = null;
    }
    NewWorkFlow.get().getMainTabPanel().getMainTab().setWindowHeader("WorkFlow Program - Main Screen");
  }
  
  private void setMainScreen(LayoutContainer layoutcontainer) {
    if (this.mainpanel != null) {
      remove(this.mainpanel);
      this.mainpanel = null;
    }
    this.mainpanel = layoutcontainer;
    add(this.mainpanel, new com.extjs.gxt.ui.client.widget.layout.RowData(1.0D, 1.0D));
    layout();
  }
  
  public LayoutContainer getMainPanel() {
    return this.mainpanel;
  }
  
  public void setManageOrderScreen() {
    setMainScreen(new ManageOrderScreen(ManageOrderScreen.ManageType.QUOTESORDERS));
  }
  
  public void setOnlineGatherScreen() {
    setMainScreen(new OnlineGatherScreen(OnlineGatherScreen.ManageType.NEWQUOTESORDERS));
  }
  
  public void setContainerScreen() {
    setMainScreen(new ContainerScreen(ContainerScreen.ManageType.BOOKINGNOTICERECEIVED));
  }
  
  public void setInstantQuoteSelectionScreen() {
    setMainScreen(new com.ottocap.NewWorkFlow.client.OnlineWorkflow.InstantQuoteSelectionScreen());
  }
  

  private com.extjs.gxt.ui.client.event.SelectionListener<MenuEvent> menulistener = new com.extjs.gxt.ui.client.event.SelectionListener()
  {
    public void componentSelected(MenuEvent ce) {
      if (ce.getItem().equals(MainScreen.this.newitem_domesticquote)) {
        OrderData myorderdata = new OrderData();
        myorderdata.setOrderType(OrderData.OrderType.DOMESTIC);
        myorderdata.setOrderStatus("New Quotation");
        myorderdata.setQuoteToOrder("quote");
        OrderTab myordertab = new OrderTab(myorderdata);
        NewWorkFlow.get().getMainTabPanel().add(myordertab);
        NewWorkFlow.get().getMainTabPanel().setSelection(myordertab);
      } else if (ce.getItem().equals(MainScreen.this.newitem_domesticorder)) {
        OrderData myorderdata = new OrderData();
        myorderdata.setOrderType(OrderData.OrderType.DOMESTIC);
        myorderdata.setOrderStatus("New Order");
        OrderTab myordertab = new OrderTab(myorderdata);
        NewWorkFlow.get().getMainTabPanel().add(myordertab);
        NewWorkFlow.get().getMainTabPanel().setSelection(myordertab);
      } else if (ce.getItem().equals(MainScreen.this.newitem_overseasquote)) {
        OrderData myorderdata = new OrderData();
        myorderdata.setOrderType(OrderData.OrderType.OVERSEAS);
        myorderdata.setOrderStatus("New Quotation");
        myorderdata.setQuoteToOrder("quote");
        OrderTab myordertab = new OrderTab(myorderdata);
        NewWorkFlow.get().getMainTabPanel().add(myordertab);
        NewWorkFlow.get().getMainTabPanel().setSelection(myordertab);
      } else if (ce.getItem().equals(MainScreen.this.newitem_overseasorder)) {
        OrderData myorderdata = new OrderData();
        myorderdata.setOrderType(OrderData.OrderType.OVERSEAS);
        myorderdata.setOrderStatus("New Order");
        OrderTab myordertab = new OrderTab(myorderdata);
        NewWorkFlow.get().getMainTabPanel().add(myordertab);
        NewWorkFlow.get().getMainTabPanel().setSelection(myordertab);
      } else if (ce.getItem().equals(MainScreen.this.importitem)) {
        new ImportOrder();
      } else if (ce.getItem().equals(MainScreen.this.manageitem_quotesorders)) {
        MainScreen.this.setMainScreen(new ManageOrderScreen(ManageOrderScreen.ManageType.QUOTESORDERS));
      } else if (ce.getItem().equals(MainScreen.this.manageitem_completedorders)) {
        MainScreen.this.setMainScreen(new ManageOrderScreen(ManageOrderScreen.ManageType.COMPLETEDORDERS));
      } else if (ce.getItem().equals(MainScreen.this.manageitem_potentialrepeatorders)) {
        MainScreen.this.setMainScreen(new ManageOrderScreen(ManageOrderScreen.ManageType.POTENTIALREPEAT));
      } else if (ce.getItem().equals(MainScreen.this.manageitem_lostquotesorders)) {
        MainScreen.this.setMainScreen(new ManageOrderScreen(ManageOrderScreen.ManageType.LOSTQUOTEORDERS));
      } else if (ce.getItem().equals(MainScreen.this.manageitem_virtualsample)) {
        MainScreen.this.setMainScreen(new ManageOrderScreen(ManageOrderScreen.ManageType.VIRTUALSAMPLES));
      } else if (ce.getItem().equals(MainScreen.this.manageitem_completedvirtualsample)) {
        MainScreen.this.setMainScreen(new ManageOrderScreen(ManageOrderScreen.ManageType.COMPLETEDVIRTUALSAMPLES));
      } else if (ce.getItem().equals(MainScreen.this.salesreport_report1)) {
        MainScreen.this.setMainScreen(new SalesReportScreen(SalesReportScreen.ReportType.REPORT1));
      } else if (ce.getItem().equals(MainScreen.this.salesreport_report2)) {
        MainScreen.this.setMainScreen(new SalesReportScreen(SalesReportScreen.ReportType.REPORT2));
      } else if (ce.getItem().equals(MainScreen.this.salesreport_report3)) {
        MainScreen.this.setMainScreen(new SalesReportScreen(SalesReportScreen.ReportType.REPORT3));
      } else if (ce.getItem().equals(MainScreen.this.salesreport_report4)) {
        MainScreen.this.setMainScreen(new SalesReportScreen(SalesReportScreen.ReportType.REPORT4));
      } else if (ce.getItem().equals(MainScreen.this.salesreport_report5)) {
        MainScreen.this.setMainScreen(new SalesReportScreen(SalesReportScreen.ReportType.REPORT5));
      } else if (ce.getItem().equals(MainScreen.this.salesreport_report6)) {
        MainScreen.this.setMainScreen(new SalesReportScreen(SalesReportScreen.ReportType.REPORT6));
      } else if (ce.getItem().equals(MainScreen.this.manageitem_onlinegather_newquotesorders)) {
        MainScreen.this.setMainScreen(new OnlineGatherScreen(OnlineGatherScreen.ManageType.NEWQUOTESORDERS));
      } else if (ce.getItem().equals(MainScreen.this.manageitem_onlinegather_exportedquotesorders)) {
        MainScreen.this.setMainScreen(new OnlineGatherScreen(OnlineGatherScreen.ManageType.EXPORTEDQUOTESORDERS));
      } else if (ce.getItem().equals(MainScreen.this.manageitem_onlinegather_newvirtualsamples)) {
        MainScreen.this.setMainScreen(new OnlineGatherScreen(OnlineGatherScreen.ManageType.NEWVIRTUALSAMPLES));
      } else if (ce.getItem().equals(MainScreen.this.manageitem_onlinegather_exportedvirtualsamples)) {
        MainScreen.this.setMainScreen(new OnlineGatherScreen(OnlineGatherScreen.ManageType.EXPORTEDVIRTUALSAMPLES));
      } else if (ce.getItem().equals(MainScreen.this.logoutitem)) {
        NewWorkFlow.get().doLogOut();
      } else if (ce.getItem().equals(MainScreen.this.manageitem_container_bookingnoticereceived)) {
        MainScreen.this.setMainScreen(new ContainerScreen(ContainerScreen.ManageType.BOOKINGNOTICERECEIVED));
      } else if (ce.getItem().equals(MainScreen.this.manageitem_container_intransit)) {
        MainScreen.this.setMainScreen(new ContainerScreen(ContainerScreen.ManageType.INTRAINSIT));
      } else if (ce.getItem().equals(MainScreen.this.manageitem_container_pendingdelivery)) {
        MainScreen.this.setMainScreen(new ContainerScreen(ContainerScreen.ManageType.PENDINGDELIVERY));
      } else if (ce.getItem().equals(MainScreen.this.manageitem_container_currentlyreceving)) {
        MainScreen.this.setMainScreen(new ContainerScreen(ContainerScreen.ManageType.CURRENTLYRECEVING));
      } else if (ce.getItem().equals(MainScreen.this.manageitem_container_shipmentsreceived)) {
        MainScreen.this.setMainScreen(new ContainerScreen(ContainerScreen.ManageType.SHIPMENTSRECEIVED));
      } else if (ce.getItem().equals(MainScreen.this.newitem_container_invoice)) {
        com.ottocap.NewWorkFlow.client.ContainerInvoice.ContainerData.ContainerData mycontainerinvoicedata = new com.ottocap.NewWorkFlow.client.ContainerInvoice.ContainerData.ContainerData();
        com.ottocap.NewWorkFlow.client.ContainerInvoice.ContainerInvoiceTab mycontainerinvoicetab = new com.ottocap.NewWorkFlow.client.ContainerInvoice.ContainerInvoiceTab(mycontainerinvoicedata);
        NewWorkFlow.get().getMainTabPanel().add(mycontainerinvoicetab);
        NewWorkFlow.get().getMainTabPanel().setSelection(mycontainerinvoicetab);
      }
    }
  };
  
  private com.extjs.gxt.ui.client.event.SelectionListener<ButtonEvent> buttonlistener = new com.extjs.gxt.ui.client.event.SelectionListener()
  {
    public void componentSelected(ButtonEvent be) {
      if (be.getButton().equals(MainScreen.this.logoutbutton)) {
        NewWorkFlow.get().doLogOut();
      }
    }
  };
}
