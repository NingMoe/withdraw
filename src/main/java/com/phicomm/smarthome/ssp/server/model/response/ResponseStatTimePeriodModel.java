package com.phicomm.smarthome.ssp.server.model.response;

public class ResponseStatTimePeriodModel {

    private String dayBegin;//时间段起始日期

    private String dayEnd;//时间段结束日期

    private String uid;// 用户标识

    private String nickname;//昵称

    private String portrailUrl;// 头像

    private String income;// 时段收入

    private int rank;// 排名

    private String totalIncome;//总收入

    public String getDayBegin() {
        return dayBegin;
    }

    public void setDayBegin(String dayBegin) {
        this.dayBegin = dayBegin;
    }

    public String getDayEnd() {
        return dayEnd;
    }

    public void setDayEnd(String dayEnd) {
        this.dayEnd = dayEnd;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPortrailUrl() {
        return portrailUrl;
    }

    public void setPortrailUrl(String portrailUrl) {
        this.portrailUrl = portrailUrl;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(String totalIncome) {
        this.totalIncome = totalIncome;
    }

    @Override
    public String toString() {
        return "ResponseStatTimePeriodModel{" +
                "dayBegin='" + dayBegin + '\'' +
                ", dayEnd='" + dayEnd + '\'' +
                ", uid='" + uid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", portrailUrl='" + portrailUrl + '\'' +
                ", income='" + income + '\'' +
                ", rank=" + rank +
                ", totalIncome='" + totalIncome + '\'' +
                '}';
    }
}
