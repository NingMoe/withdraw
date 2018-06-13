package com.phicomm.smarthome.ssp.server.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.phicomm.smarthome.ssp.server.model.dao.HomePageCardDaoModel;

public interface HomePageCardMapper {

    @Insert("INSERT INTO sw_home_page_card(pc_img_url, mobile_img_url, pc_ground_img_url, mobile_ground_img_url, "
            + "pc_img_name,pc_ground_img_name,mobile_img_name,mobile_ground_img_name,"
            + "content, type, last_opt_user_name, last_opt_uid, status, create_time, update_time) "
            + "VALUES(#{model.pcImgUrl},#{model.mobileImgUrl},#{model.pcGroundImgUrl},#{model.mobileGroundImgUrl},"
            + "#{model.pcBannerImgName},#{model.pcGroundImgName},#{model.mobileBannerImgName},#{model.mobileGroundImgName},"
            + "#{model.content},#{model.type},#{model.lastOptUserName},#{model.lastOptUid},#{model.status},#{model.createTime},#{model.updateTime})")
    int insertCard(@Param("model") HomePageCardDaoModel model);

    @Select("SELECT * FROM sw_home_page_card WHERE status=0")
    @Results({ @Result(property = "id", column = "id"), @Result(property = "pcImgUrl", column = "pc_img_url"),
            @Result(property = "mobileImgUrl", column = "mobile_img_url"),
            @Result(property = "pcGroundImgUrl", column = "pc_ground_img_url"),
            @Result(property = "mobileGroundImgUrl", column = "mobile_ground_img_url"),
            
            @Result(property = "pcBannerImgName", column = "pc_img_name"),
            @Result(property = "mobileBannerImgName", column = "mobile_img_name"),
            @Result(property = "pcGroundImgName", column = "pc_ground_img_name"),
            @Result(property = "mobileGroundImgName", column = "mobile_ground_img_name"),
            
            @Result(property = "content", column = "content"), @Result(property = "type", column = "type"),
            @Result(property = "lastOptUserName", column = "last_opt_user_name"),
            @Result(property = "lastOptUid", column = "last_opt_uid"), @Result(property = "status", column = "status"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "updateTime", column = "update_time") })
    List<HomePageCardDaoModel> listCards();

    @Update("UPDATE sw_home_page_card SET pc_img_url=#{model.pcImgUrl},mobile_img_url=#{model.mobileImgUrl},pc_ground_img_url=#{model.pcGroundImgUrl},"
            + "mobile_ground_img_url=#{model.mobileGroundImgUrl},"
            + "pc_img_name=#{model.pcBannerImgName},pc_ground_img_name=#{model.pcGroundImgName},"
            + "mobile_img_name=#{model.mobileBannerImgName},mobile_ground_img_name=#{model.mobileGroundImgName},"
            + "content=#{model.content},type=#{model.type},last_opt_user_name=#{model.lastOptUserName},last_opt_uid=#{model.lastOptUid},update_time=#{model.updateTime} WHERE id=#{model.id}")
    int updateCard(@Param("model") HomePageCardDaoModel model);

    @Select("SELECT COUNT(*) FROM sw_home_page_card WHERE status=0")
    int countCard();

}
