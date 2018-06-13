package com.phicomm.smarthome.ssp.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author fujiang.mao
 * @date 2017-10-24
 * @version V1.0
 * @Description tbl_general_detail实体类
 */
public class TblGeneralDetailModel {

    @JsonProperty("detail_index")
    private byte detailIndex;// 统计内容项标识

    @JsonProperty("detail_index_name")
    private String detailIndexName;// 统计内容项名称

    @JsonProperty("detail_column_name")
    private String detailColumnName;// 字段名

    public void setDetailIndex(byte detailIndex) {
        this.detailIndex = detailIndex;
    }

    public byte getDetailIndex() {
        return detailIndex;
    }

    public void setDetailIndexName(String detailIndexName) {
        this.detailIndexName = detailIndexName;
    }

    public String getDetailIndexName() {
        return detailIndexName;
    }

    public void setDetailColumnName(String detailColumnName) {
        this.detailColumnName = detailColumnName;
    }

    public String getDetailColumnName() {
        return detailColumnName;
    }

}
