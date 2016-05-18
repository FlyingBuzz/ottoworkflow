package com.ottocap.NewWorkFlow.server.RPCService;

import com.extjs.gxt.ui.client.Style.SortDir;
import com.extjs.gxt.ui.client.core.FastMap;
import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.ottocap.NewWorkFlow.server.SQLTable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TreeSet;
import javax.servlet.http.HttpSession;



public class ManageOnlineGather
{
  private BasePagingLoadResult<BaseModelData> myresults = null;
  private SQLTable sqltable = new SQLTable();
  private HttpSession httpsession;
  private ArrayList<String[]> csvarray = new ArrayList();
  
  public ManageOnlineGather(PagingLoadConfig loadConfig, HttpSession httpsession, boolean exportcsv) throws Exception {
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
    
    String orderdatefilterstring = "";
    if (loadConfig.get("orderfrom") != null) {
      orderdatefilterstring = orderdatefilterstring + " AND `orderdate` >= '" + new SimpleDateFormat("yyyy-MM-dd").format(loadConfig.get("orderfrom")) + "' ";
    }
    if (loadConfig.get("orderto") != null) {
      orderdatefilterstring = orderdatefilterstring + " AND `orderdate` <= '" + new SimpleDateFormat("yyyy-MM-dd").format(loadConfig.get("orderto")) + "' ";
    }
    
    String filterstring = "";
    if ((loadConfig.get("searchquery") != null) && (!loadConfig.get("searchquery").equals(""))) {
      TreeSet<String> querytags = new TreeSet();
      String searchstring = (String)loadConfig.get("searchquery");
      boolean inquote = false;
      String temptext = "";
      for (int i = 0; i < searchstring.length(); i++) {
        if ((searchstring.charAt(i) == '"') && (inquote)) {
          if (!temptext.equals("")) {
            querytags.add(temptext);
          }
          temptext = "";
          inquote = false;
        } else if ((searchstring.charAt(i) == '"') && (!inquote)) {
          if (!temptext.equals("")) {
            querytags.add(temptext);
          }
          temptext = "";
          inquote = true;
        }
        if (searchstring.charAt(i) == ' ') {
          if (inquote) {
            temptext = temptext + searchstring.charAt(i);
          } else {
            if (!temptext.equals("")) {
              querytags.add(temptext);
            }
            temptext = "";
          }
        } else if (searchstring.charAt(i) != '"') {
          temptext = temptext + searchstring.charAt(i);
        }
        if ((i + 1 == searchstring.length()) && 
          (!temptext.equals(""))) {
          querytags.add(temptext);
        }
      }
      
      filterstring = filterstring + searchfilter(querytags, (String)loadConfig.get("managetype"));
    }
    
    int totalcount = 0;
    
    if (exportcsv)
    {
      if (loadConfig.get("managetype").equals("newquotesorders")) {
        this.sqltable.makeTable("SELECT * FROM `ordermain`,`employees` WHERE `employeeid` = `username` AND `level` = 0 AND (`orderstatus` = 'New Order' OR `orderstatus` = 'New Quotation' " + orderdatefilterstring + filterstring + ") ORDER BY" + sortquery);
      } else if (loadConfig.get("managetype").equals("exportedquotesorders")) {
        this.sqltable.makeTable("SELECT * FROM `ordermain`,`employees` WHERE `employeeid` = `username` AND `level` = 0 AND (`orderstatus` = 'Exported New Order' OR `orderstatus` = 'Exported New Quotation' " + orderdatefilterstring + filterstring + ") ORDER BY" + sortquery);
      } else if (loadConfig.get("managetype").equals("newvirtualsamples")) {
        this.sqltable.makeTable("SELECT * FROM `ordermain`,`employees` WHERE `employeeid` = `username` AND `level` = 0 AND (`orderstatus` = 'New Virtual Sample Request' " + orderdatefilterstring + filterstring + ") ORDER BY" + sortquery);
      } else if (loadConfig.get("managetype").equals("exportedvirtualsamples")) {
        this.sqltable.makeTable("SELECT * FROM `ordermain`,`employees` WHERE `employeeid` = `username` AND `level` = 0 AND (`orderstatus` = 'Exported New Virtual Sample Request' " + orderdatefilterstring + filterstring + ") ORDER BY" + sortquery);
      }
    }
    else if (loadConfig.get("managetype").equals("newquotesorders")) {
      this.sqltable.makeTable("SELECT count(*) FROM `ordermain`,`employees` WHERE `employeeid` = `username` AND `level` = 0 AND (`orderstatus` = 'New Order' OR `orderstatus` = 'New Quotation' " + orderdatefilterstring + filterstring + ") ORDER BY" + sortquery);
      totalcount = (int)((Long)this.sqltable.getValue()).longValue();
      this.sqltable.makeTable("SELECT * FROM `ordermain`,`employees` WHERE `employeeid` = `username` AND `level` = 0 AND (`orderstatus` = 'New Order' OR `orderstatus` = 'New Quotation' " + orderdatefilterstring + filterstring + ") ORDER BY" + sortquery + " LIMIT " + loadConfig.getOffset() + "," + loadConfig.getLimit());
    } else if (loadConfig.get("managetype").equals("exportedquotesorders")) {
      this.sqltable.makeTable("SELECT count(*) FROM `ordermain`,`employees` WHERE `employeeid` = `username` AND `level` = 0 AND (`orderstatus` = 'Exported New Order' OR `orderstatus` = 'Exported New Quotation' " + orderdatefilterstring + filterstring + ") ORDER BY" + sortquery);
      totalcount = (int)((Long)this.sqltable.getValue()).longValue();
      this.sqltable.makeTable("SELECT * FROM `ordermain`,`employees` WHERE `employeeid` = `username` AND `level` = 0 AND (`orderstatus` = 'Exported New Order' OR `orderstatus` = 'Exported New Quotation' " + orderdatefilterstring + filterstring + ") ORDER BY" + sortquery + " LIMIT " + loadConfig.getOffset() + "," + loadConfig.getLimit());
    } else if (loadConfig.get("managetype").equals("newvirtualsamples")) {
      this.sqltable.makeTable("SELECT count(*) FROM `ordermain`,`employees` WHERE `employeeid` = `username` AND `level` = 0 AND (`orderstatus` = 'New Virtual Sample Request' " + orderdatefilterstring + filterstring + ") ORDER BY" + sortquery);
      totalcount = (int)((Long)this.sqltable.getValue()).longValue();
      this.sqltable.makeTable("SELECT * FROM `ordermain`,`employees` WHERE `employeeid` = `username` AND `level` = 0 AND (`orderstatus` = 'New Virtual Sample Request' " + orderdatefilterstring + filterstring + ") ORDER BY" + sortquery + " LIMIT " + loadConfig.getOffset() + "," + loadConfig.getLimit());
    } else if (loadConfig.get("managetype").equals("exportedvirtualsamples")) {
      this.sqltable.makeTable("SELECT count(*) FROM `ordermain`,`employees` WHERE `employeeid` = `username` AND `level` = 0 AND (`orderstatus` = 'Exported New Virtual Sample Request' " + orderdatefilterstring + filterstring + ") ORDER BY" + sortquery);
      totalcount = (int)((Long)this.sqltable.getValue()).longValue();
      this.sqltable.makeTable("SELECT * FROM `ordermain`,`employees` WHERE `employeeid` = `username` AND `level` = 0 AND (`orderstatus` = 'Exported New Virtual Sample Request' " + orderdatefilterstring + filterstring + ") ORDER BY" + sortquery + " LIMIT " + loadConfig.getOffset() + "," + loadConfig.getLimit());
    }
    


    ArrayList<FastMap<Object>> mytable = this.sqltable.getTable();
    this.sqltable.closeSQL();
    
    if (exportcsv)
    {
      if (loadConfig.get("managetype").equals("newquotesorders")) {
        String[] thekeys = { "ordertype", "hiddenkey", "customerpo", "company", "orderstatus", "orderdate" };
        String[] thekeysname = { "Order Type", "Quote Number", "Customer's PO Number", "Company", "Order Status", "Order Date" };
        addTOCSV(thekeys, thekeysname, mytable);
      } else if (loadConfig.get("managetype").equals("exportedquotesorders")) {
        String[] thekeys = { "ordertype", "hiddenkey", "customerpo", "company", "orderstatus", "orderdate" };
        String[] thekeysname = { "Order Type", "Quote Number", "Customer's PO Number", "Company", "Order Status", "Order Date" };
        addTOCSV(thekeys, thekeysname, mytable);
      } else if (loadConfig.get("managetype").equals("newvirtualsamples")) {
        String[] thekeys = { "ordertype", "hiddenkey", "customerpo", "company", "orderstatus", "orderdate" };
        String[] thekeysname = { "Order Type", "Quote Number", "Customer's PO Number", "Company", "Order Status", "Order Date" };
        addTOCSV(thekeys, thekeysname, mytable);
      } else if (loadConfig.get("managetype").equals("exportedvirtualsamples")) {
        String[] thekeys = { "ordertype", "hiddenkey", "customerpo", "company", "orderstatus", "orderdate" };
        String[] thekeysname = { "Order Type", "Quote Number", "Customer's PO Number", "Company", "Order Status", "Order Date" };
        addTOCSV(thekeys, thekeysname, mytable);
      }
    }
    else {
      ArrayList<BaseModelData> mylist = new ArrayList();
      
      for (int i = 0; i < mytable.size(); i++) {
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
      
      this.myresults = new BasePagingLoadResult(mylist, loadConfig.getOffset(), totalcount);
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
  
  private String searchfilter(TreeSet<String> querytags, String currentreport)
  {
    String tempfilterstring = "";
    while (querytags.size() > 0) {
      String query = ((String)querytags.pollFirst()).trim();
      
      if (currentreport.equals("newquotesorders")) {
        tempfilterstring = tempfilterstring + " AND (`ordertype` REGEXP '" + query + "'";
        tempfilterstring = tempfilterstring + " OR `hiddenkey` REGEXP '" + query + "'";
        tempfilterstring = tempfilterstring + " OR `customerpo` REGEXP '" + query + "'";
        tempfilterstring = tempfilterstring + " OR `company` REGEXP '" + query + "'";
        tempfilterstring = tempfilterstring + " OR `orderstatus` REGEXP '" + query + "') ";
      } else if (currentreport.equals("exportedquotesorders")) {
        tempfilterstring = tempfilterstring + " AND (`ordertype` REGEXP '" + query + "'";
        tempfilterstring = tempfilterstring + " OR `hiddenkey` REGEXP '" + query + "'";
        tempfilterstring = tempfilterstring + " OR `customerpo` REGEXP '" + query + "'";
        tempfilterstring = tempfilterstring + " OR `company` REGEXP '" + query + "'";
        tempfilterstring = tempfilterstring + " OR `orderstatus` REGEXP '" + query + "') ";
      } else if (currentreport.equals("newvirtualsamples")) {
        tempfilterstring = tempfilterstring + " AND (`ordertype` REGEXP '" + query + "'";
        tempfilterstring = tempfilterstring + " OR `hiddenkey` REGEXP '" + query + "'";
        tempfilterstring = tempfilterstring + " OR `customerpo` REGEXP '" + query + "'";
        tempfilterstring = tempfilterstring + " OR `company` REGEXP '" + query + "'";
        tempfilterstring = tempfilterstring + " OR `orderstatus` REGEXP '" + query + "') ";
      } else if (currentreport.equals("exportedvirtualsamples")) {
        tempfilterstring = tempfilterstring + " AND (`ordertype` REGEXP '" + query + "'";
        tempfilterstring = tempfilterstring + " OR `hiddenkey` REGEXP '" + query + "'";
        tempfilterstring = tempfilterstring + " OR `customerpo` REGEXP '" + query + "'";
        tempfilterstring = tempfilterstring + " OR `company` REGEXP '" + query + "'";
        tempfilterstring = tempfilterstring + " OR `orderstatus` REGEXP '" + query + "') ";
      }
    }
    
    return tempfilterstring;
  }
  
  public BasePagingLoadResult<BaseModelData> getResults() {
    return this.myresults;
  }
  
  public ArrayList<String[]> getCSVArray() {
    return this.csvarray;
  }
  
  private void checkSession() throws Exception
  {
    if (this.httpsession.getAttribute("username") != null) {
      if ((((Integer)this.httpsession.getAttribute("level")).intValue() != 1) && (((Integer)this.httpsession.getAttribute("level")).intValue() != 2) && (((Integer)this.httpsession.getAttribute("level")).intValue() != 3) && (((Integer)this.httpsession.getAttribute("level")).intValue() != 4) && (((Integer)this.httpsession.getAttribute("level")).intValue() != 5))
      {
        throw new Exception("Bad Session");
      }
    } else {
      throw new Exception("Session Expired");
    }
  }
}
