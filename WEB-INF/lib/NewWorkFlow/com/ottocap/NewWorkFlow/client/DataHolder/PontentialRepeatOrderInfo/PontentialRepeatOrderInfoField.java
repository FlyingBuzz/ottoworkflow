package com.ottocap.NewWorkFlow.client.DataHolder.PontentialRepeatOrderInfo;

import com.extjs.gxt.ui.client.binding.Bindings;
import com.extjs.gxt.ui.client.binding.FieldBinding;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.ottocap.NewWorkFlow.client.DataHolder.Stores.DataStores;
import com.ottocap.NewWorkFlow.client.NewWorkFlow;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData.OrderType;
import com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBox;
import com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBoxFieldBinding;
import com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBoxModelData;
import java.util.Date;

public class PontentialRepeatOrderInfoField
{
  private OrderData myorderdata;
  private OtherComboBox repeatfreq = new OtherComboBox();
  private DateField repeatdate = new DateField();
  private TextArea repeatinternalcomments = new TextArea();
  
  public PontentialRepeatOrderInfoField(OrderData myorderdata) {
    this.myorderdata = myorderdata;
    
    this.repeatdate.getDatePicker().setStartDay(7);
    

    setLabels();
    setBindings();
    setFields();
    setListeners();
  }
  

  private void setLabels()
  {
    this.repeatdate.setFieldLabel("Follow-up Date");
    this.repeatfreq.setFieldLabel("Potential Projects");
    this.repeatinternalcomments.setFieldLabel("Internal Comments");
  }
  
  private void setBindings()
  {
    Bindings allbindings = new Bindings();
    allbindings.bind(this.myorderdata);
    allbindings.addFieldBinding(new OtherComboBoxFieldBinding(this.repeatfreq, "potentialrepeatfrequency"));
    allbindings.addFieldBinding(new FieldBinding(this.repeatdate, "potentialrepeatdate"));
    allbindings.addFieldBinding(new FieldBinding(this.repeatinternalcomments, "potentialinternalcomments"));
  }
  
  private void setListeners()
  {
    this.repeatfreq.addListener(com.extjs.gxt.ui.client.event.Events.Change, this.changelistener);
  }
  
  private void setFields() {
    this.repeatfreq.setForceSelection(true);
    this.repeatfreq.setAllowBlank(false);
    this.repeatfreq.setStore(NewWorkFlow.get().getDataStores().getPontentialFreqStore());
    this.repeatfreq.setEmptyText("Choose One");
  }
  
  public OtherComboBox getRepeatFrequencyField() {
    return this.repeatfreq;
  }
  
  public DateField getRepeatDateField() {
    return this.repeatdate;
  }
  
  public TextArea getRepeatInternalCommentsField() {
    return this.repeatinternalcomments;
  }
  


  private Listener<BaseEvent> changelistener = new Listener()
  {
    public void handleEvent(BaseEvent be)
    {
      if (be.getSource().equals(PontentialRepeatOrderInfoField.this.repeatfreq))
      {
        Date advancedate = new Date();
        
        if (PontentialRepeatOrderInfoField.this.repeatfreq.getValue() != null) {
          if (PontentialRepeatOrderInfoField.this.repeatfreq.getValue().getValue().equals("Not Sure")) {
            if (PontentialRepeatOrderInfoField.this.myorderdata.getOrderType().equals(OrderData.OrderType.DOMESTIC)) {
              advancedate.setTime(PontentialRepeatOrderInfoField.this.myorderdata.getOrderDate().getTime() + 15724800000L);
            } else {
              advancedate.setTime(PontentialRepeatOrderInfoField.this.myorderdata.getOrderDate().getTime() + 14515200000L);
            }
          } else if (PontentialRepeatOrderInfoField.this.repeatfreq.getValue().getValue().equals("Weekly")) {
            if (PontentialRepeatOrderInfoField.this.myorderdata.getOrderType().equals(OrderData.OrderType.DOMESTIC)) {
              advancedate.setTime(PontentialRepeatOrderInfoField.this.myorderdata.getOrderDate().getTime() + 604800000L);
            } else {
              advancedate.setTime(PontentialRepeatOrderInfoField.this.myorderdata.getOrderDate().getTime() + 604800000L);
            }
          } else if (PontentialRepeatOrderInfoField.this.repeatfreq.getValue().getValue().equals("Bi-Weekly")) {
            if (PontentialRepeatOrderInfoField.this.myorderdata.getOrderType().equals(OrderData.OrderType.DOMESTIC)) {
              advancedate.setTime(PontentialRepeatOrderInfoField.this.myorderdata.getOrderDate().getTime() + 604800000L);
            } else {
              advancedate.setTime(PontentialRepeatOrderInfoField.this.myorderdata.getOrderDate().getTime() + 604800000L);
            }
          } else if (PontentialRepeatOrderInfoField.this.repeatfreq.getValue().getValue().equals("Monthly")) {
            if (PontentialRepeatOrderInfoField.this.myorderdata.getOrderType().equals(OrderData.OrderType.DOMESTIC)) {
              advancedate.setTime(PontentialRepeatOrderInfoField.this.myorderdata.getOrderDate().getTime() + 1814400000L);
            } else {
              advancedate.setTime(PontentialRepeatOrderInfoField.this.myorderdata.getOrderDate().getTime() + 1209600000L);
            }
          } else if (PontentialRepeatOrderInfoField.this.repeatfreq.getValue().getValue().equals("Quarterly")) {
            if (PontentialRepeatOrderInfoField.this.myorderdata.getOrderType().equals(OrderData.OrderType.DOMESTIC)) {
              advancedate.setTime(PontentialRepeatOrderInfoField.this.myorderdata.getOrderDate().getTime() + 6480000000L);
            } else {
              advancedate.setTime(PontentialRepeatOrderInfoField.this.myorderdata.getOrderDate().getTime() + 5184000000L);
            }
          } else if (PontentialRepeatOrderInfoField.this.repeatfreq.getValue().getValue().equals("Yearly")) {
            if (PontentialRepeatOrderInfoField.this.myorderdata.getOrderType().equals(OrderData.OrderType.DOMESTIC)) {
              advancedate.setTime(PontentialRepeatOrderInfoField.this.myorderdata.getOrderDate().getTime() + 30240000000L);
            } else {
              advancedate.setTime(PontentialRepeatOrderInfoField.this.myorderdata.getOrderDate().getTime() + 28944000000L);
            }
          } else if (PontentialRepeatOrderInfoField.this.repeatfreq.getValue().getValue().equals("None")) {
            if (PontentialRepeatOrderInfoField.this.myorderdata.getOrderType().equals(OrderData.OrderType.DOMESTIC)) {
              advancedate.setTime(PontentialRepeatOrderInfoField.this.myorderdata.getOrderDate().getTime() + 30240000000L);
            } else {
              advancedate.setTime(PontentialRepeatOrderInfoField.this.myorderdata.getOrderDate().getTime() + 28944000000L);
            }
          }
        }
        
        PontentialRepeatOrderInfoField.this.myorderdata.setPotentialRepeatDate(advancedate);
      }
    }
  };
}
