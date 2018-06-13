package com.phicomm.smarthome.ssp.server.model;

/**
 * Created by xiangrong.ke on 2017/7/7.
 */
public class SwGuestOrderBillTiModel {
    private long id;
    private long dealTime;
    private String dealTimeFormat;
    private String orderId;
    private String cost;
    private long billDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDealTimeFormat() {
        return dealTimeFormat;
    }

    public void setDealTimeFormat(String dealTimeFormat) {
        this.dealTimeFormat = dealTimeFormat;
    }

    public long getDealTime() {
        return dealTime;
    }

    public void setDealTime(long dealTime) {
        this.dealTime = dealTime;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public long getBillDate() {
        return billDate;
    }

    public void setBillDate(long billDate) {
        this.billDate = billDate;
    }
}
