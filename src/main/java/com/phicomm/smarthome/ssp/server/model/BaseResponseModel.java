package com.phicomm.smarthome.ssp.server.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.phicomm.smarthome.ssp.server.consts.Const.ResponseStatus;
import com.phicomm.smarthome.ssp.server.util.MyResponseutils;

/**
 * 返回给客户端Json数据基类，返回的数据必须包含ret_code和ret_msg数据
 * 
 * @author rongwei.huang
 *
 */
@SuppressWarnings("serial")

public class BaseResponseModel implements Serializable {
    @JsonProperty("ret_code")
    private int retCode;

    @JsonProperty("ret_msg")
    private String retMsg;

    @JsonProperty("extend_msg")
    private String extendMsg;

    public String getExtendMsg() {
        return extendMsg;
    }

    public void setExtendMsg(String extendMsg) {
        this.extendMsg = extendMsg;
    }

    public BaseResponseModel() {
        retCode = ResponseStatus.STATUS_OK;
        retMsg = MyResponseutils.parseMsg(ResponseStatus.STATUS_OK);
    }

    public int getRetCode() {
        return retCode;
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
        this.retMsg = MyResponseutils.parseMsg(retCode);
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }
}
