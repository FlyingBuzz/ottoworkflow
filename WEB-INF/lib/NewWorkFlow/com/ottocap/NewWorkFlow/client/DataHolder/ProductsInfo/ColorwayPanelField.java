package com.ottocap.NewWorkFlow.client.DataHolder.ProductsInfo;

import com.ottocap.NewWorkFlow.client.NewWorkFlow;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogo;
import com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBox;
import com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBoxFieldBinding;

public class ColorwayPanelField
{
  private OtherComboBox convertallthreads = new OtherComboBox();
  
  public ColorwayPanelField(OrderDataLogo orderdatalogo)
  {
    this.convertallthreads.setFieldLabel("Convert Threads To");
    
    this.convertallthreads.setStore(NewWorkFlow.get().getDataStores().getThreadsInformationData().getConvertToThreadStore());
    this.convertallthreads.setForceSelection(true);
    
    OtherComboBoxFieldBinding thebinding = new OtherComboBoxFieldBinding(this.convertallthreads, "threadbrand");
    thebinding.bind(orderdatalogo);
  }
  
  public OtherComboBox getConvertAllThreadsField()
  {
    return this.convertallthreads;
  }
}
