package com.ottocap.NewWorkFlow.client.OnlineWorkflow;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.VerticalAlignment;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Html;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.layout.TableData;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.PushButton;
import com.ottocap.NewWorkFlow.client.DataHolder.ProductsInfo.DomesticLogoField;
import com.ottocap.NewWorkFlow.client.DataHolder.ProductsInfo.ItemPortletField;
import com.ottocap.NewWorkFlow.client.DataHolder.ProductsInfo.OverseasLogoField;
import com.ottocap.NewWorkFlow.client.DataHolder.Stores.NewStyleStore;
import com.ottocap.NewWorkFlow.client.NewWorkFlow;
import com.ottocap.NewWorkFlow.client.OnlineWorkflow.Widget.BlackLineBar;
import com.ottocap.NewWorkFlow.client.OnlineWorkflow.Widget.HeadingBox;
import com.ottocap.NewWorkFlow.client.OnlineWorkflow.Widget.PaddedBodyContainer;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData.OrderType;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataAddress;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataCustomerInformation;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataItem;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogo;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataSet;
import com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBoxModelData;
import com.ottocap.NewWorkFlow.client.Widget.ImageHelper;
import java.util.ArrayList;

public class OnlineReviewQuoteTab extends VerticalPanel
{
  private PushButton menu_gotostep1 = new PushButton();
  private PushButton menu_gotostep2 = new PushButton();
  private PushButton menu_savepdf = new PushButton();
  private PushButton menu_gotostep4 = new PushButton();
  private InstantQuoteMainScreen instantquotemainscreen;
  private VerticalPanel pricinginfopanel = new VerticalPanel();
  
