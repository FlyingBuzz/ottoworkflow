package com.ottocap.NewWorkFlow.server;

import au.com.bytecode.opencsv.CSVWriter;
import com.extjs.gxt.ui.client.Style.SortDir;
import com.extjs.gxt.ui.client.data.BasePagingLoadConfig;
import com.extjs.gxt.ui.client.data.SortInfo;
import com.ottocap.NewWorkFlow.server.RPCService.ManageOnlineGather;
import com.ottocap.NewWorkFlow.server.RPCService.ManageOrders;
import com.ottocap.NewWorkFlow.server.RPCService.ManageSalesReport;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;





public class OutputCSV
  extends HttpServlet
{
  private static final long serialVersionUID = 1L;
  
  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException
  {
    if (request.getParameter("page").equals("salesreport")) {
      BasePagingLoadConfig loadConfig = new BasePagingLoadConfig();
      loadConfig.set("minprofit", request.getParameter("minprofit"));
      
      if (request.getParameter("orderfrom") != null) {
        loadConfig.set("orderfrom", new Date(Long.valueOf(request.getParameter("orderfrom")).longValue()));
      }
      if (request.getParameter("orderto") != null) {
        loadConfig.set("orderto", new Date(Long.valueOf(request.getParameter("orderto")).longValue()));
      }
      
      loadConfig.set("searchquery", request.getParameter("searchquery"));
      loadConfig.set("managetype", request.getParameter("managetype"));
      
      loadConfig.setSortInfo(new SortInfo(request.getParameter("sortfield"), request.getParameter("sortdir").equals("ASC") ? Style.SortDir.ASC : Style.SortDir.DESC));
      try
      {
        ManageSalesReport thesalesreport = new ManageSalesReport(loadConfig, request.getSession(), true);
        
        response.setContentType("application/x-download");
        response.setHeader("Content-disposition", "attachment; filename=\"" + new SimpleDateFormat("MM.dd.yy-hh.mm.a-").format(new Date()) + "SalesReport.csv\"");
        
        OutputStreamWriter outputwriter = new OutputStreamWriter(response.getOutputStream());
        CSVWriter currentwriter = new CSVWriter(outputwriter);
        currentwriter.writeAll(thesalesreport.getCSVArray());
        currentwriter.close();
      } catch (Exception e) {
        throw new IOException(e);
      }
    } else if (request.getParameter("page").equals("manageorders")) {
      BasePagingLoadConfig loadConfig = new BasePagingLoadConfig();
      
      if (request.getParameter("orderfrom") != null) {
        loadConfig.set("orderfrom", new Date(Long.valueOf(request.getParameter("orderfrom")).longValue()));
      }
      if (request.getParameter("orderto") != null) {
        loadConfig.set("orderto", new Date(Long.valueOf(request.getParameter("orderto")).longValue()));
      }
      
      loadConfig.set("searchquery", request.getParameter("searchquery"));
      loadConfig.set("managetype", request.getParameter("managetype"));
      loadConfig.set("viewall", Boolean.valueOf(request.getParameter("viewall").equals("TRUE")));
      
      loadConfig.setSortInfo(new SortInfo(request.getParameter("sortfield"), request.getParameter("sortdir").equals("ASC") ? Style.SortDir.ASC : Style.SortDir.DESC));
      try
      {
        ManageOrders theorders = new ManageOrders(loadConfig, request.getSession(), true);
        
        response.setContentType("application/x-download");
        response.setHeader("Content-disposition", "attachment; filename=\"" + new SimpleDateFormat("MM.dd.yy-hh.mm.a-").format(new Date()) + "ManageOrders.csv\"");
        
        OutputStreamWriter outputwriter = new OutputStreamWriter(response.getOutputStream());
        CSVWriter currentwriter = new CSVWriter(outputwriter);
        currentwriter.writeAll(theorders.getCSVArray());
        currentwriter.close();
      } catch (Exception e) {
        throw new IOException(e);
      }
    } else if (request.getParameter("page").equals("onlinegather")) {
      BasePagingLoadConfig loadConfig = new BasePagingLoadConfig();
      
      if (request.getParameter("orderfrom") != null) {
        loadConfig.set("orderfrom", new Date(Long.valueOf(request.getParameter("orderfrom")).longValue()));
      }
      if (request.getParameter("orderto") != null) {
        loadConfig.set("orderto", new Date(Long.valueOf(request.getParameter("orderto")).longValue()));
      }
      
      loadConfig.set("searchquery", request.getParameter("searchquery"));
      loadConfig.set("managetype", request.getParameter("managetype"));
      
      loadConfig.setSortInfo(new SortInfo(request.getParameter("sortfield"), request.getParameter("sortdir").equals("ASC") ? Style.SortDir.ASC : Style.SortDir.DESC));
      try
      {
        ManageOnlineGather theorders = new ManageOnlineGather(loadConfig, request.getSession(), true);
        
        response.setContentType("application/x-download");
        response.setHeader("Content-disposition", "attachment; filename=\"" + new SimpleDateFormat("MM.dd.yy-hh.mm.a-").format(new Date()) + "OnlineGather.csv\"");
        
        OutputStreamWriter outputwriter = new OutputStreamWriter(response.getOutputStream());
        CSVWriter currentwriter = new CSVWriter(outputwriter);
        currentwriter.writeAll(theorders.getCSVArray());
        currentwriter.close();
      } catch (Exception e) {
        throw new IOException(e);
      }
    }
  }
}
