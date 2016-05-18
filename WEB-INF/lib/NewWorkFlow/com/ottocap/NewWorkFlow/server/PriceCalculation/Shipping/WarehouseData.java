package com.ottocap.NewWorkFlow.server.PriceCalculation.Shipping;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;









public class WarehouseData
{
  private Vector<WarehouseUnit> mywarehouses = new Vector();
  


  public void addWarehouseUnit(long id, String zip)
  {
    this.mywarehouses.add(new WarehouseUnit(id, zip));
  }
  
  public int getTotalWearhouse() {
    return this.mywarehouses.size();
  }
  
  public WarehouseUnit getWarehouseUnit(int position) {
    return (WarehouseUnit)this.mywarehouses.get(position);
  }
  
  public void sortWarehouse()
  {
    Collections.sort(this.mywarehouses, new Comparator()
    {
      public int compare(WarehouseUnit obj1, WarehouseUnit obj2) {
        double i1 = obj1.getDistance();
        double i2 = obj2.getDistance();
        
        if (i1 > i2)
          return 1;
        if (i1 < i2) {
          return -1;
        }
        return 0;
      }
    });
  }
}
