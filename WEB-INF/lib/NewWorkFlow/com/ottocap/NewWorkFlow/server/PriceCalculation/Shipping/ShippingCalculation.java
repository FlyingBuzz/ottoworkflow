package com.ottocap.NewWorkFlow.server.PriceCalculation.Shipping;

import com.extjs.gxt.ui.client.core.FastMap;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.ottocap.NewWorkFlow.server.SQLTable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;






public class ShippingCalculation
{
  private SQLTable mytable;
  private String debugstring = "";
  private TreeMap<String, Double> totalshipping = new TreeMap();
  private double codcharge = 0.0D;
  public static final String ShippingType_UPS_NEXT_DAY_AIR = "01";
  public static final String ShippingType_UPS_SECOND_DAY_AIR = "02";
  public static final String ShippingType_UPS_GROUND = "03";
  public static final String ShippingType_UPS_WORLDWIDE_EXPRESS = "07";
  public static final String ShippingType_UPS_WORLDWIDE_EXPEDITED = "08";
  public static final String ShippingType_UPS_STANDARD = "11";
  public static final String ShippingType_UPS_THREE_DAY_SELECT = "12";
  public static final String ShippingType_UPS_NEXT_DAY_AIR_SAVER = "13";
  public static final String ShippingType_UPS_NEXT_DAY_AIR_EARLY_AM = "14";
  public static final String ShippingType_UPS_WORLDWIDE_EXPRESS_PLUS = "54";
  public static final String ShippingType_UPS_SECOND_DAY_AIR_AM = "59";
  public static final String ShippingType_UPS_SAVER = "65";
  
  public ShippingCalculation(ShippingData shippingdata, SQLTable sqltable) throws Exception {
    this(shippingdata, sqltable, null);
  }
  
