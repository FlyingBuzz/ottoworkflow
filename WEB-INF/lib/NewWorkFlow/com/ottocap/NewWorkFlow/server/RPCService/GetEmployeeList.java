package com.ottocap.NewWorkFlow.server.RPCService;

import com.extjs.gxt.ui.client.core.FastMap;
import com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBoxModelData;
import com.ottocap.NewWorkFlow.server.SQLTable;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;



public class GetEmployeeList
{
  private HttpSession httpsession;
  private ArrayList<OtherComboBoxModelData> mylist = new ArrayList();
  
  public GetEmployeeList(HttpSession httpsession) throws Exception {
    this.httpsession = httpsession;
    checkSession();
    
    SQLTable sqltable = new SQLTable();
    sqltable.makeTable("SELECT username,firstname,lastname FROM `employees` ORDER BY `username` ASC");
    sqltable.closeSQL();
    
    ArrayList<FastMap<Object>> mytable = sqltable.getTable();
    for (FastMap<Object> currentobject : mytable) {
      OtherComboBoxModelData mydata = new OtherComboBoxModelData(currentobject.get("firstname") + " " + currentobject.get("lastname"), (String)currentobject.get("username"));
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
