package com.phicomm.smarthome.ssp.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by kexiangrong on 2017/7/11.
 */
public class RequestBillReportParam {

    @ApiModelProperty(value = "当前页")
    @JsonProperty("cur_page")
    private String curPage;

    @ApiModelProperty(value = "每页大小")
    @JsonProperty("page_size")
    private String pageSize;

    @ApiModelProperty(value = "账单查询的开始时间")
    @JsonProperty("bill_date_begin")
    private String billDateBegin;

    @ApiModelProperty(value = "账单查询的结束时间")
    @JsonProperty("bill_date_end")
    private String billDateEnd;

    @ApiModelProperty(value = "H5 ,saoma,如果是所有则传空字符串")
    @JsonProperty("pay_type")
    private String payType;

    @ApiModelProperty(value = "0是正常，1是异常,如果是所有则传空字符串")
    @JsonProperty("status")
    private String status;

    public String getBillDateBegin() {
        return billDateBegin;
    }

    public void setBillDateBegin(String billDateBegin) {
        this.billDateBegin = billDateBegin;
    }

    public String getBillDateEnd() {
        return billDateEnd;
    }

    public void setBillDateEnd(String billDateEnd) {
        this.billDateEnd = billDateEnd;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCurPage() {
        return curPage;
    }

    public void setCurPage(String curPage) {
        this.curPage = curPage;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }
}
