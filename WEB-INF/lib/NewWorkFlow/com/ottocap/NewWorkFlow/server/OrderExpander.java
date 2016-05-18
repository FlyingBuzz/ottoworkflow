package com.ottocap.NewWorkFlow.server;

import com.ottocap.NewWorkFlow.client.OrderData.OrderData;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData.OrderType;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataAddress;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataCustomerInformation;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataItem;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogo;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogoColorway;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogoDecoration;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataVendorInformation;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataVendors;
import com.ottocap.NewWorkFlow.client.StyleInformationData.StyleType;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderData;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataAddress;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataCustomerInformation;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataDiscount;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataItem;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataLogo;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataLogoColorway;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataLogoDecoration;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataSet;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataVendorInformation;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataVendors;
import java.util.TreeMap;
import java.util.TreeSet;

public class OrderExpander
{
  private ExtendOrderData expendedorderdata = new ExtendOrderData();
  private OTTOCodeToColorCached ottocodetocolorcache = null;
  SQLTable sqltable;
  
  public OrderExpander(int hiddenkey, javax.servlet.http.HttpSession httpsession, SQLTable sqltable) throws Exception {
    this.sqltable = sqltable;
    com.ottocap.NewWorkFlow.server.RPCService.GetOrder getorder = new com.ottocap.NewWorkFlow.server.RPCService.GetOrder(hiddenkey, httpsession, sqltable);
    OrderData theorderdata = getorder.getOrder();
    doOrderExpander(theorderdata);
  }
  
  public OrderExpander(OrderData theorderdata, SQLTable sqltable) throws Exception {
    this.sqltable = sqltable;
    doOrderExpander(theorderdata);
  }
  
