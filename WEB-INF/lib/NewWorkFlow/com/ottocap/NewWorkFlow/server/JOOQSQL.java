package com.ottocap.NewWorkFlow.server;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;






public class JOOQSQL
{
  private static JOOQSQL instance = null;
  private MysqlDataSource datasource;
  
  protected JOOQSQL()
  {
    this.datasource = new MysqlDataSource();
    this.datasource.setUser("root");
    this.datasource.setPassword("databasepassword");
    this.datasource.setServerName("localhost");
    this.datasource.setPort(3306);
    this.datasource.setDatabaseName("fayadatabase3");
    this.datasource.setUseUnicode(true);
    this.datasource.setCharacterEncoding("UTF-8");
  }
  
  public static JOOQSQL getInstance()
  {
    if (instance == null) {
      instance = new JOOQSQL();
    }
    return instance;
  }
  
  public DSLContext create() {
    return DSL.using(this.datasource, SQLDialect.MYSQL);
  }
  
  public Connection getDataSourceConnection() {
    try {
      return this.datasource.getConnection();
    } catch (SQLException e) {}
    return null;
  }
}