  public ShippingCalculation(ShippingData shippingdata, SQLTable sqltable, WarehouseData warehousedata) throws Exception {
    this.mytable = sqltable;
    



    for (int i = 0; i < shippingdata.getTotalShippingItems(); i++) {
      String itemid = shippingdata.getShippingItem(i).getItemId();
      String itemsize = shippingdata.getShippingItem(i).getItemSize();
      
      int producttype = 0;
      if (this.mytable.makeTable("SELECT * FROM `styles_domestic_shirts` WHERE `sku` = '" + itemid + "' LIMIT 1").booleanValue()) {
        producttype = 2;
      } else if (this.mytable.makeTable("SELECT * FROM `styles_domestic_sweatshirts` WHERE `sku` = '" + itemid + "' LIMIT 1").booleanValue()) {
        producttype = 2;
      } else if (this.mytable.makeTable("SELECT * FROM `styles_domestic_totebags` WHERE `sku` = '" + itemid + "' LIMIT 1").booleanValue()) {
        producttype = 2;
      } else if (this.mytable.makeTable("SELECT * FROM `styles_domestic_aprons` WHERE `sku` = '" + itemid + "' LIMIT 1").booleanValue()) {
        producttype = 2;
      } else if (itemid.equals("85-466")) {
        producttype = 3;
      } else {
        producttype = 1;
      }
      shippingdata.getShippingItem(i).setProductType(producttype);
      
      double itemweight = 0.0D;
      if (this.mytable.makeTable("SELECT `weight` FROM `styles_domestic` WHERE `sku` = '" + itemid + "'").booleanValue()) {
        try {
          itemweight = Double.valueOf(String.valueOf(this.mytable.getValue())).doubleValue();
        }
        catch (Exception localException) {}
      } else if (this.mytable.makeTable("SELECT `weight` FROM `styles_domestic_shirts_sizecolor` WHERE `sku` = '" + itemid + "' AND `size` = '" + itemsize + "' LIMIT 1").booleanValue()) {
        try {
          itemweight = ((Double)this.mytable.getValue()).doubleValue();
        }
        catch (Exception localException1) {}
      } else if (this.mytable.makeTable("SELECT `weight` FROM `styles_domestic_sweatshirts` WHERE `sku` = '" + itemid + "' AND `new_size_option` = '" + itemsize + "' LIMIT 1").booleanValue()) {
        try {
          itemweight = ((Double)this.mytable.getValue()).doubleValue();
        }
        catch (Exception localException2) {}
      } else if (this.mytable.makeTable("SELECT `weight` FROM `styles_domestic_aprons` WHERE `sku` = '" + itemid + "' LIMIT 1").booleanValue()) {
        try {
          itemweight = ((Double)this.mytable.getValue()).doubleValue();
        }
        catch (Exception localException3) {}
      } else if (this.mytable.makeTable("SELECT `weight` FROM `styles_domestic_totebags` WHERE `sku` = '" + itemid + "' LIMIT 1").booleanValue()) {
        try {
          itemweight = ((Double)this.mytable.getValue()).doubleValue();
        }
        catch (Exception localException4) {}
      } else if (this.mytable.makeTable("SELECT `Piece Weight` FROM `styles_overseas` WHERE `Style` = '" + itemid + "' LIMIT 1").booleanValue()) {
        try {
          itemweight = Double.valueOf(String.valueOf(this.mytable.getValue())).doubleValue();
        }
        catch (Exception localException5) {}
      } else if (this.mytable.makeTable("SELECT `Piece Weight` FROM `styles_pre-designed` WHERE `Style` = '" + itemid + "' LIMIT 1").booleanValue()) {
        try {
          itemweight = Double.valueOf(String.valueOf(this.mytable.getValue())).doubleValue();
        }
        catch (Exception localException6) {}
      } else {
        throw new Exception("Cant find item " + itemid);
      }
      
      shippingdata.getShippingItem(i).setItemWeight(itemweight);
      
      if (producttype == 2) {
        if (this.mytable.makeTable("SELECT * FROM `box_weights_shirts` WHERE `style_id` = '" + itemid + "' AND `style_size` = '" + itemsize + "'").booleanValue()) {
          for (int j = 0; j < this.mytable.getTable().size(); j++) {
            shippingdata.getShippingItem(i).addBox(((Integer)((FastMap)this.mytable.getTable().get(j)).get("box_type_id")).intValue(), ((Integer)((FastMap)this.mytable.getTable().get(j)).get("cubic_in")).intValue());
          }
        } else {
          this.debugstring += "===== WARNING =====\n";
          this.debugstring = (this.debugstring + "box_weights_shirts table could not find style_id for " + itemid + " & style_size " + itemsize + "\n");
          this.debugstring += "===================\n";
        }
      }
      else if (this.mytable.makeTable("SELECT * FROM `box_weights2` WHERE `style_id` = '" + itemid + "-" + itemsize + "'").booleanValue()) {
        for (int j = 0; j < this.mytable.getTable().size(); j++) {
          shippingdata.getShippingItem(i).addBox(((Integer)((FastMap)this.mytable.getTable().get(j)).get("box_type_id")).intValue(), ((Integer)((FastMap)this.mytable.getTable().get(j)).get("cubic_in")).intValue());
        }
      } else if (this.mytable.makeTable("SELECT * FROM `box_weights2` WHERE `style_id` = '" + itemid + "'").booleanValue()) {
        for (int j = 0; j < this.mytable.getTable().size(); j++) {
          shippingdata.getShippingItem(i).addBox(((Integer)((FastMap)this.mytable.getTable().get(j)).get("box_type_id")).intValue(), ((Integer)((FastMap)this.mytable.getTable().get(j)).get("cubic_in")).intValue());
        }
      } else {
        this.debugstring += "===== WARNING =====\n";
        this.debugstring = (this.debugstring + "box_weights2 table could not find style_id for " + itemid + " \n");
        this.debugstring += "===================\n";
      }
    }
    


    this.debugstring += "<table border=1><tr><td>Item #</td><td>Style Id</td><td>Quantity</td><td>Prodcut Type</td><td>Weight</td></tr>";
    for (int i = 0; i < shippingdata.getTotalShippingItems(); i++) {
      this.debugstring = (this.debugstring + "<tr><td>" + i + "</td>");
      this.debugstring = (this.debugstring + "<td>" + shippingdata.getShippingItem(i).getItemId() + "</td>");
      this.debugstring = (this.debugstring + "<td>" + shippingdata.getShippingItem(i).getQuantity() + "</td>");
      this.debugstring = (this.debugstring + "<td>" + shippingdata.getShippingItem(i).getProductType() + "</td>");
      this.debugstring = (this.debugstring + "<td>" + shippingdata.getShippingItem(i).getItemWeight() + "</td></tr>");
    }
    this.debugstring += "</table> \n";
    





    if (warehousedata == null) {
      if (!shippingdata.getHasEmb()) {
        warehousedata = new WarehouseData();
        this.mytable.makeTable("SELECT * FROM `warehouse`");
        ArrayList<FastMap<Object>> warehousetable = this.mytable.getTable();
        for (int i = 0; i < warehousetable.size(); i++) {
          warehousedata.addWarehouseUnit(((Long)((FastMap)warehousetable.get(i)).get("id")).longValue(), (String)((FastMap)warehousetable.get(i)).get("postal_code"));
        }
      } else {
        warehousedata = new WarehouseData();
        this.mytable.makeTable("SELECT * FROM `warehouse` WHERE `postal_code` = '91761'");
        warehousedata.addWarehouseUnit(((Long)this.mytable.getCell("id")).longValue(), (String)this.mytable.getCell("postal_code"));
      }
    }
    

    this.debugstring = (this.debugstring + "Number of Warehouse: " + warehousedata.getTotalWearhouse() + " \n");
    
    this.debugstring += "<table border=1><tr><td>Warehouse Id</td><td>Warehouse Zip</td><td>Current Zip</td><td>Distance</td></tr>";
    for (int i = 0; i < warehousedata.getTotalWearhouse(); i++)
    {



      warehousedata.getWarehouseUnit(i).setDistance(100.0D);
      this.debugstring = (this.debugstring + "<tr><td>" + warehousedata.getWarehouseUnit(i).getId() + "</td>");
      this.debugstring = (this.debugstring + "<td>" + warehousedata.getWarehouseUnit(i).getFullZipCode() + "</td>");
      this.debugstring = (this.debugstring + "<td>" + shippingdata.getZipCode() + "</td>");
      this.debugstring = (this.debugstring + "<td>" + warehousedata.getWarehouseUnit(i).getDistance() + "</td></tr>");
    }
    this.debugstring += "</table> \n";
    

    warehousedata.sortWarehouse();
    this.debugstring += "Sorted Warehouse: \n";
    
    this.debugstring += "<table border=1><tr><td>Warehouse Id</td><td>Warehouse Zip</td><td>Current Zip</td><td>Distance</td></tr>";
    for (int i = 0; i < warehousedata.getTotalWearhouse(); i++) {
      this.debugstring = (this.debugstring + "<tr><td>" + warehousedata.getWarehouseUnit(i).getId() + "</td>");
      this.debugstring = (this.debugstring + "<td>" + warehousedata.getWarehouseUnit(i).getFullZipCode() + "</td>");
      this.debugstring = (this.debugstring + "<td>" + shippingdata.getZipCode() + "</td>");
      this.debugstring = (this.debugstring + "<td>" + warehousedata.getWarehouseUnit(i).getDistance() + "</td></tr>");
    }
    this.debugstring += "</table> \n";
    




    this.debugstring += "<table border=1><tr><td>Item Style</td><td>Color Code</td><td>Product ID</td>";
    for (int i = 0; i < warehousedata.getTotalWearhouse(); i++) {
      this.debugstring = (this.debugstring + "<td>Warehouse ID: " + warehousedata.getWarehouseUnit(i).getId() + " Quantity</td>");
    }
    this.debugstring += "</tr>";
    
    for (int i = 0; i < shippingdata.getTotalShippingItems(); i++)
    {
      ShippingItem theitem = shippingdata.getShippingItem(i);
      
      this.debugstring = (this.debugstring + "<tr><td>" + theitem.getItemId() + "</td><td>" + theitem.getColorCode() + "</td><td>" + theitem.getItemId() + "</td>");
      
      int itemquantity = theitem.getQuantity();
      
      for (int j = 0; j < warehousedata.getTotalWearhouse(); j++) {
        warehousedata.getWarehouseUnit(j).getId();
        

        int warehousequantity = 1000000000;
        this.debugstring = (this.debugstring + "<td>" + warehousequantity + "</td>");
        
        int amounttoadd = 0;
        if (warehousequantity - itemquantity >= 0)
        {
          amounttoadd = itemquantity;
          itemquantity = 0;
        }
        else {
          itemquantity -= warehousequantity;
          amounttoadd = warehousequantity;
        }
        
        if (amounttoadd > 0) {
          ShippingItem myitem = new ShippingItem(theitem.getItemId(), theitem.getItemSize(), theitem.getColorCode(), amounttoadd, theitem.getProductType());
          myitem.setItemWeight(theitem.getItemWeight());
          myitem.setItemBoxSizes(theitem.getItemBoxSizes());
          warehousedata.getWarehouseUnit(j).getShippingData().addShippingItem(myitem);
        }
      }
      

      this.debugstring += "</tr>";
      
      if (itemquantity > 0) {
        throw new Exception("Not Enough Items In Stock");
      }
    }
    
    this.debugstring += "</table> \n";
    
    this.debugstring += "Items Assigned To Warehouse \n";
    for (int i = 0; i < warehousedata.getTotalWearhouse(); i++) {
      this.debugstring = (this.debugstring + "Warehouse #" + warehousedata.getWarehouseUnit(i).getId() + " of zip " + warehousedata.getWarehouseUnit(i).getZipCode() + " \n");
      
      String tempstringwarehouseitem = "<table border=1><tr><td>Item Style</td>";
      String tempstringwarehouseqty = "</tr><tr><td>Warehouse QTY to use</td>";
      for (int j = 0; j < warehousedata.getWarehouseUnit(i).getShippingData().getTotalShippingItems(); j++) {
        tempstringwarehouseitem = tempstringwarehouseitem + "<td>" + warehousedata.getWarehouseUnit(i).getShippingData().getShippingItem(j).getItemId() + "_" + warehousedata.getWarehouseUnit(i).getShippingData().getShippingItem(j).getColorCode() + "</td>";
        tempstringwarehouseqty = tempstringwarehouseqty + "<td>" + warehousedata.getWarehouseUnit(i).getShippingData().getShippingItem(j).getQuantity() + "</td>";
      }
      
      this.debugstring = (this.debugstring + tempstringwarehouseitem + tempstringwarehouseqty + "</tr></table> \n");
    }
    



    for (int i = 0; i < warehousedata.getTotalWearhouse(); i++) {
      warehousedata.getWarehouseUnit(i).getShippingData().setHasEmb(shippingdata.getHasEmb());
      warehousedata.getWarehouseUnit(i).getShippingData().setPaymentType(shippingdata.getPaymentType());
      warehousedata.getWarehouseUnit(i).getShippingData().setResidential(shippingdata.getResidential());
      warehousedata.getWarehouseUnit(i).getShippingData().setZipCode(shippingdata.getZipCode());
      warehousedata.getWarehouseUnit(i).getShippingData().setAddressLine1_To(shippingdata.getAddressLine1_To());
      warehousedata.getWarehouseUnit(i).getShippingData().setCity_To(shippingdata.getCity_To());
      warehousedata.getWarehouseUnit(i).getShippingData().setState_To(shippingdata.getState_To());
      ShippingCalculationSingleLocation mylocation = new ShippingCalculationSingleLocation(warehousedata.getWarehouseUnit(i).getShippingData(), warehousedata.getWarehouseUnit(i).getFullZipCode(), this.mytable);
      
      if (shippingdata.getPaymentType() == ShippingData.PaymentType_COD) {
        this.codcharge += mylocation.getTotalCODPrice();
      }
      
      TreeMap<String, Double> totalwarehouseprice = mylocation.getTotalPrice();
      
      String[] totalwarehousekeys = (String[])totalwarehouseprice.keySet().toArray(new String[0]);
      for (int j = 0; j < totalwarehouseprice.size(); j++) { double thecost;
        double thecost;
        if (this.totalshipping.containsKey(totalwarehousekeys[j])) {
          thecost = ((Double)this.totalshipping.get(totalwarehousekeys[j])).doubleValue();
        } else {
          thecost = 0.0D;
        }
        thecost += ((Double)totalwarehouseprice.get(totalwarehousekeys[j])).doubleValue();
        this.totalshipping.put(totalwarehousekeys[j], Double.valueOf(thecost));
      }
      

      this.debugstring = (this.debugstring + mylocation.output + " \n");
    }
    
    this.debugstring += "====================================================== \n";
    this.debugstring += "Final Totals \n";
    
    String[] totalshippingkeys = (String[])this.totalshipping.keySet().toArray(new String[0]);
    String tempshipmethodstring = "<table border=1><tr><td>Ship Method</td>";
    String tempshipmethodtotal = "</tr><tr><td>Total Shipping</td>";
    for (int i = 0; i < this.totalshipping.size(); i++) {
      tempshipmethodstring = tempshipmethodstring + "<td>" + totalshippingkeys[i] + "</td>";
      tempshipmethodtotal = tempshipmethodtotal + "<td>" + this.totalshipping.get(totalshippingkeys[i]) + "</td>";
    }
    
    this.debugstring = (this.debugstring + tempshipmethodstring + tempshipmethodtotal + "</tr></table> \n");
    
    if (shippingdata.getPaymentType() == ShippingData.PaymentType_SCOD) {
      this.codcharge = 9.0D;
    }
    if ((shippingdata.getPaymentType() == ShippingData.PaymentType_SCOD) || (shippingdata.getPaymentType() == ShippingData.PaymentType_COD)) {
      String[] totalshippingkeys2 = (String[])this.totalshipping.keySet().toArray(new String[0]);
      String tempshipmethodstring2 = "<table border=1><tr><td>Ship Method</td>";
      String tempshipmethodtotal2 = "</tr><tr><td>Total Shipping With COD</td>";
      for (int i = 0; i < this.totalshipping.size(); i++) {
        tempshipmethodstring2 = tempshipmethodstring2 + "<td>" + totalshippingkeys2[i] + "</td>";
        tempshipmethodtotal2 = tempshipmethodtotal2 + "<td>" + (((Double)this.totalshipping.get(totalshippingkeys2[i])).doubleValue() + this.codcharge) + "</td>";
      }
      
      this.debugstring = (this.debugstring + tempshipmethodstring2 + tempshipmethodtotal2 + "</tr></table> \n");
    }
  }
  
