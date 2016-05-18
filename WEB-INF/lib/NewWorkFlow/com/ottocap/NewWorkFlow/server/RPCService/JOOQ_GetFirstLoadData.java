package com.ottocap.NewWorkFlow.server.RPCService;

import com.extjs.gxt.ui.client.core.FastMap;
import com.ottocap.NewWorkFlow.client.DataHolder.ProductsInfo.Decoration.DecorationInfo;
import com.ottocap.NewWorkFlow.client.DataHolder.ProductsInfo.Decoration.DecorationInfoLocations;
import com.ottocap.NewWorkFlow.client.FirstLoadData;
import com.ottocap.NewWorkFlow.client.StoredUser;
import com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBoxModelData;
import com.ottocap.NewWorkFlow.server.JOOQSQL;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectJoinStep;
import org.jooq.SelectSelectStep;
import org.jooq.SelectWhereStep;
import org.jooq.TableLike;
import org.jooq.impl.DSL;

public class JOOQ_GetFirstLoadData
{
  private FirstLoadData firstloaddata = new FirstLoadData();
  


  public JOOQ_GetFirstLoadData(HttpSession session)
    throws Exception
  {
    if ((session.getAttribute("username") == null) || (session.getAttribute("level") == null)) {
      throw new Exception("Please Login");
    }
    
    this.firstloaddata.getUserInfo().setAccessLevel(((Integer)session.getAttribute("level")).intValue());
    this.firstloaddata.getUserInfo().setUsername((String)session.getAttribute("username"));
    Record sqlrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "email" })).from(new TableLike[] { DSL.tableByName(new String[] { "employees" }) }).where(new org.jooq.Condition[] { DSL.fieldByName(new String[] { "username" }).equal(session.getAttribute("username")) }).limit(1).fetchOne();
    this.firstloaddata.getUserInfo().setEmailAddress((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "email" })));
    
    Result<org.jooq.Record1<Object>> sqlresults1 = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "name" })).from(new TableLike[] { DSL.tableByName(new String[] { "list_pontential_repeat" }) }).fetch();
    ArrayList<OtherComboBoxModelData> pontentialrepeatfreq = new ArrayList();
    OtherComboBoxModelData mymodel; for (Record currentrepeattable : sqlresults1) {
      mymodel = new OtherComboBoxModelData((String)currentrepeattable.getValue(DSL.fieldByName(new String[] { "name" })));
      pontentialrepeatfreq.add(mymodel);
    }
    this.firstloaddata.setPontentialRepeatFreqList(pontentialrepeatfreq);
    
    Result<Record> sqlrecords = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "list_order_status" })).fetch();
    ArrayList<OtherComboBoxModelData> orderstatus = new ArrayList();
    OtherComboBoxModelData mymodel; for (Record currentorderstatus : sqlrecords) {
      mymodel = new OtherComboBoxModelData((String)currentorderstatus.getValue(DSL.fieldByName(new String[] { "name" })));
      mymodel.set("allowolddate", Boolean.valueOf(((Byte)currentorderstatus.getValue(DSL.fieldByName(new String[] { "allowolddate" }))).byteValue() != 0));
      orderstatus.add(mymodel);
    }
    this.firstloaddata.setOrderStatus(orderstatus);
    
    sqlresults1 = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "name" })).from(new TableLike[] { DSL.tableByName(new String[] { "list_containerinvoice_status" }) }).fetch();
    Object containerinvoicestatus = new ArrayList();
    for (Record currentcontainerinvoicestatus : sqlresults1) {
      ((ArrayList)containerinvoicestatus).add(new OtherComboBoxModelData((String)currentcontainerinvoicestatus.getValue(DSL.fieldByName(new String[] { "name" }))));
    }
    this.firstloaddata.setContainerInvoiceStatus((ArrayList)containerinvoicestatus);
    this.firstloaddata.setContainerPortofOrigin((ArrayList)containerinvoicestatus);
    this.firstloaddata.setContainerFreightForwarder((ArrayList)containerinvoicestatus);
    
    sqlresults1 = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "name" })).from(new TableLike[] { DSL.tableByName(new String[] { "list_shipping_methods" }) }).fetch();
    ArrayList<OtherComboBoxModelData> shippingtypes = new ArrayList();
    for (Record currentshippingmethod : sqlresults1) {
      shippingtypes.add(new OtherComboBoxModelData((String)currentshippingmethod.getValue(DSL.fieldByName(new String[] { "name" }))));
    }
    this.firstloaddata.setShippingTypes(shippingtypes);
    
    sqlresults1 = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "name" })).from(new TableLike[] { DSL.tableByName(new String[] { "list_artwork_types" }) }).fetch();
    ArrayList<OtherComboBoxModelData> artworktypes = new ArrayList();
    for (Record currentartworktype : sqlresults1) {
      artworktypes.add(new OtherComboBoxModelData((String)currentartworktype.getValue(DSL.fieldByName(new String[] { "name" }))));
    }
    this.firstloaddata.setArtworkTypes(artworktypes);
    
    sqlrecords = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "list_vendors_domestic" })).fetch();
    Object domesticvendors = new ArrayList();
    for (Record currentdomesticvendor : sqlrecords) {
      OtherComboBoxModelData mycombodata = new OtherComboBoxModelData((String)currentdomesticvendor.getValue(DSL.fieldByName(new String[] { "name" })), String.valueOf(currentdomesticvendor.getValue(DSL.fieldByName(new String[] { "id" }))));
      mycombodata.set("dig", Boolean.valueOf(((Byte)currentdomesticvendor.getValue(DSL.fieldByName(new String[] { "dig" }))).byteValue() != 0));
      mycombodata.set("emb", Boolean.valueOf(((Byte)currentdomesticvendor.getValue(DSL.fieldByName(new String[] { "emb" }))).byteValue() != 0));
      mycombodata.set("heat", Boolean.valueOf(((Byte)currentdomesticvendor.getValue(DSL.fieldByName(new String[] { "heat" }))).byteValue() != 0));
      mycombodata.set("dtg", Boolean.valueOf(((Byte)currentdomesticvendor.getValue(DSL.fieldByName(new String[] { "dtg" }))).byteValue() != 0));
      mycombodata.set("screenprint", Boolean.valueOf(((Byte)currentdomesticvendor.getValue(DSL.fieldByName(new String[] { "screenprint" }))).byteValue() != 0));
      mycombodata.set("patch", Boolean.valueOf(((Byte)currentdomesticvendor.getValue(DSL.fieldByName(new String[] { "patch" }))).byteValue() != 0));
      ((ArrayList)domesticvendors).add(mycombodata);
    }
    this.firstloaddata.setDomesticVendors((ArrayList)domesticvendors);
    
    sqlrecords = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "list_vendors_overseas" })).fetch();
    Object overseasvendors = new ArrayList();
    Object overseasdefaultshippinglist = new FastMap();
    for (Record currentoverseasvendor : sqlrecords) {
      ((ArrayList)overseasvendors).add(new OtherComboBoxModelData((String)currentoverseasvendor.getValue(DSL.fieldByName(new String[] { "name" })), String.valueOf(currentoverseasvendor.getValue(DSL.fieldByName(new String[] { "id" })))));
      ((FastMap)overseasdefaultshippinglist).put(String.valueOf(currentoverseasvendor.getValue(DSL.fieldByName(new String[] { "id" }))), String.valueOf(currentoverseasvendor.getValue(DSL.fieldByName(new String[] { "defaultshipping" }))));
    }
    this.firstloaddata.setOverseasVendors((ArrayList)overseasvendors);
    this.firstloaddata.setOverseasVendorDefaultShippingList((FastMap)overseasdefaultshippinglist);
    
    sqlrecords = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "list_categories" })).fetch();
    ArrayList<OtherComboBoxModelData> categorylist = new ArrayList();
    OtherComboBoxModelData mycombodata; for (Record currentcategory : sqlrecords) {
      mycombodata = new OtherComboBoxModelData((String)currentcategory.getValue(DSL.fieldByName(new String[] { "category" })));
      mycombodata.set("domestic", Boolean.valueOf(((Byte)currentcategory.getValue(DSL.fieldByName(new String[] { "domestic" }))).byteValue() != 0));
      mycombodata.set("overseas", Boolean.valueOf(((Byte)currentcategory.getValue(DSL.fieldByName(new String[] { "overseas" }))).byteValue() != 0));
      categorylist.add(mycombodata);
    }
    this.firstloaddata.setCategoryList(categorylist);
    
    DecorationInfo thedecorationinfo = new DecorationInfo();
    
    sqlrecords = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "list_decorations_locations" })).fetch();
    int j; for (Record currentlocation : sqlrecords) {
      DecorationInfoLocations thelocation = new DecorationInfoLocations();
      thelocation.setCanAdd(Boolean.valueOf(((Byte)currentlocation.getValue(DSL.fieldByName(new String[] { "canadd" }))).byteValue() != 0));
      String locationnames = (String)currentlocation.getValue(DSL.fieldByName(new String[] { "decorations" }));
      String[] thenames = locationnames.split(",");
      for (j = 0; j < thenames.length; j++) {
        if (!thenames[j].trim().equals("")) {
          thelocation.addName(thenames[j].trim());
        }
      }
      thedecorationinfo.addLocation((String)currentlocation.getValue(DSL.fieldByName(new String[] { "id" })), thelocation);
    }
    
    sqlrecords = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "list_decorations_overall" })).fetch();
    for (Record currentdecoroverall : sqlrecords) {
      com.ottocap.NewWorkFlow.client.DataHolder.ProductsInfo.Decoration.DecorationInfoNames thename = new com.ottocap.NewWorkFlow.client.DataHolder.ProductsInfo.Decoration.DecorationInfoNames();
      thename.setNames((String)currentdecoroverall.getValue(DSL.fieldByName(new String[] { "field1" })), (String)currentdecoroverall.getValue(DSL.fieldByName(new String[] { "field2" })), (String)currentdecoroverall.getValue(DSL.fieldByName(new String[] { "field3" })), (String)currentdecoroverall.getValue(DSL.fieldByName(new String[] { "field4" })));
      thedecorationinfo.addName((String)currentdecoroverall.getValue(DSL.fieldByName(new String[] { "name" })), thename);
    }
    this.firstloaddata.setDecorationInfo(thedecorationinfo);
    
    Object dummycolorstable = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "thread_dummycolors" })).fetch();
    
    sqlrecords = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "list_threads" })).fetch();
    ArrayList<OtherComboBoxModelData> threadbrandlist = new ArrayList();
    ArrayList<OtherComboBoxModelData> converttothreadlist = new ArrayList();
    java.util.TreeMap<String, ArrayList<OtherComboBoxModelData>> threadbrandcolor = new java.util.TreeMap();
    OtherComboBoxModelData mycombodata; for (Record currentthread : sqlrecords) {
      mycombodata = new OtherComboBoxModelData((String)currentthread.getValue(DSL.fieldByName(new String[] { "threadname" })));
      threadbrandlist.add(mycombodata);
      if (((Byte)currentthread.getValue(DSL.fieldByName(new String[] { "canconvertto" }))).byteValue() != 0) {
        converttothreadlist.add(mycombodata);
      }
      Result<Record> sqlrecords2 = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { (String)currentthread.getValue(DSL.fieldByName(new String[] { "colortable" })) })).fetch();
      ArrayList<OtherComboBoxModelData> colorarray = new ArrayList();
      for (Record currentcolor : sqlrecords2) {
        OtherComboBoxModelData colorcombobxdata = new OtherComboBoxModelData((String)currentcolor.getValue(DSL.fieldByName(new String[] { "code" })) + " - " + (String)currentcolor.getValue(DSL.fieldByName(new String[] { "colorname" })), (String)currentcolor.getValue(DSL.fieldByName(new String[] { "code" })));
        colorcombobxdata.set("colorvalue", (String)currentcolor.getValue(DSL.fieldByName(new String[] { "colorvalue" })));
        colorarray.add(colorcombobxdata);
      }
      
      for (int i = 0; i < 30; i++) {
        OtherComboBoxModelData colorcombobxdata = new OtherComboBoxModelData("ColorCode " + (i + 1) + " - " + ((Record)((Result)dummycolorstable).get(i % ((Result)dummycolorstable).size())).getValue(DSL.fieldByName(new String[] { "colorname" })), "ColorCode " + (i + 1));
        colorcombobxdata.set("colorvalue", ((Record)((Result)dummycolorstable).get(i % ((Result)dummycolorstable).size())).getValue(DSL.fieldByName(new String[] { "colorvalue" })));
        colorarray.add(colorcombobxdata);
      }
      

      threadbrandcolor.put((String)currentthread.getValue(DSL.fieldByName(new String[] { "threadname" })), colorarray);
    }
    
    this.firstloaddata.setThreadBrandList(threadbrandlist);
    this.firstloaddata.setConvertToThreadList(converttothreadlist);
    this.firstloaddata.setThreadBrandColor(threadbrandcolor);
    
    sqlrecords = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "list_screenprint_inks" })).fetch();
    ArrayList<OtherComboBoxModelData> inktypeslist = new ArrayList();
    for (Record currentinktype : sqlrecords) {
      inktypeslist.add(new OtherComboBoxModelData((String)currentinktype.getValue(DSL.fieldByName(new String[] { "name" }))));
    }
    this.firstloaddata.setInkTypesList(inktypeslist);
  }
  
  public FirstLoadData getFirstLoadData()
  {
    return this.firstloaddata;
  }
}
