package com.ottocap.NewWorkFlow.server.RPCService;

import com.extjs.gxt.ui.client.core.FastMap;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData.OrderType;
import com.ottocap.NewWorkFlow.client.StyleInformationData;
import com.ottocap.NewWorkFlow.client.StyleInformationData.StyleType;
import com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBoxModelData;
import com.ottocap.NewWorkFlow.server.OTTOCodeToColorCached;
import com.ottocap.NewWorkFlow.server.SQLTable;
import com.ottocap.NewWorkFlow.server.SharedData;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;

public class GetStyleInformationData
{
  private StyleInformationData styleinformationdata = new StyleInformationData();
  private SQLTable sqltable = new SQLTable();
  private OTTOCodeToColorCached theottocodetocolorcache = null;
  
  public GetStyleInformationData(String stylenumber, OrderData.OrderType ordertype)
  {
    if (ordertype == OrderData.OrderType.DOMESTIC) {
      if (this.sqltable.makeTable("SELECT * FROM `styles_domestic` WHERE `sku` = '" + stylenumber + "' LIMIT 1").booleanValue()) {
        FastMap<Object> therow = this.sqltable.getRow();
        this.styleinformationdata.setStyleNumber((String)therow.get("sku"));
        this.styleinformationdata.setOrderType(ordertype);
        this.styleinformationdata.setCategory((String)therow.get("category"));
        this.styleinformationdata.setSameStyleNumber((String)therow.get("samestyle"));
        
        String sizes = doSizeExpand((String)therow.get("size"), false);
        
        this.styleinformationdata.setSize(makeOtherComboObject(sizes));
        this.styleinformationdata.setColorCode(makeColorObjectCache((String)therow.get("colorcodes")));
        
        String newsizeoption = ((String)therow.get("new_size_option")).trim();
        if (!newsizeoption.equals(""))
        {
          this.styleinformationdata.setUseSizeColor(true);
          
          FastMap<ArrayList<OtherComboBoxModelData>> mysizecolor = new FastMap();
          for (OtherComboBoxModelData currentsize : this.styleinformationdata.getSize()) {
            String size = currentsize.getValue();
            if (this.sqltable.makeTable("SELECT `colorcodes` FROM `styles_domestic` WHERE `sku` = '" + stylenumber + "' AND `new_size_option` = '" + size + "' LIMIT 1").booleanValue()) {
              mysizecolor.put(size, makeColorObjectCache((String)this.sqltable.getValue()));
            } else if (this.sqltable.makeTable("SELECT `colorcodes` FROM `styles_domestic` WHERE `sku` = '" + stylenumber + "' LIMIT 1").booleanValue()) {
              mysizecolor.put(size, makeColorObjectCache((String)this.sqltable.getValue()));
            }
          }
          
          this.styleinformationdata.setSizeColor(mysizecolor);
        }
        
        if (!((String)therow.get("size")).trim().equals("")) {
          this.styleinformationdata.setDescription((String)therow.get("productname") + " (" + (String)therow.get("size") + ")");
        } else {
          this.styleinformationdata.setDescription((String)therow.get("productname"));
        }
        
        this.styleinformationdata.setLogoLocation(makeDomesticLocationObject(therow));
        this.styleinformationdata.setStyleType(StyleInformationData.StyleType.DOMESTIC); } else { boolean havemultiplesize;
        if (this.sqltable.makeTable("SELECT * FROM (SELECT * FROM (SELECT * FROM `styles_domestic_specials` ORDER BY `endingdate` DESC) AS T1 GROUP BY `style`) AS T2 WHERE `style` =  '" + stylenumber + "' LIMIT 1").booleanValue()) {
          FastMap<Object> therow = this.sqltable.getRow();
          this.styleinformationdata.setStyleNumber((String)therow.get("style"));
          this.styleinformationdata.setOrderType(ordertype);
          String specialcolorcode = (String)therow.get("colorcodes");
          this.styleinformationdata.setColorCode(makeColorObjectCache(specialcolorcode));
          this.styleinformationdata.setStyleType(StyleInformationData.StyleType.DOMESTIC_SPECIAL);
          
          boolean havecolorsize = !((String)therow.get("size")).trim().equals("");
          havemultiplesize = false;
          OtherComboBoxModelData currentsize;
          if (this.sqltable.makeTable("SELECT * FROM `styles_domestic` WHERE `sku` = '" + therow.get("basestyle") + "' LIMIT 1").booleanValue()) {
            therow = this.sqltable.getRow();
            String sizes = doSizeExpand((String)therow.get("size"), false);
            
            this.styleinformationdata.setCategory((String)therow.get("category"));
            this.styleinformationdata.setSize(makeOtherComboObject(sizes));
            this.styleinformationdata.setLogoLocation(makeDomesticLocationObject(therow));
            this.styleinformationdata.setSameStyleNumber((String)therow.get("samestyle"));
            
            String newsizeoption = ((String)therow.get("new_size_option")).trim();
            if (!newsizeoption.equals("")) {
              havemultiplesize = true;
              this.styleinformationdata.setUseSizeColor(true);
              
              FastMap<ArrayList<OtherComboBoxModelData>> mysizecolor = new FastMap();
              for (Iterator localIterator2 = this.styleinformationdata.getSize().iterator(); localIterator2.hasNext();) { currentsize = (OtherComboBoxModelData)localIterator2.next();
                String size = currentsize.getValue();
                mysizecolor.put(size, makeColorObjectCache(specialcolorcode));
              }
              
              this.styleinformationdata.setSizeColor(mysizecolor);
            }
            
            if (!((String)therow.get("size")).trim().equals("")) {
              this.styleinformationdata.setDescription((String)therow.get("productname") + " (" + (String)therow.get("size") + ")");
            } else {
              this.styleinformationdata.setDescription((String)therow.get("productname"));
            }
          }
          else if (this.sqltable.makeTable("SELECT * FROM `styles_lackpard` WHERE `Style` = '" + therow.get("basestyle") + "' LIMIT 1").booleanValue()) {
            therow = this.sqltable.getRow();
            this.styleinformationdata.setSize(makeOtherComboObject((String)therow.get("Size")));
            this.styleinformationdata.setCategory((String)therow.get("Main Category"));
            
            this.styleinformationdata.setDescription((String)therow.get("Name"));
            this.styleinformationdata.setSameStyleNumber((String)therow.get("Style"));
          }
          

          if ((havecolorsize) && (havemultiplesize)) {
            this.styleinformationdata.setUseSizeColor(true);
            
            FastMap<ArrayList<OtherComboBoxModelData>> mysizecolor = new FastMap();
            
            if (this.sqltable.makeTable("SELECT * FROM (SELECT style,size,colorcodes FROM `styles_domestic_specials` WHERE style = '" + stylenumber + "' ORDER BY endingdate DESC) as t1 GROUP BY size").booleanValue())
            {



              Object thetable = this.sqltable.getTable();
              for (FastMap<Object> currenttable : (ArrayList)thetable) {
                mysizecolor.put((String)currenttable.get("size"), makeColorObjectCache((String)currenttable.get("colorcodes")));
              }
            }
            
            this.styleinformationdata.setSizeColor(mysizecolor);
          }
        } else {
          String size;
          if (this.sqltable.makeTable("SELECT * FROM `styles_domestic_shirts` WHERE `sku` = '" + stylenumber + "' LIMIT 1").booleanValue()) {
            FastMap<Object> therow = this.sqltable.getRow();
            this.styleinformationdata.setStyleNumber((String)therow.get("sku"));
            this.styleinformationdata.setOrderType(ordertype);
            this.styleinformationdata.setUseSizeColor(true);
            this.styleinformationdata.setSize(makeOtherComboObject((String)therow.get("size")));
            this.styleinformationdata.setCategory((String)therow.get("category"));
            
            FastMap<ArrayList<OtherComboBoxModelData>> mysizecolor = new FastMap();
            for (OtherComboBoxModelData currentsize : this.styleinformationdata.getSize()) {
              size = currentsize.getValue();
              this.sqltable.makeTable("SELECT `color` FROM `styles_domestic_shirts_sizecolor` WHERE `sku` = '" + stylenumber + "' AND `size` = '" + size + "' LIMIT 1");
              mysizecolor.put(size, makeColorObjectCache((String)this.sqltable.getValue()));
            }
            
            this.styleinformationdata.setSizeColor(mysizecolor);
            
            this.styleinformationdata.setLogoLocation(makeDomesticLocationObject(therow));
            this.styleinformationdata.setStyleType(StyleInformationData.StyleType.DOMESTIC_SHIRTS);
            this.styleinformationdata.setDescription((String)therow.get("productname"));
            this.styleinformationdata.setSameStyleNumber((String)therow.get("samestyle"));
          } else if (this.sqltable.makeTable("SELECT * FROM `styles_domestic_sweatshirts` WHERE `sku` = '" + stylenumber + "' LIMIT 1").booleanValue()) {
            FastMap<Object> therow = this.sqltable.getRow();
            this.styleinformationdata.setStyleNumber((String)therow.get("sku"));
            this.styleinformationdata.setOrderType(ordertype);
            this.styleinformationdata.setCategory((String)therow.get("category"));
            
            this.styleinformationdata.setSize(makeOtherComboObject((String)therow.get("size")));
            this.styleinformationdata.setColorCode(makeColorObjectCache((String)therow.get("colorcodes")));
            
            String newsizeoption = ((String)therow.get("new_size_option")).trim();
            if (!newsizeoption.equals(""))
            {
              this.styleinformationdata.setUseSizeColor(true);
              
              FastMap<ArrayList<OtherComboBoxModelData>> mysizecolor = new FastMap();
              for (OtherComboBoxModelData currentsize : this.styleinformationdata.getSize()) {
                String size = currentsize.getValue();
                if (this.sqltable.makeTable("SELECT `colorcodes` FROM `styles_domestic_sweatshirts` WHERE `sku` = '" + stylenumber + "' AND `new_size_option` = '" + size + "' LIMIT 1").booleanValue()) {
                  mysizecolor.put(size, makeColorObjectCache((String)this.sqltable.getValue()));
                } else if (this.sqltable.makeTable("SELECT `colorcodes` FROM `styles_domestic_sweatshirts` WHERE `sku` = '" + stylenumber + "' LIMIT 1").booleanValue()) {
                  mysizecolor.put(size, makeColorObjectCache((String)this.sqltable.getValue()));
                }
              }
              
              this.styleinformationdata.setSizeColor(mysizecolor);
            }
            
            this.styleinformationdata.setLogoLocation(makeDomesticLocationObject(therow));
            this.styleinformationdata.setStyleType(StyleInformationData.StyleType.DOMESTIC_SHIRTS);
            this.styleinformationdata.setDescription((String)therow.get("productname"));
            this.styleinformationdata.setSameStyleNumber((String)therow.get("samestyle"));
          }
          else if ((stylenumber != null) && ((stylenumber.equals("nonotto")) || (stylenumber.equals("nonottoflat")))) {
            this.styleinformationdata.setStyleNumber(stylenumber);
            this.styleinformationdata.setStyleType(StyleInformationData.StyleType.NONOTTO);
            this.styleinformationdata.setShowFOBDozen(true);
            this.styleinformationdata.setOrderType(ordertype);
            this.styleinformationdata.setCategory("Non OTTO");
            if (stylenumber.equals("nonotto")) {
              this.styleinformationdata.setSize(makeOtherComboObject(doSizeExpand("All", false)));
              this.styleinformationdata.setLogoLocation(makeDomesticNONOTTOLocationObject("nonotto"));
              if (SharedData.isFaya.booleanValue()) {
                this.styleinformationdata.setDescription("Non FAYA Regular");
              } else {
                this.styleinformationdata.setDescription("Non OTTO Regular");
              }
            } else {
              this.styleinformationdata.setSize(makeOtherComboObject("XS,S,M,L,XL,2XL,3XL,4XL,5XL,6XL"));
              this.styleinformationdata.setLogoLocation(makeDomesticNONOTTOLocationObject("nonottoflat"));
              if (SharedData.isFaya.booleanValue()) {
                this.styleinformationdata.setDescription("Non FAYA Flat");
              } else {
                this.styleinformationdata.setDescription("Non OTTO Flat");
              }
            }
          } else if (this.sqltable.makeTable("SELECT * FROM `styles_lackpard` WHERE `Style` = '" + stylenumber + "' LIMIT 1").booleanValue()) {
            FastMap<Object> therow = this.sqltable.getRow();
            this.styleinformationdata.setStyleNumber((String)therow.get("Style"));
            this.styleinformationdata.setOrderType(ordertype);
            this.styleinformationdata.setSize(makeOtherComboObject((String)therow.get("Size")));
            this.styleinformationdata.setColorCode(makeColorObjectCache((String)therow.get("colorcodes")));
            this.styleinformationdata.setCategory((String)therow.get("Main Category"));
            this.styleinformationdata.setStyleType(StyleInformationData.StyleType.DOMESTIC_LACKPARD);
            this.styleinformationdata.setDescription((String)therow.get("Name"));
            this.styleinformationdata.setSameStyleNumber(stylenumber);
          }
          else if (this.sqltable.makeTable("SELECT * FROM `styles_domestic_totebags` WHERE `sku` = '" + stylenumber + "' LIMIT 1").booleanValue()) {
            FastMap<Object> therow = this.sqltable.getRow();
            this.styleinformationdata.setStyleNumber((String)therow.get("sku"));
            this.styleinformationdata.setOrderType(ordertype);
            this.styleinformationdata.setCategory((String)therow.get("category"));
            

            this.styleinformationdata.setSameStyleNumber((String)therow.get("samestyle"));
            this.styleinformationdata.setColorCode(makeColorObjectCache((String)therow.get("colorcodes")));
            this.styleinformationdata.setDescription((String)therow.get("productname"));
            this.styleinformationdata.setLogoLocation(makeDomesticLocationObject(therow));
            this.styleinformationdata.setStyleType(StyleInformationData.StyleType.DOMESTIC_TOTEBAGS);
          }
          else if (this.sqltable.makeTable("SELECT * FROM `styles_domestic_aprons` WHERE `sku` = '" + stylenumber + "' LIMIT 1").booleanValue()) {
            FastMap<Object> therow = this.sqltable.getRow();
            this.styleinformationdata.setStyleNumber((String)therow.get("sku"));
            this.styleinformationdata.setOrderType(ordertype);
            this.styleinformationdata.setCategory((String)therow.get("category"));
            

            this.styleinformationdata.setSameStyleNumber((String)therow.get("samestyle"));
            this.styleinformationdata.setColorCode(makeColorObjectCache((String)therow.get("colorcodes")));
            this.styleinformationdata.setDescription((String)therow.get("productname"));
            this.styleinformationdata.setLogoLocation(makeDomesticLocationObject(therow));
            this.styleinformationdata.setStyleType(StyleInformationData.StyleType.DOMESTIC_APRONS);
          }
          else {
            System.out.println(stylenumber + " Not Found");
          }
        } } } else if (ordertype == OrderData.OrderType.OVERSEAS) {
      String eyeletstyles;
      if (this.sqltable.makeTable("SELECT `Primary VENDOR` FROM `styles_overseasinstock` WHERE `STYLE NUMBER` = '" + stylenumber + "' LIMIT 1").booleanValue()) {
        String thevendor = (String)this.sqltable.getValue();
        if (this.sqltable.makeTable("SELECT * FROM `styles_overseasinstock` WHERE `STYLE NUMBER` = '" + stylenumber + "' AND `Backup VENDOR` = '" + thevendor + "' LIMIT 1").booleanValue()) {
          FastMap<Object> therow = this.sqltable.getRow();
          this.styleinformationdata.setStyleNumber((String)therow.get("STYLE NUMBER"));
          this.styleinformationdata.setOrderType(ordertype);
          try {
            this.styleinformationdata.setVendorNumber(Integer.valueOf((String)therow.get("Backup VENDOR")).intValue());
          } catch (Exception e) {
            e.printStackTrace();
          }
          this.styleinformationdata.setLogoLocation(makeLocationObject((String)therow.get("Location")));
          this.styleinformationdata.setCategory((String)therow.get("Category"));
          
          this.styleinformationdata.setDescription(therow.get("Emb Tooling Name") != null ? (String)therow.get("Emb Tooling Name") : "");
          this.styleinformationdata.setSameStyleNumber((String)therow.get("Same Style"));
          
          String orgclosure = ((String)therow.get("Original Closure")).trim();
          String orgeyelet = ((String)therow.get("Original Eyelet")).trim();
          
          eyeletstyles = (String)therow.get("Extra Eyelet Options");
          if (!eyeletstyles.trim().equals("")) {
            this.styleinformationdata.setOriginalEyelet("Default");
            eyeletstyles = "Default," + eyeletstyles;
          }
          this.styleinformationdata.setEyeletStyleNumber(makeEyeletObject(orgeyelet, eyeletstyles));
          
          String closures = (String)therow.get("Extra Closure Options");
          if (!closures.trim().equals("")) {
            this.styleinformationdata.setOriginalClosure("Default");
            closures = "Default," + closures;
          } else if (!orgclosure.equals("")) {
            closures = "Default";
          }
          this.styleinformationdata.setClosureStyleNumber(makeClosureObject(orgclosure, closures));
          
          this.styleinformationdata.setStyleType(StyleInformationData.StyleType.OVERSEAS_INSTOCK);
          
          String sizes = (String)therow.get("Size");
          this.styleinformationdata.setOriginalSize((String)therow.get("Default Size"));
          
          this.styleinformationdata.setSize(makeOtherComboObject(sizes));
          
          this.styleinformationdata.setColorCode(makeColorObjectCache((String)therow.get("Original Color code colorcodes")));
          
          if (stylenumber.equals("888-ConceptCap")) {
            this.styleinformationdata.setVisorStyleNumber(makeVisorObject("V1,V2,V3,V4,V5,V6,V7,V8,V9,V10,V11,V12,V13,V14,V15,V16,V17,V18"));
            this.styleinformationdata.setSweatbandStyleNumber(makeSweatbandObject("S1,S2,S3,S4,S5,S6"));
          }
          
          if (this.sqltable.makeTable("SELECT * FROM `styles_overseasinstock` WHERE `STYLE NUMBER` = '" + stylenumber + "'").booleanValue()) {
            this.styleinformationdata.getAllVendorsStore().add(new OtherComboBoxModelData("Default"));
            ArrayList<FastMap<Object>> thevendortable = this.sqltable.getTable();
            
            if ((thevendortable.size() > 0) && 
              (this.sqltable.makeTable("SELECT name FROM `list_vendors_overseas` WHERE id = '" + ((FastMap)thevendortable.get(0)).get("Primary VENDOR") + "' LIMIT 1").booleanValue())) {
              this.styleinformationdata.getAllVendorsStore().add(new OtherComboBoxModelData((String)this.sqltable.getValue(), (String)((FastMap)thevendortable.get(0)).get("Primary VENDOR")));
            }
            

            for (int i = 0; i < thevendortable.size(); i++) {
              StyleInformationData stylevendorinfodata = new StyleInformationData();
              
              stylevendorinfodata.setStyleNumber((String)((FastMap)thevendortable.get(i)).get("STYLE NUMBER"));
              stylevendorinfodata.setOrderType(ordertype);
              
              stylevendorinfodata.setVendorNumber(Integer.valueOf((String)((FastMap)thevendortable.get(i)).get("Backup VENDOR")).intValue());
              stylevendorinfodata.setLogoLocation(makeLocationObject((String)((FastMap)thevendortable.get(i)).get("Location")));
              stylevendorinfodata.setCategory((String)((FastMap)thevendortable.get(i)).get("Category"));
              
              stylevendorinfodata.setDescription(((FastMap)thevendortable.get(i)).get("Emb Tooling Name") != null ? (String)((FastMap)thevendortable.get(i)).get("Emb Tooling Name") : "");
              
              String size = (String)((FastMap)thevendortable.get(i)).get("Size");
              stylevendorinfodata.setOriginalSize((String)((FastMap)thevendortable.get(i)).get("Default Size"));
              
              stylevendorinfodata.setSize(makeOtherComboObject(size));
              
              stylevendorinfodata.setClosureStyleNumber(makeClosureObject((String)((FastMap)thevendortable.get(i)).get("Extra Closure Options")));
              stylevendorinfodata.setEyeletStyleNumber(makeEyeletObject((String)((FastMap)thevendortable.get(i)).get("Extra Eyelet Options")));
              
              String extravisorcolor = ((String)((FastMap)thevendortable.get(i)).get("Extra Visor")).trim();
              if (!extravisorcolor.equals("")) {
                stylevendorinfodata.setVisorStyleNumber(makeVisorObject(extravisorcolor));
              } else {
                stylevendorinfodata.setVisorStyleNumber(makeVisorObject((String)((FastMap)thevendortable.get(i)).get("Original Visor")));
              }
              
              stylevendorinfodata.setSweatbandStyleNumber(makeSweatbandObject((String)((FastMap)thevendortable.get(i)).get("Extra Sweatband")));
              stylevendorinfodata.setFrontPanelColor(makeColorObjectCache((String)((FastMap)thevendortable.get(i)).get("Front Panel Color")));
              stylevendorinfodata.setSideBackPanelColor(makeColorObjectCache((String)((FastMap)thevendortable.get(i)).get("Side & Back Panel Color")));
              stylevendorinfodata.setPrimaryVisorColor(makeColorObjectCache((String)((FastMap)thevendortable.get(i)).get("Primary Visor Color")));
              stylevendorinfodata.setVisorTrimEdgeColor(makeColorObjectCache((String)((FastMap)thevendortable.get(i)).get("Visor Trim/Edge Color")));
              stylevendorinfodata.setVisorSandwichColor(makeColorObjectCache((String)((FastMap)thevendortable.get(i)).get("Visor Sandwich Color")));
              
              String undervisorcolor = ((String)((FastMap)thevendortable.get(i)).get("Undervisor Color")).trim();
              if (!undervisorcolor.equals("")) {
                stylevendorinfodata.setUndervisorColor(makeColorObjectCache(undervisorcolor));
              } else {
                stylevendorinfodata.setUndervisorColor(makeColorObjectCache((String)((FastMap)thevendortable.get(i)).get("Primary Visor Color")));
              }
              
              stylevendorinfodata.setDistressedVisorInsideColor(makeColorObjectCache((String)((FastMap)thevendortable.get(i)).get("Distressed Visor Inside Color")));
              stylevendorinfodata.setClosureStrapColor(makeColorObjectCache((String)((FastMap)thevendortable.get(i)).get("Closure Strap Color")));
              stylevendorinfodata.setC2C31ClosureStrapColor(makeColorObjectCache((String)((FastMap)thevendortable.get(i)).get("C2/C31 Closure Strap Color")));
              stylevendorinfodata.setFrontEyeletColor(makeColorObjectCache((String)((FastMap)thevendortable.get(i)).get("E1 Eyelet Color")));
              stylevendorinfodata.setSideBackEyeletColor(makeColorObjectCache((String)((FastMap)thevendortable.get(i)).get("E1 Eyelet Color")));
              stylevendorinfodata.setButtonColor(makeColorObjectCache((String)((FastMap)thevendortable.get(i)).get("Button Color")));
              stylevendorinfodata.setInnerTapingColor(makeColorObjectCache((String)((FastMap)thevendortable.get(i)).get("Inner Taping Color")));
              stylevendorinfodata.setSweatbandColor(makeColorObjectCache((String)((FastMap)thevendortable.get(i)).get("Sweatband Color")));
              stylevendorinfodata.setStripeColor(makeColorObjectCache((String)((FastMap)thevendortable.get(i)).get("S2 Sweatband Color")));
              stylevendorinfodata.setS5SweatbandColor(makeColorObjectCache((String)((FastMap)thevendortable.get(i)).get("S5/S6 Sweatband Color")));
              stylevendorinfodata.setColorCode(makeColorObjectCache((String)((FastMap)thevendortable.get(i)).get("Cap Color")));
              stylevendorinfodata.setOriginalClosure(((String)((FastMap)thevendortable.get(i)).get("Original Closure")).trim());
              stylevendorinfodata.setOriginalEyelet(((String)((FastMap)thevendortable.get(i)).get("Original Eyelet")).trim());
              stylevendorinfodata.setOriginalSweatband(((String)((FastMap)thevendortable.get(i)).get("Original Sweatband")).trim());
              stylevendorinfodata.setOriginalVisor(((String)((FastMap)thevendortable.get(i)).get("Original Visor")).trim());
              stylevendorinfodata.setStyleType(StyleInformationData.StyleType.OVERSEAS_INSTOCK);
              
              stylevendorinfodata.setSameStyleNumber(((String)((FastMap)thevendortable.get(i)).get("Same Style")).trim());
              
              this.styleinformationdata.addAllVendors((String)((FastMap)thevendortable.get(i)).get("Backup VENDOR"), stylevendorinfodata);
              
              if ((!((FastMap)thevendortable.get(i)).get("Backup VENDOR").equals(((FastMap)thevendortable.get(i)).get("Primary VENDOR"))) && 
                (this.sqltable.makeTable("SELECT name FROM `list_vendors_overseas` WHERE id = '" + ((FastMap)thevendortable.get(i)).get("Backup VENDOR") + "' LIMIT 1").booleanValue())) {
                this.styleinformationdata.getAllVendorsStore().add(new OtherComboBoxModelData((String)this.sqltable.getValue(), (String)((FastMap)thevendortable.get(i)).get("Backup VENDOR")));
              }
            }
            



            for (int i = 0; i < thevendortable.size(); i++) {
              ((StyleInformationData)this.styleinformationdata.getAllVendors().get((String)((FastMap)thevendortable.get(i)).get("Backup VENDOR"))).setAllVendorsStore(this.styleinformationdata.getAllVendorsStore());
            }
            
          }
          
        }
      }
      else if (this.sqltable.makeTable("SELECT * FROM `overseas_price_instockshirts` WHERE `stylenumber` = '" + stylenumber + "' LIMIT 1").booleanValue()) {
        FastMap<Object> therow = this.sqltable.getRow();
        this.styleinformationdata.setStyleNumber((String)therow.get("stylenumber"));
        this.styleinformationdata.setOrderType(ordertype);
        this.styleinformationdata.setVendorNumber(Integer.valueOf((String)therow.get("vendor")).intValue());
        
        this.styleinformationdata.setLogoLocation(makeLocationObject((String)therow.get("location")));
        this.styleinformationdata.setCategory((String)therow.get("category"));
        
        String newsizeoption = "";
        if (this.sqltable.makeTable("SELECT * FROM `styles_domestic_shirts` WHERE `sku` = '" + stylenumber + "' LIMIT 1").booleanValue()) {
          FastMap<Object> therow2 = this.sqltable.getRow();
          this.styleinformationdata.setColorCode(makeColorObjectCache((String)therow.get("colorcode")));
          therow = therow2;
          newsizeoption = ((String)therow.get("size")).trim();
        } else if (this.sqltable.makeTable("SELECT * FROM `styles_domestic_sweatshirts` WHERE `sku` = '" + stylenumber + "' LIMIT 1").booleanValue()) {
          therow = this.sqltable.getRow();
          this.styleinformationdata.setColorCode(makeColorObjectCache((String)therow.get("colorcodes")));
          newsizeoption = ((String)therow.get("new_size_option")).trim();
        }
        
        this.styleinformationdata.setSize(makeOtherComboObject((String)therow.get("size")));
        
        this.styleinformationdata.setDescription((String)therow.get("productname"));
        this.styleinformationdata.setSameStyleNumber((String)therow.get("samestyle"));
        
        if (!newsizeoption.equals(""))
        {
          this.styleinformationdata.setUseSizeColor(true);
          
          FastMap<ArrayList<OtherComboBoxModelData>> mysizecolor = new FastMap();
          for (OtherComboBoxModelData currentsize : this.styleinformationdata.getSize()) {
            String size = currentsize.getValue();
            if (this.sqltable.makeTable("SELECT `colorcode` FROM `overseas_price_instockshirts` WHERE `stylenumber` = '" + stylenumber + "' AND `size` = '" + size + "' LIMIT 1").booleanValue()) {
              mysizecolor.put(size, makeColorObjectCache((String)this.sqltable.getValue()));
            } else if (this.sqltable.makeTable("SELECT `colorcode` FROM `overseas_price_instockshirts` WHERE `stylenumber` = '" + stylenumber + "' LIMIT 1").booleanValue()) {
              mysizecolor.put(size, makeColorObjectCache((String)this.sqltable.getValue()));
            }
          }
          
          this.styleinformationdata.setSizeColor(mysizecolor);
        }
        
        this.styleinformationdata.setStyleType(StyleInformationData.StyleType.OVERSEAS_INSTOCK_SHIRTS);
      }
      else if (this.sqltable.makeTable("SELECT * FROM `overseas_price_instockflats` WHERE `stylenumber` = '" + stylenumber + "' LIMIT 1").booleanValue()) {
        FastMap<Object> therow = this.sqltable.getRow();
        this.styleinformationdata.setStyleNumber((String)therow.get("stylenumber"));
        this.styleinformationdata.setOrderType(ordertype);
        this.styleinformationdata.setVendorNumber(Integer.valueOf((String)therow.get("vendor")).intValue());
        
        this.styleinformationdata.setLogoLocation(makeLocationObject((String)therow.get("location")));
        this.styleinformationdata.setCategory((String)therow.get("category"));
        

        this.styleinformationdata.setDescription((String)therow.get("productname"));
        this.styleinformationdata.setSameStyleNumber((String)therow.get("samestyle"));
        this.styleinformationdata.setColorCode(makeColorObjectCache((String)therow.get("colorcode")));
        
        this.styleinformationdata.setStyleType(StyleInformationData.StyleType.OVERSEAS_INSTOCK_FLATS);
      }
      else if (this.sqltable.makeTable("SELECT * FROM `styles_overseas` WHERE `Style` = '" + stylenumber + "' LIMIT 1").booleanValue()) {
        FastMap<Object> therow = this.sqltable.getRow();
        
        this.styleinformationdata.setStyleNumber((String)therow.get("Style"));
        this.styleinformationdata.setOrderType(ordertype);
        
        this.styleinformationdata.setVendorNumber(Integer.valueOf((String)therow.get("Vendor")).intValue());
        this.styleinformationdata.setLogoLocation(makeLocationObject((String)therow.get("Location")));
        this.styleinformationdata.setCategory((String)therow.get("Category"));
        this.styleinformationdata.setDescription((String)therow.get("Emb Tooling Name"));
        this.styleinformationdata.setSameStyleNumber((String)therow.get("Style"));
        
        String size = doSizeExpand((String)therow.get("Size"), true);
        this.styleinformationdata.setSize(makeOtherComboObject(size));
        
        this.styleinformationdata.setClosureStyleNumber(makeClosureObject((String)therow.get("Closure")));
        this.styleinformationdata.setEyeletStyleNumber(makeEyeletObject((String)therow.get("Eyelet Options")));
        this.styleinformationdata.setVisorStyleNumber(makeVisorObject((String)therow.get("Visor")));
        this.styleinformationdata.setSweatbandStyleNumber(makeSweatbandObject((String)therow.get("Sweatband")));
        this.styleinformationdata.setFrontPanelColor(makeColorObjectCache((String)therow.get("Front Panel Color")));
        this.styleinformationdata.setBackPanelColor(makeColorObjectCache((String)therow.get("Side & Back Panel Color")));
        this.styleinformationdata.setSidePanelColor(makeColorObjectCache((String)therow.get("Side & Back Panel Color")));
        this.styleinformationdata.setPrimaryVisorColor(makeColorObjectCache((String)therow.get("Primary Visor Color")));
        this.styleinformationdata.setVisorTrimEdgeColor(makeColorObjectCache((String)therow.get("Visor Trim/Edge Color")));
        this.styleinformationdata.setVisorSandwichColor(makeColorObjectCache((String)therow.get("Visor Sandwich Color")));
        this.styleinformationdata.setUndervisorColor(makeColorObjectCache((String)therow.get("Undervisor Color")));
        this.styleinformationdata.setDistressedVisorInsideColor(makeColorObjectCache((String)therow.get("Distressed Visor Inside Color")));
        this.styleinformationdata.setClosureStrapColor(makeColorObjectCache((String)therow.get("Closure Strap Color")));
        this.styleinformationdata.setC2C31ClosureStrapColor(makeColorObjectCache((String)therow.get("C2/C31 Closure Strap Color")));
        this.styleinformationdata.setFrontEyeletColor(makeColorObjectCache((String)therow.get("Eyelet Color")));
        this.styleinformationdata.setSideBackEyeletColor(makeColorObjectCache((String)therow.get("Eyelet Color")));
        this.styleinformationdata.setButtonColor(makeColorObjectCache((String)therow.get("Button Color")));
        this.styleinformationdata.setInnerTapingColor(makeColorObjectCache((String)therow.get("Inner Taping Color")));
        this.styleinformationdata.setSweatbandColor(makeColorObjectCache((String)therow.get("Sweatband Color")));
        this.styleinformationdata.setStripeColor(makeColorObjectCache((String)therow.get("S2 Sweatband Color")));
        this.styleinformationdata.setS5SweatbandColor(makeColorObjectCache((String)therow.get("S5 Sweatband Color")));
        this.styleinformationdata.setColorCode(makeColorObjectCache((String)therow.get("Cap Color")));
        this.styleinformationdata.setStyleType(StyleInformationData.StyleType.OVERSEAS);
      } else if (this.sqltable.makeTable("SELECT * FROM `styles_pre-designed` WHERE `Style` = '" + stylenumber + "' LIMIT 1").booleanValue()) {
        FastMap<Object> therow = this.sqltable.getRow();
        this.styleinformationdata.setStyleNumber((String)therow.get("Style"));
        this.styleinformationdata.setVendorNumber(Integer.valueOf((String)therow.get("Vendor")).intValue());
        this.styleinformationdata.setLogoLocation(makeLocationObject((String)therow.get("Location")));
        this.styleinformationdata.setCategory((String)therow.get("Category"));
        this.styleinformationdata.setDescription((String)therow.get("Material Description"));
        this.styleinformationdata.setSameStyleNumber((String)therow.get("Style"));
        
        String size = doSizeExpand((String)therow.get("Size"), true);
        

        if ((therow.get("Style").equals("888-A")) || (therow.get("Style").equals("888-B")) || (therow.get("Style").equals("888-C")) || (therow.get("Style").equals("888-D")) || (therow.get("Style").equals("888-E")) || (therow.get("Style").equals("888-F")) || (therow.get("Style").equals("888-G")) || (therow.get("Style").equals("888-H")) || (therow.get("Style").equals("888-J")) || (therow.get("Style").equals("888-L")) || (therow.get("Style").equals("888-M")) || (therow.get("Style").equals("888-N")) || (therow.get("Style").equals("888-O")) || (therow.get("Style").equals("889-A")) || (therow.get("Style").equals("889-B")) || (therow.get("Style").equals("889-C")) || (therow.get("Style").equals("889-D")) || (therow.get("Style").equals("889-E")) || (therow.get("Style").equals("889-F")) || (therow.get("Style").equals("889-G")) || (therow.get("Style").equals("889-H")) || (therow.get("Style").equals("889-J")) || (therow.get("Style").equals("889-L")) || (therow.get("Style").equals("889-M")) || (therow.get("Style").equals("889-N")) || (therow.get("Style").equals("889-O"))) {
          this.styleinformationdata.setClosureStyleNumber(makeClosureObject("C1,C2,C3,C4,C5,C6,C7,C8,C9,C10,C11,C12,C13,C14,C15,C16,C17,C25,C26,C27,C29,C30,C31"));
          this.styleinformationdata.setEyeletStyleNumber(makeEyeletObject("E1,E2,E3,E4,E5,E6,E7,E8,E9,E10,E11,E12,E13"));
          
          if ((therow.get("Style").equals("888-D")) || (therow.get("Style").equals("888-M"))) {
            this.styleinformationdata.setVisorStyleNumber(makeVisorObject("V1,V2,V3,V4,V5,V6,V7,V8,V9,V10,V11,V12"));
          } else if (therow.get("Style").equals("888-C")) {
            this.styleinformationdata.setVisorStyleNumber(makeVisorObject("V1,V2,V3,V4,V5,V6,V7,V8,V9,V10,V11,V12,V13,V14,V15,V16,V17,V18"));
          } else {
            this.styleinformationdata.setVisorStyleNumber(makeVisorObject("V1,V2,V3,V4,V5,V6,V7,V8,V9,V10,V11"));
          }
          this.styleinformationdata.setSweatbandStyleNumber(makeSweatbandObject("S1,S2,S3,S4,S5,S6"));
          this.styleinformationdata.setSize(makeOtherComboObject(doSizeExpand("All", true)));
          this.styleinformationdata.setShowFOBDozen(true);
          this.styleinformationdata.setStyleType(StyleInformationData.StyleType.OVERSEAS_NONOTTO);
        } else if ((therow.get("Style").equals("888-I")) || (therow.get("Style").equals("889-I"))) {
          this.styleinformationdata.setSize(makeOtherComboObject("XS,S,M,L,XL,2XL,3XL,4XL,5XL,6XL"));
          this.styleinformationdata.setStyleType(StyleInformationData.StyleType.OVERSEAS_NONOTTO_SHIRTS);
          this.styleinformationdata.setShowFOBDozen(true);
        } else {
          this.styleinformationdata.setSize(makeOtherComboObject(size));
          this.styleinformationdata.setStyleType(StyleInformationData.StyleType.OVERSEAS_PREDESIGNED);
        }
      } else if (this.sqltable.makeTable("SELECT * FROM `styles_lackpard` WHERE `Style` = '" + stylenumber + "' LIMIT 1").booleanValue()) {
        FastMap<Object> therow = this.sqltable.getRow();
        this.styleinformationdata.setStyleNumber((String)therow.get("Style"));
        this.styleinformationdata.setOrderType(ordertype);
        this.styleinformationdata.setSize(makeOtherComboObject((String)therow.get("Size")));
        this.styleinformationdata.setColorCode(makeColorObjectCache((String)therow.get("colorcodes")));
        this.styleinformationdata.setCategory((String)therow.get("Main Category"));
        this.styleinformationdata.setStyleType(StyleInformationData.StyleType.OVERSEAS_LACKPARD);
        this.styleinformationdata.setVendorNumber(4);
        this.styleinformationdata.setDescription((String)therow.get("Name"));
        this.styleinformationdata.setSameStyleNumber((String)therow.get("Style"));
      }
      else if (this.sqltable.makeTable("SELECT * FROM `styles_overseas_customstyle` WHERE `Style` = '" + stylenumber + "' LIMIT 1").booleanValue()) {
        FastMap<Object> therow = this.sqltable.getRow();
        this.styleinformationdata.setStyleNumber((String)therow.get("Style"));
        this.styleinformationdata.setOrderType(ordertype);
        this.styleinformationdata.setVendorNumber(Integer.valueOf((String)therow.get("Vendor")).intValue());
        this.styleinformationdata.setLogoLocation(makeLocationObject(""));
        this.styleinformationdata.setCategory("");
        
        this.styleinformationdata.setDescription("Custom Style");
        this.styleinformationdata.setSameStyleNumber((String)therow.get("Style"));
        
        this.styleinformationdata.setStyleType(StyleInformationData.StyleType.OVERSEAS_CUSTOMSTYLE);
        
        this.styleinformationdata.setShowFOBDozen(true);
        
        if (this.sqltable.makeTable("SELECT * FROM `styles_overseas_customstyle` WHERE `Style` = '" + stylenumber + "' GROUP BY `Profile`").booleanValue()) {
          ArrayList<FastMap<Object>> theprofiletable = this.sqltable.getTable();
          for (int i = 0; i < theprofiletable.size(); i++) {
            this.styleinformationdata.getAllProfileStore().add(new OtherComboBoxModelData(((String)((FastMap)theprofiletable.get(i)).get("Profile")).trim()));
            
            StyleInformationData styleprofileinfodata = new StyleInformationData();
            
            styleprofileinfodata.setStyleNumber(this.styleinformationdata.getStyleNumber());
            styleprofileinfodata.setOrderType(this.styleinformationdata.getOrderType());
            styleprofileinfodata.setVendorNumber(this.styleinformationdata.getVendorNumber());
            styleprofileinfodata.setCategory(this.styleinformationdata.getCategory());
            styleprofileinfodata.setDescription(this.styleinformationdata.getDescription());
            styleprofileinfodata.setSameStyleNumber(this.styleinformationdata.getSameStyleNumber());
            styleprofileinfodata.setStyleType(this.styleinformationdata.getStyleType());
            styleprofileinfodata.setShowFOBDozen(this.styleinformationdata.getShowFOBDozen());
            
            String sizestring = doSizeExpand((String)((FastMap)theprofiletable.get(i)).get("Size"), true);
            
            styleprofileinfodata.setSize(makeOtherComboObject(sizestring));
            styleprofileinfodata.setSweatbandStyleNumber(makeSweatbandObject(((String)((FastMap)theprofiletable.get(i)).get("Sweatband")).replaceAll("N/A", "")));
            styleprofileinfodata.setVisorStyleNumber(makeVisorObject(((String)((FastMap)theprofiletable.get(i)).get("Visor")).replaceAll("N/A", "")));
            styleprofileinfodata.setClosureStyleNumber(makeClosureObject(((String)((FastMap)theprofiletable.get(i)).get("Closure")).replaceAll("N/A", "")));
            styleprofileinfodata.setEyeletStyleNumber(makeEyeletObject(((String)((FastMap)theprofiletable.get(i)).get("Eyelet")).replaceAll("N/A", "")));
            styleprofileinfodata.setLogoLocation(makeLocationObject((String)((FastMap)theprofiletable.get(i)).get("Location")));
            
            styleprofileinfodata.setNumberOfPanels(makeOtherComboObject(((String)((FastMap)theprofiletable.get(i)).get("No. of Panels")).replaceAll("N/A", "")));
            styleprofileinfodata.setCrownConstruction(makeOtherComboObject(((String)((FastMap)theprofiletable.get(i)).get("Crown Construction")).replaceAll("N/A", "")));
            styleprofileinfodata.setVisorRowStitching(makeOtherComboObject(((String)((FastMap)theprofiletable.get(i)).get("Visor Rows Stiching")).replaceAll("N/A", "")));
            
            if (this.sqltable.makeTable("SELECT * FROM `styles_overseas_customstyle` WHERE `Style` = '" + stylenumber + "' AND `Profile` = '" + (String)((FastMap)theprofiletable.get(i)).get("Profile") + "' GROUP BY `WorkFlow Fabic Name`").booleanValue()) {
              Object thefabrictable = this.sqltable.getTable();
              for (int j = 0; j < ((ArrayList)thefabrictable).size(); j++) {
                styleprofileinfodata.getFrontFabricStore().add(new OtherComboBoxModelData(((String)((FastMap)((ArrayList)thefabrictable).get(j)).get("WorkFlow Fabic Name")).trim()));
                
                if (((String)((FastMap)((ArrayList)thefabrictable).get(j)).get("Single Color")).replaceAll("N/A", "").trim().equals("")) {
                  styleprofileinfodata.getBackFabricStore().add(new OtherComboBoxModelData(((String)((FastMap)((ArrayList)thefabrictable).get(j)).get("WorkFlow Fabic Name")).trim()));
                  styleprofileinfodata.getSideFabricStore().add(new OtherComboBoxModelData(((String)((FastMap)((ArrayList)thefabrictable).get(j)).get("WorkFlow Fabic Name")).trim()));
                }
                
                StyleInformationData stylefabricinfodata = new StyleInformationData();
                
                stylefabricinfodata.setColorCode(makeColorObjectCache(((String)((FastMap)((ArrayList)thefabrictable).get(j)).get("Single Color")).replaceAll("N/A", "")));
                stylefabricinfodata.setFrontPanelColor(makeColorObjectCache(((String)((FastMap)((ArrayList)thefabrictable).get(j)).get("Front Panel Color")).replaceAll("N/A", "")));
                stylefabricinfodata.setBackPanelColor(makeColorObjectCache(((String)((FastMap)((ArrayList)thefabrictable).get(j)).get("Side & Back Panel Color")).replaceAll("N/A", "")));
                stylefabricinfodata.setSidePanelColor(makeColorObjectCache(((String)((FastMap)((ArrayList)thefabrictable).get(j)).get("Side & Back Panel Color")).replaceAll("N/A", "")));
                stylefabricinfodata.setPrimaryVisorColor(makeColorObjectCache(((String)((FastMap)((ArrayList)thefabrictable).get(j)).get("Primary Visor Color")).replaceAll("N/A", "")));
                stylefabricinfodata.setVisorTrimEdgeColor(makeColorObjectCache(((String)((FastMap)((ArrayList)thefabrictable).get(j)).get("Visor Trim/Edge Color")).replaceAll("N/A", "")));
                stylefabricinfodata.setVisorSandwichColor(makeColorObjectCache(((String)((FastMap)((ArrayList)thefabrictable).get(j)).get("Visor Sandwich Color")).replaceAll("N/A", "")));
                stylefabricinfodata.setUndervisorColor(makeColorObjectCache(((String)((FastMap)((ArrayList)thefabrictable).get(j)).get("Undervisor Color")).replaceAll("N/A", "")));
                stylefabricinfodata.setDistressedVisorInsideColor(makeColorObjectCache(((String)((FastMap)((ArrayList)thefabrictable).get(j)).get("Distressed Visor Inside Color")).replaceAll("N/A", "")));
                stylefabricinfodata.setClosureStrapColor(makeColorObjectCache(((String)((FastMap)((ArrayList)thefabrictable).get(j)).get("Closure Strap Color")).replaceAll("N/A", "")));
                stylefabricinfodata.setC2C31ClosureStrapColor(makeColorObjectCache(((String)((FastMap)((ArrayList)thefabrictable).get(j)).get("C2/C31 Closure Strap Color")).replaceAll("N/A", "")));
                stylefabricinfodata.setFrontEyeletColor(makeColorObjectCache(((String)((FastMap)((ArrayList)thefabrictable).get(j)).get("E1 Eyelet Color")).replaceAll("N/A", "")));
                if (!stylenumber.contains("111-")) {
                  stylefabricinfodata.setSideEyeletColor(makeColorObjectCache(((String)((FastMap)((ArrayList)thefabrictable).get(j)).get("E1 Eyelet Color")).replaceAll("N/A", "")));
                  stylefabricinfodata.setBackEyeletColor(makeColorObjectCache(((String)((FastMap)((ArrayList)thefabrictable).get(j)).get("E1 Eyelet Color")).replaceAll("N/A", "")));
                } else {
                  stylefabricinfodata.setSideBackEyeletColor(makeColorObjectCache(((String)((FastMap)((ArrayList)thefabrictable).get(j)).get("E1 Eyelet Color")).replaceAll("N/A", "")));
                }
                stylefabricinfodata.setButtonColor(makeColorObjectCache(((String)((FastMap)((ArrayList)thefabrictable).get(j)).get("Button Color")).replaceAll("N/A", "")));
                stylefabricinfodata.setInnerTapingColor(makeColorObjectCache(((String)((FastMap)((ArrayList)thefabrictable).get(j)).get("Inner Taping Color")).replaceAll("N/A", "")));
                stylefabricinfodata.setSweatbandColor(makeColorObjectCache(((String)((FastMap)((ArrayList)thefabrictable).get(j)).get("Sweatband Color")).replaceAll("N/A", "")));
                stylefabricinfodata.setStripeColor(makeColorObjectCache(((String)((FastMap)((ArrayList)thefabrictable).get(j)).get("S2 Sweatband Color")).replaceAll("N/A", "")));
                stylefabricinfodata.setS5SweatbandColor(makeColorObjectCache(((String)((FastMap)((ArrayList)thefabrictable).get(j)).get("S5/S6 Sweatband Color")).replaceAll("N/A", "")));
                
                stylefabricinfodata.setPanelStitchColor(makeColorObjectCache(((String)((FastMap)((ArrayList)thefabrictable).get(j)).get("Panel Stitch Color")).replaceAll("N/A", "")));
                stylefabricinfodata.setVisorStitchColor(makeColorObjectCache(((String)((FastMap)((ArrayList)thefabrictable).get(j)).get("Visor Stitch Color")).replaceAll("N/A", "")));
                



                styleprofileinfodata.addAllFabric(((String)((FastMap)((ArrayList)thefabrictable).get(j)).get("WorkFlow Fabic Name")).trim(), stylefabricinfodata);
              }
            }
            

            this.styleinformationdata.addAllProfile(((String)((FastMap)theprofiletable.get(i)).get("Profile")).trim(), styleprofileinfodata);
          }
        }
      }
      else
      {
        System.out.println(stylenumber + " Not Found");
      }
    }
    

    this.sqltable.closeSQL();
  }
  
