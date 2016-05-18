package com.ottocap.NewWorkFlow.server.RPCService;

import com.ottocap.NewWorkFlow.server.JOOQSQL;
import javax.servlet.http.HttpSession;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SelectJoinStep;
import org.jooq.impl.DSL;

public class JOOQ_GetOrderEmployee
{
  private HttpSession httpsession;
  private String employeeid;
  
  public JOOQ_GetOrderEmployee(int hiddenkey, HttpSession httpsession) throws Exception
  {
    this.httpsession = httpsession;
    checkSession();
    
    Record record = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "employeeid" })).from(new org.jooq.TableLike[] { DSL.tableByName(new String[] { "ordermain" }) }).where(new org.jooq.Condition[] { DSL.fieldByName(new String[] { "hiddenkey" }).equal(Integer.valueOf(hiddenkey)) }).limit(1).fetchOne();
    this.employeeid = ((String)record.getValue(DSL.fieldByName(new String[] { "employeeid" })));
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
