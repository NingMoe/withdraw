package com.phicomm.smarthome.ssp.server.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseUserInfoModel {

    private String uid;

    @JsonProperty("phone")
    private String iphone;

    @JsonProperty("nick_name")
    private String phiNickname;

    @JsonProperty("use_start_time")
    private String useStartTime;

    @JsonProperty("total_income")
    private String totalIncome;

    @JsonProperty("current_balance")
    private String currentBalance;

    private String userMac;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPhiNickname() {
        return phiNickname;
    }

    public void setPhiNickname(String phiNickname) {
        this.phiNickname = phiNickname;
    }

    public String getUseStartTime() {
        return useStartTime;
    }

    public void setUseStartTime(String useStartTime) {
        this.useStartTime = useStartTime;
    }

    public String getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(String totalIncome) {
        this.totalIncome = totalIncome;
    }

    public String getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(String currentBalance) {
        this.currentBalance = currentBalance;
    }

    public String getIphone() {
        return iphone;
    }

    public void setIphone(String iphone) {
        this.iphone = iphone;
    }

    public String getUserMac() {
        return userMac;
    }

    public void setUserMac(String userMac) {
        this.userMac = userMac;
    }

}
