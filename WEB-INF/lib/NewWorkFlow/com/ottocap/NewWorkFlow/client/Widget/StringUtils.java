package com.ottocap.NewWorkFlow.client.Widget;



public class StringUtils
{
  private static String[][] htmlEscape = { { "&lt;", "<" }, { "&gt;", ">" }, 
    { "&amp;", "&" }, { "&quot;", "\"" }, 
    { "&agrave;", "à" }, { "&Agrave;", "À" }, 
    { "&acirc;", "â" }, { "&auml;", "ä" }, 
    { "&Auml;", "Ä" }, { "&Acirc;", "Â" }, 
    { "&aring;", "å" }, { "&Aring;", "Å" }, 
    { "&aelig;", "æ" }, { "&AElig;", "Æ" }, 
    { "&ccedil;", "ç" }, { "&Ccedil;", "Ç" }, 
    { "&eacute;", "é" }, { "&Eacute;", "É" }, 
    { "&egrave;", "è" }, { "&Egrave;", "È" }, 
    { "&ecirc;", "ê" }, { "&Ecirc;", "Ê" }, 
    { "&euml;", "ë" }, { "&Euml;", "Ë" }, 
    { "&iuml;", "ï" }, { "&Iuml;", "Ï" }, 
    { "&ocirc;", "ô" }, { "&Ocirc;", "Ô" }, 
    { "&ouml;", "ö" }, { "&Ouml;", "Ö" }, 
    { "&oslash;", "ø" }, { "&Oslash;", "Ø" }, 
    { "&szlig;", "ß" }, { "&ugrave;", "ù" }, 
    { "&Ugrave;", "Ù" }, { "&ucirc;", "û" }, 
    { "&Ucirc;", "Û" }, { "&uuml;", "ü" }, 
    { "&Uuml;", "Ü" }, { "&nbsp;", " " }, 
    { "&copy;", "©" }, 
    { "&reg;", "®" }, 
    { "&euro;", "₠" } };
  


  public static final String unescapeHTML(String s, int start)
  {
    int i = s.indexOf("&", start);
    start = i + 1;
    if (i > -1) {
      int j = s.indexOf(";", i);
      




      if (j > i)
      {


        String temp = s.substring(i, j + 1);
        
        int k = 0;
        while (k < htmlEscape.length) {
          if (htmlEscape[k][0].equals(temp)) break;
          k++;
        }
        if (k < htmlEscape.length) {
          s = 
            s.substring(0, i) + htmlEscape[k][1] + s.substring(j + 1);
          return unescapeHTML(s, i);
        }
      }
    }
    return s;
  }
}
