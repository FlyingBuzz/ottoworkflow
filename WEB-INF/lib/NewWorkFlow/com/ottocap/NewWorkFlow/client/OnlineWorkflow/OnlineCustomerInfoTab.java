package com.ottocap.NewWorkFlow.client.OnlineWorkflow;

import com.extjs.gxt.ui.client.Style.VerticalAlignment;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.TableData;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.PushButton;
import com.ottocap.NewWorkFlow.client.DataHolder.CustomerInfo.CustomerInfoFields;
import com.ottocap.NewWorkFlow.client.DataHolder.OrderInfo.OrderInfoFields;
import com.ottocap.NewWorkFlow.client.DataHolder.ShippingInfo.ShippingInfoField;
import com.ottocap.NewWorkFlow.client.OnlineWorkflow.Widget.BlackLineBar;
import com.ottocap.NewWorkFlow.client.OnlineWorkflow.Widget.GreyDescription;
import com.ottocap.NewWorkFlow.client.OnlineWorkflow.Widget.HeadingBox;
import com.ottocap.NewWorkFlow.client.OnlineWorkflow.Widget.PaddedBodyContainer;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData;
import com.ottocap.NewWorkFlow.client.Widget.FormHorizontalPanel2;

public class OnlineCustomerInfoTab extends VerticalPanel
{
  private OrderInfoFields orderinfofields;
  private CustomerInfoFields customerinfofields;
  private ShippingInfoField shippinginfofield;
  private PushButton step1_productanddesigninfo = new PushButton();
  private PushButton step3_revieworder = new PushButton();
  private InstantQuoteMainScreen instantquotemainscreen;
  
