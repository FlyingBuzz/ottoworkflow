package com.ottocap.NewWorkFlow.client.ContainerInvoice;

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabItem.HeaderItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.extjs.gxt.ui.client.widget.toolbar.FillToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.ottocap.NewWorkFlow.client.ContainerInvoice.ContainerData.ContainerData;
import com.ottocap.NewWorkFlow.client.ContainerInvoice.ContainerData.ContainerDataContainer;
import com.ottocap.NewWorkFlow.client.Icons.ResourceIcons;
import com.ottocap.NewWorkFlow.client.Icons.Resources;
import com.ottocap.NewWorkFlow.client.NewWorkFlow;
import com.ottocap.NewWorkFlow.client.Services.WorkFlowServiceAsync;
import com.ottocap.NewWorkFlow.client.Widget.BuzzAsyncCallback;
import java.util.ArrayList;

public class ContainerInvoiceTab extends TabItem
{
  private ContainerData containerdata = null;
  private Button logoutbutton = new Button("<u>L</u>og Out", Resources.ICONS.door_open_out());
  private Button filebutton = new Button("<u>F</u>ile");
  private MenuItem savefile = new MenuItem("Save", Resources.ICONS.disk());
  private MenuItem closefile = new MenuItem("Close", Resources.ICONS.folder_horizontal());
  private TabPanel myportal = new TabPanel()
  {
    protected void onItemContextMenu(TabItem item, int x, int y) {
      if ((item instanceof ContainerPortal)) {
        if (this.closeContextMenu == null) {
          this.closeContextMenu = new Menu();
          MenuItem deletesetmenuitem = new MenuItem("Delete Container", new SelectionListener()
          {
            public void componentSelected(MenuEvent ce) {
              ContainerPortal item = (ContainerPortal)((Menu)ce.getContainer()).getData("tab");
              item.confirmdelete();
            }
          });
          this.closeContextMenu.add(deletesetmenuitem);
        }
        this.closeContextMenu.setData("tab", item);
        this.closeContextMenu.showAt(x, y);
      }
    }
  };
  private MessageBox wanttosavebox = new MessageBox();
  private boolean closenow = false;
  private TabItem containerinvoiceportlet = new TabItem("Invoice Information");
  private TabItem addcontainerbutton = new TabItem("Add Container");
  private ArrayList<ContainerPortal> mycontainers = new ArrayList();
  
  public ContainerInvoiceTab(ContainerData containerdata) {
    this.containerdata = containerdata;
    setClosable(true);
    
    setIcon(Resources.ICONS.document_overseas());
    
    this.logoutbutton.addSelectionListener(this.buttonlistener);
    this.filebutton.addSelectionListener(this.buttonlistener);
    this.savefile.addSelectionListener(this.menulistener);
    this.closefile.addSelectionListener(this.menulistener);
    
    this.wanttosavebox.setButtons("yesnocancel");
    this.wanttosavebox.setIcon(MessageBox.QUESTION);
    this.wanttosavebox.setTitleHtml("Save Changes?");
    this.wanttosavebox.addCallback(this.messageboxlistener);
    this.wanttosavebox.setMessage("You are closing a tab that has unsaved changes. Would you like to save your changes?");
    
    setTabHeader();
    setLayout(new RowLayout());
    addListener(Events.BeforeClose, this.closelistener);
    
    ToolBar ordertoolbar = new ToolBar();
    
    Menu filemenu = new Menu();
    filemenu.add(this.savefile);
    filemenu.add(this.closefile);
    
    this.filebutton.setMenu(filemenu);
    
    ordertoolbar.add(this.filebutton);
    ordertoolbar.add(new FillToolItem());
    ordertoolbar.add(this.logoutbutton);
    
    this.myportal.setTabPosition(com.extjs.gxt.ui.client.widget.TabPanel.TabPosition.BOTTOM);
    this.myportal.setTabScroll(true);
    
    this.containerinvoiceportlet.setScrollMode(com.extjs.gxt.ui.client.Style.Scroll.AUTO);
    this.containerinvoiceportlet.setIcon(Resources.ICONS.clock__pencil());
    this.containerinvoiceportlet.add(new ContainerInvoiceInfoLayout(containerdata, this));
    this.myportal.add(this.containerinvoiceportlet);
    
    int currentsetcoutner = 1;
    if (containerdata.getContainerCount() == 0) {
      containerdata.addContainer();
    }
    for (ContainerDataContainer currentcontainer : containerdata.getContainers()) {
      ContainerPortal mycontainerportal = new ContainerPortal(containerdata, currentcontainer, this);
      mycontainerportal.setText("Container " + currentsetcoutner++);
      mycontainerportal.setIcon(Resources.ICONS.t_shirt_print_gray());
      
      this.mycontainers.add(mycontainerportal);
      this.myportal.add(mycontainerportal);
    }
    
    this.addcontainerbutton.setIcon(Resources.ICONS.plus_button());
    this.addcontainerbutton.addListener(Events.BeforeSelect, this.clicklistener);
    this.addcontainerbutton.addListener(Events.Select, this.clicklistener);
    this.myportal.add(this.addcontainerbutton);
    
    add(ordertoolbar, new RowData(1.0D, -1.0D));
    add(this.myportal, new RowData(1.0D, 1.0D));
  }
  
