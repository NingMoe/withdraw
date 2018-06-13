package com.phicomm.smarthome.ssp.server.model.response;

import java.util.List;

import com.phicomm.smarthome.ssp.server.model.BaseResponseModel;

@SuppressWarnings("serial")
public class ResponseQueryStatAppInfoModel extends BaseResponseModel {

    private List<ResponseBaseModel> sltIndexs;

    private List<ResponseBaseModel> sltIndexChilds;

    private List<ResponseBaseModel> veidoos;// 维度 1.日 2.周 3.月

    public List<ResponseBaseModel> getSltIndexs() {
        return sltIndexs;
    }

    public void setSltIndexs(List<ResponseBaseModel> sltIndexs) {
        this.sltIndexs = sltIndexs;
    }

    public List<ResponseBaseModel> getSltIndexChilds() {
        return sltIndexChilds;
    }

    public void setSltIndexChilds(List<ResponseBaseModel> sltIndexChilds) {
        this.sltIndexChilds = sltIndexChilds;
    }

    public List<ResponseBaseModel> getVeidoos() {
        return veidoos;
    }

    public void setVeidoos(List<ResponseBaseModel> veidoos) {
        this.veidoos = veidoos;
    }

}
