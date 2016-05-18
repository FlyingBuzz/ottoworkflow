package com.ottocap.NewWorkFlow.client.DataHolder.CustomerInfo;

import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.form.CheckBoxGroup;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.google.gwt.user.client.Element;
import com.ottocap.NewWorkFlow.client.EditOrder.OrderTab;
import com.ottocap.NewWorkFlow.client.NewWorkFlow;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData;
import com.ottocap.NewWorkFlow.client.Widget.FormHorizontalPanel2;

public class CustomerInfoLayout extends LayoutContainer
{
  private OrderTab ordertab;
  private CustomerInfoFields customerinfofields;
  private OrderData myorderdata;
  
  public CustomerInfoLayout(OrderData myorderdata, OrderTab ordertab)
  {
    this.myorderdata = myorderdata;
    this.ordertab = ordertab;
  }
  
  protected void onRender(Element parent, int index) {
    super.onRender(parent, index);
    RunMethod();
  }
  



  public void RunMethod()
  {
    this.customerinfofields = new CustomerInfoFields(this.myorderdata);
    
    FormHorizontalPanel2 customerinfo_horz1 = new FormHorizontalPanel2();
    FormHorizontalPanel2 customerinfo_horz2 = new FormHorizontalPanel2();
    
    customerinfo_horz1.add(this.customerinfofields.getEclipseAccountNumberField());
    customerinfo_horz1.add(this.customerinfofields.getContactNameField());
    customerinfo_horz1.add(this.customerinfofields.getCustomerTermsField());
    customerinfo_horz1.add(this.customerinfofields.getCompanyField());
    customerinfo_horz1.add(this.customerinfofields.getEmailAddressField());
    
    customerinfo_horz2.add(this.customerinfofields.getPhoneNumberField());
    customerinfo_horz2.add(this.customerinfofields.getFaxNumberField());
    customerinfo_horz2.add(this.customerinfofields.getCompanyLogoField());
    customerinfo_horz2.add(this.customerinfofields.getCompanyLogoUploadFormField());
    
    FieldSet billinformationpanel = new FieldSet();
    billinformationpanel.setHeadingHtml("BILLING INFORMATION");
    
    FormHorizontalPanel2 billinfo_horz1 = new FormHorizontalPanel2();
    billinfo_horz1.add(this.customerinfofields.getBillCompanyNameField());
    
    if (NewWorkFlow.get().isFaya.booleanValue()) {
      billinfo_horz1.addCheckBox(this.customerinfofields.getTaxExamptField());
    }
    
    FormHorizontalPanel2 billinfo_horz2 = new FormHorizontalPanel2();
    billinfo_horz2.add(this.customerinfofields.getBillStreet1Field(), new FormData(418, 0));
    
    FormHorizontalPanel2 billinfo_horz3 = new FormHorizontalPanel2();
    billinfo_horz3.add(this.customerinfofields.getBillStreet2Field(), new FormData(418, 0));
    
    FormHorizontalPanel2 billinfo_horz4 = new FormHorizontalPanel2();
    billinfo_horz4.add(this.customerinfofields.getBillCityField());
    billinfo_horz4.add(this.customerinfofields.getBillStateField());
    
    FormHorizontalPanel2 billinfo_horz5 = new FormHorizontalPanel2();
    billinfo_horz5.add(this.customerinfofields.getBillZipCodeField());
    billinfo_horz5.add(this.customerinfofields.getBillCountryField());
    
    billinformationpanel.add(billinfo_horz1);
    billinformationpanel.add(billinfo_horz2);
    billinformationpanel.add(billinfo_horz3);
    billinformationpanel.add(billinfo_horz4);
    billinformationpanel.add(billinfo_horz5);
    
    FieldSet shippinginformationpanel = new FieldSet();
    shippinginformationpanel.setHeadingHtml("SHIPPING INFORMATION");
    
    HorizontalPanel shipinfo_horz1 = new HorizontalPanel();
    LayoutContainer shipinfo_ver1 = new LayoutContainer();
    
    VerticalPanel checkboxgroups = new VerticalPanel();
    FormHorizontalPanel2 checkboxspanel = new FormHorizontalPanel2();
    
    CheckBoxGroup shipinfo_ver2 = new CheckBoxGroup();
    shipinfo_ver2.setOrientation(Style.Orientation.VERTICAL);
    

    shipinfo_horz1.add(shipinfo_ver1);
    checkboxspanel.addCheckBox(shipinfo_ver2);
    checkboxgroups.add(checkboxspanel);
    shipinfo_horz1.add(checkboxgroups);
    
    FormHorizontalPanel2 shipinfo_horz2 = new FormHorizontalPanel2();
    shipinfo_horz2.add(this.customerinfofields.getShipCompanyNameField());
    shipinfo_horz2.add(this.customerinfofields.getAttentionToField());
    
    FormHorizontalPanel2 shipinfo_horz3 = new FormHorizontalPanel2();
    shipinfo_horz3.add(this.customerinfofields.getShipStreet1Field(), new FormData(418, 0));
    
    FormHorizontalPanel2 shipinfo_horz4 = new FormHorizontalPanel2();
    shipinfo_horz4.add(this.customerinfofields.getShipStreet2Field(), new FormData(418, 0));
    
    FormHorizontalPanel2 shipinfo_horz5 = new FormHorizontalPanel2();
    shipinfo_horz5.add(this.customerinfofields.getShipCityField());
    shipinfo_horz5.add(this.customerinfofields.getShipStateField());
    
    FormHorizontalPanel2 shipinfo_horz6 = new FormHorizontalPanel2();
    shipinfo_horz6.add(this.customerinfofields.getShipZipCodeField());
    shipinfo_horz6.add(this.customerinfofields.getShipCountryField());
    
    shipinfo_ver1.add(shipinfo_horz2);
    shipinfo_ver1.add(shipinfo_horz3);
    shipinfo_ver1.add(shipinfo_horz4);
    shipinfo_ver1.add(shipinfo_horz5);
    shipinfo_ver1.add(shipinfo_horz6);
    
    shipinfo_ver2.add(this.customerinfofields.getSameAsBillingField());
    shipinfo_ver2.add(this.customerinfofields.getBlindShippingRequiredField());
    shipinfo_ver2.add(this.customerinfofields.getResidentialAddressField());
    shipinfo_ver2.add(this.customerinfofields.getHaveDropShipmentField());
    
    FormHorizontalPanel2 shipinfo_horz7 = new FormHorizontalPanel2();
    shipinfo_horz7.add(this.customerinfofields.getDropShipmentAmountField(), new FormData(104, 0));
    checkboxgroups.add(shipinfo_horz7);
    
    shippinginformationpanel.add(shipinfo_horz1);
    
    HorizontalPanel addresscontrainer = new HorizontalPanel();
    addresscontrainer.add(billinformationpanel);
    addresscontrainer.add(shippinginformationpanel);
    
    add(customerinfo_horz1);
    add(customerinfo_horz2);
    add(addresscontrainer);
    
    this.customerinfofields.addListener(com.ottocap.NewWorkFlow.client.Widget.BuzzEvents.TabLabelChange, this.tablabelchangelistener);
  }
  

  private Listener<BaseEvent> tablabelchangelistener = new Listener()
  {

    public void handleEvent(BaseEvent be)
    {
      if (be.getSource().equals(CustomerInfoLayout.this.customerinfofields.getCompanyLogoUploadFormField())) {
        CustomerInfoLayout.this.ordertab.setTabHeader();
      }
    }
  };
}