  private String doSizeExpand(String sizes, boolean overseas) {
    sizes.trim();
    
    String[] allsizes;
    if (overseas) {
      if ((sizes.equals("")) || (sizes.equals("Standard"))) {
        sizes = "One Size Fits Most, Teen, Youth, Children, Otto Flex, Otto Fit";
      } else if (sizes.equals("Flex")) {
        sizes = "Otto Flex";
      } else if (sizes.equals("All")) {
        sizes = "One Size Fits Most, Teen, Youth, Children, Otto Flex, Otto Fit";
      }
      
      String[] allsizes = sizes.split(",");
      
      for (int i = 0; i < allsizes.length; i++) {
        allsizes[i] = allsizes[i].trim();
        
        if (allsizes[i].equals("One Size Fits Most")) {
          allsizes[i] = "One Size Fits Most - 58cm, One Size Fits Most - 57cm, One Size Fits Most - 56cm, One Size Fits Most";
        } else if (allsizes[i].equals("Youth")) {
          allsizes[i] = "Youth - 54cm, Youth - 56cm, Youth";
        } else if (allsizes[i].equals("Otto Flex")) {
          allsizes[i] = "Otto Flex - S/M, Otto Flex - L/XL, SM, LXL";
        } else if (allsizes[i].equals("Otto Fit")) {
          allsizes[i] = "Otto Fit - 6 7/8, Otto Fit - 7, Otto Fit - 7 1/8, Otto Fit - 7 1/4, Otto Fit - 7 3/8, Otto Fit - 7 1/2, Otto Fit - 7 5/8, Otto Fit - 7 3/4, Otto Fit - 7 7/8, 6 7/8, 7, 7 1/8, 7 1/4, 7 3/8, 7 1/2, 7 5/8, 7 3/4, 7 7/8";
        }
      }
    }
    else {
      if ((sizes.equals("")) || (sizes.equals("Standard"))) {
        sizes = "One Size Fits Most, Teen, Youth, Children";
      } else if (sizes.equals("Flex")) {
        sizes = "Otto Flex";
      } else if (sizes.equals("All")) {
        sizes = "One Size Fits Most, Teen, Youth, Children, Otto Flex, Otto Fit";
      }
      
      allsizes = sizes.split(",");
    }
    

    String newsize = "";
    for (int i = 0; i < allsizes.length; i++) {
      newsize = newsize + allsizes[i];
      if (i + 1 != allsizes.length) {
        newsize = newsize + ",";
      }
    }
    sizes = newsize;
    
    return sizes;
  }
  
