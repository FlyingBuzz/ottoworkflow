package com.ottocap.NewWorkFlow.client.OnlineWorkflow;

import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.core.FastMap;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Html;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.CenterLayout;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.ottocap.NewWorkFlow.client.DataHolder.ProductsInfo.Decoration.DecorationInfoNames;
import com.ottocap.NewWorkFlow.client.DataHolder.Stores.DataStores;
import com.ottocap.NewWorkFlow.client.DataHolder.Stores.NewStyleStore;
import com.ottocap.NewWorkFlow.client.NewWorkFlow;
import com.ottocap.NewWorkFlow.client.OnlineWorkflow.Widget.BlackLineBar;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData.OrderType;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataItem;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogo;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogoDecoration;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataSet;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataVendorInformation;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataVendors;
import com.ottocap.NewWorkFlow.client.Services.WorkFlowServiceAsync;
import com.ottocap.NewWorkFlow.client.Widget.BuzzAsyncCallback;
import java.util.ArrayList;
import java.util.TreeMap;

public class InstantQuoteMainScreen extends com.extjs.gxt.ui.client.widget.ContentPanel
{
  private OrderData orderdata;
  private PushButton menu1_productanddesigninfo = new PushButton();
  private PushButton menu2_customerinfo = new PushButton();
  private PushButton menu3_revieworder = new PushButton();
  private PushButton menu4_submitorder = new PushButton();
  private PushButton menu5_viewcart = new PushButton();
  private OnlineType onlinetype;
  private CurrentSection currentsection = CurrentSection.PRODUCTDESIGNINFO;
  private LayoutContainer mainmiddlecontainer = new LayoutContainer();
  private ArrayList<OnlineProductDesignTab> onlineproductdesignlist = new ArrayList();
  private OnlineCustomerInfoTab onlinecustomerinfotab;
  private int currentset = 0;
  
  private PopupPanel myitems = new PopupPanel(true);
  private FlexTable viewcartchoices = new FlexTable();
  private DecoratorPanel myroundpopup = new DecoratorPanel();
  
  public static enum OnlineType {
    QUOTE,  ORDER,  VIRTUALSAMPLE;
  }
  
  public static enum CurrentSection {
    PRODUCTDESIGNINFO,  CUSTOMERINFO,  REVIEWQUOTE,  SAVESUBMIT;
  }
  
  public InstantQuoteMainScreen(OrderData orderdata, OnlineType onlinetype) {
    setBodyBorder(false);
    setFrame(false);
    setHeaderVisible(false);
    
    this.orderdata = orderdata;
    this.onlinetype = onlinetype;
    
    this.menu1_productanddesigninfo.addClickHandler(this.clickhandler);
    this.menu2_customerinfo.addClickHandler(this.clickhandler);
    this.menu3_revieworder.addClickHandler(this.clickhandler);
    this.menu4_submitorder.addClickHandler(this.clickhandler);
    this.menu5_viewcart.addClickHandler(this.clickhandler);
    
    doStep(1);
    
    this.menu5_viewcart.setStylePrimaryName("viewcart-PushButton");
    
    this.myroundpopup.setStyleName("ViewCartColorCorner");
    this.viewcartchoices.setStyleName("ViewCartColor");
    this.myroundpopup.setWidget(this.viewcartchoices);
    this.viewcartchoices.addClickHandler(this.clickhandler);
    this.viewcartchoices.setWidth("147px");
    this.myitems.add(this.myroundpopup);
  }
  

  protected void onRender(Element parent, int pos)
  {
    super.onRender(parent, pos);
    
    doTheLayout();
  }
  
  private void doTheLayout()
  {
    setLayout(new com.extjs.gxt.ui.client.widget.layout.BorderLayout());
    
    doTopPanel();
    
    doMainPanel();
    
    doBottomPanel();
  }
  
  private void doMainPanel()
  {
    BorderLayoutData centerData = new BorderLayoutData(Style.LayoutRegion.CENTER);
    centerData.setMargins(new com.extjs.gxt.ui.client.util.Margins(0));
    
    this.mainmiddlecontainer.setStyleAttribute("background-color", "#FFFFFF");
    


    this.mainmiddlecontainer.setScrollMode(com.extjs.gxt.ui.client.Style.Scroll.AUTO);
    
    add(this.mainmiddlecontainer, centerData);
  }
  
