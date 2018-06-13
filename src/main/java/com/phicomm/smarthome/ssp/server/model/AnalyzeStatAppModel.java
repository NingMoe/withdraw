package com.phicomm.smarthome.ssp.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author fujiang.mao
 * @date 2017-10-13
 * @version V1.0
 * @Description tbl_stat_app实体类
 */
public class AnalyzeStatAppModel {

    @JsonProperty("id")
    private long id;// 自增长id

    @JsonProperty("connect_date_ts")
    private int connectDateTs;// 用户连接打赏网络日期

    @JsonProperty("user_mac")
    private String userMac;// 用户手机mac地址

    @JsonProperty("user_ip")
    private String userIp;// 用户手机ip地址

    @JsonProperty("login_url_cnt")
    private int loginUrlCnt;// 用户访问登录url地址的次数

    @JsonProperty("unifiedorder_url_cnt")
    private int unifiedorderUrlCnt;// 用户访问下单url的次数

    @JsonProperty("handle_withdraw_url_cnt")
    private int handleWithdrawUrlCnt;// 用户访问提现url的次数

    @JsonProperty("income_detail_url_cnt")
    private int incomeDetailUrlCnt;// 用户访问零钱明细url的次数

    @JsonProperty("chkwifistate_url_cnt")
    private int chkwifistateUrlCnt;// 用户选择金额url的次数

    @JsonProperty("wifi_ok_cnt")
    private int wifiOkCnt;// 用户点击打赏成功次数

    @JsonProperty("wifi_err_cnt")
    private int wifiErrCnt;// 用户点击打赏失败次数

    @JsonProperty("wifi_total_cnt")
    private int wifiTotalCnt;// 用户点击打赏总次数

    @JsonProperty("withdraw_ok_cnt")
    private int withdrawOkCnt;// 用户提现成功次数

    @JsonProperty("withdraw_err_cnt")
    private int withdrawErrCnt;// 用户提现失败次数

    @JsonProperty("withdraw_total_cnt")
    private int withdrawTotalCnt;// 用户提现总次数

    @JsonProperty("income_details_ok_cnt")
    private int incomeDetailsOkCnt;// 用户点击零钱明细成功次数

    @JsonProperty("income_details_err_cnt")
    private int incomeDetailsErrCnt;// 用户点击零钱明细失败次数

    @JsonProperty("inco_details_total_cnt")
    private int incoDetailsTotalCnt;// 用户点击零钱明细总次数

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setConnectDateTs(int connectDateTs) {
        this.connectDateTs = connectDateTs;
    }

    public int getConnectDateTs() {
        return connectDateTs;
    }

    public void setUserMac(String userMac) {
        this.userMac = userMac;
    }

    public String getUserMac() {
        return userMac;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setLoginUrlCnt(int loginUrlCnt) {
        this.loginUrlCnt = loginUrlCnt;
    }

    public int getLoginUrlCnt() {
        return loginUrlCnt;
    }

    public void setUnifiedorderUrlCnt(int unifiedorderUrlCnt) {
        this.unifiedorderUrlCnt = unifiedorderUrlCnt;
    }

    public int getUnifiedorderUrlCnt() {
        return unifiedorderUrlCnt;
    }

    public void setHandleWithdrawUrlCnt(int handleWithdrawUrlCnt) {
        this.handleWithdrawUrlCnt = handleWithdrawUrlCnt;
    }

    public int getHandleWithdrawUrlCnt() {
        return handleWithdrawUrlCnt;
    }

    public void setIncomeDetailUrlCnt(int incomeDetailUrlCnt) {
        this.incomeDetailUrlCnt = incomeDetailUrlCnt;
    }

    public int getIncomeDetailUrlCnt() {
        return incomeDetailUrlCnt;
    }

    public void setChkwifistateUrlCnt(int chkwifistateUrlCnt) {
        this.chkwifistateUrlCnt = chkwifistateUrlCnt;
    }

    public int getChkwifistateUrlCnt() {
        return chkwifistateUrlCnt;
    }

    public void setWifiOkCnt(int wifiOkCnt) {
        this.wifiOkCnt = wifiOkCnt;
    }

    public int getWifiOkCnt() {
        return wifiOkCnt;
    }

    public void setWifiErrCnt(int wifiErrCnt) {
        this.wifiErrCnt = wifiErrCnt;
    }

    public int getWifiErrCnt() {
        return wifiErrCnt;
    }

    public void setWifiTotalCnt(int wifiTotalCnt) {
        this.wifiTotalCnt = wifiTotalCnt;
    }

    public int getWifiTotalCnt() {
        return wifiTotalCnt;
    }

    public void setWithdrawOkCnt(int withdrawOkCnt) {
        this.withdrawOkCnt = withdrawOkCnt;
    }

    public int getWithdrawOkCnt() {
        return withdrawOkCnt;
    }

    public void setWithdrawErrCnt(int withdrawErrCnt) {
        this.withdrawErrCnt = withdrawErrCnt;
    }

    public int getWithdrawErrCnt() {
        return withdrawErrCnt;
    }

    public void setWithdrawTotalCnt(int withdrawTotalCnt) {
        this.withdrawTotalCnt = withdrawTotalCnt;
    }

    public int getWithdrawTotalCnt() {
        return withdrawTotalCnt;
    }

    public void setIncomeDetailsOkCnt(int incomeDetailsOkCnt) {
        this.incomeDetailsOkCnt = incomeDetailsOkCnt;
    }

    public int getIncomeDetailsOkCnt() {
        return incomeDetailsOkCnt;
    }

    public void setIncomeDetailsErrCnt(int incomeDetailsErrCnt) {
        this.incomeDetailsErrCnt = incomeDetailsErrCnt;
    }

    public int getIncomeDetailsErrCnt() {
        return incomeDetailsErrCnt;
    }

    public void setIncoDetailsTotalCnt(int incoDetailsTotalCnt) {
        this.incoDetailsTotalCnt = incoDetailsTotalCnt;
    }

    public int getIncoDetailsTotalCnt() {
        return incoDetailsTotalCnt;
    }

}
