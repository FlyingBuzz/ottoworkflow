package com.ottocap.NewWorkFlow.client.DataHolder.ProductsInfo;

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.BaseObservable;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.Observable;
import com.extjs.gxt.ui.client.store.ListStore;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataSet;
import com.ottocap.NewWorkFlow.client.Widget.BuzzEvents;
import com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBoxModelData;

public class SetField
{
  private ListStore<OtherComboBoxModelData> logolocations = new ListStore();
  private OrderDataSet orderset;
  private Observable observable = new BaseObservable();
  
  public SetField(OrderDataSet orderset) {
    this.orderset = orderset;
  }
  
  public void addListener(EventType eventType, Listener<? extends BaseEvent> listener) {
    this.observable.addListener(eventType, listener);
  }
  
  public void setLogoLocations()
  {
    BaseEvent be = new BaseEvent(BuzzEvents.SetLogoLocations);
    this.observable.fireEvent(BuzzEvents.SetLogoLocations, be);
  }
  
  public ListStore<OtherComboBoxModelData> getDecorationList(String logolocation) {
    String thecolor = "#d63232";
    String blankcolor = "#ffffff";
    
    ListStore<OtherComboBoxModelData> newstore = new ListStore();
    OtherComboBoxModelData currentmodel = (OtherComboBoxModelData)this.logolocations.findModel("value", logolocation);
    
    if (currentmodel != null) {
      if (((Integer)currentmodel.get("embcount")).intValue() != 0) {
        OtherComboBoxModelData flatemb = new OtherComboBoxModelData("Flat Embroidery");
        flatemb.set("matchingrest", ((Integer)currentmodel.get("embcount")).intValue() == this.orderset.getItemCount() ? blankcolor : thecolor);
        newstore.add(flatemb);
        OtherComboBoxModelData emb3d = new OtherComboBoxModelData("3D Embroidery");
        emb3d.set("matchingrest", ((Integer)currentmodel.get("embcount")).intValue() == this.orderset.getItemCount() ? blankcolor : thecolor);
        newstore.add(emb3d);
      }
      
      if (((Integer)currentmodel.get("heatcount")).intValue() != 0) {
        OtherComboBoxModelData heattransfer = new OtherComboBoxModelData("Heat Transfer");
        heattransfer.set("matchingrest", ((Integer)currentmodel.get("heatcount")).intValue() == this.orderset.getItemCount() ? blankcolor : thecolor);
        newstore.add(heattransfer);
      }
      
      if (((Integer)currentmodel.get("dtgcount")).intValue() != 0) {
        OtherComboBoxModelData directtogarment = new OtherComboBoxModelData("Direct To Garment");
        directtogarment.set("matchingrest", ((Integer)currentmodel.get("dtgcount")).intValue() == this.orderset.getItemCount() ? blankcolor : thecolor);
        newstore.add(directtogarment);
      }
      
      if (((Integer)currentmodel.get("cadprintcount")).intValue() != 0) {
        OtherComboBoxModelData cadprint = new OtherComboBoxModelData("CAD Print");
        cadprint.set("matchingrest", ((Integer)currentmodel.get("cadprintcount")).intValue() == this.orderset.getItemCount() ? blankcolor : thecolor);
        newstore.add(cadprint);
      }
      
      if (((Integer)currentmodel.get("screenprintcount")).intValue() != 0) {
        OtherComboBoxModelData screenprint = new OtherComboBoxModelData("Screen Print");
        screenprint.set("matchingrest", ((Integer)currentmodel.get("screenprintcount")).intValue() == this.orderset.getItemCount() ? blankcolor : thecolor);
        newstore.add(screenprint);
        OtherComboBoxModelData fourcolorscreenprint = new OtherComboBoxModelData("Four Color Screen Print");
        fourcolorscreenprint.set("matchingrest", ((Integer)currentmodel.get("screenprintcount")).intValue() == this.orderset.getItemCount() ? blankcolor : thecolor);
        newstore.add(fourcolorscreenprint);
      }
      
      OtherComboBoxModelData patch = new OtherComboBoxModelData("Patch");
      patch.set("matchingrest", blankcolor);
      newstore.add(patch);
    }
    

    return newstore;
  }
  
  public ListStore<OtherComboBoxModelData> getLogoLocations() {
    return this.logolocations;
  }
  
  public void setLogoLocations(ListStore<OtherComboBoxModelData> logolocations) {
    this.logolocations = logolocations;
  }
}
