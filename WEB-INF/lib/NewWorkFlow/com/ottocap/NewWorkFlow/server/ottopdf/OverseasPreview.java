package com.ottocap.NewWorkFlow.server.ottopdf;

import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;








class OverseasPreview
  implements PdfPCellEvent
{
  Image previewimage;
  
  public void setImage(Image previewimage)
  {
    this.previewimage = previewimage;
  }
  
  public void cellLayout(PdfPCell cell, Rectangle rect, PdfContentByte[] canvas) {
    this.previewimage.setAbsolutePosition(10.0F, 10.0F);
    this.previewimage.scaleToFit(564.0F, 295.0F);
    PdfContentByte cb = canvas[2];
    try
    {
      cb.addImage(this.previewimage, this.previewimage.getScaledWidth(), 0.0F, 0.0F, this.previewimage.getScaledHeight(), 564.0F - this.previewimage.getScaledWidth() + rect.getLeft(), rect.getBottom(), true);
    }
    catch (Exception localException) {}
  }
}
