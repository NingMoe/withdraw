package com.phicomm.smarthome.ssp.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author fujiang.mao
 * @date 2017-08-28
 * @version V1.0
 * @Description sw_portal_picture实体类
 */
public class SwPortalPictureModel extends BaseResponseModel{

    @JsonProperty("id")
    private int id;

    @JsonProperty("img_url")
    private String imgUrl;// 图片地址

    @JsonProperty("device_name")
    private String deviceName;// 设备名称

    @JsonProperty("effective_time")
    private long effectiveTime;// 生效时间

    @JsonProperty("create_time")
    private long createTime;

    @JsonProperty("update_time")
    private long updateTime;

    @JsonProperty("status")
    private byte status;// 0:正常 -1:已删除

    private String effectiveTimeStr;

    public String getEffectiveTimeStr() {
        return effectiveTimeStr;
    }

    public void setEffectiveTimeStr(String effectiveTimeStr) {
        this.effectiveTimeStr = effectiveTimeStr;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public long getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(long effectiveTime) {
        this.effectiveTime = effectiveTime;
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
