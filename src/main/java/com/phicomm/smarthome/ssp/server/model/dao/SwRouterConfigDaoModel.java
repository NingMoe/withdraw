package com.phicomm.smarthome.ssp.server.model.dao;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by xiangrong.ke on 2017/7/7.
 */
public class SwRouterConfigDaoModel {

    private String configVersion;

    private long routerId;

    private int isForbidden;

    private String portalUrl;

    private long createTime;

    private long updateTime;

    private int status;

    private String model;

    private String romVersion;

    public String getConfigVersion() {
        return configVersion;
    }

    public void setConfigVersion(String configVersion) {
        this.configVersion = configVersion;
    }

    public int getIsForbidden() {
        return isForbidden;
    }

    public void setIsForbidden(int isForbidden) {
        this.isForbidden = isForbidden;
    }

    public long getRouterId() {
        return routerId;
    }

    public void setRouterId(long routerId) {
        this.routerId = routerId;
    }

    public String getRomVersion() {
        return romVersion;
    }

    public void setRomVersion(String romVersion) {
        this.romVersion = romVersion;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPortalUrl() {
        return portalUrl;
    }

    public void setPortalUrl(String portalUrl) {
        this.portalUrl = portalUrl;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
