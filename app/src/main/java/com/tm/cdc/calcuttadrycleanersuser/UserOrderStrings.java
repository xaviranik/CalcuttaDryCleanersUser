package com.tm.cdc.calcuttadrycleanersuser;

import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class UserOrderStrings {

    String orderID, totalPrice, totalItems, timeStamp;
    int orderStatus;

    public UserOrderStrings(String orderID, String totalPrice, String totalItems, String timeStamp, int orderStatus)
    {
        this.orderID = orderID;
        this.totalPrice = totalPrice;
        this.totalItems = totalItems;
        this.timeStamp = timeStamp;
        this.orderStatus = orderStatus;
    }

    public String getOrderID()
    {
        return orderID;
    }

    public void setOrderID(String orderID)
    {
        this.orderID = orderID;
    }

    public String getTotalPrice()
    {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice)
    {
        this.totalPrice = totalPrice;
    }

    public String getTotalItems()
    {
        return totalItems;
    }

    public void setTotalItems(String totalItems)
    {
        this.totalItems = totalItems;
    }

    public String getTimeStamp()
    {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp)
    {
        this.timeStamp = timeStamp;
    }

    public int getOrderStatus()
    {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus)
    {
        this.orderStatus = orderStatus;
    }
}
