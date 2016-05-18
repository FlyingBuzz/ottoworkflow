package com.ottocap.NewWorkFlow.server.Utilities;

import com.ottocap.NewWorkFlow.server.SharedData;
import java.io.IOException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class QuoteCookies
  extends HttpServlet
{
  private static final long serialVersionUID = 1L;
  
  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException
  {
    request.getSession().setMaxInactiveInterval(86400);
    request.getSession().setAttribute("username", "quote");
    request.getSession().setAttribute("level", Integer.valueOf(0));
    
    Cookie eclipseaccountnumbercookie = new Cookie("idnumber", request.getParameter("accountnumber"));
    Cookie ordertypecookie = new Cookie("ordertype", request.getParameter("type"));
    Cookie onlineprogramtype = new Cookie("onlineprogramtype", request.getParameter("programtype"));
    Cookie defaultstyle = new Cookie("defaultstyle", request.getParameter("defaultstyle"));
    Cookie defaultcolor = new Cookie("defaultcolor", request.getParameter("defaultcolor"));
    if (SharedData.isDevelopment.booleanValue()) {
      eclipseaccountnumbercookie.setPath("/");
      ordertypecookie.setPath("/");
      onlineprogramtype.setPath("/");
      defaultstyle.setPath("/");
      defaultcolor.setPath("/");
    } else {
      eclipseaccountnumbercookie.setPath("/newworkflow/");
      ordertypecookie.setPath("/newworkflow/");
      onlineprogramtype.setPath("/newworkflow/");
      defaultstyle.setPath("/newworkflow/");
      defaultcolor.setPath("/newworkflow/");
    }
    
    response.addCookie(eclipseaccountnumbercookie);
    response.addCookie(ordertypecookie);
    response.addCookie(onlineprogramtype);
    response.addCookie(defaultstyle);
    response.addCookie(defaultcolor);
    

    if (SharedData.isDevelopment.booleanValue()) {
      response.sendRedirect("/");
    } else {
      response.sendRedirect("/newworkflow/");
    }
  }
}
