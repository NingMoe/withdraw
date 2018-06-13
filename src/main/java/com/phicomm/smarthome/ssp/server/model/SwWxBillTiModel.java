package com.phicomm.smarthome.ssp.server.model;

/**
 * Created by xiangrong.ke on 2017/7/6.
 */
public class SwWxBillTiModel {
    private long id;
    private long dealTime;
    private String dealTimeFormat;
    private String wxOrderId;
    private String orderId;
    private String cost;
    private String payType;
    private long billDate;

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getWxOrderId() {
        return wxOrderId;
    }

    public void setWxOrderId(String wxOrderId) {
        this.wxOrderId = wxOrderId;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBillDate() {
        return billDate;
    }

    public void setBillDate(long billDate) {
        this.billDate = billDate;
    }
}
