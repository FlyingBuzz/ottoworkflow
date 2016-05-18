package com.ottocap.NewWorkFlow.server.RPCService;

import com.extjs.gxt.ui.client.core.FastMap;
import com.ottocap.NewWorkFlow.client.DataHolder.ProductsInfo.Decoration.DecorationInfo;
import com.ottocap.NewWorkFlow.client.DataHolder.ProductsInfo.Decoration.DecorationInfoLocations;
import com.ottocap.NewWorkFlow.client.DataHolder.ProductsInfo.Decoration.DecorationInfoNames;
import com.ottocap.NewWorkFlow.client.FirstLoadData;
import com.ottocap.NewWorkFlow.client.StoredUser;
import com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBoxModelData;
import com.ottocap.NewWorkFlow.server.SQLTable;
import java.util.ArrayList;
import java.util.TreeMap;
import javax.servlet.http.HttpSession;

public class GetFirstLoadData
{
  private FirstLoadData firstloaddata = new FirstLoadData();
  
  public GetFirstLoadData(HttpSession session) throws Exception {
    if ((session.getAttribute("username") == null) || (session.getAttribute("level") == null)) {
      throw new Exception("Please Login");
    }
    
    SQLTable sqltable = new SQLTable();
    
    this.firstloaddata.getUserInfo().setAccessLevel(((Integer)session.getAttribute("level")).intValue());
    this.firstloaddata.getUserInfo().setUsername((String)session.getAttribute("username"));
    sqltable.makeTable("SELECT `email` FROM `employees` WHERE `username` = '" + session.getAttribute("username") + "' LIMIT 1");
    this.firstloaddata.getUserInfo().setEmailAddress((String)sqltable.getValue());
    
    sqltable.makeTable("SELECT `name` FROM `list_pontential_repeat`");
    ArrayList<FastMap<Object>> pontentialrepeatfreqtable = sqltable.getTable();
    ArrayList<OtherComboBoxModelData> pontentialrepeatfreq = new ArrayList();
    for (FastMap<Object> currentrepeattable : pontentialrepeatfreqtable) {
      OtherComboBoxModelData mymodel = new OtherComboBoxModelData((String)currentrepeattable.get("name"));
      pontentialrepeatfreq.add(mymodel);
    }
    this.firstloaddata.setPontentialRepeatFreqList(pontentialrepeatfreq);
    
    sqltable.makeTable("SELECT * FROM `list_order_status`");
    ArrayList<FastMap<Object>> orderstatustable = sqltable.getTable();
    Object orderstatus = new ArrayList();
    for (FastMap<Object> currentorderstatus : orderstatustable) {
      OtherComboBoxModelData mymodel = new OtherComboBoxModelData((String)currentorderstatus.get("name"));
      mymodel.set("allowolddate", currentorderstatus.get("allowolddate"));
      ((ArrayList)orderstatus).add(mymodel);
    }
    this.firstloaddata.setOrderStatus((ArrayList)orderstatus);
    
    sqltable.makeTable("SELECT `name` FROM `list_containerinvoice_status`");
    ArrayList<FastMap<Object>> containerinvoicestatustable = sqltable.getTable();
    Object containerinvoicestatus = new ArrayList();
    for (FastMap<Object> currentcontainerinvoicestatus : containerinvoicestatustable) {
      ((ArrayList)containerinvoicestatus).add(new OtherComboBoxModelData((String)currentcontainerinvoicestatus.get("name")));
    }
    this.firstloaddata.setContainerInvoiceStatus((ArrayList)containerinvoicestatus);
    
    sqltable.makeTable("SELECT `name` FROM `list_containerinvoice_status`");
    ArrayList<FastMap<Object>> containerportoforgintable = sqltable.getTable();
    Object containerportoforgin = new ArrayList();
    for (FastMap<Object> currentcontainerportoforigin : containerportoforgintable) {
      ((ArrayList)containerportoforgin).add(new OtherComboBoxModelData((String)currentcontainerportoforigin.get("name")));
    }
    this.firstloaddata.setContainerPortofOrigin((ArrayList)containerportoforgin);
    
    sqltable.makeTable("SELECT `name` FROM `list_containerinvoice_status`");
    ArrayList<FastMap<Object>> containerfreightforwardertable = sqltable.getTable();
    Object containerfreightforwarder = new ArrayList();
    for (FastMap<Object> currentcontainerfreightforwarder : containerfreightforwardertable) {
      ((ArrayList)containerfreightforwarder).add(new OtherComboBoxModelData((String)currentcontainerfreightforwarder.get("name")));
    }
    this.firstloaddata.setContainerFreightForwarder((ArrayList)containerfreightforwarder);
    
    sqltable.makeTable("SELECT `name` FROM `list_shipping_methods`");
    ArrayList<FastMap<Object>> shippingmethodstable = sqltable.getTable();
    Object shippingtypes = new ArrayList();
    for (FastMap<Object> currentshippingmethod : shippingmethodstable) {
      ((ArrayList)shippingtypes).add(new OtherComboBoxModelData((String)currentshippingmethod.get("name")));
    }
    this.firstloaddata.setShippingTypes((ArrayList)shippingtypes);
    
    sqltable.makeTable("SELECT `name` FROM `list_artwork_types`");
    ArrayList<FastMap<Object>> artworktypestable = sqltable.getTable();
    Object artworktypes = new ArrayList();
    for (FastMap<Object> currentartworktype : artworktypestable) {
      ((ArrayList)artworktypes).add(new OtherComboBoxModelData((String)currentartworktype.get("name")));
    }
    this.firstloaddata.setArtworkTypes((ArrayList)artworktypes);
    
    sqltable.makeTable("SELECT * FROM `list_vendors_domestic`");
    ArrayList<FastMap<Object>> domesticvendorstable = sqltable.getTable();
    Object domesticvendors = new ArrayList();
    for (FastMap<Object> currentdomesticvendor : domesticvendorstable) {
      OtherComboBoxModelData mycombodata = new OtherComboBoxModelData((String)currentdomesticvendor.get("name"), String.valueOf(currentdomesticvendor.get("id")));
      mycombodata.set("dig", currentdomesticvendor.get("dig"));
      mycombodata.set("emb", currentdomesticvendor.get("emb"));
      mycombodata.set("heat", currentdomesticvendor.get("heat"));
      mycombodata.set("dtg", currentdomesticvendor.get("dtg"));
      mycombodata.set("cadprint", currentdomesticvendor.get("cadprint"));
      mycombodata.set("screenprint", currentdomesticvendor.get("screenprint"));
      mycombodata.set("patch", currentdomesticvendor.get("patch"));
      ((ArrayList)domesticvendors).add(mycombodata);
    }
    this.firstloaddata.setDomesticVendors((ArrayList)domesticvendors);
    
    sqltable.makeTable("SELECT * FROM `list_vendors_overseas`");
    ArrayList<FastMap<Object>> overseasvendorstable = sqltable.getTable();
    Object overseasvendors = new ArrayList();
    for (FastMap<Object> currentoverseasvendor : overseasvendorstable) {
      ((ArrayList)overseasvendors).add(new OtherComboBoxModelData((String)currentoverseasvendor.get("name"), String.valueOf(currentoverseasvendor.get("id"))));
    }
    this.firstloaddata.setOverseasVendors((ArrayList)overseasvendors);
    
    FastMap<String> overseasdefaultshippinglist = new FastMap();
    for (Object currentoverseasvendor : overseasvendorstable) {
      overseasdefaultshippinglist.put(String.valueOf(((FastMap)currentoverseasvendor).get("id")), String.valueOf(((FastMap)currentoverseasvendor).get("defaultshipping")));
    }
    this.firstloaddata.setOverseasVendorDefaultShippingList(overseasdefaultshippinglist);
    
    sqltable.makeTable("SELECT * FROM `list_categories`");
    Object categorylisttable = sqltable.getTable();
    Object categorylist = new ArrayList();
    for (FastMap<Object> currentcategory : (ArrayList)categorylisttable) {
      OtherComboBoxModelData mycombodata = new OtherComboBoxModelData((String)currentcategory.get("category"));
      mycombodata.set("domestic", (Boolean)currentcategory.get("domestic"));
      mycombodata.set("overseas", (Boolean)currentcategory.get("overseas"));
      ((ArrayList)categorylist).add(mycombodata);
    }
    this.firstloaddata.setCategoryList((ArrayList)categorylist);
    
    DecorationInfo thedecorationinfo = new DecorationInfo();
    
    sqltable.makeTable("SELECT * FROM `list_decorations_locations`");
    Object thelocationstable = sqltable.getTable();
    DecorationInfoLocations thelocation; for (FastMap<Object> currentlocation : (ArrayList)thelocationstable) {
      thelocation = new DecorationInfoLocations();
      thelocation.setCanAdd((Boolean)currentlocation.get("canadd"));
      String locationnames = (String)currentlocation.get("decorations");
      String[] thenames = locationnames.split(",");
      for (int j = 0; j < thenames.length; j++) {
        if (!thenames[j].trim().equals("")) {
          thelocation.addName(thenames[j].trim());
        }
      }
      thedecorationinfo.addLocation((String)currentlocation.get("id"), thelocation);
    }
    
    sqltable.makeTable("SELECT * FROM `list_decorations_overall`");
    ArrayList<FastMap<Object>> decoroveralltable = sqltable.getTable();
    for (Object currentdecoroverall : decoroveralltable) {
      DecorationInfoNames thename = new DecorationInfoNames();
      thename.setNames((String)((FastMap)currentdecoroverall).get("field1"), (String)((FastMap)currentdecoroverall).get("field2"), (String)((FastMap)currentdecoroverall).get("field3"), (String)((FastMap)currentdecoroverall).get("field4"));
      thedecorationinfo.addName((String)((FastMap)currentdecoroverall).get("name"), thename);
    }
    this.firstloaddata.setDecorationInfo(thedecorationinfo);
    
    sqltable.makeTable("SELECT * FROM `list_threads`");
    Object threadbrandlist = new ArrayList();
    ArrayList<OtherComboBoxModelData> converttothreadlist = new ArrayList();
    TreeMap<String, ArrayList<OtherComboBoxModelData>> threadbrandcolor = new TreeMap();
    ArrayList<FastMap<Object>> threadstable = sqltable.getTable();
    
    sqltable.makeTable("SELECT * FROM `thread_dummycolors`");
    ArrayList<FastMap<Object>> dummycolorstable = sqltable.getTable();
    OtherComboBoxModelData mycombodata;
    for (FastMap<Object> currentthread : threadstable) {
      mycombodata = new OtherComboBoxModelData((String)currentthread.get("threadname"));
      ((ArrayList)threadbrandlist).add(mycombodata);
      if (((Boolean)currentthread.get("canconvertto")).booleanValue()) {
        converttothreadlist.add(mycombodata);
      }
      sqltable.makeTable("SELECT * FROM `" + (String)currentthread.get("colortable") + "`");
      

      ArrayList<OtherComboBoxModelData> colorarray = new ArrayList();
      ArrayList<FastMap<Object>> colortable = sqltable.getTable();
      for (FastMap<Object> currentcolor : colortable) {
        OtherComboBoxModelData colorcombobxdata = new OtherComboBoxModelData((String)currentcolor.get("code") + " - " + (String)currentcolor.get("colorname"), (String)currentcolor.get("code"));
        colorcombobxdata.set("colorvalue", (String)currentcolor.get("colorvalue"));
        colorarray.add(colorcombobxdata);
      }
      
      for (int i = 0; i < 30; i++) {
        OtherComboBoxModelData colorcombobxdata = new OtherComboBoxModelData("ColorCode " + (i + 1) + " - " + ((FastMap)dummycolorstable.get(i % dummycolorstable.size())).get("colorname"), "ColorCode " + (i + 1));
        colorcombobxdata.set("colorvalue", ((FastMap)dummycolorstable.get(i % dummycolorstable.size())).get("colorvalue"));
        colorarray.add(colorcombobxdata);
      }
      

      threadbrandcolor.put((String)currentthread.get("threadname"), colorarray);
    }
    
    converttothreadlist.add(new OtherComboBoxModelData("Keep Colors"));
    this.firstloaddata.setThreadBrandList((ArrayList)threadbrandlist);
    this.firstloaddata.setConvertToThreadList(converttothreadlist);
    this.firstloaddata.setThreadBrandColor(threadbrandcolor);
    
    sqltable.makeTable("SELECT * FROM `list_screenprint_inks`");
    ArrayList<OtherComboBoxModelData> inktypeslist = new ArrayList();
    for (Object currentinktype : sqltable.getTable()) {
      inktypeslist.add(new OtherComboBoxModelData((String)((FastMap)currentinktype).get("name")));
    }
    this.firstloaddata.setInkTypesList(inktypeslist);
    
    sqltable.closeSQL();
  }
  
  public FirstLoadData getFirstLoadData() {
    return this.firstloaddata;
  }
}
