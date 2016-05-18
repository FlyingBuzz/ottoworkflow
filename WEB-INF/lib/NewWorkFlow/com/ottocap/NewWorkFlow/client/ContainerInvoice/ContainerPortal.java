package com.ottocap.NewWorkFlow.client.ContainerInvoice;

import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.ottocap.NewWorkFlow.client.ContainerInvoice.ContainerData.ContainerData;
import com.ottocap.NewWorkFlow.client.ContainerInvoice.ContainerData.ContainerDataContainer;

public class ContainerPortal extends TabItem
{
  private ContainerData mycontainerdata;
  private ContainerDataContainer containerdatacontainer;
  private ContainerPortalTabs containerportaltabs;
  private boolean deletenow = false;
  private MessageBox deletecontainerbox = new MessageBox();
  private ContainerInvoiceTab containerinvoicetab;
  
  public ContainerPortal(ContainerData mycontainerdata, ContainerDataContainer containerdatacontainer, ContainerInvoiceTab containerinvoicetab) {
    this.containerinvoicetab = containerinvoicetab;
    this.mycontainerdata = mycontainerdata;
    this.containerdatacontainer = containerdatacontainer;
    setScrollMode(Style.Scroll.AUTO);
    setClosable(true);
    addListener(Events.BeforeClose, this.closelistener);
    
    this.deletecontainerbox.setButtons("yesno");
    this.deletecontainerbox.setIcon(MessageBox.QUESTION);
    this.deletecontainerbox.setTitleHtml("Are you sure?");
    this.deletecontainerbox.addCallback(this.messageboxlistener);
    
    setLayout(new RowLayout());
    this.containerportaltabs = new ContainerPortalTabs(mycontainerdata, containerdatacontainer, this);
    add(this.containerportaltabs, new RowData(1.0D, 1.0D));
  }
  

  public ContainerDataContainer getContainerDataContainer()
  {
    return this.containerdatacontainer;
  }
  
  public ContainerInvoiceTab getContainerInvoiceTab() {
    return this.containerinvoicetab;
  }
  
  public ContainerPortalTabs getContainerPortalTabs() {
    return this.containerportaltabs;
  }
  
  private void doCloseNow() {
    this.deletenow = true;
    this.containerinvoicetab.removeContainer(this);
  }
  
  public void confirmdelete() {
    this.deletecontainerbox.setMessage("Are you sure you want to delete Container " + (this.mycontainerdata.getContainerIndex(this.containerdatacontainer) + 1));
    this.deletecontainerbox.show();
  }
  
  private Listener<BaseEvent> closelistener = new Listener()
  {
    public void handleEvent(BaseEvent be)
    {
      if (!ContainerPortal.this.deletenow) {
        ContainerPortal.this.confirmdelete();
        be.setCancelled(true);
      }
    }
  };
  

  private Listener<MessageBoxEvent> messageboxlistener = new Listener()
  {
    public void handleEvent(MessageBoxEvent be)
    {
      if ((be.getMessageBox().equals(ContainerPortal.this.deletecontainerbox)) && 
        (be.getButtonClicked().getHtml().equals("Yes"))) {
        ContainerPortal.this.doCloseNow();
      }
    }
  };
}
