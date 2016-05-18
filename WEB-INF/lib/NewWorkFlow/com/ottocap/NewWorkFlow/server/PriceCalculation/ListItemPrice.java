package com.ottocap.NewWorkFlow.server.PriceCalculation;

import java.util.Comparator;









public class ListItemPrice
{
  int item;
  double price;
  
  public ListItemPrice(int item, double price)
  {
    this.item = item;
    this.price = price;
  }
  
  public int getItem() {
    return this.item;
  }
  
  public double getPrice() {
    return this.price;
  }
  
  public static Comparator<ListItemPrice> getPriceDesc() {
    Comparator<ListItemPrice> comparator = new Comparator()
    {
      public int compare(ListItemPrice entry1, ListItemPrice entry2) {
        if (entry1.getPrice() > entry2.getPrice())
          return -1;
        if (entry1.getPrice() == entry2.getPrice()) {
          return 0;
        }
        return 1;
      }
      
    };
    return comparator;
  }
  
  public static Comparator<ListItemPrice> getPriceAsc() {
    Comparator<ListItemPrice> comparator = new Comparator()
    {
      public int compare(ListItemPrice entry1, ListItemPrice entry2) {
        if (entry1.getPrice() > entry2.getPrice())
          return 1;
        if (entry1.getPrice() == entry2.getPrice()) {
          return 0;
        }
        return -1;
      }
      
    };
    return comparator;
  }
  
  public static Comparator<ListItemPrice> getItemAsc() {
    Comparator<ListItemPrice> comparator = new Comparator()
    {
      public int compare(ListItemPrice entry1, ListItemPrice entry2) {
        if (entry1.getItem() > entry2.getItem())
          return 1;
        if (entry1.getItem() == entry2.getItem()) {
          return 0;
        }
        return -1;
      }
      
    };
    return comparator;
  }
}
