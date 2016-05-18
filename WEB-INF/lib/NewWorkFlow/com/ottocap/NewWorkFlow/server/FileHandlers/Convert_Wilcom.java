package com.ottocap.NewWorkFlow.server.FileHandlers;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;


public class Convert_Wilcom
{
  public Convert_Wilcom(File filepath, String filename)
    throws Exception
  {
    String username = "wilcomaccountusername";
    String password = "wilcomaccountpassword";
    File fileToUpload = new File(filepath + "/" + filename);
    filename = filename.substring(0, filename.lastIndexOf(".")) + ".DST";
    File savelocation = new File(filepath + "/" + filename);
    
    DefaultHttpClient httpclient = new DefaultHttpClient();
    httpclient.getParams().setParameter("http.protocol.allow-circular-redirects", Boolean.valueOf(true));
    httpclient.getParams().setParameter("http.useragent", "User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64; rv:21.0) Gecko/20100101 Firefox/21.0");
    







    HttpPost httpPost = new HttpPost("http://www.wilcom.com/Home/Login/tabid/215/Default.aspx?returnurl=%2fAuthenticationServer.aspx%3fclient_id%3dTrueSizerWeb%26redirect_uri%3dhttp%253A%252F%252Ftruesizerweb.wilcom.com%252Fdesigner.aspx%26state%3dEbkOYk%252FCSg9KGLTLJ5lLmA%253D%253D%26scope%3dTrueSizerWeb%26response_type%3dcode");
    List<NameValuePair> params = new ArrayList();
    params.add(new BasicNameValuePair("__EVENTTARGET", "UserLogin"));
    params.add(new BasicNameValuePair("txtUsername_853", username));
    params.add(new BasicNameValuePair("txtPassword_853", password));
    UrlEncodedFormEntity paramEntity = new UrlEncodedFormEntity(params);
    httpPost.setEntity(paramEntity);
    
    HttpResponse response = httpclient.execute(httpPost);
    EntityUtils.consume(response.getEntity());
    
    String redirectlocation = response.getHeaders("Location")[0].getValue();
    
    HttpGet httpGet = new HttpGet(redirectlocation);
    response = httpclient.execute(httpGet);
    EntityUtils.consume(response.getEntity());
    



    httpGet = new HttpGet("http://truesizerweb.wilcom.com/UploadDesign.aspx?version=0.5.3.22955");
    response = httpclient.execute(httpGet);
    
    ByteOutputStream output = new ByteOutputStream();
    response.getEntity().writeTo(output);
    output.close();
    

    String viewstate = helperSubString(output.toString(), "id=\"__VIEWSTATE\" value=\"", "\" />");
    String eventvalidation = helperSubString(output.toString(), "id=\"__EVENTVALIDATION\" value=\"", "\" />");
    

    httpPost = new HttpPost("http://truesizerweb.wilcom.com/UploadDesign.aspx?version=0.5.3.22955");
    MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
    
    multipartEntity.addPart("__VIEWSTATE", new StringBody(viewstate));
    multipartEntity.addPart("__EVENTVALIDATION", new StringBody(eventvalidation));
    multipartEntity.addPart("fileUpload", new FileBody(fileToUpload, "application/octet-stream"));
    multipartEntity.addPart("btnUpload", new StringBody("OK"));
    multipartEntity.addPart("hfDesignId", new StringBody(""));
    multipartEntity.addPart("hfTimeoutExcpetion", new StringBody(""));
    
    httpPost.setEntity(multipartEntity);
    response = httpclient.execute(httpPost);
    
    output = new ByteOutputStream();
    response.getEntity().writeTo(output);
    output.close();
    


    String uploadedfilename = helperSubString(output.toString(), "id=\"hfDesignId\" value=\"", "\" />");
    



    params = new ArrayList();
    String recipe = "{\"__type\":\"XmlEWARecipe:http://schemas.datacontract.org/2004/07/Wilcom.API.RecipeManager\",\"recipe\":{\"__type\":\"XmlKioskDocument:http://schemas.datacontract.org/2004/07/Wilcom.API.RecipeManager\",\"output\":{\"__type\":\"output:http://schemas.datacontract.org/2004/07/Wilcom.API.RecipeManager\",\"design_file\":\"" + filename + "\",\"trueview_file\":\"outputTrueview.png\",\"design_version\":\"\"},\"decorations\":[{\"__type\":\"design:http://schemas.datacontract.org/2004/07/Wilcom.API.RecipeManager\",\"file\":\"" + uploadedfilename + 
      "\",\"colorway\":\"\",\"transform\":{\"__type\":\"transform:http://schemas.datacontract.org/2004/07/Wilcom.API.RecipeManager\",\"dx\":0,\"dy\":0,\"mirror\":0,\"scale\":1,\"rotation\":0}}],\"location\":{\"__type\":\"location:http://schemas.datacontract.org/2004/07/Wilcom.API.RecipeManager\",\"width\":0,\"height\":0},\"transform\":{\"__type\":\"transform:http://schemas.datacontract.org/2004/07/Wilcom.API.RecipeManager\",\"dx\":0,\"dy\":0,\"mirror\":0,\"scale\":1,\"rotation\":0}},\"files\":null}";
    params.add(new BasicNameValuePair("recipe", recipe));
    params.add(new BasicNameValuePair("pageid", "00000000-0000-0000-0000-000000000000"));
    
    paramEntity = new UrlEncodedFormEntity(params);
    
    httpPost = new HttpPost("http://truesizerweb.wilcom.com/Download.aspx");
    httpPost.setEntity(paramEntity);
    
    response = httpclient.execute(httpPost);
    FileOutputStream out = new FileOutputStream(savelocation);
    response.getEntity().writeTo(out);
    out.close();
  }
  

  public String helperSubString(String thestring, String beginstring, String endstring)
  {
    int beginstringpos = thestring.indexOf(beginstring) + beginstring.length();
    return thestring.substring(beginstringpos, thestring.indexOf(endstring, beginstringpos));
  }
}
