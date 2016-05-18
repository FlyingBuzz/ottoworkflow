package com.ottocap.NewWorkFlow.server;

import java.util.TreeMap;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.jooq.SelectJoinStep;
import org.jooq.SelectOffsetStep;
import org.jooq.SelectSelectStep;
import org.jooq.SelectWhereStep;
import org.jooq.TableLike;
import org.jooq.impl.DSL;

public class JOOQ_ArtworkProductPreview extends javax.servlet.http.HttpServlet
{
  private static final long serialVersionUID = 1L;
  ServletOutputStream out;
  String ordertype = "Domestic";
  
  public void doGet(HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws java.io.IOException {
    this.out = response.getOutputStream();
    try {
      mainPreview(request);
    } catch (Exception e) {
      e.printStackTrace();
      this.out.print(e.getLocalizedMessage());
    }
  }
  


  public void mainPreview(HttpServletRequest request)
    throws java.io.IOException
  {
    Record sqldata = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "ordertype" })).from(new TableLike[] { DSL.tableByName(new String[] { "ordermain" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "hiddenkey" }).equal(request.getParameter("hiddenkey")) }).limit(1).fetchOne();
    this.ordertype = ((String)sqldata.getValue(DSL.fieldByName(new String[] { "ordertype" })));
    

    sqldata = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "ordermain_sets_items" })).where(new Condition[] { DSL.fieldByName(new String[] { "hiddenkey" }).equal(request.getParameter("hiddenkey")) }).and(DSL.fieldByName(new String[] { "set" }).equal(request.getParameter("set"))).and(DSL.fieldByName(new String[] { "item" }).equal(request.getParameter("item"))).limit(1).fetchOne();
    

    boolean differentcolors = false;
    if (request.getParameter("oldstyle") != null) {
      if (this.ordertype.equals("Domestic")) {
        if (sqldata.getValue(DSL.fieldByName(new String[] { "stylenumber" })).equals(request.getParameter("oldstyle"))) { if (sqldata.getValue(DSL.fieldByName(new String[] { "colorcode" })).equals(request.getParameter("oldcolor"))) {}
        } else differentcolors = true;
      }
      else {
        boolean isoverseascustomstyle = false;
        if (sqldata.getValue(DSL.fieldByName(new String[] { "stylenumber" })).equals(request.getParameter("oldstyle"))) {
          Record catdata;
          if (((catdata = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_overseas_customstyle" })).where(new Condition[] { DSL.fieldByName(new String[] { "Style" }).equal(request.getParameter("oldstyle")) }).limit(1).fetchOne()) != null) && (catdata.size() != 0)) {
            isoverseascustomstyle = true;
          }
        }
        
        if (sqldata.getValue(DSL.fieldByName(new String[] { "stylenumber" })).equals(request.getParameter("oldstyle"))) if (sqldata.getValue(DSL.fieldByName(new String[] { "colorcode" })).equals(request.getParameter("oldcolor"))) if (sqldata.getValue(DSL.fieldByName(new String[] { "frontpanelcolor" })).equals(request.getParameter("oldfrontpanelcolor"))) if (sqldata.getValue(DSL.fieldByName(new String[] { "sidebackpanelcolor" })).equals(request.getParameter("oldsidebackpanelcolor"))) if (sqldata.getValue(DSL.fieldByName(new String[] { "backpanelcolor" })).equals(request.getParameter("oldbackpanelcolor"))) if (sqldata.getValue(DSL.fieldByName(new String[] { "sidepanelcolor" })).equals(request.getParameter("oldsidepanelcolor"))) if (sqldata.getValue(DSL.fieldByName(new String[] { "visorstylenumber" })).equals(request.getParameter("oldvisorstylenumber"))) if (sqldata.getValue(DSL.fieldByName(new String[] { "primaryvisorcolor" })).equals(request.getParameter("oldprimaryvisorcolor"))) if (sqldata.getValue(DSL.fieldByName(new String[] { "visortrimcolor" })).equals(request.getParameter("oldvisortrimcolor")))
                          if (sqldata.getValue(DSL.fieldByName(new String[] { "visorsandwichcolor" })).equals(request.getParameter("oldvisorsandwichcolor"))) if (sqldata.getValue(DSL.fieldByName(new String[] { "undervisorcolor" })).equals(request.getParameter("oldundervisorcolor"))) if (sqldata.getValue(DSL.fieldByName(new String[] { "distressedvisorinsidecolor" })).equals(request.getParameter("olddistressedvisorinsidecolor"))) if (sqldata.getValue(DSL.fieldByName(new String[] { "closurestylenumber" })).equals(request.getParameter("oldclosurestylenumber"))) if (sqldata.getValue(DSL.fieldByName(new String[] { "closurestrapcolor" })).equals(request.getParameter("oldclosurestrapcolor"))) if (sqldata.getValue(DSL.fieldByName(new String[] { "eyeletstylenumber" })).equals(request.getParameter("oldeyeletstylenumber"))) if (sqldata.getValue(DSL.fieldByName(new String[] { "eyeletcolor" })).equals(request.getParameter("oldfronteyeletcolor"))) if (sqldata.getValue(DSL.fieldByName(new String[] { "sidebackeyeletcolor" })).equals(request.getParameter("oldsidebackeyeletcolor")))
                                          if (sqldata.getValue(DSL.fieldByName(new String[] { "backeyeletcolor" })).equals(request.getParameter("oldbackeyeletcolor"))) if (sqldata.getValue(DSL.fieldByName(new String[] { "sideeyeletcolor" })).equals(request.getParameter("oldsideeyeletcolor"))) if (sqldata.getValue(DSL.fieldByName(new String[] { "buttoncolor" })).equals(request.getParameter("oldbuttoncolor"))) if (sqldata.getValue(DSL.fieldByName(new String[] { "innertapingcolor" })).equals(request.getParameter("oldinnertapingcolor"))) if (sqldata.getValue(DSL.fieldByName(new String[] { "sweatbandstylenumber" })).equals(request.getParameter("oldsweatbandstylenumber"))) if (sqldata.getValue(DSL.fieldByName(new String[] { "sweatbandcolor" })).equals(request.getParameter("oldsweatbandcolor"))) if (sqldata.getValue(DSL.fieldByName(new String[] { "sweatbandstripecolor" })).equals(request.getParameter("oldsweatbandstripecolor"))) if (sqldata.getValue(DSL.fieldByName(new String[] { "panelstitchcolor" })).equals(request.getParameter("oldpanelstitchcolor")))
                                                          if ((sqldata.getValue(DSL.fieldByName(new String[] { "visorrowstitching" })).equals(request.getParameter("oldvisorrowstitching"))) || (!isoverseascustomstyle)) if ((sqldata.getValue(DSL.fieldByName(new String[] { "visorstitchcolor" })).equals(request.getParameter("oldvisorstitchcolor"))) || (!isoverseascustomstyle)) if ((sqldata.getValue(DSL.fieldByName(new String[] { "profile" })).equals(request.getParameter("oldprofile"))) || (!isoverseascustomstyle)) if ((sqldata.getValue(DSL.fieldByName(new String[] { "frontpanelfabric" })).equals(request.getParameter("oldfrontpanelfabric"))) || (!isoverseascustomstyle)) if ((sqldata.getValue(DSL.fieldByName(new String[] { "sidebackpanelfabric" })).equals(request.getParameter("oldsidebackpanelfabric"))) || (!isoverseascustomstyle)) if ((sqldata.getValue(DSL.fieldByName(new String[] { "backpanelfabric" })).equals(request.getParameter("oldbackpanelfabric"))) || (!isoverseascustomstyle)) if ((sqldata.getValue(DSL.fieldByName(new String[] { "sidepanelfabric" })).equals(request.getParameter("oldsidepanelfabric"))) || (!isoverseascustomstyle))
                                                                        if ((sqldata.getValue(DSL.fieldByName(new String[] { "crownconstruction" })).equals(request.getParameter("oldcrownconstruction"))) || (!isoverseascustomstyle)) if ((sqldata.getValue(DSL.fieldByName(new String[] { "numberofpanels" })).equals(request.getParameter("oldnumberofpanels"))) || (!isoverseascustomstyle)) break label1635;
        differentcolors = true;
      }
    }
    
    label1635:
    Record therow = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "ordermain_sets_items" })).where(new Condition[] { DSL.fieldByName(new String[] { "hiddenkey" }).equal(request.getParameter("hiddenkey")) }).and(DSL.fieldByName(new String[] { "set" }).equal(request.getParameter("set"))).and(DSL.fieldByName(new String[] { "item" }).equal("item")).limit(1).fetchOne();
    
    this.out.print("<table>");
    
    if (differentcolors)
    {
      this.out.print("<tr><td>PDF Style: " + request.getParameter("oldstyle"));
      if (!request.getParameter("oldcolor").equals("")) {
        this.out.print("_" + request.getParameter("oldcolor"));
      }
      this.out.print("</td></tr>");
      
      TreeMap<String, String> thestyleinfo = getStyleTypeCategory(request.getParameter("oldstyle"));
      stringMapData("mapdata2", (String)thestyleinfo.get("styletype"), (String)thestyleinfo.get("category"), request.getParameter("oldstyle"), request.getParameter("oldcolor"), echokeysold(request));
    }
    

    this.out.print("<tr><td>Current Style: " + therow.getValue(DSL.fieldByName(new String[] { "stylenumber" })));
    if (!therow.getValue(DSL.fieldByName(new String[] { "colorcode" })).equals("")) {
      this.out.print("_" + therow.getValue(DSL.fieldByName(new String[] { "colorcode" })));
    }
    this.out.print("</td></tr>");
    
    TreeMap<String, String> thestyleinfo2 = getStyleTypeCategory((String)therow.getValue(DSL.fieldByName(new String[] { "stylenumber" })));
    stringMapData("mapdata", (String)thestyleinfo2.get("styletype"), (String)thestyleinfo2.get("category"), (String)therow.getValue(DSL.fieldByName(new String[] { "stylenumber" })), (String)therow.getValue(DSL.fieldByName(new String[] { "colorcode" })), echokeys(therow));
    
    this.out.println("</table>");
  }
  
  private void stringMapData(String mapname, String styletype, String category, String stylenumber, String colorcode, String rowdata) throws java.io.IOException
  {
    this.out.print("<tr><td>");
    
    if (this.ordertype.equals("Overseas")) {
      if (styletype.equals("OVERSEAS")) {
        this.out.print("<map name=\"" + mapname + "\">" + "<area shape=\"rect\" coords=\"520,270,625,290\" href=\"../productpreview/?" + rowdata + "category=" + getUrlSafe(category) + "&ordertype=" + getUrlSafe(this.ordertype) + "&showlayout=1&styletype=" + getUrlSafe(styletype) + "&sideview=FRONT\" target=\"_blank\" alt=\"Front View\" />" + "<area shape=\"rect\" coords=\"980,270,1075,290\" href=\"../productpreview/?" + rowdata + "category=" + getUrlSafe(category) + "&ordertype=" + getUrlSafe(this.ordertype) + "&showlayout=1&styletype=" + getUrlSafe(styletype) + "&sideview=LEFT\" target=\"_blank\" alt=\"Left View\" />" + "<area shape=\"rect\" coords=\"520,565,620,580\" href=\"../productpreview/?" + rowdata + "category=" + getUrlSafe(category) + "&ordertype=" + getUrlSafe(this.ordertype) + "&showlayout=1&styletype=" + getUrlSafe(styletype) + "&sideview=BACK\" target=\"_blank\" alt=\"Back View\" />" + "<area shape=\"rect\" coords=\"790,565,890,580\" href=\"../productpreview/?" + rowdata + 
          "category=" + getUrlSafe(category) + "&ordertype=" + getUrlSafe(this.ordertype) + "&showlayout=1&styletype=" + styletype + "&sideview=RIGHT\" target=\"_blank\" alt=\"Right View\" />" + "<area shape=\"rect\" coords=\"125,565,235,580\" href=\"../productpreview/?" + rowdata + "category=" + getUrlSafe(category) + "&ordertype=" + getUrlSafe(this.ordertype) + "&showlayout=1&styletype=" + getUrlSafe(styletype) + "&sideview=INSIDE\" target=\"_blank\" alt=\"Inside View\" />" + " </map>");
      } else if ((styletype.equals("OVERSEAS_INSTOCK_SHIRTS")) || (styletype.equals("OVERSEAS_INSTOCK_FLATS"))) {
        this.out.print("<map name=\"" + mapname + "\">" + "<area shape=\"rect\" coords=\"520,270,625,290\" href=\"../productpreview/?" + rowdata + "category=" + getUrlSafe(category) + "&ordertype=" + getUrlSafe(this.ordertype) + "&showlayout=1&styletype=" + getUrlSafe(styletype) + "&sideview=FRONT\" target=\"_blank\" alt=\"Front View\" />" + "<area shape=\"rect\" coords=\"980,270,1075,290\" href=\"../productpreview/?" + rowdata + "category=" + getUrlSafe(category) + "&ordertype=" + getUrlSafe(this.ordertype) + "&showlayout=1&styletype=" + getUrlSafe(styletype) + "&sideview=LEFT\" target=\"_blank\" alt=\"Left View\" />" + "<area shape=\"rect\" coords=\"520,565,620,580\" href=\"../productpreview/?" + rowdata + "category=" + getUrlSafe(category) + "&ordertype=" + getUrlSafe(this.ordertype) + "&showlayout=1&styletype=" + getUrlSafe(styletype) + "&sideview=BACK\" target=\"_blank\" alt=\"Back View\" />" + "<area shape=\"rect\" coords=\"790,565,890,580\" href=\"../productpreview/?" + rowdata + 
          "category=" + getUrlSafe(category) + "&ordertype=" + getUrlSafe(this.ordertype) + "&showlayout=1&styletype=" + styletype + "&sideview=RIGHT\" target=\"_blank\" alt=\"Right View\" /></map>");
      } else if (styletype.equals("OVERSEAS_INSTOCK")) {
        if ((colorcode != null) && (!colorcode.equals(""))) {
          this.out.print("<map name=\"" + mapname + "\">" + "<area shape=\"rect\" coords=\"520,270,625,290\" href=\"../ottoflow/images/isample/" + stylenumber + "/" + stylenumber + "_" + colorcode + "-F.jpg\" target=\"_blank\" alt=\"Front View\" />" + "<area shape=\"rect\" coords=\"980,270,1075,290\" href=\"../ottoflow/images/isample/" + stylenumber + "/" + stylenumber + "_" + colorcode + "-LS.jpg\" target=\"_blank\" alt=\"Left View\" />" + "<area shape=\"rect\" coords=\"520,565,620,580\" href=\"../ottoflow/images/isample/" + stylenumber + "/" + stylenumber + "_" + colorcode + "-B.jpg\" target=\"_blank\" alt=\"Back View\" />" + "<area shape=\"rect\" coords=\"790,565,890,580\" href=\"../ottoflow/images/isample/" + stylenumber + "/" + stylenumber + "_" + colorcode + "-RS.jpg\" target=\"_blank\" alt=\"Right View\" />" + "<area shape=\"rect\" coords=\"125,565,235,580\" href=\"../ottoflow/images/isample/" + stylenumber + "/" + stylenumber + "_" + colorcode + 
            "-IS.jpg\" target=\"_blank\" alt=\"Inside View\" />" + " </map>");
        } else {
          this.out.print("<map name=\"" + mapname + "\">" + "<area shape=\"rect\" coords=\"520,270,625,290\" href=\"../productpreview/?" + rowdata + "category=" + getUrlSafe(category) + "&ordertype=" + getUrlSafe(this.ordertype) + "&showlayout=1&styletype=" + getUrlSafe(styletype) + "&sideview=FRONT\" target=\"_blank\" alt=\"Front View\" />" + "<area shape=\"rect\" coords=\"980,270,1075,290\" href=\"../productpreview/?" + rowdata + "category=" + getUrlSafe(category) + "&ordertype=" + getUrlSafe(this.ordertype) + "&showlayout=1&styletype=" + getUrlSafe(styletype) + "&sideview=LEFT\" target=\"_blank\" alt=\"Left View\" />" + "<area shape=\"rect\" coords=\"520,565,620,580\" href=\"../productpreview/?" + rowdata + "category=" + getUrlSafe(category) + "&ordertype=" + getUrlSafe(this.ordertype) + "&showlayout=1&styletype=" + getUrlSafe(styletype) + "&sideview=BACK\" target=\"_blank\" alt=\"Back View\" />" + "<area shape=\"rect\" coords=\"790,565,890,580\" href=\"../productpreview/?" + rowdata + 
            "category=" + getUrlSafe(category) + "&ordertype=" + getUrlSafe(this.ordertype) + "&showlayout=1&styletype=" + styletype + "&sideview=RIGHT\" target=\"_blank\" alt=\"Right View\" />" + "<area shape=\"rect\" coords=\"125,565,235,580\" href=\"../productpreview/?" + rowdata + "category=" + getUrlSafe(category) + "&ordertype=" + getUrlSafe(this.ordertype) + "&showlayout=1&styletype=" + getUrlSafe(styletype) + "&sideview=INSIDE\" target=\"_blank\" alt=\"Inside View\" />" + " </map>");
        }
      } else if (styletype.equals("OVERSEAS_PREDESIGNED")) {
        this.out.print("<map name=\"" + mapname + "\">" + "<area shape=\"rect\" coords=\"520,270,625,290\" href=\"../ottoflow/images/isample/predesigned/" + stylenumber + "-F.jpg\" target=\"_blank\" alt=\"Front View\" />" + "<area shape=\"rect\" coords=\"980,270,1075,290\" href=\"../ottoflow/images/isample/predesigned/" + stylenumber + "-LS.jpg\" target=\"_blank\" alt=\"Left View\" />" + "<area shape=\"rect\" coords=\"520,565,620,580\" href=\"../ottoflow/images/isample/predesigned/" + stylenumber + "-B.jpg\" target=\"_blank\" alt=\"Back View\" />" + "<area shape=\"rect\" coords=\"790,565,890,580\" href=\"../ottoflow/images/isample/predesigned/" + stylenumber + "-RS.jpg\" target=\"_blank\" alt=\"Right View\" />" + "<area shape=\"rect\" coords=\"125,565,235,580\" href=\"../ottoflow/images/isample/predesigned/" + stylenumber + "-IS.jpg\" target=\"_blank\" alt=\"Inside View\" />" + " </map>");
      } else if (styletype.equals("OVERSEAS_CUSTOMSTYLE")) {
        this.out.print("<map name=\"" + mapname + "\">" + "<area shape=\"rect\" coords=\"520,270,625,290\" href=\"../productpreview/?" + rowdata + "category=" + getUrlSafe(category) + "&ordertype=" + getUrlSafe(this.ordertype) + "&showlayout=1&styletype=" + getUrlSafe(styletype) + "&sideview=FRONT\" target=\"_blank\" alt=\"Front View\" />" + "<area shape=\"rect\" coords=\"980,270,1075,290\" href=\"../productpreview/?" + rowdata + "category=" + getUrlSafe(category) + "&ordertype=" + getUrlSafe(this.ordertype) + "&showlayout=1&styletype=" + getUrlSafe(styletype) + "&sideview=LEFT\" target=\"_blank\" alt=\"Left View\" />" + "<area shape=\"rect\" coords=\"520,565,620,580\" href=\"../productpreview/?" + rowdata + "category=" + getUrlSafe(category) + "&ordertype=" + getUrlSafe(this.ordertype) + "&showlayout=1&styletype=" + getUrlSafe(styletype) + "&sideview=BACK\" target=\"_blank\" alt=\"Back View\" />" + "<area shape=\"rect\" coords=\"790,565,890,580\" href=\"../productpreview/?" + rowdata + 
          "category=" + getUrlSafe(category) + "&ordertype=" + getUrlSafe(this.ordertype) + "&showlayout=1&styletype=" + styletype + "&sideview=RIGHT\" target=\"_blank\" alt=\"Right View\" />" + "<area shape=\"rect\" coords=\"125,565,235,580\" href=\"../productpreview/?" + rowdata + "category=" + getUrlSafe(category) + "&ordertype=" + getUrlSafe(this.ordertype) + "&showlayout=1&styletype=" + getUrlSafe(styletype) + "&sideview=INSIDE\" target=\"_blank\" alt=\"Inside View\" />" + " </map>");
      }
      this.out.print("<img border=\"0\" usemap=\"#mapdata\" src=\"../productpreview/?" + rowdata + "category=" + getUrlSafe(category) + "&ordertype=" + getUrlSafe(this.ordertype) + "&showlayout=1&styletype=" + getUrlSafe(styletype) + "\"></img></a>");
    } else {
      if (styletype.equals("DOMESTIC")) {
        this.out.print("<map name=\"" + mapname + "\">" + "<area shape=\"rect\" coords=\"160,300,265,320\" href=\"../ottoflow/images/isample/" + stylenumber + "/" + stylenumber + "_" + colorcode + "-F.jpg\" target=\"_blank\" alt=\"Front View\" />" + "<area shape=\"rect\" coords=\"660,300,755,320\" href=\"../ottoflow/images/isample/" + stylenumber + "/" + stylenumber + "_" + colorcode + "-LS.jpg\" target=\"_blank\" alt=\"Left View\" />" + "<area shape=\"rect\" coords=\"160,620,265,635\" href=\"../ottoflow/images/isample/" + stylenumber + "/" + stylenumber + "_" + colorcode + "-B.jpg\" target=\"_blank\" alt=\"Back View\" />" + "<area shape=\"rect\" coords=\"460,620,565,635\" href=\"../ottoflow/images/isample/" + stylenumber + "/" + stylenumber + "_" + colorcode + "-RS.jpg\" target=\"_blank\" alt=\"Right View\" />" + " </map>");
      } else if (styletype.equals("DOMESTIC_SPECIAL")) {
        String thestyle = stylenumber.substring(0, stylenumber.length() - 1);
        this.out.print("<map name=\"" + mapname + "\">" + "<area shape=\"rect\" coords=\"160,300,265,320\" href=\"../ottoflow/images/isample/" + thestyle + "/" + thestyle + "_" + colorcode + "-F.jpg\" target=\"_blank\" alt=\"Front View\" />" + "<area shape=\"rect\" coords=\"660,300,755,320\" href=\"../ottoflow/images/isample/" + thestyle + "/" + thestyle + "_" + colorcode + "-LS.jpg\" target=\"_blank\" alt=\"Left View\" />" + "<area shape=\"rect\" coords=\"160,620,265,635\" href=\"../ottoflow/images/isample/" + thestyle + "/" + thestyle + "_" + colorcode + "-B.jpg\" target=\"_blank\" alt=\"Back View\" />" + "<area shape=\"rect\" coords=\"460,620,565,635\" href=\"../ottoflow/images/isample/" + thestyle + "/" + thestyle + "_" + colorcode + "-RS.jpg\" target=\"_blank\" alt=\"Right View\" />" + " </map>");
      } else if ((styletype.equals("DOMESTIC_SHIRTS")) || (styletype.equals("DOMESTIC_SWEATSHIRTS"))) {
        this.out.print("<map name=\"" + mapname + "\">" + "<area shape=\"rect\" coords=\"160,300,265,320\" href=\"../productpreview/?" + rowdata + "category=" + getUrlSafe(category) + "&ordertype=" + getUrlSafe(this.ordertype) + "&showlayout=1&styletype=" + getUrlSafe(styletype) + "&sideview=FRONT\" target=\"_blank\" alt=\"Front View\" />" + "<area shape=\"rect\" coords=\"660,300,755,320\" href=\"../productpreview/?" + rowdata + "category=" + getUrlSafe(category) + "&ordertype=" + getUrlSafe(this.ordertype) + "&showlayout=1&styletype=" + getUrlSafe(styletype) + "&sideview=LEFT\" target=\"_blank\" alt=\"Left View\" />" + "<area shape=\"rect\" coords=\"160,620,265,635\" href=\"../productpreview/?" + rowdata + "category=" + getUrlSafe(category) + "&ordertype=" + getUrlSafe(this.ordertype) + "&showlayout=1&styletype=" + getUrlSafe(styletype) + "&sideview=BACK\" target=\"_blank\" alt=\"Back View\" />" + "<area shape=\"rect\" coords=\"460,620,565,635\" href=\"../productpreview/?" + rowdata + 
          "category=" + getUrlSafe(category) + "&ordertype=" + getUrlSafe(this.ordertype) + "&showlayout=1&styletype=" + styletype + "&sideview=RIGHT\" target=\"_blank\" alt=\"Right View\" />" + " </map>");
      } else if (styletype.equals("DOMESTIC_TOTEBAGS")) {
        this.out.print("<map name=\"" + mapname + "\">" + "<area shape=\"rect\" coords=\"160,615,270,635\" href=\"../ottoflow/images/isample/" + stylenumber + "/" + stylenumber + "_" + colorcode + "-F.jpg\" target=\"_blank\" alt=\"Front View\" />" + "<area shape=\"rect\" coords=\"575,615,680,635\" href=\"../ottoflow/images/isample/" + stylenumber + "/" + stylenumber + "_" + colorcode + "-F.jpg\" target=\"_blank\" alt=\"Back View\" />" + " </map>");
      } else if (styletype.equals("DOMESTIC_APRONS")) {
        this.out.print("<map name=\"" + mapname + "\">" + "<area shape=\"rect\" coords=\"160,615,270,635\" href=\"../ottoflow/images/isample/" + stylenumber + "/" + stylenumber + "_" + colorcode + "-F.jpg\" target=\"_blank\" alt=\"Front View\" />" + "<area shape=\"rect\" coords=\"575,615,680,635\" href=\"../ottoflow/images/isample/" + stylenumber + "/" + stylenumber + "_" + colorcode + "-F.jpg\" target=\"_blank\" alt=\"Back View\" />" + " </map>");
      }
      this.out.print("<BR><img border=\"0\" usemap=\"#" + mapname + "\" src=\"../productpreview/?" + rowdata + "category=" + getUrlSafe(category) + "&ordertype=" + getUrlSafe(this.ordertype) + "&showlayout=1&styletype=" + getUrlSafe(styletype) + "\"></img></a>");
    }
    
    this.out.print("</td></tr>");
  }
  

  private TreeMap<String, String> getStyleTypeCategory(String thestyle)
  {
    TreeMap<String, String> thestyleinfo = new TreeMap();
    thestyleinfo.put("styletype", "");
    thestyleinfo.put("category", "");
    if (this.ordertype.equals("Overseas")) { Record sqldata;
      if (((sqldata = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "Category" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_overseasinstock" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "STYLE NUMBER" }).equal(thestyle) }).limit(1).fetchOne()) != null) && (sqldata.size() != 0)) {
        thestyleinfo.put("styletype", "OVERSEAS_INSTOCK");
        thestyleinfo.put("category", (String)sqldata.getValue(DSL.fieldByName(new String[] { "Category" })));
      } else if (((sqldata = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_overseas" })).where(new Condition[] { DSL.fieldByName(new String[] { "Style" }).equal(thestyle) }).limit(1).fetchOne()) != null) && (sqldata.size() != 0)) {
        thestyleinfo.put("styletype", "OVERSEAS");
      } else if (((sqldata = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_pre-designed" })).where(new Condition[] { DSL.fieldByName(new String[] { "Style" }).equal(thestyle) }).limit(1).fetchOne()) != null) && (sqldata.size() != 0)) {
        thestyleinfo.put("styletype", "OVERSEAS_PREDESIGNED");
      } else if (((sqldata = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "overseas_price_instockshirts" })).where(new Condition[] { DSL.fieldByName(new String[] { "stylenumber" }).equal(thestyle) }).limit(1).fetchOne()) != null) && (sqldata.size() != 0)) {
        thestyleinfo.put("styletype", "OVERSEAS_INSTOCK_SHIRTS");
      } else if (((sqldata = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "overseas_price_instockflats" })).where(new Condition[] { DSL.fieldByName(new String[] { "stylenumber" }).equal(thestyle) }).limit(1).fetchOne()) != null) && (sqldata.size() != 0)) {
        thestyleinfo.put("styletype", "OVERSEAS_INSTOCK_FLATS");
      } else if (((sqldata = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_overseas_customstyle" })).where(new Condition[] { DSL.fieldByName(new String[] { "Style" }).equal(thestyle) }).limit(1).fetchOne()) != null) && (sqldata.size() != 0)) {
        thestyleinfo.put("styletype", "OVERSEAS_CUSTOMSTYLE");
      }
    } else { Record sqldata;
      if (((sqldata = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "category" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_domestic" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "sku" }).equal(thestyle) }).limit(1).fetchOne()) != null) && (sqldata.size() != 0)) {
        thestyleinfo.put("styletype", "DOMESTIC");
        thestyleinfo.put("category", (String)sqldata.getValue(DSL.fieldByName(new String[] { "category" })));
      } else if (((sqldata = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "basestyle" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_domestic_specials" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "style" }).equal(thestyle) }).limit(1).fetchOne()) != null) && (sqldata.size() != 0)) {
        thestyleinfo.put("styletype", "DOMESTIC_SPECIAL");
        String basestyle = (String)sqldata.getValue(DSL.fieldByName(new String[] { "basestyle" }));
        if (((sqldata = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "category" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_domestic" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "sku" }).equal(basestyle) }).limit(1).fetchOne()) != null) && (sqldata.size() != 0)) {
          thestyleinfo.put("category", (String)sqldata.getValue(DSL.fieldByName(new String[] { "category" })));
        } else if (((sqldata = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "Main Category" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_lackpard" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "Style" }).equal(basestyle) }).limit(1).fetchOne()) != null) && (sqldata.size() != 0)) {
          thestyleinfo.put("category", (String)sqldata.getValue(DSL.fieldByName(new String[] { "Main Category" })));
        }
      }
      else if (((sqldata = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_domestic_shirts" })).where(new Condition[] { DSL.fieldByName(new String[] { "sku" }).equal(thestyle) }).limit(1).fetchOne()) != null) && (sqldata.size() != 0)) {
        thestyleinfo.put("styletype", "DOMESTIC_SHIRTS");
      } else if (((sqldata = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_domestic_sweatshirts" })).where(new Condition[] { DSL.fieldByName(new String[] { "sku" }).equal(thestyle) }).limit(1).fetchOne()) != null) && (sqldata.size() != 0)) {
        thestyleinfo.put("styletype", "DOMESTIC_SWEATSHIRTS");
      } else if (((sqldata = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_domestic_totebags" })).where(new Condition[] { DSL.fieldByName(new String[] { "sku" }).equal(thestyle) }).limit(1).fetchOne()) != null) && (sqldata.size() != 0)) {
        thestyleinfo.put("styletype", "DOMESTIC_TOTEBAGS");
      } else if (((sqldata = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_domestic_aprons" })).where(new Condition[] { DSL.fieldByName(new String[] { "sku" }).equal(thestyle) }).limit(1).fetchOne()) != null) && (sqldata.size() != 0)) {
        thestyleinfo.put("styletype", "DOMESTIC_APRONS");
      }
    }
    
    return thestyleinfo;
  }
  
  private String echokeys(Record therows) {
    String myecho = "stylenumber=" + getUrlSafe(therows.getValue(DSL.fieldByName(new String[] { "stylenumber" }))) + "&";
    myecho = myecho + "colorcode=" + getUrlSafe(therows.getValue(DSL.fieldByName(new String[] { "colorcode" }))) + "&";
    if (this.ordertype.equals("Overseas")) {
      myecho = myecho + "frontpanelcolor=" + getUrlSafe(therows.getValue(DSL.fieldByName(new String[] { "frontpanelcolor" }))) + "&";
      myecho = myecho + "sidebackpanelcolor=" + getUrlSafe(therows.getValue(DSL.fieldByName(new String[] { "sidebackpanelcolor" }))) + "&";
      myecho = myecho + "backpanelcolor=" + getUrlSafe(therows.getValue(DSL.fieldByName(new String[] { "backpanelcolor" }))) + "&";
      myecho = myecho + "sidepanelcolor=" + getUrlSafe(therows.getValue(DSL.fieldByName(new String[] { "sidepanelcolor" }))) + "&";
      myecho = myecho + "visorstylenumber=" + getUrlSafe(therows.getValue(DSL.fieldByName(new String[] { "visorstylenumber" }))) + "&";
      myecho = myecho + "primaryvisorcolor=" + getUrlSafe(therows.getValue(DSL.fieldByName(new String[] { "primaryvisorcolor" }))) + "&";
      myecho = myecho + "visortrimcolor=" + getUrlSafe(therows.getValue(DSL.fieldByName(new String[] { "visortrimcolor" }))) + "&";
      myecho = myecho + "visorsandwichcolor=" + getUrlSafe(therows.getValue(DSL.fieldByName(new String[] { "visorsandwichcolor" }))) + "&";
      myecho = myecho + "undervisorcolor=" + getUrlSafe(therows.getValue(DSL.fieldByName(new String[] { "undervisorcolor" }))) + "&";
      myecho = myecho + "distressedvisorinsidecolor=" + getUrlSafe(therows.getValue(DSL.fieldByName(new String[] { "distressedvisorinsidecolor" }))) + "&";
      myecho = myecho + "closurestylenumber=" + getUrlSafe(therows.getValue(DSL.fieldByName(new String[] { "closurestylenumber" }))) + "&";
      myecho = myecho + "closurestrapcolor=" + getUrlSafe(therows.getValue(DSL.fieldByName(new String[] { "closurestrapcolor" }))) + "&";
      myecho = myecho + "eyeletstylenumber=" + getUrlSafe(therows.getValue(DSL.fieldByName(new String[] { "eyeletstylenumber" }))) + "&";
      myecho = myecho + "fronteyeletcolor=" + getUrlSafe(therows.getValue(DSL.fieldByName(new String[] { "eyeletcolor" }))) + "&";
      myecho = myecho + "sidebackeyeletcolor=" + getUrlSafe(therows.getValue(DSL.fieldByName(new String[] { "sidebackeyeletcolor" }))) + "&";
      myecho = myecho + "backeyeletcolor=" + getUrlSafe(therows.getValue(DSL.fieldByName(new String[] { "backeyeletcolor" }))) + "&";
      myecho = myecho + "sideeyeletcolor=" + getUrlSafe(therows.getValue(DSL.fieldByName(new String[] { "sideeyeletcolor" }))) + "&";
      myecho = myecho + "buttoncolor=" + getUrlSafe(therows.getValue(DSL.fieldByName(new String[] { "buttoncolor" }))) + "&";
      myecho = myecho + "innertapingcolor=" + getUrlSafe(therows.getValue(DSL.fieldByName(new String[] { "innertapingcolor" }))) + "&";
      myecho = myecho + "sweatbandstylenumber=" + getUrlSafe(therows.getValue(DSL.fieldByName(new String[] { "sweatbandstylenumber" }))) + "&";
      myecho = myecho + "sweatbandcolor=" + getUrlSafe(therows.getValue(DSL.fieldByName(new String[] { "sweatbandcolor" }))) + "&";
      myecho = myecho + "sweatbandstripecolor=" + getUrlSafe(therows.getValue(DSL.fieldByName(new String[] { "sweatbandstripecolor" }))) + "&";
      
      myecho = myecho + "panelstitchcolor=" + getUrlSafe(therows.getValue(DSL.fieldByName(new String[] { "panelstitchcolor" }))) + "&";
      myecho = myecho + "visorrowstitching=" + getUrlSafe(therows.getValue(DSL.fieldByName(new String[] { "visorrowstitching" }))) + "&";
      myecho = myecho + "visorstitchcolor=" + getUrlSafe(therows.getValue(DSL.fieldByName(new String[] { "visorstitchcolor" }))) + "&";
      myecho = myecho + "profile=" + getUrlSafe(therows.getValue(DSL.fieldByName(new String[] { "profile" }))) + "&";
      myecho = myecho + "frontpanelfabric=" + getUrlSafe(therows.getValue(DSL.fieldByName(new String[] { "frontpanelfabric" }))) + "&";
      myecho = myecho + "sidebackpanelfabric=" + getUrlSafe(therows.getValue(DSL.fieldByName(new String[] { "sidebackpanelfabric" }))) + "&";
      myecho = myecho + "backpanelfabric=" + getUrlSafe(therows.getValue(DSL.fieldByName(new String[] { "backpanelfabric" }))) + "&";
      myecho = myecho + "sidepanelfabric=" + getUrlSafe(therows.getValue(DSL.fieldByName(new String[] { "sidepanelfabric" }))) + "&";
      myecho = myecho + "crownconstruction=" + getUrlSafe(therows.getValue(DSL.fieldByName(new String[] { "crownconstruction" }))) + "&";
      myecho = myecho + "numberofpanels=" + getUrlSafe(therows.getValue(DSL.fieldByName(new String[] { "numberofpanels" }))) + "&";
    }
    
    return myecho;
  }
  
  private String echokeysold(HttpServletRequest request) {
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
  
  private String getUrlSafe(Object toencode) {
    try {
      return java.net.URLEncoder.encode(String.valueOf(toencode), "UTF-8");
    } catch (java.io.UnsupportedEncodingException e) {}
    return "";
  }
}
