package com.ottocap.NewWorkFlow.server.PriceCalculation.Helper;










public class DomesticSampleDiscountCounter
{
  int freesample = 0;
  int discountsample = 0;
  double freesamplecost = 0.0D;
  double discountsamplecost = 0.0D;
  


  public void addFreeSample(double samplecost)
    throws Exception
  {
    if (this.freesamplecost == 0.0D) {
      this.freesamplecost = samplecost;
    } else if (this.freesamplecost != samplecost) {
      throw new Exception("Bad Sample Cost was " + this.freesamplecost + " now " + samplecost);
    }
    this.freesample += 1;
  }
  
  public void addDiscountSample(double samplecost) {
    this.discountsamplecost += samplecost;
    this.discountsample += 1;
  }
  
  public int getFreeSampleAmount() {
    return this.freesample;
  }
  
  public int getDiscountSampleAmount() {
    return this.discountsample;
  }
  
  public double getFreeSampleCost() {
    return this.freesamplecost;
  }
  
  public double getDiscountSampleCost() {
    return this.discountsamplecost;
  }
  
  public void setFreeSampleAmount(int amount) {
    this.freesample = amount;
  }
}
