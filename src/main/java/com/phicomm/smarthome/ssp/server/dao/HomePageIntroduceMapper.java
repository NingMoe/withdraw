package com.phicomm.smarthome.ssp.server.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.phicomm.smarthome.ssp.server.model.dao.HomePageIntroduceDaoModel;

public interface HomePageIntroduceMapper {

    @Select("SELECT COUNT(*) FROM sw_home_page_introduce WHERE status = 0")
    int countIntroduce();

    @Insert("INSERT INTO sw_home_page_introduce(pc_img_url,mobile_img_url,"
            + "pc_img_name,mobile_img_name,"
            + "last_opt_user_name,last_opt_uid,status,create_time,update_time) "
            + "VALUES(#{model.pcImgUrl},#{model.mobileImgUrl},"
            + "#{model.pcImgName},#{model.mobileImgName},"
            + "#{model.lastOptUserName},#{model.lastOptUid},#{model.status},#{model.createTime},#{model.updateTime})")
    int insertIntroduce(@Param("model") HomePageIntroduceDaoModel model);

    @Update("UPDATE sw_home_page_introduce SET pc_img_url=#{model.pcImgUrl},mobile_img_url=#{model.mobileImgUrl},"
            + "pc_img_name=#{model.pcImgName},mobile_img_name=#{model.mobileImgName},"
            + "last_opt_user_name=#{model.lastOptUserName},"
            + "last_opt_uid=#{model.lastOptUid},update_time=#{model.updateTime} WHERE id=#{model.id}")
    int updateIntroduce(@Param("model")HomePageIntroduceDaoModel model);

    @Select("SELECT * FROM sw_home_page_introduce WHERE status = 0")
    @Results({ @Result(property = "id", column = "id"), @Result(property = "pcImgUrl", column = "pc_img_url"),
        @Result(property = "mobileImgUrl", column = "mobile_img_url"),
        @Result(property = "pcImgName", column = "pc_img_name"),
        @Result(property = "mobileImgName", column = "mobile_img_name"),
        @Result(property = "lastOptUserName", column = "last_opt_user_name"),
        @Result(property = "lastOptUid", column = "last_opt_uid"), @Result(property = "status", column = "status"),
        @Result(property = "createTime", column = "create_time"),
        @Result(property = "updateTime", column = "update_time") })
    List<HomePageIntroduceDaoModel> getIntroduce();
}