  public ContainerPortal getContainerPortal(ContainerDataContainer currentcontainer)
  {
    for (TabItem theitem : this.myportal.getItems()) {
      if ((theitem instanceof ContainerPortal)) {
        ContainerPortal currentitem = (ContainerPortal)theitem;
        if (currentitem.getContainerDataContainer() == currentcontainer) {
          return currentitem;
        }
      }
    }
    return null;
  }
  
  public void addContainer() {
    addContainer(new ContainerDataContainer());
  }
  
  public void addContainer(ContainerDataContainer containerdatacontainer) {
    ContainerPortal mycontainerportal = new ContainerPortal(this.containerdata, this.containerdata.addContainer(containerdatacontainer), this);
    mycontainerportal.setText("Container " + this.containerdata.getContainers().size());
    mycontainerportal.setIcon(Resources.ICONS.t_shirt_print_gray());
    this.mycontainers.add(mycontainerportal);
    
    this.myportal.insert(mycontainerportal, this.myportal.getItemCount() - 1);
    this.myportal.setSelection(mycontainerportal);
  }
  
  private Listener<BaseEvent> clicklistener = new Listener()
  {
    public void handleEvent(BaseEvent be)
    {
      if (be.getSource().equals(ContainerInvoiceTab.this.addcontainerbutton.getTabPanel())) {
        be.setCancelled(true);
        ContainerInvoiceTab.this.addContainer();
      }
    }
  };
  
  public void removeContainer(ContainerPortal containerportal)
  {
    this.mycontainers.remove(containerportal);
    this.containerdata.removeContainer(containerportal.getContainerDataContainer());
    this.myportal.remove(containerportal);
    fixContainerTitle();
    if (this.containerdata.getContainerCount() == 0) {
      addContainer();
    }
  }
  
  public void fixContainerTitle() {
    int currentsetcounter = 1;
    for (ContainerPortal currentcontainer : this.mycontainers) {
      if (currentcontainer.getContainerDataContainer().getContainerNumber().equals("")) {
        currentcontainer.setText("Container " + currentsetcounter++);
      } else {
        currentcontainer.setText("Container " + currentsetcounter++ + " - " + currentcontainer.getContainerDataContainer().getContainerNumber());
      }
    }
  }
  
