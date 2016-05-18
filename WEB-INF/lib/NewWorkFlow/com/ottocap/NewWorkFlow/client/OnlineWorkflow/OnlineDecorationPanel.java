package com.ottocap.NewWorkFlow.client.OnlineWorkflow;

import com.extjs.gxt.ui.client.event.IconButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.button.ToolButton;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.ottocap.NewWorkFlow.client.DataHolder.ProductsInfo.Decoration.DecorationInfoLocations;
import com.ottocap.NewWorkFlow.client.DataHolder.ProductsInfo.Decoration.DecorationPanelFields;
import com.ottocap.NewWorkFlow.client.OnlineWorkflow.Widget.GreyDescription;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogo;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogoDecoration;
import com.ottocap.NewWorkFlow.client.Widget.FormHorizontalPanel2;

public class OnlineDecorationPanel extends FieldSet
{
  private ToolButton closebutton = new ToolButton("x-tool-close");
  private OnlineOverseasLogoLayout overseaslogolayout;
  private OrderDataLogoDecoration decoration;
  private DecorationPanelFields decorationpanelfields;
  
  public OnlineDecorationPanel(OrderDataLogo orderdatalogo, OrderDataLogoDecoration decoration, OnlineOverseasLogoLayout overseaslogolayout)
  {
    this.decoration = decoration;
    this.overseaslogolayout = overseaslogolayout;
    this.decorationpanelfields = new DecorationPanelFields(orderdatalogo, decoration);
    
    setDecorationStore();
    
    this.closebutton.addSelectionListener(this.selectionlistener);
    
    add(this.closebutton);
    
    FormHorizontalPanel2 hor1 = new FormHorizontalPanel2();
    hor1.add(this.decorationpanelfields.getDecorationOptionsField());
    hor1.add(this.decorationpanelfields.getField1Field());
    hor1.add(this.decorationpanelfields.getField2Field());
    hor1.add(this.decorationpanelfields.getField3Field());
    
    FormHorizontalPanel2 hor2 = new FormHorizontalPanel2();
    hor2.add(this.decorationpanelfields.getField4Field());
    hor2.add(this.decorationpanelfields.getCommentsField(), new com.extjs.gxt.ui.client.widget.layout.FormData(418, -1));
    
    add(hor1);
    add(hor2);
  }
  
  public void setDecorationStore()
  {
    DecorationInfoLocations thelocation = this.decorationpanelfields.setDecorationStore();
    
    this.closebutton.setVisible(thelocation.getCanAdd());
    this.overseaslogolayout.getAddDecorationButton().setVisible(thelocation.getCanAdd());
    this.overseaslogolayout.getAddDecorationDescription().setVisible(thelocation.getCanAdd());
    this.decorationpanelfields.getDecorationOptionsField().setForceSelection(true);
  }
  
  public OrderDataLogoDecoration getDecoration()
  {
    return this.decoration;
  }
  
  private SelectionListener<IconButtonEvent> selectionlistener = new SelectionListener()
  {
    public void componentSelected(IconButtonEvent ce)
    {
      if (ce.getSource().equals(OnlineDecorationPanel.this.closebutton)) {
        OnlineDecorationPanel.this.overseaslogolayout.removeDecoration(OnlineDecorationPanel.this);
      }
    }
  };
}
