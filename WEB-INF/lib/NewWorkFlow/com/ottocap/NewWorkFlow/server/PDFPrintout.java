package com.ottocap.NewWorkFlow.server;

import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderData;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataItem;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataLogo;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataSet;
import com.ottocap.NewWorkFlow.server.ottopdf.OttoPDF;
import java.io.IOException;
import java.util.TreeSet;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class PDFPrintout
  extends HttpServlet
{
  private static final long serialVersionUID = 1L;
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    SQLTable sqltable = new SQLTable();
    sqltable.makeTable("SELECT `ordertype` FROM `ordermain` WHERE `hiddenkey` =" + request.getParameter("hiddenkey") + " LIMIT 1");
    String ordertype = (String)sqltable.getValue();
    if (request.getParameter("printouttype").equals("Quotation")) {
      try {
        OttoPDF mypdf = new OttoPDF(request, response, sqltable);
        if (ordertype.equals("Domestic")) {
          mypdf.orderquotation();
        } else {
          mypdf.overseasquotation();
        }
      } catch (Exception e) {
        e.printStackTrace();
        sqltable.closeSQL();
        throw new ServletException(e);
      }
    } else if (request.getParameter("printouttype").equals("GenericApproval")) {
      try {
        OttoPDF mypdf = new OttoPDF(request, response, sqltable);
        if (ordertype.equals("Domestic")) {
          mypdf.genericapproval();
        } else {
          mypdf.overseasgenericapproval();
        }
      } catch (Exception e) {
        e.printStackTrace();
        sqltable.closeSQL();
        throw new ServletException(e);
      }
    } else if (request.getParameter("printouttype").equals("ProductionApproval")) {
      try {
        OrderExpander theorderexpander = new OrderExpander(Integer.valueOf(request.getParameter("hiddenkey")).intValue(), request.getSession(), sqltable);
        ExtendOrderData theorder = theorderexpander.getExpandedOrderData();
        boolean haveproduction = false;
        for (int i = 0; i < theorder.getSetCount(); i++) {
          for (int j = 0; j < theorder.getSet(i).getItemCount(); j++) {
            if (((theorder.getSet(i).getItem(j).getProductSampleTotalDone() != null) && (theorder.getSet(i).getItem(j).getProductSampleTotalDone().intValue() > 0)) || ((theorder.getSet(i).getItem(j).getProductSampleTotalShip() != null) && (theorder.getSet(i).getItem(j).getProductSampleTotalShip().intValue() > 0)) || (
              (theorder.getSet(i).getItem(j).getProductSampleTotalEmail() != null) && (theorder.getSet(i).getItem(j).getProductSampleTotalEmail().intValue() > 0))) {
              haveproduction = true;
              i = theorder.getSetCount() - 1;
              j = theorder.getSet(i).getItemCount();
            }
          }
          if (!haveproduction) {
            for (int j = 0; j < theorder.getSet(i).getLogoCount(); j++) {
              if (((theorder.getSet(i).getLogo(j).getSwatchTotalDone() != null) && (theorder.getSet(i).getLogo(j).getSwatchTotalDone().intValue() > 0)) || ((theorder.getSet(i).getLogo(j).getSwatchTotalShip() != null) && (theorder.getSet(i).getLogo(j).getSwatchTotalShip().intValue() > 0)) || ((theorder.getSet(i).getLogo(j).getSwatchTotalEmail() != null) && (theorder.getSet(i).getLogo(j).getSwatchTotalEmail().intValue() > 0))) {
                haveproduction = true;
                i = theorder.getSetCount() - 1;
                j = theorder.getSet(i).getLogoCount();
              }
            }
          }
        }
        
        if (haveproduction) {
          OttoPDF mypdf = new OttoPDF(request, response, sqltable);
          if (ordertype.equals("Domestic")) {
            mypdf.productionapproval();
          } else {
            mypdf.overseasproductionapproval();
          }
        } else {
          response.getOutputStream().println("No Production Approval");
        }
      } catch (Exception e) {
        e.printStackTrace();
        sqltable.closeSQL();
        throw new ServletException(e);
      }
    } else if (request.getParameter("printouttype").equals("Approval")) {
      try {
        OttoPDF mypdf = new OttoPDF(request, response, sqltable);
        if (ordertype.equals("Domestic")) {
          mypdf.orderapproval();
        } else {
          mypdf.overseasapproval();
        }
      } catch (Exception e) {
        e.printStackTrace();
        sqltable.closeSQL();
        throw new ServletException(e);
      }
    } else if (request.getParameter("printouttype").equals("Digitizing")) {
      try {
        if (ordertype.equals("Domestic")) {
          OrderExpander theorderexpander = new OrderExpander(Integer.valueOf(request.getParameter("hiddenkey")).intValue(), request.getSession(), sqltable);
          ExtendOrderData theorder = theorderexpander.getExpandedOrderData();
          boolean havedigitizing = false;
          for (int i = 0; i < theorder.getSetCount(); i++) {
            if (!havedigitizing) {
              for (int j = 0; j < theorder.getSet(i).getLogoCount(); j++) {
                if ((theorder.getSet(i).getLogo(j).getDigitizing()) || (theorder.getSet(i).getLogo(j).getTapeEdit())) {
                  havedigitizing = true;
                  i = theorder.getSetCount() - 1;
                  j = theorder.getSet(i).getLogoCount();
                }
              }
            }
          }
          
          if (havedigitizing) {
            OttoPDF mypdf = new OttoPDF(request, response, sqltable);
            mypdf.digitizingorder();
          } else {
            response.getOutputStream().println("No Digitizing WorkOrder");
          }
          
        }
        else if (request.getParameter("vendernumber") != null) {
          OttoPDF mypdf = new OttoPDF(request, response, sqltable);
          int vendernumber = Integer.valueOf(request.getParameter("vendernumber")).intValue();
          mypdf.overseasdigitizingorder(vendernumber);
        } else {
          OrderExpander theorderexpander = new OrderExpander(Integer.valueOf(request.getParameter("hiddenkey")).intValue(), request.getSession(), sqltable);
          ExtendOrderData theorder = theorderexpander.getExpandedOrderData();
          
          TreeSet<Integer> vendorhasdig = new TreeSet();
          
          for (int i = 0; i < theorder.getSetCount(); i++) {
            for (int j = 0; j < theorder.getSet(i).getItemCount(); j++) {
              for (int k = 0; k < theorder.getSet(i).getLogoCount(); k++) {
                if ((theorder.getSet(i).getLogo(k).getDigitizing()) || (theorder.getSet(i).getLogo(k).getTapeEdit())) {
                  vendorhasdig.add(Integer.valueOf(theorder.getSet(i).getItem(j).getOverseasVendorNumber()));
                }
              }
            }
          }
          
          Integer[] myvendors = (Integer[])vendorhasdig.toArray(new Integer[0]);
          
          if (myvendors.length == 1) {
            response.sendRedirect(response.encodeRedirectURL("?hiddenkey=" + request.getParameter("hiddenkey") + "&printouttype=" + request.getParameter("printouttype") + "&vendernumber=" + myvendors[0]));
          } else if (myvendors.length == 0) {
            response.getOutputStream().println("No Digitizing WorkOrder");
          } else {
            for (int i = 0; i < myvendors.length; i++) {
              sqltable.makeTable("SELECT `name` FROM `list_vendors_overseas` WHERE `id` =" + myvendors[i] + " LIMIT 1");
              response.getOutputStream().println("<a href=\"?hiddenkey=" + request.getParameter("hiddenkey") + "&printouttype=" + request.getParameter("printouttype") + "&vendernumber=" + myvendors[i] + "\">Print Digitizing WorkOrder For " + sqltable.getValue() + "</a><BR>");
            }
            
          }
        }
      }
      catch (Exception e)
      {
        e.printStackTrace();
        sqltable.closeSQL();
        throw new ServletException(e);
      }
    } else if (request.getParameter("printouttype").equals("SampleOrder")) {
      try {
        if (ordertype.equals("Domestic")) {
          if (request.getParameter("printouttypeoption") != null) {
            OttoPDF mypdf = new OttoPDF(request, response, sqltable);
            if (request.getParameter("printouttypeoption").equals("EMB")) {
              mypdf.embsampleorder();
            } else if (request.getParameter("printouttypeoption").equals("DTG")) {
              mypdf.directtogarmentsampleorder();
            } else if (request.getParameter("printouttypeoption").equals("HEAT")) {
              mypdf.heatsampleorder();
            } else if (request.getParameter("printouttypeoption").equals("CAD")) {
              mypdf.cadsampleorder();
            } else if (request.getParameter("printouttypeoption").equals("SCREEN")) {
              mypdf.screensampleorder();
            } else if (request.getParameter("printouttypeoption").equals("PATCH")) {
              mypdf.patchsampleorder();
            }
          } else {
            OrderExpander theorderexpander = new OrderExpander(Integer.valueOf(request.getParameter("hiddenkey")).intValue(), request.getSession(), sqltable);
            ExtendOrderData theorder = theorderexpander.getExpandedOrderData();
            boolean embsamplefound = false;
            boolean heatsamplefound = false;
            boolean cadsamplefound = false;
            boolean screensamplefound = false;
            boolean dtgsamplefound = false;
            boolean patchsamplefound = false;
            
            for (int i = 0; i < theorder.getSetCount(); i++) {
              for (int j = 0; j < theorder.getSet(i).getItemCount(); j++) {
                boolean hasproductsampletodo = false;
                if ((theorder.getSet(i).getItem(j).getProductSampleToDo() != null) && (theorder.getSet(i).getItem(j).getProductSampleToDo().intValue() > 0)) {
                  hasproductsampletodo = true;
                }
                for (int k = 0; k < theorder.getSet(i).getLogoCount(); k++) {
                  if ((hasproductsampletodo) || ((theorder.getSet(i).getLogo(k).getSwatchToDo() != null) && (theorder.getSet(i).getLogo(k).getSwatchToDo().intValue() > 0))) {
                    if (theorder.getSet(i).getLogo(k).getDecoration().equals("Direct To Garment")) {
                      dtgsamplefound = true;
                    } else if (theorder.getSet(i).getLogo(k).getDecoration().equals("Heat Transfer")) {
                      heatsamplefound = true;
                    } else if (theorder.getSet(i).getLogo(k).getDecoration().equals("CAD Print")) {
                      cadsamplefound = true;
                    } else if ((theorder.getSet(i).getLogo(k).getDecoration().equals("Screen Print")) || (theorder.getSet(i).getLogo(k).getDecoration().equals("Four Color Screen Print"))) {
                      screensamplefound = true;
                    } else if (theorder.getSet(i).getLogo(k).getDecoration().equals("Patch")) {
                      patchsamplefound = true;
                    } else {
                      embsamplefound = true;
                    }
                  }
                }
              }
            }
            
            int foundcount = 0;
            foundcount += (embsamplefound ? 1 : 0);
            foundcount += (heatsamplefound ? 1 : 0);
            foundcount += (cadsamplefound ? 1 : 0);
            foundcount += (screensamplefound ? 1 : 0);
            foundcount += (dtgsamplefound ? 1 : 0);
            foundcount += (patchsamplefound ? 1 : 0);
            
            if (foundcount == 1) {
              if (embsamplefound) {
                response.sendRedirect(response.encodeRedirectURL("?hiddenkey=" + request.getParameter("hiddenkey") + "&printouttype=" + request.getParameter("printouttype") + "&printouttypeoption=EMB"));
              } else if (heatsamplefound) {
                response.sendRedirect(response.encodeRedirectURL("?hiddenkey=" + request.getParameter("hiddenkey") + "&printouttype=" + request.getParameter("printouttype") + "&printouttypeoption=HEAT"));
              } else if (cadsamplefound) {
                response.sendRedirect(response.encodeRedirectURL("?hiddenkey=" + request.getParameter("hiddenkey") + "&printouttype=" + request.getParameter("printouttype") + "&printouttypeoption=CAD"));
              } else if (screensamplefound) {
                response.sendRedirect(response.encodeRedirectURL("?hiddenkey=" + request.getParameter("hiddenkey") + "&printouttype=" + request.getParameter("printouttype") + "&printouttypeoption=SCREEN"));
              } else if (dtgsamplefound) {
                response.sendRedirect(response.encodeRedirectURL("?hiddenkey=" + request.getParameter("hiddenkey") + "&printouttype=" + request.getParameter("printouttype") + "&printouttypeoption=DTG"));
              } else if (patchsamplefound) {
                response.sendRedirect(response.encodeRedirectURL("?hiddenkey=" + request.getParameter("hiddenkey") + "&printouttype=" + request.getParameter("printouttype") + "&printouttypeoption=PATCH"));
              }
            } else {
              if (embsamplefound) {
                response.getOutputStream().println("<a href=\"?hiddenkey=" + request.getParameter("hiddenkey") + "&printouttype=" + request.getParameter("printouttype") + "&printouttypeoption=EMB\">Emb</a><BR>");
              }
              if (heatsamplefound) {
                response.getOutputStream().println("<a href=\"?hiddenkey=" + request.getParameter("hiddenkey") + "&printouttype=" + request.getParameter("printouttype") + "&printouttypeoption=HEAT\">Heat</a><BR>");
              }
              if (cadsamplefound) {
                response.getOutputStream().println("<a href=\"?hiddenkey=" + request.getParameter("hiddenkey") + "&printouttype=" + request.getParameter("printouttype") + "&printouttypeoption=CAD\">CAD</a><BR>");
              }
              if (screensamplefound) {
                response.getOutputStream().println("<a href=\"?hiddenkey=" + request.getParameter("hiddenkey") + "&printouttype=" + request.getParameter("printouttype") + "&printouttypeoption=SCREEN\">Screen</a><BR>");
              }
              if (dtgsamplefound) {
                response.getOutputStream().println("<a href=\"?hiddenkey=" + request.getParameter("hiddenkey") + "&printouttype=" + request.getParameter("printouttype") + "&printouttypeoption=DTG\">DTG</a><BR>");
              }
              if (patchsamplefound) {
                response.getOutputStream().println("<a href=\"?hiddenkey=" + request.getParameter("hiddenkey") + "&printouttype=" + request.getParameter("printouttype") + "&printouttypeoption=PATCH\">PATCH</a><BR>");
              }
            }
          }
        }
        else if (request.getParameter("vendernumber") != null) {
          OttoPDF mypdf = new OttoPDF(request, response, sqltable);
          int vendernumber = Integer.valueOf(request.getParameter("vendernumber")).intValue();
          mypdf.overseassampleorder(vendernumber);
        } else {
          OrderExpander theorderexpander = new OrderExpander(Integer.valueOf(request.getParameter("hiddenkey")).intValue(), request.getSession(), sqltable);
          ExtendOrderData theorder = theorderexpander.getExpandedOrderData();
          
          TreeSet<Integer> vendorhassample = new TreeSet();
          
          for (int i = 0; i < theorder.getSetCount(); i++) {
            for (int j = 0; j < theorder.getSet(i).getItemCount(); j++) {
              if ((theorder.getSet(i).getItem(j).getProductSampleToDo() != null) && (theorder.getSet(i).getItem(j).getProductSampleToDo().intValue() > 0)) {
                vendorhassample.add(Integer.valueOf(theorder.getSet(i).getItem(j).getOverseasVendorNumber()));
              }
              for (int k = 0; k < theorder.getSet(i).getLogoCount(); k++) {
                if ((theorder.getSet(i).getLogo(k).getSwatchToDo() != null) && (theorder.getSet(i).getLogo(k).getSwatchToDo().intValue() > 0)) {
                  vendorhassample.add(Integer.valueOf(theorder.getSet(i).getItem(j).getOverseasVendorNumber()));
                }
              }
            }
          }
          
          Integer[] myvendors = (Integer[])vendorhassample.toArray(new Integer[0]);
          
          if (myvendors.length == 1) {
            response.sendRedirect(response.encodeRedirectURL("?hiddenkey=" + request.getParameter("hiddenkey") + "&printouttype=" + request.getParameter("printouttype") + "&vendernumber=" + myvendors[0]));
          } else if (myvendors.length == 0) {
            response.getOutputStream().println("No Sample Order");
          } else {
            for (int i = 0; i < myvendors.length; i++) {
              sqltable.makeTable("SELECT `name` FROM `list_vendors_overseas` WHERE `id` =" + myvendors[i] + " LIMIT 1");
              response.getOutputStream().println("<a href=\"?hiddenkey=" + request.getParameter("hiddenkey") + "&printouttype=" + request.getParameter("printouttype") + "&vendernumber=" + myvendors[i] + "\">Print Sample Order For " + sqltable.getValue() + "</a><BR>");
            }
            
          }
        }
      }
      catch (Exception e)
      {
        e.printStackTrace();
        sqltable.closeSQL();
        throw new ServletException(e);
      }
    } else if (request.getParameter("printouttype").equals("Order")) {
      try {
        if (ordertype.equals("Domestic")) {
          if (request.getParameter("printouttypeoption") != null) {
            OttoPDF mypdf = new OttoPDF(request, response, sqltable);
            if (request.getParameter("printouttypeoption").equals("EMB")) {
              mypdf.emborder();
            } else if (request.getParameter("printouttypeoption").equals("DTG")) {
              mypdf.directtogarmentorder();
            } else if (request.getParameter("printouttypeoption").equals("HEAT")) {
              mypdf.heatorder();
            } else if (request.getParameter("printouttypeoption").equals("CAD")) {
              mypdf.cadorder();
            } else if (request.getParameter("printouttypeoption").equals("SCREEN")) {
              mypdf.screenorder();
            } else if (request.getParameter("printouttypeoption").equals("PATCH")) {
              mypdf.patchorder();
            }
          }
          else {
            OrderExpander theorderexpander = new OrderExpander(Integer.valueOf(request.getParameter("hiddenkey")).intValue(), request.getSession(), sqltable);
            ExtendOrderData theorder = theorderexpander.getExpandedOrderData();
            boolean embfound = false;
            boolean heatfound = false;
            boolean cadfound = false;
            boolean screenfound = false;
            boolean patchfound = false;
            boolean dtgfound = false;
            
            for (int i = 0; i < theorder.getSetCount(); i++) {
              for (int j = 0; j < theorder.getSet(i).getLogoCount(); j++) {
                if (theorder.getSet(i).getLogo(j).getDecoration().equals("Direct To Garment")) {
                  dtgfound = true;
                } else if (theorder.getSet(i).getLogo(j).getDecoration().equals("Heat Transfer")) {
                  heatfound = true;
                } else if (theorder.getSet(i).getLogo(j).getDecoration().equals("CAD Print")) {
                  cadfound = true;
                } else if ((theorder.getSet(i).getLogo(j).getDecoration().equals("Screen Print")) || (theorder.getSet(i).getLogo(j).getDecoration().equals("Four Color Screen Print"))) {
                  screenfound = true;
                } else if (theorder.getSet(i).getLogo(j).getDecoration().equals("Patch")) {
                  patchfound = true;
                } else {
                  embfound = true;
                }
              }
            }
            
            int foundcount = 0;
            foundcount += (embfound ? 1 : 0);
            foundcount += (heatfound ? 1 : 0);
            foundcount += (cadfound ? 1 : 0);
            foundcount += (screenfound ? 1 : 0);
            foundcount += (dtgfound ? 1 : 0);
            foundcount += (patchfound ? 1 : 0);
            
            if (foundcount == 1) {
              if (embfound) {
                response.sendRedirect(response.encodeRedirectURL("?hiddenkey=" + request.getParameter("hiddenkey") + "&printouttype=" + request.getParameter("printouttype") + "&printouttypeoption=EMB"));
              } else if (heatfound) {
                response.sendRedirect(response.encodeRedirectURL("?hiddenkey=" + request.getParameter("hiddenkey") + "&printouttype=" + request.getParameter("printouttype") + "&printouttypeoption=HEAT"));
              } else if (cadfound) {
                response.sendRedirect(response.encodeRedirectURL("?hiddenkey=" + request.getParameter("hiddenkey") + "&printouttype=" + request.getParameter("printouttype") + "&printouttypeoption=CAD"));
              } else if (screenfound) {
                response.sendRedirect(response.encodeRedirectURL("?hiddenkey=" + request.getParameter("hiddenkey") + "&printouttype=" + request.getParameter("printouttype") + "&printouttypeoption=SCREEN"));
              } else if (dtgfound) {
                response.sendRedirect(response.encodeRedirectURL("?hiddenkey=" + request.getParameter("hiddenkey") + "&printouttype=" + request.getParameter("printouttype") + "&printouttypeoption=DTG"));
              } else if (patchfound) {
                response.sendRedirect(response.encodeRedirectURL("?hiddenkey=" + request.getParameter("hiddenkey") + "&printouttype=" + request.getParameter("printouttype") + "&printouttypeoption=PATCH"));
              }
            } else {
              if (embfound) {
                response.getOutputStream().println("<a href=\"?hiddenkey=" + request.getParameter("hiddenkey") + "&printouttype=" + request.getParameter("printouttype") + "&printouttypeoption=EMB\">Emb</a><BR>");
              }
              if (heatfound) {
                response.getOutputStream().println("<a href=\"?hiddenkey=" + request.getParameter("hiddenkey") + "&printouttype=" + request.getParameter("printouttype") + "&printouttypeoption=HEAT\">Heat</a><BR>");
              }
              if (cadfound) {
                response.getOutputStream().println("<a href=\"?hiddenkey=" + request.getParameter("hiddenkey") + "&printouttype=" + request.getParameter("printouttype") + "&printouttypeoption=CAD\">CAD</a><BR>");
              }
              if (screenfound) {
                response.getOutputStream().println("<a href=\"?hiddenkey=" + request.getParameter("hiddenkey") + "&printouttype=" + request.getParameter("printouttype") + "&printouttypeoption=SCREEN\">Screen</a><BR>");
              }
              if (dtgfound) {
                response.getOutputStream().println("<a href=\"?hiddenkey=" + request.getParameter("hiddenkey") + "&printouttype=" + request.getParameter("printouttype") + "&printouttypeoption=DTG\">DTG</a><BR>");
              }
              if (patchfound) {
                response.getOutputStream().println("<a href=\"?hiddenkey=" + request.getParameter("hiddenkey") + "&printouttype=" + request.getParameter("printouttype") + "&printouttypeoption=PATCH\">PATCH</a><BR>");
              }
            }
          }
        }
        else if (request.getParameter("vendernumber") != null) {
          OttoPDF mypdf = new OttoPDF(request, response, sqltable);
          int vendernumber = Integer.valueOf(request.getParameter("vendernumber")).intValue();
          mypdf.overseaspurchaseorder(vendernumber);
        } else {
          OrderExpander theorderexpander = new OrderExpander(Integer.valueOf(request.getParameter("hiddenkey")).intValue(), request.getSession(), sqltable);
          ExtendOrderData theorder = theorderexpander.getExpandedOrderData();
          
          TreeSet<Integer> vendorhassample = new TreeSet();
          
          for (int i = 0; i < theorder.getSetCount(); i++) {
            for (int j = 0; j < theorder.getSet(i).getItemCount(); j++) {
              vendorhassample.add(Integer.valueOf(theorder.getSet(i).getItem(j).getOverseasVendorNumber()));
            }
          }
          
          Integer[] myvendors = (Integer[])vendorhassample.toArray(new Integer[0]);
          
          if (myvendors.length == 1) {
            response.sendRedirect(response.encodeRedirectURL("?hiddenkey=" + request.getParameter("hiddenkey") + "&printouttype=" + request.getParameter("printouttype") + "&vendernumber=" + myvendors[0]));
          } else if (myvendors.length == 0) {
            response.getOutputStream().println("No Order");
          } else {
            for (int i = 0; i < myvendors.length; i++) {
              sqltable.makeTable("SELECT `name` FROM `list_vendors_overseas` WHERE `id` =" + myvendors[i] + " LIMIT 1");
              response.getOutputStream().println("<a href=\"?hiddenkey=" + request.getParameter("hiddenkey") + "&printouttype=" + request.getParameter("printouttype") + "&vendernumber=" + myvendors[i] + "\">Print Order For " + sqltable.getValue() + "</a><BR>");
            }
          }
        }
      }
      catch (Exception e)
      {
        e.printStackTrace();
        sqltable.closeSQL();
        throw new ServletException(e);
      }
    } else if (request.getParameter("printouttype").equals("ArtworkRequest")) {
      try {
        OttoPDF mypdf = new OttoPDF(request, response, sqltable);
        if (ordertype.equals("Domestic")) {
          mypdf.artworkrequest();
        } else {
          mypdf.overseasartworkrequest();
        }
      } catch (Exception e) {
        e.printStackTrace();
        sqltable.closeSQL();
        throw new ServletException(e);
      }
    }
    
    sqltable.closeSQL();
  }
}
