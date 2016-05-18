package com.ottocap.NewWorkFlow.server.RPCService;

import com.ottocap.NewWorkFlow.client.ContainerInvoice.ContainerData.ContainerData;
import com.ottocap.NewWorkFlow.client.ContainerInvoice.ContainerData.ContainerDataContainer;
import javax.servlet.http.HttpSession;
import org.jooq.InsertOnDuplicateSetMoreStep;
import org.jooq.InsertSetMoreStep;
import org.jooq.impl.DSL;

public class JOOQ_SaveContainerInvoice
{
  private ContainerData containerdata;
  private HttpSession httpsession;
  
  public JOOQ_SaveContainerInvoice(ContainerData containerdata, HttpSession httpsession) throws Exception
  {
    this.containerdata = containerdata;
    this.httpsession = httpsession;
    checkSession();
    
    com.ottocap.NewWorkFlow.server.JOOQSQL.getInstance().create().insertInto(DSL.tableByName(new String[] { "containerdatamain" })).set(DSL.fieldByName(new String[] { "hiddenkey" }), containerdata.getHiddenKey()).set(DSL.fieldByName(new String[] { "containercount" }), Integer.valueOf(containerdata.getContainerCount())).set(DSL.fieldByName(new String[] { "invoicenumber" }), containerdata.getInvoiceNumber()).set(DSL.fieldByName(new String[] { "faxinvoicereceiveddate" }), containerdata.getFaxInvoiceReceivedDate()).set(DSL.fieldByName(new String[] { "originalinvoicereceiveddate" }), containerdata.getOriginalInvoiceReceivedDate()).set(DSL.fieldByName(new String[] { "bankdocssettlementdate" }), containerdata.getBankDocsSettlementDate()).set(DSL.fieldByName(new String[] { "invoicevalue" }), containerdata.getInvoiceValue()).set(DSL.fieldByName(new String[] { "purchaseordersshipped" }), containerdata.getPurchaseOrdersShipped()).set(DSL.fieldByName(new String[] { "status" }), containerdata.getStatus()).onDuplicateKeyUpdate().set(DSL.fieldByName(new String[] { "hiddenkey" }), containerdata.getHiddenKey()).set(DSL.fieldByName(new String[] { "containercount" }), Integer.valueOf(containerdata.getContainerCount()))
      .set(DSL.fieldByName(new String[] {"invoicenumber" }), containerdata.getInvoiceNumber()).set(DSL.fieldByName(new String[] { "faxinvoicereceiveddate" }), containerdata.getFaxInvoiceReceivedDate()).set(DSL.fieldByName(new String[] { "originalinvoicereceiveddate" }), containerdata.getOriginalInvoiceReceivedDate()).set(DSL.fieldByName(new String[] { "bankdocssettlementdate" }), containerdata.getBankDocsSettlementDate()).set(DSL.fieldByName(new String[] { "invoicevalue" }), containerdata.getInvoiceValue()).set(DSL.fieldByName(new String[] { "purchaseordersshipped" }), containerdata.getPurchaseOrdersShipped()).set(DSL.fieldByName(new String[] { "status" }), containerdata.getStatus()).execute();
    
    for (int i = 0; i < containerdata.getContainerCount(); i++)
    {



      com.ottocap.NewWorkFlow.server.JOOQSQL.getInstance().create().insertInto(DSL.tableByName(new String[] { "containerdatamain_container" })).set(DSL.fieldByName(new String[] { "hiddenkey" }), containerdata.getHiddenKey()).set(DSL.fieldByName(new String[] { "container" }), Integer.valueOf(i)).set(DSL.fieldByName(new String[] { "containernumber" }), containerdata.getContainer(i).getContainerNumber()).set(DSL.fieldByName(new String[] { "billoflandingnumber" }), containerdata.getContainer(i).getBillOfLandingNumber()).set(DSL.fieldByName(new String[] { "vesselname" }), containerdata.getContainer(i).getVesselName()).set(DSL.fieldByName(new String[] { "voyagenumber" }), containerdata.getContainer(i).getVoyageNumber()).set(DSL.fieldByName(new String[] { "portoforigin" }), containerdata.getContainer(i).getPortOfOrigin()).set(DSL.fieldByName(new String[] { "exitfactorydate" }), containerdata.getContainer(i).getExitFactoryDate()).set(DSL.fieldByName(new String[] { "estimatedtimedeparturedate" }), containerdata.getContainer(i).getEstimatedTimeDepartureDate()).set(DSL.fieldByName(new String[] { "estimatedtimearrivaldate" }), containerdata.getContainer(i).getEstimatedTimeArrivalDate()).set(DSL.fieldByName(new String[] { "tenplustworeceiveddate" }), containerdata.getContainer(i).getTenPlusTwoReceivedDate()).set(DSL.fieldByName(new String[] { "freightforwarder" }), containerdata.getContainer(i).getFreightForwarder()).set(DSL.fieldByName(new String[] { "lastfreeday" }), containerdata.getContainer(i).getLastFreeDay()).set(DSL.fieldByName(new String[] { "customsreleased" }), containerdata.getContainer(i).getCustomsReleased()).set(DSL.fieldByName(new String[] { "steamshipreleased" }), containerdata.getContainer(i).getSteamshipReleased()).set(DSL.fieldByName(new String[] { "pickedupfromport" }), containerdata.getContainer(i).getPickedUpFromPort()).set(DSL.fieldByName(new String[] { "scheduleddeliverytootto" }), containerdata.getContainer(i).getScheduledDeliveryToOtto()).set(DSL.fieldByName(new String[] { "numberofcartons" }), containerdata.getContainer(i).getNumberOfCartons()).set(DSL.fieldByName(new String[] { "oceancharges" }), containerdata.getContainer(i).getOceanCharges()).set(DSL.fieldByName(new String[] { "demurrage" }), containerdata.getContainer(i).getDemurrage()).set(DSL.fieldByName(new String[] { "storage" }), containerdata.getContainer(i).getStorage()).set(DSL.fieldByName(new String[] { "perdiem" }), containerdata.getContainer(i).getPerDiem()).set(DSL.fieldByName(new String[] { "exam" }), containerdata.getContainer(i).getExam()).set(DSL.fieldByName(new String[] { "ctffee" }), containerdata.getContainer(i).getCTFFee()).set(DSL.fieldByName(new String[] { "diversionfee" }), containerdata.getContainer(i).getDiversionFee()).set(DSL.fieldByName(new String[] { "comments" }), containerdata.getContainer(i).getComments()).onDuplicateKeyUpdate().set(DSL.fieldByName(new String[] { "hiddenkey" }), containerdata.getHiddenKey()).set(DSL.fieldByName(new String[] { "container" }), Integer.valueOf(i)).set(DSL.fieldByName(new String[] { "containernumber" }), containerdata.getContainer(i).getContainerNumber()).set(DSL.fieldByName(new String[] { "billoflandingnumber" }), containerdata.getContainer(i).getBillOfLandingNumber()).set(DSL.fieldByName(new String[] { "vesselname" }), containerdata.getContainer(i).getVesselName()).set(DSL.fieldByName(new String[] { "voyagenumber" }), containerdata.getContainer(i).getVoyageNumber()).set(DSL.fieldByName(new String[] { "portoforigin" }), containerdata.getContainer(i).getPortOfOrigin()).set(DSL.fieldByName(new String[] { "exitfactorydate" }), containerdata.getContainer(i).getExitFactoryDate()).set(DSL.fieldByName(new String[] { "estimatedtimedeparturedate" }), containerdata.getContainer(i).getEstimatedTimeDepartureDate()).set(DSL.fieldByName(new String[] { "estimatedtimearrivaldate" }), containerdata.getContainer(i).getEstimatedTimeArrivalDate()).set(DSL.fieldByName(new String[] { "tenplustworeceiveddate" }), containerdata.getContainer(i).getTenPlusTwoReceivedDate()).set(DSL.fieldByName(new String[] { "freightforwarder" }), containerdata.getContainer(i).getFreightForwarder()).set(DSL.fieldByName(new String[] { "lastfreeday" }), containerdata.getContainer(i).getLastFreeDay()).set(DSL.fieldByName(new String[] { "customsreleased" }), containerdata.getContainer(i).getCustomsReleased()).set(DSL.fieldByName(new String[] { "steamshipreleased" }), containerdata.getContainer(i).getSteamshipReleased()).set(DSL.fieldByName(new String[] { "pickedupfromport" }), containerdata.getContainer(i).getPickedUpFromPort()).set(DSL.fieldByName(new String[] { "scheduleddeliverytootto" }), containerdata.getContainer(i).getScheduledDeliveryToOtto()).set(DSL.fieldByName(new String[] { "numberofcartons" }), containerdata.getContainer(i).getNumberOfCartons()).set(DSL.fieldByName(new String[] { "oceancharges" }), containerdata.getContainer(i).getOceanCharges()).set(DSL.fieldByName(new String[] { "demurrage" }), containerdata.getContainer(i).getDemurrage()).set(DSL.fieldByName(new String[] { "storage" }), containerdata.getContainer(i).getStorage()).set(DSL.fieldByName(new String[] { "perdiem" }), containerdata.getContainer(i).getPerDiem()).set(DSL.fieldByName(new String[] { "exam" }), containerdata.getContainer(i).getExam()).set(DSL.fieldByName(new String[] { "ctffee" }), containerdata.getContainer(i).getCTFFee()).set(DSL.fieldByName(new String[] { "diversionfee" }), containerdata.getContainer(i).getDiversionFee()).set(DSL.fieldByName(new String[] { "comments" }), containerdata.getContainer(i).getComments()).execute();
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
