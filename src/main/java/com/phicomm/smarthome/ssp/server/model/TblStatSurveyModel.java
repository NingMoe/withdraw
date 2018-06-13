package com.phicomm.smarthome.ssp.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author fujiang.mao
 * @date 2017-10-24
 * @version V1.0
 * @Description tbl_stat_survey实体类
 */
public class TblStatSurveyModel {

    @JsonProperty("id")
    private long id;

    @JsonProperty("logger_date_ts")
    private int loggerDateTs;// 记录的时间戳（10位，秒级）

    @JsonProperty("new_user")
    private int newUser;// 新增用户

    @JsonProperty("new_router")
    private int newRouter;// 新增路由器

    @JsonProperty("sw_user_count")
    private int swUserCount;// 打赏人数

    @JsonProperty("sw_count")
    private int swCount;// 打赏次数

    @JsonProperty("sw_money")
    private long swMoney;// 打赏金额

    @JsonProperty("show_sw_money")
    private String showSwMoney;// 打赏金额

    @JsonProperty("user_income")
    private long userIncome;// 用户收益

    @JsonProperty("show_user_income")
    private String showUserIncome;// 用户收益

    @JsonProperty("company_income")
    private long companyIncome;// 公司收益

    @JsonProperty("show_company_income")
    private String showCompanyIncome;// 公司收益

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

    @JsonProperty("logger_date_str")
    private String loggerDateStr;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getLoggerDateTs() {
        return loggerDateTs;
    }

    public void setLoggerDateTs(int loggerDateTs) {
        this.loggerDateTs = loggerDateTs;
    }

    public int getNewUser() {
        return newUser;
    }

    public void setNewUser(int newUser) {
        this.newUser = newUser;
    }

    public int getNewRouter() {
        return newRouter;
    }

    public void setNewRouter(int newRouter) {
        this.newRouter = newRouter;
    }

    public int getSwUserCount() {
        return swUserCount;
    }

    public void setSwUserCount(int swUserCount) {
        this.swUserCount = swUserCount;
    }

    public int getSwCount() {
        return swCount;
    }

    public void setSwCount(int swCount) {
        this.swCount = swCount;
    }

    public long getSwMoney() {
        return swMoney;
    }

    public void setSwMoney(long swMoney) {
        this.swMoney = swMoney;
    }

    public long getUserIncome() {
        return userIncome;
    }

    public void setUserIncome(long userIncome) {
        this.userIncome = userIncome;
    }

    public long getCompanyIncome() {
        return companyIncome;
    }

    public void setCompanyIncome(long companyIncome) {
        this.companyIncome = companyIncome;
    }

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

    public String getLoggerDateStr() {
        return loggerDateStr;
    }

    public void setLoggerDateStr(String loggerDateStr) {
        this.loggerDateStr = loggerDateStr;
    }

    public String getShowSwMoney() {
        return showSwMoney;
    }

    public void setShowSwMoney(String showSwMoney) {
        this.showSwMoney = showSwMoney;
    }

    public String getShowUserIncome() {
        return showUserIncome;
    }

    public void setShowUserIncome(String showUserIncome) {
        this.showUserIncome = showUserIncome;
    }

    public String getShowCompanyIncome() {
        return showCompanyIncome;
    }

    public void setShowCompanyIncome(String showCompanyIncome) {
        this.showCompanyIncome = showCompanyIncome;
    }

    public String getShowTotalSwIncome() {
        return showTotalSwIncome;
    }

    public void setShowTotalSwIncome(String showTotalSwIncome) {
        this.showTotalSwIncome = showTotalSwIncome;
    }

    public String getShowTotalUserIncome() {
        return showTotalUserIncome;
    }

    public void setShowTotalUserIncome(String showTotalUserIncome) {
        this.showTotalUserIncome = showTotalUserIncome;
    }

    public String getShowTotalCompanyIncome() {
        return showTotalCompanyIncome;
    }

    public void setShowTotalCompanyIncome(String showTotalCompanyIncome) {
        this.showTotalCompanyIncome = showTotalCompanyIncome;
    }

}
