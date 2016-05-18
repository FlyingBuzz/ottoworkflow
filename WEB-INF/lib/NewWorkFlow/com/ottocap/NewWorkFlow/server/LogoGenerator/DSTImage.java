package com.ottocap.NewWorkFlow.server.LogoGenerator;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Line2D.Double;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.Random;
import java.util.Vector;
import javax.imageio.ImageIO;
import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;














public class DSTImage
{
  private String filepath;
  private int stitchcount = 0;
  
  private double stitchwidth = 0.0D;
  
  private double stitchheight = 0.0D;
  
  private int totalcolors = 0;
  
  private int maxposx = 0;
  
  private int maxnegx = 0;
  
  private int maxposy = 0;
  
  private int maxnegy = 0;
  
  private int totalinstructions = 0;
  

  private byte[] body;
  
  private int IMAGEMARGIN = 10;
  
  Vector<Integer> stitchs = new Vector();
  





  public DSTImage(String filepath)
    throws Exception
  {
    this.filepath = filepath;
    gatherDSTInfo();
  }
  




  public int getTotalStitchCount()
  {
    return this.stitchcount;
  }
  




  public String getStitchSize()
  {
    double realstitchwidth = (int)(this.stitchwidth / 25.4D * 100.0D) / 100.0D;
    double realstitchheight = (int)(this.stitchheight / 25.4D * 100.0D) / 100.0D;
    
    return realstitchwidth + "\" W * " + realstitchheight + "\" H";
  }
  




  public double getStitchSizeWidth()
  {
    double realstitchwidth = (int)(this.stitchwidth / 25.4D * 100.0D) / 100.0D;
    return realstitchwidth;
  }
  




  public double getStitchSizeHeight()
  {
    double realstitchheight = (int)(this.stitchheight / 25.4D * 100.0D) / 100.0D;
    return realstitchheight;
  }
  




  public int getTotalColors()
  {
    return this.totalcolors;
  }
  






  public int getStitchCount(int pos)
  {
    return ((Integer)this.stitchs.get(pos)).intValue();
  }
  






