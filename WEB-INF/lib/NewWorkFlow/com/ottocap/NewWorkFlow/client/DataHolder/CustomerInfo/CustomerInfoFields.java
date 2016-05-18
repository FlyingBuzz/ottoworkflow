package com.ottocap.NewWorkFlow.client.DataHolder.CustomerInfo;

import com.extjs.gxt.ui.client.binding.Bindings;
import com.extjs.gxt.ui.client.binding.FieldBinding;
import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FormEvent;
import com.extjs.gxt.ui.client.event.KeyListener;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.Observable;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.FileUploadField;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.FormPanel.Method;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.Validator;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.ottocap.NewWorkFlow.client.Icons.ResourceIcons;
import com.ottocap.NewWorkFlow.client.Icons.Resources;
import com.ottocap.NewWorkFlow.client.NewWorkFlow;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData.OrderType;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataAddress;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataAddress.AddressType;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataCustomerInformation;
import com.ottocap.NewWorkFlow.client.Services.WorkFlowServiceAsync;
import com.ottocap.NewWorkFlow.client.StoredUser;
import com.ottocap.NewWorkFlow.client.Widget.BuzzAsyncCallback;
import com.ottocap.NewWorkFlow.client.Widget.BuzzEvents;
import com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBox;
import com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBoxModelData;
import com.ottocap.NewWorkFlow.client.Widget.CompanyLogoField;
import com.ottocap.NewWorkFlow.client.Widget.FormHorizontalPanel2;
import java.util.ArrayList;

public class CustomerInfoFields
{
  private OrderData myorderdata;
  private TextField<String> contactname = new TextField();
  private TextField<String> phonenumber = new TextField();
  private TextField<String> customerterms = new TextField();
  private TextField<String> company = new TextField();
  private TextField<String> emailaddress = new TextField();
  private TextField<String> faxnumber = new TextField();
  private TextField<String> billcompanyname = new TextField();
  private TextField<String> billstreet1 = new TextField();
  private TextField<String> billstreet2 = new TextField();
  private TextField<String> billcity = new TextField();
  private TextField<String> billstate = new TextField();
  private TextField<String> billzipcode = new TextField();
  private TextField<String> billcountry = new TextField();
  private TextField<String> shipcompanyname = new TextField();
  private TextField<String> shipstreet1 = new TextField();
  private TextField<String> shipstreet2 = new TextField();
  private TextField<String> shipcity = new TextField();
  private TextField<String> shipstate = new TextField();
  private TextField<String> shipzipcode = new TextField();
  private TextField<String> shipcountry = new TextField();
  private TextField<String> attentionto = new TextField();
  private CheckBox sameasbilling = new CheckBox();
  private CheckBox blindshippingrequired = new CheckBox();
  private CheckBox residentialaddress = new CheckBox();
  private CheckBox taxexampt = new CheckBox();
  private CheckBox havedropshipment = new CheckBox();
  private NumberField dropshipmentamount = new NumberField();
  private OrderDataAddress oldshippingaddress = new OrderDataAddress(OrderDataAddress.AddressType.SHIPPING);
  

  private OtherComboBox eclipseaccountnumber = new OtherComboBox();
  private ArrayList<Character> allowed = new ArrayList();
  private OrderDataCustomerInformation loadedeclipseinformation = null;
  private String baseChars = "0123456789";
  private boolean badEclipseNumber = false;
  

  private FormPanel myuploadform = new FormPanel();
  private FileUploadField companylogofile = new FileUploadField();
  private HiddenField<Integer> companylogoeclipse = new HiddenField();
  private HiddenField<Integer> companylogohiddenkey = new HiddenField();
  private HiddenField<String> companylogoordertype = new HiddenField();
  private HiddenField<String> companylogosubmittype = new HiddenField();
  private HiddenField<String> uploadtype = new HiddenField();
  private Button uploadsubmit = new Button("Submit", Resources.ICONS.drive_upload());
  private CompanyLogoField companylogo = new CompanyLogoField();
  

  private Observable observable = new com.extjs.gxt.ui.client.event.BaseObservable();
  
