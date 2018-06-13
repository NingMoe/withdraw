package com.phicomm.smarthome.ssp.server.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.phicomm.smarthome.model.cache.BaseReqPageModel;

/**
 * fujiang.mao
 */
public class RequestStatAppModel extends BaseReqPageModel {

    @JsonProperty("slt_index")
    private int sltIndex;

    @JsonProperty("slt_index_name")
    private String sltIndexName;// 选择指标

    @JsonProperty("index_child")
    private int indexChild;

    @JsonProperty("index_child_column")
    private String indexChildColumn;

    @JsonProperty("index_child_name")
    private String indexChildName;// 指标统计项

    @JsonProperty("slt_start_date")
    private String sltStartDate;// 起始日期

    @JsonProperty("slt_end_date")
    private String sltEndDate;// 结束日期

    @JsonProperty("slt_start_date_ts")
    private int sltStartDateTs;// 起始日期时间戳

    @JsonProperty("slt_end_date_ts")
    private int sltEndDateTs;// 结束日期时间戳

    @JsonProperty("logger_date_ts")
    private int loggerDateTs;// 日期时间戳

    @JsonProperty("veidoo")
    private int veidoo;

    @JsonProperty("veidoo_name")
    private String veidooName;// 维度 日 周 月

    @JsonProperty("sw_device")
    private int swDevice;

    @JsonProperty("sw_device_name")
    private String swDeviceName;// 连接设备
    
    public int getSltIndex() {
        return sltIndex;
    }

    public void setSltIndex(int sltIndex) {
        this.sltIndex = sltIndex;
    }

    public String getSltIndexName() {
        return sltIndexName;
    }

    public void setSltIndexName(String sltIndexName) {
        this.sltIndexName = sltIndexName;
    }

    public int getIndexChild() {
        return indexChild;
    }

    public void setIndexChild(int indexChild) {
        this.indexChild = indexChild;
    }

    public String getIndexChildName() {
        return indexChildName;
    }

    public void setIndexChildName(String indexChildName) {
        this.indexChildName = indexChildName;
    }

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

    public int getSltStartDateTs() {
        return sltStartDateTs;
    }

    public void setSltStartDateTs(int sltStartDateTs) {
        this.sltStartDateTs = sltStartDateTs;
    }

    public int getSltEndDateTs() {
        return sltEndDateTs;
    }

    public void setSltEndDateTs(int sltEndDateTs) {
        this.sltEndDateTs = sltEndDateTs;
    }

    public int getVeidoo() {
        return veidoo;
    }

    public void setVeidoo(int veidoo) {
        this.veidoo = veidoo;
    }

    public String getVeidooName() {
        return veidooName;
    }

    public void setVeidooName(String veidooName) {
        this.veidooName = veidooName;
    }

    public int getSwDevice() {
        return swDevice;
    }

    public void setSwDevice(int swDevice) {
        this.swDevice = swDevice;
    }

    public String getSwDeviceName() {
        return swDeviceName;
    }

    public void setSwDeviceName(String swDeviceName) {
        this.swDeviceName = swDeviceName;
    }

    public String getIndexChildColumn() {
        return indexChildColumn;
    }

    public void setIndexChildColumn(String indexChildColumn) {
        this.indexChildColumn = indexChildColumn;
    }

    public int getLoggerDateTs() {
        return loggerDateTs;
    }

    public void setLoggerDateTs(int loggerDateTs) {
        this.loggerDateTs = loggerDateTs;
    }

}
