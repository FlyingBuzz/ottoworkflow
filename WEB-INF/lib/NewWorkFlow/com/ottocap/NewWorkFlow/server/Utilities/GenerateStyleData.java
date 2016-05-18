package com.ottocap.NewWorkFlow.server.Utilities;

import com.extjs.gxt.ui.client.core.FastMap;
import com.ottocap.NewWorkFlow.server.OTTOCodeToColor;
import com.ottocap.NewWorkFlow.server.SQLTable;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class GenerateStyleData
{
  public GenerateStyleData(HttpServletResponse response) throws java.io.IOException
  {
    SQLTable mytable = new SQLTable();
    
    response.setContentType("application/x-download");
    response.setHeader("Content-disposition", "attachment; filename=\"" + new SimpleDateFormat("MM.dd.yy-hh.mm.a-").format(new Date()) + "GenerateStyleData.xls\"");
    






















    ArrayList<String[]> csvarray = new ArrayList();
    csvarray.add(new String[] { "Style", "Description", "Category", "Color Code", "Color", "Color Group", "Size", "Size Group", "Page", "Qty 1", "Cost 1", "Qty 2", "Cost 2", "Qty 3", "Cost 3", "Qty 4", "Cost 4" });
    
    mytable.makeTable("SELECT * FROM `styles_domestic`");
    
    ArrayList<FastMap<Object>> thetable = mytable.getTable();
    
    for (int i = 0; i < thetable.size(); i++) {
      String style = (String)((FastMap)thetable.get(i)).get("sku");
      String description = (String)((FastMap)thetable.get(i)).get("sizename");
      String category = (String)((FastMap)thetable.get(i)).get("category");
      String cost1 = (String)((FastMap)thetable.get(i)).get("price1");
      String cost2 = (String)((FastMap)thetable.get(i)).get("price2");
      String cost3 = (String)((FastMap)thetable.get(i)).get("price3");
      String cost4 = (String)((FastMap)thetable.get(i)).get("price4");
      String colorgroup = "";
      String sizegroup = "";
      String page = "";
      String qty1 = "1";
      String qty2 = "144";
      String qty3 = "576";
      String qty4 = "1440";
      String size = (String)((FastMap)thetable.get(i)).get("new_size_option");
      
      String colorcodes = (String)((FastMap)thetable.get(i)).get("colorcodes");
      String[] colorcodesarray = colorcodes.split(",");
      for (int j = 0; j < colorcodesarray.length; j++) {
        if (!colorcodesarray[j].trim().equals("")) {
          String colorcode = colorcodesarray[j].trim();
          OTTOCodeToColor findcolor = new OTTOCodeToColor(colorcode, mytable);
          String color = findcolor.getCode();
          
          csvarray.add(new String[] { style, description, category, colorcode, color, colorgroup, size, sizegroup, page, qty1, cost1, qty2, cost2, qty3, cost3, qty4, cost4 });
        }
      }
    }
    


    mytable.makeTable("SELECT * FROM `styles_domestic_totebags`");
    thetable = mytable.getTable();
    for (int i = 0; i < thetable.size(); i++) {
      String style = (String)((FastMap)thetable.get(i)).get("sku");
      String description = (String)((FastMap)thetable.get(i)).get("productname");
      String category = (String)((FastMap)thetable.get(i)).get("category");
      String cost1 = (String)((FastMap)thetable.get(i)).get("price1");
      String cost2 = (String)((FastMap)thetable.get(i)).get("price2");
      String cost3 = (String)((FastMap)thetable.get(i)).get("price3");
      String cost4 = (String)((FastMap)thetable.get(i)).get("price4");
      String colorgroup = "";
      String sizegroup = "";
      String page = "";
      String qty1 = "1";
      String qty2 = "144";
      String qty3 = "576";
      String qty4 = "1440";
      String size = (String)((FastMap)thetable.get(i)).get("size");
      
      String colorcodes = (String)((FastMap)thetable.get(i)).get("colorcodes");
      String[] colorcodesarray = colorcodes.split(",");
      for (int j = 0; j < colorcodesarray.length; j++) {
        if (!colorcodesarray[j].trim().equals("")) {
          String colorcode = colorcodesarray[j].trim();
          OTTOCodeToColor findcolor = new OTTOCodeToColor(colorcode, mytable);
          String color = findcolor.getCode();
          
          csvarray.add(new String[] { style, description, category, colorcode, color, colorgroup, size, sizegroup, page, qty1, cost1, qty2, cost2, qty3, cost3, qty4, cost4 });
        }
      }
    }
    


    mytable.makeTable("SELECT * FROM `styles_domestic_shirts`");
    thetable = mytable.getTable();
    for (int i = 0; i < thetable.size(); i++) {
      String style = (String)((FastMap)thetable.get(i)).get("sku");
      String description = (String)((FastMap)thetable.get(i)).get("productname");
      String category = (String)((FastMap)thetable.get(i)).get("category");
      String colorgroup = "";
      String sizegroup = "";
      String page = "";
      String qty1 = "1";
      String qty2 = "144";
      String qty3 = "576";
      String qty4 = "1440";
      
      String sizes = (String)((FastMap)thetable.get(i)).get("size");
      String[] sizesarray = sizes.split(",");
      for (int j = 0; j < sizesarray.length; j++) {
        if (!sizesarray[j].trim().equals("")) {
          String size = sizesarray[j].trim();
          
          mytable.makeTable("SELECT color FROM `styles_domestic_shirts_sizecolor` WHERE sku='" + style + "' AND size='" + size + "'");
          String colorcodes = (String)mytable.getValue();
          String[] colorcodesarray = colorcodes.split(",");
          for (int k = 0; k < colorcodesarray.length; k++) {
            if (!colorcodesarray[k].trim().equals("")) {
              String colorcode = colorcodesarray[k].trim();
              OTTOCodeToColor findcolor = new OTTOCodeToColor(colorcode, mytable);
              String color = findcolor.getCode();
              
              mytable.makeTable("SELECT * FROM `domestic_price_shirts` WHERE style='" + style + "-" + colorcode + "-" + size + "'");
              String cost1 = "";
              String cost2 = "";
              String cost3 = "";
              String cost4 = "";
              try
              {
                FastMap<Object> therow = mytable.getRow();
                cost1 = String.valueOf(therow.get("price1"));
                cost2 = String.valueOf(therow.get("price2"));
                cost3 = String.valueOf(therow.get("price3"));
                cost4 = String.valueOf(therow.get("price4"));
              } catch (Exception e) {
                System.out.println("bad style " + style + "-" + colorcode + "-" + size);
              }
              csvarray.add(new String[] { style, description, category, colorcode, color, colorgroup, size, sizegroup, page, qty1, cost1, qty2, cost2, qty3, cost3, qty4, cost4 });
            }
          }
        }
      }
    }
    






    mytable.closeSQL();
    




    HSSFWorkbook hwb = new HSSFWorkbook();
    HSSFSheet sheet = hwb.createSheet("thedata");
    
    for (int i = 0; i < csvarray.size(); i++) {
      HSSFRow row = sheet.createRow(i);
      for (int j = 0; j < ((String[])csvarray.get(i)).length; j++) {
        row.createCell(j).setCellValue(((String[])csvarray.get(i))[j]);
      }
    }
    
    hwb.write(response.getOutputStream());
    response.getOutputStream().close();
  }
}
