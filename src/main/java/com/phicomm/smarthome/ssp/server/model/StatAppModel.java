package com.phicomm.smarthome.ssp.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author fujiang.mao
 * @date 2017-10-16
 * @version V1.0
 * @Description tbl_stat_app实体类
 */
public class StatAppModel {

    @JsonProperty("id")
    private long id;// 自增长id

    @JsonProperty("connect_date_ts")
    private int connectDateTs;// 记录的时间戳（10位，秒级）

    @JsonProperty("category_type")
    private byte categoryType;// 统计指标分类类型，1共享wifi，2设置共享wifi，3编辑设备，4钱包，5零钱明细，6提现

    @JsonProperty("stat_category_value")
    private int statCategoryValue;// 统计指标对应的值，表示访问指标记录数

    @JsonProperty("kpi_type")
    private byte kpiType;// 指标统计项，1新增用户，2 pv，3 uv

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setConnectDateTs(int connectDateTs) {
        this.connectDateTs = connectDateTs;
    }

    public int getConnectDateTs() {
        return connectDateTs;
    }

    public void setCategoryType(byte categoryType) {
        this.categoryType = categoryType;
    }

    public byte getCategoryType() {
        return categoryType;
    }

    public void setStatCategoryValue(int statCategoryValue) {
        this.statCategoryValue = statCategoryValue;
    }

    public int getStatCategoryValue() {
        return statCategoryValue;
    }

    public void setKpiType(byte kpiType) {
        this.kpiType = kpiType;
    }

    public byte getKpiType() {
        return kpiType;
    }

}
