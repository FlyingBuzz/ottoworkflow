package com.ottocap.NewWorkFlow.client.ManageOrders;

import com.extjs.gxt.ui.client.Style.SortDir;
import com.extjs.gxt.ui.client.core.FastMap;
import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.data.BasePagingLoadConfig;
import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.data.SortInfo;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.KeyListener;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.DatePicker;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Status;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.button.ToggleButton;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridSelectionModel;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.extjs.gxt.ui.client.widget.toolbar.SeparatorToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.ottocap.NewWorkFlow.client.EditOrder.OrderTab;
import com.ottocap.NewWorkFlow.client.Icons.ResourceIcons;
import com.ottocap.NewWorkFlow.client.Icons.Resources;
import com.ottocap.NewWorkFlow.client.MainScreen.MainScreen;
import com.ottocap.NewWorkFlow.client.MainScreen.MainTab;
import com.ottocap.NewWorkFlow.client.MainTabPanel;
import com.ottocap.NewWorkFlow.client.NewWorkFlow;
import com.ottocap.NewWorkFlow.client.OrderDataWithStyle;
import com.ottocap.NewWorkFlow.client.Services.WorkFlowServiceAsync;
import com.ottocap.NewWorkFlow.client.StoredUser;
import com.ottocap.NewWorkFlow.client.StyleInformationData;
import com.ottocap.NewWorkFlow.client.Widget.BuzzAsyncCallback;
import java.util.ArrayList;
import java.util.Date;

public class ManageOrderScreen extends LayoutContainer
{
  private BasePagingLoader<BasePagingLoadResult<BaseModelData>> loader;
  private Grid<BaseModelData> grid;
  private BasePagingLoadConfig config = new BasePagingLoadConfig();
  private int maxdisplay = 50;
  private ToggleButton vieweveryonebutton = new ToggleButton("View Everyone", Resources.ICONS.foaf());
  private TextField<String> searchquery = new TextField();
  private DateField startorderdate = new DateField();
  private DateField endorderdate = new DateField();
  private Button configurebutton = new Button("Configure", Resources.ICONS.application_form_edit());
  private Button submitfilterbutton = new Button("Submit", Resources.ICONS.magnifier_left());
  private Button exporttocsv = new Button("Export To CSV", Resources.ICONS.report_excel());
  private PagingToolBar pagingtoolbar = new PagingToolBar(this.maxdisplay);
  private ManageType managetype;
  
  public static enum ManageType { QUOTESORDERS,  COMPLETEDORDERS,  POTENTIALREPEAT,  LOSTQUOTEORDERS,  VIRTUALSAMPLES,  COMPLETEDVIRTUALSAMPLES;
  }
  


