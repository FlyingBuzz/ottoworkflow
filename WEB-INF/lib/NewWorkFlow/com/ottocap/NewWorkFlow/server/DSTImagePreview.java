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











public class DSTImagePreview
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
      
      SQLTable mytable = new SQLTable();
      DSTImage myimage = new DSTImage("C:\\WorkFlow\\NewWorkflowData\\" + request.getParameter("ordertype") + "Data/" + request.getParameter("hiddenkey") + "/logos/" + request.getParameter("filename"));
      
      String[] mycolor = new String[myimage.getTotalColors()];
      
      mytable.makeTable("SELECT count(*) FROM `thread_dummycolors`");
      int totaldummynumbers = ((Long)mytable.getValue()).intValue();
      
      for (int i = 0; i < myimage.getTotalColors(); i++) {
        if ((request.getParameter("logocolorcode" + i) == null) || (request.getParameter("logocolorcode" + i).equals(""))) {
          mycolor[i] = "#FFFFFF";
        }
        else if (request.getParameter("logocolorcode" + i).startsWith("ColorCode ")) {
          int dummynumber = Integer.valueOf(request.getParameter("logocolorcode" + i).substring("ColorCode ".length()).trim()).intValue() - 1;
          mytable.makeTable("SELECT colorvalue FROM `thread_dummycolors` WHERE `code` = '" + dummynumber % totaldummynumbers + "' LIMIT 1");
          mycolor[i] = ((String)mytable.getValue());
        }
        else if (request.getParameter("threadtype" + i).equals("Madeira Rayon")) {
          mytable.makeTable("SELECT colorvalue FROM `thread_madeirar` WHERE `code` = '" + request.getParameter(new StringBuilder("logocolorcode").append(i).toString()) + "' LIMIT 1");
          mycolor[i] = ((String)mytable.getValue());
        } else if (request.getParameter("threadtype" + i).equals("Madeira Polyneon")) {
          mytable.makeTable("SELECT colorvalue FROM `thread_madeirap` WHERE `code` = '" + request.getParameter(new StringBuilder("logocolorcode").append(i).toString()) + "' LIMIT 1");
          mycolor[i] = ((String)mytable.getValue());
        } else if (request.getParameter("threadtype" + i).equals("Pantone")) {
          mytable.makeTable("SELECT colorvalue FROM `thread_pantone` WHERE `code` = '" + request.getParameter(new StringBuilder("logocolorcode").append(i).toString()) + "' LIMIT 1");
          mycolor[i] = ((String)mytable.getValue());
        } else if ((request.getParameter("threadtype" + i).equals("Match Cap Color")) || (request.getParameter("threadtype" + i).equals("Match Product Color"))) {
          mytable.makeTable("SELECT colorvalue FROM `thread_ottocolors` WHERE `code` = '" + request.getParameter(new StringBuilder("logocolorcode").append(i).toString()) + "' LIMIT 1");
          mycolor[i] = ((String)mytable.getValue());
        } else if (request.getParameter("threadtype" + i).equals("Robinson Rayon")) {
          mytable.makeTable("SELECT colorvalue FROM `thread_robinsonr` WHERE `code` = '" + request.getParameter(new StringBuilder("logocolorcode").append(i).toString()) + "' LIMIT 1");
          mycolor[i] = ((String)mytable.getValue());
        } else if (request.getParameter("threadtype" + i).equals("RA Super Brite Polyester")) {
          mytable.makeTable("SELECT colorvalue FROM `thread_rasuperbritepolyester` WHERE `code` = '" + request.getParameter(new StringBuilder("logocolorcode").append(i).toString()) + "' LIMIT 1");
          mycolor[i] = ((String)mytable.getValue());
        } else if (request.getParameter("threadtype" + i).equals("Fibres Rayon")) {
          mytable.makeTable("SELECT colorvalue FROM `thread_fibresr` WHERE `code` = '" + request.getParameter(new StringBuilder("logocolorcode").append(i).toString()) + "' LIMIT 1");
          mycolor[i] = ((String)mytable.getValue());
        }
      }
      

      mytable.closeSQL();
      File filepath = new File("C:\\WorkFlow\\NewWorkflowData\\" + request.getParameter("ordertype") + "Data/" + request.getParameter("hiddenkey") + "/logos/temp");
      filepath.mkdirs();
      BufferedImage resizedImage;
      BufferedImage resizedImage;
      if (request.getParameter("bigimage") != null) {
        resizedImage = myimage.showPNG(mycolor);
      } else {
        resizedImage = myimage.showResizedPNG(mycolor);
      }
      OutputStream myoutput = response.getOutputStream();
      response.setContentType("image/png");
      ImageIO.write(resizedImage, "png", myoutput);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
