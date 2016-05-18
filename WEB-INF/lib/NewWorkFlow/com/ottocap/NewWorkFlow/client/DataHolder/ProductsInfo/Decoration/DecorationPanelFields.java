package com.ottocap.NewWorkFlow.client.DataHolder.ProductsInfo.Decoration;

import com.extjs.gxt.ui.client.binding.Bindings;
import com.extjs.gxt.ui.client.binding.FieldBinding;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.ottocap.NewWorkFlow.client.DataHolder.Stores.DataStores;
import com.ottocap.NewWorkFlow.client.NewWorkFlow;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogo;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogoDecoration;
import com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBox;
import com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBoxModelData;
import java.util.ArrayList;

public class DecorationPanelFields
{
  private OtherComboBox decorationoptions = new OtherComboBox();
  private NumberField field1 = new NumberField();
  private NumberField field2 = new NumberField();
  private NumberField field3 = new NumberField();
  private NumberField field4 = new NumberField();
  private TextField<String> comments = new TextField();
  private OrderDataLogoDecoration decoration;
  private OrderDataLogo orderdatalogo;
  
  public DecorationPanelFields(OrderDataLogo orderdatalogo, OrderDataLogoDecoration decoration)
  {
    this.decoration = decoration;
    
    this.orderdatalogo = orderdatalogo;
    setDecorationStore();
    
    this.field1.setLabelSeparator(":");
    this.field2.setLabelSeparator(":");
    this.field3.setLabelSeparator(":");
    this.field4.setLabelSeparator(":");
    
    this.decorationoptions.setFieldLabel("Decoration");
    this.comments.setFieldLabel("Comments");
    
    Bindings allbinding = new Bindings();
    allbinding.bind(decoration);
    allbinding.addFieldBinding(new FieldBinding(this.field1, "field1"));
    allbinding.addFieldBinding(new FieldBinding(this.field2, "field2"));
    allbinding.addFieldBinding(new FieldBinding(this.field3, "field3"));
    allbinding.addFieldBinding(new FieldBinding(this.field4, "field4"));
    allbinding.addFieldBinding(new FieldBinding(this.comments, "comments"));
    allbinding.addFieldBinding(new com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBoxFieldBinding(this.decorationoptions, "name"));
    
    this.decorationoptions.addListener(com.extjs.gxt.ui.client.event.Events.Change, this.changelistener);
  }
  
  private void optionsfilter()
  {
    DecorationInfoNames theoptions = NewWorkFlow.get().getDataStores().getDecorationinfo().getName(this.decorationoptions.getValue() != null ? this.decorationoptions.getValue().getValue() : "");
    setFields(theoptions.getField1(), theoptions.getField2(), theoptions.getField3(), theoptions.getField4());
  }
  
  private void setFields(String fieldname1, String fieldname2, String fieldname3, String fieldname4) {
    if (fieldname1.equals("")) {
      this.field1.setVisible(false);
      this.decoration.setField1(null);
    } else {
      this.field1.setFieldLabel(fieldname1);
      this.field1.setVisible(true);
    }
    
    if (fieldname2.equals("")) {
      this.field2.setVisible(false);
      this.decoration.setField2(null);
    } else {
      this.field2.setFieldLabel(fieldname2);
      this.field2.setVisible(true);
    }
    
    if (fieldname3.equals("")) {
      this.field3.setVisible(false);
      this.decoration.setField3(null);
    } else {
      this.field3.setFieldLabel(fieldname3);
      this.field3.setVisible(true);
    }
    
    if (fieldname4.equals("")) {
      this.field4.setVisible(false);
      this.decoration.setField4(null);
    } else {
      this.field4.setFieldLabel(fieldname4);
      this.field4.setVisible(true);
    }
  }
  
  public DecorationInfoLocations setDecorationStore()
  {
    DecorationInfoLocations thelocation;
    if (NewWorkFlow.get().getDataStores().getDecorationinfo().getLocation(this.orderdatalogo.getLogoLocation()) == null) {
      DecorationInfoLocations thelocation = new DecorationInfoLocations();
      thelocation.setCanAdd(Boolean.valueOf(true));
    } else {
      thelocation = NewWorkFlow.get().getDataStores().getDecorationinfo().getLocation(this.orderdatalogo.getLogoLocation());
    }
    
    this.decorationoptions.setForceSelection(!thelocation.getCanAdd());
    ListStore<OtherComboBoxModelData> mystore = new ListStore();
    for (String currentname : thelocation.getNames()) {
      mystore.add(new OtherComboBoxModelData(currentname));
    }
    this.decorationoptions.changeStore(mystore, this.decoration.getName());
    
    if ((!thelocation.getCanAdd()) && (thelocation.getNames().size() > 0)) {
      if (this.decoration.getName().equals("")) {
        this.decoration.setName((String)thelocation.getNames().get(0));
      } else {
        this.decoration.setName(this.decorationoptions.getValue() == null ? "" : this.decorationoptions.getValue().getValue());
      }
    } else {
      this.decoration.setName(this.decorationoptions.getValue() == null ? "" : this.decorationoptions.getValue().getValue());
    }
    optionsfilter();
    
    return thelocation;
  }
  
  public OrderDataLogoDecoration getDecoration() {
    return this.decoration;
  }
  
  private Listener<BaseEvent> changelistener = new Listener()
  {
    public void handleEvent(BaseEvent be)
    {
      if (be.getSource().equals(DecorationPanelFields.this.decorationoptions)) {
        DecorationPanelFields.this.optionsfilter();
      }
    }
  };
  

  public OtherComboBox getDecorationOptionsField()
  {
    return this.decorationoptions;
  }
  
  public NumberField getField1Field() {
    return this.field1;
  }
  
  public NumberField getField2Field() {
    return this.field2;
  }
  
  public NumberField getField3Field() {
    return this.field3;
  }
  
  public NumberField getField4Field() {
    return this.field4;
  }
  
  public TextField<String> getCommentsField() {
    return this.comments;
  }
}
