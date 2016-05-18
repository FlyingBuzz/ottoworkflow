package com.ottocap.NewWorkFlow.server.RPCService;

import com.extjs.gxt.ui.client.Style.SortDir;
import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.ottocap.NewWorkFlow.server.JOOQSQL;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpSession;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.jooq.SelectJoinPartitionByStep;
import org.jooq.SelectJoinStep;
import org.jooq.SelectLimitStep;
import org.jooq.SelectOnConditionStep;
import org.jooq.SelectSelectStep;
import org.jooq.SortField;
import org.jooq.impl.DSL;

public class JOOQ_ManageOrders
{
  private com.extjs.gxt.ui.client.data.BasePagingLoadResult<BaseModelData> myresults = null;
  private HttpSession httpsession;
  private ArrayList<String[]> csvarray = new ArrayList();
  
  public JOOQ_ManageOrders(PagingLoadConfig loadConfig, HttpSession httpsession, boolean exportcsv) throws Exception {
    this.httpsession = httpsession;
    checkSession();
    
    ArrayList<String> difforderstatus = new ArrayList();
    Condition statustypes = null;
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
      statustypes = orderstatusgenerator(difforderstatus);
    } else if (loadConfig.get("managetype").equals("completedorders")) {
      difforderstatus.add("Order Completed");
      statustypes = orderstatusgenerator(difforderstatus);
    } else if (loadConfig.get("managetype").equals("potentialrepeat")) {
      difforderstatus.add("Order Completed");
      statustypes = orderstatusgenerator(difforderstatus);
    } else if (loadConfig.get("managetype").equals("lostquoteorders")) {
      difforderstatus.add("Cancelled Order");
      difforderstatus.add("Quot. Lost Delivery Time Issue");
      difforderstatus.add("Quot. Lost Other Issue");
      difforderstatus.add("Quot. Lost Price Issue");
      difforderstatus.add("Quot. Lost Quality Issue");
      statustypes = orderstatusgenerator(difforderstatus);
    } else if (loadConfig.get("managetype").equals("virtualsamples")) {
      difforderstatus.add("New Virtual Sample Request");
      difforderstatus.add("Virtual Sample Processing");
      statustypes = orderstatusgenerator(difforderstatus);
    } else if (loadConfig.get("managetype").equals("completedvirtualsamples")) {
      difforderstatus.add("Virtual Sample Sent");
      statustypes = orderstatusgenerator(difforderstatus);
    }
    
    int totalcount = 0;
    
    Result<?> sqlresults = null;
    
