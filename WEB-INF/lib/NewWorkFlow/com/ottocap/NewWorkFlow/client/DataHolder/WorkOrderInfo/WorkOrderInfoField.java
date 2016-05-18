package com.ottocap.NewWorkFlow.client.DataHolder.WorkOrderInfo;

import com.extjs.gxt.ui.client.binding.Bindings;
import com.extjs.gxt.ui.client.binding.FieldBinding;
import com.extjs.gxt.ui.client.widget.DatePicker;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.ottocap.NewWorkFlow.client.NewWorkFlow;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataVendors;
import com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBox;

public class WorkOrderInfoField
{
  private OrderData myorderdata;
  private com.extjs.gxt.ui.client.widget.form.TextField<String> workordernumber = new com.extjs.gxt.ui.client.widget.form.TextField();
  private OtherComboBox digvendor_combo = new OtherComboBox();
  private OtherComboBox embvendor_combo = new OtherComboBox();
  private OtherComboBox heatvendor_combo = new OtherComboBox();
  private OtherComboBox patchvendor_combo = new OtherComboBox();
  private OtherComboBox cadvendor_combo = new OtherComboBox();
  private OtherComboBox screenvendor_combo = new OtherComboBox();
  private OtherComboBox dtgvendor_combo = new OtherComboBox();
  private DateField digvendor_processdate = new DateField();
  private DateField embvendor_processdate = new DateField();
  private DateField patchvendor_processdate = new DateField();
  private DateField heatvendor_processdate = new DateField();
  private DateField cadvendor_processdate = new DateField();
  private DateField screenvendor_processdate = new DateField();
  private DateField dtgvendor_processdate = new DateField();
  private DateField digvendor_duedate = new DateField();
  private DateField embvendor_duedate = new DateField();
  private DateField patchvendor_duedate = new DateField();
  private DateField heatvendor_duedate = new DateField();
  private DateField cadvendor_duedate = new DateField();
  private DateField screenvendor_duedate = new DateField();
  private DateField dtgvendor_duedate = new DateField();
  
  public WorkOrderInfoField(OrderData myorderdata) {
    this.myorderdata = myorderdata;
    
    this.digvendor_processdate.getDatePicker().setStartDay(7);
    this.embvendor_processdate.getDatePicker().setStartDay(7);
    this.patchvendor_processdate.getDatePicker().setStartDay(7);
    this.heatvendor_processdate.getDatePicker().setStartDay(7);
    this.cadvendor_processdate.getDatePicker().setStartDay(7);
    this.screenvendor_processdate.getDatePicker().setStartDay(7);
    this.dtgvendor_processdate.getDatePicker().setStartDay(7);
    this.digvendor_duedate.getDatePicker().setStartDay(7);
    this.embvendor_duedate.getDatePicker().setStartDay(7);
    this.patchvendor_duedate.getDatePicker().setStartDay(7);
    this.heatvendor_duedate.getDatePicker().setStartDay(7);
    this.cadvendor_duedate.getDatePicker().setStartDay(7);
    this.screenvendor_duedate.getDatePicker().setStartDay(7);
    this.dtgvendor_duedate.getDatePicker().setStartDay(7);
    
    this.digvendor_combo.setStore(NewWorkFlow.get().getDataStores().getDomesticVendorStoreCategory("dig"));
    this.embvendor_combo.setStore(NewWorkFlow.get().getDataStores().getDomesticVendorStoreCategory("emb"));
    this.patchvendor_combo.setStore(NewWorkFlow.get().getDataStores().getDomesticVendorStoreCategory("patch"));
    this.heatvendor_combo.setStore(NewWorkFlow.get().getDataStores().getDomesticVendorStoreCategory("heat"));
    this.cadvendor_combo.setStore(NewWorkFlow.get().getDataStores().getDomesticVendorStoreCategory("cadprint"));
    this.screenvendor_combo.setStore(NewWorkFlow.get().getDataStores().getDomesticVendorStoreCategory("screenprint"));
    this.dtgvendor_combo.setStore(NewWorkFlow.get().getDataStores().getDomesticVendorStoreCategory("dtg"));
    
    setLabels();
    setBindings();
    setFields();
  }
  

