package com.ottocap.NewWorkFlow.client.DataHolder.Stores;

import com.extjs.gxt.ui.client.core.FastMap;
import com.extjs.gxt.ui.client.store.ListStore;
import com.ottocap.NewWorkFlow.client.DataHolder.ProductsInfo.Decoration.DecorationInfo;
import com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBoxModelData;
import java.util.ArrayList;
import java.util.Arrays;


public class DataStores
{
  private ListStore<OtherComboBoxModelData> orderstatusstore = new ListStore();
  private ListStore<OtherComboBoxModelData> containerinvoicestatusstore = new ListStore();
  private ListStore<OtherComboBoxModelData> containerportoforiginstore = new ListStore();
  private ListStore<OtherComboBoxModelData> containerfreightforwarderstore = new ListStore();
  private ListStore<OtherComboBoxModelData> shippingtypestore = new ListStore();
  private ArrayList<OtherComboBoxModelData> overseasvendorlist = new ArrayList();
  private ListStore<OtherComboBoxModelData> pontentialrepeatfreqstore = new ListStore();
  private ListStore<OtherComboBoxModelData> vendorshippingstore = new ListStore();
  private ListStore<OtherComboBoxModelData> artworktypestore = new ListStore();
  private ListStore<OtherComboBoxModelData> domesticcategoryliststore = new ListStore();
  private ListStore<OtherComboBoxModelData> overseascategoryliststore = new ListStore();
  private ListStore<OtherComboBoxModelData> sampleapprovedliststore = new ListStore();
  private DecorationInfo decorationinfo = null;
  private ThreadInformationData threadinfodata = new ThreadInformationData();
  private ListStore<OtherComboBoxModelData> inktypesstore = new ListStore();
  private FastMap<ListStore<OtherComboBoxModelData>> domesticvendorliststores = new FastMap();
  private FastMap<NewStyleStore> domesticstylestorelist = new FastMap();
  private FastMap<NewStyleStore> overseasstylestorelist = new FastMap();
  private ListStore<OtherComboBoxModelData> employeelist = null;
  private FastMap<String> overseasdefaultshipping = new FastMap();
  
  public FastMap<NewStyleStore> getDomesticStyleStore() {
    return this.domesticstylestorelist;
  }
  
  public FastMap<NewStyleStore> getOverseasStyleStore() {
    return this.overseasstylestorelist;
  }
  
  public DataStores() {
    this.vendorshippingstore.add(Arrays.asList(new OtherComboBoxModelData[] { new OtherComboBoxModelData("Ocean"), new OtherComboBoxModelData("Air"), new OtherComboBoxModelData("Freight Forwarder - Air"), new OtherComboBoxModelData("Freight Forwarder - Ocean"), new OtherComboBoxModelData("CFS"), new OtherComboBoxModelData("Email"), new OtherComboBoxModelData("FOB China") }));
    
    this.sampleapprovedliststore.add(Arrays.asList(new OtherComboBoxModelData[] { new OtherComboBoxModelData("Sample Approved."), new OtherComboBoxModelData("Last Sample With Correct Changes Approved."), new OtherComboBoxModelData("Last Sample with Correct Changes Approved.  Must Submit Final Production Sample for Approval for 5K and above orders. "), new OtherComboBoxModelData("Sample Not Approved Change As Follow:"), new OtherComboBoxModelData("Sample Not Approved Change As Follow.  Must Submit Final Production Sample for Approval for 5K and above orders.") }));
  }
  
  public ListStore<OtherComboBoxModelData> getSampleApprovedListStore()
  {
    return this.sampleapprovedliststore;
  }
  
  public ListStore<OtherComboBoxModelData> getEmployeeList() {
    return this.employeelist;
  }
  
  public void setEmployeeList(ListStore<OtherComboBoxModelData> employeelist) {
    this.employeelist = employeelist;
  }
  
  public ListStore<OtherComboBoxModelData> getVendorShippingStore() {
    return this.vendorshippingstore;
  }
  
  public void setOrderStatusStore(ArrayList<OtherComboBoxModelData> orderstatuslist) {
    this.orderstatusstore.add(orderstatuslist);
  }
  
  public ListStore<OtherComboBoxModelData> getOrderStatusStore() {
    return this.orderstatusstore;
  }
  
  public void setContainerInvoiceStatusStore(ArrayList<OtherComboBoxModelData> containerinvoicestatuslist) {
    this.containerinvoicestatusstore.add(containerinvoicestatuslist);
  }
  
  public ListStore<OtherComboBoxModelData> getContainerInvoiceStatusStore() {
    return this.containerinvoicestatusstore;
  }
  
  public void setContainerPortofOriginStore(ArrayList<OtherComboBoxModelData> containerportoforiginlist) {
    this.containerportoforiginstore.add(containerportoforiginlist);
  }
  
  public ListStore<OtherComboBoxModelData> getContainerPortofOriginStore() {
    return this.containerportoforiginstore;
  }
  
  public void setContainerFreightForwarderStore(ArrayList<OtherComboBoxModelData> containerfreightforwarderlist) {
    this.containerfreightforwarderstore.add(containerfreightforwarderlist);
  }
  
