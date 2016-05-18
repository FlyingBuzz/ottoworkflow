package com.ottocap.NewWorkFlow.client.DataHolder.ProductsInfo;

import com.extjs.gxt.ui.client.binding.Bindings;
import com.extjs.gxt.ui.client.binding.FieldBinding;
import com.extjs.gxt.ui.client.core.FastMap;
import com.extjs.gxt.ui.client.data.BasePagingLoadConfig;
import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.data.LoadEvent;
import com.extjs.gxt.ui.client.data.Loader;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FormEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.Observable;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.FileUploadField;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.Radio;
import com.extjs.gxt.ui.client.widget.form.RadioGroup;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.Validator;
import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.ottocap.NewWorkFlow.client.DataHolder.Stores.DataStores;
import com.ottocap.NewWorkFlow.client.DataHolder.Stores.NewStyleStore;
import com.ottocap.NewWorkFlow.client.Icons.ResourceIcons;
import com.ottocap.NewWorkFlow.client.Icons.Resources;
import com.ottocap.NewWorkFlow.client.NewWorkFlow;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData.OrderType;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataItem;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogo;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataSet;
import com.ottocap.NewWorkFlow.client.Services.WorkFlowServiceAsync;
import com.ottocap.NewWorkFlow.client.StoredUser;
import com.ottocap.NewWorkFlow.client.StyleInformationData;
import com.ottocap.NewWorkFlow.client.StyleInformationData.StyleType;
import com.ottocap.NewWorkFlow.client.Widget.BuzzAsyncCallback;
import com.ottocap.NewWorkFlow.client.Widget.BuzzEvents;
import com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBox;
import com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBoxFieldBinding;
import com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBoxModelData;
import com.ottocap.NewWorkFlow.client.Widget.FilenameField;
import com.ottocap.NewWorkFlow.client.Widget.FormHorizontalPanel2;
import com.ottocap.NewWorkFlow.client.Widget.ImageHelper;
import com.ottocap.NewWorkFlow.client.Widget.USDMoneyField;
import java.util.List;

public class ItemPortletField
{
  private OrderData orderdata;
  private OrderDataSet orderdataset;
  private OrderDataItem item;
  private FormPanel myuploadform = new FormPanel();
  private FilenameField isamplefield = new FilenameField();
  private FileUploadField isampleimagefile = new FileUploadField();
  private HiddenField<Integer> isamplehiddenkey = new HiddenField();
  private HiddenField<String> isampleordertype = new HiddenField();
  private HiddenField<String> uploadtype = new HiddenField();
  private Button uploadsubmit = new Button("Submit", Resources.ICONS.drive_upload());
  private CheckBox emailproof = new CheckBox();
  private CheckBox shipproof = new CheckBox();
  private NumberField proofstodo = new NumberField();
  private NumberField proofstotaldone = new NumberField();
  private NumberField proofstotalemail = new NumberField();
  private NumberField proofstotalship = new NumberField();
  private TextField<String> customstylename = new TextField();
  private USDMoneyField productdiscount = new USDMoneyField();
  
  private OtherComboBox sampleapprovedlist = new OtherComboBox();
  
  private OtherComboBox productcategory = new OtherComboBox();
  private OtherComboBox productstylenumber = new OtherComboBox();
  private OtherComboBox productcolor = new OtherComboBox();
  private OtherComboBox productsize = new OtherComboBox();
  private NumberField quantity = new NumberField();
  private USDMoneyField fobdozenprice = new USDMoneyField();
  private USDMoneyField fobdozenmarkupprice = new USDMoneyField();
  private OtherComboBox frontpanelcolor = new OtherComboBox();
  private OtherComboBox sidebackpanelcolor = new OtherComboBox();
  private OtherComboBox backpanelcolor = new OtherComboBox();
  private OtherComboBox sidepanelcolor = new OtherComboBox();
  private OtherComboBox visorstylenumber = new OtherComboBox();
  private OtherComboBox primaryvisorcolor = new OtherComboBox();
  private OtherComboBox visortrimedgecolor = new OtherComboBox();
  private OtherComboBox visorsandwichcolor = new OtherComboBox();
  private OtherComboBox distressedvisorinsidecolor = new OtherComboBox();
  private OtherComboBox undervisorcolor = new OtherComboBox();
  private OtherComboBox closurestylenumber = new OtherComboBox();
  private OtherComboBox closurestrapcolor = new OtherComboBox();
  private OtherComboBox eyeletstylenumber = new OtherComboBox();
  private OtherComboBox fronteyeletcolor = new OtherComboBox();
  private OtherComboBox sidebackeyeletcolor = new OtherComboBox();
  private OtherComboBox backeyeletcolor = new OtherComboBox();
  private OtherComboBox sideeyeletcolor = new OtherComboBox();
  private OtherComboBox buttoncolor = new OtherComboBox();
  private OtherComboBox innertapingcolor = new OtherComboBox();
  private OtherComboBox sweatbandstylenumber = new OtherComboBox();
  private OtherComboBox sweatbandcolor = new OtherComboBox();
  private OtherComboBox sweatbandstripecolor = new OtherComboBox();
  private OtherComboBox vendornumber = new OtherComboBox();
  private OtherComboBox artworktype = new OtherComboBox();
  private OtherComboBox profile = new OtherComboBox();
  private OtherComboBox frontpanelfabric = new OtherComboBox();
  private OtherComboBox sidebackpanelfabric = new OtherComboBox();
  private OtherComboBox backpanelfabric = new OtherComboBox();
  private OtherComboBox sidepanelfabric = new OtherComboBox();
  private OtherComboBox numberofpanels = new OtherComboBox();
  private OtherComboBox crownconstruction = new OtherComboBox();
  private OtherComboBox visorrowstitching = new OtherComboBox();
  private OtherComboBox visorstitchcolor = new OtherComboBox();
  private OtherComboBox panelstitchcolor = new OtherComboBox();
  
  private TextField<String> artworktypecomments = new TextField();
  
  private CheckBox polybag = new CheckBox();
  private CheckBox oakleaves = new CheckBox();
  private CheckBox tagging = new CheckBox();
  private CheckBox stickers = new CheckBox();
  private CheckBox sewingpatches = new CheckBox();
  private CheckBox heatpresspatches = new CheckBox();
  private CheckBox removeinnerprintedlabel = new CheckBox();
  private CheckBox addinnerprintedlabel = new CheckBox();
  private CheckBox removeinnerwovenlabel = new CheckBox();
  private CheckBox addinnerwovenlabel = new CheckBox();
  private CheckBox personalization = new CheckBox();
  private NumberField personaliztionchanges = new NumberField();
  private RadioGroup privateorstandardlabelgroup = new RadioGroup();
  private Radio standardlabelradio = new Radio();
  private Radio privatelabelradio = new Radio();
  private RadioGroup privateorstandardpackaginggroup = new RadioGroup();
  private Radio standardpackagingradio = new Radio();
  private Radio privatepackagingradio = new Radio();
  private RadioGroup privateorstandardshippingmarkgroup = new RadioGroup();
  private Radio standardshippingmarkradio = new Radio();
  private Radio privateshippingmarkradio = new Radio();
  
  private RadioGroup advancecustomizationgroup = new RadioGroup();
  private Radio advancecustomizationyesradio = new Radio();
  private Radio advancecustomizationnoradio = new Radio();
  
  private TextArea itemspecialnotes = new TextArea();
  private ImageHelper productpreviewimage = new ImageHelper();
  
  private Observable observable = new com.extjs.gxt.ui.client.event.BaseObservable();
  private SetField setfield;
  
  public ItemPortletField(OrderData orderdata, OrderDataSet orderdataset, OrderDataItem item, SetField setfield)
  {
    this.orderdata = orderdata;
    this.orderdataset = orderdataset;
    this.item = item;
    this.setfield = setfield;
    
    setLabels();
    setBindings();
    setFields();
    addListeners();
    
    this.myuploadform.setMethod(com.extjs.gxt.ui.client.widget.form.FormPanel.Method.POST);
    this.myuploadform.setEncoding(com.extjs.gxt.ui.client.widget.form.FormPanel.Encoding.MULTIPART);
    this.myuploadform.setAction(NewWorkFlow.baseurl + "uploader");
    this.myuploadform.setHeaderVisible(false);
    this.myuploadform.setBorders(false);
    this.myuploadform.setBodyBorder(false);
    this.myuploadform.setFrame(false);
    this.myuploadform.setPadding(0);
    FormHorizontalPanel2 myuploadside = new FormHorizontalPanel2();
    myuploadside.add(this.isamplefield);
    myuploadside.add(this.isampleimagefile, new com.extjs.gxt.ui.client.widget.layout.FormData(230, -1));
    myuploadside.addButton(this.uploadsubmit);
    myuploadside.add(this.uploadtype);
    myuploadside.add(this.isampleordertype);
    myuploadside.add(this.isamplehiddenkey);
    this.myuploadform.add(myuploadside);
    
    if ((NewWorkFlow.get().getStoredUser().getAccessLevel() == 1) && (!NewWorkFlow.get().getStoredUser().getUsername().equals(orderdata.getEmployeeId())) && (!orderdata.getEmployeeId().equals(""))) {
      this.isampleimagefile.setEnabled(false);
      this.uploadsubmit.setEnabled(false);
    }
    
    this.proofstodo.setValidator(this.proostodovalidator);
    
    this.standardlabelradio.setBoxLabel("OTTO Standard Label");
    this.privatelabelradio.setBoxLabel("Private Label");
    
    this.privateorstandardlabelgroup.setFieldLabel("What Label Is Needed?");
    this.privateorstandardlabelgroup.add(this.standardlabelradio);
    this.privateorstandardlabelgroup.add(this.privatelabelradio);
    
    this.standardpackagingradio.setBoxLabel("OTTO Standard Packaging");
    this.privatepackagingradio.setBoxLabel("Custom Packaging - See Attached Instructions");
    
    this.privateorstandardpackaginggroup.setFieldLabel("What Packaging Is Needed?");
    this.privateorstandardpackaginggroup.add(this.standardpackagingradio);
    this.privateorstandardpackaginggroup.add(this.privatepackagingradio);
    
    this.standardshippingmarkradio.setBoxLabel("OTTO Standard Shipping Mark");
    this.privateshippingmarkradio.setBoxLabel("Customer Shipping Mark - See Attached Instructions");
    
    this.privateorstandardshippingmarkgroup.setFieldLabel("What Shipping Mark Is Needed?");
    this.privateorstandardshippingmarkgroup.add(this.standardshippingmarkradio);
    this.privateorstandardshippingmarkgroup.add(this.privateshippingmarkradio);
    
    this.advancecustomizationyesradio.setBoxLabel("Yes");
    this.advancecustomizationnoradio.setBoxLabel("No");
    this.advancecustomizationgroup.setFieldLabel("Use Detailed Customization?");
    this.advancecustomizationgroup.add(this.advancecustomizationyesradio);
    this.advancecustomizationgroup.add(this.advancecustomizationnoradio);
  }
  

  private Validator proostodovalidator = new Validator()
  {
    public String validate(Field<?> field, String value)
    {
      if ((!ItemPortletField.this.item.getProductSampleEmail()) && (!ItemPortletField.this.item.getProductSampleShip()) && (ItemPortletField.this.item.getProductSampleToDo() != null) && (ItemPortletField.this.item.getProductSampleToDo().intValue() > 0)) {
        return "Please Select Proofs Types";
      }
      return null;
    }
  };
  

  private void addListeners()
  {
    this.uploadsubmit.addSelectionListener(this.buttonselectionlistener);
    this.myuploadform.addListener(Events.Submit, this.submitlistener);
    
    this.emailproof.addListener(Events.Change, this.changelistener);
    this.shipproof.addListener(Events.Change, this.changelistener);
    this.proofstodo.addListener(Events.Change, this.changelistener);
    
    this.productcategory.addListener(Events.Change, this.changelistener);
    this.productcategory.addListener(Events.SelectionChange, this.selectionchangelistener);
    this.productstylenumber.addListener(Events.Change, this.changelistener);
    this.productstylenumber.addListener(Events.SelectionChange, this.selectionchangelistener);
    this.vendornumber.addListener(Events.Change, this.changelistener);
    this.productcolor.addListener(Events.Change, this.changelistener);
    this.productsize.addListener(Events.Change, this.changelistener);
    this.frontpanelcolor.addListener(Events.Change, this.changelistener);
    this.sidebackpanelcolor.addListener(Events.Change, this.changelistener);
    this.backpanelcolor.addListener(Events.Change, this.changelistener);
    this.sidepanelcolor.addListener(Events.Change, this.changelistener);
    this.visorstylenumber.addListener(Events.Change, this.changelistener);
    this.primaryvisorcolor.addListener(Events.Change, this.changelistener);
    this.visortrimedgecolor.addListener(Events.Change, this.changelistener);
    this.visorsandwichcolor.addListener(Events.Change, this.changelistener);
    this.distressedvisorinsidecolor.addListener(Events.Change, this.changelistener);
    this.undervisorcolor.addListener(Events.Change, this.changelistener);
    this.closurestylenumber.addListener(Events.Change, this.changelistener);
    this.closurestrapcolor.addListener(Events.Change, this.changelistener);
    this.eyeletstylenumber.addListener(Events.Change, this.changelistener);
    this.fronteyeletcolor.addListener(Events.Change, this.changelistener);
    this.sidebackeyeletcolor.addListener(Events.Change, this.changelistener);
    this.backeyeletcolor.addListener(Events.Change, this.changelistener);
    this.sideeyeletcolor.addListener(Events.Change, this.changelistener);
    this.buttoncolor.addListener(Events.Change, this.changelistener);
    this.innertapingcolor.addListener(Events.Change, this.changelistener);
    this.sweatbandstylenumber.addListener(Events.Change, this.changelistener);
    this.sweatbandcolor.addListener(Events.Change, this.changelistener);
    this.sweatbandstripecolor.addListener(Events.Change, this.changelistener);
    this.privateorstandardlabelgroup.addListener(Events.Change, this.changelistener);
    this.privateorstandardpackaginggroup.addListener(Events.Change, this.changelistener);
    this.privateorstandardshippingmarkgroup.addListener(Events.Change, this.changelistener);
    this.advancecustomizationgroup.addListener(Events.Change, this.changelistener);
    this.profile.addListener(Events.Change, this.changelistener);
    this.frontpanelfabric.addListener(Events.Change, this.changelistener);
    this.sidebackpanelfabric.addListener(Events.Change, this.changelistener);
    this.backpanelfabric.addListener(Events.Change, this.changelistener);
    this.sidepanelfabric.addListener(Events.Change, this.changelistener);
    
    this.numberofpanels.addListener(Events.Change, this.changelistener);
    this.crownconstruction.addListener(Events.Change, this.changelistener);
    this.visorrowstitching.addListener(Events.Change, this.changelistener);
    this.visorstitchcolor.addListener(Events.Change, this.changelistener);
    this.panelstitchcolor.addListener(Events.Change, this.changelistener);
    
    this.personalization.addListener(Events.Change, this.changelistener);
    this.isamplefield.addListener(Events.TriggerClick, this.triggerclicklistener);
  }
  

  private Listener<BaseEvent> triggerclicklistener = new Listener()
  {
    public void handleEvent(BaseEvent be)
    {
      if (be.getSource().equals(ItemPortletField.this.isamplefield)) {
        ItemPortletField.this.item.setPreviewFilename("");
        ItemPortletField.this.isamplefield.setValue("");
        ItemPortletField.this.updateProductPreview();
      }
    }
  };
  

  private Listener<BaseEvent> selectionchangelistener = new Listener()
  {
    public void handleEvent(BaseEvent be)
    {
      if (be.getSource().equals(ItemPortletField.this.productstylenumber)) {
        ItemPortletField.this.changeproductstyle();
      } else if (be.getSource().equals(ItemPortletField.this.productcategory)) {
        ItemPortletField.this.filterProducts();
      }
    }
  };
  
















