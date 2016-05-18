package com.ottocap.NewWorkFlow.server;

public class ConvertColor
{
  public static String convertTo(String colorcode, String threadfrom, String threadto, SQLTable sqltable) throws Exception
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
      if (!sqltable.makeTable("SELECT " + cellname + " FROM `" + tablename + "` WHERE `code` ='" + colorcode + "' LIMIT 1").booleanValue()) {
        throw new Exception("Error Cant Convert Thread From " + threadfrom + " To " + threadto + " Of Color Code " + colorcode);
      }
      String thecolor = (String)sqltable.getValue();
      try {
        if (((thecolor.charAt(0) == 'B') || (thecolor.charAt(0) == 'b')) && (Character.isDigit(thecolor.charAt(1)))) {
          thecolor = thecolor.substring(1);
        } else if ((thecolor.charAt(0) == '*') && (Character.isDigit(thecolor.charAt(1)))) {
          thecolor = thecolor.substring(1);
        }
      }
      catch (Exception localException) {}
      
      return thecolor; }
    if (threadto.equals("Keep Colors"))
    {
      if (threadfrom.equals("Pantone"))
        return "PMS" + colorcode;
      if (threadfrom.equals("Madeira Rayon"))
        return "Madeira Rayon " + colorcode;
      if (threadfrom.equals("Madeira Polyneon"))
        return "Madeira Polyneon " + colorcode;
      if (threadfrom.equals("Fibres Rayon"))
        return "Fibres Rayon " + colorcode;
      if (threadfrom.equals("Robinson Rayon"))
        return "Robinson Rayon " + colorcode;
      if (threadfrom.equals("RA Super Brite Polyester")) {
        return "RA Super Brite Polyester " + colorcode;
      }
      return colorcode;
    }
    

    return colorcode;
  }
}
