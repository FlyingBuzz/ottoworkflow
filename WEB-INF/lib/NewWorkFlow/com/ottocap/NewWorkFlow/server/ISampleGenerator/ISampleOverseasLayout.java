package com.ottocap.NewWorkFlow.server.ISampleGenerator;

import com.ottocap.NewWorkFlow.client.StyleInformationData.StyleType;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataItem;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataItem.SideView;
import com.ottocap.NewWorkFlow.server.SQLTable;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;














public class ISampleOverseasLayout
{
  private BufferedImage newimage = new BufferedImage(1118, 585, 1);
  








  private ExtendOrderDataItem capdata = new ExtendOrderDataItem();
  private boolean emptyimage = true;
  
  public ISampleOverseasLayout(ExtendOrderDataItem capdata)
  {
    this.capdata = capdata;
  }
  
  public boolean isEmptyImage() {
    return this.emptyimage;
  }
  
  public void drawImage() throws Exception
  {
    if ((this.capdata.getStyleType() == StyleInformationData.StyleType.OVERSEAS_INSTOCK) && (this.capdata.getColorCode() != null) && (!this.capdata.getColorCode().equals(""))) {
      Graphics2D g = this.newimage.createGraphics();
      g.setPaint(Color.WHITE);
      g.fillRect(0, 0, this.newimage.getWidth(), this.newimage.getHeight());
      try
      {
        BufferedImage frontimage = ImageIO.read(new File("C:\\WorkFlow\\NewWorkflowData\\images/isample/" + this.capdata.getStyleNumber() + "/" + this.capdata.getStyleNumber() + "_" + this.capdata.getColorCode() + "-F.jpg"));
        BufferedImage scaledfrontimage = scaleToFit(frontimage, 372, 272);
        g.drawImage(scaledfrontimage, 559 - scaledfrontimage.getWidth() / 2, 136 - scaledfrontimage.getHeight() / 2, null);
        this.emptyimage = false;
      }
      catch (Exception localException) {}
      try {
        BufferedImage leftimage = ImageIO.read(new File("C:\\WorkFlow\\NewWorkflowData\\images/isample/" + this.capdata.getStyleNumber() + "/" + this.capdata.getStyleNumber() + "_" + this.capdata.getColorCode() + "-LS.jpg"));
        BufferedImage scaledleftimage = scaleToFit(leftimage, 372, 272);
        g.drawImage(scaledleftimage, 931 - scaledleftimage.getWidth() / 2, 136 - scaledleftimage.getHeight() / 2, null);
        this.emptyimage = false;
      }
      catch (Exception localException1) {}
      try {
        BufferedImage backimage = ImageIO.read(new File("C:\\WorkFlow\\NewWorkflowData\\images/isample/" + this.capdata.getStyleNumber() + "/" + this.capdata.getStyleNumber() + "_" + this.capdata.getColorCode() + "-B.jpg"));
        if ((this.capdata.getCategory().equals("Sun Visor")) || (this.capdata.getCategory().equals("Specialty Cap")) || (this.capdata.getCategory().equals("Knit Caps")) || (this.capdata.getCategory().equals("Bag")) || (this.capdata.getCategory().equals("Accessory Items"))) {
          BufferedImage scaledbackimage = scaleToFit(backimage, 372, 272);
          g.drawImage(scaledbackimage, 559 - scaledbackimage.getWidth() / 2, 428 - scaledbackimage.getHeight() / 2, null);
        } else {
          BufferedImage scaledbackimage = scaleToFit(backimage, 272, 272);
          g.drawImage(scaledbackimage, 559 - scaledbackimage.getWidth() / 2, 428 - scaledbackimage.getHeight() / 2, null);
        }
        this.emptyimage = false;
      }
      catch (Exception localException2) {}
      try {
        BufferedImage rightimage = ImageIO.read(new File("C:\\WorkFlow\\NewWorkflowData\\images/isample/" + this.capdata.getStyleNumber() + "/" + this.capdata.getStyleNumber() + "_" + this.capdata.getColorCode() + "-RS.jpg"));
        BufferedImage scaledrightimage = scaleToFit(rightimage, 372, 272);
        g.drawImage(scaledrightimage, 931 - scaledrightimage.getWidth() / 2, 428 - scaledrightimage.getHeight() / 2, null);
        this.emptyimage = false;
      }
      catch (Exception localException3) {}
      try {
        BufferedImage insideimage = ImageIO.read(new File("C:\\WorkFlow\\NewWorkflowData\\images/isample/" + this.capdata.getStyleNumber() + "/" + this.capdata.getStyleNumber() + "_" + this.capdata.getColorCode() + "-IS.jpg"));
        BufferedImage scaledinsideimage = scaleToFit(insideimage, 372, 272);
        g.drawImage(scaledinsideimage, 186 - scaledinsideimage.getWidth() / 2, 428 - scaledinsideimage.getHeight() / 2, null);
        this.emptyimage = false;
      }
      catch (Exception localException4) {}
      
      Font arialfont = new Font("Arial", 1, 20);
      g.setFont(arialfont);
      g.setColor(Color.BLACK);
      g.drawString("Front View", 520, 288);
      g.drawString("Left View", 985, 288);
      g.drawString("Inside View", 130, 581);
      g.drawString("Back View", 520, 581);
      g.drawString("Right View", 790, 581);
      
      g.dispose();
    }
    else if ((this.capdata.getStyleType() == StyleInformationData.StyleType.OVERSEAS) || (this.capdata.getStyleType() == StyleInformationData.StyleType.DOMESTIC_SHIRTS) || (this.capdata.getStyleType() == StyleInformationData.StyleType.DOMESTIC_SWEATSHIRTS) || (this.capdata.getStyleType() == StyleInformationData.StyleType.OVERSEAS_INSTOCK_SHIRTS) || (this.capdata.getStyleType() == StyleInformationData.StyleType.OVERSEAS_INSTOCK_FLATS) || (this.capdata.getStyleType() == StyleInformationData.StyleType.OVERSEAS_INSTOCK) || (this.capdata.getStyleType() == StyleInformationData.StyleType.OVERSEAS_CUSTOMSTYLE)) {
      Graphics2D g = this.newimage.createGraphics();
      g.setPaint(Color.WHITE);
      g.fillRect(0, 0, this.newimage.getWidth(), this.newimage.getHeight());
      
      SQLTable mytable = new SQLTable();
      try
      {
        ISampleLineArtDrawer backart = new ISampleLineArtDrawer(this.capdata, ExtendOrderDataItem.SideView.BACK, mytable);
        BufferedImage backimage = backart.getPNGImage();
        if ((this.capdata.getCategory().equals("Sun Visor")) || (this.capdata.getCategory().equals("Specialty Cap")) || (this.capdata.getCategory().equals("Knit Caps")) || (this.capdata.getCategory().equals("Bag")) || (this.capdata.getCategory().equals("Accessory Items"))) {
          BufferedImage scaledbackimage = scaleToFit(backimage, 372, 272);
          g.drawImage(scaledbackimage, 559 - scaledbackimage.getWidth() / 2, 428 - scaledbackimage.getHeight() / 2, null);
        } else {
          BufferedImage scaledbackimage = scaleToFit(backimage, 272, 272);
          g.drawImage(scaledbackimage, 559 - scaledbackimage.getWidth() / 2, 428 - scaledbackimage.getHeight() / 2, null);
        }
        this.emptyimage = false;
      }
      catch (Exception localException5) {}
      try
      {
        ISampleLineArtDrawer frontart = new ISampleLineArtDrawer(this.capdata, ExtendOrderDataItem.SideView.FRONT, mytable);
        BufferedImage frontimage = frontart.getPNGImage();
        BufferedImage scaledfrontimage = scaleToFit(frontimage, 372, 272);
        g.drawImage(scaledfrontimage, 559 - scaledfrontimage.getWidth() / 2, 136 - scaledfrontimage.getHeight() / 2, null);
        this.emptyimage = false;
      }
      catch (Exception localException6) {}
      try {
        ISampleLineArtDrawer rightart = new ISampleLineArtDrawer(this.capdata, ExtendOrderDataItem.SideView.RIGHT, mytable);
        BufferedImage rightimage = rightart.getPNGImage();
        BufferedImage scaledrightimage = scaleToFit(rightimage, 372, 272);
        g.drawImage(scaledrightimage, 931 - scaledrightimage.getWidth() / 2, 428 - scaledrightimage.getHeight() / 2, null);
        this.emptyimage = false;
      }
      catch (Exception localException7) {}
      try {
        ISampleLineArtDrawer leftart = new ISampleLineArtDrawer(this.capdata, ExtendOrderDataItem.SideView.LEFT, mytable);
        BufferedImage leftimage = leftart.getPNGImage();
        BufferedImage scaledleftimage = scaleToFit(leftimage, 372, 272);
        g.drawImage(scaledleftimage, 931 - scaledleftimage.getWidth() / 2, 136 - scaledleftimage.getHeight() / 2, null);
        this.emptyimage = false;
      }
      catch (Exception localException8) {}
      try {
        ISampleLineArtDrawer insideart = new ISampleLineArtDrawer(this.capdata, ExtendOrderDataItem.SideView.INSIDE, mytable);
        BufferedImage insideimage = insideart.getPNGImage();
        BufferedImage scaledinsideimage = scaleToFit(insideimage, 372, 272);
        g.drawImage(scaledinsideimage, 186 - scaledinsideimage.getWidth() / 2, 428 - scaledinsideimage.getHeight() / 2, null);
        this.emptyimage = false;
      }
      catch (Exception localException9) {}
      
      mytable.closeSQL();
      
      Font arialfont = new Font("Arial", 1, 20);
      g.setFont(arialfont);
      g.setColor(Color.BLACK);
      g.drawString("Front View", 520, 288);
      g.drawString("Left View", 985, 288);
      
      if ((this.capdata.getStyleType() != StyleInformationData.StyleType.DOMESTIC_SHIRTS) && (this.capdata.getStyleType() != StyleInformationData.StyleType.DOMESTIC_SWEATSHIRTS) && (this.capdata.getStyleType() != StyleInformationData.StyleType.OVERSEAS_INSTOCK_SHIRTS) && (this.capdata.getStyleType() != StyleInformationData.StyleType.OVERSEAS_INSTOCK_FLATS)) {
        g.drawString("Inside View", 130, 581);
      }
      g.drawString("Back View", 520, 581);
      g.drawString("Right View", 790, 581);
      
      g.dispose();
    } else if (this.capdata.getStyleType() == StyleInformationData.StyleType.OVERSEAS_PREDESIGNED) {
      Graphics2D g = this.newimage.createGraphics();
      g.setPaint(Color.WHITE);
      g.fillRect(0, 0, this.newimage.getWidth(), this.newimage.getHeight());
      try
      {
        BufferedImage frontimage = ImageIO.read(new File("C:\\WorkFlow\\NewWorkflowData\\images/isample/predesigned/" + this.capdata.getStyleNumber() + "-F.jpg"));
        BufferedImage scaledfrontimage = scaleToFit(frontimage, 372, 272);
        g.drawImage(scaledfrontimage, 559 - scaledfrontimage.getWidth() / 2, 136 - scaledfrontimage.getHeight() / 2, null);
        this.emptyimage = false;
      }
      catch (Exception localException10) {}
      try {
        BufferedImage leftimage = ImageIO.read(new File("C:\\WorkFlow\\NewWorkflowData\\images/isample/predesigned/" + this.capdata.getStyleNumber() + "-LS.jpg"));
        BufferedImage scaledleftimage = scaleToFit(leftimage, 372, 272);
        g.drawImage(scaledleftimage, 931 - scaledleftimage.getWidth() / 2, 136 - scaledleftimage.getHeight() / 2, null);
        this.emptyimage = false;
      }
      catch (Exception localException11) {}
      try {
        BufferedImage backimage = ImageIO.read(new File("C:\\WorkFlow\\NewWorkflowData\\images/isample/predesigned/" + this.capdata.getStyleNumber() + "-B.jpg"));
        BufferedImage scaledbackimage = scaleToFit(backimage, 272, 272);
        g.drawImage(scaledbackimage, 559 - scaledbackimage.getWidth() / 2, 428 - scaledbackimage.getHeight() / 2, null);
        this.emptyimage = false;
      }
      catch (Exception localException12) {}
      try {
        BufferedImage rightimage = ImageIO.read(new File("C:\\WorkFlow\\NewWorkflowData\\images/isample/predesigned/" + this.capdata.getStyleNumber() + "-RS.jpg"));
        BufferedImage scaledrightimage = scaleToFit(rightimage, 372, 272);
        g.drawImage(scaledrightimage, 931 - scaledrightimage.getWidth() / 2, 428 - scaledrightimage.getHeight() / 2, null);
        this.emptyimage = false;
      }
      catch (Exception localException13) {}
      try {
        BufferedImage insideimage = ImageIO.read(new File("C:\\WorkFlow\\NewWorkflowData\\images/isample/predesigned/" + this.capdata.getStyleNumber() + "-IS.jpg"));
        BufferedImage scaledinsideimage = scaleToFit(insideimage, 372, 272);
        g.drawImage(scaledinsideimage, 186 - scaledinsideimage.getWidth() / 2, 428 - scaledinsideimage.getHeight() / 2, null);
        this.emptyimage = false;
      }
      catch (Exception localException14) {}
      
      Font arialfont = new Font("Arial", 1, 20);
      g.setFont(arialfont);
      g.setColor(Color.BLACK);
      g.drawString("Front View", 520, 288);
      g.drawString("Left View", 985, 288);
      g.drawString("Inside View", 130, 581);
      g.drawString("Back View", 520, 581);
      g.drawString("Right View", 790, 581);
      
      g.dispose();
    }
    else {
      throw new Exception("no image");
    }
  }
  




  public BufferedImage getImage()
    throws Exception
  {
    return this.newimage;
  }
  
  public void showImage(HttpServletResponse response) throws Exception
  {
    response.setContentType("image/png");
    OutputStream myoutput = response.getOutputStream();
    ImageIO.write(this.newimage, "png", myoutput);
  }
  
  public void showQuoteImage(HttpServletResponse response)
    throws Exception
  {
    this.newimage = scaleToFit(this.newimage, 960, 502);
    
    response.setContentType("image/png");
    OutputStream myoutput = response.getOutputStream();
    ImageIO.write(this.newimage, "png", myoutput);
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
    return myimage;
  }
}
