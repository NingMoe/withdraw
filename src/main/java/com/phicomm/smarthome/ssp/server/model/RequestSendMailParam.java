package com.phicomm.smarthome.ssp.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by kexiangrong on 2017/7/11.
 */
public class RequestSendMailParam {

    @ApiModelProperty(value = "对账日期")
    @JsonProperty("bill_date")
    private String billDate;

    @ApiModelProperty(value = "内容")
    @JsonProperty("content")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }
}
