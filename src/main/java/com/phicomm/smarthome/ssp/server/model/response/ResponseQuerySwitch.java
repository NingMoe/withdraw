package com.phicomm.smarthome.ssp.server.model.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.phicomm.smarthome.ssp.server.model.BaseResponseModel;
import com.phicomm.smarthome.ssp.server.model.SwRouterConfigModel;

public class ResponseQuerySwitch extends BaseResponseModel {
    
    @JsonProperty("list")
    private List<SwRouterConfigModel> list;

    public List<SwRouterConfigModel> getList() {
        return list;
    }

    public void setList(List<SwRouterConfigModel> list) {
        this.list = list;
    }
}
