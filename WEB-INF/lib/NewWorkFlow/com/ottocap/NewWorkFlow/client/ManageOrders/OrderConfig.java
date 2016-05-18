package com.ottocap.NewWorkFlow.client.ManageOrders;

import com.extjs.gxt.ui.client.core.FastMap;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.ottocap.NewWorkFlow.client.DataHolder.Stores.DataStores;
import com.ottocap.NewWorkFlow.client.NewWorkFlow;
import com.ottocap.NewWorkFlow.client.Services.WorkFlowServiceAsync;
import com.ottocap.NewWorkFlow.client.Widget.BuzzAsyncCallback;
import com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBox;
import com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBoxModelData;
import com.ottocap.NewWorkFlow.client.Widget.FormHorizontalPanel2;
import java.util.ArrayList;

public class OrderConfig extends Window
{
  private Button savebutton = new Button("Save", com.ottocap.NewWorkFlow.client.Icons.Resources.ICONS.disk());
  private Button cancelbutton = new Button("Cancel");
  private OtherComboBox employeelist = new OtherComboBox();
  private AsyncCallback<FastMap<Object>> successcallback;
  private int hiddenkey;
  private String oldemployee;
  
  public OrderConfig(int hiddenkey, AsyncCallback<FastMap<Object>> successcallback) {
    this.hiddenkey = hiddenkey;
    this.successcallback = successcallback;
    addButton(this.savebutton);
    addButton(this.cancelbutton);
    
    NewWorkFlow.get().getWorkFlowRPC().getOrderEmployee(hiddenkey, this.employeecallback);
    
    this.savebutton.addListener(Events.OnClick, this.clicklistener);
    this.cancelbutton.addListener(Events.OnClick, this.clicklistener);
    this.employeelist.setFieldLabel("Employee");
    this.employeelist.setAllowBlank(false);
    this.employeelist.setForceSelection(true);
    this.employeelist.setTemplate(getEmployeeComoboBoxTemplate());
    this.employeelist.setItemSelector("div.search-item");
    
    FormHorizontalPanel2 row1 = new FormHorizontalPanel2();
    row1.add(this.employeelist);
    
    setModal(true);
    setHeadingHtml("Configure Key " + hiddenkey);
    add(row1);
    
    setFocusWidget(this.employeelist);
  }
  









  private BuzzAsyncCallback<String> employeecallback = new BuzzAsyncCallback()
  {
    public void onFailure(Throwable caught)
    {
      NewWorkFlow.get().reLogin2(caught, OrderConfig.this.employeecallback);
    }
    
    public void doRetry()
    {
      NewWorkFlow.get().getWorkFlowRPC().getOrderEmployee(OrderConfig.this.hiddenkey, OrderConfig.this.employeecallback);
    }
    
    public void onSuccess(String result)
    {
      OrderConfig.this.oldemployee = result;
      if (NewWorkFlow.get().getDataStores().getEmployeeList() == null) {
        NewWorkFlow.get().getWorkFlowRPC().getEmployeeList(OrderConfig.this.employeelistcallback);
      } else {
        OrderConfig.this.setEmployeeAndShow();
      }
    }
  };
  

  private BuzzAsyncCallback<ArrayList<OtherComboBoxModelData>> employeelistcallback = new BuzzAsyncCallback()
  {
    public void onFailure(Throwable caught)
    {
      NewWorkFlow.get().reLogin2(caught, OrderConfig.this.employeelistcallback);
    }
    
    public void doRetry()
    {
      NewWorkFlow.get().getWorkFlowRPC().getEmployeeList(OrderConfig.this.employeelistcallback);
    }
    
    public void onSuccess(ArrayList<OtherComboBoxModelData> result)
    {
      ListStore<OtherComboBoxModelData> mystore = new ListStore();
      mystore.add(result);
      NewWorkFlow.get().getDataStores().setEmployeeList(mystore);
      OrderConfig.this.setEmployeeAndShow();
    }
  };
  
  private native String getEmployeeComoboBoxTemplate();
  
  private void setEmployeeAndShow() { this.employeelist.setStore(NewWorkFlow.get().getDataStores().getEmployeeList());
    this.employeelist.setValue(this.oldemployee);
    show();
  }
  
  private Listener<BaseEvent> clicklistener = new Listener()
  {
    public void handleEvent(BaseEvent be)
    {
      if (be.getSource().equals(OrderConfig.this.savebutton)) {
        if ((OrderConfig.this.employeelist.validate()) && (OrderConfig.this.employeelist.getValue() != null) && (!OrderConfig.this.employeelist.getValue().getValue().equals(OrderConfig.this.oldemployee))) {
          OrderConfig.this.savebutton.disable();
          NewWorkFlow.get().getWorkFlowRPC().saveOrderEmployee(OrderConfig.this.hiddenkey, OrderConfig.this.employeelist.getValue().getValue(), OrderConfig.this.savecallback);
        } else if ((OrderConfig.this.employeelist.getValue() != null) && (OrderConfig.this.employeelist.getValue().getValue().equals(OrderConfig.this.oldemployee))) {
          OrderConfig.this.hide();
        }
      } else if (be.getSource().equals(OrderConfig.this.cancelbutton)) {
        OrderConfig.this.hide();
      }
    }
  };
  

  private BuzzAsyncCallback<Void> savecallback = new BuzzAsyncCallback()
  {
    public void onFailure(Throwable caught)
    {
      NewWorkFlow.get().reLogin2(caught, OrderConfig.this.savecallback);
    }
    
    public void doRetry()
    {
      NewWorkFlow.get().getWorkFlowRPC().saveOrderEmployee(OrderConfig.this.hiddenkey, OrderConfig.this.employeelist.getValue().getValue(), OrderConfig.this.savecallback);
    }
    
    public void onSuccess(Void result)
    {
      FastMap<Object> mydata = new FastMap();
      mydata.put("employeeid", OrderConfig.this.employeelist.getValue().getValue());
      mydata.put("managedby", OrderConfig.this.employeelist.getValue().getName());
      mydata.put("hiddenkey", Integer.valueOf(OrderConfig.this.hiddenkey));
      OrderConfig.this.successcallback.onSuccess(mydata);
      OrderConfig.this.hide();
    }
  };
}