  public CustomerInfoFields(OrderData myorderdata) {
    this.myorderdata = myorderdata;
    
    for (int i = 0; i < this.baseChars.length(); i++) {
      this.allowed.add(Character.valueOf(this.baseChars.charAt(i)));
    }
    
    RpcProxy<BasePagingLoadResult<OtherComboBoxModelData>> proxy = new RpcProxy()
    {
      protected void load(Object loadConfig, AsyncCallback<BasePagingLoadResult<OtherComboBoxModelData>> callback)
      {
        NewWorkFlow.get().getWorkFlowRPC().getEclipseAccountList((com.extjs.gxt.ui.client.data.PagingLoadConfig)loadConfig, callback);
      }
      
    };
    BasePagingLoader<BasePagingLoadResult<OtherComboBoxModelData>> loader = new BasePagingLoader(proxy);
    ListStore<OtherComboBoxModelData> store = new ListStore(loader);
    this.eclipseaccountnumber.setTemplate(getEclipseAccountTemplate());
    this.eclipseaccountnumber.setMinChars(2);
    this.eclipseaccountnumber.setMinListWidth(400);
    this.eclipseaccountnumber.setPageSize(10);
    this.eclipseaccountnumber.setHideTrigger(true);
    this.eclipseaccountnumber.setStore(store);
    this.eclipseaccountnumber.setItemSelector("div.search-item");
    this.eclipseaccountnumber.setMaxLength(10);
    this.eclipseaccountnumber.setValidateOnBlur(false);
    
    this.myuploadform.setMethod(FormPanel.Method.POST);
    this.myuploadform.setEncoding(com.extjs.gxt.ui.client.widget.form.FormPanel.Encoding.MULTIPART);
    this.myuploadform.setAction(NewWorkFlow.baseurl + "uploader");
    this.myuploadform.setHeaderVisible(false);
    this.myuploadform.setBorders(false);
    this.myuploadform.setBodyBorder(false);
    this.myuploadform.setFrame(false);
    this.myuploadform.setPadding(0);
    FormHorizontalPanel2 myuploadside = new FormHorizontalPanel2();
    myuploadside.add(this.companylogofile);
    myuploadside.addButton(this.uploadsubmit);
    myuploadside.add(this.uploadtype);
    myuploadside.add(this.companylogoeclipse);
    myuploadside.add(this.companylogoordertype);
    myuploadside.add(this.companylogosubmittype);
    myuploadside.add(this.companylogohiddenkey);
    this.myuploadform.add(myuploadside);
    

    if ((NewWorkFlow.get().getStoredUser().getAccessLevel() == 1) && (!NewWorkFlow.get().getStoredUser().getUsername().equals(myorderdata.getEmployeeId())) && (!myorderdata.getEmployeeId().equals(""))) {
      this.companylogofile.setEnabled(false);
      this.uploadsubmit.setEnabled(false);
    }
    

    this.companylogofile.setName("companylogofile");
    this.companylogofile.setButtonIcon(Resources.ICONS.folder_horizontal_open());
    this.companylogoeclipse.setName("companylogoeclipse");
    this.companylogoordertype.setName("companylogoordertype");
    this.companylogohiddenkey.setName("companylogohiddenkey");
    this.companylogosubmittype.setName("companylogosubmittype");
    this.uploadtype.setName("uploadtype");
    
    setLabels();
    setBindings();
    setFields();
    setValidators();
    addListeners();
  }
  



  private native String getEclipseAccountTemplate();
  



  private void setLabels()
  {
    this.contactname.setFieldLabel("Contact Name");
    this.eclipseaccountnumber.setFieldLabel("Eclipse Account Number");
    this.eclipseaccountnumber.setOtherTitle("");
    this.phonenumber.setFieldLabel("Phone Number");
    this.customerterms.setFieldLabel("Customer's Terms");
    this.company.setFieldLabel("Company");
    this.emailaddress.setFieldLabel("E-Mail Address");
    this.faxnumber.setFieldLabel("Fax Number");
    this.billcompanyname.setFieldLabel("Company Name");
    this.billstreet1.setFieldLabel("Address Line 1");
    this.billstreet2.setFieldLabel("Address Line 2");
    this.billcity.setFieldLabel("City");
    this.billstate.setFieldLabel("State");
    this.billzipcode.setFieldLabel("Zip");
    this.billcountry.setFieldLabel("Country");
    this.shipcompanyname.setFieldLabel("Company Name");
    this.shipstreet1.setFieldLabel("Address Line 1");
    this.shipstreet2.setFieldLabel("Address Line 2");
    this.shipcity.setFieldLabel("City");
    this.shipstate.setFieldLabel("State");
    this.shipzipcode.setFieldLabel("Zip");
    this.shipcountry.setFieldLabel("Country");
    this.attentionto.setFieldLabel("Attention To");
    this.sameasbilling.setBoxLabel("Same As Billing Address");
    this.blindshippingrequired.setBoxLabel("Blind Shipping Request");
    this.residentialaddress.setBoxLabel("Residential Address");
    this.taxexampt.setBoxLabel("Tax Exampt");
    this.companylogofile.setFieldLabel("Company Logo Uploader");
    this.companylogo.setFieldLabel("Company Logo");
    this.havedropshipment.setBoxLabel("Have Drop Shipment");
    this.dropshipmentamount.setFieldLabel("Drop Shipment Amount");
    this.dropshipmentamount.setAllowDecimals(false);
    this.dropshipmentamount.setAllowNegative(false);
    this.dropshipmentamount.setPropertyEditorType(Integer.class);
  }
  
  private void setBindings()
  {
    Bindings allbindings = new Bindings();
    allbindings.bind(this.myorderdata.getCustomerInformation());
    allbindings.addFieldBinding(new FieldBinding(this.contactname, "contactname"));
    allbindings.addFieldBinding(new FieldBinding(this.phonenumber, "phone"));
    allbindings.addFieldBinding(new FieldBinding(this.customerterms, "terms"));
    allbindings.addFieldBinding(new FieldBinding(this.company, "company"));
    allbindings.addFieldBinding(new FieldBinding(this.emailaddress, "email"));
    allbindings.addFieldBinding(new FieldBinding(this.faxnumber, "fax"));
    allbindings.addFieldBinding(new FieldBinding(this.companylogo, "companylogo"));
    allbindings.addFieldBinding(new FieldBinding(this.sameasbilling, "sameasbillingaddress"));
    allbindings.addFieldBinding(new FieldBinding(this.blindshippingrequired, "blindshippingrequired"));
    allbindings.addFieldBinding(new FieldBinding(this.attentionto, "shipattn"));
    allbindings.addFieldBinding(new FieldBinding(this.taxexampt, "taxexampt"));
    allbindings.addFieldBinding(new com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBoxFieldBinding(this.eclipseaccountnumber, "eclipseaccountnumber"));
    allbindings.addFieldBinding(new FieldBinding(this.dropshipmentamount, "dropshipmentamount"));
    
    Bindings billbindings = new Bindings();
    billbindings.bind(this.myorderdata.getCustomerInformation().getBillInformation());
    billbindings.addFieldBinding(new FieldBinding(this.billcompanyname, "company"));
    billbindings.addFieldBinding(new FieldBinding(this.billstreet1, "streetline1"));
    billbindings.addFieldBinding(new FieldBinding(this.billstreet2, "streetline2"));
    billbindings.addFieldBinding(new FieldBinding(this.billcity, "city"));
    billbindings.addFieldBinding(new FieldBinding(this.billstate, "state"));
    billbindings.addFieldBinding(new FieldBinding(this.billzipcode, "zip"));
    billbindings.addFieldBinding(new FieldBinding(this.billcountry, "country"));
    
    Bindings shipbindings = new Bindings();
    shipbindings.bind(this.myorderdata.getCustomerInformation().getShipInformation());
    shipbindings.addFieldBinding(new FieldBinding(this.shipcompanyname, "company"));
    shipbindings.addFieldBinding(new FieldBinding(this.shipstreet1, "streetline1"));
    shipbindings.addFieldBinding(new FieldBinding(this.shipstreet2, "streetline2"));
    shipbindings.addFieldBinding(new FieldBinding(this.shipcity, "city"));
    shipbindings.addFieldBinding(new FieldBinding(this.shipstate, "state"));
    shipbindings.addFieldBinding(new FieldBinding(this.shipzipcode, "zip"));
    shipbindings.addFieldBinding(new FieldBinding(this.shipcountry, "country"));
    shipbindings.addFieldBinding(new FieldBinding(this.residentialaddress, "residential"));
  }
  
