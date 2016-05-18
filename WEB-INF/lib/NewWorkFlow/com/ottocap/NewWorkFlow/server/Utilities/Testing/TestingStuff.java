package com.ottocap.NewWorkFlow.server.Utilities.Testing;

import com.extjs.gxt.ui.client.core.FastMap;
import com.ottocap.NewWorkFlow.server.JOOQ_ConvertColor;
import com.ottocap.NewWorkFlow.server.LogoGenerator.DSTImage;
import com.ottocap.NewWorkFlow.server.SQLTable;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import org.apache.commons.io.FileUtils;




public class TestingStuff
{
  public static void main(String[] args) {}
  
  public TestingStuff()
  {
    doTests();
  }
  






  private static void doTests() {}
  





  public static void FixTestConvert()
  {
    SQLTable mytable = new SQLTable();
    
    mytable.makeTable("SELECT * FROM `test_convert`");
    
    ArrayList<FastMap<Object>> thetablelist = mytable.getTable();
    
    for (int i = 0; i < thetablelist.size(); i++) {
      String currentstyle = (String)((FastMap)thetablelist.get(i)).get("product sku");
      String thevalue1 = (String)((FastMap)thetablelist.get(i)).get("specification value 1");
      String thevalue2 = (String)((FastMap)thetablelist.get(i)).get("specification value 2");
      
      System.out.println(currentstyle);
      
      String usevalue = thevalue2.trim();
      if (usevalue.equals("")) {
        usevalue = thevalue1.trim();
      }
      
      String[] thecolornames = usevalue.split(",");
      
      String thecolorcodes = "";
      
      for (int j = 0; j < thecolornames.length; j++) {
        ArrayList<String> colorlist = new ArrayList();
        if (!thecolornames[j].trim().equals("")) {
          String[] thecolornamesbyitself = thecolornames[j].split("/");
          for (int k = 0; k < thecolornamesbyitself.length; k++) {
            if (!thecolornamesbyitself[k].trim().equals("")) {
              String thecolor = thecolornamesbyitself[k].trim();
              colorlist.add(thecolor);
            }
          }
        }
        
        if (colorlist.size() == 1) {
          if (!((String)colorlist.get(0)).startsWith("CP"))
          {
            System.out.println((String)colorlist.get(0));
            mytable.makeTable("SELECT `code` FROM `thread_ottocolors` WHERE `colorname` = '" + (String)colorlist.get(0) + "' LIMIT 1");
            thecolorcodes = thecolorcodes + (String)mytable.getValue() + ", ";
          }
        }
        else if (colorlist.size() == 2) {
          System.out.println((String)colorlist.get(0));
          mytable.makeTable("SELECT `code` FROM `thread_ottocolors` WHERE `colorname` = '" + (String)colorlist.get(0) + "' LIMIT 1");
          String color1 = (String)mytable.getValue();
          System.out.println((String)colorlist.get(1));
          mytable.makeTable("SELECT `code` FROM `thread_ottocolors` WHERE `colorname` = '" + (String)colorlist.get(1) + "' LIMIT 1");
          String color2 = (String)mytable.getValue();
          
          thecolorcodes = thecolorcodes + color1.substring(1) + color2.substring(1) + ", ";
        } else if (colorlist.size() == 3) {
          System.out.println((String)colorlist.get(0));
          mytable.makeTable("SELECT `code` FROM `thread_ottocolors` WHERE `colorname` = '" + (String)colorlist.get(0) + "' LIMIT 1");
          String color1 = (String)mytable.getValue();
          System.out.println((String)colorlist.get(1));
          mytable.makeTable("SELECT `code` FROM `thread_ottocolors` WHERE `colorname` = '" + (String)colorlist.get(1) + "' LIMIT 1");
          String color2 = (String)mytable.getValue();
          System.out.println((String)colorlist.get(2));
          mytable.makeTable("SELECT `code` FROM `thread_ottocolors` WHERE `colorname` = '" + (String)colorlist.get(2) + "' LIMIT 1");
          String color3 = (String)mytable.getValue();
          
          thecolorcodes = thecolorcodes + color1.substring(1) + color2.substring(1) + color3.substring(1) + ", ";
        }
      }
      

      if (thecolorcodes.length() > 2) {
        thecolorcodes = thecolorcodes.substring(0, thecolorcodes.length() - 2);
      }
      
      FastMap<Object> mykeys = new FastMap();
      FastMap<Object> myfilter = new FastMap();
      mykeys.put("product sku", currentstyle);
      mykeys.put("colorcode", thecolorcodes);
      
      myfilter.put("product sku", currentstyle);
      mytable.insertUpdateRow("test_convert", mykeys, myfilter);
    }
    

    mytable.closeSQL();
  }
  
