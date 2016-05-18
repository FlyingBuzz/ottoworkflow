package com.ottocap.NewWorkFlow.client.Widget;

import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.core.El;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.util.Size;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.Container;
import com.extjs.gxt.ui.client.widget.Layout;
import com.extjs.gxt.ui.client.widget.ScrollContainer;
import com.extjs.gxt.ui.client.widget.layout.LayoutData;
import com.extjs.gxt.ui.client.widget.layout.RowData;











public class MinRowLayout
  extends Layout
{
  private Style.Orientation orientation = Style.Orientation.VERTICAL;
  
  private boolean adjustForScroll;
  

  public MinRowLayout()
  {
    this(Style.Orientation.VERTICAL);
    setAdjustForScroll(true);
  }
  




  public MinRowLayout(Style.Orientation orientation)
  {
    this.orientation = orientation;
    this.monitorResize = true;
  }
  




  public Style.Orientation getOrientation()
  {
    return this.orientation;
  }
  




  public boolean isAdjustForScroll()
  {
    return this.adjustForScroll;
  }
  





  public void setAdjustForScroll(boolean adjustForScroll)
  {
    this.adjustForScroll = adjustForScroll;
  }
  




  public void setOrientation(Style.Orientation orientation)
  {
    this.orientation = orientation;
  }
  
  protected void layoutHorizontal(El target) {
    Size size = target.getStyleSize();
    
    int w = size.width - (this.adjustForScroll ? 19 : 0);
    w = w < 1200 ? 1200 : w;
    int h = size.height;
    int pw = w;
    
    int count = this.container.getItemCount();
    


    for (int i = 0; i < count; i++) {
      Component c = this.container.getItem(i);
      c.el().makePositionable(true);
      c.el().setStyleAttribute("margin", "0px");
      
      RowData data = null;
      LayoutData d = getLayoutData(c);
      if ((d != null) && ((d instanceof RowData))) {
        data = (RowData)d;
      } else {
        data = new RowData();
      }
      
      if (data.getWidth() > 1.0D) {
        pw = (int)(pw - data.getWidth());
      } else if (data.getWidth() == -1.0D) {
        callLayout(c, false);
        
        pw -= c.getOffsetWidth();
        if (data.getMargins() != null) {
          pw -= data.getMargins().left;
          pw -= data.getMargins().right;
        }
      }
    }
    
    pw = pw < 0 ? 0 : pw;
    
    int x = target.getFrameWidth("l");
    int sTop = target.getFrameWidth("t");
    
    for (int i = 0; i < count; i++) {
      Component c = this.container.getItem(i);
      RowData data = null;
      LayoutData d = getLayoutData(c);
      if ((d != null) && ((d instanceof RowData))) {
        data = (RowData)d;
      } else {
        data = new RowData();
      }
      double height = data.getHeight();
      
      if ((height > 0.0D) && (height <= 1.0D)) {
        height *= h;
      } else if (height == -1.0D) {
        height = c.getOffsetHeight();
      }
      
      double width = data.getWidth();
      if ((width > 0.0D) && (width <= 1.0D)) {
        width *= pw;
      } else if (width == -1.0D) {
        width = c.getOffsetWidth();
      }
      
      int tx = x;
      int ty = sTop;
      int tw = (int)width;
      int th = (int)height;
      
      Margins m = data.getMargins();
      if (m != null) {
        tx += m.left;
        ty += m.top;
        if (data.getHeight() != -1.0D) {
          th -= m.top;
          th -= m.bottom;
        }
        if (data.getWidth() != -1.0D) {
          tw -= m.left;
          tw -= m.right;
        }
      }
      
      setPosition(c, tx, ty);
      setSize(c, tw, th);
      x += tw + (m != null ? m.right + m.left : 0);
    }
  }
  
  protected void layoutVertical(El target) {
    Size size = target.getStyleSize();
    
    int w = size.width - (this.adjustForScroll ? 19 : 0);
    w = w < 1200 ? 1200 : w;
    int h = size.height;
    int ph = h;
    
    int count = this.container.getItemCount();
    


    for (int i = 0; i < count; i++) {
      Component c = this.container.getItem(i);
      RowData data = null;
      LayoutData d = getLayoutData(c);
      if ((d != null) && ((d instanceof RowData))) {
        data = (RowData)d;
      } else {
        data = new RowData();
      }
      
      if (data.getHeight() > 1.0D) {
        ph = (int)(ph - data.getHeight());
      } else if (data.getHeight() == -1.0D) {
        callLayout(c, false);
        
        ph -= c.getOffsetHeight();
        ph -= c.el().getMargins("tb");
      }
    }
    

    ph = ph < 0 ? 0 : ph;
    
    for (int i = 0; i < count; i++) {
      Component c = this.container.getItem(i);
      RowData data = null;
      LayoutData d = getLayoutData(c);
      if ((d != null) && ((d instanceof RowData))) {
        data = (RowData)d;
      } else {
        data = new RowData();
      }
      
      double width = data.getWidth();
      
      if ((width > 0.0D) && (width <= 1.0D)) {
        width *= w;
      }
      
      width -= getSideMargins(c);
      
      double height = data.getHeight();
      if ((height > 0.0D) && (height <= 1.0D)) {
        height *= ph;
      }
      
      height -= c.el().getMargins("tb");
      
      setSize(c, (int)width, (int)height);
    }
  }
  
  protected void onLayout(Container<?> container, El target)
  {
    super.onLayout(container, target);
    
    if ((container instanceof ScrollContainer)) {
      ScrollContainer<?> sc = (ScrollContainer)container;
      sc.setScrollMode(sc.getScrollMode());
    } else {
      target.setStyleAttribute("overflow", "hidden");
    }
    
    if (this.orientation == Style.Orientation.VERTICAL) {
      layoutVertical(target);
    } else {
      target.makePositionable();
      layoutHorizontal(target);
    }
  }
}
