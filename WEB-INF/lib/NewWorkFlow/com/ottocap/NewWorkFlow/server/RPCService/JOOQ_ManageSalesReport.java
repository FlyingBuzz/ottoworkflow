package com.ottocap.NewWorkFlow.server.RPCService;

import com.extjs.gxt.ui.client.Style.SortDir;
import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.ottocap.NewWorkFlow.server.JOOQSQL;
import com.ottocap.NewWorkFlow.server.SharedData;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import javax.servlet.http.HttpSession;
import org.jooq.AggregateFunction;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.GroupField;
import org.jooq.InsertValuesStep2;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.jooq.SelectHavingStep;
import org.jooq.SelectJoinStep;
import org.jooq.SelectLimitStep;
import org.jooq.SelectOnConditionStep;
import org.jooq.SelectOnStep;
import org.jooq.SelectSelectStep;
import org.jooq.SortField;
import org.jooq.TableLike;
import org.jooq.impl.DSL;

public class JOOQ_ManageSalesReport
{
  private com.extjs.gxt.ui.client.data.BasePagingLoadResult<BaseModelData> myresults = null;
  private HttpSession httpsession;
  private ArrayList<String[]> csvarray = new ArrayList();
  
  public JOOQ_ManageSalesReport(PagingLoadConfig loadConfig, HttpSession httpsession, boolean exportcsv) {
    this.httpsession = httpsession;
    try {
      checkSession();
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    Result<?> sqlresults = null;
    
    if (loadConfig.get("managetype").equals("report1")) {
      SelectConditionStep<?> wherepart = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "eclipseaccountnumber" }), DSL.fieldByName(new String[] { "company" }), DSL.fieldByName(new String[] { "contactname" }), DSL.min(DSL.fieldByName(new String[] { "orderdate" })).as("minorderdate"), DSL.sum(DSL.fieldByName(Double.class, new String[] { "totalprice" })).as("salesdollars")).from(new TableLike[] { DSL.tableByName(new String[] { "ordermain" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "orderstatus" }).equal("Order Completed") }).and(DSL.fieldByName(new String[] { "eclipseaccountnumber" }).isNotNull());
      wherepart = doWherePart(wherepart, loadConfig);
      SelectHavingStep<?> wherepart2;
      SelectHavingStep<?> wherepart2; if (!SharedData.isFaya.booleanValue()) {
        wherepart2 = wherepart.groupBy(new GroupField[] { DSL.fieldByName(new String[] { "eclipseaccountnumber" }) });
      } else {
        wherepart2 = wherepart.groupBy(new GroupField[] { DSL.trim(DSL.lower(DSL.fieldByName(String.class, new String[] { "company" }))) });
      }
      SelectLimitStep<?> orderbypart = doOrderByPart(wherepart2, loadConfig);
      
