package com.ottocap.NewWorkFlow.client.MainScreen;

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FormEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FileUploadField;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.ottocap.NewWorkFlow.client.DataHolder.Stores.DataStores;
import com.ottocap.NewWorkFlow.client.EditOrder.OrderTab;
import com.ottocap.NewWorkFlow.client.Icons.ResourceIcons;
import com.ottocap.NewWorkFlow.client.Icons.Resources;
import com.ottocap.NewWorkFlow.client.MainTabPanel;
import com.ottocap.NewWorkFlow.client.NewWorkFlow;
import com.ottocap.NewWorkFlow.client.OrderDataWithStyle;
import com.ottocap.NewWorkFlow.client.Services.WorkFlowServiceAsync;
import com.ottocap.NewWorkFlow.client.Widget.BuzzAsyncCallback;
import com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBox;
import com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBoxModelData;
import java.util.ArrayList;

public class ImportOrder extends Window
{
  private Button savebutton = new Button("Save", Resources.ICONS.disk());
  private Button cancelbutton = new Button("Cancel");
  private OtherComboBox employeelist = new OtherComboBox();
  private String currentemployee = "";
  private FormPanel myform = new FormPanel();
  private HiddenField<String> employeeid = new HiddenField();
  private int thehiddenkey;
  
  public ImportOrder()
  {
    NewWorkFlow.get().getWorkFlowRPC().getCurrentEmployee(this.employeenamecallback);
    this.savebutton.addListener(Events.OnClick, this.clicklistener);
    this.cancelbutton.addListener(Events.OnClick, this.clicklistener);
    this.employeelist.setFieldLabel("Employee");
    this.employeelist.setName("employeename");
    this.employeelist.setAllowBlank(false);
    this.employeelist.setForceSelection(true);
    this.employeelist.setTemplate(getEmployeeComoboBoxTemplate());
    this.employeelist.setItemSelector("div.search-item");
    this.employeelist.addListener(Events.Change, this.changelistener);
    
    this.myform.setHeaderVisible(false);
    this.myform.setBodyBorder(false);
    this.myform.setFrame(false);
    this.myform.setPadding(0);
    this.myform.setMethod(com.extjs.gxt.ui.client.widget.form.FormPanel.Method.POST);
    this.myform.setEncoding(com.extjs.gxt.ui.client.widget.form.FormPanel.Encoding.MULTIPART);
    this.myform.setAction(NewWorkFlow.baseurl + "uploader");
    this.myform.addListener(Events.Submit, this.submitlistener);
    this.myform.setLabelAlign(com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign.TOP);
    
    setFocusWidget(this.employeelist);
    setHeadingHtml("Import Data");
    this.myform.addButton(this.savebutton);
    this.myform.addButton(this.cancelbutton);
    this.myform.add(this.employeelist);
    
    FileUploadField companylogofile = new FileUploadField();
    companylogofile.setFieldLabel("OrderData");
    companylogofile.setName("orderdatafile");
    companylogofile.setButtonIcon(Resources.ICONS.folder_horizontal_open());
    
    this.myform.add(companylogofile);
    
    HiddenField<String> uploadtype = new HiddenField();
    uploadtype.setName("uploadtype");
    uploadtype.setValue("Import Order");
    this.myform.add(uploadtype);
    
    this.employeeid.setName("employeeid");
    this.myform.add(this.employeeid);
    
    add(this.myform);
  }
  

  private Listener<BaseEvent> changelistener = new Listener()
  {
    public void handleEvent(BaseEvent be)
    {
      if (be.getSource().equals(ImportOrder.this.employeelist)) {
        ImportOrder.this.employeeid.setValue(ImportOrder.this.employeelist.getValue() == null ? "" : ImportOrder.this.employeelist.getValue().getValue());
      }
    }
  };
  


