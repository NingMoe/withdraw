package com.phicomm.smarthome.ssp.server.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.phicomm.smarthome.model.cache.BaseReqPageModel;

/**
 * @author fujiang.mao
 * @date 2017-12-12
 * @version V1.0
 * @Description sw_user实体类
 */
public class RequestSwUserModel extends BaseReqPageModel {

    @JsonProperty("phone")
    private String iphone;// 手机号

    @JsonProperty("use_start_date")
    private String useStartDate;

    @JsonProperty("use_end_date")
    private String useEndDate;
    
    private long startTimeTs;
    
    private long endTimeTs;

    private int startIndex; // 数据库起始下标
    
    private int offset; // 偏移量
    
    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getIphone() {
        return iphone;
    }

    public void setIphone(String iphone) {
        this.iphone = iphone;
    }

    public String getUseStartDate() {
        return useStartDate;
    }

    public void setUseStartDate(String useStartDate) {
        this.useStartDate = useStartDate;
    }

    public String getUseEndDate() {
        return useEndDate;
    }

    public void setUseEndDate(String useEndDate) {
        this.useEndDate = useEndDate;
    }

    public long getStartTimeTs() {
        return startTimeTs;
    }

    public void setStartTimeTs(long startTimeTs) {
        this.startTimeTs = startTimeTs;
    }

    public long getEndTimeTs() {
        return endTimeTs;
    }

    public void setEndTimeTs(long endTimeTs) {
        this.endTimeTs = endTimeTs;
    }

}
