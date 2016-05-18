package com.ottocap.NewWorkFlow.server.RPCService;

import com.extjs.gxt.ui.client.core.FastMap;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataAddress;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataCustomerInformation;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataDiscount;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataItem;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogo;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogoColorway;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogoDecoration;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataVendorInformation;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataVendors;
import com.ottocap.NewWorkFlow.server.SQLTable;
import java.util.Date;
import javax.servlet.http.HttpSession;

public class GetOrder
{
  OrderData orderdata = new OrderData();
  HttpSession httpsession;
  SQLTable sqltable;
  
  public GetOrder(int hiddenkey, HttpSession httpsession, SQLTable sqltable) throws Exception {
    this(hiddenkey, httpsession, sqltable, true);
  }
  
  public GetOrder(int hiddenkey, HttpSession httpsession, SQLTable sqltable, boolean checksession) throws Exception {
    this.sqltable = sqltable;
    this.httpsession = httpsession;
    if (checksession) {
      checkSession(hiddenkey);
    }
    
    sqltable.makeTable("SELECT * FROM `ordermain` WHERE `hiddenkey` =" + hiddenkey + " LIMIT 1");
    FastMap<Object> ordermainrow = sqltable.getRow();
    this.orderdata.setHiddenKey((Integer)ordermainrow.get("hiddenkey"));
    
    if (ordermainrow.get("ordertype").equals("Overseas")) {
      this.orderdata.setOrderType(com.ottocap.NewWorkFlow.client.OrderData.OrderData.OrderType.OVERSEAS);
    } else {
      this.orderdata.setOrderType(com.ottocap.NewWorkFlow.client.OrderData.OrderData.OrderType.DOMESTIC);
    }
    
    int setcount = ((Integer)ordermainrow.get("setcount")).intValue();
    int discountitemcount = ((Integer)ordermainrow.get("discountitemcount")).intValue();
    this.orderdata.getCustomerInformation().setEclipseAccountNumber((Integer)ordermainrow.get("eclipseaccountnumber"));
    this.orderdata.getCustomerInformation().setContactName((String)ordermainrow.get("contactname"));
    this.orderdata.getCustomerInformation().setCompany((String)ordermainrow.get("company"));
    this.orderdata.getCustomerInformation().setCompanyLogo((String)ordermainrow.get("companylogo"));
    this.orderdata.getCustomerInformation().setPhone((String)ordermainrow.get("phone"));
    this.orderdata.getCustomerInformation().setFax((String)ordermainrow.get("fax"));
    this.orderdata.getCustomerInformation().setEmail((String)ordermainrow.get("email"));
    this.orderdata.getCustomerInformation().setTerms((String)ordermainrow.get("terms"));
    this.orderdata.getCustomerInformation().getBillInformation().setCompany((String)ordermainrow.get("billcompany"));
    this.orderdata.getCustomerInformation().getBillInformation().setStreetLine1((String)ordermainrow.get("billstreet1"));
    this.orderdata.getCustomerInformation().getBillInformation().setStreetLine2((String)ordermainrow.get("billstreet2"));
    this.orderdata.getCustomerInformation().getBillInformation().setCity((String)ordermainrow.get("billcity"));
    this.orderdata.getCustomerInformation().getBillInformation().setState((String)ordermainrow.get("billstate"));
    this.orderdata.getCustomerInformation().getBillInformation().setZip((String)ordermainrow.get("billzip"));
    this.orderdata.getCustomerInformation().getBillInformation().setCountry((String)ordermainrow.get("billcountry"));
    this.orderdata.getCustomerInformation().setShipAttention((String)ordermainrow.get("shipattn"));
    this.orderdata.getCustomerInformation().setSameAsBillingAddress(((Boolean)ordermainrow.get("sameasbillingaddress")).booleanValue());
    this.orderdata.getCustomerInformation().setBlindShippingRequired(((Boolean)ordermainrow.get("blindshippingrequired")).booleanValue());
    this.orderdata.getCustomerInformation().setTaxExampt(((Boolean)ordermainrow.get("taxexampt")).booleanValue());
    this.orderdata.getCustomerInformation().setHaveDropShipment(((Boolean)ordermainrow.get("havedropshipment")).booleanValue());
    this.orderdata.getCustomerInformation().setDropShipmentAmount((Integer)ordermainrow.get("dropshipmentamount"));
    this.orderdata.getCustomerInformation().getShipInformation().setCompany((String)ordermainrow.get("shipcompany"));
    this.orderdata.getCustomerInformation().getShipInformation().setStreetLine1((String)ordermainrow.get("shipstreet1"));
    this.orderdata.getCustomerInformation().getShipInformation().setStreetLine2((String)ordermainrow.get("shipstreet2"));
    this.orderdata.getCustomerInformation().getShipInformation().setCity((String)ordermainrow.get("shipcity"));
    this.orderdata.getCustomerInformation().getShipInformation().setState((String)ordermainrow.get("shipstate"));
    this.orderdata.getCustomerInformation().getShipInformation().setZip((String)ordermainrow.get("shipzip"));
    this.orderdata.getCustomerInformation().getShipInformation().setCountry((String)ordermainrow.get("shipcountry"));
    this.orderdata.getCustomerInformation().getShipInformation().setResidential(((Boolean)ordermainrow.get("shipresidential")).booleanValue());
    this.orderdata.setOrderNumber((String)ordermainrow.get("ordernumber"));
    this.orderdata.setCustomerPO((String)ordermainrow.get("customerpo"));
    this.orderdata.setOrderDate((Date)ordermainrow.get("orderdate"));
    this.orderdata.setOrderShipDate((Date)ordermainrow.get("ordershipdate"));
    this.orderdata.setEstimatedShipDate((Date)ordermainrow.get("estimatedshipdate"));
    this.orderdata.setInHandDate((Date)ordermainrow.get("inhanddate"));
    this.orderdata.setRushOrder(((Boolean)ordermainrow.get("rushorder")).booleanValue());
    this.orderdata.setOrderStatus((String)ordermainrow.get("orderstatus"));
    this.orderdata.setQuoteToOrder((String)ordermainrow.get("quotetoorder"));
    this.orderdata.setSpecialNotes((String)ordermainrow.get("specialnotes"));
    this.orderdata.setNextAction((String)ordermainrow.get("nextaction"));
    this.orderdata.setInternalDueDateTime((Date)ordermainrow.get("internalduedatetime"));
    this.orderdata.setInternalComments((String)ordermainrow.get("internalcomments"));
    this.orderdata.setPotentialRepeatFrequency((String)ordermainrow.get("potentialrepeatfrequency"));
    this.orderdata.setPotentialInternalComments((String)ordermainrow.get("potentialinternalcomments"));
    this.orderdata.setPotentialRepeatDate((Date)ordermainrow.get("potentialrepeatdate"));
    this.orderdata.setShippingType((String)ordermainrow.get("shippingtype"));
    this.orderdata.setShippingCost((Double)ordermainrow.get("shippingcost"));
    this.orderdata.setEmployeeId((String)ordermainrow.get("employeeid"));
    
    for (int i = 0; i < discountitemcount; i++) {
      sqltable.makeTable("SELECT * FROM `ordermain_discounts` WHERE `hiddenkey` =" + hiddenkey + " AND `discountitem` =" + i + " LIMIT 1");
      FastMap<Object> ordermain_discountitemrow = sqltable.getRow();
      OrderDataDiscount newdiscountitem = new OrderDataDiscount();
      newdiscountitem.setAmount((Double)ordermain_discountitemrow.get("amount"));
      newdiscountitem.setReason((String)ordermain_discountitemrow.get("reason"));
      newdiscountitem.setIntoItems((Boolean)ordermain_discountitemrow.get("intoitems"));
      this.orderdata.addDiscountItem(newdiscountitem);
    }
    
    for (int i = 0; i < setcount; i++) {
      sqltable.makeTable("SELECT * FROM `ordermain_sets` WHERE `hiddenkey` =" + hiddenkey + " AND `set` =" + i + " LIMIT 1");
      FastMap<Object> ordermain_setrow = sqltable.getRow();
      com.ottocap.NewWorkFlow.client.OrderData.OrderDataSet newset = new com.ottocap.NewWorkFlow.client.OrderData.OrderDataSet();
      int itemcount = ((Integer)ordermain_setrow.get("itemcount")).intValue();
      int logocount = ((Integer)ordermain_setrow.get("logocount")).intValue();
      
      for (int j = 0; j < itemcount; j++) {
        sqltable.makeTable("SELECT * FROM `ordermain_sets_items` WHERE `hiddenkey` =" + hiddenkey + " AND `set` =" + i + " AND `item` =" + j + " LIMIT 1");
        FastMap<Object> ordermain_itemrow = sqltable.getRow();
        OrderDataItem newitem = new OrderDataItem();
        newitem.setStyleNumber((String)ordermain_itemrow.get("stylenumber"));
        newitem.setVendorNumber((String)ordermain_itemrow.get("vendornumber"));
        newitem.setClosureStyleNumber((String)ordermain_itemrow.get("closurestylenumber"));
        newitem.setVisorStyleNumber((String)ordermain_itemrow.get("visorstylenumber"));
        newitem.setSweatbandStyleNumber((String)ordermain_itemrow.get("sweatbandstylenumber"));
        newitem.setEyeletStyleNumber((String)ordermain_itemrow.get("eyeletstylenumber"));
        newitem.setColorCode((String)ordermain_itemrow.get("colorcode"));
        newitem.setFrontPanelColor((String)ordermain_itemrow.get("frontpanelcolor"));
        newitem.setSideBackPanelColor((String)ordermain_itemrow.get("sidebackpanelcolor"));
        newitem.setBackPanelColor((String)ordermain_itemrow.get("backpanelcolor"));
        newitem.setSidePanelColor((String)ordermain_itemrow.get("sidepanelcolor"));
        newitem.setPrimaryVisorColor((String)ordermain_itemrow.get("primaryvisorcolor"));
        newitem.setVisorTrimColor((String)ordermain_itemrow.get("visortrimcolor"));
        newitem.setVisorSandwichColor((String)ordermain_itemrow.get("visorsandwichcolor"));
        newitem.setUndervisorColor((String)ordermain_itemrow.get("undervisorcolor"));
        newitem.setDistressedVisorInsideColor((String)ordermain_itemrow.get("distressedvisorinsidecolor"));
        newitem.setClosureStrapColor((String)ordermain_itemrow.get("closurestrapcolor"));
        newitem.setFrontEyeletColor((String)ordermain_itemrow.get("eyeletcolor"));
        newitem.setSideBackEyeletColor((String)ordermain_itemrow.get("sidebackeyeletcolor"));
        newitem.setBackEyeletColor((String)ordermain_itemrow.get("backeyeletcolor"));
        newitem.setSideEyeletColor((String)ordermain_itemrow.get("sideeyeletcolor"));
        newitem.setButtonColor((String)ordermain_itemrow.get("buttoncolor"));
        newitem.setInnerTapingColor((String)ordermain_itemrow.get("innertapingcolor"));
        newitem.setSweatbandColor((String)ordermain_itemrow.get("sweatbandcolor"));
        newitem.setSweatbandStripeColor((String)ordermain_itemrow.get("sweatbandstripecolor"));
        newitem.setQuantity((Integer)ordermain_itemrow.get("quantity"));
        newitem.setSize((String)ordermain_itemrow.get("size"));
        newitem.setComments((String)ordermain_itemrow.get("comments"));
        newitem.setPolybag(((Boolean)ordermain_itemrow.get("polybag")).booleanValue());
        newitem.setPreviewFilename((String)ordermain_itemrow.get("previewfilename"));
        newitem.setRemoveInnerPrintedLabel(((Boolean)ordermain_itemrow.get("removeinnerprintedlabel")).booleanValue());
        newitem.setRemoveInnerWovenLabel(((Boolean)ordermain_itemrow.get("removeinnerwovenlabel")).booleanValue());
        newitem.setAddInnerPrintedLabel(((Boolean)ordermain_itemrow.get("addinnerprintedlabel")).booleanValue());
        newitem.setAddInnerWovenLabel(((Boolean)ordermain_itemrow.get("addinnerwovenlabel")).booleanValue());
        newitem.setSewingPatches(((Boolean)ordermain_itemrow.get("sewingpatches")).booleanValue());
        newitem.setHeatPressPatches(((Boolean)ordermain_itemrow.get("heatpresspatches")).booleanValue());
        newitem.setTagging(((Boolean)ordermain_itemrow.get("tagging")).booleanValue());
        newitem.setStickers(((Boolean)ordermain_itemrow.get("stickers")).booleanValue());
        newitem.setPersonalizationChanges((Integer)ordermain_itemrow.get("personalizationchanges"));
        newitem.setOakLeaves(((Boolean)ordermain_itemrow.get("oakleaves")).booleanValue());
        newitem.setFOBPrice((Double)ordermain_itemrow.get("fobprice"));
        newitem.setFOBMarkupPrice((Double)ordermain_itemrow.get("fobmarkupprice"));
        newitem.setProductSampleEmail(((Boolean)ordermain_itemrow.get("productsampleemail")).booleanValue());
        newitem.setProductSampleShip(((Boolean)ordermain_itemrow.get("productsampleship")).booleanValue());
        newitem.setProductSampleToDo((Integer)ordermain_itemrow.get("productsampletodo"));
        newitem.setProductSampleTotalDone((Integer)ordermain_itemrow.get("productsampletotaldone"));
        newitem.setProductSampleTotalShip((Integer)ordermain_itemrow.get("productsampletotalship"));
        newitem.setProductSampleTotalEmail((Integer)ordermain_itemrow.get("productsampletotalemail"));
        newitem.setSampleApprovedList((String)ordermain_itemrow.get("sampleapprovedlist"));
        newitem.setCustomStyleName((String)ordermain_itemrow.get("customstylename"));
        newitem.setHasPrivateLabel((Boolean)ordermain_itemrow.get("hasprivatelabel"));
        newitem.setHasPrivatePackaging((Boolean)ordermain_itemrow.get("hasprivatepackaging"));
        newitem.setHasPrivateShippingMark((Boolean)ordermain_itemrow.get("hasprivateshippingmark"));
        newitem.setArtworkType((String)ordermain_itemrow.get("artworktype"));
        newitem.setArtworkTypeComments((String)ordermain_itemrow.get("artworktypecomments"));
        
        newitem.setProfile((String)ordermain_itemrow.get("profile"));
        newitem.setNumberOfPanels((String)ordermain_itemrow.get("numberofpanels"));
        newitem.setCrownConstruction((String)ordermain_itemrow.get("crownconstruction"));
        newitem.setVisorRowStitching((String)ordermain_itemrow.get("visorrowstitching"));
        newitem.setFrontPanelFabric((String)ordermain_itemrow.get("frontpanelfabric"));
        newitem.setSideBackPanelFabric((String)ordermain_itemrow.get("sidebackpanelfabric"));
        newitem.setBackPanelFabric((String)ordermain_itemrow.get("backpanelfabric"));
        newitem.setSidePanelFabric((String)ordermain_itemrow.get("sidepanelfabric"));
        newitem.setPanelStitchColor((String)ordermain_itemrow.get("panelstitchcolor"));
        newitem.setVisorStitchColor((String)ordermain_itemrow.get("visorstitchcolor"));
        
        newitem.setProductDiscount((Double)ordermain_itemrow.get("productdiscount"));
        
        newset.addItem(newitem);
      }
      

      for (int j = 0; j < logocount; j++) {
        sqltable.makeTable("SELECT * FROM `ordermain_sets_logos` WHERE `hiddenkey` =" + hiddenkey + " AND `set` =" + i + " AND `logo` =" + j + " LIMIT 1");
        FastMap<Object> ordermain_item_logorow = sqltable.getRow();
        OrderDataLogo newlogo = new OrderDataLogo();
        int colorwaycount = ((Integer)ordermain_item_logorow.get("colorwaycount")).intValue();
        int decorationcount = ((Integer)ordermain_item_logorow.get("decorationoptionscount")).intValue();
        newlogo.setFilename((String)ordermain_item_logorow.get("filename"));
        newlogo.setLogoLocation((String)ordermain_item_logorow.get("logolocation"));
        newlogo.setDecoration((String)ordermain_item_logorow.get("decoration"));
        newlogo.setLogoSizeWidth((Double)ordermain_item_logorow.get("logosizewidth"));
        newlogo.setLogoSizeHeight((Double)ordermain_item_logorow.get("logosizeheight"));
        newlogo.setNumberOfColor((Integer)ordermain_item_logorow.get("numofcolor"));
        newlogo.setStitchCount((Integer)ordermain_item_logorow.get("stitchcount"));
        newlogo.setArtworkChargePerHour((Double)ordermain_item_logorow.get("artworkchargehour"));
        newlogo.setArtworkType((String)ordermain_item_logorow.get("artworktype"));
        newlogo.setArtworkTypeComments((String)ordermain_item_logorow.get("artworktypecomments"));
        newlogo.setDigitizing(((Boolean)ordermain_item_logorow.get("digitizing")).booleanValue());
        newlogo.setFilmCharge(((Boolean)ordermain_item_logorow.get("filmcharge")).booleanValue());
        newlogo.setPMSMatch(((Boolean)ordermain_item_logorow.get("pmsmatch")).booleanValue());
        newlogo.setTapeEdit(((Boolean)ordermain_item_logorow.get("tapeedit")).booleanValue());
        newlogo.setFlashCharge(((Boolean)ordermain_item_logorow.get("flashcharge")).booleanValue());
        newlogo.setMetallicThread(((Boolean)ordermain_item_logorow.get("specialthread")).booleanValue());
        newlogo.setNeonThread(((Boolean)ordermain_item_logorow.get("neonthread")).booleanValue());
        newlogo.setSpecialInk(((Boolean)ordermain_item_logorow.get("specialink")).booleanValue());
        newlogo.setColorChange(((Boolean)ordermain_item_logorow.get("colorchange")).booleanValue());
        newlogo.setColorChangeAmount((Integer)ordermain_item_logorow.get("colorchangeamount"));
        newlogo.setColorDescription((String)ordermain_item_logorow.get("colordescription"));
        newlogo.setThreadBrand((String)ordermain_item_logorow.get("threadbrand"));
        newlogo.setSwatchEmail(((Boolean)ordermain_item_logorow.get("swatchemail")).booleanValue());
        newlogo.setSwatchShip(((Boolean)ordermain_item_logorow.get("swatchship")).booleanValue());
        newlogo.setSwatchToDo((Integer)ordermain_item_logorow.get("swatchtodo"));
        newlogo.setSwatchTotalDone((Integer)ordermain_item_logorow.get("swatchtotaldone"));
        newlogo.setFilmSetupCharge((Double)ordermain_item_logorow.get("filmsetupcharge"));
        newlogo.setLogoSizeChoice((String)ordermain_item_logorow.get("logosizechoice"));
        newlogo.setDstFilename((String)ordermain_item_logorow.get("dstfilename"));
        newlogo.setNameDropLogo((String)ordermain_item_logorow.get("namedroplogo"));
        newlogo.setSwatchTotalShip((Integer)ordermain_item_logorow.get("swatchtotalship"));
        newlogo.setSwatchTotalEmail((Integer)ordermain_item_logorow.get("swatchtotalemail"));
        newlogo.setCustomerLogoPrice((Double)ordermain_item_logorow.get("customerlogoprice"));
        newlogo.setVendorLogoPrice((Double)ordermain_item_logorow.get("vendorlogoprice"));
        
        newset.addLogo(newlogo);
        
        for (int k = 0; k < colorwaycount; k++) {
          sqltable.makeTable("SELECT * FROM `ordermain_sets_logos_colorways` WHERE `hiddenkey` =" + hiddenkey + " AND `set` =" + i + " AND `logo` =" + j + " AND `colorway` =" + k + " LIMIT 1");
          FastMap<Object> ordermain_item_logo_colorwayrow = sqltable.getRow();
          OrderDataLogoColorway newcolorway = new OrderDataLogoColorway();
          newcolorway.setLogoColorCode((String)ordermain_item_logo_colorwayrow.get("logocolorcode"));
          newcolorway.setColorCodeComment((String)ordermain_item_logo_colorwayrow.get("colorcodecomment"));
          newcolorway.setThreadType((String)ordermain_item_logo_colorwayrow.get("threadtype"));
          newcolorway.setStitches((Integer)ordermain_item_logo_colorwayrow.get("stitches"));
          newcolorway.setInkType((String)ordermain_item_logo_colorwayrow.get("inktype"));
          newcolorway.setFlashCharge(((Boolean)ordermain_item_logo_colorwayrow.get("flashcharge")).booleanValue());
          
          newlogo.addColorway(newcolorway);
        }
        
        for (int k = 0; k < decorationcount; k++) {
          sqltable.makeTable("SELECT * FROM `ordermain_sets_logos_decorations` WHERE `hiddenkey` =" + hiddenkey + " AND `set` =" + i + " AND `logo` =" + j + " AND `decoration` =" + k + " LIMIT 1");
          FastMap<Object> ordermain_item_logo_decorationrow = sqltable.getRow();
          OrderDataLogoDecoration newdecoration = new OrderDataLogoDecoration();
          newdecoration.setName((String)ordermain_item_logo_decorationrow.get("name"));
          newdecoration.setField1((Double)ordermain_item_logo_decorationrow.get("field1"));
          newdecoration.setField2((Double)ordermain_item_logo_decorationrow.get("field2"));
          newdecoration.setField3((Double)ordermain_item_logo_decorationrow.get("field3"));
          newdecoration.setField4((Double)ordermain_item_logo_decorationrow.get("field4"));
          newdecoration.setComments((String)ordermain_item_logo_decorationrow.get("comments"));
          
          newlogo.addDecoration(newdecoration);
        }
      }
      
      this.orderdata.addSet(newset);
    }
    
    FastMap<Object> ordermain_vendorrow;
    if (this.orderdata.getOrderType().equals(com.ottocap.NewWorkFlow.client.OrderData.OrderData.OrderType.DOMESTIC))
    {
      if (sqltable.makeTable("SELECT * FROM `ordermain_vendors` WHERE `hiddenkey` =" + hiddenkey + " AND `vendortype` = 'digitize' LIMIT 1").booleanValue()) {
        FastMap<Object> ordermain_vendorrow = sqltable.getRow();
        this.orderdata.getVendorInformation().getDigitizerVendor().setVendor((Integer)ordermain_vendorrow.get("vendornumber"));
        this.orderdata.getVendorInformation().getDigitizerVendor().setWorkOrderNumber((String)ordermain_vendorrow.get("ponumber"));
        this.orderdata.getVendorInformation().getDigitizerVendor().setDigitizingProcessingDate((Date)ordermain_vendorrow.get("digitizingprocessingdate"));
        this.orderdata.getVendorInformation().getDigitizerVendor().setDigitizingDueDate((Date)ordermain_vendorrow.get("digitizingduedate"));
      }
      
      String ponumber = "";
      
      if (sqltable.makeTable("SELECT * FROM `ordermain_vendors` WHERE `hiddenkey` =" + hiddenkey + " AND `vendortype` = 'embroidery' LIMIT 1").booleanValue()) {
        FastMap<Object> ordermain_vendorrow = sqltable.getRow();
        this.orderdata.getVendorInformation().getEmbroideryVendor().setVendor((Integer)ordermain_vendorrow.get("vendornumber"));
        this.orderdata.getVendorInformation().getEmbroideryVendor().setWorkOrderNumber((String)ordermain_vendorrow.get("ponumber"));
        ponumber = (String)ordermain_vendorrow.get("ponumber");
        this.orderdata.getVendorInformation().getEmbroideryVendor().setWorkOrderProcessingDate((Date)ordermain_vendorrow.get("workorderprocessingdate"));
        this.orderdata.getVendorInformation().getEmbroideryVendor().setWorkOrderDueDate((Date)ordermain_vendorrow.get("workorderduedate"));
      }
      

      if (sqltable.makeTable("SELECT * FROM `ordermain_vendors` WHERE `hiddenkey` =" + hiddenkey + " AND `vendortype` = 'heattransfer' LIMIT 1").booleanValue()) {
        FastMap<Object> ordermain_vendorrow = sqltable.getRow();
        this.orderdata.getVendorInformation().getHeatTransferVendor().setVendor((Integer)ordermain_vendorrow.get("vendornumber"));
        this.orderdata.getVendorInformation().getHeatTransferVendor().setWorkOrderNumber((String)ordermain_vendorrow.get("ponumber"));
        this.orderdata.getVendorInformation().getHeatTransferVendor().setWorkOrderProcessingDate((Date)ordermain_vendorrow.get("workorderprocessingdate"));
        this.orderdata.getVendorInformation().getHeatTransferVendor().setWorkOrderDueDate((Date)ordermain_vendorrow.get("workorderduedate"));
      }
      

      if (sqltable.makeTable("SELECT * FROM `ordermain_vendors` WHERE `hiddenkey` =" + hiddenkey + " AND `vendortype` = 'patch' LIMIT 1").booleanValue()) {
        FastMap<Object> ordermain_vendorrow = sqltable.getRow();
        this.orderdata.getVendorInformation().getPatchVendor().setVendor((Integer)ordermain_vendorrow.get("vendornumber"));
        this.orderdata.getVendorInformation().getPatchVendor().setWorkOrderNumber((String)ordermain_vendorrow.get("ponumber"));
        this.orderdata.getVendorInformation().getPatchVendor().setWorkOrderProcessingDate((Date)ordermain_vendorrow.get("workorderprocessingdate"));
        this.orderdata.getVendorInformation().getPatchVendor().setWorkOrderDueDate((Date)ordermain_vendorrow.get("workorderduedate"));
      } else {
        this.orderdata.getVendorInformation().getPatchVendor().setWorkOrderNumber(ponumber);
      }
      

      if (sqltable.makeTable("SELECT * FROM `ordermain_vendors` WHERE `hiddenkey` =" + hiddenkey + " AND `vendortype` = 'cadprint' LIMIT 1").booleanValue()) {
        FastMap<Object> ordermain_vendorrow = sqltable.getRow();
        this.orderdata.getVendorInformation().getCADPrintVendor().setVendor((Integer)ordermain_vendorrow.get("vendornumber"));
        this.orderdata.getVendorInformation().getCADPrintVendor().setWorkOrderNumber((String)ordermain_vendorrow.get("ponumber"));
        this.orderdata.getVendorInformation().getCADPrintVendor().setWorkOrderProcessingDate((Date)ordermain_vendorrow.get("workorderprocessingdate"));
        this.orderdata.getVendorInformation().getCADPrintVendor().setWorkOrderDueDate((Date)ordermain_vendorrow.get("workorderduedate"));
      }
      

      if (sqltable.makeTable("SELECT * FROM `ordermain_vendors` WHERE `hiddenkey` =" + hiddenkey + " AND `vendortype` = 'screenprint' LIMIT 1").booleanValue()) {
        FastMap<Object> ordermain_vendorrow = sqltable.getRow();
        this.orderdata.getVendorInformation().getScreenPrintVendor().setVendor((Integer)ordermain_vendorrow.get("vendornumber"));
        this.orderdata.getVendorInformation().getScreenPrintVendor().setWorkOrderNumber((String)ordermain_vendorrow.get("ponumber"));
        this.orderdata.getVendorInformation().getScreenPrintVendor().setWorkOrderProcessingDate((Date)ordermain_vendorrow.get("workorderprocessingdate"));
        this.orderdata.getVendorInformation().getScreenPrintVendor().setWorkOrderDueDate((Date)ordermain_vendorrow.get("workorderduedate"));
      }
      

      if (sqltable.makeTable("SELECT * FROM `ordermain_vendors` WHERE `hiddenkey` =" + hiddenkey + " AND `vendortype` = 'directtogarment' LIMIT 1").booleanValue()) {
        ordermain_vendorrow = sqltable.getRow();
        this.orderdata.getVendorInformation().getDirectToGarmentVendor().setVendor((Integer)ordermain_vendorrow.get("vendornumber"));
        this.orderdata.getVendorInformation().getDirectToGarmentVendor().setWorkOrderNumber((String)ordermain_vendorrow.get("ponumber"));
        this.orderdata.getVendorInformation().getDirectToGarmentVendor().setWorkOrderProcessingDate((Date)ordermain_vendorrow.get("workorderprocessingdate"));
        this.orderdata.getVendorInformation().getDirectToGarmentVendor().setWorkOrderDueDate((Date)ordermain_vendorrow.get("workorderduedate"));
      }
    }
    else if (sqltable.makeTable("SELECT * FROM `ordermain_vendors` WHERE `hiddenkey` =" + hiddenkey + " AND `vendortype` = 'overseas'").booleanValue()) {
      for (FastMap<Object> currentvendor : sqltable.getTable()) {
        OrderDataVendorInformation newoverseasvendor = new OrderDataVendorInformation();
        newoverseasvendor.setVendor((Integer)currentvendor.get("vendornumber"));
        newoverseasvendor.setWorkOrderNumber((String)currentvendor.get("ponumber"));
        newoverseasvendor.setDigitizingProcessingDate((Date)currentvendor.get("digitizingprocessingdate"));
        newoverseasvendor.setDigitizingDueDate((Date)currentvendor.get("digitizingduedate"));
        newoverseasvendor.setWorkOrderProcessingDate((Date)currentvendor.get("workorderprocessingdate"));
        newoverseasvendor.setWorkOrderDueDate((Date)currentvendor.get("workorderduedate"));
        newoverseasvendor.setShippingMethod((String)currentvendor.get("shippingmethod"));
        
        this.orderdata.getVendorInformation().getOverseasVendor().put(String.valueOf(currentvendor.get("vendornumber")), newoverseasvendor);
      }
    }
    


    this.orderdata.setChangeHappened(false);
  }
  
  public OrderData getOrder() {
    return this.orderdata;
  }
  
  private void checkSession(int hiddenkey)
    throws Exception
  {
    if (this.httpsession.getAttribute("username") != null) {
      if ((((Integer)this.httpsession.getAttribute("level")).intValue() == 0) || (((Integer)this.httpsession.getAttribute("level")).intValue() == 3)) {
        this.sqltable.makeTable("SELECT `employeeid` FROM `ordermain` WHERE `hiddenkey` =" + hiddenkey + " LIMIT 1");
        if (!this.sqltable.getValue().equals(this.httpsession.getAttribute("username"))) {
          throw new Exception("This Order Does Not Belong To You");
        }
      } else if (((Integer)this.httpsession.getAttribute("level")).intValue() != 1)
      {
        if ((((Integer)this.httpsession.getAttribute("level")).intValue() != 2) && (((Integer)this.httpsession.getAttribute("level")).intValue() != 4) && (((Integer)this.httpsession.getAttribute("level")).intValue() != 5))
        {

          throw new Exception("Bad Session"); }
      }
    } else {
      throw new Exception("Session Expired");
    }
  }
}
