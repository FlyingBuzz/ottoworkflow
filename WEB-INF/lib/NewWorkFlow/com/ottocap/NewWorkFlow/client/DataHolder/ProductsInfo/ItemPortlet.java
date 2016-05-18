package com.ottocap.NewWorkFlow.client.DataHolder.ProductsInfo;

import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.IconButtonEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Header;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.button.ToolButton;
import com.extjs.gxt.ui.client.widget.form.CheckBoxGroup;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.ottocap.NewWorkFlow.client.DataHolder.WorkOrderInfo.OverseasVendorsLayout;
import com.ottocap.NewWorkFlow.client.EditOrder.OrderTab;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData.OrderType;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataItem;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataSet;
import com.ottocap.NewWorkFlow.client.Widget.BuzzEvents;
import com.ottocap.NewWorkFlow.client.Widget.FormHorizontalPanel2;

public class ItemPortlet extends com.extjs.gxt.ui.client.widget.custom.Portlet
{
  private OrderDataItem item = null;
  private SetPortlet setportlet = null;
  private ToolButton closebutton = new ToolButton("x-tool-close");
  private ToolButton copybutton = new ToolButton("x-tool-restore");
  private ItemPortletField itemportletfield;
  
  public ItemPortlet(OrderData orderdata, OrderDataSet orderdataset, OrderDataItem item, SetPortlet setportlet)
  {
    setFrame(true);
    this.setportlet = setportlet;
    this.item = item;
    setCollapsible(true);
    getHeader().addTool(this.copybutton);
    getHeader().addTool(this.closebutton);
    this.itemportletfield = new ItemPortletField(orderdata, orderdataset, item, setportlet.getSetField());
    this.itemportletfield.addListener(BuzzEvents.TabLabelChange, this.tablabelchangelistener);
    this.itemportletfield.addListener(BuzzEvents.SetVendorVisible, this.setvendorvisiblelistener);
    this.itemportletfield.addListener(BuzzEvents.AddLogo, this.addlogolistener);
    
    FormHorizontalPanel2 productsizecolorpanel = new FormHorizontalPanel2();
    FormHorizontalPanel2 quantityfobpanel = new FormHorizontalPanel2();
    FormHorizontalPanel2 frontsidecolorpanel = new FormHorizontalPanel2();
    FormHorizontalPanel2 visorpanel = new FormHorizontalPanel2();
    FormHorizontalPanel2 closurepanel = new FormHorizontalPanel2();
    FormHorizontalPanel2 eyeletpanel = new FormHorizontalPanel2();
    FormHorizontalPanel2 sweatbandpanel = new FormHorizontalPanel2();
    
    this.copybutton.addSelectionListener(this.selectionlistener);
    this.closebutton.addSelectionListener(this.selectionlistener);
    
    FieldSet productinformation = new FieldSet();
    productinformation.setHeadingHtml("Product Information");
    
    FormHorizontalPanel2 productstylepanel = new FormHorizontalPanel2();
    productstylepanel.add(this.itemportletfield.getProductCategoryField());
    productstylepanel.add(this.itemportletfield.getProductStyleNumberField());
    productstylepanel.add(this.itemportletfield.getVendorNumberField());
    productstylepanel.add(this.itemportletfield.getProfileField());
    productstylepanel.add(this.itemportletfield.getNumberOfPanelsField());
    productstylepanel.add(this.itemportletfield.getCrownConstructionField());
    productinformation.add(productstylepanel);
    
    productsizecolorpanel.add(this.itemportletfield.getProductSizeField());
    productsizecolorpanel.add(this.itemportletfield.getFrontPanelFabricField());
    productsizecolorpanel.add(this.itemportletfield.getSideBackPanelFabricField());
    productsizecolorpanel.add(this.itemportletfield.getBackPanelFabricField());
    productsizecolorpanel.add(this.itemportletfield.getSidePanelFabricField());
    productsizecolorpanel.add(this.itemportletfield.getProductColorField());
    productinformation.add(productsizecolorpanel);
    
    quantityfobpanel.add(this.itemportletfield.getQuantityField());
    quantityfobpanel.add(this.itemportletfield.getFobDozenPriceField());
    quantityfobpanel.add(this.itemportletfield.getFobDozenMarkupPriceField());
    quantityfobpanel.add(this.itemportletfield.getCustomStyleNameField());
    productinformation.add(quantityfobpanel);
    
    frontsidecolorpanel.add(this.itemportletfield.getPanelStitchColorField());
    frontsidecolorpanel.add(this.itemportletfield.getFrontPanelColorField());
    frontsidecolorpanel.add(this.itemportletfield.getSideBackPanelColorField());
    frontsidecolorpanel.add(this.itemportletfield.getBackPanelColorField());
    frontsidecolorpanel.add(this.itemportletfield.getSidePanelColorField());
    frontsidecolorpanel.add(this.itemportletfield.getButtonColorField());
    frontsidecolorpanel.add(this.itemportletfield.getInnerTapingColorField());
    productinformation.add(frontsidecolorpanel);
    
    visorpanel.add(this.itemportletfield.getVisorStyleNumberField());
    visorpanel.add(this.itemportletfield.getVisorRowStitchingField());
    visorpanel.add(this.itemportletfield.getVisorStitchColorField());
    visorpanel.add(this.itemportletfield.getPrimaryVisorColorField());
    visorpanel.add(this.itemportletfield.getVisorTrimEdgeColorField());
    visorpanel.add(this.itemportletfield.getVisorSandwichColorField());
    visorpanel.add(this.itemportletfield.getDistressedVisorInsideColorField());
    visorpanel.add(this.itemportletfield.getUndervisorColorField());
    productinformation.add(visorpanel);
    
    closurepanel.add(this.itemportletfield.getClosureStyleNumberField());
    closurepanel.add(this.itemportletfield.getClosureStrapColorField());
    productinformation.add(closurepanel);
    
    eyeletpanel.add(this.itemportletfield.getEyeletStyleNumberField());
    eyeletpanel.add(this.itemportletfield.getFrontEyeletColorField());
    eyeletpanel.add(this.itemportletfield.getSideBackEyeletColorField());
    eyeletpanel.add(this.itemportletfield.getBackEyeletColorField());
    eyeletpanel.add(this.itemportletfield.getSideEyeletColorField());
    productinformation.add(eyeletpanel);
    
    sweatbandpanel.add(this.itemportletfield.getSweatbandStyleNumberField());
    sweatbandpanel.add(this.itemportletfield.getSweatbandColorField());
    sweatbandpanel.add(this.itemportletfield.getSweatbandStripeColorField());
    productinformation.add(sweatbandpanel);
    
    FieldSet questionsset = new FieldSet();
    questionsset.setHeadingHtml("Questions");
    
    questionsset.add(this.itemportletfield.getPrivateOrStandardLabelGroupField());
    questionsset.add(this.itemportletfield.getPrivateOrStandardPackagingGroupField());
    questionsset.add(this.itemportletfield.getPrivateOrStandardShippingMarkGroupField());
    
    FieldSet extraoptions = new FieldSet();
    extraoptions.setCollapsible(true);
    extraoptions.setHeadingHtml("Extra Options");
    extraoptions.collapse();
    
    FormHorizontalPanel2 checkboxspanel = new FormHorizontalPanel2();
    
    LayoutContainer checkboxgroup1layout = new LayoutContainer();
    checkboxgroup1layout.setLayout(new FormLayout(com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign.TOP));
    CheckBoxGroup checkboxgroup1 = new CheckBoxGroup();
    checkboxgroup1.setFieldLabel("Additional Services");
    checkboxgroup1.setOrientation(Style.Orientation.VERTICAL);
    checkboxgroup1.add(this.itemportletfield.getPolybagField());
    checkboxgroup1.add(this.itemportletfield.getPersonalizationField());
    checkboxgroup1layout.add(checkboxgroup1);
    checkboxgroup1layout.add(this.itemportletfield.getPersonaliztionChangesField());
    
    CheckBoxGroup checkboxgroup2 = new CheckBoxGroup();
    checkboxgroup2.setOrientation(Style.Orientation.VERTICAL);
    checkboxgroup2.add(this.itemportletfield.getSewingPatchesField());
    checkboxgroup2.add(this.itemportletfield.getHeatPressPatchesField());
    
    CheckBoxGroup checkboxgroup3 = new CheckBoxGroup();
    checkboxgroup3.setOrientation(Style.Orientation.VERTICAL);
    checkboxgroup3.add(this.itemportletfield.getOakLeavesField());
    checkboxgroup3.add(this.itemportletfield.getTaggingField());
    checkboxgroup3.add(this.itemportletfield.getStickersField());
    
    CheckBoxGroup checkboxgroup4 = new CheckBoxGroup();
    checkboxgroup4.setOrientation(Style.Orientation.VERTICAL);
    checkboxgroup4.add(this.itemportletfield.getAddInnerPrintedLabelField());
    checkboxgroup4.add(this.itemportletfield.getRemoveInnerPrintedLabelField());
    
    CheckBoxGroup checkboxgroup5 = new CheckBoxGroup();
    checkboxgroup5.setOrientation(Style.Orientation.VERTICAL);
    checkboxgroup5.add(this.itemportletfield.getAddInnerWovenLabelField());
    checkboxgroup5.add(this.itemportletfield.getRemoveInnerWovenLabelField());
    
    checkboxspanel.add(checkboxgroup1layout);
    checkboxspanel.addCheckBox(checkboxgroup2);
    checkboxspanel.addCheckBox(checkboxgroup3);
    checkboxspanel.addCheckBox(checkboxgroup4);
    checkboxspanel.addCheckBox(checkboxgroup5);
    
    if (orderdata.getOrderType() == OrderData.OrderType.DOMESTIC) {
      extraoptions.add(checkboxspanel);
    }
    
    FormHorizontalPanel2 specialnotespnael = new FormHorizontalPanel2();
    specialnotespnael.add(this.itemportletfield.getItemSpecialNotesField(), new FormData(1072, -1));
    extraoptions.add(specialnotespnael);
    










    FieldSet productsample = new FieldSet();
    productsample.setHeadingHtml("Product Sample");
    
    FormHorizontalPanel2 proofspanel = new FormHorizontalPanel2();
    
    proofspanel.addCheckBox(this.itemportletfield.getEmailProofField(), new FormData(91, 0));
    proofspanel.addCheckBox(this.itemportletfield.getShipProofField(), new FormData(91, 0));
    proofspanel.add(this.itemportletfield.getProofsToDoField());
    if (orderdata.getOrderType().equals(OrderData.OrderType.DOMESTIC)) {
      proofspanel.add(this.itemportletfield.getProofsTotalDoneField());
    } else {
      proofspanel.add(this.itemportletfield.getProofsTotalEmailField());
      proofspanel.add(this.itemportletfield.getProofsTotalShipField());
    }
    
    productsample.add(proofspanel);
    
    if (orderdata.getOrderType().equals(OrderData.OrderType.OVERSEAS)) {
      FormHorizontalPanel2 sampleapprovedlistpanel = new FormHorizontalPanel2();
      sampleapprovedlistpanel.add(this.itemportletfield.getSampleApprovedListField(), new FormData(418, 0));
      productsample.add(sampleapprovedlistpanel);
    }
    
    FieldSet productdiscount = new FieldSet();
    productdiscount.setHeadingHtml("Product Discount");
    productdiscount.add(this.itemportletfield.getProductDiscountField());
    

    FieldSet productpreview = new FieldSet();
    productpreview.setHeadingHtml("Product Preview");
    
    productpreview.add(this.itemportletfield.getISampleUploadFormField());
    productpreview.add(this.itemportletfield.getProductPreviewImageField());
    
    add(productinformation);
    
    extraoptions.add(productsample);
    extraoptions.add(productdiscount);
    extraoptions.add(productpreview);
    if (orderdata.getOrderType() == OrderData.OrderType.OVERSEAS) {
      add(questionsset);
    }
    add(extraoptions);
  }
  

