package com.ottocap.NewWorkFlow.server;

import com.ottocap.NewWorkFlow.client.OrderData.OrderData;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataCustomerInformation;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataItem;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogo;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataVendorInformation;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataVendors;
import com.ottocap.NewWorkFlow.client.StyleInformationData.StyleType;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderData;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataCustomerInformation;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataItem;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataLogo;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataLogoColorway;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataLogoDecoration;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataSet;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataVendorInformation;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataVendors;
import java.util.TreeMap;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.jooq.SelectJoinStep;
import org.jooq.SelectOffsetStep;
import org.jooq.SelectSelectStep;
import org.jooq.SelectWhereStep;
import org.jooq.TableLike;
import org.jooq.impl.DSL;

public class JOOQ_OrderExpander
{
  private ExtendOrderData expendedorderdata = new ExtendOrderData();
  private JOOQ_OTTOCodeToColorCached ottocodetocolorcache = null;
  
  public JOOQ_OrderExpander(int hiddenkey, javax.servlet.http.HttpSession httpsession) throws Exception {
    com.ottocap.NewWorkFlow.server.RPCService.JOOQ_GetOrder getorder = new com.ottocap.NewWorkFlow.server.RPCService.JOOQ_GetOrder(hiddenkey, httpsession);
    OrderData theorderdata = getorder.getOrder();
    doOrderExpander(theorderdata);
  }
  