  public ManageOrderScreen(ManageType managetype)
  {
    this.startorderdate.getDatePicker().setStartDay(7);
    this.endorderdate.getDatePicker().setStartDay(7);
    
    this.managetype = managetype;
    
    setLayout(new com.extjs.gxt.ui.client.widget.layout.RowLayout());
    setupTopToolBar();
    
    RpcProxy<BasePagingLoadResult<BaseModelData>> proxy = new RpcProxy()
    {
      protected void load(Object loadConfig, final AsyncCallback<BasePagingLoadResult<BaseModelData>> callback)
      {
        ManageOrderScreen.this.configurebutton.disable();
        NewWorkFlow.get().getWorkFlowRPC().getOrderList((com.extjs.gxt.ui.client.data.PagingLoadConfig)loadConfig, new AsyncCallback()
        {
          public void onFailure(Throwable caught)
          {
            NewWorkFlow.get().reLogin2(caught, ManageOrderScreen.this.manageordergridrelogin);
            callback.onFailure(caught);
            ManageOrderScreen.this.pagingtoolbar.enable();
          }
          
          public void onSuccess(BasePagingLoadResult<BaseModelData> result)
          {
            callback.onSuccess(result);
            ManageOrderScreen.this.pagingtoolbar.enable();
            NewWorkFlow.get().getWait().close();
          }
          

        });
      }
      
    };
    this.loader = new BasePagingLoader(proxy);
    this.loader.setRemoteSort(true);
    ListStore<BaseModelData> store = new ListStore(this.loader);
    
    this.pagingtoolbar.bind(this.loader);
    
    ArrayList<ColumnConfig> columns = new ArrayList();
    
    if (managetype == ManageType.QUOTESORDERS) {
      NewWorkFlow.get().getMainTabPanel().getMainTab().setWindowHeader("WorkFlow Program - Manage Quotes & Orders");
      columns.add(new ColumnConfig("ordertype", "Order Type", 100));
      ColumnConfig quotenumber = new ColumnConfig("quotenumber", "Quote Number", 100);
      quotenumber.setHidden(true);
      columns.add(quotenumber);
      ColumnConfig ordernumber = new ColumnConfig("ordernumber", "Order Number", 100);
      ordernumber.setHidden(true);
      columns.add(ordernumber);
      columns.add(new ColumnConfig("quoteordernumber", "Quote/Order Number", 150));
      columns.add(new ColumnConfig("customerpo", "Customer's PO Number", 150));
      columns.add(new ColumnConfig("internalduedatetimecolor", "Status Due Date/Time", 150));
      columns.add(new ColumnConfig("alertdatecolor", "Alert Date", 150));
      columns.add(new ColumnConfig("orderstatus", "Order Status", 100));
      columns.add(new ColumnConfig("company", "Company", 200));
      ColumnConfig orderdate = new ColumnConfig("orderdate", "Order Date", 100);
      orderdate.setDateTimeFormat(DateTimeFormat.getFormat("MM/dd/y"));
      columns.add(orderdate);
      ColumnConfig inhanddate = new ColumnConfig("inhanddate", "In-Hands Date", 100);
      inhanddate.setDateTimeFormat(DateTimeFormat.getFormat("MM/dd/y"));
      columns.add(inhanddate);
      ColumnConfig employeeid = new ColumnConfig("employeeid", "Employee ID", 100);
      employeeid.setHidden(true);
      columns.add(employeeid);
      columns.add(new ColumnConfig("managedby", "Managed By", 100));
      columns.add(new ColumnConfig("nextaction", "Next Action", 200));
      columns.add(new ColumnConfig("totalcapprice", "Total Cap Price", 100));
      columns.add(new ColumnConfig("totaldecorationprice", "Total Decoration Price", 130));
    }
    else if (managetype == ManageType.COMPLETEDORDERS) {
      NewWorkFlow.get().getMainTabPanel().getMainTab().setWindowHeader("WorkFlow Program - Manage Completed Orders");
      columns.add(new ColumnConfig("ordertype", "Order Type", 100));
      ColumnConfig quotenumber = new ColumnConfig("quotenumber", "Quote Number", 100);
      quotenumber.setHidden(true);
      columns.add(quotenumber);
      columns.add(new ColumnConfig("ordernumber", "Order Number", 100));
      ColumnConfig statusduedate = new ColumnConfig("internalduedatetime", "Complete Date", 150);
      statusduedate.setDateTimeFormat(DateTimeFormat.getFormat("MM/dd/y"));
      columns.add(statusduedate);
      columns.add(new ColumnConfig("digitizingvendorname", "Digitizer", 200));
      columns.add(new ColumnConfig("totalcapprice", "Total Cap Price", 100));
      columns.add(new ColumnConfig("totaldecorationprice", "Total Decoration Price", 130));
      columns.add(new ColumnConfig("totalprice", "Total Price", 100));
      columns.add(new ColumnConfig("potentialrepeatstatus", "Potential Projects", 200));
      columns.add(new ColumnConfig("company", "Company", 200));
      ColumnConfig employeeid = new ColumnConfig("employeeid", "Employee ID", 100);
      employeeid.setHidden(true);
      columns.add(employeeid);
      columns.add(new ColumnConfig("managedby", "Managed By", 100));
      columns.add(new ColumnConfig("nextaction", "Last Action", 200));
    }
    else if (managetype == ManageType.POTENTIALREPEAT) {
      NewWorkFlow.get().getMainTabPanel().getMainTab().setWindowHeader("WorkFlow Program - Manage Potential Repeat Orders");
      columns.add(new ColumnConfig("ordertype", "Order Type", 100));
      ColumnConfig quotenumber = new ColumnConfig("quotenumber", "Quote Number", 100);
      quotenumber.setHidden(true);
      columns.add(quotenumber);
      columns.add(new ColumnConfig("ordernumber", "Order Number", 100));
      columns.add(new ColumnConfig("potentialrepeatfrequency", "Potential Projects", 200));
      columns.add(new ColumnConfig("potentialrepeatdate", "Follow-up Date", 150));
      columns.add(new ColumnConfig("company", "Company", 200));
      ColumnConfig orderdate = new ColumnConfig("orderdate", "Order Date", 100);
      orderdate.setDateTimeFormat(DateTimeFormat.getFormat("MM/dd/y"));
      columns.add(orderdate);
      ColumnConfig employeeid = new ColumnConfig("employeeid", "Employee ID", 100);
      employeeid.setHidden(true);
      columns.add(employeeid);
      columns.add(new ColumnConfig("managedby", "Managed By", 100));
      columns.add(new ColumnConfig("potentialinternalcomments", "Repeat Comments", 200));
    }
    else if (managetype == ManageType.LOSTQUOTEORDERS) {
      NewWorkFlow.get().getMainTabPanel().getMainTab().setWindowHeader("WorkFlow Program - Manage Lost Quotes & Orders");
      columns.add(new ColumnConfig("ordertype", "Order Type", 100));
      ColumnConfig quotenumber = new ColumnConfig("quotenumber", "Quote Number", 100);
      quotenumber.setHidden(true);
      columns.add(quotenumber);
      ColumnConfig ordernumber = new ColumnConfig("ordernumber", "Order Number", 100);
      ordernumber.setHidden(true);
      columns.add(ordernumber);
      columns.add(new ColumnConfig("quoteordernumber", "Quote/Order Number", 150));
      ColumnConfig statusduedate = new ColumnConfig("internalduedatetime", "Date/Time Losted", 150);
      statusduedate.setDateTimeFormat(DateTimeFormat.getFormat("MM/dd/y at hh:mm a"));
      columns.add(statusduedate);
      columns.add(new ColumnConfig("orderstatus", "Order Status", 100));
      columns.add(new ColumnConfig("company", "Company", 200));
      ColumnConfig orderdate = new ColumnConfig("orderdate", "Order Date", 100);
      orderdate.setDateTimeFormat(DateTimeFormat.getFormat("MM/dd/y"));
      columns.add(orderdate);
      ColumnConfig employeeid = new ColumnConfig("employeeid", "Employee ID", 100);
      employeeid.setHidden(true);
      columns.add(employeeid);
      columns.add(new ColumnConfig("managedby", "Managed By", 100));
      columns.add(new ColumnConfig("nextaction", "Last Action", 200));
    }
    else if (managetype == ManageType.VIRTUALSAMPLES) {
      NewWorkFlow.get().getMainTabPanel().getMainTab().setWindowHeader("WorkFlow Program - Manage Virtual Samples");
      columns.add(new ColumnConfig("ordertype", "Order Type", 100));
      columns.add(new ColumnConfig("quotenumber", "VS Number", 100));
      columns.add(new ColumnConfig("customerpo", "Customer's PO Number", 150));
      columns.add(new ColumnConfig("internalduedatetimecolor", "Status Due Date/Time", 150));
      columns.add(new ColumnConfig("orderstatus", "Virtual Sample Status", 150));
      columns.add(new ColumnConfig("company", "Company", 200));
      ColumnConfig orderdate = new ColumnConfig("orderdate", "Request Date", 100);
      orderdate.setDateTimeFormat(DateTimeFormat.getFormat("MM/dd/y"));
      columns.add(orderdate);
      ColumnConfig employeeid = new ColumnConfig("employeeid", "Employee ID", 100);
      employeeid.setHidden(true);
      columns.add(employeeid);
      columns.add(new ColumnConfig("managedby", "Managed By", 100));
      columns.add(new ColumnConfig("nextaction", "Next Action", 200));
    }
    else if (managetype == ManageType.COMPLETEDVIRTUALSAMPLES) {
      NewWorkFlow.get().getMainTabPanel().getMainTab().setWindowHeader("WorkFlow Program - Manage Completed Virtual Samples");
      columns.add(new ColumnConfig("ordertype", "Order Type", 100));
      columns.add(new ColumnConfig("quotenumber", "VS Number", 100));
      columns.add(new ColumnConfig("customerpo", "Customer's PO Number", 150));
      ColumnConfig statusduedate = new ColumnConfig("internalduedatetime", "Date Completed", 100);
      statusduedate.setDateTimeFormat(DateTimeFormat.getFormat("MM/dd/y"));
      columns.add(statusduedate);
      columns.add(new ColumnConfig("company", "Company", 200));
      ColumnConfig orderdate = new ColumnConfig("orderdate", "Request Date", 100);
      orderdate.setDateTimeFormat(DateTimeFormat.getFormat("MM/dd/y"));
      columns.add(orderdate);
      ColumnConfig employeeid = new ColumnConfig("employeeid", "Employee ID", 100);
      employeeid.setHidden(true);
      columns.add(employeeid);
      columns.add(new ColumnConfig("managedby", "Managed By", 100));
      columns.add(new ColumnConfig("nextaction", "Next Action", 200));
    }
    
    ColumnModel cm = new ColumnModel(columns);
    
    this.grid = new Grid(store, cm);
    this.grid.setView(new com.extjs.gxt.ui.client.widget.grid.BufferView());
    this.grid.setLoadMask(true);
    


    this.grid.addListener(Events.RowClick, this.rowclicklistener);
    this.grid.addListener(Events.RowDoubleClick, this.rowdoubleclicklistener);
    this.grid.getSelectionModel().setSelectionMode(com.extjs.gxt.ui.client.Style.SelectionMode.SINGLE);
    
    initConfig();
    add(this.grid, new RowData(1.0D, 1.0D));
    add(this.pagingtoolbar, new RowData(1.0D, -1.0D));
  }
  
