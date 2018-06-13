package com.phicomm.smarthome.ssp.server.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.phicomm.smarthome.ssp.server.model.ConfigConnOrUseTrModel;
import com.phicomm.smarthome.ssp.server.model.StatAppModel;
import com.phicomm.smarthome.ssp.server.model.TblStatDurationLinkOrUseModel;
import com.phicomm.smarthome.ssp.server.model.TblStatDurationUserDetailModel;
import com.phicomm.smarthome.ssp.server.model.request.RequestStatAppModel;
import com.phicomm.smarthome.ssp.server.model.request.RequestStatRewardDurationModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseBaseModel;

public interface StatAnalyzeRewardMapper {

//    @Select("SELECT user_slt_index AS str_id,user_slt_index_name AS str_value FROM `tbl_sw_user_slt_index`")
//    @Results({ @Result(property = "strId", column = "str_id"), @Result(property = "strValue", column = "str_value") })
//    List<ResponseBaseModel> getRewardSltIndexs();
//
//    @Select("SELECT stat_category_value,logger_date_ts FROM `tbl_stat_reward_user` "
//            + "WHERE category_type=#{model.sltIndex} AND device_type=#{model.swDevice} AND kpi_type=#{model.indexChild} "
//            + "AND logger_date_ts>=#{model.sltStartDateTs} AND logger_date_ts<=#{model.sltEndDateTs} ")
//    @Results({ @Result(property = "statCategoryValue", column = "stat_category_value"),
//            @Result(property = "connectDateTs", column = "logger_date_ts") })
//    List<StatAppModel> statRewardUserByDay(@Param("model") RequestStatAppModel model);

    @Select("SELECT stat_date,${statColumn} AS stat_val FROM `stat_client` "
            + "WHERE stat_date IN(${datesplit}) "
            + "AND user_agent=#{model.swDevice} AND stat_type=#{model.sltIndex} "
            + "ORDER BY stat_date ASC")
    @Results({ @Result(property = "statCategoryValue", column = "stat_category_value"),
            @Result(property = "connectDateTs", column = "logger_date_ts") })
    List<Map<String, Object>> statRewardUserByDates(@Param("datesplit")String datesplit, @Param("statColumn") String statColumn, @Param("model") RequestStatAppModel model);
    
    @Select("SELECT IFNULL(SUM(${statColumn}),0) AS stat_val FROM `stat_client` "
            + "WHERE stat_date IN(${datesplit}) "
            + "AND user_agent=#{model.swDevice} AND stat_type=#{model.sltIndex}")
    int statRewardUsers(@Param("datesplit")String datesplit, @Param("statColumn") String statColumn, @Param("model") RequestStatAppModel model);
    
    @Select("SELECT stat_date,SUM(${statColumn}) AS stat_val FROM `stat_client` "
            + "WHERE stat_date IN(${datesplit}) "
            + "AND stat_type=#{model.sltIndex} "
            + "GROUP BY stat_date "
            + "ORDER BY stat_date ASC")
    @Results({ @Result(property = "statCategoryValue", column = "stat_category_value"),
        @Result(property = "connectDateTs", column = "logger_date_ts") })
    List<Map<String, Object>> statRewardUserAllDeviceByDates(@Param("datesplit")String datesplit, @Param("statColumn") String statColumn, @Param("model") RequestStatAppModel model);
    
    @Select("SELECT IFNULL(SUM(${statColumn}),0) AS stat_val FROM `stat_client` "
            + "WHERE stat_date IN(${datesplit}) "
            + "AND stat_type=#{model.sltIndex}")
    int statRewardUserAllDevices(@Param("datesplit")String datesplit, @Param("statColumn") String statColumn, @Param("model") RequestStatAppModel model);

    @Select("SELECT SUM(stat_category_value) AS stat_category_value,logger_date_ts " + "FROM `tbl_stat_reward_user` "
            + "WHERE category_type=#{model.sltIndex} AND kpi_type=#{model.indexChild} "
            + "AND logger_date_ts>=#{model.sltStartDateTs} AND logger_date_ts<=#{model.sltEndDateTs} "
            + "GROUP BY logger_date_ts")
    @Results({ @Result(property = "statCategoryValue", column = "stat_category_value"),
            @Result(property = "connectDateTs", column = "logger_date_ts") })
    List<StatAppModel> statAllRewardUserByDay(@Param("model") RequestStatAppModel model);

