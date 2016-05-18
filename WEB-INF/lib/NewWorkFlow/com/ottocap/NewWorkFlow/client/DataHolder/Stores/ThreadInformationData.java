package com.ottocap.NewWorkFlow.client.DataHolder.Stores;

import com.extjs.gxt.ui.client.store.ListStore;
import com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBoxModelData;
import java.util.ArrayList;
import java.util.TreeMap;


public class ThreadInformationData
{
  private ListStore<OtherComboBoxModelData> threadbrandsstore = new ListStore();
  private ListStore<OtherComboBoxModelData> converttothreadstore = new ListStore();
  private TreeMap<String, ArrayList<OtherComboBoxModelData>> threadbrandcolorlist = new TreeMap();
  
  public void addThreadBrandColor(String thethread, ArrayList<OtherComboBoxModelData> threadbrandcolors) {
    this.threadbrandcolorlist.put(thethread, threadbrandcolors);
  }
  
  public ArrayList<OtherComboBoxModelData> getThreadBrandColor(String thethread) {
    if (this.threadbrandcolorlist.containsKey(thethread)) {
      return (ArrayList)this.threadbrandcolorlist.get(thethread);
    }
    return new ArrayList();
  }
  
  public void setThreadBrands(ArrayList<OtherComboBoxModelData> threadbrands)
  {
    this.threadbrandsstore.add(threadbrands);
  }
  
  public ListStore<OtherComboBoxModelData> getThreadBrands() {
    return this.threadbrandsstore;
  }
  
  public void setConvertToThreadStore(ArrayList<OtherComboBoxModelData> convertolist) {
    this.converttothreadstore.add(convertolist);
  }
  
  public ListStore<OtherComboBoxModelData> getConvertToThreadStore() {
    return this.converttothreadstore;
  }
}
