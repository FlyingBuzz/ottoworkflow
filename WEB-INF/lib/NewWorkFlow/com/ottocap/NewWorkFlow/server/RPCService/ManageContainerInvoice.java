package com.ottocap.NewWorkFlow.server.RPCService;

import com.extjs.gxt.ui.client.Style.SortDir;
import com.extjs.gxt.ui.client.core.FastMap;
import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.ottocap.NewWorkFlow.server.SQLTable;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;



public class ManageContainerInvoice
{
  private BasePagingLoadResult<BaseModelData> myresults = null;
  private SQLTable sqltable = new SQLTable();
  private HttpSession httpsession;
  private ArrayList<String[]> csvarray = new ArrayList();
  
  public ManageContainerInvoice(PagingLoadConfig loadConfig, HttpSession httpsession, boolean exportcsv) throws Exception {
    this.httpsession = httpsession;
    checkSession();
    
    String sortfield = loadConfig.getSortField();
    String sortdirection = loadConfig.getSortDir() == Style.SortDir.ASC ? "ASC" : "DESC";
    String sortquery;
    String sortquery; if (sortfield.equals("orderdatetime")) {
      sortquery = "`internalduedatetime` " + sortdirection;
    } else {
      sortquery = "`" + sortfield + "` " + sortdirection;
    }
    

















































    if (loadConfig.get("managetype").equals("newquotesorders")) {
      this.sqltable.makeTable("SELECT * FROM `ordermain` WHERE `orderstatus` = 'New Order' OR `orderstatus` = 'New Quotation' ORDER BY" + sortquery);
    } else if (loadConfig.get("managetype").equals("exportedquotesorders")) {
      this.sqltable.makeTable("SELECT * FROM `ordermain` WHERE `orderstatus` = 'Exported New Order' OR `orderstatus` = 'Exported New Quotation' ORDER BY" + sortquery);
    } else if (loadConfig.get("managetype").equals("newvirtualsamples")) {
      this.sqltable.makeTable("SELECT * FROM `ordermain` WHERE `orderstatus` = 'New Virtual Sample Request' ORDER BY" + sortquery);
    } else if (loadConfig.get("managetype").equals("exportedvirtualsamples")) {
      this.sqltable.makeTable("SELECT * FROM `ordermain` WHERE `orderstatus` = 'Exported New Virtual Sample Request' ORDER BY" + sortquery);
    }
    
    ArrayList<FastMap<Object>> mytable = this.sqltable.getTable();
    this.sqltable.closeSQL();
    
    if (exportcsv)
    {
      if (loadConfig.get("managetype").equals("newquotesorders")) {
        String[] thekeys = { "ordertype", "quotenumber", "customerpo", "company", "orderstatus", "orderdate" };
        String[] thekeysname = { "Order Type", "Quote Number", "Customer's PO Number", "Company", "Order Status", "Order Date" };
        addTOCSV(thekeys, thekeysname, mytable);
      } else if (loadConfig.get("managetype").equals("exportedquotesorders")) {
        String[] thekeys = { "ordertype", "quotenumber", "customerpo", "company", "orderstatus", "orderdate" };
        String[] thekeysname = { "Order Type", "Quote Number", "Customer's PO Number", "Company", "Order Status", "Order Date" };
        addTOCSV(thekeys, thekeysname, mytable);
      } else if (loadConfig.get("managetype").equals("newvirtualsamples")) {
        String[] thekeys = { "ordertype", "quotenumber", "customerpo", "company", "orderstatus", "orderdate" };
        String[] thekeysname = { "Order Type", "Quote Number", "Customer's PO Number", "Company", "Order Status", "Order Date" };
        addTOCSV(thekeys, thekeysname, mytable);
      } else if (loadConfig.get("managetype").equals("exportedvirtualsamples")) {
        String[] thekeys = { "ordertype", "quotenumber", "customerpo", "company", "orderstatus", "orderdate" };
        String[] thekeysname = { "Order Type", "Quote Number", "Customer's PO Number", "Company", "Order Status", "Order Date" };
        addTOCSV(thekeys, thekeysname, mytable);
      }
    }
    else {
      ArrayList<BaseModelData> mylist = new ArrayList();
      
      for (int i = loadConfig.getOffset(); (i < loadConfig.getOffset() + loadConfig.getLimit()) && (i < mytable.size()); i++) {
        BaseModelData mydata = new BaseModelData();
        
        if (loadConfig.get("managetype").equals("newquotesorders")) {
          mydata.set("ordertype", ((FastMap)mytable.get(i)).get("ordertype"));
          mydata.set("quotenumber", ((FastMap)mytable.get(i)).get("hiddenkey"));
          mydata.set("customerpo", ((FastMap)mytable.get(i)).get("customerpo"));
          mydata.set("company", ((FastMap)mytable.get(i)).get("company"));
          mydata.set("orderstatus", ((FastMap)mytable.get(i)).get("orderstatus"));
          mydata.set("orderdatetime", ((FastMap)mytable.get(i)).get("internalduedatetime"));
        } else if (loadConfig.get("managetype").equals("exportedquotesorders")) {
          mydata.set("ordertype", ((FastMap)mytable.get(i)).get("ordertype"));
          mydata.set("quotenumber", ((FastMap)mytable.get(i)).get("hiddenkey"));
          mydata.set("customerpo", ((FastMap)mytable.get(i)).get("customerpo"));
          mydata.set("company", ((FastMap)mytable.get(i)).get("company"));
          mydata.set("orderstatus", ((FastMap)mytable.get(i)).get("orderstatus"));
          mydata.set("orderdatetime", ((FastMap)mytable.get(i)).get("internalduedatetime"));
        } else if (loadConfig.get("managetype").equals("newvirtualsamples")) {
          mydata.set("ordertype", ((FastMap)mytable.get(i)).get("ordertype"));
          mydata.set("quotenumber", ((FastMap)mytable.get(i)).get("hiddenkey"));
          mydata.set("customerpo", ((FastMap)mytable.get(i)).get("customerpo"));
          mydata.set("company", ((FastMap)mytable.get(i)).get("company"));
          mydata.set("orderstatus", ((FastMap)mytable.get(i)).get("orderstatus"));
          mydata.set("orderdatetime", ((FastMap)mytable.get(i)).get("internalduedatetime"));
        } else if (loadConfig.get("managetype").equals("exportedvirtualsamples")) {
          mydata.set("ordertype", ((FastMap)mytable.get(i)).get("ordertype"));
          mydata.set("quotenumber", ((FastMap)mytable.get(i)).get("hiddenkey"));
          mydata.set("customerpo", ((FastMap)mytable.get(i)).get("customerpo"));
          mydata.set("company", ((FastMap)mytable.get(i)).get("company"));
          mydata.set("orderstatus", ((FastMap)mytable.get(i)).get("orderstatus"));
          mydata.set("orderdatetime", ((FastMap)mytable.get(i)).get("internalduedatetime"));
        }
        
        mylist.add(mydata);
      }
      
      this.myresults = new BasePagingLoadResult(mylist, loadConfig.getOffset(), mytable.size());
    }
  }
  
  private void addTOCSV(String[] thekeys, String[] thekeysname, ArrayList<FastMap<Object>> mytable) {
    this.csvarray.add(thekeysname);
    for (int i = 0; i < mytable.size(); i++) {
      String[] currentrow = new String[thekeys.length];
      for (int j = 0; j < thekeys.length; j++) {
        currentrow[j] = String.valueOf(((FastMap)mytable.get(i)).get(thekeys[j]));
      }
      this.csvarray.add(currentrow);
    }
  }
  





































  public BasePagingLoadResult<BaseModelData> getResults()
  {
    return this.myresults;
  }
  
  public ArrayList<String[]> getCSVArray() {
    return this.csvarray;
  }
  
  private void checkSession() throws Exception
  {
    if (this.httpsession.getAttribute("username") != null) {
      if (((Integer)this.httpsession.getAttribute("level")).intValue() != 6)
      {
        throw new Exception("Bad Session");
      }
    } else {
      throw new Exception("Session Expired");
    }
  }
}
