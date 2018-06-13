package com.phicomm.smarthome.ssp.server.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.phicomm.smarthome.model.cache.BaseReqPageModel;

/**
 * fujiang.mao
 */
public class RequestStatRewardDurationModel extends BaseReqPageModel {

    @JsonProperty("sw_duration")
    private int swDuration;

    @JsonProperty("sw_duration_name")
    private String swDurationName;// 时长分布

    @JsonProperty("duration_date")
    private String durationDate;

    @JsonProperty("start_time_ts")
    private int startTimeTs;// 起始时间戳

    @JsonProperty("end_time_ts")
    private int endTimeTs;// 结束时间戳

    @JsonProperty("table_name")
    private String tableName;

    @JsonProperty("time_column")
    private String timeColumn;

    @JsonProperty("index_child_min_val")
    private int indexChildMinVal;// 时长分段最小值，单位：秒

    @JsonProperty("index_child_max_val")
    private int indexChildMaxVal;// 时长分段最大值，单位：秒
    
    @JsonProperty("range_id")
    private int rangeId;
    
    public int getRangeId() {
        return rangeId;
    }

    public void setRangeId(int rangeId) {
        this.rangeId = rangeId;
    }

    public int getSwDuration() {
        return swDuration;
    }

    public void setSwDuration(int swDuration) {
        this.swDuration = swDuration;
    }

    public String getSwDurationName() {
        return swDurationName;
    }

    public void setSwDurationName(String swDurationName) {
        this.swDurationName = swDurationName;
    }

    public String getDurationDate() {
        return durationDate;
    }

    public void setDurationDate(String durationDate) {
        this.durationDate = durationDate;
    }

    public int getStartTimeTs() {
        return startTimeTs;
    }

    public void setStartTimeTs(int startTimeTs) {
        this.startTimeTs = startTimeTs;
    }

    public int getEndTimeTs() {
        return endTimeTs;
    }

    public void setEndTimeTs(int endTimeTs) {
        this.endTimeTs = endTimeTs;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTimeColumn() {
        return timeColumn;
    }

    public void setTimeColumn(String timeColumn) {
        this.timeColumn = timeColumn;
    }

    public int getIndexChildMinVal() {
        return indexChildMinVal;
    }

    public void setIndexChildMinVal(int indexChildMinVal) {
        this.indexChildMinVal = indexChildMinVal;
    }

    public int getIndexChildMaxVal() {
        return indexChildMaxVal;
    }

    public void setIndexChildMaxVal(int indexChildMaxVal) {
        this.indexChildMaxVal = indexChildMaxVal;
    }

}
