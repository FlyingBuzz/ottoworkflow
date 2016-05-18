package com.ottocap.NewWorkFlow.server.PriceCalculation.Shipping;

import java.util.TreeMap;








public class Item
{
  String stylenumber;
  double weight;
  TreeMap<Integer, Integer> boxsizes;
  
  public Item(String stylenumber, double weight, TreeMap<Integer, Integer> boxsizes)
  {
    this.stylenumber = stylenumber;
    this.weight = weight;
    this.boxsizes = boxsizes;
  }
  
  public String getStyleNumber() {
    return this.stylenumber;
  }
  
  public double getWeight() {
    return this.weight;
  }
  
  public int getBoxCubicInch(int i) {
    if (this.boxsizes.containsKey(Integer.valueOf(i))) {
      return ((Integer)this.boxsizes.get(Integer.valueOf(i))).intValue();
    }
    return 0;
  }
  
  public boolean getBoxCubicInchContains(int i)
  {
    return this.boxsizes.containsKey(Integer.valueOf(i));
  }
}
