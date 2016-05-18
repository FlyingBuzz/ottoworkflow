package com.ottocap.NewWorkFlow.server.RPCService;

import com.ottocap.NewWorkFlow.client.OrderData.OrderData;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataAddress;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataCustomerInformation;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataItem;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogo;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataVendorInformation;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataVendors;
import com.ottocap.NewWorkFlow.server.JOOQSQL;
import java.util.Date;
import javax.servlet.http.HttpSession;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.jooq.SelectOffsetStep;
import org.jooq.SelectWhereStep;
import org.jooq.impl.DSL;

public class JOOQ_GetOrder
{
  OrderData orderdata = new OrderData();
  HttpSession httpsession;
  
  public JOOQ_GetOrder(int hiddenkey, HttpSession httpsession) throws Exception {
    this(hiddenkey, httpsession, Boolean.valueOf(true));
  }
  
  public JOOQ_GetOrder(int hiddenkey, HttpSession httpsession, Boolean checksession) throws Exception {
    this.httpsession = httpsession;
    if (checksession.booleanValue()) {
      checkSession(hiddenkey);
    }
    
    Record ordermainrow = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "ordermain" })).where(new Condition[] { DSL.fieldByName(new String[] { "hiddenkey" }).equal(Integer.valueOf(hiddenkey)) }).limit(1).fetchOne();
    this.orderdata.setHiddenKey((Integer)ordermainrow.getValue(DSL.fieldByName(new String[] { "hiddenkey" })));
    
    if (ordermainrow.getValue(DSL.fieldByName(new String[] { "ordertype" })).equals("Overseas")) {
      this.orderdata.setOrderType(com.ottocap.NewWorkFlow.client.OrderData.OrderData.OrderType.OVERSEAS);
    } else {
      this.orderdata.setOrderType(com.ottocap.NewWorkFlow.client.OrderData.OrderData.OrderType.DOMESTIC);
    }
    
    int setcount = ((Integer)ordermainrow.getValue(DSL.fieldByName(new String[] { "setcount" }))).intValue();
    int discountitemcount = ((Integer)ordermainrow.getValue(DSL.fieldByName(new String[] { "discountitemcount" }))).intValue();
    this.orderdata.getCustomerInformation().setEclipseAccountNumber((Integer)ordermainrow.getValue(DSL.fieldByName(new String[] { "eclipseaccountnumber" })));
    this.orderdata.getCustomerInformation().setContactName((String)ordermainrow.getValue(DSL.fieldByName(new String[] { "contactname" })));
    this.orderdata.getCustomerInformation().setCompany((String)ordermainrow.getValue(DSL.fieldByName(new String[] { "company" })));
    this.orderdata.getCustomerInformation().setCompanyLogo((String)ordermainrow.getValue(DSL.fieldByName(new String[] { "companylogo" })));
    this.orderdata.getCustomerInformation().setPhone((String)ordermainrow.getValue(DSL.fieldByName(new String[] { "phone" })));
    this.orderdata.getCustomerInformation().setFax((String)ordermainrow.getValue(DSL.fieldByName(new String[] { "fax" })));
    this.orderdata.getCustomerInformation().setEmail((String)ordermainrow.getValue(DSL.fieldByName(new String[] { "email" })));
    this.orderdata.getCustomerInformation().setTerms((String)ordermainrow.getValue(DSL.fieldByName(new String[] { "terms" })));
    this.orderdata.getCustomerInformation().getBillInformation().setCompany((String)ordermainrow.getValue(DSL.fieldByName(new String[] { "billcompany" })));
    this.orderdata.getCustomerInformation().getBillInformation().setStreetLine1((String)ordermainrow.getValue(DSL.fieldByName(new String[] { "billstreet1" })));
    this.orderdata.getCustomerInformation().getBillInformation().setStreetLine2((String)ordermainrow.getValue(DSL.fieldByName(new String[] { "billstreet2" })));
    this.orderdata.getCustomerInformation().getBillInformation().setCity((String)ordermainrow.getValue(DSL.fieldByName(new String[] { "billcity" })));
    this.orderdata.getCustomerInformation().getBillInformation().setState((String)ordermainrow.getValue(DSL.fieldByName(new String[] { "billstate" })));
    this.orderdata.getCustomerInformation().getBillInformation().setZip((String)ordermainrow.getValue(DSL.fieldByName(new String[] { "billzip" })));
    this.orderdata.getCustomerInformation().getBillInformation().setCountry((String)ordermainrow.getValue(DSL.fieldByName(new String[] { "billcountry" })));
    this.orderdata.getCustomerInformation().setShipAttention((String)ordermainrow.getValue(DSL.fieldByName(new String[] { "shipattn" })));
    this.orderdata.getCustomerInformation().setSameAsBillingAddress(((Byte)ordermainrow.getValue(DSL.fieldByName(new String[] { "sameasbillingaddress" }))).byteValue() != 0);
    this.orderdata.getCustomerInformation().setBlindShippingRequired(((Byte)ordermainrow.getValue(DSL.fieldByName(new String[] { "blindshippingrequired" }))).byteValue() != 0);
    this.orderdata.getCustomerInformation().setTaxExampt(((Byte)ordermainrow.getValue(DSL.fieldByName(new String[] { "taxexampt" }))).byteValue() != 0);
    this.orderdata.getCustomerInformation().setHaveDropShipment(((Byte)ordermainrow.getValue(DSL.fieldByName(new String[] { "havedropshipment" }))).byteValue() != 0);
    this.orderdata.getCustomerInformation().setDropShipmentAmount((Integer)ordermainrow.getValue(DSL.fieldByName(new String[] { "dropshipmentamount" })));
    this.orderdata.getCustomerInformation().getShipInformation().setCompany((String)ordermainrow.getValue(DSL.fieldByName(new String[] { "shipcompany" })));
    this.orderdata.getCustomerInformation().getShipInformation().setStreetLine1((String)ordermainrow.getValue(DSL.fieldByName(new String[] { "shipstreet1" })));
    this.orderdata.getCustomerInformation().getShipInformation().setStreetLine2((String)ordermainrow.getValue(DSL.fieldByName(new String[] { "shipstreet2" })));
    this.orderdata.getCustomerInformation().getShipInformation().setCity((String)ordermainrow.getValue(DSL.fieldByName(new String[] { "shipcity" })));
    this.orderdata.getCustomerInformation().getShipInformation().setState((String)ordermainrow.getValue(DSL.fieldByName(new String[] { "shipstate" })));
    this.orderdata.getCustomerInformation().getShipInformation().setZip((String)ordermainrow.getValue(DSL.fieldByName(new String[] { "shipzip" })));
    this.orderdata.getCustomerInformation().getShipInformation().setCountry((String)ordermainrow.getValue(DSL.fieldByName(new String[] { "shipcountry" })));
    this.orderdata.getCustomerInformation().getShipInformation().setResidential(((Byte)ordermainrow.getValue(DSL.fieldByName(new String[] { "shipresidential" }))).byteValue() != 0);
    this.orderdata.setOrderNumber((String)ordermainrow.getValue(DSL.fieldByName(new String[] { "ordernumber" })));
    this.orderdata.setCustomerPO((String)ordermainrow.getValue(DSL.fieldByName(new String[] { "customerpo" })));
    this.orderdata.setOrderDate((Date)ordermainrow.getValue(DSL.fieldByName(new String[] { "orderdate" })));
    this.orderdata.setOrderShipDate((Date)ordermainrow.getValue(DSL.fieldByName(new String[] { "ordershipdate" })));
    this.orderdata.setEstimatedShipDate((Date)ordermainrow.getValue(DSL.fieldByName(new String[] { "estimatedshipdate" })));
    this.orderdata.setInHandDate((Date)ordermainrow.getValue(DSL.fieldByName(new String[] { "inhanddate" })));
    this.orderdata.setRushOrder(((Byte)ordermainrow.getValue(DSL.fieldByName(new String[] { "rushorder" }))).byteValue() != 0);
    this.orderdata.setOrderStatus((String)ordermainrow.getValue(DSL.fieldByName(new String[] { "orderstatus" })));
    this.orderdata.setQuoteToOrder((String)ordermainrow.getValue(DSL.fieldByName(new String[] { "quotetoorder" })));
    this.orderdata.setSpecialNotes((String)ordermainrow.getValue(DSL.fieldByName(new String[] { "specialnotes" })));
    this.orderdata.setNextAction((String)ordermainrow.getValue(DSL.fieldByName(new String[] { "nextaction" })));
    this.orderdata.setInternalDueDateTime((Date)ordermainrow.getValue(DSL.fieldByName(new String[] { "internalduedatetime" })));
    this.orderdata.setInternalComments((String)ordermainrow.getValue(DSL.fieldByName(new String[] { "internalcomments" })));
    this.orderdata.setPotentialRepeatFrequency((String)ordermainrow.getValue(DSL.fieldByName(new String[] { "potentialrepeatfrequency" })));
    this.orderdata.setPotentialInternalComments((String)ordermainrow.getValue(DSL.fieldByName(new String[] { "potentialinternalcomments" })));
    this.orderdata.setPotentialRepeatDate((Date)ordermainrow.getValue(DSL.fieldByName(new String[] { "potentialrepeatdate" })));
    this.orderdata.setShippingType((String)ordermainrow.getValue(DSL.fieldByName(new String[] { "shippingtype" })));
    this.orderdata.setShippingCost((Double)ordermainrow.getValue(DSL.fieldByName(new String[] { "shippingcost" })));
    this.orderdata.setEmployeeId((String)ordermainrow.getValue(DSL.fieldByName(new String[] { "employeeid" })));
    
    for (int i = 0; i < discountitemcount; i++) {
      Record ordermain_discountitemrow = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "ordermain_discounts" })).where(new Condition[] { DSL.fieldByName(new String[] { "hiddenkey" }).equal(Integer.valueOf(hiddenkey)) }).and(DSL.fieldByName(new String[] { "discountitem" }).equal(Integer.valueOf(i))).limit(1).fetchOne();
      com.ottocap.NewWorkFlow.client.OrderData.OrderDataDiscount newdiscountitem = new com.ottocap.NewWorkFlow.client.OrderData.OrderDataDiscount();
      newdiscountitem.setAmount((Double)ordermain_discountitemrow.getValue(DSL.fieldByName(new String[] { "amount" })));
      newdiscountitem.setReason((String)ordermain_discountitemrow.getValue(DSL.fieldByName(new String[] { "reason" })));
      newdiscountitem.setIntoItems(Boolean.valueOf(((Byte)ordermain_discountitemrow.getValue(DSL.fieldByName(new String[] { "intoitems" }))).byteValue() != 0));
      this.orderdata.addDiscountItem(newdiscountitem);
    }
    com.ottocap.NewWorkFlow.client.OrderData.OrderDataSet newset;
    for (int i = 0; i < setcount; i++) {
      Record ordermain_setrow = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "ordermain_sets" })).where(new Condition[] { DSL.fieldByName(new String[] { "hiddenkey" }).equal(Integer.valueOf(hiddenkey)) }).and(DSL.fieldByName(new String[] { "set" }).equal(Integer.valueOf(i))).limit(1).fetchOne();
      newset = new com.ottocap.NewWorkFlow.client.OrderData.OrderDataSet();
      int itemcount = ((Integer)ordermain_setrow.getValue(DSL.fieldByName(new String[] { "itemcount" }))).intValue();
      int logocount = ((Integer)ordermain_setrow.getValue(DSL.fieldByName(new String[] { "logocount" }))).intValue();
      
      for (int j = 0; j < itemcount; j++) {
        Record ordermain_itemrow = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "ordermain_sets_items" })).where(new Condition[] { DSL.fieldByName(new String[] { "hiddenkey" }).equal(Integer.valueOf(hiddenkey)) }).and(DSL.fieldByName(new String[] { "set" }).equal(Integer.valueOf(i))).and(DSL.fieldByName(new String[] { "item" }).equal(Integer.valueOf(j))).limit(1).fetchOne();
        OrderDataItem newitem = new OrderDataItem();
        newitem.setStyleNumber((String)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "stylenumber" })));
        newitem.setVendorNumber((String)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "vendornumber" })));
        newitem.setClosureStyleNumber((String)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "closurestylenumber" })));
        newitem.setVisorStyleNumber((String)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "visorstylenumber" })));
        newitem.setSweatbandStyleNumber((String)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "sweatbandstylenumber" })));
        newitem.setEyeletStyleNumber((String)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "eyeletstylenumber" })));
        newitem.setColorCode((String)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "colorcode" })));
        newitem.setFrontPanelColor((String)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "frontpanelcolor" })));
        newitem.setSideBackPanelColor((String)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "sidebackpanelcolor" })));
        newitem.setBackPanelColor((String)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "backpanelcolor" })));
        newitem.setSidePanelColor((String)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "sidepanelcolor" })));
        newitem.setPrimaryVisorColor((String)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "primaryvisorcolor" })));
        newitem.setVisorTrimColor((String)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "visortrimcolor" })));
        newitem.setVisorSandwichColor((String)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "visorsandwichcolor" })));
        newitem.setUndervisorColor((String)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "undervisorcolor" })));
        newitem.setDistressedVisorInsideColor((String)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "distressedvisorinsidecolor" })));
        newitem.setClosureStrapColor((String)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "closurestrapcolor" })));
        newitem.setFrontEyeletColor((String)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "eyeletcolor" })));
        newitem.setSideBackEyeletColor((String)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "sidebackeyeletcolor" })));
        newitem.setBackEyeletColor((String)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "backeyeletcolor" })));
        newitem.setSideEyeletColor((String)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "sideeyeletcolor" })));
        newitem.setButtonColor((String)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "buttoncolor" })));
        newitem.setInnerTapingColor((String)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "innertapingcolor" })));
        newitem.setSweatbandColor((String)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "sweatbandcolor" })));
        newitem.setSweatbandStripeColor((String)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "sweatbandstripecolor" })));
        newitem.setQuantity((Integer)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "quantity" })));
        newitem.setSize((String)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "size" })));
        newitem.setComments((String)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "comments" })));
        newitem.setPolybag(((Byte)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "polybag" }))).byteValue() != 0);
        newitem.setPreviewFilename((String)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "previewfilename" })));
        newitem.setRemoveInnerPrintedLabel(((Byte)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "removeinnerprintedlabel" }))).byteValue() != 0);
        newitem.setRemoveInnerWovenLabel(((Byte)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "removeinnerwovenlabel" }))).byteValue() != 0);
        newitem.setAddInnerPrintedLabel(((Byte)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "addinnerprintedlabel" }))).byteValue() != 0);
        newitem.setAddInnerWovenLabel(((Byte)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "addinnerwovenlabel" }))).byteValue() != 0);
        newitem.setSewingPatches(((Byte)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "sewingpatches" }))).byteValue() != 0);
        newitem.setHeatPressPatches(((Byte)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "heatpresspatches" }))).byteValue() != 0);
        newitem.setTagging(((Byte)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "tagging" }))).byteValue() != 0);
        newitem.setStickers(((Byte)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "stickers" }))).byteValue() != 0);
        newitem.setPersonalizationChanges((Integer)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "personalizationchanges" })));
        newitem.setOakLeaves(((Byte)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "oakleaves" }))).byteValue() != 0);
        newitem.setFOBPrice((Double)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "fobprice" })));
        newitem.setProductSampleEmail(((Byte)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "productsampleemail" }))).byteValue() != 0);
        newitem.setProductSampleShip(((Byte)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "productsampleship" }))).byteValue() != 0);
        newitem.setProductSampleToDo((Integer)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "productsampletodo" })));
        newitem.setProductSampleTotalDone((Integer)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "productsampletotaldone" })));
        newitem.setProductSampleTotalShip((Integer)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "productsampletotalship" })));
        newitem.setProductSampleTotalEmail((Integer)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "productsampletotalemail" })));
        newitem.setSampleApprovedList((String)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "sampleapprovedlist" })));
        newitem.setCustomStyleName((String)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "customstylename" })));
        if ((Byte)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "hasprivatelabel" })) != null) {} newitem.setHasPrivateLabel(Boolean.valueOf(((Byte)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "hasprivatelabel" }))).byteValue() != 0));
        newitem.setArtworkType((String)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "artworktype" })));
        newitem.setArtworkTypeComments((String)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "artworktypecomments" })));
        
        newitem.setProfile((String)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "profile" })));
        newitem.setNumberOfPanels((String)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "numberofpanels" })));
        newitem.setCrownConstruction((String)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "crownconstruction" })));
        newitem.setVisorRowStitching((String)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "visorrowstitching" })));
        newitem.setFrontPanelFabric((String)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "frontpanelfabric" })));
        newitem.setSideBackPanelFabric((String)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "sidebackpanelfabric" })));
        newitem.setBackPanelFabric((String)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "backpanelfabric" })));
        newitem.setSidePanelFabric((String)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "sidepanelfabric" })));
        newitem.setPanelStitchColor((String)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "panelstitchcolor" })));
        newitem.setVisorStitchColor((String)ordermain_itemrow.getValue(DSL.fieldByName(new String[] { "visorstitchcolor" })));
        
        newset.addItem(newitem);
      }
      

      for (int j = 0; j < logocount; j++) {
        Record ordermain_item_logorow = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "ordermain_sets_logos" })).where(new Condition[] { DSL.fieldByName(new String[] { "hiddenkey" }).equal(Integer.valueOf(hiddenkey)) }).and(DSL.fieldByName(new String[] { "set" }).equal(Integer.valueOf(i))).and(DSL.fieldByName(new String[] { "logo" }).equal(Integer.valueOf(j))).limit(1).fetchOne();
        OrderDataLogo newlogo = new OrderDataLogo();
        int colorwaycount = ((Integer)ordermain_item_logorow.getValue(DSL.fieldByName(new String[] { "colorwaycount" }))).intValue();
        int decorationcount = ((Integer)ordermain_item_logorow.getValue(DSL.fieldByName(new String[] { "decorationoptionscount" }))).intValue();
        newlogo.setFilename((String)ordermain_item_logorow.getValue(DSL.fieldByName(new String[] { "filename" })));
        newlogo.setLogoLocation((String)ordermain_item_logorow.getValue(DSL.fieldByName(new String[] { "logolocation" })));
        newlogo.setDecoration((String)ordermain_item_logorow.getValue(DSL.fieldByName(new String[] { "decoration" })));
        newlogo.setLogoSizeWidth((Double)ordermain_item_logorow.getValue(DSL.fieldByName(new String[] { "logosizewidth" })));
        newlogo.setLogoSizeHeight((Double)ordermain_item_logorow.getValue(DSL.fieldByName(new String[] { "logosizeheight" })));
        newlogo.setNumberOfColor((Integer)ordermain_item_logorow.getValue(DSL.fieldByName(new String[] { "numofcolor" })));
        newlogo.setStitchCount((Integer)ordermain_item_logorow.getValue(DSL.fieldByName(new String[] { "stitchcount" })));
        newlogo.setArtworkChargePerHour((Double)ordermain_item_logorow.getValue(DSL.fieldByName(new String[] { "artworkchargehour" })));
        newlogo.setArtworkType((String)ordermain_item_logorow.getValue(DSL.fieldByName(new String[] { "artworktype" })));
        newlogo.setArtworkTypeComments((String)ordermain_item_logorow.getValue(DSL.fieldByName(new String[] { "artworktypecomments" })));
        newlogo.setDigitizing(((Byte)ordermain_item_logorow.getValue(DSL.fieldByName(new String[] { "digitizing" }))).byteValue() != 0);
        newlogo.setFilmCharge(((Byte)ordermain_item_logorow.getValue(DSL.fieldByName(new String[] { "filmcharge" }))).byteValue() != 0);
        newlogo.setPMSMatch(((Byte)ordermain_item_logorow.getValue(DSL.fieldByName(new String[] { "pmsmatch" }))).byteValue() != 0);
        newlogo.setTapeEdit(((Byte)ordermain_item_logorow.getValue(DSL.fieldByName(new String[] { "tapeedit" }))).byteValue() != 0);
        newlogo.setFlashCharge(((Byte)ordermain_item_logorow.getValue(DSL.fieldByName(new String[] { "flashcharge" }))).byteValue() != 0);
        newlogo.setMetallicThread(((Byte)ordermain_item_logorow.getValue(DSL.fieldByName(new String[] { "specialthread" }))).byteValue() != 0);
        newlogo.setSpecialInk(((Byte)ordermain_item_logorow.getValue(DSL.fieldByName(new String[] { "specialink" }))).byteValue() != 0);
        newlogo.setColorChange(((Byte)ordermain_item_logorow.getValue(DSL.fieldByName(new String[] { "colorchange" }))).byteValue() != 0);
        newlogo.setColorChangeAmount((Integer)ordermain_item_logorow.getValue(DSL.fieldByName(new String[] { "colorchangeamount" })));
        newlogo.setColorDescription((String)ordermain_item_logorow.getValue(DSL.fieldByName(new String[] { "colordescription" })));
        newlogo.setThreadBrand((String)ordermain_item_logorow.getValue(DSL.fieldByName(new String[] { "threadbrand" })));
        newlogo.setSwatchEmail(((Byte)ordermain_item_logorow.getValue(DSL.fieldByName(new String[] { "swatchemail" }))).byteValue() != 0);
        newlogo.setSwatchShip(((Byte)ordermain_item_logorow.getValue(DSL.fieldByName(new String[] { "swatchship" }))).byteValue() != 0);
        newlogo.setSwatchToDo((Integer)ordermain_item_logorow.getValue(DSL.fieldByName(new String[] { "swatchtodo" })));
        newlogo.setSwatchTotalDone((Integer)ordermain_item_logorow.getValue(DSL.fieldByName(new String[] { "swatchtotaldone" })));
        newlogo.setFilmSetupCharge((Double)ordermain_item_logorow.getValue(DSL.fieldByName(new String[] { "filmsetupcharge" })));
        newlogo.setLogoSizeChoice((String)ordermain_item_logorow.getValue(DSL.fieldByName(new String[] { "logosizechoice" })));
        newlogo.setDstFilename((String)ordermain_item_logorow.getValue(DSL.fieldByName(new String[] { "dstfilename" })));
        newlogo.setNameDropLogo((String)ordermain_item_logorow.getValue(DSL.fieldByName(new String[] { "namedroplogo" })));
        newlogo.setSwatchTotalShip((Integer)ordermain_item_logorow.getValue(DSL.fieldByName(new String[] { "swatchtotalship" })));
        newlogo.setSwatchTotalEmail((Integer)ordermain_item_logorow.getValue(DSL.fieldByName(new String[] { "swatchtotalemail" })));
        newlogo.setCustomerLogoPrice((Double)ordermain_item_logorow.getValue(DSL.fieldByName(new String[] { "customerlogoprice" })));
        newlogo.setVendorLogoPrice((Double)ordermain_item_logorow.getValue(DSL.fieldByName(new String[] { "vendorlogoprice" })));
        
        newset.addLogo(newlogo);
        
        for (int k = 0; k < colorwaycount; k++) {
          Record ordermain_item_logo_colorwayrow = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "ordermain_sets_logos_colorways" })).where(new Condition[] { DSL.fieldByName(new String[] { "hiddenkey" }).equal(Integer.valueOf(hiddenkey)) }).and(DSL.fieldByName(new String[] { "set" }).equal(Integer.valueOf(i))).and(DSL.fieldByName(new String[] { "logo" }).equal(Integer.valueOf(j))).and(DSL.fieldByName(new String[] { "colorway" }).equal(Integer.valueOf(k))).limit(1).fetchOne();
          com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogoColorway newcolorway = new com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogoColorway();
          newcolorway.setLogoColorCode((String)ordermain_item_logo_colorwayrow.getValue(DSL.fieldByName(new String[] { "logocolorcode" })));
          newcolorway.setColorCodeComment((String)ordermain_item_logo_colorwayrow.getValue(DSL.fieldByName(new String[] { "colorcodecomment" })));
          newcolorway.setThreadType((String)ordermain_item_logo_colorwayrow.getValue(DSL.fieldByName(new String[] { "threadtype" })));
          newcolorway.setStitches((Integer)ordermain_item_logo_colorwayrow.getValue(DSL.fieldByName(new String[] { "stitches" })));
          newcolorway.setInkType((String)ordermain_item_logo_colorwayrow.getValue(DSL.fieldByName(new String[] { "inktype" })));
          newcolorway.setFlashCharge(((Byte)ordermain_item_logo_colorwayrow.getValue(DSL.fieldByName(new String[] { "flashcharge" }))).byteValue() != 0);
          
          newlogo.addColorway(newcolorway);
        }
        
        for (int k = 0; k < decorationcount; k++) {
          Record ordermain_item_logo_decorationrow = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "ordermain_sets_logos_decorations" })).where(new Condition[] { DSL.fieldByName(new String[] { "hiddenkey" }).equal(Integer.valueOf(hiddenkey)) }).and(DSL.fieldByName(new String[] { "set" }).equal(Integer.valueOf(i))).and(DSL.fieldByName(new String[] { "logo" }).equal(Integer.valueOf(j))).and(DSL.fieldByName(new String[] { "decoration" }).equal(Integer.valueOf(k))).limit(1).fetchOne();
          com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogoDecoration newdecoration = new com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogoDecoration();
          newdecoration.setName((String)ordermain_item_logo_decorationrow.getValue(DSL.fieldByName(new String[] { "name" })));
          newdecoration.setField1((Double)ordermain_item_logo_decorationrow.getValue(DSL.fieldByName(new String[] { "field1" })));
          newdecoration.setField2((Double)ordermain_item_logo_decorationrow.getValue(DSL.fieldByName(new String[] { "field2" })));
          newdecoration.setField3((Double)ordermain_item_logo_decorationrow.getValue(DSL.fieldByName(new String[] { "field3" })));
          newdecoration.setField4((Double)ordermain_item_logo_decorationrow.getValue(DSL.fieldByName(new String[] { "field4" })));
          newdecoration.setComments((String)ordermain_item_logo_decorationrow.getValue(DSL.fieldByName(new String[] { "comments" })));
          
          newlogo.addDecoration(newdecoration);
        }
      }
      
      this.orderdata.addSet(newset);
    }
    

    if (this.orderdata.getOrderType().equals(com.ottocap.NewWorkFlow.client.OrderData.OrderData.OrderType.DOMESTIC))
    {


      Record ordermain_vendorrow = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "ordermain_vendors" })).where(new Condition[] { DSL.fieldByName(new String[] { "hiddenkey" }).equal(Integer.valueOf(hiddenkey)) }).and(DSL.fieldByName(new String[] { "vendortype" }).equal("digitize")).limit(1).fetchOne();
      if ((ordermain_vendorrow != null) && (ordermain_vendorrow.size() != 0)) {
        this.orderdata.getVendorInformation().getDigitizerVendor().setVendor((Integer)ordermain_vendorrow.getValue(DSL.fieldByName(new String[] { "vendornumber" })));
        this.orderdata.getVendorInformation().getDigitizerVendor().setWorkOrderNumber((String)ordermain_vendorrow.getValue(DSL.fieldByName(new String[] { "ponumber" })));
        this.orderdata.getVendorInformation().getDigitizerVendor().setDigitizingProcessingDate((Date)ordermain_vendorrow.getValue(DSL.fieldByName(new String[] { "digitizingprocessingdate" })));
        this.orderdata.getVendorInformation().getDigitizerVendor().setDigitizingDueDate((Date)ordermain_vendorrow.getValue(DSL.fieldByName(new String[] { "digitizingduedate" })));
      }
      
      String ponumber = "";
      
      ordermain_vendorrow = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "ordermain_vendors" })).where(new Condition[] { DSL.fieldByName(new String[] { "hiddenkey" }).equal(Integer.valueOf(hiddenkey)) }).and(DSL.fieldByName(new String[] { "vendortype" }).equal("embroidery")).limit(1).fetchOne();
      if ((ordermain_vendorrow != null) && (ordermain_vendorrow.size() != 0)) {
        this.orderdata.getVendorInformation().getEmbroideryVendor().setVendor((Integer)ordermain_vendorrow.getValue(DSL.fieldByName(new String[] { "vendornumber" })));
        this.orderdata.getVendorInformation().getEmbroideryVendor().setWorkOrderNumber((String)ordermain_vendorrow.getValue(DSL.fieldByName(new String[] { "ponumber" })));
        ponumber = (String)ordermain_vendorrow.getValue(DSL.fieldByName(new String[] { "ponumber" }));
        this.orderdata.getVendorInformation().getEmbroideryVendor().setWorkOrderProcessingDate((Date)ordermain_vendorrow.getValue(DSL.fieldByName(new String[] { "workorderprocessingdate" })));
        this.orderdata.getVendorInformation().getEmbroideryVendor().setWorkOrderDueDate((Date)ordermain_vendorrow.getValue(DSL.fieldByName(new String[] { "workorderduedate" })));
      }
      

      ordermain_vendorrow = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "ordermain_vendors" })).where(new Condition[] { DSL.fieldByName(new String[] { "hiddenkey" }).equal(Integer.valueOf(hiddenkey)) }).and(DSL.fieldByName(new String[] { "vendortype" }).equal("heattransfer")).limit(1).fetchOne();
      if ((ordermain_vendorrow != null) && (ordermain_vendorrow.size() != 0)) {
        this.orderdata.getVendorInformation().getHeatTransferVendor().setVendor((Integer)ordermain_vendorrow.getValue(DSL.fieldByName(new String[] { "vendornumber" })));
        this.orderdata.getVendorInformation().getHeatTransferVendor().setWorkOrderNumber((String)ordermain_vendorrow.getValue(DSL.fieldByName(new String[] { "ponumber" })));
        this.orderdata.getVendorInformation().getHeatTransferVendor().setWorkOrderProcessingDate((Date)ordermain_vendorrow.getValue(DSL.fieldByName(new String[] { "workorderprocessingdate" })));
        this.orderdata.getVendorInformation().getHeatTransferVendor().setWorkOrderDueDate((Date)ordermain_vendorrow.getValue(DSL.fieldByName(new String[] { "workorderduedate" })));
      }
      

      ordermain_vendorrow = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "ordermain_vendors" })).where(new Condition[] { DSL.fieldByName(new String[] { "hiddenkey" }).equal(Integer.valueOf(hiddenkey)) }).and(DSL.fieldByName(new String[] { "vendortype" }).equal("patch")).limit(1).fetchOne();
      if ((ordermain_vendorrow != null) && (ordermain_vendorrow.size() != 0)) {
        this.orderdata.getVendorInformation().getPatchVendor().setVendor((Integer)ordermain_vendorrow.getValue(DSL.fieldByName(new String[] { "vendornumber" })));
        this.orderdata.getVendorInformation().getPatchVendor().setWorkOrderNumber((String)ordermain_vendorrow.getValue(DSL.fieldByName(new String[] { "ponumber" })));
        this.orderdata.getVendorInformation().getPatchVendor().setWorkOrderProcessingDate((Date)ordermain_vendorrow.getValue(DSL.fieldByName(new String[] { "workorderprocessingdate" })));
        this.orderdata.getVendorInformation().getPatchVendor().setWorkOrderDueDate((Date)ordermain_vendorrow.getValue(DSL.fieldByName(new String[] { "workorderduedate" })));
      } else {
        this.orderdata.getVendorInformation().getPatchVendor().setWorkOrderNumber(ponumber);
      }
      

      ordermain_vendorrow = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "ordermain_vendors" })).where(new Condition[] { DSL.fieldByName(new String[] { "hiddenkey" }).equal(Integer.valueOf(hiddenkey)) }).and(DSL.fieldByName(new String[] { "vendortype" }).equal("screenprint")).limit(1).fetchOne();
      if ((ordermain_vendorrow != null) && (ordermain_vendorrow.size() != 0)) {
        this.orderdata.getVendorInformation().getScreenPrintVendor().setVendor((Integer)ordermain_vendorrow.getValue(DSL.fieldByName(new String[] { "vendornumber" })));
        this.orderdata.getVendorInformation().getScreenPrintVendor().setWorkOrderNumber((String)ordermain_vendorrow.getValue(DSL.fieldByName(new String[] { "ponumber" })));
        this.orderdata.getVendorInformation().getScreenPrintVendor().setWorkOrderProcessingDate((Date)ordermain_vendorrow.getValue(DSL.fieldByName(new String[] { "workorderprocessingdate" })));
        this.orderdata.getVendorInformation().getScreenPrintVendor().setWorkOrderDueDate((Date)ordermain_vendorrow.getValue(DSL.fieldByName(new String[] { "workorderduedate" })));
      }
      

      ordermain_vendorrow = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "ordermain_vendors" })).where(new Condition[] { DSL.fieldByName(new String[] { "hiddenkey" }).equal(Integer.valueOf(hiddenkey)) }).and(DSL.fieldByName(new String[] { "vendortype" }).equal("directtogarment")).limit(1).fetchOne();
      if ((ordermain_vendorrow != null) && (ordermain_vendorrow.size() != 0)) {
        this.orderdata.getVendorInformation().getDirectToGarmentVendor().setVendor((Integer)ordermain_vendorrow.getValue(DSL.fieldByName(new String[] { "vendornumber" })));
        this.orderdata.getVendorInformation().getDirectToGarmentVendor().setWorkOrderNumber((String)ordermain_vendorrow.getValue(DSL.fieldByName(new String[] { "ponumber" })));
        this.orderdata.getVendorInformation().getDirectToGarmentVendor().setWorkOrderProcessingDate((Date)ordermain_vendorrow.getValue(DSL.fieldByName(new String[] { "workorderprocessingdate" })));
        this.orderdata.getVendorInformation().getDirectToGarmentVendor().setWorkOrderDueDate((Date)ordermain_vendorrow.getValue(DSL.fieldByName(new String[] { "workorderduedate" })));
      }
    } else {
      org.jooq.Result<Record> currentvendors = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "ordermain_vendors" })).where(new Condition[] { DSL.fieldByName(new String[] { "hiddenkey" }).equal(Integer.valueOf(hiddenkey)) }).and(DSL.fieldByName(new String[] { "vendortype" }).equal("overseas")).fetch();
      if ((currentvendors != null) && (currentvendors.size() != 0)) {
        for (Record currentvendor : currentvendors) {
          OrderDataVendorInformation newoverseasvendor = new OrderDataVendorInformation();
          newoverseasvendor.setVendor((Integer)currentvendor.getValue(DSL.fieldByName(new String[] { "vendornumber" })));
          newoverseasvendor.setWorkOrderNumber((String)currentvendor.getValue(DSL.fieldByName(new String[] { "ponumber" })));
          newoverseasvendor.setDigitizingProcessingDate((Date)currentvendor.getValue(DSL.fieldByName(new String[] { "digitizingprocessingdate" })));
          newoverseasvendor.setDigitizingDueDate((Date)currentvendor.getValue(DSL.fieldByName(new String[] { "digitizingduedate" })));
          newoverseasvendor.setWorkOrderProcessingDate((Date)currentvendor.getValue(DSL.fieldByName(new String[] { "workorderprocessingdate" })));
          newoverseasvendor.setWorkOrderDueDate((Date)currentvendor.getValue(DSL.fieldByName(new String[] { "workorderduedate" })));
          newoverseasvendor.setShippingMethod((String)currentvendor.getValue(DSL.fieldByName(new String[] { "shippingmethod" })));
          
          this.orderdata.getVendorInformation().getOverseasVendor().put(String.valueOf(currentvendor.getValue(DSL.fieldByName(new String[] { "vendornumber" }))), newoverseasvendor);
        }
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
        Record employeeidrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "employeeid" })).from(new org.jooq.TableLike[] { DSL.tableByName(new String[] { "ordermain" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "hiddenkey" }).equal(Integer.valueOf(hiddenkey)) }).limit(1).fetchOne();
        if (employeeidrecord != null) if (!employeeidrecord.getValue(DSL.fieldByName(new String[] { "employeeid" })).equals(this.httpsession.getAttribute("username"))) {
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