  public OnlineReviewQuoteTab(OrderData orderdata, InstantQuoteMainScreen instantquotemainscreen) {
    this.instantquotemainscreen = instantquotemainscreen;
    
    setStyleAttribute("margin-left", "auto");
    setStyleAttribute("margin-right", "auto");
    setWidth(1000);
    
    this.menu_gotostep1.setStylePrimaryName("revieworderstep1button-PushButton");
    if (instantquotemainscreen.getOnlineType() == InstantQuoteMainScreen.OnlineType.QUOTE) {
      this.menu_gotostep2.setStylePrimaryName("revieworderstep2button-PushButton");
      this.menu_savepdf.setStylePrimaryName("reviewprintoutbutton-PushButton");
      this.menu_gotostep4.setStylePrimaryName("revieworderstep4button-PushButton");
    } else if (instantquotemainscreen.getOnlineType() == InstantQuoteMainScreen.OnlineType.ORDER) {
      this.menu_gotostep2.setStylePrimaryName("revieworderstep2orderbutton-PushButton");
      this.menu_savepdf.setStylePrimaryName("reviewprintoutorderbutton-PushButton");
      this.menu_gotostep4.setStylePrimaryName("revieworderstep4orderbutton-PushButton");
    } else if (instantquotemainscreen.getOnlineType() == InstantQuoteMainScreen.OnlineType.VIRTUALSAMPLE) {
      this.menu_gotostep2.setStylePrimaryName("revieworderstep2virtualsamplebutton-PushButton");
      this.menu_savepdf.setStylePrimaryName("reviewprintoutvirtualsamplebutton-PushButton");
      this.menu_gotostep4.setStylePrimaryName("revieworderstep4virtualsamplebutton-PushButton");
    } else {
      this.menu_gotostep2.setStylePrimaryName("revieworderstep2button-PushButton");
      this.menu_savepdf.setStylePrimaryName("reviewprintoutbutton-PushButton");
      this.menu_gotostep4.setStylePrimaryName("revieworderstep4button-PushButton");
    }
    
    this.menu_gotostep1.addClickHandler(this.clickhandler);
    this.menu_gotostep2.addClickHandler(this.clickhandler);
    this.menu_savepdf.addClickHandler(this.clickhandler);
    this.menu_gotostep4.addClickHandler(this.clickhandler);
    
    add(new com.ottocap.NewWorkFlow.client.OnlineWorkflow.Widget.GreyDescription("<B>Please verify the accuracy of your quote. If you would like to make a change, simply navigate to the appropriate step. Otto International, Inc. will not be responsible for any error and/or omission once order is approved.</B>"));
    
    for (int i = 0; i < orderdata.getSetCount(); i++)
    {
      OrderDataItem currentitem = orderdata.getSet(i).getItem(0);
      add(new HeadingBox("ITEM <B>" + (i + 1) + "</B> - <B>PRODUCT INFORMATION</B>"));
      PaddedBodyContainer itembodycontainer = new PaddedBodyContainer();
      itembodycontainer.add(new Html("<B><font color=#ff0000>Item " + (i + 1) + "</font></B>"));
      
      if (!currentitem.getStyleNumber().equals("")) {
        itembodycontainer.add(new Html("<B>Style Number:</B> " + currentitem.getStyleNumber()));
      }
      
      String description = instantquotemainscreen.getStyleStore(currentitem).getDescription();
      if ((description != null) && (!description.equals(""))) {
        itembodycontainer.add(new Html("<B>Style Description:</B> " + description));
      }
      
      if (!currentitem.getColorCode().equals("")) {
        OtherComboBoxModelData colormodel = (OtherComboBoxModelData)instantquotemainscreen.getStyleStore(currentitem).getColorCodeStore().findModel("value", currentitem.getColorCode());
        if (colormodel != null) {
          itembodycontainer.add(new Html("<B>Color:</B> " + colormodel.getName()));
        } else {
          itembodycontainer.add(new Html("<B>Color:</B> " + currentitem.getColorCode()));
        }
      }
      
      if (!currentitem.getSize().equals("")) {
        itembodycontainer.add(new Html("<B>Size:</B> " + currentitem.getSize()));
      }
      
      if ((currentitem.getQuantity() != null) && (currentitem.getQuantity().intValue() != 0)) {
        itembodycontainer.add(new Html("<B>Quantity:</B> " + currentitem.getQuantity()));
      }
      
      if (!currentitem.getFrontPanelColor().equals("")) {
        OtherComboBoxModelData colormodel = (OtherComboBoxModelData)instantquotemainscreen.getStyleStore(currentitem).getFrontPanelColorStore().findModel("value", currentitem.getFrontPanelColor());
        if (colormodel != null) {
          itembodycontainer.add(new Html("<B>Front Panel Color:</B> " + colormodel.getName()));
        } else {
          itembodycontainer.add(new Html("<B>Front Panel Color:</B> " + currentitem.getFrontPanelColor()));
        }
      }
      
      if (!currentitem.getSideBackPanelColor().equals("")) {
        OtherComboBoxModelData colormodel = (OtherComboBoxModelData)instantquotemainscreen.getStyleStore(currentitem).getSideBackPanelColorStore().findModel("value", currentitem.getSideBackPanelColor());
        if (colormodel != null) {
          itembodycontainer.add(new Html("<B>Side & Back Panel Color:</B> " + colormodel.getName()));
        } else {
          itembodycontainer.add(new Html("<B>Side & Back Panel Color:</B> " + currentitem.getSideBackPanelColor()));
        }
      }
      
      if ((!currentitem.getVisorStyleNumber().equals("")) && (!currentitem.getVisorStyleNumber().equals("Default"))) {
        OtherComboBoxModelData colormodel = (OtherComboBoxModelData)instantquotemainscreen.getStyleStore(currentitem).getVisorStyleNumberStore().findModel("value", currentitem.getVisorStyleNumber());
        if (colormodel != null) {
          itembodycontainer.add(new Html("<B>Visor Style Number:</B> " + colormodel.getName()));
        } else {
          itembodycontainer.add(new Html("<B>Visor Style Number:</B> " + currentitem.getVisorStyleNumber()));
        }
      }
      
      if (!currentitem.getPrimaryVisorColor().equals("")) {
        OtherComboBoxModelData colormodel = (OtherComboBoxModelData)instantquotemainscreen.getStyleStore(currentitem).getPrimaryVisorColorStore().findModel("value", currentitem.getPrimaryVisorColor());
        if (colormodel != null) {
          itembodycontainer.add(new Html("<B>Primary Visor Color:</B> " + colormodel.getName()));
        } else {
          itembodycontainer.add(new Html("<B>Primary Visor Color:</B> " + currentitem.getPrimaryVisorColor()));
        }
      }
      
      if (!currentitem.getVisorTrimColor().equals("")) {
        OtherComboBoxModelData colormodel = (OtherComboBoxModelData)instantquotemainscreen.getStyleStore(currentitem).getVisorTrimEdgeColorStore().findModel("value", currentitem.getVisorTrimColor());
        if (colormodel != null) {
          itembodycontainer.add(new Html("<B>Visor Trim/Edge Color:</B> " + colormodel.getName()));
        } else {
          itembodycontainer.add(new Html("<B>Visor Trim/Edge Color:</B> " + currentitem.getVisorTrimColor()));
        }
      }
      
      if (!currentitem.getVisorSandwichColor().equals("")) {
        OtherComboBoxModelData colormodel = (OtherComboBoxModelData)instantquotemainscreen.getStyleStore(currentitem).getVisorSandwichColorStore().findModel("value", currentitem.getVisorSandwichColor());
        if (colormodel != null) {
          itembodycontainer.add(new Html("<B>Visor Sandwich Color:</B> " + colormodel.getName()));
        } else {
          itembodycontainer.add(new Html("<B>Visor Sandwich Color:</B> " + currentitem.getVisorSandwichColor()));
        }
      }
      
      if (!currentitem.getUndervisorColor().equals("")) {
        OtherComboBoxModelData colormodel = (OtherComboBoxModelData)instantquotemainscreen.getStyleStore(currentitem).getUndervisorColorStore().findModel("value", currentitem.getUndervisorColor());
        if (colormodel != null) {
          itembodycontainer.add(new Html("<B>Undervisor Color:</B> " + colormodel.getName()));
        } else {
          itembodycontainer.add(new Html("<B>Undervisor Color:</B> " + currentitem.getUndervisorColor()));
        }
      }
      
      if (!currentitem.getDistressedVisorInsideColor().equals("")) {
        OtherComboBoxModelData colormodel = (OtherComboBoxModelData)instantquotemainscreen.getStyleStore(currentitem).getDistressedVisorInsideColorStore().findModel("value", currentitem.getDistressedVisorInsideColor());
        if (colormodel != null) {
          itembodycontainer.add(new Html("<B>Distressed Visor Inside Color:</B> " + colormodel.getName()));
        } else {
          itembodycontainer.add(new Html("<B>Distressed Visor Inside Color:</B> " + currentitem.getDistressedVisorInsideColor()));
        }
      }
      
      if ((!currentitem.getClosureStyleNumber().equals("")) && (!currentitem.getClosureStyleNumber().equals("Default"))) {
        OtherComboBoxModelData colormodel = (OtherComboBoxModelData)instantquotemainscreen.getStyleStore(currentitem).getClosureStyleNumberStore().findModel("value", currentitem.getClosureStyleNumber());
        if (colormodel != null) {
          itembodycontainer.add(new Html("<B>Closure Style Number:</B> " + colormodel.getName()));
        } else {
          itembodycontainer.add(new Html("<B>Closure Style Number:</B> " + currentitem.getClosureStyleNumber()));
        }
      }
      
      if (!currentitem.getClosureStrapColor().equals("")) {
        OtherComboBoxModelData colormodel = (OtherComboBoxModelData)instantquotemainscreen.getStyleStore(currentitem).getClosureStrapColorStore().findModel("value", currentitem.getClosureStrapColor());
        if (colormodel != null) {
          itembodycontainer.add(new Html("<B>Closure Strap Color:</B> " + colormodel.getName()));
        } else {
          itembodycontainer.add(new Html("<B>Closure Strap Color:</B> " + currentitem.getClosureStrapColor()));
        }
      }
      
      if ((!currentitem.getEyeletStyleNumber().equals("")) && (!currentitem.getEyeletStyleNumber().equals("Default"))) {
        OtherComboBoxModelData colormodel = (OtherComboBoxModelData)instantquotemainscreen.getStyleStore(currentitem).getEyeletStyleNumberStore().findModel("value", currentitem.getEyeletStyleNumber());
        if (colormodel != null) {
          itembodycontainer.add(new Html("<B>Eyelet Style Number:</B> " + colormodel.getName()));
        } else {
          itembodycontainer.add(new Html("<B>Eyelet Style Number:</B> " + currentitem.getEyeletStyleNumber()));
        }
      }
      
      if (!currentitem.getFrontEyeletColor().equals("")) {
        OtherComboBoxModelData colormodel = (OtherComboBoxModelData)instantquotemainscreen.getStyleStore(currentitem).getFrontEyeletColorStore().findModel("value", currentitem.getFrontEyeletColor());
        if (colormodel != null) {
          itembodycontainer.add(new Html("<B>Front Eyelet Color:</B> " + colormodel.getName()));
        } else {
          itembodycontainer.add(new Html("<B>Front Eyelet Color:</B> " + currentitem.getFrontEyeletColor()));
        }
      }
      
      if (!currentitem.getSideBackEyeletColor().equals("")) {
        OtherComboBoxModelData colormodel = (OtherComboBoxModelData)instantquotemainscreen.getStyleStore(currentitem).getSideBackEyeletColorStore().findModel("value", currentitem.getSideBackEyeletColor());
        if (colormodel != null) {
          itembodycontainer.add(new Html("<B>Side & Back Eyelet Color:</B> " + colormodel.getName()));
        } else {
          itembodycontainer.add(new Html("<B>Side & Back Eyelet Color:</B> " + currentitem.getSideBackEyeletColor()));
        }
      }
      
      if (!currentitem.getButtonColor().equals("")) {
        OtherComboBoxModelData colormodel = (OtherComboBoxModelData)instantquotemainscreen.getStyleStore(currentitem).getButtonColorStore().findModel("value", currentitem.getButtonColor());
        if (colormodel != null) {
          itembodycontainer.add(new Html("<B>Button Color:</B> " + colormodel.getName()));
        } else {
          itembodycontainer.add(new Html("<B>Button Color:</B> " + currentitem.getButtonColor()));
        }
      }
      
      if (!currentitem.getInnerTapingColor().equals("")) {
        OtherComboBoxModelData colormodel = (OtherComboBoxModelData)instantquotemainscreen.getStyleStore(currentitem).getInnerTapingColorStore().findModel("value", currentitem.getInnerTapingColor());
        if (colormodel != null) {
          itembodycontainer.add(new Html("<B>Inner Tapping Color:</B> " + colormodel.getName()));
        } else {
          itembodycontainer.add(new Html("<B>Inner Tapping Color:</B> " + currentitem.getInnerTapingColor()));
        }
      }
      
      if ((!currentitem.getSweatbandStyleNumber().equals("")) && (!currentitem.getSweatbandStyleNumber().equals("Default"))) {
        OtherComboBoxModelData colormodel = (OtherComboBoxModelData)instantquotemainscreen.getStyleStore(currentitem).getSweatbandStyleNumberStore().findModel("value", currentitem.getSweatbandStyleNumber());
        if (colormodel != null) {
          itembodycontainer.add(new Html("<B>Sweatband Style Number:</B> " + colormodel.getName()));
        } else {
          itembodycontainer.add(new Html("<B>Sweatband Style Number:</B> " + currentitem.getSweatbandStyleNumber()));
        }
      }
      
      if (!currentitem.getSweatbandColor().equals("")) {
        OtherComboBoxModelData colormodel = (OtherComboBoxModelData)instantquotemainscreen.getStyleStore(currentitem).getSweatbandColorStore().findModel("value", currentitem.getSweatbandColor());
        if (colormodel != null) {
          itembodycontainer.add(new Html("<B>Sweatband Color:</B> " + colormodel.getName()));
        } else {
          itembodycontainer.add(new Html("<B>Sweatband Color:</B> " + currentitem.getSweatbandColor()));
        }
      }
      
      if (!currentitem.getSweatbandStripeColor().equals("")) {
        OtherComboBoxModelData colormodel = (OtherComboBoxModelData)instantquotemainscreen.getStyleStore(currentitem).getStripeColorStore().findModel("value", currentitem.getSweatbandStripeColor());
        if (colormodel != null) {
          itembodycontainer.add(new Html("<B>Stripe Color:</B> " + colormodel.getName()));
        } else {
          itembodycontainer.add(new Html("<B>Stripe Color:</B> " + currentitem.getSweatbandStripeColor()));
        }
      }
      
      if ((currentitem.getProductSampleEmail()) && (currentitem.getProductSampleShip())) {
        itembodycontainer.add(new Html("<B>Proof Method:</B> Email & Ship Sample"));
      } else if (currentitem.getProductSampleEmail()) {
        itembodycontainer.add(new Html("<B>Proof Method:</B> Email Sample"));
      } else if (currentitem.getProductSampleShip()) {
        itembodycontainer.add(new Html("<B>Proof Method:</B> Ship Sample"));
      }
      
      com.ottocap.NewWorkFlow.client.DataHolder.ProductsInfo.SetField setfield = new com.ottocap.NewWorkFlow.client.DataHolder.ProductsInfo.SetField(orderdata.getSet(i));
      ItemPortletField picget = new ItemPortletField(orderdata, orderdata.getSet(i), currentitem, setfield);
      picget.getProductPreviewImageField().setWidth(960);
      TableData tabledata = new TableData(Style.HorizontalAlignment.CENTER, Style.VerticalAlignment.TOP);
      itembodycontainer.add(picget.getProductPreviewImageField(), tabledata);
      
      add(itembodycontainer);
      

      if (orderdata.getSet(i).getLogoCount() > 0) {
        add(new com.ottocap.NewWorkFlow.client.OnlineWorkflow.Widget.LogoHeadingBox("ITEM <B>" + (i + 1) + "</B> - <B>DESIGN INFORMATION</B>"));
      }
      
      for (int j = 0; j < orderdata.getSet(i).getLogoCount(); j++) {
        PaddedBodyContainer logobodycontainer = new PaddedBodyContainer();
        logobodycontainer.add(new Html("<B><font color=#ff0000>Logo " + (j + 1) + "</font></B>"));
        OrderDataLogo currentlogo = orderdata.getSet(i).getLogo(j);
        
        if (orderdata.getOrderType() == OrderData.OrderType.DOMESTIC) {
          if (!currentlogo.getFilename().equals("")) {
            logobodycontainer.add(new Html("<B>Logo Name:</B> " + currentlogo.getFilename()));
          }
        }
        else if (!currentlogo.getFilename().equals("")) {
          logobodycontainer.add(new Html("<B>Logo Name:</B> " + currentlogo.getFilename()));
        } else if (!currentlogo.getDstFilename().equals("")) {
          logobodycontainer.add(new Html("<B>Logo Name:</B> " + currentlogo.getDstFilename()));
        }
        

        logobodycontainer.add(new Html("<B>Logo Size:</B> " + getLogoSize(currentlogo)));
        
        if (!currentlogo.getLogoLocation().equals("")) {
          OtherComboBoxModelData colormodel = (OtherComboBoxModelData)instantquotemainscreen.getStyleStore(currentitem).getLogoLocationStore().findModel("value", currentlogo.getLogoLocation());
          if (colormodel != null) {
            logobodycontainer.add(new Html("<B>Location Number:</B> " + colormodel.getName()));
          } else {
            logobodycontainer.add(new Html("<B>Location Number:</B> " + currentlogo.getLogoLocation()));
          }
        }
        

        if ((currentlogo.getNumberOfColor() != null) && (currentlogo.getNumberOfColor().intValue() > 0)) {
          logobodycontainer.add(new Html("<B>Number of Colors:</B> " + currentlogo.getNumberOfColor()));
        }
        
        if ((currentlogo.getStitchCount() != null) && (currentlogo.getStitchCount().intValue() > 0)) {
          logobodycontainer.add(new Html("<B>Stitch Count:</B> " + currentlogo.getStitchCount()));
        }
        
        if (orderdata.getOrderType() == OrderData.OrderType.DOMESTIC) {
          if (!currentlogo.getDecoration().equals("")) {
            logobodycontainer.add(new Html("<B>Decoration Type:</B> " + currentlogo.getDecoration()));
          }
        } else {
          for (int k = 0; k < currentlogo.getDecorationCount().intValue(); k++) {
            logobodycontainer.add(new Html("<B>Decoration Type:</B> " + currentlogo.getDecoration(Integer.valueOf(k)).getName()));
          }
        }
        

        if (currentlogo.getDigitizing()) {
          logobodycontainer.add(new Html("<B>Digitizing Required:</B> Checked"));
        }
        
        if ((currentlogo.getSwatchEmail()) && (currentlogo.getSwatchShip())) {
          logobodycontainer.add(new Html("<B>Proof Method:</B> Email & Ship Swatch"));
        } else if (currentlogo.getSwatchEmail()) {
          logobodycontainer.add(new Html("<B>Proof Method:</B> Email Swatch"));
        } else if (currentlogo.getSwatchShip()) {
          logobodycontainer.add(new Html("<B>Proof Method:</B> Ship Swatch"));
        }
        
        add(logobodycontainer);
        
        if (orderdata.getOrderType() == OrderData.OrderType.DOMESTIC) {
          DomesticLogoField logoget = new DomesticLogoField(orderdata, currentlogo, setfield);
          logoget.getDSTImageField().setWidth(960);
          logoget.getLogoImageField().setWidth(960);
          TableData logotabledata1 = new TableData(Style.HorizontalAlignment.CENTER, Style.VerticalAlignment.TOP);
          TableData logotabledata2 = new TableData(Style.HorizontalAlignment.CENTER, Style.VerticalAlignment.TOP);
          add(logoget.getDSTImageField(), logotabledata1);
          add(logoget.getLogoImageField(), logotabledata2);
        } else {
          OverseasLogoField logoget = new OverseasLogoField(orderdata, currentlogo, setfield);
          logoget.getDSTImageField().setWidth(960);
          logoget.getLogoImageField().setWidth(960);
          TableData logotabledata1 = new TableData(Style.HorizontalAlignment.CENTER, Style.VerticalAlignment.TOP);
          TableData logotabledata2 = new TableData(Style.HorizontalAlignment.CENTER, Style.VerticalAlignment.TOP);
          add(logoget.getDSTImageField(), logotabledata1);
          add(logoget.getLogoImageField(), logotabledata2);
        }
      }
    }
    



    NewWorkFlow.get().getWorkFlowRPC().getPricingTable(orderdata.getHiddenKey().intValue(), this.pricingtablecallback);
    
    add(this.pricinginfopanel);
    
    if (instantquotemainscreen.getOnlineType() == InstantQuoteMainScreen.OnlineType.VIRTUALSAMPLE) {
      add(new HeadingBox("CUSTOMER <B>INFORMATION</B>"));
      
      PaddedBodyContainer customerinfocontainer = new PaddedBodyContainer();
      if (!orderdata.getCustomerInformation().getContactName().equals("")) {
        customerinfocontainer.add(new Html(orderdata.getCustomerInformation().getContactName()));
      }
      if (!orderdata.getCustomerInformation().getCompany().equals("")) {
        customerinfocontainer.add(new Html(orderdata.getCustomerInformation().getCompany()));
      }
      if (!orderdata.getCustomerInformation().getBillInformation().getStreetLine1().equals("")) {
        customerinfocontainer.add(new Html(orderdata.getCustomerInformation().getBillInformation().getStreetLine1()));
      }
      if (!orderdata.getCustomerInformation().getBillInformation().getStreetLine2().equals("")) {
        customerinfocontainer.add(new Html(orderdata.getCustomerInformation().getBillInformation().getStreetLine2()));
      }
      if ((!orderdata.getCustomerInformation().getBillInformation().getCity().equals("")) && (!orderdata.getCustomerInformation().getBillInformation().getState().equals("")) && (!orderdata.getCustomerInformation().getBillInformation().getZip().equals(""))) {
        customerinfocontainer.add(new Html(orderdata.getCustomerInformation().getBillInformation().getCity() + ", " + orderdata.getCustomerInformation().getBillInformation().getState() + " " + orderdata.getCustomerInformation().getBillInformation().getZip()));
      }
      if (!orderdata.getCustomerInformation().getBillInformation().getCountry().equals("")) {
        customerinfocontainer.add(new Html(orderdata.getCustomerInformation().getBillInformation().getCountry()));
      }
      if (orderdata.getCustomerInformation().getPhone() != null) {
        customerinfocontainer.add(new Html(orderdata.getCustomerInformation().getPhone()));
      }
      if (!orderdata.getCustomerInformation().getEmail().equals("")) {
        customerinfocontainer.add(new Html(orderdata.getCustomerInformation().getEmail()));
      }
      add(customerinfocontainer);
    }
    else {
      HorizontalPanel customershippingorderpanel = new HorizontalPanel();
      customershippingorderpanel.setWidth("1000px");
      
      VerticalPanel customerinfopanel = new VerticalPanel();
      customerinfopanel.setStyleAttribute("margin-right", "11px");
      customerinfopanel.add(new HeadingBox("CUSTOMER <B>INFORMATION</B>", 326));
      
      PaddedBodyContainer customerinfocontainer = new PaddedBodyContainer(286);
      if (!orderdata.getCustomerInformation().getContactName().equals("")) {
        customerinfocontainer.add(new Html(orderdata.getCustomerInformation().getContactName()));
      }
      if (!orderdata.getCustomerInformation().getCompany().equals("")) {
        customerinfocontainer.add(new Html(orderdata.getCustomerInformation().getCompany()));
      }
      if (!orderdata.getCustomerInformation().getBillInformation().getStreetLine1().equals("")) {
        customerinfocontainer.add(new Html(orderdata.getCustomerInformation().getBillInformation().getStreetLine1()));
      }
      if (!orderdata.getCustomerInformation().getBillInformation().getStreetLine2().equals("")) {
        customerinfocontainer.add(new Html(orderdata.getCustomerInformation().getBillInformation().getStreetLine2()));
      }
      if ((!orderdata.getCustomerInformation().getBillInformation().getCity().equals("")) && (!orderdata.getCustomerInformation().getBillInformation().getState().equals("")) && (!orderdata.getCustomerInformation().getBillInformation().getZip().equals(""))) {
        customerinfocontainer.add(new Html(orderdata.getCustomerInformation().getBillInformation().getCity() + ", " + orderdata.getCustomerInformation().getBillInformation().getState() + " " + orderdata.getCustomerInformation().getBillInformation().getZip()));
      }
      if (!orderdata.getCustomerInformation().getBillInformation().getCountry().equals("")) {
        customerinfocontainer.add(new Html(orderdata.getCustomerInformation().getBillInformation().getCountry()));
      }
      if (orderdata.getCustomerInformation().getPhone() != null) {
        customerinfocontainer.add(new Html(orderdata.getCustomerInformation().getPhone()));
      }
      if (!orderdata.getCustomerInformation().getEmail().equals("")) {
        customerinfocontainer.add(new Html(orderdata.getCustomerInformation().getEmail()));
      }
      customerinfopanel.add(customerinfocontainer);
      
      VerticalPanel shippinginfopanel = new VerticalPanel();
      shippinginfopanel.setStyleAttribute("margin-right", "11px");
      shippinginfopanel.add(new HeadingBox("SHIPPING <B>INFORMATION</B>", 326));
      PaddedBodyContainer shippinginfocontainer = new PaddedBodyContainer(286);
      if (!orderdata.getShippingType().equals("")) {
        shippinginfocontainer.add(new Html(orderdata.getShippingType()));
      }
      if (!orderdata.getCustomerInformation().getShipInformation().getCompany().equals("")) {
        shippinginfocontainer.add(new Html(orderdata.getCustomerInformation().getShipInformation().getCompany()));
      }
      if (!orderdata.getCustomerInformation().getShipInformation().getStreetLine1().equals("")) {
        shippinginfocontainer.add(new Html(orderdata.getCustomerInformation().getShipInformation().getStreetLine1()));
      }
      if (!orderdata.getCustomerInformation().getShipInformation().getStreetLine2().equals("")) {
        shippinginfocontainer.add(new Html(orderdata.getCustomerInformation().getShipInformation().getStreetLine2()));
      }
      if ((!orderdata.getCustomerInformation().getShipInformation().getCity().equals("")) && (!orderdata.getCustomerInformation().getShipInformation().getState().equals("")) && (!orderdata.getCustomerInformation().getShipInformation().getZip().equals(""))) {
        shippinginfocontainer.add(new Html(orderdata.getCustomerInformation().getShipInformation().getCity() + ", " + orderdata.getCustomerInformation().getShipInformation().getState() + " " + orderdata.getCustomerInformation().getShipInformation().getZip()));
      }
      if (!orderdata.getCustomerInformation().getShipInformation().getCountry().equals("")) {
        shippinginfocontainer.add(new Html(orderdata.getCustomerInformation().getShipInformation().getCountry()));
      }
      shippinginfopanel.add(shippinginfocontainer);
      
      VerticalPanel orderinfopanel = new VerticalPanel();
      orderinfopanel.add(new HeadingBox("ORDER <B>INFORMATION</B>", 326));
      PaddedBodyContainer orderinfocontainer = new PaddedBodyContainer(286);
      if (!orderdata.getCustomerPO().equals("")) {
        orderinfocontainer.add(new Html("Customer PO#: " + orderdata.getCustomerPO()));
      }
      if (orderdata.getInHandDate() != null) {
        orderinfocontainer.add(new Html("In-Hand Date: " + com.google.gwt.i18n.client.DateTimeFormat.getFormat("MM/dd/y").format(orderdata.getInHandDate())));
      }
      if (!orderdata.getSpecialNotes().equals("")) {
        orderinfocontainer.add(new Html("Special Notes: " + orderdata.getSpecialNotes()));
      }
      orderinfopanel.add(orderinfocontainer);
      
      customershippingorderpanel.add(customerinfopanel);
      customershippingorderpanel.add(shippinginfopanel);
      customershippingorderpanel.add(orderinfopanel);
      add(customershippingorderpanel);
    }
    

    add(new BlackLineBar(1000));
    


    HorizontalPanel menubarrow = new HorizontalPanel();
    
    menubarrow.add(this.menu_gotostep1);
    if (instantquotemainscreen.getOnlineType() != InstantQuoteMainScreen.OnlineType.VIRTUALSAMPLE) {
      menubarrow.add(this.menu_gotostep2);
      menubarrow.add(this.menu_savepdf);
    }
    menubarrow.add(this.menu_gotostep4);
    
    add(menubarrow, new TableData(Style.HorizontalAlignment.CENTER, Style.VerticalAlignment.MIDDLE));
    
    add(new BlackLineBar(1000));
  }
  