  private SelectionListener<MenuEvent> menulistener = new SelectionListener()
  {
    public void componentSelected(MenuEvent ce) {
      if (ce.getItem().equals(ContainerInvoiceTab.this.savefile)) {
        ContainerInvoiceTab.this.checkSaveFile();
      } else if (ce.getItem().equals(ContainerInvoiceTab.this.closefile)) {
        if (ContainerInvoiceTab.this.containerdata.getChangeHappened()) {
          ContainerInvoiceTab.this.askWantToSave();
        } else {
          ContainerInvoiceTab.this.doCloseNow();
        }
      }
    }
  };
  
  private void askWantToSave() {
    this.wanttosavebox.show();
  }
  
  private Listener<MessageBoxEvent> messageboxlistener = new Listener()
  {
    public void handleEvent(MessageBoxEvent be)
    {
      if (be.getMessageBox().equals(ContainerInvoiceTab.this.wanttosavebox)) {
        if (be.getButtonClicked().getHtml().equals("Yes")) {
          if (ContainerInvoiceTab.this.checkSaveFile()) {
            ContainerInvoiceTab.this.doCloseNow();
          }
        } else if (be.getButtonClicked().getHtml().equals("No")) {
          ContainerInvoiceTab.this.doCloseNow();
        }
      }
    }
  };
  


  private boolean checkSaveFile()
  {
    if (this.containerdata.getInvoiceNumber().equals("")) {
      MessageBox.alert("Error", "Invoice number is required", null);
      this.myportal.setSelection(this.containerinvoiceportlet);
      return false;
    }
    NewWorkFlow.get().getWorkFlowRPC().saveContainerInvoice(this.containerdata, this.saverodercallback);
    return true;
  }
  
  private BuzzAsyncCallback<Integer> saverodercallback = new BuzzAsyncCallback()
  {
    public void onFailure(Throwable caught)
    {
      NewWorkFlow.get().reLogin2(caught, ContainerInvoiceTab.this.saverodercallback);
    }
    
    public void doRetry()
    {
      NewWorkFlow.get().getWorkFlowRPC().saveContainerInvoice(ContainerInvoiceTab.this.containerdata, ContainerInvoiceTab.this.saverodercallback);
    }
    
    public void onSuccess(Integer result)
    {
      if (ContainerInvoiceTab.this.containerdata.getHiddenKey() == null) {
        ContainerInvoiceTab.this.containerdata.setHiddenKey(result);
      }
      ContainerInvoiceTab.this.containerdata.setChangeHappened(false);
      Info.display("Save Successful", "The changes have been saved");
      ContainerInvoiceTab.this.setTabHeader();
    }
  };
  

  private SelectionListener<ButtonEvent> buttonlistener = new SelectionListener()
  {
    public void componentSelected(ButtonEvent be) {
      if (be.getButton().equals(ContainerInvoiceTab.this.logoutbutton)) {
        NewWorkFlow.get().doLogOut();
      } else if (be.getButton().equals(ContainerInvoiceTab.this.filebutton)) {
        ContainerInvoiceTab.this.savefile.setEnabled(ContainerInvoiceTab.this.containerdata.getChangeHappened());
      }
    }
  };
  
  private void doCloseNow() {
    this.closenow = true;
    close();
  }
  
  private Listener<BaseEvent> closelistener = new Listener()
  {
    public void handleEvent(BaseEvent be)
    {
      if ((!ContainerInvoiceTab.this.closenow) && (ContainerInvoiceTab.this.containerdata.getChangeHappened())) {
        ContainerInvoiceTab.this.askWantToSave();
        be.setCancelled(true);
      }
    }
  };
  
  public void setTabHeader()
  {
    if (!this.closenow) {
      if (!this.containerdata.getInvoiceNumber().equals("")) {
        getHeader().setText("I#: " + this.containerdata.getInvoiceNumber());
      } else {
        getHeader().setText("New Invoice");
      }
    }
  }
  
  public ContainerData getContainerInvoiceData()
  {
    return this.containerdata;
  }
  
  public Integer getHiddenKey() {
    return this.containerdata.getHiddenKey();
  }
}