  private void setValidators()
  {
    this.emailaddress.setValidator(this.validator);
    this.eclipseaccountnumber.setValidator(this.validator);
    this.companylogofile.setValidator(this.validator);
    this.contactname.setAllowBlank(false);
  }
  
  private void setFields()
  {
    this.havedropshipment.setValue(Boolean.valueOf(this.myorderdata.getCustomerInformation().getHaveDropShipment()));
    this.dropshipmentamount.setVisible(this.myorderdata.getCustomerInformation().getHaveDropShipment());
    this.uploadtype.setValue("Company Logo");
    this.oldshippingaddress.setCompany(this.myorderdata.getCustomerInformation().getShipInformation().getCompany());
    this.oldshippingaddress.setStreetLine1(this.myorderdata.getCustomerInformation().getShipInformation().getStreetLine1());
    this.oldshippingaddress.setStreetLine2(this.myorderdata.getCustomerInformation().getShipInformation().getStreetLine2());
    this.oldshippingaddress.setCity(this.myorderdata.getCustomerInformation().getShipInformation().getCity());
    this.oldshippingaddress.setState(this.myorderdata.getCustomerInformation().getShipInformation().getState());
    this.oldshippingaddress.setZip(this.myorderdata.getCustomerInformation().getShipInformation().getZip());
    this.oldshippingaddress.setCountry(this.myorderdata.getCustomerInformation().getShipInformation().getCountry());
    lockShipping();
  }
  
  private void addListeners() {
    this.havedropshipment.addListener(Events.Change, this.changelistener);
    this.sameasbilling.addListener(Events.Change, this.changelistener);
    this.billcompanyname.addListener(Events.Change, this.changelistener);
    this.billstreet1.addListener(Events.Change, this.changelistener);
    this.billstreet2.addListener(Events.Change, this.changelistener);
    this.billcity.addListener(Events.Change, this.changelistener);
    this.billstate.addListener(Events.Change, this.changelistener);
    this.billzipcode.addListener(Events.Change, this.changelistener);
    this.billcountry.addListener(Events.Change, this.changelistener);
    
    this.billcompanyname.addKeyListener(this.keylistener);
    this.billstreet1.addKeyListener(this.keylistener);
    this.billstreet2.addKeyListener(this.keylistener);
    this.billcity.addKeyListener(this.keylistener);
    this.billstate.addKeyListener(this.keylistener);
    this.billzipcode.addKeyListener(this.keylistener);
    this.billcountry.addKeyListener(this.keylistener);
    this.eclipseaccountnumber.addKeyListener(this.keylistener);
    this.eclipseaccountnumber.addListener(Events.Blur, this.blurlistener);
    
    this.uploadsubmit.addSelectionListener(this.selectionlistener);
    this.myuploadform.addListener(Events.Submit, this.submitlistener);
    
    this.companylogo.addListener(Events.TriggerClick, this.triggerclicklistener);
    this.companylogo.addListener(Events.TwinTriggerClick, this.twintriggerclicklistener);
  }
  
  public void addListener(EventType eventType, Listener<? extends BaseEvent> listener) {
    this.observable.addListener(eventType, listener);
  }
  
