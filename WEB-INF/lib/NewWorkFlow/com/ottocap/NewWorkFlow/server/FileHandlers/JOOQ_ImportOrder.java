package com.ottocap.NewWorkFlow.server.FileHandlers;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData.OrderType;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataShared;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataVendorInformation;
import com.ottocap.NewWorkFlow.client.OrderData.OrderDataVendors;
import com.ottocap.NewWorkFlow.server.JSONHelper;
import com.ottocap.NewWorkFlow.server.RPCService.JOOQ_SaveOrder;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;

public class JOOQ_ImportOrder
  extends Thread
{
  private FileItem uploadedfile;
  private String employeeid;
  private HttpServletRequest request;
  private HttpServletResponse response;
  private OrderData neworderdata;
  
  public JOOQ_ImportOrder(List<FileItem> items, HttpServletRequest request, HttpServletResponse response) throws Exception
  {
    this.request = request;
    this.response = response;
    int foundfile = 0;
    
    foundfile = items.size();
    
    if (items != null) {
      for (int i = 0; i < items.size(); i++) {
        if (((FileItem)items.get(i)).isFormField()) {
          if (((FileItem)items.get(i)).getFieldName().equals("employeeid")) {
            this.employeeid = ((FileItem)items.get(i)).getString();
          }
        }
        else if (((FileItem)items.get(i)).getFieldName().equals("orderdatafile")) {
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
    
    if (this.uploadedfile.getSize() != 0L) {
      if (!this.uploadedfile.getName().endsWith(".zip")) {
        doError("Must Upload Zip File");
        return;
      }
      OrderData orderdata = getOrderData();
      if (orderdata == null) {
        doError("Invalid Zip File");
        return;
      }
      orderdata.setHiddenKey(null);
      orderdata.setEmployeeId(this.employeeid);
      this.neworderdata = storeOrderData(orderdata);
      storeOrderFiles();
    }
    else
    {
      doError("File Does Not Exist");
      return;
    }
    try
    {
      JsonFactory jsonfactory = new JsonFactory();
      JsonGenerator generator = jsonfactory.createJsonGenerator(response.getOutputStream(), JsonEncoding.UTF8);
      generator.writeStartObject();
      JSONHelper.addObject(generator, "hiddenkey", this.neworderdata.getHiddenKey());
      generator.writeEndObject();
      generator.flush();
      generator.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  private OrderData storeOrderData(OrderData orderdata) throws Exception
  {
    if ((orderdata.getOrderStatus().equals("Exported New Quotation")) || (orderdata.getOrderStatus().equals("New Quotation"))) {
      orderdata.setOrderStatus("New Quotation");
      orderdata.setQuoteToOrder("quote");
    } else if ((orderdata.getOrderStatus().equals("Exported New Order")) || (orderdata.getOrderStatus().equals("New Order"))) {
      orderdata.setOrderStatus("New Order");
    } else if ((orderdata.getOrderStatus().equals("Exported New Virtual Sample Request")) || (orderdata.getOrderStatus().equals("New Virtual Sample Request"))) {
      orderdata.setOrderStatus("New Virtual Sample Request");
    }
    try
    {
      DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
      Date ordershipdate = df.parse((String)orderdata.get("ordershipdate"));
      orderdata.setOrderShipDate(ordershipdate);
    }
    catch (Exception localException) {}
    
    JOOQ_SaveOrder savedorder = new JOOQ_SaveOrder(orderdata, this.request.getSession());
    return savedorder.getOrder();
  }
  
  private void storeOrderFiles() throws Exception {
    ZipInputStream inputfile = new ZipInputStream(this.uploadedfile.getInputStream());
    ZipEntry entry = inputfile.getNextEntry();
    while (entry != null)
    {
      if ((!entry.getName().equals("orderdata.dat")) || (!entry.getName().equals("orderdata.txt")))
      {
        byte[] buf = new byte['Ѐ'];
        


        String currentpath = entry.getName().replaceAll("/", "\\\\").replaceAll("\\?", "_");
        String newpath;
        String newpath; if (currentpath.contains("data\\")) {
          newpath = currentpath.replace("data\\", (this.neworderdata.getOrderType() == OrderData.OrderType.DOMESTIC ? "Domestic" : "Overseas") + "Data\\" + this.neworderdata.getHiddenKey() + "\\");
        } else {
          newpath = (this.neworderdata.getOrderType() == OrderData.OrderType.DOMESTIC ? "Domestic" : "Overseas") + "Data\\" + this.neworderdata.getHiddenKey() + "\\" + currentpath;
        }
        File myfile = new File("C:\\WorkFlow\\NewWorkflowData\\" + newpath);
        myfile.getParentFile().mkdirs();
        FileOutputStream myorderdata = new FileOutputStream("C:\\WorkFlow\\NewWorkflowData\\" + newpath);
        int len;
        while ((len = inputfile.read(buf)) > 0) { int len;
          myorderdata.write(buf, 0, len);
        }
        
        myorderdata.close();
      }
      entry = inputfile.getNextEntry();
    }
    inputfile.close();
  }
  
  private OrderData getOrderData()
  {
    try {
      ZipInputStream inputfile = new ZipInputStream(this.uploadedfile.getInputStream());
      ZipEntry entry = inputfile.getNextEntry();
      while (entry != null)
      {
        if (entry.getName().equals("orderdata.dat"))
        {








          ObjectInputStream ois = new ObjectInputStream(inputfile);
          
          ObjectMapper mapper = new ObjectMapper();
          

          mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
          OrderData readorderdata = (OrderData)mapper.readValue(ois, OrderData.class);
          fixDates(readorderdata);
          
          ois.close();
          inputfile.close();
          
          return readorderdata;
        }
        entry = inputfile.getNextEntry();
      }
      inputfile.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    return null;
  }
  
  private void fixDate(OrderDataShared data, String field)
  {
    if ((data.get(field) instanceof String)) {
      try {
        SimpleDateFormat theformatter = new SimpleDateFormat("yyyy-MM-dd");
        data.set(field, theformatter.parse((String)data.get(field)));
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else if ((data.get(field) instanceof Long)) {
      data.set(field, new Date(((Long)data.get(field)).longValue()));
    }
  }
  
  private void fixVendorDate(OrderDataVendorInformation vendor)
  {
    fixDate(vendor, "digitizingprocessingdate");
    fixDate(vendor, "digitizingduedate");
    fixDate(vendor, "workorderprocessingdate");
    fixDate(vendor, "workorderduedate");
  }
  
  private void fixDates(OrderData orderdata)
  {
    fixDate(orderdata, "orderdate");
    fixDate(orderdata, "inhanddate");
    fixDate(orderdata, "estimatedshipdate");
    fixDate(orderdata, "internalduedatetime");
    fixDate(orderdata, "potentialrepeatdate");
    
    fixVendorDate(orderdata.getVendorInformation().getDigitizerVendor());
    fixVendorDate(orderdata.getVendorInformation().getEmbroideryVendor());
    fixVendorDate(orderdata.getVendorInformation().getHeatTransferVendor());
    fixVendorDate(orderdata.getVendorInformation().getPatchVendor());
    fixVendorDate(orderdata.getVendorInformation().getDirectToGarmentVendor());
    fixVendorDate(orderdata.getVendorInformation().getCADPrintVendor());
    fixVendorDate(orderdata.getVendorInformation().getScreenPrintVendor());
    
    String[] overseaskeys = (String[])orderdata.getVendorInformation().getOverseasVendor().keySet().toArray(new String[0]);
    for (int i = 0; i < overseaskeys.length; i++) {
      fixVendorDate((OrderDataVendorInformation)orderdata.getVendorInformation().getOverseasVendor().get(overseaskeys[i]));
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
        if ((((Integer)this.request.getSession().getAttribute("level")).intValue() == 0) || (((Integer)this.request.getSession().getAttribute("level")).intValue() == 1) || (((Integer)this.request.getSession().getAttribute("level")).intValue() == 3))
        {
          this.employeeid = ((String)this.request.getSession().getAttribute("username"));
        } else
          throw new Exception("Bad Session");
      }
    } else {
      throw new Exception("Session Expired");
    }
  }
}
