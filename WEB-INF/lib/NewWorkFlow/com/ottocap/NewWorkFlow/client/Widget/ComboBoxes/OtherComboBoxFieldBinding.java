package com.ottocap.NewWorkFlow.client.Widget.ComboBoxes;

import com.extjs.gxt.ui.client.binding.FieldBinding;









public class OtherComboBoxFieldBinding
  extends FieldBinding
{
  protected OtherComboBox otherComboBox;
  
  public OtherComboBoxFieldBinding(OtherComboBox field, String property)
  {
    super(field, property);
    this.otherComboBox = field;
  }
  
  protected Object onConvertFieldValue(Object value)
  {
    return this.otherComboBox.getValue() != null ? this.otherComboBox.getValue().getValue() : null;
  }
  
  protected Object onConvertModelValue(Object value)
  {
    return this.otherComboBox.findTheValue((String)value);
  }
}