      if (loadConfig.get("minprofit") != null) {
        sqlresults = JOOQSQL.getInstance().create().selectFrom(orderbypart.asTable("table1")).where(new Condition[] { DSL.fieldByName(new String[] { "salesdollars" }).greaterOrEqual(loadConfig.get("minprofit")) }).fetch();
      } else {
        sqlresults = orderbypart.fetch();
      }
    }
    else if (loadConfig.get("managetype").equals("report2")) {
      Connection myconnection = JOOQSQL.getInstance().getDataSourceConnection();
      DSLContext create = org.jooq.util.mysql.MySQLDSL.using(myconnection, org.jooq.SQLDialect.MYSQL);
      
      create.batch(new org.jooq.Query[] { create.query("CREATE TEMPORARY TABLE desctable( `stylenumber` varchar(20), `description` text, UNIQUE KEY `stylenumber` (`stylenumber`) )"), create.insertInto(DSL.tableByName(new String[] { "desctable" }), DSL.fieldByName(new String[] { "stylenumber" }), DSL.fieldByName(new String[] { "description" })).select(create.selectDistinct(DSL.fieldByName(new String[] { "sku" }), DSL.fieldByName(new String[] { "productname" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_domestic" }) }).groupBy(new GroupField[] { DSL.fieldByName(new String[] { "sku" }) })), create.insertInto(DSL.tableByName(new String[] { "desctable" }), DSL.fieldByName(new String[] { "stylenumber" }), DSL.fieldByName(new String[] { "description" })).select(create.selectDistinct(DSL.fieldByName(new String[] { "sku" }), DSL.fieldByName(new String[] { "productname" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_domestic_totebags" }) }).groupBy(new GroupField[] { DSL.fieldByName(new String[] { "sku" }) })), create.insertInto(DSL.tableByName(new String[] { "desctable" }), DSL.fieldByName(new String[] { "stylenumber" }), DSL.fieldByName(new String[] { "description" })).select(create.selectDistinct(DSL.fieldByName(new String[] { "sku" }), DSL.fieldByName(new String[] { "productname" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_domestic_aprons" }) }).groupBy(new GroupField[] { DSL.fieldByName(new String[] { "sku" }) })), 
        create.insertInto(DSL.tableByName(new String[] { "desctable" }), DSL.fieldByName(new String[] { "stylenumber" }), DSL.fieldByName(new String[] { "description" })).select(create.selectDistinct(DSL.fieldByName(new String[] { "sku" }), DSL.fieldByName(new String[] { "productname" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_domestic_shirts" }) }).groupBy(new GroupField[] { DSL.fieldByName(new String[] { "sku" }) })), create.insertInto(DSL.tableByName(new String[] { "desctable" }), DSL.fieldByName(new String[] { "stylenumber" }), DSL.fieldByName(new String[] { "description" })).select(create.selectDistinct(DSL.fieldByName(new String[] { "sku" }), DSL.fieldByName(new String[] { "productname" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_domestic_sweatshirts" }) }).groupBy(new GroupField[] { DSL.fieldByName(new String[] { "sku" }) })), create.insertInto(DSL.tableByName(new String[] { "desctable" }), DSL.fieldByName(new String[] { "stylenumber" }), DSL.fieldByName(new String[] { "description" })).select(create.selectDistinct(DSL.fieldByName(new String[] { "Style" }), DSL.fieldByName(new String[] { "Name" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_lackpard" }) }).groupBy(new GroupField[] { DSL.fieldByName(new String[] { "Style" }) })), 
        create.insertInto(DSL.tableByName(new String[] { "desctable" }), DSL.fieldByName(new String[] { "stylenumber" }), DSL.fieldByName(new String[] { "description" })).select(create.selectDistinct(DSL.fieldByName(new String[] { "Style" }), DSL.fieldByName(new String[] { "Emb Tooling Name" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_overseas" }) }).groupBy(new GroupField[] { DSL.fieldByName(new String[] { "Style" }) })), create.insertInto(DSL.tableByName(new String[] { "desctable" }), DSL.fieldByName(new String[] { "stylenumber" }), DSL.fieldByName(String.class, new String[] { "description" })).select(create.selectDistinct(DSL.fieldByName(new String[] { "Style" }), DSL.concat(new Field[] { DSL.fieldByName(new String[] { "Style of Cap (Low Profile Pro Style, Pro Style)" }), DSL.concat(new String[] { " " }), DSL.fieldByName(new String[] { "Material Description" }) })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_pre-designed" }) }).groupBy(new GroupField[] { DSL.fieldByName(new String[] { "Style" }) })) }).execute();
      
      SelectConditionStep<?> wherepart = create.select(DSL.fieldByName(new String[] { "ordermain_sets_items", "stylenumber" }), DSL.sum(DSL.fieldByName(Integer.class, new String[] { "ordermain_sets_items", "quantity" })).as("totalquantity"), DSL.min(DSL.fieldByName(new String[] { "orderdate" })).as("minorderdate")).from(new TableLike[] { DSL.tableByName(new String[] { "ordermain_sets_items" }) }).join(DSL.tableByName(new String[] { "ordermain" })).on(new Condition[] { DSL.fieldByName(new String[] { "ordermain_sets_items", "hiddenkey" }).equal(DSL.fieldByName(new String[] { "ordermain", "hiddenkey" })) }).join(DSL.tableByName(new String[] { "ordermain_sets" })).on(new Condition[] { DSL.fieldByName(new String[] { "ordermain_sets_items", "hiddenkey" }).equal(DSL.fieldByName(new String[] { "ordermain_sets", "hiddenkey" })).and(DSL.fieldByName(new String[] { "ordermain_sets_items", "set" }).equal(DSL.fieldByName(new String[] { "ordermain_sets", "set" }))) }).where(new Condition[] { DSL.fieldByName(new String[] { "ordermain", "orderstatus" }).equal("Order Completed") }).and(DSL.fieldByName(new String[] { "ordermain_sets_items", "set" }).lessThan(DSL.fieldByName(new String[] { "ordermain", "setcount" }))).and(DSL.fieldByName(new String[] { "ordermain_sets_items", "item" }).lessThan(DSL.fieldByName(new String[] { "ordermain_sets", "itemcount" })))
        .and(DSL.fieldByName(new String[] { "ordermain_sets_items", "quantity" }).isNotNull()).and(DSL.fieldByName(new String[] { "ordermain_sets_items", "stylenumber" }).notEqual(""));
      wherepart = doWherePart(wherepart, loadConfig);
      org.jooq.Table<?> newtable = wherepart.groupBy(new GroupField[] { DSL.fieldByName(new String[] { "ordermain_sets_items", "stylenumber" }) }).asTable("newtable");
      SelectOnConditionStep<?> wherepart2 = create.select(DSL.fieldByName(new String[] { "newtable", "stylenumber" }), DSL.fieldByName(new String[] { "newtable", "totalquantity" }), DSL.fieldByName(new String[] { "newtable", "minorderdate" }), DSL.fieldByName(new String[] { "desctable", "description" }).as("styledescription")).from(new TableLike[] { newtable }).leftOuterJoin(DSL.tableByName(new String[] { "desctable" })).on(new Condition[] { DSL.fieldByName(new String[] { "newtable", "stylenumber" }).equal(DSL.fieldByName(new String[] { "desctable", "stylenumber" })) });
      SelectLimitStep<?> orderbypart = doOrderByPart(wherepart2, loadConfig);
      sqlresults = orderbypart.fetch();
      create.query("drop table desctable").execute();
      try {
        myconnection.close();
      }
      catch (SQLException e) {
        e.printStackTrace();
      }
    } else if (loadConfig.get("managetype").equals("report3")) {
      Connection myconnection = JOOQSQL.getInstance().getDataSourceConnection();
      DSLContext create = org.jooq.util.mysql.MySQLDSL.using(myconnection, org.jooq.SQLDialect.MYSQL);
      
      create.batch(new org.jooq.Query[] { create.query("CREATE TEMPORARY TABLE desctable( `stylenumber` varchar(20), `description` text, UNIQUE KEY `stylenumber` (`stylenumber`) )"), create.insertInto(DSL.tableByName(new String[] { "desctable" }), DSL.fieldByName(new String[] { "stylenumber" }), DSL.fieldByName(new String[] { "description" })).select(create.selectDistinct(DSL.fieldByName(new String[] { "sku" }), DSL.fieldByName(new String[] { "productname" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_domestic" }) }).groupBy(new GroupField[] { DSL.fieldByName(new String[] { "sku" }) })), create.insertInto(DSL.tableByName(new String[] { "desctable" }), DSL.fieldByName(new String[] { "stylenumber" }), DSL.fieldByName(new String[] { "description" })).select(create.selectDistinct(DSL.fieldByName(new String[] { "sku" }), DSL.fieldByName(new String[] { "productname" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_domestic_totebags" }) }).groupBy(new GroupField[] { DSL.fieldByName(new String[] { "sku" }) })), create.insertInto(DSL.tableByName(new String[] { "desctable" }), DSL.fieldByName(new String[] { "stylenumber" }), DSL.fieldByName(new String[] { "description" })).select(create.selectDistinct(DSL.fieldByName(new String[] { "sku" }), DSL.fieldByName(new String[] { "productname" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_domestic_aprons" }) }).groupBy(new GroupField[] { DSL.fieldByName(new String[] { "sku" }) })), 
        create.insertInto(DSL.tableByName(new String[] { "desctable" }), DSL.fieldByName(new String[] { "stylenumber" }), DSL.fieldByName(new String[] { "description" })).select(create.selectDistinct(DSL.fieldByName(new String[] { "sku" }), DSL.fieldByName(new String[] { "productname" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_domestic_shirts" }) }).groupBy(new GroupField[] { DSL.fieldByName(new String[] { "sku" }) })), create.insertInto(DSL.tableByName(new String[] { "desctable" }), DSL.fieldByName(new String[] { "stylenumber" }), DSL.fieldByName(new String[] { "description" })).select(create.selectDistinct(DSL.fieldByName(new String[] { "sku" }), DSL.fieldByName(new String[] { "productname" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_domestic_sweatshirts" }) }).groupBy(new GroupField[] { DSL.fieldByName(new String[] { "sku" }) })), create.insertInto(DSL.tableByName(new String[] { "desctable" }), DSL.fieldByName(new String[] { "stylenumber" }), DSL.fieldByName(new String[] { "description" })).select(create.selectDistinct(DSL.fieldByName(new String[] { "Style" }), DSL.fieldByName(new String[] { "Name" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_lackpard" }) }).groupBy(new GroupField[] { DSL.fieldByName(new String[] { "Style" }) })), 
        create.insertInto(DSL.tableByName(new String[] { "desctable" }), DSL.fieldByName(new String[] { "stylenumber" }), DSL.fieldByName(new String[] { "description" })).select(create.selectDistinct(DSL.fieldByName(new String[] { "Style" }), DSL.fieldByName(new String[] { "Emb Tooling Name" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_overseas" }) }).groupBy(new GroupField[] { DSL.fieldByName(new String[] { "Style" }) })), create.insertInto(DSL.tableByName(new String[] { "desctable" }), DSL.fieldByName(new String[] { "stylenumber" }), DSL.fieldByName(String.class, new String[] { "description" })).select(create.selectDistinct(DSL.fieldByName(new String[] { "Style" }), DSL.concat(new Field[] { DSL.fieldByName(new String[] { "Style of Cap (Low Profile Pro Style, Pro Style)" }), DSL.concat(new String[] { " " }), DSL.fieldByName(new String[] { "Material Description" }) })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_pre-designed" }) }).groupBy(new GroupField[] { DSL.fieldByName(new String[] { "Style" }) })) }).execute();
      
      SelectConditionStep<?> wherepart = create.select(DSL.fieldByName(new String[] { "ordermain", "eclipseaccountnumber" }), DSL.fieldByName(new String[] { "ordermain", "company" }), DSL.fieldByName(new String[] { "ordermain", "contactname" }), DSL.fieldByName(new String[] { "ordermain_sets_items", "stylenumber" }), DSL.sum(DSL.fieldByName(Integer.class, new String[] { "ordermain_sets_items", "quantity" })).as("totalquantity"), DSL.min(DSL.fieldByName(new String[] { "orderdate" })).as("minorderdate")).from(new TableLike[] { DSL.tableByName(new String[] { "ordermain_sets_items" }) }).join(DSL.tableByName(new String[] { "ordermain" })).on(new Condition[] { DSL.fieldByName(new String[] { "ordermain_sets_items", "hiddenkey" }).equal(DSL.fieldByName(new String[] { "ordermain", "hiddenkey" })) }).join(DSL.tableByName(new String[] { "ordermain_sets" })).on(new Condition[] { DSL.fieldByName(new String[] { "ordermain_sets_items", "hiddenkey" }).equal(DSL.fieldByName(new String[] { "ordermain_sets", "hiddenkey" })).and(DSL.fieldByName(new String[] { "ordermain_sets_items", "set" }).equal(DSL.fieldByName(new String[] { "ordermain_sets", "set" }))) }).where(new Condition[] { DSL.fieldByName(new String[] { "ordermain", "orderstatus" }).equal("Order Completed") }).and(DSL.fieldByName(new String[] { "ordermain_sets_items", "set" }).lessThan(DSL.fieldByName(new String[] { "ordermain", "setcount" })))
        .and(DSL.fieldByName(new String[] { "ordermain_sets_items", "item" }).lessThan(DSL.fieldByName(new String[] { "ordermain_sets", "itemcount" }))).and(DSL.fieldByName(new String[] { "ordermain_sets_items", "quantity" }).isNotNull()).and(DSL.fieldByName(new String[] { "ordermain_sets_items", "stylenumber" }).notEqual(""));
      wherepart = doWherePart(wherepart, loadConfig);
      org.jooq.Table<?> newtable;
      org.jooq.Table<?> newtable; if (!SharedData.isFaya.booleanValue()) {
        newtable = wherepart.groupBy(new GroupField[] { DSL.fieldByName(new String[] { "ordermain_sets_items", "stylenumber" }), DSL.fieldByName(new String[] { "ordermain", "eclipseaccountnumber" }) }).asTable("newtable");
      } else {
        newtable = wherepart.groupBy(new GroupField[] { DSL.fieldByName(new String[] { "ordermain_sets_items", "stylenumber" }), DSL.trim(DSL.lower(DSL.fieldByName(String.class, new String[] { "company" }))) }).asTable("newtable");
      }
      SelectOnConditionStep<?> wherepart2 = create.select(DSL.fieldByName(new String[] { "newtable", "eclipseaccountnumber" }), DSL.fieldByName(new String[] { "newtable", "company" }), DSL.fieldByName(new String[] { "newtable", "contactname" }), DSL.fieldByName(new String[] { "newtable", "stylenumber" }), DSL.fieldByName(new String[] { "newtable", "totalquantity" }), DSL.fieldByName(new String[] { "newtable", "minorderdate" }), DSL.fieldByName(new String[] { "desctable", "description" }).as("styledescription")).from(new TableLike[] { newtable }).leftOuterJoin(DSL.tableByName(new String[] { "desctable" })).on(new Condition[] { DSL.fieldByName(new String[] { "newtable", "stylenumber" }).equal(DSL.fieldByName(new String[] { "desctable", "stylenumber" })) });
      SelectLimitStep<?> orderbypart = doOrderByPart(wherepart2, loadConfig);
      sqlresults = orderbypart.fetch();
      create.query("drop table desctable").execute();
      try {
        myconnection.close();
      }
      catch (SQLException e) {
        e.printStackTrace();
      }
    }
    else if (loadConfig.get("managetype").equals("report4")) {
      if (loadConfig.get("minprofit") != null) {
        SelectConditionStep<?> wherepart = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "hiddenkey" }), DSL.fieldByName(new String[] { "embqty" }), DSL.fieldByName(new String[] { "heatqty" }), DSL.fieldByName(new String[] { "osqty" }), DSL.fieldByName(new String[] { "ordernumber" }), DSL.fieldByName(new String[] { "allvendor" }), DSL.fieldByName(new String[] { "workordernumber" }), DSL.fieldByName(new String[] { "company" }), DSL.fieldByName(new String[] { "mainfilename" }), DSL.fieldByName(new String[] { "totalcapprice" }), DSL.fieldByName(new String[] { "totaldecorationprice" }), DSL.fieldByName(new String[] { "employeeid" }), DSL.fieldByName(new String[] { "employee" }), DSL.fieldByName(new String[] { "orderdate" }), DSL.fieldByName(new String[] { "ordershipdate" }), DSL.fieldByName(new String[] { "totalcapprice" }).add(DSL.fieldByName(new String[] { "totaldecorationprice" })).as("totalprice")).from(new TableLike[] { DSL.tableByName(new String[] { "ordermain_sales_report" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "totalcapprice" }).greaterOrEqual(loadConfig.get("minprofit")).or(DSL.fieldByName(new String[] { "totaldecorationprice" }).greaterOrEqual(loadConfig.get("minprofit"))) });
        wherepart = doWherePart(wherepart, loadConfig);
        SelectLimitStep<?> orderbypart = doOrderByPart(wherepart, loadConfig);
        sqlresults = orderbypart.fetch();
      } else {
        SelectConditionStep<?> wherepart = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "hiddenkey" }), DSL.fieldByName(new String[] { "embqty" }), DSL.fieldByName(new String[] { "heatqty" }), DSL.fieldByName(new String[] { "osqty" }), DSL.fieldByName(new String[] { "ordernumber" }), DSL.fieldByName(new String[] { "allvendor" }), DSL.fieldByName(new String[] { "workordernumber" }), DSL.fieldByName(new String[] { "company" }), DSL.fieldByName(new String[] { "mainfilename" }), DSL.fieldByName(new String[] { "totalcapprice" }), DSL.fieldByName(new String[] { "totaldecorationprice" }), DSL.fieldByName(new String[] { "employeeid" }), DSL.fieldByName(new String[] { "employee" }), DSL.fieldByName(new String[] { "orderdate" }), DSL.fieldByName(new String[] { "ordershipdate" }), DSL.fieldByName(new String[] { "totalcapprice" }).add(DSL.fieldByName(new String[] { "totaldecorationprice" })).as("totalprice")).from(new TableLike[] { DSL.tableByName(new String[] { "ordermain_sales_report" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "hiddenkey" }).equal(DSL.fieldByName(new String[] { "hiddenkey" })) });
        wherepart = doWherePart(wherepart, loadConfig);
        SelectLimitStep<?> orderbypart = doOrderByPart(wherepart, loadConfig);
        sqlresults = orderbypart.fetch();
      }
    }
    else if (loadConfig.get("managetype").equals("report5")) {
      SelectConditionStep<?> wherepart = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "hiddenkey" }), DSL.fieldByName(new String[] { "ordertype" }), DSL.fieldByName(new String[] { "ordernumber" }), DSL.fieldByName(new String[] { "orderstatus" }), DSL.fieldByName(new String[] { "company" }), DSL.fieldByName(new String[] { "orderdate" }), DSL.fieldByName(new String[] { "totalcapprice" }), DSL.fieldByName(new String[] { "totaldecorationprice" }), DSL.concat(new Field[] { DSL.fieldByName(new String[] { "employees", "firstname" }), DSL.concat(new String[] { "" }), DSL.fieldByName(new String[] { "employees", "lastname" }) }).as("employeename")).from(new TableLike[] { DSL.tableByName(new String[] { "ordermain" }) }).join(DSL.tableByName(new String[] { "employees" })).on(new Condition[] { DSL.fieldByName(new String[] { "ordermain", "employeeid" }).equal(DSL.fieldByName(new String[] { "employees", "username" })) }).where(new Condition[] { DSL.fieldByName(new String[] { "quotetoorder" }).isNotNull().and(DSL.fieldByName(new String[] { "quotetoorder" }).notEqual("")) });
      wherepart = doWherePart(wherepart, loadConfig);
      SelectLimitStep<?> orderbypart = doOrderByPart(wherepart, loadConfig);
      sqlresults = orderbypart.fetch();
    } else if (loadConfig.get("managetype").equals("report6")) {
      SelectConditionStep<?> wherepart = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "ordermain_sales_report" })).where(new Condition[] { DSL.fieldByName(new String[] { "ordertype" }).equal("Domestic") }).and(DSL.fieldByName(new String[] { "workordernumber" }).isNotNull()).and(DSL.fieldByName(new String[] { "workordernumber" }).notEqual(""));
      wherepart = doWherePart(wherepart, loadConfig);
      SelectLimitStep<?> orderbypart = doOrderByPart(wherepart, loadConfig);
      sqlresults = orderbypart.fetch();
    }
    
    if (exportcsv) {
      if (loadConfig.get("managetype").equals("report1")) {
        if (!SharedData.isFaya.booleanValue()) {
          String[] thekeys = { "eclipseaccountnumber", "contactname", "salesdollars", "minorderdate" };
          String[] thekeysname = { "Customer #", "Customer Name", "Sales Dollars", "First Order Date" };
          addTOCSV(thekeys, thekeysname, sqlresults);
        } else {
          String[] thekeys = { "company", "contactname", "salesdollars", "minorderdate" };
          String[] thekeysname = { "Company", "Customer Name", "Sales Dollars", "First Order Date" };
          addTOCSV(thekeys, thekeysname, sqlresults);
        }
      } else if (loadConfig.get("managetype").equals("report2")) {
        String[] thekeys = { "stylenumber", "styledescription", "totalquantity, minorderdate" };
        String[] thekeysname = { "Item #", "Item Description", "Units Sold", "First Order Date" };
        addTOCSV(thekeys, thekeysname, sqlresults);
      } else if (loadConfig.get("managetype").equals("report3")) {
        if (!SharedData.isFaya.booleanValue()) {
          String[] thekeys = { "eclipseaccountnumber", "company", "contactname", "stylenumber", "styledescription", "totalquantity", "minorderdate" };
          String[] thekeysname = { "Customer #", "Company", "Customer Name", "Item #", "Item Description", "Units Sold", "First Order Date" };
          addTOCSV(thekeys, thekeysname, sqlresults);
        } else {
          String[] thekeys = { "company", "contactname", "stylenumber", "styledescription", "totalquantity", "minorderdate" };
          String[] thekeysname = { "Company", "Customer Name", "Item #", "Item Description", "Units Sold", "First Order Date" };
          addTOCSV(thekeys, thekeysname, sqlresults);
        }
      } else if (loadConfig.get("managetype").equals("report4")) {
        String[] thekeys = { "embqty", "heatqty", "osqty", "ordernumber", "allvendor", "workordernumber", "company", "mainfilename", "totalcapprice", "totaldecorationprice", "totalprice", "employeeid", "employee", "orderdate", "ordershipdate" };
        String[] thekeysname = { "Embroidery QTY", "Heat Transfer QTY", "Overseas QTY", "INVOICE", "VENDOR", "JOB", "COMPANY", "DESCRIPTION", "PRODUCT PRICE", "SERVICE PRICE", "TOTAL PRICE", "Employee ID", "Employee Name", "Order Date", "Order Ship Date" };
        addTOCSV(thekeys, thekeysname, sqlresults);
      } else if (loadConfig.get("managetype").equals("report5")) {
        String[] thekeys = { "ordertype", "hiddenkey", "ordernumber", "orderstatus", "company", "orderdate", "employeename", "totalcapprice", "totaldecorationprice" };
        String[] thekeysname = { "Order Type", "Quote Number", "Sales Number", "Status", "Company Name", "Order Date", "Managed By", "Total Cap Price", "Total Decoration Price" };
        addTOCSV(thekeys, thekeysname, sqlresults);
      } else if (loadConfig.get("managetype").equals("report6")) {
        String[] thekeys = { "workordernumber", "orderdate", "employee", "embvendor", "embventotqty", "embventotcost", "embprocessdate", "embduedate", "screenvendor", "screenventotqty", "screenventotcost", "screenprocessdate", "screenduedate", "heatvendor", "heatventotqty", "heatventotcost", "heatprocessdate", "heatduedate", "dtgvendor", "dtgventotqty", "dtgventotcost", "dtgprocessdate", "dtgduedate" };
        String[] thekeysname = { "Work Order", "Order Date", "Employee", "Embroidery Vendor", "Employee Vendor Total QTY", "Employee Vendor Total Cost", "Embroidery Process Date", "Embroidery Due Date", "Screen Print Vendor", "Screen Print Vendor Total QTY", "Screen Print Vendor Total Cost", "Screen Print Process Date", "Screen Print Due Date", "Heat Transfer Vendor", "Heat Transfer Vendor Total QTY", "Heat Transfer Vendor Total Cost", "Heat Transfer Process Date", "Heat Transfer Due Date", "DTG Vendor", "DTG Vendor Total QTY", "DTG Vendor Total Cost", "DTG Process Date", "DTG Due Date" };
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
      
      for (int i = loadConfig.getOffset(); (i < loadConfig.getOffset() + loadConfig.getLimit()) && (i < sqlresults.size()); i++) {
        BaseModelData mydata = new BaseModelData();
        
        if (loadConfig.get("managetype").equals("report1")) {
          mydata.set("eclipseaccountnumber", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "eclipseaccountnumber" })));
          mydata.set("company", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "company" })));
          mydata.set("contactname", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "contactname" })));
          mydata.set("minorderdate", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "minorderdate" })));
          if (((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "salesdollars" })) != null) {
            mydata.set("salesdollars", NumberFormat.getCurrencyInstance(Locale.US).format(((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "salesdollars" }))));
          }
        } else if (loadConfig.get("managetype").equals("report2")) {
          mydata.set("stylenumber", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "stylenumber" })));
          mydata.set("styledescription", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "styledescription" })));
          mydata.set("totalquantity", String.valueOf(((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "totalquantity" }))));
          mydata.set("minorderdate", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "minorderdate" })));
        } else if (loadConfig.get("managetype").equals("report3")) {
          mydata.set("eclipseaccountnumber", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "eclipseaccountnumber" })));
          mydata.set("company", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "company" })));
          mydata.set("contactname", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "contactname" })));
          mydata.set("stylenumber", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "stylenumber" })));
          mydata.set("styledescription", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "styledescription" })));
          mydata.set("totalquantity", String.valueOf(((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "totalquantity" }))));
          mydata.set("minorderdate", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "minorderdate" })));
        } else if (loadConfig.get("managetype").equals("report4")) {
          mydata.set("hiddenkey", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "hiddenkey" })));
          mydata.set("embqty", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "embqty" })));
          mydata.set("heatqty", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "heatqty" })));
          mydata.set("osqty", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "osqty" })));
          mydata.set("ordernumber", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "ordernumber" })));
          mydata.set("allvendor", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "allvendor" })));
          mydata.set("workordernumber", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "workordernumber" })));
          mydata.set("company", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "company" })));
          mydata.set("mainfilename", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "mainfilename" })));
          mydata.set("totalcapprice", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(tmp8187_8184)) != null ? NumberFormat.getCurrencyInstance(Locale.US).format(((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "totalcapprice" }))) : null);
          mydata.set("totaldecorationprice", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(tmp8272_8269)) != null ? NumberFormat.getCurrencyInstance(Locale.US).format(((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "totaldecorationprice" }))) : null);
          mydata.set("totalprice", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(tmp8356_8353)) != null ? NumberFormat.getCurrencyInstance(Locale.US).format(((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "totalprice" }))) : null);
          mydata.set("employeeid", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "employeeid" })));
          mydata.set("employee", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "employee" })));
          mydata.set("orderdate", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "orderdate" })));
          mydata.set("ordershipdate", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "ordershipdate" })));
        } else if (loadConfig.get("managetype").equals("report5")) {
          mydata.set("ordertype", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "ordertype" })));
          mydata.set("hiddenkey", String.valueOf(((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "hiddenkey" }))));
          mydata.set("ordernumber", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "ordernumber" })));
          mydata.set("orderstatus", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "orderstatus" })));
          mydata.set("company", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "company" })));
          mydata.set("orderdate", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "orderdate" })));
          mydata.set("employeename", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "employeename" })));
          if (((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "totalcapprice" })) != null) {
            mydata.set("totalcapprice", NumberFormat.getCurrencyInstance(Locale.US).format(((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "totalcapprice" }))));
          }
          if (((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "totaldecorationprice" })) != null) {
            mydata.set("totaldecorationprice", NumberFormat.getCurrencyInstance(Locale.US).format(((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "totaldecorationprice" }))));
          }
        } else if (loadConfig.get("managetype").equals("report6")) {
          mydata.set("workordernumber", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "workordernumber" })));
          mydata.set("orderdate", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "orderdate" })));
          mydata.set("employee", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "employee" })));
          mydata.set("embvendor", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "embvendor" })));
          mydata.set("embventotqty", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "embventotqty" })));
          mydata.set("embventotcost", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "embventotcost" })));
          mydata.set("embprocessdate", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "embprocessdate" })));
          mydata.set("embduedate", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "embduedate" })));
          mydata.set("screenvendor", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "screenvendor" })));
          mydata.set("screenventotqty", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "screenventotqty" })));
          mydata.set("screenventotcost", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "screenventotcost" })));
          mydata.set("screenprocessdate", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "screenprocessdate" })));
          mydata.set("screenduedate", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "screenduedate" })));
          mydata.set("heatvendor", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "heatvendor" })));
          mydata.set("heatventotqty", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "heatventotqty" })));
          mydata.set("heatventotcost", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "heatventotcost" })));
          mydata.set("heatprocessdate", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "heatprocessdate" })));
          mydata.set("heatduedate", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "heatduedate" })));
          mydata.set("dtgvendor", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "dtgvendor" })));
          mydata.set("dtgventotqty", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "dtgventotqty" })));
          mydata.set("dtgventotcost", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "dtgventotcost" })));
          mydata.set("dtgprocessdate", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "dtgprocessdate" })));
          mydata.set("dtgduedate", ((Record)sqlresults.get(i)).getValue(DSL.fieldByName(new String[] { "dtgduedate" })));
        }
        mylist.add(mydata);
      }
      this.myresults = new com.extjs.gxt.ui.client.data.BasePagingLoadResult(mylist, loadConfig.getOffset(), sqlresults.size());
    }
  }
  
  private SelectLimitStep<?> doOrderByPart(SelectHavingStep<?> wherepart, PagingLoadConfig loadConfig)
  {
    if (loadConfig.getSortField().equals("salesdollars")) {
      if (loadConfig.getSortDir() == Style.SortDir.ASC) {
        return wherepart.orderBy(new SortField[] { DSL.fieldByName(new String[] { "salesdollars" }).asc(), DSL.fieldByName(new String[] { "eclipseaccountnumber" }).asc() });
      }
      return wherepart.orderBy(new SortField[] { DSL.fieldByName(new String[] { "salesdollars" }).desc(), DSL.fieldByName(new String[] { "eclipseaccountnumber" }).asc() });
    }
    
    if (loadConfig.getSortDir() == Style.SortDir.ASC) {
      return wherepart.orderBy(new SortField[] { DSL.fieldByName(new String[] { loadConfig.getSortField() }).asc() });
    }
    return wherepart.orderBy(new SortField[] { DSL.fieldByName(new String[] { loadConfig.getSortField() }).desc() });
  }
  


  private SelectLimitStep<?> doOrderByPart(SelectConditionStep<?> wherepart, PagingLoadConfig loadConfig)
  {
    if (loadConfig.getSortField().equals("salesdollars")) {
      if (loadConfig.getSortDir() == Style.SortDir.ASC) {
        return wherepart.orderBy(new SortField[] { DSL.fieldByName(new String[] { "salesdollars" }).asc(), DSL.fieldByName(new String[] { "eclipseaccountnumber" }).asc() });
      }
      return wherepart.orderBy(new SortField[] { DSL.fieldByName(new String[] { "salesdollars" }).desc(), DSL.fieldByName(new String[] { "eclipseaccountnumber" }).asc() });
    }
    
    if (loadConfig.getSortDir() == Style.SortDir.ASC) {
      return wherepart.orderBy(new SortField[] { DSL.fieldByName(new String[] { loadConfig.getSortField() }).asc() });
    }
    return wherepart.orderBy(new SortField[] { DSL.fieldByName(new String[] { loadConfig.getSortField() }).desc() });
  }
  


  private SelectConditionStep<?> doWherePart(SelectConditionStep<?> wherepart, PagingLoadConfig loadConfig)
  {
    if (loadConfig.get("managetype").equals("report4")) {
      if (loadConfig.get("orderfrom") != null) {
        wherepart = wherepart.and(DSL.fieldByName(new String[] { "ordershipdate" }).greaterOrEqual(loadConfig.get("orderfrom")));
      }
      if (loadConfig.get("orderto") != null) {
        wherepart = wherepart.and(DSL.fieldByName(new String[] { "ordershipdate" }).lessOrEqual(loadConfig.get("orderto")));
      }
    } else {
      if (loadConfig.get("orderfrom") != null) {
        wherepart = wherepart.and(DSL.fieldByName(new String[] { "orderdate" }).greaterOrEqual(loadConfig.get("orderfrom")));
      }
      if (loadConfig.get("orderto") != null) {
        wherepart = wherepart.and(DSL.fieldByName(new String[] { "orderdate" }).lessOrEqual(loadConfig.get("orderto")));
      }
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
      
      if (loadConfig.get("managetype").equals("report1")) {
        for (int i = 0; i < matchList.size(); i++) {
          if (searchcondition == null) {
            searchcondition = DSL.fieldByName(new String[] { "eclipseaccountnumber" }).equal(DSL.fieldByName(new String[] { "eclipseaccountnumber" })).and(DSL.fieldByName(new String[] { "eclipseaccountnumber" }).contains(matchList.get(i)).or(DSL.fieldByName(new String[] { "company" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "contactname" }).contains(matchList.get(i))));
          } else {
            searchcondition = searchcondition.and(DSL.fieldByName(new String[] { "eclipseaccountnumber" }).contains(matchList.get(i)).or(DSL.fieldByName(new String[] { "contactname" }).contains(matchList.get(i))));
          }
        }
      } else if (loadConfig.get("managetype").equals("report2")) {
        for (int i = 0; i < matchList.size(); i++) {
          if (searchcondition == null) {
            searchcondition = DSL.fieldByName(new String[] { "ordermain_sets_items", "stylenumber" }).contains(matchList.get(i));
          } else {
            searchcondition = searchcondition.and(DSL.fieldByName(new String[] { "ordermain_sets_items", "stylenumber" }).contains(matchList.get(i)));
          }
        }
      } else if (loadConfig.get("managetype").equals("report3")) {
        for (int i = 0; i < matchList.size(); i++) {
          if (searchcondition == null) {
            searchcondition = DSL.fieldByName(new String[] { "eclipseaccountnumber" }).equal(DSL.fieldByName(new String[] { "eclipseaccountnumber" })).and(DSL.fieldByName(new String[] { "eclipseaccountnumber" }).contains(matchList.get(i)).or(DSL.fieldByName(new String[] { "company" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "contactname" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "ordermain_sets_items", "stylenumber" }).contains(matchList.get(i))));
          } else {
            searchcondition = searchcondition.and(DSL.fieldByName(new String[] { "eclipseaccountnumber" }).contains(matchList.get(i)).or(DSL.fieldByName(new String[] { "contactname" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "ordermain_sets_items", "stylenumber" }).contains(matchList.get(i))));
          }
        }
      } else if (loadConfig.get("managetype").equals("report4")) {
        for (int i = 0; i < matchList.size(); i++) {
          if (searchcondition == null) {
            searchcondition = DSL.fieldByName(new String[] { "hiddenkey" }).equal(DSL.fieldByName(new String[] { "hiddenkey" })).and(DSL.fieldByName(new String[] { "hiddenkey" }).contains(matchList.get(i)).or(DSL.fieldByName(new String[] { "embqty" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "heatqty" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "osqty" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "ordernumber" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "allvendor" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "workordernumber" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "company" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "mainfilename" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "totalcapprice" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "totaldecorationprice" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "employeeid" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "employee" }).contains(matchList.get(i))));
          } else {
            searchcondition = searchcondition.and(DSL.fieldByName(new String[] { "hiddenkey" }).contains(matchList.get(i)).or(DSL.fieldByName(new String[] { "embqty" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "heatqty" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "osqty" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "ordernumber" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "allvendor" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "workordernumber" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "company" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "mainfilename" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "totalcapprice" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "totaldecorationprice" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "employeeid" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "employee" }).contains(matchList.get(i))));
          }
        }
      } else if (loadConfig.get("managetype").equals("report5")) {
        for (int i = 0; i < matchList.size(); i++) {
          if (searchcondition == null) {
            searchcondition = DSL.fieldByName(new String[] { "hiddenkey" }).equal(DSL.fieldByName(new String[] { "hiddenkey" })).and(DSL.fieldByName(new String[] { "hiddenkey" }).contains(matchList.get(i)).or(DSL.fieldByName(new String[] { "ordertype" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "ordernumber" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "orderstatus" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "company" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "employees", "firstname" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "employees", "lastname" }).contains(matchList.get(i))));
          } else {
            searchcondition = searchcondition.and(DSL.fieldByName(new String[] { "hiddenkey" }).contains(matchList.get(i)).or(DSL.fieldByName(new String[] { "ordertype" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "ordernumber" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "orderstatus" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "company" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "employees", "firstname" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "employees", "lastname" }).contains(matchList.get(i))));
          }
        }
      } else if (loadConfig.get("managetype").equals("report6")) {
        for (int i = 0; i < matchList.size(); i++) {
          if (searchcondition == null) {
            searchcondition = DSL.fieldByName(new String[] { "hiddenkey" }).equal(DSL.fieldByName(new String[] { "hiddenkey" })).and(DSL.fieldByName(new String[] { "hiddenkey" }).contains(matchList.get(i)).or(DSL.fieldByName(new String[] { "workordernumber" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "employee" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "embvendor" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "screenvendor" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "heatvendor" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "dtgvendor" }).contains(matchList.get(i))));
          } else {
            searchcondition = searchcondition.and(DSL.fieldByName(new String[] { "hiddenkey" }).contains(matchList.get(i)).or(DSL.fieldByName(new String[] { "workordernumber" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "employee" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "embvendor" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "screenvendor" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "heatvendor" }).contains(matchList.get(i))).or(DSL.fieldByName(new String[] { "dtgvendor" }).contains(matchList.get(i))));
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
