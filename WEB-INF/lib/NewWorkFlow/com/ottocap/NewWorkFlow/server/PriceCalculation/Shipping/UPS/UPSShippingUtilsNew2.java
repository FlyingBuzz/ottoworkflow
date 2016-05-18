package com.ottocap.NewWorkFlow.server.PriceCalculation.Shipping.UPS;

import java.io.PrintStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;























































































public class UPSShippingUtilsNew2
{
  public static final int REQUEST_FAILED = 0;
  public static final int REQUEST_SUCCEEDED = 1;
  public static final String UPS_NEXT_DAY_AIR = "01";
  public static final String UPS_SECOND_DAY_AIR = "02";
  public static final String UPS_GROUND = "03";
  public static final String UPS_WORLDWIDE_EXPRESS = "07";
  public static final String UPS_WORLDWIDE_EXPEDITED = "08";
  public static final String UPS_STANDARD = "11";
  public static final String UPS_THREE_DAY_SELECT = "12";
  public static final String UPS_NEXT_DAY_AIR_SAVER = "13";
  public static final String UPS_NEXT_DAY_AIR_EARLY_AM = "14";
  public static final String UPS_WORLDWIDE_EXPRESS_PLUS = "54";
  public static final String UPS_SECOND_DAY_AIR_AM = "59";
  public static final String UPS_SAVER = "65";
  
  public static RatingServiceSelectionResponseContainer submitUPSShippingRequest(String zipCodeTo, String zipCodeFrom, BigDecimal shipmentValue, String[] allweights, boolean negotiatedRates, boolean residentialAddress, String addressline1_to, String city_to, String state_to)
    throws Exception
  {
    String UPSUsername = "ottoca";
    String UPSPassword = "jurupa";
    String XMLAccessKey = "4C2B5118DDD3E49C";
    String companyName = "Otto International, Inc.";
    String UPSAccount = "920187";
    


    String packagingType = "02";
    




    String pickupType = "01";
    






    boolean writeXMLtoFile = false;
    


    String responseFile = "C:\\tech\\shiprate_response.xml";
    String requestFile = "C:\\tech\\shiprate_request.xml";
    


    String protocol = "https";
    String hostname = "wwwcie.ups.com";
    String URLPrefix = "ups.app/xml";
    String service = "Rate";
    









    UPSXMLTransmitter xmlTransmitter = null;
    StringBuffer responseXML = new StringBuffer();
    



    try
    {
      xmlTransmitter = new UPSXMLTransmitter(hostname, protocol);


    }
    catch (Exception e)
    {

      System.out.println("Cannot build XmlTransmitter: " + e.toString());
      return null;
    }
    

    StringBuffer requestXML = getAccessRequestXML(XMLAccessKey, UPSUsername, UPSPassword);
    requestXML.append(getShipRateRequestStartXML(zipCodeFrom, zipCodeTo, UPSAccount, companyName, pickupType, residentialAddress, addressline1_to, city_to, state_to));
    
    for (int i = 0; i < allweights.length; i++) {
      requestXML.append(getShipRatePackageRequestXML(allweights[i], shipmentValue, packagingType));
    }
    
    requestXML.append(getShipRateRequestEndXML(negotiatedRates));
    
    xmlTransmitter.setXml(requestXML);
    try
    {
      xmlTransmitter.contactService(service, URLPrefix);
      responseXML = xmlTransmitter.getXml();
    } catch (Exception e) {
      System.out.println("Cannot contact the service: " + e.toString());
      return null;
    }
    
    RatingServiceSelectionResponseContainer rssrc = null;
    try {
      rssrc = parseRepsonseXML(responseXML);
    } catch (Exception e) {
      System.out.println("Cannot parse response XML");
    }
    

    if (writeXMLtoFile) {
      try {
        UPSXMLTransmitter.writeOutputFile(requestXML, requestFile);
        UPSXMLTransmitter.writeOutputFile(responseXML, responseFile);
      } catch (Exception e) {
        System.out.println("Cannot write the xml response to file: " + e.toString());
      }
    }
    
    return rssrc;
  }
  












