package com.ottocap.NewWorkFlow.client.Widget;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class BuzzAsyncCallback<T>
  implements AsyncCallback<T>
{
  public void onFailure(Throwable caught) {}
  
  public void doRetry() {}
  
  public void onSuccess(T result) {}
}
