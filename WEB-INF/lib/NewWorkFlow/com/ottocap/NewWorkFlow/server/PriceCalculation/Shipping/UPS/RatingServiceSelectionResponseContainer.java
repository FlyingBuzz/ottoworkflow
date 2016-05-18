package com.ottocap.NewWorkFlow.server.PriceCalculation.Shipping.UPS;

import java.util.ArrayList;





































public class RatingServiceSelectionResponseContainer
{
  private RSSResponse rssResponse = new RSSResponse();
  private ArrayList<RSSRatedShipment> rssRatedShipments = new ArrayList();
  



  public ArrayList<RSSRatedShipment> getRssRatedShipments()
  {
    return this.rssRatedShipments;
  }
  
  public void setRssRatedShipments(ArrayList<RSSRatedShipment> rssRatedShipments) {
    this.rssRatedShipments = rssRatedShipments;
  }
  
  public RSSResponse getRssResponse() {
    return this.rssResponse;
  }
  
  public void setRssResponse(RSSResponse rssResponse) {
    this.rssResponse = rssResponse;
  }
}