  private void lockShipping() {
    if (this.sameasbilling.getValue().booleanValue()) {
      this.shipcompanyname.setEnabled(false);
      this.shipstreet1.setEnabled(false);
      this.shipstreet2.setEnabled(false);
      this.shipcity.setEnabled(false);
      this.shipstate.setEnabled(false);
      this.shipzipcode.setEnabled(false);
      this.shipcountry.setEnabled(false);
      this.oldshippingaddress.setCompany(this.myorderdata.getCustomerInformation().getShipInformation().getCompany());
      this.oldshippingaddress.setStreetLine1(this.myorderdata.getCustomerInformation().getShipInformation().getStreetLine1());
      this.oldshippingaddress.setStreetLine2(this.myorderdata.getCustomerInformation().getShipInformation().getStreetLine2());
      this.oldshippingaddress.setCity(this.myorderdata.getCustomerInformation().getShipInformation().getCity());
      this.oldshippingaddress.setState(this.myorderdata.getCustomerInformation().getShipInformation().getState());
      this.oldshippingaddress.setZip(this.myorderdata.getCustomerInformation().getShipInformation().getZip());
      this.oldshippingaddress.setCountry(this.myorderdata.getCustomerInformation().getShipInformation().getCountry());
      this.myorderdata.getCustomerInformation().getShipInformation().setCompany(this.myorderdata.getCustomerInformation().getBillInformation().getCompany());
      this.myorderdata.getCustomerInformation().getShipInformation().setStreetLine1(this.myorderdata.getCustomerInformation().getBillInformation().getStreetLine1());
      this.myorderdata.getCustomerInformation().getShipInformation().setStreetLine2(this.myorderdata.getCustomerInformation().getBillInformation().getStreetLine2());
      this.myorderdata.getCustomerInformation().getShipInformation().setCity(this.myorderdata.getCustomerInformation().getBillInformation().getCity());
      this.myorderdata.getCustomerInformation().getShipInformation().setState(this.myorderdata.getCustomerInformation().getBillInformation().getState());
      this.myorderdata.getCustomerInformation().getShipInformation().setZip(this.myorderdata.getCustomerInformation().getBillInformation().getZip());
      this.myorderdata.getCustomerInformation().getShipInformation().setCountry(this.myorderdata.getCustomerInformation().getBillInformation().getCountry());
    } else {
      this.myorderdata.getCustomerInformation().getShipInformation().setCompany(this.oldshippingaddress.getCompany());
      this.myorderdata.getCustomerInformation().getShipInformation().setStreetLine1(this.oldshippingaddress.getStreetLine1());
      this.myorderdata.getCustomerInformation().getShipInformation().setStreetLine2(this.oldshippingaddress.getStreetLine2());
      this.myorderdata.getCustomerInformation().getShipInformation().setCity(this.oldshippingaddress.getCity());
      this.myorderdata.getCustomerInformation().getShipInformation().setState(this.oldshippingaddress.getState());
      this.myorderdata.getCustomerInformation().getShipInformation().setZip(this.oldshippingaddress.getZip());
      this.myorderdata.getCustomerInformation().getShipInformation().setCountry(this.oldshippingaddress.getCountry());
      this.shipcompanyname.setEnabled(true);
      this.shipstreet1.setEnabled(true);
      this.shipstreet2.setEnabled(true);
      this.shipcity.setEnabled(true);
      this.shipstate.setEnabled(true);
      this.shipzipcode.setEnabled(true);
      this.shipcountry.setEnabled(true);
    }
    this.shipcompanyname.setValue(this.myorderdata.getCustomerInformation().getShipInformation().getCompany());
    this.shipstreet1.setValue(this.myorderdata.getCustomerInformation().getShipInformation().getStreetLine1());
    this.shipstreet2.setValue(this.myorderdata.getCustomerInformation().getShipInformation().getStreetLine2());
    this.shipcity.setValue(this.myorderdata.getCustomerInformation().getShipInformation().getCity());
    this.shipstate.setValue(this.myorderdata.getCustomerInformation().getShipInformation().getState());
    this.shipzipcode.setValue(this.myorderdata.getCustomerInformation().getShipInformation().getZip());
    this.shipcountry.setValue(this.myorderdata.getCustomerInformation().getShipInformation().getCountry());
  }
  
  private Validator validator = new Validator()
  {
    public String validate(Field<?> field, String value)
    {
      if (field == CustomerInfoFields.this.emailaddress) {
        if (!((String)CustomerInfoFields.this.emailaddress.getValue()).toLowerCase().matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")) {
          return "Bad E-mail Address";
        }
      } else if (field == CustomerInfoFields.this.eclipseaccountnumber) {
        if (CustomerInfoFields.this.badEclipseNumber) {
          return "Eclipse Number Not Found";
        }
      } else if (field == CustomerInfoFields.this.companylogofile) {
        if ((CustomerInfoFields.this.eclipseaccountnumber.getValue() == null) || (CustomerInfoFields.this.eclipseaccountnumber.getValue().getValue() == null) || (CustomerInfoFields.this.eclipseaccountnumber.getValue().getValue().equals(""))) {
          return "Missing Eclipse Number";
        }
        try {
          Integer.valueOf(CustomerInfoFields.this.eclipseaccountnumber.getValue() != null ? CustomerInfoFields.this.eclipseaccountnumber.getValue().getValue() : null);
        } catch (Exception e) {
          return "Bad Eclipse Number";
        }
      }
      
      return null;
    }
  };
  
  public void queryEclipseNumber(int eclipseaccountnumber)
  {
    NewWorkFlow.get().getWorkFlowRPC().getEclipseAccount(eclipseaccountnumber, new AsyncCallback()
    {
      public void onFailure(Throwable caught)
      {
        Info.display("Error", caught.getLocalizedMessage());
      }
      
      public void onSuccess(EclipseDataHolder result)
      {
        CustomerInfoFields.this.loadEclipseNumber(result);
      }
    });
  }
  
  public void loadEclipseNumber(EclipseDataHolder result)
  {
    this.myorderdata.getCustomerInformation().setCompanyLogo(result.getCompanyLogo());
    if (result.getOrderDataCustomerInformation() == null) {
      this.badEclipseNumber = true;
      this.eclipseaccountnumber.validate();
      this.loadedeclipseinformation = null;
    } else {
      this.badEclipseNumber = false;
      this.eclipseaccountnumber.validate();
      this.loadedeclipseinformation = result.getOrderDataCustomerInformation();
      MessageBox mybox = new MessageBox();
      mybox.setTitleHtml("Confirm");
      mybox.setMessage("Do you want to load eclipse information?");
      mybox.setIcon(MessageBox.QUESTION);
      mybox.setButtons("yesno");
      mybox.addCallback(this.messageboxlistener);
      mybox.show();
    }
  }
  
