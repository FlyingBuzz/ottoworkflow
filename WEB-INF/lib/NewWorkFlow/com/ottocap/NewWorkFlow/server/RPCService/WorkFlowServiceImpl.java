package com.ottocap.NewWorkFlow.server.RPCService;

import com.extjs.gxt.ui.client.core.FastMap;
import com.extjs.gxt.ui.client.core.FastSet;
import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.ottocap.NewWorkFlow.client.ContainerInvoice.ContainerData.ContainerData;
import com.ottocap.NewWorkFlow.client.DataHolder.CustomerInfo.EclipseDataHolder;
import com.ottocap.NewWorkFlow.client.FirstLoadData;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData.OrderType;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataAddress;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataCustomerInformation;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataItem;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogo;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogoDecoration;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataSet;
import com.ottocap.NewWorkFlow.client.OrderDataWithStyle;
import com.ottocap.NewWorkFlow.client.Services.WorkFlowService;
import com.ottocap.NewWorkFlow.client.StoredUser;
import com.ottocap.NewWorkFlow.client.StyleInformationData;
import com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBoxModelData;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderData;
import com.ottocap.NewWorkFlow.server.OrderExpander;
import com.ottocap.NewWorkFlow.server.PriceCalculation.DomesticPriceCalculation;
import com.ottocap.NewWorkFlow.server.PriceCalculation.OverseasPriceCalculation;
import com.ottocap.NewWorkFlow.server.SQLTable;
import com.ottocap.NewWorkFlow.server.SharedData;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.FileUtils;

public class WorkFlowServiceImpl extends RemoteServiceServlet implements WorkFlowService
{
  protected void onAfterResponseSerialized(String serializedResponse)
  {
    super.onAfterResponseSerialized(serializedResponse);
  }
  
  protected void onBeforeRequestDeserialized(String serializedRequest)
  {
    super.onBeforeRequestDeserialized(serializedRequest);
  }
  
  public StoredUser submitLogin(String username, String password) {
    SQLTable sqltable = new SQLTable();
    if (sqltable.makeTable("SELECT username,level,email FROM `employees` WHERE `username` = '" + username + "' AND `password` = PASSWORD('" + password + "') LIMIT 1").booleanValue()) {
      StoredUser userdata = new StoredUser();
      userdata.setAccessLevel(((Integer)sqltable.getCell("level")).intValue());
      userdata.setEmailAddress((String)sqltable.getCell("email"));
      userdata.setUsername((String)sqltable.getCell("username"));
      getThreadLocalRequest().getSession().setAttribute("username", (String)sqltable.getCell("username"));
      getThreadLocalRequest().getSession().setAttribute("level", (Integer)sqltable.getCell("level"));
      getThreadLocalRequest().getSession().setMaxInactiveInterval(86400);
      sqltable.closeSQL();
      return userdata;
    }
    sqltable.closeSQL();
    return null;
  }
  

  public BasePagingLoadResult<OtherComboBoxModelData> getEclipseAccountList(PagingLoadConfig loadConfig)
  {
    String query = (String)loadConfig.get("query");
    SQLTable sqltable = new SQLTable();
    sqltable.makeTable("SELECT eclipseaccount,company,contact,email FROM `eclipse_customer_info` WHERE `eclipseaccount` LIKE '" + query + "%' ORDER BY `eclipseaccount` ASC");
    ArrayList<FastMap<Object>> mytable = sqltable.getTable();
    sqltable.closeSQL();
    
    ArrayList<OtherComboBoxModelData> mylist = new ArrayList();
    
    for (int i = loadConfig.getOffset(); (i < loadConfig.getOffset() + loadConfig.getLimit()) && (i < mytable.size()); i++) {
      OtherComboBoxModelData mydata = new OtherComboBoxModelData(String.valueOf(((FastMap)mytable.get(i)).get("eclipseaccount")), String.valueOf(((FastMap)mytable.get(i)).get("eclipseaccount")));
      mydata.set("company", ((FastMap)mytable.get(i)).get("company"));
      mydata.set("contact", ((FastMap)mytable.get(i)).get("contact"));
      mydata.set("email", ((FastMap)mytable.get(i)).get("email"));
      mylist.add(mydata);
    }
    
    return new BasePagingLoadResult(mylist, loadConfig.getOffset(), mytable.size());
  }
  
