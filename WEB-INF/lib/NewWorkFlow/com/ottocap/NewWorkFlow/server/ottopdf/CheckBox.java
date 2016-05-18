package com.ottocap.NewWorkFlow.server.ottopdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;









class CheckBox
  implements PdfPCellEvent
{
  private boolean isChecked = false;
  
  public void setChecked(Boolean ischecked) {
    this.isChecked = ischecked.booleanValue();
  }
  
  public void cellLayout(PdfPCell cell, Rectangle rect, PdfContentByte[] canvas)
  {
    PdfContentByte cb = canvas[2];
    cb.setLineWidth(0.0F);
    cb.setColorStroke(BaseColor.BLACK);
    cb.rectangle(rect.getLeft(), rect.getBottom(), 10.0F, 10.0F);
    cb.stroke();
    
    if (this.isChecked) {
      cb.moveTo(rect.getLeft(), rect.getBottom());
      cb.lineTo(rect.getLeft() + 10.0F, rect.getBottom() + 10.0F);
      cb.moveTo(rect.getLeft() + 10.0F, rect.getBottom());
      cb.lineTo(rect.getLeft(), rect.getBottom() + 10.0F);
      cb.stroke();
    }
    
    cb.resetRGBColorStroke();
  }
}
