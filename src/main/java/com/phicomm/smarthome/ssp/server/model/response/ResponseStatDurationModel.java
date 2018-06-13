package com.phicomm.smarthome.ssp.server.model.response;

public class ResponseStatDurationModel {

    public String date;

    public String linkTimeDesc;

    public int linkSwCount;

    public String linkPercent;

    public String useTimeDesc;

    public int useSwCount;

    public String usePercent;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getLinkSwCount() {
        return linkSwCount;
    }

    public void setLinkSwCount(int linkSwCount) {
        this.linkSwCount = linkSwCount;
    }

    public String getLinkPercent() {
        return linkPercent;
    }

    public void setLinkPercent(String linkPercent) {
        this.linkPercent = linkPercent;
    }

    public String getUsePercent() {
        return usePercent;
    }

    public void setUsePercent(String usePercent) {
        this.usePercent = usePercent;
    }

    public int getUseSwCount() {
        return useSwCount;
    }

    public void setUseSwCount(int useSwCount) {
        this.useSwCount = useSwCount;
    }

    public String getLinkTimeDesc() {
        return linkTimeDesc;
    }

    public void setLinkTimeDesc(String linkTimeDesc) {
        this.linkTimeDesc = linkTimeDesc;
    }

    public String getUseTimeDesc() {
        return useTimeDesc;
    }

    public void setUseTimeDesc(String useTimeDesc) {
        this.useTimeDesc = useTimeDesc;
    }

}