  public void loadStoredEclipseInfo() {
    if (this.loadedeclipseinformation != null) {
      this.myorderdata.getCustomerInformation().setEclipseAccountNumber(this.loadedeclipseinformation.getEclipseAccountNumber());
      this.myorderdata.getCustomerInformation().setContactName(this.loadedeclipseinformation.getContactName());
      this.myorderdata.getCustomerInformation().setTerms(this.loadedeclipseinformation.getTerms());
      this.myorderdata.getCustomerInformation().setCompany(this.loadedeclipseinformation.getCompany());
      this.myorderdata.getCustomerInformation().setEmail(this.loadedeclipseinformation.getEmail());
      this.myorderdata.getCustomerInformation().setPhone(this.loadedeclipseinformation.getPhone());
      this.myorderdata.getCustomerInformation().setFax(this.loadedeclipseinformation.getFax());
      this.myorderdata.getCustomerInformation().setSameAsBillingAddress(this.loadedeclipseinformation.getSameAsBillingAddress());
      this.myorderdata.getCustomerInformation().getBillInformation().setCompany(this.loadedeclipseinformation.getBillInformation().getCompany());
      this.myorderdata.getCustomerInformation().getBillInformation().setStreetLine1(this.loadedeclipseinformation.getBillInformation().getStreetLine1());
      this.myorderdata.getCustomerInformation().getBillInformation().setStreetLine2(this.loadedeclipseinformation.getBillInformation().getStreetLine2());
      this.myorderdata.getCustomerInformation().getBillInformation().setCity(this.loadedeclipseinformation.getBillInformation().getCity());
      this.myorderdata.getCustomerInformation().getBillInformation().setState(this.loadedeclipseinformation.getBillInformation().getState());
      this.myorderdata.getCustomerInformation().getBillInformation().setZip(this.loadedeclipseinformation.getBillInformation().getZip());
      this.myorderdata.getCustomerInformation().getBillInformation().setCountry(this.loadedeclipseinformation.getBillInformation().getCountry());
      this.myorderdata.getCustomerInformation().getShipInformation().setCompany(this.loadedeclipseinformation.getShipInformation().getCompany());
      this.myorderdata.getCustomerInformation().getShipInformation().setStreetLine1(this.loadedeclipseinformation.getShipInformation().getStreetLine1());
      this.myorderdata.getCustomerInformation().getShipInformation().setStreetLine2(this.loadedeclipseinformation.getShipInformation().getStreetLine2());
      this.myorderdata.getCustomerInformation().getShipInformation().setCity(this.loadedeclipseinformation.getShipInformation().getCity());
      this.myorderdata.getCustomerInformation().getShipInformation().setState(this.loadedeclipseinformation.getShipInformation().getState());
      this.myorderdata.getCustomerInformation().getShipInformation().setZip(this.loadedeclipseinformation.getShipInformation().getZip());
      this.myorderdata.getCustomerInformation().getShipInformation().setCountry(this.loadedeclipseinformation.getShipInformation().getCountry());
      this.myorderdata.getCustomerInformation().getShipInformation().setResidential(this.loadedeclipseinformation.getShipInformation().getResidential());
      setFields();
    }
  }
  
  private Listener<BaseEvent> triggerclicklistener = new Listener()
  {
    public void handleEvent(BaseEvent be)
    {
      if (be.getSource().equals(CustomerInfoFields.this.companylogo)) {
        if (CustomerInfoFields.this.myorderdata.getCustomerInformation().getCompanyLogo().equals("Default")) {
          Window.open(NewWorkFlow.filepath + "pdflogos/" + CustomerInfoFields.this.myorderdata.getCustomerInformation().getEclipseAccountNumber() + "/logo.jpg", "popup", "");
        } else if (!CustomerInfoFields.this.myorderdata.getCustomerInformation().getCompanyLogo().equals("")) {
          if (CustomerInfoFields.this.myorderdata.getOrderType() == OrderData.OrderType.DOMESTIC) {
            Window.open(NewWorkFlow.filepath + "DomesticData/" + CustomerInfoFields.this.myorderdata.getHiddenKey() + "/companylogo/" + CustomerInfoFields.this.myorderdata.getCustomerInformation().getCompanyLogo(), "popup", "");
          } else if (CustomerInfoFields.this.myorderdata.getOrderType() == OrderData.OrderType.OVERSEAS) {
            Window.open(NewWorkFlow.filepath + "OverseasData/" + CustomerInfoFields.this.myorderdata.getHiddenKey() + "/companylogo/" + CustomerInfoFields.this.myorderdata.getCustomerInformation().getCompanyLogo(), "popup", "");
          }
        }
      }
    }
  };
  

  private Listener<BaseEvent> twintriggerclicklistener = new Listener()
  {
    public void handleEvent(BaseEvent be)
    {
      if (be.getSource().equals(CustomerInfoFields.this.companylogo)) {
        if (CustomerInfoFields.this.myorderdata.getCustomerInformation().getCompanyLogo().equals("Default")) {
          MessageBox.confirm("Remove Global Logo", "Are you sure you want to remove the global logo?", CustomerInfoFields.this.removelogocallback);
        } else if (!CustomerInfoFields.this.myorderdata.getCustomerInformation().getCompanyLogo().equals("")) {
          MessageBox.confirm("Remove Local WorkOrder Logo", "Are you sure you want to remove the local workorder logo?", CustomerInfoFields.this.removelogocallback);
        }
      }
    }
  };
  

