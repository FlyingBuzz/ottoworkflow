package com.ottocap.NewWorkFlow.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Writer;
import java.net.URLDecoder;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




public class ExternalFiles
  extends HttpServlet
{
  private static final long serialVersionUID = 1L;
  private static String externalDocBase = "C:\\WorkFlow\\NewWorkflowData\\";
  


  private static File file(String name)
  {
    return new File(externalDocBase, name);
  }
  
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setCharacterEncoding("UTF-8");
    ServletContext context = getServletContext();
    
    String fullurlpath = request.getRequestURI();
    String suburlpath = request.getServletPath();
    String currenturl = fullurlpath.substring(fullurlpath.indexOf(suburlpath) + suburlpath.length());
    

    File file = file(URLDecoder.decode(currenturl, "UTF-8"));
    

    if ((!file.exists()) || (!file.canRead()) || ((file.isDirectory()) && (!request.getRequestURI().endsWith("/")))) {
      response.setStatus(404);
      return;
    }
    
    if ((file.isDirectory()) && (request.getRequestURI().endsWith("/"))) {
      File[] myfiles = file.listFiles();
      Writer out = response.getWriter();
      out.append("<a href=\"../\">../</a><BR>");
      for (int i = 0; i < myfiles.length; i++) {
        if (myfiles[i].isDirectory()) {
          out.append("<a href=\"" + myfiles[i].getName() + "/\">" + myfiles[i].getName() + "/</a><BR>");
        } else {
          out.append("<a href=\"" + myfiles[i].getName() + "\">" + myfiles[i].getName() + "</a><BR>");
        }
      }
      out.close();
      return;
    }
    

    String mimeType = context.getMimeType(file.getAbsolutePath());
    if (mimeType == null) {
      response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
    }
    else {
      response.setContentType(mimeType);
    }
    


    response.setContentLength((int)file.length());
    

    FileInputStream in = new FileInputStream(file);
    ServletOutputStream out = response.getOutputStream();
    

    byte[] buf = new byte['Ѐ'];
    int count = 0;
    while ((count = in.read(buf)) >= 0) {
      out.write(buf, 0, count);
    }
    in.close();
    out.close();
  }
}
