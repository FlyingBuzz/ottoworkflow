package com.ottocap.NewWorkFlow.server.PriceCalculation.Shipping.UPS;

import java.util.ArrayList;




























public class RSSResponse
{
  public class Error
  {
    public String errorSeverity;
    public String errorCode;
    public String errorDescription;
    public String minimumRetrySeconds;
    
    public Error() {}
    
    public ArrayList<String> errorDigests = new ArrayList();
    
    public class ErrorLocation { public String errorLocationElementName;
      public String errorLocationAttributeName;
      
      public ErrorLocation() {} }
    
    public ArrayList<ErrorLocation> errorLocations = new ArrayList();
     }
  private TransactionReference transactionReference = new TransactionReference();
  private ArrayList<Error> errors = new ArrayList();
  
  private String responseStatusCode;
  
  private String responseStatusDescription;
  

  public ArrayList<Error> getErrors()
  {
    return this.errors;
  }
  
  public void setErrors(ArrayList<Error> errors) {
    this.errors = errors;
  }
  
  public String getResponseStatusCode() {
    return this.responseStatusCode;
  }
  
  public void setResponseStatusCode(String responseStatusCode) {
    this.responseStatusCode = responseStatusCode;
  }
  
  public String getResponseStatusDescription() {
    return this.responseStatusDescription;
  }
  
  public void setResponseStatusDescription(String responseStatusDescription) {
    this.responseStatusDescription = responseStatusDescription;
  }
  
  public TransactionReference getTransactionReference() {
    return this.transactionReference;
  }
  
  public void setTransactionReference(TransactionReference transactionReference) {
    this.transactionReference = transactionReference;
  }
  
  public class TransactionReference
  {
    public String customerContext;
    public String xpciVersion;
    
    public TransactionReference() {}
  }
}
