package com.phicomm.smarthome.ssp.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author fujiang.mao
 * @date 2018-01-09
 * @version V1.0
 * @Description sw_router实体类
 */
public class SwRouterModel {

    @JsonProperty("id")
    private long id;// 自增长id

    @JsonProperty("router_ip")
    private String routerIp;// 路由器lan口ip

    @JsonProperty("router_mac")
    private String routerMac;// 路由器mac地址

    @JsonProperty("model")
    private String model;// 路由器型号

    @JsonProperty("rom_version")
    private String romVersion;// 固件版本

    @JsonProperty("create_time")
    private long createTime;// 创建时间

    @JsonProperty("update_time")
    private long updateTime;// 更新时间

    @JsonProperty("status")
    private byte status;// 状态 0 正常 -1 删除

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setRouterIp(String routerIp) {
        this.routerIp = routerIp;
    }

    public String getRouterIp() {
        return routerIp;
    }

    public void setRouterMac(String routerMac) {
        this.routerMac = routerMac;
    }

    public String getRouterMac() {
        return routerMac;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getModel() {
        return model;
    }

    public void setRomVersion(String romVersion) {
        this.romVersion = romVersion;
    }

    public String getRomVersion() {
        return romVersion;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public byte getStatus() {
        return status;
    }

}
