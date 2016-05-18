package com.ottocap.NewWorkFlow.server.RPCService;

import com.extjs.gxt.ui.client.core.FastMap;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData.OrderType;
import com.ottocap.NewWorkFlow.client.StyleInformationData;
import com.ottocap.NewWorkFlow.client.StyleInformationData.StyleType;
import com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBoxModelData;
import com.ottocap.NewWorkFlow.server.JOOQSQL;
import java.util.ArrayList;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.jooq.SelectJoinStep;
import org.jooq.SelectOffsetStep;
import org.jooq.SelectSelectStep;
import org.jooq.SelectWhereStep;
import org.jooq.TableLike;
import org.jooq.impl.DSL;

public class JOOQ_GetStyleInformationData
{
  private StyleInformationData styleinformationdata = new StyleInformationData();
  private com.ottocap.NewWorkFlow.server.JOOQ_OTTOCodeToColorCached theottocodetocolorcache = null;
  

  public JOOQ_GetStyleInformationData(String stylenumber, OrderData.OrderType ordertype)
  {
    if (ordertype == OrderData.OrderType.DOMESTIC) { Record sqlrecord;
      if ((sqlrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_domestic" })).where(new Condition[] { DSL.fieldByName(new String[] { "sku" }).equal(stylenumber) }).limit(1).fetchOne()) != null) {
        this.styleinformationdata.setStyleNumber((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "sku" })));
        this.styleinformationdata.setOrderType(ordertype);
        this.styleinformationdata.setCategory((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "category" })));
        this.styleinformationdata.setSameStyleNumber((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "samestyle" })));
        
        String sizes = doSizeExpand((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "size" })), false);
        
        this.styleinformationdata.setSize(makeOtherComboObject(sizes));
        this.styleinformationdata.setColorCode(makeColorObjectCache((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "colorcodes" }))));
        
        String newsizeoption = ((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "new_size_option" }))).trim();
        if (!newsizeoption.equals(""))
        {
          this.styleinformationdata.setUseSizeColor(true);
          
          FastMap<ArrayList<OtherComboBoxModelData>> mysizecolor = new FastMap();
          for (OtherComboBoxModelData currentsize : this.styleinformationdata.getSize()) {
            String size = currentsize.getValue();
            Record sqlcolorcoderecord;
            if ((sqlcolorcoderecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "colorcodes" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_domestic" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "sku" }).equal(stylenumber) }).and(DSL.fieldByName(new String[] { "new_size_option" }).equal(size)).limit(1).fetchOne()) != null) {
              mysizecolor.put(size, makeColorObjectCache((String)sqlcolorcoderecord.getValue(DSL.fieldByName(new String[] { "colorcodes" }))));
            } else if ((sqlcolorcoderecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "colorcodes" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_domestic" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "sku" }).equal(stylenumber) }).limit(1).fetchOne()) != null) {
              mysizecolor.put(size, makeColorObjectCache((String)sqlcolorcoderecord.getValue(DSL.fieldByName(new String[] { "colorcodes" }))));
            }
          }
          
          this.styleinformationdata.setSizeColor(mysizecolor);
        }
        
        if (!((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "size" }))).trim().equals("")) {
          this.styleinformationdata.setDescription((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "productname" })) + " (" + (String)sqlrecord.getValue(DSL.fieldByName(new String[] { "size" })) + ")");
        } else {
          this.styleinformationdata.setDescription((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "productname" })));
        }
        
        this.styleinformationdata.setLogoLocation(makeDomesticLocationObject(sqlrecord));
        this.styleinformationdata.setStyleType(StyleInformationData.StyleType.DOMESTIC); } else { boolean havemultiplesize;
        if ((sqlrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_domestic_specials" })).where(new Condition[] { DSL.fieldByName(new String[] { "style" }).equal(stylenumber) }).orderBy(new org.jooq.SortField[] { DSL.fieldByName(new String[] { "endingdate" }).desc() }).limit(1).fetchOne()) != null) {
          this.styleinformationdata.setStyleNumber((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "style" })));
          this.styleinformationdata.setOrderType(ordertype);
          String specialcolorcode = (String)sqlrecord.getValue(DSL.fieldByName(new String[] { "colorcodes" }));
          this.styleinformationdata.setColorCode(makeColorObjectCache(specialcolorcode));
          this.styleinformationdata.setStyleType(StyleInformationData.StyleType.DOMESTIC_SPECIAL);
          
          boolean havecolorsize = !((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "size" }))).trim().equals("");
          havemultiplesize = false;
          
          String basestyle = (String)sqlrecord.getValue(DSL.fieldByName(new String[] { "basestyle" }));
          if ((sqlrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_domestic" })).where(new Condition[] { DSL.fieldByName(new String[] { "sku" }).equal(basestyle) }).limit(1).fetchOne()) != null) {
            String sizes = doSizeExpand((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "size" })), false);
            
            this.styleinformationdata.setCategory((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "category" })));
            this.styleinformationdata.setSize(makeOtherComboObject(sizes));
            this.styleinformationdata.setLogoLocation(makeDomesticLocationObject(sqlrecord));
            this.styleinformationdata.setSameStyleNumber((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "samestyle" })));
            
            String newsizeoption = ((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "new_size_option" }))).trim();
            if (!newsizeoption.equals("")) {
              havemultiplesize = true;
              this.styleinformationdata.setUseSizeColor(true);
              
              FastMap<ArrayList<OtherComboBoxModelData>> mysizecolor = new FastMap();
              for (OtherComboBoxModelData currentsize : this.styleinformationdata.getSize()) {
                String size = currentsize.getValue();
                mysizecolor.put(size, makeColorObjectCache(specialcolorcode));
              }
              
              this.styleinformationdata.setSizeColor(mysizecolor);
            }
            
            if (!((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "size" }))).trim().equals("")) {
              this.styleinformationdata.setDescription((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "productname" })) + " (" + (String)sqlrecord.getValue(DSL.fieldByName(new String[] { "size" })) + ")");
            } else {
              this.styleinformationdata.setDescription((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "productname" })));
            }
          }
          else if ((sqlrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_lackpard" })).where(new Condition[] { DSL.fieldByName(new String[] { "Style" }).equal(basestyle) }).limit(1).fetchOne()) != null) {
            this.styleinformationdata.setSize(makeOtherComboObject((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Size" }))));
            this.styleinformationdata.setCategory((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Main Category" })));
            
            this.styleinformationdata.setDescription((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Name" })));
            this.styleinformationdata.setSameStyleNumber((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Style" })));
          }
          

          if ((havecolorsize) && (havemultiplesize)) {
            this.styleinformationdata.setUseSizeColor(true);
            
            Object mysizecolor = new FastMap();
            
            org.jooq.Table<org.jooq.Record3<Object, Object, Object>> records = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "style" }), DSL.fieldByName(new String[] { "size" }), DSL.fieldByName(new String[] { "colorcodes" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_domestic_specials" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "style" }).equal(stylenumber) }).orderBy(new org.jooq.SortField[] { DSL.fieldByName(new String[] { "endingdate" }).desc() }).asTable("t1");
            
            Result<org.jooq.Record3<Object, Object, Object>> sqlrecords;
            if ((sqlrecords = JOOQSQL.getInstance().create().selectFrom(records).groupBy(new org.jooq.GroupField[] { DSL.fieldByName(new String[] { "size" }) }).fetch()) != null) {
              for (Record currenttable : sqlrecords) {
                ((FastMap)mysizecolor).put((String)currenttable.getValue(DSL.fieldByName(new String[] { "size" })), makeColorObjectCache((String)currenttable.getValue(DSL.fieldByName(new String[] { "colorcodes" }))));
              }
            }
            
            this.styleinformationdata.setSizeColor((FastMap)mysizecolor);
          }
        } else {
          String size;
          if ((sqlrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_domestic_shirts" })).where(new Condition[] { DSL.fieldByName(new String[] { "sku" }).equal(stylenumber) }).limit(1).fetchOne()) != null) {
            this.styleinformationdata.setStyleNumber((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "sku" })));
            this.styleinformationdata.setOrderType(ordertype);
            this.styleinformationdata.setUseSizeColor(true);
            this.styleinformationdata.setSize(makeOtherComboObject((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "size" }))));
            this.styleinformationdata.setCategory((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "category" })));
            
            FastMap<ArrayList<OtherComboBoxModelData>> mysizecolor = new FastMap();
            for (OtherComboBoxModelData currentsize : this.styleinformationdata.getSize()) {
              size = currentsize.getValue();
              Record sqlcolorrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "color" })).from("styles_domestic_shirts_sizecolor").where(new Condition[] { DSL.fieldByName(new String[] { "sku" }).equal(stylenumber) }).and(DSL.fieldByName(new String[] { "size" }).equal(size)).limit(1).fetchOne();
              if (sqlcolorrecord != null) {
                mysizecolor.put(size, makeColorObjectCache((String)sqlcolorrecord.getValue(DSL.fieldByName(new String[] { "color" }))));
              }
            }
            
            this.styleinformationdata.setSizeColor(mysizecolor);
            
            this.styleinformationdata.setLogoLocation(makeDomesticLocationObject(sqlrecord));
            this.styleinformationdata.setStyleType(StyleInformationData.StyleType.DOMESTIC_SHIRTS);
            this.styleinformationdata.setDescription((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "productname" })));
            this.styleinformationdata.setSameStyleNumber((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "samestyle" })));
          } else if ((sqlrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_domestic_sweatshirts" })).where(new Condition[] { DSL.fieldByName(new String[] { "sku" }).equal(stylenumber) }).limit(1).fetchOne()) != null) {
            this.styleinformationdata.setStyleNumber((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "sku" })));
            this.styleinformationdata.setOrderType(ordertype);
            this.styleinformationdata.setCategory((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "category" })));
            
            this.styleinformationdata.setSize(makeOtherComboObject((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "size" }))));
            this.styleinformationdata.setColorCode(makeColorObjectCache((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "colorcodes" }))));
            
            String newsizeoption = ((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "new_size_option" }))).trim();
            if (!newsizeoption.equals(""))
            {
              this.styleinformationdata.setUseSizeColor(true);
              
              FastMap<ArrayList<OtherComboBoxModelData>> mysizecolor = new FastMap();
              for (OtherComboBoxModelData currentsize : this.styleinformationdata.getSize()) {
                String size = currentsize.getValue();
                Record sqlcolorrecord;
                if ((sqlcolorrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "colorcodes" })).from("styles_domestic_sweatshirts").where(new Condition[] { DSL.fieldByName(new String[] { "sku" }).equal(stylenumber) }).and(DSL.fieldByName(new String[] { "new_size_option" }).equal(size)).limit(1).fetchOne()) != null) {
                  mysizecolor.put(size, makeColorObjectCache((String)sqlcolorrecord.getValue(DSL.fieldByName(new String[] { "colorcodes" }))));
                } else if ((sqlcolorrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "colorcodes" })).from("styles_domestic_sweatshirts").where(new Condition[] { DSL.fieldByName(new String[] { "sku" }).equal(stylenumber) }).limit(1).fetchOne()) != null) {
                  mysizecolor.put(size, makeColorObjectCache((String)sqlcolorrecord.getValue(DSL.fieldByName(new String[] { "colorcodes" }))));
                }
              }
              
              this.styleinformationdata.setSizeColor(mysizecolor);
            }
            
            this.styleinformationdata.setLogoLocation(makeDomesticLocationObject(sqlrecord));
            this.styleinformationdata.setStyleType(StyleInformationData.StyleType.DOMESTIC_SHIRTS);
            this.styleinformationdata.setDescription((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "productname" })));
            this.styleinformationdata.setSameStyleNumber((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "samestyle" })));
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
              if (com.ottocap.NewWorkFlow.server.SharedData.isFaya.booleanValue()) {
                this.styleinformationdata.setDescription("Non FAYA Regular");
              } else {
                this.styleinformationdata.setDescription("Non OTTO Regular");
              }
            } else {
              this.styleinformationdata.setSize(makeOtherComboObject("XS,S,M,L,XL,2XL,3XL,4XL,5XL,6XL"));
              this.styleinformationdata.setLogoLocation(makeDomesticNONOTTOLocationObject("nonottoflat"));
              if (com.ottocap.NewWorkFlow.server.SharedData.isFaya.booleanValue()) {
                this.styleinformationdata.setDescription("Non FAYA Flat");
              } else {
                this.styleinformationdata.setDescription("Non OTTO Flat");
              }
            }
          } else if ((sqlrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_lackpard" })).where(new Condition[] { DSL.fieldByName(new String[] { "Style" }).equal(stylenumber) }).limit(1).fetchOne()) != null) {
            this.styleinformationdata.setStyleNumber((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Style" })));
            this.styleinformationdata.setOrderType(ordertype);
            this.styleinformationdata.setSize(makeOtherComboObject((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Size" }))));
            this.styleinformationdata.setColorCode(makeColorObjectCache((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "colorcodes" }))));
            this.styleinformationdata.setCategory((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Main Category" })));
            this.styleinformationdata.setStyleType(StyleInformationData.StyleType.DOMESTIC_LACKPARD);
            this.styleinformationdata.setDescription((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Name" })));
            this.styleinformationdata.setSameStyleNumber(stylenumber);
          }
          else if ((sqlrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_domestic_totebags" })).where(new Condition[] { DSL.fieldByName(new String[] { "sku" }).equal(stylenumber) }).limit(1).fetchOne()) != null) {
            this.styleinformationdata.setStyleNumber((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "sku" })));
            this.styleinformationdata.setOrderType(ordertype);
            this.styleinformationdata.setCategory((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "category" })));
            this.styleinformationdata.setSameStyleNumber((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "samestyle" })));
            this.styleinformationdata.setColorCode(makeColorObjectCache((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "colorcodes" }))));
            this.styleinformationdata.setDescription((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "productname" })));
            this.styleinformationdata.setLogoLocation(makeDomesticLocationObject(sqlrecord));
            this.styleinformationdata.setStyleType(StyleInformationData.StyleType.DOMESTIC_TOTEBAGS);
          }
          else if ((sqlrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_domestic_aprons" })).where(new Condition[] { DSL.fieldByName(new String[] { "sku" }).equal(stylenumber) }).limit(1).fetchOne()) != null) {
            this.styleinformationdata.setStyleNumber((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "sku" })));
            this.styleinformationdata.setOrderType(ordertype);
            this.styleinformationdata.setCategory((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "category" })));
            this.styleinformationdata.setSameStyleNumber((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "samestyle" })));
            this.styleinformationdata.setColorCode(makeColorObjectCache((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "colorcodes" }))));
            this.styleinformationdata.setDescription((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "productname" })));
            this.styleinformationdata.setLogoLocation(makeDomesticLocationObject(sqlrecord));
            this.styleinformationdata.setStyleType(StyleInformationData.StyleType.DOMESTIC_APRONS);
          }
          else {
            System.out.println(stylenumber + " Not Found");
          }
        } } } else if (ordertype == OrderData.OrderType.OVERSEAS) { Record sqlrecord;
      Object closures;
      if ((sqlrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "Primary VENDOR" })).from(new TableLike[] { DSL.tableByName(new String[] { "styles_overseasinstock" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "STYLE NUMBER" }).equal(stylenumber) }).limit(1).fetchOne()) != null) {
        String thevendor = (String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Primary VENDOR" }));
        if ((sqlrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_overseasinstock" })).where(new Condition[] { DSL.fieldByName(new String[] { "STYLE NUMBER" }).equal(stylenumber) }).and(DSL.fieldByName(new String[] { "Backup VENDOR" }).equal(thevendor)).limit(1).fetchOne()) != null) {
          this.styleinformationdata.setStyleNumber((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "STYLE NUMBER" })));
          this.styleinformationdata.setOrderType(ordertype);
          this.styleinformationdata.setVendorNumber(Integer.valueOf((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Backup VENDOR" }))).intValue());
          this.styleinformationdata.setLogoLocation(makeLocationObject((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Location" }))));
          this.styleinformationdata.setCategory((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Category" })));
          
          this.styleinformationdata.setDescription(sqlrecord.getValue(DSL.fieldByName(tmp4451_4448)) != null ? (String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Emb Tooling Name" })) : "");
          this.styleinformationdata.setSameStyleNumber((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Same Style" })));
          
          String orgclosure = ((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Original Closure" }))).trim();
          String orgeyelet = ((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Original Eyelet" }))).trim();
          
          String eyeletstyles = (String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Extra Eyelet Options" }));
          if (!eyeletstyles.trim().equals("")) {
            this.styleinformationdata.setOriginalEyelet("Default");
            eyeletstyles = "Default," + eyeletstyles;
          }
          this.styleinformationdata.setEyeletStyleNumber(makeEyeletObject(orgeyelet, eyeletstyles));
          
          closures = (String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Extra Closure Options" }));
          if (!((String)closures).trim().equals("")) {
            this.styleinformationdata.setOriginalClosure("Default");
            closures = "Default," + (String)closures;
          } else if (!orgclosure.equals("")) {
            closures = "Default";
          }
          this.styleinformationdata.setClosureStyleNumber(makeClosureObject(orgclosure, (String)closures));
          
          this.styleinformationdata.setStyleType(StyleInformationData.StyleType.OVERSEAS_INSTOCK);
          
          String sizes = (String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Size" }));
          this.styleinformationdata.setOriginalSize((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Default Size" })));
          
          this.styleinformationdata.setSize(makeOtherComboObject(sizes));
          
          this.styleinformationdata.setColorCode(makeColorObjectCache((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Original Color code colorcodes" }))));
          
          if (stylenumber.equals("888-ConceptCap")) {
            this.styleinformationdata.setVisorStyleNumber(makeVisorObject("V1,V2,V3,V4,V5,V6,V7,V8,V9,V10,V11,V12,V13,V14,V15,V16,V17,V18"));
            this.styleinformationdata.setSweatbandStyleNumber(makeSweatbandObject("S1,S2,S3,S4,S5,S6"));
          }
          
          Result<Record> sqlrecords;
          if (((sqlrecords = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_overseasinstock" })).where(new Condition[] { DSL.fieldByName(new String[] { "STYLE NUMBER" }).equal(stylenumber) }).fetch()) != null) && (sqlrecords.size() != 0)) {
            this.styleinformationdata.getAllVendorsStore().add(new OtherComboBoxModelData("Default"));
            
            if (sqlrecords.size() > 0) {
              Record sqlnamerecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "name" })).from(new TableLike[] { DSL.tableByName(new String[] { "list_vendors_overseas" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "id" }).equal(((Record)sqlrecords.get(0)).getValue(DSL.fieldByName(new String[] { "Primary VENDOR" }))) }).limit(1).fetchOne();
              if (sqlnamerecord != null) {
                this.styleinformationdata.getAllVendorsStore().add(new OtherComboBoxModelData((String)sqlnamerecord.getValue(DSL.fieldByName(new String[] { "name" })), (String)((Record)sqlrecords.get(0)).getValue(DSL.fieldByName(new String[] { "Primary VENDOR" }))));
              }
            }
            
            for (int i = 0; i < sqlrecords.size(); i++) {
              StyleInformationData stylevendorinfodata = new StyleInformationData();
              
              stylevendorinfodata.setStyleNumber((String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "STYLE NUMBER" })));
              stylevendorinfodata.setOrderType(ordertype);
              
              stylevendorinfodata.setVendorNumber(Integer.valueOf((String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "Backup VENDOR" }))).intValue());
              stylevendorinfodata.setLogoLocation(makeLocationObject((String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "Location" }))));
              stylevendorinfodata.setCategory((String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "Category" })));
              
              stylevendorinfodata.setDescription(((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(tmp5417_5414)) != null ? (String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "Emb Tooling Name" })) : "");
              
              String size = (String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "Size" }));
              stylevendorinfodata.setOriginalSize((String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "Default Size" })));
              
              stylevendorinfodata.setSize(makeOtherComboObject(size));
              
              stylevendorinfodata.setClosureStyleNumber(makeClosureObject((String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "Extra Closure Options" }))));
              stylevendorinfodata.setEyeletStyleNumber(makeEyeletObject((String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "Extra Eyelet Options" }))));
              
              String extravisorcolor = ((String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "Extra Visor" }))).trim();
              if (!extravisorcolor.equals("")) {
                stylevendorinfodata.setVisorStyleNumber(makeVisorObject(extravisorcolor));
              } else {
                stylevendorinfodata.setVisorStyleNumber(makeVisorObject((String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "Original Visor" }))));
              }
              
              stylevendorinfodata.setSweatbandStyleNumber(makeSweatbandObject((String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "Extra Sweatband" }))));
              stylevendorinfodata.setFrontPanelColor(makeColorObjectCache((String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "Front Panel Color" }))));
              stylevendorinfodata.setSideBackPanelColor(makeColorObjectCache((String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "Side & Back Panel Color" }))));
              stylevendorinfodata.setPrimaryVisorColor(makeColorObjectCache((String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "Primary Visor Color" }))));
              stylevendorinfodata.setVisorTrimEdgeColor(makeColorObjectCache((String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "Visor Trim/Edge Color" }))));
              stylevendorinfodata.setVisorSandwichColor(makeColorObjectCache((String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "Visor Sandwich Color" }))));
              
              String undervisorcolor = ((String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "Undervisor Color" }))).trim();
              if (!undervisorcolor.equals("")) {
                stylevendorinfodata.setUndervisorColor(makeColorObjectCache(undervisorcolor));
              } else {
                stylevendorinfodata.setUndervisorColor(makeColorObjectCache((String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "Primary Visor Color" }))));
              }
              
              stylevendorinfodata.setDistressedVisorInsideColor(makeColorObjectCache((String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "Distressed Visor Inside Color" }))));
              stylevendorinfodata.setClosureStrapColor(makeColorObjectCache((String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "Closure Strap Color" }))));
              stylevendorinfodata.setC2C31ClosureStrapColor(makeColorObjectCache((String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "C2/C31 Closure Strap Color" }))));
              stylevendorinfodata.setFrontEyeletColor(makeColorObjectCache((String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "E1 Eyelet Color" }))));
              stylevendorinfodata.setSideBackEyeletColor(makeColorObjectCache((String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "E1 Eyelet Color" }))));
              stylevendorinfodata.setButtonColor(makeColorObjectCache((String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "Button Color" }))));
              stylevendorinfodata.setInnerTapingColor(makeColorObjectCache((String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "Inner Taping Color" }))));
              stylevendorinfodata.setSweatbandColor(makeColorObjectCache((String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "Sweatband Color" }))));
              stylevendorinfodata.setStripeColor(makeColorObjectCache((String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "S2 Sweatband Color" }))));
              stylevendorinfodata.setS5SweatbandColor(makeColorObjectCache((String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "S5/S6 Sweatband Color" }))));
              stylevendorinfodata.setColorCode(makeColorObjectCache((String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "Cap Color" }))));
              stylevendorinfodata.setOriginalClosure(((String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "Original Closure" }))).trim());
              stylevendorinfodata.setOriginalEyelet(((String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "Original Eyelet" }))).trim());
              stylevendorinfodata.setOriginalSweatband(((String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "Original Sweatband" }))).trim());
              stylevendorinfodata.setOriginalVisor(((String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "Original Visor" }))).trim());
              stylevendorinfodata.setStyleType(StyleInformationData.StyleType.OVERSEAS_INSTOCK);
              
              stylevendorinfodata.setSameStyleNumber(((String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "Same Style" }))).trim());
              
              this.styleinformationdata.addAllVendors((String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "Backup VENDOR" })), stylevendorinfodata);
              
              if (!((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "Backup VENDOR" })).equals(((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "Primary VENDOR" })))) {
                Record sqlnamerecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "name" })).from(new TableLike[] { DSL.tableByName(new String[] { "list_vendors_overseas" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "id" }).equal(((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "Backup VENDOR" }))) }).limit(1).fetchOne();
                if (sqlnamerecord != null) {
                  this.styleinformationdata.getAllVendorsStore().add(new OtherComboBoxModelData((String)sqlnamerecord.getValue(DSL.fieldByName(new String[] { "name" })), (String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "Backup VENDOR" }))));
                }
              }
            }
            
            for (int i = 0; i < sqlrecords.size(); i++) {
              ((StyleInformationData)this.styleinformationdata.getAllVendors().get((String)((Record)sqlrecords.get(i)).getValue(DSL.fieldByName(new String[] { "Backup VENDOR" })))).setAllVendorsStore(this.styleinformationdata.getAllVendorsStore());
            }
            
          }
          
        }
      }
      else if ((sqlrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "overseas_price_instockshirts" })).where(new Condition[] { DSL.fieldByName(new String[] { "stylenumber" }).equal(stylenumber) }).limit(1).fetchOne()) != null) {
        this.styleinformationdata.setStyleNumber((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "stylenumber" })));
        this.styleinformationdata.setOrderType(ordertype);
        this.styleinformationdata.setVendorNumber(Integer.valueOf((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "vendor" }))).intValue());
        
        this.styleinformationdata.setLogoLocation(makeLocationObject((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "location" }))));
        this.styleinformationdata.setCategory((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "category" })));
        
        String newsizeoption = "";
        Record sqlrecord2;
        if ((sqlrecord2 = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_domestic_shirts" })).where(new Condition[] { DSL.fieldByName(new String[] { "sku" }).equal(stylenumber) }).limit(1).fetchOne()) != null) {
          this.styleinformationdata.setColorCode(makeColorObjectCache((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "colorcode" }))));
          newsizeoption = ((String)sqlrecord2.getValue(DSL.fieldByName(new String[] { "size" }))).trim();
          this.styleinformationdata.setSize(makeOtherComboObject((String)sqlrecord2.getValue(DSL.fieldByName(new String[] { "size" }))));
          this.styleinformationdata.setDescription((String)sqlrecord2.getValue(DSL.fieldByName(new String[] { "productname" })));
          this.styleinformationdata.setSameStyleNumber((String)sqlrecord2.getValue(DSL.fieldByName(new String[] { "samestyle" })));
        } else if ((sqlrecord2 = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_domestic_sweatshirts" })).where(new Condition[] { DSL.fieldByName(new String[] { "sku" }).equal(stylenumber) }).limit(1).fetchOne()) != null) {
          this.styleinformationdata.setColorCode(makeColorObjectCache((String)sqlrecord2.getValue(DSL.fieldByName(new String[] { "colorcodes" }))));
          newsizeoption = ((String)sqlrecord2.getValue(DSL.fieldByName(new String[] { "new_size_option" }))).trim();
          this.styleinformationdata.setSize(makeOtherComboObject((String)sqlrecord2.getValue(DSL.fieldByName(new String[] { "size" }))));
          this.styleinformationdata.setDescription((String)sqlrecord2.getValue(DSL.fieldByName(new String[] { "productname" })));
          this.styleinformationdata.setSameStyleNumber((String)sqlrecord2.getValue(DSL.fieldByName(new String[] { "samestyle" })));
        }
        
        if (!newsizeoption.equals(""))
        {
          this.styleinformationdata.setUseSizeColor(true);
          
          FastMap<ArrayList<OtherComboBoxModelData>> mysizecolor = new FastMap();
          for (closures = this.styleinformationdata.getSize().iterator(); ((java.util.Iterator)closures).hasNext();) { OtherComboBoxModelData currentsize = (OtherComboBoxModelData)((java.util.Iterator)closures).next();
            String size = currentsize.getValue();
            Record sqlcolorcoderecord;
            if ((sqlcolorcoderecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "colorcode" })).from(new TableLike[] { DSL.tableByName(new String[] { "overseas_price_instockshirts" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "stylenumber" }).equal(stylenumber) }).and(DSL.fieldByName(new String[] { "size" }).equal(size)).limit(1).fetchOne()) != null) {
              mysizecolor.put(size, makeColorObjectCache((String)sqlcolorcoderecord.getValue(DSL.fieldByName(new String[] { "colorcode" }))));
            } else if ((sqlcolorcoderecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "colorcode" })).from(new TableLike[] { DSL.tableByName(new String[] { "overseas_price_instockshirts" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "stylenumber" }).equal(stylenumber) }).limit(1).fetchOne()) != null) {
              mysizecolor.put(size, makeColorObjectCache((String)sqlcolorcoderecord.getValue(DSL.fieldByName(new String[] { "colorcode" }))));
            }
          }
          
          this.styleinformationdata.setSizeColor(mysizecolor);
        }
        
        this.styleinformationdata.setStyleType(StyleInformationData.StyleType.OVERSEAS_INSTOCK_SHIRTS);
      }
      else if ((sqlrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "overseas_price_instockflats" })).where(new Condition[] { DSL.fieldByName(new String[] { "stylenumber" }).equal(stylenumber) }).limit(1).fetchOne()) != null) {
        this.styleinformationdata.setStyleNumber((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "stylenumber" })));
        this.styleinformationdata.setOrderType(ordertype);
        this.styleinformationdata.setVendorNumber(Integer.valueOf((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "vendor" }))).intValue());
        
        this.styleinformationdata.setLogoLocation(makeLocationObject((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "location" }))));
        this.styleinformationdata.setCategory((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "category" })));
        
        this.styleinformationdata.setDescription((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "productname" })));
        this.styleinformationdata.setSameStyleNumber((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "samestyle" })));
        this.styleinformationdata.setColorCode(makeColorObjectCache((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "colorcode" }))));
        
        this.styleinformationdata.setStyleType(StyleInformationData.StyleType.OVERSEAS_INSTOCK_FLATS);
      }
      else if ((sqlrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_overseas" })).where(new Condition[] { DSL.fieldByName(new String[] { "Style" }).equal(stylenumber) }).limit(1).fetchOne()) != null)
      {
        this.styleinformationdata.setStyleNumber((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Style" })));
        this.styleinformationdata.setOrderType(ordertype);
        
        this.styleinformationdata.setVendorNumber(Integer.valueOf((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Vendor" }))).intValue());
        this.styleinformationdata.setLogoLocation(makeLocationObject((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Location" }))));
        this.styleinformationdata.setCategory((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Category" })));
        this.styleinformationdata.setDescription((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Emb Tooling Name" })));
        this.styleinformationdata.setSameStyleNumber((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Style" })));
        
        String size = doSizeExpand((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Size" })), true);
        this.styleinformationdata.setSize(makeOtherComboObject(size));
        
        this.styleinformationdata.setClosureStyleNumber(makeClosureObject((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Closure" }))));
        this.styleinformationdata.setEyeletStyleNumber(makeEyeletObject((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Eyelet Options" }))));
        this.styleinformationdata.setVisorStyleNumber(makeVisorObject((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Visor" }))));
        this.styleinformationdata.setSweatbandStyleNumber(makeSweatbandObject((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Sweatband" }))));
        this.styleinformationdata.setFrontPanelColor(makeColorObjectCache((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Front Panel Color" }))));
        this.styleinformationdata.setBackPanelColor(makeColorObjectCache((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Side & Back Panel Color" }))));
        this.styleinformationdata.setSidePanelColor(makeColorObjectCache((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Side & Back Panel Color" }))));
        this.styleinformationdata.setPrimaryVisorColor(makeColorObjectCache((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Primary Visor Color" }))));
        this.styleinformationdata.setVisorTrimEdgeColor(makeColorObjectCache((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Visor Trim/Edge Color" }))));
        this.styleinformationdata.setVisorSandwichColor(makeColorObjectCache((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Visor Sandwich Color" }))));
        this.styleinformationdata.setUndervisorColor(makeColorObjectCache((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Undervisor Color" }))));
        this.styleinformationdata.setDistressedVisorInsideColor(makeColorObjectCache((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Distressed Visor Inside Color" }))));
        this.styleinformationdata.setClosureStrapColor(makeColorObjectCache((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Closure Strap Color" }))));
        this.styleinformationdata.setC2C31ClosureStrapColor(makeColorObjectCache((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "C2/C31 Closure Strap Color" }))));
        this.styleinformationdata.setFrontEyeletColor(makeColorObjectCache((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Eyelet Color" }))));
        this.styleinformationdata.setSideBackEyeletColor(makeColorObjectCache((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Eyelet Color" }))));
        this.styleinformationdata.setButtonColor(makeColorObjectCache((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Button Color" }))));
        this.styleinformationdata.setInnerTapingColor(makeColorObjectCache((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Inner Taping Color" }))));
        this.styleinformationdata.setSweatbandColor(makeColorObjectCache((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Sweatband Color" }))));
        this.styleinformationdata.setStripeColor(makeColorObjectCache((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "S2 Sweatband Color" }))));
        this.styleinformationdata.setS5SweatbandColor(makeColorObjectCache((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "S5 Sweatband Color" }))));
        this.styleinformationdata.setColorCode(makeColorObjectCache((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Cap Color" }))));
        this.styleinformationdata.setStyleType(StyleInformationData.StyleType.OVERSEAS);
      } else if ((sqlrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_pre-designed" })).where(new Condition[] { DSL.fieldByName(new String[] { "Style" }).equal(stylenumber) }).limit(1).fetchOne()) != null) {
        this.styleinformationdata.setStyleNumber((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Style" })));
        this.styleinformationdata.setVendorNumber(Integer.valueOf((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Vendor" }))).intValue());
        this.styleinformationdata.setLogoLocation(makeLocationObject((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Location" }))));
        this.styleinformationdata.setCategory((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Category" })));
        this.styleinformationdata.setDescription((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Material Description" })));
        this.styleinformationdata.setSameStyleNumber((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Style" })));
        
        String size = doSizeExpand((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Size" })), true);
        
        if (!sqlrecord.getValue(DSL.fieldByName(new String[] { "Style" })).equals("888-A")) if (!sqlrecord.getValue(DSL.fieldByName(new String[] { "Style" })).equals("888-B")) if (!sqlrecord.getValue(DSL.fieldByName(new String[] { "Style" })).equals("888-C")) if (!sqlrecord.getValue(DSL.fieldByName(new String[] { "Style" })).equals("888-D")) if (!sqlrecord.getValue(DSL.fieldByName(new String[] { "Style" })).equals("888-E")) if (!sqlrecord.getValue(DSL.fieldByName(new String[] { "Style" })).equals("888-F")) if (!sqlrecord.getValue(DSL.fieldByName(new String[] { "Style" })).equals("888-G")) if (!sqlrecord.getValue(DSL.fieldByName(new String[] { "Style" })).equals("888-H")) if (!sqlrecord.getValue(DSL.fieldByName(new String[] { "Style" })).equals("888-J")) if (!sqlrecord.getValue(DSL.fieldByName(new String[] { "Style" })).equals("888-L")) if (!sqlrecord.getValue(DSL.fieldByName(new String[] { "Style" })).equals("888-M")) if (!sqlrecord.getValue(DSL.fieldByName(new String[] { "Style" })).equals("889-A")) if (!sqlrecord.getValue(DSL.fieldByName(new String[] { "Style" })).equals("889-B")) if (!sqlrecord.getValue(DSL.fieldByName(new String[] { "Style" })).equals("889-C")) if (!sqlrecord.getValue(DSL.fieldByName(new String[] { "Style" })).equals("889-D")) if (!sqlrecord.getValue(DSL.fieldByName(new String[] { "Style" })).equals("889-E"))
                                        if (!sqlrecord.getValue(DSL.fieldByName(new String[] { "Style" })).equals("889-F")) if (!sqlrecord.getValue(DSL.fieldByName(new String[] { "Style" })).equals("889-G")) if (!sqlrecord.getValue(DSL.fieldByName(new String[] { "Style" })).equals("889-H")) if (!sqlrecord.getValue(DSL.fieldByName(new String[] { "Style" })).equals("889-J")) if (!sqlrecord.getValue(DSL.fieldByName(new String[] { "Style" })).equals("889-L")) if (!sqlrecord.getValue(DSL.fieldByName(new String[] { "Style" })).equals("889-M")) break label10685;
        this.styleinformationdata.setClosureStyleNumber(makeClosureObject("C1,C2,C3,C4,C5,C6,C7,C8,C9,C10,C11,C12,C13,C14,C15,C16,C17,C25,C26,C27,C29,C30,C31"));
        this.styleinformationdata.setEyeletStyleNumber(makeEyeletObject("E1,E2,E3,E4,E5,E6,E7,E8,E9,E10,E11,E12,E13"));
        
        if (!sqlrecord.getValue(DSL.fieldByName(new String[] { "Style" })).equals("888-D")) { if (!sqlrecord.getValue(DSL.fieldByName(new String[] { "Style" })).equals("888-M")) {}
        } else { this.styleinformationdata.setVisorStyleNumber(makeVisorObject("V1,V2,V3,V4,V5,V6,V7,V8,V9,V10,V11,V12"));
          break label10631; } if (sqlrecord.getValue(DSL.fieldByName(new String[] { "Style" })).equals("888-C")) {
          this.styleinformationdata.setVisorStyleNumber(makeVisorObject("V1,V2,V3,V4,V5,V6,V7,V8,V9,V10,V11,V12,V13,V14,V15,V16,V17,V18"));
        } else
          this.styleinformationdata.setVisorStyleNumber(makeVisorObject("V1,V2,V3,V4,V5,V6,V7,V8,V9,V10,V11"));
        label10631:
        this.styleinformationdata.setSweatbandStyleNumber(makeSweatbandObject("S1,S2,S3,S4,S5,S6"));
        this.styleinformationdata.setSize(makeOtherComboObject(doSizeExpand("All", true)));
        this.styleinformationdata.setShowFOBDozen(true);
        this.styleinformationdata.setStyleType(StyleInformationData.StyleType.OVERSEAS_NONOTTO);
        return; label10685: if (!sqlrecord.getValue(DSL.fieldByName(new String[] { "Style" })).equals("888-I")) { if (!sqlrecord.getValue(DSL.fieldByName(new String[] { "Style" })).equals("889-I")) {}
        } else { this.styleinformationdata.setSize(makeOtherComboObject("XS,S,M,L,XL,2XL,3XL,4XL,5XL,6XL"));
          this.styleinformationdata.setStyleType(StyleInformationData.StyleType.OVERSEAS_NONOTTO_SHIRTS);
          this.styleinformationdata.setShowFOBDozen(true);
          return; }
        this.styleinformationdata.setSize(makeOtherComboObject(size));
        this.styleinformationdata.setStyleType(StyleInformationData.StyleType.OVERSEAS_PREDESIGNED);
      }
      else if ((sqlrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_lackpard" })).where(new Condition[] { DSL.fieldByName(new String[] { "Style" }).equal(stylenumber) }).limit(1).fetchOne()) != null) {
        this.styleinformationdata.setStyleNumber((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Style" })));
        this.styleinformationdata.setOrderType(ordertype);
        this.styleinformationdata.setSize(makeOtherComboObject((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Size" }))));
        this.styleinformationdata.setColorCode(makeColorObjectCache((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "colorcodes" }))));
        this.styleinformationdata.setCategory((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Main Category" })));
        this.styleinformationdata.setStyleType(StyleInformationData.StyleType.OVERSEAS_LACKPARD);
        this.styleinformationdata.setVendorNumber(4);
        this.styleinformationdata.setDescription((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Name" })));
        this.styleinformationdata.setSameStyleNumber((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Style" })));
      }
      else if ((sqlrecord = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_overseas_customstyle" })).where(new Condition[] { DSL.fieldByName(new String[] { "Style" }).equal(stylenumber) }).limit(1).fetchOne()) != null) {
        this.styleinformationdata.setStyleNumber((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Style" })));
        this.styleinformationdata.setOrderType(ordertype);
        this.styleinformationdata.setVendorNumber(Integer.valueOf((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Vendor" }))).intValue());
        this.styleinformationdata.setLogoLocation(makeLocationObject(""));
        this.styleinformationdata.setCategory("");
        
        this.styleinformationdata.setDescription("Custom Style");
        this.styleinformationdata.setSameStyleNumber((String)sqlrecord.getValue(DSL.fieldByName(new String[] { "Style" })));
        
        this.styleinformationdata.setStyleType(StyleInformationData.StyleType.OVERSEAS_CUSTOMSTYLE);
        
        this.styleinformationdata.setShowFOBDozen(true);
        
        Result<Record> theprofiletable = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_overseas_customstyle" })).where(new Condition[] { DSL.fieldByName(new String[] { "Style" }).equal(stylenumber) }).groupBy(new org.jooq.GroupField[] { DSL.fieldByName(new String[] { "Profile" }) }).fetch();
        if ((theprofiletable != null) && (theprofiletable.size() != 0)) {
          for (int i = 0; i < theprofiletable.size(); i++) {
            this.styleinformationdata.getAllProfileStore().add(new OtherComboBoxModelData(((String)((Record)theprofiletable.get(i)).getValue(DSL.fieldByName(new String[] { "Profile" }))).trim()));
            
            StyleInformationData styleprofileinfodata = new StyleInformationData();
            
            styleprofileinfodata.setStyleNumber(this.styleinformationdata.getStyleNumber());
            styleprofileinfodata.setOrderType(this.styleinformationdata.getOrderType());
            styleprofileinfodata.setVendorNumber(this.styleinformationdata.getVendorNumber());
            styleprofileinfodata.setCategory(this.styleinformationdata.getCategory());
            styleprofileinfodata.setDescription(this.styleinformationdata.getDescription());
            styleprofileinfodata.setSameStyleNumber(this.styleinformationdata.getSameStyleNumber());
            styleprofileinfodata.setStyleType(this.styleinformationdata.getStyleType());
            styleprofileinfodata.setShowFOBDozen(this.styleinformationdata.getShowFOBDozen());
            
            String sizestring = doSizeExpand((String)((Record)theprofiletable.get(i)).getValue(DSL.fieldByName(new String[] { "Size" })), true);
            
            styleprofileinfodata.setSize(makeOtherComboObject(sizestring));
            styleprofileinfodata.setSweatbandStyleNumber(makeSweatbandObject(((String)((Record)theprofiletable.get(i)).getValue(DSL.fieldByName(new String[] { "Sweatband" }))).replaceAll("N/A", "")));
            styleprofileinfodata.setVisorStyleNumber(makeVisorObject(((String)((Record)theprofiletable.get(i)).getValue(DSL.fieldByName(new String[] { "Visor" }))).replaceAll("N/A", "")));
            styleprofileinfodata.setClosureStyleNumber(makeClosureObject(((String)((Record)theprofiletable.get(i)).getValue(DSL.fieldByName(new String[] { "Closure" }))).replaceAll("N/A", "")));
            styleprofileinfodata.setEyeletStyleNumber(makeEyeletObject(((String)((Record)theprofiletable.get(i)).getValue(DSL.fieldByName(new String[] { "Eyelet" }))).replaceAll("N/A", "")));
            styleprofileinfodata.setLogoLocation(makeLocationObject((String)((Record)theprofiletable.get(i)).getValue(DSL.fieldByName(new String[] { "Location" }))));
            
            styleprofileinfodata.setNumberOfPanels(makeOtherComboObject(((String)((Record)theprofiletable.get(i)).getValue(DSL.fieldByName(new String[] { "No. of Panels" }))).replaceAll("N/A", "")));
            styleprofileinfodata.setCrownConstruction(makeOtherComboObject(((String)((Record)theprofiletable.get(i)).getValue(DSL.fieldByName(new String[] { "Crown Construction" }))).replaceAll("N/A", "")));
            styleprofileinfodata.setVisorRowStitching(makeOtherComboObject(((String)((Record)theprofiletable.get(i)).getValue(DSL.fieldByName(new String[] { "Visor Rows Stiching" }))).replaceAll("N/A", "")));
            
            Object thefabrictable = JOOQSQL.getInstance().create().selectFrom(DSL.tableByName(new String[] { "styles_overseas_customstyle" })).where(new Condition[] { DSL.fieldByName(new String[] { "Style" }).equal(stylenumber) }).and(DSL.fieldByName(new String[] { "Profile" }).equal((String)((Record)theprofiletable.get(i)).getValue(DSL.fieldByName(new String[] { "Profile" })))).groupBy(new org.jooq.GroupField[] { DSL.fieldByName(new String[] { "WorkFlow Fabic Name" }) }).fetch();
            if ((thefabrictable != null) && (((Result)thefabrictable).size() != 0)) {
              for (int j = 0; j < ((Result)thefabrictable).size(); j++) {
                styleprofileinfodata.getFrontFabricStore().add(new OtherComboBoxModelData(((String)((Record)((Result)thefabrictable).get(j)).getValue(DSL.fieldByName(new String[] { "WorkFlow Fabic Name" }))).trim()));
                
                if (((String)((Record)((Result)thefabrictable).get(j)).getValue(DSL.fieldByName(new String[] { "Single Color" }))).replaceAll("N/A", "").trim().equals("")) {
                  styleprofileinfodata.getBackFabricStore().add(new OtherComboBoxModelData(((String)((Record)((Result)thefabrictable).get(j)).getValue(DSL.fieldByName(new String[] { "WorkFlow Fabic Name" }))).trim()));
                  styleprofileinfodata.getSideFabricStore().add(new OtherComboBoxModelData(((String)((Record)((Result)thefabrictable).get(j)).getValue(DSL.fieldByName(new String[] { "WorkFlow Fabic Name" }))).trim()));
                }
                
                StyleInformationData stylefabricinfodata = new StyleInformationData();
                
                stylefabricinfodata.setColorCode(makeColorObjectCache(((String)((Record)((Result)thefabrictable).get(j)).getValue(DSL.fieldByName(new String[] { "Single Color" }))).replaceAll("N/A", "")));
                stylefabricinfodata.setFrontPanelColor(makeColorObjectCache(((String)((Record)((Result)thefabrictable).get(j)).getValue(DSL.fieldByName(new String[] { "Front Panel Color" }))).replaceAll("N/A", "")));
                stylefabricinfodata.setBackPanelColor(makeColorObjectCache(((String)((Record)((Result)thefabrictable).get(j)).getValue(DSL.fieldByName(new String[] { "Side & Back Panel Color" }))).replaceAll("N/A", "")));
                stylefabricinfodata.setSidePanelColor(makeColorObjectCache(((String)((Record)((Result)thefabrictable).get(j)).getValue(DSL.fieldByName(new String[] { "Side & Back Panel Color" }))).replaceAll("N/A", "")));
                stylefabricinfodata.setPrimaryVisorColor(makeColorObjectCache(((String)((Record)((Result)thefabrictable).get(j)).getValue(DSL.fieldByName(new String[] { "Primary Visor Color" }))).replaceAll("N/A", "")));
                stylefabricinfodata.setVisorTrimEdgeColor(makeColorObjectCache(((String)((Record)((Result)thefabrictable).get(j)).getValue(DSL.fieldByName(new String[] { "Visor Trim/Edge Color" }))).replaceAll("N/A", "")));
                stylefabricinfodata.setVisorSandwichColor(makeColorObjectCache(((String)((Record)((Result)thefabrictable).get(j)).getValue(DSL.fieldByName(new String[] { "Visor Sandwich Color" }))).replaceAll("N/A", "")));
                stylefabricinfodata.setUndervisorColor(makeColorObjectCache(((String)((Record)((Result)thefabrictable).get(j)).getValue(DSL.fieldByName(new String[] { "Undervisor Color" }))).replaceAll("N/A", "")));
                stylefabricinfodata.setDistressedVisorInsideColor(makeColorObjectCache(((String)((Record)((Result)thefabrictable).get(j)).getValue(DSL.fieldByName(new String[] { "Distressed Visor Inside Color" }))).replaceAll("N/A", "")));
                stylefabricinfodata.setClosureStrapColor(makeColorObjectCache(((String)((Record)((Result)thefabrictable).get(j)).getValue(DSL.fieldByName(new String[] { "Closure Strap Color" }))).replaceAll("N/A", "")));
                stylefabricinfodata.setC2C31ClosureStrapColor(makeColorObjectCache(((String)((Record)((Result)thefabrictable).get(j)).getValue(DSL.fieldByName(new String[] { "C2/C31 Closure Strap Color" }))).replaceAll("N/A", "")));
                stylefabricinfodata.setFrontEyeletColor(makeColorObjectCache(((String)((Record)((Result)thefabrictable).get(j)).getValue(DSL.fieldByName(new String[] { "E1 Eyelet Color" }))).replaceAll("N/A", "")));
                if (!stylenumber.contains("111-")) {
                  stylefabricinfodata.setSideEyeletColor(makeColorObjectCache(((String)((Record)((Result)thefabrictable).get(j)).getValue(DSL.fieldByName(new String[] { "E1 Eyelet Color" }))).replaceAll("N/A", "")));
                  stylefabricinfodata.setBackEyeletColor(makeColorObjectCache(((String)((Record)((Result)thefabrictable).get(j)).getValue(DSL.fieldByName(new String[] { "E1 Eyelet Color" }))).replaceAll("N/A", "")));
                } else {
                  stylefabricinfodata.setSideBackEyeletColor(makeColorObjectCache(((String)((Record)((Result)thefabrictable).get(j)).getValue(DSL.fieldByName(new String[] { "E1 Eyelet Color" }))).replaceAll("N/A", "")));
                }
                stylefabricinfodata.setButtonColor(makeColorObjectCache(((String)((Record)((Result)thefabrictable).get(j)).getValue(DSL.fieldByName(new String[] { "Button Color" }))).replaceAll("N/A", "")));
                stylefabricinfodata.setInnerTapingColor(makeColorObjectCache(((String)((Record)((Result)thefabrictable).get(j)).getValue(DSL.fieldByName(new String[] { "Inner Taping Color" }))).replaceAll("N/A", "")));
                stylefabricinfodata.setSweatbandColor(makeColorObjectCache(((String)((Record)((Result)thefabrictable).get(j)).getValue(DSL.fieldByName(new String[] { "Sweatband Color" }))).replaceAll("N/A", "")));
                stylefabricinfodata.setStripeColor(makeColorObjectCache(((String)((Record)((Result)thefabrictable).get(j)).getValue(DSL.fieldByName(new String[] { "S2 Sweatband Color" }))).replaceAll("N/A", "")));
                stylefabricinfodata.setS5SweatbandColor(makeColorObjectCache(((String)((Record)((Result)thefabrictable).get(j)).getValue(DSL.fieldByName(new String[] { "S5/S6 Sweatband Color" }))).replaceAll("N/A", "")));
                
                stylefabricinfodata.setPanelStitchColor(makeColorObjectCache(((String)((Record)((Result)thefabrictable).get(j)).getValue(DSL.fieldByName(new String[] { "Panel Stitch Color" }))).replaceAll("N/A", "")));
                stylefabricinfodata.setVisorStitchColor(makeColorObjectCache(((String)((Record)((Result)thefabrictable).get(j)).getValue(DSL.fieldByName(new String[] { "Visor Stitch Color" }))).replaceAll("N/A", "")));
                
                styleprofileinfodata.addAllFabric(((String)((Record)((Result)thefabrictable).get(j)).getValue(DSL.fieldByName(new String[] { "WorkFlow Fabic Name" }))).trim(), stylefabricinfodata);
              }
            }
            

            this.styleinformationdata.addAllProfile(((String)((Record)theprofiletable.get(i)).getValue(DSL.fieldByName(new String[] { "Profile" }))).trim(), styleprofileinfodata);
          }
        }
      }
      else
      {
        System.out.println(stylenumber + " Not Found");
      }
    }
  }
  

  private String doSizeExpand(String sizes, boolean overseas)
  {
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
  
  private ArrayList<OtherComboBoxModelData> makeDomesticLocationObject(Record therow) {
    ArrayList<String> thelocations = trimStringList((String)therow.getValue(DSL.fieldByName(new String[] { "location" })));
    ArrayList<String> thelocationsheat = trimStringList((String)therow.getValue(DSL.fieldByName(new String[] { "locationheat" })));
    ArrayList<String> thelocationsemb = trimStringList((String)therow.getValue(DSL.fieldByName(new String[] { "locationemb" })));
    ArrayList<String> thelocationsdtg = trimStringList((String)therow.getValue(DSL.fieldByName(new String[] { "locationdtg" })));
    ArrayList<String> thelocationsscreenprint = trimStringList((String)therow.getValue(DSL.fieldByName(new String[] { "locationscreenprint" })));
    
    ArrayList<OtherComboBoxModelData> thelist = new ArrayList();
    for (String currentlocation : thelocations) {
      OtherComboBoxModelData mylocation = new OtherComboBoxModelData(getLocationLabel(currentlocation), currentlocation);
      mylocation.set("heat", Boolean.valueOf(thelocationsheat.contains(currentlocation)));
      mylocation.set("emb", Boolean.valueOf(thelocationsemb.contains(currentlocation)));
      mylocation.set("dtg", Boolean.valueOf(thelocationsdtg.contains(currentlocation)));
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
    ArrayList<String> thelocationsscreenprint;
    if (styletype.equals("nonotto")) {
      ArrayList<String> thelocations = trimStringList("1,2,3,4,8,9,18,19,42");
      ArrayList<String> thelocationsheat = trimStringList("2,3,8,9,18,19");
      ArrayList<String> thelocationsemb = trimStringList("1,2,3,8,9,18,19");
      ArrayList<String> thelocationsdtg = trimStringList("1,2,3,4,18,19,42");
      thelocationsscreenprint = trimStringList("");
    } else {
      thelocations = trimStringList("1,8,14,18,19,A1,A2,A4,A5,A6,A7,A8,A9,A10,A11,A12,A13,A14,A15,A16");
      thelocationsheat = trimStringList("1,8,14,18,19,A1,A2,A4,A5,A6,A7,A8,A9,A10,A11,A12,A13,A14,A15,A16");
      thelocationsemb = trimStringList("1,8,14,18,19,A1,A2,A4,A5,A6,A7,A8,A9,A10,A11,A12,A13,A14,A15,A16");
      thelocationsdtg = trimStringList("1,8,14,18,19,A1,A2,A4,A5,A6,A9,A10,A11,A12,A13,A14,A15,A16");
      thelocationsscreenprint = trimStringList("1,8,14,18,19,A1,A2,A4,A5,A6,A7,A8,A9,A10,A11,A12,A13,A14,A15,A16");
    }
    
    ArrayList<OtherComboBoxModelData> thelist = new ArrayList();
    for (String currentlocation : thelocations) {
      OtherComboBoxModelData mylocation = new OtherComboBoxModelData(getLocationLabel(currentlocation), currentlocation);
      mylocation.set("heat", Boolean.valueOf(thelocationsheat.contains(currentlocation)));
      mylocation.set("emb", Boolean.valueOf(thelocationsemb.contains(currentlocation)));
      mylocation.set("dtg", Boolean.valueOf(thelocationsdtg.contains(currentlocation)));
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
      this.theottocodetocolorcache = new com.ottocap.NewWorkFlow.server.JOOQ_OTTOCodeToColorCached();
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
        OtherComboBoxModelData mycolor = new OtherComboBoxModelData(colorcodesarray[i].trim() + com.ottocap.NewWorkFlow.server.JOOQ_OTTOCodeToColor.getStaticCodeDisc(colorcodesarray[i].trim()), colorcodesarray[i].trim());
        colorcode.add(mycolor);
      }
    }
    return colorcode;
  }
  
  private String getClosureStyleLabel(String closurestylenumber) {
    Record sqlrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "name" })).from(new TableLike[] { DSL.tableByName(new String[] { "overseas_price_styles_closure" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "style" }).equal(closurestylenumber) }).limit(1).fetchOne();
    if (sqlrecord != null) {
      return closurestylenumber + " - " + (String)sqlrecord.getValue(DSL.fieldByName(new String[] { "name" }));
    }
    return closurestylenumber;
  }
  
  private String getEyeletStyleLabel(String eyeletstylenumber)
  {
    Record sqlrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "name" })).from(new TableLike[] { DSL.tableByName(new String[] { "overseas_price_styles_eyelet" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "style" }).equal(eyeletstylenumber) }).limit(1).fetchOne();
    if (sqlrecord != null) {
      return eyeletstylenumber + " - " + (String)sqlrecord.getValue(DSL.fieldByName(new String[] { "name" }));
    }
    return eyeletstylenumber;
  }
  
  private String getSweatbandStyleLabel(String sweatbandstylenumber)
  {
    Record sqlrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "name" })).from(new TableLike[] { DSL.tableByName(new String[] { "overseas_price_styles_sweatband" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "style" }).equal(sweatbandstylenumber) }).limit(1).fetchOne();
    if (sqlrecord != null) {
      return sweatbandstylenumber + " - " + (String)sqlrecord.getValue(DSL.fieldByName(new String[] { "name" }));
    }
    return sweatbandstylenumber;
  }
  
  private String getVisorStyleLabel(String visorstylenumber)
  {
    Record sqlrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "name" })).from(new TableLike[] { DSL.tableByName(new String[] { "overseas_price_styles_visor" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "style" }).equal(visorstylenumber) }).limit(1).fetchOne();
    if (sqlrecord != null) {
      return visorstylenumber + " - " + (String)sqlrecord.getValue(DSL.fieldByName(new String[] { "name" }));
    }
    return visorstylenumber;
  }
  
  private String getLocationLabel(String locationnumber)
  {
    Record sqlrecord = JOOQSQL.getInstance().create().select(DSL.fieldByName(new String[] { "name" })).from(new TableLike[] { DSL.tableByName(new String[] { "list_decorations_locations" }) }).where(new Condition[] { DSL.fieldByName(new String[] { "id" }).equal(locationnumber) }).limit(1).fetchOne();
    if (sqlrecord != null) {
      if ((locationnumber.equals("1")) || (locationnumber.equals("2")) || (locationnumber.equals("3")) || (locationnumber.equals("4")) || (locationnumber.equals("5")) || (locationnumber.equals("6")) || (locationnumber.equals("7")) || (locationnumber.equals("8")) || (locationnumber.equals("9"))) {
        return "0" + locationnumber + " - " + (String)sqlrecord.getValue(DSL.fieldByName(new String[] { "name" }));
      }
      return locationnumber + " - " + (String)sqlrecord.getValue(DSL.fieldByName(new String[] { "name" }));
    }
    
    return locationnumber;
  }
  
  public StyleInformationData getStyleInformationData()
  {
    return this.styleinformationdata;
  }
}
