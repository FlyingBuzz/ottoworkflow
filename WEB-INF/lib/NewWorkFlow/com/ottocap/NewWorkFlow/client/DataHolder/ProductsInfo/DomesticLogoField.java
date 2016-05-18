package com.ottocap.NewWorkFlow.client.DataHolder.ProductsInfo;

import com.extjs.gxt.ui.client.binding.Bindings;
import com.extjs.gxt.ui.client.binding.FieldBinding;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FormEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.Observable;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.FileUploadField;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.FormPanel.Method;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.ottocap.NewWorkFlow.client.DataHolder.Stores.DataStores;
import com.ottocap.NewWorkFlow.client.Icons.ResourceIcons;
import com.ottocap.NewWorkFlow.client.Icons.Resources;
import com.ottocap.NewWorkFlow.client.NewWorkFlow;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData.OrderType;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogo;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogoColorway;
import com.ottocap.NewWorkFlow.client.Services.WorkFlowServiceAsync;
import com.ottocap.NewWorkFlow.client.StoredUser;
import com.ottocap.NewWorkFlow.client.Widget.BuzzAsyncCallback;
import com.ottocap.NewWorkFlow.client.Widget.BuzzCheckBoxList.BuzzCheckBoxList;
import com.ottocap.NewWorkFlow.client.Widget.BuzzEvents;
import com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBox;
import com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBoxDoubleFieldBinding;
import com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBoxModelData;
import com.ottocap.NewWorkFlow.client.Widget.FilenameField;
import com.ottocap.NewWorkFlow.client.Widget.FormHorizontalPanel2;
import com.ottocap.NewWorkFlow.client.Widget.ImageHelper;
import com.ottocap.NewWorkFlow.client.Widget.StringUtils;
import com.ottocap.NewWorkFlow.client.Widget.USDMoneyField;
import java.util.Date;
import java.util.TreeSet;

public class DomesticLogoField
{
  private FilenameField filename = new FilenameField();
  private CheckBox digitizingrequired = new CheckBox();
  private CheckBox metallicthread = new CheckBox();
  private CheckBox neonthread = new CheckBox();
  private CheckBox tapeedit = new CheckBox();
  
  private CheckBox flashcharge = new CheckBox();
  private CheckBox filmcharge = new CheckBox();
  private CheckBox pmsmatch = new CheckBox();
  private OtherComboBox filmsetupcharge = new OtherComboBox();
  private OtherComboBox logosizechoice = new OtherComboBox();
  private CheckBox specialink = new CheckBox();
  private NumberField colorchangeamount = new NumberField();
  
  private FormPanel myuploadform = new FormPanel();
  private FileUploadField fileupload = new FileUploadField();
  private HiddenField<Integer> filehiddenkey = new HiddenField();
  private HiddenField<String> fileordertype = new HiddenField();
  private HiddenField<String> uploadtype = new HiddenField();
  private Button uploadsubmit = new Button("Submit", Resources.ICONS.drive_upload());
  private OtherComboBox logolocation = new OtherComboBox();
  private NumberField logosizewidth = new NumberField();
  private NumberField logosizeheight = new NumberField();
  private NumberField numberofcolor = new NumberField();
  private NumberField totalstitchcount = new NumberField();
  private TextArea logodescription = new TextArea();
  private BuzzCheckBoxList artworktype = new BuzzCheckBoxList();
  private NumberField artworkcharge = new NumberField();
  private TextField<String> artworktypecomments = new TextField();
  private CheckBox emailproof = new CheckBox();
  private CheckBox shipproof = new CheckBox();
  private NumberField proofstodo = new NumberField();
  private NumberField totalproofs = new NumberField();
  private ImageHelper logoimage = new ImageHelper();
  private ImageHelper dstimage = new ImageHelper();
  private OtherComboBox decorationlist = new OtherComboBox();
  private USDMoneyField customerlogoprice = new USDMoneyField();
  private USDMoneyField vendorlogoprice = new USDMoneyField();
  
  private Observable observable = new com.extjs.gxt.ui.client.event.BaseObservable();
  private OrderDataLogo orderdatalogo;
  private OrderData orderdata;
  private SetField setfield;
  
