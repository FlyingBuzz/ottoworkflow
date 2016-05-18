package com.ottocap.NewWorkFlow.server.RPCService;

import com.extjs.gxt.ui.client.core.FastMap;
import com.ottocap.NewWorkFlow.server.SQLTable;
import javax.servlet.http.HttpSession;

public class SaveOrderEmployee
{
  private HttpSession httpsession;
  
  public SaveOrderEmployee(int hiddenkey, String employeeid, HttpSession httpsession) throws Exception
  {
    this.httpsession = httpsession;
    checkSession();
    
    FastMap<Object> myfield = new FastMap();
    FastMap<Object> myfieldfilter = new FastMap();
    
    myfieldfilter.put("hiddenkey", Integer.valueOf(hiddenkey));
    myfield.put("hiddenkey", Integer.valueOf(hiddenkey));
    myfield.put("employeeid", employeeid);
    
    SQLTable sqltable = new SQLTable();
    sqltable.insertUpdateRow("ordermain", myfield, myfieldfilter);
    sqltable.closeSQL();
  }
  
  private void checkSession() throws Exception
  {
    if (this.httpsession.getAttribute("username") != null) {
      if ((((Integer)this.httpsession.getAttribute("level")).intValue() != 2) && (((Integer)this.httpsession.getAttribute("level")).intValue() != 5))
      {

        throw new Exception("Bad Session");
      }
    } else {
      throw new Exception("Session Expired");
    }
  }
}