  Listener<GridEvent<BaseModelData>> rowdoubleclicklistener = new Listener()
  {
    public void handleEvent(GridEvent<BaseModelData> be)
    {
      if (be.getSource().equals(ManageOrderScreen.this.grid)) {
        boolean foundexisting = false;
        for (com.extjs.gxt.ui.client.widget.TabItem currentitem : NewWorkFlow.get().getMainTabPanel().getItems()) {
          if (((currentitem instanceof OrderTab)) && 
            (((OrderTab)currentitem).getHiddenKey() != null) && (((OrderTab)currentitem).getHiddenKey().intValue() == ((Integer)((BaseModelData)be.getGrid().getSelectionModel().getSelectedItem()).get("quotenumber")).intValue())) {
            foundexisting = true;
            NewWorkFlow.get().getMainTabPanel().setSelection(currentitem);
            break;
          }
        }
        
        if (!foundexisting) {
          NewWorkFlow.get().getWait().show();
          NewWorkFlow.get().getWait().getDialog().center();
          NewWorkFlow.get().getWorkFlowRPC().getOrderWithStyle(((Integer)((BaseModelData)be.getGrid().getSelectionModel().getSelectedItem()).get("quotenumber")).intValue(), ManageOrderScreen.this.getordercallback);
        }
      }
    }
  };
  

