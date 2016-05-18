package com.ottocap.NewWorkFlow.client.DataHolder.ShippingInfo;

import com.extjs.gxt.ui.client.binding.Bindings;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.ottocap.NewWorkFlow.client.DataHolder.Stores.DataStores;
import com.ottocap.NewWorkFlow.client.NewWorkFlow;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData;
import com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBox;
import com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBoxFieldBinding;
import com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBoxModelData;
import com.ottocap.NewWorkFlow.client.Widget.FormHorizontalPanel2;
import com.ottocap.NewWorkFlow.client.Widget.USDMoneyField;

public class ShippingInfoField
{
  private OrderData myorderdata;
  private USDMoneyField shippingcost;
  private OtherComboBox shippingtype;
  
  public ShippingInfoField(OrderData myorderdata)
  {
    this.myorderdata = myorderdata;
    this.shippingcost = new USDMoneyField();
    this.shippingtype = new OtherComboBox();
    
    this.shippingtype.setEmptyText("Choose One:");
    this.shippingtype.setStore(NewWorkFlow.get().getDataStores().getShippingTypeStore());
    
    setLabels();
    setBindings();
    addListeners();
    
    FormHorizontalPanel2 orderhorizontalpanel1 = new FormHorizontalPanel2();
    
    orderhorizontalpanel1.add(this.shippingtype, new com.extjs.gxt.ui.client.widget.layout.FormData(418, 0));
    orderhorizontalpanel1.add(this.shippingcost);
    
    showhideshippingcost();
  }
  

  private void setLabels()
  {
    this.shippingtype.setFieldLabel("Shipping Type");
    this.shippingcost.setFieldLabel("Shipping Cost");
  }
  
  private void setBindings() {
    Bindings allbindings = new Bindings();
    allbindings.bind(this.myorderdata);
    allbindings.addFieldBinding(new com.extjs.gxt.ui.client.binding.FieldBinding(this.shippingcost, "shippingcost"));
    allbindings.addFieldBinding(new OtherComboBoxFieldBinding(this.shippingtype, "shippingtype"));
  }
  
  private void addListeners() {
    this.shippingtype.addListener(com.extjs.gxt.ui.client.event.Events.Change, this.changelistener);
  }
  
  private Listener<BaseEvent> changelistener = new Listener()
  {
    public void handleEvent(BaseEvent be)
    {
      if (be.getSource().equals(ShippingInfoField.this.shippingtype)) {
        ShippingInfoField.this.myorderdata.setShippingType(ShippingInfoField.this.shippingtype.getValue() != null ? ShippingInfoField.this.shippingtype.getValue().getValue() : null);
        ShippingInfoField.this.showhideshippingcost();
      }
    }
  };
  
  private void showhideshippingcost()
  {
    if (this.myorderdata.getShippingType().equals("Pick-up")) {
      this.shippingcost.setVisible(false);
    } else {
      this.shippingcost.setVisible(true);
    }
  }
  
  public USDMoneyField getShippingCostField() {
    return this.shippingcost;
  }
  
  public OtherComboBox getShippingTypeField() {
    return this.shippingtype;
  }
}