    @Select("SELECT IFNULL(SUM(stat_category_value),0) FROM `tbl_stat_reward_user` "
            + "WHERE category_type=#{model.sltIndex} AND device_type=#{model.swDevice} AND kpi_type=#{model.indexChild} "
            + "AND logger_date_ts>=#{model.sltStartDateTs} AND logger_date_ts<=#{model.sltEndDateTs} ")
    int getRewardPvOrUvValBySltIndexTS(@Param("model") RequestStatAppModel model);

    @Select("SELECT IFNULL(SUM(stat_category_value),0) FROM `tbl_stat_reward_user` "
            + "WHERE category_type=#{model.sltIndex} AND kpi_type=#{model.indexChild} "
            + "AND logger_date_ts>=#{model.sltStartDateTs} AND logger_date_ts<=#{model.sltEndDateTs} ")
    int getAllRewardPvOrUvValBySltIndexTS(@Param("model") RequestStatAppModel model);

    @Select("SELECT IFNULL(SUM(stat_category_value),0) FROM `tbl_stat_reward_user` "
            + "WHERE category_type=#{model.sltIndex} AND device_type=#{model.swDevice} AND kpi_type=#{model.indexChild} "
            + "AND logger_date_ts>=#{model.sltStartDateTs} AND logger_date_ts<=#{model.sltEndDateTs} ")
    int statRewardUserByTimestamp(@Param("model") RequestStatAppModel model);

    @Select("SELECT IFNULL(SUM(stat_category_value),0) FROM `tbl_stat_reward_user` "
            + "WHERE category_type=#{model.sltIndex} AND kpi_type=#{model.indexChild} "
            + "AND logger_date_ts>=#{model.sltStartDateTs} AND logger_date_ts<=#{model.sltEndDateTs} ")
    int statAllRewardUserByTimestamp(@Param("model") RequestStatAppModel model);
//
//    @Select("SELECT device_index_name FROM `tbl_sw_user_slt_device` " + "WHERE device_index=#{device_index}")
//    String getSwDeviceNameById(@Param("device_index") int deviceIndex);
//
//    @Select("SELECT user_slt_index_name FROM `tbl_sw_user_slt_index` " + "WHERE user_slt_index=#{user_slt_index}")
//    String getSwUserIndexNameById(@Param("user_slt_index") int userSltIndex);
//
//    @Select("SELECT * FROM ${tableName}")
//    @Results({ @Result(property = "indexChild", column = "index_child"),
//            @Result(property = "indexChildDesc", column = "index_child_desc"),
//            @Result(property = "indexChildMinVal", column = "index_child_min_val"),
//            @Result(property = "indexChildMaxVal", column = "index_child_max_val") })
//    List<TblStatDurationLinkOrUseModel> getStatDurationLinkOrUseInfo(@Param("tableName") String tableName);

    @Select("SELECT * FROM ${tableName}")
    @Results({ @Result(property = "rangeId", column = "range_id"),
            @Result(property = "rangeStart", column = "range_start"),
            @Result(property = "rangeEnd", column = "range_end"),
            @Result(property = "rangeText", column = "range_text") })
    List<ConfigConnOrUseTrModel> getStatDurationLinkOrUseInfo(@Param("tableName") String tableName);

    @Select("SELECT * FROM tbl_stat_duration_use " + "where index_child=#{index_child}")
    @Results({ @Result(property = "indexChild", column = "index_child"),
            @Result(property = "indexChildDesc", column = "index_child_desc"),
            @Result(property = "indexChildMinVal", column = "index_child_min_val"),
            @Result(property = "indexChildMaxVal", column = "index_child_max_val") })
    TblStatDurationLinkOrUseModel getStatDurationUseInfoById(@Param("index_child") byte indexChild);
//    
//    @Select("SELECT COUNT(*) FROM `tbl_stat_duration_user_detail` "
//            + "WHERE order_date_ts>=#{model.startTimeTs} AND order_date_ts<=#{model.endTimeTs} "
//            + "AND ${model.timeColumn}>=#{model.indexChildMinVal} AND ${model.timeColumn}<#{model.indexChildMaxVal}")
//    int getLinkOrUseTimeCountByTsAndScope(@Param("model") RequestStatRewardDurationModel model);