  public EclipseDataHolder getEclipseAccount(int eclipseaccountnumber)
  {
    EclipseDataHolder myeclipsedataholder = new EclipseDataHolder();
    File filepath = new File("C:\\WorkFlow\\NewWorkflowData\\pdflogos/" + eclipseaccountnumber + "/logo.jpg");
    if (filepath.isFile()) {
      myeclipsedataholder.setCompanyLogo("Default");
    }
    
    SQLTable sqltable = new SQLTable();
    if (sqltable.makeTable("SELECT * FROM `eclipse_customer_info` WHERE `eclipseaccount` =" + eclipseaccountnumber + " LIMIT 1 ").booleanValue()) {
      OrderDataCustomerInformation mycustomerinformation = new OrderDataCustomerInformation();
      mycustomerinformation.setEclipseAccountNumber((Integer)sqltable.getRow().get("eclipseaccount"));
      mycustomerinformation.setCompany((String)sqltable.getRow().get("company"));
      mycustomerinformation.setContactName((String)sqltable.getRow().get("contact"));
      mycustomerinformation.setPhone((String)sqltable.getRow().get("phone"));
      mycustomerinformation.setFax((String)sqltable.getRow().get("fax"));
      mycustomerinformation.setEmail((String)sqltable.getRow().get("email"));
      mycustomerinformation.setTerms((String)sqltable.getRow().get("terms"));
      mycustomerinformation.setSameAsBillingAddress(((Boolean)sqltable.getRow().get("sameasbilling")).booleanValue());
      int currentbilling = ((Integer)sqltable.getRow().get("currentbilling")).intValue();
      int currentshipping = ((Integer)sqltable.getRow().get("currentshipping")).intValue();
      if (sqltable.makeTable("SELECT * FROM `eclipse_customer_address` WHERE `eclipseaccount` =" + eclipseaccountnumber + " AND `id` =" + currentbilling + " LIMIT 1").booleanValue()) {
        mycustomerinformation.getBillInformation().setCompany((String)sqltable.getRow().get("company"));
        mycustomerinformation.getBillInformation().setStreetLine1((String)sqltable.getRow().get("street1"));
        mycustomerinformation.getBillInformation().setStreetLine2((String)sqltable.getRow().get("street2"));
        mycustomerinformation.getBillInformation().setCity((String)sqltable.getRow().get("city"));
        mycustomerinformation.getBillInformation().setState((String)sqltable.getRow().get("state"));
        mycustomerinformation.getBillInformation().setZip((String)sqltable.getRow().get("zip"));
        mycustomerinformation.getBillInformation().setCountry((String)sqltable.getRow().get("country"));
        if (mycustomerinformation.getSameAsBillingAddress()) {
          mycustomerinformation.getShipInformation().setCompany((String)sqltable.getRow().get("company"));
          mycustomerinformation.getShipInformation().setStreetLine1((String)sqltable.getRow().get("street1"));
          mycustomerinformation.getShipInformation().setStreetLine2((String)sqltable.getRow().get("street2"));
          mycustomerinformation.getShipInformation().setCity((String)sqltable.getRow().get("city"));
          mycustomerinformation.getShipInformation().setState((String)sqltable.getRow().get("state"));
          mycustomerinformation.getShipInformation().setZip((String)sqltable.getRow().get("zip"));
          mycustomerinformation.getShipInformation().setCountry((String)sqltable.getRow().get("country"));
          mycustomerinformation.getShipInformation().setResidential(((Boolean)sqltable.getRow().get("residential")).booleanValue());
        }
      }
      
      if ((!mycustomerinformation.getSameAsBillingAddress()) && (sqltable.makeTable("SELECT * FROM `eclipse_customer_address` WHERE `eclipseaccount` =" + eclipseaccountnumber + " AND `id` =" + currentshipping + " LIMIT 1").booleanValue())) {
        mycustomerinformation.getShipInformation().setCompany((String)sqltable.getRow().get("company"));
        mycustomerinformation.getShipInformation().setStreetLine1((String)sqltable.getRow().get("street1"));
        mycustomerinformation.getShipInformation().setStreetLine2((String)sqltable.getRow().get("street2"));
        mycustomerinformation.getShipInformation().setCity((String)sqltable.getRow().get("city"));
        mycustomerinformation.getShipInformation().setState((String)sqltable.getRow().get("state"));
        mycustomerinformation.getShipInformation().setZip((String)sqltable.getRow().get("zip"));
        mycustomerinformation.getShipInformation().setCountry((String)sqltable.getRow().get("country"));
        mycustomerinformation.getShipInformation().setResidential(((Boolean)sqltable.getRow().get("residential")).booleanValue());
      }
      
      myeclipsedataholder.setOrderDataCustomerInformation(mycustomerinformation);
    }
    sqltable.closeSQL();
    



    if (myeclipsedataholder.getOrderDataCustomerInformation() == null) {
      String jsonresponse = sendGetRequest("http://www.ottocap.com/json/geteclipse.php", "password=workflowgather&eclipsenumber=" + eclipseaccountnumber);
      try
      {
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> untyped = (HashMap)mapper.readValue(jsonresponse, HashMap.class);
        boolean haveerror = ((Boolean)untyped.get("error")).booleanValue();
        if (!haveerror)
        {
          LinkedHashMap<String, Object> eclipsecustomerinfo = (LinkedHashMap)untyped.get("eclipsecustomerinfo");
          LinkedHashMap<String, Object> eclipsecustomerbilling = (LinkedHashMap)untyped.get("eclipsecustomerbilling");
          LinkedHashMap<String, Object> eclipsecustomershipping = (LinkedHashMap)untyped.get("eclipsecustomershipping");
          
          OrderDataCustomerInformation mycustomerinformation = new OrderDataCustomerInformation();
          mycustomerinformation.setEclipseAccountNumber((Integer)eclipsecustomerinfo.get("eclipseaccount"));
          mycustomerinformation.setCompany((String)eclipsecustomerinfo.get("company"));
          mycustomerinformation.setContactName((String)eclipsecustomerinfo.get("contact"));
          mycustomerinformation.setPhone((String)eclipsecustomerinfo.get("phone"));
          mycustomerinformation.setFax((String)eclipsecustomerinfo.get("fax"));
          mycustomerinformation.setEmail((String)eclipsecustomerinfo.get("email"));
          mycustomerinformation.setTerms((String)eclipsecustomerinfo.get("terms"));
          mycustomerinformation.setSameAsBillingAddress(((Boolean)eclipsecustomerinfo.get("sameasbilling")).booleanValue());
          
          mycustomerinformation.getBillInformation().setCompany((String)eclipsecustomerbilling.get("company"));
          mycustomerinformation.getBillInformation().setStreetLine1((String)eclipsecustomerbilling.get("street1"));
          mycustomerinformation.getBillInformation().setStreetLine2((String)eclipsecustomerbilling.get("street2"));
          mycustomerinformation.getBillInformation().setCity((String)eclipsecustomerbilling.get("city"));
          mycustomerinformation.getBillInformation().setState((String)eclipsecustomerbilling.get("state"));
          mycustomerinformation.getBillInformation().setZip((String)eclipsecustomerbilling.get("zip"));
          mycustomerinformation.getBillInformation().setCountry((String)eclipsecustomerbilling.get("country"));
          
          mycustomerinformation.getShipInformation().setCompany((String)eclipsecustomershipping.get("company"));
          mycustomerinformation.getShipInformation().setStreetLine1((String)eclipsecustomershipping.get("street1"));
          mycustomerinformation.getShipInformation().setStreetLine2((String)eclipsecustomershipping.get("street2"));
          mycustomerinformation.getShipInformation().setCity((String)eclipsecustomershipping.get("city"));
          mycustomerinformation.getShipInformation().setState((String)eclipsecustomershipping.get("state"));
          mycustomerinformation.getShipInformation().setZip((String)eclipsecustomershipping.get("zip"));
          mycustomerinformation.getShipInformation().setCountry((String)eclipsecustomershipping.get("country"));
          mycustomerinformation.getShipInformation().setResidential(((Boolean)eclipsecustomershipping.get("residential")).booleanValue());
          
          myeclipsedataholder.setOrderDataCustomerInformation(mycustomerinformation);
        }
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
    
    return myeclipsedataholder;
  }
  
  public static String sendGetRequest(String endpoint, String requestParameters) {
    String result = null;
    if (endpoint.startsWith("http://"))
    {
      try
      {

        String urlStr = endpoint;
        if ((requestParameters != null) && (requestParameters.length() > 0)) {
          urlStr = urlStr + "?" + requestParameters;
        }
        URL url = new URL(urlStr);
        URLConnection conn = url.openConnection();
        conn.setConnectTimeout(5000);
        

        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = rd.readLine()) != null) { String line;
          sb.append(line);
        }
        rd.close();
        result = sb.toString();
      }
      catch (Exception localException) {}
    }
    return result;
  }
  
