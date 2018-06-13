package com.phicomm.smarthome.ssp.server.model;

/**
 * Created by xiangrong.ke on 2017/7/7.
 */
public class SwGuestOrderModel {
    private long id;
    private String orderId;
    private long guestId;
    private long userShareParaId;
    private int orderStatus;
    private int orderPayStatus;
    private long buyTime;
    private float onlineTimeTotal;
    private String onlineTimeTotalCost;
    private long createTime;
    private long updateTime;
    private int status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public long getGuestId() {
        return guestId;
    }

    public void setGuestId(long guestId) {
        this.guestId = guestId;
    }

    public long getUserShareParaId() {
        return userShareParaId;
    }

    public void setUserShareParaId(long userShareParaId) {
        this.userShareParaId = userShareParaId;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getOrderPayStatus() {
        return orderPayStatus;
    }

    public void setOrderPayStatus(int orderPayStatus) {
        this.orderPayStatus = orderPayStatus;
    }

    public float getOnlineTimeTotal() {
        return onlineTimeTotal;
    }

    public void setOnlineTimeTotal(float onlineTimeTotal) {
        this.onlineTimeTotal = onlineTimeTotal;
    }

    public long getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(long buyTime) {
        this.buyTime = buyTime;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getOnlineTimeTotalCost() {
        return onlineTimeTotalCost;
    }

    public void setOnlineTimeTotalCost(String onlineTimeTotalCost) {
        this.onlineTimeTotalCost = onlineTimeTotalCost;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }
}
