package com.ottocap.NewWorkFlow.client.OrderData;

import java.io.Serializable;
import java.util.TreeMap;










public class OrderDataVendors
  extends OrderDataShared
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  protected OrderDataVendorInformation digitizervendor = new OrderDataVendorInformation();
  protected OrderDataVendorInformation embroideryvendor = new OrderDataVendorInformation();
  protected OrderDataVendorInformation cadprintvendor = new OrderDataVendorInformation();
  protected OrderDataVendorInformation screenprintvendor = new OrderDataVendorInformation();
  protected OrderDataVendorInformation heattransfervendor = new OrderDataVendorInformation();
  protected OrderDataVendorInformation directtogarmentvendor = new OrderDataVendorInformation();
  protected OrderDataVendorInformation patchvendor = new OrderDataVendorInformation();
  protected TreeMap<String, OrderDataVendorInformation> overseasvendors = new TreeMap();
  
  public OrderDataVendors() {
    this.digitizervendor.setVendor(Integer.valueOf(1));
    this.embroideryvendor.setVendor(Integer.valueOf(1));
    this.directtogarmentvendor.setVendor(Integer.valueOf(1));
    this.cadprintvendor.setVendor(Integer.valueOf(12));
    this.screenprintvendor.setVendor(Integer.valueOf(12));
    this.heattransfervendor.setVendor(Integer.valueOf(5));
    this.patchvendor.setVendor(Integer.valueOf(10));
  }
  
  public void setChangeHappened(boolean changehappened)
  {
    this.digitizervendor.setChangeHappened(changehappened);
    this.embroideryvendor.setChangeHappened(changehappened);
    this.cadprintvendor.setChangeHappened(changehappened);
    this.screenprintvendor.setChangeHappened(changehappened);
    this.heattransfervendor.setChangeHappened(changehappened);
    this.patchvendor.setChangeHappened(changehappened);
    this.directtogarmentvendor.setChangeHappened(changehappened);
    for (OrderDataVendorInformation currentvendor : this.overseasvendors.values()) {
      currentvendor.setChangeHappened(changehappened);
    }
    super.setChangeHappened(changehappened);
  }
  
  public boolean getChangeHappened()
  {
    if ((this.digitizervendor.getChangeHappened()) || (this.heattransfervendor.getChangeHappened()) || (this.patchvendor.getChangeHappened()) || (this.embroideryvendor.getChangeHappened()) || (this.cadprintvendor.getChangeHappened()) || (this.screenprintvendor.getChangeHappened()) || (this.directtogarmentvendor.getChangeHappened())) {
      return true;
    }
    
    for (OrderDataVendorInformation currentvendor : this.overseasvendors.values()) {
      if (currentvendor.getChangeHappened()) {
        return true;
      }
    }
    
    return super.getChangeHappened();
  }
  
  public TreeMap<String, OrderDataVendorInformation> getOverseasVendor() {
    return this.overseasvendors;
  }
  
  public OrderDataVendorInformation getDigitizerVendor() {
    return this.digitizervendor;
  }
  
  public OrderDataVendorInformation getCADPrintVendor() {
    return this.cadprintvendor;
  }
  
  public OrderDataVendorInformation getScreenPrintVendor() {
    return this.screenprintvendor;
  }
  
  public OrderDataVendorInformation getHeatTransferVendor() {
    return this.heattransfervendor;
  }
  
  public OrderDataVendorInformation getPatchVendor() {
    return this.patchvendor;
  }
  
  public OrderDataVendorInformation getEmbroideryVendor() {
    return this.embroideryvendor;
  }
  
  public OrderDataVendorInformation getDirectToGarmentVendor() {
    return this.directtogarmentvendor;
  }
}
