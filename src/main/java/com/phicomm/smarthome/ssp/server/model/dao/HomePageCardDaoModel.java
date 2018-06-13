package com.phicomm.smarthome.ssp.server.model.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by zhengwang.li on 2018/4/17.
 */
public class HomePageCardDaoModel implements Serializable{

    private static final long serialVersionUID = -6136643293719968916L;

    @JsonProperty("id")
    private long id;// 自增长id

    @JsonProperty("pc_img_url")
    private String pcImgUrl;// pc端图标地址url

    @JsonProperty("mobile_img_url")
    private String mobileImgUrl;// 移动端图标地址url

    @JsonProperty("pc_ground_img_url")
    private String pcGroundImgUrl;// pc端落地图片

    @JsonProperty("mobile_ground_img_url")
    private String mobileGroundImgUrl;// 移动端落地图片

    @JsonProperty("pc_banner_img_name")
    private String pcBannerImgName; // pc端banner图片的名称
    
    @JsonProperty("pc_ground_img_name")
    private String pcGroundImgName; // pc端落地图片的名称
    
    @JsonProperty("mobile_banner_img_name")
    private String mobileBannerImgName; // 移动端banner图片的名称
    
    @JsonProperty("mobile_ground_img_name")
    private String mobileGroundImgName; // 移动端落地图片的名称
    
    @JsonProperty("content")
    private String content;// 配文

    @JsonProperty("type")
    private int type;// 1代表左侧卡片，2代表右侧卡片

    @JsonProperty("real_name")
    private String lastOptUserName;// 最后操作人姓名

    @JsonProperty("lastOptUid")
    private long lastOptUid;// 最后操作人UID

    @JsonIgnore
    private int status;// 状态 0.未删除， -1删除

    @JsonIgnore
    private long createTime;// 创建时间
    
    @JsonIgnore
    private long updateTime;// 更新时间
    
    @JsonProperty("update_time")
    private String updateTimeStr; // 更新时间
    
    public String getUpdateTimeStr() {
        return updateTimeStr;
    }

    public void setUpdateTimeStr(String updateTimeStr) {
        this.updateTimeStr = updateTimeStr;
    }

    public void setId(long id) {
        this.id=id;
    }

    public long getId() {
        return id;
    }

    public void setPcImgUrl(String pcImgUrl) {
        this.pcImgUrl=pcImgUrl;
    }

    public String getPcImgUrl() {
        return pcImgUrl;
    }

    public void setMobileImgUrl(String mobileImgUrl) {
        this.mobileImgUrl=mobileImgUrl;
    }

    public String getMobileImgUrl() {
        return mobileImgUrl;
    }

    public void setPcGroundImgUrl(String pcGroundImgUrl) {
        this.pcGroundImgUrl=pcGroundImgUrl;
    }

    public String getPcGroundImgUrl() {
        return pcGroundImgUrl;
    }

    public void setMobileGroundImgUrl(String mobileGroundImgUrl) {
        this.mobileGroundImgUrl=mobileGroundImgUrl;
    }

    public String getMobileGroundImgUrl() {
        return mobileGroundImgUrl;
    }

    public void setContent(String content) {
        this.content=content;
    }

    public String getContent() {
        return content;
    }

    public void setType(int type) {
        this.type=type;
    }

    public int getType() {
        return type;
    }

    public void setLastOptUserName(String lastOptUserName) {
        this.lastOptUserName=lastOptUserName;
    }

    public String getLastOptUserName() {
        return lastOptUserName;
    }

    public void setLastOptUid(long lastOptUid) {
        this.lastOptUid=lastOptUid;
    }

    public long getLastOptUid() {
        return lastOptUid;
    }

    public void setStatus(int status) {
        this.status=status;
    }

    public int getStatus() {
        return status;
    }

    public void setCreateTime(long createTime) {
        this.createTime=createTime;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime=updateTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public String getPcBannerImgName() {
        return pcBannerImgName;
    }

    public void setPcBannerImgName(String pcBannerImgName) {
        this.pcBannerImgName = pcBannerImgName;
    }

    public String getPcGroundImgName() {
        return pcGroundImgName;
    }

    public void setPcGroundImgName(String pcGroundImgName) {
        this.pcGroundImgName = pcGroundImgName;
    }

    public String getMobileBannerImgName() {
        return mobileBannerImgName;
    }

    public void setMobileBannerImgName(String mobileBannerImgName) {
        this.mobileBannerImgName = mobileBannerImgName;
    }

    public String getMobileGroundImgName() {
        return mobileGroundImgName;
    }

    public void setMobileGroundImgName(String mobileGroundImgName) {
        this.mobileGroundImgName = mobileGroundImgName;
    }
}