  private BuzzAsyncCallback<OrderDataWithStyle> getordercallback = new BuzzAsyncCallback()
  {
    public void onFailure(Throwable caught)
    {
      if (caught.getMessage().equals("This Order Does Not Belong To You")) {
        MessageBox.confirm("Copy Order", "Do you want to copy the order?", ManageOrderScreen.this.copyordercallback);
      } else {
        NewWorkFlow.get().reLogin2(caught, ManageOrderScreen.this.getordercallback);
      }
      NewWorkFlow.get().getWait().close();
    }
    

    public void doRetry()
    {
      NewWorkFlow.get().getWorkFlowRPC().getOrderWithStyle(((Integer)((BaseModelData)ManageOrderScreen.this.grid.getSelectionModel().getSelectedItem()).get("quotenumber")).intValue(), ManageOrderScreen.this.getordercallback);
    }
    
    public void onSuccess(OrderDataWithStyle result)
    {
      try {
        for (StyleInformationData currentdata : result.getStyleInfoArray()) {
          NewWorkFlow.get().setStyleStore(currentdata);
        }
        OrderTab mytab = new OrderTab(result.getOrderData());
        NewWorkFlow.get().getMainTabPanel().add(mytab);
        NewWorkFlow.get().getMainTabPanel().setSelection(mytab);
      } catch (Exception e) {
        e.printStackTrace();
      }
      NewWorkFlow.get().getWait().close();
    }
  };
  

