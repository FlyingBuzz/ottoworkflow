package com.ottocap.NewWorkFlow.server;

import com.extjs.gxt.ui.client.core.FastMap;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Set;








public class SQLTable
{
  private ArrayList<FastMap<Object>> arraylisttable = new ArrayList();
  









  private Connection myconnection;
  









  public SQLTable()
  {
    try
    {
      Class.forName("com.mysql.jdbc.Driver");
      

      this.myconnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fayadatabase3?useUnicode=true&characterEncoding=UTF-8", "root", "databasepassword");
    }
    catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }
  
  public void executeBatch(String[] query)
  {
    try {
      Statement mystatement = this.myconnection.createStatement();
      String[] arrayOfString; int j = (arrayOfString = query).length; for (int i = 0; i < j; i++) { String singlequery = arrayOfString[i];
        mystatement.addBatch(singlequery);
      }
      mystatement.executeBatch();
      mystatement.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
  






  public void runQuery(String query)
  {
    try
    {
      Statement mystatement = this.myconnection.createStatement();
      mystatement.execute(query);
      mystatement.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
  






  public Boolean makeTable(String query)
  {
    try
    {
      Statement mystatement = this.myconnection.createStatement();
      ResultSet myresult = mystatement.executeQuery(query);
      

      this.arraylisttable = new ArrayList();
      boolean havetable = false;
      while (myresult.next()) {
        FastMap<Object> table = new FastMap();
        for (int j = 1; j <= myresult.getMetaData().getColumnCount(); j++) {
          table.put(myresult.getMetaData().getColumnLabel(j), myresult.getObject(j));
        }
        this.arraylisttable.add(table);
        havetable = true;
      }
      
      myresult.close();
      mystatement.close();
      return Boolean.valueOf(havetable);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
  




  public ArrayList<FastMap<Object>> getTable()
  {
    return this.arraylisttable;
  }
  




  public FastMap<Object> getRow()
  {
    return (FastMap)this.arraylisttable.get(0);
  }
  






  public Object getCell(String cellname)
  {
    return ((FastMap)this.arraylisttable.get(0)).get(cellname);
  }
  




  public Object getValue()
  {
    return ((FastMap)this.arraylisttable.get(0)).get(((String[])((FastMap)this.arraylisttable.get(0)).keySet().toArray(new String[0]))[0]);
  }
  
  public int insertRow(String table, FastMap<Object> mykeys) {
    String query = "INSERT INTO `" + table + "` (";
    
    Object[] mykey = mykeys.keySet().toArray();
    for (int i = 0; i < mykeys.size(); i++) {
      query = query + "`" + mykey[i] + "`,";
    }
    query = query.substring(0, query.length() - 1);
    query = query + ") VALUES (";
    for (int i = 0; i < mykeys.size(); i++) {
      query = query + "?,";
    }
    query = query.substring(0, query.length() - 1);
    query = query + ")";
    try
    {
      PreparedStatement mystatement = this.myconnection.prepareStatement(query, 1);
      for (int i = 0; i < mykeys.size(); i++) {
        mystatement.setObject(i + 1, mykeys.get(mykey[i]));
      }
      mystatement.execute();
      ResultSet myresult = mystatement.getGeneratedKeys();
      int index = -1;
      if (myresult.next()) {
        index = myresult.getInt(1);
      }
      myresult.close();
      mystatement.close();
      return index;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
  










  public void insertUpdateRow(String table, FastMap<Object> mykeys, FastMap<Object> myfilter)
  {
    String filterstring = "";
    String[] myfilters = (String[])myfilter.keySet().toArray(new String[0]);
    for (int i = 0; i < myfilter.size(); i++) {
      filterstring = filterstring + "`" + myfilters[i] + "` = '" + myfilter.get(myfilters[i]) + "' AND";
    }
    filterstring = filterstring.substring(0, filterstring.length() - 3);
    try
    {
      String filler = "";
      String[] keyset = (String[])mykeys.keySet().toArray(new String[0]);
      for (int i = 0; i < mykeys.size(); i++) {
        filler = filler + "`" + keyset[i] + "` = ?,";
      }
      filler = filler.substring(0, filler.length() - 1);
      String update = "UPDATE `" + table + "` SET " + filler + " WHERE " + filterstring;
      PreparedStatement mystatement = this.myconnection.prepareStatement(update);
      for (int i = 0; i < mykeys.size(); i++) {
        mystatement.setObject(i + 1, mykeys.get(keyset[i]));
      }
      int rowchanged = mystatement.executeUpdate();
      mystatement.close();
      if (rowchanged == 0) {
        insertRow(table, mykeys);
      }
    } catch (SQLException e1) {
      throw new RuntimeException(e1);
    }
  }
  





















































































































  public void closeSQL()
  {
    try
    {
      this.myconnection.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