  public OrderData createOrder(OrderData orderdata) throws Exception {
    SQLTable sqltable = new SQLTable();
    SaveOrder myorder = new SaveOrder(orderdata, getThreadLocalRequest().getSession(), sqltable);
    sqltable.closeSQL();
    return myorder.getOrder();
  }
  
  public Integer saveOrder(OrderData orderdata) throws Exception {
    SQLTable sqltable = new SQLTable();
    SaveOrder myorder = new SaveOrder(orderdata, getThreadLocalRequest().getSession(), sqltable);
    sqltable.closeSQL();
    return myorder.getOrder().getHiddenKey();
  }
  
  public OrderData getOrder(int hiddenkey) throws Exception {
    SQLTable sqltable = new SQLTable();
    GetOrder myorder = new GetOrder(hiddenkey, getThreadLocalRequest().getSession(), sqltable);
    sqltable.closeSQL();
    return myorder.getOrder();
  }
  
  public BasePagingLoadResult<BaseModelData> getOrderList(PagingLoadConfig loadConfig) throws Exception {
    ManageOrders myorder = new ManageOrders(loadConfig, getThreadLocalRequest().getSession(), false);
    return myorder.getResults();
  }
  
  public ArrayList<OtherComboBoxModelData> getEmployeeList() throws Exception {
    GetEmployeeList employeelist = new GetEmployeeList(getThreadLocalRequest().getSession());
    return employeelist.getList();
  }
  
  public String getOrderEmployee(int hiddenkey) throws Exception {
    GetOrderEmployee theemployee = new GetOrderEmployee(hiddenkey, getThreadLocalRequest().getSession());
    return theemployee.getEmployeeID();
  }
  
  public void saveOrderEmployee(int hiddenkey, String employeeid) throws Exception {
    new SaveOrderEmployee(hiddenkey, employeeid, getThreadLocalRequest().getSession());
  }
  
  public FirstLoadData getFirstLoad() throws Exception {
    GetFirstLoadData mydata = new GetFirstLoadData(getThreadLocalRequest().getSession());
    return mydata.getFirstLoadData();
  }
  
  public Boolean getIsFaya() throws Exception {
    return SharedData.isFaya;
  }
  
  public StyleInformationData getStyleData(String stylenumber, OrderData.OrderType ordertype) {
    GetStyleInformationData mydata = new GetStyleInformationData(stylenumber, ordertype);
    return mydata.getStyleInformationData();
  }
  
  public OrderDataWithStyle getOrderWithStyle(int hiddenkey) throws Exception
  {
    OrderDataWithStyle orderdatawithstyle = new OrderDataWithStyle();
    orderdatawithstyle.setOrderData(getOrder(hiddenkey));
    FastSet myitems = new FastSet();
    Iterator localIterator2; OrderDataItem currentitem; for (Iterator localIterator1 = orderdatawithstyle.getOrderData().getSets().iterator(); localIterator1.hasNext(); 
        localIterator2.hasNext())
    {
      OrderDataSet currentset = (OrderDataSet)localIterator1.next();
      localIterator2 = currentset.getItems().iterator(); continue;currentitem = (OrderDataItem)localIterator2.next();
      if ((currentitem.getStyleNumber() != null) && (!currentitem.getStyleNumber().equals(""))) {
        myitems.add(currentitem.getStyleNumber());
      }
    }
    

    ArrayList<StyleInformationData> myarray = new ArrayList();
    for (String currentitem : myitems) {
      myarray.add(getStyleData(currentitem, orderdatawithstyle.getOrderData().getOrderType()));
    }
    
    orderdatawithstyle.setStyleInfoArray(myarray);
    
    return orderdatawithstyle;
  }
  
  public void getRemoveEclipseAccountLogo(int eclipseAccountNumber)
  {
    new RemoveEclipseAccountLogo(eclipseAccountNumber);
  }
  
  public String getCurrentEmployee()
  {
    if (getThreadLocalRequest().getSession().getAttribute("username") != null) {
      return (String)getThreadLocalRequest().getSession().getAttribute("username");
    }
    return "";
  }
  
  public OrderDataWithStyle copyOrderByKey(int hiddenkey) throws Exception
  {
    SQLTable sqltable = new SQLTable();
    GetOrder myorder = new GetOrder(hiddenkey, getThreadLocalRequest().getSession(), sqltable, false);
    sqltable.closeSQL();
    OrderData newdata = copyOrder(myorder.getOrder());
    return getOrderWithStyle(newdata.getHiddenKey().intValue());
  }
  
