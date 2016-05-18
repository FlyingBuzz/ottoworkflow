package com.ottocap.NewWorkFlow.client.OnlineWorkflow;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayoutData;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.ottocap.NewWorkFlow.client.DataHolder.CustomerInfo.EclipseDataHolder;
import com.ottocap.NewWorkFlow.client.NewWorkFlow;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData.OrderType;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataAddress;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataCustomerInformation;
import com.ottocap.NewWorkFlow.client.Services.WorkFlowServiceAsync;
import com.ottocap.NewWorkFlow.client.StyleInformationData;
import java.util.Date;

public class InstantQuoteSelectionScreen extends com.extjs.gxt.ui.client.widget.LayoutContainer
{
  private Button newdomesticquotationbutton = new Button("New Domestic Quotation");
  private Button newoverseasquotationbutton = new Button("New Overseas Quotation");
  private Button newdomesticorderbutton = new Button("New Domestic Order");
  private Button newoverseasorderbutton = new Button("New Overseas Order");
  private Button newdomesticvirtualsamplerequestbutton = new Button("New Domestic Virtual Sample Request");
  private Button newoverseasvirtualsamplerequestbutton = new Button("New Overseas Virtual Sample Request");
  private OrderData autoorderdata = null;
  private InstantQuoteMainScreen.OnlineType autoonlinetype = null;
  
  public InstantQuoteSelectionScreen()
  {
    if ((Cookies.getCookie("idnumber") != null) && (!Cookies.getCookie("idnumber").equals(""))) {
      this.autoorderdata = new OrderData();
      this.autoorderdata.setInternalDueDateTime(new Date());
      this.autoorderdata.getCustomerInformation().setEclipseAccountNumber(Integer.valueOf(Cookies.getCookie("idnumber")));
      if ((Cookies.getCookie("ordertype") != null) && (Cookies.getCookie("ordertype").equals("Overseas"))) {
        this.autoorderdata.setOrderType(OrderData.OrderType.OVERSEAS);
      } else if ((Cookies.getCookie("ordertype") != null) && (Cookies.getCookie("ordertype").equals("Domestic"))) {
        this.autoorderdata.setOrderType(OrderData.OrderType.DOMESTIC);
      }
      
      if ((Cookies.getCookie("onlineprogramtype") != null) && (Cookies.getCookie("onlineprogramtype").equals("quote"))) {
        this.autoonlinetype = InstantQuoteMainScreen.OnlineType.QUOTE;
        this.autoorderdata.setOrderStatus("Canceled Online Quotation");
      } else if ((Cookies.getCookie("onlineprogramtype") != null) && (Cookies.getCookie("onlineprogramtype").equals("order"))) {
        this.autoonlinetype = InstantQuoteMainScreen.OnlineType.ORDER;
        this.autoorderdata.setOrderStatus("Canceled Online Order");
      } else if ((Cookies.getCookie("onlineprogramtype") != null) && (Cookies.getCookie("onlineprogramtype").equals("virtualsample"))) {
        this.autoonlinetype = InstantQuoteMainScreen.OnlineType.VIRTUALSAMPLE;
        this.autoorderdata.setOrderStatus("Canceled Online Virtual Sample Request");
      }
      
      NewWorkFlow.get().getWorkFlowRPC().getEclipseAccount(this.autoorderdata.getCustomerInformation().getEclipseAccountNumber().intValue(), this.eclipseaccountcallback);
    }
    else
    {
      this.newdomesticquotationbutton.addSelectionListener(this.selectionlistener);
      this.newoverseasquotationbutton.addSelectionListener(this.selectionlistener);
      this.newdomesticorderbutton.addSelectionListener(this.selectionlistener);
      this.newoverseasorderbutton.addSelectionListener(this.selectionlistener);
      this.newdomesticvirtualsamplerequestbutton.addSelectionListener(this.selectionlistener);
      this.newoverseasvirtualsamplerequestbutton.addSelectionListener(this.selectionlistener);
      
      setLayout(new com.extjs.gxt.ui.client.widget.layout.HBoxLayout());
      HBoxLayoutData flex = new HBoxLayoutData(new Margins(0, 5, 0, 0));
      flex.setFlex(1.0D);
      
      add(this.newdomesticquotationbutton, flex);
      add(this.newoverseasquotationbutton, flex);
      add(this.newdomesticorderbutton, flex);
      add(this.newoverseasorderbutton, flex);
      add(this.newdomesticvirtualsamplerequestbutton, flex);
      
      HBoxLayoutData flex2 = new HBoxLayoutData(new Margins(0));
      flex2.setFlex(1.0D);
      
      add(this.newoverseasvirtualsamplerequestbutton, flex2);
    }
  }
  

