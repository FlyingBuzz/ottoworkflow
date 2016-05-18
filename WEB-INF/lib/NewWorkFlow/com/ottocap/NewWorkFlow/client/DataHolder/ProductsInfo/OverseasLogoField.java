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
import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.JSONObject;
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
import com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBoxModelData;
import com.ottocap.NewWorkFlow.client.Widget.FilenameField;
import com.ottocap.NewWorkFlow.client.Widget.FormHorizontalPanel2;
import com.ottocap.NewWorkFlow.client.Widget.ImageHelper;
import com.ottocap.NewWorkFlow.client.Widget.StringUtils;
import java.util.Date;
import java.util.TreeSet;

public class OverseasLogoField
{
  private FilenameField artworkfilename = new FilenameField();
  private FilenameField dstfilename = new FilenameField();
  private CheckBox digitizingrequired = new CheckBox();
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
  private TextField<String> namedroplogo = new TextField();
  private TextArea logodescription = new TextArea();
  private BuzzCheckBoxList artworktype = new BuzzCheckBoxList();
  private TextField<String> artworktypecomments = new TextField();
  private NumberField artworkcharge = new NumberField();
  private CheckBox emailproof = new CheckBox();
  private CheckBox shipproof = new CheckBox();
  private NumberField proofstodo = new NumberField();
  private NumberField totalemailproofs = new NumberField();
  private NumberField totalshipproofs = new NumberField();
  private ImageHelper logoimage = new ImageHelper();
  private ImageHelper dstimage = new ImageHelper();
  
  private OrderDataLogo orderdatalogo;
  private OrderData orderdata;
  private SetField setfield;
  private Observable observable = new com.extjs.gxt.ui.client.event.BaseObservable();
  
  public OverseasLogoField(OrderData orderdata, OrderDataLogo orderdatalogo, SetField setfield) {
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
    hor1.add(this.artworkfilename);
    hor1.add(this.dstfilename);
    hor1.add(this.fileupload, new com.extjs.gxt.ui.client.widget.layout.FormData(230, -1));
    hor1.addButton(this.uploadsubmit);
    hor1.addCheckBox(this.digitizingrequired);
    hor1.add(this.filehiddenkey);
    hor1.add(this.fileordertype);
    hor1.add(this.uploadtype);
    this.myuploadform.add(hor1);
    
    if ((NewWorkFlow.get().getStoredUser().getAccessLevel() == 1) && (!NewWorkFlow.get().getStoredUser().getUsername().equals(orderdata.getEmployeeId())) && (!orderdata.getEmployeeId().equals(""))) {
      this.fileupload.setEnabled(false);
      this.uploadsubmit.setEnabled(false);
    }
    

    if (orderdatalogo.getDecorationCount().intValue() == 0) {
      orderdatalogo.addDecoration();
    }
    
    if ((orderdatalogo.getFilename() != null) && (!orderdatalogo.getFilename().equals(""))) {
      setImageURL();
      this.logoimage.setVisible(true);
    } else {
      this.logoimage.setVisible(false);
    }
    
    if ((orderdatalogo.getDstFilename() != null) && (!orderdatalogo.getDstFilename().equals(""))) {
      setDSTImageURL();
      this.dstimage.setVisible(true);
    } else {
      this.dstimage.setVisible(false);
    }
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
    this.artworkfilename.addListener(Events.TriggerClick, this.triggerclicklistener);
    this.dstfilename.addListener(Events.TriggerClick, this.triggerclicklistener);
    this.uploadsubmit.addSelectionListener(this.selectionlistener);
    this.myuploadform.addListener(Events.Submit, this.submitlistener);
    this.logolocation.addListener(Events.Change, this.changelistener);
    this.numberofcolor.addListener(Events.Change, this.changelistener);
    
    this.artworktype.addListener(Events.OnClick, this.clicklistener);
  }
  
