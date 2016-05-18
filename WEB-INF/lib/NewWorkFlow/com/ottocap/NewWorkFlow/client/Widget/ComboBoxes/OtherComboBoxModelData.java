package com.ottocap.NewWorkFlow.client.Widget.ComboBoxes;

import com.extjs.gxt.ui.client.data.BaseModelData;




public class OtherComboBoxModelData
  extends BaseModelData
{
  private static final long serialVersionUID = 1L;
  
  public OtherComboBoxModelData() {}
  
  public OtherComboBoxModelData(String name, String value)
  {
    set("name", name);
    set("value", value);
  }
  
  public OtherComboBoxModelData(String name) {
    this(name, name);
  }
  
  public String getName() {
    return (String)get("name");
  }
  
  public String getValue() {
    return (String)get("value");
  }
}