  private SVGGraphics2D drawDSTImage(String[] colorarray)
    throws Exception
  {
    DOMImplementation domImpl = GenericDOMImplementation.getDOMImplementation();
    
    String svgNS = "http://www.w3.org/2000/svg";
    Document document = domImpl.createDocument(svgNS, "svg", null);
    

    SVGGraphics2D svgGenerator = new SVGGraphics2D(document);
    
    int imagelinex1 = this.IMAGEMARGIN + this.maxnegx;
    int imagelinex2 = this.IMAGEMARGIN + this.maxnegx;
    int imageliney1 = this.IMAGEMARGIN + this.maxposy;
    int imageliney2 = this.IMAGEMARGIN + this.maxposy;
    int hadjump = 0;
    int colorchangecount = 0;
    
    svgGenerator.setSVGCanvasSize(new Dimension(this.IMAGEMARGIN + this.maxposx + this.maxnegx + this.IMAGEMARGIN, this.IMAGEMARGIN + this.maxposy + this.maxnegy + this.IMAGEMARGIN));
    svgGenerator.setStroke(new BasicStroke(5.0F));
    svgGenerator.setPaint(Color.decode(colorarray[0]));
    
    for (int i = 0; i < this.totalinstructions; i++) {
      byte[] instructionset = { this.body[(i * 3)], this.body[(i * 3 + 1)], this.body[(i * 3 + 2)] };
      boolean[] instructionsetasbinary = fromByteArray(instructionset);
      
      int yloc = (instructionsetasbinary[0] != 0 ? 1 : 0) - (instructionsetasbinary[1] != 0 ? 1 : 0) + (instructionsetasbinary[2] != 0 ? 9 : 0) - (instructionsetasbinary[3] != 0 ? 9 : 0) + (instructionsetasbinary[8] != 0 ? 3 : 0) - (instructionsetasbinary[9] != 0 ? 3 : 0) + (instructionsetasbinary[10] != 0 ? 27 : 0) - (instructionsetasbinary[11] != 0 ? 27 : 0) + (instructionsetasbinary[18] != 0 ? 81 : 0) - (
        instructionsetasbinary[19] != 0 ? 81 : 0);
      int xloc = (instructionsetasbinary[5] != 0 ? 9 : 0) - (instructionsetasbinary[4] != 0 ? 9 : 0) - (instructionsetasbinary[6] != 0 ? 1 : 0) + (instructionsetasbinary[7] != 0 ? 1 : 0) - (instructionsetasbinary[12] != 0 ? 27 : 0) + (instructionsetasbinary[13] != 0 ? 27 : 0) - (instructionsetasbinary[14] != 0 ? 3 : 0) + (instructionsetasbinary[15] != 0 ? 3 : 0) - (instructionsetasbinary[20] != 0 ? 81 : 0) + (
        instructionsetasbinary[21] != 0 ? 81 : 0);
      
      if ((instructionsetasbinary[16] == 0) && (instructionsetasbinary[17] == 0))
      {

        imagelinex2 = imagelinex1 + xloc;
        imageliney2 = imageliney1 - yloc;
        
        if (hadjump <= 1) {
          svgGenerator.draw(new Line2D.Double(imagelinex1, imageliney1, imagelinex2, imageliney2));
        }
        hadjump = 0;
        
        imagelinex1 = imagelinex2;
        imageliney1 = imageliney2;
      }
      else if ((instructionsetasbinary[16] != 0) && (instructionsetasbinary[17] == 0))
      {

        imagelinex2 = imagelinex1 + xloc;
        imageliney2 = imageliney1 - yloc;
        

        if ((hadjump == 0) && 
          (i + 2 < this.totalinstructions))
        {
          byte[] tempset1 = { this.body[((i + 1) * 3)], this.body[((i + 1) * 3 + 1)], this.body[((i + 1) * 3 + 2)] };
          boolean[] tempsetbinary1 = fromByteArray(tempset1);
          if ((tempsetbinary1[16] != 0) && (tempsetbinary1[17] == 0)) {
            int tempyloc = (tempsetbinary1[0] != 0 ? 1 : 0) - (tempsetbinary1[1] != 0 ? 1 : 0) + (tempsetbinary1[2] != 0 ? 9 : 0) - (tempsetbinary1[3] != 0 ? 9 : 0) + (tempsetbinary1[8] != 0 ? 3 : 0) - (tempsetbinary1[9] != 0 ? 3 : 0) + (tempsetbinary1[10] != 0 ? 27 : 0) - (tempsetbinary1[11] != 0 ? 27 : 0) + (tempsetbinary1[18] != 0 ? 81 : 0) - (tempsetbinary1[19] != 0 ? 81 : 0);
            int tempxloc = (tempsetbinary1[5] != 0 ? 9 : 0) - (tempsetbinary1[4] != 0 ? 9 : 0) - (tempsetbinary1[6] != 0 ? 1 : 0) + (tempsetbinary1[7] != 0 ? 1 : 0) - (tempsetbinary1[12] != 0 ? 27 : 0) + (tempsetbinary1[13] != 0 ? 27 : 0) - (tempsetbinary1[14] != 0 ? 3 : 0) + (tempsetbinary1[15] != 0 ? 3 : 0) - (tempsetbinary1[20] != 0 ? 81 : 0) + (tempsetbinary1[21] != 0 ? 81 : 0);
            int tempimagelinex2 = imagelinex2 + tempxloc;
            int tempimageliney2 = imageliney2 - tempyloc;
            
            byte[] tempset = { this.body[((i + 2) * 3)], this.body[((i + 2) * 3 + 1)], this.body[((i + 2) * 3 + 2)] };
            boolean[] tempsetbinary = fromByteArray(tempset);
            if ((tempsetbinary[16] == 0) && (tempsetbinary[17] == 0)) {
              svgGenerator.draw(new Line2D.Double(imagelinex1, imageliney1, tempimagelinex2, tempimageliney2));
            }
          }
        }
        


        imagelinex1 = imagelinex2;
        imageliney1 = imageliney2;
        
        hadjump++;
      } else if ((instructionsetasbinary[16] != 0) && (instructionsetasbinary[17] != 0))
      {
        colorchangecount++;
        try {
          svgGenerator.setPaint(Color.decode(colorarray[colorchangecount]));
        } catch (NullPointerException e) {
          svgGenerator.setPaint(Color.BLACK);
        }
        

        imagelinex1 += xloc;
        imageliney1 -= yloc;
        
        hadjump = 3;
      }
    }
    

    return svgGenerator;
  }
  






