package com.phicomm.smarthome.ssp.server.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;

import com.phicomm.smarthome.ssp.server.model.UseHelpModel;

public interface UseHelpMapper {
    
    @Insert("INSERT INTO sw_use_help(title, html, client, sequence, last_opt_uid,  last_opt_user_name, status, create_time, update_time) "
            + "VALUES(#{model.title}, #{model.html}, #{model.client}, #{model.sequence}, #{model.lastOptUid}, #{model.lastOptUserName}, "
            + "#{model.status}, #{model.createTime}, #{model.updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "model.id", keyColumn = "id")
    int addUseHelp(@Param("model") UseHelpModel model);

    @UpdateProvider(type = UseHelpProvider.class,method = "editUseHelp")
    int editUseHelp(UseHelpModel model);

    @InsertProvider(type = UseHelpProvider.class, method = "batchAddContentUrlList") 
    int addContentUrlList(@Param("list") List<UseHelpModel.HelpContent> modelList);

    @Update("UPDATE `sw_use_help` SET " + "STATUS=-1,"
            + "last_opt_uid=#{model.lastOptUid},last_opt_user_name=#{model.lastOptUserName},update_time=#{model.updateTime} "
            + "WHERE id=#{model.id} AND status >=0")
    int deleteUseHelp(@Param("model") UseHelpModel model);

    @Update("UPDATE `sw_use_help_content` SET " + "STATUS=-1 "
            + "WHERE use_help_id=#{model.id} AND status >=0")
    int deleteUseHelpContentUrl(@Param("model") UseHelpModel model);

    @Update("UPDATE `sw_use_help` SET "
            + "sequence=#{sequence},"
            + "last_opt_uid=#{lastOptUid},last_opt_user_name=#{realName} "
            + "WHERE id=#{id}")
    int moveUseHelp(@Param("id") int id, @Param("sequence") int sequence, @Param("lastOptUid") long lastOptUid,
                    @Param("realName") String realName);

    @Select("SELECT MAX(sequence) FROM sw_use_help WHERE status = 0")
    Integer getMaxSequence();

    @Select("SELECT * FROM `sw_use_help` WHERE STATUS = 0 AND client=#{client} ORDER BY sequence ASC LIMIT #{startPage},#{endPage}")
    @Results({ @Result(property = "id", column = "id"), @Result(property = "title", column = "title"),
            @Result(property = "html", column = "html"), @Result(property = "sequence", column = "sequence"),
            @Result(property = "client", column = "client"),
            @Result(property = "lastOptUid", column = "last_opt_uid"),
            @Result(property = "lastOptUserName", column = "last_opt_user_name"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "updateTime", column = "update_time") })
    List<UseHelpModel> getUseHelpList(@Param("client") String client, @Param("startPage") int startPage, @Param("endPage") int endPage);

    @Select("SELECT count(*) FROM `sw_use_help` WHERE STATUS = 0 AND client=#{client}")
    int countUseHelpList(@Param("client") String client);

    
    @Select("SELECT * FROM `sw_use_help_content` WHERE STATUS = 0 ORDER BY sequence ASC")
    @Results({ @Result(property = "id", column = "id"), @Result(property = "useHelpId", column = "use_help_id"),
            @Result(property = "content", column = "help_content"), @Result(property = "type", column = "type")})
    List<UseHelpModel.HelpContent> getHelpContentList();

    @Select("SELECT COUNT(*) FROM `sw_use_help` WHERE STATUS = 0")
    int getUseHelpListCount();
}