  private void doTopPanel()
  {
    BorderLayoutData northData = new BorderLayoutData(Style.LayoutRegion.NORTH, 160.0F);
    
    LayoutContainer layoutcontainer = new LayoutContainer();
    layoutcontainer.setLayout(new com.extjs.gxt.ui.client.widget.layout.RowLayout(com.extjs.gxt.ui.client.Style.Orientation.VERTICAL));
    
    Html redbar = new Html(" ");
    
    redbar.setStyleAttribute("background-color", "#b51820");
    redbar.setStyleAttribute("padding-top", "5px");
    redbar.setStyleAttribute("padding-bottom", "5px");
    
    layoutcontainer.add(redbar, new RowData(1.0D, -1.0D));
    
    Image logo = new Image();
    logo.setSize("900px", "89px");
    if ((this.orderdata.getOrderType() == OrderData.OrderType.DOMESTIC) && (this.onlinetype == OnlineType.QUOTE)) {
      logo.setUrl(NewWorkFlow.filepath + "images/instantquote/" + "domesticuserheaderlogo.gif");
    } else if ((this.orderdata.getOrderType() == OrderData.OrderType.OVERSEAS) && (this.onlinetype == OnlineType.QUOTE)) {
      logo.setUrl(NewWorkFlow.filepath + "images/instantquote/" + "overseasuserheaderlogo.gif");
    } else if ((this.orderdata.getOrderType() == OrderData.OrderType.DOMESTIC) && (this.onlinetype == OnlineType.ORDER)) {
      logo.setUrl(NewWorkFlow.filepath + "images/instantquote/" + "domesticuserheaderlogoorder.gif");
    } else if ((this.orderdata.getOrderType() == OrderData.OrderType.OVERSEAS) && (this.onlinetype == OnlineType.ORDER)) {
      logo.setUrl(NewWorkFlow.filepath + "images/instantquote/" + "overseasuserheaderlogoorder.gif");
    } else if ((this.orderdata.getOrderType() == OrderData.OrderType.DOMESTIC) && (this.onlinetype == OnlineType.VIRTUALSAMPLE)) {
      logo.setUrl(NewWorkFlow.filepath + "images/instantquote/" + "domesticuserheaderlogovirtualsample.gif");
    } else if ((this.orderdata.getOrderType() == OrderData.OrderType.OVERSEAS) && (this.onlinetype == OnlineType.VIRTUALSAMPLE)) {
      logo.setUrl(NewWorkFlow.filepath + "images/instantquote/" + "overseasuserheaderlogovirtualsample.gif");
    } else {
      logo.setUrl(NewWorkFlow.filepath + "images/instantquote/" + "domesticuserheaderlogo.gif");
    }
    
    LayoutContainer thelogocontainer = new LayoutContainer();
    thelogocontainer.setStyleAttribute("background-color", "#FFFFFF");
    thelogocontainer.setLayout(new CenterLayout());
    
    thelogocontainer.add(logo);
    
    layoutcontainer.add(thelogocontainer, new RowData(1.0D, 90.0D));
    

    LayoutContainer barcontainer = new LayoutContainer();
    barcontainer.setStyleAttribute("background-color", "#FFFFFF");
    barcontainer.setLayout(new CenterLayout());
    
    barcontainer.add(new BlackLineBar(1000));
    
    layoutcontainer.add(barcontainer, new RowData(1.0D, 10.0D));
    

    LayoutContainer menubarcontainer = new LayoutContainer();
    menubarcontainer.setStyleAttribute("background-color", "#FFFFFF");
    menubarcontainer.setLayout(new CenterLayout());
    
    HorizontalPanel menubarrow = new HorizontalPanel();
    
    menubarrow.add(this.menu1_productanddesigninfo);
    if (this.onlinetype != OnlineType.VIRTUALSAMPLE) {
      menubarrow.add(this.menu2_customerinfo);
    }
    menubarrow.add(this.menu3_revieworder);
    menubarrow.add(this.menu4_submitorder);
    menubarrow.add(this.menu5_viewcart);
    
    menubarcontainer.add(menubarrow);
    
    layoutcontainer.add(menubarcontainer, new RowData(1.0D, 40.0D));
    

    LayoutContainer barcontainer1 = new LayoutContainer();
    barcontainer1.setStyleAttribute("background-color", "#FFFFFF");
    barcontainer1.setLayout(new CenterLayout());
    
    barcontainer1.add(new BlackLineBar(1000));
    
    layoutcontainer.add(barcontainer1, new RowData(1.0D, 10.0D));
    

    add(layoutcontainer, northData);
  }
  

