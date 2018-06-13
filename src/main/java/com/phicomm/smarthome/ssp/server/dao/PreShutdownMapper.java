package com.phicomm.smarthome.ssp.server.dao;

import com.phicomm.smarthome.ssp.server.model.dao.ParamSettingDaoModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by xiangrong.ke on 2017/8/23.
 */
public interface PreShutdownMapper {
    //更新配置项
    @Update(" replace into sw_service_param_setting " +
            " (busid,module,field_param,value) " +
            " values( #{model.busid}, #{model.module},#{model.fieldParam},#{model.value} ) ")
    public int updateSwitch(@Param("model") ParamSettingDaoModel model);

    //busid=1000表示sharedwifi业务，module=pre_shutdown_setting表示预关闭功能的设置,status = 0表示是有效的配置项
    @Select(" select busid,module,field_param,value from sw_service_param_setting " +
            " where busid = 1000 and module = 'pre_shutdown_setting' " +
            " and status = 0 ")
    @Results({
            @Result(property = "busid",column = "busid"),
            @Result(property = "module",column = "module"),
            @Result(property = "fieldParam",column = "field_param"),
            @Result(property = "value",column = "value")
    })
    public List<ParamSettingDaoModel> querySwitch();
}
