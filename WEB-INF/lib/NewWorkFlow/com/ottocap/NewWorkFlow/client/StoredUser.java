package com.ottocap.NewWorkFlow.client;

import java.io.Serializable;



public class StoredUser
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String username = "";
  

  private String emailaddress = "";
  private int accesslevel = -1;
  















  public void setUsername(String username)
  {
    this.username = username;
  }
  
  public String getUsername() {
    return this.username;
  }
  
  public void setEmailAddress(String emailaddress) {
    this.emailaddress = emailaddress;
  }
  
  public void setAccessLevel(int accesslevel) {
    this.accesslevel = accesslevel;
  }
  
  public String getEmailAddress() {
    return this.emailaddress;
  }
  
  public int getAccessLevel() {
    return this.accesslevel;
  }
}