  public void doOrderExpander(OrderData theorderdata) throws Exception
  {
    this.expendedorderdata.setCustomerPO(theorderdata.getCustomerPO());
    this.expendedorderdata.setEmployeeId(theorderdata.getEmployeeId());
    this.expendedorderdata.setEstimatedShipDate(theorderdata.getEstimatedShipDate());
    this.expendedorderdata.setHiddenKey(theorderdata.getHiddenKey());
    this.expendedorderdata.setInHandDate(theorderdata.getInHandDate());
    this.expendedorderdata.setInternalComments(theorderdata.getInternalComments());
    this.expendedorderdata.setInternalDueDateTime(theorderdata.getInternalDueDateTime());
    this.expendedorderdata.setNextAction(theorderdata.getNextAction());
    this.expendedorderdata.setOrderShipDate(theorderdata.getOrderShipDate());
    this.expendedorderdata.setOrderDate(theorderdata.getOrderDate());
    this.expendedorderdata.setOrderNumber(theorderdata.getOrderNumber());
    this.expendedorderdata.setOrderStatus(theorderdata.getOrderStatus());
    
    this.expendedorderdata.getCustomerInformation().setBlindShippingRequired(theorderdata.getCustomerInformation().getBlindShippingRequired());
    this.expendedorderdata.getCustomerInformation().setTaxExampt(theorderdata.getCustomerInformation().getTaxExampt());
    this.expendedorderdata.getCustomerInformation().setCompany(theorderdata.getCustomerInformation().getCompany());
    this.expendedorderdata.getCustomerInformation().setCompanyLogo(theorderdata.getCustomerInformation().getCompanyLogo());
    this.expendedorderdata.getCustomerInformation().setContactName(theorderdata.getCustomerInformation().getContactName());
    this.expendedorderdata.getCustomerInformation().setEclipseAccountNumber(theorderdata.getCustomerInformation().getEclipseAccountNumber());
    this.expendedorderdata.getCustomerInformation().setEmail(theorderdata.getCustomerInformation().getEmail());
    this.expendedorderdata.getCustomerInformation().setFax(theorderdata.getCustomerInformation().getFax());
    this.expendedorderdata.getCustomerInformation().setPhone(theorderdata.getCustomerInformation().getPhone());
    this.expendedorderdata.getCustomerInformation().setSameAsBillingAddress(theorderdata.getCustomerInformation().getSameAsBillingAddress());
    this.expendedorderdata.getCustomerInformation().setShipAttention(theorderdata.getCustomerInformation().getShipAttention());
    this.expendedorderdata.getCustomerInformation().setTerms(theorderdata.getCustomerInformation().getTerms());
    this.expendedorderdata.getCustomerInformation().setHaveDropShipment(theorderdata.getCustomerInformation().getHaveDropShipment());
    this.expendedorderdata.getCustomerInformation().setDropShipmentAmount(theorderdata.getCustomerInformation().getDropShipmentAmount());
    this.expendedorderdata.getCustomerInformation().getBillInformation().setCity(theorderdata.getCustomerInformation().getBillInformation().getCity());
    this.expendedorderdata.getCustomerInformation().getBillInformation().setCompany(theorderdata.getCustomerInformation().getBillInformation().getCompany());
    this.expendedorderdata.getCustomerInformation().getBillInformation().setCountry(theorderdata.getCustomerInformation().getBillInformation().getCountry());
    this.expendedorderdata.getCustomerInformation().getBillInformation().setResidential(theorderdata.getCustomerInformation().getBillInformation().getResidential());
    this.expendedorderdata.getCustomerInformation().getBillInformation().setState(theorderdata.getCustomerInformation().getBillInformation().getState());
    this.expendedorderdata.getCustomerInformation().getBillInformation().setStreetLine1(theorderdata.getCustomerInformation().getBillInformation().getStreetLine1());
    this.expendedorderdata.getCustomerInformation().getBillInformation().setStreetLine2(theorderdata.getCustomerInformation().getBillInformation().getStreetLine2());
    this.expendedorderdata.getCustomerInformation().getBillInformation().setZip(theorderdata.getCustomerInformation().getBillInformation().getZip());
    this.expendedorderdata.getCustomerInformation().getShipInformation().setCity(theorderdata.getCustomerInformation().getShipInformation().getCity());
    this.expendedorderdata.getCustomerInformation().getShipInformation().setCompany(theorderdata.getCustomerInformation().getShipInformation().getCompany());
    this.expendedorderdata.getCustomerInformation().getShipInformation().setCountry(theorderdata.getCustomerInformation().getShipInformation().getCountry());
    this.expendedorderdata.getCustomerInformation().getShipInformation().setResidential(theorderdata.getCustomerInformation().getShipInformation().getResidential());
    this.expendedorderdata.getCustomerInformation().getShipInformation().setState(theorderdata.getCustomerInformation().getShipInformation().getState());
    this.expendedorderdata.getCustomerInformation().getShipInformation().setStreetLine1(theorderdata.getCustomerInformation().getShipInformation().getStreetLine1());
    this.expendedorderdata.getCustomerInformation().getShipInformation().setStreetLine2(theorderdata.getCustomerInformation().getShipInformation().getStreetLine2());
    this.expendedorderdata.getCustomerInformation().getShipInformation().setZip(theorderdata.getCustomerInformation().getShipInformation().getZip());
    
    if (theorderdata.getOrderType() == OrderData.OrderType.DOMESTIC) {
      this.expendedorderdata.setOrderType(OrderData.OrderType.DOMESTIC);
    } else if (theorderdata.getOrderType() == OrderData.OrderType.OVERSEAS) {
      this.expendedorderdata.setOrderType(OrderData.OrderType.OVERSEAS);
    }
    this.expendedorderdata.setPotentialInternalComments(theorderdata.getPotentialInternalComments());
    this.expendedorderdata.setPotentialRepeatDate(theorderdata.getPotentialRepeatDate());
    this.expendedorderdata.setPotentialRepeatFrequency(theorderdata.getPotentialRepeatFrequency());
    this.expendedorderdata.setRushOrder(theorderdata.getRushOrder());
    this.expendedorderdata.setShippingCost(theorderdata.getShippingCost());
    this.expendedorderdata.setShippingType(theorderdata.getShippingType());
    this.expendedorderdata.setSpecialNotes(theorderdata.getSpecialNotes());
    
    OrderDataVendors theorderdatavendors = theorderdata.getVendorInformation();
    ExtendOrderDataVendors myvendors = this.expendedorderdata.getVendorInformation();
    myvendors.getDigitizerVendor().setDigitizingDueDate(theorderdatavendors.getDigitizerVendor().getDigitizingDueDate());
    myvendors.getDigitizerVendor().setDigitizingProcessingDate(theorderdatavendors.getDigitizerVendor().getDigitizingProcessingDate());
    myvendors.getDigitizerVendor().setShippingMethod(theorderdatavendors.getDigitizerVendor().getShippingMethod());
    myvendors.getDigitizerVendor().setVendor(theorderdatavendors.getDigitizerVendor().getVendor());
    myvendors.getDigitizerVendor().setWorkOrderDueDate(theorderdatavendors.getDigitizerVendor().getWorkOrderDueDate());
    myvendors.getDigitizerVendor().setWorkOrderNumber(theorderdatavendors.getDigitizerVendor().getWorkOrderNumber());
    myvendors.getDigitizerVendor().setWorkOrderProcessingDate(theorderdatavendors.getDigitizerVendor().getWorkOrderProcessingDate());
    
    myvendors.getDirectToGarmentVendor().setDigitizingDueDate(theorderdatavendors.getDirectToGarmentVendor().getDigitizingDueDate());
    myvendors.getDirectToGarmentVendor().setDigitizingProcessingDate(theorderdatavendors.getDirectToGarmentVendor().getDigitizingProcessingDate());
    myvendors.getDirectToGarmentVendor().setShippingMethod(theorderdatavendors.getDirectToGarmentVendor().getShippingMethod());
    myvendors.getDirectToGarmentVendor().setVendor(theorderdatavendors.getDirectToGarmentVendor().getVendor());
    myvendors.getDirectToGarmentVendor().setWorkOrderDueDate(theorderdatavendors.getDirectToGarmentVendor().getWorkOrderDueDate());
    myvendors.getDirectToGarmentVendor().setWorkOrderNumber(theorderdatavendors.getDirectToGarmentVendor().getWorkOrderNumber());
    myvendors.getDirectToGarmentVendor().setWorkOrderProcessingDate(theorderdatavendors.getDirectToGarmentVendor().getWorkOrderProcessingDate());
    
    myvendors.getEmbroideryVendor().setDigitizingDueDate(theorderdatavendors.getEmbroideryVendor().getDigitizingDueDate());
    myvendors.getEmbroideryVendor().setDigitizingProcessingDate(theorderdatavendors.getEmbroideryVendor().getDigitizingProcessingDate());
    myvendors.getEmbroideryVendor().setShippingMethod(theorderdatavendors.getEmbroideryVendor().getShippingMethod());
    myvendors.getEmbroideryVendor().setVendor(theorderdatavendors.getEmbroideryVendor().getVendor());
    myvendors.getEmbroideryVendor().setWorkOrderDueDate(theorderdatavendors.getEmbroideryVendor().getWorkOrderDueDate());
    myvendors.getEmbroideryVendor().setWorkOrderNumber(theorderdatavendors.getEmbroideryVendor().getWorkOrderNumber());
    myvendors.getEmbroideryVendor().setWorkOrderProcessingDate(theorderdatavendors.getEmbroideryVendor().getWorkOrderProcessingDate());
    
    myvendors.getHeatTransferVendor().setDigitizingDueDate(theorderdatavendors.getHeatTransferVendor().getDigitizingDueDate());
    myvendors.getHeatTransferVendor().setDigitizingProcessingDate(theorderdatavendors.getHeatTransferVendor().getDigitizingProcessingDate());
    myvendors.getHeatTransferVendor().setShippingMethod(theorderdatavendors.getHeatTransferVendor().getShippingMethod());
    myvendors.getHeatTransferVendor().setVendor(theorderdatavendors.getHeatTransferVendor().getVendor());
    myvendors.getHeatTransferVendor().setWorkOrderDueDate(theorderdatavendors.getHeatTransferVendor().getWorkOrderDueDate());
    myvendors.getHeatTransferVendor().setWorkOrderNumber(theorderdatavendors.getHeatTransferVendor().getWorkOrderNumber());
    myvendors.getHeatTransferVendor().setWorkOrderProcessingDate(theorderdatavendors.getHeatTransferVendor().getWorkOrderProcessingDate());
    
    myvendors.getPatchVendor().setDigitizingDueDate(theorderdatavendors.getPatchVendor().getDigitizingDueDate());
    myvendors.getPatchVendor().setDigitizingProcessingDate(theorderdatavendors.getPatchVendor().getDigitizingProcessingDate());
    myvendors.getPatchVendor().setShippingMethod(theorderdatavendors.getPatchVendor().getShippingMethod());
    myvendors.getPatchVendor().setVendor(theorderdatavendors.getPatchVendor().getVendor());
    myvendors.getPatchVendor().setWorkOrderDueDate(theorderdatavendors.getPatchVendor().getWorkOrderDueDate());
    myvendors.getPatchVendor().setWorkOrderNumber(theorderdatavendors.getPatchVendor().getWorkOrderNumber());
    myvendors.getPatchVendor().setWorkOrderProcessingDate(theorderdatavendors.getPatchVendor().getWorkOrderProcessingDate());
    
    myvendors.getCADPrintVendor().setDigitizingDueDate(theorderdatavendors.getCADPrintVendor().getDigitizingDueDate());
    myvendors.getCADPrintVendor().setDigitizingProcessingDate(theorderdatavendors.getCADPrintVendor().getDigitizingProcessingDate());
    myvendors.getCADPrintVendor().setShippingMethod(theorderdatavendors.getCADPrintVendor().getShippingMethod());
    myvendors.getCADPrintVendor().setVendor(theorderdatavendors.getCADPrintVendor().getVendor());
    myvendors.getCADPrintVendor().setWorkOrderDueDate(theorderdatavendors.getCADPrintVendor().getWorkOrderDueDate());
    myvendors.getCADPrintVendor().setWorkOrderNumber(theorderdatavendors.getCADPrintVendor().getWorkOrderNumber());
    myvendors.getCADPrintVendor().setWorkOrderProcessingDate(theorderdatavendors.getCADPrintVendor().getWorkOrderProcessingDate());
    
    myvendors.getScreenPrintVendor().setDigitizingDueDate(theorderdatavendors.getScreenPrintVendor().getDigitizingDueDate());
    myvendors.getScreenPrintVendor().setDigitizingProcessingDate(theorderdatavendors.getScreenPrintVendor().getDigitizingProcessingDate());
    myvendors.getScreenPrintVendor().setShippingMethod(theorderdatavendors.getScreenPrintVendor().getShippingMethod());
    myvendors.getScreenPrintVendor().setVendor(theorderdatavendors.getScreenPrintVendor().getVendor());
    myvendors.getScreenPrintVendor().setWorkOrderDueDate(theorderdatavendors.getScreenPrintVendor().getWorkOrderDueDate());
    myvendors.getScreenPrintVendor().setWorkOrderNumber(theorderdatavendors.getScreenPrintVendor().getWorkOrderNumber());
    myvendors.getScreenPrintVendor().setWorkOrderProcessingDate(theorderdatavendors.getScreenPrintVendor().getWorkOrderProcessingDate());
    
    String[] theoverseasvendorkeys = (String[])theorderdatavendors.getOverseasVendor().keySet().toArray(new String[0]);
    for (int i = 0; i < theoverseasvendorkeys.length; i++) {
      ExtendOrderDataVendorInformation myvendorinfo = new ExtendOrderDataVendorInformation();
      myvendorinfo.setDigitizingDueDate(((OrderDataVendorInformation)theorderdatavendors.getOverseasVendor().get(theoverseasvendorkeys[i])).getDigitizingDueDate());
      myvendorinfo.setDigitizingProcessingDate(((OrderDataVendorInformation)theorderdatavendors.getOverseasVendor().get(theoverseasvendorkeys[i])).getDigitizingProcessingDate());
      myvendorinfo.setShippingMethod(((OrderDataVendorInformation)theorderdatavendors.getOverseasVendor().get(theoverseasvendorkeys[i])).getShippingMethod());
      myvendorinfo.setVendor(((OrderDataVendorInformation)theorderdatavendors.getOverseasVendor().get(theoverseasvendorkeys[i])).getVendor());
      myvendorinfo.setWorkOrderDueDate(((OrderDataVendorInformation)theorderdatavendors.getOverseasVendor().get(theoverseasvendorkeys[i])).getWorkOrderDueDate());
      myvendorinfo.setWorkOrderNumber(((OrderDataVendorInformation)theorderdatavendors.getOverseasVendor().get(theoverseasvendorkeys[i])).getWorkOrderNumber());
      myvendorinfo.setWorkOrderProcessingDate(((OrderDataVendorInformation)theorderdatavendors.getOverseasVendor().get(theoverseasvendorkeys[i])).getWorkOrderProcessingDate());
      myvendors.getOverseasVendor().put(theoverseasvendorkeys[i], myvendorinfo);
    }
    
    for (int i = 0; i < theorderdata.getDiscountItemCount(); i++) {
      com.ottocap.NewWorkFlow.client.OrderData.OrderDataDiscount theorderdatadiscount = theorderdata.getDiscountItem(i);
      ExtendOrderDataDiscount mydiscount = new ExtendOrderDataDiscount();
      mydiscount.setAmount(theorderdatadiscount.getAmount());
      mydiscount.setReason(theorderdatadiscount.getReason());
      mydiscount.setIntoItems(theorderdatadiscount.getIntoItems());
      this.expendedorderdata.addDiscountItem(mydiscount);
    }
    
    TreeMap<Integer, TreeSet<String>> vendoralreadydig = new TreeMap();
    for (int i = 0; i < theorderdata.getSetCount(); i++)
    {
      com.ottocap.NewWorkFlow.client.OrderData.OrderDataSet theorderdataset = theorderdata.getSet(i);
      for (int j = 0; j < theorderdataset.getItemCount(); j++) {
        OrderDataItem theorderdataitem = theorderdataset.getItem(j);
        ExtendOrderDataSet myset = new ExtendOrderDataSet();
        
        ExtendOrderDataItem myitem = new ExtendOrderDataItem();
        myitem.setOrgSetNum(i);
        myitem.setOrgItemNum(j);
        myitem.setAddInnerPrintedLabel(theorderdataitem.getAddInnerPrintedLabel());
        myitem.setAddInnerWovenLabel(theorderdataitem.getAddInnerWovenLabel());
        myitem.setSideBackPanelColor(theorderdataitem.getSideBackPanelColor());
        myitem.setBackPanelColor(theorderdataitem.getBackPanelColor());
        myitem.setSidePanelColor(theorderdataitem.getSidePanelColor());
        myitem.setButtonColor(theorderdataitem.getButtonColor());
        myitem.setClosureStrapColor(theorderdataitem.getClosureStrapColor());
        myitem.setClosureStyleNumber((theorderdataitem.getClosureStyleNumber() != null) && (!theorderdataitem.getClosureStyleNumber().equals("Default")) ? theorderdataitem.getClosureStyleNumber() : "");
        myitem.setColorCode(theorderdataitem.getColorCode());
        myitem.setComments(theorderdataitem.getComments());
        myitem.setCustomStyleName(theorderdataitem.getCustomStyleName());
        myitem.setDistressedVisorInsideColor(theorderdataitem.getDistressedVisorInsideColor());
        myitem.setEyeletStyleNumber((theorderdataitem.getEyeletStyleNumber() != null) && (!theorderdataitem.getEyeletStyleNumber().equals("Default")) ? theorderdataitem.getEyeletStyleNumber() : "");
        myitem.setFOBPrice(theorderdataitem.getFOBPrice());
        myitem.setFOBMarkupPrice(theorderdataitem.getFOBMarkupPrice());
        myitem.setFrontEyeletColor(theorderdataitem.getFrontEyeletColor());
        myitem.setFrontPanelColor(theorderdataitem.getFrontPanelColor());
        myitem.setHeatPressPatches(theorderdataitem.getHeatPressPatches());
        myitem.setInnerTapingColor(theorderdataitem.getInnerTapingColor());
        myitem.setOakLeaves(theorderdataitem.getOakLeaves());
        myitem.setPersonalizationChanges(theorderdataitem.getPersonalizationChanges());
        myitem.setPolybag(theorderdataitem.getPolybag());
        myitem.setPreviewFilename(theorderdataitem.getPreviewFilename());
        myitem.setPrimaryVisorColor(theorderdataitem.getPrimaryVisorColor());
        myitem.setProductSampleEmail(theorderdataitem.getProductSampleEmail());
        myitem.setProductSampleShip(theorderdataitem.getProductSampleShip());
        myitem.setProductSampleToDo(theorderdataitem.getProductSampleToDo());
        myitem.setProductSampleTotalDone(theorderdataitem.getProductSampleTotalDone());
        myitem.setProductSampleTotalEmail(theorderdataitem.getProductSampleTotalEmail());
        myitem.setProductSampleTotalShip(theorderdataitem.getProductSampleTotalShip());
        myitem.setSampleApprovedList(theorderdataitem.getSampleApprovedList());
        myitem.setQuantity(theorderdataitem.getQuantity());
        myitem.setRemoveInnerPrintedLabel(theorderdataitem.getRemoveInnerPrintedLabel());
        myitem.setRemoveInnerWovenLabel(theorderdataitem.getRemoveInnerWovenLabel());
        myitem.setSewingPatches(theorderdataitem.getSewingPatches());
        myitem.setSideBackEyeletColor(theorderdataitem.getSideBackEyeletColor());
        myitem.setBackEyeletColor(theorderdataitem.getBackEyeletColor());
        myitem.setSideEyeletColor(theorderdataitem.getSideEyeletColor());
        myitem.setSize(theorderdataitem.getSize());
        myitem.setStyleNumber(theorderdataitem.getStyleNumber());
        myitem.setVendorNumber(theorderdataitem.getVendorNumber());
        myitem.setSweatbandColor(theorderdataitem.getSweatbandColor());
        myitem.setSweatbandStripeColor(theorderdataitem.getSweatbandStripeColor());
        myitem.setSweatbandStyleNumber(theorderdataitem.getSweatbandStyleNumber());
        myitem.setTagging(theorderdataitem.getTagging());
        myitem.setStickers(theorderdataitem.getStickers());
        myitem.setUndervisorColor(theorderdataitem.getUndervisorColor());
        myitem.setVisorSandwichColor(theorderdataitem.getVisorSandwichColor());
        myitem.setVisorStyleNumber(theorderdataitem.getVisorStyleNumber());
        myitem.setVisorTrimColor(theorderdataitem.getVisorTrimColor());
        myitem.setArtworkType(theorderdataitem.getArtworkType());
        myitem.setArtworkTypeComments(theorderdataitem.getArtworkTypeComments());
        myitem.setHasPrivateLabel(theorderdataitem.getHasPrivateLabel());
        myitem.setHasPrivatePackaging(theorderdataitem.getHasPrivatePackaging());
        myitem.setHasPrivateShippingMark(theorderdataitem.getHasPrivateShippingMark());
        
        myitem.setProfile(theorderdataitem.getProfile());
        myitem.setNumberOfPanels(theorderdataitem.getNumberOfPanels());
        myitem.setCrownConstruction(theorderdataitem.getCrownConstruction());
        myitem.setVisorRowStitching(theorderdataitem.getVisorRowStitching());
        myitem.setFrontPanelFabric(theorderdataitem.getFrontPanelFabric());
        myitem.setSideBackPanelFabric(theorderdataitem.getSideBackPanelFabric());
        myitem.setBackPanelFabric(theorderdataitem.getBackPanelFabric());
        myitem.setSidePanelFabric(theorderdataitem.getSidePanelFabric());
        myitem.setPanelStitchColor(theorderdataitem.getPanelStitchColor());
        myitem.setVisorStitchColor(theorderdataitem.getVisorStitchColor());
        
        myitem.setProductDiscount(theorderdataitem.getProductDiscount());
        
        myset.addItem(myitem);
        
        for (int k = 0; k < theorderdataset.getLogoCount(); k++) {
          OrderDataLogo theorderdatalogo = theorderdataset.getLogo(k);
          ExtendOrderDataLogo mylogo = new ExtendOrderDataLogo();
          mylogo.setArtworkType(theorderdatalogo.getArtworkType());
          mylogo.setArtworkTypeComments(theorderdatalogo.getArtworkTypeComments());
          mylogo.setColorDescription(theorderdatalogo.getColorDescription());
          mylogo.setDecoration(theorderdatalogo.getDecoration());
          mylogo.setDstFilename(theorderdatalogo.getDstFilename());
          mylogo.setFilename(theorderdatalogo.getFilename());
          mylogo.setLogoLocation(theorderdatalogo.getLogoLocation());
          mylogo.setLogoSizeHeight(theorderdatalogo.getLogoSizeHeight());
          mylogo.setLogoSizeWidth(theorderdatalogo.getLogoSizeWidth());
          mylogo.setNameDropLogo(theorderdatalogo.getNameDropLogo());
          mylogo.setNumberOfColor(theorderdatalogo.getNumberOfColor());
          mylogo.setLogoSizeChoice(theorderdatalogo.getLogoSizeChoice());
          
          if ((theorderdata.getOrderType() == OrderData.OrderType.OVERSEAS) || (theorderdatalogo.getDecoration() == null)) {
            mylogo.setStitchCount(theorderdatalogo.getStitchCount());
            mylogo.setSpecialInk(theorderdatalogo.getSpecialInk());
            mylogo.setMetallicThread(theorderdatalogo.getMetallicThread());
            mylogo.setNeonThread(theorderdatalogo.getNeonThread());
          }
          else if (theorderdatalogo.getDecoration().equals("CAD Print")) {
            mylogo.setStitchCount(null);
            mylogo.setSpecialInk(false);
            mylogo.setMetallicThread(false);
            mylogo.setNeonThread(false);
          }
          else if ((theorderdatalogo.getDecoration().equals("Screen Print")) || (theorderdatalogo.getDecoration().equals("Four Color Screen Print"))) {
            mylogo.setStitchCount(null);
            mylogo.setSpecialInk(false);
            mylogo.setMetallicThread(false);
            mylogo.setNeonThread(false);
          }
          else if (theorderdatalogo.getDecoration().equals("Heat Transfer")) {
            mylogo.setStitchCount(null);
            mylogo.setSpecialInk(theorderdatalogo.getSpecialInk());
            mylogo.setMetallicThread(false);
            mylogo.setNeonThread(false);
            mylogo.setFlashCharge(theorderdatalogo.getFlashCharge());
          }
          else if (theorderdatalogo.getDecoration().equals("Direct To Garment")) {
            mylogo.setStitchCount(null);
            mylogo.setSpecialInk(false);
            mylogo.setMetallicThread(false);
            mylogo.setNeonThread(false);
          }
          else if ((theorderdatalogo.getDecoration().equals("Flat Embroidery")) || (theorderdatalogo.getDecoration().equals("3D Embroidery"))) {
            mylogo.setStitchCount(theorderdatalogo.getStitchCount());
            mylogo.setSpecialInk(false);
            mylogo.setMetallicThread(theorderdatalogo.getMetallicThread());
            mylogo.setNeonThread(theorderdatalogo.getNeonThread());
          }
          else {
            mylogo.setStitchCount(Integer.valueOf(0));
            mylogo.setSpecialInk(false);
            mylogo.setMetallicThread(false);
            mylogo.setNeonThread(false);
          }
          

          mylogo.setCustomerLogoPrice(theorderdatalogo.getCustomerLogoPrice());
          mylogo.setVendorLogoPrice(theorderdatalogo.getVendorLogoPrice());
          if (j == 0) {
            mylogo.setColorChange(theorderdatalogo.getColorChange());
            mylogo.setColorChangeAmount(theorderdatalogo.getColorChangeAmount());
            mylogo.setArtworkChargePerHour(theorderdatalogo.getArtworkChargePerHour());
            mylogo.setSwatchEmail(theorderdatalogo.getSwatchEmail());
            mylogo.setSwatchShip(theorderdatalogo.getSwatchShip());
            mylogo.setSwatchToDo(theorderdatalogo.getSwatchToDo());
            mylogo.setSwatchTotalDone(theorderdatalogo.getSwatchTotalDone());
            mylogo.setSwatchTotalEmail(theorderdatalogo.getSwatchTotalEmail());
            mylogo.setSwatchTotalShip(theorderdatalogo.getSwatchTotalShip());
            
            if ((theorderdata.getOrderType() == OrderData.OrderType.OVERSEAS) || (theorderdatalogo.getDecoration() == null)) {
              mylogo.setDigitizing(theorderdatalogo.getDigitizing());
              mylogo.setFilmSetupCharge(theorderdatalogo.getFilmSetupCharge());
              mylogo.setFilmCharge(theorderdatalogo.getFilmCharge());
              mylogo.setPMSMatch(theorderdatalogo.getPMSMatch());
              mylogo.setFlashCharge(theorderdatalogo.getFlashCharge());
              mylogo.setTapeEdit(theorderdatalogo.getTapeEdit());
            } else if (theorderdatalogo.getDecoration().equals("CAD Print")) {
              mylogo.setDigitizing(false);
              mylogo.setFilmSetupCharge(theorderdatalogo.getFilmSetupCharge());
              mylogo.setFilmCharge(theorderdatalogo.getFilmCharge());
              mylogo.setPMSMatch(theorderdatalogo.getPMSMatch());
              mylogo.setFlashCharge(theorderdatalogo.getFlashCharge());
              mylogo.setTapeEdit(false);
            } else if ((theorderdatalogo.getDecoration().equals("Screen Print")) || (theorderdatalogo.getDecoration().equals("Four Color Screen Print"))) {
              mylogo.setDigitizing(false);
              mylogo.setFilmSetupCharge(theorderdatalogo.getFilmSetupCharge());
              mylogo.setFilmCharge(theorderdatalogo.getFilmCharge());
              mylogo.setPMSMatch(theorderdatalogo.getPMSMatch());
              mylogo.setFlashCharge(theorderdatalogo.getFlashCharge());
              mylogo.setTapeEdit(false);
            } else if (theorderdatalogo.getDecoration().equals("Heat Transfer")) {
              mylogo.setDigitizing(false);
              mylogo.setFilmSetupCharge(theorderdatalogo.getFilmSetupCharge());
              mylogo.setFilmCharge(theorderdatalogo.getFilmCharge());
              mylogo.setPMSMatch(false);
              mylogo.setTapeEdit(false);
            } else if (theorderdatalogo.getDecoration().equals("Direct To Garment")) {
              mylogo.setDigitizing(false);
              mylogo.setFilmSetupCharge(Double.valueOf(0.0D));
              mylogo.setFilmCharge(false);
              mylogo.setPMSMatch(false);
              mylogo.setFlashCharge(false);
              mylogo.setTapeEdit(false);
              mylogo.setColorChangeAmount(null);
            } else if ((theorderdatalogo.getDecoration().equals("Flat Embroidery")) || (theorderdatalogo.getDecoration().equals("3D Embroidery"))) {
              mylogo.setDigitizing(theorderdatalogo.getDigitizing());
              mylogo.setFilmSetupCharge(null);
              mylogo.setFilmCharge(false);
              mylogo.setPMSMatch(false);
              mylogo.setFlashCharge(false);
              mylogo.setTapeEdit(theorderdatalogo.getTapeEdit());
            } else {
              mylogo.setDigitizing(false);
              mylogo.setFilmSetupCharge(null);
              mylogo.setFilmCharge(false);
              mylogo.setPMSMatch(false);
              mylogo.setFlashCharge(false);
              mylogo.setTapeEdit(false);
            }
          }
          if ((theorderdata.getOrderType() == OrderData.OrderType.OVERSEAS) && 
            (theorderdatalogo.getDigitizing())) {
            int currentvendor = styletovendor(theorderdataitem.getStyleNumber(), theorderdataitem.getVendorNumber());
            if (vendoralreadydig.containsKey(Integer.valueOf(currentvendor))) {
              TreeSet<String> thefilenames = (TreeSet)vendoralreadydig.get(Integer.valueOf(currentvendor));
              if ((theorderdatalogo.getFilename() != null) && (theorderdatalogo.getFilename().equals("")) && 
                (!thefilenames.contains(theorderdatalogo.getFilename()))) {
                mylogo.setDigitizing(theorderdatalogo.getDigitizing());
                thefilenames.add(theorderdatalogo.getFilename());
              }
              
              if ((theorderdatalogo.getDstFilename() != null) && (theorderdatalogo.getDstFilename().equals("")) && 
                (!thefilenames.contains(theorderdatalogo.getDstFilename()))) {
                mylogo.setDigitizing(theorderdatalogo.getDigitizing());
                thefilenames.add(theorderdatalogo.getDstFilename());
              }
            }
            else
            {
              TreeSet<String> newvalue = new TreeSet();
              if ((theorderdatalogo.getFilename() != null) && (theorderdatalogo.getFilename().equals(""))) {
                newvalue.add(theorderdatalogo.getFilename());
              }
              if ((theorderdatalogo.getDstFilename() != null) && (theorderdatalogo.getDstFilename().equals(""))) {
                newvalue.add(theorderdatalogo.getDstFilename());
              }
              mylogo.setDigitizing(theorderdatalogo.getDigitizing());
              vendoralreadydig.put(Integer.valueOf(currentvendor), newvalue);
            }
          }
          

          mylogo.setThreadBrand(theorderdatalogo.getThreadBrand());
          for (int l = 0; l < theorderdatalogo.getColorwayCount(); l++) {
            OrderDataLogoColorway theorderdatalogocolorway = theorderdatalogo.getColorway(l);
            ExtendOrderDataLogoColorway mycolorway = new ExtendOrderDataLogoColorway();
            mycolorway.setColorCodeComment(theorderdatalogocolorway.getColorCodeComment());
            
            mycolorway.setFlashCharge(theorderdatalogocolorway.getFlashCharge());
            
            mycolorway.setInkType(theorderdatalogocolorway.getInkType());
            mycolorway.setLogoColorCode(theorderdatalogocolorway.getLogoColorCode());
            mycolorway.setStitches(theorderdatalogocolorway.getStitches());
            mycolorway.setThreadType(theorderdatalogocolorway.getThreadType());
            mylogo.addColorway(mycolorway);
          }
          for (int l = 0; l < theorderdatalogo.getDecorationCount().intValue(); l++) {
            OrderDataLogoDecoration theorderdatalogodecoration = theorderdatalogo.getDecoration(Integer.valueOf(l));
            ExtendOrderDataLogoDecoration mydecoration = new ExtendOrderDataLogoDecoration();
            mydecoration.setComments(theorderdatalogodecoration.getComments());
            mydecoration.setField1(Double.valueOf(theorderdatalogodecoration.getField1() != null ? theorderdatalogodecoration.getField1().doubleValue() : 0.0D));
            mydecoration.setField2(Double.valueOf(theorderdatalogodecoration.getField2() != null ? theorderdatalogodecoration.getField2().doubleValue() : 0.0D));
            mydecoration.setField3(Double.valueOf(theorderdatalogodecoration.getField3() != null ? theorderdatalogodecoration.getField3().doubleValue() : 0.0D));
            mydecoration.setField4(Double.valueOf(theorderdatalogodecoration.getField4() != null ? theorderdatalogodecoration.getField4().doubleValue() : 0.0D));
            mydecoration.setName(theorderdatalogodecoration.getName());
            mylogo.addDecoration(mydecoration);
          }
          
          myset.addLogo(mylogo);
        }
        
        this.expendedorderdata.addSet(myset);
      }
    }
    


    this.expendedorderdata.setOrderTypeFolder(this.expendedorderdata.getOrderType() == OrderData.OrderType.DOMESTIC ? "Domestic" : "Overseas");
    if (this.sqltable.makeTable("SELECT * FROM `employees` WHERE `username` = '" + this.expendedorderdata.getEmployeeId() + "' LIMIT 1").booleanValue()) {
      this.expendedorderdata.setEmployeeFullName(this.sqltable.getCell("firstname") + " " + this.sqltable.getCell("lastname"));
      this.expendedorderdata.setEmployeeEmail((String)this.sqltable.getCell("email"));
    }
    
    for (int i = 0; i < this.expendedorderdata.getSetCount(); i++)
    {
      for (int j = 0; j < this.expendedorderdata.getSet(i).getItemCount(); j++) {
        this.expendedorderdata.getSet(i).getItem(j).setColorCodeName(convertCodeToName(this.expendedorderdata.getSet(i).getItem(j).getColorCode()));
        this.expendedorderdata.getSet(i).getItem(j).setFrontPanelColorName(convertCodeToName(this.expendedorderdata.getSet(i).getItem(j).getFrontPanelColor()));
        this.expendedorderdata.getSet(i).getItem(j).setSideBackPanelColorName(convertCodeToName(this.expendedorderdata.getSet(i).getItem(j).getSideBackPanelColor()));
        this.expendedorderdata.getSet(i).getItem(j).setBackPanelColorName(convertCodeToName(this.expendedorderdata.getSet(i).getItem(j).getBackPanelColor()));
        this.expendedorderdata.getSet(i).getItem(j).setSidePanelColorName(convertCodeToName(this.expendedorderdata.getSet(i).getItem(j).getSidePanelColor()));
        this.expendedorderdata.getSet(i).getItem(j).setPrimaryVisorColorName(convertCodeToName(this.expendedorderdata.getSet(i).getItem(j).getPrimaryVisorColor()));
        this.expendedorderdata.getSet(i).getItem(j).setVisorTrimColorName(convertCodeToName(this.expendedorderdata.getSet(i).getItem(j).getVisorTrimColor()));
        this.expendedorderdata.getSet(i).getItem(j).setVisorSandwichColorName(convertCodeToName(this.expendedorderdata.getSet(i).getItem(j).getVisorSandwichColor()));
        this.expendedorderdata.getSet(i).getItem(j).setUndervisorColorName(convertCodeToName(this.expendedorderdata.getSet(i).getItem(j).getUndervisorColor()));
        this.expendedorderdata.getSet(i).getItem(j).setDistressedVisorInsideColorName(convertCodeToName(this.expendedorderdata.getSet(i).getItem(j).getDistressedVisorInsideColor()));
        this.expendedorderdata.getSet(i).getItem(j).setClosureStrapColorName(convertCodeToName(this.expendedorderdata.getSet(i).getItem(j).getClosureStrapColor()));
        this.expendedorderdata.getSet(i).getItem(j).setFrontEyeletColorName(convertCodeToName(this.expendedorderdata.getSet(i).getItem(j).getFrontEyeletColor()));
        this.expendedorderdata.getSet(i).getItem(j).setSideBackEyeletColorName(convertCodeToName(this.expendedorderdata.getSet(i).getItem(j).getSideBackEyeletColor()));
        this.expendedorderdata.getSet(i).getItem(j).setBackEyeletColorName(convertCodeToName(this.expendedorderdata.getSet(i).getItem(j).getBackEyeletColor()));
        this.expendedorderdata.getSet(i).getItem(j).setSideEyeletColorName(convertCodeToName(this.expendedorderdata.getSet(i).getItem(j).getSideEyeletColor()));
        this.expendedorderdata.getSet(i).getItem(j).setButtonColorName(convertCodeToName(this.expendedorderdata.getSet(i).getItem(j).getButtonColor()));
        this.expendedorderdata.getSet(i).getItem(j).setInnerTapingColorName(convertCodeToName(this.expendedorderdata.getSet(i).getItem(j).getInnerTapingColor()));
        this.expendedorderdata.getSet(i).getItem(j).setSweatbandColorName(convertCodeToName(this.expendedorderdata.getSet(i).getItem(j).getSweatbandColor()));
        this.expendedorderdata.getSet(i).getItem(j).setSweatbandStripeColorName(convertCodeToName(this.expendedorderdata.getSet(i).getItem(j).getSweatbandStripeColor()));
        
        this.expendedorderdata.getSet(i).getItem(j).setPanelStitchColorName(convertCodeToName(this.expendedorderdata.getSet(i).getItem(j).getPanelStitchColor()));
        this.expendedorderdata.getSet(i).getItem(j).setVisorStitchColorName(convertCodeToName(this.expendedorderdata.getSet(i).getItem(j).getVisorStitchColor()));
        
        if ((this.expendedorderdata.getSet(i).getItem(j).getClosureStyleNumber() != null) && (!this.expendedorderdata.getSet(i).getItem(j).getClosureStyleNumber().equals("")) && (this.sqltable.makeTable("SELECT `name` FROM `overseas_price_styles_closure` WHERE `style` = '" + this.expendedorderdata.getSet(i).getItem(j).getClosureStyleNumber() + "' LIMIT 1").booleanValue())) {
          this.expendedorderdata.getSet(i).getItem(j).setClosureStyleNumberName(this.expendedorderdata.getSet(i).getItem(j).getClosureStyleNumber() + " - " + (String)this.sqltable.getValue());
        } else {
          this.expendedorderdata.getSet(i).getItem(j).setClosureStyleNumberName(this.expendedorderdata.getSet(i).getItem(j).getClosureStyleNumber());
        }
        
        if ((this.expendedorderdata.getSet(i).getItem(j).getEyeletStyleNumber() != null) && (!this.expendedorderdata.getSet(i).getItem(j).getEyeletStyleNumber().equals("")) && (this.sqltable.makeTable("SELECT `name` FROM `overseas_price_styles_eyelet` WHERE `style` = '" + this.expendedorderdata.getSet(i).getItem(j).getEyeletStyleNumber() + "' LIMIT 1").booleanValue())) {
          this.expendedorderdata.getSet(i).getItem(j).setEyeletStyleNumberName(this.expendedorderdata.getSet(i).getItem(j).getEyeletStyleNumber() + " - " + (String)this.sqltable.getValue());
        } else {
          this.expendedorderdata.getSet(i).getItem(j).setEyeletStyleNumberName(this.expendedorderdata.getSet(i).getItem(j).getEyeletStyleNumber());
        }
        
        if ((this.expendedorderdata.getSet(i).getItem(j).getSweatbandStyleNumber() != null) && (!this.expendedorderdata.getSet(i).getItem(j).getSweatbandStyleNumber().equals("")) && (this.sqltable.makeTable("SELECT `name` FROM `overseas_price_styles_sweatband` WHERE `style` = '" + this.expendedorderdata.getSet(i).getItem(j).getSweatbandStyleNumber() + "' LIMIT 1").booleanValue())) {
          this.expendedorderdata.getSet(i).getItem(j).setSweatbandStyleNumberName(this.expendedorderdata.getSet(i).getItem(j).getSweatbandStyleNumber() + " - " + (String)this.sqltable.getValue());
        } else {
          this.expendedorderdata.getSet(i).getItem(j).setSweatbandStyleNumberName(this.expendedorderdata.getSet(i).getItem(j).getSweatbandStyleNumber());
        }
        
        if ((this.expendedorderdata.getSet(i).getItem(j).getVisorStyleNumber() != null) && (!this.expendedorderdata.getSet(i).getItem(j).getVisorStyleNumber().equals("")) && (this.sqltable.makeTable("SELECT `name` FROM `overseas_price_styles_visor` WHERE `style` = '" + this.expendedorderdata.getSet(i).getItem(j).getVisorStyleNumber() + "' LIMIT 1").booleanValue())) {
          this.expendedorderdata.getSet(i).getItem(j).setVisorStyleNumberName(this.expendedorderdata.getSet(i).getItem(j).getVisorStyleNumber() + " - " + (String)this.sqltable.getValue());
        } else {
          this.expendedorderdata.getSet(i).getItem(j).setVisorStyleNumberName(this.expendedorderdata.getSet(i).getItem(j).getVisorStyleNumber());
        }
        
        this.sqltable.makeTable("SELECT `exchangerate` FROM `ordermain` WHERE `hiddenkey` =" + theorderdata.getHiddenKey());
        this.expendedorderdata.setExchangeRate(this.sqltable.getValue() != null ? ((Double)this.sqltable.getValue()).doubleValue() : 0.0D);
        
        if (this.expendedorderdata.getOrderType() == OrderData.OrderType.DOMESTIC) {
          if (this.sqltable.makeTable("SELECT * FROM `styles_domestic` WHERE `sku` = '" + this.expendedorderdata.getSet(i).getItem(j).getStyleNumber() + "' LIMIT 1").booleanValue()) {
            this.expendedorderdata.getSet(i).getItem(j).setStyleType(StyleInformationData.StyleType.DOMESTIC);
            this.expendedorderdata.getSet(i).getItem(j).setCrownStyle((String)this.sqltable.getCell("crownstyle"));
            this.expendedorderdata.getSet(i).getItem(j).setNumberOfPanels((String)this.sqltable.getCell("panel"));
            this.expendedorderdata.getSet(i).getItem(j).setCategory(((String)this.sqltable.getCell("category")).trim());
            this.expendedorderdata.getSet(i).getItem(j).setGoodsType(((String)this.sqltable.getCell("type")).trim());
            this.expendedorderdata.getSet(i).getItem(j).setSameStyle(((String)this.sqltable.getCell("samestyle")).trim());
            
            if (((String)this.sqltable.getCell("construction")).equals("U")) {
              this.expendedorderdata.getSet(i).getItem(j).setCrownConstruction("Unstructured");
            } else if (((String)this.sqltable.getCell("construction")).equals("S")) {
              this.expendedorderdata.getSet(i).getItem(j).setCrownConstruction("Structured");
            } else if (((String)this.sqltable.getCell("construction")).equals("F")) {
              this.expendedorderdata.getSet(i).getItem(j).setCrownConstruction("Structured - Half Buckram");
            } else {
              this.expendedorderdata.getSet(i).getItem(j).setCrownConstruction((String)this.sqltable.getCell("construction"));
            }
            
            if (this.sqltable.makeTable("SELECT `name` FROM `list_domestic_materials` WHERE `id` ='" + this.sqltable.getCell("fabric") + "' LIMIT 1").booleanValue()) {
              this.expendedorderdata.getSet(i).getItem(j).setMaterial((String)this.sqltable.getValue());



            }
            




          }
          else if (this.sqltable.makeTable("SELECT * FROM `styles_domestic_specials` WHERE `style` = '" + this.expendedorderdata.getSet(i).getItem(j).getStyleNumber() + "' LIMIT 1").booleanValue()) {
            this.expendedorderdata.getSet(i).getItem(j).setStyleType(StyleInformationData.StyleType.DOMESTIC_SPECIAL);
            String basestyle = (String)this.sqltable.getCell("basestyle");
            
            if (this.sqltable.makeTable("SELECT * FROM `styles_domestic` WHERE `sku` = '" + basestyle + "' LIMIT 1").booleanValue())
            {
              this.expendedorderdata.getSet(i).getItem(j).setCrownStyle((String)this.sqltable.getCell("crownstyle"));
              this.expendedorderdata.getSet(i).getItem(j).setNumberOfPanels((String)this.sqltable.getCell("panel"));
              this.expendedorderdata.getSet(i).getItem(j).setCategory(((String)this.sqltable.getCell("category")).trim());
              this.expendedorderdata.getSet(i).getItem(j).setGoodsType(((String)this.sqltable.getCell("type")).trim());
              this.expendedorderdata.getSet(i).getItem(j).setSameStyle(((String)this.sqltable.getCell("samestyle")).trim());
              
              if (((String)this.sqltable.getCell("construction")).equals("U")) {
                this.expendedorderdata.getSet(i).getItem(j).setCrownConstruction("Unstructured");
              } else if (((String)this.sqltable.getCell("construction")).equals("S")) {
                this.expendedorderdata.getSet(i).getItem(j).setCrownConstruction("Structured");
              } else if (((String)this.sqltable.getCell("construction")).equals("F")) {
                this.expendedorderdata.getSet(i).getItem(j).setCrownConstruction("Structured - Half Buckram");
              } else {
                this.expendedorderdata.getSet(i).getItem(j).setCrownConstruction((String)this.sqltable.getCell("construction"));
              }
              
              if (this.sqltable.makeTable("SELECT `name` FROM `list_domestic_materials` WHERE `id` ='" + this.sqltable.getCell("fabric") + "' LIMIT 1").booleanValue()) {
                this.expendedorderdata.getSet(i).getItem(j).setMaterial((String)this.sqltable.getValue());
              }
            }
            else if (this.sqltable.makeTable("SELECT * FROM `styles_lackpard` WHERE `Style` = '" + basestyle + "' LIMIT 1").booleanValue()) {
              this.expendedorderdata.getSet(i).getItem(j).setCategory(((String)this.sqltable.getCell("Main Category")).trim());
              this.expendedorderdata.getSet(i).getItem(j).setGoodsType("3D");
              this.expendedorderdata.getSet(i).getItem(j).setSameStyle(((String)this.sqltable.getCell("Style")).trim());
            }
            
          }
          else if (this.sqltable.makeTable("SELECT * FROM `styles_domestic_shirts` WHERE `sku` = '" + this.expendedorderdata.getSet(i).getItem(j).getStyleNumber() + "' LIMIT 1").booleanValue()) {
            this.expendedorderdata.getSet(i).getItem(j).setStyleType(StyleInformationData.StyleType.DOMESTIC_SHIRTS);
            this.expendedorderdata.getSet(i).getItem(j).setCategory(((String)this.sqltable.getCell("category")).trim());
            this.expendedorderdata.getSet(i).getItem(j).setGoodsType(((String)this.sqltable.getCell("type")).trim());
            this.expendedorderdata.getSet(i).getItem(j).setSameStyle(((String)this.sqltable.getCell("samestyle")).trim());
            


            if (this.sqltable.makeTable("SELECT `name` FROM `list_domestic_materials` WHERE `id` ='" + this.sqltable.getCell("fabric") + "' LIMIT 1").booleanValue()) {
              this.expendedorderdata.getSet(i).getItem(j).setMaterial((String)this.sqltable.getValue());
            }
          } else if (this.sqltable.makeTable("SELECT * FROM `styles_domestic_sweatshirts` WHERE `sku` = '" + this.expendedorderdata.getSet(i).getItem(j).getStyleNumber() + "' LIMIT 1").booleanValue()) {
            this.expendedorderdata.getSet(i).getItem(j).setStyleType(StyleInformationData.StyleType.DOMESTIC_SWEATSHIRTS);
            this.expendedorderdata.getSet(i).getItem(j).setCategory(((String)this.sqltable.getCell("category")).trim());
            this.expendedorderdata.getSet(i).getItem(j).setGoodsType(((String)this.sqltable.getCell("type")).trim());
            this.expendedorderdata.getSet(i).getItem(j).setSameStyle(((String)this.sqltable.getCell("samestyle")).trim());
            


            if (this.sqltable.makeTable("SELECT `name` FROM `list_domestic_materials` WHERE `id` ='" + this.sqltable.getCell("fabric") + "' LIMIT 1").booleanValue()) {
              this.expendedorderdata.getSet(i).getItem(j).setMaterial((String)this.sqltable.getValue());
            }
          } else if (this.sqltable.makeTable("SELECT * FROM `styles_lackpard` WHERE `Style` = '" + this.expendedorderdata.getSet(i).getItem(j).getStyleNumber() + "' LIMIT 1").booleanValue()) {
            this.expendedorderdata.getSet(i).getItem(j).setStyleType(StyleInformationData.StyleType.DOMESTIC_LACKPARD);
            this.expendedorderdata.getSet(i).getItem(j).setCategory(((String)this.sqltable.getCell("Main Category")).trim());
            this.expendedorderdata.getSet(i).getItem(j).setGoodsType("3D");
            this.expendedorderdata.getSet(i).getItem(j).setSameStyle(((String)this.sqltable.getCell("Style")).trim());

          }
          else if (this.sqltable.makeTable("SELECT * FROM `styles_domestic_totebags` WHERE `sku` = '" + this.expendedorderdata.getSet(i).getItem(j).getStyleNumber() + "' LIMIT 1").booleanValue()) {
            this.expendedorderdata.getSet(i).getItem(j).setStyleType(StyleInformationData.StyleType.DOMESTIC_TOTEBAGS);
            

            this.expendedorderdata.getSet(i).getItem(j).setCategory(((String)this.sqltable.getCell("category")).trim());
            this.expendedorderdata.getSet(i).getItem(j).setGoodsType(((String)this.sqltable.getCell("type")).trim());
            this.expendedorderdata.getSet(i).getItem(j).setSameStyle(((String)this.sqltable.getCell("samestyle")).trim());
            


            if (this.sqltable.makeTable("SELECT `name` FROM `list_domestic_materials` WHERE `id` ='" + this.sqltable.getCell("fabric") + "' LIMIT 1").booleanValue()) {
              this.expendedorderdata.getSet(i).getItem(j).setMaterial((String)this.sqltable.getValue());
            }
          }
          else if (this.sqltable.makeTable("SELECT * FROM `styles_domestic_aprons` WHERE `sku` = '" + this.expendedorderdata.getSet(i).getItem(j).getStyleNumber() + "' LIMIT 1").booleanValue()) {
            this.expendedorderdata.getSet(i).getItem(j).setStyleType(StyleInformationData.StyleType.DOMESTIC_APRONS);
            

            this.expendedorderdata.getSet(i).getItem(j).setCategory(((String)this.sqltable.getCell("category")).trim());
            this.expendedorderdata.getSet(i).getItem(j).setGoodsType(((String)this.sqltable.getCell("type")).trim());
            this.expendedorderdata.getSet(i).getItem(j).setSameStyle(((String)this.sqltable.getCell("samestyle")).trim());
            


            if (this.sqltable.makeTable("SELECT `name` FROM `list_domestic_materials` WHERE `id` ='" + this.sqltable.getCell("fabric") + "' LIMIT 1").booleanValue()) {
              this.expendedorderdata.getSet(i).getItem(j).setMaterial((String)this.sqltable.getValue());
            }
          }
          else if ((this.expendedorderdata.getSet(i).getItem(j).getStyleNumber().equals("nonotto")) || (this.expendedorderdata.getSet(i).getItem(j).getStyleNumber().equals("nonottoflat"))) {
            this.expendedorderdata.getSet(i).getItem(j).setStyleType(StyleInformationData.StyleType.NONOTTO);
            this.expendedorderdata.getSet(i).getItem(j).setCategory("nonotto");
            if (this.expendedorderdata.getSet(i).getItem(j).getStyleNumber().equals("nonotto")) {
              this.expendedorderdata.getSet(i).getItem(j).setGoodsType("3D");
              this.expendedorderdata.getSet(i).getItem(j).setSameStyle(this.expendedorderdata.getSet(i).getItem(j).getCustomStyleName() + "3D");
            } else if (this.expendedorderdata.getSet(i).getItem(j).getStyleNumber().equals("nonottoflat")) {
              this.expendedorderdata.getSet(i).getItem(j).setGoodsType("Flat");
              this.expendedorderdata.getSet(i).getItem(j).setSameStyle(this.expendedorderdata.getSet(i).getItem(j).getCustomStyleName() + "Flat");
            }
            
          }
        }
        else
        {
          if (this.sqltable.makeTable("SELECT * FROM `styles_overseas` WHERE `Style` = '" + this.expendedorderdata.getSet(i).getItem(j).getStyleNumber() + "' LIMIT 1").booleanValue()) {
            this.expendedorderdata.getSet(i).getItem(j).setStyleType(StyleInformationData.StyleType.OVERSEAS);
            this.expendedorderdata.getSet(i).getItem(j).setCrownStyle((String)this.sqltable.getCell("Crown Style"));
            this.expendedorderdata.getSet(i).getItem(j).setMaterial((String)this.sqltable.getCell("Material"));
            this.expendedorderdata.getSet(i).getItem(j).setNumberOfPanels((String)this.sqltable.getCell("Panel"));
            this.expendedorderdata.getSet(i).getItem(j).setCategory(((String)this.sqltable.getCell("Category")).trim());
            this.expendedorderdata.getSet(i).getItem(j).setOverseasVendorNumber(Integer.valueOf((String)this.sqltable.getCell("Vendor")).intValue());
            
            if (((String)this.sqltable.getCell("Construction")).equals("U")) {
              this.expendedorderdata.getSet(i).getItem(j).setCrownConstruction("Unstructured");
            } else if (((String)this.sqltable.getCell("Construction")).equals("S")) {
              this.expendedorderdata.getSet(i).getItem(j).setCrownConstruction("Structured");
            } else if (((String)this.sqltable.getCell("Construction")).equals("F")) {
              this.expendedorderdata.getSet(i).getItem(j).setCrownConstruction("Structured - Half Buckram");
            } else {
              this.expendedorderdata.getSet(i).getItem(j).setCrownConstruction((String)this.sqltable.getCell("Construction"));
            }
          }
          else if (this.sqltable.makeTable("SELECT * FROM `styles_pre-designed` WHERE `Style` = '" + this.expendedorderdata.getSet(i).getItem(j).getStyleNumber() + "' LIMIT 1").booleanValue()) {
            this.expendedorderdata.getSet(i).getItem(j).setStyleType(StyleInformationData.StyleType.OVERSEAS_PREDESIGNED);
            this.expendedorderdata.getSet(i).getItem(j).setMaterial((String)this.sqltable.getCell("Material Description"));
            this.expendedorderdata.getSet(i).getItem(j).setCrownConstruction((String)this.sqltable.getCell("Structured/ Unstructured"));
            this.expendedorderdata.getSet(i).getItem(j).setNumberOfPanels((String)this.sqltable.getCell("Panels"));
            this.expendedorderdata.getSet(i).getItem(j).setCategory(((String)this.sqltable.getCell("Category")).trim());
            this.expendedorderdata.getSet(i).getItem(j).setOverseasVendorNumber(Integer.valueOf((String)this.sqltable.getCell("Vendor")).intValue());
            
            if ((this.expendedorderdata.getSet(i).getItem(j).getStyleNumber().equals("888-A")) || (this.expendedorderdata.getSet(i).getItem(j).getStyleNumber().equals("888-B")) || (this.expendedorderdata.getSet(i).getItem(j).getStyleNumber().equals("888-C")) || (this.expendedorderdata.getSet(i).getItem(j).getStyleNumber().equals("888-D")) || (this.expendedorderdata.getSet(i).getItem(j).getStyleNumber().equals("888-E")) || (this.expendedorderdata.getSet(i).getItem(j).getStyleNumber().equals("888-F")) || (this.expendedorderdata.getSet(i).getItem(j).getStyleNumber().equals("888-G")) || (this.expendedorderdata.getSet(i).getItem(j).getStyleNumber().equals("888-H")) || (this.expendedorderdata.getSet(i).getItem(j).getStyleNumber().equals("888-J")) || (this.expendedorderdata.getSet(i).getItem(j).getStyleNumber().equals("888-L")) || (this.expendedorderdata.getSet(i).getItem(j).getStyleNumber().equals("888-M")) || (this.expendedorderdata.getSet(i).getItem(j).getStyleNumber().equals("888-N")) || (this.expendedorderdata.getSet(i).getItem(j).getStyleNumber().equals("888-O")) || (this.expendedorderdata.getSet(i).getItem(j).getStyleNumber().equals("889-A")) || (this.expendedorderdata.getSet(i).getItem(j).getStyleNumber().equals("889-B")) || (this.expendedorderdata.getSet(i).getItem(j).getStyleNumber().equals("889-C")) || 
              (this.expendedorderdata.getSet(i).getItem(j).getStyleNumber().equals("889-D")) || (this.expendedorderdata.getSet(i).getItem(j).getStyleNumber().equals("889-E")) || (this.expendedorderdata.getSet(i).getItem(j).getStyleNumber().equals("889-F")) || (this.expendedorderdata.getSet(i).getItem(j).getStyleNumber().equals("889-G")) || (this.expendedorderdata.getSet(i).getItem(j).getStyleNumber().equals("889-H")) || (this.expendedorderdata.getSet(i).getItem(j).getStyleNumber().equals("889-J")) || (this.expendedorderdata.getSet(i).getItem(j).getStyleNumber().equals("889-L")) || (this.expendedorderdata.getSet(i).getItem(j).getStyleNumber().equals("889-M")) || (this.expendedorderdata.getSet(i).getItem(j).getStyleNumber().equals("889-N")) || (this.expendedorderdata.getSet(i).getItem(j).getStyleNumber().equals("889-O"))) {
              this.expendedorderdata.getSet(i).getItem(j).setStyleType(StyleInformationData.StyleType.OVERSEAS_NONOTTO);
            } else if ((this.expendedorderdata.getSet(i).getItem(j).getStyleNumber().equals("888-I")) || (this.expendedorderdata.getSet(i).getItem(j).getStyleNumber().equals("889-I"))) {
              this.expendedorderdata.getSet(i).getItem(j).setStyleType(StyleInformationData.StyleType.OVERSEAS_NONOTTO_SHIRTS);
            }
          }
          else if (this.sqltable.makeTable("SELECT * FROM `styles_overseasinstock` WHERE `STYLE NUMBER` = '" + this.expendedorderdata.getSet(i).getItem(j).getStyleNumber() + "' LIMIT 1").booleanValue()) {
            this.expendedorderdata.getSet(i).getItem(j).setStyleType(StyleInformationData.StyleType.OVERSEAS_INSTOCK);
            this.expendedorderdata.getSet(i).getItem(j).setOverseasVendorNumber(Integer.valueOf((String)this.sqltable.getCell("Primary VENDOR")).intValue());
            this.expendedorderdata.getSet(i).getItem(j).setCrownStyle((String)this.sqltable.getCell("Crown Style"));
            this.expendedorderdata.getSet(i).getItem(j).setNumberOfPanels((String)this.sqltable.getCell("Panel"));
            this.expendedorderdata.getSet(i).getItem(j).setCategory(((String)this.sqltable.getCell("Category")).trim());
            this.expendedorderdata.getSet(i).getItem(j).setSameStyle(((String)this.sqltable.getCell("Same Style")).trim());
            
            if (((String)this.sqltable.getCell("Construction")).equals("U")) {
              this.expendedorderdata.getSet(i).getItem(j).setCrownConstruction("Unstructured");
            } else if (((String)this.sqltable.getCell("Construction")).equals("S")) {
              this.expendedorderdata.getSet(i).getItem(j).setCrownConstruction("Structured");
            } else if (((String)this.sqltable.getCell("Construction")).equals("F")) {
              this.expendedorderdata.getSet(i).getItem(j).setCrownConstruction("Structured - Half Buckram");
            } else {
              this.expendedorderdata.getSet(i).getItem(j).setCrownConstruction((String)this.sqltable.getCell("Construction"));
            }
            







            if ((this.expendedorderdata.getSet(i).getItem(j).getVendorNumber() != null) && (!this.expendedorderdata.getSet(i).getItem(j).getVendorNumber().equals("")) && (!this.expendedorderdata.getSet(i).getItem(j).getVendorNumber().equals("Default"))) {
              this.expendedorderdata.getSet(i).getItem(j).setOverseasVendorNumber(Integer.valueOf(this.expendedorderdata.getSet(i).getItem(j).getVendorNumber()).intValue());
              
              if (this.sqltable.makeTable("SELECT * FROM `styles_overseasinstock` WHERE `STYLE NUMBER` = '" + this.expendedorderdata.getSet(i).getItem(j).getStyleNumber() + "' AND `Backup VENDOR` = '" + this.expendedorderdata.getSet(i).getItem(j).getVendorNumber() + "' LIMIT 1").booleanValue())
              {
                this.expendedorderdata.getSet(i).getItem(j).setMaterial((String)this.sqltable.getCell("Fabric Name"));
                this.expendedorderdata.getSet(i).getItem(j).setFrontPanelFabricName((String)this.sqltable.getCell("Fabric Name"));
                this.expendedorderdata.getSet(i).getItem(j).setFrontPanelContentName((String)this.sqltable.getCell("Material Content"));
                this.expendedorderdata.getSet(i).getItem(j).setFrontPanelFabricSpecName((String)this.sqltable.getCell("Material Specs"));
                
                if (this.expendedorderdata.getSet(i).getItem(j).getVisorStyleNumber().equals(((String)this.sqltable.getCell("Original Visor")).trim()))
                {
                  String visorrowsitching = ((String)this.sqltable.getCell("Original # Visor Row Stitching")).trim();
                  if ((!visorrowsitching.equals("")) && (!visorrowsitching.equals("N/A")) && (!visorrowsitching.equals("Discontinued"))) {
                    this.expendedorderdata.getSet(i).getItem(j).setVisorRowStitching(visorrowsitching);
                  }
                  String orgfactoryvisorcode = ((String)this.sqltable.getCell("Original Factory Visor Code")).trim();
                  if ((!orgfactoryvisorcode.equals("")) && (!orgfactoryvisorcode.equals("N/A"))) {
                    this.expendedorderdata.getSet(i).getItem(j).setFactoryVisorCode(orgfactoryvisorcode);
                  }
                }
              }
            }
            else {
              String primvennum = (String)this.sqltable.getCell("Primary VENDOR");
              
              if (this.sqltable.makeTable("SELECT * FROM `styles_overseasinstock` WHERE `STYLE NUMBER` = '" + this.expendedorderdata.getSet(i).getItem(j).getStyleNumber() + "' AND `Backup VENDOR` = '" + primvennum + "' LIMIT 1").booleanValue())
              {
                this.expendedorderdata.getSet(i).getItem(j).setMaterial((String)this.sqltable.getCell("Fabric Name"));
                this.expendedorderdata.getSet(i).getItem(j).setFrontPanelFabricName((String)this.sqltable.getCell("Fabric Name"));
                this.expendedorderdata.getSet(i).getItem(j).setFrontPanelContentName((String)this.sqltable.getCell("Material Content"));
                this.expendedorderdata.getSet(i).getItem(j).setFrontPanelFabricSpecName((String)this.sqltable.getCell("Material Specs"));
                
                String visorrowsitching = ((String)this.sqltable.getCell("Original # Visor Row Stitching")).trim();
                if ((!visorrowsitching.equals("")) && (!visorrowsitching.equals("N/A")) && (!visorrowsitching.equals("Discontinued"))) {
                  this.expendedorderdata.getSet(i).getItem(j).setVisorRowStitching(visorrowsitching);
                }
                String orgfactoryvisorcode = ((String)this.sqltable.getCell("Original Factory Visor Code")).trim();
                if ((!orgfactoryvisorcode.equals("")) && (!orgfactoryvisorcode.equals("N/A"))) {
                  this.expendedorderdata.getSet(i).getItem(j).setFactoryVisorCode(orgfactoryvisorcode);
                }
                
              }
            }
          }
          else if (this.sqltable.makeTable("SELECT * FROM `overseas_price_instockshirts` WHERE `stylenumber` = '" + this.expendedorderdata.getSet(i).getItem(j).getStyleNumber() + "' LIMIT 1").booleanValue()) {
            this.expendedorderdata.getSet(i).getItem(j).setStyleType(StyleInformationData.StyleType.OVERSEAS_INSTOCK_SHIRTS);
            this.expendedorderdata.getSet(i).getItem(j).setCategory(((String)this.sqltable.getCell("category")).trim());
            this.expendedorderdata.getSet(i).getItem(j).setOverseasVendorNumber(Integer.valueOf((String)this.sqltable.getCell("vendor")).intValue());
            
            if (!this.sqltable.makeTable("SELECT `samestyle`,`fabric` FROM `styles_domestic_shirts` WHERE `sku` = '" + this.expendedorderdata.getSet(i).getItem(j).getStyleNumber() + "' LIMIT 1").booleanValue()) {
              this.sqltable.makeTable("SELECT `samestyle`,`fabric` FROM `styles_domestic_sweatshirts` WHERE `sku` = '" + this.expendedorderdata.getSet(i).getItem(j).getStyleNumber() + "' LIMIT 1").booleanValue();
            }
            
            this.expendedorderdata.getSet(i).getItem(j).setSameStyle(((String)this.sqltable.getCell("samestyle")).trim());
            if (this.sqltable.makeTable("SELECT `name` FROM `list_domestic_materials` WHERE `id` ='" + this.sqltable.getCell("fabric") + "' LIMIT 1").booleanValue()) {
              this.expendedorderdata.getSet(i).getItem(j).setMaterial((String)this.sqltable.getValue());
            }
          }
          else if (this.sqltable.makeTable("SELECT * FROM `overseas_price_instockflats` WHERE `stylenumber` = '" + this.expendedorderdata.getSet(i).getItem(j).getStyleNumber() + "' LIMIT 1").booleanValue()) {
            this.expendedorderdata.getSet(i).getItem(j).setStyleType(StyleInformationData.StyleType.OVERSEAS_INSTOCK_FLATS);
            this.expendedorderdata.getSet(i).getItem(j).setCategory(((String)this.sqltable.getCell("category")).trim());
            this.expendedorderdata.getSet(i).getItem(j).setOverseasVendorNumber(Integer.valueOf((String)this.sqltable.getCell("vendor")).intValue());
            this.expendedorderdata.getSet(i).getItem(j).setSameStyle(((String)this.sqltable.getCell("samestyle")).trim());
            
            if (this.sqltable.makeTable("SELECT `name` FROM `list_domestic_materials` WHERE `id` ='" + this.sqltable.getCell("fabric") + "' LIMIT 1").booleanValue()) {
              this.expendedorderdata.getSet(i).getItem(j).setMaterial((String)this.sqltable.getValue());
            }
          }
          else if (this.sqltable.makeTable("SELECT * FROM `styles_lackpard` WHERE `Style` = '" + this.expendedorderdata.getSet(i).getItem(j).getStyleNumber() + "' LIMIT 1").booleanValue()) {
            this.expendedorderdata.getSet(i).getItem(j).setStyleType(StyleInformationData.StyleType.OVERSEAS_LACKPARD);
            this.expendedorderdata.getSet(i).getItem(j).setCategory(((String)this.sqltable.getCell("Main Category")).trim());
            this.expendedorderdata.getSet(i).getItem(j).setGoodsType("3D");
            this.expendedorderdata.getSet(i).getItem(j).setSameStyle(((String)this.sqltable.getCell("Style")).trim());
            this.expendedorderdata.getSet(i).getItem(j).setOverseasVendorNumber(4);
          }
          else if (this.sqltable.makeTable("SELECT * FROM `styles_overseas_customstyle` WHERE `Style` = '" + this.expendedorderdata.getSet(i).getItem(j).getStyleNumber() + "' LIMIT 1").booleanValue()) {
            this.expendedorderdata.getSet(i).getItem(j).setStyleType(StyleInformationData.StyleType.OVERSEAS_CUSTOMSTYLE);
            this.expendedorderdata.getSet(i).getItem(j).setOverseasVendorNumber(Integer.valueOf((String)this.sqltable.getCell("Vendor")).intValue());
            if (this.sqltable.makeTable("SELECT * FROM `styles_overseas_customstyle` WHERE `Style` = '" + this.expendedorderdata.getSet(i).getItem(j).getStyleNumber() + "' AND `Profile` = '" + this.expendedorderdata.getSet(i).getItem(j).getProfile() + "' LIMIT 1").booleanValue()) {
              this.expendedorderdata.getSet(i).getItem(j).setCategory(this.expendedorderdata.getSet(i).getItem(j).getProfile());
              if (this.sqltable.makeTable("SELECT * FROM `styles_overseas_customstyle` WHERE `Style` = '" + this.expendedorderdata.getSet(i).getItem(j).getStyleNumber() + "' AND `Profile` = '" + this.expendedorderdata.getSet(i).getItem(j).getProfile() + "' AND `WorkFlow Fabic Name` = '" + this.expendedorderdata.getSet(i).getItem(j).getFrontPanelFabric() + "' LIMIT 1").booleanValue()) {
                this.expendedorderdata.getSet(i).getItem(j).setFrontPanelFabricName((String)this.sqltable.getCell("Panel Fabric"));
                this.expendedorderdata.getSet(i).getItem(j).setFrontPanelContentName((String)this.sqltable.getCell("Fabric Content"));
                this.expendedorderdata.getSet(i).getItem(j).setFrontPanelFabricSpecName((String)this.sqltable.getCell("Fabric Spec."));
              } else {
                this.expendedorderdata.getSet(i).getItem(j).setFrontPanelFabricName(this.expendedorderdata.getSet(i).getItem(j).getFrontPanelFabric());
              }
              if (this.sqltable.makeTable("SELECT * FROM `styles_overseas_customstyle` WHERE `Style` = '" + this.expendedorderdata.getSet(i).getItem(j).getStyleNumber() + "' AND `Profile` = '" + this.expendedorderdata.getSet(i).getItem(j).getProfile() + "' AND `WorkFlow Fabic Name` = '" + this.expendedorderdata.getSet(i).getItem(j).getBackPanelFabric() + "' LIMIT 1").booleanValue()) {
                this.expendedorderdata.getSet(i).getItem(j).setBackPanelFabricName((String)this.sqltable.getCell("Panel Fabric"));
                this.expendedorderdata.getSet(i).getItem(j).setBackPanelContentName((String)this.sqltable.getCell("Fabric Content"));
                this.expendedorderdata.getSet(i).getItem(j).setBackPanelFabricSpecName((String)this.sqltable.getCell("Fabric Spec."));
              } else {
                this.expendedorderdata.getSet(i).getItem(j).setBackPanelFabricName(this.expendedorderdata.getSet(i).getItem(j).getBackPanelFabric());
              }
              if (this.sqltable.makeTable("SELECT * FROM `styles_overseas_customstyle` WHERE `Style` = '" + this.expendedorderdata.getSet(i).getItem(j).getStyleNumber() + "' AND `Profile` = '" + this.expendedorderdata.getSet(i).getItem(j).getProfile() + "' AND `WorkFlow Fabic Name` = '" + this.expendedorderdata.getSet(i).getItem(j).getSidePanelFabric() + "' LIMIT 1").booleanValue()) {
                this.expendedorderdata.getSet(i).getItem(j).setSidePanelFabricName((String)this.sqltable.getCell("Panel Fabric"));
                this.expendedorderdata.getSet(i).getItem(j).setSidePanelContentName((String)this.sqltable.getCell("Fabric Content"));
                this.expendedorderdata.getSet(i).getItem(j).setSidePanelFabricSpecName((String)this.sqltable.getCell("Fabric Spec."));
              } else {
                this.expendedorderdata.getSet(i).getItem(j).setSidePanelFabricName(this.expendedorderdata.getSet(i).getItem(j).getSidePanelFabric());
              }
            }
          }
          


          boolean haveemb = false;
          boolean havescreen = false;
          for (int k = 0; k < this.expendedorderdata.getSet(i).getLogoCount(); k++) {
            for (int l = 0; l < this.expendedorderdata.getSet(i).getLogo(k).getDecorationCount().intValue(); l++) {
              if ((this.expendedorderdata.getSet(i).getLogo(k).getDecoration(Integer.valueOf(l)).getName().equals("CAD Print")) || (this.expendedorderdata.getSet(i).getLogo(k).getDecoration(Integer.valueOf(l)).getName().equals("Screen Print")) || (this.expendedorderdata.getSet(i).getLogo(k).getDecoration(Integer.valueOf(l)).getName().equals("Screen Print Full Panel")) || (this.expendedorderdata.getSet(i).getLogo(k).getDecoration(Integer.valueOf(l)).getName().equals("Screen Print Golf Style Front Full Panel")) || (this.expendedorderdata.getSet(i).getLogo(k).getDecoration(Integer.valueOf(l)).getName().equals("Sublimation")) || (this.expendedorderdata.getSet(i).getLogo(k).getDecoration(Integer.valueOf(l)).getName().equals("Sublimation Full Panel")) || (this.expendedorderdata.getSet(i).getLogo(k).getDecoration(Integer.valueOf(l)).getName().equals("Sublimation Golf Front Full Panel"))) {
                havescreen = true;
              } else if ((!this.expendedorderdata.getSet(i).getLogo(k).getDecoration(Integer.valueOf(l)).getName().equals("Custom Inside Woven Label")) && (!this.expendedorderdata.getSet(i).getLogo(k).getDecoration(Integer.valueOf(l)).getName().equals("Custom Inside Print Label")) && (!this.expendedorderdata.getSet(i).getLogo(k).getDecoration(Integer.valueOf(l)).getName().equals("Cap Hang Tag")) && (!this.expendedorderdata.getSet(i).getLogo(k).getDecoration(Integer.valueOf(l)).getName().equals("Inside Woven Hang Tag")) && (!this.expendedorderdata.getSet(i).getLogo(k).getDecoration(Integer.valueOf(l)).getName().equals("Woven Hang Tag")) && (!this.expendedorderdata.getSet(i).getLogo(k).getDecoration(Integer.valueOf(l)).getName().equals("Crown Embossed Button")) && (!this.expendedorderdata.getSet(i).getLogo(k).getDecoration(Integer.valueOf(l)).getName().equals("Embossed Buckle")) && (!this.expendedorderdata.getSet(i).getLogo(k).getDecoration(Integer.valueOf(l)).getName().equals("Mesh Eyelet")) && (!this.expendedorderdata.getSet(i).getLogo(k).getDecoration(Integer.valueOf(l)).getName().equals("Metal Eyelet")) && (!this.expendedorderdata.getSet(i).getLogo(k).getDecoration(Integer.valueOf(l)).getName().equals("N/A")) && (!this.expendedorderdata.getSet(i).getLogo(k).getDecoration(Integer.valueOf(l)).getName().equals("Other")))
              {

                haveemb = true;
              }
            }
          }
          if ((haveemb) && (havescreen)) {
            this.expendedorderdata.getSet(i).getItem(j).setAdvanceType("AEAS");
          } else if (havescreen) {
            this.expendedorderdata.getSet(i).getItem(j).setAdvanceType("AS");
          } else if (haveemb) {
            this.expendedorderdata.getSet(i).getItem(j).setAdvanceType("AE");
          } else {
            this.expendedorderdata.getSet(i).getItem(j).setAdvanceType("AB");
          }
        }
      }
      


      for (int j = 0; j < this.expendedorderdata.getSet(i).getLogoCount(); j++)
      {
        if (this.sqltable.makeTable("SELECT `name` FROM `list_decorations_locations` WHERE `id` = '" + this.expendedorderdata.getSet(i).getLogo(j).getLogoLocation() + "'").booleanValue()) {
          this.expendedorderdata.getSet(i).getLogo(j).setLogoLocationName(this.expendedorderdata.getSet(i).getLogo(j).getLogoLocation() + " - " + this.sqltable.getValue());
        } else {
          this.expendedorderdata.getSet(i).getLogo(j).setLogoLocationName(this.expendedorderdata.getSet(i).getLogo(j).getLogoLocation());
        }
        
        for (int k = 0; k < this.expendedorderdata.getSet(i).getLogo(j).getColorwayCount(); k++) {
          this.expendedorderdata.getSet(i).getLogo(j).getColorway(k).setConvertCode(ConvertColor.convertTo(this.expendedorderdata.getSet(i).getLogo(j).getColorway(k).getLogoColorCode(), this.expendedorderdata.getSet(i).getLogo(j).getColorway(k).getThreadType(), this.expendedorderdata.getSet(i).getLogo(j).getThreadBrand(), this.sqltable));
          this.expendedorderdata.getSet(i).getLogo(j).getColorway(k).setConvertColorName(threadtoconvert(this.expendedorderdata.getSet(i).getLogo(j).getColorway(k).getConvertCode(), this.expendedorderdata.getSet(i).getLogo(j).getThreadBrand()));
          this.expendedorderdata.getSet(i).getLogo(j).getColorway(k).setLogoColorCodeName(threadtoconvert(this.expendedorderdata.getSet(i).getLogo(j).getColorway(k).getLogoColorCode(), this.expendedorderdata.getSet(i).getLogo(j).getColorway(k).getThreadType()));
          this.expendedorderdata.getSet(i).getLogo(j).getColorway(k).setLogoColorCodeValue(threadtocolorvalue(this.expendedorderdata.getSet(i).getLogo(j).getColorway(k).getLogoColorCode(), this.expendedorderdata.getSet(i).getLogo(j).getColorway(k).getThreadType()));
          this.expendedorderdata.getSet(i).getLogo(j).getColorway(k).setConvertCodeColorValue(threadtocolorvalue(this.expendedorderdata.getSet(i).getLogo(j).getColorway(k).getConvertCode(), this.expendedorderdata.getSet(i).getLogo(j).getThreadBrand()));
        }
        
        for (int k = 0; k < this.expendedorderdata.getSet(i).getLogo(j).getDecorationCount().intValue(); k++) {
          if (this.sqltable.makeTable("SELECT * FROM `list_decorations_overall` WHERE `name` = '" + this.expendedorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getName() + "' LIMIT 1").booleanValue()) {
            this.expendedorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).setField1Name((String)this.sqltable.getCell("field1"));
            this.expendedorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).setField2Name((String)this.sqltable.getCell("field2"));
            this.expendedorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).setField3Name((String)this.sqltable.getCell("field3"));
            this.expendedorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).setField4Name((String)this.sqltable.getCell("field4"));
          } else if (this.sqltable.makeTable("SELECT * FROM `list_decorations_overall` WHERE `name` = 'Other' LIMIT 1").booleanValue()) {
            this.expendedorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).setField1Name((String)this.sqltable.getCell("field1"));
            this.expendedorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).setField2Name((String)this.sqltable.getCell("field2"));
            this.expendedorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).setField3Name((String)this.sqltable.getCell("field3"));
            this.expendedorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).setField4Name((String)this.sqltable.getCell("field4"));
          }
        }
      }
    }
    



    if (this.expendedorderdata.getOrderType() == OrderData.OrderType.DOMESTIC) {
      if (this.expendedorderdata.getVendorInformation().getDigitizerVendor().getVendor() != null) {
        this.sqltable.makeTable("SELECT `name` FROM `list_vendors_domestic` WHERE `id` =" + this.expendedorderdata.getVendorInformation().getDigitizerVendor().getVendor());
        this.expendedorderdata.getVendorInformation().getDigitizerVendor().setVendorName((String)this.sqltable.getValue());
      }
      if (this.expendedorderdata.getVendorInformation().getHeatTransferVendor().getVendor() != null) {
        this.sqltable.makeTable("SELECT `name` FROM `list_vendors_domestic` WHERE `id` =" + this.expendedorderdata.getVendorInformation().getHeatTransferVendor().getVendor());
        this.expendedorderdata.getVendorInformation().getHeatTransferVendor().setVendorName((String)this.sqltable.getValue());
      }
      if (this.expendedorderdata.getVendorInformation().getPatchVendor().getVendor() != null) {
        this.sqltable.makeTable("SELECT `name` FROM `list_vendors_domestic` WHERE `id` =" + this.expendedorderdata.getVendorInformation().getPatchVendor().getVendor());
        this.expendedorderdata.getVendorInformation().getPatchVendor().setVendorName((String)this.sqltable.getValue());
      }
      if (this.expendedorderdata.getVendorInformation().getCADPrintVendor().getVendor() != null) {
        this.sqltable.makeTable("SELECT `name` FROM `list_vendors_domestic` WHERE `id` =" + this.expendedorderdata.getVendorInformation().getCADPrintVendor().getVendor());
        this.expendedorderdata.getVendorInformation().getCADPrintVendor().setVendorName((String)this.sqltable.getValue());
      }
      if (this.expendedorderdata.getVendorInformation().getScreenPrintVendor().getVendor() != null) {
        this.sqltable.makeTable("SELECT `name` FROM `list_vendors_domestic` WHERE `id` =" + this.expendedorderdata.getVendorInformation().getScreenPrintVendor().getVendor());
        this.expendedorderdata.getVendorInformation().getScreenPrintVendor().setVendorName((String)this.sqltable.getValue());
      }
      if (this.expendedorderdata.getVendorInformation().getDirectToGarmentVendor().getVendor() != null) {
        this.sqltable.makeTable("SELECT `name` FROM `list_vendors_domestic` WHERE `id` =" + this.expendedorderdata.getVendorInformation().getDirectToGarmentVendor().getVendor());
        this.expendedorderdata.getVendorInformation().getDirectToGarmentVendor().setVendorName((String)this.sqltable.getValue());
      }
      if (this.expendedorderdata.getVendorInformation().getEmbroideryVendor().getVendor() != null) {
        this.sqltable.makeTable("SELECT `name` FROM `list_vendors_domestic` WHERE `id` =" + this.expendedorderdata.getVendorInformation().getEmbroideryVendor().getVendor());
        this.expendedorderdata.getVendorInformation().getEmbroideryVendor().setVendorName((String)this.sqltable.getValue());
      }
    } else {
      String[] vendorkeys = (String[])this.expendedorderdata.getVendorInformation().getOverseasVendor().keySet().toArray(new String[0]);
      for (int i = 0; i < vendorkeys.length; i++) {
        if (((OrderDataVendorInformation)this.expendedorderdata.getVendorInformation().getOverseasVendor().get(vendorkeys[i])).getVendor() != null)
        {

          if (this.sqltable.makeTable("SELECT `name` FROM `list_vendors_overseas` WHERE `id` =" + ((OrderDataVendorInformation)this.expendedorderdata.getVendorInformation().getOverseasVendor().get(vendorkeys[i])).getVendor()).booleanValue()) {
            ((ExtendOrderDataVendorInformation)this.expendedorderdata.getVendorInformation().getOverseasVendor().get(vendorkeys[i])).setVendorName((String)this.sqltable.getValue());
          }
        }
      }
    }
    
    if ((this.expendedorderdata.getCustomerInformation().getEclipseAccountNumber() != null) && 
      (this.sqltable.makeTable("SELECT `showaddress` FROM `eclipse_virtualsample_settings` WHERE `eclipseaccount` =" + this.expendedorderdata.getCustomerInformation().getEclipseAccountNumber() + " LIMIT 1").booleanValue())) {
      this.expendedorderdata.getCustomerInformation().setVirtualSampleShowAddress(((Boolean)this.sqltable.getValue()).booleanValue());
    }
  }
  



  private int styletovendor(String stylenumber, String vendornumber)
  {
    if (this.sqltable.makeTable("SELECT `Vendor` FROM `styles_overseas` WHERE `Style` = '" + stylenumber + "' LIMIT 1").booleanValue())
      return Integer.valueOf((String)this.sqltable.getCell("Vendor")).intValue();
    if (this.sqltable.makeTable("SELECT `Vendor` FROM `styles_pre-designed` WHERE `Style` = '" + stylenumber + "' LIMIT 1").booleanValue())
      return Integer.valueOf((String)this.sqltable.getCell("Vendor")).intValue();
    if (this.sqltable.makeTable("SELECT `Primary VENDOR` FROM `styles_overseasinstock` WHERE `STYLE NUMBER` = '" + stylenumber + "' LIMIT 1").booleanValue()) {
      if ((vendornumber != null) && (!vendornumber.equals("")) && (!vendornumber.equals("Default"))) {
        return Integer.valueOf(vendornumber).intValue();
      }
      return Integer.valueOf((String)this.sqltable.getValue()).intValue();
    }
    if (this.sqltable.makeTable("SELECT `vendor` FROM `overseas_price_instockshirts` WHERE `stylenumber` = '" + stylenumber + "' LIMIT 1").booleanValue())
      return Integer.valueOf((String)this.sqltable.getCell("vendor")).intValue();
    if (this.sqltable.makeTable("SELECT `vendor` FROM `overseas_price_instockflats` WHERE `stylenumber` = '" + stylenumber + "' LIMIT 1").booleanValue()) {
      return Integer.valueOf((String)this.sqltable.getCell("vendor")).intValue();
    }
    return 0;
  }
  
  private String threadtocolorvalue(String code, String threadbrand) {
    if ((threadbrand == null) || (threadbrand.equals(""))) {
      return "#000000";
    }
    if (code.startsWith("Match ")) {
      String ottocode = code.substring("Match OTTO ".length());
      if (this.sqltable.makeTable("SELECT `colorvalue` FROM `thread_ottocolors` WHERE `code` = '" + ottocode + "' LIMIT 1").booleanValue()) {
        return (String)this.sqltable.getValue();
      }
    }
    
    String tempcode = code;
    if (code.toLowerCase().startsWith("b")) {
      tempcode = code.substring(1);
      try {
        Integer.valueOf(tempcode);
      } catch (Exception e) {
        tempcode = code;
      }
    }
    
    if (threadbrand.equals("Madeira Polyneon")) {
      if (this.sqltable.makeTable("SELECT `colorvalue` FROM `thread_madeirap` WHERE `code` = '" + tempcode + "' LIMIT 1").booleanValue()) {
        return (String)this.sqltable.getValue();
      }
    } else if (threadbrand.equals("Madeira Rayon")) {
      if (this.sqltable.makeTable("SELECT `colorvalue` FROM `thread_madeirar` WHERE `code` = '" + tempcode + "' LIMIT 1").booleanValue()) {
        return (String)this.sqltable.getValue();
      }
    } else if (threadbrand.equals("Pantone")) {
      if (this.sqltable.makeTable("SELECT `colorvalue` FROM `thread_pantone` WHERE `code` = '" + tempcode + "' LIMIT 1").booleanValue()) {
        return (String)this.sqltable.getValue();
      }
    } else if (threadbrand.equals("Robinson Rayon")) {
      if (this.sqltable.makeTable("SELECT `colorvalue` FROM `thread_robinsonr` WHERE `code` = '" + tempcode + "' LIMIT 1").booleanValue()) {
        return (String)this.sqltable.getValue();
      }
    } else if (threadbrand.equals("RA Super Brite Polyester")) {
      if (this.sqltable.makeTable("SELECT `colorvalue` FROM `thread_rasuperbritepolyester` WHERE `code` = '" + tempcode + "' LIMIT 1").booleanValue()) {
        return (String)this.sqltable.getValue();
      }
    } else if (threadbrand.equals("Fibres Rayon")) {
      if (this.sqltable.makeTable("SELECT `colorvalue` FROM `thread_fibresr` WHERE `code` = '" + tempcode + "' LIMIT 1").booleanValue()) {
        return (String)this.sqltable.getValue();
      }
    } else if ((threadbrand.equals("Match Cap Color")) || (threadbrand.equals("Match Product Color"))) {
      if (this.sqltable.makeTable("SELECT `colorvalue` FROM `thread_ottocolors` WHERE `code` = '" + tempcode + "' LIMIT 1").booleanValue()) {
        return (String)this.sqltable.getValue();
      }
    } else if (threadbrand.equals("Keep Colors"))
    {
      if (tempcode.startsWith("Madeira Polyneon ")) {
        if (this.sqltable.makeTable("SELECT `colorvalue` FROM `thread_madeirap` WHERE `code` = '" + tempcode.substring("Madeira Polyneon ".length()) + "' LIMIT 1").booleanValue()) {
          return (String)this.sqltable.getValue();
        }
      } else if (tempcode.startsWith("Madeira Rayon ")) {
        if (this.sqltable.makeTable("SELECT `colorvalue` FROM `thread_madeirar` WHERE `code` = '" + tempcode.substring("Madeira Rayon ".length()) + "' LIMIT 1").booleanValue()) {
          return (String)this.sqltable.getValue();
        }
      } else if (tempcode.startsWith("PMS")) {
        if (this.sqltable.makeTable("SELECT `colorvalue` FROM `thread_pantone` WHERE `code` = '" + tempcode.substring("PMS".length()) + "' LIMIT 1").booleanValue()) {
          return (String)this.sqltable.getValue();
        }
      } else if (tempcode.startsWith("Robinson Rayon ")) {
        if (this.sqltable.makeTable("SELECT `colorvalue` FROM `thread_robinsonr` WHERE `code` = '" + tempcode.substring("Robinson Rayon ".length()) + "' LIMIT 1").booleanValue()) {
          return (String)this.sqltable.getValue();
        }
      } else if (tempcode.startsWith("RA Super Brite Polyester ")) {
        if (this.sqltable.makeTable("SELECT `colorvalue` FROM `thread_rasuperbritepolyester` WHERE `code` = '" + tempcode.substring("RA Super Brite Polyester ".length()) + "' LIMIT 1").booleanValue()) {
          return (String)this.sqltable.getValue();
        }
      } else if ((tempcode.startsWith("Fibres Rayon ")) && 
        (this.sqltable.makeTable("SELECT `colorvalue` FROM `thread_fibresr` WHERE `code` = '" + tempcode.substring("Fibres Rayon ".length()) + "' LIMIT 1").booleanValue())) {
        return (String)this.sqltable.getValue();
      }
    }
    


    return "#000000";
  }
  
  private String threadtoconvert(String code, String threadbrand)
  {
    if ((threadbrand == null) || (threadbrand.equals("")))
      return "";
    if ((threadbrand.equals("Match Cap Color")) || (threadbrand.equals("Match Product Color"))) {
      return convertCodeToName(code);
    }
    if (code.startsWith("Match OTTO ")) {
      String ottocode = code.substring("Match OTTO ".length());
      return convertCodeToName(ottocode);
    }
    
    String tempcode = code;
    if (code.toLowerCase().startsWith("b")) {
      tempcode = code.substring(1);
      try {
        Integer.valueOf(tempcode);
      } catch (Exception e) {
        tempcode = code;
      }
    }
    
    if (threadbrand.equals("Madeira Polyneon")) {
      if (this.sqltable.makeTable("SELECT `colorname` FROM `thread_madeirap` WHERE `code` = '" + tempcode + "' LIMIT 1").booleanValue()) {
        return (String)this.sqltable.getValue();
      }
    } else if (threadbrand.equals("Madeira Rayon")) {
      if (this.sqltable.makeTable("SELECT `colorname` FROM `thread_madeirar` WHERE `code` = '" + tempcode + "' LIMIT 1").booleanValue()) {
        return (String)this.sqltable.getValue();
      }
    } else if (threadbrand.equals("Pantone")) {
      if (this.sqltable.makeTable("SELECT `colorname` FROM `thread_pantone` WHERE `code` = '" + tempcode + "' LIMIT 1").booleanValue()) {
        return (String)this.sqltable.getValue();
      }
    } else if (threadbrand.equals("Robinson Rayon")) {
      if (this.sqltable.makeTable("SELECT `colorname` FROM `thread_robinsonr` WHERE `code` = '" + tempcode + "' LIMIT 1").booleanValue()) {
        return (String)this.sqltable.getValue();
      }
    } else if (threadbrand.equals("RA Super Brite Polyester")) {
      if (this.sqltable.makeTable("SELECT `colorname` FROM `thread_rasuperbritepolyester` WHERE `code` = '" + tempcode + "' LIMIT 1").booleanValue()) {
        return (String)this.sqltable.getValue();
      }
    } else if (threadbrand.equals("Fibres Rayon")) {
      if (this.sqltable.makeTable("SELECT `colorname` FROM `thread_fibresr` WHERE `code` = '" + tempcode + "' LIMIT 1").booleanValue()) {
        return (String)this.sqltable.getValue();
      }
    } else if (threadbrand.equals("Keep Colors"))
    {
      if (tempcode.startsWith("Madeira Polyneon ")) {
        if (this.sqltable.makeTable("SELECT `colorname` FROM `thread_madeirap` WHERE `code` = '" + tempcode.substring("Madeira Polyneon ".length()) + "' LIMIT 1").booleanValue()) {
          return (String)this.sqltable.getValue();
        }
      } else if (tempcode.startsWith("Madeira Rayon ")) {
        if (this.sqltable.makeTable("SELECT `colorname` FROM `thread_madeirar` WHERE `code` = '" + tempcode.substring("Madeira Rayon ".length()) + "' LIMIT 1").booleanValue()) {
          return (String)this.sqltable.getValue();
        }
      } else if (tempcode.startsWith("PMS")) {
        if (this.sqltable.makeTable("SELECT `colorname` FROM `thread_pantone` WHERE `code` = '" + tempcode.substring("PMS".length()) + "' LIMIT 1").booleanValue()) {
          return (String)this.sqltable.getValue();
        }
      } else if (tempcode.startsWith("Robinson Rayon ")) {
        if (this.sqltable.makeTable("SELECT `colorname` FROM `thread_robinsonr` WHERE `code` = '" + tempcode.substring("Robinson Rayon ".length()) + "' LIMIT 1").booleanValue()) {
          return (String)this.sqltable.getValue();
        }
      } else if (tempcode.startsWith("RA Super Brite Polyester ")) {
        if (this.sqltable.makeTable("SELECT `colorname` FROM `thread_rasuperbritepolyester` WHERE `code` = '" + tempcode.substring("RA Super Brite Polyester ".length()) + "' LIMIT 1").booleanValue()) {
          return (String)this.sqltable.getValue();
        }
      } else if ((tempcode.startsWith("Fibres Rayon ")) && 
        (this.sqltable.makeTable("SELECT `colorname` FROM `thread_fibresr` WHERE `code` = '" + tempcode.substring("Fibres Rayon ".length()) + "' LIMIT 1").booleanValue())) {
        return (String)this.sqltable.getValue();
      }
    }
    


    return "";
  }
  
  public String convertCodeToName(String code)
  {
    if (this.ottocodetocolorcache == null) {
      this.ottocodetocolorcache = new OTTOCodeToColorCached(this.sqltable);
    }
    return this.ottocodetocolorcache.getName(code);
  }
  
  public ExtendOrderData getExpandedOrderData() {
    return this.expendedorderdata;
  }
}
