package com.ottocap.NewWorkFlow.server.PriceCalculation.Shipping;

import com.ottocap.NewWorkFlow.server.JOOQSQL;
import com.ottocap.NewWorkFlow.server.PriceCalculation.Shipping.UPS.RSSResponse;
import com.ottocap.NewWorkFlow.server.PriceCalculation.Shipping.UPS.RSSResponse.Error;
import com.ottocap.NewWorkFlow.server.PriceCalculation.Shipping.UPS.RatingServiceSelectionResponseContainer;
import com.ottocap.NewWorkFlow.server.PriceCalculation.Shipping.UPS.UPSShippingUtilsNew2;
import java.io.PrintStream;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.jooq.SelectJoinStep;
import org.jooq.SelectLimitStep;
import org.jooq.SelectSelectStep;
import org.jooq.SelectWhereStep;
import org.jooq.SortField;
import org.jooq.impl.DSL;

public class JOOQ_ShippingCalculationSingleLocation
{
  String output = "";
  ShippingData myorderdata;
  TreeMap<String, Double> totalprice = new TreeMap();
  double totalcodprice = 0.0D;
  String fromzip;
  TreeMap<Integer, PriceHolder> seperatedtotal = new TreeMap();
  
  public JOOQ_ShippingCalculationSingleLocation(ShippingData shippingdata, String fromzip) throws Exception {
    this.output += "====================================================== \n";
    this.output = (this.output + "Start of warehouse calculate of zip " + fromzip + " \n");
    this.output += "====================================================== \n";
    this.fromzip = fromzip;
    this.myorderdata = shippingdata;
    

    this.myorderdata.generateNewFilterProduct();
    
    TreeMap<Integer, Vector<Box>> allboxes = new TreeMap();
    boolean lessthansixdiff = false;
    int lessqtycount = 0;
    


    for (int i = 0; i < 4; i++) {
      boolean sameboxset = false;
      if (this.myorderdata.getTotalShippingItemsOf(i) != 0) {
        Vector<Box> theboxes = getBoxes(i);
        allboxes.put(Integer.valueOf(i), theboxes);
        for (int j = 0; j < theboxes.size(); j++) {
          Box currentbox = (Box)theboxes.get(j);
          if ((currentbox.getQuantity() < 6) && (currentbox.getQuantity() > 0)) {
            if (!sameboxset) {
              lessthansixdiff = lessqtycount != 0;
            }
            lessqtycount += currentbox.getQuantity();
            sameboxset = true;
          }
        }
      }
    }
    
    if ((lessthansixdiff) && (lessqtycount <= 6) && (lessqtycount > 0)) {
      int lastprojecttype = 0;
      this.output += "Will Have Mix Qty \n";
      Record mixboxdimrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "value" })).from(new org.jooq.TableLike[] { DSL.tableByName(new String[] { "settings" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "name" }).equal("mix_box_dim") }).limit(1).fetchOne();
      int mix_box_dim = Integer.valueOf((String)mixboxdimrecord.getValue(DSL.fieldByName(new String[] { "value" }))).intValue();
      Box themixbox = new Box(6, mix_box_dim, 0);
      for (int i = 0; i < 4; i++) {
        if (this.myorderdata.getTotalShippingItemsOf(i) != 0) {
          Vector<Box> theboxes = (Vector)allboxes.get(Integer.valueOf(i));
          for (int k = 0; k < theboxes.size(); k++) {
            Box currentbox = (Box)theboxes.get(k);
            if (currentbox.getQuantity() < 6) {
              theboxes.remove(k);
              k--;
              Item[] myitems = currentbox.getItems();
              for (int j = 0; j < myitems.length; j++) {
                if (myitems[j] != null) {
                  themixbox.add(myitems[j]);
                  lastprojecttype = i;
                }
              }
            }
          }
        }
      }
      ((Vector)allboxes.get(Integer.valueOf(lastprojecttype))).add(themixbox);
    }
    
    for (int i = 0; i < 4; i++) {
      if (this.myorderdata.getTotalShippingItemsOf(i) != 0) {
        this.seperatedtotal.put(Integer.valueOf(i), getShipmentPrices((Vector)allboxes.get(Integer.valueOf(i))));
        
        String[] itemkey = (String[])((PriceHolder)this.seperatedtotal.get(Integer.valueOf(i))).getShippingCosts().keySet().toArray(new String[0]);
        String tempitemshipmethod = "<table border=1><tr><td>Ship Method</td>";
        String tempitemtotalprice = "</tr><tr><td>Total Price Of ProductType: " + i + "</td>";
        for (int j = 0; j < ((PriceHolder)this.seperatedtotal.get(Integer.valueOf(i))).getShippingCosts().size(); j++) {
          tempitemshipmethod = tempitemshipmethod + "<td>" + itemkey[j] + "</td>";
          tempitemtotalprice = tempitemtotalprice + "<td>" + ((PriceHolder)this.seperatedtotal.get(Integer.valueOf(i))).getShippingCosts().get(itemkey[j]) + "</td>";
        }
        
        this.output = (this.output + tempitemshipmethod + tempitemtotalprice + "</tr></table> \n");
      }
    }
    

    for (int i = 0; i < 4; i++) {
      if (this.seperatedtotal.containsKey(Integer.valueOf(i))) {
        this.totalcodprice += ((PriceHolder)this.seperatedtotal.get(Integer.valueOf(i))).getCODPrice();
        String[] shippingtypeskey = (String[])((PriceHolder)this.seperatedtotal.get(Integer.valueOf(i))).getShippingCosts().keySet().toArray(new String[0]);
        for (int j = 0; j < shippingtypeskey.length; j++) {
          if (this.totalprice.containsKey(shippingtypeskey[j])) {
            this.totalprice.put(shippingtypeskey[j], Double.valueOf(((Double)this.totalprice.get(shippingtypeskey[j])).doubleValue() + ((Double)((PriceHolder)this.seperatedtotal.get(Integer.valueOf(i))).getShippingCosts().get(shippingtypeskey[j])).doubleValue()));
          } else {
            this.totalprice.put(shippingtypeskey[j], (Double)((PriceHolder)this.seperatedtotal.get(Integer.valueOf(i))).getShippingCosts().get(shippingtypeskey[j]));
          }
        }
      }
    }
    
    String[] currentkey = (String[])this.totalprice.keySet().toArray(new String[0]);
    String tempshipmethod = "<table border=1><tr><td>Ship Method</td>";
    String temptotalprice = "</tr><tr><td>Total Price of Warehouse</td>";
    for (int i = 0; i < this.totalprice.size(); i++) {
      tempshipmethod = tempshipmethod + "<td>" + currentkey[i] + "</td>";
      temptotalprice = temptotalprice + "<td>" + this.totalprice.get(currentkey[i]) + "</td>";
    }
    
    this.output = (this.output + tempshipmethod + temptotalprice + "</tr></table> \n");
  }
  
  public TreeMap<String, Double> getTotalPrice()
  {
    return this.totalprice;
  }
  
  public double getTotalCODPrice() {
    return this.totalcodprice;
  }
  
  public String getOutput() {
    return this.output;
  }
  
  public Vector<Box> getBoxes(int producttype) throws Exception
  {
    int totalitems = 0;
    
    String temptotalitemnum = "<table border=1><tr><td>Item #</td>";
    String temptotalitmstyle = "</tr><tr><td><B>Style Number</B></td>";
    String temptotalitmqty = "</tr><tr><td>Quantity</td>";
    for (int i = 0; i < this.myorderdata.getTotalShippingItemsOf(producttype); i++) {
      totalitems += ((ShippingItem)this.myorderdata.getShippingItemOf(producttype).get(i)).getQuantity();
      temptotalitemnum = temptotalitemnum + "<td>" + i + "</td>";
      temptotalitmstyle = temptotalitmstyle + "<td>" + ((ShippingItem)this.myorderdata.getShippingItemOf(producttype).get(i)).getItemId() + "</td>";
      temptotalitmqty = temptotalitmqty + "<td>" + ((ShippingItem)this.myorderdata.getShippingItemOf(producttype).get(i)).getQuantity() + "</td>";
    }
    
    this.output = (this.output + temptotalitemnum + temptotalitmstyle + temptotalitmqty + "</tr></table> \n");
    
    this.output = (this.output + "Total Items: " + totalitems + " \n");
    
    Vector<Box> myboxes = new Vector();
    
    Result<Record> boxtypesrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "box_types" })).where(new Condition[] { DSL.fieldByName(new String[] { "product_type" }).equal(Integer.valueOf(producttype)) }).orderBy(new SortField[] { DSL.fieldByName(new String[] { "max_qty" }).desc() }).fetch();
    this.output += "<table border=1>";
    for (int i = 0; i < boxtypesrecord.size(); i++) {
      myboxes.addAll(putItemInBoxSize(((Integer)((Record)boxtypesrecord.get(i)).getValue(DSL.fieldByName(new String[] { "max_qty" }))).intValue(), ((Integer)((Record)boxtypesrecord.get(i)).getValue(DSL.fieldByName(new String[] { "id" }))).intValue(), ((Integer)((Record)boxtypesrecord.get(i)).getValue(DSL.fieldByName(new String[] { "fit_qty" }))).intValue(), producttype));
    }
    this.output += "</table> \n";
    
    return myboxes;
  }
  
  private PriceHolder getShipmentPrices(Vector<Box> myboxes) throws Exception { String to_zip;
    String to_zip;
    if (this.myorderdata.getZipCode().length() > 5) {
      to_zip = this.myorderdata.getZipCode().substring(0, 5);
    } else {
      to_zip = this.myorderdata.getZipCode();
    }
    
    TreeMap<String, Double> shippingcosts = shipmentPrice(myboxes, to_zip, Boolean.valueOf(this.myorderdata.getResidential()));
    double codprice = myboxes.size() * 9.0D;
    

    return new PriceHolder(shippingcosts, codprice);
  }
  
  private TreeMap<String, Double> shipmentPrice(Vector<Box> boxes, String to_zip, Boolean residential) throws Exception
  {
    double totalboxweights = 0.0D;
    for (int i = 0; i < boxes.size(); i++) {
      totalboxweights += ((Box)boxes.get(i)).getHigherWeight();
    }
    this.output = (this.output + "Total Box: " + boxes.size() + " \n");
    this.output = (this.output + "Total Box Weight: " + totalboxweights + " \n");
    
    totalboxweights = Math.round(totalboxweights);
    this.output = (this.output + "Total Box Weight Rounded: " + totalboxweights + " \n");
    



































































































































































































































































    TreeMap<String, Double> shippingtotalprice = new TreeMap();
    TreeMap<String, Integer> matchingweight = new TreeMap();
    for (int i = 0; i < boxes.size(); i++) {
      String weightkey = String.valueOf(((Box)boxes.get(i)).getHigherWeight());
      if (!matchingweight.containsKey(weightkey)) {
        matchingweight.put(weightkey, Integer.valueOf(1));
      } else {
        matchingweight.put(weightkey, Integer.valueOf(((Integer)matchingweight.get(weightkey)).intValue() + 1));
      }
    }
    
    String[] mykeys = (String[])matchingweight.keySet().toArray(new String[0]);
    
    if (mykeys.length == 0) {
      return null;
    }
    
    TreeMap<String, Double[]> itemandprice = upsnew3(residential, this.fromzip, to_zip, mykeys);
    if (itemandprice == null) {
      throw new UnknownHostException("Can't get UPS server");
    }
    
    String[] itemandpricekeys = (String[])itemandprice.keySet().toArray(new String[0]);
    this.output += "-------------------------------------------- \n";
    this.output += "UPS Server Data \n";
    this.output += "-------------------------------------------- \n";
    for (int i = 0; i < itemandprice.size(); i++)
    {
      double currentypetotal = 0.0D;
      this.output = (this.output + "Current Ship Method: " + itemandpricekeys[i] + " \n");
      String temppackageweight = "<table border=1><tr><td>Package Weight</td>";
      String temppackageprice = "</tr><tr><td>Package Price</td>";
      for (int j = 0; j < ((Double[])itemandprice.get(itemandpricekeys[i])).length; j++) {
        temppackageweight = temppackageweight + "<td>" + mykeys[j] + "</td>";
        temppackageprice = temppackageprice + "<td>" + ((Double[])itemandprice.get(itemandpricekeys[i]))[j] + " of " + (Integer)matchingweight.get(mykeys[j]) + " packages</td>";
        currentypetotal += ((Double[])itemandprice.get(itemandpricekeys[i]))[j].doubleValue() * ((Integer)matchingweight.get(mykeys[j])).intValue();
      }
      
      this.output = (this.output + temppackageweight + temppackageprice + "</tr></table> \n");
      
      shippingtotalprice.put(itemandpricekeys[i], Double.valueOf(currentypetotal));
    }
    
    this.output += "-------------------------------------------- \n";
    this.output += "UPS Server Calculation \n";
    this.output += "-------------------------------------------- \n";
    
    String[] itemkey = (String[])shippingtotalprice.keySet().toArray(new String[0]);
    String tempitemshipmethod = "<table border=1><tr><td>Ship Method</td>";
    String tempitemtotalprice = "</tr><tr><td>Total Price Of Product </td>";
    for (int i = 0; i < shippingtotalprice.size(); i++) {
      tempitemshipmethod = tempitemshipmethod + "<td>" + itemkey[i] + "</td>";
      tempitemtotalprice = tempitemtotalprice + "<td>" + shippingtotalprice.get(itemkey[i]) + "</td>";
    }
    this.output = (this.output + tempitemshipmethod + tempitemtotalprice + "</tr></table> \n");
    























    return shippingtotalprice;
  }
  

  private TreeMap<String, Double[]> upsnew3(Boolean residential, String from_zip, String to_zip, String[] allweights)
    throws Exception
  {
    TreeMap<String, Double[]> itemandprice = null;
    



    RatingServiceSelectionResponseContainer rssrc = UPSShippingUtilsNew2.submitUPSShippingRequest(to_zip, 
    

      from_zip, new java.math.BigDecimal("0.0"), 
      allweights, false, 
      residential.booleanValue(), this.myorderdata.getAddressLine1_To(), this.myorderdata.getCity_To(), this.myorderdata.getState_To());
    




    if (UPSShippingUtilsNew2.requestSucceeded(rssrc)) {
      itemandprice = UPSShippingUtilsNew2.getUPSShippingCost(rssrc);
    }
    else if (rssrc != null) {
      RSSResponse.Error error = (RSSResponse.Error)rssrc.getRssResponse().getErrors().get(0);
      


      throw new Exception(error.errorDescription);
    }
    


    return itemandprice;
  }
  
  private Vector<Box> putItemInBoxSize(int max_qty, int box_id, int fit_qty, int producttype) throws Exception {
    Vector<Box> myboxes = new Vector();
    
    this.output = (this.output + "<tr><td>Currently at max_qty " + max_qty + "</td>");
    

    for (int i = 0; i < this.myorderdata.getTotalShippingItemsOf(producttype); i++) {
      while (((ShippingItem)this.myorderdata.getShippingItemOf(producttype).get(i)).getQuantity() >= max_qty)
      {
        Box mybox = new Box(max_qty, ((ShippingItem)this.myorderdata.getShippingItemOf(producttype).get(i)).getItem().getBoxCubicInch(box_id), box_id);
        for (int j = 0; j < max_qty; j++) {
          mybox.add(((ShippingItem)this.myorderdata.getShippingItemOf(producttype).get(i)).getItem());
        }
        myboxes.add(mybox);
        

        ((ShippingItem)this.myorderdata.getShippingItemOf(producttype).get(i)).setQuantity(((ShippingItem)this.myorderdata.getShippingItemOf(producttype).get(i)).getQuantity() - max_qty);
      }
    }
    


    TreeMap<String, Integer> cubicinchweight = new TreeMap();
    for (int i = 0; i < this.myorderdata.getTotalShippingItemsOf(producttype); i++) {
      double itemweight = ((ShippingItem)this.myorderdata.getShippingItemOf(producttype).get(i)).getItem().getWeight();
      int itemboxcubicinch = ((ShippingItem)this.myorderdata.getShippingItemOf(producttype).get(i)).getItem().getBoxCubicInch(box_id);
      String uniquekey = itemboxcubicinch + " " + itemweight;
      if (!cubicinchweight.containsKey(uniquekey)) {
        cubicinchweight.put(uniquekey, Integer.valueOf(((ShippingItem)this.myorderdata.getShippingItemOf(producttype).get(i)).getQuantity()));
      } else {
        cubicinchweight.put(uniquekey, Integer.valueOf(((Integer)cubicinchweight.get(uniquekey)).intValue() + ((ShippingItem)this.myorderdata.getShippingItemOf(producttype).get(i)).getQuantity()));
      }
    }
    String[] cubicinchweightkeys = (String[])cubicinchweight.keySet().toArray(new String[0]);
    for (int i = 0; i < cubicinchweightkeys.length; i++) {
      while (((Integer)cubicinchweight.get(cubicinchweightkeys[i])).intValue() >= max_qty) {
        String[] cubicinchandweight = cubicinchweightkeys[i].split(" ");
        int cubicinch = Integer.valueOf(cubicinchandweight[0]).intValue();
        double itemweight = Double.valueOf(cubicinchandweight[1]).doubleValue();
        Box mybox = new Box(max_qty, cubicinch, box_id);
        int remainingquantity = max_qty;
        for (int j = 0; j < this.myorderdata.getTotalShippingItemsOf(producttype); j++) {
          if ((((ShippingItem)this.myorderdata.getShippingItemOf(producttype).get(j)).getItem().getWeight() == itemweight) && (((ShippingItem)this.myorderdata.getShippingItemOf(producttype).get(j)).getItem().getBoxCubicInch(box_id) == cubicinch)) {
            while ((remainingquantity > 0) && (((ShippingItem)this.myorderdata.getShippingItemOf(producttype).get(j)).getQuantity() > 0) && (!mybox.isFilled())) {
              mybox.add(((ShippingItem)this.myorderdata.getShippingItemOf(producttype).get(j)).getItem());
              remainingquantity--;
              ((ShippingItem)this.myorderdata.getShippingItemOf(producttype).get(j)).setQuantity(((ShippingItem)this.myorderdata.getShippingItemOf(producttype).get(j)).getQuantity() - 1);
              cubicinchweight.put(cubicinchweightkeys[i], Integer.valueOf(((Integer)cubicinchweight.get(cubicinchweightkeys[i])).intValue() - 1));
            }
          }
        }
        myboxes.add(mybox);
      }
    }
    


    TreeMap<String, Integer> cubicinchonly = new TreeMap();
    for (int i = 0; i < this.myorderdata.getTotalShippingItemsOf(producttype); i++) {
      int itemboxcubicinch = ((ShippingItem)this.myorderdata.getShippingItemOf(producttype).get(i)).getItem().getBoxCubicInch(box_id);
      String uniquekey = String.valueOf(itemboxcubicinch);
      if (!cubicinchonly.containsKey(uniquekey)) {
        cubicinchonly.put(uniquekey, Integer.valueOf(((ShippingItem)this.myorderdata.getShippingItemOf(producttype).get(i)).getQuantity()));
      } else {
        cubicinchonly.put(uniquekey, Integer.valueOf(((Integer)cubicinchonly.get(uniquekey)).intValue() + ((ShippingItem)this.myorderdata.getShippingItemOf(producttype).get(i)).getQuantity()));
      }
    }
    String[] cubicinchonlykeys = (String[])cubicinchonly.keySet().toArray(new String[0]);
    for (int i = 0; i < cubicinchonlykeys.length; i++) {
      while (((Integer)cubicinchonly.get(cubicinchonlykeys[i])).intValue() >= max_qty) {
        int cubicinch = Integer.valueOf(cubicinchonlykeys[i]).intValue();
        Box mybox = new Box(max_qty, cubicinch, box_id);
        int remainingquantity = max_qty;
        for (int j = 0; j < this.myorderdata.getTotalShippingItemsOf(producttype); j++) {
          if (((ShippingItem)this.myorderdata.getShippingItemOf(producttype).get(j)).getItem().getBoxCubicInch(box_id) == cubicinch) {
            while ((remainingquantity > 0) && (((ShippingItem)this.myorderdata.getShippingItemOf(producttype).get(j)).getQuantity() > 0) && (!mybox.isFilled())) {
              mybox.add(((ShippingItem)this.myorderdata.getShippingItemOf(producttype).get(j)).getItem());
              remainingquantity--;
              ((ShippingItem)this.myorderdata.getShippingItemOf(producttype).get(j)).setQuantity(((ShippingItem)this.myorderdata.getShippingItemOf(producttype).get(j)).getQuantity() - 1);
              cubicinchonly.put(cubicinchonlykeys[i], Integer.valueOf(((Integer)cubicinchonly.get(cubicinchonlykeys[i])).intValue() - 1));
            }
          }
        }
        myboxes.add(mybox);
      }
    }
    




    int remainingitems = 0;
    Condition sqlgroupsearchinfo = null;
    
    for (int i = 0; i < this.myorderdata.getTotalShippingItemsOf(producttype); i++) {
      remainingitems += ((ShippingItem)this.myorderdata.getShippingItemOf(producttype).get(i)).getQuantity();
      if (producttype == 2) {
        if (i == 0) {
          sqlgroupsearchinfo = DSL.fieldByName(new String[] { "style_id" }).equal(((ShippingItem)this.myorderdata.getShippingItemOf(producttype).get(i)).getItemId()).and(DSL.fieldByName(new String[] { "style_size" }).equal(((ShippingItem)this.myorderdata.getShippingItemOf(producttype).get(i)).getItemSize()));
        } else {
          sqlgroupsearchinfo = sqlgroupsearchinfo.or(DSL.fieldByName(new String[] { "style_id" }).equal(((ShippingItem)this.myorderdata.getShippingItemOf(producttype).get(i)).getItemId()).and(DSL.fieldByName(new String[] { "style_size" }).equal(((ShippingItem)this.myorderdata.getShippingItemOf(producttype).get(i)).getItemSize())));
        }
      }
      else if (i == 0) {
        sqlgroupsearchinfo = DSL.fieldByName(new String[] { "style_id" }).equal(((ShippingItem)this.myorderdata.getShippingItemOf(producttype).get(i)).getItemId());
      } else {
        sqlgroupsearchinfo = sqlgroupsearchinfo.or(DSL.fieldByName(new String[] { "style_id" }).equal(((ShippingItem)this.myorderdata.getShippingItemOf(producttype).get(i)).getItemId()));
      }
    }
    

    while (remainingitems >= max_qty) {
      Result<Record> cuberecords;
      if (producttype == 2) {
        Result<Record> cuberecords = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "box_weights_shirts" })).where(new Condition[] { sqlgroupsearchinfo }).and(DSL.fieldByName(new String[] { "box_type_id" }).equal(Integer.valueOf(box_id))).orderBy(new SortField[] { DSL.fieldByName(new String[] { "cubic_in" }).asc() }).fetch();
        if (cuberecords == null) {
          System.out.println(JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "box_weights_shirts" })).where(new Condition[] { sqlgroupsearchinfo }).and(DSL.fieldByName(new String[] { "box_type_id" }).equal(Integer.valueOf(box_id))).orderBy(new SortField[] { DSL.fieldByName(new String[] { "cubic_in" }).asc() }).getSQL() + " Field to get result");
        }
      } else {
        cuberecords = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "box_weights2" })).where(new Condition[] { sqlgroupsearchinfo }).and(DSL.fieldByName(new String[] { "box_type_id" }).equal(Integer.valueOf(box_id))).orderBy(new SortField[] { DSL.fieldByName(new String[] { "cubic_in" }).asc() }).fetch();
        if (cuberecords == null) {
          System.out.println(JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "box_weights2" })).where(new Condition[] { sqlgroupsearchinfo }).and(DSL.fieldByName(new String[] { "box_type_id" }).equal(Integer.valueOf(box_id))).orderBy(new SortField[] { DSL.fieldByName(new String[] { "cubic_in" }).asc() }).getSQL() + " Field to get result");
        }
      }
      
      int currentitemquantitycount = 0;
      int cubicinchtouse = 0;
      for (int i = 0; i < cuberecords.size(); i++) {
        String searchstyle = (String)((Record)cuberecords.get(i)).getValue(DSL.fieldByName(new String[] { "style_id" }));
        searchstyle = searchstyle.trim();
        for (int j = 0; j < this.myorderdata.getTotalShippingItemsOf(producttype); j++) {
          if (((ShippingItem)this.myorderdata.getShippingItemOf(producttype).get(j)).getItemId().equals(searchstyle)) {
            currentitemquantitycount += ((ShippingItem)this.myorderdata.getShippingItemOf(producttype).get(j)).getQuantity();
          }
        }
        if (currentitemquantitycount >= max_qty) {
          cubicinchtouse = ((Integer)((Record)cuberecords.get(i)).getValue(DSL.fieldByName(new String[] { "cubic_in" }))).intValue();
          i = cuberecords.size();
        }
      }
      





      if (producttype == 2) {
        cuberecords = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "box_weights_shirts" })).where(new Condition[] { sqlgroupsearchinfo }).and(DSL.fieldByName(new String[] { "box_type_id" }).equal(Integer.valueOf(box_id))).and(DSL.fieldByName(new String[] { "cubic_in" }).lessOrEqual(Integer.valueOf(cubicinchtouse))).orderBy(new SortField[] { DSL.fieldByName(new String[] { "cubic_in" }).asc() }).fetch();
        if (cuberecords == null) {
          System.out.println(JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "box_weights_shirts" })).where(new Condition[] { sqlgroupsearchinfo }).and(DSL.fieldByName(new String[] { "box_type_id" }).equal(Integer.valueOf(box_id))).and(DSL.fieldByName(new String[] { "cubic_in" }).lessOrEqual(Integer.valueOf(cubicinchtouse))).orderBy(new SortField[] { DSL.fieldByName(new String[] { "cubic_in" }).asc() }).getSQL() + " Field to get result");
        }
      } else {
        cuberecords = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "box_weights2" })).where(new Condition[] { sqlgroupsearchinfo }).and(DSL.fieldByName(new String[] { "box_type_id" }).equal(Integer.valueOf(box_id))).and(DSL.fieldByName(new String[] { "cubic_in" }).lessOrEqual(Integer.valueOf(cubicinchtouse))).orderBy(new SortField[] { DSL.fieldByName(new String[] { "cubic_in" }).asc() }).fetch();
        if (cuberecords == null) {
          System.out.println(JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "box_weights2" })).where(new Condition[] { sqlgroupsearchinfo }).and(DSL.fieldByName(new String[] { "box_type_id" }).equal(Integer.valueOf(box_id))).and(DSL.fieldByName(new String[] { "cubic_in" }).lessOrEqual(Integer.valueOf(cubicinchtouse))).orderBy(new SortField[] { DSL.fieldByName(new String[] { "cubic_in" }).asc() }).getSQL() + " Field to get result");
        }
      }
      
      Box mybox = new Box(max_qty, cubicinchtouse, box_id);
      int boxslotsleft = max_qty;
      for (int i = 0; i < cuberecords.size(); i++) {
        String searchstyle = ((String)((Record)cuberecords.get(i)).getValue(DSL.fieldByName(new String[] { "style_id" }))).trim();
        

        for (int j = 0; j < this.myorderdata.getTotalShippingItemsOf(producttype); j++) {
          while ((((ShippingItem)this.myorderdata.getShippingItemOf(producttype).get(j)).getItemId().equals(searchstyle)) && (boxslotsleft > 0) && (((ShippingItem)this.myorderdata.getShippingItemOf(producttype).get(j)).getQuantity() > 0) && (!mybox.isFilled()))
          {




            if (!mybox.add(((ShippingItem)this.myorderdata.getShippingItemOf(producttype).get(j)).getItem())) {
              this.output = (this.output + "MISS UP! " + mybox.getQuantity());
            }
            ((ShippingItem)this.myorderdata.getShippingItemOf(producttype).get(j)).setQuantity(((ShippingItem)this.myorderdata.getShippingItemOf(producttype).get(j)).getQuantity() - 1);
            remainingitems--;
            boxslotsleft--;
          }
        }
      }
      

      myboxes.add(mybox);
    }
    
    int itemleft = 0;
    int maxcubicinch = 0;
    for (int i = 0; i < this.myorderdata.getTotalShippingItemsOf(producttype); i++) {
      if (((ShippingItem)this.myorderdata.getShippingItemOf(producttype).get(i)).getQuantity() > 0) {
        itemleft += ((ShippingItem)this.myorderdata.getShippingItemOf(producttype).get(i)).getQuantity();
        maxcubicinch = Math.max(maxcubicinch, ((ShippingItem)this.myorderdata.getShippingItemOf(producttype).get(i)).getItem().getBoxCubicInch(box_id));
      }
    }
    

    if (itemleft >= fit_qty) {
      this.output = (this.output + "<td>Fit qty " + fit_qty + "</td>");
      Box mybox = new Box(max_qty, maxcubicinch, box_id);
      for (int i = 0; i < this.myorderdata.getTotalShippingItemsOf(producttype); i++) {
        while (((ShippingItem)this.myorderdata.getShippingItemOf(producttype).get(i)).getQuantity() > 0) {
          mybox.add(((ShippingItem)this.myorderdata.getShippingItemOf(producttype).get(i)).getItem());
          itemleft--;
          ((ShippingItem)this.myorderdata.getShippingItemOf(producttype).get(i)).setQuantity(((ShippingItem)this.myorderdata.getShippingItemOf(producttype).get(i)).getQuantity() - 1);
        }
      }
      myboxes.add(mybox);
    } else {
      this.output += "<td></td>";
    }
    
    this.output = (this.output + "<td>Total Box of " + max_qty + " is " + myboxes.size() + "</td>");
    this.output = (this.output + "<td>Item Left " + itemleft + "</td></tr>");
    


    return myboxes;
  }
}
