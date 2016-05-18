package com.ottocap.NewWorkFlow.client.Widget;

import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.google.gwt.i18n.client.NumberFormat;

public class USDMoneyField extends NumberField
{
  String currentformat = "$#,##0.00";
  
  public USDMoneyField() {
    setFormat(NumberFormat.getFormat(this.currentformat));
    setBaseChars("0123456789$,");
  }
  
  public void setCurrentFormat(String theformat) {
    this.currentformat = theformat;
  }
  
  protected void onBlur(ComponentEvent arg0)
  {
    if ((getRawValue() != null) && (!getRawValue().equals(""))) {
      try {
        if (getRawValue().contains("$")) {
          Double rawvalue = Double.valueOf(NumberFormat.getFormat(this.currentformat).parse(getRawValue()));
          setRawValue(NumberFormat.getFormat(this.currentformat).format(rawvalue));
        } else {
          Double rawvalue = Double.valueOf(NumberFormat.getFormat(this.currentformat.substring(1)).parse(getRawValue()));
          setRawValue(NumberFormat.getFormat(this.currentformat).format(rawvalue));
        }
      }
      catch (NumberFormatException localNumberFormatException) {}
    }
    super.onBlur(arg0);
  }
}