    @Select("SELECT IFNULL(SUM(device_total),0) AS total FROM ${tableName} "
            + "WHERE stat_date=#{model.durationDate} and range_id=#{model.rangeId}")
    int getLinkOrUseTimeCountByTsAndScope(@Param("tableName") String tableName,@Param("model") RequestStatRewardDurationModel model);
//
//    @Select("SELECT COUNT(*) FROM `tbl_stat_duration_user_detail` "
//            + "WHERE order_date_ts>=#{model.startTimeTs} AND order_date_ts<=#{model.endTimeTs}")
//    int getLinkOrUseTimeCountByTs(@Param("model") RequestStatRewardDurationModel model);

    @Select("SELECT IFNULL(SUM(device_total),0) AS total FROM ${tableName} "
            + "WHERE stat_date=#{model.durationDate}")
    int getLinkOrUseTimeCountByTs(@Param("tableName") String tableName,@Param("model") RequestStatRewardDurationModel model);

//    @Select("SELECT * FROM `tbl_stat_duration_user_detail` "
//            + "WHERE order_date_ts>=#{model.sltStartDateTs} AND order_date_ts<=#{model.sltEndDateTs} "
//            + "ORDER BY order_date_ts " + "limit #{model.start},#{model.pageSize}")
//    @Results({ @Result(property = "orderDateTs", column = "order_date_ts"),
//            @Result(property = "swMoney", column = "sw_money"), @Result(property = "linkTime", column = "link_time"),
//            @Result(property = "userTime", column = "user_time"),
//            @Result(property = "linkDevice", column = "link_device"),
//            @Result(property = "ipAddress", column = "ip_address"),
//            @Result(property = "routerMac", column = "router_mac"),
//            @Result(property = "deviceMac", column = "device_mac"), @Result(property = "orderId", column = "order_id"),
//            @Result(property = "guestId", column = "guest_id"),
//            @Result(property = "deviceType", column = "device_type") })
//    List<TblStatDurationUserDetailModel> geTblStatDurationUserDetails(@Param("model") RequestStatAppModel model);
    
    @Select("SELECT * FROM `stat_order_dtl` "
            + "WHERE stat_date in(${dateSplite}) "
            + "ORDER BY stat_date " + "limit #{model.start},#{model.pageSize}")
    @Results({ @Result(property = "date", column = "stat_date"),
        @Result(property = "swMoney", column = "order_amount"), @Result(property = "linkTime", column = "conn_time"),
        @Result(property = "userTime", column = "use_time"),
        @Result(property = "linkDevice", column = "link_device"),
        @Result(property = "ipAddress", column = "router_ip"),
        @Result(property = "routerMac", column = "router_mac"),
        @Result(property = "deviceMac", column = "device_mac"), @Result(property = "orderId", column = "order_id"),
        @Result(property = "guestId", column = "guest_id"),
        @Result(property = "deviceType", column = "user_agent") })
    List<TblStatDurationUserDetailModel> geTblStatDurationUserDetails(@Param("dateSplite") String dateSplite, @Param("model") RequestStatAppModel model);

    @Select("SELECT * FROM `stat_order_dtl` "
            + "WHERE stat_date in(${dateSplite}) "
            + "ORDER BY stat_date")
    @Results({ @Result(property = "date", column = "stat_date"),
        @Result(property = "swMoney", column = "order_amount"), @Result(property = "linkTime", column = "conn_time"),
        @Result(property = "userTime", column = "use_time"),
        @Result(property = "linkDevice", column = "link_device"),
        @Result(property = "ipAddress", column = "router_ip"),
        @Result(property = "routerMac", column = "router_mac"),
        @Result(property = "deviceMac", column = "device_mac"), @Result(property = "orderId", column = "order_id"),
        @Result(property = "guestId", column = "guest_id"),
        @Result(property = "deviceType", column = "user_agent") })
    List<TblStatDurationUserDetailModel> geAllTblStatDurationUserDetails(@Param("dateSplite") String dateSplite, @Param("model") RequestStatAppModel model);

    @Select("SELECT count(*) FROM `stat_order_dtl` "
            + "WHERE stat_date in(${dateSplite}) ")
    int geTblStatDurationUserDetailsCount(@Param("dateSplite") String dateSplite, @Param("model") RequestStatAppModel model);

}