  private com.google.gwt.event.dom.client.ClickHandler clickhandler = new com.google.gwt.event.dom.client.ClickHandler()
  {
    public void onClick(ClickEvent event)
    {
      if (event.getSource() == OnlineReviewQuoteTab.this.menu_gotostep1) {
        OnlineReviewQuoteTab.this.instantquotemainscreen.doStep(1);
      } else if (event.getSource() == OnlineReviewQuoteTab.this.menu_gotostep2) {
        OnlineReviewQuoteTab.this.instantquotemainscreen.doStep(2);
      } else if (event.getSource() == OnlineReviewQuoteTab.this.menu_savepdf) {
        OnlineReviewQuoteTab.this.instantquotemainscreen.printPDF();
      } else if (event.getSource() == OnlineReviewQuoteTab.this.menu_gotostep4) {
        OnlineReviewQuoteTab.this.instantquotemainscreen.doStep(4);
      }
    }
  };
  

  private String getLogoSize(OrderDataLogo thelogo)
  {
    double width = thelogo.getLogoSizeWidth() != null ? thelogo.getLogoSizeWidth().doubleValue() : 0.0D;
    double height = thelogo.getLogoSizeHeight() != null ? thelogo.getLogoSizeHeight().doubleValue() : 0.0D;
    if ((width == 0.0D) && (height == 0.0D))
      return "Proportional W & H";
    if (width == 0.0D)
      return "Proportional W By " + height + "\" H";
    if (height == 0.0D) {
      return width + "\" W By Proportional H";
    }
    return width + "\" W * " + height + "\" H";
  }
  