  public static TreeMap<String, Double[]> getUPSShippingCost(RatingServiceSelectionResponseContainer rssrc)
  {
    if ((rssrc != null) && (Integer.parseInt(rssrc.getRssResponse().getResponseStatusCode()) == 1))
    {

      TreeMap<String, ArrayList<Double>> myshippingtypes = new TreeMap();
      Iterator<RSSRatedShipment> rsIt = rssrc.getRssRatedShipments().iterator();
      while (rsIt.hasNext()) {
        RSSRatedShipment rssrs = (RSSRatedShipment)rsIt.next();
        
        String currenttype = rssrs.getService().code;
        ArrayList<Double> currentarray;
        ArrayList<Double> currentarray; if (myshippingtypes.containsKey(currenttype)) {
          currentarray = (ArrayList)myshippingtypes.get(currenttype);
        } else {
          currentarray = new ArrayList();
        }
        
        ArrayList<RSSRSRatedPackage> mypackages = rssrs.getRatedPackages();
        for (int i = 0; i < mypackages.size(); i++) {
          currentarray.add(Double.valueOf(((RSSRSRatedPackage)mypackages.get(i)).getTotalCharges().monetaryValue));
        }
        
        myshippingtypes.put(currenttype, currentarray);
      }
      

      String[] mykeys = (String[])myshippingtypes.keySet().toArray(new String[0]);
      TreeMap<String, Double[]> myshippingtypesdouble = new TreeMap();
      for (int i = 0; i < myshippingtypes.size(); i++) {
        myshippingtypesdouble.put(mykeys[i], (Double[])((ArrayList)myshippingtypes.get(mykeys[i])).toArray(new Double[0]));
      }
      
      return myshippingtypesdouble;
    }
    return null;
  }
  







  private static RatingServiceSelectionResponseContainer parseRepsonseXML(StringBuffer responseXML)
    throws Exception
  {
    if (responseXML != null) {
      StringReader sr = new StringReader(responseXML.toString());
      

      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      

      Document doc = factory.newDocumentBuilder().parse(new InputSource(sr));
      
      return fillResponseContainer(doc);
    }
    return null;
  }
  









