package com.ottocap.NewWorkFlow.server.FileHandlers;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.ottocap.NewWorkFlow.server.JSONHelper;
import com.ottocap.NewWorkFlow.server.SQLTable;
import java.io.File;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;







public class UploadProductPreview
{
  private FileItem uploadedfile;
  private Integer hiddenkey;
  private String ordertype;
  HttpServletRequest request;
  HttpServletResponse response;
  
  public UploadProductPreview(List<FileItem> items, HttpServletRequest request, HttpServletResponse response)
  {
    this.request = request;
    this.response = response;
    int foundfile = 0;
    
    foundfile = items.size();
    
    if (items != null) {
      for (int i = 0; i < items.size(); i++) {
        if (((FileItem)items.get(i)).isFormField()) {
          if (((FileItem)items.get(i)).getFieldName().equals("isamplehiddenkey")) {
            try {
              this.hiddenkey = Integer.valueOf(((FileItem)items.get(i)).getString());

            }
            catch (Exception localException1) {}
          } else if (((FileItem)items.get(i)).getFieldName().equals("isampleordertype")) {
            this.ordertype = ((FileItem)items.get(i)).getString();
          }
        }
        else if (((FileItem)items.get(i)).getFieldName().equals("isampleimagefile")) {
          this.uploadedfile = ((FileItem)items.get(i));
        }
      }
    }
    
    try
    {
      checkSession();
    } catch (Exception e) {
      doError(e.getLocalizedMessage());
      return;
    }
    
    if (this.uploadedfile == null) {
      doError("File Does Not Exist" + foundfile);
      return;
    }
    
    String filename = this.uploadedfile.getName();
    if (filename.contains("\\")) {
      filename = filename.substring(filename.lastIndexOf('\\') + 1, filename.length());
    }
    
    if (this.uploadedfile.getSize() != 0L) {
      File filepath = new File("C:\\WorkFlow\\NewWorkflowData\\" + this.ordertype + "Data/" + this.hiddenkey + "/cappreview");
      filepath.mkdirs();
      try {
        File newfile = new File("C:\\WorkFlow\\NewWorkflowData\\" + this.ordertype + "Data/" + this.hiddenkey + "/cappreview/" + filename);
        this.uploadedfile.write(newfile);
      } catch (Exception e) {
        doError(e.getLocalizedMessage());
        return;
      }
    }
    else {
      doError("File Does Not Exist");
      return;
    }
    try
    {
      JsonFactory jsonfactory = new JsonFactory();
      JsonGenerator generator = jsonfactory.createJsonGenerator(response.getOutputStream(), JsonEncoding.UTF8);
      generator.writeStartObject();
      JSONHelper.addObject(generator, "Filename", filename);
      generator.writeEndObject();
      generator.flush();
      generator.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  





  private void doError(String errormsg)
  {
    try
    {
      JsonFactory jsonfactory = new JsonFactory();
      JsonGenerator generator = jsonfactory.createJsonGenerator(this.response.getOutputStream(), JsonEncoding.UTF8);
      generator.writeStartObject();
      JSONHelper.addObject(generator, "error", errormsg);
      generator.writeEndObject();
      generator.flush();
      generator.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  






  private void checkSession()
    throws Exception
  {
    if (this.request.getSession().getAttribute("username") != null) {
      if ((((Integer)this.request.getSession().getAttribute("level")).intValue() != 2) && (((Integer)this.request.getSession().getAttribute("level")).intValue() != 5))
      {
        if ((((Integer)this.request.getSession().getAttribute("level")).intValue() == 0) || (((Integer)this.request.getSession().getAttribute("level")).intValue() == 1) || (((Integer)this.request.getSession().getAttribute("level")).intValue() == 3)) {
          try
          {
            SQLTable mysql = new SQLTable();
            mysql.makeTable("SELECT `employeeid` FROM `ordermain` WHERE `hiddenkey` =" + this.hiddenkey + " LIMIT 1");
            mysql.closeSQL();
            if (mysql.getValue().equals(this.request.getSession().getAttribute("username"))) return;
            throw new Exception("Bad Session");
          }
          catch (Exception e) {
            throw new Exception("Bad Session");
          }
        } else
          throw new Exception("Bad Session");
      }
    } else {
      throw new Exception("Session Expired");
    }
  }
}
