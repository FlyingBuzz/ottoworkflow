package com.ottocap.NewWorkFlow.server.PriceCalculation.Shipping.UPS;

import java.util.ArrayList;




































public class RSSRatedShipment
{
  public class BillingWeight
  {
    public UnitOfMeasurement unitOfMeasurement = new UnitOfMeasurement();
    public String weight;
    
    public BillingWeight() {}
    
    public class UnitOfMeasurement
    {
      public String code;
      public String description;
      
      public UnitOfMeasurement() {}
    }
  }
  
  public class HandlingChargeAmount
  {
    public String currencyCode;
    public String monetaryValue;
    
    public HandlingChargeAmount() {}
  }
  
  public class NegotiatedRates
  {
    public NegotiatedRates() {}
    
    public class NetSummaryCharges
    {
      public NetSummaryCharges() {}
      
      public class GrandTotal {
        public String currencyCode;
        public String monetaryValue;
        
        public GrandTotal() {}
      }
      
      public GrandTotal grandTotal = new GrandTotal();
       }
    public NetSummaryCharges netSummaryCharges = new NetSummaryCharges(); }
  
  private Service service = new Service();
  private BillingWeight billingWeight = new BillingWeight();
  private TransportationCharges transportationCharges = new TransportationCharges();
  private ServiceOptionsCharges serviceOptionsCharges = new ServiceOptionsCharges();
  private HandlingChargeAmount handlingChargeAmount = new HandlingChargeAmount();
  private TotalCharges totalCharges = new TotalCharges();
  private NegotiatedRates negotiatedRates = new NegotiatedRates();
  private String ratedShipmentWarning;
  private String guaranteedDaysToDelivery;
  private String scheduledDeliveryTime;
  private ArrayList<RSSRSRatedPackage> ratedPackages = new ArrayList();
  



  public NegotiatedRates getNegotiatedRates()
  {
    return this.negotiatedRates;
  }
  
  public void setNegotiatedRates(NegotiatedRates negotiatedRates) {
    this.negotiatedRates = negotiatedRates;
  }
  
  public BillingWeight getBillingWeight() {
    return this.billingWeight;
  }
  
  public void setBillingWeight(BillingWeight billingWeight) {
    this.billingWeight = billingWeight;
  }
  
  public String getGuaranteedDaysToDelivery() {
    return this.guaranteedDaysToDelivery;
  }
  
  public void setGuaranteedDaysToDelivery(String guaranteedDaysToDelivery) {
    this.guaranteedDaysToDelivery = guaranteedDaysToDelivery;
  }
  
  public HandlingChargeAmount getHandlingChargeAmount() {
    return this.handlingChargeAmount;
  }
  
  public void setHandlingChargeAmount(HandlingChargeAmount handlingChargeAmount) {
    this.handlingChargeAmount = handlingChargeAmount;
  }
  
  public ArrayList<RSSRSRatedPackage> getRatedPackages() {
    return this.ratedPackages;
  }
  
  public void setRatedPackages(ArrayList<RSSRSRatedPackage> ratedPackages) {
    this.ratedPackages = ratedPackages;
  }
  
  public String getRatedShipmentWarning() {
    return this.ratedShipmentWarning;
  }
  
  public void setRatedShipmentWarning(String ratedShipmentWarning) {
    this.ratedShipmentWarning = ratedShipmentWarning;
  }
  
  public String getScheduledDeliveryTime() {
    return this.scheduledDeliveryTime;
  }
  
  public void setScheduledDeliveryTime(String scheduledDeliveryTime) {
    this.scheduledDeliveryTime = scheduledDeliveryTime;
  }
  
  public Service getService() {
    return this.service;
  }
  
  public void setService(Service service) {
    this.service = service;
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
  
  public class Service
  {
    public String code;
    public String description;
    
    public Service() {}
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