  private static RatingServiceSelectionResponseContainer fillResponseContainer(Document doc)
  {
    RatingServiceSelectionResponseContainer rssrc = new RatingServiceSelectionResponseContainer();
    

    Node root = doc.getDocumentElement();
    




    for (Node mainsection = root.getFirstChild(); mainsection != null; mainsection = mainsection.getNextSibling()) {
      if (mainsection.getNodeName().equalsIgnoreCase("Response")) {
        for (Node node = mainsection.getFirstChild(); node != null; node = node.getNextSibling())
        {



          if (node.getNodeName().equalsIgnoreCase("TransactionReference")) {
            for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {
              if (child.getNodeName().equalsIgnoreCase("CustomerContext")) {
                rssrc.getRssResponse().getTransactionReference().customerContext = getNodeTextContent(child);
              } else if (child.getNodeName().equalsIgnoreCase("XpciVersion")) {
                rssrc.getRssResponse().getTransactionReference().xpciVersion = getNodeTextContent(child);
              }
            }
          } else if (node.getNodeName().equalsIgnoreCase("ResponseStatusCode")) {
            rssrc.getRssResponse().setResponseStatusCode(getNodeTextContent(node));
          } else if (node.getNodeName().equalsIgnoreCase("ResponseStatusDescription")) {
            rssrc.getRssResponse().setResponseStatusDescription(getNodeTextContent(node));
          } else if (node.getNodeName().equalsIgnoreCase("Error"))
          {
            RSSResponse tmp247_244 = rssrc.getRssResponse();tmp247_244.getClass();RSSResponse.Error err = new RSSResponse.Error(tmp247_244);
            
            for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {
              if (child.getNodeName().equalsIgnoreCase("ErrorSeverity")) {
                err.errorSeverity = getNodeTextContent(child);
              } else if (child.getNodeName().equalsIgnoreCase("ErrorCode")) {
                err.errorCode = getNodeTextContent(child);
              } else if (child.getNodeName().equalsIgnoreCase("ErrorDescription")) {
                err.errorDescription = getNodeTextContent(child);
              } else if (child.getNodeName().equalsIgnoreCase("MinimumRetrySeconds")) {
                err.minimumRetrySeconds = getNodeTextContent(child);
              } else if (child.getNodeName().equalsIgnoreCase("ErrorLocation")) {
                RSSResponse.Error tmp407_405 = err;tmp407_405.getClass();RSSResponse.Error.ErrorLocation errLoc = new RSSResponse.Error.ErrorLocation(tmp407_405);
                
                for (Node subchild = child.getFirstChild(); subchild != null; subchild = subchild.getNextSibling()) {
                  if (subchild.getNodeName().equalsIgnoreCase("ErrorLocationElementName")) {
                    errLoc.errorLocationElementName = getNodeTextContent(subchild);
                  } else if (subchild.getNodeName().equalsIgnoreCase("ErrorLocationAttributeName")) {
                    errLoc.errorLocationAttributeName = getNodeTextContent(subchild);
                  }
                }
                
                err.errorLocations.add(errLoc);
              } else if (child.getNodeName().equalsIgnoreCase("ErrorDigest")) {
                String errDigest = getNodeTextContent(child);
                err.errorDigests.add(errDigest);
              }
            }
            
            rssrc.getRssResponse().getErrors().add(err);
          }
        }
      } else if (mainsection.getNodeName().equalsIgnoreCase("RatedShipment"))
      {
        RSSRatedShipment rssrs = new RSSRatedShipment();
        
        for (Node node = mainsection.getFirstChild(); node != null; node = node.getNextSibling()) {
          if (node.getNodeName().equalsIgnoreCase("Service")) {
            for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {
              if (child.getNodeName().equalsIgnoreCase("Code")) {
                rssrs.getService().code = getNodeTextContent(child);
              } else if (child.getNodeName().equalsIgnoreCase("Description")) {
                rssrs.getService().description = getNodeTextContent(child);
              }
            }
          } else if (node.getNodeName().equalsIgnoreCase("RatedShipmentWarning")) {
            rssrs.setRatedShipmentWarning(getNodeTextContent(node));
          } else if (node.getNodeName().equalsIgnoreCase("BillingWeight")) {
            for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {
              if (child.getNodeName().equalsIgnoreCase("UnitOfMeasurement")) {
                for (Node subchild = child.getFirstChild(); subchild != null; subchild = subchild.getNextSibling()) {
                  if (subchild.getNodeName().equalsIgnoreCase("Code")) {
                    rssrs.getBillingWeight().unitOfMeasurement.code = getNodeTextContent(subchild);
                  } else if (subchild.getNodeName().equalsIgnoreCase("Description")) {
                    rssrs.getBillingWeight().unitOfMeasurement.description = getNodeTextContent(subchild);
                  }
                }
              } else if (child.getNodeName().equalsIgnoreCase("Weight")) {
                rssrs.getBillingWeight().weight = getNodeTextContent(child);
              }
            }
          } else if (node.getNodeName().equalsIgnoreCase("TransportationCharges")) {
            for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {
              if (child.getNodeName().equalsIgnoreCase("CurrencyCode")) {
                rssrs.getTransportationCharges().currencyCode = getNodeTextContent(child);
              } else if (child.getNodeName().equalsIgnoreCase("MonetaryValue")) {
                rssrs.getTransportationCharges().monetaryValue = getNodeTextContent(child);
              }
            }
          } else if (node.getNodeName().equalsIgnoreCase("ServiceOptionsCharges")) {
            for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {
              if (child.getNodeName().equalsIgnoreCase("CurrencyCode")) {
                rssrs.getServiceOptionsCharges().currencyCode = getNodeTextContent(child);
              } else if (child.getNodeName().equalsIgnoreCase("MonetaryValue")) {
                rssrs.getServiceOptionsCharges().monetaryValue = getNodeTextContent(child);
              }
            }
          } else if (node.getNodeName().equalsIgnoreCase("HandlingChargeAmount")) {
            for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {
              if (child.getNodeName().equalsIgnoreCase("CurrencyCode")) {
                rssrs.getHandlingChargeAmount().currencyCode = getNodeTextContent(child);
              } else if (child.getNodeName().equalsIgnoreCase("MonetaryValue")) {
                rssrs.getHandlingChargeAmount().monetaryValue = getNodeTextContent(child);
              }
            }
          } else if (node.getNodeName().equalsIgnoreCase("TotalCharges")) {
            for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {
              if (child.getNodeName().equalsIgnoreCase("CurrencyCode")) {
                rssrs.getTotalCharges().currencyCode = getNodeTextContent(child);
              } else if (child.getNodeName().equalsIgnoreCase("MonetaryValue")) {
                rssrs.getTotalCharges().monetaryValue = getNodeTextContent(child);
              }
            }
          } else if (node.getNodeName().equalsIgnoreCase("GuaranteedDaysToDelivery")) {
            rssrs.setGuaranteedDaysToDelivery(getNodeTextContent(node));
          } else if (node.getNodeName().equalsIgnoreCase("ScheduledDeliveryTime")) {
            rssrs.setScheduledDeliveryTime(getNodeTextContent(node));
          } else if (node.getNodeName().equalsIgnoreCase("RatedPackage"))
          {
            RSSRSRatedPackage rssrsrp = new RSSRSRatedPackage();
            
            for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {
              if (child.getNodeName().equalsIgnoreCase("TransportationCharges")) {
                for (Node subchild = child.getFirstChild(); subchild != null; subchild = subchild.getNextSibling()) {
                  if (subchild.getNodeName().equalsIgnoreCase("CurrencyCode")) {
                    rssrsrp.getTransportationCharges().currencyCode = getNodeTextContent(subchild);
                  } else if (subchild.getNodeName().equalsIgnoreCase("MonetaryValue")) {
                    rssrsrp.getTransportationCharges().monetaryValue = getNodeTextContent(subchild);
                  }
                }
              } else if (child.getNodeName().equalsIgnoreCase("ServiceOptionsCharges")) {
                for (Node subchild = child.getFirstChild(); subchild != null; subchild = subchild.getNextSibling()) {
                  if (subchild.getNodeName().equalsIgnoreCase("CurrencyCode")) {
                    rssrsrp.getServiceOptionsCharges().currencyCode = getNodeTextContent(subchild);
                  } else if (subchild.getNodeName().equalsIgnoreCase("MonetaryValue")) {
                    rssrsrp.getServiceOptionsCharges().monetaryValue = getNodeTextContent(subchild);
                  }
                }
              } else if (child.getNodeName().equalsIgnoreCase("TotalCharges")) {
                for (Node subchild = child.getFirstChild(); subchild != null; subchild = subchild.getNextSibling()) {
                  if (subchild.getNodeName().equalsIgnoreCase("CurrencyCode")) {
                    rssrsrp.getTotalCharges().currencyCode = getNodeTextContent(subchild);
                  } else if (subchild.getNodeName().equalsIgnoreCase("MonetaryValue")) {
                    rssrsrp.getTotalCharges().monetaryValue = getNodeTextContent(subchild);
                  }
                }
              } else if (child.getNodeName().equalsIgnoreCase("Weight")) {
                rssrsrp.setWeight(getNodeTextContent(child));
              } else if (child.getNodeName().equalsIgnoreCase("BillingWeight")) {
                for (Node subchild = child.getFirstChild(); subchild != null; subchild = subchild.getNextSibling()) {
                  if (subchild.getNodeName().equalsIgnoreCase("UnitOfMeasurement")) {
                    for (Node subsubchild = subchild.getFirstChild(); subsubchild != null; subsubchild = subsubchild.getNextSibling()) {
                      if (subsubchild.getNodeName().equalsIgnoreCase("Code")) {
                        rssrsrp.getBillingWeight().unitOfMeasurement.code = getNodeTextContent(subsubchild);
                      } else if (subsubchild.getNodeName().equalsIgnoreCase("Description")) {
                        rssrsrp.getBillingWeight().unitOfMeasurement.description = getNodeTextContent(subsubchild);
                      }
                    }
                  } else if (subchild.getNodeName().equalsIgnoreCase("Weight")) {
                    rssrsrp.getBillingWeight().weight = getNodeTextContent(subchild);
                  }
                }
              }
            }
            
            rssrs.getRatedPackages().add(rssrsrp);
          } else if (node.getNodeName().equalsIgnoreCase("NegotiatedRates")) {
            for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {
              if (child.getNodeName().equalsIgnoreCase("NetSummaryCharges")) {
                for (Node subchild = child.getFirstChild(); subchild != null; subchild = subchild.getNextSibling()) {
                  if (subchild.getNodeName().equalsIgnoreCase("GrandTotal")) {
                    for (Node subsubchild = subchild.getFirstChild(); subsubchild != null; subsubchild = subsubchild.getNextSibling()) {
                      if (subsubchild.getNodeName().equalsIgnoreCase("CurrencyCode")) {
                        rssrs.getNegotiatedRates().netSummaryCharges.grandTotal.currencyCode = getNodeTextContent(subsubchild);
                      } else if (subsubchild.getNodeName().equalsIgnoreCase("MonetaryValue")) {
                        rssrs.getNegotiatedRates().netSummaryCharges.grandTotal.monetaryValue = getNodeTextContent(subsubchild);
                      }
                    }
                  }
                }
              }
            }
          }
        }
        
        rssrc.getRssRatedShipments().add(rssrs);
      }
    }
    return rssrc;
  }
  










