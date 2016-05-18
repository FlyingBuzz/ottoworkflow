package com.ottocap.NewWorkFlow.client.Widget.ComboBoxes;

import com.extjs.gxt.ui.client.core.FastMap;
import com.extjs.gxt.ui.client.data.BasePagingLoadConfig;
import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.ListLoader;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OtherComboBox extends ComboBox<OtherComboBoxModelData>
{
  private ArrayList<String> searchfields = new ArrayList();
  private ArrayList<OtherComboBoxModelData> datasource = null;
  private FastMap<String> remotefilter = new FastMap();
  private String othertitle = "Other: ";
  
  public OtherComboBox() {
    setDisplayField("name");
    setQueryDelay(100);
    
    setTriggerAction(com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction.ALL);
    this.searchfields.add("name");
    this.searchfields.add("value");
  }
  
  public void setOtherTitle(String othertitle) {
    this.othertitle = othertitle;
  }
  
  protected void triggerBlur(com.extjs.gxt.ui.client.event.ComponentEvent ce)
  {
    if ((getValue() == null) || (!getValue().getName().equals(getRawValue()))) {
      setValue(getRawValue());
    }
    
    if ((this.datasource != null) && (getForceSelection())) {
      super.triggerBlur(ce);
    } else {
      super.triggerBlur(ce);
    }
  }
  
  private OtherComboBoxModelData matchValue(String value) {
    if ((value == null) || (value.equals(""))) {
      return null;
    }
    value = value.toLowerCase();
    Iterator localIterator1;
    Iterator localIterator2;
    if (this.datasource == null)
    {


      for (localIterator1 = this.store.getAllModels().iterator(); localIterator1.hasNext(); 
          localIterator2.hasNext())
      {
        OtherComboBoxModelData model = (OtherComboBoxModelData)localIterator1.next();
        localIterator2 = this.searchfields.iterator(); continue;String currentfield = (String)localIterator2.next();
        if (value.equals(((String)model.get(currentfield)).toLowerCase())) {
          return model;
        }
        
      }
    } else {
      for (localIterator1 = this.datasource.iterator(); localIterator1.hasNext(); 
          localIterator2.hasNext())
      {
        OtherComboBoxModelData model = (OtherComboBoxModelData)localIterator1.next();
        localIterator2 = this.searchfields.iterator(); continue;String currentfield = (String)localIterator2.next();
        if (value.equals(((String)model.get(currentfield)).toLowerCase())) {
          return model;
        }
      }
    }
    
    return null;
  }
  
  public OtherComboBoxModelData findTheValue(String value) {
    OtherComboBoxModelData thevalue = null;
    if ((value == null) || (value.equals(""))) {
      return thevalue;
    }
    
    if (value.startsWith(this.othertitle)) {
      value = value.substring(this.othertitle.length());
    }
    if (this.store != null) {
      thevalue = matchValue(value);
    }
    if (((this.store == null) || (thevalue == null)) && (!getForceSelection())) {
      thevalue = new OtherComboBoxModelData(this.othertitle + value, value);
    }
    
    if ((thevalue == null) && (getForceSelection()))
    {
      return getValue();
    }
    return thevalue;
  }
  
  public void setValue(String value) {
    OtherComboBoxModelData thevalue = null;
    if ((value == null) || (value.equals(""))) {
      if (getForceSelection()) {
        doForce();
        return;
      }
      setRawValue(null);
      setValue(thevalue);
      
      return;
    }
    

    if (value.startsWith(this.othertitle)) {
      value = value.substring(this.othertitle.length());
    }
    if (this.store != null) {
      thevalue = matchValue(value);
    }
    if (((this.store == null) || (thevalue == null)) && (!getForceSelection())) {
      thevalue = new OtherComboBoxModelData(this.othertitle + value, value);
    }
    
    if ((thevalue == null) && (getForceSelection())) {
      doForce();
      return;
    }
    if (thevalue == null) {
      setRawValue(null);
    } else {
      setRawValue((String)thevalue.get(getDisplayField()));
    }
    setValue(thevalue);
  }
  
  public void changeStore(ListStore<OtherComboBoxModelData> thestore, String value)
  {
    if (getStore() != null) {
      setStore(thestore);
      getListView().setStore(thestore);
    } else {
      setStore(thestore);
    }
    if (getForceSelection()) {
      OtherComboBoxModelData tempvalue = matchValue(value);
      if (tempvalue == null) {
        if (thestore.getAllModels().size() == 1) {
          setValue((OtherComboBoxModelData)thestore.getAllModels().get(0));
        } else {
          setRawValue(null);
          setValue(tempvalue);
        }
      } else {
        setRawValue((String)tempvalue.get(getDisplayField()));
        setValue(tempvalue);
      }
    } else {
      setValue(value);
    }
  }
  
  public boolean isOther()
  {
    if (getValue() == null) {
      return true;
    }
    return getValue().getName().startsWith(this.othertitle);
  }
  
  public void addSearchField(String field)
  {
    this.searchfields.add(field);
  }
  
  public void setDataSource(ArrayList<OtherComboBoxModelData> datasource) {
    this.datasource = datasource;
  }
  
  protected PagingLoadConfig getParams(String query)
  {
    BasePagingLoadConfig config = new BasePagingLoadConfig();
    config.setLimit(getPageSize());
    config.setOffset(0);
    config.set("query", query);
    config.set("searchfields", this.searchfields);
    config.set("datasource", this.datasource);
    config.set("remotefilter", this.remotefilter);
    return config;
  }
  
  public void changeDataSource(ArrayList<OtherComboBoxModelData> datasource, String value) {
    setDataSource(datasource);
    if (getForceSelection()) {
      OtherComboBoxModelData tempvalue = matchValue(value);
      if (tempvalue == null) {
        if (datasource.size() == 1) {
          setValue((OtherComboBoxModelData)datasource.get(0));
        } else {
          setRawValue(null);
          setValue(tempvalue);
        }
      } else {
        setRawValue((String)tempvalue.get(getDisplayField()));
        setValue(tempvalue);
      }
    } else {
      setValue(value);
    }
  }
  
  public OtherComboBoxModelData getValue() {
    if (this.datasource == null) {
      if (getForceSelection()) {
        return (OtherComboBoxModelData)super.getValue();
      }
      return (OtherComboBoxModelData)this.value;
    }
    

    if (getForceSelection()) {
      OtherComboBoxModelData thevalue = matchValue(getRawValue());
      if (thevalue == null) {
        return (OtherComboBoxModelData)super.getValue();
      }
      return thevalue;
    }
    
    return (OtherComboBoxModelData)this.value;
  }
  

  public void setRemoteFilter(String source, String filter)
  {
    this.remotefilter.put(source, filter);
    this.store.getLoader().load(getParams(""));
  }
  
  public void clearRemoteFilter() {
    this.remotefilter.clear();
    this.store.getLoader().load(getParams(""));
  }
  
  public static ListStore<OtherComboBoxModelData> getPagingStore() {
    RpcProxy<BasePagingLoadResult<OtherComboBoxModelData>> proxy = new RpcProxy()
    {

      protected void load(Object loadConfig, AsyncCallback<BasePagingLoadResult<OtherComboBoxModelData>> callback)
      {
        PagingLoadConfig loadconfig = (PagingLoadConfig)loadConfig;
        ArrayList<OtherComboBoxModelData> fulldata = (ArrayList)loadconfig.get("datasource");
        String currentsearch = (String)loadconfig.get("query");
        ArrayList<String> searchfields = (ArrayList)loadconfig.get("searchfields");
        FastMap<String> remotefilter = (FastMap)loadconfig.get("remotefilter");
        currentsearch = currentsearch.toLowerCase();
        
        int matchcounter = 0;
        int offsetcount = 0;
        
        ArrayList<OtherComboBoxModelData> partdata = new ArrayList();
        
        for (OtherComboBoxModelData currentmodeldata : fulldata) {
          boolean passfilter = true;
          for (String currentfilter : remotefilter.keySet()) {
            if (!((String)remotefilter.get(currentfilter)).equals(currentmodeldata.get(currentfilter))) {
              passfilter = false;
              break;
            }
          }
          if (passfilter) {
            for (String currentsearchfield : searchfields) {
              if (((String)currentmodeldata.get(currentsearchfield)).toLowerCase().contains(currentsearch)) {
                if (offsetcount < loadconfig.getOffset()) {
                  offsetcount++;
                  matchcounter++;
                  break; } if (partdata.size() < loadconfig.getLimit()) {
                  matchcounter++;
                  partdata.add(currentmodeldata);
                  break; }
                matchcounter++;
                
                break;
              }
            }
          }
        }
        
        callback.onSuccess(new BasePagingLoadResult(partdata, loadconfig.getOffset(), matchcounter));
      }
      
    };
    com.extjs.gxt.ui.client.data.BasePagingLoader<BasePagingLoadResult<OtherComboBoxModelData>> loader = new com.extjs.gxt.ui.client.data.BasePagingLoader(proxy);
    
    return new ListStore(loader);
  }
}