    if (loadConfig.get("managetype").equals("completedorders")) {
      SelectConditionStep<?> wherepart = JOOQSQL.getInstance().create()
        .select(new Field[] {DSL.fieldByName(new String[] {"ordertype" }), DSL.fieldByName(new String[] { "ordermain", "hiddenkey" }), DSL.fieldByName(new String[] { "ordernumber" }), DSL.fieldByName(new String[] { "customerpo" }), DSL.fieldByName(new String[] { "internalduedatetime" }), DSL.fieldByName(new String[] { "alertdate" }), DSL.fieldByName(new String[] { "alertdatedue" }), DSL.fieldByName(new String[] { "orderstatus" }), DSL.fieldByName(new String[] { "company" }), DSL.fieldByName(new String[] { "orderdate" }), DSL.fieldByName(new String[] { "inhanddate" }), DSL.fieldByName(new String[] { "employeeid" }), DSL.fieldByName(new String[] { "nextaction" }), DSL.fieldByName(new String[] { "totalcapprice" }), DSL.fieldByName(new String[] { "totaldecorationprice" }), DSL.fieldByName(new String[] { "totalprice" }), DSL.fieldByName(new String[] { "potentialrepeatfrequency" }), DSL.fieldByName(new String[] { "potentialrepeatdate" }), DSL.fieldByName(new String[] { "potentialinternalcomments" }), DSL.fieldByName(new String[] { "employees", "firstname" }).as("employeefirstname"), DSL.fieldByName(new String[] { "employees", "lastname" }).as("employeelastname"), DSL.fieldByName(new String[] { "ordermain_vendors", "vendornumber" }).as("digitizingvendornumber"), DSL.fieldByName(new String[] { "list_vendors_domestic", "name" }).as("digitizingvendorname") })
        .from(new org.jooq.TableLike[] {DSL.tableByName(new String[] {"ordermain" }) }).join(DSL.tableByName(new String[] { "employees" })).on(new Condition[] { DSL.fieldByName(new String[] { "ordermain", "employeeid" }).equal(DSL.fieldByName(new String[] { "employees", "username" })) }).leftOuterJoin(DSL.tableByName(new String[] { "ordermain_vendors" })).on(new Condition[] { DSL.fieldByName(new String[] { "ordermain", "hiddenkey" }).equal(DSL.fieldByName(new String[] { "ordermain_vendors", "hiddenkey" })).and(DSL.fieldByName(new String[] { "vendortype" }).equal("digitize")) }).leftOuterJoin(DSL.tableByName(new String[] { "list_vendors_domestic" })).on(new Condition[] { DSL.fieldByName(new String[] { "ordermain_vendors", "vendornumber" }).equal(DSL.fieldByName(new String[] { "list_vendors_domestic", "id" })) }).where(new Condition[] { statustypes });
      wherepart = doWherePart(wherepart, loadConfig);
      SelectLimitStep<?> orderbypart = doOrderByPart(wherepart, loadConfig);
      if (exportcsv) {
        sqlresults = orderbypart.fetch();
      } else {
        totalcount = orderbypart.fetchCount();
        sqlresults = orderbypart.limit(loadConfig.getOffset(), loadConfig.getLimit()).fetch();
      }
    } else {
      SelectConditionStep<?> wherepart = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "ordertype" }), DSL.fieldByName(new String[] { "ordermain", "hiddenkey" }), DSL.fieldByName(new String[] { "ordernumber" }), DSL.fieldByName(new String[] { "customerpo" }), DSL.fieldByName(new String[] { "internalduedatetime" }), DSL.fieldByName(new String[] { "alertdate" }), DSL.fieldByName(new String[] { "alertdatedue" }), DSL.fieldByName(new String[] { "orderstatus" }), DSL.fieldByName(new String[] { "company" }), DSL.fieldByName(new String[] { "orderdate" }), DSL.fieldByName(new String[] { "inhanddate" }), DSL.fieldByName(new String[] { "employeeid" }), DSL.fieldByName(new String[] { "nextaction" }), DSL.fieldByName(new String[] { "totalcapprice" }), DSL.fieldByName(new String[] { "totaldecorationprice" }), DSL.fieldByName(new String[] { "totalprice" }), DSL.fieldByName(new String[] { "potentialrepeatfrequency" }), DSL.fieldByName(new String[] { "potentialrepeatdate" }), DSL.fieldByName(new String[] { "potentialinternalcomments" }), DSL.fieldByName(new String[] { "employees", "firstname" }).as("employeefirstname"), DSL.fieldByName(new String[] { "employees", "lastname" }).as("employeelastname")).from(new org.jooq.TableLike[] { DSL.tableByName(new String[] { "ordermain" }) }).join(DSL.tableByName(new String[] { "employees" }))
        .on(new Condition[] {DSL.fieldByName(new String[] { "ordermain", "employeeid" }).equal(DSL.fieldByName(new String[] { "employees", "username" })) }).leftOuterJoin(DSL.tableByName(new String[] { "ordermain_vendors" })).on(new Condition[] { DSL.fieldByName(new String[] { "ordermain", "hiddenkey" }).equal(DSL.fieldByName(new String[] { "ordermain_vendors", "hiddenkey" })).and(DSL.fieldByName(new String[] { "vendortype" }).equal("digitize")) }).leftOuterJoin(DSL.tableByName(new String[] { "list_vendors_domestic" })).on(new Condition[] { DSL.fieldByName(new String[] { "ordermain_vendors", "vendornumber" }).equal(DSL.fieldByName(new String[] { "list_vendors_domestic", "id" })) }).where(new Condition[] { statustypes });
      wherepart = doWherePart(wherepart, loadConfig);
      SelectLimitStep<?> orderbypart = doOrderByPart(wherepart, loadConfig);
      if (exportcsv) {
        sqlresults = orderbypart.fetch();
      } else {
        totalcount = orderbypart.fetchCount();
        sqlresults = orderbypart.limit(loadConfig.getOffset(), loadConfig.getLimit()).fetch();
      }
    }
    
    if (exportcsv) {
      if (loadConfig.get("managetype").equals("quotesorders")) {
        String[] thekeys = { "ordertype", "hiddenkey", "ordernumber", "customerpo", "internalduedatetime", "alertdate", "orderstatus", "company", "orderdate", "inhanddate", "employeeid", "nextaction", "totalcapprice", "totaldecorationprice" };
        String[] thekeysname = { "Order Type", "Quote Number", "Order Number", "Customer's PO Number", "Statue Due Date/Time", "Alert Date", "Order Status", "Company", "Order Date", "In-Hands Date", "Employee ID", "Next Action", "Total Cap Price", "Total Decoration Price" };
        addTOCSV(thekeys, thekeysname, sqlresults);
      } else if (loadConfig.get("managetype").equals("completedorders")) {
        String[] thekeys = { "ordertype", "ordernumber", "internalduedatetime", "digitizingvendorname", "totalcapprice", "totaldecorationprice", "totalprice", "potentialrepeatfrequency", "company", "employeeid", "nextaction" };
        String[] thekeysname = { "Order Type", "Order Number", "Completed Date", "Digitizer", "Total Cap Price", "Total Decoration Price", "Total Price", "Pontential Repeat Status", "Company", "Employee ID", "Last Action" };
        addTOCSV(thekeys, thekeysname, sqlresults);
      } else if (loadConfig.get("managetype").equals("potentialrepeat")) {
        String[] thekeys = { "ordertype", "ordernumber", "potentialrepeatfrequency", "potentialrepeatdate", "company", "orderdate", "employeeid", "potentialinternalcomments" };
        String[] thekeysname = { "Order Type", "Order Number", "Potential Projects", "Follow-up Date", "Company", "Order Date", "Employee ID", "Repeat Comments" };
        addTOCSV(thekeys, thekeysname, sqlresults);
      } else if (loadConfig.get("managetype").equals("lostquoteorders")) {
        String[] thekeys = { "ordertype", "hiddenkey", "ordernumber", "internalduedatetime", "orderstatus", "company", "orderdate", "employeeid", "nextaction" };
        String[] thekeysname = { "Order Type", "Quote Number", "Order Number", "Date/Time Losted", "Order Status", "Company", "Order Date", "Employee ID", "Last Action" };
        addTOCSV(thekeys, thekeysname, sqlresults);
      } else if (loadConfig.get("managetype").equals("virtualsamples")) {
        String[] thekeys = { "ordertype", "hiddenkey", "customerpo", "internalduedatetime", "orderstatus", "company", "orderdate", "employeeid", "nextaction" };
        String[] thekeysname = { "Order Type", "VS Number", "Customer's PO Number", "Status Due Date/Time", "Virtual Sample Status", "Company", "Request Date", "Employee ID", "Next Action" };
        addTOCSV(thekeys, thekeysname, sqlresults);
      } else if (loadConfig.get("managetype").equals("completedvirtualsamples")) {
        String[] thekeys = { "ordertype", "hiddenkey", "customerpo", "internalduedatetime", "company", "orderdate", "employeeid", "nextaction" };
        String[] thekeysname = { "Order Type", "VS Number", "Customer's PO Number", "Date Completed", "Company", "Request Date", "Employee ID", "Next Action" };
        addTOCSV(thekeys, thekeysname, sqlresults);
      } else {
        Field[] thefields = sqlresults.fields();
        ArrayList<String> fieldnames = new ArrayList();
        for (int i = 0; i < thefields.length; i++) {
          fieldnames.add(thefields[i].getName());
        }
        String[] thekeys = (String[])fieldnames.toArray(new String[0]);
        addTOCSV(thekeys, thekeys, sqlresults);
      }
    }
    else {
      ArrayList<BaseModelData> mylist = new ArrayList();
      Date currentdate = new Date();
      Date endday = org.apache.commons.lang3.time.DateUtils.addMilliseconds(org.apache.commons.lang3.time.DateUtils.ceiling(new Date(), 5), -1);
      
      SimpleDateFormat datetimeformat = new SimpleDateFormat("MM/dd/y 'at' hh:mm a");
      SimpleDateFormat dateonlyformat = new SimpleDateFormat("MM/dd/y");
      
      for (int i = 0; i < sqlresults.size(); i++) {
        BaseModelData mydata = new BaseModelData();
        mydata.set("ordertype", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "ordertype" })));
        mydata.set("quotenumber", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "hiddenkey" })));
        mydata.set("ordernumber", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "ordernumber" })));
        mydata.set("customerpo", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "customerpo" })));
        mydata.set("internalduedatetime", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "internalduedatetime" })));
        mydata.set("alertdate", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "alertdate" })));
        mydata.set("orderstatus", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "orderstatus" })));
        mydata.set("company", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "company" })));
        mydata.set("orderdate", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "orderdate" })));
        mydata.set("inhanddate", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "inhanddate" })));
        mydata.set("employeeid", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "employeeid" })));
        mydata.set("managedby", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "employeefirstname" })) + " " + ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "employeelastname" })));
        mydata.set("nextaction", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "nextaction" })));
        mydata.set("potentialrepeatfrequency", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "potentialrepeatfrequency" })));
        mydata.set("potentialinternalcomments", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "potentialinternalcomments" })));
        if (loadConfig.get("managetype").equals("completedorders")) {
          mydata.set("digitizingvendorname", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "digitizingvendorname" })));
        }
        
        if (((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "potentialrepeatdate" })) != null) {
          if (((Date)((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "potentialrepeatdate" }))).before(currentdate)) {
            mydata.set("potentialrepeatdate", "<font color='red'>" + dateonlyformat.format(((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "potentialrepeatdate" }))) + "</font>");
          } else {
            mydata.set("potentialrepeatdate", dateonlyformat.format(((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "potentialrepeatdate" }))));
          }
        }
        

        if (((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "totalcapprice" })) != null) {
          mydata.set("totalcapprice", NumberFormat.getCurrencyInstance(Locale.US).format(((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "totalcapprice" }))));
        }
        if (((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "totaldecorationprice" })) != null) {
          mydata.set("totaldecorationprice", NumberFormat.getCurrencyInstance(Locale.US).format(((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "totaldecorationprice" }))));
        }
        
        if (((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "totalprice" })) != null) {
          mydata.set("totalprice", NumberFormat.getCurrencyInstance(Locale.US).format(((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "totalprice" }))));
        }
        
        if (((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "ordernumber" })) != null) if (!((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "ordernumber" })).equals("")) {
            mydata.set("quoteordernumber", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "ordernumber" })));
            break label4308; }
        mydata.set("quoteordernumber", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "hiddenkey" })));
        label4308:
        if (((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "potentialrepeatfrequency" })) != null) if (!((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "potentialrepeatfrequency" })).equals("")) if (!((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "potentialrepeatfrequency" })).equals("Never")) break label4431;
        mydata.set("potentialrepeatstatus", "Never");
        break label4598;
        label4431: if (((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "potentialrepeatdate" })) != null) {
          mydata.set("potentialrepeatstatus", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "potentialrepeatfrequency" })) + " At " + dateonlyformat.format(((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "potentialrepeatdate" }))));
        } else {
          mydata.set("potentialrepeatstatus", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "potentialrepeatfrequency" })));
        }
        label4598:
        if (((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "internalduedatetime" })) != null) {
          Date fielddate = (Date)((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "internalduedatetime" }));
          if ((fielddate.before(currentdate)) || (fielddate.equals(currentdate))) {
            mydata.set("internalduedatetimecolor", "<font color='red'>" + datetimeformat.format(((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "internalduedatetime" }))) + "</font>");
          } else if ((fielddate.after(currentdate)) && (fielddate.before(endday))) {
            mydata.set("internalduedatetimecolor", "<font color='green'>" + datetimeformat.format(((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "internalduedatetime" }))) + "</font>");
          } else {
            mydata.set("internalduedatetimecolor", datetimeformat.format(((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "internalduedatetime" }))));
          }
        }
        

        if (((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "alertdate" })) != null) { if (((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "alertdatedue" })) != null) {
            Date fielddate = (Date)((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "alertdate" }));
            Date fielddatedue = (Date)((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "alertdatedue" }));
            
            if ((fielddatedue.before(currentdate)) || (fielddatedue.equals(currentdate))) {
              mydata.set("alertdatecolor", "<font color='red'>" + dateonlyformat.format(((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "alertdatedue" }))) + "</font>");
            } else if ((fielddate.before(currentdate)) || (fielddate.equals(currentdate))) {
              mydata.set("alertdatecolor", "<font color='#FFCC00'>" + dateonlyformat.format(((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "alertdatedue" }))) + "</font>");
            } else {
              mydata.set("alertdatecolor", dateonlyformat.format(((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "alertdatedue" }))));
            }
          }
        }
        
        mylist.add(mydata);
      }
      this.myresults = new com.extjs.gxt.ui.client.data.BasePagingLoadResult(mylist, loadConfig.getOffset(), totalcount);
    }
  }
  

  private SelectLimitStep<?> doOrderByPart(SelectConditionStep<?> wherepart, PagingLoadConfig loadConfig)
  {
    if (loadConfig.getSortField().equals("managedby")) {
      if (loadConfig.getSortDir() == Style.SortDir.ASC) {
        return wherepart.orderBy(new SortField[] { DSL.fieldByName(new String[] { "employeefirstname" }).asc(), DSL.fieldByName(new String[] { "employeelastname" }).asc() });
      }
      return wherepart.orderBy(new SortField[] { DSL.fieldByName(new String[] { "employeefirstname" }).desc(), DSL.fieldByName(new String[] { "employeelastname" }).desc() });
    }
    if (loadConfig.getSortField().equals("quoteordernumber")) {
      if (loadConfig.getSortDir() == Style.SortDir.ASC) {
        return wherepart.orderBy(new SortField[] { DSL.fieldByName(new String[] { "ordernumber" }).asc(), DSL.fieldByName(new String[] { "hiddenkey" }).asc() });
      }
      return wherepart.orderBy(new SortField[] { DSL.fieldByName(new String[] { "ordernumber" }).desc(), DSL.fieldByName(new String[] { "hiddenkey" }).desc() });
    }
    if (loadConfig.getSortField().equals("potentialrepeatstatus")) {
      if (loadConfig.getSortDir() == Style.SortDir.ASC) {
        return wherepart.orderBy(new SortField[] { DSL.fieldByName(new String[] { "potentialrepeatfrequency" }).asc(), DSL.fieldByName(new String[] { "potentialrepeatdate" }).asc() });
      }
      return wherepart.orderBy(new SortField[] { DSL.fieldByName(new String[] { "potentialrepeatfrequency" }).desc(), DSL.fieldByName(new String[] { "potentialrepeatdate" }).desc() });
    }
    if (loadConfig.getSortField().equals("quotenumber")) {
      if (loadConfig.getSortDir() == Style.SortDir.ASC) {
        return wherepart.orderBy(new SortField[] { DSL.fieldByName(new String[] { "hiddenkey" }).asc() });
      }
      return wherepart.orderBy(new SortField[] { DSL.fieldByName(new String[] { "hiddenkey" }).desc() });
    }
    if (loadConfig.getSortField().equals("internalduedatetimecolor")) {
      if (loadConfig.getSortDir() == Style.SortDir.ASC) {
        return wherepart.orderBy(new SortField[] { DSL.fieldByName(new String[] { "internalduedatetime" }).asc() });
      }
      return wherepart.orderBy(new SortField[] { DSL.fieldByName(new String[] { "internalduedatetime" }).desc() });
    }
    if (loadConfig.getSortField().equals("alertdatecolor")) {
      if (loadConfig.getSortDir() == Style.SortDir.ASC) {
        return wherepart.orderBy(new SortField[] { DSL.fieldByName(new String[] { "alertdate" }).asc().nullsLast() });
      }
      return wherepart.orderBy(new SortField[] { DSL.fieldByName(new String[] { "alertdate" }).desc().nullsLast() });
    }
    
    if (loadConfig.getSortDir() == Style.SortDir.ASC) {
      return wherepart.orderBy(new SortField[] { DSL.fieldByName(new String[] { loadConfig.getSortField() }).asc() });
    }
    return wherepart.orderBy(new SortField[] { DSL.fieldByName(new String[] { loadConfig.getSortField() }).desc() });
  }
  


  private SelectConditionStep<?> doWherePart(SelectConditionStep<?> wherepart, PagingLoadConfig loadConfig)
  {
    if (loadConfig.get("managetype").equals("potentialrepeat")) {
      wherepart = wherepart.and(DSL.fieldByName(new String[] { "potentialrepeatfrequency" }).notEqual("Never")).and(DSL.fieldByName(new String[] { "potentialrepeatfrequency" }).notEqual(""));
    }
    
    if (!((Boolean)loadConfig.get("viewall")).booleanValue()) {
      wherepart = wherepart.and(DSL.fieldByName(new String[] { "employeeid" }).equal(this.httpsession.getAttribute("username")));
    }
    
    if (loadConfig.get("orderfrom") != null) {
      wherepart = wherepart.and(DSL.fieldByName(new String[] { "orderdate" }).greaterOrEqual(loadConfig.get("orderfrom")));
    }
    if (loadConfig.get("orderto") != null) {
      wherepart = wherepart.and(DSL.fieldByName(new String[] { "orderdate" }).lessOrEqual(loadConfig.get("orderto")));
    }
    
    if ((loadConfig.get("searchquery") != null) && (!loadConfig.get("searchquery").equals(""))) {
      ArrayList<String> matchList = new ArrayList();
      Pattern regex = Pattern.compile("[^\\s\"']+|\"([^\"]*)\"|'([^']*)'");
      Matcher regexMatcher = regex.matcher((String)loadConfig.get("searchquery"));
      while (regexMatcher.find()) {
        if (regexMatcher.group(1) != null)
        {
          matchList.add(regexMatcher.group(1));
        } else if (regexMatcher.group(2) != null)
        {
          matchList.add(regexMatcher.group(2));
        }
        else {
          matchList.add(regexMatcher.group());
        }
      }
      
      Condition pocondition = null;
      for (int i = 0; i < matchList.size(); i++) {
        if (pocondition == null) {
          pocondition = DSL.fieldByName(new String[] { "ponumber" }).contains(matchList.get(i));
        } else {
          pocondition = pocondition.or(DSL.fieldByName(new String[] { "ponumber" }).contains(matchList.get(i)));
        }
      }
      Result<Record1<Object>> theporecords = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "hiddenkey" })).from(new org.jooq.TableLike[] { DSL.tableByName(new String[] { "ordermain_vendors" }) }).where(new Condition[] { pocondition }).fetch();
      TreeSet<Integer> thehiddenkeystrings = new TreeSet();
      for (int i = 0; i < theporecords.size(); i++) {
        thehiddenkeystrings.add((Integer)((Record1)theporecords.get(i)).getValue(DSL.fieldByName(new String[] { "hiddenkey" })));
      }
      
      Condition hiddenkeystringscondtion = null;
      while (thehiddenkeystrings.size() > 0) {
        int hiddenkeystring = ((Integer)thehiddenkeystrings.pollFirst()).intValue();
        if (hiddenkeystringscondtion == null) {
          hiddenkeystringscondtion = DSL.fieldByName(new String[] { "ordermain", "hiddenkey" }).equal(Integer.valueOf(hiddenkeystring));
        } else {
          hiddenkeystringscondtion = hiddenkeystringscondtion.or(DSL.fieldByName(new String[] { "ordermain", "hiddenkey" }).equal(Integer.valueOf(hiddenkeystring)));
        }
      }
      

      Condition searchcondition = null;
      for (int i = 0; i < matchList.size(); i++) {
        if (searchcondition == null) {
          searchcondition = 
          

            DSL.fieldByName(new String[] { "ordermain", "hiddenkey" }).equal(DSL.fieldByName(new String[] { "ordermain", "hiddenkey" })).and(DSL.fieldByName(new String[] { "ordermain", "hiddenkey" }).contains(matchList.get(i)).or(DSL.fieldByName(new String[] { "ordertype" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "contactname" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "eclipseaccountnumber" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "phone" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "company" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "ordermain", "email" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "fax" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "employeeid" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "employees", "firstname" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "employees", "lastname" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "employees", "email" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "ordernumber" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "customerpo" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "nextaction" }).contains(matchList.get(i)))
            .or(DSL.fieldByName(new String[] { "searchtags" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "orderstatus" }).contains(matchList.get(i))));
        } else {
          searchcondition = searchcondition.and(DSL.fieldByName(new String[] { "ordermain", "hiddenkey" }).contains(matchList.get(i)).or(DSL.fieldByName(new String[] { "ordertype" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "contactname" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "eclipseaccountnumber" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "phone" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "company" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "ordermain", "email" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "fax" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "employeeid" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "employees", "firstname" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "employees", "lastname" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "employees", "email" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "ordernumber" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "customerpo" }).contains(matchList.get(i)))
            .or(DSL.fieldByName(new String[] { "nextaction" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "searchtags" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "orderstatus" }).contains(matchList.get(i))));
        }
      }
      

      if (hiddenkeystringscondtion != null) {
        hiddenkeystringscondtion = hiddenkeystringscondtion.or(searchcondition);
        wherepart = wherepart.and(hiddenkeystringscondtion);
      } else {
        wherepart = wherepart.and(searchcondition);
      }
    }
    

    return wherepart;
  }
  
  private void addTOCSV(String[] thekeys, String[] thekeysname, Result<?> sqlresults) {
    this.csvarray.add(thekeysname);
    for (int i = 0; i < sqlresults.size(); i++) {
      String[] currentrow = new String[thekeys.length];
      for (int j = 0; j < thekeys.length; j++) {
        currentrow[j] = String.valueOf(((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { thekeys[j] })));
      }
      this.csvarray.add(currentrow);
    }
  }
  
  private Condition orderstatusgenerator(ArrayList<String> orderstatus)
  {
    Condition singlecondition = null;
    
    for (String currentorderstatus : orderstatus) {
      if (singlecondition == null) {
        singlecondition = DSL.fieldByName(new String[] { "orderstatus" }).equal(currentorderstatus);
      } else {
        singlecondition = singlecondition.or(DSL.fieldByName(new String[] { "orderstatus" }).equal(currentorderstatus));
      }
    }
    
    return singlecondition;
  }
  
  public com.extjs.gxt.ui.client.data.BasePagingLoadResult<BaseModelData> getResults() {
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
