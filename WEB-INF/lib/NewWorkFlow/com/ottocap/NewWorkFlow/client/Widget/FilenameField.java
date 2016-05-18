package com.ottocap.NewWorkFlow.client.Widget;

import com.extjs.gxt.ui.client.core.El;
import com.google.gwt.user.client.Element;

public class FilenameField extends com.extjs.gxt.ui.client.widget.form.TriggerField<String>
{
  public FilenameField()
  {
    setTriggerStyle("x-form-clear-trigger");
  }
  
  protected void onRender(Element target, int index)
  {
    super.onRender(target, index);
    this.input.disable();
    this.trigger.setTitle("Remove Logo");
  }
}
