package com.ottocap.NewWorkFlow.client.OnlineWorkflow.Widget;

import com.extjs.gxt.ui.client.widget.VerticalPanel;

public class PaddedBodyContainer extends VerticalPanel
{
  public PaddedBodyContainer() {
    this(960);
  }
  
  public PaddedBodyContainer(int size) {
    setWidth(size);
    
    setStyleAttribute("margin-left", "20px");
    setStyleAttribute("margin-right", "20px");
  }
}
