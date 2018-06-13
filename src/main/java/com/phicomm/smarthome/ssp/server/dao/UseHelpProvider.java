package com.phicomm.smarthome.ssp.server.dao;

import com.phicomm.smarthome.ssp.server.model.UseHelpModel;
import com.phicomm.smarthome.util.StringUtil;
import org.apache.ibatis.jdbc.SQL;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

public class UseHelpProvider {

    public String editUseHelp(final UseHelpModel model) {
        return new SQL() {
            {
                UPDATE("sw_use_help");
                if (model != null && !StringUtil.isNullOrEmpty(model.getTitle())) {
                    SET("title = #{title}");
                }
                if (model != null && !StringUtil.isNullOrEmpty(model.getHtml())) {
                    SET("html = #{html}");
                }
                if (model != null && StringUtil.isNullOrEmpty(model.getClient())) {
                    SET("client = #{client}");
                }
                if (model != null && !StringUtil.isNullOrEmpty(model.getLastOptUserName())) {
                    SET("last_opt_user_name = #{lastOptUserName}");
                }
                if (model != null && model.getLastOptUid() != 0) {
                    SET("last_opt_uid = #{lastOptUid}");
                }
                SET("update_time = #{updateTime}");
                WHERE("id = #{id} AND status = 0");
            }
        }.toString();
    }

    public String batchAddContentUrlList(Map map) {  
        List<UseHelpModel.HelpContent> list = (List<UseHelpModel.HelpContent>) map.get("list");
        StringBuilder sb = new StringBuilder();  
        sb.append("INSERT INTO sw_use_help_content ");
        sb.append("(use_help_id, help_content, type, sequence, status) ");
        sb.append("VALUES ");  
        MessageFormat mf = new MessageFormat("(#'{'list[{0}].useHelpId}, #'{'list[{0}].content},"
                + "#'{'list[{0}].type}, #'{'list[{0}].sequence}, #'{'list[{0}].status})");
        for (int i = 0; i < list.size(); i++) {
            sb.append(mf.format(new Object[]{i}));  
            if (i < list.size() - 1) {
                sb.append(",");  
            }  
        }  
        return sb.toString();  
    }  
}  

