package com.phicomm.smarthome.ssp.server.dao;

import com.phicomm.smarthome.ssp.server.model.SwProblemFeedbackModel;
import com.phicomm.smarthome.util.StringUtil;
import org.apache.ibatis.jdbc.SQL;

/**
 * Created by zhengwang.li on 2018/5/4.
 */
public class ProblemFeedbackProvider {

    public String listProblemFeedbacks(final SwProblemFeedbackModel model) {
        return new SQL() {
            {
                SELECT("*");
                FROM("sw_problem_feedback");
                WHERE("1=1");
                if (!StringUtil.isNullOrEmpty(model.getFeedbackTimeStr())) {
                    WHERE("feedback_time <= #{endTime}");
                }
                if (!StringUtil.isNullOrEmpty(model.getFeedbackTimeStr())) {
                    WHERE("feedback_time >= #{startTime}");
                }
                if (model.getSceneType() != 0 && model.getSceneType() != -1) {
                    WHERE("scene_type=#{sceneType}");
                }
                if (model.getProblemType() != 0 && model.getProblemType() != -1) {
                    WHERE("problem_type=#{problemType}");
                }
                if (!StringUtil.isNullOrEmpty(model.getBroadbandOperator())) {
                    WHERE("broadband_operator like #{broadbandOperator}");
                }
                if (!StringUtil.isNullOrEmpty(model.getPhoneModelVersion())) {
                    WHERE("phone_model_version like #{phoneModelVersion}");
                }
                if (!StringUtil.isNullOrEmpty(model.getBrowserSystemVersion())) {
                    WHERE("browser_system_version like #{browserSystemVersion}");
                }
                ORDER_BY("feedback_time DESC");
            }
        }.toString() + " LIMIT #{starePage},#{endPage}";
    }
    
    public String countProblemFeedbacks(final SwProblemFeedbackModel model) {
        return new SQL() {
            {
                SELECT("COUNT(*)");
                FROM("sw_problem_feedback");
                WHERE("1=1");
                if (!StringUtil.isNullOrEmpty(model.getFeedbackTimeStr())) {
                    WHERE("feedback_time <= #{endTime}");
                }
                if (!StringUtil.isNullOrEmpty(model.getFeedbackTimeStr())) {
                    WHERE("feedback_time >= #{startTime}");
                }
                if (model.getSceneType() != 0 && model.getSceneType() != -1) {
                    WHERE("scene_type=#{sceneType}");
                }
                if (model.getProblemType() != 0 && model.getProblemType() != -1) {
                    WHERE("problem_type=#{problemType}");
                }
                if (!StringUtil.isNullOrEmpty(model.getBroadbandOperator())) {
                    WHERE("broadband_operator like #{broadbandOperator}");
                }
                if (!StringUtil.isNullOrEmpty(model.getPhoneModelVersion())) {
                    WHERE("phone_model_version like #{phoneModelVersion}");
                }
                if (!StringUtil.isNullOrEmpty(model.getBrowserSystemVersion())) {
                    WHERE("browser_system_version like #{browserSystemVersion}");
                }
            }
        }.toString();
    }
}
