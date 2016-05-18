package com.ottocap.NewWorkFlow.server;

import com.ottocap.NewWorkFlow.server.LogoGenerator.DSTImage;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

public class JOOQ_DSTImagePreview
  extends HttpServlet
{
  private static final long serialVersionUID = 1L;
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    doPost(request, response);
  }
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      response.setHeader("Cache-Control", "no-cache");
      response.setHeader("Pragma", "no-cache");
      response.setDateHeader("Expires", 0L);
      
      DSTImage myimage = new DSTImage("C:\\WorkFlow\\NewWorkflowData\\" + request.getParameter("ordertype") + "Data/" + request.getParameter("hiddenkey") + "/logos/" + request.getParameter("filename"));
      
      String[] mycolor = new String[myimage.getTotalColors()];
      
      int totaldummynumbers = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "thread_dummycolors" })).fetchCount();
      
      for (int i = 0; i < myimage.getTotalColors(); i++) {
        if ((request.getParameter("logocolorcode" + i) == null) || (request.getParameter("logocolorcode" + i).equals(""))) {
          mycolor[i] = "#FFFFFF";
        }
        else if (request.getParameter("logocolorcode" + i).startsWith("ColorCode ")) {
          int dummynumber = Integer.valueOf(request.getParameter("logocolorcode" + i).substring("ColorCode ".length()).trim()).intValue() - 1;
          Record colorvalue = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "colorvalue" })).from(new TableLike[] { DSL.tableByName(new String[] { "thread_dummycolors" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "code" }).equal(Integer.valueOf(dummynumber % totaldummynumbers)) }).limit(1).fetchOne();
          mycolor[i] = ((String)colorvalue.getValue(DSL.fieldByName(new String[] { "colorvalue" })));
        }
        else if (request.getParameter("threadtype" + i).equals("Madeira Rayon")) {
          Record colorvalue = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "colorvalue" })).from(new TableLike[] { DSL.tableByName(new String[] { "thread_madeirar" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "code" }).equal(request.getParameter("logocolorcode" + i)) }).limit(1).fetchOne();
          mycolor[i] = ((String)colorvalue.getValue(DSL.fieldByName(new String[] { "colorvalue" })));
        } else if (request.getParameter("threadtype" + i).equals("Madeira Polyneon")) {
          Record colorvalue = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "colorvalue" })).from(new TableLike[] { DSL.tableByName(new String[] { "thread_madeirap" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "code" }).equal(request.getParameter("logocolorcode" + i)) }).limit(1).fetchOne();
          mycolor[i] = ((String)colorvalue.getValue(DSL.fieldByName(new String[] { "colorvalue" })));
        } else if (request.getParameter("threadtype" + i).equals("Pantone")) {
          Record colorvalue = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "colorvalue" })).from(new TableLike[] { DSL.tableByName(new String[] { "thread_pantone" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "code" }).equal(request.getParameter("logocolorcode" + i)) }).limit(1).fetchOne();
          mycolor[i] = ((String)colorvalue.getValue(DSL.fieldByName(new String[] { "colorvalue" })));
        } else if ((request.getParameter("threadtype" + i).equals("Match Cap Color")) || (request.getParameter("threadtype" + i).equals("Match Product Color"))) {
          Record colorvalue = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "colorvalue" })).from(new TableLike[] { DSL.tableByName(new String[] { "thread_ottocolors" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "code" }).equal(request.getParameter("logocolorcode" + i)) }).limit(1).fetchOne();
          mycolor[i] = ((String)colorvalue.getValue(DSL.fieldByName(new String[] { "colorvalue" })));
        } else if (request.getParameter("threadtype" + i).equals("Robinson Rayon")) {
          Record colorvalue = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "colorvalue" })).from(new TableLike[] { DSL.tableByName(new String[] { "thread_robinsonr" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "code" }).equal(request.getParameter("logocolorcode" + i)) }).limit(1).fetchOne();
          mycolor[i] = ((String)colorvalue.getValue(DSL.fieldByName(new String[] { "colorvalue" })));
        } else if (request.getParameter("threadtype" + i).equals("RA Super Brite Polyester")) {
          Record colorvalue = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "colorvalue" })).from(new TableLike[] { DSL.tableByName(new String[] { "thread_rasuperbritepolyester" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "code" }).equal(request.getParameter("logocolorcode" + i)) }).limit(1).fetchOne();
          mycolor[i] = ((String)colorvalue.getValue(DSL.fieldByName(new String[] { "colorvalue" })));
        } else if (request.getParameter("threadtype" + i).equals("Fibres Rayon")) {
          Record colorvalue = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "colorvalue" })).from(new TableLike[] { DSL.tableByName(new String[] { "thread_fibresr" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "code" }).equal(request.getParameter("logocolorcode" + i)) }).limit(1).fetchOne();
          mycolor[i] = ((String)colorvalue.getValue(DSL.fieldByName(new String[] { "colorvalue" })));
        }
      }
      


      File filepath = new File("C:\\WorkFlow\\NewWorkflowData\\" + request.getParameter("ordertype") + "Data/" + request.getParameter("hiddenkey") + "/logos/temp");
      filepath.mkdirs();
      BufferedImage resizedImage = myimage.showResizedPNG(mycolor);
      OutputStream myoutput = response.getOutputStream();
      response.setContentType("image/png");
      ImageIO.write(resizedImage, "png", myoutput);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