  private ArrayList<OtherComboBoxModelData> makeDomesticLocationObject(FastMap<Object> therow) {
    ArrayList<String> thelocations = trimStringList((String)therow.get("location"));
    ArrayList<String> thelocationsheat = trimStringList((String)therow.get("locationheat"));
    ArrayList<String> thelocationsemb = trimStringList((String)therow.get("locationemb"));
    ArrayList<String> thelocationsdtg = trimStringList((String)therow.get("locationdtg"));
    ArrayList<String> thelocationsscreenprint;
    ArrayList<String> thelocationscadprint;
    ArrayList<String> thelocationsscreenprint; if (SharedData.isOnline.booleanValue()) {
      ArrayList<String> thelocationscadprint = trimStringList("");
      thelocationsscreenprint = trimStringList("");
    } else {
      thelocationscadprint = trimStringList((String)therow.get("locationcadprint"));
      thelocationsscreenprint = trimStringList((String)therow.get("locationscreenprint"));
    }
    
    ArrayList<OtherComboBoxModelData> thelist = new ArrayList();
    for (String currentlocation : thelocations) {
      OtherComboBoxModelData mylocation = new OtherComboBoxModelData(getLocationLabel(currentlocation), currentlocation);
      mylocation.set("heat", Boolean.valueOf(thelocationsheat.contains(currentlocation)));
      mylocation.set("emb", Boolean.valueOf(thelocationsemb.contains(currentlocation)));
      mylocation.set("dtg", Boolean.valueOf(thelocationsdtg.contains(currentlocation)));
      mylocation.set("cadprint", Boolean.valueOf(thelocationscadprint.contains(currentlocation)));
      mylocation.set("screenprint", Boolean.valueOf(thelocationsscreenprint.contains(currentlocation)));
      thelist.add(mylocation);
    }
    
    return thelist;
  }
  