  private Listener<MessageBoxEvent> copyordercallback = new Listener()
  {
    public void handleEvent(MessageBoxEvent be)
    {
      if (be.getButtonClicked().getHtml().equals("Yes")) {
        int ordertocopy = ((Integer)((BaseModelData)ManageOrderScreen.this.grid.getSelectionModel().getSelectedItem()).get("quotenumber")).intValue();
        NewWorkFlow.get().getWorkFlowRPC().copyOrderByKey(ordertocopy, ManageOrderScreen.this.getordercallback);
      }
    }
  };
  


  Listener<GridEvent<BaseModelData>> rowclicklistener = new Listener()
  {
    public void handleEvent(GridEvent<BaseModelData> be)
    {
      if (be.getSource().equals(ManageOrderScreen.this.grid)) {
        ManageOrderScreen.this.configurebutton.enable();
      }
    }
  };
  
  public void initConfig()
  {
    this.config.setOffset(0);
    this.config.setLimit(this.maxdisplay);
    this.config.set("viewall", Boolean.valueOf(false));
    this.config.set("searchquery", "");
    this.config.set("orderfrom", (Date)this.startorderdate.getValue());
    this.config.set("orderto", (Date)this.endorderdate.getValue());
    
    if (this.managetype == ManageType.QUOTESORDERS) {
      this.config.setSortInfo(new SortInfo("internalduedatetimecolor", Style.SortDir.ASC));
      this.config.set("managetype", "quotesorders");
    } else if (this.managetype == ManageType.COMPLETEDORDERS) {
      this.config.setSortInfo(new SortInfo("internalduedatetime", Style.SortDir.DESC));
      this.config.set("managetype", "completedorders");
    } else if (this.managetype == ManageType.POTENTIALREPEAT) {
      this.config.setSortInfo(new SortInfo("potentialrepeatdate", Style.SortDir.ASC));
      this.config.set("managetype", "potentialrepeat");
    } else if (this.managetype == ManageType.LOSTQUOTEORDERS) {
      this.config.setSortInfo(new SortInfo("internalduedatetime", Style.SortDir.DESC));
      this.config.set("managetype", "lostquoteorders");
    } else if (this.managetype == ManageType.VIRTUALSAMPLES) {
      this.config.setSortInfo(new SortInfo("internalduedatetimecolor", Style.SortDir.ASC));
      this.config.set("managetype", "virtualsamples");
    } else if (this.managetype == ManageType.COMPLETEDVIRTUALSAMPLES) {
      this.config.setSortInfo(new SortInfo("internalduedatetime", Style.SortDir.DESC));
      this.config.set("managetype", "completedvirtualsamples");
    }
    
    this.loader.useLoadConfig(this.config);
    this.loader.load();
  }
  
  public void refleshLoader()
  {
    if (this.loader != null) {
      this.loader.load();
    }
  }
  
