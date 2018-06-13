package com.phicomm.smarthome.ssp.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phicomm.smarthome.ssp.server.consts.Const;
import com.phicomm.smarthome.ssp.server.dao.ProblemFeedbackMapper;
import com.phicomm.smarthome.ssp.server.model.SwProblemFeedbackContentModel;
import com.phicomm.smarthome.ssp.server.model.SwProblemFeedbackModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseProblemFeedBackModel;
import com.phicomm.smarthome.ssp.server.service.ProblemFeedbackService;
import com.phicomm.smarthome.ssp.server.util.CommonUtils;
import com.phicomm.smarthome.ssp.server.util.MyListUtils;
import com.phicomm.smarthome.util.OptDateUtil;
import com.phicomm.smarthome.util.StringUtil;

/**
 * Created by zhengwang.li on 2018/5/4.
 */
@Service
public class ProblemFeedbackServiceimpl implements ProblemFeedbackService {

    private static final Logger LOGGER = LogManager.getLogger(ProblemFeedbackServiceimpl.class);

    @Autowired
    private ProblemFeedbackMapper mapper;

    /** 文本值为1 */
    public static final int TEXT_VALUE = 1;
    
    /** 图片值为1 */
    public static final int PIC_VALUE = 2;
    
    @Override
    public ResponseProblemFeedBackModel listProblemFeedbacks(Integer curPage, Integer pageSize, SwProblemFeedbackModel model) {
        ResponseProblemFeedBackModel rspObj = new ResponseProblemFeedBackModel();
        List<SwProblemFeedbackModel> list = new ArrayList<>();
        long count = 0;
        try {
            if (!StringUtil.isNullOrEmpty(model.getFeedbackTimeStr())) {
                model.setStartTime(OptDateUtil.getLTimeByStr(model.getFeedbackTimeStr().replaceAll("/", "-") + " 00:00:00"));
                model.setEndTime(OptDateUtil.getLTimeByStr(model.getFeedbackTimeStr().replaceAll("/", "-") + " 23:59:59"));
            }
            if (!StringUtil.isNullOrEmpty(model.getBroadbandOperator())) {
                model.setBroadbandOperator("%" + model.getBroadbandOperator() + "%");
            }
            if (!StringUtil.isNullOrEmpty(model.getBrowserSystemVersion())) {
                model.setBrowserSystemVersion("%" + model.getBrowserSystemVersion() + "%");
            }
            if (!StringUtil.isNullOrEmpty(model.getPhoneModelVersion())) {
                model.setPhoneModelVersion("%" + model.getPhoneModelVersion() + "%");
            }
            model.setStarePage((curPage-1) * pageSize);
            model.setEndPage(pageSize);
            list = mapper.listProblemFeedbacks(model);
            count = mapper.countProblemFeedbacks(model);
            StringBuilder listId = new StringBuilder();
            if (MyListUtils.isEmpty(list)) {
                rspObj.setList(list);
                rspObj.setTotalCount((int)count);
                return rspObj;
            }
            for (SwProblemFeedbackModel swProblemFeedbackModel : list) {
                listId.append(swProblemFeedbackModel.getId() + ",");
            }
            String idString = listId.toString().substring(0, listId.toString().lastIndexOf(","));            
            List<SwProblemFeedbackContentModel> listContent = mapper.listProblemFeedbackContent(idString);
            for (SwProblemFeedbackModel feedbackModel : list) {
                for (SwProblemFeedbackContentModel contentModel : listContent) {
                    if (contentModel.getProblemFeedbackId() == feedbackModel.getId()) {
                        if (contentModel.getContentType().equals(PIC_VALUE + "")) {
                            feedbackModel.setImgUrl(contentModel.getFeedbackContent());
                        }
                        if (contentModel.getContentType().equals(TEXT_VALUE + "")) {
                            feedbackModel.setFeedbackContent(contentModel.getFeedbackContent());
                        }
                    }
                }
                if (StringUtil.isNullOrEmpty(feedbackModel.getImgUrl())) {
                    feedbackModel.setImgUrl("");
                }
                if (StringUtil.isNullOrEmpty(feedbackModel.getFeedbackContent())) {
                    feedbackModel.setFeedbackContent("");
                }
            }
        } catch (Exception e) {
            LOGGER.error(e);
            rspObj.setRetCode(Const.ResponseStatus.STATUS_COMMON_FAILED);
            rspObj.setRetMsg(Const.ResponseStatus.STATUS_COMMON_FAILED_STR);
            return rspObj;
        }
        for (SwProblemFeedbackModel problemFeedbackModel: list) {
            problemFeedbackModel.setFeedbackTimeStr(CommonUtils.stampToDateTimeStr(problemFeedbackModel.getFeedbackTime(), "yyyy/MM/dd hh:mm"));
        }
        rspObj.setList(list);
        rspObj.setTotalCount((int)count);
        return rspObj;
    }
}