  public DomesticLogoField(OrderData orderdata, OrderDataLogo orderdatalogo, SetField setfield)
  {
    this.orderdata = orderdata;
    this.orderdatalogo = orderdatalogo;
    this.setfield = setfield;
    
    setLabels();
    setBindings();
    setFields();
    addListeners();
    
    this.myuploadform.setMethod(FormPanel.Method.POST);
    this.myuploadform.setEncoding(com.extjs.gxt.ui.client.widget.form.FormPanel.Encoding.MULTIPART);
    this.myuploadform.setAction(NewWorkFlow.baseurl + "uploader");
    this.myuploadform.setHeaderVisible(false);
    this.myuploadform.setBorders(false);
    this.myuploadform.setBodyBorder(false);
    this.myuploadform.setFrame(false);
    this.myuploadform.setPadding(0);
    
    FormHorizontalPanel2 hor1 = new FormHorizontalPanel2();
    hor1.add(this.logolocation, new FormData(418, 0));
    hor1.add(this.filename);
    hor1.add(this.fileupload, new FormData(230, -1));
    hor1.addButton(this.uploadsubmit);
    hor1.add(this.filehiddenkey);
    hor1.add(this.fileordertype);
    hor1.add(this.uploadtype);
    this.myuploadform.add(hor1);
    
    if ((NewWorkFlow.get().getStoredUser().getAccessLevel() == 1) && (!NewWorkFlow.get().getStoredUser().getUsername().equals(orderdata.getEmployeeId())) && (!orderdata.getEmployeeId().equals(""))) {
      this.fileupload.setEnabled(false);
      this.uploadsubmit.setEnabled(false);
    }
    

    if ((orderdatalogo.getFilename() == null) || (orderdatalogo.getFilename().equals(""))) {
      this.logoimage.setVisible(false);
      this.dstimage.setVisible(false);
    } else if (orderdatalogo.getFilename().toLowerCase().endsWith(".dst")) {
      setDSTImageURL();
      this.logoimage.setVisible(false);
      this.dstimage.setVisible(true);
    } else {
      setImageURL();
      this.logoimage.setVisible(true);
      this.dstimage.setVisible(false);
    }
    
    updatedecorationfields();
  }
  
  public void calcNumberOfColors()
  {
    TreeSet<String> samethread = new TreeSet();
    for (OrderDataLogoColorway thecolors : this.orderdatalogo.getColorways()) {
      samethread.add(thecolors.getThreadType() + thecolors.getLogoColorCode());
    }
    this.numberofcolor.setValue(Integer.valueOf(samethread.size()));
    this.orderdatalogo.setNumberOfColor(Integer.valueOf(samethread.size()));
  }
  
  private void addListeners() {
    this.logolocation.addListener(Events.Change, this.changelistener);
    this.numberofcolor.addListener(Events.Change, this.changelistener);
    this.decorationlist.addListener(Events.Change, this.changelistener);
    
    this.filename.addListener(Events.TriggerClick, this.triggerclicklistener);
    this.uploadsubmit.addSelectionListener(this.selectionlistener);
    this.myuploadform.addListener(Events.Submit, this.submitlistener);
    
    this.artworktype.addListener(Events.OnClick, this.clicklistener);
  }
  
  private void setBindings() {
    Bindings allbindings = new Bindings();
    allbindings.bind(this.orderdatalogo);
    allbindings.addFieldBinding(new FieldBinding(this.logosizewidth, "logosizewidth"));
    allbindings.addFieldBinding(new FieldBinding(this.logosizeheight, "logosizeheight"));
    allbindings.addFieldBinding(new FieldBinding(this.totalstitchcount, "stitchcount"));
    allbindings.addFieldBinding(new FieldBinding(this.logodescription, "colordescription"));
    allbindings.addFieldBinding(new FieldBinding(this.customerlogoprice, "customerlogoprice"));
    allbindings.addFieldBinding(new FieldBinding(this.vendorlogoprice, "vendorlogoprice"));
    
    allbindings.addFieldBinding(new FieldBinding(this.artworkcharge, "artworkchargehour"));
    allbindings.addFieldBinding(new FieldBinding(this.artworktypecomments, "artworktypecomments"));
    allbindings.addFieldBinding(new FieldBinding(this.emailproof, "swatchemail"));
    allbindings.addFieldBinding(new FieldBinding(this.shipproof, "swatchship"));
    allbindings.addFieldBinding(new FieldBinding(this.proofstodo, "swatchtodo"));
    allbindings.addFieldBinding(new FieldBinding(this.totalproofs, "swatchtotaldone"));
    allbindings.addFieldBinding(new FieldBinding(this.digitizingrequired, "digitizing"));
    allbindings.addFieldBinding(new FieldBinding(this.metallicthread, "metallicthread"));
    allbindings.addFieldBinding(new FieldBinding(this.neonthread, "neonthread"));
    allbindings.addFieldBinding(new FieldBinding(this.tapeedit, "tapeedit"));
    allbindings.addFieldBinding(new FieldBinding(this.flashcharge, "flashcharge"));
    allbindings.addFieldBinding(new FieldBinding(this.filmcharge, "filmcharge"));
    allbindings.addFieldBinding(new FieldBinding(this.pmsmatch, "pmsmatch"));
    allbindings.addFieldBinding(new FieldBinding(this.specialink, "specialink"));
    allbindings.addFieldBinding(new OtherComboBoxDoubleFieldBinding(this.filmsetupcharge, "filmsetupcharge"));
    allbindings.addFieldBinding(new com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBoxFieldBinding(this.logosizechoice, "logosizechoice"));
    allbindings.addFieldBinding(new FieldBinding(this.colorchangeamount, "colorchangeamount"));
  }
  
