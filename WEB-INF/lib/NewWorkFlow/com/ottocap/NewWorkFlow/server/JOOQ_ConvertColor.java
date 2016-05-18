package com.ottocap.NewWorkFlow.server;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.impl.DSL;

public class JOOQ_ConvertColor
{
  public static String convertTo(String colorcode, String threadfrom, String threadto)
  {
    if ((threadto == null) || (threadto.equals("")) || (threadfrom.equals(threadto)))
      return colorcode;
    if ((threadfrom.equals("Match Cap Color")) || (threadfrom.equals("Match Product Color"))) {
      return "Match OTTO " + colorcode;
    }
    String tablename = "";
    String cellname = "";
    if (threadto.equals("Madeira Rayon")) {
      cellname = "madeirar";
    } else if (threadto.equals("Madeira Polyneon")) {
      cellname = "madeirap";
    } else if (threadto.equals("Fibres Rayon")) {
      cellname = "fibresr";
    } else if (threadto.equals("Robinson Rayon")) {
      cellname = "robinsonr";
    } else if (threadto.equals("RA Super Brite Polyester")) {
      cellname = "rasuperbritepolyester";
    }
    if (threadfrom.equals("Pantone")) {
      tablename = "thread_pantone";
    } else if (threadfrom.equals("Madeira Rayon")) {
      tablename = "thread_madeirar";
    } else if (threadfrom.equals("Madeira Polyneon")) {
      tablename = "thread_madeirap";
    } else if (threadfrom.equals("Fibres Rayon")) {
      tablename = "thread_fibresr";
    } else if (threadfrom.equals("Robinson Rayon")) {
      tablename = "thread_robinsonr";
    } else if (threadfrom.equals("RA Super Brite Polyester")) {
      tablename = "thread_rasuperbritepolyester";
    }
    
    if ((!tablename.equals("")) && (!cellname.equals("")) && (!colorcode.equals(""))) {
      Record sqlrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { cellname })).from(new org.jooq.TableLike[] { DSL.tableByName(new String[] { tablename }) }).where(new org.jooq.Condition[] { DSL.fieldByName(new String[] { "code" }).equal(colorcode) }).limit(1).fetchOne();
      if (sqlrecord == null)
        try {
          throw new RuntimeException("Error Cant Convert Thread From " + threadfrom + " To " + threadto + " Of Color Code " + colorcode);
        } catch (Exception e) {
          e.printStackTrace();
          
          return "";
        }
      String thecolor = (String)sqlrecord.getValue(DSL.fieldByName(new String[] { cellname }));
      thecolor.toLowerCase().replace("b", "");
      try {
        thecolor = String.valueOf(Integer.valueOf(thecolor.replace("B", "").replace("b", "")));
      }
      catch (Exception localException1) {}
      
      return thecolor;
    }
    
    return colorcode;
  }
}