  private void doBottomPanel()
  {
    BorderLayoutData southData = new BorderLayoutData(Style.LayoutRegion.SOUTH, 27.0F);
    String currenttype;
    String currenttype;
    if ((this.orderdata.getOrderType() == OrderData.OrderType.DOMESTIC) && (this.onlinetype == OnlineType.QUOTE)) {
      currenttype = "2013 OTTO INTERNATIONAL, INC. DOMESTIC QUOTATION"; } else { String currenttype;
      if ((this.orderdata.getOrderType() == OrderData.OrderType.OVERSEAS) && (this.onlinetype == OnlineType.QUOTE)) {
        currenttype = "2013 OTTO INTERNATIONAL, INC. OVERSEAS QUOTATION"; } else { String currenttype;
        if ((this.orderdata.getOrderType() == OrderData.OrderType.DOMESTIC) && (this.onlinetype == OnlineType.ORDER)) {
          currenttype = "2013 OTTO INTERNATIONAL, INC. DOMESTIC ORDER"; } else { String currenttype;
          if ((this.orderdata.getOrderType() == OrderData.OrderType.OVERSEAS) && (this.onlinetype == OnlineType.ORDER)) {
            currenttype = "2013 OTTO INTERNATIONAL, INC. OVERSEAS ORDER"; } else { String currenttype;
            if ((this.orderdata.getOrderType() == OrderData.OrderType.DOMESTIC) && (this.onlinetype == OnlineType.VIRTUALSAMPLE)) {
              currenttype = "2013 OTTO INTERNATIONAL, INC. DOMESTIC VIRTUAL SAMPLE REQUEST"; } else { String currenttype;
              if ((this.orderdata.getOrderType() == OrderData.OrderType.OVERSEAS) && (this.onlinetype == OnlineType.VIRTUALSAMPLE)) {
                currenttype = "2013 OTTO INTERNATIONAL, INC. OVERSEAS VIRTUAL SAMPLE REQUEST";
              } else
                currenttype = "2013 OTTO INTERNATIONAL, INC. DOMESTIC QUOTATION";
            }
          } } } }
    Label bottomlabel = new Label(currenttype);
    bottomlabel.setStyleAttribute("background-color", "#b51820");
    bottomlabel.setStyleAttribute("padding-top", "3px");
    bottomlabel.setStyleAttribute("text-align", "center");
    bottomlabel.setStyleAttribute("color", "#FFFFFF");
    bottomlabel.setStyleAttribute("letter-spacing", "5px");
    bottomlabel.setStyleAttribute("word-spacing", "10px");
    bottomlabel.setStyleAttribute("font-size", "16px");
    
    add(bottomlabel, southData);
  }
  
  private void doButtonSection()
  {
    this.menu1_productanddesigninfo.setStylePrimaryName("productinfo-PushButton");
    
    if (this.onlinetype == OnlineType.QUOTE) {
      this.menu2_customerinfo.setStylePrimaryName("customerinfo-PushButton");
      this.menu3_revieworder.setStylePrimaryName("reviewquote-PushButton");
      this.menu4_submitorder.setStylePrimaryName("savequote-PushButton");
    } else if (this.onlinetype == OnlineType.ORDER) {
      this.menu2_customerinfo.setStylePrimaryName("customerinfoorder-PushButton");
      this.menu3_revieworder.setStylePrimaryName("revieworder-PushButton");
      this.menu4_submitorder.setStylePrimaryName("saveorder-PushButton");
    } else if (this.onlinetype == OnlineType.VIRTUALSAMPLE) {
      this.menu2_customerinfo.setStylePrimaryName("customerinfovirtualsample-PushButton");
      this.menu3_revieworder.setStylePrimaryName("reviewvirtualsample-PushButton");
      this.menu4_submitorder.setStylePrimaryName("saveordervirtualsample-PushButton");
    } else {
      this.menu2_customerinfo.setStylePrimaryName("customerinfo-PushButton");
      this.menu3_revieworder.setStylePrimaryName("reviewquote-PushButton");
      this.menu4_submitorder.setStylePrimaryName("savequote-PushButton");
    }
    
    if (this.currentsection == CurrentSection.PRODUCTDESIGNINFO) {
      this.menu1_productanddesigninfo.setStylePrimaryName("productinfoselected-PushButton");
    } else if (this.currentsection == CurrentSection.CUSTOMERINFO) {
      if (this.onlinetype == OnlineType.QUOTE) {
        this.menu2_customerinfo.setStylePrimaryName("customerinfoselected-PushButton");
      } else if (this.onlinetype == OnlineType.ORDER) {
        this.menu2_customerinfo.setStylePrimaryName("customerinfoorderselected-PushButton");
      } else if (this.onlinetype == OnlineType.VIRTUALSAMPLE) {
        this.menu2_customerinfo.setStylePrimaryName("customerinfovirtualsampleselected-PushButton");
      } else {
        this.menu2_customerinfo.setStylePrimaryName("customerinfoselected-PushButton");
      }
    } else if (this.currentsection == CurrentSection.REVIEWQUOTE) {
      if (this.onlinetype == OnlineType.QUOTE) {
        this.menu3_revieworder.setStylePrimaryName("reviewquoteselected-PushButton");
      } else if (this.onlinetype == OnlineType.ORDER) {
        this.menu3_revieworder.setStylePrimaryName("revieworderselected-PushButton");
      } else if (this.onlinetype == OnlineType.VIRTUALSAMPLE) {
        this.menu3_revieworder.setStylePrimaryName("reviewvirtualsampleselected-PushButton");
      } else {
        this.menu3_revieworder.setStylePrimaryName("reviewquoteselected-PushButton");
      }
    } else if (this.currentsection == CurrentSection.SAVESUBMIT) {
      if (this.onlinetype == OnlineType.QUOTE) {
        this.menu4_submitorder.setStylePrimaryName("savequoteselected-PushButton");
      } else if (this.onlinetype == OnlineType.ORDER) {
        this.menu4_submitorder.setStylePrimaryName("saveorderselected-PushButton");
      } else if (this.onlinetype == OnlineType.VIRTUALSAMPLE) {
        this.menu4_submitorder.setStylePrimaryName("savevirtualsampleselected-PushButton");
      } else {
        this.menu4_submitorder.setStylePrimaryName("savequoteselected-PushButton");
      }
    }
  }
  
