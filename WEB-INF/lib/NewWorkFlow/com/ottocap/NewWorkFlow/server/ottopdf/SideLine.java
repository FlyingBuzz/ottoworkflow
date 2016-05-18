package com.ottocap.NewWorkFlow.server.ottopdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;









class SideLine
  implements PdfPCellEvent
{
  private Boolean dashed = Boolean.valueOf(false);
  
  public void setDashed(Boolean isdashed) {
    this.dashed = isdashed;
  }
  
  public void cellLayout(PdfPCell cell, Rectangle rect, PdfContentByte[] canvas)
  {
    PdfContentByte cb = canvas[2];
    cb.setColorStroke(BaseColor.BLACK);
    cb.setLineWidth(0.15F);
    if (this.dashed.booleanValue()) {
      cb.setLineWidth(0.5F);
      cb.setLineDash(2.0F, 2.0F);
    }
    cb.moveTo(rect.getRight(), rect.getTop());
    cb.lineTo(rect.getRight(), rect.getBottom());
    cb.stroke();
    cb.setLineDash(0.0F);
    cb.resetRGBColorStroke();
  }
}