  private AsyncCallback<EclipseDataHolder> eclipseaccountcallback = new AsyncCallback()
  {
    public void onFailure(Throwable caught)
    {
      if ((Cookies.getCookie("defaultstyle") != null) && (!Cookies.getCookie("defaultstyle").equals("")) && (!Cookies.getCookie("defaultstyle").equals("N/A"))) {
        NewWorkFlow.get().getWorkFlowRPC().getStyleData(Cookies.getCookie("defaultstyle"), InstantQuoteSelectionScreen.this.autoorderdata.getOrderType(), InstantQuoteSelectionScreen.this.styleloadcallback);
      }
      else if ((InstantQuoteSelectionScreen.this.autoorderdata.getOrderType() != null) && (InstantQuoteSelectionScreen.this.autoonlinetype != null)) {
        NewWorkFlow.get().getViewport().removeAll();
        NewWorkFlow.get().getViewport().add(new InstantQuoteMainScreen(InstantQuoteSelectionScreen.this.autoorderdata, InstantQuoteSelectionScreen.this.autoonlinetype));
        NewWorkFlow.get().getViewport().layout(true);
      }
    }
    

    public void onSuccess(EclipseDataHolder result)
    {
      if (result != null) {
        InstantQuoteSelectionScreen.this.autoorderdata.getCustomerInformation().setCompanyLogo(result.getCompanyLogo());
        
        if (result.getOrderDataCustomerInformation() != null) {
          InstantQuoteSelectionScreen.this.autoorderdata.getCustomerInformation().setEclipseAccountNumber(result.getOrderDataCustomerInformation().getEclipseAccountNumber());
          InstantQuoteSelectionScreen.this.autoorderdata.getCustomerInformation().setContactName(result.getOrderDataCustomerInformation().getContactName());
          InstantQuoteSelectionScreen.this.autoorderdata.getCustomerInformation().setTerms(result.getOrderDataCustomerInformation().getTerms());
          InstantQuoteSelectionScreen.this.autoorderdata.getCustomerInformation().setCompany(result.getOrderDataCustomerInformation().getCompany());
          InstantQuoteSelectionScreen.this.autoorderdata.getCustomerInformation().setEmail(result.getOrderDataCustomerInformation().getEmail());
          InstantQuoteSelectionScreen.this.autoorderdata.getCustomerInformation().setPhone(result.getOrderDataCustomerInformation().getPhone());
          InstantQuoteSelectionScreen.this.autoorderdata.getCustomerInformation().setFax(result.getOrderDataCustomerInformation().getFax());
          InstantQuoteSelectionScreen.this.autoorderdata.getCustomerInformation().setSameAsBillingAddress(result.getOrderDataCustomerInformation().getSameAsBillingAddress());
          InstantQuoteSelectionScreen.this.autoorderdata.getCustomerInformation().getBillInformation().setCompany(result.getOrderDataCustomerInformation().getBillInformation().getCompany());
          InstantQuoteSelectionScreen.this.autoorderdata.getCustomerInformation().getBillInformation().setStreetLine1(result.getOrderDataCustomerInformation().getBillInformation().getStreetLine1());
          InstantQuoteSelectionScreen.this.autoorderdata.getCustomerInformation().getBillInformation().setStreetLine2(result.getOrderDataCustomerInformation().getBillInformation().getStreetLine2());
          InstantQuoteSelectionScreen.this.autoorderdata.getCustomerInformation().getBillInformation().setCity(result.getOrderDataCustomerInformation().getBillInformation().getCity());
          InstantQuoteSelectionScreen.this.autoorderdata.getCustomerInformation().getBillInformation().setState(result.getOrderDataCustomerInformation().getBillInformation().getState());
          InstantQuoteSelectionScreen.this.autoorderdata.getCustomerInformation().getBillInformation().setZip(result.getOrderDataCustomerInformation().getBillInformation().getZip());
          InstantQuoteSelectionScreen.this.autoorderdata.getCustomerInformation().getBillInformation().setCountry(result.getOrderDataCustomerInformation().getBillInformation().getCountry());
          InstantQuoteSelectionScreen.this.autoorderdata.getCustomerInformation().getShipInformation().setCompany(result.getOrderDataCustomerInformation().getShipInformation().getCompany());
          InstantQuoteSelectionScreen.this.autoorderdata.getCustomerInformation().getShipInformation().setStreetLine1(result.getOrderDataCustomerInformation().getShipInformation().getStreetLine1());
          InstantQuoteSelectionScreen.this.autoorderdata.getCustomerInformation().getShipInformation().setStreetLine2(result.getOrderDataCustomerInformation().getShipInformation().getStreetLine2());
          InstantQuoteSelectionScreen.this.autoorderdata.getCustomerInformation().getShipInformation().setCity(result.getOrderDataCustomerInformation().getShipInformation().getCity());
          InstantQuoteSelectionScreen.this.autoorderdata.getCustomerInformation().getShipInformation().setState(result.getOrderDataCustomerInformation().getShipInformation().getState());
          InstantQuoteSelectionScreen.this.autoorderdata.getCustomerInformation().getShipInformation().setZip(result.getOrderDataCustomerInformation().getShipInformation().getZip());
          InstantQuoteSelectionScreen.this.autoorderdata.getCustomerInformation().getShipInformation().setCountry(result.getOrderDataCustomerInformation().getShipInformation().getCountry());
          InstantQuoteSelectionScreen.this.autoorderdata.getCustomerInformation().getShipInformation().setResidential(result.getOrderDataCustomerInformation().getShipInformation().getResidential());
        }
      }
      
      if ((Cookies.getCookie("defaultstyle") != null) && (!Cookies.getCookie("defaultstyle").equals("")) && (!Cookies.getCookie("defaultstyle").equals("N/A"))) {
        NewWorkFlow.get().getWorkFlowRPC().getStyleData(Cookies.getCookie("defaultstyle"), InstantQuoteSelectionScreen.this.autoorderdata.getOrderType(), InstantQuoteSelectionScreen.this.styleloadcallback);
      }
      else if ((InstantQuoteSelectionScreen.this.autoorderdata.getOrderType() != null) && (InstantQuoteSelectionScreen.this.autoonlinetype != null)) {
        NewWorkFlow.get().getViewport().removeAll();
        NewWorkFlow.get().getViewport().add(new InstantQuoteMainScreen(InstantQuoteSelectionScreen.this.autoorderdata, InstantQuoteSelectionScreen.this.autoonlinetype));
        NewWorkFlow.get().getViewport().layout(true);
      }
    }
  };
  



