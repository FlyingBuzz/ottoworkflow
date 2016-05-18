package com.ottocap.NewWorkFlow.server.RPCService;

import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.ottocap.NewWorkFlow.server.JOOQSQL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import javax.servlet.http.HttpSession;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.jooq.SelectForUpdateStep;
import org.jooq.SelectJoinStep;
import org.jooq.SelectLimitStep;
import org.jooq.SelectSelectStep;
import org.jooq.SortField;
import org.jooq.TableLike;
import org.jooq.impl.DSL;

public class JOOQ_ManageContainerInvoice
{
  private com.extjs.gxt.ui.client.data.BasePagingLoadResult<BaseModelData> myresults = null;
  private HttpSession httpsession;
  private ArrayList<String[]> csvarray = new ArrayList();
  
  public JOOQ_ManageContainerInvoice(PagingLoadConfig loadConfig, HttpSession httpsession, boolean exportcsv) throws Exception {
    this.httpsession = httpsession;
    checkSession();
    
    ArrayList<String> difforderstatus = new ArrayList();
    Condition statustypes = null;
    if (loadConfig.get("managetype").equals("newquotesorders")) {
      difforderstatus.add("New Order");
      difforderstatus.add("New Quotation");
      statustypes = orderstatusgenerator(difforderstatus);
    } else if (loadConfig.get("managetype").equals("exportedquotesorders")) {
      difforderstatus.add("Exported New Order");
      difforderstatus.add("Exported New Quotation");
      statustypes = orderstatusgenerator(difforderstatus);
    } else if (loadConfig.get("managetype").equals("newvirtualsamples")) {
      difforderstatus.add("New Virtual Sample Request");
      statustypes = orderstatusgenerator(difforderstatus);
    } else if (loadConfig.get("managetype").equals("exportedvirtualsamples")) {
      difforderstatus.add("Exported New Virtual Sample Request");
      statustypes = orderstatusgenerator(difforderstatus);
    }
    
    int totalcount = 0;
    
    Result<?> sqlresults = null;
    
    if (loadConfig.get("managetype").equals("newquotesorders")) {
      SelectConditionStep<?> wherepart = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "ordertype" }), DSL.fieldByName(new String[] { "hiddenkey" }), DSL.fieldByName(new String[] { "customerpo" }), DSL.fieldByName(new String[] { "company" }), DSL.fieldByName(new String[] { "orderstatus" }), DSL.fieldByName(new String[] { "orderdate" }), DSL.fieldByName(new String[] { "internalduedatetime" })).from(new TableLike[] { DSL.tableByName(new String[] { "ordermain" }), DSL.tableByName(new String[] { "employees" }) }).where(new Condition[] { statustypes }).and(DSL.fieldByName(new String[] { "employeeid" }).equal(DSL.fieldByName(new String[] { "username" }))).and(DSL.fieldByName(new String[] { "level" }).equal(Integer.valueOf(0)));
      wherepart = doWherePart(wherepart, loadConfig);
      SelectLimitStep<?> orderbypart = doOrderByPart(wherepart, loadConfig);
      if (exportcsv) {
        sqlresults = orderbypart.fetch();
      } else {
        totalcount = orderbypart.fetchCount();
        sqlresults = orderbypart.limit(loadConfig.getOffset(), loadConfig.getLimit()).fetch();
      }
    } else if (loadConfig.get("managetype").equals("exportedquotesorders")) {
      SelectConditionStep<?> wherepart = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "ordertype" }), DSL.fieldByName(new String[] { "hiddenkey" }), DSL.fieldByName(new String[] { "customerpo" }), DSL.fieldByName(new String[] { "company" }), DSL.fieldByName(new String[] { "orderstatus" }), DSL.fieldByName(new String[] { "orderdate" }), DSL.fieldByName(new String[] { "internalduedatetime" })).from(new TableLike[] { DSL.tableByName(new String[] { "ordermain" }), DSL.tableByName(new String[] { "employees" }) }).where(new Condition[] { statustypes }).and(DSL.fieldByName(new String[] { "employeeid" }).equal(DSL.fieldByName(new String[] { "username" }))).and(DSL.fieldByName(new String[] { "level" }).equal(Integer.valueOf(0)));
      wherepart = doWherePart(wherepart, loadConfig);
      SelectLimitStep<?> orderbypart = doOrderByPart(wherepart, loadConfig);
      if (exportcsv) {
        sqlresults = orderbypart.fetch();
      } else {
        totalcount = orderbypart.fetchCount();
        sqlresults = orderbypart.limit(loadConfig.getOffset(), loadConfig.getLimit()).fetch();
      }
    } else if (loadConfig.get("managetype").equals("newvirtualsamples")) {
      SelectConditionStep<?> wherepart = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "ordertype" }), DSL.fieldByName(new String[] { "hiddenkey" }), DSL.fieldByName(new String[] { "customerpo" }), DSL.fieldByName(new String[] { "company" }), DSL.fieldByName(new String[] { "orderstatus" }), DSL.fieldByName(new String[] { "orderdate" }), DSL.fieldByName(new String[] { "internalduedatetime" })).from(new TableLike[] { DSL.tableByName(new String[] { "ordermain" }), DSL.tableByName(new String[] { "employees" }) }).where(new Condition[] { statustypes }).and(DSL.fieldByName(new String[] { "employeeid" }).equal(DSL.fieldByName(new String[] { "username" }))).and(DSL.fieldByName(new String[] { "level" }).equal(Integer.valueOf(0)));
      wherepart = doWherePart(wherepart, loadConfig);
      SelectLimitStep<?> orderbypart = doOrderByPart(wherepart, loadConfig);
      if (exportcsv) {
        sqlresults = orderbypart.fetch();
      } else {
        totalcount = orderbypart.fetchCount();
        sqlresults = orderbypart.limit(loadConfig.getOffset(), loadConfig.getLimit()).fetch();
      }
    } else if (loadConfig.get("managetype").equals("exportedvirtualsamples")) {
      SelectConditionStep<?> wherepart = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "ordertype" }), DSL.fieldByName(new String[] { "hiddenkey" }), DSL.fieldByName(new String[] { "customerpo" }), DSL.fieldByName(new String[] { "company" }), DSL.fieldByName(new String[] { "orderstatus" }), DSL.fieldByName(new String[] { "orderdate" }), DSL.fieldByName(new String[] { "internalduedatetime" })).from(new TableLike[] { DSL.tableByName(new String[] { "ordermain" }), DSL.tableByName(new String[] { "employees" }) }).where(new Condition[] { statustypes }).and(DSL.fieldByName(new String[] { "employeeid" }).equal(DSL.fieldByName(new String[] { "username" }))).and(DSL.fieldByName(new String[] { "level" }).equal(Integer.valueOf(0)));
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
      if (loadConfig.get("managetype").equals("newquotesorders")) {
        String[] thekeys = { "ordertype", "hiddenkey", "customerpo", "company", "orderstatus", "orderdate" };
        String[] thekeysname = { "Order Type", "Quote Number", "Customer's PO Number", "Company", "Order Status", "Order Date" };
        addTOCSV(thekeys, thekeysname, sqlresults);
      } else if (loadConfig.get("managetype").equals("exportedquotesorders")) {
        String[] thekeys = { "ordertype", "hiddenkey", "customerpo", "company", "orderstatus", "orderdate" };
        String[] thekeysname = { "Order Type", "Quote Number", "Customer's PO Number", "Company", "Order Status", "Order Date" };
        addTOCSV(thekeys, thekeysname, sqlresults);
      } else if (loadConfig.get("managetype").equals("newvirtualsamples")) {
        String[] thekeys = { "ordertype", "hiddenkey", "customerpo", "company", "orderstatus", "orderdate" };
        String[] thekeysname = { "Order Type", "Quote Number", "Customer's PO Number", "Company", "Order Status", "Order Date" };
        addTOCSV(thekeys, thekeysname, sqlresults);
      } else if (loadConfig.get("managetype").equals("exportedvirtualsamples")) {
        String[] thekeys = { "ordertype", "hiddenkey", "customerpo", "company", "orderstatus", "orderdate" };
        String[] thekeysname = { "Order Type", "Quote Number", "Customer's PO Number", "Company", "Order Status", "Order Date" };
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
      
      for (int i = 0; i < sqlresults.size(); i++) {
        BaseModelData mydata = new BaseModelData();
        
        if (loadConfig.get("managetype").equals("newquotesorders")) {
          mydata.set("ordertype", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "ordertype" })));
          mydata.set("quotenumber", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "hiddenkey" })));
          mydata.set("customerpo", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "customerpo" })));
          mydata.set("company", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "company" })));
          mydata.set("orderstatus", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "orderstatus" })));
          mydata.set("orderdatetime", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "internalduedatetime" })));
        } else if (loadConfig.get("managetype").equals("exportedquotesorders")) {
          mydata.set("ordertype", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "ordertype" })));
          mydata.set("quotenumber", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "hiddenkey" })));
          mydata.set("customerpo", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "customerpo" })));
          mydata.set("company", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "company" })));
          mydata.set("orderstatus", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "orderstatus" })));
          mydata.set("orderdatetime", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "internalduedatetime" })));
        } else if (loadConfig.get("managetype").equals("newvirtualsamples")) {
          mydata.set("ordertype", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "ordertype" })));
          mydata.set("quotenumber", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "hiddenkey" })));
          mydata.set("customerpo", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "customerpo" })));
          mydata.set("company", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "company" })));
          mydata.set("orderstatus", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "orderstatus" })));
          mydata.set("orderdatetime", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "internalduedatetime" })));
        } else if (loadConfig.get("managetype").equals("exportedvirtualsamples")) {
          mydata.set("ordertype", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "ordertype" })));
          mydata.set("quotenumber", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "hiddenkey" })));
          mydata.set("customerpo", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "customerpo" })));
          mydata.set("company", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "company" })));
          mydata.set("orderstatus", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "orderstatus" })));
          mydata.set("orderdatetime", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "internalduedatetime" })));
        }
        mylist.add(mydata);
      }
      this.myresults = new com.extjs.gxt.ui.client.data.BasePagingLoadResult(mylist, loadConfig.getOffset(), totalcount);
    }
  }
  

  private SelectLimitStep<?> doOrderByPart(SelectConditionStep<?> wherepart, PagingLoadConfig loadConfig)
  {
    if (loadConfig.getSortField().equals("orderdatetime")) {
      if (loadConfig.getSortDir() == com.extjs.gxt.ui.client.Style.SortDir.ASC) {
        return wherepart.orderBy(new SortField[] { DSL.fieldByName(new String[] { "internalduedatetime" }).asc() });
      }
      return wherepart.orderBy(new SortField[] { DSL.fieldByName(new String[] { "internalduedatetime" }).desc() });
    }
    
    if (loadConfig.getSortDir() == com.extjs.gxt.ui.client.Style.SortDir.ASC) {
      return wherepart.orderBy(new SortField[] { DSL.fieldByName(new String[] { loadConfig.getSortField() }).asc() });
    }
    return wherepart.orderBy(new SortField[] { DSL.fieldByName(new String[] { loadConfig.getSortField() }).desc() });
  }
  


  private SelectConditionStep<?> doWherePart(SelectConditionStep<?> wherepart, PagingLoadConfig loadConfig)
  {
    if (loadConfig.get("orderfrom") != null) {
      wherepart = wherepart.and(DSL.fieldByName(new String[] { "orderdate" }).greaterOrEqual(loadConfig.get("orderfrom")));
    }
    if (loadConfig.get("orderto") != null) {
      wherepart = wherepart.and(DSL.fieldByName(new String[] { "orderdate" }).lessOrEqual(loadConfig.get("orderto")));
    }
    
    if ((loadConfig.get("searchquery") != null) && (!loadConfig.get("searchquery").equals(""))) {
      ArrayList<String> matchList = new ArrayList();
      java.util.regex.Pattern regex = java.util.regex.Pattern.compile("[^\\s\"']+|\"([^\"]*)\"|'([^']*)'");
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
      
      Condition searchcondition = null;
      
      if (loadConfig.get("managetype").equals("newquotesorders")) {
        for (int i = 0; i < matchList.size(); i++) {
          if (searchcondition == null) {
            searchcondition = DSL.fieldByName(new String[] { "hiddenkey" }).equal(DSL.fieldByName(new String[] { "hiddenkey" })).and(DSL.fieldByName(new String[] { "hiddenkey" }).contains(matchList.get(i)).or(DSL.fieldByName(new String[] { "ordertype" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "customerpo" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "company" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "orderstatus" }).contains(matchList.get(i))));
          } else {
            searchcondition = searchcondition.and(DSL.fieldByName(new String[] { "ordermain", "hiddenkey" }).contains(matchList.get(i)).or(DSL.fieldByName(new String[] { "ordertype" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "customerpo" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "company" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "orderstatus" }).contains(matchList.get(i))));
          }
        }
      } else if (loadConfig.get("managetype").equals("exportedquotesorders")) {
        for (int i = 0; i < matchList.size(); i++) {
          if (searchcondition == null) {
            searchcondition = DSL.fieldByName(new String[] { "hiddenkey" }).equal(DSL.fieldByName(new String[] { "hiddenkey" })).and(DSL.fieldByName(new String[] { "hiddenkey" }).contains(matchList.get(i)).or(DSL.fieldByName(new String[] { "ordertype" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "customerpo" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "company" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "orderstatus" }).contains(matchList.get(i))));
          } else {
            searchcondition = searchcondition.and(DSL.fieldByName(new String[] { "hiddenkey" }).contains(matchList.get(i)).or(DSL.fieldByName(new String[] { "ordertype" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "customerpo" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "company" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "orderstatus" }).contains(matchList.get(i))));
          }
        }
      } else if (loadConfig.get("managetype").equals("newvirtualsamples")) {
        for (int i = 0; i < matchList.size(); i++) {
          if (searchcondition == null) {
            searchcondition = DSL.fieldByName(new String[] { "hiddenkey" }).equal(DSL.fieldByName(new String[] { "hiddenkey" })).and(DSL.fieldByName(new String[] { "hiddenkey" }).contains(matchList.get(i)).or(DSL.fieldByName(new String[] { "ordertype" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "customerpo" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "company" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "orderstatus" }).contains(matchList.get(i))));
          } else {
            searchcondition = searchcondition.and(DSL.fieldByName(new String[] { "hiddenkey" }).contains(matchList.get(i)).or(DSL.fieldByName(new String[] { "ordertype" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "customerpo" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "company" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "orderstatus" }).contains(matchList.get(i))));
          }
        }
      } else if (loadConfig.get("managetype").equals("exportedvirtualsamples")) {
        for (int i = 0; i < matchList.size(); i++) {
          if (searchcondition == null) {
            searchcondition = DSL.fieldByName(new String[] { "hiddenkey" }).equal(DSL.fieldByName(new String[] { "hiddenkey" })).and(DSL.fieldByName(new String[] { "hiddenkey" }).contains(matchList.get(i)).or(DSL.fieldByName(new String[] { "ordertype" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "customerpo" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "company" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "orderstatus" }).contains(matchList.get(i))));
          } else {
            searchcondition = searchcondition.and(DSL.fieldByName(new String[] { "hiddenkey" }).contains(matchList.get(i)).or(DSL.fieldByName(new String[] { "ordertype" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "customerpo" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "company" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "orderstatus" }).contains(matchList.get(i))));
          }
        }
      }
      
      wherepart = wherepart.and(searchcondition);
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
