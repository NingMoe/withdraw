package com.phicomm.smarthome.ssp.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author zhengwang.li
 * @date 2018-05-04
 * @version V1.0
 * @Description sw_problem_feedback实体类
 */
public class SwProblemFeedbackModel {

    @JsonProperty("id")
    private long id;// 自增长id

    private long feedbackTime;// 反馈日期

    @JsonProperty("feedback_time")
    private String feedbackTimeStr; // 反馈日期，用于显示

    private long startTime; // 开始时间

    private long endTime; // 结束时间

    @JsonProperty("scene_type")
    private int sceneType;// 场景类型，1代表"支付"和2代表"使用"

    @JsonProperty("reward_amount")
    private String rewardAmount;// 打赏金额

    @JsonProperty("purchase_time")
    private String purchaseTime;// 购买时长

    @JsonProperty("problem_type")
    private int problemType;// 问题类型,1代表"网络环境未能通过安全验证， 请稍后再试"，2代表"商家参数格式有误，请联系",3代表"商家存在未配置参数，请联系商家解决",4代表"无法跳转微信支付",5代表"网速太差",6代表"付款后不能上网",7代表"其他"

    @JsonProperty("broadband_operator")
    private String broadbandOperator;// 宽带运行商

    @JsonProperty("phone_model_version")
    private String phoneModelVersion;// 手机型号及其版本

    @JsonProperty("browser_system_version")
    private String browserSystemVersion;// 浏览器系统及其版本

    @JsonProperty("feedback_content")
    private String feedbackContent;// 反馈内容

    @JsonProperty("img_url")
    private String imgUrl;// 图片url

    @JsonProperty("contact_way")
    private String contactWay;// 联系方式

    private int starePage; // 当前页
    
    private int endPage; // 页码大小

    public int getStarePage() {
        return starePage;
    }

    public void setStarePage(int starePage) {
        this.starePage = starePage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public void setId(long id) {
        this.id=id;
    } 

    public long getId() {
        return id;
    } 

    public void setFeedbackTime(long feedbackTime) {
        this.feedbackTime=feedbackTime;
    } 

    public long getFeedbackTime() {
        return feedbackTime;
    } 

    public void setSceneType(int sceneType) {
        this.sceneType=sceneType;
    } 

    public int getSceneType() {
        return sceneType;
    } 

    public void setRewardAmount(String rewardAmount) {
        this.rewardAmount=rewardAmount;
    } 

    public String getRewardAmount() {
        return rewardAmount;
    } 

    public void setPurchaseTime(String purchaseTime) {
        this.purchaseTime=purchaseTime;
    } 

    public String getPurchaseTime() {
        return purchaseTime;
    } 

    public void setProblemType(int problemType) {
        this.problemType=problemType;
    } 

    public int getProblemType() {
        return problemType;
    } 

    public void setBroadbandOperator(String broadbandOperator) {
        this.broadbandOperator=broadbandOperator;
    } 

    public String getBroadbandOperator() {
        return broadbandOperator;
    } 

    public void setPhoneModelVersion(String phoneModelVersion) {
        this.phoneModelVersion=phoneModelVersion;
    } 

    public String getPhoneModelVersion() {
        return phoneModelVersion;
    } 

    public void setBrowserSystemVersion(String browserSystemVersion) {
        this.browserSystemVersion=browserSystemVersion;
    } 

    public String getBrowserSystemVersion() {
        return browserSystemVersion;
    } 

    public void setFeedbackContent(String feedbackContent) {
        this.feedbackContent=feedbackContent;
    } 

    public String getFeedbackContent() {
        return feedbackContent;
    } 

    public void setImgUrl(String imgUrl) {
        this.imgUrl=imgUrl;
    } 

    public String getImgUrl() {
        return imgUrl;
    } 

    public void setContactWay(String contactWay) {
        this.contactWay=contactWay;
    } 

    public String getContactWay() {
        return contactWay;
    }

    public String getFeedbackTimeStr() {
        return feedbackTimeStr;
    }

    public void setFeedbackTimeStr(String feedbackTimeStr) {
        this.feedbackTimeStr = feedbackTimeStr;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
}

