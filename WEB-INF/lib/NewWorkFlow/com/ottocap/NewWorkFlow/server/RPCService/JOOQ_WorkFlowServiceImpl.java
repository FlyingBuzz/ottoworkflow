package com.ottocap.NewWorkFlow.server.RPCService;

import com.extjs.gxt.ui.client.core.FastSet;
import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.ottocap.NewWorkFlow.client.ContainerInvoice.ContainerData.ContainerData;
import com.ottocap.NewWorkFlow.client.DataHolder.CustomerInfo.EclipseDataHolder;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData.OrderType;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataAddress;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataCustomerInformation;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataItem;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogo;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogoDecoration;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataSet;
import com.ottocap.NewWorkFlow.client.OrderDataWithStyle;
import com.ottocap.NewWorkFlow.client.StoredUser;
import com.ottocap.NewWorkFlow.client.StyleInformationData;
import com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBoxModelData;
import com.ottocap.NewWorkFlow.server.JOOQSQL;
import com.ottocap.NewWorkFlow.server.JOOQ_OrderExpander;
import com.ottocap.NewWorkFlow.server.PriceCalculation.JOOQ_DomesticPriceCalculation;
import com.ottocap.NewWorkFlow.server.PriceCalculation.JOOQ_OverseasPriceCalculation;
import java.io.BufferedReader;
import java.io.File;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.FileUtils;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.jooq.SelectJoinStep;
import org.jooq.SelectOffsetStep;
import org.jooq.SelectSelectStep;
import org.jooq.SelectWhereStep;
import org.jooq.TableLike;
import org.jooq.impl.DSL;

public class JOOQ_WorkFlowServiceImpl extends RemoteServiceServlet implements com.ottocap.NewWorkFlow.client.Services.WorkFlowService
{
  protected void onAfterResponseSerialized(String serializedResponse)
  {
    super.onAfterResponseSerialized(serializedResponse);
  }
  
  protected void onBeforeRequestDeserialized(String serializedRequest)
  {
    super.onBeforeRequestDeserialized(serializedRequest);
  }
  
