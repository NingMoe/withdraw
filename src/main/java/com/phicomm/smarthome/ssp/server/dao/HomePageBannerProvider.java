package com.phicomm.smarthome.ssp.server.dao;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import com.phicomm.smarthome.ssp.server.model.dao.HomePageBannerDaoModel;

public class HomePageBannerProvider {
    
    public String batchAddWebsiteBanner(Map map) {  
        List<HomePageBannerDaoModel> list = (List<HomePageBannerDaoModel>) map.get("list");
        StringBuilder sb = new StringBuilder();  
        sb.append("INSERT INTO sw_website_banner ");  
        sb.append("(id, backend_pc_banner_img_url, backend_pc_ground_img_url, backend_mobile_banner_img_url, backend_mobile_ground_img_url, ");
        sb.append("action, sequence, last_opt_user_name, last_opt_uid, status, ");
        sb.append("create_time, update_time) "); 
        sb.append("VALUES ");  
        MessageFormat mf = new MessageFormat("(#'{'list[{0}].id}, #'{'list[{0}].backendPcBannerImgUrl},"
                + "#'{'list[{0}].backendPcGroundImgUrl}, #'{'list[{0}].backendMobileBannerImgUrl}, #'{'list[{0}].backendMobileGroundImgUrl},"
                + "#'{'list[{0}].action},#'{'list[{0}].sequence},#'{'list[{0}].lastOptUserName},#'{'list[{0}].lastOptUid},"
                + "#'{'list[{0}].status},#'{'list[{0}].createTime},#'{'list[{0}].updateTime})");
        for (int i = 0; i < list.size(); i++) {
            sb.append(mf.format(new Object[]{i}));  
            if (i < list.size() - 1) {
                sb.append(",");  
            }  
        }  
        return sb.toString();  
    }
}
