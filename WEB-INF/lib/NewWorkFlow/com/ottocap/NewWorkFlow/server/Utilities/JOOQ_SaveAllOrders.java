package com.ottocap.NewWorkFlow.server.Utilities;

import com.ottocap.NewWorkFlow.server.JOOQSQL;
import com.ottocap.NewWorkFlow.server.RPCService.JOOQ_GetOrder;
import com.ottocap.NewWorkFlow.server.RPCService.JOOQ_SaveOrder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.SelectJoinStep;
import org.jooq.SelectSelectStep;
import org.jooq.TableLike;
import org.jooq.impl.DSL;

public class JOOQ_SaveAllOrders
{
  public JOOQ_SaveAllOrders(HttpServletRequest request, HttpServletResponse response)
  {
    Result<Record1<Object>> sqlrecords = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "hiddenkey" })).from(new TableLike[] { DSL.tableByName(new String[] { "ordermain" }) }).fetch();
    
    for (int i = 0; i < sqlrecords.size(); i++) {
      int thehiddenkey = ((Integer)((Record1)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "hiddenkey" }))).intValue();
      
      try
      {
        JOOQ_GetOrder getorderdata = new JOOQ_GetOrder(thehiddenkey, request.getSession());
        com.ottocap.NewWorkFlow.client.OrderData.OrderData theorderdata = getorderdata.getOrder();
        new JOOQ_SaveOrder(theorderdata, request.getSession());
      } catch (Exception e) {
        e.printStackTrace();
        System.out.println(thehiddenkey + " Failed");
      }
    }
  }
}
