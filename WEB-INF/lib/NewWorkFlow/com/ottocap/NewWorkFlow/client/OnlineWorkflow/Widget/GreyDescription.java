package com.ottocap.NewWorkFlow.client.OnlineWorkflow.Widget;

import com.extjs.gxt.ui.client.widget.Html;

public class GreyDescription extends com.extjs.gxt.ui.client.widget.HorizontalPanel
{
  public GreyDescription(String text)
  {
    this(text, 980);
  }
  
  public GreyDescription(String text, int size) {
    setWidth(size);
    setStyleAttribute("background-color", "#ebebeb");
    
    setStyleAttribute("margin-top", "5px");
    setStyleAttribute("margin-bottom", "5px");
    setStyleAttribute("margin-left", "10px");
    setStyleAttribute("margin-right", "10px");
    setStyleAttribute("padding-top", "5px");
    setStyleAttribute("padding-bottom", "5px");
    setStyleAttribute("padding-left", "10px");
    setStyleAttribute("padding-right", "10px");
    
    Html startext = new Html("*");
    startext.setStyleAttribute("color", "#b51820");
    Html descriptiontext = new Html(text);
    descriptiontext.setStyleAttribute("font-size", "12px");
    add(startext);
    add(descriptiontext);
  }
}
