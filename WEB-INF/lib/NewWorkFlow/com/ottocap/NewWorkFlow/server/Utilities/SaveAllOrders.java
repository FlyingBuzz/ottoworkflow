package com.ottocap.NewWorkFlow.server.Utilities;

import com.extjs.gxt.ui.client.core.FastMap;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData;
import com.ottocap.NewWorkFlow.server.RPCService.GetOrder;
import com.ottocap.NewWorkFlow.server.RPCService.SaveOrder;
import com.ottocap.NewWorkFlow.server.SQLTable;
import java.io.PrintStream;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class SaveAllOrders
{
  public SaveAllOrders(HttpServletRequest request, HttpServletResponse response)
  {
    SQLTable mytable = new SQLTable();
    
    mytable.makeTable("SELECT hiddenkey FROM ordermain");
    
    ArrayList<FastMap<Object>> thelist = mytable.getTable();
    
    for (int i = 0; i < thelist.size(); i++) {
      int thehiddenkey = ((Integer)((FastMap)thelist.get(i)).get("hiddenkey")).intValue();
      
      try
      {
        GetOrder getorderdata = new GetOrder(thehiddenkey, request.getSession(), mytable);
        OrderData theorderdata = getorderdata.getOrder();
        new SaveOrder(theorderdata, request.getSession(), mytable);
      } catch (Exception e) {
        e.printStackTrace();
        System.out.println(thehiddenkey + " Failed");
      }
    }
    

    mytable.closeSQL();
  }
}
