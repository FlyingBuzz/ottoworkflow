package com.ottocap.NewWorkFlow.server;

import com.extjs.gxt.ui.client.core.FastMap;

public class OTTOCodeToColorCached
{
  private FastMap<String> ottocolorlist = new FastMap();
  
  public OTTOCodeToColorCached(SQLTable mytable) {
    mytable.makeTable("SELECT `code`,`colorname` FROM `thread_ottocolors`");
    for (int i = 0; i < mytable.getTable().size(); i++) {
      this.ottocolorlist.put((String)((FastMap)mytable.getTable().get(i)).get("code"), (String)((FastMap)mytable.getTable().get(i)).get("colorname"));
    }
  }
  
  public String getName(String code)
  {
    String codefound = (String)this.ottocolorlist.get(code);
    if (codefound != null) {
      return codefound;
    }
    
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
        String codename = (String)this.ottocolorlist.get(code);
        if (codename == null) {
          throw new RuntimeException("Bad OTTO Color Code: " + code);
        }
        this.ottocolorlist.put(code, codename);
        return codename;
      }
      if ((code.length() % 2 == 0) && (code.length() != 0)) {
        String colorname = "";
        for (int i = 0; i < code.length() / 2; i++) {
          String singlecode = code.substring(i * 2, i * 2 + 2);
          singlecode = "0" + singlecode;
          String codename = (String)this.ottocolorlist.get(singlecode);
          if (codename == null) {
            throw new RuntimeException("Bad OTTO Color Code: " + singlecode + " of " + code);
          }
          if (i + 1 == code.length() / 2) {
            colorname = colorname + (String)this.ottocolorlist.get(singlecode);
          } else {
            colorname = colorname + (String)this.ottocolorlist.get(singlecode) + "/";
          }
        }
        this.ottocolorlist.put(code, colorname);
        return colorname;
      }
    }
    this.ottocolorlist.put(code, "");
    return "";
  }
  
  public String getDesc(String code)
  {
    String thecolor = getName(code);
    if (thecolor.equals("")) {
      return "";
    }
    return " - " + thecolor;
  }
}