  private void filterProducts()
  {
    if (this.productstylenumber.getStore().getLoadConfig() != null) {
      this.productstylenumber.getStore().getLoadConfig().set("category", this.productcategory.getValue() == null ? "" : this.productcategory.getValue().getValue());
    }
  }
  
  private void changeproductstyle()
  {
    String oldstylenumber = "";
    if ((this.item.getStyleNumber() != null) && (!this.item.getStyleNumber().equals(""))) {
      oldstylenumber = this.item.getStyleNumber();
    }
    this.item.setStyleNumber(this.productstylenumber.getValue() == null ? oldstylenumber : this.productstylenumber.getValue().getValue());
    OtherComboBoxModelData styledata = this.productstylenumber.getValue();
    
    if (styledata == null) {
      setStyleStoreFields(false);
    } else if ((this.orderdata.getOrderType() == OrderData.OrderType.DOMESTIC) && (NewWorkFlow.get().getDataStores().getDomesticStyleStore().get(this.productstylenumber.getValue().getValue()) == null)) {
      NewWorkFlow.get().getWorkFlowRPC().getStyleData(styledata.getValue(), this.orderdata.getOrderType(), this.styleloadcallback);
    } else if ((this.orderdata.getOrderType() == OrderData.OrderType.OVERSEAS) && (NewWorkFlow.get().getDataStores().getOverseasStyleStore().get(this.productstylenumber.getValue().getValue()) == null)) {
      NewWorkFlow.get().getWorkFlowRPC().getStyleData(styledata.getValue(), this.orderdata.getOrderType(), this.styleloadcallback);
    } else {
      setStyleStoreFields(false);
    }
  }
  
  private AsyncCallback<StyleInformationData> styleloadcallback = new AsyncCallback()
  {
    public void onFailure(Throwable caught)
    {
      NewWorkFlow.get().getWorkFlowRPC().getStyleData(ItemPortletField.this.productstylenumber.getValue().getValue(), ItemPortletField.this.orderdata.getOrderType(), ItemPortletField.this.styleloadcallback);
    }
    
    public void onSuccess(StyleInformationData result)
    {
      if (result.getStyleNumber().equals(ItemPortletField.this.item.getStyleNumber())) {
        NewWorkFlow.get().setStyleStore(result);
        ItemPortletField.this.setStyleStoreFields(false);
      } else {
        NewWorkFlow.get().getWorkFlowRPC().getStyleData(ItemPortletField.this.productstylenumber.getValue().getValue(), ItemPortletField.this.orderdata.getOrderType(), ItemPortletField.this.styleloadcallback);
      }
    }
  };
  
