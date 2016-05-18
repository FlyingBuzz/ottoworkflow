package com.ottocap.NewWorkFlow.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData.OrderType;
import com.ottocap.NewWorkFlow.server.RPCService.JOOQ_GetOrder;
import com.ottocap.NewWorkFlow.server.RPCService.JOOQ_SaveOrder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;


public class JOOQ_ExportOrder
  extends HttpServlet
{
  private static final long serialVersionUID = 1L;
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    response.setCharacterEncoding("UTF-8");
    response.setContentType("application/zip");
    response.setHeader("Content-Disposition", "inline; filename=Export-" + request.getParameter("hiddenkey") + ".zip");
    
    try
    {
      JOOQ_GetOrder myorder = new JOOQ_GetOrder(Integer.valueOf(request.getParameter("hiddenkey")).intValue(), request.getSession());
      OrderData orderdata = myorder.getOrder();
      
      ZipArchiveOutputStream zipout = new ZipArchiveOutputStream(response.getOutputStream());
      




      zipDir1("C:\\WorkFlow\\NewWorkflowData\\" + (orderdata.getOrderType() == OrderData.OrderType.DOMESTIC ? "Domestic" : "Overseas") + "Data/" + orderdata.getHiddenKey(), zipout, true);
      
      zipout.putArchiveEntry(new ZipArchiveEntry("orderdata.dat"));
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      ObjectOutputStream myorderoutput = new ObjectOutputStream(bos);
      ObjectMapper mapper = new ObjectMapper();
      
      mapper.writeValue(myorderoutput, orderdata);
      myorderoutput.close();
      IOUtils.copy(new ByteArrayInputStream(bos.toByteArray()), zipout);
      zipout.closeArchiveEntry();
      
      zipout.close();
      






















      if (request.getParameter("managetype") != null) {
        if (request.getParameter("managetype").equals("NEWQUOTESORDERS")) {
          if (orderdata.getOrderStatus().equals("New Quotation")) {
            orderdata.setOrderStatus("Exported New Quotation");
            new JOOQ_SaveOrder(orderdata, request.getSession());
          } else if (orderdata.getOrderStatus().equals("New Order")) {
            orderdata.setOrderStatus("Exported New Order");
            new JOOQ_SaveOrder(orderdata, request.getSession());
          }
        } else if ((request.getParameter("managetype").equals("NEWVIRTUALSAMPLES")) && 
          (orderdata.getOrderStatus().equals("New Virtual Sample Request"))) {
          orderdata.setOrderStatus("Exported New Virtual Sample Request");
          new JOOQ_SaveOrder(orderdata, request.getSession());
        }
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
      throw new ServletException(e);
    }
  }
  
  private void zipDir1(String dir2zip, ArchiveOutputStream zipout, boolean rootpath)
  {
    try
    {
      File zipDir = new File(dir2zip);
      if (!zipDir.isDirectory()) {
        return;
      }
      if (rootpath) {
        this.theroot = zipDir.getPath();
      }
      
      String[] dirList = zipDir.list();
      

      for (int i = 0; i < dirList.length; i++) {
        File f = new File(zipDir, dirList[i]);
        if (f.isDirectory())
        {

          String filePath = f.getPath();
          zipDir1(filePath, zipout, false);


        }
        else
        {

          zipout.putArchiveEntry(new ZipArchiveEntry(f.getPath().replace(this.theroot, "data")));
          IOUtils.copy(new FileInputStream(f), zipout);
          zipout.closeArchiveEntry();
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  

  private String theroot = "";
  
  public void zipDir(String dir2zip, ZipOutputStream zos, boolean rootpath)
  {
    try
    {
      File zipDir = new File(dir2zip);
      if (!zipDir.isDirectory()) {
        return;
      }
      if (rootpath) {
        this.theroot = zipDir.getPath();
      }
      
      String[] dirList = zipDir.list();
      byte[] readBuffer = new byte['࡬'];
      int bytesIn = 0;
      
      for (int i = 0; i < dirList.length; i++) {
        File f = new File(zipDir, dirList[i]);
        if (f.isDirectory())
        {

          String filePath = f.getPath();
          zipDir(filePath, zos, false);

        }
        else
        {

          FileInputStream fis = new FileInputStream(f);
          
          ZipEntry anEntry = new ZipEntry(f.getPath().replace(this.theroot, "data"));
          
          zos.putNextEntry(anEntry);
          
          while ((bytesIn = fis.read(readBuffer)) != -1) {
            zos.write(readBuffer, 0, bytesIn);
          }
          
          fis.close();
        }
      }
    } catch (Exception e) { e.printStackTrace();
    }
  }
}
