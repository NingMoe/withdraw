package com.phicomm.smarthome.ssp.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author fujiang.mao
 * @date 2017-12-13
 * @version V1.0
 * @Description sw_user_income实体类
 */
public class SwUserIncomeModel {

    @JsonProperty("id")
    private long id;// 自增长id

    @JsonProperty("uid")
    private String uid;// 用户的id

    @JsonProperty("router_mac")
    private String routerMac;// 路由器mac地址

    @JsonProperty("today_income")
    private String todayIncome;// 今日收入

    @JsonProperty("total_income")
    private String totalIncome;// 总收入

    @JsonProperty("today_date")
    private String todayDate;// 今日日期，精确到天

    @JsonProperty("order_id")
    private String orderId;// 对外显示的订单id

    @JsonProperty("create_time")
    private long createTime;// 创建时间

    @JsonProperty("update_time")
    private long updateTime;// 更新时间

    @JsonProperty("status")
    private byte status;// 状态 0 正常 -1 删除

    private String iphone;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setRouterMac(String routerMac) {
        this.routerMac = routerMac;
    }

    public String getRouterMac() {
        return routerMac;
    }

    public void setTodayIncome(String todayIncome) {
        this.todayIncome = todayIncome;
    }

    public String getTodayIncome() {
        return todayIncome;
    }

    public void setTotalIncome(String totalIncome) {
        this.totalIncome = totalIncome;
    }

    public String getTotalIncome() {
        return totalIncome;
    }

    public void setTodayDate(String todayDate) {
        this.todayDate = todayDate;
    }

    public String getTodayDate() {
        return todayDate;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public byte getStatus() {
        return status;
    }

    public String getIphone() {
        return iphone;
    }

    public void setIphone(String iphone) {
        this.iphone = iphone;
    }

}
