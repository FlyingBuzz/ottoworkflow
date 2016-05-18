package com.ottocap.NewWorkFlow.client.Widget;

import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.extjs.gxt.ui.client.widget.layout.LayoutData;
import com.google.gwt.user.client.ui.Widget;

public class FormHorizontalPanel2 extends HorizontalPanel
{
  public boolean addCheckBox(Widget widget)
  {
    FormData mydata = new FormData();
    mydata.setMargins(new Margins(14, 18, 0, 0));
    return add(widget, mydata, true);
  }
  
  public boolean addCheckBox(Widget widget, FormData formData) {
    if (formData.getMargins() == null) {
      formData.setMargins(new Margins(14, 18, 0, 0));
    }
    return add(widget, formData, true);
  }
  
  public boolean addButton(Widget widget) {
    FormData mydata = new FormData();
    mydata.setMargins(new Margins(18, 18, 0, 0));
    return add(widget, mydata);
  }
  
  public boolean add(Widget widget, FormData formData, boolean hidelabels) {
    if (formData == null) {
      formData = new FormData();
      formData.setMargins(new Margins(0, 18, 0, 0));
    } else if (formData.getMargins() == null) {
      formData.setMargins(new Margins(0, 18, 0, 0));
    }
    LayoutContainer theform = new LayoutContainer();
    
    FormLayout thelayout = new FormLayout(com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign.TOP);
    thelayout.setHideLabels(hidelabels);
    theform.setLayout(thelayout);
    
    theform.add(widget, formData);
    return super.add(theform);
  }
  
  public boolean add(Widget widget)
  {
    return add(widget, null);
  }
  
  public boolean add(Widget widget, LayoutData layoutData)
  {
    return add(widget, (FormData)layoutData, false);
  }
}
