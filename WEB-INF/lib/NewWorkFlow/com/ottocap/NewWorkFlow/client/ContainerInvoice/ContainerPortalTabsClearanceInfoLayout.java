package com.ottocap.NewWorkFlow.client.ContainerInvoice;

import com.extjs.gxt.ui.client.binding.Bindings;
import com.extjs.gxt.ui.client.binding.FieldBinding;
import com.extjs.gxt.ui.client.widget.DatePicker;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.ottocap.NewWorkFlow.client.ContainerInvoice.ContainerData.ContainerDataContainer;
import com.ottocap.NewWorkFlow.client.Widget.FormHorizontalPanel2;

public class ContainerPortalTabsClearanceInfoLayout extends LayoutContainer
{
  private ContainerDataContainer containerdatacontainer;
  private ContainerPortalTabs containerportaltabs;
  private DateField lastfreeday;
  private DateField customsreleased;
  private DateField steamshipreleased;
  
  public ContainerPortalTabsClearanceInfoLayout(ContainerDataContainer containerdatacontainer, ContainerPortalTabs containerportaltabs)
  {
    this.containerdatacontainer = containerdatacontainer;
    this.containerportaltabs = containerportaltabs;
  }
  
  protected void onRender(com.google.gwt.user.client.Element parent, int index) {
    super.onRender(parent, index);
    RunMethod();
  }
  
  public void RunMethod() {
    this.lastfreeday = new DateField();
    this.customsreleased = new DateField();
    this.steamshipreleased = new DateField();
    
    this.lastfreeday.getDatePicker().setStartDay(7);
    this.customsreleased.getDatePicker().setStartDay(7);
    this.steamshipreleased.getDatePicker().setStartDay(7);
    
    setLabels();
    setBindings();
    setFields();
    setValidators();
    addListeners();
    
    FormHorizontalPanel2 orderhorizontalpanel1 = new FormHorizontalPanel2();
    
    orderhorizontalpanel1.add(this.lastfreeday);
    orderhorizontalpanel1.add(this.customsreleased);
    orderhorizontalpanel1.add(this.steamshipreleased);
    
    add(orderhorizontalpanel1);
  }
  
  private void setLabels()
  {
    this.lastfreeday.setFieldLabel("Last Free Day (LFD)");
    this.customsreleased.setFieldLabel("Customes Released");
    this.steamshipreleased.setFieldLabel("Steamship Released");
  }
  

  private void setValidators() {}
  
  private void setBindings()
  {
    Bindings allbindings = new Bindings();
    allbindings.bind(this.containerdatacontainer);
    allbindings.addFieldBinding(new FieldBinding(this.lastfreeday, "lastfreeday"));
    allbindings.addFieldBinding(new FieldBinding(this.customsreleased, "customsreleased"));
    allbindings.addFieldBinding(new FieldBinding(this.steamshipreleased, "steamshipreleased"));
  }
  

  private void setFields() {}
  
  private void addListeners() {}
  
  public ContainerPortalTabs getContainerPortalTabs()
  {
    return this.containerportaltabs;
  }
}
