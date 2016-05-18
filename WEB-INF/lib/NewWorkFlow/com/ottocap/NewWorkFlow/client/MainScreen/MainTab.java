package com.ottocap.NewWorkFlow.client.MainScreen;

import com.extjs.gxt.ui.client.widget.Status;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.ottocap.NewWorkFlow.client.Icons.ResourceIcons;
import com.ottocap.NewWorkFlow.client.Icons.Resources;
import com.ottocap.NewWorkFlow.client.ManageOrders.ManageOrderScreen;
import com.ottocap.NewWorkFlow.client.NewWorkFlow;

public class MainTab extends TabItem
{
  private Status titlename = new Status();
  private String mainscreentext = "WorkFlow Program - Main Screen";
  private MainScreen mainscreen = new MainScreen();
  private Status welcomeback = new Status();
  
  public void updateEmail() {
    this.welcomeback.setText("Welcome Back, " + NewWorkFlow.get().getStoredUser().getEmailAddress());
  }
  
  public void setWindowHeader() {
    this.titlename.setText(this.mainscreentext);
  }
  
  public void setWindowHeader(String mainscreentext) {
    this.mainscreentext = mainscreentext;
    setWindowHeader();
  }
  
  protected void onShow()
  {
    super.onShow();
    setWindowHeader();
    if ((this.mainscreen.getMainPanel() != null) && ((this.mainscreen.getMainPanel() instanceof ManageOrderScreen))) {
      ((ManageOrderScreen)this.mainscreen.getMainPanel()).refleshLoader();
    }
  }
  
  public MainTab() {
    ToolBar headertool = new ToolBar();
    this.titlename.setText("WorkFlow Program");
    headertool.add(this.titlename);
    headertool.add(new com.extjs.gxt.ui.client.widget.toolbar.FillToolItem());
    headertool.add(this.welcomeback);
    
    setIcon(Resources.ICONS.home());
    setText("Main");
    setLayout(new RowLayout());
    add(headertool, new RowData(1.0D, -1.0D));
    add(this.mainscreen, new RowData(1.0D, 1.0D));
    
    setWindowHeader();
  }
  
  public MainScreen getMainScreen() {
    return this.mainscreen;
  }
}
