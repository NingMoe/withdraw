package com.phicomm.smarthome.ssp.server.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface HtDeployMapper {

    @Insert("REPLACE INTO `sw_service_param_setting`(busid,module,field_param,VALUE) "
            + "VALUES(#{busId},#{module},#{fieldParam},#{param_value})")
    int uploadServiceParam(@Param("busId") int busId, @Param("module") String module,
            @Param("fieldParam") String fieldParam, @Param("param_value") String paramValue);

    @Select("SELECT VALUE FROM  `sw_service_param_setting` "
            + "WHERE busid=#{busId} AND module=#{module} AND field_param=#{fieldParam}")
    String getServiceParam(@Param("busId") int busId, @Param("module") String module,
            @Param("fieldParam") String fieldParam);
}
