package com.ottocap.NewWorkFlow.client.DataHolder.OrderInfo;

import com.extjs.gxt.ui.client.binding.Bindings;
import com.extjs.gxt.ui.client.binding.FieldBinding;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.DatePicker;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.Time;
import com.extjs.gxt.ui.client.widget.form.TimeField;
import com.extjs.gxt.ui.client.widget.form.Validator;
import com.ottocap.NewWorkFlow.client.DataHolder.Stores.DataStores;
import com.ottocap.NewWorkFlow.client.NewWorkFlow;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData;
import com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBox;
import com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBoxModelData;
import java.util.Date;

public class OrderInfoFields
{
  private OrderData myorderdata;
  private DateField orderdate = new DateField();
  private DateField ordershipdate = new DateField();
  private DateField estimatedshipdate = new DateField();
  private DateField inhanddate = new DateField();
  private DateField statusduedate = new DateField();
  private TimeField statusduetime = new TimeField();
  private TextField<String> ordernumber = new TextField();
  private TextField<String> customerpo = new TextField();
  private TextField<String> nextaction = new TextField();
  private OtherComboBox orderstatus = new OtherComboBox();
  private TextArea internalcomment = new TextArea();
  private TextArea ordercomment = new TextArea();
  private CheckBox rushorder = new CheckBox();
  
  public OrderInfoFields(OrderData myorderdata) {
    this.myorderdata = myorderdata;
    
    this.orderstatus.setEmptyText("Choose One:");
    this.orderstatus.setForceSelection(true);
    this.orderstatus.setStore(NewWorkFlow.get().getDataStores().getOrderStatusStore());
    
    this.orderdate.getDatePicker().setStartDay(7);
    this.ordershipdate.getDatePicker().setStartDay(7);
    this.estimatedshipdate.getDatePicker().setStartDay(7);
    this.inhanddate.getDatePicker().setStartDay(7);
    this.statusduedate.getDatePicker().setStartDay(7);
    
    Time mintime = new Time(8, 0);
    Time maxtime = new Time(17, 1);
    this.statusduetime.setMinValue(mintime.getDate());
    this.statusduetime.setMaxValue(maxtime.getDate());
    this.statusduetime.setFormat(com.google.gwt.i18n.client.DateTimeFormat.getFormat(com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat.HOUR_MINUTE));
    this.statusduetime.setForceSelection(true);
    this.statusduetime.setQueryDelay(0);
    this.statusduetime.setTriggerAction(com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction.ALL);
    
    setLabels();
    setBindings();
    setFields();
    setValidators();
    addListeners();
  }
  

  private void setLabels()
  {
    this.orderdate.setFieldLabel("Order Date");
    this.ordershipdate.setFieldLabel("Order Ship Date");
    this.estimatedshipdate.setFieldLabel("Estimated Ship Date");
    this.inhanddate.setFieldLabel("In-Hands Date");
    this.statusduedate.setFieldLabel("Status Due Date");
    this.statusduetime.setFieldLabel("Status Due Time");
    this.ordernumber.setFieldLabel("Order Number");
    this.customerpo.setFieldLabel("Customer's PO #");
    this.nextaction.setFieldLabel("Next Action");
    this.orderstatus.setFieldLabel("Order Status");
    this.internalcomment.setFieldLabel("Internal Comment");
    this.ordercomment.setFieldLabel("Order Comment");
    this.rushorder.setBoxLabel("Rush Order");
  }
  
  private void setValidators()
  {
    this.statusduetime.setAllowBlank(false);
    this.statusduedate.setAllowBlank(false);
    this.orderstatus.setAllowBlank(false);
    this.orderdate.setAllowBlank(false);
    this.ordershipdate.setAllowBlank(true);
    this.estimatedshipdate.setValidator(this.validator);
    this.inhanddate.setValidator(this.validator);
    this.statusduedate.setValidator(this.validator);
  }
  
  private void setBindings() {
    Bindings allbindings = new Bindings();
    allbindings.bind(this.myorderdata);
    allbindings.addFieldBinding(new FieldBinding(this.orderdate, "orderdate"));
    allbindings.addFieldBinding(new FieldBinding(this.ordershipdate, "ordershipdate"));
    allbindings.addFieldBinding(new FieldBinding(this.estimatedshipdate, "estimatedshipdate"));
    allbindings.addFieldBinding(new FieldBinding(this.inhanddate, "inhanddate"));
    allbindings.addFieldBinding(new FieldBinding(this.ordernumber, "ordernumber"));
    allbindings.addFieldBinding(new FieldBinding(this.customerpo, "customerpo"));
    allbindings.addFieldBinding(new FieldBinding(this.nextaction, "nextaction"));
    allbindings.addFieldBinding(new com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBoxFieldBinding(this.orderstatus, "orderstatus"));
    allbindings.addFieldBinding(new FieldBinding(this.internalcomment, "internalcomments"));
    allbindings.addFieldBinding(new FieldBinding(this.ordercomment, "specialnotes"));
    allbindings.addFieldBinding(new FieldBinding(this.rushorder, "rushorder"));
  }
  
