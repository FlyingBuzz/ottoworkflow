package com.ottocap.NewWorkFlow.server.RPCService;

import com.ottocap.NewWorkFlow.server.JOOQSQL;
import javax.servlet.http.HttpSession;
import org.jooq.DSLContext;
import org.jooq.UpdateSetMoreStep;
import org.jooq.impl.DSL;

public class JOOQ_SaveOrderEmployee
{
  private HttpSession httpsession;
  
  public JOOQ_SaveOrderEmployee(int hiddenkey, String employeeid, HttpSession httpsession) throws Exception
  {
    this.httpsession = httpsession;
    checkSession();
    
    JOOQSQL.getInstance().create().update(DSL.tableByName(new String[] { "ordermain" })).set(DSL.fieldByName(new String[] { "hiddenkey" }), Integer.valueOf(hiddenkey)).set(DSL.fieldByName(new String[] { "employeeid" }), employeeid).execute();
  }
  
  private void checkSession()
    throws Exception
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
