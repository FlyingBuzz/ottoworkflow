package com.ottocap.NewWorkFlow.server.RPCService;

import com.extjs.gxt.ui.client.Style.SortDir;
import com.extjs.gxt.ui.client.core.FastMap;
import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.ottocap.NewWorkFlow.server.SQLTable;
import com.ottocap.NewWorkFlow.server.SharedData;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;
import javax.servlet.http.HttpSession;


public class ManageSalesReport
{
  private BasePagingLoadResult<BaseModelData> myresults = null;
  private SQLTable sqltable = new SQLTable();
  private HttpSession httpsession;
  private ArrayList<String[]> csvarray = new ArrayList();
  
  public ManageSalesReport(PagingLoadConfig loadConfig, HttpSession httpsession, boolean exportcsv) throws Exception {
    this.httpsession = httpsession;
    
    checkSession();
    
    String sortfield = loadConfig.getSortField();
    String sortdirection = loadConfig.getSortDir() == Style.SortDir.ASC ? "ASC" : "DESC";
    String sortquery;
    String sortquery; if (sortfield.equals("salesdollars")) {
      sortquery = "`salesdollars` " + sortdirection + " , eclipseaccountnumber ASC";
    } else {
      sortquery = "`" + sortfield + "` " + sortdirection;
    }
    
    String orderdatefilterstring = "";
    String orderwarpupfilterstring = "";
    if (loadConfig.get("orderfrom") != null) {
      orderdatefilterstring = orderdatefilterstring + " AND `orderdate` >= '" + new SimpleDateFormat("yyyy-MM-dd").format(loadConfig.get("orderfrom")) + "' ";
      orderwarpupfilterstring = orderwarpupfilterstring + " AND `ordershipdate` >= '" + new SimpleDateFormat("yyyy-MM-dd").format(loadConfig.get("orderfrom")) + "' ";
    }
    if (loadConfig.get("orderto") != null) {
      orderdatefilterstring = orderdatefilterstring + " AND `orderdate` <= '" + new SimpleDateFormat("yyyy-MM-dd").format(loadConfig.get("orderto")) + "' ";
      orderwarpupfilterstring = orderwarpupfilterstring + " AND `ordershipdate` <= '" + new SimpleDateFormat("yyyy-MM-dd").format(loadConfig.get("orderto")) + "' ";
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
    
    if (loadConfig.get("managetype").equals("report1")) {
      if (!SharedData.isFaya.booleanValue()) {
        if (loadConfig.get("minprofit") != null) {
          this.sqltable.makeTable("SELECT * FROM (SELECT eclipseaccountnumber, company, contactname, MIN( orderdate ) AS \"minorderdate\", SUM( totalprice ) AS \"salesdollars\" FROM ordermain WHERE `orderstatus` = 'Order Completed' AND `eclipseaccountnumber` IS NOT NULL " + orderdatefilterstring + filterstring + " GROUP BY eclipseaccountnumber ORDER BY " + sortquery + ") AS table1 WHERE salesdollars >= " + loadConfig.get("minprofit"));
        } else {
          this.sqltable.makeTable("SELECT eclipseaccountnumber, company, contactname, MIN( orderdate ) AS \"minorderdate\", SUM( totalprice ) AS \"salesdollars\" FROM ordermain WHERE `orderstatus` = 'Order Completed' AND `eclipseaccountnumber` IS NOT NULL " + orderdatefilterstring + filterstring + " GROUP BY eclipseaccountnumber ORDER BY " + sortquery);
        }
      }
      else if (loadConfig.get("minprofit") != null) {
        this.sqltable.makeTable("SELECT * FROM company, contactname, MIN( orderdate ) AS \"minorderdate\", SUM( totalprice ) AS \"salesdollars\" FROM ordermain WHERE `orderstatus` = 'Order Completed' " + orderdatefilterstring + filterstring + " GROUP BY Trim( LOWER( company ) ) ORDER BY " + sortquery + ") AS table1 WHERE salesdollars >= " + loadConfig.get("minprofit"));
      } else {
        this.sqltable.makeTable("SELECT company, contactname, MIN( orderdate ) AS \"minorderdate\", SUM( totalprice ) AS \"salesdollars\" FROM ordermain WHERE `orderstatus` = 'Order Completed' " + orderdatefilterstring + filterstring + " GROUP BY Trim( LOWER( company ) ) ORDER BY " + sortquery);
      }
      
    }
    else if (loadConfig.get("managetype").equals("report2")) {
      String[] batchstring = { "CREATE TEMPORARY TABLE desctable( `stylenumber` varchar(20), `description` text, UNIQUE KEY `stylenumber` (`stylenumber`) );", "INSERT INTO desctable (stylenumber, description) SELECT DISTINCT sku, productname FROM `styles_domestic` GROUP BY sku ON DUPLICATE KEY UPDATE stylenumber=stylenumber;", "INSERT INTO desctable (stylenumber, description) SELECT DISTINCT sku, productname FROM `styles_domestic_totebags` GROUP BY sku ON DUPLICATE KEY UPDATE stylenumber=stylenumber;", "INSERT INTO desctable (stylenumber, description) SELECT DISTINCT sku, productname FROM `styles_domestic_aprons` GROUP BY sku ON DUPLICATE KEY UPDATE stylenumber=stylenumber;", "INSERT INTO desctable (stylenumber, description) SELECT DISTINCT sku, productname FROM `styles_domestic_shirts` GROUP BY sku ON DUPLICATE KEY UPDATE stylenumber=stylenumber;", "INSERT INTO desctable (stylenumber, description) SELECT DISTINCT sku, productname FROM `styles_domestic_sweatshirts` GROUP BY sku ON DUPLICATE KEY UPDATE stylenumber=stylenumber;", "INSERT INTO desctable (stylenumber, description) SELECT DISTINCT Style, Name FROM `styles_lackpard` GROUP BY Style ON DUPLICATE KEY UPDATE stylenumber=stylenumber;", "INSERT INTO desctable (stylenumber, description) SELECT DISTINCT Style, `Emb Tooling Name` FROM `styles_overseas` GROUP BY Style ON DUPLICATE KEY UPDATE stylenumber=stylenumber;", "INSERT INTO desctable (stylenumber, description) SELECT DISTINCT Style, CONCAT( `Style of Cap (Low Profile Pro Style, Pro Style)`  , ' ', `Material Description`  ) FROM `styles_pre-designed` GROUP BY Style ON DUPLICATE KEY UPDATE stylenumber=stylenumber;" };
      this.sqltable.executeBatch(batchstring);
      this.sqltable.makeTable("SELECT newtable.stylenumber,newtable.totalquantity, newtable.minorderdate, desctable.description AS styledescription FROM (SELECT ordermain_sets_items.stylenumber, SUM(ordermain_sets_items.quantity) AS totalquantity, MIN(orderdate) as minorderdate FROM ordermain_sets_items INNER JOIN ordermain ON ordermain_sets_items.hiddenkey = ordermain.hiddenkey INNER JOIN ordermain_sets ON ( ordermain_sets_items.hiddenkey = ordermain_sets.hiddenkey AND ordermain_sets_items.set = ordermain_sets.set ) WHERE ordermain.orderstatus = 'Order Completed' AND ordermain_sets_items.set < ordermain.setcount AND ordermain_sets_items.item < ordermain_sets.itemcount AND ordermain_sets_items.quantity IS NOT NULL AND ordermain_sets_items.stylenumber != '' " + orderdatefilterstring + filterstring + " GROUP BY ordermain_sets_items.stylenumber) AS newtable LEFT JOIN desctable ON newtable.stylenumber = desctable.stylenumber ORDER BY " + sortquery);
      this.sqltable.runQuery("drop table desctable;");
    } else if (loadConfig.get("managetype").equals("report3")) {
      String[] batchstring = { "CREATE TEMPORARY TABLE desctable( `stylenumber` varchar(20), `description` text, UNIQUE KEY `stylenumber` (`stylenumber`) );", "INSERT INTO desctable (stylenumber, description) SELECT DISTINCT sku, productname FROM `styles_domestic` GROUP BY sku ON DUPLICATE KEY UPDATE stylenumber=stylenumber;", "INSERT INTO desctable (stylenumber, description) SELECT DISTINCT sku, productname FROM `styles_domestic_totebags` GROUP BY sku ON DUPLICATE KEY UPDATE stylenumber=stylenumber;", "INSERT INTO desctable (stylenumber, description) SELECT DISTINCT sku, productname FROM `styles_domestic_aprons` GROUP BY sku ON DUPLICATE KEY UPDATE stylenumber=stylenumber;", "INSERT INTO desctable (stylenumber, description) SELECT DISTINCT sku, productname FROM `styles_domestic_shirts` GROUP BY sku ON DUPLICATE KEY UPDATE stylenumber=stylenumber;", "INSERT INTO desctable (stylenumber, description) SELECT DISTINCT sku, productname FROM `styles_domestic_sweatshirts` GROUP BY sku ON DUPLICATE KEY UPDATE stylenumber=stylenumber;", "INSERT INTO desctable (stylenumber, description) SELECT DISTINCT Style, Name FROM `styles_lackpard` GROUP BY Style ON DUPLICATE KEY UPDATE stylenumber=stylenumber;", "INSERT INTO desctable (stylenumber, description) SELECT DISTINCT Style, `Emb Tooling Name` FROM `styles_overseas` GROUP BY Style ON DUPLICATE KEY UPDATE stylenumber=stylenumber;", "INSERT INTO desctable (stylenumber, description) SELECT DISTINCT Style, CONCAT( `Style of Cap (Low Profile Pro Style, Pro Style)`  , ' ', `Material Description`  ) FROM `styles_pre-designed` GROUP BY Style ON DUPLICATE KEY UPDATE stylenumber=stylenumber;" };
      this.sqltable.executeBatch(batchstring);
      if (!SharedData.isFaya.booleanValue()) {
        this.sqltable.makeTable("SELECT newtable.eclipseaccountnumber,newtable.company, newtable.contactname,newtable.stylenumber,newtable.totalquantity,newtable.minorderdate,desctable.description as styledescription FROM (SELECT ordermain.eclipseaccountnumber,ordermain.company, ordermain.contactname, ordermain_sets_items.stylenumber, SUM(ordermain_sets_items.quantity) AS totalquantity, MIN(orderdate) as minorderdate FROM ordermain_sets_items INNER JOIN ordermain ON ordermain_sets_items.hiddenkey = ordermain.hiddenkey INNER JOIN ordermain_sets ON ( ordermain_sets_items.hiddenkey = ordermain_sets.hiddenkey AND ordermain_sets_items.set = ordermain_sets.set ) WHERE ordermain.orderstatus = 'Order Completed' AND ordermain_sets_items.set < ordermain.setcount AND ordermain_sets_items.item < ordermain_sets.itemcount AND ordermain_sets_items.quantity IS NOT NULL AND ordermain_sets_items.stylenumber != '' " + orderdatefilterstring + filterstring + 
          " GROUP BY ordermain_sets_items.stylenumber,ordermain.eclipseaccountnumber) AS newtable LEFT JOIN desctable ON newtable.stylenumber = desctable.stylenumber  ORDER BY " + sortquery);
      } else {
        this.sqltable.makeTable("SELECT newtable.eclipseaccountnumber,newtable.company, newtable.contactname,newtable.stylenumber,newtable.totalquantity,newtable.minorderdate,desctable.description as styledescription FROM (SELECT ordermain.eclipseaccountnumber,ordermain.company, ordermain.contactname, ordermain_sets_items.stylenumber, SUM(ordermain_sets_items.quantity) AS totalquantity, MIN(orderdate) as minorderdate FROM ordermain_sets_items INNER JOIN ordermain ON ordermain_sets_items.hiddenkey = ordermain.hiddenkey INNER JOIN ordermain_sets ON ( ordermain_sets_items.hiddenkey = ordermain_sets.hiddenkey AND ordermain_sets_items.set = ordermain_sets.set ) WHERE ordermain.orderstatus = 'Order Completed' AND ordermain_sets_items.set < ordermain.setcount AND ordermain_sets_items.item < ordermain_sets.itemcount AND ordermain_sets_items.quantity IS NOT NULL AND ordermain_sets_items.stylenumber != '' " + orderdatefilterstring + filterstring + 
          " GROUP BY ordermain_sets_items.stylenumber,Trim( LOWER( company ) )) AS newtable LEFT JOIN desctable ON newtable.stylenumber = desctable.stylenumber  ORDER BY " + sortquery);
      }
      this.sqltable.runQuery("drop table desctable;");
    } else if (loadConfig.get("managetype").equals("report4")) {
      if (loadConfig.get("minprofit") != null) {
        this.sqltable.makeTable("SELECT hiddenkey, embqty, heatqty, osqty, ordernumber, allvendor, workordernumber, company, mainfilename, totalcapprice, totaldecorationprice, employeeid, employee, orderdate, ordershipdate, (totalcapprice + totaldecorationprice) as totalprice FROM `ordermain_sales_report` WHERE (totalcapprice >= " + loadConfig.get("minprofit") + " OR totaldecorationprice >= " + loadConfig.get("minprofit") + ") " + orderwarpupfilterstring + filterstring + "ORDER BY " + sortquery);
      } else {
        this.sqltable.makeTable("SELECT hiddenkey, embqty, heatqty, osqty, ordernumber, allvendor, workordernumber, company, mainfilename, totalcapprice, totaldecorationprice, employeeid, employee, orderdate, ordershipdate, (totalcapprice + totaldecorationprice) as totalprice FROM `ordermain_sales_report` WHERE hiddenkey = hiddenkey " + orderwarpupfilterstring + filterstring + "ORDER BY " + sortquery);
      }
    } else if (loadConfig.get("managetype").equals("report5")) {
      this.sqltable.makeTable("SELECT hiddenkey,ordertype,ordernumber,orderstatus,company,orderdate,totalcapprice,totaldecorationprice,CONCAT(employees.firstname,' ',employees.lastname) AS employeename FROM ordermain JOIN `employees` ON ordermain.employeeid = employees.username WHERE `quotetoorder` IS NOT NULL AND `quotetoorder` != '' " + orderdatefilterstring + filterstring + "  ORDER BY " + sortquery);
    } else if (loadConfig.get("managetype").equals("report6"))
    {
      this.sqltable.makeTable("SELECT * FROM ordermain_sales_report WHERE ordertype = 'Domestic' AND workordernumber IS NOT NULL AND workordernumber != '' " + orderdatefilterstring + filterstring + "  ORDER BY " + sortquery);
    }
    

    ArrayList<FastMap<Object>> mytable = this.sqltable.getTable();
    this.sqltable.closeSQL();
    
    if (exportcsv)
    {
      if (loadConfig.get("managetype").equals("report1")) {
        if (!SharedData.isFaya.booleanValue()) {
          String[] thekeys = { "eclipseaccountnumber", "contactname", "salesdollars", "minorderdate" };
          String[] thekeysname = { "Customer #", "Customer Name", "Sales Dollars", "First Order Date" };
          addTOCSV(thekeys, thekeysname, mytable);
        } else {
          String[] thekeys = { "company", "contactname", "salesdollars", "minorderdate" };
          String[] thekeysname = { "Company", "Customer Name", "Sales Dollars", "First Order Date" };
          addTOCSV(thekeys, thekeysname, mytable);
        }
      } else if (loadConfig.get("managetype").equals("report2")) {
        String[] thekeys = { "stylenumber", "styledescription", "totalquantity, minorderdate" };
        String[] thekeysname = { "Item #", "Item Description", "Units Sold", "First Order Date" };
        addTOCSV(thekeys, thekeysname, mytable);
      } else if (loadConfig.get("managetype").equals("report3")) {
        if (!SharedData.isFaya.booleanValue()) {
          String[] thekeys = { "eclipseaccountnumber", "company", "contactname", "stylenumber", "styledescription", "totalquantity", "minorderdate" };
          String[] thekeysname = { "Customer #", "Company", "Customer Name", "Item #", "Item Description", "Units Sold", "First Order Date" };
          addTOCSV(thekeys, thekeysname, mytable);
        } else {
          String[] thekeys = { "company", "contactname", "stylenumber", "styledescription", "totalquantity", "minorderdate" };
          String[] thekeysname = { "Company", "Customer Name", "Item #", "Item Description", "Units Sold", "First Order Date" };
          addTOCSV(thekeys, thekeysname, mytable);
        }
      } else if (loadConfig.get("managetype").equals("report4")) {
        String[] thekeys = { "embqty", "heatqty", "osqty", "ordernumber", "allvendor", "workordernumber", "company", "mainfilename", "totalcapprice", "totaldecorationprice", "totalprice", "employeeid", "employee", "orderdate", "ordershipdate" };
        String[] thekeysname = { "Embroidery QTY", "Heat Transfer QTY", "Overseas QTY", "INVOICE", "VENDOR", "JOB", "COMPANY", "DESCRIPTION", "PRODUCT PRICE", "SERVICE PRICE", "TOTAL PRICE", "Employee ID", "Employee Name", "Order Date", "Order Ship Date" };
        addTOCSV(thekeys, thekeysname, mytable);
      } else if (loadConfig.get("managetype").equals("report5")) {
        String[] thekeys = { "ordertype", "hiddenkey", "ordernumber", "orderstatus", "company", "orderdate", "employeename", "totalcapprice", "totaldecorationprice" };
        String[] thekeysname = { "Order Type", "Quote Number", "Sales Number", "Status", "Company Name", "Order Date", "Managed By", "Total Cap Price", "Total Decoration Price" };
        addTOCSV(thekeys, thekeysname, mytable);
      } else if (loadConfig.get("managetype").equals("report6")) {
        String[] thekeys = { "workordernumber", "orderdate", "employee", "embvendor", "embventotqty", "embventotcost", "embprocessdate", "embduedate", "cadvendor", "cadventotqty", "cadventotcost", "cadprocessdate ", "cadduedate", "screenvendor", "screenventotqty", "screenventotcost", "screenprocessdate ", "screenduedate", "heatvendor", "heatventotqty", "heatventotcost", "heatprocessdate ", "heatduedate", "dtgvendor", "dtgventotqty", "dtgventotcost", "dtgprocessdate ", "dtgduedate" };
        String[] thekeysname = { "Work Order", "Order Date", "Employee", "Embroidery Vendor", "Embroidery Process Date", "Embroidery Due Date", "CAD Print Vendor", "CAD Print Vendor Total QTY", "CAD Print Vendor Total Cost", "CAD Print Process Date", "CAD Print Due Date", "Screen Print Vendor", "Screen Print Vendor Total QTY", "Screen Print Vendor Total Cost", "Screen Print Process Date", "Screen Print Due Date", "Heat Transfer Vendor", "Heat Transfer Vendor Total QTY", "Heat Transfer Vendor Total Cost", "Heat Transfer Vendor Total QTY", "Heat Transfer Vendor Total Cost", "Heat Transfer Process Date", "Heat Transfer Due Date", "DTG Vendor", "DTG Vendor Total QTY", "DTG Vendor Total Cost", "DTG Process Date", "DTG Due Date" };
        addTOCSV(thekeys, thekeysname, mytable);
      } else {
        String[] thekeys = (String[])((FastMap)mytable.get(0)).keySet().toArray(new String[0]);
        addTOCSV(thekeys, thekeys, mytable);
      }
    } else {
      ArrayList<BaseModelData> mylist = new ArrayList();
      
      for (int i = loadConfig.getOffset(); (i < loadConfig.getOffset() + loadConfig.getLimit()) && (i < mytable.size()); i++) {
        BaseModelData mydata = new BaseModelData();
        
        if (loadConfig.get("managetype").equals("report1")) {
          mydata.set("eclipseaccountnumber", ((FastMap)mytable.get(i)).get("eclipseaccountnumber"));
          mydata.set("company", ((FastMap)mytable.get(i)).get("company"));
          mydata.set("contactname", ((FastMap)mytable.get(i)).get("contactname"));
          mydata.set("minorderdate", ((FastMap)mytable.get(i)).get("minorderdate"));
          if (((FastMap)mytable.get(i)).get("salesdollars") != null) {
            mydata.set("salesdollars", NumberFormat.getCurrencyInstance(Locale.US).format(((FastMap)mytable.get(i)).get("salesdollars")));
          }
        } else if (loadConfig.get("managetype").equals("report2")) {
          mydata.set("stylenumber", ((FastMap)mytable.get(i)).get("stylenumber"));
          mydata.set("styledescription", ((FastMap)mytable.get(i)).get("styledescription"));
          mydata.set("totalquantity", String.valueOf(((FastMap)mytable.get(i)).get("totalquantity")));
          mydata.set("minorderdate", ((FastMap)mytable.get(i)).get("minorderdate"));
        } else if (loadConfig.get("managetype").equals("report3")) {
          mydata.set("eclipseaccountnumber", ((FastMap)mytable.get(i)).get("eclipseaccountnumber"));
          mydata.set("company", ((FastMap)mytable.get(i)).get("company"));
          mydata.set("contactname", ((FastMap)mytable.get(i)).get("contactname"));
          mydata.set("stylenumber", ((FastMap)mytable.get(i)).get("stylenumber"));
          mydata.set("styledescription", ((FastMap)mytable.get(i)).get("styledescription"));
          mydata.set("totalquantity", String.valueOf(((FastMap)mytable.get(i)).get("totalquantity")));
          mydata.set("minorderdate", ((FastMap)mytable.get(i)).get("minorderdate"));
        } else if (loadConfig.get("managetype").equals("report4")) {
          mydata.set("hiddenkey", ((FastMap)mytable.get(i)).get("hiddenkey"));
          mydata.set("embqty", ((FastMap)mytable.get(i)).get("embqty"));
          mydata.set("heatqty", ((FastMap)mytable.get(i)).get("heatqty"));
          mydata.set("osqty", ((FastMap)mytable.get(i)).get("osqty"));
          mydata.set("ordernumber", ((FastMap)mytable.get(i)).get("ordernumber"));
          mydata.set("allvendor", ((FastMap)mytable.get(i)).get("allvendor"));
          mydata.set("workordernumber", ((FastMap)mytable.get(i)).get("workordernumber"));
          mydata.set("company", ((FastMap)mytable.get(i)).get("company"));
          mydata.set("mainfilename", ((FastMap)mytable.get(i)).get("mainfilename"));
          mydata.set("totalcapprice", ((FastMap)mytable.get(i)).get("totalcapprice") != null ? NumberFormat.getCurrencyInstance(Locale.US).format(((FastMap)mytable.get(i)).get("totalcapprice")) : null);
          mydata.set("totaldecorationprice", ((FastMap)mytable.get(i)).get("totaldecorationprice") != null ? NumberFormat.getCurrencyInstance(Locale.US).format(((FastMap)mytable.get(i)).get("totaldecorationprice")) : null);
          mydata.set("totalprice", ((FastMap)mytable.get(i)).get("totalprice") != null ? NumberFormat.getCurrencyInstance(Locale.US).format(((FastMap)mytable.get(i)).get("totalprice")) : null);
          mydata.set("employeeid", ((FastMap)mytable.get(i)).get("employeeid"));
          mydata.set("employee", ((FastMap)mytable.get(i)).get("employee"));
          mydata.set("orderdate", ((FastMap)mytable.get(i)).get("orderdate"));
          mydata.set("ordershipdate", ((FastMap)mytable.get(i)).get("ordershipdate"));
        } else if (loadConfig.get("managetype").equals("report5")) {
          mydata.set("ordertype", ((FastMap)mytable.get(i)).get("ordertype"));
          mydata.set("hiddenkey", String.valueOf(((FastMap)mytable.get(i)).get("hiddenkey")));
          mydata.set("ordernumber", ((FastMap)mytable.get(i)).get("ordernumber"));
          mydata.set("orderstatus", ((FastMap)mytable.get(i)).get("orderstatus"));
          mydata.set("company", ((FastMap)mytable.get(i)).get("company"));
          mydata.set("orderdate", ((FastMap)mytable.get(i)).get("orderdate"));
          mydata.set("employeename", ((FastMap)mytable.get(i)).get("employeename"));
          if (((FastMap)mytable.get(i)).get("totalcapprice") != null) {
            mydata.set("totalcapprice", NumberFormat.getCurrencyInstance(Locale.US).format(((FastMap)mytable.get(i)).get("totalcapprice")));
          }
          if (((FastMap)mytable.get(i)).get("totaldecorationprice") != null) {
            mydata.set("totaldecorationprice", NumberFormat.getCurrencyInstance(Locale.US).format(((FastMap)mytable.get(i)).get("totaldecorationprice")));
          }
        } else if (loadConfig.get("managetype").equals("report6")) {
          mydata.set("workordernumber", ((FastMap)mytable.get(i)).get("workordernumber"));
          mydata.set("orderdate", ((FastMap)mytable.get(i)).get("orderdate"));
          mydata.set("employee", ((FastMap)mytable.get(i)).get("employee"));
          mydata.set("embvendor", ((FastMap)mytable.get(i)).get("embvendor"));
          mydata.set("embventotqty", ((FastMap)mytable.get(i)).get("embventotqty"));
          mydata.set("embventotcost", ((FastMap)mytable.get(i)).get("embventotcost"));
          mydata.set("embprocessdate", ((FastMap)mytable.get(i)).get("embprocessdate"));
          mydata.set("embduedate", ((FastMap)mytable.get(i)).get("embduedate"));
          mydata.set("cadvendor", ((FastMap)mytable.get(i)).get("cadvendor"));
          mydata.set("cadventotqty", ((FastMap)mytable.get(i)).get("cadventotqty"));
          mydata.set("cadventotcost", ((FastMap)mytable.get(i)).get("cadventotcost"));
          mydata.set("cadprocessdate", ((FastMap)mytable.get(i)).get("cadprocessdate"));
          mydata.set("cadduedate", ((FastMap)mytable.get(i)).get("cadduedate"));
          mydata.set("screenvendor", ((FastMap)mytable.get(i)).get("screenvendor"));
          mydata.set("screenventotqty", ((FastMap)mytable.get(i)).get("screenventotqty"));
          mydata.set("screenventotcost", ((FastMap)mytable.get(i)).get("screenventotcost"));
          mydata.set("screenprocessdate", ((FastMap)mytable.get(i)).get("screenprocessdate"));
          mydata.set("screenduedate", ((FastMap)mytable.get(i)).get("screenduedate"));
          mydata.set("heatvendor", ((FastMap)mytable.get(i)).get("heatvendor"));
          mydata.set("heatventotqty", ((FastMap)mytable.get(i)).get("heatventotqty"));
          mydata.set("heatventotcost", ((FastMap)mytable.get(i)).get("heatventotcost"));
          mydata.set("heatprocessdate", ((FastMap)mytable.get(i)).get("heatprocessdate"));
          mydata.set("heatduedate", ((FastMap)mytable.get(i)).get("heatduedate"));
          mydata.set("dtgvendor", ((FastMap)mytable.get(i)).get("dtgvendor"));
          mydata.set("dtgventotqty", ((FastMap)mytable.get(i)).get("dtgventotqty"));
          mydata.set("dtgventotcost", ((FastMap)mytable.get(i)).get("dtgventotcost"));
          mydata.set("dtgprocessdate", ((FastMap)mytable.get(i)).get("dtgprocessdate"));
          mydata.set("dtgduedate", ((FastMap)mytable.get(i)).get("dtgduedate"));
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
  
  private String searchfilter(TreeSet<String> querytags, String currentreport)
  {
    String tempfilterstring = "";
    while (querytags.size() > 0) {
      String query = ((String)querytags.pollFirst()).trim();
      
      if (currentreport.equals("report1")) {
        tempfilterstring = tempfilterstring + " AND (`eclipseaccountnumber` REGEXP '" + query + "'";
        tempfilterstring = tempfilterstring + " OR `company` REGEXP '" + query + "'";
        tempfilterstring = tempfilterstring + " OR `contactname` REGEXP '" + query + "') ";
      } else if (currentreport.equals("report2")) {
        tempfilterstring = tempfilterstring + " AND (ordermain_sets_items.`stylenumber` REGEXP '" + query + "') ";
      } else if (currentreport.equals("report3")) {
        tempfilterstring = tempfilterstring + " AND (ordermain_sets_items.`stylenumber` REGEXP '" + query + "'";
        tempfilterstring = tempfilterstring + " OR `eclipseaccountnumber` REGEXP '" + query + "'";
        tempfilterstring = tempfilterstring + " OR `company` REGEXP '" + query + "'";
        tempfilterstring = tempfilterstring + " OR `contactname` REGEXP '" + query + "') ";
      } else if (currentreport.equals("report4")) {
        tempfilterstring = tempfilterstring + " AND (`hiddenkey` REGEXP '" + query + "'";
        tempfilterstring = tempfilterstring + " OR `embqty` REGEXP '" + query + "'";
        tempfilterstring = tempfilterstring + " OR `heatqty` REGEXP '" + query + "'";
        tempfilterstring = tempfilterstring + " OR `osqty` REGEXP '" + query + "'";
        tempfilterstring = tempfilterstring + " OR `ordernumber` REGEXP '" + query + "'";
        tempfilterstring = tempfilterstring + " OR `allvendor` REGEXP '" + query + "'";
        tempfilterstring = tempfilterstring + " OR `workordernumber` REGEXP '" + query + "'";
        tempfilterstring = tempfilterstring + " OR `company` REGEXP '" + query + "'";
        tempfilterstring = tempfilterstring + " OR `mainfilename` REGEXP '" + query + "'";
        tempfilterstring = tempfilterstring + " OR `totalcapprice` REGEXP '" + query + "'";
        tempfilterstring = tempfilterstring + " OR `totaldecorationprice` REGEXP '" + query + "'";
        tempfilterstring = tempfilterstring + " OR `employeeid` REGEXP '" + query + "'";
        tempfilterstring = tempfilterstring + " OR `employee` REGEXP '" + query + "') ";
      } else if (currentreport.equals("report5")) {
        tempfilterstring = tempfilterstring + " AND (`hiddenkey` REGEXP '" + query + "'";
        tempfilterstring = tempfilterstring + " OR `ordertype` REGEXP '" + query + "'";
        tempfilterstring = tempfilterstring + " OR `ordernumber` REGEXP '" + query + "'";
        tempfilterstring = tempfilterstring + " OR `orderstatus` REGEXP '" + query + "'";
        tempfilterstring = tempfilterstring + " OR `company` REGEXP '" + query + "'";
        tempfilterstring = tempfilterstring + " OR employees.firstname REGEXP '" + query + "'";
        tempfilterstring = tempfilterstring + " OR employees.lastname REGEXP '" + query + "') ";
      } else if (currentreport.equals("report6")) {
        tempfilterstring = tempfilterstring + " AND (`hiddenkey` REGEXP '" + query + "'";
        tempfilterstring = tempfilterstring + " OR `workordernumber` REGEXP '" + query + "'";
        tempfilterstring = tempfilterstring + " OR `employee` REGEXP '" + query + "'";
        tempfilterstring = tempfilterstring + " OR `embvendor` REGEXP '" + query + "'";
        tempfilterstring = tempfilterstring + " OR `cadvendor` REGEXP '" + query + "'";
        tempfilterstring = tempfilterstring + " OR `screenvendor` REGEXP '" + query + "'";
        tempfilterstring = tempfilterstring + " OR `heatvendor` REGEXP '" + query + "'";
        tempfilterstring = tempfilterstring + " OR `dtgvendor` REGEXP '" + query + "') ";
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
