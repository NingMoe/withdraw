package com.phicomm.smarthome.ssp.server.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MoveReqModel {
    
    @JsonProperty("up_id") 
    private int upId;
    
    @JsonProperty("up_sequence") 
    private int upSequence; 
    
    @JsonProperty("down_id") 
    private int downId;
    
    @JsonProperty("down_sequence") 
    private int downSequence;

    @JsonProperty("real_name") 
    private String realName;
    
    @JsonProperty("lastOptUid") 
    private Long lastOptUid;

    public int getUpId() {
        return upId;
    }

    public void setUpId(int upId) {
        this.upId = upId;
    }

    public int getUpSequence() {
        return upSequence;
    }

    public void setUpSequence(int upSequence) {
        this.upSequence = upSequence;
    }

    public int getDownId() {
        return downId;
    }

    public void setDownId(int downId) {
        this.downId = downId;
    }

    public int getDownSequence() {
        return downSequence;
    }

    public void setDownSequence(int downSequence) {
        this.downSequence = downSequence;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Long getLastOptUid() {
        return lastOptUid;
    }

    public void setLastOptUid(Long lastOptUid) {
        this.lastOptUid = lastOptUid;
    }
}
