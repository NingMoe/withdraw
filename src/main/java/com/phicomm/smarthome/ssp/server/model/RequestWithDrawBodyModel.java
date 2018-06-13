package com.phicomm.smarthome.ssp.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by xiangrong.ke on 2017/6/21.
 */
public class RequestWithDrawBodyModel implements Serializable {

    @JsonProperty("mobile_num")
    private String mobileNum;

    @JsonProperty("applytime_begin")
    private String applytimeBegin;

    @JsonProperty("applytime_end")
    private String applytimeEnd;

    @JsonProperty("optime_begin")
    private String optimeBegin;

    @JsonProperty("optime_end")
    private String optimeEnd;

    @JsonProperty("querytimestamp")
    private String queryTimestamp;

    @JsonProperty("page_size")
    private String pageSize;

    @JsonProperty("cur_page")
    private String curPage;


    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

    public String getApplytimeBegin() {
        return applytimeBegin;
    }

    public void setApplytimeBegin(String applytimeBegin) {
        this.applytimeBegin = applytimeBegin;
    }

    public String getApplytimeEnd() {
        return applytimeEnd;
    }

    public void setApplytimeEnd(String applytimeEnd) {
        this.applytimeEnd = applytimeEnd;
    }

    public String getOptimeBegin() {
        return optimeBegin;
    }

    public void setOptimeBegin(String optimeBegin) {
        this.optimeBegin = optimeBegin;
    }

    public String getOptimeEnd() {
        return optimeEnd;
    }

    public void setOptimeEnd(String optimeEnd) {
        this.optimeEnd = optimeEnd;
    }

    public String getQueryTimestamp() {
        return queryTimestamp;
    }

    public void setQueryTimestamp(String queryTimestamp) {
        this.queryTimestamp = queryTimestamp;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getCurPage() {
        return curPage;
    }

    public void setCurPage(String curPage) {
        this.curPage = curPage;
    }
}