  public StoredUser submitLogin(String username, String password)
  {
    Record sqlrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "username" }), DSL.fieldByName(new String[] { "level" }), DSL.fieldByName(new String[] { "email" })).from(new TableLike[] { DSL.tableByName(new String[] { "employees" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "username" }).equal(username) }).and(DSL.fieldByName(new String[] { "password" }).equal(org.jooq.util.mysql.MySQLDSL.password(password))).limit(1).fetchOne();
    if (sqlrecord != null) {
      StoredUser userdata = new StoredUser();
      userdata.setAccessLevel(((Integer)sqlrecord.getValue(DSL.fieldByName(new String[] { "level" }))).intValue());
      userdata.setEmailAddress((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "email" })));
      userdata.setUsername((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "username" })));
      getThreadLocalRequest().getSession().setAttribute("username", (String)sqlrecord.getValue(DSL.fieldByName(new String[] { "username" })));
      getThreadLocalRequest().getSession().setAttribute("level", (Integer)sqlrecord.getValue(DSL.fieldByName(new String[] { "level" })));
      getThreadLocalRequest().getSession().setMaxInactiveInterval(86400);
      return userdata;
    }
    return null;
  }
  

  public BasePagingLoadResult<OtherComboBoxModelData> getEclipseAccountList(PagingLoadConfig loadConfig)
  {
    String query = (String)loadConfig.get("query");
    Result<Record4<Object, Object, Object, Object>> sqlrecords = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "eclipseaccount" }), DSL.fieldByName(new String[] { "company" }), DSL.fieldByName(new String[] { "contact" }), DSL.fieldByName(new String[] { "email" })).from("eclipse_customer_info").where(new Condition[] { DSL.fieldByName(new String[] { "eclipseaccount" }).like(query + "%") }).orderBy(new org.jooq.SortField[] { DSL.fieldByName(new String[] { "eclipseaccount" }).asc() }).fetch();
    
    ArrayList<OtherComboBoxModelData> mylist = new ArrayList();
    
    for (int i = loadConfig.getOffset(); (i < loadConfig.getOffset() + loadConfig.getLimit()) && (i < sqlrecords.size()); i++) {
      OtherComboBoxModelData mydata = new OtherComboBoxModelData(String.valueOf(((Record4)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "eclipseaccount" }))), String.valueOf(((Record4)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "eclipseaccount" }))));
      mydata.set("company", ((Record4)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "company" })));
      mydata.set("contact", ((Record4)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "contact" })));
      mydata.set("email", ((Record4)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "email" })));
      mylist.add(mydata);
    }
    
    return new BasePagingLoadResult(mylist, loadConfig.getOffset(), sqlrecords.size());
  }
  
  public EclipseDataHolder getEclipseAccount(int eclipseaccountnumber)
  {
    EclipseDataHolder myeclipsedataholder = new EclipseDataHolder();
    File filepath = new File("C:\\WorkFlow\\NewWorkflowData\\pdflogos/" + eclipseaccountnumber + "/logo.jpg");
    if (filepath.isFile()) {
      myeclipsedataholder.setCompanyLogo("Default");
    }
    
    Record sqlrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "eclipse_customer_info" })).where(new Condition[] { DSL.fieldByName(new String[] { "eclipseaccount" }).equal(Integer.valueOf(eclipseaccountnumber)) }).limit(1).fetchOne();
    if ((sqlrecord != null) && (sqlrecord.size() != 0)) {
      OrderDataCustomerInformation mycustomerinformation = new OrderDataCustomerInformation();
      mycustomerinformation.setEclipseAccountNumber((Integer)sqlrecord.getValue(DSL.fieldByName(new String[] { "eclipseaccount" })));
      mycustomerinformation.setCompany((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "company" })));
      mycustomerinformation.setContactName((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "contact" })));
      mycustomerinformation.setPhone((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "phone" })));
      mycustomerinformation.setFax((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "fax" })));
      mycustomerinformation.setEmail((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "email" })));
      mycustomerinformation.setTerms((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "terms" })));
      mycustomerinformation.setSameAsBillingAddress(((Byte)sqlrecord.getValue(DSL.fieldByName(new String[] { "sameasbilling" }))).byteValue() != 0);
      int currentbilling = ((Integer)sqlrecord.getValue(DSL.fieldByName(new String[] { "currentbilling" }))).intValue();
      int currentshipping = ((Integer)sqlrecord.getValue(DSL.fieldByName(new String[] { "currentshipping" }))).intValue();
      sqlrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "eclipse_customer_address" })).where(new Condition[] { DSL.fieldByName(new String[] { "eclipseaccount" }).equal(Integer.valueOf(eclipseaccountnumber)) }).and(DSL.fieldByName(new String[] { "id" }).equal(Integer.valueOf(currentbilling))).limit(1).fetchOne();
      if ((sqlrecord != null) && (sqlrecord.size() != 0)) {
        mycustomerinformation.getBillInformation().setCompany((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "company" })));
        mycustomerinformation.getBillInformation().setStreetLine1((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "street1" })));
        mycustomerinformation.getBillInformation().setStreetLine2((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "street2" })));
        mycustomerinformation.getBillInformation().setCity((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "city" })));
        mycustomerinformation.getBillInformation().setState((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "state" })));
        mycustomerinformation.getBillInformation().setZip((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "zip" })));
        mycustomerinformation.getBillInformation().setCountry((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "country" })));
        if (mycustomerinformation.getSameAsBillingAddress()) {
          mycustomerinformation.getShipInformation().setCompany((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "company" })));
          mycustomerinformation.getShipInformation().setStreetLine1((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "street1" })));
          mycustomerinformation.getShipInformation().setStreetLine2((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "street2" })));
          mycustomerinformation.getShipInformation().setCity((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "city" })));
          mycustomerinformation.getShipInformation().setState((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "state" })));
          mycustomerinformation.getShipInformation().setZip((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "zip" })));
          mycustomerinformation.getShipInformation().setCountry((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "country" })));
          mycustomerinformation.getShipInformation().setResidential(((Byte)sqlrecord.getValue(DSL.fieldByName(new String[] { "residential" }))).byteValue() != 0);
        }
      }
      
      if (!mycustomerinformation.getSameAsBillingAddress()) {
        sqlrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "eclipse_customer_address" })).where(new Condition[] { DSL.fieldByName(new String[] { "eclipseaccount" }).equal(Integer.valueOf(eclipseaccountnumber)) }).and(DSL.fieldByName(new String[] { "id" }).equal(Integer.valueOf(currentshipping))).limit(1).fetchOne();
        if ((sqlrecord != null) && (sqlrecord.size() != 0)) {
          mycustomerinformation.getShipInformation().setStreetLine1((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "street1" })));
          mycustomerinformation.getShipInformation().setStreetLine2((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "street2" })));
          mycustomerinformation.getShipInformation().setCity((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "city" })));
          mycustomerinformation.getShipInformation().setState((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "state" })));
          mycustomerinformation.getShipInformation().setZip((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "zip" })));
          mycustomerinformation.getShipInformation().setCountry((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "country" })));
          mycustomerinformation.getShipInformation().setResidential(((Byte)sqlrecord.getValue(DSL.fieldByName(new String[] { "residential" }))).byteValue() != 0);
        }
      }
      
      myeclipsedataholder.setOrderDataCustomerInformation(mycustomerinformation);
    }
    



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
          mycustomerinformation.setSameAsBillingAddress(((Byte)eclipsecustomerinfo.get("sameasbilling")).byteValue() != 0);
          
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
          mycustomerinformation.getShipInformation().setResidential(((Byte)eclipsecustomershipping.get("residential")).byteValue() != 0);
          
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
        

        BufferedReader rd = new BufferedReader(new java.io.InputStreamReader(conn.getInputStream()));
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
    try {
      JOOQ_SaveOrder myorder = new JOOQ_SaveOrder(orderdata, getThreadLocalRequest().getSession());
      return myorder.getOrder();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
  public Integer saveOrder(OrderData orderdata) throws Exception {
    try {
      JOOQ_SaveOrder myorder = new JOOQ_SaveOrder(orderdata, getThreadLocalRequest().getSession());
      return myorder.getOrder().getHiddenKey();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
  public OrderData getOrder(int hiddenkey) throws Exception {
    try {
      JOOQ_GetOrder myorder = new JOOQ_GetOrder(hiddenkey, getThreadLocalRequest().getSession());
      return myorder.getOrder();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
  public BasePagingLoadResult<BaseModelData> getOrderList(PagingLoadConfig loadConfig) throws Exception {
    try {
      JOOQ_ManageOrders myorder = new JOOQ_ManageOrders(loadConfig, getThreadLocalRequest().getSession(), false);
      return myorder.getResults();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
  public ArrayList<OtherComboBoxModelData> getEmployeeList() throws Exception {
    JOOQ_GetEmployeeList employeelist = new JOOQ_GetEmployeeList(getThreadLocalRequest().getSession());
    return employeelist.getList();
  }
  
  public String getOrderEmployee(int hiddenkey) throws Exception {
    JOOQ_GetOrderEmployee theemployee = new JOOQ_GetOrderEmployee(hiddenkey, getThreadLocalRequest().getSession());
    return theemployee.getEmployeeID();
  }
  
  public void saveOrderEmployee(int hiddenkey, String employeeid) throws Exception {
    new JOOQ_SaveOrderEmployee(hiddenkey, employeeid, getThreadLocalRequest().getSession());
  }
  
  public com.ottocap.NewWorkFlow.client.FirstLoadData getFirstLoad() throws Exception {
    try {
      JOOQ_GetFirstLoadData mydata = new JOOQ_GetFirstLoadData(getThreadLocalRequest().getSession());
      return mydata.getFirstLoadData();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
  public Boolean getIsFaya() throws Exception {
    return com.ottocap.NewWorkFlow.server.SharedData.isFaya;
  }
  
  public StyleInformationData getStyleData(String stylenumber, OrderData.OrderType ordertype) {
    JOOQ_GetStyleInformationData mydata = new JOOQ_GetStyleInformationData(stylenumber, ordertype);
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
    JOOQ_GetOrder myorder = new JOOQ_GetOrder(hiddenkey, getThreadLocalRequest().getSession(), Boolean.valueOf(false));
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
      

      if (orderdata.getEmployeeId().equals(getThreadLocalRequest().getSession().getAttribute("username"))) {
        new JOOQ_SaveOrder(orderdata, getThreadLocalRequest().getSession());
      }
      
      orderdata.setPotentialRepeatFrequency(potentialrepeatfeq);
      
      orderdata.setHiddenKey(null);
      orderdata.setEmployeeId(null);
      orderdata.setQuoteToOrder(null);
      
      orderdata.setOrderStatus("New Copy");
      JOOQ_SaveOrder myorder = new JOOQ_SaveOrder(orderdata, getThreadLocalRequest().getSession());
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
      
      return myorder.getOrder();
    }
    catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
  public OrderDataWithStyle copyToOrder(OrderData orderdata) throws Exception { Iterator localIterator1;
    OrderDataLogoDecoration currentdecoration;
    if (orderdata.getOrderType() == OrderData.OrderType.DOMESTIC) {
      orderdata.setOrderType(OrderData.OrderType.OVERSEAS);
      for (localIterator1 = orderdata.getSets().iterator(); localIterator1.hasNext(); 
          





          ???.hasNext())
      {
        OrderDataSet currentset = (OrderDataSet)localIterator1.next();
        for (OrderDataItem currentitem : currentset.getItems()) {
          Record sqlrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "basestyle" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_domestic_specials" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "style" }).equal(currentitem.getStyleNumber()) }).limit(1).fetchOne();
          if (sqlrecord != null) {
            currentitem.setStyleNumber((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "basestyle" })));
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
          Record sqlrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "sku" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_domestic" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "sku" }).equal(currentitem.getStyleNumber()) }).limit(1).fetchOne();
          if ((sqlrecord == null) || (sqlrecord.size() == 0)) {
            sqlrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "sku" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_domestic_totebags" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "sku" }).equal(currentitem.getStyleNumber()) }).limit(1).fetchOne();
            if ((sqlrecord == null) || (sqlrecord.size() == 0)) {
              sqlrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "sku" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_domestic_aprons" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "sku" }).equal(currentitem.getStyleNumber()) }).limit(1).fetchOne();
              if ((sqlrecord == null) || (sqlrecord.size() == 0)) {
                sqlrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "sku" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_domestic_shirts" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "sku" }).equal(currentitem.getStyleNumber()) }).limit(1).fetchOne();
                if ((sqlrecord == null) || (sqlrecord.size() == 0)) {
                  sqlrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "sku" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_domestic_sweatshirts" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "sku" }).equal(currentitem.getStyleNumber()) }).limit(1).fetchOne();
                  if ((sqlrecord == null) || (sqlrecord.size() == 0)) {
                    currentitem.setStyleNumber("");
                  }
                }
              }
            }
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
      return orderdatawithstyle;
    }
    int hiddenkey = orderdata.getHiddenKey().intValue();
    orderdata.setHiddenKey(null);
    orderdata.setEmployeeId(null);
    JOOQ_SaveOrder myorder = new JOOQ_SaveOrder(orderdata, getThreadLocalRequest().getSession());
    int newhiddenkey = myorder.getOrder().getHiddenKey().intValue();
    
    if (orderdata.getOrderType() == OrderData.OrderType.OVERSEAS) {
      FileUtils.copyDirectory(new File("C:\\WorkFlow\\NewWorkflowData\\DomesticData/" + hiddenkey + "/"), new File("C:\\WorkFlow\\NewWorkflowData\\OverseasData/" + newhiddenkey + "/"));
    } else {
      FileUtils.copyDirectory(new File("C:\\WorkFlow\\NewWorkflowData\\OverseasData/" + hiddenkey + "/"), new File("C:\\WorkFlow\\NewWorkflowData\\DomesticData/" + newhiddenkey + "/"));
    }
    
    orderdatawithstyle.setOrderData(myorder.getOrder());
    return orderdatawithstyle;
  }
  

  public String doLogoff()
  {
    getThreadLocalRequest().getSession().invalidate();
    return "Done";
  }
  
  public BasePagingLoadResult<BaseModelData> getSalesReportList(PagingLoadConfig loadConfig) throws Exception {
    try {
      JOOQ_ManageSalesReport myorder = new JOOQ_ManageSalesReport(loadConfig, getThreadLocalRequest().getSession(), false);
      return myorder.getResults();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
  public BasePagingLoadResult<OtherComboBoxModelData> getCategoryList(PagingLoadConfig loadConfig, OrderData.OrderType ordertype)
  {
    String query = (String)loadConfig.get("query");
    Result<Record1<Object>> sqlrecords;
    Result<Record1<Object>> sqlrecords;
    if (ordertype == OrderData.OrderType.OVERSEAS) {
      sqlrecords = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "category" })).from(new TableLike[] { DSL.tableByName(new String[] { "list_categories" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "category" }).like(query + "%").and(DSL.fieldByName(new String[] { "overseas" }).equal(Integer.valueOf(1))) }).fetch();
    } else {
      sqlrecords = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "category" })).from(new TableLike[] { DSL.tableByName(new String[] { "list_categories" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "category" }).like(query + "%").and(DSL.fieldByName(new String[] { "domestic" }).equal(Integer.valueOf(1))) }).fetch();
    }
    
    ArrayList<OtherComboBoxModelData> mylist = new ArrayList();
    for (int i = 0; i < sqlrecords.size(); i++) {
      OtherComboBoxModelData mydata = new OtherComboBoxModelData((String)((Record1)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "category" })), (String)((Record1)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "category" })));
      mylist.add(mydata);
    }
    
    return new BasePagingLoadResult(mylist, loadConfig.getOffset(), sqlrecords.size());
  }
  
  public BasePagingLoadResult<OtherComboBoxModelData> getProductsList(PagingLoadConfig loadConfig, OrderData.OrderType ordertype)
  {
    JOOQ_GetProductsList getproductlist = new JOOQ_GetProductsList(loadConfig, ordertype, getThreadLocalRequest().getSession());
    return getproductlist.results();
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
    try
    {
      JOOQ_ManageOnlineGather myorder = new JOOQ_ManageOnlineGather(loadConfig, getThreadLocalRequest().getSession(), false);
      return myorder.getResults();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
  public BasePagingLoadResult<BaseModelData> getContainerInvoiceList(PagingLoadConfig loadConfig) throws Exception
  {
    try {
      JOOQ_ManageContainerInvoice myorder = new JOOQ_ManageContainerInvoice(loadConfig, getThreadLocalRequest().getSession(), false);
      return myorder.getResults();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
  public Integer saveContainerInvoice(ContainerData containerdata) throws Exception
  {
    JOOQ_SaveContainerInvoice mycontainerinvoice = new JOOQ_SaveContainerInvoice(containerdata, getThreadLocalRequest().getSession());
    return mycontainerinvoice.getContainerData().getHiddenKey();
  }
  
  public ArrayList<String[]> getPricingTable(int hiddenkey) throws Exception
  {
    JOOQ_OrderExpander myorder = new JOOQ_OrderExpander(hiddenkey, getThreadLocalRequest().getSession());
    
    com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderData thedata = myorder.getExpandedOrderData();
    if (thedata.getOrderType() == OrderData.OrderType.DOMESTIC) {
      JOOQ_DomesticPriceCalculation mycalc = new JOOQ_DomesticPriceCalculation(thedata);
      mycalc.calculateCustomerPrice(true);
      return mycalc.getTableRow();
    }
    JOOQ_OverseasPriceCalculation mycalc = new JOOQ_OverseasPriceCalculation(thedata);
    mycalc.calculateOrderPrice(true);
    return mycalc.getTableRow();
  }
}