  private void setBindings() {
    Bindings allbindings = new Bindings();
    allbindings.bind(this.orderdatalogo);
    allbindings.addFieldBinding(new FieldBinding(this.logosizewidth, "logosizewidth"));
    allbindings.addFieldBinding(new FieldBinding(this.logosizeheight, "logosizeheight"));
    allbindings.addFieldBinding(new FieldBinding(this.totalstitchcount, "stitchcount"));
    allbindings.addFieldBinding(new FieldBinding(this.namedroplogo, "namedroplogo"));
    allbindings.addFieldBinding(new FieldBinding(this.logodescription, "colordescription"));
    
    allbindings.addFieldBinding(new FieldBinding(this.artworkcharge, "artworkchargehour"));
    allbindings.addFieldBinding(new FieldBinding(this.artworktypecomments, "artworktypecomments"));
    allbindings.addFieldBinding(new FieldBinding(this.emailproof, "swatchemail"));
    allbindings.addFieldBinding(new FieldBinding(this.shipproof, "swatchship"));
    allbindings.addFieldBinding(new FieldBinding(this.proofstodo, "swatchtodo"));
    allbindings.addFieldBinding(new FieldBinding(this.totalemailproofs, "swatchtotalemail"));
    allbindings.addFieldBinding(new FieldBinding(this.totalshipproofs, "swatchtotalship"));
    allbindings.addFieldBinding(new FieldBinding(this.digitizingrequired, "digitizing"));
  }
  
  private void setFields() {
    this.artworkfilename.setValue(this.orderdatalogo.getFilename());
    this.dstfilename.setValue(this.orderdatalogo.getDstFilename());
    if (this.orderdata.getOrderType() == OrderData.OrderType.DOMESTIC) {
      this.fileordertype.setValue("Domestic");
    } else {
      this.fileordertype.setValue("Overseas");
    }
    this.uploadtype.setValue("Logo Upload");
    
    this.logolocation.setStore(this.setfield.getLogoLocations());
    this.artworktype.setStore(NewWorkFlow.get().getDataStores().getArtworkTypeStore());
    
    String[] artowkrtypechoices = this.orderdatalogo.getArtworkType().split("\n");
    String[] arrayOfString1; int j = (arrayOfString1 = artowkrtypechoices).length; for (int i = 0; i < j; i++) { String artworktypechoice = arrayOfString1[i];
      this.artworktype.setChecked((OtherComboBoxModelData)this.artworktype.getStore().findModel("name", artworktypechoice), true);
    }
    
    this.logolocation.setValue(this.orderdatalogo.getLogoLocation());
    
    this.logosizewidth.setAllowNegative(false);
    this.logosizeheight.setAllowNegative(false);
    this.numberofcolor.setAllowNegative(false);
    this.numberofcolor.setAllowDecimals(false);
    this.numberofcolor.setPropertyEditorType(Integer.class);
    
    this.numberofcolor.setValue(this.orderdatalogo.getNumberOfColor());
    this.numberofcolor.setReadOnly((this.orderdatalogo.getDstFilename() != null) && (this.orderdatalogo.getDstFilename().toLowerCase().endsWith(".dst")));
    
    this.totalstitchcount.setAllowNegative(false);
    this.totalstitchcount.setAllowDecimals(false);
    this.totalstitchcount.setPropertyEditorType(Integer.class);
    this.artworkcharge.setAllowNegative(false);
    this.proofstodo.setAllowNegative(false);
    this.proofstodo.setAllowDecimals(false);
    this.proofstodo.setPropertyEditorType(Integer.class);
    this.totalemailproofs.setAllowNegative(false);
    this.totalemailproofs.setAllowDecimals(false);
    this.totalemailproofs.setPropertyEditorType(Integer.class);
    this.totalshipproofs.setAllowNegative(false);
    this.totalshipproofs.setAllowDecimals(false);
    this.totalshipproofs.setPropertyEditorType(Integer.class);
    
    this.logolocation.setTemplate(getLogoLocationTemplate());
    this.logolocation.setItemSelector("div.search-item");
  }
  


