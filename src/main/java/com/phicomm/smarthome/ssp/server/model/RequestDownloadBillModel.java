package com.phicomm.smarthome.ssp.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by xiangrong.ke on 2017/6/21.
 */
public class RequestDownloadBillModel implements Serializable {

    @JsonProperty("bill_date")
    private String billDate;

    @JsonProperty("bill_type")
    private String billType;


    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }
}
