package com.phicomm.smarthome.ssp.server.model.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by zhengwang.li on 2018/4/17.
 */
public class HomePageBannerDaoModel implements Serializable{

    private static final long serialVersionUID = -7323032147518722172L;

    @JsonProperty("id")
    private long id;// 自增长id

    @JsonProperty("backend_pc_banner_img_url")
    private String backendPcBannerImgUrl;// pc端banner图片地址,用于管理后台显示

    @JsonProperty("backend_pc_ground_img_url")
    private String backendPcGroundImgUrl;// pc端落地图片,用于管理后台显示

    @JsonProperty("backend_mobile_banner_img_url")
    private String backendMobileBannerImgUrl;// 移动端后台banner图片地址

    @JsonProperty("backend_mobile_ground_img_url")
    private String backendMobileGroundImgUrl;// 移动端后台落地图片地址

    @JsonProperty("pc_banner_img_name")
    private String pcBannerImgName; // pc端banner图片的名称
    
    @JsonProperty("pc_ground_img_name")
    private String pcGroundImgName; // pc端落地图片的名称
    
    @JsonProperty("mobile_banner_img_name")
    private String mobileBannerImgName; // 移动端banner图片的名称
    
    @JsonProperty("mobile_ground_img_name")
    private String mobileGroundImgName; // 移动端落地图片的名称

    @JsonProperty("action")
    private int action;// 动作，1为无，2为跳转到图片

    @JsonProperty("sequence")
    private int sequence;// banner图片排序字段

    @JsonProperty("real_name")
    private String lastOptUserName;// 最后操作人姓名

    @JsonProperty("lastOptUid")
    private long lastOptUid;// 最后操作人UID

    @JsonIgnore
    private int status;// 状态 0.添加，1.为发布为删除,2为删除未发布, -1删除

    @JsonIgnore
    private long createTime;// 创建时间

    @JsonIgnore
    private long updateTime;// 更新时间

    @JsonProperty("update_time")
    private String updateTimeStr;// 更新时间
    
    public void setId(long id) {
        this.id=id;
    }

    public long getId() {
        return id;
    }

    public void setBackendPcBannerImgUrl(String backendPcBannerImgUrl) {
        this.backendPcBannerImgUrl=backendPcBannerImgUrl;
    }

    public String getBackendPcBannerImgUrl() {
        return backendPcBannerImgUrl;
    }

    public void setBackendPcGroundImgUrl(String backendPcGroundImgUrl) {
        this.backendPcGroundImgUrl=backendPcGroundImgUrl;
    }

    public String getBackendPcGroundImgUrl() {
        return backendPcGroundImgUrl;
    }

    public void setBackendMobileBannerImgUrl(String backendMobileBannerImgUrl) {
        this.backendMobileBannerImgUrl=backendMobileBannerImgUrl;
    }

    public String getBackendMobileBannerImgUrl() {
        return backendMobileBannerImgUrl;
    }

    public void setBackendMobileGroundImgUrl(String backendMobileGroundImgUrl) {
        this.backendMobileGroundImgUrl=backendMobileGroundImgUrl;
    }

    public String getBackendMobileGroundImgUrl() {
        return backendMobileGroundImgUrl;
    }

    public void setAction(int action) {
        this.action=action;
    }

    public int getAction() {
        return action;
    }

    public void setSequence(int sequence) {
        this.sequence=sequence;
    }

    public int getSequence() {
        return sequence;
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

    public String getUpdateTimeStr() {
        return updateTimeStr;
    }

    public void setUpdateTimeStr(String updateTimeStr) {
        this.updateTimeStr = updateTimeStr;
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
