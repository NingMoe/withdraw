package com.phicomm.smarthome.ssp.server.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.phicomm.smarthome.model.cache.BaseReqPageModel;

/**
 *
 */
public class RequestStatTimePeriodModel extends BaseReqPageModel {

    @JsonProperty("slt_start_date")
    private String sltStartDate;// 起始日期

    @JsonProperty("slt_end_date")
    private String sltEndDate;// 结束日期

    public String getSltStartDate() {
        return sltStartDate;
    }

    public void setSltStartDate(String sltStartDate) {
        this.sltStartDate = sltStartDate;
    }

    public String getSltEndDate() {
        return sltEndDate;
    }

    public void setSltEndDate(String sltEndDate) {
        this.sltEndDate = sltEndDate;
    }
}
