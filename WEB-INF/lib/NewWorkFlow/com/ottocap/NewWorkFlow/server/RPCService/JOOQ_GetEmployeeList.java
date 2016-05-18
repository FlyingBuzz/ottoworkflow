package com.ottocap.NewWorkFlow.server.RPCService;

import com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBoxModelData;
import com.ottocap.NewWorkFlow.server.JOOQSQL;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.SelectJoinStep;
import org.jooq.SelectSelectStep;
import org.jooq.SortField;
import org.jooq.TableLike;
import org.jooq.impl.DSL;

public class JOOQ_GetEmployeeList
{
  private HttpSession httpsession;
  private ArrayList<OtherComboBoxModelData> mylist = new ArrayList();
  
  public JOOQ_GetEmployeeList(HttpSession httpsession) throws Exception {
    this.httpsession = httpsession;
    checkSession();
    
    org.jooq.Result<org.jooq.Record3<Object, Object, Object>> sqldata = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "username" }), DSL.fieldByName(new String[] { "firstname" }), DSL.fieldByName(new String[] { "lastname" })).from(new TableLike[] { DSL.tableByName(new String[] { "employees" }) }).orderBy(new SortField[] { DSL.fieldByName(new String[] { "username" }).asc() }).fetch();
    
    for (Record sqlrecord : sqldata) {
      OtherComboBoxModelData mydata = new OtherComboBoxModelData(sqlrecord.getValue(DSL.fieldByName(new String[] { "firstname" })) + " " + sqlrecord.getValue(DSL.fieldByName(new String[] { "lastname" })), (String)sqlrecord.getValue(DSL.fieldByName(new String[] { "username" })));
      this.mylist.add(mydata);
    }
  }
  
  public ArrayList<OtherComboBoxModelData> getList()
  {
    return this.mylist;
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
