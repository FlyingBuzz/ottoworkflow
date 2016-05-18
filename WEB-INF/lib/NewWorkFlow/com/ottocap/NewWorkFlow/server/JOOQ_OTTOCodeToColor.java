package com.ottocap.NewWorkFlow.server;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.impl.DSL;

public class JOOQ_OTTOCodeToColor
{
  private String colorname = "";
  
  public JOOQ_OTTOCodeToColor(String code) {
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
        Record therecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "thread_ottocolors" })).where(new org.jooq.Condition[] { DSL.fieldByName(new String[] { "code" }).equal(code) }).limit(1).fetchOne();
        if (therecord == null) {
          try {
            throw new Exception("Bad OTTO Color Code: " + code);
          } catch (Exception e) {
            e.printStackTrace();
          }
        } else {
          this.colorname = ((String)therecord.getValue(DSL.fieldByName(new String[] { "colorname" })));
        }
      } else if ((code.length() % 2 == 0) && (code.length() != 0)) {
        for (int i = 0; i < code.length() / 2; i++) {
          String singlecode = code.substring(i * 2, i * 2 + 2);
          singlecode = "0" + singlecode;
          
          Record therecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "thread_ottocolors" })).where(new org.jooq.Condition[] { DSL.fieldByName(new String[] { "code" }).equal(singlecode) }).limit(1).fetchOne();
          if (therecord == null) {
            try {
              throw new Exception("Bad OTTO Color Code: " + code);
            } catch (Exception e) {
              e.printStackTrace();
            }
            
          } else if (i + 1 == code.length() / 2) {
            this.colorname = ((String)therecord.getValue(DSL.fieldByName(new String[] { "colorname" })));
          } else {
            this.colorname = ((String)therecord.getValue(DSL.fieldByName(new String[] { "colorname" })) + "/");
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
  
  public static String getStaticCodeDisc(String code)
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
        Record sqlrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "thread_ottocolors" })).where(new org.jooq.Condition[] { DSL.fieldByName(new String[] { "code" }).equal(code) }).limit(1).fetchOne();
        if (sqlrecord != null) {
          thecolor = (String)sqlrecord.getValue(DSL.fieldByName(new String[] { "colorname" }));
        } else {
          throw new RuntimeException("Bad OTTO Color Code: " + code);
        }
      } else if ((code.length() % 2 == 0) && (code.length() != 0)) {
        for (int i = 0; i < code.length() / 2; i++) {
          String singlecode = code.substring(i * 2, i * 2 + 2);
          singlecode = "0" + singlecode;
          Record sqlrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "thread_ottocolors" })).where(new org.jooq.Condition[] { DSL.fieldByName(new String[] { "code" }).equal(singlecode) }).limit(1).fetchOne();
          if (sqlrecord != null) {
            if (i + 1 == code.length() / 2) {
              thecolor = thecolor + (String)sqlrecord.getValue(DSL.fieldByName(new String[] { "colorname" }));
            } else {
              thecolor = thecolor + (String)sqlrecord.getValue(DSL.fieldByName(new String[] { "colorname" })) + "/";
            }
          } else {
            throw new RuntimeException("Bad OTTO Color Code: " + singlecode + " of " + code);
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