  private void setFields()
  {
    this.statusduedate.setValue(this.myorderdata.getInternalDueDateTime());
    if (this.myorderdata.getInternalDueDateTime() != null) {
      this.statusduetime.setDateValue(this.myorderdata.getInternalDueDateTime());
    }
  }
  
  private void addListeners() {
    this.statusduedate.addListener(Events.Change, this.changelistener);
    this.statusduetime.addListener(Events.Change, this.changelistener);
    this.ordernumber.addListener(Events.Change, this.changelistener);
    this.orderstatus.addListener(Events.Change, this.changelistener);
  }
  
  private Listener<BaseEvent> changelistener = new Listener()
  {

    public void handleEvent(BaseEvent be)
    {
      if ((be.getSource().equals(OrderInfoFields.this.statusduedate)) || (be.getSource().equals(OrderInfoFields.this.statusduetime))) {
        if ((OrderInfoFields.this.statusduedate.getValue() != null) && (OrderInfoFields.this.statusduetime.getValue() != null)) {
          Date thedate = new Date(((Date)OrderInfoFields.this.statusduedate.getValue()).getTime());
          thedate.setHours(((Time)OrderInfoFields.this.statusduetime.getValue()).getHour());
          thedate.setMinutes(((Time)OrderInfoFields.this.statusduetime.getValue()).getMinutes());
          OrderInfoFields.this.myorderdata.setInternalDueDateTime(thedate);
        } else {
          OrderInfoFields.this.myorderdata.setInternalDueDateTime(null);
        }
      } else if (be.getSource().equals(OrderInfoFields.this.ordernumber)) {
        OrderInfoFields.this.myorderdata.setOrderNumber((String)OrderInfoFields.this.ordernumber.getValue());
      }
      else if ((be.getSource().equals(OrderInfoFields.this.orderstatus)) && 
        (OrderInfoFields.this.orderstatus.getValue().getValue().equals("Order Wrap-Up Call")) && 
        (OrderInfoFields.this.myorderdata.getOrderShipDate() == null)) {
        OrderInfoFields.this.myorderdata.setOrderShipDate(new Date());
      }
    }
  };
  



  private Validator validator = new Validator()
  {

    public String validate(Field<?> field, String value)
    {
      if (field == OrderInfoFields.this.estimatedshipdate) {
        if ((OrderInfoFields.this.estimatedshipdate.getValue() != null) && (OrderInfoFields.this.orderdate.getValue() != null) && 
          (((Date)OrderInfoFields.this.estimatedshipdate.getValue()).before((Date)OrderInfoFields.this.orderdate.getValue()))) {
          return "Date should be after order date";
        }
      }
      else if (field == OrderInfoFields.this.inhanddate) {
        if ((OrderInfoFields.this.inhanddate.getValue() != null) && (OrderInfoFields.this.orderdate.getValue() != null) && 
          (((Date)OrderInfoFields.this.inhanddate.getValue()).before((Date)OrderInfoFields.this.orderdate.getValue()))) {
          return "Date should be after order date";
        }
      }
      else if (field == OrderInfoFields.this.statusduedate) {
        Date currentday = new Date();
        currentday.setHours(0);
        currentday.setMinutes(0);
        currentday.setTime(currentday.getTime() / 100000L * 100000L);
        if ((OrderInfoFields.this.statusduedate.getValue() != null) && (((Date)OrderInfoFields.this.statusduedate.getValue()).before(currentday)) && (!OrderInfoFields.this.checkAllowOldDate())) {
          return "Date can't be before current date";
        }
      }
      return null;
    }
  };
  
  private boolean checkAllowOldDate()
  {
    if (this.myorderdata.getOrderStatus() == null)
      return false;
    if (NewWorkFlow.get().getDataStores().getOrderStatusStore().findModel("name", this.myorderdata.getOrderStatus()) == null) {
      return false;
    }
    return ((Boolean)((OtherComboBoxModelData)NewWorkFlow.get().getDataStores().getOrderStatusStore().findModel("name", this.myorderdata.getOrderStatus())).get("allowolddate")).booleanValue();
  }
  
  public DateField getOrderDateField()
  {
    return this.orderdate;
  }
  
  public DateField getOrderShipDateField() {
    return this.ordershipdate;
  }
  
  public DateField getEstimatedShipDateField() {
    return this.estimatedshipdate;
  }
  
  public DateField getInhandDateField() {
    return this.inhanddate;
  }
  
  public DateField getStatusDueDateField() {
    return this.statusduedate;
  }
  
  public TimeField getStatusDueTimeField() {
    return this.statusduetime;
  }
  
  public TextField<String> getOrderNumberField() {
    return this.ordernumber;
  }
  
  public TextField<String> getCustomerPOField() {
    return this.customerpo;
  }
  
  public TextField<String> getNextActionField() {
    return this.nextaction;
  }
  
  public OtherComboBox getOrderStatusField() {
    return this.orderstatus;
  }
  
  public TextArea getInternalCommentField() {
    return this.internalcomment;
  }
  
  public TextArea getOrderCommentField() {
    return this.ordercomment;
  }
  
  public CheckBox getRushOrderField() {
    return this.rushorder;
  }
}