  private ArrayList<OtherComboBoxModelData> makeDomesticNONOTTOLocationObject(String styletype) {
    ArrayList<String> thelocationsscreenprint;
    ArrayList<String> thelocations;
    ArrayList<String> thelocationsheat;
    ArrayList<String> thelocationsemb;
    ArrayList<String> thelocationsdtg;
    ArrayList<String> thelocationscadprint;
    ArrayList<String> thelocationsscreenprint;
    if (styletype.equals("nonotto")) {
      ArrayList<String> thelocations = trimStringList("1,2,3,4,8,9,18,19,42");
      ArrayList<String> thelocationsheat = trimStringList("2,3,8,9,18,19");
      ArrayList<String> thelocationsemb = trimStringList("1,2,3,8,9,18,19");
      ArrayList<String> thelocationsdtg = trimStringList("1,2,3,4,18,19,42");
      ArrayList<String> thelocationscadprint = trimStringList("2,3,8,9,18,19");
      thelocationsscreenprint = trimStringList("");
    } else {
      thelocations = trimStringList("1,8,14,18,19,A1,A2,A4,A5,A6,A7,A8,A9,A10,A11,A12,A13,A14,A15,A16");
      thelocationsheat = trimStringList("1,8,14,18,19,A1,A2,A4,A5,A6,A7,A8,A9,A10,A11,A12,A13,A14,A15,A16");
      thelocationsemb = trimStringList("1,8,14,18,19,A1,A2,A4,A5,A6,A7,A8,A9,A10,A11,A12,A13,A14,A15,A16");
      thelocationsdtg = trimStringList("1,8,14,18,19,A1,A2,A4,A5,A6,A9,A10,A11,A12,A13,A14,A15,A16");
      thelocationscadprint = trimStringList("1,8,14,18,19,A1,A2,A4,A5,A6,A7,A8,A9,A10,A11,A12,A13,A14,A15,A16");
      thelocationsscreenprint = trimStringList("1,8,14,18,19,A1,A2,A4,A5,A6,A7,A8,A9,A10,A11,A12,A13,A14,A15,A16");
    }
    
    ArrayList<OtherComboBoxModelData> thelist = new ArrayList();
    for (String currentlocation : thelocations) {
      OtherComboBoxModelData mylocation = new OtherComboBoxModelData(getLocationLabel(currentlocation), currentlocation);
      mylocation.set("heat", Boolean.valueOf(thelocationsheat.contains(currentlocation)));
      mylocation.set("emb", Boolean.valueOf(thelocationsemb.contains(currentlocation)));
      mylocation.set("dtg", Boolean.valueOf(thelocationsdtg.contains(currentlocation)));
      mylocation.set("cadprint", Boolean.valueOf(thelocationscadprint.contains(currentlocation)));
      mylocation.set("screenprint", Boolean.valueOf(thelocationsscreenprint.contains(currentlocation)));
      thelist.add(mylocation);
    }
    
    return thelist;
  }
  