  private void setFields() {
    this.filename.setValue(this.orderdatalogo.getFilename());
    
    ListStore<OtherComboBoxModelData> filmsetupstore = new ListStore();
    filmsetupstore.add(new OtherComboBoxModelData("$0.00", "0.0"));
    filmsetupstore.add(new OtherComboBoxModelData("$10.00", "10.0"));
    filmsetupstore.add(new OtherComboBoxModelData("$25.00", "25.0"));
    filmsetupstore.add(new OtherComboBoxModelData("$35.00", "35.0"));
    this.filmsetupcharge.setStore(filmsetupstore);
    this.filmsetupcharge.setForceSelection(true);
    
    ListStore<OtherComboBoxModelData> logosizechoicestore = new ListStore();
    logosizechoicestore.add(new OtherComboBoxModelData("Small", "Small"));
    logosizechoicestore.add(new OtherComboBoxModelData("Large", "Large"));
    this.logosizechoice.setStore(logosizechoicestore);
    this.logosizechoice.setForceSelection(true);
    
    if (this.orderdata.getOrderType() == OrderData.OrderType.DOMESTIC) {
      this.fileordertype.setValue("Domestic");
    } else {
      this.fileordertype.setValue("Overseas");
    }
    this.uploadtype.setValue("Logo Upload");
    
    this.logolocation.setStore(this.setfield.getLogoLocations());
    this.decorationlist.setStore(this.setfield.getDecorationList(this.orderdatalogo.getLogoLocation()));
    this.artworktype.setStore(NewWorkFlow.get().getDataStores().getArtworkTypeStore());
    
    String[] artowkrtypechoices = this.orderdatalogo.getArtworkType().split("\n");
    String[] arrayOfString1; int j = (arrayOfString1 = artowkrtypechoices).length; for (int i = 0; i < j; i++) { String artworktypechoice = arrayOfString1[i];
      this.artworktype.setChecked((OtherComboBoxModelData)this.artworktype.getStore().findModel("name", artworktypechoice), true);
    }
    
    this.logolocation.setValue(this.orderdatalogo.getLogoLocation());
    this.decorationlist.setValue(this.orderdatalogo.getDecoration());
    
    if ((this.orderdatalogo.getDecoration() != null) && (this.orderdatalogo.getDecoration().equals("Heat Transfer"))) {
      this.flashcharge.setBoxLabel("Color Garment Charge");
    } else {
      this.flashcharge.setBoxLabel("Flash Charge");
    }
    
    this.logosizewidth.setAllowNegative(false);
    this.logosizeheight.setAllowNegative(false);
    this.numberofcolor.setAllowNegative(false);
    this.numberofcolor.setAllowDecimals(false);
    this.numberofcolor.setPropertyEditorType(Integer.class);
    this.numberofcolor.setValue(this.orderdatalogo.getNumberOfColor());
    this.numberofcolor.setReadOnly((this.orderdatalogo.getFilename() != null) && (this.orderdatalogo.getFilename().toLowerCase().endsWith(".dst")));
    
    this.totalstitchcount.setAllowNegative(false);
    this.totalstitchcount.setAllowDecimals(false);
    this.totalstitchcount.setPropertyEditorType(Integer.class);
    
    this.customerlogoprice.setAllowNegative(false);
    this.customerlogoprice.setCurrentFormat("$#,##0.00##");
    this.vendorlogoprice.setAllowNegative(false);
    this.vendorlogoprice.setCurrentFormat("$#,##0.00##");
    
    this.artworkcharge.setAllowNegative(false);
    this.proofstodo.setAllowNegative(false);
    this.proofstodo.setAllowDecimals(false);
    this.proofstodo.setPropertyEditorType(Integer.class);
    this.totalproofs.setAllowNegative(false);
    this.totalproofs.setAllowDecimals(false);
    this.totalproofs.setPropertyEditorType(Integer.class);
    
    this.logolocation.setTemplate(getLogoLocationTemplate());
    this.logolocation.setItemSelector("div.search-item");
    
    this.decorationlist.setTemplate(getDecorationListTemplate());
    this.decorationlist.setItemSelector("div.search-item");
  }
  



  private native String getDecorationListTemplate();
  



  private native String getLogoLocationTemplate();
  


