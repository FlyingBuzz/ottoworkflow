package com.ottocap.NewWorkFlow.server;

import java.util.TreeMap;
import org.jooq.DSLContext;
import org.jooq.Record2;
import org.jooq.Result;
import org.jooq.SelectJoinStep;
import org.jooq.impl.DSL;

public class JOOQ_OTTOCodeToColorCached
{
  private TreeMap<String, String> ottocolorlist = new TreeMap();
  
  public JOOQ_OTTOCodeToColorCached() {
    Result<Record2<Object, Object>> records = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "code" }), DSL.fieldByName(new String[] { "colorname" })).from("thread_ottocolors").fetch();
    for (int i = 0; i < records.size(); i++) {
      this.ottocolorlist.put((String)((Record2)records.get(i)).getValue(DSL.fieldByName(new String[] { "code" })), (String)((Record2)records.get(i)).getValue(DSL.fieldByName(new String[] { "colorname" })));
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
