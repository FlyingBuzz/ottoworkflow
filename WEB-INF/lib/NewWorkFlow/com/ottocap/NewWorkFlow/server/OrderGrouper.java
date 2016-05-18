package com.ottocap.NewWorkFlow.server;

import com.ottocap.NewWorkFlow.client.OrderData.OrderDataItem;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogo;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataSet;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataVendorInformation;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataVendors;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderData;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataAddress;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataCustomerInformation;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataVendorInformation;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataVendors;

public class OrderGrouper
{
  private ExtendOrderData groupedorderdata = new ExtendOrderData();
  
  public OrderGrouper(ExtendOrderData theorderdata) {
    this(theorderdata, true);
  }
  
  public OrderGrouper(ExtendOrderData theorderdata, boolean skipisample)
  {
    this.groupedorderdata.setCustomerPO(theorderdata.getCustomerPO());
    this.groupedorderdata.setEmployeeId(theorderdata.getEmployeeId());
    this.groupedorderdata.setEstimatedShipDate(theorderdata.getEstimatedShipDate());
    this.groupedorderdata.setHiddenKey(theorderdata.getHiddenKey());
    this.groupedorderdata.setInHandDate(theorderdata.getInHandDate());
    this.groupedorderdata.setInternalComments(theorderdata.getInternalComments());
    this.groupedorderdata.setInternalDueDateTime(theorderdata.getInternalDueDateTime());
    this.groupedorderdata.setNextAction(theorderdata.getNextAction());
    this.groupedorderdata.setOrderDate(theorderdata.getOrderDate());
    this.groupedorderdata.setOrderNumber(theorderdata.getOrderNumber());
    this.groupedorderdata.setOrderStatus(theorderdata.getOrderStatus());
    
    this.groupedorderdata.getCustomerInformation().setBlindShippingRequired(theorderdata.getCustomerInformation().getBlindShippingRequired());
    this.groupedorderdata.getCustomerInformation().setTaxExampt(theorderdata.getCustomerInformation().getTaxExampt());
    this.groupedorderdata.getCustomerInformation().setCompany(theorderdata.getCustomerInformation().getCompany());
    this.groupedorderdata.getCustomerInformation().setCompanyLogo(theorderdata.getCustomerInformation().getCompanyLogo());
    this.groupedorderdata.getCustomerInformation().setContactName(theorderdata.getCustomerInformation().getContactName());
    this.groupedorderdata.getCustomerInformation().setEclipseAccountNumber(theorderdata.getCustomerInformation().getEclipseAccountNumber());
    this.groupedorderdata.getCustomerInformation().setEmail(theorderdata.getCustomerInformation().getEmail());
    this.groupedorderdata.getCustomerInformation().setFax(theorderdata.getCustomerInformation().getFax());
    this.groupedorderdata.getCustomerInformation().setPhone(theorderdata.getCustomerInformation().getPhone());
    this.groupedorderdata.getCustomerInformation().setSameAsBillingAddress(theorderdata.getCustomerInformation().getSameAsBillingAddress());
    this.groupedorderdata.getCustomerInformation().setShipAttention(theorderdata.getCustomerInformation().getShipAttention());
    this.groupedorderdata.getCustomerInformation().setTerms(theorderdata.getCustomerInformation().getTerms());
    this.groupedorderdata.getCustomerInformation().setHaveDropShipment(theorderdata.getCustomerInformation().getHaveDropShipment());
    this.groupedorderdata.getCustomerInformation().setDropShipmentAmount(theorderdata.getCustomerInformation().getDropShipmentAmount());
    this.groupedorderdata.getCustomerInformation().getBillInformation().setCity(theorderdata.getCustomerInformation().getBillInformation().getCity());
    this.groupedorderdata.getCustomerInformation().getBillInformation().setCompany(theorderdata.getCustomerInformation().getBillInformation().getCompany());
    this.groupedorderdata.getCustomerInformation().getBillInformation().setCountry(theorderdata.getCustomerInformation().getBillInformation().getCountry());
    this.groupedorderdata.getCustomerInformation().getBillInformation().setResidential(theorderdata.getCustomerInformation().getBillInformation().getResidential());
    this.groupedorderdata.getCustomerInformation().getBillInformation().setState(theorderdata.getCustomerInformation().getBillInformation().getState());
    this.groupedorderdata.getCustomerInformation().getBillInformation().setStreetLine1(theorderdata.getCustomerInformation().getBillInformation().getStreetLine1());
    this.groupedorderdata.getCustomerInformation().getBillInformation().setStreetLine2(theorderdata.getCustomerInformation().getBillInformation().getStreetLine2());
    this.groupedorderdata.getCustomerInformation().getBillInformation().setZip(theorderdata.getCustomerInformation().getBillInformation().getZip());
    this.groupedorderdata.getCustomerInformation().getShipInformation().setCity(theorderdata.getCustomerInformation().getShipInformation().getCity());
    this.groupedorderdata.getCustomerInformation().getShipInformation().setCompany(theorderdata.getCustomerInformation().getShipInformation().getCompany());
    this.groupedorderdata.getCustomerInformation().getShipInformation().setCountry(theorderdata.getCustomerInformation().getShipInformation().getCountry());
    this.groupedorderdata.getCustomerInformation().getShipInformation().setResidential(theorderdata.getCustomerInformation().getShipInformation().getResidential());
    this.groupedorderdata.getCustomerInformation().getShipInformation().setState(theorderdata.getCustomerInformation().getShipInformation().getState());
    this.groupedorderdata.getCustomerInformation().getShipInformation().setStreetLine1(theorderdata.getCustomerInformation().getShipInformation().getStreetLine1());
    this.groupedorderdata.getCustomerInformation().getShipInformation().setStreetLine2(theorderdata.getCustomerInformation().getShipInformation().getStreetLine2());
    this.groupedorderdata.getCustomerInformation().getShipInformation().setZip(theorderdata.getCustomerInformation().getShipInformation().getZip());
    
    if (theorderdata.getOrderType() == com.ottocap.NewWorkFlow.client.OrderData.OrderData.OrderType.DOMESTIC) {
      this.groupedorderdata.setOrderType(com.ottocap.NewWorkFlow.client.OrderData.OrderData.OrderType.DOMESTIC);
    } else if (theorderdata.getOrderType() == com.ottocap.NewWorkFlow.client.OrderData.OrderData.OrderType.OVERSEAS) {
      this.groupedorderdata.setOrderType(com.ottocap.NewWorkFlow.client.OrderData.OrderData.OrderType.OVERSEAS);
    }
    this.groupedorderdata.setPotentialInternalComments(theorderdata.getPotentialInternalComments());
    this.groupedorderdata.setPotentialRepeatDate(theorderdata.getPotentialRepeatDate());
    this.groupedorderdata.setPotentialRepeatFrequency(theorderdata.getPotentialRepeatFrequency());
    this.groupedorderdata.setRushOrder(theorderdata.getRushOrder());
    this.groupedorderdata.setShippingCost(theorderdata.getShippingCost());
    this.groupedorderdata.setShippingType(theorderdata.getShippingType());
    this.groupedorderdata.setSpecialNotes(theorderdata.getSpecialNotes());
    this.groupedorderdata.setEmployeeFullName(theorderdata.getEmployeeFullName());
    this.groupedorderdata.setEmployeeEmail(theorderdata.getEmployeeEmail());
    this.groupedorderdata.setOrderTypeFolder(theorderdata.getOrderTypeFolder());
    this.groupedorderdata.setExchangeRate(theorderdata.getExchangeRate());
    
    OrderDataVendors theorderdatavendors = theorderdata.getVendorInformation();
    ExtendOrderDataVendors myvendors = this.groupedorderdata.getVendorInformation();
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
    
    myvendors.getScreenPrintVendor().setDigitizingDueDate(theorderdatavendors.getCADPrintVendor().getDigitizingDueDate());
    myvendors.getScreenPrintVendor().setDigitizingProcessingDate(theorderdatavendors.getCADPrintVendor().getDigitizingProcessingDate());
    myvendors.getScreenPrintVendor().setShippingMethod(theorderdatavendors.getCADPrintVendor().getShippingMethod());
    myvendors.getScreenPrintVendor().setVendor(theorderdatavendors.getCADPrintVendor().getVendor());
    myvendors.getScreenPrintVendor().setWorkOrderDueDate(theorderdatavendors.getCADPrintVendor().getWorkOrderDueDate());
    myvendors.getScreenPrintVendor().setWorkOrderNumber(theorderdatavendors.getCADPrintVendor().getWorkOrderNumber());
    myvendors.getScreenPrintVendor().setWorkOrderProcessingDate(theorderdatavendors.getCADPrintVendor().getWorkOrderProcessingDate());
    
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
    com.ottocap.NewWorkFlow.client.OrderData.OrderDataDiscount theorderdatadiscount;
    for (int i = 0; i < theorderdata.getDiscountItemCount(); i++) {
      theorderdatadiscount = theorderdata.getDiscountItem(i);
      com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataDiscount mydiscount = new com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataDiscount();
      mydiscount.setAmount(theorderdatadiscount.getAmount());
      mydiscount.setReason(theorderdatadiscount.getReason());
      this.groupedorderdata.addDiscountItem(mydiscount);
    }
    

    for (OrderDataSet theset : theorderdata.getSets())
    {
      OrderDataSet matchingitemset = null;
      boolean alllogomatch = false;
      OrderDataLogo thelogos;
      boolean allcolorwaymatch;
      for (OrderDataSet groupedorderdatasets : this.groupedorderdata.getSets())
      {
        if (((skipisample) || (groupedorderdatasets.getItem(0).getPreviewFilename().equals(theset.getItem(0).getPreviewFilename()))) && (groupedorderdatasets.getItem(0).getStyleNumber().equals(theset.getItem(0).getStyleNumber())) && (groupedorderdatasets.getItem(0).getCustomStyleName().equals(theset.getItem(0).getCustomStyleName())) && (groupedorderdatasets.getItem(0).getColorCode().equals(theset.getItem(0).getColorCode())) && (groupedorderdatasets.getItem(0).getFrontPanelColor().equals(theset.getItem(0).getFrontPanelColor())) && (groupedorderdatasets.getItem(0).getSideBackPanelColor().equals(theset.getItem(0).getSideBackPanelColor())) && (groupedorderdatasets.getItem(0).getBackPanelColor().equals(theset.getItem(0).getBackPanelColor())) && (groupedorderdatasets.getItem(0).getSidePanelColor().equals(theset.getItem(0).getSidePanelColor())) && (groupedorderdatasets.getItem(0).getButtonColor().equals(theset.getItem(0).getButtonColor())) && (groupedorderdatasets.getItem(0).getInnerTapingColor().equals(theset.getItem(0).getInnerTapingColor())) && (groupedorderdatasets.getItem(0).getVisorStyleNumber().equals(theset.getItem(0).getVisorStyleNumber())) && (groupedorderdatasets.getItem(0).getPrimaryVisorColor().equals(theset.getItem(0).getPrimaryVisorColor())) && (groupedorderdatasets.getItem(0).getVisorTrimColor().equals(theset.getItem(0).getVisorTrimColor())) && (groupedorderdatasets.getItem(0).getVisorSandwichColor().equals(theset.getItem(0).getVisorSandwichColor())) && (groupedorderdatasets.getItem(0).getUndervisorColor().equals(theset.getItem(0).getUndervisorColor())) && (groupedorderdatasets.getItem(0).getDistressedVisorInsideColor().equals(theset.getItem(0).getDistressedVisorInsideColor())) && (groupedorderdatasets.getItem(0).getClosureStyleNumber().equals(theset.getItem(0).getClosureStyleNumber())) && (groupedorderdatasets.getItem(0).getClosureStrapColor().equals(theset.getItem(0).getClosureStrapColor())) && (groupedorderdatasets.getItem(0).getEyeletStyleNumber().equals(theset.getItem(0).getEyeletStyleNumber())) && (groupedorderdatasets.getItem(0).getFrontEyeletColor().equals(theset.getItem(0).getFrontEyeletColor())) && (groupedorderdatasets.getItem(0).getSideBackEyeletColor().equals(theset.getItem(0).getSideBackEyeletColor())) && (groupedorderdatasets.getItem(0).getBackEyeletColor().equals(theset.getItem(0).getBackEyeletColor())) && (groupedorderdatasets.getItem(0).getSideEyeletColor().equals(theset.getItem(0).getSideEyeletColor())) && (groupedorderdatasets.getItem(0).getSweatbandStyleNumber().equals(theset.getItem(0).getSweatbandStyleNumber())) && (groupedorderdatasets.getItem(0).getSweatbandColor().equals(theset.getItem(0).getSweatbandColor())) && (groupedorderdatasets.getItem(0).getSweatbandStripeColor().equals(theset.getItem(0).getSweatbandStripeColor())) && (groupedorderdatasets.getItem(0).getProfile().equals(theset.getItem(0).getProfile())) && (groupedorderdatasets.getItem(0).getNumberOfPanels().equals(theset.getItem(0).getNumberOfPanels())) && (groupedorderdatasets.getItem(0).getCrownConstruction().equals(theset.getItem(0).getCrownConstruction())) && (groupedorderdatasets.getItem(0).getVisorRowStitching().equals(theset.getItem(0).getVisorRowStitching())) && (groupedorderdatasets.getItem(0).getFrontPanelFabric().equals(theset.getItem(0).getFrontPanelFabric())) && (groupedorderdatasets.getItem(0).getSideBackPanelFabric().equals(theset.getItem(0).getSideBackPanelFabric())) && (groupedorderdatasets.getItem(0).getBackPanelFabric().equals(theset.getItem(0).getBackPanelFabric())) && (groupedorderdatasets.getItem(0).getSidePanelFabric().equals(theset.getItem(0).getSidePanelFabric()))) {
          matchingitemset = groupedorderdatasets;
          
          alllogomatch = true;
          if (groupedorderdatasets.getLogoCount() != theset.getLogoCount()) {
            alllogomatch = false;
          }
          else {
            for (OrderDataLogo groupedorderdatalogo : groupedorderdatasets.getLogos()) {
              boolean foundamatch = false;
              for (java.util.Iterator localIterator3 = theset.getLogos().iterator(); localIterator3.hasNext();) { thelogos = (OrderDataLogo)localIterator3.next();
                if ((groupedorderdatalogo.getLogoLocation().equals(thelogos.getLogoLocation())) && (groupedorderdatalogo.getFilename().equals(thelogos.getFilename())) && (groupedorderdatalogo.getDstFilename().equals(thelogos.getDstFilename())) && (groupedorderdatalogo.getColorwayCount() == thelogos.getColorwayCount())) {
                  allcolorwaymatch = true;
                  for (int i = 0; i < groupedorderdatalogo.getColorwayCount(); i++) {
                    if ((!groupedorderdatalogo.getColorway(i).getThreadType().equals(thelogos.getColorway(i).getThreadType())) || (!groupedorderdatalogo.getColorway(i).getLogoColorCode().equals(thelogos.getColorway(i).getLogoColorCode())) || (!groupedorderdatalogo.getColorway(i).getInkType().equals(thelogos.getColorway(i).getInkType()))) {
                      allcolorwaymatch = false;
                    }
                  }
                  if (allcolorwaymatch) {
                    foundamatch = true;
                  }
                }
              }
              if (!foundamatch) {
                alllogomatch = false;
              }
            }
          }
        }
        



        if ((matchingitemset != null) && (alllogomatch)) {
          break;
        }
      }
      


      if ((matchingitemset != null) && (alllogomatch))
      {


        com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataItem currentitem = ((com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataSet)theset).getItem(0).copy();
        com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataItem orgitem = ((com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataSet)matchingitemset).getItem(0);
        
        if (currentitem.getPolybag()) {
          orgitem.setPolybag(true);
        }
        if (currentitem.getOakLeaves()) {
          orgitem.setOakLeaves(true);
        }
        if (currentitem.getRemoveInnerPrintedLabel()) {
          orgitem.setRemoveInnerPrintedLabel(true);
        }
        if (currentitem.getAddInnerPrintedLabel()) {
          orgitem.setAddInnerPrintedLabel(true);
        }
        if (currentitem.getRemoveInnerWovenLabel()) {
          orgitem.setRemoveInnerWovenLabel(true);
        }
        if (currentitem.getAddInnerWovenLabel()) {
          orgitem.setAddInnerWovenLabel(true);
        }
        if (currentitem.getSewingPatches()) {
          orgitem.setSewingPatches(true);
        }
        if (currentitem.getHeatPressPatches()) {
          orgitem.setHeatPressPatches(true);
        }
        if (currentitem.getTagging()) {
          orgitem.setTagging(true);
        }
        if (currentitem.getStickers()) {
          orgitem.setStickers(true);
        }
        if (currentitem.getProductSampleEmail()) {
          orgitem.setProductSampleEmail(true);
        }
        if (currentitem.getProductSampleShip()) {
          orgitem.setProductSampleShip(true);
        }
        int tempper1 = orgitem.getPersonalizationChanges() != null ? orgitem.getPersonalizationChanges().intValue() : 0;
        int tempper2 = currentitem.getPersonalizationChanges() != null ? currentitem.getPersonalizationChanges().intValue() : 0;
        orgitem.setPersonalizationChanges(Integer.valueOf(tempper1 + tempper2));
        
        matchingitemset.addItem(currentitem);
        
        for (thelogos = matchingitemset.getLogos().iterator(); thelogos.hasNext(); 
            allcolorwaymatch.hasNext())
        {
          OrderDataLogo groupedorderdatalogo = (OrderDataLogo)thelogos.next();
          allcolorwaymatch = theset.getLogos().iterator(); continue;OrderDataLogo thelogos = (OrderDataLogo)allcolorwaymatch.next();
          if ((groupedorderdatalogo.getLogoLocation().equals(thelogos.getLogoLocation())) && (groupedorderdatalogo.getFilename().equals(thelogos.getFilename())) && (groupedorderdatalogo.getDstFilename().equals(thelogos.getDstFilename())) && (groupedorderdatalogo.getColorwayCount() == thelogos.getColorwayCount())) {
            boolean allcolorwaymatch = true;
            for (int i = 0; i < groupedorderdatalogo.getColorwayCount(); i++) {
              if ((!groupedorderdatalogo.getColorway(i).getThreadType().equals(thelogos.getColorway(i).getThreadType())) || (!groupedorderdatalogo.getColorway(i).getLogoColorCode().equals(thelogos.getColorway(i).getLogoColorCode())) || (!groupedorderdatalogo.getColorway(i).getInkType().equals(thelogos.getColorway(i).getInkType()))) {
                allcolorwaymatch = false;
              }
            }
            if (allcolorwaymatch) {
              double tempartcharge1 = groupedorderdatalogo.getArtworkChargePerHour() != null ? groupedorderdatalogo.getArtworkChargePerHour().doubleValue() : 0.0D;
              double tempartcharge2 = thelogos.getArtworkChargePerHour() != null ? thelogos.getArtworkChargePerHour().doubleValue() : 0.0D;
              groupedorderdatalogo.setArtworkChargePerHour(Double.valueOf(tempartcharge1 + tempartcharge2));
              if (thelogos.getDigitizing()) {
                groupedorderdatalogo.setDigitizing(true);
              }
              if (thelogos.getFilmCharge()) {
                groupedorderdatalogo.setFilmCharge(true);
              }
              if (thelogos.getPMSMatch()) {
                groupedorderdatalogo.setPMSMatch(true);
              }
              if (thelogos.getTapeEdit()) {
                groupedorderdatalogo.setTapeEdit(true);
              }
              if (thelogos.getFlashCharge()) {
                groupedorderdatalogo.setFlashCharge(true);
              }
              if (thelogos.getMetallicThread()) {
                groupedorderdatalogo.setMetallicThread(true);
              }
              if (thelogos.getNeonThread()) {
                groupedorderdatalogo.setNeonThread(true);
              }
              if (thelogos.getSpecialInk()) {
                groupedorderdatalogo.setSpecialInk(true);
              }
              if (thelogos.getColorChange()) {
                groupedorderdatalogo.setColorChange(true);
              }
              if (thelogos.getSwatchEmail()) {
                groupedorderdatalogo.setSwatchEmail(true);
              }
              if (thelogos.getSwatchShip()) {
                groupedorderdatalogo.setSwatchShip(true);
              }
              int tempcolorchangeamt1 = groupedorderdatalogo.getColorChangeAmount() != null ? groupedorderdatalogo.getColorChangeAmount().intValue() : 0;
              int tempcolorchangeamt2 = thelogos.getColorChangeAmount() != null ? thelogos.getColorChangeAmount().intValue() : 0;
              groupedorderdatalogo.setColorChangeAmount(Integer.valueOf(tempcolorchangeamt1 + tempcolorchangeamt2));
            }
            
          }
          
        }
      }
      else
      {
        this.groupedorderdata.addSet(((com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataSet)theset).copy());
      }
    }
  }
  

  public ExtendOrderData getGroupedOrderData()
  {
    return this.groupedorderdata;
  }
}