  private void setLabels()
  {
    this.filename.setFieldLabel("Filename");
    this.digitizingrequired.setBoxLabel("Digitizing Required");
    this.metallicthread.setBoxLabel("Metallic Thread");
    this.neonthread.setBoxLabel("Neon Thread");
    this.tapeedit.setBoxLabel("Tape Edit");
    
    this.filmcharge.setBoxLabel("Film Charge");
    this.pmsmatch.setBoxLabel("PMS Match");
    this.filmsetupcharge.setFieldLabel("Screen Setup Charge");
    this.logosizechoice.setFieldLabel("Logo Size Choice");
    this.specialink.setBoxLabel("Special Ink");
    this.colorchangeamount.setFieldLabel("Color Change Amount");
    this.colorchangeamount.setAllowDecimals(false);
    this.colorchangeamount.setAllowNegative(false);
    this.colorchangeamount.setPropertyEditorType(Integer.class);
    
    this.fileupload.setFieldLabel("Upload Artwork / DST File");
    this.fileupload.setName("fileimagefile");
    this.fileupload.setButtonIcon(Resources.ICONS.folder_horizontal_open());
    
    this.filehiddenkey.setName("filehiddenkey");
    this.fileordertype.setName("fileordertype");
    this.uploadtype.setName("uploadtype");
    this.logolocation.setFieldLabel("Logo Location");
    this.logosizewidth.setFieldLabel("Logo Size (Width)");
    this.logosizeheight.setFieldLabel("Logo Size (Height)");
    this.numberofcolor.setFieldLabel("# of Colors");
    this.totalstitchcount.setFieldLabel("Total Stitch Count");
    this.logodescription.setFieldLabel("Logo Description");
    this.customerlogoprice.setFieldLabel("Customer Logo Price");
    this.vendorlogoprice.setFieldLabel("Vendor Logo Price");
    
    this.artworkcharge.setFieldLabel("Artwork Charge (Hours)");
    this.artworktypecomments.setFieldLabel("Change Comments");
    this.emailproof.setBoxLabel("E-Mail Proof");
    this.shipproof.setBoxLabel("Ship Proof");
    this.proofstodo.setFieldLabel("Proofs To Do");
    this.totalproofs.setFieldLabel("Total Proofs");
    this.decorationlist.setFieldLabel("Decoration Type");
  }
  

  private SelectionListener<ButtonEvent> selectionlistener = new SelectionListener()
  {
    public void componentSelected(ButtonEvent ce)
    {
      if (ce.getSource().equals(DomesticLogoField.this.uploadsubmit)) {
        if (DomesticLogoField.this.orderdata.getHiddenKey() == null) {
          NewWorkFlow.get().getWorkFlowRPC().saveOrder(DomesticLogoField.this.orderdata, DomesticLogoField.this.logosavecallback);
        } else {
          DomesticLogoField.this.doUploadLogo();
        }
      }
    }
  };
  

  private BuzzAsyncCallback<Integer> logosavecallback = new BuzzAsyncCallback()
  {
    public void onFailure(Throwable caught)
    {
      NewWorkFlow.get().reLogin2(caught, DomesticLogoField.this.logosavecallback);
    }
    
    public void doRetry()
    {
      NewWorkFlow.get().getWorkFlowRPC().saveOrder(DomesticLogoField.this.orderdata, DomesticLogoField.this.logosavecallback);
    }
    
    public void onSuccess(Integer result)
    {
      DomesticLogoField.this.orderdata.setHiddenKey(result);
      DomesticLogoField.this.doTabLabelChangeEvent();
      DomesticLogoField.this.doUploadLogo();
    }
  };
  
  private void doUploadLogo()
  {
    this.filehiddenkey.setValue(this.orderdata.getHiddenKey());
    this.uploadsubmit.disable();
    this.myuploadform.submit();
  }
  
  private Listener<BaseEvent> triggerclicklistener = new Listener()
  {
    public void handleEvent(BaseEvent be)
    {
      if (be.getSource().equals(DomesticLogoField.this.filename)) {
        DomesticLogoField.this.orderdatalogo.setFilename("");
        DomesticLogoField.this.orderdatalogo.setDstFilename("");
        DomesticLogoField.this.filename.setValue("");
        DomesticLogoField.this.doUpdateColorwaysEvent();
        DomesticLogoField.this.numberofcolor.setReadOnly(false);
        DomesticLogoField.this.logoimage.setVisible(false);
        DomesticLogoField.this.dstimage.setVisible(false);
      }
    }
  };
  

  private Listener<BaseEvent> clicklistener = new Listener()
  {
    public void handleEvent(BaseEvent be)
    {
      if (be.getSource().equals(DomesticLogoField.this.artworktype))
      {
        String selections = "";
        for (OtherComboBoxModelData currentchecked : DomesticLogoField.this.artworktype.getChecked()) {
          selections = selections + currentchecked.getName() + "\n";
        }
        
        DomesticLogoField.this.orderdatalogo.setArtworkType(selections);
      }
    }
  };
  