  private void setMiddleContainer(CurrentSection currentsection)
  {
    if (currentsection == CurrentSection.PRODUCTDESIGNINFO) {
      this.mainmiddlecontainer.removeAll();
      OnlineProductDesignTab onlineproductdesigntab;
      try {
        onlineproductdesigntab = (OnlineProductDesignTab)this.onlineproductdesignlist.get(this.currentset);
      } catch (Exception e) {
        OrderDataSet theset;
        try {
          OnlineProductDesignTab onlineproductdesigntab;
          theset = this.orderdata.getSet(this.currentset);
        } catch (Exception e1) { OrderDataSet theset;
          theset = this.orderdata.addSet();
        }
        onlineproductdesigntab = new OnlineProductDesignTab(this.orderdata, theset, this);
        this.onlineproductdesignlist.add(onlineproductdesigntab);
      }
      
      this.mainmiddlecontainer.add(onlineproductdesigntab);
      this.mainmiddlecontainer.layout();
    } else if (currentsection == CurrentSection.CUSTOMERINFO) {
      this.mainmiddlecontainer.removeAll();
      if (this.onlinecustomerinfotab == null) {
        this.onlinecustomerinfotab = new OnlineCustomerInfoTab(this.orderdata, this);
      }
      this.mainmiddlecontainer.add(this.onlinecustomerinfotab);
      this.mainmiddlecontainer.layout();
    } else if (currentsection == CurrentSection.REVIEWQUOTE) {
      this.mainmiddlecontainer.removeAll();
      OnlineReviewQuoteTab onlinereviewquotetab = new OnlineReviewQuoteTab(this.orderdata, this);
      this.mainmiddlecontainer.add(onlinereviewquotetab);
      this.mainmiddlecontainer.layout();
    } else if (currentsection == CurrentSection.SAVESUBMIT) {
      if (this.orderdata.getOrderType() == OrderData.OrderType.OVERSEAS) {
        if (this.onlinetype == OnlineType.ORDER) {
          Window.open("https://www.ottocap.com/customordersaveoverseas.php", "_self", "");
        } else if (this.onlinetype == OnlineType.VIRTUALSAMPLE) {
          Window.open("https://www.ottocap.com/virtualsamplesaveorder.php", "_self", "");
        } else if (this.onlinetype == OnlineType.QUOTE) {
          Window.open("https://www.ottocap.com/instantquotesaveoverseas.php", "_self", "");
        }
      } else if (this.orderdata.getOrderType() == OrderData.OrderType.DOMESTIC) {
        if (this.onlinetype == OnlineType.ORDER) {
          Window.open("https://www.ottocap.com/customordersavedomestic.php", "_self", "");
        } else if (this.onlinetype == OnlineType.VIRTUALSAMPLE) {
          Window.open("https://www.ottocap.com/virtualsamplesaveorder.php", "_self", "");
        } else if (this.onlinetype == OnlineType.QUOTE) {
          Window.open("https://www.ottocap.com/instantquotesavedomestic.php", "_self", "");
        }
      }
    }
  }
  

