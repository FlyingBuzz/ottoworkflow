package com.ottocap.NewWorkFlow.client.DataHolder.WorkOrderInfo;

import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.ottocap.NewWorkFlow.client.NewWorkFlow;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData;
import com.ottocap.NewWorkFlow.client.Widget.FormHorizontalPanel2;

public class WorkOrderInfoLayout extends LayoutContainer
{
  private OrderData myorderdata;
  private WorkOrderInfoField workorderinfofield;
  
  public WorkOrderInfoLayout(OrderData myorderdata)
  {
    this.myorderdata = myorderdata;
  }
  
  protected void onRender(com.google.gwt.user.client.Element parent, int index) {
    super.onRender(parent, index);
    RunMethod();
  }
  
  public void RunMethod() {
    this.workorderinfofield = new WorkOrderInfoField(this.myorderdata);
    
    FormHorizontalPanel2 workordernumberpanel = new FormHorizontalPanel2();
    workordernumberpanel.add(this.workorderinfofield.getWorkOrderNumberField());
    add(workordernumberpanel);
    if (NewWorkFlow.get().isFaya.booleanValue()) {
      this.workorderinfofield.getWorkOrderNumberField().setVisible(false);
    }
    
    FieldSet digitizerinfofields = new FieldSet();
    digitizerinfofields.setHeadingHtml("Digitizing Vendor Information");
    FormHorizontalPanel2 digitizerinfohorz = new FormHorizontalPanel2();
    digitizerinfohorz.add(this.workorderinfofield.getDigitizingVendorField());
    digitizerinfohorz.add(this.workorderinfofield.getDigitizingVendorProcessDateField());
    digitizerinfohorz.add(this.workorderinfofield.getDigitizingVendorDueDateField());
    digitizerinfofields.add(digitizerinfohorz);
    add(digitizerinfofields);
    
    FieldSet embinfofields = new FieldSet();
    embinfofields.setHeadingHtml("Embroidery Vendor Information");
    FormHorizontalPanel2 embinfohorz = new FormHorizontalPanel2();
    embinfohorz.add(this.workorderinfofield.getEmbroideryVendorField());
    embinfohorz.add(this.workorderinfofield.getEmbroideryVendorProcessDateField());
    embinfohorz.add(this.workorderinfofield.getEmbroideryVendorDueDateField());
    embinfofields.add(embinfohorz);
    add(embinfofields);
    
    FieldSet heattransferinfofields = new FieldSet();
    heattransferinfofields.setHeadingHtml("Heat Transfer Vendor Information");
    FormHorizontalPanel2 heattransferhorz = new FormHorizontalPanel2();
    heattransferhorz.add(this.workorderinfofield.getHeatTransferVendorField());
    heattransferhorz.add(this.workorderinfofield.getHeatTransferVendorProcessDateField());
    heattransferhorz.add(this.workorderinfofield.getHeatTransferVendorDueDateField());
    heattransferinfofields.add(heattransferhorz);
    add(heattransferinfofields);
    
    FieldSet cadprintinfofields = new FieldSet();
    cadprintinfofields.setHeadingHtml("CAD Print Vendor Information");
    FormHorizontalPanel2 cadprinthorz = new FormHorizontalPanel2();
    cadprinthorz.add(this.workorderinfofield.getCADPrintVendorField());
    cadprinthorz.add(this.workorderinfofield.getCADPrintVendorProcessDateField());
    cadprinthorz.add(this.workorderinfofield.getCADPrintVendorDueDateField());
    cadprintinfofields.add(cadprinthorz);
    add(cadprintinfofields);
    
    FieldSet screenprintinfofields = new FieldSet();
    screenprintinfofields.setHeadingHtml("Screen Print Vendor Information");
    FormHorizontalPanel2 screenprinthorz = new FormHorizontalPanel2();
    screenprinthorz.add(this.workorderinfofield.getScreenPrintVendorField());
    screenprinthorz.add(this.workorderinfofield.getScreenPrintVendorProcessDateField());
    screenprinthorz.add(this.workorderinfofield.getScreenPrintVendorDueDateField());
    screenprintinfofields.add(screenprinthorz);
    add(screenprintinfofields);
    
    FieldSet dtginfofields = new FieldSet();
    dtginfofields.setHeadingHtml("Direct To Garment Vendor Information");
    FormHorizontalPanel2 dtginfohorz = new FormHorizontalPanel2();
    dtginfohorz.add(this.workorderinfofield.getDirectToGarmentVendorField());
    dtginfohorz.add(this.workorderinfofield.getDirectToGarmentVendorProcessDateField());
    dtginfohorz.add(this.workorderinfofield.getDirectToGarmentVendorDueDateField());
    dtginfofields.add(dtginfohorz);
    add(dtginfofields);
    
    FieldSet patchinfofields = new FieldSet();
    patchinfofields.setHeadingHtml("Patch Information");
    FormHorizontalPanel2 patchhorz = new FormHorizontalPanel2();
    patchhorz.add(this.workorderinfofield.getPatchVendorField());
    patchhorz.add(this.workorderinfofield.getPatchVendorProcessDateField());
    patchhorz.add(this.workorderinfofield.getPatchVendorDueDateField());
    patchinfofields.add(patchhorz);
    add(patchinfofields);
  }
}
