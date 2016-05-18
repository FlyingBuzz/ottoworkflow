package com.ottocap.NewWorkFlow.server.PriceCalculation.Shipping;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData.OrderType;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderData;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataAddress;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataCustomerInformation;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataItem;
import com.ottocap.NewWorkFlow.server.ExtendOrderData.ExtendOrderDataSet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;








public class JOOQ_WorkFlowShippingCalculation
{
  private double totalprice = 0.0D;
  
  String debugstring = "";
  
  public JOOQ_WorkFlowShippingCalculation(ExtendOrderData myorderdata) throws Exception {
    boolean shipresidential = myorderdata.getCustomerInformation().getShipInformation().getResidential();
    String shipzip = myorderdata.getCustomerInformation().getShipInformation().getZip();
    String shippingtype = myorderdata.getShippingType();
    

    DefaultHttpClient httpclient = new DefaultHttpClient();
    HttpPost httppost = new HttpPost("http://www.ottocap.com/workflowshippingcalc.php");
    
    try
    {
      ArrayList<NameValuePair> nameValuePairs = new ArrayList(2);
      if (shipresidential) {
        nameValuePairs.add(new BasicNameValuePair("is_residential", "Y"));
      } else {
        nameValuePairs.add(new BasicNameValuePair("is_residential", "N"));
      }
      nameValuePairs.add(new BasicNameValuePair("postal_code", shipzip));
      
      for (int i = 0; i < myorderdata.getSetCount(); i++) {
        nameValuePairs.add(new BasicNameValuePair("styles[" + i + "][id]", myorderdata.getSet(i).getItem(0).getStyleNumber()));
        nameValuePairs.add(new BasicNameValuePair("styles[" + i + "][quantity]", String.valueOf(myorderdata.getSet(i).getItem(0).getQuantity())));
        nameValuePairs.add(new BasicNameValuePair("styles[" + i + "][size]", String.valueOf(myorderdata.getSet(i).getItem(0).getSize())));
        nameValuePairs.add(new BasicNameValuePair("styles[" + i + "][colorcode]", String.valueOf(myorderdata.getSet(i).getItem(0).getColorCode())));
      }
      
      httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
      

      HttpResponse response = httpclient.execute(httppost);
      String jsonstring = EntityUtils.toString(response.getEntity());
      


      ObjectMapper mapper = new ObjectMapper();
      JsonFactory factory = mapper.getJsonFactory();
      JsonParser jp = factory.createJsonParser(jsonstring);
      ArrayNode responsearray = (ArrayNode)mapper.readTree(jp);
      for (int i = 0; i < responsearray.size(); i++)
      {

        JsonNode theobject = responsearray.get(i).get("method");
        
        if ((shippingtype.equals("UPS Ground")) || (shippingtype.equals("UPS Ground Residential"))) {
          if ((theobject.get("eclipse_code").textValue().equals("UPS GRND")) || (theobject.get("eclipse_code").textValue().equals("UPS GRND RESID"))) {
            this.totalprice = responsearray.get(i).get("price").asDouble();
            i = responsearray.size();
          }
        } else if ((shippingtype.equals("UPS Next Day")) || (shippingtype.equals("UPS Next Saturday"))) {
          if (theobject.get("eclipse_code").textValue().equals("UPS NEXT DY")) {
            this.totalprice = responsearray.get(i).get("price").asDouble();
            i = responsearray.size();
          }
        } else if (shippingtype.equals("UPS Next Day Saver")) {
          if (theobject.get("eclipse_code").textValue().equals("UPS NEXT DY SVR")) {
            this.totalprice = responsearray.get(i).get("price").asDouble();
            i = responsearray.size();
          }
        } else if (shippingtype.equals("UPS 2nd Day")) {
          if (theobject.get("eclipse_code").textValue().equals("UPS 2DY")) {
            this.totalprice = responsearray.get(i).get("price").asDouble();
            i = responsearray.size();
          }
        } else if ((shippingtype.equals("UPS 3rd Day")) && 
          (theobject.get("eclipse_code").textValue().equals("UPS 3DY"))) {
          this.totalprice = responsearray.get(i).get("price").asDouble();
          i = responsearray.size();
        }
      }
    }
    catch (ClientProtocolException localClientProtocolException) {}catch (IOException localIOException) {}
    






    this.debugstring = ("totalprice = " + this.totalprice + "<BR>\n");
  }
  