  private Listener<BaseEvent> changelistener = new Listener()
  {
    public void handleEvent(BaseEvent be)
    {
      if (be.getSource().equals(DomesticLogoField.this.logolocation)) {
        DomesticLogoField.this.orderdatalogo.setLogoLocation(DomesticLogoField.this.logolocation.getValue() == null ? "" : DomesticLogoField.this.logolocation.getValue().getValue());
        DomesticLogoField.this.updateDecorationList();
        

        if ((DomesticLogoField.this.orderdatalogo.getDecoration().equals("Direct To Garment")) && (DomesticLogoField.this.orderdatalogo.getLogoLocation().startsWith("A")) && (!DomesticLogoField.this.orderdatalogo.getLogoLocation().startsWith("AP"))) {
          DomesticLogoField.this.logosizechoice.setVisible(true);
        } else {
          DomesticLogoField.this.logosizechoice.setVisible(false);
        }
      }
      else if (be.getSource().equals(DomesticLogoField.this.numberofcolor)) {
        int coloramount = DomesticLogoField.this.numberofcolor.getValue() == null ? 0 : ((Number)DomesticLogoField.this.numberofcolor.getValue()).intValue();
        if (coloramount > 20) {
          com.extjs.gxt.ui.client.widget.MessageBox.confirm("Are you sure?", "Are you sure you want to add " + coloramount + " colors?", DomesticLogoField.this.addcolorscallback);
        } else {
          DomesticLogoField.this.orderdatalogo.setNumberOfColor(Integer.valueOf(coloramount));
          DomesticLogoField.this.doUpdateColorwaysEvent();
        }
      } else if (be.getSource().equals(DomesticLogoField.this.decorationlist)) {
        DomesticLogoField.this.orderdatalogo.setDecoration(DomesticLogoField.this.decorationlist.getValue() == null ? "" : DomesticLogoField.this.decorationlist.getValue().getValue());
        DomesticLogoField.this.updatedecorationfields();
        
        if (NewWorkFlow.get().getStoredUser().getAccessLevel() == 0) {
          if (DomesticLogoField.this.orderdatalogo.getDecoration().equals("Heat Transfer")) {
            DomesticLogoField.this.orderdatalogo.setFilmCharge(true);
            DomesticLogoField.this.orderdatalogo.setFilmSetupCharge(Double.valueOf(25.0D));
          } else if (DomesticLogoField.this.orderdatalogo.getDecoration().equals("CAD Print")) {
            DomesticLogoField.this.orderdatalogo.setFilmCharge(true);
            DomesticLogoField.this.orderdatalogo.setFilmSetupCharge(Double.valueOf(25.0D));
          } else if (DomesticLogoField.this.orderdatalogo.getDecoration().equals("Screen Print")) {
            DomesticLogoField.this.orderdatalogo.setFilmCharge(true);
            DomesticLogoField.this.orderdatalogo.setFilmSetupCharge(Double.valueOf(25.0D));
          } else if (DomesticLogoField.this.orderdatalogo.getDecoration().equals("Four Color Screen Print")) {
            DomesticLogoField.this.orderdatalogo.setFilmCharge(true);
            DomesticLogoField.this.orderdatalogo.setFilmSetupCharge(Double.valueOf(25.0D));
          } else {
            DomesticLogoField.this.orderdatalogo.setFilmCharge(false);
            DomesticLogoField.this.orderdatalogo.setFilmSetupCharge(Double.valueOf(0.0D));
          }
        }
      }
    }
  };
  


  private Listener<MessageBoxEvent> addcolorscallback = new Listener()
  {
    public void handleEvent(MessageBoxEvent be)
    {
      if (be.getButtonClicked().getHtml().equals("Yes")) {
        DomesticLogoField.this.orderdatalogo.setNumberOfColor(Integer.valueOf(DomesticLogoField.this.numberofcolor.getValue() == null ? 0 : ((Number)DomesticLogoField.this.numberofcolor.getValue()).intValue()));
        DomesticLogoField.this.doUpdateColorwaysEvent();
      } else {
        DomesticLogoField.this.numberofcolor.setValue(DomesticLogoField.this.orderdatalogo.getNumberOfColor());
      }
    }
  };
  
