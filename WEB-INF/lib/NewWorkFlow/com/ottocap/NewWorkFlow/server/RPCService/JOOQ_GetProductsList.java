package com.ottocap.NewWorkFlow.server.RPCService;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBoxModelData;
import com.ottocap.NewWorkFlow.server.JOOQSQL;
import java.util.TreeMap;
import javax.servlet.http.HttpSession;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.GroupField;
import org.jooq.Record;
import org.jooq.Record3;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.jooq.SelectHavingStep;
import org.jooq.SelectJoinStep;
import org.jooq.SelectOnConditionStep;
import org.jooq.SelectSelectStep;
import org.jooq.TableLike;
import org.jooq.impl.DSL;

public class JOOQ_GetProductsList
{
  private com.extjs.gxt.ui.client.data.BasePagingLoadResult<OtherComboBoxModelData> pageloadingresult = null;
  
  public JOOQ_GetProductsList(PagingLoadConfig loadConfig, com.ottocap.NewWorkFlow.client.OrderData.OrderData.OrderType ordertype, HttpSession httpSession) {
    boolean usecategoryquery = false;
    
    String query = (String)loadConfig.get("query");
    
    String category = (String)loadConfig.get("category");
    if ((category != null) && (!category.equals("")) && (!category.equals("All Items"))) {
      usecategoryquery = true;
    }
    
    TreeMap<String, OtherComboBoxModelData> thestyles = new TreeMap();
    
    boolean showfactorystyles = false;
    if ((httpSession.getAttribute("username") != null) && (
      (((Integer)httpSession.getAttribute("level")).intValue() == 1) || (((Integer)httpSession.getAttribute("level")).intValue() == 2) || (((Integer)httpSession.getAttribute("level")).intValue() == 3) || (((Integer)httpSession.getAttribute("level")).intValue() == 5))) {
      showfactorystyles = true;
    }
    
    Object domesticstyletable3;
    if (ordertype == com.ottocap.NewWorkFlow.client.OrderData.OrderData.OrderType.DOMESTIC)
    {
      Result<org.jooq.Record4<Object, Object, Object, Object>> domesticstyletable1;
      Result<org.jooq.Record4<Object, Object, Object, Object>> domesticstyletable1;
      if (usecategoryquery) {
        domesticstyletable1 = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "sku" }), DSL.fieldByName(new String[] { "productname" }), DSL.fieldByName(new String[] { "size" }), DSL.fieldByName(new String[] { "category" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_domestic" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "sku" }).equal(query) }).or(DSL.fieldByName(new String[] { "sku" }).like(query + "%").and(DSL.fieldByName(new String[] { "category" }).equal(category))).fetch();
      } else
        domesticstyletable1 = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "sku" }), DSL.fieldByName(new String[] { "productname" }), DSL.fieldByName(new String[] { "size" }), DSL.fieldByName(new String[] { "category" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_domestic" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "sku" }).equal(query) }).or(DSL.fieldByName(new String[] { "sku" }).like(query + "%")).fetch();
      OtherComboBoxModelData mycombodata;
      if ((domesticstyletable1 != null) && (domesticstyletable1.size() != 0)) {
        for (Record currentdomesticstyle : domesticstyletable1) {
          mycombodata = new OtherComboBoxModelData((String)currentdomesticstyle.getValue(DSL.fieldByName(new String[] { "sku" })));
          if (!((String)currentdomesticstyle.getValue(DSL.fieldByName(new String[] { "size" }))).trim().equals("")) {
            mycombodata.set("description", (String)currentdomesticstyle.getValue(DSL.fieldByName(new String[] { "productname" })) + " (" + (String)currentdomesticstyle.getValue(DSL.fieldByName(new String[] { "size" })) + ")");
          } else {
            mycombodata.set("description", (String)currentdomesticstyle.getValue(DSL.fieldByName(new String[] { "productname" })));
          }
          if ((showfactorystyles) || ((!mycombodata.getName().startsWith("888-")) && (!mycombodata.getName().startsWith("889-"))))
          {

            thestyles.put(mycombodata.getName(), mycombodata);
          }
        }
      }
      
      Result<org.jooq.Record4<Object, Object, Object, Object>> domesticstyletable2;
      Result<org.jooq.Record4<Object, Object, Object, Object>> domesticstyletable2;
      if (usecategoryquery) {
        domesticstyletable2 = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "style" }), DSL.fieldByName(new String[] { "styles_domestic", "productname" }), DSL.fieldByName(new String[] { "styles_domestic", "size" }), DSL.fieldByName(new String[] { "styles_domestic", "category" })).from(new TableLike[] { JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "style" }), DSL.fieldByName(new String[] { "basestyle" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_domestic_specials" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "style" }).like(query + "%") }).orderBy(new org.jooq.SortField[] { DSL.fieldByName(new String[] { "endingdate" }).desc() }).asTable("t1") }).join(DSL.tableByName(new String[] { "styles_domestic" })).on(new Condition[] { DSL.fieldByName(new String[] { "styles_domestic", "sku" }).equal(DSL.fieldByName(new String[] { "t1", "basestyle" })) }).where(new Condition[] { DSL.fieldByName(new String[] { "styles_domestic", "category" }).equal(category).or(DSL.fieldByName(new String[] { "style" }).equal(query)) }).groupBy(new GroupField[] { DSL.fieldByName(new String[] { "style" }) }).fetch();
      } else
        domesticstyletable2 = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "style" }), DSL.fieldByName(new String[] { "styles_domestic", "productname" }), DSL.fieldByName(new String[] { "styles_domestic", "size" }), DSL.fieldByName(new String[] { "styles_domestic", "category" })).from(new TableLike[] { JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "style" }), DSL.fieldByName(new String[] { "basestyle" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_domestic_specials" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "style" }).like(query + "%") }).orderBy(new org.jooq.SortField[] { DSL.fieldByName(new String[] { "endingdate" }).desc() }).asTable("t1") }).join(DSL.tableByName(new String[] { "styles_domestic" })).on(new Condition[] { DSL.fieldByName(new String[] { "styles_domestic", "sku" }).equal(DSL.fieldByName(new String[] { "t1", "basestyle" })) }).groupBy(new GroupField[] { DSL.fieldByName(new String[] { "style" }) }).fetch();
      OtherComboBoxModelData mycombodata;
      if ((domesticstyletable2 != null) && (domesticstyletable2.size() != 0)) {
        for (Record currentdomesticstyle : domesticstyletable2) {
          mycombodata = new OtherComboBoxModelData((String)currentdomesticstyle.getValue(DSL.fieldByName(new String[] { "style" })));
          if (currentdomesticstyle.getValue(DSL.fieldByName(new String[] { "size" })) != null) {
            if (!((String)currentdomesticstyle.getValue(DSL.fieldByName(new String[] { "size" }))).trim().equals("")) {
              mycombodata.set("description", (String)currentdomesticstyle.getValue(DSL.fieldByName(new String[] { "productname" })) + " (" + (String)currentdomesticstyle.getValue(DSL.fieldByName(new String[] { "size" })) + ")");
            } else
              mycombodata.set("description", (String)currentdomesticstyle.getValue(DSL.fieldByName(new String[] { "productname" })));
          }
          thestyles.put(mycombodata.getName(), mycombodata);
        }
      }
      
      Object domesticstyletable3;
      
      if (usecategoryquery) {
        domesticstyletable3 = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "t1", "style" }), DSL.fieldByName(new String[] { "styles_lackpard", "Name" }), DSL.fieldByName(new String[] { "styles_lackpard", "Main Category" })).from(new TableLike[] { JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "style" }), DSL.fieldByName(new String[] { "basestyle" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_domestic_specials" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "style" }).like(query + "%") }).orderBy(new org.jooq.SortField[] { DSL.fieldByName(new String[] { "endingdate" }).desc() }).asTable("t1") }).join(DSL.tableByName(new String[] { "styles_lackpard" })).on(new Condition[] { DSL.fieldByName(new String[] { "styles_lackpard", "Style" }).equal(DSL.fieldByName(new String[] { "t1", "basestyle" })) }).where(new Condition[] { DSL.fieldByName(new String[] { "styles_lackpard", "Main Category" }).equal(category).or(DSL.fieldByName(new String[] { "styles_lackpard", "style" }).equal(query)) }).groupBy(new GroupField[] { DSL.fieldByName(new String[] { "t1", "style" }) }).fetch();
      } else
        domesticstyletable3 = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "t1", "style" }), DSL.fieldByName(new String[] { "styles_lackpard", "Name" }), DSL.fieldByName(new String[] { "styles_lackpard", "Main Category" })).from(new TableLike[] { JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "style" }), DSL.fieldByName(new String[] { "basestyle" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_domestic_specials" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "style" }).like(query + "%") }).orderBy(new org.jooq.SortField[] { DSL.fieldByName(new String[] { "endingdate" }).desc() }).asTable("t1") }).join(DSL.tableByName(new String[] { "styles_lackpard" })).on(new Condition[] { DSL.fieldByName(new String[] { "styles_lackpard", "Style" }).equal(DSL.fieldByName(new String[] { "t1", "basestyle" })) }).groupBy(new GroupField[] { DSL.fieldByName(new String[] { "t1", "style" }) }).fetch();
      OtherComboBoxModelData mycombodata;
      if ((domesticstyletable3 != null) && (((Result)domesticstyletable3).size() != 0)) {
        for (Record currentdomesticstyle : (Result)domesticstyletable3) {
          mycombodata = new OtherComboBoxModelData((String)currentdomesticstyle.getValue(DSL.fieldByName(new String[] { "style" })));
          mycombodata.set("description", (String)currentdomesticstyle.getValue(DSL.fieldByName(new String[] { "Name" })));
          thestyles.put(mycombodata.getName(), mycombodata);
        }
      }
      
      Result<Record3<Object, Object, Object>> domesticstyletable4;
      Result<Record3<Object, Object, Object>> domesticstyletable4;
      if (usecategoryquery) {
        domesticstyletable4 = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "productname" }), DSL.fieldByName(new String[] { "category" }), DSL.fieldByName(new String[] { "sku" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_domestic_shirts" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "sku" }).equal(query) }).or(DSL.fieldByName(new String[] { "sku" }).like(query + "%").and(DSL.fieldByName(new String[] { "category" }).equal(category))).fetch();
      } else
        domesticstyletable4 = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "productname" }), DSL.fieldByName(new String[] { "category" }), DSL.fieldByName(new String[] { "sku" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_domestic_shirts" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "sku" }).equal(query) }).or(DSL.fieldByName(new String[] { "sku" }).like(query + "%")).fetch();
      OtherComboBoxModelData mycombodata;
      if ((domesticstyletable4 != null) && (domesticstyletable4.size() != 0)) {
        for (Record currentdomesticstyle : domesticstyletable4) {
          mycombodata = new OtherComboBoxModelData((String)currentdomesticstyle.getValue(DSL.fieldByName(new String[] { "sku" })));
          mycombodata.set("description", (String)currentdomesticstyle.getValue(DSL.fieldByName(new String[] { "productname" })));
          thestyles.put(mycombodata.getName(), mycombodata);
        }
      }
      
      Result<Record3<Object, Object, Object>> domesticstyletable5;
      Result<Record3<Object, Object, Object>> domesticstyletable5;
      if (usecategoryquery) {
        domesticstyletable5 = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "productname" }), DSL.fieldByName(new String[] { "category" }), DSL.fieldByName(new String[] { "sku" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_domestic_sweatshirts" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "sku" }).equal(query) }).or(DSL.fieldByName(new String[] { "sku" }).like(query + "%").and(DSL.fieldByName(new String[] { "category" }).equal(category))).fetch();
      } else
        domesticstyletable5 = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "productname" }), DSL.fieldByName(new String[] { "category" }), DSL.fieldByName(new String[] { "sku" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_domestic_sweatshirts" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "sku" }).equal(query) }).or(DSL.fieldByName(new String[] { "sku" }).like(query + "%")).fetch();
      OtherComboBoxModelData mycombodata;
      if ((domesticstyletable5 != null) && (domesticstyletable5.size() != 0)) {
        for (Record currentdomesticstyle : domesticstyletable5) {
          mycombodata = new OtherComboBoxModelData((String)currentdomesticstyle.getValue(DSL.fieldByName(new String[] { "sku" })));
          mycombodata.set("description", (String)currentdomesticstyle.getValue(DSL.fieldByName(new String[] { "productname" })));
          thestyles.put(mycombodata.getName(), mycombodata);
        }
      }
      
      Result<Record3<Object, Object, Object>> domesticstyletable6;
      Result<Record3<Object, Object, Object>> domesticstyletable6;
      if (usecategoryquery) {
        domesticstyletable6 = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "Style" }), DSL.fieldByName(new String[] { "Name" }), DSL.fieldByName(new String[] { "Main Category" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_lackpard" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "Style" }).equal(query) }).or(DSL.fieldByName(new String[] { "Style" }).like(query + "%").and(DSL.fieldByName(new String[] { "Main Category" }).equal(category))).fetch();
      } else
        domesticstyletable6 = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "Style" }), DSL.fieldByName(new String[] { "Name" }), DSL.fieldByName(new String[] { "Main Category" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_lackpard" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "Style" }).equal(query) }).or(DSL.fieldByName(new String[] { "Style" }).like(query + "%")).fetch();
      OtherComboBoxModelData mycombodata;
      if ((domesticstyletable6 != null) && (domesticstyletable6.size() != 0)) {
        for (Record currentdomesticstyle : domesticstyletable6) {
          mycombodata = new OtherComboBoxModelData((String)currentdomesticstyle.getValue(DSL.fieldByName(new String[] { "Style" })));
          mycombodata.set("description", (String)currentdomesticstyle.getValue(DSL.fieldByName(new String[] { "Name" })));
          thestyles.put(mycombodata.getName(), mycombodata);
        }
      }
      
      Result<Record3<Object, Object, Object>> domesticstyletable7;
      Result<Record3<Object, Object, Object>> domesticstyletable7;
      if (usecategoryquery) {
        domesticstyletable7 = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "productname" }), DSL.fieldByName(new String[] { "category" }), DSL.fieldByName(new String[] { "sku" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_domestic_totebags" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "sku" }).equal(query) }).or(DSL.fieldByName(new String[] { "sku" }).like(query + "%").and(DSL.fieldByName(new String[] { "category" }).equal(category))).fetch();
      } else
        domesticstyletable7 = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "productname" }), DSL.fieldByName(new String[] { "category" }), DSL.fieldByName(new String[] { "sku" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_domestic_totebags" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "sku" }).equal(query) }).or(DSL.fieldByName(new String[] { "sku" }).like(query + "%")).fetch();
      OtherComboBoxModelData mycombodata;
      if ((domesticstyletable7 != null) && (domesticstyletable7.size() != 0)) {
        for (Record currentdomesticstyle : domesticstyletable7) {
          mycombodata = new OtherComboBoxModelData((String)currentdomesticstyle.getValue(DSL.fieldByName(new String[] { "sku" })));
          mycombodata.set("description", (String)currentdomesticstyle.getValue(DSL.fieldByName(new String[] { "productname" })));
          thestyles.put(mycombodata.getName(), mycombodata);
        }
      }
      
      Result<Record3<Object, Object, Object>> domesticstyletable8;
      Result<Record3<Object, Object, Object>> domesticstyletable8;
      if (usecategoryquery) {
        domesticstyletable8 = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "productname" }), DSL.fieldByName(new String[] { "category" }), DSL.fieldByName(new String[] { "sku" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_domestic_aprons" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "sku" }).equal(query) }).or(DSL.fieldByName(new String[] { "sku" }).like(query + "%").and(DSL.fieldByName(new String[] { "category" }).equal(category))).fetch();
      } else {
        domesticstyletable8 = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "productname" }), DSL.fieldByName(new String[] { "category" }), DSL.fieldByName(new String[] { "sku" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_domestic_aprons" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "sku" }).equal(query) }).or(DSL.fieldByName(new String[] { "sku" }).like(query + "%")).fetch();
      }
      if ((domesticstyletable8 != null) && (domesticstyletable8.size() != 0)) {
        for (Record currentdomesticstyle : domesticstyletable8) {
          OtherComboBoxModelData mycombodata = new OtherComboBoxModelData((String)currentdomesticstyle.getValue(DSL.fieldByName(new String[] { "sku" })));
          mycombodata.set("description", (String)currentdomesticstyle.getValue(DSL.fieldByName(new String[] { "productname" })));
          thestyles.put(mycombodata.getName(), mycombodata);
        }
      }
      

      if (com.ottocap.NewWorkFlow.server.SharedData.isFaya.booleanValue()) {
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
        
        OtherComboBoxModelData nonottoflatcombodata = new OtherComboBoxModelData("Non OTTO Flat", "nonottoflat");
        nonottoflatcombodata.set("description", "Other Flat Products");
        thestyles.put(nonottoflatcombodata.getName(), nonottoflatcombodata);
      }
    }
    else
    {
      Result<Record3<Object, Object, Object>> overseasstyletable1;
      
      Result<Record3<Object, Object, Object>> overseasstyletable1;
      if (usecategoryquery) {
        overseasstyletable1 = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "STYLE NUMBER" }), DSL.fieldByName(new String[] { "Category" }), DSL.fieldByName(new String[] { "Emb Tooling Name" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_overseasinstock" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "STYLE NUMBER" }).equal(query) }).or(DSL.fieldByName(new String[] { "STYLE NUMBER" }).like(query + "%").and(DSL.fieldByName(new String[] { "Category" }).equal(category))).groupBy(new GroupField[] { DSL.fieldByName(new String[] { "STYLE NUMBER" }) }).fetch();
      } else
        overseasstyletable1 = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "STYLE NUMBER" }), DSL.fieldByName(new String[] { "Category" }), DSL.fieldByName(new String[] { "Emb Tooling Name" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_overseasinstock" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "STYLE NUMBER" }).equal(query) }).or(DSL.fieldByName(new String[] { "STYLE NUMBER" }).like(query + "%")).groupBy(new GroupField[] { DSL.fieldByName(new String[] { "STYLE NUMBER" }) }).fetch();
      OtherComboBoxModelData mycombodata;
      if ((overseasstyletable1 != null) && (overseasstyletable1.size() != 0)) {
        for (domesticstyletable3 = overseasstyletable1.iterator(); ((java.util.Iterator)domesticstyletable3).hasNext();) { Record currentoverseasstyle = (Record)((java.util.Iterator)domesticstyletable3).next();
          mycombodata = new OtherComboBoxModelData((String)currentoverseasstyle.getValue(DSL.fieldByName(new String[] { "STYLE NUMBER" })));
          mycombodata.set("description", currentoverseasstyle.getValue(DSL.fieldByName(tmp5597_5594)) != null ? (String)currentoverseasstyle.getValue(DSL.fieldByName(new String[] { "Emb Tooling Name" })) : "");
          if ((showfactorystyles) || ((!mycombodata.getName().startsWith("888-")) && (!mycombodata.getName().startsWith("889-"))))
          {

            thestyles.put(mycombodata.getName(), mycombodata);
          }
        }
      }
      
      Result<Record3<Object, Object, Object>> overseasstyletable2;
      Result<Record3<Object, Object, Object>> overseasstyletable2;
      if (usecategoryquery) {
        overseasstyletable2 = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "Style" }), DSL.fieldByName(new String[] { "Name" }), DSL.fieldByName(new String[] { "Main Category" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_lackpard" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "Style" }).equal(query) }).or(DSL.fieldByName(new String[] { "Style" }).like(query + "%").and(DSL.fieldByName(new String[] { "Main Category" }).equal(category))).fetch();
      } else
        overseasstyletable2 = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "Style" }), DSL.fieldByName(new String[] { "Name" }), DSL.fieldByName(new String[] { "Main Category" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_lackpard" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "Style" }).equal(query) }).or(DSL.fieldByName(new String[] { "Style" }).like(query + "%")).fetch();
      OtherComboBoxModelData mycombodata;
      if ((overseasstyletable2 != null) && (overseasstyletable2.size() != 0)) {
        for (Record currentdomesticstyle : overseasstyletable2) {
          mycombodata = new OtherComboBoxModelData((String)currentdomesticstyle.getValue(DSL.fieldByName(new String[] { "Style" })));
          mycombodata.set("description", (String)currentdomesticstyle.getValue(DSL.fieldByName(new String[] { "Name" })));
          thestyles.put(mycombodata.getName(), mycombodata);
        }
      }
      
      Object overseasstyletable3;
      Object overseasstyletable3;
      if (usecategoryquery) {
        overseasstyletable3 = JOOQSQL.getInstance().create().selectDistinct(DSL.fieldByName(new String[] { "stylenumber" }), DSL.fieldByName(new String[] { "category" })).from(new TableLike[] { DSL.tableByName(new String[] { "overseas_price_instockshirts" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "stylenumber" }).equal(query) }).or(DSL.fieldByName(new String[] { "stylenumber" }).like(query + "%").and(DSL.fieldByName(new String[] { "category" }).equal(category))).fetch();
      } else
        overseasstyletable3 = JOOQSQL.getInstance().create().selectDistinct(DSL.fieldByName(new String[] { "stylenumber" }), DSL.fieldByName(new String[] { "category" })).from(new TableLike[] { DSL.tableByName(new String[] { "overseas_price_instockshirts" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "stylenumber" }).equal(query) }).or(DSL.fieldByName(new String[] { "stylenumber" }).like(query + "%")).fetch();
      OtherComboBoxModelData mycombodata;
      if ((overseasstyletable3 != null) && (((Result)overseasstyletable3).size() != 0)) {
        for (Record currentoverseasstyle : (Result)overseasstyletable3) {
          mycombodata = new OtherComboBoxModelData((String)currentoverseasstyle.getValue(DSL.fieldByName(new String[] { "stylenumber" })));
          
          String productname = "";
          Record sqlrecord;
          if ((sqlrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "productname" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_domestic_shirts" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "sku" }).equal((String)currentoverseasstyle.getValue(DSL.fieldByName(new String[] { "stylenumber" }))) }).limit(1).fetchOne()) != null) {
            productname = (String)sqlrecord.getValue(DSL.fieldByName(new String[] { "productname" }));
          } else if ((sqlrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "productname" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_domestic_sweatshirts" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "sku" }).equal((String)currentoverseasstyle.getValue(DSL.fieldByName(new String[] { "stylenumber" }))) }).limit(1).fetchOne()) != null) {
            productname = (String)sqlrecord.getValue(DSL.fieldByName(new String[] { "productname" }));
          }
          
          mycombodata.set("description", productname);
          if ((showfactorystyles) || ((!mycombodata.getName().startsWith("888-")) && (!mycombodata.getName().startsWith("889-"))))
          {

            thestyles.put(mycombodata.getName(), mycombodata);
          }
        }
      }
      
      Result<Record3<Object, Object, Object>> overseasstyletable4;
      Result<Record3<Object, Object, Object>> overseasstyletable4;
      if (usecategoryquery) {
        overseasstyletable4 = JOOQSQL.getInstance().create().selectDistinct(DSL.fieldByName(new String[] { "stylenumber" }), DSL.fieldByName(new String[] { "productname" }), DSL.fieldByName(new String[] { "category" })).from(new TableLike[] { DSL.tableByName(new String[] { "overseas_price_instockflats" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "stylenumber" }).equal(query) }).or(DSL.fieldByName(new String[] { "stylenumber" }).like(query + "%").and(DSL.fieldByName(new String[] { "category" }).equal(category))).fetch();
      } else
        overseasstyletable4 = JOOQSQL.getInstance().create().selectDistinct(DSL.fieldByName(new String[] { "stylenumber" }), DSL.fieldByName(new String[] { "productname" }), DSL.fieldByName(new String[] { "category" })).from(new TableLike[] { DSL.tableByName(new String[] { "overseas_price_instockflats" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "stylenumber" }).equal(query) }).or(DSL.fieldByName(new String[] { "stylenumber" }).like(query + "%")).fetch();
      OtherComboBoxModelData mycombodata;
      if ((overseasstyletable4 != null) && (overseasstyletable4.size() != 0)) {
        for (Record currentoverseasstyle : overseasstyletable4) {
          mycombodata = new OtherComboBoxModelData((String)currentoverseasstyle.getValue(DSL.fieldByName(new String[] { "stylenumber" })));
          mycombodata.set("description", (String)currentoverseasstyle.getValue(DSL.fieldByName(new String[] { "productname" })));
          thestyles.put(mycombodata.getName(), mycombodata);
        }
      }
      
      Result<Record3<Object, Object, Object>> overseasstyletable5;
      Result<Record3<Object, Object, Object>> overseasstyletable5;
      if (usecategoryquery) {
        overseasstyletable5 = JOOQSQL.getInstance().create().selectDistinct(DSL.fieldByName(new String[] { "Style" }), DSL.fieldByName(new String[] { "Emb Tooling Name" }), DSL.fieldByName(new String[] { "Category" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_overseas" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "Style" }).equal(query) }).or(DSL.fieldByName(new String[] { "Style" }).like(query + "%").and(DSL.fieldByName(new String[] { "Category" }).equal(category))).fetch();
      } else
        overseasstyletable5 = JOOQSQL.getInstance().create().selectDistinct(DSL.fieldByName(new String[] { "Style" }), DSL.fieldByName(new String[] { "Emb Tooling Name" }), DSL.fieldByName(new String[] { "Category" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_overseas" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "Style" }).equal(query) }).or(DSL.fieldByName(new String[] { "Style" }).like(query + "%")).fetch();
      OtherComboBoxModelData mycombodata;
      if ((overseasstyletable5 != null) && (overseasstyletable5.size() != 0)) {
        for (Record currentoverseasstyle : overseasstyletable5) {
          mycombodata = new OtherComboBoxModelData((String)currentoverseasstyle.getValue(DSL.fieldByName(new String[] { "Style" })));
          mycombodata.set("description", (String)currentoverseasstyle.getValue(DSL.fieldByName(new String[] { "Emb Tooling Name" })));
          thestyles.put(mycombodata.getName(), mycombodata);
        }
      }
      
      Result<Record3<Object, Object, Object>> overseasstyletable6;
      Result<Record3<Object, Object, Object>> overseasstyletable6;
      if (usecategoryquery) {
        overseasstyletable6 = JOOQSQL.getInstance().create().selectDistinct(DSL.fieldByName(new String[] { "Style" }), DSL.fieldByName(new String[] { "Material Description" }), DSL.fieldByName(new String[] { "Category" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_pre-designed" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "Style" }).equal(query) }).or(DSL.fieldByName(new String[] { "Style" }).like(query + "%").and(DSL.fieldByName(new String[] { "Category" }).equal(category))).fetch();
      } else
        overseasstyletable6 = JOOQSQL.getInstance().create().selectDistinct(DSL.fieldByName(new String[] { "Style" }), DSL.fieldByName(new String[] { "Material Description" }), DSL.fieldByName(new String[] { "Category" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_pre-designed" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "Style" }).equal(query) }).or(DSL.fieldByName(new String[] { "Style" }).like(query + "%")).fetch();
      OtherComboBoxModelData mycombodata;
      if ((overseasstyletable6 != null) && (overseasstyletable6.size() != 0)) {
        for (Record currentoverseasstyle : overseasstyletable6) {
          mycombodata = new OtherComboBoxModelData((String)currentoverseasstyle.getValue(DSL.fieldByName(new String[] { "Style" })));
          mycombodata.set("description", (String)currentoverseasstyle.getValue(DSL.fieldByName(new String[] { "Material Description" })));
          if ((showfactorystyles) || ((!mycombodata.getName().startsWith("888-")) && (!mycombodata.getName().startsWith("889-"))))
          {

            thestyles.put(mycombodata.getName(), mycombodata);
          }
        }
      }
      

      if (showfactorystyles)
      {
        Result<org.jooq.Record1<Object>> overseasstyletable7 = JOOQSQL.getInstance().create().selectDistinct(DSL.fieldByName(new String[] { "Style" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_overseas_customstyle" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "Style" }).equal(query) }).or(DSL.fieldByName(new String[] { "Style" }).like(query + "%")).fetch();
        if ((overseasstyletable7 != null) && (overseasstyletable7.size() != 0)) {
          for (Record currentoverseasstyle : overseasstyletable7) {
            OtherComboBoxModelData mycombodata = new OtherComboBoxModelData((String)currentoverseasstyle.getValue(DSL.fieldByName(new String[] { "Style" })));
            mycombodata.set("description", "Custom Style");
            thestyles.put(mycombodata.getName(), mycombodata);
          }
        }
      }
    }
    

    java.util.ArrayList<OtherComboBoxModelData> mylist = new java.util.ArrayList();
    
    String[] thekeys = (String[])thestyles.keySet().toArray(new String[0]);
    for (int i = loadConfig.getOffset(); (i < loadConfig.getOffset() + loadConfig.getLimit()) && (i < thestyles.size()); i++) {
      OtherComboBoxModelData mydata = (OtherComboBoxModelData)thestyles.get(thekeys[i]);
      mylist.add(mydata);
    }
    
    this.pageloadingresult = new com.extjs.gxt.ui.client.data.BasePagingLoadResult(mylist, loadConfig.getOffset(), thestyles.size());
  }
  
  public com.extjs.gxt.ui.client.data.BasePagingLoadResult<OtherComboBoxModelData> results() {
    return this.pageloadingresult;
  }
}
