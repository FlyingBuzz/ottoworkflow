package com.ottocap.NewWorkFlow.client.Services;

import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.ottocap.NewWorkFlow.client.ContainerInvoice.ContainerData.ContainerData;
import com.ottocap.NewWorkFlow.client.DataHolder.CustomerInfo.EclipseDataHolder;
import com.ottocap.NewWorkFlow.client.FirstLoadData;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData;
import com.ottocap.NewWorkFlow.client.OrderData.OrderData.OrderType;
import com.ottocap.NewWorkFlow.client.OrderDataWithStyle;
import com.ottocap.NewWorkFlow.client.StoredUser;
import com.ottocap.NewWorkFlow.client.StyleInformationData;
import com.ottocap.NewWorkFlow.client.Widget.ComboBoxes.OtherComboBoxModelData;
import java.util.ArrayList;

@RemoteServiceRelativePath("workflowservice")
public abstract interface WorkFlowService
  extends RemoteService
{
  public abstract BasePagingLoadResult<OtherComboBoxModelData> getProductsLocationsList(PagingLoadConfig paramPagingLoadConfig, OrderData.OrderType paramOrderType);
  
  public abstract BasePagingLoadResult<OtherComboBoxModelData> getProductsList(PagingLoadConfig paramPagingLoadConfig, OrderData.OrderType paramOrderType);
  
  public abstract BasePagingLoadResult<OtherComboBoxModelData> getCategoryList(PagingLoadConfig paramPagingLoadConfig, OrderData.OrderType paramOrderType);
  
  public abstract BasePagingLoadResult<OtherComboBoxModelData> getEclipseAccountList(PagingLoadConfig paramPagingLoadConfig);
  
  public abstract EclipseDataHolder getEclipseAccount(int paramInt);
  
  public abstract StoredUser submitLogin(String paramString1, String paramString2);
  
  public abstract Integer saveOrder(OrderData paramOrderData)
    throws Exception;
  
  public abstract OrderData getOrder(int paramInt)
    throws Exception;
  
  public abstract BasePagingLoadResult<BaseModelData> getOrderList(PagingLoadConfig paramPagingLoadConfig)
    throws Exception;
  
  public abstract ArrayList<OtherComboBoxModelData> getEmployeeList()
    throws Exception;
  
  public abstract String getOrderEmployee(int paramInt)
    throws Exception;
  
  public abstract void saveOrderEmployee(int paramInt, String paramString)
    throws Exception;
  
  public abstract FirstLoadData getFirstLoad()
    throws Exception;
  
  public abstract Boolean getIsFaya()
    throws Exception;
  
  public abstract StyleInformationData getStyleData(String paramString, OrderData.OrderType paramOrderType);
  
  public abstract OrderDataWithStyle getOrderWithStyle(int paramInt)
    throws Exception;
  
  public abstract void getRemoveEclipseAccountLogo(int paramInt);
  
  public abstract String getCurrentEmployee();
  
  public abstract OrderData copyOrder(OrderData paramOrderData)
    throws Exception;
  
  public abstract OrderDataWithStyle copyToOrder(OrderData paramOrderData)
    throws Exception;
  
  public abstract OrderDataWithStyle copyOrderByKey(int paramInt)
    throws Exception;
  
  public abstract String doLogoff();
  
  public abstract BasePagingLoadResult<BaseModelData> getSalesReportList(PagingLoadConfig paramPagingLoadConfig)
    throws Exception;
  
  public abstract BasePagingLoadResult<BaseModelData> getOnlineGatherList(PagingLoadConfig paramPagingLoadConfig)
    throws Exception;
  
  public abstract BasePagingLoadResult<BaseModelData> getContainerInvoiceList(PagingLoadConfig paramPagingLoadConfig)
    throws Exception;
  
  public abstract Integer saveContainerInvoice(ContainerData paramContainerData)
    throws Exception;
  
  public abstract ArrayList<String[]> getPricingTable(int paramInt)
    throws Exception;
}
