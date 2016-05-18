package com.ottocap.NewWorkFlow.client.ContainerInvoice;

import com.extjs.gxt.ui.client.widget.TabItem;
import com.ottocap.NewWorkFlow.client.ContainerInvoice.ContainerData.ContainerData;
import com.ottocap.NewWorkFlow.client.ContainerInvoice.ContainerData.ContainerDataContainer;
import com.ottocap.NewWorkFlow.client.Icons.ResourceIcons;
import com.ottocap.NewWorkFlow.client.Icons.Resources;

public class ContainerPortalTabs extends com.extjs.gxt.ui.client.widget.TabPanel
{
  private ContainerData mycontainerdata;
  private ContainerDataContainer containerdatacontainer;
  private ContainerPortal containerportal;
  private TabItem shipmentinfo;
  private TabItem clearanceinfo;
  private TabItem deliveryinfo;
  private TabItem fees;
  private TabItem comments;
  
  public ContainerPortalTabs(ContainerData mycontainerdata, ContainerDataContainer containerdatacontainer, ContainerPortal containerportal)
  {
    this.mycontainerdata = mycontainerdata;
    this.containerdatacontainer = containerdatacontainer;
    this.containerportal = containerportal;
  }
  

  protected void onRender(com.google.gwt.user.client.Element parent, int index)
  {
    setTabPosition(com.extjs.gxt.ui.client.widget.TabPanel.TabPosition.BOTTOM);
    setBodyBorder(false);
    setTabScroll(true);
    super.onRender(parent, index);
    RunMethod();
  }
  
  public void RunMethod()
  {
    this.shipmentinfo = new TabItem("Shipment Information");
    this.clearanceinfo = new TabItem("Clearance Information");
    this.deliveryinfo = new TabItem("Delivery Information");
    this.fees = new TabItem("Fees");
    this.comments = new TabItem("Comments");
    
    this.shipmentinfo.setIcon(Resources.ICONS.cargoship_black());
    this.clearanceinfo.setIcon(Resources.ICONS.clock__pencil());
    this.deliveryinfo.setIcon(Resources.ICONS.truck());
    this.fees.setIcon(Resources.ICONS.price_tag());
    this.comments.setIcon(Resources.ICONS.notepad());
    
    this.shipmentinfo.add(new ContainerPortalTabsShipmentInfoLayout(this.containerdatacontainer, this));
    this.clearanceinfo.add(new ContainerPortalTabsClearanceInfoLayout(this.containerdatacontainer, this));
    this.deliveryinfo.add(new ContainerPortalTabsDeliveryInfoLayout(this.containerdatacontainer, this));
    this.fees.add(new ContainerPortalTabsFeesLayout(this.containerdatacontainer, this));
    this.comments.add(new ContainerPortalTabsCommentsLayout(this.containerdatacontainer, this));
    
    add(this.shipmentinfo);
    add(this.clearanceinfo);
    add(this.deliveryinfo);
    add(this.fees);
    add(this.comments);
  }
  
  public ContainerData getContainerData()
  {
    return this.mycontainerdata;
  }
  
  public ContainerDataContainer getContainerDataContainer() {
    return this.containerdatacontainer;
  }
  
  public ContainerPortal getContainerPortal() {
    return this.containerportal;
  }
}
