package com.ottocap.NewWorkFlow.server.RPCService;

import com.extjs.gxt.ui.client.Style.SortDir;
import com.extjs.gxt.ui.client.core.FastMap;
import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.ottocap.NewWorkFlow.server.SQLTable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.time.DateUtils;




public class ManageOrders
{
  private BasePagingLoadResult<BaseModelData> myresults = null;
  private SQLTable sqltable = new SQLTable();
  private HttpSession httpsession;
  private ArrayList<String[]> csvarray = new ArrayList();
  
  public ManageOrders(PagingLoadConfig loadConfig, HttpSession httpsession, boolean exportcsv) throws Exception {
    this.httpsession = httpsession;
    checkSession();
    
    String sortfield = loadConfig.getSortField();
    String sortdirection = loadConfig.getSortDir() == Style.SortDir.ASC ? "ASC" : "DESC";
    String sortquery;
    String sortquery; if (sortfield.equals("managedby")) {
      sortquery = "employeefirstname " + sortdirection + " , employeelastname " + sortdirection; } else { String sortquery;
      if (sortfield.equals("quoteordernumber")) {
        sortquery = "ordernumber " + sortdirection + " , hiddenkey " + sortdirection; } else { String sortquery;
        if (sortfield.equals("potentialrepeatstatus")) {
          sortquery = "potentialrepeatfrequency " + sortdirection + " , potentialrepeatdate " + sortdirection; } else { String sortquery;
          if (sortfield.equals("quotenumber")) {
            sortquery = "`hiddenkey` " + sortdirection; } else { String sortquery;
            if (sortfield.equals("internalduedatetimecolor")) {
              sortquery = "`internalduedatetime` " + sortdirection; } else { String sortquery;
              if (sortfield.equals("alertdatecolor")) {
                sortquery = "ISNULL(`alertdate`), `alertdate` " + sortdirection;
              } else
                sortquery = "`" + sortfield + "` " + sortdirection;
            }
          } } } }
    String filterstring = "WHERE ";
    ArrayList<String> difforderstatus = new ArrayList();
    if (loadConfig.get("managetype").equals("quotesorders")) {
      difforderstatus.add("Choose One:");
      difforderstatus.add("New Copy");
      difforderstatus.add("New Order");
      difforderstatus.add("New Quotation");
      difforderstatus.add("Order Wrap-Up Call");
      difforderstatus.add("Partially Shipped");
      difforderstatus.add("Presentation Concept Request");
      difforderstatus.add("Presentation Sample Request");
      difforderstatus.add("Quotation Follow-Up");
      difforderstatus.add("Rush Order");
      difforderstatus.add("Rush Order Request");
      difforderstatus.add("Waiting for Accounting Approval");
      difforderstatus.add("Waiting for Balance");
      difforderstatus.add("Waiting for Creating Artwork");
      difforderstatus.add("Waiting for Creating Film");
      difforderstatus.add("Waiting for Customer Artwork");
      difforderstatus.add("Waiting for Customer Tape");
      difforderstatus.add("Waiting for Deposit");
      difforderstatus.add("Waiting for Digitize");
      difforderstatus.add("Waiting for Heat Press Transfer");
      difforderstatus.add("Waiting for i-Sample");
      difforderstatus.add("Waiting for Logo Color");
      difforderstatus.add("Waiting for New Account Setup");
      difforderstatus.add("Waiting for Overseas Confirmation");
      difforderstatus.add("Waiting for Production Final Approval");
      difforderstatus.add("Waiting for Purchase Order");
      difforderstatus.add("Waiting for Q.C. Report");
      difforderstatus.add("Waiting for Sample");
      difforderstatus.add("Waiting for Sample Approval");
      difforderstatus.add("Waiting for Stock");
      difforderstatus.add("Waiting for Swatch");
      difforderstatus.add("Waiting for Swatch Approval");
      difforderstatus.add("Waiting to Receive Production");
      filterstring = filterstring + orderstatusgenerator(difforderstatus);
    } else if (loadConfig.get("managetype").equals("completedorders")) {
      difforderstatus.add("Order Completed");
      filterstring = filterstring + orderstatusgenerator(difforderstatus);
    } else if (loadConfig.get("managetype").equals("potentialrepeat")) {
      difforderstatus.add("Order Completed");
      filterstring = filterstring + orderstatusgenerator(difforderstatus);
    } else if (loadConfig.get("managetype").equals("lostquoteorders")) {
      difforderstatus.add("Cancelled Order");
      difforderstatus.add("Quot. Lost Delivery Time Issue");
      difforderstatus.add("Quot. Lost Other Issue");
      difforderstatus.add("Quot. Lost Price Issue");
      difforderstatus.add("Quot. Lost Quality Issue");
      filterstring = filterstring + orderstatusgenerator(difforderstatus);
    } else if (loadConfig.get("managetype").equals("virtualsamples")) {
      difforderstatus.add("New Virtual Sample Request");
      difforderstatus.add("Virtual Sample Processing");
      filterstring = filterstring + orderstatusgenerator(difforderstatus);
    } else if (loadConfig.get("managetype").equals("completedvirtualsamples")) {
      difforderstatus.add("Virtual Sample Sent");
      filterstring = filterstring + orderstatusgenerator(difforderstatus);
    }
    
    if (loadConfig.get("managetype").equals("potentialrepeat")) {
      filterstring = filterstring + "AND `potentialrepeatfrequency` != 'Never' AND `potentialrepeatfrequency` != '' AND `potentialrepeatfrequency` != 'None'";
    }
    
    if (!((Boolean)loadConfig.get("viewall")).booleanValue()) {
      filterstring = filterstring + "AND `employeeid` = '" + httpsession.getAttribute("username") + "' ";
    }
    
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
      

      TreeSet<Integer> thehiddenkeystrings = new TreeSet();
      
      String ponumberfilter = "";
      String[] thequeries = (String[])querytags.toArray(new String[0]);
      for (int i = 0; i < thequeries.length; i++) {
        ponumberfilter = ponumberfilter + " `ponumber` REGEXP '" + thequeries[i] + "' OR ";
      }
      ponumberfilter = ponumberfilter.substring(0, ponumberfilter.length() - 4);
      

      if (this.sqltable.makeTable("SELECT `hiddenkey` FROM `ordermain_vendors` WHERE " + ponumberfilter).booleanValue()) {
        for (int i = 0; i < this.sqltable.getTable().size(); i++) {
          thehiddenkeystrings.add((Integer)((FastMap)this.sqltable.getTable().get(i)).get("hiddenkey"));
        }
      }
      
      if (thehiddenkeystrings.size() > 0) {
        String hiddenkeystringfilter = "";
        while (thehiddenkeystrings.size() > 0) {
          int hiddenkeystring = ((Integer)thehiddenkeystrings.pollFirst()).intValue();
          hiddenkeystringfilter = hiddenkeystringfilter + " OR ordermain.hiddenkey=" + hiddenkeystring;
        }
        filterstring = filterstring + "AND (" + searchfilter(querytags) + hiddenkeystringfilter + ")";
      } else {
        filterstring = filterstring + "AND " + searchfilter(querytags);
      }
    }
    