  private ArrayList<String> trimStringList(String thestring) {
    ArrayList<String> mylist = new ArrayList();
    String[] locationarray = thestring.split(",");
    for (int i = 0; i < locationarray.length; i++) {
      if (!locationarray[i].trim().equals("")) {
        mylist.add(locationarray[i].trim());
      }
    }
    return mylist;
  }
  
  private ArrayList<OtherComboBoxModelData> makeLocationObject(String commalist) {
    String[] thearray = commalist.split(",");
    ArrayList<OtherComboBoxModelData> thelist = new ArrayList();
    for (int i = 0; i < thearray.length; i++) {
      if (!thearray[i].trim().equals("")) {
        OtherComboBoxModelData mycolor = new OtherComboBoxModelData(getLocationLabel(thearray[i].trim()), thearray[i].trim());
        thelist.add(mycolor);
      }
    }
    return thelist;
  }
  
  private ArrayList<OtherComboBoxModelData> makeSweatbandObject(String commalist) {
    String[] thearray = commalist.split(",");
    ArrayList<OtherComboBoxModelData> thelist = new ArrayList();
    for (int i = 0; i < thearray.length; i++) {
      if (!thearray[i].trim().equals("")) {
        OtherComboBoxModelData mycolor = new OtherComboBoxModelData(getSweatbandStyleLabel(thearray[i].trim()), thearray[i].trim());
        thelist.add(mycolor);
      }
    }
    return thelist;
  }
  
