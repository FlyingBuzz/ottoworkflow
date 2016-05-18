package com.ottocap.NewWorkFlow.server.FileHandlers;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.ottocap.NewWorkFlow.server.JSONHelper;
import com.ottocap.NewWorkFlow.server.SQLTable;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;












public class UploadCompanyLogo
{
  private String eclipseaccountnumber = "";
  private FileItem uploadedfile;
  private Integer hiddenkey;
  private String ordertype;
  private String submittype;
  HttpServletRequest request;
  HttpServletResponse response;
  
  public UploadCompanyLogo(List<FileItem> items, HttpServletRequest request, HttpServletResponse response) {
    this.request = request;
    this.response = response;
    int foundfile = 0;
    
    foundfile = items.size();
    
    if (items != null) {
      for (int i = 0; i < items.size(); i++) {
        if (((FileItem)items.get(i)).isFormField()) {
          if (((FileItem)items.get(i)).getFieldName().equals("companylogoeclipse")) {
            this.eclipseaccountnumber = ((FileItem)items.get(i)).getString();
          } else if (((FileItem)items.get(i)).getFieldName().equals("companylogosubmittype")) {
            this.submittype = ((FileItem)items.get(i)).getString();
          } else if (((FileItem)items.get(i)).getFieldName().equals("companylogohiddenkey")) {
            this.hiddenkey = Integer.valueOf(((FileItem)items.get(i)).getString());
          } else if (((FileItem)items.get(i)).getFieldName().equals("companylogoordertype")) {
            this.ordertype = ((FileItem)items.get(i)).getString();
          }
        }
        else if (((FileItem)items.get(i)).getFieldName().equals("companylogofile")) {
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
      filename = filename.substring(0, filename.lastIndexOf('.')) + ".jpg";
    }
    if (this.uploadedfile.getSize() != 0L)
    {
      try
      {
        myimage = ImageIO.read(this.uploadedfile.getInputStream());
      } catch (Exception e) { BufferedImage myimage;
        doError(e.getLocalizedMessage()); return;
      }
      BufferedImage myimage;
      if (myimage == null) {
        doError("File is not a readable image");
        return;
      }
      Image scaledimage = myimage.getScaledInstance(myimage.getWidth(), myimage.getHeight(), 4);
      
      BufferedImage newcopyimage = new BufferedImage(myimage.getWidth(), myimage.getHeight(), 1);
      Graphics currentimage = newcopyimage.getGraphics();
      currentimage.setColor(Color.WHITE);
      currentimage.fillRect(0, 0, newcopyimage.getWidth(), newcopyimage.getHeight());
      currentimage.drawImage(scaledimage, 0, 0, null);
      
      if (this.submittype.equals("global")) {
        File filepath = new File("C:\\WorkFlow\\NewWorkflowData\\pdflogos/" + this.eclipseaccountnumber);
        filepath.mkdirs();
        try
        {
          ImageIO.write(newcopyimage, "jpeg", new File("C:\\WorkFlow\\NewWorkflowData\\pdflogos/" + this.eclipseaccountnumber + "/logo.jpg"));
        } catch (Exception e) {
          doError(e.getLocalizedMessage());
          return;
        }
        filename = "Default";
      } else {
        File filepath = new File("C:\\WorkFlow\\NewWorkflowData\\" + this.ordertype + "Data/" + this.hiddenkey + "/companylogo/");
        filepath.mkdirs();
        try
        {
          ImageIO.write(newcopyimage, "jpeg", new File("C:\\WorkFlow\\NewWorkflowData\\" + this.ordertype + "Data/" + this.hiddenkey + "/companylogo/" + filename));
        } catch (Exception e) {
          doError(e.getLocalizedMessage());
          return;
        }
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
