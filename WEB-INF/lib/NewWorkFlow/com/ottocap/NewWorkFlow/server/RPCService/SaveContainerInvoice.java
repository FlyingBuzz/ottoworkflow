package com.ottocap.NewWorkFlow.server.RPCService;

import com.extjs.gxt.ui.client.core.FastMap;
import com.ottocap.NewWorkFlow.client.ContainerInvoice.ContainerData.ContainerData;
import com.ottocap.NewWorkFlow.client.ContainerInvoice.ContainerData.ContainerDataContainer;
import com.ottocap.NewWorkFlow.server.SQLTable;
import javax.servlet.http.HttpSession;

public class SaveContainerInvoice
{
  private ContainerData containerdata;
  private HttpSession httpsession;
  
  public SaveContainerInvoice(ContainerData containerdata, HttpSession httpsession, SQLTable sqltable)
    throws Exception
  {
    this.containerdata = containerdata;
    this.httpsession = httpsession;
    checkSession();
    
    FastMap<Object> datafields = new FastMap();
    datafields.put("hiddenkey", containerdata.getHiddenKey());
    
    datafields.put("containercount", Integer.valueOf(containerdata.getContainerCount()));
    datafields.put("invoicenumber", containerdata.getInvoiceNumber());
    datafields.put("faxinvoicereceiveddate", containerdata.getFaxInvoiceReceivedDate());
    datafields.put("originalinvoicereceiveddate", containerdata.getOriginalInvoiceReceivedDate());
    datafields.put("bankdocssettlementdate", containerdata.getBankDocsSettlementDate());
    datafields.put("invoicevalue", containerdata.getInvoiceValue());
    datafields.put("purchaseordersshipped", containerdata.getPurchaseOrdersShipped());
    datafields.put("status", containerdata.getStatus());
    
    if (containerdata.getHiddenKey() == null)
    {
      containerdata.setHiddenKey(Integer.valueOf(sqltable.insertRow("containerdatamain", datafields)));
    }
    else
    {
      FastMap<Object> datafieldsfilter = new FastMap();
      datafieldsfilter.put("hiddenkey", containerdata.getHiddenKey());
      sqltable.insertUpdateRow("containerdatamain", datafields, datafieldsfilter);
    }
    
    for (int i = 0; i < containerdata.getContainerCount(); i++) {
      FastMap<Object> datafields_containers = new FastMap();
      FastMap<Object> datafields_containersfilter = new FastMap();
      
      datafields_containersfilter.put("hiddenkey", containerdata.getHiddenKey());
      datafields_containersfilter.put("container", Integer.valueOf(i));
      
      datafields_containers.put("hiddenkey", containerdata.getHiddenKey());
      datafields_containers.put("container", Integer.valueOf(i));
      datafields_containers.put("containernumber", containerdata.getContainer(i).getContainerNumber());
      datafields_containers.put("billoflandingnumber", containerdata.getContainer(i).getBillOfLandingNumber());
      datafields_containers.put("vesselname", containerdata.getContainer(i).getVesselName());
      datafields_containers.put("voyagenumber", containerdata.getContainer(i).getVoyageNumber());
      datafields_containers.put("portoforigin", containerdata.getContainer(i).getPortOfOrigin());
      datafields_containers.put("exitfactorydate", containerdata.getContainer(i).getExitFactoryDate());
      datafields_containers.put("estimatedtimedeparturedate", containerdata.getContainer(i).getEstimatedTimeDepartureDate());
      datafields_containers.put("estimatedtimearrivaldate", containerdata.getContainer(i).getEstimatedTimeArrivalDate());
      datafields_containers.put("tenplustworeceiveddate", containerdata.getContainer(i).getTenPlusTwoReceivedDate());
      datafields_containers.put("freightforwarder", containerdata.getContainer(i).getFreightForwarder());
      datafields_containers.put("lastfreeday", containerdata.getContainer(i).getLastFreeDay());
      datafields_containers.put("customsreleased", containerdata.getContainer(i).getCustomsReleased());
      datafields_containers.put("steamshipreleased", containerdata.getContainer(i).getSteamshipReleased());
      datafields_containers.put("pickedupfromport", containerdata.getContainer(i).getPickedUpFromPort());
      datafields_containers.put("scheduleddeliverytootto", containerdata.getContainer(i).getScheduledDeliveryToOtto());
      datafields_containers.put("numberofcartons", containerdata.getContainer(i).getNumberOfCartons());
      datafields_containers.put("oceancharges", containerdata.getContainer(i).getOceanCharges());
      datafields_containers.put("demurrage", containerdata.getContainer(i).getDemurrage());
      datafields_containers.put("storage", containerdata.getContainer(i).getStorage());
      datafields_containers.put("perdiem", containerdata.getContainer(i).getPerDiem());
      datafields_containers.put("exam", containerdata.getContainer(i).getExam());
      datafields_containers.put("ctffee", containerdata.getContainer(i).getCTFFee());
      datafields_containers.put("diversionfee", containerdata.getContainer(i).getDiversionFee());
      datafields_containers.put("comments", containerdata.getContainer(i).getComments());
      
      sqltable.insertUpdateRow("containerdatamain_container", datafields_containers, datafields_containersfilter);
    }
    
    containerdata.setChangeHappened(false);
  }
  
  public ContainerData getContainerData() {
    return this.containerdata;
  }
  
  private void checkSession()
    throws Exception
  {
    if (this.httpsession.getAttribute("username") != null) {
      if (((Integer)this.httpsession.getAttribute("level")).intValue() != 6)
      {

        throw new Exception("Bad Session");
      }
    } else {
      throw new Exception("Session Expired");
    }
  }
}
