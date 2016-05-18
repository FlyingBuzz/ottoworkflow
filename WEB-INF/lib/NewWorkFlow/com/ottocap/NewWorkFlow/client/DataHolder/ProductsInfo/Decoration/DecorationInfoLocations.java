package com.ottocap.NewWorkFlow.client.DataHolder.ProductsInfo.Decoration;

import java.io.Serializable;
import java.util.ArrayList;

public class DecorationInfoLocations
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private boolean canadd = false;
  private ArrayList<String> locationnames = new ArrayList();
  


  public boolean getCanAdd()
  {
    return this.canadd;
  }
  
  public void addName(String name) {
    this.locationnames.add(name);
  }
  
  public ArrayList<String> getNames() {
    return this.locationnames;
  }
  
  public void setCanAdd(Boolean canadd) {
    this.canadd = canadd.booleanValue();
  }
}