  private com.google.gwt.user.client.rpc.AsyncCallback<ArrayList<String[]>> pricingtablecallback = new com.google.gwt.user.client.rpc.AsyncCallback()
  {
    public void onFailure(Throwable caught)
    {
      caught.printStackTrace();
    }
    

    public void onSuccess(ArrayList<String[]> result)
    {
      OnlineReviewQuoteTab.this.pricinginfopanel.add(new HeadingBox("PRICING <B>INFORMATION</B>"));
      OnlineReviewQuoteTab.this.pricinginfopanel.add(new com.ottocap.NewWorkFlow.client.OnlineWorkflow.Widget.GreyDescription("<B>This quote is an estimate and will only be valid for 30 days. Special Shipping may be required to meet in-hands date.</B>"));
      PaddedBodyContainer pricelistcontainer = new PaddedBodyContainer();
      HorizontalPanel titlepanel = new HorizontalPanel();
      titlepanel.setWidth(940);
      TableData celldata0 = new TableData();
      celldata0.setWidth("502px");
      TableData celldata1 = new TableData();
      celldata1.setWidth("146px");
      TableData celldata2 = new TableData();
      celldata2.setWidth("146px");
      TableData celldata3 = new TableData();
      celldata3.setWidth("146px");
      titlepanel.add(new Html("<B><font color=#ff0000>Detail</font></B>"), celldata0);
      titlepanel.add(new Html("<B><font color=#ff0000>QTY</font></B>"), celldata1);
      titlepanel.add(new Html("<B><font color=#ff0000>Unit Price</font></B>"), celldata2);
      titlepanel.add(new Html("<B><font color=#ff0000>Total</font></B>"), celldata3);
      pricelistcontainer.add(titlepanel);
      for (int i = 0; i < result.size() - 1; i++)
      {
        HorizontalPanel pricelinepanel = new HorizontalPanel();
        pricelinepanel.setWidth(940);
        TableData pcelldata0 = new TableData();
        pcelldata0.setWidth("502px");
        TableData pcelldata1 = new TableData();
        pcelldata1.setWidth("146px");
        TableData pcelldata2 = new TableData();
        pcelldata2.setWidth("146px");
        TableData pcelldata3 = new TableData();
        pcelldata3.setWidth("146px");
        
        if (!((String[])result.get(i))[0].equals("")) {
          pricelinepanel.add(new Html(((String[])result.get(i))[0]), pcelldata0);
        } else {
          Html paddedtext = new Html(((String[])result.get(i))[1]);
          paddedtext.setStyleAttribute("padding-left", "30px");
          pricelinepanel.add(paddedtext, pcelldata0);
        }
        pricelinepanel.add(new Html(((String[])result.get(i))[2]), pcelldata1);
        pricelinepanel.add(new Html(((String[])result.get(i))[3]), pcelldata2);
        pricelinepanel.add(new Html(((String[])result.get(i))[4]), pcelldata3);
        pricelistcontainer.add(pricelinepanel);
      }
      
      HorizontalPanel totalpanel = new HorizontalPanel();
      totalpanel.setWidth(940);
      TableData tcelldata0 = new TableData();
      tcelldata0.setWidth("648px");
      TableData tcelldata2 = new TableData();
      tcelldata2.setWidth("146px");
      TableData tcelldata3 = new TableData();
      tcelldata3.setWidth("146px");
      totalpanel.add(new Html(""), tcelldata0);
      totalpanel.add(new Html("<B><font color=#ff0000>" + ((String[])result.get(result.size() - 1))[3] + "</font></B>"), tcelldata2);
      totalpanel.add(new Html("<B><font color=#ff0000>" + ((String[])result.get(result.size() - 1))[4] + "</font></B>"), tcelldata3);
      pricelistcontainer.add(totalpanel);
      
      OnlineReviewQuoteTab.this.pricinginfopanel.add(pricelistcontainer);
      
      OnlineReviewQuoteTab.this.pricinginfopanel.layout(true);
    }
  };
}