  private BufferedImage drawPNGImage(String[] colorarray)
    throws Exception
  {
    int EXTRASPACE = 1000;
    int minx = this.IMAGEMARGIN + this.maxnegx + this.maxposx + this.IMAGEMARGIN + EXTRASPACE;
    int miny = this.IMAGEMARGIN + this.maxposy + this.maxnegy + this.IMAGEMARGIN + EXTRASPACE;
    int imagelinex1 = this.IMAGEMARGIN + this.maxnegx + EXTRASPACE / 2;
    int imagelinex2 = this.IMAGEMARGIN + this.maxnegx + EXTRASPACE / 2;
    int imageliney1 = this.IMAGEMARGIN + this.maxposy + EXTRASPACE / 2;
    int imageliney2 = this.IMAGEMARGIN + this.maxposy + EXTRASPACE / 2;
    int hadjump = 0;
    
    int colorchangecount = 0;
    
    BufferedImage newimage = new BufferedImage(this.IMAGEMARGIN + this.maxposx + this.maxnegx + this.IMAGEMARGIN + EXTRASPACE, this.IMAGEMARGIN + this.maxposy + this.maxnegy + this.IMAGEMARGIN + EXTRASPACE, 1);
    Graphics2D g = newimage.createGraphics();
    g.setPaint(Color.WHITE);
    g.fillRect(0, 0, newimage.getWidth(), newimage.getHeight());
    

    g.setStroke(new BasicStroke(4.3F, 1, 1));
    


    for (int i = 0; i < this.totalinstructions; i++) {
      byte[] instructionset = { this.body[(i * 3)], this.body[(i * 3 + 1)], this.body[(i * 3 + 2)] };
      boolean[] instructionsetasbinary = fromByteArray(instructionset);
      
      int yloc = (instructionsetasbinary[0] != 0 ? 1 : 0) - (instructionsetasbinary[1] != 0 ? 1 : 0) + (instructionsetasbinary[2] != 0 ? 9 : 0) - (instructionsetasbinary[3] != 0 ? 9 : 0) + (instructionsetasbinary[8] != 0 ? 3 : 0) - (instructionsetasbinary[9] != 0 ? 3 : 0) + (instructionsetasbinary[10] != 0 ? 27 : 0) - (instructionsetasbinary[11] != 0 ? 27 : 0) + (instructionsetasbinary[18] != 0 ? 81 : 0) - (
        instructionsetasbinary[19] != 0 ? 81 : 0);
      int xloc = (instructionsetasbinary[5] != 0 ? 9 : 0) - (instructionsetasbinary[4] != 0 ? 9 : 0) - (instructionsetasbinary[6] != 0 ? 1 : 0) + (instructionsetasbinary[7] != 0 ? 1 : 0) - (instructionsetasbinary[12] != 0 ? 27 : 0) + (instructionsetasbinary[13] != 0 ? 27 : 0) - (instructionsetasbinary[14] != 0 ? 3 : 0) + (instructionsetasbinary[15] != 0 ? 3 : 0) - (instructionsetasbinary[20] != 0 ? 81 : 0) + (
        instructionsetasbinary[21] != 0 ? 81 : 0);
      
      if (((instructionsetasbinary[16] == 0) && (instructionsetasbinary[17] == 0)) || ((instructionsetasbinary[16] == 0) && (instructionsetasbinary[17] != 0)))
      {

        imagelinex2 = imagelinex1 + xloc;
        imageliney2 = imageliney1 - yloc;
        
        if (hadjump <= 1) {
          try {
            Random randomGenerator = new Random();
            if (randomGenerator.nextInt(2) == 1) {
              g.setPaint(new GradientPaint(imagelinex1, imageliney1, colorDarker(Color.decode(colorarray[colorchangecount]), 0.9D), imagelinex2, imageliney2, Color.decode(colorarray[colorchangecount])));
            } else {
              g.setPaint(new GradientPaint(imagelinex1, imageliney1, colorDarker(Color.decode(colorarray[colorchangecount]), 0.95D), imagelinex2, imageliney2, Color.decode(colorarray[colorchangecount])));
            }
          } catch (Exception e) {
            try {
              g.setPaint(Color.decode(colorarray[colorchangecount]));
            } catch (Exception ex) {
              g.setPaint(Color.BLACK);
            }
          }
          g.drawLine(imagelinex1, imageliney1, imagelinex2, imageliney2);
        }
        hadjump = 0;
        

        imagelinex1 = imagelinex2;
        imageliney1 = imageliney2;
      } else if ((instructionsetasbinary[16] != 0) && (instructionsetasbinary[17] == 0))
      {

        imagelinex2 = imagelinex1 + xloc;
        imageliney2 = imageliney1 - yloc;
        
        if ((hadjump == 0) && 
          (i + 2 < this.totalinstructions))
        {
          byte[] tempset1 = { this.body[((i + 1) * 3)], this.body[((i + 1) * 3 + 1)], this.body[((i + 1) * 3 + 2)] };
          boolean[] tempsetbinary1 = fromByteArray(tempset1);
          if ((tempsetbinary1[16] != 0) && (tempsetbinary1[17] == 0)) {
            int tempyloc = (tempsetbinary1[0] != 0 ? 1 : 0) - (tempsetbinary1[1] != 0 ? 1 : 0) + (tempsetbinary1[2] != 0 ? 9 : 0) - (tempsetbinary1[3] != 0 ? 9 : 0) + (tempsetbinary1[8] != 0 ? 3 : 0) - (tempsetbinary1[9] != 0 ? 3 : 0) + (tempsetbinary1[10] != 0 ? 27 : 0) - (tempsetbinary1[11] != 0 ? 27 : 0) + (tempsetbinary1[18] != 0 ? 81 : 0) - (tempsetbinary1[19] != 0 ? 81 : 0);
            int tempxloc = (tempsetbinary1[5] != 0 ? 9 : 0) - (tempsetbinary1[4] != 0 ? 9 : 0) - (tempsetbinary1[6] != 0 ? 1 : 0) + (tempsetbinary1[7] != 0 ? 1 : 0) - (tempsetbinary1[12] != 0 ? 27 : 0) + (tempsetbinary1[13] != 0 ? 27 : 0) - (tempsetbinary1[14] != 0 ? 3 : 0) + (tempsetbinary1[15] != 0 ? 3 : 0) - (tempsetbinary1[20] != 0 ? 81 : 0) + (tempsetbinary1[21] != 0 ? 81 : 0);
            int tempimagelinex2 = imagelinex2 + tempxloc;
            int tempimageliney2 = imageliney2 - tempyloc;
            
            byte[] tempset = { this.body[((i + 2) * 3)], this.body[((i + 2) * 3 + 1)], this.body[((i + 2) * 3 + 2)] };
            boolean[] tempsetbinary = fromByteArray(tempset);
            if ((tempsetbinary[16] == 0) && (tempsetbinary[17] == 0)) {
              Random randomGenerator = new Random();
              if (randomGenerator.nextInt(2) == 1) {
                g.setPaint(new GradientPaint(imagelinex1, imageliney1, colorDarker(Color.decode(colorarray[colorchangecount]), 0.9D), tempimagelinex2, tempimageliney2, Color.decode(colorarray[colorchangecount])));
              } else {
                g.setPaint(new GradientPaint(imagelinex1, imageliney1, colorDarker(Color.decode(colorarray[colorchangecount]), 0.95D), tempimagelinex2, tempimageliney2, Color.decode(colorarray[colorchangecount])));
              }
              g.drawLine(imagelinex1, imageliney1, tempimagelinex2, tempimageliney2);
            }
          }
        }
        







































        imagelinex1 = imagelinex2;
        imageliney1 = imageliney2;
        
        hadjump++;
      }
      else if ((instructionsetasbinary[16] != 0) && (instructionsetasbinary[17] != 0))
      {
        colorchangecount++;
        





        imagelinex1 += xloc;
        imageliney1 -= yloc;
        

        hadjump = 3;
      }
      

      minx = Math.min(minx, imagelinex1);
      miny = Math.min(miny, imageliney1);
    }
    
    newimage = newimage.getSubimage(minx - this.IMAGEMARGIN, miny - this.IMAGEMARGIN, this.IMAGEMARGIN + this.maxnegx + this.maxposx + this.IMAGEMARGIN, this.IMAGEMARGIN + this.maxnegy + this.maxposy + this.IMAGEMARGIN);
    return newimage;
  }
  
