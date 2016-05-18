package com.ottocap.NewWorkFlow.server;

import com.fasterxml.jackson.core.JsonGenerator;







public class JSONHelper
{
  public static void addObject(JsonGenerator generator, String fieldname, Object object)
    throws Exception
  {
    if (object == null) {
      return;
    }
    generator.writeFieldName(fieldname);
    if ((object instanceof String)) {
      generator.writeString((String)object);
    } else if ((object instanceof Boolean)) {
      generator.writeBoolean(((Boolean)object).booleanValue());
    } else if ((object instanceof Integer)) {
      generator.writeNumber(((Integer)object).intValue());
    } else if ((object instanceof Double)) {
      generator.writeNumber(((Double)object).doubleValue());
    } else if ((object instanceof Long)) {
      generator.writeNumber(((Long)object).longValue());
    } else if ((object instanceof Float)) {
      generator.writeNumber(((Float)object).floatValue());
    } else {
      throw new Exception("Cast Not Found" + object.getClass());
    }
  }
}