  private ArrayList<OtherComboBoxModelData> makeVisorObject(String commalist) {
    String[] thearray = commalist.split(",");
    ArrayList<OtherComboBoxModelData> thelist = new ArrayList();
    for (int i = 0; i < thearray.length; i++) {
      if (!thearray[i].trim().equals("")) {
        OtherComboBoxModelData mycolor = new OtherComboBoxModelData(getVisorStyleLabel(thearray[i].trim()), thearray[i].trim());
        thelist.add(mycolor);
      }
    }
    return thelist;
  }
  
  private ArrayList<OtherComboBoxModelData> makeClosureObject(String commalist) {
    String[] thearray = commalist.split(",");
    ArrayList<OtherComboBoxModelData> thelist = new ArrayList();
    for (int i = 0; i < thearray.length; i++) {
      if (!thearray[i].trim().equals("")) {
        OtherComboBoxModelData mycolor = new OtherComboBoxModelData(getClosureStyleLabel(thearray[i].trim()), thearray[i].trim());
        thelist.add(mycolor);
      }
    }
    return thelist;
  }
  
  private ArrayList<OtherComboBoxModelData> makeClosureObject(String filterout, String commalist) {
    String[] thearray = commalist.split(",");
    ArrayList<OtherComboBoxModelData> thelist = new ArrayList();
    for (int i = 0; i < thearray.length; i++) {
      if ((!thearray[i].trim().equals("")) && 
        (!thearray[i].trim().equals(filterout))) {
        OtherComboBoxModelData mycolor = new OtherComboBoxModelData(getClosureStyleLabel(thearray[i].trim()), thearray[i].trim());
        thelist.add(mycolor);
      }
    }
    
    return thelist;
  }
  
