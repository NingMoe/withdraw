package com.phicomm.smarthome.ssp.server.service;

import com.phicomm.smarthome.ssp.server.model.SwProblemFeedbackModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseProblemFeedBackModel;

/**
 * Created by zhengwang.li on 2018/5/4.
 */
public interface ProblemFeedbackService {

    ResponseProblemFeedBackModel listProblemFeedbacks(Integer curPage, Integer pageSize, SwProblemFeedbackModel model);
}
