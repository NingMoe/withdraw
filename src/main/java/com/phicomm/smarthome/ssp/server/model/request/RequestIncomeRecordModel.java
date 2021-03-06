package com.phicomm.smarthome.ssp.server.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.phicomm.smarthome.model.cache.BaseReqPageModel;

/**
 * @author fujiang.mao
 * @date 2017-12-12
 * @version V1.0
 * @Description sw_user实体类
 */
public class RequestIncomeRecordModel extends BaseReqPageModel {

    @JsonProperty("phone")
    private String iphone;// 手机号

    @JsonProperty("reward_start_date")
    private String rewardStartDate;

    @JsonProperty("reward_end_date")
    private String rewardEndDate;
    
    private long startTimeTs;
    
    private long endTimeTs;

    public String getIphone() {
        return iphone;
    }

    public void setIphone(String iphone) {
        this.iphone = iphone;
    }

    public String getRewardStartDate() {
        return rewardStartDate;
    }

    public void setRewardStartDate(String rewardStartDate) {
        this.rewardStartDate = rewardStartDate;
    }

    public String getRewardEndDate() {
        return rewardEndDate;
    }

    public void setRewardEndDate(String rewardEndDate) {
        this.rewardEndDate = rewardEndDate;
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