    if (loadConfig.get("orderfrom") != null) {
      filterstring = filterstring + " AND `orderdate` >= '" + new SimpleDateFormat("yyyy-MM-dd").format(loadConfig.get("orderfrom")) + "' ";
    }
    if (loadConfig.get("orderto") != null) {
      filterstring = filterstring + " AND `orderdate` <= '" + new SimpleDateFormat("yyyy-MM-dd").format(loadConfig.get("orderto")) + "' ";
    }
    






    int totalcount = 0;
    
    if (exportcsv) {
      if (loadConfig.get("managetype").equals("completedorders")) {
        this.sqltable.makeTable("SELECT ordermain.* , employees.firstname AS employeefirstname, employees.lastname AS employeelastname, ordermain_vendors.vendornumber AS digitizingvendornumber, list_vendors_domestic.name AS digitizingvendorname FROM `ordermain` JOIN `employees` ON ordermain.employeeid = employees.username LEFT JOIN ordermain_vendors ON (ordermain.hiddenkey = ordermain_vendors.hiddenkey AND `vendortype` = 'digitize') LEFT JOIN `list_vendors_domestic` ON ordermain_vendors.vendornumber = list_vendors_domestic.id " + filterstring + " ORDER BY " + sortquery);
      } else {
        this.sqltable.makeTable("SELECT ordermain.* , employees.firstname AS employeefirstname, employees.lastname AS employeelastname FROM `ordermain` JOIN `employees` ON ordermain.employeeid = employees.username " + filterstring + " ORDER BY " + sortquery);
      }
    }
    else if (loadConfig.get("managetype").equals("completedorders")) {
      this.sqltable.makeTable("SELECT count(*) FROM `ordermain` JOIN `employees` ON ordermain.employeeid = employees.username LEFT JOIN ordermain_vendors ON (ordermain.hiddenkey = ordermain_vendors.hiddenkey AND `vendortype` = 'digitize') LEFT JOIN `list_vendors_domestic` ON ordermain_vendors.vendornumber = list_vendors_domestic.id " + filterstring);
      totalcount = (int)((Long)this.sqltable.getValue()).longValue();
      this.sqltable.makeTable("SELECT ordermain.* , employees.firstname AS employeefirstname, employees.lastname AS employeelastname, ordermain_vendors.vendornumber AS digitizingvendornumber, list_vendors_domestic.name AS digitizingvendorname FROM `ordermain` JOIN `employees` ON ordermain.employeeid = employees.username LEFT JOIN ordermain_vendors ON (ordermain.hiddenkey = ordermain_vendors.hiddenkey AND `vendortype` = 'digitize') LEFT JOIN `list_vendors_domestic` ON ordermain_vendors.vendornumber = list_vendors_domestic.id " + filterstring + " ORDER BY " + sortquery + " LIMIT " + loadConfig.getOffset() + "," + loadConfig.getLimit());
    } else {
      this.sqltable.makeTable("SELECT count(*) FROM `ordermain` JOIN `employees` ON ordermain.employeeid = employees.username " + filterstring);
      totalcount = (int)((Long)this.sqltable.getValue()).longValue();
      this.sqltable.makeTable("SELECT ordermain.* , employees.firstname AS employeefirstname, employees.lastname AS employeelastname FROM `ordermain` JOIN `employees` ON ordermain.employeeid = employees.username " + filterstring + " ORDER BY " + sortquery + " LIMIT " + loadConfig.getOffset() + "," + loadConfig.getLimit());
    }
    


