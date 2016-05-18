package com.ottocap.NewWorkFlow.server.FileHandlers;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.ottocap.NewWorkFlow.server.JSONHelper;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;












public class Uploader
  extends HttpServlet
{
  private static final long serialVersionUID = 1L;
  
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException
  {
    req.setCharacterEncoding("UTF-8");
    resp.setContentType("text/html");
    
    String uploadtype = "";
    

    FileItemFactory factory = new DiskFileItemFactory();
    

    ServletFileUpload upload = new ServletFileUpload(factory);
    

    List<FileItem> items = null;
    try {
      items = upload.parseRequest(req);
      for (int i = 0; i < items.size(); i++) {
        if ((((FileItem)items.get(i)).isFormField()) && 
          (((FileItem)items.get(i)).getFieldName().equals("uploadtype"))) {
          uploadtype = ((FileItem)items.get(i)).getString();
          i = items.size();
        }
      }
    }
    catch (FileUploadException e) {
      doError(e.getLocalizedMessage(), resp);
    }
    
    if (uploadtype.equals("Company Logo")) {
      upload.setSizeMax(2000000L);
      new UploadCompanyLogo(items, req, resp);
    } else if (uploadtype.equals("Product Preview Image")) {
      upload.setSizeMax(2000000L);
      new UploadProductPreview(items, req, resp);
    } else if (uploadtype.equals("Logo Upload")) {
      upload.setSizeMax(2000000L);
      new UploadLogo(items, req, resp);
    } else if (uploadtype.equals("Import Order")) {
      upload.setSizeMax(100000000L);
      try {
        new ImportOrder(items, req, resp);
      } catch (Exception e) {
        e.printStackTrace();
        doError(e.getLocalizedMessage(), resp);
      }
    }
  }
  
  public static void doError(String errormsg, HttpServletResponse resp)
  {
    try {
      JsonFactory jsonfactory = new JsonFactory();
      JsonGenerator generator = jsonfactory.createJsonGenerator(resp.getOutputStream(), JsonEncoding.UTF8);
      generator.writeStartObject();
      JSONHelper.addObject(generator, "error", errormsg);
      generator.writeEndObject();
      generator.flush();
      generator.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