  private native String getLogoLocationTemplate();
  


  private void setLabels()
  {
    this.artworkfilename.setFieldLabel("Artwork Filename");
    this.dstfilename.setFieldLabel("DST Filename");
    this.digitizingrequired.setBoxLabel("Digitizing Required");
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
    this.namedroplogo.setFieldLabel("Name Drop Logo");
    this.logodescription.setFieldLabel("Logo Description");
    
    this.artworkcharge.setFieldLabel("Artwork Charge (Hours)");
    this.artworktypecomments.setFieldLabel("Change Comments");
    this.emailproof.setBoxLabel("E-Mail Proof");
    this.shipproof.setBoxLabel("Ship Proof");
    this.proofstodo.setFieldLabel("Proofs To Do");
    this.totalemailproofs.setFieldLabel("Total E-Mail Only Proofs");
    this.totalshipproofs.setFieldLabel("Total Ship Proofs");
  }
  

  private SelectionListener<ButtonEvent> selectionlistener = new SelectionListener()
  {
    public void componentSelected(ButtonEvent ce)
    {
      if (ce.getSource().equals(OverseasLogoField.this.uploadsubmit)) {
        if (OverseasLogoField.this.orderdata.getHiddenKey() == null) {
          NewWorkFlow.get().getWorkFlowRPC().saveOrder(OverseasLogoField.this.orderdata, OverseasLogoField.this.logosavecallback);
        } else {
          OverseasLogoField.this.doUploadLogo();
        }
      }
    }
  };
  

  private BuzzAsyncCallback<Integer> logosavecallback = new BuzzAsyncCallback()
  {
    public void onFailure(Throwable caught)
    {
      NewWorkFlow.get().reLogin2(caught, OverseasLogoField.this.logosavecallback);
    }
    
    public void doRetry()
    {
      NewWorkFlow.get().getWorkFlowRPC().saveOrder(OverseasLogoField.this.orderdata, OverseasLogoField.this.logosavecallback);
    }
    
    public void onSuccess(Integer result)
    {
      OverseasLogoField.this.orderdata.setHiddenKey(result);
      OverseasLogoField.this.doTabLabelChangeEvent();
      OverseasLogoField.this.doUploadLogo();
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
      if (be.getSource().equals(OverseasLogoField.this.artworkfilename)) {
        OverseasLogoField.this.orderdatalogo.setFilename("");
        OverseasLogoField.this.artworkfilename.setValue("");
        OverseasLogoField.this.doUpdateColorwaysEvent();
        OverseasLogoField.this.logoimage.setVisible(false);
      } else if (be.getSource().equals(OverseasLogoField.this.dstfilename)) {
        OverseasLogoField.this.orderdatalogo.setDstFilename("");
        OverseasLogoField.this.dstfilename.setValue("");
        OverseasLogoField.this.doUpdateColorwaysEvent();
        OverseasLogoField.this.numberofcolor.setReadOnly(false);
        OverseasLogoField.this.dstimage.setVisible(false);
      }
    }
  };
  

  private Listener<BaseEvent> clicklistener = new Listener()
  {
    public void handleEvent(BaseEvent be)
    {
      if (be.getSource().equals(OverseasLogoField.this.artworktype))
      {
        String selections = "";
        for (OtherComboBoxModelData currentchecked : OverseasLogoField.this.artworktype.getChecked()) {
          selections = selections + currentchecked.getName() + "\n";
        }
        
        OverseasLogoField.this.orderdatalogo.setArtworkType(selections);
      }
    }
  };
  



