package com.ottocap.NewWorkFlow.client.ContainerInvoice;

import com.extjs.gxt.ui.client.Style.SortDir;
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
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.DatePicker;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.Status;
import com.extjs.gxt.ui.client.widget.button.Button;
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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.ottocap.NewWorkFlow.client.Icons.ResourceIcons;
import com.ottocap.NewWorkFlow.client.Icons.Resources;
import com.ottocap.NewWorkFlow.client.MainScreen.MainScreen;
import com.ottocap.NewWorkFlow.client.MainScreen.MainTab;
import com.ottocap.NewWorkFlow.client.MainTabPanel;
import com.ottocap.NewWorkFlow.client.NewWorkFlow;
import com.ottocap.NewWorkFlow.client.Services.WorkFlowServiceAsync;
import com.ottocap.NewWorkFlow.client.Widget.BuzzAsyncCallback;
import java.util.ArrayList;
import java.util.Date;

public class ContainerScreen extends LayoutContainer
{
  private BasePagingLoader<BasePagingLoadResult<BaseModelData>> loader;
  private Grid<BaseModelData> grid;
  private BasePagingLoadConfig config = new BasePagingLoadConfig();
  private int maxdisplay = 50;
  private TextField<String> searchquery = new TextField();
  private DateField startorderdate = new DateField();
  private DateField endorderdate = new DateField();
  private Button submitfilterbutton = new Button("Submit", Resources.ICONS.magnifier_left());
  private Button exporttocsv = new Button("Export To CSV", Resources.ICONS.report_excel());
  private PagingToolBar pagingtoolbar = new PagingToolBar(this.maxdisplay);
  private ManageType managetype;
  
  public static enum ManageType { BOOKINGNOTICERECEIVED,  INTRAINSIT,  PENDINGDELIVERY,  CURRENTLYRECEVING,  SHIPMENTSRECEIVED;
  }
  


