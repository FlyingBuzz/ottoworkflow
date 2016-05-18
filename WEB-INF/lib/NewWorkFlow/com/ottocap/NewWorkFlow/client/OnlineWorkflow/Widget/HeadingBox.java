package com.ottocap.NewWorkFlow.client.OnlineWorkflow.Widget;

import com.extjs.gxt.ui.client.widget.Html;

public class HeadingBox extends Html
{
  public HeadingBox(String text) {
    this(text, 1000);
  }
  
  public HeadingBox(String text, int size) {
    setHtml(text);
    setWidth(size);
    setStyleAttribute("background-color", "#213e7a");
    setStyleAttribute("color", "#FFFFFF");
    setStyleAttribute("margin-top", "10px");
    setStyleAttribute("margin-bottom", "5px");
    setStyleAttribute("padding-top", "5px");
    setStyleAttribute("padding-bottom", "5px");
    setStyleAttribute("padding-left", "10px");
  }
}
