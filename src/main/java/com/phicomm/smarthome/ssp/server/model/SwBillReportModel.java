package com.phicomm.smarthome.ssp.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

/**
 * Created by xiangrong.ke on 2017/7/10.
 */
@Entity
@Table(name = "sw_bill_report")
@ApiModel(value = "SwBillReportModel", description = "对账结果实体表")
public class SwBillReportModel {

    @ApiModelProperty(value = "主键ID")
    @Column(name = "id")
    @JsonProperty("num")
    private long id;

    @ApiModelProperty(value = "后台交易时间（时间戳）")
    @Column(name = "backend_deal_time")
    @JsonProperty("backend_deal_time")
    private long backendDealTime;

    @ApiModelProperty(value = "后台交易时间（格式化）")
    @Column(name = "backend_deal_time_format")
    @JsonProperty("backend_deal_time_format")
    private String backendDealTimeFormat;

    @ApiModelProperty(value = "后台订单号")
    @Column(name = "backend_order_id")
    @JsonProperty("backend_order_id")
    private String backendOrderId;

    @ApiModelProperty(value = "微信订单号")
    @Column(name = "wx_order_id")
    @JsonProperty("wx_order_id")
    private String wxOrderId;

    @ApiModelProperty(value = "微信商户内部订单号")
    @Column(name = "wx_backend_order_id")
    @JsonProperty("wx_backend_order_id")
    private String wxBackendOrderId;

    @ApiModelProperty(value = "后台交易金额")
    @Column(name = "backend_cost")
    @JsonProperty("backend_cost")
    private String backendCost;

    @ApiModelProperty(value = "交易方式")
    @Column(name = "wx_pay_type")
    @JsonProperty("wx_pay_type")
    private String wxPayType;

    @ApiModelProperty(value = "微信交易时间（时间戳）")
    @Column(name = "wx_deal_time")
    @JsonProperty("wx_deal_time")
    private long wxDealTime;

    @ApiModelProperty(value = "微信交易时间（格式化）")
    @Column(name = "wx_deal_time_format")
    @JsonProperty("wx_deal_time_format")
    private String wxDealTimeFormat;

    @ApiModelProperty(value = "微信交易金额")
    @Column(name = "wx_cost")
    @JsonProperty("wx_cost")
    private String wxCost;

    @ApiModelProperty(value = "状态，0正常，1异常")
    @Column(name = "status")
    @JsonProperty("status")
    private int status;

    @ApiModelProperty(value = "账单日期")
    @Column(name = "bill_date")
    @JsonProperty("bill_date")
    private long billDate;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBackendDealTime() {
        return backendDealTime;
    }

    public void setBackendDealTime(long backendDealTime) {
        this.backendDealTime = backendDealTime;
    }

    public String getBackendDealTimeFormat() {
        return backendDealTimeFormat;
    }

    public void setBackendDealTimeFormat(String backendDealTimeFormat) {
        this.backendDealTimeFormat = backendDealTimeFormat;
    }

    public String getBackendOrderId() {
        return backendOrderId;
    }

    public void setBackendOrderId(String backendOrderId) {
        this.backendOrderId = backendOrderId;
    }

    public String getWxOrderId() {
        return wxOrderId;
    }

    public void setWxOrderId(String wxOrderId) {
        this.wxOrderId = wxOrderId;
    }

    public String getBackendCost() {
        return backendCost;
    }

    public void setBackendCost(String backendCost) {
        this.backendCost = backendCost;
    }

    public String getWxPayType() {
        return wxPayType;
    }

    public void setWxPayType(String wxPayType) {
        this.wxPayType = wxPayType;
    }

    public long getWxDealTime() {
        return wxDealTime;
    }

    public void setWxDealTime(long wxDealTime) {
        this.wxDealTime = wxDealTime;
    }

    public String getWxDealTimeFormat() {
        return wxDealTimeFormat;
    }

    public void setWxDealTimeFormat(String wxDealTimeFormat) {
        this.wxDealTimeFormat = wxDealTimeFormat;
    }

    public String getWxCost() {
        return wxCost;
    }

    public void setWxCost(String wxCost) {
        this.wxCost = wxCost;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getBillDate() {
        return billDate;
    }

    public void setBillDate(long billDate) {
        this.billDate = billDate;
    }

    public String getWxBackendOrderId() {
        return wxBackendOrderId;
    }

    public void setWxBackendOrderId(String wxBackendOrderId) {
        this.wxBackendOrderId = wxBackendOrderId;
    }
}
