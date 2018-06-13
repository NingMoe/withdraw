package com.phicomm.smarthome.ssp.server.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import com.phicomm.smarthome.ssp.server.model.SwProblemFeedbackContentModel;
import com.phicomm.smarthome.ssp.server.model.SwProblemFeedbackModel;

/**
 * Created by zhengwang.li on 2018/5/4.
 */
public interface ProblemFeedbackMapper {

    @SelectProvider(type = ProblemFeedbackProvider.class, method = "listProblemFeedbacks")
    @Results({ @Result(property = "id", column = "id"),
            @Result(property = "feedbackTime", column = "feedback_time"),
            @Result(property = "sceneType", column = "scene_type"),
            @Result(property = "rewardAmount", column = "reward_amount"),
            @Result(property = "purchaseTime", column = "purchase_time"),
            @Result(property = "problemType", column = "problem_type"),
            @Result(property = "broadbandOperator", column = "broadband_operator"),
            @Result(property = "phoneModelVersion", column = "phone_model_version"),
            @Result(property = "browserSystemVersion", column = "browser_system_version"),
            @Result(property = "contactWay", column = "contact_way") })
    List<SwProblemFeedbackModel> listProblemFeedbacks(SwProblemFeedbackModel model);
    
    @SelectProvider(type = ProblemFeedbackProvider.class, method = "countProblemFeedbacks")
    int countProblemFeedbacks(SwProblemFeedbackModel model);
    
    @Select("SELECT * FROM sw_problem_feedback_content WHERE problem_feedback_id IN (${listId})")
    @Results({ @Result(property = "id", column = "id"),
        @Result(property = "problemFeedbackId", column = "problem_feedback_id"),
        @Result(property = "feedbackContent", column = "feedback_content"),
        @Result(property = "contentType", column = "content_type"),
        @Result(property = "contentSequence", column = "content_sequence") })
    List<SwProblemFeedbackContentModel> listProblemFeedbackContent(@Param("listId") String listId);
}
