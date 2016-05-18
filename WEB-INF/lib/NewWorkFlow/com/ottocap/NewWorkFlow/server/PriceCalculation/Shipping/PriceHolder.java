package com.ottocap.NewWorkFlow.server.PriceCalculation.Shipping;

import java.util.TreeMap;










public class PriceHolder
{
  private TreeMap<String, Double> shippingcosts = new TreeMap();
  private double codprice = 0.0D;
  
  public PriceHolder(TreeMap<String, Double> shippingcosts, double codprice) {
    this.shippingcosts = shippingcosts;
    this.codprice = codprice;
  }
  
  public double getCODPrice() {
    return this.codprice;
  }
  
  public TreeMap<String, Double> getShippingCosts() {
    return this.shippingcosts;
  }
}
