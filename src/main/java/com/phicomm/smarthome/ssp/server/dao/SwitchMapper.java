package com.phicomm.smarthome.ssp.server.dao;

import java.util.List;

import com.phicomm.smarthome.ssp.server.model.dao.SwRouterConfigDaoModel;
import org.apache.ibatis.annotations.*;

import com.phicomm.smarthome.ssp.server.model.SwRouterConfigModel;

/**
 * Created by xiangrong.ke on 2017/7/7.
 */
public interface SwitchMapper {
    @Update("update sw_router_config set is_forbidden = #{model.isForbidden} "
            + "where config_version = #{model.configVersion} and router_id = #{model.routerId} "
            + "and rom_version = #{model.romVersion} and model = #{model.model}")
    public int updateSwitch(@Param("model") SwRouterConfigModel model);

    @Update("update sw_router_config set is_forbidden = #{isForbidden}, config_version = #{newConfigVersion} where 1 = 1")
    public int updateSwitchAll(@Param("isForbidden") int isForbidden,
            @Param("newConfigVersion") String newConfigVersion);

    @Select("select config_version,router_id,rom_version,model,is_forbidden from sw_router_config")
    @Results({ @Result(property = "configVersion", column = "config_version"),
            @Result(property = "routerId", column = "router_id"),
            @Result(property = "romVersion", column = "rom_version"), @Result(property = "model", column = "model"),
            @Result(property = "isForbidden", column = "is_forbidden") })
    public List<SwRouterConfigModel> querySwitch();

}