  private ClickHandler clickhandler = new ClickHandler()
  {

    public void onClick(ClickEvent event)
    {
      if (event.getSource() == InstantQuoteMainScreen.this.menu1_productanddesigninfo) {
        InstantQuoteMainScreen.this.doStep(1);
      } else if (event.getSource() == InstantQuoteMainScreen.this.menu2_customerinfo) {
        InstantQuoteMainScreen.this.doStep(2);
      } else if (event.getSource() == InstantQuoteMainScreen.this.menu3_revieworder) {
        InstantQuoteMainScreen.this.doStep(3);
      } else if (event.getSource() == InstantQuoteMainScreen.this.menu4_submitorder) {
        InstantQuoteMainScreen.this.doStep(4);
      } else if (event.getSource() == InstantQuoteMainScreen.this.menu5_viewcart)
      {
        InstantQuoteMainScreen.this.viewcartchoices.removeAllRows();
        
        for (int i = 0; i < InstantQuoteMainScreen.this.orderdata.getSetCount(); i++) {
          InstantQuoteMainScreen.this.viewcartchoices.setText(i, 0, i + 1 + ".) " + InstantQuoteMainScreen.this.orderdata.getSet(i).getItem(0).getStyleNumber());
        }
        
        InstantQuoteMainScreen.this.myitems.setPopupPosition(InstantQuoteMainScreen.this.menu5_viewcart.getAbsoluteLeft(), InstantQuoteMainScreen.this.menu5_viewcart.getAbsoluteTop() + InstantQuoteMainScreen.this.menu5_viewcart.getOffsetHeight());
        InstantQuoteMainScreen.this.myitems.show();
        InstantQuoteMainScreen.this.myroundpopup.setVisible(true);
      } else if (event.getSource() == InstantQuoteMainScreen.this.viewcartchoices) {
        int row = InstantQuoteMainScreen.this.viewcartchoices.getCellForEvent(event).getRowIndex();
        InstantQuoteMainScreen.this.myitems.hide();
        InstantQuoteMainScreen.this.currentset = row;
        InstantQuoteMainScreen.this.doStep(1);
      }
    }
  };
  

  public void doStep(int step)
  {
    String errormsg = "";
    if (step == 1) {
      this.currentsection = CurrentSection.PRODUCTDESIGNINFO;
      doButtonSection();
      setMiddleContainer(this.currentsection);
    } else if (step == 2) {
      errormsg = errormsg + checkStep1("");
      if (errormsg.equals("")) {
        this.currentsection = CurrentSection.CUSTOMERINFO;
        doButtonSection();
        setMiddleContainer(this.currentsection);
      }
    } else if (step == 3) {
      errormsg = errormsg + checkStep1("");
      errormsg = errormsg + checkStep2("");
      if (errormsg.equals("")) {
        this.currentsection = CurrentSection.REVIEWQUOTE;
        doButtonSection();
        fixSample();
        if (this.orderdata.getChangeHappened()) {
          NewWorkFlow.get().getWorkFlowRPC().saveOrder(this.orderdata, this.savebeforestepcallback);
        } else {
          setMiddleContainer(this.currentsection);
        }
      }
    } else if (step == 4) {
      errormsg = errormsg + checkStep1("");
      errormsg = errormsg + checkStep2("");
      if (errormsg.equals("")) {
        MessageBox box = new MessageBox();
        box.setTitleHtml("Notice");
        box.setMessage("Please save a copy of this quotation before submitting for review.<br>You will not be able to retrieve the quotation once it is submitted.<br><br>Press OK to submit or CANCEL to go back.");
        box.addCallback(this.submitconfirmcallback);
        box.setIcon(MessageBox.INFO);
        box.setButtons("okcancel");
        box.show();
      }
    }
    
    if (!errormsg.equals("")) {
      MessageBox.alert("Error", errormsg, null);
    }
  }
  
  private Listener<MessageBoxEvent> submitconfirmcallback = new Listener()
  {
    public void handleEvent(MessageBoxEvent be)
    {
      if (be.getButtonClicked().getHtml().equals("OK")) {
        InstantQuoteMainScreen.this.currentsection = InstantQuoteMainScreen.CurrentSection.SAVESUBMIT;
        InstantQuoteMainScreen.this.doButtonSection();
        InstantQuoteMainScreen.this.fixSample();
        InstantQuoteMainScreen.this.fixOrderStatus();
        NewWorkFlow.get().getWorkFlowRPC().saveOrder(InstantQuoteMainScreen.this.orderdata, InstantQuoteMainScreen.this.savebeforestepcallback);
      }
    }
  };
  

  private void fixOrderStatus()
  {
    if (this.onlinetype == OnlineType.QUOTE) {
      this.orderdata.setOrderStatus("New Quotation");
    } else if (this.onlinetype == OnlineType.ORDER) {
      this.orderdata.setOrderStatus("New Order");
    } else if (this.onlinetype == OnlineType.VIRTUALSAMPLE) {
      this.orderdata.setOrderStatus("New Virtual Sample Request");
    }
  }
  
