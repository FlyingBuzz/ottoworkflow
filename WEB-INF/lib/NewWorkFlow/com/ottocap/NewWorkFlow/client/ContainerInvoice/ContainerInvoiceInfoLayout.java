package com.ottocap.NewWorkFlow.client.ContainerInvoice;

import com.extjs.gxt.ui.client.binding.Bindings;
import com.extjs.gxt.ui.client.binding.FieldBinding;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.widget.DatePicker;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.google.gwt.user.client.Element;
import com.ottocap.NewWorkFlow.client.ContainerInvoice.ContainerData.ContainerData;
import com.ottocap.NewWorkFlow.client.NewWorkFlow;
import com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBox;
import com.ottocap.NewWorkFlow.client.Widget.FormHorizontalPanel2;
import com.ottocap.NewWorkFlow.client.Widget.USDMoneyField;

public class ContainerInvoiceInfoLayout extends LayoutContainer
{
  private ContainerData mycontainerdata;
  private ContainerInvoiceTab containerinvoicetab;
  private TextField<String> invoicenumber;
  private DateField faxinvoicereceiveddate;
  private DateField originalinvoicereceiveddate;
  private DateField bankdocssettlementdate;
  private USDMoneyField invoicevalue;
  private TextArea purchaseordersshipped;
  private OtherComboBox status;
  
  public ContainerInvoiceInfoLayout(ContainerData mycontainerdata, ContainerInvoiceTab containerinvoicetab)
  {
    this.mycontainerdata = mycontainerdata;
    this.containerinvoicetab = containerinvoicetab;
  }
  
  protected void onRender(Element parent, int index) {
    super.onRender(parent, index);
    RunMethod();
  }
  
  public void RunMethod() {
    this.invoicenumber = new TextField();
    this.faxinvoicereceiveddate = new DateField();
    this.originalinvoicereceiveddate = new DateField();
    this.bankdocssettlementdate = new DateField();
    this.invoicevalue = new USDMoneyField();
    this.purchaseordersshipped = new TextArea();
    this.status = new OtherComboBox();
    
    this.status.setEmptyText("Choose One:");
    this.status.setForceSelection(true);
    this.status.setStore(NewWorkFlow.get().getDataStores().getContainerInvoiceStatusStore());
    
    this.faxinvoicereceiveddate.getDatePicker().setStartDay(7);
    this.originalinvoicereceiveddate.getDatePicker().setStartDay(7);
    this.bankdocssettlementdate.getDatePicker().setStartDay(7);
    
    setLabels();
    setBindings();
    setFields();
    setValidators();
    addListeners();
    
    FormHorizontalPanel2 orderhorizontalpanel1 = new FormHorizontalPanel2();
    FormHorizontalPanel2 orderhorizontalpanel2 = new FormHorizontalPanel2();
    FormHorizontalPanel2 orderhorizontalpanel3 = new FormHorizontalPanel2();
    FormHorizontalPanel2 orderhorizontalpanel4 = new FormHorizontalPanel2();
    FormHorizontalPanel2 orderhorizontalpanel5 = new FormHorizontalPanel2();
    
    orderhorizontalpanel1.add(this.invoicenumber);
    
    orderhorizontalpanel2.add(this.faxinvoicereceiveddate);
    orderhorizontalpanel2.add(this.originalinvoicereceiveddate);
    orderhorizontalpanel2.add(this.bankdocssettlementdate);
    
    orderhorizontalpanel3.add(this.invoicevalue);
    
    this.purchaseordersshipped.setHeight(232);
    orderhorizontalpanel4.add(this.purchaseordersshipped, new FormData(854, -1));
    
    orderhorizontalpanel5.add(this.status, new FormData(418, -1));
    
    add(orderhorizontalpanel1);
    add(orderhorizontalpanel2);
    add(orderhorizontalpanel3);
    add(orderhorizontalpanel4);
    add(orderhorizontalpanel5);
  }
  
  private void setLabels()
  {
    this.invoicenumber.setFieldLabel("Invoice #");
    this.faxinvoicereceiveddate.setFieldLabel("Fax Invoice Rec'd");
    this.originalinvoicereceiveddate.setFieldLabel("Original Invoice Rec'd");
    this.bankdocssettlementdate.setFieldLabel("Bank Docs Settlement Date");
    this.invoicevalue.setFieldLabel("Invoice Value");
    this.purchaseordersshipped.setFieldLabel("Purchase Orders Shipped");
    this.status.setFieldLabel("Status");
  }
  

  private void setValidators() {}
  
  private void setBindings()
  {
    Bindings allbindings = new Bindings();
    allbindings.bind(this.mycontainerdata);
    allbindings.addFieldBinding(new FieldBinding(this.faxinvoicereceiveddate, "faxinvoicereceiveddate"));
    allbindings.addFieldBinding(new FieldBinding(this.originalinvoicereceiveddate, "originalinvoicereceiveddate"));
    allbindings.addFieldBinding(new FieldBinding(this.bankdocssettlementdate, "bankdocssettlementdate"));
    allbindings.addFieldBinding(new FieldBinding(this.invoicevalue, "invoicevalue"));
    allbindings.addFieldBinding(new com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBoxFieldBinding(this.status, "status"));
  }
  
  private void setFields() {}
  
  private void addListeners()
  {
    this.invoicenumber.addListener(Events.Change, this.changelistener);
    this.purchaseordersshipped.addListener(Events.Change, this.changelistener);
  }
  
  private com.extjs.gxt.ui.client.event.Listener<BaseEvent> changelistener = new com.extjs.gxt.ui.client.event.Listener()
  {
    public void handleEvent(BaseEvent be)
    {
      if (be.getSource().equals(ContainerInvoiceInfoLayout.this.invoicenumber)) {
        ContainerInvoiceInfoLayout.this.mycontainerdata.setInvoiceNumber((String)ContainerInvoiceInfoLayout.this.invoicenumber.getValue());
        ContainerInvoiceInfoLayout.this.getContainerInvoiceTab().setTabHeader();
      } else if (be.getSource().equals(ContainerInvoiceInfoLayout.this.purchaseordersshipped)) {
        ContainerInvoiceInfoLayout.this.purchaseordersshipped.setValue(((String)ContainerInvoiceInfoLayout.this.purchaseordersshipped.getValue()).toUpperCase());
        ContainerInvoiceInfoLayout.this.mycontainerdata.setPurchaseOrdersShipped((String)ContainerInvoiceInfoLayout.this.purchaseordersshipped.getValue());
      }
    }
  };
  
  public ContainerInvoiceTab getContainerInvoiceTab()
  {
    return this.containerinvoicetab;
  }
}
