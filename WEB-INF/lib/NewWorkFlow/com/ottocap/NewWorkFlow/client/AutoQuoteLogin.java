package com.ottocap.NewWorkFlow.client;

import com.extjs.gxt.ui.client.widget.Info;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.ottocap.NewWorkFlow.client.Widget.BuzzAsyncCallback;

public class AutoQuoteLogin
{
  private BuzzAsyncCallback<?> callback;
  
  public AutoQuoteLogin(BuzzAsyncCallback<?> callback)
  {
    this.callback = callback;
    
    NewWorkFlow.get().getWorkFlowRPC().submitLogin("quote", "quotequote", this.quotelogincallback);
  }
  
  private AsyncCallback<StoredUser> quotelogincallback = new AsyncCallback()
  {
    public void onFailure(Throwable caught)
    {
      Info.display("Error", "Server Is Down");
    }
    
    public void onSuccess(StoredUser result)
    {
      if (result == null) {
        Info.display("Error", "Server Is Down");
      } else {
        NewWorkFlow.get().getStoredUser().setAccessLevel(result.getAccessLevel());
        NewWorkFlow.get().getStoredUser().setEmailAddress(result.getEmailAddress());
        NewWorkFlow.get().getMainTabPanel().getMainTab().updateEmail();
        
        com.google.gwt.user.client.Cookies.setCookie("username", "quote");
        AutoQuoteLogin.this.callback.doRetry();
      }
    }
  };
}