  private Listener<BaseEvent> changelistener = new Listener()
  {
    public void handleEvent(BaseEvent be)
    {
      if (be.getSource().equals(OverseasLogoField.this.logolocation)) {
        OverseasLogoField.this.orderdatalogo.setLogoLocation(OverseasLogoField.this.logolocation.getValue() == null ? "" : OverseasLogoField.this.logolocation.getValue().getValue());
        OverseasLogoField.this.doFixDecorationsEvent();
      } else if (be.getSource().equals(OverseasLogoField.this.numberofcolor)) {
        int coloramount = OverseasLogoField.this.numberofcolor.getValue() == null ? 0 : ((Number)OverseasLogoField.this.numberofcolor.getValue()).intValue();
        if (coloramount > 20) {
          com.extjs.gxt.ui.client.widget.MessageBox.confirm("Are you sure?", "Are you sure you want to add " + coloramount + " colors?", OverseasLogoField.this.addcolorscallback);
        } else {
          OverseasLogoField.this.orderdatalogo.setNumberOfColor(Integer.valueOf(coloramount));
          OverseasLogoField.this.doUpdateColorwaysEvent();
        }
      }
    }
  };
  

  private Listener<MessageBoxEvent> addcolorscallback = new Listener()
  {
    public void handleEvent(MessageBoxEvent be)
    {
      if (be.getButtonClicked().getHtml().equals("Yes")) {
        OverseasLogoField.this.orderdatalogo.setNumberOfColor(Integer.valueOf(OverseasLogoField.this.numberofcolor.getValue() == null ? 0 : ((Number)OverseasLogoField.this.numberofcolor.getValue()).intValue()));
        OverseasLogoField.this.doUpdateColorwaysEvent();
      } else {
        OverseasLogoField.this.numberofcolor.setValue(OverseasLogoField.this.orderdatalogo.getNumberOfColor());
      }
    }
  };
  

  private Listener<FormEvent> submitlistener = new Listener()
  {
    public void handleEvent(FormEvent be)
    {
      if (be.getComponent().equals(OverseasLogoField.this.myuploadform)) {
        String htmlstring = StringUtils.unescapeHTML(be.getResultHtml(), 0);
        htmlstring = htmlstring.replace("<pre>", "");
        htmlstring = htmlstring.replace("</pre>", "");
        OverseasLogoField.this.uploadsubmit.enable();
        JSONObject jsonObject = com.google.gwt.json.client.JSONParser.parseStrict(htmlstring).isObject();
        if (!jsonObject.containsKey("error")) {
          String filename = jsonObject.get("Filename").isString().stringValue();
          if (((OverseasLogoField.this.artworkfilename.getValue() == null) || (((String)OverseasLogoField.this.artworkfilename.getValue()).equals(""))) && (filename.toLowerCase().endsWith(".dst"))) {
            OverseasLogoField.this.numberofcolor.setReadOnly(true);
            OverseasLogoField.this.orderdatalogo.setDstFilename(filename);
            OverseasLogoField.this.dstfilename.setValue(OverseasLogoField.this.orderdatalogo.getDstFilename());
            OverseasLogoField.this.setDSTInfo(new DSTInfoContainer(jsonObject));
            OverseasLogoField.this.setDSTImageURL();
            OverseasLogoField.this.calcNumberOfColors();
            OverseasLogoField.this.dstimage.setVisible(true);
          } else if (filename.toLowerCase().endsWith(".dst")) {
            OverseasLogoField.this.setDSTInfo2(new DSTInfoContainer(jsonObject));
          } else {
            OverseasLogoField.this.orderdatalogo.setDstFilename("");
            OverseasLogoField.this.dstfilename.setValue("");
            OverseasLogoField.this.doUpdateColorwaysEvent();
            OverseasLogoField.this.numberofcolor.setReadOnly(false);
            OverseasLogoField.this.dstimage.setVisible(false);
            
            OverseasLogoField.this.orderdatalogo.setFilename(filename);
            OverseasLogoField.this.artworkfilename.setValue(OverseasLogoField.this.orderdatalogo.getFilename());
            OverseasLogoField.this.setImageURL();
            OverseasLogoField.this.logoimage.setVisible(true);
          }
          com.extjs.gxt.ui.client.widget.Info.display("Success", "Logo Been Uploaded");
          OverseasLogoField.this.uploadsubmit.enable();
        } else {
          Throwable myerror = new Throwable(jsonObject.get("error").isString().stringValue());
          NewWorkFlow.get().reLogin2(myerror, OverseasLogoField.this.uploadlogorelogin);
        }
      }
    }
  };
  