  private void setupTopToolBar() {
    NewWorkFlow.get().getMainTabPanel().getMainTab().getMainScreen().resetTopToolBar();
    ToolBar toolbar = NewWorkFlow.get().getMainTabPanel().getMainTab().getMainScreen().getTopToolBar();
    Status startdate = new Status();
    startdate.setText("Order Date From:");
    Status enddate = new Status();
    enddate.setText("Order Date To:");
    Status searchlabel = new Status();
    searchlabel.setText("Search:");
    this.configurebutton.disable();
    


    LayoutContainer startdatelayout = new LayoutContainer();
    startdatelayout.add(this.startorderdate);
    startdatelayout.setWidth(165);
    
    LayoutContainer enddatelayout = new LayoutContainer();
    enddatelayout.add(this.endorderdate);
    enddatelayout.setWidth(165);
    
    toolbar.insert(this.exporttocsv, 1);
    toolbar.insert(new SeparatorToolItem(), 1);
    
    if ((NewWorkFlow.get().getStoredUser().getAccessLevel() == 2) || (NewWorkFlow.get().getStoredUser().getAccessLevel() == 5)) {
      toolbar.insert(this.configurebutton, 1);
      toolbar.insert(new SeparatorToolItem(), 1);
    }
    toolbar.insert(this.submitfilterbutton, 1);
    toolbar.insert(enddatelayout, 1);
    toolbar.insert(enddate, 1);
    toolbar.insert(startdatelayout, 1);
    toolbar.insert(startdate, 1);
    toolbar.insert(this.searchquery, 1);
    toolbar.insert(searchlabel, 1);
    toolbar.insert(new SeparatorToolItem(), 1);
    
    if ((NewWorkFlow.get().getStoredUser().getAccessLevel() == 1) || (NewWorkFlow.get().getStoredUser().getAccessLevel() == 2) || (NewWorkFlow.get().getStoredUser().getAccessLevel() == 5)) {
      toolbar.insert(this.vieweveryonebutton, 1);
      toolbar.insert(new SeparatorToolItem(), 1);
    }
    
    this.submitfilterbutton.addSelectionListener(this.selectionlistener);
    this.vieweveryonebutton.addSelectionListener(this.selectionlistener);
    this.searchquery.addListener(Events.Change, this.changelistener);
    this.startorderdate.addListener(Events.Change, this.changelistener);
    this.endorderdate.addListener(Events.Change, this.changelistener);
    this.configurebutton.addSelectionListener(this.selectionlistener);
    this.exporttocsv.addSelectionListener(this.selectionlistener);
    this.searchquery.addKeyListener(this.keylistener);
    this.searchquery.addListener(Events.Blur, this.blurlistener);
  }
  

  private KeyListener keylistener = new KeyListener()
  {
    public void componentKeyUp(ComponentEvent event)
    {
      if ((event.getSource().equals(ManageOrderScreen.this.searchquery)) && 
        (event.getKeyCode() == 13)) {
        ManageOrderScreen.this.config.set("searchquery", (String)ManageOrderScreen.this.searchquery.getValue());
        if (ManageOrderScreen.this.loader != null) {
          ManageOrderScreen.this.loader.load();
        }
      }
    }
  };
  

  private Listener<BaseEvent> blurlistener = new Listener()
  {
    public void handleEvent(BaseEvent be)
    {
      if ((be.getSource().equals(ManageOrderScreen.this.searchquery)) && 
        (ManageOrderScreen.this.loader != null)) {
        ManageOrderScreen.this.loader.load();
      }
    }
  };
  


  private Listener<BaseEvent> changelistener = new Listener()
  {
    public void handleEvent(BaseEvent be)
    {
      if (be.getSource().equals(ManageOrderScreen.this.searchquery)) {
        ManageOrderScreen.this.config.set("searchquery", (String)ManageOrderScreen.this.searchquery.getValue());
      }
      else if ((be.getSource().equals(ManageOrderScreen.this.startorderdate)) || (be.getSource().equals(ManageOrderScreen.this.endorderdate))) {
        if ((ManageOrderScreen.this.startorderdate.getValue() != null) && (ManageOrderScreen.this.endorderdate.getValue() != null) && (((Date)ManageOrderScreen.this.startorderdate.getValue()).after((Date)ManageOrderScreen.this.endorderdate.getValue()))) {
          Date tempdate = (Date)ManageOrderScreen.this.startorderdate.getValue();
          ManageOrderScreen.this.startorderdate.setValue((Date)ManageOrderScreen.this.endorderdate.getValue());
          ManageOrderScreen.this.endorderdate.setValue(tempdate);
          ManageOrderScreen.this.config.set("orderfrom", (Date)ManageOrderScreen.this.startorderdate.getValue());
          ManageOrderScreen.this.config.set("orderto", (Date)ManageOrderScreen.this.endorderdate.getValue());
        }
        else if (be.getSource().equals(ManageOrderScreen.this.startorderdate)) {
          ManageOrderScreen.this.config.set("orderfrom", (Date)ManageOrderScreen.this.startorderdate.getValue());
        } else if (be.getSource().equals(ManageOrderScreen.this.endorderdate)) {
          ManageOrderScreen.this.config.set("orderto", (Date)ManageOrderScreen.this.endorderdate.getValue());
        }
      }
    }
  };
  


