package com.ottocap.NewWorkFlow.server.FileHandlers;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.ottocap.NewWorkFlow.server.JOOQSQL;
import com.ottocap.NewWorkFlow.server.JSONHelper;
import com.ottocap.NewWorkFlow.server.LogoGenerator.DSTImage;
import com.ottocap.NewWorkFlow.server.SharedData;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.jooq.SelectJoinStep;
import org.jooq.SelectSelectStep;
import org.jooq.TableLike;
import org.jooq.impl.DSL;

public class JOOQ_UploadLogo extends Thread
{
  private FileItem uploadedfile;
  private Integer hiddenkey;
  private String ordertype;
  HttpServletRequest request;
  HttpServletResponse response;
  
  public JOOQ_UploadLogo(List<FileItem> items, HttpServletRequest request, HttpServletResponse response)
  {
    this.request = request;
    this.response = response;
    int foundfile = 0;
    
    foundfile = items.size();
    
    if (items != null) {
      for (int i = 0; i < items.size(); i++) {
        if (((FileItem)items.get(i)).isFormField()) {
          if (((FileItem)items.get(i)).getFieldName().equals("filehiddenkey")) {
            try {
              this.hiddenkey = Integer.valueOf(((FileItem)items.get(i)).getString());

            }
            catch (Exception localException1) {}
          } else if (((FileItem)items.get(i)).getFieldName().equals("fileordertype")) {
            this.ordertype = ((FileItem)items.get(i)).getString();
          }
        }
        else if (((FileItem)items.get(i)).getFieldName().equals("fileimagefile")) {
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
      File filepath = new File("C:\\WorkFlow\\NewWorkflowData\\" + this.ordertype + "Data/" + this.hiddenkey + "/logos");
      filepath.mkdirs();
      try {
        File newfile = new File("C:\\WorkFlow\\NewWorkflowData\\" + this.ordertype + "Data/" + this.hiddenkey + "/logos/" + filename);
        this.uploadedfile.write(newfile);
      } catch (Exception e) {
        doError(e.getLocalizedMessage());
        return;
      }
      try
      {
        sleep(1000L);
      }
      catch (InterruptedException localInterruptedException) {}
      boolean convertsuccess = false;
      
      if ((convertsuccess) && (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".emb")) && ("C:\\Program Files\\S & S Computing\\SewWhat-Pro\\sewwhat-pro.exe" != null))
      {
        try
        {
          File checkfile = new File(filepath + "\\" + filename.substring(0, filename.lastIndexOf(".")) + ".dst");
          if (checkfile.exists()) {
            checkfile.delete();
          }
          
          Runtime rt = Runtime.getRuntime();
          rt.exec("C:\\Program Files\\S & S Computing\\SewWhat-Pro\\sewwhat-pro.exe \"" + filename + "\" \"" + filename.substring(0, filename.lastIndexOf(".")) + ".dst\" /c", null, filepath);
          
          Date currenttime = new Date();
          boolean timeup = false;
          while ((!checkfile.exists()) && (!timeup)) {
            sleep(1000L);
            Date looptime = new Date();
            if (looptime.getTime() - currenttime.getTime() > 10000L) {
              timeup = true;
            }
          }
          
          if ((checkfile.exists()) && (checkfile.length() > 0L)) {
            filename = filename.substring(0, filename.lastIndexOf(".")) + ".dst";
            convertsuccess = true;
          }
        }
        catch (Exception localException2) {}
      }
      

      if ((!convertsuccess) && ((filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".pdf")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".eps")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".ai")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".psd")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".tif")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".bmp"))) && ("C:\\Program Files\\IrfanView\\i_view32.exe" != null)) {
        try {
          File checkfile = new File(filepath + "/" + filename.substring(0, filename.lastIndexOf(".")) + ".png");
          if (checkfile.exists()) {
            checkfile.delete();
          }
          
          Runtime rt = Runtime.getRuntime();
          





          ArrayList<String> thearray = new ArrayList();
          thearray.add("C:\\Program Files\\IrfanView\\i_view32.exe");
          thearray.add(filename);
          thearray.addAll(Arrays.asList(("/convert=" + filename.substring(0, filename.lastIndexOf('.')) + ".png").split(" ")));
          thearray.add("/silent");
          
          rt.exec((String[])thearray.toArray(new String[0]), null, filepath);
          
          Date currenttime = new Date();
          boolean timeup = false;
          while ((!checkfile.exists()) && (!timeup)) {
            sleep(1000L);
            Date looptime = new Date();
            if (looptime.getTime() - currenttime.getTime() > 10000L) {
              timeup = true;
            }
          }
          
          if ((checkfile.exists()) && (checkfile.length() > 0L)) {
            filename = filename.substring(0, filename.lastIndexOf('.')) + ".png";
            convertsuccess = true;
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
      

      if ((!convertsuccess) && ((filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".pxf")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".pof")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".tbf")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".tcf")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".shv")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".vip")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".vp3")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".exp")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".xxx")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".jef")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".hus")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".pes")))) {
        try
        {
          String userid = UUID.randomUUID().toString();
          String server = "http://ambassador.pulsemicro.com/UploadDesign.ashx?User=TempUser&File=temp.pxf&Session=" + userid;
          
          DefaultHttpClient httpclient = new DefaultHttpClient();
          httpclient.getParams().setParameter("http.protocol.version", HttpVersion.HTTP_1_1);
          
          HttpPost method = new HttpPost(server);
          
          File file = new File(filepath + "/" + filename);
          
          FileEntity fileentity = new FileEntity(file);
          method.setEntity(fileentity);
          
          HttpResponse httpresponse = httpclient.execute(method);
          if (httpresponse.getStatusLine().getStatusCode() == 200)
          {
            File checkfile = new File(filepath + "/" + filename.substring(0, filename.lastIndexOf(".")) + ".dst");
            FileUtils.copyURLToFile(new URL("http://ambassador.pulsemicro.com/SaveDesign.ashx?File=temp.dst&Session=" + userid), checkfile);
            if ((checkfile.exists()) && (checkfile.length() > 0L)) {
              filename = filename.substring(0, filename.lastIndexOf(".")) + ".dst";
              convertsuccess = true;
            }
          }
        }
        catch (Exception localException3) {}
      }
      



      if ((!convertsuccess) && (
        (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".emb")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".art")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".art42")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".art50")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".art60")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".jan")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".u01")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".dsb")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".exp")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".dsz")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".z01")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".10o")) || 
        (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".ksm")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".tap")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".inb")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".xxx")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".sew")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".jef")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".hus")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".pes")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".pec")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".pcs")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".pcd")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".pcq")) || 
        (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".csd")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".emx")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".arx")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".bro")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".pch")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".pum")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".pmu")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".stc")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".stx")))) {
        try {
          new Convert_Wilcom(filepath, filename);
          File checkfile = new File(filepath + "/" + filename.substring(0, filename.lastIndexOf(".")) + ".DST");
          if ((checkfile.exists()) && (checkfile.length() > 0L)) {
            filename = filename.substring(0, filename.lastIndexOf(".")) + ".DST";
            convertsuccess = true;
          }
        }
        catch (Exception localException4) {}
      }
      

      if ((!convertsuccess) && 
        ((filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".emb")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".art")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".art42")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".art50")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".art60")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".jan")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".u01")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".dsb")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".exp")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".dsz")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".z01")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".10o")) || 
        (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".ksm")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".tap")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".inb")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".xxx")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".sew")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".jef")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".hus")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".pes")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".pec")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".pcs")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".pcd")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".pcq")) || 
        (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".csd")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".emx")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".arx")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".bro")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".pch")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".pum")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".pmu")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".stc")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".stx"))) && (SharedData.WILCOMLOCATION != null)) {
        try
        {
          File checkfile = new File(filepath + "/" + filename.substring(0, filename.lastIndexOf(".")) + ".dst");
          if (checkfile.exists()) {
            checkfile.delete();
          }
          
          Runtime rt = Runtime.getRuntime();
          rt.exec("C:\\WorkFlow\\NewWorkflowData\\scripts/wilcomts3autosave.exe \"" + SharedData.WILCOMLOCATION + "\" \"" + filepath + "\\\\\" \"" + filename.substring(0, filename.lastIndexOf(".")) + "\" \"" + filename.substring(filename.lastIndexOf(".")) + "\"", null, filepath);
          
          Date currenttime = new Date();
          boolean timeup = false;
          while ((!checkfile.exists()) && (!timeup)) {
            sleep(1000L);
            Date looptime = new Date();
            if (looptime.getTime() - currenttime.getTime() > 10000L) {
              timeup = true;
            }
          }
          
          if ((checkfile.exists()) && (checkfile.length() > 0L)) {
            filename = filename.substring(0, filename.lastIndexOf(".")) + ".dst";
            convertsuccess = true;
          }
        }
        catch (Exception localException5) {}
      }
      
      if ((!convertsuccess) && 
        ((filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".ofm")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".cnd")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".exp")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".fdr")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".fmc")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".zsk")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".pes")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".pec")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".sew")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".jef")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".jef+")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".pcs")) || 
        (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".pcm")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".csd")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".xxx")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".hus")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".oef")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".pat")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".gnc")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".emd")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".shv")) || (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".vip"))) && (SharedData.MELCOLOCATION != null)) {
        try
        {
          File checkfile = new File(filepath + "/" + filename.substring(0, filename.lastIndexOf(".")) + ".dst");
          if (checkfile.exists()) {
            checkfile.delete();
          }
          
          Runtime rt = Runtime.getRuntime();
          rt.exec("C:\\WorkFlow\\NewWorkflowData\\scripts/melcoautosave.exe \"" + SharedData.MELCOLOCATION + "\" \"" + filepath + "\\\\\" \"" + filename.substring(0, filename.lastIndexOf(".")) + "\" \"" + filename.substring(filename.lastIndexOf(".")) + "\"", null, filepath);
          
          Date currenttime = new Date();
          boolean timeup = false;
          while ((!checkfile.exists()) && (!timeup)) {
            sleep(1000L);
            Date looptime = new Date();
            if (looptime.getTime() - currenttime.getTime() > 10000L) {
              timeup = true;
            }
          }
          
          if ((!checkfile.exists()) || (checkfile.length() <= 0L)) break label4577;
          filename = filename.substring(0, filename.lastIndexOf(".")) + ".dst";
          convertsuccess = true;
        }
        catch (Exception localException6) {}
      }
    }
    else
    {
      doError("File Does Not Exist");
      return;
    }
    try {
      label4577:
      JsonFactory jsonfactory = new JsonFactory();
      JsonGenerator generator = jsonfactory.createJsonGenerator(response.getOutputStream(), JsonEncoding.UTF8);
      generator.writeStartObject();
      JSONHelper.addObject(generator, "Filename", filename);
      
      if (filename.substring(filename.lastIndexOf('.')).toLowerCase().equals(".dst")) {
        try {
          DSTImage mydst = new DSTImage("C:\\WorkFlow\\NewWorkflowData\\" + this.ordertype + "Data/" + this.hiddenkey + "/logos/" + filename);
          JSONHelper.addObject(generator, "Colorways", Integer.valueOf(mydst.getTotalColors()));
          JSONHelper.addObject(generator, "Total Stitch Count", Integer.valueOf(mydst.getTotalStitchCount()));
          JSONHelper.addObject(generator, "Logo Size Width", Double.valueOf(mydst.getStitchSizeWidth()));
          JSONHelper.addObject(generator, "Logo Size Height", Double.valueOf(mydst.getStitchSizeHeight()));
          
          generator.writeFieldName("Colorway");
          generator.writeStartArray();
          
          for (int i = 0; i < mydst.getTotalColors(); i++) {
            generator.writeStartObject();
            JSONHelper.addObject(generator, "Stitches Per Color", Integer.valueOf(mydst.getStitchCount(i)));
            generator.writeEndObject();
          }
          generator.writeEndArray();
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }
      

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
            Record sqlrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "employeeid" })).from(new TableLike[] { DSL.tableByName(new String[] { "ordermain" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "hiddenkey" }).equal(this.hiddenkey) }).limit(1).fetchOne();
            if (sqlrecord == null) return; if (sqlrecord.getValue(DSL.fieldByName(new String[] { "employeeid" })).equals(this.request.getSession().getAttribute("username"))) return;
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
