package com.phicomm.smarthome.ssp.server.controller.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.phicomm.smarthome.model.SmartHomeResponse;
import com.phicomm.smarthome.ssp.server.consts.Const;
import com.phicomm.smarthome.ssp.server.controller.BaseController;
import com.phicomm.smarthome.ssp.server.model.SwProblemFeedbackModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseProblemFeedBackModel;
import com.phicomm.smarthome.ssp.server.service.ProblemFeedbackService;


/**
 * Created by zhengwang.li on 2018/5/4.
 */
@ComponentScan
@RestController
@RequestMapping("config/problem_feedback")
public class ProblemFeedbackController extends BaseController {

    private static final Logger LOGGER = LogManager.getLogger(ProblemFeedbackController.class);

    @Autowired
    private ProblemFeedbackService service;

    @RequestMapping(value = "/{cur_page}/{page_size}", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<Object> listProblemFeedbacks(@PathVariable("cur_page") Integer curPage, @PathVariable("page_size") Integer pageSize,
                                                    @RequestBody SwProblemFeedbackModel model) {
        ResponseProblemFeedBackModel rspObj = new ResponseProblemFeedBackModel();
        if (curPage == null || curPage == 0 || pageSize == null || pageSize == 0 ) {
            return errorResponse(Const.ResponseStatus.STATUS_COMMON_NULL,
                    Const.ResponseStatus.STATUS_COMMON_NULL_STR);
        }
        LOGGER.info("feedback_time[{}], scene_type[{}], problem_type[{}], broadband_operator[{}], phone_model_version[{}], browser_system_version[{}]", model.getFeedbackTime());
        try {
            LOGGER.info("ProblemFeedbackController beganing... ");
            rspObj = service.listProblemFeedbacks(curPage, pageSize, model);
            LOGGER.info("ProblemFeedbackController end. ");
        } catch (Exception e) {
            LOGGER.error(e);
            return errorResponse(Const.ResponseStatus.STATUS_COMMON_FAILED, Const.ResponseStatus.STATUS_COMMON_FAILED_STR);
        }
        return successResponse(rspObj);
    }

}