  private SelectionListener<ButtonEvent> selectionlistener = new SelectionListener()
  {
    public void componentSelected(ButtonEvent ce)
    {
      if (ce.getSource().equals(ManageOrderScreen.this.vieweveryonebutton)) {
        ManageOrderScreen.this.config.set("viewall", Boolean.valueOf(ManageOrderScreen.this.vieweveryonebutton.isPressed()));
        ManageOrderScreen.this.loader.load();
      } else if (ce.getSource().equals(ManageOrderScreen.this.configurebutton)) {
        new OrderConfig(((Integer)((BaseModelData)ManageOrderScreen.this.grid.getSelectionModel().getSelectedItem()).get("quotenumber")).intValue(), ManageOrderScreen.this.orderconfigcallback);
      } else if (ce.getSource().equals(ManageOrderScreen.this.submitfilterbutton)) {
        ManageOrderScreen.this.loader.load();
      } else if (ce.getSource().equals(ManageOrderScreen.this.exporttocsv)) {
        String variablesstring = "";
        if (ManageOrderScreen.this.config.get("managetype") != null) {
          variablesstring = variablesstring + "&managetype=" + ManageOrderScreen.this.config.get("managetype");
        }
        if (ManageOrderScreen.this.config.get("searchquery") != null) {
          variablesstring = variablesstring + "&searchquery=" + ManageOrderScreen.this.config.get("searchquery");
        }
        if (ManageOrderScreen.this.config.get("orderfrom") != null) {
          variablesstring = variablesstring + "&orderfrom=" + ((Date)ManageOrderScreen.this.config.get("orderfrom")).getTime();
        }
        if (ManageOrderScreen.this.config.get("orderto") != null) {
          variablesstring = variablesstring + "&orderto=" + ((Date)ManageOrderScreen.this.config.get("orderto")).getTime();
        }
        if (ManageOrderScreen.this.config.getSortField() != null) {
          variablesstring = variablesstring + "&sortfield=" + ManageOrderScreen.this.config.getSortField();
        }
        if (ManageOrderScreen.this.config.getSortField() != null) {
          variablesstring = variablesstring + "&sortdir=" + (ManageOrderScreen.this.config.getSortDir() == Style.SortDir.ASC ? "ASC" : "DESC");
        }
        variablesstring = variablesstring + "&viewall=" + (ManageOrderScreen.this.vieweveryonebutton.isPressed() ? "TRUE" : "FALSE");
        Window.open(NewWorkFlow.baseurl + "outputcsv/?page=manageorders" + variablesstring, "_blank", "");
      }
    }
  };
  

  private AsyncCallback<FastMap<Object>> orderconfigcallback = new AsyncCallback()
  {
    public void onFailure(Throwable caught) {}
    


    public void onSuccess(FastMap<Object> result)
    {
      for (BaseModelData currentmodel : ManageOrderScreen.this.grid.getStore().getModels()) {
        if (((Integer)currentmodel.get("quotenumber")).intValue() == ((Integer)result.get("hiddenkey")).intValue()) {
          currentmodel.set("employeeid", result.get("employeeid"));
          currentmodel.set("managedby", result.get("managedby"));
          ManageOrderScreen.this.grid.getStore().update(currentmodel);
        }
      }
    }
  };
  


  private BuzzAsyncCallback<String> manageordergridrelogin = new BuzzAsyncCallback()
  {
    public void onFailure(Throwable caught) {}
    


    public void onSuccess(String result)
    {
      ManageOrderScreen.this.loader.load();
    }
    
    public void doRetry()
    {
      ManageOrderScreen.this.loader.load();
    }
  };
}