  private String checkStep1(String errormsg) {
    for (int i = 0; i < this.orderdata.getSetCount(); i++) {
      if (this.orderdata.getSet(i).getItem(0).getStyleNumber().equals("")) {
        errormsg = errormsg + "Item " + (i + 1) + " Style Not Selected<BR>";
      }
      if ((this.onlinetype != OnlineType.VIRTUALSAMPLE) && (
        (this.orderdata.getSet(i).getItem(0).getQuantity() == null) || (this.orderdata.getSet(i).getItem(0).getQuantity().intValue() == 0))) {
        errormsg = errormsg + "Item " + (i + 1) + " Quantity Is Invalid<BR>";
      }
      
      if (this.orderdata.getOrderType() == OrderData.OrderType.OVERSEAS) {
        for (int j = 0; j < this.orderdata.getSet(i).getLogoCount(); j++) {
          for (int k = 0; k < this.orderdata.getSet(i).getLogo(j).getDecorationCount().intValue(); k++) {
            if (this.orderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getName().equals("")) {
              errormsg = errormsg + "Item " + (i + 1) + " Logo " + (j + 1) + " Decoration Is Invalid<BR>";
            } else {
              String currentdec = this.orderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getName();
              
              DecorationInfoNames theoptions = NewWorkFlow.get().getDataStores().getDecorationinfo().getName(currentdec);
              if ((!theoptions.getField1().equals("")) && (this.orderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getField1() == null)) {
                errormsg = errormsg + "Item " + (i + 1) + " Logo " + (j + 1) + " Decoration Field(s) Is Invalid<BR>";
              } else if ((!theoptions.getField2().equals("")) && (this.orderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getField2() == null)) {
                errormsg = errormsg + "Item " + (i + 1) + " Logo " + (j + 1) + " Decoration Field(s) Is Invalid<BR>";
              } else if ((!theoptions.getField3().equals("")) && (this.orderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getField3() == null)) {
                errormsg = errormsg + "Item " + (i + 1) + " Logo " + (j + 1) + " Decoration Field(s) Is Invalid<BR>";
              } else if ((!theoptions.getField4().equals("")) && (this.orderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getField4() == null)) {
                errormsg = errormsg + "Item " + (i + 1) + " Logo " + (j + 1) + " Decoration Field(s) Is Invalid<BR>";
              }
            }
          }
        }
        
        if (!this.orderdata.getSet(i).getItem(0).getStyleNumber().equals(""))
        {
          NewStyleStore thedata = (NewStyleStore)NewWorkFlow.get().getDataStores().getOverseasStyleStore().get(this.orderdata.getSet(i).getItem(0).getStyleNumber());
          

          String thevendornumber = String.valueOf(thedata.getVendorNumber());
          
          OrderDataVendorInformation vendorinfodata;
          if (this.orderdata.getVendorInformation().getOverseasVendor().containsKey(thevendornumber)) {
            vendorinfodata = (OrderDataVendorInformation)this.orderdata.getVendorInformation().getOverseasVendor().get(thevendornumber);
          } else {
            OrderDataVendorInformation vendorinfodata = new OrderDataVendorInformation();
            this.orderdata.getVendorInformation().getOverseasVendor().put(thevendornumber, vendorinfodata);
            vendorinfodata.setVendor(Integer.valueOf(thedata.getVendorNumber()));
            


            vendorinfodata.setShippingMethod(NewWorkFlow.get().getDataStores().getOverseasVendorDefaultShipping(thevendornumber));
          }
        }
        

        if (this.orderdata.getSet(i).getItem(0).getHasPrivateLabel() == null) {
          errormsg = errormsg + "Item " + (i + 1) + " Missing Type Of Label<BR>";
        }
      }
    }
    










    if (this.orderdata.getOrderType() == OrderData.OrderType.OVERSEAS)
    {
      FastMap<Integer> stylequantity = new FastMap();
      FastMap<Integer> stylelogoquantity = new FastMap();
      
      for (int i = 0; i < this.orderdata.getSetCount(); i++) {
        for (int j = 0; j < this.orderdata.getSet(i).getItemCount(); j++) {
          if (!this.orderdata.getSet(i).getItem(j).getStyleNumber().equals("")) {
            String samestyle = ((NewStyleStore)NewWorkFlow.get().getDataStores().getOverseasStyleStore().get(this.orderdata.getSet(i).getItem(j).getStyleNumber())).getSameStyleNumber();
            int qtytoadd = this.orderdata.getSet(i).getItem(j).getQuantity() == null ? 0 : this.orderdata.getSet(i).getItem(j).getQuantity().intValue();
            int currentqty = stylequantity.get(samestyle) == null ? 0 : ((Integer)stylequantity.get(samestyle)).intValue();
            stylequantity.put(samestyle, Integer.valueOf(currentqty + qtytoadd));
            
            for (int k = 0; k < this.orderdata.getSet(i).getLogoCount(); k++) {
              String samefilename = this.orderdata.getSet(i).getLogo(k).getFilename();
              String samedstfilename = this.orderdata.getSet(i).getLogo(k).getDstFilename();
              String stylelogoname = samestyle + "_" + getMatchingFilename(samefilename) + "_" + getMatchingFilename(samedstfilename);
              
              int currentlogoqty = stylelogoquantity.get(stylelogoname) == null ? 0 : ((Integer)stylelogoquantity.get(stylelogoname)).intValue();
              stylelogoquantity.put(stylelogoname, Integer.valueOf(currentlogoqty + qtytoadd));
            }
          }
        }
      }
      

      String[] currentstyles = (String[])stylequantity.keySet().toArray(new String[0]);
      for (int i = 0; i < currentstyles.length; i++) {
        if (((Integer)stylequantity.get(currentstyles[i])).intValue() < 144) {
          errormsg = errormsg + "Style " + currentstyles[i] + " Family Must Have At Least 144 Quantity<BR>";
        }
      }
      
      String[] currentlogostyles = (String[])stylelogoquantity.keySet().toArray(new String[0]);
      for (int i = 0; i < currentlogostyles.length; i++) {
        if (((Integer)stylelogoquantity.get(currentlogostyles[i])).intValue() < 144) {
          String currentstyle = currentlogostyles[i].substring(0, currentlogostyles[i].indexOf("_"));
          errormsg = errormsg + "Style " + currentstyle + " Family Must Have At Least 144 Logo Quantity<BR>";
        }
      }
    }
    
    return errormsg;
  }
  
