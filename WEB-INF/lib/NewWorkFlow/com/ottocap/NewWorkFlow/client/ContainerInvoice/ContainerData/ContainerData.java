package com.ottocap.NewWorkFlow.client.ContainerInvoice.ContainerData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;






public class ContainerData
  extends ContainerDataShared
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  protected ArrayList<ContainerDataContainer> thecontainers = new ArrayList();
  
  public ContainerDataContainer addContainer() {
    this.changehappened = true;
    ContainerDataContainer newcontainer = new ContainerDataContainer();
    this.thecontainers.add(newcontainer);
    return newcontainer;
  }
  
  public ContainerDataContainer addContainer(ContainerDataContainer newcontainer) {
    this.changehappened = true;
    this.thecontainers.add(newcontainer);
    return newcontainer;
  }
  
  public void removeContainer(int i) {
    this.changehappened = true;
    this.thecontainers.remove(i);
  }
  
  public void removeContainer(ContainerDataContainer thecontainer) {
    this.changehappened = true;
    this.thecontainers.remove(thecontainer);
  }
  
  public ArrayList<ContainerDataContainer> getContainers() {
    return this.thecontainers;
  }
  
  public ContainerDataContainer getContainer(int i) {
    return (ContainerDataContainer)this.thecontainers.get(i);
  }
  
  public int getContainerIndex(ContainerDataContainer thecontainer) {
    return this.thecontainers.indexOf(thecontainer);
  }
  
  public int getContainerCount() {
    return this.thecontainers.size();
  }
  
  public void moveContainer(int startpos, int newpos) {
    if ((startpos < newpos) || (startpos > newpos)) {
      ContainerDataContainer mycontainer = (ContainerDataContainer)this.thecontainers.get(startpos);
      this.thecontainers.remove(startpos);
      this.thecontainers.add(newpos, mycontainer);
      this.changehappened = true;
    }
  }
  

  public boolean getChangeHappened()
  {
    for (ContainerDataContainer currentcontainer : this.thecontainers) {
      if (currentcontainer.getChangeHappened()) {
        return true;
      }
    }
    
    return super.getChangeHappened();
  }
  
  public void setChangeHappened(boolean changehappened)
  {
    for (ContainerDataContainer currentcontainer : this.thecontainers) {
      currentcontainer.setChangeHappened(changehappened);
    }
    super.setChangeHappened(changehappened);
  }
  


  public Integer getHiddenKey()
  {
    return (Integer)get("hiddenkey");
  }
  
  public void setHiddenKey(Integer hiddenkey) {
    set("hiddenkey", hiddenkey);
  }
  
  public String getInvoiceNumber() {
    return (String)get("invoicenumber", "");
  }
  
  public void setInvoiceNumber(String invoicenumber) {
    set("invoicenumber", invoicenumber);
  }
  
  public Date getFaxInvoiceReceivedDate() {
    return (Date)get("faxinvoicereceiveddate");
  }
  
  public void setFaxInvoiceReceivedDate(Date faxinvoicereceiveddate) {
    set("faxinvoicereceiveddate", faxinvoicereceiveddate);
  }
  
  public Date getOriginalInvoiceReceivedDate() {
    return (Date)get("originalinvoicereceiveddate");
  }
  
  public void setOriginalInvoiceReceivedDate(Date originalinvoicereceiveddate) {
    set("originalinvoicereceiveddate", originalinvoicereceiveddate);
  }
  
  public Date getBankDocsSettlementDate() {
    return (Date)get("bankdocssettlementdate");
  }
  
  public void setBankDocsSettlementDate(Date bankdocssettlementdate) {
    set("bankdocssettlementdate", bankdocssettlementdate);
  }
  
  public Double getInvoiceValue() {
    return (Double)get("invoicevalue");
  }
  
  public void setInvoiceValue(Double invoicevalue) {
    set("invoicevalue", invoicevalue);
  }
  
  public String getPurchaseOrdersShipped() {
    return (String)get("purchaseordersshipped", "");
  }
  
  public void setPurchaseOrdersShipped(String purchaseordersshipped) {
    set("purchaseordersshipped", purchaseordersshipped.toUpperCase());
  }
  
  public String getStatus() {
    return (String)get("status", "");
  }
  
  public void setStatus(String status) {
    set("status", status);
  }
}
