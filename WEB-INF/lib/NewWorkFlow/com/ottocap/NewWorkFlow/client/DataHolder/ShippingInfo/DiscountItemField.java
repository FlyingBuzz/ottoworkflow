package com.ottocap.NewWorkFlow.client.DataHolder.ShippingInfo;

import com.extjs.gxt.ui.client.binding.Bindings;
import com.extjs.gxt.ui.client.binding.FieldBinding;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataDiscount;
import com.ottocap.NewWorkFlow.client.Widget.USDMoneyField;

public class DiscountItemField
{
  private TextField<String> reason = new TextField();
  private USDMoneyField amount = new USDMoneyField();
  private CheckBox intoitems = new CheckBox();
  
  public DiscountItemField(OrderDataDiscount discountitem)
  {
    this.reason.setFieldLabel("Reason");
    this.amount.setFieldLabel("Amount");
    this.amount.setAllowBlank(false);
    this.intoitems.setFieldLabel("Into Items");
    
    Bindings allbindings = new Bindings();
    allbindings.bind(discountitem);
    allbindings.addFieldBinding(new FieldBinding(this.reason, "reason"));
    allbindings.addFieldBinding(new FieldBinding(this.amount, "amount"));
    allbindings.addFieldBinding(new FieldBinding(this.intoitems, "intoitems"));
  }
  
  public TextField<String> getReasonField()
  {
    return this.reason;
  }
  
  public USDMoneyField getAmountField() {
    return this.amount;
  }
  
  public CheckBox getIntoItemsField() {
    return this.intoitems;
  }
}
