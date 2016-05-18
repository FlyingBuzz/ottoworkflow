package com.ottocap.NewWorkFlow.server.ISampleGenerator;

import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataItem;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataItem.SideView;
import com.ottocap.NewWorkFlow.server.SQLTable;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.TreeSet;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;















public class ISampleLineArtDrawer
{
  private SQLTable mytable;
  private String svgdata = "";
  
  private TreeSet<String> camoused = new TreeSet();
  

  private String style = "";
  private String colortype = "Normal";
  
  private int svgwidth = 0;
  
  private int svgheight = 0;
  









  public ISampleLineArtDrawer(ExtendOrderDataItem capdata, ExtendOrderDataItem.SideView whichview)
    throws Exception
  {
    this(capdata, whichview, new SQLTable());
    this.mytable.closeSQL();
  }
  












  public ISampleLineArtDrawer(ExtendOrderDataItem capdata, ExtendOrderDataItem.SideView whichview, SQLTable sqlTable)
    throws Exception
  {
    this.mytable = sqlTable;
    this.style = capdata.getStyleNumber();
    boolean structuredcap = false;
    String colorcode = capdata.getColorCode();
    String frontpanelcolor = capdata.getFrontPanelColor();
    String sidebackpanelcolor = capdata.getSideBackPanelColor();
    String backpanelcolor = capdata.getBackPanelColor();
    String sidepanelcolor = capdata.getSidePanelColor();
    String buttoncolor = capdata.getButtonColor();
    String innertapingcolor = capdata.getInnerTapingColor();
    String visorstylenumber = capdata.getVisorStyleNumber();
    String primaryvisorcolor = capdata.getPrimaryVisorColor();
    String visortrimcolor = capdata.getVisorTrimColor();
    String visorsandwichcolor = capdata.getVisorSandwichColor();
    String undervisorcolor = capdata.getUndervisorColor();
    String distressedvisorinsidecolor = capdata.getDistressedVisorInsideColor();
    String closurestylenumber = capdata.getClosureStyleNumber();
    String closurestrapcolor = capdata.getClosureStrapColor();
    String eyeletstylenumber = capdata.getEyeletStyleNumber();
    String fronteyeletcolor = capdata.getFrontEyeletColor();
    String sidebackeyeletcolor = capdata.getSideBackEyeletColor();
    String backeyeletcolor = capdata.getBackEyeletColor();
    String sideeyeletcolor = capdata.getSideEyeletColor();
    String sweatbandstylenumber = capdata.getSweatbandStyleNumber();
    String sweatbandcolor = capdata.getSweatbandColor();
    String sweatbandstripecolor = capdata.getSweatbandStripeColor();
    
    String panelstitchcolor = capdata.getPanelStitchColor();
    String visorstitchcolor = capdata.getVisorStitchColor();
    
    String profile = capdata.getProfile();
    
    String sidebackpanelfabric = capdata.getSideBackPanelFabric();
    String backpanelfabric = capdata.getBackPanelFabric();
    
    String numberofpanels = capdata.getNumberOfPanels();
    String crownconstruction = capdata.getCrownConstruction();
    ExtendOrderDataItem.SideView sideview;
    ExtendOrderDataItem.SideView sideview;
    if (whichview == null) {
      sideview = capdata.getSideView();
    } else {
      sideview = whichview;
    }
    
    String sideviewfilestring = "";
    if (sideview == ExtendOrderDataItem.SideView.BACK) {
      sideviewfilestring = "Back";
    } else if (sideview == ExtendOrderDataItem.SideView.FRONT) {
      sideviewfilestring = "Front";
    } else if (sideview == ExtendOrderDataItem.SideView.INSIDE) {
      sideviewfilestring = "Inside";
    } else if (sideview == ExtendOrderDataItem.SideView.LEFT) {
      sideviewfilestring = "Left";
    } else if (sideview == ExtendOrderDataItem.SideView.RIGHT) {
      sideviewfilestring = "Right";
    }
    
    String captype = null;
    if (this.mytable.makeTable("SELECT `imagetype` , `Wash Type`, `Structured Type` FROM `styles_overseas` WHERE `Style` = '" + this.style + "' LIMIT 1").booleanValue()) {
      captype = ((String)this.mytable.getCell("imagetype")).trim();
      this.colortype = ((String)this.mytable.getCell("Wash Type")).trim();
      String currentstructure = ((String)this.mytable.getCell("Structured Type")).toLowerCase();
      if (currentstructure.contains("unstructured")) {
        structuredcap = false;
      } else if (currentstructure.contains("structured")) {
        structuredcap = true;
      }
    } else if (this.mytable.makeTable("SELECT category FROM `styles_domestic_shirts` WHERE `sku` = '" + this.style + "' LIMIT 1").booleanValue()) {
      captype = ((String)this.mytable.getValue()).trim().equals("T-Shirt") ? "tshirt" : "sportsshirt";
    } else if (this.mytable.makeTable("SELECT category FROM `styles_domestic_sweatshirts` WHERE `sku` = '" + this.style + "' LIMIT 1").booleanValue()) {
      captype = "sweatshirt";
    } else if (this.mytable.makeTable("SELECT `imagetype` , `Wash Type` FROM `styles_overseasinstock` WHERE `STYLE NUMBER` = '" + this.style + "' LIMIT 1").booleanValue()) {
      captype = ((String)this.mytable.getCell("imagetype")).trim();
      this.colortype = ((String)this.mytable.getCell("Wash Type")).trim();
    } else if (this.mytable.makeTable("SELECT `Style` FROM `styles_overseas_customstyle` WHERE `Style` = '" + this.style + "' LIMIT 1").booleanValue())
    {
      if (profile.equals("Beanie")) {
        captype = "beanie";
      } else if ((profile.equals("Head Band")) || (profile.equals("Head Wrap"))) {
        captype = "headband";
      } else if (profile.equals("Wrist Band")) {
        captype = "wristband";
      } else if (profile.equals("Sun Visor")) {
        captype = "sunvisor";
      } else if ((profile.contains("Low Profile")) || (profile.contains("Pro Style"))) {
        if ((sidebackpanelfabric.contains("Mesh")) || (backpanelfabric.contains("Mesh"))) {
          captype = numberofpanels + "pmesh";
        } else {
          captype = numberofpanels + "p";
        }
      }
      
      if (crownconstruction != null) {
        String currentstructure = crownconstruction.toLowerCase();
        if (currentstructure.contains("unstructured")) {
          structuredcap = false;
        } else if (currentstructure.contains("structured")) {
          structuredcap = true;
        }
      }
      
      if ((capdata.getFrontPanelColor().equals("")) && (capdata.getSideBackPanelColor().equals("")) && (capdata.getSidePanelColor().equals("")) && (capdata.getBackPanelColor().equals(""))) {
        frontpanelcolor = capdata.getColorCode();
        sidebackpanelcolor = capdata.getColorCode();
        if (capdata.getInnerTapingColor().equals("")) {
          innertapingcolor = capdata.getColorCode();
        }
        if (capdata.getPrimaryVisorColor().equals("")) {
          primaryvisorcolor = capdata.getColorCode();
        }
        if (capdata.getClosureStrapColor().equals("")) {
          closurestrapcolor = capdata.getColorCode();
        }
        if (capdata.getSweatbandColor().equals("")) {
          sweatbandcolor = capdata.getColorCode();
        }
        if (capdata.getSweatbandStripeColor().equals("")) {
          sweatbandstripecolor = capdata.getColorCode();
        }
        if (capdata.getVisorStitchColor().equals("")) {
          visorstitchcolor = capdata.getPanelStitchColor();
        }
        if ((capdata.getEyeletStyleNumber().equals("E1")) && (capdata.getSideBackEyeletColor().equals("")) && (capdata.getSideBackEyeletColor().equals("")) && (capdata.getBackEyeletColor().equals(""))) {
          sidebackeyeletcolor = capdata.getFrontEyeletColor();
        }
      }
    }
    

    if (this.mytable.makeTable("SELECT `description2` FROM `styles_domestic` WHERE `sku` = '" + this.style + "' LIMIT 1").booleanValue()) {
      String currentstructure = ((String)this.mytable.getValue()).toLowerCase();
      if (currentstructure.contains("unstructured")) {
        structuredcap = false;
      } else if (currentstructure.contains("structured")) {
        structuredcap = true;
      }
    }
    
    if (backpanelcolor.equals("")) {
      backpanelcolor = sidebackpanelcolor;
    }
    if (sidepanelcolor.equals("")) {
      sidepanelcolor = sidebackpanelcolor;
    }
    
    if (sideeyeletcolor.equals("")) {
      sideeyeletcolor = sidebackeyeletcolor;
    }
    if (backeyeletcolor.equals("")) {
      backeyeletcolor = sidebackeyeletcolor;
    }
    
    if ((closurestylenumber.equals("")) && (captype.equals("sunvisorclip"))) {
      closurestylenumber = "c32";
    } else if (closurestylenumber.equals("")) {
      closurestylenumber = "c25";
    }
    if (visorstylenumber.equals("V3")) {
      undervisorcolor = visortrimcolor;
    }
    
    if (eyeletstylenumber != null)
    {
      if (eyeletstylenumber.equals("E5")) {
        fronteyeletcolor = "#f8eeba";
        sidebackeyeletcolor = "#f8eeba";
        backeyeletcolor = "#f8eeba";
        sideeyeletcolor = "#f8eeba";
      } else if (eyeletstylenumber.equals("E6")) {
        fronteyeletcolor = "#e9e9e9";
        sidebackeyeletcolor = "#e9e9e9";
        backeyeletcolor = "#e9e9e9";
        sideeyeletcolor = "#e9e9e9";
      } else if (eyeletstylenumber.equals("E7")) {
        fronteyeletcolor = "#616161";
        sidebackeyeletcolor = "#616161";
        backeyeletcolor = "#616161";
        sideeyeletcolor = "#616161";
      } else if (eyeletstylenumber.equals("E8")) {
        fronteyeletcolor = "#cfb253";
        sidebackeyeletcolor = "#cfb253";
        backeyeletcolor = "#cfb253";
        sideeyeletcolor = "#cfb253";
      } else if (eyeletstylenumber.equals("E9")) {
        fronteyeletcolor = "#9b9b9b";
        sidebackeyeletcolor = "#9b9b9b";
        backeyeletcolor = "#9b9b9b";
        sideeyeletcolor = "#9b9b9b";
      } else if (eyeletstylenumber.equals("E10")) {
        fronteyeletcolor = "#515151";
        sidebackeyeletcolor = "#515151";
        backeyeletcolor = "#515151";
        sideeyeletcolor = "#515151";
      } else if (eyeletstylenumber.equals("E11")) {
        fronteyeletcolor = "#715f33";
        sidebackeyeletcolor = "#715f33";
        backeyeletcolor = "#715f33";
        sideeyeletcolor = "#715f33";
      } else if (eyeletstylenumber.equals("E12")) {
        fronteyeletcolor = "#cbcbcb";
        sidebackeyeletcolor = "#cbcbcb";
        backeyeletcolor = "#cbcbcb";
        sideeyeletcolor = "#cbcbcb";
      } else if (eyeletstylenumber.equals("E13")) {
        fronteyeletcolor = "#676768";
        sidebackeyeletcolor = "#676768";
        backeyeletcolor = "#676768";
        sideeyeletcolor = "#676768";
      }
    }
    

    String fullpath = "";
    if (captype.equals("5p")) {
      if (sideview == ExtendOrderDataItem.SideView.BACK) {
        fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/5 Panel/Closure - 5 Panel/" + closurestylenumber + " - 5Panel - Back.svg";
      } else if (sideview == ExtendOrderDataItem.SideView.INSIDE) {
        if (closurestylenumber.toLowerCase().equals("c1")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/5 Panel/Inside - 5 Panel/C1 Adj Hook-Loop - 5 Panel/" + visorstylenumber + "_" + "C1 - 5Panel - Inside.svg";
        } else if (closurestylenumber.toLowerCase().equals("c2")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/5 Panel/Inside - 5 Panel/C2 Plastic Snap - 5 Panel/" + visorstylenumber + "_" + "C2 - 5Panel - Inside.svg";
        } else if (closurestylenumber.toLowerCase().equals("c3")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/5 Panel/Inside - 5 Panel/C3 Adj Metal Buckle-Brass - 5 Panel/" + visorstylenumber + "_" + "C3 - 5Panel - Inside.svg";
        } else if (closurestylenumber.toLowerCase().equals("c4")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/5 Panel/Inside - 5 Panel/C4 Adj Metal Buckle-Silver - 5 Panel/" + visorstylenumber + "_" + "C4 - 5Panel - Inside.svg";
        } else if (closurestylenumber.toLowerCase().equals("c5")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/5 Panel/Inside - 5 Panel/C5 Adj Metal Buckle-Black - 5 Panel/" + visorstylenumber + "_" + "C5 - 5Panel - Inside.svg";
        } else if ((closurestylenumber.toLowerCase().equals("c6")) || (closurestylenumber.toLowerCase().equals("c9"))) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/5 Panel/Inside - 5 Panel/C6-C9 Metal Press Buckle-Brass - 5 Panel/" + visorstylenumber + "_" + "C6-C9 - 5Panel - Inside.svg";
        } else if ((closurestylenumber.toLowerCase().equals("c7")) || (closurestylenumber.toLowerCase().equals("c10"))) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/5 Panel/Inside - 5 Panel/C7-C10 Metal Press Buckle-Silver - 5 Panel/" + visorstylenumber + "_" + "C7-C10 - 5Panel - Inside.svg";
        } else if ((closurestylenumber.toLowerCase().equals("c8")) || (closurestylenumber.toLowerCase().equals("c11"))) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/5 Panel/Inside - 5 Panel/C8-C11 Metal Press Buckle-Black - 5 Panel/" + visorstylenumber + "_" + "C8-C11 - 5Panel - Inside.svg";
        } else if (closurestylenumber.toLowerCase().equals("c12")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/5 Panel/Inside - 5 Panel/C12 Leather Strap Metal Press Buckle-Brass - 5 Panel/" + visorstylenumber + "_" + "C12 - 5Panel - Inside.svg";
        } else if (closurestylenumber.toLowerCase().equals("c13")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/5 Panel/Inside - 5 Panel/C13 Leather Strap Metal Press Buckle-Silver - 5 Panel/" + visorstylenumber + "_" + "C13 - 5Panel - Inside.svg";
        } else if (closurestylenumber.toLowerCase().equals("c14")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/5 Panel/Inside - 5 Panel/C14 Leather Strap Metal Press Buckle-Black - 5 Panel/" + visorstylenumber + "_" + "C14 - 5Panel - Inside.svg";
        } else if (closurestylenumber.toLowerCase().equals("c25")) {
          if (sweatbandstylenumber.toLowerCase().equals("s4")) {
            fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/5 Panel/Inside - 5 Panel/C25 Enclosed - 5 Panel/C25 S4 003 Enclosed - 5 Panel/" + visorstylenumber + "_" + "C25_S4_003 - 5Panel - Inside.svg";
          } else if ((sweatbandstylenumber.toLowerCase().equals("s5")) && (sweatbandcolor.equals("003"))) {
            fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/5 Panel/Inside - 5 Panel/C25 Enclosed - 5 Panel/C25 S5 003 Enclosed - 5 Panel/" + visorstylenumber + "_" + "C25_S5_003 - 5Panel - Inside.svg";
          } else if ((sweatbandstylenumber.toLowerCase().equals("s5")) && (sweatbandcolor.equals("016"))) {
            fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/5 Panel/Inside - 5 Panel/C25 Enclosed - 5 Panel/C25 S5 016 Enclosed - 5 Panel/" + visorstylenumber + "_" + "C25_S5_016 - 5Panel - Inside.svg";
          } else {
            fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/5 Panel/Inside - 5 Panel/C25 Enclosed - 5 Panel/" + visorstylenumber + "_" + "C25 - 5Panel - Inside.svg";
          }
        }
      } else if ((sideview == ExtendOrderDataItem.SideView.FRONT) || (sideview == ExtendOrderDataItem.SideView.LEFT) || (sideview == ExtendOrderDataItem.SideView.RIGHT)) {
        if (visorstylenumber.toLowerCase().equals("v1")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/5 Panel/Visor - 5 Panel/V1 Standard - 5 Panel/V1 - 5Panel - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v2")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/5 Panel/Visor - 5 Panel/V2 Sandwich - 5 Panel/V2 - 5Panel - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v3")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/5 Panel/Visor - 5 Panel/V3 Flipped Edge - 5 Panel/V3 - 5Panel - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v4")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/5 Panel/Visor - 5 Panel/V4 Trim - 5 Panel/V4 - 5Panel - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v5")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/5 Panel/Visor - 5 Panel/V5 Wave Trim - 5 Panel/V5 - 5Panel - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v6")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/5 Panel/Visor - 5 Panel/V6 Distressed - 5 Panel/V6 - 5Panel - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v7")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/5 Panel/Visor - 5 Panel/V7 Curved Trim - 5 Panel/V7 - 5Panel - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v8")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/5 Panel/Visor - 5 Panel/V8 Curved Trim Sandwich - 5 Panel/V8 - 5Panel - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v10")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/5 Panel/Visor - 5 Panel/V10 Binding Edge - 5 Panel/V10 - 5Panel - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v11")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/5 Panel/Visor - 5 Panel/V11 Flat - 5 Panel/V11 - 5Panel - " + sideviewfilestring + ".svg";
        }
      }
    } else if ((captype.equals("5pmesh")) || (captype.equals("5pmeshf"))) {
      if (sideview == ExtendOrderDataItem.SideView.BACK) {
        fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/5 Panel/Closure - 5 Panel Mesh/" + closurestylenumber + " - 5PanelMesh - Back.svg";
      } else if (sideview == ExtendOrderDataItem.SideView.INSIDE) {
        if (closurestylenumber.toLowerCase().equals("c1")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/5 Panel/Inside - 5 Panel Mesh/C1 Adj Hook-Loop - 5 Panel Mesh/" + visorstylenumber + "_" + "C1 - 5PanelMesh - Inside.svg";
        } else if (closurestylenumber.toLowerCase().equals("c2")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/5 Panel/Inside - 5 Panel Mesh/C2 Plastic Snap - 5 Panel Mesh/" + visorstylenumber + "_" + "C2 - 5PanelMesh - Inside.svg";
        } else if (closurestylenumber.toLowerCase().equals("c25")) {
          if (sweatbandstylenumber.toLowerCase().equals("s4")) {
            fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/5 Panel/Inside - 5 Panel Mesh/C25 Enclosed - 5 Panel Mesh/C25 S4 003 Enclosed - 5 Panel Mesh/" + visorstylenumber + "_" + "C25_S4_003 - 5PanelMesh - Inside.svg";
          } else if ((sweatbandstylenumber.toLowerCase().equals("s5")) && (sweatbandcolor.equals("003"))) {
            fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/5 Panel/Inside - 5 Panel Mesh/C25 Enclosed - 5 Panel Mesh/C25 S5 003 Enclosed - 5 Panel Mesh/" + visorstylenumber + "_" + "C25_S5_003 - 5PanelMesh - Inside.svg";
          } else if ((sweatbandstylenumber.toLowerCase().equals("s5")) && (sweatbandcolor.equals("016"))) {
            fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/5 Panel/Inside - 5 Panel Mesh/C25 Enclosed - 5 Panel Mesh/C25 S5 016 Enclosed - 5 Panel Mesh/" + visorstylenumber + "_" + "C25_S5_016 - 5PanelMesh - Inside.svg";
          } else {
            fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/5 Panel/Inside - 5 Panel Mesh/C25 Enclosed - 5 Panel Mesh/" + visorstylenumber + "_" + "C2 - 5PanelMesh - Inside.svg";
          }
        } else {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/5 Panel/Inside - 5 Panel Mesh/All Other Closures - 5 Panel Mesh/" + closurestylenumber + "_" + visorstylenumber + "_5PanelMesh.svg";
        }
      } else if ((sideview == ExtendOrderDataItem.SideView.FRONT) || (sideview == ExtendOrderDataItem.SideView.LEFT) || (sideview == ExtendOrderDataItem.SideView.RIGHT)) {
        if (visorstylenumber.toLowerCase().equals("v1")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/5 Panel/Visor - 5 Panel Mesh/V1 Standard - 5 Panel Mesh/V1 - 5PanelMesh - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v2")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/5 Panel/Visor - 5 Panel Mesh/V2 Sandwich - 5 Panel Mesh/V2 - 5PanelMesh - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v3")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/5 Panel/Visor - 5 Panel Mesh/V3 Flipped Edge - 5 Panel Mesh/V3 - 5PanelMesh - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v4")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/5 Panel/Visor - 5 Panel Mesh/V4 Trim - 5 Panel Mesh/V4 - 5PanelMesh - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v5")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/5 Panel/Visor - 5 Panel Mesh/V5 Wave Trim - 5 Panel Mesh/V5 - 5PanelMesh - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v6")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/5 Panel/Visor - 5 Panel Mesh/V6 Distressed - 5 Panel Mesh/V6 - 5PanelMesh - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v7")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/5 Panel/Visor - 5 Panel Mesh/V7 Curved Trim - 5 Panel Mesh/V7 - 5PanelMesh - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v8")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/5 Panel/Visor - 5 Panel Mesh/V8 Curved Trim Sandwich - 5 Panel Mesh/V8 - 5PanelMesh - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v10")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/5 Panel/Visor - 5 Panel Mesh/V10 Binding Edge - 5 Panel Mesh/V10 - 5PanelMesh - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v11")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/5 Panel/Visor - 5 Panel Mesh/V11 Flat - 5 Panel Mesh/V11 - 5PanelMesh - " + sideviewfilestring + ".svg";
        }
      }
    } else if (captype.equals("6p")) {
      if (sideview == ExtendOrderDataItem.SideView.BACK) {
        fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/6 Panel/Closure - 6 Panel/" + closurestylenumber + " - 6Panel - Back.svg";
      } else if (sideview == ExtendOrderDataItem.SideView.INSIDE) {
        if (closurestylenumber.toLowerCase().equals("c1")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/6 Panel/Inside - 6 Panel/C1 Adj Hook-Loop - 6 Panel/" + visorstylenumber + "_" + "C1 - 6Panel - Inside.svg";
        } else if (closurestylenumber.toLowerCase().equals("c2")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/6 Panel/Inside - 6 Panel/C2 Plastic Snap - 6 Panel/" + visorstylenumber + "_" + "C2 - 6Panel - Inside.svg";
        } else if (closurestylenumber.toLowerCase().equals("c3")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/6 Panel/Inside - 6 Panel/C3 Adj Metal Buckle-Brass - 6 Panel/" + visorstylenumber + "_" + "C3 - 6Panel - Inside.svg";
        } else if (closurestylenumber.toLowerCase().equals("c4")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/6 Panel/Inside - 6 Panel/C4 Adj Metal Buckle-Silver - 6 Panel/" + visorstylenumber + "_" + "C4 - 6Panel - Inside.svg";
        } else if (closurestylenumber.toLowerCase().equals("c5")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/6 Panel/Inside - 6 Panel/C5 Adj Metal Buckle-Black - 6 Panel/" + visorstylenumber + "_" + "C5 - 6Panel - Inside.svg";
        } else if ((closurestylenumber.toLowerCase().equals("c6")) || (closurestylenumber.toLowerCase().equals("c9"))) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/6 Panel/Inside - 6 Panel/C6-C9 Metal Press Buckle-Brass - 6 Panel/" + visorstylenumber + "_" + "C6-C9 - 6Panel - Inside.svg";
        } else if ((closurestylenumber.toLowerCase().equals("c7")) || (closurestylenumber.toLowerCase().equals("c10"))) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/6 Panel/Inside - 6 Panel/C7-C10 Metal Press Buckle-Silver - 6 Panel/" + visorstylenumber + "_" + "C7-C10 - 6Panel - Inside.svg";
        } else if ((closurestylenumber.toLowerCase().equals("c8")) || (closurestylenumber.toLowerCase().equals("c11"))) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/6 Panel/Inside - 6 Panel/C8-C11 Metal Press Buckle-Black - 6 Panel/" + visorstylenumber + "_" + "C8-C11 - 6Panel - Inside.svg";
        } else if (closurestylenumber.toLowerCase().equals("c12")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/6 Panel/Inside - 6 Panel/C12 Leather Strap Metal Press Buckle-Brass - 6 Panel/" + visorstylenumber + "_" + "C12 - 6Panel - Inside.svg";
        } else if (closurestylenumber.toLowerCase().equals("c13")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/6 Panel/Inside - 6 Panel/C13 Leather Strap Metal Press Buckle-Silver - 6 Panel/" + visorstylenumber + "_" + "C13 - 6Panel - Inside.svg";
        } else if (closurestylenumber.toLowerCase().equals("c14")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/6 Panel/Inside - 6 Panel/C14 Leather Strap Metal Press Buckle-Black - 6 Panel/" + visorstylenumber + "_" + "C14 - 6Panel - Inside.svg";
        } else if (closurestylenumber.toLowerCase().equals("c25")) {
          if (sweatbandstylenumber.toLowerCase().equals("s4")) {
            fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/6 Panel/Inside - 6 Panel/C25 Enclosed - 6 Panel/C25 S4 003 Enclosed - 6 Panel/" + visorstylenumber + "_" + "C25_S4_003 - 6Panel - Inside.svg";
          } else if ((sweatbandstylenumber.toLowerCase().equals("s5")) && (sweatbandcolor.equals("003"))) {
            fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/6 Panel/Inside - 6 Panel/C25 Enclosed - 6 Panel/C25 S5 003 Enclosed - 6 Panel/" + visorstylenumber + "_" + "C25_S5_003 - 6Panel - Inside.svg";
          } else if ((sweatbandstylenumber.toLowerCase().equals("s5")) && (sweatbandcolor.equals("016"))) {
            fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/6 Panel/Inside - 6 Panel/C25 Enclosed - 6 Panel/C25 S5 016 Enclosed - 6 Panel/C25_" + visorstylenumber + "_6 Panel_S5_016.svg";
          } else {
            fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/6 Panel/Inside - 6 Panel/C25 Enclosed - 6 Panel/" + visorstylenumber + "_" + "C25 - 6Panel - Inside.svg";
          }
        }
      } else if ((sideview == ExtendOrderDataItem.SideView.FRONT) || (sideview == ExtendOrderDataItem.SideView.LEFT) || (sideview == ExtendOrderDataItem.SideView.RIGHT)) {
        if (visorstylenumber.toLowerCase().equals("v1")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/6 Panel/Visor - 6 Panel/V1 Standard - 6 Panel/V1 - 6Panel - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v2")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/6 Panel/Visor - 6 Panel/V2 Sandwich - 6 Panel/V2 - 6Panel - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v3")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/6 Panel/Visor - 6 Panel/V3 Flipped Edge - 6 Panel/V3 - 6Panel - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v4")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/6 Panel/Visor - 6 Panel/V4 Trim - 6 Panel/V4 - 6Panel - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v5")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/6 Panel/Visor - 6 Panel/V5 Wave Trim - 6 Panel/V5 - 6Panel - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v6")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/6 Panel/Visor - 6 Panel/V6 Distressed - 6 Panel/V6 - 6Panel - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v7")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/6 Panel/Visor - 6 Panel/V7 Curved Trim - 6 Panel/V7 - 6Panel - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v8")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/6 Panel/Visor - 6 Panel/V8 Curved Trim Sandwich - 6 Panel/V8 - 6Panel - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v10")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/6 Panel/Visor - 6 Panel/V10 Binding Edge - 6 Panel/V10 - 6Panel - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v11")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/6 Panel/Visor - 6 Panel/V11 Flat - 6 Panel/V11 - 6Panel - " + sideviewfilestring + ".svg";
        }
      }
    } else if (captype.equals("6pmesh")) {
      if (sideview == ExtendOrderDataItem.SideView.BACK) {
        fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/6 Panel/Closure - 6 Panel Mesh/" + closurestylenumber + " - 6PanelMesh - Back.svg";
      } else if (sideview == ExtendOrderDataItem.SideView.INSIDE) {
        if (closurestylenumber.toLowerCase().equals("c1")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/6 Panel/Inside - 6 Panel Mesh/C1 Adj Hook-Loop - 6 Panel Mesh/" + visorstylenumber + "_" + "C1 - 6PanelMesh - Inside.svg";
        } else if (closurestylenumber.toLowerCase().equals("c2")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/6 Panel/Inside - 6 Panel Mesh/C2 Plastic Snap - 6 Panel Mesh/" + visorstylenumber + "_" + "C2 - 6PanelMesh - Inside.svg";
        } else if (closurestylenumber.toLowerCase().equals("c25")) {
          if (sweatbandstylenumber.toLowerCase().equals("s4")) {
            fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/6 Panel/Inside - 6 Panel Mesh/C25 Enclosed - 6 Panel Mesh/C25 S4 003 Enclosed - 6 Panel/" + visorstylenumber + "_" + "C25_S4_003 - 6PanelMesh - Inside.svg";
          } else if ((sweatbandstylenumber.toLowerCase().equals("s5")) && (sweatbandcolor.equals("003"))) {
            fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/6 Panel/Inside - 6 Panel Mesh/C25 Enclosed - 6 Panel Mesh/C25 S5 003 Enclosed - 6 Panel/" + visorstylenumber + "_" + "C25_S5_003 - 6PanelMesh - Inside.svg";
          } else if ((sweatbandstylenumber.toLowerCase().equals("s5")) && (sweatbandcolor.equals("016"))) {
            fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/6 Panel/Inside - 6 Panel Mesh/C25 Enclosed - 6 Panel Mesh/C25 S5 016 Enclosed - 6 Panel/" + visorstylenumber + "_" + "C25_S5_016 - 6PanelMesh - Inside.svg";
          } else {
            fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/6 Panel/Inside - 6 Panel Mesh/C25 Enclosed - 6 Panel Mesh/" + visorstylenumber + "_" + "C25 - 6PanelMesh - Inside.svg";
          }
        }
      } else if ((sideview == ExtendOrderDataItem.SideView.FRONT) || (sideview == ExtendOrderDataItem.SideView.LEFT) || (sideview == ExtendOrderDataItem.SideView.RIGHT)) {
        if (visorstylenumber.toLowerCase().equals("v1")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/6 Panel/Visor - 6 Panel Mesh/V1 Standard - 6 Panel Mesh/V1 - 6PanelMesh - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v2")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/6 Panel/Visor - 6 Panel Mesh/V2 Sandwich - 6 Panel Mesh/V2 - 6PanelMesh - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v3")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/6 Panel/Visor - 6 Panel Mesh/V3 Flipped Edge - 6 Panel Mesh/V3 - 6PanelMesh - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v4")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/6 Panel/Visor - 6 Panel Mesh/V4 Trim - 6 Panel Mesh/V4 - 6PanelMesh - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v5")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/6 Panel/Visor - 6 Panel Mesh/V5 Wave Trim - 6 Panel Mesh/V5 - 6PanelMesh - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v6")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/6 Panel/Visor - 6 Panel Mesh/V6 Distressed - 6 Panel Mesh/V6 - 6PanelMesh - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v7")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/6 Panel/Visor - 6 Panel Mesh/V7 Curved Trim - 6 Panel Mesh/V7 - 6PanelMesh - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v8")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/6 Panel/Visor - 6 Panel Mesh/V8 Curved Trim Sandwich - 6 Panel Mesh/V8 - 6PanelMesh - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v10")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/6 Panel/Visor - 6 Panel Mesh/V10 Binding Edge - 6 Panel Mesh/V10 - 6PanelMesh - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v11")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/6 Panel/Visor - 6 Panel Mesh/V11 Flat - 6 Panel Mesh/V11 - 6PanelMesh - " + sideviewfilestring + ".svg";
        }
      }
    } else if ((captype.equals("military")) || (captype.equals("militarydistressed")) || (captype.equals("militaryhighcrown"))) {
      if (sideview == ExtendOrderDataItem.SideView.BACK) {
        fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Military/Closure - All Military/" + closurestylenumber + " - Military - Back.svg";
      } else if (sideview == ExtendOrderDataItem.SideView.INSIDE) {
        if (closurestylenumber.toLowerCase().equals("c1")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Military/Inside - All Military/C1 Adj Hook-Loop - Military/" + visorstylenumber + "_" + "C1 - Military - Inside.svg";
        } else if (closurestylenumber.toLowerCase().equals("c2")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Military/Inside - All Military/C2 Plastic Snap - Military/" + visorstylenumber + "_" + "C2 - Military - Inside.svg";
        }
      } else if ((sideview == ExtendOrderDataItem.SideView.FRONT) || (sideview == ExtendOrderDataItem.SideView.LEFT) || (sideview == ExtendOrderDataItem.SideView.RIGHT)) {
        if (captype.equals("military")) {
          if (visorstylenumber.toLowerCase().equals("v1")) {
            fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Military/Visor - Standard Military/V1 Standard - Standard Military/V1 - StandardMilitary - " + sideviewfilestring + ".svg";
          } else if (visorstylenumber.toLowerCase().equals("v2")) {
            fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Military/Visor - Standard Military/V2 Sandwich - Standard Military/V2 - StandardMilitary - " + sideviewfilestring + ".svg";
          } else if (visorstylenumber.toLowerCase().equals("v3")) {
            fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Military/Visor - Standard Military/V3 Flipped Edge - Standard Military/V3 - StandardMilitary - " + sideviewfilestring + ".svg";
          } else if (visorstylenumber.toLowerCase().equals("v6")) {
            fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Military/Visor - Standard Military/V6 Distressed - Standard Military/V6 - StandardMilitary - " + sideviewfilestring + ".svg";
          } else if (visorstylenumber.toLowerCase().equals("v9")) {
            fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Military/Visor - Standard Military/V9 Bendable Soft - Standard Military/V9 - StandardMilitary - " + sideviewfilestring + ".svg";
          } else if (visorstylenumber.toLowerCase().equals("v10")) {
            fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Military/Visor - Standard Military/V10 Binding Edge - Standard Military/V10 - StandardMilitary - " + sideviewfilestring + ".svg";
          }
        } else if (captype.equals("militarydistressed")) {
          if (visorstylenumber.toLowerCase().equals("v1")) {
            fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Military/Visor - Distressed Military/V1 Standard - Distressed Military/V1 - DistressedMilitary - " + sideviewfilestring + ".svg";
          } else if (visorstylenumber.toLowerCase().equals("v2")) {
            fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Military/Visor - Distressed Military/V2 Sandwich - Distressed Military/V2 - DistressedMilitary - " + sideviewfilestring + ".svg";
          } else if (visorstylenumber.toLowerCase().equals("v3")) {
            fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Military/Visor - Distressed Military/V3 Flipped Edge - Distressed Military/V3 - DistressedMilitary - " + sideviewfilestring + ".svg";
          } else if (visorstylenumber.toLowerCase().equals("v6")) {
            fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Military/Visor - Distressed Military/V6 Distressed - Distressed Military/V6 - DistressedMilitary - " + sideviewfilestring + ".svg";
          } else if (visorstylenumber.toLowerCase().equals("v9")) {
            fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Military/Visor - Distressed Military/V9 Bendable Soft - Distressed Military/V9 - DistressedMilitary - " + sideviewfilestring + ".svg";
          } else if (visorstylenumber.toLowerCase().equals("v10")) {
            fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Military/Visor - Distressed Military/V10 Binding Edge - Distressed Military/V10 - DistressedMilitary - " + sideviewfilestring + ".svg";
          }
        } else if (captype.equals("militaryhighcrown")) {
          if (visorstylenumber.toLowerCase().equals("v1")) {
            fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Military/Visor - High Crown Military/V1 Standard - High Crown Military/V1 - HighCrownMilitary - " + sideviewfilestring + ".svg";
          } else if (visorstylenumber.toLowerCase().equals("v2")) {
            fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Military/Visor - High Crown Military/V2 Sandwich - High Crown Military/V2 - HighCrownMilitary - " + sideviewfilestring + ".svg";
          } else if (visorstylenumber.toLowerCase().equals("v3")) {
            fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Military/Visor - High Crown Military/V3 Flipped Edge - High Crown Military/V3 - HighCrownMilitary - " + sideviewfilestring + ".svg";
          } else if (visorstylenumber.toLowerCase().equals("v6")) {
            fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Military/Visor - High Crown Military/V6 Distressed - High Crown Military/V6 - HighCrownMilitary - " + sideviewfilestring + ".svg";
          } else if (visorstylenumber.toLowerCase().equals("v9")) {
            fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Military/Visor - High Crown Military/V9 Bendable Soft - High Crown Military/V9 - HighCrownMilitary - " + sideviewfilestring + ".svg";
          } else if (visorstylenumber.toLowerCase().equals("v10")) {
            fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Military/Visor - High Crown Military/V10 Binding Edge - High Crown Military/V10 - HighCrownMilitary - " + sideviewfilestring + ".svg";
          }
        }
      }
    } else if (captype.equals("sunvisor")) {
      if (sideview == ExtendOrderDataItem.SideView.BACK) {
        if ((closurestylenumber.toLowerCase().equals("c29")) && (sweatbandstylenumber.toLowerCase().equals("s4"))) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/Standard Sun Visor/Closure - Standard Sun Visor/C29_S4_003 - StandardSV - Back.svg";
        } else if ((closurestylenumber.toLowerCase().equals("c29")) && (sweatbandstylenumber.toLowerCase().equals("s5")) && (sweatbandcolor.equals("003"))) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/Standard Sun Visor/Closure - Standard Sun Visor/C29_S5_003 - StandardSV - Back.svg";
        } else if ((closurestylenumber.toLowerCase().equals("c29")) && (sweatbandstylenumber.toLowerCase().equals("s5")) && (sweatbandcolor.equals("016"))) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/Standard Sun Visor/Closure - Standard Sun Visor/C29_S5_016 - StandardSV - Back.svg";
        } else {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/Standard Sun Visor/Closure - Standard Sun Visor/" + closurestylenumber + " - StandardSV - Back.svg";
        }
      } else if (sideview == ExtendOrderDataItem.SideView.INSIDE) {
        if (closurestylenumber.toLowerCase().equals("c23")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/Standard Sun Visor/Inside - Standard Sun Visor/C23 Elastic Strap - Standard Sun Visor/" + visorstylenumber + "_" + "C23 - StandardSV - Inside.svg";
        } else if (closurestylenumber.toLowerCase().equals("c30")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/Standard Sun Visor/Inside - Standard Sun Visor/C30 Adj Hook Loop - Standard Sun Visor/" + visorstylenumber + "_" + "C30 - StandardSV - Inside.svg";
        } else if (closurestylenumber.toLowerCase().equals("c31")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/Standard Sun Visor/Inside - Standard Sun Visor/C31 Plastic Snap - Standard Sun Visor/" + visorstylenumber + "_" + "C31 - StandardSV - Inside.svg";
        } else if (closurestylenumber.toLowerCase().equals("c32")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/Standard Sun Visor/Inside - Standard Sun Visor/C32 Clip On - Standard Sun Visor/" + visorstylenumber + "_" + "C32 - StandardSV - Inside.svg";
        } else if (closurestylenumber.toLowerCase().equals("c29")) {
          if (sweatbandstylenumber.toLowerCase().equals("s4")) {
            fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/Standard Sun Visor/Inside - Standard Sun Visor/C29 Enclosed - Standard Sun Visor/C29 S4 003 Enclosed - Standard Sun Visor/" + visorstylenumber + "_" + "C29_S4_003 - Standard - Inside.svg";
          } else if ((sweatbandstylenumber.toLowerCase().equals("s5")) && (sweatbandcolor.equals("003"))) {
            fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/Standard Sun Visor/Inside - Standard Sun Visor/C29 Enclosed - Standard Sun Visor/C29 S5 003 Enclosed - Standard Sun Visor/" + visorstylenumber + "_" + "C29_S5_003 - Standard - Inside.svg";
          } else if ((sweatbandstylenumber.toLowerCase().equals("s5")) && (sweatbandcolor.equals("016"))) {
            fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/Standard Sun Visor/Inside - Standard Sun Visor/C29 Enclosed - Standard Sun Visor/C29 S5 016 Enclosed - Standard Sun Visor/" + visorstylenumber + "_" + "C29_S5_016 - Standard - Inside.svg";
          } else {
            fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/Standard Sun Visor/Inside - Standard Sun Visor/C29 Enclosed - Standard Sun Visor/" + visorstylenumber + "_" + "C29 - StandardSV - Inside.svg";
          }
        }
      } else if ((sideview == ExtendOrderDataItem.SideView.FRONT) || (sideview == ExtendOrderDataItem.SideView.LEFT) || (sideview == ExtendOrderDataItem.SideView.RIGHT)) {
        if (visorstylenumber.toLowerCase().equals("v1")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/Standard Sun Visor/Visor - Standard Sun Visor/V1 Standard - Standard/V1 - Standard - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v2")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/Standard Sun Visor/Visor - Standard Sun Visor/V2 Sandwich - Standard/V2 - Standard - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v3")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/Standard Sun Visor/Visor - Standard Sun Visor/V3 Flipped Edge - Standard/V3 - Standard - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v4")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/Standard Sun Visor/Visor - Standard Sun Visor/V4 Trim - Standard/V4 - Standard - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v5")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/Standard Sun Visor/Visor - Standard Sun Visor/V5 Wave Trim - Standard/V5 - Standard - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v6")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/Standard Sun Visor/Visor - Standard Sun Visor/V6 Distressed - Standard/V6 - Standard - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v7")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/Standard Sun Visor/Visor - Standard Sun Visor/V7 Curved Trim - Standard/V7 - Standard - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v8")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/Standard Sun Visor/Visor - Standard Sun Visor/V8 Curved Trim Sandwich - Standard/V8 - Standard - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v10")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/Standard Sun Visor/Visor - Standard Sun Visor/V10 Binding Edge - Standard/V10 - Standard - " + sideviewfilestring + ".svg";
        }
      }
    } else if (captype.equals("sunvisorhighcrown")) {
      if (sideview == ExtendOrderDataItem.SideView.BACK) {
        fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/High Crown Sun Visor/Closure - High Crown Sun Visor/" + closurestylenumber + " - HighCrownSV - Back.svg";
      } else if (sideview == ExtendOrderDataItem.SideView.INSIDE) {
        if (closurestylenumber.toLowerCase().equals("c15")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/High Crown Sun Visor/Inside - High Crown Sun Visor/C15 Adj Metal Buckle-Brass - High Crown Sun Visor/" + visorstylenumber + "_" + "C15 - HighCrownSV - Inside.svg";
        } else if (closurestylenumber.toLowerCase().equals("c16")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/High Crown Sun Visor/Inside - High Crown Sun Visor/C16 Adj Metal Buckle-Silver - High Crown Sun Visor/" + visorstylenumber + "_" + "C16 - HighCrownSV - Inside.svg";
        } else if (closurestylenumber.toLowerCase().equals("c17")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/High Crown Sun Visor/Inside - High Crown Sun Visor/C17 Adj Metal Buckle-Black - High Crown Sun Visor/" + visorstylenumber + "_" + "C17 - HighCrownSV - Inside.svg";
        } else if (closurestylenumber.toLowerCase().equals("c26")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/High Crown Sun Visor/Inside - High Crown Sun Visor/C26 Adj Plastic Buckle-Black - High Crown Sun Visor/" + visorstylenumber + "_" + "C26 - HighCrownSV - Inside.svg";
        } else if (closurestylenumber.toLowerCase().equals("c27")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/High Crown Sun Visor/Inside - High Crown Sun Visor/C27 Adj Plastic Buckle-White - High Crown Sun Visor/" + visorstylenumber + "_" + "C27 - HighCrownSV - Inside.svg";
        }
      } else if ((sideview == ExtendOrderDataItem.SideView.FRONT) || (sideview == ExtendOrderDataItem.SideView.LEFT) || (sideview == ExtendOrderDataItem.SideView.RIGHT)) {
        if (visorstylenumber.toLowerCase().equals("v1")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/High Crown Sun Visor/Visor - High Crown Sun Visor/V1 Standard - High Crown Sun Visor/V1 - HighCrownSV - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v2")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/High Crown Sun Visor/Visor - High Crown Sun Visor/V2 Sandwich - High Crown Sun Visor/V2 - HighCrownSV - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v3")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/High Crown Sun Visor/Visor - High Crown Sun Visor/V3 Flipped Edge - High Crown Sun Visor/V3 - HighCrownSV - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v4")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/High Crown Sun Visor/Visor - High Crown Sun Visor/V4 Trim - High Crown Sun Visor/V4 - HighCrownSV - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v5")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/High Crown Sun Visor/Visor - High Crown Sun Visor/V5 Wave Trim - High Crown Sun Visor/V5 - HighCrownSV - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v6")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/High Crown Sun Visor/Visor - High Crown Sun Visor/V6 Distressed - High Crown Sun Visor/V6 - HighCrownSV - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v7")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/High Crown Sun Visor/Visor - High Crown Sun Visor/V7 Curved Trim - High Crown Sun Visor/V7 - HighCrownSV - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v8")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/High Crown Sun Visor/Visor - High Crown Sun Visor/V8 Curved Trim Sandwich - High Crown Sun Visor/V8 - HighCrownSV - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v10")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/High Crown Sun Visor/Visor - High Crown Sun Visor/V10 Binding Edge - High Crown Sun Visor/V10 - HighCrownSV - " + sideviewfilestring + ".svg";
        }
      }
    } else if (captype.equals("sunvisorclip")) {
      if (sideview == ExtendOrderDataItem.SideView.BACK) {
        fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/Clip On Sun Visor/Closure - Clip On/" + closurestylenumber + " - ClipOn - Back.svg";
      } else if (sideview == ExtendOrderDataItem.SideView.INSIDE) {
        fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/Clip On Sun Visor/Inside - Clip On/" + visorstylenumber + "_" + "C32 - ClipOn - Inside.svg";
      } else if ((sideview == ExtendOrderDataItem.SideView.FRONT) || (sideview == ExtendOrderDataItem.SideView.LEFT) || (sideview == ExtendOrderDataItem.SideView.RIGHT)) {
        if (visorstylenumber.toLowerCase().equals("v1")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/Clip On Sun Visor/Visor - Clip On/V1 Standard - ClipOn/V1 - ClipOn - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v2")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/Clip On Sun Visor/Visor - Clip On/V2 Sandwich - ClipOn/V2 - ClipOn - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v3")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/Clip On Sun Visor/Visor - Clip On/V3 Flipped Edge - ClipOn/V3 - ClipOn - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v4")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/Clip On Sun Visor/Visor - Clip On/V4 Trim - ClipOn/V4 - ClipOn - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v5")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/Clip On Sun Visor/Visor - Clip On/V5 Wave Trim - ClipOn/V5 - ClipOn - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v7")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/Clip On Sun Visor/Visor - Clip On/V7 Curved Trim - ClipOn/V7 - ClipOn - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v8")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/Clip On Sun Visor/Visor - Clip On/V8 Curved Trim Sandwich - ClipOn/V8 - ClipOn - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v10")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/Clip On Sun Visor/Visor - Clip On/V10 Binding Edge - ClipOn/V10 - ClipOn - " + sideviewfilestring + ".svg";
        }
      }
    } else if (captype.equals("sunvisorbinding")) {
      if (sideview == ExtendOrderDataItem.SideView.BACK) {
        if ((closurestylenumber.toLowerCase().equals("c29")) && (sweatbandstylenumber.toLowerCase().equals("s4"))) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/Binding Edge/Closure - Binding Edge Crown Sun Visor/C29_S4_003 - BindingEdgeSV - Back.svg";
        } else if ((closurestylenumber.toLowerCase().equals("c29")) && (sweatbandstylenumber.toLowerCase().equals("s5")) && (sweatbandcolor.equals("003"))) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/Binding Edge/Closure - Binding Edge Crown Sun Visor/C29_S5_003 - BindingEdgeSV - Back.svg";
        } else if ((closurestylenumber.toLowerCase().equals("c29")) && (sweatbandstylenumber.toLowerCase().equals("s5")) && (sweatbandcolor.equals("016"))) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/Binding Edge/Closure - Binding Edge Crown Sun Visor/C29_S5_016 - BindingEdgeSV - Back.svg";
        } else {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/Binding Edge/Closure - Binding Edge Crown Sun Visor/" + closurestylenumber + " - BindingEdgeSV - Back.svg";
        }
      } else if (sideview == ExtendOrderDataItem.SideView.INSIDE) {
        if (closurestylenumber.toLowerCase().equals("c23")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/Binding Edge/Inside/C23 Elastic Strap - Binding Edge Sun Visor/" + visorstylenumber + "_" + "C23 - BindingEdgeSV - Inside.svg";
        } else if (closurestylenumber.toLowerCase().equals("c30")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/Binding Edge/Inside/C30 Adj Hook Loop - Binding Edge Sun Visor/" + visorstylenumber + "_" + "C30 - BindingEdgeSV - Inside.svg";
        } else if (closurestylenumber.toLowerCase().equals("c31")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/Binding Edge/Inside/C31 Plastic Snap - Binding Edge Sun Visor/" + visorstylenumber + "_" + "C31 - BindingEdgeSV - Inside.svg";
        } else if (closurestylenumber.toLowerCase().equals("c32")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/Binding Edge/Inside/C32 Clip On - Binding Edge Sun Visor/" + visorstylenumber + "_" + "C32 - BindingEdgeSV - Inside.svg";
        } else if (closurestylenumber.toLowerCase().equals("c29")) {
          if (sweatbandstylenumber.toLowerCase().equals("s4")) {
            fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/Binding Edge/Inside/C29 Enclosed - Binding Edge Sun Visor/C29 S4 003 Enclosed - Binding Edge Sun Visor/" + visorstylenumber + "_" + "C29 - BindingEdgeSV - Inside.svg";
          } else if ((sweatbandstylenumber.toLowerCase().equals("s5")) && (sweatbandcolor.equals("003"))) {
            fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/Binding Edge/Inside/C29 Enclosed - Binding Edge Sun Visor/C29 S5 003 Enclosed - Binding Edge Sun Visor/" + visorstylenumber + "_" + "C29 - BindingEdgeSV - Inside.svg";
          } else if ((sweatbandstylenumber.toLowerCase().equals("s5")) && (sweatbandcolor.equals("016"))) {
            fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/Binding Edge/Inside/C29 Enclosed - Binding Edge Sun Visor/C29 S5 016 Enclosed - Binding Edge Sun Visor/" + visorstylenumber + "_" + "C29 - BindingEdgeSV - Inside.svg";
          } else {
            fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/Binding Edge/Inside/C29 Enclosed - Binding Edge Sun Visor/" + visorstylenumber + "_" + "C29 - BindingEdgeSV - Inside.svg";
          }
        }
      } else if ((sideview == ExtendOrderDataItem.SideView.FRONT) || (sideview == ExtendOrderDataItem.SideView.LEFT) || (sideview == ExtendOrderDataItem.SideView.RIGHT)) {
        if (visorstylenumber.toLowerCase().equals("v1")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/Binding Edge/Visor - Binding Edge Crown Sun Visor/V1  Standard - Binding Edge Sun Visor/V1 - BindingEdgeSV - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v2")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/Binding Edge/Visor - Binding Edge Crown Sun Visor/V2 Sandwich - Binding Edge Sun Visor/V2 - BindingEdgeSV - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v3")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/Binding Edge/Visor - Binding Edge Crown Sun Visor/V3 Flipped Edge - Binding Edge Sun Visor/V3 - BindingEdgeSV - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v4")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/Binding Edge/Visor - Binding Edge Crown Sun Visor/V4 Trim - Binding Edge Sun Visor/V4 - BindingEdgeSV - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v5")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/Binding Edge/Visor - Binding Edge Crown Sun Visor/V5 Wave Trim - Binding Edge Sun Visor/V5 - BindingEdgeSV - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v6")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/Binding Edge/Visor - Binding Edge Crown Sun Visor/V6 Distressed - Binding Edge Sun Visor/V6 - BindingEdgeSV - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v7")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/Binding Edge/Visor - Binding Edge Crown Sun Visor/V7 Curved Trim - Binding Edge Sun Visor/V7 - BindingEdgeSV - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v8")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/Binding Edge/Visor - Binding Edge Crown Sun Visor/V8 Curved Trim Sandwich - Binding Edge Sun Visor/V8 - BindingEdgeSV - " + sideviewfilestring + ".svg";
        } else if (visorstylenumber.toLowerCase().equals("v10")) {
          fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Sun Visor/Binding Edge/Visor - Binding Edge Crown Sun Visor/V10 Binding Edge - Binding Edge Sun Visor/V10 - BindingEdgeSV - " + sideviewfilestring + ".svg";
        }
      }
    } else if (captype.equals("sportsshirt")) {
      if (sideview == ExtendOrderDataItem.SideView.FRONT) {
        fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/apperal/sportshirt_front.svg";
      } else if (sideview == ExtendOrderDataItem.SideView.BACK) {
        fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/apperal/sportshirt_back.svg";
      } else if (sideview == ExtendOrderDataItem.SideView.LEFT) {
        fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/apperal/sportshirt_leftside.svg";
      } else if (sideview == ExtendOrderDataItem.SideView.RIGHT) {
        fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/apperal/sportshirt_rightside.svg";
      }
      
    }
    else if ((captype.equals("beanie")) || (captype.equals("beaniestripe")) || (captype.equals("beaniefold")) || (captype.equals("wristband")) || (captype.equals("headband"))) {
      fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/lineart/" + captype + "_" + sideviewfilestring + ".svg";
    }
    else if (sideview == ExtendOrderDataItem.SideView.BACK) {
      fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/lineart/" + captype + "_" + closurestylenumber + "_" + sideviewfilestring + ".svg";
    } else if (sideview == ExtendOrderDataItem.SideView.INSIDE) {
      if (captype.equals("sunvisorclip")) {
        fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/lineart/" + captype + "_" + visorstylenumber + "_" + sideviewfilestring + ".svg";
      } else {
        fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/lineart/" + captype + "_" + visorstylenumber + "_" + closurestylenumber + "_" + sideviewfilestring + ".svg";
      }
    } else {
      fullpath = "C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/lineart/" + captype + "_" + visorstylenumber + "_" + sideviewfilestring + ".svg";
    }
    





    File myfile = new File(fullpath);
    if (!myfile.exists()) {
      return;
    }
    
    FileInputStream fstream = new FileInputStream(myfile);
    byte[] context = new byte[(int)myfile.length()];
    fstream.read(context);
    fstream.close();
    
    String fileData = new String(context);
    
    String widthstring = fileData.substring(fileData.indexOf("width=\"") + 7);
    widthstring = widthstring.substring(0, widthstring.indexOf("px\""));
    String heightstring = fileData.substring(fileData.indexOf("height=\"") + 8);
    heightstring = heightstring.substring(0, heightstring.indexOf("px\""));
    
    this.svgwidth = ((int)Math.ceil(Double.valueOf(widthstring).doubleValue()));
    this.svgheight = ((int)Math.ceil(Double.valueOf(heightstring).doubleValue()));
    
    fileData = replacecolor(fileData, "colorcode", ottocodetocolorhtml(colorcode), "fill");
    fileData = replacecolor(fileData, "visorsandwichcolor", ottocodetocolorhtml(visorsandwichcolor), "fill");
    fileData = replacecolor(fileData, "undervisorcolor", ottocodetocolorhtml(undervisorcolor), "fill");
    fileData = replacecolor(fileData, "distressedprimaryvisorcolor", ottocodetocolorhtml(distressedvisorinsidecolor), "fill");
    fileData = replacecolor(fileData, "closurestrapcolor", ottocodetocolorhtml(closurestrapcolor), "fill");
    fileData = replacecolor(fileData, "innertapingcolor", ottocodetocolorhtml(innertapingcolor), "fill");
    fileData = replacecolor(fileData, "innerarchtapingcolor", ottocodetocolorhtml(innertapingcolor), "fill");
    
    fileData = replacecolor(fileData, "sweatbandcolor", ottocodetocolorhtml(sweatbandcolor), "fill");
    fileData = replacecolor(fileData, "s2sweatbandcolor", ottocodetocolorhtml(sweatbandstripecolor), "fill");
    fileData = replacecolor(fileData, "s5sweatbandcolor", ottocodetocolorhtml(sweatbandcolor), "fill");
    
    fileData = replacecolor(fileData, "primaryvisorcolor", ottocodetocolorhtml(primaryvisorcolor), "fill");
    fileData = replacecolor(fileData, "visortrimcolor", ottocodetocolorhtml(visortrimcolor), "fill");
    
    if ((sideview == ExtendOrderDataItem.SideView.INSIDE) && (captype.equals("5pmeshf"))) {
      fileData = replacecolor(fileData, "frontpanelcolor", "#F2ECB8", "fill");
      fileData = replacecolor(fileData, "leftfrontpanelcolor", "#F2ECB8", "fill");
      fileData = replacecolor(fileData, "rightfrontpanelcolor", "#F2ECB8", "fill");
    } else if ((sideview == ExtendOrderDataItem.SideView.INSIDE) && (structuredcap)) {
      fileData = replacecolor(fileData, "frontpanelcolor", "#DFDEDB", "fill");
      fileData = replacecolor(fileData, "leftfrontpanelcolor", "#DFDEDB", "fill");
      fileData = replacecolor(fileData, "rightfrontpanelcolor", "#DFDEDB", "fill");
    } else {
      fileData = replacecolor(fileData, "frontpanelcolor", ottocodetocolorhtml(frontpanelcolor), "fill");
      fileData = replacecolor(fileData, "leftfrontpanelcolor", ottocodetocolorhtml(frontpanelcolor), "fill");
      fileData = replacecolor(fileData, "rightfrontpanelcolor", ottocodetocolorhtml(frontpanelcolor), "fill");
    }
    fileData = replacecolor(fileData, "backpanelcolor", ottocodetocolorhtml(backpanelcolor), "fill");
    fileData = replacecolor(fileData, "meshcolor", ottocodetocolorhtml(backpanelcolor), "fill");
    fileData = replacecolor(fileData, "meshpanelcolor", ottocodetocolorhtml(backpanelcolor), "fill");
    fileData = replacecolor(fileData, "leftbackpanelcolor", ottocodetocolorhtml(backpanelcolor), "fill");
    fileData = replacecolor(fileData, "rightbackpanelcolor", ottocodetocolorhtml(backpanelcolor), "fill");
    fileData = replacecolor(fileData, "leftsidepanelcolor", ottocodetocolorhtml(sidepanelcolor), "fill");
    fileData = replacecolor(fileData, "rightsidepanelcolor", ottocodetocolorhtml(sidepanelcolor), "fill");
    fileData = replacecolor(fileData, "insidecrowncolor", ottocodetocolorhtml(frontpanelcolor), "fill");
    
    fileData = replacecolor(fileData, "buttoncolor", ottocodetocolorhtml(buttoncolor), "fill");
    fileData = replacecolor(fileData, "fronteyeletcolor", darkenotto(fronteyeletcolor, 1), "stroke");
    if (!backeyeletcolor.equals("")) {
      fileData = replacecolor(fileData, "backeyeletcolor", darkenotto(backeyeletcolor, 1), "stroke");
    } else {
      fileData = replacecolor(fileData, "backeyeletcolor", darkenotto(sidebackeyeletcolor, 1), "stroke");
    }
    if (!sideeyeletcolor.equals("")) {
      fileData = replacecolor(fileData, "sideeyeletcolor", darkenotto(sideeyeletcolor, 1), "stroke");
    } else {
      fileData = replacecolor(fileData, "sideeyeletcolor", darkenotto(sidebackeyeletcolor, 1), "stroke");
    }
    fileData = replacecolor(fileData, "distressedprimaryvisorcolor", darkenotto(primaryvisorcolor, 1), "stroke");
    
    if (!panelstitchcolor.equals("")) {
      fileData = replacegroupcolor(fileData, "capstitches", ottocodetocolorhtml(panelstitchcolor), "stroke");
    }
    if (!visorstitchcolor.equals("")) {
      fileData = replacegroupcolor(fileData, "visorstitches", ottocodetocolorhtml(visorstitchcolor), "stroke");
    }
    

    fileData = replacecolor(fileData, "toppanelcolor", ottocodetocolorhtml(frontpanelcolor), "fill");
    fileData = replacecolor(fileData, "bandcolor", ottocodetocolorhtml(frontpanelcolor), "fill");
    fileData = replacecolor(fileData, "leftpanelcolor", ottocodetocolorhtml(backpanelcolor), "fill");
    fileData = replacecolor(fileData, "rightpanelcolor", ottocodetocolorhtml(backpanelcolor), "fill");
    fileData = replacecolor(fileData, "leftpanelshadecolor", darkenotto(backpanelcolor, 1), "fill");
    fileData = replacecolor(fileData, "rightpanelshadecolor", darkenotto(backpanelcolor, 1), "fill");
    fileData = replacecolor(fileData, "bandshadecolor", darkenotto(frontpanelcolor, 1), "fill");
    fileData = replacecolor(fileData, "distresscapcolor", ottocodetocolorhtml(backpanelcolor), "fill");
    fileData = replacecolor(fileData, "distresscapcolor", darkenotto(backpanelcolor, 1), "stroke");
    fileData = replacecolor(fileData, "distressedcapcolor", ottocodetocolorhtml(backpanelcolor), "fill");
    fileData = replacecolor(fileData, "distressedcapcolor", darkenotto(backpanelcolor, 1), "stroke");
    

    fileData = replacecolor(fileData, "bindingcolor", darkenotto(frontpanelcolor, 1), "fill");
    fileData = replacecolor(fileData, "panelcolor", ottocodetocolorhtml(frontpanelcolor), "fill");
    fileData = replacecolor(fileData, "capcolor", ottocodetocolorhtml(frontpanelcolor), "fill");
    fileData = replacecolor(fileData, "sweatband2color", ottocodetocolorhtml(sweatbandstripecolor), "fill");
    fileData = setCamos(fileData);
    
    this.svgdata = fileData;
  }
  















  private String setCamos(String fileData)
    throws Exception
  {
    fileData = fileData.replace("</svg>", "");
    
    String[] mykeys = (String[])this.camoused.toArray(new String[0]);
    for (int i = 0; i < mykeys.length; i++) {
      String camoname = mykeys[i];
      
      File myfile = new File("C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Camo Patterns/" + camoname + ".png");
      BufferedImage myimage = ImageIO.read(myfile);
      int width = myimage.getWidth();
      int height = myimage.getHeight();
      
      double percent = this.svgwidth / width;
      double newwidth = width * percent;
      double newheight = height * percent;
      
      if (this.svgheight > newheight) {
        percent = this.svgheight / height;
        newwidth = width * percent;
        newheight = height * percent;
      }
      
      String fileloc = "C:\\WorkFlow\\NewWorkflowData\\";
      fileloc.replace(" ", "%20");
      fileData = fileData + "<pattern patternUnits=\"userSpaceOnUse\" width=\"" + this.svgwidth + "\" height=\"" + this.svgheight + "\" id=\"" + camoname + "\">";
      




      fileData = fileData + "<image xlink:href=\"file:///" + fileloc + "images/linedrawing/Camo%20Patterns/" + camoname + ".png\" width=\"" + (int)Math.ceil(newwidth) + "\" height=\"" + (int)Math.ceil(newheight) + "\"/>";
      fileData = fileData + "</pattern>";
    }
    



























    fileData = fileData + "</svg>";
    
    return fileData;
  }
  




  public BufferedImage getPNGImage()
    throws Exception
  {
    if (this.svgdata.equals("")) {
      return null;
    }
    
    ByteArrayInputStream mystream = new ByteArrayInputStream(this.svgdata.getBytes());
    ByteArrayOutputStream myoutputstream = new ByteArrayOutputStream();
    
    PNGTranscoder mypng = new PNGTranscoder();
    mypng.addTranscodingHint(PNGTranscoder.KEY_BACKGROUND_COLOR, Color.WHITE);
    
    TranscoderInput input = new TranscoderInput(mystream);
    TranscoderOutput output = new TranscoderOutput(myoutputstream);
    
    mypng.transcode(input, output);
    
    ByteArrayInputStream myinputstream = new ByteArrayInputStream(myoutputstream.toByteArray());
    return ImageIO.read(myinputstream);
  }
  



  public void showPNGImage(HttpServletResponse response)
    throws Exception
  {
    BufferedImage myimage = getPNGImage();
    if (myimage == null) {
      response.getOutputStream().print("Bad Image");
    } else {
      response.setContentType("image/png");
      ImageIO.write(myimage, "png", response.getOutputStream());
    }
  }
  




  public void showResizedPNGImage(HttpServletResponse response, int width, int height)
    throws Exception
  {
    BufferedImage myimage = getPNGImage();
    if (myimage == null) {
      response.getOutputStream().print("Bad Image");
    } else {
      response.setContentType("image/png");
      ImageIO.write(scaleToFit(myimage, width, height), "png", response.getOutputStream());
    }
  }
  
  private BufferedImage scaleToFit(BufferedImage myimage, int maxwidth, int maxheight) throws Exception
  {
    if (myimage.getHeight() > maxheight)
    {
      BufferedImage scaleimage = new BufferedImage((int)(myimage.getWidth() * (maxheight / myimage.getHeight())), maxheight, 1);
      Graphics2D g = scaleimage.createGraphics();
      g.drawImage(myimage.getScaledInstance((int)(myimage.getWidth() * (maxheight / myimage.getHeight())), maxheight, 4), 0, 0, null);
      g.dispose();
      myimage = scaleimage;
    }
    if (myimage.getWidth() > maxwidth)
    {
      BufferedImage scaleimage = new BufferedImage(maxwidth, (int)(myimage.getHeight() * (maxwidth / myimage.getWidth())), 1);
      Graphics2D g = scaleimage.createGraphics();
      g.drawImage(myimage.getScaledInstance(maxwidth, (int)(myimage.getHeight() * (maxwidth / myimage.getWidth())), 4), 0, 0, null);
      g.dispose();
      myimage = scaleimage;
    }
    return myimage;
  }
  







  private String ottocodetocolorhtml(String code)
    throws Exception
  {
    if ((code == null) || (code.equals("")))
      return "none";
    if (code.startsWith("#")) {
      return code;
    }
    
    if (code.endsWith("SC")) {
      code = code.substring(0, code.length() - 2);
    }
    
    Boolean foundswatch = Boolean.valueOf(false);
    String structurematerial = "";
    if (this.style.length() % 2 == 0) {
      structurematerial = this.style.substring(2);
    } else {
      structurematerial = this.style.substring(3);
    }
    Boolean isWashed = Boolean.valueOf(false);
    String structure = "";
    try {
      structure = structurematerial.substring(0, 2);
    }
    catch (Exception localException) {}
    
    if (this.mytable.makeTable("SELECT * FROM `list_overseas_work_structure` WHERE `id` = '" + structure + "' AND `name` REGEXP 'washed' LIMIT 1").booleanValue()) {
      isWashed = Boolean.valueOf(true);
    }
    String material = "";
    try {
      material = structurematerial.substring(2, 4);
    }
    catch (Exception localException1) {}
    
    String swatchfile = material;
    if (isWashed.booleanValue()) {
      swatchfile = swatchfile + "G";
    }
    swatchfile = swatchfile + "_" + code;
    

    File myfile = new File("C:\\WorkFlow\\NewWorkflowData\\images/linedrawing/Camo Patterns/" + swatchfile + ".png");
    
    String realswatchtouse = "";
    if (myfile.exists()) {
      foundswatch = Boolean.valueOf(true);
      realswatchtouse = swatchfile;
    }
    
    if (foundswatch.booleanValue()) {
      this.camoused.add(realswatchtouse);
      return "url(#" + realswatchtouse + ")";
    }
    


    if (this.mytable.makeTable("SELECT * FROM `thread_ottocolors` WHERE `code` = '" + code + "'" + " LIMIT 1").booleanValue()) {
      if (this.colortype.equals("Garment Washed")) {
        String thecolor = ((String)this.mytable.getCell("washedcolor")).trim();
        if (!thecolor.equals("")) {
          return thecolor;
        }
        return ((String)this.mytable.getCell("colorvalue")).trim();
      }
      if (this.colortype.equals("Washed Pigment Dyed")) {
        String thecolor = ((String)this.mytable.getCell("pigmentdyed")).trim();
        if (!thecolor.equals("")) {
          return thecolor;
        }
        return ((String)this.mytable.getCell("colorvalue")).trim();
      }
      
      return ((String)this.mytable.getCell("colorvalue")).trim();
    }
    
    return "#FFFFFF";
  }
  










  private String darkenotto(String code, int amount)
    throws Exception
  {
    String htmlvalue = ottocodetocolorhtml(code);
    if (htmlvalue.equals("none")) {
      return "none";
    }
    if (htmlvalue.startsWith("url")) {
      return htmlvalue;
    }
    Color mycolor = Color.decode(htmlvalue);
    for (int i = 0; i < amount; i++) {
      mycolor = mycolor.brighter();
    }
    return String.format("#%02X%02X%02X", new Object[] { Integer.valueOf(mycolor.getRed()), Integer.valueOf(mycolor.getGreen()), Integer.valueOf(mycolor.getBlue()) });
  }
  












  private String replacecolor(String fileData, String commentname, String color, String vartochange)
  {
    fileData = svgreplace(fileData, commentname, color, vartochange, "path");
    fileData = svgreplace(fileData, commentname, color, vartochange, "polygon");
    fileData = svgreplace(fileData, commentname, color, vartochange, "rect");
    return fileData;
  }
  














  private String svgreplace(String fileData, String commentname, String color, String vartochange, String type)
  {
    int lastIndex = 0;
    
    while (lastIndex != -1) {
      lastIndex = fileData.indexOf("<" + type + " id=\"" + commentname, lastIndex + 1);
      
      if (lastIndex != -1) {
        String mysubstring = fileData.substring(lastIndex, fileData.indexOf("/>", lastIndex));
        String tochange = mysubstring.substring(mysubstring.indexOf(vartochange));
        fileData = fileData.substring(0, lastIndex) + mysubstring.substring(0, mysubstring.indexOf(vartochange)) + tochange.substring(0, tochange.indexOf("=\"") + 2) + color + tochange.substring(tochange.indexOf("\" ")) + fileData.substring(fileData.indexOf("/>", lastIndex));
      }
    }
    return fileData;
  }
  



  private String replacegroupcolor(String fileData, String commentname, String color, String vartochange)
  {
    int lastIndex = 0;
    while (lastIndex != -1) {
      lastIndex = fileData.indexOf("<g id=\"" + commentname, lastIndex + 1);
      
      int currentpointer = lastIndex;
      
      int moregroups = 0;
      if (lastIndex != -1) {
        while (moregroups > -1)
        {
          int groupend = fileData.indexOf("</g>", currentpointer + 1);
          int groupstart = fileData.indexOf("<g", currentpointer + 1);
          
          if ((groupstart < groupend) && (groupstart != -1)) {
            moregroups++;
          } else {
            moregroups--;
          }
          
          if (moregroups > 0) {
            currentpointer = groupstart;
          }
          if (moregroups < 0) {
            currentpointer = groupend;
          }
        }
        

        String mysubstring = fileData.substring(lastIndex, currentpointer);
        
        mysubstring = svggroupreplacesub(mysubstring, color, vartochange, "path");
        mysubstring = svggroupreplacesub(mysubstring, color, vartochange, "polyline");
        
        fileData = fileData.substring(0, lastIndex) + mysubstring + fileData.substring(currentpointer);
      }
    }
    





    return fileData;
  }
  
  private String svggroupreplacesub(String fileData, String color, String vartochange, String type) {
    int lastIndex = 0;
    
    while (lastIndex != -1) {
      lastIndex = fileData.indexOf("<" + type + " ", lastIndex + 1);
      
      if (lastIndex != -1) {
        String mysubstring = fileData.substring(lastIndex, fileData.indexOf("/>", lastIndex));
        String tochange = mysubstring.substring(mysubstring.indexOf(vartochange));
        fileData = fileData.substring(0, lastIndex) + mysubstring.substring(0, mysubstring.indexOf(vartochange)) + tochange.substring(0, tochange.indexOf("=\"") + 2) + color + tochange.substring(tochange.indexOf("\" ")) + fileData.substring(fileData.indexOf("/>", lastIndex));
      }
    }
    return fileData;
  }
}
