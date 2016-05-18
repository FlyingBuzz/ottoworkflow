package com.ottocap.NewWorkFlow.server.Utilities;

import com.ottocap.NewWorkFlow.server.JOOQSQL;
import com.ottocap.NewWorkFlow.server.JOOQ_OTTOCodeToColor;
import java.io.PrintStream;
import java.util.ArrayList;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.jooq.SelectOffsetStep;
import org.jooq.SelectSelectStep;
import org.jooq.SelectWhereStep;
import org.jooq.impl.DSL;

public class JOOQ_GenerateStyleData
{
  public JOOQ_GenerateStyleData(HttpServletResponse response) throws java.io.IOException
  {
    response.setContentType("application/x-download");
    response.setHeader("Content-disposition", "attachment; filename=\"" + new java.text.SimpleDateFormat("MM.dd.yy-hh.mm.a-").format(new java.util.Date()) + "GenerateStyleData.xls\"");
    





















    Result<Record> sqlrecords = null;
    
    ArrayList<String[]> csvarray = new ArrayList();
    csvarray.add(new String[] { "Style", "Description", "Category", "Color Code", "Color", "Color Group", "Size", "Size Group", "Page", "Qty 1", "Cost 1", "Qty 2", "Cost 2", "Qty 3", "Cost 3", "Qty 4", "Cost 4" });
    
    sqlrecords = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_domestic" })).fetch();
    if (sqlrecords != null) {
      for (int i = 0; i < sqlrecords.size(); i++) {
        String style = (String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "sku" }));
        String description = (String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "sizename" }));
        String category = (String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "category" }));
        String cost1 = (String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "price1" }));
        String cost2 = (String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "price2" }));
        String cost3 = (String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "price3" }));
        String cost4 = (String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "price4" }));
        String colorgroup = "";
        String sizegroup = "";
        String page = "";
        String qty1 = "1";
        String qty2 = "144";
        String qty3 = "576";
        String qty4 = "1440";
        String size = (String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "new_size_option" }));
        String colorcodes = (String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "colorcodes" }));
        String[] colorcodesarray = colorcodes.split(",");
        for (int j = 0; j < colorcodesarray.length; j++) {
          if (!colorcodesarray[j].trim().equals("")) {
            String colorcode = colorcodesarray[j].trim();
            JOOQ_OTTOCodeToColor findcolor = new JOOQ_OTTOCodeToColor(colorcode);
            String color = findcolor.getCode();
            
            csvarray.add(new String[] { style, description, category, colorcode, color, colorgroup, size, sizegroup, page, qty1, cost1, qty2, cost2, qty3, cost3, qty4, cost4 });
          }
        }
      }
    }
    

    sqlrecords = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_domestic_totebags" })).fetch();
    if (sqlrecords != null) {
      for (int i = 0; i < sqlrecords.size(); i++) {
        String style = (String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "sku" }));
        String description = (String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "productname" }));
        String category = (String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "category" }));
        String cost1 = (String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "price1" }));
        String cost2 = (String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "price2" }));
        String cost3 = (String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "price3" }));
        String cost4 = (String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "price4" }));
        String colorgroup = "";
        String sizegroup = "";
        String page = "";
        String qty1 = "1";
        String qty2 = "144";
        String qty3 = "576";
        String qty4 = "1440";
        String size = (String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "size" }));
        String colorcodes = (String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "colorcodes" }));
        String[] colorcodesarray = colorcodes.split(",");
        for (int j = 0; j < colorcodesarray.length; j++) {
          if (!colorcodesarray[j].trim().equals("")) {
            String colorcode = colorcodesarray[j].trim();
            JOOQ_OTTOCodeToColor findcolor = new JOOQ_OTTOCodeToColor(colorcode);
            String color = findcolor.getCode();
            
            csvarray.add(new String[] { style, description, category, colorcode, color, colorgroup, size, sizegroup, page, qty1, cost1, qty2, cost2, qty3, cost3, qty4, cost4 });
          }
        }
      }
    }
    

    sqlrecords = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_domestic_aprons" })).fetch();
    if (sqlrecords != null) {
      for (int i = 0; i < sqlrecords.size(); i++) {
        String style = (String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "sku" }));
        String description = (String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "productname" }));
        String category = (String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "category" }));
        String cost1 = (String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "price1" }));
        String cost2 = (String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "price2" }));
        String cost3 = (String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "price3" }));
        String cost4 = (String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "price4" }));
        String colorgroup = "";
        String sizegroup = "";
        String page = "";
        String qty1 = "1";
        String qty2 = "144";
        String qty3 = "576";
        String qty4 = "1440";
        String size = (String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "size" }));
        String colorcodes = (String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "colorcodes" }));
        String[] colorcodesarray = colorcodes.split(",");
        for (int j = 0; j < colorcodesarray.length; j++) {
          if (!colorcodesarray[j].trim().equals("")) {
            String colorcode = colorcodesarray[j].trim();
            JOOQ_OTTOCodeToColor findcolor = new JOOQ_OTTOCodeToColor(colorcode);
            String color = findcolor.getCode();
            
            csvarray.add(new String[] { style, description, category, colorcode, color, colorgroup, size, sizegroup, page, qty1, cost1, qty2, cost2, qty3, cost3, qty4, cost4 });
          }
        }
      }
    }
    

    sqlrecords = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_domestic_shirts" })).fetch();
    if (sqlrecords != null) {
      for (int i = 0; i < sqlrecords.size(); i++) {
        String style = (String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "sku" }));
        String description = (String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "productname" }));
        String category = (String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "category" }));
        String colorgroup = "";
        String sizegroup = "";
        String page = "";
        String qty1 = "1";
        String qty2 = "144";
        String qty3 = "576";
        String qty4 = "1440";
        String sizes = (String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "size" }));
        
        String[] sizesarray = sizes.split(",");
        for (int j = 0; j < sizesarray.length; j++) {
          if (!sizesarray[j].trim().equals("")) {
            String size = sizesarray[j].trim();
            
            Record colorrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "color" })).from(new org.jooq.TableLike[] { DSL.tableByName(new String[] { "styles_domestic_shirts_sizecolor" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "sku" }).equal(style).and(DSL.fieldByName(new String[] { "size" }).equal(size)) }).limit(1).fetchOne();
            if (colorrecord != null) {
              String colorcodes = (String)colorrecord.getValue(DSL.fieldByName(new String[] { "color" }));
              String[] colorcodesarray = colorcodes.split(",");
              for (int k = 0; k < colorcodesarray.length; k++) {
                if (!colorcodesarray[k].trim().equals("")) {
                  String colorcode = colorcodesarray[k].trim();
                  JOOQ_OTTOCodeToColor findcolor = new JOOQ_OTTOCodeToColor(colorcode);
                  String color = findcolor.getCode();
                  
                  Record pricingrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "domestic_price_shirts" })).where(new Condition[] { DSL.fieldByName(new String[] { "style" }).equal(style + "-" + colorcode + "-" + size) }).limit(1).fetchOne();
                  
                  String cost1 = "";
                  String cost2 = "";
                  String cost3 = "";
                  String cost4 = "";
                  
                  if (pricingrecord != null) {
                    cost1 = String.valueOf(pricingrecord.getValue(DSL.fieldByName(new String[] { "price1" })));
                    cost2 = String.valueOf(pricingrecord.getValue(DSL.fieldByName(new String[] { "price2" })));
                    cost3 = String.valueOf(pricingrecord.getValue(DSL.fieldByName(new String[] { "price3" })));
                    cost4 = String.valueOf(pricingrecord.getValue(DSL.fieldByName(new String[] { "price4" })));
                  } else {
                    System.out.println("bad style " + style + "-" + colorcode + "-" + size);
                  }
                  
                  csvarray.add(new String[] { style, description, category, colorcode, color, colorgroup, size, sizegroup, page, qty1, cost1, qty2, cost2, qty3, cost3, qty4, cost4 });
                }
              }
            }
          }
        }
      }
    }
    



    sqlrecords = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_domestic_sweatshirts" })).fetch();
    if (sqlrecords != null) {
      for (int i = 0; i < sqlrecords.size(); i++) {
        String style = (String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "sku" }));
        String description = (String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "productname" }));
        String category = (String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "category" }));
        String colorgroup = "";
        String sizegroup = "";
        String page = "";
        String qty1 = "1";
        String qty2 = "144";
        String qty3 = "576";
        String qty4 = "1440";
        String sizes = (String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "size" }));
        
        String[] sizesarray = sizes.split(",");
        for (int j = 0; j < sizesarray.length; j++) {
          if (!sizesarray[j].trim().equals("")) {
            String size = sizesarray[j].trim();
            
            Record colorrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "colorcodes" })).from(new org.jooq.TableLike[] { DSL.tableByName(new String[] { "styles_domestic_sweatshirts" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "sku" }).equal(style).and(DSL.fieldByName(new String[] { "new_size_option" }).equal(size)) }).limit(1).fetchOne();
            if (colorrecord != null) {
              String colorcodes = (String)colorrecord.getValue(DSL.fieldByName(new String[] { "colorcodes" }));
              String[] colorcodesarray = colorcodes.split(",");
              for (int k = 0; k < colorcodesarray.length; k++) {
                if (!colorcodesarray[k].trim().equals("")) {
                  String colorcode = colorcodesarray[k].trim();
                  JOOQ_OTTOCodeToColor findcolor = new JOOQ_OTTOCodeToColor(colorcode);
                  String color = findcolor.getCode();
                  
                  Record pricingrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "domestic_price_sweatshirts" })).where(new Condition[] { DSL.fieldByName(new String[] { "style" }).equal(style + "-" + colorcode + "-" + size) }).limit(1).fetchOne();
                  
                  String cost1 = "";
                  String cost2 = "";
                  String cost3 = "";
                  String cost4 = "";
                  
                  if (pricingrecord != null) {
                    cost1 = String.valueOf(pricingrecord.getValue(DSL.fieldByName(new String[] { "price1" })));
                    cost2 = String.valueOf(pricingrecord.getValue(DSL.fieldByName(new String[] { "price2" })));
                    cost3 = String.valueOf(pricingrecord.getValue(DSL.fieldByName(new String[] { "price3" })));
                    cost4 = String.valueOf(pricingrecord.getValue(DSL.fieldByName(new String[] { "price4" })));
                  } else {
                    System.out.println("bad style " + style + "-" + colorcode + "-" + size);
                  }
                  
                  csvarray.add(new String[] { style, description, category, colorcode, color, colorgroup, size, sizegroup, page, qty1, cost1, qty2, cost2, qty3, cost3, qty4, cost4 });
                }
              }
            }
          }
        }
      }
    }
    







    HSSFWorkbook hwb = new HSSFWorkbook();
    HSSFSheet sheet = hwb.createSheet("thedata");
    
    for (int i = 0; i < csvarray.size(); i++) {
      org.apache.poi.hssf.usermodel.HSSFRow row = sheet.createRow(i);
      for (int j = 0; j < ((String[])csvarray.get(i)).length; j++) {
        row.createCell(j).setCellValue(((String[])csvarray.get(i))[j]);
      }
    }
    
    hwb.write(response.getOutputStream());
    response.getOutputStream().close();
  }
}