  private SelectionListener<IconButtonEvent> selectionlistener = new SelectionListener()
  {
    public void componentSelected(IconButtonEvent ce)
    {
      if (ce.getSource().equals(ItemPortlet.this.closebutton)) {
        ItemPortlet.this.setportlet.removeItem(ItemPortlet.this);
      } else if (ce.getSource().equals(ItemPortlet.this.copybutton)) {
        ItemPortlet.this.setportlet.addItem(ItemPortlet.this.item.copy());
      }
    }
  };
  
  public OrderDataItem getItem() {
    return this.itemportletfield.getItem();
  }
  
  private Listener<BaseEvent> tablabelchangelistener = new Listener()
  {

    public void handleEvent(BaseEvent be)
    {
      ItemPortlet.this.setportlet.getProductsPortal().getOrderTab().setTabHeader();
    }
  };
  


  private Listener<BaseEvent> setvendorvisiblelistener = new Listener()
  {
    public void handleEvent(BaseEvent be)
    {
      if (ItemPortlet.this.setportlet.getProductsPortal().getOrderTab().getOverseasVendorsLayout() != null) {
        ItemPortlet.this.setportlet.getProductsPortal().getOrderTab().getOverseasVendorsLayout().setVendorsVisible();
      }
    }
  };
  


  private Listener<BaseEvent> addlogolistener = new Listener()
  {

    public void handleEvent(BaseEvent be)
    {
      ItemPortlet.this.setportlet.addLogo((com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogo)be.getSource());
    }
  };
  

  public ItemPortletField getItemPortletField()
  {
    return this.itemportletfield;
  }
}
