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















public class ISampleDomesticLayout
{
  private BufferedImage newimage = new BufferedImage(825, 638, 1);
  
  private ExtendOrderDataItem capdata = new ExtendOrderDataItem();
  boolean emptyimage = true;
  
  public ISampleDomesticLayout(ExtendOrderDataItem capdata) {
    this.capdata = capdata;
  }
  
  public void drawImage() throws Exception
  {
    if ((this.capdata.getStyleType() == StyleInformationData.StyleType.DOMESTIC) || (this.capdata.getStyleType() == StyleInformationData.StyleType.DOMESTIC_SPECIAL)) {
      if (this.capdata.getStyleType() == StyleInformationData.StyleType.DOMESTIC_SPECIAL) {
        this.capdata.setStyleNumber(this.capdata.getStyleNumber().substring(0, this.capdata.getStyleNumber().length() - 1));
      }
      Graphics2D g = this.newimage.createGraphics();
      g.setPaint(Color.WHITE);
      g.fillRect(0, 0, this.newimage.getWidth(), this.newimage.getHeight());
      try
      {
        BufferedImage frontimage = ImageIO.read(new File("C:\\WorkFlow\\NewWorkflowData\\images/isample/" + this.capdata.getStyleNumber() + "/" + this.capdata.getStyleNumber() + "_" + this.capdata.getColorCode() + "-F.jpg"));
        BufferedImage scaledfrontimage = scaleToFit(frontimage, 412, 299);
        g.drawImage(scaledfrontimage, 206 - scaledfrontimage.getWidth() / 2, 149 - scaledfrontimage.getHeight() / 2, null);
        this.emptyimage = false;
      }
      catch (Exception localException) {}
      try {
        BufferedImage leftimage = ImageIO.read(new File("C:\\WorkFlow\\NewWorkflowData\\images/isample/" + this.capdata.getStyleNumber() + "/" + this.capdata.getStyleNumber() + "_" + this.capdata.getColorCode() + "-LS.jpg"));
        BufferedImage scaledleftimage = scaleToFit(leftimage, 412, 299);
        g.drawImage(scaledleftimage, 618 - scaledleftimage.getWidth() / 2, 149 - scaledleftimage.getHeight() / 2, null);
        this.emptyimage = false;
      }
      catch (Exception localException1) {}
      try {
        BufferedImage backimage = ImageIO.read(new File("C:\\WorkFlow\\NewWorkflowData\\images/isample/" + this.capdata.getStyleNumber() + "/" + this.capdata.getStyleNumber() + "_" + this.capdata.getColorCode() + "-B.jpg"));
        if ((this.capdata.getCategory().equals("Sun Visor")) || (this.capdata.getCategory().equals("Specialty Cap")) || (this.capdata.getCategory().equals("Knit Caps")) || (this.capdata.getCategory().equals("Bag")) || (this.capdata.getCategory().equals("Accessory Items"))) {
          BufferedImage scaledbackimage = scaleToFit(backimage, 412, 299);
          g.drawImage(scaledbackimage, 206 - scaledbackimage.getWidth() / 2, 468 - scaledbackimage.getHeight() / 2, null);
        } else {
          BufferedImage scaledbackimage = scaleToFit(backimage, 312, 299);
          g.drawImage(scaledbackimage, 206 - scaledbackimage.getWidth() / 2, 468 - scaledbackimage.getHeight() / 2, null);
        }
        this.emptyimage = false;
      }
      catch (Exception localException2) {}
      try {
        BufferedImage rightimage = ImageIO.read(new File("C:\\WorkFlow\\NewWorkflowData\\images/isample/" + this.capdata.getStyleNumber() + "/" + this.capdata.getStyleNumber() + "_" + this.capdata.getColorCode() + "-RS.jpg"));
        BufferedImage scaledrightimage = scaleToFit(rightimage, 412, 299);
        g.drawImage(scaledrightimage, 618 - scaledrightimage.getWidth() / 2, 468 - scaledrightimage.getHeight() / 2, null);
        this.emptyimage = false;
      }
      catch (Exception localException3) {}
      
      Font arialfont = new Font("Arial", 1, 20);
      g.setFont(arialfont);
      g.setColor(Color.BLACK);
      g.drawString("Front View", 164, 315);
      g.drawString("Left View", 665, 315);
      g.drawString("Back View", 164, 634);
      g.drawString("Right View", 465, 634);
      
      g.dispose();
    } else if (this.capdata.getStyleType() == StyleInformationData.StyleType.DOMESTIC_TOTEBAGS)
    {
      Graphics2D g = this.newimage.createGraphics();
      g.setPaint(Color.WHITE);
      g.fillRect(0, 0, this.newimage.getWidth(), this.newimage.getHeight());
      try
      {
        BufferedImage frontimage = ImageIO.read(new File("C:\\WorkFlow\\NewWorkflowData\\images/isample/" + this.capdata.getStyleNumber() + "/" + this.capdata.getStyleNumber() + "_" + this.capdata.getColorCode() + "-F.jpg"));
        BufferedImage scaledfrontimage = scaleToFit(frontimage, 412, 600);
        g.drawImage(scaledfrontimage, 206 - scaledfrontimage.getWidth() / 2, 300 - scaledfrontimage.getHeight() / 2, null);
        this.emptyimage = false;
      }
      catch (Exception localException4) {}
      try {
        BufferedImage backimage = ImageIO.read(new File("C:\\WorkFlow\\NewWorkflowData\\images/isample/" + this.capdata.getStyleNumber() + "/" + this.capdata.getStyleNumber() + "_" + this.capdata.getColorCode() + "-F.jpg"));
        BufferedImage scaledbackimage = scaleToFit(backimage, 412, 600);
        g.drawImage(scaledbackimage, 619 - scaledbackimage.getWidth() / 2, 300 - scaledbackimage.getHeight() / 2, null);
        this.emptyimage = false;
      }
      catch (Exception localException5) {}
      
      Font arialfont = new Font("Arial", 1, 20);
      g.setFont(arialfont);
      g.setColor(Color.BLACK);
      g.drawString("Front View", 164, 634);
      g.drawString("Back View", 580, 634);
      
      g.dispose();
    } else if (this.capdata.getStyleType() == StyleInformationData.StyleType.DOMESTIC_APRONS)
    {
      Graphics2D g = this.newimage.createGraphics();
      g.setPaint(Color.WHITE);
      g.fillRect(0, 0, this.newimage.getWidth(), this.newimage.getHeight());
      try
      {
        BufferedImage frontimage = ImageIO.read(new File("C:\\WorkFlow\\NewWorkflowData\\images/isample/" + this.capdata.getStyleNumber() + "/" + this.capdata.getStyleNumber() + "_" + this.capdata.getColorCode() + "-F.jpg"));
        BufferedImage scaledfrontimage = scaleToFit(frontimage, 412, 600);
        g.drawImage(scaledfrontimage, 206 - scaledfrontimage.getWidth() / 2, 300 - scaledfrontimage.getHeight() / 2, null);
        this.emptyimage = false;
      }
      catch (Exception localException6) {}
      try {
        BufferedImage backimage = ImageIO.read(new File("C:\\WorkFlow\\NewWorkflowData\\images/isample/" + this.capdata.getStyleNumber() + "/" + this.capdata.getStyleNumber() + "_" + this.capdata.getColorCode() + "-F.jpg"));
        BufferedImage scaledbackimage = scaleToFit(backimage, 412, 600);
        g.drawImage(scaledbackimage, 619 - scaledbackimage.getWidth() / 2, 300 - scaledbackimage.getHeight() / 2, null);
        this.emptyimage = false;
      }
      catch (Exception localException7) {}
      
      Font arialfont = new Font("Arial", 1, 20);
      g.setFont(arialfont);
      g.setColor(Color.BLACK);
      g.drawString("Front View", 164, 634);
      g.drawString("Back View", 580, 634);
      
      g.dispose();
    } else if ((this.capdata.getStyleType() == StyleInformationData.StyleType.OVERSEAS) || (this.capdata.getStyleType() == StyleInformationData.StyleType.DOMESTIC_SHIRTS) || (this.capdata.getStyleType() == StyleInformationData.StyleType.DOMESTIC_SWEATSHIRTS)) {
      SQLTable mytable = new SQLTable();
      
      Graphics2D g = this.newimage.createGraphics();
      g.setPaint(Color.WHITE);
      g.fillRect(0, 0, this.newimage.getWidth(), this.newimage.getHeight());
      try
      {
        ISampleLineArtDrawer backart = new ISampleLineArtDrawer(this.capdata, ExtendOrderDataItem.SideView.BACK, mytable);
        BufferedImage backimage = backart.getPNGImage();
        if ((this.capdata.getCategory().equals("Sun Visor")) || (this.capdata.getCategory().equals("Specialty Cap")) || (this.capdata.getCategory().equals("Knit Caps")) || (this.capdata.getCategory().equals("Bag")) || (this.capdata.getCategory().equals("Accessory Items"))) {
          BufferedImage scaledbackimage = scaleToFit(backimage, 412, 299);
          g.drawImage(scaledbackimage, 206 - scaledbackimage.getWidth() / 2, 468 - scaledbackimage.getHeight() / 2, null);
        } else {
          BufferedImage scaledbackimage = scaleToFit(backimage, 312, 299);
          g.drawImage(scaledbackimage, 206 - scaledbackimage.getWidth() / 2, 468 - scaledbackimage.getHeight() / 2, null);
        }
        this.emptyimage = false;
      }
      catch (Exception localException8) {}
      try {
        ISampleLineArtDrawer frontart = new ISampleLineArtDrawer(this.capdata, ExtendOrderDataItem.SideView.FRONT, mytable);
        BufferedImage frontimage = frontart.getPNGImage();
        BufferedImage scaledfrontimage = scaleToFit(frontimage, 412, 299);
        g.drawImage(scaledfrontimage, 206 - scaledfrontimage.getWidth() / 2, 149 - scaledfrontimage.getHeight() / 2, null);
        this.emptyimage = false;
      }
      catch (Exception localException9) {}
      try {
        ISampleLineArtDrawer rightart = new ISampleLineArtDrawer(this.capdata, ExtendOrderDataItem.SideView.RIGHT, mytable);
        BufferedImage rightimage = rightart.getPNGImage();
        BufferedImage scaledrightimage = scaleToFit(rightimage, 412, 299);
        g.drawImage(scaledrightimage, 618 - scaledrightimage.getWidth() / 2, 468 - scaledrightimage.getHeight() / 2, null);
        this.emptyimage = false;
      }
      catch (Exception localException10) {}
      try {
        ISampleLineArtDrawer leftart = new ISampleLineArtDrawer(this.capdata, ExtendOrderDataItem.SideView.LEFT, mytable);
        BufferedImage leftimage = leftart.getPNGImage();
        BufferedImage scaledleftimage = scaleToFit(leftimage, 412, 299);
        g.drawImage(scaledleftimage, 618 - scaledleftimage.getWidth() / 2, 149 - scaledleftimage.getHeight() / 2, null);
        this.emptyimage = false;
      }
      catch (Exception localException11) {}
      mytable.closeSQL();
      
      Font arialfont = new Font("Arial", 1, 20);
      g.setFont(arialfont);
      g.setColor(Color.BLACK);
      g.drawString("Front View", 164, 315);
      g.drawString("Left View", 665, 315);
      g.drawString("Back View", 164, 634);
      g.drawString("Right View", 465, 634);
      







      g.dispose();
    } else {
      throw new Exception("no image");
    }
  }
  
  public boolean isEmptyImage()
  {
    return this.emptyimage;
  }
  
  public BufferedImage getImage() throws Exception {
    return this.newimage;
  }
  



  public void showImage(HttpServletResponse response)
    throws Exception
  {
    response.setContentType("image/png");
    OutputStream myoutput = response.getOutputStream();
    ImageIO.write(this.newimage, "png", myoutput);
  }
  
  public void showQuoteImage(HttpServletResponse response) throws Exception
  {
    this.newimage = scaleToFit(this.newimage, 649, 502);
    
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
