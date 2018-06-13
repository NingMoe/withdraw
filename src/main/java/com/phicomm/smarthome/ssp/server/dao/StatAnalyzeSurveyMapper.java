package com.phicomm.smarthome.ssp.server.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.phicomm.smarthome.ssp.server.model.StatAppModel;
import com.phicomm.smarthome.ssp.server.model.TblGeneralDetailModel;
import com.phicomm.smarthome.ssp.server.model.TblStatSurveyModel;
import com.phicomm.smarthome.ssp.server.model.request.RequestStatAppModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseBaseModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseStatSurveyTotalInfoModel;

public interface StatAnalyzeSurveyMapper {
    
    @Select("SELECT detail_index AS str_id,detail_index_name AS str_value FROM `tbl_general_detail`")
    @Results({
    	@Result(property = "strId",column = "str_id"),
    	@Result(property = "strValue",column = "str_value")
    })
    List<ResponseBaseModel> getSurveySltIndexChilds();
    
    @Select("SELECT ${model.indexChildColumn} as stat_category_value,logger_date_ts FROM `tbl_stat_survey` "
    		+ "WHERE logger_date_ts>=#{model.sltStartDateTs} AND logger_date_ts<=#{model.sltEndDateTs} ")
    @Results({
    	@Result(property = "statCategoryValue",column = "stat_category_value"),
    	@Result(property = "connectDateTs",column = "logger_date_ts")
    })
    List<StatAppModel> statSurveyByDay(@Param("model") RequestStatAppModel model);
    
    @Select("SELECT IFNULL(SUM(${model.indexChildColumn}),0) FROM `tbl_stat_survey` "
    		+ "WHERE logger_date_ts>=#{model.sltStartDateTs} AND logger_date_ts<=#{model.sltEndDateTs} ")
    long statSurveyByTimestamp(@Param("model") RequestStatAppModel model);
    
    @Select("SELECT detail_index_name, detail_column_name FROM `tbl_general_detail` "
    		+ "WHERE detail_index=#{index_child}")
    @Results({
    	@Result(property = "detailIndexName",column = "detail_index_name"),
    	@Result(property = "detailColumnName",column = "detail_column_name")
    })
    TblGeneralDetailModel getSurveyIndexChildInfoById(@Param("index_child") int indexChild);
    
    @Select("select * from tbl_stat_survey "
    		+ "where logger_date_ts>=#{model.sltStartDateTs} AND logger_date_ts<=#{model.sltEndDateTs}")
    @Results({
    	@Result(property = "loggerDateTs",column = "logger_date_ts"),
    	@Result(property = "newUser",column = "new_user"),
    	@Result(property = "newRouter",column = "new_router"),
    	@Result(property = "swUserCount",column = "sw_user_count"),
    	@Result(property = "swCount",column = "sw_count"),
    	@Result(property = "swMoney",column = "sw_money"),
    	@Result(property = "userIncome",column = "user_income"),
    	@Result(property = "companyIncome",column = "company_income"),
    	@Result(property = "totalUser",column = "total_user"),
    	@Result(property = "totalRouter",column = "total_router"),
    	@Result(property = "totalSwUser",column = "total_sw_user"),
    	@Result(property = "totalSwCount",column = "total_sw_count"),
    	@Result(property = "totalSwIncome",column = "total_sw_income"),
    	@Result(property = "totalUserIncome",column = "total_user_income"),
    	@Result(property = "totalCompanyIncome",column = "total_company_income")
    })
    List<TblStatSurveyModel> statSurveyDataList(@Param("model") RequestStatAppModel model);
    
    @Select("SELECT IFNULL(SUM(new_user),0) AS new_user, IFNULL(SUM(new_router),0) AS new_user, "
    		+ "IFNULL(SUM(sw_user_count),0) AS sw_user_count, IFNULL(SUM(sw_count),0) AS sw_count, "
    		+ "IFNULL(SUM(sw_money),0) AS sw_money, IFNULL(SUM(user_income),0) AS user_income, "
    		+ "IFNULL(SUM(company_income),0) AS company_income "
    		+ "FROM `tbl_stat_survey` "
    		+ "where logger_date_ts>=#{model.sltStartDateTs} AND logger_date_ts<=#{model.sltEndDateTs}")
    @Results({
    	@Result(property = "loggerDateTs",column = "logger_date_ts"),
    	@Result(property = "newUser",column = "new_user"),
    	@Result(property = "newRouter",column = "new_router"),
    	@Result(property = "swUserCount",column = "sw_user_count"),
    	@Result(property = "swCount",column = "sw_count"),
    	@Result(property = "swMoney",column = "sw_money"),
    	@Result(property = "userIncome",column = "user_income"),
    	@Result(property = "companyIncome",column = "company_income")
    })
    TblStatSurveyModel statSurveyDataByDateTS(@Param("model") RequestStatAppModel model);
    
    @Select("SELECT * FROM `tbl_stat_survey` "
    		+ "where logger_date_ts=#{model.loggerDateTs}")
    @Results({
    	@Result(property = "loggerDateTs",column = "logger_date_ts"),
    	@Result(property = "totalUser",column = "total_user"),
    	@Result(property = "totalRouter",column = "total_router"),
    	@Result(property = "totalSwUser",column = "total_sw_user"),
    	@Result(property = "totalSwCount",column = "total_sw_count"),
    	@Result(property = "totalSwIncome",column = "total_sw_income"),
    	@Result(property = "totalUserIncome",column = "total_user_income"),
    	@Result(property = "totalCompanyIncome",column = "total_company_income")
    })
    TblStatSurveyModel statSurveyDataByDate(@Param("model") RequestStatAppModel model);
    
    @Select("SELECT * FROM `tbl_stat_survey` "
    		+ "where logger_date_ts=#{logger_date_ts}")
    @Results({
    	@Result(property = "totalUser",column = "total_user"),
    	@Result(property = "totalRouter",column = "total_router"),
    	@Result(property = "totalSwUser",column = "total_sw_user"),
    	@Result(property = "totalSwCount",column = "total_sw_count"),
    	@Result(property = "totalSwIncome",column = "total_sw_income"),
    	@Result(property = "totalUserIncome",column = "total_user_income"),
    	@Result(property = "totalCompanyIncome",column = "total_company_income")
    })
    ResponseStatSurveyTotalInfoModel statSurveyTotalByDate(@Param("logger_date_ts") int loggerDateTs);
    
}
