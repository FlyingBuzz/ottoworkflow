package com.ottocap.NewWorkFlow.server.PriceCalculation.Shipping.UPS;

import com.sun.net.ssl.internal.ssl.Provider;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Security;
import java.util.Properties;











public class UPSXMLTransmitter
{
  private String hostname;
  private String protocol;
  private StringBuffer XmlIn;
  private StringBuffer XmlOut;
  private String encodedPass;
  
  public UPSXMLTransmitter(String hostname, String protocol)
  {
    this.hostname = hostname.trim();
    this.protocol = protocol.trim();
  }
  










  public UPSXMLTransmitter(String hostname, String protocol, String proxy, String port, String username, String password)
  {
    this.hostname = hostname.trim();
    this.protocol = protocol.trim();
    if ((proxy != null) && (port != null)) {
      System.getProperties().put("https.proxyHost", proxy);
      System.getProperties().put("https.proxyPort", port);
    }
  }
  



  public void contactService(String service, String prefix)
    throws Exception
  {
    try
    {
      HttpURLConnection connection;
      


      HttpURLConnection connection;
      

      if (this.protocol.equalsIgnoreCase("https"))
      {
        Security.addProvider(new Provider());
        System.getProperties().put("java.protocol.handler.pkgs", "com.sun.net.ssl.internal.www.protocol");
        URL url = new URL(this.protocol + "://" + this.hostname + "/" + prefix + "/" + service);
        
        connection = (HttpURLConnection)url.openConnection();
      } else {
        URL url = new URL(this.protocol + "://" + this.hostname + "/" + prefix + "/" + service);
        connection = (HttpURLConnection)url.openConnection();
      }
      


      connection.setDoOutput(true);
      connection.setDoInput(true);
      connection.setUseCaches(false);
      if (this.encodedPass != null) {
        connection.setRequestProperty("Proxy-Authorization", this.encodedPass);
      }
      String queryString = this.XmlIn.toString();
      

      OutputStream out = connection.getOutputStream();
      out.write(queryString.getBytes());
      
      out.close();
      

      String data = "";
      try {
        data = readURLConnection(connection);
      } catch (Exception e) {
        System.out.println("Eror in reading URL Connection" + e.getMessage());
        throw e;
      }
      
      this.XmlOut = new StringBuffer(data);
    } catch (Exception e1) {
      System.out.println("Error sending data to server: " + e1.toString() + " " + e1.getMessage());
    }
  }
  






  public StringBuffer getXml()
  {
    return this.XmlOut;
  }
  


























































































  public static StringBuffer readInputFile(String file)
    throws Exception
  {
    StringBuffer xml = new StringBuffer();
    InputStreamReader in = new InputStreamReader(new FileInputStream(file));
    LineNumberReader lineReader = new LineNumberReader(in);
    String line_xml = null;
    while ((line_xml = lineReader.readLine()) != null) {
      xml.append(line_xml);
    }
    lineReader.close();
    return xml;
  }
  
  /* Error */
  private static String readURLConnection(java.net.URLConnection uc)
    throws Exception
  {
    // Byte code:
    //   0: new 126	java/lang/StringBuffer
    //   3: dup
    //   4: invokespecial 189	java/lang/StringBuffer:<init>	()V
    //   7: astore_1
    //   8: aconst_null
    //   9: astore_2
    //   10: new 216	java/io/BufferedReader
    //   13: dup
    //   14: new 190	java/io/InputStreamReader
    //   17: dup
    //   18: aload_0
    //   19: invokevirtual 218	java/net/URLConnection:getInputStream	()Ljava/io/InputStream;
    //   22: invokespecial 195	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
    //   25: invokespecial 224	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   28: astore_2
    //   29: iconst_0
    //   30: istore_3
    //   31: goto +10 -> 41
    //   34: aload_1
    //   35: iload_3
    //   36: i2c
    //   37: invokevirtual 225	java/lang/StringBuffer:append	(C)Ljava/lang/StringBuffer;
    //   40: pop
    //   41: aload_2
    //   42: invokevirtual 228	java/io/BufferedReader:read	()I
    //   45: dup
    //   46: istore_3
    //   47: iconst_m1
    //   48: if_icmpne -14 -> 34
    //   51: goto +56 -> 107
    //   54: astore_3
    //   55: getstatic 151	java/lang/System:out	Ljava/io/PrintStream;
    //   58: new 79	java/lang/StringBuilder
    //   61: dup
    //   62: ldc -24
    //   64: invokespecial 85	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   67: aload_3
    //   68: invokevirtual 170	java/lang/Exception:toString	()Ljava/lang/String;
    //   71: invokevirtual 90	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   74: invokevirtual 96	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   77: invokevirtual 160	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   80: aload_3
    //   81: athrow
    //   82: astore 4
    //   84: aload_2
    //   85: invokevirtual 234	java/io/BufferedReader:close	()V
    //   88: goto +16 -> 104
    //   91: astore 5
    //   93: getstatic 151	java/lang/System:out	Ljava/io/PrintStream;
    //   96: ldc -21
    //   98: invokevirtual 160	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   101: aload 5
    //   103: athrow
    //   104: aload 4
    //   106: athrow
    //   107: aload_2
    //   108: invokevirtual 234	java/io/BufferedReader:close	()V
    //   111: goto +16 -> 127
    //   114: astore 5
    //   116: getstatic 151	java/lang/System:out	Ljava/io/PrintStream;
    //   119: ldc -21
    //   121: invokevirtual 160	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   124: aload 5
    //   126: athrow
    //   127: aload_1
    //   128: invokevirtual 125	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   131: areturn
    // Line number table:
    //   Java source line #236	-> byte code offset #0
    //   Java source line #237	-> byte code offset #8
    //   Java source line #239	-> byte code offset #10
    //   Java source line #240	-> byte code offset #29
    //   Java source line #241	-> byte code offset #31
    //   Java source line #242	-> byte code offset #34
    //   Java source line #241	-> byte code offset #41
    //   Java source line #244	-> byte code offset #51
    //   Java source line #245	-> byte code offset #55
    //   Java source line #246	-> byte code offset #80
    //   Java source line #247	-> byte code offset #82
    //   Java source line #249	-> byte code offset #84
    //   Java source line #250	-> byte code offset #88
    //   Java source line #251	-> byte code offset #93
    //   Java source line #252	-> byte code offset #101
    //   Java source line #254	-> byte code offset #104
    //   Java source line #249	-> byte code offset #107
    //   Java source line #250	-> byte code offset #111
    //   Java source line #251	-> byte code offset #116
    //   Java source line #252	-> byte code offset #124
    //   Java source line #255	-> byte code offset #127
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	132	0	uc	java.net.URLConnection
    //   7	121	1	buffer	StringBuffer
    //   9	99	2	reader	java.io.BufferedReader
    //   30	17	3	letter	int
    //   54	27	3	e	Exception
    //   82	23	4	localObject	Object
    //   91	11	5	io	java.io.IOException
    //   114	11	5	io	java.io.IOException
    // Exception table:
    //   from	to	target	type
    //   10	51	54	java/lang/Exception
    //   10	82	82	finally
    //   84	88	91	java/io/IOException
    //   107	111	114	java/io/IOException
  }
  
  public void setXml(StringBuffer input)
  {
    this.XmlIn = input;
  }
  


  public static void writeOutputFile(StringBuffer str, String file)
    throws Exception
  {
    FileOutputStream fout = new FileOutputStream(file);
    fout.write(str.toString().getBytes());
    fout.close();
  }
}
