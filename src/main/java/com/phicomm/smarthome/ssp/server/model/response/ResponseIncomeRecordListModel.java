package com.phicomm.smarthome.ssp.server.model.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.phicomm.smarthome.ssp.server.model.BaseResponseModel;

@SuppressWarnings("serial")
public class ResponseIncomeRecordListModel extends BaseResponseModel {

    @JsonProperty("income_record_list")
    private List<ResponseIncomeRedordModel> incomeRecordList;

    @JsonProperty("total_count")
    private int totalCount;

    public List<ResponseIncomeRedordModel> getIncomeRecordList() {
        return incomeRecordList;
    }

    public void setIncomeRecordList(List<ResponseIncomeRedordModel> incomeRecordList) {
        this.incomeRecordList = incomeRecordList;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

}
