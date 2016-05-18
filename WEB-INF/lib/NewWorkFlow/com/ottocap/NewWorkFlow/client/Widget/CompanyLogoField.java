package com.ottocap.NewWorkFlow.client.Widget;

import com.extjs.gxt.ui.client.core.El;
import com.google.gwt.user.client.Element;

public class CompanyLogoField extends com.extjs.gxt.ui.client.widget.form.TwinTriggerField<String>
{
  public CompanyLogoField()
  {
    setTriggerStyle("x-form-search-trigger");
    setTwinTriggerStyle("x-form-clear-trigger");
  }
  
  protected void onRender(Element target, int index)
  {
    super.onRender(target, index);
    this.input.disable();
    
    this.trigger.setTitle("View Logo");
    this.twinTrigger.setTitle("Remove");
  }
}