  private ArrayList<OtherComboBoxModelData> makeEyeletObject(String commalist) {
    String[] thearray = commalist.split(",");
    ArrayList<OtherComboBoxModelData> thelist = new ArrayList();
    for (int i = 0; i < thearray.length; i++) {
      if (!thearray[i].trim().equals("")) {
        OtherComboBoxModelData mycolor = new OtherComboBoxModelData(getEyeletStyleLabel(thearray[i].trim()), thearray[i].trim());
        thelist.add(mycolor);
      }
    }
    return thelist;
  }
  
  private ArrayList<OtherComboBoxModelData> makeEyeletObject(String filterout, String commalist) {
    String[] thearray = commalist.split(",");
    ArrayList<OtherComboBoxModelData> thelist = new ArrayList();
    for (int i = 0; i < thearray.length; i++) {
      if ((!thearray[i].trim().equals("")) && 
        (!thearray[i].trim().equals(filterout))) {
        OtherComboBoxModelData mycolor = new OtherComboBoxModelData(getEyeletStyleLabel(thearray[i].trim()), thearray[i].trim());
        thelist.add(mycolor);
      }
    }
    
    return thelist;
  }
  
  private ArrayList<OtherComboBoxModelData> makeOtherComboObject(String commalist) {
    String[] thearray = commalist.split(",");
    ArrayList<OtherComboBoxModelData> thelist = new ArrayList();
    for (int i = 0; i < thearray.length; i++) {
      if (!thearray[i].trim().equals("")) {
        OtherComboBoxModelData mycolor = new OtherComboBoxModelData(thearray[i].trim());
        thelist.add(mycolor);
      }
    }
    return thelist;
  }
  
