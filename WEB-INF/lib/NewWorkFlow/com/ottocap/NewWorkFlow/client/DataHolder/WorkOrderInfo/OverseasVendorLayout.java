package com.ottocap.NewWorkFlow.client.DataHolder.WorkOrderInfo;

import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData;
import com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBoxModelData;
import com.ottocap.NewWorkFlow.client.Widget.FormHorizontalPanel2;

public class OverseasVendorLayout extends com.extjs.gxt.ui.client.widget.LayoutContainer
{
  private OverseasVendorField overseasvendorfield;
  
  public OverseasVendorLayout(OrderData myorderdata, OtherComboBoxModelData vendordata)
  {
    this.overseasvendorfield = new OverseasVendorField(myorderdata, vendordata);
    
    FieldSet digitizerinfofields = new FieldSet();
    digitizerinfofields.setHeadingHtml(vendordata.getName());
    FormHorizontalPanel2 horz1 = new FormHorizontalPanel2();
    horz1.add(this.overseasvendorfield.getPurchaseOrderNumberField());
    horz1.add(this.overseasvendorfield.getDigitizingProcessDateField());
    horz1.add(this.overseasvendorfield.getDigitizingDueDateField());
    digitizerinfofields.add(horz1);
    FormHorizontalPanel2 horz2 = new FormHorizontalPanel2();
    horz2.add(this.overseasvendorfield.getPurchaseOrderProcessDateField());
    horz2.add(this.overseasvendorfield.getPurchaseOrderDueDateField());
    horz2.add(this.overseasvendorfield.getVendorShippingMethodField());
    digitizerinfofields.add(horz2);
    add(digitizerinfofields);
  }
}
