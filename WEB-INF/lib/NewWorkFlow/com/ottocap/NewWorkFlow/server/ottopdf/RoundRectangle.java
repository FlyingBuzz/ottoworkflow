package com.ottocap.NewWorkFlow.server.ottopdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;









class RoundRectangle
  implements PdfPCellEvent
{
  BaseColor mycolor = BaseColor.BLACK;
  Boolean myfill = Boolean.valueOf(false);
  
  public void setColor(BaseColor color) {
    this.mycolor = color;
  }
  
  private BaseColor getColor() {
    return this.mycolor;
  }
  
  public void setFill(Boolean fill) {
    this.myfill = fill;
  }
  
  private Boolean getFill() {
    return this.myfill;
  }
  
  public void cellLayout(PdfPCell cell, Rectangle rect, PdfContentByte[] canvas)
  {
    PdfContentByte cb = canvas[2];
    cb.setLineWidth(0.5F);
    cb.setColorStroke(getColor());
    cb.roundRectangle(rect.getLeft(), rect.getBottom(), rect.getWidth(), rect.getHeight(), 3.0F);
    if (getFill().booleanValue()) {
      cb.setColorFill(getColor());
      cb.fillStroke();
      cb.resetRGBColorFill();
    } else {
      cb.stroke();
    }
    cb.resetRGBColorStroke();
  }
}