  private Listener<MessageBoxEvent> removelogocallback = new Listener()
  {
    public void handleEvent(MessageBoxEvent be)
    {
      if (be.getButtonClicked().getHtml().equals("Yes")) {
        if (CustomerInfoFields.this.myorderdata.getCustomerInformation().getCompanyLogo().equals("Default")) {
          NewWorkFlow.get().getWorkFlowRPC().getRemoveEclipseAccountLogo(CustomerInfoFields.this.myorderdata.getCustomerInformation().getEclipseAccountNumber().intValue(), CustomerInfoFields.this.removecompanylogocallback);
        } else if (!CustomerInfoFields.this.myorderdata.getCustomerInformation().getCompanyLogo().equals("")) {
          if (CustomerInfoFields.this.myorderdata.getCustomerInformation().getEclipseAccountNumber() != null) {
            NewWorkFlow.get().getWorkFlowRPC().getEclipseAccount(CustomerInfoFields.this.myorderdata.getCustomerInformation().getEclipseAccountNumber().intValue(), CustomerInfoFields.this.checkcompanylogocallback);
          } else {
            CustomerInfoFields.this.myorderdata.getCustomerInformation().setCompanyLogo("");
          }
        }
      }
    }
  };
  


  private AsyncCallback<Void> removecompanylogocallback = new AsyncCallback()
  {
    public void onFailure(Throwable caught) {}
    


    public void onSuccess(Void result)
    {
      CustomerInfoFields.this.myorderdata.getCustomerInformation().setCompanyLogo("");
    }
  };
  

  private AsyncCallback<EclipseDataHolder> checkcompanylogocallback = new AsyncCallback()
  {
    public void onFailure(Throwable caught) {}
    


    public void onSuccess(EclipseDataHolder result)
    {
      CustomerInfoFields.this.myorderdata.getCustomerInformation().setCompanyLogo(result.getCompanyLogo());
    }
  };
  

  private Listener<MessageBoxEvent> messageboxlistener = new Listener()
  {
    public void handleEvent(MessageBoxEvent be)
    {
      if (be.getButtonClicked().getHtml().equals("Yes")) {
        CustomerInfoFields.this.loadStoredEclipseInfo();
      }
    }
  };
  
  private SelectionListener<ButtonEvent> selectionlistener = new SelectionListener()
  {
    public void componentSelected(ButtonEvent ce)
    {
      CustomerInfoFields.this.uploadsubmit.disable();
      if (ce.getSource().equals(CustomerInfoFields.this.uploadsubmit)) {
        if (CustomerInfoFields.this.myorderdata.getHiddenKey() == null) {
          NewWorkFlow.get().getWorkFlowRPC().saveOrder(CustomerInfoFields.this.myorderdata, CustomerInfoFields.this.companylogosaveordercallback);
        } else {
          CustomerInfoFields.this.doUploadCompanyLogo();
        }
      }
    }
  };
  

  private BuzzAsyncCallback<Integer> companylogosaveordercallback = new BuzzAsyncCallback()
  {
    public void onFailure(Throwable caught)
    {
      NewWorkFlow.get().reLogin2(caught, CustomerInfoFields.this.companylogosaveordercallback);
    }
    
    public void doRetry()
    {
      NewWorkFlow.get().getWorkFlowRPC().saveOrder(CustomerInfoFields.this.myorderdata, CustomerInfoFields.this.companylogosaveordercallback);
    }
    
    public void onSuccess(Integer result)
    {
      CustomerInfoFields.this.myorderdata.setHiddenKey(result);
      CustomerInfoFields.this.doTabLabelChangeEvent();
      CustomerInfoFields.this.doUploadCompanyLogo();
    }
  };
  
  private void doTabLabelChangeEvent()
  {
    BaseEvent be = new BaseEvent(BuzzEvents.TabLabelChange);
    be.setSource(this.myuploadform);
    this.observable.fireEvent(BuzzEvents.TabLabelChange, be);
  }
  
  private void doUploadCompanyLogo()
  {
    MessageBox.confirm("Upload Type", "Do you want to upload a global company logo?", this.uploadtypecallback);
  }
  
  private Listener<MessageBoxEvent> uploadtypecallback = new Listener() {
    public void handleEvent(MessageBoxEvent ce) {
      Button btn = ce.getButtonClicked();
      if (btn.getHtml().equals("Yes")) {
        CustomerInfoFields.this.companylogosubmittype.setValue("global");
        if (!CustomerInfoFields.this.companylogofile.validate()) {
          MessageBox.alert("Error", "Missing Eclipse Account Number", null);
          CustomerInfoFields.this.uploadsubmit.enable();
        }
      }
      else {
        CustomerInfoFields.this.companylogosubmittype.setValue("local");
      }
      
      CustomerInfoFields.this.companylogoeclipse.setValue(CustomerInfoFields.this.myorderdata.getCustomerInformation().getEclipseAccountNumber());
      CustomerInfoFields.this.companylogohiddenkey.setValue(CustomerInfoFields.this.myorderdata.getHiddenKey());
      if (CustomerInfoFields.this.myorderdata.getOrderType() == OrderData.OrderType.DOMESTIC) {
        CustomerInfoFields.this.companylogoordertype.setValue("Domestic");
      } else {
        CustomerInfoFields.this.companylogoordertype.setValue("Overseas");
      }
      CustomerInfoFields.this.myuploadform.submit();
    }
  };
  
