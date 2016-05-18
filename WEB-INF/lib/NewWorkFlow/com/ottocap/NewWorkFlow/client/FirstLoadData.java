package com.ottocap.NewWorkFlow.client;

import com.extjs.gxt.ui.client.core.FastMap;
import com.ottocap.NewWorkFlow.client.DataHolder.ProductsInfo.Decoration.DecorationInfo;
import com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBoxModelData;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeMap;




public class FirstLoadData
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private ArrayList<OtherComboBoxModelData> orderstatus = null;
  private ArrayList<OtherComboBoxModelData> containerinvoicestatus = null;
  private ArrayList<OtherComboBoxModelData> containerportoforigin = null;
  private ArrayList<OtherComboBoxModelData> containerfreightforwarder = null;
  private ArrayList<OtherComboBoxModelData> shippingtypes = null;
  private ArrayList<OtherComboBoxModelData> domesticvendors = null;
  private ArrayList<OtherComboBoxModelData> overseasvendors = null;
  private ArrayList<OtherComboBoxModelData> categorylist = null;
  private ArrayList<OtherComboBoxModelData> pontentialrepeatfreqlist = null;
  private ArrayList<OtherComboBoxModelData> artworktypes = null;
  private StoredUser userinfo = new StoredUser();
  private DecorationInfo decorationinfo = null;
  private ArrayList<OtherComboBoxModelData> converttothreadlist = null;
  private ArrayList<OtherComboBoxModelData> threadbrandlist = null;
  private TreeMap<String, ArrayList<OtherComboBoxModelData>> threadbrandcolor = null;
  private ArrayList<OtherComboBoxModelData> inktypeslist = null;
  private FastMap<String> overseasvendordefaultshippinglist = null;
  



  public void setOrderStatus(ArrayList<OtherComboBoxModelData> orderstatus)
  {
    this.orderstatus = orderstatus;
  }
  
  public ArrayList<OtherComboBoxModelData> getOrderStatus() {
    return this.orderstatus;
  }
  
  public void setContainerInvoiceStatus(ArrayList<OtherComboBoxModelData> containerinvoicestatus)
  {
    this.containerinvoicestatus = containerinvoicestatus;
  }
  
  public ArrayList<OtherComboBoxModelData> getContainerInvoiceStatus() {
    return this.containerinvoicestatus;
  }
  
  public void setContainerPortofOrigin(ArrayList<OtherComboBoxModelData> containerportoforigin) {
    this.containerportoforigin = containerportoforigin;
  }
  
  public ArrayList<OtherComboBoxModelData> getContainerPortofOrigin() {
    return this.containerportoforigin;
  }
  
  public void setContainerFreightForwarder(ArrayList<OtherComboBoxModelData> containerfreightforwarder) {
    this.containerfreightforwarder = containerfreightforwarder;
  }
  
  public ArrayList<OtherComboBoxModelData> getContainerFreightForwarder() {
    return this.containerfreightforwarder;
  }
  
  public void setShippingTypes(ArrayList<OtherComboBoxModelData> shippingtypes) {
    this.shippingtypes = shippingtypes;
  }
  
  public ArrayList<OtherComboBoxModelData> getShippingTypes() {
    return this.shippingtypes;
  }
  
  public void setDomesticVendors(ArrayList<OtherComboBoxModelData> domesticvendors) {
    this.domesticvendors = domesticvendors;
  }
  
  public ArrayList<OtherComboBoxModelData> getDomesticVendors() {
    return this.domesticvendors;
  }
  
  public void setCategoryList(ArrayList<OtherComboBoxModelData> categorylist) {
    this.categorylist = categorylist;
  }
  
  public ArrayList<OtherComboBoxModelData> getCategoryList() {
    return this.categorylist;
  }
  
  public void setInkTypesList(ArrayList<OtherComboBoxModelData> inktypeslist) {
    this.inktypeslist = inktypeslist;
  }
  
  public ArrayList<OtherComboBoxModelData> getInkTypesList() {
    return this.inktypeslist;
  }
  
  public StoredUser getUserInfo()
  {
    return this.userinfo;
  }
  
  public void setPontentialRepeatFreqList(ArrayList<OtherComboBoxModelData> pontentialrepeatfreqlist) {
    this.pontentialrepeatfreqlist = pontentialrepeatfreqlist;
  }
  
  public ArrayList<OtherComboBoxModelData> getPontentialRepeatFreqList() {
    return this.pontentialrepeatfreqlist;
  }
  
  public void setOverseasVendors(ArrayList<OtherComboBoxModelData> overseasvendors) {
    this.overseasvendors = overseasvendors;
  }
  
  public ArrayList<OtherComboBoxModelData> getOverseasVendors() {
    return this.overseasvendors;
  }
  
  public void setArtworkTypes(ArrayList<OtherComboBoxModelData> artworktypes) {
    this.artworktypes = artworktypes;
  }
  
  public ArrayList<OtherComboBoxModelData> getArtworkTypes() {
    return this.artworktypes;
  }
  
  public void setDecorationInfo(DecorationInfo decorationinfo) {
    this.decorationinfo = decorationinfo;
  }
  
  public DecorationInfo getDecorationInfo() {
    return this.decorationinfo;
  }
  
  public void setConvertToThreadList(ArrayList<OtherComboBoxModelData> converttothreadlist) {
    this.converttothreadlist = converttothreadlist;
  }
  
  public ArrayList<OtherComboBoxModelData> getConvertToThreadList() {
    return this.converttothreadlist;
  }
  
  public void setThreadBrandList(ArrayList<OtherComboBoxModelData> threadbrandlist) {
    this.threadbrandlist = threadbrandlist;
  }
  
  public ArrayList<OtherComboBoxModelData> getThreadBrandList() {
    return this.threadbrandlist;
  }
  
  public void setThreadBrandColor(TreeMap<String, ArrayList<OtherComboBoxModelData>> threadbrandcolor) {
    this.threadbrandcolor = threadbrandcolor;
  }
  
  public TreeMap<String, ArrayList<OtherComboBoxModelData>> getThreadBrandColor() {
    return this.threadbrandcolor;
  }
  
  public FastMap<String> setOverseasVendorDefaultShippingList(FastMap<String> overseasvendordefaultshippinglist) {
    return this.overseasvendordefaultshippinglist = overseasvendordefaultshippinglist;
  }
  
  public FastMap<String> getOverseasVendorDefaultShippingList() {
    return this.overseasvendordefaultshippinglist;
  }
}