  public double getTotalShippingOfType(String shippingtype)
  {
    return ((Double)this.totalshipping.get(shippingtype)).doubleValue();
  }
  
  public TreeMap<String, Double> getTotalShipping() {
    return this.totalshipping;
  }
  
  public double getCODCharge() {
    return this.codcharge;
  }
  
  public double getShipmentDistance(String zip1, String zip2)
    throws Exception
  {
    String thezip1;
    String thezip1;
    if (zip1.length() > 5) {
      thezip1 = zip1.substring(0, 5);
    } else
      thezip1 = zip1;
    String thezip2;
    String thezip2;
    if (zip2.length() > 5) {
      thezip2 = zip2.substring(0, 5);
    } else {
      thezip2 = zip2;
    }
    


    double lat1 = 0.0D;
    double lat2 = 0.0D;
    double long1 = 0.0D;
    double long2 = 0.0D;
    try {
      this.mytable.makeTable("SELECT * FROM `zip_cache` WHERE `zip` = '" + thezip1 + "'");
      lat1 = Double.valueOf((String)this.mytable.getCell("lat")).doubleValue();
      long1 = Double.valueOf((String)this.mytable.getCell("long")).doubleValue();
    } catch (Exception e) {
      Double[] mypos = queryGoogleZip(thezip1);
      lat1 = mypos[1].doubleValue();
      long1 = mypos[0].doubleValue();
    }
    try
    {
      this.mytable.makeTable("SELECT * FROM `zip_cache` WHERE `zip` = '" + thezip2 + "'");
      lat2 = Double.valueOf((String)this.mytable.getCell("lat")).doubleValue();
      long2 = Double.valueOf((String)this.mytable.getCell("long")).doubleValue();
    } catch (Exception e) {
      Double[] mypos = queryGoogleZip(thezip2);
      lat2 = mypos[1].doubleValue();
      long2 = mypos[0].doubleValue();
    }
    
    return calculateDistance(lat1, long1, lat2, long2);
  }
  
