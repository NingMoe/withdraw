package com.phicomm.smarthome.ssp.server.model.response;

import java.util.List;

import com.phicomm.smarthome.ssp.server.model.BaseResponseModel;

@SuppressWarnings("serial")
public class ResponseStatAppInfoModel extends BaseResponseModel {

    private List<String> lineChartXs;

    private List<Number> lineChartYs;

    private String indexChildName;

    public List<String> getLineChartXs() {
        return lineChartXs;
    }

    public void setLineChartXs(List<String> lineChartXs) {
        this.lineChartXs = lineChartXs;
    }

    public List<Number> getLineChartYs() {
        return lineChartYs;
    }

    public void setLineChartYs(List<Number> lineChartYs) {
        this.lineChartYs = lineChartYs;
    }

    public String getIndexChildName() {
        return indexChildName;
    }

    public void setIndexChildName(String indexChildName) {
        this.indexChildName = indexChildName;
    }

}
