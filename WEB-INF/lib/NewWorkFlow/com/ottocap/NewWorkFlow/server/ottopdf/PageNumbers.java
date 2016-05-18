package com.ottocap.NewWorkFlow.server.ottopdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;









public class PageNumbers
  extends PdfPageEventHelper
{
  public PdfTemplate tpl;
  public BaseFont helv;
  private int fontsize = 9;
  BaseColor fontcolor = BaseColor.WHITE;
  
  public void setFontColor(BaseColor color) {
    this.fontcolor = color;
  }
  
  public void onOpenDocument(PdfWriter writer, Document document)
  {
    try
    {
      this.tpl = writer.getDirectContent().createTemplate(100.0F, 100.0F);
      



      this.helv = FontFactory.getFont("C:\\WorkFlow\\NewWorkflowData\\images/pdf/arialnb.ttf", "Identity-H", true).getBaseFont();
    } catch (Exception e) {
      throw new ExceptionConverter(e);
    }
  }
  
  public void onEndPage(PdfWriter writer, Document document)
  {
    PdfContentByte cb = writer.getDirectContent();
    
    String text = "Page " + writer.getPageNumber() + " of ";
    float textSize = this.helv.getWidthPoint(text, this.fontsize);
    float textBase = document.top() - 18.0F;
    if (this.fontcolor.equals(Color.BLACK)) {
      textBase = document.top() - 9.0F;
    }
    
    cb.beginText();
    cb.setFontAndSize(this.helv, this.fontsize);
    float adjust = this.helv.getWidthPoint("0", this.fontsize);
    cb.setTextMatrix(document.right() - 7.0F - textSize - adjust, textBase);
    cb.setColorFill(this.fontcolor);
    cb.showText(text);
    cb.endText();
    cb.addTemplate(this.tpl, document.right() - 7.0F - adjust, textBase);
    cb.resetRGBColorFill();
  }
  
  public void onCloseDocument(PdfWriter writer, Document document)
  {
    this.tpl.beginText();
    this.tpl.setFontAndSize(this.helv, this.fontsize);
    this.tpl.setTextMatrix(0.0F, 0.0F);
    this.tpl.showText(writer.getPageNumber() - 1);
    this.tpl.endText();
  }
}