    ArrayList<FastMap<Object>> mytable = this.sqltable.getTable();
    this.sqltable.closeSQL();
    
    if (exportcsv) {
      if (loadConfig.get("managetype").equals("quotesorders")) {
        String[] thekeys = { "ordertype", "hiddenkey", "ordernumber", "customerpo", "internalduedatetime", "alertdate", "orderstatus", "company", "orderdate", "inhanddate", "employeeid", "nextaction", "totalcapprice", "totaldecorationprice" };
        String[] thekeysname = { "Order Type", "Quote Number", "Order Number", "Customer's PO Number", "Statue Due Date/Time", "Alert Date", "Order Status", "Company", "Order Date", "In-Hands Date", "Employee ID", "Next Action", "Total Cap Price", "Total Decoration Price" };
        addTOCSV(thekeys, thekeysname, mytable);
      } else if (loadConfig.get("managetype").equals("completedorders")) {
        String[] thekeys = { "ordertype", "ordernumber", "internalduedatetime", "digitizingvendorname", "totalcapprice", "totaldecorationprice", "totalprice", "potentialrepeatfrequency", "company", "employeeid", "nextaction" };
        String[] thekeysname = { "Order Type", "Order Number", "Completed Date", "Digitizer", "Total Cap Price", "Total Decoration Price", "Total Price", "Potential Projects", "Company", "Employee ID", "Last Action" };
        addTOCSV(thekeys, thekeysname, mytable);
      } else if (loadConfig.get("managetype").equals("potentialrepeat")) {
        String[] thekeys = { "ordertype", "ordernumber", "potentialrepeatfrequency", "potentialrepeatdate", "company", "orderdate", "employeeid", "potentialinternalcomments" };
        String[] thekeysname = { "Order Type", "Order Number", "Potential Projects", "Follow-up Date", "Company", "Order Date", "Employee ID", "Repeat Comments" };
        addTOCSV(thekeys, thekeysname, mytable);
      } else if (loadConfig.get("managetype").equals("lostquoteorders")) {
        String[] thekeys = { "ordertype", "hiddenkey", "ordernumber", "internalduedatetime", "orderstatus", "company", "orderdate", "employeeid", "nextaction" };
        String[] thekeysname = { "Order Type", "Quote Number", "Order Number", "Date/Time Losted", "Order Status", "Company", "Order Date", "Employee ID", "Last Action" };
        addTOCSV(thekeys, thekeysname, mytable);
      } else if (loadConfig.get("managetype").equals("virtualsamples")) {
        String[] thekeys = { "ordertype", "hiddenkey", "customerpo", "internalduedatetime", "orderstatus", "company", "orderdate", "employeeid", "nextaction" };
        String[] thekeysname = { "Order Type", "VS Number", "Customer's PO Number", "Status Due Date/Time", "Virtual Sample Status", "Company", "Request Date", "Employee ID", "Next Action" };
        addTOCSV(thekeys, thekeysname, mytable);
      } else if (loadConfig.get("managetype").equals("completedvirtualsamples")) {
        String[] thekeys = { "ordertype", "hiddenkey", "customerpo", "internalduedatetime", "company", "orderdate", "employeeid", "nextaction" };
        String[] thekeysname = { "Order Type", "VS Number", "Customer's PO Number", "Date Completed", "Company", "Request Date", "Employee ID", "Next Action" };
        addTOCSV(thekeys, thekeysname, mytable);
      } else {
        String[] thekeys = (String[])((FastMap)mytable.get(0)).keySet().toArray(new String[0]);
        addTOCSV(thekeys, thekeys, mytable);
      }
    }
    else {
      ArrayList<BaseModelData> mylist = new ArrayList();
      Date currentdate = new Date();
      Date endday = DateUtils.addMilliseconds(DateUtils.ceiling(new Date(), 5), -1);
      
      SimpleDateFormat datetimeformat = new SimpleDateFormat("MM/dd/y 'at' hh:mm a");
      SimpleDateFormat dateonlyformat = new SimpleDateFormat("MM/dd/y");
      
      for (int i = 0; i < mytable.size(); i++) {
        BaseModelData mydata = new BaseModelData();
        mydata.set("ordertype", ((FastMap)mytable.get(i)).get("ordertype"));
        mydata.set("quotenumber", ((FastMap)mytable.get(i)).get("hiddenkey"));
        mydata.set("ordernumber", ((FastMap)mytable.get(i)).get("ordernumber"));
        mydata.set("customerpo", ((FastMap)mytable.get(i)).get("customerpo"));
        mydata.set("internalduedatetime", ((FastMap)mytable.get(i)).get("internalduedatetime"));
        mydata.set("alertdate", ((FastMap)mytable.get(i)).get("alertdate"));
        mydata.set("orderstatus", ((FastMap)mytable.get(i)).get("orderstatus"));
        mydata.set("company", ((FastMap)mytable.get(i)).get("company"));
        mydata.set("orderdate", ((FastMap)mytable.get(i)).get("orderdate"));
        mydata.set("inhanddate", ((FastMap)mytable.get(i)).get("inhanddate"));
        mydata.set("employeeid", ((FastMap)mytable.get(i)).get("employeeid"));
        mydata.set("managedby", ((FastMap)mytable.get(i)).get("employeefirstname") + " " + ((FastMap)mytable.get(i)).get("employeelastname"));
        mydata.set("nextaction", ((FastMap)mytable.get(i)).get("nextaction"));
        mydata.set("potentialrepeatfrequency", ((FastMap)mytable.get(i)).get("potentialrepeatfrequency"));
        mydata.set("potentialinternalcomments", ((FastMap)mytable.get(i)).get("potentialinternalcomments"));
        mydata.set("digitizingvendorname", ((FastMap)mytable.get(i)).get("digitizingvendorname"));
        
        if (((FastMap)mytable.get(i)).get("potentialrepeatdate") != null) {
          if (((Date)((FastMap)mytable.get(i)).get("potentialrepeatdate")).before(currentdate)) {
            mydata.set("potentialrepeatdate", "<font color='red'>" + dateonlyformat.format(((FastMap)mytable.get(i)).get("potentialrepeatdate")) + "</font>");
          } else {
            mydata.set("potentialrepeatdate", dateonlyformat.format(((FastMap)mytable.get(i)).get("potentialrepeatdate")));
          }
        }
        

        if (((FastMap)mytable.get(i)).get("totalcapprice") != null) {
          mydata.set("totalcapprice", NumberFormat.getCurrencyInstance(Locale.US).format(((FastMap)mytable.get(i)).get("totalcapprice")));
        }
        if (((FastMap)mytable.get(i)).get("totaldecorationprice") != null) {
          mydata.set("totaldecorationprice", NumberFormat.getCurrencyInstance(Locale.US).format(((FastMap)mytable.get(i)).get("totaldecorationprice")));
        }
        
        if (((FastMap)mytable.get(i)).get("totalprice") != null) {
          mydata.set("totalprice", NumberFormat.getCurrencyInstance(Locale.US).format(((FastMap)mytable.get(i)).get("totalprice")));
        }
        
        if ((((FastMap)mytable.get(i)).get("ordernumber") != null) && (!((FastMap)mytable.get(i)).get("ordernumber").equals(""))) {
          mydata.set("quoteordernumber", ((FastMap)mytable.get(i)).get("ordernumber"));
        } else {
          mydata.set("quoteordernumber", ((FastMap)mytable.get(i)).get("hiddenkey"));
        }
        if ((((FastMap)mytable.get(i)).get("potentialrepeatfrequency") == null) || (((FastMap)mytable.get(i)).get("potentialrepeatfrequency").equals("")) || (((FastMap)mytable.get(i)).get("potentialrepeatfrequency").equals("Never"))) {
          mydata.set("potentialrepeatstatus", "Never");
        }
        else if (((FastMap)mytable.get(i)).get("potentialrepeatdate") != null) {
          mydata.set("potentialrepeatstatus", ((FastMap)mytable.get(i)).get("potentialrepeatfrequency") + " At " + dateonlyformat.format(((FastMap)mytable.get(i)).get("potentialrepeatdate")));
        } else {
          mydata.set("potentialrepeatstatus", ((FastMap)mytable.get(i)).get("potentialrepeatfrequency"));
        }
        
        if (((FastMap)mytable.get(i)).get("internalduedatetime") != null) {
          Date fielddate = (Date)((FastMap)mytable.get(i)).get("internalduedatetime");
          if ((fielddate.before(currentdate)) || (fielddate.equals(currentdate))) {
            mydata.set("internalduedatetimecolor", "<font color='red'>" + datetimeformat.format(((FastMap)mytable.get(i)).get("internalduedatetime")) + "</font>");
          } else if ((fielddate.after(currentdate)) && (fielddate.before(endday))) {
            mydata.set("internalduedatetimecolor", "<font color='green'>" + datetimeformat.format(((FastMap)mytable.get(i)).get("internalduedatetime")) + "</font>");
          } else {
            mydata.set("internalduedatetimecolor", datetimeformat.format(((FastMap)mytable.get(i)).get("internalduedatetime")));
          }
        }
        

        if ((((FastMap)mytable.get(i)).get("alertdate") != null) && (((FastMap)mytable.get(i)).get("alertdatedue") != null)) {
          Date fielddate = (Date)((FastMap)mytable.get(i)).get("alertdate");
          Date fielddatedue = (Date)((FastMap)mytable.get(i)).get("alertdatedue");
          
          if ((fielddatedue.before(currentdate)) || (fielddatedue.equals(currentdate))) {
            mydata.set("alertdatecolor", "<font color='red'>" + dateonlyformat.format(((FastMap)mytable.get(i)).get("alertdatedue")) + "</font>");
          } else if ((fielddate.before(currentdate)) || (fielddate.equals(currentdate))) {
            mydata.set("alertdatecolor", "<font color='#FFCC00'>" + dateonlyformat.format(((FastMap)mytable.get(i)).get("alertdatedue")) + "</font>");
          } else {
            mydata.set("alertdatecolor", dateonlyformat.format(((FastMap)mytable.get(i)).get("alertdatedue")));
          }
        }
        

        mylist.add(mydata);
      }
      this.myresults = new BasePagingLoadResult(mylist, loadConfig.getOffset(), totalcount);
    }
  }
  
  private void addTOCSV(String[] thekeys, String[] thekeysname, ArrayList<FastMap<Object>> mytable)
  {
    this.csvarray.add(thekeysname);
    for (int i = 0; i < mytable.size(); i++) {
      String[] currentrow = new String[thekeys.length];
      for (int j = 0; j < thekeys.length; j++) {
        currentrow[j] = String.valueOf(((FastMap)mytable.get(i)).get(thekeys[j]));
      }
      this.csvarray.add(currentrow);
    }
  }
  
  private String searchfilter(TreeSet<String> querytags)
  {
    String tempfilterstring = "";
    while (querytags.size() > 0) {
      String query = ((String)querytags.pollFirst()).trim();
      tempfilterstring = tempfilterstring + " (ordermain.`hiddenkey` REGEXP '" + query + "'";
      tempfilterstring = tempfilterstring + " OR `ordertype` REGEXP '" + query + "'";
      tempfilterstring = tempfilterstring + " OR `contactname` REGEXP '" + query + "'";
      tempfilterstring = tempfilterstring + " OR `eclipseaccountnumber` REGEXP '" + query + "'";
      tempfilterstring = tempfilterstring + " OR `phone` REGEXP '" + query + "'";
      tempfilterstring = tempfilterstring + " OR `company` REGEXP '" + query + "'";
      tempfilterstring = tempfilterstring + " OR `ordermain`.`email` REGEXP '" + query + "'";
      tempfilterstring = tempfilterstring + " OR `fax` REGEXP '" + query + "'";
      tempfilterstring = tempfilterstring + " OR `employeeid` REGEXP '" + query + "'";
      tempfilterstring = tempfilterstring + " OR `employees`.`firstname` REGEXP '" + query + "'";
      tempfilterstring = tempfilterstring + " OR `employees`.`lastname` REGEXP '" + query + "'";
      tempfilterstring = tempfilterstring + " OR `employees`.`email` REGEXP '" + query + "'";
      tempfilterstring = tempfilterstring + " OR `ordernumber` REGEXP '" + query + "'";
      tempfilterstring = tempfilterstring + " OR `customerpo` REGEXP '" + query + "'";
      tempfilterstring = tempfilterstring + " OR `nextaction` REGEXP '" + query + "'";
      tempfilterstring = tempfilterstring + " OR `searchtags` REGEXP '" + query + "'";
      tempfilterstring = tempfilterstring + " OR `orderstatus` REGEXP '" + query + "') ";
      tempfilterstring = tempfilterstring + " AND ";
    }
    tempfilterstring = tempfilterstring.substring(0, tempfilterstring.length() - 5);
    return tempfilterstring;
  }
  
  private String orderstatusgenerator(ArrayList<String> orderstatus) {
    String orderstatusstring = "(";
    for (String currentorderstatus : orderstatus) {
      orderstatusstring = orderstatusstring + "`orderstatus` = '" + currentorderstatus + "' OR ";
    }
    orderstatusstring = orderstatusstring.substring(0, orderstatusstring.length() - 4) + ") ";
    
    return orderstatusstring;
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