  private void setStyleStoreSizeColorFields(boolean firstload)
  {
    if (hasFrontFabricColors()) {
      if ((getCurrentStyle().getAllFabric(this.item.getFrontPanelFabric()).getUseSizeColor()) && (!this.item.getSize().equals(""))) {
        this.productcolor.changeStore((ListStore)getCurrentStyle().getAllFabric(this.item.getFrontPanelFabric()).getSizeColorStore().get(this.item.getSize()), this.item.getColorCode());
      } else {
        this.productcolor.changeStore(getCurrentStyle().getAllFabric(this.item.getFrontPanelFabric()).getColorCodeStore(), this.item.getColorCode());
      }
    } else if ((getCurrentStyle().getUseSizeColor()) && (!this.item.getSize().equals(""))) {
      this.productcolor.changeStore((ListStore)getCurrentStyle().getSizeColorStore().get(this.item.getSize()), this.item.getColorCode());
    } else {
      this.productcolor.changeStore(getCurrentStyle().getColorCodeStore(), this.item.getColorCode());
    }
    




    this.productcolor.setVisible(((this.productcolor.getStore() != null) && (this.productcolor.getStore().getAllModels().size() != 0)) || (getCurrentStyle().getStyleType() == StyleInformationData.StyleType.NONOTTO) || (getCurrentStyle().getStyleType() == StyleInformationData.StyleType.OVERSEAS_NONOTTO_SHIRTS) || ((this.item.getStyleNumber().equals("888-ConceptCap")) && (getCurrentStyle().getStyleType() == StyleInformationData.StyleType.DOMESTIC)));
    





    this.item.setColorCode((this.productcolor.getValue() == null) || ((this.productcolor.isOther()) && (!firstload) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_CUSTOMSTYLE)) ? "" : this.productcolor.getValue().getValue());
    if (this.item.getColorCode().equals("")) {
      this.productcolor.setValue(null);
    }
  }
  
  private void updateProductPreview()
  {
    if (!this.item.getPreviewFilename().equals("")) {
      if (this.orderdata.getOrderType() == OrderData.OrderType.DOMESTIC) {
        this.productpreviewimage.setUrl(NewWorkFlow.filepath + "DomesticData/" + this.orderdata.getHiddenKey() + "/cappreview/" + URL.encodeQueryString(this.item.getPreviewFilename()));
      } else {
        this.productpreviewimage.setUrl(NewWorkFlow.filepath + "OverseasData/" + this.orderdata.getHiddenKey() + "/cappreview/" + URL.encodeQueryString(this.item.getPreviewFilename()));
      }
    } else {
      String productpreviewtags = "?";
      productpreviewtags = productpreviewtags + "stylenumber=" + URL.encodeQueryString(this.item.getStyleNumber());
      productpreviewtags = productpreviewtags + "&colorcode=" + URL.encodeQueryString(this.item.getColorCode());
      productpreviewtags = productpreviewtags + "&frontpanelcolor=" + URL.encodeQueryString(this.item.getFrontPanelColor());
      productpreviewtags = productpreviewtags + "&sidebackpanelcolor=" + URL.encodeQueryString(this.item.getSideBackPanelColor());
      productpreviewtags = productpreviewtags + "&backpanelcolor=" + URL.encodeQueryString(this.item.getBackPanelColor());
      productpreviewtags = productpreviewtags + "&sidepanelcolor=" + URL.encodeQueryString(this.item.getSidePanelColor());
      productpreviewtags = productpreviewtags + "&buttoncolor=" + URL.encodeQueryString(this.item.getButtonColor());
      productpreviewtags = productpreviewtags + "&innertapingcolor=" + URL.encodeQueryString(this.item.getInnerTapingColor());
      productpreviewtags = productpreviewtags + "&visorstylenumber=" + URL.encodeQueryString(this.item.getVisorStyleNumber());
      productpreviewtags = productpreviewtags + "&primaryvisorcolor=" + URL.encodeQueryString(this.item.getPrimaryVisorColor());
      productpreviewtags = productpreviewtags + "&visortrimcolor=" + URL.encodeQueryString(this.item.getVisorTrimColor());
      productpreviewtags = productpreviewtags + "&visorsandwichcolor=" + URL.encodeQueryString(this.item.getVisorSandwichColor());
      productpreviewtags = productpreviewtags + "&undervisorcolor=" + URL.encodeQueryString(this.item.getUndervisorColor());
      productpreviewtags = productpreviewtags + "&distressedvisorinsidecolor=" + URL.encodeQueryString(this.item.getDistressedVisorInsideColor());
      productpreviewtags = productpreviewtags + "&closurestylenumber=" + URL.encodeQueryString(this.item.getClosureStyleNumber());
      productpreviewtags = productpreviewtags + "&closurestrapcolor=" + URL.encodeQueryString(this.item.getClosureStrapColor());
      productpreviewtags = productpreviewtags + "&eyeletstylenumber=" + URL.encodeQueryString(this.item.getEyeletStyleNumber());
      productpreviewtags = productpreviewtags + "&fronteyeletcolor=" + URL.encodeQueryString(this.item.getFrontEyeletColor());
      productpreviewtags = productpreviewtags + "&sidebackeyeletcolor=" + URL.encodeQueryString(this.item.getSideBackEyeletColor());
      productpreviewtags = productpreviewtags + "&backeyeletcolor=" + URL.encodeQueryString(this.item.getBackEyeletColor());
      productpreviewtags = productpreviewtags + "&sideeyeletcolor=" + URL.encodeQueryString(this.item.getSideEyeletColor());
      productpreviewtags = productpreviewtags + "&sweatbandstylenumber=" + URL.encodeQueryString(this.item.getSweatbandStyleNumber());
      productpreviewtags = productpreviewtags + "&sweatbandcolor=" + URL.encodeQueryString(this.item.getSweatbandColor());
      productpreviewtags = productpreviewtags + "&sweatbandstripecolor=" + URL.encodeQueryString(this.item.getSweatbandStripeColor());
      
      productpreviewtags = productpreviewtags + "&panelstitchcolor=" + URL.encodeQueryString(this.item.getPanelStitchColor());
      productpreviewtags = productpreviewtags + "&visorrowstitching=" + URL.encodeQueryString(this.item.getVisorRowStitching());
      productpreviewtags = productpreviewtags + "&visorstitchcolor=" + URL.encodeQueryString(this.item.getVisorStitchColor());
      productpreviewtags = productpreviewtags + "&profile=" + URL.encodeQueryString(this.item.getProfile());
      productpreviewtags = productpreviewtags + "&frontpanelfabric=" + URL.encodeQueryString(this.item.getFrontPanelFabric());
      productpreviewtags = productpreviewtags + "&sidebackpanelfabric=" + URL.encodeQueryString(this.item.getSideBackPanelFabric());
      productpreviewtags = productpreviewtags + "&backpanelfabric=" + URL.encodeQueryString(this.item.getBackPanelFabric());
      productpreviewtags = productpreviewtags + "&sidepanelfabric=" + URL.encodeQueryString(this.item.getSidePanelFabric());
      productpreviewtags = productpreviewtags + "&numberofpanels=" + URL.encodeQueryString(this.item.getNumberOfPanels());
      productpreviewtags = productpreviewtags + "&crownconstruction=" + URL.encodeQueryString(this.item.getCrownConstruction());
      
      if (getCurrentStyle().getStyleType() != null) {
        productpreviewtags = productpreviewtags + "&styletype=" + URL.encodeQueryString(getCurrentStyle().getStyleType().name());
      } else {
        productpreviewtags = productpreviewtags + "&styletype=";
      }
      
      if ((this.item.getStyleNumber() != null) && (!this.item.getStyleNumber().equals("")))
      {

        productpreviewtags = productpreviewtags + "&category=" + URL.encodeQueryString(getCurrentStyle().getCategory());
      } else {
        productpreviewtags = productpreviewtags + "&category=";
      }
      
      if (this.orderdata.getOrderType() == OrderData.OrderType.DOMESTIC) {
        productpreviewtags = productpreviewtags + "&ordertype=Domestic";
      } else {
        productpreviewtags = productpreviewtags + "&ordertype=Overseas";
      }
      
      this.productpreviewimage.setUrl(NewWorkFlow.baseurl + "productpreview/" + productpreviewtags);
    }
  }
  
  private boolean hasFrontFabricColors() {
    if ((this.item.getFrontPanelFabric() != null) && (!this.item.getFrontPanelFabric().equals("")) && 
      (getCurrentStyle().getAllFabric(this.item.getFrontPanelFabric()) != null)) {
      return true;
    }
    
    return false;
  }
  
  private boolean hasSideBackFabricColors() {
    if ((this.item.getSideBackPanelFabric() != null) && (!this.item.getSideBackPanelFabric().equals("")) && 
      (getCurrentStyle().getAllFabric(this.item.getSideBackPanelFabric()) != null)) {
      return true;
    }
    
    return false;
  }
  
  private boolean hasBackFabricColors() {
    if ((this.item.getBackPanelFabric() != null) && (!this.item.getBackPanelFabric().equals("")) && 
      (getCurrentStyle().getAllFabric(this.item.getBackPanelFabric()) != null)) {
      return true;
    }
    
    return false;
  }
  
  private boolean hasSideFabricColors() {
    if ((this.item.getSidePanelFabric() != null) && (!this.item.getSidePanelFabric().equals("")) && 
      (getCurrentStyle().getAllFabric(this.item.getSidePanelFabric()) != null)) {
      return true;
    }
    
    return false;
  }
  
  private void setStyleStoreFields(boolean firstload)
  {
    updateProductPreview();
    


    this.fobdozenprice.setVisible(getCurrentStyle().getShowFOBDozen());
    
    this.fobdozenprice.setValue(getCurrentStyle().getShowFOBDozen() ? this.item.getFOBPrice() : null);
    
    this.fobdozenmarkupprice.setVisible(getCurrentStyle().getShowFOBDozen());
    this.fobdozenmarkupprice.setValue(getCurrentStyle().getShowFOBDozen() ? this.item.getFOBMarkupPrice() : null);
    
    if ((getCurrentStyle().getStyleType() == StyleInformationData.StyleType.NONOTTO) || (getCurrentStyle().getStyleType() == StyleInformationData.StyleType.OVERSEAS_NONOTTO) || (getCurrentStyle().getStyleType() == StyleInformationData.StyleType.OVERSEAS_NONOTTO_SHIRTS) || (getCurrentStyle().getStyleType() == StyleInformationData.StyleType.OVERSEAS_CUSTOMSTYLE))
    {
      this.customstylename.setVisible(true);
      
      this.customstylename.setValue(this.item.getCustomStyleName());
    }
    else {
      this.customstylename.setVisible(false);
      
      this.customstylename.setValue("");
      this.item.setCustomStyleName("");
    }
    
    this.visorstylenumber.setForceSelection(getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_CUSTOMSTYLE);
    this.closurestylenumber.setForceSelection(getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_CUSTOMSTYLE);
    this.eyeletstylenumber.setForceSelection(getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_CUSTOMSTYLE);
    this.sweatbandstylenumber.setForceSelection(getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_CUSTOMSTYLE);
    
    this.productsize.setForceSelection((getCurrentStyle().getStyleType() != StyleInformationData.StyleType.NONOTTO) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_CUSTOMSTYLE) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO_SHIRTS));
    this.productsize.changeStore(getCurrentStyle().getSizeStore(), this.item.getSize());
    String defaultsize = "";
    if ((getCurrentStyle().getOriginalSize() != null) && (!getCurrentStyle().getOriginalSize().equals(""))) {
      defaultsize = getCurrentStyle().getOriginalSize();
    }
    
    String currentsizestylenumber = (this.productsize.getValue() == null) || ((this.productsize.isOther()) && (!firstload) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_CUSTOMSTYLE) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO_SHIRTS)) ? "" : this.productsize.getValue().getValue();
    if ((currentsizestylenumber.equals("")) && (!defaultsize.equals(""))) {
      currentsizestylenumber = defaultsize;
    }
    if (currentsizestylenumber.equals("")) {
      this.item.setSize(currentsizestylenumber);
      this.productsize.setValue(null);
    } else {
      this.item.setSize(currentsizestylenumber);
      this.productsize.setValue(currentsizestylenumber);
    }
    



    setStyleStoreSizeColorFields(firstload);
    
    this.vendornumber.changeStore(getCurrentStyle().getAllVendorsStore(), this.item.getVendorNumber());
    this.item.setVendorNumber((this.vendornumber.getValue() == null) || ((this.vendornumber.isOther()) && (!firstload) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_CUSTOMSTYLE)) ? "" : this.vendornumber.getValue().getValue());
    if (this.item.getVendorNumber().equals("")) {
      if (getCurrentStyle().getAllVendorsStore().getAllModels().size() == 0) {
        this.vendornumber.setValue(null);
      } else {
        this.vendornumber.setValue("Default");
      }
    }
    
    this.profile.changeStore(getCurrentStyle().getAllProfileStore(), this.item.getProfile());
    this.item.setProfile((this.profile.getValue() == null) || ((this.profile.isOther()) && (!firstload) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_CUSTOMSTYLE)) ? "" : this.profile.getValue().getValue());
    if (this.item.getProfile().equals("")) {
      this.profile.setValue(null);
    }
    
    this.numberofpanels.changeStore(getCurrentStyle().getNumberOfPanelsStore(), this.item.getNumberOfPanels());
    this.item.setNumberOfPanels((this.numberofpanels.getValue() == null) || ((this.numberofpanels.isOther()) && (!firstload) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_CUSTOMSTYLE)) ? "" : this.numberofpanels.getValue().getValue());
    if (this.item.getNumberOfPanels().equals("")) {
      this.numberofpanels.setValue(null);
    }
    
    this.crownconstruction.changeStore(getCurrentStyle().getCrownConstructionStore(), this.item.getCrownConstruction());
    this.item.setCrownConstruction((this.crownconstruction.getValue() == null) || ((this.crownconstruction.isOther()) && (!firstload) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_CUSTOMSTYLE)) ? "" : this.crownconstruction.getValue().getValue());
    if (this.item.getCrownConstruction().equals("")) {
      this.crownconstruction.setValue(null);
    }
    
    this.frontpanelfabric.changeStore(getCurrentStyle().getFrontFabricStore(), this.item.getFrontPanelFabric());
    this.item.setFrontPanelFabric((this.frontpanelfabric.getValue() == null) || ((this.frontpanelfabric.isOther()) && (!firstload) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_CUSTOMSTYLE)) ? "" : this.frontpanelfabric.getValue().getValue());
    if (this.item.getFrontPanelFabric().equals("")) {
      this.frontpanelfabric.setValue(null);
    }
    
    this.sidebackpanelfabric.changeStore(getCurrentStyle().getSideBackFabricStore(), this.item.getSideBackPanelFabric());
    this.item.setSideBackPanelFabric((this.sidebackpanelfabric.getValue() == null) || ((this.sidebackpanelfabric.isOther()) && (!firstload) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_CUSTOMSTYLE)) ? "" : this.sidebackpanelfabric.getValue().getValue());
    if (this.item.getSideBackPanelFabric().equals("")) {
      this.sidebackpanelfabric.setValue(null);
    }
    
    this.backpanelfabric.changeStore(getCurrentStyle().getBackFabricStore(), this.item.getBackPanelFabric());
    this.item.setBackPanelFabric((this.backpanelfabric.getValue() == null) || ((this.backpanelfabric.isOther()) && (!firstload) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_CUSTOMSTYLE)) ? "" : this.backpanelfabric.getValue().getValue());
    if (this.item.getBackPanelFabric().equals("")) {
      this.backpanelfabric.setValue(null);
    }
    
    this.sidepanelfabric.changeStore(getCurrentStyle().getSideFabricStore(), this.item.getSidePanelFabric());
    this.item.setSidePanelFabric((this.sidepanelfabric.getValue() == null) || ((this.sidepanelfabric.isOther()) && (!firstload) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_CUSTOMSTYLE)) ? "" : this.sidepanelfabric.getValue().getValue());
    if (this.item.getSidePanelFabric().equals("")) {
      this.sidepanelfabric.setValue(null);
    }
    
    if ((this.sidebackpanelfabric.getStore().getAllModels().size() == 0) && (this.backpanelfabric.getStore().getAllModels().size() == 0) && (this.sidepanelfabric.getStore().getAllModels().size() == 0)) {
      this.frontpanelfabric.setFieldLabel("Panel Fabric");
    } else {
      this.frontpanelfabric.setFieldLabel("Front Panel Fabric");
    }
    
    NewStyleStore sidestore = null;
    NewStyleStore backstore = null;
    NewStyleStore frontstore = null;
    if (hasBackFabricColors()) {
      backstore = getCurrentStyle().getAllFabric(this.item.getBackPanelFabric());
    } else if (hasSideBackFabricColors()) {
      backstore = getCurrentStyle().getAllFabric(this.item.getSideBackPanelFabric());
    } else {
      backstore = getCurrentStyle().getAllFabric(this.item.getFrontPanelFabric());
    }
    if (hasSideFabricColors()) {
      sidestore = getCurrentStyle().getAllFabric(this.item.getSidePanelFabric());
    } else if (hasSideBackFabricColors()) {
      sidestore = getCurrentStyle().getAllFabric(this.item.getSideBackPanelFabric());
    } else {
      sidestore = getCurrentStyle().getAllFabric(this.item.getFrontPanelFabric());
    }
    if (hasFrontFabricColors()) {
      frontstore = getCurrentStyle().getAllFabric(this.item.getFrontPanelFabric());
    } else if (hasSideBackFabricColors()) {
      frontstore = getCurrentStyle().getAllFabric(this.item.getSideBackPanelFabric());
    } else {
      frontstore = getCurrentStyle().getAllFabric(this.item.getBackPanelFabric());
    }
    
    if (frontstore != null) {
      this.frontpanelcolor.changeStore(frontstore.getFrontPanelColorStore(), this.item.getFrontPanelColor());
      this.sidebackpanelcolor.changeStore(backstore.getSideBackPanelColorStore(), this.item.getSideBackPanelColor());
      this.backpanelcolor.changeStore(backstore.getBackPanelColorStore(), this.item.getBackPanelColor());
      this.sidepanelcolor.changeStore(sidestore.getSidePanelColorStore(), this.item.getSidePanelColor());
      this.primaryvisorcolor.changeStore(frontstore.getPrimaryVisorColorStore(), this.item.getPrimaryVisorColor());
      this.visortrimedgecolor.changeStore(frontstore.getVisorTrimEdgeColorStore(), this.item.getVisorTrimColor());
      this.visorsandwichcolor.changeStore(frontstore.getVisorSandwichColorStore(), this.item.getVisorSandwichColor());
      this.distressedvisorinsidecolor.changeStore(frontstore.getDistressedVisorInsideColorStore(), this.item.getDistressedVisorInsideColor());
      this.undervisorcolor.changeStore(frontstore.getUndervisorColorStore(), this.item.getUndervisorColor());
      this.fronteyeletcolor.changeStore(frontstore.getFrontEyeletColorStore(), this.item.getFrontEyeletColor());
      this.sidebackeyeletcolor.changeStore(backstore.getSideBackEyeletColorStore(), this.item.getSideBackEyeletColor());
      this.backeyeletcolor.changeStore(backstore.getBackEyeletColorStore(), this.item.getBackEyeletColor());
      this.sideeyeletcolor.changeStore(sidestore.getSideEyeletColorStore(), this.item.getSideEyeletColor());
      this.buttoncolor.changeStore(frontstore.getButtonColorStore(), this.item.getButtonColor());
      this.innertapingcolor.changeStore(frontstore.getInnerTapingColorStore(), this.item.getInnerTapingColor());
      this.sweatbandstripecolor.changeStore(frontstore.getStripeColorStore(), this.item.getSweatbandStripeColor());
      
      this.visorstitchcolor.changeStore(frontstore.getVisorStitchColorStore(), this.item.getVisorStitchColor());
      this.panelstitchcolor.changeStore(frontstore.getPanelStitchColorStore(), this.item.getPanelStitchColor());
      
      if ((this.item.getClosureStyleNumber() != null) && ((this.item.getClosureStyleNumber().equals("C2")) || (this.item.getClosureStyleNumber().equals("C31")))) {
        this.closurestrapcolor.changeStore(frontstore.getC2C31ClosureStrapColorStore(), this.item.getClosureStrapColor());
      } else {
        this.closurestrapcolor.changeStore(frontstore.getClosureStrapColorStore(), this.item.getClosureStrapColor());
      }
      
      if ((this.item.getSweatbandStyleNumber() != null) && (this.item.getSweatbandStyleNumber().equals("S5"))) {
        this.sweatbandcolor.changeStore(frontstore.getS5SweatbandColorStore(), this.item.getSweatbandColor());
      } else {
        this.sweatbandcolor.changeStore(frontstore.getSweatbandColorStore(), this.item.getSweatbandColor());
      }
    }
    else {
      this.frontpanelcolor.changeStore(getCurrentStyle().getFrontPanelColorStore(), this.item.getFrontPanelColor());
      this.sidebackpanelcolor.changeStore(getCurrentStyle().getSideBackPanelColorStore(), this.item.getSideBackPanelColor());
      this.backpanelcolor.changeStore(getCurrentStyle().getBackPanelColorStore(), this.item.getBackPanelColor());
      this.sidepanelcolor.changeStore(getCurrentStyle().getSidePanelColorStore(), this.item.getSidePanelColor());
      this.primaryvisorcolor.changeStore(getCurrentStyle().getPrimaryVisorColorStore(), this.item.getPrimaryVisorColor());
      this.visortrimedgecolor.changeStore(getCurrentStyle().getVisorTrimEdgeColorStore(), this.item.getVisorTrimColor());
      this.visorsandwichcolor.changeStore(getCurrentStyle().getVisorSandwichColorStore(), this.item.getVisorSandwichColor());
      this.distressedvisorinsidecolor.changeStore(getCurrentStyle().getDistressedVisorInsideColorStore(), this.item.getDistressedVisorInsideColor());
      this.undervisorcolor.changeStore(getCurrentStyle().getUndervisorColorStore(), this.item.getUndervisorColor());
      this.fronteyeletcolor.changeStore(getCurrentStyle().getFrontEyeletColorStore(), this.item.getFrontEyeletColor());
      this.sidebackeyeletcolor.changeStore(getCurrentStyle().getSideBackEyeletColorStore(), this.item.getSideBackEyeletColor());
      this.backeyeletcolor.changeStore(getCurrentStyle().getBackEyeletColorStore(), this.item.getBackEyeletColor());
      this.sideeyeletcolor.changeStore(getCurrentStyle().getSideEyeletColorStore(), this.item.getSideEyeletColor());
      this.buttoncolor.changeStore(getCurrentStyle().getButtonColorStore(), this.item.getButtonColor());
      this.innertapingcolor.changeStore(getCurrentStyle().getInnerTapingColorStore(), this.item.getInnerTapingColor());
      this.sweatbandstripecolor.changeStore(getCurrentStyle().getStripeColorStore(), this.item.getSweatbandStripeColor());
      
      this.visorstitchcolor.changeStore(getCurrentStyle().getVisorStitchColorStore(), this.item.getVisorStitchColor());
      this.panelstitchcolor.changeStore(getCurrentStyle().getPanelStitchColorStore(), this.item.getPanelStitchColor());
      
      if ((this.item.getClosureStyleNumber() != null) && ((this.item.getClosureStyleNumber().equals("C2")) || (this.item.getClosureStyleNumber().equals("C31")))) {
        this.closurestrapcolor.changeStore(getCurrentStyle().getC2C31ClosureStrapColorStore(), this.item.getClosureStrapColor());
      } else {
        this.closurestrapcolor.changeStore(getCurrentStyle().getClosureStrapColorStore(), this.item.getClosureStrapColor());
      }
      
      if ((this.item.getSweatbandStyleNumber() != null) && (this.item.getSweatbandStyleNumber().equals("S5"))) {
        this.sweatbandcolor.changeStore(getCurrentStyle().getS5SweatbandColorStore(), this.item.getSweatbandColor());
      } else {
        this.sweatbandcolor.changeStore(getCurrentStyle().getSweatbandColorStore(), this.item.getSweatbandColor());
      }
    }
    

    this.item.setVisorStitchColor((this.visorstitchcolor.getValue() == null) || ((this.visorstitchcolor.isOther()) && (!firstload) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_CUSTOMSTYLE) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO_SHIRTS)) ? "" : this.visorstitchcolor.getValue().getValue());
    if (this.item.getVisorStitchColor().equals("")) {
      this.visorstitchcolor.setValue(null);
    }
    
    this.item.setPanelStitchColor((this.panelstitchcolor.getValue() == null) || ((this.panelstitchcolor.isOther()) && (!firstload) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_CUSTOMSTYLE) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO_SHIRTS)) ? "" : this.panelstitchcolor.getValue().getValue());
    if (this.item.getPanelStitchColor().equals("")) {
      this.panelstitchcolor.setValue(null);
    }
    



    this.item.setFrontPanelColor((this.frontpanelcolor.getValue() == null) || ((this.frontpanelcolor.isOther()) && (!firstload) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_CUSTOMSTYLE) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO_SHIRTS)) ? "" : this.frontpanelcolor.getValue().getValue());
    if (this.item.getFrontPanelColor().equals("")) {
      this.frontpanelcolor.setValue(null);
    }
    
    this.item.setSideBackPanelColor((this.sidebackpanelcolor.getValue() == null) || ((this.sidebackpanelcolor.isOther()) && (!firstload) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_CUSTOMSTYLE) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO_SHIRTS)) ? "" : this.sidebackpanelcolor.getValue().getValue());
    if (this.item.getSideBackPanelColor().equals("")) {
      this.sidebackpanelcolor.setValue(null);
    }
    this.item.setBackPanelColor((this.backpanelcolor.getValue() == null) || ((this.backpanelcolor.isOther()) && (!firstload) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_CUSTOMSTYLE) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO_SHIRTS)) ? "" : this.backpanelcolor.getValue().getValue());
    if (this.item.getBackPanelColor().equals("")) {
      this.backpanelcolor.setValue(null);
    }
    this.item.setSidePanelColor((this.sidepanelcolor.getValue() == null) || ((this.sidepanelcolor.isOther()) && (!firstload) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_CUSTOMSTYLE) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO_SHIRTS)) ? "" : this.sidepanelcolor.getValue().getValue());
    if (this.item.getSidePanelColor().equals("")) {
      this.sidepanelcolor.setValue(null);
    }
    
    this.visorstylenumber.changeStore(getCurrentStyle().getVisorStyleNumberStore(), this.item.getVisorStyleNumber());
    String currentvisorstylenumber = (this.visorstylenumber.getValue() == null) || ((this.visorstylenumber.isOther()) && (!firstload) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_CUSTOMSTYLE) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO_SHIRTS)) ? "" : this.visorstylenumber.getValue().getValue();
    String defaultvisor = "";
    if ((getCurrentStyle().getOriginalVisor() != null) && (!getCurrentStyle().getOriginalVisor().equals(""))) {
      defaultvisor = getCurrentStyle().getOriginalVisor();
    }
    if ((currentvisorstylenumber.equals("")) && (!defaultvisor.equals(""))) {
      currentvisorstylenumber = defaultvisor;
    }
    if (currentvisorstylenumber.equals("")) {
      this.item.setVisorStyleNumber(currentvisorstylenumber);
      this.visorstylenumber.setValue(null);
    } else {
      this.item.setVisorStyleNumber(currentvisorstylenumber);
      this.visorstylenumber.setValue(currentvisorstylenumber);
    }
    
    this.visorrowstitching.changeStore(getCurrentStyle().getVisorRowStitchingStore(), this.item.getVisorRowStitching());
    this.item.setVisorRowStitching((this.visorrowstitching.getValue() == null) || ((this.visorrowstitching.isOther()) && (!firstload) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_CUSTOMSTYLE) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO_SHIRTS)) ? "" : this.visorrowstitching.getValue().getValue());
    if (this.item.getVisorRowStitching().equals("")) {
      this.visorrowstitching.setValue(null);
    }
    


    this.item.setPrimaryVisorColor((this.primaryvisorcolor.getValue() == null) || ((this.primaryvisorcolor.isOther()) && (!firstload) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_CUSTOMSTYLE) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO_SHIRTS)) ? "" : this.primaryvisorcolor.getValue().getValue());
    if (this.item.getPrimaryVisorColor().equals("")) {
      this.primaryvisorcolor.setValue(null);
    }
    


    this.item.setVisorTrimColor((this.visortrimedgecolor.getValue() == null) || ((this.visortrimedgecolor.isOther()) && (!firstload) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_CUSTOMSTYLE) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO_SHIRTS)) ? "" : this.visortrimedgecolor.getValue().getValue());
    if (this.item.getVisorTrimColor().equals("")) {
      this.visortrimedgecolor.setValue(null);
    }
    


    this.item.setVisorSandwichColor((this.visorsandwichcolor.getValue() == null) || ((this.visorsandwichcolor.isOther()) && (!firstload) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_CUSTOMSTYLE) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO_SHIRTS)) ? "" : this.visorsandwichcolor.getValue().getValue());
    if (this.item.getVisorSandwichColor().equals("")) {
      this.visorsandwichcolor.setValue(null);
    }
    


    this.item.setDistressedVisorInsideColor((this.distressedvisorinsidecolor.getValue() == null) || ((this.distressedvisorinsidecolor.isOther()) && (!firstload) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_CUSTOMSTYLE) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO_SHIRTS)) ? "" : this.distressedvisorinsidecolor.getValue().getValue());
    if (this.item.getDistressedVisorInsideColor().equals("")) {
      this.distressedvisorinsidecolor.setValue(null);
    }
    


    this.item.setUndervisorColor((this.undervisorcolor.getValue() == null) || ((this.undervisorcolor.isOther()) && (!firstload) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_CUSTOMSTYLE) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO_SHIRTS)) ? "" : this.undervisorcolor.getValue().getValue());
    if (this.item.getUndervisorColor().equals("")) {
      this.undervisorcolor.setValue(null);
    }
    
    this.closurestylenumber.changeStore(getCurrentStyle().getClosureStyleNumberStore(), this.item.getClosureStyleNumber());
    String currentclosurestylenumber = (this.closurestylenumber.getValue() == null) || ((this.closurestylenumber.isOther()) && (!firstload) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_CUSTOMSTYLE) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO_SHIRTS)) ? "" : this.closurestylenumber.getValue().getValue();
    String defaultclosure = "";
    if ((getCurrentStyle().getOriginalClosure() != null) && (!getCurrentStyle().getOriginalClosure().equals(""))) {
      defaultclosure = getCurrentStyle().getOriginalClosure();
    }
    if ((currentclosurestylenumber.equals("")) && (!defaultclosure.equals(""))) {
      currentclosurestylenumber = defaultclosure;
    }
    if (currentclosurestylenumber.equals("")) {
      this.item.setClosureStyleNumber(currentclosurestylenumber);
      this.closurestylenumber.setValue(null);
    } else {
      this.item.setClosureStyleNumber(currentclosurestylenumber);
      this.closurestylenumber.setValue(currentclosurestylenumber);
    }
    
    this.item.setClosureStrapColor((this.closurestrapcolor.getValue() == null) || ((this.closurestrapcolor.isOther()) && (!firstload) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_CUSTOMSTYLE) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO_SHIRTS)) ? "" : this.closurestrapcolor.getValue().getValue());
    if (this.item.getClosureStrapColor().equals("")) {
      this.closurestrapcolor.setValue(null);
    }
    
    this.eyeletstylenumber.changeStore(getCurrentStyle().getEyeletStyleNumberStore(), this.item.getEyeletStyleNumber());
    String currenteyeletstylenumber = (this.eyeletstylenumber.getValue() == null) || ((this.eyeletstylenumber.isOther()) && (!firstload) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_CUSTOMSTYLE) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO_SHIRTS)) ? "" : this.eyeletstylenumber.getValue().getValue();
    String defaulteyelet = "";
    if ((getCurrentStyle().getOriginalEyelet() != null) && (!getCurrentStyle().getOriginalEyelet().equals(""))) {
      defaulteyelet = getCurrentStyle().getOriginalEyelet();
    }
    if ((currenteyeletstylenumber.equals("")) && (!defaulteyelet.equals(""))) {
      currenteyeletstylenumber = defaulteyelet;
    }
    if (currenteyeletstylenumber.equals("")) {
      this.item.setEyeletStyleNumber(currenteyeletstylenumber);
      this.eyeletstylenumber.setValue(null);
    } else {
      this.item.setEyeletStyleNumber(currenteyeletstylenumber);
      this.eyeletstylenumber.setValue(currenteyeletstylenumber);
    }
    
    this.item.setFrontEyeletColor((this.fronteyeletcolor.getValue() == null) || ((this.fronteyeletcolor.isOther()) && (!firstload) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_CUSTOMSTYLE) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO_SHIRTS)) ? "" : this.fronteyeletcolor.getValue().getValue());
    if (this.item.getFrontEyeletColor().equals("")) {
      this.fronteyeletcolor.setValue(null);
    }
    
    this.item.setSideBackEyeletColor((this.sidebackeyeletcolor.getValue() == null) || ((this.sidebackeyeletcolor.isOther()) && (!firstload) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_CUSTOMSTYLE) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO_SHIRTS)) ? "" : this.sidebackeyeletcolor.getValue().getValue());
    if (this.item.getSideBackEyeletColor().equals("")) {
      this.sidebackeyeletcolor.setValue(null);
    }
    
    this.item.setBackEyeletColor((this.backeyeletcolor.getValue() == null) || ((this.backeyeletcolor.isOther()) && (!firstload) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_CUSTOMSTYLE) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO_SHIRTS)) ? "" : this.backeyeletcolor.getValue().getValue());
    if (this.item.getBackEyeletColor().equals("")) {
      this.backeyeletcolor.setValue(null);
    }
    
    this.item.setSideEyeletColor((this.sideeyeletcolor.getValue() == null) || ((this.sideeyeletcolor.isOther()) && (!firstload) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_CUSTOMSTYLE) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO_SHIRTS)) ? "" : this.sideeyeletcolor.getValue().getValue());
    if (this.item.getSideEyeletColor().equals("")) {
      this.sideeyeletcolor.setValue(null);
    }
    
    this.item.setButtonColor((this.buttoncolor.getValue() == null) || ((this.buttoncolor.isOther()) && (!firstload) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_CUSTOMSTYLE) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO_SHIRTS)) ? "" : this.buttoncolor.getValue().getValue());
    if (this.item.getButtonColor().equals("")) {
      this.buttoncolor.setValue(null);
    }
    
    this.item.setInnerTapingColor((this.innertapingcolor.getValue() == null) || ((this.innertapingcolor.isOther()) && (!firstload) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_CUSTOMSTYLE) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO_SHIRTS)) ? "" : this.innertapingcolor.getValue().getValue());
    if (this.item.getInnerTapingColor().equals("")) {
      this.innertapingcolor.setValue(null);
    }
    
    this.sweatbandstylenumber.changeStore(getCurrentStyle().getSweatbandStyleNumberStore(), this.item.getSweatbandStyleNumber());
    String currentsweatbandstylenumber = (this.sweatbandstylenumber.getValue() == null) || ((this.sweatbandstylenumber.isOther()) && (!firstload) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_CUSTOMSTYLE) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO_SHIRTS)) ? "" : this.sweatbandstylenumber.getValue().getValue();
    String defaultsweatband = "";
    if ((getCurrentStyle().getOriginalSweatband() != null) && (!getCurrentStyle().getOriginalSweatband().equals(""))) {
      defaultsweatband = getCurrentStyle().getOriginalSweatband();
    }
    if ((currentsweatbandstylenumber.equals("")) && (!defaultsweatband.equals(""))) {
      currentsweatbandstylenumber = defaultsweatband;
    }
    if (currentsweatbandstylenumber.equals("")) {
      this.item.setSweatbandStyleNumber(currentsweatbandstylenumber);
      this.sweatbandstylenumber.setValue(null);
    } else {
      this.item.setSweatbandStyleNumber(currentsweatbandstylenumber);
      this.sweatbandstylenumber.setValue(currentsweatbandstylenumber);
    }
    
    this.item.setSweatbandColor((this.sweatbandcolor.getValue() == null) || ((this.sweatbandcolor.isOther()) && (!firstload) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_CUSTOMSTYLE) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO_SHIRTS)) ? "" : this.sweatbandcolor.getValue().getValue());
    if (this.item.getSweatbandColor().equals("")) {
      this.sweatbandcolor.setValue(null);
    }
    


    this.item.setSweatbandStripeColor((this.sweatbandstripecolor.getValue() == null) || ((this.sweatbandstripecolor.isOther()) && (!firstload) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_CUSTOMSTYLE) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO_SHIRTS)) ? "" : this.sweatbandstripecolor.getValue().getValue());
    if (this.item.getSweatbandStripeColor().equals("")) {
      this.sweatbandstripecolor.setValue(null);
    }
    
    filterOutDisplay();
    if (this.orderdata.getOrderType() == OrderData.OrderType.OVERSEAS) {
      doSetVendorVisibleEvent();
    }
    this.setfield.setLogoLocations();
    
    if (!firstload) {
      doStyleChangedEvent();
    }
  }
  
  private void filterOutDisplay()
  {
    if (this.vendornumber.getStore().getAllModels().size() == 0) {
      this.vendornumber.setVisible(false);
      this.vendornumber.setValue("");
      this.item.setVendorNumber("");
      
      this.advancecustomizationgroup.setVisible(false);
      this.advancecustomizationgroup.setValue(null);
    }
    else {
      this.vendornumber.setVisible(true);
      if ((this.item.getVendorNumber().equals("")) || (this.item.getVendorNumber().equals("Default"))) {
        this.advancecustomizationgroup.setValue(this.advancecustomizationnoradio);
      } else {
        this.advancecustomizationgroup.setValue(this.advancecustomizationyesradio);
      }
      this.advancecustomizationgroup.setVisible(true);
    }
    
    if (this.profile.getStore().getAllModels().size() == 0) {
      this.profile.setVisible(false);
      this.profile.setValue("");
      this.item.setProfile("");
    } else {
      this.profile.setVisible(true);
    }
    
    if (this.numberofpanels.getStore().getAllModels().size() == 0) {
      this.numberofpanels.setVisible(false);
      this.numberofpanels.setValue("");
      this.item.setNumberOfPanels("");
    } else {
      this.numberofpanels.setVisible(true);
    }
    
    if (this.visorstitchcolor.getStore().getAllModels().size() == 0) {
      this.visorstitchcolor.setVisible(false);
      this.visorstitchcolor.setValue("");
      this.item.setVisorStitchColor("");
    } else {
      this.visorstitchcolor.setVisible(true);
    }
    
    if (this.panelstitchcolor.getStore().getAllModels().size() == 0) {
      this.panelstitchcolor.setVisible(false);
      this.panelstitchcolor.setValue("");
      this.item.setPanelStitchColor("");
    } else {
      this.panelstitchcolor.setVisible(true);
    }
    
    if (this.crownconstruction.getStore().getAllModels().size() == 0) {
      this.crownconstruction.setVisible(false);
      this.crownconstruction.setValue("");
      this.item.setCrownConstruction("");
    } else {
      this.crownconstruction.setVisible(true);
    }
    
    if (this.visorrowstitching.getStore().getAllModels().size() == 0) {
      this.visorrowstitching.setVisible(false);
      this.visorrowstitching.setValue("");
      this.item.setVisorRowStitching("");
    } else {
      this.visorrowstitching.setVisible(true);
    }
    
    if (this.frontpanelfabric.getStore().getAllModels().size() == 0) {
      this.frontpanelfabric.setVisible(false);
      this.frontpanelfabric.setValue("");
      this.item.setFrontPanelFabric("");
    } else {
      this.frontpanelfabric.setVisible(true);
    }
    
    if (this.sidebackpanelfabric.getStore().getAllModels().size() == 0) {
      this.sidebackpanelfabric.setVisible(false);
      this.sidebackpanelfabric.setValue("");
      this.item.setSideBackPanelFabric("");
    } else {
      this.sidebackpanelfabric.setVisible(true);
    }
    
    if (this.backpanelfabric.getStore().getAllModels().size() == 0) {
      this.backpanelfabric.setVisible(false);
      this.backpanelfabric.setValue("");
      this.item.setBackPanelFabric("");
    } else {
      this.backpanelfabric.setVisible(true);
    }
    
    if (this.sidepanelfabric.getStore().getAllModels().size() == 0) {
      this.sidepanelfabric.setVisible(false);
      this.sidepanelfabric.setValue("");
      this.item.setSidePanelFabric("");
    } else {
      this.sidepanelfabric.setVisible(true);
    }
    
    if ((this.productsize.getStore().getAllModels().size() == 0) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.NONOTTO))
    {
      this.productsize.setVisible(false);
      
      this.productsize.setValue("");
      this.item.setSize("");
    }
    else {
      this.productsize.setVisible(true);
    }
    
    if ((this.productcolor.getStore().getAllModels().size() == 0) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.NONOTTO) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO_SHIRTS) && ((!this.item.getStyleNumber().equals("888-ConceptCap")) || (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.DOMESTIC)))
    {
      this.productcolor.setVisible(false);
      
      this.productcolor.setValue("");
      this.item.setColorCode("");
    }
    else {
      this.productcolor.setVisible(true);
    }
    
    if ((this.frontpanelcolor.getStore().getAllModels().size() == 0) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO) && ((!this.item.getStyleNumber().equals("888-ConceptCap")) || (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_INSTOCK)))
    {
      this.frontpanelcolor.setVisible(false);
      
      this.frontpanelcolor.setValue("");
      this.item.setFrontPanelColor("");
    }
    else {
      this.frontpanelcolor.setVisible(true);
    }
    
    if ((this.sidebackpanelcolor.getStore().getAllModels().size() == 0) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO) && ((!this.item.getStyleNumber().equals("888-ConceptCap")) || (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_INSTOCK))) {
      this.sidebackpanelcolor.setVisible(false);
      this.sidebackpanelcolor.setValue("");
      this.item.setSideBackPanelColor("");
    } else {
      this.sidebackpanelcolor.setVisible(true);
    }
    if ((this.backpanelcolor.getStore().getAllModels().size() == 0) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO) && ((!this.item.getStyleNumber().equals("888-ConceptCap")) || (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_INSTOCK))) {
      this.backpanelcolor.setVisible(false);
      this.backpanelcolor.setValue("");
      this.item.setBackPanelColor("");
    } else {
      this.backpanelcolor.setVisible(true);
    }
    if ((this.sidepanelcolor.getStore().getAllModels().size() == 0) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO) && ((!this.item.getStyleNumber().equals("888-ConceptCap")) || (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_INSTOCK))) {
      this.sidepanelcolor.setVisible(false);
      this.sidepanelcolor.setValue("");
      this.item.setSidePanelColor("");
    } else {
      this.sidepanelcolor.setVisible(true);
    }
    
    if ((this.buttoncolor.getStore().getAllModels().size() == 0) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO) && ((!this.item.getStyleNumber().equals("888-ConceptCap")) || (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_INSTOCK)))
    {
      this.buttoncolor.setVisible(false);
      
      this.buttoncolor.setValue("");
      this.item.setButtonColor("");
    }
    else {
      this.buttoncolor.setVisible(true);
    }
    
    if ((this.innertapingcolor.getStore().getAllModels().size() == 0) && (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_NONOTTO) && ((!this.item.getStyleNumber().equals("888-ConceptCap")) || (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_INSTOCK)))
    {

      this.innertapingcolor.setVisible(false);
      
      this.innertapingcolor.setValue("");
      this.item.setInnerTapingColor("");
    }
    else {
      this.innertapingcolor.setVisible(true);
    }
    

    filterVisorStyleNumber();
    filterClosureStyleNumber();
    filterEyeletStyleNumber();
    filterSweatbandStyleNumber();
  }
  
  public void filterSweatbandStyleNumber()
  {
    if (this.sweatbandstylenumber.getStore().getAllModels().size() == 0)
    {
      this.sweatbandstylenumber.setVisible(false);
      
      this.sweatbandstylenumber.setValue("");
      this.item.setSweatbandStyleNumber("");
    }
    else {
      this.sweatbandstylenumber.setVisible(true);
    }
    

    if (this.item.getSweatbandStyleNumber().equals("")) {
      showhidesweatbandquickfilter(false, false);
    } else if (this.item.getSweatbandStyleNumber().equals("S1")) {
      showhidesweatbandquickfilter(true, false);
    } else if (this.item.getSweatbandStyleNumber().equals("S2")) {
      showhidesweatbandquickfilter(true, true);
    } else if (this.item.getSweatbandStyleNumber().equals("S3")) {
      showhidesweatbandquickfilter(true, false);
    } else if (this.item.getSweatbandStyleNumber().equals("S4")) {
      showhidesweatbandquickfilter(true, false);
    } else if (this.item.getSweatbandStyleNumber().equals("S5")) {
      showhidesweatbandquickfilter(true, false);
    } else if (this.item.getSweatbandStyleNumber().equals("S6")) {
      showhidesweatbandquickfilter(true, false);
    } else {
      showhidesweatbandquickfilter(false, false);
    }
  }
  
  public void showhidesweatbandquickfilter(boolean field1, boolean field2) {
    if (!field1) {
      this.sweatbandcolor.setValue("");
      this.item.setSweatbandColor("");
    }
    if (!field2) {
      this.sweatbandstripecolor.setValue("");
      this.item.setSweatbandStripeColor("");
    }
    


    if (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_CUSTOMSTYLE) {
      this.sweatbandcolor.setVisible(field1);
      this.sweatbandstripecolor.setVisible(field2);
    } else {
      this.sweatbandcolor.setVisible((field1) && (this.sweatbandcolor.getStore().getAllModels().size() != 0));
      if ((!field1) || (this.sweatbandcolor.getStore().getAllModels().size() == 0)) {
        this.sweatbandcolor.setValue("");
        this.item.setSweatbandColor("");
      }
      this.sweatbandstripecolor.setVisible((field2) && (this.sweatbandstripecolor.getStore().getAllModels().size() != 0));
      if ((!field2) || (this.sweatbandstripecolor.getStore().getAllModels().size() == 0)) {
        this.sweatbandstripecolor.setValue("");
        this.item.setSweatbandStripeColor("");
      }
    }
  }
  
  public void filterEyeletStyleNumber() {
    if (this.eyeletstylenumber.getStore().getAllModels().size() == 0)
    {
      this.eyeletstylenumber.setVisible(false);
      this.eyeletstylenumber.setValue("");
      this.item.setEyeletStyleNumber("");
    }
    else {
      this.eyeletstylenumber.setVisible(true);
    }
    
    if (this.item.getEyeletStyleNumber().equals("E1")) {
      showhideeyeletquickfilter(true, true);
    } else if (this.item.getEyeletStyleNumber().equals("E2")) {
      showhideeyeletquickfilter(false, true);
    } else if (this.item.getEyeletStyleNumber().equals("E3")) {
      showhideeyeletquickfilter(true, false);
    } else {
      showhideeyeletquickfilter(false, false);
    }
  }
  
  public void showhideeyeletquickfilter(boolean field1, boolean field2) {
    if (!field1) {
      this.fronteyeletcolor.setValue("");
      this.item.setFrontEyeletColor("");
    }
    if (!field2) {
      this.sidebackeyeletcolor.setValue("");
      this.item.setSideBackEyeletColor("");
      this.backeyeletcolor.setValue("");
      this.item.setBackEyeletColor("");
      this.sideeyeletcolor.setValue("");
      this.item.setSideEyeletColor("");
    }
    
    this.fronteyeletcolor.setVisible((field1) && ((getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_INSTOCK) || ((getCurrentStyle().getStyleType() == StyleInformationData.StyleType.OVERSEAS_INSTOCK) && (!this.item.getVendorNumber().equals("Default")) && (!this.item.getVendorNumber().equals("")))));
    
    this.fronteyeletcolor.setFieldLabel("Front Eyelet Color");
    
    if (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_CUSTOMSTYLE) {
      this.backeyeletcolor.setValue("");
      this.item.setBackEyeletColor("");
      this.sideeyeletcolor.setValue("");
      this.item.setSideEyeletColor("");
      this.backeyeletcolor.setVisible(false);
      this.sideeyeletcolor.setVisible(false);
      this.sidebackeyeletcolor.setVisible((field2) && ((getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_INSTOCK) || ((getCurrentStyle().getStyleType() == StyleInformationData.StyleType.OVERSEAS_INSTOCK) && (!this.item.getVendorNumber().equals("Default")) && (!this.item.getVendorNumber().equals("")))));
    } else {
      this.sidebackeyeletcolor.setValue("");
      this.item.setSideBackEyeletColor("");
      this.sidebackeyeletcolor.setVisible((field2) && (this.sidebackeyeletcolor.getStore().getAllModels().size() != 0) && (!field1));
      if ((!field2) || (this.sidebackeyeletcolor.getStore().getAllModels().size() == 0) || (field1)) {
        this.sidebackeyeletcolor.setValue("");
        this.item.setSideBackEyeletColor("");
        if (field2) {
          this.fronteyeletcolor.setFieldLabel("Eyelet Color");
        }
      }
      this.backeyeletcolor.setVisible((field2) && (this.backeyeletcolor.getStore().getAllModels().size() != 0));
      if ((!field2) || (this.backeyeletcolor.getStore().getAllModels().size() == 0)) {
        this.backeyeletcolor.setValue("");
        this.item.setBackEyeletColor("");
      }
      this.sideeyeletcolor.setVisible((field2) && (this.sideeyeletcolor.getStore().getAllModels().size() != 0));
      if ((!field2) || (this.sideeyeletcolor.getStore().getAllModels().size() == 0)) {
        this.sideeyeletcolor.setValue("");
        this.item.setSideEyeletColor("");
      }
    }
  }
  
  public void filterClosureStyleNumber()
  {
    if (this.closurestylenumber.getStore().getAllModels().size() == 0)
    {
      this.closurestylenumber.setVisible(false);
      this.closurestylenumber.setValue("");
      this.item.setClosureStyleNumber("");
    }
    else {
      this.closurestylenumber.setVisible(true);
    }
    
    if (this.item.getClosureStyleNumber().equals("")) {
      showhideclosurequickfilter(false);
    } else if (this.item.getClosureStyleNumber().equals("C1")) {
      showhideclosurequickfilter(true);
    } else if (this.item.getClosureStyleNumber().equals("C2")) {
      showhideclosurequickfilter(true);
    } else if (this.item.getClosureStyleNumber().equals("C3")) {
      showhideclosurequickfilter(true);
    } else if (this.item.getClosureStyleNumber().equals("C4")) {
      showhideclosurequickfilter(true);
    } else if (this.item.getClosureStyleNumber().equals("C5")) {
      showhideclosurequickfilter(true);
    } else if (this.item.getClosureStyleNumber().equals("C6")) {
      showhideclosurequickfilter(true);
    } else if (this.item.getClosureStyleNumber().equals("C7")) {
      showhideclosurequickfilter(true);
    } else if (this.item.getClosureStyleNumber().equals("C8")) {
      showhideclosurequickfilter(true);
    } else if (this.item.getClosureStyleNumber().equals("C9")) {
      showhideclosurequickfilter(true);
    } else if (this.item.getClosureStyleNumber().equals("C10")) {
      showhideclosurequickfilter(true);
    } else if (this.item.getClosureStyleNumber().equals("C11")) {
      showhideclosurequickfilter(true);
    } else if (this.item.getClosureStyleNumber().equals("C12")) {
      showhideclosurequickfilter(false);
    } else if (this.item.getClosureStyleNumber().equals("C13")) {
      showhideclosurequickfilter(false);
    } else if (this.item.getClosureStyleNumber().equals("C14")) {
      showhideclosurequickfilter(false);
    } else if (this.item.getClosureStyleNumber().equals("C15")) {
      showhideclosurequickfilter(true);
    } else if (this.item.getClosureStyleNumber().equals("C16")) {
      showhideclosurequickfilter(true);
    } else if (this.item.getClosureStyleNumber().equals("C17")) {
      showhideclosurequickfilter(true);
    } else if (this.item.getClosureStyleNumber().equals("C18")) {
      showhideclosurequickfilter(true);
    } else if (this.item.getClosureStyleNumber().equals("C19")) {
      showhideclosurequickfilter(true);
    } else if (this.item.getClosureStyleNumber().equals("C20")) {
      showhideclosurequickfilter(true);
    } else if (this.item.getClosureStyleNumber().equals("C21")) {
      showhideclosurequickfilter(true);
    } else if (this.item.getClosureStyleNumber().equals("C22")) {
      showhideclosurequickfilter(true);
    } else if (this.item.getClosureStyleNumber().equals("C23")) {
      showhideclosurequickfilter(true);
    } else if (this.item.getClosureStyleNumber().equals("C24")) {
      showhideclosurequickfilter(true);
    } else if (this.item.getClosureStyleNumber().equals("C25")) {
      showhideclosurequickfilter(false);
    } else if (this.item.getClosureStyleNumber().equals("C26")) {
      showhideclosurequickfilter(true);
    } else if (this.item.getClosureStyleNumber().equals("C27")) {
      showhideclosurequickfilter(true);
    } else if (this.item.getClosureStyleNumber().equals("C28")) {
      showhideclosurequickfilter(true);
    } else if (this.item.getClosureStyleNumber().equals("C29")) {
      showhideclosurequickfilter(true);
    } else if (this.item.getClosureStyleNumber().equals("C30")) {
      showhideclosurequickfilter(true);
    } else if (this.item.getClosureStyleNumber().equals("C31")) {
      showhideclosurequickfilter(true);
    } else if (this.item.getClosureStyleNumber().equals("Default")) {
      showhideclosurequickfilter(true);
    } else {
      showhideclosurequickfilter(false);
    }
  }
  
  public void showhideclosurequickfilter(boolean field1) {
    if (!field1) {
      this.closurestrapcolor.setValue("");
      this.item.setClosureStrapColor("");
    }
    





    if (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_CUSTOMSTYLE) {
      this.closurestrapcolor.setVisible((field1) && ((getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_INSTOCK) || ((getCurrentStyle().getStyleType() == StyleInformationData.StyleType.OVERSEAS_INSTOCK) && (!this.item.getVendorNumber().equals("Default")) && (!this.item.getVendorNumber().equals("")))));
    } else {
      this.closurestrapcolor.setVisible((field1) && (this.closurestrapcolor.getStore().getAllModels().size() != 0));
      if ((!field1) || (this.closurestrapcolor.getStore().getAllModels().size() == 0)) {
        this.closurestrapcolor.setValue("");
        this.item.setClosureStrapColor("");
      }
    }
  }
  
  private void filterVisorStyleNumber() {
    if (this.visorstylenumber.getStore().getAllModels().size() == 0)
    {
      this.visorstylenumber.setVisible(false);
      this.visorstylenumber.setValue("");
      this.item.setVisorStyleNumber("");
    }
    else {
      this.visorstylenumber.setVisible(true);
    }
    
    if (this.item.getVisorStyleNumber().equals("")) {
      showhidevisorquickfilter(false, false, false, false, false);
    } else if (this.item.getVisorStyleNumber().equals("V1")) {
      showhidevisorquickfilter(true, false, false, true, false);
    } else if (this.item.getVisorStyleNumber().equals("V2")) {
      showhidevisorquickfilter(true, false, true, true, false);
    } else if (this.item.getVisorStyleNumber().equals("V3")) {
      showhidevisorquickfilter(true, true, false, false, false);
    } else if (this.item.getVisorStyleNumber().equals("V4")) {
      showhidevisorquickfilter(true, true, false, true, false);
    } else if (this.item.getVisorStyleNumber().equals("V5")) {
      showhidevisorquickfilter(true, true, false, true, false);
    } else if (this.item.getVisorStyleNumber().equals("V6")) {
      showhidevisorquickfilter(true, false, false, true, true);
    } else if (this.item.getVisorStyleNumber().equals("V7")) {
      showhidevisorquickfilter(true, true, false, true, false);
    } else if (this.item.getVisorStyleNumber().equals("V8")) {
      showhidevisorquickfilter(true, true, true, true, false);
    } else if (this.item.getVisorStyleNumber().equals("V9")) {
      showhidevisorquickfilter(true, false, false, true, false);
    } else if (this.item.getVisorStyleNumber().equals("V10")) {
      showhidevisorquickfilter(true, true, false, true, false);
    } else if (this.item.getVisorStyleNumber().equals("V11")) {
      showhidevisorquickfilter(true, false, false, true, false);
    } else if (this.item.getVisorStyleNumber().equals("V12")) {
      showhidevisorquickfilter(true, false, false, true, false);
    } else if (this.item.getVisorStyleNumber().equals("V15")) {
      showhidevisorquickfilter(true, true, false, false, false);
    } else if (this.item.getVisorStyleNumber().equals("V16")) {
      showhidevisorquickfilter(true, false, true, true, false);
    } else if (this.item.getVisorStyleNumber().equals("V18")) {
      showhidevisorquickfilter(true, false, false, true, false);
    } else {
      showhidevisorquickfilter(false, false, false, false, false);
    }
  }
  
  public void showhidevisorquickfilter(boolean field1, boolean field2, boolean field3, boolean field4, boolean field5)
  {
    if (!field1) {
      this.primaryvisorcolor.setValue("");
      this.item.setPrimaryVisorColor("");
    }
    if (!field2) {
      this.visortrimedgecolor.setValue("");
      this.item.setVisorTrimColor("");
    }
    if (!field3) {
      this.visorsandwichcolor.setValue("");
      this.item.setVisorSandwichColor("");
    }
    if (!field4) {
      this.undervisorcolor.setValue("");
      this.item.setUndervisorColor("");
    }
    if (!field5) {
      this.distressedvisorinsidecolor.setValue("");
      this.item.setDistressedVisorInsideColor("");
    }
    





    if (getCurrentStyle().getStyleType() != StyleInformationData.StyleType.OVERSEAS_CUSTOMSTYLE) {
      this.primaryvisorcolor.setVisible(field1);
    } else {
      this.primaryvisorcolor.setVisible((field1) && (this.primaryvisorcolor.getStore().getAllModels().size() != 0));
      if ((!field1) || (this.primaryvisorcolor.getStore().getAllModels().size() == 0)) {
        this.primaryvisorcolor.setValue("");
        this.item.setPrimaryVisorColor("");
      }
    }
    this.visortrimedgecolor.setVisible(field2);
    this.visorsandwichcolor.setVisible(field3);
    this.undervisorcolor.setVisible(field4);
    this.distressedvisorinsidecolor.setVisible(field5);
  }
  
  private Listener<BaseEvent> changelistener = new Listener()
  {
    public void handleEvent(BaseEvent be)
    {
      if (be.getSource().equals(ItemPortletField.this.emailproof)) {
        ItemPortletField.this.item.setProductSampleEmail(ItemPortletField.this.emailproof.getValue().booleanValue());
        ItemPortletField.this.proofstodo.validate();
      } else if (be.getSource().equals(ItemPortletField.this.shipproof)) {
        ItemPortletField.this.item.setProductSampleShip(ItemPortletField.this.shipproof.getValue().booleanValue());
        ItemPortletField.this.proofstodo.validate();
      } else if (be.getSource().equals(ItemPortletField.this.proofstodo)) {
        ItemPortletField.this.item.setProductSampleToDo(ItemPortletField.this.proofstodo.getValue() == null ? null : Integer.valueOf(((Number)ItemPortletField.this.proofstodo.getValue()).intValue()));
        ItemPortletField.this.proofstodo.validate();
      } else if (be.getSource().equals(ItemPortletField.this.productcategory)) {
        ItemPortletField.this.filterProducts();
      } else if (be.getSource().equals(ItemPortletField.this.productstylenumber)) {
        ItemPortletField.this.changeproductstyle();
      } else if (be.getSource().equals(ItemPortletField.this.vendornumber)) {
        ItemPortletField.this.item.setVendorNumber(ItemPortletField.this.vendornumber.getValue() == null ? "" : ItemPortletField.this.vendornumber.getValue().getValue());
        ItemPortletField.this.changeproductstyle();
      } else if (be.getSource().equals(ItemPortletField.this.productcolor)) {
        ItemPortletField.this.item.setColorCode(ItemPortletField.this.productcolor.getValue() == null ? "" : ItemPortletField.this.productcolor.getValue().getValue());
        ItemPortletField.this.updateProductPreview();
      } else if (be.getSource().equals(ItemPortletField.this.productsize)) {
        ItemPortletField.this.item.setSize(ItemPortletField.this.productsize.getValue() == null ? "" : ItemPortletField.this.productsize.getValue().getValue());
        ItemPortletField.this.setStyleStoreSizeColorFields(false);
      } else if (be.getSource().equals(ItemPortletField.this.frontpanelcolor)) {
        ItemPortletField.this.item.setFrontPanelColor(ItemPortletField.this.frontpanelcolor.getValue() == null ? "" : ItemPortletField.this.frontpanelcolor.getValue().getValue());
        ItemPortletField.this.updateProductPreview();
      } else if (be.getSource().equals(ItemPortletField.this.sidebackpanelcolor)) {
        ItemPortletField.this.item.setSideBackPanelColor(ItemPortletField.this.sidebackpanelcolor.getValue() == null ? "" : ItemPortletField.this.sidebackpanelcolor.getValue().getValue());
        ItemPortletField.this.updateProductPreview();
      } else if (be.getSource().equals(ItemPortletField.this.backpanelcolor)) {
        ItemPortletField.this.item.setBackPanelColor(ItemPortletField.this.backpanelcolor.getValue() == null ? "" : ItemPortletField.this.backpanelcolor.getValue().getValue());
        ItemPortletField.this.updateProductPreview();
      } else if (be.getSource().equals(ItemPortletField.this.sidepanelcolor)) {
        ItemPortletField.this.item.setSidePanelColor(ItemPortletField.this.sidepanelcolor.getValue() == null ? "" : ItemPortletField.this.sidepanelcolor.getValue().getValue());
        ItemPortletField.this.updateProductPreview();
      } else if (be.getSource().equals(ItemPortletField.this.visorstylenumber)) {
        ItemPortletField.this.item.setVisorStyleNumber(ItemPortletField.this.visorstylenumber.getValue() == null ? "" : ItemPortletField.this.visorstylenumber.getValue().getValue());
        ItemPortletField.this.filterVisorStyleNumber();
        ItemPortletField.this.updateProductPreview();
      } else if (be.getSource().equals(ItemPortletField.this.primaryvisorcolor)) {
        ItemPortletField.this.item.setPrimaryVisorColor(ItemPortletField.this.primaryvisorcolor.getValue() == null ? "" : ItemPortletField.this.primaryvisorcolor.getValue().getValue());
        ItemPortletField.this.updateProductPreview();
      } else if (be.getSource().equals(ItemPortletField.this.visortrimedgecolor)) {
        ItemPortletField.this.item.setVisorTrimColor(ItemPortletField.this.visortrimedgecolor.getValue() == null ? "" : ItemPortletField.this.visortrimedgecolor.getValue().getValue());
        ItemPortletField.this.updateProductPreview();
      } else if (be.getSource().equals(ItemPortletField.this.visorsandwichcolor)) {
        ItemPortletField.this.item.setVisorSandwichColor(ItemPortletField.this.visorsandwichcolor.getValue() == null ? "" : ItemPortletField.this.visorsandwichcolor.getValue().getValue());
        ItemPortletField.this.updateProductPreview();
      } else if (be.getSource().equals(ItemPortletField.this.distressedvisorinsidecolor)) {
        ItemPortletField.this.item.setDistressedVisorInsideColor(ItemPortletField.this.distressedvisorinsidecolor.getValue() == null ? "" : ItemPortletField.this.distressedvisorinsidecolor.getValue().getValue());
        ItemPortletField.this.updateProductPreview();
      } else if (be.getSource().equals(ItemPortletField.this.undervisorcolor)) {
        ItemPortletField.this.item.setUndervisorColor(ItemPortletField.this.undervisorcolor.getValue() == null ? "" : ItemPortletField.this.undervisorcolor.getValue().getValue());
        ItemPortletField.this.updateProductPreview();
      } else if (be.getSource().equals(ItemPortletField.this.closurestylenumber)) {
        ItemPortletField.this.item.setClosureStyleNumber(ItemPortletField.this.closurestylenumber.getValue() == null ? "" : ItemPortletField.this.closurestylenumber.getValue().getValue());
        ItemPortletField.this.changeproductstyle();













      }
      else if (be.getSource().equals(ItemPortletField.this.closurestrapcolor)) {
        ItemPortletField.this.item.setClosureStrapColor(ItemPortletField.this.closurestrapcolor.getValue() == null ? "" : ItemPortletField.this.closurestrapcolor.getValue().getValue());
        ItemPortletField.this.updateProductPreview();
      } else if (be.getSource().equals(ItemPortletField.this.eyeletstylenumber)) {
        ItemPortletField.this.item.setEyeletStyleNumber(ItemPortletField.this.eyeletstylenumber.getValue() == null ? "" : ItemPortletField.this.eyeletstylenumber.getValue().getValue());
        ItemPortletField.this.filterEyeletStyleNumber();
        ItemPortletField.this.updateProductPreview();
      } else if (be.getSource().equals(ItemPortletField.this.fronteyeletcolor)) {
        ItemPortletField.this.item.setFrontEyeletColor(ItemPortletField.this.fronteyeletcolor.getValue() == null ? "" : ItemPortletField.this.fronteyeletcolor.getValue().getValue());
        ItemPortletField.this.updateProductPreview();
      } else if (be.getSource().equals(ItemPortletField.this.sidebackeyeletcolor)) {
        ItemPortletField.this.item.setSideBackEyeletColor(ItemPortletField.this.sidebackeyeletcolor.getValue() == null ? "" : ItemPortletField.this.sidebackeyeletcolor.getValue().getValue());
        ItemPortletField.this.updateProductPreview();
      } else if (be.getSource().equals(ItemPortletField.this.backeyeletcolor)) {
        ItemPortletField.this.item.setBackEyeletColor(ItemPortletField.this.backeyeletcolor.getValue() == null ? "" : ItemPortletField.this.backeyeletcolor.getValue().getValue());
        ItemPortletField.this.updateProductPreview();
      } else if (be.getSource().equals(ItemPortletField.this.sideeyeletcolor)) {
        ItemPortletField.this.item.setSideEyeletColor(ItemPortletField.this.sideeyeletcolor.getValue() == null ? "" : ItemPortletField.this.sideeyeletcolor.getValue().getValue());
        ItemPortletField.this.updateProductPreview();
      } else if (be.getSource().equals(ItemPortletField.this.buttoncolor)) {
        ItemPortletField.this.item.setButtonColor(ItemPortletField.this.buttoncolor.getValue() == null ? "" : ItemPortletField.this.buttoncolor.getValue().getValue());
        ItemPortletField.this.updateProductPreview();
      } else if (be.getSource().equals(ItemPortletField.this.innertapingcolor)) {
        ItemPortletField.this.item.setInnerTapingColor(ItemPortletField.this.innertapingcolor.getValue() == null ? "" : ItemPortletField.this.innertapingcolor.getValue().getValue());
        ItemPortletField.this.updateProductPreview();
      } else if (be.getSource().equals(ItemPortletField.this.sweatbandstylenumber)) {
        ItemPortletField.this.item.setSweatbandStyleNumber(ItemPortletField.this.sweatbandstylenumber.getValue() == null ? "" : ItemPortletField.this.sweatbandstylenumber.getValue().getValue());
        ItemPortletField.this.changeproductstyle();










      }
      else if (be.getSource().equals(ItemPortletField.this.sweatbandcolor)) {
        ItemPortletField.this.item.setSweatbandColor(ItemPortletField.this.sweatbandcolor.getValue() == null ? "" : ItemPortletField.this.sweatbandcolor.getValue().getValue());
        ItemPortletField.this.updateProductPreview();
      } else if (be.getSource().equals(ItemPortletField.this.sweatbandstripecolor)) {
        ItemPortletField.this.item.setSweatbandStripeColor(ItemPortletField.this.sweatbandstripecolor.getValue() == null ? "" : ItemPortletField.this.sweatbandstripecolor.getValue().getValue());
        ItemPortletField.this.updateProductPreview();
      } else if (be.getSource().equals(ItemPortletField.this.personalization)) {
        ItemPortletField.this.personaliztionchanges.setVisible(ItemPortletField.this.personalization.getValue().booleanValue());
        if (!ItemPortletField.this.personalization.getValue().booleanValue()) {
          ItemPortletField.this.personaliztionchanges.setValue(null);
          ItemPortletField.this.item.setPersonalizationChanges(null);
        }
      } else if (be.getSource().equals(ItemPortletField.this.privateorstandardlabelgroup)) {
        if (ItemPortletField.this.privateorstandardlabelgroup.getValue() == null) {
          ItemPortletField.this.item.setHasPrivateLabel(null);
        } else if (ItemPortletField.this.privateorstandardlabelgroup.getValue().equals(ItemPortletField.this.standardlabelradio)) {
          ItemPortletField.this.item.setHasPrivateLabel(Boolean.valueOf(false));
        } else if (ItemPortletField.this.privateorstandardlabelgroup.getValue().equals(ItemPortletField.this.privatelabelradio)) {
          ItemPortletField.this.item.setHasPrivateLabel(Boolean.valueOf(true));
          if (!ItemPortletField.this.item.getStyleNumber().equals("")) {
            boolean foundlabel = false;
            NewStyleStore currentstylestore = ItemPortletField.this.getCurrentStyle();
            for (OrderDataLogo thelogo : ItemPortletField.this.orderdataset.getLogos())
            {
              if ((currentstylestore.getStyleType() != StyleInformationData.StyleType.OVERSEAS_INSTOCK_SHIRTS) && (thelogo.getLogoLocation().equals("28"))) {
                foundlabel = true;
              } else if ((currentstylestore.getStyleType() == StyleInformationData.StyleType.OVERSEAS_INSTOCK_SHIRTS) && (thelogo.getLogoLocation().equals("A17"))) {
                foundlabel = true;
              } else if (currentstylestore.getStyleType() == StyleInformationData.StyleType.OVERSEAS_INSTOCK_FLATS) {
                foundlabel = true;
              }
            }
            if (!foundlabel) {
              OrderDataLogo thenewlogo = new OrderDataLogo();
              if (currentstylestore.getStyleType() == StyleInformationData.StyleType.OVERSEAS_INSTOCK_SHIRTS) {
                thenewlogo.setLogoLocation("A17");
              } else {
                thenewlogo.setLogoLocation("28");
              }
              ItemPortletField.this.doAddLogoEvent(thenewlogo);
            }
          }
        }
      } else if (be.getSource().equals(ItemPortletField.this.privateorstandardpackaginggroup)) {
        if (ItemPortletField.this.privateorstandardpackaginggroup.getValue() == null) {
          ItemPortletField.this.item.setHasPrivatePackaging(null);
        } else if (ItemPortletField.this.privateorstandardpackaginggroup.getValue().equals(ItemPortletField.this.standardpackagingradio)) {
          ItemPortletField.this.item.setHasPrivatePackaging(Boolean.valueOf(false));
        } else if (ItemPortletField.this.privateorstandardpackaginggroup.getValue().equals(ItemPortletField.this.privatepackagingradio)) {
          ItemPortletField.this.item.setHasPrivatePackaging(Boolean.valueOf(true));
        }
      } else if (be.getSource().equals(ItemPortletField.this.privateorstandardshippingmarkgroup)) {
        if (ItemPortletField.this.privateorstandardshippingmarkgroup.getValue() == null) {
          ItemPortletField.this.item.setHasPrivateShippingMark(null);
        } else if (ItemPortletField.this.privateorstandardshippingmarkgroup.getValue().equals(ItemPortletField.this.standardshippingmarkradio)) {
          ItemPortletField.this.item.setHasPrivateShippingMark(Boolean.valueOf(false));
        } else if (ItemPortletField.this.privateorstandardshippingmarkgroup.getValue().equals(ItemPortletField.this.privateshippingmarkradio)) {
          ItemPortletField.this.item.setHasPrivateShippingMark(Boolean.valueOf(true));
        }
      } else if (be.getSource().equals(ItemPortletField.this.advancecustomizationgroup)) {
        if (ItemPortletField.this.advancecustomizationgroup.getValue() != null)
        {
          if (ItemPortletField.this.advancecustomizationgroup.getValue().equals(ItemPortletField.this.advancecustomizationyesradio)) {
            if ((ItemPortletField.this.vendornumber.getStore().getAllModels().size() > 1) && 
              (ItemPortletField.this.vendornumber.getValue() == ItemPortletField.this.vendornumber.getStore().getAt(0))) {
              ItemPortletField.this.vendornumber.setValue((OtherComboBoxModelData)ItemPortletField.this.vendornumber.getStore().getAt(1));
              ItemPortletField.this.item.setVendorNumber(ItemPortletField.this.vendornumber.getValue() == null ? "" : ItemPortletField.this.vendornumber.getValue().getValue());
              ItemPortletField.this.changeproductstyle();
            }
          }
          else if ((ItemPortletField.this.advancecustomizationgroup.getValue().equals(ItemPortletField.this.advancecustomizationnoradio)) && 
            (ItemPortletField.this.vendornumber.getStore().getAllModels().size() != 0) && 
            (ItemPortletField.this.vendornumber.getValue() != ItemPortletField.this.vendornumber.getStore().getAt(0))) {
            ItemPortletField.this.vendornumber.setValue((OtherComboBoxModelData)ItemPortletField.this.vendornumber.getStore().getAt(0));
            ItemPortletField.this.item.setVendorNumber(ItemPortletField.this.vendornumber.getValue() == null ? "" : ItemPortletField.this.vendornumber.getValue().getValue());
            ItemPortletField.this.changeproductstyle();
          }
          
        }
        

      }
      else if (be.getSource().equals(ItemPortletField.this.profile)) {
        ItemPortletField.this.item.setProfile(ItemPortletField.this.profile.getValue() == null ? "" : ItemPortletField.this.profile.getValue().getValue());
        ItemPortletField.this.changeproductstyle();
      }
      else if (be.getSource().equals(ItemPortletField.this.frontpanelfabric)) {
        ItemPortletField.this.item.setFrontPanelFabric(ItemPortletField.this.frontpanelfabric.getValue() == null ? "" : ItemPortletField.this.frontpanelfabric.getValue().getValue());
        ItemPortletField.this.changeproductstyle();
      } else if (be.getSource().equals(ItemPortletField.this.sidebackpanelfabric)) {
        ItemPortletField.this.item.setSideBackPanelFabric(ItemPortletField.this.sidebackpanelfabric.getValue() == null ? "" : ItemPortletField.this.sidebackpanelfabric.getValue().getValue());
        ItemPortletField.this.changeproductstyle();
      } else if (be.getSource().equals(ItemPortletField.this.backpanelfabric)) {
        ItemPortletField.this.item.setBackPanelFabric(ItemPortletField.this.backpanelfabric.getValue() == null ? "" : ItemPortletField.this.backpanelfabric.getValue().getValue());
        ItemPortletField.this.changeproductstyle();
      } else if (be.getSource().equals(ItemPortletField.this.sidepanelfabric)) {
        ItemPortletField.this.item.setSidePanelFabric(ItemPortletField.this.sidepanelfabric.getValue() == null ? "" : ItemPortletField.this.sidepanelfabric.getValue().getValue());
        ItemPortletField.this.changeproductstyle();
      } else if (be.getSource().equals(ItemPortletField.this.numberofpanels)) {
        ItemPortletField.this.item.setNumberOfPanels(ItemPortletField.this.numberofpanels.getValue() == null ? "" : ItemPortletField.this.numberofpanels.getValue().getValue());
        ItemPortletField.this.updateProductPreview();
      } else if (be.getSource().equals(ItemPortletField.this.crownconstruction)) {
        ItemPortletField.this.item.setCrownConstruction(ItemPortletField.this.crownconstruction.getValue() == null ? "" : ItemPortletField.this.crownconstruction.getValue().getValue());
        ItemPortletField.this.updateProductPreview();
      } else if (be.getSource().equals(ItemPortletField.this.visorrowstitching)) {
        ItemPortletField.this.item.setVisorRowStitching(ItemPortletField.this.visorrowstitching.getValue() == null ? "" : ItemPortletField.this.visorrowstitching.getValue().getValue());
        ItemPortletField.this.updateProductPreview();
      } else if (be.getSource().equals(ItemPortletField.this.visorstitchcolor)) {
        ItemPortletField.this.item.setVisorStitchColor(ItemPortletField.this.visorstitchcolor.getValue() == null ? "" : ItemPortletField.this.visorstitchcolor.getValue().getValue());
        ItemPortletField.this.updateProductPreview();
      } else if (be.getSource().equals(ItemPortletField.this.panelstitchcolor)) {
        ItemPortletField.this.item.setPanelStitchColor(ItemPortletField.this.panelstitchcolor.getValue() == null ? "" : ItemPortletField.this.panelstitchcolor.getValue().getValue());
        ItemPortletField.this.updateProductPreview();
      }
    }
  };
  


  private Listener<FormEvent> submitlistener = new Listener()
  {
    public void handleEvent(FormEvent be)
    {
      if (be.getComponent().equals(ItemPortletField.this.myuploadform)) {
        String htmlstring = com.ottocap.NewWorkFlow.client.Widget.StringUtils.unescapeHTML(be.getResultHtml(), 0);
        htmlstring = htmlstring.replace("<pre>", "");
        htmlstring = htmlstring.replace("</pre>", "");
        JSONObject jsonObject = com.google.gwt.json.client.JSONParser.parseStrict(htmlstring).isObject();
        if (!jsonObject.containsKey("error")) {
          ItemPortletField.this.item.setPreviewFilename(jsonObject.get("Filename").isString().stringValue());
          ItemPortletField.this.isamplefield.setValue(ItemPortletField.this.item.getPreviewFilename());
          com.extjs.gxt.ui.client.widget.Info.display("Success", "iSample Image Been Uploaded");
          ItemPortletField.this.uploadsubmit.enable();
          ItemPortletField.this.updateProductPreview();
        } else {
          Throwable myerror = new Throwable(jsonObject.get("error").isString().stringValue());
          NewWorkFlow.get().reLogin2(myerror, ItemPortletField.this.uploadlogorelogin);
        }
      }
    }
  };
  

  BuzzAsyncCallback<String> uploadlogorelogin = new BuzzAsyncCallback()
  {
    public void onFailure(Throwable caught)
    {
      ItemPortletField.this.uploadsubmit.enable();
    }
    
    public void onSuccess(String result)
    {
      ItemPortletField.this.myuploadform.submit();
    }
    
    public void doRetry()
    {
      ItemPortletField.this.myuploadform.submit();
    }
  };
  
  private void setBindings()
  {
    Bindings allbindings = new Bindings();
    allbindings.bind(this.item);
    allbindings.addFieldBinding(new FieldBinding(this.proofstotaldone, "productsampletotaldone"));
    allbindings.addFieldBinding(new FieldBinding(this.proofstotalemail, "productsampletotalemail"));
    allbindings.addFieldBinding(new FieldBinding(this.proofstotalship, "productsampletotalship"));
    allbindings.addFieldBinding(new FieldBinding(this.quantity, "quantity"));
    allbindings.addFieldBinding(new FieldBinding(this.fobdozenprice, "fobprice"));
    allbindings.addFieldBinding(new FieldBinding(this.fobdozenmarkupprice, "fobmarkupprice"));
    allbindings.addFieldBinding(new FieldBinding(this.customstylename, "customstylename"));
    allbindings.addFieldBinding(new FieldBinding(this.polybag, "polybag"));
    allbindings.addFieldBinding(new FieldBinding(this.oakleaves, "oakleaves"));
    allbindings.addFieldBinding(new FieldBinding(this.tagging, "tagging"));
    allbindings.addFieldBinding(new FieldBinding(this.stickers, "stickers"));
    allbindings.addFieldBinding(new FieldBinding(this.sewingpatches, "sewingpatches"));
    allbindings.addFieldBinding(new FieldBinding(this.heatpresspatches, "heatpresspatches"));
    allbindings.addFieldBinding(new FieldBinding(this.removeinnerprintedlabel, "removeinnerprintedlabel"));
    allbindings.addFieldBinding(new FieldBinding(this.addinnerprintedlabel, "addinnerprintedlabel"));
    allbindings.addFieldBinding(new FieldBinding(this.removeinnerwovenlabel, "removeinnerwovenlabel"));
    allbindings.addFieldBinding(new FieldBinding(this.addinnerwovenlabel, "addinnerwovenlabel"));
    allbindings.addFieldBinding(new FieldBinding(this.personaliztionchanges, "personalizationchanges"));
    allbindings.addFieldBinding(new FieldBinding(this.itemspecialnotes, "comments"));
    allbindings.addFieldBinding(new OtherComboBoxFieldBinding(this.artworktype, "artworktype"));
    allbindings.addFieldBinding(new FieldBinding(this.artworktypecomments, "artworktypecomments"));
    allbindings.addFieldBinding(new OtherComboBoxFieldBinding(this.sampleapprovedlist, "sampleapprovedlist"));
    allbindings.addFieldBinding(new FieldBinding(this.productdiscount, "productdiscount"));
  }
  

  private void setFields()
  {
    if (this.orderdata.getOrderType() == OrderData.OrderType.DOMESTIC) {
      this.isampleordertype.setValue("Domestic");
    } else {
      this.isampleordertype.setValue("Overseas");
    }
    
    this.productcategory.setValue(new OtherComboBoxModelData("All Items"));
    this.productstylenumber.setValue(new OtherComboBoxModelData(this.item.getStyleNumber()));
    



    this.vendornumber.setValue(this.item.getVendorNumber());
    this.frontpanelcolor.setValue(this.item.getFrontPanelColor());
    this.sidebackpanelcolor.setValue(this.item.getSideBackPanelColor());
    this.backpanelcolor.setValue(this.item.getBackPanelColor());
    this.sidepanelcolor.setValue(this.item.getSidePanelColor());
    this.visorstylenumber.setValue(this.item.getVisorStyleNumber());
    this.primaryvisorcolor.setValue(this.item.getPrimaryVisorColor());
    this.visortrimedgecolor.setValue(this.item.getVisorTrimColor());
    this.visorsandwichcolor.setValue(this.item.getVisorSandwichColor());
    this.distressedvisorinsidecolor.setValue(this.item.getDistressedVisorInsideColor());
    this.undervisorcolor.setValue(this.item.getUndervisorColor());
    this.closurestylenumber.setValue(this.item.getClosureStyleNumber());
    this.closurestrapcolor.setValue(this.item.getClosureStrapColor());
    this.eyeletstylenumber.setValue(this.item.getEyeletStyleNumber());
    this.fronteyeletcolor.setValue(this.item.getFrontEyeletColor());
    this.sidebackeyeletcolor.setValue(this.item.getSideBackEyeletColor());
    this.backeyeletcolor.setValue(this.item.getBackEyeletColor());
    this.sideeyeletcolor.setValue(this.item.getSideEyeletColor());
    this.buttoncolor.setValue(this.item.getButtonColor());
    this.innertapingcolor.setValue(this.item.getInnerTapingColor());
    this.sweatbandstylenumber.setValue(this.item.getSweatbandStyleNumber());
    this.sweatbandcolor.setValue(this.item.getSweatbandColor());
    this.sweatbandstripecolor.setValue(this.item.getSweatbandStripeColor());
    this.profile.setValue(this.item.getProfile());
    this.frontpanelfabric.setValue(this.item.getFrontPanelFabric());
    this.sidebackpanelfabric.setValue(this.item.getSideBackPanelFabric());
    this.backpanelfabric.setValue(this.item.getBackPanelFabric());
    this.sidepanelfabric.setValue(this.item.getSidePanelFabric());
    
    if ((this.item.getHasPrivateLabel() != null) && (this.item.getHasPrivateLabel().booleanValue())) {
      this.privatelabelradio.setValue(Boolean.valueOf(true));
    } else if ((this.item.getHasPrivateLabel() != null) && (!this.item.getHasPrivateLabel().booleanValue())) {
      this.standardlabelradio.setValue(Boolean.valueOf(true));
    }
    

    if ((this.item.getHasPrivatePackaging() != null) && (this.item.getHasPrivatePackaging().booleanValue())) {
      this.privatepackagingradio.setValue(Boolean.valueOf(true));
    } else if ((this.item.getHasPrivatePackaging() != null) && (!this.item.getHasPrivatePackaging().booleanValue())) {
      this.standardpackagingradio.setValue(Boolean.valueOf(true));
    }
    
    if ((this.item.getHasPrivateShippingMark() != null) && (this.item.getHasPrivateShippingMark().booleanValue())) {
      this.privateshippingmarkradio.setValue(Boolean.valueOf(true));
    } else if ((this.item.getHasPrivateShippingMark() != null) && (!this.item.getHasPrivateShippingMark().booleanValue())) {
      this.standardshippingmarkradio.setValue(Boolean.valueOf(true));
    }
    
    this.isamplefield.setValue(this.item.getPreviewFilename());
    
    this.personalization.setValue(Boolean.valueOf((this.item.getPersonalizationChanges() != null) && (this.item.getPersonalizationChanges().intValue() > 0)));
    this.personaliztionchanges.setVisible((this.item.getPersonalizationChanges() != null) && (this.item.getPersonalizationChanges().intValue() > 0));
    
    this.emailproof.setValue(Boolean.valueOf(this.item.getProductSampleEmail()));
    this.shipproof.setValue(Boolean.valueOf(this.item.getProductSampleShip()));
    this.proofstodo.setValue(this.item.getProductSampleToDo());
    
    this.uploadtype.setValue("Product Preview Image");
    
    this.artworktype.setStore(NewWorkFlow.get().getDataStores().getArtworkTypeStore());
    
    this.sampleapprovedlist.setStore(NewWorkFlow.get().getDataStores().getSampleApprovedListStore());
    
    setStyleStoreFields(true);
  }
  

  private void setLabels()
  {
    this.productcategory.setFieldLabel("Category");
    this.productstylenumber.setFieldLabel("Style Number");
    this.vendornumber.setFieldLabel("Vendor Number");
    this.profile.setFieldLabel("Profile");
    this.numberofpanels.setFieldLabel("Number Of Panels");
    this.visorstitchcolor.setFieldLabel("Visor Stitch Color");
    this.panelstitchcolor.setFieldLabel("Panel Stitch Color");
    this.crownconstruction.setFieldLabel("Crown Construction");
    this.frontpanelfabric.setFieldLabel("Front Panel Fabric");
    this.sidebackpanelfabric.setFieldLabel("Side/Back Panel Fabric");
    this.backpanelfabric.setFieldLabel("Back Panel Fabric");
    this.sidepanelfabric.setFieldLabel("Side Panel Fabric");
    this.productcolor.setFieldLabel("Color");
    this.productsize.setFieldLabel("Size");
    
    this.quantity.setFieldLabel("Quantity");
    this.quantity.setAllowDecimals(false);
    this.quantity.setAllowNegative(false);
    this.quantity.setPropertyEditorType(Integer.class);
    
    if (this.orderdata.getOrderType() == OrderData.OrderType.DOMESTIC) {
      this.fobdozenprice.setFieldLabel("Vendor Piece Price");
      this.fobdozenmarkupprice.setFieldLabel("Customer Piece Price");
    } else {
      this.fobdozenprice.setFieldLabel("FOB Dozen Vendor Price");
      this.fobdozenmarkupprice.setFieldLabel("FOB Dozen Customer Price");
    }
    this.fobdozenprice.setAllowNegative(false);
    this.fobdozenprice.setCurrentFormat("$#,##0.00##");
    this.fobdozenmarkupprice.setAllowNegative(false);
    this.fobdozenmarkupprice.setCurrentFormat("$#,##0.00##");
    
    this.customstylename.setFieldLabel("Custom Style Name");
    
    this.frontpanelcolor.setFieldLabel("Front Panel Color");
    this.sidebackpanelcolor.setFieldLabel("Back & Side Panel Color");
    this.backpanelcolor.setFieldLabel("Back Panel Color");
    this.sidepanelcolor.setFieldLabel("Side Panel Color");
    this.visorstylenumber.setFieldLabel("Visor Style Number");
    
    this.visorrowstitching.setFieldLabel("Visor Row Stitching");
    this.primaryvisorcolor.setFieldLabel("Primary Visor Color");
    this.visortrimedgecolor.setFieldLabel("Visor Trim/Edge Color");
    this.visorsandwichcolor.setFieldLabel("Visor Sandwich Color");
    this.distressedvisorinsidecolor.setFieldLabel("Distressed Visor Inside Color");
    this.undervisorcolor.setFieldLabel("Undervisor Color");
    this.closurestylenumber.setFieldLabel("Closure Style Number");
    
    this.closurestrapcolor.setFieldLabel("Closure Strap Color");
    this.eyeletstylenumber.setFieldLabel("Eyelet Style Number");
    
    this.fronteyeletcolor.setFieldLabel("Front Eyelet Color");
    this.sidebackeyeletcolor.setFieldLabel("Back & Side Eyelet Color");
    this.backeyeletcolor.setFieldLabel("Back Eyelet Color");
    this.sideeyeletcolor.setFieldLabel("Side Eyelet Color");
    this.buttoncolor.setFieldLabel("Button Color");
    this.innertapingcolor.setFieldLabel("Inner Taping Color");
    this.sweatbandstylenumber.setFieldLabel("Sweatband Style Number");
    
    this.sweatbandcolor.setFieldLabel("Sweatband Color");
    this.sweatbandstripecolor.setFieldLabel("Stripe Color");
    
    this.artworktype.setFieldLabel("Artwork Type");
    this.artworktypecomments.setFieldLabel("Change Comments");
    
    this.polybag.setBoxLabel("Polybag & Fold");
    this.oakleaves.setBoxLabel("Oak Levaes Emblems");
    this.tagging.setBoxLabel("Tagging");
    this.stickers.setBoxLabel("Stickers");
    this.sewingpatches.setBoxLabel("Sewing Patches");
    this.heatpresspatches.setBoxLabel("Heat Press Patches");
    this.removeinnerprintedlabel.setBoxLabel("Remove Inner Printed Label");
    this.addinnerprintedlabel.setBoxLabel("Add Inner Printer Label");
    this.removeinnerwovenlabel.setBoxLabel("Remove Inner Woven Label");
    this.addinnerwovenlabel.setBoxLabel("Add Woven Label");
    this.personalization.setBoxLabel("Personalization");
    this.personaliztionchanges.setFieldLabel("# of Personalization");
    this.personaliztionchanges.setAllowDecimals(false);
    this.personaliztionchanges.setAllowNegative(false);
    this.personaliztionchanges.setPropertyEditorType(Integer.class);
    
    this.itemspecialnotes.setFieldLabel("Item Special Notes");
    
    this.emailproof.setBoxLabel("E-Mail Proof");
    this.shipproof.setBoxLabel("Ship Proof");
    this.proofstodo.setFieldLabel("Proofs To Do");
    this.proofstotaldone.setFieldLabel("Proofs Total Done");
    this.proofstotalemail.setFieldLabel("Proofs Total E-mail");
    this.proofstotalship.setFieldLabel("Proofs Total Ship");
    this.productdiscount.setFieldLabel("Discount");
    this.proofstodo.setAllowDecimals(false);
    this.proofstodo.setAllowNegative(false);
    this.proofstodo.setPropertyEditorType(Integer.class);
    this.proofstotaldone.setAllowDecimals(false);
    this.proofstotaldone.setAllowNegative(false);
    this.proofstotaldone.setPropertyEditorType(Integer.class);
    this.proofstotalemail.setAllowDecimals(false);
    this.proofstotalemail.setAllowNegative(false);
    this.proofstotalemail.setPropertyEditorType(Integer.class);
    this.proofstotalship.setAllowDecimals(false);
    this.proofstotalship.setAllowNegative(false);
    this.proofstotalship.setPropertyEditorType(Integer.class);
    
    this.isamplefield.setFieldLabel("Product Preview Filename");
    this.isampleimagefile.setFieldLabel("Product Preview Uploader");
    this.isampleimagefile.setButtonIcon(Resources.ICONS.folder_horizontal_open());
    this.isampleimagefile.setName("isampleimagefile");
    this.isampleordertype.setName("isampleordertype");
    this.isamplehiddenkey.setName("isamplehiddenkey");
    this.uploadtype.setName("uploadtype");
    
    if (this.orderdata.getOrderType() == OrderData.OrderType.DOMESTIC) {
      RpcProxy<BasePagingLoadResult<OtherComboBoxModelData>> categoryproxy = new RpcProxy()
      {
        protected void load(Object loadConfig, AsyncCallback<BasePagingLoadResult<OtherComboBoxModelData>> callback)
        {
          NewWorkFlow.get().getWorkFlowRPC().getCategoryList((PagingLoadConfig)loadConfig, OrderData.OrderType.DOMESTIC, callback);
        }
        
      };
      BasePagingLoader<BasePagingLoadResult<OtherComboBoxModelData>> categoryloader = new BasePagingLoader(categoryproxy);
      ListStore<OtherComboBoxModelData> categorystore = new ListStore(categoryloader);
      this.productcategory.setStore(categorystore);
      
      RpcProxy<BasePagingLoadResult<OtherComboBoxModelData>> productproxy = new RpcProxy()
      {
        protected void load(Object loadConfig, AsyncCallback<BasePagingLoadResult<OtherComboBoxModelData>> callback)
        {
          NewWorkFlow.get().getWorkFlowRPC().getProductsList((PagingLoadConfig)loadConfig, OrderData.OrderType.DOMESTIC, callback);
        }
        
      };
      BasePagingLoader<BasePagingLoadResult<OtherComboBoxModelData>> productloader = new BasePagingLoader(productproxy);
      productloader.addListener(Loader.BeforeLoad, new Listener() {
        public void handleEvent(LoadEvent be) {
          ((BasePagingLoadConfig)be.getConfig()).set("category", ItemPortletField.this.productcategory.getValue() == null ? "" : ItemPortletField.this.productcategory.getValue().getValue());
        }
        
      });
      ListStore<OtherComboBoxModelData> productstore = new ListStore(productloader);
      this.productstylenumber.setStore(productstore);

    }
    else
    {
      RpcProxy<BasePagingLoadResult<OtherComboBoxModelData>> categoryproxy = new RpcProxy()
      {
        protected void load(Object loadConfig, AsyncCallback<BasePagingLoadResult<OtherComboBoxModelData>> callback)
        {
          NewWorkFlow.get().getWorkFlowRPC().getCategoryList((PagingLoadConfig)loadConfig, OrderData.OrderType.OVERSEAS, callback);
        }
        
      };
      BasePagingLoader<BasePagingLoadResult<OtherComboBoxModelData>> categoryloader = new BasePagingLoader(categoryproxy);
      ListStore<OtherComboBoxModelData> categorystore = new ListStore(categoryloader);
      this.productcategory.setStore(categorystore);
      
      RpcProxy<BasePagingLoadResult<OtherComboBoxModelData>> productproxy = new RpcProxy()
      {
        protected void load(Object loadConfig, AsyncCallback<BasePagingLoadResult<OtherComboBoxModelData>> callback)
        {
          NewWorkFlow.get().getWorkFlowRPC().getProductsList((PagingLoadConfig)loadConfig, OrderData.OrderType.OVERSEAS, callback);
        }
        
      };
      BasePagingLoader<BasePagingLoadResult<OtherComboBoxModelData>> productloader = new BasePagingLoader(productproxy);
      productloader.addListener(Loader.BeforeLoad, new Listener() {
        public void handleEvent(LoadEvent be) {
          ((BasePagingLoadConfig)be.getConfig()).set("category", ItemPortletField.this.productcategory.getValue() == null ? "" : ItemPortletField.this.productcategory.getValue().getValue());
        }
        
      });
      ListStore<OtherComboBoxModelData> productstore = new ListStore(productloader);
      this.productstylenumber.setStore(productstore);
    }
    


    this.productcategory.setMinChars(0);
    this.productcategory.setForceSelection(true);
    
    this.productstylenumber.setForceSelection(true);
    this.productstylenumber.setAllowBlank(false);
    this.productstylenumber.setTemplate(getProductListTemplet());
    this.productstylenumber.setItemSelector("div.search-item");
    this.productstylenumber.setMinListWidth(763);
    this.productstylenumber.setMinChars(0);
    this.productstylenumber.setPageSize(25);
    
    this.vendornumber.setForceSelection(true);
    this.profile.setForceSelection(true);
    
    this.eyeletstylenumber.setMinListWidth(418);
    
    this.sampleapprovedlist.setFieldLabel("Purchase Order Sample Approval");
    this.sampleapprovedlist.setEmptyText("Choose One:");
    this.sampleapprovedlist.setForceSelection(true);
    this.closurestylenumber.setMinListWidth(418);
    
    this.frontpanelfabric.setMinListWidth(418);
    this.sidebackpanelfabric.setMinListWidth(418);
    this.backpanelfabric.setMinListWidth(418);
    this.sidepanelfabric.setMinListWidth(418);
  }
  






  private SelectionListener<ButtonEvent> buttonselectionlistener = new SelectionListener()
  {
    public void componentSelected(ButtonEvent ce)
    {
      if (ce.getSource() == ItemPortletField.this.uploadsubmit) {
        if (ItemPortletField.this.orderdata.getHiddenKey() == null) {
          NewWorkFlow.get().getWorkFlowRPC().saveOrder(ItemPortletField.this.orderdata, ItemPortletField.this.isamplesavecallback);
        } else {
          ItemPortletField.this.doUploadISampleImage();
        }
      }
    }
  };
  


  private BuzzAsyncCallback<Integer> isamplesavecallback = new BuzzAsyncCallback()
  {
    public void onFailure(Throwable caught)
    {
      NewWorkFlow.get().reLogin2(caught, ItemPortletField.this.isamplesavecallback);
    }
    
    public void doRetry()
    {
      NewWorkFlow.get().getWorkFlowRPC().saveOrder(ItemPortletField.this.orderdata, ItemPortletField.this.isamplesavecallback);
    }
    
    public void onSuccess(Integer result)
    {
      ItemPortletField.this.orderdata.setHiddenKey(result);
      ItemPortletField.this.doTabLabelChangeEvent();
      ItemPortletField.this.doUploadISampleImage();
    }
  };
  
  private native String getProductListTemplet();
  
  private void doUploadISampleImage() { this.isamplehiddenkey.setValue(this.orderdata.getHiddenKey());
    
    this.uploadsubmit.disable();
    this.myuploadform.submit();
  }
  
  public void addListener(EventType eventType, Listener<? extends BaseEvent> listener) {
    this.observable.addListener(eventType, listener);
  }
  
  private void doTabLabelChangeEvent() {
    BaseEvent be = new BaseEvent(BuzzEvents.TabLabelChange);
    this.observable.fireEvent(BuzzEvents.TabLabelChange, be);
  }
  
  private void doSetVendorVisibleEvent()
  {
    BaseEvent be = new BaseEvent(BuzzEvents.SetVendorVisible);
    this.observable.fireEvent(BuzzEvents.SetVendorVisible, be);
  }
  
  private void doAddLogoEvent(OrderDataLogo thenewlogo)
  {
    BaseEvent be = new BaseEvent(BuzzEvents.AddLogo);
    be.setSource(thenewlogo);
    this.observable.fireEvent(BuzzEvents.AddLogo, be);
  }
  
  private void doStyleChangedEvent()
  {
    BaseEvent be = new BaseEvent(BuzzEvents.StyleChanged);
    this.observable.fireEvent(BuzzEvents.StyleChanged, be);
  }
  
  public OrderDataItem getItem()
  {
    return this.item;
  }
  
  public NewStyleStore getCurrentStyle() {
    if (this.orderdata.getOrderType() == OrderData.OrderType.DOMESTIC) {
      NewStyleStore stylestore = (this.item.getStyleNumber() == null) || (this.item.getStyleNumber().equals("")) ? new NewStyleStore() : (NewStyleStore)NewWorkFlow.get().getDataStores().getDomesticStyleStore().get(this.item.getStyleNumber());
      if (stylestore == null) {
        stylestore = new NewStyleStore();
      }
      return stylestore;
    }
    if ((this.item.getStyleNumber() == null) || (this.item.getStyleNumber().equals(""))) {
      return new NewStyleStore();
    }
    
    if ((this.item.getProfile() != null) && (!this.item.getProfile().equals(""))) {
      if (((NewStyleStore)NewWorkFlow.get().getDataStores().getOverseasStyleStore().get(this.item.getStyleNumber())).getAllProfile(this.item.getProfile()) == null) {
        return (NewStyleStore)NewWorkFlow.get().getDataStores().getOverseasStyleStore().get(this.item.getStyleNumber());
      }
      return ((NewStyleStore)NewWorkFlow.get().getDataStores().getOverseasStyleStore().get(this.item.getStyleNumber())).getAllProfile(this.item.getProfile());
    }
    
    if ((this.item.getVendorNumber() != null) && (!this.item.getVendorNumber().equals("")) && (!this.item.getVendorNumber().equals("Default")) && (NewWorkFlow.get().getDataStores().getOverseasStyleStore().get(this.item.getStyleNumber()) != null)) {
      if (((NewStyleStore)NewWorkFlow.get().getDataStores().getOverseasStyleStore().get(this.item.getStyleNumber())).getAllVendors(this.item.getVendorNumber()) == null) {
        return (NewStyleStore)NewWorkFlow.get().getDataStores().getOverseasStyleStore().get(this.item.getStyleNumber());
      }
      return ((NewStyleStore)NewWorkFlow.get().getDataStores().getOverseasStyleStore().get(this.item.getStyleNumber())).getAllVendors(this.item.getVendorNumber());
    }
    
    NewStyleStore stylestore = (NewStyleStore)NewWorkFlow.get().getDataStores().getOverseasStyleStore().get(this.item.getStyleNumber());
    if (stylestore == null) {
      stylestore = new NewStyleStore();
    }
    return stylestore;
  }
  



  public OtherComboBox getProductCategoryField()
  {
    return this.productcategory;
  }
  
  public OtherComboBox getProductStyleNumberField() {
    return this.productstylenumber;
  }
  
  public OtherComboBox getVendorNumberField() {
    return this.vendornumber;
  }
  
  public OtherComboBox getProfileField() {
    return this.profile;
  }
  
  public OtherComboBox getNumberOfPanelsField() {
    return this.numberofpanels;
  }
  
  public OtherComboBox getVisorStitchColorField() {
    return this.visorstitchcolor;
  }
  
  public OtherComboBox getPanelStitchColorField() {
    return this.panelstitchcolor;
  }
  
  public OtherComboBox getCrownConstructionField() {
    return this.crownconstruction;
  }
  
  public OtherComboBox getFrontPanelFabricField() {
    return this.frontpanelfabric;
  }
  
  public OtherComboBox getSideBackPanelFabricField() {
    return this.sidebackpanelfabric;
  }
  
  public OtherComboBox getBackPanelFabricField() {
    return this.backpanelfabric;
  }
  
  public OtherComboBox getSidePanelFabricField() {
    return this.sidepanelfabric;
  }
  
  public OtherComboBox getProductSizeField() {
    return this.productsize;
  }
  
  public OtherComboBox getProductColorField() {
    return this.productcolor;
  }
  
  public NumberField getQuantityField() {
    return this.quantity;
  }
  
  public NumberField getFobDozenPriceField() {
    return this.fobdozenprice;
  }
  
  public NumberField getFobDozenMarkupPriceField() {
    return this.fobdozenmarkupprice;
  }
  
  public TextField<String> getCustomStyleNameField() {
    return this.customstylename;
  }
  
  public OtherComboBox getFrontPanelColorField() {
    return this.frontpanelcolor;
  }
  
  public OtherComboBox getSideBackPanelColorField() {
    return this.sidebackpanelcolor;
  }
  
  public OtherComboBox getBackPanelColorField() {
    return this.backpanelcolor;
  }
  
  public OtherComboBox getSidePanelColorField() {
    return this.sidepanelcolor;
  }
  
  public OtherComboBox getButtonColorField() {
    return this.buttoncolor;
  }
  
  public OtherComboBox getInnerTapingColorField() {
    return this.innertapingcolor;
  }
  
  public OtherComboBox getVisorStyleNumberField() {
    return this.visorstylenumber;
  }
  
  public OtherComboBox getVisorRowStitchingField() {
    return this.visorrowstitching;
  }
  
  public OtherComboBox getPrimaryVisorColorField() {
    return this.primaryvisorcolor;
  }
  
  public OtherComboBox getVisorTrimEdgeColorField() {
    return this.visortrimedgecolor;
  }
  
  public OtherComboBox getVisorSandwichColorField() {
    return this.visorsandwichcolor;
  }
  
  public OtherComboBox getDistressedVisorInsideColorField() {
    return this.distressedvisorinsidecolor;
  }
  
  public OtherComboBox getUndervisorColorField() {
    return this.undervisorcolor;
  }
  
  public OtherComboBox getClosureStyleNumberField() {
    return this.closurestylenumber;
  }
  
  public OtherComboBox getClosureStrapColorField() {
    return this.closurestrapcolor;
  }
  
  public OtherComboBox getEyeletStyleNumberField() {
    return this.eyeletstylenumber;
  }
  
  public OtherComboBox getFrontEyeletColorField() {
    return this.fronteyeletcolor;
  }
  
  public OtherComboBox getSideBackEyeletColorField() {
    return this.sidebackeyeletcolor;
  }
  
  public OtherComboBox getBackEyeletColorField() {
    return this.backeyeletcolor;
  }
  
  public OtherComboBox getSideEyeletColorField() {
    return this.sideeyeletcolor;
  }
  
  public OtherComboBox getSweatbandStyleNumberField() {
    return this.sweatbandstylenumber;
  }
  
  public OtherComboBox getSweatbandColorField() {
    return this.sweatbandcolor;
  }
  
  public OtherComboBox getSweatbandStripeColorField() {
    return this.sweatbandstripecolor;
  }
  
  public RadioGroup getPrivateOrStandardLabelGroupField() {
    return this.privateorstandardlabelgroup;
  }
  
  public RadioGroup getPrivateOrStandardPackagingGroupField() {
    return this.privateorstandardpackaginggroup;
  }
  
  public RadioGroup getPrivateOrStandardShippingMarkGroupField() {
    return this.privateorstandardshippingmarkgroup;
  }
  
  public CheckBox getPolybagField() {
    return this.polybag;
  }
  
  public CheckBox getPersonalizationField() {
    return this.personalization;
  }
  
  public NumberField getPersonaliztionChangesField() {
    return this.personaliztionchanges;
  }
  
  public CheckBox getSewingPatchesField() {
    return this.sewingpatches;
  }
  
  public CheckBox getHeatPressPatchesField() {
    return this.heatpresspatches;
  }
  
  public CheckBox getOakLeavesField() {
    return this.oakleaves;
  }
  
  public CheckBox getTaggingField() {
    return this.tagging;
  }
  
  public CheckBox getStickersField() {
    return this.stickers;
  }
  
  public CheckBox getAddInnerPrintedLabelField() {
    return this.addinnerprintedlabel;
  }
  
  public CheckBox getRemoveInnerPrintedLabelField() {
    return this.removeinnerprintedlabel;
  }
  
  public CheckBox getAddInnerWovenLabelField() {
    return this.addinnerwovenlabel;
  }
  
  public CheckBox getRemoveInnerWovenLabelField() {
    return this.removeinnerwovenlabel;
  }
  
  public TextArea getItemSpecialNotesField() {
    return this.itemspecialnotes;
  }
  
  public CheckBox getEmailProofField() {
    return this.emailproof;
  }
  
  public CheckBox getShipProofField() {
    return this.shipproof;
  }
  
  public NumberField getProofsToDoField() {
    return this.proofstodo;
  }
  
  public NumberField getProofsTotalDoneField() {
    return this.proofstotaldone;
  }
  
  public NumberField getProofsTotalEmailField() {
    return this.proofstotalemail;
  }
  
  public NumberField getProofsTotalShipField() {
    return this.proofstotalship;
  }
  
  public FormPanel getISampleUploadFormField() {
    return this.myuploadform;
  }
  
  public ImageHelper getProductPreviewImageField() {
    return this.productpreviewimage;
  }
  
  public RadioGroup getAdvanceCustomizationGroupField() {
    return this.advancecustomizationgroup;
  }
  
  public OtherComboBox getArtworkTypeField() {
    return this.artworktype;
  }
  
  public TextField<String> getArtworkTypeCommentsField() {
    return this.artworktypecomments;
  }
  
  public OtherComboBox getSampleApprovedListField() {
    return this.sampleapprovedlist;
  }
  
  public USDMoneyField getProductDiscountField() {
    return this.productdiscount;
  }
}
