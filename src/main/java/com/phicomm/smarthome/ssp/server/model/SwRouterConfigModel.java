package com.phicomm.smarthome.ssp.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by xiangrong.ke on 2017/7/7.
 */
public class SwRouterConfigModel {
    @JsonProperty("config_version")
    private String configVersion;

    @JsonProperty("router_id")
    private long routerId;

    @JsonProperty("rom_version")
    private String romVersion;

    @JsonProperty("model")
    private String model;

    @JsonProperty("is_forbidden")
    private int isForbidden;

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
}
