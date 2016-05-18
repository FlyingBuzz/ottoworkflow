package com.ottocap.NewWorkFlow.server.RPCService;

import java.io.File;


public class RemoveEclipseAccountLogo
{
  public RemoveEclipseAccountLogo(int eclipseaccountnumber)
  {
    File thefile = new File("C:\\WorkFlow\\NewWorkflowData\\pdflogos/" + eclipseaccountnumber + "/logo.jpg");
    if (thefile.exists()) {
      thefile.delete();
    }
  }
}