  private void setLabels()
  {
    this.workordernumber.setFieldLabel("Work Order Number");
    this.digvendor_combo.setFieldLabel("Vendor");
    this.embvendor_combo.setFieldLabel("Vendor");
    this.patchvendor_combo.setFieldLabel("Vendor");
    this.heatvendor_combo.setFieldLabel("Vendor");
    this.cadvendor_combo.setFieldLabel("Vendor");
    this.screenvendor_combo.setFieldLabel("Vendor");
    this.dtgvendor_combo.setFieldLabel("Vendor");
    this.digvendor_processdate.setFieldLabel("Process Date");
    this.embvendor_processdate.setFieldLabel("Process Date");
    this.patchvendor_processdate.setFieldLabel("Process Date");
    this.heatvendor_processdate.setFieldLabel("Process Date");
    this.cadvendor_processdate.setFieldLabel("Process Date");
    this.screenvendor_processdate.setFieldLabel("Process Date");
    this.dtgvendor_processdate.setFieldLabel("Process Date");
    this.digvendor_duedate.setFieldLabel("Due Date");
    this.embvendor_duedate.setFieldLabel("Due Date");
    this.patchvendor_duedate.setFieldLabel("Due Date");
    this.heatvendor_duedate.setFieldLabel("Due Date");
    this.cadvendor_duedate.setFieldLabel("Due Date");
    this.screenvendor_duedate.setFieldLabel("Due Date");
    this.dtgvendor_duedate.setFieldLabel("Due Date");
  }
  

  private void setBindings()
  {
    Bindings digvendorbindings = new Bindings();
    digvendorbindings.bind(this.myorderdata.getVendorInformation().getDigitizerVendor());
    digvendorbindings.addFieldBinding(new FieldBinding(this.workordernumber, "workordernumber"));
    digvendorbindings.addFieldBinding(new FieldBinding(this.digvendor_processdate, "digitizingprocessingdate"));
    digvendorbindings.addFieldBinding(new FieldBinding(this.digvendor_duedate, "digitizingduedate"));
    digvendorbindings.addFieldBinding(new com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBoxFieldBinding(this.digvendor_combo, "vendor"));
    
    Bindings embvendorbindings = new Bindings();
    embvendorbindings.bind(this.myorderdata.getVendorInformation().getEmbroideryVendor());
    embvendorbindings.addFieldBinding(new FieldBinding(this.workordernumber, "workordernumber"));
    embvendorbindings.addFieldBinding(new FieldBinding(this.embvendor_processdate, "workorderprocessingdate"));
    embvendorbindings.addFieldBinding(new FieldBinding(this.embvendor_duedate, "workorderduedate"));
    embvendorbindings.addFieldBinding(new com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBoxFieldBinding(this.embvendor_combo, "vendor"));
    
    Bindings cadvendorbindings = new Bindings();
    cadvendorbindings.bind(this.myorderdata.getVendorInformation().getCADPrintVendor());
    cadvendorbindings.addFieldBinding(new FieldBinding(this.workordernumber, "workordernumber"));
    cadvendorbindings.addFieldBinding(new FieldBinding(this.cadvendor_processdate, "workorderprocessingdate"));
    cadvendorbindings.addFieldBinding(new FieldBinding(this.cadvendor_duedate, "workorderduedate"));
    cadvendorbindings.addFieldBinding(new com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBoxFieldBinding(this.cadvendor_combo, "vendor"));
    
    Bindings screenvendorbindings = new Bindings();
    screenvendorbindings.bind(this.myorderdata.getVendorInformation().getScreenPrintVendor());
    screenvendorbindings.addFieldBinding(new FieldBinding(this.workordernumber, "workordernumber"));
    screenvendorbindings.addFieldBinding(new FieldBinding(this.screenvendor_processdate, "workorderprocessingdate"));
    screenvendorbindings.addFieldBinding(new FieldBinding(this.screenvendor_duedate, "workorderduedate"));
    screenvendorbindings.addFieldBinding(new com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBoxFieldBinding(this.screenvendor_combo, "vendor"));
    
    Bindings heatvendorbindings = new Bindings();
    heatvendorbindings.bind(this.myorderdata.getVendorInformation().getHeatTransferVendor());
    heatvendorbindings.addFieldBinding(new FieldBinding(this.workordernumber, "workordernumber"));
    heatvendorbindings.addFieldBinding(new FieldBinding(this.heatvendor_processdate, "workorderprocessingdate"));
    heatvendorbindings.addFieldBinding(new FieldBinding(this.heatvendor_duedate, "workorderduedate"));
    heatvendorbindings.addFieldBinding(new com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBoxFieldBinding(this.heatvendor_combo, "vendor"));
    
    Bindings dtgvendorbindings = new Bindings();
    dtgvendorbindings.bind(this.myorderdata.getVendorInformation().getDirectToGarmentVendor());
    dtgvendorbindings.addFieldBinding(new FieldBinding(this.workordernumber, "workordernumber"));
    dtgvendorbindings.addFieldBinding(new FieldBinding(this.dtgvendor_processdate, "workorderprocessingdate"));
    dtgvendorbindings.addFieldBinding(new FieldBinding(this.dtgvendor_duedate, "workorderduedate"));
    dtgvendorbindings.addFieldBinding(new com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBoxFieldBinding(this.dtgvendor_combo, "vendor"));
    
    Bindings patchbindings = new Bindings();
    patchbindings.bind(this.myorderdata.getVendorInformation().getPatchVendor());
    patchbindings.addFieldBinding(new FieldBinding(this.workordernumber, "workordernumber"));
    patchbindings.addFieldBinding(new FieldBinding(this.patchvendor_processdate, "workorderprocessingdate"));
    patchbindings.addFieldBinding(new FieldBinding(this.patchvendor_duedate, "workorderduedate"));
    patchbindings.addFieldBinding(new com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBoxFieldBinding(this.patchvendor_combo, "vendor"));
  }
  
