package com.phicomm.smarthome.ssp.server.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.phicomm.smarthome.ssp.server.model.SwPortalPictureModel;

public interface PortalMapper {

    @Insert("insert into sw_portal_picture(img_url,device_name,effective_time,create_time,update_time,status) "
            + "values(#{model.imgUrl},#{model.deviceName},#{model.effectiveTime},#{model.createTime},#{model.updateTime},"
            + "#{model.status}) ")
    int addImgInfo(@Param("model") SwPortalPictureModel model);
    
    @Delete("delete from sw_portal_picture "
            + "where device_name=#{model.deviceName} and status=0")
    int removeAfterEftImgInfo(@Param("model") SwPortalPictureModel model);

    @Delete("UPDATE `sw_portal_picture` SET STATUS = -1, update_time=#{update_time} " + "WHERE img_url=#{img_url}")
    int removeImgInfo(@Param("img_url") String imgUrl, @Param("update_time") long updateTime);

    @Select("SELECT img_url, effective_time, device_name FROM `sw_portal_picture` "
            + "WHERE device_name=#{device_name} " + "ORDER BY STATUS, update_time DESC LIMIT 1")
    @Results({ @Result(property = "imgUrl", column = "img_url"),
            @Result(property = "effectiveTime", column = "effective_time"),
            @Result(property = "deviceName", column = "device_name") })
    SwPortalPictureModel queryPortal(@Param("device_name") String deviceName);

    @Select("SELECT COUNT(0) FROM `sw_portal_picture` WHERE device_name=#{model.deviceName} AND STATUS=0")
    int getIneffectiveImgCount(@Param("model") SwPortalPictureModel model);

    @Delete("UPDATE sw_portal_picture "
            + "SET img_url=#{model.imgUrl}, effective_time=#{model.effectiveTime}, create_time=#{model.createTime}, "
            + "update_time=#{model.updateTime} " + "WHERE device_name=#{model.deviceName} AND STATUS=0")
    int updateImgInfo(@Param("model") SwPortalPictureModel model);

    @Select("SELECT img_url,device_name,effective_time FROM `sw_portal_picture` WHERE STATUS=0")
    @Results({ @Result(property = "imgUrl", column = "img_url"),
            @Result(property = "deviceName", column = "device_name"),
            @Result(property = "effectiveTime", column = "effective_time") })
    List<SwPortalPictureModel> getIneffectiveImg();

    @Update("UPDATE sw_portal_picture SET STATUS=1, update_time=#{model.updateTime} "
            + "WHERE img_url=#{model.imgUrl} AND STATUS=0")
    void updateIneffectiveImg(@Param("model") SwPortalPictureModel model);
}
