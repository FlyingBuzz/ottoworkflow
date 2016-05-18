package com.ottocap.NewWorkFlow.client.SalesReport;

import com.extjs.gxt.ui.client.Style.SortDir;
import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.data.BasePagingLoadConfig;
import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.data.SortInfo;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
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
import com.google.gwt.i18n.client.DateTimeFormat;
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
import com.ottocap.NewWorkFlow.client.StyleInformationData;
import com.ottocap.NewWorkFlow.client.Widget.BuzzAsyncCallback;
import com.ottocap.NewWorkFlow.client.Widget.USDMoneyField;
import java.util.ArrayList;
import java.util.Date;

public class SalesReportScreen extends LayoutContainer
{
  private BasePagingLoader<BasePagingLoadResult<BaseModelData>> loader;
  private Grid<BaseModelData> grid;
  private BasePagingLoadConfig config = new BasePagingLoadConfig();
  private int maxdisplay = 50;
  private TextField<String> searchquery = new TextField();
  private DateField startorderdate = new DateField();
  private DateField endorderdate = new DateField();
  private USDMoneyField minprofit = new USDMoneyField();
  private Button submitfilterbutton = new Button("Submit", Resources.ICONS.magnifier_left());
  private Button exporttocsv = new Button("Export To CSV", Resources.ICONS.report_excel());
  private PagingToolBar pagingtoolbar = new PagingToolBar(this.maxdisplay);
  private ReportType reporttype;
  
  public static enum ReportType { REPORT1,  REPORT2,  REPORT3,  REPORT4,  REPORT5,  REPORT6;
  }
  


