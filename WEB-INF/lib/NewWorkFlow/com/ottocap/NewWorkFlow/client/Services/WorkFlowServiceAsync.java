package com.ottocap.NewWorkFlow.client.Services;

import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.google.gwt.user.client.rpc.AsyncCallback;
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

public abstract interface WorkFlowServiceAsync
{
  public abstract void getProductsLocationsList(PagingLoadConfig paramPagingLoadConfig, OrderData.OrderType paramOrderType, AsyncCallback<BasePagingLoadResult<OtherComboBoxModelData>> paramAsyncCallback);
  
  public abstract void getProductsList(PagingLoadConfig paramPagingLoadConfig, OrderData.OrderType paramOrderType, AsyncCallback<BasePagingLoadResult<OtherComboBoxModelData>> paramAsyncCallback);
  
  public abstract void getCategoryList(PagingLoadConfig paramPagingLoadConfig, OrderData.OrderType paramOrderType, AsyncCallback<BasePagingLoadResult<OtherComboBoxModelData>> paramAsyncCallback);
  
  public abstract void getEclipseAccountList(PagingLoadConfig paramPagingLoadConfig, AsyncCallback<BasePagingLoadResult<OtherComboBoxModelData>> paramAsyncCallback);
  
  public abstract void getEclipseAccount(int paramInt, AsyncCallback<EclipseDataHolder> paramAsyncCallback);
  
  public abstract void submitLogin(String paramString1, String paramString2, AsyncCallback<StoredUser> paramAsyncCallback);
  
  public abstract void saveOrder(OrderData paramOrderData, AsyncCallback<Integer> paramAsyncCallback);
  
  public abstract void getOrder(int paramInt, AsyncCallback<OrderData> paramAsyncCallback);
  
  public abstract void getOrderWithStyle(int paramInt, AsyncCallback<OrderDataWithStyle> paramAsyncCallback);
  
  public abstract void getOrderList(PagingLoadConfig paramPagingLoadConfig, AsyncCallback<BasePagingLoadResult<BaseModelData>> paramAsyncCallback);
  
  public abstract void getEmployeeList(AsyncCallback<ArrayList<OtherComboBoxModelData>> paramAsyncCallback);
  
  public abstract void getOrderEmployee(int paramInt, AsyncCallback<String> paramAsyncCallback);
  
  public abstract void saveOrderEmployee(int paramInt, String paramString, AsyncCallback<Void> paramAsyncCallback);
  
  public abstract void getFirstLoad(AsyncCallback<FirstLoadData> paramAsyncCallback);
  
  public abstract void getIsFaya(AsyncCallback<Boolean> paramAsyncCallback);
  
  public abstract void getStyleData(String paramString, OrderData.OrderType paramOrderType, AsyncCallback<StyleInformationData> paramAsyncCallback);
  
  public abstract void getRemoveEclipseAccountLogo(int paramInt, AsyncCallback<Void> paramAsyncCallback);
  
  public abstract void getCurrentEmployee(AsyncCallback<String> paramAsyncCallback);
  
  public abstract void copyOrder(OrderData paramOrderData, AsyncCallback<OrderData> paramAsyncCallback);
  
  public abstract void copyToOrder(OrderData paramOrderData, AsyncCallback<OrderDataWithStyle> paramAsyncCallback);
  
  public abstract void copyOrderByKey(int paramInt, AsyncCallback<OrderDataWithStyle> paramAsyncCallback);
  
  public abstract void doLogoff(AsyncCallback<String> paramAsyncCallback);
  
  public abstract void getSalesReportList(PagingLoadConfig paramPagingLoadConfig, AsyncCallback<BasePagingLoadResult<BaseModelData>> paramAsyncCallback);
  
  public abstract void getOnlineGatherList(PagingLoadConfig paramPagingLoadConfig, AsyncCallback<BasePagingLoadResult<BaseModelData>> paramAsyncCallback);
  
  public abstract void getContainerInvoiceList(PagingLoadConfig paramPagingLoadConfig, AsyncCallback<BasePagingLoadResult<BaseModelData>> paramAsyncCallback);
  
  public abstract void saveContainerInvoice(ContainerData paramContainerData, AsyncCallback<Integer> paramAsyncCallback);
  
  public abstract void getPricingTable(int paramInt, AsyncCallback<ArrayList<String[]>> paramAsyncCallback);
}
