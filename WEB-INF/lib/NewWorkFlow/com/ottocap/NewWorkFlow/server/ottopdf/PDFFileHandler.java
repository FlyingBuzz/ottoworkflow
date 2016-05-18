package com.ottocap.NewWorkFlow.server.ottopdf;

import com.itextpdf.text.Image;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderData;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataItem;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataLogo;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataLogoColorway;
import com.ottocap.NewWorkFlow.server.ISampleGenerator.ISampleDomesticLayout;
import com.ottocap.NewWorkFlow.server.ISampleGenerator.ISampleOverseasLayout;
import com.ottocap.NewWorkFlow.server.LogoGenerator.DSTImage;
import com.ottocap.NewWorkFlow.server.SQLTable;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Iterator;
import java.util.TreeMap;
import javax.imageio.IIOException;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;











public class PDFFileHandler
{
  TreeMap<String, Image> myimages = new TreeMap();
  


  public boolean containsKey(String key)
  {
    return this.myimages.containsKey(key);
  }
  
  public Image getDomesticISampleImage(String imagekey, ExtendOrderDataItem isampledata) throws Exception {
    if (this.myimages.containsKey(imagekey)) {
      return (Image)this.myimages.get(imagekey);
    }
    
    Image logopreview = null;
    try {
      ISampleDomesticLayout mycap = new ISampleDomesticLayout(isampledata);
      mycap.drawImage();
      if (!mycap.isEmptyImage()) {
        logopreview = Image.getInstance(mycap.getImage(), null);
      }
    } catch (Exception e) {
      logopreview = null;
    }
    
    this.myimages.put(imagekey, logopreview);
    return logopreview;
  }
  
  public Image getOverseasISampleImage(String imagekey, ExtendOrderDataItem isampledata) throws Exception
  {
    if (this.myimages.containsKey(imagekey)) {
      return (Image)this.myimages.get(imagekey);
    }
    
    Image logopreview = null;
    try {
      ISampleOverseasLayout mycap = new ISampleOverseasLayout(isampledata);
      mycap.drawImage();
      if (!mycap.isEmptyImage()) {
        logopreview = Image.getInstance(mycap.getImage(), null);
      }
    } catch (Exception e) {
      logopreview = null;
    }
    
    this.myimages.put(imagekey, logopreview);
    return logopreview;
  }
  
  public Image getImageNoConvert(String imagekey, String filelocation) throws Exception
  {
    if (this.myimages.containsKey(imagekey)) {
      return (Image)this.myimages.get(imagekey);
    }
    Image logopreview = Image.getInstance(filelocation);
    this.myimages.put(imagekey, logopreview);
    return logopreview;
  }
  
  public Image getImage(String imagekey, String filelocation) throws Exception
  {
    return getImage(imagekey, filelocation, 0, 0);
  }
  
  public Image getImage(String imagekey, String filelocation, int width, int height) throws Exception
  {
    if (this.myimages.containsKey(imagekey)) {
      return (Image)this.myimages.get(imagekey);
    }
    
    Image logopreview;
    try
    {
      BufferedImage realimage = ImageIO.read(new File(filelocation));
      if ((width != 0) && (height != 0)) {
        realimage = scaleToFit(realimage, width, height);
      } else {
        realimage = scaleToFit(realimage, realimage.getWidth(), realimage.getHeight());
      }
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ImageOutputStream ios = ImageIO.createImageOutputStream(baos);
      Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("jpeg");
      ImageWriter writer = (ImageWriter)iter.next();
      ImageWriteParam iwp = writer.getDefaultWriteParam();
      iwp.setCompressionMode(2);
      iwp.setCompressionQuality(1.0F);
      writer.setOutput(ios);
      IIOImage image = new IIOImage(realimage, null, null);
      writer.write(null, image, iwp);
      logopreview = Image.getInstance(baos.toByteArray());
    } catch (IIOException e) { Image logopreview;
      logopreview = Image.getInstance(filelocation);
    }
    
    this.myimages.put(imagekey, logopreview);
    return logopreview;
  }
  
