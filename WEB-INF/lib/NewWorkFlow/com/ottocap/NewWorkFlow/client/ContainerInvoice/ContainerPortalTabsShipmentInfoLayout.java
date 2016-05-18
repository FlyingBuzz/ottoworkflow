package com.ottocap.NewWorkFlow.client.ContainerInvoice;

import com.extjs.gxt.ui.client.binding.Bindings;
import com.extjs.gxt.ui.client.binding.FieldBinding;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.widget.DatePicker;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.user.client.Element;
import com.ottocap.NewWorkFlow.client.ContainerInvoice.ContainerData.ContainerDataContainer;
import com.ottocap.NewWorkFlow.client.DataHolder.Stores.DataStores;
import com.ottocap.NewWorkFlow.client.NewWorkFlow;
import com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBox;
import com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBoxFieldBinding;
import com.ottocap.NewWorkFlow.client.Widget.FormHorizontalPanel2;

public class ContainerPortalTabsShipmentInfoLayout extends LayoutContainer
{
  private ContainerDataContainer containerdatacontainer;
  private ContainerPortalTabs containerportaltabs;
  private TextField<String> containernumber;
  private TextField<String> billoflandingnumber;
  private TextField<String> vesselname;
  private TextField<String> voyagenumber;
  private OtherComboBox portoforigin;
  private DateField exitfactorydate;
  private DateField estimatedtimedeparturedate;
  private DateField estimatedtimearrivaldate;
  private DateField tenplustworeceiveddate;
  private OtherComboBox freightforwarder;
  
  public ContainerPortalTabsShipmentInfoLayout(ContainerDataContainer containerdatacontainer, ContainerPortalTabs containerportaltabs)
  {
    this.containerdatacontainer = containerdatacontainer;
    this.containerportaltabs = containerportaltabs;
  }
  
  protected void onRender(Element parent, int index) {
    super.onRender(parent, index);
    RunMethod();
  }
  
  public void RunMethod() {
    this.containernumber = new TextField();
    this.billoflandingnumber = new TextField();
    this.vesselname = new TextField();
    this.voyagenumber = new TextField();
    this.portoforigin = new OtherComboBox();
    this.exitfactorydate = new DateField();
    this.estimatedtimedeparturedate = new DateField();
    this.estimatedtimearrivaldate = new DateField();
    this.tenplustworeceiveddate = new DateField();
    this.freightforwarder = new OtherComboBox();
    
    this.exitfactorydate.getDatePicker().setStartDay(7);
    this.estimatedtimedeparturedate.getDatePicker().setStartDay(7);
    this.estimatedtimearrivaldate.getDatePicker().setStartDay(7);
    this.tenplustworeceiveddate.getDatePicker().setStartDay(7);
    
    this.portoforigin.setEmptyText("Choose One:");
    this.portoforigin.setForceSelection(true);
    this.portoforigin.setStore(NewWorkFlow.get().getDataStores().getContainerPortofOriginStore());
    
    this.freightforwarder.setEmptyText("Choose One:");
    this.freightforwarder.setStore(NewWorkFlow.get().getDataStores().getContainerFreightForwarderStore());
    
    setLabels();
    setBindings();
    setFields();
    setValidators();
    addListeners();
    
    FormHorizontalPanel2 orderhorizontalpanel1 = new FormHorizontalPanel2();
    FormHorizontalPanel2 orderhorizontalpanel2 = new FormHorizontalPanel2();
    FormHorizontalPanel2 orderhorizontalpanel3 = new FormHorizontalPanel2();
    FormHorizontalPanel2 orderhorizontalpanel4 = new FormHorizontalPanel2();
    
    orderhorizontalpanel1.add(this.containernumber);
    orderhorizontalpanel1.add(this.billoflandingnumber);
    
    orderhorizontalpanel2.add(this.vesselname);
    orderhorizontalpanel2.add(this.voyagenumber);
    orderhorizontalpanel2.add(this.portoforigin);
    
    orderhorizontalpanel3.add(this.exitfactorydate);
    orderhorizontalpanel3.add(this.estimatedtimedeparturedate);
    orderhorizontalpanel3.add(this.estimatedtimearrivaldate);
    orderhorizontalpanel3.add(this.tenplustworeceiveddate);
    
    orderhorizontalpanel4.add(this.freightforwarder);
    
    add(orderhorizontalpanel1);
    add(orderhorizontalpanel2);
    add(orderhorizontalpanel3);
    add(orderhorizontalpanel4);
  }
  
  private void setLabels()
  {
    this.containernumber.setFieldLabel("Container #");
    this.billoflandingnumber.setFieldLabel("B/L #");
    this.vesselname.setFieldLabel("Vessel Name");
    this.voyagenumber.setFieldLabel("Voyage Number");
    this.portoforigin.setFieldLabel("Port Of Origion");
    this.exitfactorydate.setFieldLabel("Ex-Fcty Date");
    this.estimatedtimedeparturedate.setFieldLabel("ETD Date");
    this.estimatedtimearrivaldate.setFieldLabel("ETA Date");
    this.tenplustworeceiveddate.setFieldLabel("10+2 Rec'd");
    this.freightforwarder.setFieldLabel("Fright Forwarder");
  }
  

  private void setValidators() {}
  
  private void setBindings()
  {
    Bindings allbindings = new Bindings();
    allbindings.bind(this.containerdatacontainer);
    allbindings.addFieldBinding(new FieldBinding(this.billoflandingnumber, "billoflandingnumber"));
    allbindings.addFieldBinding(new FieldBinding(this.vesselname, "vesselname"));
    allbindings.addFieldBinding(new FieldBinding(this.voyagenumber, "voyagenumber"));
    allbindings.addFieldBinding(new OtherComboBoxFieldBinding(this.portoforigin, "portoforigin"));
    allbindings.addFieldBinding(new FieldBinding(this.exitfactorydate, "exitfactorydate"));
    allbindings.addFieldBinding(new FieldBinding(this.estimatedtimedeparturedate, "estimatedtimedeparturedate"));
    allbindings.addFieldBinding(new FieldBinding(this.estimatedtimearrivaldate, "estimatedtimearrivaldate"));
    allbindings.addFieldBinding(new FieldBinding(this.tenplustworeceiveddate, "tenplustworeceiveddate"));
    allbindings.addFieldBinding(new OtherComboBoxFieldBinding(this.freightforwarder, "freightforwarder"));
  }
  
  private void setFields() {}
  
  private void addListeners()
  {
    this.containernumber.addListener(com.extjs.gxt.ui.client.event.Events.Change, this.changelistener);
  }
  
  private com.extjs.gxt.ui.client.event.Listener<BaseEvent> changelistener = new com.extjs.gxt.ui.client.event.Listener()
  {
    public void handleEvent(BaseEvent be)
    {
      if (be.getSource().equals(ContainerPortalTabsShipmentInfoLayout.this.containernumber)) {
        ContainerPortalTabsShipmentInfoLayout.this.containerdatacontainer.setContainerNumber((String)ContainerPortalTabsShipmentInfoLayout.this.containernumber.getValue());
        ContainerPortalTabsShipmentInfoLayout.this.getContainerPortalTabs().getContainerPortal().getContainerInvoiceTab().fixContainerTitle();
      }
    }
  };
  
  public ContainerPortalTabs getContainerPortalTabs()
  {
    return this.containerportaltabs;
  }
}
