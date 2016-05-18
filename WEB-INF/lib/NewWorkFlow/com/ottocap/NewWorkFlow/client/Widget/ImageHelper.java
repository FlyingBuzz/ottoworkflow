package com.ottocap.NewWorkFlow.client.Widget;

import com.google.gwt.user.client.ui.Image;

public class ImageHelper extends com.extjs.gxt.ui.client.widget.LayoutContainer
{
  public void setUrl(String newurl)
  {
    removeAll(true);
    Image newimage = new Image(newurl);
    add(newimage);
    layout();
  }
}
