package com.ottocap.NewWorkFlow.client.DataHolder.ProductsInfo.Decoration;

import java.io.Serializable;
import java.util.TreeMap;

public class DecorationInfo implements Serializable
{
  private static final long serialVersionUID = 1L;
  private TreeMap<String, DecorationInfoLocations> thelocations = new TreeMap();
  private TreeMap<String, DecorationInfoNames> thenames = new TreeMap();
  



  public void addLocation(String location, DecorationInfoLocations declocation)
  {
    this.thelocations.put(location, declocation);
  }
  
  public DecorationInfoLocations getLocation(String location) {
    if (this.thelocations.containsKey(location)) {
      return (DecorationInfoLocations)this.thelocations.get(location);
    }
    return (DecorationInfoLocations)this.thelocations.get("Other");
  }
  
  public void addName(String name, DecorationInfoNames thename)
  {
    this.thenames.put(name, thename);
  }
  
  public DecorationInfoNames getName(String name) {
    if (this.thenames.containsKey(name)) {
      return (DecorationInfoNames)this.thenames.get(name);
    }
    return (DecorationInfoNames)this.thenames.get("Other");
  }
}
