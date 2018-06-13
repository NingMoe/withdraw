package com.phicomm.smarthome.ssp.server.model.response;

public class ResponseStatAppModel {

    private String date;

    private String sltIndexName;

    private int newUser;

    private int pvVal;

    private int uvVal;

    private String swDeviceName;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSltIndexName() {
        return sltIndexName;
    }

    public void setSltIndexName(String sltIndexName) {
        this.sltIndexName = sltIndexName;
    }

    public int getNewUser() {
        return newUser;
    }

    public void setNewUser(int newUser) {
        this.newUser = newUser;
    }

    public int getPvVal() {
        return pvVal;
    }

    public void setPvVal(int pvVal) {
        this.pvVal = pvVal;
    }

    public int getUvVal() {
        return uvVal;
    }

    public void setUvVal(int uvVal) {
        this.uvVal = uvVal;
    }

    public String getSwDeviceName() {
        return swDeviceName;
    }

    public void setSwDeviceName(String swDeviceName) {
        this.swDeviceName = swDeviceName;
    }

}