  public ListStore<OtherComboBoxModelData> getContainerFreightForwarderStore() {
    return this.containerfreightforwarderstore;
  }
  
  public void setShippingTypeStore(ArrayList<OtherComboBoxModelData> shippingtypelist) {
    this.shippingtypestore.add(shippingtypelist);
  }
  
  public ListStore<OtherComboBoxModelData> getShippingTypeStore() {
    return this.shippingtypestore;
  }
  
  public void setOverseasVendorList(ArrayList<OtherComboBoxModelData> overseasvendorlist) {
    this.overseasvendorlist = overseasvendorlist;
  }
  
  public ArrayList<OtherComboBoxModelData> getOverseasVendorList() {
    return this.overseasvendorlist;
  }
  
  public void setCategoryListStore(ArrayList<OtherComboBoxModelData> categorylist) {
    for (OtherComboBoxModelData model : categorylist) {
      if (((Boolean)model.get("domestic")).booleanValue()) {
        this.domesticcategoryliststore.add(model);
      }
      if (((Boolean)model.get("overseas")).booleanValue()) {
        this.overseascategoryliststore.add(model);
      }
    }
  }
  
  public ListStore<OtherComboBoxModelData> getDomesticCategoryListStore() {
    return this.domesticcategoryliststore;
  }
  
  public ListStore<OtherComboBoxModelData> getOverseasCategoryListStore() {
    return this.overseascategoryliststore;
  }
  
  public void setPontentialFreqStore(ArrayList<OtherComboBoxModelData> pontentialrepeatfreqlist) {
    this.pontentialrepeatfreqstore.add(pontentialrepeatfreqlist);
  }
  
  public ListStore<OtherComboBoxModelData> getPontentialFreqStore() {
    return this.pontentialrepeatfreqstore;
  }
  
  public void setArtworkTypeStore(ArrayList<OtherComboBoxModelData> artworktypelist) {
    this.artworktypestore.add(artworktypelist);
  }
  
  public ListStore<OtherComboBoxModelData> getArtworkTypeStore() {
    return this.artworktypestore;
  }
  
  public void setDecorationInfo(DecorationInfo decorationinfo) {
    this.decorationinfo = decorationinfo;
  }
  
  public DecorationInfo getDecorationinfo() {
    return this.decorationinfo;
  }
  
  public ThreadInformationData getThreadsInformationData() {
    return this.threadinfodata;
  }
  
  public void setInkTypes(ArrayList<OtherComboBoxModelData> inktypes) {
    this.inktypesstore.add(inktypes);
  }
  
  public ListStore<OtherComboBoxModelData> getInkTypes() {
    return this.inktypesstore;
  }
  
  public void setDomesticVendorStore(ArrayList<OtherComboBoxModelData> domesticvendorlist) {
    ListStore<OtherComboBoxModelData> dig = new ListStore();
    ListStore<OtherComboBoxModelData> emb = new ListStore();
    ListStore<OtherComboBoxModelData> heat = new ListStore();
    ListStore<OtherComboBoxModelData> cadprint = new ListStore();
    ListStore<OtherComboBoxModelData> screenprint = new ListStore();
    ListStore<OtherComboBoxModelData> dtg = new ListStore();
    ListStore<OtherComboBoxModelData> patch = new ListStore();
    for (OtherComboBoxModelData currentdomesticvendor : domesticvendorlist) {
      if (((Boolean)currentdomesticvendor.get("dig")).booleanValue()) {
        dig.add(currentdomesticvendor);
      }
      if (((Boolean)currentdomesticvendor.get("emb")).booleanValue()) {
        emb.add(currentdomesticvendor);
      }
      if (((Boolean)currentdomesticvendor.get("heat")).booleanValue()) {
        heat.add(currentdomesticvendor);
      }
      if (((Boolean)currentdomesticvendor.get("cadprint")).booleanValue()) {
        cadprint.add(currentdomesticvendor);
      }
      if (((Boolean)currentdomesticvendor.get("screenprint")).booleanValue()) {
        screenprint.add(currentdomesticvendor);
      }
      if (((Boolean)currentdomesticvendor.get("dtg")).booleanValue()) {
        dtg.add(currentdomesticvendor);
      }
      if (((Boolean)currentdomesticvendor.get("patch")).booleanValue()) {
        patch.add(currentdomesticvendor);
      }
    }
    
    this.domesticvendorliststores.put("dig", dig);
    this.domesticvendorliststores.put("emb", dig);
    this.domesticvendorliststores.put("heat", heat);
    this.domesticvendorliststores.put("cadprint", cadprint);
    this.domesticvendorliststores.put("screenprint", screenprint);
    this.domesticvendorliststores.put("dtg", dtg);
    this.domesticvendorliststores.put("patch", patch);
  }
  
  public ListStore<OtherComboBoxModelData> getDomesticVendorStoreCategory(String string) {
    return (ListStore)this.domesticvendorliststores.get(string);
  }
  
  public String getOverseasVendorDefaultShipping(String vendornumber) {
    return (String)this.overseasdefaultshipping.get(vendornumber);
  }
  
  public void setOverseasVendorDefaultShipping(FastMap<String> defaultshippinglist) {
    this.overseasdefaultshipping = defaultshippinglist;
  }
}
