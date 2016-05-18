package com.ottocap.NewWorkFlow.server.ottopdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;









class MiddleLine
  implements PdfPCellEvent
{
  public void cellLayout(PdfPCell cell, Rectangle rect, PdfContentByte[] canvas)
  {
    PdfContentByte cb = canvas[2];
    cb.setColorStroke(BaseColor.BLACK);
    cb.setLineWidth(0.15F);
    cb.moveTo(rect.getLeft(), (rect.getTop() + rect.getBottom()) / 2.0F);
    cb.lineTo(rect.getRight(), (rect.getTop() + rect.getBottom()) / 2.0F);
    cb.stroke();
    cb.resetRGBColorStroke();
  }
}