  public ContainerScreen(ManageType managetype)
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
        NewWorkFlow.get().getWorkFlowRPC().getContainerInvoiceList((com.extjs.gxt.ui.client.data.PagingLoadConfig)loadConfig, new AsyncCallback()
        {
          public void onFailure(Throwable caught)
          {
            NewWorkFlow.get().reLogin2(caught, ContainerScreen.this.manageordergridrelogin);
            callback.onFailure(caught);
            ContainerScreen.this.pagingtoolbar.enable();
          }
          
          public void onSuccess(BasePagingLoadResult<BaseModelData> result)
          {
            callback.onSuccess(result);
            ContainerScreen.this.pagingtoolbar.enable();
          }
          

        });
      }
      
    };
    this.loader = new BasePagingLoader(proxy);
    this.loader.setRemoteSort(true);
    ListStore<BaseModelData> store = new ListStore(this.loader);
    
    this.pagingtoolbar.bind(this.loader);
    
    ArrayList<ColumnConfig> columns = new ArrayList();
    
    if (managetype == ManageType.BOOKINGNOTICERECEIVED) {
      NewWorkFlow.get().getMainTabPanel().getMainTab().setWindowHeader("WorkFlow Program - Booking Notice Received");
      columns.add(new ColumnConfig("ordertype", "Order Type", 100));
      columns.add(new ColumnConfig("quotenumber", "Quote Number", 150));
      columns.add(new ColumnConfig("customerpo", "Customer's PO Number", 150));
      columns.add(new ColumnConfig("company", "Company", 250));
      columns.add(new ColumnConfig("orderstatus", "Order Status", 150));
      columns.add(new ColumnConfig("orderdatetime", "Order Date/Time", 150));
    }
    else if (managetype == ManageType.INTRAINSIT) {
      NewWorkFlow.get().getMainTabPanel().getMainTab().setWindowHeader("WorkFlow Program - In Transit, Pending Arrival at Port");
      columns.add(new ColumnConfig("ordertype", "Order Type", 100));
      columns.add(new ColumnConfig("quotenumber", "Quote Number", 150));
      columns.add(new ColumnConfig("customerpo", "Customer's PO Number", 150));
      columns.add(new ColumnConfig("company", "Company", 250));
      columns.add(new ColumnConfig("orderstatus", "Order Status", 150));
      columns.add(new ColumnConfig("orderdatetime", "Order Date/Time", 150));
    }
    else if (managetype == ManageType.PENDINGDELIVERY) {
      NewWorkFlow.get().getMainTabPanel().getMainTab().setWindowHeader("WorkFlow Program - Released from Port, Pending Delivery");
      columns.add(new ColumnConfig("ordertype", "Order Type", 100));
      columns.add(new ColumnConfig("quotenumber", "Quote Number", 150));
      columns.add(new ColumnConfig("customerpo", "Customer's PO Number", 150));
      columns.add(new ColumnConfig("company", "Company", 250));
      columns.add(new ColumnConfig("orderdatetime", "Order Date/Time", 150));
    }
    else if (managetype == ManageType.CURRENTLYRECEVING) {
      NewWorkFlow.get().getMainTabPanel().getMainTab().setWindowHeader("WorkFlow Program - Otto Warehouse Currently Receiving");
      columns.add(new ColumnConfig("ordertype", "Order Type", 100));
      columns.add(new ColumnConfig("quotenumber", "Quote Number", 150));
      columns.add(new ColumnConfig("customerpo", "Customer's PO Number", 150));
      columns.add(new ColumnConfig("company", "Company", 250));
      columns.add(new ColumnConfig("orderdatetime", "Order Date/Time", 150));
    }
    else if (managetype == ManageType.SHIPMENTSRECEIVED) {
      NewWorkFlow.get().getMainTabPanel().getMainTab().setWindowHeader("WorkFlow Program - Shipments Received");
      columns.add(new ColumnConfig("ordertype", "Order Type", 100));
      columns.add(new ColumnConfig("quotenumber", "Quote Number", 150));
      columns.add(new ColumnConfig("customerpo", "Customer's PO Number", 150));
      columns.add(new ColumnConfig("company", "Company", 250));
      columns.add(new ColumnConfig("orderdatetime", "Order Date/Time", 150));
    }
    

    ColumnModel cm = new ColumnModel(columns);
    
    this.grid = new Grid(store, cm);
    this.grid.setView(new com.extjs.gxt.ui.client.widget.grid.BufferView());
    this.grid.setLoadMask(true);
    


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
      if (be.getSource().equals(ContainerScreen.this.grid))
      {


        int hiddenkey = ((Integer)((BaseModelData)be.getGrid().getSelectionModel().getSelectedItem()).get("quotenumber")).intValue();
        Window.open(NewWorkFlow.baseurl + "exportorder/?hiddenkey=" + hiddenkey + "&managetype=" + ContainerScreen.this.managetype, "_blank", "");
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
    
    if (this.managetype == ManageType.BOOKINGNOTICERECEIVED) {
      this.config.setSortInfo(new SortInfo("orderdatetime", Style.SortDir.ASC));
      this.config.set("managetype", "newquotesorders");
    } else if (this.managetype == ManageType.INTRAINSIT) {
      this.config.setSortInfo(new SortInfo("orderdatetime", Style.SortDir.DESC));
      this.config.set("managetype", "exportedquotesorders");
    } else if (this.managetype == ManageType.PENDINGDELIVERY) {
      this.config.setSortInfo(new SortInfo("orderdatetime", Style.SortDir.ASC));
      this.config.set("managetype", "newvirtualsamples");
    } else if (this.managetype == ManageType.CURRENTLYRECEVING) {
      this.config.setSortInfo(new SortInfo("orderdatetime", Style.SortDir.DESC));
      this.config.set("managetype", "exportedvirtualsamples");
    } else if (this.managetype == ManageType.SHIPMENTSRECEIVED) {
      this.config.setSortInfo(new SortInfo("orderdatetime", Style.SortDir.DESC));
      this.config.set("managetype", "exportedvirtualsamples");
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
    


    LayoutContainer startdatelayout = new LayoutContainer();
    startdatelayout.add(this.startorderdate);
    startdatelayout.setWidth(165);
    
    LayoutContainer enddatelayout = new LayoutContainer();
    enddatelayout.add(this.endorderdate);
    enddatelayout.setWidth(165);
    
    toolbar.insert(this.exporttocsv, 1);
    toolbar.insert(new SeparatorToolItem(), 1);
    
    toolbar.insert(this.submitfilterbutton, 1);
    toolbar.insert(enddatelayout, 1);
    toolbar.insert(enddate, 1);
    toolbar.insert(startdatelayout, 1);
    toolbar.insert(startdate, 1);
    toolbar.insert(this.searchquery, 1);
    toolbar.insert(searchlabel, 1);
    toolbar.insert(new SeparatorToolItem(), 1);
    
    this.submitfilterbutton.addSelectionListener(this.selectionlistener);
    this.searchquery.addListener(Events.Change, this.changelistener);
    this.startorderdate.addListener(Events.Change, this.changelistener);
    this.endorderdate.addListener(Events.Change, this.changelistener);
    this.exporttocsv.addSelectionListener(this.selectionlistener);
    this.searchquery.addKeyListener(this.keylistener);
    this.searchquery.addListener(Events.Blur, this.blurlistener);
  }
  

  private KeyListener keylistener = new KeyListener()
  {
    public void componentKeyUp(ComponentEvent event)
    {
      if ((event.getSource().equals(ContainerScreen.this.searchquery)) && 
        (event.getKeyCode() == 13)) {
        ContainerScreen.this.config.set("searchquery", (String)ContainerScreen.this.searchquery.getValue());
        if (ContainerScreen.this.loader != null) {
          ContainerScreen.this.loader.load();
        }
      }
    }
  };
  

  private Listener<BaseEvent> blurlistener = new Listener()
  {
    public void handleEvent(BaseEvent be)
    {
      if ((be.getSource().equals(ContainerScreen.this.searchquery)) && 
        (ContainerScreen.this.loader != null)) {
        ContainerScreen.this.loader.load();
      }
    }
  };
  


  private Listener<BaseEvent> changelistener = new Listener()
  {
    public void handleEvent(BaseEvent be)
    {
      if (be.getSource().equals(ContainerScreen.this.searchquery)) {
        ContainerScreen.this.config.set("searchquery", (String)ContainerScreen.this.searchquery.getValue());
      }
      else if ((be.getSource().equals(ContainerScreen.this.startorderdate)) || (be.getSource().equals(ContainerScreen.this.endorderdate))) {
        if ((ContainerScreen.this.startorderdate.getValue() != null) && (ContainerScreen.this.endorderdate.getValue() != null) && (((Date)ContainerScreen.this.startorderdate.getValue()).after((Date)ContainerScreen.this.endorderdate.getValue()))) {
          Date tempdate = (Date)ContainerScreen.this.startorderdate.getValue();
          ContainerScreen.this.startorderdate.setValue((Date)ContainerScreen.this.endorderdate.getValue());
          ContainerScreen.this.endorderdate.setValue(tempdate);
          ContainerScreen.this.config.set("orderfrom", (Date)ContainerScreen.this.startorderdate.getValue());
          ContainerScreen.this.config.set("orderto", (Date)ContainerScreen.this.endorderdate.getValue());
        }
        else if (be.getSource().equals(ContainerScreen.this.startorderdate)) {
          ContainerScreen.this.config.set("orderfrom", (Date)ContainerScreen.this.startorderdate.getValue());
        } else if (be.getSource().equals(ContainerScreen.this.endorderdate)) {
          ContainerScreen.this.config.set("orderto", (Date)ContainerScreen.this.endorderdate.getValue());
        }
      }
    }
  };
  


  private SelectionListener<ButtonEvent> selectionlistener = new SelectionListener()
  {
    public void componentSelected(ButtonEvent ce)
    {
      if (ce.getSource().equals(ContainerScreen.this.submitfilterbutton)) {
        ContainerScreen.this.loader.load();
      } else if (ce.getSource().equals(ContainerScreen.this.exporttocsv)) {
        String variablesstring = "";
        if (ContainerScreen.this.config.get("managetype") != null) {
          variablesstring = variablesstring + "&managetype=" + ContainerScreen.this.config.get("managetype");
        }
        if (ContainerScreen.this.config.get("searchquery") != null) {
          variablesstring = variablesstring + "&searchquery=" + ContainerScreen.this.config.get("searchquery");
        }
        if (ContainerScreen.this.config.get("orderfrom") != null) {
          variablesstring = variablesstring + "&orderfrom=" + ((Date)ContainerScreen.this.config.get("orderfrom")).getTime();
        }
        if (ContainerScreen.this.config.get("orderto") != null) {
          variablesstring = variablesstring + "&orderto=" + ((Date)ContainerScreen.this.config.get("orderto")).getTime();
        }
        if (ContainerScreen.this.config.getSortField() != null) {
          variablesstring = variablesstring + "&sortfield=" + ContainerScreen.this.config.getSortField();
        }
        if (ContainerScreen.this.config.getSortField() != null) {
          variablesstring = variablesstring + "&sortdir=" + (ContainerScreen.this.config.getSortDir() == Style.SortDir.ASC ? "ASC" : "DESC");
        }
        Window.open(NewWorkFlow.baseurl + "outputcsv/?page=onlinegather" + variablesstring, "_blank", "");
      }
    }
  };
  

  private BuzzAsyncCallback<String> manageordergridrelogin = new BuzzAsyncCallback()
  {
    public void onFailure(Throwable caught) {}
    


    public void onSuccess(String result)
    {
      ContainerScreen.this.loader.load();
    }
    
    public void doRetry()
    {
      ContainerScreen.this.loader.load();
    }
  };
}
