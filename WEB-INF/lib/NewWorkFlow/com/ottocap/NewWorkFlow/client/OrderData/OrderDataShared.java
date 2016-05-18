package com.ottocap.NewWorkFlow.client.OrderData;

import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.data.PropertyChangeEvent;
import com.extjs.gxt.ui.client.util.Util;
import java.io.Serializable;












public abstract class OrderDataShared
  extends BaseModel
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  protected boolean changehappened = false;
  




  protected void notifyPropertyChanged(String name, Object value, Object oldValue)
  {
    if (!Util.equalWithNull(value, oldValue)) {
      setChangeHappened(true);
      notify(new PropertyChangeEvent(40, this, name, oldValue, value));
    }
  }
  
  public void setChangeHappened(boolean changehappened) {
    this.changehappened = changehappened;
  }
  
  public boolean getChangeHappened() {
    return this.changehappened;
  }
  
  public void checkForChange(Object currentitem, Object changeditem) {
    if ((currentitem == null) && (changeditem != null)) {
      this.changehappened = true;
    } else if ((currentitem != null) && (changeditem == null)) {
      this.changehappened = true;
    } else if (((currentitem != null) || (changeditem == null) || (!changeditem.equals(""))) && 
      ((currentitem == null) || (changeditem != null) || (!currentitem.equals(""))) && 
      (currentitem != null) && (changeditem != null) && (!currentitem.equals(changeditem))) {
      this.changehappened = true;
    }
  }
}
