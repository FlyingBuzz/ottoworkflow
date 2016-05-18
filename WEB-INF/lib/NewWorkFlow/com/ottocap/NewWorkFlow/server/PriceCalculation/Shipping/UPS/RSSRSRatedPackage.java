package com.ottocap.NewWorkFlow.server.PriceCalculation.Shipping.UPS;









public class RSSRSRatedPackage
{
  public class BillingWeight
  {
    public BillingWeight() {}
    








    public class UnitOfMeasurement
    {
      public String code;
      







      public String description;
      








      public UnitOfMeasurement() {}
    }
    







    public UnitOfMeasurement unitOfMeasurement = new UnitOfMeasurement();
    public String weight;
     }
  private TransportationCharges transportationCharges = new TransportationCharges();
  private ServiceOptionsCharges serviceOptionsCharges = new ServiceOptionsCharges();
  private TotalCharges totalCharges = new TotalCharges();
  private BillingWeight billingWeight = new BillingWeight();
  

  private String weight;
  

  public BillingWeight getBillingWeight()
  {
    return this.billingWeight;
  }
  
  public void setBillingWeight(BillingWeight billingWeight) {
    this.billingWeight = billingWeight;
  }
  
  public ServiceOptionsCharges getServiceOptionsCharges() {
    return this.serviceOptionsCharges;
  }
  
  public void setServiceOptionsCharges(ServiceOptionsCharges serviceOptionsCharges) {
    this.serviceOptionsCharges = serviceOptionsCharges;
  }
  
  public TotalCharges getTotalCharges() {
    return this.totalCharges;
  }
  
  public void setTotalCharges(TotalCharges totalCharges) {
    this.totalCharges = totalCharges;
  }
  
  public TransportationCharges getTransportationCharges() {
    return this.transportationCharges;
  }
  
  public void setTransportationCharges(TransportationCharges transportationCharges) {
    this.transportationCharges = transportationCharges;
  }
  
  public String getWeight() {
    return this.weight;
  }
  
  public void setWeight(String weight) {
    this.weight = weight;
  }
  
  public class ServiceOptionsCharges
  {
    public String currencyCode;
    public String monetaryValue;
    
    public ServiceOptionsCharges() {}
  }
  
  public class TotalCharges
  {
    public String currencyCode;
    public String monetaryValue;
    
    public TotalCharges() {}
  }
  
  public class TransportationCharges
  {
    public String currencyCode;
    public String monetaryValue;
    
    public TransportationCharges() {}
  }
}
