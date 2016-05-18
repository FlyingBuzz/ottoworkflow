package com.ottocap.NewWorkFlow.client.OnlineWorkflow.Widget;

import com.extjs.gxt.ui.client.widget.Text;

public class BlackLineBar extends Text
{
  public BlackLineBar(int size) {
    setText(" ");
    setStyleAttribute("background-color", "#808080");
    setStyleAttribute("padding-top", "1px");
    setStyleAttribute("padding-bottom", "1px");
    setStyleAttribute("margin-top", "10px");
    setStyleAttribute("margin-bottom", "10px");
    setWidth(size);
  }
}
