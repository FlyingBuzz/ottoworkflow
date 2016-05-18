package com.ottocap.NewWorkFlow.client.DataHolder.WorkOrderInfo;

import com.extjs.gxt.ui.client.binding.Bindings;
import com.extjs.gxt.ui.client.binding.FieldBinding;
import com.extjs.gxt.ui.client.widget.DatePicker;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.ottocap.NewWorkFlow.client.NewWorkFlow;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataVendorInformation;
import com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBox;
import com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBoxModelData;

public class OverseasVendorField
{
  private TextField<String> purchaseordernumber = new TextField();
  private DateField dig_processdate = new DateField();
  private DateField dig_duedate = new DateField();
  private DateField purchaseorder_processdate = new DateField();
  private DateField purchaseorder_duedate = new DateField();
  private OtherComboBox vendorshippingmethod = new OtherComboBox();
  private OrderDataVendorInformation vendorinfodata;
  
  public OverseasVendorField(OrderData myorderdata, OtherComboBoxModelData vendordata)
  {
    this.dig_processdate.getDatePicker().setStartDay(7);
    this.dig_duedate.getDatePicker().setStartDay(7);
    this.purchaseorder_processdate.getDatePicker().setStartDay(7);
    this.purchaseorder_duedate.getDatePicker().setStartDay(7);
    
    this.vendorshippingmethod.setStore(NewWorkFlow.get().getDataStores().getVendorShippingStore());
    
    if (myorderdata.getVendorInformation().getOverseasVendor().containsKey(vendordata.getValue())) {
      this.vendorinfodata = ((OrderDataVendorInformation)myorderdata.getVendorInformation().getOverseasVendor().get(vendordata.getValue()));
    } else {
      this.vendorinfodata = new OrderDataVendorInformation();
      myorderdata.getVendorInformation().getOverseasVendor().put(vendordata.getValue(), this.vendorinfodata);
      this.vendorinfodata.setVendor(Integer.valueOf(vendordata.getValue()));
      


      this.vendorinfodata.setShippingMethod(NewWorkFlow.get().getDataStores().getOverseasVendorDefaultShipping(vendordata.getValue()));
    }
    

    setLabels();
    setBindings();
  }
  

  private void setLabels()
  {
    this.purchaseordernumber.setFieldLabel("Purchase Order Number");
    this.dig_processdate.setFieldLabel("Digitizing Process Date");
    this.dig_duedate.setFieldLabel("Digitizing Due Date");
    this.purchaseorder_processdate.setFieldLabel("Purchase Order Process Date");
    this.purchaseorder_duedate.setFieldLabel("Purchase Order Due Date");
    this.vendorshippingmethod.setFieldLabel("Vendor Shipping Method");
    this.vendorshippingmethod.setForceSelection(true);
  }
  
  private void setBindings() {
    Bindings allbindings = new Bindings();
    allbindings.bind(this.vendorinfodata);
    allbindings.addFieldBinding(new FieldBinding(this.purchaseordernumber, "workordernumber"));
    allbindings.addFieldBinding(new FieldBinding(this.dig_processdate, "digitizingprocessingdate"));
    allbindings.addFieldBinding(new FieldBinding(this.dig_duedate, "digitizingduedate"));
    allbindings.addFieldBinding(new FieldBinding(this.purchaseorder_processdate, "workorderprocessingdate"));
    allbindings.addFieldBinding(new FieldBinding(this.purchaseorder_duedate, "workorderduedate"));
    allbindings.addFieldBinding(new com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBoxFieldBinding(this.vendorshippingmethod, "shippingmethod"));
  }
  
  public TextField<String> getPurchaseOrderNumberField() {
    return this.purchaseordernumber;
  }
  
  public DateField getDigitizingProcessDateField() {
    return this.dig_processdate;
  }
  
  public DateField getDigitizingDueDateField() {
    return this.dig_duedate;
  }
  
  public DateField getPurchaseOrderProcessDateField() {
    return this.purchaseorder_processdate;
  }
  
  public DateField getPurchaseOrderDueDateField() {
    return this.purchaseorder_duedate;
  }
  
  public OtherComboBox getVendorShippingMethodField() {
    return this.vendorshippingmethod;
  }
}
