package com.ottocap.NewWorkFlow.client.DataHolder.ProductsInfo.Decoration;

import com.extjs.gxt.ui.client.event.IconButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.button.ToolButton;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.ottocap.NewWorkFlow.client.DataHolder.ProductsInfo.OverseasLogoLayout;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogo;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogoDecoration;
import com.ottocap.NewWorkFlow.client.Widget.FormHorizontalPanel2;

public class DecorationPanel extends FieldSet
{
  private ToolButton closebutton = new ToolButton("x-tool-close");
  private OverseasLogoLayout overseaslogolayout;
  private OrderDataLogoDecoration decoration;
  private DecorationPanelFields decorationpanelfields;
  
  public DecorationPanel(OrderDataLogo orderdatalogo, OrderDataLogoDecoration decoration, OverseasLogoLayout overseaslogolayout)
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
    hor1.add(this.decorationpanelfields.getField4Field());
    
    FormHorizontalPanel2 hor2 = new FormHorizontalPanel2();
    hor2.add(this.decorationpanelfields.getCommentsField(), new com.extjs.gxt.ui.client.widget.layout.FormData(418, -1));
    
    add(hor1);
    add(hor2);
  }
  
  public void setDecorationStore()
  {
    DecorationInfoLocations thelocation = this.decorationpanelfields.setDecorationStore();
    
    this.closebutton.setVisible(thelocation.getCanAdd());
    this.overseaslogolayout.getAddDecorationButton().setVisible(thelocation.getCanAdd());
  }
  
  public OrderDataLogoDecoration getDecoration()
  {
    return this.decoration;
  }
  
  private SelectionListener<IconButtonEvent> selectionlistener = new SelectionListener()
  {
    public void componentSelected(IconButtonEvent ce)
    {
      if (ce.getSource().equals(DecorationPanel.this.closebutton)) {
        DecorationPanel.this.overseaslogolayout.removeDecoration(DecorationPanel.this);
      }
    }
  };
}