  public OrderData copyOrder(OrderData orderdata) throws Exception {
    try {
      if (orderdata.getHiddenKey() == null) {
        return orderdata;
      }
      int hiddenkey = orderdata.getHiddenKey().intValue();
      String potentialrepeatfeq = orderdata.getPotentialRepeatFrequency();
      
      if ((orderdata.getOrderStatus().equals("Order Completed")) && 
        (!orderdata.getPotentialRepeatFrequency().equals("Never"))) {
        orderdata.setPotentialRepeatFrequency("Never");
        orderdata.setPotentialRepeatDate(null);
      }
      
      SQLTable sqltable = new SQLTable();
      
      if (orderdata.getEmployeeId().equals(getThreadLocalRequest().getSession().getAttribute("username"))) {
        new SaveOrder(orderdata, getThreadLocalRequest().getSession(), sqltable);
      }
      
      orderdata.setPotentialRepeatFrequency(potentialrepeatfeq);
      
      orderdata.setHiddenKey(null);
      orderdata.setEmployeeId(null);
      orderdata.setQuoteToOrder(null);
      
      orderdata.setOrderStatus("New Copy");
      SaveOrder myorder = new SaveOrder(orderdata, getThreadLocalRequest().getSession(), sqltable);
      int newhiddenkey = myorder.getOrder().getHiddenKey().intValue();
      
      if (orderdata.getOrderType() == OrderData.OrderType.DOMESTIC) {
        File thefolder = new File("C:\\WorkFlow\\NewWorkflowData\\DomesticData/" + hiddenkey + "/");
        if (thefolder.exists()) {
          FileUtils.copyDirectory(thefolder, new File("C:\\WorkFlow\\NewWorkflowData\\DomesticData/" + newhiddenkey + "/"));
        }
      } else {
        File thefolder = new File("C:\\WorkFlow\\NewWorkflowData\\OverseasData/" + hiddenkey + "/");
        if (thefolder.exists()) {
          FileUtils.copyDirectory(thefolder, new File("C:\\WorkFlow\\NewWorkflowData\\OverseasData/" + newhiddenkey + "/"));
        }
      }
      
      sqltable.closeSQL();
      return myorder.getOrder();
    }
    catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
  public OrderDataWithStyle copyToOrder(OrderData orderdata) throws Exception
  {
    SQLTable sqltable = new SQLTable();
    Iterator localIterator1; OrderDataLogoDecoration currentdecoration; if (orderdata.getOrderType() == OrderData.OrderType.DOMESTIC) {
      orderdata.setOrderType(OrderData.OrderType.OVERSEAS);
      for (localIterator1 = orderdata.getSets().iterator(); localIterator1.hasNext(); 
          




          ???.hasNext())
      {
        OrderDataSet currentset = (OrderDataSet)localIterator1.next();
        for (OrderDataItem currentitem : currentset.getItems()) {
          if (sqltable.makeTable("SELECT `basestyle` FROM `styles_domestic_specials` WHERE `style` = '" + currentitem.getStyleNumber() + "'").booleanValue()) {
            currentitem.setStyleNumber((String)sqltable.getValue());
          }
        }
        ??? = currentset.getLogos().iterator(); continue;OrderDataLogo currentlogo = (OrderDataLogo)???.next();
        if (currentlogo.getFilename().toLowerCase().endsWith(".dst")) {
          currentlogo.setDstFilename(currentlogo.getFilename());
          currentlogo.setFilename("");
        }
        OrderDataLogoDecoration currentdecoration = currentlogo.getDecorationCount().intValue() == 0 ? currentlogo.addDecoration() : currentlogo.getDecoration(Integer.valueOf(0));
        currentdecoration.setName(currentlogo.getDecoration());
      }
    }
    else
    {
      orderdata.setOrderType(OrderData.OrderType.DOMESTIC);
      for (localIterator1 = orderdata.getSets().iterator(); localIterator1.hasNext(); 
          














          ???.hasNext())
      {
        OrderDataSet currentset = (OrderDataSet)localIterator1.next();
        for (OrderDataItem currentitem : currentset.getItems()) {
          if ((!sqltable.makeTable("SELECT sku FROM `styles_domestic` WHERE `sku` = '" + currentitem.getStyleNumber() + "' LIMIT 1").booleanValue()) && 
            (!sqltable.makeTable("SELECT sku FROM `styles_domestic_totebags` WHERE `sku` = '" + currentitem.getStyleNumber() + "' LIMIT 1").booleanValue()) && 
            (!sqltable.makeTable("SELECT sku FROM `styles_domestic_aprons` WHERE `sku` = '" + currentitem.getStyleNumber() + "' LIMIT 1").booleanValue()) && 
            (!sqltable.makeTable("SELECT sku FROM `styles_domestic_shirts` WHERE `sku` = '" + currentitem.getStyleNumber() + "' LIMIT 1").booleanValue()) && 
            (!sqltable.makeTable("SELECT sku FROM `styles_domestic_sweatshirts` WHERE `sku` = '" + currentitem.getStyleNumber() + "' LIMIT 1").booleanValue())) {
            currentitem.setStyleNumber("");
          }
          



          int totalproofs = (currentitem.getProductSampleTotalEmail() != null ? currentitem.getProductSampleTotalEmail().intValue() : 0) + (currentitem.getProductSampleTotalShip() != null ? currentitem.getProductSampleTotalShip().intValue() : 0);
          currentitem.setProductSampleTotalDone(totalproofs == 0 ? null : Integer.valueOf(totalproofs));
        }
        ??? = currentset.getLogos().iterator(); continue;OrderDataLogo currentlogo = (OrderDataLogo)???.next();
        if (currentlogo.getFilename().equals("")) {
          currentlogo.setFilename(currentlogo.getDstFilename());
        }
        int totalproofs = (currentlogo.getSwatchTotalEmail() != null ? currentlogo.getSwatchTotalEmail().intValue() : 0) + (currentlogo.getSwatchTotalShip() != null ? currentlogo.getSwatchTotalShip().intValue() : 0);
        currentlogo.setSwatchTotalDone(totalproofs == 0 ? null : Integer.valueOf(totalproofs));
        if (currentlogo.getDecorationCount().intValue() > 0) {
          currentdecoration = currentlogo.getDecoration(Integer.valueOf(0));
          currentlogo.setDecoration(currentdecoration.getName());
        }
      }
    }
    

    OrderDataWithStyle orderdatawithstyle = new OrderDataWithStyle();
    FastSet myitems = new FastSet();
    OrderDataItem currentitem; for (??? = orderdata.getSets().iterator(); ???.hasNext(); 
        currentdecoration.hasNext())
    {
      OrderDataSet currentset = (OrderDataSet)???.next();
      currentdecoration = currentset.getItems().iterator(); continue;currentitem = (OrderDataItem)currentdecoration.next();
      if ((currentitem.getStyleNumber() != null) && (!currentitem.getStyleNumber().equals(""))) {
        myitems.add(currentitem.getStyleNumber());
      }
    }
    

    ArrayList<StyleInformationData> myarray = new ArrayList();
    for (String currentitem : myitems) {
      myarray.add(getStyleData(currentitem, orderdata.getOrderType()));
    }
    
    orderdatawithstyle.setStyleInfoArray(myarray);
    
    if (orderdata.getHiddenKey() == null) {
      orderdatawithstyle.setOrderData(orderdata);
      sqltable.closeSQL();
      return orderdatawithstyle;
    }
    int hiddenkey = orderdata.getHiddenKey().intValue();
    orderdata.setHiddenKey(null);
    orderdata.setEmployeeId(null);
    SaveOrder myorder = new SaveOrder(orderdata, getThreadLocalRequest().getSession(), sqltable);
    int newhiddenkey = myorder.getOrder().getHiddenKey().intValue();
    
    if (orderdata.getOrderType() == OrderData.OrderType.OVERSEAS) {
      File thefolder = new File("C:\\WorkFlow\\NewWorkflowData\\DomesticData/" + hiddenkey + "/");
      if (thefolder.exists()) {
        FileUtils.copyDirectory(thefolder, new File("C:\\WorkFlow\\NewWorkflowData\\OverseasData/" + newhiddenkey + "/"));
      }
    } else {
      File thefolder = new File("C:\\WorkFlow\\NewWorkflowData\\OverseasData/" + hiddenkey + "/");
      if (thefolder.exists()) {
        FileUtils.copyDirectory(thefolder, new File("C:\\WorkFlow\\NewWorkflowData\\DomesticData/" + newhiddenkey + "/"));
      }
    }
    
    orderdatawithstyle.setOrderData(myorder.getOrder());
    sqltable.closeSQL();
    return orderdatawithstyle;
  }
  

  public String doLogoff()
  {
    getThreadLocalRequest().getSession().invalidate();
    return "Done";
  }
  
  public BasePagingLoadResult<BaseModelData> getSalesReportList(PagingLoadConfig loadConfig) throws Exception {
    ManageSalesReport myorder = new ManageSalesReport(loadConfig, getThreadLocalRequest().getSession(), false);
    return myorder.getResults();
  }
  
  public BasePagingLoadResult<OtherComboBoxModelData> getCategoryList(PagingLoadConfig loadConfig, OrderData.OrderType ordertype)
  {
    String query = (String)loadConfig.get("query");
    
    String whichtype = "AND `domestic`=1";
    if (ordertype == OrderData.OrderType.OVERSEAS) {
      whichtype = "AND `overseas`=1";
    }
    SQLTable sqltable = new SQLTable();
    sqltable.makeTable("SELECT category FROM `list_categories` WHERE `category` LIKE '" + query + "%' " + whichtype + " ORDER BY `category` ASC");
    ArrayList<FastMap<Object>> mytable = sqltable.getTable();
    sqltable.closeSQL();
    
    ArrayList<OtherComboBoxModelData> mylist = new ArrayList();
    for (int i = 0; i < mytable.size(); i++) {
      OtherComboBoxModelData mydata = new OtherComboBoxModelData((String)((FastMap)mytable.get(i)).get("category"), (String)((FastMap)mytable.get(i)).get("category"));
      mylist.add(mydata);
    }
    
    return new BasePagingLoadResult(mylist, loadConfig.getOffset(), mytable.size());
  }
  
  public BasePagingLoadResult<OtherComboBoxModelData> getProductsList(PagingLoadConfig loadConfig, OrderData.OrderType ordertype)
  {
    String query = (String)loadConfig.get("query");
    String unmoddedquery = (String)loadConfig.get("query");
    
    String category = (String)loadConfig.get("category");
    
    String categoryquery = "";
    String categoryquery2 = "";
    String categoryquery3 = "";
    String categoryquery4 = "";
    String categoryquery5 = "";
    if ((category != null) && (!category.equals("")) && (!category.equals("All Items"))) {
      categoryquery = " AND category='" + category + "' ";
      categoryquery2 = " WHERE (styles_domestic.category='" + category + "' OR style='" + unmoddedquery + "') ";
      categoryquery3 = " AND `Main Category`='" + category + "' ";
      categoryquery4 = " AND `Category`='" + category + "' ";
      categoryquery5 = " WHERE (styles_lackpard.`Main Category`='" + category + "' OR styles_lackpard.`style`='" + unmoddedquery + "') ";
    }
    

    query.replaceAll("-", "%");
    
    SQLTable sqltable = new SQLTable();
    
    TreeMap<String, OtherComboBoxModelData> thestyles = new TreeMap();
    
    boolean showfactorystyles = false;
    if ((getThreadLocalRequest().getSession().getAttribute("username") != null) && (
      (((Integer)getThreadLocalRequest().getSession().getAttribute("level")).intValue() == 1) || (((Integer)getThreadLocalRequest().getSession().getAttribute("level")).intValue() == 2) || (((Integer)getThreadLocalRequest().getSession().getAttribute("level")).intValue() == 3) || (((Integer)getThreadLocalRequest().getSession().getAttribute("level")).intValue() == 5))) {
      showfactorystyles = true;
    }
    
    Object nonottoflatcombodata;
    if (ordertype == OrderData.OrderType.DOMESTIC)
    {

      if (sqltable.makeTable("SELECT sku, productname, size, category FROM `styles_domestic` WHERE (`sku` LIKE '" + query + "%' " + categoryquery + ") OR `sku` = '" + unmoddedquery + "'").booleanValue()) {
        ArrayList<FastMap<Object>> domesticstyletable = sqltable.getTable();
        for (FastMap<Object> currentdomesticstyle : domesticstyletable) {
          OtherComboBoxModelData mycombodata = new OtherComboBoxModelData((String)currentdomesticstyle.get("sku"));
          if (!((String)currentdomesticstyle.get("size")).trim().equals("")) {
            mycombodata.set("description", (String)currentdomesticstyle.get("productname") + " (" + (String)currentdomesticstyle.get("size") + ")");
          } else {
            mycombodata.set("description", (String)currentdomesticstyle.get("productname"));
          }
          if ((showfactorystyles) || ((!mycombodata.getName().startsWith("888-")) && (!mycombodata.getName().startsWith("889-"))))
          {

            thestyles.put(mycombodata.getName(), mycombodata);
          }
        }
      }
      
      if (sqltable.makeTable("SELECT style,styles_domestic.productname,styles_domestic.size,styles_domestic.category FROM (SELECT style,basestyle FROM `styles_domestic_specials` WHERE (`style` LIKE '" + query + "%' OR `style` = '" + unmoddedquery + "') ORDER BY `endingdate` DESC) AS t1 INNER JOIN styles_domestic ON styles_domestic.sku = t1.basestyle " + categoryquery2 + " GROUP BY `style`").booleanValue()) {
        ArrayList<FastMap<Object>> domesticstyletable = sqltable.getTable();
        for (FastMap<Object> currentdomesticstyle : domesticstyletable) {
          OtherComboBoxModelData mycombodata = new OtherComboBoxModelData((String)currentdomesticstyle.get("style"));
          if (currentdomesticstyle.get("size") != null) {
            if (!((String)currentdomesticstyle.get("size")).trim().equals("")) {
              mycombodata.set("description", (String)currentdomesticstyle.get("productname") + " (" + (String)currentdomesticstyle.get("size") + ")");
            } else
              mycombodata.set("description", (String)currentdomesticstyle.get("productname"));
          }
          thestyles.put(mycombodata.getName(), mycombodata);
        }
      }
      
      if (sqltable.makeTable("SELECT t1.style,styles_lackpard.Name,styles_lackpard.`Main Category` FROM (SELECT style,basestyle FROM `styles_domestic_specials` WHERE (`style` LIKE '" + query + "%' OR `style` = '" + unmoddedquery + "') ORDER BY `endingdate` DESC) AS t1 INNER JOIN styles_lackpard ON styles_lackpard.Style = t1.basestyle " + categoryquery5 + " GROUP BY t1.`style`").booleanValue()) {
        ArrayList<FastMap<Object>> domesticstyletable = sqltable.getTable();
        for (FastMap<Object> currentdomesticstyle : domesticstyletable) {
          OtherComboBoxModelData mycombodata = new OtherComboBoxModelData((String)currentdomesticstyle.get("style"));
          mycombodata.set("description", (String)currentdomesticstyle.get("Name"));
          thestyles.put(mycombodata.getName(), mycombodata);
        }
      }
      
      if (sqltable.makeTable("SELECT productname,category,sku FROM `styles_domestic_shirts` WHERE (`sku` LIKE '" + query + "%' " + categoryquery + ") OR `sku` = '" + unmoddedquery + "'").booleanValue()) {
        ArrayList<FastMap<Object>> domesticstyletable = sqltable.getTable();
        for (FastMap<Object> currentdomesticstyle : domesticstyletable) {
          OtherComboBoxModelData mycombodata = new OtherComboBoxModelData((String)currentdomesticstyle.get("sku"));
          mycombodata.set("description", (String)currentdomesticstyle.get("productname"));
          thestyles.put(mycombodata.getName(), mycombodata);
        }
      }
      
      if (sqltable.makeTable("SELECT productname,category,sku FROM `styles_domestic_sweatshirts` WHERE (`sku` LIKE '" + query + "%' " + categoryquery + ") OR `sku` = '" + unmoddedquery + "'").booleanValue()) {
        ArrayList<FastMap<Object>> domesticstyletable = sqltable.getTable();
        for (FastMap<Object> currentdomesticstyle : domesticstyletable) {
          OtherComboBoxModelData mycombodata = new OtherComboBoxModelData((String)currentdomesticstyle.get("sku"));
          mycombodata.set("description", (String)currentdomesticstyle.get("productname"));
          thestyles.put(mycombodata.getName(), mycombodata);
        }
      }
      
      if (sqltable.makeTable("SELECT `Style`,`Name`,`Main Category` FROM `styles_lackpard` WHERE (`Style` LIKE '" + query + "%' " + categoryquery3 + ") OR `Style` = '" + unmoddedquery + "'").booleanValue()) {
        ArrayList<FastMap<Object>> domesticstyletable = sqltable.getTable();
        for (FastMap<Object> currentdomesticstyle : domesticstyletable) {
          OtherComboBoxModelData mycombodata = new OtherComboBoxModelData((String)currentdomesticstyle.get("Style"));
          mycombodata.set("description", (String)currentdomesticstyle.get("Name"));
          thestyles.put(mycombodata.getName(), mycombodata);
        }
      }
      
      if (SharedData.isFaya.booleanValue())
      {
        OtherComboBoxModelData nonottocombodata = new OtherComboBoxModelData("Non FAYA Regular", "nonotto");
        nonottocombodata.set("description", "Other Regular Products");
        thestyles.put(nonottocombodata.getName(), nonottocombodata);
        
        OtherComboBoxModelData nonottoflatcombodata = new OtherComboBoxModelData("Non FAYA Flat", "nonottoflat");
        nonottoflatcombodata.set("description", "Other Flat Products");
        thestyles.put(nonottoflatcombodata.getName(), nonottoflatcombodata);
      } else {
        OtherComboBoxModelData nonottocombodata = new OtherComboBoxModelData("Non OTTO Regular", "nonotto");
        nonottocombodata.set("description", "Other Regular Products");
        thestyles.put(nonottocombodata.getName(), nonottocombodata);
        
        nonottoflatcombodata = new OtherComboBoxModelData("Non OTTO Flat", "nonottoflat");
        ((OtherComboBoxModelData)nonottoflatcombodata).set("description", "Other Flat Products");
        thestyles.put(((OtherComboBoxModelData)nonottoflatcombodata).getName(), nonottoflatcombodata);
      }
      


      if (sqltable.makeTable("SELECT sku, productname, category FROM `styles_domestic_totebags` WHERE (`sku` LIKE '" + query + "%' " + categoryquery + ") OR `sku` = '" + unmoddedquery + "'").booleanValue()) {
        ArrayList<FastMap<Object>> domesticstyletable = sqltable.getTable();
        for (nonottoflatcombodata = domesticstyletable.iterator(); ((Iterator)nonottoflatcombodata).hasNext();) { FastMap<Object> currentdomesticstyle = (FastMap)((Iterator)nonottoflatcombodata).next();
          OtherComboBoxModelData mycombodata = new OtherComboBoxModelData((String)currentdomesticstyle.get("sku"));
          mycombodata.set("description", (String)currentdomesticstyle.get("productname"));
          thestyles.put(mycombodata.getName(), mycombodata);
        }
      }
      


      if (sqltable.makeTable("SELECT sku, productname, category FROM `styles_domestic_aprons` WHERE (`sku` LIKE '" + query + "%' " + categoryquery + ") OR `sku` = '" + unmoddedquery + "'").booleanValue()) {
        ArrayList<FastMap<Object>> domesticstyletable = sqltable.getTable();
        for (nonottoflatcombodata = domesticstyletable.iterator(); ((Iterator)nonottoflatcombodata).hasNext();) { FastMap<Object> currentdomesticstyle = (FastMap)((Iterator)nonottoflatcombodata).next();
          OtherComboBoxModelData mycombodata = new OtherComboBoxModelData((String)currentdomesticstyle.get("sku"));
          mycombodata.set("description", (String)currentdomesticstyle.get("productname"));
          thestyles.put(mycombodata.getName(), mycombodata);
        }
        
      }
      

    }
    else
    {

      if (sqltable.makeTable("SELECT `STYLE NUMBER`,`Category`,`Emb Tooling Name` FROM `styles_overseasinstock` WHERE (`STYLE NUMBER` LIKE '" + query + "%' " + categoryquery4 + ") OR `STYLE NUMBER` = '" + unmoddedquery + "' GROUP BY `STYLE NUMBER`").booleanValue()) {
        ArrayList<FastMap<Object>> overseasstyletable = sqltable.getTable();
        for (nonottoflatcombodata = overseasstyletable.iterator(); ((Iterator)nonottoflatcombodata).hasNext();) { FastMap<Object> currentoverseasstyle = (FastMap)((Iterator)nonottoflatcombodata).next();
          OtherComboBoxModelData mycombodata = new OtherComboBoxModelData((String)currentoverseasstyle.get("STYLE NUMBER"));
          mycombodata.set("description", currentoverseasstyle.get("Emb Tooling Name") != null ? (String)currentoverseasstyle.get("Emb Tooling Name") : "");
          if ((showfactorystyles) || ((!mycombodata.getName().startsWith("888-")) && (!mycombodata.getName().startsWith("889-"))))
          {

            thestyles.put(mycombodata.getName(), mycombodata);
          }
        }
      }
      
      if (sqltable.makeTable("SELECT `Style`,`Name`,`Main Category` FROM `styles_lackpard` WHERE (`Style` LIKE '" + query + "%' " + categoryquery3 + ") OR `Style` = '" + unmoddedquery + "'").booleanValue()) {
        ArrayList<FastMap<Object>> overseasstyletable = sqltable.getTable();
        for (nonottoflatcombodata = overseasstyletable.iterator(); ((Iterator)nonottoflatcombodata).hasNext();) { FastMap<Object> currentdomesticstyle = (FastMap)((Iterator)nonottoflatcombodata).next();
          OtherComboBoxModelData mycombodata = new OtherComboBoxModelData((String)currentdomesticstyle.get("Style"));
          mycombodata.set("description", (String)currentdomesticstyle.get("Name"));
          thestyles.put(mycombodata.getName(), mycombodata);
        }
      }
      
      if (sqltable.makeTable("SELECT DISTINCT stylenumber,category FROM `overseas_price_instockshirts` WHERE (`stylenumber` LIKE '" + query + "%' " + categoryquery + ") OR `stylenumber` = '" + unmoddedquery + "'").booleanValue()) {
        ArrayList<FastMap<Object>> overseasstyletable = sqltable.getTable();
        for (nonottoflatcombodata = overseasstyletable.iterator(); ((Iterator)nonottoflatcombodata).hasNext();) { FastMap<Object> currentoverseasstyle = (FastMap)((Iterator)nonottoflatcombodata).next();
          OtherComboBoxModelData mycombodata = new OtherComboBoxModelData((String)currentoverseasstyle.get("stylenumber"));
          
          String productname = "";
          if (sqltable.makeTable("SELECT productname FROM `styles_domestic_shirts` WHERE `sku` = '" + (String)currentoverseasstyle.get("stylenumber") + "' LIMIT 1").booleanValue()) {
            productname = (String)sqltable.getValue();
          } else if (sqltable.makeTable("SELECT productname FROM `styles_domestic_sweatshirts` WHERE `sku` = '" + (String)currentoverseasstyle.get("stylenumber") + "' LIMIT 1").booleanValue()) {
            productname = (String)sqltable.getValue();
          }
          
          mycombodata.set("description", productname);
          if ((showfactorystyles) || ((!mycombodata.getName().startsWith("888-")) && (!mycombodata.getName().startsWith("889-"))))
          {

            thestyles.put(mycombodata.getName(), mycombodata);
          }
        }
      }
      
      if (sqltable.makeTable("SELECT DISTINCT stylenumber,productname,category FROM `overseas_price_instockflats` WHERE (`stylenumber` LIKE '" + query + "%' " + categoryquery + ") OR `stylenumber` = '" + unmoddedquery + "'").booleanValue()) {
        ArrayList<FastMap<Object>> overseasstyletable = sqltable.getTable();
        for (nonottoflatcombodata = overseasstyletable.iterator(); ((Iterator)nonottoflatcombodata).hasNext();) { FastMap<Object> currentoverseasstyle = (FastMap)((Iterator)nonottoflatcombodata).next();
          OtherComboBoxModelData mycombodata = new OtherComboBoxModelData((String)currentoverseasstyle.get("stylenumber"));
          mycombodata.set("description", (String)currentoverseasstyle.get("productname"));
          thestyles.put(mycombodata.getName(), mycombodata);
        }
      }
      

      if (sqltable.makeTable("SELECT `Style`,`Emb Tooling Name`,`Category` FROM `styles_overseas` WHERE (`Style` LIKE '" + query + "%' " + categoryquery4 + ") OR `Style` = '" + unmoddedquery + "'").booleanValue()) {
        ArrayList<FastMap<Object>> overseasstyletable = sqltable.getTable();
        for (nonottoflatcombodata = overseasstyletable.iterator(); ((Iterator)nonottoflatcombodata).hasNext();) { FastMap<Object> currentoverseasstyle = (FastMap)((Iterator)nonottoflatcombodata).next();
          OtherComboBoxModelData mycombodata = new OtherComboBoxModelData((String)currentoverseasstyle.get("Style"));
          mycombodata.set("description", (String)currentoverseasstyle.get("Emb Tooling Name"));
          thestyles.put(mycombodata.getName(), mycombodata);
        }
      }
      
      if (sqltable.makeTable("SELECT `Style`,`Material Description`,`Category` FROM `styles_pre-designed` WHERE (`Style` LIKE '" + query + "%' " + categoryquery4 + ") OR `Style` = '" + unmoddedquery + "'").booleanValue()) {
        ArrayList<FastMap<Object>> overseasstyletable = sqltable.getTable();
        for (nonottoflatcombodata = overseasstyletable.iterator(); ((Iterator)nonottoflatcombodata).hasNext();) { FastMap<Object> currentoverseasstyle = (FastMap)((Iterator)nonottoflatcombodata).next();
          OtherComboBoxModelData mycombodata = new OtherComboBoxModelData((String)currentoverseasstyle.get("Style"));
          mycombodata.set("description", (String)currentoverseasstyle.get("Material Description"));
          if ((showfactorystyles) || ((!mycombodata.getName().startsWith("888-")) && (!mycombodata.getName().startsWith("889-"))))
          {

            thestyles.put(mycombodata.getName(), mycombodata);
          }
        }
      }
      
      if ((showfactorystyles) && 
        (sqltable.makeTable("SELECT `Style` FROM `styles_overseas_customstyle` WHERE (`Style` LIKE '" + query + "%' ) OR `Style` = '" + unmoddedquery + "' GROUP BY `Style`").booleanValue())) {
        ArrayList<FastMap<Object>> overseasstyletable = sqltable.getTable();
        for (nonottoflatcombodata = overseasstyletable.iterator(); ((Iterator)nonottoflatcombodata).hasNext();) { FastMap<Object> currentoverseasstyle = (FastMap)((Iterator)nonottoflatcombodata).next();
          OtherComboBoxModelData mycombodata = new OtherComboBoxModelData((String)currentoverseasstyle.get("Style"));
          mycombodata.set("description", "Custom Style");
          thestyles.put(mycombodata.getName(), mycombodata);
        }
      }
    }
    


    sqltable.closeSQL();
    
    ArrayList<OtherComboBoxModelData> mylist = new ArrayList();
    
    String[] thekeys = (String[])thestyles.keySet().toArray(new String[0]);
    for (int i = loadConfig.getOffset(); (i < loadConfig.getOffset() + loadConfig.getLimit()) && (i < thestyles.size()); i++) {
      OtherComboBoxModelData mydata = (OtherComboBoxModelData)thestyles.get(thekeys[i]);
      mylist.add(mydata);
    }
    
    return new BasePagingLoadResult(mylist, loadConfig.getOffset(), thestyles.size());
  }
  
  public BasePagingLoadResult<OtherComboBoxModelData> getProductsLocationsList(PagingLoadConfig loadConfig, OrderData.OrderType ordertype)
  {
    String query = (String)loadConfig.get("query");
    FastSet theitems = (FastSet)loadConfig.get("items");
    String[] allitems = (String[])theitems.toArray(new String[0]);
    for (int i = 0; i < allitems.length; i++) {
      System.out.println(allitems[i]);
      System.out.println(query);
    }
    
    ArrayList<OtherComboBoxModelData> mylist = new ArrayList();
    
    return new BasePagingLoadResult(mylist, loadConfig.getOffset(), mylist.size());
  }
  
  public BasePagingLoadResult<BaseModelData> getOnlineGatherList(PagingLoadConfig loadConfig) throws Exception
  {
    ManageOnlineGather myorder = new ManageOnlineGather(loadConfig, getThreadLocalRequest().getSession(), false);
    return myorder.getResults();
  }
  
  public BasePagingLoadResult<BaseModelData> getContainerInvoiceList(PagingLoadConfig loadConfig) throws Exception
  {
    ManageContainerInvoice myorder = new ManageContainerInvoice(loadConfig, getThreadLocalRequest().getSession(), false);
    return myorder.getResults();
  }
  
  public Integer saveContainerInvoice(ContainerData containerdata) throws Exception
  {
    SQLTable sqltable = new SQLTable();
    SaveContainerInvoice mycontainerinvoice = new SaveContainerInvoice(containerdata, getThreadLocalRequest().getSession(), sqltable);
    sqltable.closeSQL();
    return mycontainerinvoice.getContainerData().getHiddenKey();
  }
  
  public ArrayList<String[]> getPricingTable(int hiddenkey) throws Exception
  {
    SQLTable sqltable = new SQLTable();
    OrderExpander myorder = new OrderExpander(hiddenkey, getThreadLocalRequest().getSession(), sqltable);
    
    ExtendOrderData thedata = myorder.getExpandedOrderData();
    if (thedata.getOrderType() == OrderData.OrderType.DOMESTIC) {
      DomesticPriceCalculation mycalc = new DomesticPriceCalculation(sqltable, thedata);
      mycalc.calculateCustomerPrice(true);
      sqltable.closeSQL();
      return mycalc.getTableRow();
    }
    OverseasPriceCalculation mycalc = new OverseasPriceCalculation(sqltable, thedata);
    mycalc.calculateOrderPrice(true);
    sqltable.closeSQL();
    return mycalc.getTableRow();
  }
}