  private String getMatchingFilename(String filename) {
    int fileextindex = filename.lastIndexOf(".");
    if (fileextindex != -1) {
      int substringindex = filename.lastIndexOf(".", fileextindex - 1);
      if (substringindex != -1) {
        return (filename.substring(0, substringindex) + filename.substring(fileextindex)).toLowerCase();
      }
    }
    
    return filename.toLowerCase();
  }
  
  private String checkStep2(String errormsg) {
    if ((this.onlinetype != OnlineType.VIRTUALSAMPLE) && 
      (this.orderdata.getCustomerInformation().getContactName().equals(""))) {
      errormsg = errormsg + "Contact Name Is Missing<BR>";
    }
    
    return errormsg;
  }
  
  public OnlineType getOnlineType() {
    return this.onlinetype;
  }
  
  public void deleteSet(final OrderDataSet orderdataset) {
    Listener<MessageBoxEvent> callback = new Listener()
    {
      public void handleEvent(MessageBoxEvent be)
      {
        if (be.getButtonClicked().getHtml().equals("Yes")) {
          InstantQuoteMainScreen.this.onlineproductdesignlist.remove(InstantQuoteMainScreen.this.currentset);
          InstantQuoteMainScreen.this.orderdata.removeSet(orderdataset);
          if ((InstantQuoteMainScreen.this.orderdata.getSetCount() - 1 < InstantQuoteMainScreen.this.currentset) && (InstantQuoteMainScreen.this.currentset > 0)) {
            InstantQuoteMainScreen.this.currentset -= 1;
          }
          for (OnlineProductDesignTab currenttab : InstantQuoteMainScreen.this.onlineproductdesignlist) {
            currenttab.fixItemDisplay();
          }
          InstantQuoteMainScreen.this.setMiddleContainer(InstantQuoteMainScreen.this.currentsection);
        }
        
      }
      

    };
    MessageBox.confirm("Alert", "Are you sure you want to remove item " + (this.currentset + 1), callback);
  }
  
  public void addSet() {
    this.orderdata.addSet();
    this.currentset = (this.orderdata.getSetCount() - 1);
    ((OnlineProductDesignTab)this.onlineproductdesignlist.get(this.onlineproductdesignlist.size() - 1)).fixItemDisplay();
    setMiddleContainer(this.currentsection);
  }
  
  public void copySet(OrderDataSet orderdataset) {
    this.orderdata.addSet(orderdataset.copy());
    this.currentset = (this.orderdata.getSetCount() - 1);
    ((OnlineProductDesignTab)this.onlineproductdesignlist.get(this.onlineproductdesignlist.size() - 1)).fixItemDisplay();
    setMiddleContainer(this.currentsection);
  }
  
