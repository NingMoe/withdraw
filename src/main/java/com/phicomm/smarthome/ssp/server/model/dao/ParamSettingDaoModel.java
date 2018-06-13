package com.phicomm.smarthome.ssp.server.model.dao;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by xiangrong.ke on 2017/8/23.
 */
public class ParamSettingDaoModel {

    private int busid;

    private String module;

    private String fieldParam;

    private String value;

    private int status;

    public int getBusid() {
        return busid;
    }

    public void setBusid(int busid) {
        this.busid = busid;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getFieldParam() {
        return fieldParam;
    }

    public void setFieldParam(String fieldParam) {
        this.fieldParam = fieldParam;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
