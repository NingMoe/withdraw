package com.phicomm.smarthome.ssp.server.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.phicomm.smarthome.ssp.server.model.StatAppModel;
import com.phicomm.smarthome.ssp.server.model.request.RequestStatAppModel;

public interface StatAnalyzeAppUserMapper {
    
    @Select("SELECT stat_category_value,connect_date_ts FROM `tbl_stat_app` "
    		+ "WHERE category_type=#{model.sltIndex} AND kpi_type=#{model.indexChild} "
    		+ "AND connect_date_ts>=#{model.sltStartDateTs} AND connect_date_ts<=#{model.sltEndDateTs} ")
    @Results({
        @Result(property = "statCategoryValue",column = "stat_category_value"),
        @Result(property = "connectDateTs",column = "connect_date_ts")
	})
    List<StatAppModel> statAppUserByDay(@Param("model") RequestStatAppModel model);
    
    @Select("SELECT stat_date,${statColumn} AS stat_val "
            + "FROM `stat_income` WHERE stat_date IN(${dates})")
    List<Map<String, Object>> getStatIncomeEveryDateByDates(@Param("dates")String dates, @Param("statColumn")String statColumn);
    
    @Select("SELECT stat_date,router_active AS stat_val "
            + "FROM `stat_router` WHERE stat_date IN(${dates})")
    List<Map<String, Object>> getStatRouterEveryDateByDates(@Param("dates")String dates);
    
    @Select("SELECT IFNULL(SUM(${statColumn}),0) AS stat_val "
            + "FROM `stat_income` WHERE stat_date IN(${dates})")
    int getStatIncomeValByDates(@Param("dates")String dates, @Param("statColumn")String statColumn);
    
    @Select("SELECT IFNULL(SUM(router_active),0) AS stat_val "
            + "FROM `stat_router` WHERE stat_date IN(${dates})")
    int getStatRouterValByDates(@Param("dates")String dates);
    
    @Select("SELECT IFNULL(SUM(stat_category_value),0) FROM `tbl_stat_app` "
    		+ "WHERE category_type=#{model.sltIndex} AND kpi_type=#{model.indexChild} "
    		+ "AND connect_date_ts>=#{model.sltStartDateTs} AND connect_date_ts<=#{model.sltEndDateTs} ")
    int getPvOrUvValBySltIndexTS(@Param("model") RequestStatAppModel model);
    
    @Select("SELECT IFNULL(SUM(stat_category_value),0) FROM `tbl_stat_app` "
    		+ "WHERE category_type=#{model.sltIndex} AND kpi_type=#{model.indexChild} "
    		+ "AND connect_date_ts>=#{model.sltStartDateTs} AND connect_date_ts<=#{model.sltEndDateTs} ")
    int statAppUserByTimestamp(@Param("model") RequestStatAppModel model);
    
}
