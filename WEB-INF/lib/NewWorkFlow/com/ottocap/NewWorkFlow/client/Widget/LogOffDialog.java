package com.ottocap.NewWorkFlow.client.Widget;

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.ottocap.NewWorkFlow.client.NewWorkFlow;

public class LogOffDialog extends Window
{
  private Button yesbutton = new Button();
  private Button nobutton = new Button();
  
  public LogOffDialog() {
    addButton(this.yesbutton);
    addButton(this.nobutton);
    this.yesbutton.setText("Yes");
    this.yesbutton.addListener(Events.OnClick, this.clicklistener);
    this.nobutton.setText("No");
    this.nobutton.addListener(Events.OnClick, this.clicklistener);
    
    setMonitorWindowResize(true);
    setModal(true);
    
    setHeadingHtml("Are you sure you want to logout?");
    addText("You are about to logoff. All unsaved orders will be lost. Do you want to continue to logout?");
  }
  

  protected void onWindowResize(int width, int height)
  {
    super.onWindowResize(width, height);
    center();
  }
  
  private Listener<BaseEvent> clicklistener = new Listener()
  {
    public void handleEvent(BaseEvent be)
    {
      if (be.getSource().equals(LogOffDialog.this.yesbutton)) {
        LogOffDialog.this.yesbutton.disable();
        NewWorkFlow.get().getWorkFlowRPC().doLogoff(LogOffDialog.this.callback);
      } else if (be.getSource().equals(LogOffDialog.this.nobutton)) {
        LogOffDialog.this.hide();
      }
    }
  };
  

  private AsyncCallback<String> callback = new AsyncCallback()
  {
    public void onFailure(Throwable caught)
    {
      LogOffDialog.this.yesbutton.enable();
    }
    
    public void onSuccess(String result)
    {
      if (result == null) {
        LogOffDialog.this.yesbutton.enable();
      } else {
        LogOffDialog.this.reloadPage();
      }
    }
  };
  
  public native void reloadPage();
}