  public Image getDSTImage(String imagekey, ExtendOrderData orderdata, ExtendOrderDataLogo orderdatalogo, SQLTable sqltable, int width, int height)
    throws Exception
  {
    if (this.myimages.containsKey(imagekey)) {
      return (Image)this.myimages.get(imagekey);
    }
    
    String filename = orderdatalogo.getFilename().toLowerCase().endsWith(".dst") ? orderdatalogo.getFilename() : orderdatalogo.getDstFilename();
    
    DSTImage myimage = new DSTImage("C:\\WorkFlow\\NewWorkflowData\\" + orderdata.getOrderTypeFolder() + "Data/" + orderdata.getHiddenKey() + "/logos/" + filename);
    
    String[] mycolor = new String[myimage.getTotalColors()];
    
    for (int i = 0; i < myimage.getTotalColors(); i++) {
      try {
        mycolor[i] = orderdatalogo.getColorway(i).getConvertCodeColorValue();
      }
      catch (Exception localException) {}
    }
    
    BufferedImage resizedImage = myimage.showResizedPNG(mycolor);
    
    if ((width != 0) && (height != 0)) {
      resizedImage = scaleToFit(resizedImage, width, height);
    }
    
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ImageOutputStream ios = ImageIO.createImageOutputStream(baos);
    Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("jpeg");
    ImageWriter writer = (ImageWriter)iter.next();
    ImageWriteParam iwp = writer.getDefaultWriteParam();
    iwp.setCompressionMode(2);
    iwp.setCompressionQuality(1.0F);
    writer.setOutput(ios);
    IIOImage image = new IIOImage(resizedImage, null, null);
    writer.write(null, image, iwp);
    Image logopreview = Image.getInstance(baos.toByteArray());
    
    this.myimages.put(imagekey, logopreview);
    return logopreview;
  }
  
  public Image getBiggerDSTImage(String imagekey, ExtendOrderData orderdata, ExtendOrderDataLogo orderdatalogo, SQLTable sqltable, int width, int height) throws Exception
  {
    if (this.myimages.containsKey(imagekey)) {
      return (Image)this.myimages.get(imagekey);
    }
    
    String filename = orderdatalogo.getFilename().toLowerCase().endsWith(".dst") ? orderdatalogo.getFilename() : orderdatalogo.getDstFilename();
    
    DSTImage myimage = new DSTImage("C:\\WorkFlow\\NewWorkflowData\\" + orderdata.getOrderTypeFolder() + "Data/" + orderdata.getHiddenKey() + "/logos/" + filename);
    
    String[] mycolor = new String[myimage.getTotalColors()];
    
    for (int i = 0; i < myimage.getTotalColors(); i++) {
      try {
        mycolor[i] = orderdatalogo.getColorway(i).getConvertCodeColorValue();
      }
      catch (Exception localException) {}
    }
    
    BufferedImage resizedImage = myimage.showPNG(mycolor);
    
    if ((width != 0) && (height != 0)) {
      resizedImage = scaleToFit(resizedImage, width, height);
    }
    
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ImageOutputStream ios = ImageIO.createImageOutputStream(baos);
    Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("jpeg");
    ImageWriter writer = (ImageWriter)iter.next();
    ImageWriteParam iwp = writer.getDefaultWriteParam();
    iwp.setCompressionMode(2);
    iwp.setCompressionQuality(1.0F);
    writer.setOutput(ios);
    IIOImage image = new IIOImage(resizedImage, null, null);
    writer.write(null, image, iwp);
    Image logopreview = Image.getInstance(baos.toByteArray());
    
    this.myimages.put(imagekey, logopreview);
    return logopreview;
  }
  