  private void updatedecorationfields()
  {
    if ((this.orderdatalogo.getDecoration().equals("Flat Embroidery")) || (this.orderdatalogo.getDecoration().equals("3D Embroidery")))
    {














      this.numberofcolor.setVisible(true);
      this.totalstitchcount.setVisible(true);
      this.digitizingrequired.setVisible(true);
      this.metallicthread.setVisible(true);
      this.neonthread.setVisible(true);
      this.tapeedit.setVisible(true);
      
      this.flashcharge.setVisible(false);
      this.filmcharge.setVisible(false);
      this.pmsmatch.setVisible(false);
      this.filmsetupcharge.setVisible(false);
      this.logosizechoice.setVisible(false);
      this.specialink.setVisible(false);
      this.colorchangeamount.setVisible(false);
      this.customerlogoprice.setVisible(false);
      this.vendorlogoprice.setVisible(false);
      
      doUpdateColorwaysEvent();
    } else if (this.orderdatalogo.getDecoration().equals("Heat Transfer"))
    {














      this.numberofcolor.setVisible(true);
      this.totalstitchcount.setVisible(false);
      this.digitizingrequired.setVisible(false);
      this.metallicthread.setVisible(false);
      this.neonthread.setVisible(false);
      this.tapeedit.setVisible(false);
      
      this.flashcharge.setBoxLabel("Color Garment Charge");
      this.flashcharge.setVisible(true);
      this.filmcharge.setVisible(true);
      this.pmsmatch.setVisible(false);
      this.filmsetupcharge.setVisible(true);
      this.logosizechoice.setVisible(false);
      this.specialink.setVisible(true);
      this.colorchangeamount.setVisible(true);
      this.customerlogoprice.setVisible(false);
      this.vendorlogoprice.setVisible(false);
      
      doUpdateColorwaysEvent();
    } else if (this.orderdatalogo.getDecoration().equals("CAD Print"))
    {














      this.numberofcolor.setVisible(true);
      this.totalstitchcount.setVisible(false);
      this.digitizingrequired.setVisible(false);
      this.metallicthread.setVisible(false);
      this.neonthread.setVisible(false);
      this.tapeedit.setVisible(false);
      
      this.flashcharge.setBoxLabel("Color Garment Charge");
      this.flashcharge.setVisible(false);
      this.filmcharge.setVisible(false);
      this.pmsmatch.setVisible(false);
      this.filmsetupcharge.setVisible(false);
      this.logosizechoice.setVisible(false);
      this.specialink.setVisible(false);
      this.colorchangeamount.setVisible(false);
      this.customerlogoprice.setVisible(true);
      this.vendorlogoprice.setVisible(true);
      
      doUpdateColorwaysEvent();
    } else if (this.orderdatalogo.getDecoration().equals("Screen Print"))
    {














      this.numberofcolor.setVisible(true);
      this.totalstitchcount.setVisible(false);
      this.digitizingrequired.setVisible(false);
      this.metallicthread.setVisible(false);
      this.neonthread.setVisible(false);
      this.tapeedit.setVisible(false);
      
      this.flashcharge.setBoxLabel("Flash Charge");
      this.flashcharge.setVisible(true);
      this.filmcharge.setVisible(true);
      this.pmsmatch.setVisible(true);
      this.filmsetupcharge.setVisible(true);
      this.logosizechoice.setVisible(false);
      this.specialink.setVisible(false);
      this.colorchangeamount.setVisible(true);
      this.customerlogoprice.setVisible(true);
      this.vendorlogoprice.setVisible(true);
      
      doUpdateColorwaysEvent();
    } else if (this.orderdatalogo.getDecoration().equals("Direct To Garment"))
    {














      this.numberofcolor.setVisible(false);
      this.totalstitchcount.setVisible(false);
      this.digitizingrequired.setVisible(false);
      this.metallicthread.setVisible(false);
      this.neonthread.setVisible(false);
      this.tapeedit.setVisible(false);
      
      this.flashcharge.setVisible(false);
      this.filmcharge.setVisible(false);
      this.pmsmatch.setVisible(false);
      this.filmsetupcharge.setVisible(false);
      if ((this.orderdatalogo.getLogoLocation().startsWith("A")) && (!this.orderdatalogo.getLogoLocation().startsWith("AP"))) {
        this.logosizechoice.setVisible(true);
      } else {
        this.logosizechoice.setVisible(false);
      }
      this.specialink.setVisible(false);
      this.colorchangeamount.setVisible(false);
      
      this.numberofcolor.setValue(Integer.valueOf(0));
      this.orderdatalogo.setNumberOfColor(Integer.valueOf(0));
      doUpdateColorwaysEvent();
      this.customerlogoprice.setVisible(false);
      this.vendorlogoprice.setVisible(false);
    }
    else if (this.orderdatalogo.getDecoration().equals("Four Color Screen Print"))
    {














      this.numberofcolor.setVisible(false);
      this.totalstitchcount.setVisible(false);
      this.digitizingrequired.setVisible(false);
      this.metallicthread.setVisible(false);
      this.neonthread.setVisible(false);
      this.tapeedit.setVisible(false);
      
      this.flashcharge.setVisible(false);
      this.filmcharge.setVisible(false);
      this.pmsmatch.setVisible(true);
      this.filmsetupcharge.setVisible(false);
      this.logosizechoice.setVisible(false);
      this.specialink.setVisible(false);
      this.colorchangeamount.setVisible(false);
      
      this.numberofcolor.setValue(Integer.valueOf(0));
      this.orderdatalogo.setNumberOfColor(Integer.valueOf(0));
      this.customerlogoprice.setVisible(false);
      this.vendorlogoprice.setVisible(false);
      
      doUpdateColorwaysEvent();







    }
    else
    {






      this.numberofcolor.setVisible(true);
      this.totalstitchcount.setVisible(false);
      this.digitizingrequired.setVisible(false);
      this.metallicthread.setVisible(false);
      this.neonthread.setVisible(false);
      this.tapeedit.setVisible(false);
      
      this.flashcharge.setVisible(false);
      this.filmcharge.setVisible(false);
      this.pmsmatch.setVisible(false);
      this.filmsetupcharge.setVisible(false);
      this.logosizechoice.setVisible(false);
      this.specialink.setVisible(false);
      this.colorchangeamount.setVisible(false);
      this.customerlogoprice.setVisible(true);
      this.vendorlogoprice.setVisible(true);
      
      doUpdateColorwaysEvent();
    }
  }
  