  public SalesReportScreen(ReportType reporttype)
  {
    this.startorderdate.getDatePicker().setStartDay(7);
    this.endorderdate.getDatePicker().setStartDay(7);
    
    this.reporttype = reporttype;
    
    setLayout(new com.extjs.gxt.ui.client.widget.layout.RowLayout());
    setupTopToolBar();
    
    RpcProxy<BasePagingLoadResult<BaseModelData>> proxy = new RpcProxy()
    {
      protected void load(Object loadConfig, final AsyncCallback<BasePagingLoadResult<BaseModelData>> callback)
      {
        NewWorkFlow.get().getWorkFlowRPC().getSalesReportList((com.extjs.gxt.ui.client.data.PagingLoadConfig)loadConfig, new AsyncCallback()
        {
          public void onFailure(Throwable caught)
          {
            NewWorkFlow.get().reLogin2(caught, SalesReportScreen.this.manageordergridrelogin);
            callback.onFailure(caught);
            SalesReportScreen.this.pagingtoolbar.enable();
          }
          
          public void onSuccess(BasePagingLoadResult<BaseModelData> result)
          {
            callback.onSuccess(result);
            SalesReportScreen.this.pagingtoolbar.enable();
          }
          

        });
      }
      
    };
    this.loader = new BasePagingLoader(proxy);
    this.loader.setRemoteSort(true);
    ListStore<BaseModelData> store = new ListStore(this.loader);
    
    this.pagingtoolbar.bind(this.loader);
    
    ArrayList<ColumnConfig> columns = new ArrayList();
    
    if (reporttype == ReportType.REPORT1) {
      NewWorkFlow.get().getMainTabPanel().getMainTab().setWindowHeader("WorkFlow Program - Customer Sales Report");
      if (!NewWorkFlow.get().isFaya.booleanValue()) {
        columns.add(new ColumnConfig("eclipseaccountnumber", "Customer #", 100));
        columns.add(new ColumnConfig("contactname", "Customer Name", 200));
        columns.add(new ColumnConfig("salesdollars", "Sales Dollars", 100));
        columns.add(new ColumnConfig("minorderdate", "First Order Date", 100));
      } else {
        columns.add(new ColumnConfig("company", "Company", 100));
        columns.add(new ColumnConfig("contactname", "Customer Name", 200));
        columns.add(new ColumnConfig("salesdollars", "Sales Dollars", 100));
        columns.add(new ColumnConfig("minorderdate", "First Order Date", 100));
      }
    } else if (reporttype == ReportType.REPORT2) {
      NewWorkFlow.get().getMainTabPanel().getMainTab().setWindowHeader("WorkFlow Program - Item Sales Histroy");
      columns.add(new ColumnConfig("stylenumber", "ITEM #", 100));
      columns.add(new ColumnConfig("styledescription", "ITEM DESCRIPTION", 500));
      columns.add(new ColumnConfig("totalquantity", "UNITS SOLD", 100));
      columns.add(new ColumnConfig("minorderdate", "First Order Date", 100));
    } else if (reporttype == ReportType.REPORT3) {
      NewWorkFlow.get().getMainTabPanel().getMainTab().setWindowHeader("WorkFlow Program - Customer Item Sales");
      if (!NewWorkFlow.get().isFaya.booleanValue()) {
        columns.add(new ColumnConfig("eclipseaccountnumber", "Customer #", 100));
      }
      columns.add(new ColumnConfig("company", "Company", 200));
      columns.add(new ColumnConfig("contactname", "Customer Name", 200));
      columns.add(new ColumnConfig("stylenumber", "ITEM #", 100));
      columns.add(new ColumnConfig("styledescription", "ITEM DESCRIPTION", 500));
      columns.add(new ColumnConfig("totalquantity", "UNITS SOLD", 100));
      columns.add(new ColumnConfig("minorderdate", "First Order Date", 100));
    } else if (reporttype == ReportType.REPORT4) {
      NewWorkFlow.get().getMainTabPanel().getMainTab().setWindowHeader("WorkFlow Program - Monthly Sales");
      ColumnConfig quotenumber = new ColumnConfig("quote_number", "QUOTE NUMBER", 100);
      quotenumber.setHidden(true);
      columns.add(quotenumber);
      columns.add(new ColumnConfig("embqty", "Embroidery QTY", 100));
      columns.add(new ColumnConfig("heatqty", "Heat Transfer QTY", 100));
      columns.add(new ColumnConfig("osqty", "Overseas QTY", 100));
      columns.add(new ColumnConfig("ordernumber", "INVOICE", 100));
      columns.add(new ColumnConfig("allvendor", "VENDOR", 100));
      columns.add(new ColumnConfig("workordernumber", "JOB", 100));
      columns.add(new ColumnConfig("company", "COMPANY", 200));
      columns.add(new ColumnConfig("mainfilename", "DESCRIPTION", 200));
      columns.add(new ColumnConfig("totalcapprice", "PRODUCT PRICE", 100));
      columns.add(new ColumnConfig("totaldecorationprice", "SERVICE PRICE", 100));
      columns.add(new ColumnConfig("totalprice", "TOTAL PRICE", 100));
      ColumnConfig employeeidcolumn = new ColumnConfig("employeeid", "Employee ID", 100);
      columns.add(employeeidcolumn);
      if (!NewWorkFlow.get().isFaya.booleanValue()) {
        employeeidcolumn.setHidden(true);
      }
      columns.add(new ColumnConfig("employee", "Employee Name", 100));
      columns.add(new ColumnConfig("orderdate", "Order Date", 100));
      columns.add(new ColumnConfig("ordershipdate", "Order Ship Date", 100));
    } else if (reporttype == ReportType.REPORT5) {
      NewWorkFlow.get().getMainTabPanel().getMainTab().setWindowHeader("WorkFlow Program - Quotes To Order Conversion");
      columns.add(new ColumnConfig("ordertype", "Order Type", 100));
      columns.add(new ColumnConfig("hiddenkey", "Quote Number", 100));
      columns.add(new ColumnConfig("ordernumber", "Sales Number", 100));
      columns.add(new ColumnConfig("orderstatus", "Status", 200));
      columns.add(new ColumnConfig("company", "Company Name", 200));
      ColumnConfig orderdate = new ColumnConfig("orderdate", "Order Date", 100);
      orderdate.setDateTimeFormat(DateTimeFormat.getFormat("MM/dd/y"));
      columns.add(orderdate);
      columns.add(new ColumnConfig("employeename", "Managed By", 100));
      columns.add(new ColumnConfig("totalcapprice", "Total Cap Price", 100));
      columns.add(new ColumnConfig("totaldecorationprice", "Total Decoration Price", 150));
    } else if (reporttype == ReportType.REPORT6) {
      NewWorkFlow.get().getMainTabPanel().getMainTab().setWindowHeader("WorkFlow Program - Vendor");
      columns.add(new ColumnConfig("workordernumber", "Work Order", 100));
      ColumnConfig orderdate = new ColumnConfig("orderdate", "Order Date", 100);
      orderdate.setDateTimeFormat(DateTimeFormat.getFormat("MM/dd/y"));
      columns.add(orderdate);
      columns.add(new ColumnConfig("employee", "Employee", 100));
      columns.add(new ColumnConfig("embvendor", "Embroidery Vendor", 150));
      columns.add(new ColumnConfig("embventotqty", "Embroidery Vendor Total QTY", 170));
      columns.add(new ColumnConfig("embventotcost", "Embroidery Vendor Total Cost", 170));
      ColumnConfig embprocessdate = new ColumnConfig("embprocessdate", "Embroidery Process Date", 150);
      embprocessdate.setDateTimeFormat(DateTimeFormat.getFormat("MM/dd/y"));
      columns.add(embprocessdate);
      ColumnConfig embduedate = new ColumnConfig("embduedate", "Embroidery Due Date", 150);
      embduedate.setDateTimeFormat(DateTimeFormat.getFormat("MM/dd/y"));
      columns.add(embduedate);
      columns.add(new ColumnConfig("cadvendor", "CAD Print Vendor", 150));
      columns.add(new ColumnConfig("cadventotqty", "CAD Print Vendor Total QTY", 170));
      columns.add(new ColumnConfig("cadventotcost", "CAD Print Vendor Total Cost", 170));
      ColumnConfig cadprocessdate = new ColumnConfig("cadprocessdate", "CAD Print Process Date", 150);
      cadprocessdate.setDateTimeFormat(DateTimeFormat.getFormat("MM/dd/y"));
      columns.add(cadprocessdate);
      ColumnConfig cadduedate = new ColumnConfig("cadduedate", "CAD Print Due Date", 150);
      cadduedate.setDateTimeFormat(DateTimeFormat.getFormat("MM/dd/y"));
      columns.add(cadduedate);
      columns.add(new ColumnConfig("screenvendor", "Screen Print Vendor", 150));
      columns.add(new ColumnConfig("screenventotqty", "Screen Print Vendor Total QTY", 170));
      columns.add(new ColumnConfig("screenventotcost", "Screen Print Vendor Total Cost", 170));
      ColumnConfig screenprocessdate = new ColumnConfig("screenprocessdate", "Screen Print Process Date", 150);
      screenprocessdate.setDateTimeFormat(DateTimeFormat.getFormat("MM/dd/y"));
      columns.add(screenprocessdate);
      ColumnConfig screenduedate = new ColumnConfig("screenduedate", "Screen Print Due Date", 150);
      screenduedate.setDateTimeFormat(DateTimeFormat.getFormat("MM/dd/y"));
      columns.add(screenduedate);
      columns.add(new ColumnConfig("heatvendor", "Heat Transfer Vendor", 150));
      columns.add(new ColumnConfig("heatventotqty", "Heat Transfer Vendor Total QTY", 170));
      columns.add(new ColumnConfig("heatventotcost", "Heat Transfer Vendor Total Cost", 170));
      ColumnConfig heatprocessdate = new ColumnConfig("heatprocessdate", "Heat Transfer Process Date", 150);
      heatprocessdate.setDateTimeFormat(DateTimeFormat.getFormat("MM/dd/y"));
      columns.add(heatprocessdate);
      ColumnConfig heatduedate = new ColumnConfig("heatduedate", "Heat Transfer Due Date", 150);
      heatduedate.setDateTimeFormat(DateTimeFormat.getFormat("MM/dd/y"));
      columns.add(heatduedate);
      columns.add(new ColumnConfig("dtgvendor", "DTG Vendor", 150));
      columns.add(new ColumnConfig("dtgventotqty", "DTG Vendor Total QTY", 170));
      columns.add(new ColumnConfig("dtgventotcost", "DTG Vendor Total Cost", 170));
      ColumnConfig dtgprocessdate = new ColumnConfig("dtgprocessdate", "DTG Process Date", 150);
      dtgprocessdate.setDateTimeFormat(DateTimeFormat.getFormat("MM/dd/y"));
      columns.add(dtgprocessdate);
      ColumnConfig dtgduedate = new ColumnConfig("dtgduedate", "DTG Due Date", 150);
      dtgduedate.setDateTimeFormat(DateTimeFormat.getFormat("MM/dd/y"));
      columns.add(dtgduedate);
    }
    

    ColumnModel cm = new ColumnModel(columns);
    
    this.grid = new Grid(store, cm);
    this.grid.setView(new com.extjs.gxt.ui.client.widget.grid.BufferView());
    this.grid.setLoadMask(true);
    


    this.grid.getSelectionModel().setSelectionMode(com.extjs.gxt.ui.client.Style.SelectionMode.SINGLE);
    
    initConfig();
    add(this.grid, new RowData(1.0D, 1.0D));
    add(this.pagingtoolbar, new RowData(1.0D, -1.0D));
  }
  
