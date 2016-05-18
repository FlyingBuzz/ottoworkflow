package com.extjs.gxt.ui.client.store;

import com.extjs.gxt.ui.client.data.ChangeEvent;
import com.extjs.gxt.ui.client.data.ChangeEventSource;
import com.extjs.gxt.ui.client.data.ChangeListener;
import com.extjs.gxt.ui.client.data.DefaultModelComparer;
import com.extjs.gxt.ui.client.data.ModelComparer;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.ModelKeyProvider;
import com.extjs.gxt.ui.client.data.SortInfo;
import com.extjs.gxt.ui.client.event.BaseObservable;
import com.extjs.gxt.ui.client.event.EventType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



























































public abstract class Store<M extends ModelData>
  extends BaseObservable
{
  public static final EventType BeforeAdd = new EventType();
  



  public static final EventType BeforeClear = new EventType();
  



  public static final EventType BeforeDataChanged = new EventType();
  



  public static final EventType BeforeRemove = new EventType();
  



  public static final EventType BeforeSort = new EventType();
  



  public static final EventType DataChanged = new EventType();
  



  public static final EventType Filter = new EventType();
  



  public static final EventType Sort = new EventType();
  



  public static final EventType Add = new EventType();
  



  public static final EventType Remove = new EventType();
  



  public static final EventType Update = new EventType();
  



  public static final EventType Clear = new EventType();
  
  protected List<M> all = new ArrayList();
  protected Map<M, Record> recordMap = new HashMap();
  protected List<M> filtered;
  protected List<Record> modified = new ArrayList();
  protected SortInfo sortInfo = new SortInfo();
  
  protected StoreSorter<M> storeSorter;
  protected String filterProperty;
  protected String filterBeginsWith;
  protected boolean filtersEnabled;
  protected List<M> snapshot;
  protected List<StoreFilter<M>> filters;
  private ModelComparer<M> comparer;
  private ChangeListener changeListener;
  private boolean monitorChanges;
  private ModelKeyProvider<M> keyProvider;
  
  public Store()
  {
    this.comparer = DefaultModelComparer.DEFAULT;
  }
  




  public void addFilter(StoreFilter<M> filter)
  {
    if (this.filters == null) {
      this.filters = new ArrayList();
    }
    if (!this.filters.contains(filter)) {
      this.filters.add(filter);
    }
  }
  




  public void addStoreListener(StoreListener<M> listener)
  {
    addListener(Filter, listener);
    addListener(Sort, listener);
    addListener(BeforeDataChanged, listener);
    addListener(DataChanged, listener);
    addListener(Add, listener);
    addListener(Remove, listener);
    addListener(Update, listener);
    addListener(Clear, listener);
  }
  




  public void applyFilters(String property)
  {
    this.filterProperty = property;
    if (!this.filtersEnabled) {
      this.snapshot = this.all;
    }
    
    this.filtersEnabled = true;
    this.filtered = new ArrayList();
    for (M items : this.snapshot) {
      if (!isFiltered(items, property)) {
        this.filtered.add(items);
      }
    }
    this.all = this.filtered;
    
    if (this.storeSorter != null) {
      applySort(false);
    }
    
    fireEvent(Filter, createStoreEvent());
  }
  


  public void clearFilters()
  {
    if (isFiltered()) {
      this.filtersEnabled = false;
      this.all = this.snapshot;
      this.snapshot = null;
      fireEvent(Filter, createStoreEvent());
    }
  }
  




  public void commitChanges()
  {
    List<Record> mod = new ArrayList(this.modified);
    for (Record r : mod) {
      r.commit(false);
    }
    this.modified = new ArrayList();
  }
  





  public boolean contains(M item)
  {
    return findModel(item) != null;
  }
  






  public boolean equals(M model1, M model2)
  {
    return this.comparer.equals(model1, model2);
  }
  




  public void filter(String property)
  {
    filter(property, null);
  }
  





  public void filter(String property, String beginsWith)
  {
    this.filterProperty = property;
    this.filterBeginsWith = beginsWith;
    applyFilters(property);
  }
  






  public M findModel(M model)
  {
    for (M m : this.all) {
      if (this.comparer.equals(m, model)) {
        return m;
      }
    }
    return null;
  }
  
  public M findModel(String key) {
    if (this.keyProvider != null) {
      int i = 0; for (int len = this.all.size(); i < len; i++) {
        String id = this.keyProvider.getKey((ModelData)this.all.get(i));
        if (key.equals(id)) {
          return (ModelData)this.all.get(i);
        }
      }
    }
    return null;
  }
  






  public M findModel(String property, Object value)
  {
    for (M m : this.all) {
      Object val = m.get(property);
      if ((val == value) || ((val != null) && (val.equals(value)))) {
        return m;
      }
    }
    return null;
  }
  







  public List<M> findModels(String property, Object value)
  {
    List<M> models = new ArrayList();
    for (M m : this.all) {
      Object val = m.get(property);
      if ((val == value) || ((val != null) && (val.equals(value)))) {
        models.add(m);
      }
    }
    return models;
  }
  




  public List<StoreFilter<M>> getFilters()
  {
    return this.filters;
  }
  




  public ModelKeyProvider<M> getKeyProvider()
  {
    return this.keyProvider;
  }
  




  public ModelComparer<M> getModelComparer()
  {
    return this.comparer;
  }
  




  public List<M> getModels()
  {
    return new ArrayList(this.all);
  }
  




  public List<M> getAllModels()
  {
    if (this.filtersEnabled) {
      return new ArrayList(this.snapshot);
    }
    return new ArrayList(this.all);
  }
  






  public List<Record> getModifiedRecords()
  {
    return new ArrayList(this.modified);
  }
  






  public Record getRecord(M model)
  {
    assert (model != null) : "Model my not be null";
    Record record = (Record)this.recordMap.get(model);
    if (record == null) {
      record = new Record(model);
      record.join(this);
      this.recordMap.put(model, record);
    }
    return record;
  }
  




  public StoreSorter<M> getStoreSorter()
  {
    return this.storeSorter;
  }
  





  public boolean hasRecord(M model)
  {
    return this.recordMap.containsKey(model);
  }
  




  public boolean isFiltered()
  {
    return this.filtersEnabled;
  }
  




  public boolean isMonitorChanges()
  {
    return this.monitorChanges;
  }
  


  public void rejectChanges()
  {
    for (Record r : new ArrayList(this.modified)) {
      r.reject(false);
    }
    this.modified.clear();
  }
  


  public void removeAll()
  {
    StoreEvent<M> event = createStoreEvent();
    if (fireEvent(BeforeClear, event)) {
      for (M m : this.all) {
        unregisterModel(m);
      }
      this.all.clear();
      this.modified.clear();
      this.recordMap.clear();
      if (this.snapshot != null) {
        this.snapshot.clear();
      }
      fireEvent(Clear, event);
    }
  }
  




  public void removeFilter(StoreFilter<M> filter)
  {
    if (this.filters != null) {
      this.filters.remove(filter);
    }
    if ((this.filters != null) && (this.filters.size() == 0) && (this.filtersEnabled)) {
      clearFilters();
    } else if (this.filtersEnabled) {
      applyFilters(this.filterProperty);
    }
  }
  




  public void removeStoreListener(StoreListener<M> listener)
  {
    removeListener(Sort, listener);
    removeListener(Filter, listener);
    removeListener(BeforeDataChanged, listener);
    removeListener(DataChanged, listener);
    removeListener(Add, listener);
    removeListener(Remove, listener);
    removeListener(Update, listener);
    removeListener(Clear, listener);
  }
  





  public void setKeyProvider(ModelKeyProvider<M> keyProvider)
  {
    this.keyProvider = keyProvider;
  }
  




  public void setModelComparer(ModelComparer<M> comparer)
  {
    this.comparer = comparer;
  }
  







  public void setMonitorChanges(boolean monitorChanges)
  {
    if (this.changeListener == null) {
      this.changeListener = new ChangeListener()
      {
        public void modelChanged(ChangeEvent event) {
          Store.this.onModelChange(event);
        }
      };
    }
    
    this.monitorChanges = monitorChanges;
  }
  




  public void setStoreSorter(StoreSorter<M> storeSorter)
  {
    this.storeSorter = storeSorter;
  }
  





  public void update(M model)
  {
    M m = findModel(model);
    if (m != null) {
      if (m != model) {
        swapModelInstance(m, model);
      }
      StoreEvent<M> evt = createStoreEvent();
      evt.setModel(model);
      fireEvent(Update, evt);
    }
  }
  
  protected void afterCommit(Record record) {
    this.modified.remove(record);
    fireStoreEvent(Update, Record.RecordUpdate.COMMIT, record);
  }
  
  protected void afterEdit(Record record) {
    if (record.isDirty()) {
      if (!this.modified.contains(record)) {
        this.modified.add(record);
      }
    } else {
      this.modified.remove(record);
    }
    fireStoreEvent(Update, Record.RecordUpdate.EDIT, record);
  }
  
  protected void afterReject(Record record) {
    this.modified.remove(record);
    fireStoreEvent(Update, Record.RecordUpdate.REJECT, record);
  }
  

  protected void applySort(boolean supressEvent) {}
  
  protected StoreEvent<M> createStoreEvent()
  {
    return new StoreEvent(this);
  }
  
  protected void fireStoreEvent(EventType type, Record.RecordUpdate operation, Record record)
  {
    StoreEvent<M> evt = createStoreEvent();
    evt.setOperation(operation);
    evt.setRecord(record);
    evt.setModel(record.getModel());
    fireEvent(type, evt);
  }
  
  protected boolean isFiltered(ModelData record, String property)
  {
    if ((this.filterBeginsWith != null) && (property != null)) {
      Object o = record.get(property);
      if ((o != null) && 
        (!o.toString().toLowerCase().startsWith(this.filterBeginsWith.toLowerCase()))) {
        return true;
      }
    }
    
    if (this.filters != null) {
      for (StoreFilter filter : this.filters) {
        boolean result = filter.select(this, record, record, property);
        if (!result) {
          return true;
        }
      }
    }
    return false;
  }
  
  protected void onModelChange(ChangeEvent ce)
  {
    if (ce.getType() == 40)
    {
      M m = ce.getSource();
      boolean rec = hasRecord(m);
      if ((!rec) || ((rec) && (!getRecord(m).isEditing()))) {
        update(ce.getSource());
      }
    }
  }
  




  protected void registerModel(M model)
  {
    if ((this.monitorChanges) && ((model instanceof ChangeEventSource))) {
      ((ChangeEventSource)model).addChangeListener(new ChangeListener[] { this.changeListener });
    }
  }
  
  protected void swapModelInstance(M oldModel, M newModel) {
    M oldM = findModel(oldModel);
    int index = this.all.indexOf(oldM);
    if (index != -1) {
      this.all.remove(oldM);
      this.all.add(index, newModel);
      unregisterModel(oldM);
      registerModel(newModel);
    }
    if (isFiltered()) {
      index = this.snapshot.indexOf(oldM);
      if (index != -1) {
        this.snapshot.remove(oldM);
        this.snapshot.add(index, newModel);
      }
    }
  }
  




  protected void unregisterModel(M model)
  {
    if ((this.monitorChanges) && ((model instanceof ChangeEventSource))) {
      ((ChangeEventSource)model).removeChangeListener(new ChangeListener[] { this.changeListener });
    }
    this.recordMap.remove(model);
  }
}
