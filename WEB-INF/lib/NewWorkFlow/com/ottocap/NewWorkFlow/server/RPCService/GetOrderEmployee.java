package com.ottocap.NewWorkFlow.server.RPCService;

import com.ottocap.NewWorkFlow.server.SQLTable;
import javax.servlet.http.HttpSession;

public class GetOrderEmployee
{
  private HttpSession httpsession;
  private String employeeid;
  
  public GetOrderEmployee(int hiddenkey, HttpSession httpsession) throws Exception
  {
    this.httpsession = httpsession;
    checkSession();
    
    SQLTable sqltable = new SQLTable();
    sqltable.makeTable("SELECT `employeeid` FROM `ordermain` WHERE `hiddenkey` =" + hiddenkey + " LIMIT 1");
    this.employeeid = ((String)sqltable.getValue());
    sqltable.closeSQL();
  }
  
  public String getEmployeeID() {
    return this.employeeid;
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
