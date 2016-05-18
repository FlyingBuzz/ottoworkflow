package com.ottocap.NewWorkFlow.server;

public class OTTOCodeToColor
{
  private String colorname = "";
  
  public OTTOCodeToColor(String code, SQLTable mytable) {
    boolean haveletters = false;
    try {
      Integer.valueOf(code);
    } catch (Exception e) {
      haveletters = true;
    }
    
    if ((haveletters) && (code.endsWith("SC"))) {
      code = code.substring(0, code.length() - 2);
      try {
        Integer.valueOf(code);
        haveletters = false;
      } catch (Exception e) {
        haveletters = true;
      }
    }
    
    if ((code != null) && (!code.toLowerCase().startsWith("cp")) && (!code.toLowerCase().startsWith("dp")) && (!code.equals("")) && (!haveletters))
    {
      if (code.length() == 3) {
        if (!mytable.makeTable("SELECT * FROM `thread_ottocolors` WHERE `code` = '" + code + "' LIMIT 1").booleanValue()) {
          throw new RuntimeException("Bad OTTO Color Code: " + code);
        }
        this.colorname = ((String)mytable.getCell("colorname"));
      } else if ((code.length() % 2 == 0) && (code.length() != 0)) {
        for (int i = 0; i < code.length() / 2; i++) {
          String singlecode = code.substring(i * 2, i * 2 + 2);
          singlecode = "0" + singlecode;
          if (!mytable.makeTable("SELECT * FROM `thread_ottocolors` WHERE `code` = '" + singlecode + "' LIMIT 1").booleanValue()) {
            throw new RuntimeException("Bad OTTO Color Code: " + singlecode + " of " + code);
          }
          if (i + 1 == code.length() / 2) {
            this.colorname += (String)mytable.getCell("colorname");
          } else {
            this.colorname = (this.colorname + (String)mytable.getCell("colorname") + "/");
          }
        }
      }
    }
  }
  
  public String getCode()
  {
    return this.colorname;
  }
  
  public String getCodeDisc() {
    if (this.colorname.equals("")) {
      return "";
    }
    return " - " + this.colorname;
  }
  
  public static String getStaticCodeDisc(String code, SQLTable mytable)
  {
    boolean haveletters = false;
    String thecolor = "";
    try {
      Integer.valueOf(code);
    } catch (Exception e) {
      haveletters = true;
    }
    
    if ((haveletters) && (code.endsWith("SC"))) {
      code = code.substring(0, code.length() - 2);
      try {
        Integer.valueOf(code);
        haveletters = false;
      } catch (Exception e) {
        haveletters = true;
      }
    }
    
    if ((!code.toLowerCase().startsWith("cp")) && (!code.toLowerCase().startsWith("dp")) && (!code.equals("N/A")) && (!haveletters))
    {
      if (code.length() == 3) {
        if (!mytable.makeTable("SELECT * FROM `thread_ottocolors` WHERE `code` = '" + code + "' LIMIT 1").booleanValue()) {
          throw new RuntimeException("Bad OTTO Color Code: " + code);
        }
        thecolor = (String)mytable.getCell("colorname");
      } else if ((code.length() % 2 == 0) && (code.length() != 0)) {
        for (int i = 0; i < code.length() / 2; i++) {
          String singlecode = code.substring(i * 2, i * 2 + 2);
          singlecode = "0" + singlecode;
          if (!mytable.makeTable("SELECT * FROM `thread_ottocolors` WHERE `code` = '" + singlecode + "' LIMIT 1").booleanValue()) {
            throw new RuntimeException("Bad OTTO Color Code: " + singlecode + " of " + code);
          }
          if (i + 1 == code.length() / 2) {
            thecolor = thecolor + (String)mytable.getCell("colorname");
          } else {
            thecolor = thecolor + (String)mytable.getCell("colorname") + "/";
          }
        }
      }
    }
    
    if (thecolor.equals("")) {
      return "";
    }
    return " - " + thecolor;
  }
}