  private static StringBuffer getAccessRequestXML(String accessKey, String username, String password)
  {
    StringBuffer accessRequest = new StringBuffer("<?xml version=\"1.0\"?>\n");
    
    accessRequest.append("<AccessRequest xml:lang=\"en-US\">\n");
    accessRequest.append("\t<AccessLicenseNumber>" + accessKey + "</AccessLicenseNumber>\n");
    accessRequest.append("\t<UserId>" + username + "</UserId>\n");
    accessRequest.append("\t<Password>" + password + "</Password>\n");
    accessRequest.append("</AccessRequest>\n");
    
    return accessRequest;
  }
  








































  private static StringBuffer getShipRateRequestStartXML(String zipcodeFrom, String zipcodeTo, String UPSAccount, String coName, String pickupTypeCode, boolean residentialAddress, String addressline1_to, String city_to, String state_to)
  {
    StringBuffer shipRateRequest = new StringBuffer("<?xml version=\"1.0\"?>\n");
    
    shipRateRequest.append("<RatingServiceSelectionRequest xml:lang=\"en-US\">\n");
    shipRateRequest.append("<Request>\n");
    shipRateRequest.append("\t<TransactionReference>\n");
    shipRateRequest.append("\t\t<CustomerContext>Rating and Service</CustomerContext>\n");
    shipRateRequest.append("\t\t<XpciVersion>1.0001</XpciVersion>\n");
    shipRateRequest.append("\t</TransactionReference>\n");
    shipRateRequest.append("\t<RequestAction>Rate</RequestAction>\n");
    shipRateRequest.append("\t<RequestOption>Shop</RequestOption>\n");
    shipRateRequest.append("</Request>\n");
    shipRateRequest.append("<PickupType>\n");
    shipRateRequest.append("\t<Code>" + pickupTypeCode + "</Code>\n");
    shipRateRequest.append("</PickupType>\n");
    shipRateRequest.append("<Shipment>\n");
    shipRateRequest.append("\t<Shipper>\n");
    shipRateRequest.append("\t\t<Name>" + coName + "</Name>\n");
    shipRateRequest.append("\t\t<ShipperNumber>" + UPSAccount + "</ShipperNumber>\n");
    shipRateRequest.append("\t\t<Address>\n");
    

    shipRateRequest.append("\t\t\t<PostalCode>" + zipcodeFrom + "</PostalCode>\n");
    shipRateRequest.append("\t\t\t<CountryCode>US</CountryCode>\n");
    shipRateRequest.append("\t\t</Address>\n");
    shipRateRequest.append("\t</Shipper>\n");
    shipRateRequest.append("\t<ShipTo>\n");
    shipRateRequest.append("\t\t<Address>\n");
    if ((addressline1_to != null) && (!addressline1_to.trim().equals(""))) {
      shipRateRequest.append("\t\t\t<AddressLine1>" + addressline1_to.trim() + "</AddressLine1>\n");
    }
    if ((city_to != null) && (!city_to.trim().equals(""))) {
      shipRateRequest.append("\t\t\t<City>" + city_to.trim() + "</City>\n");
    }
    if ((state_to != null) && (!state_to.trim().equals(""))) {
      shipRateRequest.append("\t\t\t<StateProvinceCode>" + state_to.trim() + "</StateProvinceCode>\n");
    }
    shipRateRequest.append("\t\t\t<PostalCode>" + zipcodeTo + "</PostalCode>\n");
    shipRateRequest.append("\t\t\t<CountryCode>US</CountryCode>\n");
    if (residentialAddress) {
      shipRateRequest.append("\t\t\t<ResidentialAddressIndicator/>\n");
    }
    shipRateRequest.append("\t\t</Address>\n");
    shipRateRequest.append("\t</ShipTo>\n");
    
    return shipRateRequest;
  }
  
