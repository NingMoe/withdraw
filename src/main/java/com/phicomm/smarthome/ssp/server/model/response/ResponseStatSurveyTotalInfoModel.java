package com.phicomm.smarthome.ssp.server.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.phicomm.smarthome.ssp.server.model.BaseResponseModel;
import com.phicomm.smarthome.ssp.server.util.NumberUtils;

@SuppressWarnings("serial")
public class ResponseStatSurveyTotalInfoModel extends BaseResponseModel {

    @JsonProperty("total_user")
    private int totalUser;// 累计用户

    @JsonProperty("total_router")
    private int totalRouter;// 累计路由器数

    @JsonProperty("total_sw_user")
    private int totalSwUser;// 累计打赏人数

    @JsonProperty("total_sw_count")
    private int totalSwCount;// 累计打赏次数

    @JsonProperty("total_sw_income")
    private long totalSwIncome;// 累计打赏金额

    @JsonProperty("show_total_sw_income")
    private String showTotalSwIncome;// 展示累计打赏金额

    @JsonProperty("total_user_income")
    private long totalUserIncome;// 累计用户收益

    @JsonProperty("show_total_user_income")
    private String showTotalUserIncome;// 展示累计用户收益

    @JsonProperty("total_company_income")
    private long totalCompanyIncome;// 累计公司收益

    @JsonProperty("show_total_company_income")
    private String showTotalCompanyIncome;// 展示累计公司收益

    public int getTotalUser() {
        return totalUser;
    }

    public void setTotalUser(int totalUser) {
        this.totalUser = totalUser;
    }

    public int getTotalRouter() {
        return totalRouter;
    }

    public void setTotalRouter(int totalRouter) {
        this.totalRouter = totalRouter;
    }

    public int getTotalSwUser() {
        return totalSwUser;
    }

    public void setTotalSwUser(int totalSwUser) {
        this.totalSwUser = totalSwUser;
    }

    public int getTotalSwCount() {
        return totalSwCount;
    }

    public void setTotalSwCount(int totalSwCount) {
        this.totalSwCount = totalSwCount;
    }

    public long getTotalSwIncome() {
        return totalSwIncome;
    }

    public void setTotalSwIncome(long totalSwIncome) {
        this.totalSwIncome = totalSwIncome;
    }

    public long getTotalUserIncome() {
        return totalUserIncome;
    }

    public void setTotalUserIncome(long totalUserIncome) {
        this.totalUserIncome = totalUserIncome;
    }

    public long getTotalCompanyIncome() {
        return totalCompanyIncome;
    }

    public void setTotalCompanyIncome(long totalCompanyIncome) {
        this.totalCompanyIncome = totalCompanyIncome;
    }

    public String getShowTotalSwIncome() {
        return NumberUtils.changeF2Y(totalSwIncome + "");
    }

    public void setShowTotalSwIncome(String showTotalSwIncome) {
        this.showTotalSwIncome = showTotalSwIncome;
    }

    public String getShowTotalUserIncome() {
        return NumberUtils.changeF2Y(totalUserIncome + "");
    }

    public void setShowTotalUserIncome(String showTotalUserIncome) {
        this.showTotalUserIncome = showTotalUserIncome;
    }

    public String getShowTotalCompanyIncome() {
        return NumberUtils.changeF2Y(totalCompanyIncome + "");
    }

    public void setShowTotalCompanyIncome(String showTotalCompanyIncome) {
        this.showTotalCompanyIncome = showTotalCompanyIncome;
    }

}
