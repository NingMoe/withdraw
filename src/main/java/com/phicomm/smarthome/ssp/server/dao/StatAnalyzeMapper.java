package com.phicomm.smarthome.ssp.server.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.phicomm.smarthome.ssp.server.model.response.ResponseBaseModel;

public interface StatAnalyzeMapper {
    
//    @Select("SELECT viedoo_index AS str_id,viedoo_index_name AS str_value FROM `tbl_app_viedoo`")
//    @Results({
//        @Result(property = "strId",column = "str_id"),
//        @Result(property = "strValue",column = "str_value")
//	})
//    List<ResponseBaseModel> getVedioos();
    
    @Select("SELECT index_child_name FROM `tbl_index_child` "
    		+ "WHERE index_child=#{index_child}")
    String getIndexChildNameById(@Param("index_child") int indexChild);
    
    @Select("SELECT slt_index_name FROM `tbl_app_slt_index` "
    		+ "WHERE slt_index=#{slt_index}")
    String getSltIndexNameById(@Param("slt_index") int sltIndex);
    
}