  public Double[] queryGoogleZip(String zip) throws Exception
  {
    Double[] myloc = new Double[2];
    boolean foundzip = false;
    myloc[0] = Double.valueOf(0.0D);
    myloc[1] = Double.valueOf(0.0D);
    
    this.debugstring += "Zip Code Missing From Database Looking It Up From Google \n";
    URL mysite = new URL("http://maps.google.com/maps/geo?q=" + zip + "&output=json&oe=utf8\\&sensor=true_or_false&key=ABQIAAAA_ehwO9R2zTJtVql-yVuJOBTIqciBol7PnK3Sj4Gk48l5c3GNDhRu8AeNuAf4OMrXn0fh87hYgtkbgw");
    String jsoncontext = null;
    try {
      jsoncontext = UrlReader.getUrlContext(mysite);
    } catch (Exception e) {
      return myloc;
    }
    JSONObject myobject = JSONParser.parseStrict(jsoncontext).isObject();
    if (myobject.containsKey("Placemark")) {
      JSONArray placemarkarray = myobject.get("Placemark").isArray();
      for (int i = 0; i < placemarkarray.size(); i++) {
        if ((placemarkarray.get(i).isObject().get("AddressDetails").isObject().get("Coutnry").isObject().containsKey("AdministrativeArea")) && 
          (placemarkarray.get(i).isObject().get("AddressDetails").isObject().get("Coutnry").isObject().get("AdministrativeArea").isObject().containsKey("Locality")) && 
          (placemarkarray.get(i).isObject().get("AddressDetails").isObject().get("Coutnry").isObject().get("AdministrativeArea").isObject().get("Locality").isObject().get("PostalCode").isObject().get("PostalCodeNumber").isString().stringValue().equals(zip))) {
          String state = placemarkarray.get(i).isObject().get("AddressDetails").isObject().get("Coutnry").isObject().get("AdministrativeArea").isObject().get("AdministrativeAreaName").isString().stringValue();
          String city = placemarkarray.get(i).isObject().get("AddressDetails").isObject().get("Coutnry").isObject().get("AdministrativeArea").isObject().get("Locality").isObject().get("LocalityName").isString().stringValue();
          String country = placemarkarray.get(i).isObject().get("AddressDetails").isObject().get("Coutnry").isObject().get("CountryNameCode").isString().stringValue();
          JSONArray myarray = myobject.get("Placemark").isArray().get(0).isObject().get("Point").isObject().get("coordinates").isArray();
          myloc[0] = Double.valueOf(myarray.get(0).isNumber().doubleValue());
          myloc[1] = Double.valueOf(myarray.get(1).isNumber().doubleValue());
          this.debugstring += myobject.toString();
          FastMap<Object> mykeys = new FastMap();
          mykeys.put("zip", zip);
          mykeys.put("lat", String.valueOf(myloc[1]));
          mykeys.put("long", String.valueOf(myloc[0]));
          mykeys.put("state", state);
          mykeys.put("city", city);
          mykeys.put("country", country);
          this.mytable.insertRow("zip_cache", mykeys);
          foundzip = true;
          i = placemarkarray.size();
        }
      }
    }
    

    if (!foundzip) {
      this.debugstring += "Invalid Zip Using Distance of 0 \n";
    }
    
    return myloc;
  }
  
  public double calculateDistance(double lat1, double long1, double lat2, double long2) {
    double distance = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(long1 - long2));
    
    distance = Math.toDegrees(Math.acos(distance)) * 69.09D;
    
    return distance;
  }
  
  public String getDebugString() {
    return this.debugstring;
  }
}