  public OnlineCustomerInfoTab(OrderData orderdata, InstantQuoteMainScreen instantquotemainscreen) {
    this.instantquotemainscreen = instantquotemainscreen;
    
    this.orderinfofields = new OrderInfoFields(orderdata);
    this.customerinfofields = new CustomerInfoFields(orderdata);
    this.shippinginfofield = new ShippingInfoField(orderdata);
    this.customerinfofields.getEclipseAccountNumberField().disable();
    
    this.step1_productanddesigninfo.setStylePrimaryName("customerinfostep1button-PushButton");
    
    this.step1_productanddesigninfo.addClickHandler(this.clickhandler);
    this.step3_revieworder.addClickHandler(this.clickhandler);
    
    setStyleAttribute("margin-left", "auto");
    setStyleAttribute("margin-right", "auto");
    setWidth(1000);
    

    if (instantquotemainscreen.getOnlineType() == InstantQuoteMainScreen.OnlineType.QUOTE) {
      add(new HeadingBox("QUOTATION <B>INFORMATION</B>"));
      add(new GreyDescription("<B>Please fill out this section completely. For RUSH ORDERS, additional charges will apply. The in-hands date is not guaranteed until the quotation is reviewed and confirmed by a customer service representative.</B>"));
      this.orderinfofields.getOrderCommentField().setFieldLabel("Quotation Comment");
      this.step3_revieworder.setStylePrimaryName("customerinfostep3button-PushButton");
    } else if (instantquotemainscreen.getOnlineType() == InstantQuoteMainScreen.OnlineType.ORDER) {
      add(new HeadingBox("ORDER <B>INFORMATION</B>"));
      add(new GreyDescription("<B>Please fill out this section completely. For RUSH ORDERS, additional charges will apply. The in-hands date is not guaranteed until the order is reviewed and confirmed by a customer service representative.</B>"));
      this.step3_revieworder.setStylePrimaryName("customerinfostep3orderbutton-PushButton");
    }
    
    PaddedBodyContainer quoteinfobody = new PaddedBodyContainer();
    FormHorizontalPanel2 ordernumberandinhandrow = new FormHorizontalPanel2();
    ordernumberandinhandrow.add(this.orderinfofields.getCustomerPOField());
    ordernumberandinhandrow.add(this.orderinfofields.getInhandDateField());
    ordernumberandinhandrow.addCheckBox(this.orderinfofields.getRushOrderField());
    
    FormHorizontalPanel2 quotecommentrow = new FormHorizontalPanel2();
    quotecommentrow.add(this.orderinfofields.getOrderCommentField(), new FormData(854, -1));
    
    quoteinfobody.add(ordernumberandinhandrow);
    quoteinfobody.add(quotecommentrow);
    add(quoteinfobody);
    

    add(new HeadingBox("CUSTOMER <B>INFORMATION</B>"));
    add(new GreyDescription("<B>Please fill out this section completely or verify if the pre-populated information is correct.</b>"));
    
    PaddedBodyContainer customerinfobody = new PaddedBodyContainer();
    FormHorizontalPanel2 namecompanyaccountrow = new FormHorizontalPanel2();
    namecompanyaccountrow.add(this.customerinfofields.getContactNameField());
    namecompanyaccountrow.add(this.customerinfofields.getCompanyField());
    namecompanyaccountrow.add(this.customerinfofields.getEclipseAccountNumberField());
    namecompanyaccountrow.add(this.customerinfofields.getCompanyLogoField());
    FormHorizontalPanel2 phonefaxemailrow = new FormHorizontalPanel2();
    phonefaxemailrow.add(this.customerinfofields.getPhoneNumberField());
    phonefaxemailrow.add(this.customerinfofields.getFaxNumberField());
    phonefaxemailrow.add(this.customerinfofields.getEmailAddressField());
    phonefaxemailrow.add(this.customerinfofields.getCompanyLogoUploadFormField());
    
    customerinfobody.add(namecompanyaccountrow);
    customerinfobody.add(phonefaxemailrow);
    add(customerinfobody);
    

    HorizontalPanel addresscontainer = new HorizontalPanel();
    
    VerticalPanel billingcontainer = new VerticalPanel();
    billingcontainer.setStyleAttribute("margin-right", "10px");
    billingcontainer.add(new HeadingBox("BILLING <B>INFORMATION</B>", 490));
    billingcontainer.add(new GreyDescription("<B>Please fill out this section completely or verify if the pre-populated information is correct.</B>", 470));
    
    PaddedBodyContainer billbody = new PaddedBodyContainer(450);
    billbody.setStyleAttribute("margin-top", "28px");
    FormHorizontalPanel2 billcompanyrow = new FormHorizontalPanel2();
    billcompanyrow.add(this.customerinfofields.getBillCompanyNameField());
    FormHorizontalPanel2 billstreet1row = new FormHorizontalPanel2();
    billstreet1row.add(this.customerinfofields.getBillStreet1Field(), new FormData(418, 0));
    FormHorizontalPanel2 billstreet2row = new FormHorizontalPanel2();
    billstreet2row.add(this.customerinfofields.getBillStreet2Field(), new FormData(418, 0));
    FormHorizontalPanel2 billcitystaterow = new FormHorizontalPanel2();
    billcitystaterow.add(this.customerinfofields.getBillCityField());
    billcitystaterow.add(this.customerinfofields.getBillStateField());
    FormHorizontalPanel2 billzipcountryrow = new FormHorizontalPanel2();
    billzipcountryrow.add(this.customerinfofields.getBillZipCodeField());
    billzipcountryrow.add(this.customerinfofields.getBillCountryField());
    
    billbody.add(billcompanyrow);
    billbody.add(billstreet1row);
    billbody.add(billstreet2row);
    billbody.add(billcitystaterow);
    billbody.add(billzipcountryrow);
    
    billingcontainer.add(billbody);
    
    VerticalPanel shippingcontainer = new VerticalPanel();
    shippingcontainer.setStyleAttribute("margin-left", "10px");
    shippingcontainer.add(new HeadingBox("SHIPPING <B>INFORMATION</B>", 490));
    shippingcontainer.add(new GreyDescription("<B>Please fill out this section completely or verify if the pre-populated information is correct.</B>", 470));
    
    PaddedBodyContainer shipbody = new PaddedBodyContainer(450);
    FormHorizontalPanel2 shipcheckboxrow = new FormHorizontalPanel2();
    shipcheckboxrow.add(this.customerinfofields.getSameAsBillingField(), new FormData(150, 0), true);
    shipcheckboxrow.add(this.customerinfofields.getBlindShippingRequiredField(), new FormData(150, 0), true);
    shipcheckboxrow.add(this.customerinfofields.getResidentialAddressField(), new FormData(150, 0), true);
    FormHorizontalPanel2 shipcompanyattnrow = new FormHorizontalPanel2();
    shipcompanyattnrow.add(this.customerinfofields.getShipCompanyNameField());
    shipcompanyattnrow.add(this.customerinfofields.getAttentionToField());
    FormHorizontalPanel2 shipstreet1row = new FormHorizontalPanel2();
    shipstreet1row.add(this.customerinfofields.getShipStreet1Field(), new FormData(418, 0));
    FormHorizontalPanel2 shipstreet2row = new FormHorizontalPanel2();
    shipstreet2row.add(this.customerinfofields.getShipStreet2Field(), new FormData(418, 0));
    FormHorizontalPanel2 shipcitystaterow = new FormHorizontalPanel2();
    shipcitystaterow.add(this.customerinfofields.getShipCityField());
    shipcitystaterow.add(this.customerinfofields.getShipStateField());
    FormHorizontalPanel2 shipzipcountryrow = new FormHorizontalPanel2();
    shipzipcountryrow.add(this.customerinfofields.getShipZipCodeField());
    shipzipcountryrow.add(this.customerinfofields.getShipCountryField());
    
    shipbody.add(shipcheckboxrow);
    shipbody.add(shipcompanyattnrow);
    shipbody.add(shipstreet1row);
    shipbody.add(shipstreet2row);
    shipbody.add(shipcitystaterow);
    shipbody.add(shipzipcountryrow);
    
    shippingcontainer.add(shipbody);
    
    addresscontainer.add(billingcontainer);
    addresscontainer.add(shippingcontainer);
    
    add(addresscontainer);
    

    add(new HeadingBox("SHIPPING <B>METHOD</B>"));
    add(new GreyDescription("<B>Please pick a shipping method. We offer the following: <font color='#b51820'>1</font>. UPS: Ground, 3-Day Select, 2nd Day Air, Next Day Saver and Next Day Air. <font color='#b51820'>2</font>. PICK UP: Pick up at our Ontario, California location. <font color='#b51820'>3</font>. OTHER OPTIONS: Fill in your preferred shipping method.</b>"));
    
    PaddedBodyContainer shippingbody = new PaddedBodyContainer();
    FormHorizontalPanel2 shippingrow = new FormHorizontalPanel2();
    shippingrow.add(this.shippinginfofield.getShippingTypeField());
    shippingbody.add(shippingrow);
    add(shippingbody);
    

    add(new BlackLineBar(1000));
    

    HorizontalPanel menubarrow = new HorizontalPanel();
    
    menubarrow.add(this.step1_productanddesigninfo);
    menubarrow.add(this.step3_revieworder);
    
    add(menubarrow, new TableData(com.extjs.gxt.ui.client.Style.HorizontalAlignment.CENTER, Style.VerticalAlignment.MIDDLE));
    
    add(new BlackLineBar(1000));
  }
  

  private ClickHandler clickhandler = new ClickHandler()
  {

    public void onClick(ClickEvent event)
    {
      if (event.getSource() == OnlineCustomerInfoTab.this.step1_productanddesigninfo) {
        OnlineCustomerInfoTab.this.instantquotemainscreen.doStep(1);
      } else if (event.getSource() == OnlineCustomerInfoTab.this.step3_revieworder) {
        OnlineCustomerInfoTab.this.instantquotemainscreen.doStep(3);
      }
    }
  };
}