  private Listener<FormEvent> submitlistener = new Listener()
  {
    public void handleEvent(FormEvent be)
    {
      if (be.getComponent().equals(DomesticLogoField.this.myuploadform)) {
        String htmlstring = StringUtils.unescapeHTML(be.getResultHtml(), 0);
        
        htmlstring = htmlstring.replace("<pre>", "");
        htmlstring = htmlstring.replace("</pre>", "");
        DomesticLogoField.this.uploadsubmit.enable();
        
        JSONObject jsonObject = JSONParser.parseStrict(htmlstring).isObject();
        if (!jsonObject.containsKey("error")) {
          String filenamestring = jsonObject.get("Filename").isString().stringValue();
          
          if (((DomesticLogoField.this.orderdatalogo.getFilename() == null) || (DomesticLogoField.this.orderdatalogo.getFilename().equals("")) || (DomesticLogoField.this.orderdatalogo.getFilename().endsWith(".dst"))) && (filenamestring.toLowerCase().endsWith(".dst"))) {
            DomesticLogoField.this.numberofcolor.setReadOnly(true);
            DomesticLogoField.this.orderdatalogo.setDstFilename(filenamestring);
            DomesticLogoField.this.orderdatalogo.setFilename(filenamestring);
            DomesticLogoField.this.filename.setValue(DomesticLogoField.this.orderdatalogo.getFilename());
            DomesticLogoField.this.setDSTInfo(new DSTInfoContainer(jsonObject));
            DomesticLogoField.this.setDSTImageURL();
            DomesticLogoField.this.calcNumberOfColors();
            DomesticLogoField.this.dstimage.setVisible(true);
            DomesticLogoField.this.logoimage.setVisible(false);
          } else if (filenamestring.toLowerCase().endsWith(".dst")) {
            DomesticLogoField.this.setDSTInfo2(new DSTInfoContainer(jsonObject));
          }
          else {
            DomesticLogoField.this.numberofcolor.setReadOnly(false);
            DomesticLogoField.this.orderdatalogo.setDstFilename(filenamestring);
            DomesticLogoField.this.orderdatalogo.setFilename(filenamestring);
            DomesticLogoField.this.filename.setValue(DomesticLogoField.this.orderdatalogo.getFilename());
            DomesticLogoField.this.setImageURL();
            DomesticLogoField.this.logoimage.setVisible(true);
            DomesticLogoField.this.dstimage.setVisible(false);
            DomesticLogoField.this.doUpdateColorwaysEvent();
            if ((DomesticLogoField.this.orderdatalogo.getDecoration() != null) && ((DomesticLogoField.this.orderdatalogo.getDecoration().equals("Flat Embroidery")) || (DomesticLogoField.this.orderdatalogo.getDecoration().equals("3D Embroidery")))) {
              DomesticLogoField.this.orderdatalogo.setDigitizing(true);
            }
          }
          com.extjs.gxt.ui.client.widget.Info.display("Success", "Logo Been Uploaded");
          DomesticLogoField.this.uploadsubmit.enable();
        } else {
          Throwable myerror = new Throwable(jsonObject.get("error").isString().stringValue());
          NewWorkFlow.get().reLogin2(myerror, DomesticLogoField.this.uploadlogorelogin);
        }
      }
    }
  };
  

  BuzzAsyncCallback<String> uploadlogorelogin = new BuzzAsyncCallback()
  {
    public void onFailure(Throwable caught)
    {
      DomesticLogoField.this.uploadsubmit.enable();
    }
    
    public void onSuccess(String result)
    {
      DomesticLogoField.this.myuploadform.submit();
    }
    
    public void doRetry()
    {
      DomesticLogoField.this.myuploadform.submit();
    }
  };
  
  private void setImageURL()
  {
    this.logoimage.setUrl(NewWorkFlow.filepath + "DomesticData/" + this.orderdata.getHiddenKey() + "/logos/" + URL.encodeQueryString(this.orderdatalogo.getFilename()).replace("+", "%20"));
  }
  
  public void setDSTImageURL()
  {
    String submitstring = "&colorways=" + this.orderdatalogo.getColorwayCount();
    submitstring = submitstring + "&filename=" + URL.encodeQueryString(this.orderdatalogo.getDstFilename() != null ? this.orderdatalogo.getDstFilename() : "");
    
    int currentcolorwaycounter = 0;
    for (OrderDataLogoColorway currentcolorway : this.orderdatalogo.getColorways()) {
      submitstring = submitstring + "&logocolorcode" + currentcolorwaycounter + "=" + currentcolorway.getLogoColorCode();
      submitstring = submitstring + "&threadtype" + currentcolorwaycounter + "=" + currentcolorway.getThreadType();
      currentcolorwaycounter++;
    }
    
    submitstring = submitstring + "&hiddenkey=" + this.orderdata.getHiddenKey();
    Date date = new Date();
    this.dstimage.setUrl(NewWorkFlow.baseurl + "dstimage/?ordertype=Domestic&date=" + date.getTime() + submitstring);
  }
  
