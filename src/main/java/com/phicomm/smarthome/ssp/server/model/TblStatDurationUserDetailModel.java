package com.phicomm.smarthome.ssp.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author fujiang.mao
 * @date 2017-10-27
 * @version V1.0
 * @Description tbl_stat_duration_user_detail实体类
 */
public class TblStatDurationUserDetailModel {

    @JsonProperty("id")
    private long id;

    @JsonProperty("order_date_ts")
    private int orderDateTs;// 订单的时间戳（10位，秒级）

    @JsonProperty("date")
    private String date;

    @JsonProperty("sw_money")
    private int swMoney;// 打赏金额，单位：分

    @JsonProperty("show_sw_money")
    private String showSwMoney;// 打赏金额，单位：元

    @JsonProperty("link_time")
    private int linkTime;// 连接时长，单位：秒

    @JsonProperty("user_time")
    private int userTime;// 使用时长，单位：秒

    @JsonProperty("link_device")
    private String linkDevice;// 连接设备

    @JsonProperty("ip_address")
    private String ipAddress;// IP地址

    @JsonProperty("router_mac")
    private String routerMac;// 路由器MAC地址

    @JsonProperty("device_mac")
    private String deviceMac;// 设备MAC地址

    @JsonProperty("order_id")
    private String orderId;// 订单号

    @JsonProperty("guest_id")
    private long guestId;// 客户id,对应sw_guest表中的自增长id

    @JsonProperty("device_type")
    private byte deviceType;// 设备类型，对应tbl_sw_user_slt_device表id

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setOrderDateTs(int orderDateTs) {
        this.orderDateTs = orderDateTs;
    }

    public int getOrderDateTs() {
        return orderDateTs;
    }

    public void setSwMoney(int swMoney) {
        this.swMoney = swMoney;
    }

    public int getSwMoney() {
        return swMoney;
    }

    public void setLinkTime(int linkTime) {
        this.linkTime = linkTime;
    }

    public int getLinkTime() {
        return linkTime;
    }

    public void setUserTime(int userTime) {
        this.userTime = userTime;
    }

    public int getUserTime() {
        return userTime;
    }

    public void setLinkDevice(String linkDevice) {
        this.linkDevice = linkDevice;
    }

    public String getLinkDevice() {
        return linkDevice;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setRouterMac(String routerMac) {
        this.routerMac = routerMac;
    }

    public String getRouterMac() {
        return routerMac;
    }

    public void setDeviceMac(String deviceMac) {
        this.deviceMac = deviceMac;
    }

    public String getDeviceMac() {
        return deviceMac;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setGuestId(long guestId) {
        this.guestId = guestId;
    }

    public long getGuestId() {
        return guestId;
    }

    public void setDeviceType(byte deviceType) {
        this.deviceType = deviceType;
    }

    public byte getDeviceType() {
        return deviceType;
    }

    public String getShowSwMoney() {
        return showSwMoney;
    }

    public void setShowSwMoney(String showSwMoney) {
        this.showSwMoney = showSwMoney;
    }

}
