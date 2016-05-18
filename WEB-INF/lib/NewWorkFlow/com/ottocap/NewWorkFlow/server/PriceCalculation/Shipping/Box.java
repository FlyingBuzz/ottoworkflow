package com.ottocap.NewWorkFlow.server.PriceCalculation.Shipping;





public class Box
{
  private int max_qty;
  



  private int currentquantity = 0;
  private double weight = 0.0D;
  private int cubicinch = 0;
  private Item[] myitems;
  private int box_id;
  
  public int getQuantity() {
    return this.currentquantity;
  }
  
  public int getMaxQty() {
    return this.max_qty;
  }
  
  public boolean isFilled() {
    return this.max_qty == this.currentquantity;
  }
  
  public Box(int max_qty, int cubicinch, int box_id) {
    this.max_qty = max_qty;
    this.cubicinch = cubicinch;
    this.box_id = box_id;
    this.myitems = new Item[max_qty];
  }
  
  public double getWeight() {
    return this.weight;
  }
  
  public int getCubicInch() {
    return this.cubicinch;
  }
  
  public double getDimWeight()
  {
    return this.cubicinch / 166.0D;
  }
  
  public double getHigherWeight()
  {
    return Math.max(this.weight, this.cubicinch / 166.0D);
  }
  
  private void addWeight(double itemweight) {
    this.weight += itemweight;
  }
  
  public Item[] getItems() {
    return this.myitems;
  }
  
  public boolean add(Item item) throws Exception {
    if ((this.currentquantity < this.max_qty) && (item.getBoxCubicInch(this.box_id) <= getCubicInch())) {
      this.myitems[this.currentquantity] = item;
      addWeight(item.getWeight());
      this.currentquantity += 1;
      return true; }
    if (item.getBoxCubicInch(this.box_id) > getCubicInch()) {
      throw new Exception("Item " + item.getStyleNumber() + " can't fit box can only hold " + getCubicInch() + " however item is " + item.getBoxCubicInch(this.box_id));
    }
    
    return false;
  }
}
