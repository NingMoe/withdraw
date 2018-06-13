package com.phicomm.smarthome.ssp.server.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.phicomm.smarthome.ssp.server.model.dao.HomePageBannerDaoModel;

/**
 * 描述:官网管理数据库操作 Created by zhengwang.li on 2018/4/17.
 */
public interface HomePageBannerMapper {

    /** 查询banner的sequence排序最大值，状态为0或者1 */
    @Select("SELECT MAX(sequence) FROM sw_home_page_banner WHERE status=0")
    Integer getMaxSequence();

    /** 插入一条banner信息 */
    @Insert("INSERT INTO sw_home_page_banner("
            + "backend_pc_banner_img_url,backend_pc_ground_img_url,backend_mobile_banner_img_url,"
            + "backend_mobile_ground_img_url,"
            + "pc_banner_img_name,pc_ground_img_name,mobile_banner_img_name,mobile_ground_img_name,"
            + "action,sequence,last_opt_user_name,last_opt_uid,status,create_time,update_time) "
            + "VALUES(#{model.backendPcBannerImgUrl},#{model.backendPcGroundImgUrl},#{model.backendMobileBannerImgUrl},"
            + "#{model.backendMobileGroundImgUrl},"
            + "#{model.pcBannerImgName},#{model.pcGroundImgName},#{model.mobileBannerImgName},#{model.mobileGroundImgName},"
            + "#{model.action},#{model.sequence},#{model.lastOptUserName},#{model.lastOptUid},#{model.status},#{model.createTime},#{model.updateTime})")
    int addBanner(@Param("model") HomePageBannerDaoModel model);

    /** 通过Id更新banner信息 */
    @Update("UPDATE sw_home_page_banner SET "
            + "backend_pc_banner_img_url=#{model.backendPcBannerImgUrl},backend_pc_ground_img_url=#{model.backendPcGroundImgUrl},"
            + "backend_mobile_banner_img_url=#{model.backendMobileBannerImgUrl},backend_mobile_ground_img_url=#{model.backendMobileGroundImgUrl},"
            + "pc_banner_img_name=#{model.pcBannerImgName},pc_ground_img_name=#{model.pcGroundImgName},"
            + "mobile_banner_img_name=#{model.mobileBannerImgName},mobile_ground_img_name=#{model.mobileGroundImgName},"
            + "action=#{model.action},last_opt_user_name=#{model.lastOptUserName},last_opt_uid=#{model.lastOptUid},"
            + "update_time=#{model.updateTime} WHERE id = #{model.id}")
    int editBannerById(@Param("model") HomePageBannerDaoModel model);

    /** 查询banner记录数，状态为0或者1 */
    @Select("SELECT COUNT(*) FROM sw_home_page_banner WHERE status=0")
    int countBanners();

    @Update("UPDATE sw_home_page_banner SET status = #{model.status}, last_opt_user_name=#{model.lastOptUserName},"
            + "last_opt_uid=#{model.lastOptUid},update_time=#{model.updateTime}  WHERE id = #{model.id}")
    void deleteBanner(@Param("model") HomePageBannerDaoModel model);

    @Update("UPDATE sw_website_banner SET status=-1")
    void deleteWebsiteBanner();
    
    @Select("SELECT status FROM sw_home_page_banner WHERE id = #{id}")
    int getStatusById(@Param("id") long id);

    @Select("SELECT * FROM sw_home_page_banner WHERE status=0 ORDER BY sequence")
    @Results({ @Result(property = "id", column = "id"),
            @Result(property = "backendPcBannerImgUrl", column = "backend_pc_banner_img_url"),
            @Result(property = "backendPcGroundImgUrl", column = "backend_pc_ground_img_url"),
            @Result(property = "backendMobileBannerImgUrl", column = "backend_mobile_banner_img_url"),
            @Result(property = "backendMobileGroundImgUrl", column = "backend_mobile_ground_img_url"),
            @Result(property = "action", column = "action"), @Result(property = "sequence", column = "sequence"),
            @Result(property = "lastOptUserName", column = "last_opt_user_name"),
            @Result(property = "lastOptUid", column = "last_opt_uid"), @Result(property = "status", column = "status"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "updateTime", column = "update_time") })
    List<HomePageBannerDaoModel> listBanners();

    @Select("SELECT * FROM sw_home_page_banner WHERE status=0 ORDER BY sequence")
    @Results({ @Result(property = "id", column = "id"),
            // 管理后台图片url
            @Result(property = "backendPcBannerImgUrl", column = "backend_pc_banner_img_url"),
            @Result(property = "backendPcGroundImgUrl", column = "backend_pc_ground_img_url"),
            @Result(property = "backendMobileBannerImgUrl", column = "backend_mobile_banner_img_url"),
            @Result(property = "backendMobileGroundImgUrl", column = "backend_mobile_ground_img_url"),
             // 图片名称
            @Result(property = "pcBannerImgName", column = "pc_banner_img_name"),
            @Result(property = "pcGroundImgName", column = "pc_ground_img_name"),
            @Result(property = "mobileBannerImgName", column = "mobile_banner_img_name"),
            @Result(property = "mobileGroundImgName", column = "mobile_ground_img_name"),
            @Result(property = "action", column = "action"), @Result(property = "sequence", column = "sequence"),
            @Result(property = "lastOptUserName", column = "last_opt_user_name"),
            @Result(property = "lastOptUid", column = "last_opt_uid"), @Result(property = "status", column = "status"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "updateTime", column = "update_time") })
    List<HomePageBannerDaoModel> listBannersForBackend();

    @Update("UPDATE sw_home_page_banner SET offc_pc_banner_img_url=#{model.offcPcBannerImgUrl}, offc_pc_ground_img_url=#{model.offcPcGroundImgUrl},"
            + "offc_mobile_banner_img_url=#{model.offcMobileBannerImgUrl}, offc_mobile_ground_img_url=#{model.offcMobileGroundImgUrl},"
            + "status=#{model.status} WHERE id = #{model.id}")
    void updateOffcImgById(@Param("model") HomePageBannerDaoModel daoModel);

    @Update("UPDATE sw_home_page_banner SET status = #{model.status} WHERE id = #{model.id}")
    void updateStatusById(@Param("model") HomePageBannerDaoModel model);

    @Insert("INSERT INTO sw_home_page_publish_log(public_time, last_opt_user_name, last_opt_uid, status) "
            + "VALUES(#{model.updateTime}, #{model.lastOptUserName}, #{model.lastOptUid}, #{model.status})")
    void insertPublicLog(@Param("model") HomePageBannerDaoModel model);

    @Update("UPDATE sw_home_page_banner SET sequence=#{sequence}, update_time=#{updateTime}, last_opt_user_name=#{realName}, last_opt_uid=#{lastOptUid} WHERE id=#{id}")
    int updateSequenceById(@Param("id") int id, @Param("sequence") int sequence, @Param("updateTime") long updateTime,
            @Param("realName") String realName, @Param("lastOptUid") long uid);

    @Select("SELECT MAX(public_time) FROM sw_home_page_publish_log WHERE status=0")
    Long getPublicTime();
    
    @InsertProvider(type = HomePageBannerProvider.class, method = "batchAddWebsiteBanner") 
    int addWebsiteBannerList(@Param("list") List<HomePageBannerDaoModel> modelList);
}
