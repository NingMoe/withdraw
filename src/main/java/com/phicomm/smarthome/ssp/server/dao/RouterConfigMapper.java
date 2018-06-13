package com.phicomm.smarthome.ssp.server.dao;

import com.phicomm.smarthome.ssp.server.model.SwRouterConfigModel;
import com.phicomm.smarthome.ssp.server.model.dao.SwRouterConfigDaoModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by xiangrong.ke on 2017/7/7.
 */
public interface RouterConfigMapper {

    @Select("select config_version,router_id,rom_version,model,is_forbidden from sw_router_config limit 1")
    @Results({
            @Result(property = "configVersion", column = "config_version"),
            @Result(property = "routerId", column = "router_id"),
            @Result(property = "isForbidden", column = "is_forbidden"),
            @Result(property = "portalUrl", column = "portal_url"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "updateTime", column = "update_time"),
            @Result(property = "status", column = "status"),
            @Result(property = "model", column = "model"),
            @Result(property = "romVersion", column = "rom_version")
    })
    public List<SwRouterConfigDaoModel> queryRouterConfig();

    @Update(" REPLACE INTO sw_router_config (config_version,router_id,is_forbidden,portal_url,create_time,update_time,status,model,rom_version) " +
            " VALUES (#{model.configVersion},#{model.routerId},#{model.isForbidden},#{model.portalUrl},#{model.createTime},#{model.updateTime}, " +
            " #{model.status},#{model.model},#{model.romVersion}) ")
    public int updateRouterConfig(@Param("model") SwRouterConfigDaoModel model);
}
