package com.ottocap.NewWorkFlow.client.Widget.ComboBoxes;

import com.extjs.gxt.ui.client.binding.FieldBinding;









public class OtherComboBoxDoubleFieldBinding
  extends FieldBinding
{
  protected OtherComboBox otherComboBox;
  
  public OtherComboBoxDoubleFieldBinding(OtherComboBox field, String property)
  {
    super(field, property);
    this.otherComboBox = field;
  }
  
  protected Object onConvertFieldValue(Object value)
  {
    String thevalue = this.otherComboBox.getValue() != null ? this.otherComboBox.getValue().getValue() : null;
    if (thevalue != null) {
      return Double.valueOf(thevalue);
    }
    return null;
  }
  

  protected Object onConvertModelValue(Object value)
  {
    return this.otherComboBox.findTheValue(String.valueOf(value));
  }
}