  public Image getDSTImageNoConvert(String imagekey, ExtendOrderData orderdata, ExtendOrderDataLogo orderdatalogo, SQLTable sqltable) throws Exception
  {
    if (this.myimages.containsKey(imagekey)) {
      return (Image)this.myimages.get(imagekey);
    }
    
    String filename = orderdatalogo.getFilename().toLowerCase().endsWith(".dst") ? orderdatalogo.getFilename() : orderdatalogo.getDstFilename();
    
    DSTImage myimage = new DSTImage("C:\\WorkFlow\\NewWorkflowData\\" + orderdata.getOrderTypeFolder() + "Data/" + orderdata.getHiddenKey() + "/logos/" + filename);
    
    String[] mycolor = new String[myimage.getTotalColors()];
    for (int i = 0; i < myimage.getTotalColors(); i++) {
      mycolor[i] = orderdatalogo.getColorway(i).getConvertCodeColorValue();
    }
    BufferedImage resizedImage = myimage.showResizedPNG(mycolor);
    
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ImageOutputStream ios = ImageIO.createImageOutputStream(baos);
    Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("png");
    ImageWriter writer = (ImageWriter)iter.next();
    ImageWriteParam iwp = writer.getDefaultWriteParam();
    

    writer.setOutput(ios);
    IIOImage image = new IIOImage(resizedImage, null, null);
    writer.write(null, image, iwp);
    Image logopreview = Image.getInstance(baos.toByteArray());
    
    this.myimages.put(imagekey, logopreview);
    return logopreview;
  }
  
  public Image getDSTImageNoResize(String imagekey, ExtendOrderData orderdata, ExtendOrderDataLogo orderdatalogo, SQLTable sqltable) throws Exception
  {
    if (this.myimages.containsKey(imagekey)) {
      return (Image)this.myimages.get(imagekey);
    }
    
    String filename = orderdatalogo.getFilename().toLowerCase().endsWith(".dst") ? orderdatalogo.getFilename() : orderdatalogo.getDstFilename();
    
    DSTImage myimage = new DSTImage("C:\\WorkFlow\\NewWorkflowData\\" + orderdata.getOrderTypeFolder() + "Data/" + orderdata.getHiddenKey() + "/logos/" + filename);
    
    String[] mycolor = new String[myimage.getTotalColors()];
    for (int i = 0; i < myimage.getTotalColors(); i++) {
      mycolor[i] = orderdatalogo.getColorway(i).getConvertCodeColorValue();
    }
    BufferedImage resizedImage = myimage.showPNG(mycolor);
    
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ImageOutputStream ios = ImageIO.createImageOutputStream(baos);
    Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("png");
    ImageWriter writer = (ImageWriter)iter.next();
    ImageWriteParam iwp = writer.getDefaultWriteParam();
    

    writer.setOutput(ios);
    IIOImage image = new IIOImage(resizedImage, null, null);
    writer.write(null, image, iwp);
    Image logopreview = Image.getInstance(baos.toByteArray());
    
    this.myimages.put(imagekey, logopreview);
    return logopreview;
  }
  
  private BufferedImage scaleToFit(BufferedImage myimage, int maxwidth, int maxheight)
    throws Exception
  {
    if (myimage.getHeight() > maxheight)
    {
      BufferedImage scaleimage = new BufferedImage((int)(myimage.getWidth() * (maxheight / myimage.getHeight())), maxheight, 1);
      Graphics2D g = scaleimage.createGraphics();
      g.drawImage(myimage.getScaledInstance((int)(myimage.getWidth() * (maxheight / myimage.getHeight())), maxheight, 4), 0, 0, null);
      g.dispose();
      myimage = scaleimage;
    }
    if (myimage.getWidth() > maxwidth)
    {
      BufferedImage scaleimage = new BufferedImage(maxwidth, (int)(myimage.getHeight() * (maxwidth / myimage.getWidth())), 1);
      Graphics2D g = scaleimage.createGraphics();
      g.drawImage(myimage.getScaledInstance(maxwidth, (int)(myimage.getHeight() * (maxwidth / myimage.getWidth())), 4), 0, 0, null);
      g.dispose();
      myimage = scaleimage;
    }
    
    BufferedImage finalimage = new BufferedImage(myimage.getWidth(), myimage.getHeight(), 1);
    Graphics2D g = finalimage.createGraphics();
    g.drawImage(myimage.getScaledInstance(myimage.getWidth(), myimage.getHeight(), 4), 0, 0, null);
    g.dispose();
    myimage = finalimage;
    
    return myimage;
  }
}
