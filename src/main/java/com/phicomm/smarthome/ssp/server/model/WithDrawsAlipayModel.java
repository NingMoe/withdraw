package com.phicomm.smarthome.ssp.server.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

/**
 * 支付宝提现申请对应数据库表sw_user_with_draws_aliplay
 *
 * @author xiangrong.ke
 *
 */
@Entity
@Table(name = "sw_user_with_draws_alipay")
@ApiModel(value = "WithDrawsAlipayModel", description = "提现管理后台实体表")

public class WithDrawsAlipayModel extends BaseResponseModel{

    @ApiModelProperty(value = "主键ID")
    @Column(name = "id")
    @JsonProperty("num")
    private long id;

    @ApiModelProperty(value = "斐讯用户标识")
    @Column(name = "uid")
    @JsonProperty("uid")
    private String uid;

    @ApiModelProperty(value = "提现金额")
    @Column(name = "cost")
    @JsonProperty("money_num")
    private String cost;

    @ApiModelProperty(value = "支付宝账号")
    @Column(name = "alipay_account")
    @JsonProperty("alipay_account")
    private String alipayAccount;

    @ApiModelProperty(value = "订单编号")
    @Column(name = "order_id")
    @JsonProperty("order_num")
    private String orderId;

    @ApiModelProperty(value = "提现用户手机号")
    @Column(name = "phone")
    @JsonProperty("mobile_num")
    private String phone;

    @ApiModelProperty(value = "提现用户昵称")
    @Column(name = "nick_name")
    @JsonProperty("user_name")
    private String nickName;

    @ApiModelProperty(value = "提现申请时间")
    @Column(name = "create_time")
    @JsonProperty("apply_time")
    private long createTime;

    @ApiModelProperty(value = "更新时间")
    @Column(name = "update_time")
    @JsonIgnore
    private long updateTime;

    @ApiModelProperty(value = "后台提现时间")
    @Column(name = "withdraw_time")
    @JsonProperty("success_time")
    private long withdrawTime;

    @ApiModelProperty(value = "提现状态：成功为0，失败为1")
    @JsonProperty("withdraw_state")
    @Column(name = "status")
    private int status;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getAlipayAccount() {
        return alipayAccount;
    }

    public void setAlipayAccount(String alipayAccount) {
        this.alipayAccount = alipayAccount;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public long getWithdrawTime() {
        return withdrawTime;
    }

    public void setWithdrawTime(long withdrawTime) {
        this.withdrawTime = withdrawTime;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

}