  public Color colorDarker(Color currentcolor, double FACTOR) {
    return new Color(Math.max((int)(currentcolor.getRed() * FACTOR), 0), Math.max((int)(currentcolor.getGreen() * FACTOR), 0), Math.max((int)(currentcolor.getBlue() * FACTOR), 0));
  }
  







  public void saveSVG(String[] colorarray, String savepathname)
    throws Exception
  {
    SVGGraphics2D svgGenerator = drawDSTImage(colorarray);
    svgGenerator.stream(savepathname, true);
  }
  







  public void savePNG(String[] colorarray, String savepathname)
    throws Exception
  {
    BufferedImage myimage = drawPNGImage(colorarray);
    ImageIO.write(myimage, "png", new File(savepathname));
  }
  







  public void saveResizedPNG(String[] colorarray, String savepathname)
    throws Exception
  {
    int resizedx = (int)((this.IMAGEMARGIN + this.maxposx + this.maxnegx + this.IMAGEMARGIN) / 10.0D * 2.834645669291339D);
    int resizedy = (int)((this.IMAGEMARGIN + this.maxposy + this.maxnegy + this.IMAGEMARGIN) / 10.0D * 2.834645669291339D);
    BufferedImage image = drawPNGImage(colorarray);
    Image myimage = image.getScaledInstance(resizedx, resizedy, 4);
    BufferedImage resizedImage = new BufferedImage(resizedx, resizedy, 1);
    

    Graphics2D g = resizedImage.createGraphics();
    g.drawImage(myimage, 0, 0, null);
    g.dispose();
    
    ImageIO.write(resizedImage, "png", new File(savepathname));
  }
  






