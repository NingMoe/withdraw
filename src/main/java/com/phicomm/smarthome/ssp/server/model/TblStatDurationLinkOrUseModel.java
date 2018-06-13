package com.phicomm.smarthome.ssp.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author fujiang.mao
 * @date 2017-10-26
 * @version V1.0
 * @Description tbl_stat_duration_link、tbl_stat_duration_use实体类
 */
public class TblStatDurationLinkOrUseModel {

    @JsonProperty("index_child")
    private byte indexChild;// 时长分段标识

    @JsonProperty("index_child_desc")
    private String indexChildDesc;// 时长分段描述

    @JsonProperty("index_child_min_val")
    private int indexChildMinVal;// 时长分段最小值，单位：秒

    @JsonProperty("index_child_max_val")
    private int indexChildMaxVal;// 时长分段最大值，单位：秒

    public void setIndexChild(byte indexChild) {
        this.indexChild = indexChild;
    }

    public byte getIndexChild() {
        return indexChild;
    }

    public void setIndexChildDesc(String indexChildDesc) {
        this.indexChildDesc = indexChildDesc;
    }

    public String getIndexChildDesc() {
        return indexChildDesc;
    }

    public void setIndexChildMinVal(int indexChildMinVal) {
        this.indexChildMinVal = indexChildMinVal;
    }

    public int getIndexChildMinVal() {
        return indexChildMinVal;
    }

    public void setIndexChildMaxVal(int indexChildMaxVal) {
        this.indexChildMaxVal = indexChildMaxVal;
    }

    public int getIndexChildMaxVal() {
        return indexChildMaxVal;
    }

}
