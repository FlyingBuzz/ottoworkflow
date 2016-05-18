package com.ottocap.NewWorkFlow.server.ExtendOrderData;

import com.ottocap.NewWorkFlow.client.OrderData.OrderDataVendors;

public class ExtendOrderDataVendors
  extends OrderDataVendors
{
  private static final long serialVersionUID = 1L;
  
  public ExtendOrderDataVendors()
  {
    this.digitizervendor = new ExtendOrderDataVendorInformation();
    this.embroideryvendor = new ExtendOrderDataVendorInformation();
    this.cadprintvendor = new ExtendOrderDataVendorInformation();
    this.screenprintvendor = new ExtendOrderDataVendorInformation();
    this.heattransfervendor = new ExtendOrderDataVendorInformation();
    this.directtogarmentvendor = new ExtendOrderDataVendorInformation();
    this.patchvendor = new ExtendOrderDataVendorInformation();
  }
  
  public ExtendOrderDataVendorInformation getDigitizerVendor() {
    return (ExtendOrderDataVendorInformation)this.digitizervendor;
  }
  
  public ExtendOrderDataVendorInformation getCADPrintVendor() {
    return (ExtendOrderDataVendorInformation)this.cadprintvendor;
  }
  
  public ExtendOrderDataVendorInformation getScreenPrintVendor() {
    return (ExtendOrderDataVendorInformation)this.screenprintvendor;
  }
  
  public ExtendOrderDataVendorInformation getHeatTransferVendor() {
    return (ExtendOrderDataVendorInformation)this.heattransfervendor;
  }
  
  public ExtendOrderDataVendorInformation getEmbroideryVendor() {
    return (ExtendOrderDataVendorInformation)this.embroideryvendor;
  }
  
  public ExtendOrderDataVendorInformation getDirectToGarmentVendor() {
    return (ExtendOrderDataVendorInformation)this.directtogarmentvendor;
  }
  
  public ExtendOrderDataVendorInformation getPatchVendor() {
    return (ExtendOrderDataVendorInformation)this.patchvendor;
  }
}
