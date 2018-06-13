package com.phicomm.smarthome.ssp.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by xiangrong.ke on 2017/6/21.
 */
public class RequestHandleWithDrawBodyModel implements Serializable {

    @ApiModelProperty(value = "主键ID")
    @JsonProperty("num")
    private long id;

    @ApiModelProperty(value = "订单编号")
    @JsonProperty("order_num")
    private String orderNum;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }
}
