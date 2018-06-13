package com.phicomm.smarthome.ssp.server.model.dao;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by zhengwang.li on 2018/4/17.
 */
public class HomePageIntroduceDaoModel implements Serializable{

    private static final long serialVersionUID = -1449604371143007469L;

    @JsonProperty("id")
    private long id;// 自增长id

    @JsonProperty("pc_img_url")
    private String pcImgUrl;// pc客户端图片地址url

    @JsonProperty("mobile_img_url")
    private String mobileImgUrl;// 移动客户端图片地址url

    @JsonProperty("pc_img_name")
    private String pcImgName; // pc客户端图片名称
    
    @JsonProperty("mobile_img_name")
    private String mobileImgName; // 移动客户端图片名称
    
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
    private String updateTimeStr; // 更新时间，用于显示
    
    
    public String getUpdateTimeStr() {
        return updateTimeStr;
    }

    public void setUpdateTimeStr(String updateTimeStr) {
        this.updateTimeStr = updateTimeStr;
    }

    public String getPcImgName() {
        return pcImgName;
    }

    public void setPcImgName(String pcImgName) {
        this.pcImgName = pcImgName;
    }

    public String getMobileImgName() {
        return mobileImgName;
    }

    public void setMobileImgName(String mobileImgName) {
        this.mobileImgName = mobileImgName;
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

}
