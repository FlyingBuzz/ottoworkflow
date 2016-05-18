package com.ottocap.NewWorkFlow.client.ContainerInvoice.ContainerData;

import java.io.Serializable;
import java.util.Date;














public class ContainerDataContainer
  extends ContainerDataShared
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  
  public String getContainerNumber()
  {
    return (String)get("containernumber", "");
  }
  
  public void setContainerNumber(String reason) {
    set("containernumber", reason);
  }
  
  public String getBillOfLandingNumber() {
    return (String)get("billoflandingnumber", "");
  }
  
  public void setBillOfLandingNumber(String billoflandingnumber) {
    set("billoflandingnumber", billoflandingnumber);
  }
  
  public String getVesselName() {
    return (String)get("vesselname", "");
  }
  
  public void setVesselName(String vesselname) {
    set("vesselname", vesselname);
  }
  
  public String getVoyageNumber() {
    return (String)get("voyagenumber", "");
  }
  
  public void setVoyageNumber(String voyagenumber) {
    set("voyagenumber", voyagenumber);
  }
  
  public String getPortOfOrigin() {
    return (String)get("portoforigin", "");
  }
  
  public void setPortOfOrigin(String portoforigin) {
    set("portoforigin", portoforigin);
  }
  
  public Date getExitFactoryDate() {
    return (Date)get("exitfactorydate");
  }
  
  public void setExitFactoryDate(Date exitfactorydate) {
    set("exitfactorydate", exitfactorydate);
  }
  
  public Date getEstimatedTimeDepartureDate() {
    return (Date)get("estimatedtimedeparturedate");
  }
  
  public void setEstimatedTimeDepartureDate(Date estimatedtimedeparturedate) {
    set("estimatedtimedeparturedate", estimatedtimedeparturedate);
  }
  
  public Date getEstimatedTimeArrivalDate() {
    return (Date)get("estimatedtimearrivaldate");
  }
  
  public void setEstimatedTimeArrivalDate(Date estimatedtimearrivaldate) {
    set("estimatedtimearrivaldate", estimatedtimearrivaldate);
  }
  
  public Date getTenPlusTwoReceivedDate() {
    return (Date)get("tenplustworeceiveddate");
  }
  
  public void setTenPlusTwoReceivedDate(Date tenplustworeceiveddate) {
    set("tenplustworeceiveddate", tenplustworeceiveddate);
  }
  
  public String getFreightForwarder() {
    return (String)get("freightforwarder", "");
  }
  
  public void setFreightForwarder(String freightforwarder) {
    set("freightforwarder", freightforwarder);
  }
  

  public Date getLastFreeDay()
  {
    return (Date)get("lastfreeday");
  }
  
  public void setLastFreeDay(Date lastfreeday) {
    set("lastfreeday", lastfreeday);
  }
  
  public Date getCustomsReleased() {
    return (Date)get("customsreleased");
  }
  
  public void setCustomsReleased(Date customsreleased) {
    set("customsreleased", customsreleased);
  }
  
  public Date getSteamshipReleased() {
    return (Date)get("steamshipreleased");
  }
  
  public void setSteamshipReleased(Date steamshipreleased) {
    set("steamshipreleased", steamshipreleased);
  }
  

  public Date getPickedUpFromPort()
  {
    return (Date)get("pickedupfromport");
  }
  
  public void setPickedUpFromPort(Date pickedupfromport) {
    set("pickedupfromport", pickedupfromport);
  }
  
  public Date getScheduledDeliveryToOtto() {
    return (Date)get("scheduleddeliverytootto");
  }
  
  public void setScheduledDeliveryToOtto(Date scheduleddeliverytootto) {
    set("scheduleddeliverytootto", scheduleddeliverytootto);
  }
  
  public Integer getNumberOfCartons() {
    return (Integer)get("numberofcartons");
  }
  
  public void setNumberOfCartons(Integer numberofcartons) {
    set("numberofcartons", numberofcartons);
  }
  

  public Double getOceanCharges()
  {
    return (Double)get("oceancharges");
  }
  
  public void setOceanCharges(Double oceancharges) {
    set("oceancharges", oceancharges);
  }
  
  public Double getDemurrage() {
    return (Double)get("demurrage");
  }
  
  public void setDemurrage(Double demurrage) {
    set("demurrage", demurrage);
  }
  
  public Double getStorage() {
    return (Double)get("storage");
  }
  
  public void setStorage(Double storage) {
    set("storage", storage);
  }
  
  public Double getPerDiem() {
    return (Double)get("perdiem");
  }
  
  public void setPerDiem(Double perdiem) {
    set("perdiem", perdiem);
  }
  
  public Double getExam() {
    return (Double)get("exam");
  }
  
  public void setExam(Double exam) {
    set("exam", exam);
  }
  
  public Double getCTFFee() {
    return (Double)get("ctffee");
  }
  
  public void setCTFFee(Double ctffee) {
    set("ctffee", ctffee);
  }
  
  public Double getDiversionFee() {
    return (Double)get("diversionfee");
  }
  
  public void setDiversionFee(Double diversionfee) {
    set("diversionfee", diversionfee);
  }
  

  public String getComments()
  {
    return (String)get("comments", "");
  }
  
  public void setComments(String comments) {
    set("comments", comments);
  }
}
