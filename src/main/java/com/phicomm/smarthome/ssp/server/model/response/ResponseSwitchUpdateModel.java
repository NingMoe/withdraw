package com.phicomm.smarthome.ssp.server.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.phicomm.smarthome.model.ldapsys.common.BaseResponseModel;

public class ResponseSwitchUpdateModel extends BaseResponseModel {
    
    @JsonProperty("affect_row")
    int affectRow;

    public int getAffectRow() {
        return affectRow;
    }

    public void setAffectRow(int affectRow) {
        this.affectRow = affectRow;
    }
}
