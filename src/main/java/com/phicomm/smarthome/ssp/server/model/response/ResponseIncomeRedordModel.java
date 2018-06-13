package com.phicomm.smarthome.ssp.server.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseIncomeRedordModel {

    private String uid;

    @JsonProperty("phone")
    private String iphone;

    @JsonProperty("reward_time")
    private String rewardTime;

    @JsonProperty("wx_order_id")
    private String wxOrderId;

    @JsonProperty("ip_address")
    private String ipAddress;

    @JsonProperty("mac_address")
    private String macAddress;

    @JsonProperty("use_duration")
    private Double useDuration;

    @JsonProperty("reward_money")
    private String rewardMoney;

    @JsonProperty("user_income")
    private String userIncome;

    @JsonProperty("company_income")
    private String companyIncome;

    private long buyTime;
    
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getIphone() {
        return iphone;
    }

    public void setIphone(String iphone) {
        this.iphone = iphone;
    }

    public String getRewardTime() {
        return rewardTime;
    }

    public void setRewardTime(String rewardTime) {
        this.rewardTime = rewardTime;
    }

    public String getWxOrderId() {
        return wxOrderId;
    }

    public void setWxOrderId(String wxOrderId) {
        this.wxOrderId = wxOrderId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public Double getUseDuration() {
        return useDuration;
    }

    public void setUseDuration(Double useDuration) {
        this.useDuration = useDuration;
    }

    public String getRewardMoney() {
        return rewardMoney;
    }

    public void setRewardMoney(String rewardMoney) {
        this.rewardMoney = rewardMoney;
    }

    public String getUserIncome() {
        return userIncome;
    }

    public void setUserIncome(String userIncome) {
        this.userIncome = userIncome;
    }

    public String getCompanyIncome() {
        return companyIncome;
    }

    public void setCompanyIncome(String companyIncome) {
        this.companyIncome = companyIncome;
    }

    public long getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(long buyTime) {
        this.buyTime = buyTime;
    }

}
