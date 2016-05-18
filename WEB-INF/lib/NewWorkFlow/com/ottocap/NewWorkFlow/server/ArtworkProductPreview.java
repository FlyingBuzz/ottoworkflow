package com.ottocap.NewWorkFlow.server;

import com.extjs.gxt.ui.client.core.FastMap;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;





public class ArtworkProductPreview
  extends HttpServlet
{
  private static final long serialVersionUID = 1L;
  ServletOutputStream out;
  String ordertype = "Domestic";
  
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    this.out = response.getOutputStream();
    try {
      mainPreview(request);
    } catch (Exception e) {
      e.printStackTrace();
      this.out.print(e.getLocalizedMessage());
    }
  }
  
  public void mainPreview(HttpServletRequest request) throws Exception {
    SQLTable mytable = new SQLTable();
    
    boolean differentcolors = false;
    FastMap<Object> therow = null;
    if (request.getParameter("hiddenkey") != null)
    {
      mytable.makeTable("SELECT ordertype FROM `ordermain` WHERE `hiddenkey`=" + request.getParameter("hiddenkey") + " LIMIT 1");
      this.ordertype = ((String)mytable.getValue());
      
      mytable.makeTable("SELECT * FROM `ordermain_sets_items` WHERE `hiddenkey`=" + request.getParameter("hiddenkey") + " AND `set`=" + request.getParameter("set") + " AND `item`=" + request.getParameter("item") + " LIMIT 1");
      therow = mytable.getRow();
      
      if (request.getParameter("oldstyle") != null) {
        if (this.ordertype.equals("Domestic")) {
          if ((!therow.get("stylenumber").equals(request.getParameter("oldstyle"))) || (!therow.get("colorcode").equals(request.getParameter("oldcolor")))) {
            differentcolors = true;
          }
        }
        else if ((!therow.get("stylenumber").equals(request.getParameter("oldstyle"))) || (!therow.get("colorcode").equals(request.getParameter("oldcolor"))) || (!therow.get("frontpanelcolor").equals(request.getParameter("oldfrontpanelcolor"))) || (!therow.get("sidebackpanelcolor").equals(request.getParameter("oldsidebackpanelcolor"))) || (!therow.get("backpanelcolor").equals(request.getParameter("oldbackpanelcolor"))) || (!therow.get("sidepanelcolor").equals(request.getParameter("oldsidepanelcolor"))) || (!therow.get("visorstylenumber").equals(request.getParameter("oldvisorstylenumber"))) || (!therow.get("primaryvisorcolor").equals(request.getParameter("oldprimaryvisorcolor"))) || (!therow.get("visortrimcolor").equals(request.getParameter("oldvisortrimcolor"))) || (!therow.get("visorsandwichcolor").equals(request.getParameter("oldvisorsandwichcolor"))) || (!therow.get("undervisorcolor").equals(request.getParameter("oldundervisorcolor"))) || 
          (!therow.get("distressedvisorinsidecolor").equals(request.getParameter("olddistressedvisorinsidecolor"))) || (!therow.get("closurestylenumber").equals(request.getParameter("oldclosurestylenumber"))) || (!therow.get("closurestrapcolor").equals(request.getParameter("oldclosurestrapcolor"))) || (!therow.get("eyeletstylenumber").equals(request.getParameter("oldeyeletstylenumber"))) || (!therow.get("eyeletcolor").equals(request.getParameter("oldfronteyeletcolor"))) || (!therow.get("sidebackeyeletcolor").equals(request.getParameter("oldsidebackeyeletcolor"))) || (!therow.get("backeyeletcolor").equals(request.getParameter("oldbackeyeletcolor"))) || (!therow.get("sideeyeletcolor").equals(request.getParameter("oldsideeyeletcolor"))) || (!therow.get("buttoncolor").equals(request.getParameter("oldbuttoncolor"))) || (!therow.get("innertapingcolor").equals(request.getParameter("oldinnertapingcolor"))) || 
          (!therow.get("sweatbandstylenumber").equals(request.getParameter("oldsweatbandstylenumber"))) || (!therow.get("sweatbandcolor").equals(request.getParameter("oldsweatbandcolor"))) || (!therow.get("sweatbandstripecolor").equals(request.getParameter("oldsweatbandstripecolor"))) || (!therow.get("panelstitchcolor").equals(request.getParameter("oldpanelstitchcolor"))) || (!therow.get("visorrowstitching").equals(request.getParameter("oldvisorrowstitching"))) || (!therow.get("visorstitchcolor").equals(request.getParameter("oldvisorstitchcolor"))) || (!therow.get("profile").equals(request.getParameter("oldprofile"))) || (!therow.get("frontpanelfabric").equals(request.getParameter("oldfrontpanelfabric"))) || (!therow.get("sidebackpanelfabric").equals(request.getParameter("oldsidebackpanelfabric"))) || (!therow.get("backpanelfabric").equals(request.getParameter("oldbackpanelfabric"))) || (!therow.get("sidepanelfabric").equals(request.getParameter("oldsidepanelfabric"))) || 
          (!therow.get("crownconstruction").equals(request.getParameter("oldcrownconstruction"))) || (!therow.get("numberofpanels").equals(request.getParameter("oldnumberofpanels")))) {
          differentcolors = true;
        }
      }
    }
    else
    {
      differentcolors = true;
    }
    
    this.out.print("<table>");
    
    if (differentcolors)
    {
      this.out.print("<tr><td>PDF Style: " + request.getParameter("oldstyle"));
      if (!request.getParameter("oldcolor").equals("")) {
        this.out.print("_" + request.getParameter("oldcolor"));
      }
      this.out.print("</td></tr><tr><td>");
      
      String styletype = "";
      String category = "";
      
      if (this.ordertype.equals("Overseas")) {
        if (mytable.makeTable("SELECT * FROM `styles_overseasinstock` WHERE `STYLE NUMBER` = '" + request.getParameter("oldstyle") + "' LIMIT 1").booleanValue()) {
          styletype = "OVERSEAS_INSTOCK";
          category = (String)mytable.getCell("Category");
        } else if (mytable.makeTable("SELECT * FROM `styles_overseas` WHERE `Style` = '" + request.getParameter("oldstyle") + "' LIMIT 1").booleanValue()) {
          styletype = "OVERSEAS";
        } else if (mytable.makeTable("SELECT * FROM `styles_pre-designed` WHERE `Style` = '" + request.getParameter("oldstyle") + "' LIMIT 1").booleanValue()) {
          styletype = "OVERSEAS_PREDESIGNED";
        } else if (mytable.makeTable("SELECT * FROM `overseas_price_instockshirts` WHERE `stylenumber` = '" + request.getParameter("oldstyle") + "' LIMIT 1").booleanValue()) {
          styletype = "OVERSEAS_INSTOCK_SHIRTS";
        } else if (mytable.makeTable("SELECT * FROM `overseas_price_instockflats` WHERE `stylenumber` = '" + request.getParameter("oldstyle") + "' LIMIT 1").booleanValue()) {
          styletype = "OVERSEAS_INSTOCK_FLATS";
        } else if (mytable.makeTable("SELECT * FROM `styles_overseas_customstyle` WHERE `Style` = '" + request.getParameter("oldstyle") + "' LIMIT 1").booleanValue()) {
          styletype = "OVERSEAS_CUSTOMSTYLE";
        }
      }
      else if (mytable.makeTable("SELECT `category` FROM `styles_domestic` WHERE `sku` = '" + request.getParameter("oldstyle") + "' LIMIT 1").booleanValue()) {
        styletype = "DOMESTIC";
        category = (String)mytable.getCell("category");
      } else if (mytable.makeTable("SELECT `basestyle` FROM `styles_domestic_specials` WHERE `style` = '" + request.getParameter("oldstyle") + "' LIMIT 1").booleanValue()) {
        styletype = "DOMESTIC_SPECIAL";
        String basestyle = (String)mytable.getValue();
        if (mytable.makeTable("SELECT `category` FROM `styles_domestic` WHERE `sku` = '" + basestyle + "' LIMIT 1").booleanValue()) {
          category = (String)mytable.getCell("category");
        } else if (mytable.makeTable("SELECT `Main Category` FROM `styles_lackpard` WHERE `Style` = '" + basestyle + "' LIMIT 1").booleanValue()) {
          category = (String)mytable.getCell("Main Category");
        }
      } else if (mytable.makeTable("SELECT * FROM `styles_domestic_shirts` WHERE `sku` = '" + request.getParameter("oldstyle") + "' LIMIT 1").booleanValue()) {
        styletype = "DOMESTIC_SHIRTS";
      } else if (mytable.makeTable("SELECT * FROM `styles_domestic_sweatshirts` WHERE `sku` = '" + request.getParameter("oldstyle") + "' LIMIT 1").booleanValue()) {
        styletype = "DOMESTIC_SWEATSHIRTS";
      } else if (mytable.makeTable("SELECT * FROM `styles_domestic_totebags` WHERE `sku` = '" + request.getParameter("oldstyle") + "' LIMIT 1").booleanValue()) {
        styletype = "DOMESTIC_TOTEBAGS";
      } else if (mytable.makeTable("SELECT * FROM `styles_domestic_aprons` WHERE `sku` = '" + request.getParameter("oldstyle") + "' LIMIT 1").booleanValue()) {
        styletype = "DOMESTIC_APRONS";
      }
      

      if (this.ordertype.equals("Overseas")) {
        if (styletype.equals("OVERSEAS")) {
          this.out.print("<map name=\"mapdata2\"><area shape=\"rect\" coords=\"520,270,625,290\" href=\"../productpreview/?" + echokeysold(request) + "category=" + category + "&ordertype=" + this.ordertype + "&showlayout=1&styletype=" + styletype + "&sideview=FRONT\" target=\"_blank\" alt=\"Front View\" />" + "<area shape=\"rect\" coords=\"980,270,1075,290\" href=\"../productpreview/?" + echokeysold(request) + "category=" + category + "&ordertype=" + this.ordertype + "&showlayout=1&styletype=" + styletype + "&sideview=LEFT\" target=\"_blank\" alt=\"Left View\" />" + "<area shape=\"rect\" coords=\"520,565,620,580\" href=\"../productpreview/?" + echokeysold(request) + "category=" + category + "&ordertype=" + this.ordertype + "&showlayout=1&styletype=" + styletype + "&sideview=BACK\" target=\"_blank\" alt=\"Back View\" />" + "<area shape=\"rect\" coords=\"790,565,890,580\" href=\"../productpreview/?" + echokeysold(request) + "category=" + category + "&ordertype=" + this.ordertype + 
            "&showlayout=1&styletype=" + styletype + "&sideview=RIGHT\" target=\"_blank\" alt=\"Right View\" />" + "<area shape=\"rect\" coords=\"125,565,235,580\" href=\"../productpreview/?" + echokeysold(request) + "category=" + category + "&ordertype=" + this.ordertype + "&showlayout=1&styletype=" + styletype + "&sideview=INSIDE\" target=\"_blank\" alt=\"Inside View\" />" + " </map>");
        } else if (styletype.equals("OVERSEAS_INSTOCK")) {
          if (request.getParameter("oldcolor") == "") {
            this.out.print("<map name=\"mapdata2\"><area shape=\"rect\" coords=\"520,270,625,290\" href=\"../productpreview/?" + echokeysold(request) + "category=" + category + "&ordertype=" + this.ordertype + "&showlayout=1&styletype=" + styletype + "&sideview=FRONT\" target=\"_blank\" alt=\"Front View\" />" + "<area shape=\"rect\" coords=\"980,270,1075,290\" href=\"../productpreview/?" + echokeysold(request) + "category=" + category + "&ordertype=" + this.ordertype + "&showlayout=1&styletype=" + styletype + "&sideview=LEFT\" target=\"_blank\" alt=\"Left View\" />" + "<area shape=\"rect\" coords=\"520,565,620,580\" href=\"../productpreview/?" + echokeysold(request) + "category=" + category + "&ordertype=" + this.ordertype + "&showlayout=1&styletype=" + styletype + "&sideview=BACK\" target=\"_blank\" alt=\"Back View\" />" + "<area shape=\"rect\" coords=\"790,565,890,580\" href=\"../productpreview/?" + echokeysold(request) + "category=" + category + "&ordertype=" + this.ordertype + 
              "&showlayout=1&styletype=" + styletype + "&sideview=RIGHT\" target=\"_blank\" alt=\"Right View\" />" + "<area shape=\"rect\" coords=\"125,565,235,580\" href=\"../productpreview/?" + echokeysold(request) + "category=" + category + "&ordertype=" + this.ordertype + "&showlayout=1&styletype=" + styletype + "&sideview=INSIDE\" target=\"_blank\" alt=\"Inside View\" />" + " </map>");
          } else {
            this.out.print("<map name=\"mapdata2\"><area shape=\"rect\" coords=\"520,270,625,290\" href=\"../ottoflow/images/isample/" + request.getParameter("oldstyle") + "/" + request.getParameter("oldstyle") + "_" + request.getParameter("oldcolor") + "-F.jpg\" target=\"_blank\" alt=\"Front View\" />" + "<area shape=\"rect\" coords=\"980,270,1075,290\" href=\"../ottoflow/images/isample/" + request.getParameter("oldstyle") + "/" + request.getParameter("oldstyle") + "_" + request.getParameter("oldcolor") + "-LS.jpg\" target=\"_blank\" alt=\"Left View\" />" + "<area shape=\"rect\" coords=\"520,565,620,580\" href=\"../ottoflow/images/isample/" + request.getParameter("oldstyle") + "/" + request.getParameter("oldstyle") + "_" + request.getParameter("oldcolor") + "-B.jpg\" target=\"_blank\" alt=\"Back View\" />" + "<area shape=\"rect\" coords=\"790,565,890,580\" href=\"../ottoflow/images/isample/" + request.getParameter("oldstyle") + "/" + request.getParameter("oldstyle") + "_" + 
              request.getParameter("oldcolor") + "-RS.jpg\" target=\"_blank\" alt=\"Right View\" />" + "<area shape=\"rect\" coords=\"125,565,235,580\" href=\"../ottoflow/images/isample/" + request.getParameter("oldstyle") + "/" + request.getParameter("oldstyle") + "_" + request.getParameter("oldcolor") + "-IS.jpg\" target=\"_blank\" alt=\"Inside View\" />" + " </map>");
          }
        } else if ((styletype.equals("OVERSEAS_INSTOCK_SHIRTS")) || (styletype.equals("OVERSEAS_INSTOCK_FLATS"))) {
          this.out.print("<map name=\"mapdata2\"><area shape=\"rect\" coords=\"520,270,625,290\" href=\"../productpreview/?" + echokeysold(request) + "category=" + category + "&ordertype=" + this.ordertype + "&showlayout=1&styletype=" + styletype + "&sideview=FRONT\" target=\"_blank\" alt=\"Front View\" />" + "<area shape=\"rect\" coords=\"980,270,1075,290\" href=\"../productpreview/?" + echokeysold(request) + "category=" + category + "&ordertype=" + this.ordertype + "&showlayout=1&styletype=" + styletype + "&sideview=LEFT\" target=\"_blank\" alt=\"Left View\" />" + "<area shape=\"rect\" coords=\"520,565,620,580\" href=\"../productpreview/?" + echokeysold(request) + "category=" + category + "&ordertype=" + this.ordertype + "&showlayout=1&styletype=" + styletype + "&sideview=BACK\" target=\"_blank\" alt=\"Back View\" />" + "<area shape=\"rect\" coords=\"790,565,890,580\" href=\"../productpreview/?" + echokeysold(request) + "category=" + category + "&ordertype=" + this.ordertype + 
            "&showlayout=1&styletype=" + styletype + "&sideview=RIGHT\" target=\"_blank\" alt=\"Right View\" /></map>");
        } else if (styletype.equals("OVERSEAS_PREDESIGNED")) {
          this.out.print("<map name=\"mapdata2\"><area shape=\"rect\" coords=\"520,270,625,290\" href=\"../ottoflow/images/isample/predesigned/" + request.getParameter("oldstyle") + "-F.jpg\" target=\"_blank\" alt=\"Front View\" />" + "<area shape=\"rect\" coords=\"980,270,1075,290\" href=\"../ottoflow/images/isample/predesigned/" + request.getParameter("oldstyle") + "-LS.jpg\" target=\"_blank\" alt=\"Left View\" />" + "<area shape=\"rect\" coords=\"520,565,620,580\" href=\"../ottoflow/images/isample/predesigned/" + request.getParameter("oldstyle") + "-B.jpg\" target=\"_blank\" alt=\"Back View\" />" + "<area shape=\"rect\" coords=\"790,565,890,580\" href=\"../ottoflow/images/isample/predesigned/" + request.getParameter("oldstyle") + "-RS.jpg\" target=\"_blank\" alt=\"Right View\" />" + "<area shape=\"rect\" coords=\"125,565,235,580\" href=\"../ottoflow/images/isample/predesigned/" + request.getParameter("oldstyle") + "-IS.jpg\" target=\"_blank\" alt=\"Inside View\" />" + 
            " </map>");
        } else if (styletype.equals("OVERSEAS_CUSTOMSTYLE")) {
          this.out.print("<map name=\"mapdata2\"><area shape=\"rect\" coords=\"520,270,625,290\" href=\"../productpreview/?" + echokeysold(request) + "category=" + category + "&ordertype=" + this.ordertype + "&showlayout=1&styletype=" + styletype + "&sideview=FRONT\" target=\"_blank\" alt=\"Front View\" />" + "<area shape=\"rect\" coords=\"980,270,1075,290\" href=\"../productpreview/?" + echokeysold(request) + "category=" + category + "&ordertype=" + this.ordertype + "&showlayout=1&styletype=" + styletype + "&sideview=LEFT\" target=\"_blank\" alt=\"Left View\" />" + "<area shape=\"rect\" coords=\"520,565,620,580\" href=\"../productpreview/?" + echokeysold(request) + "category=" + category + "&ordertype=" + this.ordertype + "&showlayout=1&styletype=" + styletype + "&sideview=BACK\" target=\"_blank\" alt=\"Back View\" />" + "<area shape=\"rect\" coords=\"790,565,890,580\" href=\"../productpreview/?" + echokeysold(request) + "category=" + category + "&ordertype=" + this.ordertype + 
            "&showlayout=1&styletype=" + styletype + "&sideview=RIGHT\" target=\"_blank\" alt=\"Right View\" />" + "<area shape=\"rect\" coords=\"125,565,235,580\" href=\"../productpreview/?" + echokeysold(request) + "category=" + category + "&ordertype=" + this.ordertype + "&showlayout=1&styletype=" + styletype + "&sideview=INSIDE\" target=\"_blank\" alt=\"Inside View\" />" + " </map>");
        }
        this.out.print("<img border=\"0\" usemap=\"#mapdata2\" src=\"../productpreview/?" + echokeysold(request) + "category=" + category + "&ordertype=" + this.ordertype + "&showlayout=1&styletype=" + styletype + "\"></img></a>");
      } else {
        if (styletype.equals("DOMESTIC")) {
          this.out.print("<map name=\"mapdata2\"><area shape=\"rect\" coords=\"160,300,265,320\" href=\"../ottoflow/images/isample/" + request.getParameter("oldstyle") + "/" + request.getParameter("oldstyle") + "_" + request.getParameter("oldcolor") + "-F.jpg\" target=\"_blank\" alt=\"Front View\" />" + "<area shape=\"rect\" coords=\"660,300,755,320\" href=\"../ottoflow/images/isample/" + request.getParameter("oldstyle") + "/" + request.getParameter("oldstyle") + "_" + request.getParameter("oldcolor") + "-LS.jpg\" target=\"_blank\" alt=\"Left View\" />" + "<area shape=\"rect\" coords=\"160,620,265,635\" href=\"../ottoflow/images/isample/" + request.getParameter("oldstyle") + "/" + request.getParameter("oldstyle") + "_" + request.getParameter("oldcolor") + "-B.jpg\" target=\"_blank\" alt=\"Back View\" />" + "<area shape=\"rect\" coords=\"460,620,565,635\" href=\"../ottoflow/images/isample/" + request.getParameter("oldstyle") + "/" + request.getParameter("oldstyle") + "_" + 
            request.getParameter("oldcolor") + "-RS.jpg\" target=\"_blank\" alt=\"Right View\" />" + " </map>");
        } else if (styletype.equals("DOMESTIC_SPECIAL")) {
          String thestyle = request.getParameter("oldstyle").substring(0, request.getParameter("oldstyle").length() - 1);
          this.out.print("<map name=\"mapdata2\"><area shape=\"rect\" coords=\"160,300,265,320\" href=\"../ottoflow/images/isample/" + thestyle + "/" + thestyle + "_" + request.getParameter("oldcolor") + "-F.jpg\" target=\"_blank\" alt=\"Front View\" />" + "<area shape=\"rect\" coords=\"660,300,755,320\" href=\"../ottoflow/images/isample/" + thestyle + "/" + thestyle + "_" + request.getParameter("oldcolor") + "-LS.jpg\" target=\"_blank\" alt=\"Left View\" />" + "<area shape=\"rect\" coords=\"160,620,265,635\" href=\"../ottoflow/images/isample/" + thestyle + "/" + thestyle + "_" + request.getParameter("oldcolor") + "-B.jpg\" target=\"_blank\" alt=\"Back View\" />" + "<area shape=\"rect\" coords=\"460,620,565,635\" href=\"../ottoflow/images/isample/" + thestyle + "/" + thestyle + "_" + request.getParameter("oldcolor") + "-RS.jpg\" target=\"_blank\" alt=\"Right View\" />" + " </map>");
        } else if ((styletype.equals("DOMESTIC_SHIRTS")) || (styletype.equals("DOMESTIC_SWEATSHIRTS"))) {
          this.out.print("<map name=\"mapdata2\"><area shape=\"rect\" coords=\"520,270,625,290\" href=\"../productpreview/?" + echokeysold(request) + "category=" + category + "&ordertype=" + this.ordertype + "&showlayout=1&styletype=" + styletype + "&sideview=FRONT\" target=\"_blank\" alt=\"Front View\" />" + "<area shape=\"rect\" coords=\"980,270,1075,290\" href=\"../productpreview/?" + echokeysold(request) + "category=" + category + "&ordertype=" + this.ordertype + "&showlayout=1&styletype=" + styletype + "&sideview=LEFT\" target=\"_blank\" alt=\"Left View\" />" + "<area shape=\"rect\" coords=\"520,565,620,580\" href=\"../productpreview/?" + echokeysold(request) + "category=" + category + "&ordertype=" + this.ordertype + "&showlayout=1&styletype=" + styletype + "&sideview=BACK\" target=\"_blank\" alt=\"Back View\" />" + "<area shape=\"rect\" coords=\"790,565,890,580\" href=\"../productpreview/?" + echokeysold(request) + "category=" + category + "&ordertype=" + this.ordertype + 
            "&showlayout=1&styletype=" + styletype + "&sideview=RIGHT\" target=\"_blank\" alt=\"Right View\" /></map>");
        } else if (styletype.equals("DOMESTIC_TOTEBAGS")) {
          this.out.print("<map name=\"mapdata2\"><area shape=\"rect\" coords=\"160,615,270,635\" href=\"../ottoflow/images/isample/" + request.getParameter("oldstyle") + "/" + request.getParameter("oldstyle") + "_" + request.getParameter("oldcolor") + "-F.jpg\" target=\"_blank\" alt=\"Front View\" />" + "<area shape=\"rect\" coords=\"575,615,680,635\" href=\"../ottoflow/images/isample/" + request.getParameter("oldstyle") + "/" + request.getParameter("oldstyle") + "_" + request.getParameter("oldcolor") + "-F.jpg\" target=\"_blank\" alt=\"Back View\" />" + " </map>");
        } else if (styletype.equals("DOMESTIC_APRONS")) {
          this.out.print("<map name=\"mapdata2\"><area shape=\"rect\" coords=\"160,615,270,635\" href=\"../ottoflow/images/isample/" + request.getParameter("oldstyle") + "/" + request.getParameter("oldstyle") + "_" + request.getParameter("oldcolor") + "-F.jpg\" target=\"_blank\" alt=\"Front View\" />" + "<area shape=\"rect\" coords=\"575,615,680,635\" href=\"../ottoflow/images/isample/" + request.getParameter("oldstyle") + "/" + request.getParameter("oldstyle") + "_" + request.getParameter("oldcolor") + "-F.jpg\" target=\"_blank\" alt=\"Back View\" />" + " </map>");
        }
        this.out.print("<BR><img border=\"0\" usemap=\"#mapdata2\" src=\"../productpreview/?" + echokeysold(request) + "category=" + category + "&ordertype=" + this.ordertype + "&showlayout=1&styletype=" + styletype + "\"></img></a>");
      }
      
      this.out.print("</td></tr>");
    }
    

    if (request.getParameter("hiddenkey") != null) {
      this.out.print("<tr><td>Current Style: " + therow.get("stylenumber"));
      if (!therow.get("colorcode").equals("")) {
        this.out.print("_" + therow.get("colorcode"));
      }
      this.out.print("</td></tr><tr><td>");
      
      String styletype = "";
      String category = "";
      
      if (this.ordertype.equals("Overseas")) {
        if (mytable.makeTable("SELECT * FROM `styles_overseasinstock` WHERE `STYLE NUMBER` = '" + therow.get("stylenumber") + "' LIMIT 1").booleanValue()) {
          styletype = "OVERSEAS_INSTOCK";
          category = (String)mytable.getCell("Category");
        } else if (mytable.makeTable("SELECT * FROM `styles_overseas` WHERE `Style` = '" + therow.get("stylenumber") + "' LIMIT 1").booleanValue()) {
          styletype = "OVERSEAS";
        } else if (mytable.makeTable("SELECT * FROM `styles_pre-designed` WHERE `Style` = '" + therow.get("stylenumber") + "' LIMIT 1").booleanValue()) {
          styletype = "OVERSEAS_PREDESIGNED";
        } else if (mytable.makeTable("SELECT * FROM `overseas_price_instockshirts` WHERE `stylenumber` = '" + therow.get("stylenumber") + "' LIMIT 1").booleanValue()) {
          styletype = "OVERSEAS_INSTOCK_SHIRTS";
        } else if (mytable.makeTable("SELECT * FROM `overseas_price_instockflats` WHERE `stylenumber` = '" + therow.get("stylenumber") + "' LIMIT 1").booleanValue()) {
          styletype = "OVERSEAS_INSTOCK_FLATS";
        } else if (mytable.makeTable("SELECT * FROM `styles_overseas_customstyle` WHERE `Style` = '" + therow.get("stylenumber") + "' LIMIT 1").booleanValue()) {
          styletype = "OVERSEAS_CUSTOMSTYLE";
        }
      }
      else if (mytable.makeTable("SELECT * FROM `styles_domestic` WHERE `sku` = '" + therow.get("stylenumber") + "' LIMIT 1").booleanValue()) {
        styletype = "DOMESTIC";
        category = (String)mytable.getCell("category");
      } else if (mytable.makeTable("SELECT `basestyle` FROM `styles_domestic_specials` WHERE `style` = '" + therow.get("stylenumber") + "' LIMIT 1").booleanValue()) {
        styletype = "DOMESTIC_SPECIAL";
        String basestyle = (String)mytable.getValue();
        if (mytable.makeTable("SELECT `category` FROM `styles_domestic` WHERE `sku` = '" + basestyle + "' LIMIT 1").booleanValue()) {
          category = (String)mytable.getCell("category");
        } else if (mytable.makeTable("SELECT `Main Category` FROM `styles_lackpard` WHERE `Style` = '" + basestyle + "' LIMIT 1").booleanValue()) {
          category = (String)mytable.getCell("Main Category");
        }
      }
      else if (mytable.makeTable("SELECT * FROM `styles_domestic_shirts` WHERE `sku` = '" + therow.get("stylenumber") + "' LIMIT 1").booleanValue()) {
        styletype = "DOMESTIC_SHIRTS";
      } else if (mytable.makeTable("SELECT * FROM `styles_domestic_sweatshirts` WHERE `sku` = '" + therow.get("stylenumber") + "' LIMIT 1").booleanValue()) {
        styletype = "DOMESTIC_SWEATSHIRTS";
      } else if (mytable.makeTable("SELECT * FROM `styles_domestic_totebags` WHERE `sku` = '" + therow.get("stylenumber") + "' LIMIT 1").booleanValue()) {
        styletype = "DOMESTIC_TOTEBAGS";
      } else if (mytable.makeTable("SELECT * FROM `styles_domestic_aprons` WHERE `sku` = '" + therow.get("stylenumber") + "' LIMIT 1").booleanValue()) {
        styletype = "DOMESTIC_APRONS";
      }
      

      mytable.closeSQL();
      
      if (this.ordertype.equals("Overseas")) {
        if (styletype.equals("OVERSEAS")) {
          this.out.print("<map name=\"mapdata\"><area shape=\"rect\" coords=\"520,270,625,290\" href=\"../productpreview/?" + echokeys(therow) + "category=" + category + "&ordertype=" + this.ordertype + "&showlayout=1&styletype=" + styletype + "&sideview=FRONT\" target=\"_blank\" alt=\"Front View\" />" + "<area shape=\"rect\" coords=\"980,270,1075,290\" href=\"../productpreview/?" + echokeys(therow) + "category=" + category + "&ordertype=" + this.ordertype + "&showlayout=1&styletype=" + styletype + "&sideview=LEFT\" target=\"_blank\" alt=\"Left View\" />" + "<area shape=\"rect\" coords=\"520,565,620,580\" href=\"../productpreview/?" + echokeys(therow) + "category=" + category + "&ordertype=" + this.ordertype + "&showlayout=1&styletype=" + styletype + "&sideview=BACK\" target=\"_blank\" alt=\"Back View\" />" + "<area shape=\"rect\" coords=\"790,565,890,580\" href=\"../productpreview/?" + echokeys(therow) + "category=" + category + "&ordertype=" + this.ordertype + "&showlayout=1&styletype=" + 
            styletype + "&sideview=RIGHT\" target=\"_blank\" alt=\"Right View\" />" + "<area shape=\"rect\" coords=\"125,565,235,580\" href=\"../productpreview/?" + echokeys(therow) + "category=" + category + "&ordertype=" + this.ordertype + "&showlayout=1&styletype=" + styletype + "&sideview=INSIDE\" target=\"_blank\" alt=\"Inside View\" />" + " </map>");
        } else if ((styletype.equals("OVERSEAS_INSTOCK_SHIRTS")) || (styletype.equals("OVERSEAS_INSTOCK_FLATS"))) {
          this.out.print("<map name=\"mapdata\"><area shape=\"rect\" coords=\"520,270,625,290\" href=\"../productpreview/?" + echokeys(therow) + "category=" + category + "&ordertype=" + this.ordertype + "&showlayout=1&styletype=" + styletype + "&sideview=FRONT\" target=\"_blank\" alt=\"Front View\" />" + "<area shape=\"rect\" coords=\"980,270,1075,290\" href=\"../productpreview/?" + echokeys(therow) + "category=" + category + "&ordertype=" + this.ordertype + "&showlayout=1&styletype=" + styletype + "&sideview=LEFT\" target=\"_blank\" alt=\"Left View\" />" + "<area shape=\"rect\" coords=\"520,565,620,580\" href=\"../productpreview/?" + echokeys(therow) + "category=" + category + "&ordertype=" + this.ordertype + "&showlayout=1&styletype=" + styletype + "&sideview=BACK\" target=\"_blank\" alt=\"Back View\" />" + "<area shape=\"rect\" coords=\"790,565,890,580\" href=\"../productpreview/?" + echokeys(therow) + "category=" + category + "&ordertype=" + this.ordertype + "&showlayout=1&styletype=" + 
            styletype + "&sideview=RIGHT\" target=\"_blank\" alt=\"Right View\" /></map>");
        } else if (styletype.equals("OVERSEAS_INSTOCK")) {
          if ((therow.get("colorcode") != null) && (!therow.get("colorcode").equals(""))) {
            this.out.print("<map name=\"mapdata\"><area shape=\"rect\" coords=\"520,270,625,290\" href=\"../ottoflow/images/isample/" + therow.get("stylenumber") + "/" + therow.get("stylenumber") + "_" + therow.get("colorcode") + "-F.jpg\" target=\"_blank\" alt=\"Front View\" />" + "<area shape=\"rect\" coords=\"980,270,1075,290\" href=\"../ottoflow/images/isample/" + therow.get("stylenumber") + "/" + therow.get("stylenumber") + "_" + therow.get("colorcode") + "-LS.jpg\" target=\"_blank\" alt=\"Left View\" />" + "<area shape=\"rect\" coords=\"520,565,620,580\" href=\"../ottoflow/images/isample/" + therow.get("stylenumber") + "/" + therow.get("stylenumber") + "_" + therow.get("colorcode") + "-B.jpg\" target=\"_blank\" alt=\"Back View\" />" + "<area shape=\"rect\" coords=\"790,565,890,580\" href=\"../ottoflow/images/isample/" + therow.get("stylenumber") + "/" + therow.get("stylenumber") + "_" + therow.get("colorcode") + "-RS.jpg\" target=\"_blank\" alt=\"Right View\" />" + 
              "<area shape=\"rect\" coords=\"125,565,235,580\" href=\"../ottoflow/images/isample/" + therow.get("stylenumber") + "/" + therow.get("stylenumber") + "_" + therow.get("colorcode") + "-IS.jpg\" target=\"_blank\" alt=\"Inside View\" />" + " </map>");
          } else {
            this.out.print("<map name=\"mapdata\"><area shape=\"rect\" coords=\"520,270,625,290\" href=\"../productpreview/?" + echokeys(therow) + "category=" + category + "&ordertype=" + this.ordertype + "&showlayout=1&styletype=" + styletype + "&sideview=FRONT\" target=\"_blank\" alt=\"Front View\" />" + "<area shape=\"rect\" coords=\"980,270,1075,290\" href=\"../productpreview/?" + echokeys(therow) + "category=" + category + "&ordertype=" + this.ordertype + "&showlayout=1&styletype=" + styletype + "&sideview=LEFT\" target=\"_blank\" alt=\"Left View\" />" + "<area shape=\"rect\" coords=\"520,565,620,580\" href=\"../productpreview/?" + echokeys(therow) + "category=" + category + "&ordertype=" + this.ordertype + "&showlayout=1&styletype=" + styletype + "&sideview=BACK\" target=\"_blank\" alt=\"Back View\" />" + "<area shape=\"rect\" coords=\"790,565,890,580\" href=\"../productpreview/?" + echokeys(therow) + "category=" + category + "&ordertype=" + this.ordertype + "&showlayout=1&styletype=" + 
              styletype + "&sideview=RIGHT\" target=\"_blank\" alt=\"Right View\" />" + "<area shape=\"rect\" coords=\"125,565,235,580\" href=\"../productpreview/?" + echokeys(therow) + "category=" + category + "&ordertype=" + this.ordertype + "&showlayout=1&styletype=" + styletype + "&sideview=INSIDE\" target=\"_blank\" alt=\"Inside View\" />" + " </map>");
          }
        } else if (styletype.equals("OVERSEAS_PREDESIGNED")) {
          this.out.print("<map name=\"mapdata\"><area shape=\"rect\" coords=\"520,270,625,290\" href=\"../ottoflow/images/isample/predesigned/" + therow.get("stylenumber") + "-F.jpg\" target=\"_blank\" alt=\"Front View\" />" + "<area shape=\"rect\" coords=\"980,270,1075,290\" href=\"../ottoflow/images/isample/predesigned/" + therow.get("stylenumber") + "-LS.jpg\" target=\"_blank\" alt=\"Left View\" />" + "<area shape=\"rect\" coords=\"520,565,620,580\" href=\"../ottoflow/images/isample/predesigned/" + therow.get("stylenumber") + "-B.jpg\" target=\"_blank\" alt=\"Back View\" />" + "<area shape=\"rect\" coords=\"790,565,890,580\" href=\"../ottoflow/images/isample/predesigned/" + therow.get("stylenumber") + "-RS.jpg\" target=\"_blank\" alt=\"Right View\" />" + "<area shape=\"rect\" coords=\"125,565,235,580\" href=\"../ottoflow/images/isample/predesigned/" + therow.get("stylenumber") + "-IS.jpg\" target=\"_blank\" alt=\"Inside View\" />" + " </map>");
        } else if (styletype.equals("OVERSEAS_CUSTOMSTYLE")) {
          this.out.print("<map name=\"mapdata\"><area shape=\"rect\" coords=\"520,270,625,290\" href=\"../productpreview/?" + echokeys(therow) + "category=" + category + "&ordertype=" + this.ordertype + "&showlayout=1&styletype=" + styletype + "&sideview=FRONT\" target=\"_blank\" alt=\"Front View\" />" + "<area shape=\"rect\" coords=\"980,270,1075,290\" href=\"../productpreview/?" + echokeys(therow) + "category=" + category + "&ordertype=" + this.ordertype + "&showlayout=1&styletype=" + styletype + "&sideview=LEFT\" target=\"_blank\" alt=\"Left View\" />" + "<area shape=\"rect\" coords=\"520,565,620,580\" href=\"../productpreview/?" + echokeys(therow) + "category=" + category + "&ordertype=" + this.ordertype + "&showlayout=1&styletype=" + styletype + "&sideview=BACK\" target=\"_blank\" alt=\"Back View\" />" + "<area shape=\"rect\" coords=\"790,565,890,580\" href=\"../productpreview/?" + echokeys(therow) + "category=" + category + "&ordertype=" + this.ordertype + "&showlayout=1&styletype=" + 
            styletype + "&sideview=RIGHT\" target=\"_blank\" alt=\"Right View\" />" + "<area shape=\"rect\" coords=\"125,565,235,580\" href=\"../productpreview/?" + echokeys(therow) + "category=" + category + "&ordertype=" + this.ordertype + "&showlayout=1&styletype=" + styletype + "&sideview=INSIDE\" target=\"_blank\" alt=\"Inside View\" />" + " </map>");
        }
        this.out.print("<img border=\"0\" usemap=\"#mapdata\" src=\"../productpreview/?" + echokeys(therow) + "category=" + category + "&ordertype=" + this.ordertype + "&showlayout=1&styletype=" + styletype + "\"></img></a>");
      } else {
        if (styletype.equals("DOMESTIC")) {
          this.out.print("<map name=\"mapdata\"><area shape=\"rect\" coords=\"160,300,265,320\" href=\"../ottoflow/images/isample/" + therow.get("stylenumber") + "/" + therow.get("stylenumber") + "_" + therow.get("colorcode") + "-F.jpg\" target=\"_blank\" alt=\"Front View\" />" + "<area shape=\"rect\" coords=\"660,300,755,320\" href=\"../ottoflow/images/isample/" + therow.get("stylenumber") + "/" + therow.get("stylenumber") + "_" + therow.get("colorcode") + "-LS.jpg\" target=\"_blank\" alt=\"Left View\" />" + "<area shape=\"rect\" coords=\"160,620,265,635\" href=\"../ottoflow/images/isample/" + therow.get("stylenumber") + "/" + therow.get("stylenumber") + "_" + therow.get("colorcode") + "-B.jpg\" target=\"_blank\" alt=\"Back View\" />" + "<area shape=\"rect\" coords=\"460,620,565,635\" href=\"../ottoflow/images/isample/" + therow.get("stylenumber") + "/" + therow.get("stylenumber") + "_" + therow.get("colorcode") + "-RS.jpg\" target=\"_blank\" alt=\"Right View\" />" + 
            " </map>");
        } else if (styletype.equals("DOMESTIC_SPECIAL")) {
          String thestyle = ((String)therow.get("stylenumber")).substring(0, request.getParameter("oldstyle").length() - 1);
          this.out.print("<map name=\"mapdata\"><area shape=\"rect\" coords=\"160,300,265,320\" href=\"../ottoflow/images/isample/" + thestyle + "/" + thestyle + "_" + therow.get("colorcode") + "-F.jpg\" target=\"_blank\" alt=\"Front View\" />" + "<area shape=\"rect\" coords=\"660,300,755,320\" href=\"../ottoflow/images/isample/" + thestyle + "/" + thestyle + "_" + therow.get("colorcode") + "-LS.jpg\" target=\"_blank\" alt=\"Left View\" />" + "<area shape=\"rect\" coords=\"160,620,265,635\" href=\"../ottoflow/images/isample/" + thestyle + "/" + thestyle + "_" + therow.get("colorcode") + "-B.jpg\" target=\"_blank\" alt=\"Back View\" />" + "<area shape=\"rect\" coords=\"460,620,565,635\" href=\"../ottoflow/images/isample/" + thestyle + "/" + thestyle + "_" + therow.get("colorcode") + "-RS.jpg\" target=\"_blank\" alt=\"Right View\" />" + " </map>");
        } else if ((styletype.equals("DOMESTIC_SHIRTS")) || (styletype.equals("DOMESTIC_SWEATSHIRTS"))) {
          this.out.print("<map name=\"mapdata\"><area shape=\"rect\" coords=\"160,300,265,320\" href=\"../productpreview/?" + echokeys(therow) + "category=" + category + "&ordertype=" + this.ordertype + "&showlayout=1&styletype=" + styletype + "&sideview=FRONT\" target=\"_blank\" alt=\"Front View\" />" + "<area shape=\"rect\" coords=\"660,300,755,320\" href=\"../productpreview/?" + echokeys(therow) + "category=" + category + "&ordertype=" + this.ordertype + "&showlayout=1&styletype=" + styletype + "&sideview=LEFT\" target=\"_blank\" alt=\"Left View\" />" + "<area shape=\"rect\" coords=\"160,620,265,635\" href=\"../productpreview/?" + echokeys(therow) + "category=" + category + "&ordertype=" + this.ordertype + "&showlayout=1&styletype=" + styletype + "&sideview=BACK\" target=\"_blank\" alt=\"Back View\" />" + "<area shape=\"rect\" coords=\"460,620,565,635\" href=\"../productpreview/?" + echokeys(therow) + "category=" + category + "&ordertype=" + this.ordertype + "&showlayout=1&styletype=" + 
            styletype + "&sideview=RIGHT\" target=\"_blank\" alt=\"Right View\" />" + " </map>");
        } else if (styletype.equals("DOMESTIC_TOTEBAGS")) {
          this.out.print("<map name=\"mapdata\"><area shape=\"rect\" coords=\"160,615,270,635\" href=\"../ottoflow/images/isample/" + therow.get("stylenumber") + "/" + therow.get("stylenumber") + "_" + therow.get("colorcode") + "-F.jpg\" target=\"_blank\" alt=\"Front View\" />" + "<area shape=\"rect\" coords=\"575,615,680,635\" href=\"../ottoflow/images/isample/" + therow.get("stylenumber") + "/" + therow.get("stylenumber") + "_" + therow.get("colorcode") + "-F.jpg\" target=\"_blank\" alt=\"Back View\" />" + " </map>");
        } else if (styletype.equals("DOMESTIC_APRONS")) {
          this.out.print("<map name=\"mapdata\"><area shape=\"rect\" coords=\"160,615,270,635\" href=\"../ottoflow/images/isample/" + therow.get("stylenumber") + "/" + therow.get("stylenumber") + "_" + therow.get("colorcode") + "-F.jpg\" target=\"_blank\" alt=\"Front View\" />" + "<area shape=\"rect\" coords=\"575,615,680,635\" href=\"../ottoflow/images/isample/" + therow.get("stylenumber") + "/" + therow.get("stylenumber") + "_" + therow.get("colorcode") + "-F.jpg\" target=\"_blank\" alt=\"Back View\" />" + " </map>");
        }
        this.out.print("<BR><img border=\"0\" usemap=\"#mapdata\" src=\"../productpreview/?" + echokeys(therow) + "category=" + category + "&ordertype=" + this.ordertype + "&showlayout=1&styletype=" + styletype + "\"></img></a>");
      }
      
      this.out.print("</td></tr>");
    }
    
    this.out.print("</table>");
  }
  
  private String echokeysold(HttpServletRequest request) throws Exception
  {
    String myecho = "stylenumber=" + getUrlSafe(request.getParameter("oldstyle")) + "&";
    myecho = myecho + "colorcode=" + getUrlSafe(request.getParameter("oldcolor")) + "&";
    if (this.ordertype.equals("Overseas")) {
      myecho = myecho + "frontpanelcolor=" + getUrlSafe(request.getParameter("oldfrontpanelcolor")) + "&";
      myecho = myecho + "sidebackpanelcolor=" + getUrlSafe(request.getParameter("oldsidebackpanelcolor")) + "&";
      myecho = myecho + "backpanelcolor=" + getUrlSafe(request.getParameter("oldbackpanelcolor")) + "&";
      myecho = myecho + "sidepanelcolor=" + getUrlSafe(request.getParameter("oldsidepanelcolor")) + "&";
      myecho = myecho + "visorstylenumber=" + getUrlSafe(request.getParameter("oldvisorstylenumber")) + "&";
      myecho = myecho + "primaryvisorcolor=" + getUrlSafe(request.getParameter("oldprimaryvisorcolor")) + "&";
      myecho = myecho + "visortrimcolor=" + getUrlSafe(request.getParameter("oldvisortrimcolor")) + "&";
      myecho = myecho + "visorsandwichcolor=" + getUrlSafe(request.getParameter("oldvisorsandwichcolor")) + "&";
      myecho = myecho + "undervisorcolor=" + getUrlSafe(request.getParameter("oldundervisorcolor")) + "&";
      myecho = myecho + "distressedvisorinsidecolor=" + getUrlSafe(request.getParameter("olddistressedvisorinsidecolor")) + "&";
      myecho = myecho + "closurestylenumber=" + getUrlSafe(request.getParameter("oldclosurestylenumber")) + "&";
      myecho = myecho + "closurestrapcolor=" + getUrlSafe(request.getParameter("oldclosurestrapcolor")) + "&";
      myecho = myecho + "eyeletstylenumber=" + getUrlSafe(request.getParameter("oldeyeletstylenumber")) + "&";
      myecho = myecho + "fronteyeletcolor=" + getUrlSafe(request.getParameter("oldfronteyeletcolor")) + "&";
      myecho = myecho + "sidebackeyeletcolor=" + getUrlSafe(request.getParameter("oldsidebackeyeletcolor")) + "&";
      myecho = myecho + "backeyeletcolor=" + getUrlSafe(request.getParameter("oldbackeyeletcolor")) + "&";
      myecho = myecho + "sideeyeletcolor=" + getUrlSafe(request.getParameter("oldsideeyeletcolor")) + "&";
      myecho = myecho + "buttoncolor=" + getUrlSafe(request.getParameter("oldbuttoncolor")) + "&";
      myecho = myecho + "innertapingcolor=" + getUrlSafe(request.getParameter("oldinnertapingcolor")) + "&";
      myecho = myecho + "sweatbandstylenumber=" + getUrlSafe(request.getParameter("oldsweatbandstylenumber")) + "&";
      myecho = myecho + "sweatbandcolor=" + getUrlSafe(request.getParameter("oldsweatbandcolor")) + "&";
      myecho = myecho + "sweatbandstripecolor=" + getUrlSafe(request.getParameter("oldsweatbandstripecolor")) + "&";
      
      myecho = myecho + "panelstitchcolor=" + getUrlSafe(request.getParameter("oldpanelstitchcolor")) + "&";
      myecho = myecho + "visorrowstitching=" + getUrlSafe(request.getParameter("oldvisorrowstitching")) + "&";
      myecho = myecho + "visorstitchcolor=" + getUrlSafe(request.getParameter("oldvisorstitchcolor")) + "&";
      myecho = myecho + "profile=" + getUrlSafe(request.getParameter("oldprofile")) + "&";
      myecho = myecho + "frontpanelfabric=" + getUrlSafe(request.getParameter("oldfrontpanelfabric")) + "&";
      myecho = myecho + "sidebackpanelfabric=" + getUrlSafe(request.getParameter("oldsidebackpanelfabric")) + "&";
      myecho = myecho + "backpanelfabric=" + getUrlSafe(request.getParameter("oldbackpanelfabric")) + "&";
      myecho = myecho + "sidepanelfabric=" + getUrlSafe(request.getParameter("oldsidepanelfabric")) + "&";
      myecho = myecho + "crownconstruction=" + getUrlSafe(request.getParameter("oldcrownconstruction")) + "&";
      myecho = myecho + "numberofpanels=" + getUrlSafe(request.getParameter("oldnumberofpanels")) + "&";
    }
    
    return myecho;
  }
  
  private String echokeys(FastMap<Object> therows) {
    String myecho = "stylenumber=" + getUrlSafe(therows.get("stylenumber")) + "&";
    myecho = myecho + "colorcode=" + getUrlSafe(therows.get("colorcode")) + "&";
    if (this.ordertype.equals("Overseas")) {
      myecho = myecho + "frontpanelcolor=" + getUrlSafe(therows.get("frontpanelcolor")) + "&";
      myecho = myecho + "sidebackpanelcolor=" + getUrlSafe(therows.get("sidebackpanelcolor")) + "&";
      myecho = myecho + "backpanelcolor=" + getUrlSafe(therows.get("backpanelcolor")) + "&";
      myecho = myecho + "sidepanelcolor=" + getUrlSafe(therows.get("sidepanelcolor")) + "&";
      myecho = myecho + "visorstylenumber=" + getUrlSafe(therows.get("visorstylenumber")) + "&";
      myecho = myecho + "primaryvisorcolor=" + getUrlSafe(therows.get("primaryvisorcolor")) + "&";
      myecho = myecho + "visortrimcolor=" + getUrlSafe(therows.get("visortrimcolor")) + "&";
      myecho = myecho + "visorsandwichcolor=" + getUrlSafe(therows.get("visorsandwichcolor")) + "&";
      myecho = myecho + "undervisorcolor=" + getUrlSafe(therows.get("undervisorcolor")) + "&";
      myecho = myecho + "distressedvisorinsidecolor=" + getUrlSafe(therows.get("distressedvisorinsidecolor")) + "&";
      myecho = myecho + "closurestylenumber=" + getUrlSafe(therows.get("closurestylenumber")) + "&";
      myecho = myecho + "closurestrapcolor=" + getUrlSafe(therows.get("closurestrapcolor")) + "&";
      myecho = myecho + "eyeletstylenumber=" + getUrlSafe(therows.get("eyeletstylenumber")) + "&";
      myecho = myecho + "fronteyeletcolor=" + getUrlSafe(therows.get("eyeletcolor")) + "&";
      myecho = myecho + "sidebackeyeletcolor=" + getUrlSafe(therows.get("sidebackeyeletcolor")) + "&";
      myecho = myecho + "backeyeletcolor=" + getUrlSafe(therows.get("backeyeletcolor")) + "&";
      myecho = myecho + "sideeyeletcolor=" + getUrlSafe(therows.get("sideeyeletcolor")) + "&";
      myecho = myecho + "buttoncolor=" + getUrlSafe(therows.get("buttoncolor")) + "&";
      myecho = myecho + "innertapingcolor=" + getUrlSafe(therows.get("innertapingcolor")) + "&";
      myecho = myecho + "sweatbandstylenumber=" + getUrlSafe(therows.get("sweatbandstylenumber")) + "&";
      myecho = myecho + "sweatbandcolor=" + getUrlSafe(therows.get("sweatbandcolor")) + "&";
      myecho = myecho + "sweatbandstripecolor=" + getUrlSafe(therows.get("sweatbandstripecolor")) + "&";
      
      myecho = myecho + "panelstitchcolor=" + getUrlSafe(therows.get("panelstitchcolor")) + "&";
      myecho = myecho + "visorrowstitching=" + getUrlSafe(therows.get("visorrowstitching")) + "&";
      myecho = myecho + "visorstitchcolor=" + getUrlSafe(therows.get("visorstitchcolor")) + "&";
      myecho = myecho + "profile=" + getUrlSafe(therows.get("profile")) + "&";
      myecho = myecho + "frontpanelfabric=" + getUrlSafe(therows.get("frontpanelfabric")) + "&";
      myecho = myecho + "sidebackpanelfabric=" + getUrlSafe(therows.get("sidebackpanelfabric")) + "&";
      myecho = myecho + "backpanelfabric=" + getUrlSafe(therows.get("backpanelfabric")) + "&";
      myecho = myecho + "sidepanelfabric=" + getUrlSafe(therows.get("sidepanelfabric")) + "&";
      myecho = myecho + "crownconstruction=" + getUrlSafe(therows.get("crownconstruction")) + "&";
      myecho = myecho + "numberofpanels=" + getUrlSafe(therows.get("numberofpanels")) + "&";
    }
    
    return myecho;
  }
  
  private String getUrlSafe(Object toencode) {
    try {
      return URLEncoder.encode(String.valueOf(toencode), "UTF-8");
    } catch (UnsupportedEncodingException e) {}
    return "";
  }
}