  public JOOQ_OrderExpander(OrderData theorderdata) throws Exception {
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
    
    if (theorderdata.getOrderType() == com.ottocap.NewWorkFlow.client.OrderData.OrderData.OrderType.DOMESTIC) {
      this.expendedorderdata.setOrderType(com.ottocap.NewWorkFlow.client.OrderData.OrderData.OrderType.DOMESTIC);
    } else if (theorderdata.getOrderType() == com.ottocap.NewWorkFlow.client.OrderData.OrderData.OrderType.OVERSEAS) {
      this.expendedorderdata.setOrderType(com.ottocap.NewWorkFlow.client.OrderData.OrderData.OrderType.OVERSEAS);
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
      com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataDiscount mydiscount = new com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataDiscount();
      mydiscount.setAmount(theorderdatadiscount.getAmount());
      mydiscount.setReason(theorderdatadiscount.getReason());
      this.expendedorderdata.addDiscountItem(mydiscount);
    }
    
    TreeMap<Integer, java.util.TreeSet<String>> vendoralreadydig = new TreeMap();
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
          
          if ((theorderdata.getOrderType() == com.ottocap.NewWorkFlow.client.OrderData.OrderData.OrderType.OVERSEAS) || (theorderdatalogo.getDecoration() == null)) {
            mylogo.setStitchCount(theorderdatalogo.getStitchCount());
            mylogo.setSpecialInk(theorderdatalogo.getSpecialInk());
            mylogo.setMetallicThread(theorderdatalogo.getMetallicThread());
          }
          else if (theorderdatalogo.getDecoration().equals("Screen Print")) {
            mylogo.setStitchCount(null);
            mylogo.setSpecialInk(false);
            mylogo.setMetallicThread(false);
          }
          else if (theorderdatalogo.getDecoration().equals("Heat Transfer")) {
            mylogo.setStitchCount(null);
            mylogo.setSpecialInk(theorderdatalogo.getSpecialInk());
            mylogo.setMetallicThread(false);
            mylogo.setFlashCharge(theorderdatalogo.getFlashCharge());
          }
          else if (theorderdatalogo.getDecoration().equals("Direct To Garment")) {
            mylogo.setStitchCount(null);
            mylogo.setSpecialInk(false);
            mylogo.setMetallicThread(false);
          }
          else if ((theorderdatalogo.getDecoration().equals("Flat Embroidery")) || (theorderdatalogo.getDecoration().equals("3D Embroidery"))) {
            mylogo.setStitchCount(theorderdatalogo.getStitchCount());
            mylogo.setSpecialInk(false);
            mylogo.setMetallicThread(theorderdatalogo.getMetallicThread());
          }
          else {
            mylogo.setStitchCount(Integer.valueOf(0));
            mylogo.setSpecialInk(false);
            mylogo.setMetallicThread(false);
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
            
            if ((theorderdata.getOrderType() == com.ottocap.NewWorkFlow.client.OrderData.OrderData.OrderType.OVERSEAS) || (theorderdatalogo.getDecoration() == null)) {
              mylogo.setDigitizing(theorderdatalogo.getDigitizing());
              mylogo.setFilmSetupCharge(theorderdatalogo.getFilmSetupCharge());
              mylogo.setFilmCharge(theorderdatalogo.getFilmCharge());
              mylogo.setPMSMatch(theorderdatalogo.getPMSMatch());
              mylogo.setFlashCharge(theorderdatalogo.getFlashCharge());
              mylogo.setTapeEdit(theorderdatalogo.getTapeEdit());
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
          if ((theorderdata.getOrderType() == com.ottocap.NewWorkFlow.client.OrderData.OrderData.OrderType.OVERSEAS) && 
            (theorderdatalogo.getDigitizing())) {
            int currentvendor = styletovendor(theorderdataitem.getStyleNumber(), theorderdataitem.getVendorNumber());
            if (vendoralreadydig.containsKey(Integer.valueOf(currentvendor))) {
              java.util.TreeSet<String> thefilenames = (java.util.TreeSet)vendoralreadydig.get(Integer.valueOf(currentvendor));
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
              java.util.TreeSet<String> newvalue = new java.util.TreeSet();
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
            com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogoColorway theorderdatalogocolorway = theorderdatalogo.getColorway(l);
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
            com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogoDecoration theorderdatalogodecoration = theorderdatalogo.getDecoration(Integer.valueOf(l));
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
    


    this.expendedorderdata.setOrderTypeFolder(this.expendedorderdata.getOrderType() == com.ottocap.NewWorkFlow.client.OrderData.OrderData.OrderType.DOMESTIC ? "Domestic" : "Overseas");
    Record sqlrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "employees" })).where(new Condition[] { DSL.fieldByName(new String[] { "username" }).equal(this.expendedorderdata.getEmployeeId()) }).limit(1).fetchOne();
    if ((sqlrecord != null) && (sqlrecord.size() != 0)) {
      this.expendedorderdata.setEmployeeFullName(sqlrecord.getValue(DSL.fieldByName(new String[] { "firstname" })) + " " + sqlrecord.getValue(DSL.fieldByName(new String[] { "lastname" })));
      this.expendedorderdata.setEmployeeEmail((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "email" })));
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
        
        if ((this.expendedorderdata.getSet(i).getItem(j).getClosureStyleNumber() != null) && (!this.expendedorderdata.getSet(i).getItem(j).getClosureStyleNumber().equals(""))) if (((sqlrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "name" })).from(new TableLike[] { DSL.tableByName(new String[] { "overseas_price_styles_closure" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "style" }).equal(this.expendedorderdata.getSet(i).getItem(j).getClosureStyleNumber()) }).limit(1).fetchOne()) != null) && (sqlrecord.size() != 0)) {
            this.expendedorderdata.getSet(i).getItem(j).setClosureStyleNumberName(this.expendedorderdata.getSet(i).getItem(j).getClosureStyleNumber() + " - " + (String)sqlrecord.getValue(DSL.fieldByName(new String[] { "name" })));
            break label5338; }
        this.expendedorderdata.getSet(i).getItem(j).setClosureStyleNumberName(this.expendedorderdata.getSet(i).getItem(j).getClosureStyleNumber());
        
        label5338:
        if ((this.expendedorderdata.getSet(i).getItem(j).getEyeletStyleNumber() != null) && (!this.expendedorderdata.getSet(i).getItem(j).getEyeletStyleNumber().equals(""))) if (((sqlrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "name" })).from(new TableLike[] { DSL.tableByName(new String[] { "overseas_price_styles_eyelet" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "style" }).equal(this.expendedorderdata.getSet(i).getItem(j).getEyeletStyleNumber()) }).limit(1).fetchOne()) != null) && (sqlrecord.size() != 0)) {
            this.expendedorderdata.getSet(i).getItem(j).setEyeletStyleNumberName(this.expendedorderdata.getSet(i).getItem(j).getEyeletStyleNumber() + " - " + (String)sqlrecord.getValue(DSL.fieldByName(new String[] { "name" })));
            break label5623; }
        this.expendedorderdata.getSet(i).getItem(j).setEyeletStyleNumberName(this.expendedorderdata.getSet(i).getItem(j).getEyeletStyleNumber());
        
        label5623:
        if ((this.expendedorderdata.getSet(i).getItem(j).getSweatbandStyleNumber() != null) && (!this.expendedorderdata.getSet(i).getItem(j).getSweatbandStyleNumber().equals(""))) if (((sqlrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "name" })).from(new TableLike[] { DSL.tableByName(new String[] { "overseas_price_styles_sweatband" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "style" }).equal(this.expendedorderdata.getSet(i).getItem(j).getSweatbandStyleNumber()) }).limit(1).fetchOne()) != null) && (sqlrecord.size() != 0)) {
            this.expendedorderdata.getSet(i).getItem(j).setSweatbandStyleNumberName(this.expendedorderdata.getSet(i).getItem(j).getSweatbandStyleNumber() + " - " + (String)sqlrecord.getValue(DSL.fieldByName(new String[] { "name" })));
            break label5908; }
        this.expendedorderdata.getSet(i).getItem(j).setSweatbandStyleNumberName(this.expendedorderdata.getSet(i).getItem(j).getSweatbandStyleNumber());
        
        label5908:
        if ((this.expendedorderdata.getSet(i).getItem(j).getVisorStyleNumber() != null) && (!this.expendedorderdata.getSet(i).getItem(j).getVisorStyleNumber().equals(""))) if (((sqlrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "name" })).from(new TableLike[] { DSL.tableByName(new String[] { "overseas_price_styles_visor" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "style" }).equal(this.expendedorderdata.getSet(i).getItem(j).getVisorStyleNumber()) }).limit(1).fetchOne()) != null) && (sqlrecord.size() != 0)) {
            this.expendedorderdata.getSet(i).getItem(j).setVisorStyleNumberName(this.expendedorderdata.getSet(i).getItem(j).getVisorStyleNumber() + " - " + (String)sqlrecord.getValue(DSL.fieldByName(new String[] { "name" })));
            break label6193; }
        this.expendedorderdata.getSet(i).getItem(j).setVisorStyleNumberName(this.expendedorderdata.getSet(i).getItem(j).getVisorStyleNumber());
        
        label6193:
        sqlrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "exchangerate" })).from(new TableLike[] { DSL.tableByName(new String[] { "ordermain" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "hiddenkey" }).equal(theorderdata.getHiddenKey()) }).limit(1).fetchOne();
        if (sqlrecord != null) {} this.expendedorderdata.setExchangeRate(sqlrecord.getValue(DSL.fieldByName(tmp6304_6301)) != null ? ((Double)sqlrecord.getValue(DSL.fieldByName(new String[] { "exchangerate" }))).doubleValue() : 0.0D);
        
        if (this.expendedorderdata.getOrderType() == com.ottocap.NewWorkFlow.client.OrderData.OrderData.OrderType.DOMESTIC)
        {
          if (((sqlrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_domestic" })).where(new Condition[] { DSL.fieldByName(new String[] { "sku" }).equal(this.expendedorderdata.getSet(i).getItem(j).getStyleNumber()) }).limit(1).fetchOne()) != null) && (sqlrecord.size() != 0)) {
            this.expendedorderdata.getSet(i).getItem(j).setStyleType(StyleInformationData.StyleType.DOMESTIC);
            this.expendedorderdata.getSet(i).getItem(j).setCrownStyle((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "crownstyle" })));
            this.expendedorderdata.getSet(i).getItem(j).setNumberOfPanels((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "panel" })));
            this.expendedorderdata.getSet(i).getItem(j).setCategory(((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "category" }))).trim());
            this.expendedorderdata.getSet(i).getItem(j).setGoodsType(((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "type" }))).trim());
            this.expendedorderdata.getSet(i).getItem(j).setSameStyle(((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "samestyle" }))).trim());
            
            if (((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "construction" }))).equals("U")) {
              this.expendedorderdata.getSet(i).getItem(j).setCrownConstruction("Unstructured");
            } else if (((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "construction" }))).equals("S")) {
              this.expendedorderdata.getSet(i).getItem(j).setCrownConstruction("Structured");
            } else if (((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "construction" }))).equals("F")) {
              this.expendedorderdata.getSet(i).getItem(j).setCrownConstruction("Structured - Half Buckram");
            } else {
              this.expendedorderdata.getSet(i).getItem(j).setCrownConstruction((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "construction" })));
            }
            
            Record sqlrecord2 = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "name" })).from(new TableLike[] { DSL.tableByName(new String[] { "list_domestic_materials" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "id" }).equal(sqlrecord.getValue(DSL.fieldByName(new String[] { "fabric" }))) }).limit(1).fetchOne();
            if ((sqlrecord2 != null) && (sqlrecord2.size() != 0)) {
              this.expendedorderdata.getSet(i).getItem(j).setMaterial((String)sqlrecord2.getValue(DSL.fieldByName(new String[] { "name" })));



            }
            




          }
          else if (((sqlrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_domestic_specials" })).where(new Condition[] { DSL.fieldByName(new String[] { "style" }).equal(this.expendedorderdata.getSet(i).getItem(j).getStyleNumber()) }).limit(1).fetchOne()) != null) && (sqlrecord.size() != 0)) {
            this.expendedorderdata.getSet(i).getItem(j).setStyleType(StyleInformationData.StyleType.DOMESTIC_SPECIAL);
            String basestyle = (String)sqlrecord.getValue(DSL.fieldByName(new String[] { "basestyle" }));
            
            if (((sqlrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_domestic" })).where(new Condition[] { DSL.fieldByName(new String[] { "sku" }).equal(basestyle) }).limit(1).fetchOne()) != null) && (sqlrecord.size() != 0))
            {
              this.expendedorderdata.getSet(i).getItem(j).setCrownStyle((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "crownstyle" })));
              this.expendedorderdata.getSet(i).getItem(j).setNumberOfPanels((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "panel" })));
              this.expendedorderdata.getSet(i).getItem(j).setCategory(((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "category" }))).trim());
              this.expendedorderdata.getSet(i).getItem(j).setGoodsType(((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "type" }))).trim());
              this.expendedorderdata.getSet(i).getItem(j).setSameStyle(((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "samestyle" }))).trim());
              
              if (((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "construction" }))).equals("U")) {
                this.expendedorderdata.getSet(i).getItem(j).setCrownConstruction("Unstructured");
              } else if (((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "construction" }))).equals("S")) {
                this.expendedorderdata.getSet(i).getItem(j).setCrownConstruction("Structured");
              } else if (((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "construction" }))).equals("F")) {
                this.expendedorderdata.getSet(i).getItem(j).setCrownConstruction("Structured - Half Buckram");
              } else {
                this.expendedorderdata.getSet(i).getItem(j).setCrownConstruction((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "construction" })));
              }
              
              Record sqlrecord2 = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "name" })).from(new TableLike[] { DSL.tableByName(new String[] { "list_domestic_materials" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "id" }).equal(sqlrecord.getValue(DSL.fieldByName(new String[] { "fabric" }))) }).limit(1).fetchOne();
              if ((sqlrecord2 != null) && (sqlrecord2.size() != 0)) {
                this.expendedorderdata.getSet(i).getItem(j).setMaterial((String)sqlrecord2.getValue(DSL.fieldByName(new String[] { "name" })));
              }
            }
            else if (((sqlrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_lackpard" })).where(new Condition[] { DSL.fieldByName(new String[] { "Style" }).equal(basestyle) }).limit(1).fetchOne()) != null) && (sqlrecord.size() != 0)) {
              this.expendedorderdata.getSet(i).getItem(j).setCategory(((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Main Category" }))).trim());
              this.expendedorderdata.getSet(i).getItem(j).setGoodsType("3D");
              this.expendedorderdata.getSet(i).getItem(j).setSameStyle(((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Style" }))).trim());
            }
            
          }
          else if (((sqlrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_domestic_shirts" })).where(new Condition[] { DSL.fieldByName(new String[] { "sku" }).equal(this.expendedorderdata.getSet(i).getItem(j).getStyleNumber()) }).limit(1).fetchOne()) != null) && (sqlrecord.size() != 0)) {
            this.expendedorderdata.getSet(i).getItem(j).setStyleType(StyleInformationData.StyleType.DOMESTIC_SHIRTS);
            this.expendedorderdata.getSet(i).getItem(j).setCategory(((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "category" }))).trim());
            this.expendedorderdata.getSet(i).getItem(j).setGoodsType(((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "type" }))).trim());
            this.expendedorderdata.getSet(i).getItem(j).setSameStyle(((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "samestyle" }))).trim());
            


            Record sqlrecord2 = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "name" })).from(new TableLike[] { DSL.tableByName(new String[] { "list_domestic_materials" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "id" }).equal(sqlrecord.getValue(DSL.fieldByName(new String[] { "fabric" }))) }).limit(1).fetchOne();
            if ((sqlrecord2 != null) && (sqlrecord2.size() != 0)) {
              this.expendedorderdata.getSet(i).getItem(j).setMaterial((String)sqlrecord2.getValue(DSL.fieldByName(new String[] { "name" })));
            }
          }
          else if (((sqlrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_domestic_sweatshirts" })).where(new Condition[] { DSL.fieldByName(new String[] { "sku" }).equal(this.expendedorderdata.getSet(i).getItem(j).getStyleNumber()) }).limit(1).fetchOne()) != null) && (sqlrecord.size() != 0)) {
            this.expendedorderdata.getSet(i).getItem(j).setStyleType(StyleInformationData.StyleType.DOMESTIC_SWEATSHIRTS);
            this.expendedorderdata.getSet(i).getItem(j).setCategory(((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "category" }))).trim());
            this.expendedorderdata.getSet(i).getItem(j).setGoodsType(((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "type" }))).trim());
            this.expendedorderdata.getSet(i).getItem(j).setSameStyle(((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "samestyle" }))).trim());
            


            Record sqlrecord2 = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "name" })).from(new TableLike[] { DSL.tableByName(new String[] { "list_domestic_materials" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "id" }).equal(sqlrecord.getValue(DSL.fieldByName(new String[] { "fabric" }))) }).limit(1).fetchOne();
            if ((sqlrecord2 != null) && (sqlrecord2.size() != 0)) {
              this.expendedorderdata.getSet(i).getItem(j).setMaterial((String)sqlrecord2.getValue(DSL.fieldByName(new String[] { "name" })));
            }
          }
          else if (((sqlrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_lackpard" })).where(new Condition[] { DSL.fieldByName(new String[] { "Style" }).equal(this.expendedorderdata.getSet(i).getItem(j).getStyleNumber()) }).limit(1).fetchOne()) != null) && (sqlrecord.size() != 0)) {
            this.expendedorderdata.getSet(i).getItem(j).setStyleType(StyleInformationData.StyleType.DOMESTIC_LACKPARD);
            this.expendedorderdata.getSet(i).getItem(j).setCategory(((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Main Category" }))).trim());
            this.expendedorderdata.getSet(i).getItem(j).setGoodsType("3D");
            this.expendedorderdata.getSet(i).getItem(j).setSameStyle(((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Style" }))).trim());

          }
          else if (((sqlrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_domestic_totebags" })).where(new Condition[] { DSL.fieldByName(new String[] { "sku" }).equal(this.expendedorderdata.getSet(i).getItem(j).getStyleNumber()) }).limit(1).fetchOne()) != null) && (sqlrecord.size() != 0)) {
            this.expendedorderdata.getSet(i).getItem(j).setStyleType(StyleInformationData.StyleType.DOMESTIC_TOTEBAGS);
            

            this.expendedorderdata.getSet(i).getItem(j).setCategory(((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "category" }))).trim());
            this.expendedorderdata.getSet(i).getItem(j).setGoodsType(((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "type" }))).trim());
            this.expendedorderdata.getSet(i).getItem(j).setSameStyle(((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "samestyle" }))).trim());
            


            Record sqlrecord2 = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "name" })).from(new TableLike[] { DSL.tableByName(new String[] { "list_domestic_materials" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "id" }).equal(sqlrecord.getValue(DSL.fieldByName(new String[] { "fabric" }))) }).limit(1).fetchOne();
            if ((sqlrecord2 != null) && (sqlrecord2.size() != 0)) {
              this.expendedorderdata.getSet(i).getItem(j).setMaterial((String)sqlrecord2.getValue(DSL.fieldByName(new String[] { "name" })));
            }
          }
          else if (((sqlrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_domestic_aprons" })).where(new Condition[] { DSL.fieldByName(new String[] { "sku" }).equal(this.expendedorderdata.getSet(i).getItem(j).getStyleNumber()) }).limit(1).fetchOne()) != null) && (sqlrecord.size() != 0)) {
            this.expendedorderdata.getSet(i).getItem(j).setStyleType(StyleInformationData.StyleType.DOMESTIC_APRONS);
            

            this.expendedorderdata.getSet(i).getItem(j).setCategory(((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "category" }))).trim());
            this.expendedorderdata.getSet(i).getItem(j).setGoodsType(((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "type" }))).trim());
            this.expendedorderdata.getSet(i).getItem(j).setSameStyle(((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "samestyle" }))).trim());
            


            Record sqlrecord2 = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "name" })).from(new TableLike[] { DSL.tableByName(new String[] { "list_domestic_materials" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "id" }).equal(sqlrecord.getValue(DSL.fieldByName(new String[] { "fabric" }))) }).limit(1).fetchOne();
            if ((sqlrecord2 != null) && (sqlrecord2.size() != 0)) {
              this.expendedorderdata.getSet(i).getItem(j).setMaterial((String)sqlrecord2.getValue(DSL.fieldByName(new String[] { "name" })));
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
          if (((sqlrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_overseas" })).where(new Condition[] { DSL.fieldByName(new String[] { "Style" }).equal(this.expendedorderdata.getSet(i).getItem(j).getStyleNumber()) }).limit(1).fetchOne()) != null) && (sqlrecord.size() != 0)) {
            this.expendedorderdata.getSet(i).getItem(j).setStyleType(StyleInformationData.StyleType.OVERSEAS);
            this.expendedorderdata.getSet(i).getItem(j).setCrownStyle((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Crown Style" })));
            this.expendedorderdata.getSet(i).getItem(j).setMaterial((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Material" })));
            this.expendedorderdata.getSet(i).getItem(j).setNumberOfPanels((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Panel" })));
            this.expendedorderdata.getSet(i).getItem(j).setCategory(((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Category" }))).trim());
            this.expendedorderdata.getSet(i).getItem(j).setOverseasVendorNumber(Integer.valueOf((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Vendor" }))).intValue());
            
            if (((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Construction" }))).equals("U")) {
              this.expendedorderdata.getSet(i).getItem(j).setCrownConstruction("Unstructured");
            } else if (((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Construction" }))).equals("S")) {
              this.expendedorderdata.getSet(i).getItem(j).setCrownConstruction("Structured");
            } else if (((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Construction" }))).equals("F")) {
              this.expendedorderdata.getSet(i).getItem(j).setCrownConstruction("Structured - Half Buckram");
            } else {
              this.expendedorderdata.getSet(i).getItem(j).setCrownConstruction((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Construction" })));
            }
          }
          else if (((sqlrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_pre-designed" })).where(new Condition[] { DSL.fieldByName(new String[] { "Style" }).equal(this.expendedorderdata.getSet(i).getItem(j).getStyleNumber()) }).limit(1).fetchOne()) != null) && (sqlrecord.size() != 0)) {
            this.expendedorderdata.getSet(i).getItem(j).setStyleType(StyleInformationData.StyleType.OVERSEAS_PREDESIGNED);
            this.expendedorderdata.getSet(i).getItem(j).setMaterial((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Material Description" })));
            this.expendedorderdata.getSet(i).getItem(j).setCrownConstruction((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Structured/ Unstructured" })));
            this.expendedorderdata.getSet(i).getItem(j).setNumberOfPanels((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Panels" })));
            this.expendedorderdata.getSet(i).getItem(j).setCategory(((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Category" }))).trim());
            this.expendedorderdata.getSet(i).getItem(j).setOverseasVendorNumber(Integer.valueOf((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Vendor" }))).intValue());
            
            if ((this.expendedorderdata.getSet(i).getItem(j).getStyleNumber().equals("888-A")) || (this.expendedorderdata.getSet(i).getItem(j).getStyleNumber().equals("888-B")) || (this.expendedorderdata.getSet(i).getItem(j).getStyleNumber().equals("888-C")) || (this.expendedorderdata.getSet(i).getItem(j).getStyleNumber().equals("888-D")) || (this.expendedorderdata.getSet(i).getItem(j).getStyleNumber().equals("888-E")) || (this.expendedorderdata.getSet(i).getItem(j).getStyleNumber().equals("888-F")) || (this.expendedorderdata.getSet(i).getItem(j).getStyleNumber().equals("888-G")) || (this.expendedorderdata.getSet(i).getItem(j).getStyleNumber().equals("888-H")) || (this.expendedorderdata.getSet(i).getItem(j).getStyleNumber().equals("888-J")) || (this.expendedorderdata.getSet(i).getItem(j).getStyleNumber().equals("888-L")) || (this.expendedorderdata.getSet(i).getItem(j).getStyleNumber().equals("888-M")) || (this.expendedorderdata.getSet(i).getItem(j).getStyleNumber().equals("889-A")) || (this.expendedorderdata.getSet(i).getItem(j).getStyleNumber().equals("889-B")) || (this.expendedorderdata.getSet(i).getItem(j).getStyleNumber().equals("889-C")) || 
              (this.expendedorderdata.getSet(i).getItem(j).getStyleNumber().equals("889-D")) || (this.expendedorderdata.getSet(i).getItem(j).getStyleNumber().equals("889-E")) || (this.expendedorderdata.getSet(i).getItem(j).getStyleNumber().equals("889-F")) || (this.expendedorderdata.getSet(i).getItem(j).getStyleNumber().equals("889-G")) || (this.expendedorderdata.getSet(i).getItem(j).getStyleNumber().equals("889-H")) || (this.expendedorderdata.getSet(i).getItem(j).getStyleNumber().equals("889-J")) || (this.expendedorderdata.getSet(i).getItem(j).getStyleNumber().equals("889-L")) || (this.expendedorderdata.getSet(i).getItem(j).getStyleNumber().equals("889-M"))) {
              this.expendedorderdata.getSet(i).getItem(j).setStyleType(StyleInformationData.StyleType.OVERSEAS_NONOTTO);
            } else if ((this.expendedorderdata.getSet(i).getItem(j).getStyleNumber().equals("888-I")) || (this.expendedorderdata.getSet(i).getItem(j).getStyleNumber().equals("889-I"))) {
              this.expendedorderdata.getSet(i).getItem(j).setStyleType(StyleInformationData.StyleType.OVERSEAS_NONOTTO_SHIRTS);
            }
          }
          else if (((sqlrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_overseasinstock" })).where(new Condition[] { DSL.fieldByName(new String[] { "STYLE NUMBER" }).equal(this.expendedorderdata.getSet(i).getItem(j).getStyleNumber()) }).limit(1).fetchOne()) != null) && (sqlrecord.size() != 0)) {
            this.expendedorderdata.getSet(i).getItem(j).setStyleType(StyleInformationData.StyleType.OVERSEAS_INSTOCK);
            this.expendedorderdata.getSet(i).getItem(j).setOverseasVendorNumber(Integer.valueOf((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Primary VENDOR" }))).intValue());
            this.expendedorderdata.getSet(i).getItem(j).setCrownStyle((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Crown Style" })));
            this.expendedorderdata.getSet(i).getItem(j).setNumberOfPanels((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Panel" })));
            this.expendedorderdata.getSet(i).getItem(j).setCategory(((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Category" }))).trim());
            this.expendedorderdata.getSet(i).getItem(j).setSameStyle(((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Same Style" }))).trim());
            
            if (((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Construction" }))).equals("U")) {
              this.expendedorderdata.getSet(i).getItem(j).setCrownConstruction("Unstructured");
            } else if (((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Construction" }))).equals("S")) {
              this.expendedorderdata.getSet(i).getItem(j).setCrownConstruction("Structured");
            } else if (((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Construction" }))).equals("F")) {
              this.expendedorderdata.getSet(i).getItem(j).setCrownConstruction("Structured - Half Buckram");
            } else {
              this.expendedorderdata.getSet(i).getItem(j).setCrownConstruction((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Construction" })));
            }
            







            if ((this.expendedorderdata.getSet(i).getItem(j).getVendorNumber() != null) && (!this.expendedorderdata.getSet(i).getItem(j).getVendorNumber().equals("")) && (!this.expendedorderdata.getSet(i).getItem(j).getVendorNumber().equals("Default"))) {
              this.expendedorderdata.getSet(i).getItem(j).setOverseasVendorNumber(Integer.valueOf(this.expendedorderdata.getSet(i).getItem(j).getVendorNumber()).intValue());
              
              if (((sqlrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_overseasinstock" })).where(new Condition[] { DSL.fieldByName(new String[] { "STYLE NUMBER" }).equal(this.expendedorderdata.getSet(i).getItem(j).getStyleNumber()) }).and(DSL.fieldByName(new String[] { "Backup VENDOR" }).equal(this.expendedorderdata.getSet(i).getItem(j).getVendorNumber())).limit(1).fetchOne()) != null) && (sqlrecord.size() != 0))
              {
                this.expendedorderdata.getSet(i).getItem(j).setMaterial((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Fabric Name" })));
                this.expendedorderdata.getSet(i).getItem(j).setFrontPanelFabricName((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Fabric Name" })));
                this.expendedorderdata.getSet(i).getItem(j).setFrontPanelContentName((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Material Content" })));
                this.expendedorderdata.getSet(i).getItem(j).setFrontPanelFabricSpecName((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Material Specs" })));
                
                if (this.expendedorderdata.getSet(i).getItem(j).getVisorStyleNumber().equals(((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Original Visor" }))).trim()))
                {
                  String visorrowsitching = ((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Original # Visor Row Stitching" }))).trim();
                  if ((!visorrowsitching.equals("")) && (!visorrowsitching.equals("N/A")) && (!visorrowsitching.equals("Discontinued"))) {
                    this.expendedorderdata.getSet(i).getItem(j).setVisorRowStitching(visorrowsitching);
                  }
                  String orgfactoryvisorcode = ((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Original Factory Visor Code" }))).trim();
                  if ((!orgfactoryvisorcode.equals("")) && (!orgfactoryvisorcode.equals("N/A"))) {
                    this.expendedorderdata.getSet(i).getItem(j).setFactoryVisorCode(orgfactoryvisorcode);
                  }
                }
              }
            }
            else {
              String primvennum = (String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Primary VENDOR" }));
              
              if (((sqlrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_overseasinstock" })).where(new Condition[] { DSL.fieldByName(new String[] { "STYLE NUMBER" }).equal(this.expendedorderdata.getSet(i).getItem(j).getStyleNumber()) }).and(DSL.fieldByName(new String[] { "Backup VENDOR" }).equal(primvennum)).limit(1).fetchOne()) != null) && (sqlrecord.size() != 0))
              {
                this.expendedorderdata.getSet(i).getItem(j).setMaterial((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Fabric Name" })));
                this.expendedorderdata.getSet(i).getItem(j).setFrontPanelFabricName((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Fabric Name" })));
                this.expendedorderdata.getSet(i).getItem(j).setFrontPanelContentName((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Material Content" })));
                this.expendedorderdata.getSet(i).getItem(j).setFrontPanelFabricSpecName((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Material Specs" })));
                
                String visorrowsitching = ((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Original # Visor Row Stitching" }))).trim();
                if ((!visorrowsitching.equals("")) && (!visorrowsitching.equals("N/A")) && (!visorrowsitching.equals("Discontinued"))) {
                  this.expendedorderdata.getSet(i).getItem(j).setVisorRowStitching(visorrowsitching);
                }
                String orgfactoryvisorcode = ((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Original Factory Visor Code" }))).trim();
                if ((!orgfactoryvisorcode.equals("")) && (!orgfactoryvisorcode.equals("N/A"))) {
                  this.expendedorderdata.getSet(i).getItem(j).setFactoryVisorCode(orgfactoryvisorcode);
                }
                
              }
            }
          }
          else if (((sqlrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "overseas_price_instockshirts" })).where(new Condition[] { DSL.fieldByName(new String[] { "stylenumber" }).equal(this.expendedorderdata.getSet(i).getItem(j).getStyleNumber()) }).limit(1).fetchOne()) != null) && (sqlrecord.size() != 0)) {
            this.expendedorderdata.getSet(i).getItem(j).setStyleType(StyleInformationData.StyleType.OVERSEAS_INSTOCK_SHIRTS);
            this.expendedorderdata.getSet(i).getItem(j).setCategory(((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "category" }))).trim());
            this.expendedorderdata.getSet(i).getItem(j).setOverseasVendorNumber(Integer.valueOf((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "vendor" }))).intValue());
            
            if (((sqlrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_domestic_shirts" })).where(new Condition[] { DSL.fieldByName(new String[] { "sku" }).equal(this.expendedorderdata.getSet(i).getItem(j).getStyleNumber()) }).limit(1).fetchOne()) != null) && (sqlrecord.size() != 0)) {
              this.expendedorderdata.getSet(i).getItem(j).setSameStyle(((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "samestyle" }))).trim());
              Record sqlrecord2 = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "name" })).from(new TableLike[] { DSL.tableByName(new String[] { "list_domestic_materials" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "id" }).equal(sqlrecord.getValue(DSL.fieldByName(new String[] { "fabric" }))) }).limit(1).fetchOne();
              if ((sqlrecord2 != null) && (sqlrecord2.size() != 0)) {
                this.expendedorderdata.getSet(i).getItem(j).setMaterial((String)sqlrecord2.getValue(DSL.fieldByName(new String[] { "name" })));
              }
            } else if (((sqlrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_domestic_sweatshirts" })).where(new Condition[] { DSL.fieldByName(new String[] { "sku" }).equal(this.expendedorderdata.getSet(i).getItem(j).getStyleNumber()) }).limit(1).fetchOne()) != null) && (sqlrecord.size() != 0)) {
              this.expendedorderdata.getSet(i).getItem(j).setSameStyle(((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "samestyle" }))).trim());
              Record sqlrecord2 = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "name" })).from(new TableLike[] { DSL.tableByName(new String[] { "list_domestic_materials" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "id" }).equal(sqlrecord.getValue(DSL.fieldByName(new String[] { "fabric" }))) }).limit(1).fetchOne();
              if ((sqlrecord2 != null) && (sqlrecord2.size() != 0)) {
                this.expendedorderdata.getSet(i).getItem(j).setMaterial((String)sqlrecord2.getValue(DSL.fieldByName(new String[] { "name" })));
              }
            }
          }
          else if (((sqlrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "overseas_price_instockflats" })).where(new Condition[] { DSL.fieldByName(new String[] { "stylenumber" }).equal(this.expendedorderdata.getSet(i).getItem(j).getStyleNumber()) }).limit(1).fetchOne()) != null) && (sqlrecord.size() != 0)) {
            this.expendedorderdata.getSet(i).getItem(j).setStyleType(StyleInformationData.StyleType.OVERSEAS_INSTOCK_FLATS);
            this.expendedorderdata.getSet(i).getItem(j).setCategory(((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "category" }))).trim());
            this.expendedorderdata.getSet(i).getItem(j).setOverseasVendorNumber(Integer.valueOf((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "vendor" }))).intValue());
            this.expendedorderdata.getSet(i).getItem(j).setSameStyle(((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "samestyle" }))).trim());
            
            Record sqlrecord2 = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "name" })).from(new TableLike[] { DSL.tableByName(new String[] { "list_domestic_materials" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "id" }).equal(sqlrecord.getValue(DSL.fieldByName(new String[] { "fabric" }))) }).limit(1).fetchOne();
            if ((sqlrecord2 != null) && (sqlrecord2.size() != 0)) {
              this.expendedorderdata.getSet(i).getItem(j).setMaterial((String)sqlrecord2.getValue(DSL.fieldByName(new String[] { "name" })));
            }
          }
          else if (((sqlrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_lackpard" })).where(new Condition[] { DSL.fieldByName(new String[] { "Style" }).equal(this.expendedorderdata.getSet(i).getItem(j).getStyleNumber()) }).limit(1).fetchOne()) != null) && (sqlrecord.size() != 0)) {
            this.expendedorderdata.getSet(i).getItem(j).setStyleType(StyleInformationData.StyleType.OVERSEAS_LACKPARD);
            this.expendedorderdata.getSet(i).getItem(j).setCategory(((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Main Category" }))).trim());
            this.expendedorderdata.getSet(i).getItem(j).setGoodsType("3D");
            this.expendedorderdata.getSet(i).getItem(j).setSameStyle(((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Style" }))).trim());
            this.expendedorderdata.getSet(i).getItem(j).setOverseasVendorNumber(4);
          }
          else if (((sqlrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_overseas_customstyle" })).where(new Condition[] { DSL.fieldByName(new String[] { "Style" }).equal(this.expendedorderdata.getSet(i).getItem(j).getStyleNumber()) }).limit(1).fetchOne()) != null) && (sqlrecord.size() != 0)) {
            this.expendedorderdata.getSet(i).getItem(j).setStyleType(StyleInformationData.StyleType.OVERSEAS_CUSTOMSTYLE);
            this.expendedorderdata.getSet(i).getItem(j).setOverseasVendorNumber(Integer.valueOf((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Vendor" }))).intValue());
            
            if (((sqlrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_overseas_customstyle" })).where(new Condition[] { DSL.fieldByName(new String[] { "Style" }).equal(this.expendedorderdata.getSet(i).getItem(j).getStyleNumber()) }).and(DSL.fieldByName(new String[] { "Profile" }).equal(this.expendedorderdata.getSet(i).getItem(j).getProfile())).limit(1).fetchOne()) != null) && (sqlrecord.size() != 0)) {
              this.expendedorderdata.getSet(i).getItem(j).setCategory(this.expendedorderdata.getSet(i).getItem(j).getProfile());
              if (((sqlrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_overseas_customstyle" })).where(new Condition[] { DSL.fieldByName(new String[] { "Style" }).equal(this.expendedorderdata.getSet(i).getItem(j).getStyleNumber()) }).and(DSL.fieldByName(new String[] { "Profile" }).equal(this.expendedorderdata.getSet(i).getItem(j).getProfile())).and(DSL.fieldByName(new String[] { "WorkFlow Fabic Name" }).equal(this.expendedorderdata.getSet(i).getItem(j).getFrontPanelFabric())).limit(1).fetchOne()) != null) && (sqlrecord.size() != 0)) {
                this.expendedorderdata.getSet(i).getItem(j).setFrontPanelFabricName((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Panel Fabric" })));
                this.expendedorderdata.getSet(i).getItem(j).setFrontPanelContentName((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Fabric Content" })));
                this.expendedorderdata.getSet(i).getItem(j).setFrontPanelFabricSpecName((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Fabric Spec." })));
              } else {
                this.expendedorderdata.getSet(i).getItem(j).setFrontPanelFabricName(this.expendedorderdata.getSet(i).getItem(j).getFrontPanelFabric());
              }
              if (((sqlrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_overseas_customstyle" })).where(new Condition[] { DSL.fieldByName(new String[] { "Style" }).equal(this.expendedorderdata.getSet(i).getItem(j).getStyleNumber()) }).and(DSL.fieldByName(new String[] { "Profile" }).equal(this.expendedorderdata.getSet(i).getItem(j).getProfile())).and(DSL.fieldByName(new String[] { "WorkFlow Fabic Name" }).equal(this.expendedorderdata.getSet(i).getItem(j).getBackPanelFabric())).limit(1).fetchOne()) != null) && (sqlrecord.size() != 0)) {
                this.expendedorderdata.getSet(i).getItem(j).setBackPanelFabricName((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Panel Fabric" })));
                this.expendedorderdata.getSet(i).getItem(j).setBackPanelContentName((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Fabric Content" })));
                this.expendedorderdata.getSet(i).getItem(j).setBackPanelFabricSpecName((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Fabric Spec." })));
              } else {
                this.expendedorderdata.getSet(i).getItem(j).setBackPanelFabricName(this.expendedorderdata.getSet(i).getItem(j).getBackPanelFabric());
              }
              if (((sqlrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_overseas_customstyle" })).where(new Condition[] { DSL.fieldByName(new String[] { "Style" }).equal(this.expendedorderdata.getSet(i).getItem(j).getStyleNumber()) }).and(DSL.fieldByName(new String[] { "Profile" }).equal(this.expendedorderdata.getSet(i).getItem(j).getProfile())).and(DSL.fieldByName(new String[] { "WorkFlow Fabic Name" }).equal(this.expendedorderdata.getSet(i).getItem(j).getSidePanelFabric())).limit(1).fetchOne()) != null) && (sqlrecord.size() != 0)) {
                this.expendedorderdata.getSet(i).getItem(j).setSidePanelFabricName((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Panel Fabric" })));
                this.expendedorderdata.getSet(i).getItem(j).setSidePanelContentName((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Fabric Content" })));
                this.expendedorderdata.getSet(i).getItem(j).setSidePanelFabricSpecName((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Fabric Spec." })));
              } else {
                this.expendedorderdata.getSet(i).getItem(j).setSidePanelFabricName(this.expendedorderdata.getSet(i).getItem(j).getSidePanelFabric());
              }
            }
          }
          


          boolean haveemb = false;
          boolean havescreen = false;
          for (int k = 0; k < this.expendedorderdata.getSet(i).getLogoCount(); k++) {
            for (int l = 0; l < this.expendedorderdata.getSet(i).getLogo(k).getDecorationCount().intValue(); l++) {
              if ((this.expendedorderdata.getSet(i).getLogo(k).getDecoration(Integer.valueOf(l)).getName().equals("Screen Print")) || (this.expendedorderdata.getSet(i).getLogo(k).getDecoration(Integer.valueOf(l)).getName().equals("Screen Print Full Panel")) || (this.expendedorderdata.getSet(i).getLogo(k).getDecoration(Integer.valueOf(l)).getName().equals("Screen Print Golf Style Front Full Panel")) || (this.expendedorderdata.getSet(i).getLogo(k).getDecoration(Integer.valueOf(l)).getName().equals("Sublimation")) || (this.expendedorderdata.getSet(i).getLogo(k).getDecoration(Integer.valueOf(l)).getName().equals("Sublimation Full Panel")) || (this.expendedorderdata.getSet(i).getLogo(k).getDecoration(Integer.valueOf(l)).getName().equals("Sublimation Golf Front Full Panel"))) {
                havescreen = true;
              } else {
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
        if (((sqlrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "name" })).from(new TableLike[] { DSL.tableByName(new String[] { "list_decorations_locations" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "id" }).equal(this.expendedorderdata.getSet(i).getLogo(j).getLogoLocation()) }).limit(1).fetchOne()) != null) && (sqlrecord.size() != 0)) {
          this.expendedorderdata.getSet(i).getLogo(j).setLogoLocationName(this.expendedorderdata.getSet(i).getLogo(j).getLogoLocation() + " - " + sqlrecord.getValue(DSL.fieldByName(new String[] { "name" })));
        } else {
          this.expendedorderdata.getSet(i).getLogo(j).setLogoLocationName(this.expendedorderdata.getSet(i).getLogo(j).getLogoLocation());
        }
        
        for (int k = 0; k < this.expendedorderdata.getSet(i).getLogo(j).getColorwayCount(); k++) {
          this.expendedorderdata.getSet(i).getLogo(j).getColorway(k).setConvertCode(JOOQ_ConvertColor.convertTo(this.expendedorderdata.getSet(i).getLogo(j).getColorway(k).getLogoColorCode(), this.expendedorderdata.getSet(i).getLogo(j).getColorway(k).getThreadType(), this.expendedorderdata.getSet(i).getLogo(j).getThreadBrand()));
          this.expendedorderdata.getSet(i).getLogo(j).getColorway(k).setConvertColorName(threadtoconvert(this.expendedorderdata.getSet(i).getLogo(j).getColorway(k).getConvertCode(), this.expendedorderdata.getSet(i).getLogo(j).getThreadBrand()));
          this.expendedorderdata.getSet(i).getLogo(j).getColorway(k).setLogoColorCodeName(threadtoconvert(this.expendedorderdata.getSet(i).getLogo(j).getColorway(k).getLogoColorCode(), this.expendedorderdata.getSet(i).getLogo(j).getColorway(k).getThreadType()));
          this.expendedorderdata.getSet(i).getLogo(j).getColorway(k).setLogoColorCodeValue(threadtocolorvalue(this.expendedorderdata.getSet(i).getLogo(j).getColorway(k).getLogoColorCode(), this.expendedorderdata.getSet(i).getLogo(j).getColorway(k).getThreadType()));
          this.expendedorderdata.getSet(i).getLogo(j).getColorway(k).setConvertCodeColorValue(threadtocolorvalue(this.expendedorderdata.getSet(i).getLogo(j).getColorway(k).getConvertCode(), this.expendedorderdata.getSet(i).getLogo(j).getThreadBrand()));
        }
        
        for (int k = 0; k < this.expendedorderdata.getSet(i).getLogo(j).getDecorationCount().intValue(); k++) {
          if (((sqlrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "list_decorations_overall" })).where(new Condition[] { DSL.fieldByName(new String[] { "name" }).equal(this.expendedorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getName()) }).limit(1).fetchOne()) != null) && (sqlrecord.size() != 0)) {
            this.expendedorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).setField1Name((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "field1" })));
            this.expendedorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).setField2Name((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "field2" })));
            this.expendedorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).setField3Name((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "field3" })));
            this.expendedorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).setField4Name((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "field4" })));
          } else if (((sqlrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "list_decorations_overall" })).where(new Condition[] { DSL.fieldByName(new String[] { "name" }).equal("Other") }).limit(1).fetchOne()) != null) && (sqlrecord.size() != 0)) {
            this.expendedorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).setField1Name((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "field1" })));
            this.expendedorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).setField2Name((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "field2" })));
            this.expendedorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).setField3Name((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "field3" })));
            this.expendedorderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).setField4Name((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "field4" })));
          }
        }
      }
    }
    



    if (this.expendedorderdata.getOrderType() == com.ottocap.NewWorkFlow.client.OrderData.OrderData.OrderType.DOMESTIC) {
      if (this.expendedorderdata.getVendorInformation().getDigitizerVendor().getVendor() != null) {
        sqlrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "name" })).from(new TableLike[] { DSL.tableByName(new String[] { "list_vendors_domestic" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "id" }).equal(this.expendedorderdata.getVendorInformation().getDigitizerVendor().getVendor()) }).limit(1).fetchOne();
        this.expendedorderdata.getVendorInformation().getDigitizerVendor().setVendorName((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "name" })));
      }
      if (this.expendedorderdata.getVendorInformation().getHeatTransferVendor().getVendor() != null) {
        sqlrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "name" })).from(new TableLike[] { DSL.tableByName(new String[] { "list_vendors_domestic" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "id" }).equal(this.expendedorderdata.getVendorInformation().getHeatTransferVendor().getVendor()) }).limit(1).fetchOne();
        this.expendedorderdata.getVendorInformation().getHeatTransferVendor().setVendorName((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "name" })));
      }
      if (this.expendedorderdata.getVendorInformation().getPatchVendor().getVendor() != null) {
        sqlrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "name" })).from(new TableLike[] { DSL.tableByName(new String[] { "list_vendors_domestic" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "id" }).equal(this.expendedorderdata.getVendorInformation().getPatchVendor().getVendor()) }).limit(1).fetchOne();
        this.expendedorderdata.getVendorInformation().getPatchVendor().setVendorName((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "name" })));
      }
      if (this.expendedorderdata.getVendorInformation().getScreenPrintVendor().getVendor() != null) {
        sqlrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "name" })).from(new TableLike[] { DSL.tableByName(new String[] { "list_vendors_domestic" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "id" }).equal(this.expendedorderdata.getVendorInformation().getScreenPrintVendor().getVendor()) }).limit(1).fetchOne();
        this.expendedorderdata.getVendorInformation().getScreenPrintVendor().setVendorName((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "name" })));
      }
      if (this.expendedorderdata.getVendorInformation().getDirectToGarmentVendor().getVendor() != null) {
        sqlrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "name" })).from(new TableLike[] { DSL.tableByName(new String[] { "list_vendors_domestic" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "id" }).equal(this.expendedorderdata.getVendorInformation().getDirectToGarmentVendor().getVendor()) }).limit(1).fetchOne();
        this.expendedorderdata.getVendorInformation().getDirectToGarmentVendor().setVendorName((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "name" })));
      }
      if (this.expendedorderdata.getVendorInformation().getEmbroideryVendor().getVendor() != null) {
        sqlrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "name" })).from(new TableLike[] { DSL.tableByName(new String[] { "list_vendors_domestic" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "id" }).equal(this.expendedorderdata.getVendorInformation().getEmbroideryVendor().getVendor()) }).limit(1).fetchOne();
        this.expendedorderdata.getVendorInformation().getEmbroideryVendor().setVendorName((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "name" })));
      }
    } else {
      String[] vendorkeys = (String[])this.expendedorderdata.getVendorInformation().getOverseasVendor().keySet().toArray(new String[0]);
      for (int i = 0; i < vendorkeys.length; i++) {
        if (((OrderDataVendorInformation)this.expendedorderdata.getVendorInformation().getOverseasVendor().get(vendorkeys[i])).getVendor() != null)
        {

          if (((sqlrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "name" })).from(new TableLike[] { DSL.tableByName(new String[] { "list_vendors_overseas" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "id" }).equal(((OrderDataVendorInformation)this.expendedorderdata.getVendorInformation().getOverseasVendor().get(vendorkeys[i])).getVendor()) }).limit(1).fetchOne()) != null) && (sqlrecord.size() != 0)) {
            ((ExtendOrderDataVendorInformation)this.expendedorderdata.getVendorInformation().getOverseasVendor().get(vendorkeys[i])).setVendorName((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "name" })));
          }
        }
      }
    }
    
    if (this.expendedorderdata.getCustomerInformation().getEclipseAccountNumber() != null) {
      if (((sqlrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "showaddress" })).from(new TableLike[] { DSL.tableByName(new String[] { "eclipse_virtualsample_settings" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "eclipseaccount" }).equal(this.expendedorderdata.getCustomerInformation().getEclipseAccountNumber()) }).limit(1).fetchOne()) != null) && (sqlrecord.size() != 0)) {
        this.expendedorderdata.getCustomerInformation().setVirtualSampleShowAddress(((Byte)sqlrecord.getValue(DSL.fieldByName(new String[] { "showaddress" }))).byteValue() != 0);
      }
    }
  }
  

  private int styletovendor(String stylenumber, String vendornumber)
  {
    Record sqlrecord;
    
    if (((sqlrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "Vendor" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_overseas" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "Style" }).equal(stylenumber) }).limit(1).fetchOne()) != null) && (sqlrecord.size() != 0))
      return Integer.valueOf((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Vendor" }))).intValue();
    if (((sqlrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "Vendor" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_pre-designed" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "Style" }).equal(stylenumber) }).limit(1).fetchOne()) != null) && (sqlrecord.size() != 0))
      return Integer.valueOf((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Vendor" }))).intValue();
    if (((sqlrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "Primary VENDOR" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_overseasinstock" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "STYLE NUMBER" }).equal(stylenumber) }).limit(1).fetchOne()) != null) && (sqlrecord.size() != 0)) {
      if ((vendornumber != null) && (!vendornumber.equals("")) && (!vendornumber.equals("Default"))) {
        return Integer.valueOf(vendornumber).intValue();
      }
      return Integer.valueOf((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Primary VENDOR" }))).intValue();
    }
    if (((sqlrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "vendor" })).from(new TableLike[] { DSL.tableByName(new String[] { "overseas_price_instockshirts" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "stylenumber" }).equal(stylenumber) }).limit(1).fetchOne()) != null) && (sqlrecord.size() != 0))
      return Integer.valueOf((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "vendor" }))).intValue();
    if (((sqlrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "vendor" })).from(new TableLike[] { DSL.tableByName(new String[] { "overseas_price_instockflats" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "stylenumber" }).equal(stylenumber) }).limit(1).fetchOne()) != null) && (sqlrecord.size() != 0)) {
      return Integer.valueOf((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "vendor" }))).intValue();
    }
    
    return 0;
  }
  
  private String threadtocolorvalue(String code, String threadbrand)
  {
    if ((threadbrand == null) || (threadbrand.equals(""))) {
      return "#000000";
    }
    if (code.startsWith("Match ")) {
      String ottocode = code.substring("Match OTTO ".length());
      Record sqlrecord; if (((sqlrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "colorvalue" })).from(new TableLike[] { DSL.tableByName(new String[] { "thread_ottocolors" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "code" }).equal(ottocode) }).limit(1).fetchOne()) != null) && (sqlrecord.size() != 0)) {
        return (String)sqlrecord.getValue(DSL.fieldByName(new String[] { "colorvalue" }));
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
    
    if (threadbrand.equals("Madeira Polyneon")) { Record sqlrecord;
      if (((sqlrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "colorvalue" })).from(new TableLike[] { DSL.tableByName(new String[] { "thread_madeirap" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "code" }).equal(tempcode) }).limit(1).fetchOne()) != null) && (sqlrecord.size() != 0)) {
        return (String)sqlrecord.getValue(DSL.fieldByName(new String[] { "colorvalue" }));
      }
    } else if (threadbrand.equals("Madeira Rayon")) { Record sqlrecord;
      if (((sqlrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "colorvalue" })).from(new TableLike[] { DSL.tableByName(new String[] { "thread_madeirar" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "code" }).equal(tempcode) }).limit(1).fetchOne()) != null) && (sqlrecord.size() != 0)) {
        return (String)sqlrecord.getValue(DSL.fieldByName(new String[] { "colorvalue" }));
      }
    } else if (threadbrand.equals("Pantone")) { Record sqlrecord;
      if (((sqlrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "colorvalue" })).from(new TableLike[] { DSL.tableByName(new String[] { "thread_pantone" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "code" }).equal(tempcode) }).limit(1).fetchOne()) != null) && (sqlrecord.size() != 0)) {
        return (String)sqlrecord.getValue(DSL.fieldByName(new String[] { "colorvalue" }));
      }
    } else if (threadbrand.equals("Robinson Rayon")) { Record sqlrecord;
      if (((sqlrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "colorvalue" })).from(new TableLike[] { DSL.tableByName(new String[] { "thread_robinsonr" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "code" }).equal(tempcode) }).limit(1).fetchOne()) != null) && (sqlrecord.size() != 0)) {
        return (String)sqlrecord.getValue(DSL.fieldByName(new String[] { "colorvalue" }));
      }
    } else if (threadbrand.equals("RA Super Brite Polyester")) { Record sqlrecord;
      if (((sqlrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "colorvalue" })).from(new TableLike[] { DSL.tableByName(new String[] { "thread_rasuperbritepolyester" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "code" }).equal(tempcode) }).limit(1).fetchOne()) != null) && (sqlrecord.size() != 0)) {
        return (String)sqlrecord.getValue(DSL.fieldByName(new String[] { "colorvalue" }));
      }
    } else if (threadbrand.equals("Fibres Rayon")) { Record sqlrecord;
      if (((sqlrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "colorvalue" })).from(new TableLike[] { DSL.tableByName(new String[] { "thread_fibresr" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "code" }).equal(tempcode) }).limit(1).fetchOne()) != null) && (sqlrecord.size() != 0)) {
        return (String)sqlrecord.getValue(DSL.fieldByName(new String[] { "colorvalue" }));
      }
    } else if ((threadbrand.equals("Match Cap Color")) || (threadbrand.equals("Match Product Color"))) { Record sqlrecord;
      if (((sqlrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "colorvalue" })).from(new TableLike[] { DSL.tableByName(new String[] { "thread_ottocolors" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "code" }).equal(tempcode) }).limit(1).fetchOne()) != null) && (sqlrecord.size() != 0)) {
        return (String)sqlrecord.getValue(DSL.fieldByName(new String[] { "colorvalue" }));
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
    
    if (threadbrand.equals("Madeira Polyneon")) { Record sqlrecord;
      if (((sqlrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "colorname" })).from(new TableLike[] { DSL.tableByName(new String[] { "thread_madeirap" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "code" }).equal(tempcode) }).limit(1).fetchOne()) != null) && (sqlrecord.size() != 0)) {
        return (String)sqlrecord.getValue(DSL.fieldByName(new String[] { "colorname" }));
      }
    } else if (threadbrand.equals("Madeira Rayon")) { Record sqlrecord;
      if (((sqlrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "colorname" })).from(new TableLike[] { DSL.tableByName(new String[] { "thread_madeirar" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "code" }).equal(tempcode) }).limit(1).fetchOne()) != null) && (sqlrecord.size() != 0)) {
        return (String)sqlrecord.getValue(DSL.fieldByName(new String[] { "colorname" }));
      }
    } else if (threadbrand.equals("Pantone")) { Record sqlrecord;
      if (((sqlrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "colorname" })).from(new TableLike[] { DSL.tableByName(new String[] { "thread_pantone" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "code" }).equal(tempcode) }).limit(1).fetchOne()) != null) && (sqlrecord.size() != 0)) {
        return (String)sqlrecord.getValue(DSL.fieldByName(new String[] { "colorname" }));
      }
    } else if (threadbrand.equals("Robinson Rayon")) { Record sqlrecord;
      if (((sqlrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "colorname" })).from(new TableLike[] { DSL.tableByName(new String[] { "thread_robinsonr" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "code" }).equal(tempcode) }).limit(1).fetchOne()) != null) && (sqlrecord.size() != 0)) {
        return (String)sqlrecord.getValue(DSL.fieldByName(new String[] { "colorname" }));
      }
    } else if (threadbrand.equals("RA Super Brite Polyester")) { Record sqlrecord;
      if (((sqlrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "colorname" })).from(new TableLike[] { DSL.tableByName(new String[] { "thread_rasuperbritepolyester" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "code" }).equal(tempcode) }).limit(1).fetchOne()) != null) && (sqlrecord.size() != 0)) {
        return (String)sqlrecord.getValue(DSL.fieldByName(new String[] { "colorname" }));
      }
    } else if (threadbrand.equals("Fibres Rayon")) { Record sqlrecord;
      if (((sqlrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "colorname" })).from(new TableLike[] { DSL.tableByName(new String[] { "thread_fibresr" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "code" }).equal(tempcode) }).limit(1).fetchOne()) != null) && (sqlrecord.size() != 0)) {
        return (String)sqlrecord.getValue(DSL.fieldByName(new String[] { "colorname" }));
      }
    }
    return "";
  }
  
  public String convertCodeToName(String code)
  {
    if (this.ottocodetocolorcache == null) {
      this.ottocodetocolorcache = new JOOQ_OTTOCodeToColorCached();
    }
    return this.ottocodetocolorcache.getName(code);
  }
  
  public ExtendOrderData getExpandedOrderData() {
    return this.expendedorderdata;
  }
}