  private Listener<BaseEvent> blurlistener = new Listener()
  {
    public void handleEvent(BaseEvent be)
    {
      if (be.getSource().equals(CustomerInfoFields.this.eclipseaccountnumber)) {
        if ((CustomerInfoFields.this.eclipseaccountnumber.getValue() == null) || (CustomerInfoFields.this.eclipseaccountnumber.getValue().getValue() == null) || (CustomerInfoFields.this.eclipseaccountnumber.getValue().getValue().equals("")))
        {
          CustomerInfoFields.this.myorderdata.getCustomerInformation().setCompanyLogo("");
          CustomerInfoFields.this.badEclipseNumber = false;
          CustomerInfoFields.this.eclipseaccountnumber.validate();
          CustomerInfoFields.this.loadedeclipseinformation = null;
        } else {
          try {
            CustomerInfoFields.this.queryEclipseNumber(Integer.valueOf(CustomerInfoFields.this.eclipseaccountnumber.getValue().getValue()).intValue());
          } catch (Exception e) {
            CustomerInfoFields.this.badEclipseNumber = true;
            CustomerInfoFields.this.eclipseaccountnumber.validate();
            CustomerInfoFields.this.loadedeclipseinformation = null;
            
            CustomerInfoFields.this.myorderdata.getCustomerInformation().setCompanyLogo("");
          }
        }
      }
    }
  };
  


  private Listener<BaseEvent> changelistener = new Listener()
  {
    public void handleEvent(BaseEvent be)
    {
      if (be.getSource().equals(CustomerInfoFields.this.sameasbilling)) {
        CustomerInfoFields.this.lockShipping();
      } else if (be.getSource().equals(CustomerInfoFields.this.billcompanyname)) {
        if (CustomerInfoFields.this.myorderdata.getCustomerInformation().getSameAsBillingAddress()) {
          CustomerInfoFields.this.myorderdata.getCustomerInformation().getShipInformation().setCompany((String)CustomerInfoFields.this.billcompanyname.getValue());
        }
      } else if (be.getSource().equals(CustomerInfoFields.this.billstreet1)) {
        if (CustomerInfoFields.this.myorderdata.getCustomerInformation().getSameAsBillingAddress()) {
          CustomerInfoFields.this.myorderdata.getCustomerInformation().getShipInformation().setStreetLine1((String)CustomerInfoFields.this.billstreet1.getValue());
        }
      } else if (be.getSource().equals(CustomerInfoFields.this.billstreet2)) {
        if (CustomerInfoFields.this.myorderdata.getCustomerInformation().getSameAsBillingAddress()) {
          CustomerInfoFields.this.myorderdata.getCustomerInformation().getShipInformation().setStreetLine2((String)CustomerInfoFields.this.billstreet2.getValue());
        }
      } else if (be.getSource().equals(CustomerInfoFields.this.billcity)) {
        if (CustomerInfoFields.this.myorderdata.getCustomerInformation().getSameAsBillingAddress()) {
          CustomerInfoFields.this.myorderdata.getCustomerInformation().getShipInformation().setCity((String)CustomerInfoFields.this.billcity.getValue());
        }
      } else if (be.getSource().equals(CustomerInfoFields.this.billstate)) {
        if (CustomerInfoFields.this.myorderdata.getCustomerInformation().getSameAsBillingAddress()) {
          CustomerInfoFields.this.myorderdata.getCustomerInformation().getShipInformation().setState((String)CustomerInfoFields.this.billstate.getValue());
        }
      } else if (be.getSource().equals(CustomerInfoFields.this.billzipcode)) {
        if (CustomerInfoFields.this.myorderdata.getCustomerInformation().getSameAsBillingAddress()) {
          CustomerInfoFields.this.myorderdata.getCustomerInformation().getShipInformation().setCompany((String)CustomerInfoFields.this.shipcompanyname.getValue());
        }
      } else if (be.getSource().equals(CustomerInfoFields.this.billcountry)) {
        if (CustomerInfoFields.this.myorderdata.getCustomerInformation().getSameAsBillingAddress()) {
          CustomerInfoFields.this.myorderdata.getCustomerInformation().getShipInformation().setCountry((String)CustomerInfoFields.this.shipcountry.getValue());
        }
      } else if (be.getSource().equals(CustomerInfoFields.this.havedropshipment)) {
        CustomerInfoFields.this.myorderdata.getCustomerInformation().setHaveDropShipment(CustomerInfoFields.this.havedropshipment.getValue().booleanValue());
        CustomerInfoFields.this.dropshipmentamount.setVisible(CustomerInfoFields.this.havedropshipment.getValue().booleanValue());
      }
    }
  };
  

  private Listener<FormEvent> submitlistener = new Listener()
  {
    public void handleEvent(FormEvent be)
    {
      if (be.getComponent().equals(CustomerInfoFields.this.myuploadform)) {
        String htmlstring = com.ottocap.NewWorkFlow.client.Widget.StringUtils.unescapeHTML(be.getResultHtml(), 0);
        
        htmlstring = htmlstring.replace("<pre>", "");
        htmlstring = htmlstring.replace("</pre>", "");
        JSONObject jsonObject = com.google.gwt.json.client.JSONParser.parseStrict(htmlstring).isObject();
        if (!jsonObject.containsKey("error")) {
          CustomerInfoFields.this.myorderdata.getCustomerInformation().setCompanyLogo(jsonObject.get("Filename").isString().stringValue());
          Info.display("Success", "Company Logo Have Been Uploaded");
          CustomerInfoFields.this.myuploadform.enable();
        } else {
          Throwable myerror = new Throwable(jsonObject.get("error").isString().stringValue());
          NewWorkFlow.get().reLogin2(myerror, CustomerInfoFields.this.uploadlogorelogin);
        }
      }
    }
  };
  

