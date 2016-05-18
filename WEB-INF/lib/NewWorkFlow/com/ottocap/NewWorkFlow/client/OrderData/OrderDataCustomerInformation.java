package com.ottocap.NewWorkFlow.client.OrderData;

import java.io.Serializable;






public class OrderDataCustomerInformation
  extends OrderDataShared
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  protected OrderDataAddress billinformation = new OrderDataAddress(OrderDataAddress.AddressType.BILLING);
  protected OrderDataAddress shipinformation = new OrderDataAddress(OrderDataAddress.AddressType.SHIPPING);
  
  public boolean getChangeHappened()
  {
    if ((this.billinformation.getChangeHappened()) || (this.shipinformation.getChangeHappened())) {
      return true;
    }
    return super.getChangeHappened();
  }
  
  public void setChangeHappened(boolean changehappened)
  {
    this.billinformation.setChangeHappened(changehappened);
    this.shipinformation.setChangeHappened(changehappened);
    super.setChangeHappened(changehappened);
  }
  
  public OrderDataCustomerInformation() {
    set("sameasbillingaddress", Boolean.valueOf(true));
  }
  
  public void setBillInformation(OrderDataAddress billinformation) {
    this.billinformation = billinformation;
  }
  
  public OrderDataAddress getBillInformation() {
    return this.billinformation;
  }
  
  public void setShipInformation(OrderDataAddress shipinformation) {
    this.shipinformation = shipinformation;
  }
  
  public OrderDataAddress getShipInformation() {
    return this.shipinformation;
  }
  
  public String getContactName() {
    return (String)get("contactname", "");
  }
  
  public void setContactName(String contactname) {
    set("contactname", contactname);
  }
  
  public Integer getEclipseAccountNumber() {
    return get("eclipseaccountnumber") != null ? Integer.valueOf((String)get("eclipseaccountnumber")) : null;
  }
  
  public void setEclipseAccountNumber(Integer eclipseaccountnumber) {
    set("eclipseaccountnumber", eclipseaccountnumber != null ? String.valueOf(eclipseaccountnumber) : null);
  }
  
  public String getPhone() {
    return (String)get("phone", "");
  }
  
  public void setPhone(String phone) {
    set("phone", phone);
  }
  
  public String getCompany() {
    return (String)get("company", "");
  }
  
  public void setCompany(String company) {
    set("company", company);
  }
  
  public String getCompanyLogo() {
    return (String)get("companylogo", "");
  }
  
  public void setCompanyLogo(String companylogo) {
    set("companylogo", companylogo);
  }
  
  public String getEmail() {
    return (String)get("email", "");
  }
  
  public void setEmail(String email) {
    set("email", email);
  }
  
  public void setFax(String fax) {
    set("fax", fax);
  }
  
  public String getFax() {
    return (String)get("fax", "");
  }
  
  public void setSameAsBillingAddress(boolean sameasbillingaddress) {
    set("sameasbillingaddress", Boolean.valueOf(sameasbillingaddress));
  }
  
  public boolean getSameAsBillingAddress() {
    return ((Boolean)get("sameasbillingaddress", Boolean.valueOf(true))).booleanValue();
  }
  
  public void setBlindShippingRequired(boolean blindshippingrequired) {
    set("blindshippingrequired", Boolean.valueOf(blindshippingrequired));
  }
  
  public boolean getBlindShippingRequired() {
    return ((Boolean)get("blindshippingrequired", Boolean.valueOf(false))).booleanValue();
  }
  
  public void setShipAttention(String shipattn) {
    set("shipattn", shipattn);
  }
  
  public String getShipAttention() {
    return (String)get("shipattn", "");
  }
  
  public void setTerms(String terms) {
    set("terms", terms);
  }
  
  public String getTerms() {
    return (String)get("terms", "");
  }
  
  public void setTaxExampt(boolean taxexampt) {
    set("taxexampt", Boolean.valueOf(taxexampt));
  }
  
  public boolean getTaxExampt() {
    return ((Boolean)get("taxexampt", Boolean.valueOf(false))).booleanValue();
  }
  
  public void setHaveDropShipment(boolean havedropshipment) {
    set("havedropshipment", Boolean.valueOf(havedropshipment));
  }
  
  public boolean getHaveDropShipment() {
    return ((Boolean)get("havedropshipment", Boolean.valueOf(false))).booleanValue();
  }
  
  public void setDropShipmentAmount(Integer dropshipmentamount) {
    set("dropshipmentamount", dropshipmentamount);
  }
  
  public Integer getDropShipmentAmount() {
    return (Integer)get("dropshipmentamount", Integer.valueOf(0));
  }
}
