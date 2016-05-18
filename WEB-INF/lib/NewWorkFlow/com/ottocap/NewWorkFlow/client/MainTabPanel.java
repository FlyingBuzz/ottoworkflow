package com.ottocap.NewWorkFlow.client;

import com.extjs.gxt.ui.client.widget.TabPanel;
import com.google.gwt.user.client.Element;
import com.ottocap.NewWorkFlow.client.MainScreen.MainTab;

public class MainTabPanel extends TabPanel
{
  private MainTab maintab = new MainTab();
  
  protected void onRender(Element target, int index)
  {
    setTabPosition(com.extjs.gxt.ui.client.widget.TabPanel.TabPosition.BOTTOM);
    super.onRender(target, index);
    add(this.maintab);
  }
  
  public MainTab getMainTab() {
    return this.maintab;
  }
}