  private ArrayList<OtherComboBoxModelData> makeColorObjectCache(String colorcodes) {
    if (this.theottocodetocolorcache == null) {
      this.theottocodetocolorcache = new OTTOCodeToColorCached(this.sqltable);
    }
    
    String[] colorcodesarray = colorcodes.split(",");
    ArrayList<OtherComboBoxModelData> colorcode = new ArrayList();
    for (int i = 0; i < colorcodesarray.length; i++) {
      if (!colorcodesarray[i].trim().equals("")) {
        try {
          OtherComboBoxModelData mycolor = new OtherComboBoxModelData(colorcodesarray[i].trim() + this.theottocodetocolorcache.getDesc(colorcodesarray[i].trim()), colorcodesarray[i].trim());
          colorcode.add(mycolor);
        } catch (Exception e) {
          System.out.println("Not Otto Color: " + colorcodesarray[i].trim());
          OtherComboBoxModelData mycolor = new OtherComboBoxModelData(colorcodesarray[i].trim());
          colorcode.add(mycolor);
        }
      }
    }
    return colorcode;
  }
  
  private ArrayList<OtherComboBoxModelData> makeColorObject(String colorcodes)
  {
    String[] colorcodesarray = colorcodes.split(",");
    ArrayList<OtherComboBoxModelData> colorcode = new ArrayList();
    for (int i = 0; i < colorcodesarray.length; i++) {
      if (!colorcodesarray[i].trim().equals("")) {
        OtherComboBoxModelData mycolor = new OtherComboBoxModelData(colorcodesarray[i].trim() + com.ottocap.NewWorkFlow.server.OTTOCodeToColor.getStaticCodeDisc(colorcodesarray[i].trim(), this.sqltable), colorcodesarray[i].trim());
        colorcode.add(mycolor);
      }
    }
    return colorcode;
  }
  
  private String getClosureStyleLabel(String closurestylenumber) {
    if (this.sqltable.makeTable("SELECT `name` FROM `overseas_price_styles_closure` WHERE `style` = '" + closurestylenumber + "' LIMIT 1").booleanValue()) {
      return closurestylenumber + " - " + (String)this.sqltable.getValue();
    }
    return closurestylenumber;
  }
  
  private String getEyeletStyleLabel(String eyeletstylenumber)
  {
    if (this.sqltable.makeTable("SELECT `name` FROM `overseas_price_styles_eyelet` WHERE `style` = '" + eyeletstylenumber + "' LIMIT 1").booleanValue()) {
      return eyeletstylenumber + " - " + (String)this.sqltable.getValue();
    }
    return eyeletstylenumber;
  }
  
  private String getSweatbandStyleLabel(String sweatbandstylenumber)
  {
    if (this.sqltable.makeTable("SELECT `name` FROM `overseas_price_styles_sweatband` WHERE `style` = '" + sweatbandstylenumber + "' LIMIT 1").booleanValue()) {
      return sweatbandstylenumber + " - " + (String)this.sqltable.getValue();
    }
    return sweatbandstylenumber;
  }
  
  private String getVisorStyleLabel(String visorstylenumber)
  {
    if (this.sqltable.makeTable("SELECT `name` FROM `overseas_price_styles_visor` WHERE `style` = '" + visorstylenumber + "' LIMIT 1").booleanValue()) {
      return visorstylenumber + " - " + (String)this.sqltable.getValue();
    }
    return visorstylenumber;
  }
  
  private String getLocationLabel(String locationnumber)
  {
    if (this.sqltable.makeTable("SELECT `name` FROM `list_decorations_locations` WHERE `id` = '" + locationnumber + "' LIMIT 1").booleanValue()) {
      if ((locationnumber.equals("1")) || (locationnumber.equals("2")) || (locationnumber.equals("3")) || (locationnumber.equals("4")) || (locationnumber.equals("5")) || (locationnumber.equals("6")) || (locationnumber.equals("7")) || (locationnumber.equals("8")) || (locationnumber.equals("9"))) {
        return "0" + locationnumber + " - " + (String)this.sqltable.getValue();
      }
      return locationnumber + " - " + (String)this.sqltable.getValue();
    }
    
    return locationnumber;
  }
  
  public StyleInformationData getStyleInformationData()
  {
    return this.styleinformationdata;
  }
}