  private AsyncCallback<StyleInformationData> styleloadcallback = new AsyncCallback()
  {
    public void onFailure(Throwable caught) {}
    


    public void onSuccess(StyleInformationData result)
    {
      NewWorkFlow.get().setStyleStore(result);
      
      if ((InstantQuoteSelectionScreen.this.autoorderdata.getOrderType() != null) && (InstantQuoteSelectionScreen.this.autoonlinetype != null)) {
        NewWorkFlow.get().getViewport().removeAll();
        NewWorkFlow.get().getViewport().add(new InstantQuoteMainScreen(InstantQuoteSelectionScreen.this.autoorderdata, InstantQuoteSelectionScreen.this.autoonlinetype));
        NewWorkFlow.get().getViewport().layout(true);
      }
    }
  };
  

  private com.extjs.gxt.ui.client.event.SelectionListener<ButtonEvent> selectionlistener = new com.extjs.gxt.ui.client.event.SelectionListener()
  {
    public void componentSelected(ButtonEvent ce)
    {
      if (ce.getSource().equals(InstantQuoteSelectionScreen.this.newdomesticquotationbutton)) {
        OrderData orderdata = new OrderData();
        orderdata.setInternalDueDateTime(new Date());
        orderdata.setOrderType(OrderData.OrderType.DOMESTIC);
        orderdata.setOrderStatus("Canceled Online Quotation");
        NewWorkFlow.get().getViewport().removeAll();
        NewWorkFlow.get().getViewport().add(new InstantQuoteMainScreen(orderdata, InstantQuoteMainScreen.OnlineType.QUOTE));
        NewWorkFlow.get().getViewport().layout(true);
      } else if (ce.getSource().equals(InstantQuoteSelectionScreen.this.newoverseasquotationbutton)) {
        OrderData orderdata = new OrderData();
        orderdata.setInternalDueDateTime(new Date());
        orderdata.setOrderType(OrderData.OrderType.OVERSEAS);
        orderdata.setOrderStatus("Canceled Online Quotation");
        NewWorkFlow.get().getViewport().removeAll();
        NewWorkFlow.get().getViewport().add(new InstantQuoteMainScreen(orderdata, InstantQuoteMainScreen.OnlineType.QUOTE));
        NewWorkFlow.get().getViewport().layout(true);
      } else if (ce.getSource().equals(InstantQuoteSelectionScreen.this.newdomesticorderbutton)) {
        OrderData orderdata = new OrderData();
        orderdata.setInternalDueDateTime(new Date());
        orderdata.setOrderType(OrderData.OrderType.DOMESTIC);
        orderdata.setOrderStatus("Canceled Online Order");
        NewWorkFlow.get().getViewport().removeAll();
        NewWorkFlow.get().getViewport().add(new InstantQuoteMainScreen(orderdata, InstantQuoteMainScreen.OnlineType.ORDER));
        NewWorkFlow.get().getViewport().layout(true);
      } else if (ce.getSource().equals(InstantQuoteSelectionScreen.this.newoverseasorderbutton)) {
        OrderData orderdata = new OrderData();
        orderdata.setInternalDueDateTime(new Date());
        orderdata.setOrderType(OrderData.OrderType.OVERSEAS);
        orderdata.setOrderStatus("Canceled Online Order");
        NewWorkFlow.get().getViewport().removeAll();
        NewWorkFlow.get().getViewport().add(new InstantQuoteMainScreen(orderdata, InstantQuoteMainScreen.OnlineType.ORDER));
        NewWorkFlow.get().getViewport().layout(true);
      } else if (ce.getSource().equals(InstantQuoteSelectionScreen.this.newdomesticvirtualsamplerequestbutton)) {
        OrderData orderdata = new OrderData();
        orderdata.setInternalDueDateTime(new Date());
        orderdata.setOrderType(OrderData.OrderType.DOMESTIC);
        orderdata.setOrderStatus("Canceled Online Virtual Sample Request");
        NewWorkFlow.get().getViewport().removeAll();
        NewWorkFlow.get().getViewport().add(new InstantQuoteMainScreen(orderdata, InstantQuoteMainScreen.OnlineType.VIRTUALSAMPLE));
        NewWorkFlow.get().getViewport().layout(true);
      } else if (ce.getSource().equals(InstantQuoteSelectionScreen.this.newoverseasvirtualsamplerequestbutton)) {
        OrderData orderdata = new OrderData();
        orderdata.setInternalDueDateTime(new Date());
        orderdata.setOrderType(OrderData.OrderType.OVERSEAS);
        orderdata.setOrderStatus("Canceled Online Virtual Sample Request");
        NewWorkFlow.get().getViewport().removeAll();
        NewWorkFlow.get().getViewport().add(new InstantQuoteMainScreen(orderdata, InstantQuoteMainScreen.OnlineType.VIRTUALSAMPLE));
        NewWorkFlow.get().getViewport().layout(true);
      }
    }
  };
}