  BuzzAsyncCallback<String> uploadlogorelogin = new BuzzAsyncCallback()
  {
    public void onFailure(Throwable caught)
    {
      CustomerInfoFields.this.myuploadform.enable();
    }
    
    public void onSuccess(String result)
    {
      CustomerInfoFields.this.myuploadform.submit();
    }
    
    public void doRetry()
    {
      CustomerInfoFields.this.myuploadform.submit();
    }
  };
  

  private KeyListener keylistener = new KeyListener()
  {
    public void componentKeyUp(ComponentEvent event) {
      if (CustomerInfoFields.this.myorderdata.getCustomerInformation().getSameAsBillingAddress()) {
        if (event.getComponent().equals(CustomerInfoFields.this.billcompanyname)) {
          CustomerInfoFields.this.shipcompanyname.setValue((String)CustomerInfoFields.this.billcompanyname.getValue());
        } else if (event.getComponent().equals(CustomerInfoFields.this.billstreet1)) {
          CustomerInfoFields.this.shipstreet1.setValue((String)CustomerInfoFields.this.billstreet1.getValue());
        } else if (event.getComponent().equals(CustomerInfoFields.this.billstreet2)) {
          CustomerInfoFields.this.shipstreet2.setValue((String)CustomerInfoFields.this.billstreet2.getValue());
        } else if (event.getComponent().equals(CustomerInfoFields.this.billcity)) {
          CustomerInfoFields.this.shipcity.setValue((String)CustomerInfoFields.this.billcity.getValue());
        } else if (event.getComponent().equals(CustomerInfoFields.this.billstate)) {
          CustomerInfoFields.this.shipstate.setValue((String)CustomerInfoFields.this.billstate.getValue());
        } else if (event.getComponent().equals(CustomerInfoFields.this.billzipcode)) {
          CustomerInfoFields.this.shipzipcode.setValue((String)CustomerInfoFields.this.billzipcode.getValue());
        } else if (event.getComponent().equals(CustomerInfoFields.this.billcountry)) {
          CustomerInfoFields.this.shipcountry.setValue((String)CustomerInfoFields.this.billcountry.getValue());
        }
      }
    }
    
    public void componentKeyPress(ComponentEvent event)
    {
      super.componentKeyPress(event);
      
      if (event.getComponent().equals(CustomerInfoFields.this.eclipseaccountnumber))
      {
        if (event.isSpecialKey(getKeyCode(event.getEvent()))) {
          return;
        }
        
        char key = getChar(event.getEvent());
        
        if ((!CustomerInfoFields.this.allowed.contains(Character.valueOf(key))) || ((CustomerInfoFields.this.eclipseaccountnumber.getSelectedText().isEmpty()) && (CustomerInfoFields.this.eclipseaccountnumber.getRawValue().length() == 10))) {
          event.stopEvent();
        }
      }
    }
    



    private native char getChar(NativeEvent paramAnonymousNativeEvent);
    



    private native int getKeyCode(NativeEvent paramAnonymousNativeEvent);
  };
  


  public TextField<String> getContactNameField()
  {
    return this.contactname;
  }
  
  public TextField<String> getPhoneNumberField() {
    return this.phonenumber;
  }
  
  public TextField<String> getCustomerTermsField() {
    return this.customerterms;
  }
  
  public TextField<String> getCompanyField() {
    return this.company;
  }
  
  public TextField<String> getEmailAddressField() {
    return this.emailaddress;
  }
  
  public TextField<String> getFaxNumberField() {
    return this.faxnumber;
  }
  
  public TextField<String> getBillCompanyNameField() {
    return this.billcompanyname;
  }
  
  public TextField<String> getBillStreet1Field() {
    return this.billstreet1;
  }
  
  public TextField<String> getBillStreet2Field() {
    return this.billstreet2;
  }
  
  public TextField<String> getBillCityField() {
    return this.billcity;
  }
  
  public TextField<String> getBillStateField() {
    return this.billstate;
  }
  
  public TextField<String> getBillZipCodeField() {
    return this.billzipcode;
  }
  
  public TextField<String> getBillCountryField() {
    return this.billcountry;
  }
  
  public TextField<String> getShipCompanyNameField() {
    return this.shipcompanyname;
  }
  
  public TextField<String> getShipStreet1Field() {
    return this.shipstreet1;
  }
  
  public TextField<String> getShipStreet2Field() {
    return this.shipstreet2;
  }
  
  public TextField<String> getShipCityField() {
    return this.shipcity;
  }
  
  public TextField<String> getShipStateField() {
    return this.shipstate;
  }
  
  public TextField<String> getShipZipCodeField() {
    return this.shipzipcode;
  }
  
  public TextField<String> getShipCountryField() {
    return this.shipcountry;
  }
  
  public TextField<String> getAttentionToField() {
    return this.attentionto;
  }
  
  public CheckBox getSameAsBillingField() {
    return this.sameasbilling;
  }
  
  public CheckBox getBlindShippingRequiredField() {
    return this.blindshippingrequired;
  }
  
  public CheckBox getTaxExamptField() {
    return this.taxexampt;
  }
  
  public CheckBox getResidentialAddressField() {
    return this.residentialaddress;
  }
  
  public OtherComboBox getEclipseAccountNumberField() {
    return this.eclipseaccountnumber;
  }
  
  public CompanyLogoField getCompanyLogoField() {
    return this.companylogo;
  }
  
  public FormPanel getCompanyLogoUploadFormField() {
    return this.myuploadform;
  }
  
  public CheckBox getHaveDropShipmentField() {
    return this.havedropshipment;
  }
  
  public NumberField getDropShipmentAmountField() {
    return this.dropshipmentamount;
  }
}