  public static void ParseForColors()
  {
    try {
      String thestring = FileUtils.readFileToString(new File("C:\\TECH\\test\\pantonelist.txt"));
      String[] thelist = thestring.split("<div style=\"height: 60px; color: black; background-color: rgb\\(");
      
      String codelist = "";
      String valuelist = "";
      
      for (int i = 1; i < thelist.length; i++) {
        String currentstring = thelist[i].substring(0, thelist[i].indexOf("<br />"));
        
        String[] currentstringlist = currentstring.split("\\);");
        
        String[] rgbcol = currentstringlist[0].split(",");
        

        int red = Integer.valueOf(rgbcol[0].trim()).intValue();
        int green = Integer.valueOf(rgbcol[1].trim()).intValue();
        int blue = Integer.valueOf(rgbcol[2].trim()).intValue();
        
        String code = currentstringlist[1].substring(6).trim();
        String colorvalue = String.format("#%02x%02x%02x", new Object[] { Integer.valueOf(red), Integer.valueOf(green), Integer.valueOf(blue) });
        

        codelist = codelist + code + "\n";
        valuelist = valuelist + colorvalue + "\n";
      }
      
      FileUtils.writeStringToFile(new File("C:\\TECH\\test\\codelist.txt"), codelist);
      FileUtils.writeStringToFile(new File("C:\\TECH\\test\\valuelist.txt"), valuelist);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
  public static void MatchThreadColors() {
    SQLTable mytable = new SQLTable();
    
    mytable.makeTable("SELECT * FROM `thread_pantonenew`");
    ArrayList<FastMap<Object>> currenttable = mytable.getTable();
    
    mytable.makeTable("SELECT * FROM `thread_fibresr`");
    ArrayList<FastMap<Object>> thread_fibresr_table = mytable.getTable();
    
    mytable.makeTable("SELECT * FROM `thread_madeirap`");
    ArrayList<FastMap<Object>> thread_madeirap_table = mytable.getTable();
    
    mytable.makeTable("SELECT * FROM `thread_madeirar`");
    ArrayList<FastMap<Object>> thread_madeirar_table = mytable.getTable();
    
    mytable.makeTable("SELECT * FROM `thread_robinsonr`");
    ArrayList<FastMap<Object>> thread_robinsonr_table = mytable.getTable();
    
    mytable.makeTable("SELECT * FROM `thread_rasuperbritepolyester`");
    ArrayList<FastMap<Object>> thread_rasuperbritepolyester_table = mytable.getTable();
    
    for (int i = 0; i < currenttable.size(); i++) {
      String thecode = (String)((FastMap)currenttable.get(i)).get("code");
      String colorvalue = (String)((FastMap)currenttable.get(i)).get("colorvalue");
      String madeirap = (String)((FastMap)currenttable.get(i)).get("madeirap");
      String madeirar = (String)((FastMap)currenttable.get(i)).get("madeirar");
      String fibresr = (String)((FastMap)currenttable.get(i)).get("fibresr");
      String robinsonr = (String)((FastMap)currenttable.get(i)).get("robinsonr");
      String rasuperbritepolyester = (String)((FastMap)currenttable.get(i)).get("rasuperbritepolyester");
      
      Color myColor = Color.decode(colorvalue);
      
      if (madeirap.equals(""))
      {
        int smallestrgbdistance = Integer.MAX_VALUE;
        String smallestrgbdistancecode = "";
        
        for (int j = 0; j < thread_madeirap_table.size(); j++) {
          String threadcolorcode = (String)((FastMap)thread_madeirap_table.get(j)).get("code");
          String threadcolorvalue = (String)((FastMap)thread_madeirap_table.get(j)).get("colorvalue");
          Color color = Color.decode(threadcolorvalue);
          
          int rgbDistance = Math.abs(myColor.getRed() - color.getRed() + Math.abs(myColor.getGreen() - color.getGreen()) + Math.abs(myColor.getBlue() - color.getBlue()));
          
          if (rgbDistance < smallestrgbdistance) {
            smallestrgbdistance = rgbDistance;
            smallestrgbdistancecode = threadcolorcode;
          }
        }
        
        madeirap = "*" + smallestrgbdistancecode;
      }
      

      if (madeirar.equals(""))
      {
        int smallestrgbdistance = Integer.MAX_VALUE;
        String smallestrgbdistancecode = "";
        
        for (int j = 0; j < thread_madeirar_table.size(); j++) {
          String threadcolorcode = (String)((FastMap)thread_madeirar_table.get(j)).get("code");
          String threadcolorvalue = (String)((FastMap)thread_madeirar_table.get(j)).get("colorvalue");
          Color color = Color.decode(threadcolorvalue);
          
          int rgbDistance = Math.abs(myColor.getRed() - color.getRed() + Math.abs(myColor.getGreen() - color.getGreen()) + Math.abs(myColor.getBlue() - color.getBlue()));
          
          if (rgbDistance < smallestrgbdistance) {
            smallestrgbdistance = rgbDistance;
            smallestrgbdistancecode = threadcolorcode;
          }
        }
        
        madeirar = "*" + smallestrgbdistancecode;
      }
      

      if (fibresr.equals(""))
      {
        int smallestrgbdistance = Integer.MAX_VALUE;
        String smallestrgbdistancecode = "";
        
        for (int j = 0; j < thread_fibresr_table.size(); j++) {
          String threadcolorcode = (String)((FastMap)thread_fibresr_table.get(j)).get("code");
          String threadcolorvalue = (String)((FastMap)thread_fibresr_table.get(j)).get("colorvalue");
          Color color = Color.decode(threadcolorvalue);
          
          int rgbDistance = Math.abs(myColor.getRed() - color.getRed() + Math.abs(myColor.getGreen() - color.getGreen()) + Math.abs(myColor.getBlue() - color.getBlue()));
          
          if (rgbDistance < smallestrgbdistance) {
            smallestrgbdistance = rgbDistance;
            smallestrgbdistancecode = threadcolorcode;
          }
        }
        
        fibresr = "*" + smallestrgbdistancecode;
      }
      

      if (robinsonr.equals(""))
      {
        int smallestrgbdistance = Integer.MAX_VALUE;
        String smallestrgbdistancecode = "";
        
        for (int j = 0; j < thread_robinsonr_table.size(); j++) {
          String threadcolorcode = (String)((FastMap)thread_robinsonr_table.get(j)).get("code");
          String threadcolorvalue = (String)((FastMap)thread_robinsonr_table.get(j)).get("colorvalue");
          Color color = Color.decode(threadcolorvalue);
          
          int rgbDistance = Math.abs(myColor.getRed() - color.getRed() + Math.abs(myColor.getGreen() - color.getGreen()) + Math.abs(myColor.getBlue() - color.getBlue()));
          
          if (rgbDistance < smallestrgbdistance) {
            smallestrgbdistance = rgbDistance;
            smallestrgbdistancecode = threadcolorcode;
          }
        }
        
        robinsonr = "*" + smallestrgbdistancecode;
      }
      

      if (rasuperbritepolyester.equals(""))
      {
        int smallestrgbdistance = Integer.MAX_VALUE;
        String smallestrgbdistancecode = "";
        
        for (int j = 0; j < thread_rasuperbritepolyester_table.size(); j++) {
          String threadcolorcode = (String)((FastMap)thread_rasuperbritepolyester_table.get(j)).get("code");
          String threadcolorvalue = (String)((FastMap)thread_rasuperbritepolyester_table.get(j)).get("colorvalue");
          Color color = Color.decode(threadcolorvalue);
          
          int rgbDistance = Math.abs(myColor.getRed() - color.getRed() + Math.abs(myColor.getGreen() - color.getGreen()) + Math.abs(myColor.getBlue() - color.getBlue()));
          
          if (rgbDistance < smallestrgbdistance) {
            smallestrgbdistance = rgbDistance;
            smallestrgbdistancecode = threadcolorcode;
          }
        }
        
        rasuperbritepolyester = "*" + smallestrgbdistancecode;
      }
      

      FastMap<Object> mykeys = new FastMap();
      FastMap<Object> myfilter = new FastMap();
      mykeys.put("code", thecode);
      mykeys.put("madeirap", madeirap);
      mykeys.put("madeirar", madeirar);
      mykeys.put("fibresr", fibresr);
      mykeys.put("robinsonr", robinsonr);
      mykeys.put("rasuperbritepolyester", rasuperbritepolyester);
      
      myfilter.put("code", thecode);
      mytable.insertUpdateRow("thread_pantonenew", mykeys, myfilter);
    }
    

    mytable.closeSQL();
  }
  
  public static void GetColorNames2() {
    SQLTable mytable = new SQLTable();
    
    mytable.makeTable("SELECT * FROM `list_wikicolornames`");
    ArrayList<FastMap<Object>> currenttable = mytable.getTable();
    

    for (int i = 0; i < currenttable.size(); i++) {
      String thecode = (String)((FastMap)currenttable.get(i)).get("colorcode");
      String colorname = (String)((FastMap)currenttable.get(i)).get("colorname");
      
      if (mytable.makeTable("SELECT * FROM `thread_pantone` WHERE code='" + thecode + " C' LIMIT 1").booleanValue()) {
        FastMap<Object> mykeys = new FastMap();
        FastMap<Object> myfilter = new FastMap();
        mykeys.put("code", thecode + " C");
        mykeys.put("colorname", colorname.trim());
        
        myfilter.put("code", thecode + " C");
        mytable.insertUpdateRow("thread_pantone", mykeys, myfilter);
      }
    }
    



    mytable.closeSQL();
  }
  
  public static void GetColorNames() {
    SQLTable mytable = new SQLTable();
    
    mytable.makeTable("SELECT * FROM `thread_pantone`");
    ArrayList<FastMap<Object>> currenttable = mytable.getTable();
    
    mytable.makeTable("SELECT * FROM `list_wikicolornames`");
    ArrayList<FastMap<Object>> colornamestable = mytable.getTable();
    
    for (int i = 0; i < currenttable.size(); i++) {
      String thecode = (String)((FastMap)currenttable.get(i)).get("code");
      String colorvalue = (String)((FastMap)currenttable.get(i)).get("colorvalue");
      
      Color myColor = Color.decode(colorvalue);
      
      double smallestrgbdistance = Double.MAX_VALUE;
      String smallestrgbdistancename = "";
      
      for (int j = 0; j < colornamestable.size(); j++) {
        String wikicolorname = (String)((FastMap)colornamestable.get(j)).get("colorname");
        String wikicolorvalue = (String)((FastMap)colornamestable.get(j)).get("colorvalue");
        Color color = Color.decode(wikicolorvalue);
        
        double rgbDistance = Math.pow((myColor.getRed() - color.getRed()) * 0.3D, 2.0D) + Math.pow((myColor.getGreen() - color.getGreen()) * 0.59D, 2.0D) + Math.pow((myColor.getBlue() - color.getBlue()) * 0.11D, 2.0D);
        
        if (rgbDistance < smallestrgbdistance) {
          smallestrgbdistance = rgbDistance;
          smallestrgbdistancename = wikicolorname;
        }
      }
      
      FastMap<Object> mykeys = new FastMap();
      FastMap<Object> myfilter = new FastMap();
      mykeys.put("code", thecode);
      mykeys.put("colorname", smallestrgbdistancename);
      
      myfilter.put("code", thecode);
      mytable.insertUpdateRow("thread_pantone", mykeys, myfilter);
    }
    

    mytable.closeSQL();
  }
  
  public static void GetOtherThreads() {
    SQLTable mytable = new SQLTable();
    
    mytable.makeTable("SELECT * FROM `thread_pantonenew`");
    ArrayList<FastMap<Object>> currenttable = mytable.getTable();
    
    for (int i = 0; i < currenttable.size(); i++) {
      String thecode = (String)((FastMap)currenttable.get(i)).get("code");
      
      String tosearch = thecode.replace(" C", "");
      
      if (mytable.makeTable("SELECT * FROM `thread_pantone` WHERE `code` = '" + tosearch + "' limit 1").booleanValue())
      {
        FastMap<Object> therow = mytable.getRow();
        String madeirap = (String)therow.get("madeirap");
        String madeirar = (String)therow.get("madeirar");
        String fibresr = (String)therow.get("fibresr");
        String robinsonr = (String)therow.get("robinsonr");
        String rasuperbritepolyester = (String)therow.get("rasuperbritepolyester");
        
        FastMap<Object> mykeys = new FastMap();
        FastMap<Object> myfilter = new FastMap();
        mykeys.put("code", thecode);
        mykeys.put("madeirap", madeirap);
        mykeys.put("madeirar", madeirar);
        mykeys.put("fibresr", fibresr);
        mykeys.put("robinsonr", robinsonr);
        mykeys.put("rasuperbritepolyester", rasuperbritepolyester);
        
        myfilter.put("code", thecode);
        mytable.insertUpdateRow("thread_pantonenew", mykeys, myfilter);
      }
    }
    


    mytable.closeSQL();
  }
  
  public static void CodeRGBHTML() {
    SQLTable mytable = new SQLTable();
    
    mytable.makeTable("SELECT * FROM `thread_pantonenew`");
    ArrayList<FastMap<Object>> currenttable = mytable.getTable();
    
    for (int i = 0; i < currenttable.size(); i++) {
      String thecode = (String)((FastMap)currenttable.get(i)).get("code");
      int red = Integer.valueOf((String)((FastMap)currenttable.get(i)).get("red")).intValue();
      int green = Integer.valueOf((String)((FastMap)currenttable.get(i)).get("green")).intValue();
      int blue = Integer.valueOf((String)((FastMap)currenttable.get(i)).get("blue")).intValue();
      
      String colorvalue = String.format("#%02x%02x%02x", new Object[] { Integer.valueOf(red), Integer.valueOf(green), Integer.valueOf(blue) });
      
      FastMap<Object> mykeys = new FastMap();
      FastMap<Object> myfilter = new FastMap();
      mykeys.put("code", thecode);
      mykeys.put("colorvalue", colorvalue);
      
      myfilter.put("code", thecode);
      mytable.insertUpdateRow("thread_pantonenew", mykeys, myfilter);
    }
    

    mytable.closeSQL();
  }
  
  public static void Test_DSTFile() {
    try {
      DSTImage theimage = new DSTImage("C:\\TECH\\convertest\\test.DST");
      String[] colorarray = new String[10];
      colorarray[0] = "#FF0000";
      colorarray[1] = "#FFA500";
      colorarray[2] = "#FFFF00";
      colorarray[3] = "#000080";
      colorarray[4] = "#0000FF";
      colorarray[5] = "#4B0082";
      colorarray[6] = "#FF0000";
      colorarray[7] = "#FFA500";
      colorarray[8] = "#FFFF00";
      colorarray[9] = "#000080";
      theimage.savePNG(colorarray, "C:\\TECH\\convertest\\test.png");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  

  public static void Test_JOOQ_ConvertColor()
  {
    System.out.println(JOOQ_ConvertColor.convertTo("1000", "Madeira Rayon", "Madeira Rayon"));
    System.out.println(JOOQ_ConvertColor.convertTo("1001", "Madeira Rayon", "Madeira Polyneon"));
    System.out.println(JOOQ_ConvertColor.convertTo("1001", "Match Cap Color", "Madeira Rayon"));
  }
}
