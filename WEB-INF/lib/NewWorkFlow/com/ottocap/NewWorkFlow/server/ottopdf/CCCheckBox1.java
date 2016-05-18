package com.ottocap.NewWorkFlow.server.ottopdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;









class CCCheckBox1
  implements PdfPCellEvent
{
  private boolean havedigits = false;
  
  public void haveDigits(Boolean havedigits) {
    this.havedigits = havedigits.booleanValue();
  }
  
  public void cellLayout(PdfPCell cell, Rectangle rect, PdfContentByte[] canvas)
  {
    PdfContentByte cb = canvas[2];
    cb.setLineWidth(0.0F);
    cb.setColorStroke(BaseColor.BLACK);
    cb.rectangle(rect.getLeft() + 5.0F, rect.getBottom(), 8.0F, 8.0F);
    cb.stroke();
    
    if (this.havedigits) {
      cb.rectangle(rect.getLeft() + 100.0F, rect.getBottom(), 10.0F, 10.0F);
      cb.rectangle(rect.getLeft() + 110.0F, rect.getBottom(), 10.0F, 10.0F);
      cb.rectangle(rect.getLeft() + 120.0F, rect.getBottom(), 10.0F, 10.0F);
      cb.rectangle(rect.getLeft() + 130.0F, rect.getBottom(), 10.0F, 10.0F);
      cb.stroke();
    }
    
    cb.resetRGBColorStroke();
  }
}
