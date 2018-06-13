package com.phicomm.smarthome.ssp.server.model.response;

public class ResponseCloudUserDeviceModel {

    private String devMac;// 如：FF:FF:FF:FF:FF:FF

    private String devModel;// 如：K2

    private String devName;// 如：家路由

    private String devRemoteIP;// 如：222.73.156.131

    private String devRemotePort;// 如：30005

    private String netType;// 包括公网（public）、私网（private）和UPnP（upnp）

    private String online;// 在线：1；不在线：0

    public String getDevMac() {
        return devMac;
    }

    public void setDevMac(String devMac) {
        this.devMac = devMac;
    }

    public String getDevModel() {
        return devModel;
    }

    public void setDevModel(String devModel) {
        this.devModel = devModel;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public String getDevRemoteIP() {
        return devRemoteIP;
    }

    public void setDevRemoteIP(String devRemoteIP) {
        this.devRemoteIP = devRemoteIP;
    }

    public String getDevRemotePort() {
        return devRemotePort;
    }

    public void setDevRemotePort(String devRemotePort) {
        this.devRemotePort = devRemotePort;
    }

    public String getNetType() {
        return netType;
    }

    public void setNetType(String netType) {
        this.netType = netType;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

}