  public void previousItem() {
    this.currentset -= 1;
    if (this.currentset < 0) {
      this.currentset = 0;
    }
    setMiddleContainer(this.currentsection);
  }
  
  public void nextItem() {
    this.currentset += 1;
    setMiddleContainer(this.currentsection);
  }
  
  private void fixSample() {
    for (int i = 0; i < this.orderdata.getSetCount(); i++) {
      if ((this.orderdata.getSet(i).getItem(0).getProductSampleEmail()) || (this.orderdata.getSet(i).getItem(0).getProductSampleShip())) {
        this.orderdata.getSet(i).getItem(0).setProductSampleToDo(Integer.valueOf(1));
        this.orderdata.getSet(i).getItem(0).setProductSampleTotalDone(Integer.valueOf(1));
        if (this.orderdata.getSet(i).getItem(0).getProductSampleEmail()) {
          this.orderdata.getSet(i).getItem(0).setProductSampleTotalEmail(Integer.valueOf(1));
        }
        if (this.orderdata.getSet(i).getItem(0).getProductSampleShip()) {
          this.orderdata.getSet(i).getItem(0).setProductSampleTotalShip(Integer.valueOf(1));
        }
      }
      for (int j = 0; j < this.orderdata.getSet(i).getLogoCount(); j++) {
        if ((this.orderdata.getSet(i).getLogo(j).getSwatchEmail()) || (this.orderdata.getSet(i).getLogo(j).getSwatchShip())) {
          this.orderdata.getSet(i).getLogo(j).setSwatchToDo(Integer.valueOf(1));
          this.orderdata.getSet(i).getLogo(j).setSwatchTotalDone(Integer.valueOf(1));
          if (this.orderdata.getSet(i).getLogo(j).getSwatchEmail()) {
            this.orderdata.getSet(i).getLogo(j).setSwatchTotalEmail(Integer.valueOf(1));
          }
          if (this.orderdata.getSet(i).getLogo(j).getSwatchShip()) {
            this.orderdata.getSet(i).getLogo(j).setSwatchTotalShip(Integer.valueOf(1));
          }
        }
      }
    }
  }
  
  public void printPDF() {
    Window.open(NewWorkFlow.baseurl + "pdfprintout/?hiddenkey=" + this.orderdata.getHiddenKey() + "&printouttype=Quotation", "_blank", "");
  }
  
  private BuzzAsyncCallback<Integer> savebeforestepcallback = new BuzzAsyncCallback()
  {
    public void onFailure(Throwable caught)
    {
      NewWorkFlow.get().reLogin2(caught, InstantQuoteMainScreen.this.savebeforestepcallback);
    }
    
    public void doRetry()
    {
      NewWorkFlow.get().getWorkFlowRPC().saveOrder(InstantQuoteMainScreen.this.orderdata, InstantQuoteMainScreen.this.savebeforestepcallback);
    }
    
    public void onSuccess(Integer result)
    {
      if (InstantQuoteMainScreen.this.orderdata.getHiddenKey() == null) {
        InstantQuoteMainScreen.this.orderdata.setHiddenKey(result);
      }
      InstantQuoteMainScreen.this.orderdata.setChangeHappened(false);
      InstantQuoteMainScreen.this.setMiddleContainer(InstantQuoteMainScreen.this.currentsection);
    }
  };
  
  public NewStyleStore getStyleStore(OrderDataItem item)
  {
    if (this.orderdata.getOrderType() == OrderData.OrderType.DOMESTIC) {
      NewStyleStore stylestore = (item.getStyleNumber() == null) || (item.getStyleNumber().equals("")) ? new NewStyleStore() : (NewStyleStore)NewWorkFlow.get().getDataStores().getDomesticStyleStore().get(item.getStyleNumber());
      return stylestore;
    }
    if ((item.getStyleNumber() == null) || (item.getStyleNumber().equals(""))) {
      return new NewStyleStore();
    }
    if ((item.getVendorNumber() != null) && (!item.getVendorNumber().equals("")) && (!item.getVendorNumber().equals("Default"))) {
      if (NewWorkFlow.get().getDataStores().getOverseasStyleStore().get(item.getStyleNumber() + "_" + item.getVendorNumber()) == null) {
        return (NewStyleStore)NewWorkFlow.get().getDataStores().getOverseasStyleStore().get(item.getStyleNumber());
      }
      return (NewStyleStore)NewWorkFlow.get().getDataStores().getOverseasStyleStore().get(item.getStyleNumber() + "_" + item.getVendorNumber());
    }
    
    return (NewStyleStore)NewWorkFlow.get().getDataStores().getOverseasStyleStore().get(item.getStyleNumber());
  }
}