  private void setDSTInfo(DSTInfoContainer theinfo) {
    this.orderdatalogo.setLogoSizeWidth(Double.valueOf(theinfo.getLogoSizeWidth()));
    this.logosizewidth.setValue(this.orderdatalogo.getLogoSizeWidth());
    this.orderdatalogo.setLogoSizeHeight(Double.valueOf(theinfo.getLogoSizeHeight()));
    this.logosizeheight.setValue(this.orderdatalogo.getLogoSizeHeight());
    this.orderdatalogo.setStitchCount(Integer.valueOf(theinfo.getTotalStitchCount()));
    this.totalstitchcount.setValue(this.orderdatalogo.getStitchCount());
    doSetDSTInfoEvent(theinfo);
  }
  

  private void setDSTInfo2(DSTInfoContainer theinfo)
  {
    this.orderdatalogo.setLogoSizeWidth(Double.valueOf(theinfo.getLogoSizeWidth()));
    this.logosizewidth.setValue(this.orderdatalogo.getLogoSizeWidth());
    this.orderdatalogo.setLogoSizeHeight(Double.valueOf(theinfo.getLogoSizeHeight()));
    this.logosizeheight.setValue(this.orderdatalogo.getLogoSizeHeight());
    this.orderdatalogo.setStitchCount(Integer.valueOf(theinfo.getTotalStitchCount()));
    this.totalstitchcount.setValue(this.orderdatalogo.getStitchCount());
  }
  


  private void doUpdateColorwaysEvent()
  {
    BaseEvent be = new BaseEvent(BuzzEvents.UpdateColorways);
    this.observable.fireEvent(BuzzEvents.UpdateColorways, be);
  }
  
  private void doSetDSTInfoEvent(DSTInfoContainer theinfo)
  {
    BaseEvent be = new BaseEvent(BuzzEvents.SetDSTInfo);
    be.setSource(theinfo);
    this.observable.fireEvent(BuzzEvents.SetDSTInfo, be);
  }
  
  private void doTabLabelChangeEvent()
  {
    BaseEvent be = new BaseEvent(BuzzEvents.TabLabelChange);
    this.observable.fireEvent(BuzzEvents.TabLabelChange, be);
  }
  
  public void updateLocations()
  {
    this.logolocation.changeStore(this.setfield.getLogoLocations(), this.logolocation.getValue() == null ? "" : this.logolocation.getValue().getValue());
    updateDecorationList();
  }
  
  private void updateDecorationList() {
    this.decorationlist.changeStore(this.setfield.getDecorationList(this.orderdatalogo.getLogoLocation()), this.orderdatalogo.getDecoration());
  }
  
  public void addListener(EventType eventType, Listener<? extends BaseEvent> listener) {
    this.observable.addListener(eventType, listener);
  }
  
  public FormPanel getLogoUploadFormField() {
    return this.myuploadform;
  }
  
  public OtherComboBox getDecorationListField() {
    return this.decorationlist;
  }
  
  public NumberField getLogoSizeWidthField() {
    return this.logosizewidth;
  }
  
  public NumberField getLogoSizeHeightField() {
    return this.logosizeheight;
  }
  
  public NumberField getNumberOfColorField() {
    return this.numberofcolor;
  }
  
  public NumberField getTotalStitchCountField() {
    return this.totalstitchcount;
  }
  
  public NumberField getCustomerLogoPriceField() {
    return this.customerlogoprice;
  }
  
  public NumberField getVendorLogoPriceField() {
    return this.vendorlogoprice;
  }
  
  public OtherComboBox getLogoSizeChoiceField() {
    return this.logosizechoice;
  }
  
  public CheckBox getDigitizingRequiredField() {
    return this.digitizingrequired;
  }
  
  public CheckBox getMetallicThreadField() {
    return this.metallicthread;
  }
  
  public CheckBox getNeonThreadField() {
    return this.neonthread;
  }
  
  public CheckBox getTapeEditField() {
    return this.tapeedit;
  }
  
  public CheckBox getFlashChargeField() {
    return this.flashcharge;
  }
  
  public CheckBox getFilmChargeField() {
    return this.filmcharge;
  }
  
  public CheckBox getPMSMatchField() {
    return this.pmsmatch;
  }
  
  public OtherComboBox getFilmSetupChargeField() {
    return this.filmsetupcharge;
  }
  
  public CheckBox getSpecialInkField() {
    return this.specialink;
  }
  
  public NumberField getColorChangeAmountField() {
    return this.colorchangeamount;
  }
  
  public ImageHelper getDSTImageField() {
    return this.dstimage;
  }
  
  public ImageHelper getLogoImageField() {
    return this.logoimage;
  }
  
  public TextArea getLogoDescriptionField() {
    return this.logodescription;
  }
  
  public BuzzCheckBoxList getArtworkTypeField() {
    return this.artworktype;
  }
  
  public NumberField getArtworkChargeField() {
    return this.artworkcharge;
  }
  
  public TextField<String> getArtworkTypeCommentsField() {
    return this.artworktypecomments;
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
  
  public NumberField getTotalProofsField() {
    return this.totalproofs;
  }
  
  public OtherComboBox getLogoLocationField() {
    return this.logolocation;
  }
}