  public void oldCode(ExtendOrderData myorderdata)
    throws Exception
  {
    String shipzip = myorderdata.getCustomerInformation().getShipInformation().getZip();
    String shippingtype = myorderdata.getShippingType();
    boolean shipresidential = myorderdata.getCustomerInformation().getShipInformation().getResidential();
    String addressline1_to = myorderdata.getCustomerInformation().getShipInformation().getStreetLine1() + " " + myorderdata.getCustomerInformation().getShipInformation().getStreetLine2();
    String city_to = myorderdata.getCustomerInformation().getShipInformation().getCity();
    String state_to = myorderdata.getCustomerInformation().getShipInformation().getState();
    
    ShippingData mydata = new ShippingData();
    mydata.setZipCode(shipzip);
    mydata.setAddressLine1_To(addressline1_to);
    mydata.setCity_To(city_to);
    mydata.setState_To(state_to);
    mydata.setHasEmb(true);
    mydata.setPaymentType(ShippingData.PaymentType_CC);
    mydata.setResidential(shipresidential);
    
    for (int i = 0; i < myorderdata.getSetCount(); i++) {
      mydata.addShippingItem(new ShippingItem(myorderdata.getSet(i).getItem(0).getStyleNumber(), myorderdata.getSet(i).getItem(0).getSize(), myorderdata.getSet(i).getItem(0).getColorCode(), myorderdata.getSet(i).getItem(0).getQuantity().intValue()));
      
      if (myorderdata.getSet(i).getItem(0).getProductSampleShip()) {
        if (myorderdata.getOrderType() == OrderData.OrderType.DOMESTIC) {
          if ((myorderdata.getSet(i).getItem(0).getProductSampleTotalDone() != null) && (myorderdata.getSet(i).getItem(0).getProductSampleTotalDone().intValue() > 0)) {
            mydata.addShippingItem(new ShippingItem(myorderdata.getSet(i).getItem(0).getStyleNumber(), myorderdata.getSet(i).getItem(0).getSize(), myorderdata.getSet(i).getItem(0).getColorCode(), myorderdata.getSet(i).getItem(0).getProductSampleTotalDone().intValue()));
          }
        }
        else if ((myorderdata.getSet(i).getItem(0).getProductSampleTotalShip() != null) && (myorderdata.getSet(i).getItem(0).getProductSampleTotalShip().intValue() > 0)) {
          mydata.addShippingItem(new ShippingItem(myorderdata.getSet(i).getItem(0).getStyleNumber(), myorderdata.getSet(i).getItem(0).getSize(), myorderdata.getSet(i).getItem(0).getColorCode(), myorderdata.getSet(i).getItem(0).getProductSampleTotalShip().intValue()));
        }
      }
    }
    


    JOOQ_ShippingCalculation newcalc = new JOOQ_ShippingCalculation(mydata);
    if ((shippingtype.equals("UPS Ground")) || (shippingtype.equals("UPS Ground Residential"))) {
      this.totalprice = ((Double)newcalc.getTotalShipping().get("03")).doubleValue();
    } else if ((shippingtype.equals("UPS Next Day")) || (shippingtype.equals("UPS Next Saturday"))) {
      this.totalprice = ((Double)newcalc.getTotalShipping().get("01")).doubleValue();
    } else if (shippingtype.equals("UPS Next Day Saver")) {
      this.totalprice = ((Double)newcalc.getTotalShipping().get("13")).doubleValue();
    } else if (shippingtype.equals("UPS 2nd Day")) {
      this.totalprice = ((Double)newcalc.getTotalShipping().get("02")).doubleValue();
    } else if (shippingtype.equals("UPS 3rd Day")) {
      this.totalprice = ((Double)newcalc.getTotalShipping().get("12")).doubleValue();
    }
    
    this.debugstring = newcalc.getDebugString().replace("\n", "<BR>\n");
  }
  
  public String getDebugInfo()
  {
    return this.debugstring;
  }
  
  public double getTotalPrice() {
    return this.totalprice;
  }
}
