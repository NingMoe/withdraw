package com.phicomm.smarthome.ssp.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author fujiang.mao
 * @date 2018-05-25
 * @version V1.0
 * @Description stat_conn_tr和stat_use_tr实体类
 */
public class StatConnOrUseTrModel {

    @JsonProperty("stat_id")
    private long statId;

    @JsonProperty("stat_date")
    private String statDate;// 统计日期

    @JsonProperty("range_id")
    private int rangeId;

    @JsonProperty("device_total")
    private long deviceTotal;// 时长范围内的设备数, 一个设备如果多次连接会有多个连接时长；时长范围内的设备数,
                             // 一个设备如果打赏多次则会有多个使用时长

    @JsonProperty("order_total")
    private long orderTotal;// 打赏次数

    @JsonProperty("create_date")
    private long createDate;

    public void setStatId(long statId) {
        this.statId = statId;
    }

    public long getStatId() {
        return statId;
    }

    public void setStatDate(String statDate) {
        this.statDate = statDate;
    }

    public String getStatDate() {
        return statDate;
    }

    public void setRangeId(int rangeId) {
        this.rangeId = rangeId;
    }

    public int getRangeId() {
        return rangeId;
    }

    public void setDeviceTotal(long deviceTotal) {
        this.deviceTotal = deviceTotal;
    }

    public long getDeviceTotal() {
        return deviceTotal;
    }

    public void setOrderTotal(long orderTotal) {
        this.orderTotal = orderTotal;
    }

    public long getOrderTotal() {
        return orderTotal;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public long getCreateDate() {
        return createDate;
    }

}
