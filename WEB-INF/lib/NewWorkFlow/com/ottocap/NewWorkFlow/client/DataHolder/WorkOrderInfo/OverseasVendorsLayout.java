package com.ottocap.NewWorkFlow.client.DataHolder.WorkOrderInfo;

import com.extjs.gxt.ui.client.core.FastMap;
import com.extjs.gxt.ui.client.core.FastSet;
import com.ottocap.NewWorkFlow.client.DataHolder.Stores.DataStores;
import com.ottocap.NewWorkFlow.client.DataHolder.Stores.NewStyleStore;
import com.ottocap.NewWorkFlow.client.NewWorkFlow;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataItem;
import java.util.Iterator;

public class OverseasVendorsLayout extends com.extjs.gxt.ui.client.widget.LayoutContainer
{
  private OrderData myorderdata;
  private FastMap<OverseasVendorLayout> thevendors;
  
  public OverseasVendorsLayout(OrderData myorderdata)
  {
    this.myorderdata = myorderdata;
  }
  
  protected void onRender(com.google.gwt.user.client.Element parent, int index) {
    super.onRender(parent, index);
    RunMethod();
  }
  
  public void RunMethod() {
    this.thevendors = new FastMap();
    
    for (com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBoxModelData mydata : NewWorkFlow.get().getDataStores().getOverseasVendorList()) {
      OverseasVendorLayout thevendor = new OverseasVendorLayout(this.myorderdata, mydata);
      this.thevendors.put(mydata.getValue(), thevendor);
      add(thevendor);
    }
    
    setVendorsVisible();
  }
  
  public void setVendorsVisible() {
    if (this.thevendors != null) {
      for (OverseasVendorLayout thevendorlayout : this.thevendors.values()) {
        thevendorlayout.setVisible(false);
      }
      
      FastSet vendortoenable = new FastSet();
      Iterator localIterator3; for (Iterator localIterator2 = this.myorderdata.getSets().iterator(); localIterator2.hasNext(); 
          localIterator3.hasNext())
      {
        com.ottocap.NewWorkFlow.client.OrderData.OrderDataSet theset = (com.ottocap.NewWorkFlow.client.OrderData.OrderDataSet)localIterator2.next();
        localIterator3 = theset.getItems().iterator(); continue;OrderDataItem theitem = (OrderDataItem)localIterator3.next();
        String stylenumber = theitem.getStyleNumber();
        if (!stylenumber.equals("")) {
          NewStyleStore thedata = (NewStyleStore)NewWorkFlow.get().getDataStores().getOverseasStyleStore().get(stylenumber);
          if ((theitem.getVendorNumber() != null) && (!theitem.getVendorNumber().equals("")) && (!theitem.getVendorNumber().equals("Default"))) {
            NewStyleStore thedata2 = ((NewStyleStore)NewWorkFlow.get().getDataStores().getOverseasStyleStore().get(stylenumber)).getAllVendors(theitem.getVendorNumber());
            vendortoenable.add(String.valueOf(thedata2.getVendorNumber()));
          } else if (thedata != null) {
            vendortoenable.add(String.valueOf(thedata.getVendorNumber()));
          }
        }
      }
      

      for (String thevendor : vendortoenable) {
        OverseasVendorLayout vendorlayout = (OverseasVendorLayout)this.thevendors.get(thevendor);
        if (vendorlayout != null) {
          vendorlayout.setVisible(true);
        }
      }
    }
  }
}
