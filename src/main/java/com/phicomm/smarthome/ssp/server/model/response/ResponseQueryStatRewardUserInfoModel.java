package com.phicomm.smarthome.ssp.server.model.response;

import java.util.List;

import com.phicomm.smarthome.ssp.server.model.BaseResponseModel;

@SuppressWarnings("serial")
public class ResponseQueryStatRewardUserInfoModel extends BaseResponseModel {

    private List<ResponseBaseModel> sltIndexs;

    private List<ResponseBaseModel> swDevices;

    private List<ResponseBaseModel> veidoos;

    private List<ResponseBaseModel> sltIndexChilds;

    public List<ResponseBaseModel> getSltIndexs() {
        return sltIndexs;
    }

    public void setSltIndexs(List<ResponseBaseModel> sltIndexs) {
        this.sltIndexs = sltIndexs;
    }

    public List<ResponseBaseModel> getSwDevices() {
        return swDevices;
    }

    public void setSwDevices(List<ResponseBaseModel> swDevices) {
        this.swDevices = swDevices;
    }

    public List<ResponseBaseModel> getVeidoos() {
        return veidoos;
    }

    public void setVeidoos(List<ResponseBaseModel> veidoos) {
        this.veidoos = veidoos;
    }

    public List<ResponseBaseModel> getSltIndexChilds() {
        return sltIndexChilds;
    }

    public void setSltIndexChilds(List<ResponseBaseModel> sltIndexChilds) {
        this.sltIndexChilds = sltIndexChilds;
    }

}