  BuzzAsyncCallback<OrderDataWithStyle> getordercallback = new BuzzAsyncCallback()
  {
    public void onFailure(Throwable caught)
    {
      NewWorkFlow.get().reLogin2(caught, SalesReportScreen.this.getordercallback);
    }
    

    public void doRetry()
    {
      NewWorkFlow.get().getWorkFlowRPC().getOrderWithStyle(((Integer)((BaseModelData)SalesReportScreen.this.grid.getSelectionModel().getSelectedItem()).get("quotenumber")).intValue(), SalesReportScreen.this.getordercallback);
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
  
  public void initConfig()
  {
    this.config.setOffset(0);
    this.config.setLimit(this.maxdisplay);
    this.config.set("searchquery", "");
    this.config.set("orderfrom", (Date)this.startorderdate.getValue());
    this.config.set("orderto", (Date)this.endorderdate.getValue());
    this.config.set("minprofit", (Number)this.minprofit.getValue());
    
    if (this.reporttype == ReportType.REPORT1) {
      this.config.setSortInfo(new SortInfo("salesdollars", Style.SortDir.DESC));
      this.config.set("managetype", "report1");
    } else if (this.reporttype == ReportType.REPORT2) {
      this.config.setSortInfo(new SortInfo("totalquantity", Style.SortDir.DESC));
      this.config.set("managetype", "report2");
    } else if (this.reporttype == ReportType.REPORT3) {
      this.config.setSortInfo(new SortInfo("totalquantity", Style.SortDir.DESC));
      this.config.set("managetype", "report3");
    } else if (this.reporttype == ReportType.REPORT4) {
      this.config.setSortInfo(new SortInfo("ordernumber", Style.SortDir.DESC));
      this.config.set("managetype", "report4");
    } else if (this.reporttype == ReportType.REPORT5) {
      this.config.setSortInfo(new SortInfo("orderdate", Style.SortDir.DESC));
      this.config.set("managetype", "report5");
    } else if (this.reporttype == ReportType.REPORT6) {
      this.config.setSortInfo(new SortInfo("embprocessdate", Style.SortDir.DESC));
      this.config.set("managetype", "report6");
    }
    
    this.loader.useLoadConfig(this.config);
    this.loader.load();
  }
  
  private void setupTopToolBar()
  {
    NewWorkFlow.get().getMainTabPanel().getMainTab().getMainScreen().resetTopToolBar();
    ToolBar toolbar = NewWorkFlow.get().getMainTabPanel().getMainTab().getMainScreen().getTopToolBar();
    Status startdate = new Status();
    Status enddate = new Status();
    Status searchlabel = new Status();
    searchlabel.setText("Search:");
    Status minprofitlabel = new Status();
    minprofitlabel.setText("Minimum Profit");
    
    if (this.reporttype == ReportType.REPORT4) {
      startdate.setText("Order Ship Date From:");
      enddate.setText("Order Ship Date To:");
    } else {
      startdate.setText("Order Date From:");
      enddate.setText("Order Date To:");
    }
    



    LayoutContainer startdatelayout = new LayoutContainer();
    startdatelayout.add(this.startorderdate);
    startdatelayout.setWidth(165);
    
    LayoutContainer enddatelayout = new LayoutContainer();
    enddatelayout.add(this.endorderdate);
    enddatelayout.setWidth(165);
    
    toolbar.insert(this.exporttocsv, 1);
    toolbar.insert(new SeparatorToolItem(), 1);
    
    toolbar.insert(this.submitfilterbutton, 1);
    
    if ((this.reporttype == ReportType.REPORT1) || (this.reporttype == ReportType.REPORT4)) {
      toolbar.insert(this.minprofit, 1);
      toolbar.insert(minprofitlabel, 1);
    }
    
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
    this.minprofit.addListener(Events.Change, this.changelistener);
    this.exporttocsv.addSelectionListener(this.selectionlistener);
  }
  
  private Listener<BaseEvent> changelistener = new Listener()
  {
    public void handleEvent(BaseEvent be)
    {
      if (be.getSource().equals(SalesReportScreen.this.searchquery)) {
        SalesReportScreen.this.config.set("searchquery", (String)SalesReportScreen.this.searchquery.getValue());
      }
      else if ((be.getSource().equals(SalesReportScreen.this.startorderdate)) || (be.getSource().equals(SalesReportScreen.this.endorderdate))) {
        if ((SalesReportScreen.this.startorderdate.getValue() != null) && (SalesReportScreen.this.endorderdate.getValue() != null) && (((Date)SalesReportScreen.this.startorderdate.getValue()).after((Date)SalesReportScreen.this.endorderdate.getValue()))) {
          Date tempdate = (Date)SalesReportScreen.this.startorderdate.getValue();
          SalesReportScreen.this.startorderdate.setValue((Date)SalesReportScreen.this.endorderdate.getValue());
          SalesReportScreen.this.endorderdate.setValue(tempdate);
          SalesReportScreen.this.config.set("orderfrom", (Date)SalesReportScreen.this.startorderdate.getValue());
          SalesReportScreen.this.config.set("orderto", (Date)SalesReportScreen.this.endorderdate.getValue());
        }
        else if (be.getSource().equals(SalesReportScreen.this.startorderdate)) {
          SalesReportScreen.this.config.set("orderfrom", (Date)SalesReportScreen.this.startorderdate.getValue());
        } else if (be.getSource().equals(SalesReportScreen.this.endorderdate)) {
          SalesReportScreen.this.config.set("orderto", (Date)SalesReportScreen.this.endorderdate.getValue());
        }
      }
      else if (be.getSource().equals(SalesReportScreen.this.minprofit)) {
        SalesReportScreen.this.config.set("minprofit", (Number)SalesReportScreen.this.minprofit.getValue());
      }
    }
  };
  

  private SelectionListener<ButtonEvent> selectionlistener = new SelectionListener()
  {
    public void componentSelected(ButtonEvent ce)
    {
      if (ce.getSource().equals(SalesReportScreen.this.submitfilterbutton)) {
        SalesReportScreen.this.loader.load();
      } else if (ce.getSource().equals(SalesReportScreen.this.exporttocsv)) {
        String variablesstring = "";
        if (SalesReportScreen.this.config.get("managetype") != null) {
          variablesstring = variablesstring + "&managetype=" + SalesReportScreen.this.config.get("managetype");
        }
        if (SalesReportScreen.this.config.get("searchquery") != null) {
          variablesstring = variablesstring + "&searchquery=" + SalesReportScreen.this.config.get("searchquery");
        }
        if (SalesReportScreen.this.config.get("minprofit") != null) {
          variablesstring = variablesstring + "&minprofit=" + SalesReportScreen.this.config.get("minprofit");
        }
        if (SalesReportScreen.this.config.get("orderfrom") != null) {
          variablesstring = variablesstring + "&orderfrom=" + ((Date)SalesReportScreen.this.config.get("orderfrom")).getTime();
        }
        if (SalesReportScreen.this.config.get("orderto") != null) {
          variablesstring = variablesstring + "&orderto=" + ((Date)SalesReportScreen.this.config.get("orderto")).getTime();
        }
        if (SalesReportScreen.this.config.getSortField() != null) {
          variablesstring = variablesstring + "&sortfield=" + SalesReportScreen.this.config.getSortField();
        }
        if (SalesReportScreen.this.config.getSortField() != null) {
          variablesstring = variablesstring + "&sortdir=" + (SalesReportScreen.this.config.getSortDir() == Style.SortDir.ASC ? "ASC" : "DESC");
        }
        
        com.google.gwt.user.client.Window.open(NewWorkFlow.baseurl + "outputcsv/?page=salesreport" + variablesstring, "_blank", "");
      }
    }
  };
  

  private BuzzAsyncCallback<String> manageordergridrelogin = new BuzzAsyncCallback()
  {
    public void onFailure(Throwable caught) {}
    


    public void onSuccess(String result)
    {
      SalesReportScreen.this.loader.load();
    }
    
    public void doRetry()
    {
      SalesReportScreen.this.loader.load();
    }
  };
}
