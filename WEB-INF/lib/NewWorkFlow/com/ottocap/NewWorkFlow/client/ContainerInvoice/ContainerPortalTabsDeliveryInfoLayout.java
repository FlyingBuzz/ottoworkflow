package com.ottocap.NewWorkFlow.client.ContainerInvoice;

import com.extjs.gxt.ui.client.binding.Bindings;
import com.extjs.gxt.ui.client.binding.FieldBinding;
import com.extjs.gxt.ui.client.widget.DatePicker;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.ottocap.NewWorkFlow.client.ContainerInvoice.ContainerData.ContainerDataContainer;
import com.ottocap.NewWorkFlow.client.Widget.FormHorizontalPanel2;

public class ContainerPortalTabsDeliveryInfoLayout extends LayoutContainer
{
  private ContainerDataContainer containerdatacontainer;
  private ContainerPortalTabs containerportaltabs;
  private DateField pickedupfromport;
  private DateField scheduleddeliverytootto;
  private NumberField numberofcartons;
  
  public ContainerPortalTabsDeliveryInfoLayout(ContainerDataContainer containerdatacontainer, ContainerPortalTabs containerportaltabs)
  {
    this.containerdatacontainer = containerdatacontainer;
    this.containerportaltabs = containerportaltabs;
  }
  
  protected void onRender(com.google.gwt.user.client.Element parent, int index) {
    super.onRender(parent, index);
    RunMethod();
  }
  
  public void RunMethod() {
    this.pickedupfromport = new DateField();
    this.scheduleddeliverytootto = new DateField();
    this.numberofcartons = new NumberField();
    
    this.pickedupfromport.getDatePicker().setStartDay(7);
    this.scheduleddeliverytootto.getDatePicker().setStartDay(7);
    
    setLabels();
    setBindings();
    setFields();
    setValidators();
    addListeners();
    
    FormHorizontalPanel2 orderhorizontalpanel1 = new FormHorizontalPanel2();
    FormHorizontalPanel2 orderhorizontalpanel2 = new FormHorizontalPanel2();
    
    orderhorizontalpanel1.add(this.pickedupfromport);
    orderhorizontalpanel1.add(this.scheduleddeliverytootto);
    
    orderhorizontalpanel2.add(this.numberofcartons);
    
    add(orderhorizontalpanel1);
    add(orderhorizontalpanel2);
  }
  
  private void setLabels()
  {
    this.pickedupfromport.setFieldLabel("Pick up from Port");
    this.scheduleddeliverytootto.setFieldLabel("Scheduled Delivery to Otto");
    this.numberofcartons.setFieldLabel("Number of Cartons");
  }
  

  private void setValidators() {}
  
  private void setBindings()
  {
    Bindings allbindings = new Bindings();
    allbindings.bind(this.containerdatacontainer);
    allbindings.addFieldBinding(new FieldBinding(this.pickedupfromport, "pickedupfromport"));
    allbindings.addFieldBinding(new FieldBinding(this.scheduleddeliverytootto, "scheduleddeliverytootto"));
    allbindings.addFieldBinding(new FieldBinding(this.numberofcartons, "numberofcartons"));
  }
  
  private void setFields()
  {
    this.numberofcartons.setAllowNegative(false);
    this.numberofcartons.setAllowDecimals(false);
    this.numberofcartons.setPropertyEditorType(Integer.class);
  }
  

  private void addListeners() {}
  
  public ContainerPortalTabs getContainerPortalTabs()
  {
    return this.containerportaltabs;
  }
}
