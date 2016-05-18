package com.ottocap.NewWorkFlow.client.ContainerInvoice;

import com.extjs.gxt.ui.client.binding.Bindings;
import com.extjs.gxt.ui.client.binding.FieldBinding;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.google.gwt.user.client.Element;
import com.ottocap.NewWorkFlow.client.ContainerInvoice.ContainerData.ContainerDataContainer;
import com.ottocap.NewWorkFlow.client.Widget.FormHorizontalPanel2;

public class ContainerPortalTabsCommentsLayout extends LayoutContainer
{
  private ContainerDataContainer containerdatacontainer;
  private ContainerPortalTabs containerportaltabs;
  private TextArea comments;
  
  public ContainerPortalTabsCommentsLayout(ContainerDataContainer containerdatacontainer, ContainerPortalTabs containerportaltabs)
  {
    this.containerdatacontainer = containerdatacontainer;
    this.containerportaltabs = containerportaltabs;
  }
  
  protected void onRender(Element parent, int index) {
    super.onRender(parent, index);
    RunMethod();
  }
  
  public void RunMethod() {
    this.comments = new TextArea();
    
    setLabels();
    setBindings();
    setFields();
    setValidators();
    addListeners();
    
    FormHorizontalPanel2 orderhorizontalpanel1 = new FormHorizontalPanel2();
    
    this.comments.setHeight(232);
    orderhorizontalpanel1.add(this.comments, new FormData(854, -1));
    
    add(orderhorizontalpanel1);
  }
  
  private void setLabels()
  {
    this.comments.setFieldLabel("Comments");
  }
  

  private void setValidators() {}
  
  private void setBindings()
  {
    Bindings allbindings = new Bindings();
    allbindings.bind(this.containerdatacontainer);
    allbindings.addFieldBinding(new FieldBinding(this.comments, "comments"));
  }
  

  private void setFields() {}
  
  private void addListeners() {}
  
  public ContainerPortalTabs getContainerPortalTabs()
  {
    return this.containerportaltabs;
  }
}
