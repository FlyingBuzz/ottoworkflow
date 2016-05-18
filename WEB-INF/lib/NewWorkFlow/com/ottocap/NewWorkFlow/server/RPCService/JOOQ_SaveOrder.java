package com.ottocap.NewWorkFlow.server.RPCService;

import com.extjs.gxt.ui.client.core.FastMap;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData.OrderType;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataAddress;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataCustomerInformation;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataItem;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogo;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataLogoDecoration;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataSet;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataVendorInformation;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataVendors;
import com.ottocap.NewWorkFlow.server.JOOQSQL;
import com.ottocap.NewWorkFlow.server.PriceCalculation.JOOQ_DomesticPriceCalculation;
import com.ottocap.NewWorkFlow.server.PriceCalculation.JOOQ_OverseasPriceCalculation;
import com.ottocap.NewWorkFlow.server.SharedData;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;
import javax.servlet.http.HttpSession;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.InsertSetMoreStep;
import org.jooq.Record;
import org.jooq.SelectJoinStep;
import org.jooq.SelectOffsetStep;
import org.jooq.SelectSelectStep;
import org.jooq.TableLike;
import org.jooq.impl.DSL;

public class JOOQ_SaveOrder
{
  private OrderData orderdata;
  private HttpSession httpsession;
  
  public JOOQ_SaveOrder(OrderData orderdata, HttpSession httpsession) throws Exception
  {
    try
    {
      this.orderdata = orderdata;
      this.httpsession = httpsession;
      checkSession();
      String searchtags = "";
      
      new FindColorwayChange(orderdata);
      

      boolean embfound = false;
      boolean heatfound = false;
      boolean patchfound = false;
      boolean screenfound = false;
      boolean dtgfound = false;
      String mainfilename = "";
      int totalembqty = 0;
      int totalheatqty = 0;
      
      int totalosqty = 0;
      
      FastMap<Object> datafields = new FastMap();
      datafields.put("hiddenkey", orderdata.getHiddenKey());
      
      datafields.put("setcount", Integer.valueOf(orderdata.getSetCount()));
      datafields.put("discountitemcount", Integer.valueOf(orderdata.getDiscountItemCount()));
      datafields.put("eclipseaccountnumber", orderdata.getCustomerInformation().getEclipseAccountNumber());
      datafields.put("contactname", orderdata.getCustomerInformation().getContactName());
      datafields.put("company", orderdata.getCustomerInformation().getCompany());
      datafields.put("companylogo", orderdata.getCustomerInformation().getCompanyLogo());
      datafields.put("phone", orderdata.getCustomerInformation().getPhone());
      datafields.put("fax", orderdata.getCustomerInformation().getFax());
      datafields.put("email", orderdata.getCustomerInformation().getEmail());
      datafields.put("terms", orderdata.getCustomerInformation().getTerms());
      datafields.put("billcompany", orderdata.getCustomerInformation().getBillInformation().getCompany());
      datafields.put("billstreet1", orderdata.getCustomerInformation().getBillInformation().getStreetLine1());
      datafields.put("billstreet2", orderdata.getCustomerInformation().getBillInformation().getStreetLine2());
      datafields.put("billcity", orderdata.getCustomerInformation().getBillInformation().getCity());
      datafields.put("billstate", orderdata.getCustomerInformation().getBillInformation().getState());
      datafields.put("billzip", orderdata.getCustomerInformation().getBillInformation().getZip());
      datafields.put("billcountry", orderdata.getCustomerInformation().getBillInformation().getCountry());
      datafields.put("shipattn", orderdata.getCustomerInformation().getShipAttention());
      datafields.put("sameasbillingaddress", Boolean.valueOf(orderdata.getCustomerInformation().getSameAsBillingAddress()));
      datafields.put("blindshippingrequired", Boolean.valueOf(orderdata.getCustomerInformation().getBlindShippingRequired()));
      datafields.put("taxexampt", Boolean.valueOf(orderdata.getCustomerInformation().getTaxExampt()));
      datafields.put("havedropshipment", Boolean.valueOf(orderdata.getCustomerInformation().getHaveDropShipment()));
      datafields.put("dropshipmentamount", orderdata.getCustomerInformation().getDropShipmentAmount());
      if (orderdata.getCustomerInformation().getSameAsBillingAddress()) {
        datafields.put("shipcompany", orderdata.getCustomerInformation().getBillInformation().getCompany());
        datafields.put("shipstreet1", orderdata.getCustomerInformation().getBillInformation().getStreetLine1());
        datafields.put("shipstreet2", orderdata.getCustomerInformation().getBillInformation().getStreetLine2());
        datafields.put("shipcity", orderdata.getCustomerInformation().getBillInformation().getCity());
        datafields.put("shipstate", orderdata.getCustomerInformation().getBillInformation().getState());
        datafields.put("shipzip", orderdata.getCustomerInformation().getBillInformation().getZip());
        datafields.put("shipcountry", orderdata.getCustomerInformation().getBillInformation().getCountry());
      } else {
        datafields.put("shipcompany", orderdata.getCustomerInformation().getShipInformation().getCompany());
        datafields.put("shipstreet1", orderdata.getCustomerInformation().getShipInformation().getStreetLine1());
        datafields.put("shipstreet2", orderdata.getCustomerInformation().getShipInformation().getStreetLine2());
        datafields.put("shipcity", orderdata.getCustomerInformation().getShipInformation().getCity());
        datafields.put("shipstate", orderdata.getCustomerInformation().getShipInformation().getState());
        datafields.put("shipzip", orderdata.getCustomerInformation().getShipInformation().getZip());
        datafields.put("shipcountry", orderdata.getCustomerInformation().getShipInformation().getCountry());
      }
      
      datafields.put("shipresidential", Boolean.valueOf(orderdata.getCustomerInformation().getShipInformation().getResidential()));
      
      if ((SharedData.isFaya.booleanValue()) && (orderdata.getHiddenKey() != null)) {
        orderdata.setOrderNumber(String.valueOf(orderdata.getHiddenKey()));
      }
      datafields.put("ordernumber", orderdata.getOrderNumber());
      
      datafields.put("customerpo", orderdata.getCustomerPO());
      datafields.put("orderdate", orderdata.getOrderDate());
      datafields.put("ordershipdate", orderdata.getOrderShipDate());
      datafields.put("estimatedshipdate", orderdata.getEstimatedShipDate());
      datafields.put("inhanddate", orderdata.getInHandDate());
      datafields.put("rushorder", Boolean.valueOf(orderdata.getRushOrder()));
      datafields.put("orderstatus", orderdata.getOrderStatus());
      datafields.put("specialnotes", orderdata.getSpecialNotes());
      datafields.put("nextaction", orderdata.getNextAction());
      datafields.put("internalduedatetime", orderdata.getInternalDueDateTime());
      datafields.put("internalcomments", orderdata.getInternalComments());
      datafields.put("potentialrepeatfrequency", orderdata.getPotentialRepeatFrequency());
      datafields.put("potentialinternalcomments", orderdata.getPotentialInternalComments());
      datafields.put("potentialrepeatdate", orderdata.getPotentialRepeatDate());
      datafields.put("shippingtype", orderdata.getShippingType());
      datafields.put("shippingcost", orderdata.getShippingCost());
      datafields.put("ordertype", orderdata.getOrderType() == OrderData.OrderType.OVERSEAS ? "Overseas" : "Domestic");
      datafields.put("quotetoorder", orderdata.getQuoteToOrder());
      
      if (((orderdata.getQuoteToOrder().equals("quote")) || (orderdata.getQuoteToOrder().equals("quotecanceled"))) && (
        (orderdata.getOrderStatus().equals("Order Completed")) || (orderdata.getOrderStatus().equals("Order Wrap-Up Call")) || (orderdata.getOrderStatus().equals("Partially Shipped")))) {
        orderdata.setQuoteToOrder("quotetoorder");
        datafields.put("quotetoorder", orderdata.getQuoteToOrder());
      }
      
      if (((orderdata.getQuoteToOrder().equals("quote")) || (orderdata.getQuoteToOrder().equals("quotetoorder"))) && (
        (orderdata.getOrderStatus().equals("Cancelled Order")) || (orderdata.getOrderStatus().equals("Quot. Lost Delivery Time Issue")) || (orderdata.getOrderStatus().equals("Quot. Lost Other Issue")) || (orderdata.getOrderStatus().equals("Quot. Lost Price Issue")) || (orderdata.getOrderStatus().equals("Quot. Lost Quality Issue")))) {
        orderdata.setQuoteToOrder("quotecanceled");
        datafields.put("quotetoorder", orderdata.getQuoteToOrder());
      }
      
      if ((orderdata.getOrderStatus().equals("New Quotation")) || (orderdata.getOrderStatus().equals("Quotation Follow-Up"))) {
        orderdata.setQuoteToOrder("quote");
        datafields.put("quotetoorder", orderdata.getQuoteToOrder());
      }
      
      datafields = calculateAlertDate(datafields);
      
      if (orderdata.getHiddenKey() == null) {
        if ((orderdata.getEmployeeId() != null) && (!orderdata.getEmployeeId().equals(""))) {
          datafields.put("employeeid", orderdata.getEmployeeId());
        } else {
          datafields.put("employeeid", httpsession.getAttribute("username"));
        }
        if (orderdata.getOrderType() == OrderData.OrderType.OVERSEAS) {
          Record sqlraterecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "rate" })).from(new TableLike[] { DSL.tableByName(new String[] { "exchange_rates" }) }).orderBy(new org.jooq.SortField[] { DSL.fieldByName(new String[] { "ratedate" }).desc() }).limit(1).fetchOne();
          if (sqlraterecord != null) {
            datafields.put("exchangerate", sqlraterecord.getValue(DSL.fieldByName(new String[] { "rate" })));
          } else {
            throw new Exception("Missing Exchange Rate");
          }
        }
        