  public BufferedImage showResizedPNG(String[] colorarray)
    throws Exception
  {
    int resizedx = (int)((this.IMAGEMARGIN + this.maxposx + this.maxnegx + this.IMAGEMARGIN) / 10.0D * 2.834645669291339D);
    int resizedy = (int)((this.IMAGEMARGIN + this.maxposy + this.maxnegy + this.IMAGEMARGIN) / 10.0D * 2.834645669291339D);
    BufferedImage image = drawPNGImage(colorarray);
    

    Image myimage = image.getScaledInstance(resizedx, resizedy, 4);
    BufferedImage resizedImage = new BufferedImage(resizedx, resizedy, 2);
    Graphics2D g = resizedImage.createGraphics();
    g.drawImage(myimage, 0, 0, null);
    g.dispose();
    
    return resizedImage;
  }
  
  public BufferedImage showPNG(String[] colorarray)
    throws Exception
  {
    BufferedImage image = drawPNGImage(colorarray);
    return image;
  }
  




  private void gatherDSTInfo()
    throws Exception
  {
    boolean needcalcsize = false;
    File myfile = new File(this.filepath);
    FileInputStream fstream = new FileInputStream(myfile);
    byte[] header = new byte['Ȁ'];
    fstream.read(header, 0, 512);
    this.body = new byte[(int)myfile.length() - 512];
    fstream.read(this.body);
    fstream.close();
    
    String headerstring = new String(header);
    String[] headerarray = headerstring.split(String.valueOf('\r'));
    try {
      this.stitchcount = Integer.valueOf(headerarray[1].substring(3).trim()).intValue();
      this.maxposx = Integer.valueOf(headerarray[3].substring(3).trim()).intValue();
      this.maxnegx = Integer.valueOf(headerarray[4].substring(3).trim()).intValue();
      this.maxposy = Integer.valueOf(headerarray[5].substring(3).trim()).intValue();
      this.maxnegy = Integer.valueOf(headerarray[6].substring(3).trim()).intValue();
    }
    catch (Exception e) {
      needcalcsize = true;
    }
    this.stitchwidth = ((this.maxposx + this.maxnegx) / 10.0D);
    this.stitchheight = ((this.maxposy + this.maxnegy) / 10.0D);
    





    this.totalinstructions = ((this.body.length - 4) / 3);
    
    int currentcount = 0;
    int currentcolor = 0;
    Vector<Integer> stitchpercolor = new Vector();
    stitchpercolor.add(Integer.valueOf(0));
    
    int imagelinex1 = this.maxnegx;
    int imagelinex2 = this.maxnegx;
    int imageliney1 = this.maxposy;
    int imageliney2 = this.maxposy;
    int minx = this.maxnegx + this.maxposx;
    int miny = this.maxposy + this.maxnegy;
    






    for (int i = 0; i < this.totalinstructions; i++) {
      byte[] instructionset = { this.body[(i * 3)], this.body[(i * 3 + 1)], this.body[(i * 3 + 2)] };
      
      boolean[] instructionsetasbinary = fromByteArray(instructionset);
      
      int yloc = (instructionsetasbinary[0] != 0 ? 1 : 0) - (instructionsetasbinary[1] != 0 ? 1 : 0) + (instructionsetasbinary[2] != 0 ? 9 : 0) - (instructionsetasbinary[3] != 0 ? 9 : 0) + (instructionsetasbinary[8] != 0 ? 3 : 0) - (instructionsetasbinary[9] != 0 ? 3 : 0) + (instructionsetasbinary[10] != 0 ? 27 : 0) - (instructionsetasbinary[11] != 0 ? 27 : 0) + (instructionsetasbinary[18] != 0 ? 81 : 0) - (
        instructionsetasbinary[19] != 0 ? 81 : 0);
      int xloc = (instructionsetasbinary[5] != 0 ? 9 : 0) - (instructionsetasbinary[4] != 0 ? 9 : 0) - (instructionsetasbinary[6] != 0 ? 1 : 0) + (instructionsetasbinary[7] != 0 ? 1 : 0) - (instructionsetasbinary[12] != 0 ? 27 : 0) + (instructionsetasbinary[13] != 0 ? 27 : 0) - (instructionsetasbinary[14] != 0 ? 3 : 0) + (instructionsetasbinary[15] != 0 ? 3 : 0) - (instructionsetasbinary[20] != 0 ? 81 : 0) + (
        instructionsetasbinary[21] != 0 ? 81 : 0);
      
      if ((instructionsetasbinary[16] == 0) && (instructionsetasbinary[17] == 0)) {
        currentcount++;
        stitchpercolor.set(currentcolor, Integer.valueOf(currentcount));
        
        imagelinex2 = imagelinex1 + xloc;
        imageliney2 = imageliney1 - yloc;
        imagelinex1 = imagelinex2;
        imageliney1 = imageliney2;
      }
      else if ((instructionsetasbinary[16] != 0) && (instructionsetasbinary[17] == 0)) {
        if (i != 0) {
          currentcount++;
          stitchpercolor.set(currentcolor, Integer.valueOf(currentcount));
          
          imagelinex2 = imagelinex1 + xloc;
          imageliney2 = imageliney1 - yloc;
          imagelinex1 = imagelinex2;
          imageliney1 = imageliney2;
        }
      }
      else if ((instructionsetasbinary[16] != 0) && (instructionsetasbinary[17] != 0)) {
        currentcount++;
        stitchpercolor.set(currentcolor, Integer.valueOf(currentcount));
        currentcolor++;
        currentcount = 0;
        stitchpercolor.add(Integer.valueOf(0));
      }
      
      minx = Math.min(minx, imagelinex1);
      miny = Math.min(miny, imageliney1);
    }
    




    if (minx < 0) {
      this.maxposx -= minx;
      this.maxnegx += minx;
    }
    if (miny < 0) {
      this.maxposy -= miny;
      this.maxnegy += miny;
    }
    this.stitchwidth = ((this.maxposx + this.maxnegx) / 10.0D);
    this.stitchheight = ((this.maxposy + this.maxnegy) / 10.0D);
    
    if (((Integer)stitchpercolor.get(stitchpercolor.size() - 1)).intValue() == 0) {
      stitchpercolor.remove(stitchpercolor.size() - 1);
    }
    this.totalcolors = stitchpercolor.size();
    this.stitchs = stitchpercolor;
    
    if (needcalcsize) {
      this.stitchcount = 0;
      for (int i = 0; i < stitchpercolor.size(); i++) {
        this.stitchcount += ((Integer)stitchpercolor.get(i)).intValue();
      }
      
      this.maxposx = Math.abs(this.maxposx);
      this.maxnegx = Math.abs(this.maxnegx);
      this.maxposy = Math.abs(this.maxposy);
      this.maxnegy = Math.abs(this.maxnegy);
      
      this.stitchwidth = ((this.maxposx + this.maxnegx) / 10.0D);
      this.stitchheight = ((this.maxposy + this.maxnegy) / 10.0D);
    }
  }
  

  public boolean[] fromByteArray(byte[] bytes)
    throws Exception
  {
    boolean[] binaryboolean = new boolean[bytes.length * 8];
    for (int i = 0; i < bytes.length * 8; i++) {
      if ((bytes[(bytes.length - i / 8 - 1)] & 1 << i % 8) > 0) {
        binaryboolean[i] = true;
      }
    }
    
    boolean[] binaryboolean2 = new boolean[bytes.length * 8];
    for (int i = 0; i < binaryboolean.length; i++) {
      binaryboolean2[i] = binaryboolean[(binaryboolean.length - 1 - i)];
    }
    return binaryboolean2;
  }
}