  BuzzAsyncCallback<String> uploadlogorelogin = new BuzzAsyncCallback()
  {
    public void onFailure(Throwable caught)
    {
      OverseasLogoField.this.uploadsubmit.enable();
    }
    
    public void onSuccess(String result)
    {
      OverseasLogoField.this.myuploadform.submit();
    }
    
    public void doRetry()
    {
      OverseasLogoField.this.myuploadform.submit();
    }
  };
  
  private void setImageURL()
  {
    this.logoimage.setUrl(NewWorkFlow.filepath + "OverseasData/" + this.orderdata.getHiddenKey() + "/logos/" + URL.encodeQueryString(this.orderdatalogo.getFilename()).replace("+", "%20"));
  }
  
  public void setDSTImageURL()
  {
    String submitstring = "&colorways=" + this.orderdatalogo.getColorwayCount();
    submitstring = submitstring + "&filename=" + URL.encodeQueryString(this.orderdatalogo.getDstFilename());
    
    int colorwaycounter = 0;
    for (OrderDataLogoColorway currentcolorway : this.orderdatalogo.getColorways()) {
      submitstring = submitstring + "&logocolorcode" + colorwaycounter + "=" + currentcolorway.getLogoColorCode();
      submitstring = submitstring + "&threadtype" + colorwaycounter + "=" + currentcolorway.getThreadType();
      colorwaycounter++;
    }
    
    submitstring = submitstring + "&hiddenkey=" + this.orderdata.getHiddenKey();
    Date date = new Date();
    this.dstimage.setUrl(NewWorkFlow.baseurl + "dstimage/?ordertype=Overseas&date=" + date.getTime() + submitstring);
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
  
  private void setDSTInfo2(DSTInfoContainer theinfo) {
    this.orderdatalogo.setLogoSizeWidth(Double.valueOf(theinfo.getLogoSizeWidth()));
    this.logosizewidth.setValue(this.orderdatalogo.getLogoSizeWidth());
    this.orderdatalogo.setLogoSizeHeight(Double.valueOf(theinfo.getLogoSizeHeight()));
    this.logosizeheight.setValue(this.orderdatalogo.getLogoSizeHeight());
    this.orderdatalogo.setStitchCount(Integer.valueOf(theinfo.getTotalStitchCount()));
    this.totalstitchcount.setValue(this.orderdatalogo.getStitchCount());
  }
  
  public void updateLocations()
  {
    this.logolocation.changeStore(this.setfield.getLogoLocations(), this.logolocation.getValue() == null ? "" : this.logolocation.getValue().getValue());
  }
  
  public void addListener(EventType eventType, Listener<? extends BaseEvent> listener) {
    this.observable.addListener(eventType, listener);
  }
  
  private void doUpdateColorwaysEvent() {
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
  
  private void doFixDecorationsEvent()
  {
    BaseEvent be = new BaseEvent(BuzzEvents.FixDecorations);
    this.observable.fireEvent(BuzzEvents.FixDecorations, be);
  }
  
  public OtherComboBox getLogoLocationField()
  {
    return this.logolocation;
  }
  
  public FormPanel getLogoUploadFormField() {
    return this.myuploadform;
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
  
  public TextField<String> getNameDropLogoField() {
    return this.namedroplogo;
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
  
  public NumberField getTotalEmailProofsField() {
    return this.totalemailproofs;
  }
  
  public NumberField getTotalShipProofsField() {
    return this.totalshipproofs;
  }
}