        Date currentdate = new Date();
        orderdata.setOrderDate(currentdate);
        datafields.put("orderdate", orderdata.getOrderDate());
        orderdata.setHiddenKey(insertRow("ordermain", datafields));
        
        if (SharedData.isFaya.booleanValue()) {
          FastMap<Object> datafields2 = new FastMap();
          orderdata.setOrderNumber(String.valueOf(orderdata.getHiddenKey()));
          datafields2.put("ordernumber", orderdata.getOrderNumber());
          insertUpdateRow("ordermain", datafields2);
        }
      }
      else {
        datafields.remove("employeeid");
        insertUpdateRow("ordermain", datafields);
      }
      
      for (int i = 0; i < orderdata.getDiscountItemCount(); i++) {
        FastMap<Object> datafields_discountitems = new FastMap();
        
        datafields_discountitems.put("hiddenkey", orderdata.getHiddenKey());
        datafields_discountitems.put("discountitem", Integer.valueOf(i));
        datafields_discountitems.put("reason", orderdata.getDiscountItem(i).getReason());
        datafields_discountitems.put("amount", orderdata.getDiscountItem(i).getAmount());
        datafields_discountitems.put("intoitems", Boolean.valueOf(orderdata.getDiscountItem(i).getIntoItems() == null ? false : orderdata.getDiscountItem(i).getIntoItems().booleanValue()));
        
        insertUpdateRow("ordermain_discounts", datafields_discountitems);
      }
      
      for (int i = 0; i < orderdata.getSetCount(); i++) {
        FastMap<Object> datafields_sets = new FastMap();
        
        datafields_sets.put("hiddenkey", orderdata.getHiddenKey());
        datafields_sets.put("set", Integer.valueOf(i));
        datafields_sets.put("itemcount", Integer.valueOf(orderdata.getSet(i).getItemCount()));
        datafields_sets.put("logocount", Integer.valueOf(orderdata.getSet(i).getLogoCount()));
        
        insertUpdateRow("ordermain_sets", datafields_sets);
        

        int salesreportitemcount = 0;
        
        for (int j = 0; j < orderdata.getSet(i).getItemCount(); j++) {
          FastMap<Object> datafields_items = new FastMap();
          
          datafields_items.put("hiddenkey", orderdata.getHiddenKey());
          datafields_items.put("set", Integer.valueOf(i));
          datafields_items.put("item", Integer.valueOf(j));
          datafields_items.put("stylenumber", orderdata.getSet(i).getItem(j).getStyleNumber());
          datafields_items.put("vendornumber", orderdata.getSet(i).getItem(j).getVendorNumber());
          datafields_items.put("closurestylenumber", orderdata.getSet(i).getItem(j).getClosureStyleNumber());
          datafields_items.put("visorstylenumber", orderdata.getSet(i).getItem(j).getVisorStyleNumber());
          datafields_items.put("sweatbandstylenumber", orderdata.getSet(i).getItem(j).getSweatbandStyleNumber());
          datafields_items.put("eyeletstylenumber", orderdata.getSet(i).getItem(j).getEyeletStyleNumber());
          datafields_items.put("colorcode", orderdata.getSet(i).getItem(j).getColorCode());
          datafields_items.put("frontpanelcolor", orderdata.getSet(i).getItem(j).getFrontPanelColor());
          datafields_items.put("sidebackpanelcolor", orderdata.getSet(i).getItem(j).getSideBackPanelColor());
          datafields_items.put("backpanelcolor", orderdata.getSet(i).getItem(j).getBackPanelColor());
          datafields_items.put("sidepanelcolor", orderdata.getSet(i).getItem(j).getSidePanelColor());
          datafields_items.put("primaryvisorcolor", orderdata.getSet(i).getItem(j).getPrimaryVisorColor());
          datafields_items.put("visortrimcolor", orderdata.getSet(i).getItem(j).getVisorTrimColor());
          datafields_items.put("visorsandwichcolor", orderdata.getSet(i).getItem(j).getVisorSandwichColor());
          datafields_items.put("undervisorcolor", orderdata.getSet(i).getItem(j).getUndervisorColor());
          datafields_items.put("distressedvisorinsidecolor", orderdata.getSet(i).getItem(j).getDistressedVisorInsideColor());
          datafields_items.put("closurestrapcolor", orderdata.getSet(i).getItem(j).getClosureStrapColor());
          datafields_items.put("eyeletcolor", orderdata.getSet(i).getItem(j).getFrontEyeletColor());
          datafields_items.put("sidebackeyeletcolor", orderdata.getSet(i).getItem(j).getSideBackEyeletColor());
          datafields_items.put("backeyeletcolor", orderdata.getSet(i).getItem(j).getBackEyeletColor());
          datafields_items.put("sideeyeletcolor", orderdata.getSet(i).getItem(j).getSideEyeletColor());
          datafields_items.put("buttoncolor", orderdata.getSet(i).getItem(j).getButtonColor());
          datafields_items.put("innertapingcolor", orderdata.getSet(i).getItem(j).getInnerTapingColor());
          datafields_items.put("sweatbandcolor", orderdata.getSet(i).getItem(j).getSweatbandColor());
          datafields_items.put("sweatbandstripecolor", orderdata.getSet(i).getItem(j).getSweatbandStripeColor());
          datafields_items.put("quantity", orderdata.getSet(i).getItem(j).getQuantity());
          datafields_items.put("size", orderdata.getSet(i).getItem(j).getSize());
          datafields_items.put("comments", orderdata.getSet(i).getItem(j).getComments());
          datafields_items.put("polybag", Boolean.valueOf(orderdata.getSet(i).getItem(j).getPolybag()));
          datafields_items.put("previewfilename", orderdata.getSet(i).getItem(j).getPreviewFilename());
          datafields_items.put("removeinnerprintedlabel", Boolean.valueOf(orderdata.getSet(i).getItem(j).getRemoveInnerPrintedLabel()));
          datafields_items.put("removeinnerwovenlabel", Boolean.valueOf(orderdata.getSet(i).getItem(j).getRemoveInnerWovenLabel()));
          datafields_items.put("addinnerprintedlabel", Boolean.valueOf(orderdata.getSet(i).getItem(j).getAddInnerPrintedLabel()));
          datafields_items.put("addinnerwovenlabel", Boolean.valueOf(orderdata.getSet(i).getItem(j).getAddInnerWovenLabel()));
          datafields_items.put("sewingpatches", Boolean.valueOf(orderdata.getSet(i).getItem(j).getSewingPatches()));
          datafields_items.put("heatpresspatches", Boolean.valueOf(orderdata.getSet(i).getItem(j).getHeatPressPatches()));
          datafields_items.put("tagging", Boolean.valueOf(orderdata.getSet(i).getItem(j).getTagging()));
          datafields_items.put("stickers", Boolean.valueOf(orderdata.getSet(i).getItem(j).getStickers()));
          datafields_items.put("personalizationchanges", orderdata.getSet(i).getItem(j).getPersonalizationChanges());
          datafields_items.put("oakleaves", Boolean.valueOf(orderdata.getSet(i).getItem(j).getOakLeaves()));
          datafields_items.put("fobprice", orderdata.getSet(i).getItem(j).getFOBPrice());
          datafields_items.put("productsampleemail", Boolean.valueOf(orderdata.getSet(i).getItem(j).getProductSampleEmail()));
          datafields_items.put("productsampleship", Boolean.valueOf(orderdata.getSet(i).getItem(j).getProductSampleShip()));
          datafields_items.put("productsampletodo", orderdata.getSet(i).getItem(j).getProductSampleToDo());
          datafields_items.put("productsampletotaldone", orderdata.getSet(i).getItem(j).getProductSampleTotalDone());
          datafields_items.put("productsampletotalship", orderdata.getSet(i).getItem(j).getProductSampleTotalShip());
          datafields_items.put("productsampletotalemail", orderdata.getSet(i).getItem(j).getProductSampleTotalEmail());
          datafields_items.put("sampleapprovedlist", orderdata.getSet(i).getItem(j).getSampleApprovedList());
          datafields_items.put("customstylename", orderdata.getSet(i).getItem(j).getCustomStyleName());
          datafields_items.put("hasprivatelabel", orderdata.getSet(i).getItem(j).getHasPrivateLabel());
          datafields_items.put("artworktype", orderdata.getSet(i).getItem(j).getArtworkType());
          datafields_items.put("artworktypecomments", orderdata.getSet(i).getItem(j).getArtworkTypeComments());
          
          datafields_items.put("profile", orderdata.getSet(i).getItem(j).getProfile());
          datafields_items.put("numberofpanels", orderdata.getSet(i).getItem(j).getNumberOfPanels());
          datafields_items.put("crownconstruction", orderdata.getSet(i).getItem(j).getCrownConstruction());
          datafields_items.put("visorrowstitching", orderdata.getSet(i).getItem(j).getVisorRowStitching());
          datafields_items.put("frontpanelfabric", orderdata.getSet(i).getItem(j).getFrontPanelFabric());
          datafields_items.put("sidebackpanelfabric", orderdata.getSet(i).getItem(j).getSideBackPanelFabric());
          datafields_items.put("backpanelfabric", orderdata.getSet(i).getItem(j).getBackPanelFabric());
          datafields_items.put("sidepanelfabric", orderdata.getSet(i).getItem(j).getSidePanelFabric());
          datafields_items.put("panelstitchcolor", orderdata.getSet(i).getItem(j).getPanelStitchColor());
          datafields_items.put("visorstitchcolor", orderdata.getSet(i).getItem(j).getVisorStitchColor());
          
          searchtags = searchtags + orderdata.getSet(i).getItem(j).getStyleNumber() + " " + orderdata.getSet(i).getItem(j).getCustomStyleName() + " ";
          
          insertUpdateRow("ordermain_sets_items", datafields_items);
          
          salesreportitemcount += (orderdata.getSet(i).getItem(j).getQuantity() != null ? orderdata.getSet(i).getItem(j).getQuantity().intValue() : 0);
        }
        
        totalosqty += salesreportitemcount;
        boolean doheatadd = false;
        boolean doembadd = false;
        boolean dopatchadd = false;
        
        for (int j = 0; j < orderdata.getSet(i).getLogoCount(); j++) {
          FastMap<Object> datafields_items_logos = new FastMap();
          
          datafields_items_logos.put("hiddenkey", orderdata.getHiddenKey());
          datafields_items_logos.put("set", Integer.valueOf(i));
          datafields_items_logos.put("logo", Integer.valueOf(j));
          datafields_items_logos.put("filename", orderdata.getSet(i).getLogo(j).getFilename());
          datafields_items_logos.put("logolocation", orderdata.getSet(i).getLogo(j).getLogoLocation());
          datafields_items_logos.put("decoration", orderdata.getSet(i).getLogo(j).getDecoration());
          if (orderdata.getOrderType() == OrderData.OrderType.DOMESTIC) {
            if (orderdata.getSet(i).getLogo(j).getDecoration().equals("Direct To Garment")) {
              dtgfound = true;
              doheatadd = true;
            } else if (orderdata.getSet(i).getLogo(j).getDecoration().equals("Heat Transfer")) {
              heatfound = true;
              doheatadd = true;
            } else if (orderdata.getSet(i).getLogo(j).getDecoration().equals("Patch")) {
              patchfound = true;
              dopatchadd = true;
            } else if ((orderdata.getSet(i).getLogo(j).getDecoration().equals("Screen Print")) || (orderdata.getSet(i).getLogo(j).getDecoration().equals("Four Color Screen Print"))) {
              screenfound = true;
              doheatadd = true;
            } else {
              embfound = true;
              doembadd = true;
            }
          }
          

          datafields_items_logos.put("logosizewidth", orderdata.getSet(i).getLogo(j).getLogoSizeWidth());
          datafields_items_logos.put("logosizeheight", orderdata.getSet(i).getLogo(j).getLogoSizeHeight());
          datafields_items_logos.put("numofcolor", orderdata.getSet(i).getLogo(j).getNumberOfColor());
          datafields_items_logos.put("stitchcount", orderdata.getSet(i).getLogo(j).getStitchCount());
          datafields_items_logos.put("artworkchargehour", orderdata.getSet(i).getLogo(j).getArtworkChargePerHour());
          datafields_items_logos.put("artworktype", orderdata.getSet(i).getLogo(j).getArtworkType());
          datafields_items_logos.put("artworktypecomments", orderdata.getSet(i).getLogo(j).getArtworkTypeComments());
          datafields_items_logos.put("digitizing", Boolean.valueOf(orderdata.getSet(i).getLogo(j).getDigitizing()));
          datafields_items_logos.put("filmcharge", Boolean.valueOf(orderdata.getSet(i).getLogo(j).getFilmCharge()));
          datafields_items_logos.put("pmsmatch", Boolean.valueOf(orderdata.getSet(i).getLogo(j).getPMSMatch()));
          datafields_items_logos.put("tapeedit", Boolean.valueOf(orderdata.getSet(i).getLogo(j).getTapeEdit()));
          datafields_items_logos.put("flashcharge", Boolean.valueOf(orderdata.getSet(i).getLogo(j).getFlashCharge()));
          datafields_items_logos.put("specialthread", Boolean.valueOf(orderdata.getSet(i).getLogo(j).getMetallicThread()));
          datafields_items_logos.put("specialink", Boolean.valueOf(orderdata.getSet(i).getLogo(j).getSpecialInk()));
          datafields_items_logos.put("colorchange", Boolean.valueOf(orderdata.getSet(i).getLogo(j).getColorChange()));
          datafields_items_logos.put("colorchangeamount", orderdata.getSet(i).getLogo(j).getColorChangeAmount());
          datafields_items_logos.put("colordescription", orderdata.getSet(i).getLogo(j).getColorDescription());
          datafields_items_logos.put("threadbrand", orderdata.getSet(i).getLogo(j).getThreadBrand());
          datafields_items_logos.put("colorwaycount", Integer.valueOf(orderdata.getSet(i).getLogo(j).getColorwayCount()));
          datafields_items_logos.put("swatchemail", Boolean.valueOf(orderdata.getSet(i).getLogo(j).getSwatchEmail()));
          datafields_items_logos.put("swatchship", Boolean.valueOf(orderdata.getSet(i).getLogo(j).getSwatchShip()));
          datafields_items_logos.put("swatchtodo", orderdata.getSet(i).getLogo(j).getSwatchToDo());
          datafields_items_logos.put("swatchtotaldone", orderdata.getSet(i).getLogo(j).getSwatchTotalDone());
          datafields_items_logos.put("filmsetupcharge", orderdata.getSet(i).getLogo(j).getFilmSetupCharge());
          datafields_items_logos.put("logosizechoice", orderdata.getSet(i).getLogo(j).getLogoSizeChoice());
          datafields_items_logos.put("dstfilename", orderdata.getSet(i).getLogo(j).getDstFilename());
          datafields_items_logos.put("namedroplogo", orderdata.getSet(i).getLogo(j).getNameDropLogo());
          datafields_items_logos.put("decorationoptionscount", orderdata.getSet(i).getLogo(j).getDecorationCount());
          datafields_items_logos.put("swatchtotalship", orderdata.getSet(i).getLogo(j).getSwatchTotalShip());
          datafields_items_logos.put("swatchtotalemail", orderdata.getSet(i).getLogo(j).getSwatchTotalEmail());
          datafields_items_logos.put("customerlogoprice", orderdata.getSet(i).getLogo(j).getCustomerLogoPrice());
          datafields_items_logos.put("vendorlogoprice", orderdata.getSet(i).getLogo(j).getVendorLogoPrice());
          
          if (mainfilename.equals("")) {
            if (!orderdata.getSet(i).getLogo(j).getFilename().equals("")) {
              mainfilename = orderdata.getSet(i).getLogo(j).getFilename();
            } else if (!orderdata.getSet(i).getLogo(j).getDstFilename().equals("")) {
              mainfilename = orderdata.getSet(i).getLogo(j).getDstFilename();
            }
          }
          
          insertUpdateRow("ordermain_sets_logos", datafields_items_logos);
          
          com.ottocap.NewWorkFlow.server.LogoGenerator.DSTImage mydst = null;
          if (((orderdata.getSet(i).getLogo(j).getDstFilename() != null) && (orderdata.getSet(i).getLogo(j).getDstFilename().toLowerCase().endsWith(".dst"))) || ((orderdata.getSet(i).getLogo(j).getFilename() != null) && (orderdata.getSet(i).getLogo(j).getFilename().toLowerCase().endsWith(".dst")))) {
            String filename = "";
            if ((orderdata.getSet(i).getLogo(j).getDstFilename() != null) && (orderdata.getSet(i).getLogo(j).getDstFilename().toLowerCase().endsWith(".dst"))) {
              filename = orderdata.getSet(i).getLogo(j).getDstFilename();
            } else {
              filename = orderdata.getSet(i).getLogo(j).getFilename();
            }
            try {
              mydst = new com.ottocap.NewWorkFlow.server.LogoGenerator.DSTImage("C:\\WorkFlow\\NewWorkflowData\\" + (orderdata.getOrderType() == OrderData.OrderType.DOMESTIC ? "Domestic" : "Overseas") + "Data/" + orderdata.getHiddenKey() + "/logos/" + filename);
            }
            catch (Exception localException1) {}
          }
          

          for (int k = 0; k < orderdata.getSet(i).getLogo(j).getColorwayCount(); k++) {
            FastMap<Object> datafields_items_logos_colorways = new FastMap();
            
            datafields_items_logos_colorways.put("hiddenkey", orderdata.getHiddenKey());
            datafields_items_logos_colorways.put("set", Integer.valueOf(i));
            datafields_items_logos_colorways.put("logo", Integer.valueOf(j));
            datafields_items_logos_colorways.put("colorway", Integer.valueOf(k));
            datafields_items_logos_colorways.put("logocolorcode", orderdata.getSet(i).getLogo(j).getColorway(k).getLogoColorCode());
            datafields_items_logos_colorways.put("colorcodecomment", orderdata.getSet(i).getLogo(j).getColorway(k).getColorCodeComment());
            datafields_items_logos_colorways.put("threadtype", orderdata.getSet(i).getLogo(j).getColorway(k).getThreadType());
            datafields_items_logos_colorways.put("stitches", orderdata.getSet(i).getLogo(j).getColorway(k).getStitches());
            if ((mydst != null) && (k < mydst.getTotalColors())) {
              datafields_items_logos_colorways.put("stitches", Integer.valueOf(mydst.getStitchCount(k)));
            }
            
            datafields_items_logos_colorways.put("flashcharge", Boolean.valueOf(orderdata.getSet(i).getLogo(j).getColorway(k).getFlashCharge()));
            datafields_items_logos_colorways.put("inktype", orderdata.getSet(i).getLogo(j).getColorway(k).getInkType());
            
            insertUpdateRow("ordermain_sets_logos_colorways", datafields_items_logos_colorways);
          }
          

          for (int k = 0; k < orderdata.getSet(i).getLogo(j).getDecorationCount().intValue(); k++) {
            FastMap<Object> datafields_items_logos_decorations = new FastMap();
            
            datafields_items_logos_decorations.put("hiddenkey", orderdata.getHiddenKey());
            datafields_items_logos_decorations.put("set", Integer.valueOf(i));
            datafields_items_logos_decorations.put("logo", Integer.valueOf(j));
            datafields_items_logos_decorations.put("decoration", Integer.valueOf(k));
            datafields_items_logos_decorations.put("name", orderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getName());
            datafields_items_logos_decorations.put("field1", orderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getField1());
            datafields_items_logos_decorations.put("field2", orderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getField2());
            datafields_items_logos_decorations.put("field3", orderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getField3());
            datafields_items_logos_decorations.put("field4", orderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getField4());
            datafields_items_logos_decorations.put("comments", orderdata.getSet(i).getLogo(j).getDecoration(Integer.valueOf(k)).getComments());
            
            insertUpdateRow("ordermain_sets_logos_decorations", datafields_items_logos_decorations);
          }
        }
        

        if (doheatadd) {
          totalheatqty += salesreportitemcount;
        }
        


        if (doembadd) {
          totalembqty += salesreportitemcount;
        }
      }
      

      if (orderdata.getOrderType().equals(OrderData.OrderType.DOMESTIC))
      {
        FastMap<Object> digitizingvendor = new FastMap();
        
        digitizingvendor.put("hiddenkey", orderdata.getHiddenKey());
        digitizingvendor.put("vendortype", "digitize");
        digitizingvendor.put("vendornumber", orderdata.getVendorInformation().getDigitizerVendor().getVendor());
        if (SharedData.isFaya.booleanValue()) {
          orderdata.getVendorInformation().getDigitizerVendor().setWorkOrderNumber(String.valueOf(orderdata.getHiddenKey()));
        }
        digitizingvendor.put("ponumber", orderdata.getVendorInformation().getDigitizerVendor().getWorkOrderNumber());
        digitizingvendor.put("digitizingprocessingdate", orderdata.getVendorInformation().getDigitizerVendor().getDigitizingProcessingDate());
        digitizingvendor.put("digitizingduedate", orderdata.getVendorInformation().getDigitizerVendor().getDigitizingDueDate());
        
        insertUpdateRow("ordermain_vendors", digitizingvendor);
        

        FastMap<Object> embroideryvendor = new FastMap();
        
        embroideryvendor.put("hiddenkey", orderdata.getHiddenKey());
        embroideryvendor.put("vendortype", "embroidery");
        embroideryvendor.put("vendornumber", orderdata.getVendorInformation().getEmbroideryVendor().getVendor());
        if (SharedData.isFaya.booleanValue()) {
          orderdata.getVendorInformation().getEmbroideryVendor().setWorkOrderNumber(String.valueOf(orderdata.getHiddenKey()));
        }
        embroideryvendor.put("ponumber", orderdata.getVendorInformation().getEmbroideryVendor().getWorkOrderNumber());
        embroideryvendor.put("workorderprocessingdate", orderdata.getVendorInformation().getEmbroideryVendor().getWorkOrderProcessingDate());
        embroideryvendor.put("workorderduedate", orderdata.getVendorInformation().getEmbroideryVendor().getWorkOrderDueDate());
        
        insertUpdateRow("ordermain_vendors", embroideryvendor);
        


        FastMap<Object> heattransfervendor = new FastMap();
        
        heattransfervendor.put("hiddenkey", orderdata.getHiddenKey());
        heattransfervendor.put("vendortype", "heattransfer");
        heattransfervendor.put("vendornumber", orderdata.getVendorInformation().getHeatTransferVendor().getVendor());
        if (SharedData.isFaya.booleanValue()) {
          orderdata.getVendorInformation().getHeatTransferVendor().setWorkOrderNumber(String.valueOf(orderdata.getHiddenKey()));
        }
        heattransfervendor.put("ponumber", orderdata.getVendorInformation().getHeatTransferVendor().getWorkOrderNumber());
        heattransfervendor.put("workorderprocessingdate", orderdata.getVendorInformation().getHeatTransferVendor().getWorkOrderProcessingDate());
        heattransfervendor.put("workorderduedate", orderdata.getVendorInformation().getHeatTransferVendor().getWorkOrderDueDate());
        
        insertUpdateRow("ordermain_vendors", heattransfervendor);
        


        FastMap<Object> patchvendor = new FastMap();
        
        patchvendor.put("hiddenkey", orderdata.getHiddenKey());
        patchvendor.put("vendortype", "patch");
        patchvendor.put("vendornumber", orderdata.getVendorInformation().getPatchVendor().getVendor());
        if (SharedData.isFaya.booleanValue()) {
          orderdata.getVendorInformation().getPatchVendor().setWorkOrderNumber(String.valueOf(orderdata.getHiddenKey()));
        }
        patchvendor.put("ponumber", orderdata.getVendorInformation().getPatchVendor().getWorkOrderNumber());
        patchvendor.put("workorderprocessingdate", orderdata.getVendorInformation().getPatchVendor().getWorkOrderProcessingDate());
        patchvendor.put("workorderduedate", orderdata.getVendorInformation().getPatchVendor().getWorkOrderDueDate());
        
        insertUpdateRow("ordermain_vendors", patchvendor);
        


        FastMap<Object> screenprintvendor = new FastMap();
        
        screenprintvendor.put("hiddenkey", orderdata.getHiddenKey());
        screenprintvendor.put("vendortype", "screenprint");
        screenprintvendor.put("vendornumber", orderdata.getVendorInformation().getScreenPrintVendor().getVendor());
        if (SharedData.isFaya.booleanValue()) {
          orderdata.getVendorInformation().getScreenPrintVendor().setWorkOrderNumber(String.valueOf(orderdata.getHiddenKey()));
        }
        screenprintvendor.put("ponumber", orderdata.getVendorInformation().getScreenPrintVendor().getWorkOrderNumber());
        screenprintvendor.put("workorderprocessingdate", orderdata.getVendorInformation().getScreenPrintVendor().getWorkOrderProcessingDate());
        screenprintvendor.put("workorderduedate", orderdata.getVendorInformation().getScreenPrintVendor().getWorkOrderDueDate());
        
        insertUpdateRow("ordermain_vendors", screenprintvendor);
        


        FastMap<Object> directtogarmentvendor = new FastMap();
        
        directtogarmentvendor.put("hiddenkey", orderdata.getHiddenKey());
        directtogarmentvendor.put("vendortype", "directtogarment");
        if (SharedData.isFaya.booleanValue()) {
          orderdata.getVendorInformation().getDirectToGarmentVendor().setWorkOrderNumber(String.valueOf(orderdata.getHiddenKey()));
        }
        directtogarmentvendor.put("vendornumber", orderdata.getVendorInformation().getDirectToGarmentVendor().getVendor());
        directtogarmentvendor.put("ponumber", orderdata.getVendorInformation().getDirectToGarmentVendor().getWorkOrderNumber());
        directtogarmentvendor.put("workorderprocessingdate", orderdata.getVendorInformation().getDirectToGarmentVendor().getWorkOrderProcessingDate());
        directtogarmentvendor.put("workorderduedate", orderdata.getVendorInformation().getDirectToGarmentVendor().getWorkOrderDueDate());
        
        insertUpdateRow("ordermain_vendors", directtogarmentvendor);
      }
      else {
        String[] overseasvendorkeys = (String[])orderdata.getVendorInformation().getOverseasVendor().keySet().toArray(new String[0]);
        for (int i = 0; i < orderdata.getVendorInformation().getOverseasVendor().size(); i++) {
          FastMap<Object> overseasvendor = new FastMap();
          
          overseasvendor.put("hiddenkey", orderdata.getHiddenKey());
          overseasvendor.put("vendortype", "overseas");
          overseasvendor.put("vendornumber", ((OrderDataVendorInformation)orderdata.getVendorInformation().getOverseasVendor().get(overseasvendorkeys[i])).getVendor());
          overseasvendor.put("ponumber", ((OrderDataVendorInformation)orderdata.getVendorInformation().getOverseasVendor().get(overseasvendorkeys[i])).getWorkOrderNumber());
          overseasvendor.put("digitizingprocessingdate", ((OrderDataVendorInformation)orderdata.getVendorInformation().getOverseasVendor().get(overseasvendorkeys[i])).getDigitizingProcessingDate());
          overseasvendor.put("digitizingduedate", ((OrderDataVendorInformation)orderdata.getVendorInformation().getOverseasVendor().get(overseasvendorkeys[i])).getDigitizingDueDate());
          overseasvendor.put("workorderprocessingdate", ((OrderDataVendorInformation)orderdata.getVendorInformation().getOverseasVendor().get(overseasvendorkeys[i])).getWorkOrderProcessingDate());
          overseasvendor.put("workorderduedate", ((OrderDataVendorInformation)orderdata.getVendorInformation().getOverseasVendor().get(overseasvendorkeys[i])).getWorkOrderDueDate());
          overseasvendor.put("shippingmethod", ((OrderDataVendorInformation)orderdata.getVendorInformation().getOverseasVendor().get(overseasvendorkeys[i])).getShippingMethod());
          
          insertUpdateRow("ordermain_vendors", overseasvendor);
        }
      }
      
      try
      {
        Record sqltotalpricerecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "totalprice" })).from(new TableLike[] { DSL.tableByName(new String[] { "ordermain" }) }).where(new org.jooq.Condition[] { DSL.fieldByName(new String[] { "hiddenkey" }).equal(orderdata.getHiddenKey()) }).limit(1).fetchOne();
        if (sqltotalpricerecord != null)
        {
          FastMap<Object> totalpricefields = new FastMap();
          
          totalpricefields.put("hiddenkey", orderdata.getHiddenKey());
          totalpricefields.put("searchtags", searchtags);
          
          if (orderdata.getOrderStatus().equals("Order Completed")) {
            if (sqltotalpricerecord.getValue(DSL.fieldByName(new String[] { "totalprice" })) != null) { if (((Double)sqltotalpricerecord.getValue(DSL.fieldByName(new String[] { "totalprice" }))).doubleValue() != 0.0D) {}
            } else { double totalcapprice = 0.0D;
              double totaldecorationprice = 0.0D;
              double totalprice = 0.0D;
              

              com.ottocap.NewWorkFlow.server.JOOQ_OrderExpander myorder = new com.ottocap.NewWorkFlow.server.JOOQ_OrderExpander(orderdata);
              com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderData thedata = myorder.getExpandedOrderData();
              if (thedata.getOrderType() == OrderData.OrderType.DOMESTIC) {
                JOOQ_DomesticPriceCalculation mycalc = new JOOQ_DomesticPriceCalculation(thedata);
                mycalc.calculateCustomerPrice(true);
                totalcapprice = mycalc.getCustomerTotalCapPrice();
                totaldecorationprice = mycalc.getCustomerTotalDecorationPrice();
                totalprice = mycalc.getCustomerTotalPrice();
              } else {
                JOOQ_OverseasPriceCalculation mycalc = new JOOQ_OverseasPriceCalculation(thedata);
                mycalc.calculateOrderPrice(true);
                totalcapprice = mycalc.getCustomerTotalCapPrice();
                totaldecorationprice = mycalc.getCustomerTotalDecorationPrice();
                totalprice = mycalc.getCustomerTotalPrice();
              }
              
              totalpricefields.put("totalcapprice", Double.valueOf(totalcapprice));
              totalpricefields.put("totaldecorationprice", Double.valueOf(totaldecorationprice));
              totalpricefields.put("totalprice", Double.valueOf(totalprice));
            }
          } else {
            double totalcapprice = 0.0D;
            double totaldecorationprice = 0.0D;
            
            com.ottocap.NewWorkFlow.server.JOOQ_OrderExpander myorder = new com.ottocap.NewWorkFlow.server.JOOQ_OrderExpander(orderdata);
            com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderData thedata = myorder.getExpandedOrderData();
            if (thedata.getOrderType() == OrderData.OrderType.DOMESTIC) {
              JOOQ_DomesticPriceCalculation mycalc = new JOOQ_DomesticPriceCalculation(thedata);
              mycalc.calculateCustomerPrice(false);
              totalcapprice = mycalc.getCustomerTotalCapPrice();
              totaldecorationprice = mycalc.getCustomerTotalDecorationPrice();
            } else {
              JOOQ_OverseasPriceCalculation mycalc = new JOOQ_OverseasPriceCalculation(thedata);
              mycalc.calculateOrderPrice(false);
              totalcapprice = mycalc.getCustomerTotalCapPrice();
              totaldecorationprice = mycalc.getCustomerTotalDecorationPrice();
            }
            
            totalpricefields.put("totalcapprice", Double.valueOf(totalcapprice));
            totalpricefields.put("totaldecorationprice", Double.valueOf(totaldecorationprice));
          }
          
          insertUpdateRow("ordermain", totalpricefields);
        }
      }
      catch (Exception localException2) {}
      


      String employee = "";
      if ((orderdata.getEmployeeId() != null) && (!orderdata.getEmployeeId().equals(""))) {
        employee = orderdata.getEmployeeId();
      } else {
        employee = (String)httpsession.getAttribute("username");
      }
      Record sqlemployeenamerecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "firstname" }), DSL.fieldByName(new String[] { "lastname" })).from(new TableLike[] { DSL.tableByName(new String[] { "employees" }) }).where(new org.jooq.Condition[] { DSL.fieldByName(new String[] { "username" }).equal(employee) }).limit(1).fetchOne();
      if (sqlemployeenamerecord != null) {
        employee = sqlemployeenamerecord.getValue(DSL.fieldByName(new String[] { "firstname" })) + " " + sqlemployeenamerecord.getValue(DSL.fieldByName(new String[] { "lastname" }));
      }
      
      if (orderdata.getOrderType() == OrderData.OrderType.DOMESTIC)
      {
        String embvendor = null;
        String dtgvendor = null;
        String heatvendor = null;
        
        String screenvendor = null;
        Date embprocessdate = null;
        Date embduedate = null;
        Date dtgprocessdate = null;
        Date dtgduedate = null;
        Date heatprocessdate = null;
        Date heatduedate = null;
        

        Date screenprocessdate = null;
        Date screenduedate = null;
        
        org.jooq.Result<org.jooq.Record2<Object, Object>> thevendorstable = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "id" }), DSL.fieldByName(new String[] { "name" })).from(new TableLike[] { DSL.tableByName(new String[] { "list_vendors_domestic" }) }).fetch();
        
        if ((embfound) && 
          (orderdata.getVendorInformation().getEmbroideryVendor().getVendor() != null)) {
          for (Record currentvendor : thevendorstable) {
            if (((Integer)currentvendor.getValue(DSL.fieldByName(new String[] { "id" }))).intValue() == orderdata.getVendorInformation().getEmbroideryVendor().getVendor().intValue()) {
              embvendor = (String)currentvendor.getValue(DSL.fieldByName(new String[] { "name" }));
              embprocessdate = orderdata.getVendorInformation().getEmbroideryVendor().getWorkOrderProcessingDate();
              embduedate = orderdata.getVendorInformation().getEmbroideryVendor().getWorkOrderDueDate();
            }
          }
        }
        
        if ((dtgfound) && 
          (orderdata.getVendorInformation().getDirectToGarmentVendor().getVendor() != null)) {
          for (Record currentvendor : thevendorstable) {
            if (((Integer)currentvendor.getValue(DSL.fieldByName(new String[] { "id" }))).intValue() == orderdata.getVendorInformation().getDirectToGarmentVendor().getVendor().intValue()) {
              dtgvendor = (String)currentvendor.getValue(DSL.fieldByName(new String[] { "name" }));
              dtgprocessdate = orderdata.getVendorInformation().getDirectToGarmentVendor().getWorkOrderProcessingDate();
              dtgduedate = orderdata.getVendorInformation().getDirectToGarmentVendor().getWorkOrderDueDate();
            }
          }
        }
        
        if ((heatfound) && 
          (orderdata.getVendorInformation().getHeatTransferVendor().getVendor() != null)) {
          for (Record currentvendor : thevendorstable) {
            if (((Integer)currentvendor.getValue(DSL.fieldByName(new String[] { "id" }))).intValue() == orderdata.getVendorInformation().getHeatTransferVendor().getVendor().intValue()) {
              heatvendor = (String)currentvendor.getValue(DSL.fieldByName(new String[] { "name" }));
              heatprocessdate = orderdata.getVendorInformation().getHeatTransferVendor().getWorkOrderProcessingDate();
              heatduedate = orderdata.getVendorInformation().getHeatTransferVendor().getWorkOrderDueDate();
            }
          }
        }
        
        if ((patchfound) && 
          (orderdata.getVendorInformation().getPatchVendor().getVendor() != null)) {
          for (??? = thevendorstable.iterator(); ???.hasNext(); 
              orderdata.getVendorInformation().getPatchVendor().getVendor().intValue())
          {
            Record currentvendor = (Record)???.next();
            ((Integer)currentvendor.getValue(DSL.fieldByName(new String[] { "id" }))).intValue();
          }
        }
        







        if ((screenfound) && 
          (orderdata.getVendorInformation().getScreenPrintVendor().getVendor() != null)) {
          for (Record currentvendor : thevendorstable) {
            if (((Integer)currentvendor.getValue(DSL.fieldByName(new String[] { "id" }))).intValue() == orderdata.getVendorInformation().getScreenPrintVendor().getVendor().intValue()) {
              screenvendor = (String)currentvendor.getValue(DSL.fieldByName(new String[] { "name" }));
              screenprocessdate = orderdata.getVendorInformation().getScreenPrintVendor().getWorkOrderProcessingDate();
              screenduedate = orderdata.getVendorInformation().getScreenPrintVendor().getWorkOrderDueDate();
            }
          }
        }
        

        FastMap<Object> mykeys = new FastMap();
        mykeys.put("hiddenkey", orderdata.getHiddenKey());
        mykeys.put("ordertype", orderdata.getOrderType() == OrderData.OrderType.OVERSEAS ? "Overseas" : "Domestic");
        mykeys.put("employee", employee);
        mykeys.put("employeeid", orderdata.getEmployeeId());
        mykeys.put("orderdate", orderdata.getOrderDate());
        mykeys.put("embvendor", embvendor);
        mykeys.put("embprocessdate", embprocessdate);
        mykeys.put("embduedate", embduedate);
        mykeys.put("screenvendor", screenvendor);
        mykeys.put("screenprocessdate", screenprocessdate);
        mykeys.put("screenduedate", screenduedate);
        mykeys.put("heatvendor", heatvendor);
        mykeys.put("heatprocessdate", heatprocessdate);
        mykeys.put("heatduedate", heatduedate);
        mykeys.put("dtgvendor", dtgvendor);
        mykeys.put("dtgprocessdate", dtgprocessdate);
        mykeys.put("dtgduedate", dtgduedate);
        if (SharedData.isFaya.booleanValue()) {
          orderdata.getVendorInformation().getEmbroideryVendor().setWorkOrderNumber(String.valueOf(orderdata.getHiddenKey()));
        }
        mykeys.put("workordernumber", orderdata.getVendorInformation().getEmbroideryVendor().getWorkOrderNumber());
        

        mykeys.put("ordershipdate", orderdata.getOrderShipDate());
        mykeys.put("embqty", Integer.valueOf(totalembqty));
        mykeys.put("heatqty", Integer.valueOf(totalheatqty));
        mykeys.put("osqty", null);
        mykeys.put("ordernumber", orderdata.getOrderNumber());
        String combinedvendors = "";
        com.extjs.gxt.ui.client.core.FastSet differentvendors = new com.extjs.gxt.ui.client.core.FastSet();
        if ((embvendor != null) && (!embvendor.equals(""))) {
          differentvendors.add(embvendor);
        }
        if ((screenvendor != null) && (!screenvendor.equals(""))) {
          differentvendors.add(screenvendor);
        }
        if ((heatvendor != null) && (!heatvendor.equals(""))) {
          differentvendors.add(heatvendor);
        }
        if ((dtgvendor != null) && (!dtgvendor.equals(""))) {
          differentvendors.add(dtgvendor);
        }
        for (String currentvendor : differentvendors) {
          combinedvendors = combinedvendors + currentvendor + ", ";
        }
        if (combinedvendors.endsWith(", ")) {
          combinedvendors.substring(0, combinedvendors.length() - 2);
        }
        
        mykeys.put("allvendor", combinedvendors);
        mykeys.put("company", orderdata.getCustomerInformation().getCompany());
        mykeys.put("mainfilename", mainfilename);
        
        Record sqltotalpricerecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "totalcapprice" }), DSL.fieldByName(new String[] { "totaldecorationprice" })).from(new TableLike[] { DSL.tableByName(new String[] { "ordermain" }) }).where(new org.jooq.Condition[] { DSL.fieldByName(new String[] { "hiddenkey" }).equal(orderdata.getHiddenKey()) }).limit(1).fetchOne();
        Double totalcapprice = (Double)sqltotalpricerecord.getValue(DSL.fieldByName(new String[] { "totalcapprice" }));
        Double totaldecorationprice = (Double)sqltotalpricerecord.getValue(DSL.fieldByName(new String[] { "totaldecorationprice" }));
        mykeys.put("totalcapprice", totalcapprice);
        mykeys.put("totaldecorationprice", totaldecorationprice);
        
        insertUpdateRow("ordermain_sales_report", mykeys);
      } else if (orderdata.getOrderType() == OrderData.OrderType.OVERSEAS)
      {
        FastMap<Object> mykeys = new FastMap();
        mykeys.put("hiddenkey", orderdata.getHiddenKey());
        mykeys.put("ordertype", orderdata.getOrderType() == OrderData.OrderType.OVERSEAS ? "Overseas" : "Domestic");
        mykeys.put("employee", employee);
        mykeys.put("employeeid", orderdata.getEmployeeId());
        mykeys.put("workordernumber", "OS");
        

        mykeys.put("ordershipdate", orderdata.getOrderShipDate());
        mykeys.put("embqty", null);
        mykeys.put("heatqty", null);
        mykeys.put("osqty", Integer.valueOf(totalosqty));
        mykeys.put("ordernumber", orderdata.getOrderNumber());
        
        mykeys.put("allvendor", "OS");
        mykeys.put("company", orderdata.getCustomerInformation().getCompany());
        mykeys.put("mainfilename", mainfilename);
        
        Record sqltotalpricerecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "totalcapprice" }), DSL.fieldByName(new String[] { "totaldecorationprice" })).from(new TableLike[] { DSL.tableByName(new String[] { "ordermain" }) }).where(new org.jooq.Condition[] { DSL.fieldByName(new String[] { "hiddenkey" }).equal(orderdata.getHiddenKey()) }).limit(1).fetchOne();
        Double totalcapprice = (Double)sqltotalpricerecord.getValue(DSL.fieldByName(new String[] { "totalcapprice" }));
        Double totaldecorationprice = (Double)sqltotalpricerecord.getValue(DSL.fieldByName(new String[] { "totaldecorationprice" }));
        mykeys.put("totalcapprice", totalcapprice);
        mykeys.put("totaldecorationprice", totaldecorationprice);
        
        insertUpdateRow("ordermain_sales_report", mykeys);
      }
      

      orderdata.setChangeHappened(false);
    }
    catch (Exception e) {
      throw e;
    }
  }
  
  public OrderData getOrder() {
    return this.orderdata;
  }
  
  private void checkSession()
    throws Exception
  {
    if (this.httpsession.getAttribute("username") != null) {
      if ((((Integer)this.httpsession.getAttribute("level")).intValue() == 0) || (((Integer)this.httpsession.getAttribute("level")).intValue() == 1) || (((Integer)this.httpsession.getAttribute("level")).intValue() == 3)) {
        if ((this.orderdata.getHiddenKey() != null) && (
          (!this.orderdata.getOrderStatus().equals("Order Completed")) || (((Integer)this.httpsession.getAttribute("level")).intValue() != 1)))
        {


          Record employeeidrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "employeeid" })).from(new TableLike[] { DSL.tableByName(new String[] { "ordermain" }) }).where(new org.jooq.Condition[] { DSL.fieldByName(new String[] { "hiddenkey" }).equal(this.orderdata.getHiddenKey()) }).limit(1).fetchOne();
          if (employeeidrecord != null) if (!employeeidrecord.getValue(DSL.fieldByName(new String[] { "employeeid" })).equals(this.httpsession.getAttribute("username"))) {
              throw new Exception("Bad Session");
            }
        }
      }
      else if ((((Integer)this.httpsession.getAttribute("level")).intValue() != 2) && (((Integer)this.httpsession.getAttribute("level")).intValue() != 4) && (((Integer)this.httpsession.getAttribute("level")).intValue() != 5))
      {

        throw new Exception("Bad Session");
      }
    } else {
      throw new Exception("Session Expired");
    }
  }
  

  private FastMap<Object> calculateAlertDate(FastMap<Object> datafields)
  {
    ArrayList<String> thevendors = new ArrayList();
    
    if (this.orderdata.getOrderType() == OrderData.OrderType.OVERSEAS) {
      if (this.orderdata.getInHandDate() != null) {
        for (int i = 0; i < this.orderdata.getSetCount(); i++) {
          for (int j = 0; j < this.orderdata.getSet(i).getItemCount(); j++)
          {
            if ((this.orderdata.getSet(i).getItem(j).getVendorNumber().equals("")) || (this.orderdata.getSet(i).getItem(j).getVendorNumber().equals("Default"))) {
              Record sqlvendorrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "Primary VENDOR" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_overseasinstock" }) }).where(new org.jooq.Condition[] { DSL.fieldByName(new String[] { "STYLE NUMBER" }).equal(this.orderdata.getSet(i).getItem(j).getStyleNumber()) }).limit(1).fetchOne();
              if (sqlvendorrecord != null) {
                thevendors.add((String)sqlvendorrecord.getValue(DSL.fieldByName(new String[] { "Primary VENDOR" })));
              }
            } else {
              thevendors.add(this.orderdata.getSet(i).getItem(j).getVendorNumber());
            }
          }
        }
        
        String[] overseasvendorkeys = (String[])this.orderdata.getVendorInformation().getOverseasVendor().keySet().toArray(new String[0]);
        boolean hasair = false;
        for (int i = 0; i < overseasvendorkeys.length; i++) {
          if ((((OrderDataVendorInformation)this.orderdata.getVendorInformation().getOverseasVendor().get(overseasvendorkeys[i])).getVendor() != null) && 
            (thevendors.contains(((OrderDataVendorInformation)this.orderdata.getVendorInformation().getOverseasVendor().get(overseasvendorkeys[i])).getVendor().toString())) && 
            (((OrderDataVendorInformation)this.orderdata.getVendorInformation().getOverseasVendor().get(overseasvendorkeys[i])).getShippingMethod().equals("Air"))) {
            hasair = true;
            i = overseasvendorkeys.length;
          }
        }
        



        ArrayList<String> difforderstatus = new ArrayList();
        difforderstatus.add("Choose One:");
        difforderstatus.add("New Copy");
        difforderstatus.add("New Order");
        difforderstatus.add("New Quotation");
        difforderstatus.add("New Virtual Sample Request");
        difforderstatus.add("Presentation Concept Request");
        difforderstatus.add("Presentation Sample Request");
        difforderstatus.add("Quotation Follow-Up");
        difforderstatus.add("Virtual Sample Processing");
        difforderstatus.add("Virtual Sample Sent");
        difforderstatus.add("Waiting for i-Sample");
        difforderstatus.add("Waiting for Logo Color");
        difforderstatus.add("Waiting for Customer Artwork");
        difforderstatus.add("Waiting for Customer Tape");
        difforderstatus.add("Waiting for Creating Artwork");
        difforderstatus.add("Waiting for Creating Film");
        difforderstatus.add("Waiting for New Account Setup");
        difforderstatus.add("Waiting for Digitize");
        difforderstatus.add("Waiting for Stock");
        if (difforderstatus.contains(this.orderdata.getOrderStatus())) {
          if (hasair) {
            datafields.put("alertdate", new Date(this.orderdata.getInHandDate().getTime() - 5097600000L));
            datafields.put("alertdatedue", new Date(this.orderdata.getInHandDate().getTime() - 4838400000L));
          } else {
            datafields.put("alertdate", new Date(this.orderdata.getInHandDate().getTime() - 8467200000L));
            datafields.put("alertdatedue", new Date(this.orderdata.getInHandDate().getTime() - 8035200000L));
          }
          return datafields;
        }
        

        difforderstatus = new ArrayList();
        difforderstatus.add("Waiting for Accounting Approval");
        difforderstatus.add("Waiting for Deposit");
        difforderstatus.add("Waiting for Swatch");
        difforderstatus.add("Waiting for Swatch Approval");
        difforderstatus.add("Waiting for Sample");
        difforderstatus.add("Waiting for Sample Approval");
        difforderstatus.add("Waiting for Production Final Approval");
        difforderstatus.add("Waiting for Purchase Order");
        if (difforderstatus.contains(this.orderdata.getOrderStatus())) {
          if (hasair) {
            datafields.put("alertdate", new Date(this.orderdata.getInHandDate().getTime() - 4579200000L));
            datafields.put("alertdatedue", new Date(this.orderdata.getInHandDate().getTime() - 4320000000L));
          } else {
            datafields.put("alertdate", new Date(this.orderdata.getInHandDate().getTime() - 7689600000L));
            datafields.put("alertdatedue", new Date(this.orderdata.getInHandDate().getTime() - 7430400000L));
          }
          return datafields;
        }
        

        difforderstatus = new ArrayList();
        difforderstatus.add("Waiting for Overseas Confirmation");
        difforderstatus.add("Waiting to Receive Production");
        difforderstatus.add("Waiting for Overseas Confirmation");
        difforderstatus.add("Waiting for Heat Press Transfer");
        if (difforderstatus.contains(this.orderdata.getOrderStatus())) {
          if (hasair) {
            datafields.put("alertdate", new Date(this.orderdata.getInHandDate().getTime() - 3024000000L));
            datafields.put("alertdatedue", new Date(this.orderdata.getInHandDate().getTime() - 2851200000L));
          } else {
            datafields.put("alertdate", new Date(this.orderdata.getInHandDate().getTime() - 5875200000L));
            datafields.put("alertdatedue", new Date(this.orderdata.getInHandDate().getTime() - 5356800000L));
          }
          return datafields;
        }
        

        difforderstatus = new ArrayList();
        difforderstatus.add("Waiting for Q.C. Report");
        difforderstatus.add("Waiting for Balance");
        difforderstatus.add("Partially Shipped");
        difforderstatus.add("Order Completed");
        difforderstatus.add("Order Wrap-Up Call");
        if (difforderstatus.contains(this.orderdata.getOrderStatus())) {
          if (hasair) {
            datafields.put("alertdate", new Date(this.orderdata.getInHandDate().getTime() - 518400000L));
            datafields.put("alertdatedue", new Date(this.orderdata.getInHandDate().getTime() - 345600000L));
          } else {
            datafields.put("alertdate", new Date(this.orderdata.getInHandDate().getTime() - 691200000L));
            datafields.put("alertdatedue", new Date(this.orderdata.getInHandDate().getTime() - 518400000L));
          }
          return datafields;
        }
        
        datafields.put("alertdate", null);
        datafields.put("alertdatedue", null);
        return datafields;
      }
      
      datafields.put("alertdate", null);
      datafields.put("alertdatedue", null);

    }
    else if (this.orderdata.getInHandDate() != null) {
      datafields.put("alertdate", new Date(this.orderdata.getInHandDate().getTime() - 1728000000L));
      datafields.put("alertdatedue", new Date(this.orderdata.getInHandDate().getTime() - 1296000000L));
    } else {
      datafields.put("alertdate", null);
      datafields.put("alertdatedue", null);
    }
    

    return datafields;
  }
  
  private void insertUpdateRow(String tablename, FastMap<Object> mykeys)
  {
    org.jooq.InsertSetStep<Record> steps = JOOQSQL.getInstance().create().insertInto(DSL.tableByName(new String[] { tablename }));
    InsertSetMoreStep<Record> steps2 = null;
    String[] keyset = (String[])mykeys.keySet().toArray(new String[0]);
    
    for (int i = 0; i < mykeys.size(); i++) {
      if (steps2 == null) {
        steps2 = steps.set(DSL.fieldByName(new String[] { keyset[i] }), mykeys.get(keyset[i]));
      } else {
        steps2 = steps2.set(DSL.fieldByName(new String[] { keyset[i] }), mykeys.get(keyset[i]));
      }
    }
    
    org.jooq.InsertOnDuplicateSetStep<Record> updatestuff = steps2.onDuplicateKeyUpdate();
    org.jooq.InsertOnDuplicateSetMoreStep<Record> steps3 = null;
    for (int i = 0; i < mykeys.size(); i++) {
      if (steps3 == null) {
        steps3 = updatestuff.set(DSL.fieldByName(new String[] { keyset[i] }), mykeys.get(keyset[i]));
      } else {
        steps3 = steps3.set(DSL.fieldByName(new String[] { keyset[i] }), mykeys.get(keyset[i]));
      }
    }
    
    steps3.execute();
  }
  
  private Integer insertRow(String tablename, FastMap<Object> mykeys) throws Exception
  {
    java.sql.Connection myconnection = JOOQSQL.getInstance().getDataSourceConnection();
    DSLContext create = org.jooq.util.mysql.MySQLDSL.using(myconnection, org.jooq.SQLDialect.MYSQL);
    
    org.jooq.InsertSetStep<Record> steps = create.insertInto(DSL.tableByName(new String[] { tablename }));
    InsertSetMoreStep<Record> steps2 = null;
    String[] keyset = (String[])mykeys.keySet().toArray(new String[0]);
    
    for (int i = 0; i < mykeys.size(); i++) {
      if (steps2 == null) {
        steps2 = steps.set(DSL.fieldByName(new String[] { keyset[i] }), mykeys.get(keyset[i]));
      } else {
        steps2 = steps2.set(DSL.fieldByName(new String[] { keyset[i] }), mykeys.get(keyset[i]));
      }
    }
    
    steps2.returning().fetchOne();
    int generatedkey = create.lastID().intValue();
    myconnection.close();
    
    return Integer.valueOf(generatedkey);
  }
}
