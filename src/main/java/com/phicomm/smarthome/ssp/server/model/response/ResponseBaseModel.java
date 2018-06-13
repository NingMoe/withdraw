package com.phicomm.smarthome.ssp.server.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseBaseModel {

    @JsonProperty("str_id")
    private String strId;

    @JsonProperty("str_value")
    private String strValue;

    public String getStrId() {
        return strId;
    }

    public void setStrId(String strId) {
        this.strId = strId;
    }

    public String getStrValue() {
        return strValue;
    }

    public void setStrValue(String strValue) {
        this.strValue = strValue;
    }

    public ResponseBaseModel() {
    }

    public ResponseBaseModel(String strId, String strValue) {
        this.strId = strId;
        this.strValue = strValue;
    }

}
