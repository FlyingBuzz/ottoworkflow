package com.ottocap.NewWorkFlow.client.Widget;

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.KeyListener;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.ottocap.NewWorkFlow.client.Icons.Resources;
import com.ottocap.NewWorkFlow.client.MainScreen.MainTab;
import com.ottocap.NewWorkFlow.client.NewWorkFlow;
import com.ottocap.NewWorkFlow.client.Services.WorkFlowServiceAsync;
import com.ottocap.NewWorkFlow.client.StoredUser;

public class LoginDialog extends Window
{
  private Button loginbutton = new Button("Login", Resources.ICONS.key());
  private TextField<String> username = new TextField();
  private TextField<String> password = new TextField();
  private BuzzAsyncCallback<?> successcallback;
  
  protected void onRender(Element parent, int pos)
  {
    addButton(this.loginbutton);
    setClosable(false);
    super.onRender(parent, pos);
    this.loginbutton.addListener(com.extjs.gxt.ui.client.event.Events.OnClick, this.clicklistener);
    this.password.setPassword(true);
    this.username.setFieldLabel("Username");
    this.password.setFieldLabel("Password");
    this.password.addKeyListener(this.keylistener);
    
    this.username.setValue(Cookies.getCookie("username"));
    
    setMonitorWindowResize(true);
    setModal(true);
    
    if ((this.username.getValue() != null) && (!((String)this.username.getValue()).equals(""))) {
      setFocusWidget(this.password);
    } else {
      setFocusWidget(this.username);
    }
    
    setHeadingHtml("Login");
    
    FormHorizontalPanel2 row1 = new FormHorizontalPanel2();
    row1.add(this.username);
    FormHorizontalPanel2 row2 = new FormHorizontalPanel2();
    row2.add(this.password);
    
    add(row1);
    add(row2);
  }
  
  protected void onWindowResize(int width, int height)
  {
    super.onWindowResize(width, height);
    center();
  }
  
  public void show(BuzzAsyncCallback<?> successcallback) {
    this.successcallback = successcallback;
    this.password.setValue("");
    show();
  }
  
  private Listener<BaseEvent> clicklistener = new Listener()
  {
    public void handleEvent(BaseEvent be)
    {
      if (be.getSource().equals(LoginDialog.this.loginbutton)) {
        LoginDialog.this.loginbutton.disable();
        NewWorkFlow.get().getWorkFlowRPC().submitLogin((String)LoginDialog.this.username.getValue(), (String)LoginDialog.this.password.getValue(), LoginDialog.this.callback);
      }
    }
  };
  

  private KeyListener keylistener = new KeyListener() {
    public void componentKeyUp(ComponentEvent event) {
      if (event.getKeyCode() == 13) {
        LoginDialog.this.loginbutton.disable();
        NewWorkFlow.get().getWorkFlowRPC().submitLogin((String)LoginDialog.this.username.getValue(), (String)LoginDialog.this.password.getValue(), LoginDialog.this.callback);
      }
    }
  };
  

  private AsyncCallback<StoredUser> callback = new AsyncCallback()
  {
    public void onFailure(Throwable caught)
    {
      LoginDialog.this.loginbutton.enable();
      Info.display("Error", "Username/Password incorrect");
    }
    
    public void onSuccess(StoredUser result)
    {
      if (result == null) {
        LoginDialog.this.loginbutton.enable();
        Info.display("Error", "Username/Password incorrect");
      } else {
        NewWorkFlow.get().getStoredUser().setAccessLevel(result.getAccessLevel());
        NewWorkFlow.get().getStoredUser().setEmailAddress(result.getEmailAddress());
        NewWorkFlow.get().getMainTabPanel().getMainTab().updateEmail();
        
        Cookies.setCookie("username", (String)LoginDialog.this.username.getValue());
        LoginDialog.this.loginbutton.enable();
        LoginDialog.this.hide();
        LoginDialog.this.successcallback.doRetry();
      }
    }
  };
}