  private Listener<FormEvent> submitlistener = new Listener()
  {
    public void handleEvent(FormEvent be)
    {
      if (be.getComponent().equals(ImportOrder.this.myform)) {
        String htmlstring = com.ottocap.NewWorkFlow.client.Widget.StringUtils.unescapeHTML(be.getResultHtml(), 0);
        htmlstring = htmlstring.replace("<pre>", "");
        htmlstring = htmlstring.replace("</pre>", "");
        JSONObject jsonObject = JSONParser.parseStrict(htmlstring).isObject();
        if (!jsonObject.containsKey("error")) {
          ImportOrder.this.hide();
          ImportOrder.this.thehiddenkey = ((int)jsonObject.get("hiddenkey").isNumber().doubleValue());
          com.extjs.gxt.ui.client.widget.Info.display("Success", "New Order Key is " + ImportOrder.this.thehiddenkey);
          
          NewWorkFlow.get().getWait().show();
          NewWorkFlow.get().getWait().getDialog().center();
          NewWorkFlow.get().getWorkFlowRPC().getOrderWithStyle(ImportOrder.this.thehiddenkey, ImportOrder.this.getordercallback);
        }
        else {
          Throwable myerror = new Throwable(jsonObject.get("error").isString().stringValue());
          NewWorkFlow.get().reLogin2(myerror, null);
        }
      }
    }
  };
  

  BuzzAsyncCallback<OrderDataWithStyle> getordercallback = new BuzzAsyncCallback()
  {
    public void onFailure(Throwable caught)
    {
      NewWorkFlow.get().reLogin2(caught, ImportOrder.this.getordercallback);
    }
    

    public void doRetry()
    {
      NewWorkFlow.get().getWorkFlowRPC().getOrderWithStyle(ImportOrder.this.thehiddenkey, ImportOrder.this.getordercallback);
    }
    
    public void onSuccess(OrderDataWithStyle result)
    {
      for (com.ottocap.NewWorkFlow.client.StyleInformationData currentstyle : result.getStyleInfoArray()) {
        NewWorkFlow.get().setStyleStore(currentstyle);
      }
      OrderTab mytab = new OrderTab(result.getOrderData());
      NewWorkFlow.get().getMainTabPanel().add(mytab);
      NewWorkFlow.get().getMainTabPanel().setSelection(mytab);
      NewWorkFlow.get().getWait().close();
    }
  };
  

  private AsyncCallback<String> employeenamecallback = new AsyncCallback()
  {
    public void onFailure(Throwable caught)
    {
      if (NewWorkFlow.get().getDataStores().getEmployeeList() == null) {
        NewWorkFlow.get().getWorkFlowRPC().getEmployeeList(ImportOrder.this.employeelistcallback);
      } else {
        ImportOrder.this.setEmployeeAndShow();
      }
    }
    
    public void onSuccess(String result)
    {
      ImportOrder.this.currentemployee = result;
      if (NewWorkFlow.get().getDataStores().getEmployeeList() == null) {
        NewWorkFlow.get().getWorkFlowRPC().getEmployeeList(ImportOrder.this.employeelistcallback);
      } else {
        ImportOrder.this.setEmployeeAndShow();
      }
    }
  };
  

  private Listener<BaseEvent> clicklistener = new Listener()
  {
    public void handleEvent(BaseEvent be)
    {
      if (be.getSource().equals(ImportOrder.this.cancelbutton)) {
        ImportOrder.this.hide();
      } else if (be.getSource().equals(ImportOrder.this.savebutton)) {
        ImportOrder.this.myform.submit();
      }
    }
  };
  











  private BuzzAsyncCallback<ArrayList<OtherComboBoxModelData>> employeelistcallback = new BuzzAsyncCallback()
  {
    public void onFailure(Throwable caught)
    {
      NewWorkFlow.get().reLogin2(caught, ImportOrder.this.employeelistcallback);
    }
    
    public void doRetry()
    {
      NewWorkFlow.get().getWorkFlowRPC().getEmployeeList(ImportOrder.this.employeelistcallback);
    }
    
    public void onSuccess(ArrayList<OtherComboBoxModelData> result)
    {
      ListStore<OtherComboBoxModelData> mystore = new ListStore();
      mystore.add(result);
      NewWorkFlow.get().getDataStores().setEmployeeList(mystore);
      ImportOrder.this.setEmployeeAndShow();
    }
  };
  
  private native String getEmployeeComoboBoxTemplate();
  
  private void setEmployeeAndShow() { this.employeelist.setStore(NewWorkFlow.get().getDataStores().getEmployeeList());
    this.employeelist.setValue(this.currentemployee);
    this.employeeid.setValue(this.currentemployee);
    show();
  }
}