  private static StringBuffer getShipRateRequestEndXML(boolean negotiatedRates) {
    StringBuffer shipRateRequest = new StringBuffer("<?xml version=\"1.0\"?>\n");
    
    shipRateRequest.append("\t<ShipmentServiceOptions/>\n");
    if (negotiatedRates) {
      shipRateRequest.append("\t<RateInformation>\n");
      shipRateRequest.append("\t\t<NegotiatedRatesIndicator/>\n");
      shipRateRequest.append("\t</RateInformation>\n");
    }
    shipRateRequest.append("</Shipment>\n");
    shipRateRequest.append("</RatingServiceSelectionRequest>");
    
    return shipRateRequest;
  }
  
  private static StringBuffer getShipRatePackageRequestXML(String weight, BigDecimal value, String packageTypeCode) {
    StringBuffer shipRateRequest = new StringBuffer("<?xml version=\"1.0\"?>\n");
    
    shipRateRequest.append("\t<Package>\n");
    shipRateRequest.append("\t\t<PackagingType>\n");
    shipRateRequest.append("\t\t\t<Code>" + packageTypeCode + "</Code>\n");
    shipRateRequest.append("\t\t\t<Description>Package</Description>\n");
    shipRateRequest.append("\t\t</PackagingType>\n");
    shipRateRequest.append("\t\t<Description>Rate Shopping</Description>\n");
    shipRateRequest.append("\t\t<PackageWeight>\n");
    shipRateRequest.append("\t\t\t<UnitOfMeasurement>LBS</UnitOfMeasurement>\n");
    shipRateRequest.append("\t\t\t<Weight>" + weight + "</Weight>\n");
    shipRateRequest.append("\t\t</PackageWeight>\n");
    shipRateRequest.append("\t\t<PackageServiceOptions>\n");
    shipRateRequest.append("\t\t\t<InsuredValue>\n");
    shipRateRequest.append("\t\t\t\t<CurrencyCode>USD</CurrencyCode>\n");
    shipRateRequest.append("\t\t\t\t<MonetaryValue>" + value + "</MonetaryValue>\n");
    shipRateRequest.append("\t\t\t</InsuredValue>\n");
    shipRateRequest.append("\t\t</PackageServiceOptions>\n");
    shipRateRequest.append("\t</Package>\n");
    
    return shipRateRequest;
  }
  









  private static String getNodeTextContent(Node node)
  {
    for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {
      if (child.getNodeName().equalsIgnoreCase("#text")) {
        return node.getFirstChild().getNodeValue();
      }
    }
    return null;
  }
  
  public static boolean requestSucceeded(RatingServiceSelectionResponseContainer rssrc) {
    if (rssrc != null) {
      return Integer.parseInt(rssrc.getRssResponse().getResponseStatusCode()) == 1;
    }
    return false;
  }
}
