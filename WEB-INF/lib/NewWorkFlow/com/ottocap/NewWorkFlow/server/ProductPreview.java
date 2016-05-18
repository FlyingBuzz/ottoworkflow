package com.ottocap.NewWorkFlow.server;

import com.ottocap.NewWorkFlow.client.StyleInformationData.StyleType;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataItem;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataItem.SideView;
import com.ottocap.NewWorkFlow.server.ISampleGenerator.ISampleDomesticLayout;
import com.ottocap.NewWorkFlow.server.ISampleGenerator.ISampleLineArtDrawer;
import com.ottocap.NewWorkFlow.server.ISampleGenerator.ISampleOverseasLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class ProductPreview
  extends HttpServlet
{
  private static final long serialVersionUID = 1L;
  
  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException
  {
    ExtendOrderDataItem capdata = new ExtendOrderDataItem();
    capdata.setStyleNumber(request.getParameter("stylenumber"));
    capdata.setColorCode(request.getParameter("colorcode"));
    capdata.setFrontPanelColor(request.getParameter("frontpanelcolor"));
    capdata.setSideBackPanelColor(request.getParameter("sidebackpanelcolor"));
    capdata.setBackPanelColor(request.getParameter("backpanelcolor"));
    capdata.setSidePanelColor(request.getParameter("sidepanelcolor"));
    capdata.setButtonColor(request.getParameter("buttoncolor"));
    capdata.setInnerTapingColor(request.getParameter("innertapingcolor"));
    capdata.setVisorStyleNumber(request.getParameter("visorstylenumber"));
    capdata.setPrimaryVisorColor(request.getParameter("primaryvisorcolor"));
    capdata.setVisorTrimColor(request.getParameter("visortrimcolor"));
    capdata.setVisorSandwichColor(request.getParameter("visorsandwichcolor"));
    capdata.setUndervisorColor(request.getParameter("undervisorcolor"));
    capdata.setDistressedVisorInsideColor(request.getParameter("distressedvisorinsidecolor"));
    capdata.setClosureStyleNumber(request.getParameter("closurestylenumber"));
    capdata.setClosureStrapColor(request.getParameter("closurestrapcolor"));
    capdata.setEyeletStyleNumber(request.getParameter("eyeletstylenumber"));
    capdata.setFrontEyeletColor(request.getParameter("fronteyeletcolor"));
    capdata.setSideBackEyeletColor(request.getParameter("sidebackeyeletcolor"));
    capdata.setBackEyeletColor(request.getParameter("backeyeletcolor"));
    capdata.setSideEyeletColor(request.getParameter("sideeyeletcolor"));
    capdata.setSweatbandStyleNumber(request.getParameter("sweatbandstylenumber"));
    capdata.setSweatbandColor(request.getParameter("sweatbandcolor"));
    capdata.setSweatbandStripeColor(request.getParameter("sweatbandstripecolor"));
    
    capdata.setPanelStitchColor(request.getParameter("panelstitchcolor"));
    capdata.setVisorRowStitching(request.getParameter("visorrowstitching"));
    capdata.setVisorStitchColor(request.getParameter("visorstitchcolor"));
    capdata.setProfile(request.getParameter("profile"));
    capdata.setFrontPanelFabric(request.getParameter("frontpanelfabric"));
    capdata.setSideBackPanelFabric(request.getParameter("sidebackpanelfabric"));
    capdata.setBackPanelFabric(request.getParameter("backpanelfabric"));
    capdata.setSidePanelFabric(request.getParameter("sidepanelfabric"));
    capdata.setNumberOfPanels(request.getParameter("numberofpanels"));
    capdata.setCrownConstruction(request.getParameter("crownconstruction"));
    
    String profile = request.getParameter("profile");
    if ((profile != null) && ((profile.equals("Beanie")) || (profile.equals("Head Band")) || (profile.equals("Sun Visor")))) {
      capdata.setCategory("Accessory Item");
    }
    

    if ((request.getParameter("styletype") != null) && (!request.getParameter("styletype").equals(""))) {
      capdata.setStyleType(StyleInformationData.StyleType.valueOf(request.getParameter("styletype")));
    }
    capdata.setCategory(request.getParameter("category"));
    
    boolean showlayout = request.getParameter("showlayout") != null;
    
    if (request.getParameter("ordertype").equals("Domestic"))
    {
      if (request.getParameter("sideview") != null)
      {
        capdata.setSideView(ExtendOrderDataItem.SideView.valueOf(request.getParameter("sideview")));
        try {
          ISampleLineArtDrawer backart = new ISampleLineArtDrawer(capdata, null);
          backart.showPNGImage(response);
          return;
        }
        catch (Exception localException) {}
      }
      else
      {
        ISampleDomesticLayout domesticlayout = new ISampleDomesticLayout(capdata);
        try {
          domesticlayout.drawImage();
        }
        catch (Exception localException1) {}
        if ((!domesticlayout.isEmptyImage()) || (showlayout)) {
          try {
            if ((request.getSession() != null) && (request.getSession().getAttribute("level") != null) && (((Integer)request.getSession().getAttribute("level")).intValue() == 0)) {
              domesticlayout.showQuoteImage(response);
            } else {
              domesticlayout.showImage(response);
            }
            return;

          }
          catch (Exception localException2) {}
        }
        
      }
    }
    else if (request.getParameter("sideview") != null) {
      capdata.setSideView(ExtendOrderDataItem.SideView.valueOf(request.getParameter("sideview")));
      try {
        ISampleLineArtDrawer backart = new ISampleLineArtDrawer(capdata, null);
        backart.showPNGImage(response);
        return;

      }
      catch (Exception localException3) {}

    }
    else
    {
      ISampleOverseasLayout overseaslayout = new ISampleOverseasLayout(capdata);
      try {
        overseaslayout.drawImage();
      }
      catch (Exception localException4) {}
      
      if ((!overseaslayout.isEmptyImage()) || (showlayout)) {
        try
        {
          if ((request.getSession() != null) && (request.getSession().getAttribute("level") != null) && (((Integer)request.getSession().getAttribute("level")).intValue() == 0)) {
            overseaslayout.showQuoteImage(response);
          } else {
            overseaslayout.showImage(response);
          }
          
          return;
        }
        catch (Exception localException5) {}
      }
    }
    


    String filelocation = "C:\\WorkFlow\\NewWorkflowData\\images/caps/na.jpg";
    if ((request.getParameter("styletype").equals("NONOTTO")) || (request.getParameter("styletype").equals("OVERSEAS_NONOTTO")) || (request.getParameter("styletype").equals("OVERSEAS_NONOTTO_SHIRTS"))) {
      filelocation = "C:\\WorkFlow\\NewWorkflowData\\images/caps/nonotto.jpg";
    } else if (request.getParameter("styletype").equals("")) {
      filelocation = "C:\\WorkFlow\\NewWorkflowData\\images/caps/na.jpg";
    } else {
      filelocation = "C:\\WorkFlow\\NewWorkflowData\\images/caps/nopreview.jpg";
    }
    
    File picturefile = new File(filelocation);
    if (!picturefile.exists()) {
      picturefile = new File("C:\\WorkFlow\\NewWorkflowData\\images/caps/nopreview.jpg");
    }
    
    response.setContentType("image/jpeg");
    BufferedImage newimage = ImageIO.read(picturefile);
    ImageIO.write(newimage, "jpeg", response.getOutputStream());
  }
}
