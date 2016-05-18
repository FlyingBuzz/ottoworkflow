package com.ottocap.NewWorkFlow.client.ContainerInvoice;

import com.extjs.gxt.ui.client.binding.Bindings;
import com.extjs.gxt.ui.client.binding.FieldBinding;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.google.gwt.user.client.Element;
import com.ottocap.NewWorkFlow.client.ContainerInvoice.ContainerData.ContainerDataContainer;
import com.ottocap.NewWorkFlow.client.Widget.FormHorizontalPanel2;
import com.ottocap.NewWorkFlow.client.Widget.USDMoneyField;

public class ContainerPortalTabsFeesLayout extends LayoutContainer
{
  private ContainerDataContainer containerdatacontainer;
  private ContainerPortalTabs containerportaltabs;
  private USDMoneyField oceancharges;
  private USDMoneyField demurrage;
  private USDMoneyField storage;
  private USDMoneyField perdiem;
  private USDMoneyField exam;
  private USDMoneyField ctffee;
  private USDMoneyField diversionfee;
  
  public ContainerPortalTabsFeesLayout(ContainerDataContainer containerdatacontainer, ContainerPortalTabs containerportaltabs)
  {
    this.containerdatacontainer = containerdatacontainer;
    this.containerportaltabs = containerportaltabs;
  }
  
  protected void onRender(Element parent, int index) {
    super.onRender(parent, index);
    RunMethod();
  }
  
  public void RunMethod() {
    this.oceancharges = new USDMoneyField();
    this.demurrage = new USDMoneyField();
    this.storage = new USDMoneyField();
    this.perdiem = new USDMoneyField();
    this.exam = new USDMoneyField();
    this.ctffee = new USDMoneyField();
    this.diversionfee = new USDMoneyField();
    
    setLabels();
    setBindings();
    setFields();
    setValidators();
    addListeners();
    
    FormHorizontalPanel2 orderhorizontalpanel1 = new FormHorizontalPanel2();
    FormHorizontalPanel2 orderhorizontalpanel2 = new FormHorizontalPanel2();
    FormHorizontalPanel2 orderhorizontalpanel3 = new FormHorizontalPanel2();
    
    orderhorizontalpanel1.add(this.oceancharges);
    orderhorizontalpanel1.add(this.demurrage);
    orderhorizontalpanel1.add(this.storage);
    
    orderhorizontalpanel2.add(this.perdiem);
    orderhorizontalpanel2.add(this.exam);
    orderhorizontalpanel2.add(this.ctffee);
    
    orderhorizontalpanel3.add(this.diversionfee);
    
    add(orderhorizontalpanel1);
    add(orderhorizontalpanel2);
    add(orderhorizontalpanel3);
  }
  
  private void setLabels()
  {
    this.oceancharges.setFieldLabel("Ocean Charges");
    this.demurrage.setFieldLabel("Demurrage");
    this.storage.setFieldLabel("Storage");
    this.perdiem.setFieldLabel("Per Diem");
    this.exam.setFieldLabel("Exam");
    this.ctffee.setFieldLabel("CTF Fee");
    this.diversionfee.setFieldLabel("Diversion Fee");
  }
  

  private void setValidators() {}
  
  private void setBindings()
  {
    Bindings allbindings = new Bindings();
    allbindings.bind(this.containerdatacontainer);
    allbindings.addFieldBinding(new FieldBinding(this.oceancharges, "oceancharges"));
    allbindings.addFieldBinding(new FieldBinding(this.demurrage, "demurrage"));
    allbindings.addFieldBinding(new FieldBinding(this.storage, "storage"));
    allbindings.addFieldBinding(new FieldBinding(this.perdiem, "perdiem"));
    allbindings.addFieldBinding(new FieldBinding(this.exam, "exam"));
    allbindings.addFieldBinding(new FieldBinding(this.ctffee, "ctffee"));
    allbindings.addFieldBinding(new FieldBinding(this.diversionfee, "diversionfee"));
  }
  

  private void setFields() {}
  
  private void addListeners() {}
  
  public ContainerPortalTabs getContainerPortalTabs()
  {
    return this.containerportaltabs;
  }
}
