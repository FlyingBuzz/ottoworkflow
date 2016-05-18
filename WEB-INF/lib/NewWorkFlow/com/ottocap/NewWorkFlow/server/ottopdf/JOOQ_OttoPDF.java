package com.ottocap.NewWorkFlow.server.ottopdf;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData.OrderType;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataItem;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataVendorInformation;
import com.ottocap.NewWorkFlow.client.StyleInformationData.StyleType;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderData;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataAddress;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataCustomerInformation;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataItem;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataLogo;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataLogoColorway;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataLogoDecoration;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataSet;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataVendorInformation;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataVendors;
import com.ottocap.NewWorkFlow.server.JOOQSQL;
import com.ottocap.NewWorkFlow.server.JOOQ_OrderExpander;
import com.ottocap.NewWorkFlow.server.OrderGrouper;
import com.ottocap.NewWorkFlow.server.PriceCalculation.Helper.JOOQ_OverseasVendorFreeProductSampleCalculator;
import com.ottocap.NewWorkFlow.server.PriceCalculation.JOOQ_DomesticPriceCalculation;
import com.ottocap.NewWorkFlow.server.PriceCalculation.JOOQ_OverseasPriceCalculation;
import com.ottocap.NewWorkFlow.server.SharedData;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.SelectWhereStep;
import org.jooq.impl.DSL;

public class JOOQ_OttoPDF
{
  private Document document = new Document(PageSize.LETTER);
  private Font cellfont = FontFactory.getFont("C:\\WorkFlow\\NewWorkflowData\\images/pdf/arialn.ttf", "Identity-H", true, 9.0F);
  private Font cellfontbold = FontFactory.getFont("C:\\WorkFlow\\NewWorkflowData\\images/pdf/arialnb.ttf", "Identity-H", true, 9.0F);
  private Font cellhyperlink = FontFactory.getFont("C:\\WorkFlow\\NewWorkflowData\\images/pdf/arialnb.ttf", "Identity-H", true, 9.0F);
  private Font headerfont = FontFactory.getFont("C:\\WorkFlow\\NewWorkflowData\\images/pdf/arialnb.ttf", "Identity-H", true, 9.0F);
  private Font companyheaderfont = FontFactory.getFont("C:\\WorkFlow\\NewWorkflowData\\images/pdf/arialnb.ttf", "Identity-H", true, 11.0F);
  private Font companyaddressheaderfont = FontFactory.getFont("C:\\WorkFlow\\NewWorkflowData\\images/pdf/arialn.ttf", "Identity-H", true, 7.0F);
  private Font mainheaderfont = FontFactory.getFont("C:\\WorkFlow\\NewWorkflowData\\images/pdf/arialnb.ttf", "Identity-H", true, 12.0F);
  private Font mainheaderfontsmall = FontFactory.getFont("C:\\WorkFlow\\NewWorkflowData\\images/pdf/arialn.ttf", "Identity-H", true, 8.0F);
  private Font mainheaderfontordernumber = FontFactory.getFont("C:\\WorkFlow\\NewWorkflowData\\images/pdf/arialnb.ttf", "Identity-H", true, 9.0F);
  
  private Font artworkfont = FontFactory.getFont("C:\\WorkFlow\\NewWorkflowData\\images/pdf/arialn.ttf", "Identity-H", true, 9.0F);
  private Font artworkfontbold = FontFactory.getFont("C:\\WorkFlow\\NewWorkflowData\\images/pdf/arialnb.ttf", "Identity-H", true, 9.0F);
  
  static BaseColor DomesticColor = new BaseColor(32, 179, 75);
  static BaseColor OverseasColor = new BaseColor(12, 77, 162);
  static BaseColor OrderColor = new BaseColor(215, 25, 33);
  private BaseColor headercolor = new BaseColor(255, 255, 255);
  private PDFType ordertype;
  
  public static enum PDFType { QUOTATION,  APPROVAL,  PRODUCTIONAPPROVAL,  GENERIC,  ARTWORK,  DIGITIZING,  EMBSAMPLE,  HEATSAMPLE,  SCREENSAMPLE,  DIRECTTOGARMENTSAMPLE,  EMBORDER,  HEATORDER,  SCREENORDER,  DIRECTTOGARMENTORDER,  OVERSEASQUOTATION,  OVERSEASAPPROVAL,  OVERSEASPRODUCTIONAPPROVAL,  OVERSEASGENERIC,  OVERSEASARTWORK,  OVERSEASDIGITIZING,  OVERSEASSAMPLE,  OVERSEASPURCHASEORDER,  PATCHORDER,  PATCHSAMPLE;
  }
  

  private int overseasvendornumber = 0;
  private HttpServletResponse response;
  private HttpServletRequest request;
  private ExtendOrderData therealorder;
  boolean isIE = false;
  
  private JOOQ_DomesticPriceCalculation domesticpricecalc;
  private JOOQ_OverseasPriceCalculation overseaspricecalc;
  private JOOQ_PDFFileHandler sameimages = new JOOQ_PDFFileHandler();
  private JOOQ_OverseasVendorFreeProductSampleCalculator overseassamplevendorcalc = null;
  
  private String term_quotation = "This quote is an estimate and will only be valid for 30 days.\nAuthorization signature & a minimum of 50% deposit required to start production. 50% remaining balance will be due before shipping. All terms subject to credit approval.\n7 to 15 working days for production after final order approval & deposit is received.\nDelivery date may vary depending on quantity and design.\nSpecial shipping may be required to meet in-hands date.\nOtto International, Inc. will not be responsible for any error and/or omission once order is approved.\nSample recommended, if waived, Otto International, Inc. will not be responsible for poor quality digitizing provided by customer.\nAdditional charges will apply for the shipping of all samples.\nIndustry damage allowance is 2% of order, please order extra pieces as needed.\nNo cancellation allowed once order is approved and processed.";
  private String term_approval = "This order approval is final and will be valid for 30 days.\nAuthorization signature & a minimum of 50% deposit required to start production. 50% remaining balance will be due before shipping. All terms subject to credit approval.\n7 to 15 Working days for production after final order approval & deposit is received.\nDelivery date may vary depending on quantity and design.\nSpecial shipping may be required to meet in-hands date.\nOtto International, Inc. will not be responsible for any error and/or omission once order is approved.\nSample recommended, if waived, Otto International, Inc. will not be responsible for poor quality digitizing provided by customer.\nAdditional charges will apply for the shipping of all samples.\nIndustry damage allowance is 2% of order, please order extra pieces as needed.\nNo cancellation allowed once order is approved and processed.";
  private String term_overseas_quotation = "This quote is an estimate and will only be valid for 10 days.\nAuthorization signature & a minimum of 50% deposit required to start production. 50% remaining balance will be due before shipping. All terms subject to credit approval.\n60 to 75 days for production after final order approval & deposit is received.\nDelivery date may vary depending on quantity and design.\nSpecial shipping may be required to meet in-hands date.\nOtto International, Inc. will not be responsible for any error and/or omission once order is approved.\nSample recommended, if waived, Otto International, Inc. will not be responsible for poor quality digitizing provided by customer.\nAdditional charges will apply for the shipping of all samples.\nIndustry damage allowance is 2% of order, please order extra pieces as needed.\nNo cancellation allowed once order is approved and processed.";
  private String term_overseas_approval = "This order approval is final and will be valid for 10 days.\nAuthorization signature & a minimum of 50% deposit required to start production. 50% remaining balance will be due before shipping. All terms subject to credit approval.\n60 to 75 days for production after final order approval & deposit is received.\nDelivery date may vary depending on quantity and design.\nSpecial shipping may be required to meet in-hands date.\nOtto International, Inc. will not be responsible for any error and/or omission once order is approved.\nSample recommended, if waived, Otto International, Inc. will not be responsible for poor quality digitizing provided by customer.\nAdditional charges will apply for the shipping of all samples.\nIndustry damage allowance is 2% of order, please order extra pieces as needed.\nNo cancellation allowed once order is approved and processed.";
  
  public JOOQ_OttoPDF(HttpServletRequest request, HttpServletResponse response) throws Exception
  {
    if (SharedData.isFaya.booleanValue()) {
      this.term_quotation = "This quote is an estimate and will only be valid for 30 days.\nAuthorization signature & 50% deposit required to start production. 50% remaining balance will be due before shipping.\n7 to 15 working days for production after final order approval & deposit is received.\nDelivery date may vary depending on quantity and design.\nSpecial shipping may be required to meet in-hands date.\nFaya Corporation will not be responsible for any error and/or omission once order is approved.\nSample recommended, if waived, Faya Corporation will not be responsible for poor quality digitizing provided by customer.\nIndustry damage allowance is 2% of order, please order extra pieces as needed.\nNo cancellation allowed once order is approved and processed.";
      this.term_approval = "This order approval is final and will be valid for 30 days.\nAuthorization signature & 50% deposit required to start production. 50% remaining balance will be due before shipping.\n7 to 15 Working days for production after final order approval & deposit is received.\nDelivery date may vary depending on quantity and design.\nSpecial shipping may be required to meet in-hands date.\nFaya Corporation will not be responsible for any error and/or omission once order is approved.\nSample recommended, if waived, Faya Corporation will not be responsible for poor quality digitizing provided by customer.\nIndustry damage allowance is 2% of order, please order extra pieces as needed.\nNo cancellation allowed once order is approved and processed.";
      this.term_overseas_quotation = "This quote is an estimate and will only be valid for 10 days.\nAuthorization signature & 50% deposit required to start production. 50% remaining balance will be due before shipping.\n60 to 75 days for production after final order approval & deposit is received.\nDelivery date may vary depending on quantity and design.\nSpecial shipping may be required to meet in-hands date.\nFaya Corporation will not be responsible for any error and/or omission once order is approved.\nSample recommended, if waived, Faya Corporation will not be responsible for poor quality digitizing provided by customer.\nIndustry damage allowance is 2% of order, please order extra pieces as needed.\nNo cancellation allowed once order is approved and processed.";
      this.term_overseas_approval = "This order approval is final and will be valid for 10 days.\nAuthorization signature & 50% deposit required to start production. 50% remaining balance will be due before shipping.\n60 to 75 days for production after final order approval & deposit is received.\nDelivery date may vary depending on quantity and design.\nSpecial shipping may be required to meet in-hands date.\nFaya Corporation will not be responsible for any error and/or omission once order is approved.\nSample recommended, if waived, Faya Corporation will not be responsible for poor quality digitizing provided by customer.\nIndustry damage allowance is 2% of order, please order extra pieces as needed.\nNo cancellation allowed once order is approved and processed.";
    }
    
    this.request = request;
    this.response = response;
    JOOQ_OrderExpander theorderexpander = new JOOQ_OrderExpander(Integer.valueOf(request.getParameter("hiddenkey")).intValue(), request.getSession());
    this.therealorder = theorderexpander.getExpandedOrderData();
    
    this.domesticpricecalc = new JOOQ_DomesticPriceCalculation(this.therealorder);
    this.overseaspricecalc = new JOOQ_OverseasPriceCalculation(this.therealorder);
    this.cellhyperlink.setColor(BaseColor.BLUE);
    this.headerfont.setColor(BaseColor.WHITE);
    this.mainheaderfont.setColor(BaseColor.WHITE);
    this.mainheaderfontsmall.setColor(BaseColor.WHITE);
    this.mainheaderfontordernumber.setColor(BaseColor.WHITE);
    
    this.artworkfontbold.setColor(BaseColor.MAGENTA);
    
    this.isIE = ((request.getHeader("User-Agent") != null) && (request.getHeader("User-Agent").indexOf("MSIE") != -1));
    
    if (this.isIE) {
      response.setContentType("application/pdf");
    } else {
      response.setContentType("application/download");
    }
    this.document.setMargins(20.0F, 20.0F, 20.0F, 20.0F);
  }
  
  private void setHeaderColor(BaseColor color)
  {
    this.headercolor = color;
  }
  
  private BaseColor getHeaderColor() {
    return this.headercolor;
  }
  
  private void setOrderType(PDFType theform) {
    this.ordertype = theform;
  }
  
  private PDFType getOrderType() {
    return this.ordertype;
  }
  
  public void overseasgenericapproval() throws Exception {
    OrderGrouper theordergrouper = new OrderGrouper(this.therealorder, false);
    ExtendOrderData thegroupedorder = theordergrouper.getGroupedOrderData();
    if (!this.isIE) {
      this.response.setHeader("Content-disposition", "attachment; filename=\"" + new SimpleDateFormat("MM.dd.yy-hh.mm.a-").format(new Date()) + "OverseasGenericApproval.pdf\"");
    }
    this.headerfont.setColor(BaseColor.BLACK);
    setOrderType(PDFType.OVERSEASGENERIC);
    PdfWriter writer = PdfWriter.getInstance(this.document, this.response.getOutputStream());
    PageNumbers mypage = new PageNumbers();
    mypage.setFontColor(BaseColor.BLACK);
    writer.setPageEvent(mypage);
    this.document.open();
    productpreviewPage(thegroupedorder);
    this.document.close();
  }
  
  public void genericapproval() throws Exception {
    OrderGrouper theordergrouper = new OrderGrouper(this.therealorder, false);
    ExtendOrderData thegroupedorder = theordergrouper.getGroupedOrderData();
    
    if (!this.isIE) {
      this.response.setHeader("Content-disposition", "attachment; filename=\"" + new SimpleDateFormat("MM.dd.yy-hh.mm.a-").format(new Date()) + "DomesticGenericApproval.pdf\"");
    }
    this.headerfont.setColor(BaseColor.BLACK);
    setOrderType(PDFType.GENERIC);
    PdfWriter writer = PdfWriter.getInstance(this.document, this.response.getOutputStream());
    PageNumbers mypage = new PageNumbers();
    mypage.setFontColor(BaseColor.BLACK);
    writer.setPageEvent(mypage);
    this.document.open();
    productpreviewPage(thegroupedorder);
    this.document.close();
  }
  
  public void directtogarmentorder() throws Exception {
    this.domesticpricecalc.calculateOrderPrice(JOOQ_DomesticPriceCalculation.DirectToGarment);
    
    if (!this.isIE) {
      this.response.setHeader("Content-disposition", "attachment; filename=\"" + new SimpleDateFormat("MM.dd.yy-hh.mm.a-").format(new Date()) + "DomesticWorkOrderD" + this.therealorder.getVendorInformation().getDirectToGarmentVendor().getWorkOrderNumber() + ".pdf\"");
    }
    setHeaderColor(OrderColor);
    setOrderType(PDFType.DIRECTTOGARMENTORDER);
    PdfWriter writer = PdfWriter.getInstance(this.document, this.response.getOutputStream());
    writer.setPageEvent(new PageNumbers());
    this.document.open();
    
    productpreviewPage(this.therealorder);
    
    this.document.close();
  }
  
  public void heatorder() throws Exception {
    this.domesticpricecalc.calculateOrderPrice(JOOQ_DomesticPriceCalculation.HeatTransfer);
    
    if (!this.isIE) {
      this.response.setHeader("Content-disposition", "attachment; filename=\"" + new SimpleDateFormat("MM.dd.yy-hh.mm.a-").format(new Date()) + "DomesticWorkOrderH" + this.therealorder.getVendorInformation().getHeatTransferVendor().getWorkOrderNumber() + ".pdf\"");
    }
    setHeaderColor(OrderColor);
    setOrderType(PDFType.HEATORDER);
    PdfWriter writer = PdfWriter.getInstance(this.document, this.response.getOutputStream());
    writer.setPageEvent(new PageNumbers());
    this.document.open();
    
    productpreviewPage(this.therealorder);
    
    this.document.close();
  }
  
  public void screenorder() throws Exception {
    this.domesticpricecalc.calculateOrderPrice(JOOQ_DomesticPriceCalculation.ScreenPrint);
    
    if (!this.isIE) {
      this.response.setHeader("Content-disposition", "attachment; filename=\"" + new SimpleDateFormat("MM.dd.yy-hh.mm.a-").format(new Date()) + "DomesticWorkOrderS" + this.therealorder.getVendorInformation().getScreenPrintVendor().getWorkOrderNumber() + ".pdf\"");
    }
    setHeaderColor(OrderColor);
    setOrderType(PDFType.SCREENORDER);
    PdfWriter writer = PdfWriter.getInstance(this.document, this.response.getOutputStream());
    writer.setPageEvent(new PageNumbers());
    this.document.open();
    
    productpreviewPage(this.therealorder);
    
    this.document.close();
  }
  
  public void patchorder() throws Exception {
    this.domesticpricecalc.calculateOrderPrice(JOOQ_DomesticPriceCalculation.Patch);
    
    if (!this.isIE) {
      this.response.setHeader("Content-disposition", "attachment; filename=\"" + new SimpleDateFormat("MM.dd.yy-hh.mm.a-").format(new Date()) + "DomesticWorkOrderP" + this.therealorder.getVendorInformation().getPatchVendor().getWorkOrderNumber() + ".pdf\"");
    }
    setHeaderColor(OrderColor);
    setOrderType(PDFType.PATCHORDER);
    PdfWriter writer = PdfWriter.getInstance(this.document, this.response.getOutputStream());
    writer.setPageEvent(new PageNumbers());
    this.document.open();
    
    productpreviewPage(this.therealorder);
    
    this.document.close();
  }
  
  public void emborder() throws Exception {
    this.domesticpricecalc.calculateOrderPrice(JOOQ_DomesticPriceCalculation.Embroidery);
    
    if (!this.isIE) {
      this.response.setHeader("Content-disposition", "attachment; filename=\"" + new SimpleDateFormat("MM.dd.yy-hh.mm.a-").format(new Date()) + "DomesticWorkOrderE" + this.therealorder.getVendorInformation().getEmbroideryVendor().getWorkOrderNumber() + ".pdf\"");
    }
    setHeaderColor(OrderColor);
    setOrderType(PDFType.EMBORDER);
    PdfWriter writer = PdfWriter.getInstance(this.document, this.response.getOutputStream());
    writer.setPageEvent(new PageNumbers());
    this.document.open();
    
    productpreviewPage(this.therealorder);
    
    this.document.close();
  }
  
  public void directtogarmentsampleorder() throws Exception {
    this.domesticpricecalc.calculateSamplePrice(JOOQ_DomesticPriceCalculation.DirectToGarment);
    
    if (!this.isIE) {
      this.response.setHeader("Content-disposition", "attachment; filename=\"" + new SimpleDateFormat("MM.dd.yy-hh.mm.a-").format(new Date()) + "DomesticSampleWorkOrderD" + this.therealorder.getVendorInformation().getDirectToGarmentVendor().getWorkOrderNumber() + ".pdf\"");
    }
    setHeaderColor(OrderColor);
    setOrderType(PDFType.DIRECTTOGARMENTSAMPLE);
    PdfWriter writer = PdfWriter.getInstance(this.document, this.response.getOutputStream());
    writer.setPageEvent(new PageNumbers());
    this.document.open();
    
    productpreviewPage(this.therealorder);
    
    this.document.close();
  }
  
  public void heatsampleorder() throws Exception {
    this.domesticpricecalc.calculateSamplePrice(JOOQ_DomesticPriceCalculation.HeatTransfer);
    
    if (!this.isIE) {
      this.response.setHeader("Content-disposition", "attachment; filename=\"" + new SimpleDateFormat("MM.dd.yy-hh.mm.a-").format(new Date()) + "DomesticSampleWorkOrderH" + this.therealorder.getVendorInformation().getHeatTransferVendor().getWorkOrderNumber() + ".pdf\"");
    }
    setHeaderColor(OrderColor);
    setOrderType(PDFType.HEATSAMPLE);
    PdfWriter writer = PdfWriter.getInstance(this.document, this.response.getOutputStream());
    writer.setPageEvent(new PageNumbers());
    this.document.open();
    
    productpreviewPage(this.therealorder);
    
    this.document.close();
  }
  
  public void screensampleorder() throws Exception {
    this.domesticpricecalc.calculateSamplePrice(JOOQ_DomesticPriceCalculation.ScreenPrint);
    
    if (!this.isIE) {
      this.response.setHeader("Content-disposition", "attachment; filename=\"" + new SimpleDateFormat("MM.dd.yy-hh.mm.a-").format(new Date()) + "DomesticSampleWorkOrderS" + this.therealorder.getVendorInformation().getScreenPrintVendor().getWorkOrderNumber() + ".pdf\"");
    }
    setHeaderColor(OrderColor);
    setOrderType(PDFType.SCREENSAMPLE);
    PdfWriter writer = PdfWriter.getInstance(this.document, this.response.getOutputStream());
    writer.setPageEvent(new PageNumbers());
    this.document.open();
    
    productpreviewPage(this.therealorder);
    
    this.document.close();
  }
  
  public void patchsampleorder() throws Exception {
    this.domesticpricecalc.calculateSamplePrice(JOOQ_DomesticPriceCalculation.Patch);
    
    if (!this.isIE) {
      this.response.setHeader("Content-disposition", "attachment; filename=\"" + new SimpleDateFormat("MM.dd.yy-hh.mm.a-").format(new Date()) + "DomesticSampleWorkOrderP" + this.therealorder.getVendorInformation().getPatchVendor().getWorkOrderNumber() + ".pdf\"");
    }
    setHeaderColor(OrderColor);
    setOrderType(PDFType.PATCHSAMPLE);
    PdfWriter writer = PdfWriter.getInstance(this.document, this.response.getOutputStream());
    writer.setPageEvent(new PageNumbers());
    this.document.open();
    
    productpreviewPage(this.therealorder);
    
    this.document.close();
  }
  
  public void embsampleorder() throws Exception {
    this.domesticpricecalc.calculateSamplePrice(JOOQ_DomesticPriceCalculation.Embroidery);
    
    if (!this.isIE) {
      this.response.setHeader("Content-disposition", "attachment; filename=\"" + new SimpleDateFormat("MM.dd.yy-hh.mm.a-").format(new Date()) + "DomesticSampleWorkOrderE" + this.therealorder.getVendorInformation().getEmbroideryVendor().getWorkOrderNumber() + ".pdf\"");
    }
    setHeaderColor(OrderColor);
    setOrderType(PDFType.EMBSAMPLE);
    PdfWriter writer = PdfWriter.getInstance(this.document, this.response.getOutputStream());
    writer.setPageEvent(new PageNumbers());
    this.document.open();
    
    productpreviewPage(this.therealorder);
    
    this.document.close();
  }
  
  private void setOverseasVendorNumber(int vendornumber) {
    this.overseasvendornumber = vendornumber;
  }
  
  private int getOverseasVendorNumber() {
    return this.overseasvendornumber;
  }
  
  public void overseaspurchaseorder(int overseasvendornumber) throws Exception {
    this.overseaspricecalc.calculateOrderPrice(true);
    
    if (!this.isIE) {
      this.response.setHeader("Content-disposition", "attachment; filename=\"" + new SimpleDateFormat("MM.dd.yy-hh.mm.a-").format(new Date()) + "OverseasPurchaseOrder" + ((ExtendOrderDataVendorInformation)this.therealorder.getVendorInformation().getOverseasVendor().get(String.valueOf(overseasvendornumber))).getVendorName() + ((OrderDataVendorInformation)this.therealorder.getVendorInformation().getOverseasVendor().get(String.valueOf(overseasvendornumber))).getWorkOrderNumber() + ".pdf\"");
    }
    setOverseasVendorNumber(overseasvendornumber);
    setHeaderColor(OrderColor);
    setOrderType(PDFType.OVERSEASPURCHASEORDER);
    PdfWriter writer = PdfWriter.getInstance(this.document, this.response.getOutputStream());
    writer.setPageEvent(new PageNumbers());
    this.document.open();
    
    productpreviewPage(this.therealorder);
    
    this.document.close();
  }
  
  public void overseassampleorder(int overseasvendornumber) throws Exception {
    this.overseassamplevendorcalc = new JOOQ_OverseasVendorFreeProductSampleCalculator(this.therealorder);
    this.overseaspricecalc.calculateSamplePrice(this.overseassamplevendorcalc);
    
    if (!this.isIE) {
      this.response.setHeader("Content-disposition", "attachment; filename=\"" + new SimpleDateFormat("MM.dd.yy-hh.mm.a-").format(new Date()) + "OverseasSampleOrder" + ((ExtendOrderDataVendorInformation)this.therealorder.getVendorInformation().getOverseasVendor().get(String.valueOf(overseasvendornumber))).getVendorName() + this.therealorder.getOrderNumber() + ".pdf\"");
    }
    setOverseasVendorNumber(overseasvendornumber);
    setHeaderColor(OrderColor);
    setOrderType(PDFType.OVERSEASSAMPLE);
    PdfWriter writer = PdfWriter.getInstance(this.document, this.response.getOutputStream());
    writer.setPageEvent(new PageNumbers());
    this.document.open();
    
    productpreviewPage(this.therealorder);
    
    this.document.close();
  }
  
  public void overseasdigitizingorder(int overseasvendornumber) throws Exception {
    if (!this.isIE) {
      this.response.setHeader("Content-disposition", "attachment; filename=\"" + new SimpleDateFormat("MM.dd.yy-hh.mm.a-").format(new Date()) + "OverseasDigitizingWorkOrder" + ((ExtendOrderDataVendorInformation)this.therealorder.getVendorInformation().getOverseasVendor().get(String.valueOf(overseasvendornumber))).getVendorName() + ((OrderDataVendorInformation)this.therealorder.getVendorInformation().getOverseasVendor().get(String.valueOf(overseasvendornumber))).getWorkOrderNumber() + ".pdf\"");
    }
    setOverseasVendorNumber(overseasvendornumber);
    setHeaderColor(OrderColor);
    setOrderType(PDFType.OVERSEASDIGITIZING);
    PdfWriter writer = PdfWriter.getInstance(this.document, this.response.getOutputStream());
    writer.setPageEvent(new PageNumbers());
    this.document.open();
    
    productpreviewPage(this.therealorder);
    
    this.document.close();
  }
  
  public void digitizingorder() throws Exception {
    this.domesticpricecalc.calculateDigitizingPrice();
    
    if (!this.isIE) {
      this.response.setHeader("Content-disposition", "attachment; filename=\"" + new SimpleDateFormat("MM.dd.yy-hh.mm.a-").format(new Date()) + "DomesticDigitizingWorkOrder" + this.therealorder.getVendorInformation().getDigitizerVendor().getWorkOrderNumber() + ".pdf\"");
    }
    setHeaderColor(OrderColor);
    setOrderType(PDFType.DIGITIZING);
    PdfWriter writer = PdfWriter.getInstance(this.document, this.response.getOutputStream());
    writer.setPageEvent(new PageNumbers());
    this.document.open();
    
    productpreviewPage(this.therealorder);
    
    this.document.close();
  }
  
  public void overseasquotation() throws Exception {
    this.overseaspricecalc.calculateOrderPrice(true);
    OrderGrouper theordergrouper = new OrderGrouper(this.therealorder);
    ExtendOrderData thegroupedorder = theordergrouper.getGroupedOrderData();
    
    if (!this.isIE) {
      this.response.setHeader("Content-disposition", "attachment; filename=\"" + new SimpleDateFormat("MM.dd.yy-hh.mm.a-").format(new Date()) + "OverseasQuotation" + this.therealorder.getHiddenKey() + ".pdf\"");
    }
    setHeaderColor(OverseasColor);
    setOrderType(PDFType.OVERSEASQUOTATION);
    PdfWriter writer = PdfWriter.getInstance(this.document, this.response.getOutputStream());
    writer.setPageEvent(new PageNumbers());
    this.document.open();
    
    PdfPTable mytable = new PdfPTable(1);
    mytable.setWidthPercentage(100.0F);
    mytable.setSplitLate(false);
    
    mytable.addCell(paddedTableCell(mainheaderPanel(thegroupedorder), 0));
    mytable.setHeaderRows(1);
    mytable.addCell(paddedTableCell(customeradditionalPanel(thegroupedorder), 0));
    mytable.addCell(paddedTableCell(billshipPanel(thegroupedorder), 0));
    mytable.addCell(paddedTableCell(itemsOverseasSummaryPage(thegroupedorder), 0));
    
    mytable.setTotalWidth(this.document.getPageSize().getWidth() - this.document.leftMargin() - this.document.rightMargin());
    
    float currentpageheight = mytable.getTotalHeight();
    mytable.addCell(paddedTableCell(pricingcommentstermsPanel(thegroupedorder, 1.0F), 0));
    float pricingheight = mytable.getTotalHeight() - currentpageheight;
    mytable.deleteLastRow();
    
    boolean morethanonepage = false;
    while (currentpageheight > this.document.getPageSize().getHeight() - this.document.topMargin() - this.document.bottomMargin()) {
      morethanonepage = true;
      currentpageheight = currentpageheight - (this.document.getPageSize().getHeight() - this.document.topMargin() - this.document.bottomMargin()) + mytable.getHeaderHeight() + itemsOverseasSummaryPage(thegroupedorder).getHeaderHeight();
    }
    
    if (morethanonepage) {
      if (currentpageheight + pricingheight <= this.document.getPageSize().getHeight() - this.document.topMargin() - this.document.bottomMargin()) {
        currentpageheight = this.document.getPageSize().getHeight() - this.document.topMargin() - this.document.bottomMargin() - pricingheight;
      }
      mytable.deleteLastRow();
      PdfPCell itemsummerycell = paddedTableCell(itemsOverseasSummaryPage(thegroupedorder), 0);
      itemsummerycell.setMinimumHeight(currentpageheight - mytable.getHeaderHeight());
      mytable.addCell(itemsummerycell);
    }
    if (pricingheight <= this.document.getPageSize().getHeight() - this.document.topMargin() - this.document.bottomMargin() - mytable.getHeaderHeight()) {
      if (currentpageheight + pricingheight <= this.document.getPageSize().getHeight() - this.document.topMargin() - this.document.bottomMargin())
      {
        PdfPCell pricingcell = paddedTableCell(pricingcommentstermsPanel(thegroupedorder, this.document.getPageSize().getHeight() - this.document.topMargin() - this.document.bottomMargin() - currentpageheight), 0);
        pricingcell.setMinimumHeight(this.document.getPageSize().getHeight() - this.document.topMargin() - this.document.bottomMargin() - currentpageheight);
        mytable.addCell(pricingcell);
      }
      else
      {
        if (morethanonepage) {
          mytable.deleteLastRow();
          PdfPCell itemsummerycell = paddedTableCell(itemsOverseasSummaryPage(thegroupedorder), 0);
          itemsummerycell.setMinimumHeight(this.document.getPageSize().getHeight() - this.document.topMargin() - this.document.bottomMargin() - mytable.getHeaderHeight());
          mytable.addCell(itemsummerycell);
        } else {
          mytable.deleteLastRow();
          PdfPCell itemsummerycell = paddedTableCell(itemsOverseasSummaryPage(thegroupedorder), 0);
          itemsummerycell.setMinimumHeight(this.document.getPageSize().getHeight() - this.document.topMargin() - this.document.bottomMargin() - mytable.getTotalHeight());
          mytable.addCell(itemsummerycell);
        }
        PdfPCell pricingcell = paddedTableCell(pricingcommentstermsPanel(thegroupedorder, this.document.getPageSize().getHeight() - this.document.topMargin() - this.document.bottomMargin() - mytable.getHeaderHeight()), 0);
        pricingcell.setMinimumHeight(this.document.getPageSize().getHeight() - this.document.topMargin() - this.document.bottomMargin() - mytable.getHeaderHeight());
        mytable.addCell(pricingcell);
      }
    }
    else {
      mytable.deleteLastRow();
      mytable.addCell(paddedTableCell(itemsOverseasSummaryPage(thegroupedorder), 0));
      float currentheight = mytable.getTotalHeight();
      mytable.addCell(paddedTableCell(commenttermsPanel(thegroupedorder), 0));
      currentheight = mytable.getTotalHeight() - currentheight;
      PdfPCell pricingcellpanel = paddedTableCell(pricingPanel(Boolean.valueOf(false)), 0);
      pricingcellpanel.setMinimumHeight(this.document.getPageSize().getHeight() - this.document.topMargin() - this.document.bottomMargin() - currentheight - mytable.getHeaderHeight());
      mytable.deleteLastRow();
      mytable.addCell(pricingcellpanel);
      mytable.addCell(paddedTableCell(commenttermsPanel(thegroupedorder), 0));
    }
    
    this.document.add(mytable);
    
    productpreviewPage(thegroupedorder);
    
    this.document.close();
  }
  
  public void orderquotation() throws Exception {
    this.domesticpricecalc.calculateCustomerPrice(true);
    OrderGrouper theordergrouper = new OrderGrouper(this.therealorder);
    ExtendOrderData thegroupedorder = theordergrouper.getGroupedOrderData();
    
    if (!this.isIE) {
      this.response.setHeader("Content-disposition", "attachment; filename=\"" + new SimpleDateFormat("MM.dd.yy-hh.mm.a-").format(new Date()) + "DomesticQuotation" + this.therealorder.getHiddenKey() + ".pdf\"");
    }
    setHeaderColor(DomesticColor);
    setOrderType(PDFType.QUOTATION);
    PdfWriter writer = PdfWriter.getInstance(this.document, this.response.getOutputStream());
    writer.setPageEvent(new PageNumbers());
    this.document.open();
    
    PdfPTable mytable = new PdfPTable(1);
    mytable.setWidthPercentage(100.0F);
    mytable.setSplitLate(false);
    
    mytable.addCell(paddedTableCell(mainheaderPanel(thegroupedorder), 0));
    mytable.setHeaderRows(1);
    mytable.addCell(paddedTableCell(customeradditionalPanel(thegroupedorder), 0));
    mytable.addCell(paddedTableCell(billshipPanel(thegroupedorder), 0));
    mytable.addCell(paddedTableCell(itemsSummaryPage(thegroupedorder), 0));
    
    mytable.setTotalWidth(this.document.getPageSize().getWidth() - this.document.leftMargin() - this.document.rightMargin());
    
    float currentpageheight = mytable.getTotalHeight();
    mytable.addCell(paddedTableCell(pricingcommentstermsPanel(thegroupedorder, 1.0F), 0));
    float pricingheight = mytable.getTotalHeight() - currentpageheight;
    mytable.deleteLastRow();
    
    boolean morethanonepage = false;
    while (currentpageheight > this.document.getPageSize().getHeight() - this.document.topMargin() - this.document.bottomMargin()) {
      morethanonepage = true;
      currentpageheight = currentpageheight - (this.document.getPageSize().getHeight() - this.document.topMargin() - this.document.bottomMargin()) + mytable.getHeaderHeight() + itemsSummaryPage(thegroupedorder).getHeaderHeight();
    }
    
    if (morethanonepage) {
      if (currentpageheight + pricingheight <= this.document.getPageSize().getHeight() - this.document.topMargin() - this.document.bottomMargin()) {
        currentpageheight = this.document.getPageSize().getHeight() - this.document.topMargin() - this.document.bottomMargin() - pricingheight;
      }
      mytable.deleteLastRow();
      PdfPCell itemsummerycell = paddedTableCell(itemsSummaryPage(thegroupedorder), 0);
      itemsummerycell.setMinimumHeight(currentpageheight - mytable.getHeaderHeight());
      mytable.addCell(itemsummerycell);
    }
    if (pricingheight <= this.document.getPageSize().getHeight() - this.document.topMargin() - this.document.bottomMargin() - mytable.getHeaderHeight()) {
      if (currentpageheight + pricingheight <= this.document.getPageSize().getHeight() - this.document.topMargin() - this.document.bottomMargin())
      {
        PdfPCell pricingcell = paddedTableCell(pricingcommentstermsPanel(thegroupedorder, this.document.getPageSize().getHeight() - this.document.topMargin() - this.document.bottomMargin() - currentpageheight), 0);
        pricingcell.setMinimumHeight(this.document.getPageSize().getHeight() - this.document.topMargin() - this.document.bottomMargin() - currentpageheight);
        mytable.addCell(pricingcell);
      }
      else
      {
        if (morethanonepage) {
          mytable.deleteLastRow();
          PdfPCell itemsummerycell = paddedTableCell(itemsSummaryPage(thegroupedorder), 0);
          itemsummerycell.setMinimumHeight(this.document.getPageSize().getHeight() - this.document.topMargin() - this.document.bottomMargin() - mytable.getHeaderHeight());
          mytable.addCell(itemsummerycell);
        } else {
          mytable.deleteLastRow();
          PdfPCell itemsummerycell = paddedTableCell(itemsSummaryPage(thegroupedorder), 0);
          itemsummerycell.setMinimumHeight(this.document.getPageSize().getHeight() - this.document.topMargin() - this.document.bottomMargin() - mytable.getTotalHeight());
          mytable.addCell(itemsummerycell);
        }
        PdfPCell pricingcell = paddedTableCell(pricingcommentstermsPanel(thegroupedorder, this.document.getPageSize().getHeight() - this.document.topMargin() - this.document.bottomMargin() - mytable.getHeaderHeight()), 0);
        pricingcell.setMinimumHeight(this.document.getPageSize().getHeight() - this.document.topMargin() - this.document.bottomMargin() - mytable.getHeaderHeight());
        mytable.addCell(pricingcell);
      }
    }
    else {
      mytable.deleteLastRow();
      mytable.addCell(paddedTableCell(itemsSummaryPage(thegroupedorder), 0));
      float currentheight = mytable.getTotalHeight();
      mytable.addCell(paddedTableCell(commenttermsPanel(thegroupedorder), 0));
      currentheight = mytable.getTotalHeight() - currentheight;
      PdfPCell pricingcellpanel = paddedTableCell(pricingPanel(Boolean.valueOf(false)), 0);
      pricingcellpanel.setMinimumHeight(this.document.getPageSize().getHeight() - this.document.topMargin() - this.document.bottomMargin() - currentheight - mytable.getHeaderHeight());
      mytable.deleteLastRow();
      mytable.addCell(pricingcellpanel);
      mytable.addCell(paddedTableCell(commenttermsPanel(thegroupedorder), 0));
    }
    
    this.document.add(mytable);
    
    productpreviewPage(thegroupedorder);
    
    this.document.close();
  }
  
  public void overseasartworkrequest() throws Exception {
    if (!this.isIE) {
      if (this.therealorder.getOrderNumber().equals("")) {
        this.response.setHeader("Content-disposition", "attachment; filename=\"" + new SimpleDateFormat("MM.dd.yy-hh.mm.a-").format(new Date()) + "OverseasArtworkRequest" + this.therealorder.getHiddenKey() + ".pdf\"");
      } else {
        this.response.setHeader("Content-disposition", "attachment; filename=\"" + new SimpleDateFormat("MM.dd.yy-hh.mm.a-").format(new Date()) + "OverseasArtworkRequest" + this.therealorder.getOrderNumber() + ".pdf\"");
      }
    }
    setHeaderColor(OverseasColor);
    setOrderType(PDFType.OVERSEASARTWORK);
    PdfWriter writer = PdfWriter.getInstance(this.document, this.response.getOutputStream());
    writer.setPageEvent(new PageNumbers());
    this.document.open();
    
    PdfPTable mytable = new PdfPTable(1);
    mytable.setWidthPercentage(100.0F);
    mytable.setSplitLate(false);
    
    mytable.addCell(paddedTableCell(mainheaderPanel(this.therealorder), 0));
    mytable.setHeaderRows(1);
    mytable.addCell(paddedTableCell(customeradditionalPanel(this.therealorder), 0));
    mytable.addCell(paddedTableCell(itemsArtworkSummaryPage(this.therealorder), 0));
    
    this.document.add(mytable);
    
    this.document.close();
  }
  
  public void artworkrequest() throws Exception {
    if (!this.isIE) {
      if (this.therealorder.getOrderNumber().equals("")) {
        this.response.setHeader("Content-disposition", "attachment; filename=\"" + new SimpleDateFormat("MM.dd.yy-hh.mm.a-").format(new Date()) + "DomesticArtworkRequest" + this.therealorder.getHiddenKey() + ".pdf\"");
      } else {
        this.response.setHeader("Content-disposition", "attachment; filename=\"" + new SimpleDateFormat("MM.dd.yy-hh.mm.a-").format(new Date()) + "DomesticArtworkRequest" + this.therealorder.getOrderNumber() + ".pdf\"");
      }
    }
    setHeaderColor(DomesticColor);
    setOrderType(PDFType.ARTWORK);
    PdfWriter writer = PdfWriter.getInstance(this.document, this.response.getOutputStream());
    writer.setPageEvent(new PageNumbers());
    this.document.open();
    
    PdfPTable mytable = new PdfPTable(1);
    mytable.setWidthPercentage(100.0F);
    mytable.setSplitLate(false);
    
    mytable.addCell(paddedTableCell(mainheaderPanel(this.therealorder), 0));
    mytable.setHeaderRows(1);
    mytable.addCell(paddedTableCell(customeradditionalPanel(this.therealorder), 0));
    mytable.addCell(paddedTableCell(itemsArtworkSummaryPage(this.therealorder), 0));
    
    this.document.add(mytable);
    
    this.document.close();
  }
  
  public void overseasapproval() throws Exception {
    this.overseaspricecalc.calculateOrderPrice(true);
    OrderGrouper theordergrouper = new OrderGrouper(this.therealorder);
    ExtendOrderData thegroupedorder = theordergrouper.getGroupedOrderData();
    
    if (!this.isIE) {
      this.response.setHeader("Content-disposition", "attachment; filename=\"" + new SimpleDateFormat("MM.dd.yy-hh.mm.a-").format(new Date()) + "OverseasApproval" + this.therealorder.getOrderNumber() + ".pdf\"");
    }
    setHeaderColor(OverseasColor);
    setOrderType(PDFType.OVERSEASAPPROVAL);
    PdfWriter writer = PdfWriter.getInstance(this.document, this.response.getOutputStream());
    writer.setPageEvent(new PageNumbers());
    this.document.open();
    
    PdfPTable mytable = new PdfPTable(1);
    mytable.setWidthPercentage(100.0F);
    mytable.setSplitLate(false);
    
    mytable.addCell(paddedTableCell(mainheaderPanel(thegroupedorder), 0));
    mytable.setHeaderRows(1);
    mytable.addCell(paddedTableCell(customeradditionalPanel(thegroupedorder), 0));
    mytable.addCell(paddedTableCell(billshipPanel(thegroupedorder), 0));
    mytable.addCell(paddedTableCell(itemsOverseasSummaryPage(thegroupedorder), 0));
    
    mytable.setTotalWidth(this.document.getPageSize().getWidth() - this.document.leftMargin() - this.document.rightMargin());
    
    float currentpageheight = mytable.getTotalHeight();
    mytable.addCell(paddedTableCell(pricingcommentstermsPanel(thegroupedorder, 1.0F), 0));
    float pricingheight = mytable.getTotalHeight() - currentpageheight;
    mytable.deleteLastRow();
    
    boolean morethanonepage = false;
    while (currentpageheight > this.document.getPageSize().getHeight() - this.document.topMargin() - this.document.bottomMargin()) {
      morethanonepage = true;
      currentpageheight = currentpageheight - (this.document.getPageSize().getHeight() - this.document.topMargin() - this.document.bottomMargin()) + mytable.getHeaderHeight() + itemsOverseasSummaryPage(thegroupedorder).getHeaderHeight();
    }
    
    if (morethanonepage) {
      if (currentpageheight + pricingheight <= this.document.getPageSize().getHeight() - this.document.topMargin() - this.document.bottomMargin()) {
        currentpageheight = this.document.getPageSize().getHeight() - this.document.topMargin() - this.document.bottomMargin() - pricingheight;
      }
      mytable.deleteLastRow();
      PdfPCell itemsummerycell = paddedTableCell(itemsOverseasSummaryPage(thegroupedorder), 0);
      itemsummerycell.setMinimumHeight(currentpageheight - mytable.getHeaderHeight());
      mytable.addCell(itemsummerycell);
    }
    if (pricingheight <= this.document.getPageSize().getHeight() - this.document.topMargin() - this.document.bottomMargin() - mytable.getHeaderHeight()) {
      if (currentpageheight + pricingheight <= this.document.getPageSize().getHeight() - this.document.topMargin() - this.document.bottomMargin())
      {
        PdfPCell pricingcell = paddedTableCell(pricingcommentstermsPanel(thegroupedorder, this.document.getPageSize().getHeight() - this.document.topMargin() - this.document.bottomMargin() - currentpageheight), 0);
        pricingcell.setMinimumHeight(this.document.getPageSize().getHeight() - this.document.topMargin() - this.document.bottomMargin() - currentpageheight);
        mytable.addCell(pricingcell);
      }
      else
      {
        if (morethanonepage) {
          mytable.deleteLastRow();
          PdfPCell itemsummerycell = paddedTableCell(itemsOverseasSummaryPage(thegroupedorder), 0);
          itemsummerycell.setMinimumHeight(this.document.getPageSize().getHeight() - this.document.topMargin() - this.document.bottomMargin() - mytable.getHeaderHeight());
          mytable.addCell(itemsummerycell);
        } else {
          mytable.deleteLastRow();
          PdfPCell itemsummerycell = paddedTableCell(itemsOverseasSummaryPage(thegroupedorder), 0);
          itemsummerycell.setMinimumHeight(this.document.getPageSize().getHeight() - this.document.topMargin() - this.document.bottomMargin() - mytable.getTotalHeight());
          mytable.addCell(itemsummerycell);
        }
        PdfPCell pricingcell = paddedTableCell(pricingcommentstermsPanel(thegroupedorder, this.document.getPageSize().getHeight() - this.document.topMargin() - this.document.bottomMargin() - mytable.getHeaderHeight()), 0);
        pricingcell.setMinimumHeight(this.document.getPageSize().getHeight() - this.document.topMargin() - this.document.bottomMargin() - mytable.getHeaderHeight());
        mytable.addCell(pricingcell);
      }
    }
    else {
      mytable.deleteLastRow();
      mytable.addCell(paddedTableCell(itemsOverseasSummaryPage(thegroupedorder), 0));
      float currentheight = mytable.getTotalHeight();
      mytable.addCell(paddedTableCell(commenttermsPanel(thegroupedorder), 0));
      currentheight = mytable.getTotalHeight() - currentheight;
      PdfPCell pricingcellpanel = paddedTableCell(pricingPanel(Boolean.valueOf(false)), 0);
      pricingcellpanel.setMinimumHeight(this.document.getPageSize().getHeight() - this.document.topMargin() - this.document.bottomMargin() - currentheight - mytable.getHeaderHeight());
      mytable.deleteLastRow();
      mytable.addCell(pricingcellpanel);
      mytable.addCell(paddedTableCell(commenttermsPanel(thegroupedorder), 0));
    }
    
    this.document.add(mytable);
    
    productpreviewPage(thegroupedorder);
    
    this.document.close();
  }
  
  public void orderapproval() throws Exception {
    this.domesticpricecalc.calculateCustomerPrice(true);
    OrderGrouper theordergrouper = new OrderGrouper(this.therealorder);
    ExtendOrderData thegroupedorder = theordergrouper.getGroupedOrderData();
    
    if (!this.isIE) {
      this.response.setHeader("Content-disposition", "attachment; filename=\"" + new SimpleDateFormat("MM.dd.yy-hh.mm.a-").format(new Date()) + "DomesticApproval" + this.therealorder.getOrderNumber() + ".pdf\"");
    }
    setHeaderColor(DomesticColor);
    setOrderType(PDFType.APPROVAL);
    PdfWriter writer = PdfWriter.getInstance(this.document, this.response.getOutputStream());
    writer.setPageEvent(new PageNumbers());
    this.document.open();
    PdfPTable mytable = new PdfPTable(1);
    mytable.setWidthPercentage(100.0F);
    mytable.setSplitLate(false);
    
    mytable.addCell(paddedTableCell(mainheaderPanel(thegroupedorder), 0));
    mytable.setHeaderRows(1);
    mytable.addCell(paddedTableCell(customeradditionalPanel(thegroupedorder), 0));
    mytable.addCell(paddedTableCell(billshipPanel(thegroupedorder), 0));
    mytable.addCell(paddedTableCell(itemsSummaryPage(thegroupedorder), 0));
    
    mytable.setTotalWidth(this.document.getPageSize().getWidth() - this.document.leftMargin() - this.document.rightMargin());
    
    float currentpageheight = mytable.getTotalHeight();
    mytable.addCell(paddedTableCell(pricingcommentstermsPanel(thegroupedorder, 1.0F), 0));
    float pricingheight = mytable.getTotalHeight() - currentpageheight;
    mytable.deleteLastRow();
    
    boolean morethanonepage = false;
    while (currentpageheight > this.document.getPageSize().getHeight() - this.document.topMargin() - this.document.bottomMargin()) {
      morethanonepage = true;
      currentpageheight = currentpageheight - (this.document.getPageSize().getHeight() - this.document.topMargin() - this.document.bottomMargin()) + mytable.getHeaderHeight() + itemsSummaryPage(thegroupedorder).getHeaderHeight();
    }
    
    if (morethanonepage) {
      if (currentpageheight + pricingheight <= this.document.getPageSize().getHeight() - this.document.topMargin() - this.document.bottomMargin()) {
        currentpageheight = this.document.getPageSize().getHeight() - this.document.topMargin() - this.document.bottomMargin() - pricingheight;
      }
      mytable.deleteLastRow();
      PdfPCell itemsummerycell = paddedTableCell(itemsSummaryPage(thegroupedorder), 0);
      itemsummerycell.setMinimumHeight(currentpageheight - mytable.getHeaderHeight());
      mytable.addCell(itemsummerycell);
    }
    if (pricingheight <= this.document.getPageSize().getHeight() - this.document.topMargin() - this.document.bottomMargin() - mytable.getHeaderHeight()) {
      if (currentpageheight + pricingheight <= this.document.getPageSize().getHeight() - this.document.topMargin() - this.document.bottomMargin())
      {
        PdfPCell pricingcell = paddedTableCell(pricingcommentstermsPanel(thegroupedorder, this.document.getPageSize().getHeight() - this.document.topMargin() - this.document.bottomMargin() - currentpageheight), 0);
        pricingcell.setMinimumHeight(this.document.getPageSize().getHeight() - this.document.topMargin() - this.document.bottomMargin() - currentpageheight);
        mytable.addCell(pricingcell);
      }
      else
      {
        if (morethanonepage) {
          mytable.deleteLastRow();
          PdfPCell itemsummerycell = paddedTableCell(itemsSummaryPage(thegroupedorder), 0);
          itemsummerycell.setMinimumHeight(this.document.getPageSize().getHeight() - this.document.topMargin() - this.document.bottomMargin() - mytable.getHeaderHeight());
          mytable.addCell(itemsummerycell);
        } else {
          mytable.deleteLastRow();
          PdfPCell itemsummerycell = paddedTableCell(itemsSummaryPage(thegroupedorder), 0);
          itemsummerycell.setMinimumHeight(this.document.getPageSize().getHeight() - this.document.topMargin() - this.document.bottomMargin() - mytable.getTotalHeight());
          mytable.addCell(itemsummerycell);
        }
        PdfPCell pricingcell = paddedTableCell(pricingcommentstermsPanel(thegroupedorder, this.document.getPageSize().getHeight() - this.document.topMargin() - this.document.bottomMargin() - mytable.getHeaderHeight()), 0);
        pricingcell.setMinimumHeight(this.document.getPageSize().getHeight() - this.document.topMargin() - this.document.bottomMargin() - mytable.getHeaderHeight());
        mytable.addCell(pricingcell);
      }
    }
    else {
      mytable.deleteLastRow();
      mytable.addCell(paddedTableCell(itemsSummaryPage(thegroupedorder), 0));
      float currentheight = mytable.getTotalHeight();
      mytable.addCell(paddedTableCell(commenttermsPanel(thegroupedorder), 0));
      currentheight = mytable.getTotalHeight() - currentheight;
      PdfPCell pricingcellpanel = paddedTableCell(pricingPanel(Boolean.valueOf(false)), 0);
      pricingcellpanel.setMinimumHeight(this.document.getPageSize().getHeight() - this.document.topMargin() - this.document.bottomMargin() - currentheight - mytable.getHeaderHeight());
      mytable.deleteLastRow();
      mytable.addCell(pricingcellpanel);
      mytable.addCell(paddedTableCell(commenttermsPanel(thegroupedorder), 0));
    }
    
    this.document.add(mytable);
    
    productpreviewPage(thegroupedorder);
    
    this.document.close();
  }
  
  public void overseasproductionapproval() throws Exception {
    if (!this.isIE) {
      this.response.setHeader("Content-disposition", "attachment; filename=\"" + new SimpleDateFormat("MM.dd.yy-hh.mm.a-").format(new Date()) + "OverseasProductionApproval" + this.therealorder.getOrderNumber() + ".pdf\"");
    }
    setHeaderColor(OverseasColor);
    setOrderType(PDFType.OVERSEASPRODUCTIONAPPROVAL);
    PdfWriter writer = PdfWriter.getInstance(this.document, this.response.getOutputStream());
    writer.setPageEvent(new PageNumbers());
    this.document.open();
    productpreviewPage(this.therealorder);
    this.document.close();
  }
  
  public void productionapproval() throws Exception {
    if (!this.isIE) {
      this.response.setHeader("Content-disposition", "attachment; filename=\"" + new SimpleDateFormat("MM.dd.yy-hh.mm.a-").format(new Date()) + "DomesticProductionApproval" + this.therealorder.getOrderNumber() + ".pdf\"");
    }
    setHeaderColor(DomesticColor);
    setOrderType(PDFType.PRODUCTIONAPPROVAL);
    PdfWriter writer = PdfWriter.getInstance(this.document, this.response.getOutputStream());
    writer.setPageEvent(new PageNumbers());
    this.document.open();
    productpreviewPage(this.therealorder);
    this.document.close();
  }
  
  private void productpreviewPage(ExtendOrderData currentorder) throws Exception {
    for (int i = 0; i < currentorder.getSetCount(); i++) {
      if (getOrderType() == PDFType.PRODUCTIONAPPROVAL) {
        if ((currentorder.getSet(i).getItem(0).getProductSampleTotalDone() != null) && (currentorder.getSet(i).getItem(0).getProductSampleTotalDone().intValue() > 0)) {
          this.document.newPage();
          this.document.add(productpreviewitem(currentorder, i));
        } else {
          Boolean foundswatch = Boolean.valueOf(false);
          for (int j = 0; j < currentorder.getSet(i).getLogoCount(); j++) {
            if ((currentorder.getSet(i).getLogo(j).getSwatchTotalDone() != null) && (currentorder.getSet(i).getLogo(j).getSwatchTotalDone().intValue() > 0)) {
              foundswatch = Boolean.valueOf(true);
              break;
            }
          }
          if (foundswatch.booleanValue()) {
            this.document.newPage();
            this.document.add(productpreviewitem(currentorder, i));
          }
        }
      } else if (getOrderType() == PDFType.OVERSEASPRODUCTIONAPPROVAL) {
        if (((currentorder.getSet(i).getItem(0).getProductSampleTotalEmail() != null) && (currentorder.getSet(i).getItem(0).getProductSampleTotalEmail().intValue() > 0)) || ((currentorder.getSet(i).getItem(0).getProductSampleTotalShip() != null) && (currentorder.getSet(i).getItem(0).getProductSampleTotalShip().intValue() > 0))) {
          this.document.newPage();
          this.document.add(productpreviewitem(currentorder, i));
        } else {
          Boolean foundswatch = Boolean.valueOf(false);
          for (int j = 0; j < currentorder.getSet(i).getLogoCount(); j++) {
            if (((currentorder.getSet(i).getLogo(j).getSwatchTotalEmail() != null) && (currentorder.getSet(i).getLogo(j).getSwatchTotalEmail().intValue() > 0)) || ((currentorder.getSet(i).getLogo(j).getSwatchTotalShip() != null) && (currentorder.getSet(i).getLogo(j).getSwatchTotalEmail().intValue() > 0))) {
              foundswatch = Boolean.valueOf(true);
              break;
            }
          }
          if (foundswatch.booleanValue()) {
            this.document.newPage();
            this.document.add(productpreviewitem(currentorder, i));
          }
        }
      } else if ((getOrderType() == PDFType.EMBSAMPLE) || (getOrderType() == PDFType.HEATSAMPLE) || (getOrderType() == PDFType.SCREENSAMPLE) || (getOrderType() == PDFType.DIRECTTOGARMENTSAMPLE) || (getOrderType() == PDFType.PATCHSAMPLE)) {
        boolean containsemb = false;
        boolean containsheat = false;
        boolean containsscreen = false;
        boolean containsdirecttogarment = false;
        boolean containspatch = false;
        boolean containsembswatch = false;
        boolean containsheatswatch = false;
        boolean containsscreenswatch = false;
        boolean containsdirecttogarmentswatch = false;
        boolean containspatchswatch = false;
        for (int j = 0; j < currentorder.getSet(i).getLogoCount(); j++) {
          if ((currentorder.getSet(i).getLogo(j).getDecoration().equals("Flat Embroidery")) || (currentorder.getSet(i).getLogo(j).getDecoration().equals("3D Embroidery"))) {
            containsemb = true;
            if ((currentorder.getSet(i).getLogo(j).getSwatchToDo() != null) && (currentorder.getSet(i).getLogo(j).getSwatchToDo().intValue() > 0)) {
              containsembswatch = true;
            }
          } else if (currentorder.getSet(i).getLogo(j).getDecoration().equals("Heat Transfer")) {
            containsheat = true;
            if ((currentorder.getSet(i).getLogo(j).getSwatchToDo() != null) && (currentorder.getSet(i).getLogo(j).getSwatchToDo().intValue() > 0)) {
              containsheatswatch = true;
            }
          } else if ((currentorder.getSet(i).getLogo(j).getDecoration().equals("Four Color Screen Print")) || (currentorder.getSet(i).getLogo(j).getDecoration().equals("Screen Print"))) {
            containsscreen = true;
            if ((currentorder.getSet(i).getLogo(j).getSwatchToDo() != null) && (currentorder.getSet(i).getLogo(j).getSwatchToDo().intValue() > 0)) {
              containsscreenswatch = true;
            }
          } else if (currentorder.getSet(i).getLogo(j).getDecoration().equals("Direct To Garment")) {
            containsdirecttogarment = true;
            if ((currentorder.getSet(i).getLogo(j).getSwatchToDo() != null) && (currentorder.getSet(i).getLogo(j).getSwatchToDo().intValue() > 0)) {
              containsdirecttogarmentswatch = true;
            }
          } else if (currentorder.getSet(i).getLogo(j).getDecoration().equals("Patch")) {
            containspatch = true;
            if ((currentorder.getSet(i).getLogo(j).getSwatchToDo() != null) && (currentorder.getSet(i).getLogo(j).getSwatchToDo().intValue() > 0)) {
              containspatchswatch = true;
            }
          } else {
            containsemb = true;
            if ((currentorder.getSet(i).getLogo(j).getSwatchToDo() != null) && (currentorder.getSet(i).getLogo(j).getSwatchToDo().intValue() > 0)) {
              containsembswatch = true;
            }
          }
        }
        int vendornumber = 0;
        if (getOrderType() == PDFType.EMBSAMPLE) {
          vendornumber = currentorder.getVendorInformation().getEmbroideryVendor().getVendor().intValue();
        } else if (getOrderType() == PDFType.HEATSAMPLE) {
          vendornumber = currentorder.getVendorInformation().getHeatTransferVendor().getVendor().intValue();
        } else if (getOrderType() == PDFType.SCREENSAMPLE) {
          vendornumber = currentorder.getVendorInformation().getScreenPrintVendor().getVendor().intValue();
        } else if (getOrderType() == PDFType.DIRECTTOGARMENTSAMPLE) {
          vendornumber = currentorder.getVendorInformation().getDirectToGarmentVendor().getVendor().intValue();
        } else if (getOrderType() == PDFType.PATCHSAMPLE) {
          vendornumber = currentorder.getVendorInformation().getPatchVendor().getVendor().intValue();
        }
        if ((vendornumber == currentorder.getVendorInformation().getEmbroideryVendor().getVendor().intValue()) && ((containsembswatch) || ((containsemb) && (currentorder.getSet(i).getItem(0).getProductSampleToDo() != null) && (currentorder.getSet(i).getItem(0).getProductSampleToDo().intValue() > 0)))) {
          this.document.newPage();
          this.document.add(productpreviewitem(currentorder, i));
        } else if ((vendornumber == currentorder.getVendorInformation().getHeatTransferVendor().getVendor().intValue()) && ((containsheatswatch) || ((containsheat) && (currentorder.getSet(i).getItem(0).getProductSampleToDo() != null) && (currentorder.getSet(i).getItem(0).getProductSampleToDo().intValue() > 0)))) {
          this.document.newPage();
          this.document.add(productpreviewitem(currentorder, i));
        } else if ((vendornumber == currentorder.getVendorInformation().getScreenPrintVendor().getVendor().intValue()) && ((containsscreenswatch) || ((containsscreen) && (currentorder.getSet(i).getItem(0).getProductSampleToDo() != null) && (currentorder.getSet(i).getItem(0).getProductSampleToDo().intValue() > 0)))) {
          this.document.newPage();
          this.document.add(productpreviewitem(currentorder, i));
        } else if ((vendornumber == currentorder.getVendorInformation().getDirectToGarmentVendor().getVendor().intValue()) && ((containsdirecttogarmentswatch) || ((containsdirecttogarment) && (currentorder.getSet(i).getItem(0).getProductSampleToDo() != null) && (currentorder.getSet(i).getItem(0).getProductSampleToDo().intValue() > 0)))) {
          this.document.newPage();
          this.document.add(productpreviewitem(currentorder, i));
        } else if ((vendornumber == currentorder.getVendorInformation().getPatchVendor().getVendor().intValue()) && ((containspatchswatch) || ((containspatch) && (currentorder.getSet(i).getItem(0).getProductSampleToDo() != null) && (currentorder.getSet(i).getItem(0).getProductSampleToDo().intValue() > 0)))) {
          this.document.newPage();
          this.document.add(productpreviewitem(currentorder, i));
        }
      } else if (getOrderType() == PDFType.OVERSEASSAMPLE) {
        boolean containsswatch = false;
        for (int j = 0; j < currentorder.getSet(i).getLogoCount(); j++) {
          if ((currentorder.getSet(i).getLogo(j).getSwatchToDo() != null) && (currentorder.getSet(i).getLogo(j).getSwatchToDo().intValue() > 0)) {
            containsswatch = true;
          }
        }
        if ((currentorder.getSet(i).getItem(0).getOverseasVendorNumber() == getOverseasVendorNumber()) && ((containsswatch) || ((currentorder.getSet(i).getItem(0).getProductSampleToDo() != null) && (currentorder.getSet(i).getItem(0).getProductSampleToDo().intValue() > 0)))) {
          this.document.newPage();
          this.document.add(productpreviewitem(currentorder, i));
        }
      } else if (getOrderType() == PDFType.OVERSEASPURCHASEORDER) {
        if (currentorder.getSet(i).getItem(0).getOverseasVendorNumber() == getOverseasVendorNumber()) {
          this.document.newPage();
          this.document.add(productpreviewitem(currentorder, i));
        }
      } else if ((getOrderType() == PDFType.EMBORDER) || (getOrderType() == PDFType.SCREENORDER) || (getOrderType() == PDFType.HEATORDER) || (getOrderType() == PDFType.DIRECTTOGARMENTORDER) || (getOrderType() == PDFType.PATCHORDER)) {
        boolean containsemb = false;
        boolean containsheat = false;
        boolean containsscreen = false;
        boolean containsdirecttogarment = false;
        boolean containspatch = false;
        for (int j = 0; j < currentorder.getSet(i).getLogoCount(); j++) {
          if ((currentorder.getSet(i).getLogo(j).getDecoration().equals("Flat Embroidery")) || (currentorder.getSet(i).getLogo(j).getDecoration().equals("3D Embroidery"))) {
            containsemb = true;
          } else if (currentorder.getSet(i).getLogo(j).getDecoration().equals("Heat Transfer")) {
            containsheat = true;
          } else if (currentorder.getSet(i).getLogo(j).getDecoration().equals("Direct To Garment")) {
            containsdirecttogarment = true;
          } else if ((currentorder.getSet(i).getLogo(j).getDecoration().equals("Screen Print")) || (currentorder.getSet(i).getLogo(j).getDecoration().equals("Four Color Screen Print"))) {
            containsscreen = true;
          } else if (currentorder.getSet(i).getLogo(j).getDecoration().equals("Patch")) {
            containspatch = true;
          } else {
            containsemb = true;
          }
        }
        int vendornumber = 0;
        if (getOrderType() == PDFType.EMBORDER) {
          vendornumber = currentorder.getVendorInformation().getEmbroideryVendor().getVendor().intValue();
        } else if (getOrderType() == PDFType.HEATORDER) {
          vendornumber = currentorder.getVendorInformation().getHeatTransferVendor().getVendor().intValue();
        } else if (getOrderType() == PDFType.SCREENORDER) {
          vendornumber = currentorder.getVendorInformation().getScreenPrintVendor().getVendor().intValue();
        } else if (getOrderType() == PDFType.DIRECTTOGARMENTORDER) {
          vendornumber = currentorder.getVendorInformation().getDirectToGarmentVendor().getVendor().intValue();
        } else if (getOrderType() == PDFType.PATCHORDER) {
          vendornumber = currentorder.getVendorInformation().getPatchVendor().getVendor().intValue();
        }
        if ((currentorder.getVendorInformation().getEmbroideryVendor().getVendor().intValue() == vendornumber) && (containsemb)) {
          this.document.newPage();
          this.document.add(productpreviewitem(currentorder, i));
        } else if ((currentorder.getVendorInformation().getHeatTransferVendor().getVendor().intValue() == vendornumber) && (containsheat)) {
          this.document.newPage();
          this.document.add(productpreviewitem(currentorder, i));
        } else if ((currentorder.getVendorInformation().getScreenPrintVendor().getVendor().intValue() == vendornumber) && (containsscreen)) {
          this.document.newPage();
          this.document.add(productpreviewitem(currentorder, i));
        } else if ((currentorder.getVendorInformation().getDirectToGarmentVendor().getVendor().intValue() == vendornumber) && (containsdirecttogarment)) {
          this.document.newPage();
          this.document.add(productpreviewitem(currentorder, i));
        } else if ((currentorder.getVendorInformation().getPatchVendor().getVendor().intValue() == vendornumber) && (containspatch)) {
          this.document.newPage();
          this.document.add(productpreviewitem(currentorder, i));
        }
      }
      else if (getOrderType() == PDFType.DIGITIZING) {
        Boolean founddigitizingtape = Boolean.valueOf(false);
        for (int j = 0; j < currentorder.getSet(i).getLogoCount(); j++) {
          if ((currentorder.getSet(i).getLogo(j).getDigitizing()) || (currentorder.getSet(i).getLogo(j).getTapeEdit())) {
            founddigitizingtape = Boolean.valueOf(true);
            break;
          }
        }
        if (founddigitizingtape.booleanValue()) {
          this.document.newPage();
          this.document.add(productpreviewitem(currentorder, i));
        }
      } else if (getOrderType() == PDFType.OVERSEASDIGITIZING) {
        Boolean founddigitizingtape = Boolean.valueOf(false);
        if (currentorder.getSet(i).getItem(0).getOverseasVendorNumber() == getOverseasVendorNumber()) {
          for (int j = 0; j < currentorder.getSet(i).getLogoCount(); j++) {
            if (currentorder.getSet(i).getLogo(j).getDigitizing()) {
              founddigitizingtape = Boolean.valueOf(true);
              break;
            }
          }
        }
        if (founddigitizingtape.booleanValue()) {
          this.document.newPage();
          this.document.add(productpreviewitem(currentorder, i));
        }
      } else {
        this.document.newPage();
        this.document.add(productpreviewitem(currentorder, i));
      }
    }
  }
  
  private PdfPTable productpreviewitem(ExtendOrderData currentorder, int item) throws Exception {
    PdfPTable mytable = new PdfPTable(1);
    mytable.setTotalWidth(this.document.getPageSize().getWidth() - this.document.leftMargin() - this.document.rightMargin());
    mytable.setWidthPercentage(100.0F);
    mytable.setSplitLate(false);
    
    if ((getOrderType() == PDFType.GENERIC) || (getOrderType() == PDFType.OVERSEASGENERIC))
    {





      mytable.addCell(paddedTableCell(genericheaderPanel(currentorder), 0));
    } else {
      mytable.addCell(paddedTableCell(mainheaderPanel(currentorder), 0));
    }
    mytable.setHeaderRows(1);
    
    if ((getOrderType() == PDFType.DIGITIZING) || (getOrderType() == PDFType.EMBSAMPLE) || (getOrderType() == PDFType.HEATSAMPLE) || (getOrderType() == PDFType.SCREENSAMPLE) || (getOrderType() == PDFType.DIRECTTOGARMENTSAMPLE) || (getOrderType() == PDFType.PATCHSAMPLE) || (getOrderType() == PDFType.EMBORDER) || (getOrderType() == PDFType.HEATORDER) || (getOrderType() == PDFType.SCREENORDER) || (getOrderType() == PDFType.DIRECTTOGARMENTORDER) || (getOrderType() == PDFType.PATCHORDER) || (getOrderType() == PDFType.OVERSEASDIGITIZING) || (getOrderType() == PDFType.OVERSEASSAMPLE) || (getOrderType() == PDFType.OVERSEASPURCHASEORDER)) {
      mytable.addCell(paddedTableCell(previewitemnotesorderinfoPanel(currentorder, item), 0));
    }
    mytable.addCell(paddedTableCell(previewitemPanel(currentorder, item), 0));
    
    float itemapprovalpanelheight = 0.0F;
    if ((getOrderType() == PDFType.APPROVAL) || (getOrderType() == PDFType.GENERIC) || (getOrderType() == PDFType.PRODUCTIONAPPROVAL) || (getOrderType() == PDFType.QUOTATION) || (getOrderType() == PDFType.OVERSEASQUOTATION) || (getOrderType() == PDFType.OVERSEASGENERIC) || (getOrderType() == PDFType.OVERSEASAPPROVAL) || (getOrderType() == PDFType.OVERSEASPRODUCTIONAPPROVAL)) {
      mytable.addCell(paddedTableCell(previewitemnotesapprovalPanel(currentorder, item), 0));
      itemapprovalpanelheight = mytable.getRowHeight(mytable.getRows().size() - 1);
    }
    
    PdfPCell mylogocell = paddedTableCell(previewlogosPanel(currentorder, item), 0);
    mytable.addCell(mylogocell);
    
    boolean greaterthanonepage = false;
    double totalheight = mytable.getTotalHeight();
    if (totalheight > this.document.getPageSize().getHeight() - this.document.bottomMargin() - this.document.topMargin()) {
      greaterthanonepage = true;
    }
    mytable.deleteLastRow();
    



    if (greaterthanonepage) {
      mylogocell.setMinimumHeight(this.document.getPageSize().getHeight() - this.document.bottomMargin() - this.document.topMargin() - itemapprovalpanelheight - mytable.getHeaderHeight());
    } else {
      mylogocell.setMinimumHeight(this.document.getPageSize().getHeight() - this.document.bottomMargin() - this.document.topMargin() - mytable.getTotalHeight() - 1.0E-4F);
    }
    
    if ((getOrderType() == PDFType.APPROVAL) || (getOrderType() == PDFType.GENERIC) || (getOrderType() == PDFType.PRODUCTIONAPPROVAL) || (getOrderType() == PDFType.QUOTATION) || (getOrderType() == PDFType.OVERSEASQUOTATION) || (getOrderType() == PDFType.OVERSEASGENERIC) || (getOrderType() == PDFType.OVERSEASAPPROVAL) || (getOrderType() == PDFType.OVERSEASPRODUCTIONAPPROVAL)) {
      mytable.deleteLastRow();
    }
    if (getOrderType() == PDFType.OVERSEASQUOTATION) {
      if (currentorder.getSet(item).getLogoCount() > 0) {
        mytable.addCell(mylogocell);
      }
    } else {
      mytable.addCell(mylogocell);
    }
    if ((getOrderType() == PDFType.APPROVAL) || (getOrderType() == PDFType.GENERIC) || (getOrderType() == PDFType.PRODUCTIONAPPROVAL) || (getOrderType() == PDFType.QUOTATION) || (getOrderType() == PDFType.OVERSEASQUOTATION) || (getOrderType() == PDFType.OVERSEASGENERIC) || (getOrderType() == PDFType.OVERSEASAPPROVAL) || (getOrderType() == PDFType.OVERSEASPRODUCTIONAPPROVAL)) {
      mytable.addCell(paddedTableCell(previewitemnotesapprovalPanel(currentorder, item), 0));
    }
    return mytable;
  }
  
  private PdfPTable previewitemnotesorderinfoPanel(ExtendOrderData currentorder, int item) throws Exception {
    PdfPTable mytable = new PdfPTable(3);
    float[] tableWidths = { 57.5F, 1.0F, 41.5F };
    mytable.setWidths(tableWidths);
    
    mytable.addCell(paddedTableCell(orderinformationPanel(currentorder, item), 0));
    mytable.addCell(buzzcell("", this.cellfont));
    mytable.addCell(paddedTableCell(previewitemnotesPanel(currentorder, item), 0));
    
    return mytable;
  }
  
  private PdfPTable orderinformationPanel(ExtendOrderData currentorder, int item) throws Exception {
    PdfPTable header = new PdfPTable(1);
    PdfPCell customerinformationcell = headercell("ORDER INFORMATION", this.headerfont);
    customerinformationcell.setHorizontalAlignment(1);
    header.addCell(customerinformationcell);
    
    PdfPTable body = new PdfPTable(2);
    float[] bodyWidths = { 60.0F, 40.0F };
    body.setWidths(bodyWidths);
    
    PdfPTable info = new PdfPTable(2);
    float[] infoWidths = { 50.0F, 50.0F };
    info.setWidths(infoWidths);
    
    if (getOrderType() == PDFType.DIGITIZING) {
      info.addCell(buzzcell("Digitizing Vendor:", this.cellfontbold));
      info.addCell(buzzcell(currentorder.getVendorInformation().getDigitizerVendor().getVendorName(), this.cellfont));
    } else if ((getOrderType() == PDFType.EMBSAMPLE) || (getOrderType() == PDFType.EMBORDER)) {
      info.addCell(buzzcell("Work Order Vendor:", this.cellfontbold));
      info.addCell(buzzcell(currentorder.getVendorInformation().getEmbroideryVendor().getVendorName(), this.cellfont));
    } else if ((getOrderType() == PDFType.HEATSAMPLE) || (getOrderType() == PDFType.HEATORDER)) {
      info.addCell(buzzcell("Work Order Vendor:", this.cellfontbold));
      info.addCell(buzzcell(currentorder.getVendorInformation().getHeatTransferVendor().getVendorName(), this.cellfont));
    } else if ((getOrderType() == PDFType.SCREENSAMPLE) || (getOrderType() == PDFType.SCREENORDER)) {
      info.addCell(buzzcell("Work Order Vendor:", this.cellfontbold));
      info.addCell(buzzcell(currentorder.getVendorInformation().getScreenPrintVendor().getVendorName(), this.cellfont));
    } else if ((getOrderType() == PDFType.DIRECTTOGARMENTSAMPLE) || (getOrderType() == PDFType.DIRECTTOGARMENTORDER)) {
      info.addCell(buzzcell("Work Order Vendor:", this.cellfontbold));
      info.addCell(buzzcell(currentorder.getVendorInformation().getDirectToGarmentVendor().getVendorName(), this.cellfont));
    } else if ((getOrderType() == PDFType.PATCHSAMPLE) || (getOrderType() == PDFType.PATCHORDER)) {
      info.addCell(buzzcell("Work Order Vendor:", this.cellfontbold));
      info.addCell(buzzcell(currentorder.getVendorInformation().getPatchVendor().getVendorName(), this.cellfont));
    } else if (getOrderType() == PDFType.OVERSEASDIGITIZING) {
      info.addCell(buzzcell("Digitizing Vendor:", this.cellfontbold));
      info.addCell(buzzcell(((ExtendOrderDataVendorInformation)currentorder.getVendorInformation().getOverseasVendor().get(String.valueOf(getOverseasVendorNumber()))).getVendorName(), this.cellfont));
    } else if ((getOrderType() == PDFType.OVERSEASSAMPLE) || (getOrderType() == PDFType.OVERSEASPURCHASEORDER)) {
      info.addCell(buzzcell("Purchase Order Vendor:", this.cellfontbold));
      info.addCell(buzzcell(((ExtendOrderDataVendorInformation)currentorder.getVendorInformation().getOverseasVendor().get(String.valueOf(getOverseasVendorNumber()))).getVendorName(), this.cellfont));
    }
    
    info.addCell(buzzcell("Processing Agent:", this.cellfontbold));
    info.addCell(buzzcell(currentorder.getEmployeeFullName(), this.cellfont));
    
    info.addCell(buzzcell("Processing Agent Email:", this.cellfontbold));
    info.addCell(buzzcell(currentorder.getEmployeeEmail(), this.cellfont));
    
    if (getOrderType() == PDFType.DIGITIZING) {
      info.addCell(buzzcell("Processing Date:", this.cellfontbold));
      info.addCell(buzzcell(fixdate(currentorder.getVendorInformation().getDigitizerVendor().getDigitizingProcessingDate()), this.cellfont));
      
      info.addCell(buzzcell("Due Date:", this.cellfontbold));
      info.addCell(buzzcell(fixdate(currentorder.getVendorInformation().getDigitizerVendor().getDigitizingDueDate()), this.cellfont));
    } else if (getOrderType() == PDFType.OVERSEASDIGITIZING) {
      info.addCell(buzzcell("Processing Date:", this.cellfontbold));
      info.addCell(buzzcell(fixdate(((OrderDataVendorInformation)currentorder.getVendorInformation().getOverseasVendor().get(String.valueOf(getOverseasVendorNumber()))).getDigitizingProcessingDate()), this.cellfont));
      
      info.addCell(buzzcell("Due Date:", this.cellfontbold));
      info.addCell(buzzcell(fixdate(((OrderDataVendorInformation)currentorder.getVendorInformation().getOverseasVendor().get(String.valueOf(getOverseasVendorNumber()))).getDigitizingDueDate()), this.cellfont));
    } else if ((getOrderType() == PDFType.EMBSAMPLE) || (getOrderType() == PDFType.EMBORDER)) {
      info.addCell(buzzcell("Processing Date:", this.cellfontbold));
      info.addCell(buzzcell(fixdate(currentorder.getVendorInformation().getEmbroideryVendor().getWorkOrderProcessingDate()), this.cellfont));
      
      info.addCell(buzzcell("Due Date:", this.cellfontbold));
      info.addCell(buzzcell(fixdate(currentorder.getVendorInformation().getEmbroideryVendor().getWorkOrderDueDate()), this.cellfont));
    } else if ((getOrderType() == PDFType.HEATSAMPLE) || (getOrderType() == PDFType.HEATORDER)) {
      info.addCell(buzzcell("Processing Date:", this.cellfontbold));
      info.addCell(buzzcell(fixdate(currentorder.getVendorInformation().getHeatTransferVendor().getWorkOrderProcessingDate()), this.cellfont));
      
      info.addCell(buzzcell("Due Date:", this.cellfontbold));
      info.addCell(buzzcell(fixdate(currentorder.getVendorInformation().getHeatTransferVendor().getWorkOrderDueDate()), this.cellfont));
    } else if ((getOrderType() == PDFType.SCREENSAMPLE) || (getOrderType() == PDFType.SCREENORDER)) {
      info.addCell(buzzcell("Processing Date:", this.cellfontbold));
      info.addCell(buzzcell(fixdate(currentorder.getVendorInformation().getScreenPrintVendor().getWorkOrderProcessingDate()), this.cellfont));
      
      info.addCell(buzzcell("Due Date:", this.cellfontbold));
      info.addCell(buzzcell(fixdate(currentorder.getVendorInformation().getScreenPrintVendor().getWorkOrderDueDate()), this.cellfont));
    } else if ((getOrderType() == PDFType.DIRECTTOGARMENTSAMPLE) || (getOrderType() == PDFType.DIRECTTOGARMENTORDER)) {
      info.addCell(buzzcell("Processing Date:", this.cellfontbold));
      info.addCell(buzzcell(fixdate(currentorder.getVendorInformation().getDirectToGarmentVendor().getWorkOrderProcessingDate()), this.cellfont));
      
      info.addCell(buzzcell("Due Date:", this.cellfontbold));
      info.addCell(buzzcell(fixdate(currentorder.getVendorInformation().getDirectToGarmentVendor().getWorkOrderDueDate()), this.cellfont));
    } else if ((getOrderType() == PDFType.PATCHSAMPLE) || (getOrderType() == PDFType.PATCHORDER)) {
      info.addCell(buzzcell("Processing Date:", this.cellfontbold));
      info.addCell(buzzcell(fixdate(currentorder.getVendorInformation().getPatchVendor().getWorkOrderProcessingDate()), this.cellfont));
      
      info.addCell(buzzcell("Due Date:", this.cellfontbold));
      info.addCell(buzzcell(fixdate(currentorder.getVendorInformation().getPatchVendor().getWorkOrderDueDate()), this.cellfont));
    } else if ((getOrderType() == PDFType.OVERSEASSAMPLE) || (getOrderType() == PDFType.OVERSEASPURCHASEORDER)) {
      info.addCell(buzzcell("Processing Date:", this.cellfontbold));
      info.addCell(buzzcell(fixdate(((OrderDataVendorInformation)currentorder.getVendorInformation().getOverseasVendor().get(String.valueOf(getOverseasVendorNumber()))).getWorkOrderProcessingDate()), this.cellfont));
      
      info.addCell(buzzcell("Due Date:", this.cellfontbold));
      info.addCell(buzzcell(fixdate(((OrderDataVendorInformation)currentorder.getVendorInformation().getOverseasVendor().get(String.valueOf(getOverseasVendorNumber()))).getWorkOrderDueDate()), this.cellfont));
      
      info.addCell(buzzcell("Shipping Method:", this.cellfontbold));
      info.addCell(buzzcell(((OrderDataVendorInformation)currentorder.getVendorInformation().getOverseasVendor().get(String.valueOf(getOverseasVendorNumber()))).getShippingMethod(), this.cellfont));
    }
    
    body.addCell(paddedTableCell(info, 0));
    
    PdfPTable info2 = new PdfPTable(2);
    float[] info2Widths = { 50.0F, 50.0F };
    info2.setWidths(info2Widths);
    
    if (getOrderType() == PDFType.OVERSEASDIGITIZING) {
      info2.addCell(buzzcell("Purchase Order #:", this.cellfontbold));
      info2.addCell(buzzcell(((OrderDataVendorInformation)currentorder.getVendorInformation().getOverseasVendor().get(String.valueOf(getOverseasVendorNumber()))).getWorkOrderNumber(), this.cellfont));
    } else if (getOrderType() == PDFType.OVERSEASSAMPLE) {
      info2.addCell(buzzcell("Invoice #:", this.cellfontbold));
      info2.addCell(buzzcell(currentorder.getOrderNumber(), this.cellfont));
      info2.addCell(buzzcell("Total Order Cost:", this.cellfontbold));
      info2.addCell(buzzcell(NumberFormat.getCurrencyInstance().format(this.overseaspricecalc.getSamplePrice(getOverseasVendorNumber())), this.cellfont));
    } else if (getOrderType() == PDFType.OVERSEASPURCHASEORDER) {
      info2.addCell(buzzcell("Purchase Order #:", this.cellfontbold));
      info2.addCell(buzzcell(((OrderDataVendorInformation)currentorder.getVendorInformation().getOverseasVendor().get(String.valueOf(getOverseasVendorNumber()))).getWorkOrderNumber(), this.cellfont));
      info2.addCell(buzzcell("Invoice #:", this.cellfontbold));
      info2.addCell(buzzcell(currentorder.getOrderNumber(), this.cellfont));
      info2.addCell(buzzcell("Customer P.O. #:", this.cellfontbold));
      info2.addCell(buzzcell(currentorder.getCustomerPO(), this.cellfont));
      info2.addCell(buzzcell("Total Order Cost:", this.cellfontbold));
      info2.addCell(buzzcell(NumberFormat.getCurrencyInstance().format(this.overseaspricecalc.getVendorTotalCharge(getOverseasVendorNumber())), this.cellfont));
    } else {
      info2.addCell(buzzcell("Work Order #:", this.cellfontbold));
      info2.addCell(buzzcell(currentorder.getVendorInformation().getEmbroideryVendor().getWorkOrderNumber(), this.cellfont));
      if (getOrderType() == PDFType.DIGITIZING) {
        info2.addCell(buzzcell("Total Order Cost:", this.cellfontbold));
        info2.addCell(buzzcell(NumberFormat.getCurrencyInstance().format(this.domesticpricecalc.getDigitizingTotalCost()), this.cellfont));
      } else if ((getOrderType() == PDFType.EMBSAMPLE) || (getOrderType() == PDFType.HEATSAMPLE) || (getOrderType() == PDFType.SCREENSAMPLE) || (getOrderType() == PDFType.DIRECTTOGARMENTSAMPLE) || (getOrderType() == PDFType.PATCHSAMPLE)) {
        info2.addCell(buzzcell("Total Order Cost:", this.cellfontbold));
        info2.addCell(buzzcell(NumberFormat.getCurrencyInstance().format(this.domesticpricecalc.getSampleTotalCost()), this.cellfont));
      } else if ((getOrderType() == PDFType.HEATORDER) || (getOrderType() == PDFType.SCREENORDER) || (getOrderType() == PDFType.EMBORDER) || (getOrderType() == PDFType.DIRECTTOGARMENTORDER) || (getOrderType() == PDFType.PATCHORDER)) {
        info2.addCell(buzzcell("Total Order Cost:", this.cellfontbold));
        info2.addCell(buzzcell(NumberFormat.getCurrencyInstance().format(this.domesticpricecalc.getOrderTotalCost()), this.cellfont));
      }
    }
    
    info2.addCell(buzzcell("Rush Order:", this.cellfontbold));
    if (currentorder.getRushOrder()) {
      info2.addCell(buzzcell("Yes", this.cellfont));
    } else {
      info2.addCell(buzzcell("No", this.cellfont));
    }
    body.addCell(paddedTableCell(info2, 0));
    
    return headerBodyPanel(header, body);
  }
  
  private PdfPTable previewitemnotesapprovalPanel(ExtendOrderData currentorder, int item) throws Exception {
    PdfPTable mytable = new PdfPTable(3);
    float[] tableWidths = { 49.5F, 1.0F, 49.5F };
    mytable.setWidths(tableWidths);
    boolean notesshowing = false;
    float notestotalheight = 0.0F;
    boolean ccinfoshowing = false;
    
    if ((!currentorder.getSet(item).getItem(0).getComments().equals("")) || (getOrderType() == PDFType.APPROVAL) || (getOrderType() == PDFType.PRODUCTIONAPPROVAL) || (getOrderType() == PDFType.GENERIC) || (getOrderType() == PDFType.OVERSEASAPPROVAL) || (getOrderType() == PDFType.OVERSEASPRODUCTIONAPPROVAL) || (getOrderType() == PDFType.OVERSEASGENERIC)) {
      notesshowing = true;
      PdfPTable previewitemnotespanel = previewitemnotesPanel(currentorder, item);
      previewitemnotespanel.setTotalWidth(283.13998F);
      mytable.addCell(paddedTableCell(previewitemnotespanel, 0));
      notestotalheight = previewitemnotespanel.getTotalHeight();
    } else {
      mytable.addCell(buzzcell("", this.cellfont));
    }
    mytable.addCell(buzzcell("", this.cellfont));
    
    if ((getOrderType() == PDFType.APPROVAL) || (getOrderType() == PDFType.PRODUCTIONAPPROVAL) || (getOrderType() == PDFType.GENERIC) || (getOrderType() == PDFType.OVERSEASAPPROVAL) || (getOrderType() == PDFType.OVERSEASPRODUCTIONAPPROVAL) || (getOrderType() == PDFType.OVERSEASGENERIC)) {
      if (((getOrderType() == PDFType.APPROVAL) || (getOrderType() == PDFType.PRODUCTIONAPPROVAL) || (getOrderType() == PDFType.OVERSEASAPPROVAL) || (getOrderType() == PDFType.OVERSEASPRODUCTIONAPPROVAL)) && (
        (currentorder.getCustomerInformation().getTerms().trim().toLowerCase().equals("creditcard")) || (currentorder.getCustomerInformation().getTerms().trim().toLowerCase().equals("credit card")) || (currentorder.getCustomerInformation().getTerms().trim().toLowerCase().equals("cod")) || (currentorder.getCustomerInformation().getTerms().trim().toLowerCase().equals("prepaid")) || (currentorder.getCustomerInformation().getTerms().trim().toLowerCase().equals("cod1")))) {
        ccinfoshowing = true;
      }
      
      if (notesshowing) {
        PdfPTable spaceapprovaltable = new PdfPTable(1);
        PdfPCell emptycell = new PdfPCell();
        emptycell.setBorder(0);
        emptycell.setPadding(0.0F);
        
        PdfPTable myapprovalsign = approvalPanel();
        myapprovalsign.setTotalWidth(283.13998F);
        
        PdfPTable ccinfopanel = ccinfoPanel(currentorder);
        ccinfopanel.setTotalWidth(283.13998F);
        float remainingheight;
        float remainingheight;
        if (ccinfoshowing) {
          remainingheight = notestotalheight - ccinfopanel.getTotalHeight() - myapprovalsign.getTotalHeight();
        } else {
          remainingheight = notestotalheight - myapprovalsign.getTotalHeight();
        }
        if (remainingheight > 0.0F) {
          emptycell.setMinimumHeight(remainingheight);
        }
        spaceapprovaltable.addCell(emptycell);
        if (ccinfoshowing) {
          spaceapprovaltable.addCell(paddedTableCell(ccinfopanel, 0));
        }
        spaceapprovaltable.addCell(paddedTableCell(myapprovalsign, 0));
        
        mytable.addCell(paddedTableCell(spaceapprovaltable, 0));
      }
      else if (ccinfoshowing) {
        PdfPTable spaceapprovaltable = new PdfPTable(1);
        
        spaceapprovaltable.addCell(paddedTableCell(ccinfoPanel(currentorder), 0));
        spaceapprovaltable.addCell(paddedTableCell(approvalPanel(), 0));
        
        mytable.addCell(paddedTableCell(spaceapprovaltable, 0));
      } else {
        mytable.addCell(paddedTableCell(approvalPanel(), 0));
      }
    }
    else {
      mytable.addCell(buzzcell("", this.cellfont));
    }
    return mytable;
  }
  
  private PdfPTable previewitemnotesPanel(ExtendOrderData currentorder, int item) {
    PdfPTable header = new PdfPTable(1);
    PdfPCell billinformationcell = headercell("ITEM NOTES", this.headerfont);
    billinformationcell.setHorizontalAlignment(1);
    header.addCell(billinformationcell);
    
    PdfPTable body = new PdfPTable(1);
    
    PdfPTable info = new PdfPTable(1);
    




























    String privatelabeltext = "";
    if ((currentorder.getSet(item).getItem(0).getHasPrivateLabel() != null) && (currentorder.getSet(item).getItem(0).getHasPrivateLabel().booleanValue())) {
      privatelabeltext = privatelabeltext + "Private Label\n";
    }
    
    if ((getOrderType() == PDFType.OVERSEASSAMPLE) && (this.overseassamplevendorcalc.checkItemFree(item))) {
      info.addCell(buzzcell("Free Sample meets quantity minimum.\n" + currentorder.getSet(item).getItem(0).getComments(), this.cellfont));
    } else if (getOrderType() == PDFType.OVERSEASPURCHASEORDER) {
      info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getSampleApprovedList() + "\n" + privatelabeltext + currentorder.getSet(item).getItem(0).getComments(), this.cellfont));
    } else if (getOrderType() == PDFType.OVERSEASSAMPLE) {
      info.addCell(buzzcell(privatelabeltext + currentorder.getSet(item).getItem(0).getComments(), this.cellfont));
    } else {
      info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getComments(), this.cellfont));
    }
    
    body.addCell(paddedTableCell(info, 1));
    
    PdfPTable mytable = headerBodyPanel(header, body);
    return mytable;
  }
  
  private PdfPTable previewlogosPanel(ExtendOrderData currentorder, int item) throws Exception {
    PdfPTable header = new PdfPTable(1);
    PdfPCell billinformationcell = headercell("LOGO INFORMATION", this.headerfont);
    billinformationcell.setHorizontalAlignment(1);
    header.addCell(billinformationcell);
    
    PdfPTable body = new PdfPTable(1);
    
    if ((getOrderType() == PDFType.PRODUCTIONAPPROVAL) && ((currentorder.getSet(item).getItem(0).getProductSampleTotalDone() == null) || (currentorder.getSet(item).getItem(0).getProductSampleTotalDone().intValue() == 0))) {
      for (int i = 0; i < currentorder.getSet(item).getLogoCount(); i++) {
        if ((currentorder.getSet(item).getLogo(i).getSwatchTotalDone() != null) && (currentorder.getSet(item).getLogo(i).getSwatchTotalDone().intValue() > 0)) {
          PdfPCell mytablecell = paddedTableCell(previewlogoPanel(currentorder, item, i), 0);
          mytablecell.setPaddingBottom(10.0F);
          body.addCell(mytablecell);
          body.addCell(buzzcell("", this.cellfont));
        }
      }
    } else if ((getOrderType() == PDFType.OVERSEASPRODUCTIONAPPROVAL) && ((currentorder.getSet(item).getItem(0).getProductSampleTotalEmail() == null) || (currentorder.getSet(item).getItem(0).getProductSampleTotalEmail().intValue() == 0)) && ((currentorder.getSet(item).getItem(0).getProductSampleTotalShip() == null) || (currentorder.getSet(item).getItem(0).getProductSampleTotalShip().intValue() == 0))) {
      for (int i = 0; i < currentorder.getSet(item).getLogoCount(); i++) {
        if (((currentorder.getSet(item).getLogo(i).getSwatchTotalEmail() != null) && (currentorder.getSet(item).getLogo(i).getSwatchTotalEmail().intValue() > 0)) || ((currentorder.getSet(item).getLogo(i).getSwatchTotalShip() != null) && (currentorder.getSet(item).getLogo(i).getSwatchTotalShip().intValue() > 0))) {
          PdfPCell mytablecell = paddedTableCell(previewlogoPanel(currentorder, item, i), 0);
          mytablecell.setPaddingBottom(10.0F);
          body.addCell(mytablecell);
          body.addCell(buzzcell("", this.cellfont));
        }
      }
    } else if (getOrderType() == PDFType.DIGITIZING) {
      for (int i = 0; i < currentorder.getSet(item).getLogoCount(); i++) {
        if ((currentorder.getSet(item).getLogo(i).getDigitizing()) || (currentorder.getSet(item).getLogo(i).getTapeEdit())) {
          PdfPCell mytablecell = paddedTableCell(previewlogoPanel(currentorder, item, i), 0);
          mytablecell.setPaddingBottom(10.0F);
          body.addCell(mytablecell);
          body.addCell(buzzcell("", this.cellfont));
        }
      }
    } else if (getOrderType() == PDFType.OVERSEASDIGITIZING) {
      for (int i = 0; i < currentorder.getSet(item).getLogoCount(); i++) {
        if (currentorder.getSet(item).getLogo(i).getDigitizing()) {
          PdfPCell mytablecell = paddedTableCell(previewlogoPanel(currentorder, item, i), 0);
          mytablecell.setPaddingBottom(10.0F);
          body.addCell(mytablecell);
          body.addCell(buzzcell("", this.cellfont));
        }
      }
    } else if ((getOrderType() == PDFType.EMBSAMPLE) || (getOrderType() == PDFType.HEATSAMPLE) || (getOrderType() == PDFType.SCREENSAMPLE) || (getOrderType() == PDFType.DIRECTTOGARMENTSAMPLE) || (getOrderType() == PDFType.PATCHSAMPLE)) {
      for (int i = 0; i < currentorder.getSet(item).getLogoCount(); i++) {
        int vendornumber = 0;
        if (getOrderType() == PDFType.EMBSAMPLE) {
          vendornumber = currentorder.getVendorInformation().getEmbroideryVendor().getVendor().intValue();
        } else if (getOrderType() == PDFType.HEATSAMPLE) {
          vendornumber = currentorder.getVendorInformation().getHeatTransferVendor().getVendor().intValue();
        } else if (getOrderType() == PDFType.SCREENSAMPLE) {
          vendornumber = currentorder.getVendorInformation().getScreenPrintVendor().getVendor().intValue();
        } else if (getOrderType() == PDFType.DIRECTTOGARMENTSAMPLE) {
          vendornumber = currentorder.getVendorInformation().getDirectToGarmentVendor().getVendor().intValue();
        } else if (getOrderType() == PDFType.PATCHSAMPLE) {
          vendornumber = currentorder.getVendorInformation().getPatchVendor().getVendor().intValue();
        }
        if ((vendornumber == currentorder.getVendorInformation().getEmbroideryVendor().getVendor().intValue()) && ((currentorder.getSet(item).getLogo(i).getDecoration().equals("Flat Embroidery")) || (currentorder.getSet(item).getLogo(i).getDecoration().equals("3D Embroidery"))) && (((currentorder.getSet(item).getItem(0).getProductSampleToDo() != null) && (currentorder.getSet(item).getItem(0).getProductSampleToDo().intValue() > 0)) || ((currentorder.getSet(item).getLogo(i).getSwatchToDo() != null) && (currentorder.getSet(item).getLogo(i).getSwatchToDo().intValue() > 0)))) {
          PdfPCell mytablecell = paddedTableCell(previewlogoPanel(currentorder, item, i), 0);
          mytablecell.setPaddingBottom(10.0F);
          body.addCell(mytablecell);
          body.addCell(buzzcell("", this.cellfont));
        } else if ((vendornumber == currentorder.getVendorInformation().getHeatTransferVendor().getVendor().intValue()) && (currentorder.getSet(item).getLogo(i).getDecoration().equals("Heat Transfer")) && (((currentorder.getSet(item).getItem(0).getProductSampleToDo() != null) && (currentorder.getSet(item).getItem(0).getProductSampleToDo().intValue() > 0)) || ((currentorder.getSet(item).getLogo(i).getSwatchToDo() != null) && (currentorder.getSet(item).getLogo(i).getSwatchToDo().intValue() > 0)))) {
          PdfPCell mytablecell = paddedTableCell(previewlogoPanel(currentorder, item, i), 0);
          mytablecell.setPaddingBottom(10.0F);
          body.addCell(mytablecell);
          body.addCell(buzzcell("", this.cellfont));
        } else if ((vendornumber == currentorder.getVendorInformation().getScreenPrintVendor().getVendor().intValue()) && ((currentorder.getSet(item).getLogo(i).getDecoration().equals("Screen Print")) || (currentorder.getSet(item).getLogo(i).getDecoration().equals("Four Color Screen Print"))) && (((currentorder.getSet(item).getItem(0).getProductSampleToDo() != null) && (currentorder.getSet(item).getItem(0).getProductSampleToDo().intValue() > 0)) || ((currentorder.getSet(item).getLogo(i).getSwatchToDo() != null) && (currentorder.getSet(item).getLogo(i).getSwatchToDo().intValue() > 0)))) {
          PdfPCell mytablecell = paddedTableCell(previewlogoPanel(currentorder, item, i), 0);
          mytablecell.setPaddingBottom(10.0F);
          body.addCell(mytablecell);
          body.addCell(buzzcell("", this.cellfont));
        } else if ((vendornumber == currentorder.getVendorInformation().getDirectToGarmentVendor().getVendor().intValue()) && (currentorder.getSet(item).getLogo(i).getDecoration().equals("Direct To Garment")) && (((currentorder.getSet(item).getItem(0).getProductSampleToDo() != null) && (currentorder.getSet(item).getItem(0).getProductSampleToDo().intValue() > 0)) || ((currentorder.getSet(item).getLogo(i).getSwatchToDo() != null) && (currentorder.getSet(item).getLogo(i).getSwatchToDo().intValue() > 0)))) {
          PdfPCell mytablecell = paddedTableCell(previewlogoPanel(currentorder, item, i), 0);
          mytablecell.setPaddingBottom(10.0F);
          body.addCell(mytablecell);
          body.addCell(buzzcell("", this.cellfont));
        } else if ((vendornumber == currentorder.getVendorInformation().getPatchVendor().getVendor().intValue()) && (currentorder.getSet(item).getLogo(i).getDecoration().equals("Patch")) && (((currentorder.getSet(item).getItem(0).getProductSampleToDo() != null) && (currentorder.getSet(item).getItem(0).getProductSampleToDo().intValue() > 0)) || ((currentorder.getSet(item).getLogo(i).getSwatchToDo() != null) && (currentorder.getSet(item).getLogo(i).getSwatchToDo().intValue() > 0)))) {
          PdfPCell mytablecell = paddedTableCell(previewlogoPanel(currentorder, item, i), 0);
          mytablecell.setPaddingBottom(10.0F);
          body.addCell(mytablecell);
          body.addCell(buzzcell("", this.cellfont));
        } else if ((vendornumber == currentorder.getVendorInformation().getEmbroideryVendor().getVendor().intValue()) && (((currentorder.getSet(item).getItem(0).getProductSampleToDo() != null) && (currentorder.getSet(item).getItem(0).getProductSampleToDo().intValue() > 0)) || ((currentorder.getSet(item).getLogo(i).getSwatchToDo() != null) && (currentorder.getSet(item).getLogo(i).getSwatchToDo().intValue() > 0)))) {
          PdfPCell mytablecell = paddedTableCell(previewlogoPanel(currentorder, item, i), 0);
          mytablecell.setPaddingBottom(10.0F);
          body.addCell(mytablecell);
          body.addCell(buzzcell("", this.cellfont));
        }
      }
    } else if (getOrderType() == PDFType.OVERSEASSAMPLE) {
      for (int i = 0; i < currentorder.getSet(item).getLogoCount(); i++) {
        if ((getOrderType() == PDFType.OVERSEASSAMPLE) && (((currentorder.getSet(item).getItem(0).getProductSampleToDo() != null) && (currentorder.getSet(item).getItem(0).getProductSampleToDo().intValue() > 0)) || ((currentorder.getSet(item).getLogo(i).getSwatchToDo() != null) && (currentorder.getSet(item).getLogo(i).getSwatchToDo().intValue() > 0)))) {
          PdfPCell mytablecell = paddedTableCell(previewlogoPanel(currentorder, item, i), 0);
          mytablecell.setPaddingBottom(10.0F);
          body.addCell(mytablecell);
          body.addCell(buzzcell("", this.cellfont));
        }
      }
    } else if ((getOrderType() == PDFType.EMBORDER) || (getOrderType() == PDFType.HEATORDER) || (getOrderType() == PDFType.SCREENORDER) || (getOrderType() == PDFType.DIRECTTOGARMENTORDER) || (getOrderType() == PDFType.PATCHORDER)) {
      for (int i = 0; i < currentorder.getSet(item).getLogoCount(); i++) {
        int vendornumber = 0;
        if (getOrderType() == PDFType.EMBORDER) {
          vendornumber = currentorder.getVendorInformation().getEmbroideryVendor().getVendor().intValue();
        } else if (getOrderType() == PDFType.HEATORDER) {
          vendornumber = currentorder.getVendorInformation().getHeatTransferVendor().getVendor().intValue();
        } else if (getOrderType() == PDFType.SCREENORDER) {
          vendornumber = currentorder.getVendorInformation().getScreenPrintVendor().getVendor().intValue();
        } else if (getOrderType() == PDFType.DIRECTTOGARMENTORDER) {
          vendornumber = currentorder.getVendorInformation().getDirectToGarmentVendor().getVendor().intValue();
        } else if (getOrderType() == PDFType.PATCHORDER) {
          vendornumber = currentorder.getVendorInformation().getPatchVendor().getVendor().intValue();
        }
        if ((currentorder.getVendorInformation().getEmbroideryVendor().getVendor() != null) && (currentorder.getVendorInformation().getEmbroideryVendor().getVendor().intValue() == vendornumber) && ((currentorder.getSet(item).getLogo(i).getDecoration().equals("Flat Embroidery")) || (currentorder.getSet(item).getLogo(i).getDecoration().equals("3D Embroidery")))) {
          PdfPCell mytablecell = paddedTableCell(previewlogoPanel(currentorder, item, i), 0);
          mytablecell.setPaddingBottom(10.0F);
          body.addCell(mytablecell);
          body.addCell(buzzcell("", this.cellfont));
        } else if ((currentorder.getVendorInformation().getHeatTransferVendor().getVendor() != null) && (currentorder.getVendorInformation().getHeatTransferVendor().getVendor().intValue() == vendornumber) && (currentorder.getSet(item).getLogo(i).getDecoration().equals("Heat Transfer"))) {
          PdfPCell mytablecell = paddedTableCell(previewlogoPanel(currentorder, item, i), 0);
          mytablecell.setPaddingBottom(10.0F);
          body.addCell(mytablecell);
          body.addCell(buzzcell("", this.cellfont));
        } else if ((currentorder.getVendorInformation().getScreenPrintVendor().getVendor() != null) && (currentorder.getVendorInformation().getScreenPrintVendor().getVendor().intValue() == vendornumber) && ((currentorder.getSet(item).getLogo(i).getDecoration().equals("Screen Print")) || (currentorder.getSet(item).getLogo(i).getDecoration().equals("Four Color Screen Print")))) {
          PdfPCell mytablecell = paddedTableCell(previewlogoPanel(currentorder, item, i), 0);
          mytablecell.setPaddingBottom(10.0F);
          body.addCell(mytablecell);
          body.addCell(buzzcell("", this.cellfont));
        } else if ((((currentorder.getVendorInformation().getDirectToGarmentVendor().getVendor() != null ? 1 : 0) & (currentorder.getVendorInformation().getDirectToGarmentVendor().getVendor().intValue() == vendornumber ? 1 : 0)) != 0) && (currentorder.getSet(item).getLogo(i).getDecoration().equals("Direct To Garment"))) {
          PdfPCell mytablecell = paddedTableCell(previewlogoPanel(currentorder, item, i), 0);
          mytablecell.setPaddingBottom(10.0F);
          body.addCell(mytablecell);
          body.addCell(buzzcell("", this.cellfont));
        } else if ((((currentorder.getVendorInformation().getPatchVendor().getVendor() != null ? 1 : 0) & (currentorder.getVendorInformation().getPatchVendor().getVendor().intValue() == vendornumber ? 1 : 0)) != 0) && (currentorder.getSet(item).getLogo(i).getDecoration().equals("Patch"))) {
          PdfPCell mytablecell = paddedTableCell(previewlogoPanel(currentorder, item, i), 0);
          mytablecell.setPaddingBottom(10.0F);
          body.addCell(mytablecell);
          body.addCell(buzzcell("", this.cellfont));
        } else if ((currentorder.getVendorInformation().getEmbroideryVendor().getVendor() != null) && (currentorder.getVendorInformation().getEmbroideryVendor().getVendor().intValue() == vendornumber) && (!currentorder.getSet(item).getLogo(i).getDecoration().equals("Flat Embroidery")) && (!currentorder.getSet(item).getLogo(i).getDecoration().equals("3D Embroidery")) && (!currentorder.getSet(item).getLogo(i).getDecoration().equals("Heat Transfer")) && (!currentorder.getSet(item).getLogo(i).getDecoration().equals("Screen Print")) && (!currentorder.getSet(item).getLogo(i).getDecoration().equals("Four Color Screen Print")) && (!currentorder.getSet(item).getLogo(i).getDecoration().equals("Direct To Garment"))) {
          PdfPCell mytablecell = paddedTableCell(previewlogoPanel(currentorder, item, i), 0);
          mytablecell.setPaddingBottom(10.0F);
          body.addCell(mytablecell);
          body.addCell(buzzcell("", this.cellfont));
        }
      }
    } else {
      for (int i = 0; i < currentorder.getSet(item).getLogoCount(); i++) {
        PdfPCell mytablecell = paddedTableCell(previewlogoPanel(currentorder, item, i), 0);
        mytablecell.setPaddingBottom(10.0F);
        body.addCell(mytablecell);
        body.addCell(buzzcell("", this.cellfont));
      }
    }
    
    PdfPTable mytable = headerBodyPanel(header, body);
    return mytable;
  }
  
  private PdfPTable previewlogoPanel(ExtendOrderData currentorder, int item, int logo) throws Exception {
    PdfPTable info = new PdfPTable(3);
    info.setSplitLate(false);
    float[] infoWidths = { 20.0F, 30.0F, 50.0F };
    info.setWidths(infoWidths);
    PdfPCell logopicture;
    PdfPCell logopicture;
    if (!currentorder.getSet(item).getLogo(logo).getFilename().equals("")) {
      Image logopreview;
      Image logopreview;
      if (currentorder.getSet(item).getLogo(logo).getFilename().toLowerCase().endsWith(".dst")) {
        String samedstimage = currentorder.getSet(item).getLogo(logo).getFilename() + currentorder.getSet(item).getLogo(logo).getThreadBrand();
        for (int i = 0; i < currentorder.getSet(item).getLogo(logo).getColorwayCount(); i++) {
          samedstimage = samedstimage + currentorder.getSet(item).getLogo(logo).getColorway(i).getConvertCode();
        }
        logopreview = this.sameimages.getDSTImage(samedstimage, currentorder, currentorder.getSet(item).getLogo(logo), 300, 180);
      } else {
        logopreview = this.sameimages.getImage(currentorder.getSet(item).getLogo(logo).getFilename(), "C:\\WorkFlow\\NewWorkflowData\\" + currentorder.getOrderTypeFolder() + "Data/" + currentorder.getHiddenKey() + "/logos/" + currentorder.getSet(item).getLogo(logo).getFilename(), 300, 180);
      }
      
      logopreview.scaleToFit(100.0F, 60.0F);
      logopicture = new PdfPCell(logopreview, false);
    } else { PdfPCell logopicture;
      if ((currentorder.getOrderType() == OrderData.OrderType.OVERSEAS) && (!currentorder.getSet(item).getLogo(logo).getDstFilename().equals(""))) {
        String samedstimage = currentorder.getSet(item).getLogo(logo).getDstFilename() + currentorder.getSet(item).getLogo(logo).getThreadBrand();
        for (int i = 0; i < currentorder.getSet(item).getLogo(logo).getColorwayCount(); i++) {
          samedstimage = samedstimage + currentorder.getSet(item).getLogo(logo).getColorway(i).getConvertCode();
        }
        Image logopreview = this.sameimages.getDSTImage(samedstimage, currentorder, currentorder.getSet(item).getLogo(logo), 300, 180);
        logopreview.scaleToFit(100.0F, 60.0F);
        logopicture = new PdfPCell(logopreview, false);
      } else {
        logopicture = new PdfPCell(new Phrase(" ", this.cellfontbold));
      }
    }
    
    logopicture.setBorder(0);
    logopicture.setMinimumHeight(60.0F);
    logopicture.setHorizontalAlignment(1);
    logopicture.setVerticalAlignment(5);
    info.addCell(logopicture);
    
    PdfPTable logoinfotable = new PdfPTable(2);
    logoinfotable.setSplitLate(false);
    
    if (getOrderType() == PDFType.DIGITIZING) {
      logoinfotable.addCell(buzzcell("Job Type:", this.cellfontbold));
      if (currentorder.getSet(item).getLogo(logo).getDigitizing()) {
        logoinfotable.addCell(buzzcell("Digitizing", this.cellfont));
      } else if (currentorder.getSet(item).getLogo(logo).getTapeEdit()) {
        logoinfotable.addCell(buzzcell("Tape Edit", this.cellfont));
      }
    }
    
    logoinfotable.addCell(buzzcell("Logo #:", this.cellfontbold));
    logoinfotable.addCell(buzzcell(String.valueOf(logo + 1), this.cellfont));
    
    if (currentorder.getOrderType() == OrderData.OrderType.DOMESTIC) {
      logoinfotable.addCell(buzzcell("Logo Name:", this.cellfontbold));
      logoinfotable.addCell(buzzcell(currentorder.getSet(item).getLogo(logo).getFilename(), this.cellfont));
    } else {
      logoinfotable.addCell(buzzcell("Logo Name:", this.cellfontbold));
      if ((currentorder.getSet(item).getLogo(logo).getFilename().equals("")) && (!currentorder.getSet(item).getLogo(logo).getDstFilename().equals(""))) {
        logoinfotable.addCell(buzzcell(currentorder.getSet(item).getLogo(logo).getDstFilename(), this.cellfont));
      } else {
        logoinfotable.addCell(buzzcell(currentorder.getSet(item).getLogo(logo).getFilename(), this.cellfont));
      }
    }
    
    logoinfotable.addCell(buzzcell("Logo Size:", this.cellfontbold));
    logoinfotable.addCell(buzzcell(currentorder.getSet(item).getLogo(logo).getLogoSize(), this.cellfont));
    
    logoinfotable.addCell(buzzcell("Location #:", this.cellfontbold));
    logoinfotable.addCell(buzzcell(currentorder.getSet(item).getLogo(logo).getLogoLocationName(), this.cellfont));
    
    if ((currentorder.getSet(item).getLogo(logo).getNumberOfColor() != null) && (currentorder.getSet(item).getLogo(logo).getNumberOfColor().intValue() > 0)) {
      logoinfotable.addCell(buzzcell("# of Colors:", this.cellfontbold));
      logoinfotable.addCell(buzzcell(String.valueOf(currentorder.getSet(item).getLogo(logo).getNumberOfColor()), this.cellfont));
    }
    
    if ((currentorder.getSet(item).getLogo(logo).getStitchCount() != null) && (currentorder.getSet(item).getLogo(logo).getStitchCount().intValue() > 0)) {
      logoinfotable.addCell(buzzcell("Stitch Count:", this.cellfontbold));
      logoinfotable.addCell(buzzcell(String.valueOf(currentorder.getSet(item).getLogo(logo).getStitchCount()), this.cellfont));
    }
    
    if (currentorder.getOrderType() == OrderData.OrderType.DOMESTIC) {
      logoinfotable.addCell(buzzcell("Decoration Type:", this.cellfontbold));
      logoinfotable.addCell(buzzcell(currentorder.getSet(item).getLogo(logo).getDecoration(), this.cellfont));
    } else {
      for (int i = 0; i < currentorder.getSet(item).getLogo(logo).getDecorationCount().intValue(); i++) {
        logoinfotable.addCell(buzzcell("Decoration Type:", this.cellfontbold));
        logoinfotable.addCell(buzzcell(currentorder.getSet(item).getLogo(logo).getDecoration(Integer.valueOf(i)).getName(), this.cellfont));
        
        if ((currentorder.getSet(item).getLogo(logo).getDecoration(Integer.valueOf(i)).getField1() != null) && (currentorder.getSet(item).getLogo(logo).getDecoration(Integer.valueOf(i)).getField1().doubleValue() > 0.0D)) {
          PdfPCell field1cell = buzzcell(currentorder.getSet(item).getLogo(logo).getDecoration(Integer.valueOf(i)).getField1Name() + " - " + currentorder.getSet(item).getLogo(logo).getDecoration(Integer.valueOf(i)).getField1(), this.cellfont);
          field1cell.setColspan(2);
          field1cell.setPaddingLeft(10.0F);
          if (((getOrderType() != PDFType.OVERSEASGENERIC) && (getOrderType() != PDFType.OVERSEASQUOTATION) && (getOrderType() != PDFType.OVERSEASAPPROVAL) && (getOrderType() != PDFType.OVERSEASPRODUCTIONAPPROVAL)) || (!currentorder.getSet(item).getLogo(logo).getDecoration(Integer.valueOf(i)).getField1Name().equals("FOB Cost"))) {
            logoinfotable.addCell(field1cell);
          }
        }
        if ((currentorder.getSet(item).getLogo(logo).getDecoration(Integer.valueOf(i)).getField2() != null) && (currentorder.getSet(item).getLogo(logo).getDecoration(Integer.valueOf(i)).getField2().doubleValue() > 0.0D)) {
          PdfPCell field2cell = buzzcell(currentorder.getSet(item).getLogo(logo).getDecoration(Integer.valueOf(i)).getField2Name() + " - " + currentorder.getSet(item).getLogo(logo).getDecoration(Integer.valueOf(i)).getField2(), this.cellfont);
          field2cell.setColspan(2);
          field2cell.setPaddingLeft(10.0F);
          if (((getOrderType() != PDFType.OVERSEASGENERIC) && (getOrderType() != PDFType.OVERSEASQUOTATION) && (getOrderType() != PDFType.OVERSEASAPPROVAL) && (getOrderType() != PDFType.OVERSEASPRODUCTIONAPPROVAL)) || (!currentorder.getSet(item).getLogo(logo).getDecoration(Integer.valueOf(i)).getField2Name().equals("Setup Charge"))) {
            logoinfotable.addCell(field2cell);
          }
        }
        if ((currentorder.getSet(item).getLogo(logo).getDecoration(Integer.valueOf(i)).getField3() != null) && (currentorder.getSet(item).getLogo(logo).getDecoration(Integer.valueOf(i)).getField3().doubleValue() > 0.0D)) {
          PdfPCell field3cell = buzzcell(currentorder.getSet(item).getLogo(logo).getDecoration(Integer.valueOf(i)).getField3Name() + " - " + currentorder.getSet(item).getLogo(logo).getDecoration(Integer.valueOf(i)).getField3(), this.cellfont);
          field3cell.setColspan(2);
          field3cell.setPaddingLeft(10.0F);
          logoinfotable.addCell(field3cell);
        }
        if ((currentorder.getSet(item).getLogo(logo).getDecoration(Integer.valueOf(i)).getField4() != null) && (currentorder.getSet(item).getLogo(logo).getDecoration(Integer.valueOf(i)).getField4().doubleValue() > 0.0D)) {
          PdfPCell field4cell = buzzcell(currentorder.getSet(item).getLogo(logo).getDecoration(Integer.valueOf(i)).getField4Name() + " - " + currentorder.getSet(item).getLogo(logo).getDecoration(Integer.valueOf(i)).getField4(), this.cellfont);
          field4cell.setColspan(2);
          field4cell.setPaddingLeft(10.0F);
          logoinfotable.addCell(field4cell);
        }
        if (!currentorder.getSet(item).getLogo(logo).getDecoration(Integer.valueOf(i)).getComments().equals("")) {
          PdfPCell field4cell = buzzcell("Comments - " + currentorder.getSet(item).getLogo(logo).getDecoration(Integer.valueOf(i)).getComments(), this.cellfont);
          field4cell.setColspan(2);
          field4cell.setPaddingLeft(10.0F);
          logoinfotable.addCell(field4cell);
        }
      }
    }
    
    if ((getOrderType() == PDFType.EMBSAMPLE) || (getOrderType() == PDFType.HEATSAMPLE) || (getOrderType() == PDFType.SCREENSAMPLE) || (getOrderType() == PDFType.DIRECTTOGARMENTSAMPLE) || (getOrderType() == PDFType.PATCHSAMPLE) || (getOrderType() == PDFType.OVERSEASSAMPLE)) {
      String extrainfo = "";
      if ((currentorder.getSet(item).getLogo(logo).getSwatchShip()) && (currentorder.getSet(item).getLogo(logo).getSwatchEmail())) {
        extrainfo = "email/ship";
      } else if (currentorder.getSet(item).getLogo(logo).getSwatchShip()) {
        extrainfo = "ship only";
      } else if (currentorder.getSet(item).getLogo(logo).getSwatchEmail()) {
        extrainfo = "email only";
      }
      if ((currentorder.getSet(item).getLogo(logo).getSwatchToDo() != null) && (currentorder.getSet(item).getLogo(logo).getSwatchToDo().intValue() > 0)) {
        logoinfotable.addCell(buzzcell("Quantity:", this.cellfontbold));
        if (currentorder.getSet(item).getLogo(logo).getSwatchToDo().intValue() > 1) {
          logoinfotable.addCell(buzzcell(currentorder.getSet(item).getLogo(logo).getSwatchToDo() + " swatches " + extrainfo, this.cellfont));
        } else {
          logoinfotable.addCell(buzzcell(currentorder.getSet(item).getLogo(logo).getSwatchToDo() + " swatch " + extrainfo, this.cellfont));
        }
      }
    }
    

    info.addCell(paddedTableCell(logoinfotable, 0));
    
    PdfPTable colorlogotable = new PdfPTable(1);
    colorlogotable.setSplitLate(false);
    
    if (currentorder.getSet(item).getLogo(logo).getColorwayCount() > 0)
    {
      PdfPCell previewcolorway = paddedTableCell(previewcolorwayPanel(currentorder, item, logo), 0);
      previewcolorway.setPaddingBottom(5.0F);
      colorlogotable.addCell(previewcolorway);
    }
    
    if (!currentorder.getSet(item).getLogo(logo).getColorDescription().equals("")) {
      PdfPTable logodescriptiontable = new PdfPTable(1);
      logodescriptiontable.setSplitLate(false);
      logodescriptiontable.addCell(buzzcell("Logo Description", this.cellfontbold));
      logodescriptiontable.setHeaderRows(1);
      PdfPCell logodescriptioncell = buzzcell(currentorder.getSet(item).getLogo(logo).getColorDescription(), this.cellfont);
      logodescriptioncell.setPaddingLeft(10.0F);
      logodescriptiontable.addCell(logodescriptioncell);
      
      colorlogotable.addCell(paddedTableCell(logodescriptiontable, 0));
    }
    
    info.addCell(paddedTableCell(colorlogotable, 0));
    
    return info;
  }
  
  private PdfPTable previewcolorwayPanel(ExtendOrderData currentorder, int item, int logo) throws Exception
  {
    if (((currentorder.getSet(item).getLogo(logo).getFilename() != null) && (currentorder.getSet(item).getLogo(logo).getFilename().toLowerCase().endsWith(".dst"))) || ((currentorder.getSet(item).getLogo(logo).getDstFilename() != null) && (currentorder.getSet(item).getLogo(logo).getDstFilename().toLowerCase().endsWith(".dst")))) {
      PdfPTable colorwaytable = new PdfPTable(5);
      colorwaytable.setSplitLate(false);
      float[] colorwaytableWidths = { 5.0F, 13.0F, 24.0F, 25.0F, 33.0F };
      colorwaytable.setWidths(colorwaytableWidths);
      
      colorwaytable.addCell(buzzcell("#", this.cellfontbold));
      colorwaytable.addCell(buzzcell("Stitches", this.cellfontbold));
      colorwaytable.addCell(buzzcell(currentorder.getSet(item).getLogo(logo).getThreadBrand(), this.cellfontbold));
      colorwaytable.addCell(buzzcell("Color", this.cellfontbold));
      colorwaytable.addCell(buzzcell("Sequence Description", this.cellfontbold));
      colorwaytable.setHeaderRows(1);
      
      for (int i = 0; i < currentorder.getSet(item).getLogo(logo).getColorwayCount(); i++) {
        colorwaytable.addCell(buzzcell(String.valueOf(i + 1), this.cellfont));
        colorwaytable.addCell(buzzcell(String.valueOf(currentorder.getSet(item).getLogo(logo).getColorway(i).getStitches()), this.cellfont));
        colorwaytable.addCell(buzzcell(currentorder.getSet(item).getLogo(logo).getColorway(i).getConvertCode(), this.cellfont));
        colorwaytable.addCell(buzzcell(currentorder.getSet(item).getLogo(logo).getColorway(i).getConvertColorName(), this.cellfont));
        colorwaytable.addCell(buzzcell(currentorder.getSet(item).getLogo(logo).getColorway(i).getColorCodeComment(), this.cellfont));
      }
      return colorwaytable; }
    if ((currentorder.getSet(item).getLogo(logo).getDecoration() != null) && (currentorder.getSet(item).getLogo(logo).getDecoration().equals("Screen Print"))) {
      PdfPTable colorwaytable = new PdfPTable(7);
      colorwaytable.setSplitLate(false);
      float[] colorwaytableWidths = { 5.0F, 15.0F, 10.0F, 17.0F, 13.0F, 20.0F, 20.0F };
      colorwaytable.setWidths(colorwaytableWidths);
      
      colorwaytable.addCell(buzzcell("#", this.cellfontbold));
      colorwaytable.addCell(buzzcell("Ink Type", this.cellfontbold));
      colorwaytable.addCell(buzzcell("Flash Charge", this.cellfontbold));
      colorwaytable.addCell(buzzcell("Brand", this.cellfontbold));
      colorwaytable.addCell(buzzcell("Code", this.cellfontbold));
      colorwaytable.addCell(buzzcell("Color", this.cellfontbold));
      colorwaytable.addCell(buzzcell("Description", this.cellfontbold));
      colorwaytable.setHeaderRows(1);
      
      for (int i = 0; i < currentorder.getSet(item).getLogo(logo).getColorwayCount(); i++) {
        colorwaytable.addCell(buzzcell(String.valueOf(i + 1), this.cellfont));
        colorwaytable.addCell(buzzcell(currentorder.getSet(item).getLogo(logo).getColorway(i).getInkType(), this.cellfont));
        colorwaytable.addCell(buzzcell(currentorder.getSet(item).getLogo(logo).getColorway(i).getFlashCharge() ? "Yes" : "", this.cellfont));
        colorwaytable.addCell(buzzcell(currentorder.getSet(item).getLogo(logo).getColorway(i).getThreadType(), this.cellfont));
        colorwaytable.addCell(buzzcell(currentorder.getSet(item).getLogo(logo).getColorway(i).getLogoColorCode(), this.cellfont));
        colorwaytable.addCell(buzzcell(currentorder.getSet(item).getLogo(logo).getColorway(i).getLogoColorCodeName(), this.cellfont));
        colorwaytable.addCell(buzzcell(currentorder.getSet(item).getLogo(logo).getColorway(i).getColorCodeComment(), this.cellfont));
      }
      return colorwaytable;
    }
    if ((currentorder.getSet(item).getLogo(logo).getDecoration() != null) && (currentorder.getSet(item).getLogo(logo).getDecoration().equals("Heat Transfer"))) {
      PdfPTable colorwaytable = new PdfPTable(6);
      colorwaytable.setSplitLate(false);
      float[] colorwaytableWidths = { 5.0F, 23.0F, 14.0F, 15.0F, 23.0F, 20.0F };
      colorwaytable.setWidths(colorwaytableWidths);
      
      colorwaytable.addCell(buzzcell("#", this.cellfontbold));
      colorwaytable.addCell(buzzcell("Color Garment Charge", this.cellfontbold));
      colorwaytable.addCell(buzzcell("Brand", this.cellfontbold));
      colorwaytable.addCell(buzzcell("Code", this.cellfontbold));
      colorwaytable.addCell(buzzcell("Color", this.cellfontbold));
      colorwaytable.addCell(buzzcell("Description", this.cellfontbold));
      colorwaytable.setHeaderRows(1);
      
      for (int i = 0; i < currentorder.getSet(item).getLogo(logo).getColorwayCount(); i++) {
        colorwaytable.addCell(buzzcell(String.valueOf(i + 1), this.cellfont));
        colorwaytable.addCell(buzzcell(currentorder.getSet(item).getLogo(logo).getColorway(i).getFlashCharge() ? "Yes" : "", this.cellfont));
        colorwaytable.addCell(buzzcell(currentorder.getSet(item).getLogo(logo).getColorway(i).getThreadType(), this.cellfont));
        colorwaytable.addCell(buzzcell(currentorder.getSet(item).getLogo(logo).getColorway(i).getLogoColorCode(), this.cellfont));
        colorwaytable.addCell(buzzcell(currentorder.getSet(item).getLogo(logo).getColorway(i).getLogoColorCodeName(), this.cellfont));
        colorwaytable.addCell(buzzcell(currentorder.getSet(item).getLogo(logo).getColorway(i).getColorCodeComment(), this.cellfont));
      }
      return colorwaytable;
    }
    PdfPTable colorwaytable = new PdfPTable(5);
    colorwaytable.setSplitLate(false);
    float[] colorwaytableWidths = { 5.0F, 23.0F, 14.0F, 25.0F, 33.0F };
    colorwaytable.setWidths(colorwaytableWidths);
    
    colorwaytable.addCell(buzzcell("#", this.cellfontbold));
    colorwaytable.addCell(buzzcell("Brand", this.cellfontbold));
    colorwaytable.addCell(buzzcell("Code", this.cellfontbold));
    colorwaytable.addCell(buzzcell("Color", this.cellfontbold));
    colorwaytable.addCell(buzzcell("Sequence Description", this.cellfontbold));
    colorwaytable.setHeaderRows(1);
    
    for (int i = 0; i < currentorder.getSet(item).getLogo(logo).getColorwayCount(); i++) {
      colorwaytable.addCell(buzzcell(String.valueOf(i + 1), this.cellfont));
      colorwaytable.addCell(buzzcell(currentorder.getSet(item).getLogo(logo).getColorway(i).getThreadType(), this.cellfont));
      colorwaytable.addCell(buzzcell(currentorder.getSet(item).getLogo(logo).getColorway(i).getLogoColorCode(), this.cellfont));
      colorwaytable.addCell(buzzcell(currentorder.getSet(item).getLogo(logo).getColorway(i).getLogoColorCodeName(), this.cellfont));
      colorwaytable.addCell(buzzcell(currentorder.getSet(item).getLogo(logo).getColorway(i).getColorCodeComment(), this.cellfont));
    }
    return colorwaytable;
  }
  
  private PdfPTable previewitemPanel(ExtendOrderData currentorder, int item)
    throws Exception
  {
    PdfPTable header = new PdfPTable(1);
    
    PdfPCell billinformationcell;
    PdfPCell billinformationcell;
    if (currentorder.getSet(item).getItem(0).getPreviewFilename().equals("")) {
      billinformationcell = headercell("VIRTUAL PRODUCT PREVIEW", this.headerfont);
    } else {
      billinformationcell = headercell("VIRTUAL PRODUCT SAMPLE PREVIEW", this.headerfont);
    }
    
    billinformationcell.setHorizontalAlignment(1);
    header.addCell(billinformationcell);
    
    PdfPTable body = new PdfPTable(1);
    
    PdfPTable infobody = new PdfPTable(2);
    float[] infobodyWidths = { 38.0F, 62.0F };
    infobody.setWidths(infobodyWidths);
    
    PdfPTable info = new PdfPTable(2);
    float[] infoWidths = { 50.0F, 50.0F };
    info.setWidths(infoWidths);
    
    if ((currentorder.getSet(item).getItem(0).getStyleNumber().equals("nonotto")) || (currentorder.getSet(item).getItem(0).getStyleNumber().equals("nonottoflat"))) {
      info.addCell(buzzcell("Style:", this.cellfontbold));
      
      if (SharedData.isFaya.booleanValue()) {
        if ((currentorder.getSet(item).getItem(0).getCustomStyleName() != null) && (!currentorder.getSet(item).getItem(0).getCustomStyleName().equals(""))) {
          info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getCustomStyleName(), this.cellfont));
        } else {
          info.addCell(buzzcell("Non Faya", this.cellfont));
        }
        
      }
      else if ((currentorder.getSet(item).getItem(0).getCustomStyleName() != null) && (!currentorder.getSet(item).getItem(0).getCustomStyleName().equals(""))) {
        info.addCell(buzzcell("Non Otto: " + currentorder.getSet(item).getItem(0).getCustomStyleName(), this.cellfont));
      } else {
        info.addCell(buzzcell("Non Otto", this.cellfont));
      }
      


      info.addCell(buzzcell("Type:", this.cellfontbold));
      if (currentorder.getSet(item).getItem(0).getStyleNumber().equals("nonotto")) {
        info.addCell(buzzcell("Regular", this.cellfont));
      } else {
        info.addCell(buzzcell("Flat", this.cellfont));
      }
      
      if (!currentorder.getSet(item).getItem(0).getColorCodeName().equals("")) {
        info.addCell(buzzcell("Color Name:", this.cellfontbold));
        info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getColorCodeName(), this.cellfont));
      }
      
      if (!currentorder.getSet(item).getItem(0).getColorCode().equals("")) {
        info.addCell(buzzcell("Color #:", this.cellfontbold));
        info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getColorCode(), this.cellfont));
      }
    }
    else if (currentorder.getOrderType() == OrderData.OrderType.DOMESTIC) {
      info.addCell(buzzcell("Style:", this.cellfontbold));
      info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getStyleNumber(), this.cellfont));
      
      info.addCell(buzzcell("Color Name:", this.cellfontbold));
      info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getColorCodeName(), this.cellfont));
      
      info.addCell(buzzcell("Color #:", this.cellfontbold));
      info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getColorCode(), this.cellfont));
      
      if (!currentorder.getSet(item).getItem(0).getCrownStyle().equals("")) {
        info.addCell(buzzcell("Crown Style:", this.cellfontbold));
        info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getCrownStyle(), this.cellfont));
      }
      
      if (!currentorder.getSet(item).getItem(0).getMaterial().equals("")) {
        info.addCell(buzzcell("Material:", this.cellfontbold));
        info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getMaterial(), this.cellfont));
      }
      
      if (!currentorder.getSet(item).getItem(0).getCrownConstruction().equals("")) {
        info.addCell(buzzcell("Crown Construction:", this.cellfontbold));
        info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getCrownConstruction(), this.cellfont));
      }
      
      if (!currentorder.getSet(item).getItem(0).getNumberOfPanels().equals("")) {
        info.addCell(buzzcell("# of Panel:", this.cellfontbold));
        info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getNumberOfPanels(), this.cellfont));

      }
      


    }
    else
    {


      if ((currentorder.getSet(item).getItem(0).getStyleNumber().startsWith("888-")) || (currentorder.getSet(item).getItem(0).getStyleNumber().startsWith("889-")) || (currentorder.getSet(item).getItem(0).getStyleNumber().startsWith("111-")) || (currentorder.getSet(item).getItem(0).getStyleNumber().startsWith("777-"))) {
        info.addCell(buzzcell("Style:", this.cellfontbold));
        if ((currentorder.getSet(item).getItem(0).getCustomStyleName() != null) && (!currentorder.getSet(item).getItem(0).getCustomStyleName().equals(""))) {
          info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getStyleNumber() + currentorder.getSet(item).getItem(0).getAdvanceType() + ": " + currentorder.getSet(item).getItem(0).getCustomStyleName(), this.cellfont));
        } else {
          info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getStyleNumber() + currentorder.getSet(item).getItem(0).getAdvanceType(), this.cellfont));
        }
      } else {
        info.addCell(buzzcell("Style:", this.cellfontbold));
        info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getStyleNumber() + currentorder.getSet(item).getItem(0).getAdvanceType(), this.cellfont));
      }
      
      if (!currentorder.getSet(item).getItem(0).getColorCode().equals("")) {
        info.addCell(buzzcell("Color:", this.cellfontbold));
        info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getColorCode() + " - " + currentorder.getSet(item).getItem(0).getColorCodeName(), this.cellfont));
      }
      
      if (!currentorder.getSet(item).getItem(0).getProfile().equals("")) {
        info.addCell(buzzcell("Profile:", this.cellfontbold));
        info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getProfile(), this.cellfont));
      }
      
      if (!currentorder.getSet(item).getItem(0).getCrownStyle().equals("")) {
        info.addCell(buzzcell("Crown Style:", this.cellfontbold));
        info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getCrownStyle(), this.cellfont));
      }
      
      if ((getOrderType() != PDFType.OVERSEASSAMPLE) && (getOrderType() != PDFType.OVERSEASPURCHASEORDER) && 
        (!currentorder.getSet(item).getItem(0).getMaterial().equals(""))) {
        info.addCell(buzzcell("Material:", this.cellfontbold));
        info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getMaterial(), this.cellfont));
      }
      

      if (!currentorder.getSet(item).getItem(0).getCrownConstruction().equals("")) {
        info.addCell(buzzcell("Crown Construction:", this.cellfontbold));
        info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getCrownConstruction(), this.cellfont));
      }
      
      if (!currentorder.getSet(item).getItem(0).getNumberOfPanels().equals("")) {
        info.addCell(buzzcell("# of Panel:", this.cellfontbold));
        info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getNumberOfPanels(), this.cellfont));
      }
      
      if (!currentorder.getSet(item).getItem(0).getPanelStitchColor().equals("")) {
        info.addCell(buzzcell("Panel Stitch Color:", this.cellfontbold));
        info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getPanelStitchColor() + " - " + currentorder.getSet(item).getItem(0).getPanelStitchColorName(), this.cellfont));
      }
      







      if ((getOrderType() == PDFType.OVERSEASSAMPLE) || (getOrderType() == PDFType.OVERSEASPURCHASEORDER))
      {
        if ((currentorder.getSet(item).getItem(0).getSideBackPanelFabric().equals("")) && (currentorder.getSet(item).getItem(0).getBackPanelFabric().equals("")) && (currentorder.getSet(item).getItem(0).getSidePanelFabric().equals(""))) {
          if (!currentorder.getSet(item).getItem(0).getFrontPanelFabricName().equals("")) {
            info.addCell(buzzcell("Panel Fabric:", this.cellfontbold));
            info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getFrontPanelFabricName(), this.cellfont));
          }
          if (!currentorder.getSet(item).getItem(0).getFrontPanelContentName().equals("")) {
            info.addCell(buzzcell("Panel Content:", this.cellfontbold));
            info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getFrontPanelContentName(), this.cellfont));
          }
          if (!currentorder.getSet(item).getItem(0).getFrontPanelFabricSpecName().equals("")) {
            info.addCell(buzzcell("Panel Spec:", this.cellfontbold));
            info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getFrontPanelFabricSpecName(), this.cellfont));
          }
        } else {
          if (!currentorder.getSet(item).getItem(0).getFrontPanelFabricName().equals("")) {
            info.addCell(buzzcell("Front Panel Fabric:", this.cellfontbold));
            info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getFrontPanelFabricName(), this.cellfont));
          }
          if (!currentorder.getSet(item).getItem(0).getFrontPanelContentName().equals("")) {
            info.addCell(buzzcell("Front Panel Content:", this.cellfontbold));
            info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getFrontPanelContentName(), this.cellfont));
          }
          if (!currentorder.getSet(item).getItem(0).getFrontPanelFabricSpecName().equals("")) {
            info.addCell(buzzcell("Front Panel Spec:", this.cellfontbold));
            info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getFrontPanelFabricSpecName(), this.cellfont));
          }
        }
      }
      
      if (!currentorder.getSet(item).getItem(0).getFrontPanelColor().equals("")) {
        info.addCell(buzzcell("Front Panel Color:", this.cellfontbold));
        info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getFrontPanelColor() + " - " + currentorder.getSet(item).getItem(0).getFrontPanelColorName(), this.cellfont));
      }
      
      if ((getOrderType() == PDFType.OVERSEASSAMPLE) || (getOrderType() == PDFType.OVERSEASPURCHASEORDER)) {
        if (!currentorder.getSet(item).getItem(0).getSideBackPanelFabricName().equals("")) {
          info.addCell(buzzcell("Side & Back Fabric:", this.cellfontbold));
          info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getSideBackPanelFabricName(), this.cellfont));
        }
        if (!currentorder.getSet(item).getItem(0).getSideBackPanelContentName().equals("")) {
          info.addCell(buzzcell("Side & Back Content:", this.cellfontbold));
          info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getSideBackPanelContentName(), this.cellfont));
        }
        if (!currentorder.getSet(item).getItem(0).getSideBackPanelFabricSpecName().equals("")) {
          info.addCell(buzzcell("Side & Back Panel Spec:", this.cellfontbold));
          info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getSideBackPanelFabricSpecName(), this.cellfont));
        }
      }
      
      if (!currentorder.getSet(item).getItem(0).getSideBackPanelColor().equals("")) {
        info.addCell(buzzcell("Side & Back Panel Color:", this.cellfontbold));
        info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getSideBackPanelColor() + " - " + currentorder.getSet(item).getItem(0).getSideBackPanelColorName(), this.cellfont));
      }
      
      if ((getOrderType() == PDFType.OVERSEASSAMPLE) || (getOrderType() == PDFType.OVERSEASPURCHASEORDER)) {
        if (!currentorder.getSet(item).getItem(0).getBackPanelFabricName().equals("")) {
          info.addCell(buzzcell("Back Fabric:", this.cellfontbold));
          info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getBackPanelFabricName(), this.cellfont));
        }
        if (!currentorder.getSet(item).getItem(0).getBackPanelContentName().equals("")) {
          info.addCell(buzzcell("Back Content:", this.cellfontbold));
          info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getBackPanelContentName(), this.cellfont));
        }
        if (!currentorder.getSet(item).getItem(0).getBackPanelFabricSpecName().equals("")) {
          info.addCell(buzzcell("Back Panel Spec:", this.cellfontbold));
          info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getBackPanelFabricSpecName(), this.cellfont));
        }
      }
      
      if (!currentorder.getSet(item).getItem(0).getBackPanelColor().equals("")) {
        info.addCell(buzzcell("Back Panel Color:", this.cellfontbold));
        info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getBackPanelColor() + " - " + currentorder.getSet(item).getItem(0).getBackPanelColorName(), this.cellfont));
      }
      
      if ((getOrderType() == PDFType.OVERSEASSAMPLE) || (getOrderType() == PDFType.OVERSEASPURCHASEORDER)) {
        if (!currentorder.getSet(item).getItem(0).getSidePanelFabricName().equals("")) {
          info.addCell(buzzcell("Side Fabric:", this.cellfontbold));
          info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getSidePanelFabricName(), this.cellfont));
        }
        if (!currentorder.getSet(item).getItem(0).getSidePanelContentName().equals("")) {
          info.addCell(buzzcell("Side Content:", this.cellfontbold));
          info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getSidePanelContentName(), this.cellfont));
        }
        if (!currentorder.getSet(item).getItem(0).getSidePanelFabricSpecName().equals("")) {
          info.addCell(buzzcell("Side Panel Spec:", this.cellfontbold));
          info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getSidePanelFabricSpecName(), this.cellfont));
        }
      }
      

      if (!currentorder.getSet(item).getItem(0).getSidePanelColor().equals("")) {
        info.addCell(buzzcell("Side Panel Color:", this.cellfontbold));
        info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getSidePanelColor() + " - " + currentorder.getSet(item).getItem(0).getSidePanelColorName(), this.cellfont));
      }
      
      if (!currentorder.getSet(item).getItem(0).getVisorStyleNumber().equals("")) {
        info.addCell(buzzcell("Visor Style #:", this.cellfontbold));
        info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getVisorStyleNumberName(), this.cellfont));
      }
      
      if (((getOrderType() == PDFType.OVERSEASSAMPLE) || (getOrderType() == PDFType.OVERSEASPURCHASEORDER)) && 
        (!currentorder.getSet(item).getItem(0).getFactoryVisorCode().equals(""))) {
        info.addCell(buzzcell("Factory Visor Code:", this.cellfontbold));
        info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getFactoryVisorCode(), this.cellfont));
      }
      

      if (!currentorder.getSet(item).getItem(0).getVisorRowStitching().equals("")) {
        info.addCell(buzzcell("Visor Row Stitching:", this.cellfontbold));
        info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getVisorRowStitching(), this.cellfont));
      }
      
      if (!currentorder.getSet(item).getItem(0).getVisorStitchColor().equals("")) {
        info.addCell(buzzcell("Visor Stitch Color:", this.cellfontbold));
        info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getVisorStitchColor() + " - " + currentorder.getSet(item).getItem(0).getVisorStitchColorName(), this.cellfont));
      }
      
      if (!currentorder.getSet(item).getItem(0).getPrimaryVisorColor().equals("")) {
        info.addCell(buzzcell("Primary Visor Color:", this.cellfontbold));
        info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getPrimaryVisorColor() + " - " + currentorder.getSet(item).getItem(0).getPrimaryVisorColorName(), this.cellfont));
      }
      if (!currentorder.getSet(item).getItem(0).getVisorTrimColor().equals("")) {
        info.addCell(buzzcell("Visor Trim/Edge Color:", this.cellfontbold));
        info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getVisorTrimColor() + " - " + currentorder.getSet(item).getItem(0).getVisorTrimColorName(), this.cellfont));
      }
      if (!currentorder.getSet(item).getItem(0).getVisorSandwichColor().equals("")) {
        info.addCell(buzzcell("Visor Sandwich Color:", this.cellfontbold));
        info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getVisorSandwichColor() + " - " + currentorder.getSet(item).getItem(0).getVisorSandwichColorName(), this.cellfont));
      }
      if (!currentorder.getSet(item).getItem(0).getUndervisorColor().equals("")) {
        info.addCell(buzzcell("Undervisor Color:", this.cellfontbold));
        info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getUndervisorColor() + " - " + currentorder.getSet(item).getItem(0).getUndervisorColorName(), this.cellfont));
      }
      if (!currentorder.getSet(item).getItem(0).getDistressedVisorInsideColor().equals("")) {
        info.addCell(buzzcell("Distressed Visor Inside Color:", this.cellfontbold));
        info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getDistressedVisorInsideColor() + " - " + currentorder.getSet(item).getItem(0).getDistressedVisorInsideColorName(), this.cellfont));
      }
      if (!currentorder.getSet(item).getItem(0).getClosureStyleNumber().equals("")) {
        info.addCell(buzzcell("Closure Style #:", this.cellfontbold));
        info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getClosureStyleNumberName(), this.cellfont));
      }
      
      if (!currentorder.getSet(item).getItem(0).getClosureStrapColor().equals("")) {
        info.addCell(buzzcell("Closure Strap Color:", this.cellfontbold));
        info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getClosureStrapColor() + " - " + currentorder.getSet(item).getItem(0).getClosureStrapColorName(), this.cellfont));
      }
      
      if (!currentorder.getSet(item).getItem(0).getEyeletStyleNumber().equals("")) {
        info.addCell(buzzcell("Eyelet Style #:", this.cellfontbold));
        info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getEyeletStyleNumberName(), this.cellfont));
      }
      
      if ((currentorder.getSet(item).getItem(0).getEyeletStyleNumber().equals("E1")) && (currentorder.getSet(item).getItem(0).getSideBackEyeletColor().equals("")) && (currentorder.getSet(item).getItem(0).getBackEyeletColor().equals("")) && (currentorder.getSet(item).getItem(0).getSideEyeletColor().equals(""))) {
        if (!currentorder.getSet(item).getItem(0).getFrontEyeletColor().equals("")) {
          info.addCell(buzzcell("Eyelet Color:", this.cellfontbold));
          info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getFrontEyeletColor() + " - " + currentorder.getSet(item).getItem(0).getFrontEyeletColorName(), this.cellfont));
        }
      }
      else if (!currentorder.getSet(item).getItem(0).getFrontEyeletColor().equals("")) {
        info.addCell(buzzcell("Front Eyelet Color:", this.cellfontbold));
        info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getFrontEyeletColor() + " - " + currentorder.getSet(item).getItem(0).getFrontEyeletColorName(), this.cellfont));
      }
      

      if (!currentorder.getSet(item).getItem(0).getSideBackEyeletColor().equals("")) {
        info.addCell(buzzcell("Side & Back Eyelet Color:", this.cellfontbold));
        info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getSideBackEyeletColor() + " - " + currentorder.getSet(item).getItem(0).getSideBackEyeletColorName(), this.cellfont));
      }
      
      if (!currentorder.getSet(item).getItem(0).getBackEyeletColor().equals("")) {
        info.addCell(buzzcell("Back Eyelet Color:", this.cellfontbold));
        info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getBackEyeletColor() + " - " + currentorder.getSet(item).getItem(0).getBackEyeletColorName(), this.cellfont));
      }
      
      if (!currentorder.getSet(item).getItem(0).getSideEyeletColor().equals("")) {
        info.addCell(buzzcell("Side Eyelet Color:", this.cellfontbold));
        info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getSideEyeletColor() + " - " + currentorder.getSet(item).getItem(0).getSideEyeletColorName(), this.cellfont));
      }
      
      if (!currentorder.getSet(item).getItem(0).getButtonColor().equals("")) {
        info.addCell(buzzcell("Button Color:", this.cellfontbold));
        info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getButtonColor() + " - " + currentorder.getSet(item).getItem(0).getButtonColorName(), this.cellfont));
      }
      
      if (!currentorder.getSet(item).getItem(0).getInnerTapingColor().equals("")) {
        info.addCell(buzzcell("Inner Tapping Color:", this.cellfontbold));
        info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getInnerTapingColor() + " - " + currentorder.getSet(item).getItem(0).getInnerTapingColorName(), this.cellfont));
      }
      
      if (!currentorder.getSet(item).getItem(0).getSweatbandStyleNumber().equals("")) {
        info.addCell(buzzcell("Sweatband Style #:", this.cellfontbold));
        info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getSweatbandStyleNumberName(), this.cellfont));
      }
      
      if (!currentorder.getSet(item).getItem(0).getSweatbandColor().equals("")) {
        info.addCell(buzzcell("Sweatband Color:", this.cellfontbold));
        info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getSweatbandColor() + " - " + currentorder.getSet(item).getItem(0).getSweatbandColorName(), this.cellfont));
      }
      
      if (!currentorder.getSet(item).getItem(0).getSweatbandStripeColor().equals("")) {
        info.addCell(buzzcell("Stripe Color:", this.cellfontbold));
        info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getSweatbandStripeColor() + " - " + currentorder.getSet(item).getItem(0).getSweatbandStripeColorName(), this.cellfont));
      }
    }
    

    if ((getOrderType() == PDFType.DIGITIZING) || (getOrderType() == PDFType.EMBORDER) || (getOrderType() == PDFType.HEATORDER) || (getOrderType() == PDFType.SCREENORDER) || (getOrderType() == PDFType.DIRECTTOGARMENTORDER) || (getOrderType() == PDFType.PATCHORDER) || (getOrderType() == PDFType.OVERSEASDIGITIZING) || (getOrderType() == PDFType.OVERSEASPURCHASEORDER))
    {
      if (!currentorder.getSet(item).getItem(0).getSize().equals("")) {
        info.addCell(buzzcell("Size:", this.cellfontbold));
        info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getSize(), this.cellfont));
      }
      
      info.addCell(buzzcell("Piece Quantity:", this.cellfontbold));
      info.addCell(buzzcell(String.valueOf(currentorder.getSet(item).getItem(0).getQuantity()), this.cellfont)); } else { String extrainfo;
      if ((getOrderType() == PDFType.EMBSAMPLE) || (getOrderType() == PDFType.HEATSAMPLE) || (getOrderType() == PDFType.SCREENSAMPLE) || (getOrderType() == PDFType.DIRECTTOGARMENTSAMPLE) || (getOrderType() == PDFType.PATCHSAMPLE) || (getOrderType() == PDFType.OVERSEASSAMPLE))
      {
        if (!currentorder.getSet(item).getItem(0).getSize().equals("")) {
          info.addCell(buzzcell("Size:", this.cellfontbold));
          info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getSize(), this.cellfont));
        }
        
        int productsample = 0;
        if (currentorder.getSet(item).getItem(0).getProductSampleToDo() != null) {
          productsample = currentorder.getSet(item).getItem(0).getProductSampleToDo().intValue();
        }
        extrainfo = "";
        if ((currentorder.getSet(item).getItem(0).getProductSampleShip()) && (currentorder.getSet(item).getItem(0).getProductSampleEmail())) {
          extrainfo = "email/ship";
        } else if (currentorder.getSet(item).getItem(0).getProductSampleShip()) {
          extrainfo = "ship only";
        } else if (currentorder.getSet(item).getItem(0).getProductSampleEmail()) {
          extrainfo = "email only";
        }
        info.addCell(buzzcell("Quantity:", this.cellfontbold));
        if (productsample > 1) {
          info.addCell(buzzcell(productsample + " samples " + extrainfo, this.cellfont));
        } else {
          info.addCell(buzzcell(productsample + " sample " + extrainfo, this.cellfont));
        }
        int totalswatchs = 0;
        for (int i = 0; i < currentorder.getSet(item).getLogoCount(); i++) {
          if ((getOrderType() == PDFType.OVERSEASSAMPLE) && (currentorder.getSet(item).getLogo(i).getSwatchToDo() != null)) {
            totalswatchs += currentorder.getSet(item).getLogo(i).getSwatchToDo().intValue();
          } else {
            int vendornumber = 0;
            if (getOrderType() == PDFType.EMBSAMPLE) {
              vendornumber = currentorder.getVendorInformation().getEmbroideryVendor().getVendor().intValue();
            } else if (getOrderType() == PDFType.HEATSAMPLE) {
              vendornumber = currentorder.getVendorInformation().getHeatTransferVendor().getVendor().intValue();
            } else if (getOrderType() == PDFType.SCREENSAMPLE) {
              vendornumber = currentorder.getVendorInformation().getScreenPrintVendor().getVendor().intValue();
            } else if (getOrderType() == PDFType.DIRECTTOGARMENTSAMPLE) {
              vendornumber = currentorder.getVendorInformation().getDirectToGarmentVendor().getVendor().intValue();
            } else if (getOrderType() == PDFType.PATCHSAMPLE) {
              vendornumber = currentorder.getVendorInformation().getPatchVendor().getVendor().intValue();
            }
            if ((currentorder.getVendorInformation().getEmbroideryVendor().getVendor() != null) && (vendornumber == currentorder.getVendorInformation().getEmbroideryVendor().getVendor().intValue()) && (currentorder.getSet(item).getLogo(i).getSwatchToDo() != null) && ((currentorder.getSet(item).getLogo(i).getDecoration().equals("Flat Embroidery")) || (currentorder.getSet(item).getLogo(i).getDecoration().equals("3D Embroidery")))) {
              totalswatchs += currentorder.getSet(item).getLogo(i).getSwatchToDo().intValue();
            } else if ((currentorder.getVendorInformation().getScreenPrintVendor().getVendor() != null) && (vendornumber == currentorder.getVendorInformation().getScreenPrintVendor().getVendor().intValue()) && (currentorder.getSet(item).getLogo(i).getSwatchToDo() != null) && ((currentorder.getSet(item).getLogo(i).getDecoration().equals("Screen Print")) || (currentorder.getSet(item).getLogo(i).getDecoration().equals("Four Color Screen Print")))) {
              totalswatchs += currentorder.getSet(item).getLogo(i).getSwatchToDo().intValue();
            } else if ((currentorder.getVendorInformation().getHeatTransferVendor().getVendor() != null) && (vendornumber == currentorder.getVendorInformation().getHeatTransferVendor().getVendor().intValue()) && (currentorder.getSet(item).getLogo(i).getSwatchToDo() != null) && (currentorder.getSet(item).getLogo(i).getDecoration().equals("Heat Transfer"))) {
              totalswatchs += currentorder.getSet(item).getLogo(i).getSwatchToDo().intValue();
            } else if ((currentorder.getVendorInformation().getDirectToGarmentVendor().getVendor() != null) && (vendornumber == currentorder.getVendorInformation().getDirectToGarmentVendor().getVendor().intValue()) && (currentorder.getSet(item).getLogo(i).getSwatchToDo() != null) && (currentorder.getSet(item).getLogo(i).getDecoration().equals("Direct To Garment"))) {
              totalswatchs += currentorder.getSet(item).getLogo(i).getSwatchToDo().intValue();
            } else if ((currentorder.getVendorInformation().getPatchVendor().getVendor() != null) && (vendornumber == currentorder.getVendorInformation().getPatchVendor().getVendor().intValue()) && (currentorder.getSet(item).getLogo(i).getSwatchToDo() != null) && (currentorder.getSet(item).getLogo(i).getDecoration().equals("Patch"))) {
              totalswatchs += currentorder.getSet(item).getLogo(i).getSwatchToDo().intValue();
            } else if ((currentorder.getVendorInformation().getEmbroideryVendor().getVendor() != null) && (vendornumber == currentorder.getVendorInformation().getEmbroideryVendor().getVendor().intValue()) && (currentorder.getSet(item).getLogo(i).getSwatchToDo() != null)) {
              totalswatchs += currentorder.getSet(item).getLogo(i).getSwatchToDo().intValue();
            }
          }
        }
        if (totalswatchs > 0) {
          info.addCell(buzzcell("", this.cellfontbold));
          if (totalswatchs == 1) {
            info.addCell(buzzcell(totalswatchs + " swatch ", this.cellfont));
          } else {
            info.addCell(buzzcell(totalswatchs + " swatches ", this.cellfont));
          }
        }
        
        if (getOrderType() == PDFType.OVERSEASSAMPLE) {
          info.addCell(buzzcell("Same Cap Quantity:", this.cellfontbold));
          info.addCell(buzzcell(String.valueOf(this.overseassamplevendorcalc.getSameItemQuantity(item)), this.cellfont));
        }
        else if ((currentorder.getSet(item).getItem(0).getQuantity() != null) && (currentorder.getSet(item).getItem(0).getQuantity().intValue() > 0)) {
          info.addCell(buzzcell("Same Cap Quantity:", this.cellfontbold));
          info.addCell(buzzcell(String.valueOf(currentorder.getSet(item).getItem(0).getQuantity()), this.cellfont));

        }
        

      }
      else if (currentorder.getSet(item).getItemCount() > 1) {
        info.addCell(buzzcell("Size:", this.cellfontbold));
        info.addCell(buzzcell("Quantity:", this.cellfontbold));
        
        for (OrderDataItem theitems : currentorder.getSet(item).getItems()) {
          info.addCell(buzzcell(theitems.getSize(), this.cellfont));
          info.addCell(buzzcell(String.valueOf(theitems.getQuantity()), this.cellfont));
        }
      }
      else {
        if (!currentorder.getSet(item).getItem(0).getSize().equals("")) {
          info.addCell(buzzcell("Size:", this.cellfontbold));
          info.addCell(buzzcell(currentorder.getSet(item).getItem(0).getSize(), this.cellfont));
        }
        
        info.addCell(buzzcell("Quantity:", this.cellfontbold));
        info.addCell(buzzcell(String.valueOf(((OrderDataItem)currentorder.getSet(item).getItems().get(0)).getQuantity()), this.cellfont));
      }
    }
    


    if ((getOrderType() == PDFType.APPROVAL) || (getOrderType() == PDFType.PRODUCTIONAPPROVAL) || (getOrderType() == PDFType.QUOTATION) || (getOrderType() == PDFType.OVERSEASAPPROVAL) || (getOrderType() == PDFType.OVERSEASPRODUCTIONAPPROVAL) || (getOrderType() == PDFType.OVERSEASQUOTATION)) {
      info.addCell(buzzcell(" ", this.cellfontbold));
      info.addCell(buzzcell(" ", this.cellfont));
      if ((getOrderType() == PDFType.QUOTATION) || (getOrderType() == PDFType.OVERSEASQUOTATION)) {
        info.addCell(buzzcell("Quotation #:", this.cellfontbold));
        info.addCell(buzzcell(String.valueOf(currentorder.getHiddenKey()), this.cellfont));
      } else {
        info.addCell(buzzcell("Order #:", this.cellfontbold));
        info.addCell(buzzcell(currentorder.getOrderNumber(), this.cellfont));
      }
      
      if (!currentorder.getCustomerPO().equals("")) {
        info.addCell(buzzcell("Customer P.O. #:", this.cellfontbold));
        info.addCell(buzzcell(currentorder.getCustomerPO(), this.cellfont));
      }
      
      if (currentorder.getCustomerInformation().getEclipseAccountNumber() != null) {
        info.addCell(buzzcell("Account Number#:", this.cellfontbold));
        info.addCell(buzzcell(String.valueOf(currentorder.getCustomerInformation().getEclipseAccountNumber()), this.cellfont));
      }
    } else if ((getOrderType() == PDFType.EMBSAMPLE) || (getOrderType() == PDFType.HEATSAMPLE) || (getOrderType() == PDFType.SCREENSAMPLE) || (getOrderType() == PDFType.DIRECTTOGARMENTSAMPLE) || (getOrderType() == PDFType.PATCHSAMPLE) || (getOrderType() == PDFType.EMBORDER) || (getOrderType() == PDFType.HEATORDER) || (getOrderType() == PDFType.SCREENORDER) || (getOrderType() == PDFType.DIRECTTOGARMENTORDER) || (getOrderType() == PDFType.PATCHORDER))
    {
      if ((getOrderType() == PDFType.EMBORDER) || (getOrderType() == PDFType.HEATORDER) || (getOrderType() == PDFType.SCREENORDER) || (getOrderType() == PDFType.DIRECTTOGARMENTORDER) || (getOrderType() == PDFType.PATCHORDER)) {
        info.addCell(buzzcell("Piece Cost:", this.cellfontbold));
        info.addCell(buzzcell(NumberFormat.getCurrencyInstance().format(this.domesticpricecalc.getOrderItemCost(item)), this.cellfont));
        info.addCell(buzzcell("Total Cost:", this.cellfontbold));
        info.addCell(buzzcell(NumberFormat.getCurrencyInstance().format(this.domesticpricecalc.getOrderItemCost(item) * currentorder.getSet(item).getItem(0).getQuantity().intValue()), this.cellfont));
      }
      
      String allservices = "";
      if (currentorder.getSet(item).getItem(0).getPolybag()) {
        allservices = allservices + "Polybag & Fold, ";
      }
      if (currentorder.getSet(item).getItem(0).getOakLeaves()) {
        allservices = allservices + "Oak Leaves Emblems, ";
      }
      if (currentorder.getSet(item).getItem(0).getTagging()) {
        allservices = allservices + "Tagging, ";
      }
      if (currentorder.getSet(item).getItem(0).getStickers()) {
        allservices = allservices + "Stickers, ";
      }
      if (currentorder.getSet(item).getItem(0).getSewingPatches()) {
        allservices = allservices + "Sewing Patches, ";
      }
      if (currentorder.getSet(item).getItem(0).getHeatPressPatches()) {
        allservices = allservices + "Heat Press Patches, ";
      }
      if (currentorder.getSet(item).getItem(0).getRemoveInnerPrintedLabel()) {
        allservices = allservices + "Remove Inner Printed Label, ";
      }
      if (currentorder.getSet(item).getItem(0).getAddInnerPrintedLabel()) {
        allservices = allservices + "Add Inner Printed Label, ";
      }
      if (currentorder.getSet(item).getItem(0).getRemoveInnerWovenLabel()) {
        allservices = allservices + "Remove Inner Woven Label, ";
      }
      if (currentorder.getSet(item).getItem(0).getAddInnerWovenLabel()) {
        allservices = allservices + "Add Inner Woven Label, ";
      }
      if ((currentorder.getSet(item).getItem(0).getPersonalizationChanges() != null) && (currentorder.getSet(item).getItem(0).getPersonalizationChanges().intValue() > 0)) {
        allservices = allservices + "Personalization, ";
      }
      if (!allservices.equals("")) {
        info.addCell(buzzcell("Services:", this.cellfontbold));
        allservices = allservices.substring(0, allservices.length() - 2);
        info.addCell(buzzcell(allservices, this.cellfont));
      }
    }
    else if (getOrderType() == PDFType.OVERSEASPURCHASEORDER) {
      info.addCell(buzzcell("Piece Cost:", this.cellfontbold));
      info.addCell(buzzcell(NumberFormat.getCurrencyInstance().format(this.overseaspricecalc.getVendorItemDozCost(item) / 12.0D), this.cellfont));
      info.addCell(buzzcell("Total Cost:", this.cellfontbold));
      info.addCell(buzzcell(NumberFormat.getCurrencyInstance().format(this.overseaspricecalc.getVendorItemDozCost(item) / 12.0D * currentorder.getSet(item).getItem(0).getQuantity().intValue()), this.cellfont));
    }
    
    if ((getOrderType() == PDFType.OVERSEASPURCHASEORDER) || (getOrderType() == PDFType.OVERSEASPRODUCTIONAPPROVAL)) {
      if ((currentorder.getSet(item).getItem(0).getHasPrivateLabel() != null) && (currentorder.getSet(item).getItem(0).getHasPrivateLabel().booleanValue())) {
        info.addCell(buzzcell("Private Label", this.cellfontbold));
        info.addCell(buzzcell("", this.cellfont));
      } else {
        info.addCell(buzzcell("OTTO Standard Label", this.cellfontbold));
        info.addCell(buzzcell("", this.cellfont));
      }
    }
    
    if ((getOrderType() == PDFType.OVERSEASPURCHASEORDER) || (getOrderType() == PDFType.EMBORDER) || (getOrderType() == PDFType.HEATORDER) || (getOrderType() == PDFType.SCREENORDER) || (getOrderType() == PDFType.DIRECTTOGARMENTORDER) || (getOrderType() == PDFType.PATCHORDER)) {
      String filename = currentorder.getSet(item).getItem(0).getPreviewFilename();
      if ((filename.lastIndexOf("_r") != -1) || (filename.lastIndexOf("_R") != -1)) {
        info.addCell(buzzcell("i-Sample Revision:", this.cellfontbold));
        info.addCell(buzzcell(filename, this.cellfont));
      }
    }
    
    String overseasurlstring = "";
    
    if ((!currentorder.getSet(item).getItem(0).getPreviewFilename().equals("")) && (currentorder.getOrderType() == OrderData.OrderType.OVERSEAS)) {
      OverseasPreview mypreview = new OverseasPreview();
      Image artworkpreview = this.sameimages.getImage(currentorder.getSet(item).getItem(0).getPreviewFilename(), "C:\\WorkFlow\\NewWorkflowData\\" + currentorder.getOrderTypeFolder() + "Data/" + currentorder.getHiddenKey() + "/cappreview/" + currentorder.getSet(item).getItem(0).getPreviewFilename());
      mypreview.setImage(artworkpreview);
      
      PdfPCell specialpreviewcell = buzzcell("", this.cellfont);
      specialpreviewcell.setMinimumHeight(145.0F);
      specialpreviewcell.setCellEvent(mypreview);
      info.addCell(specialpreviewcell);
      info.addCell(buzzcell("", this.cellfontbold));
    } else if ((currentorder.getSet(item).getItem(0).getPreviewFilename().equals("")) && (currentorder.getOrderType() == OrderData.OrderType.OVERSEAS))
    {
      try {
        overseasurlstring = 
        
          "styletype=" + currentorder.getSet(item).getItem(0).getStyleType().name() + "&category=" + currentorder.getSet(item).getItem(0).getCategory().replace(" ", "%20") + "&stylenumber=" + currentorder.getSet(item).getItem(0).getStyleNumber() + "&colorcode=" + currentorder.getSet(item).getItem(0).getColorCode() + "&frontpanelcolor=" + currentorder.getSet(item).getItem(0).getFrontPanelColor() + "&sidebackpanelcolor=" + currentorder.getSet(item).getItem(0).getSideBackPanelColor() + "&backpanelcolor=" + currentorder.getSet(item).getItem(0).getBackPanelColor() + "&sidepanelcolor=" + currentorder.getSet(item).getItem(0).getSidePanelColor() + "&visorstylenumber=" + currentorder.getSet(item).getItem(0).getVisorStyleNumber() + "&primaryvisorcolor=" + currentorder.getSet(item).getItem(0).getPrimaryVisorColor() + "&visortrimcolor=" + currentorder.getSet(item).getItem(0).getVisorTrimColor() + "&visorsandwichcolor=" + currentorder.getSet(item).getItem(0).getVisorSandwichColor() + "&undervisorcolor=" + currentorder.getSet(item).getItem(0).getUndervisorColor() + "&distressedvisorinsidecolor=" + currentorder.getSet(item).getItem(0).getDistressedVisorInsideColor() + "&closurestylenumber=" + currentorder.getSet(item).getItem(0).getClosureStyleNumber() + "&closurestrapcolor=" + currentorder.getSet(item).getItem(0).getClosureStrapColor() + "&eyeletstylenumber=" + currentorder.getSet(item).getItem(0).getEyeletStyleNumber() + "&eyeletcolor=" + currentorder.getSet(item).getItem(0).getFrontEyeletColor() + "&sidebackeyeletcolor=" + currentorder.getSet(item).getItem(0).getSideBackEyeletColor() + "&backeyeletcolor=" + currentorder.getSet(item).getItem(0).getBackEyeletColor() + "&sideeyeletcolor=" + currentorder.getSet(item).getItem(0).getSideEyeletColor() + "&buttoncolor=" + currentorder.getSet(item).getItem(0).getButtonColor() + "&innertapingcolor=" + currentorder.getSet(item).getItem(0).getInnerTapingColor() + "&sweatbandstylenumber=" + currentorder.getSet(item).getItem(0).getSweatbandStyleNumber() + "&sweatbandcolor=" + currentorder.getSet(item).getItem(0).getSweatbandColor() + "&sweatbandstripecolor=" + currentorder.getSet(item).getItem(0).getSweatbandStripeColor();
      } catch (Exception e) {
        overseasurlstring = "";
      }
      
      Image artworkpreview = this.sameimages.getOverseasISampleImage(overseasurlstring, currentorder.getSet(item).getItem(0));
      
      if (artworkpreview != null) {
        OverseasPreview mypreview = new OverseasPreview();
        mypreview.setImage(artworkpreview);
        
        PdfPCell specialpreviewcell = buzzcell("", this.cellfont);
        specialpreviewcell.setMinimumHeight(145.0F);
        specialpreviewcell.setCellEvent(mypreview);
        info.addCell(specialpreviewcell);
        info.addCell(buzzcell("", this.cellfontbold));
      }
    }
    


    infobody.addCell(paddedTableCell(info, 1));
    
    if (!currentorder.getSet(item).getItem(0).getPreviewFilename().equals(""))
    {
      if (currentorder.getOrderType() == OrderData.OrderType.DOMESTIC)
      {
        Image artworkpreview = this.sameimages.getImage(currentorder.getSet(item).getItem(0).getPreviewFilename(), "C:\\WorkFlow\\NewWorkflowData\\" + currentorder.getOrderTypeFolder() + "Data/" + currentorder.getHiddenKey() + "/cappreview/" + currentorder.getSet(item).getItem(0).getPreviewFilename());
        
        PdfPCell previewimage = new PdfPCell(artworkpreview, true);
        previewimage.setBorder(0);
        previewimage.setPadding(0.0F);
        previewimage.setHorizontalAlignment(1);
        previewimage.setVerticalAlignment(5);
        infobody.addCell(previewimage);
      } else if (currentorder.getOrderType() == OrderData.OrderType.OVERSEAS) {
        PdfPCell emptypeviewcell = buzzcell("", this.cellfont);
        emptypeviewcell.setMinimumHeight(296.0F);
        infobody.addCell(emptypeviewcell);
      }
    } else if ((currentorder.getSet(item).getItem(0).getPreviewFilename().equals("")) && (currentorder.getOrderType() == OrderData.OrderType.DOMESTIC))
    {
      String domesticurlstring = "";
      try {
        domesticurlstring = "styletype=" + currentorder.getSet(item).getItem(0).getStyleType().name() + "&category=" + currentorder.getSet(item).getItem(0).getCategory().replace(" ", "%20") + "&stylenumber=" + currentorder.getSet(item).getItem(0).getStyleNumber() + "&colorcode=" + currentorder.getSet(item).getItem(0).getColorCode();
      } catch (Exception e) {
        domesticurlstring = "";
      }
      
      Image artworkpreview = this.sameimages.getDomesticISampleImage(domesticurlstring, currentorder.getSet(item).getItem(0));
      
      if (artworkpreview != null) {
        PdfPCell previewimage = new PdfPCell(artworkpreview, true);
        previewimage.setBorder(0);
        previewimage.setPadding(0.0F);
        previewimage.setHorizontalAlignment(1);
        previewimage.setVerticalAlignment(5);
        infobody.addCell(previewimage);
      } else {
        infobody.addCell(buzzcell("", this.cellfont));
      }
    } else if ((currentorder.getSet(item).getItem(0).getPreviewFilename().equals("")) && (currentorder.getOrderType() == OrderData.OrderType.OVERSEAS) && (this.sameimages.containsKey(overseasurlstring))) {
      PdfPCell emptypeviewcell = buzzcell("", this.cellfont);
      emptypeviewcell.setMinimumHeight(296.0F);
      infobody.addCell(emptypeviewcell);
    } else {
      infobody.addCell(buzzcell("", this.cellfont));
    }
    
    body.addCell(paddedTableCell(infobody, 1));
    
    PdfPTable mytable = headerBodyPanel(header, body);
    return mytable;
  }
  
  private PdfPTable pricingcommentstermsPanel(ExtendOrderData currentorder, float minheight) throws Exception {
    PdfPTable mytable = new PdfPTable(3);
    mytable.setSplitRows(false);
    mytable.setSplitLate(true);
    float[] tableWidths = { 49.5F, 1.0F, 49.5F };
    mytable.setWidths(tableWidths);
    PdfPCell pricingcell = paddedTableCell(pricingPanel(Boolean.valueOf(true)), 0);
    mytable.addCell(pricingcell);
    mytable.addCell(buzzcell("", this.cellfont));
    
    PdfPTable mystackpanel = new PdfPTable(1);
    mystackpanel.setTotalWidth(283.13998F);
    
    mystackpanel.addCell(paddedTableCell(termsPanel(), 0));
    
    if ((getOrderType() == PDFType.APPROVAL) || (getOrderType() == PDFType.OVERSEASAPPROVAL)) {
      if ((currentorder.getCustomerInformation().getTerms().trim().toLowerCase().equals("creditcard")) || (currentorder.getCustomerInformation().getTerms().trim().toLowerCase().equals("credit card")) || (currentorder.getCustomerInformation().getTerms().trim().toLowerCase().equals("cod")) || (currentorder.getCustomerInformation().getTerms().trim().toLowerCase().equals("prepaid")) || (currentorder.getCustomerInformation().getTerms().trim().toLowerCase().equals("cod1"))) {
        mystackpanel.addCell(paddedTableCell(ccinfoPanel(currentorder), 0));
      }
      mystackpanel.addCell(paddedTableCell(approvalPanel(), 0));
    }
    PdfPCell emptycell = paddedTableCell(ordercommentPanel(currentorder), 0);
    emptycell.setMinimumHeight(minheight - mystackpanel.getTotalHeight());
    
    mystackpanel.deleteLastRow();
    if ((getOrderType() == PDFType.APPROVAL) || (getOrderType() == PDFType.OVERSEASAPPROVAL)) {
      if ((currentorder.getCustomerInformation().getTerms().trim().toLowerCase().equals("creditcard")) || (currentorder.getCustomerInformation().getTerms().trim().toLowerCase().equals("credit card")) || (currentorder.getCustomerInformation().getTerms().trim().toLowerCase().equals("cod")) || (currentorder.getCustomerInformation().getTerms().trim().toLowerCase().equals("prepaid")) || (currentorder.getCustomerInformation().getTerms().trim().toLowerCase().equals("cod1"))) {
        mystackpanel.deleteLastRow();
      }
      mystackpanel.deleteLastRow();
    }
    mystackpanel.addCell(emptycell);
    mystackpanel.addCell(paddedTableCell(termsPanel(), 0));
    
    if ((getOrderType() == PDFType.APPROVAL) || (getOrderType() == PDFType.OVERSEASAPPROVAL)) {
      if ((currentorder.getCustomerInformation().getTerms().trim().toLowerCase().equals("creditcard")) || (currentorder.getCustomerInformation().getTerms().trim().toLowerCase().equals("credit card")) || (currentorder.getCustomerInformation().getTerms().trim().toLowerCase().equals("cod")) || (currentorder.getCustomerInformation().getTerms().trim().toLowerCase().equals("prepaid")) || (currentorder.getCustomerInformation().getTerms().trim().toLowerCase().equals("cod1"))) {
        mystackpanel.addCell(paddedTableCell(ccinfoPanel(currentorder), 0));
      }
      mystackpanel.addCell(paddedTableCell(approvalPanel(), 0));
    }
    mytable.addCell(paddedTableCell(mystackpanel, 0));
    



    return mytable;
  }
  
  private PdfPTable commenttermsPanel(ExtendOrderData currentorder) throws Exception {
    PdfPTable mytable = new PdfPTable(3);
    mytable.setSplitRows(false);
    mytable.setSplitLate(true);
    float[] tableWidths = { 49.5F, 1.0F, 49.5F };
    mytable.setWidths(tableWidths);
    mytable.addCell(paddedTableCell(ordercommentPanel(currentorder), 0));
    mytable.addCell(buzzcell("", this.cellfont));
    mytable.addCell(paddedTableCell(termapprovalPanel(currentorder), 0));
    return mytable;
  }
  
  private PdfPTable termapprovalPanel(ExtendOrderData currentorder) throws Exception {
    PdfPTable mytable = new PdfPTable(1);
    mytable.setSplitRows(false);
    mytable.setSplitLate(true);
    mytable.addCell(paddedTableCell(termsPanel(), 0));
    if ((getOrderType() == PDFType.APPROVAL) || (getOrderType() == PDFType.PRODUCTIONAPPROVAL) || (getOrderType() == PDFType.OVERSEASAPPROVAL) || (getOrderType() == PDFType.OVERSEASPRODUCTIONAPPROVAL)) {
      if ((currentorder.getCustomerInformation().getTerms().trim().toLowerCase().equals("creditcard")) || (currentorder.getCustomerInformation().getTerms().trim().toLowerCase().equals("credit card")) || (currentorder.getCustomerInformation().getTerms().trim().toLowerCase().equals("cod")) || (currentorder.getCustomerInformation().getTerms().trim().toLowerCase().equals("prepaid")) || (currentorder.getCustomerInformation().getTerms().trim().toLowerCase().equals("cod1"))) {
        mytable.addCell(paddedTableCell(ccinfoPanel(currentorder), 0));
      }
      mytable.addCell(paddedTableCell(approvalPanel(), 0));
    }
    return mytable;
  }
  
  private boolean termran = false;
  PdfPTable cachedgenericheaderpanel;
  
  private PdfPTable termsPanel() throws Exception { PdfPTable header = new PdfPTable(1);
    PdfPCell billinformationcell = headercell("TERMS & CONDITIONS", this.headerfont);
    billinformationcell.setHorizontalAlignment(1);
    header.addCell(billinformationcell);
    
    PdfPTable info = new PdfPTable(2);
    float[] infoWidths = { 2.0F, 98.0F };
    info.setWidths(infoWidths);
    
    Record sqlrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "eclipse_special_terms" })).where(new Condition[] { DSL.fieldByName(new String[] { "eclipseaccount" }).equal(this.therealorder.getCustomerInformation().getEclipseAccountNumber()) }).limit(1).fetchOne();
    if ((!this.termran) && (this.therealorder.getCustomerInformation().getEclipseAccountNumber() != null) && (sqlrecord != null) && (sqlrecord.size() != 0)) {
      String tempterm_quotation = ((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "quotation" }))).trim();
      if (!tempterm_quotation.equals("")) {
        this.term_quotation = tempterm_quotation;
      }
      String tempterm_approval = ((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "approval" }))).trim();
      if (!tempterm_approval.equals("")) {
        this.term_approval = tempterm_approval;
      }
      String tempterm_overseasquotation = ((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "overseasquotation" }))).trim();
      if (!tempterm_overseasquotation.equals("")) {
        this.term_overseas_quotation = tempterm_overseasquotation;
      }
      String tempterm_overseasapproval = ((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "overseasapproval" }))).trim();
      if (!tempterm_overseasapproval.equals("")) {
        this.term_overseas_approval = tempterm_overseasapproval;
      }
    }
    this.termran = true;
    
    if (getOrderType() == PDFType.APPROVAL) {
      String[] termarray = this.term_approval.split("\n");
      for (int i = 0; i < termarray.length; i++) {
        if (!termarray[i].trim().equals("")) {
          info.addCell(buzzcell("*", this.cellfontbold));
          info.addCell(buzzcell(termarray[i], this.cellfont));
        }
      }
    } else if (getOrderType() == PDFType.QUOTATION) {
      String[] termarray = this.term_quotation.split("\n");
      for (int i = 0; i < termarray.length; i++) {
        if (!termarray[i].trim().equals("")) {
          info.addCell(buzzcell("*", this.cellfontbold));
          info.addCell(buzzcell(termarray[i], this.cellfont));
        }
      }
    } else if (getOrderType() == PDFType.OVERSEASQUOTATION) {
      String[] termarray = this.term_overseas_quotation.split("\n");
      for (int i = 0; i < termarray.length; i++) {
        if (!termarray[i].trim().equals("")) {
          info.addCell(buzzcell("*", this.cellfontbold));
          if (termarray[i].equals("This quote is an estimate and will only be valid for 10 days.")) {
            Phrase thephrase = new Phrase();
            ArrayList<Chunk> thechunks = new ArrayList();
            thechunks.add(new Chunk("This quote is an estimate and will only be valid for ", this.cellfont));
            thechunks.add(new Chunk("10 days", this.cellfontbold));
            thechunks.add(new Chunk(".", this.cellfont));
            thephrase.addAll(thechunks);
            PdfPCell mycell = new PdfPCell(thephrase);
            mycell.setBorder(0);
            mycell.setPadding(0.0F);
            mycell.setPaddingBottom(1.0F);
            info.addCell(mycell);
          } else {
            info.addCell(buzzcell(termarray[i], this.cellfont));
          }
        }
      }
    } else if (getOrderType() == PDFType.OVERSEASAPPROVAL) {
      String[] termarray = this.term_overseas_approval.split("\n");
      for (int i = 0; i < termarray.length; i++) {
        if (!termarray[i].trim().equals("")) {
          info.addCell(buzzcell("*", this.cellfontbold));
          if (termarray[i].equals("This order approval is final and will be valid for 10 days.")) {
            Phrase thephrase = new Phrase();
            ArrayList<Chunk> thechunks = new ArrayList();
            thechunks.add(new Chunk("This order approval is final and will be valid for ", this.cellfont));
            thechunks.add(new Chunk("10 days", this.cellfontbold));
            thechunks.add(new Chunk(".", this.cellfont));
            thephrase.addAll(thechunks);
            PdfPCell mycell = new PdfPCell(thephrase);
            mycell.setBorder(0);
            mycell.setPadding(0.0F);
            mycell.setPaddingBottom(1.0F);
            info.addCell(mycell);
          } else {
            info.addCell(buzzcell(termarray[i], this.cellfont));
          }
        }
      }
    }
    
    PdfPTable mytable = headerBodyPanel(header, info);
    return mytable;
  }
  
  private PdfPTable ccinfoPanel(ExtendOrderData currentorder) throws Exception {
    PdfPTable header = new PdfPTable(1);
    PdfPCell billinformationcell = headercell("Credit Card Information", this.headerfont);
    billinformationcell.setHorizontalAlignment(1);
    header.addCell(billinformationcell);
    
    PdfPTable body = new PdfPTable(1);
    
    CCCheckBox1 blindchecked = new CCCheckBox1();
    blindchecked.haveDigits(Boolean.valueOf(true));
    PdfPCell cell1 = new PdfPCell(buzzcell("        Use credit card on file                        (Last 4 digits)", this.cellfontbold));
    cell1.setCellEvent(blindchecked);
    
    CCCheckBox1 blindchecked2 = new CCCheckBox1();
    PdfPCell cell2 = new PdfPCell(buzzcell("        New credit card please send auth.", this.cellfontbold));
    cell2.setCellEvent(blindchecked2);
    
    body.addCell(cell1);
    body.addCell(cell2);
    
    PdfPTable mytable = headerBodyPanel(header, body);
    mytable.setSplitLate(true);
    mytable.setSplitRows(false);
    return mytable;
  }
  
  private PdfPTable approvalPanel() throws Exception {
    PdfPTable header = new PdfPTable(1);
    PdfPCell billinformationcell = headercell("CUSTOMER APPROVAL", this.headerfont);
    billinformationcell.setHorizontalAlignment(1);
    header.addCell(billinformationcell);
    
    PdfPTable body = new PdfPTable(1);
    
    if ((getOrderType() == PDFType.GENERIC) || (getOrderType() == PDFType.OVERSEASGENERIC)) {
      body.addCell(buzzcell("Please sign this approval for verification of accuracy.\n ", this.cellfont));
    } else {
      if (SharedData.isFaya.booleanValue()) {
        body.addCell(buzzcell("Please sign and return to Faya Corporation", this.cellfont));
      } else {
        body.addCell(buzzcell("Please sign and return to Otto International, Inc.", this.cellfont));
      }
      body.addCell(buzzcell("Production will not start without signature\n ", this.cellfont));
    }
    body.addCell(buzzcell("Customer Signature: __________________________       Date: _______________\n ", this.cellfont));
    body.addCell(buzzcell("Printed Name: _______________________________", this.cellfont));
    
    PdfPTable mytable = headerBodyPanel(header, body);
    mytable.setSplitLate(true);
    mytable.setSplitRows(false);
    return mytable;
  }
  
  private PdfPTable ordercommentPanel(ExtendOrderData currentorder) throws Exception {
    PdfPTable header = new PdfPTable(1);
    PdfPCell billinformationcell = headercell("ORDER COMMENT", this.headerfont);
    billinformationcell.setHorizontalAlignment(1);
    header.addCell(billinformationcell);
    
    PdfPTable body = new PdfPTable(1);
    
    if (((getOrderType() == PDFType.OVERSEASAPPROVAL) || (getOrderType() == PDFType.OVERSEASQUOTATION) || (getOrderType() == PDFType.APPROVAL) || (getOrderType() == PDFType.QUOTATION)) && 
      (currentorder.getCustomerInformation().getHaveDropShipment()) && (currentorder.getCustomerInformation().getDropShipmentAmount().intValue() > 0)) {
      body.addCell(buzzcell("Additional shipping charges will apply per drop ship location.", this.cellfontbold));
    }
    
    body.addCell(buzzcell(currentorder.getSpecialNotes(), this.cellfont));
    
    return headerBodyPanel(header, body);
  }
  
  private PdfPTable pricingPanel(Boolean halfrow) throws Exception {
    PdfPTable header = new PdfPTable(1);
    PdfPCell billinformationcell = headercell("PRICING", this.headerfont);
    billinformationcell.setHorizontalAlignment(1);
    header.addCell(billinformationcell);
    
    PdfPTable body = new PdfPTable(4);
    
    if ((getOrderType() == PDFType.APPROVAL) || (getOrderType() == PDFType.QUOTATION)) {
      if (halfrow.booleanValue()) {
        float[] bodyWidths = { 50.0F, 10.0F, 20.0F, 20.0F };
        body.setWidths(bodyWidths);
      } else {
        float[] bodyWidths = { 70.0F, 10.0F, 10.0F, 10.0F };
        body.setWidths(bodyWidths);
      }
      
      body.addCell(buzzcell("Detail", this.cellfontbold));
      body.addCell(buzzcell("QTY", this.cellfontbold));
      body.addCell(buzzcell("Unit Price", this.cellfontbold));
      body.addCell(buzzcell("Total", this.cellfontbold));
      body.setHeaderRows(1);
      
      for (int i = 0; i < this.domesticpricecalc.getTableRow().size() - 1; i++)
      {
        if (!((String[])this.domesticpricecalc.getTableRow().get(i))[0].equals("")) {
          body.addCell(buzzcell(((String[])this.domesticpricecalc.getTableRow().get(i))[0], this.cellfont));
        } else {
          PdfPCell mycell = buzzcell(((String[])this.domesticpricecalc.getTableRow().get(i))[1], this.cellfont);
          mycell.setPaddingLeft(20.0F);
          body.addCell(mycell);
        }
        
        if (((String[])this.domesticpricecalc.getTableRow().get(i))[2].equals("Subtotal:")) {
          if (halfrow.booleanValue()) {
            float[] bodyWidths = { 40.0F, 20.0F, 20.0F, 20.0F };
            body.setWidths(bodyWidths);
          }
          
          PdfPCell mycell = buzzcell(((String[])this.domesticpricecalc.getTableRow().get(i))[2], this.cellfontbold);
          mycell.setPaddingTop(1.0F);
          mycell.setPaddingBottom(4.0F);
          body.addCell(mycell);
          
          PdfPCell mycell2 = buzzcell(((String[])this.domesticpricecalc.getTableRow().get(i))[3], this.cellfontbold);
          mycell2.setPaddingTop(1.0F);
          mycell2.setPaddingBottom(4.0F);
          body.addCell(mycell2);
          
          PdfPCell mycell3 = buzzcell(((String[])this.domesticpricecalc.getTableRow().get(i))[4], this.cellfontbold);
          mycell3.setPaddingTop(1.0F);
          mycell3.setPaddingBottom(4.0F);
          body.addCell(mycell3);
        }
        else {
          body.addCell(buzzcell(((String[])this.domesticpricecalc.getTableRow().get(i))[2], this.cellfont));
          body.addCell(buzzcell(((String[])this.domesticpricecalc.getTableRow().get(i))[3], this.cellfont));
          body.addCell(buzzcell(((String[])this.domesticpricecalc.getTableRow().get(i))[4], this.cellfont));
        }
      }
      
      body.addCell(buzzcell("", this.cellfontbold));
      body.addCell(buzzcell("", this.cellfontbold));
      body.addCell(buzzcell(((String[])this.domesticpricecalc.getTableRow().get(this.domesticpricecalc.getTableRow().size() - 1))[3], this.cellfontbold));
      body.addCell(buzzcell(((String[])this.domesticpricecalc.getTableRow().get(this.domesticpricecalc.getTableRow().size() - 1))[4], this.cellfontbold));
    }
    else if ((getOrderType() == PDFType.OVERSEASAPPROVAL) || (getOrderType() == PDFType.OVERSEASQUOTATION)) {
      if (halfrow.booleanValue()) {
        float[] bodyWidths = { 50.0F, 10.0F, 20.0F, 20.0F };
        body.setWidths(bodyWidths);
      } else {
        float[] bodyWidths = { 70.0F, 10.0F, 10.0F, 10.0F };
        body.setWidths(bodyWidths);
      }
      
      body.addCell(buzzcell("Detail", this.cellfontbold));
      body.addCell(buzzcell("QTY", this.cellfontbold));
      body.addCell(buzzcell("Unit Price", this.cellfontbold));
      body.addCell(buzzcell("Total", this.cellfontbold));
      body.setHeaderRows(1);
      
      for (int i = 0; i < this.overseaspricecalc.getTableRow().size() - 1; i++) {
        if (!((String[])this.overseaspricecalc.getTableRow().get(i))[0].equals("")) {
          body.addCell(buzzcell(((String[])this.overseaspricecalc.getTableRow().get(i))[0], this.cellfont));
        } else {
          PdfPCell mycell = buzzcell(((String[])this.overseaspricecalc.getTableRow().get(i))[1], this.cellfont);
          mycell.setPaddingLeft(20.0F);
          body.addCell(mycell);
        }
        body.addCell(buzzcell(((String[])this.overseaspricecalc.getTableRow().get(i))[2], this.cellfont));
        body.addCell(buzzcell(((String[])this.overseaspricecalc.getTableRow().get(i))[3], this.cellfont));
        body.addCell(buzzcell(((String[])this.overseaspricecalc.getTableRow().get(i))[4], this.cellfont));
      }
      body.addCell(buzzcell("", this.cellfontbold));
      body.addCell(buzzcell("", this.cellfontbold));
      body.addCell(buzzcell(((String[])this.overseaspricecalc.getTableRow().get(this.overseaspricecalc.getTableRow().size() - 1))[3], this.cellfontbold));
      body.addCell(buzzcell(((String[])this.overseaspricecalc.getTableRow().get(this.overseaspricecalc.getTableRow().size() - 1))[4], this.cellfontbold));
    }
    

    return headerBodyPanel(header, body);
  }
  
  private PdfPTable genericheaderPanel(ExtendOrderData currentorder)
    throws Exception
  {
    if (this.cachedgenericheaderpanel == null) {
      this.cachedgenericheaderpanel = genericheaderPanel2(currentorder);
    }
    return this.cachedgenericheaderpanel;
  }
  
  private PdfPTable genericheaderPanel2(ExtendOrderData currentorder) throws Exception {
    String headertitle = "CUSTOM APPAREL PRODUCTION APPROVAL";
    
    PdfPTable maintable = new PdfPTable(1);
    PdfPTable header = new PdfPTable(3);
    float[] headerWidths = { 20.0F, 72.0F, 8.0F };
    header.setWidths(headerWidths);
    try
    {
      Image ottologo = null;
      if ((currentorder.getCustomerInformation().getCompanyLogo() != null) && (!currentorder.getCustomerInformation().getCompanyLogo().equals(""))) {
        try {
          ottologo = this.sameimages.getImage("customerlogo", "C:\\WorkFlow\\NewWorkflowData\\DomesticData/" + currentorder.getHiddenKey() + "/companylogo/" + currentorder.getCustomerInformation().getCompanyLogo(), 345, 120);
        }
        catch (Exception localException1) {}
      }
      
      if (ottologo == null) {
        try {
          ottologo = this.sameimages.getImage("customerlogo", "C:\\WorkFlow\\NewWorkflowData\\pdflogos/" + currentorder.getCustomerInformation().getEclipseAccountNumber() + "/logo.jpg", 345, 120);
        }
        catch (Exception localException2) {}
      }
      
      if (ottologo != null) {
        ottologo.scaleToFit(115.0F, 40.0F);
        PdfPCell imagecell = new PdfPCell(ottologo, false);
        imagecell.setPadding(0.0F);
        imagecell.setMinimumHeight(40.0F);
        imagecell.setBorder(0);
        imagecell.setHorizontalAlignment(1);
        imagecell.setVerticalAlignment(5);
        
        header.addCell(imagecell);
      } else {
        header.addCell(buzzcell("", this.headerfont));
      }
    } catch (Exception e) {
      header.addCell(buzzcell("", this.headerfont));
    }
    
    PdfPTable titlepanel = new PdfPTable(1);
    
    if (!currentorder.getCustomerInformation().getCompany().equals("")) {
      PdfPCell companyheader = buzzcell(currentorder.getCustomerInformation().getCompany(), this.companyheaderfont);
      
      companyheader.setVerticalAlignment(4);
      companyheader.setHorizontalAlignment(0);
      titlepanel.addCell(companyheader);
    }
    
    PdfPCell titleheader = buzzcell(headertitle, this.headerfont);
    
    titleheader.setVerticalAlignment(4);
    titleheader.setHorizontalAlignment(0);
    titlepanel.addCell(titleheader);
    
    String addressbartext = "";
    
    if (currentorder.getCustomerInformation().getVirtualSampleShowAddress()) {
      addressbartext = addressbartext + currentorder.getCustomerInformation().getBillInformation().getStreetLine1() + " " + currentorder.getCustomerInformation().getBillInformation().getStreetLine2() + "   ■ " + currentorder.getCustomerInformation().getBillInformation().getCity() + ", " + currentorder.getCustomerInformation().getBillInformation().getState() + ", " + currentorder.getCustomerInformation().getBillInformation().getZip();
      if (!currentorder.getCustomerInformation().getPhone().trim().equals("")) {
        addressbartext = addressbartext + "   ■ " + currentorder.getCustomerInformation().getPhone();
      }
      if (!currentorder.getCustomerInformation().getFax().trim().equals("")) {
        addressbartext = addressbartext + "   ■ Fax: " + currentorder.getCustomerInformation().getFax();
      }
      if (!currentorder.getCustomerInformation().getEmail().trim().equals("")) {
        addressbartext = addressbartext + "   ■ " + currentorder.getCustomerInformation().getEmail();
      }
    }
    
    PdfPCell addressheader = buzzcell(addressbartext, this.companyaddressheaderfont);
    
    addressheader.setVerticalAlignment(4);
    addressheader.setHorizontalAlignment(0);
    titlepanel.addCell(addressheader);
    
    header.addCell(paddedTableCell(titlepanel, 1));
    
    PdfPTable pageorderpanel = new PdfPTable(1);
    header.addCell(paddedTableCell(pageorderpanel, 0));
    
    maintable.addCell(roundedCell(header, getHeaderColor(), Boolean.valueOf(true)));
    return maintable;
  }
  
  private PdfPTable mainheaderPanel(ExtendOrderData currentorder)
    throws Exception
  {
    if (this.cachedmainheaderpanel == null) {
      this.cachedmainheaderpanel = mainheaderPanel2(currentorder);
    }
    return this.cachedmainheaderpanel;
  }
  
  private PdfPTable mainheaderPanel2(ExtendOrderData currentorder) throws Exception {
    String headertitle = "";
    String headermessage = "";
    if (getOrderType() == PDFType.APPROVAL) {
      headertitle = "DOMESTIC CUSTOM APPAREL ORDER APPROVAL";
      if (SharedData.isFaya.booleanValue()) {
        headermessage = "This order approval will confirm your order as shown. Please review and return the signed form to Faya Corporation to proceed with production. Thank you.";
      } else {
        headermessage = "This order approval will confirm your order as shown. Please review and return the signed form to Otto International, Inc. to proceed with production. Thank you.";
      }
    } else if (getOrderType() == PDFType.QUOTATION) {
      headertitle = "DOMESTIC CUSTOM APPAREL QUOTATION";
      headermessage = "This quotation is an estimate and will only be valid for 30 days. Please contact your customer service representative to place the order. Thank you.";
    } else if (getOrderType() == PDFType.PRODUCTIONAPPROVAL) {
      headertitle = "DOMESTIC CUSTOM APPAREL PRODUCTION APPROVAL";
      if (SharedData.isFaya.booleanValue()) {
        headermessage = "This production approval will confirm your samples & swatches as shown. Please review and return the signed form to Faya Corporation to begin production. Thank you.";
      } else {
        headermessage = "This production approval will confirm your samples & swatches as shown. Please review and return the signed form to Otto International, Inc. to begin production. Thank you.";
      }
    } else if (getOrderType() == PDFType.DIGITIZING) {
      headertitle = "DOMESTIC CUSTOM APPAREL DIGITIZING/TAPE EDIT WORK ORDER";
      if (SharedData.isFaya.booleanValue()) {
        headermessage = "This is a purchase/work order from Faya Corporation Please process and contact us immediately if there is any problem with the order. Thank you.";
      } else {
        headermessage = "This is a purchase/work order from Otto International, Inc. Please process and contact us immediately if there is any problem with the order. Thank you.";
      }
    } else if ((getOrderType() == PDFType.EMBSAMPLE) || (getOrderType() == PDFType.HEATSAMPLE) || (getOrderType() == PDFType.SCREENSAMPLE) || (getOrderType() == PDFType.DIRECTTOGARMENTSAMPLE) || (getOrderType() == PDFType.PATCHSAMPLE)) {
      headertitle = "DOMESTIC CUSTOM APPAREL PRE-PRODUCTION WORK ORDER";
      if (SharedData.isFaya.booleanValue()) {
        headermessage = "This is a purchase/work order from Faya Corporation. Please process and contact us immediately if there is any problem with the order. Thank you.";
      } else {
        headermessage = "This is a purchase/work order from Otto International, Inc. Please process and contact us immediately if there is any problem with the order. Thank you.";
      }
    } else if ((getOrderType() == PDFType.EMBORDER) || (getOrderType() == PDFType.HEATORDER) || (getOrderType() == PDFType.SCREENORDER) || (getOrderType() == PDFType.DIRECTTOGARMENTORDER) || (getOrderType() == PDFType.PATCHORDER)) {
      headertitle = "DOMESTIC CUSTOM APPAREL WORK ORDER";
      if (SharedData.isFaya.booleanValue()) {
        headermessage = "This is a purchase/work order from Faya Corporation. Please process and contact us immediately if there is any problem with the order. Thank you.";
      } else {
        headermessage = "This is a purchase/work order from Otto International, Inc. Please process and contact us immediately if there is any problem with the order. Thank you.";
      }
    } else if (getOrderType() == PDFType.ARTWORK) {
      headertitle = "DOMESTIC CUSTOM APPAREL ARTWORK";
      if (SharedData.isFaya.booleanValue()) {
        headermessage = "Internal use only. If this paper is found outside of Faya Corporation. Please contact us immediately on the whereabouts of the paper.";
      } else {
        headermessage = "Internal use only. If this paper is found outside of Otto International, Inc. Please contact us immediately on the whereabouts of the paper.";
      }
    } else if (getOrderType() == PDFType.OVERSEASQUOTATION) {
      headertitle = "OVERSEAS CUSTOM APPAREL PROGRAM QUOTATION";
      headermessage = "This quotation is an estimate and will only be valid for 10 days. Please contact your customer service representative to place the order. Thank you.";
    } else if (getOrderType() == PDFType.OVERSEASAPPROVAL) {
      headertitle = "OVERSEAS CUSTOM APPAREL ORDER APPROVAL";
      if (SharedData.isFaya.booleanValue()) {
        headermessage = "This order approval will confirm your order as shown. Please review and return the signed form to Faya Corporation to proceed with production. Thank you.";
      } else {
        headermessage = "This order approval will confirm your order as shown. Please review and return the signed form to Otto International, Inc. to proceed with production. Thank you.";
      }
    } else if (getOrderType() == PDFType.OVERSEASPRODUCTIONAPPROVAL) {
      headertitle = "OVERSEAS CUSTOM APPAREL PRODUCTION APPROVAL";
      if (SharedData.isFaya.booleanValue()) {
        headermessage = "This production approval will confirm your samples & swatches as shown. Please review and return the signed form to Faya Corporation to begin production. Thank you.";
      } else {
        headermessage = "This production approval will confirm your samples & swatches as shown. Please review and return the signed form to Otto International, Inc. to begin production. Thank you.";
      }
    } else if (getOrderType() == PDFType.OVERSEASARTWORK) {
      headertitle = "OVERSEAS CUSTOM APPAREL ARTWORK";
      if (SharedData.isFaya.booleanValue()) {
        headermessage = "Internal use only. If this paper is found outside of Faya Corporation. Please contact us immediately on the whereabouts of the paper.";
      } else {
        headermessage = "Internal use only. If this paper is found outside of Otto International, Inc. Please contact us immediately on the whereabouts of the paper.";
      }
    } else if (getOrderType() == PDFType.OVERSEASDIGITIZING) {
      headertitle = "OVERSEAS CUSTOM APPAREL DIGITIZING WORK ORDER";
      if (SharedData.isFaya.booleanValue()) {
        headermessage = "This is a purchase/work order from Faya Corporation. Please process and contact us immediately if there is any problem with the order. Thank you.";
      } else {
        headermessage = "This is a purchase/work order from Otto International, Inc. Please process and contact us immediately if there is any problem with the order. Thank you.";
      }
    } else if (getOrderType() == PDFType.OVERSEASSAMPLE) {
      headertitle = "OVERSEAS CUSTOM APPAREL PRE-PRODUCTION PURCHASE ORDER";
      if (SharedData.isFaya.booleanValue()) {
        headermessage = "This is a purchase/work order from Faya Corporation. Please process and contact us immediately if there is any problem with the order. Thank you.";
      } else {
        headermessage = "This is a purchase/work order from Otto International, Inc. Please process and contact us immediately if there is any problem with the order. Thank you.";
      }
    } else if (getOrderType() == PDFType.OVERSEASPURCHASEORDER) {
      headertitle = "OVERSEAS CUSTOM APPAREL PURCHASE ORDER";
      if (SharedData.isFaya.booleanValue()) {
        headermessage = "This is a purchase/work order from Faya Corporation. Please process and contact us immediately if there is any problem with the order. Thank you.";
      } else {
        headermessage = "This is a purchase/work order from Otto International, Inc. Please process and contact us immediately if there is any problem with the order. Thank you.";
      }
    }
    
    PdfPTable maintable = new PdfPTable(1);
    PdfPTable header = new PdfPTable(3);
    float[] headerWidths = { 10.0F, 75.0F, 15.0F };
    header.setWidths(headerWidths);
    
    Image ottologo;
    Image ottologo;
    if ((getOrderType() == PDFType.DIGITIZING) || (getOrderType() == PDFType.EMBSAMPLE) || (getOrderType() == PDFType.HEATSAMPLE) || (getOrderType() == PDFType.SCREENSAMPLE) || (getOrderType() == PDFType.DIRECTTOGARMENTSAMPLE) || (getOrderType() == PDFType.PATCHSAMPLE) || (getOrderType() == PDFType.EMBORDER) || (getOrderType() == PDFType.HEATORDER) || (getOrderType() == PDFType.SCREENORDER) || (getOrderType() == PDFType.DIRECTTOGARMENTORDER) || (getOrderType() == PDFType.PATCHORDER) || (getOrderType() == PDFType.OVERSEASDIGITIZING) || (getOrderType() == PDFType.OVERSEASSAMPLE) || (getOrderType() == PDFType.OVERSEASPURCHASEORDER)) {
      ottologo = this.sameimages.getImage("ottopdflogored", "C:\\WorkFlow\\NewWorkflowData\\images/pdf/ottologo_red.png", 120, 120); } else { Image ottologo;
      if ((getOrderType() == PDFType.OVERSEASQUOTATION) || (getOrderType() == PDFType.OVERSEASPRODUCTIONAPPROVAL) || (getOrderType() == PDFType.OVERSEASAPPROVAL) || (getOrderType() == PDFType.OVERSEASARTWORK)) {
        ottologo = this.sameimages.getImage("ottopdflogoblue", "C:\\WorkFlow\\NewWorkflowData\\images/pdf/ottologo_blue.png", 120, 120);
      } else
        ottologo = this.sameimages.getImage("ottopdflogogreen", "C:\\WorkFlow\\NewWorkflowData\\images/pdf/ottologo_green.png", 120, 120);
    }
    if (SharedData.isFaya.booleanValue()) {
      ottologo = this.sameimages.getImage("fayapdflogogreen", "C:\\WorkFlow\\NewWorkflowData\\images/pdf/fayalogo_green.png", 120, 120);
    }
    
    ottologo.scaleToFit(40.0F, 40.0F);
    PdfPCell imagecell = new PdfPCell(ottologo, false);
    imagecell.setMinimumHeight(40.0F);
    imagecell.setPadding(0.0F);
    imagecell.setBorder(0);
    imagecell.setHorizontalAlignment(1);
    imagecell.setVerticalAlignment(5);
    
    header.addCell(imagecell);
    
    PdfPTable titlepanel = new PdfPTable(1);
    PdfPCell titleheader = buzzcell(headertitle, this.mainheaderfont);
    titleheader.setPadding(1.0F);
    titleheader.setVerticalAlignment(5);
    titlepanel.addCell(titleheader);
    PdfPCell streetaddress;
    PdfPCell streetaddress;
    if (SharedData.isFaya.booleanValue()) {
      streetaddress = buzzcell("2330 S Archibald Ave   ■ Ontario CA 91761   ■ (888)868-8598   ■ Fax: (888)868-8578   ■ info@capbargain.com   ■ www.capbargain.com", this.mainheaderfontsmall);
    } else {
      streetaddress = buzzcell("3550-A Jurupa Street   ■ Ontario CA 91761   ■ (800)826-8903   ■ Fax: (800)329-6886   ■ artwork@ottocap.com   ■ www.ottocap.com", this.mainheaderfontsmall);
    }
    
    streetaddress.setPadding(1.0F);
    streetaddress.setVerticalAlignment(5);
    titlepanel.addCell(streetaddress);
    
    header.addCell(paddedTableCell(titlepanel, 0));
    
    PdfPTable pageorderpanel = new PdfPTable(1);
    pageorderpanel.addCell(buzzcell(" ", this.mainheaderfontsmall));
    PdfPCell number;
    PdfPCell number; if ((getOrderType() == PDFType.DIGITIZING) || (getOrderType() == PDFType.EMBSAMPLE) || (getOrderType() == PDFType.HEATSAMPLE) || (getOrderType() == PDFType.SCREENSAMPLE) || (getOrderType() == PDFType.DIRECTTOGARMENTSAMPLE) || (getOrderType() == PDFType.PATCHSAMPLE) || (getOrderType() == PDFType.EMBORDER) || (getOrderType() == PDFType.HEATORDER) || (getOrderType() == PDFType.SCREENORDER) || (getOrderType() == PDFType.DIRECTTOGARMENTORDER) || (getOrderType() == PDFType.PATCHORDER)) {
      number = buzzcell("Work Order # " + currentorder.getVendorInformation().getEmbroideryVendor().getWorkOrderNumber(), this.mainheaderfontordernumber); } else { PdfPCell number;
      if ((getOrderType() == PDFType.OVERSEASDIGITIZING) || (getOrderType() == PDFType.OVERSEASPURCHASEORDER)) {
        number = buzzcell("P.O. # " + ((OrderDataVendorInformation)currentorder.getVendorInformation().getOverseasVendor().get(String.valueOf(getOverseasVendorNumber()))).getWorkOrderNumber(), this.mainheaderfontordernumber); } else { PdfPCell number;
        if (getOrderType() == PDFType.OVERSEASSAMPLE) {
          number = buzzcell("Invoice # " + currentorder.getOrderNumber(), this.mainheaderfontordernumber);
        } else { PdfPCell number;
          if ((getOrderType() == PDFType.QUOTATION) || (getOrderType() == PDFType.OVERSEASQUOTATION) || (((getOrderType() == PDFType.ARTWORK) || (getOrderType() == PDFType.OVERSEASARTWORK)) && (currentorder.getOrderNumber().equals("")))) {
            number = buzzcell("Quotation # " + String.valueOf(currentorder.getHiddenKey()), this.mainheaderfontordernumber);
          } else
            number = buzzcell("Order # " + currentorder.getOrderNumber(), this.mainheaderfontordernumber);
        }
      } }
    number.setPadding(0.0F);
    number.setPaddingRight(1.0F);
    number.setHorizontalAlignment(2);
    number.setVerticalAlignment(5);
    pageorderpanel.addCell(number);
    header.addCell(paddedTableCell(pageorderpanel, 0));
    
    maintable.addCell(roundedCell(header, getHeaderColor(), Boolean.valueOf(true)));
    PdfPCell descriptioncell;
    PdfPCell descriptioncell; if (getOrderType() != PDFType.OVERSEASQUOTATION) {
      descriptioncell = buzzcell(headermessage, this.cellfont);
    } else {
      Phrase thephrase = new Phrase();
      ArrayList<Chunk> thechunks = new ArrayList();
      thechunks.add(new Chunk("This quotation is an estimate and will only be valid for ", this.cellfont));
      thechunks.add(new Chunk("10 days", this.cellfontbold));
      thechunks.add(new Chunk(". Please contact your customer service representative to place the order. Thank you.", this.cellfont));
      thephrase.addAll(thechunks);
      PdfPCell mycell = new PdfPCell(thephrase);
      mycell.setBorder(0);
      mycell.setPadding(0.0F);
      mycell.setPaddingBottom(1.0F);
      descriptioncell = mycell;
    }
    descriptioncell.setHorizontalAlignment(1);
    descriptioncell.setVerticalAlignment(5);
    descriptioncell.setPadding(0.0F);
    maintable.addCell(descriptioncell);
    return maintable;
  }
  
  private PdfPTable customeradditionalPanel(ExtendOrderData currentorder) throws Exception {
    PdfPTable mytable = new PdfPTable(3);
    float[] tableWidths = { 57.5F, 1.0F, 41.5F };
    mytable.setWidths(tableWidths);
    mytable.addCell(paddedTableCell(customerinformationPanel(currentorder), 0));
    mytable.addCell(buzzcell("", this.cellfont));
    mytable.addCell(paddedTableCell(additionalinformationPanel(currentorder), 0));
    return mytable;
  }
  
  private PdfPTable customerinformationPanel(ExtendOrderData currentorder) throws Exception {
    PdfPTable header = new PdfPTable(1);
    PdfPCell customerinformationcell = headercell("CUSTOMER INFORMATION", this.headerfont);
    customerinformationcell.setHorizontalAlignment(1);
    header.addCell(customerinformationcell);
    
    PdfPTable body = new PdfPTable(2);
    float[] bodyWidths = { 60.0F, 40.0F };
    body.setWidths(bodyWidths);
    
    PdfPTable info = new PdfPTable(2);
    float[] infoWidths = { 32.0F, 68.0F };
    info.setWidths(infoWidths);
    info.addCell(buzzcell("Contact Name:", this.cellfontbold));
    info.addCell(buzzcell(currentorder.getCustomerInformation().getContactName(), this.cellfont));
    
    info.addCell(buzzcell("Company Name:", this.cellfontbold));
    info.addCell(buzzcell(currentorder.getCustomerInformation().getCompany(), this.cellfont));
    
    info.addCell(buzzcell("Phone:", this.cellfontbold));
    info.addCell(buzzcell(currentorder.getCustomerInformation().getPhone(), this.cellfont));
    
    info.addCell(buzzcell("Fax:", this.cellfontbold));
    info.addCell(buzzcell(currentorder.getCustomerInformation().getFax(), this.cellfont));
    
    info.addCell(buzzcell("Email:", this.cellfontbold));
    info.addCell(buzzcell(currentorder.getCustomerInformation().getEmail(), this.cellfont));
    body.addCell(paddedTableCell(info, 0));
    
    PdfPTable info2 = new PdfPTable(2);
    float[] info2Widths = { 46.0F, 54.0F };
    info2.setWidths(info2Widths);
    
    if ((getOrderType() == PDFType.QUOTATION) || (getOrderType() == PDFType.OVERSEASQUOTATION) || (((getOrderType() == PDFType.ARTWORK) || (getOrderType() == PDFType.OVERSEASARTWORK)) && (currentorder.getOrderNumber().equals("")))) {
      info2.addCell(buzzcell("Quotation #:", this.cellfontbold));
      info2.addCell(buzzcell(String.valueOf(currentorder.getHiddenKey()), this.cellfont));
    } else {
      info2.addCell(buzzcell("Order #:", this.cellfontbold));
      info2.addCell(buzzcell(currentorder.getOrderNumber(), this.cellfont));
    }
    
    info2.addCell(buzzcell("Customer P.O.#:", this.cellfontbold));
    info2.addCell(buzzcell(currentorder.getCustomerPO(), this.cellfont));
    
    if (currentorder.getCustomerInformation().getEclipseAccountNumber() != null) {
      info2.addCell(buzzcell("Account:", this.cellfontbold));
      info2.addCell(buzzcell(String.valueOf(currentorder.getCustomerInformation().getEclipseAccountNumber()), this.cellfont));
    }
    
    info2.addCell(buzzcell("Term:", this.cellfontbold));
    info2.addCell(buzzcell(currentorder.getCustomerInformation().getTerms(), this.cellfont));
    
    body.addCell(paddedTableCell(info2, 0));
    
    return headerBodyPanel(header, body);
  }
  
  private PdfPTable additionalinformationPanel(ExtendOrderData currentorder) throws Exception {
    PdfPTable header = new PdfPTable(1);
    PdfPCell additionalinformationcell = headercell("ADDITIONAL INFORMATION", this.headerfont);
    additionalinformationcell.setHorizontalAlignment(1);
    header.addCell(additionalinformationcell);
    


    PdfPTable info = new PdfPTable(2);
    float[] infoWidths = { 41.0F, 59.0F };
    info.setWidths(infoWidths);
    
    if (getOrderType() == PDFType.QUOTATION) {
      info.addCell(buzzcell("Quotation Date:", this.cellfontbold));
    } else {
      info.addCell(buzzcell("Order Date:", this.cellfontbold));
    }
    info.addCell(buzzcell(fixdate(currentorder.getOrderDate()), this.cellfont));
    
    if (fixdate(currentorder.getEstimatedShipDate()) != null) {
      info.addCell(buzzcell("Estimated Ship Date:", this.cellfontbold));
      info.addCell(buzzcell(fixdate(currentorder.getEstimatedShipDate()), this.cellfont));
    }
    
    if (fixdate(currentorder.getInHandDate()) != null) {
      info.addCell(buzzcell("In-hands Date:", this.cellfontbold));
      info.addCell(buzzcell(fixdate(currentorder.getInHandDate()), this.cellfont));
    }
    
    info.addCell(buzzcell("Shipping Method:", this.cellfontbold));
    info.addCell(buzzcell(currentorder.getShippingType(), this.cellfont));
    info.addCell(buzzcell("Processing Agent Name:", this.cellfontbold));
    info.addCell(buzzcell(currentorder.getEmployeeFullName(), this.cellfont));
    info.addCell(buzzcell("Processing Agent Email:", this.cellfontbold));
    info.addCell(buzzcell(currentorder.getEmployeeEmail(), this.cellfont));
    

    return headerBodyPanel(header, info);
  }
  
  private String fixdate(Object date) {
    if (date != null) {
      String[] datearray = date.toString().split("-");
      return datearray[1] + "/" + datearray[2] + "/" + datearray[0];
    }
    return null;
  }
  
  private PdfPTable billshipPanel(ExtendOrderData currentorder) throws Exception
  {
    PdfPTable mytable = new PdfPTable(3);
    float[] tableWidths = { 49.5F, 1.0F, 49.5F };
    mytable.setWidths(tableWidths);
    mytable.addCell(paddedTableCell(billPanel(currentorder), 0));
    mytable.addCell(buzzcell("", this.cellfont));
    mytable.addCell(paddedTableCell(shipPanel(currentorder), 0));
    return mytable;
  }
  
  private PdfPTable billPanel(ExtendOrderData currentorder) throws Exception {
    PdfPTable header = new PdfPTable(1);
    PdfPCell billinformationcell = headercell("BILL INFORMATION", this.headerfont);
    billinformationcell.setHorizontalAlignment(1);
    header.addCell(billinformationcell);
    
    PdfPTable info = new PdfPTable(2);
    float[] infoWidths = { 15.0F, 85.0F };
    info.setWidths(infoWidths);
    
    if (!currentorder.getCustomerInformation().getBillInformation().getCompany().equals("")) {
      info.addCell(buzzcell("Company:", this.cellfontbold));
      info.addCell(buzzcell(currentorder.getCustomerInformation().getBillInformation().getCompany(), this.cellfont));
    }
    
    info.addCell(buzzcell("Address:", this.cellfontbold));
    info.addCell(buzzcell(currentorder.getCustomerInformation().getBillInformation().getStreetLine1() + "\n" + (currentorder.getCustomerInformation().getBillInformation().getStreetLine2().equals("") ? "" : new StringBuilder(String.valueOf(currentorder.getCustomerInformation().getBillInformation().getStreetLine2())).append("\n").toString()) + currentorder.getCustomerInformation().getBillInformation().getCity() + ", " + currentorder.getCustomerInformation().getBillInformation().getState() + " " + currentorder.getCustomerInformation().getBillInformation().getZip(), this.cellfont));
    
    return headerBodyPanel(header, info);
  }
  
  private PdfPTable shipPanel(ExtendOrderData currentorder) throws Exception {
    PdfPTable header = new PdfPTable(1);
    PdfPCell shipinformationcell = headercell("SHIP INFORMATION", this.headerfont);
    shipinformationcell.setHorizontalAlignment(1);
    header.addCell(shipinformationcell);
    
    PdfPTable body = new PdfPTable(2);
    float[] bodyWidths = { 77.0F, 23.0F };
    body.setWidths(bodyWidths);
    
    PdfPTable info = new PdfPTable(2);
    float[] infoWidths = { 20.0F, 80.0F };
    info.setWidths(infoWidths);
    
    if (!currentorder.getCustomerInformation().getShipInformation().getCompany().equals("")) {
      info.addCell(buzzcell("Company:", this.cellfontbold));
      info.addCell(buzzcell(currentorder.getCustomerInformation().getShipInformation().getCompany(), this.cellfont));
    }
    
    if (!currentorder.getCustomerInformation().getShipAttention().equals("")) {
      info.addCell(buzzcell("Attention:", this.cellfontbold));
      info.addCell(buzzcell(currentorder.getCustomerInformation().getShipAttention(), this.cellfont));
    }
    
    info.addCell(buzzcell("Address:", this.cellfontbold));
    info.addCell(buzzcell(currentorder.getCustomerInformation().getShipInformation().getStreetLine1() + "\n" + (currentorder.getCustomerInformation().getShipInformation().getStreetLine2().equals("") ? "" : new StringBuilder(String.valueOf(currentorder.getCustomerInformation().getShipInformation().getStreetLine2())).append("\n").toString()) + currentorder.getCustomerInformation().getShipInformation().getCity() + ", " + currentorder.getCustomerInformation().getShipInformation().getState() + " " + currentorder.getCustomerInformation().getShipInformation().getZip(), this.cellfont));
    body.addCell(paddedTableCell(info, 0));
    
    PdfPCell blindshippingcell = new PdfPCell(buzzcell("Blind Shipping", this.cellfontbold));
    blindshippingcell.setVerticalAlignment(6);
    blindshippingcell.setHorizontalAlignment(2);
    blindshippingcell.setPadding(1.0F);
    CheckBox blindchecked = new CheckBox();
    blindchecked.setChecked(Boolean.valueOf(currentorder.getCustomerInformation().getBlindShippingRequired()));
    blindshippingcell.setCellEvent(blindchecked);
    body.addCell(blindshippingcell);
    
    return headerBodyPanel(header, body);
  }
  
  private PdfPTable headerBodyPanel(PdfPTable header, PdfPTable body) {
    PdfPTable panel = new PdfPTable(1);
    panel.setHeaderRows(3);
    
    PdfPCell emptycell = new PdfPCell();
    emptycell.setBorder(0);
    emptycell.setPaddingTop(2.0F);
    emptycell.setPaddingBottom(2.0F);
    panel.addCell(emptycell);
    
    PdfPCell headercell = roundedCell(header, getHeaderColor(), Boolean.valueOf(true));
    headercell.setPaddingLeft(0.0F);
    headercell.setPaddingRight(0.0F);
    headercell.setPaddingTop(1.0F);
    headercell.setPaddingBottom(3.0F);
    panel.addCell(headercell);
    
    PdfPCell emptycell2 = new PdfPCell();
    emptycell2.setBorder(0);
    emptycell2.setPaddingTop(1.0F);
    emptycell2.setPaddingBottom(1.0F);
    panel.addCell(emptycell2);
    
    PdfPCell bodycell = roundedCell(body, BaseColor.BLACK, Boolean.valueOf(false));
    bodycell.setPadding(2.0F);
    panel.addCell(bodycell);
    return panel;
  }
  
  private PdfPCell roundedCell(PdfPTable table, BaseColor color, Boolean fillcolor) {
    PdfPCell mycell = new PdfPCell(table);
    mycell.setBorder(0);
    RoundRectangle boxlayout = new RoundRectangle();
    boxlayout.setColor(color);
    boxlayout.setFill(fillcolor);
    mycell.setCellEvent(boxlayout);
    return mycell;
  }
  
  private PdfPTable itemsOverseasSummaryPage(ExtendOrderData thegroupedorder) throws Exception {
    PdfPTable header = new PdfPTable(3);
    float[] headerWidths = { 38.0F, 33.0F, 29.0F };
    header.setWidths(headerWidths);
    PdfPCell productinformationcell = headercell("PRODUCT INFORMATION", this.headerfont);
    productinformationcell.setHorizontalAlignment(1);
    header.addCell(productinformationcell);
    PdfPCell designinformationcell = headercell("DESIGN INFORMATION", this.headerfont);
    designinformationcell.setHorizontalAlignment(1);
    header.addCell(designinformationcell);
    PdfPCell additionalservicescell = headercell("ADDITIONAL SERVICES", this.headerfont);
    additionalservicescell.setHorizontalAlignment(1);
    header.addCell(additionalservicescell);
    
    PdfPTable body = new PdfPTable(1);
    body.setSplitLate(false);
    for (int i = 0; i < thegroupedorder.getSetCount(); i++) {
      body.addCell(paddedTableCell(itemOverseasSummaryPage(thegroupedorder.getSet(i)), 0));
      PdfPCell linebreak = new PdfPCell();
      if (i != thegroupedorder.getSetCount() - 1) {
        linebreak.setCellEvent(new MiddleLine());
        linebreak.setBorder(0);
        body.addCell(linebreak);
      }
    }
    
    PdfPTable mytable = headerBodyPanel(header, body);
    mytable.setTotalWidth(this.document.getPageSize().getWidth() - this.document.leftMargin() - this.document.rightMargin());
    return mytable;
  }
  
  private PdfPTable itemsSummaryPage(ExtendOrderData thegroupedorder) throws Exception {
    PdfPTable header = new PdfPTable(3);
    float[] headerWidths = { 22.0F, 49.0F, 29.0F };
    header.setWidths(headerWidths);
    PdfPCell productinformationcell = headercell("PRODUCT INFORMATION", this.headerfont);
    productinformationcell.setHorizontalAlignment(1);
    header.addCell(productinformationcell);
    PdfPCell designinformationcell = headercell("DESIGN INFORMATION", this.headerfont);
    designinformationcell.setHorizontalAlignment(1);
    header.addCell(designinformationcell);
    PdfPCell additionalservicescell = headercell("ADDITIONAL SERVICES", this.headerfont);
    additionalservicescell.setHorizontalAlignment(1);
    header.addCell(additionalservicescell);
    
    PdfPTable body = new PdfPTable(1);
    body.setSplitLate(false);
    for (int i = 0; i < thegroupedorder.getSetCount(); i++) {
      body.addCell(paddedTableCell(itemSummaryPage(thegroupedorder.getSet(i)), 0));
      PdfPCell linebreak = new PdfPCell();
      if (i != thegroupedorder.getSetCount() - 1) {
        linebreak.setCellEvent(new MiddleLine());
        linebreak.setBorder(0);
        body.addCell(linebreak);
      }
    }
    
    PdfPTable mytable = headerBodyPanel(header, body);
    mytable.setTotalWidth(this.document.getPageSize().getWidth() - this.document.leftMargin() - this.document.rightMargin());
    return mytable;
  }
  
  private PdfPTable itemsArtworkSummaryPage(ExtendOrderData currentorder) throws Exception {
    PdfPTable header = new PdfPTable(3);
    float[] headerWidths = { 22.0F, 62.0F, 16.0F };
    header.setWidths(headerWidths);
    PdfPCell productinformationcell = headercell("PRODUCT INFORMATION", this.headerfont);
    productinformationcell.setHorizontalAlignment(1);
    
    header.addCell(productinformationcell);
    PdfPCell designinformationcell = headercell("DESIGN INFORMATION", this.headerfont);
    
    designinformationcell.setHorizontalAlignment(1);
    header.addCell(designinformationcell);
    PdfPCell additionalservicescell = headercell("LOGO PREVIEW", this.headerfont);
    
    additionalservicescell.setHorizontalAlignment(1);
    header.addCell(additionalservicescell);
    
    PdfPTable body = new PdfPTable(1);
    body.setSplitLate(false);
    for (int i = 0; i < currentorder.getSetCount(); i++) {
      body.addCell(paddedTableCell(itemArtworkSummaryPage(currentorder, i), 0));
      PdfPCell linebreak = new PdfPCell();
      if (i != currentorder.getSetCount() - 1) {
        linebreak.setCellEvent(new MiddleLine());
        linebreak.setBorder(0);
        body.addCell(linebreak);
      }
    }
    
    return headerBodyPanel(header, body);
  }
  
  private PdfPCell paddedTableCell(PdfPTable table, int padding) {
    PdfPCell mycell = new PdfPCell(table);
    mycell.setBorder(0);
    mycell.setPadding(padding);
    return mycell;
  }
  
  private PdfPCell headercell(String text, Font font) {
    PdfPCell mycell = new PdfPCell(new Phrase(text, font));
    mycell.setBorder(0);
    mycell.setPadding(0.0F);
    return mycell;
  }
  
  private PdfPCell buzzcell(String text, Font font) {
    PdfPCell mycell = new PdfPCell(new Phrase(text, font));
    mycell.setBorder(0);
    mycell.setPadding(0.0F);
    mycell.setPaddingBottom(1.0F);
    return mycell;
  }
  


  PdfPTable cachedmainheaderpanel;
  

  private PdfPTable itemArtworkSummaryPage(ExtendOrderData currentorder, int item)
    throws Exception
  {
    PdfPTable itemTable = new PdfPTable(2);
    float[] itemTableWidths = { 22.0F, 78.0F };
    itemTable.setWidths(itemTableWidths);
    itemTable.setSplitLate(false);
    PdfPTable itemdesc = new PdfPTable(2);
    float[] itemdescWidths = { 38.0F, 62.0F };
    itemdesc.setWidths(itemdescWidths);
    
















    if ((currentorder.getSet(item).getItem(0).getStyleNumber().equals("nonotto")) || (currentorder.getSet(item).getItem(0).getStyleNumber().equals("nonottoflat"))) {
      itemdesc.addCell(buzzcell("Style:", this.cellfontbold));
      if ((currentorder.getSet(item).getItem(0).getCustomStyleName() != null) && (!currentorder.getSet(item).getItem(0).getCustomStyleName().equals(""))) {
        itemdesc.addCell(buzzcell("Non Otto: " + currentorder.getSet(item).getItem(0).getCustomStyleName(), this.cellfont));
      } else {
        itemdesc.addCell(buzzcell("Non Otto", this.cellfont));
      }
      
      if (!currentorder.getSet(item).getItem(0).getColorCodeName().equals("")) {
        itemdesc.addCell(buzzcell("Color Name:", this.cellfontbold));
        itemdesc.addCell(buzzcell(currentorder.getSet(item).getItem(0).getColorCodeName(), this.cellfont));
      }
      
      if (!currentorder.getSet(item).getItem(0).getColorCode().equals("")) {
        itemdesc.addCell(buzzcell("Color #:", this.cellfontbold));
        itemdesc.addCell(buzzcell(currentorder.getSet(item).getItem(0).getColorCode(), this.cellfont));
      }
      
      if (!currentorder.getSet(item).getItem(0).getSize().equals("")) {
        itemdesc.addCell(buzzcell("Size:", this.cellfontbold));
        itemdesc.addCell(buzzcell(currentorder.getSet(item).getItem(0).getSize(), this.cellfont));
      }
      
      itemdesc.addCell(buzzcell("Quantity:", this.cellfontbold));
      itemdesc.addCell(buzzcell(String.valueOf(currentorder.getSet(item).getItem(0).getQuantity()), this.cellfont));
    }
    else if (currentorder.getOrderType() == OrderData.OrderType.DOMESTIC) {
      Chunk chunk = new Chunk("Virtual Preview Link", this.cellhyperlink);
      chunk.setUnderline(0.5F, -1.0F);
      Anchor anchor = new Anchor(chunk);
      anchor.setReference(this.request.getRequestURL().subSequence(0, this.request.getRequestURL().indexOf(this.request.getServletPath())) + "/newworkflow/artworkproductpreview/?hiddenkey=" + currentorder.getHiddenKey() + "&set=" + currentorder.getSet(item).getItem(0).getOrgSetNum() + "&item=" + currentorder.getSet(item).getItem(0).getOrgItemNum() + "&oldstyle=" + getUrlSafe(currentorder.getSet(item).getItem(0).getStyleNumber()) + "&oldcolor=" + getUrlSafe(currentorder.getSet(item).getItem(0).getColorCode()));
      anchor.setName("previewlink" + item);
      Phrase myurl = new Phrase();
      myurl.add(anchor);
      PdfPCell myurlcell = new PdfPCell(myurl);
      myurlcell.setBorder(0);
      myurlcell.setPadding(0.0F);
      myurlcell.setPaddingBottom(1.0F);
      myurlcell.setColspan(2);
      itemdesc.addCell(myurlcell);
      
      if (SharedData.isFaya.booleanValue()) {
        Chunk chunk2 = new Chunk("OTTO Preview Link", this.cellhyperlink);
        chunk2.setUnderline(0.5F, -1.0F);
        Anchor anchor2 = new Anchor(chunk2);
        anchor2.setReference("http://ottowf01.otto2000server.com:8080/newworkflow//newworkflow/artworkproductpreview/?set=" + currentorder.getSet(item).getItem(0).getOrgSetNum() + "&item=" + currentorder.getSet(item).getItem(0).getOrgItemNum() + "&oldstyle=" + getUrlSafe(currentorder.getSet(item).getItem(0).getStyleNumber()) + "&oldcolor=" + getUrlSafe(currentorder.getSet(item).getItem(0).getColorCode()));
        anchor2.setName("previewlink" + item);
        Phrase myurl2 = new Phrase();
        myurl.add(anchor2);
        PdfPCell myurlcell2 = new PdfPCell(myurl2);
        myurlcell2.setBorder(0);
        myurlcell2.setPadding(0.0F);
        myurlcell2.setPaddingBottom(1.0F);
        myurlcell2.setColspan(2);
        itemdesc.addCell(myurlcell2);
      }
      
      itemdesc.addCell(buzzcell("Style:", this.cellfontbold));
      itemdesc.addCell(buzzcell(currentorder.getSet(item).getItem(0).getStyleNumber(), this.cellfont));
      
      itemdesc.addCell(buzzcell("Color Name:", this.cellfontbold));
      itemdesc.addCell(buzzcell(currentorder.getSet(item).getItem(0).getColorCodeName(), this.cellfont));
      
      itemdesc.addCell(buzzcell("Color #:", this.cellfontbold));
      itemdesc.addCell(buzzcell(currentorder.getSet(item).getItem(0).getColorCode(), this.cellfont));
      
      if (!currentorder.getSet(item).getItem(0).getSize().equals("")) {
        itemdesc.addCell(buzzcell("Size:", this.cellfontbold));
        itemdesc.addCell(buzzcell(currentorder.getSet(item).getItem(0).getSize(), this.cellfont));
      }
      
      itemdesc.addCell(buzzcell("Quantity:", this.cellfontbold));
      itemdesc.addCell(buzzcell(String.valueOf(currentorder.getSet(item).getItem(0).getQuantity()), this.cellfont));

    }
    else
    {

      Chunk chunk = new Chunk("Virtual Preview Link", this.cellhyperlink);
      chunk.setUnderline(0.5F, -1.0F);
      Anchor anchor = new Anchor(chunk);
      anchor.setReference(this.request.getRequestURL().subSequence(0, this.request.getRequestURL().indexOf(this.request.getServletPath())) + "/newworkflow/artworkproductpreview/?hiddenkey=" + currentorder.getHiddenKey() + "&set=" + currentorder.getSet(item).getItem(0).getOrgSetNum() + "&item=" + currentorder.getSet(item).getItem(0).getOrgItemNum() + "&oldstyle=" + getUrlSafe(currentorder.getSet(item).getItem(0).getStyleNumber()) + "&oldcolor=" + getUrlSafe(currentorder.getSet(item).getItem(0).getColorCode()) + "&oldfrontpanelcolor=" + getUrlSafe(currentorder.getSet(item).getItem(0).getFrontPanelColor()) + "&oldsidebackpanelcolor=" + getUrlSafe(currentorder.getSet(item).getItem(0).getSideBackPanelColor()) + "&oldbackpanelcolor=" + getUrlSafe(currentorder.getSet(item).getItem(0).getBackPanelColor()) + "&oldsidepanelcolor=" + getUrlSafe(currentorder.getSet(item).getItem(0).getSidePanelColor()) + "&oldvisorstylenumber=" + getUrlSafe(currentorder.getSet(item).getItem(0).getVisorStyleNumber()) + 
        "&oldprimaryvisorcolor=" + getUrlSafe(currentorder.getSet(item).getItem(0).getPrimaryVisorColor()) + "&oldvisortrimcolor=" + getUrlSafe(currentorder.getSet(item).getItem(0).getVisorTrimColor()) + "&oldvisorsandwichcolor=" + getUrlSafe(currentorder.getSet(item).getItem(0).getVisorSandwichColor()) + "&oldundervisorcolor=" + getUrlSafe(currentorder.getSet(item).getItem(0).getUndervisorColor()) + "&olddistressedvisorinsidecolor=" + getUrlSafe(currentorder.getSet(item).getItem(0).getDistressedVisorInsideColor()) + "&oldclosurestylenumber=" + getUrlSafe(currentorder.getSet(item).getItem(0).getClosureStyleNumber()) + "&oldclosurestrapcolor=" + getUrlSafe(currentorder.getSet(item).getItem(0).getClosureStrapColor()) + "&oldeyeletstylenumber=" + getUrlSafe(currentorder.getSet(item).getItem(0).getEyeletStyleNumber()) + "&oldfronteyeletcolor=" + getUrlSafe(currentorder.getSet(item).getItem(0).getFrontEyeletColor()) + "&oldsidebackeyeletcolor=" + 
        getUrlSafe(currentorder.getSet(item).getItem(0).getSideBackEyeletColor()) + "&oldbackeyeletcolor=" + getUrlSafe(currentorder.getSet(item).getItem(0).getBackEyeletColor()) + "&oldsideeyeletcolor=" + getUrlSafe(currentorder.getSet(item).getItem(0).getSideEyeletColor()) + "&oldbuttoncolor=" + getUrlSafe(currentorder.getSet(item).getItem(0).getButtonColor()) + "&oldinnertapingcolor=" + getUrlSafe(currentorder.getSet(item).getItem(0).getInnerTapingColor()) + "&oldsweatbandstylenumber=" + getUrlSafe(currentorder.getSet(item).getItem(0).getSweatbandStyleNumber()) + "&oldsweatbandcolor=" + getUrlSafe(currentorder.getSet(item).getItem(0).getSweatbandColor()) + "&oldsweatbandstripecolor=" + getUrlSafe(currentorder.getSet(item).getItem(0).getSweatbandStripeColor()) + "&oldpanelstitchcolor=" + getUrlSafe(currentorder.getSet(item).getItem(0).getPanelStitchColor()) + "&oldvisorrowstitching=" + getUrlSafe(currentorder.getSet(item).getItem(0).getVisorRowStitching()) + 
        "&oldvisorstitchcolor=" + getUrlSafe(currentorder.getSet(item).getItem(0).getVisorStitchColor()) + "&oldprofile=" + getUrlSafe(currentorder.getSet(item).getItem(0).getProfile()) + "&oldfrontpanelfabric=" + getUrlSafe(currentorder.getSet(item).getItem(0).getFrontPanelFabric()) + "&oldsidebackpanelfabric=" + getUrlSafe(currentorder.getSet(item).getItem(0).getSideBackPanelFabric()) + "&oldbackpanelfabric=" + getUrlSafe(currentorder.getSet(item).getItem(0).getBackPanelFabric()) + "&oldsidepanelfabric=" + getUrlSafe(currentorder.getSet(item).getItem(0).getSidePanelFabric()) + "&oldcrownconstruction=" + getUrlSafe(currentorder.getSet(item).getItem(0).getCrownConstruction()) + "&oldnumberofpanels=" + getUrlSafe(currentorder.getSet(item).getItem(0).getNumberOfPanels()));
      anchor.setName("previewlink" + item);
      Phrase myurl = new Phrase();
      myurl.add(anchor);
      PdfPCell myurlcell = new PdfPCell(myurl);
      myurlcell.setBorder(0);
      myurlcell.setPadding(0.0F);
      myurlcell.setPaddingBottom(1.0F);
      myurlcell.setColspan(2);
      itemdesc.addCell(myurlcell);
      

      if (SharedData.isFaya.booleanValue()) {
        Chunk chunk2 = new Chunk("OTTO Preview Link", this.cellhyperlink);
        chunk2.setUnderline(0.5F, -1.0F);
        Anchor anchor2 = new Anchor(chunk2);
        anchor2.setReference("http://ottowf01.otto2000server.com:8080/newworkflow/newworkflow/artworkproductpreview/?set=" + currentorder.getSet(item).getItem(0).getOrgSetNum() + "&item=" + currentorder.getSet(item).getItem(0).getOrgItemNum() + "&oldstyle=" + getUrlSafe(currentorder.getSet(item).getItem(0).getStyleNumber()) + "&oldcolor=" + getUrlSafe(currentorder.getSet(item).getItem(0).getColorCode()) + "&oldfrontpanelcolor=" + getUrlSafe(currentorder.getSet(item).getItem(0).getFrontPanelColor()) + "&oldsidebackpanelcolor=" + getUrlSafe(currentorder.getSet(item).getItem(0).getSideBackPanelColor()) + "&oldbackpanelcolor=" + getUrlSafe(currentorder.getSet(item).getItem(0).getBackPanelColor()) + "&oldsidepanelcolor=" + getUrlSafe(currentorder.getSet(item).getItem(0).getSidePanelColor()) + "&oldvisorstylenumber=" + getUrlSafe(currentorder.getSet(item).getItem(0).getVisorStyleNumber()) + 
          "&oldprimaryvisorcolor=" + getUrlSafe(currentorder.getSet(item).getItem(0).getPrimaryVisorColor()) + "&oldvisortrimcolor=" + getUrlSafe(currentorder.getSet(item).getItem(0).getVisorTrimColor()) + "&oldvisorsandwichcolor=" + getUrlSafe(currentorder.getSet(item).getItem(0).getVisorSandwichColor()) + "&oldundervisorcolor=" + getUrlSafe(currentorder.getSet(item).getItem(0).getUndervisorColor()) + "&olddistressedvisorinsidecolor=" + getUrlSafe(currentorder.getSet(item).getItem(0).getDistressedVisorInsideColor()) + "&oldclosurestylenumber=" + getUrlSafe(currentorder.getSet(item).getItem(0).getClosureStyleNumber()) + "&oldclosurestrapcolor=" + getUrlSafe(currentorder.getSet(item).getItem(0).getClosureStrapColor()) + "&oldeyeletstylenumber=" + getUrlSafe(currentorder.getSet(item).getItem(0).getEyeletStyleNumber()) + "&oldfronteyeletcolor=" + getUrlSafe(currentorder.getSet(item).getItem(0).getFrontEyeletColor()) + "&oldsidebackeyeletcolor=" + 
          getUrlSafe(currentorder.getSet(item).getItem(0).getSideBackEyeletColor()) + "&oldbackeyeletcolor=" + getUrlSafe(currentorder.getSet(item).getItem(0).getBackEyeletColor()) + "&oldsideeyeletcolor=" + getUrlSafe(currentorder.getSet(item).getItem(0).getSideEyeletColor()) + "&oldbuttoncolor=" + getUrlSafe(currentorder.getSet(item).getItem(0).getButtonColor()) + "&oldinnertapingcolor=" + getUrlSafe(currentorder.getSet(item).getItem(0).getInnerTapingColor()) + "&oldsweatbandstylenumber=" + getUrlSafe(currentorder.getSet(item).getItem(0).getSweatbandStyleNumber()) + "&oldsweatbandcolor=" + getUrlSafe(currentorder.getSet(item).getItem(0).getSweatbandColor()) + "&oldsweatbandstripecolor=" + getUrlSafe(currentorder.getSet(item).getItem(0).getSweatbandStripeColor()) + "&oldpanelstitchcolor=" + getUrlSafe(currentorder.getSet(item).getItem(0).getPanelStitchColor()) + "&oldvisorrowstitching=" + getUrlSafe(currentorder.getSet(item).getItem(0).getVisorRowStitching()) + 
          "&oldvisorstitchcolor=" + getUrlSafe(currentorder.getSet(item).getItem(0).getVisorStitchColor()) + "&oldprofile=" + getUrlSafe(currentorder.getSet(item).getItem(0).getProfile()) + "&oldfrontpanelfabric=" + getUrlSafe(currentorder.getSet(item).getItem(0).getFrontPanelFabric()) + "&oldsidebackpanelfabric=" + getUrlSafe(currentorder.getSet(item).getItem(0).getSideBackPanelFabric()) + "&oldbackpanelfabric=" + getUrlSafe(currentorder.getSet(item).getItem(0).getBackPanelFabric()) + "&oldsidepanelfabric=" + getUrlSafe(currentorder.getSet(item).getItem(0).getSidePanelFabric()) + "&oldcrownconstruction=" + getUrlSafe(currentorder.getSet(item).getItem(0).getCrownConstruction()) + "&oldnumberofpanels=" + getUrlSafe(currentorder.getSet(item).getItem(0).getNumberOfPanels()));
        anchor2.setName("previewlink" + item);
        Phrase myurl2 = new Phrase();
        myurl2.add(anchor2);
        PdfPCell myurlcell2 = new PdfPCell(myurl2);
        myurlcell2.setBorder(0);
        myurlcell2.setPadding(0.0F);
        myurlcell2.setPaddingBottom(1.0F);
        myurlcell2.setColspan(2);
        itemdesc.addCell(myurlcell2);
      }
      

      PdfPCell stylecell = buzzcell("Style:", this.cellfontbold);
      stylecell.setColspan(2);
      itemdesc.addCell(stylecell);
      PdfPCell stylecellinfo = buzzcell(currentorder.getSet(item).getItem(0).getStyleNumber(), this.cellfont);
      stylecellinfo.setColspan(2);
      stylecellinfo.setPaddingLeft(10.0F);
      itemdesc.addCell(stylecellinfo);
      
      if (!currentorder.getSet(item).getItem(0).getProfile().equals("")) {
        PdfPCell cell = buzzcell("Profile:", this.cellfontbold);
        cell.setColspan(2);
        itemdesc.addCell(cell);
        PdfPCell cellinfo = buzzcell(currentorder.getSet(item).getItem(0).getProfile(), this.cellfont);
        cellinfo.setColspan(2);
        cellinfo.setPaddingLeft(10.0F);
        itemdesc.addCell(cellinfo);
      }
      
      if (!currentorder.getSet(item).getItem(0).getNumberOfPanels().equals("")) {
        PdfPCell cell = buzzcell("Number Of Panels:", this.cellfontbold);
        cell.setColspan(2);
        itemdesc.addCell(cell);
        PdfPCell cellinfo = buzzcell(currentorder.getSet(item).getItem(0).getNumberOfPanels(), this.cellfont);
        cellinfo.setColspan(2);
        cellinfo.setPaddingLeft(10.0F);
        itemdesc.addCell(cellinfo);
      }
      
      if (!currentorder.getSet(item).getItem(0).getMaterial().equals("")) {
        PdfPCell cell = buzzcell("Material:", this.cellfontbold);
        cell.setColspan(2);
        itemdesc.addCell(cell);
        PdfPCell cellinfo = buzzcell(currentorder.getSet(item).getItem(0).getMaterial(), this.cellfont);
        cellinfo.setColspan(2);
        cellinfo.setPaddingLeft(10.0F);
        itemdesc.addCell(cellinfo);
      }
      

      if (!currentorder.getSet(item).getItem(0).getCrownConstruction().equals("")) {
        PdfPCell cell = buzzcell("Crown Construction:", this.cellfontbold);
        cell.setColspan(2);
        itemdesc.addCell(cell);
        PdfPCell cellinfo = buzzcell(currentorder.getSet(item).getItem(0).getCrownConstruction(), this.cellfont);
        cellinfo.setColspan(2);
        cellinfo.setPaddingLeft(10.0F);
        itemdesc.addCell(cellinfo);
      }
      
      if (!currentorder.getSet(item).getItem(0).getColorCode().equals("")) {
        PdfPCell cell = buzzcell("Color:", this.cellfontbold);
        cell.setColspan(2);
        itemdesc.addCell(cell);
        PdfPCell cellinfo = buzzcell(currentorder.getSet(item).getItem(0).getColorCode() + " - " + currentorder.getSet(item).getItem(0).getColorCodeName(), this.cellfont);
        cellinfo.setColspan(2);
        cellinfo.setPaddingLeft(10.0F);
        itemdesc.addCell(cellinfo);
      }
      
      if (!currentorder.getSet(item).getItem(0).getSize().equals("")) {
        PdfPCell cell = buzzcell("Size:", this.cellfontbold);
        cell.setColspan(2);
        itemdesc.addCell(cell);
        PdfPCell cellinfo = buzzcell(currentorder.getSet(item).getItem(0).getSize(), this.cellfont);
        cellinfo.setColspan(2);
        cellinfo.setPaddingLeft(10.0F);
        itemdesc.addCell(cellinfo);
      }
      
      if (currentorder.getSet(item).getItem(0).getQuantity() != null) {
        PdfPCell cell = buzzcell("Quantity:", this.cellfontbold);
        cell.setColspan(2);
        itemdesc.addCell(cell);
        PdfPCell cellinfo = buzzcell(String.valueOf(currentorder.getSet(item).getItem(0).getQuantity()), this.cellfont);
        cellinfo.setColspan(2);
        cellinfo.setPaddingLeft(10.0F);
        itemdesc.addCell(cellinfo);
      }
      
      if (!currentorder.getSet(item).getItem(0).getFrontPanelColor().equals("")) {
        PdfPCell cell = buzzcell("Front Panel Color:", this.cellfontbold);
        cell.setColspan(2);
        itemdesc.addCell(cell);
        PdfPCell cellinfo = buzzcell(currentorder.getSet(item).getItem(0).getFrontPanelColor() + " - " + currentorder.getSet(item).getItem(0).getFrontPanelColorName(), this.cellfont);
        cellinfo.setColspan(2);
        cellinfo.setPaddingLeft(10.0F);
        itemdesc.addCell(cellinfo);
      }
      if (!currentorder.getSet(item).getItem(0).getSideBackPanelColor().equals("")) {
        PdfPCell cell = buzzcell("Side & Back Panel Color:", this.cellfontbold);
        cell.setColspan(2);
        itemdesc.addCell(cell);
        PdfPCell cellinfo = buzzcell(currentorder.getSet(item).getItem(0).getSideBackPanelColor() + " - " + currentorder.getSet(item).getItem(0).getSideBackPanelColorName(), this.cellfont);
        cellinfo.setColspan(2);
        cellinfo.setPaddingLeft(10.0F);
        itemdesc.addCell(cellinfo);
      }
      if (!currentorder.getSet(item).getItem(0).getBackPanelColor().equals("")) {
        PdfPCell cell = buzzcell("Back Panel Color:", this.cellfontbold);
        cell.setColspan(2);
        itemdesc.addCell(cell);
        PdfPCell cellinfo = buzzcell(currentorder.getSet(item).getItem(0).getBackPanelColor() + " - " + currentorder.getSet(item).getItem(0).getBackPanelColorName(), this.cellfont);
        cellinfo.setColspan(2);
        cellinfo.setPaddingLeft(10.0F);
        itemdesc.addCell(cellinfo);
      }
      if (!currentorder.getSet(item).getItem(0).getSidePanelColor().equals("")) {
        PdfPCell cell = buzzcell("Side Panel Color:", this.cellfontbold);
        cell.setColspan(2);
        itemdesc.addCell(cell);
        PdfPCell cellinfo = buzzcell(currentorder.getSet(item).getItem(0).getSidePanelColor() + " - " + currentorder.getSet(item).getItem(0).getSidePanelColorName(), this.cellfont);
        cellinfo.setColspan(2);
        cellinfo.setPaddingLeft(10.0F);
        itemdesc.addCell(cellinfo);
      }
      
      if (!currentorder.getSet(item).getItem(0).getVisorStyleNumber().equals("")) {
        PdfPCell cell = buzzcell("Visor Style #:", this.cellfontbold);
        cell.setColspan(2);
        itemdesc.addCell(cell);
        PdfPCell cellinfo = buzzcell(currentorder.getSet(item).getItem(0).getVisorStyleNumberName(), this.cellfont);
        cellinfo.setColspan(2);
        cellinfo.setPaddingLeft(10.0F);
        itemdesc.addCell(cellinfo);
      }
      
      if (!currentorder.getSet(item).getItem(0).getPrimaryVisorColor().equals("")) {
        PdfPCell cell = buzzcell("Primary Visor Color:", this.cellfontbold);
        cell.setColspan(2);
        itemdesc.addCell(cell);
        PdfPCell cellinfo = buzzcell(currentorder.getSet(item).getItem(0).getPrimaryVisorColor() + " - " + currentorder.getSet(item).getItem(0).getPrimaryVisorColorName(), this.cellfont);
        cellinfo.setColspan(2);
        cellinfo.setPaddingLeft(10.0F);
        itemdesc.addCell(cellinfo);
      }
      if (!currentorder.getSet(item).getItem(0).getVisorTrimColor().equals("")) {
        PdfPCell cell = buzzcell("Visor Trim/Edge Color:", this.cellfontbold);
        cell.setColspan(2);
        itemdesc.addCell(cell);
        PdfPCell cellinfo = buzzcell(currentorder.getSet(item).getItem(0).getVisorTrimColor() + " - " + currentorder.getSet(item).getItem(0).getVisorTrimColorName(), this.cellfont);
        cellinfo.setColspan(2);
        cellinfo.setPaddingLeft(10.0F);
        itemdesc.addCell(cellinfo);
      }
      if (!currentorder.getSet(item).getItem(0).getVisorSandwichColor().equals("")) {
        PdfPCell cell = buzzcell("Visor Sandwich Color:", this.cellfontbold);
        cell.setColspan(2);
        itemdesc.addCell(cell);
        PdfPCell cellinfo = buzzcell(currentorder.getSet(item).getItem(0).getVisorSandwichColor() + " - " + currentorder.getSet(item).getItem(0).getVisorSandwichColorName(), this.cellfont);
        cellinfo.setColspan(2);
        cellinfo.setPaddingLeft(10.0F);
        itemdesc.addCell(cellinfo);
      }
      if (!currentorder.getSet(item).getItem(0).getUndervisorColor().equals("")) {
        PdfPCell cell = buzzcell("Undervisor Color:", this.cellfontbold);
        cell.setColspan(2);
        itemdesc.addCell(cell);
        PdfPCell cellinfo = buzzcell(currentorder.getSet(item).getItem(0).getUndervisorColor() + " - " + currentorder.getSet(item).getItem(0).getUndervisorColorName(), this.cellfont);
        cellinfo.setColspan(2);
        cellinfo.setPaddingLeft(10.0F);
        itemdesc.addCell(cellinfo);
      }
      if (!currentorder.getSet(item).getItem(0).getDistressedVisorInsideColor().equals("")) {
        PdfPCell cell = buzzcell("Distressed Visor Inside Color:", this.cellfontbold);
        cell.setColspan(2);
        itemdesc.addCell(cell);
        PdfPCell cellinfo = buzzcell(currentorder.getSet(item).getItem(0).getDistressedVisorInsideColor() + " - " + currentorder.getSet(item).getItem(0).getDistressedVisorInsideColorName(), this.cellfont);
        cellinfo.setColspan(2);
        cellinfo.setPaddingLeft(10.0F);
        itemdesc.addCell(cellinfo);
      }
      
      if (!currentorder.getSet(item).getItem(0).getClosureStyleNumber().equals("")) {
        PdfPCell cell = buzzcell("Closure Style #:", this.cellfontbold);
        cell.setColspan(2);
        itemdesc.addCell(cell);
        PdfPCell cellinfo = buzzcell(currentorder.getSet(item).getItem(0).getClosureStyleNumberName(), this.cellfont);
        cellinfo.setColspan(2);
        cellinfo.setPaddingLeft(10.0F);
        itemdesc.addCell(cellinfo);
      }
      
      if (!currentorder.getSet(item).getItem(0).getClosureStrapColor().equals("")) {
        PdfPCell cell = buzzcell("Closure Strap Color:", this.cellfontbold);
        cell.setColspan(2);
        itemdesc.addCell(cell);
        PdfPCell cellinfo = buzzcell(currentorder.getSet(item).getItem(0).getClosureStrapColor() + " - " + currentorder.getSet(item).getItem(0).getClosureStrapColorName(), this.cellfont);
        cellinfo.setColspan(2);
        cellinfo.setPaddingLeft(10.0F);
        itemdesc.addCell(cellinfo);
      }
      
      if (!currentorder.getSet(item).getItem(0).getEyeletStyleNumber().equals("")) {
        PdfPCell cell = buzzcell("Eyelet Style #:", this.cellfontbold);
        cell.setColspan(2);
        itemdesc.addCell(cell);
        PdfPCell cellinfo = buzzcell(currentorder.getSet(item).getItem(0).getEyeletStyleNumberName(), this.cellfont);
        cellinfo.setColspan(2);
        cellinfo.setPaddingLeft(10.0F);
        itemdesc.addCell(cellinfo);
      }
      
      if ((currentorder.getSet(item).getItem(0).getEyeletStyleNumber().equals("E1")) && (currentorder.getSet(item).getItem(0).getSideBackEyeletColor().equals("")) && (currentorder.getSet(item).getItem(0).getBackEyeletColor().equals("")) && (currentorder.getSet(item).getItem(0).getSideEyeletColor().equals(""))) {
        if (!currentorder.getSet(item).getItem(0).getFrontEyeletColor().equals("")) {
          PdfPCell cell = buzzcell("Eyelet Color:", this.cellfontbold);
          cell.setColspan(2);
          itemdesc.addCell(cell);
          PdfPCell cellinfo = buzzcell(currentorder.getSet(item).getItem(0).getFrontEyeletColor() + " - " + currentorder.getSet(item).getItem(0).getFrontEyeletColorName(), this.cellfont);
          cellinfo.setColspan(2);
          cellinfo.setPaddingLeft(10.0F);
          itemdesc.addCell(cellinfo);
        }
      }
      else if (!currentorder.getSet(item).getItem(0).getFrontEyeletColor().equals("")) {
        PdfPCell cell = buzzcell("Front Eyelet Color:", this.cellfontbold);
        cell.setColspan(2);
        itemdesc.addCell(cell);
        PdfPCell cellinfo = buzzcell(currentorder.getSet(item).getItem(0).getFrontEyeletColor() + " - " + currentorder.getSet(item).getItem(0).getFrontEyeletColorName(), this.cellfont);
        cellinfo.setColspan(2);
        cellinfo.setPaddingLeft(10.0F);
        itemdesc.addCell(cellinfo);
      }
      

      if (!currentorder.getSet(item).getItem(0).getSideBackEyeletColor().equals("")) {
        PdfPCell cell = buzzcell("Side & Back Eyelet Color:", this.cellfontbold);
        cell.setColspan(2);
        itemdesc.addCell(cell);
        PdfPCell cellinfo = buzzcell(currentorder.getSet(item).getItem(0).getSideBackEyeletColor() + " - " + currentorder.getSet(item).getItem(0).getSideBackEyeletColorName(), this.cellfont);
        cellinfo.setColspan(2);
        cellinfo.setPaddingLeft(10.0F);
        itemdesc.addCell(cellinfo);
      }
      
      if (!currentorder.getSet(item).getItem(0).getBackEyeletColor().equals("")) {
        PdfPCell cell = buzzcell("Back Eyelet Color:", this.cellfontbold);
        cell.setColspan(2);
        itemdesc.addCell(cell);
        PdfPCell cellinfo = buzzcell(currentorder.getSet(item).getItem(0).getBackEyeletColor() + " - " + currentorder.getSet(item).getItem(0).getBackEyeletColorName(), this.cellfont);
        cellinfo.setColspan(2);
        cellinfo.setPaddingLeft(10.0F);
        itemdesc.addCell(cellinfo);
      }
      
      if (!currentorder.getSet(item).getItem(0).getSideEyeletColor().equals("")) {
        PdfPCell cell = buzzcell("Side Eyelet Color:", this.cellfontbold);
        cell.setColspan(2);
        itemdesc.addCell(cell);
        PdfPCell cellinfo = buzzcell(currentorder.getSet(item).getItem(0).getSideEyeletColor() + " - " + currentorder.getSet(item).getItem(0).getSideEyeletColorName(), this.cellfont);
        cellinfo.setColspan(2);
        cellinfo.setPaddingLeft(10.0F);
        itemdesc.addCell(cellinfo);
      }
      
      if (!currentorder.getSet(item).getItem(0).getButtonColor().equals("")) {
        PdfPCell cell = buzzcell("Button Color:", this.cellfontbold);
        cell.setColspan(2);
        itemdesc.addCell(cell);
        PdfPCell cellinfo = buzzcell(currentorder.getSet(item).getItem(0).getButtonColor() + " - " + currentorder.getSet(item).getItem(0).getButtonColorName(), this.cellfont);
        cellinfo.setColspan(2);
        cellinfo.setPaddingLeft(10.0F);
        itemdesc.addCell(cellinfo);
      }
      
      if (!currentorder.getSet(item).getItem(0).getInnerTapingColor().equals("")) {
        PdfPCell cell = buzzcell("Inner Tapping Color:", this.cellfontbold);
        cell.setColspan(2);
        itemdesc.addCell(cell);
        PdfPCell cellinfo = buzzcell(currentorder.getSet(item).getItem(0).getInnerTapingColor() + " - " + currentorder.getSet(item).getItem(0).getInnerTapingColorName(), this.cellfont);
        cellinfo.setColspan(2);
        cellinfo.setPaddingLeft(10.0F);
        itemdesc.addCell(cellinfo);
      }
      
      if (!currentorder.getSet(item).getItem(0).getSweatbandStyleNumber().equals("")) {
        PdfPCell cell = buzzcell("Sweatband Style #:", this.cellfontbold);
        cell.setColspan(2);
        itemdesc.addCell(cell);
        PdfPCell cellinfo = buzzcell(currentorder.getSet(item).getItem(0).getSweatbandStyleNumberName(), this.cellfont);
        cellinfo.setColspan(2);
        cellinfo.setPaddingLeft(10.0F);
        itemdesc.addCell(cellinfo);
      }
      
      if (!currentorder.getSet(item).getItem(0).getSweatbandColor().equals("")) {
        PdfPCell cell = buzzcell("Sweatband Color:", this.cellfontbold);
        cell.setColspan(2);
        itemdesc.addCell(cell);
        PdfPCell cellinfo = buzzcell(currentorder.getSet(item).getItem(0).getSweatbandColor() + " - " + currentorder.getSet(item).getItem(0).getSweatbandColorName(), this.cellfont);
        cellinfo.setColspan(2);
        cellinfo.setPaddingLeft(10.0F);
        itemdesc.addCell(cellinfo);
      }
      
      if (!currentorder.getSet(item).getItem(0).getSweatbandStripeColor().equals("")) {
        PdfPCell cell = buzzcell("Stripe Color:", this.cellfontbold);
        cell.setColspan(2);
        itemdesc.addCell(cell);
        PdfPCell cellinfo = buzzcell(currentorder.getSet(item).getItem(0).getSweatbandStripeColor() + " - " + currentorder.getSet(item).getItem(0).getSweatbandStripeColorName(), this.cellfont);
        cellinfo.setColspan(2);
        cellinfo.setPaddingLeft(10.0F);
        itemdesc.addCell(cellinfo);
      }
    }
    
    if ((currentorder.getSet(item).getItem(0).getHasPrivateLabel() != null) && (currentorder.getSet(item).getItem(0).getHasPrivateLabel().booleanValue())) {
      PdfPCell cell = buzzcell("Private Label", this.cellfontbold);
      cell.setColspan(2);
      itemdesc.addCell(cell);
    } else {
      PdfPCell cell = buzzcell("Otto Standard Label", this.cellfontbold);
      cell.setColspan(2);
      itemdesc.addCell(cell);
    }
    
    PdfPCell itemnotecell = buzzcell("Item Notes:", this.cellfontbold);
    itemnotecell.setColspan(2);
    itemdesc.addCell(itemnotecell);
    String itemnotestext;
    String itemnotestext;
    if (getOrderType() == PDFType.OVERSEASPURCHASEORDER) {
      itemnotestext = currentorder.getSet(item).getItem(0).getSampleApprovedList() + "\n" + currentorder.getSet(item).getItem(0).getComments();
    } else {
      itemnotestext = currentorder.getSet(item).getItem(0).getComments();
    }
    PdfPCell itemnoteinfocell = buzzcell(itemnotestext, this.cellfont);
    
    itemnoteinfocell.setColspan(2);
    itemnoteinfocell.setPaddingLeft(10.0F);
    itemdesc.addCell(itemnoteinfocell);
    
    PdfPCell itemCell = paddedTableCell(itemdesc, 0);
    itemCell.setCellEvent(new SideLine());
    itemTable.addCell(itemCell);
    
    PdfPTable logosTable = new PdfPTable(1);
    for (int i = 0; i < currentorder.getSet(item).getLogoCount(); i++) {
      PdfPTable logoTable = logoArtworkSummaryPage(currentorder, item, i);
      logosTable.addCell(paddedTableCell(logoTable, 0));
      if (i + 1 != currentorder.getSet(item).getLogoCount()) {
        PdfPCell mycell = buzzcell(" ", this.cellfont);
        mycell.setCellEvent(new MiddleLine());
        logosTable.addCell(mycell);
      }
    }
    itemTable.addCell(paddedTableCell(logosTable, 0));
    
    return itemTable;
  }
  
  private PdfPTable itemOverseasSummaryPage(ExtendOrderDataSet theset) throws Exception {
    PdfPTable itemTable = new PdfPTable(3);
    float[] itemTableWidths = { 38.0F, 49.0F, 13.0F };
    itemTable.setWidths(itemTableWidths);
    itemTable.setSplitLate(false);
    PdfPTable itemdesc = new PdfPTable(2);
    float[] itemdescWidths = { 50.0F, 50.0F };
    itemdesc.setWidths(itemdescWidths);
    
    if ((theset.getItem(0).getStyleNumber().startsWith("888-")) || (theset.getItem(0).getStyleNumber().startsWith("889-")) || (theset.getItem(0).getStyleNumber().startsWith("111-")) || (theset.getItem(0).getStyleNumber().startsWith("777-"))) {
      itemdesc.addCell(buzzcell("Style:", this.cellfontbold));
      if ((theset.getItem(0).getCustomStyleName() != null) && (!theset.getItem(0).getCustomStyleName().equals(""))) {
        itemdesc.addCell(buzzcell(theset.getItem(0).getStyleNumber() + theset.getItem(0).getAdvanceType() + ": " + theset.getItem(0).getCustomStyleName(), this.cellfont));
      } else {
        itemdesc.addCell(buzzcell(theset.getItem(0).getStyleNumber() + theset.getItem(0).getAdvanceType(), this.cellfont));
      }
    } else {
      itemdesc.addCell(buzzcell("Style:", this.cellfontbold));
      itemdesc.addCell(buzzcell(theset.getItem(0).getStyleNumber() + theset.getItem(0).getAdvanceType(), this.cellfont));
    }
    
    if (!theset.getItem(0).getProfile().equals("")) {
      itemdesc.addCell(buzzcell("Profile:", this.cellfontbold));
      itemdesc.addCell(buzzcell(theset.getItem(0).getProfile(), this.cellfont));
    }
    
    if (!theset.getItem(0).getColorCode().equals("")) {
      itemdesc.addCell(buzzcell("Color:", this.cellfontbold));
      itemdesc.addCell(buzzcell(theset.getItem(0).getColorCode() + " - " + theset.getItem(0).getColorCodeName(), this.cellfont));
    }
    
    if (!theset.getItem(0).getCrownStyle().equals("")) {
      itemdesc.addCell(buzzcell("Crown Style:", this.cellfontbold));
      itemdesc.addCell(buzzcell(theset.getItem(0).getCrownStyle(), this.cellfont));
    }
    
    if ((getOrderType() != PDFType.OVERSEASSAMPLE) && (getOrderType() != PDFType.OVERSEASPURCHASEORDER) && 
      (!theset.getItem(0).getMaterial().equals(""))) {
      itemdesc.addCell(buzzcell("Material:", this.cellfontbold));
      itemdesc.addCell(buzzcell(theset.getItem(0).getMaterial(), this.cellfont));
    }
    

    if (!theset.getItem(0).getCrownConstruction().equals("")) {
      itemdesc.addCell(buzzcell("Crown Construction:", this.cellfontbold));
      itemdesc.addCell(buzzcell(theset.getItem(0).getCrownConstruction(), this.cellfont));
    }
    
    if (!theset.getItem(0).getNumberOfPanels().equals("")) {
      itemdesc.addCell(buzzcell("# of Panel:", this.cellfontbold));
      itemdesc.addCell(buzzcell(theset.getItem(0).getNumberOfPanels(), this.cellfont));
    }
    
    if (!theset.getItem(0).getPanelStitchColor().equals("")) {
      itemdesc.addCell(buzzcell("Panel Stitch Color:", this.cellfontbold));
      itemdesc.addCell(buzzcell(theset.getItem(0).getPanelStitchColor() + " - " + theset.getItem(0).getPanelStitchColorName(), this.cellfont));
    }
    
    if ((getOrderType() == PDFType.OVERSEASSAMPLE) || (getOrderType() == PDFType.OVERSEASPURCHASEORDER)) {
      if ((theset.getItem(0).getSideBackPanelFabric().equals("")) && (theset.getItem(0).getBackPanelFabric().equals("")) && (theset.getItem(0).getSidePanelFabric().equals(""))) {
        if (!theset.getItem(0).getFrontPanelFabricName().equals("")) {
          itemdesc.addCell(buzzcell("Panel Fabric:", this.cellfontbold));
          itemdesc.addCell(buzzcell(theset.getItem(0).getFrontPanelFabricName(), this.cellfont));
        }
        if (!theset.getItem(0).getFrontPanelContentName().equals("")) {
          itemdesc.addCell(buzzcell("Panel Content:", this.cellfontbold));
          itemdesc.addCell(buzzcell(theset.getItem(0).getFrontPanelContentName(), this.cellfont));
        }
        if (!theset.getItem(0).getFrontPanelFabricSpecName().equals("")) {
          itemdesc.addCell(buzzcell("Panel Spec:", this.cellfontbold));
          itemdesc.addCell(buzzcell(theset.getItem(0).getFrontPanelFabricSpecName(), this.cellfont));
        }
      } else {
        if (!theset.getItem(0).getFrontPanelFabricName().equals("")) {
          itemdesc.addCell(buzzcell("Front Panel Fabric:", this.cellfontbold));
          itemdesc.addCell(buzzcell(theset.getItem(0).getFrontPanelFabricName(), this.cellfont));
        }
        if (!theset.getItem(0).getFrontPanelContentName().equals("")) {
          itemdesc.addCell(buzzcell("Front Panel Content:", this.cellfontbold));
          itemdesc.addCell(buzzcell(theset.getItem(0).getFrontPanelContentName(), this.cellfont));
        }
        if (!theset.getItem(0).getFrontPanelFabricSpecName().equals("")) {
          itemdesc.addCell(buzzcell("Front Panel Spec:", this.cellfontbold));
          itemdesc.addCell(buzzcell(theset.getItem(0).getFrontPanelFabricSpecName(), this.cellfont));
        }
      }
    }
    
    if (!theset.getItem(0).getFrontPanelColor().equals("")) {
      itemdesc.addCell(buzzcell("Front Panel Color:", this.cellfontbold));
      itemdesc.addCell(buzzcell(theset.getItem(0).getFrontPanelColor() + " - " + theset.getItem(0).getFrontPanelColorName(), this.cellfont));
    }
    
    if ((getOrderType() == PDFType.OVERSEASSAMPLE) || (getOrderType() == PDFType.OVERSEASPURCHASEORDER)) {
      if (!theset.getItem(0).getSideBackPanelFabricName().equals("")) {
        itemdesc.addCell(buzzcell("Side & Back Panel Fabric:", this.cellfontbold));
        itemdesc.addCell(buzzcell(theset.getItem(0).getSideBackPanelFabricName(), this.cellfont));
      }
      if (!theset.getItem(0).getSideBackPanelContentName().equals("")) {
        itemdesc.addCell(buzzcell("Side & Back Panel Content:", this.cellfontbold));
        itemdesc.addCell(buzzcell(theset.getItem(0).getSideBackPanelContentName(), this.cellfont));
      }
      if (!theset.getItem(0).getSideBackPanelFabricSpecName().equals("")) {
        itemdesc.addCell(buzzcell("Side & Back Panel Spec:", this.cellfontbold));
        itemdesc.addCell(buzzcell(theset.getItem(0).getSideBackPanelFabricSpecName(), this.cellfont));
      }
    }
    
    if (!theset.getItem(0).getSideBackPanelColor().equals("")) {
      itemdesc.addCell(buzzcell("Side & Back Panel Color:", this.cellfontbold));
      itemdesc.addCell(buzzcell(theset.getItem(0).getSideBackPanelColor() + " - " + theset.getItem(0).getSideBackPanelColorName(), this.cellfont));
    }
    
    if ((getOrderType() == PDFType.OVERSEASSAMPLE) || (getOrderType() == PDFType.OVERSEASPURCHASEORDER)) {
      if (!theset.getItem(0).getBackPanelFabricName().equals("")) {
        itemdesc.addCell(buzzcell("Back Panel Fabric:", this.cellfontbold));
        itemdesc.addCell(buzzcell(theset.getItem(0).getBackPanelFabricName(), this.cellfont));
      }
      if (!theset.getItem(0).getBackPanelContentName().equals("")) {
        itemdesc.addCell(buzzcell("Back Panel Content:", this.cellfontbold));
        itemdesc.addCell(buzzcell(theset.getItem(0).getBackPanelContentName(), this.cellfont));
      }
      if (!theset.getItem(0).getBackPanelFabricSpecName().equals("")) {
        itemdesc.addCell(buzzcell("Back Panel Spec:", this.cellfontbold));
        itemdesc.addCell(buzzcell(theset.getItem(0).getBackPanelFabricSpecName(), this.cellfont));
      }
    }
    
    if (!theset.getItem(0).getBackPanelColor().equals("")) {
      itemdesc.addCell(buzzcell("Back Panel Color:", this.cellfontbold));
      itemdesc.addCell(buzzcell(theset.getItem(0).getBackPanelColor() + " - " + theset.getItem(0).getBackPanelColorName(), this.cellfont));
    }
    
    if ((getOrderType() == PDFType.OVERSEASSAMPLE) || (getOrderType() == PDFType.OVERSEASPURCHASEORDER)) {
      if (!theset.getItem(0).getSidePanelFabricName().equals("")) {
        itemdesc.addCell(buzzcell("Side Panel Fabric:", this.cellfontbold));
        itemdesc.addCell(buzzcell(theset.getItem(0).getSidePanelFabricName(), this.cellfont));
      }
      if (!theset.getItem(0).getSidePanelContentName().equals("")) {
        itemdesc.addCell(buzzcell("Side Panel Content:", this.cellfontbold));
        itemdesc.addCell(buzzcell(theset.getItem(0).getSidePanelContentName(), this.cellfont));
      }
      if (!theset.getItem(0).getSidePanelFabricSpecName().equals("")) {
        itemdesc.addCell(buzzcell("Side Panel Spec:", this.cellfontbold));
        itemdesc.addCell(buzzcell(theset.getItem(0).getSidePanelFabricSpecName(), this.cellfont));
      }
    }
    
    if (!theset.getItem(0).getSidePanelColor().equals("")) {
      itemdesc.addCell(buzzcell("Side Panel Color:", this.cellfontbold));
      itemdesc.addCell(buzzcell(theset.getItem(0).getSidePanelColor() + " - " + theset.getItem(0).getSidePanelColorName(), this.cellfont));
    }
    
    if (!theset.getItem(0).getVisorStyleNumber().equals("")) {
      itemdesc.addCell(buzzcell("Visor Style #:", this.cellfontbold));
      itemdesc.addCell(buzzcell(theset.getItem(0).getVisorStyleNumberName(), this.cellfont));
    }
    
    if (((getOrderType() == PDFType.OVERSEASSAMPLE) || (getOrderType() == PDFType.OVERSEASPURCHASEORDER)) && 
      (!theset.getItem(0).getFactoryVisorCode().equals(""))) {
      itemdesc.addCell(buzzcell("Factory Visor Code:", this.cellfontbold));
      itemdesc.addCell(buzzcell(theset.getItem(0).getFactoryVisorCode(), this.cellfont));
    }
    

    if (!theset.getItem(0).getVisorRowStitching().equals("")) {
      itemdesc.addCell(buzzcell("Visor Row Stitching:", this.cellfontbold));
      itemdesc.addCell(buzzcell(theset.getItem(0).getVisorRowStitching(), this.cellfont));
    }
    
    if (!theset.getItem(0).getVisorStitchColor().equals("")) {
      itemdesc.addCell(buzzcell("Visor Stitch Color:", this.cellfontbold));
      itemdesc.addCell(buzzcell(theset.getItem(0).getVisorStitchColor() + " - " + theset.getItem(0).getVisorStitchColorName(), this.cellfont));
    }
    
    if (!theset.getItem(0).getPrimaryVisorColor().equals("")) {
      itemdesc.addCell(buzzcell("Primary Visor Color:", this.cellfontbold));
      itemdesc.addCell(buzzcell(theset.getItem(0).getPrimaryVisorColor() + " - " + theset.getItem(0).getPrimaryVisorColorName(), this.cellfont));
    }
    
    if (!theset.getItem(0).getVisorTrimColor().equals("")) {
      itemdesc.addCell(buzzcell("Visor Trim/Edge Color:", this.cellfontbold));
      itemdesc.addCell(buzzcell(theset.getItem(0).getVisorTrimColor() + " - " + theset.getItem(0).getVisorTrimColorName(), this.cellfont));
    }
    
    if (!theset.getItem(0).getVisorSandwichColor().equals("")) {
      itemdesc.addCell(buzzcell("Visor Sandwich Color:", this.cellfontbold));
      itemdesc.addCell(buzzcell(theset.getItem(0).getVisorSandwichColor() + " - " + theset.getItem(0).getVisorSandwichColorName(), this.cellfont));
    }
    
    if (!theset.getItem(0).getUndervisorColor().equals("")) {
      itemdesc.addCell(buzzcell("Undervisor Color:", this.cellfontbold));
      itemdesc.addCell(buzzcell(theset.getItem(0).getUndervisorColor() + " - " + theset.getItem(0).getUndervisorColorName(), this.cellfont));
    }
    
    if (!theset.getItem(0).getDistressedVisorInsideColor().equals("")) {
      itemdesc.addCell(buzzcell("Distressed Visor Inside Color:", this.cellfontbold));
      itemdesc.addCell(buzzcell(theset.getItem(0).getDistressedVisorInsideColor() + " - " + theset.getItem(0).getDistressedVisorInsideColorName(), this.cellfont));
    }
    
    if (!theset.getItem(0).getClosureStyleNumber().equals("")) {
      itemdesc.addCell(buzzcell("Closure Style #:", this.cellfontbold));
      itemdesc.addCell(buzzcell(theset.getItem(0).getClosureStyleNumberName(), this.cellfont));
    }
    
    if (!theset.getItem(0).getClosureStrapColor().equals("")) {
      itemdesc.addCell(buzzcell("Closure Strap Color:", this.cellfontbold));
      itemdesc.addCell(buzzcell(theset.getItem(0).getClosureStrapColor() + " - " + theset.getItem(0).getClosureStrapColorName(), this.cellfont));
    }
    
    if (!theset.getItem(0).getEyeletStyleNumber().equals("")) {
      itemdesc.addCell(buzzcell("Eyelet Style #:", this.cellfontbold));
      itemdesc.addCell(buzzcell(theset.getItem(0).getEyeletStyleNumberName(), this.cellfont));
    }
    
    if ((theset.getItem(0).getEyeletStyleNumber().equals("E1")) && (theset.getItem(0).getSideBackEyeletColor().equals("")) && (theset.getItem(0).getBackEyeletColor().equals("")) && (theset.getItem(0).getSideEyeletColor().equals(""))) {
      if (!theset.getItem(0).getFrontEyeletColor().equals("")) {
        itemdesc.addCell(buzzcell("Eyelet Color:", this.cellfontbold));
        itemdesc.addCell(buzzcell(theset.getItem(0).getFrontEyeletColor() + " - " + theset.getItem(0).getFrontEyeletColorName(), this.cellfont));
      }
    }
    else if (!theset.getItem(0).getFrontEyeletColor().equals("")) {
      itemdesc.addCell(buzzcell("Front Eyelet Color:", this.cellfontbold));
      itemdesc.addCell(buzzcell(theset.getItem(0).getFrontEyeletColor() + " - " + theset.getItem(0).getFrontEyeletColorName(), this.cellfont));
    }
    

    if (!theset.getItem(0).getSideBackEyeletColor().equals("")) {
      itemdesc.addCell(buzzcell("Side & Back Eyelet Color:", this.cellfontbold));
      itemdesc.addCell(buzzcell(theset.getItem(0).getSideBackEyeletColor() + " - " + theset.getItem(0).getSideBackEyeletColorName(), this.cellfont));
    }
    
    if (!theset.getItem(0).getBackEyeletColor().equals("")) {
      itemdesc.addCell(buzzcell("Back Eyelet Color:", this.cellfontbold));
      itemdesc.addCell(buzzcell(theset.getItem(0).getBackEyeletColor() + " - " + theset.getItem(0).getBackEyeletColorName(), this.cellfont));
    }
    
    if (!theset.getItem(0).getSideEyeletColor().equals("")) {
      itemdesc.addCell(buzzcell("Side Eyelet Color:", this.cellfontbold));
      itemdesc.addCell(buzzcell(theset.getItem(0).getSideEyeletColor() + " - " + theset.getItem(0).getSideEyeletColorName(), this.cellfont));
    }
    
    if (!theset.getItem(0).getButtonColor().equals("")) {
      itemdesc.addCell(buzzcell("Button Color:", this.cellfontbold));
      itemdesc.addCell(buzzcell(theset.getItem(0).getButtonColor() + " - " + theset.getItem(0).getButtonColorName(), this.cellfont));
    }
    
    if (!theset.getItem(0).getInnerTapingColor().equals("")) {
      itemdesc.addCell(buzzcell("Inner Tapping Color:", this.cellfontbold));
      itemdesc.addCell(buzzcell(theset.getItem(0).getInnerTapingColor() + " - " + theset.getItem(0).getInnerTapingColorName(), this.cellfont));
    }
    
    if (!theset.getItem(0).getSweatbandStyleNumber().equals("")) {
      itemdesc.addCell(buzzcell("Sweatband Style #:", this.cellfontbold));
      itemdesc.addCell(buzzcell(theset.getItem(0).getSweatbandStyleNumberName(), this.cellfont));
    }
    
    if (!theset.getItem(0).getSweatbandColor().equals("")) {
      itemdesc.addCell(buzzcell("Sweatband Color:", this.cellfontbold));
      itemdesc.addCell(buzzcell(theset.getItem(0).getSweatbandColor() + " - " + theset.getItem(0).getSweatbandColorName(), this.cellfont));
    }
    
    if (!theset.getItem(0).getSweatbandStripeColor().equals("")) {
      itemdesc.addCell(buzzcell("Stripe Color:", this.cellfontbold));
      itemdesc.addCell(buzzcell(theset.getItem(0).getSweatbandStripeColor() + " - " + theset.getItem(0).getSweatbandStripeColorName(), this.cellfont));
    }
    
    if (theset.getItemCount() > 1)
    {
      itemdesc.addCell(buzzcell("Size:", this.cellfontbold));
      itemdesc.addCell(buzzcell("Piece QTY:", this.cellfontbold));
      
      for (int i = 0; i < theset.getItemCount(); i++) {
        itemdesc.addCell(buzzcell(theset.getItem(i).getSize(), this.cellfont));
        itemdesc.addCell(buzzcell(String.valueOf(theset.getItem(i).getQuantity()), this.cellfont));
      }
    } else {
      if (!theset.getItem(0).getSize().equals("")) {
        itemdesc.addCell(buzzcell("Size:", this.cellfontbold));
        itemdesc.addCell(buzzcell(theset.getItem(0).getSize(), this.cellfont));
      }
      itemdesc.addCell(buzzcell("Piece QTY:", this.cellfontbold));
      itemdesc.addCell(buzzcell(String.valueOf(theset.getItem(0).getQuantity()), this.cellfont));
    }
    

    PdfPCell itemCell = paddedTableCell(itemdesc, 0);
    itemCell.setCellEvent(new SideLine());
    itemTable.addCell(itemCell);
    
    PdfPTable logosTable = new PdfPTable(1);
    for (int i = 0; i < theset.getLogoCount(); i++) {
      PdfPTable logoTable = logoOverseasSummaryPage(theset.getLogo(i), i);
      logosTable.addCell(paddedTableCell(logoTable, 0));
    }
    if (theset.getLogoCount() == 0) {
      logosTable.addCell(paddedTableCell(logoOverseasEmptySummaryPage(), 0));
    }
    
    PdfPCell logocell = paddedTableCell(logosTable, 0);
    SideLine mylogocellline = new SideLine();
    mylogocellline.setDashed(Boolean.valueOf(true));
    logocell.setCellEvent(mylogocellline);
    itemTable.addCell(logocell);
    
    PdfPTable itemserv = new PdfPTable(1);
    if (theset.getItem(0).getProductSampleEmail()) {
      itemserv.addCell(buzzcell("Email Product Sample", this.cellfont));
    }
    if (theset.getItem(0).getProductSampleShip()) {
      itemserv.addCell(buzzcell("Ship Product Sample", this.cellfont));
    }
    PdfPCell itemservcell = paddedTableCell(itemserv, 0);
    itemservcell.setPaddingLeft(1.0F);
    itemTable.addCell(itemservcell);
    
    return itemTable;
  }
  
  private PdfPTable itemSummaryPage(ExtendOrderDataSet theset) throws Exception {
    PdfPTable itemTable = new PdfPTable(3);
    float[] itemTableWidths = { 22.0F, 65.0F, 13.0F };
    itemTable.setWidths(itemTableWidths);
    itemTable.setSplitLate(false);
    PdfPTable itemdesc = new PdfPTable(2);
    float[] itemdescWidths = { 38.0F, 62.0F };
    itemdesc.setWidths(itemdescWidths);
    if ((theset.getItem(0).getStyleNumber().equals("nonotto")) || (theset.getItem(0).getStyleNumber().equals("nonottoflat"))) {
      itemdesc.addCell(buzzcell("Style:", this.cellfontbold));
      
      if (SharedData.isFaya.booleanValue()) {
        if ((theset.getItem(0).getCustomStyleName() != null) && (!theset.getItem(0).getCustomStyleName().equals(""))) {
          itemdesc.addCell(buzzcell(theset.getItem(0).getCustomStyleName(), this.cellfont));
        } else {
          itemdesc.addCell(buzzcell("Non Faya", this.cellfont));
        }
      }
      else if ((theset.getItem(0).getCustomStyleName() != null) && (!theset.getItem(0).getCustomStyleName().equals(""))) {
        itemdesc.addCell(buzzcell("Non Otto: " + theset.getItem(0).getCustomStyleName(), this.cellfont));
      } else {
        itemdesc.addCell(buzzcell("Non Otto", this.cellfont));
      }
      

      itemdesc.addCell(buzzcell("Type:", this.cellfontbold));
      if (theset.getItem(0).getStyleNumber().equals("nonotto")) {
        itemdesc.addCell(buzzcell("Regular", this.cellfont));
      } else {
        itemdesc.addCell(buzzcell("Flat", this.cellfont));
      }
      
      if (!theset.getItem(0).getColorCodeName().equals("")) {
        itemdesc.addCell(buzzcell("Color Name:", this.cellfontbold));
        itemdesc.addCell(buzzcell(theset.getItem(0).getColorCodeName(), this.cellfont));
      }
      
      if (!theset.getItem(0).getColorCode().equals("")) {
        itemdesc.addCell(buzzcell("Color #:", this.cellfontbold));
        itemdesc.addCell(buzzcell(theset.getItem(0).getColorCode(), this.cellfont));
      }
    }
    else {
      itemdesc.addCell(buzzcell("Style:", this.cellfontbold));
      itemdesc.addCell(buzzcell(theset.getItem(0).getStyleNumber(), this.cellfont));
      
      if (!theset.getItem(0).getProfile().equals("")) {
        itemdesc.addCell(buzzcell("Profile:", this.cellfontbold));
        itemdesc.addCell(buzzcell(theset.getItem(0).getProfile(), this.cellfont));
      }
      
      if (!theset.getItem(0).getColorCodeName().equals("")) {
        itemdesc.addCell(buzzcell("Color Name:", this.cellfontbold));
        itemdesc.addCell(buzzcell(theset.getItem(0).getColorCodeName(), this.cellfont));
      }
      
      if (!theset.getItem(0).getColorCode().equals("")) {
        itemdesc.addCell(buzzcell("Color #:", this.cellfontbold));
        itemdesc.addCell(buzzcell(theset.getItem(0).getColorCode(), this.cellfont));
      }
      
      if (!theset.getItem(0).getCrownStyle().equals("")) {
        itemdesc.addCell(buzzcell("Crown Style:", this.cellfontbold));
        itemdesc.addCell(buzzcell(theset.getItem(0).getCrownStyle(), this.cellfont));
      }
      
      if ((getOrderType() != PDFType.OVERSEASSAMPLE) && (getOrderType() != PDFType.OVERSEASPURCHASEORDER) && 
        (!theset.getItem(0).getMaterial().equals(""))) {
        itemdesc.addCell(buzzcell("Material:", this.cellfontbold));
        itemdesc.addCell(buzzcell(theset.getItem(0).getMaterial(), this.cellfont));
      }
      


      if (!theset.getItem(0).getCrownConstruction().equals("")) {
        itemdesc.addCell(buzzcell("Crown Contruction:", this.cellfontbold));
        itemdesc.addCell(buzzcell(theset.getItem(0).getCrownConstruction(), this.cellfont));
      }
      
      if (!theset.getItem(0).getNumberOfPanels().equals("")) {
        itemdesc.addCell(buzzcell("# of Panel:", this.cellfontbold));
        itemdesc.addCell(buzzcell(theset.getItem(0).getNumberOfPanels(), this.cellfont));
      }
      
      if ((getOrderType() == PDFType.OVERSEASSAMPLE) || (getOrderType() == PDFType.OVERSEASPURCHASEORDER))
      {
        if ((theset.getItem(0).getSideBackPanelFabric().equals("")) && (theset.getItem(0).getBackPanelFabric().equals("")) && (theset.getItem(0).getSidePanelFabric().equals(""))) {
          if (!theset.getItem(0).getFrontPanelFabricName().equals("")) {
            itemdesc.addCell(buzzcell("Panel Fabric:", this.cellfontbold));
            itemdesc.addCell(buzzcell(theset.getItem(0).getFrontPanelFabricName(), this.cellfont));
          }
          if (!theset.getItem(0).getFrontPanelContentName().equals("")) {
            itemdesc.addCell(buzzcell("Panel Content:", this.cellfontbold));
            itemdesc.addCell(buzzcell(theset.getItem(0).getFrontPanelContentName(), this.cellfont));
          }
          if (!theset.getItem(0).getFrontPanelFabricSpecName().equals("")) {
            itemdesc.addCell(buzzcell("Panel Spec:", this.cellfontbold));
            itemdesc.addCell(buzzcell(theset.getItem(0).getFrontPanelFabricSpecName(), this.cellfont));
          }
        } else {
          if (!theset.getItem(0).getFrontPanelFabricName().equals("")) {
            itemdesc.addCell(buzzcell("Front Panel Fabric:", this.cellfontbold));
            itemdesc.addCell(buzzcell(theset.getItem(0).getFrontPanelFabricName(), this.cellfont));
          }
          if (!theset.getItem(0).getFrontPanelContentName().equals("")) {
            itemdesc.addCell(buzzcell("Front Panel Content:", this.cellfontbold));
            itemdesc.addCell(buzzcell(theset.getItem(0).getFrontPanelContentName(), this.cellfont));
          }
          if (!theset.getItem(0).getFrontPanelFabricSpecName().equals("")) {
            itemdesc.addCell(buzzcell("Front Panel Spec:", this.cellfontbold));
            itemdesc.addCell(buzzcell(theset.getItem(0).getFrontPanelFabricSpecName(), this.cellfont));
          }
        }
        
        if (!theset.getItem(0).getSideBackPanelFabricName().equals("")) {
          itemdesc.addCell(buzzcell("Side & Back Panel Fabric:", this.cellfontbold));
          itemdesc.addCell(buzzcell(theset.getItem(0).getSideBackPanelFabricName(), this.cellfont));
        }
        if (!theset.getItem(0).getSideBackPanelContentName().equals("")) {
          itemdesc.addCell(buzzcell("Side & Back Panel Content:", this.cellfontbold));
          itemdesc.addCell(buzzcell(theset.getItem(0).getSideBackPanelContentName(), this.cellfont));
        }
        if (!theset.getItem(0).getSideBackPanelFabricSpecName().equals("")) {
          itemdesc.addCell(buzzcell("Side & Back Panel Spec:", this.cellfontbold));
          itemdesc.addCell(buzzcell(theset.getItem(0).getSideBackPanelFabricSpecName(), this.cellfont));
        }
        if (!theset.getItem(0).getBackPanelFabricName().equals("")) {
          itemdesc.addCell(buzzcell("Back Panel Fabric:", this.cellfontbold));
          itemdesc.addCell(buzzcell(theset.getItem(0).getBackPanelFabricName(), this.cellfont));
        }
        if (!theset.getItem(0).getBackPanelContentName().equals("")) {
          itemdesc.addCell(buzzcell("Back Panel Content:", this.cellfontbold));
          itemdesc.addCell(buzzcell(theset.getItem(0).getBackPanelContentName(), this.cellfont));
        }
        if (!theset.getItem(0).getBackPanelFabricSpecName().equals("")) {
          itemdesc.addCell(buzzcell("Back Panel Spec:", this.cellfontbold));
          itemdesc.addCell(buzzcell(theset.getItem(0).getBackPanelFabricSpecName(), this.cellfont));
        }
        if (!theset.getItem(0).getSidePanelFabricName().equals("")) {
          itemdesc.addCell(buzzcell("Side Panel Fabric:", this.cellfontbold));
          itemdesc.addCell(buzzcell(theset.getItem(0).getSidePanelFabricName(), this.cellfont));
        }
        if (!theset.getItem(0).getSidePanelContentName().equals("")) {
          itemdesc.addCell(buzzcell("Side Panel Content:", this.cellfontbold));
          itemdesc.addCell(buzzcell(theset.getItem(0).getSidePanelContentName(), this.cellfont));
        }
        if (!theset.getItem(0).getSidePanelFabricSpecName().equals("")) {
          itemdesc.addCell(buzzcell("Side Panel Spec:", this.cellfontbold));
          itemdesc.addCell(buzzcell(theset.getItem(0).getSidePanelFabricSpecName(), this.cellfont));
        }
      }
    }
    

    if (theset.getItemCount() > 1) {
      itemdesc.addCell(buzzcell("Size:", this.cellfontbold));
      itemdesc.addCell(buzzcell("Piece QTY:", this.cellfontbold));
      
      for (int i = 0; i < theset.getItemCount(); i++) {
        itemdesc.addCell(buzzcell(theset.getItem(i).getSize(), this.cellfont));
        itemdesc.addCell(buzzcell(String.valueOf(theset.getItem(i).getQuantity()), this.cellfont));
      }
    } else {
      if (!theset.getItem(0).getSize().equals("")) {
        itemdesc.addCell(buzzcell("Size:", this.cellfontbold));
        itemdesc.addCell(buzzcell(theset.getItem(0).getSize(), this.cellfont));
      }
      itemdesc.addCell(buzzcell("Piece QTY:", this.cellfontbold));
      itemdesc.addCell(buzzcell(String.valueOf(theset.getItem(0).getQuantity()), this.cellfont));
    }
    

    PdfPCell itemCell = paddedTableCell(itemdesc, 0);
    itemCell.setCellEvent(new SideLine());
    itemTable.addCell(itemCell);
    
    PdfPTable logosTable = new PdfPTable(1);
    for (int i = 0; i < theset.getLogoCount(); i++) {
      PdfPTable logoTable = logoSummaryPage(theset.getLogo(i), i);
      logosTable.addCell(paddedTableCell(logoTable, 0));
    }
    PdfPCell logocell = paddedTableCell(logosTable, 0);
    SideLine mylogocellline = new SideLine();
    mylogocellline.setDashed(Boolean.valueOf(true));
    logocell.setCellEvent(mylogocellline);
    itemTable.addCell(logocell);
    
    PdfPTable itemserv = new PdfPTable(1);
    if (theset.getItem(0).getPolybag()) {
      itemserv.addCell(buzzcell("Polybag", this.cellfont));
    }
    if (theset.getItem(0).getOakLeaves()) {
      itemserv.addCell(buzzcell("Oak Leaves Emblems", this.cellfont));
    }
    if (theset.getItem(0).getRemoveInnerPrintedLabel()) {
      itemserv.addCell(buzzcell("Remove Inner Printed Label", this.cellfont));
    }
    if (theset.getItem(0).getAddInnerPrintedLabel()) {
      itemserv.addCell(buzzcell("Add Inner Printed Label", this.cellfont));
    }
    if (theset.getItem(0).getRemoveInnerWovenLabel()) {
      itemserv.addCell(buzzcell("Remove Inner Woven Label", this.cellfont));
    }
    if (theset.getItem(0).getAddInnerWovenLabel()) {
      itemserv.addCell(buzzcell("Add Inner Woven Label", this.cellfont));
    }
    if (theset.getItem(0).getSewingPatches()) {
      itemserv.addCell(buzzcell("Sewing Patches", this.cellfont));
    }
    if (theset.getItem(0).getHeatPressPatches()) {
      itemserv.addCell(buzzcell("Heat Press Patches", this.cellfont));
    }
    if (theset.getItem(0).getTagging()) {
      itemserv.addCell(buzzcell("Tagging", this.cellfont));
    }
    if (theset.getItem(0).getStickers()) {
      itemserv.addCell(buzzcell("Stickers", this.cellfont));
    }
    if ((theset.getItem(0).getPersonalizationChanges() != null) && (theset.getItem(0).getPersonalizationChanges().intValue() > 0)) {
      itemserv.addCell(buzzcell("Personalization - " + theset.getItem(0).getPersonalizationChanges(), this.cellfont));
    }
    if (theset.getItem(0).getProductSampleEmail()) {
      itemserv.addCell(buzzcell("Email Product Sample", this.cellfont));
    }
    if (theset.getItem(0).getProductSampleShip()) {
      itemserv.addCell(buzzcell("Ship Product Sample", this.cellfont));
    }
    PdfPCell itemservcell = paddedTableCell(itemserv, 0);
    itemservcell.setPaddingLeft(1.0F);
    itemTable.addCell(itemservcell);
    
    return itemTable;
  }
  
  private PdfPTable logoArtworkSummaryPage(ExtendOrderData currentorder, int item, int logo) throws Exception {
    PdfPTable logoTable = new PdfPTable(3);
    float[] logoWidths = { 35.0F, 45.0F, 20.0F };
    logoTable.setWidths(logoWidths);
    logoTable.setSplitLate(false);
    
    PdfPTable logodesc = new PdfPTable(2);
    float[] logodescWidths = { 42.0F, 58.0F };
    logodesc.setWidths(logodescWidths);
    
    if ((currentorder.getSet(item).getLogo(logo).getArtworkType() != null) && (!currentorder.getSet(item).getLogo(logo).getArtworkType().equals(""))) {
      logodesc.addCell(buzzcell("Artwork Job:", this.artworkfontbold));
      logodesc.addCell(buzzcell(currentorder.getSet(item).getLogo(logo).getArtworkType(), this.artworkfont));
    }
    
    logodesc.addCell(buzzcell("Logo #:", this.cellfontbold));
    logodesc.addCell(buzzcell(String.valueOf(logo + 1), this.cellfont));
    
    logodesc.addCell(buzzcell("Logo Name:", this.cellfontbold));
    if ((currentorder.getSet(item).getLogo(logo).getFilename().equals("")) && (!currentorder.getSet(item).getLogo(logo).getDstFilename().equals(""))) {
      logodesc.addCell(buzzcell(currentorder.getSet(item).getLogo(logo).getDstFilename(), this.cellfont));
    } else {
      logodesc.addCell(buzzcell(currentorder.getSet(item).getLogo(logo).getFilename(), this.cellfont));
    }
    
    logodesc.addCell(buzzcell("Logo Size:", this.cellfontbold));
    logodesc.addCell(buzzcell(currentorder.getSet(item).getLogo(logo).getLogoSize(), this.cellfont));
    
    logodesc.addCell(buzzcell("Location:", this.cellfontbold));
    logodesc.addCell(buzzcell(currentorder.getSet(item).getLogo(logo).getLogoLocationName(), this.cellfont));
    
    if ((currentorder.getSet(item).getLogo(logo).getNumberOfColor() != null) && (currentorder.getSet(item).getLogo(logo).getNumberOfColor().intValue() > 0)) {
      logodesc.addCell(buzzcell("# of Colors:", this.cellfontbold));
      logodesc.addCell(buzzcell(String.valueOf(currentorder.getSet(item).getLogo(logo).getNumberOfColor()), this.cellfont));
    }
    
    if ((currentorder.getSet(item).getLogo(logo).getStitchCount() != null) && (currentorder.getSet(item).getLogo(logo).getStitchCount().intValue() > 0)) {
      logodesc.addCell(buzzcell("Stitch Count:", this.cellfontbold));
      logodesc.addCell(buzzcell(String.valueOf(currentorder.getSet(item).getLogo(logo).getStitchCount()), this.cellfont));
    }
    
    if ((currentorder.getSet(item).getLogo(logo).getNameDropLogo() != null) && (!currentorder.getSet(item).getLogo(logo).getNameDropLogo().equals(""))) {
      logodesc.addCell(buzzcell("Name Drop Logo:", this.cellfontbold));
      logodesc.addCell(buzzcell(currentorder.getSet(item).getLogo(logo).getNameDropLogo(), this.cellfont));
    }
    
    if (currentorder.getOrderType() == OrderData.OrderType.DOMESTIC) {
      logodesc.addCell(buzzcell("Decoration Type:", this.cellfontbold));
      logodesc.addCell(buzzcell(currentorder.getSet(item).getLogo(logo).getDecoration(), this.cellfont));
    } else {
      for (int i = 0; i < currentorder.getSet(item).getLogo(logo).getDecorationCount().intValue(); i++) {
        logodesc.addCell(buzzcell("Decoration Type:", this.cellfontbold));
        logodesc.addCell(buzzcell(currentorder.getSet(item).getLogo(logo).getDecoration(Integer.valueOf(i)).getName(), this.cellfont));
      }
    }
    


    logoTable.addCell(paddedTableCell(logodesc, 1));
    
    PdfPTable logodesc2 = new PdfPTable(1);
    
    PdfPTable colorways = new PdfPTable(4);
    colorways.setHeaderRows(1);
    colorways.setSplitLate(false);
    colorways.addCell(buzzcell("#", this.cellfontbold));
    colorways.addCell(buzzcell("Color Value", this.cellfontbold));
    colorways.addCell(buzzcell("Color Name", this.cellfontbold));
    colorways.addCell(buzzcell("Sequence Description", this.cellfontbold));
    float[] colorwaysWidths = { 7.0F, 25.0F, 30.0F, 43.0F };
    colorways.setWidths(colorwaysWidths);
    
    for (int i = 0; i < currentorder.getSet(item).getLogo(logo).getColorwayCount(); i++) {
      colorways.addCell(buzzcell(String.valueOf(i + 1), this.cellfont));
      if (currentorder.getSet(item).getLogo(logo).getFilename().toLowerCase().endsWith(".dst")) {
        colorways.addCell(buzzcell(currentorder.getSet(item).getLogo(logo).getColorway(i).getConvertCodeColorValue(), this.cellfont));
        colorways.addCell(buzzcell(currentorder.getSet(item).getLogo(logo).getColorway(i).getConvertColorName(), this.cellfont));
      } else {
        colorways.addCell(buzzcell(currentorder.getSet(item).getLogo(logo).getColorway(i).getLogoColorCodeValue(), this.cellfont));
        colorways.addCell(buzzcell(currentorder.getSet(item).getLogo(logo).getColorway(i).getLogoColorCodeName(), this.cellfont));
      }
      colorways.addCell(buzzcell(currentorder.getSet(item).getLogo(logo).getColorway(i).getColorCodeComment(), this.cellfont));
    }
    

    PdfPCell coloywaycell = paddedTableCell(colorways, 0);
    coloywaycell.setPaddingBottom(5.0F);
    logodesc2.addCell(coloywaycell);
    
    if ((currentorder.getSet(item).getLogo(logo).getArtworkTypeComments() != null) && (!currentorder.getSet(item).getLogo(logo).getArtworkTypeComments().equals(""))) {
      PdfPTable artworkcomment = new PdfPTable(1);
      artworkcomment.setHeaderRows(1);
      artworkcomment.setSplitLate(false);
      artworkcomment.addCell(buzzcell("Artwork Comment:", this.cellfontbold));
      PdfPCell artworkcommentinfo = buzzcell(currentorder.getSet(item).getLogo(logo).getArtworkTypeComments(), this.cellfont);
      artworkcommentinfo.setPaddingLeft(10.0F);
      artworkcomment.addCell(artworkcommentinfo);
      logodesc2.addCell(paddedTableCell(artworkcomment, 0));
    }
    
    PdfPTable logodescription = new PdfPTable(1);
    logodescription.setHeaderRows(1);
    logodescription.setSplitLate(false);
    logodescription.addCell(buzzcell("Logo Description:", this.cellfontbold));
    PdfPCell logdescriptioninfo = buzzcell(currentorder.getSet(item).getLogo(logo).getColorDescription(), this.cellfont);
    logdescriptioninfo.setPaddingLeft(10.0F);
    logodescription.addCell(logdescriptioninfo);
    logodesc2.addCell(paddedTableCell(logodescription, 0));
    

    PdfPCell logodesc2cell = paddedTableCell(logodesc2, 1);
    logodesc2cell.setCellEvent(new SideLine());
    logodesc2cell.setPaddingRight(1.0F);
    logoTable.addCell(logodesc2cell);
    
    PdfPTable logoserv = new PdfPTable(1);
    PdfPCell logopicture;
    PdfPCell logopicture;
    if (!currentorder.getSet(item).getLogo(logo).getFilename().equals("")) { Image logopreview;
      Image logopreview;
      if (currentorder.getSet(item).getLogo(logo).getFilename().toLowerCase().endsWith(".dst")) {
        String samedstimage = currentorder.getSet(item).getLogo(logo).getFilename() + currentorder.getSet(item).getLogo(logo).getThreadBrand();
        for (int i = 0; i < currentorder.getSet(item).getLogo(logo).getColorwayCount(); i++) {
          samedstimage = samedstimage + currentorder.getSet(item).getLogo(logo).getColorway(i).getConvertCode();
        }
        Image logopreview;
        if (getOrderType() == PDFType.ARTWORK) {
          logopreview = this.sameimages.getDSTImageNoResize(samedstimage, currentorder, currentorder.getSet(item).getLogo(logo));
        } else {
          logopreview = this.sameimages.getDSTImageNoConvert(samedstimage, currentorder, currentorder.getSet(item).getLogo(logo));
        }
      }
      else {
        logopreview = this.sameimages.getImageNoConvert(currentorder.getSet(item).getLogo(logo).getFilename(), "C:\\WorkFlow\\NewWorkflowData\\" + currentorder.getOrderTypeFolder() + "Data/" + currentorder.getHiddenKey() + "/logos/" + currentorder.getSet(item).getLogo(logo).getFilename());
      }
      
      logopreview.scaleToFit(85.0F, 60.0F);
      logopicture = new PdfPCell(logopreview, false);
    } else { PdfPCell logopicture;
      if ((currentorder.getOrderType() == OrderData.OrderType.OVERSEAS) && (!currentorder.getSet(item).getLogo(logo).getDstFilename().equals(""))) {
        String samedstimage = currentorder.getSet(item).getLogo(logo).getDstFilename() + currentorder.getSet(item).getLogo(logo).getThreadBrand();
        for (int i = 0; i < currentorder.getSet(item).getLogo(logo).getColorwayCount(); i++) {
          samedstimage = samedstimage + currentorder.getSet(item).getLogo(logo).getColorway(i).getConvertCode();
        }
        Image logopreview;
        Image logopreview;
        if (getOrderType() == PDFType.OVERSEASARTWORK) {
          logopreview = this.sameimages.getDSTImageNoResize(samedstimage, currentorder, currentorder.getSet(item).getLogo(logo));
        } else {
          logopreview = this.sameimages.getDSTImageNoConvert(samedstimage, currentorder, currentorder.getSet(item).getLogo(logo));
        }
        logopreview.scaleToFit(85.0F, 60.0F);
        logopicture = new PdfPCell(logopreview, false);
      } else {
        logopicture = new PdfPCell(new Phrase(" ", this.cellfontbold));
      }
    }
    
    logopicture.setBorder(0);
    logopicture.setMinimumHeight(60.0F);
    logopicture.setHorizontalAlignment(1);
    logopicture.setVerticalAlignment(5);
    logoserv.addCell(logopicture);
    
    logoserv.addCell(buzzcell(" ", this.cellfont));
    logoTable.addCell(paddedTableCell(logoserv, 0));
    return logoTable;
  }
  
  private PdfPTable logoOverseasEmptySummaryPage() throws Exception {
    PdfPTable logoTable = new PdfPTable(2);
    float[] logoWidths = { 68.0F, 32.0F };
    logoTable.setWidths(logoWidths);
    logoTable.setSplitLate(false);
    
    PdfPCell logodesccell = buzzcell("", this.cellfont);
    logodesccell.setCellEvent(new SideLine());
    logodesccell.setPaddingRight(1.0F);
    logoTable.addCell(logodesccell);
    logoTable.addCell(buzzcell("", this.cellfont));
    return logoTable;
  }
  
  private PdfPTable logoOverseasSummaryPage(ExtendOrderDataLogo thelogo, int logopos) throws Exception {
    PdfPTable logoTable = new PdfPTable(2);
    float[] logoWidths = { 68.0F, 32.0F };
    logoTable.setWidths(logoWidths);
    logoTable.setSplitLate(false);
    
    PdfPTable logodesc = new PdfPTable(2);
    float[] logodescWidths = { 35.0F, 65.0F };
    logodesc.setWidths(logodescWidths);
    logodesc.addCell(buzzcell("Logo #:", this.cellfontbold));
    logodesc.addCell(buzzcell(String.valueOf(logopos + 1), this.cellfont));
    
    logodesc.addCell(buzzcell("Logo Name:", this.cellfontbold));
    
    if ((thelogo.getFilename().equals("")) && (!thelogo.getDstFilename().equals(""))) {
      logodesc.addCell(buzzcell(thelogo.getDstFilename(), this.cellfont));
    } else {
      logodesc.addCell(buzzcell(thelogo.getFilename(), this.cellfont));
    }
    logodesc.addCell(buzzcell("Logo Size:", this.cellfontbold));
    logodesc.addCell(buzzcell(thelogo.getLogoSize(), this.cellfont));
    
    logodesc.addCell(buzzcell("Location:", this.cellfontbold));
    logodesc.addCell(buzzcell(thelogo.getLogoLocationName(), this.cellfont));
    
    if ((thelogo.getNumberOfColor() != null) && (thelogo.getNumberOfColor().intValue() > 0)) {
      logodesc.addCell(buzzcell("# of Colors:", this.cellfontbold));
      logodesc.addCell(buzzcell(String.valueOf(thelogo.getNumberOfColor()), this.cellfont));
    }
    
    if ((thelogo.getStitchCount() != null) && (thelogo.getStitchCount().intValue() > 0)) {
      logodesc.addCell(buzzcell("Stitch Count:", this.cellfontbold));
      logodesc.addCell(buzzcell(String.valueOf(thelogo.getStitchCount()), this.cellfont));
    }
    
    if (!thelogo.getNameDropLogo().equals("")) {
      logodesc.addCell(buzzcell("Name Drop Logo:", this.cellfontbold));
      logodesc.addCell(buzzcell(thelogo.getNameDropLogo(), this.cellfont));
    }
    
    logodesc.addCell(buzzcell(" ", this.cellfont));
    logodesc.addCell(buzzcell(" ", this.cellfont));
    
    PdfPCell logodesccell = paddedTableCell(logodesc, 1);
    logodesccell.setCellEvent(new SideLine());
    logodesccell.setPaddingRight(1.0F);
    logoTable.addCell(logodesccell);
    
    PdfPTable logoserv = new PdfPTable(1);
    
    for (int i = 0; i < thelogo.getDecorationCount().intValue(); i++) {
      logoserv.addCell(buzzcell(thelogo.getDecoration(Integer.valueOf(i)).getName(), this.cellfont));
    }
    
    if (thelogo.getDigitizing()) {
      logoserv.addCell(buzzcell("Digitizing", this.cellfont));
    }
    if (thelogo.getSwatchEmail()) {
      logoserv.addCell(buzzcell("Email Swatch", this.cellfont));
    }
    if (thelogo.getSwatchShip()) {
      logoserv.addCell(buzzcell("Ship Swatch", this.cellfont));
    }
    logoserv.addCell(buzzcell(" ", this.cellfont));
    PdfPCell logoservcell = paddedTableCell(logoserv, 1);
    logoservcell.setPaddingLeft(1.0F);
    logoTable.addCell(logoservcell);
    return logoTable;
  }
  
  private PdfPTable logoSummaryPage(ExtendOrderDataLogo thelogo, int logopos) throws Exception {
    PdfPTable logoTable = new PdfPTable(3);
    float[] logoWidths = { 38.0F, 38.0F, 24.0F };
    logoTable.setWidths(logoWidths);
    logoTable.setSplitLate(false);
    
    PdfPTable logodesc = new PdfPTable(2);
    float[] logodescWidths = { 35.0F, 65.0F };
    logodesc.setWidths(logodescWidths);
    logodesc.addCell(buzzcell("Logo #:", this.cellfontbold));
    logodesc.addCell(buzzcell(String.valueOf(logopos + 1), this.cellfont));
    
    logodesc.addCell(buzzcell("Logo Name:", this.cellfontbold));
    logodesc.addCell(buzzcell(thelogo.getFilename(), this.cellfont));
    
    logodesc.addCell(buzzcell("Logo Size:", this.cellfontbold));
    logodesc.addCell(buzzcell(thelogo.getLogoSize(), this.cellfont));
    
    logodesc.addCell(buzzcell("Location:", this.cellfontbold));
    logodesc.addCell(buzzcell(thelogo.getLogoLocationName(), this.cellfont));
    
    logodesc.addCell(buzzcell(" ", this.cellfont));
    logodesc.addCell(buzzcell(" ", this.cellfont));
    logoTable.addCell(paddedTableCell(logodesc, 1));
    
    PdfPTable logodesc2 = new PdfPTable(2);
    
    if ((thelogo.getNumberOfColor() != null) && (thelogo.getNumberOfColor().intValue() > 0)) {
      logodesc2.addCell(buzzcell("# of Colors:", this.cellfontbold));
      logodesc2.addCell(buzzcell(String.valueOf(thelogo.getNumberOfColor()), this.cellfont));
    }
    
    if ((thelogo.getStitchCount() != null) && (thelogo.getStitchCount().intValue() > 0)) {
      logodesc2.addCell(buzzcell("Stitch Count:", this.cellfontbold));
      logodesc2.addCell(buzzcell(String.valueOf(thelogo.getStitchCount()), this.cellfont));
    }
    
    logodesc2.addCell(buzzcell("Decoration Type:", this.cellfontbold));
    logodesc2.addCell(buzzcell(thelogo.getDecoration(), this.cellfont));
    
    logodesc2.addCell(buzzcell(" ", this.cellfont));
    logodesc2.addCell(buzzcell(" ", this.cellfont));
    PdfPCell logodesc2cell = paddedTableCell(logodesc2, 1);
    logodesc2cell.setCellEvent(new SideLine());
    logodesc2cell.setPaddingRight(1.0F);
    logoTable.addCell(logodesc2cell);
    
    PdfPTable logoserv = new PdfPTable(1);
    if ((thelogo.getArtworkChargePerHour() != null) && (thelogo.getArtworkChargePerHour().doubleValue() > 0.0D)) {
      if (thelogo.getArtworkChargePerHour().doubleValue() > 1.0D) {
        logoserv.addCell(buzzcell("Artwork Charge - " + thelogo.getArtworkChargePerHour() + " hours", this.cellfont));
      } else {
        logoserv.addCell(buzzcell("Artwork Charge - " + thelogo.getArtworkChargePerHour() + " hour", this.cellfont));
      }
    }
    if (thelogo.getDigitizing()) {
      logoserv.addCell(buzzcell("Digitizing", this.cellfont));
    }
    if (thelogo.getFilmCharge()) {
      logoserv.addCell(buzzcell("Film Charge", this.cellfont));
    }
    if (thelogo.getPMSMatch()) {
      logoserv.addCell(buzzcell("PMS Match", this.cellfont));
    }
    if (thelogo.getTapeEdit()) {
      logoserv.addCell(buzzcell("Tape Edit", this.cellfont));
    }
    if (thelogo.getFlashCharge()) {
      logoserv.addCell(buzzcell((thelogo.getDecoration() != null) && (thelogo.getDecoration().equals("Heat Transfer")) ? "Color Garment Charge" : "Flash Charge", this.cellfont));
    }
    if (thelogo.getMetallicThread()) {
      logoserv.addCell(buzzcell("Special Thread", this.cellfont));
    }
    if (thelogo.getSpecialInk()) {
      logoserv.addCell(buzzcell("Special Ink", this.cellfont));
    }
    if (thelogo.getColorChange()) {
      if ((thelogo.getColorChangeAmount() != null) && (thelogo.getColorChangeAmount().intValue() > 0)) {
        logoserv.addCell(buzzcell("Color Change - " + thelogo.getColorChangeAmount(), this.cellfont));
      } else {
        logoserv.addCell(buzzcell("Color Change", this.cellfont));
      }
    }
    if (thelogo.getSwatchEmail()) {
      logoserv.addCell(buzzcell("Email Swatch", this.cellfont));
    }
    if (thelogo.getSwatchShip()) {
      logoserv.addCell(buzzcell("Ship Swatch", this.cellfont));
    }
    logoserv.addCell(buzzcell(" ", this.cellfont));
    PdfPCell logoservcell = paddedTableCell(logoserv, 1);
    logoservcell.setPaddingLeft(1.0F);
    logoTable.addCell(logoservcell);
    return logoTable;
  }
  
  private String getUrlSafe(String toencode) {
    try {
      return URLEncoder.encode(toencode, "UTF-8");
    } catch (UnsupportedEncodingException e) {}
    return "";
  }
}