  private void setFields()
  {
    this.digvendor_combo.setForceSelection(true);
    this.embvendor_combo.setForceSelection(true);
    this.heatvendor_combo.setForceSelection(true);
    this.cadvendor_combo.setForceSelection(true);
    this.screenvendor_combo.setForceSelection(true);
    this.dtgvendor_combo.setForceSelection(true);
    this.patchvendor_combo.setForceSelection(true);
  }
  
  public com.extjs.gxt.ui.client.widget.form.TextField<String> getWorkOrderNumberField()
  {
    return this.workordernumber;
  }
  
  public OtherComboBox getDigitizingVendorField() {
    return this.digvendor_combo;
  }
  
  public OtherComboBox getEmbroideryVendorField() {
    return this.embvendor_combo;
  }
  
  public OtherComboBox getPatchVendorField() {
    return this.patchvendor_combo;
  }
  
  public OtherComboBox getHeatTransferVendorField() {
    return this.heatvendor_combo;
  }
  
  public OtherComboBox getCADPrintVendorField() {
    return this.cadvendor_combo;
  }
  
  public OtherComboBox getScreenPrintVendorField() {
    return this.screenvendor_combo;
  }
  
  public OtherComboBox getDirectToGarmentVendorField() {
    return this.dtgvendor_combo;
  }
  
  public DateField getDigitizingVendorProcessDateField() {
    return this.digvendor_processdate;
  }
  
  public DateField getEmbroideryVendorProcessDateField() {
    return this.embvendor_processdate;
  }
  
  public DateField getPatchVendorProcessDateField() {
    return this.patchvendor_processdate;
  }
  
  public DateField getHeatTransferVendorProcessDateField() {
    return this.heatvendor_processdate;
  }
  
  public DateField getCADPrintVendorProcessDateField() {
    return this.cadvendor_processdate;
  }
  
  public DateField getScreenPrintVendorProcessDateField() {
    return this.screenvendor_processdate;
  }
  
  public DateField getDirectToGarmentVendorProcessDateField() {
    return this.dtgvendor_processdate;
  }
  
  public DateField getDigitizingVendorDueDateField() {
    return this.digvendor_duedate;
  }
  
  public DateField getEmbroideryVendorDueDateField() {
    return this.embvendor_duedate;
  }
  
  public DateField getPatchVendorDueDateField() {
    return this.patchvendor_duedate;
  }
  
  public DateField getHeatTransferVendorDueDateField() {
    return this.heatvendor_duedate;
  }
  
  public DateField getCADPrintVendorDueDateField() {
    return this.cadvendor_duedate;
  }
  
  public DateField getScreenPrintVendorDueDateField() {
    return this.screenvendor_duedate;
  }
  
  public DateField getDirectToGarmentVendorDueDateField() {
    return this.dtgvendor_duedate;
  }
}
